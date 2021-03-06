package com.YiDian.RainBow.main.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseAvtivity;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.base.Common;
import com.YiDian.RainBow.custom.loading.CustomDialog;
import com.YiDian.RainBow.custom.zbar.CaptureActivity;
import com.YiDian.RainBow.dynamic.bean.SaveMsgSuccessBean;
import com.YiDian.RainBow.main.fragment.FragmentFind;
import com.YiDian.RainBow.main.fragment.FragmentHome;
import com.YiDian.RainBow.main.fragment.FragmentMine;
import com.YiDian.RainBow.main.fragment.FragmentMsg;
import com.YiDian.RainBow.main.fragment.mine.bean.GiftBean;
import com.YiDian.RainBow.service.GrayService;
import com.YiDian.RainBow.topic.SaveIntentMsgBean;
import com.YiDian.RainBow.user.PersonHomeActivity;
import com.YiDian.RainBow.utils.NetUtils;
import com.YiDian.RainBow.utils.SPUtil;
import com.shuyu.gsyvideoplayer.GSYVideoManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseAvtivity implements RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.rbHome)
    RadioButton rbHome;
    @BindView(R.id.rbFind)
    RadioButton rbFind;
    @BindView(R.id.rbMsg)
    RadioButton rbMsg;
    @BindView(R.id.rbMine)
    RadioButton rbMine;
    @BindView(R.id.rgMenu)
    RadioGroup rgMenu;
    RadioButton[] rbs = new RadioButton[4];
    @BindView(R.id.rl_main)
    RelativeLayout rlMain;
    @BindView(R.id.vp)
    ViewPager vp;
    String TAG = "xxx";
    /**
     * ??????Fragment??????
     */
    private FragmentHome fragmentHome;
    //private FragmentIM fragmentIM;
    private FragmentFind fragmentfind;
    private FragmentMsg fragmentMsg;
    private FragmentMine fragmentmine;
    private List<Fragment> list;
    private int userid;

    @Override
    protected int getResId() {
        return R.layout.activity_main;
    }

    private final Handler mHandler = new Handler() {
        @SuppressLint("HandlerLeak")
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 100) {
                //????????????
                setAlias();
            }
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void getData() {

        userid = Integer.parseInt(Common.getUserId());

        //????????????
        setAlias();





        boolean ignoringBatteryOptimizations = isIgnoringBatteryOptimizations(MainActivity.this);
        //??????????????????????????????
        if (!ignoringBatteryOptimizations) {
            //????????????????????????
            requestIgnoreBatteryOptimizations(MainActivity.this);
        }
        //????????????
        Intent intent = new Intent(MainActivity.this, GrayService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //android8.0????????????startForegroundService??????service
            startForegroundService(intent);
        } else {
            startService(intent);
        }
        rbs[0] = rbHome;
        //rbs[1] = rbIM;
        rbs[1] = rbFind;
        rbs[2] = rbMsg;
        rbs[3] = rbMine;

        for (RadioButton rb : rbs) {
            //?????????RadioButton??????drawable??????????????????????????????
            Drawable[] drawables = rb.getCompoundDrawables();
            //??????drawables
            Rect rt = new Rect(0, 0, 100, 70);
            //????????????Rect??????
            drawables[1].setBounds(rt);

            //?????????????????????
            rb.setCompoundDrawables(null, drawables[1], null, null);
        }

        list = new ArrayList<>();

        rgMenu.setOnCheckedChangeListener(this);
        //??????fragment??????
        fragmentHome = new FragmentHome();
        //fragmentIM = new FragmentIM();
        fragmentfind = new FragmentFind();
        fragmentMsg = new FragmentMsg();
        fragmentmine = new FragmentMine();
        /**
         * ?????????????????????????????????
         */
        rbHome.setChecked(true);
        vp.setCurrentItem(0);

        list.add(fragmentHome);
        list.add(fragmentfind);
        list.add(fragmentMsg);
        list.add(fragmentmine);

        MyAdapter myAdapter = new MyAdapter(getSupportFragmentManager());
        vp.setAdapter(myAdapter);

        getCount();
    }

    public void getCount() {
        NetUtils.getInstance().getApis()
                .dogetSendGift(userid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GiftBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(GiftBean giftBean) {
                        if (giftBean.getMsg().equals("????????????")) {
                            List<GiftBean.ObjectBean> list = giftBean.getObject();
                            if (list!=null  && list.size() > 0) {
                                GiftBean.ObjectBean bean = giftBean.getObject().get(0);
                                SPUtil.getInstance().saveData(MainActivity.this, SPUtil.FILE_NAME, SPUtil.SENG_COUNT, String.valueOf(bean.getAllNums()));
                            } else {
                                SPUtil.getInstance().saveData(MainActivity.this, SPUtil.FILE_NAME, SPUtil.SENG_COUNT, "0");
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
        NetUtils.getInstance().getApis()
                .dogetReciveGift(userid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GiftBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(GiftBean giftBean) {
                        if (giftBean.getMsg().equals("????????????")) {
                            List<GiftBean.ObjectBean> list = giftBean.getObject();
                            if (list!=null  && list.size() > 0) {
                                GiftBean.ObjectBean bean = giftBean.getObject().get(0);
                                SPUtil.getInstance().saveData(MainActivity.this, SPUtil.FILE_NAME, SPUtil.RECIVE_COUNT, String.valueOf(bean.getAllNums()));
                            } else {
                                SPUtil.getInstance().saveData(MainActivity.this, SPUtil.FILE_NAME, SPUtil.RECIVE_COUNT, "0");
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

    //???????????????????????????
    @RequiresApi(Build.VERSION_CODES.M)
    public boolean isIgnoringBatteryOptimizations(Context context) {
        boolean isIgnoring = false;
        if (Build.VERSION.SDK_INT >= 23) {
            PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);

            if (powerManager != null) {
                isIgnoring = powerManager.isIgnoringBatteryOptimizations(getPackageName());
            }
        }
        return isIgnoring;
    }

    //???????????????
    public void requestIgnoreBatteryOptimizations(Context context) {
        try {
            Intent intent = new Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
            intent.setData(Uri.parse("package:" + getPackageName()));
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    public void setAlias() {
        JPushInterface.setAlias(MainActivity.this, String.valueOf(userid), new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {
                Log.d("xxx", "?????????" + i);
                if (i == 0) {
                    Log.d("xxx", "??????????????????");
                }
                if (i == 6002) {
                    Message obtain = Message.obtain();
                    obtain.what = 100;
                    mHandler.sendMessageDelayed(obtain, 1000 * 30);//60??????????????????
                }
            }
        });
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rbHome:
                vp.setCurrentItem(0);
                break;
            /*case R.id.rbIM:
                replace(fragmentIM);
                break;*/
            case R.id.rbFind:
                vp.setCurrentItem(1);
                break;
            case R.id.rbMsg:
                vp.setCurrentItem(2);
                break;
            case R.id.rbMine:
                vp.setCurrentItem(3);
                break;
            default:
                break;
        }
    }


    //??????????????????
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getId(String str) {
        if (str.equals("??????????????????")) {
            vp.setCurrentItem(1);
            rbFind.setChecked(true);
        }
    }


    public class MyAdapter extends FragmentPagerAdapter {
        public MyAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

    }

    //??????????????????????????????????????????
    private static int isExit = 0;

    //??????????????????????????????
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
            Toast.makeText(this, "??????????????????", Toast.LENGTH_SHORT).show();
            //??????handler??????????????????????????????
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
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GSYVideoManager.releaseAllVideos();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }


    // TODO: 2020/12/25 0025 ?????????????????????
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("xxx", +requestCode + "   " + resultCode);
        Log.e("xxx", "????????????");
        // ?????????????????????
        if (resultCode == RESULT_OK) {
            if (data != null) {
                //??????????????????
                Bundle bundle = data.getExtras();
                String result = bundle.getString(CaptureActivity.EXTRA_STRING);

                //?????????????????????????????? ????????????+id?????? ???????????????????????? ??????ID??????????????????
                if (result.contains("??????App?????????")) {
                    String substring = result.substring(8);

                    Log.e("xxx", substring);

                    //????????????????????????
                    Intent intent = new Intent(MainActivity.this, PersonHomeActivity.class);
                    SaveIntentMsgBean saveIntentMsgBean = new SaveIntentMsgBean();
                    saveIntentMsgBean.setId(Integer.parseInt(substring));
                    //2??????????????????  1????????????id
                    saveIntentMsgBean.setFlag(1);
                    intent.putExtra("msg", saveIntentMsgBean);
                    startActivity(intent);
                }else{
                    Toast.makeText(this, "???????????????????????????", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "MainActivity???onRestart?????????");
        EventBus.getDefault().post("??????????????????????????????");
    }
}