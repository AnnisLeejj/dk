package com.annis.dk.ui

import android.os.Bundle
import com.annis.baselib.base.base.BaseActivity
import com.annis.baselib.base.base.TitleBean
import com.annis.dk.R
import kotlinx.android.synthetic.main.activity_text.*

class TextActivity : BaseActivity() {
    override fun initViewAndListener() {
        intent.getStringExtra("content")?.let {
            tvContainer.text = it
        }
    }

    override fun getMyTitle(): TitleBean {
        var title = "协议"
        intent.getStringExtra("title")?.let {
            title = it
        }
        return TitleBean(title).setBack(true)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text)
    }
}
