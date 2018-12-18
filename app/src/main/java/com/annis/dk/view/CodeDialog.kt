package com.annis.dk.view

import android.Manifest
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
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
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*


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

        dialog_tv_money.text = "支付${money}元"

        codeUrl?.contains("http").let {
            if (it != true) {
                codeUrl = "${BuildConfig.IP}$codeUrl"
            }
        }

        PicassoUtil.loadBigImage(activity!!, codeUrl, dialog_iv_code)
    }

    var subscribe: Disposable? = null
    fun checkPermission() {
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
                                    .setAction("添加") { v1 ->
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

    fun save() {
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

    /**
     * 针对系统文夹只需要扫描,不用插入内容提供者,不然会重复
     *
     * @param context  上下文
     * @param filePath 文件路径
     */
    private fun scanFile(context: Context, filePath: String) {
        if (!File(filePath).exists())
            return
        var intent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
        intent.data = Uri.fromFile(File(filePath))
        context.sendBroadcast(intent)
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

    /**
     * 随机生产文件名
     *
     * @return
     */
    private fun generateFileName(): String {
        return UUID.randomUUID().toString()
    }

    var codeUrl: String? = null
    var money: String? = null
    fun setInfo(url: String, money: String) {
        codeUrl = url
        this.money = money
    }

    companion object {
        private const val IN_PATH = "/dskqxt/pic/"
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

        /**
         * 保存bitmap到本地
         *
         * @param context
         * @param mBitmap
         * @return
         */
        private fun saveBitmap(codeDialog: CodeDialog, context: Context, mBitmap: Bitmap): String? {
            var savePath: File?
            var filePic: File?
            if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
                savePath = context.externalCacheDir
            } else {
                savePath = context.obbDir
            }
            try {
                filePic = File(savePath, codeDialog.generateFileName() + ".jpg")
                if (!filePic.exists()) {
                    filePic.parentFile.mkdirs()
                    filePic.createNewFile()
                }
                var fos = FileOutputStream(filePic)
                mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
                fos.flush()
                fos.close()
            } catch (e: IOException) {
                e.printStackTrace()
                return null
            } finally {
            }
            return filePic?.absolutePath
        }
    }
}