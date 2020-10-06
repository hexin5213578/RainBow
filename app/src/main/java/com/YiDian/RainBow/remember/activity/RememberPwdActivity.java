package com.YiDian.RainBow.remember.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseAvtivity;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.feedback.FeedBackActivity;
import com.YiDian.RainBow.setpwd.activity.SetPwdActivity;

import butterknife.BindView;

public class RememberPwdActivity extends BaseAvtivity implements View.OnClickListener {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.tv_mt_pro)
    TextView tvMtPro;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.tv_getcode)
    TextView tvGetcode;
    @BindView(R.id.et_pwd1)
    EditText etPwd1;
    @BindView(R.id.iv_see_pwd1)
    ImageView ivSeePwd1;
    @BindView(R.id.bt_confirm)
    Button btConfirm;
    private CountDownTimer mTimer;

    @Override
    protected int getResId() {
        return R.layout.activity_remember_pwd;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void getData() {
        back.setOnClickListener(this);
        tvGetcode.setOnClickListener(this);
        tvMtPro.setOnClickListener(this);
        btConfirm.setOnClickListener(this);

        //密码明文密文切换
        ivSeePwd1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        etPwd1.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        ivSeePwd1.setImageResource(R.mipmap.eyeopen);
                        break;
                    case MotionEvent.ACTION_UP:
                        etPwd1.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        ivSeePwd1.setImageResource(R.mipmap.eyeclose);
                        break;
                }
                return true;
            }
        });
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                //返回登录页
                finish();
                break;
                //确认修改密码
            case R.id.bt_confirm:
                // TODO: 2020/10/6 0006 调用修改密码接口  修改成功后跳转至登录页

                break;
                //跳转至遇到问题界面
            case R.id.tv_mt_pro:
                startActivity(new Intent(RememberPwdActivity.this, FeedBackActivity.class));
                break;
                //获取验证码
            case R.id.tv_getcode:
                countDownTime();
                break;
        }
    }
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
