package com.YiDian.RainBow.main.fragment.home.fragment;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
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

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.jpush.android.cache.Sp;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.YiDian.RainBow.main.fragment.home.adapter.NewDynamicAdapter.TAG;

//最新动态

public class FragmentNewDynamic extends BaseFragment {
    @BindView(R.id.rc_newDynamic)
    RecyclerView rcNewDynamic;
    @BindView(R.id.sv)
    SpringView sv;
    int page = 1;
    int count = 6;
    @BindView(R.id.no_data)
    RelativeLayout noData;
    @BindView(R.id.bt_reload)
    Button btReload;
    @BindView(R.id.rl_nonet)
    RelativeLayout rlNonet;
    private NewDynamicAdapter newDynamicAdapter;
    private LinearLayoutManager linearLayoutManager;
    int id;
    private List<NewDynamicBean.ObjectBean.ListBean> alllist;
    File f = new File(
            "/data/data/com.YiDian.RainBow/shared_prefs/dynamic.xml");
    private Tencent mTencent;

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
        alllist = new ArrayList<>();
        id = Integer.valueOf(Common.getUserId());

        rcNewDynamic.setHasFixedSize(true);
        rcNewDynamic.setItemAnimator(null);

        //直接取消动画
        RecyclerView.ItemAnimator animator = rcNewDynamic.getItemAnimator();
        if (animator instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
        }

        //腾讯AppId(替换你自己App Id)、上下文
        mTencent = Tencent.createInstance("101906973", getContext());
        KeyBoardUtils.closeKeyboard(getActivity());

            Gson gson = new Gson();
            List<NewDynamicBean.ObjectBean.ListBean> SpList = new ArrayList<>();

            for (int i = 1; i < 10; i++) {
                String json = SPUtil.getInstance().getData(getContext(), SPUtil.JSON_Dynamic, "json" + i);
                NewDynamicBean.ObjectBean.ListBean listBean = gson.fromJson(json, NewDynamicBean.ObjectBean.ListBean.class);
                if(listBean!=null){
                    SpList.add(listBean);
                }
            }
            if(f.exists()){
                if(SpList.size()>0 && SpList!=null){
                    sv.setVisibility(View.VISIBLE);
                    noData.setVisibility(View.GONE);

                    sv.setHeader(new AliHeader(getContext()));
                    //创建最新动态适配器
                    linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                    rcNewDynamic.setLayoutManager(linearLayoutManager);
                    newDynamicAdapter = new NewDynamicAdapter(getActivity(), SpList,mTencent);
                    newDynamicAdapter.setHasStableIds(true);

                    rcNewDynamic.setAdapter(newDynamicAdapter);
                }
            }

        getNew(page, count);
        //下拉刷新下拉加载
        sv.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                alllist.clear();
                page = 1;
                getNew(page, count);
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
                        getNew(page, count);

                        sv.onFinishFreshAndLoad();
                    }
                }, 1000);
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

                        Log.d("xxx","划出去了");

                        //如果滑出去了上面和下面就是否，和今日头条一样
                        //是否全屏
                            GSYVideoManager.releaseAllVideos();
                            newDynamicAdapter.notifyItemChanged(position);
                    }
                }
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
    }
    public boolean onBackPressed() {
        if (GSYVideoManager.backFromWindowFull(getActivity())) {
            return true;
        }
        return false;
    }

    public void getNew(int page, int count) {
        new Thread(new Runnable() {
            @Override
            public void run() {
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
                                NewDynamicBean.ObjectBean object = newDynamicBean.getObject();
                                List<NewDynamicBean.ObjectBean.ListBean> list = object.getList();
                                if (list.size() > 0 && list != null) {
                                    //存五条数据
                                    for (int i = 1; i <= list.size(); i++) {
                                        NewDynamicBean.ObjectBean.ListBean listBean = list.get(i-1);
                                        Gson gson = new Gson();
                                        String json1 = gson.toJson(listBean);
                                        SPUtil.getInstance().saveData(getContext(), SPUtil.JSON_Dynamic, "json"+i, json1);
                                    }

                                    alllist.addAll(list);
                                    sv.setVisibility(View.VISIBLE);
                                    noData.setVisibility(View.GONE);

                                    sv.setHeader(new AliHeader(getContext()));
                                    //创建最新动态适配器
                                    linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                                    rcNewDynamic.setLayoutManager(linearLayoutManager);
                                    newDynamicAdapter = new NewDynamicAdapter(getActivity(), alllist,mTencent);

                                    newDynamicAdapter.setHasStableIds(true);

                                    rcNewDynamic.setAdapter(newDynamicAdapter);
                                } else {
                                    if (alllist.size() > 0 && alllist != null) {
                                        Toast.makeText(getContext(), "没有更多内容了", Toast.LENGTH_SHORT).show();
                                    } else {
                                        sv.setVisibility(View.GONE);
                                        noData.setVisibility(View.VISIBLE);
                                    }
                                }
                                if (list.size() > 4) {
                                    sv.setFooter(new AliFooter(getContext()));
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

    @Override
    public void onPause() {
        super.onPause();
        GSYVideoManager.onPause();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        GSYVideoManager.releaseAllVideos();
        if (EventBus.getDefault().isRegistered(this)) {

            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        GSYVideoManager.onResume();
        //关闭输入框
        KeyBoardUtils.closeKeyboard(getActivity());
        Log.d("xxx", "onResume");
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }
    //获取传过来的信息 刷新界面
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getStr(String str) {
        if (str.equals("刷新界面")) {
            if(alllist.size()>0){
                alllist.clear();
            }
            getNew(1, count);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        GSYVideoManager.releaseAllVideos();
    }
}
