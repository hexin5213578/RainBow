package com.YiDian.RainBow.setup.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.agreement.XingweiActivity;
import com.YiDian.RainBow.agreement.YinsiActivity;
import com.YiDian.RainBow.agreement.YonghuActivity;
import com.YiDian.RainBow.base.BaseAvtivity;
import com.YiDian.RainBow.base.BasePresenter;
import com.leaf.library.StatusBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

//关于我们
public class AboutUsActivity extends BaseAvtivity implements View.OnClickListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_back)
    LinearLayout ivBack;
    @BindView(R.id.tv_edition)
    TextView tvEdition;
    @BindView(R.id.rl_xieyi)
    RelativeLayout rlXieyi;
    @BindView(R.id.rl_yinsi)
    RelativeLayout rlYinsi;
    @BindView(R.id.rl_xingwei)
    RelativeLayout rlXingwei;
    private Intent intent;

    @Override
    protected int getResId() {
        return R.layout.activity_aboutus;
    }

    @Override
    protected void getData() {
        StatusBarUtil.setGradientColor(AboutUsActivity.this,toolbar);
        StatusBarUtil.setDarkMode(AboutUsActivity.this);

        ivBack.setOnClickListener(this);
        rlXieyi.setOnClickListener(this);
        rlXingwei.setOnClickListener(this);
        rlYinsi.setOnClickListener(this);

        //获取当前应用版本号
        String appVersionName = "";
        try {
            PackageInfo packageInfo = this.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(this.getPackageName(), 0);
            appVersionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("", e.getMessage());
        }
        tvEdition.setText("v"+appVersionName + "");
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.rl_xieyi:
                intent = new Intent(AboutUsActivity.this, YonghuActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_yinsi:
                intent = new Intent(AboutUsActivity.this, YinsiActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_xingwei:
                intent = new Intent(AboutUsActivity.this, XingweiActivity.class);
                startActivity(intent);
                break;
        }
    }
}
