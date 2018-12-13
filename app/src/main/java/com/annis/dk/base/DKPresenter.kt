package com.annis.dk.base

import com.annis.baselib.BuildConfig
import com.annis.baselib.base.http.HttpProvider
import com.annis.baselib.base.mvp.BaseView
import com.annis.baselib.base.mvp.MvpPresenter
import com.annis.baselib.base.rx.RxUtil
import com.annis.dk.utils.DkSPUtils

/**
 * @author Lee
 * @date 2018/12/11 18:48
 * @Description
 */
open class DKPresenter<V : BaseView?>(view: V?) : MvpPresenter<V>(view) {
    internal var httpApi: HttpApi? = null

    fun getHttpApi(): HttpApi? {
        if (httpApi == null) {
            httpApi = HttpProvider.getRetrofit(BuildConfig.IP).create(HttpApi::class.java)
        }
        return httpApi
    }

    fun getKey() {
        addSubscribe(getHttpApi()?.key?.compose(RxUtil.rxSchedulerHelper())?.subscribe {
            it.key.let { it ->
                DkSPUtils.saveKey(it)
            }
        })
    }
}
