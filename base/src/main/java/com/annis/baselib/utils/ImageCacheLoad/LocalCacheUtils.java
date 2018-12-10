package com.annis.baselib.utils.ImageCacheLoad;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import com.annis.baselib.utils.utils_haoma.EncryptUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Description: 本地缓存
 * User: chenzheng
 * Date: 2017/2/11 0011
 * Time: 11:15
 */
public class LocalCacheUtils {
//    private static final String CACHE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/com.cmct.pile.app/imageLoader";

    /**
     * 从本地读取图片
     *
     * @param url
     */
    public Bitmap getBitmapFromLocal(String url) {
        String fileName = null;//把图片的url当做文件名,并进行MD5加密
        try {
            fileName = EncryptUtils.encryptMD5ToString(url);
            //MD5Util.GetMD5Code(url);
            File file = new File(FileManager.getImgDir(), fileName);

            if (file.exists()) {
                Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
                return bitmap;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 从网络获取图片后,保存至本地缓存
     *
     * @param url
     * @param bitmap
     */
    public void setBitmapToLocal(String url, Bitmap bitmap) {
        try {
            String fileName = EncryptUtils.encryptMD5ToString(url);
            //MD5Util.GetMD5Code(url);//把图片的url当做文件名,并进行MD5加密
            File file = new File(FileManager.getImgDir(), fileName);

            //通过得到文件的父文件,判断父文件是否存在
            File parentFile = file.getParentFile();
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }

            //把图片保存至本地
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(file));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

class FileManager {
    public static String APP_NAME = "com.cmct.pile.app";

    /**
     * 获取app 外部存储路径
     *
     * @return
     */
    public static String getExternalDir() {
        return Environment.getExternalStorageDirectory().getAbsolutePath() + File.separatorChar + APP_NAME;
    }

    /**
     * 获取app 缓存路径
     *
     * @return
     */
    public static String getCacheDir() {
        String path = Environment.getDownloadCacheDirectory().getAbsolutePath();
        File file = new File(path);
        if (!file.exists()) file.mkdir();
        return path;
    }

    /**
     * 获取app 外部存储路径 - 文件
     *
     * @return
     */
    public static String getFileDir() {
        String path = getExternalDir() + File.separatorChar + "data";
        File file = new File(path);
        if (!file.exists()) file.mkdir();
        return path;
    }

    /**
     * 获取app 外部存储路径 - 图片
     *
     * @return
     */
    public static String getImgDir() {
        String path = getExternalDir() + File.separatorChar + "img";
        File file = new File(path);
        if (!file.exists()) file.mkdir();
        return path;
    }

    /**
     * 根据基桩Id 获取基桩相关文件目录
     *
     * @param pileId 基桩Id
     * @return
     */
    public static String getPileFileDir(String pileId) {
//        String path = getCacheDir() + File.separatorChar + pileId;
        String path = getFileDir() + File.separatorChar + pileId;
        File file = new File(path);
        if (!file.exists()) file.mkdir();
        return path;
    }
}