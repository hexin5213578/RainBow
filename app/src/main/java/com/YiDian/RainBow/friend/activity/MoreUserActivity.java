package com.YiDian.RainBow.friend.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseAvtivity;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.dynamic.bean.SelectFriendOrGroupBean;
import com.YiDian.RainBow.friend.adapter.SearchFriendAdapter;
import com.leaf.library.StatusBarUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoreUserActivity extends BaseAvtivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_back)
    LinearLayout ivBack;
    @BindView(R.id.rc_user)
    RecyclerView rcUser;

    @Override
    protected int getResId() {
        return R.layout.activity_moreuser;
    }

    @Override
    protected void getData() {

        StatusBarUtil.setGradientColor(MoreUserActivity.this,toolbar);
        StatusBarUtil.setDarkMode(MoreUserActivity.this);

        Intent intent = getIntent();
        List<SelectFriendOrGroupBean.ObjectBean.UserListBean> list = (List<SelectFriendOrGroupBean.ObjectBean.UserListBean>)intent.getSerializableExtra("list");

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Log.d("xxx","集合长度为"+list.size());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MoreUserActivity.this, RecyclerView.VERTICAL, false);
        rcUser.setLayoutManager(linearLayoutManager);

        SearchFriendAdapter searchFriendAdapter = new SearchFriendAdapter(MoreUserActivity.this, list);
        rcUser.setAdapter(searchFriendAdapter);
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

}
