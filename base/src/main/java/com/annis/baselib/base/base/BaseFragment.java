package com.annis.baselib.base.base;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import com.annis.baselib.utils.DownloadUtil;
import com.annis.baselib.utils.LogUtils;

import java.io.File;

import static android.content.Context.DOWNLOAD_SERVICE;

public abstract class BaseFragment extends Fragment implements View.OnTouchListener {
    View rootView;
    /**
     * 是否已经初始化
     **/
    boolean inited = false;
    protected static final String OBJECT = "object";

    public abstract int getLayoutID();

    public abstract void initView(View view);

    public abstract void initData();

    public void showToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    public void startActivity(Class clazz) {
        startActivity(new Intent(getActivity(), clazz));
    }

    public void startActivity(Class clazz, String key, String value) {
        Intent intent = new Intent(getActivity(), clazz);
        intent.putExtra(key, value);
        startActivity(intent);
    }

    public <T extends Parcelable> void nextAcitvity(Class clazz, T obj) {
        Intent intent = new Intent(getActivity(), clazz);
        intent.putExtra(OBJECT, obj);
        startActivity(intent);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        if (!inited) {
            initView(view);
            initData();
            inited = true;
        }
        super.onViewCreated(view, savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(getLayoutID(), container, false);
        } else {
            //缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
        }
        /***配合 onTouch 方法 防止点击事件穿透问题***/
        rootView.setOnTouchListener(this);
        return rootView;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //true 告诉 上级视图  本视图 决定本次点击事件处理完毕
        return true;
    }

    /**
     * 文件下载
     *
     * @param url
     */
    ProgressDialog progressDialog;

    public void downFile(String url, String parentDir, String fileName, @NonNull DownloadUtil.OnDownloadListener listener) {
        File file = new File(parentDir);
        if (!file.exists())
            file.mkdir();
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("正在下载");
        progressDialog.setMessage("请稍后...");
        progressDialog.setProgress(0);
        progressDialog.setMax(100);
        progressDialog.show();
        progressDialog.setCancelable(false);
        DownloadUtil.get().download(url, parentDir, fileName, new DownloadUtil.OnDownloadListener() {
            @Override
            public void onDownloadSuccess(File file) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                listener.onDownloadSuccess(file);
            }

            @Override
            public void onDownloading(int progress) {
                LogUtils.w("progress", "文件下载:" + progress);
                progressDialog.setProgress(progress);
                listener.onDownloading(progress);
            }

            @Override
            public void onDownloadFailed(Exception e) {
                //下载异常进行相关提示操作
                progressDialog.dismiss();
                listener.onDownloadFailed(e);
            }
        });
    }

    DownloadManager mDownloadManager;
    long downloadId = 0;

    /**
     * 显示软件下载对话框
     */
    protected void showDownloadDialog(String downPath, String fileName) {//下载路径 根据服务器返回的apk存放路径
        // 使用系统下载类
        mDownloadManager = (DownloadManager) getActivity().getSystemService(DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(downPath);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setAllowedOverRoaming(false);
        //创建目录下载
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);
        // 把id保存好，在接收者里面要用
        downloadId = mDownloadManager.enqueue(request);
        //设置允许使用的网络类型，这里是移动网络和wifi都可以
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
        //机型适配
        request.setMimeType("application/vnd.android.package-archive");
        //通知栏显示
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setTitle("下载");
        request.setDescription("正在下载中...");
        request.setVisibleInDownloadsUi(true);
        getActivity().registerReceiver(mReceiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            checkStatus();
        }
    };

    /**
     * 检查下载状态
     */
    private void checkStatus() {
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(downloadId);
        Cursor cursor = mDownloadManager.query(query);
        if (cursor.moveToFirst()) {
            int status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
            switch (status) {
                case DownloadManager.STATUS_PAUSED:  //下载暂停
                    LogUtils.w("下载暂停");
                    break;
                case DownloadManager.STATUS_PENDING://下载延迟
                    LogUtils.w("下载延迟");
                    break;
                case DownloadManager.STATUS_RUNNING://正在下载
                    LogUtils.w("正在下载");
                    break;
                case DownloadManager.STATUS_SUCCESSFUL:// 下载完成
                    LogUtils.w("下载完成");
                    break;
                case DownloadManager.STATUS_FAILED: //下载失败
                    Toast.makeText(getActivity(), "下载失败", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
        cursor.close();
    }

    /**
     * 7.0兼容
     */
    private void installAPK() {
        File apkFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "下载.apk");
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri apkUri = FileProvider.getUriForFile(getActivity(), getActivity().getPackageName() + ".provider", apkFile);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
        }
        getActivity().startActivity(intent);
    }

}
