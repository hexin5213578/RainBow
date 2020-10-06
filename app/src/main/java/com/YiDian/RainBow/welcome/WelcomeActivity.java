package com.YiDian.RainBow.welcome;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseAvtivity;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.regist.activity.RegistActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class WelcomeActivity extends BaseAvtivity{


    @Override
    protected int getResId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void getData() {
        // 设置标题栏颜色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            Window window = WelcomeActivity.this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(WelcomeActivity.this.getResources().getColor(R.color.white));
        }

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(WelcomeActivity.this, RegistActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2000);//2秒后执行Runnable中的run方法
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }
}
