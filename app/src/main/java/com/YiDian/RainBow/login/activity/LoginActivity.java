package com.YiDian.RainBow.login.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
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
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.App;
import com.YiDian.RainBow.base.BaseAvtivity;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.base.Common;
import com.YiDian.RainBow.custom.loading.CustomDialog;
import com.YiDian.RainBow.feedback.activity.FeedBackActivity;
import com.YiDian.RainBow.login.bean.ComPleteMsgBean;
import com.YiDian.RainBow.login.bean.LoginBean;
import com.YiDian.RainBow.main.activity.MainActivity;
import com.YiDian.RainBow.regist.activity.RegistActivity;
import com.YiDian.RainBow.remember.activity.RememberPwdActivity;
import com.YiDian.RainBow.utils.NetUtils;
import com.YiDian.RainBow.utils.SPUtil;
import com.YiDian.RainBow.utils.StringUtil;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.leaf.library.StatusBarUtil;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.Constants;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.IllegalFormatCodePointException;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.DownloadAvatarCallback;
import cn.jpush.im.android.api.callback.GetAvatarBitmapCallback;
import cn.jpush.im.api.BasicCallback;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.tencent.connect.common.Constants.PACKAGE_QQ;


public class LoginActivity extends BaseAvtivity implements View.OnClickListener, AMapLocationListener {

    @BindView(R.id.tv_mt_pro)
    RelativeLayout tvMtPro;
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
    @BindView(R.id.rl1)
    RelativeLayout rl1;
    private double latitude;
    private double longitude;
    //声明mlocationClient对象
    AMapLocationClient mlocationClient;
    //声明mLocationOption对象
    AMapLocationClientOption mLocationOption = null;
    //需要腾讯提供的一个Tencent类
    private Tencent mTencent;
    //还需要一个IUiListener 的实现类（LogInListener implements IUiListener）
    private IUiListener listener;
    private String wechatName;
    private String wechatHeadimgurl;
    private IWXAPI mWXApi;
    private String openid1;
    private static final String TAG = "LoginActivity";
    private String avatar;
    private String nickName;
    private String openId;
    private cn.jpush.im.android.api.model.UserInfo userInfo;
    private CustomDialog dialog;

