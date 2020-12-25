package com.YiDian.RainBow.search;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseAvtivity;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.search.fragment.FragmentHotTopic;
import com.YiDian.RainBow.search.fragment.FragmentSearchPreson;
import com.YiDian.RainBow.search.fragment.FragmentSearchRoom;
import com.leaf.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends BaseAvtivity implements RadioGroup.OnCheckedChangeListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_back)
    LinearLayout ivBack;
    @BindView(R.id.rbSearchHuati)
    RadioButton rbSearchHuati;
    @BindView(R.id.rbSearchPerson)
    RadioButton rbSearchPerson;
    @BindView(R.id.rgTab)
    RadioGroup rgTab;
    RadioButton[] rbs = new RadioButton[3];
    @BindView(R.id.vp)
    ViewPager vp;

    /**
     * 创建Fragment实例
     */
    private FragmentHotTopic fragmentHotTopic;
    private FragmentSearchPreson fragmentSearchPreson;
    private FragmentSearchRoom fragmentSearchRoom;
    private List<Fragment> list;

    @Override
    protected int getResId() {
        return R.layout.activity_search;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void getData() {
        //设置状态栏颜色 字体颜色
        StatusBarUtil.setGradientColor(this, toolbar);
        StatusBarUtil.setDarkMode(this);
        list = new ArrayList<>();

        rbs[0] = rbSearchHuati;
        rbs[1] = rbSearchPerson;
        rgTab.setOnCheckedChangeListener(this);
        //创建fragment实例
        fragmentHotTopic = new FragmentHotTopic();
        fragmentSearchPreson = new FragmentSearchPreson();
        //fragmentSearchRoom = new FragmentSearchRoom();


        list.add(fragmentHotTopic);
        list.add(fragmentSearchPreson);

        MyAdapter myAdapter = new MyAdapter(getSupportFragmentManager());
        vp.setAdapter(myAdapter);

        /**
         * 首次进入加载第一个界面
         */
        rbSearchHuati.setChecked(true);
        rbSearchHuati.setTextSize(18);
        rbSearchHuati.setTextAppearance(R.style.txt_bold);

        //点击返回 关闭界面
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
    protected BasePresenter initPresenter() {
        return null;
    }

    //单选框选中状态监听
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.iv_back:
                finish();
                break;
            //设置切换页面及选中字体大小
            case R.id.rbSearchHuati:
                //调用切换界面方法
                vp.setCurrentItem(0);
                rbSearchHuati.setTextSize(18);
                rbSearchHuati.setTextAppearance(R.style.txt_bold);

                rbSearchPerson.setTextSize(16);
                rbSearchPerson.setTextAppearance(R.style.txt_nomal);

                break;
            case R.id.rbSearchPerson:
                vp.setCurrentItem(1);

                rbSearchPerson.setTextSize(18);
                rbSearchPerson.setTextAppearance(R.style.txt_bold);

                rbSearchHuati.setTextSize(16);
                rbSearchHuati.setTextAppearance(R.style.txt_nomal);
                break;
            default:
                break;
        }
    }

}
