package com.YiDian.RainBow.main.fragment.home.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.Common;
import com.YiDian.RainBow.main.fragment.home.activity.CommentDetailsActivity;
import com.YiDian.RainBow.main.fragment.home.bean.CommentBean;
import com.YiDian.RainBow.topic.SaveIntentMsgBean;
import com.YiDian.RainBow.user.PersonHomeActivity;
import com.YiDian.RainBow.utils.NetUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

import org.greenrobot.eventbus.EventBus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommentDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    private final List<CommentBean.ObjectBean> list;
    private CommentBean.ObjectBean listbean;
    private String userId;


    public CommentDetailsAdapter(Context context, List<CommentBean.ObjectBean> list) {

        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //回复一级评论
        if (viewType == 1) {
            View view = View.inflate(context, R.layout.item_commentdetailsone, null);
            return new ViewHolder(view);
        }
        //回复评论中的回复
        if (viewType == 2) {
            View view = View.inflate(context, R.layout.item_commentdetailstwo, null);
            return new ViewHolder(view);
        }
        return null;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        listbean = list.get(position);
        userId = Common.getUserId();
        CommentBean.ObjectBean.UserInfoBean userInfo = listbean.getUserInfo();
        //设置用户名
        ((CommentDetailsAdapter.ViewHolder)holder).tvName.setText(userInfo.getNickName());
        //加载头像
        Glide.with(context).load(userInfo.getHeadImg()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(((CommentDetailsAdapter.ViewHolder) holder).ivHeadimg);
        //跳转到用户信息页
        ((CommentDetailsAdapter.ViewHolder)holder).ivHeadimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listbean = list.get(position);

                Intent intent = new Intent(context, PersonHomeActivity.class);
                SaveIntentMsgBean saveIntentMsgBean = new SaveIntentMsgBean();
                saveIntentMsgBean.setId(listbean.getUserId());
                //2标记传入姓名  1标记传入id
                saveIntentMsgBean.setFlag(1);
                intent.putExtra("msg",saveIntentMsgBean);
                context.startActivity(intent);
            }
        });
        //获取发布时间
        String createTime = listbean.getCreateTime();
        if (createTime != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd HH:mm:ss", Locale.SIMPLIFIED_CHINESE);
            try {
                Date parse = sdf.parse(createTime);

                long time = parse.getTime();

                //获取当前时间
                long l = System.currentTimeMillis();
                //获取发布过的时长
                long difference = l - time;

                //时长大于12小时 显示日期
                if (difference > 43200000) {
                    ((CommentDetailsAdapter.ViewHolder) holder).tvTime.setText(createTime);
                }
                //时长小于12小时 展示时间
                if (difference > 1800000 && difference < 43200000) {
                    String[] s = createTime.split(" ");
                    ((CommentDetailsAdapter.ViewHolder) holder).tvTime.setText(s[1]);
                }
                if (difference > 1200000 && difference < 1800000) {
                    ((CommentDetailsAdapter.ViewHolder) holder).tvTime.setText("半小时前发布");
                }
                if (difference > 600000 && difference < 1200000) {
                    ((CommentDetailsAdapter.ViewHolder) holder).tvTime.setText("20分钟前发布");
                }
                if (difference > 300000 && difference < 600000) {
                    ((CommentDetailsAdapter.ViewHolder) holder).tvTime.setText("10分钟前发布");
                }
                if (difference > 240000 && difference < 300000) {
                    ((CommentDetailsAdapter.ViewHolder) holder).tvTime.setText("5分钟前发布");
                }
                if (difference > 180000 && difference < 240000) {
                    ((CommentDetailsAdapter.ViewHolder) holder).tvTime.setText("4分钟前发布");
                }
                if (difference > 120000 && difference < 180000) {
                    ((CommentDetailsAdapter.ViewHolder) holder).tvTime.setText("3分钟前发布");
                }
                if (difference > 60000 && difference < 120000) {
                    ((CommentDetailsAdapter.ViewHolder) holder).tvTime.setText("2分钟前发布");
                }
                if (difference < 60000) {
                    ((CommentDetailsAdapter.ViewHolder) holder).tvTime.setText("1分钟前发布");
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        //条目点击回复回复
        ((ViewHolder)holder).rlItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*int id = listbean.getId();
                //回复回复
                NetUtils.getInstance().getApis()
                        .doWriteComment(userId,id,)
                // TODO: 2020/12/21 0021 回复成功刷新界面

                EventBus.getDefault().post("刷新界面");*/
            }
        });

        int replyType = listbean.getReplyType();
        if(replyType==1){
            //评论内容
            ((CommentDetailsAdapter.ViewHolder)holder).tvContent.setText(listbean.getCommentInfo());
        }
        if(replyType==2){
            CommentBean.ObjectBean.BeCommentUserInfoBean beCommentUserInfo = listbean.getBeCommentUserInfo();
            String str = "回复"+beCommentUserInfo.getNickName()+":"+listbean.getCommentInfo();

            SpannableStringBuilder spannable = new SpannableStringBuilder(str);

            spannable.setSpan(new ForegroundColorSpan(R.color.start),2, str.indexOf(":"), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            ((CommentDetailsAdapter.ViewHolder)holder).tvContent.setText(spannable);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        int replyType = list.get(position).getReplyType();

        return replyType;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_headimg)
        ImageView ivHeadimg;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.rl_item)
        RelativeLayout rlItem;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
