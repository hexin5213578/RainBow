package com.YiDian.RainBow.main.fragment.mine.adapter.draftviewholder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.custom.videoplayer.SampleCoverVideo;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MydraftViewHolderVideo extends RecyclerView.ViewHolder {

    private  Context context;
    private  View itemView;

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
    @BindView(R.id.video_player)
    SampleCoverVideo videoPlayer;

    public MydraftViewHolderVideo(Context context, @NonNull View itemView) {
        super(itemView);
        this.context = context;
        this.itemView = itemView;
        ButterKnife.bind(this,itemView);
    }

    public static MydraftViewHolderVideo createViewHolder(Context context,
                                                          ViewGroup parent, int layoutId) {
        View itemView = LayoutInflater.from(context).inflate(layoutId, parent,
                false);
        return new MydraftViewHolderVideo(context, itemView);
    }
}
