package com.annis.dk.ui.authentication.alipay

import com.annis.baselib.base.mvp.BaseView
import com.annis.baselib.base.rx.RxUtil
import com.annis.dk.base.DKPresenter
import com.annis.dk.utils.DkSPUtils
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

/**
 * @author Lee
 * @date 2018/12/15 16:46
 * @Description
 */
interface AuthAlipayView : BaseView {
    /**
     * 文件上传成功
     */
    fun fileUploadSuccess(img: String?)

    /**
     * 提交成功
     */
    fun uploadAuthSuccess()
}

class AuthAlipayPresenter(view: AuthAlipayView?) : DKPresenter<AuthAlipayView>(view) {

    fun uploadAuth(account: String, psw: String, img: String) {
        view.showWaitting()
        addSubscribe(
            getHttpApi()!!.saveAlipay(
                DkSPUtils.getUID(), DkSPUtils.getKey(), account, psw, img
            ).compose(RxUtil.rxSchedulerHelper()).subscribe({ r ->
                if (r.isSave == 0) {
                    view.uploadAuthSuccess()
                } else {
                    view.errorMsg("提交失败")
                }
                view.dismissWaitting()
            }, { e ->
                view.dismissWaitting()
                view.errorMsg("提交失败")
            })
        )
    }

    fun loadFile(file: File) {
        view.showWaitting()
        val body = RequestBody.create(MediaType.parse("image/png"), file)
        val part = MultipartBody.Part.createFormData("file", file.name, body)
        addSubscribe(
            getHttpApi()!!.uploadFile(part)
                .compose(RxUtil.rxSchedulerHelper())
                .subscribe({ r ->
                    if (r.state == "200") {
                        view.fileUploadSuccess(r.img)
                    } else {
                        view.errorMsg("图片上传失败")
                    }
                    view.dismissWaitting()
                }, { e ->
                    view.errorMsg("图片上传失败")
                    view.dismissWaitting()
                })
        )
    }

}