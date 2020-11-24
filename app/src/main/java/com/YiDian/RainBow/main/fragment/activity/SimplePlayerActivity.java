package com.YiDian.RainBow.main.fragment.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.widget.Toolbar;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseAvtivity;
import com.YiDian.RainBow.base.BasePresenter;
import com.leaf.library.StatusBarUtil;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SimplePlayerActivity extends BaseAvtivity {
    @BindView(R.id.viewPlayer)
    StandardGSYVideoPlayer viewPlayer;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private OrientationUtils orientationUtils;
    private String url;

    @Override
    protected int getResId() {
        return R.layout.activity_simpleplayer;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void getData() {
        StatusBarUtil.setLightMode(this);
        StatusBarUtil.setGradientColor(this, toolbar);

        Intent intent = getIntent();
        url = intent.getStringExtra("url");


        viewPlayer.setUp(url, true, "");

        //增加封面
        ImageView imageView = new ImageView(this);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(R.mipmap.headimg);
        viewPlayer.setThumbImageView(imageView);
        //增加title
        viewPlayer.getTitleTextView().setVisibility(View.VISIBLE);
        //设置返回键
        viewPlayer.getBackButton().setVisibility(View.VISIBLE);
        //设置旋转
        orientationUtils = new OrientationUtils(this, viewPlayer);
        //设置全屏按键功能,这是使用的是选择屏幕，而不是全屏
        viewPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orientationUtils.resolveByClick();
            }
        });
        //是否可以滑动调整
        viewPlayer.setIsTouchWiget(true);
        //设置返回按键功能
        viewPlayer.getBackButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        viewPlayer.startPlayLogic();
    }

    @Override
    protected void onPause() {
        super.onPause();
        viewPlayer.onVideoPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewPlayer.onVideoResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GSYVideoManager.releaseAllVideos();
        if (orientationUtils != null)
            orientationUtils.releaseListener();
    }

    @Override
    public void onBackPressed() {
        //先返回正常状态
        if (orientationUtils.getScreenType() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            viewPlayer.getFullscreenButton().performClick();
            return;
        }
        //释放所有
        viewPlayer.setVideoAllCallBack(null);
        super.onBackPressed();
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
