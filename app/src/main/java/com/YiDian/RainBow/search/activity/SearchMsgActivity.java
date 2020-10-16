package com.YiDian.RainBow.search.activity;

import android.content.Intent;
import android.util.Log;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseAvtivity;
import com.YiDian.RainBow.base.BasePresenter;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;

//搜索信息展示页
public class SearchMsgActivity extends BaseAvtivity {
    @Override
    protected int getResId() {
        return R.layout.activity_searchmsg;
    }

    @Override
    protected void getData() {
        Intent intent = getIntent();
        //获取要搜索的内容
        String text = intent.getStringExtra("text");


    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }
}
