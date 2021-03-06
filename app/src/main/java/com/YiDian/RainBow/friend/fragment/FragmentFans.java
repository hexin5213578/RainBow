package com.YiDian.RainBow.friend.fragment;

import android.os.Handler;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseFragment;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.base.Common;
import com.YiDian.RainBow.custom.loading.CustomDialog;
import com.YiDian.RainBow.friend.adapter.ContactAdapter;
import com.YiDian.RainBow.friend.adapter.MyFansAdapter;
import com.YiDian.RainBow.friend.bean.FriendBean;
import com.YiDian.RainBow.friend.bean.MyFansBean;
import com.YiDian.RainBow.utils.NetUtils;
import com.YiDian.RainBow.utils.SPUtil;
import com.google.gson.Gson;
import com.liaoinstan.springview.container.AliFooter;
import com.liaoinstan.springview.container.AliHeader;
import com.liaoinstan.springview.widget.SpringView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FragmentFans extends BaseFragment {
    @BindView(R.id.rc_fans)
    RecyclerView rcFans;
    @BindView(R.id.sv)
    SpringView sv;
    @BindView(R.id.rl_nodata)
    RelativeLayout rlNodata;
    private int userid;
    private List<MyFansBean.ObjectBean.ListBean> allList;
    private LinearLayoutManager linearLayoutManager;
    private MyFansAdapter myFansAdapter;
    int page = 1;
    int size = 15;

    private CustomDialog dialog;

    @Override
    protected void getid(View view) {

    }

    @Override
    protected int getResId() {
        return R.layout.fragment_fans;
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void getData() {
        userid = Integer.valueOf(Common.getUserId());
        allList = new ArrayList<>();
        //??????????????????
        RecyclerView.ItemAnimator animator = rcFans.getItemAnimator();
        if (animator instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
        }


        dialog = new CustomDialog(getContext(), "????????????...");

        //??????????????????
        getMyFans(page,size);

        sv.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                allList.clear();
                page = 1;
                getMyFans(page,size);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        sv.onFinishFreshAndLoad();
                    }
                },1000);
            }

            @Override
            public void onLoadmore() {
                page++;
                getMyFans(page,size);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        sv.onFinishFreshAndLoad();
                    }
                },1000);
            }
        });
    }
    public void getMyFans(int page,int size){
        dialog.show();
        NetUtils.getInstance().getApis()
                .doGetMyFans(userid,page,size)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MyFansBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(MyFansBean myFansBean) {

                        dialog.dismiss();

                        List<MyFansBean.ObjectBean.ListBean> list =
                                myFansBean.getObject().getList();

                        if(list.size()>0 && list!=null){
                            allList.addAll(list);

                            sv.setVisibility(View.VISIBLE);
                            rlNodata.setVisibility(View.GONE);

                            sv.setHeader(new AliHeader(getContext()));

                            linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                            rcFans.setLayoutManager(linearLayoutManager);

                            myFansAdapter = new MyFansAdapter(getContext(), allList);

                            rcFans.setAdapter(myFansAdapter);
                        }else{
                            if(allList.size()>0 && allList!=null){
                                linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                                rcFans.setLayoutManager(linearLayoutManager);

                                myFansAdapter = new MyFansAdapter(getContext(), allList);

                                rcFans.setAdapter(myFansAdapter);
                            }else{
                                rlNodata.setVisibility(View.VISIBLE);
                                sv.setVisibility(View.GONE);
                            }

                        }
                        if(list.size()>14){
                            sv.setFooter(new AliFooter(getContext()));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        dialog.dismiss();

                        Toast.makeText(getContext(), "??????????????????", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getStr (String str){
        if(str.equals("??????????????????")){
            allList.clear();
            getMyFans(page,size);
        }
    }
}
