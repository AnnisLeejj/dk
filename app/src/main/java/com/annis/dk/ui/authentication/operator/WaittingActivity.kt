package com.annis.dk.ui.authentication.operator

import android.app.ProgressDialog
import android.os.Bundle
import com.annis.baselib.base.base.BaseActivity
import com.annis.baselib.base.base.TitleBean
import com.annis.dk.R
import java.util.*

class WaittingActivity : BaseActivity() {
    override fun getMyTitle(): TitleBean? {
        return null
    }

    override fun initViewAndListener() {
        showWait()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_waitting)
    }


    fun showWait() {
        var progressDialog = ProgressDialog(this)
        progressDialog.isIndeterminate = false//循环滚动
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        progressDialog.setCancelable(true)//false不能取消显示，true可以取消显示
        progressDialog.show()

        var timer = Timer()
        var timerTask = object : TimerTask() {
            override fun run() {
                showNext()
            }
        }
        timer.schedule(timerTask, 2500)
    }

    fun showNext() {
        startActivity(ReadBackActivity::class.java)
        finish()
    }
}
