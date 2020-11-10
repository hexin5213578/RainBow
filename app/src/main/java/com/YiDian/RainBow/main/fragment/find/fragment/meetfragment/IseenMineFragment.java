package com.YiDian.RainBow.main.fragment.find.fragment.meetfragment;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseFragment;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.main.fragment.find.adapter.ISeenPersonAdapter;
import com.liaoinstan.springview.container.AliHeader;
import com.liaoinstan.springview.widget.SpringView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class IseenMineFragment extends BaseFragment {
    @BindView(R.id.rc_iseenMine)
    RecyclerView rcIseenMine;
    @BindView(R.id.sv)
    SpringView sv;

    @Override
    protected void getid(View view) {

    }

    @Override
    protected int getResId() {
        return R.layout.home_find_meet_iseenminefragment;
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void getData() {
        sv.setHeader(new AliHeader(getContext()));

        List<String> list = new ArrayList<>();
        list.add("何梦洋");

        //创建布局管理区
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        rcIseenMine.setLayoutManager(linearLayoutManager);
        //创建适配器
        ISeenPersonAdapter iSeenPersonAdapter = new ISeenPersonAdapter(getContext(), list);
        rcIseenMine.setAdapter(iSeenPersonAdapter);
    }
}
