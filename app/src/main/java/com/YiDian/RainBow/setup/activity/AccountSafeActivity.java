package com.YiDian.RainBow.setup.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseAvtivity;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.base.Common;
import com.YiDian.RainBow.setup.activity.bindphone.BindPhoneActivity;
import com.YiDian.RainBow.setup.activity.changepwd.ChangePwdActivity;
import com.YiDian.RainBow.setup.bean.GetBindPhoneMsgBean;
import com.YiDian.RainBow.utils.NetUtils;
import com.leaf.library.StatusBarUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

//账号安全
public class AccountSafeActivity extends BaseAvtivity implements View.OnClickListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_back)
    LinearLayout ivBack;
    @BindView(R.id.rl_change_pwd)
    RelativeLayout rlChangePwd;
    @BindView(R.id.tv_isbind)
    TextView tvIsbind;
    @BindView(R.id.rl_change_phone)
    RelativeLayout rlChangePhone;
    @BindView(R.id.tv_bind)
    TextView tvBind;
    private int userid;
    private Intent intent;
    private boolean isClick;
    @Override
    protected int getResId() {
        return R.layout.activity_accountsafe;
    }

    @Override
    protected void getData() {
        StatusBarUtil.setGradientColor(AccountSafeActivity.this, toolbar);
        StatusBarUtil.setDarkMode(AccountSafeActivity.this);

        userid = Integer.valueOf(Common.getUserId());

        ivBack.setOnClickListener(this);
        rlChangePhone.setOnClickListener(this);
        rlChangePwd.setOnClickListener(this);

        isbind();
    }
    public void isbind(){
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

                            tvBind.setText("更改手机号");
                            tvIsbind.setText(getBindPhoneMsgBean.getObject().getPhoneNum());

                            isClick = true;
                        } else if (getBindPhoneMsgBean.getMsg().equals("您还未绑定手机号！")) {

                            tvBind.setText("绑定手机号");
                            tvIsbind.setText("未绑定");

                            isClick = false;
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
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getStr(String msg){
        if (msg.equals("绑定成功")){
            isbind();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(EventBus.getDefault().isRegistered(this)){
           EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.rl_change_pwd:
                if (isClick){
                    intent = new Intent(AccountSafeActivity.this, ChangePwdActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(this, "请先绑定手机号", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.rl_change_phone:

                intent = new Intent(AccountSafeActivity.this, BindPhoneActivity.class);
                startActivity(intent);
                break;
        }
    }
}
