package com.annis.dk.ui.mine.mineLoans

import android.os.Bundle
import com.annis.baselib.base.base.TitleBean
import com.annis.baselib.base.mvp.MVPActivty
import com.annis.dk.R
import com.annis.dk.view.CodeDialog
import kotlinx.android.synthetic.main.activity_my_loans.*

class MyLoansActivity : MVPActivty<MyLoansPresenter>(), MyLoansView {

    override fun getPersenter(): MyLoansPresenter {
        return MyLoansPresenter(this)
    }

    override fun getMyTitle(): TitleBean {
        return TitleBean("我的贷款").setBack(true)
    }

    override fun initViewAndListener() {
        success_edu_tv.text = intent.getStringExtra("edu")//额度
        act_success_remark.text = intent.getStringExtra("remark")//会员服务费提示
        tvWeixin.text = intent.getStringExtra("weixin")//微信号
        tvPhone.text = intent.getStringExtra("phone")//手机号
        click()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_loans)
    }

    fun click() {
        act_bt_call.setOnClickListener {
            //打电话

        }
        act_bt_copy.setOnClickListener {
            //复制微信

        }
        act_bt_pay.setOnClickListener {
            //支付
            persenter.getCode()
        }
    }

    /**
     * 显示二维码
     */
    override fun showCode() {
        var codeDialog = CodeDialog(this)

        codeDialog.show()
    }

}
