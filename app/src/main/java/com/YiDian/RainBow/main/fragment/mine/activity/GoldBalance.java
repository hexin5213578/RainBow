package com.YiDian.RainBow.main.fragment.mine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseAvtivity;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.base.Common;
import com.YiDian.RainBow.main.fragment.mine.adapter.GoldBalanceAdapter;
import com.YiDian.RainBow.main.fragment.mine.bean.ConsumeRecordBean;
import com.YiDian.RainBow.utils.BasisTimesUtils;
import com.YiDian.RainBow.utils.NetUtils;
import com.leaf.library.StatusBarUtil;
import com.liaoinstan.springview.container.AliHeader;
import com.liaoinstan.springview.widget.SpringView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class GoldBalance extends BaseAvtivity implements View.OnClickListener {

    @BindView(R.id.tv_Recharge)
    TextView tvRecharge;
    @BindView(R.id.tv_cash)
    TextView tvCash;
    @BindView(R.id.iv_date)
    RelativeLayout ivDate;
    @BindView(R.id.tv_balance)
    TextView tvBalance;
    @BindView(R.id.tv_ableuse)
    TextView tvAbleuse;
    @BindView(R.id.sv)
    SpringView sv;
    @BindView(R.id.rl_default)
    RelativeLayout rlDefault;
    @BindView(R.id.recycler_view_record)
    RecyclerView recyclerViewRecord;
    @BindView(R.id.l_return)
    LinearLayout lReturn;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_consume_income)
    TextView tvConsumeIncome;
    @BindView(R.id.tv_time_period)
    TextView tvTimePeriod;
    private Intent intent;
    private int userId;
    private final String TAG = "xxx";
    int page = 1;
    String nowday;

    @Override
    protected int getResId() {
        return R.layout.activity_baseactive;
    }

    @Override
    protected void getData() {
        //注册监听
        lReturn.setOnClickListener(this);
        tvCash.setOnClickListener(this);
        tvRecharge.setOnClickListener(this);
        ivDate.setOnClickListener(this);
        //设置状态栏颜色与字体颜色
        StatusBarUtil.setGradientColor(GoldBalance.this, toolbar);
        StatusBarUtil.setDarkMode(GoldBalance.this);

        //处理业务逻辑  ;
        userId = Integer.parseInt(Common.getUserId());
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        //nowday = formatter.format(currentTime);
        //Log.d(TAG, "getData: " + nowday);

        //进去先刷新数据
        refresh(page);
        sv.setFooter(new AliHeader(GoldBalance.this));
        sv.setHeader(new AliHeader(GoldBalance.this));
        sv.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        refresh(1);
                        sv.onFinishFreshAndLoad();

                    }
                }, 2000);
                //下拉刷新数据

            }

            @Override
            public void onLoadmore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        page++;
                        refresh(page, nowday);
                        sv.onFinishFreshAndLoad();

                    }
                }, 2000);

            }
        });
    }

    //刷新页面数据nowDAU
    public void refresh(int page,String nowDay) {
        //调用接口请求数据
        NetUtils.getInstance().getApis().doGetConsumeRecord(userId,nowDay, page, 20).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<ConsumeRecordBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ConsumeRecordBean consumeRecordBean) {
                        if (consumeRecordBean.getType().equals("OK")) {
                            rlDefault.setVisibility(View.GONE);
                            sv.setVisibility(View.VISIBLE);
                            //总金币
                            int total = consumeRecordBean.getObject().getGoldNum().getGoldAll();
                            tvBalance.setText(total + "");
                            //可提现金币
                            String ableUseGold = "可提现金币（个）：" + consumeRecordBean.getObject().getGoldNum().getGoldUsable();
                            tvAbleuse.setText(ableUseGold);
                            //总消费
                            String totalconsume = consumeRecordBean.getObject().getSpendingGoldNum() + "";
                            //总收入
                            String totalIncome = consumeRecordBean.getObject().getIncomeGoldNum() + "";
                            tvConsumeIncome.setText("总消费：￥" + totalconsume + "总充值：￥" + totalIncome);
                            //
                            tvConsumeIncome.setVisibility(View.VISIBLE);
                            tvConsumeIncome.setText(nowDay);
                            List<ConsumeRecordBean.ObjectBean.PageInfoBean.ListBean> list = consumeRecordBean.getObject().getPageInfo().getList();
                            GoldBalanceAdapter adapter = new GoldBalanceAdapter(GoldBalance.this, list);
                            LinearLayoutManager manager = new LinearLayoutManager(GoldBalance.this, RecyclerView.VERTICAL, false);
                            recyclerViewRecord.setLayoutManager(manager);
                            recyclerViewRecord.setAdapter(adapter);

                        } else {
                            rlDefault.setVisibility(View.VISIBLE);
                            sv.setVisibility(View.GONE);
                            Log.d(TAG, "onNext: 查询数据失败");

                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: -----------------------------");
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void refresh(int page) {
        //调用接口请求数据
        NetUtils.getInstance().getApis().doGetConsumeRecord(userId, page, 15).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<ConsumeRecordBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ConsumeRecordBean consumeRecordBean) {
                        if (consumeRecordBean.getType().equals("OK")) {
                            rlDefault.setVisibility(View.GONE);
                            sv.setVisibility(View.VISIBLE);
                            //总金币
                            int total = consumeRecordBean.getObject().getGoldNum().getGoldAll();
                            tvBalance.setText(total + "");
                            //可提现金币
                            String ableUseGold = "可提现金币（个）：" + consumeRecordBean.getObject().getGoldNum().getGoldUsable();
                            tvAbleuse.setText(ableUseGold);
                            //总消费
                            String totalconsume = consumeRecordBean.getObject().getSpendingGoldNum() + "";
                            //总收入
                            String totalIncome = consumeRecordBean.getObject().getIncomeGoldNum() + "";
                            tvConsumeIncome.setText("总消费：￥" + totalconsume + "总充值：￥" + totalIncome);
                            //
                            tvConsumeIncome.setVisibility(View.GONE);
                            List<ConsumeRecordBean.ObjectBean.PageInfoBean.ListBean> list = consumeRecordBean.getObject().getPageInfo().getList();
                            GoldBalanceAdapter adapter = new GoldBalanceAdapter(GoldBalance.this, list);
                            LinearLayoutManager manager = new LinearLayoutManager(GoldBalance.this, RecyclerView.VERTICAL, false);
                            recyclerViewRecord.setLayoutManager(manager);
                            recyclerViewRecord.setAdapter(adapter);

                        } else {
                            rlDefault.setVisibility(View.VISIBLE);
                            sv.setVisibility(View.GONE);
                            Log.d(TAG, "onNext: 查询数据失败");

                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: -----------------------------");
                        e.printStackTrace();
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
            case R.id.l_return:
                //返回
                finish();
                break;
            case R.id.tv_cash:
                //跳转到提现界面
                intent = new Intent(GoldBalance.this, CashActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_Recharge:
                //跳转到充值页面
                intent = new Intent(GoldBalance.this, RechargeGlodActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_date:
                Log.d(TAG, "onClick: 点击了日期");
                //如果用户更改日期。拿到用户更改日期的时间段 刷新数据
                BasisTimesUtils.showDatePickerDialog(GoldBalance.this, "请选择开始年月日", 1998, 1, 1, new BasisTimesUtils.OnDatePickerListener() {

                    @Override
                    public void onConfirm(int year, int month, int dayOfMonth) {

                        refresh(1, year + "-" + month + "-" + dayOfMonth);
                    }

                    @Override
                    public void onCancel() {

                    }
                });

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
