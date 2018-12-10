package com.annis.baselib.base.mvp;

import android.app.ProgressDialog;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import com.annis.baselib.base.base.BaseFragment;

public abstract class MVPFragment<P extends BasePersenter> extends BaseFragment implements BaseView {
    P persenter;

    public abstract P getPersenter();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        persenter = getPersenter();
    }

    @Override
    public void errorMsg(String msg) {
        showToast(msg);
    }

    /**
     * 显示等待对话框
     */
    ProgressDialog progressDialog;
    /**
     * 显示等待对话框,并显示默认 提示内容
     */
    @Override
    public void showWaitting() {
        showWaitting("正在加载...");
    }
    /**
     * 显示等待对话框,
     */
    @Override
    public void showWaitting(String msg) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setIndeterminate(false);//循环滚动
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setCancelable(true);//false不能取消显示，true可以取消显示
            progressDialog.setOnDismissListener(dialog -> persenter.unSubscribe());
        }
        progressDialog.setMessage(msg);
        progressDialog.show();
    }

    /**
     * 隐藏等待对话框
     */
    @Override
    public void dismissWaitting() {
        if (progressDialog != null && progressDialog.isShowing()) progressDialog.dismiss();
    }

    /**
     * 向用户显示告知信息
     */
    @Override
    public void showNotification(String msg) {
        new AlertDialog.Builder(getActivity()).setTitle("请注意").setMessage(msg).setNegativeButton("确定", (dialog, which) ->
                notificationRespond(msg)).setCancelable(false).create().show();
    }

    /**
     * 告知信息 确认
     */
    @Override
    public void notificationRespond(String msg) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        dismissWaitting();
        persenter.detach();
        super.onDestroy();
    }
}
