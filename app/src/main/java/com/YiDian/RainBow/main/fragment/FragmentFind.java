package com.YiDian.RainBow.main.fragment;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseFragment;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.custom.seekbar.DoubleSlideSeekBar;
import com.YiDian.RainBow.main.fragment.find.bean.SaveFilterBean;
import com.YiDian.RainBow.main.fragment.find.fragment.FragmentNear;
import com.YiDian.RainBow.main.fragment.find.fragment.Fragmentmatch;
import com.YiDian.RainBow.main.fragment.find.fragment.Fragmentmeet;
import com.leaf.library.StatusBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;


// 发现
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

    String Person = "";
    String IsSingle = "";
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
    private PopupWindow mPopupWindow;

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

        ivFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelect();
            }
        });
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            //设置切换页面及选中字体大小
            case R.id.rbMatch:
                replace(fragmentmatch);
                ivFilter.setVisibility(View.VISIBLE);

                rbMatch.setTextSize(18);
                rbMatch.setTextAppearance(R.style.txt_bold);

                rbMeet.setTextSize(16);
                rbMeet.setTextAppearance(R.style.txt_nomal);

                rbNear.setTextSize(16);
                rbNear.setTextAppearance(R.style.txt_nomal);

                break;
            case R.id.rbMeet:
                replace(fragmentmeet);

                ivFilter.setVisibility(View.GONE);

                rbMeet.setTextSize(18);
                rbMeet.setTextAppearance(R.style.txt_bold);

                rbMatch.setTextSize(16);
                rbMatch.setTextAppearance(R.style.txt_nomal);
                rbNear.setTextSize(16);
                rbNear.setTextAppearance(R.style.txt_nomal);
                break;
            case R.id.rbNear:
                replace(fragmentNear);

                //附近关闭筛选按钮
                ivFilter.setVisibility(View.GONE);

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

    //弹出选择规格
    public void showSelect() {
        //创建popwiondow弹出框
        mPopupWindow = new PopupWindow();
        mPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_filter_person, null);

        //找控件
        LinearLayout ivCancle = view.findViewById(R.id.iv_cancle);
        RadioButton rb5 = view.findViewById(R.id.rb5);
        RadioButton rb1 = view.findViewById(R.id.rb1);
        RadioButton rb2 = view.findViewById(R.id.rb2);
        RadioButton rb3 = view.findViewById(R.id.rb3);
        RadioButton rb4 = view.findViewById(R.id.rb4);

        RadioButton rb_issingle = view.findViewById(R.id.rb_issingle);
        RadioButton rb_isNosingle = view.findViewById(R.id.rb_isNosingle);

        DoubleSlideSeekBar seekBar_age = view.findViewById(R.id.seekbar_age);
        SeekBar seekBar_distance = view.findViewById(R.id.seekbar_distance);
        TextView tv_age = view.findViewById(R.id.tv_age);
        TextView tv_distance = view.findViewById(R.id.tv_distance);

        Button bt_confirm = view.findViewById(R.id.bt_confirm);
        //隐藏弹出框单击事件
        ivCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        if (rb1.isChecked()) {
            Person = rb1.getText().toString();
        }
        if (rb2.isChecked()) {
            Person = rb2.getText().toString();
        }
        if (rb3.isChecked()) {
            Person = rb3.getText().toString();
        }
        if (rb4.isChecked()) {
            Person = rb4.getText().toString();
        }
        if (rb5.isChecked()) {
            Person = rb5.getText().toString();
        }


        if (rb_issingle.isChecked()) {
            IsSingle = rb_issingle.getText().toString();
        }
        if (rb_isNosingle.isChecked()) {
            IsSingle = rb_isNosingle.getText().toString();
        }
        //设置距离筛选
        seekBar_distance.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tv_distance.setText(progress+1+"km");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        //年龄筛选
        seekBar_age.setOnRangeListener(new DoubleSlideSeekBar.onRangeListener() {
            @Override
            public void onRange(float low, float big) {
                //设置年龄区间
                tv_age.setText(Integer.valueOf((int) low)+"-"+Integer.valueOf((int) big));
            }
        });

        bt_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String age = tv_age.getText().toString();
                int progress = seekBar_distance.getProgress();

                Toast.makeText(getContext(), ""+age+(progress+1)+Person+IsSingle, Toast.LENGTH_SHORT).show();

                Bundle bundle = new Bundle();
                SaveFilterBean saveFilterBean = new SaveFilterBean();
                saveFilterBean.setRole(Person);
                saveFilterBean.setAge(age);
                saveFilterBean.setDistance(progress+1);
                saveFilterBean.setIsSingle(IsSingle);
                bundle.putSerializable("Msg",saveFilterBean);
                //将筛选信息传递到匹配fragment
                fragmentmatch.setArguments(bundle);

                //关闭弹出框
                dismiss();
            }
        });
        //popwindow设置属性
        mPopupWindow.setAnimationStyle(R.style.popwindow_anim_style);
        mPopupWindow.setContentView(view);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setWindowAlpa(false);
            }
        });
        show(view);
    }

    //设置透明度
    public void setWindowAlpa(boolean isopen) {
        if (Build.VERSION.SDK_INT < 11) {
            return;
        }
        final Window window = getActivity().getWindow();
        final WindowManager.LayoutParams lp = window.getAttributes();
        window.setFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND, WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        ValueAnimator animator;
        if (isopen) {
            animator = ValueAnimator.ofFloat(1.0f, 0.5f);
        } else {
            animator = ValueAnimator.ofFloat(0.5f, 1.0f);
        }
        animator.setDuration(400);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float alpha = (float) animation.getAnimatedValue();
                lp.alpha = alpha;
                window.setAttributes(lp);
            }
        });
        animator.start();
    }

    /**
     * 显示PopupWindow
     */
    private void show(View v) {
        if (mPopupWindow != null && !mPopupWindow.isShowing()) {
            mPopupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
        }
        setWindowAlpa(true);

    }

    /**
     * 消失PopupWindow
     */
    public void dismiss() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
    }
}
