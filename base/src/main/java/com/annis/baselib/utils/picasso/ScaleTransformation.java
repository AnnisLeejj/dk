package com.annis.baselib.utils.picasso;

import android.graphics.Bitmap;
import com.squareup.picasso.Transformation;

/**
 * 小图加载
 */
public class ScaleTransformation implements Transformation {
    private static final int defaultW = 1000;
    private static int minW = 0;

    public ScaleTransformation() {
    }

    public ScaleTransformation(int minW) {
        this.minW = minW;
    }

    @Override
    public Bitmap transform(Bitmap source) {
        int size = Math.min(source.getWidth(), source.getHeight());
//        //如果小于最小的 直接返回
//        if ((minW == 0 && size < defaultW) || size < minW) {
//            return source;
//        }
//        int ImgH = source.getHeight() * (minW == 0 ? defaultW : minW) / source.getWidth();
//        int x = (source.getWidth() - size) / 2;
//        int y = (source.getHeight() - size) / 2;
//        Bitmap result = Bitmap.createBitmap(source, x, y, (minW == 0 ? defaultW : minW), ImgH);
//        if (result != source) {
        //如果小于最小的 直接返回
        if (size < defaultW) {
            return source;
        }
        int ImgH = source.getHeight() * defaultW / source.getWidth();
        int x = (source.getWidth() - size) / 2;
        int y = (source.getHeight() - size) / 2;
        Bitmap result = Bitmap.createBitmap(source, x, y, defaultW, ImgH);
        if (result != source) {
            source.recycle();
        }
        return result;
    }

    @Override
    public String key() {
        return "square()";
    }
}
