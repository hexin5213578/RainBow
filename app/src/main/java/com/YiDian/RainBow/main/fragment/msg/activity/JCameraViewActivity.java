package com.YiDian.RainBow.main.fragment.msg.activity;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseAvtivity;
import com.YiDian.RainBow.base.BasePresenter;
import com.cjt2325.cameralibrary.JCameraView;
import com.cjt2325.cameralibrary.listener.ClickListener;
import com.cjt2325.cameralibrary.listener.ErrorListener;
import com.cjt2325.cameralibrary.listener.JCameraListener;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

public class JCameraViewActivity extends BaseAvtivity {
    @BindView(R.id.cameraview)
    JCameraView cameraview;

    @Override
    protected int getResId() {
        return R.layout.activity_jcamera;
    }

    @Override
    protected void getData() {
        if (Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        } else {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(option);
        }

        //设置视频保存路径
        cameraview.setSaveVideoPath(Environment.getExternalStorageDirectory().getPath() + File.separator + "JCamera");

        //设置只能录像或只能拍照或两种都可以（默认两种都可以）
        cameraview.setFeatures(JCameraView.BUTTON_STATE_BOTH);

        //设置视频质量
        cameraview.setMediaQuality(JCameraView.MEDIA_QUALITY_HIGH);

        //JCameraView监听
        cameraview.setErrorLisenter(new ErrorListener() {
            @Override
            public void onError() {
                //打开Camera失败回调
                Log.i("CJT", "open camera error");
            }
            @Override
            public void AudioPermissionError() {
                //没有录取权限回调
                Log.i("CJT", "AudioPermissionError");
            }
        });

        cameraview.setJCameraLisenter(new JCameraListener() {
            @Override
            public void captureSuccess(Bitmap bitmap) {
                //获取图片bitmap
                Log.i("CJT", "bitmap = " + bitmap.getWidth());

                // TODO: 2021/1/11 0011  图片获取成功 直接发送并返回上一界面

            }
            @Override
            public void recordSuccess(String url,Bitmap firstFrame) {
                //获取视频路径
                Log.i("CJT", "url = " + url);

                // TODO: 2021/1/11 0011 视频获取成功 直接发送并返回上一界面
            }
            //@Override
            //public void quit() {
            //    (1.1.9+后用左边按钮的点击事件替换)
            //}
        });
        //左边按钮点击事件
        cameraview.setLeftClickListener(new ClickListener() {
            @Override
            public void onClick() {
                JCameraViewActivity.this.finish();
            }
        });
        //右边按钮点击事件
        cameraview.setRightClickListener(new ClickListener() {
            @Override
            public void onClick() {

            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        cameraview.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        cameraview.onPause();
    }
    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

}
