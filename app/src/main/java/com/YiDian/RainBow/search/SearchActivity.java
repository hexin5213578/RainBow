package com.YiDian.RainBow.search;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseAvtivity;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.search.fragment.FragmentHotTopic;
import com.YiDian.RainBow.search.fragment.FragmentSearchPreson;
import com.YiDian.RainBow.search.fragment.FragmentSearchRoom;
import com.leaf.library.StatusBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends BaseAvtivity implements RadioGroup.OnCheckedChangeListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.rbSearchHuati)
    RadioButton rbSearchHuati;
    @BindView(R.id.rbSearchPerson)
    RadioButton rbSearchPerson;
    @BindView(R.id.rgTab)
    RadioGroup rgTab;
    RadioButton[] rbs = new RadioButton[3];
    @BindView(R.id.flContent)
    FrameLayout flContent;
    @BindView(R.id.rbSearchRoom)
    RadioButton rbSearchRoom;
    private FragmentManager fmManager;

    /**
     * 当前展示的Fragment
     */
    private Fragment currentFragment;

    /**
     * 创建Fragment实例
     */
    private FragmentHotTopic fragmentHotTopic;
    private FragmentSearchPreson fragmentSearchPreson;
    private FragmentSearchRoom fragmentSearchRoom;
    @Override
    protected int getResId() {
        return R.layout.activity_search;
    }

    @Override
    protected void getData() {
        //设置状态栏颜色 字体颜色
        StatusBarUtil.setGradientColor(this, toolbar);
        StatusBarUtil.setDarkMode(this);

        rbs[0] = rbSearchHuati;
        rbs[1] = rbSearchPerson;
        rbs[2] = rbSearchRoom;
        fmManager = getSupportFragmentManager();
        rgTab.setOnCheckedChangeListener(this);
        //创建fragment实例
        fragmentHotTopic = new FragmentHotTopic();
        fragmentSearchPreson = new FragmentSearchPreson();
        fragmentSearchRoom = new FragmentSearchRoom();
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

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    //单选框选中状态监听
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            //设置切换页面及选中字体大小
            case R.id.rbSearchHuati:
                //调用切换界面方法
                replace(fragmentHotTopic);
                rbSearchHuati.setTextSize(18);
                rbSearchHuati.setTextAppearance(R.style.txt_bold);

                rbSearchPerson.setTextSize(16);
                rbSearchPerson.setTextAppearance(R.style.txt_nomal);

                rbSearchRoom.setTextSize(16);
                rbSearchRoom.setTextAppearance(R.style.txt_nomal);
                break;
            case R.id.rbSearchPerson:
                replace(fragmentSearchPreson);
                rbSearchPerson.setTextSize(18);
                rbSearchPerson.setTextAppearance(R.style.txt_bold);

                rbSearchHuati.setTextSize(16);
                rbSearchHuati.setTextAppearance(R.style.txt_nomal);

                rbSearchRoom.setTextSize(16);
                rbSearchRoom.setTextAppearance(R.style.txt_nomal);
                break;
            case R.id.rbSearchRoom:
                rbSearchRoom.setTextSize(18);
                rbSearchRoom.setTextAppearance(R.style.txt_bold);

                rbSearchPerson.setTextSize(16);
                rbSearchPerson.setTextAppearance(R.style.txt_nomal);

                rbSearchHuati.setTextSize(16);
                rbSearchHuati.setTextAppearance(R.style.txt_nomal);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
