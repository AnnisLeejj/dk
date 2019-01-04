package com.annis.dk.ui.mine.progress

import android.os.Bundle
import com.annis.baselib.base.base.TitleBean
import com.annis.baselib.base.mvp.BaseView
import  com.annis.baselib.base.mvp.MVPActivity
import com.annis.dk.R
import com.annis.dk.base.DKPresenter

class PaidActivity : MVPActivity<DKPresenter<BaseView>>() {
    override fun getMyTitle(): TitleBean {
        return TitleBean("贷款进度").setBack(true)
    }

    override fun getPresenter(): DKPresenter<BaseView> {
        return DKPresenter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paid)
    }

    override fun initViewAndListener() {

    }
}
