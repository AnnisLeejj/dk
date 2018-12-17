package com.annis.dk.ui.mine.bankmanage

import com.annis.baselib.base.mvp.BaseView
import com.annis.baselib.base.rx.RxUtil
import com.annis.dk.base.DKConstant
import com.annis.dk.base.DKPresenter
import com.annis.dk.bean.BankInfo
import com.annis.dk.utils.DkSPUtils

interface BankManageVIew : BaseView {
    fun updateBankCard(it: BankInfo)
}

class BankManagePresenter(view: BankManageVIew?) : DKPresenter<BankManageVIew>(view) {
    /**
     * 更新银行卡信息
     */
    fun updateBankCard1() {
        view.showWaitting()
        addSubscribe(
            getHttpApi()!!.getBank(DkSPUtils.getUID(), DkSPUtils.getKey())
                .compose(RxUtil.rxSchedulerHelper())
                .subscribe({
                    it?.let {
                        DKConstant.saveBankCard(it)
                        view.updateBankCard(it)
                    }
                    view.dismissWaitting()
                }, {
                    view.dismissWaitting()
                })
        )
    }
}