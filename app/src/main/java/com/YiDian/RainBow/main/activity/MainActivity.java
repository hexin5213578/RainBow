package com.YiDian.RainBow.main.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.IntDef;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseAvtivity;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.main.fragment.FragmentFind;
import com.YiDian.RainBow.main.fragment.FragmentHome;
import com.YiDian.RainBow.main.fragment.FragmentIM;
import com.YiDian.RainBow.main.fragment.FragmentMine;
import com.YiDian.RainBow.main.fragment.FragmentMsg;
import com.YiDian.RainBow.main.fragment.home.activity.NewDynamicImage;
import com.YiDian.RainBow.main.fragment.home.bean.NewDynamicBean;
import com.YiDian.RainBow.utils.NetUtils;
import com.leaf.library.StatusBarUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.shuyu.gsyvideoplayer.GSYVideoManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseAvtivity implements RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.flContent)
    FrameLayout flContent;
    @BindView(R.id.rbHome)
    RadioButton rbHome;
    @BindView(R.id.rbIM)
    RadioButton rbIM;
    @BindView(R.id.rbFind)
    RadioButton rbFind;
    @BindView(R.id.rbMsg)
    RadioButton rbMsg;
    @BindView(R.id.rbMine)
    RadioButton rbMine;
    @BindView(R.id.rgMenu)
    RadioGroup rgMenu;
    RadioButton[] rbs = new RadioButton[5];
    @BindView(R.id.rl_main)
    RelativeLayout rlMain;
    private FragmentManager fmManager;

    /**
     * 当前展示的Fragment
     */
    private Fragment currentFragment;
    /**
     * 上次点击返回按钮的时间戳
     */
    private long firstPressedTime;

    /**
     * 创建Fragment实例
     */
    private FragmentHome fragmentHome;
    private FragmentIM fragmentIM;
    private FragmentFind fragmentfind;
    private FragmentMsg fragmentMsg;
    private FragmentMine fragmentmine;

    @Override
    protected int getResId() {
        return R.layout.activity_main;
    }

    @SuppressLint({"UseCompatLoadingForDrawables", "ResourceAsColor"})
    @Override
    protected void getData() {

        rbs[0] = rbHome;
        rbs[1] = rbIM;
        rbs[2] = rbFind;
        rbs[3] = rbMsg;
        rbs[4] = rbMine;

        for (RadioButton rb : rbs) {
            //给每个RadioButton加入drawable限制边距控制显示大小
            Drawable[] drawables = rb.getCompoundDrawables();
            //获取drawables
            Rect rt = new Rect(0, 0, 70, 70);
            //定义一个Rect边界
            drawables[1].setBounds(rt);

            //添加限制给控件
            rb.setCompoundDrawables(null, drawables[1], null, null);
        }

        fmManager = getSupportFragmentManager();
        rgMenu.setOnCheckedChangeListener(this);
        //创建fragment实例
        fragmentHome = new FragmentHome();
        fragmentIM = new FragmentIM();
        fragmentfind = new FragmentFind();
        fragmentMsg = new FragmentMsg();
        fragmentmine = new FragmentMine();
        /**
         * 首次进入加载第一个界面
         */
        rbHome.setChecked(true);
        //设置状态栏颜色

    }


    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rbHome:
                replace(fragmentHome);
                break;
            case R.id.rbIM:
                replace(fragmentIM);
                break;
            case R.id.rbFind:
                replace(fragmentfind);
                break;
            case R.id.rbMsg:
                replace(fragmentMsg);
                break;
            case R.id.rbMine:
                replace(fragmentmine);
                break;
            default:
                break;
        }
    }

    /**
     * 切换页面显示fragment
     *
     * @param to 跳转到的fragment
     */
    private void replace(Fragment to) {
        if (to == null || to == currentFragment) {
            // 如果跳转的fragment为空或者跳转的fragment为当前fragment则不做操作
            return;
        }
        if (currentFragment == null) {
            // 如果当前fragment为空,即为第一次添加fragment
            fmManager.beginTransaction()
                    .add(R.id.flContent, to)
                    .commitAllowingStateLoss();
            currentFragment = to;
            return;
        }
        // 切换fragment
        FragmentTransaction transaction = fmManager.beginTransaction().hide(currentFragment);
        if (!to.isAdded()) {
            transaction.add(R.id.flContent, to);
        } else {
            transaction.show(to);
        }
        transaction.commitAllowingStateLoss();
        currentFragment = to;
    }

    //定义一个变量，来标识是否退出
    private static int isExit = 0;

    //实现按两次后退才退出
    Handler handler = new Handler() {
        @SuppressLint("HandlerLeak")
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit--;
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            isExit++;
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if (isExit < 2) {
            Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
            //利用handler延迟发送更改状态信息
            handler.sendEmptyMessageDelayed(0, 2000);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onBackPressed() {
        if (GSYVideoManager.backFromWindowFull(this)) {
            return;
        }
        super.onBackPressed();
    }
    @Override
    protected void onPause() {
        super.onPause();
        GSYVideoManager.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        GSYVideoManager.onResume(false);
        /*if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }*/
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        GSYVideoManager.releaseAllVideos();
       /*   if(EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }*/
    }
}