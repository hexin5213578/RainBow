package com.YiDian.RainBow.friend.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.Common;
import com.YiDian.RainBow.friend.bean.InitGroupBean;
import com.YiDian.RainBow.friend.bean.RecommendGroupBean;
import com.YiDian.RainBow.imgroup.activity.LordMsgActivity;
import com.YiDian.RainBow.imgroup.activity.MemberMsgActivity;
import com.YiDian.RainBow.imgroup.activity.NoJoinGroupActivity;
import com.YiDian.RainBow.imgroup.adapter.GroupMyCreateAdapter;
import com.YiDian.RainBow.imgroup.bean.GroupMsgBean;
import com.YiDian.RainBow.imgroup.bean.MyJoinGroupMsgBean;
import com.YiDian.RainBow.utils.NetUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RecommendAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    private final List<RecommendGroupBean.ObjectBean> list;
    private RecommendGroupBean.ObjectBean listbean;
    private int userid;


    public RecommendAdapter(Context context, List<RecommendGroupBean.ObjectBean> list) {
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
        userid = Integer.valueOf(Common.getUserId());

        //设置头像
        if (listbean.getGroupImg()==null){
            Glide.with(context).load(R.mipmap.headimg3).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(((ViewHolder)holder).ivHeadimg);
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

        ((ViewHolder)holder).rlItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listbean = list.get(position);

                // TODO: 2021/1/14 0014 跳转到群聊页
                NetUtils.getInstance()
                        .getApis().doGetGroupMsg(listbean.getId(),userid)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<GroupMsgBean>() {
                            @Override
                            public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {

                            }

                            @Override
                            public void onNext(@io.reactivex.annotations.NonNull GroupMsgBean groupMsgBean) {
                                if (groupMsgBean.getObject().getGroupType()==1){
                                    //跳转到可管理群组主页
                                    //当前用户为群主
                                    Intent intent = new Intent(context, LordMsgActivity.class);

                                    intent.putExtra("groupid",listbean.getId());

                                    context.startActivity(intent);
                                }
                                if (groupMsgBean.getObject().getGroupType()==2){
                                    //跳转到不可管理群组主页
                                    //当前用户为群成员
                                    Intent intent = new Intent(context, MemberMsgActivity.class);

                                    intent.putExtra("groupid",listbean.getId());

                                    context.startActivity(intent);
                                }
                                if (groupMsgBean.getObject().getGroupType()==3){
                                    //跳转到加入群组页
                                    //当前用户并非群成员
                                    Intent intent = new Intent(context, NoJoinGroupActivity.class);
                                    intent.putExtra("groupid",listbean.getId());
                                    context.startActivity(intent);
                                }
                            }

                            @Override
                            public void onError(@io.reactivex.annotations.NonNull Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        });
            }
        });
    }

    @Override
    public int getItemCount() {
        if(list.size()>3){
            return 3;
        }else {
            return list.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_headimg)
        ImageView ivHeadimg;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_num)
        TextView tvNum;
        @BindView(R.id.ll_num)
        LinearLayout llNum;
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
