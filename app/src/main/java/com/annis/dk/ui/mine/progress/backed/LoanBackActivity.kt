package com.annis.dk.ui.mine.progress.backed

import android.os.Bundle
import com.annis.baselib.base.base.TitleBean
import com.annis.baselib.base.mvp.MVPActivty
import com.annis.dk.R
import kotlinx.android.synthetic.main.activity_loan_back.*

class LoanBackActivity : MVPActivty<LoanBaackPrensenter>(), LoanBaackView {
    override fun success() {
         showToast("申请提交成功")
        finish()
    }

    override fun getPresenter(): LoanBaackPrensenter {
        return LoanBaackPrensenter(this)
    }

    override fun getMyTitle(): TitleBean {
        return TitleBean("贷款进度").setBack(true)
    }

    override fun initViewAndListener() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loan_back)
        act_bt_login.setOnClickListener {
            //提交贷款申请
            presenter.saveLoan()
        }
    }
}
