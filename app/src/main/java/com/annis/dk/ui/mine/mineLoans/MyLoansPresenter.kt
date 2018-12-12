package com.annis.dk.ui.mine.mineLoans

import com.annis.baselib.base.mvp.MvpPresenter

class MyLoansPresenter(view: MyLoansView?) : MvpPresenter<MyLoansView>(view) {
    fun getCode() {
         view.showCode()
    }
}