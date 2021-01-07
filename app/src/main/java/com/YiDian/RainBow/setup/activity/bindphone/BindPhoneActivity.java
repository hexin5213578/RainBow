package com.YiDian.RainBow.setup.activity.bindphone;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseAvtivity;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.base.Common;
import com.YiDian.RainBow.setpwd.bean.GetPhoneCodeBean;
import com.YiDian.RainBow.setup.bean.GetBindPhoneMsgBean;
import com.YiDian.RainBow.setup.bean.InsertRealBean;
import com.YiDian.RainBow.utils.NetUtils;
import com.YiDian.RainBow.utils.StringUtil;
import com.leaf.library.StatusBarUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class BindPhoneActivity extends BaseAvtivity implements View.OnClickListener {

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
    @BindView(R.id.bt_bind1)
    Button btBind1;
    @BindView(R.id.rl_bind)
    RelativeLayout rlBind;
    @BindView(R.id.et_newphone)
    EditText etNewphone;
    @BindView(R.id.et_code2)
    EditText etCode2;
    @BindView(R.id.tv_getcode2)
    TextView tvGetcode2;
    @BindView(R.id.bt_bind2)
    Button btBind2;
    @BindView(R.id.rl_change)
    RelativeLayout rlChange;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    private int userid;
    private CountDownTimer mTimer;
    private String num;
    private String phone;
    private String code;

    @Override
    protected int getResId() {
        return R.layout.activity_bindphone;
    }

    @Override
    protected void getData() {
        //状态栏背景与字体颜色
        StatusBarUtil.setGradientColor(BindPhoneActivity.this, toolbar);
        StatusBarUtil.setDarkMode(BindPhoneActivity.this);

        userid = Integer.valueOf(Common.getUserId());

        getPhoneMsg();

        ivBack.setOnClickListener(this);
        tvGetcode1.setOnClickListener(this);
        tvGetcode2.setOnClickListener(this);
        btBind1.setOnClickListener(this);
        btBind2.setOnClickListener(this);
    }


    //判断是否绑定过
    public void getPhoneMsg() {
        //判断是否已经绑定手机号
        NetUtils.getInstance().getApis()
                .doGetPhoneMsg(userid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GetBindPhoneMsgBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(GetBindPhoneMsgBean getBindPhoneMsgBean) {
                        if (getBindPhoneMsgBean.getMsg().equals("您已绑定手机号！")) {
                            rlChange.setVisibility(View.VISIBLE);
                            rlBind.setVisibility(View.GONE);
                            tvTitle.setText("修改手机号");
                        } else {
                            rlBind.setVisibility(View.VISIBLE);
                            rlChange.setVisibility(View.GONE);
                            tvTitle.setText("绑定手机号");
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

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_getcode1:
                phone = etPhone.getText().toString();

                if (StringUtil.checkPhoneNumber(phone)) {

                    countDownTime(tvGetcode1);

                    NetUtils.getInstance().getApis()
                            .getPhoneCode(phone)
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
            case R.id.tv_getcode2:
                phone = etNewphone.getText().toString();
                if (StringUtil.checkPhoneNumber(phone)) {

                    countDownTime(tvGetcode2);

                    NetUtils.getInstance().getApis()
                            .getPhoneCode(phone)
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
            case R.id.bt_bind1:
                phone = etPhone.getText().toString();
                code = etCode1.getText().toString();
                if (StringUtil.checkPhoneNumber(phone)) {
                    if (StringUtil.checkSms(code)) {
                        if (code.equals(num)) {
                            NetUtils.getInstance().getApis()
                                    .doBindPhone(userid, phone)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Observer<InsertRealBean>() {
                                        @Override
                                        public void onSubscribe(Disposable d) {

                                        }

                                        @Override
                                        public void onNext(InsertRealBean insertRealBean) {
                                            if (insertRealBean.getMsg().equals("绑定手机号成功！")) {
                                                Toast.makeText(BindPhoneActivity.this, "手机号绑定成功", Toast.LENGTH_SHORT).show();
                                                EventBus.getDefault().post("绑定成功");
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

                        } else {
                            Toast.makeText(this, "验证码有误", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                break;
            case R.id.bt_bind2:
                phone = etNewphone.getText().toString();
                code = etCode2.getText().toString();
                if (StringUtil.checkPhoneNumber(phone)) {
                    if (StringUtil.checkSms(code)) {
                        if (code.equals(num)) {
                            NetUtils.getInstance().getApis()
                                    .doBindPhone(userid, phone)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Observer<InsertRealBean>() {
                                        @Override
                                        public void onSubscribe(Disposable d) {

                                        }

                                        @Override
                                        public void onNext(InsertRealBean insertRealBean) {
                                            if (insertRealBean.getMsg().equals("修改手机号成功！")) {
                                                Toast.makeText(BindPhoneActivity.this, "手机号修改成功", Toast.LENGTH_SHORT).show();
                                                EventBus.getDefault().post("绑定成功");
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

                        } else {
                            Toast.makeText(this, "验证码有误", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                break;
        }
    }

    private void countDownTime(TextView tv) {
        //用安卓自带的CountDownTimer实现 倒技计时
        mTimer = new CountDownTimer(60 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tv.setText(millisUntilFinished / 1000 + "秒后重发");
            }

            @Override
            public void onFinish() {
                tv.setEnabled(true);
                tv.setText("发送验证码");
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
        if (mTimer != null) {
            mTimer.cancel();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
