package com.annis.dk.ui.mine

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.annis.baselib.BuildConfig
import com.annis.baselib.base.mvp.MVPFragment
import com.annis.baselib.utils.picasso.PicassoUtil
import com.annis.dk.R
import com.annis.dk.base.DKConstant
import com.annis.dk.bean.LoanInfo
import com.annis.dk.bean.UserEntity
import com.annis.dk.bean.WebSite
import com.annis.dk.ui.ComstorServiceActivity
import com.annis.dk.ui.TextActivity
import com.annis.dk.ui.login.LoginActivity
import com.annis.dk.ui.mine.bankmanage.BankManageActivity
import com.annis.dk.ui.mine.mineLoans.MyLoansActivity
import com.annis.dk.ui.mine.progress.FailedActivity
import com.annis.dk.ui.mine.progress.SuccessActivity
import com.annis.dk.ui.mine.progress.WaitingActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_mine.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class MineFragment : MVPFragment<MinePresenter>(), MineView {
    override fun updateSuccess(it: UserEntity) {
        userEntity = it
        reloadByUser(it)
    }

    override fun reloadLoan(it: LoanInfo) {
        loanInfo = it
    }

    override fun getPresenter(): MinePresenter {
        return MinePresenter(this)
    }

    override fun getLayoutID(): Int {
        return R.layout.fragment_mine
    }

    override fun initView(view: View?) {
        click()
    }

    /**
     * 根据数据初始化界面
     */
    var userEntity: UserEntity? = null
    var webSite: WebSite? = null
    var loanInfo: LoanInfo? = null

    override fun initData() {
        Gson().toJson(null)
        loanInfo = DKConstant.getLoan()
        webSite = DKConstant.getWebsite()

        userEntity = DKConstant.getUserEntity()
        reloadByUser(userEntity)
    }

    fun reloadByUser(userEntity: UserEntity?) {
        userEntity?.let {
            //account
            frag_mine_count.text = it.phone
            //加载头像
            it.userHead?.let { it ->
                PicassoUtil.loadImageCircle(
                    activity,
                    BuildConfig.IP + it,
                    R.drawable.touxiang,
                    frag_mine_header
                )
            }
            //认证状态
            if (it.authAll()) {
                mine_auth_status.text = "已认证"
                mine_auth_status.setBackgroundResource(R.drawable.sp_bt_bg_green)
            } else {
                mine_auth_status.text = "未认证"
                mine_auth_status.setBackgroundResource(R.drawable.weirenzheng)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.updateAll()
        userEntity?.phone?.let {
            presenter.uploadUserEntity(it)
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
            MineFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    fun click() {
        act_bt_outlogin.setOnClickListener {
            DKConstant.clear()
            startActivity(LoginActivity::class.java)
            activity!!.finish()
        }
        rl_progress.setOnClickListener {
            loanInfo?.let {
                if (it.isNew == "0") {
                    showToast("您未申请贷款")
                    return@setOnClickListener
                }
                when (it.isPass) {
                    "0" -> startActivity(WaitingActivity::class.java)
                    "1" -> {
                        var intent = Intent(activity, FailedActivity::class.java)
                        intent.putExtra("reason", "失败原因:${it.resultInfo}")
                        startActivity(intent)
                    }
                    "2" -> {
                        var intent = Intent(activity, SuccessActivity::class.java)
                        intent.putExtra("edu", "${it.loanAmount}")//额度
                        intent.putExtra("fuwufei", "会员服务费:${it.serviceCharge}元")//服务费
                        intent.putExtra("remark", resources.getString(R.string.daikuan_remark))//服务费提示
                        webSite?.let {
                            intent.putExtra("weixin", it.csWeChat)//微信号
                            intent.putExtra("phone", it.csTelephone)//手机号
                        }
                        startActivity(intent)
                    }
                }
            }
        }
        //我的贷款
        mine_loan.setOnClickListener {
            loanInfo?.let { it ->
                if (it.isNew == "0") {
                    showToast("您未申请贷款")
                    return@setOnClickListener
                }
                if (it.mloan == "0") {
                    showToast("您的贷款未发放")
                    return@setOnClickListener
                }
                var intent = Intent(activity, MyLoansActivity::class.java)
                intent.putExtra("edu", it.loanAmount)//金额
                intent.putExtra("remark", resources.getString(R.string.daikuan_remark))//服务费提示
                webSite?.let {
                    intent.putExtra("weixin", it.csWeChat)//微信号
                    intent.putExtra("phone", it.csTelephone)//手机号
                }
                startActivity(intent)
            }

            loanInfo ?: let {
                showToast("未查询到您的贷款信息")
            }
        }
        //客服
        kefu.setOnClickListener {
            var intent = Intent(activity, ComstorServiceActivity::class.java)
            webSite?.let {
                intent.putExtra("weixin", it.csWeChat)//微信号
                intent.putExtra("phone", it.csTelephone)//手机号
            }
            startActivity(intent)
        }
        //银行卡管理
        bank_manage.setOnClickListener {
            DKConstant.getUserEntity()?.isChecBankCard?.let { it ->
                if (it == 1) {
                    startActivity(BankManageActivity::class.java)
                } else {
                    showToast("您尚未绑定银行卡")
                }
            }
        }
        //帮助中心
        help_center.setOnClickListener {
            var intent = Intent(activity, TextActivity::class.java)
            intent.putExtra("title", "帮助中心")
            intent.putExtra("content", resources.getString(R.string.help))
            startActivity(intent)
        }
    }
}