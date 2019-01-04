package com.annis.dk.ui.renzheng

import com.annis.baselib.base.rx.RxUtil
import com.annis.dk.base.DKConstant
import com.annis.dk.base.DKPresenter
import com.annis.dk.utils.DkSPUtils

class RenzhengPresenter(view: RenzhengView?) : DKPresenter<RenzhengView>(view) {
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

    /**
     * 提交贷款申请
     */
    fun saveLoan() {
        view.showWaitting()
        /***还没有申请***/
        addSubscribe(
            getHttpApi()!!.saveLoan(DkSPUtils.getUID(), DkSPUtils.getKey())
                .compose(RxUtil.rxSchedulerHelper())
                .subscribe({ r ->
                    if (r.isSave == 0) {
                        updateAllInfo()
                        view.errorMsg("申请提交成功")
                    } else {
                        view.errorMsg("申请提交失败")
                    }
                    view.dismissWaitting()
                }, {
                    view.dismissWaitting()
                })
        )
    }
}