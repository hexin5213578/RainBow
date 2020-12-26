package com.YiDian.RainBow.friend;

import android.os.Bundle;
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
import com.YiDian.RainBow.friend.fragment.FragmentAtt;
import com.YiDian.RainBow.friend.fragment.FragmentFans;
import com.YiDian.RainBow.friend.fragment.FragmentFriend;
import com.YiDian.RainBow.friend.fragment.FragmentGroup;
import com.YiDian.RainBow.main.fragment.FragmentHome;
import com.leaf.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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


    /**
     * 创建Fragment实例
     */
    private FragmentFriend fragmentFriend;
    private FragmentFans fragmentFans;
    private FragmentGroup fragmentGroup;
    private FragmentAtt fragmentAtt;

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
        fragmentAtt = new FragmentAtt();

        list.add(fragmentFriend);
        list.add(fragmentFans);
        list.add(fragmentGroup);
        list.add(fragmentAtt);

        MyAdapter myAdapter = new MyAdapter(getSupportFragmentManager());
        vp.setAdapter(myAdapter);

        /**
         * 首次进入加载第一个界面
         */
        rbfriend.setChecked(true);
        vp.setCurrentItem(0);
        rbfriend.setTextSize(15);
        rbfriend.setTextAppearance(FriendsActivity.this, R.style.txt_bold);


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
                break;
            case R.id.ll_search:

                break;
            case R.id.ll_addfriend:

                break;
            case R.id.ll_addgroup:

                break;
        }
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
