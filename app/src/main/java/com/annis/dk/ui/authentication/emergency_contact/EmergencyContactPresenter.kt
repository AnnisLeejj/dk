package com.annis.dk.ui.authentication.emergency_contact

import android.text.TextUtils
import com.annis.baselib.base.rx.RxUtil
import com.annis.dk.base.DKPresenter
import com.annis.dk.utils.DkSPUtils

/**
 * @author Lee
 * @date 2018/12/14 13:55
 * @Description
 */
class EmergencyContactPresenter(view: EmergencyContactView?) : DKPresenter<EmergencyContactView>(view) {

    fun uploadEmergencyConyact(
        toString: String, toString1: String, toString2: String,
        toString3: String, toString4: String, toString5: String,
        toString6: String, toString7: String, toString8: String
    ) {
        if (TextUtils.isEmpty(toString)) {
            view.errorMsg("请填写紧急联系人1 姓名")
            return
        }
        if (TextUtils.isEmpty(toString1)) {
            view.errorMsg("请填写紧急联系人1 关系")
            return
        }
        if (TextUtils.isEmpty(toString2)) {
            view.errorMsg("请填写紧急联系人1 电话")
            return
        }
        if (TextUtils.isEmpty(toString3)) {
            view.errorMsg("请填写紧急联系人2 姓名")
            return
        }
        if (TextUtils.isEmpty(toString4)) {
            view.errorMsg("请填写紧急联系人2 关系")
            return
        }
        if (TextUtils.isEmpty(toString5)) {
            view.errorMsg("请填写紧急联系人2 电话")
            return
        }
        if (TextUtils.isEmpty(toString6)) {
            view.errorMsg("请填写紧急联系人3 姓名")
            return
        }
        if (TextUtils.isEmpty(toString7)) {
            view.errorMsg("请填写紧急联系人3 关系")
            return
        }
        if (TextUtils.isEmpty(toString8)) {
            view.errorMsg("请填写紧急联系人3 电话")
            return
        }

        addSubscribe(
            getHttpApi()!!.saveOperator(
                DkSPUtils.getUID(), DkSPUtils.getKey(),
                toString, toString1, toString2,
                toString3, toString4, toString5,
                toString6, toString7, toString8
            ).compose(RxUtil.rxSchedulerHelper())
                .subscribe({
                    it.let { isSave ->
                        if (isSave.isSave == 0) {
                            view.upSuccess()
                        } else {
                            view.errorMsg("请求失败,请重试")
                        }
                    }
                }, {
                    view.errorMsg("请求失败,请重试")
                })
        )
    }
}