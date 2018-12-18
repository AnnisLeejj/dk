package com.annis.dk.ui.login

import com.annis.baselib.base.rx.RxUtil
import com.annis.dk.base.DKConstant
import com.annis.dk.base.DKPresenter
import com.annis.dk.utils.DkSPUtils

/**
 * @author Lee
 * @date 2018/12/10 12:31
 * @Description
 */
class LoginPresenter(view: LoginView?) : DKPresenter<LoginView>(view) {
    fun login(phone: String, code: String) {
        if (phone.isEmpty()) {
            view.errorMsg("请输入手机号")
            return
        }
        if (code.isEmpty()) {
            view.errorMsg("请输入验证码")
            return
        }
        view.showWaitting()
        var mCode = DkSPUtils.getLastCode()
        if (code != mCode) {
            view.showWaitting()
            view.errorMsg("验证码不正确")
            return
        }
        addSubscribe(
            getHttpApi()!!.getUser(phone, DkSPUtils.getKey())
                .compose(RxUtil.rxSchedulerHelper())
                .subscribe({ r ->
                    r?.let {
                        DKConstant.saveUserEntity(it)
                        view.loginSuccess()
                    }
                    r ?: let {
                        view.errorMsg("登录失败")
                    }
                    view.dismissWaitting()
                }, { e ->
                    view.dismissWaitting()
                    view.errorMsg("网络请求失败")
                })
        )
    }
}