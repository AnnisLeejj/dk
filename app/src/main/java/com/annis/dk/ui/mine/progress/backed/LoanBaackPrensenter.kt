package com.annis.dk.ui.mine.progress.backed

import com.annis.baselib.base.mvp.BaseView
import com.annis.baselib.base.rx.RxUtil
import com.annis.dk.base.DKPresenter
import com.annis.dk.utils.DkSPUtils

interface LoanBaackView : BaseView {
    fun success()

}
class LoanBaackPrensenter(view: LoanBaackView?) : DKPresenter<LoanBaackView>(view) {
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
                        view.success()
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