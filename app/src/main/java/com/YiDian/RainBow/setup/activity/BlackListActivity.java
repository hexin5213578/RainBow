package com.YiDian.RainBow.setup.activity;

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
import com.YiDian.RainBow.setup.adapter.BlackListAdapter;
import com.YiDian.RainBow.setup.bean.BlackListBean;
import com.YiDian.RainBow.utils.NetUtils;
import com.leaf.library.StatusBarUtil;
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

//黑名单
public class BlackListActivity extends BaseAvtivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_back)
    LinearLayout ivBack;
    @BindView(R.id.rc_list)
    RecyclerView rcList;
    @BindView(R.id.sv)
    SpringView sv;
    int page = 1;
    int size = 15;
    @BindView(R.id.rl_nodata)
    RelativeLayout rlNodata;
    private int userid;
    List<BlackListBean.ObjectBean.ListBean> allList;

    @Override
    protected int getResId() {
        return R.layout.activity_blacklist;
    }

    @Override
    protected void getData() {
        StatusBarUtil.setGradientColor(BlackListActivity.this, toolbar);
        StatusBarUtil.setDarkMode(BlackListActivity.this);

        allList = new ArrayList<>();

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        userid = Integer.valueOf(Common.getUserId());
        //获取数据
        getBlackFriend(page,size);

        sv.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page = 1;
                        allList.clear();
                        getBlackFriend(page,size);
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
                        getBlackFriend(page,size);
                        sv.onFinishFreshAndLoad();
                    }
                }, 1000);
            }
        });
    }

    public void getBlackFriend(int page, int size) {
        NetUtils.getInstance().getApis()
                .doGetBlackFriend(userid, page, size)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BlackListBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BlackListBean blackListBean) {
                        List<BlackListBean.ObjectBean.ListBean> list = blackListBean.getObject().getList();

                        if (list.size() > 0 && list != null) {
                            sv.setVisibility(View.VISIBLE);
                            rlNodata.setVisibility(View.GONE);

                            allList.addAll(list);

                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(BlackListActivity.this, RecyclerView.VERTICAL, false);
                            rcList.setLayoutManager(linearLayoutManager);

                            BlackListAdapter blackListAdapter = new BlackListAdapter(BlackListActivity.this, allList);
                            rcList.setAdapter(blackListAdapter);
                        }else{
                            if (allList.size()>0 && allList!=null){
                                Toast.makeText(BlackListActivity.this, "没有更多内容了", Toast.LENGTH_SHORT).show();
                            }else{
                                sv.setVisibility(View.GONE);
                                rlNodata.setVisibility(View.VISIBLE);
                            }
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

    @Override
    protected void onResume() {
        super.onResume();
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getString(String str){
        if (str.equals("刷新界面")){
            allList.clear();
            getBlackFriend(1,size);
        }
    }
    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

}
