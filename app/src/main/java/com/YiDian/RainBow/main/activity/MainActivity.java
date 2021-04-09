package com.YiDian.RainBow.main.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseAvtivity;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.base.Common;
import com.YiDian.RainBow.custom.zbar.CaptureActivity;
import com.YiDian.RainBow.imgroup.activity.LordMsgActivity;
import com.YiDian.RainBow.imgroup.activity.MemberMsgActivity;
import com.YiDian.RainBow.imgroup.activity.NoJoinGroupActivity;
import com.YiDian.RainBow.imgroup.bean.GroupMsgBean;
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
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.leaf.library.StatusBarUtil;
import com.shuyu.gsyvideoplayer.GSYVideoManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.Conversation;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 主页
 *
 * @author hmy
 */
public class MainActivity extends BaseAvtivity {

    @BindView(R.id.rl_main)
    RelativeLayout rlMain;
    String TAG = "xxx";
    @BindView(R.id.tl_commen)
    CommonTabLayout tlCommen;
    @BindView(R.id.vp)
    ViewPager vp;
    RadioButton[] rbs = new RadioButton[4];
    /**
     * 创建Fragment实例
     */
    private FragmentHome fragmentHome;
    //private FragmentIM fragmentIM;
    private FragmentFind fragmentfind;
    private FragmentMsg fragmentMsg;
    private FragmentMine fragmentmine;
    private int userid;
    private Intent intent;
    int a = 0;
    //tab选中图标集合
    private ArrayList<Integer> selectedIconRes = new ArrayList<>();
    //tab未选中图标集合
    private ArrayList<Integer> unselectedIconRes = new ArrayList<>();
    //tab标题集合
    private ArrayList<String> titleRes = new ArrayList<>();
    //fragment集合
    private ArrayList<Fragment> fsRes = new ArrayList<>();
    //CommonTabLayout 所需数据集合
    private List<CustomTabEntity> data = new ArrayList<>();
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
        StatusBarUtil.setDarkMode(MainActivity.this);

        userid = Integer.parseInt(Common.getUserId());

        //设置别名
        setAlias();

        //创建fragment实例
        fragmentHome = new FragmentHome();
        //fragmentIM = new FragmentIM();
        fragmentfind = new FragmentFind();
        fragmentMsg = new FragmentMsg();
        fragmentmine = new FragmentMine();

        //图片选中资源
        selectedIconRes.add(R.drawable.homeselected);
        selectedIconRes.add(R.drawable.orderselected);
        selectedIconRes.add(R.drawable.msgselected);
        selectedIconRes.add(R.drawable.meselected);

        //图片未选中资源
        unselectedIconRes.add(R.drawable.home);
        unselectedIconRes.add(R.drawable.order);
        unselectedIconRes.add(R.drawable.msg);
        unselectedIconRes.add(R.drawable.me);

        //标题资源
        titleRes.add("动态");
        titleRes.add("发现");
        titleRes.add("消息");
        titleRes.add("我的");

        //fragment数据
        fsRes.add(fragmentHome);
        fsRes.add(fragmentfind);
        fsRes.add(fragmentMsg);
        fsRes.add(fragmentmine);

        //设置数据
        for (int i = 0; i < titleRes.size(); i++) {
            final int index = i;
            data.add(new CustomTabEntity() {
                @Override
                public String getTabTitle() {
                    return titleRes.get(index);
                }

                @Override
                public int getTabSelectedIcon() {
                    return selectedIconRes.get(index);
                }

                @Override
                public int getTabUnselectedIcon() {
                    return unselectedIconRes.get(index);
                }
            });
        }
        //设置数据
        tlCommen.setTabData((ArrayList<CustomTabEntity>) data);
        vp.setAdapter(new MyPagerAdapter(getSupportFragmentManager(),fsRes));
        initListener();

        //设置默认第0个
        vp.setCurrentItem(0);

        //隐藏指定位置未读红点或消息
        tlCommen.hideMsg(0);
        tlCommen.hideMsg(1);
        tlCommen.hideMsg(3);

