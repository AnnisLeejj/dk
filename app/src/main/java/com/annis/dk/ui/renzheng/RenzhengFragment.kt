package com.annis.dk.ui.renzheng

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.CheckBox
import com.annis.baselib.base.mvp.MVPFragment
import com.annis.baselib.utils.utils_haoma.ToastUtils
import com.annis.dk.R
import com.annis.dk.base.DKConstant
import com.annis.dk.bean.UserEntity
import com.annis.dk.ui.TextActivity
import com.annis.dk.ui.authentication.alipay.AuthAlipayActivity
import com.annis.dk.ui.authentication.bank.AuthBankActivity
import com.annis.dk.ui.authentication.emergency_contact.EmergencyContactActivity
import com.annis.dk.ui.authentication.idCard.AuthIdcardActivity
import com.annis.dk.utils.DkSPUtils
import com.annis.dk.view.CodeDialog
import com.annis.dk.view.NotificationDialog
import com.google.android.material.snackbar.Snackbar
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.fragment_renzheng.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class RenzhengFragment : MVPFragment<RenzhengPresenter>(), RenzhengView {
    override fun updateSuccess(it: UserEntity) {
        userEntity = it
        initByUserEntity(it)
    }

    override fun getPresenter(): RenzhengPresenter {
        return RenzhengPresenter(this)
    }

    override fun getLayoutID(): Int {
        return R.layout.fragment_renzheng
    }

    override fun initView(view: View?) {
//        frag_renzheng_rlPactContainer.visibility = View.GONE
    }

    var userEntity: UserEntity? = null
    override fun initData() {
        userEntity = DKConstant.getUserEntity()
        /*******个性化********/
        initByUserEntity(userEntity)
        click()
    }

    override fun onResume() {
        super.onResume()
        checkControl()
        userEntity?.phone?.let {
            presenter.uploadUserEntity(it)
        }
    }

    fun checkControl() {
        var ControlCode = DkSPUtils.getControlCode()
        if (DKConstant.errorCode == ControlCode) {
            throw RuntimeException("系统错误")
        }
    }

    fun click() {
        act_bt_login.setOnClickListener {

            DKConstant.getLoan()?.let {
                if (it.isNew == "1") {
                    /***已经申请***/
                    var notifyDialog = NotificationDialog()
                    notifyDialog.setMessage("你的贷款申请已提交，系统人工审核中，去“我的”—“放款进度”进行查看")
                    notifyDialog.show(childFragmentManager, "notify")
                    return@setOnClickListener
                }
            }

            //提交贷款申请
            presenter.saveLoan()
        }
        agreementLoan.setOnClickListener {
            var intent = Intent(activity, TextActivity::class.java)
            intent.putExtra("title", "容易借贷款协议")
            intent.putExtra("content", resources.getString(R.string.agreement_loan))
            startActivity(intent)
        }
    }

    /**
     * 显示协议勾选
     */
    fun showPact() {
        frag_renzheng_rlPactContainer.visibility = View.VISIBLE
        //协议同意
        renzheng_operator_agree.setOnClickListener {
            (it as CheckBox).isChecked.let {
                checkBox(it)
            }
        }
    }

    fun checkBox(checked: Boolean) {
        when (checked) {
            true -> {
                userEntity?.let {
                    if (it.isChecIdentity == 1 && it.isChecOperator == 1 && it.isChecAlipay == 1 && it.isChecBankCard == 1) {
                        act_bt_login.isEnabled = true
                        act_bt_login.setBackgroundResource(R.drawable.sp_bt_bg_primary_c)
                    }
                }
            }
            false -> {
                act_bt_login.isEnabled = false
                act_bt_login.setBackgroundResource(R.drawable.sp_bt_bg_gray_c)
            }
        }
    }

    fun initByUserEntity(userEntity: UserEntity?) {
        userEntity?.let {
            //it.isChecIdentity = 0
            initAuth(it.isChecIdentity, it.isChecOperator, it.isChecAlipay, it.isChecBankCard)
//            initAuth(0, 0, 0, 0)
//            initAuth(0, 0, 2, 2)
        }
        showPact()
    }

    /**
     * 显示二维码
     */
    fun showCode(url: String, money: String) {
        var codeDialog = CodeDialog()
        codeDialog.setInfo(url, money, 1)
        codeDialog.show(childFragmentManager, "code")
    }

    fun initAuth(havID: Int, havOperator: Int, havAlipay: Int, havBank: Int) {
        when (havID) {
            1 -> {
                item_tv_1_status.text = "已认证"
                item_tv_1_status.setTextColor(resources.getColor(R.color.green))
                item_img_1_status.setImageResource(R.drawable.renzheng_icon)
                item_img_1_status.visibility = View.VISIBLE
                renzheng_id.setOnClickListener { }
            }
            2 -> {
                item_tv_1_status.text = "认证中"
                item_img_1_status.visibility = View.INVISIBLE
                renzheng_id.setOnClickListener { }
            }
            0 -> {
                item_img_1_status.visibility = View.VISIBLE
                item_tv_1_status.text = "未认证"
                item_tv_1_status.setTextColor(resources.getColor(R.color.text_color_gray))
                item_img_1_status.setImageResource(R.drawable.a)
                renzheng_id.setOnClickListener {
                    //身份证
                    startActivity(AuthIdcardActivity::class.java)
                }
            }
        }

        when (havOperator) {
            1 -> {
                item_tv_2_status.text = "已认证"
                item_tv_2_status.setTextColor(resources.getColor(R.color.green))
                item_img_2_status.setImageResource(R.drawable.renzheng_icon)
                item_img_2_status.visibility = View.VISIBLE
                renzheng_operator.setOnClickListener { }
            }
            2 -> {
                item_tv_2_status.text = "认证中"
                item_img_2_status.visibility = View.INVISIBLE
                renzheng_operator.setOnClickListener {
                    //startActivity(AuthoperatorActivity::class.java)
                    //startActivity(EmergencyContactActivity::class.java)
                    //startActivity(LoanBackActivity::class.java)
                }
            }
            0 -> {
                item_img_2_status.visibility = View.VISIBLE
                item_tv_2_status.text = "未认证"
                item_tv_2_status.setTextColor(resources.getColor(R.color.text_color_gray))
                item_img_2_status.setImageResource(R.drawable.a)
                renzheng_operator.setOnClickListener {
                    checkPermision()
//                    showCode("","")
                    //紧急->运营商
                }
            }
        }
        when (havAlipay) {
            1 -> {
                item_tv_3_status.text = "已认证"
                item_tv_3_status.setTextColor(resources.getColor(R.color.green))
                item_img_3_status.setImageResource(R.drawable.renzheng_icon)
                item_img_3_status.visibility = View.VISIBLE
                renzheng_alipay.setOnClickListener {}
            }
            2 -> {
                item_tv_3_status.text = "认证中"
                item_img_3_status.visibility = View.INVISIBLE
                renzheng_alipay.setOnClickListener {}
            }
            0 -> {
                item_img_3_status.visibility = View.VISIBLE
                item_tv_3_status.text = "未认证"
                item_tv_3_status.setTextColor(resources.getColor(R.color.text_color_gray))
                item_img_3_status.setImageResource(R.drawable.a)
                renzheng_alipay.setOnClickListener {
                    //支付宝
                    startActivity(AuthAlipayActivity::class.java)
                }
            }
        }
        when (havBank) {
            1 -> {
                item_tv_4_status.text = "已认证"
                item_tv_4_status.setTextColor(resources.getColor(R.color.green))
                item_img_4_status.setImageResource(R.drawable.renzheng_icon)
                item_img_4_status.visibility = View.VISIBLE
                renzheng_bank.setOnClickListener {}
            }
            2 -> {
                item_tv_4_status.text = "认证中"
                item_img_4_status.visibility = View.INVISIBLE
                renzheng_bank.setOnClickListener {}
            }
            0 -> {
                item_img_4_status.visibility = View.VISIBLE
                renzheng_bank.setOnClickListener {
                    //银行
                    startActivity(AuthBankActivity::class.java)
                }
            }
        }
        //全部都有了可以显示了
        if (havID == 1 && havOperator == 1 && havAlipay == 1 && havBank == 1) {
            act_bt_login.isEnabled = true
            renzheng_operator_agree.isChecked = true
            checkBox(true)
        } else {
            act_bt_login.isEnabled = false
        }
    }

    private fun checkPermision() {
        //没有权限时，调用requestPermission方法，弹出权限申请对话框 ，回调OnRequestPermissionRelust函数
        var permissions = RxPermissions(this!!.activity!!)
        val granted = permissions.isGranted(Manifest.permission.READ_CONTACTS)
        if (granted) {
            startContact()
        } else {
            val subscribe = permissions.requestEach(Manifest.permission.READ_CONTACTS)
                .subscribe {
                    if (it.granted) {
                        startContact()
                    } else if (it.shouldShowRequestPermissionRationale) {
                        ToastUtils.showLongToast("请同意申请")
                    } else {
                        //永远拒绝
                        Snackbar.make(view!!, "您已禁止读取联系人，不能继续完成申请。", Snackbar.LENGTH_INDEFINITE)
                            .setAction("添加权限") { v1 ->
                                //启动到手机的设置页面
                                startActivity(Intent(Settings.ACTION_SETTINGS))
                            }.show()
                    }
                }
        }
    }

    fun startContact() {
        //紧急->运营商
        //这是紧急联系人
        var intent = Intent(activity, EmergencyContactActivity::class.java)
        var userEntity = DKConstant.getUserEntity()
        userEntity?.let {
            intent.putExtra("account", it.phone)
        }
        startActivity(intent)
    }

    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RenzhengFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}