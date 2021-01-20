package com.YiDian.RainBow.main.fragment.mine.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.YiDian.RainBow.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GoldBalanceHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_gift)
    TextView tvGift;
    @BindView(R.id.tv_consumption)
    TextView tvConsumption;
    @BindView(R.id.tv_tv_consumption_count)
    TextView tvTvConsumptionCount;

    private View itemView;
    private Context context;
    public GoldBalanceHolder(Context context,View itemView) {
        super(itemView);
        this.context = context;
        this.itemView = itemView;
        ButterKnife.bind(this, itemView);
    }


    public static GoldBalanceHolder createViewHolder(Context context,
                                                     ViewGroup parent, int layoutId) {
        View itemView = LayoutInflater.from(context).inflate(layoutId, parent,
                false);
        return new GoldBalanceHolder(context, itemView);
    }
}
