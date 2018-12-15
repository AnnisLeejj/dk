package com.annis.dk.ui.authentication.operator

import com.annis.baselib.base.mvp.BaseView
import com.annis.dk.base.DKPresenter

/**
 * @author Lee
 * @date 2018/12/15 16:35
 * @Description
 */
interface AuthoperatorView : BaseView {

}

class AuthoperatorPresenter(view: AuthoperatorView?) : DKPresenter<AuthoperatorView>(view) {
    fun uploadAuth() {
    }
}
