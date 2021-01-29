package com.YiDian.RainBow.main.fragment.mine.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseAvtivity;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.base.Common;
import com.YiDian.RainBow.custom.customDialog.EveryDayDialogDialog;
import com.YiDian.RainBow.main.fragment.mine.adapter.GoldBalanceAdapter;
import com.YiDian.RainBow.main.fragment.mine.bean.ConsumeRecordBean;
import com.YiDian.RainBow.utils.BasisTimesUtils;
import com.YiDian.RainBow.utils.NetUtils;
import com.leaf.library.StatusBarUtil;
import com.liaoinstan.springview.container.AliFooter;
import com.liaoinstan.springview.container.AliHeader;
import com.liaoinstan.springview.widget.SpringView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    List<ConsumeRecordBean.ObjectBean.PageInfoBean.ListBean> alllist;
    @BindView(R.id.tv_consume_income2)
    TextView tvConsumeIncome2;

    private Intent intent;
    private int userId;
    private final String TAG = "xxx";
    int page = 1;
    String nowday = null;
    String[] datelist = new String[5];
    String selectday = "";

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
        alllist = new ArrayList<>();
        //处理业务逻辑  ;
        userId = Integer.parseInt(Common.getUserId());

        //直接取消动画
        RecyclerView.ItemAnimator animator = recyclerViewRecord.getItemAnimator();
        if (animator instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
        }

        //Log.d(TAG, "getData: " + nowday);
        //进去先刷新数据
        refresh(page);
        sv.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {

            }
            @Override
            public void onLoadmore() {
                page++;
                if (selectday.equals("")) {
                    //如果没有选择日期  就按照全部的账单加载
                    refresh(page);
                } else {
                    //如果日历选择了天数  就按照选择的天数加载
                    refresh(page, selectday);
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        sv.onFinishFreshAndLoad();
                    }
                }, 1000);

            }
        });
    }

    //刷新页面数据nowDAU
    public void refresh(int page, String nowDay) {
        tvTimePeriod.setText(selectday);
        //调用接口请求数据
        NetUtils.getInstance().getApis().doGetConsumeRecord(userId, nowDay, page, 15).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<ConsumeRecordBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ConsumeRecordBean consumeRecordBean) {
                        Log.d(TAG, "onNext: ----------------------------");
                        GoldBalanceAdapter adapter;
                        LinearLayoutManager manager = new LinearLayoutManager(GoldBalance.this, RecyclerView.VERTICAL, false);
                        List<ConsumeRecordBean.ObjectBean.PageInfoBean.ListBean> list = consumeRecordBean.getObject().getPageInfo().getList();

                        //总金币
                        int total = consumeRecordBean.getObject().getGoldNum().getGoldAll();
                        tvBalance.setText(total + "");
                        //可提现金币
                        Integer goldUsable = consumeRecordBean.getObject().getGoldNum().getGoldUsable();
                        tvAbleuse.setText(goldUsable+"");

                        //总消费
                        String totalconsume = consumeRecordBean.getObject().getSpendingGoldNum() + "";
                        //总收入
                        String totalIncome = consumeRecordBean.getObject().getIncomeGoldNum() + "";
                        tvConsumeIncome.setText("总消费：￥" + totalconsume);
                        tvConsumeIncome2.setText("总充值：￥" + totalIncome);
                        if (list!=null&&list.size()>0) {
                            sv.setVisibility(View.VISIBLE);
                            rlDefault.setVisibility(View.GONE);

                            alllist.addAll(list);
                            adapter = new GoldBalanceAdapter(GoldBalance.this, alllist);
                            //查出数据放入list
                            recyclerViewRecord.setLayoutManager(manager);
                            recyclerViewRecord.setAdapter(adapter);
                        } else {
                            if (alllist.size()>0){
                                Toast.makeText(GoldBalance.this, "没有更多内容了", Toast.LENGTH_SHORT).show();
                            }else{
                                Log.d(TAG, "onNext: 没有数据");
                                sv.setVisibility(View.GONE);
                                rlDefault.setVisibility(View.VISIBLE);
                            }
                        }
                        if (list.size()>8){
                            sv.setFooter(new AliFooter(GoldBalance.this));
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
        tvTimePeriod.setText("全部账单");
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
                        GoldBalanceAdapter adapter;
                        LinearLayoutManager manager = new LinearLayoutManager(GoldBalance.this, RecyclerView.VERTICAL, false);
                        List<ConsumeRecordBean.ObjectBean.PageInfoBean.ListBean> list = consumeRecordBean.getObject().getPageInfo().getList();
                        //总金币
                        int total = consumeRecordBean.getObject().getGoldNum().getGoldAll();
                        tvBalance.setText(total + "");
                        //可提现金币
                        Integer goldUsable = consumeRecordBean.getObject().getGoldNum().getGoldUsable();
                        tvAbleuse.setText(goldUsable+"");
                        //总消费
                        String totalconsume = consumeRecordBean.getObject().getSpendingGoldNum() + "";
                        //总收入
                        String totalIncome = consumeRecordBean.getObject().getIncomeGoldNum() + "";
                        tvConsumeIncome.setText("总消费：￥" + totalconsume);
                        tvConsumeIncome2.setText("总充值：￥" + totalIncome);

                        if (list!=null&&list.size()>0) {
                            rlDefault.setVisibility(View.GONE);
                            sv.setVisibility(View.VISIBLE);

                            alllist.addAll(list);
                            adapter = new GoldBalanceAdapter(GoldBalance.this, alllist);
                            recyclerViewRecord.setLayoutManager(manager);
                            recyclerViewRecord.setAdapter(adapter);
                            recyclerViewRecord.setVisibility(View.VISIBLE);

                        } else {
                            if (alllist.size()>0){
                                Toast.makeText(GoldBalance.this, "没有更多内容了", Toast.LENGTH_SHORT).show();
                            }else{
                                rlDefault.setVisibility(View.VISIBLE);
                                sv.setVisibility(View.GONE);
                            }
                            Log.d(TAG, "onNext: 查询数据失败");
                        }
                        if (list.size()>10){
                            sv.setFooter(new AliFooter(GoldBalance.this));
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
        EveryDayDialogDialog.Builder builder = new EveryDayDialogDialog.Builder(GoldBalance.this);
        switch (v.getId()) {
            case R.id.l_return:
                //返回
                finish();
                break;
            case R.id.tv_cash:
                //跳转到提现界面
//                intent = new Intent(GoldBalance.this, CashActivity.class);
//                startActivity(intent);
                builder.setMessage("该功能暂未开放").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //签到 补签
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builder.create().show();
                break;
            case R.id.tv_Recharge:
                //跳转到充值页面
//                intent = new Intent(GoldBalance.this, RechargeGlodActivity.class);
//                startActivity(intent);
                builder = new EveryDayDialogDialog.Builder(GoldBalance.this);
                builder.setMessage("该功能暂未开放").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //签到 补签
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builder.create().show();
                break;
            case R.id.iv_date:
                Log.d(TAG, "onClick: 点击了日期");
                //拿到当天时间限制用户选择大于今天的时间
                Date currentTime = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                nowday = formatter.format(currentTime);
                datelist = nowday.split("-");
                //如果用户更改日期。拿到用户更改日期的时间段 刷新数据
                BasisTimesUtils.showDatePickerDialog(GoldBalance.this, "请选择日期", Integer.valueOf(datelist[0]),
                        Integer.valueOf(datelist[1]), Integer.valueOf(datelist[2]), new BasisTimesUtils.OnDatePickerListener() {

                            @Override
                            public void onConfirm(int year, int month, int dayOfMonth) {
                                selectday = year + "-" + month + "-" + dayOfMonth;
                                Log.d(TAG, "onConfirm: "+selectday);
                                alllist.clear();
                                refresh(1, selectday);
                            }

                            @Override
                            public void onCancel() {

                            }
                        });
                break;
        }

    }
}
