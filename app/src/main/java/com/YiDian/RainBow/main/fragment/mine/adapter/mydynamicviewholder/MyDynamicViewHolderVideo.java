package com.YiDian.RainBow.main.fragment.mine.adapter.mydynamicviewholder;

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
import com.YiDian.RainBow.custom.videoplayer.SampleCoverVideo;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyDynamicViewHolderVideo extends RecyclerView.ViewHolder {

    private  Context context;
    private  View itemView;

    public MyDynamicViewHolderVideo(Context context, View itemView) {
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
    @BindView(R.id.isattaction)
    ImageView isattaction;
    @BindView(R.id.tv_delete)
    TextView tvDelete;
    @BindView(R.id.video_player)
    SampleCoverVideo videoPlayer;
    @BindView(R.id.ll_huati)
    RelativeLayout llHuati;
    @BindView(R.id.iv_dianzan)
    ImageView ivDianzan;
    @BindView(R.id.tv_dianzan_count)
    TextView tvDianzanCount;
    @BindView(R.id.rl_dianzan)
    RelativeLayout rlDianzan;
    @BindView(R.id.iv_pinglun)
    ImageView ivPinglun;
    @BindView(R.id.tv_pinglun_count)
    TextView tvPinglunCount;
    @BindView(R.id.rl_pinglun)
    RelativeLayout rlPinglun;
    @BindView(R.id.iv_zhuanfa)
    ImageView ivZhuanfa;
    @BindView(R.id.zhuangfa)
    TextView zhuangfa;
    @BindView(R.id.rl_zhuanfa)
    RelativeLayout rlZhuanfa;
    @BindView(R.id.tv_distance)
    TextView tvDistance;
    @BindView(R.id.rl_item)
    RelativeLayout rlItem;
    @BindView(R.id.tv_topic)
    TextView tvTopic;
    public static MyDynamicViewHolderVideo createViewHolder(Context context,
                                                            ViewGroup parent, int layoutId) {
        View itemView = LayoutInflater.from(context).inflate(layoutId, parent,
                false);
        return new MyDynamicViewHolderVideo(context, itemView);
    }
}
