package com.annis.dk.ui.mine

import com.annis.baselib.base.rx.RxUtil
import com.annis.dk.base.DKConstant
import com.annis.dk.base.DKPresenter
import com.annis.dk.utils.DkSPUtils

class MinePresenter(view: MineView?) : DKPresenter<MineView>(view) {
    fun updateAll() {
        updateLoans()
        updateBankCard()


    }

    /**
     * 贷款信息
     */
    fun updateLoans() {
        addSubscribe(
            getHttpApi()!!.getLoan(DkSPUtils.getUID(), DkSPUtils.getKey())
                .compose(RxUtil.rxSchedulerHelper())
                .subscribe({
                    it?.let {
                        DKConstant.saveLoan(it)
                        view.reloadLoan(it)
                    }
                }, {})
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