package com.annis.dk.ui.welcome

import android.os.Bundle
import com.annis.baselib.base.base.TitleBean
import com.annis.baselib.base.mvp.MVPActivty
import com.annis.dk.R
import com.annis.dk.ui.MainActivity
import com.annis.dk.ui.login.LoginActivity
import com.annis.dk.utils.DkSPUtils

class WelcomeActivity : MVPActivty<WelcomePresenter>(), WelcomeView {
    override fun getPersenter(): WelcomePresenter {
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
        persenter.getKey()
        window.decorView.postDelayed({
            startNextActvitiy()
        }, 2500)
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
