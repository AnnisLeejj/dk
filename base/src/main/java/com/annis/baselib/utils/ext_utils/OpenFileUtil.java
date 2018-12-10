package com.annis.baselib.utils.ext_utils;


import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.StrictMode;
import androidx.core.content.FileProvider;
import com.annis.baselib.utils.utils_haoma.Utils;

import java.io.File;
import java.util.HashMap;
import java.util.Locale;

public class OpenFileUtil {

    public static  Intent openFile(File file){
        return openFile(file.getAbsolutePath());
    }
    
    public static Intent openFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists())
            return null;
        /* 取得扩展名 */
        String end = file.getName().substring(file.getName().lastIndexOf(".") + 1).toLowerCase(Locale.getDefault());

        // 判断版本大于等于7.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
        /* 依扩展名的类型决定MimeType */
        if (end.equals("m4a") || end.equals("mp3") || end.equals("mid") || end.equals("xmf") || end.equals("ogg") || end.equals("wav")) {
            return getAudioFileIntent(filePath);
        } else if (end.equals("3gp") || end.equals("mp4")) {
            return getVideoFileIntent(filePath);
        } else if (end.equals("jpg") || end.equals("gif") || end.equals("png") || end.equals("jpeg") || end.equals("bmp")) {
            return getImageFileIntent(filePath);
        } else if (end.equals("apk")) {
            return getApkFileIntent(filePath);
        } else if (end.equals("ppt")) {
            return getPptFileIntent(filePath);
        } else if (end.equals("xls") || end.equals("xlsx")) {
            return getExcelFileIntent(filePath);
        } else if (end.equals("doc")) {
            return getWordFileIntent(filePath);
        } else if (end.equals("pdf")) {
            return getPdfFileIntent(filePath);
        } else if (end.equals("chm")) {
            return getChmFileIntent(filePath);
        } else if (end.equals("txt")) {
            return getTextFileIntent(filePath, false);
        } else if (end.equals("zip") || end.equals("rar")) {
            return getZipFileIntent(filePath);
        } else {
            return getAllIntent(filePath);
        }
    }

    private static Intent getZipFileIntent(String param) {
        Intent intent = new Intent();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = FileProvider.getUriForFile(Utils.getContext(), Utils.getContext().getPackageName() + ".fileprovider", new File(param));
        intent.setDataAndType(uri, "application/x-gzip");
        return intent;
    }

    // Android获取一个用于打开APK文件的intent
    public static Intent getAllIntent(String param) {
        Intent intent = new Intent();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = FileProvider.getUriForFile(Utils.getContext(), Utils.getContext().getPackageName() + ".fileprovider", new File(param));
        intent.setDataAndType(uri, "*/*");
        return intent;
    }

    // Android获取一个用于打开APK文件的intent
    public static Intent getApkFileIntent(String param) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = FileProvider.getUriForFile(Utils.getContext(), Utils.getContext().getPackageName() + ".fileprovider", new File(param));
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        return intent;
    }

    // Android获取一个用于打开VIDEO文件的intent
    public static Intent getVideoFileIntent(String param) {
        Intent intent = new Intent("android.intent.action.VIEW");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("oneshot", 0);
        intent.putExtra("configchange", 0);
        Uri uri = FileProvider.getUriForFile(Utils.getContext(), Utils.getContext().getPackageName() + ".fileprovider", new File(param));
        intent.setDataAndType(uri, "video/*");
        return intent;
    }

    // Android获取一个用于打开AUDIO文件的intent
    public static Intent getAudioFileIntent(String param) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("oneshot", 0);
        intent.putExtra("configchange", 0);
        Uri uri = FileProvider.getUriForFile(Utils.getContext(), Utils.getContext().getPackageName() + ".fileprovider", new File(param));
        intent.setDataAndType(uri, "audio/*");
        return intent;
    }

    // Android获取一个用于打开Html文件的intent
    public static Intent getHtmlFileIntent(String param) {
        Uri uri = Uri.parse(param).buildUpon().encodedAuthority("com.android.htmlfileprovider").scheme("content").encodedPath(param).build();
        Intent intent = new Intent("android.intent.action.VIEW");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
        intent.setDataAndType(uri, "text/html");
        return intent;
    }

    // Android获取一个用于打开图片文件的intent
    public static Intent getImageFileIntent(String param) {
        Intent intent = new Intent("android.intent.action.VIEW");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = FileProvider.getUriForFile(Utils.getContext(), Utils.getContext().getPackageName() + ".fileprovider", new File(param));
        intent.setDataAndType(uri, "image/*");
        return intent;
    }

    // Android获取一个用于打开PPT文件的intent
    public static Intent getPptFileIntent(String param) {
        Intent intent = new Intent("android.intent.action.VIEW");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = FileProvider.getUriForFile(Utils.getContext(), Utils.getContext().getPackageName() + ".fileprovider", new File(param));
        intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
        return intent;
    }

    // Android获取一个用于打开Excel文件的intent
    public static Intent getExcelFileIntent(String param) {
        Intent intent = new Intent("android.intent.action.VIEW");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = FileProvider.getUriForFile(Utils.getContext(), Utils.getContext().getPackageName() + ".fileprovider", new File(param));
        intent.setDataAndType(uri, "application/vnd.ms-excel");
        return intent;
    }

    // Android获取一个用于打开Word文件的intent
    public static Intent getWordFileIntent(String param) {
        Intent intent = new Intent("android.intent.action.VIEW");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = FileProvider.getUriForFile(Utils.getContext(), Utils.getContext().getPackageName() + ".fileprovider", new File(param));
        intent.setDataAndType(uri, "application/msword");
        return intent;
    }

    // Android获取一个用于打开CHM文件的intent
    public static Intent getChmFileIntent(String param) {
        Intent intent = new Intent("android.intent.action.VIEW");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = FileProvider.getUriForFile(Utils.getContext(), Utils.getContext().getPackageName() + ".fileprovider", new File(param));
        intent.setDataAndType(uri, "application/x-chm");
        return intent;
    }

    // Android获取一个用于打开文本文件的intent
    public static Intent getTextFileIntent(String param, boolean paramBoolean) {
        Intent intent = new Intent("android.intent.action.VIEW");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


        if (paramBoolean) {
            Uri uri1 = Uri.parse(param);
            intent.setDataAndType(uri1, "text/plain");
        } else {
            Uri uri2 = FileProvider.getUriForFile(Utils.getContext(), Utils.getContext().getPackageName() + ".fileprovider", new File(param));
            intent.setDataAndType(uri2, "text/plain");
        }

        if (paramBoolean) {
            Uri uri1 = Uri.parse(param);
            intent.setDataAndType(uri1, "text/plain");
        } else {
            Uri uri = Uri.fromFile(new File("/mnt/sdcard/hello.txt"));
            intent.setDataAndType(uri, "text/plain");
        }

        return intent;
    }

    // Android获取一个用于打开PDF文件的intent
    public static Intent getPdfFileIntent(String param) {
        Intent intent = new Intent("android.intent.action.VIEW");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = FileProvider.getUriForFile(Utils.getContext(), Utils.getContext().getPackageName() + ".fileprovider", new File(param));
        intent.setDataAndType(uri, "application/pdf");
        return intent;
    }

    public final static HashMap<String, String> FileType = new HashMap<>();
    static {
        FileType.put(".3gp", "video/3gpp");
        FileType.put(".apk", "application/vnd.Android.package-archive");
        FileType.put(".asf", "video/x-ms-asf");
        FileType.put(".avi", "video/x-msvideo");
        FileType.put(".bin", "application/octet-stream");
        FileType.put(".bmp", "image/bmp");
        FileType.put(".c", "text/plain");
        FileType.put(".class", "application/octet-stream");
        FileType.put(".conf", "text/plain");
        FileType.put(".cpp", "text/plain");
        FileType.put(".doc", "application/msword");
        FileType.put(".docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        FileType.put(".xls", "application/vnd.ms-excel");
        FileType.put(".xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        FileType.put(".exe", "application/octet-stream");
        FileType.put(".gif", "image/gif");
        FileType.put(".gtar", "application/x-gtar");
        FileType.put(".gz", "application/x-gzip");
        FileType.put(".h", "text/plain");
        FileType.put(".htm", "text/html");
        FileType.put(".html", "text/html");
        FileType.put(".jar", "application/java-archive");
        FileType.put(".java", "text/plain");
        FileType.put(".jpeg", "image/jpeg");
        FileType.put(".jpg", "image/jpeg");
        FileType.put(".js", "application/x-javascript");
        FileType.put(".log", "text/plain");
        FileType.put(".m3u", "audio/x-mpegurl");
        FileType.put(".m4a", "audio/mp4a-latm");
        FileType.put(".m4b", "audio/mp4a-latm");
        FileType.put(".m4p", "audio/mp4a-latm");
        FileType.put(".m4u", "video/vnd.mpegurl");
        FileType.put(".m4v", "video/x-m4v");
        FileType.put(".mov", "video/quicktime");
        FileType.put(".mp2", "audio/x-mpeg");
        FileType.put(".mp3", "audio/x-mpeg");
        FileType.put(".mp4", "video/mp4");
        FileType.put(".mpc", "application/vnd.mpohun.certificate");
        FileType.put(".mpe", "video/mpeg");
        FileType.put(".mpeg", "video/mpeg");
        FileType.put(".mpg", "video/mpeg");
        FileType.put(".mpg4", "video/mp4");
        FileType.put(".mpga", "audio/mpeg");
        FileType.put(".msg", "application/vnd.ms-outlook");
        FileType.put(".ogg", "audio/ogg");
        FileType.put(".pdf", "application/pdf");
        FileType.put(".png", "image/png");
        FileType.put(".pps", "application/vnd.ms-powerpoint");
        FileType.put(".ppt", "application/vnd.ms-powerpoint");
        FileType.put(".pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation");
        FileType.put(".prop", "text/plain");
        FileType.put(".rc", "text/plain");
        FileType.put(".rmvb", "audio/x-pn-realaudio");
        FileType.put(".rtf", "application/rtf");
        FileType.put(".sh", "text/plain");
        FileType.put(".tar", "application/x-tar");
        FileType.put(".tgz", "application/x-compressed");
        FileType.put(".txt", "text/plain");
        FileType.put(".wav", "audio/x-wav");
        FileType.put(".wma", "audio/x-ms-wma");
        FileType.put(".wmv", "audio/x-ms-wmv");
        FileType.put(".wps", "application/vnd.ms-works");
        FileType.put(".xml", "text/plain");
        FileType.put(".z", "application/x-compress");
        FileType.put(".zip", "application/x-zip-compressed");
        FileType.put(".rar", "application/x-zip-compressed");
        FileType.put("", "*/*");
    }

    private final String[][] MIME_MapTable = {
            //{后缀名，MIME类型}
            {".3gp", "video/3gpp"},
            {".apk", "application/vnd.Android.package-archive"},
            {".asf", "video/x-ms-asf"},
            {".avi", "video/x-msvideo"},
            {".bin", "application/octet-stream"},
            {".bmp", "image/bmp"},
            {".c", "text/plain"},
            {".class", "application/octet-stream"},
            {".conf", "text/plain"},
            {".cpp", "text/plain"},
            {".doc", "application/msword"},
            {".docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"},
            {".xls", "application/vnd.ms-excel"},
            {".xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"},
            {".exe", "application/octet-stream"},
            {".gif", "image/gif"},
            {".gtar", "application/x-gtar"},
            {".gz", "application/x-gzip"},
            {".h", "text/plain"},
            {".htm", "text/html"},
            {".html", "text/html"},
            {".jar", "application/java-archive"},
            {".java", "text/plain"},
            {".jpeg", "image/jpeg"},
            {".jpg", "image/jpeg"},
            {".js", "application/x-javascript"},
            {".log", "text/plain"},
            {".m3u", "audio/x-mpegurl"},
            {".m4a", "audio/mp4a-latm"},
            {".m4b", "audio/mp4a-latm"},
            {".m4p", "audio/mp4a-latm"},
            {".m4u", "video/vnd.mpegurl"},
            {".m4v", "video/x-m4v"},
            {".mov", "video/quicktime"},
            {".mp2", "audio/x-mpeg"},
            {".mp3", "audio/x-mpeg"},
            {".mp4", "video/mp4"},
            {".mpc", "application/vnd.mpohun.certificate"},
            {".mpe", "video/mpeg"},
            {".mpeg", "video/mpeg"},
            {".mpg", "video/mpeg"},
            {".mpg4", "video/mp4"},
            {".mpga", "audio/mpeg"},
            {".msg", "application/vnd.ms-outlook"},
            {".ogg", "audio/ogg"},
            {".pdf", "application/pdf"},
            {".png", "image/png"},
            {".pps", "application/vnd.ms-powerpoint"},
            {".ppt", "application/vnd.ms-powerpoint"},
            {".pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation"},
            {".prop", "text/plain"},
            {".rc", "text/plain"},
            {".rmvb", "audio/x-pn-realaudio"},
            {".rtf", "application/rtf"},
            {".sh", "text/plain"},
            {".tar", "application/x-tar"},
            {".tgz", "application/x-compressed"},
            {".txt", "text/plain"},
            {".wav", "audio/x-wav"},
            {".wma", "audio/x-ms-wma"},
            {".wmv", "audio/x-ms-wmv"},
            {".wps", "application/vnd.ms-works"},
            {".xml", "text/plain"},
            {".z", "application/x-compress"},
            {".zip", "application/x-zip-compressed"},
            {"", "*/*"}
    };
}