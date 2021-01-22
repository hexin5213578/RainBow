package com.YiDian.RainBow.main.fragment.mine.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseAvtivity;
import com.YiDian.RainBow.base.BasePresenter;
import com.leaf.library.StatusBarUtil;

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
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.l_return)
    LinearLayout lReturn;

    @Override
    protected int getResId() {
        return R.layout.activity_cash;
    }

    @Override
    protected void getData() {
        //设置状态栏颜色与字体颜色
        StatusBarUtil.setGradientColor(CashActivity.this, toolbar);
        StatusBarUtil.setDarkMode(CashActivity.this);
        lReturn.setOnClickListener(this);
        tvSend.setOnClickListener(this);
        checkWechat.setOnClickListener(this);
        checkAlipay.setOnClickListener(this);
        tvInputCount.setOnClickListener(this);



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
            case R.id.tv_send:
                //提交数据。开始提现

                break;
            case R.id.check_Alipay:
                //支付宝被选中

                break;
            case R.id.check_wechat:
                //微信被选中


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
