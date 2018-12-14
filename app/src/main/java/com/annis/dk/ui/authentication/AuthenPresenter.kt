package com.annis.dk.ui.authentication

import com.annis.baselib.base.rx.RxUtil
import com.annis.dk.base.DKPresenter
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class AuthenPresenter(view: AuthenView?) : DKPresenter<AuthenView>(view) {
    /**
     * 上传身份证
     */
    fun loadIdcard(file1: File, file2: File, file3: File) {
        loadFile(file1)
    }


    fun loadFile(file: File) {
        val body = RequestBody.create(MediaType.parse("image/png"), file)
        val part = MultipartBody.Part.createFormData("file", file.name, body)
        addSubscribe(
            getHttpApi()!!.uploadFile(part)
                .compose(RxUtil.rxSchedulerHelper())
                .subscribe({ r ->
                    if (r.state == "200") {

                    }
                }, { e ->
                    view.errorMsg("图片上传失败")
                })
        )
    }
}