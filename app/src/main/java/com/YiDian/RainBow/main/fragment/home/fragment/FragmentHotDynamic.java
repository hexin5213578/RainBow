package com.YiDian.RainBow.main.fragment.home.fragment;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseFragment;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.base.Common;
import com.YiDian.RainBow.main.fragment.home.adapter.NewDynamicAdapter;
import com.YiDian.RainBow.main.fragment.home.bean.NewDynamicBean;
import com.YiDian.RainBow.utils.KeyBoardUtils;
import com.YiDian.RainBow.utils.NetUtils;
import com.YiDian.RainBow.utils.SPUtil;
import com.google.gson.Gson;
import com.liaoinstan.springview.container.AliFooter;
import com.liaoinstan.springview.container.AliHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.tencent.tauth.Tencent;

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

import static com.YiDian.RainBow.main.fragment.home.adapter.NewDynamicAdapter.TAG;

public class FragmentHotDynamic extends BaseFragment {
    @BindView(R.id.no_data)
    RelativeLayout noData;
    @BindView(R.id.bt_reload)
    Button btReload;
    @BindView(R.id.rl_nonet)
    RelativeLayout rlNonet;
    @BindView(R.id.rc_newDynamic)
    RecyclerView rcNewDynamic;
    @BindView(R.id.sv)
    SpringView sv;
    int page = 1;
    int size = 15;
    private int userid;
    private Tencent mTencent;
    private List<NewDynamicBean.ObjectBean.ListBean> alllist;
    private LinearLayoutManager linearLayoutManager;
    private NewDynamicAdapter newDynamicAdapter;
    @Override
    protected void getid(View view) {

    }

    @Override
    protected int getResId() {
        return R.layout.home_home_fragment_hotdynamic;
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void getData() {
        alllist = new ArrayList<>();
        userid = Integer.valueOf(Common.getUserId());

        //腾讯AppId(替换你自己App Id)、上下文
        mTencent = Tencent.createInstance("101906973", getContext());
        KeyBoardUtils.closeKeyboard(getActivity());

        //直接取消动画
        RecyclerView.ItemAnimator animator = rcNewDynamic.getItemAnimator();
        if (animator instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
        }
        rcNewDynamic.setItemAnimator(null);
        rcNewDynamic.setHasFixedSize(true);
        rcNewDynamic.setNestedScrollingEnabled(false);
        rcNewDynamic.setFocusableInTouchMode(false);
        rcNewDynamic.requestFocus();

        //进入界面获取数据
        getDynamic(page,size);
        //下拉刷新下拉加载
        sv.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                alllist.clear();
                page = 1;
                getDynamic(page, size);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        sv.onFinishFreshAndLoad();
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
                        getDynamic(page, size);

                        sv.onFinishFreshAndLoad();
                    }
                }, 1000);
            }
        });

        //判断是否有网 有网加载数据 无网展示缺省页
        if (NetWork(getContext())) {
            sv.setVisibility(View.VISIBLE);
            rlNonet.setVisibility(View.GONE);
        } else {
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
        rcNewDynamic.addOnScrollListener(new RecyclerView.OnScrollListener() {
            int firstVisibleItem, lastVisibleItem;
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                firstVisibleItem   = linearLayoutManager.findFirstVisibleItemPosition();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                //大于0说明有播放
                if (GSYVideoManager.instance().getPlayPosition() >= 0) {
                    //当前播放的位置
                    int position = GSYVideoManager.instance().getPlayPosition();
                    //对应的播放列表TAG
                    if (GSYVideoManager.instance().getPlayTag().equals(TAG)
                            && (position < firstVisibleItem || position > lastVisibleItem)) {

                        //如果滑出去了上面和下面就是否，和今日头条一样
                        //是否全屏
                        if(!GSYVideoManager.isFullState(getActivity())) {
                            GSYVideoManager.releaseAllVideos();
                            newDynamicAdapter.notifyItemChanged(position);
                        }
                    }
                }
            }
        });
    }
    //获取传过来的信息 刷新界面
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getStr(String str) {
        if (str.equals("刷新界面")) {
            if(alllist.size()>0){
                alllist.clear();
            }
            getDynamic(1, size);
        }
    }
    public void getDynamic(int page,int size){
        new Thread(new Runnable() {
            @Override
            public void run() {
                //获取最新动态
                NetUtils.getInstance().getApis().getHotDynamic(userid, page, size)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<NewDynamicBean>() {


                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(NewDynamicBean newDynamicBean) {
                                NewDynamicBean.ObjectBean object = newDynamicBean.getObject();
                                List<NewDynamicBean.ObjectBean.ListBean> list = object.getList();
                                if (list.size() > 0 && list != null) {

                                    if (list.size() > 5) {
                                        sv.setFooter(new AliFooter(getContext()));
                                    }
                                    alllist.addAll(list);
                                    sv.setVisibility(View.VISIBLE);
                                    noData.setVisibility(View.GONE);

                                    sv.setHeader(new AliHeader(getContext()));
                                    //创建最新动态适配器
                                    linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                                    rcNewDynamic.setLayoutManager(linearLayoutManager);
                                    newDynamicAdapter = new NewDynamicAdapter(getActivity(),mTencent);

                                    newDynamicAdapter.setData(alllist);

                                    newDynamicAdapter.setHasStableIds(true);

                                    rcNewDynamic.setAdapter(newDynamicAdapter);
                                } else {
                                    if (alllist.size() > 0 && alllist != null) {
                                        //创建最新动态适配器
                                        Toast.makeText(getContext(), "没有更多内容了", Toast.LENGTH_SHORT).show();
                                    } else {
                                        sv.setVisibility(View.GONE);
                                        noData.setVisibility(View.VISIBLE);
                                    }
                                }


                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.d("hmy", e.getMessage() + "");

                                Toast.makeText(getContext(), "请求失败", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onComplete() {

                            }
                        });
            }
        }).start();
    }
}
