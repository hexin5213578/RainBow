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
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseAvtivity;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.base.Common;
import com.YiDian.RainBow.main.fragment.home.adapter.NewDynamicAdapter;
import com.YiDian.RainBow.main.fragment.home.bean.NewDynamicBean;
import com.YiDian.RainBow.main.fragment.mine.adapter.MyDynamicAdapter;
import com.YiDian.RainBow.utils.NetUtils;
import com.YiDian.RainBow.utils.SPUtil;
import com.google.gson.Gson;
import com.leaf.library.StatusBarUtil;
import com.liaoinstan.springview.container.AliFooter;
import com.liaoinstan.springview.container.AliHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.tencent.tauth.Tencent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

//发布过的动态
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
    File f = new File(
            "/data/data/com.YiDian.RainBow/shared_prefs/relesedynamic.xml");
    @Override
    protected int getResId() {
        return R.layout.activity_release_dynameics;
    }

    @Override
    protected void getData() {

        StatusBarUtil.setGradientColor(ReleaseDynamicsActivity.this,toolbar);
        StatusBarUtil.setDarkMode(ReleaseDynamicsActivity.this);

        //直接取消动画
        RecyclerView.ItemAnimator animator = rcMydraftDevelopment.getItemAnimator();
        if (animator instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
        }

        Gson gson = new Gson();
        List<NewDynamicBean.ObjectBean.ListBean> SpList = new ArrayList<>();

        for (int i = 1; i < 10; i++) {
            String json = SPUtil.getInstance().getData(ReleaseDynamicsActivity.this, SPUtil.JSON_RELESE, "json" + i);
            NewDynamicBean.ObjectBean.ListBean listBean = gson.fromJson(json, NewDynamicBean.ObjectBean.ListBean.class);
            if(listBean!=null){
                SpList.add(listBean);
            }
        }

        if(f.exists()){
            if(SpList.size()>0 && SpList!=null){
                sv.setVisibility(View.VISIBLE);
                rlNodata.setVisibility(View.GONE);

                sv.setHeader(new AliHeader(ReleaseDynamicsActivity.this));
                //创建最新动态适配器
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ReleaseDynamicsActivity.this, RecyclerView.VERTICAL, false);
                rcMydraftDevelopment.setLayoutManager(linearLayoutManager);

                MyDynamicAdapter myDynamicAdapter = new MyDynamicAdapter(ReleaseDynamicsActivity.this, SpList, mTencent);
                rcMydraftDevelopment.setAdapter(myDynamicAdapter);
            }
        }

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //腾讯AppId(替换你自己App Id)、上下文
        mTencent = Tencent.createInstance("101906973", ReleaseDynamicsActivity.this);
        myId = Integer.parseInt(Common.getUserId());

        allList = new ArrayList<>();


        dogetDynamicById(page);

        sv.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        allList.clear();
                        page = 1;
                        dogetDynamicById(page);
                        //等待2.5秒后结束刷新
                        sv.onFinishFreshAndLoad();
                    }
                },1000);
            }

            @Override
            public void onLoadmore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page++;
                        dogetDynamicById(page);
                        sv.onFinishFreshAndLoad();
                    }
                },1000);
            }
        });
    }

    //动态信息填充到列表里面
    public void dogetDynamicById(int page) {
        NetUtils.getInstance().getApis().
                doGetDynamicByUserid(myId, myId, page, 5).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.
                        mainThread()).
                subscribe(new Observer<NewDynamicBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(NewDynamicBean newDynamicBean) {
                        List<NewDynamicBean.ObjectBean.ListBean> list = newDynamicBean.getObject().getList();

                        if ( list != null && list.size() > 0) {
                            //RelativeLayout rlNodata;
                            rlNodata.setVisibility(View.GONE);
                            //RecyclerView  rcDynamic
                            rcMydraftDevelopment.setVisibility(View.VISIBLE);

                            sv.setHeader(new AliHeader(ReleaseDynamicsActivity.this));

                            //存五条数据
                            for (int i = 1; i <= list.size(); i++) {
                                NewDynamicBean.ObjectBean.ListBean listBean = list.get(i-1);
                                Gson gson = new Gson();
                                String json1 = gson.toJson(listBean);
                                SPUtil.getInstance().saveData(ReleaseDynamicsActivity.this, SPUtil.JSON_RELESE, "json"+i, json1);
                            }

                            allList.addAll(list);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ReleaseDynamicsActivity.this, RecyclerView.VERTICAL, false);
                            rcMydraftDevelopment.setLayoutManager(linearLayoutManager);

                            MyDynamicAdapter myDynamicAdapter = new MyDynamicAdapter(ReleaseDynamicsActivity.this, allList, mTencent);
                            rcMydraftDevelopment.setAdapter(myDynamicAdapter);
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
    protected void onResume() {
        super.onResume();
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
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getStr(String str){
        if (str.equals("刷新数据")){
            allList.clear();
            dogetDynamicById(1);
        }
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;

    }

}
