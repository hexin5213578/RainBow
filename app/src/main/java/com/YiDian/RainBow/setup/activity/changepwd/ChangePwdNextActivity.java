package com.YiDian.RainBow.setup.activity.changepwd;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseAvtivity;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.remember.activity.RememberPwdActivity;
import com.YiDian.RainBow.remember.bean.RememberPwdBean;
import com.YiDian.RainBow.utils.NetUtils;
import com.YiDian.RainBow.utils.StringUtil;
import com.leaf.library.StatusBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ChangePwdNextActivity extends BaseAvtivity implements View.OnClickListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_back)
    LinearLayout ivBack;
    @BindView(R.id.et_pwd1)
    EditText etPwd1;
    @BindView(R.id.et_pwd2)
    EditText etPwd2;
    @BindView(R.id.bt_confirm)
    Button btConfirm;
    private String phone;

    @Override
    protected int getResId() {
        return R.layout.activity_changepwdnext;
    }

    @Override
    protected void getData() {
        StatusBarUtil.setGradientColor(ChangePwdNextActivity.this,toolbar);
        StatusBarUtil.setDarkMode(ChangePwdNextActivity.this);

        Intent intent = getIntent();
        phone = intent.getStringExtra("phone");

        btConfirm.setOnClickListener(this);
        ivBack.setOnClickListener(this);
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
            case R.id.bt_confirm:
                String pwd1 = etPwd1.getText().toString();
                String pwd2 = etPwd2.getText().toString();
                if (StringUtil.checkPassword(pwd1)){
                    if (!TextUtils.isEmpty(pwd2)){
                        if (pwd1.equals(pwd2)){
                            NetUtils.getInstance().getApis().doRemeberPwd(pwd1, phone)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Observer<RememberPwdBean>() {
                                        @Override
                                        public void onSubscribe(Disposable d) {

                                        }

                                        @Override
                                        public void onNext(RememberPwdBean rememberPwdBean) {
                                            if (rememberPwdBean.getType().equals("OK")) {
                                                Toast.makeText(ChangePwdNextActivity.this, "密码修改成功", Toast.LENGTH_SHORT).show();
                                                finish();
                                            }
                                        }

                                        @Override
                                        public void onError(Throwable e) {

                                        }

                                        @Override
                                        public void onComplete() {

                                        }
                                    });
                        }else{
                            Toast.makeText(this, "两次输入密码不一致", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(this, "请再次输入密码", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }
}
