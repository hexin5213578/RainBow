package com.YiDian.RainBow.notice.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.notice.bean.NoticeMsgBean;
import com.YiDian.RainBow.utils.StringUtil;
import com.YiDian.RainBow.utils.TimeUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SystemNoticeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    private final List<NoticeMsgBean.ObjectBean> list;


    public SystemNoticeAdapter(Context context, List<NoticeMsgBean.ObjectBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_systemnotice, null);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        NoticeMsgBean.ObjectBean bean = list.get(position);

        //加载头像
        Glide.with(context).load(bean.getHeadImg()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(((ViewHolder)holder).ivHeadimg);
        //设置文本

        ((ViewHolder)holder).tvMsg.setText(bean.getMsgContent());
        //设置时间
        String createTime = bean.getCreateTime();
        long l = TimeUtil.TimeToLong(createTime);
        String newChatTime = StringUtil.getNewChatTime(l);

        //设置时间
        ((ViewHolder)holder).tvTime.setText(newChatTime);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.iv_headimg)
        ImageView ivHeadimg;
        @BindView(R.id.tv_msg)
        TextView tvMsg;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
