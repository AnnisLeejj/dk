package com.annis.baselib.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.annis.baselib.R;
import com.annis.baselib.widget.CircleIndicator;
import com.bm.library.PhotoView;

import java.util.List;

public class ImagePreviewer<T> extends DialogFragment {
    private View view;

    private ViewPager mPager;
    private CircleIndicator indicator;
    private List<T> imgs;
    private ImagePicker.ImgSetting<T> imgSetting;
    private int index;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Holo_Light_NoActionBar);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null)
            view = inflater.inflate(R.layout.image_previewer, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPager = view.findViewById(R.id.pager);
        mPager.setPageMargin((int) (getResources().getDisplayMetrics().density * 15));
        PagerAdapter adapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return imgs.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                PhotoView view = new PhotoView(getActivity());
                view.enable();
                view.setScaleType(ImageView.ScaleType.FIT_CENTER);
//                view.setImageResource(imgs.get(position));
                imgSetting.setImage(view, imgs.get(position));
                container.addView(view);
                view.setOnClickListener(v -> ImagePreviewer.this.dismiss());
                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        };
        mPager.setAdapter(adapter);
        mPager.setCurrentItem(index);

        indicator = view.findViewById(R.id.indicator);
        indicator.setViewPager(mPager);
    }

    public void setData(List<T> list, int index, ImagePicker.ImgSetting<T> imgSetting) {
        if (list == null) throw new RuntimeException("请传入数据");
        imgs = list;
        this.imgSetting = imgSetting;
        this.index = index;

    }

}
