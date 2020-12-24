package com.YiDian.RainBow.main.fragment.find.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseFragment;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.base.Common;
import com.YiDian.RainBow.main.fragment.find.activity.UserDetailsActivity;
import com.YiDian.RainBow.main.fragment.find.adapter.CardsDataAdapter;
import com.YiDian.RainBow.main.fragment.find.bean.AllUserInfoBean;
import com.YiDian.RainBow.main.fragment.find.bean.LikeUserBean;
import com.YiDian.RainBow.main.fragment.find.bean.SaveFilterBean;
import com.YiDian.RainBow.utils.NetUtils;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.wenchao.cardstack.CardStack;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

//匹配界面
public class Fragmentmatch extends BaseFragment implements AMapLocationListener {
    @BindView(R.id.container)
    CardStack container;
    int index = 0;
    @BindView(R.id.rl_nodata)
    RelativeLayout rlNodata;
    private CardsDataAdapter cardsDataAdapter;
    private int userid;
    //声明mlocationClient对象
    AMapLocationClient mlocationClient;
    //声明mLocationOption对象
    AMapLocationClientOption mLocationOption = null;
    private double latitude;
    private double longitude;
    private List<AllUserInfoBean.ObjectBean.ListBean> list;
    private List<AllUserInfoBean.ObjectBean.ListBean> Alllist;
    private int id;
    private boolean DataType = true;
    private String age;
    private int distance;
    private String role;
    int single = 0;

    @Override
    protected void getid(View view) {

    }

