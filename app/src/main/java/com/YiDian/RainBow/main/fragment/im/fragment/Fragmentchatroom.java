package com.YiDian.RainBow.main.fragment.im.fragment;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseFragment;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.main.fragment.im.adapter.ChatRoomAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class Fragmentchatroom extends BaseFragment {
    @BindView(R.id.rl1)
    RelativeLayout rl1;
    @BindView(R.id.rl2)
    RelativeLayout rl2;
    @BindView(R.id.rl3)
    RelativeLayout rl3;
    @BindView(R.id.rl4)
    RelativeLayout rl4;
    @BindView(R.id.rl5)
    RelativeLayout rl5;
    @BindView(R.id.rl6)
    RelativeLayout rl6;
    @BindView(R.id.rl7)
    RelativeLayout rl7;
    @BindView(R.id.rl8)
    RelativeLayout rl8;
    @BindView(R.id.ll_filter)
    LinearLayout llFilter;
    @BindView(R.id.rc_chatroom)
    RecyclerView rcChatroom;
    private List<String> list = new ArrayList();
    @Override
    protected void getid(View view) {

    }

    @Override
    protected int getResId() {
        return R.layout.home_im_fragment_chatroom;
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void getData() {
        list.add("何梦洋");
        //测试推荐房间
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        rcChatroom.setLayoutManager(gridLayoutManager);
        ChatRoomAdapter chatRoomAdapter = new ChatRoomAdapter(getContext(), list);
        rcChatroom.setAdapter(chatRoomAdapter);
    }
}
