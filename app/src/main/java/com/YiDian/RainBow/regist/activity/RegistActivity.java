package com.YiDian.RainBow.regist.activity;

import android.content.Intent;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseAvtivity;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.feedback.activity.FeedBackActivity;
import com.YiDian.RainBow.login.activity.LoginActivity;
import com.YiDian.RainBow.setpwd.activity.SetPwdActivity;

import butterknife.BindView;

public class RegistActivity extends BaseAvtivity implements View.OnClickListener {

    @BindView(R.id.tv_go_login)
    TextView goLogin;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.tv_getcode)
    TextView tvGetcode;
    @BindView(R.id.bt_regist)
    Button btRegist;
    private CountDownTimer mTimer;

    @Override
    protected int getResId() {
        return R.layout.activity_regist;
    }

    @Override
    protected void getData() {
        goLogin.setOnClickListener(this);
        tvGetcode.setOnClickListener(this);
        btRegist.setOnClickListener(this);
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_go_login:
                startActivity(new Intent(RegistActivity.this, LoginActivity.class));
                finish();
                break;
            case R.id.tv_getcode:
                //调用倒计时方法
                countDownTime();
                break;
            case R.id.bt_regist:
                Intent intent = new Intent(RegistActivity.this, SetPwdActivity.class);
                startActivity(intent);
                break;
        }
    }
    //倒计时获取验证码
    private void countDownTime() {
        //用安卓自带的CountDownTimer实现
        mTimer = new CountDownTimer(60 * 1000, 1000) {
             @Override
             public void onTick(long millisUntilFinished) {
                 tvGetcode.setText(millisUntilFinished / 1000 + "秒重发");
             }

             @Override
             public void onFinish() {
                 tvGetcode.setEnabled(true);
                 tvGetcode.setText("获取验证码");
                 cancel();
             }
         };
        mTimer.start();
        tvGetcode.setEnabled(false);
    }
}