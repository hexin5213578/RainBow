package com.YiDian.RainBow.friend.fragment;

import android.view.View;
import android.widget.RelativeLayout;

import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseFragment;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.base.Common;
import com.YiDian.RainBow.friend.adapter.MyFansAdapter;
import com.YiDian.RainBow.friend.bean.MyFansBean;
import com.YiDian.RainBow.utils.NetUtils;
import com.liaoinstan.springview.widget.SpringView;

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

        //获取我的粉丝
    }
    public void getMyFans(int page,int size){
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
                        List<MyFansBean.ObjectBean.ListBean> list =
                                myFansBean.getObject().getList();

                        if(list.size()>0 && list!=null){
                            allList.addAll(list);

                            linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                            rcFans.setLayoutManager(linearLayoutManager);

                            MyFansAdapter myFansAdapter = new MyFansAdapter(getContext(), allList);

                            rcFans.setAdapter(myFansAdapter);
                        }else{
                            rlNodata.setVisibility(View.VISIBLE);
                            sv.setVisibility(View.GONE);
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
}
