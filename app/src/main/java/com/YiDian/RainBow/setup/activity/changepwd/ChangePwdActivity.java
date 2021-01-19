package com.YiDian.RainBow.setup.activity.changepwd;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseAvtivity;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.setpwd.bean.GetPhoneCodeBean;
import com.YiDian.RainBow.utils.NetUtils;
import com.YiDian.RainBow.utils.StringUtil;
import com.leaf.library.StatusBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ChangePwdActivity extends BaseAvtivity implements View.OnClickListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_back)
    LinearLayout ivBack;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_code1)
    EditText etCode1;
    @BindView(R.id.tv_getcode1)
    TextView tvGetcode1;
    @BindView(R.id.bt_next)
    Button btNext;
    private String phone;
    private CountDownTimer mTimer;
    private boolean isPlayer;
    private String num;

    @Override
    protected int getResId() {
        return R.layout.activity_changepwd;
    }

    @Override
    protected void getData() {
        StatusBarUtil.setGradientColor(ChangePwdActivity.this,toolbar);

        ivBack.setOnClickListener(this);
        btNext.setOnClickListener(this);
        tvGetcode1.setOnClickListener(this);
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
            case R.id.bt_next:
                phone = etPhone.getText().toString();
                //跳转到下一步
                String code = etCode1.getText().toString();

                if (StringUtil.checkSms(code)){
                    if (code.equals(num)){
                        Intent intent = new Intent(ChangePwdActivity.this, ChangePwdNextActivity.class);
                        intent.putExtra("phone",phone);
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(this, "验证码有误，请查证后再输入", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.tv_getcode1:
                phone = etPhone.getText().toString();

                if (StringUtil.checkPhoneNumber(this.phone)) {

                    countDownTime(tvGetcode1);

                    NetUtils.getInstance().getApis()
                            .getPhoneCode(this.phone)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<GetPhoneCodeBean>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(GetPhoneCodeBean getPhoneCodeBean) {
                                    num = getPhoneCodeBean.getObject();
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
        }
    }
    private void countDownTime(TextView tv) {
        //用安卓自带的CountDownTimer实现 倒技计时
        CountDownTimer  mTimer = new CountDownTimer(60 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tv.setText(millisUntilFinished / 1000 + "秒后重发");
                isPlayer = true;
            }

            @Override
            public void onFinish() {
                tv.setEnabled(true);
                tv.setText("发送验证码");
                isPlayer =false;
                cancel();
            }
        };
        mTimer.start();
        tv.setEnabled(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //界面关闭销毁倒计时
        if (isPlayer) {
            mTimer.cancel();
        }
    }
}
