package com.annis.baselib.utils.picasso;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.widget.ImageView;
import com.annis.baselib.utils.picasso.transform.CircleTransform;
import com.annis.baselib.utils.picasso.transform.CropSquareTransformation;
import com.squareup.picasso.Picasso;

import java.io.File;


public class PicassoUtil {
    /**
     * 加载小图片(不设置圆角或原型)
     *
     * @param context
     * @param url
     * @param defaultImg
     * @param imageView
     */
    public static void loadSmallImage(Context context, String url, int defaultImg, ImageView imageView) {
        if (url.startsWith("http")) {
            Picasso.get().load(url)
//                    .transform(new ScaleTransformation())
                    .into(imageView);//
        } else if (url.startsWith("/")) {
            File file = new File(url);
            if (file.exists()) {
                Picasso.get().load(new File(url))
//                        .transform(new ScaleTransformation())
                        .into(imageView);
            }
        }
    }

    /**
     * 加载圆形图片
     *
     * @param context
     * @param url
     * @param defaultImg
     * @param imageView
     */
    public static void loadImageCircle(Context context, String url, int defaultImg, ImageView imageView) {
        if (TextUtils.isEmpty(url) || !url.contains("://")) {
            Picasso.get().load(defaultImg).transform(new CircleTransform()).into(imageView);
        } else {
//            Picasso picasso = new Picasso.Builder(context)
//                    .downloader(new OkHttp3Downloader(context))//设置disk缓存
//                    .defaultBitmapConfig(Bitmap.Config.RGB_565) // 设置全局的图片样式
//                    .loggingEnabled(true)       //log
//                    .build();
//            Picasso.setSingletonInstance(picasso); // 设置Picasso单例
            Picasso.get().load(url).placeholder(defaultImg).transform(new CircleTransform()).into(imageView);
        }
    }

    /**
     * 加载圆形图片
     *
     * @param context
     * @param bitmap
     * @param imageView
     */
    public static void loadImageCircle(Context context, File bitmap, ImageView imageView) {
        Picasso.get().load(bitmap).transform(new CircleTransform()).into(imageView);
    }

    /**
     * 加载圆角图片
     *
     * @param context
     * @param url
     * @param defaultImg
     * @param imageView
     */
    public static void loadImageCorner(Context context, String url, int defaultImg, ImageView imageView) {
        if (url.startsWith("http")) {
            Picasso.get().load(url).placeholder(defaultImg).transform(new CropSquareTransformation()).into(imageView);
        } else if (url.startsWith("/")) {
            File file = new File(url);
            if (file.exists()) {
                Picasso.get().load(new File(url)).transform(new CropSquareTransformation()).into(imageView);
            } else {
                Picasso.get().load(defaultImg).transform(new CropSquareTransformation()).into(imageView);
            }
        }
    }

    /**
     * 加载大图 - 放弃 memory cache(不设置圆角或原型)
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void loadBigImage(Context context, String url, ImageView imageView) {
        if (url.startsWith("http")) {
            Picasso.get().load(url)
//                    .transform(new CircleTransform())
                    .into(imageView);//
        } else if (url.startsWith("/")) {
            File file = new File(url);
            if (file.exists()) {
                Picasso.get().load(new File(url))
                        //  .transform(new CircleTransform())
                        .into(imageView);
            }
        }
    }
}
