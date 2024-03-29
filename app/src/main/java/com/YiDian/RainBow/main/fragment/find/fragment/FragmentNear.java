package com.YiDian.RainBow.main.fragment.find.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.Common;
import com.YiDian.RainBow.custom.loading.CustomDialog;
import com.YiDian.RainBow.main.fragment.find.adapter.NearPersonAdapter;
import com.YiDian.RainBow.main.fragment.find.bean.NearPersonBean;
import com.YiDian.RainBow.utils.NetUtils;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.liaoinstan.springview.container.AliFooter;
import com.liaoinstan.springview.container.AliHeader;
import com.liaoinstan.springview.widget.SpringView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

// 发现 - 附近
public class FragmentNear extends Fragment implements AMapLocationListener {
    @BindView(R.id.rc_near_person)
    RecyclerView rcNearPerson;
    @BindView(R.id.sv)
    SpringView sv;
    @BindView(R.id.rl_nodata)
    RelativeLayout rlNodata;
    @BindView(R.id.bt_open)
    Button btOpen;
    @BindView(R.id.rl_no_location)
    RelativeLayout rlNoLocation;
    private int userid;
    //声明mlocationClient对象
    AMapLocationClient mlocationClient;
    //声明mLocationOption对象
    AMapLocationClientOption mLocationOption = null;
    private double latitude;
    private double longitude;
    int page = 1;
    int size = 15;
    private List<NearPersonBean.ObjectBean.ListBean> alllist;
    //加载的试图
    private View mContentView;
    //三个核心变量
    private boolean isUserHint;//用户是否可见
    private boolean isViewInit;//view视图是否加载过
    private boolean isDataLoad;//耗时操作是否加载过
    private Unbinder bind;
    private CustomDialog dialog1;
    private CustomDialog dialog2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mContentView == null) {
            mContentView = createView(inflater, container);
        }

        bind = ButterKnife.bind(this, mContentView);

        return mContentView;
    }


    public View createView(LayoutInflater inflater, ViewGroup container) {
        View view = null;
        if (getResId() != 0) {
            view = inflater.inflate(getResId(), container, false);
        } else {
            throw new IllegalStateException("this layout id is null");
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i("baseF", "onViewCreated");

        if (!isViewInit) {
            getid(mContentView);
        }
        isViewInit = true;
        loadData();
    }

    protected void getid(View view) {

    }

    protected int getResId() {
        return R.layout.home_find_fragment_near;
    }

    protected void getData() {
        userid = Integer.valueOf(Common.getUserId());
        alllist = new ArrayList<>();

        dialog1 = new CustomDialog(getContext(), "正在获取位置信息...");
        dialog2 = new CustomDialog(getContext(), "正在加载...");

        //进入定位 定位成功获取第一次数据
        Request();
        sv.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                int page = 1;
                alllist.clear();
                getNearPerson(page, size);
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
                getNearPerson(page, size);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        sv.onFinishFreshAndLoad();
                    }
                }, 1000);
            }
        });

        btOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Request();
            }
        });
    }

    //这个方法优先级很高
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {//用户可见就为true 不可见就是false
        super.setUserVisibleHint(isVisibleToUser);
        this.isUserHint = isVisibleToUser;
        loadData();
    }

    void loadData() {
        //进行优化懒加载的方法
        if (isUserHint && isViewInit) {
            getData();
            isDataLoad = true;
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
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        bind.unbind();
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
        mLocationOption.setOnceLocation(true);
        //设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为1000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
        //启动定位
        mlocationClient.startLocation();
        dialog1.show();
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

                Log.d("xxx", "定位成功");
                alllist.clear();
                dialog1.dismiss();
                page = 1;
                getNearPerson(page, size);
            }
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
            }
    }

    //安卓10.0定位权限
    public void Request() {
        if (Build.VERSION.SDK_INT >= 23) {
            int request = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION);
            if (request != PackageManager.PERMISSION_GRANTED)//缺少权限，进行权限申请
            {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);
                return;//
            } else {
                doLocation();
            }
        } else {

        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getGetLocation1(String str){
        if (str.equals("获取位置权限成功")){
            sv.setVisibility(View.VISIBLE);
            rlNoLocation.setVisibility(View.GONE);
            doLocation();
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getGetLocation2(String str){
        if (str.equals("获取位置权限失败")){
            sv.setVisibility(View.GONE);
            rlNoLocation.setVisibility(View.VISIBLE);
        }
    }

    //获取数据
    public void getNearPerson(int page, int size) {
        dialog2.show();
        NetUtils.getInstance().getApis()
                .doGetNearPerson(userid, longitude, latitude, page, size)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NearPersonBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(NearPersonBean nearPersonBean) {
                        dialog2.dismiss();

                        List<NearPersonBean.ObjectBean.ListBean> list = nearPersonBean.getObject().getList();
                        if (list.size() > 0 && list != null) {

                            sv.setVisibility(View.VISIBLE);
                            rlNodata.setVisibility(View.GONE);

                            sv.setHeader(new AliHeader(getContext()));

                            alllist.addAll(list);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                            rcNearPerson.setLayoutManager(linearLayoutManager);
                            NearPersonAdapter nearPersonAdapter = new NearPersonAdapter(getContext(), alllist);
                            rcNearPerson.setAdapter(nearPersonAdapter);
                        } else {
                            if (alllist.size() > 0 && alllist != null) {
                                Toast.makeText(getContext(), "没有更多内容了", Toast.LENGTH_SHORT).show();
                            } else {
                                sv.setVisibility(View.GONE);
                                rlNodata.setVisibility(View.VISIBLE);
                            }
                        }
                        if (list.size() > 8) {
                            sv.setFooter(new AliFooter(getContext()));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        dialog2.dismiss();
                        Toast.makeText(getContext(), "数据请求失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
