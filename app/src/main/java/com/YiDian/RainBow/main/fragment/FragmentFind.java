package com.YiDian.RainBow.main.fragment;

import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseFragment;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.main.activity.MainActivity;
import com.YiDian.RainBow.main.fragment.find.fragment.FragmentNear;
import com.YiDian.RainBow.main.fragment.find.fragment.Fragmentmatch;
import com.YiDian.RainBow.main.fragment.find.fragment.Fragmentmeet;
import com.leaf.library.StatusBarUtil;

import butterknife.BindView;

public class FragmentFind extends BaseFragment implements RadioGroup.OnCheckedChangeListener {
    @BindView(R.id.rbMatch)
    RadioButton rbMatch;
    @BindView(R.id.rbMeet)
    RadioButton rbMeet;
    @BindView(R.id.rbNear)
    RadioButton rbNear;
    @BindView(R.id.rgTab)
    RadioGroup rgTab;
    @BindView(R.id.iv_filter)
    ImageView ivFilter;
    @BindView(R.id.flContent)
    FrameLayout flContent;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    RadioButton[] rbs = new RadioButton[3];
    private FragmentManager fmManager;

    /**
     * 当前展示的Fragment
     */
    private Fragment currentFragment;

    /**
     * 创建Fragment实例
     */
    private Fragmentmatch fragmentmatch;
    private Fragmentmeet fragmentmeet;
    private FragmentNear fragmentNear;

    @Override
    protected void getid(View view) {

    }

    @Override
    protected int getResId() {
        return R.layout.home_fragment_find;
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void getData() {
        //设置状态栏颜色与字体颜色
        StatusBarUtil.setGradientColor(getActivity(), toolbar);
        StatusBarUtil.setDarkMode(getActivity());

        rbs[0] = rbMatch;
        rbs[1] = rbMeet;
        rbs[2] = rbNear;
        fmManager = getChildFragmentManager();
        rgTab.setOnCheckedChangeListener(this);
        //创建fragment实例
        fragmentmatch = new Fragmentmatch();
        fragmentmeet = new Fragmentmeet();
        fragmentNear = new FragmentNear();
        /**
         * 首次进入加载第一个界面
         */
        rbMatch.setChecked(true);
        rbMatch.setTextSize(18);
        rbMatch.setTextAppearance(R.style.txt_bold);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            //设置切换页面及选中字体大小
            case R.id.rbMatch:
                replace(fragmentmatch);
                rbMatch.setTextSize(18);
                rbMatch.setTextAppearance(R.style.txt_bold);

                rbMeet.setTextSize(16);
                rbMeet.setTextAppearance(R.style.txt_nomal);

                rbNear.setTextSize(16);
                rbNear.setTextAppearance(R.style.txt_nomal);

                break;
            case R.id.rbMeet:
                replace(fragmentmeet);
                rbMeet.setTextSize(18);
                rbMeet.setTextAppearance(R.style.txt_bold);

                rbMatch.setTextSize(16);
                rbMatch.setTextAppearance(R.style.txt_nomal);
                rbNear.setTextSize(16);
                rbNear.setTextAppearance(R.style.txt_nomal);
                break;
            case R.id.rbNear:
                replace(fragmentNear);
                rbNear.setTextSize(18);
                rbNear.setTextAppearance(R.style.txt_bold);

                rbMatch.setTextSize(16);
                rbMatch.setTextAppearance(R.style.txt_nomal);

                rbMeet.setTextSize(16);
                rbMeet.setTextAppearance(R.style.txt_nomal);
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
