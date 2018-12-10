package com.annis.baselib.base.mvp;

public interface BaseView {
    /**
     * 显示错误信息
     *
     * @param msg
     */
    void errorMsg(String msg);

    /**
     * 向用户显示告知信息
     *
     * @param msg
     */
    void showNotification(String msg);

    /**
     * 告知信息 确认
     */
    void notificationRespond(String msg);

    /**
     * 显示等待对话框
     */
    void showWaitting();

    /**
     * 显示等待对话框
     */
    void showWaitting(String msg);

    /**
     * 消除等待对话框
     */
    void dismissWaitting();
}

