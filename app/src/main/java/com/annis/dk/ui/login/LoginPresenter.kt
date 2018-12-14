package com.annis.dk.ui.login

import com.annis.dk.base.DKPresenter
import com.annis.dk.base.DkConstast
import com.annis.dk.utils.DkSPUtils

/**
 * @author Lee
 * @date 2018/12/10 12:31
 * @Description
 */
class LoginPresenter(view: LoginView?) : DKPresenter<LoginView>(view) {
    fun getCode(phone: String) {
        httpApi
    }

    fun login(phone: String, psw: String) {
        addSubscribe(
            httpApi!!.getUser(phone, DkSPUtils.getKey())
                .subscribe({ r ->
                    r ?: let {
                        view.errorMsg("网络请求失败")
                    }
                    r.let {
                        DkConstast.saveUserEntity(it)
                    }
                }, { e ->
                    view.errorMsg("网络请求失败")
                })
        )
    }
}