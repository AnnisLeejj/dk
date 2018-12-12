package com.annis.dk.ui.renzheng

import android.os.Bundle
import android.view.View
import com.annis.baselib.base.mvp.MVPFragment
import com.annis.dk.R
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
        click()
    }

    override fun initData() {

    }

    fun click() {
        renzheng_id.setOnClickListener {
            //身份证
            startActivity(AuthIdcardActivity::class.java)
        }
        renzheng_operator.setOnClickListener {
            //运营商
            startActivity(AuthoperatorActivity::class.java)
        }
        renzheng_alipay.setOnClickListener {
            //支付宝
            startActivity(AuthAlipayActivity::class.java)
        }
        renzheng_bank.setOnClickListener {
            //银行
            startActivity(AuthBankActivity::class.java)
        }
    }


    // TODO: Rename and change types of parameters
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
        // TODO: Rename and change types and number of parameters
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
