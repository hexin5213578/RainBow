package com.YiDian.RainBow.main.fragment.mine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseAvtivity;
import com.YiDian.RainBow.base.BasePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GoldBalance extends BaseAvtivity implements View.OnClickListener {
    @BindView(R.id.l_return)
    LinearLayout lReturn;
    @BindView(R.id.tv_Recharge)
    TextView tvRecharge;
    @BindView(R.id.tv_cash)
    TextView tvCash;
    @BindView(R.id.tv_time_period)
    TextView tvTimePeriod;
    private Intent intent;

    @Override
    protected int getResId() {
        return R.layout.activity_baseactive;
    }

    @Override
    protected void getData() {
        //处理业务逻辑


    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.l_return:
                //返回
                finish();
                break;
            case R.id.tv_cash:
                //跳转到提现界面
                intent = new Intent(GoldBalance.this,CashActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_Recharge:
                //跳转到充值页面
                intent = new Intent(GoldBalance.this,RechargeGlodActivity.class);
                startActivity(intent);
                break;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
