package com.annis.dk.ui.authentication.idCard

import com.annis.baselib.base.rx.RxUtil
import com.annis.dk.base.DKPresenter
import com.annis.dk.utils.DkSPUtils
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class AuthenPresenter(view: AuthenView?) : DKPresenter<AuthenView>(view) {
    /**
     * 上传身份证
     */
    fun loadIdcard(file1: String, file2: String, file3: String) {
        view.showWaitting("正在提交...")
        addSubscribe(
            getHttpApi()!!.saveIDCard(DkSPUtils.getUID(), DkSPUtils.getKey(), file1, file2, file3).compose(
                RxUtil.rxSchedulerHelper()
            ).subscribe({
                it.isSave.let { it ->
                    if (it == 0) {
                        view.uploadSuccess()
                    } else {
                        view.errorMsg("提交失败")
                    }
                }
                view.dismissWaitting()
            }, {
                view.dismissWaitting()
                view.errorMsg("网络请求失败")
            })
        )
    }

    fun loadFile(file: File, currentImg: Int) {
        view.showWaitting("正在上传...")
        val body = RequestBody.create(MediaType.parse("image/png"), file)
        val part = MultipartBody.Part.createFormData("file", file.name, body)
        addSubscribe(
            getHttpApi()!!.uploadFile(part)
                .compose(RxUtil.rxSchedulerHelper())
                .subscribe({ r ->
                    if (r.state == "200") {
                        view.fileUploadSuccess(r.img, currentImg)
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