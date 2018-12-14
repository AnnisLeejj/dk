package com.annis.dk.ui.mine.mineLoans

import com.annis.baselib.base.mvp.BaseView

interface MyLoansView : BaseView {
    /**
     * 显示二维码
     */
    fun showCode(url: String)
}