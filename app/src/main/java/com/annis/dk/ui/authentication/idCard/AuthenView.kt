package com.annis.dk.ui.authentication.idCard

import com.annis.baselib.base.mvp.BaseView

interface AuthenView : BaseView {
    /**
     * 文件上传成功
     */
    fun fileUploadSuccess(img: String?, currentImg: Int)

    /**
     *上传成功
     */
    fun uploadSuccess()
}