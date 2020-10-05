package com.YiDian.RainBow.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.utils.SPUtil;
import com.bumptech.glide.Glide;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @ClassName: BaseAvtivity
 * @Description: (java类作用描述)
 * @Author: hmy
 */
public abstract class BaseAvtivity<P extends BasePresenter> extends AppCompatActivity implements BaseView  {
    private P presenter;
    private Unbinder bind;
    Dialog mLoadingDialog;
    private LatitudeandlongitudeBean latitudeandlongitudeBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getResId());

        //NetUtils netUtils = new NetUtils();

        presenter = initPresenter();
        bind = ButterKnife.bind(this);
        getData();
    }

    // 展示loading圈
    public void showDialog() {
        if (mLoadingDialog == null) {
            mLoadingDialog = new Dialog(this);
            mLoadingDialog.setCancelable(false);
            View v = View.inflate(this, R.layout.dialog_loading, null);
            ImageView iv = v.findViewById(R.id.iv_loading);
            Glide.with(this).asGif().load(R.mipmap.ic_launcher).into(iv);

            mLoadingDialog.addContentView(v,
                    new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));
        }

        mLoadingDialog.show();
    }
    //  隐藏loading圈
    public void hideDialog() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }

    public P getPresenter() {
        return presenter;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(presenter!=null){
            presenter.detachView();
            presenter=null;
        }
        bind.unbind();
    }

    // 设置标题栏颜色
    public void setTitleColor(Activity activity){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(activity.getResources().getColor(R.color.colorPrimary));
        }
    }
    //判断网络状态
    public boolean NetWork(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
        if(activeNetworkInfo!=null){
            return true;
        }
        return false;
    }

    protected abstract int getResId();
    protected abstract void getData();
    protected abstract P initPresenter();

    //  关闭软键盘
    public void closekeyboard(){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE); //得到InputMethodManager的实例
        if (imm.isActive()) {//如果开启
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);//关闭软键盘，开启方法相同，这个方法是切换开启与关闭状态的
        }
    }

    /**
     * 拨打电话（直接拨打电话）
     *
     * @param phoneNum 电话号码
     */
    public void callPhone(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        startActivity(intent);
    }
}
