package com.annis.baselib.base.base;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import androidx.core.content.FileProvider;
import com.annis.baselib.BuildConfig;
import com.annis.baselib.utils.ext_utils.OpenFileUtil;

import java.io.File;
import java.util.Locale;

/**
 * 处理 BaseActivity 基础视图控制之外的功能
 */
public abstract class BaseActExt extends BaseActivity {
    @SuppressLint("CheckResult")
    public void openFile(File file) {
        String end = file.getName().substring(file.getName().lastIndexOf(".") + 1).toLowerCase(Locale.getDefault());
        String type = OpenFileUtil.FileType.get("." + end);

        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //data是file类型,忘了复制过来
            uri = FileProvider.getUriForFile(this, BuildConfig.AUTHORITIES, file);
        } else {
            uri = Uri.fromFile(file);
        }
        //文件要被读取所以加入读取权限
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(uri, type);
        try {
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
