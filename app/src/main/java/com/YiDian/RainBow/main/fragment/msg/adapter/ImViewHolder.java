package com.YiDian.RainBow.main.fragment.msg.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;


import com.YiDian.RainBow.R;
import com.YiDian.RainBow.custom.image.CustomRoundAngleImageView;
import com.YiDian.RainBow.custom.videoplayer.SampleCoverVideo;
import com.YiDian.RainBow.custom.videoplayer.SampleCoverVideoNoOpen;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImViewHolder extends RecyclerView.ViewHolder {


    public ImViewHolder(Context context, @NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.iv_headimg)
    ImageView ivHeadimg;
    @Nullable
    @BindView(R.id.tv_msg)
    TextView tvMsg;
    @Nullable
    @BindView(R.id.tv_duration)
    TextView tvDuration;
    @Nullable
    @BindView(R.id.ll_vocie)
    LinearLayout llVocie;
    @Nullable
    @BindView(R.id.iv_anim)
    ImageView ivAnim;
    @Nullable
    @BindView(R.id.iv_img)
    ImageView ivImg;
    @Nullable
    @BindView(R.id.videoView)
    VideoView videoView;
    @Nullable
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @Nullable
    @BindView(R.id.rl_location)
    RelativeLayout rlLocation;
    @Nullable
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @Nullable
    @BindView(R.id.iv_musicimg)
    CustomRoundAngleImageView ivMusicimg;
    @Nullable
    @BindView(R.id.iv_isplay)
    ImageView ivIsplay;
    @Nullable
    @BindView(R.id.rl_play)
    RelativeLayout rlPlay;
    @Nullable
    @BindView(R.id.tv_musicname)
    TextView tvMusicname;
    @Nullable
    @BindView(R.id.tv_author)
    TextView tvAuthor;
    @Nullable
    @BindView(R.id.rl_music)
    RelativeLayout rlMusic;
    @Nullable
    @BindView(R.id.rl_item)
    RelativeLayout rlItem;
    public static ImViewHolder createViewHolder(Context context,
                                              ViewGroup parent, int layoutId) {
        View itemView = LayoutInflater.from(context).inflate(layoutId, parent,
                false);
        return new ImViewHolder(context, itemView);
    }
}
