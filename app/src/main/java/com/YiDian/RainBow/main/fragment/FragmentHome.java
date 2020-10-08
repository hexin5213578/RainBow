package com.YiDian.RainBow.main.fragment;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.App;
import com.YiDian.RainBow.base.BaseFragment;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.main.fragment.home.bean.NewDynamicBean;
import com.YiDian.RainBow.main.fragment.home.fragment.FragmentAttDynamic;
import com.YiDian.RainBow.main.fragment.home.fragment.FragmentHotDynamic;
import com.YiDian.RainBow.main.fragment.home.fragment.FragmentNearDynamic;
import com.YiDian.RainBow.main.fragment.home.fragment.FragmentNewDynamic;
import com.YiDian.RainBow.utils.NetUtils;
import com.leaf.library.StatusBarUtil;

import java.util.List;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

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
    @BindView(R.id.flContent)
    FrameLayout flContent;

    RadioButton[] rbs = new RadioButton[4];
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private FragmentManager fmManager;


    /**
     * 当前展示的Fragment
     */
    private Fragment currentFragment;

    /**
     * 创建Fragment实例
     */
    private FragmentNewDynamic fragmentNewDynamic;
    private FragmentNearDynamic fragmentNearDynamic;
    private FragmentAttDynamic fragmentAttDynamic;
    private FragmentHotDynamic fragmentHotDynamic;

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

    @SuppressLint("ResourceAsColor")
    @Override
    protected void getData() {
        StatusBarUtil.setGradientColor(getActivity(), toolbar);


        StatusBarUtil.setDarkMode(getActivity());

        rbs[0] = rbNewDynamic;
        rbs[1] = rbNearDynamic;
        rbs[2] = rbAttDynamic;
        rbs[3] = rbHotDynamic;

        fmManager = getChildFragmentManager();
        rgTab.setOnCheckedChangeListener(this);
        //创建fragment实例
        fragmentNewDynamic = new FragmentNewDynamic();
        fragmentNearDynamic = new FragmentNearDynamic();
        fragmentAttDynamic = new FragmentAttDynamic();
        fragmentHotDynamic = new FragmentHotDynamic();
        /**
         * 首次进入加载第一个界面
         */
        rbNewDynamic.setChecked(true);
        rbNewDynamic.setTextSize(18);
        rbNewDynamic.setTextAppearance(R.style.txt_bold);


    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            //设置切换页面及选中字体大小
            case R.id.rbNewDynamic:
                replace(fragmentNewDynamic);
                rbNewDynamic.setTextSize(18);
                rbNewDynamic.setTextAppearance(R.style.txt_bold);

                rbNearDynamic.setTextSize(16);
                rbNearDynamic.setTextAppearance(R.style.txt_nomal);

                rbAttDynamic.setTextSize(16);
                rbAttDynamic.setTextAppearance(R.style.txt_nomal);

                rbHotDynamic.setTextSize(16);
                rbHotDynamic.setTextAppearance(R.style.txt_nomal);
                break;
            case R.id.rbNearDynamic:
                replace(fragmentNearDynamic);
                rbNearDynamic.setTextSize(18);
                rbNearDynamic.setTextAppearance(R.style.txt_bold);

                rbNewDynamic.setTextSize(16);
                rbNewDynamic.setTextAppearance(R.style.txt_nomal);

                rbAttDynamic.setTextSize(16);
                rbAttDynamic.setTextAppearance(R.style.txt_nomal);

                rbHotDynamic.setTextSize(16);
                rbHotDynamic.setTextAppearance(R.style.txt_nomal);
                break;
            case R.id.rbAttDynamic:
                replace(fragmentAttDynamic);
                rbAttDynamic.setTextSize(18);
                rbAttDynamic.setTextAppearance(R.style.txt_bold);

                rbNewDynamic.setTextSize(16);
                rbNewDynamic.setTextAppearance(R.style.txt_nomal);

                rbNearDynamic.setTextSize(16);
                rbNearDynamic.setTextAppearance(R.style.txt_nomal);

                rbHotDynamic.setTextSize(16);
                rbHotDynamic.setTextAppearance(R.style.txt_nomal);
                break;
            case R.id.rbHotDynamic:
                replace(fragmentHotDynamic);
                rbHotDynamic.setTextSize(18);
                rbHotDynamic.setTextAppearance(R.style.txt_bold);

                rbNewDynamic.setTextSize(16);
                rbNewDynamic.setTextAppearance(R.style.txt_nomal);

                rbNearDynamic.setTextSize(16);
                rbNearDynamic.setTextAppearance(R.style.txt_nomal);

                rbAttDynamic.setTextSize(16);
                rbAttDynamic.setTextAppearance(R.style.txt_nomal);
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
}
