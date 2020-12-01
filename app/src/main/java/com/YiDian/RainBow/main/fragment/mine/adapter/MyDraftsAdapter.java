package com.YiDian.RainBow.main.fragment.mine.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.custom.image.NineGridTestLayout;
import com.YiDian.RainBow.custom.videoplayer.SampleCoverVideo;
import com.YiDian.RainBow.main.fragment.mine.bean.SelectAllDraftsBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyDraftsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    private final List<SelectAllDraftsBean.ObjectBean.ListBean> list;


    public MyDraftsAdapter(Context context, List<SelectAllDraftsBean.ObjectBean.ListBean> list) {

        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //纯文本
        if (viewType == 1) {
            View view = View.inflate(context, R.layout.item_drafts_text, null);
            return new ViewHolder(view);
        }
        //纯图片
        if (viewType == 2) {
            View view = View.inflate(context, R.layout.item_drafts_img, null);
            return new ViewHolder(view);
        }
        //文本加图片
        if (viewType == 21) {
            View view = View.inflate(context, R.layout.item_drafts_text_img, null);
            return new ViewHolder(view);
        }
        //纯视频
        if (viewType == 3) {
            View view = View.inflate(context, R.layout.item_drafts_video, null);
            return new ViewHolder(view);
        }
        //视频加文本
        if (viewType == 31) {
            View view = View.inflate(context, R.layout.item_drafts_video_text, null);
            return new ViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        SelectAllDraftsBean.ObjectBean.ListBean listBean = list.get(position);
        int imgType = listBean.getImgType();
        //纯文本
        if (imgType == 1) {

        }
        //纯图片
        if (imgType == 2) {

        }
        //文本加图片
        if (imgType == 21) {

        }
        //纯视频
        if (imgType == 3) {

        }
        //文本加视频
        if (imgType == 31) {

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        int imgType = list.get(position).getImgType();
        //返回获取到的动态类型
        return imgType;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_headimg)
        ImageView ivHeadimg;
        @BindView(R.id.tv_age)
        TextView tvAge;
        @BindView(R.id.tv_username)
        TextView tvUsername;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.rl_xiala)
        RelativeLayout rlXiala;
        @BindView(R.id.tv_dynamic_text)
        TextView tvDynamicText;
        @BindView(R.id.rc_image)
        NineGridTestLayout rcImage;
        @BindView(R.id.video_player)
        SampleCoverVideo videoPlayer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
