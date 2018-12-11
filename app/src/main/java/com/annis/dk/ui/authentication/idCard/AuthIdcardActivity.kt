package com.annis.dk.ui.authentication.idCard

import android.os.Bundle
import com.annis.baselib.base.base.BaseActivity
import com.annis.baselib.base.base.TitleBean
import com.annis.dk.R

class AuthIdcardActivity : BaseActivity() {
    override fun getMyTitle(): TitleBean {
        return TitleBean("身份证认证").setBack(true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth_idcard)
    }

    override fun initViewAndListener() {
    }
}
