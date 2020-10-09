package com.YiDian.RainBow.main.fragment.activity;

import android.annotation.SuppressLint;
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
    final String urlH = "https://vd3.bdstatic.com/mda-kiund8tjth20wphr/v1-cae/sc/mda-kiund8tjth20wphr.mp4?auth_key=1602209471-0-0-a5add77a37ec7dcb68acd8c25ad7d7ea&bcevod_channel=searchbox_feed&pd=1&pt=3&abtest=8797_2&sle=1&sl=786&split=720054";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private OrientationUtils orientationUtils;

    @Override
    protected int getResId() {
        return R.layout.activity_simpleplayer;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void getData() {
        StatusBarUtil.setLightMode(this);
        StatusBarUtil.setGradientColor(this, toolbar);


        viewPlayer.setUp(urlH, true, "");

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
