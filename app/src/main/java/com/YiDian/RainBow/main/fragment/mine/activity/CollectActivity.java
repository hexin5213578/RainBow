package com.YiDian.RainBow.main.fragment.mine.activity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseAvtivity;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.base.Common;
import com.leaf.library.StatusBarUtil;
import com.liaoinstan.springview.container.AliHeader;
import com.liaoinstan.springview.widget.SpringView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CollectActivity extends BaseAvtivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_back)
    LinearLayout ivBack;
    @BindView(R.id.rl_top)
    RelativeLayout rlTop;
    @BindView(R.id.rl_nodata)
    RelativeLayout rlNodata;
    @BindView(R.id.rc_mydraft_development)
    RecyclerView rcMydraftDevelopment;
    @BindView(R.id.sv)
    SpringView sv;
    int page =1;
    private int userid;

    @Override
    protected int getResId() {
        return R.layout.activity_collect;
    }

    @Override
    protected void getData() {

        userid = Integer.valueOf(Common.getUserId());

        //直接取消动画
        RecyclerView.ItemAnimator animator = rcMydraftDevelopment.getItemAnimator();
        if (animator instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
        }

        //设置背景透明
        StatusBarUtil.setGradientColor(CollectActivity.this,toolbar);
        //设置状态栏字体黑色
        StatusBarUtil.setDarkMode(this);

        sv.setHeader(new AliHeader(this));

        sv.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        page = 1;

                        //等待2.5秒后结束刷新
                        sv.onFinishFreshAndLoad();
                    }
                },1000);
            }

            @Override
            public void onLoadmore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page++;

                        sv.onFinishFreshAndLoad();
                    }
                },1000);
            }
        });


    }
    public void getDynamic(int page,int size){

    }
    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

}
