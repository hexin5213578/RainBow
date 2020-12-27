package com.YiDian.RainBow.main.fragment.find.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseAvtivity;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.base.Common;
import com.YiDian.RainBow.main.fragment.find.bean.AllUserInfoBean;
import com.YiDian.RainBow.main.fragment.home.adapter.NewDynamicAdapter;
import com.YiDian.RainBow.main.fragment.home.bean.NewDynamicBean;
import com.YiDian.RainBow.utils.NetUtils;
import com.bumptech.glide.Glide;
import com.leaf.library.StatusBarUtil;
import com.tencent.tauth.Tencent;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

//用户详情页 由匹配跳转

public class UserDetailsActivity extends BaseAvtivity implements View.OnClickListener {
    @BindView(R.id.iv_bg)
    ImageView ivBg;
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.sandian)
    LinearLayout sandian;
    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.tv_age)
    TextView tvAge;
    @BindView(R.id.tv_distance)
    TextView tvDistance;
    @BindView(R.id.rl_nodata)
    RelativeLayout rlNodata;
    @BindView(R.id.rc_dynamic)
    RecyclerView rcDynamic;
    private int id;
    private int userid;
    private AllUserInfoBean.ObjectBean.ListBean bean;
    int page =1;
    int size = 15;
    private LinearLayoutManager linearLayoutManager;
    private NewDynamicAdapter newDynamicAdapter;
    private Tencent mTencent;

    @Override
    protected int getResId() {
        return R.layout.activity_user_details;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void getData() {
        back.setOnClickListener(this);
        sandian.setOnClickListener(this);


        //腾讯AppId(替换你自己App Id)、上下文
        mTencent = Tencent.createInstance("101906973", UserDetailsActivity.this);
        //设置背景透明
        StatusBarUtil.setTransparentForWindow(this);
        //设置状态栏字体黑色
        StatusBarUtil.setLightMode(this);
        Intent intent = getIntent();
        //获取用户信息
        bean = (AllUserInfoBean.ObjectBean.ListBean) intent.getSerializableExtra("bean");

        id = bean.getId();

        userid = Integer.valueOf(Common.getUserId());


        getUserDynamic(page,size);
        //设置数据
        InitData();

        Glide.with(UserDetailsActivity.this).load(bean.getBackImg()).into(ivBg);

    }
    public void InitData(){


        tvUsername.setText(bean.getNickName());
        if (bean.getUserRole().equals("保密")){
            tvAge.setText(bean.getAge()+"");
        }else{
            tvAge.setText(bean.getUserRole()+"  "+bean.getAge());
        }
        //设置距离
        int distance = bean.getDistance();
        if(distance<1000){
            tvDistance.setText(distance+"m");
        }else{
            String dis = String.valueOf(distance/1000);
            tvDistance.setText(dis+"Km");
        }
    }
    @Override
    protected BasePresenter initPresenter() {
        return null;
    }
    //获取网络数据
    public void getUserDynamic(int page,int size){
        NetUtils.getInstance().getApis()
                .doGetDynamicByUserid(id,userid,page,size)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NewDynamicBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(NewDynamicBean newDynamicBean) {
                        List<NewDynamicBean.ObjectBean.ListBean> list =
                                newDynamicBean.getObject().getList();
                        if (list.size()>0 && list!=null){
                            rlNodata.setVisibility(View.GONE);
                            rcDynamic.setVisibility(View.VISIBLE);
                            //创建最新动态适配器
                            linearLayoutManager = new LinearLayoutManager(UserDetailsActivity.this, RecyclerView.VERTICAL, false);
                            rcDynamic.setLayoutManager(linearLayoutManager);
                            newDynamicAdapter = new NewDynamicAdapter(UserDetailsActivity.this, list,mTencent);
                            rcDynamic.setAdapter(newDynamicAdapter);
                        }else{
                            rlNodata.setVisibility(View.VISIBLE);
                            rcDynamic.setVisibility(View.GONE);
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
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                //退出当前界面
                finish();
                break;
            case R.id.sandian:

                break;
        }
    }
}
