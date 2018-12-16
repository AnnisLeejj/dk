package com.annis.dk.ui.emergency_contact

import android.Manifest
import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Toast
import com.annis.baselib.base.base.TitleBean
import com.annis.baselib.base.mvp.MVPActivty
import com.annis.dk.R
import com.annis.dk.ui.authentication.operator.AuthoperatorActivity
import com.annis.dk.utils.ExcelUtil
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_emergency_contact.*
import java.io.File

class EmergencyContactActivity : MVPActivty<EmergencyContactPresenter>(), EmergencyContactView {
    override fun upSuccess() {
        startActivity(AuthoperatorActivity::class.java)
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

    }

    fun click() {
        act_bt_change.setOnClickListener {
            var name1 = contact_1_name.text.toString()
            var relation1 = contact_1_relation.text.toString()
            var mobel1 = contact_1_mobel.text.toString()

            var name2 = contact_2_name.text.toString()
            var relation2 = contact_2_relation.text.toString()
            var mobel2 = contact_2_mobel.text.toString()

            var name3 = contact_3_name.text.toString()
            var relation3 = contact_3_relation.text.toString()
            var mobel3 = contact_3_mobel.text.toString()

            presenter.uploadEmergencyConyact(
                name1, relation1, mobel1,
                name2, relation2, mobel2,
                name3, relation3, mobel3
            )
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