package com.annis.dk.ui.mine.bankmanage

import android.os.Bundle
import com.annis.baselib.base.base.BaseActivity
import com.annis.baselib.base.base.TitleBean
import com.annis.dk.R
import kotlinx.android.synthetic.main.activity_bank_manage.*

class BankManageActivity : BaseActivity() {
    override fun getMyTitle(): TitleBean {
        return TitleBean("银行卡管理").setBack(true)
    }

    override fun initViewAndListener() {
        click()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bank_manage)
    }

    fun click() {
        //更换卡片
        act_bt_change.setOnClickListener {

        }
    }
}
