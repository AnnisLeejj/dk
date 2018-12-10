package com.annis.baselib.view.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.annis.baselib.R;
import com.annis.baselib.base.adapter.BaseViewHolder;
import com.annis.baselib.base.adapter.RvAdapter;
import com.annis.baselib.base.listener.ListItemClickListener;

import java.util.List;

public abstract class BottomSelectorDialog<T> extends Dialog {
    public abstract String getDescribe(T item, int position);

    public abstract void onClickItem(T item, int position);

    View view;
    RecyclerView recyclerView;
    RvAdapter<T> adapter;

    public BottomSelectorDialog(@NonNull Activity context) {
        super(context, R.style.BottomDialog);
        initRecycleView(context.getResources().getDisplayMetrics().widthPixels);
        setContentView(view);

        getWindow().setGravity(Gravity.BOTTOM);
        getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
    }

    private void initRecycleView(int width) {
        ListItemClickListener<T> listener = (item, position) -> {
            dismiss();
            onClickItem(item, position);
        };
        adapter = new RvAdapter<T>(getContext(), listener) {
            @Override
            public void bindView(BaseViewHolder holder, int position, T item) {
                ((TextView) holder.itemView).setText(getDescribe(item, position));
            }

            @Override
            public int getLayoutId() {
                return R.layout.item_single_line_center;
            }
        };
        view = LayoutInflater.from(getContext()).inflate(R.layout.part_recycleview, null);
        recyclerView = view.findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        ViewGroup.LayoutParams layoutParams = recyclerView.getLayoutParams();
        layoutParams.width = width;
        recyclerView.setLayoutParams(layoutParams);
    }

    public void setDatas(List<T> list) {
        adapter.setDatas(list);
    }
}
