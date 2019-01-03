package com.annis.dk.view

import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import com.annis.baselib.BuildConfig
import com.annis.baselib.utils.picasso.PicassoUtil
import com.annis.baselib.utils.utils_haoma.ToastUtils
import com.annis.dk.R
import com.google.android.material.snackbar.Snackbar
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.dialog_code.*

class CodeDialog : DialogFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)//取消对话框fragment的标题

        return inflater.inflate(R.layout.dialog_code, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        isCancelable = false
        initView()
    }

    private fun initView() {
        view?.findViewById<ImageView>(R.id.dialog_colse)?.setOnClickListener { dismiss() }
        view?.setOnLongClickListener {
            checkPermission()
            return@setOnLongClickListener true
        }
        when (type) {
            1 -> {
                dialog_tv_money.text = "需还款：${money}元"
                dialog_tv_email.text = "请添加红鲤鱼客服微信还款"
            }
            2 -> {
                dialog_tv_money.text = "会员服务费：${money}元"
                dialog_tv_email.text = "请添加红鲤鱼客服微信缴纳会员费"
            }
        }

        codeUrl?.contains("http").let {
            if (it != true) {
                codeUrl = "${BuildConfig.IP}$codeUrl"
            }
        }

        PicassoUtil.loadBigImage(activity!!, codeUrl, dialog_iv_code)
    }

    var subscribe: Disposable? = null
    private fun checkPermission() {
        subscribe =
                RxPermissions(activity!!).requestEach(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .subscribe { permission ->
                        when {
                            permission.granted -> save()
                            permission.shouldShowRequestPermissionRationale -> {
                                ToastUtils.showLongToast("请允许保存截图")
                            }
                            else -> {
                                dismiss()
                                //永远拒绝
                                Snackbar.make(view!!, "您已禁止读写，请手动添加权限。", Snackbar.LENGTH_INDEFINITE)
                                    .setAction("添加") {
                                        //启动到手机的设置页面
                                        startActivity(Intent(Settings.ACTION_SETTINGS))
                                    }.show()
                            }
                        }
                    }
    }

    override fun onDestroy() {
        subscribe?.dispose()
        super.onDestroy()
    }

    private fun save() {
        var bitmap = getViewBp(view)// screenshot(view!!)
        bitmap?.let { bitmap ->
            //                val path = saveBitmap(this, context, bitmap)
//            var filePic = File(activity!!.externalCacheDir, generateFileName() + ".jpg")

            val image = MediaStore.Images.Media.insertImage(
                activity!!.contentResolver,
                bitmap, "payCode1", "payCode2"
            )

            //保存图片后发送广播通知更新数据库
//        Uri uri = Uri.fromFile(file);
//        activity!!.sendBroadcast(  Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
            activity!!.sendBroadcast(
                Intent(
                    Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                    Uri.parse(image)
                )
            )
            ToastUtils.showLongToast("付款码已经保存至相册")
            dismiss()
        }
    }

    override fun onStart() {
        super.onStart()
        var dialog = dialog
        if (dialog != null) {
            var dm = DisplayMetrics()
            activity!!.windowManager.defaultDisplay.getMetrics(dm)
            dialog.window.setLayout(((dm.widthPixels * 0.85).toInt()), ViewGroup.LayoutParams.WRAP_CONTENT)
        }
    }

    private var codeUrl: String? = null
    private var money: String? = null
    private var type: Int? = null
    fun setInfo(url: String, money: String, type: Int) {
        codeUrl = url
        this.money = money
        this.type = type
    }

    private fun getViewBp(v: View?): Bitmap? {
        if (null == v) {
            return null
        }
        v.isDrawingCacheEnabled = true
        v.buildDrawingCache()
        if (Build.VERSION.SDK_INT >= 11) {
            v.measure(
                View.MeasureSpec.makeMeasureSpec(
                    v.width,
                    View.MeasureSpec.EXACTLY
                ), View.MeasureSpec.makeMeasureSpec(
                    v.height, View.MeasureSpec.EXACTLY
                )
            )
            v.layout(
                v.x.toInt(), v.y.toInt(),
                v.x.toInt() + v.measuredWidth,
                v.y.toInt() + v.measuredHeight
            )
        } else {
            v.measure(
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
            )
            v.layout(0, 0, v.measuredWidth, v.measuredHeight)
        }
        val b = Bitmap.createBitmap(v.drawingCache, 0, 0, v.measuredWidth, v.measuredHeight)

        v.isDrawingCacheEnabled = false
        v.destroyDrawingCache()
        return b
    }
}