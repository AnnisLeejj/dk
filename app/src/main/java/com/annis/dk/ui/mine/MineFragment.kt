package com.annis.dk.ui.mine

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.annis.baselib.base.mvp.MVPFragment
import com.annis.baselib.utils.picasso.PicassoUtil
import com.annis.dk.R
import com.annis.dk.ui.ComstorServiceActivity
import com.annis.dk.ui.emergency_contact.EmergencyContactActivity
import com.annis.dk.ui.login.LoginActivity
import com.annis.dk.ui.mine.bankmanage.BankManageActivity
import com.annis.dk.ui.mine.mineLoans.MyLoansActivity
import com.annis.dk.ui.mine.progress.FailedActivity
import com.annis.dk.ui.mine.progress.SuccessActivity
import com.annis.dk.ui.mine.progress.WaitingActivity
import com.annis.dk.utils.DkSPUtils
import kotlinx.android.synthetic.main.fragment_mine.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class MineFragment : MVPFragment<MinePresenter>(), MineView {

    override fun getPersenter(): MinePresenter {
        return MinePresenter(this)
    }

    override fun getLayoutID(): Int {
        return R.layout.fragment_mine
    }

    override fun initView(view: View?) {
        var url = URL
        var count = "158232681500"
        //account
        frag_mine_count.text = count
        //加载头像
        PicassoUtil.loadImageCircle(activity, url, R.drawable.touxiang, frag_mine_header)
        click()

    }

    override fun initData() {
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
        const val URL = "http://img1.touxiang.cn/uploads/20130820/20-024619_267.jpg"
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MineFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    var applyProgress = 2
    fun click() {
        act_bt_outlogin.setOnClickListener {
            startActivity(LoginActivity::class.java)
            DkSPUtils.saveLogin(false)
            activity!!.finish()
        }
        rl_progress.setOnClickListener {
            when (applyProgress) {
                0 -> startActivity(WaitingActivity::class.java)
                1 -> {
                    var intent = Intent(activity, FailedActivity::class.java)
                    intent.putExtra("reason", "失败原因:asfasvvadfa efaefadvadsadsf")
                    startActivity(intent)
                }
                2 -> {
                    var intent = Intent(activity, SuccessActivity::class.java)
                    intent.putExtra("edu", "50000")//额度
                    intent.putExtra("fuwufei", "会员服务费:55元")//服务费
                    intent.putExtra("remark", resources.getString(R.string.daikuan_remark))//服务费提示
                    intent.putExtra("weixin", "castle0905")//微信号
                    intent.putExtra("phone", "15600001111")//手机号
                    startActivity(intent)
                }
            }
        }
        //我的贷款
        mine_loan.setOnClickListener {
            var intent = Intent(activity, MyLoansActivity::class.java)
            intent.putExtra("edu", "50000")//额度
            intent.putExtra("remark", resources.getString(R.string.daikuan_remark))//服务费提示
            intent.putExtra("weixin", "castle0905")//微信号
            intent.putExtra("phone", "15600001111")//手机号
            startActivity(intent)
        }
        //客服
        kefu.setOnClickListener {
            var intent = Intent(activity, ComstorServiceActivity::class.java)
            intent.putExtra("weixin", "castle0905")//微信号
            intent.putExtra("phone", "15600001111")//手机号
            startActivity(intent)
        }
        //银行卡管理
        bank_manage.setOnClickListener {
            startActivity(BankManageActivity::class.java)
        }
        //帮助中心
        help_center.setOnClickListener {
            //这是紧急联系人
            var intent = Intent(activity,EmergencyContactActivity::class.java)
            intent.putExtra("account","15823680000")
            startActivity(intent)
        }
    }
}
