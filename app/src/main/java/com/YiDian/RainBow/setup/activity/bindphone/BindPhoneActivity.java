package com.YiDian.RainBow.setup.activity.bindphone;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseAvtivity;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.base.Common;
import com.YiDian.RainBow.setup.bean.GetBindPhoneMsgBean;
import com.YiDian.RainBow.utils.NetUtils;
import com.leaf.library.StatusBarUtil;

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
    private int userid;

    @Override
    protected int getResId() {
        return R.layout.activity_bindphone;
    }

    @Override
    protected void getData() {
        //状态栏背景与字体颜色
        StatusBarUtil.setGradientColor(BindPhoneActivity.this,toolbar);
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
                        if (getBindPhoneMsgBean.equals("您已绑定手机号！")) {
                            rlChange.setVisibility(View.VISIBLE);
                            rlBind.setVisibility(View.GONE);
                        } else if (getBindPhoneMsgBean.equals("您还未绑定手机号！")) {
                            rlBind.setVisibility(View.VISIBLE);
                            rlChange.setVisibility(View.GONE);
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
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_getcode1:

                break;
            case R.id.tv_getcode2:

                break;
            case R.id.bt_bind1:

                break;
            case R.id.bt_bind2:

                break;
        }
    }
}
