package com.YiDian.RainBow.main.fragment.find.fragment;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.text.TextPaint;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseFragment;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.main.fragment.find.fragment.meetfragment.IseenFragment;
import com.YiDian.RainBow.main.fragment.find.fragment.meetfragment.IseenMineFragment;
import com.YiDian.RainBow.main.fragment.find.fragment.meetfragment.MatchedFragment;
import com.google.android.material.tabs.TabLayout;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

//发现 - 遇见
public class Fragmentmeet extends BaseFragment {
    @BindView(R.id.tab)
    TabLayout tab;
    @BindView(R.id.vp)
    ViewPager vp;
    private List<String> tabs = new ArrayList<>();
    List<Fragment> fragments = new ArrayList<>();
    private LinearGradient mLinearGradient;

    @Override
    protected void getid(View view) {

    }

    @Override
    protected int getResId() {
        return R.layout.home_find_fragment_meet;
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void getData() {
        tabs.add("我看过的");
        tabs.add("看过我的");
        tabs.add("匹配过的");
        tab.addTab(tab.newTab().setText(tabs.get(0)));
        tab.addTab(tab.newTab().setText(tabs.get(1)));
        tab.addTab(tab.newTab().setText(tabs.get(2)));

        IseenFragment iseenFragment = new IseenFragment();
        IseenMineFragment iseenMineFragment = new IseenMineFragment();
        MatchedFragment matchedFragment = new MatchedFragment();

        fragments.add(iseenFragment);
        fragments.add(iseenMineFragment);
        fragments.add(matchedFragment);


        //设置tab的长度
        tab.post(new Runnable() {
            @Override
            public void run() {
                setIndicator(tab, 30, 30);
            }
        });
        //设置tab监听
        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @SuppressLint({"UseCompatLoadingForDrawables", "ResourceAsColor"})
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                View customView = tab.getCustomView();
                if (customView == null) {
                    tab.setCustomView(R.layout.tab_text_layout);
                }
                TextView textView = tab.getCustomView().findViewById(android.R.id.text1);
                textView.setTextSize(15);

                textView.setTextColor(R.color.start);

                TextPaint tp = textView.getPaint();
                tp.setFakeBoldText(true);
            }

            @SuppressLint("ResourceAsColor")
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View customView = tab.getCustomView();
                if (customView == null) {
                    tab.setCustomView(R.layout.tab_text_layout);
                }
                TextView textView = tab.getCustomView().findViewById(android.R.id.text1);

                textView.setTextSize(13);

                textView.setTextColor(R.color.color_999999);

                TextPaint tp = textView.getPaint();
                tp.setFakeBoldText(false);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        //设置适配器
        MyAdapter myAdapter = new MyAdapter(getChildFragmentManager());
        vp.setAdapter(myAdapter);
        //绑定tab与vp
        tab.setupWithViewPager(vp);
    }

    //设置tab栏下划线长度
    public void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("slidingTabIndicator");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }
    }

    public class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return tabs.get(position);
        }
    }
}
