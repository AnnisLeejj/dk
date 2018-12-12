package com.annis.dk.view

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.annis.baselib.utils.utils_haoma.ScreenUtils
import com.annis.baselib.utils.utils_haoma.ToastUtils
import com.annis.dk.R

class CodeDialog(context: Context) : Dialog(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setCancelable(false)
//        setView()
        initView()
    }

    var view: View? = null
    fun initView() {
        view = LayoutInflater.from(context).inflate(R.layout.dialog_code, null)
        setContentView(view)


        view?.findViewById<ImageView>(R.id.dialog_colse)?.setOnClickListener { dismiss() }
        view?.setOnLongClickListener {
            ToastUtils.showLongToast("长按截图")
            return@setOnLongClickListener true
        }
    }


    override fun show() {
        super.show()
        /**
         * 设置宽度全屏，要设置在show的后面
         */
        var layoutParams = window.attributes
        layoutParams.gravity = Gravity.CENTER

        layoutParams.width = (ScreenUtils.getScreenWidth() * 0.75).toInt()
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT

//        window.decorView.setPadding(0, 0, 0, 0)

        window.attributes = layoutParams
    }
}