package com.annis.dk.ui.authentication.bank

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.annis.baselib.base.base.BaseActivity
import com.annis.baselib.base.base.TitleBean
import com.annis.dk.R

class AuthBankActivity : BaseActivity() {
    override fun getMyTitle(): TitleBean {
        return TitleBean("银行卡认证").setBack(true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth_bank)
    }

    override fun initViewAndListener() {
    }
}
