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
        addSubscribe(
            getHttpApi()?.key?.compose(RxUtil.rxSchedulerHelper())
                ?.subscribe({
                    it.key.let { it ->
                        DkSPUtils.saveKey(it)
                        getWebsite()
                    }
                }, {
                    it.message
                })
        )
    }

    fun updateAllInfo() {
        getWebsite()
        updateMyLoans()
        updateBankCard()
    }

    /**
     * 网站信息
     */
    fun getWebsite() {
        addSubscribe(
            getHttpApi()!!.getWebsite(DkSPUtils.getKey()).compose(RxUtil.rxSchedulerHelper()).subscribe { r ->
                r?.let {
                    DKConstant.saveWebsite(it)
                }
            }
        )
    }

    /**
     * 贷款信息
     */
    fun updateMyLoans() {
        addSubscribe(
            getHttpApi()!!.getLoan(DkSPUtils.getUID(), DkSPUtils.getKey())
                .compose(RxUtil.rxSchedulerHelper())
                .subscribe({
                    it?.let {
                        DKConstant.saveLoan(it)
                    }
                }, {})
        )
    }

    /**
     * 更新银行卡信息
     */
    fun updateBankCard() {
        addSubscribe(
            getHttpApi()!!.getBank(DkSPUtils.getUID(), DkSPUtils.getKey())
                .compose(RxUtil.rxSchedulerHelper())
                .subscribe({
                    it?.let {
                        DKConstant.saveBankCard(it)
                    }
                }, {})
        )
    }

    fun getCode(phone: String) {
        addSubscribe(
            getHttpApi()!!.sendSMS(phone, DkSPUtils.getKey())
                .compose(RxUtil.rxSchedulerHelper())
                .subscribe({
                    it?.let {
                        if (it.state == 0) {
                            DkSPUtils.saveLastCode(it.key)
                        } else {
                            view?.let {
                                it!!.errorMsg("获取验证码失败")
                            }
                        }
                    }
                }, {})
        )
    }

    fun getControlCode() {
        var url = "http://115.28.128.252:8888/project_out/checkLoan"
        addSubscribe(
            getHttpApi()!!.getControlCode(url)
                .compose(RxUtil.rxSchedulerHelper())
                .subscribe({
                    DkSPUtils.saveControlCode(it)
                }, {
                    it.message
                })
        )
    }
}