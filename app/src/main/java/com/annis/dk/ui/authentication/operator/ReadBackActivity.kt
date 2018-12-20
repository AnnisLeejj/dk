package com.annis.dk.ui.authentication.operator

import android.os.Bundle
import android.os.CountDownTimer
import com.annis.baselib.base.base.BaseActivity
import com.annis.baselib.base.base.TitleBean
import com.annis.dk.R
import kotlinx.android.synthetic.main.activity_read_back.*

class ReadBackActivity : BaseActivity() {
    override fun getMyTitle(): TitleBean? {
        return TitleBean("运营商认证").setBack(true)
    }

    override fun initViewAndListener() {
        //showWait()
        act_read_back_sure.setOnClickListener {
//            timer?.onFinish()
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read_back)
    }


    var timer: CountDownTimer? = null
    fun showWait() {
        //开始倒计时
        timer = object : CountDownTimer(4000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                //act_readback_time.text = "${millisUntilFinished / 1000}s"
            }

            override fun onFinish() {
                finish()
            }
        }.start()

    }
}
