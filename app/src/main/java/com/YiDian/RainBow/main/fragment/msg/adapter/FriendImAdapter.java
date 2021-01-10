package com.YiDian.RainBow.main.fragment.msg.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.main.fragment.msg.bean.ImMsgBean;
import com.YiDian.RainBow.utils.StringUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jpush.im.android.api.model.Message;


public class FriendImAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ImMsgBean> list;
    private Context context;
    private ImMsgBean imMsgBean;
    private File file;
    private String newChatTime;

    public FriendImAdapter(Context context) {

        this.context = context;
    }

    public void setData(List<ImMsgBean> messages) {

        this.list = messages;
    }
    public void setHeadimg(File file){

        this.file = file;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View view = View.inflate(context, R.layout.item_im_left, null);
            return new ViewHolder(view);
        } else {
            View view = View.inflate(context, R.layout.item_im_right, null);
            return new ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        imMsgBean = list.get(position);
        if (position!=list.size()-1){
            ImMsgBean imMsgBean = list.get(position + 1);

            newChatTime = StringUtil.getNewChatTime(this.imMsgBean.getCreateTimeInMillis());
            String newChatTime2 = StringUtil.getNewChatTime(imMsgBean.getCreateTimeInMillis());

            if (newChatTime.length()<6){
                if (newChatTime.equals(newChatTime2)){
                    ((ViewHolder)holder).tvTime.setVisibility(View.GONE);
                }
            }

        }
        ((ViewHolder)holder).tvTime.setText(newChatTime);

        ((ViewHolder)holder).tvMsg.setText(this.imMsgBean.getContent().getText());

        if (this.imMsgBean.getDirect().equals("receive")) {
            Glide.with(context).load(file).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(((ViewHolder)holder).ivHeadimg);
        } else {
            Glide.with(context).load(R.mipmap.headimg).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(((ViewHolder)holder).ivHeadimg);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        imMsgBean = list.get(position);
        if (imMsgBean.getDirect().equals("receive")) {
            return 0;
        } else {
            return 1;
        }
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
