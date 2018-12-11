package com.annis.dk.ui.mine.progress

import android.os.Bundle
import com.annis.baselib.base.base.BaseActivity
import com.annis.baselib.base.base.TitleBean
import com.annis.dk.R
import kotlinx.android.synthetic.main.activity_failed.*

class FailedActivity : BaseActivity() {
    override fun getMyTitle(): TitleBean {
        return TitleBean("贷款进度").setBack(true)
    }

    override fun initViewAndListener() {
        act_failed_reason.text = intent.getStringExtra("reason")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_failed)
    }
}
