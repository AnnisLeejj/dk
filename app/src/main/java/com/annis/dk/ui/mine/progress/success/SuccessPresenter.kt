package com.annis.dk.ui.mine.progress.success

import com.annis.baselib.base.rx.RxUtil
import com.annis.dk.base.DKPresenter

class SuccessPresenter(view: SuccessView?) : DKPresenter<SuccessView>(view){

    /**
     * 获取新的用户贷款信息
     */
    fun updatelsPayCost(uid: String, key: String?) {
        addSubscribe(
            getHttpApi()!!.getUpdatelsPayCost(uid,key)
                .compose(RxUtil.rxSchedulerHelper())
                .subscribe({
                    if(it.isSave==0){
                        view.nextActivity()
                    }
                }, {
                    it.message
                })
        )
    }
}
