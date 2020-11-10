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

//  发现 - 遇见 -我看过的
public class IseenFragment extends BaseFragment {
    @BindView(R.id.rc_iseen)
    RecyclerView rcIseen;
    @BindView(R.id.sv)
    SpringView sv;

    @Override
    protected void getid(View view) {

    }

    @Override
    protected int getResId() {
        return R.layout.home_find_meet_iseenfragment;
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
        rcIseen.setLayoutManager(linearLayoutManager);
        //创建适配器
        ISeenPersonAdapter iSeenPersonAdapter = new ISeenPersonAdapter(getContext(), list);
        rcIseen.setAdapter(iSeenPersonAdapter);
    }
}
