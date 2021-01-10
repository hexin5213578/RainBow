package com.YiDian.RainBow.main.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseFragment;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.dynamic.activity.DevelopmentDynamicActivity;
import com.YiDian.RainBow.main.fragment.home.fragment.FragmentAttDynamic;
import com.YiDian.RainBow.main.fragment.home.fragment.FragmentHotDynamic;
import com.YiDian.RainBow.main.fragment.home.fragment.FragmentNearDynamic;
import com.YiDian.RainBow.main.fragment.home.fragment.FragmentNewDynamic;
import com.YiDian.RainBow.search.SearchActivity;
import com.leaf.library.StatusBarUtil;
import com.shuyu.gsyvideoplayer.GSYVideoManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class FragmentHome extends BaseFragment implements RadioGroup.OnCheckedChangeListener {
    @BindView(R.id.ll_search)
    LinearLayout llSearch;
    @BindView(R.id.iv_add)
    ImageView ivAdd;
    @BindView(R.id.rbNewDynamic)
    RadioButton rbNewDynamic;
    @BindView(R.id.rbNearDynamic)
    RadioButton rbNearDynamic;
    @BindView(R.id.rbAttDynamic)
    RadioButton rbAttDynamic;
    @BindView(R.id.rbHotDynamic)
    RadioButton rbHotDynamic;
    @BindView(R.id.rgTab)
    RadioGroup rgTab;


    RadioButton[] rbs = new RadioButton[4];
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.vp)
    ViewPager vp;

    /**
     * 创建Fragment实例
     */
    private FragmentNewDynamic fragmentNewDynamic;
    private FragmentNearDynamic fragmentNearDynamic;
    private FragmentAttDynamic fragmentAttDynamic;
    private FragmentHotDynamic fragmentHotDynamic;
    private List<Fragment> list;

    @Override
    protected void getid(View view) {

    }

    @Override
    protected int getResId() {
        return R.layout.home_fragment_home;
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("ResourceAsColor")
    @Override
    protected void getData() {
        StatusBarUtil.setGradientColor(getActivity(), toolbar);
        StatusBarUtil.setDarkMode(getActivity());

        list = new ArrayList<>();

        llSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到搜索页
                startActivity(new Intent(getContext(), SearchActivity.class));
            }
        });
        ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到发布动态页
                startActivity(new Intent(getContext(), DevelopmentDynamicActivity.class));
            }
        });
        //申请开启内存卡权限
        if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) && (getContext().checkSelfPermission
                (Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
            //请求权限
            this.requestPermissions(new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        rbs[0] = rbNewDynamic;
        rbs[1] = rbNearDynamic;
        rbs[2] = rbAttDynamic;
        rbs[3] = rbHotDynamic;

        rgTab.setOnCheckedChangeListener(this);
        //创建fragment实例
        fragmentNewDynamic = new FragmentNewDynamic();
        fragmentNearDynamic = new FragmentNearDynamic();
        fragmentAttDynamic = new FragmentAttDynamic();
        fragmentHotDynamic = new FragmentHotDynamic();

        list.add(fragmentNewDynamic);
        list.add(fragmentNearDynamic);
        list.add(fragmentAttDynamic);
        list.add(fragmentHotDynamic);

        MyAdapter myAdapter = new MyAdapter(getChildFragmentManager());
        vp.setAdapter(myAdapter);

        /**
         * 首次进入加载第一个界面
         */
        rbNewDynamic.setChecked(true);
        vp.setCurrentItem(0);
        rbNewDynamic.setTextSize(18);
        rbNewDynamic.setTextAppearance(getContext(), R.style.txt_bold);

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
        switch (checkedId) {
            //设置切换页面及选中字体大小
            case R.id.rbNewDynamic:
                vp.setCurrentItem(0);
                rbNewDynamic.setTextSize(18);
                rbNewDynamic.setTextAppearance(getContext(), R.style.txt_bold);

                rbNearDynamic.setTextSize(16);
                rbNearDynamic.setTextAppearance(getContext(), R.style.txt_nomal);

                rbAttDynamic.setTextSize(16);
                rbAttDynamic.setTextAppearance(getContext(), R.style.txt_nomal);

                rbHotDynamic.setTextSize(16);
                rbHotDynamic.setTextAppearance(getContext(), R.style.txt_nomal);
                break;
            case R.id.rbNearDynamic:

                vp.setCurrentItem(1);

                rbNearDynamic.setTextSize(18);
                rbNearDynamic.setTextAppearance(getContext(), R.style.txt_bold);

                rbNewDynamic.setTextSize(16);
                rbNewDynamic.setTextAppearance(getContext(), R.style.txt_nomal);

                rbAttDynamic.setTextSize(16);
                rbAttDynamic.setTextAppearance(getContext(), R.style.txt_nomal);

                rbHotDynamic.setTextSize(16);
                rbHotDynamic.setTextAppearance(getContext(), R.style.txt_nomal);
                break;
            case R.id.rbAttDynamic:
                vp.setCurrentItem(2);

                rbAttDynamic.setTextSize(18);
                rbAttDynamic.setTextAppearance(getContext(), R.style.txt_bold);

                rbNewDynamic.setTextSize(16);
                rbNewDynamic.setTextAppearance(getContext(), R.style.txt_nomal);

                rbNearDynamic.setTextSize(16);
                rbNearDynamic.setTextAppearance(getContext(), R.style.txt_nomal);

                rbHotDynamic.setTextSize(16);
                rbHotDynamic.setTextAppearance(getContext(), R.style.txt_nomal);
                break;
            case R.id.rbHotDynamic:
                vp.setCurrentItem(3);

                rbHotDynamic.setTextSize(18);
                rbHotDynamic.setTextAppearance(getContext(), R.style.txt_bold);

                rbNewDynamic.setTextSize(16);
                rbNewDynamic.setTextAppearance(getContext(), R.style.txt_nomal);

                rbNearDynamic.setTextSize(16);
                rbNearDynamic.setTextAppearance(getContext(), R.style.txt_nomal);

                rbAttDynamic.setTextSize(16);
                rbAttDynamic.setTextAppearance(getContext(), R.style.txt_nomal);
                break;
            default:
                break;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        GSYVideoManager.onPause();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        GSYVideoManager.releaseAllVideos();
    }

    @Override
    public void onResume() {
        super.onResume();
        GSYVideoManager.onResume();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        GSYVideoManager.releaseAllVideos();
    }
}
