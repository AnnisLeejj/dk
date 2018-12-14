package com.annis.dk.ui.mine.mineLoans

import com.annis.baselib.base.mvp.MvpPresenter
import com.annis.dk.ui.mine.MineFragment

class MyLoansPresenter(view: MyLoansView?) : MvpPresenter<MyLoansView>(view) {
    fun getCode() {
        view.showCode(MineFragment.URL)
    }
}