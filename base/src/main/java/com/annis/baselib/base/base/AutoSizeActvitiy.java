package com.annis.baselib.base.base;

import android.app.Activity;
import android.view.View;
import me.jessyan.autosize.AutoSizeConfig;

/**
 * 扩充
 * AutoSize  自适应布局的相关 操作
 */
public abstract class AutoSizeActvitiy extends BaseActExt {
    /**
     * 需要注意的是暂停 AndroidAutoSize 后, AndroidAutoSize 只是停止了对后续还没有启动的 {@link Activity} 进行适配的工作
     * 但对已经启动且已经适配的 {@link Activity} 不会有任何影响
     *
     * @param view {@link View}
     */
    public void stop(View view) {
       // Toast.makeText(getApplicationContext(), "AndroidAutoSize stops working!", Toast.LENGTH_SHORT).show();
        AutoSizeConfig.getInstance().stop(this);
    }

    /**
     * 需要注意的是重新启动 AndroidAutoSize 后, AndroidAutoSize 只是重新开始了对后续还没有启动的 {@link Activity} 进行适配的工作
     * 但对已经启动且在 stop 期间未适配的 {@link Activity} 不会有任何影响
     *
     * @param view {@link View}
     */
    public void restart(View view) {
       // Toast.makeText(getApplicationContext(), "AndroidAutoSize continues to work", Toast.LENGTH_SHORT).show();
        AutoSizeConfig.getInstance().restart();
    }
}
