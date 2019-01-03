package com.annis.dk.view

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.annis.dk.R

/**
 * @author Lee
 * @date 2018/12/17 18:19
 * @Description
 */
class PaidDialog : DialogFragment() {
    interface Dismiss {
        fun agree()
        fun jujue()
    }

    fun setDismissListener(dismiss: Dismiss) {
        mDismiss = dismiss
    }

    var mDismiss: Dismiss? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)//取消对话框fragment的标题
        return inflater.inflate(R.layout.dialog_paid, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        isCancelable = false
        initView()
    }

    var mMessage: String? = null
    var tvTitle: TextView? = null
    private fun initView() {
        tvTitle = view?.findViewById<TextView>(R.id.dialog_noti_tvMessage)
        mMessage?.let {
            tvTitle?.text = mMessage
        }
        view?.findViewById<TextView>(R.id.tongyi)?.setOnClickListener { mDismiss?.jujue() }
        view?.findViewById<TextView>(R.id.jujue)?.setOnClickListener { mDismiss?.agree() }
    }

    fun setMessage(message: String) {
        mMessage = message
    }

    override fun onStart() {
        super.onStart()
        var dialog = dialog
        if (dialog != null) {
            var dm = DisplayMetrics()
            activity!!.windowManager.defaultDisplay.getMetrics(dm)
            dialog.window.setLayout(((dm.widthPixels * 0.75).toInt()), ViewGroup.LayoutParams.WRAP_CONTENT)
        }
    }
}
