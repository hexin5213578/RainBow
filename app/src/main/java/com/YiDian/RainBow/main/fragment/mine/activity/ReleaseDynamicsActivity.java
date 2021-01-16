package com.YiDian.RainBow.main.fragment.mine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseAvtivity;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.base.Common;
import com.YiDian.RainBow.main.fragment.home.adapter.NewDynamicAdapter;
import com.YiDian.RainBow.main.fragment.home.bean.NewDynamicBean;
import com.YiDian.RainBow.utils.NetUtils;
import com.liaoinstan.springview.container.AliFooter;
import com.liaoinstan.springview.container.AliHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.tencent.tauth.Tencent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ReleaseDynamicsActivity extends BaseAvtivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_back)
    LinearLayout ivBack;
    @BindView(R.id.rl_top)
    RelativeLayout rlTop;
    @BindView(R.id.rl_nodata)
    RelativeLayout rlNodata;
    @BindView(R.id.rc_mydraft_development)
    RecyclerView rcMydraftDevelopment;
    @BindView(R.id.sv)
    SpringView sv;
    private Tencent mTencent;
    private int myId;
    int page  =1;
    List<NewDynamicBean.ObjectBean.ListBean> allList;

    @Override
    protected int getResId() {
        return R.layout.activity_release_dynameics;
    }

    @Override
    protected void getData() {
        //腾讯AppId(替换你自己App Id)、上下文
        mTencent = Tencent.createInstance("101906973", ReleaseDynamicsActivity.this);
        myId = Integer.parseInt(Common.getUserId());

        allList = new ArrayList<>();

        sv.setHeader(new AliHeader(this));

        sv.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        allList.clear();
                        page = 1;
                        dogetDynamicById(page,myId);
                        //等待2.5秒后结束刷新
                        sv.onFinishFreshAndLoad();
                    }
                },2500);
            }

            @Override
            public void onLoadmore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page++;
                        dogetDynamicById(page,myId);
                        sv.onFinishFreshAndLoad();
                    }
                },2500);
            }
        });
    }




    //动态信息填充到列表里面
    public void dogetDynamicById(int page, int thePageuserId) {
        showDialog();//显示加载圈
        NetUtils.getInstance().getApis().
                doGetDynamicByUserid(thePageuserId, myId, page, 5).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.
                        mainThread()).
                subscribe(new Observer<NewDynamicBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(NewDynamicBean newDynamicBean) {
                        hideDialog();//隐藏加载圈
                        List<NewDynamicBean.ObjectBean.ListBean> list = newDynamicBean.getObject().getList();

                        if (list.size() > 0 && list != null) {
                            //RelativeLayout rlNodata;
                            rlNodata.setVisibility(View.GONE);
                            //RecyclerView  rcDynamic
                            rcMydraftDevelopment.setVisibility(View.VISIBLE);

                            allList.addAll(list);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ReleaseDynamicsActivity.this, RecyclerView.VERTICAL, false);
                            rcMydraftDevelopment.setLayoutManager(linearLayoutManager);

                            NewDynamicAdapter newDynamicAdapter = new NewDynamicAdapter(ReleaseDynamicsActivity.this, allList, mTencent);
                            rcMydraftDevelopment.setAdapter(newDynamicAdapter);
                        } else {
                            if (allList.size() > 0) {
                                Toast.makeText(ReleaseDynamicsActivity.this, "没有更多内容了", Toast.LENGTH_SHORT).show();
                            } else {
                                rlNodata.setVisibility(View.VISIBLE);
                                rcMydraftDevelopment.setVisibility(View.GONE);
                            }
                        }
                        if (list.size() > 4) {
                            //设置底部
                            sv.setFooter(new AliFooter(ReleaseDynamicsActivity.this));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        hideDialog();
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
}
