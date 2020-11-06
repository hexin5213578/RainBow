package com.YiDian.RainBow.main.fragment.im.activity;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseAvtivity;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.custom.image.CustomRoundAngleImageView;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;

//我的公会
public class MyUnionActivity extends BaseAvtivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.ll_shusandian)
    RelativeLayout llShusandian;
    @BindView(R.id.iv_img)
    CustomRoundAngleImageView ivImg;
    @BindView(R.id.tv_union_name)
    TextView tvUnionName;
    @BindView(R.id.tv_union_id)
    TextView tvUnionId;
    @BindView(R.id.tv_union_count)
    TextView tvUnionCount;
    @BindView(R.id.iv_raking)
    SimpleDraweeView ivRaking;
    @BindView(R.id.iv_person)
    SimpleDraweeView ivPerson;
    @BindView(R.id.iv_manager)
    SimpleDraweeView ivManager;
    @BindView(R.id.tv_notice)
    TextView tvNotice;
    @BindView(R.id.rc_chatroom)
    RecyclerView rcChatroom;
    @BindView(R.id.tv_card)
    TextView tvCard;

    @Override
    protected int getResId() {
        return R.layout.activity_my_union;
    }

    @Override
    protected void getData() {

    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

}
