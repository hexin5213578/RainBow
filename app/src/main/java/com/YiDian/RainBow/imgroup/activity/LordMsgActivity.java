package com.YiDian.RainBow.imgroup.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseAvtivity;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.base.Common;
import com.YiDian.RainBow.custom.image.CustomRoundAngleImageView;
import com.YiDian.RainBow.imgroup.adapter.GroupMemberAdapter;
import com.YiDian.RainBow.imgroup.bean.GroupMemberBean;
import com.YiDian.RainBow.imgroup.bean.GroupMsgBean;
import com.YiDian.RainBow.utils.NetUtils;
import com.bumptech.glide.Glide;
import com.leaf.library.StatusBarUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author hmy 群主信息页
 */
public class LordMsgActivity extends BaseAvtivity implements View.OnClickListener {
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
    @BindView(R.id.rl_top)
    RelativeLayout rlTop;
    @BindView(R.id.rc_member)
    RecyclerView rcMember;
    @BindView(R.id.rl_seemore)
    LinearLayout rlSeemore;
    @BindView(R.id.rl_change_name)
    RelativeLayout rlChangeName;
    @BindView(R.id.rl_change_gonggao)
    RelativeLayout rlChangeGonggao;
    @BindView(R.id.rl_change_jianjie)
    RelativeLayout rlChangeJianjie;
    @BindView(R.id.rl_qunzu_code)
    RelativeLayout rlQunzuCode;
    @BindView(R.id.rl_qunzu_manager)
    RelativeLayout rlQunzuManager;
    @BindView(R.id.rl_clean)
    RelativeLayout rlClean;
    @BindView(R.id.bt_disband)
    Button btDisband;
    @BindView(R.id.iv_bg)
    ImageView ivBg;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_jianjie)
    TextView tvJianjie;
    @BindView(R.id.tv_gonggao)
    TextView tvGonggao;

    @Override
    protected int getResId() {
        return R.layout.activity_lordmsg;
    }

    @Override
    protected void getData() {
        //设置背景透明
        StatusBarUtil.setTransparentForWindow(this);
        //设置状态栏字体黑色
        StatusBarUtil.setLightMode(this);

        int userId = Integer.parseInt(Common.getUserId());



        Intent intent = getIntent();
        int groupid = intent.getIntExtra("groupid", 0);
        Log.d("xxx", "群ID为" + groupid);

        llBack.setOnClickListener(this);
        rlTop.setOnClickListener(this);
        rlSeemore.setOnClickListener(this);
        rlChangeName.setOnClickListener(this);
        rlChangeGonggao.setOnClickListener(this);
        rlChangeJianjie.setOnClickListener(this);
        rlQunzuCode.setOnClickListener(this);
        rlQunzuManager.setOnClickListener(this);
        rlClean.setOnClickListener(this);
        btDisband.setOnClickListener(this);


        //获取群信息
        getGroupMsg(groupid, userId);
        //获取群成员列表
        getGroupMember(groupid,1,25);
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }
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

                                GridLayoutManager gridLayoutManager = new GridLayoutManager(LordMsgActivity.this, 7);
                                rcMember.setLayoutManager(gridLayoutManager);

                                GroupMemberAdapter groupMemberAdapter = new GroupMemberAdapter(LordMsgActivity.this);
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
                            //设置头像
                            Glide.with(LordMsgActivity.this).load(object.getGroupImg()).into(ivImg);
                            //设置背景
                            Glide.with(LordMsgActivity.this).load(object.getBaseMap()).into(ivBg);
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
    public void onClick(View v) {
        switch (v.getId()) {
            //更换群组背景图
            case R.id.rl_top:

                break;
            //退出当前页面
            case R.id.ll_back:
                finish();
                break;
            //查看更多群成员
            case R.id.rl_seemore:

                break;
            //更改群名
            case R.id.rl_change_name:

                break;
            //更改群公告
            case R.id.rl_change_gonggao:

                break;
            //更改群简介
            case R.id.rl_change_jianjie:

                break;
            //查看群二维码
            case R.id.rl_qunzu_code:

                break;
            //管理群聊
            case R.id.rl_qunzu_manager:

                break;
            //清空聊天记录
            case R.id.rl_clean:

                break;
            //解散群聊
            case R.id.bt_disband:

                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
