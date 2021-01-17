package com.YiDian.RainBow.welcome;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseAvtivity;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.base.Common;
import com.YiDian.RainBow.dynamic.bean.SaveMsgSuccessBean;
import com.YiDian.RainBow.login.activity.CompleteMsgActivity;
import com.YiDian.RainBow.login.activity.LoginActivity;
import com.YiDian.RainBow.main.activity.MainActivity;
import com.YiDian.RainBow.regist.activity.RegistActivity;
import com.YiDian.RainBow.utils.NetUtils;
import com.YiDian.RainBow.utils.SPUtil;
import com.leaf.library.StatusBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class WelcomeActivity extends BaseAvtivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private Handler handler;

    @Override
    protected int getResId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void getData() {

        String userId = Common.getUserId();

        StatusBarUtil.setGradientColor(WelcomeActivity.this,toolbar);
        StatusBarUtil.setDarkMode(WelcomeActivity.this);


        //获取七牛云uploadToken
        NetUtils.getInstance().getApis().getUpdateToken()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SaveMsgSuccessBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(SaveMsgSuccessBean saveMsgSuccessBean) {
                        String upToken = saveMsgSuccessBean.getUpToken();
                        SPUtil.getInstance().saveData(WelcomeActivity.this, SPUtil.FILE_NAME, SPUtil.UPTOKEN, upToken);
                        Log.d("xxx", "uptoken存入成功");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        requestPermission();
        handler = new Handler();
        if (userId != null) {
            JMessageClient.login(userId, userId, new BasicCallback() {
                @Override
                public void gotResult(int i, String s) {
                    if (i == 0) {
                        Log.d("xxx", "极光登录状态为" + i + "原因为" + s);
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                String isLogin = Common.getIsLogin();
                                String isPerfect = Common.getIsPerfect();

                                if (isLogin != null) {
                                    if (isLogin.equals("0")) {
                                        if (isPerfect!=null && isPerfect.equals("0")){
                                            Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }else{
                                            Intent intent = new Intent(WelcomeActivity.this, CompleteMsgActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    } else {
                                        Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                } else {
                                    Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        }, 2000);//2秒后执行Runnable中的run方法
                    }
                }
            });
        } else {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    String isLogin = Common.getIsLogin();
                    if (isLogin != null) {
                        if (isLogin.equals("0")) {
                            Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    } else {
                        Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }, 2000);//2秒后执行Runnable中的run方法
        }
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // 先判断有没有权限
            if (Environment.isExternalStorageManager()) {

            } else {
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                intent.setData(Uri.parse("package:" + WelcomeActivity.this.getPackageName()));
                startActivityForResult(intent, 101);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (Environment.isExternalStorageManager()) {
                Toast.makeText(this, "存储权限获取成功", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(this, "存储权限获取失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
