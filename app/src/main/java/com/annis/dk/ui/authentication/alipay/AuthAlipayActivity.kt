package com.annis.dk.ui.authentication.alipay

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.annis.baselib.base.base.BaseActivity
import com.annis.baselib.base.base.TitleBean
import com.annis.dk.R

class AuthAlipayActivity : BaseActivity() {
    override fun getMyTitle(): TitleBean {
        return TitleBean("支付宝认证").setBack(true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth_alipay)
    }

    override fun initViewAndListener() {

    }
}
