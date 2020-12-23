package com.YiDian.RainBow.main.fragment.find.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseAvtivity;
import com.YiDian.RainBow.base.BasePresenter;
import com.leaf.library.StatusBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

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
    @BindView(R.id.tv_guanzhu)
    TextView tvGuanzhu;
    @BindView(R.id.tv_age)
    TextView tvAge;
    @BindView(R.id.tv_distance)
    TextView tvDistance;
    @BindView(R.id.rl_nodata)
    RelativeLayout rlNodata;
    @BindView(R.id.rc_dynamic)
    RecyclerView rcDynamic;

    @Override
    protected int getResId() {
        return R.layout.activity_user_details;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void getData() {
        //设置背景透明
        StatusBarUtil.setTransparentForWindow(this);
        //设置状态栏字体黑色
        StatusBarUtil.setLightMode(this);

        back.setOnClickListener(this);
        sandian.setOnClickListener(this);
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
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

    }
}
