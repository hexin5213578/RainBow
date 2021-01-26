package com.YiDian.RainBow.main.fragment.find.fragment.meetfragment;

import android.os.Handler;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseFragment;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.base.Common;
import com.YiDian.RainBow.custom.loading.CustomDialog;
import com.YiDian.RainBow.main.fragment.find.adapter.AllLikeAdapter;
import com.YiDian.RainBow.main.fragment.find.adapter.MyLikeAdapter;
import com.YiDian.RainBow.main.fragment.find.bean.AllLikeBean;
import com.YiDian.RainBow.main.fragment.find.bean.UserMySeeBean;
import com.YiDian.RainBow.utils.NetUtils;
import com.liaoinstan.springview.container.AliFooter;
import com.liaoinstan.springview.container.AliHeader;
import com.liaoinstan.springview.widget.SpringView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

//互相喜欢
public class AllLikeFragment extends BaseFragment {
    @BindView(R.id.rc_alllike)
    RecyclerView rcallLike;
    @BindView(R.id.sv)
    SpringView sv;
    @BindView(R.id.rl_nodata)
    RelativeLayout rlNodata;
    int page = 1;
    int size = 15;
    private boolean isfirst;
    private List<AllLikeBean.ObjectBean.ListBean> allList;
    private LinearLayoutManager linearLayoutManager;
    private AllLikeAdapter allLikeAdapter;
    private int userid;
    private CustomDialog dialog;

    @Override
    protected void getid(View view) {

    }

    @Override
    protected int getResId() {
        return R.layout.home_find_meet_alllike_fragment;
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void getData() {
        allList = new ArrayList<>();
        userid = Integer.valueOf(Common.getUserId());
        dialog = new CustomDialog(getContext(), "正在加载...");

        isfirst = true;
        getStr(page, size);
        sv.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                page = 1;

                allList.clear();

                getStr(page, size);
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

                getStr(page, size);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {


                        sv.onFinishFreshAndLoad();
                    }
                }, 1000);
            }
        });
    }

    public void getStr(int page, int size) {
        dialog.show();
        NetUtils.getInstance().getApis()
                .doGetAllLike(userid, page, size)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AllLikeBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(AllLikeBean bean) {
                        dialog.dismiss();
                        List<AllLikeBean.ObjectBean.ListBean> list = bean.getObject().getList();
                        if (list.size() > 0 && list != null) {
                            sv.setVisibility(View.VISIBLE);
                            rlNodata.setVisibility(View.GONE);

                            sv.setHeader(new AliHeader(getContext()));
                            allList.addAll(list);
                            linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                            rcallLike.setLayoutManager(linearLayoutManager);

                            allLikeAdapter = new AllLikeAdapter(getContext(), allList);

                            rcallLike.setAdapter(allLikeAdapter);
                        } else {
                            if (allList.size() > 0 && allList != null) {
                                Toast.makeText(getContext(), "没有更多内容了", Toast.LENGTH_SHORT).show();
                            } else {
                                rlNodata.setVisibility(View.VISIBLE);
                                sv.setVisibility(View.GONE);
                            }
                        }
                        if (list.size() > 14) {
                            sv.setFooter(new AliFooter(getContext()));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        dialog.dismiss();

                        Toast.makeText(getContext(), "请求失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    //接收关注成功 取消关注成功后的处理
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getStr(String str) {
        if (str.equals("匹配过的刷新界面")) {
            allList.clear();
            getStr(1, 15);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (isfirst) {
                allList.clear();
                getStr(1, size);

            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
