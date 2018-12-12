package com.annis.dk.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.annis.baselib.base.base.BaseFragment
import com.annis.baselib.base.base.TitleBean
import com.annis.baselib.base.mvp.BaseView
import com.annis.baselib.base.mvp.MVPActivty
import com.annis.baselib.base.mvp.MvpPersenter
import com.annis.dk.R
import com.annis.dk.ui.mine.MineFragment
import com.annis.dk.ui.renzheng.RenzhengFragment
import kotlinx.android.synthetic.main.activity_main.*
import android.widget.Toast
import androidx.annotation.NonNull
import android.provider.ContactsContract
import android.database.Cursor


class MainActivity : MVPActivty<MvpPersenter<BaseView>>(), BaseView {
    override fun getMyTitle(): TitleBean? {
        return null
    }

    override fun getPersenter(): MvpPersenter<BaseView> {
        return MvpPersenter(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun initViewAndListener() {
        bottomInit()
        checkPermision()
    }

    private fun checkPermision() {
        //没有权限时，调用requestPermission方法，弹出权限申请对话框 ，回调OnRequestPermissionRelust函数
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.READ_CONTACTS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_CONTACTS), 1)
        } else {//有权限时,调用自定义函数，读取系统联系人
            readContacts()
        }
    }

    override//调用requestPermissions()后，系统弹出权限申请的对话框，选择后回调到下面这个函数，授权结果会封装到grantResults
    //grant授权
    fun onRequestPermissionsResult(requestCode: Int, @NonNull permissions: Array<String>, @NonNull grantResults: IntArray) {
        when (requestCode) {
            1 -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                readContacts()
            } else {
                Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show()
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

    /**
     * 底部菜单初始化
     */
    fun bottomInit() {
        act_main_rl_renzheng.setOnClickListener { bottomClick(0) }
        act_main_rb_renzheng.setOnClickListener { bottomClick(0) }
        act_main_rl_mine.setOnClickListener { bottomClick(1) }
        act_main_rb_mine.setOnClickListener { bottomClick(1) }
        bottomClick(0)
    }

    fun bottomClick(index: Int) {
        act_main_rb_renzheng.isChecked = false
        act_main_rb_mine.isChecked = false

        if (index == 0) {
            act_main_rb_renzheng.isChecked = true
        } else {
            act_main_rb_mine.isChecked = true
        }
        setFragment(index)
    }

    fun setFragment(index: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.act_main_container, getFragment(index), "${index}")
        transaction.commit()
    }

    var renzhengFragment: RenzhengFragment? = null
    var mineFragment: MineFragment? = null
    fun getFragment(index: Int): BaseFragment {
        if (index == 0) {
            if (renzhengFragment == null) {
                renzhengFragment = RenzhengFragment.newInstance("", "")
            }
            return renzhengFragment as BaseFragment
        } else {
            if (mineFragment == null) {
                mineFragment = MineFragment.newInstance("", "")
            }
            return mineFragment as BaseFragment
        }
    }
}