package com.annis.baselib.utils;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileOutputStream;

public class ImageUtil {
//    public static final String CROP_CACHE_FILE_NAME = "icon_my.png";
    public static final int REQUEST_GALLERY = 0xa0;
    public static final int REQUEST_CAMERA = 0xa1;

    public static final int RE_GALLERY = 127;
    public static final int RE_CAMERA = 128;

    private Activity context;

    private ImageUtil(Activity context) {
        this.context = context;
    }

    private static ImageUtil instance;

    public static ImageUtil getCropHelperInstance(Activity context) {
        if (instance == null) {
            instance = new ImageUtil(context);
        }
        return instance;
    }
    public static String getName(){
        return  "image_pile.png";
    }

    private Uri buildUri() {
        Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            String authority = context.getPackageName() + ".fileprovider";
            File file = new File(Environment.getExternalStorageDirectory() + "/" + getName());
            uri = FileProvider.getUriForFile(context, authority, file);
        } else {
            uri = Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/" + getName()));
        }

        return uri;
    }

    public void setHandleResultListener(CropHandler handler, int requestCode,
                                        int resultCode, Intent data) {
        if (handler == null)
            return;
        if (resultCode == Activity.RESULT_CANCELED) {
            handler.onCropCancel();
        } else if (resultCode == Activity.RESULT_OK) {
            Bitmap photo;
            switch (requestCode) {
                case RE_CAMERA:
                    if (data == null || data.getExtras() == null) {
                        handler.onCropFailed("CropHandler's context MUST NOT be null。");
                        return;
                    }
                    photo = data.getExtras().getParcelable("data");
                    try {
                        photo.compress(Bitmap.CompressFormat.JPEG, 30,
                                new FileOutputStream(getCachedCropFile()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    handler.onPhotoCropped(buildUri(), photo, requestCode);
                    break;
                case RE_GALLERY:
                    if (data == null || data.getExtras() == null) {
                        handler.onCropFailed("CropHandler's context MUST NOT be null。");
                        return;
                    }
                    photo = data.getExtras().getParcelable("data");
                    try {
                        photo.compress(Bitmap.CompressFormat.JPEG, 30,
                                new FileOutputStream(getCachedCropFile()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    handler.onPhotoCropped(buildUri(), photo, requestCode);
                    break;
                case REQUEST_CAMERA:
                    Intent intent = buildCropIntent(buildUri());
                    if (context != null) {
                        context.startActivityForResult(intent,
                                RE_CAMERA);
                    } else {
                        handler.onCropFailed("CropHandler's context MUST NOT be null。");
                    }
                    break;
                case REQUEST_GALLERY:
                    if (data == null) {
                        handler.onCropFailed("Data MUST NOT be null。");
                        return;
                    }
                    Intent intent2 = buildCropIntent(data.getData());

                    if (context != null) {
                        context.startActivityForResult(intent2,
                                RE_GALLERY);
                    } else {
                        handler.onCropFailed("CropHandler's context MUST NOT be null。");
                    }
                    break;
            }
        }
    }


    public Intent buildGalleryIntent() {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_PICK);
        galleryIntent.setType("image/*");
        return galleryIntent;
    }

    public Intent buildCameraIntent() {
        Intent cameraIntent = new Intent();
        cameraIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.addCategory("android.intent.category.DEFAULT");
        if (hasSdcard()) {
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, buildUri());
        }
        return cameraIntent;
    }

    private boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    private Intent buildCropIntent(Uri uri) {
        Intent cropIntent = new Intent("com.android.camera.action.CROP");
        cropIntent.setDataAndType(uri, "image/*");
        cropIntent.putExtra("crop", "true");
        cropIntent.putExtra("aspectX", 1);
        cropIntent.putExtra("aspectY", 1);
        cropIntent.putExtra("outputX", 300);
        cropIntent.putExtra("outputY", 300);
        cropIntent.putExtra("return-data", true);
        return cropIntent;
    }

    public interface CropHandler {
        void onPhotoCropped(Uri uri, Bitmap photo, int requestCode);

        void onCropCancel();

        void onCropFailed(String message);
    }

    public File getCachedCropFile() {
        if (buildUri() == null)
            return null;
        return new File(buildUri().getPath());
    }
}
