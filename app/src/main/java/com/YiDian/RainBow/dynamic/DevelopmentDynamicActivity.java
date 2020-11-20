package com.YiDian.RainBow.dynamic;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseAvtivity;
import com.YiDian.RainBow.base.BasePresenter;
import com.leaf.library.StatusBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

//发布动态
public class DevelopmentDynamicActivity extends BaseAvtivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_back)
    LinearLayout ivBack;
    @BindView(R.id.tv_release)
    TextView tvRelease;
    @BindView(R.id.rl_top)
    RelativeLayout rlTop;

    @Override
    protected int getResId() {
        return R.layout.activity_development_dynamic;
    }

    @Override
    protected void getData() {
        //设置状态栏颜色 跟状态栏字体颜色
        StatusBarUtil.setGradientColor(DevelopmentDynamicActivity.this,toolbar);
        StatusBarUtil.setDarkMode(DevelopmentDynamicActivity.this);


    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

}
