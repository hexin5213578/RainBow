package com.YiDian.RainBow.main.fragment.im.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseFragment;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.main.fragment.im.activity.MyUnionActivity;
import com.YiDian.RainBow.main.fragment.im.activity.UnionRankingActivity;
import com.YiDian.RainBow.main.fragment.im.adapter.RecommendUnionAdapter;
import com.liaoinstan.springview.widget.SpringView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

//公会界面
public class Fragmentunion extends BaseFragment implements View.OnClickListener {
    @BindView(R.id.tv_ranking)
    TextView tvRanking;
    @BindView(R.id.tv_myunion)
    TextView tvMyunion;
    @BindView(R.id.rc_union)
    RecyclerView rcUnion;
    @BindView(R.id.sv)
    SpringView sv;

    @Override
    protected void getid(View view) {

    }

    @Override
    protected int getResId() {
        return R.layout.home_im_fragment_union;
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void getData() {
        tvRanking.setOnClickListener(this);
        tvMyunion.setOnClickListener(this);

        //推荐公会数据测试
        List<String> list = new ArrayList<>();
        list.add("何梦洋");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        rcUnion.setLayoutManager(linearLayoutManager);
        //创建适配器
        RecommendUnionAdapter recommendUnionAdapter = new RecommendUnionAdapter(getContext(), list);
        rcUnion.setAdapter(recommendUnionAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
                //跳转至公会排行榜
            case R.id.tv_ranking:
                startActivity(new Intent(getContext(), UnionRankingActivity.class));
                break;
                //跳转至我的公会
            case R.id.tv_myunion:
                startActivity(new Intent(getContext(), MyUnionActivity.class));
                break;
        }
    }
}