    @Override
    protected int getResId() {
        return R.layout.activity_login;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void getData() {


        dialog = new CustomDialog(this, "正在登录...");

        StatusBarUtil.setTransparentForWindow(LoginActivity.this);

        tvMtPro.setOnClickListener(this);
        tvToRegist.setOnClickListener(this);
        tvRemePwd.setOnClickListener(this);
        btLogin.setOnClickListener(this);
        rlQqLogin.setOnClickListener(this);
        rlWechatLogin.setOnClickListener(this);
        //密码明文密文切换
        rl1.setOnTouchListener(new View.OnTouchListener() {
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

        //腾讯AppId(替换你自己App Id)、上下文
        mTencent = Tencent.createInstance("101906973", this);


        userInfo = new cn.jpush.im.android.api.model.UserInfo() {
           @Override
           public String getNotename() {
               return null;
           }

           @Override
           public String getNoteText() {
               return null;
           }

           @Override
           public long getBirthday() {
               return 0;
           }

           @Override
           public File getAvatarFile() {
               return null;
           }

           @Override
           public void getAvatarFileAsync(DownloadAvatarCallback downloadAvatarCallback) {

           }

           @Override
           public void getAvatarBitmap(GetAvatarBitmapCallback getAvatarBitmapCallback) {

           }

           @Override
           public File getBigAvatarFile() {
               return null;
           }

           @Override
           public void getBigAvatarBitmap(GetAvatarBitmapCallback getAvatarBitmapCallback) {

           }

           @Override
           public int getBlacklist() {
               return 0;
           }

           @Override
           public int getNoDisturb() {
               return 0;
           }

           @Override
           public boolean isFriend() {
               return false;
           }

           @Override
           public String getAppKey() {
               return null;
           }

           @Override
           public void setUserExtras(Map<String, String> map) {

           }

           @Override
           public void setUserExtras(String s, String s1) {

           }

           @Override
           public void setBirthday(long l) {

           }

           @Override
           public void setNoDisturb(int i, BasicCallback basicCallback) {

           }

           @Override
           public void removeFromFriendList(BasicCallback basicCallback) {

           }

           @Override
           public void updateNoteName(String s, BasicCallback basicCallback) {

           }

           @Override
           public void updateNoteText(String s, BasicCallback basicCallback) {

           }

           @Override
           public String getDisplayName() {
               return null;
           }
       };


    }

    public void doLocation() {
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
                Log.e("AmapError", "location Error, ErrCode:"
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
                //调用登录接口
                String phone = etPhone.getText().toString();
                String pwd = etPwd1.getText().toString();
                if (StringUtil.checkPhoneNumber(phone)) {
                    if (StringUtil.checkPassword(pwd)) {
                        if (longitude == 0.0 && latitude == 0.0) {
                            //调取定位权限
                            Request();
                            Toast.makeText(LoginActivity.this, "正在获取当前位置信息，请稍后再试", Toast.LENGTH_SHORT).show();
                        } else {
                            dialog.show();
                            NetUtils.getInstance().getApis().doPwdLogin(phone, pwd, 1, longitude, latitude)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Observer<LoginBean>() {
                                        @Override
                                        public void onSubscribe(Disposable d) {

                                        }

                                        @Override
                                        public void onNext(LoginBean loginBean) {
                                            LoginBean.ObjectBean object = loginBean.getObject();

                                            if (loginBean.getType().equals("OK")) {
                                                String id = String.valueOf(object.getId());
                                                double lng = object.getLng();
                                                double lat = object.getLat();
                                                //极光注册登录
                                                JMessageClient.login(id, id, new BasicCallback() {
                                                    @Override
                                                    public void gotResult(int i, String s) {
                                                        Log.d("xxx", id+"极光登录状态为" + i + "原因为" + s);
                                                        if (i == 0) {
                                                            dialog.dismiss();
                                                            //记录登录后的信息
                                                            SPUtil.getInstance().saveData(LoginActivity.this, SPUtil.FILE_NAME, SPUtil.USER_ID, id);
                                                            SPUtil.getInstance().saveData(LoginActivity.this, SPUtil.FILE_NAME, SPUtil.USER_NAME, object.getNickName());
                                                            SPUtil.getInstance().saveData(LoginActivity.this, SPUtil.FILE_NAME, SPUtil.HEAD_IMG, object.getHeadImg());
                                                            SPUtil.getInstance().saveData(LoginActivity.this, SPUtil.FILE_NAME, SPUtil.BIRTHDAY, object.getBirthday());
                                                            SPUtil.getInstance().saveData(LoginActivity.this, SPUtil.FILE_NAME, SPUtil.ROLE, object.getUserRole());
                                                            SPUtil.getInstance().saveData(LoginActivity.this, SPUtil.FILE_NAME, SPUtil.AGE, String.valueOf(object.getAge()));
                                                            SPUtil.getInstance().saveData(LoginActivity.this, SPUtil.FILE_NAME, SPUtil.ISSINGLE, String.valueOf(object.getIsSingle()));
                                                            SPUtil.getInstance().saveData(LoginActivity.this, SPUtil.FILE_NAME, SPUtil.BACK_IMG, object.getBackImg());
                                                            SPUtil.getInstance().saveData(LoginActivity.this, SPUtil.FILE_NAME, SPUtil.KEY_PHONE, phone);
                                                            SPUtil.getInstance().saveData(LoginActivity.this, SPUtil.FILE_NAME, SPUtil.IS_LOGIN, "0");

                                                            userInfo.setNickname(object.getNickName());
                                                            Log.d("xxx", id+"---------------准备设置头像----------------");
                                                            //登陆成功设置极光用户名及头像
                                                            JMessageClient.updateMyInfo(cn.jpush.im.android.api.model.UserInfo.Field.nickname,userInfo,new BasicCallback() {
                                                                @Override
                                                                public void gotResult(int i, String s) {
                                                                    if (i==0){
                                                                        Log.d("xxx",id+"极光昵称设置成"+object.getNickName());
                                                                        //极光更换成功调用 上传到服务器
                                                                    }else{
                                                                        Log.d("xxx","设置失败，原因为"+s);
                                                                    }
                                                                }
                                                            });
                                                            new Thread(new Runnable() {
                                                                @Override
                                                                public void run() {
                                                                    try {
                                                                        Bitmap bitmap = returnBitMap(object.getHeadImg());
                                                                        String name =  System.currentTimeMillis()+".jpg";
                                                                        Log.d("xxx", "run:-------> "+bitmap);
                                                                        File file = saveFile(bitmap, name);
                                                                        Log.d("xxx","头像为"+file.toString());

                                                                        JMessageClient.updateUserAvatar(file, new BasicCallback() {
                                                                            @Override
                                                                            public void gotResult(int i, String s) {
                                                                                if (i==0){
                                                                                    Log.d("xxx",id+"头像设置成功");
                                                                                }else {
                                                                                    Log.d("xxx",s);
                                                                                }
                                                                            }
                                                                        });
                                                                    } catch (IOException e) {
                                                                        e.printStackTrace();
                                                                    }
                                                                }
                                                            }).start();

                                                            //登录成功跳转至完善信息页
                                                            String isPerfect = Common.getIsPerfect();
                                                            if (isPerfect!=null && !isPerfect.equals("")){
                                                                if (isPerfect.equals("0")) {
                                                                    Log.d("hmy", isPerfect);
                                                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                                                    finish();
                                                                } else {
                                                                    startActivity(new Intent(LoginActivity.this, CompleteMsgActivity.class));
                                                                    finish();
                                                                }
                                                            }else{
                                                                startActivity(new Intent(LoginActivity.this, CompleteMsgActivity.class));
                                                                finish();
                                                            }
                                                        }else{
                                                            dialog.dismiss();
                                                            Toast.makeText(LoginActivity.this, "极光登录失败", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                            } else {
                                                Toast.makeText(LoginActivity.this, "" + loginBean.getMsg(), Toast.LENGTH_SHORT).show();
                                            }


                                        }

                                        @Override
                                        public void onError(Throwable e) {
                                            dialog.dismiss();

                                        }

                                        @Override
                                        public void onComplete() {

                                        }
                                    });
                        }
                    }
                }
                break;
            case R.id.rl_qq_login:
                //QQ登录
                if (longitude == 0.0 && latitude == 0.0) {
                    //调取定位权限
                    Toast.makeText(LoginActivity.this, "正在获取当前位置信息，请稍后再试", Toast.LENGTH_SHORT).show();

                    Request();
                } else {
                    //注意：此段非必要，如果手机未安装应用则会跳转网页进行授权
                    if (!hasApp(LoginActivity.this, PACKAGE_QQ)) {
                        Toast.makeText(LoginActivity.this, "未安装QQ应用",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                    //如果session无效，就开始做登录操作
                    if (!mTencent.isSessionValid()) {
                        doQlogin();
                    }
                }
                break;
            case R.id.rl_wechat_login:
                //微信登录
                if (longitude == 0.0 && latitude == 0.0) {
                    //调取定位权限
                    Toast.makeText(LoginActivity.this, "正在获取当前位置信息，请稍后再试", Toast.LENGTH_SHORT).show();

                    Request();
                } else {
                    doWechatLogin();
                }

                break;
        }
    }

    //微信登录
    public void doWechatLogin() {

        if (!App.getWXApi().isWXAppInstalled()) {
            Toast.makeText(LoginActivity.this, "您的设备未安装微信客户端", Toast.LENGTH_SHORT).show();
        } else {
            final SendAuth.Req req = new SendAuth.Req();
            req.scope = "snsapi_userinfo";
            req.state = "wechat_sdk_demo_test";
            App.getWXApi().sendReq(req);
        }
    }

    //微信回调信息
    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sp = getSharedPreferences("wechatInfo", MODE_PRIVATE);
        String responseInfo = sp.getString("responseInfo", "");

        if (!responseInfo.isEmpty()) {
            try {
                JSONObject jsonObject = new JSONObject(responseInfo);
                wechatName = jsonObject.getString("nickname");
                wechatHeadimgurl = jsonObject.getString("headimgurl");
                openid1 = jsonObject.getString("openid");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            SharedPreferences.Editor editor = getSharedPreferences("wechatInfo", MODE_PRIVATE).edit();
            editor.clear();
            editor.commit();

            Log.e("xxx", "openid" + openid1 + "    name" + wechatName + "    头像" + wechatHeadimgurl);

            dialog.show();
            //获取完用户信息调用微信登录接口
            NetUtils.getInstance().getApis().doWechatLogin(2, openid1, longitude, latitude)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<LoginBean>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }
                        @Override
                        public void onNext(LoginBean loginBean) {
                            if (loginBean.getType().equals("OK")) {
                                LoginBean.ObjectBean object = loginBean.getObject();
                                String id = String.valueOf(object.getId());
                                SPUtil.getInstance().saveData(LoginActivity.this, SPUtil.FILE_NAME, SPUtil.USER_ID, id);
                                //极光注册登录
                                JMessageClient.register(id, id, new BasicCallback() {
                                    @Override
                                    public void gotResult(int i, String s) {
                                        Log.d("xxx", id+"极光注册状态为" + i + "原因为" + s);
                                        if (i == 0 || i==898001) {
                                            JMessageClient.login(id, id, new BasicCallback() {
                                                @Override
                                                public void gotResult(int i, String s) {
                                                    Log.d("xxx", id+"极光登录状态为" + i + "原因为" + s);
                                                    if (i == 0) {

                                                        //更新用户信息
                                                        NetUtils.getInstance()
                                                                .getApis()
                                                                .doComPlteThiredLogin(object.getId(),wechatName,wechatHeadimgurl)
                                                                .subscribeOn(Schedulers.io())
                                                                .observeOn(AndroidSchedulers.mainThread())
                                                                .subscribe(new Observer<ComPleteMsgBean>() {
                                                                    @Override
                                                                    public void onSubscribe(Disposable d) {

                                                                    }

                                                                    @Override
                                                                    public void onNext(ComPleteMsgBean comPleteMsgBean) {
                                                                        dialog.dismiss();
                                                                        if (comPleteMsgBean.getMsg().equals("数据修改成功！")) {

                                                                            SPUtil.getInstance().saveData(LoginActivity.this, SPUtil.FILE_NAME, SPUtil.IS_LOGIN, "0");

                                                                            SPUtil.getInstance().saveData(LoginActivity.this, SPUtil.FILE_NAME, SPUtil.USER_NAME, wechatName);
                                                                            SPUtil.getInstance().saveData(LoginActivity.this, SPUtil.FILE_NAME, SPUtil.HEAD_IMG, wechatHeadimgurl);

                                                                            userInfo.setNickname(wechatName);

                                                                            //登陆成功设置极光用户名及头像
                                                                            JMessageClient.updateMyInfo(cn.jpush.im.android.api.model.UserInfo.Field.nickname,userInfo,new BasicCallback() {
                                                                                @Override
                                                                                public void gotResult(int i, String s) {
                                                                                    if (i==0){
                                                                                        Log.d("xxx",id+"极光昵称设置成"+wechatName);
                                                                                        //极光更换成功调用 上传到服务器
                                                                                    }else{
                                                                                        Log.d("xxx","设置失败，原因为"+s);
                                                                                    }
                                                                                }
                                                                            });
                                                                            new Thread(new Runnable() {
                                                                                @Override
                                                                                public void run() {
                                                                                    try {
                                                                                        Bitmap bitmap = returnBitMap(wechatHeadimgurl);
                                                                                        String name =  System.currentTimeMillis()+".jpg";
                                                                                        File file = saveFile(bitmap, name);
                                                                                        Log.d("xxx","微信头像为"+file.toString());
                                                                                        JMessageClient.updateUserAvatar(file, new BasicCallback() {
                                                                                            @Override
                                                                                            public void gotResult(int i, String s) {
                                                                                                if (i==0){
                                                                                                    Log.d("xxx",id+"头像设置成功");
                                                                                                }else {
                                                                                                    Log.d("xxx",s);
                                                                                                }
                                                                                            }
                                                                                        });
                                                                                    } catch (IOException e) {
                                                                                        e.printStackTrace();
                                                                                    }
                                                                                }
                                                                            }).start();
                                                                        }
                                                                    }

                                                                    @Override
                                                                    public void onError(Throwable e) {

                                                                    }

                                                                    @Override
                                                                    public void onComplete() {

                                                                    }
                                                                });



                                                        //登录成功跳转至完善信息页
                                                        String isPerfect = Common.getIsPerfect();
                                                        if (isPerfect!=null && !isPerfect.equals("")){
                                                            if (isPerfect.equals("0")) {
                                                                Log.d("hmy", isPerfect);
                                                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                                                finish();
                                                            } else {
                                                                startActivity(new Intent(LoginActivity.this, CompleteMsgActivity.class));
                                                                finish();
                                                            }
                                                        }else{
                                                            startActivity(new Intent(LoginActivity.this, CompleteMsgActivity.class));
                                                            finish();
                                                        }
                                                    }else{
                                                        Toast.makeText(LoginActivity.this, "极光登录失败", Toast.LENGTH_SHORT).show();
                                                        dialog.dismiss();
                                                    }
                                                }
                                            });
                                        }else{
                                            Toast.makeText(LoginActivity.this, "极光注册失败", Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();
                                        }
                                    }
                                });

                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            dialog.dismiss();
                            Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onComplete() {

                        }
                    });


        }
    }

    //QQ登录
    private void doQlogin() {
        listener = new IUiListener() {
            @Override
            public void onComplete(Object object) {

                Log.e(TAG, "登录成功: " + object.toString());

                JSONObject jsonObject = (JSONObject) object;
                try {
                    //得到token、expires、openId等参数
                    String token = jsonObject.getString(Constants.PARAM_ACCESS_TOKEN);
                    String expires = jsonObject.getString(Constants.PARAM_EXPIRES_IN);
                    openId = jsonObject.getString(Constants.PARAM_OPEN_ID);

                    mTencent.setAccessToken(token, expires);
                    mTencent.setOpenId(openId);
                    Log.e(TAG, "token: " + token);
                    Log.e(TAG, "expires: " + expires);
                    Log.e(TAG, "openId: " + openId);

                    //获取个人信息
                    getQQInfo();
                } catch (Exception e) {
                }
            }

            @Override
            public void onError(UiError uiError) {
                dialog.dismiss();
                Toast.makeText(LoginActivity.this, "QQ登录失败", Toast.LENGTH_SHORT).show();
                //登录失败
                Log.e(TAG, "登录失败" + uiError.errorDetail);
                Log.e(TAG, "登录失败" + uiError.errorMessage);
                Log.e(TAG, "登录失败" + uiError.errorCode + "");
            }
            @Override
            public void onCancel() {
                //登录取消
                Log.e(TAG, "登录取消");
            }
        };
        //context上下文、第二个参数SCOPO 是一个String类型的字符串，表示一些权限
        //应用需要获得权限，由“,”分隔。例如：SCOPE = “get_user_info,add_t”；所有权限用“all”
        //第三个参数事件监听器
        mTencent.login(this, "all", listener);
        //注销登录
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //腾讯QQ回调
        Tencent.onActivityResultData(requestCode, resultCode, data, listener);
        if (requestCode == Constants.REQUEST_API) {
            if (resultCode == Constants.REQUEST_LOGIN) {
                Tencent.handleResultData(data, listener);
            }
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

                Toast.makeText(this, "权限申请失败，用户拒绝权限", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mlocationClient) {
            //页面销毁停止定位
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
    }

    /**
     * 获取QQ个人信息
     */
    private void getQQInfo() {
        //获取基本信息
        QQToken qqToken = mTencent.getQQToken();
        UserInfo info = new UserInfo(this, qqToken);
        info.getUserInfo(new IUiListener() {
            @Override
            public void onComplete(Object object) {
                try {
                    Log.e(TAG, "个人信息：" + object.toString());
                    //头像
                    avatar = ((JSONObject) object).getString("figureurl_2");
                    nickName = ((JSONObject) object).getString("nickname");


                    SPUtil.getInstance().saveData(LoginActivity.this, SPUtil.FILE_NAME, SPUtil.USER_NAME, nickName);
                    SPUtil.getInstance().saveData(LoginActivity.this, SPUtil.FILE_NAME, SPUtil.HEAD_IMG, avatar);


                    dialog.show();
                    //获取完用户信息调用QQ登录接口
                    NetUtils.getInstance().getApis().doQqLogin(3, openId, longitude, latitude)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<LoginBean>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(LoginBean loginBean) {
                                    if (loginBean.getType().equals("OK")) {
                                        LoginBean.ObjectBean object = loginBean.getObject();
                                        String id = String.valueOf(object.getId());
                                        SPUtil.getInstance().saveData(LoginActivity.this, SPUtil.FILE_NAME, SPUtil.USER_ID, id);
                                        //极光注册登录
                                        JMessageClient.register(id, id, new BasicCallback() {
                                            @Override
                                            public void gotResult(int i, String s) {
                                                Log.d("xxx", id+"极光注册状态为" + i + "原因为" + s);
                                                if (i == 0 || i==898001) {
                                                    JMessageClient.login(id, id, new BasicCallback() {
                                                        @Override
                                                        public void gotResult(int i, String s) {
                                                            Log.d("xxx", id+"极光登录状态为" + i + "原因为" + s);
                                                            if (i == 0) {
                                                                dialog.dismiss();

                                                                NetUtils.getInstance().getApis()
                                                                        .doComPlteThiredLogin(object.getId(),nickName,avatar)
                                                                        .subscribeOn(Schedulers.io())
                                                                        .observeOn(AndroidSchedulers.mainThread())
                                                                        .subscribe(new Observer<ComPleteMsgBean>() {
                                                                            @Override
                                                                            public void onSubscribe(Disposable d) {

                                                                            }

                                                                            @Override
                                                                            public void onNext(ComPleteMsgBean comPleteMsgBean) {
                                                                                if (comPleteMsgBean.getMsg().equals("数据修改成功！")) {

                                                                                    //将登录状态改为已经登录
                                                                                    SPUtil.getInstance().saveData(LoginActivity.this, SPUtil.FILE_NAME, SPUtil.IS_LOGIN, "0");

                                                                                    userInfo.setNickname(nickName);
                                                                                    //登陆成功设置极光用户名及头像
                                                                                    JMessageClient.updateMyInfo(cn.jpush.im.android.api.model.UserInfo.Field.nickname,userInfo,new BasicCallback() {
                                                                                        @Override
                                                                                        public void gotResult(int i, String s) {
                                                                                            if (i==0){
                                                                                                Log.d("xxx","极光昵称设置成"+nickName);
                                                                                                //极光更换成功调用 上传到服务器
                                                                                            }else{
                                                                                                Log.d("xxx","设置失败，原因为"+s);
                                                                                            }
                                                                                        }
                                                                                    });

                                                                                    new Thread(new Runnable() {
                                                                                        @Override
                                                                                        public void run() {
                                                                                            try {
                                                                                                Bitmap bitmap = returnBitMap(avatar);
                                                                                                String name =  System.currentTimeMillis()+".jpg";
                                                                                                File file = saveFile(bitmap, name);

                                                                                                JMessageClient.updateUserAvatar(file, new BasicCallback() {
                                                                                                    @Override
                                                                                                    public void gotResult(int i, String s) {
                                                                                                        if (i==0){
                                                                                                            Log.d("xxx","当前登录用户头像设置成功");
                                                                                                        }else {
                                                                                                            Log.d("xxx",s);
                                                                                                        }
                                                                                                    }
                                                                                                });
                                                                                            } catch (IOException e) {
                                                                                                e.printStackTrace();
                                                                                            }
                                                                                        }
                                                                                    }).start();
                                                                                }
                                                                            }

                                                                            @Override
                                                                            public void onError(Throwable e) {

                                                                            }

                                                                            @Override
                                                                            public void onComplete() {

                                                                            }
                                                                        });
                                                                //登录成功跳转至完善信息页
                                                                String isPerfect = Common.getIsPerfect();
                                                                if (isPerfect!=null && !isPerfect.equals("")){
                                                                    if (isPerfect.equals("0")) {
                                                                        Log.d("xxx", isPerfect);
                                                                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                                                        finish();
                                                                    } else {
                                                                        startActivity(new Intent(LoginActivity.this, CompleteMsgActivity.class));
                                                                        finish();
                                                                    }
                                                                }else{
                                                                    startActivity(new Intent(LoginActivity.this, CompleteMsgActivity.class));
                                                                    finish();
                                                                }
                                                            }else{
                                                                dialog.dismiss();
                                                                Toast.makeText(LoginActivity.this, "极光登录失败", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    });
                                                }else{
                                                    dialog.dismiss();
                                                    Toast.makeText(LoginActivity.this, "极光注册失败", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });

                                    }


                                }

                                @Override
                                public void onError(Throwable e) {
                                    dialog.dismiss();

                                }
                                @Override
                                public void onComplete() {

                                }
                            });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(UiError uiError) {
            }

            @Override
            public void onCancel() {
            }
        });
    }

    /**
     * true 安装了相应包名的app
     */
    private boolean hasApp(Context context, String packName) {
        boolean is = false;
        List<PackageInfo> packages = context.getPackageManager()
                .getInstalledPackages(0);
        for (int i = 0; i < packages.size(); i++) {
            PackageInfo packageInfo = packages.get(i);
            String packageName = packageInfo.packageName;
            if (packageName.equals(packName)) {
                is = true;
            }
        }
        return is;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    public final static Bitmap returnBitMap(String url) {
        URL myFileUrl = null;
        Bitmap bitmap = null;
        try {
            myFileUrl = new URL(url);
            HttpURLConnection conn;
            conn = (HttpURLConnection) myFileUrl.openConnection();
            conn.setDoInput(true);
            int length = conn.getContentLength();
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is, length);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize =2;    // 设置缩放比例
            Rect rect = new Rect(0, 0,0,0);
            bitmap = BitmapFactory.decodeStream(bis,rect,options);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
    public static File saveFile(Bitmap bm, String fileName) throws IOException {
        String path = getSDPath() +"/wuliu/";
        File dirFile = new File(path);
        if(!dirFile.exists()){
            dirFile.mkdir();
        }
        File myCaptureFile = new File(path + fileName);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
        bos.flush();
        bos.close();
        return myCaptureFile;
    }
    //获取sd卡路径
    public static String getSDPath(){
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(Environment.MEDIA_MOUNTED);//判断sd卡是否存在
        if(sdCardExist)
        {
            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
        }
        return sdDir.toString();
    }

}
