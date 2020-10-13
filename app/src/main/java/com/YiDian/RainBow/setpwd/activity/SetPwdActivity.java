package com.YiDian.RainBow.setpwd.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseAvtivity;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.feedback.activity.FeedBackActivity;
import com.YiDian.RainBow.login.activity.CompleteMsgActivity;
import com.YiDian.RainBow.login.bean.LoginBean;
import com.YiDian.RainBow.regist.bean.RegistBean;
import com.YiDian.RainBow.utils.NetUtils;
import com.YiDian.RainBow.utils.StringUtil;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class SetPwdActivity extends BaseAvtivity implements View.OnClickListener, AMapLocationListener {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.tv_mt_pro)
    TextView tvMtPro;
    @BindView(R.id.et_pwd1)
    EditText etPwd1;
    @BindView(R.id.iv_see_pwd1)
    ImageView ivSeePwd1;
    @BindView(R.id.et_pwd2)
    EditText etPwd2;
    @BindView(R.id.iv_see_pwd2)
    ImageView ivSeePwd2;
    @BindView(R.id.bt_login)
    Button btLogin;
    private double latitude;
    private double longitude;
    //声明mlocationClient对象
    AMapLocationClient mlocationClient;
    //声明mLocationOption对象
    AMapLocationClientOption mLocationOption = null;
    private String phone;

    @Override
    protected int getResId() {
        return R.layout.activity_setpwd;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void getData() {
        back.setOnClickListener(this);
        tvMtPro.setOnClickListener(this);
        btLogin.setOnClickListener(this);

        //获取传递过来的手机号
        Intent intent = getIntent();
        phone = intent.getStringExtra("phone");


        //密码明文密文切换
        ivSeePwd1.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        etPwd1.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        ivSeePwd1.setImageResource(R.mipmap.eyeopen);
                        break;
                    case MotionEvent.ACTION_UP:
                        etPwd1.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        ivSeePwd1.setImageResource(R.mipmap.eyeclose);
                        break;
                }
                return true;
            }
        });
        ivSeePwd2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        etPwd2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        ivSeePwd2.setImageResource(R.mipmap.eyeopen);
                        break;
                    case MotionEvent.ACTION_UP:
                        etPwd2.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        ivSeePwd2.setImageResource(R.mipmap.eyeclose);
                        break;
                }
                return true;
            }
        });
        //开启权限
        Request();
        //开启定位
        doLocation();
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_mt_pro:
                // TODO: 2020/10/6 0006 跳转遇到问题界面
                startActivity(new Intent(SetPwdActivity.this, FeedBackActivity.class));
                break;
            // TODO: 2020/10/6 0006 调用登录接口 密码设置后直接登录
            case R.id.bt_login:
                String pwd1 = etPwd1.getText().toString();
                String pwd2 = etPwd2.getText().toString();
                //调用注册接口
                if (StringUtil.checkPassword(pwd1)&&StringUtil.checkPassword(pwd2)){
                    NetUtils.getInstance().getApis().doRegist(phone,pwd1)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<RegistBean>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(RegistBean registBean) {
                                    //判断注册成功调用登录接口
                                    if(registBean.getType().equals("OK")){
                                        NetUtils.getInstance().getApis().doPwdLogin(phone,pwd1,1,longitude,latitude)
                                                .subscribeOn(Schedulers.io())
                                                .observeOn(AndroidSchedulers.mainThread())
                                                .subscribe(new Observer<LoginBean>() {
                                                    @Override
                                                    public void onSubscribe(Disposable d) {

                                                    }

                                                    @Override
                                                    public void onNext(LoginBean loginBean) {
                                                        //登录成功跳转至主页
                                                        startActivity(new Intent(SetPwdActivity.this, CompleteMsgActivity.class));

                                                        //记录登录后的信息

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
                                @Override
                                public void onError(Throwable e) {
                                }

                                @Override
                                public void onComplete() {

                                }
                            });
                }
                break;
        }
    }
    public void doLocation(){
        mlocationClient = new AMapLocationClient(this);
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
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                //获取纬度
                latitude = amapLocation.getLatitude();
                //获取经度
                longitude = amapLocation.getLongitude();
                amapLocation.getAccuracy();//获取精度信息
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(amapLocation.getTime());
                df.format(date);//定位时间
            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError","location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());
            }
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    //安卓10.0定位权限
    public void Request() {
        if (Build.VERSION.SDK_INT >= 23) {
            int request = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
            if (request != PackageManager.PERMISSION_GRANTED)//缺少权限，进行权限申请
            {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);
                return;//
            }
            else {

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
        if(requestCode==100){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {

                Toast.makeText(this, "权限申请失败，用户拒绝权限", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(null != mlocationClient){
            //页面销毁停止定位
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
    }
}
