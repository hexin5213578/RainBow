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
    //??????mlocationClient??????
    AMapLocationClient mlocationClient;
    //??????mLocationOption??????
    AMapLocationClientOption mLocationOption = null;
    //???????????????????????????Tencent???
    private Tencent mTencent;
    //???????????????IUiListener ???????????????LogInListener implements IUiListener???
    private IUiListener listener;
    private String wechatHeadimgurl;
    private IWXAPI mWXApi;
    private String openid1;
    private static final String TAG = "LoginActivity";
    private String avatar;
    private String openId;
    private cn.jpush.im.android.api.model.UserInfo userInfo;
    private CustomDialog dialog;
    private Intent intent;

    @Override
    protected int getResId() {
        return R.layout.activity_login;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void getData() {


        dialog = new CustomDialog(this, "????????????...");

        StatusBarUtil.setTransparentForWindow(LoginActivity.this);

        tvMtPro.setOnClickListener(this);
        tvToRegist.setOnClickListener(this);
        tvRemePwd.setOnClickListener(this);
        btLogin.setOnClickListener(this);
        rlQqLogin.setOnClickListener(this);
        rlWechatLogin.setOnClickListener(this);
        //????????????????????????
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

        //??????????????????
        Request();
        //????????????
        doLocation();

        //??????AppId(???????????????App Id)????????????
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
        //?????????????????????
        mLocationOption = new AMapLocationClientOption();
        //??????????????????
        mlocationClient.setLocationListener(this);
        //???????????????????????????????????????Battery_Saving?????????????????????Device_Sensors??????????????????
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //??????????????????,????????????,?????????2000ms
        mLocationOption.setInterval(2000);
        //??????????????????
        mlocationClient.setLocationOption(mLocationOption);
        // ????????????????????????????????????????????????????????????????????????????????????????????????????????????
        // ??????????????????????????????????????????????????????????????????1000ms?????????????????????????????????stopLocation()???????????????????????????
        // ???????????????????????????????????????????????????onDestroy()??????
        // ?????????????????????????????????????????????????????????????????????stopLocation()???????????????????????????sdk???????????????
        //????????????
        mlocationClient.startLocation();
    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                //?????????????????????????????????????????????
                amapLocation.getLocationType();//??????????????????????????????????????????????????????????????????????????????
                //????????????
                latitude = amapLocation.getLatitude();
                //????????????
                longitude = amapLocation.getLongitude();
                amapLocation.getAccuracy();//??????????????????
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(amapLocation.getTime());
                df.format(date);//????????????
            } else {
                //??????????????????ErrCode???????????????errInfo???????????????????????????????????????
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
//            case R.id.tv_mt_pro:
//                //?????????????????????
//                startActivity(new Intent(LoginActivity.this, FeedBackActivity.class));
//                break;
            case R.id.tv_to_regist:
                //???????????????
                intent = new Intent(LoginActivity.this, RegistActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.tv_reme_pwd:
                startActivity(new Intent(LoginActivity.this, RememberPwdActivity.class));
                //?????????????????????
                break;
            case R.id.bt_login:
                //??????????????????
                String phone = etPhone.getText().toString();
                String pwd = etPwd1.getText().toString();
                if (StringUtil.checkPhoneNumber(phone)) {
                    if (StringUtil.checkPassword(pwd)) {
                        if (longitude == 0.0 && latitude == 0.0) {
                            //??????????????????
                            Request();
                            Toast.makeText(LoginActivity.this, "????????????????????????????????????????????????", Toast.LENGTH_SHORT).show();
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
                                                //??????????????????
                                                JMessageClient.login(id, id, new BasicCallback() {
                                                    @Override
                                                    public void gotResult(int i, String s) {
                                                        Log.d("xxx", id+"?????????????????????" + i + "?????????" + s);
                                                        if (i == 0) {
                                                            dialog.dismiss();
                                                            //????????????????????????
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
                                                            Log.d("xxx", id+"---------------??????????????????----------------");
                                                            //??????????????????????????????????????????
                                                            JMessageClient.updateMyInfo(cn.jpush.im.android.api.model.UserInfo.Field.nickname,userInfo,new BasicCallback() {
                                                                @Override
                                                                public void gotResult(int i, String s) {
                                                                    if (i==0){
                                                                        Log.d("xxx",id+"?????????????????????"+object.getNickName());
                                                                        //???????????????????????? ??????????????????
                                                                    }else{
                                                                        Log.d("xxx","????????????????????????"+s);
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
                                                                        Log.d("xxx","?????????"+file.toString());

                                                                        JMessageClient.updateUserAvatar(file, new BasicCallback() {
                                                                            @Override
                                                                            public void gotResult(int i, String s) {
                                                                                if (i==0){
                                                                                    Log.d("xxx",id+"??????????????????");
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

                                                            //????????????????????????????????????
                                                            String isPerfect = Common.getIsPerfect();
                                                            if (isPerfect!=null && !isPerfect.equals("")){
                                                                if (isPerfect.equals("0")) {
                                                                    Log.d("hmy", isPerfect);
                                                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                                                    finish();
                                                                } else {
                                                                    intent = new Intent(LoginActivity.this, CompleteMsgActivity.class);
                                                                    intent.putExtra("headimg",object.getHeadImg());
                                                                    intent.putExtra("name",object.getNickName());
                                                                    startActivity(intent);
                                                                    finish();
                                                                }
                                                            }else{
                                                                intent = new Intent(LoginActivity.this, CompleteMsgActivity.class);
                                                                intent.putExtra("headimg",object.getHeadImg());
                                                                intent.putExtra("name",object.getNickName());
                                                                startActivity(intent);
                                                                finish();
                                                            }
                                                        }else{
                                                            dialog.dismiss();
                                                            Toast.makeText(LoginActivity.this, "??????????????????", Toast.LENGTH_SHORT).show();
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
                //QQ??????
                if (longitude == 0.0 && latitude == 0.0) {
                    //??????????????????
                    Toast.makeText(LoginActivity.this, "????????????????????????????????????????????????", Toast.LENGTH_SHORT).show();

                    Request();
                } else {
                    //????????????????????????????????????????????????????????????????????????????????????
                    if (!isQQClientAvailable(LoginActivity.this)) {
                        Toast.makeText(LoginActivity.this, "?????????QQ??????",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                    //??????session?????????????????????????????????
                    if (!mTencent.isSessionValid()) {
                        doQlogin();
                    }
                }
                break;
            case R.id.rl_wechat_login:
                //????????????
                if (longitude == 0.0 && latitude == 0.0) {
                    //??????????????????
                    Toast.makeText(LoginActivity.this, "????????????????????????????????????????????????", Toast.LENGTH_SHORT).show();

                    Request();
                } else {
                    doWechatLogin();
                }

                break;
        }
    }

    //????????????
    public void doWechatLogin() {

        if (!App.getWXApi().isWXAppInstalled()) {
            Toast.makeText(LoginActivity.this, "????????????????????????????????????", Toast.LENGTH_SHORT).show();
        } else {
            final SendAuth.Req req = new SendAuth.Req();
            req.scope = "snsapi_userinfo";
            req.state = "wechat_sdk_demo_test";
            App.getWXApi().sendReq(req);
        }
    }

    //??????????????????
    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sp = getSharedPreferences("wechatInfo", MODE_PRIVATE);
        String responseInfo = sp.getString("responseInfo", "");

        if (!responseInfo.isEmpty()) {
            try {
                JSONObject jsonObject = new JSONObject(responseInfo);
                wechatHeadimgurl = jsonObject.getString("headimgurl");
                openid1 = jsonObject.getString("openid");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            SharedPreferences.Editor editor = getSharedPreferences("wechatInfo", MODE_PRIVATE).edit();
            editor.clear();
            editor.commit();


            dialog.show();
            //?????????????????????????????????????????????
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
                                String nickName = object.getNickName();
                                SPUtil.getInstance().saveData(LoginActivity.this, SPUtil.FILE_NAME, SPUtil.USER_ID, id);
                                //??????????????????
                                JMessageClient.register(id, id, new BasicCallback() {
                                    @Override
                                    public void gotResult(int i, String s) {
                                        Log.d("xxx", id+"?????????????????????" + i + "?????????" + s);
                                        if (i == 0 || i==898001) {
                                            JMessageClient.login(id, id, new BasicCallback() {
                                                @Override
                                                public void gotResult(int i, String s) {
                                                    Log.d("xxx", id+"?????????????????????" + i + "?????????" + s);
                                                    if (i == 0) {

                                                        //??????????????????
                                                        NetUtils.getInstance()
                                                                .getApis()
                                                                .doComPleteHeadImg(object.getId(),wechatHeadimgurl)
                                                                .subscribeOn(Schedulers.io())
                                                                .observeOn(AndroidSchedulers.mainThread())
                                                                .subscribe(new Observer<ComPleteMsgBean>() {
                                                                    @Override
                                                                    public void onSubscribe(Disposable d) {

                                                                    }

                                                                    @Override
                                                                    public void onNext(ComPleteMsgBean comPleteMsgBean) {
                                                                        dialog.dismiss();
                                                                        if (comPleteMsgBean.getMsg().equals("?????????????????????")) {

                                                                            SPUtil.getInstance().saveData(LoginActivity.this, SPUtil.FILE_NAME, SPUtil.IS_LOGIN, "0");

                                                                            SPUtil.getInstance().saveData(LoginActivity.this, SPUtil.FILE_NAME, SPUtil.USER_NAME, nickName);
                                                                            SPUtil.getInstance().saveData(LoginActivity.this, SPUtil.FILE_NAME, SPUtil.HEAD_IMG, wechatHeadimgurl);

                                                                            userInfo.setNickname(nickName);

                                                                            //??????????????????????????????????????????
                                                                            JMessageClient.updateMyInfo(cn.jpush.im.android.api.model.UserInfo.Field.nickname,userInfo,new BasicCallback() {
                                                                                @Override
                                                                                public void gotResult(int i, String s) {
                                                                                    if (i==0){
                                                                                        Log.d("xxx",id+"?????????????????????"+nickName);
                                                                                        //???????????????????????? ??????????????????
                                                                                    }else{
                                                                                        Log.d("xxx","????????????????????????"+s);
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
                                                                                        Log.d("xxx","???????????????"+file.toString());
                                                                                        JMessageClient.updateUserAvatar(file, new BasicCallback() {
                                                                                            @Override
                                                                                            public void gotResult(int i, String s) {
                                                                                                if (i==0){
                                                                                                    Log.d("xxx",id+"??????????????????");
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



                                                        //????????????????????????????????????
                                                        String isPerfect = Common.getIsPerfect();
                                                        if (isPerfect!=null && !isPerfect.equals("")){
                                                            if (isPerfect.equals("0")) {
                                                                Log.d("hmy", isPerfect);
                                                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                                                finish();
                                                            } else {
                                                                intent = new Intent(LoginActivity.this, CompleteMsgActivity.class);
                                                                intent.putExtra("headimg",wechatHeadimgurl);
                                                                intent.putExtra("name",nickName);
                                                                startActivity(intent);
                                                                finish();
                                                            }
                                                        }else{
                                                            intent = new Intent(LoginActivity.this, CompleteMsgActivity.class);
                                                            intent.putExtra("headimg",wechatHeadimgurl);
                                                            intent.putExtra("name",nickName);
                                                            startActivity(intent);
                                                            finish();
                                                        }
                                                    }else{
                                                        Toast.makeText(LoginActivity.this, "??????????????????", Toast.LENGTH_SHORT).show();
                                                        dialog.dismiss();
                                                    }
                                                }
                                            });
                                        }else{
                                            Toast.makeText(LoginActivity.this, "??????????????????", Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();
                                        }
                                    }
                                });

                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            dialog.dismiss();
                            Toast.makeText(LoginActivity.this, "????????????", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onComplete() {

                        }
                    });


        }
    }

    //QQ??????
    private void doQlogin() {
        listener = new IUiListener() {
            @Override
            public void onComplete(Object object) {

                Log.e(TAG, "????????????: " + object.toString());

                JSONObject jsonObject = (JSONObject) object;
                try {
                    //??????token???expires???openId?????????
                    String token = jsonObject.getString(Constants.PARAM_ACCESS_TOKEN);
                    String expires = jsonObject.getString(Constants.PARAM_EXPIRES_IN);
                    openId = jsonObject.getString(Constants.PARAM_OPEN_ID);

                    mTencent.setAccessToken(token, expires);
                    mTencent.setOpenId(openId);
                    Log.e(TAG, "token: " + token);
                    Log.e(TAG, "expires: " + expires);
                    Log.e(TAG, "openId: " + openId);

                    //??????????????????
                    getQQInfo();
                } catch (Exception e) {
                }
            }

            @Override
            public void onError(UiError uiError) {
                dialog.dismiss();
                Toast.makeText(LoginActivity.this, "QQ????????????", Toast.LENGTH_SHORT).show();
                //????????????
                Log.e(TAG, "????????????" + uiError.errorDetail);
                Log.e(TAG, "????????????" + uiError.errorMessage);
                Log.e(TAG, "????????????" + uiError.errorCode + "");
            }
            @Override
            public void onCancel() {
                //????????????
                Log.e(TAG, "????????????");
            }
        };
        //context???????????????????????????SCOPO ?????????String???????????????????????????????????????
        //?????????????????????????????????,?????????????????????SCOPE = ???get_user_info,add_t????????????????????????all???
        //??????????????????????????????
        mTencent.login(this, "all", listener);
        //????????????
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //??????QQ??????
        Tencent.onActivityResultData(requestCode, resultCode, data, listener);
        if (requestCode == Constants.REQUEST_API) {
            if (resultCode == Constants.REQUEST_LOGIN) {
                Tencent.handleResultData(data, listener);
            }
        }
    }

    //??????10.0????????????
    public void Request() {
        if (Build.VERSION.SDK_INT >= 23) {
            int request = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
            if (request != PackageManager.PERMISSION_GRANTED)//?????????????????????????????????
            {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);
                return;//
            } else {

            }
        } else {

        }
    }

    //?????? requestCode????????????????????????????????????????????????????????????
    //String[] permission????????????????????????????????????
    //int[] grantResults ????????????????????????????????????????????????????????????
    //??????????????????????????????????????????????????????????????????????????????????????????????????????1
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {

                Toast.makeText(this, "???????????????????????????????????????", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mlocationClient) {
            //????????????????????????
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
    }

    /**
     * ??????QQ????????????
     */
    private void getQQInfo() {
        //??????????????????
        QQToken qqToken = mTencent.getQQToken();
        UserInfo info = new UserInfo(this, qqToken);
        info.getUserInfo(new IUiListener() {
            @Override
            public void onComplete(Object object) {
                try {
                    Log.e(TAG, "???????????????" + object.toString());
                    //??????
                    avatar = ((JSONObject) object).getString("figureurl_2");


                    SPUtil.getInstance().saveData(LoginActivity.this, SPUtil.FILE_NAME, SPUtil.HEAD_IMG, avatar);

                    dialog.show();
                    //???????????????????????????QQ????????????
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
                                        String nickName = object.getNickName();
                                        SPUtil.getInstance().saveData(LoginActivity.this, SPUtil.FILE_NAME, SPUtil.USER_ID, id);
                                        //??????????????????
                                        JMessageClient.register(id, id, new BasicCallback() {
                                            @Override
                                            public void gotResult(int i, String s) {
                                                Log.d("xxx", id+"?????????????????????" + i + "?????????" + s);
                                                if (i == 0 || i==898001) {
                                                    JMessageClient.login(id, id, new BasicCallback() {
                                                        @Override
                                                        public void gotResult(int i, String s) {
                                                            Log.d("xxx", id+"?????????????????????" + i + "?????????" + s);
                                                            if (i == 0) {
                                                                dialog.dismiss();

                                                                NetUtils.getInstance().getApis()
                                                                        .doComPleteHeadImg(object.getId(),avatar)
                                                                        .subscribeOn(Schedulers.io())
                                                                        .observeOn(AndroidSchedulers.mainThread())
                                                                        .subscribe(new Observer<ComPleteMsgBean>() {
                                                                            @Override
                                                                            public void onSubscribe(Disposable d) {

                                                                            }

                                                                            @Override
                                                                            public void onNext(ComPleteMsgBean comPleteMsgBean) {
                                                                                if (comPleteMsgBean.getMsg().equals("?????????????????????")) {

                                                                                    //?????????????????????????????????
                                                                                    SPUtil.getInstance().saveData(LoginActivity.this, SPUtil.FILE_NAME, SPUtil.IS_LOGIN, "0");
                                                                                    SPUtil.getInstance().saveData(LoginActivity.this, SPUtil.FILE_NAME, SPUtil.USER_NAME, nickName);

                                                                                    userInfo.setNickname(nickName);
                                                                                    //??????????????????????????????????????????
                                                                                    JMessageClient.updateMyInfo(cn.jpush.im.android.api.model.UserInfo.Field.nickname,userInfo,new BasicCallback() {
                                                                                        @Override
                                                                                        public void gotResult(int i, String s) {
                                                                                            if (i==0){
                                                                                                Log.d("xxx","?????????????????????"+nickName);
                                                                                                //???????????????????????? ??????????????????
                                                                                            }else{
                                                                                                Log.d("xxx","????????????????????????"+s);
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
                                                                                                            Log.d("xxx","????????????????????????????????????");
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
                                                                //????????????????????????????????????
                                                                String isPerfect = Common.getIsPerfect();
                                                                if (isPerfect!=null && !isPerfect.equals("")){
                                                                    if (isPerfect.equals("0")) {
                                                                        Log.d("xxx", isPerfect);
                                                                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                                                        finish();
                                                                    } else {
                                                                        intent = new Intent(LoginActivity.this, CompleteMsgActivity.class);
                                                                        intent.putExtra("headimg",avatar);
                                                                        intent.putExtra("name",nickName);
                                                                        startActivity(intent);
                                                                        finish();
                                                                    }
                                                                }else{
                                                                    intent = new Intent(LoginActivity.this, CompleteMsgActivity.class);
                                                                    intent.putExtra("headimg",avatar);
                                                                    intent.putExtra("name",nickName);
                                                                    startActivity(intent);
                                                                    finish();
                                                                }
                                                            }else{
                                                                dialog.dismiss();
                                                                Toast.makeText(LoginActivity.this, "??????????????????", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    });
                                                }else{
                                                    dialog.dismiss();
                                                    Toast.makeText(LoginActivity.this, "??????????????????", Toast.LENGTH_SHORT).show();
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
     * ??????qq????????????
     *
     * @param context
     * @return
     */
    public static boolean isQQClientAvailable(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mobileqq")) {
                    return true;
                }
            }
        }
        return false;
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
            options.inSampleSize =2;    // ??????????????????
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
    //??????sd?????????
    public static String getSDPath(){
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(Environment.MEDIA_MOUNTED);//??????sd???????????????
        if(sdCardExist)
        {
            sdDir = Environment.getExternalStorageDirectory();//???????????????
        }
        return sdDir.toString();
    }

}
