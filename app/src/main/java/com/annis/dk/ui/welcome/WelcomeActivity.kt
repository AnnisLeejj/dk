package com.annis.dk.ui.welcome

import android.os.Bundle
import com.annis.baselib.base.base.TitleBean
import com.annis.baselib.base.mvp.MVPActivty
import com.annis.dk.R
import com.annis.dk.ui.MainActivity
import com.annis.dk.ui.login.LoginActivity
import com.annis.dk.utils.DkSPUtils

class WelcomeActivity : MVPActivty<WelcomePresenter>(), WelcomeView {
    override fun getPresenter(): WelcomePresenter {
        return WelcomePresenter(this)
    }

    override fun getMyTitle(): TitleBean? {
        return null
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
    }

    override fun initViewAndListener() {
        presenter.getKey()
        presenter.getControlCode()
        var time = 2500L
//        when (BuildConfig.DEBUG) {
//            true -> 500L
//            false -> 2500L
//        }
        window.decorView.postDelayed(
            {
                startNextActvitiy()
            }, time
        )
    }

    fun startNextActvitiy() {
        DkSPUtils.getLogin().let {
            if (it) {
                startActivity(MainActivity::class.java)
            } else {
                startActivity(LoginActivity::class.java)
            }
        }
        finish()
    }
}
