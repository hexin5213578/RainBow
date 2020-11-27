package com.YiDian.RainBow.setup;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
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
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseAvtivity;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.utils.DataCleanManager;
import com.leaf.library.StatusBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SetupActivity extends BaseAvtivity implements View.OnClickListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_back)
    LinearLayout ivBack;
    @BindView(R.id.rl_safe)
    RelativeLayout rlSafe;
    @BindView(R.id.tv_IsRealname)
    TextView tvIsRealname;
    @BindView(R.id.rl_shiming)
    RelativeLayout rlShiming;
    @BindView(R.id.rl_yinsi)
    RelativeLayout rlYinsi;
    @BindView(R.id.rl_kefu)
    RelativeLayout rlKefu;
    @BindView(R.id.rl_guanyu)
    RelativeLayout rlGuanyu;
    @BindView(R.id.tv_Memory)
    TextView tvMemory;
    @BindView(R.id.rl_clean)
    RelativeLayout rlClean;
    @BindView(R.id.tv_edition)
    TextView tvEdition;
    @BindView(R.id.rl_update)
    RelativeLayout rlUpdate;
    @BindView(R.id.rl_loginout)
    RelativeLayout rlLoginout;
    private PopupWindow mPopupWindow;

    @Override
    protected int getResId() {
        return R.layout.activity_setup;
    }

    @Override
    protected void getData() {
        StatusBarUtil.setGradientColor(this,toolbar);
        StatusBarUtil.setDarkMode(this);
        //绑定单击事件
        ivBack.setOnClickListener(this);
        rlSafe.setOnClickListener(this);
        rlShiming.setOnClickListener(this);
        rlYinsi.setOnClickListener(this);
        rlKefu.setOnClickListener(this);
        rlGuanyu.setOnClickListener(this);
        rlClean.setOnClickListener(this);
        rlUpdate.setOnClickListener(this);
        rlLoginout.setOnClickListener(this);


        //获取当前应用版本号
        String appVersionName = "";
        try {
            PackageInfo packageInfo = this.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(this.getPackageName(), 0);
            appVersionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("", e.getMessage());
        }
        tvEdition.setText(appVersionName + "");

        //获取缓存
        String totalCacheSize = null;
        try {
            totalCacheSize = DataCleanManager.getTotalCacheSize(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        tvMemory.setText(totalCacheSize);

    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
                //账户安全
            case R.id.rl_safe:

                break;
                //实名认证
            case R.id.rl_shiming:

                break;
                //隐私设置
            case R.id.rl_yinsi:

                break;
                //客服中心
            case R.id.rl_kefu:

                break;
                //关于我们
            case R.id.rl_guanyu:

                break;
                //清除缓存
            case R.id.rl_clean:
                //展示清除缓存的弹出框
                showCleanManager();
                break;
                //版本更新
            case R.id.rl_update:

                break;
                //登出
            case R.id.rl_loginout:

                break;
        }
    }
    // 弹出清除缓存
    public void showCleanManager() {
        //创建popwiondow弹出框
        mPopupWindow = new PopupWindow();
        mPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_cleanmanager, null);

        //取消
        view.findViewById(R.id.tv_cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        view.findViewById(R.id.tv_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //清除缓存
                DataCleanManager.clearAllCache(SetupActivity.this);

                //重新获取缓存
                String totalCacheSize = null;
                try {
                    totalCacheSize = DataCleanManager.getTotalCacheSize(SetupActivity.this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                tvMemory.setText(totalCacheSize);
                dismiss();
            }
        });

        //popwindow设置属性
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

    // 设置透明度
    public void setWindowAlpa(boolean isopen) {
        if (Build.VERSION.SDK_INT < 11) {
            return;
        }
        final Window window = this.getWindow();
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
            mPopupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
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
