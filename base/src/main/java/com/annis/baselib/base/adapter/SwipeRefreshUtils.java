package com.annis.baselib.base.adapter;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class SwipeRefreshUtils {
    SwipeRefreshLayout mSswipeRefreshLayout;

    public SwipeRefreshUtils(SwipeRefreshLayout swipeRefreshLayout, SwipeRefreschHelper helper) {
        mSswipeRefreshLayout = swipeRefreshLayout;
        mSswipeRefreshLayout.setOnRefreshListener(() -> {
                if(helper!=null){
                    helper.loadding();
                }
        });
    }
}
