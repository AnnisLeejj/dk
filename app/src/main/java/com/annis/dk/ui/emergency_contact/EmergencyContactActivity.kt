package com.annis.dk.ui.emergency_contact

import android.Manifest
import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Toast
import com.annis.baselib.base.base.BaseActivity
import com.annis.baselib.base.base.TitleBean
import com.annis.dk.R
import com.tbruyelle.rxpermissions2.RxPermissions

class EmergencyContactActivity : BaseActivity() {
    override fun getMyTitle(): TitleBean {
        return TitleBean("紧急联系人").setBack(true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emergency_contact)
    }

    override fun initViewAndListener() {
        checkPermision()
    }

    private fun checkPermision() {
        //没有权限时，调用requestPermission方法，弹出权限申请对话框 ，回调OnRequestPermissionRelust函数

        var permissions = RxPermissions(this)

        val granted = permissions.isGranted(Manifest.permission.READ_CONTACTS)
        if (granted) {
            readContacts()
        } else {
            permissions.requestEach(Manifest.permission.READ_CONTACTS)
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
                    list.add(displayName + '\n'.toString() + number)
                }
                list.size
                //notify公布
//                adapter.notifyDataSetChanged()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            if (cursor != null) {
                cursor!!.close()
            }
        }
    }
}
