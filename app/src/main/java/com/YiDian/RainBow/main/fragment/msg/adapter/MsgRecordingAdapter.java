package com.YiDian.RainBow.main.fragment.msg.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.login.activity.CompleteMsgActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MsgRecordingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    private final List<String> list;

    public MsgRecordingAdapter(Context context, List<String> list) {

        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_msg_recording, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Glide.with(context).load(R.mipmap.headimg).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(((ViewHolder)holder).ivHeadimg);
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_headimg)
        ImageView ivHeadimg;
        @BindView(R.id.tv_username)
        TextView tvUsername;
        @BindView(R.id.tv_last_msg)
        TextView tvLastMsg;
        @BindView(R.id.tv_time)
        TextView tvTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
