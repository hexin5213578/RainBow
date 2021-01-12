package com.YiDian.RainBow.custom.videoplayer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.YiDian.RainBow.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.shuyu.gsyvideoplayer.utils.CommonUtil;
import com.shuyu.gsyvideoplayer.utils.Debuger;
import com.shuyu.gsyvideoplayer.utils.GSYVideoType;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.shuyu.gsyvideoplayer.video.base.GSYBaseVideoPlayer;


/**
 * 带封面
 * Created by guoshuyu on 2017/9/3.
 */

public class SampleCoverVideoNoOpen extends StandardGSYVideoPlayer {

    ImageView mCoverImage;

    String mCoverOriginUrl;

    int  mCoverOriginId = 0;

    Bitmap mDefaultRes;
    private TextView tv_duration;

    public SampleCoverVideoNoOpen(Context context, Boolean fullFlag) {
        super(context, fullFlag);
    }

    public SampleCoverVideoNoOpen(Context context) {
        super(context);
    }

    public SampleCoverVideoNoOpen(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void init(Context context) {
        super.init(context);
        mCoverImage = (ImageView) findViewById(R.id.thumbImage);
        tv_duration = (TextView) findViewById(R.id.tv_duration);
        if (mThumbImageViewLayout != null &&
                (mCurrentState == -1 || mCurrentState == CURRENT_STATE_NORMAL || mCurrentState == CURRENT_STATE_ERROR)) {
            mThumbImageViewLayout.setVisibility(VISIBLE);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.video_layout_cover_no_open;
    }
    public void setDuration(String str){
        tv_duration.setText(str);
    }
    public void loadCoverImage(String url, Bitmap res) {
        mCoverOriginUrl = url;
        mDefaultRes = res;
        Glide.with(getContext().getApplicationContext())
                .setDefaultRequestOptions(
                        new RequestOptions()
                                .frame(1000000)
                                .centerCrop()
                                )
                .load(url)
                .into(mCoverImage);
    }

    public void loadCoverImageBy(int id, Bitmap res) {
        mCoverOriginId = id;
        mDefaultRes = res;
        mCoverImage.setImageResource(id);
    }

    @Override
    public GSYBaseVideoPlayer startWindowFullscreen(Context context, boolean actionBar, boolean statusBar) {
        GSYBaseVideoPlayer gsyBaseVideoPlayer = super.startWindowFullscreen(context, actionBar, statusBar);
        SampleCoverVideoNoOpen sampleCoverVideo = (SampleCoverVideoNoOpen) gsyBaseVideoPlayer;
        if(mCoverOriginUrl != null) {
            sampleCoverVideo.loadCoverImage(mCoverOriginUrl, mDefaultRes);
        } else  if(mCoverOriginId != 0) {
            sampleCoverVideo.loadCoverImageBy(mCoverOriginId, mDefaultRes);
        }
        return gsyBaseVideoPlayer;
    }


    @Override
    public GSYBaseVideoPlayer showSmallVideo(Point size, boolean actionBar, boolean statusBar) {
        //下面这里替换成你自己的强制转化
        SampleCoverVideoNoOpen sampleCoverVideo = (SampleCoverVideoNoOpen) super.showSmallVideo(size, actionBar, statusBar);
        sampleCoverVideo.mStartButton.setVisibility(GONE);
        sampleCoverVideo.mStartButton = null;
        return sampleCoverVideo;
    }

    @Override
    protected void cloneParams(GSYBaseVideoPlayer from, GSYBaseVideoPlayer to) {
        super.cloneParams(from, to);
        SampleCoverVideoNoOpen sf = (SampleCoverVideoNoOpen) from;
        SampleCoverVideoNoOpen st = (SampleCoverVideoNoOpen) to;
        st.mShowFullAnimation = sf.mShowFullAnimation;
    }


    /**
     * 退出window层播放全屏效果
     */
    @SuppressWarnings("ResourceType")
    @Override
    protected void clearFullscreenLayout() {
        if (!mFullAnimEnd) {
            return;
        }
        mIfCurrentIsFullscreen = false;
        int delay = 0;
        if (mOrientationUtils != null) {
            delay = mOrientationUtils.backToProtVideo();
            mOrientationUtils.setEnable(false);
            if (mOrientationUtils != null) {
                mOrientationUtils.releaseListener();
                mOrientationUtils = null;
            }
        }

        if (!mShowFullAnimation) {
            delay = 0;
        }

        final ViewGroup vp = (CommonUtil.scanForActivity(getContext())).findViewById(Window.ID_ANDROID_CONTENT);
        final View oldF = vp.findViewById(getFullId());
        if (oldF != null) {
            //此处fix bug#265，推出全屏的时候，虚拟按键问题
            SampleCoverVideoNoOpen gsyVideoPlayer = (SampleCoverVideoNoOpen) oldF;
            gsyVideoPlayer.mIfCurrentIsFullscreen = false;
        }

        if (delay == 0) {
            backToNormal();
        } else {
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    backToNormal();
                }
            }, delay);
        }

    }


    /******************* 下方两个重载方法，在播放开始前不屏蔽封面，不需要可屏蔽 ********************/
    @Override
    public void onSurfaceUpdated(Surface surface) {
        super.onSurfaceUpdated(surface);
        if (mThumbImageViewLayout != null && mThumbImageViewLayout.getVisibility() == VISIBLE) {
            mThumbImageViewLayout.setVisibility(INVISIBLE);
        }
    }

    @Override
    protected void setViewShowState(View view, int visibility) {
        if (view == mThumbImageViewLayout && visibility != VISIBLE) {
            return;
        }
        super.setViewShowState(view, visibility);
    }

    @Override
    public void onSurfaceAvailable(Surface surface) {
        super.onSurfaceAvailable(surface);
        if (GSYVideoType.getRenderType() != GSYVideoType.TEXTURE) {
            if (mThumbImageViewLayout != null && mThumbImageViewLayout.getVisibility() == VISIBLE) {
                mThumbImageViewLayout.setVisibility(INVISIBLE);
            }
        }
    }

    /******************* 下方重载方法，在播放开始不显示底部进度和按键，不需要可屏蔽 ********************/

    protected boolean byStartedClick;

    @Override
    protected void onClickUiToggle() {
        if (mIfCurrentIsFullscreen && mLockCurScreen && mNeedLockFull) {
            setViewShowState(mLockScreen, VISIBLE);
            return;
        }
        byStartedClick = true;
        super.onClickUiToggle();

    }

    @Override
    protected void changeUiToNormal() {
        super.changeUiToNormal();
        byStartedClick = false;
    }

    @Override
    protected void changeUiToPreparingShow() {
        super.changeUiToPreparingShow();
        Debuger.printfLog("Sample changeUiToPreparingShow");
        setViewShowState(mBottomContainer, INVISIBLE);
        setViewShowState(mStartButton, INVISIBLE);
    }

    @Override
    protected void changeUiToPlayingBufferingShow() {
        super.changeUiToPlayingBufferingShow();
        Debuger.printfLog("Sample changeUiToPlayingBufferingShow");
        if (!byStartedClick) {
            setViewShowState(mBottomContainer, INVISIBLE);
            setViewShowState(mStartButton, INVISIBLE);
        }
    }

    @Override
    protected void changeUiToPlayingShow() {
        super.changeUiToPlayingShow();
        Debuger.printfLog("Sample changeUiToPlayingShow");
        if (!byStartedClick) {
            setViewShowState(mBottomContainer, INVISIBLE);
            setViewShowState(mStartButton, INVISIBLE);
        }
    }

    @Override
    public void startAfterPrepared() {
        super.startAfterPrepared();
        Debuger.printfLog("Sample startAfterPrepared");
        setViewShowState(mBottomContainer, INVISIBLE);
        setViewShowState(mStartButton, INVISIBLE);
        setViewShowState(mBottomProgressBar, VISIBLE);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        byStartedClick = true;
        super.onStartTrackingTouch(seekBar);
    }
}
