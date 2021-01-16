package com.YiDian.RainBow.regist.activity;

import android.content.Intent;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseAvtivity;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.dynamic.bean.SaveMsgSuccessBean;
import com.YiDian.RainBow.feedback.activity.FeedBackActivity;
import com.YiDian.RainBow.login.activity.LoginActivity;
import com.YiDian.RainBow.main.activity.MainActivity;
import com.YiDian.RainBow.setpwd.activity.SetPwdActivity;
import com.YiDian.RainBow.setpwd.bean.GetPhoneCodeBean;
import com.YiDian.RainBow.utils.NetUtils;
import com.YiDian.RainBow.utils.SPUtil;
import com.YiDian.RainBow.utils.StringUtil;
import com.leaf.library.StatusBarUtil;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RegistActivity extends BaseAvtivity implements View.OnClickListener {

    @BindView(R.id.tv_go_login)
    RelativeLayout goLogin;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.tv_getcode)
    TextView tvGetcode;
    @BindView(R.id.bt_regist)
    Button btRegist;
    private CountDownTimer mTimer;
    private boolean  isplayer = false;
    private String phone;
    private String auth;

    @Override
    protected int getResId() {
        return R.layout.activity_regist;
    }

    @Override
    protected void getData() {

        StatusBarUtil.setTransparentForWindow(RegistActivity.this);

        goLogin.setOnClickListener(this);
        tvGetcode.setOnClickListener(this);
        btRegist.setOnClickListener(this);

        //获取七牛云uploadToken
        NetUtils.getInstance().getApis().getUpdateToken()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SaveMsgSuccessBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(SaveMsgSuccessBean saveMsgSuccessBean) {
                        String upToken = saveMsgSuccessBean.getUpToken();
                        SPUtil.getInstance().saveData(RegistActivity.this, SPUtil.FILE_NAME, SPUtil.UPTOKEN, upToken);
                        Log.d("xxx", "uptoken存入成功");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

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
            case R.id.tv_go_login:
                startActivity(new Intent(RegistActivity.this, LoginActivity.class));
                finish();
                break;
            case R.id.tv_getcode:
                phone = etPhone.getText().toString();
                if(StringUtil.checkPhoneNumber(phone)){
                    //调用倒计时方法
                    countDownTime();
                    NetUtils.getInstance().getApis().getPhoneCode(phone)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<GetPhoneCodeBean>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(GetPhoneCodeBean getPhoneCodeBean) {
                                    if(getPhoneCodeBean.getMsg().equals("验证码返回成功")){
                                        auth = getPhoneCodeBean.getObject();
                                    }else{
                                        Toast.makeText(RegistActivity.this, ""+getPhoneCodeBean.getMsg(), Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onError(Throwable e) {

                                }

                                @Override
                                public void onComplete() {

                                }
                            });
                }
                break;
            case R.id.bt_regist:
                phone = etPhone.getText().toString();
                String code = etCode.getText().toString();
                if(StringUtil.checkPhoneNumber(phone)){
                    if(StringUtil.checkSms(code)){
                        if(code.equals(auth)){
                            Intent intent = new Intent(RegistActivity.this, SetPwdActivity.class);
                            intent.putExtra("phone", phone);
                            startActivity(intent);
                        }else{
                            Toast.makeText(this, "验证码输入有误", Toast.LENGTH_SHORT).show();
                            etCode.setText("");
                        }

                    }
                }
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
                 isplayer = true;
             }

             @Override
             public void onFinish() {
                 tvGetcode.setEnabled(true);
                 tvGetcode.setText("获取验证码");
                 isplayer = false;
                 cancel();
             }
         };
        mTimer.start();
        tvGetcode.setEnabled(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(isplayer){
            mTimer.cancel();
        }
    }
}