package com.YiDian.RainBow.main.fragment.home.fragment;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseFragment;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.main.fragment.home.adapter.NewDynamicAdapter;
import com.YiDian.RainBow.main.fragment.home.bean.NewDynamicBean;
import com.YiDian.RainBow.utils.NetUtils;
import com.liaoinstan.springview.container.AliHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.shuyu.gsyvideoplayer.GSYVideoManager;

import java.util.List;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

//最新动态

public class FragmentNewDynamic extends BaseFragment {
    @BindView(R.id.rc_newDynamic)
    RecyclerView rcNewDynamic;
    @BindView(R.id.sv)
    SpringView sv;
    int page = 1;
    int count = 15;
    @BindView(R.id.no_data)
    RelativeLayout noData;
    @BindView(R.id.bt_reload)
    Button btReload;
    @BindView(R.id.rl_nonet)
    RelativeLayout rlNonet;
    private NewDynamicAdapter newDynamicAdapter;
    private LinearLayoutManager linearLayoutManager;
    int id = 1030;

    @Override
    protected void getid(View view) {

    }

    @Override
    protected int getResId() {
        return R.layout.home_home_fragment_newdynamic;
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void getData() {
        getNew(page, count);
        //下拉刷新下拉加载
        sv.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page = 1;
                        sv.onFinishFreshAndLoad();
                        getNew(page, count);
                        GSYVideoManager.releaseAllVideos();
                    }
                }, 1000);
            }

            @Override
            public void onLoadmore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page++;
                        sv.onFinishFreshAndLoad();
                        getNew(page, count);
                    }
                }, 1000);
            }
        });
        rcNewDynamic.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {

                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                GSYVideoManager.releaseAllVideos();
            }
        });
        //判断是否有网 有网加载数据 无网展示缺省页
        if(NetWork(getContext())){
            sv.setVisibility(View.VISIBLE);
            rlNonet.setVisibility(View.GONE);
        }else{
            rlNonet.setVisibility(View.VISIBLE);
            sv.setVisibility(View.GONE);
        }
        btReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //重新执行加载方法
                getData();
            }
        });
    }

    public void getNew(int page, int count) {
        showDialog();
        //获取最新动态
        NetUtils.getInstance().getApis().getNewDynamic(id, page, count)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NewDynamicBean>() {


                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(NewDynamicBean newDynamicBean) {
                        hideDialog();
                        NewDynamicBean.ObjectBean object = newDynamicBean.getObject();
                        List<NewDynamicBean.ObjectBean.ListBean> list = object.getList();
                        if (list.size() > 0 && list != null) {

                            sv.setVisibility(View.VISIBLE);
                            noData.setVisibility(View.GONE);

                            sv.setHeader(new AliHeader(getContext()));
                            //创建最新动态适配器
                            linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                            rcNewDynamic.setLayoutManager(linearLayoutManager);
                            newDynamicAdapter = new NewDynamicAdapter(getActivity(), list);
                            rcNewDynamic.setAdapter(newDynamicAdapter);
                        } else {
                            sv.setVisibility(View.GONE);
                            noData.setVisibility(View.VISIBLE);
                        }

                        if (list.size() > 14) {
                            sv.setHeader(new AliHeader(getContext()));
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("hmy", e.getMessage() + "");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void onPause() {
        super.onPause();
        GSYVideoManager.onPause();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        GSYVideoManager.releaseAllVideos();
    }

    @Override
    public void onResume() {
        super.onResume();
        GSYVideoManager.onResume();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        GSYVideoManager.releaseAllVideos();
    }
}
