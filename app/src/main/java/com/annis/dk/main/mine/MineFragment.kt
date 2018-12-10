package com.annis.dk.main.mine

import android.os.Bundle
import android.view.View
import com.annis.baselib.base.mvp.MVPFragment
import com.annis.dk.R

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
}
