package com.YiDian.RainBow.main.fragment.im.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseAvtivity;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.main.fragment.im.adapter.ChatRoomAdapter;
import com.leaf.library.StatusBarUtil;
import com.liaoinstan.springview.widget.SpringView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

//更多聊天室房间
public class MordRoomActivity extends BaseAvtivity implements View.OnClickListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.rl_top)
    RelativeLayout rlTop;
    @BindView(R.id.rc_room)
    RecyclerView rcRoom;
    @BindView(R.id.sv)
    SpringView sv;
    private List<String> list;

    @Override
    protected int getResId() {
        return R.layout.activity_moreroom;
    }

    @Override
    protected void getData() {
        //设置标题栏颜色及字体颜色
        StatusBarUtil.setGradientColor(MordRoomActivity.this,toolbar);
        StatusBarUtil.setDarkMode(MordRoomActivity.this);

        llBack.setOnClickListener(this);

        sv.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadmore() {

            }
        });

        list = new ArrayList<>();

        list.add("何梦洋");
        //测试推荐房间
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        rcRoom.setLayoutManager(gridLayoutManager);
        ChatRoomAdapter chatRoomAdapter = new ChatRoomAdapter(this, list);
        rcRoom.setAdapter(chatRoomAdapter);
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
        }
    }
}