    @Override
    protected int getResId() {
        return R.layout.home_find_fragment_match;
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void getData() {
        Alllist = new ArrayList<>();


        userid = Integer.valueOf(Common.getUserId());

        container.setStackMargin(20);

        container.setListener(new CardStack.CardEventListener() {
            @Override
            public boolean swipeEnd(int i, float v) {
                //if "return true" the dismiss animation will be triggered  如果“ return true”，则将触发关闭动画
                //if false, the card will move back to stack  如果“ return false”，则卡将移回堆栈
                //distance is finger swipe distance in dp  距离是手指滑动距离，以dp为单位
                //the direction indicate swipe direction  方向指示滑动方向
                //there are four directions  有四个方向
                //  0  |  1
                // ----------
                //  2  |  3
                return (v > 360) ? true : false;
            }

            @Override
            public boolean swipeStart(int i, float v) {

                return false;
            }

            @Override
            public boolean swipeContinue(int i, float v, float v1) {

                return false;
            }

            //关闭动画结束时调用此回调。
            @Override
            public void discarded(int i, int i1) {
                Log.e("xxx", i + "   " + i1);

                //滑动到倒数第二条  重新添加数据
                if(i==Alllist.size()){
                    rlNodata.setVisibility(View.VISIBLE);
                    container.setVisibility(View.GONE);
                }

                if (i % 14 == 0  && DataType) {
                    NetUtils.getInstance().getApis()
                            .doGetAllUserInfo(userid, longitude, latitude, 1, 15)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<AllUserInfoBean>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }
                                @Override
                                public void onNext(AllUserInfoBean allUserInfoBean) {
                                    list = allUserInfoBean.getObject().getList();

                                    if (list.size() > 0 && list != null) {
                                        rlNodata.setVisibility(View.GONE);
                                        container.setVisibility(View.VISIBLE);
                                        Alllist.addAll(list);

                                        for (int i = 0; i < list.size(); i++) {
                                            cardsDataAdapter.add(list.get(i));
                                        }
                                        container.setAdapter(cardsDataAdapter);
                                    }

                                }

                                @Override
                                public void onError(Throwable e) {

                                }

                                @Override
                                public void onComplete() {

                                }
                            });
                }else if(i % 14 == 0  && !DataType){
                    //获取筛选后的数据
                    NetUtils.getInstance().getApis().doGetFilterUser(userid,age,distance,single,role,longitude,latitude,1,15)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<AllUserInfoBean>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(AllUserInfoBean allUserInfoBean) {
                                    list = allUserInfoBean.getObject().getList();

                                    if (list.size() > 0 && list != null) {
                                        rlNodata.setVisibility(View.GONE);
                                        container.setVisibility(View.VISIBLE);

                                        Alllist.addAll(list);

                                        for (int i = 0; i < list.size(); i++) {
                                            cardsDataAdapter.add(list.get(i));
                                        }
                                        container.setAdapter(cardsDataAdapter);
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
                index = i;

                id = 1;

                //i   下标
                if (i1 == 1 || i1 == 3) {
                    // TODO: 2020/10/12 0012  右滑喜欢
                    Log.d("xxx", "喜欢了" + Alllist.get(index - 1).getNickName());
                    NetUtils.getInstance().getApis()
                            .doLikeUser(userid,Alllist.get(index-1).getId(),1)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<LikeUserBean>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(LikeUserBean likeUserBean) {

                                }

                                @Override
                                public void onError(Throwable e) {

                                }

                                @Override
                                public void onComplete() {

                                }
                            });
                } else {
                    // TODO: 2020/10/12 0012  左滑不喜欢
                    Log.d("xxx", "不喜欢" + Alllist.get(index - 1).getNickName());
                    NetUtils.getInstance().getApis()
                            .doLikeUser(userid,Alllist.get(index-1).getId(),0)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<LikeUserBean>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(LikeUserBean likeUserBean) {

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

            //当用户点击顶部卡片时调用此回调。
            @Override
            public void topCardTapped() {
                if (id == 0) {
                    // TODO: 2020/10/12 0012 传入需要使用的用户信息
                    Intent intent = new Intent(getContext(), UserDetailsActivity.class);
                    AllUserInfoBean.ObjectBean.ListBean listBean = Alllist.get(0);
                    intent.putExtra("bean",listBean);
                    startActivity(intent);
                } else {
                    // TODO: 2020/10/12 0012 传入需要使用的用户信息
                    Intent intent = new Intent(getContext(), UserDetailsActivity.class);
                    AllUserInfoBean.ObjectBean.ListBean listBean = Alllist.get(index);
                    intent.putExtra("bean",listBean);
                    startActivity(intent);

                }

            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getMsg(SaveFilterBean saveFilterBean) {
        age = saveFilterBean.getAge();
        //距离
        distance = saveFilterBean.getDistance();
        //是否单身
        String isSingle = saveFilterBean.getIsSingle();
        //角色
        role = saveFilterBean.getRole();

        Log.e("xxx", age + "   " + distance + "   " + isSingle + "   " + role);
        DataType = false;
        // TODO: 2020/11/18 0018 通过该筛选信息 查询列表
        if(isSingle.equals("是")){
            single = 1;
        }else if(isSingle.equals("否")){
            single = 2;
        }
        Alllist.clear();
        NetUtils.getInstance().getApis().doGetFilterUser(userid, age, distance,single, role,longitude,latitude,1,15)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AllUserInfoBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }
                    @Override
                    public void onNext(AllUserInfoBean allUserInfoBean) {
                        String msg = allUserInfoBean.getMsg();

                        if(msg.equals("暂无数据")){
                            rlNodata.setVisibility(View.VISIBLE);
                            container.setVisibility(View.GONE);
                        }else{
                            list = allUserInfoBean.getObject().getList();

                            if (list.size() > 0 && list != null) {
                                rlNodata.setVisibility(View.GONE);
                                container.setVisibility(View.VISIBLE);

                                Alllist.addAll(list);

                                cardsDataAdapter.notifyDataSetChanged();

                                for (int i = 0; i < Alllist.size(); i++) {
                                    cardsDataAdapter.add(Alllist.get(i));
                                }
                                cardsDataAdapter.notifyDataSetChanged();
                                container.setAdapter(cardsDataAdapter);
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
        // TODO: 2020/11/18 0018 设置给左滑右滑数据源
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
                getUserData();

            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
            }
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("hmy", "onViewCreated");
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d("hmy", "onAttach");

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.d("hmy", "onHiddenChanged");
    }

    @Override
    public void onStart() {
        super.onStart();
        index = 0;
        Log.d("hmy", "onStart");
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("hmy", "onResume");
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("hmy", "onStop");
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("hmy", "onDetach");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("hmy", "onPause");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("hmy", "onDestroy");
        //停止定位
        mlocationClient.stopLocation();
        if(EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("hmy", "onDestroyView");
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getStr(String str){
        if(str.equals("重新请求数据")){
            doLocation();
        }
    }
    //获取用户信息
    public void getUserData() {
        DataType = true;
        NetUtils.getInstance().getApis()
                .doGetAllUserInfo(userid, longitude, latitude, 1, 15)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AllUserInfoBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(AllUserInfoBean allUserInfoBean) {
                        list = allUserInfoBean.getObject().getList();

                        if (list.size() > 0 && list != null) {
                            rlNodata.setVisibility(View.GONE);
                            container.setVisibility(View.VISIBLE);

                            Alllist.addAll(list);

                            cardsDataAdapter = new CardsDataAdapter(getContext(), R.layout.card_layout);

                            for (int i = 0; i < Alllist.size(); i++) {
                                cardsDataAdapter.add(Alllist.get(i));
                            }
                            container.setAdapter(cardsDataAdapter);
                        } else {
                            rlNodata.setVisibility(View.VISIBLE);
                            container.setVisibility(View.GONE);
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

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        Log.d("hmy", "onAttach");
        doLocation();
    }
}
