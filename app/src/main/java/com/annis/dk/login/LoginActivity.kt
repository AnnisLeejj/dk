package com.annis.dk.login

import android.os.Bundle
import com.annis.baselib.base.base.TitleBean
import com.annis.baselib.base.mvp.MVPActivty
import com.annis.dk.R

class LoginActivity : MVPActivty<LoginPersenter>(), LoginView {
    override fun initViewAndListener() {

    }

    override fun getMyTitle(): TitleBean? {
        return null
    }

    override fun getPersenter(): LoginPersenter {
        return LoginPersenter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }
}
