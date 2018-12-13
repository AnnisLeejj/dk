package com.annis.dk.ui

import android.Manifest
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import com.annis.baselib.base.base.BaseActivity
import com.annis.baselib.base.base.TitleBean
import com.annis.baselib.utils.utils_haoma.ToastUtils
import com.annis.dk.R
import com.google.android.material.snackbar.Snackbar
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_my_loans.*

class ComstorServiceActivity : BaseActivity() {
    override fun getMyTitle(): TitleBean {
        return TitleBean("客服").setBack(true)
    }

    override fun initViewAndListener() {
        tvWeixin.text = intent.getStringExtra("weixin")//微信号
        tvPhone.text = intent.getStringExtra("phone")//手机号
        click()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service)
    }

    fun click() {
        act_bt_call.setOnClickListener {
            //打电话
            call(tvPhone.text.toString())
        }
        act_bt_copy.setOnClickListener {
            //复制微信
            // 获取系统剪贴板
            var clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

            // 创建一个剪贴数据集，包含一个普通文本数据条目（需要复制的数据）
            var clipData = ClipData.newPlainText("Label", tvWeixin.text.toString())

            // 把数据集设置（复制）到剪贴板
            clipboard.primaryClip = clipData
            ToastUtils.showLongToast("复制成功")
        }
    }

    fun call(tel: String) {
        var permission = RxPermissions(this)
        permission.requestEach(Manifest.permission.CALL_PHONE).subscribe { r ->
            if (r.granted) {
                callOut(tel)
            } else if (r.shouldShowRequestPermissionRationale) {
                ToastUtils.showLongToast("请允许拨打电话。")
            } else {
                //永远拒绝
                Snackbar.make(window.decorView, "您已禁止拨打电话，请手动添加权限。", Snackbar.LENGTH_INDEFINITE)
                    .setAction("添加") {
                        //启动到手机的设置页面
                        startActivity(Intent(Settings.ACTION_SETTINGS))
                    }.show()
            }
        }

    }

    fun callOut(tel: String) {
        var intent = Intent(Intent.ACTION_CALL)
        var data = Uri.parse("tel:$tel")
        intent.data = data
        startActivity(intent)
    }
}