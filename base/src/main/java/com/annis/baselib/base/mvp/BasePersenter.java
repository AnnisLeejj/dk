package com.annis.baselib.base.mvp;

public interface BasePersenter<V extends BaseView> {
    //进行和View层的绑定  activity
    void attach(V view);

    //和View层解绑
    void detach();

    void unSubscribe();
}
