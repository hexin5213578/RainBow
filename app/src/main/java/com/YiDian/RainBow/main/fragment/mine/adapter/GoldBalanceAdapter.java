package com.YiDian.RainBow.main.fragment.mine.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.YiDian.RainBow.R;

import java.util.List;

import butterknife.BindView;

public class GoldBalanceAdapter extends RecyclerView.Adapter<GoldBalanceHolder> {


    private GoldBalanceHolder viewHolder;
    private Context context;
    private List list;

    public GoldBalanceAdapter(Context context, List list) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public GoldBalanceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        viewHolder = GoldBalanceHolder.createViewHolder(context, parent, R.layout.item_balance_text);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GoldBalanceHolder holder, int position) {
        //绑定数据
        //holder.tvGift
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
