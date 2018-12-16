package com.annis.dk.ui.renzheng

import com.annis.baselib.base.mvp.BaseView
import com.annis.dk.bean.UserEntity

interface RenzhengView : BaseView {
    fun updateSuccess(it: UserEntity)
}