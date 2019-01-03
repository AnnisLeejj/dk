package com.annis.dk.ui.mine.progress.success

import android.Manifest
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import com.annis.baselib.base.base.TitleBean
import com.annis.baselib.base.mvp.MVPActivty
import com.annis.baselib.utils.utils_haoma.ToastUtils
import com.annis.dk.R
import com.annis.dk.base.DKConstant
import com.annis.dk.ui.mine.progress.PaidActivity
import com.annis.dk.utils.DkSPUtils
import com.annis.dk.view.CodeDialog
import com.annis.dk.view.PaidDialog
import com.google.android.material.snackbar.Snackbar
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_success.*

class SuccessActivity : MVPActivty<SuccessPresenter>(), SuccessView {
    override fun getPresenter(): SuccessPresenter {
        return SuccessPresenter(this)
    }

    override fun getMyTitle(): TitleBean {
        return TitleBean("贷款进度").setBack(true)
    }

    override fun initViewAndListener() {
        success_edu_tv.text = intent.getStringExtra("edu")//额度
        fuwufei.text = intent.getStringExtra("fuwufei")//服务费
        act_success_remark.text = intent.getStringExtra("remark")//会员服务费提示
        tvWeixin.text = intent.getStringExtra("weixin")//微信号
        tvPhone.text = intent.getStringExtra("phone")//手机号
        click()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success)
    }

    /**
     * 显示二维码
     */
    private fun showCode(url: String, serviceCharge: String) {
        var codeDialog = CodeDialog()
        codeDialog.setInfo(url, serviceCharge, 2)
        codeDialog.show(supportFragmentManager, "code")
    }

    private fun click() {
        act_bt_pay.setOnClickListener {
            //支付
            var loan = DKConstant.getLoan() ?: let {
                showToast("没有查到贷款信息")
                return@setOnClickListener
            }
            //支付
            var user = DKConstant.getUserEntity() ?: let {
                showToast("没有查到用户信息")
                return@setOnClickListener
            }
            var url = when (user.limit) {
                1 -> loan.feeAddress1
                2 -> loan.feeAddress2
                3 -> loan.feeAddress3
                else -> null
            }
            url?.let {
                showCode(it, loan.serviceCharge)
            }
        }

        //我已支付
        act_bt_paid.setOnClickListener {
            var dialog = PaidDialog()
            dialog.setDismissListener(object : PaidDialog.Dismiss {
                override fun agree() {
                    dialog.dismiss()
                    getMyLoan()
                }

                override fun jujue() {
                    dialog.dismiss()
                }
            })
            dialog.show(supportFragmentManager, "notify")
        }
        act_bt_call.setOnClickListener {
            //打电话
            call(tvPhone.text.toString())
        }
        act_bt_copy.setOnClickListener {
            //复制微信
            // 获取系统剪贴板
            var clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

            // 创建一个剪贴数据集，包含一个普通文本数据条目（需要复制的数据）
            var clipData = ClipData.newPlainText("Label", tvWeixin.text.toString())

            // 把数据集设置（复制）到剪贴板
            clipboard.primaryClip = clipData
            ToastUtils.showLongToast("复制成功")

        }
    }

    private fun call(tel: String) {
        var permission = RxPermissions(this)
        permission.requestEach(Manifest.permission.CALL_PHONE).subscribe { r ->
            if (r.granted) {
                callOut(tel)
            } else if (r.shouldShowRequestPermissionRationale) {
                ToastUtils.showLongToast("请允许拨打电话。")
            } else {
                //永远拒绝
                Snackbar.make(window.decorView, "您已禁止拨打电话，请手动添加权限。", Snackbar.LENGTH_INDEFINITE)
                    .setAction("添加") {
                        //启动到手机的设置页面
                        startActivity(Intent(Settings.ACTION_SETTINGS))
                    }.show()
            }
        }
    }

    fun callOut(tel: String) {
        var intent = Intent(Intent.ACTION_CALL)
        var data = Uri.parse("tel:$tel")
        intent.data = data
        startActivity(intent)
    }

    fun getMyLoan() {
        presenter.updatelsPayCost(DkSPUtils.getUID(), DkSPUtils.getKey())
    }

    override fun nextActivity() {
        startActivity(PaidActivity::class.java)
    }
}