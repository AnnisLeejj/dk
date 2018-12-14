package com.annis.dk.ui.login

import android.os.Bundle
import android.os.CountDownTimer
import com.annis.baselib.base.base.TitleBean
import com.annis.baselib.base.mvp.MVPActivty
import com.annis.dk.R
import com.annis.dk.ui.MainActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : MVPActivty<LoginPresenter>(), LoginView {

    override fun getMyTitle(): TitleBean? {
        return null
    }

    override fun getPersenter(): LoginPresenter {
        return LoginPresenter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    override fun initViewAndListener() {
        onClick()
    }

    var timer: CountDownTimer? = null
    fun onClick() {
        act_bt_login.setOnClickListener {
            var tel = act_et_tel.text.toString()
            var code = act_et_code.text.toString()
            persenter.login(tel, code);

            startActivity(MainActivity::class.java)
            finish()
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
                    act_bt_getcode.text = "发送验证码"

                }
            }.start()
        }
    }

    override fun onDestroy() {
        timer?.cancel()
        super.onDestroy()
    }
}
