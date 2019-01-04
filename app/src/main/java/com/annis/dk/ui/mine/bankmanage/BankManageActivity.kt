package com.annis.dk.ui.mine.bankmanage

import android.os.Bundle
import com.annis.baselib.base.base.TitleBean
import  com.annis.baselib.base.mvp.MVPActivity
import com.annis.dk.R
import com.annis.dk.base.DKConstant
import com.annis.dk.bean.BankInfo
import com.annis.dk.ui.authentication.bank.AuthBankActivity
import kotlinx.android.synthetic.main.activity_bank_manage.*

class BankManageActivity : MVPActivity<BankManagePresenter>(), BankManageVIew {
    override fun updateBankCard(it: BankInfo) {
        setBank(it)
    }

    override fun getPresenter(): BankManagePresenter {
        return BankManagePresenter(this)
    }

    override fun getMyTitle(): TitleBean {
        return TitleBean("银行卡管理").setBack(true)
    }

    var bankInfo: BankInfo? = null
    override fun initViewAndListener() {
        bankInfo = DKConstant.getBankCard()
        bankInfo?.let {
            setBank(it)
        }
        click()
        presenter.updateBankCard1()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bank_manage)
    }

    fun setBank(bankInfo: BankInfo) {
        tv_card_name.text = bankInfo.bankcard
        tv_card_account.text = bankInfo.savingscard
    }

    fun click() {
        //更换卡片
        act_bt_change.setOnClickListener {
            startActivity(AuthBankActivity::class.java)
            finish()
        }
    }
}
