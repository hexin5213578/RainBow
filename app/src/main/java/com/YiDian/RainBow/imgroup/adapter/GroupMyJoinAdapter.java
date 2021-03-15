package com.YiDian.RainBow.imgroup.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.friend.bean.InitGroupBean;
import com.YiDian.RainBow.imgroup.activity.LordMsgActivity;
import com.YiDian.RainBow.imgroup.activity.MemberMsgActivity;
import com.YiDian.RainBow.imgroup.bean.MyCreateGroupMsgBean;
import com.YiDian.RainBow.imgroup.bean.MyJoinGroupMsgBean;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GroupMyJoinAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context context;
    private final List<InitGroupBean.ObjectBean.GroupJiaRuBean> list;
    private InitGroupBean.ObjectBean.GroupJiaRuBean listbean;

    public GroupMyJoinAdapter(Context context, List<InitGroupBean.ObjectBean.GroupJiaRuBean> list) {

        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_mycreat_groupmsg, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        listbean = list.get(position);
        //设置头像
        if (listbean.getGroupImg()==null){
            Glide.with(context).load(R.mipmap.groupimg).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(((ViewHolder)holder).ivHeadimg);
        }else{
            Glide.with(context).load(listbean.getGroupImg()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(((ViewHolder)holder).ivHeadimg);
        }
        //设置姓名
        ((ViewHolder)holder).tvName.setText(listbean.getGroupName());
        //设置签名
        if (listbean.getGroupInfo()==null){
            ((ViewHolder)holder).tvAutograph.setText("设置一个默认的签名哦");
        }else{
            ((ViewHolder)holder).tvAutograph.setText(listbean.getGroupInfo());
        }
        ((ViewHolder)holder).tvNum.setText(listbean.getUserNum()+"");

        ((ViewHolder)holder).ivHeadimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //跳转到不可管理群组主页
                //当前用户为群成员
                Intent intent = new Intent(context, MemberMsgActivity.class);

                intent.putExtra("groupid",list.get(position).getGroupId());

                context.startActivity(intent);
            }
        });

        ((ViewHolder)holder).rlItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2021/1/14 0014 跳转到群聊页


            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_headimg)
        ImageView ivHeadimg;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_num)
        TextView tvNum;
        @BindView(R.id.tv_autograph)
        TextView tvAutograph;
        @BindView(R.id.rl_item)
        RelativeLayout rlItem;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
