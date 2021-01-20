package com.YiDian.RainBow.main.fragment.mine.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseAvtivity;
import com.YiDian.RainBow.base.BasePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 提现Activity
 * fst
 * 2020-1-20
 */
public class CashActivity extends BaseAvtivity implements View.OnClickListener {

    //输入提现金额文本框
    @BindView(R.id.tv_input_count)
    EditText tvInputCount;
    @BindView(R.id.check_Alipay)
    RadioButton checkAlipay;
    @BindView(R.id.check_wechat)
    RadioButton checkWechat;
    //提交
    @BindView(R.id.tv_send)
    TextView tvSend;

    @Override
    protected int getResId() {
        return R.layout.activity_cash;
    }

    @Override
    protected void getData() {

    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.l_return:
                finish();
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
