package com.YiDian.RainBow.main.fragment.find.fragment;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseFragment;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.main.fragment.find.adapter.NearPersonAdapter;
import com.liaoinstan.springview.container.AliHeader;
import com.liaoinstan.springview.widget.SpringView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

// 发现 - 附近
public class FragmentNear extends BaseFragment {
    @BindView(R.id.rc_near_person)
    RecyclerView rcNearPerson;
    @BindView(R.id.sv)
    SpringView sv;

    @Override
    protected void getid(View view) {

    }

    @Override
    protected int getResId() {
        return R.layout.home_find_fragment_near;
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void getData() {
        sv.setHeader(new AliHeader(getContext()));

        sv.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadmore() {

            }
        });
        //创建数据 展示附近的人
        List<String> list = new ArrayList<>();
        list.add("何梦洋");
        list.add("何梦洋");
        //创建适配器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        rcNearPerson.setLayoutManager(linearLayoutManager);
        NearPersonAdapter nearPersonAdapter = new NearPersonAdapter(getContext(), list);
        //设置适配器
        rcNearPerson.setAdapter(nearPersonAdapter);

    }
}
