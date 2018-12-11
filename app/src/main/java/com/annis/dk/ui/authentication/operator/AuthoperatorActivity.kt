package com.annis.dk.ui.authentication.operator

import android.os.Bundle
import com.annis.baselib.base.base.BaseActivity
import com.annis.baselib.base.base.TitleBean
import com.annis.dk.R

class AuthoperatorActivity : BaseActivity() {
    override fun getMyTitle(): TitleBean {
        return TitleBean("手机认证").setBack(true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authoperator)
    }

    override fun initViewAndListener() {

    }
}
