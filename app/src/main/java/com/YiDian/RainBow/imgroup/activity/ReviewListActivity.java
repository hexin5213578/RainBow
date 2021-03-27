package com.YiDian.RainBow.imgroup.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseAvtivity;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.base.Common;
import com.YiDian.RainBow.custom.loading.CustomDialog;
import com.YiDian.RainBow.imgroup.adapter.ReviewListAdapter;
import com.YiDian.RainBow.imgroup.bean.ShenHeListBean;
import com.YiDian.RainBow.utils.NetUtils;
import com.leaf.library.StatusBarUtil;
import com.liaoinstan.springview.container.AliFooter;
import com.liaoinstan.springview.container.AliHeader;
import com.liaoinstan.springview.widget.SpringView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author hmy
 * @date 2021/3/12
 * 审核列表
 */
public class ReviewListActivity extends BaseAvtivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_back)
    LinearLayout ivBack;
    @BindView(R.id.rl_top)
    RelativeLayout rlTop;
    @BindView(R.id.rc_list)
    RecyclerView rcList;
    @BindView(R.id.sv)
    SpringView sv;
    @BindView(R.id.rl_nodata)
    RelativeLayout rlNodata;
    private int id;
    private int jgid;
    private int userid;
    int page = 1;
    int size = 15;
    private CustomDialog dialog;

    @Override
    protected int getResId() {
        return R.layout.activity_reviewlist;
    }

    @Override
    protected void getData() {
        StatusBarUtil.setGradientColor(ReviewListActivity.this, toolbar);
        StatusBarUtil.setDarkMode(ReviewListActivity.this);

        dialog = new CustomDialog(ReviewListActivity.this, "正在加载...");


        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        sv.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                getList(page, size);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        sv.onFinishFreshAndLoad();
                    }
                }, 1000);
            }

            @Override
            public void onLoadmore() {
                page++;
                getList(page, size);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        sv.onFinishFreshAndLoad();
                    }
                }, 1000);
            }
        });

        userid = Integer.valueOf(Common.getUserId());

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        jgid = intent.getIntExtra("jgid", 0);

        Log.d("xxx", "群id为:" + id + "极光id为:" + jgid);

        //获取审核列表
        getList(page, size);
    }

    public void getList(int page, int size) {
        dialog.show();
        NetUtils.getInstance().getApis().doGetReviewList(userid, id,page, size)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ShenHeListBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull ShenHeListBean shenHeListBean) {
                        dialog.dismiss();
                        List<ShenHeListBean.ObjectBean.ListBean> list =
                                shenHeListBean.getObject().getList();

                        if (list!=null && list.size() > 0) {
                            sv.setHeader(new AliHeader(ReviewListActivity.this));


                            sv.setVisibility(View.VISIBLE);
                            rlNodata.setVisibility(View.GONE);

                            //创建适配器
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ReviewListActivity.this, RecyclerView.VERTICAL, false);
                            rcList.setLayoutManager(linearLayoutManager);

                            ReviewListAdapter reviewListAdapter = new ReviewListAdapter(ReviewListActivity.this, list,jgid,id);
                            rcList.setAdapter(reviewListAdapter);

                        }else{
                            sv.setVisibility(View.GONE);
                            rlNodata.setVisibility(View.VISIBLE);
                        }

                        if (list.size()>14){
                            sv.setFooter(new AliFooter(ReviewListActivity.this));
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        dialog.dismiss();

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
    public void getStr(String str){
        if (str.equals("刷新审核列表")){
            getList(1,size);
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)){
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
