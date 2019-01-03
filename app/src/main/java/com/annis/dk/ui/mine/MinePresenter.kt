package com.annis.dk.ui.mine

import com.annis.baselib.base.rx.RxUtil
import com.annis.dk.base.DKConstant
import com.annis.dk.base.DKPresenter
import com.annis.dk.utils.DkSPUtils

class MinePresenter(view: MineView?) : DKPresenter<MineView>(view) {
    fun updateAll() {
        updateLoans(0)
        updateBankCard()
    }

    /**
     * 贷款信息
     */
    fun updateLoans(flag: Int) {
        if (flag != 0) {
            view.showWaitting()
        }
        addSubscribe(
            getHttpApi()!!.getLoan(DkSPUtils.getUID(), DkSPUtils.getKey())
                .compose(RxUtil.rxSchedulerHelper())
                .subscribe({
                    it?.let { it ->
                        DKConstant.saveLoan(it)
                        when (flag) {
                            0 -> view.reloadLoan(it)
                            1 -> view.showMyProgress(it)
                            2 -> view.showMyLoan(it)
                        }
                    }
                    view.dismissWaitting()
                }, {
                    view.dismissWaitting()
                })
        )
    }

    fun uploadUserEntity(phone: String) {
        addSubscribe(
            getHttpApi()!!.getUser(phone, DkSPUtils.getKey())
                .compose(RxUtil.rxSchedulerHelper())
                .subscribe({ r ->
                    r?.let {
                        DKConstant.saveUserEntity(it)
                        view.updateSuccess(it)
                    }
                }, {})
        )
    }
}