package com.annis.dk.main

import android.os.Bundle
import com.annis.baselib.base.base.TitleBean
import com.annis.baselib.base.mvp.BaseView
import com.annis.baselib.base.mvp.MVPActivty
import com.annis.baselib.base.mvp.MvpPersenter
import com.annis.dk.R

class MainActivity : MVPActivty<MvpPersenter<BaseView>>(), BaseView {
    override fun getMyTitle(): TitleBean? {
        return null
    }

    override fun getPersenter(): MvpPersenter<BaseView> {
        return MvpPersenter(this)
    }

    override fun initViewAndListener() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}