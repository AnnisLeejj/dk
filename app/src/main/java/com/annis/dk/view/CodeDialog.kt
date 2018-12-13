package com.annis.dk.view

import android.app.Dialog
import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import com.annis.baselib.utils.utils_haoma.ToastUtils
import com.annis.dk.R
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*


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

    fun getViewBp(v: View?): Bitmap? {
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

    private val SD_PATH = "/sdcard/dskqxt/pic/"
    private val IN_PATH = "/dskqxt/pic/"
    /**
     * 保存bitmap到本地
     *
     * @param context
     * @param mBitmap
     * @return
     */
    fun saveBitmap(context: Context, mBitmap: Bitmap): String? {
        var savePath: String?
        var filePic: File?
        if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            savePath = SD_PATH
        } else {
            savePath = context.applicationContext.filesDir.absolutePath + IN_PATH
        }
        try {
            filePic = File(savePath + generateFileName() + ".jpg")
            if (!filePic.exists()) {
                filePic.getParentFile().mkdirs()
                filePic.createNewFile()
            }
            var fos = FileOutputStream(filePic)
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
            fos.flush()
            fos.close()
        } catch (e: IOException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
            return null
        }

        return filePic.absolutePath
    }

    /**
     * 随机生产文件名
     *
     * @return
     */
    private fun generateFileName(): String {
        return UUID.randomUUID().toString()
    }
}