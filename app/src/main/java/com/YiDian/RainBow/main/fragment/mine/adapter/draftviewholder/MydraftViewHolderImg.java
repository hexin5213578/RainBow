package com.YiDian.RainBow.main.fragment.mine.adapter.draftviewholder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.custom.image.NineGridTestLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MydraftViewHolderImg extends RecyclerView.ViewHolder {

    private  Context context;
    private  View itemView;

    public MydraftViewHolderImg(Context context, View itemView) {
        super(itemView);
        this.context = context;
        this.itemView = itemView;
        ButterKnife.bind(this, itemView);

    }
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
    @BindView(R.id.rc_image)
    NineGridTestLayout rcImage;
    public static MydraftViewHolderImg createViewHolder(Context context,
                                                        ViewGroup parent, int layoutId) {
        View itemView = LayoutInflater.from(context).inflate(layoutId, parent,
                false);
        return new MydraftViewHolderImg(context, itemView);
    }
}
