package com.YiDian.RainBow.imgroup.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.Common;
import com.YiDian.RainBow.custom.customDialog.CustomDialogCleanNotice;
import com.YiDian.RainBow.custom.image.CustomRoundAngleImageView;
import com.YiDian.RainBow.imgroup.bean.ChangeGroupHeadBean;
import com.YiDian.RainBow.imgroup.bean.GroupMemberTwoBean;
import com.YiDian.RainBow.notice.SystemNoticeActivity;
import com.YiDian.RainBow.notice.bean.CleanNoticeBean;
import com.YiDian.RainBow.utils.NetUtils;
import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class GroupMemberTwoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private final Context context;
    private final List<GroupMemberTwoBean.ObjectBean> list;
    private final int jgid;
    private GroupMemberTwoBean.ObjectBean bean;


    public GroupMemberTwoAdapter(Context context, List<GroupMemberTwoBean.ObjectBean> list, int jgid) {

        this.context = context;
        this.list = list;
        this.jgid = jgid;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_groupmember_manager, null);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        bean = list.get(position);
        Glide.with(context).load(bean.getHeadImg()).into(((ViewHolder)holder).ivHeadimg);

        ((ViewHolder)holder).tvName.setText(bean.getNickName()+"");

        if (bean.getUserId()== Integer.valueOf(Common.getUserId())){
            //判断为自己
            ((ViewHolder)holder).tvBt.setVisibility(View.GONE);
            String[] s1 = bean.getCreateTime().split(" ");
            ((ViewHolder)holder).tvTime.setText(s1[0]+"创建");
        }else{
            String[] s = bean.getCreateTime().split(" ");
            ((ViewHolder)holder).tvTime.setText(s[0]+"加入");
        }

        ((ViewHolder)holder).tvBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击事件
                //获取点击到的用户对象
                bean = list.get(position);

                CustomDialogCleanNotice.Builder builder = new CustomDialogCleanNotice.Builder(context);
                builder.setMessage("确定将他移出群组嘛?").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //发起极光移出群聊
                        List<String> userlist = new ArrayList<>();
                        userlist.add(String.valueOf(bean.getUserId()));

                        JMessageClient.removeGroupMembers(jgid, userlist, new BasicCallback() {
                            @Override
                            public void gotResult(int i, String s) {
                                Log.d("xxx","错误码为"+i+"原因为"+s);
                                if (i==0){
                                    //删除成功 清楚集合 发起服务器同步删除
                                    userlist.clear();
                                    NetUtils.getInstance().getApis()
                                            .doDeleteMember(bean.getGroupId(),bean.getUserId())
                                            .subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(new Observer<ChangeGroupHeadBean>() {
                                                @Override
                                                public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {

                                                }

                                                @Override
                                                public void onNext(@io.reactivex.annotations.NonNull ChangeGroupHeadBean changeGroupHeadBean) {
                                                    if (changeGroupHeadBean.getMsg().equals("删除成功")){
                                                        EventBus.getDefault().post("删除成功重新请求数据");
                                                        dialog.dismiss();
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
                            }
                        });

                    }
                });
                builder.setNegativeButton("取消",
                        new android.content.DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builder.create().show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_headimg)
        CustomRoundAngleImageView ivHeadimg;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_bt)
        TextView tvBt;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
