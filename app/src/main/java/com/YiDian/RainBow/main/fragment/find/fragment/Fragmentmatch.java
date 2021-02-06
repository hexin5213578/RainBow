package com.YiDian.RainBow.main.fragment.find.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseFragment;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.base.Common;
import com.YiDian.RainBow.custom.loading.CustomDialog;
import com.YiDian.RainBow.main.fragment.find.adapter.CardsAdapterFst;
import com.YiDian.RainBow.main.fragment.find.adapter.CardsDataAdapter;
import com.YiDian.RainBow.main.fragment.find.bean.AllUserInfoBean;
import com.YiDian.RainBow.main.fragment.find.bean.LikeUserBean;
import com.YiDian.RainBow.main.fragment.find.bean.SaveFilterBean;
import com.YiDian.RainBow.utils.NetUtils;
import com.YiDian.RainBow.utils.SPUtil;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.fashare.stack_layout.StackLayout;
import com.fashare.stack_layout.transformer.AngleTransformer;
import com.fashare.stack_layout.transformer.StackPageTransformer;
import com.wenchao.cardstack.CardStack;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

//匹配界面
public class Fragmentmatch extends BaseFragment implements AMapLocationListener {

    int index = 0;
    @BindView(R.id.rl_nodata)
    RelativeLayout rlNodata;
    @BindView(R.id.stack_layout)
    StackLayout stackLayout;
    private CardsDataAdapter cardsDataAdapter;
    private int userid;
    //声明mlocationClient对象
    AMapLocationClient mlocationClient;
    //声明mLocationOption对象
    AMapLocationClientOption mLocationOption = null;
    private double latitude;
    private double longitude;
    private List<AllUserInfoBean.ObjectBean.ListBean> list;
    private LinkedList<AllUserInfoBean.ObjectBean.ListBean> linkedList;
    private int id;
    private boolean DataType = true;
    private String age;
    private int distance;
    private String role;
    int single = 0;
    private CustomDialog dialog;
    private CustomDialog dialog1;

    public String TAG = "fst";
    AllUserInfoBean.ObjectBean.ListBean topItem;

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
        //从网络获取数据表后将其中的列表存放在这个链表中
        linkedList = new LinkedList<>();
        //dialog1 = new CustomDialog(getContext(), "正在加载...");
        doLocation();
        userid = Integer.valueOf(Common.getUserId());
        initView();


    }

    //卡片数据
    private void initView() {
        stackLayout.addPageTransformer(
//                minScale  最后一张卡片缩放比例    maxScale 第一张卡片缩放比例   stackCount   卡片显示数量
                new StackPageTransformer(0.8f,1f,3),     // 堆叠
                new MyAlphaTransformer(),       // 渐变  我没做
                new AngleTransformer(-60,0)     // 角度  移开角度  ， 未移动角度
        );
        //卡片滑动事件监听
        stackLayout.setOnSwipeListener(new StackLayout.OnSwipeListener() {
            @Override
            public void onSwiped(View swipedView, int swipedItemPos, boolean isSwipeLeft, int itemLeft) {
                //Log.d(TAG, isSwipeLeft+ "往左" + "往右" + "移除" + swipedItemPos + "." + "剩余" + itemLeft + "项");
                //获取数据并渲染
                getUserData();

                Log.d(TAG, "onSwiped: "+swipedView.toString()+swipedItemPos);
                if(isSwipeLeft){
                    //左滑处理
                    NetUtils.getInstance().getApis()
                            .doLikeUser(userid, topItem.getId(), 0)
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
                }else {
                    //右滑处理
                    NetUtils.getInstance().getApis()
                            .doLikeUser(userid, topItem.getId(), 1)
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
        });
    }

    //从链表获取数据，填充进适配器，并为stackLayout设置适配器
    public void bindData(){
        boolean f=true;   //true表示第一个数据，链表中用pop获取数据后从链表删除
        ArrayList<AllUserInfoBean.ObjectBean.ListBean> list2 = new ArrayList();
        for(int i=0;i<3;i++){
            if(f){
                //将第一个卡片的绑定数据先暂存一份，用户在确定喜欢不喜欢时获取用户ID
                topItem = linkedList.peek();
                list2.add(linkedList.poll());
                f=false;
            }else {
                //后面两个数据获取后不从链表中删除
                list2.add(linkedList.get(i-1));
            }
        }
        CardsAdapterFst adapter= new CardsAdapterFst(getContext(),list2);
        stackLayout.setAdapter(adapter);
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
        if (isSingle.equals("是")) {
            single = 1;
        } else if (isSingle.equals("否")) {
            single = 2;
        }

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
//        dialog = new CustomDialog(getContext(), "正在获取位置信息...");
//        dialog.show();
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
//                dialog.dismiss();
                getUserData();
            } else {
//                dialog.dismiss();
                Toast.makeText(getContext(), "获取位置信息失败", Toast.LENGTH_SHORT).show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (aMapLocation.getErrorCode() == 12) {
                            Request();
                        }
                    }
                }, 1000);

                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
            }
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

            }
        } else {

        }
    }

    //参数 requestCode是我们在申请权限的时候使用的唯一的申请码
    //String[] permission则是权限列表，一般用不到
    //int[] grantResults 是用户的操作响应，包含这权限是够请求成功
    //由于在权限申请的时候，我们就申请了一个权限，所以此处的数组的长度都是1
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                Toast.makeText(getContext(), "权限申请失败，用户拒绝权限", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("hmy", "fragmentmatch的onResume");
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("hmy", "onDestroy");
        //停止定位
        mlocationClient.stopLocation();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("hmy", "onDestroyView");
    }
    //获取用户信息   将数据加载到链表
    public void getUserData() {
        DataType = true;
//        dialog1.show();
        // 少于5条, 往链表里面放入更多数据，如果链表里面有数据，则从链表里面获取
        if(linkedList.size() < 6){
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
//                            dialog1.dismiss();
                            list = allUserInfoBean.getObject().getList();

                            if (list.size() > 1 && list != null) {
                                //从网络拿到数据准备加入链表
                                for(AllUserInfoBean.ObjectBean.ListBean s:list){
                                    linkedList.offer(s);
                                }

                                Log.d(TAG, "onNext:"+linkedList.peek().getNickName());
                                //拿到数据后再绑定
                                bindData();
                                //有数据，把page++准备下一页。
                            } else {
                                //拿不到数据
//                              说明服务器已经没有了数据但是链表中还有一小部分，当链表中没有数据时，展示缺省页
                                //从网络轻求数据后链表还是为0显示完展示缺省页
                                if(linkedList.size()==0){
                                    //展示缺省页
                                    stackLayout.setVisibility(View.GONE);
                                    rlNodata.setVisibility(View.VISIBLE);
                                }
                            }


                        }
                        @Override
                        public void onError(Throwable e) {
//                            dialog1.dismiss();
                            Toast.makeText(getContext(), "请求失败", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onComplete() {

                        }
                    });

        }else {
            //链表中数据大于6条直接从链表获取数据进行绑定
            bindData();
        }

    }
}
