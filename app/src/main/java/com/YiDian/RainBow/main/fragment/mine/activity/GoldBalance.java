package com.YiDian.RainBow.main.fragment.mine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import com.liaoinstan.springview.container.AliHeader;
import com.liaoinstan.springview.widget.SpringView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
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
    @BindView(R.id.tv_time_period)
    TextView tvTimePeriod;
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
    private Intent intent;
    private int userId;
    private final String TAG = "xxx";
    int page = 1;

    String starDate;
    String endDate;

    @Override
    protected int getResId() {
        return R.layout.activity_baseactive;
    }

    @Override
    protected void getData() {
        SimpleDateFormat dataformatter = new SimpleDateFormat("yyyy-MM-dd");
        //从网络获取当前时间
        Date newTime = getWebsiteDatetime();
        //获取本月初时间
        Date firstTime = new Date(newTime.getYear(), newTime.getMonth(), 01);
        //转换为String类型
        String format = dataformatter.format(newTime);
        //获取月初时间
        String format1 = dataformatter.format(firstTime);
        //处理业务逻辑
        userId = Integer.parseInt(Common.getUserId());

        tvTimePeriod.setText(format1 + "~" + format);
        //进去先刷新数据
        refresh(page);
        sv.setFooter(new AliHeader(GoldBalance.this));
        sv.setHeader(new AliHeader(GoldBalance.this));
        sv.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                //下拉刷新数据
                refresh(1);
            }

            @Override
            public void onLoadmore() {
                page++;
                if (starDate == null) {
                    refresh(page);
                } else {
                    refresh(page, starDate, endDate);
                }

            }
        });

    }

    //刷新页面数据
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
                            int ableUseGold = consumeRecordBean.getObject().getGoldNum().getGoldUsable();
                            tvAbleuse.setText(ableUseGold + "");

                            List<ConsumeRecordBean.ObjectBean.PageInfoBean.ListBean> list = consumeRecordBean.getObject().getPageInfo().getList();
                            GoldBalanceAdapter adapter = new GoldBalanceAdapter(GoldBalance.this, list);


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

    public void refresh(int page, String starDate, String endDate) {
        //调用接口请求数据
        NetUtils.getInstance().getApis().doGetConsumeRecord(userId, starDate, endDate, page, 15).
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
                            int ableUseGold = consumeRecordBean.getObject().getGoldNum().getGoldUsable();
                            tvAbleuse.setText(ableUseGold + "");
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(GoldBalance.this, RecyclerView.VERTICAL, false);
                            recyclerViewRecord.setLayoutManager(linearLayoutManager);

                            List<ConsumeRecordBean.ObjectBean.PageInfoBean.ListBean> list = consumeRecordBean.getObject().getPageInfo().getList();
                            GoldBalanceAdapter adapter = new GoldBalanceAdapter(GoldBalance.this, list);
                            //创建最新动态适配器
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

    /**
     * 获取网络时间
     *
     * @return
     */
    public static Date getWebsiteDatetime() {
        try {
            URL url = new URL("http://www.baidu.com");// 取得资源对象
            URLConnection uc = url.openConnection();// 生成连接对象
            uc.connect();// 发出连接
            long ld = uc.getDate();// 读取网站日期时间
            Date date = new Date(ld);// 转换为标准时间对象
            return date;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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
                //如果用户更改日期。拿到用户更改日期的时间段 刷新数据
                BasisTimesUtils.showDatePickerDialog(GoldBalance.this, "请选择开始年月日", 1998, 1, 1, new BasisTimesUtils.OnDatePickerListener() {

                    @Override
                    public void onConfirm(int year, int month, int dayOfMonth) {
                        starDate = year + "-" + month + "-" + dayOfMonth;
                    }

                    @Override
                    public void onCancel() {

                    }
                });
                BasisTimesUtils.showDatePickerDialog(GoldBalance.this, "请选择结束年月日", 1998, 1, 1, new BasisTimesUtils.OnDatePickerListener() {

                    @Override
                    public void onConfirm(int year, int month, int dayOfMonth) {
                        endDate = year + "-" + month + "-" + dayOfMonth;
                    }

                    @Override
                    public void onCancel() {

                    }
                });
                tvTimePeriod.setText(starDate + "~" + endDate);
                refresh(1, starDate, endDate);

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
