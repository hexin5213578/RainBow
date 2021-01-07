package com.YiDian.RainBow.main.fragment.home.fragment;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseFragment;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.base.Common;
import com.YiDian.RainBow.friend.bean.MyfollowBean;
import com.YiDian.RainBow.main.fragment.home.adapter.NewDynamicAdapter;
import com.YiDian.RainBow.main.fragment.home.bean.NewDynamicBean;
import com.YiDian.RainBow.utils.KeyBoardUtils;
import com.YiDian.RainBow.utils.NetUtils;
import com.google.gson.Gson;
import com.liaoinstan.springview.container.AliFooter;
import com.liaoinstan.springview.container.AliHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.tencent.tauth.Tencent;

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

import static com.YiDian.RainBow.main.fragment.home.adapter.NewDynamicAdapter.TAG;

//关注动态
public class FragmentAttDynamic extends BaseFragment {
    @BindView(R.id.no_att_img)
    ImageView noAttImg;
    @BindView(R.id.weiguanzhu)
    TextView weiguanzhu;
    @BindView(R.id.rl_no_att)
    RelativeLayout rlNoAtt;
    @BindView(R.id.bt_go_att)
    Button btGoAtt;
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
    private int userid;
    private NewDynamicAdapter newDynamicAdapter;
    private LinearLayoutManager linearLayoutManager;
    private List<NewDynamicBean.ObjectBean.ListBean> alllist;
    private int page = 1;
    private int size = 5;
    private Tencent mTencent;
    private boolean FirstInit;
    @Override
    protected void getid(View view) {

    }

    @Override
    protected int getResId() {
        return R.layout.home_home_fragment_attdynamic;
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void getData() {

        alllist = new ArrayList<>();

        FirstInit = true;

        //腾讯AppId(替换你自己App Id)、上下文
        mTencent = Tencent.createInstance("101906973", getContext());

        //直接取消动画
        RecyclerView.ItemAnimator animator = rcNewDynamic.getItemAnimator();
        if (animator instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
        }

        rcNewDynamic.setHasFixedSize(true);
        rcNewDynamic.setItemAnimator(null);

        getAttUser();

        //下拉刷新下拉加载
        sv.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        alllist.clear();
                        page = 1;
                        sv.onFinishFreshAndLoad();
                        getAttUser();
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
                        getDynamic(page, size);
                        GSYVideoManager.releaseAllVideos();
                    }
                }, 1000);
            }
        });

        userid = Integer.parseInt(Common.getUserId());
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
        btGoAtt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post("跳转到匹配页");
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

    //获取数据
    public void getDynamic(int page,int size){
        new Thread(new Runnable() {
            @Override
            public void run() {
                NetUtils.getInstance().getApis()
                        .getAttDynamic(userid,page,size)
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

                                    alllist.addAll(list);
                                    sv.setVisibility(View.VISIBLE);
                                    noData.setVisibility(View.GONE);

                                    sv.setHeader(new AliHeader(getContext()));
                                    sv.setFooter(new AliFooter(getContext()));
                                    //创建最新动态适配器
                                    linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                                    rcNewDynamic.setLayoutManager(linearLayoutManager);
                                    newDynamicAdapter = new NewDynamicAdapter(getActivity(), alllist,mTencent);

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

                            }

                            @Override
                            public void onComplete() {

                            }
                        });
            }
        }).start();

    }
    public void getAttUser(){
        //判断有无关注用户
        new Thread(new Runnable() {
            @Override
            public void run() {

                NetUtils.getInstance().getApis()
                        .doGetMyFollow(userid,1,1)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<MyfollowBean>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(MyfollowBean myFollowBean) {
                                List<MyfollowBean.ObjectBean.ListBean> list =
                                        myFollowBean.getObject().getList();
                                //没有关注的用户去关注
                                if(list.size()==0){
                                    rlNoAtt.setVisibility(View.VISIBLE);
                                    sv.setVisibility(View.GONE);
                                    rlNonet.setVisibility(View.GONE);
                                }else{
                                    //有关注的用户获取关注人的动态
                                    rlNoAtt.setVisibility(View.GONE);
                                    getDynamic(page,size);
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
            getDynamic(1, size);
        }
    }
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        GSYVideoManager.releaseAllVideos();
    }
}
