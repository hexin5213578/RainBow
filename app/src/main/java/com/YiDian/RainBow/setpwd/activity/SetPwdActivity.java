package com.YiDian.RainBow.setpwd.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseAvtivity;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.feedback.activity.FeedBackActivity;
import com.YiDian.RainBow.login.activity.CompleteMsgActivity;

import butterknife.BindView;


public class SetPwdActivity extends BaseAvtivity implements View.OnClickListener {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.tv_mt_pro)
    TextView tvMtPro;
    @BindView(R.id.et_pwd1)
    EditText etPwd1;
    @BindView(R.id.iv_see_pwd1)
    ImageView ivSeePwd1;
    @BindView(R.id.et_pwd2)
    EditText etPwd2;
    @BindView(R.id.iv_see_pwd2)
    ImageView ivSeePwd2;
    @BindView(R.id.bt_login)
    Button btLogin;

    @Override
    protected int getResId() {
        return R.layout.activity_setpwd;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void getData() {
        back.setOnClickListener(this);
        tvMtPro.setOnClickListener(this);
        btLogin.setOnClickListener(this);

        //密码明文密文切换
        ivSeePwd1.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
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
        ivSeePwd2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        etPwd2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        ivSeePwd2.setImageResource(R.mipmap.eyeopen);
                        break;
                    case MotionEvent.ACTION_UP:
                        etPwd2.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        ivSeePwd2.setImageResource(R.mipmap.eyeclose);
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
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_mt_pro:
                // TODO: 2020/10/6 0006 跳转遇到问题界面
                startActivity(new Intent(SetPwdActivity.this, FeedBackActivity.class));
                break;
            // TODO: 2020/10/6 0006 调用登录接口 密码设置后直接登录
            case R.id.bt_login:
                Toast.makeText(this, "跳转至主页", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(SetPwdActivity.this, CompleteMsgActivity.class));
                break;
        }
    }
}
