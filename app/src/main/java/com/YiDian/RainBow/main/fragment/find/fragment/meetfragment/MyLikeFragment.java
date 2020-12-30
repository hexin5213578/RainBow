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
import com.YiDian.RainBow.main.fragment.find.adapter.MyLikeAdapter;
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

//  发现 - 遇见 -我喜欢的
public class MyLikeFragment extends BaseFragment {
    @BindView(R.id.rc_mylike)
    RecyclerView rcMylike;
    @BindView(R.id.sv)
    SpringView sv;
    @BindView(R.id.rl_nodata)
    RelativeLayout rlNodata;
    private int userid;
    int page = 1;
    int size = 15;
    private List<UserMySeeBean.ObjectBean> allList;
    private LinearLayoutManager linearLayoutManager;
    private MyLikeAdapter myLikeAdapter;

    @Override
    protected void getid(View view) {

    }

    @Override
    protected int getResId() {
        return R.layout.home_find_meet_mylikefragment;
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void getData() {
        allList = new ArrayList<>();
        userid = Integer.valueOf(Common.getUserId());

        getStr(page,size);

        sv.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page = 1;
                        allList.clear();
                        getStr(page,size);
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
                        getStr(page,size);
                        sv.onFinishFreshAndLoad();
                    }
                },1000);
            }
        });
    }

    public void getStr(int page, int size) {
        showDialog();

        new Thread(new Runnable() {
            @Override
            public void run() {
                NetUtils.getInstance().getApis()
                        .doGetMyLike(userid, page, size)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<UserMySeeBean>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(UserMySeeBean userMySeeBean) {
                                hideDialog();
                                List<UserMySeeBean.ObjectBean> list = userMySeeBean.getObject();
                                if (list.size() > 0 && list != null) {
                                    sv.setVisibility(View.VISIBLE);
                                    rlNodata.setVisibility(View.GONE);

                                    sv.setHeader(new AliHeader(getContext()));
                                    allList.addAll(list);
                                    linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                                    rcMylike.setLayoutManager(linearLayoutManager);

                                    myLikeAdapter = new MyLikeAdapter(getContext(), allList);

                                    rcMylike.setAdapter(myLikeAdapter);
                                }else{
                                    if(allList.size()>0 && allList!=null){
                                        Toast.makeText(getContext(), "没有更多内容了", Toast.LENGTH_SHORT).show();
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
                                hideDialog();
                                Toast.makeText(getContext(), "请求失败", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onComplete() {

                            }
                        });
            }
        }).start();
    }
    //接收关注成功 取消关注成功后的处理
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getStr(String str){
        if(str.equals("我喜欢的刷新界面")){
            allList.clear();
            getStr(1,15);
        }
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
}
