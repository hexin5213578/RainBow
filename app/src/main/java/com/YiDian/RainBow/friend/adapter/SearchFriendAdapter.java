package com.YiDian.RainBow.friend.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.Common;
import com.YiDian.RainBow.custom.customDialog.CustomDialogCancleFollow;
import com.YiDian.RainBow.dynamic.bean.SelectFriendOrGroupBean;
import com.YiDian.RainBow.main.fragment.find.adapter.MyLikeAdapter;
import com.YiDian.RainBow.main.fragment.home.bean.FollowBean;
import com.YiDian.RainBow.topic.SaveIntentMsgBean;
import com.YiDian.RainBow.user.PersonHomeActivity;
import com.YiDian.RainBow.utils.NetUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SearchFriendAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    private final List<SelectFriendOrGroupBean.ObjectBean.UserListBean> list;
    private SelectFriendOrGroupBean.ObjectBean.UserListBean bean;
    private int userid;


    public SearchFriendAdapter(Context context, List<SelectFriendOrGroupBean.ObjectBean.UserListBean> list) {

        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_iseen_person, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        userid = Integer.valueOf(Common.getUserId());
        bean = list.get(position);
        //设置用户名
        ((ViewHolder)holder).tvName.setText(bean.getNickName());
        //设置头像
        Glide.with(context).load(bean.getHeadImg()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(((ViewHolder) holder).ivHeadimg);
        //设置签名
        String explains = bean.getExplains();
        if (explains!=null){
            ((ViewHolder)holder).tvAutograph.setText(bean.getExplains());
        }else{
            ((ViewHolder)holder).tvAutograph.setText("还没有设置签名哦");
        }
        //设置时间
        ((ViewHolder)holder).tvTime.setVisibility(View.INVISIBLE);

        //判断角色
        String userRole = bean.getUserRole();
        if(userRole.equals("保密")){
            ((ViewHolder)holder).tvXingbie.setVisibility(View.GONE);
        }else{
            ((ViewHolder)holder).tvXingbie.setVisibility(View.VISIBLE);
            ((ViewHolder)holder).tvXingbie.setText(userRole);
        }
        //跳转到用户详情页
        ((ViewHolder)holder).ivHeadimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bean = list.get(position);

                Intent intent = new Intent(context, PersonHomeActivity.class);
                SaveIntentMsgBean saveIntentMsgBean = new SaveIntentMsgBean();
                saveIntentMsgBean.setId(bean.getId());
                //2标记传入姓名  1标记传入id
                saveIntentMsgBean.setFlag(1);
                intent.putExtra("msg",saveIntentMsgBean);
                context.startActivity(intent);
            }
        });
        //判断是否关注
        //判断是否关注
        if (bean.getAttestation()==0){
            //设置成未关注
            ((ViewHolder)holder).btGuanzhu.setText("关注");
            //未关注 发起关注
        }else if(bean.getAttestation()==1){
            //已关注 取消关注
            ((ViewHolder)holder).btGuanzhu.setText("已关注");
        }
        
        ((ViewHolder)holder).btGuanzhu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bean = list.get(position);
                if (bean.getAttestation()==0){
                    //未关注 发起关注
                    ((ViewHolder)holder).btGuanzhu.setEnabled(false);
                    NetUtils.getInstance().getApis()
                            .doFollow(userid, bean.getId())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<FollowBean>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(FollowBean followBean) {
                                    ((ViewHolder)holder).btGuanzhu.setEnabled(true);
                                    ((ViewHolder)holder).btGuanzhu.setText("已关注");
                                    bean.setAttestation(1);

                                    EventBus.getDefault().post("搜索好友刷新界面");
                                }

                                @Override
                                public void onError(Throwable e) {

                                }

                                @Override
                                public void onComplete() {

                                }
                            });
                }else if(bean.getAttestation()==1){
                    ((ViewHolder)holder).btGuanzhu.setEnabled(false);

                    CustomDialogCancleFollow.Builder builder = new CustomDialogCancleFollow.Builder(context);
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //已关注 取消关注
                            ((ViewHolder)holder).btGuanzhu.setEnabled(false);
                            dialog.dismiss();

                            NetUtils.getInstance().getApis()
                                    .doCancleFollow(userid, bean.getId())
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Observer<FollowBean>() {
                                        @Override
                                        public void onSubscribe(Disposable d) {

                                        }

                                        @Override
                                        public void onNext(FollowBean followBean) {
                                            ((ViewHolder)holder).btGuanzhu.setEnabled(true);
                                            ((ViewHolder)holder).btGuanzhu.setText("关注");
                                            bean.setAttestation(0);

                                            EventBus.getDefault().post("搜索好友刷新界面");
                                        }

                                        @Override
                                        public void onError(Throwable e) {

                                        }

                                        @Override
                                        public void onComplete() {

                                        }
                                    });
                        }
                    });
                    builder.setNegativeButton("取消",
                            new android.content.DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    builder.create().show();
                }
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
        @BindView(R.id.tv_xingbie)
        TextView tvXingbie;
        @BindView(R.id.rl_img)
        RelativeLayout rlImg;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_autograph)
        TextView tvAutograph;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.bt_guanzhu)
        Button btGuanzhu;
        @BindView(R.id.v1)
        View v1;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
