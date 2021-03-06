package com.YiDian.RainBow.setpwd.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
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
import com.YiDian.RainBow.base.Common;
import com.YiDian.RainBow.feedback.activity.FeedBackActivity;
import com.YiDian.RainBow.login.activity.CompleteMsgActivity;
import com.YiDian.RainBow.login.activity.LoginActivity;
import com.YiDian.RainBow.login.bean.ComPleteMsgBean;
import com.YiDian.RainBow.login.bean.LoginBean;
import com.YiDian.RainBow.main.activity.MainActivity;
import com.YiDian.RainBow.main.fragment.mine.activity.EditMsgActivity;
import com.YiDian.RainBow.regist.activity.RegistActivity;
import com.YiDian.RainBow.regist.bean.RegistBean;
import com.YiDian.RainBow.utils.KeyBoardUtils;
import com.YiDian.RainBow.utils.NetUtils;
import com.YiDian.RainBow.utils.SPUtil;
import com.YiDian.RainBow.utils.StringUtil;
import com.YiDian.RainBow.welcome.WelcomeActivity;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.leaf.library.StatusBarUtil;

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
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.DownloadAvatarCallback;
import cn.jpush.im.android.api.callback.GetAvatarBitmapCallback;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class SetPwdActivity extends BaseAvtivity implements View.OnClickListener, AMapLocationListener {
    @BindView(R.id.back)
    RelativeLayout back;
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
    @BindView(R.id.rl1)
    RelativeLayout rl1;
    @BindView(R.id.rl2)
    RelativeLayout rl2;
    private double latitude;
    private double longitude;
    //??????mlocationClient??????
    AMapLocationClient mlocationClient;
    //??????mLocationOption??????
    AMapLocationClientOption mLocationOption = null;
    private String phone;
    private UserInfo userInfo;

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

        StatusBarUtil.setTransparentForWindow(SetPwdActivity.this);

        //??????????????????????????????
        Intent intent = getIntent();
        phone = intent.getStringExtra("phone");

        userInfo = new UserInfo() {
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


        //????????????????????????
        rl1.setOnTouchListener(new View.OnTouchListener() {
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
        rl2.setOnTouchListener(new View.OnTouchListener() {
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
        //????????????
        Request();
        //????????????
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
                // TODO: 2020/10/6 0006 ????????????????????????
                startActivity(new Intent(SetPwdActivity.this, FeedBackActivity.class));
                break;
            // TODO: 2020/10/6 0006 ?????????????????? ???????????????????????????
            case R.id.bt_login:
                String pwd1 = etPwd1.getText().toString();
                String pwd2 = etPwd2.getText().toString();
                //??????????????????
                if (StringUtil.checkPassword(pwd1) && StringUtil.checkPassword(pwd2)) {
                    NetUtils.getInstance().getApis().doRegist(phone, pwd1)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<RegistBean>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(RegistBean registBean) {

                                    if (registBean.getMsg().equals("????????????????????????")){
                                        Toast.makeText(SetPwdActivity.this, "???????????????????????????????????????????????????", Toast.LENGTH_SHORT).show();

                                        KeyBoardUtils.closeKeyboard(SetPwdActivity.this);

                                      new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                               startActivity(new Intent(SetPwdActivity.this, LoginActivity.class));
                                               finish();
                                            }
                                        }, 2000);//2????????????Runnable??????run??????
                                    }
                                    //????????????????????????????????????
                                    if (registBean.getMsg().equals("???????????????")) {
                                        if (longitude == 0.0 && latitude == 0.0) {
                                            Toast.makeText(SetPwdActivity.this, "????????????????????????????????????????????????", Toast.LENGTH_SHORT).show();
                                        } else {
                                            NetUtils.getInstance().getApis().doPwdLogin(phone, pwd1, 1, longitude, latitude)
                                                    .subscribeOn(Schedulers.io())
                                                    .observeOn(AndroidSchedulers.mainThread())
                                                    .subscribe(new Observer<LoginBean>() {
                                                        @Override
                                                        public void onSubscribe(Disposable d) {

                                                        }

                                                        @Override
                                                        public void onNext(LoginBean loginBean) {
                                                            if (loginBean.getMsg().equals("???????????????")){
                                                                LoginBean.ObjectBean bean = loginBean.getObject();
                                                                String id = String.valueOf(bean.getId());
                                                                double lng = bean.getLng();
                                                                double lat = bean.getLat();
                                                                String name = bean.getNickName();
                                                                String  headImg = bean.getHeadImg();

                                                                //??????????????????????????????????????????
                                                                userInfo.setNickname(name);

                                                                //??????????????????
                                                                JMessageClient.register(id, id, new BasicCallback() {
                                                                    @Override
                                                                    public void gotResult(int i, String s) {
                                                                        if (i == 0|| i==898001) {
                                                                            JMessageClient.login(id, id, new BasicCallback() {
                                                                                @Override
                                                                                public void gotResult(int i, String s) {
                                                                                    if (i == 0) {
                                                                                        //????????????????????????
                                                                                        SPUtil.getInstance().saveData(SetPwdActivity.this, SPUtil.FILE_NAME, SPUtil.USER_ID, id);
                                                                                        SPUtil.getInstance().saveData(SetPwdActivity.this, SPUtil.FILE_NAME, SPUtil.KEY_PHONE, phone);
                                                                                        SPUtil.getInstance().saveData(SetPwdActivity.this, SPUtil.FILE_NAME, SPUtil.IS_LOGIN, "0");
                                                                                        SPUtil.getInstance().saveData(SetPwdActivity.this,SPUtil.FILE_NAME,SPUtil.USER_NAME,name);
                                                                                        SPUtil.getInstance().saveData(SetPwdActivity.this,SPUtil.FILE_NAME,SPUtil.HEAD_IMG,headImg);

                                                                                        //??????????????????????????????????????????
                                                                                        JMessageClient.updateMyInfo(UserInfo.Field.nickname,userInfo,new BasicCallback() {
                                                                                            @Override
                                                                                            public void gotResult(int i, String s) {
                                                                                                if (i==0){
                                                                                                    Log.d("xxx",id+"?????????????????????"+name);
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
                                                                                                    Bitmap bitmap = returnBitMap(headImg);
                                                                                                    String name =  System.currentTimeMillis()+".jpg";
                                                                                                    File file = saveFile(bitmap, name);

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
                                                                                        startActivity(new Intent(SetPwdActivity.this, CompleteMsgActivity.class));
                                                                                        finish();
                                                                                    }
                                                                                }
                                                                            });
                                                                        }
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
    public void onPointerCaptureChanged(boolean hasCapture) {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
