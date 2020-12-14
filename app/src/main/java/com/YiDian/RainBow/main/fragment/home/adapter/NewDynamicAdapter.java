package com.YiDian.RainBow.main.fragment.home.adapter;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaMetadataRetriever;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.App;
import com.YiDian.RainBow.custom.image.NineGridTestLayout;
import com.YiDian.RainBow.custom.videoplayer.SampleCoverVideo;
import com.YiDian.RainBow.main.fragment.home.bean.DianzanBean;
import com.YiDian.RainBow.main.fragment.home.bean.NewDynamicBean;
import com.YiDian.RainBow.main.fragment.mine.activity.MyQrCodeActivity;
import com.YiDian.RainBow.utils.NetUtils;
import com.YiDian.RainBow.utils.StringUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.tencent.connect.share.QQShare;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class NewDynamicAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Activity context;
    private final List<NewDynamicBean.ObjectBean.ListBean> list;

    public static final String TAG = "ListNormalAdapter22";
    private PopupWindow mPopupWindow;
    private Tencent mTencent;
    int userid = 1030;

    public NewDynamicAdapter(Activity context, List<NewDynamicBean.ObjectBean.ListBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //纯文本
        if (viewType == 1) {
            View view = View.inflate(context, R.layout.item_dynamic_text, null);
            return new ViewHolder(view);
        }
        //纯图片
        if (viewType == 2) {
            View view = View.inflate(context, R.layout.item_dynamic_img, null);
            return new ViewHolder(view);
        }
        //文本加图片
        if (viewType == 21) {
            View view = View.inflate(context, R.layout.item_dynamic_text_img, null);
            return new ViewHolder(view);
        }
        //纯视频
        if (viewType == 3) {
            View view = View.inflate(context, R.layout.item_dynamic_video, null);
            return new ViewHolder(view);
        }
        //视频加文本
        if (viewType == 31) {
            View view = View.inflate(context, R.layout.item_dynamic_video_text, null);
            return new ViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        //腾讯AppId(替换你自己App Id)、上下文
        mTencent = Tencent.createInstance("101906973", context);


        NewDynamicBean.ObjectBean.ListBean listBean = list.get(position);
        int id = listBean.getId();

        NewDynamicBean.ObjectBean.ListBean.UserInfoBean userInfo = list.get(position).getUserInfo();
        //设置用户名
        ((ViewHolder) holder).tvUsername.setText(userInfo.getNickName());
        //加载头像
        Glide.with(context).load(userInfo.getHeadImg()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(((ViewHolder) holder).ivHeadimg);
        //设置角色
        ((ViewHolder) holder).tvAge.setText(userInfo.getUserRole());
        //是否认证
        if (userInfo.getAttestation() == 1) {
            ((ViewHolder)holder).isattaction.setVisibility(View.VISIBLE);
        } else {
            ((ViewHolder)holder).isattaction.setVisibility(View.GONE);
        }
        //判断性别是否保密
        String userRole = userInfo.getUserRole();
        if(userRole.equals("保密")){
            ((ViewHolder)holder).tvAge.setVisibility(View.GONE);
        }

        //判断是否点赞
        if(listBean.isIsClick()){
            ((ViewHolder)holder).ivDianzan.setImageResource(R.mipmap.dianzan);
        }else{
            ((ViewHolder)holder).ivDianzan.setImageResource(R.mipmap.weidianzan);
        }

        //点赞的单击事件
        ((ViewHolder)holder).rlDianzan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listBean.isIsClick()){
                    //取消点赞
                    //开始执行设置不可点击 防止多次点击发生冲突
                    ((ViewHolder)holder).rlDianzan.setEnabled(false);
                    NetUtils.getInstance().getApis()
                            .doCancleDianzan(1,id,userid)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<DianzanBean>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(DianzanBean dianzanBean) {
                                    //处理结束后恢复点击
                                    ((ViewHolder)holder).rlDianzan.setEnabled(true);


                                    //取消点赞成功
                                    ((ViewHolder)holder).ivDianzan.setImageResource(R.mipmap.weidianzan);
                                    listBean.setIsClick(false);


                                    //取消点赞成功数量加一
                                    String s = ((ViewHolder) holder).tvDianzanCount.getText().toString();
                                    Integer integer = Integer.valueOf(s);

                                    integer-=1;

                                    ((ViewHolder)holder).tvDianzanCount.setText(integer+"");
                                }
                                @Override
                                public void onError(Throwable e) {

                                }

                                @Override
                                public void onComplete() {

                                }
                            });
                }else{
                    //点赞
                    NetUtils.getInstance().getApis()
                            .doDianzan(userid,1,id)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<DianzanBean>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(DianzanBean dianzanBean) {
                                    //处理结束后恢复点击
                                    ((ViewHolder)holder).rlDianzan.setEnabled(true);

                                    if(dianzanBean.getObject().equals("插入成功")){
                                        //点赞成功
                                        ((ViewHolder)holder).ivDianzan.setImageResource(R.mipmap.dianzan);
                                        listBean.setIsClick(true);

                                        //点赞成功数量加一
                                        String s = ((ViewHolder) holder).tvDianzanCount.getText().toString();
                                        Integer integer = Integer.valueOf(s);

                                        integer+=1;

                                        ((ViewHolder)holder).tvDianzanCount.setText(integer+"");
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
            }
        });

        //设置点赞数
        ((ViewHolder)holder).tvDianzanCount.setText(listBean.getClickNum()+"");
        //设置评论数
        ((ViewHolder)holder).tvPinglunCount.setText(listBean.getCommentCount()+"");

        //判断是否关注
        if(listBean.isIsAttention()){
            ((ViewHolder)holder).tvGuanzhu.setBackground(context.getResources().getDrawable(R.drawable.newdynamic_yiguanzhu));
            ((ViewHolder)holder).tvGuanzhu.setText("已关注");
        }else{
            ((ViewHolder)holder).tvGuanzhu.setBackground(context.getResources().getDrawable(R.drawable.newdynamic_weiguanzhu));
            ((ViewHolder)holder).tvGuanzhu.setText("未关注");
        }

        ((ViewHolder)holder).tvGuanzhu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listBean.isIsAttention()){
                    //取消关注


                }else{
                    //关注


                }
            }
        });

        //转发点击事件
        ((ViewHolder)holder).rlZhuanfa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelect();
            }
        });

        //获取发布时位置距离当前的距离
        String distance = listBean.getDistance();
        if(distance!=null){
            ((ViewHolder)holder).tvDistance.setVisibility(View.VISIBLE);
            String round = StringUtil.round(distance);
            ((ViewHolder)holder).tvDistance.setText(round+"km");
        }else{
            ((ViewHolder)holder).tvDistance.setVisibility(View.GONE);
        }

        //获取发布时间
        String createTime = listBean.getCreateTime();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd HH:mm:ss", Locale.SIMPLIFIED_CHINESE);
        try {
            Date parse = sdf.parse(createTime);

            long time = parse.getTime();

            //获取当前时间
            long l = System.currentTimeMillis();
            //获取发布过的时长
            long difference = l - time;
            Log.d("xxx",difference+"");
            if(difference>1800000){
                ((ViewHolder)holder).tvTime.setText(createTime);
            }
            if(difference>1200000 && difference<1800000){
                ((ViewHolder)holder).tvTime.setText("半小时前发布");
            }
            if(difference>600000 && difference<1200000){
                ((ViewHolder)holder).tvTime.setText("20分钟前发布");
            }
            if(difference>300000 && difference<600000){
                ((ViewHolder)holder).tvTime.setText("10分钟前发布");
            }
            if(difference>240000 && difference<300000){
                ((ViewHolder)holder).tvTime.setText("5分钟前发布");
            }
            if(difference>180000 && difference<240000){
                ((ViewHolder)holder).tvTime.setText("4分钟前发布");
            }
            if(difference>120000 && difference<180000){
                ((ViewHolder)holder).tvTime.setText("3分钟前发布");
            }
            if(difference>60000 && difference<120000){
                ((ViewHolder)holder).tvTime.setText("2分钟前发布");
            }
            if(difference<60000){
                ((ViewHolder)holder).tvTime.setText("1分钟前发布");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int imgType = list.get(position).getImgType();
        //纯文本
        if (imgType == 1) {
            ((ViewHolder) holder).tvDynamicText.setText(list.get(position).getContentInfo());
        }
        //纯图片
        if (imgType == 2) {
            String contentImg = list.get(position).getContentImg();
            String[] split = contentImg.split(",");

            Log.d("xxx",split.length+"");
            List<String> imglist = new ArrayList<>();

            for (int i = 0; i < split.length; i++) {
                imglist.add(split[i].trim());
            }

            ((ViewHolder) holder).layout.setIsShowAll(false); //当传入的图片数超过9张时，是否全部显示
            ((ViewHolder) holder).layout.setSpacing(5); //动态设置图片之间的间隔
            ((ViewHolder) holder).layout.setUrlList(imglist); //最后再设置图片url
        }
        //文本加图片
        if (imgType == 21) {
            //设置文本
            ((ViewHolder) holder).tvDynamicText.setText(list.get(position).getContentInfo());
            //设置图片
            String contentImg = list.get(position).getContentImg();
            String[] split = contentImg.split(",");
            List<String> img = new ArrayList<>();
            for (int i = 0; i < split.length; i++) {
                img.add(split[i].trim());
            }
            ((ViewHolder) holder).layout.setIsShowAll(false); //当传入的图片数超过9张时，是否全部显示
            ((ViewHolder) holder).layout.setSpacing(5); //动态设置图片之间的间隔
            ((ViewHolder) holder).layout.setUrlList(img); //最后再设置图片url
        }
        //纯视频
        if (imgType == 3) {
            //设置播放视频
            String contentImg = list.get(position).getContentImg();

            Bitmap netVideoBitmap = getNetVideoBitmap(contentImg);
            //设置封面
            ((ViewHolder)holder).videoPlayer.loadCoverImage(contentImg,netVideoBitmap);

            ((ViewHolder) holder).videoPlayer.setUpLazy(contentImg, true, null, null, "");

            //防止错位设置
            ((ViewHolder) holder).videoPlayer.setPlayTag(TAG);
            ((ViewHolder) holder).videoPlayer.setLockLand(true);
            ((ViewHolder) holder).videoPlayer.setPlayPosition(position);
            //是否根据视频尺寸，自动选择竖屏全屏或者横屏全屏，这个标志为和 setLockLand 冲突，需要和 orientationUtils 使用
            ((ViewHolder) holder).videoPlayer.setAutoFullWithSize(false);
            //音频焦点冲突时是否释放
            ((ViewHolder) holder).videoPlayer.setReleaseWhenLossAudio(false);
            //全屏动画
            ((ViewHolder) holder).videoPlayer.setShowFullAnimation(true);
            //小屏时不触摸滑动
            ((ViewHolder) holder).videoPlayer.setIsTouchWiget(false);

        }
        //视频加文本
        if (imgType == 31) {
            //设置文本
            ((ViewHolder) holder).tvDynamicText.setText(list.get(position).getContentInfo());

            //设置播放视频
            String contentImg = list.get(position).getContentImg();

            Bitmap netVideoBitmap = getNetVideoBitmap(contentImg);
            //设置封面
            ((ViewHolder)holder).videoPlayer.loadCoverImage(contentImg,netVideoBitmap);

            //设置播放路径
            ((ViewHolder) holder).videoPlayer.setUpLazy(contentImg, true, null, null, "");

            //防止错位设置
            ((ViewHolder) holder).videoPlayer.setPlayTag(TAG);
            ((ViewHolder) holder).videoPlayer.setLockLand(true);
            ((ViewHolder) holder).videoPlayer.setPlayPosition(position);
            //是否根据视频尺寸，自动选择竖屏全屏或者横屏全屏，这个标志为和 setLockLand 冲突，需要和 orientationUtils 使用
            ((ViewHolder) holder).videoPlayer.setAutoFullWithSize(false);
            //音频焦点冲突时是否释放
            ((ViewHolder) holder).videoPlayer.setReleaseWhenLossAudio(false);
            //全屏动画
            ((ViewHolder) holder).videoPlayer.setShowFullAnimation(true);
            //小屏时不触摸滑动
            ((ViewHolder) holder).videoPlayer.setIsTouchWiget(false);

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        int imgType = list.get(position).getImgType();

        return imgType;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_headimg)
        ImageView ivHeadimg;
        @BindView(R.id.tv_age)
        TextView tvAge;
        @BindView(R.id.tv_username)
        TextView tvUsername;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_guanzhu)
        TextView tvGuanzhu;
        @Nullable
        @BindView(R.id.tv_dynamic_text)
        TextView tvDynamicText;
        @BindView(R.id.ll_huati)
        LinearLayout llHuati;
        @BindView(R.id.iv_dianzan)
        ImageView ivDianzan;
        @BindView(R.id.tv_dianzan_count)
        TextView tvDianzanCount;
        @BindView(R.id.rl_dianzan)
        RelativeLayout rlDianzan;
        @BindView(R.id.iv_pinglun)
        ImageView ivPinglun;
        @BindView(R.id.tv_pinglun_count)
        TextView tvPinglunCount;
        @BindView(R.id.rl_pinglun)
        RelativeLayout rlPinglun;
        @BindView(R.id.rl_zhuanfa)
        RelativeLayout rlZhuanfa;
        @BindView(R.id.tv_distance)
        TextView tvDistance;
        @Nullable
        @BindView(R.id.rc_image)
        NineGridTestLayout layout;
        @Nullable
        @BindView(R.id.video_player)
        SampleCoverVideo videoPlayer;
        @BindView(R.id.isattaction)
        ImageView isattaction;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
    public void showSelect() {
        //添加成功后处理
        mPopupWindow = new PopupWindow();
        mPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_share, null);

        LinearLayout friend = view.findViewById(R.id.ll_share_friend);
        LinearLayout qq = view.findViewById(R.id.ll_share_qq);
        LinearLayout space = view.findViewById(R.id.ll_share_space);
        LinearLayout wechat = view.findViewById(R.id.ll_share_Wechat);
        LinearLayout moments = view.findViewById(R.id.ll_share_wechatmoments);

        friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //分享到好友
            }
        });
        qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //分享到QQ好友
                onClickShare();
            }
        });
        space.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //分享到QQ空间
                onClickShareQzone();
            }
        });
        wechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //分享到微信好友
                onclickShareWechatFriend();
            }
        });
        moments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //分享到朋友圈
                onclickShareWechatmoments();
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
    public static Bitmap getNetVideoBitmap(String videoUrl) {
        Bitmap bitmap = null;

        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            //根据url获取缩略图
            retriever.setDataSource(videoUrl, new HashMap());
            //获得第一帧图片
            bitmap = retriever.getFrameAtTime();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } finally {
            retriever.release();
        }
        return bitmap;
    }
    //分享到QQ信息
    private void onClickShare() {
        /*Bundle params = new Bundle();

        params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, file.toString());
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE);
        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE);
        mTencent.shareToQQ(context, params, new IUiListener() {
            @Override
            public void onComplete(Object o) {
                Log.e(TAG, "分享成功: " + o.toString());
            }

            @Override
            public void onError(UiError uiError) {
                Log.e(TAG, "分享失败: " + uiError.toString());
            }

            @Override
            public void onCancel() {
                Log.e(TAG, "取消分享");

            }
        });*/
    }

    //分享到QQ空间
    private void onClickShareQzone() {
    /*    Bundle params = new Bundle();

        params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, file.toString());
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE);
        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
        mTencent.shareToQQ(context, params, new IUiListener() {
            @Override
            public void onComplete(Object o) {
            }

            @Override
            public void onError(UiError uiError) {

            }

            @Override
            public void onCancel() {

            }
        });*/
    }

    //分享到微信好友
    private void onclickShareWechatFriend() {
/*        //初始化 WXImageObject 和 WXMediaMessage 对象
        WXImageObject imgObj = new WXImageObject(bitmap1);
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObj;

        //构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("img");
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneSession;
        //调用api接口，发送数据到微信
        App.getWXApi().sendReq(req);*/

    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    //分享到微信朋友圈
    private void onclickShareWechatmoments() {
        /*//初始化 WXImageObject 和 WXMediaMessage 对象
        WXImageObject imgObj = new WXImageObject(bitmap1);
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObj;

        //构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("img");
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneTimeline;
        //调用api接口，发送数据到微信
        App.getWXApi().sendReq(req);*/
    }

    //设置透明度
    public void setWindowAlpa(boolean isopen) {
        if (android.os.Build.VERSION.SDK_INT < 11) {
            return;
        }
        final Window window = context.getWindow();
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
            mPopupWindow.showAtLocation(v, Gravity.CENTER_HORIZONTAL, 0, 0);
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
