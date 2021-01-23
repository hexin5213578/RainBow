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
import com.YiDian.RainBow.friend.adapter.SearchGroupAdapter;
import com.leaf.library.StatusBarUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoreGroupActivity extends BaseAvtivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_back)
    LinearLayout ivBack;
    @BindView(R.id.rc_group)
    RecyclerView rcGroup;

    @Override
    protected int getResId() {
        return R.layout.activity_moregroup;
    }

    @Override
    protected void getData() {
        StatusBarUtil.setGradientColor(MoreGroupActivity.this,toolbar);
        StatusBarUtil.setDarkMode(MoreGroupActivity.this);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent = getIntent();
        List<SelectFriendOrGroupBean.ObjectBean.GroupListBean> groupList = (List<SelectFriendOrGroupBean.ObjectBean.GroupListBean>)intent.getSerializableExtra("list");

        Log.d("xxx","集合长度为"+groupList.size());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MoreGroupActivity.this, RecyclerView.VERTICAL, false);
        rcGroup.setLayoutManager(linearLayoutManager);

        SearchGroupAdapter searchGroupAdapter = new SearchGroupAdapter(MoreGroupActivity.this, groupList);
        rcGroup.setAdapter(searchGroupAdapter);
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

}
