package com.YiDian.RainBow.main.fragment.home.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.Common;
import com.YiDian.RainBow.main.fragment.home.activity.CommentDetailsActivity;
import com.YiDian.RainBow.main.fragment.home.activity.DynamicDetailsActivity;
import com.YiDian.RainBow.main.fragment.home.bean.CommentBean;
import com.YiDian.RainBow.main.fragment.home.bean.DianzanBean;
import com.YiDian.RainBow.topic.SaveIntentMsgBean;
import com.YiDian.RainBow.user.PersonHomeActivity;
import com.YiDian.RainBow.utils.KeyBoardUtils;
import com.YiDian.RainBow.utils.NetUtils;
import com.YiDian.RainBow.utils.StringUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private final Context context;
    private final List<CommentBean.ObjectBean> list;
    private int userId;
    private int id;
    private CommentBean.ObjectBean listBean;

    public CommentAdapter(Context context, List<CommentBean.ObjectBean> list) {

        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_firstcomment, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        listBean = list.get(position);
        CommentBean.ObjectBean.UserInfoBean userInfo = list.get(position).getUserInfo();
        //设置用户名
        ((ViewHolder) holder).tvName.setText(userInfo.getNickName());
        //加载头像
        Glide.with(context).load(userInfo.getHeadImg()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(((ViewHolder) holder).ivHeadimg);

        //跳转到用户信息页
        ((ViewHolder)holder).ivHeadimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listBean = list.get(position);

                Intent intent = new Intent(context, PersonHomeActivity.class);
                SaveIntentMsgBean saveIntentMsgBean = new SaveIntentMsgBean();
                saveIntentMsgBean.setId(listBean.getUserId());
                //2标记传入姓名  1标记传入id
                saveIntentMsgBean.setFlag(1);
                intent.putExtra("msg",saveIntentMsgBean);
                context.startActivity(intent);
            }
        });

        //用户ID
        userId = Integer.parseInt(Common.getUserId());
        //评论ID
        //获取发布时间
        String createTime = listBean.getCreateTime();
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
                if (difference > 1800000) {
                    String newChatTime = StringUtil.getNewChatTime(time);
                    ((ViewHolder) holder).tvTime.setText(newChatTime);
                }
                if (difference > 1200000 && difference < 1800000) {
                    ((ViewHolder) holder).tvTime.setText("半小时前发布");
                }
                if (difference > 600000 && difference < 1200000) {
                    ((ViewHolder) holder).tvTime.setText("20分钟前发布");
                }
                if (difference > 300000 && difference < 600000) {
                    ((ViewHolder) holder).tvTime.setText("10分钟前发布");
                }
                if (difference > 240000 && difference < 300000) {
                    ((ViewHolder) holder).tvTime.setText("5分钟前发布");
                }
                if (difference > 180000 && difference < 240000) {
                    ((ViewHolder) holder).tvTime.setText("4分钟前发布");
                }
                if (difference > 120000 && difference < 180000) {
                    ((ViewHolder) holder).tvTime.setText("3分钟前发布");
                }
                if (difference > 60000 && difference < 120000) {
                    ((ViewHolder) holder).tvTime.setText("2分钟前发布");
                }
                if (difference < 60000) {
                    ((ViewHolder) holder).tvTime.setText("1分钟前发布");
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        //评论内容
        ((ViewHolder)holder).tvContent.setText(listBean.getCommentInfo());

        //判断是否点赞
        if (listBean.isClick()) {
            ((ViewHolder) holder).ivDianzan.setImageResource(R.mipmap.dianzan);
        } else {
            ((ViewHolder) holder).ivDianzan.setImageResource(R.mipmap.weidianzan);
        }
        //点赞数设置
        int clickNum = listBean.getClickNum();
        if(clickNum<10000){
            ((ViewHolder)holder).tvDianzanCount.setText(listBean.getClickNum()+"");
        }else{
            String s = StringUtil.rawIntStr2IntStr(String.valueOf(clickNum));

            ((ViewHolder)holder).tvDianzanCount.setText(s);
        }
        ((ViewHolder)holder).rlDianzan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listBean = list.get(position);
                id = listBean.getId();

                if (listBean.isClick()==true) {
                    //取消点赞
                    //开始执行设置不可点击 防止多次点击发生冲突
                    ((ViewHolder) holder).rlDianzan.setEnabled(false);
                    NetUtils.getInstance().getApis()
                            .doCancleDianzan(2, id,userId)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<DianzanBean>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(DianzanBean dianzanBean) {
                                    //处理结束后恢复点击
                                    ((ViewHolder) holder).rlDianzan.setEnabled(true);

                                    if(dianzanBean.getObject().equals("删除成功")){
                                        //取消点赞成功
                                        ((ViewHolder) holder).ivDianzan.setImageResource(R.mipmap.weidianzan);
                                        listBean.setClick(false);

                                        //取消点赞成功数量加一
                                        String s = ((ViewHolder) holder).tvDianzanCount.getText().toString();
                                        if(!s.contains("w")){
                                            Integer integer = Integer.valueOf(s);

                                            integer -= 1;

                                            ((ViewHolder) holder).tvDianzanCount.setText(integer + "");
                                        }

                                    }
                                }

                                @Override
                                public void onError(Throwable e) {

                                }

                                @Override
                                public void onComplete() {

                                }
                            });
                } else if(listBean.isClick()==false){
                    //点赞
                    ((ViewHolder) holder).rlDianzan.setEnabled(false);

                    NetUtils.getInstance().getApis()
                            .doDianzan(userId, 2, id)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<DianzanBean>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(DianzanBean dianzanBean) {

                                    //处理结束后恢复点击
                                    ((ViewHolder) holder).rlDianzan.setEnabled(true);

                                    if (dianzanBean.getObject().equals("插入成功")) {
                                        //点赞成功
                                        ((ViewHolder) holder).ivDianzan.setImageResource(R.mipmap.dianzan);
                                        listBean.setClick(true);


                                        //点赞成功数量加一
                                        String s = ((ViewHolder) holder).tvDianzanCount.getText().toString();
                                        if(!s.contains("w")){
                                            Integer integer = Integer.valueOf(s);

                                            integer += 1;

                                            ((ViewHolder) holder).tvDianzanCount.setText(integer + "");
                                        }

                                    }else{
                                        ((ViewHolder) holder).rlDianzan.setEnabled(false);
                                        NetUtils.getInstance().getApis()
                                                .doCancleDianzan(2, id,userId)
                                                .subscribeOn(Schedulers.io())
                                                .observeOn(AndroidSchedulers.mainThread())
                                                .subscribe(new Observer<DianzanBean>() {
                                                    @Override
                                                    public void onSubscribe(Disposable d) {

                                                    }

                                                    @Override
                                                    public void onNext(DianzanBean dianzanBean) {
                                                        //处理结束后恢复点击
                                                        ((ViewHolder) holder).rlDianzan.setEnabled(true);

                                                        if(dianzanBean.getObject().equals("删除成功")){
                                                            //取消点赞成功
                                                            ((ViewHolder) holder).ivDianzan.setImageResource(R.mipmap.weidianzan);
                                                            listBean.setClick(false);

                                                            //取消点赞成功数量加一
                                                            String s = ((ViewHolder) holder).tvDianzanCount.getText().toString();
                                                            if(!s.contains("w")){
                                                                Integer integer = Integer.valueOf(s);

                                                                integer -= 1;

                                                                ((ViewHolder) holder).tvDianzanCount.setText(integer + "");
                                                            }

                                                        }
                                                    }

                                                    @Override
                                                    public void onError(Throwable e) {

                                                    }

                                                    @Override
                                                    public void onComplete() {

                                                    }
                                                });
                                    }
                                }

                                @Override
                                public void onError(Throwable e) {

                                }

                                @Override
                                public void onComplete() {

                                }
                            });

                }
            }
        });
        //设置共有回复数
        int commentNum = listBean.getCommentNum();
        if(commentNum==0){
            ((ViewHolder)holder).llReply.setVisibility(View.GONE);
        }else{
            ((ViewHolder)holder).llReply.setVisibility(View.VISIBLE);
            ((ViewHolder)holder).tvReplyCount.setText(commentNum+"条回复");
        }
        //跳转评论详情页
        ((ViewHolder)holder).llReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listBean = list.get(position);
                id = listBean.getId();
                Intent intent = new Intent(context, CommentDetailsActivity.class);
                intent.putExtra("id", CommentAdapter.this.id);
                context.startActivity(intent);
            }
        });
        ((ViewHolder)holder).rlItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listBean = list.get(position);
                id = listBean.getId();

                Intent intent = new Intent(context, CommentDetailsActivity.class);
                intent.putExtra("id",id);

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
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.tv_reply_count)
        TextView tvReplyCount;
        @BindView(R.id.ll_reply)
        LinearLayout llReply;
        @BindView(R.id.rl_item)
        RelativeLayout rlItem;
        @BindView(R.id.iv_dianzan)
        ImageView ivDianzan;
        @BindView(R.id.tv_dianzan_count)
        TextView tvDianzanCount;
        @BindView(R.id.rl_dianzan)
        LinearLayout rlDianzan;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
