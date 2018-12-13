package com.annis.baselib.base.base;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AppCompatActivity;
import com.annis.baselib.R;
import com.annis.baselib.utils.utils_haoma.KeyboardUtils;
import com.annis.baselib.utils.utils_haoma.ToastUtils;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.List;

import static android.app.ActivityManager.RECENT_WITH_EXCLUDED;

/**
 * 基础Activity 只控制视图
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected static final String OBJECT = "object";
    protected static final String SubObj = "object2";
    private static final String LIST = "list";
    /**
     * 按键监听事件间隔
     */
    public static final long PRESS_INTERVAL = 1000;


    /**
     * 获取Title
     *
     * @return
     */
    protected abstract TitleBean getMyTitle();

    /**
     * 初始化视图
     */
    public abstract void initViewAndListener();

    View view;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        view = getLayoutInflater().inflate(R.layout.base_act, null, true);
        getLayoutInflater().inflate(layoutResID, view.findViewById(R.id.base_content_container), true);
        setContentView(view);
        setMyTitle(view);
        initViewAndListener();
    }

    public void showToast(String msg) {
        ToastUtils.showShortToast(msg);
    }

    public void startActivity(Class clazz) {
        startActivity(new Intent(this, clazz));
    }

    public <T extends Parcelable> void nextAcitvity(Class clazz, T obj) {
        Intent intent = new Intent(this, clazz);
        intent.putExtra(OBJECT, obj);
        startActivity(intent);
    }

    public void startActivity(Class clazz, Serializable list) {
        Intent intent = new Intent(this, clazz);
        intent.putExtra(LIST, list);
        startActivity(intent);
    }

    public <T extends Parcelable> T getObject() {
        return (T) getIntent().getParcelableExtra(OBJECT);
    }

    public <T extends Parcelable> List<T> getIntentList() {
        return (List<T>) getIntent().getSerializableExtra(LIST);
    }

    public void startActivityAndFinish(Class clazz) {
        startActivity(new Intent(this, clazz));
        finish();
    }

    /**
     * 设置标题
     * 如果有返回标题靠左显示,否则居中, 右边如果有view 传入View对象
     *
     * @param view
     */
    private void setMyTitle(View view) {
        /**
         * 软引用
         */
        WeakReference<TitleBean> title = new WeakReference<>(getMyTitle());
        if (title.get() == null) {
            view.findViewById(R.id.base_title_container).setVisibility(View.GONE);
        } else {
            if (title.get().getBackColor() == 0) {
                view.findViewById(R.id.base_title_container).setBackgroundColor(getDarkColorPrimary());
            } else {
                view.findViewById(R.id.base_title_container).setBackgroundColor(title.get().getBackColor());
            }
            if (title.get().getTitleColor() != 0) {
                ((TextView) view.findViewById(R.id.middle_title)).setTextColor(title.get().getTitleColor());
                ((TextView) view.findViewById(R.id.left_title)).setTextColor(title.get().getTitleColor());
            }
            if (!title.get().isBack()) {
                view.findViewById(R.id.toolbar_back_container).setVisibility(View.GONE);
            } else {
                view.findViewById(R.id.toolbar_back_container).setOnClickListener(v -> finish());
            }
            if (!TextUtils.isEmpty(title.get().getLeftTitle())) {
                ((TextView) view.findViewById(R.id.left_title)).setText(title.get().getLeftTitle());
            }
            if (!TextUtils.isEmpty(title.get().getTitle())) {
                TextView titleTV = view.findViewById(R.id.middle_title);
                String titleStr = title.get().getTitle();
                if (titleStr.length() > 25) {
                    titleStr = titleStr.substring(0, 22) + "...";
                }
                titleTV.setText(titleStr);
            }
            if (title.get().getRightView() != null) {
                ((ViewGroup) view.findViewById(R.id.right_view)).addView(title.get().getRightView());
            }
            if (title.get().getRightViewId() != 0) {
                getLayoutInflater().inflate(title.get().getRightViewId(), view.findViewById(R.id.right_view), true);
            }
        }
        title.clear();
    }

    @SuppressLint("ResourceType")
    public void setRightView(@IdRes int layout) {
        getLayoutInflater().inflate(layout, view.findViewById(R.id.right_view), true);
    }

    /**
     * 获取主题颜色
     *
     * @return
     */
    public int getDarkColorPrimary() {
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValue, true);
        return typedValue.data;
    }

    /**
     * 全局 返回键控制
     *
     * @param keyCode
     * @param event
     * @return
     */
    long last_press;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            ActivityManager.RunningTaskInfo taskInfo = getRunningTask(this);
            if (taskInfo.baseActivity.getClassName().equals(taskInfo.topActivity.getClassName())) {
                if ((System.currentTimeMillis() - last_press) > PRESS_INTERVAL) {
                    last_press = System.currentTimeMillis();
                    showToast("再按一次退出");
                    return false;
                }
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 获取Activity堆栈信息
     *
     * @param context
     * @return
     */
    public static ActivityManager.RunningTaskInfo getRunningTask(Context context) {
        if (context != null) {
            ActivityManager am = (ActivityManager) context
                    .getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> rTasks = am.getRunningTasks(1);//
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                am.getAppTasks();
                am.getAppTaskThumbnailSize();
            }
            am.getRecentTasks(10, RECENT_WITH_EXCLUDED);
            return rTasks.get(0);
        }
        return null;
    }

    @Override
    protected void onDestroy() {
        KeyboardUtils.hideSoftInput(this);
        super.onDestroy();
    }
}