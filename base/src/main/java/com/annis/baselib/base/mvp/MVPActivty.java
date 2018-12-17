package com.annis.baselib.base.mvp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import com.annis.baselib.base.base.AutoSizeActvitiy;
import com.annis.baselib.utils.LogUtils;
import com.annis.baselib.view.MyProgressDialog;

public abstract class MVPActivty<P extends BasePersenter> extends AutoSizeActvitiy implements BaseView {
    public P presenter;

    public abstract P getPresenter();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = getPresenter();
        LogUtils.w("presenter", presenter == null ? "presenter null" : presenter.toString());
    }

    @Override
    protected void onDestroy() {
        dismissWaitting();
        presenter.detach();
        super.onDestroy();
    }

    @Override
    public void errorMsg(String msg) {
        showToast(msg);
    }

    /**
     * 向用户显示告知信息
     */
    @Override
    public void showNotification(String msg) {
        new AlertDialog.Builder(this).setTitle("请注意").setMessage(msg).setNegativeButton("确定", (dialog, which) ->
                notificationRespond(msg)).setCancelable(false).create().show();
    }

    /**
     * 告知信息 确认
     */
    @Override
    public void notificationRespond(String msg) {

    }

    /**
     * 显示等待对话框
     */
    ProgressDialog progressDialog;
    MyProgressDialog loading;

    /**
     * 显示等待对话框,并显示默认 提示内容
     */
    @Override
    public void showWaitting() {
        showWaitting("正在加载...");
    }

    /**
     * 显示等待对话框
     */
    @Override
    public void showWaitting(String msg) {
        if (loading == null) {
            loading = new MyProgressDialog(this);
            loading.setIndeterminate(false);//循环滚动
            loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            loading.setMessage(msg);
            loading.setCancelable(true);//false不能取消显示，true可以取消显示
            loading.setOnDismissListener(dialog -> presenter.unSubscribe());
        }
        if (loading.isShowing()) return;
        loading.show();

//        if (progressDialog == null) {
//            progressDialog = new ProgressDialog(this);
//            progressDialog.setIndeterminate(false);//循环滚动
//            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//            progressDialog.setMessage(msg);
//            progressDialog.setCancelable(true);//false不能取消显示，true可以取消显示
//            progressDialog.setOnDismissListener(dialog -> presenter.unSubscribe());
//        }
//        if (progressDialog.isShowing()) return;
//        progressDialog.show();
    }

    /**
     * 隐藏等待对话框
     */
    @Override
    public void dismissWaitting() {
//        if (progressDialog != null && progressDialog.isShowing()) progressDialog.dismiss();
        if (loading != null && loading.isShowing()) loading.dismiss();
    }
}