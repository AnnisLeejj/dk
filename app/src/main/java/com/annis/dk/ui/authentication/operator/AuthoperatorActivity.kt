package com.annis.dk.ui.authentication.operator

import android.os.Bundle
import android.os.CountDownTimer
import android.widget.CheckBox
import com.annis.baselib.base.base.TitleBean
import com.annis.baselib.base.mvp.MVPActivty
import com.annis.dk.R
import kotlinx.android.synthetic.main.activity_authoperator.*

class AuthoperatorActivity : MVPActivty<AuthoperatorPresenter>(), AuthoperatorView {
    override fun getPresenter(): AuthoperatorPresenter {
        return AuthoperatorPresenter(this)
    }

    override fun getMyTitle(): TitleBean {
        return TitleBean("手机认证").setBack(true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authoperator)
    }

    override fun initViewAndListener() {
        click()
    }

    var timer: CountDownTimer? = null
    fun click() {
        act_bt_login.setOnClickListener {
            act_et_tel.text.toString()
            act_et_tel_psw.text.toString()
            act_et_code.text.toString()

        }

        renzheng_operator_agree.setOnClickListener {
            setAgree((it as CheckBox).isChecked)
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

    fun setAgree(isChecked: Boolean) {
        act_bt_login.isEnabled = isChecked
        act_bt_login.setBackgroundResource(
            when (isChecked) {
                true -> R.drawable.sp_bt_bg_primary_c
                false -> R.drawable.sp_bt_bg_gray_c
            }
        )
    }

    override fun onDestroy() {
        timer?.cancel()
        super.onDestroy()
    }

}
