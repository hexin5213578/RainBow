package com.YiDian.RainBow.login.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseAvtivity;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.feedback.activity.FeedBackActivity;
import com.YiDian.RainBow.regist.activity.RegistActivity;
import com.YiDian.RainBow.remember.activity.RememberPwdActivity;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends BaseAvtivity implements View.OnClickListener, AMapLocationListener {

    @BindView(R.id.tv_mt_pro)
    TextView tvMtPro;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_pwd1)
    EditText etPwd1;
    @BindView(R.id.iv_see_pwd1)
    ImageView ivSeePwd1;
    @BindView(R.id.tv_to_regist)
    TextView tvToRegist;
    @BindView(R.id.tv_reme_pwd)
    TextView tvRemePwd;
    @BindView(R.id.bt_login)
    Button btLogin;
    @BindView(R.id.rl_qq_login)
    RelativeLayout rlQqLogin;
    @BindView(R.id.rl_wechat_login)
    RelativeLayout rlWechatLogin;
    private double latitude;
    private double longitude;
    //声明mlocationClient对象
    AMapLocationClient mlocationClient;
    //声明mLocationOption对象
    AMapLocationClientOption mLocationOption = null;
    @Override
    protected int getResId() {
        return R.layout.activity_login;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void getData() {
        tvMtPro.setOnClickListener(this);
        tvToRegist.setOnClickListener(this);
        tvRemePwd.setOnClickListener(this);
        btLogin.setOnClickListener(this);
        rlQqLogin.setOnClickListener(this);
        rlWechatLogin.setOnClickListener(this);
        //密码明文密文切换
        ivSeePwd1.setOnTouchListener(new View.OnTouchListener() {
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

        //调取定位权限
        Request();
        //开启定位
        doLocation();
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
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_mt_pro:
                //跳转遇到问题页
                startActivity(new Intent(LoginActivity.this, FeedBackActivity.class));
                break;
            case R.id.tv_to_regist:
                //跳转注册页
                Intent intent = new Intent(LoginActivity.this, RegistActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.tv_reme_pwd:
                startActivity(new Intent(LoginActivity.this, RememberPwdActivity.class));
                //跳转忘记密码页
                break;
            case R.id.bt_login:

                //调用注册登录接口
                Toast.makeText(this, "登录"+latitude+"   "+longitude, Toast.LENGTH_SHORT).show();

                break;
            case R.id.rl_qq_login:
                // TODO: 2020/10/6 0006 调用QQ登录
                

                break;
            case R.id.rl_wechat_login:
                // TODO: 2020/10/6 0006 调用微信登录


                break;
        }
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