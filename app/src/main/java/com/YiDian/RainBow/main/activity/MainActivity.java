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
import com.YiDian.RainBow.custom.zbar.CaptureActivity;
import com.YiDian.RainBow.dynamic.bean.SaveMsgSuccessBean;
import com.YiDian.RainBow.main.fragment.FragmentFind;
import com.YiDian.RainBow.main.fragment.FragmentHome;
import com.YiDian.RainBow.main.fragment.FragmentMine;
import com.YiDian.RainBow.main.fragment.FragmentMsg;
import com.YiDian.RainBow.service.GrayService;
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

    /**
     * 创建Fragment实例
     */
    private FragmentHome fragmentHome;
    //private FragmentIM fragmentIM;
    private FragmentFind fragmentfind;
    private FragmentMsg fragmentMsg;
    private FragmentMine fragmentmine;
    private List<Fragment> list;

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
                //设置别名
                setAlias();
            }
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void getData() {


        JMessageClient.login("1038", "1038", new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                if (i==0){
                    Log.d("xxx", "1038 极光登录状态为" + i + "原因为" + s);


               /*     Bitmap bitmap=BitmapFactory.decodeResource(getResources(),R.mipmap.headimg3,null);//将资源文件转化为bitmap

                    File file = getFile(bitmap);

                    Log.d("xxx",file.getAbsolutePath());

                    JMessageClient.updateUserAvatar(file, new BasicCallback() {
                        @Override
                        public void gotResult(int i, String s) {
                            if (i==0){
                                Log.d("xxx","1038 头像设置成功");
                            }else {
                                Log.d("xxx",s);
                            }
                        }
                    });
*/
                    UserInfo myInfo = JMessageClient.getMyInfo();
                    Log.d("xxx","1038信息为"+myInfo.toJson());


                    String avatarFile = myInfo.getAvatar();
                    if (!avatarFile.equals(null)){
                        Log.d("xxx","存在头像");
                    }else{
                        Log.d("xxx","不存在头像");
                    }

                    //JMessageClient.deleteSingleConversation("1038","87ce5706efafab51ddd2be08");

                }
            }
        });

        //设置别名
        //setAlias();
        boolean ignoringBatteryOptimizations = isIgnoringBatteryOptimizations(MainActivity.this);
        //判断是否存在于白名单
        if (!ignoringBatteryOptimizations) {
            //不存在加入白名单
            requestIgnoreBatteryOptimizations(MainActivity.this);
        }
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
                        SPUtil.getInstance().saveData(MainActivity.this, SPUtil.FILE_NAME, SPUtil.UPTOKEN, upToken);
                        Log.d("xxx", "uptoken存入成功");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
        //开启服务
        Intent intent = new Intent(MainActivity.this, GrayService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //android8.0以上通过startForegroundService启动service
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
            //给每个RadioButton加入drawable限制边距控制显示大小
            Drawable[] drawables = rb.getCompoundDrawables();
            //获取drawables
            Rect rt = new Rect(0, 0, 100, 70);
            //定义一个Rect边界
            drawables[1].setBounds(rt);

            //添加限制给控件
            rb.setCompoundDrawables(null, drawables[1], null, null);
        }

        list = new ArrayList<>();

        rgMenu.setOnCheckedChangeListener(this);
        //创建fragment实例
        fragmentHome = new FragmentHome();
        //fragmentIM = new FragmentIM();
        fragmentfind = new FragmentFind();
        fragmentMsg = new FragmentMsg();
        fragmentmine = new FragmentMine();
        /**
         * 首次进入加载第一个界面
         */
        rbHome.setChecked(true);
        vp.setCurrentItem(0);

        list.add(fragmentHome);
        list.add(fragmentfind);
        list.add(fragmentMsg);
        list.add(fragmentmine);

        MyAdapter myAdapter = new MyAdapter(getSupportFragmentManager());
        vp.setAdapter(myAdapter);
    }

    //判断是否存在白名单
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

    //加入白名单
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
        JPushInterface.setAlias(MainActivity.this, "1038", new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {
                Log.d("xxx", "回调是" + i);
                if (i == 0) {
                    Log.d("xxx", "设置别名成功");
                }
                if (i == 6002) {
                    Message obtain = Message.obtain();
                    obtain.what = 100;
                    mHandler.sendMessageDelayed(obtain, 1000 * 60);//60秒后重新验证
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


    //跳转到匹配页
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getId(String str) {
        if (str.equals("跳转到匹配页")) {
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


    // TODO: 2020/12/25 0025 二维码识别回调
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("xxx", +requestCode + "   " + resultCode);
        Log.e("xxx", "识别成功");
        // 扫描二维码回传
        if (resultCode == RESULT_OK) {
            if (data != null) {
                //获取扫描结果
                Bundle bundle = data.getExtras();
                String result = bundle.getString(CaptureActivity.EXTRA_STRING);

                //判断信息是否属于彩虹 属于彩虹+id格式 跳转到扫描成功页 通过ID查询用户信息
                Log.e("xxx", result);
            }
        }
    }
    //在这里抽取了一个方法   可以封装到自己的工具类中...
    public File getFile(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        File file = new File(Environment.getExternalStorageDirectory() + "/temp.jpg");
        try {
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            InputStream is = new ByteArrayInputStream(baos.toByteArray());
            int x = 0;
            byte[] b = new byte[1024 * 100];
            while ((x = is.read(b)) != -1) {
                fos.write(b, 0, x);
            }
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }
}