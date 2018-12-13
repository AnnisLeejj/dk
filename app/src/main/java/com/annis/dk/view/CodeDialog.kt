package com.annis.dk.view

import android.app.Dialog
import android.content.Context
import android.graphics.Bitmap
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
        val attributes = window!!.attributes
        attributes.gravity = Gravity.CENTER
//        attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
        attributes.width = (window!!.windowManager.defaultDisplay.width * 0.9).toInt()
        window!!.attributes = attributes
    }


    /**
     * 对View进行量测，布局后截图
     *
     * @param view
     * @return
     */
    fun screenshot(view: View): Bitmap {
        view.measure(
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        )
        view.layout(0, 0, view.measuredWidth, view.measuredHeight)
        view.isDrawingCacheEnabled = true
        view.buildDrawingCache()
        var bitmap = view.drawingCache
        return bitmap
    }
}