package com.YiDian.RainBow.notice.adapter;

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
import com.YiDian.RainBow.base.Common;
import com.YiDian.RainBow.main.fragment.find.adapter.AllLikeAdapter;
import com.YiDian.RainBow.main.fragment.home.activity.CommentDetailsActivity;
import com.YiDian.RainBow.main.fragment.home.activity.DynamicDetailsActivity;
import com.YiDian.RainBow.notice.bean.ClickNoticeBean;
import com.YiDian.RainBow.topic.SaveIntentMsgBean;
import com.YiDian.RainBow.user.PersonHomeActivity;
import com.YiDian.RainBow.utils.StringUtil;
import com.YiDian.RainBow.utils.TimeUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ClickNoticeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    private final List<ClickNoticeBean.ObjectBean> list;
    private ClickNoticeBean.ObjectBean listBean;
    private int userid;


    public ClickNoticeAdapter(Context context, List<ClickNoticeBean.ObjectBean> list) {

        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_clicknotice, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        userid = Integer.valueOf(Common.getUserId());
        listBean = list.get(position);
        //加载头像
        Glide.with(context).load(listBean.getHeadImg()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(((ViewHolder)holder).ivHeadimg);
        //设置名字
        ((ViewHolder)holder).tvName.setText(listBean.getNickName());
        //设置时间
        String createTime = listBean.getCreateTime();
        long l = TimeUtil.TimeToLong(createTime);
        String newChatTime = StringUtil.getNewChatTime(l);

        //设置时间
        ((ViewHolder)holder).tvTime.setText(newChatTime);
        //判断类型
        int msgType = listBean.getMsgType();
        if(msgType==4){
            ((ViewHolder)holder).tvData.setText("赞了您的动态");

            //跳转到动态详情页
            ((ViewHolder)holder).rlItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listBean = list.get(position);

                    int id = listBean.getContentId();

                    Intent intent = new Intent(context, DynamicDetailsActivity.class);
                    intent.putExtra("id", id);
                    context.startActivity(intent);
                }
            });

        }else if(msgType==3){
            ((ViewHolder)holder).tvData.setText("赞了您的评论");

            //跳转评论详情页
            ((ViewHolder)holder).rlItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listBean = list.get(position);
                    Intent intent = new Intent(context, CommentDetailsActivity.class);
                    intent.putExtra("id", listBean.getFirstComment());
                    context.startActivity(intent);
                }
            });
        }
        //跳转到用户主页
        ((ViewHolder)holder).ivHeadimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listBean = list.get(position);

                Intent intent = new Intent(context, PersonHomeActivity.class);
                SaveIntentMsgBean saveIntentMsgBean = new SaveIntentMsgBean();
                saveIntentMsgBean.setId(listBean.getUserMsgId());
                //2标记传入姓名  1标记传入id
                saveIntentMsgBean.setFlag(1);
                intent.putExtra("msg",saveIntentMsgBean);
                context.startActivity(intent);
            }
        });
        ((ViewHolder)holder).tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listBean = list.get(position);

                Intent intent = new Intent(context, PersonHomeActivity.class);
                SaveIntentMsgBean saveIntentMsgBean = new SaveIntentMsgBean();
                saveIntentMsgBean.setId(listBean.getUserMsgId());
                //2标记传入姓名  1标记传入id
                saveIntentMsgBean.setFlag(1);
                intent.putExtra("msg",saveIntentMsgBean);
                context.startActivity(intent);
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
        @BindView(R.id.tv_data)
        TextView tvData;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.rl_item)
        RelativeLayout rlItem;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
