package com.annis.dk.ui.mine.mineLoans

import com.annis.baselib.BuildConfig
import com.annis.baselib.base.mvp.MvpPresenter
import com.annis.dk.base.DKConstant

class MyLoansPresenter(view: MyLoansView?) : MvpPresenter<MyLoansView>(view) {
    fun getCode() {
        var codePath = DKConstant.getWebsite().receiptAddress
        codePath?.let {
            view.showCode(BuildConfig.IP + codePath)
        }
        codePath ?: let {
            view.errorMsg("加载二维码失败,请联系客服")
        }
    }
}