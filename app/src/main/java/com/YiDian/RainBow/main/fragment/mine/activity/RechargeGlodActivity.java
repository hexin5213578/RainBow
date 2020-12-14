package com.YiDian.RainBow.main.fragment.mine.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseAvtivity;
import com.YiDian.RainBow.base.BasePresenter;
import com.leaf.library.StatusBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

//充值金币
public class RechargeGlodActivity extends BaseAvtivity implements View.OnClickListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_back)
    LinearLayout ivBack;
    @BindView(R.id.tv_Recharge_record)
    TextView tvRechargeRecord;
    @BindView(R.id.tv_balance)
    TextView tvBalance;
    @BindView(R.id.rb1)
    RadioButton rb1;
    @BindView(R.id.l1)
    LinearLayout l1;
    @BindView(R.id.rb2)
    RadioButton rb2;
    @BindView(R.id.l2)
    LinearLayout l2;
    @BindView(R.id.rb3)
    RadioButton rb3;
    @BindView(R.id.l3)
    LinearLayout l3;
    @BindView(R.id.rb4)
    RadioButton rb4;
    @BindView(R.id.l4)
    LinearLayout l4;
    @BindView(R.id.rb5)
    RadioButton rb5;
    @BindView(R.id.l5)
    LinearLayout l5;
    @BindView(R.id.rb6)
    RadioButton rb6;
    @BindView(R.id.l6)
    LinearLayout l6;
    @BindView(R.id.check_wechat)
    RadioButton checkWechat;
    @BindView(R.id.rl_wechat_pay)
    RelativeLayout rlWechatPay;
    @BindView(R.id.check_Alipay)
    RadioButton checkAlipay;
    @BindView(R.id.rl_ali_pay)
    RelativeLayout rlAliPay;
    @BindView(R.id.bt_confirm_pay)
    Button btConfirmPay;
    String payway;
    int money;
    @Override
    protected int getResId() {
        return R.layout.activity_recharge_glod;
    }

    @Override
    protected void getData() {
        StatusBarUtil.setGradientColor(RechargeGlodActivity.this,toolbar);
        StatusBarUtil.setDarkMode(RechargeGlodActivity.this);

        //绑定单击事件
        ivBack.setOnClickListener(this);
        tvRechargeRecord.setOnClickListener(this);
        rlWechatPay.setOnClickListener(this);
        rlAliPay.setOnClickListener(this);
        btConfirmPay.setOnClickListener(this);
        l1.setOnClickListener(this);
        l2.setOnClickListener(this);
        l3.setOnClickListener(this);
        l4.setOnClickListener(this);
        l5.setOnClickListener(this);
        l6.setOnClickListener(this);


        //设置单选框不能点击
        checkAlipay.setClickable(false);
        checkWechat.setClickable(false);
        //获取剩余金币数量并复制
        tvBalance.setText("3000");

    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_Recharge_record:
                //查看我的充值记录

                break;
            case R.id.rl_ali_pay:
                this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        checkAlipay.setChecked(true);
                        checkWechat.setChecked(false);
                    }
                });

                break;
            case R.id.rl_wechat_pay:
                this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        checkAlipay.setChecked(false);
                        checkWechat.setChecked(true);
                    }
                });

                break;
            case R.id.l1:
                this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        l1.setBackground(RechargeGlodActivity.this.getResources().getDrawable(R.drawable.select_money_xuanzhong));

                        rb1.setChecked(true);
                        rb2.setChecked(false);
                        rb3.setChecked(false);
                        rb4.setChecked(false);
                        rb5.setChecked(false);
                        rb6.setChecked(false);

                        l2.setBackground(RechargeGlodActivity.this.getResources().getDrawable(R.drawable.select_money_weixuanzhong));
                        l3.setBackground(RechargeGlodActivity.this.getDrawable(R.drawable.select_money_weixuanzhong));
                        l4.setBackground(RechargeGlodActivity.this.getDrawable(R.drawable.select_money_weixuanzhong));
                        l5.setBackground(RechargeGlodActivity.this.getDrawable(R.drawable.select_money_weixuanzhong));
                        l6.setBackground(RechargeGlodActivity.this.getDrawable(R.drawable.select_money_weixuanzhong));

                    }
                });

                break;
            case R.id.l2:
                this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        l2.setBackground(RechargeGlodActivity.this.getResources().getDrawable(R.drawable.select_money_xuanzhong));
                        rb2.setChecked(true);

                        rb1.setChecked(false);
                        rb3.setChecked(false);
                        rb4.setChecked(false);
                        rb5.setChecked(false);
                        rb6.setChecked(false);

                        l1.setBackground(RechargeGlodActivity.this.getResources().getDrawable(R.drawable.select_money_weixuanzhong));
                        l3.setBackground(RechargeGlodActivity.this.getResources().getDrawable(R.drawable.select_money_weixuanzhong));
                        l4.setBackground(RechargeGlodActivity.this.getResources().getDrawable(R.drawable.select_money_weixuanzhong));
                        l5.setBackground(RechargeGlodActivity.this.getResources().getDrawable(R.drawable.select_money_weixuanzhong));
                        l6.setBackground(RechargeGlodActivity.this.getResources().getDrawable(R.drawable.select_money_weixuanzhong));

                    }
                });

                break;
            case R.id.l3:
                this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        l3.setBackground(RechargeGlodActivity.this.getResources().getDrawable(R.drawable.select_money_xuanzhong));
                        rb3.setChecked(true);

                        rb1.setChecked(false);
                        rb2.setChecked(false);
                        rb4.setChecked(false);
                        rb5.setChecked(false);
                        rb6.setChecked(false);

                        l1.setBackground(RechargeGlodActivity.this.getResources().getDrawable(R.drawable.select_money_weixuanzhong));
                        l2.setBackground(RechargeGlodActivity.this.getResources().getDrawable(R.drawable.select_money_weixuanzhong));
                        l4.setBackground(RechargeGlodActivity.this.getResources().getDrawable(R.drawable.select_money_weixuanzhong));
                        l5.setBackground(RechargeGlodActivity.this.getResources().getDrawable(R.drawable.select_money_weixuanzhong));
                        l6.setBackground(RechargeGlodActivity.this.getResources().getDrawable(R.drawable.select_money_weixuanzhong));
                    }
                });

                break;
            case R.id.l4:
                this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        l4.setBackground(RechargeGlodActivity.this.getResources().getDrawable(R.drawable.select_money_xuanzhong));
                        rb4.setChecked(true);

                        rb1.setChecked(false);
                        rb2.setChecked(false);
                        rb3.setChecked(false);
                        rb5.setChecked(false);
                        rb6.setChecked(false);

                        l1.setBackground(RechargeGlodActivity.this.getResources().getDrawable(R.drawable.select_money_weixuanzhong));
                        l2.setBackground(RechargeGlodActivity.this.getResources().getDrawable(R.drawable.select_money_weixuanzhong));
                        l3.setBackground(RechargeGlodActivity.this.getResources().getDrawable(R.drawable.select_money_weixuanzhong));
                        l5.setBackground(RechargeGlodActivity.this.getResources().getDrawable(R.drawable.select_money_weixuanzhong));
                        l6.setBackground(RechargeGlodActivity.this.getResources().getDrawable(R.drawable.select_money_weixuanzhong));
                    }
                });

                break;
            case R.id.l5:
                this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        l5.setBackground(RechargeGlodActivity.this.getResources().getDrawable(R.drawable.select_money_xuanzhong));
                        rb5.setChecked(true);

                        rb1.setChecked(false);
                        rb2.setChecked(false);
                        rb3.setChecked(false);
                        rb4.setChecked(false);
                        rb6.setChecked(false);

                        l1.setBackground(RechargeGlodActivity.this.getResources().getDrawable(R.drawable.select_money_weixuanzhong));
                        l2.setBackground(RechargeGlodActivity.this.getResources().getDrawable(R.drawable.select_money_weixuanzhong));
                        l3.setBackground(RechargeGlodActivity.this.getResources().getDrawable(R.drawable.select_money_weixuanzhong));
                        l4.setBackground(RechargeGlodActivity.this.getResources().getDrawable(R.drawable.select_money_weixuanzhong));
                        l6.setBackground(RechargeGlodActivity.this.getResources().getDrawable(R.drawable.select_money_weixuanzhong));
                    }
                });

                break;
            case R.id.l6:
                this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        l6.setBackground(RechargeGlodActivity.this.getResources().getDrawable(R.drawable.select_money_xuanzhong));
                        rb6.setChecked(true);

                        rb1.setChecked(false);
                        rb2.setChecked(false);
                        rb3.setChecked(false);
                        rb4.setChecked(false);
                        rb5.setChecked(false);

                        l1.setBackground(RechargeGlodActivity.this.getResources().getDrawable(R.drawable.select_money_weixuanzhong));
                        l2.setBackground(RechargeGlodActivity.this.getResources().getDrawable(R.drawable.select_money_weixuanzhong));
                        l3.setBackground(RechargeGlodActivity.this.getResources().getDrawable(R.drawable.select_money_weixuanzhong));
                        l4.setBackground(RechargeGlodActivity.this.getResources().getDrawable(R.drawable.select_money_weixuanzhong));
                        l5.setBackground(RechargeGlodActivity.this.getResources().getDrawable(R.drawable.select_money_weixuanzhong));
                    }
                });

                break;
            case R.id.bt_confirm_pay:
                //判断支付方式
                if(checkWechat.isChecked()){
                    payway = "微信支付";
                }
                if(checkAlipay.isChecked()){
                    payway = "支付宝支付";
                }

                //判断支付金额
                if(rb1.isChecked()){
                    money = 6;
                }
                if(rb2.isChecked()){
                    money = 12;
                }
                if(rb3.isChecked()){
                    money = 45;
                }
                if(rb4.isChecked()){
                    money = 68;
                }
                if(rb5.isChecked()){
                    money = 118;
                }
                if(rb6.isChecked()){
                    money = 648;
                }

                Toast.makeText(this, ""+payway+"    "+money, Toast.LENGTH_SHORT).show();

                break;
        }
    }
}
