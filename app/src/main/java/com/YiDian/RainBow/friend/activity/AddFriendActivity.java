package com.YiDian.RainBow.friend.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseAvtivity;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.base.Common;
import com.YiDian.RainBow.friend.bean.RecommendGroupBean;
import com.YiDian.RainBow.friend.bean.RecommendUserBean;
import com.YiDian.RainBow.utils.NetUtils;
import com.leaf.library.StatusBarUtil;
import com.liaoinstan.springview.container.AliHeader;
import com.liaoinstan.springview.widget.SpringView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AddFriendActivity extends BaseAvtivity implements View.OnClickListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_back)
    LinearLayout ivBack;
    @BindView(R.id.ll_tosearch)
    LinearLayout llTosearch;
    @BindView(R.id.rc_recommendfriend)
    RecyclerView rcRecommendfriend;
    @BindView(R.id.rc_recommendgroup)
    RecyclerView rcRecommendgroup;
    @BindView(R.id.sv)
    SpringView sv;
    private int userid;

    @Override
    protected int getResId() {
        return R.layout.activity_addfriend;
    }

    @Override
    protected void getData() {
        StatusBarUtil.setGradientColor(AddFriendActivity.this, toolbar);
        StatusBarUtil.setDarkMode(AddFriendActivity.this);

        userid = Integer.valueOf(Common.getUserId());

        sv.setHeader(new AliHeader(this));


        //获取数据
        getRecommendFriend();
        getRecommendGroup();

        sv.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getRecommendFriend();
                        getRecommendGroup();
                    }
                },1000);
            }

            @Override
            public void onLoadmore() {

            }
        });
        ivBack.setOnClickListener(this);
        llTosearch.setOnClickListener(this);
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    //获取推荐好友
    public void getRecommendFriend(){
        NetUtils.getInstance()
                .getApis()
                .dogetRecommendFriend(userid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RecommendUserBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(RecommendUserBean recommendUserBean) {
                        List<RecommendUserBean.ObjectBean> list = recommendUserBean.getObject();

                        
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    //获取推荐群组
    public void getRecommendGroup(){
        NetUtils.getInstance()
                .getApis()
                .dogetRecommendGroup(userid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RecommendGroupBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(RecommendGroupBean recommendGroupBean) {

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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                //返回
                finish();
                break;
            case R.id.ll_tosearch:
                //跳转到搜索好友页

                break;
        }
    }

}
