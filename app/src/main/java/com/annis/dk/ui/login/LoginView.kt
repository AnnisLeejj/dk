package com.annis.dk.ui.login

import com.annis.baselib.base.mvp.BaseView
import com.annis.dk.bean.UserEntity

/**
 * @author Lee
 * @date 2018/12/10 12:34
 * @Description
 */
interface LoginView : BaseView {
    fun loginSuccess(it: UserEntity)
}