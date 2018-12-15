package com.annis.dk.ui.authentication.bank

import android.os.Bundle
import com.annis.baselib.base.base.TitleBean
import com.annis.baselib.base.mvp.MVPActivty
import com.annis.dk.R
import kotlinx.android.synthetic.main.activity_auth_bank.*

class AuthBankActivity : MVPActivty<AuthBankPresenter>(), AuthBankView {
    override fun uploadSuccess() {
        showToast("提交成功")
        finish()
    }

    override fun getPresenter(): AuthBankPresenter {
        return AuthBankPresenter(this)
    }

    override fun getMyTitle(): TitleBean {
        return TitleBean("银行卡认证").setBack(true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth_bank)
    }

    override fun initViewAndListener() {


        act_bt_outlogin.setOnClickListener {
            var name = act_auth_bank_etName.text.toString()
            var id = act_auth_bank_etID.text.toString()
            var card = act_auth_bank_etCard.text.toString()
            var mobel = act_auth_bank_etMobel.text.toString()

            presenter.upload(name, id, card, mobel)

        }
    }
}
