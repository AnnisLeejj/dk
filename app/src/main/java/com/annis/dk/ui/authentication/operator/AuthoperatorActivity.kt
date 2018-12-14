package com.annis.dk.ui.authentication.operator

import android.os.Bundle
import android.os.CountDownTimer
import android.widget.CheckBox
import com.annis.baselib.base.base.BaseActivity
import com.annis.baselib.base.base.TitleBean
import com.annis.dk.R
import kotlinx.android.synthetic.main.activity_authoperator.*

class AuthoperatorActivity : BaseActivity() {
    override fun getMyTitle(): TitleBean {
        return TitleBean("手机认证").setBack(true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authoperator)
    }

    override fun initViewAndListener() {
        act_bt_login.isEnabled = false
        click()
    }

    var timer: CountDownTimer? = null
    fun click() {

        renzheng_operator_agree.setOnClickListener {
            (it as CheckBox).isChecked.let { it ->
                act_bt_login.isEnabled = it
                act_bt_login.setBackgroundResource(
                    when (it) {
                        true -> R.drawable.sp_bt_bg_primary_c
                        false -> R.drawable.sp_bt_bg_gray_c
                    }
                )
            }

        }

        act_bt_getcode.setOnClickListener {
            //开始倒计时
            timer = object : CountDownTimer(60000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    act_bt_getcode.isEnabled = false
                    act_bt_getcode.setBackgroundResource(R.drawable.sp_bt_wait_code)
                    act_bt_getcode.setTextColor(resources.getColor(R.color.text_color_gray))
                    act_bt_getcode.text = "${millisUntilFinished / 1000}s后重发"
                }

                override fun onFinish() {
                    act_bt_getcode.isEnabled = true
                    act_bt_getcode.setBackgroundResource(R.drawable.sp_bt_get_code)
                    act_bt_getcode.setTextColor(resources.getColor(R.color.colorPrimary))
                    act_bt_getcode.text = "获取验证码"

                }
            }.start()
        }
    }

    override fun onDestroy() {
        timer?.cancel()
        super.onDestroy()
    }

}
