package com.annis.dk.ui.authentication.idCard

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import com.annis.baselib.base.base.TitleBean
import  com.annis.baselib.base.mvp.MVPActivity
import com.annis.baselib.utils.picasso.PicassoUtil
import com.annis.dk.R
import com.annis.dk.view.NotificationDialog
import com.google.android.material.snackbar.Snackbar
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_auth_idcard.*
import java.io.File


class AuthIdcardActivity : MVPActivity<AuthenPresenter>(), AuthenView {
    override fun uploadSuccess() {
        showToast("提交成功")

        var dialog = NotificationDialog()
        dialog.setDismissListener(object : NotificationDialog.Dismiss {
            override fun finish() {
                this@AuthIdcardActivity.finish()
            }

        })
        dialog.setMessage("身份证正在系统扫描中，等待系统认证中...")
        dialog.show(supportFragmentManager, "notify")

    }

    /**
     * 文件上传成功后保存的地址
     */
    var netImg1: String? = null
    var netImg2: String? = null
    var netImg3: String? = null
    override fun fileUploadSuccess(img: String?, currentImg: Int) {
        when (currentImg) {
            1 -> {
                netImg1 = img
            }
            2 -> {
                netImg2 = img
            }
            3 -> {
                netImg3 = img
            }
        }
    }

    override fun getPresenter(): AuthenPresenter {
        return AuthenPresenter(this)
    }

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
            openPicker(1)
        }
        idcard_2_img.setOnClickListener {
            currentImg = 2
            openPicker(2)
        }
        idcard_3_img.setOnClickListener {
            currentImg = 3
            openPicker(3)
        }
        act_bt_login.setOnClickListener {
            localImg1 ?: let {
                showToast("请添加身份证人像面")
                return@setOnClickListener
            }
            localImg2 ?: let {
                showToast("请添加身份证国徽面")
                return@setOnClickListener
            }
            localImg3 ?: let {
                showToast("请添加手持身份证照片")
                return@setOnClickListener
            }

            netImg1 ?: let {
                showToast("正在上传身份证人像面")
                return@setOnClickListener
            }
            netImg2 ?: let {
                showToast("正在上传身份证国徽面")
                return@setOnClickListener
            }
            netImg3 ?: let {
                showToast("正在上传手持身份证照片")
                return@setOnClickListener
            }
            presenter.loadIdcard(netImg1!!, netImg2!!, netImg3!!)
        }
    }

    var permissions: RxPermissions? = null
    fun openPicker(current: Int) {
        permissions ?: let {
            permissions = RxPermissions(this)
        }
        /***  检测权限  ***/
        val subscribe = permissions?.requestEach(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)
            ?.subscribe { permission ->
                if (permission.granted) {
                    showHeaderSelectView(current)
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

    private fun showHeaderSelectView(current: Int) {

        // 进入相册 以下是例子：用不到的api可以不写
        PictureSelector.create(this)
            .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
            //.theme()//主题样式(不设置为默认样式) 也可参考demo values/styles下 例如：R.style.picture.white.style
            .maxSelectNum(1)// 最大图片选择数量 int
            .minSelectNum(1)// 最小选择数量 int
            .imageSpanCount(4)// 每行显示个数 int
            .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
            .previewImage(true)// 是否可预览图片 true or false
            .previewVideo(false)// 是否可预览视频 true or false
            .enablePreviewAudio(false) // 是否可播放音频 true or false
            .isCamera(true)// 是否显示拍照按钮 true or false
            .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
            .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
            .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
            .setOutputCameraPath("/CustomPath")// 自定义拍照保存路径,可不填
            //.enableCrop(true)// 是否裁剪 true or false
//            .compress(true)// 是否压缩 true or false
            //.glideOverride()// int glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
            //.withAspectRatio(16, 10)// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
            // .hideBottomControls()// 是否显示uCrop工具栏，默认不显示 true or false
            .isGif(false)// 是否显示gif图片 true or false
//            .compressSavePath(getPath(current))//压缩图片保存地址
            // .freeStyleCropEnabled(true)// 裁剪框是否可拖拽 true or false
//            .circleDimmedLayer()// 是否圆形裁剪 true or false
            .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
            .showCropGrid(true)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
            .openClickSound(true)// 是否开启点击声音 true or false
//            .selectionMedia()// 是否传入已选图片 List<LocalMedia> list
//            .previewEggs()// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
//            .cropCompressQuality()// 裁剪压缩质量 默认90 int
//            .minimumCompressSize(100)// 小于100kb的图片不压缩
            .synOrAsy(true)//同步true或异步false 压缩 默认同步
//            .cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效 int
           // .rotateEnabled(true) // 裁剪是否可旋转图片 true or false
            .scaleEnabled(true)// 裁剪是否可放大缩小图片 true or false
//            .videoQuality()// 视频录制质量 0 or 1 int
            .videoMaxSecond(15)// 显示多少秒以内的视频or音频也可适用 int
            .videoMinSecond(10)// 显示多少秒以内的视频or音频也可适用 int
            .recordVideoSecond(60)//视频秒数录制 默认60s int
            .isDragFrame(false)// 是否可拖动裁剪框(固定)
            .forResult(PictureConfig.CHOOSE_REQUEST)//结果回调onActivityResult code
    }

    /**
     * 拍照返回结果
     *
     * @param data
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                PictureConfig.CHOOSE_REQUEST -> {
                    // 图片、视频、音频选择结果回调
                    val selectList = PictureSelector.obtainMultipleResult(data)
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    var compPath = selectList[0].compressPath
                    var cutPath = selectList[0].cutPath
                    var path = selectList[0].path

                    showImg(compPath ?: (cutPath ?: path))
                }
            }
        }
    }


    var localImg1: String? = null
    var localImg2: String? = null
    var localImg3: String? = null
    fun showImg(path: String) {
        when (currentImg) {
            1 -> {
                localImg1 = path
                PicassoUtil.loadBigImage(this@AuthIdcardActivity, localImg1, idcard_1_img)
                presenter.loadFile(File(localImg1), 1)
            }
            2 -> {
                localImg2 = path
                PicassoUtil.loadBigImage(this@AuthIdcardActivity, localImg2, idcard_2_img)
                presenter.loadFile(File(localImg2), 2)
            }
            3 -> {
                localImg3 = path
                PicassoUtil.loadBigImage(this@AuthIdcardActivity, localImg3, idcard_3_img)
                presenter.loadFile(File(localImg3), 3)
            }
        }
    }
}
