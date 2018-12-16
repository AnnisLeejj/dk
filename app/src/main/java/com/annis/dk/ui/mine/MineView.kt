package com.annis.dk.ui.mine

import com.annis.baselib.base.mvp.BaseView
import com.annis.dk.bean.LoanInfo
import com.annis.dk.bean.UserEntity

interface MineView : BaseView {
    /**
     * 加载我的认证信息
     */
    fun reloadLoan(it: LoanInfo)

    fun updateSuccess(it: UserEntity)
}