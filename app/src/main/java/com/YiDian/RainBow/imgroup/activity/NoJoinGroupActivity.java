package com.YiDian.RainBow.imgroup.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseAvtivity;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.base.Common;
import com.YiDian.RainBow.custom.customDialog.CustomDialogCleanNotice;
import com.YiDian.RainBow.custom.image.CustomRoundAngleImageView;
import com.YiDian.RainBow.imgroup.adapter.GroupMemberAdapter;
import com.YiDian.RainBow.imgroup.bean.ChangeGroupHeadBean;
import com.YiDian.RainBow.imgroup.bean.GroupMemberBean;
import com.YiDian.RainBow.imgroup.bean.GroupMsgBean;
import com.YiDian.RainBow.imgroup.bean.MyJoinGroupMsgBean;
import com.YiDian.RainBow.utils.NetUtils;
import com.bumptech.glide.Glide;
import com.leaf.library.StatusBarUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetGroupInfoCallback;
import cn.jpush.im.android.api.model.GroupInfo;
import cn.jpush.im.api.BasicCallback;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author hmy
 * 未加入群聊页
 */
public class NoJoinGroupActivity extends BaseAvtivity implements View.OnClickListener {
    @BindView(R.id.iv_bg)
    ImageView ivBg;
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.iv_img)
    CustomRoundAngleImageView ivImg;
    @BindView(R.id.tv_group_name)
    TextView tvGroupName;
    @BindView(R.id.tv_group_id)
    TextView tvGroupId;
    @BindView(R.id.tv_group_people_count)
    TextView tvGroupPeopleCount;
    @BindView(R.id.iv_lordimg)
    CustomRoundAngleImageView ivLordimg;
    @BindView(R.id.tv_lordname)
    TextView tvLordname;
    @BindView(R.id.rc_member)
    RecyclerView rcMember;
    @BindView(R.id.rl_seemore)
    LinearLayout rlSeemore;
    @BindView(R.id.tv_jianjie)
    TextView tvJianjie;
    @BindView(R.id.bt_join)
    Button btJoin;
    private String token;
    private int groupid;
    private int userId;
    private String groupName;
    private int jgGroupId;
    private String groupImg;
    private Intent intent;

    @Override
    protected int getResId() {
        return R.layout.activity_nojoin;
    }

    @Override
    protected void getData() {
        //设置背景透明
        StatusBarUtil.setTransparentForWindow(this);
        //设置状态栏字体黑色
        StatusBarUtil.setLightMode(this);

        //获取七牛云token
        token = Common.getToken();
        //获取当前登陆用户id
        userId = Integer.parseInt(Common.getUserId());

        Intent intent = getIntent();
        groupid = intent.getIntExtra("groupid", 0);
        Log.d("xxx", "群ID为" + groupid);

        llBack.setOnClickListener(this);
        rlSeemore.setOnClickListener(this);
        btJoin.setOnClickListener(this);

        //获取群信息
        getGroupMsg(groupid, userId);
        //获取群成员列表
        getGroupMember(groupid,1,25);


    }
    //获取群成员
    public void getGroupMember(int groupid,int page,int size){
        NetUtils.getInstance().getApis()
                .doGetGroupMember(groupid,page,size)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GroupMemberBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull GroupMemberBean groupMemberBean) {
                        if (groupMemberBean.getType().equals("OK")){
                            List<GroupMemberBean.ObjectBean.ListBean> list = groupMemberBean.getObject().getList();

                            //加载群主头像姓名
                            GroupMemberBean.ObjectBean.ListBean listBean = list.get(0);
                            tvLordname.setText(listBean.getNickName());
                            Glide.with(NoJoinGroupActivity.this).load(listBean.getHeadImg()).into(ivLordimg);

                            if (list!=null && list.size()>0){

                                GridLayoutManager gridLayoutManager = new GridLayoutManager(NoJoinGroupActivity.this, 7);
                                rcMember.setLayoutManager(gridLayoutManager);

                                GroupMemberAdapter groupMemberAdapter = new GroupMemberAdapter(NoJoinGroupActivity.this);
                                //设置数据源
                                groupMemberAdapter.setData(list);

                                rcMember.setAdapter(groupMemberAdapter);
                                //创建适配器
                            }
                            if (list.size()>21){
                                rlSeemore.setVisibility(View.VISIBLE);
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    //获取群信息
    public void getGroupMsg(int groupid, int userid) {
        NetUtils.getInstance().getApis()
                .doGetGroupMsg(groupid, userid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GroupMsgBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull GroupMsgBean groupMsgBean) {
                        if (groupMsgBean.getType().equals("OK")) {
                            GroupMsgBean.ObjectBean object = groupMsgBean.getObject();

                            groupImg = object.getGroupImg();
                            jgGroupId = object.getJgGroupId();
                            groupName = object.getGroupName();
                            //设置头像
                            Glide.with(NoJoinGroupActivity.this).load(groupImg).into(ivImg);
                            //设置背景
                            Glide.with(NoJoinGroupActivity.this).load(object.getBaseMap()).into(ivBg);
                            //群ID
                            tvGroupId.setText("ID:"+object.getId() + "");
                            //群人数
                            tvGroupPeopleCount.setText(object.getUserNum() + "");
                            //设置群名
                            tvGroupName.setText(groupName+"");

                            //设置简介

                            if (object.getGroupInfo().equals("还没有简介，快来设置吧")){
                                tvJianjie.setText("群主还没有设置简介");
                            }

                            //设置极光简介
                            JMessageClient.getGroupInfo(jgGroupId, new GetGroupInfoCallback() {
                                @Override
                                public void gotResult(int i, String s, GroupInfo groupInfo) {
                                    if (i==0){
                                        groupInfo.updateDescription(object.getGroupInfo(), new BasicCallback() {
                                            @Override
                                            public void gotResult(int i, String s) {
                                                if (i==0){
                                                    Log.d("xxx","极光描述修改成功");
                                                }
                                            }
                                        });
                                    }
                                }
                            });
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_back:
                finish();
                break;
                //查看更多群成员
            case R.id.rl_seemore:
                intent = new Intent(NoJoinGroupActivity.this,GroupMemberDetailsActivity.class);
                intent.putExtra("groupid",groupid);
                startActivity(intent);
                break;
                //申请加入
            case R.id.bt_join:
               CustomDialogCleanNotice.Builder builder = new CustomDialogCleanNotice.Builder(NoJoinGroupActivity.this);
                builder.setMessage("确定要申请加入该群组嘛?").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                         //先申请极光加入群组

                        //申请入群
                        JMessageClient.applyJoinGroup(jgGroupId, "", new BasicCallback() {
                            @Override
                            public void gotResult(int responseCode, String responseMessage) {
                                if (responseCode == 0) {
                                    //极光申请发出 发出本地审核
                                    NetUtils.getInstance().getApis()
                                            .doInserGroup(groupid,userId)
                                            .subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(new Observer<ChangeGroupHeadBean>() {
                                                @Override
                                                public void onSubscribe(@NonNull Disposable d) {

                                                }

                                                @Override
                                                public void onNext(@NonNull ChangeGroupHeadBean changeGroupHeadBean) {
                                                    dialog.dismiss();
                                                    if (changeGroupHeadBean.getType().equals("OK")){
                                                        Toast.makeText(NoJoinGroupActivity.this, "审请已发起", Toast.LENGTH_SHORT).show();
                                                    }
                                                }

                                                @Override
                                                public void onError(@NonNull Throwable e) {

                                                }

                                                @Override
                                                public void onComplete() {

                                                }
                                            });
                                }
                                else if(responseCode==856003){
                                    dialog.dismiss();
                                    Toast.makeText(NoJoinGroupActivity.this, "请勿重复发起申请", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Log.d("xxx", "apply failed. code :" + responseCode + " msg : " + responseMessage);
                                    Toast.makeText(NoJoinGroupActivity.this, "申请失败", Toast.LENGTH_SHORT).show();
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
                break;
            default:

                break;
        }
    }
}
