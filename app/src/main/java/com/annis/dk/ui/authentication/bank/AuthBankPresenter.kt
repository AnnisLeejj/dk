package com.annis.dk.ui.authentication.bank

import com.annis.baselib.base.mvp.BaseView
import com.annis.baselib.base.rx.RxUtil
import com.annis.dk.base.DKConstant
import com.annis.dk.base.DKPresenter
import com.annis.dk.bean.BankInfo
import com.annis.dk.utils.DkSPUtils

/**
 * @author Lee
 * @date 2018/12/15 17:18
 * @Description
 */
interface AuthBankView : BaseView {
    fun uploadSuccess()
    fun updateBankCard(it: BankInfo)
}

class AuthBankPresenter(view: AuthBankView?) : DKPresenter<AuthBankView>(view) {
    fun upload(name: String, id: String, card: String, mobel: String) {
        view.showWaitting()
        addSubscribe(
            getHttpApi()!!.saveBank(DkSPUtils.getUID(), DkSPUtils.getKey(), name, id, card, mobel)
                .compose(RxUtil.rxSchedulerHelper())
                .subscribe({
                    view.dismissWaitting()
                    if (it.isSave == 0) {
                        view.uploadSuccess()
                    } else {
                        view.errorMsg("请求失败")
                    }

                }, {
                    view.dismissWaitting()
                    view.errorMsg("请求失败")
                })
        )
    }

    /**
     * 更新银行卡信息
     */
    fun updateBankCard1() {
        addSubscribe(
            getHttpApi()!!.getBank(DkSPUtils.getUID(), DkSPUtils.getKey())
                .compose(RxUtil.rxSchedulerHelper())
                .subscribe({
                    it?.let {
                        DKConstant.saveBankCard(it)
                        view.updateBankCard(it)
                    }
                }, {

                })
        )
    }
}