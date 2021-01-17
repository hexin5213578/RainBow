package com.YiDian.RainBow.main.fragment.mine.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseAvtivity;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.base.Common;
import com.YiDian.RainBow.main.fragment.mine.bean.SigninMsgBean;
import com.YiDian.RainBow.utils.NetUtils;
import com.leaf.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class EveryDayRegisterActivity extends BaseAvtivity implements View.OnClickListener {


    private static final String TAG = "xxxx";
    @BindView(R.id.tv_days)
    TextView tvDays;
    @BindView(R.id.tv_zhouyi)
    TextView tvZhouyi;
    @BindView(R.id.tv_zhouyi_count)
    TextView tvZhouyiCount;
    @BindView(R.id.rl_zhouyi)
    RelativeLayout rlZhouyi;
    @BindView(R.id.tv_zhouer)
    TextView tvZhouer;
    @BindView(R.id.tv_zhouer_count)
    TextView tvZhouerCount;
    @BindView(R.id.rl_zhouer)
    RelativeLayout rlZhouer;
    @BindView(R.id.tv_zhousan)
    TextView tvZhousan;
    @BindView(R.id.tv_zhousan_count)
    TextView tvZhousanCount;
    @BindView(R.id.rl_zhousan)
    RelativeLayout rlZhousan;
    @BindView(R.id.tv_zhousi)
    TextView tvZhousi;
    @BindView(R.id.tv_zhousi_count)
    TextView tvZhousiCount;
    @BindView(R.id.rl_zhousi)
    RelativeLayout rlZhousi;
    @BindView(R.id.tv_zhouwu)
    TextView tvZhouwu;
    @BindView(R.id.tv_zhouwu_count)
    TextView tvZhouwuCount;
    @BindView(R.id.rl_zhouwu)
    RelativeLayout rlZhouwu;
    @BindView(R.id.tv_zhouliu)
    TextView tvZhouliu;
    @BindView(R.id.tv_zhouliu_count)
    TextView tvZhouliuCount;
    @BindView(R.id.rl_zhouliu)
    RelativeLayout rlZhouliu;
    @BindView(R.id.tv_zhouri)
    TextView tvZhouri;
    @BindView(R.id.tv_zhouri_count)
    TextView tvZhouriCount;
    @BindView(R.id.rl_zhouri)
    RelativeLayout rlZhouri;
    @BindView(R.id.bt_qiandao)
    Button btQiandao;
    @BindView(R.id.rc_task)
    RecyclerView rcTask;


    public SigninMsgBean signinMsgBean;
    @BindView(R.id.l_black)
    LinearLayout lBlack;

    /**
     * tv _zhouyi  天数 未签到换成补签
     * rl_zhouyi   每日签到的背景
     * tv_zhouyi count  当天签到金币数
     */
    @Override
    protected int getResId() {
        return R.layout.activity_everydayregister;
    }

    @Override
    protected void getData() {
        //
        btQiandao.setOnClickListener(this);


        //设置状态栏透明
        StatusBarUtil.setTransparentForWindow(EveryDayRegisterActivity.this);
        Integer userId = Integer.parseInt(Common.getUserId());
        Log.d(TAG, "getData: 1" + userId);
        Toast.makeText(EveryDayRegisterActivity.this, "1" + userId, Toast.LENGTH_SHORT).show();

        //拿到数据
        NetUtils.getInstance().getApis().

                doGetSigninMsg(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SigninMsgBean>() {

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(SigninMsgBean signinMsgBean) {

                        try {
                            Toast.makeText(EveryDayRegisterActivity.this, "2", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onNext: 2");
                            idSign(signinMsgBean);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: 网络请求失败");
                        Toast.makeText(EveryDayRegisterActivity.this, "网络请求失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    public void signinMsgBean() {

    }

    public void idSign(SigninMsgBean signinMsgBean) throws Exception {
        //判断当天是否签到
        List<SigninMsgBean.ObjectBean> list = signinMsgBean.getObject();

        ArrayList<TextView> list1 = new ArrayList<>();
        ArrayList<View> list2 = new ArrayList<>();
        ArrayList<View> list3 = new ArrayList<>();
        //头
        list1.add(tvZhouyi);
        list1.add(tvZhouer);
        list1.add(tvZhousan);
        list1.add(tvZhousi);
        list1.add(tvZhouwu);
        list1.add(tvZhouliu);
        list1.add(tvZhouri);
        //背景
        list2.add(rlZhouyi);
        list2.add(rlZhouer);
        list2.add(rlZhousan);
        list2.add(rlZhousi);
        list2.add(rlZhouwu);
        list2.add(rlZhouliu);
        list2.add(rlZhouri);
        //金币
        list3.add(rlZhouyi);
        list3.add(rlZhouer);
        list3.add(rlZhousan);
        list3.add(rlZhousi);
        list3.add(rlZhouwu);
        list3.add(rlZhouliu);
        list3.add(rlZhouri);

        int td = getWeekbyDate(new Date());
        Toast.makeText(EveryDayRegisterActivity.this, "3", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "idSign: 今天是=" + td);
        TextView view;
        View view1;
        for (int i = 1; i < 7; i++) {
            view = list1.get(i - 1);
            view1 = list2.get(i - 1);
            if (i < td) {
                //今天之前的操作
                Log.d(TAG, "idSign: 今天之前=" + i);
                //判断是否签到设置背景
                if (list.get(i - 1).getIsSign() == 1) {
                    //设置为签到
                    view.setBackgroundResource(R.drawable.select_qiandao_yiqiandao);
                    view1.setBackgroundResource(R.drawable.select_qiandao_yiqiandao_bg);
                    //禁止签到
                    view1.isEnabled();

                } else {
                    //设置为未签到
                    view.setBackgroundResource(R.drawable.select_qiandao_weiqiandao);
                    view1.setBackgroundResource(R.drawable.select_qiandao_weiqiandao_bg);
                    //设置补签
                    view.setText("点击补签");
                    view.setOnClickListener(this);
                }


            } else if (i == td) {
                btQiandao.setOnClickListener(this);
                view1.isEnabled();          //不允许再次签到
                Log.d(TAG, "idSign: 今天的操作=" + i);
                //设置今天的操作
                if (list.get(i - 1).getIsSign() == 1) {
                    btQiandao.setText("已签到");
                    //设置为签到

                    view.setBackgroundResource(R.drawable.select_qiandao_yiqiandao);
                    view1.setBackgroundResource(R.drawable.select_qiandao_yiqiandao_bg);

                } else {
                    btQiandao.setText("未签到");
                    //设置为未签到
                    view.setBackgroundResource(R.drawable.select_qiandao_weiqiandao);
                    view1.setBackgroundResource(R.drawable.select_qiandao_weiqiandao_bg);
                }

            } else {
                Log.d(TAG, "idSign: 今天的操作=" + i);
                //设置今天之后的操作
                view1.isEnabled();
                view.setBackgroundResource(R.drawable.select_qiandao_weiqiandao);
                view1.setBackgroundResource(R.drawable.select_qiandao_weiqiandao_bg);

            }

        }

        //
    }

    public void setsign() {

    }


    public static int getWeekbyDate(Date pTime) throws Exception {
        Calendar c = Calendar.getInstance();
        c.setTime(pTime);
        int dayForWeek = 0;
        if (c.get(Calendar.DAY_OF_WEEK) == 1) {
            dayForWeek = 7;
        } else {
            dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
        }
        return dayForWeek;
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_qiandao:
                Log.d(TAG, "onClick: 开始签到");
                break;
            case R.id.rl_zhouyi:
                Log.d(TAG, "onClick: 开始签到");
                break;
            case R.id.rl_zhouer:
                Log.d(TAG, "onClick: 开始签到");
                break;
            case R.id.rl_zhousan:
                Log.d(TAG, "onClick: 开始签到");
                break;
            case R.id.rl_zhousi:
                Log.d(TAG, "onClick: 开始签到");
                break;
            case R.id.rl_zhouwu:
                Log.d(TAG, "onClick: 开始签到");
                break;
            case R.id.rl_zhouliu:
                Log.d(TAG, "onClick: 开始签到");
                break;
            case R.id.rl_zhouri:
                Log.d(TAG, "onClick: 开始签到");
                break;
            case R.id.l_black:

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
