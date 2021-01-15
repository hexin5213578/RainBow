package com.YiDian.RainBow.main.fragment.mine.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseAvtivity;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.base.Common;
import com.YiDian.RainBow.main.fragment.mine.adapter.FangkeAdapter;
import com.YiDian.RainBow.main.fragment.mine.bean.FangkeMsgBean;
import com.YiDian.RainBow.utils.NetUtils;
import com.leaf.library.StatusBarUtil;
import com.liaoinstan.springview.container.AliFooter;
import com.liaoinstan.springview.container.AliHeader;
import com.liaoinstan.springview.widget.SpringView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

//我的访客
public class FangkerecordActivity extends BaseAvtivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_back)
    LinearLayout ivBack;
    @BindView(R.id.tv_all_count)
    TextView tvAllCount;
    @BindView(R.id.tv_today_count)
    TextView tvTodayCount;
    @BindView(R.id.rc_record)
    RecyclerView rcRecord;
    @BindView(R.id.sv)
    SpringView sv;
    @BindView(R.id.rl_nodata)
    RelativeLayout rlNodata;
    private int userid;
    int page = 1;
    int size = 15;
    private List<FangkeMsgBean.ObjectBean.MeetsBean> allList;

    @Override
    protected int getResId() {
        return R.layout.activity_fangkerecord;
    }

    @Override
    protected void getData() {
        StatusBarUtil.setGradientColor(FangkerecordActivity.this, toolbar);
        StatusBarUtil.setDarkMode(FangkerecordActivity.this);

        userid = Integer.valueOf(Common.getUserId());
        allList = new ArrayList<>();

        getMyFangke(page,size);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        sv.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page = 1;
                        allList.clear();
                        getMyFangke(page,size);

                        sv.onFinishFreshAndLoad();
                    }
                }, 1000);
            }

            @Override
            public void onLoadmore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page++;
                        getMyFangke(page,size);

                        sv.onFinishFreshAndLoad();
                    }
                }, 1000);
            }
        });
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    public void getMyFangke(int page, int size) {
        NetUtils.getInstance().getApis()
                .dogetMyFangke(userid, page, size)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<FangkeMsgBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(FangkeMsgBean fangkeMsgBean) {

                        tvTodayCount.setText("+"+fangkeMsgBean.getObject().getMeetToDayNum());
                        tvAllCount.setText(""+fangkeMsgBean.getObject().getMeetAllDayNum());

                        List<FangkeMsgBean.ObjectBean.MeetsBean> list =
                                fangkeMsgBean.getObject().getMeets();

                        if (list.size() > 0 && list != null) {
                            sv.setHeader(new AliHeader(FangkerecordActivity.this));

                            sv.setVisibility(View.VISIBLE);
                            rlNodata.setVisibility(View.GONE);

                            allList.addAll(list);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(FangkerecordActivity.this, RecyclerView.VERTICAL, false);
                            rcRecord.setLayoutManager(linearLayoutManager);

                            FangkeAdapter fangkeAdapter = new FangkeAdapter(FangkerecordActivity.this, allList);
                            rcRecord.setAdapter(fangkeAdapter);
                        } else {
                            if (allList.size() > 0 && allList!=null) {
                                Toast.makeText(FangkerecordActivity.this, "没有更多记录了", Toast.LENGTH_SHORT).show();
                            } else {
                                sv.setVisibility(View.GONE);
                                rlNodata.setVisibility(View.VISIBLE);
                            }
                        }

                        if (list.size()>14){
                            sv.setFooter(new AliFooter(FangkerecordActivity.this));
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getStr(String str) {
        if (str.equals("刷新访客页面")) {
            allList.clear();
            getMyFangke(1, 15);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
