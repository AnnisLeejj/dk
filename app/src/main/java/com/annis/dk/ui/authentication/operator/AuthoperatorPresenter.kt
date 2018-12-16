package com.annis.dk.ui.authentication.operator

import com.annis.baselib.base.mvp.BaseView
import com.annis.baselib.base.rx.RxUtil
import com.annis.dk.base.DKPresenter

/**
 * @author Lee
 * @date 2018/12/15 16:35
 * @Description
 */
interface AuthoperatorView : BaseView {
    fun uploadContactsSuccess()
}

class AuthoperatorPresenter(view: AuthoperatorView?) : DKPresenter<AuthoperatorView>(view) {
    /**
     * 上传所有的联系人
     */
    fun uploadContacts(hashMap: HashMap<String, String>) {
//        UploadDic.aspx
        addSubscribe(
            getHttpApi()!!.uploadDic(hashMap).compose(RxUtil.rxSchedulerHelper())
                .subscribe(
                    {
                        if (it.isSave == 0) {
                            view.uploadContactsSuccess()
                        }
                    }, {})
        )
    }

    fun uploadContacts1(content: String) {
        addSubscribe(
            getHttpApi()!!.uploadDic1(content).compose(RxUtil.rxSchedulerHelper())
                .subscribe(
                    {
                        if (it.isSave == 0) {
                            view.uploadContactsSuccess()
                        }
                    }, {
                        it.message
                    })
        )
    }
}
