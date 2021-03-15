package com.YiDian.RainBow.friend.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseAvtivity;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.base.Common;
import com.YiDian.RainBow.custom.customDialog.CustomDialogCleanNotice;
import com.YiDian.RainBow.dynamic.activity.DevelopmentDynamicActivity;
import com.YiDian.RainBow.friend.fragment.FragmentFollow;
import com.YiDian.RainBow.friend.fragment.FragmentFans;
import com.YiDian.RainBow.friend.fragment.FragmentFriend;
import com.YiDian.RainBow.friend.fragment.FragmentGroup;
import com.YiDian.RainBow.imgroup.activity.CreateGroupActivity;
import com.YiDian.RainBow.setup.activity.RealnameActivity;
import com.YiDian.RainBow.setup.bean.GetRealDataBean;
import com.YiDian.RainBow.utils.NetUtils;
import com.leaf.library.StatusBarUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

//我的好友页
public class FriendsActivity extends BaseAvtivity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.ll_search)
    LinearLayout llSearch;
    @BindView(R.id.ll_addfriend)
    LinearLayout llAddfriend;
    @BindView(R.id.ll_addgroup)
    LinearLayout llAddgroup;
    @BindView(R.id.rbfriend)
    RadioButton rbfriend;
    @BindView(R.id.rbfans)
    RadioButton rbfans;
    @BindView(R.id.rbgroup)
    RadioButton rbgroup;
    @BindView(R.id.rbatt)
    RadioButton rbatt;
    @BindView(R.id.rgTab)
    RadioGroup rgTab;
    @BindView(R.id.vp)
    ViewPager vp;

    RadioButton[] rbs = new RadioButton[4];
    private List<Fragment> list;
    String TAG = "xxx";

    /**
     * 创建Fragment实例
     */
    private FragmentFriend fragmentFriend;
    private FragmentFans fragmentFans;
    private FragmentGroup fragmentGroup;
    private FragmentFollow fragmentAtt;
    private Intent intent;
    private int userid;
    private boolean isClick = false;

    @Override
    protected int getResId() {
        return R.layout.activity_friend;
    }

    @Override
    protected void getData() {
        StatusBarUtil.setGradientColor(FriendsActivity.this, toolbar);
        StatusBarUtil.setDarkMode(FriendsActivity.this);
        //创建集合存入fragment
        list = new ArrayList<>();

        userid = Integer.valueOf(Common.getUserId());

        Intent intent = getIntent();
        int flag = intent.getIntExtra("flag", 0);

        //获取实名认证信息
        getRealStatus();

        llBack.setOnClickListener(this);
        llAddfriend.setOnClickListener(this);
        llAddgroup.setOnClickListener(this);
        llSearch.setOnClickListener(this);

        rbs[0] = rbfriend;
        rbs[1] = rbfans;
        rbs[2] = rbgroup;
        rbs[3] = rbatt;

        rgTab.setOnCheckedChangeListener(this);

        fragmentFriend = new FragmentFriend();
        fragmentFans = new FragmentFans();
        fragmentGroup = new FragmentGroup();
        fragmentAtt = new FragmentFollow();

        list.add(fragmentFriend);
        list.add(fragmentFans);
        list.add(fragmentGroup);
        list.add(fragmentAtt);

        MyAdapter myAdapter = new MyAdapter(getSupportFragmentManager());
        vp.setAdapter(myAdapter);

        if (flag==1){
            vp.setCurrentItem(0);
            rbfriend.setChecked(true);

            rbfriend.setTextSize(15);
            rbfriend.setTextAppearance(FriendsActivity.this, R.style.txt_bold);
        }else if (flag==2){
            vp.setCurrentItem(1);

            rbfans.setChecked(true);

            llAddgroup.setVisibility(View.GONE);
            llAddfriend.setVisibility(View.VISIBLE);

            rbfans.setTextSize(15);
            rbfans.setTextAppearance(FriendsActivity.this, R.style.txt_bold);
        }else if (flag==3){
            vp.setCurrentItem(2);
            rbgroup.setChecked(true);

            llAddgroup.setVisibility(View.VISIBLE);
            llAddfriend.setVisibility(View.GONE);

            rbgroup.setTextSize(15);
            rbgroup.setTextAppearance(FriendsActivity.this, R.style.txt_bold);

        }else {
            vp.setCurrentItem(3);
            rbatt.setChecked(true);

            llAddgroup.setVisibility(View.GONE);
            llAddfriend.setVisibility(View.VISIBLE);

            rbatt.setTextSize(15);
            rbatt.setTextAppearance(FriendsActivity.this, R.style.txt_bold);

        }
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        rbfriend.setChecked(true);
                        break;
                    case 1:
                        rbfans.setChecked(true);
                        break;
                    case 2:
                        rbgroup.setChecked(true);
                        break;
                    case 3:
                        rbatt.setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    //获取实名认证状态
    public void getRealStatus(){
        NetUtils.getInstance().getApis()
                .doGetRealMsg(userid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GetRealDataBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(GetRealDataBean getRealDataBean) {
                        String msg = getRealDataBean.getMsg();
                        if (msg.equals("您还没有提交实名信息")) {
                            isClick = false;
                        } else {
                            int auditStatus = getRealDataBean.getObject().getAuditStatus();
                            if (auditStatus == 2) {
                                isClick = false;
                            }
                            if (auditStatus == 1) {
                                isClick = true;
                            }
                            if (auditStatus==0){
                                isClick = false;
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_back:
                //返回主页
                finish();
                EventBus.getDefault().post("收到了信息");
                break;
            case R.id.ll_search:
            case R.id.ll_addfriend:
                //跳转至添加好友页
                intent = new Intent(FriendsActivity.this,AddFriendActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_addgroup:
                //跳转至创建群聊页
                if (isClick){
                    //跳转到发布动态页
                    intent = new Intent(FriendsActivity.this, CreateGroupActivity.class);
                    startActivity(intent);
                }else{
                    CustomDialogCleanNotice.Builder builder = new CustomDialogCleanNotice.Builder(FriendsActivity.this);
                    builder.setMessage("您还没有提交实名认证信息").setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(FriendsActivity.this, RealnameActivity.class));
                        }
                    });
                    builder.setNegativeButton("取消",
                            new android.content.DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    builder.create().show();
                }
                break;
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        EventBus.getDefault().post("重新获取群组列表");
    }

    public class MyAdapter extends FragmentPagerAdapter{

        public MyAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }
    }
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            //设置切换页面及选中字体大小
            case R.id.rbfriend:
                vp.setCurrentItem(0);

                llAddgroup.setVisibility(View.GONE);
                llAddfriend.setVisibility(View.VISIBLE);

                rbfriend.setTextSize(15);
                rbfriend.setTextAppearance(FriendsActivity.this, R.style.txt_bold);

                rbfans.setTextSize(12);
                rbfans.setTextAppearance(FriendsActivity.this, R.style.txt_nomal);

                rbgroup.setTextSize(12);
                rbgroup.setTextAppearance(FriendsActivity.this, R.style.txt_nomal);

                rbatt.setTextSize(12);
                rbatt.setTextAppearance(FriendsActivity.this, R.style.txt_nomal);
                break;
            case R.id.rbfans:


                vp.setCurrentItem(1);

                llAddgroup.setVisibility(View.GONE);
                llAddfriend.setVisibility(View.VISIBLE);

                rbfans.setTextSize(15);
                rbfans.setTextAppearance(FriendsActivity.this, R.style.txt_bold);

                rbfriend.setTextSize(12);
                rbfriend.setTextAppearance(FriendsActivity.this, R.style.txt_nomal);

                rbgroup.setTextSize(12);
                rbgroup.setTextAppearance(FriendsActivity.this, R.style.txt_nomal);

                rbatt.setTextSize(12);
                rbatt.setTextAppearance(FriendsActivity.this, R.style.txt_nomal);
                break;
            case R.id.rbgroup:
                vp.setCurrentItem(2);

                llAddgroup.setVisibility(View.VISIBLE);
                llAddfriend.setVisibility(View.GONE);

                rbgroup.setTextSize(15);
                rbgroup.setTextAppearance(FriendsActivity.this, R.style.txt_bold);

                rbfriend.setTextSize(12);
                rbfriend.setTextAppearance(FriendsActivity.this, R.style.txt_nomal);

                rbfans.setTextSize(12);
                rbfans.setTextAppearance(FriendsActivity.this, R.style.txt_nomal);

                rbatt.setTextSize(12);
                rbatt.setTextAppearance(FriendsActivity.this, R.style.txt_nomal);
                break;
            case R.id.rbatt:
                vp.setCurrentItem(3);

                llAddgroup.setVisibility(View.GONE);
                llAddfriend.setVisibility(View.VISIBLE);

                rbatt.setTextSize(15);
                rbatt.setTextAppearance(FriendsActivity.this, R.style.txt_bold);

                rbfriend.setTextSize(12);
                rbfriend.setTextAppearance(FriendsActivity.this, R.style.txt_nomal);

                rbfans.setTextSize(12);
                rbfans.setTextAppearance(FriendsActivity.this, R.style.txt_nomal);

                rbgroup.setTextSize(12);
                rbgroup.setTextAppearance(FriendsActivity.this, R.style.txt_nomal);
                break;
            default:
                break;
        }
    }
}
