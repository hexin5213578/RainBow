package com.YiDian.RainBow.main.fragment.mine.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.main.fragment.mine.bean.ConsumeRecordBean;

import java.util.List;

public class GoldBalanceAdapter extends RecyclerView.Adapter<GoldBalanceHolder> {


    private GoldBalanceHolder viewHolder;
    private Context context;
    private List<ConsumeRecordBean.ObjectBean.PageInfoBean.ListBean> list;

    public GoldBalanceAdapter(Context context, List<ConsumeRecordBean.ObjectBean.PageInfoBean.ListBean> list) {
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
        if(list.get(position).getRecordType()==1){
            //收入
            holder.tvGift.setText("收入");
            holder.tvConsumption.setText(list.get(position).getCreateTime()+"");
            holder.tvTvConsumptionCount.setText("+"+list.get(position).getGoldNum());
        }else {
            //支出
            holder.tvGift.setText("支出");
            holder.tvConsumption.setText(list.get(position).getCreateTime()+"");
            holder.tvTvConsumptionCount.setText("-"+list.get(position).getGoldNum());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
