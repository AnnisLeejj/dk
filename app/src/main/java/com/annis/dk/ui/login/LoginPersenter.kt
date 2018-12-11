package com.annis.dk.ui.login

import com.annis.dk.base.DKPresenter

/**
 * @author Lee
 * @date 2018/12/10 12:31
 * @Description
 */
class LoginPersenter(view: LoginView?) : DKPresenter<LoginView>(view) {
    fun getCode(phone: String) {
        httpApi
    }

    fun login(phone: String, psw: String) {
        httpApi
    }
}