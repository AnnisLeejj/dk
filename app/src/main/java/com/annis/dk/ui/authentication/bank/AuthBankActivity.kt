package com.annis.dk.ui.authentication.bank

import android.os.Bundle
import com.annis.baselib.base.base.TitleBean
import  com.annis.baselib.base.mvp.MVPActivity
import com.annis.dk.R
import com.annis.dk.bean.BankInfo
import kotlinx.android.synthetic.main.activity_auth_bank.*

class AuthBankActivity : MVPActivity<AuthBankPresenter>(), AuthBankView {
    override fun updateBankCard(it: BankInfo) {
        setBank(it)
    }

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
        presenter.updateBankCard1()

        act_bt_outlogin.setOnClickListener {
            var name = act_auth_bank_etName.text.toString()
            var id = act_auth_bank_etID.text.toString()
            var card = act_auth_bank_etCard.text.toString()
            var mobel = act_auth_bank_etMobel.text.toString()
            if (name.isEmpty()) {
                showToast("请输入姓名")
                return@setOnClickListener
            }
            if (id.isEmpty()) {
                showToast("请输入身份证")
                return@setOnClickListener
            }
            if (card.isEmpty()) {
                showToast("请输入储存卡")
                return@setOnClickListener
            }
            if (mobel.isEmpty()) {
                showToast("请输入手机号")
                return@setOnClickListener
            }
            presenter.upload(name, id, card, mobel)
        }
    }

    private fun setBank(it: BankInfo) {
        it?.let {
            act_auth_bank_etName.setText(it.bankcard)
            act_auth_bank_etID.setText(it.idnumber)
            act_auth_bank_etCard.setText(it.savingscard)
            act_auth_bank_etMobel.setText(it.phone)
        }
    }
}
