package com.YiDian.RainBow.setup.activity;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.YiDian.RainBow.base.Common;
import com.YiDian.RainBow.custom.customDialog.CustomDialogCleanNotice;
import com.YiDian.RainBow.login.activity.LoginActivity;
import com.YiDian.RainBow.setup.bean.GetRealDataBean;
import com.YiDian.RainBow.utils.DataCleanManager;
import com.YiDian.RainBow.utils.NetUtils;
import com.YiDian.RainBow.utils.SPUtil;
import com.leaf.library.StatusBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jpush.im.android.api.JMessageClient;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

//设置页
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
    @BindView(R.id.rl_hei)
    RelativeLayout rlHei;
    @BindView(R.id.rl_fankui)
    RelativeLayout rlFankui;
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
    private Intent intent;
    private int userid;
    private PopupWindow popupWindow1;

    @Override
    protected int getResId() {
        return R.layout.activity_setup;
    }

    @Override
    protected void getData() {
        StatusBarUtil.setGradientColor(this, toolbar);
        StatusBarUtil.setDarkMode(this);
        //绑定单击事件
        ivBack.setOnClickListener(this);
        rlSafe.setOnClickListener(this);
        rlShiming.setOnClickListener(this);
        rlHei.setOnClickListener(this);
        rlFankui.setOnClickListener(this);
        rlGuanyu.setOnClickListener(this);
        rlClean.setOnClickListener(this);
        rlUpdate.setOnClickListener(this);
        rlLoginout.setOnClickListener(this);


        userid = Integer.valueOf(Common.getUserId());
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
        //获取实名认证状态
        getRealStatus();
    }

    //获取实名认证状态
    public void getRealStatus() {
        NetUtils.getInstance().getApis()
                .doGetRealMsg(userid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GetRealDataBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(GetRealDataBean getRealDataBean) {
                        String msg = getRealDataBean.getMsg();
                        if (msg.equals("您还没有提交实名信息")) {
                            tvIsRealname.setText("未认证");
                        } else {
                            int auditStatus = getRealDataBean.getObject().getAuditStatus();
                            if (auditStatus == 2) {
                                tvIsRealname.setText("正在审核中");
                            }
                            if (auditStatus == 1) {
                                tvIsRealname.setText("已认证");
                            }
                            if (auditStatus == 0) {
                                tvIsRealname.setText("认证失败");
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            //账户安全
            case R.id.rl_safe:
                intent = new Intent(SetupActivity.this, AccountSafeActivity.class);
                startActivity(intent);
                break;
            //实名认证
            case R.id.rl_shiming:
                intent = new Intent(SetupActivity.this, RealnameActivity.class);
                startActivity(intent);
                break;
            //黑名单
            case R.id.rl_hei:
                intent = new Intent(SetupActivity.this, BlackListActivity.class);
                startActivity(intent);
                break;
            //关于我们
            case R.id.rl_guanyu:
                intent = new Intent(SetupActivity.this, AboutUsActivity.class);
                startActivity(intent);
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
                // TODO: 2021/1/6 0006 退出登录
                CustomDialogCleanNotice.Builder builder = new CustomDialogCleanNotice.Builder(SetupActivity.this);
                builder.setMessage("确定退出登录吗?").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SPUtil.unReg(SetupActivity.this, SPUtil.FILE_NAME);
                        JMessageClient.logout();
                        intent = new Intent(SetupActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
                builder.setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builder.create().show();
                break;
            default:

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
