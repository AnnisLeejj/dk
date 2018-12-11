package com.annis.dk.ui.mine.progress

import android.os.Bundle
import com.annis.baselib.base.base.BaseActivity
import com.annis.baselib.base.base.TitleBean
import com.annis.dk.R
import kotlinx.android.synthetic.main.activity_success.*

class SuccessActivity : BaseActivity() {
    override fun getMyTitle(): TitleBean {
        return TitleBean("贷款进度").setBack(true)
    }

    override fun initViewAndListener() {
        success_edu_tv.text = intent.getStringExtra("edu")//额度
        fuwufei.text = intent.getStringExtra("fuwufei")//服务费
        act_success_remark.text = intent.getStringExtra("remark")//会员服务费提示
        tvWeixin.text = intent.getStringExtra("weixin")//微信号
        tvPhone.text = intent.getStringExtra("phone")//手机号
        click()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success)
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

        }
    }

}
