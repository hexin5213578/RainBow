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
        //????????????
        lReturn.setOnClickListener(this);
        tvCash.setOnClickListener(this);
        tvRecharge.setOnClickListener(this);
        ivDate.setOnClickListener(this);
        //????????????????????????????????????
        StatusBarUtil.setGradientColor(GoldBalance.this, toolbar);
        StatusBarUtil.setDarkMode(GoldBalance.this);
        alllist = new ArrayList<>();
        //??????????????????  ;
        userId = Integer.parseInt(Common.getUserId());

        //??????????????????
        RecyclerView.ItemAnimator animator = recyclerViewRecord.getItemAnimator();
        if (animator instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
        }

        //Log.d(TAG, "getData: " + nowday);
        //?????????????????????
        refresh(page);
        sv.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {

            }
            @Override
            public void onLoadmore() {
                page++;
                if (selectday.equals("")) {
                    //????????????????????????  ??????????????????????????????
                    refresh(page);
                } else {
                    //???????????????????????????  ??????????????????????????????
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

    //??????????????????nowDAU
    public void refresh(int page, String nowDay) {
        tvTimePeriod.setText(selectday);
        //????????????????????????
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

                        //?????????
                        int total = consumeRecordBean.getObject().getGoldNum().getGoldAll();
                        tvBalance.setText(total + "");
                        //???????????????
                        Integer goldUsable = consumeRecordBean.getObject().getGoldNum().getGoldUsable();
                        tvAbleuse.setText(goldUsable+"");

                        //?????????
                        String totalconsume = consumeRecordBean.getObject().getSpendingGoldNum() + "";
                        //?????????
                        String totalIncome = consumeRecordBean.getObject().getIncomeGoldNum() + "";
                        tvConsumeIncome.setText("???????????????" + totalconsume);
                        tvConsumeIncome2.setText("???????????????" + totalIncome);
                        if (list!=null&&list.size()>0) {
                            sv.setVisibility(View.VISIBLE);
                            rlDefault.setVisibility(View.GONE);

                            alllist.addAll(list);
                            adapter = new GoldBalanceAdapter(GoldBalance.this, alllist);
                            //??????????????????list
                            recyclerViewRecord.setLayoutManager(manager);
                            recyclerViewRecord.setAdapter(adapter);
                        } else {
                            if (alllist.size()>0){
                                Toast.makeText(GoldBalance.this, "?????????????????????", Toast.LENGTH_SHORT).show();
                            }else{
                                Log.d(TAG, "onNext: ????????????");
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
        tvTimePeriod.setText("????????????");
        //????????????????????????
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
                        //?????????
                        int total = consumeRecordBean.getObject().getGoldNum().getGoldAll();
                        tvBalance.setText(total + "");
                        //???????????????
                        Integer goldUsable = consumeRecordBean.getObject().getGoldNum().getGoldUsable();
                        tvAbleuse.setText(goldUsable+"");
                        //?????????
                        String totalconsume = consumeRecordBean.getObject().getSpendingGoldNum() + "";
                        //?????????
                        String totalIncome = consumeRecordBean.getObject().getIncomeGoldNum() + "";
                        tvConsumeIncome.setText("???????????????" + totalconsume);
                        tvConsumeIncome2.setText("???????????????" + totalIncome);

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
                                Toast.makeText(GoldBalance.this, "?????????????????????", Toast.LENGTH_SHORT).show();
                            }else{
                                rlDefault.setVisibility(View.VISIBLE);
                                sv.setVisibility(View.GONE);
                            }
                            Log.d(TAG, "onNext: ??????????????????");
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
                //??????
                finish();
                break;
            case R.id.tv_cash:
                //?????????????????????
//                intent = new Intent(GoldBalance.this, CashActivity.class);
//                startActivity(intent);
                builder.setMessage("?????????????????????").setPositiveButton("??????", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //?????? ??????
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("??????",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builder.create().show();
                break;
            case R.id.tv_Recharge:
                //?????????????????????
//                intent = new Intent(GoldBalance.this, RechargeGlodActivity.class);
//                startActivity(intent);
                builder = new EveryDayDialogDialog.Builder(GoldBalance.this);
                builder.setMessage("?????????????????????").setPositiveButton("??????", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //?????? ??????
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("??????",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builder.create().show();
                break;
            case R.id.iv_date:
                Log.d(TAG, "onClick: ???????????????");
                //?????????????????????????????????????????????????????????
                Date currentTime = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                nowday = formatter.format(currentTime);
                datelist = nowday.split("-");
                //??????????????????????????????????????????????????????????????? ????????????
                BasisTimesUtils.showDatePickerDialog(GoldBalance.this, "???????????????", Integer.valueOf(datelist[0]),
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
