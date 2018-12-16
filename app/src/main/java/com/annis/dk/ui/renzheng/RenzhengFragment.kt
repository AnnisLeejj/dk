package com.annis.dk.ui.renzheng

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import com.annis.baselib.base.mvp.MVPFragment
import com.annis.dk.R
import com.annis.dk.base.DKConstant
import com.annis.dk.bean.UserEntity
import com.annis.dk.ui.TextActivity
import com.annis.dk.ui.authentication.alipay.AuthAlipayActivity
import com.annis.dk.ui.authentication.bank.AuthBankActivity
import com.annis.dk.ui.authentication.idCard.AuthIdcardActivity
import com.annis.dk.ui.authentication.operator.AuthoperatorActivity
import com.annis.dk.ui.emergency_contact.EmergencyContactActivity
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
        frag_renzheng_rlPactContainer.visibility = View.GONE
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
        userEntity?.phone?.let {
            presenter.uploadUserEntity(it)
        }
    }

    fun click() {
        act_bt_login.setOnClickListener {
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
                act_bt_login.isEnabled = true
                act_bt_login.setBackgroundResource(R.drawable.sp_bt_bg_primary_c)
            }
            false -> {
                act_bt_login.isEnabled = false
                act_bt_login.setBackgroundResource(R.drawable.sp_bt_bg_gray_c)
            }
        }
    }

    fun initByUserEntity(userEntity: UserEntity?) {
        userEntity?.let {
            initAuth(it.isChecIdentity == 1, it.isChecOperator == 1, it.isChecAlipay == 1, it.isChecBankCard == 1)
        }
    }

    fun initAuth(havID: Boolean, havOperator: Boolean, havAlipay: Boolean, havBank: Boolean) {
        if (havID) {
            item_tv_1_status.text = "已认证"
            item_tv_1_status.setTextColor(resources.getColor(R.color.green))
            item_img_1_status.setImageResource(R.drawable.renzheng_icon)
        } else {
            item_tv_1_status.text = "未认证"
            item_tv_1_status.setTextColor(resources.getColor(R.color.text_color_gray))
            item_img_1_status.setImageResource(R.drawable.a)
            renzheng_id.setOnClickListener {
                //身份证
                startActivity(AuthIdcardActivity::class.java)
            }
        }
        if (havOperator) {
            item_tv_2_status.text = "已认证"
            item_tv_2_status.setTextColor(resources.getColor(R.color.green))
            item_img_2_status.setImageResource(R.drawable.renzheng_icon)
        } else {
            item_tv_2_status.text = "未认证"
            item_tv_2_status.setTextColor(resources.getColor(R.color.text_color_gray))
            item_img_2_status.setImageResource(R.drawable.a)
            renzheng_operator.setOnClickListener {
                //紧急->运营商
                //这是紧急联系人
                var intent = Intent(activity, EmergencyContactActivity::class.java)
                var userEntity = DKConstant.getUserEntity()
                userEntity?.let {
                    intent.putExtra("account", it.phone)
                }
                startActivity(intent)
            }
        }
        if (havAlipay) {
            item_tv_3_status.text = "已认证"
            item_tv_3_status.setTextColor(resources.getColor(R.color.green))
            item_img_3_status.setImageResource(R.drawable.renzheng_icon)
        } else {
            item_tv_3_status.text = "未认证"
            item_tv_3_status.setTextColor(resources.getColor(R.color.text_color_gray))
            item_img_3_status.setImageResource(R.drawable.a)
            renzheng_alipay.setOnClickListener {
                //支付宝
                startActivity(AuthAlipayActivity::class.java)
            }
        }
        if (havBank) {
            item_tv_4_status.text = "已认证"
            item_tv_4_status.setTextColor(resources.getColor(R.color.green))
            item_img_4_status.setImageResource(R.drawable.renzheng_icon)
        } else {
            renzheng_bank.setOnClickListener {
                //银行
                startActivity(AuthBankActivity::class.java)
            }
        }
        //全部都有了可以显示了
        if (havID && havOperator && havAlipay && havBank) {
            act_bt_login.isEnabled = true
            renzheng_operator_agree.isChecked = true
            checkBox(true)

            showPact()
        } else {
            act_bt_login.isEnabled = false
        }
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