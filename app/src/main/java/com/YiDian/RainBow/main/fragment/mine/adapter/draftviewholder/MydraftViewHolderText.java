package com.YiDian.RainBow.main.fragment.mine.adapter.draftviewholder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.YiDian.RainBow.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MydraftViewHolderText extends RecyclerView.ViewHolder {

    @BindView(R.id.iv_headimg)
    ImageView ivHeadimg;
    @BindView(R.id.tv_age)
    TextView tvAge;
    @BindView(R.id.rl_head)
    RelativeLayout rlHead;
    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.iv_isatt)
    ImageView ivIsatt;
    @BindView(R.id.rl_xiala)
    RelativeLayout rlXiala;
    @BindView(R.id.tv_dynamic_text)
    TextView tvDynamicText;

    private  Context context;
    private  View itemView;

    public MydraftViewHolderText(Context context, View itemView) {
        super(itemView);
        this.context = context;
        this.itemView = itemView;
        ButterKnife.bind(this, itemView);
    }



    public static MydraftViewHolderText createViewHolder(Context context,
                                                         ViewGroup parent, int layoutId) {
        View itemView = LayoutInflater.from(context).inflate(layoutId, parent,
                false);
        return new MydraftViewHolderText(context, itemView);
    }
}
