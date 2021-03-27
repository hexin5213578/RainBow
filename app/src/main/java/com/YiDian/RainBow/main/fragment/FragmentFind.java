package com.YiDian.RainBow.main.fragment;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseFragment;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.custom.seekbar.DoubleSlideSeekBar;
import com.YiDian.RainBow.main.fragment.find.bean.SaveFilterBean;
import com.YiDian.RainBow.main.fragment.find.fragment.FragmentNear;
import com.YiDian.RainBow.main.fragment.find.fragment.Fragmentmatch;
import com.YiDian.RainBow.main.fragment.find.fragment.Fragmentmeet;
import com.YiDian.RainBow.utils.SPUtil;
import com.leaf.library.StatusBarUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


// 发现
public class FragmentFind extends BaseFragment implements RadioGroup.OnCheckedChangeListener {
    @BindView(R.id.rbMatch)
    RadioButton rbMatch;
    @BindView(R.id.rgTab)
    RadioGroup rgTab;
    @BindView(R.id.iv_fileter)
    LinearLayout ivFilter;
    @BindView(R.id.vp)
    ViewPager vp;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    String Person = "";
    String IsSingle = "";
    RadioButton[] rbs = new RadioButton[3];

    /**
     * 创建Fragment实例
     */
    private Fragmentmatch fragmentmatch;
    /*private Fragmentmeet fragmentmeet;
    private FragmentNear fragmentNear;*/
    private PopupWindow mPopupWindow;
    private List<Fragment> list;

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

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void getData() {
        //设置状态栏颜色与字体颜色
        StatusBarUtil.setGradientColor(getActivity(), toolbar);
        StatusBarUtil.setLightMode(getActivity());

        list = new ArrayList<>();

        rbs[0] = rbMatch;
        rgTab.setOnCheckedChangeListener(this);
        //创建fragment实例
        fragmentmatch = new Fragmentmatch();
       /* fragmentmeet = new Fragmentmeet();
        fragmentNear = new FragmentNear();*/

        list.add(fragmentmatch);
       /* list.add(fragmentmeet);
        list.add(fragmentNear);*/


        MyAdapter myAdapter = new MyAdapter(getChildFragmentManager());
        vp.setAdapter(myAdapter);
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
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            //设置切换页面及选中字体大小
            case R.id.rbMatch:
                vp.setCurrentItem(0);

                rbMatch.setTextSize(18);
                rbMatch.setTextAppearance(R.style.txt_bold);
                break;
            default:
                break;
        }
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

        String role = SPUtil.getInstance().getData(getContext(), SPUtil.FILE_NAME, SPUtil.SELECTROLE);
        if (role!=null){
            switch (role){
                case "T":
                    rb1.setChecked(true);
                    break;
                case "P":
                    rb2.setChecked(true);
                    break;
                case "H":
                    rb3.setChecked(true);
                    break;
                case "BI":
                    rb4.setChecked(true);
                    break;
                case "全部":
                    rb5.setChecked(true);
                    break;
                default:
                    break;
            }
        }
        Button bt_confirm = view.findViewById(R.id.bt_confirm);
        //隐藏弹出框单击事件
        ivCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        bt_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

                SaveFilterBean saveFilterBean = new SaveFilterBean();
                saveFilterBean.setRole(Person);

                SPUtil.getInstance().saveData(getContext(),SPUtil.FILE_NAME,SPUtil.SELECTROLE,Person);

                EventBus.getDefault().post(saveFilterBean);
                //关闭弹出框
                dismiss();
            }
        });
        //要为popWindow设置一个背景才有效
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
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
