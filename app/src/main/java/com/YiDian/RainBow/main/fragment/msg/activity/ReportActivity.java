package com.YiDian.RainBow.main.fragment.msg.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseAvtivity;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.base.Common;
import com.YiDian.RainBow.main.fragment.msg.bean.ReportActivityBean;
import com.YiDian.RainBow.utils.NetUtils;
import com.leaf.library.StatusBarUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ReportActivity extends BaseAvtivity implements View.OnClickListener {
    String TAG = "fst";
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_commit)
    TextView tvCommit;
    @BindView(R.id.rg_group)
    RadioGroup rgGroup;
    ArrayList<RadioButton> arraylist;
    String userId;
    Integer detailed;
    String id;
    @BindView(R.id.rb_1)
    RadioButton rb1;
    @BindView(R.id.rb_2)
    RadioButton rb2;
    @BindView(R.id.rb_3)
    RadioButton rb3;
    @BindView(R.id.rb_4)
    RadioButton rb4;
    @BindView(R.id.rb_5)
    RadioButton rb5;
    @BindView(R.id.rb_6)
    RadioButton rb6;
    @BindView(R.id.rb_7)
    RadioButton rb7;

    @Override
    protected int getResId() {
        return R.layout.activity_report;
    }

    @Override
    protected void getData() {
        llBack.setOnClickListener(this);
        rb1.setOnClickListener(this);
        rb2.setOnClickListener(this);
        rb3.setOnClickListener(this);
        rb4.setOnClickListener(this);
        rb5.setOnClickListener(this);
        rb6.setOnClickListener(this);
        rb7.setOnClickListener(this);
        tvCommit.setOnClickListener(this);
        StatusBarUtil.setDarkMode(ReportActivity.this);
        StatusBarUtil.setGradientColor(this, toolbar);

        arraylist = new ArrayList<>();
        arraylist.add(rb1);
        arraylist.add(rb2);
        arraylist.add(rb3);
        arraylist.add(rb4);
        arraylist.add(rb5);
        arraylist.add(rb6);
        arraylist.add(rb7);
        userId = Common.getUserId();
        tvCommit.setEnabled(false);//设置提交按钮不可点击

        Intent intent = getIntent();
        id = intent.getStringExtra("id");

    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    public void report() {
        Log.d(TAG, "report: ---网络请求");
        NetUtils.getInstance().getApis().doGetInsertReport(1, detailed, Integer.valueOf(userId), Integer.valueOf(id), 1).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<ReportActivityBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ReportActivityBean reportActivityBean) {
                        if (reportActivityBean.getType().equals("OK")) {
                            Toast.makeText(ReportActivity.this, "举报成功", Toast.LENGTH_SHORT).show();
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
    }

    public void chacked(RadioButton rb) {
        for (RadioButton r : arraylist) {
            //Log.d(TAG, "chacked: ---->"+r.isChecked());
            if (!(r == rb)) {
                r.setChecked(false);
            }
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.rb_1:
                chacked(rb1);
                detailed = 1;
                tvCommit.setBackgroundResource(R.drawable.report_jubao_btu2);
                tvCommit.setEnabled(true);
                tvCommit.setTextColor(this.getResources().getColor(R.color.white));
                break;
            case R.id.rb_2:
                chacked(rb2);
                detailed = 2;
                tvCommit.setBackgroundResource(R.drawable.report_jubao_btu2);
                tvCommit.setEnabled(true);
                tvCommit.setTextColor(this.getResources().getColor(R.color.white));
                break;
            case R.id.rb_3:
                chacked(rb3);
                detailed = 3;
                tvCommit.setBackgroundResource(R.drawable.report_jubao_btu2);
                tvCommit.setEnabled(true);
                tvCommit.setTextColor(this.getResources().getColor(R.color.white));
                break;
            case R.id.rb_4:
                chacked(rb4);
                detailed = 4;
                tvCommit.setBackgroundResource(R.drawable.report_jubao_btu2);
                tvCommit.setEnabled(true);
                tvCommit.setTextColor(this.getResources().getColor(R.color.white));
                break;
            case R.id.rb_5:
                chacked(rb5);
                detailed = 5;
                tvCommit.setBackgroundResource(R.drawable.report_jubao_btu2);
                tvCommit.setEnabled(true);
                tvCommit.setTextColor(this.getResources().getColor(R.color.white));
                break;
            case R.id.rb_6:
                chacked(rb6);
                detailed = 6;
                tvCommit.setBackgroundResource(R.drawable.report_jubao_btu2);
                tvCommit.setEnabled(true);
                tvCommit.setTextColor(this.getResources().getColor(R.color.white));
                break;
            case R.id.rb_7:
                chacked(rb7);
                detailed = 7;
                tvCommit.setBackgroundResource(R.drawable.report_jubao_btu2);
                tvCommit.setEnabled(true);
                tvCommit.setTextColor(this.getResources().getColor(R.color.white));
                break;
            case R.id.tv_commit:
                report();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
