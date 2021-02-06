package com.YiDian.RainBow.main.fragment.find.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.YiDian.RainBow.R;
import com.YiDian.RainBow.custom.image.CustomRoundAngleImageView;
import com.YiDian.RainBow.main.fragment.find.activity.UserDetailsActivity;
import com.YiDian.RainBow.main.fragment.find.bean.AllUserInfoBean;
import com.bumptech.glide.Glide;
import com.fashare.stack_layout.StackLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * fst
 * 新卡片适配器
 * time：2021-2-6
 */
public class CardsAdapterFst extends StackLayout.Adapter<CardsAdapterFst.ViewHolder> {
    String TAG = "fst";
    Context context;
    ArrayList<AllUserInfoBean.ObjectBean.ListBean> list;

    public CardsAdapterFst(Context context, ArrayList<AllUserInfoBean.ObjectBean.ListBean> arrayList) {
        this.context = context;
        this.list = arrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //加载头像
        Glide.with(context).load(list.get(position).getHeadImg()).into(holder.ivImg);
        //设置距离
        int distance = list.get(position).getDistance();
        if (distance < 1000) {
            holder.tvDistance.setText(distance + "m");
        } else {
            String dis = String.valueOf(distance / 1000);
            holder.tvDistance.setText(dis + "Km");
        }
        //设置用户名
        holder.tvUsername.setText(list.get(position).getNickName());

        //设置角色
        if (list.get(position).getUserRole() == null) {
            holder.tvXingbie.setText("密");
        } else {
            if (list.get(position).getUserRole().equals("保密")) {
                holder.tvXingbie.setText("密");
            } else {
                holder.tvXingbie.setText(list.get(position).getUserRole());
            }
        }
        //设置个性签名
        Log.d(TAG, "onBindViewHolder: " +list.get(position).getExplains());
        if(list.get(position).getExplains()!=null && !list.get(position).getExplains().equals("")){
            holder.tvQianming.setText(list.get(position).getExplains());
        }


        //注册点击事件
        holder.rlItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: "+position);
                Intent intent = new Intent(context, UserDetailsActivity.class);
                AllUserInfoBean.ObjectBean.ListBean listBean = list.get(position);
                intent.putExtra("bean",listBean);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends StackLayout.ViewHolder {
        @BindView(R.id.iv_img)
        CustomRoundAngleImageView ivImg;
        @BindView(R.id.tv_username)
        TextView tvUsername;
        @BindView(R.id.tv_distance)
        TextView tvDistance;
        @BindView(R.id.tv_xingbie)
        TextView tvXingbie;
        @BindView(R.id.tv_qianming)
        TextView tvQianming;
        @BindView(R.id.rl_item)
        RelativeLayout rlItem;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
