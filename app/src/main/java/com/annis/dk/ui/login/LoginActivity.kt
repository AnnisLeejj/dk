package com.annis.dk.ui.login

import android.os.Bundle
import android.os.CountDownTimer
import com.annis.baselib.base.base.TitleBean
import com.annis.baselib.base.mvp.MVPActivty
import com.annis.dk.R
import com.annis.dk.bean.UserEntity
import com.annis.dk.ui.MainActivity
import com.annis.dk.view.NotificationDialog
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : MVPActivty<LoginPresenter>(), LoginView {


    override fun loginSuccess(it: UserEntity) {
        if (it.isillegal == "1") {
            var dialog = NotificationDialog()
            dialog.setDismissListener(object : NotificationDialog.Dismiss {
                override fun finish() {

                }

            })
            dialog.setMessage("您已被加入黑名单，暂时禁止使用该app，请联系工作人员")
            dialog.show(supportFragmentManager, "notify")
        } else {
            startActivity(MainActivity::class.java)
            finish()
        }
    }

    override fun getMyTitle(): TitleBean? {
        return null
    }

    override fun getPresenter(): LoginPresenter {
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
            presenter.login(act_et_tel.text.toString(), act_et_code.text.toString())
        }

        act_bt_getcode.setOnClickListener {
            var phone = act_et_tel.text.toString()
            if (phone.length < 11) {
                errorMsg("手机号不正确")
                return@setOnClickListener
            }
            presenter.getCode(phone)

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
