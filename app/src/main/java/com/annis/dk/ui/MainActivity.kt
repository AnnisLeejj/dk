package com.annis.dk.ui

import android.os.Bundle
import com.annis.baselib.base.base.BaseFragment
import com.annis.baselib.base.base.TitleBean
import com.annis.baselib.base.mvp.BaseView
import com.annis.baselib.base.mvp.MVPActivty
import com.annis.dk.R
import com.annis.dk.base.DKPresenter
import com.annis.dk.ui.mine.MineFragment
import com.annis.dk.ui.renzheng.RenzhengFragment
import com.annis.dk.view.CodeDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : MVPActivty<DKPresenter<BaseView>>(), BaseView {
    override fun getMyTitle(): TitleBean? {
        return null
    }

    override fun getPresenter(): DKPresenter<BaseView> {
        return DKPresenter(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun initViewAndListener() {
        bottomInit()

        presenter.getControlCode()
       // showCode("http://","2342134");
    }

    /**
     * 底部菜单初始化
     */
    private fun bottomInit() {
        act_main_rl_renzheng.setOnClickListener { bottomClick(0) }
        act_main_rb_renzheng.setOnClickListener { bottomClick(0) }
        act_main_rl_mine.setOnClickListener { bottomClick(1) }
        act_main_rb_mine.setOnClickListener { bottomClick(1) }
        bottomClick(0)
    }

    private fun bottomClick(index: Int) {
        act_main_rb_renzheng.isChecked = false
        act_main_rb_mine.isChecked = false

        if (index == 0) {
            act_main_rb_renzheng.isChecked = true
        } else {
            act_main_rb_mine.isChecked = true
        }
        setFragment(index)
    }

    private fun setFragment(index: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.act_main_container, getFragment(index), "${index}")
        transaction.commit()
    }

    private var renzhengFragment: RenzhengFragment? = null
    private var mineFragment: MineFragment? = null
    private fun getFragment(index: Int): BaseFragment {
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