package com.annis.dk.base;

import com.annis.baselib.BuildConfig;
import com.annis.baselib.base.http.HttpProvider;
import com.annis.baselib.base.mvp.BaseView;
import com.annis.baselib.base.mvp.MvpPresenter;

/**
 * @author Lee
 * @date 2018/12/11 18:48
 * @Description
 */
public class DKPresenter<V extends BaseView> extends MvpPresenter<V> {
    HttpApi httpApi;

    public DKPresenter(V view) {
        super(view);
    }

    public HttpApi getHttpApi() {
        if (httpApi == null) {
            httpApi = HttpProvider.getRetrofit(BuildConfig.IP + BuildConfig.USER_PORT).create(HttpApi.class);
        }
        return httpApi;
    }
}
