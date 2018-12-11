package com.annis.dk.ui.mine

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.annis.baselib.base.mvp.MVPFragment
import com.annis.dk.R
import com.annis.dk.ui.login.LoginActivity
import com.annis.dk.ui.mine.progress.FailedActivity
import com.annis.dk.ui.mine.progress.SuccessActivity
import com.annis.dk.ui.mine.progress.WaitingActivity
import kotlinx.android.synthetic.main.fragment_mine.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class MineFragment : MVPFragment<MinePersenter>(), MineView {
    override fun getPersenter(): MinePersenter {
        return MinePersenter(this)
    }

    override fun getLayoutID(): Int {
        return R.layout.fragment_mine
    }

    override fun initView(view: View?) {
        click()
    }

    override fun initData() {
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
        //银行卡管理
        bank_manage.setOnClickListener {
            
        }
    }
}
