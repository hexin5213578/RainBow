package com.YiDian.RainBow.main.fragment.msg.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.YiDian.RainBow.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jpush.im.android.api.model.Conversation;

public class MsgRecordingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context context;
    private List<Conversation> list;


    public MsgRecordingAdapter(Context context) {

        this.context = context;
    }

    public void setData(List<Conversation> list){
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
        Conversation conversation = list.get(position);

        Glide.with(context).load(R.mipmap.headimg).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(((ViewHolder) holder).ivHeadimg);

        ((ViewHolder)holder).tvUsername.setText(conversation.getTitle());

        ((ViewHolder)holder).tvLastMsg.setText(conversation.getLatestText());

        long lastMsgDate = conversation.getLastMsgDate();
        //设置时间
        Date date = new Date(lastMsgDate);
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = sd.format(date);
        ((ViewHolder)holder).tvTime.setText(format);

        //设置未读消息数
        int unReadMsgCnt = conversation.getUnReadMsgCnt();
        if (unReadMsgCnt==0){
            ((ViewHolder)holder).weiducount.setVisibility(View.GONE);
        }else{
            ((ViewHolder)holder).weiducount.setText(unReadMsgCnt+"");
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
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
        @BindView(R.id.weiducount)
        TextView weiducount;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
