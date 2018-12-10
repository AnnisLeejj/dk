package com.annis.baselib.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import com.annis.baselib.R;
import com.annis.baselib.base.adapter.BaseViewHolder;
import com.annis.baselib.base.adapter.RvAdapter;
import com.annis.baselib.base.base.BaseFragment;
import com.annis.baselib.utils.ImageCacheLoad.ImageLoader;
import com.annis.baselib.utils.ext_utils.FileUtilsExt;
import com.annis.baselib.utils.recycleview.FullyGridLayoutManager;
import com.annis.baselib.utils.utils_haoma.TimeUtils;
import com.annis.baselib.utils.utils_haoma.ToastUtils;
import com.annis.baselib.view.model.ImageEntity;
import com.bm.library.PhotoView;
import com.google.android.material.snackbar.Snackbar;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.werb.pickphotoview.PickPhotoView;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ImagePicker extends BaseFragment implements View.OnTouchListener {
    /**
     * 显示模式(默认)
     */
    public static final String MODE_SHOW = "MODE_SHOW";
    /**
     * 采集模式
     */
    public static final String MODE_PICK = "MODE_PICK";
    /**
     * 当前的模式
     */
    private String MODE = MODE_SHOW;

    /**
     * 照片 设置的回调
     */
    ImgSetting<ImageEntity> imgSetting;
    RecyclerView recyclerView;
    RvAdapter adapter;

    int itemSzie = 0;

    public List<ImageEntity> getDatas() {
        List<ImageEntity> result = new ArrayList<>();
        result.addAll(adapter.getItems());
        if (MODE.equals(MODE_PICK)) {
            result.remove(0);
        }
        return result;
    }

    public void setMode(String mode) {
        if (adapter != null && adapter.getItemCount() > 0) {
            return;
        }
        if (mode.equals(MODE_PICK) || mode.equals(MODE_SHOW)) {
            if (mode.equals(MODE)) {
                return;
            }
            this.MODE = mode;
        }
    }

    public void setWindowWidth(int windowWidth) {
        itemSzie = (windowWidth * 19) / 80;
    }

    /**
     * 所有配置参数设置好了才 填充数据,否则再设置参数将会无效
     *
     * @param list
     */
    public void setData(List<ImageEntity> list, @NonNull ImgSetting<ImageEntity> imgSetting) {
        if (itemSzie == 0) {
            return;
        }
        List<ImageEntity> newData = new ArrayList<>();
        if (list != null) {
            for (ImageEntity item : list) {
                if (!TextUtils.isEmpty(item.getUrl())) {
                    newData.add(item);
                }
            }
        }
        if (MODE.equals(MODE_PICK)) {
            newData.add(0, new ImageEntity("", ""));
        }
        this.imgSetting = imgSetting;
        adapter.setDatas(newData);
    }

    /**
     * 添加照片
     */
    @SuppressLint("CheckResult")
    private void toAdd() {
        /***  检测权限  ***/
        permissions.requestEach(Manifest.permission.CAMERA).subscribe(permission -> {
            if (permission.granted) {
                tokePhoto();
            } else if (permission.shouldShowRequestPermissionRationale) {
                ToastUtils.showLongToast("请允许拍照。");
            } else {
                //永远拒绝
                Snackbar.make(getView(), "您已禁止拍照，请手动添加权限。", Snackbar.LENGTH_INDEFINITE).setAction("添加", v1 -> {
                    //启动到手机的设置页面
                    startActivity(new Intent(Settings.ACTION_SETTINGS));
                }).show();
            }
        });
    }

    /**
     * 保存最近一次照片路径
     */
    private String photoPath;

    private void tokePhoto() {
        photoPath = FileUtilsExt.getPhotoPath() + TimeUtils.date2String(new Date(), "yyyyMMddHHmmss") + ".png";
        new PickPhotoView.Builder(getActivity())
                .setPickPhotoSize(1)                  // select image size
                .setClickSelectable(true)             // click one image immediately close and return image
                .setShowCamera(true)                  // is show camera
                .setSpanCount(3)                      // span count
                .setLightStatusBar(true)              // lightStatusBar used in Android M or higher
                .setStatusBarColor(R.color.white)     // statusBar color
                .setToolbarColor(R.color.white)       // toolbar color
                .setToolbarTextColor(R.color.black)   // toolbar text color
                // .setSelectIconColor(R.color.pink)     // select icon color
                .setShowGif(false)                    // is show gif
                .start();
    }

    /**
     * 预览照片
     *
     * @param items
     * @param position
     */
    ImagePreviewer<ImageEntity> imagePreviewer;

    private void previewImage(List<ImageEntity> items, int position) {
        //TODO sometime can't push out
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        imagePreviewer = new ImagePreviewer<>();
        imagePreviewer.setData(items, position, imgSetting);
        imagePreviewer.show(transaction, "imageViewer");
        transaction.commit();
    }

    RxPermissions permissions;

    @Override
    public int getLayoutID() {
        return R.layout.part_recycleview;
    }

    @Override
    public void initView(View view) {
        /**
         * 配合 onTouch 方法 防止点击事件穿透问题
         */
        view.setOnTouchListener(this);
    }

    @Override
    public void initData() {
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = getView().findViewById(R.id.rv);
        recyclerView.setLayoutManager(new FullyGridLayoutManager(getActivity(), 4));
        adapter = new RvAdapter<ImageEntity>(view.getContext(), (item, position) -> {
            if (MODE.equals(MODE_PICK) && TextUtils.isEmpty(item.getUrl())) {
                toAdd();
            } else {
                //TODO 预览图片
                List items = new ArrayList(adapter.getItems());
                if (MODE.equals(MODE_PICK)) {
                    items.remove(0);
                    previewImage(items, position - 1);
                } else {
                    previewImage(items, position);
                }
            }
        }) {
            @Override
            public void bindView(BaseViewHolder holder, int position, ImageEntity item) {
                ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
                params.width = itemSzie;
                params.height = itemSzie;
                if (MODE.equals(MODE_SHOW)) {
                    holder.getView(R.id.item_picker_delete).setVisibility(View.GONE);
                } else {
                    holder.getView(R.id.item_picker_delete).setVisibility(View.VISIBLE);
                    holder.getView(R.id.item_picker_delete).setOnClickListener(v -> removeData(position));
                }
                if (TextUtils.isEmpty(item.getUrl())) {
                    holder.getView(R.id.item_picker_delete).setVisibility(View.GONE);
                    holder.setImageRes(R.id.item_picker_iv, R.mipmap.ic_add_image);
                } else {
                    ImageLoader.getInstance().loadImageView(holder.getView(R.id.item_picker_iv), item.getUrl());
                }
            }

            @Override
            public int getLayoutId() {
                return R.layout.item_image_picker;
            }
        };
        recyclerView.setAdapter(adapter);
        if (permissions == null) {
            permissions = new RxPermissions(getActivity());
        }
    }

    /**
     * 拍照返回结果
     *
     * @param path
     */
    public void onPhotoReceived(String path) {
        final File oldFile = new File(path);
        //图片压缩
        Luban.with(getContext()).load(oldFile).setCompressListener(new OnCompressListener() {
            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess(File file) {
                //移动到原来的路径
                FileUtilsExt.copyFiles(file.getAbsolutePath(), photoPath, true);
                adapter.addData(new ImageEntity(photoPath, ""));
            }

            @Override
            public void onError(Throwable e) {
            }
        }).launch();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return true;
    }

    public interface ImgSetting<T> {
        void setImage(PhotoView view, T t);
    }
}