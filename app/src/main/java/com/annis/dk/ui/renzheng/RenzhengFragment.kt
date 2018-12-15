package com.annis.dk.ui.renzheng

import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import com.annis.baselib.base.mvp.MVPFragment
import com.annis.dk.R
import com.annis.dk.base.DKConstant
import com.annis.dk.bean.UserEntity
import com.annis.dk.ui.authentication.alipay.AuthAlipayActivity
import com.annis.dk.ui.authentication.bank.AuthBankActivity
import com.annis.dk.ui.authentication.idCard.AuthIdcardActivity
import com.annis.dk.ui.authentication.operator.AuthoperatorActivity
import kotlinx.android.synthetic.main.fragment_renzheng.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class RenzhengFragment : MVPFragment<RenzhengPresenter>(), RenzhengView {
    override fun getPersenter(): RenzhengPresenter {
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
        userEntity?.let {
            initAuth(it.isChecIdentity == 1, it.isChecOperator == 1, it.isChecAlipay == 1, it.isChecBankCard == 1)
        }
    }

    fun initAuth(havID: Boolean, havOperator: Boolean, havAlipay: Boolean, havBank: Boolean) {
        if (havID) {
            item_tv_1_status.text = "已认证"
            item_tv_1_status.setTextColor(resources.getColor(R.color.colorPrimary))
            item_img_1_status.setImageResource(R.drawable.renzheng_icon)
        } else {
            renzheng_id.setOnClickListener {
                //身份证
                startActivity(AuthIdcardActivity::class.java)
            }
        }
        if (havOperator) {
            item_tv_2_status.text = "已认证"
            item_tv_2_status.setTextColor(resources.getColor(R.color.colorPrimary))
            item_img_2_status.setImageResource(R.drawable.renzheng_icon)
        } else {
            renzheng_operator.setOnClickListener {
                //运营商
                startActivity(AuthoperatorActivity::class.java)
            }
        }
        if (havAlipay) {
            item_tv_3_status.text = "已认证"
            item_tv_3_status.setTextColor(resources.getColor(R.color.colorPrimary))
            item_img_3_status.setImageResource(R.drawable.renzheng_icon)
        } else {
            renzheng_alipay.setOnClickListener {
                //支付宝
                startActivity(AuthAlipayActivity::class.java)
            }
        }
        if (havBank) {
            item_tv_4_status.text = "已认证"
            item_tv_4_status.setTextColor(resources.getColor(R.color.colorPrimary))
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
            showPact()
            act_bt_login.setOnClickListener {
                //提交贷款申请
            }
        } else {
            act_bt_login.isEnabled = false
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
                when (it) {
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