package com.annis.dk.ui.authentication.idCard

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import com.annis.baselib.base.base.BaseActivity
import com.annis.baselib.base.base.TitleBean
import com.annis.baselib.utils.ext_utils.FileUtilsExt
import com.annis.baselib.utils.picasso.PicassoUtil
import com.annis.baselib.utils.utils_haoma.TimeUtils
import com.annis.dk.R
import com.google.android.material.snackbar.Snackbar
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_auth_idcard.*
import top.zibin.luban.Luban
import top.zibin.luban.OnCompressListener
import java.io.File
import java.util.*

class AuthIdcardActivity : BaseActivity() {
    val REQUEST_CODE_IMAGE = 1
    override fun getMyTitle(): TitleBean {
        return TitleBean("身份证认证").setBack(true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth_idcard)
    }

    var currentImg = 0
    override fun initViewAndListener() {
        idcard_1_img.setOnClickListener {
            currentImg = 1
            openPicker()
        }
        idcard_2_img.setOnClickListener {
            currentImg = 2
            openPicker()
        }
        idcard_3_img.setOnClickListener {
            currentImg = 3
            openPicker()
        }
    }

    var permissions: RxPermissions? = null
    fun openPicker() {
        permissions ?: let {
            permissions = RxPermissions(this)
        }
        /***  检测权限  ***/
        val subscribe = permissions?.requestEach(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)
            ?.subscribe { permission ->
                if (permission.granted) {
                    showHeaderSelectView()
                } else if (permission.shouldShowRequestPermissionRationale) {
                    showToast("请允许拍照。")
                } else {
                    //永远拒绝
                    Snackbar.make(idcard_1_img, "您已禁止拍照，请手动添加权限。", Snackbar.LENGTH_INDEFINITE).setAction("添加") {
                        //启动到手机的设置页面
                        startActivity(Intent(Settings.ACTION_SETTINGS))
                    }.show()
                }
            }
    }

    private fun showHeaderSelectView() {


        
//        PickPhotoView.Builder(this)
//            .setPickPhotoSize(1)                  // select image size
//            .setClickSelectable(true)             // click one image immediately close and return image
//            .setShowCamera(true)                  // is show camera
//            .setSpanCount(3)                      // span count
//            .setLightStatusBar(true)              // lightStatusBar used in Android M or higher
//            .setStatusBarColor(R.color.white)     // statusBar color
//            .setToolbarColor(R.color.white)       // toolbar color
//            .setToolbarTextColor(R.color.black)   // toolbar text color
//            // .setSelectIconColor(R.color.pink)     // select icon color
//            .setShowGif(false)                    // is show gif
//            .start()
    }

    /**
     * 拍照返回结果
     *
     * @param data
     */
    var photoPath1: String? = null
    var photoPath2: String? = null
    var photoPath3: String? = null

    private fun onPhotoReceived(path: String) {
        val oldFile = File(path)
        when (currentImg) {
            1 -> {
                photoPath1 = FileUtilsExt.getPhotoPath() + TimeUtils.date2String(Date(), "yyyyMMddHHmmss") + ".png"
            }
            2 -> {
                photoPath2 = FileUtilsExt.getPhotoPath() + TimeUtils.date2String(Date(), "yyyyMMddHHmmss") + ".png"
            }
            3 -> {
                photoPath3 = FileUtilsExt.getPhotoPath() + TimeUtils.date2String(Date(), "yyyyMMddHHmmss") + ".png"
            }
        }
        //图片压缩
        Luban.with(this).load(oldFile).setCompressListener(object : OnCompressListener {
            override fun onSuccess(file: File?) {
                //移动到原来的路径
                FileUtilsExt.copyFiles(
                    file?.absolutePath, getPath(currentImg), true
                )
                //file?.delete()
//                persenter.uploadFile(FileBean.Imgs, photoPath, FileUtils.getFileName(photoPath))
                showImg()
            }

            override fun onError(e: Throwable?) {
            }

            override fun onStart() {
            }
        }).launch()
    }

    fun showImg() {
        when (currentImg) {
            1 -> {
                PicassoUtil.loadBigImage(this@AuthIdcardActivity, photoPath1, idcard_1_img)
            }
            2 -> {
                PicassoUtil.loadBigImage(this@AuthIdcardActivity, photoPath2, idcard_2_img)
            }
            3 -> {
                PicassoUtil.loadBigImage(this@AuthIdcardActivity, photoPath3, idcard_3_img)
            }
        }
    }

    fun getPath(current: Int): String? {
        return when (current) {
            1 -> photoPath1
            2 -> photoPath2
            3 -> photoPath3
            else -> null
        }
    }
}
