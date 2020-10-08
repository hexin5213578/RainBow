package com.YiDian.RainBow.main.fragment;

import android.annotation.SuppressLint;
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
import com.YiDian.RainBow.base.BaseFragment;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.main.fragment.im.fragment.Fragmentchatroom;
import com.YiDian.RainBow.main.fragment.im.fragment.Fragmentunion;
import com.leaf.library.StatusBarUtil;

import butterknife.BindView;

public class FragmentIM extends BaseFragment implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {
    @BindView(R.id.rbChatRoom)
    RadioButton rbChatRoom;
    @BindView(R.id.rbUnion)
    RadioButton rbUnion;
    @BindView(R.id.rgTab)
    RadioGroup rgTab;
    @BindView(R.id.iv_add)
    ImageView ivAdd;
    @BindView(R.id.iv_search)
    ImageView ivSearch;
    @BindView(R.id.flContent)
    FrameLayout flContent;
    RadioButton[] rbs = new RadioButton[2];
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
    private Fragmentchatroom fragmentchatroom;
    private Fragmentunion fragmentunion;

    @Override
    protected void getid(View view) {

    }

    @Override
    protected int getResId() {
        return R.layout.home_fragment_im;
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

        ivAdd.setOnClickListener(this);
        ivSearch.setOnClickListener(this);

        rbs[0] = rbChatRoom;
        rbs[1] = rbUnion;
        fmManager = getChildFragmentManager();
        rgTab.setOnCheckedChangeListener(this);
        //创建fragment实例
        fragmentchatroom = new Fragmentchatroom();
        fragmentunion = new Fragmentunion();
        /**
         * 首次进入加载第一个界面
         */
        rbChatRoom.setChecked(true);
        rbChatRoom.setTextSize(18);
        rbChatRoom.setTextAppearance(R.style.txt_bold);

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            //设置切换页面及选中字体大小
            case R.id.rbChatRoom:
                replace(fragmentchatroom);
                rbChatRoom.setTextSize(18);
                rbChatRoom.setTextAppearance(R.style.txt_bold);

                rbUnion.setTextSize(16);
                rbUnion.setTextAppearance(R.style.txt_nomal);

                break;
            case R.id.rbUnion:
                replace(fragmentunion);
                rbUnion.setTextSize(18);
                rbUnion.setTextAppearance(R.style.txt_bold);

                rbChatRoom.setTextSize(16);
                rbChatRoom.setTextAppearance(R.style.txt_nomal);

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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_search:

                break;
            case R.id.iv_add:

                break;
        }
    }
}
