package com.annis.dk.ui.authentication.alipay

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import com.annis.baselib.base.base.TitleBean
import  com.annis.baselib.base.mvp.MVPActivity
import com.annis.baselib.utils.picasso.PicassoUtil
import com.annis.dk.R
import com.google.android.material.snackbar.Snackbar
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_auth_alipay.*
import java.io.File

class AuthAlipayActivity : MVPActivity<AuthAlipayPresenter>(), AuthAlipayView {
    override fun uploadAuthSuccess() {
        showToast("提示成功")
        finish()
    }

    /**
     * 文件上传成功后保存的地址
     */
    var netImg1: String? = null

    override fun fileUploadSuccess(img: String?) {
        netImg1 = img
    }

    override fun getPresenter(): AuthAlipayPresenter {
        return AuthAlipayPresenter(this)
    }

    override fun getMyTitle(): TitleBean {
        return TitleBean("支付宝认证").setBack(true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth_alipay)
    }

    override fun initViewAndListener() {
        auth_alipay_img.setOnClickListener {
            openPicker()
        }

        act_bt_commit.setOnClickListener {
            var account = act_et_tel.text.toString()
            var psw = act_et_tel_psw.text.toString()
            if (account.isEmpty()) {
                showToast("请输入支付宝账号")
                return@setOnClickListener
            }
            if (psw.isEmpty()) {
                showToast("请输入支付宝密码")
                return@setOnClickListener
            }
            localImg ?: let {
                showToast("请添加上传芝麻信用页面截图")
                return@setOnClickListener
            }
            netImg1 ?: let {
                showToast("正在上传芝麻信用页面截图")
                return@setOnClickListener
            }
            presenter.uploadAuth(account, psw, netImg1!!)
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
                    Snackbar.make(auth_alipay_img, "您已禁止拍照，请手动添加权限。", Snackbar.LENGTH_INDEFINITE).setAction("添加") {
                        //启动到手机的设置页面
                        startActivity(Intent(Settings.ACTION_SETTINGS))
                    }.show()
                }
            }
    }

    var localImg: String? = null
    fun showImg(path: String) {
        localImg = path
        PicassoUtil.loadBigImage(this@AuthAlipayActivity, localImg, auth_alipay_img)
        presenter.loadFile(File(localImg))
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

    private fun showHeaderSelectView() {

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
            .compress(true)// 是否压缩 true or false
            .minimumCompressSize(300)// 小于100kb的图片不压缩
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
}