        getCount();
        //遍历当前未读消息
        a = 0;
        unReadMsg();
    }

    /**
     * 获取未读消息
     */
    public void unReadMsg() {
        List<Conversation> conversationList = JMessageClient.getConversationList();

        for (int i =0;i<conversationList.size();i++){
            int unReadMsgCnt = conversationList.get(i).getUnReadMsgCnt();

            a+=unReadMsgCnt;

        }
        if (a==0){
            //隐藏指定位置未读红点或消息
            tlCommen.hideMsg(2);
        }else{
            tlCommen.showMsg(2, a);
            tlCommen.setMsgMargin(2, -5, 5);
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getStr(String str){
        if (str.equals("收到了信息")){
            a = 0;
            unReadMsg();
        }
    }

    public void initListener() {
        //TabLayout监听
        tlCommen.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                //显示相应的item界面
                vp.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        //ViewPager监听
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                //设置相应选中图标和颜色
                tlCommen.setCurrentTab(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    public class MyPagerAdapter extends FragmentPagerAdapter {

        private ArrayList<Fragment> fs;

        public MyPagerAdapter(FragmentManager fm, ArrayList<Fragment> fs) {
            super(fm);
            this.fs = fs;
        }

        @Override
        public Fragment getItem(int i) {
            return fs.get(i);
        }

        @Override
        public int getCount() {
            return fs.size();
        }
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
                        if (giftBean.getMsg().equals("查询成功")) {
                            List<GiftBean.ObjectBean> list = giftBean.getObject();
                            if (list != null && list.size() > 0) {
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
                        if (giftBean.getMsg().equals("查询成功")) {
                            List<GiftBean.ObjectBean> list = giftBean.getObject();
                            if (list != null && list.size() > 0) {
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

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    public void setAlias() {
        JPushInterface.setAlias(MainActivity.this, String.valueOf(userid), new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {
                Log.d("xxx", "回调是" + i);
                if (i == 0) {
                    Log.d("xxx", "设置别名成功");
                }
                if (i == 6002) {
                    Message obtain = Message.obtain();
                    obtain.what = 100;
                    mHandler.sendMessageDelayed(obtain, 1000 * 30);//60秒后重新验证
                }
            }
        });
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
                //开启定位
                EventBus.getDefault().post("获取位置权限成功");

            } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                EventBus.getDefault().post("获取位置权限失败");

            }
        }
    }

    //跳转到匹配页
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getId(String str) {
        if (str.equals("跳转到匹配页")) {
            //设置默认第0个
            vp.setCurrentItem(0);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
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
                if (result.contains("彩虹App内用户")) {
                    String substring = result.substring(8);

                    Log.e("xxx", substring);

                    //跳转到用户信息页
                    Intent intent = new Intent(MainActivity.this, PersonHomeActivity.class);
                    SaveIntentMsgBean saveIntentMsgBean = new SaveIntentMsgBean();
                    saveIntentMsgBean.setId(Integer.parseInt(substring));
                    //2标记传入姓名  1标记传入id
                    saveIntentMsgBean.setFlag(1);
                    intent.putExtra("msg", saveIntentMsgBean);
                    startActivity(intent);
                } else {
                    String substring = result.substring(8);
                    Log.d("xxx", "扫描到的群id为" + substring);

                    NetUtils.getInstance()
                            .getApis().doGetGroupMsg(Integer.parseInt(substring), userid)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<GroupMsgBean>() {
                                @Override
                                public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {

                                }

                                @Override
                                public void onNext(@io.reactivex.annotations.NonNull GroupMsgBean groupMsgBean) {
                                    GroupMsgBean.ObjectBean object = groupMsgBean.getObject();
                                    if (object.getGroupType() == 1) {
                                        //我是群主 跳转到群主查看群信息页
                                        intent = new Intent(MainActivity.this, LordMsgActivity.class);
                                        intent.putExtra("groupid", object.getGroupId());
                                        startActivity(intent);

                                    } else if (object.getGroupType() == 2) {
                                        // TODO: 2021/3/12  我是群成员  跳转到成员查看信息页
                                        intent = new Intent(MainActivity.this, MemberMsgActivity.class);
                                        intent.putExtra("groupid", object.getGroupId());
                                        startActivity(intent);

                                    } else {
                                        // TODO: 2021/3/12  未加入该群 跳转到未加入查看群信息页
                                        intent = new Intent(MainActivity.this, NoJoinGroupActivity.class);
                                        intent.putExtra("groupid", object.getGroupId());
                                        startActivity(intent);
                                    }
                                }

                                @Override
                                public void onError(@io.reactivex.annotations.NonNull Throwable e) {

                                }

                                @Override
                                public void onComplete() {

                                }
                            });

                }
            }
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "MainActivity的onRestart执行了");
        EventBus.getDefault().post("重新获取我的基本信息");
    }
}