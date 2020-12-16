package com.YiDian.RainBow.main.fragment.home.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseAvtivity;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.base.Common;
import com.YiDian.RainBow.main.fragment.home.bean.DynamicDetailsBean;
import com.YiDian.RainBow.utils.NetUtils;
import com.leaf.library.StatusBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DynamicDetailsActivity extends BaseAvtivity implements View.OnClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_back)
    LinearLayout ivBack;
    private int id;
    private int userId;

    @Override
    protected int getResId() {
        return R.layout.activity_dynamicdetails;
    }

    @Override
    protected void getData() {
        Intent intent =
                getIntent();
        id = intent.getIntExtra("id", 0);
        userId = Integer.valueOf(Common.getUserId());

        StatusBarUtil.setGradientColor(this,toolbar);
        StatusBarUtil.setDarkMode(this);

        ivBack.setOnClickListener(this);

        //获取指定id下动态的详情
        showDialog();
        NetUtils.getInstance().getApis()
                .dogetDynamicDetails(id, userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DynamicDetailsBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(DynamicDetailsBean dynamicDetailsBean) {
                        hideDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        hideDialog();
                        Toast.makeText(DynamicDetailsActivity.this, "获取数据失败", Toast.LENGTH_SHORT).show();
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    //绑定单击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
        }
    }
}
