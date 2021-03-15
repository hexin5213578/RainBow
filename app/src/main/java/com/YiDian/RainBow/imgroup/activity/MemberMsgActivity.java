package com.YiDian.RainBow.imgroup.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import com.YiDian.RainBow.custom.loading.CustomDialog;
import com.YiDian.RainBow.imgroup.adapter.GroupMemberAdapter;
import com.YiDian.RainBow.imgroup.bean.ChangeGroupHeadBean;
import com.YiDian.RainBow.imgroup.bean.GroupMemberBean;
import com.YiDian.RainBow.imgroup.bean.GroupMsgBean;
import com.YiDian.RainBow.imgroup.bean.SaveIdAndHeadImgBean;
import com.YiDian.RainBow.main.fragment.mine.activity.GroupQrCodeActivity;
import com.YiDian.RainBow.utils.NetUtils;
import com.bumptech.glide.Glide;
import com.leaf.library.StatusBarUtil;

import org.greenrobot.eventbus.EventBus;

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
 * 群成员查看群信息
 *
 * @author hmy
 */
public class MemberMsgActivity extends BaseAvtivity implements View.OnClickListener {
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
    @BindView(R.id.rc_member)
    RecyclerView rcMember;
    @BindView(R.id.rl_seemore)
    LinearLayout rlSeemore;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_gonggao)
    TextView tvGonggao;
    @BindView(R.id.rl_change_gonggao)
    RelativeLayout rlChangeGonggao;
    @BindView(R.id.tv_jianjie)
    TextView tvJianjie;
    @BindView(R.id.rl_qunzu_code)
    RelativeLayout rlQunzuCode;
    @BindView(R.id.rl_clean)
    RelativeLayout rlClean;
    @BindView(R.id.bt_disband)
    Button btDisband;
    private Intent intent;
    private int groupid;
    private String groupImg;
    private int jgGroupId;
    private int userId;
    private String token;
    private String groupName;
    private SaveIdAndHeadImgBean saveIdAndHeadImgBean;
    private CustomDialogCleanNotice.Builder builder;

    @Override
    protected int getResId() {
        return R.layout.activity_membermsg;
    }

    @Override
    protected void getData() {
        //设置背景透明
        StatusBarUtil.setTransparentForWindow(this);
        //设置状态栏字体黑色
        StatusBarUtil.setLightMode(this);




        //获取当前登陆用户id
        userId = Integer.parseInt(Common.getUserId());

        //获取七牛云token
        token = Common.getToken();

        Intent intent = getIntent();
        groupid = intent.getIntExtra("groupid", 0);
        Log.d("xxx", "群ID为" + groupid);

        llBack.setOnClickListener(this);
        rlSeemore.setOnClickListener(this);
        rlChangeGonggao.setOnClickListener(this);
        rlQunzuCode.setOnClickListener(this);
        rlClean.setOnClickListener(this);
        btDisband.setOnClickListener(this);



        //获取群信息
        getGroupMsg(groupid, userId);
        //获取群成员列表
        getGroupMember(groupid,1,25);

    }
    @Override
    protected void onRestart() {
        super.onRestart();
        //获取群信息
        getGroupMsg(groupid, userId);
        //获取群成员列表
        getGroupMember(groupid,1,25);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_back:
                finish();
                break;
                //查看更多群成员
            case R.id.rl_seemore:
                intent = new Intent(MemberMsgActivity.this,GroupMemberDetailsActivity.class);
                intent.putExtra("groupid",groupid);
                startActivity(intent);
                break;
                //查看群公告
            case R.id.rl_change_gonggao:

                break;
                //查看群二维码
            case R.id.rl_qunzu_code:
                Intent intent = new Intent(MemberMsgActivity.this, GroupQrCodeActivity.class);
                saveIdAndHeadImgBean = new SaveIdAndHeadImgBean();
                saveIdAndHeadImgBean.setId(groupid);
                saveIdAndHeadImgBean.setName(groupName);
                saveIdAndHeadImgBean.setHeadimg(groupImg);
                intent.putExtra("msg", saveIdAndHeadImgBean);
                startActivity(intent);
                break;
                //清空聊天记录
            case R.id.rl_clean:
                builder = new CustomDialogCleanNotice.Builder(MemberMsgActivity.this);
                builder.setMessage("确定要清空聊天记录嘛?").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        boolean b = JMessageClient.deleteGroupConversation(groupid);
                        if (b){
                            Toast.makeText(MemberMsgActivity.this, "聊天记录删除成功", Toast.LENGTH_SHORT).show();
                        }
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
                //退出群聊
            case R.id.bt_disband:
                builder = new CustomDialogCleanNotice.Builder(MemberMsgActivity.this);
                builder.setMessage("确定要退出该群组嘛?").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //发起极光退出群组
                        JMessageClient.exitGroup(jgGroupId, new BasicCallback() {
                            @Override
                            public void gotResult(int i, String s) {
                                Log.d("xxx","错误码为"+i+",原因为"+s);
                                if (i==0){
                                    //极光退出成功 在退出本地群组
                                    NetUtils.getInstance().getApis()
                                            .doDeleteMember(groupid,userId)
                                            .subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(new Observer<ChangeGroupHeadBean>() {
                                                @Override
                                                public void onSubscribe(@NonNull Disposable d) {

                                                }

                                                @Override
                                                public void onNext(@NonNull ChangeGroupHeadBean changeGroupHeadBean) {
                                                    dialog.dismiss();
                                                    if (changeGroupHeadBean.getMsg().equals("删除成功")){
                                                        JMessageClient.deleteGroupConversation(jgGroupId);
                                                        finish();
                                                    }
                                                }

                                                @Override
                                                public void onError(@NonNull Throwable e) {
                                                    dialog.dismiss();

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
                break;
            default:
                break;
        }
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
                            if (list!=null && list.size()>0){

                                GridLayoutManager gridLayoutManager = new GridLayoutManager(MemberMsgActivity.this, 7);
                                rcMember.setLayoutManager(gridLayoutManager);

                                GroupMemberAdapter groupMemberAdapter = new GroupMemberAdapter(MemberMsgActivity.this);
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
                            Glide.with(MemberMsgActivity.this).load(groupImg).into(ivImg);
                            //设置背景
                            Glide.with(MemberMsgActivity.this).load(object.getBaseMap()).into(ivBg);
                            //群ID
                            tvGroupId.setText("ID:"+object.getId() + "");
                            //群人数
                            tvGroupPeopleCount.setText(object.getUserNum() + "");
                            //设置群名
                            tvGroupName.setText(object.getGroupName() + "");
                            tvName.setText(object.getGroupName() + "");
                            //设置公告
                            tvGonggao.setText(object.getGroupNotice()+"");
                            //设置简介
                            tvJianjie.setText(object.getGroupInfo()+"");

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


}
