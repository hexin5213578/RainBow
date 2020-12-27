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
import com.YiDian.RainBow.base.Common;
import com.YiDian.RainBow.main.fragment.home.adapter.NewDynamicAdapter;
import com.YiDian.RainBow.main.fragment.home.bean.NewDynamicBean;
import com.YiDian.RainBow.utils.KeyBoardUtils;
import com.YiDian.RainBow.utils.NetUtils;
import com.YiDian.RainBow.utils.SPUtil;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.google.gson.Gson;
import com.liaoinstan.springview.container.AliFooter;
import com.liaoinstan.springview.container.AliHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.tencent.tauth.Tencent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.YiDian.RainBow.main.fragment.home.adapter.NewDynamicAdapter.TAG;

public class FragmentNearDynamic extends BaseFragment implements AMapLocationListener {
    //声明mlocationClient对象
    AMapLocationClient mlocationClient;
    //声明mLocationOption对象
    AMapLocationClientOption mLocationOption = null;
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
    private double latitude;
    private double longitude;
    private int userid;
    private LinearLayoutManager linearLayoutManager;
    private NewDynamicAdapter newDynamicAdapter;
    private List<NewDynamicBean.ObjectBean.ListBean> alllist;
    int page =1;
    int size = 5;
    private Tencent mTencent;

    @Override
    protected void getid(View view) {

    }

    @Override
    protected int getResId() {
        return R.layout.home_home_fragment_neardynamic;
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void getData() {
        //开启定位
        doLocation();
        userid = Integer.valueOf(Common.getUserId());

        //腾讯AppId(替换你自己App Id)、上下文
        mTencent = Tencent.createInstance("101906973", getContext());


        alllist = new ArrayList<>();


        sv.setFooter(new AliFooter(getContext()));

        //获取数据
        getDynamic(page,size);
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
                        getDynamic(page, size);
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
    //获取数据
    public void getDynamic(int page,int size){
        new Thread(new Runnable() {
            @Override
            public void run() {
                NetUtils.getInstance().getApis()
                        .getNearDynamic(userid,latitude,longitude,page,size)
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
                                    //创建最新动态适配器
                                    linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                                    rcNewDynamic.setLayoutManager(linearLayoutManager);
                                    newDynamicAdapter = new NewDynamicAdapter(getActivity(), alllist,mTencent);
                                    rcNewDynamic.setAdapter(newDynamicAdapter);
                                } else {
                                    if (alllist.size() > 0 && alllist != null) {
                                        //创建最新动态适配器
                                        linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                                        rcNewDynamic.setLayoutManager(linearLayoutManager);
                                        newDynamicAdapter = new NewDynamicAdapter(getActivity(), alllist,mTencent);
                                        rcNewDynamic.setAdapter(newDynamicAdapter);

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
    public void doLocation() {
        mlocationClient = new AMapLocationClient(getContext());
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位监听
        mlocationClient.setLocationListener(this);
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        //设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为1000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
        //启动定位
        mlocationClient.startLocation();
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                aMapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                //获取纬度
                latitude = aMapLocation.getLatitude();
                //获取经度
                longitude = aMapLocation.getLongitude();
                aMapLocation.getAccuracy();//获取精度信息
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(aMapLocation.getTime());
                df.format(date);//定位时间

            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
            }
        }
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
            getDynamic(1, size);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        GSYVideoManager.releaseAllVideos();
    }
}
