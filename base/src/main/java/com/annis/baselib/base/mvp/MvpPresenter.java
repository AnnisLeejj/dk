package com.annis.baselib.base.mvp;

import android.app.Activity;
import androidx.fragment.app.Fragment;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

import java.lang.ref.WeakReference;

public class MvpPresenter<V extends BaseView> implements BasePersenter<V>, IDisposable {
    protected Activity mContext;

    //    private V view;
    //双层保证 不会出现内存泄漏
    //1、软引用  在内存不足时会回收   如果Activity意外终止 使用该方式能保证
    private WeakReference<V> mRefView;

    //2、通过手动的方式直接释放  activity销毁时直接回收
    public MvpPresenter(V view) {
        attach(view);
    }

    public V getView() {
        return mRefView.get();
    }

    //进行和View层的绑定  activity
    public void attach(V view) {
        if (view instanceof Activity) {
            mContext = (Activity) view;
        } else if (view instanceof Fragment) {
            mContext = ((Fragment) view).getActivity();
        }
        mRefView = new WeakReference<>(view);
        mRefView.get();
    }

    //和View层解绑
    public void detach() {
        mRefView.clear();
        unSubscribe();
    }

    @Override
    public void unSubscribe() {
        mCompositeDisposable.clear();
    }

    @Override
    public void addSubscribe(Disposable subscription) {
        mCompositeDisposable.add(subscription);
    }
}