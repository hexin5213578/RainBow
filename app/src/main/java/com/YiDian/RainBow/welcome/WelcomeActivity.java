package com.YiDian.RainBow.welcome;

import android.Manifest;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.agreement.YinsiActivity;
import com.YiDian.RainBow.agreement.YonghuActivity;
import com.YiDian.RainBow.base.BaseAvtivity;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.base.Common;
import com.YiDian.RainBow.dynamic.bean.SaveMsgSuccessBean;
import com.YiDian.RainBow.login.activity.CompleteMsgActivity;
import com.YiDian.RainBow.login.activity.LoginActivity;
import com.YiDian.RainBow.main.activity.MainActivity;
import com.YiDian.RainBow.main.fragment.home.adapter.NewDynamicAdapter;
import com.YiDian.RainBow.regist.activity.RegistActivity;
import com.YiDian.RainBow.topic.SaveIntentMsgBean;
import com.YiDian.RainBow.topic.TopicDetailsActivity;
import com.YiDian.RainBow.user.PersonHomeActivity;
import com.YiDian.RainBow.utils.NetUtils;
import com.YiDian.RainBow.utils.SPUtil;
import com.leaf.library.StatusBarUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class WelcomeActivity extends BaseAvtivity {
    private Handler handler;
    private PopupWindow mPopupWindow1;
    private String userId;
    private Intent intent;

    @Override
    protected int getResId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void getData() {

        userId = Common.getUserId();

        StatusBarUtil.setTransparentForWindow(WelcomeActivity.this);
        StatusBarUtil.setDarkMode(WelcomeActivity.this);

        handler = new Handler();

        //???????????????uploadToken
        NetUtils.getInstance().getApis().getUpdateToken()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SaveMsgSuccessBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(SaveMsgSuccessBean saveMsgSuccessBean) {
                        String upToken = saveMsgSuccessBean.getUpToken();
                        SPUtil.getInstance().saveData(WelcomeActivity.this, SPUtil.FILE_TOKEN, SPUtil.UPTOKEN, upToken);
                        Log.d("xxx", "uptoken????????????");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
        requestPermission();

        String isshow = SPUtil.getInstance().getData(WelcomeActivity.this, SPUtil.FILE_TOKEN, SPUtil.IS_SHOW);

        if (isshow != null) {
            if (isshow.equals("0")) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        showselect();
                    }
                }, 200);
            } else {
                if (userId != null) {
                    JMessageClient.login(userId, userId, new BasicCallback() {
                        @Override
                        public void gotResult(int i, String s) {
                            if (i == 0) {
                                Log.d("xxx", userId + "?????????????????????" + i + "?????????" + s);
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        String isLogin = Common.getIsLogin();
                                        String isPerfect = Common.getIsPerfect();

                                        if (isLogin != null) {
                                            if (isLogin.equals("0")) {
                                                if (isPerfect != null && isPerfect.equals("0")) {
                                                    intent = new Intent(WelcomeActivity.this, MainActivity.class);
                                                    startActivity(intent);
                                                    finish();
                                                } else {
                                                    intent = new Intent(WelcomeActivity.this, CompleteMsgActivity.class);
                                                    startActivity(intent);
                                                    finish();
                                                }
                                            } else {
                                                intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        } else {
                                            intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }
                                }, 2000);//2????????????Runnable??????run??????
                            } else {
                                Log.d("xxx", "??????????????????????????????" + i + "?????????" + s);
                            }
                        }
                    });
                } else {
                    Log.d("xxx", "???????????????");
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            String isLogin = Common.getIsLogin();
                            if (isLogin != null) {
                                if (isLogin.equals("0")) {
                                    intent = new Intent(WelcomeActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            } else {
                                intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    }, 2000);//2????????????Runnable??????run??????
                }
            }
        } else {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    showselect();
                }
            }, 200);
        }
    }

    //??????6.0???????????????????????????
    public void request(){
        if(Build.VERSION.SDK_INT==23){
            //????????????????????????
            //??????????????????????????????   checkSelfPermission this?????????    ????????????   ????????????PackageManager.PERMISSION_GRANTED
            //????????????     PackageManager#PERMISSION_DENIED
            int request = ContextCompat.checkSelfPermission(this, Manifest.permission.SYSTEM_ALERT_WINDOW);
            if(request!=PackageManager.PERMISSION_GRANTED){
                //??????????????????
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SYSTEM_ALERT_WINDOW},105);
                return;
            }

        }
    }
    //???????????????????????????
    //?????? requestCode????????????????????????????????????????????????????????????
    //String[] permission????????????????????????????????????
    //int[] grantResults ????????????????????????????????????????????????????????????
    //??????????????????????????????????????????????????????????????????????????????????????????????????????1
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==105){
            if(grantResults [0]== PackageManager.PERMISSION_GRANTED){

            }else {
                Toast.makeText(this, "???????????????????????????????????????", Toast.LENGTH_SHORT).show();
            }
        }


    }



    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // ????????????????????????
            if (Environment.isExternalStorageManager()) {

            } else {
                intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                intent.setData(Uri.parse("package:" + WelcomeActivity.this.getPackageName()));
                startActivityForResult(intent, 101);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (Environment.isExternalStorageManager()) {
                Toast.makeText(this, "????????????????????????", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(this, "????????????????????????", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void showselect() {
        //??????????????????
        mPopupWindow1 = new PopupWindow();
        mPopupWindow1.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow1.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        View view = LayoutInflater.from(WelcomeActivity.this).inflate(R.layout.dialog_home_selector, null);


        TextView tv_cancle = view.findViewById(R.id.tv_no);
        TextView tv_confirm = view.findViewById(R.id.tv_agree);
        TextView tv_content = view.findViewById(R.id.tv_content);

        String str = tv_content.getText().toString();

        tv_content.setText(updateTextStyle(str));
        tv_content.setHighlightColor(Color.parseColor("#00000000"));

        //???????????????????????????????????????
        tv_content.setMovementMethod(LinkMovementMethod.getInstance());


        tv_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SPUtil.getInstance().saveData(WelcomeActivity.this, SPUtil.FILE_TOKEN, SPUtil.IS_SHOW, "0");
                finish();
            }
        });
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SPUtil.getInstance().saveData(WelcomeActivity.this, SPUtil.FILE_TOKEN, SPUtil.IS_SHOW, "1");

                if (userId != null) {
                    JMessageClient.login(userId, userId, new BasicCallback() {
                        @Override
                        public void gotResult(int i, String s) {
                            if (i == 0) {
                                Log.d("xxx", userId + "?????????????????????" + i + "?????????" + s);
                                String isLogin = Common.getIsLogin();
                                String isPerfect = Common.getIsPerfect();

                                if (isLogin != null) {
                                    if (isLogin.equals("0")) {
                                        if (isPerfect != null && isPerfect.equals("0")) {
                                            intent = new Intent(WelcomeActivity.this, MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            intent = new Intent(WelcomeActivity.this, CompleteMsgActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    } else {
                                        intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                } else {
                                    intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            } else {
                                Log.d("xxx", "??????????????????????????????" + i + "?????????" + s);
                            }
                        }
                    });
                } else {
                    Log.d("xxx", "???????????????");
                    String isLogin = Common.getIsLogin();
                    if (isLogin != null) {
                        if (isLogin.equals("0")) {
                            intent = new Intent(WelcomeActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    } else {
                        intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });
        //popwindow????????????
        mPopupWindow1.setContentView(view);
        mPopupWindow1.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow1.setFocusable(false);
        mPopupWindow1.setOutsideTouchable(false);
        mPopupWindow1.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setWindowAlpa(false);
            }
        });
        show(view);
    }

    private SpannableStringBuilder updateTextStyle(String content) {
        SpannableStringBuilder spannableString = new SpannableStringBuilder();
        spannableString.append(content);

        int privacyBeginIndex = content.indexOf("???");
        int privacyEndIndex = content.indexOf("???") + 1;
        int protocolBeginIndex = content.lastIndexOf("???");
        int protocolEndIndex = content.lastIndexOf("???") + 1;

        // ????????????????????????????????????????????????
        ClickableSpan clickableSpanOne = new ClickableSpan() {
            @Override
            public void onClick(View view) {

                intent = new Intent(WelcomeActivity.this, YinsiActivity.class);
                startActivity(intent);

            }

            @Override
            public void updateDrawState(TextPaint paint) {
                paint.setColor(Color.parseColor("#8CD6F7"));//????????????
                paint.setUnderlineText(false); //???????????????
                // paint.setStrikeThruText(true);
            }
        };

        // ????????????????????????????????????????????????
        ClickableSpan clickableSpanTwo = new ClickableSpan() {
            @Override
            public void onClick(View view) {

                intent = new Intent(WelcomeActivity.this, YonghuActivity.class);
                startActivity(intent);
            }

            @Override
            public void updateDrawState(TextPaint paint) {
                paint.setColor(Color.parseColor("#8CD6F7"));//????????????
                paint.setUnderlineText(false); //???????????????
                // paint.setStrikeThruText(true);
            }
        };


        //????????????????????????????????????????????????????????????????????????????????????
        spannableString.setSpan(clickableSpanOne, protocolBeginIndex, protocolEndIndex, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        spannableString.setSpan(clickableSpanTwo, privacyBeginIndex, privacyEndIndex, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);


        return spannableString;
    }

    private void show(View v) {
        if (mPopupWindow1 != null && !mPopupWindow1.isShowing()) {
            mPopupWindow1.showAtLocation(v, Gravity.CENTER, 0, 0);
        }
        setWindowAlpa(true);
    }

    //???????????????
    public void setWindowAlpa(boolean isopen) {
        if (android.os.Build.VERSION.SDK_INT < 11) {
            return;
        }
        final Window window = WelcomeActivity.this.getWindow();
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
}
