package com.YiDian.RainBow.main.fragment.mine.adapter.mydynamicviewholder;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.App;
import com.YiDian.RainBow.base.Common;
import com.YiDian.RainBow.custom.customDialog.CustomDialogCleanNotice;
import com.YiDian.RainBow.custom.videoplayer.SampleCoverVideo;
import com.YiDian.RainBow.main.fragment.home.activity.DynamicDetailsActivity;
import com.YiDian.RainBow.main.fragment.home.bean.CollectDynamicBean;
import com.YiDian.RainBow.main.fragment.home.bean.DianzanBean;
import com.YiDian.RainBow.main.fragment.home.bean.NewDynamicBean;
import com.YiDian.RainBow.topic.SaveIntentMsgBean;
import com.YiDian.RainBow.topic.TopicDetailsActivity;
import com.YiDian.RainBow.user.PersonHomeActivity;
import com.YiDian.RainBow.utils.NetUtils;
import com.YiDian.RainBow.utils.StringUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.tencent.connect.share.QQShare;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXVideoObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.greenrobot.eventbus.EventBus;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.YiDian.RainBow.base.Common.decodeUriAsBitmapFromNet;

public class MyDynamicAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Activity context;
    private final List<NewDynamicBean.ObjectBean.ListBean> list;



    private Tencent mTencent;

    public static final String TAG = "ListNormalAdapter22";
    String wechatUrl = "http://web.p.qq.com/qqmpmobile/aio/app.html?id=101906973";

    private PopupWindow mPopupWindow;
    int userid;
    private NewDynamicBean.ObjectBean.ListBean listBean;
    private NewDynamicBean.ObjectBean.ListBean.UserInfoBean userInfo;
    private int id;
    private MyDynamicViewHolderText myDynamicViewHolderText;
    private MyDynamicViewHolderImg myDynamicViewHolderImg;
    private MyDynamicViewHolderTextAndImg myDynamicViewHolderTextAndImg;
    private MyDynamicViewHolderVideo myDynamicViewHolderVideo;
    private MyDynamicViewHolderVideoAndText myDynamicViewHolderVideoAndText;

    public MyDynamicAdapter(Activity context, List<NewDynamicBean.ObjectBean.ListBean> list, Tencent mTencent) {
        this.context = context;
        this.list = list;
        this.mTencent = mTencent;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //纯文本
        if (viewType == 1) {
            myDynamicViewHolderText = MyDynamicViewHolderText.createViewHolder(context, parent, R.layout.item_mydynamic_text);
            return myDynamicViewHolderText;
        }
        //纯图片
        if (viewType == 2) {
            myDynamicViewHolderImg = MyDynamicViewHolderImg.createViewHolder(context, parent, R.layout.item_mydynamic_img);
            return myDynamicViewHolderImg;
        }
        //文本加图片
        if (viewType == 21) {
            myDynamicViewHolderTextAndImg = MyDynamicViewHolderTextAndImg.createViewHolder(context, parent, R.layout.item_mydynamic_text_img);
            return myDynamicViewHolderTextAndImg;
        }
        //纯视频
        if (viewType == 3) {
            myDynamicViewHolderVideo = MyDynamicViewHolderVideo.createViewHolder(context, parent, R.layout.item_mydynamic_video);
            return myDynamicViewHolderVideo;
        }
        //视频加文本
        if (viewType == 31) {
            myDynamicViewHolderVideoAndText = MyDynamicViewHolderVideoAndText.createViewHolder(context, parent, R.layout.item_mydynamic_video_text);
            return myDynamicViewHolderVideoAndText;
        }
        return null;
    }

    @SuppressLint("ResourceAsColor")
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        userid = Integer.valueOf(Common.getUserId());


        if (holder instanceof MyDynamicViewHolderText) {
            myDynamicViewHolderText = (MyDynamicViewHolderText) holder;
            setDate1(myDynamicViewHolderText, position);
            //获取文本内容
            String contentInfo = listBean.getContentInfo();

            //设置@ ## 颜色及点击
            if (!contentInfo.equals("") && contentInfo.contains("@") || contentInfo.contains("#")) {
                getWeiBoContent(context, contentInfo, myDynamicViewHolderText.tvDynamicText);
            } else {
                myDynamicViewHolderText.tvDynamicText.setText(contentInfo);
            }
            List<NewDynamicBean.ObjectBean.ListBean.TopicsBean> topics = listBean.getTopics();

            if (topics!=null &&topics.size()>0){
                myDynamicViewHolderText.tvTopic.setText("#"+topics.get(0).getTopicTitle()+"");
            }else{
                myDynamicViewHolderText.llHuati.setVisibility(View.GONE);
            }
            myDynamicViewHolderText.llHuati.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, TopicDetailsActivity.class);
                    SaveIntentMsgBean saveIntentMsgBean = new SaveIntentMsgBean();
                    saveIntentMsgBean.setMsg(topics.get(0).getTopicTitle());
                    //2标记传入话题名  1标记传入id
                    saveIntentMsgBean.setFlag(2);
                    intent.putExtra("msg", saveIntentMsgBean);
                    context.startActivity(intent);
                }
            });

        } else if (holder instanceof MyDynamicViewHolderImg) {
            myDynamicViewHolderImg = (MyDynamicViewHolderImg) holder;

            setDate2(myDynamicViewHolderImg, position);

            String contentImg = list.get(position).getContentImg();
            String[] split = contentImg.split(",");

            List<String> imglist = new ArrayList<>();

            for (int i = 0; i < split.length; i++) {
                imglist.add(split[i].trim() + "?imageView2/0/format/jpg/w/400");
            }

            myDynamicViewHolderImg.layout.setIsShowAll(false); //当传入的图片数超过9张时，是否全部显示
            myDynamicViewHolderImg.layout.setSpacing(5); //动态设置图片之间的间隔
            myDynamicViewHolderImg.layout.setAnimation(null);
            myDynamicViewHolderImg.layout.setBackground(null);
            myDynamicViewHolderImg.layout.setUrlList(imglist); //最后再设置图片url

            List<NewDynamicBean.ObjectBean.ListBean.TopicsBean> topics = listBean.getTopics();

            if (topics!=null &&topics.size()>0){
                myDynamicViewHolderImg.tvTopic.setText("#"+topics.get(0).getTopicTitle()+"");
            }else{
                myDynamicViewHolderImg.llHuati.setVisibility(View.GONE);
            }
            myDynamicViewHolderImg.llHuati.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, TopicDetailsActivity.class);
                    SaveIntentMsgBean saveIntentMsgBean = new SaveIntentMsgBean();
                    saveIntentMsgBean.setMsg(topics.get(0).getTopicTitle());
                    //2标记传入话题名  1标记传入id
                    saveIntentMsgBean.setFlag(2);
                    intent.putExtra("msg", saveIntentMsgBean);
                    context.startActivity(intent);
                }
            });

        } else if (holder instanceof MyDynamicViewHolderTextAndImg) {
            myDynamicViewHolderTextAndImg = (MyDynamicViewHolderTextAndImg) holder;
            setDate3(myDynamicViewHolderTextAndImg, position);

            //获取文本内容
            String contentInfo = listBean.getContentInfo();

            //设置@ ## 颜色及点击
            if (!contentInfo.equals("") && contentInfo.contains("@") || contentInfo.contains("#")) {
                getWeiBoContent(context, contentInfo, myDynamicViewHolderTextAndImg.tvDynamicText);
            } else {
                myDynamicViewHolderTextAndImg.tvDynamicText.setText(contentInfo);
            }

            List<NewDynamicBean.ObjectBean.ListBean.TopicsBean> topics = listBean.getTopics();

            if (topics!=null &&topics.size()>0){
                myDynamicViewHolderTextAndImg.tvTopic.setText("#"+topics.get(0).getTopicTitle()+"");
            }else{
                myDynamicViewHolderTextAndImg.llHuati.setVisibility(View.GONE);
            }
            myDynamicViewHolderTextAndImg.llHuati.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, TopicDetailsActivity.class);
                    SaveIntentMsgBean saveIntentMsgBean = new SaveIntentMsgBean();
                    saveIntentMsgBean.setMsg(topics.get(0).getTopicTitle());
                    //2标记传入话题名  1标记传入id
                    saveIntentMsgBean.setFlag(2);
                    intent.putExtra("msg", saveIntentMsgBean);
                    context.startActivity(intent);
                }
            });


            //设置图片
            String contentImg = list.get(position).getContentImg();
            String[] split = contentImg.split(",");
            List<String> img = new ArrayList<>();
            for (int i = 0; i < split.length; i++) {
                img.add(split[i].trim() + "?imageView2/0/format/jpg/w/400");
            }
            myDynamicViewHolderTextAndImg.layout.setIsShowAll(false); //当传入的图片数超过9张时，是否全部显示
            myDynamicViewHolderTextAndImg.layout.setSpacing(5); //动态设置图片之间的间隔
            myDynamicViewHolderTextAndImg.layout.setAnimation(null);
            myDynamicViewHolderTextAndImg.layout.setBackground(null);
            myDynamicViewHolderTextAndImg.layout.setUrlList(img); //最后再设置图片url

        } else if (holder instanceof MyDynamicViewHolderVideo) {
            myDynamicViewHolderVideo = (MyDynamicViewHolderVideo) holder;
            setDate4(myDynamicViewHolderVideo, position);


            List<NewDynamicBean.ObjectBean.ListBean.TopicsBean> topics = listBean.getTopics();

            if (topics!=null &&topics.size()>0){
                myDynamicViewHolderVideo.tvTopic.setText("#"+topics.get(0).getTopicTitle()+"");
            }else{
                myDynamicViewHolderVideo.llHuati.setVisibility(View.GONE);
            }
            myDynamicViewHolderVideo.llHuati.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, TopicDetailsActivity.class);
                    SaveIntentMsgBean saveIntentMsgBean = new SaveIntentMsgBean();
                    saveIntentMsgBean.setMsg(topics.get(0).getTopicTitle());
                    //2标记传入话题名  1标记传入id
                    saveIntentMsgBean.setFlag(2);
                    intent.putExtra("msg", saveIntentMsgBean);
                    context.startActivity(intent);
                }
            });


            //设置播放视频
            String contentImg = list.get(position).getContentImg();

            Bitmap netVideoBitmap = decodeUriAsBitmapFromNet(contentImg + "?vframe/jpg/offset/1/w/480/h/360");

            //设置封面
            myDynamicViewHolderVideo.videoPlayer.loadCoverImage(contentImg, netVideoBitmap);

            myDynamicViewHolderVideo.videoPlayer.setUpLazy(contentImg, true, null, null, "");

            //防止错位设置
            myDynamicViewHolderVideo.videoPlayer.setPlayTag(TAG);
            myDynamicViewHolderVideo.videoPlayer.setLockLand(true);
            myDynamicViewHolderVideo.videoPlayer.setPlayPosition(position);
            //是否根据视频尺寸，自动选择竖屏全屏或者横屏全屏，这个标志为和 setLockLand 冲突，需要和 orientationUtils 使用
            myDynamicViewHolderVideo.videoPlayer.setAutoFullWithSize(false);
            //音频焦点冲突时是否释放
            myDynamicViewHolderVideo.videoPlayer.setReleaseWhenLossAudio(false);
            //全屏动画
            myDynamicViewHolderVideo.videoPlayer.setShowFullAnimation(true);
            //小屏时不触摸滑动
            myDynamicViewHolderVideo.videoPlayer.setIsTouchWiget(false);

        } else if (holder instanceof MyDynamicViewHolderVideoAndText) {
            myDynamicViewHolderVideoAndText = (MyDynamicViewHolderVideoAndText) holder;
            setDate5(myDynamicViewHolderVideoAndText, position);
            //设置文本
            //获取文本内容
            String contentInfo = listBean.getContentInfo();

            //设置@ ## 颜色及点击
            if (!contentInfo.equals("") && contentInfo.contains("@") || contentInfo.contains("#")) {
                getWeiBoContent(context, contentInfo, myDynamicViewHolderVideoAndText.tvDynamicText);
            } else {
                myDynamicViewHolderVideoAndText.tvDynamicText.setText(contentInfo);
            }

            List<NewDynamicBean.ObjectBean.ListBean.TopicsBean> topics = listBean.getTopics();

            if (topics!=null &&topics.size()>0){
                myDynamicViewHolderVideoAndText.tvTopic.setText("#"+topics.get(0).getTopicTitle()+"");
            }else{
                myDynamicViewHolderVideoAndText.llHuati.setVisibility(View.GONE);
            }
            myDynamicViewHolderVideoAndText.llHuati.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, TopicDetailsActivity.class);
                    SaveIntentMsgBean saveIntentMsgBean = new SaveIntentMsgBean();
                    saveIntentMsgBean.setMsg(topics.get(0).getTopicTitle());
                    //2标记传入话题名  1标记传入id
                    saveIntentMsgBean.setFlag(2);
                    intent.putExtra("msg", saveIntentMsgBean);
                    context.startActivity(intent);
                }
            });


            //设置播放视频
            String contentImg = list.get(position).getContentImg();

            Bitmap netVideoBitmap = decodeUriAsBitmapFromNet(contentImg + "?vframe/jpg/offset/1/w/480/h/360");

            //设置封面
            myDynamicViewHolderVideoAndText.videoPlayer.loadCoverImage(contentImg, netVideoBitmap);

            //设置播放路径
            myDynamicViewHolderVideoAndText.videoPlayer.setUpLazy(contentImg, true, null, null, "");

            //防止错位设置
            myDynamicViewHolderVideoAndText.videoPlayer.setPlayTag(TAG);
            myDynamicViewHolderVideoAndText.videoPlayer.setLockLand(true);
            myDynamicViewHolderVideoAndText.videoPlayer.setPlayPosition(position);
            //是否根据视频尺寸，自动选择竖屏全屏或者横屏全屏，这个标志为和 setLockLand 冲突，需要和 orientationUtils 使用
            myDynamicViewHolderVideoAndText.videoPlayer.setAutoFullWithSize(false);
            //音频焦点冲突时是否释放
            myDynamicViewHolderVideoAndText.videoPlayer.setReleaseWhenLossAudio(false);
            //全屏动画
            myDynamicViewHolderVideoAndText.videoPlayer.setShowFullAnimation(true);
            //小屏时不触摸滑动
            myDynamicViewHolderVideoAndText.videoPlayer.setIsTouchWiget(false);

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        int imgType = list.get(position).getImgType();

        return imgType;
    }


    public void showSelect() {
        //添加成功后处理
        mPopupWindow = new PopupWindow();
        mPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_share, null);

        LinearLayout qq = view.findViewById(R.id.ll_share_qq);
        LinearLayout space = view.findViewById(R.id.ll_share_space);
        LinearLayout wechat = view.findViewById(R.id.ll_share_Wechat);
        LinearLayout moments = view.findViewById(R.id.ll_share_wechatmoments);

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

    //分享到QQ信息
    private void onClickShare() {
        int imgType = listBean.getImgType();
        Bundle params = new Bundle();

        if (imgType == 1) {
            params.putString(QQShare.SHARE_TO_QQ_SUMMARY, listBean.getContentInfo());
            params.putString(QQShare.SHARE_TO_QQ_TITLE, userInfo.getNickName() + "的动态");
            params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "http://web.p.qq.com/qqmpmobile/aio/app.html?id=101906973");
            params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
            params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE);
        }
        if (imgType == 21) {
            String[] split = listBean.getContentImg().split(",");

            params.putString(QQShare.SHARE_TO_QQ_SUMMARY, listBean.getContentInfo());
            params.putString(QQShare.SHARE_TO_QQ_TITLE, userInfo.getNickName() + "的动态");
            params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "http://web.p.qq.com/qqmpmobile/aio/app.html?id=101906973");
            params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, split[0]);
            params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
            params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE);
        }
        if (imgType == 2) {
            String[] split = listBean.getContentImg().split(",");
            params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, split[0]);
            params.putString(QQShare.SHARE_TO_QQ_TITLE, userInfo.getNickName() + "的动态");
            params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "http://web.p.qq.com/qqmpmobile/aio/app.html?id=101906973");
            params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE);
            params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE);
        }
        if (imgType == 3) {
            params.putString(QQShare.SHARE_TO_QQ_AUDIO_URL, listBean.getContentImg());
            params.putString(QQShare.SHARE_TO_QQ_TITLE, userInfo.getNickName() + "的动态");
            params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "http://web.p.qq.com/qqmpmobile/aio/app.html?id=101906973");
            params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_AUDIO);
            params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE);
        }
        if (imgType == 31) {
            params.putString(QQShare.SHARE_TO_QQ_SUMMARY, listBean.getContentInfo());
            params.putString(QQShare.SHARE_TO_QQ_TITLE, userInfo.getNickName() + "的动态");
            params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "http://web.p.qq.com/qqmpmobile/aio/app.html?id=101906973");
            params.putString(QQShare.SHARE_TO_QQ_AUDIO_URL, listBean.getContentImg());
            params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
            params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE);
        }
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
        });

    }

    //分享到QQ空间
    private void onClickShareQzone() {
        int imgType = listBean.getImgType();
        Bundle params = new Bundle();

        if (imgType == 1) {
            params.putString(QQShare.SHARE_TO_QQ_SUMMARY, listBean.getContentInfo());
            params.putString(QQShare.SHARE_TO_QQ_TITLE, userInfo.getNickName() + "的动态");
            params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "http://web.p.qq.com/qqmpmobile/aio/app.html?id=101906973");
            params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
            params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
        }
        if (imgType == 21) {
            String[] split = listBean.getContentImg().split(",");
            params.putString(QQShare.SHARE_TO_QQ_SUMMARY, listBean.getContentInfo());
            params.putString(QQShare.SHARE_TO_QQ_TITLE, userInfo.getNickName() + "的动态");
            params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "http://web.p.qq.com/qqmpmobile/aio/app.html?id=101906973");
            params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, split[0]);
            params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
            params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
        }
        if (imgType == 2) {
            String[] split = listBean.getContentImg().split(",");
            params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, split[0]);
            params.putString(QQShare.SHARE_TO_QQ_TITLE, userInfo.getNickName() + "的动态");
            params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "http://web.p.qq.com/qqmpmobile/aio/app.html?id=101906973");
            params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE);
            params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);

        }
        if (imgType == 3) {
            params.putString(QQShare.SHARE_TO_QQ_AUDIO_URL, listBean.getContentImg());
            params.putString(QQShare.SHARE_TO_QQ_TITLE, userInfo.getNickName() + "的动态");
            params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "http://web.p.qq.com/qqmpmobile/aio/app.html?id=101906973");
            params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_AUDIO);
            params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);

        }
        if (imgType == 31) {
            params.putString(QQShare.SHARE_TO_QQ_SUMMARY, listBean.getContentInfo());
            params.putString(QQShare.SHARE_TO_QQ_TITLE, userInfo.getNickName() + "的动态");
            params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "http://web.p.qq.com/qqmpmobile/aio/app.html?id=101906973");
            params.putString(QQShare.SHARE_TO_QQ_AUDIO_URL, listBean.getContentImg());
            params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
            params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);

        }
        mTencent.shareToQQ(context, params, new IUiListener() {
            @Override
            public void onComplete(Object o) {
                Log.e(TAG, "分享成功: " + o.toString());
            }

            @Override
            public void onError(UiError uiError) {

            }

            @Override
            public void onCancel() {

            }
        });
    }

    //分享到微信好友
    private void onclickShareWechatFriend() {
        int imgType = listBean.getImgType();
        if (!App.getWXApi().isWXAppInstalled()) {
            Toast.makeText(context, "您未安装微信", Toast.LENGTH_SHORT).show();
            return;
        } else {
            if (imgType == 1) {
                // TODO: 2020/12/18 0018 要跳转的链接

                //初始化 WXImageObject 和 WXMediaMessage 对象
                WXWebpageObject localWXWebpageObject = new WXWebpageObject();
                localWXWebpageObject.webpageUrl = wechatUrl;

                WXMediaMessage msg = new WXMediaMessage(localWXWebpageObject);
                msg.description = listBean.getContentInfo();

                msg.title = userInfo.getNickName() + "的动态";
                //构造一个Req
                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.transaction = String.valueOf(System.currentTimeMillis());
                req.message = msg;
                req.scene = SendMessageToWX.Req.WXSceneSession;
                //调用api接口，发送数据到微信
                App.getWXApi().sendReq(req);
            }
            if (imgType == 2) {
                String[] split = listBean.getContentImg().split(",");

                Glide.with(context).asBitmap().load(split[0])
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                WXWebpageObject localWXWebpageObject = new WXWebpageObject();
                                localWXWebpageObject.webpageUrl = wechatUrl;
                                WXMediaMessage localWXMediaMessage = new WXMediaMessage(
                                        localWXWebpageObject);
                                localWXMediaMessage.title = userInfo.getNickName() + "的动态";//不能太长，否则微信会提示出错。不过博主没验证过具体能输入多长。
                                localWXMediaMessage.description = "";
                                localWXMediaMessage.thumbData = getBitmapBytes(resource, false);

                                //构造一个Req
                                SendMessageToWX.Req req = new SendMessageToWX.Req();
                                req.transaction = String.valueOf(System.currentTimeMillis());
                                req.message = localWXMediaMessage;
                                req.scene = SendMessageToWX.Req.WXSceneSession;
                                //调用api接口，发送数据到微信
                                App.getWXApi().sendReq(req);
                            }
                        });
            }
            if (imgType == 21) {
                String[] split = listBean.getContentImg().split(",");

                Glide.with(context).asBitmap().load(split[0])
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                WXWebpageObject localWXWebpageObject = new WXWebpageObject();
                                localWXWebpageObject.webpageUrl = wechatUrl;
                                WXMediaMessage localWXMediaMessage = new WXMediaMessage(
                                        localWXWebpageObject);
                                localWXMediaMessage.title = userInfo.getNickName() + "的动态";//不能太长，否则微信会提示出错。不过博主没验证过具体能输入多长。
                                localWXMediaMessage.description = listBean.getContentInfo();
                                localWXMediaMessage.thumbData = getBitmapBytes(resource, false);

                                //构造一个Req
                                SendMessageToWX.Req req = new SendMessageToWX.Req();
                                req.transaction = String.valueOf(System.currentTimeMillis());
                                req.message = localWXMediaMessage;
                                req.scene = SendMessageToWX.Req.WXSceneSession;
                                //调用api接口，发送数据到微信
                                App.getWXApi().sendReq(req);
                            }
                        });
            }
            if (imgType == 3) {
                //初始化一个WXVideoObject，填写url
                WXVideoObject video = new WXVideoObject();
                video.videoUrl = listBean.getContentImg();

                //用 WXVideoObject 对象初始化一个 WXMediaMessage 对象
                WXMediaMessage msg = new WXMediaMessage(video);
                msg.title = userInfo.getNickName() + "的动态";

                Bitmap netVideoBitmap = decodeUriAsBitmapFromNet(listBean.getContentImg() + "?vframe/jpg/offset/1/w/480/h/360");

                //设置封面
                msg.thumbData = getBitmapBytes(netVideoBitmap, false);

                //构造一个Req
                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.transaction = String.valueOf(System.currentTimeMillis());
                req.message = msg;
                req.scene = SendMessageToWX.Req.WXSceneSession;

                //调用api接口，发送数据到微信
                App.getWXApi().sendReq(req);
            }
            if (imgType == 31) {
                //初始化一个WXVideoObject，填写url
                WXVideoObject video = new WXVideoObject();
                video.videoUrl = listBean.getContentImg();

                //用 WXVideoObject 对象初始化一个 WXMediaMessage 对象
                WXMediaMessage msg = new WXMediaMessage(video);
                msg.title = userInfo.getNickName() + "的动态";
                msg.description = listBean.getContentInfo();
                Bitmap netVideoBitmap = decodeUriAsBitmapFromNet(listBean.getContentImg() + "?vframe/jpg/offset/1/w/480/h/360");

                //设置封面
                msg.thumbData = getBitmapBytes(netVideoBitmap, false);

                //构造一个Req
                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.transaction = String.valueOf(System.currentTimeMillis());
                req.message = msg;
                req.scene = SendMessageToWX.Req.WXSceneSession;

                //调用api接口，发送数据到微信
                App.getWXApi().sendReq(req);

            }
        }


    }

    //分享到微信朋友圈
    private void onclickShareWechatmoments() {
        int imgType = listBean.getImgType();
        if (!App.getWXApi().isWXAppInstalled()) {
            Toast.makeText(context, "您未安装微信", Toast.LENGTH_SHORT).show();
            return;
        } else {
            if (imgType == 1) {
                // TODO: 2020/12/18 0018 要跳转的链接

                //初始化 WXImageObject 和 WXMediaMessage 对象
                WXWebpageObject localWXWebpageObject = new WXWebpageObject();
                localWXWebpageObject.webpageUrl = wechatUrl;

                WXMediaMessage msg = new WXMediaMessage(localWXWebpageObject);
                msg.description = listBean.getContentInfo();

                msg.title = userInfo.getNickName() + "的动态";
                //构造一个Req
                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.transaction = String.valueOf(System.currentTimeMillis());
                req.message = msg;
                req.scene = SendMessageToWX.Req.WXSceneTimeline;
                //调用api接口，发送数据到微信
                App.getWXApi().sendReq(req);
            }
            if (imgType == 2) {
                String[] split = listBean.getContentImg().split(",");

                Glide.with(context).asBitmap().load(split[0])
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                WXWebpageObject localWXWebpageObject = new WXWebpageObject();
                                localWXWebpageObject.webpageUrl = wechatUrl;
                                WXMediaMessage localWXMediaMessage = new WXMediaMessage(
                                        localWXWebpageObject);
                                localWXMediaMessage.title = userInfo.getNickName() + "的动态";//不能太长，否则微信会提示出错。不过博主没验证过具体能输入多长。
                                localWXMediaMessage.description = "";
                                localWXMediaMessage.thumbData = getBitmapBytes(resource, false);

                                //构造一个Req
                                SendMessageToWX.Req req = new SendMessageToWX.Req();
                                req.transaction = String.valueOf(System.currentTimeMillis());
                                req.message = localWXMediaMessage;
                                req.scene = SendMessageToWX.Req.WXSceneTimeline;
                                //调用api接口，发送数据到微信
                                App.getWXApi().sendReq(req);
                            }
                        });
            }
            if (imgType == 21) {
                String[] split = listBean.getContentImg().split(",");

                Glide.with(context).asBitmap().load(split[0])
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                WXWebpageObject localWXWebpageObject = new WXWebpageObject();
                                localWXWebpageObject.webpageUrl = wechatUrl;
                                WXMediaMessage localWXMediaMessage = new WXMediaMessage(
                                        localWXWebpageObject);
                                localWXMediaMessage.title = userInfo.getNickName() + "的动态";//不能太长，否则微信会提示出错。不过博主没验证过具体能输入多长。
                                localWXMediaMessage.description = listBean.getContentInfo();
                                localWXMediaMessage.thumbData = getBitmapBytes(resource, false);

                                //构造一个Req
                                SendMessageToWX.Req req = new SendMessageToWX.Req();
                                req.transaction = String.valueOf(System.currentTimeMillis());
                                req.message = localWXMediaMessage;
                                req.scene = SendMessageToWX.Req.WXSceneTimeline;
                                //调用api接口，发送数据到微信
                                App.getWXApi().sendReq(req);
                            }
                        });
            }
            if (imgType == 3) {
                //初始化一个WXVideoObject，填写url
                WXVideoObject video = new WXVideoObject();
                video.videoUrl = listBean.getContentImg();

                //用 WXVideoObject 对象初始化一个 WXMediaMessage 对象
                WXMediaMessage msg = new WXMediaMessage(video);
                msg.title = userInfo.getNickName() + "的动态";

                Bitmap netVideoBitmap = decodeUriAsBitmapFromNet(listBean.getContentImg() + "?vframe/jpg/offset/1/w/480/h/360");

                //设置封面
                msg.thumbData = getBitmapBytes(netVideoBitmap, false);

                //构造一个Req
                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.transaction = String.valueOf(System.currentTimeMillis());
                req.message = msg;
                req.scene = SendMessageToWX.Req.WXSceneTimeline;

                //调用api接口，发送数据到微信
                App.getWXApi().sendReq(req);
            }
            if (imgType == 31) {
                //初始化一个WXVideoObject，填写url
                WXVideoObject video = new WXVideoObject();
                video.videoUrl = listBean.getContentImg();

                //用 WXVideoObject 对象初始化一个 WXMediaMessage 对象
                WXMediaMessage msg = new WXMediaMessage(video);
                msg.title = userInfo.getNickName() + "的动态";
                msg.description = listBean.getContentInfo();
                Bitmap netVideoBitmap = decodeUriAsBitmapFromNet(listBean.getContentImg() + "?vframe/jpg/offset/1/w/480/h/360");

                //设置封面
                msg.thumbData = getBitmapBytes(netVideoBitmap, false);

                //构造一个Req
                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.transaction = String.valueOf(System.currentTimeMillis());
                req.message = msg;
                req.scene = SendMessageToWX.Req.WXSceneTimeline;

                //调用api接口，发送数据到微信
                App.getWXApi().sendReq(req);

            }
        }
    }

    //设置透明度
    public void setWindowAlpa(boolean isopen) {
        if (Build.VERSION.SDK_INT < 11) {
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

    @SuppressLint("ResourceAsColor")
    public static Spannable getWeiBoContent(final Context context, String source, TextView tv) {

        SpannableStringBuilder spannable = new SpannableStringBuilder(source);
        // 定义正则表达式
        String AT = "@[\\u4e00-\\u9fa5\\w\\-]+";// @人
        String TOPIC = "#([^\\#|.]+)#";// ##话题
        //设置正则
        Pattern pattern = Pattern.compile("(" + AT + ")|" + "(" + TOPIC + ")");
        Matcher matcher = pattern.matcher(spannable);


        while (matcher.find()) {
            // 根据group的括号索引，可得出具体匹配哪个正则(0代表全部，1代表第一个括号)
            final String at = matcher.group(1);
            final String topic = matcher.group(2);
            // 处理@符号
            if (at != null) {
                //获取匹配位置
                int start = matcher.start(1);
                int end = start + at.length();

                tv.setMovementMethod(LinkMovementMethod.getInstance());
                spannable.setSpan(new MyClickableSpanAt() {
                    @Override
                    public void onClick(View widget) {
                        //这里需要做跳转用户的实现，先用一个Toast代替
                        String substring = at.substring(1);

                        Intent intent = new Intent(context, PersonHomeActivity.class);
                        SaveIntentMsgBean saveIntentMsgBean = new SaveIntentMsgBean();
                        saveIntentMsgBean.setMsg(substring);
                        //2标记传入姓名  1标记传入id
                        saveIntentMsgBean.setFlag(2);
                        intent.putExtra("msg", saveIntentMsgBean);
                        context.startActivity(intent);
                    }
                }, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            }

            // 处理话题##符号
            if (topic != null) {
                int start = matcher.start(2);
                int end = start + topic.length();

                tv.setMovementMethod(LinkMovementMethod.getInstance());
                spannable.setSpan(new MyClickableSpanTopic() {
                    @Override
                    public void onClick(@NonNull View widget) {
                        String substring = topic.substring(1, topic.length() - 1);

                        Intent intent = new Intent(context, TopicDetailsActivity.class);
                        SaveIntentMsgBean saveIntentMsgBean = new SaveIntentMsgBean();
                        saveIntentMsgBean.setMsg(substring);
                        //2标记传入话题名  1标记传入id
                        saveIntentMsgBean.setFlag(2);
                        intent.putExtra("msg", saveIntentMsgBean);
                        context.startActivity(intent);

                    }
                }, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            }
        }
        tv.setText(spannable);

        return spannable;
    }

    public static class MyClickableSpanTopic extends ClickableSpan {
        @Override
        public void onClick(@NonNull View widget) {

        }

        @SuppressLint("ResourceAsColor")
        @Override
        public void updateDrawState(@NonNull TextPaint ds) {
            super.updateDrawState(ds);

            ds.setColor(R.color.start);
            ds.setUnderlineText(false);
        }
    }

    public static class MyClickableSpanAt extends ClickableSpan {
        @Override
        public void onClick(@NonNull View widget) {

        }

        @SuppressLint("ResourceAsColor")
        @Override
        public void updateDrawState(@NonNull TextPaint ds) {
            super.updateDrawState(ds);

            ds.setColor(Color.BLUE);
            ds.setUnderlineText(false);
        }
    }

    // 需要对图片进行处理，否则微信会在log中输出thumbData检查错误
    private static byte[] getBitmapBytes(Bitmap bitmap, boolean paramBoolean) {
        Bitmap localBitmap = Bitmap.createBitmap(80, 80, Bitmap.Config.RGB_565);
        Canvas localCanvas = new Canvas(localBitmap);
        int i;
        int j;
        if (bitmap.getHeight() > bitmap.getWidth()) {
            i = bitmap.getWidth();
            j = bitmap.getWidth();
        } else {
            i = bitmap.getHeight();
            j = bitmap.getHeight();
        }
        while (true) {
            localCanvas.drawBitmap(bitmap, new Rect(0, 0, i, j), new Rect(0, 0, 80
                    , 80), null);
            if (paramBoolean)
                bitmap.recycle();
            ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
            localBitmap.compress(Bitmap.CompressFormat.JPEG, 100,
                    localByteArrayOutputStream);
            localBitmap.recycle();
            byte[] arrayOfByte = localByteArrayOutputStream.toByteArray();
            try {
                localByteArrayOutputStream.close();
                return arrayOfByte;
            } catch (Exception e) {
                Log.d("xxx", e.getMessage());
            }
            i = bitmap.getHeight();
            j = bitmap.getHeight();
        }
    }

    public void setDate1(MyDynamicViewHolderText holder, int position) {
        listBean = list.get(position);

        //跳转到动态详情页
        holder.rlItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listBean = list.get(position);
                id = listBean.getId();

                Intent intent = new Intent(context, DynamicDetailsActivity.class);
                intent.putExtra("id", id);
                context.startActivity(intent);
            }
        });

        //跳转到用户信息页
        holder.ivHeadimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listBean = list.get(position);

                Intent intent = new Intent(context, PersonHomeActivity.class);
                SaveIntentMsgBean saveIntentMsgBean = new SaveIntentMsgBean();
                saveIntentMsgBean.setId(listBean.getUserId());
                //2标记传入姓名  1标记传入id
                saveIntentMsgBean.setFlag(1);
                intent.putExtra("msg", saveIntentMsgBean);
                context.startActivity(intent);
            }
        });

        userInfo = list.get(position).getUserInfo();
        //设置用户名
        holder.tvUsername.setText(userInfo.getNickName());
        //加载头像
        Glide.with(context).load(userInfo.getHeadImg()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(holder.ivHeadimg);


        int attestation = userInfo.getAttestation();

        //认证等级
        if (attestation == 0) {
            holder.isattaction.setVisibility(View.GONE);
        } else if (attestation == 1) {
            holder.isattaction.setImageResource(R.mipmap.qingtong);
        } else if (attestation == 2) {
            holder.isattaction.setImageResource(R.mipmap.baiyin);
        } else if (attestation == 3) {
            holder.isattaction.setImageResource(R.mipmap.huangjin);
        } else if (attestation == 4) {
            holder.isattaction.setImageResource(R.mipmap.bojin);
        } else if (attestation == 5) {
            holder.isattaction.setImageResource(R.mipmap.zuanshi);
        }else{
            holder.isattaction.setImageResource(R.mipmap.guanfang);
        }


        //判断性别是否保密
        String userRole = userInfo.getUserRole();
        if (userRole != null) {
            if (userRole.equals("保密")) {
                holder.tvAge.setVisibility(View.GONE);
            } else if (userRole.equals("")) {
                holder.tvAge.setVisibility(View.GONE);
            } else {
                //设置角色
                holder.tvAge.setText(userRole);
            }
        } else {
            holder.tvAge.setVisibility(View.GONE);
        }


        //判断是否点赞
        if (listBean.isIsClick()) {
            holder.ivDianzan.setImageResource(R.mipmap.dianzan);
        } else {
            holder.ivDianzan.setImageResource(R.mipmap.weidianzan);
        }
        //点赞的单击事件
        holder.rlDianzan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listBean = list.get(position);
                id = listBean.getId();

                if (listBean.isIsClick() == true) {
                    //取消点赞
                    //开始执行设置不可点击 防止多次点击发生冲突
                    holder.rlDianzan.setEnabled(false);
                    NetUtils.getInstance().getApis()
                            .doCancleDianzan(1, id, userid)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<DianzanBean>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(DianzanBean dianzanBean) {
                                    //处理结束后恢复点击
                                    holder.rlDianzan.setEnabled(true);

                                    //取消点赞成功
                                    holder.ivDianzan.setImageResource(R.mipmap.weidianzan);
                                    listBean.setIsClick(false);

                                    //取消点赞成功数量加一
                                    String s = holder.tvDianzanCount.getText().toString();
                                    if (!s.contains("w")) {
                                        Integer integer = Integer.valueOf(s);

                                        integer -= 1;

                                        holder.tvDianzanCount.setText(integer + "");
                                    }
                                }

                                @Override
                                public void onError(Throwable e) {

                                }

                                @Override
                                public void onComplete() {

                                }
                            });
                } else if (listBean.isIsClick() == false) {
                    //点赞
                    holder.rlDianzan.setEnabled(false);

                    NetUtils.getInstance().getApis()
                            .doDianzan(userid, 1, id)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<DianzanBean>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(DianzanBean dianzanBean) {

                                    //处理结束后恢复点击
                                    holder.rlDianzan.setEnabled(true);

                                    //点赞成功
                                    holder.ivDianzan.setImageResource(R.mipmap.dianzan);
                                    listBean.setIsClick(true);

                                    //点赞成功数量加一
                                    String s = holder.tvDianzanCount.getText().toString();

                                    if (!s.contains("w")) {
                                        Integer integer = Integer.valueOf(s);

                                        integer += 1;

                                        holder.tvDianzanCount.setText(integer + "");
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

        //点赞数设置
        int clickNum = listBean.getClickNum();
        if (clickNum < 10000) {
            holder.tvDianzanCount.setText(clickNum + "");
        } else {
            String s = StringUtil.rawIntStr2IntStr(String.valueOf(clickNum));

            holder.tvDianzanCount.setText(s);
        }
        holder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listBean = list.get(position);
                CustomDialogCleanNotice.Builder builder = new CustomDialogCleanNotice.Builder(context);
                builder.setMessage("确定删除该动态吗?").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        NetUtils.getInstance().getApis()
                                .doDeleteDynamic(listBean.getId(), userid)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Observer<CollectDynamicBean>() {
                                    @Override
                                    public void onSubscribe(Disposable d) {

                                    }

                                    @Override
                                    public void onNext(CollectDynamicBean collectDynamicBean) {
                                        if (collectDynamicBean.getObject().equals("删除成功")) {
                                            EventBus.getDefault().post("刷新数据");
                                        }
                                        dialog.dismiss();
                                    }

                                    @Override
                                    public void onError(Throwable e) {

                                    }

                                    @Override
                                    public void onComplete() {

                                    }
                                });
                    }
                });
                builder.setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builder.create().show();
            }
        });


        //设置评论数
        holder.tvPinglunCount.setText(listBean.getCommentCount() + "");

        //转发点击事件
        holder.rlZhuanfa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelect();
            }
        });

        //获取发布时位置距离当前的距离
        String distance = listBean.getDistance();
        if (distance != null) {
            holder.tvDistance.setVisibility(View.VISIBLE);

            double a = Double.valueOf(distance);
            long round = Math.round(a);
            if (round < 1000) {
                holder.tvDistance.setText(round + "m");
            } else {
                holder.tvDistance.setText(round / 1000 + "km");
            }

        } else {
            holder.tvDistance.setVisibility(View.GONE);
        }

        //获取发布时间
        String createTime = listBean.getCreateTime();
        if (createTime != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd HH:mm:ss", Locale.SIMPLIFIED_CHINESE);
            try {
                Date parse = sdf.parse(createTime);

                long time = parse.getTime();
                String newChatTime = StringUtil.getNewChatTime(time);
                holder.tvTime.setText(newChatTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public void setDate2(MyDynamicViewHolderImg holder, int position) {
        listBean = list.get(position);

        //跳转到动态详情页
        holder.rlItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listBean = list.get(position);
                id = listBean.getId();

                Intent intent = new Intent(context, DynamicDetailsActivity.class);
                intent.putExtra("id", id);
                context.startActivity(intent);
            }
        });

        //跳转到用户信息页
        holder.ivHeadimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listBean = list.get(position);

                Intent intent = new Intent(context, PersonHomeActivity.class);
                SaveIntentMsgBean saveIntentMsgBean = new SaveIntentMsgBean();
                saveIntentMsgBean.setId(listBean.getUserId());
                //2标记传入姓名  1标记传入id
                saveIntentMsgBean.setFlag(1);
                intent.putExtra("msg", saveIntentMsgBean);
                context.startActivity(intent);
            }
        });

        userInfo = list.get(position).getUserInfo();
        //设置用户名
        holder.tvUsername.setText(userInfo.getNickName());
        //加载头像
        Glide.with(context).load(userInfo.getHeadImg()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(holder.ivHeadimg);


        int attestation = userInfo.getAttestation();

        //认证等级
        if (attestation == 0) {
            holder.isattaction.setVisibility(View.GONE);
        } else if (attestation == 1) {
            holder.isattaction.setImageResource(R.mipmap.qingtong);
        } else if (attestation == 2) {
            holder.isattaction.setImageResource(R.mipmap.baiyin);
        } else if (attestation == 3) {
            holder.isattaction.setImageResource(R.mipmap.huangjin);
        } else if (attestation == 4) {
            holder.isattaction.setImageResource(R.mipmap.bojin);
        } else if (attestation == 5) {
            holder.isattaction.setImageResource(R.mipmap.zuanshi);
        }else{
            holder.isattaction.setImageResource(R.mipmap.guanfang);
        }


        //判断性别是否保密
        String userRole = userInfo.getUserRole();
        if (userRole != null) {
            if (userRole.equals("保密")) {
                holder.tvAge.setVisibility(View.GONE);
            } else if (userRole.equals("")) {
                holder.tvAge.setVisibility(View.GONE);
            } else {
                //设置角色
                holder.tvAge.setText(userRole);
            }
        } else {
            holder.tvAge.setVisibility(View.GONE);
        }


        //判断是否点赞
        if (listBean.isIsClick()) {
            holder.ivDianzan.setImageResource(R.mipmap.dianzan);
        } else {
            holder.ivDianzan.setImageResource(R.mipmap.weidianzan);
        }
        //点赞的单击事件
        holder.rlDianzan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listBean = list.get(position);
                id = listBean.getId();

                if (listBean.isIsClick() == true) {
                    //取消点赞
                    //开始执行设置不可点击 防止多次点击发生冲突
                    holder.rlDianzan.setEnabled(false);
                    NetUtils.getInstance().getApis()
                            .doCancleDianzan(1, id, userid)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<DianzanBean>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(DianzanBean dianzanBean) {
                                    //处理结束后恢复点击
                                    holder.rlDianzan.setEnabled(true);

                                    //取消点赞成功
                                    holder.ivDianzan.setImageResource(R.mipmap.weidianzan);
                                    listBean.setIsClick(false);

                                    //取消点赞成功数量加一
                                    String s = holder.tvDianzanCount.getText().toString();
                                    if (!s.contains("w")) {
                                        Integer integer = Integer.valueOf(s);

                                        integer -= 1;

                                        holder.tvDianzanCount.setText(integer + "");
                                    }
                                }

                                @Override
                                public void onError(Throwable e) {

                                }

                                @Override
                                public void onComplete() {

                                }
                            });
                } else if (listBean.isIsClick() == false) {
                    //点赞
                    holder.rlDianzan.setEnabled(false);

                    NetUtils.getInstance().getApis()
                            .doDianzan(userid, 1, id)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<DianzanBean>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(DianzanBean dianzanBean) {

                                    //处理结束后恢复点击
                                    holder.rlDianzan.setEnabled(true);

                                    //点赞成功
                                    holder.ivDianzan.setImageResource(R.mipmap.dianzan);
                                    listBean.setIsClick(true);

                                    //点赞成功数量加一
                                    String s = holder.tvDianzanCount.getText().toString();

                                    if (!s.contains("w")) {
                                        Integer integer = Integer.valueOf(s);

                                        integer += 1;

                                        holder.tvDianzanCount.setText(integer + "");
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

        //点赞数设置
        int clickNum = listBean.getClickNum();
        if (clickNum < 10000) {
            holder.tvDianzanCount.setText(clickNum + "");
        } else {
            String s = StringUtil.rawIntStr2IntStr(String.valueOf(clickNum));

            holder.tvDianzanCount.setText(s);
        }
        holder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listBean = list.get(position);
                CustomDialogCleanNotice.Builder builder = new CustomDialogCleanNotice.Builder(context);
                builder.setMessage("确定删除该动态吗?").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        NetUtils.getInstance().getApis()
                                .doDeleteDynamic(listBean.getId(), userid)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Observer<CollectDynamicBean>() {
                                    @Override
                                    public void onSubscribe(Disposable d) {

                                    }

                                    @Override
                                    public void onNext(CollectDynamicBean collectDynamicBean) {
                                        if (collectDynamicBean.getObject().equals("删除成功")) {
                                            EventBus.getDefault().post("刷新数据");
                                        }
                                        dialog.dismiss();
                                    }

                                    @Override
                                    public void onError(Throwable e) {

                                    }

                                    @Override
                                    public void onComplete() {

                                    }
                                });
                    }
                });
                builder.setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builder.create().show();
            }
        });


        //设置评论数
        holder.tvPinglunCount.setText(listBean.getCommentCount() + "");

        //转发点击事件
        holder.rlZhuanfa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelect();
            }
        });

        //获取发布时位置距离当前的距离
        String distance = listBean.getDistance();
        if (distance != null) {
            holder.tvDistance.setVisibility(View.VISIBLE);

            double a = Double.valueOf(distance);
            long round = Math.round(a);
            if (round < 1000) {
                holder.tvDistance.setText(round + "m");
            } else {
                holder.tvDistance.setText(round / 1000 + "km");
            }

        } else {
            holder.tvDistance.setVisibility(View.GONE);
        }

        //获取发布时间
        String createTime = listBean.getCreateTime();
        if (createTime != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd HH:mm:ss", Locale.SIMPLIFIED_CHINESE);
            try {
                Date parse = sdf.parse(createTime);

                long time = parse.getTime();
                String newChatTime = StringUtil.getNewChatTime(time);
                holder.tvTime.setText(newChatTime);

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public void setDate3(MyDynamicViewHolderTextAndImg holder, int position) {
        listBean = list.get(position);

        //跳转到动态详情页
        holder.rlItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listBean = list.get(position);
                id = listBean.getId();

                Intent intent = new Intent(context, DynamicDetailsActivity.class);
                intent.putExtra("id", id);
                context.startActivity(intent);
            }
        });

        //跳转到用户信息页
        holder.ivHeadimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listBean = list.get(position);

                Intent intent = new Intent(context, PersonHomeActivity.class);
                SaveIntentMsgBean saveIntentMsgBean = new SaveIntentMsgBean();
                saveIntentMsgBean.setId(listBean.getUserId());
                //2标记传入姓名  1标记传入id
                saveIntentMsgBean.setFlag(1);
                intent.putExtra("msg", saveIntentMsgBean);
                context.startActivity(intent);
            }
        });

        userInfo = list.get(position).getUserInfo();
        //设置用户名
        holder.tvUsername.setText(userInfo.getNickName());
        //加载头像
        Glide.with(context).load(userInfo.getHeadImg()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(holder.ivHeadimg);


        int attestation = userInfo.getAttestation();

        //认证等级
        if (attestation == 0) {
            holder.isattaction.setVisibility(View.GONE);
        } else if (attestation == 1) {
            holder.isattaction.setImageResource(R.mipmap.qingtong);
        } else if (attestation == 2) {
            holder.isattaction.setImageResource(R.mipmap.baiyin);
        } else if (attestation == 3) {
            holder.isattaction.setImageResource(R.mipmap.huangjin);
        } else if (attestation == 4) {
            holder.isattaction.setImageResource(R.mipmap.bojin);
        } else if (attestation == 5) {
            holder.isattaction.setImageResource(R.mipmap.zuanshi);
        }else{
            holder.isattaction.setImageResource(R.mipmap.guanfang);
        }


        //判断性别是否保密
        String userRole = userInfo.getUserRole();
        if (userRole != null) {
            if (userRole.equals("保密")) {
                holder.tvAge.setVisibility(View.GONE);
            } else if (userRole.equals("")) {
                holder.tvAge.setVisibility(View.GONE);
            } else {
                //设置角色
                holder.tvAge.setText(userRole);
            }
        } else {
            holder.tvAge.setVisibility(View.GONE);
        }


        //判断是否点赞
        if (listBean.isIsClick()) {
            holder.ivDianzan.setImageResource(R.mipmap.dianzan);
        } else {
            holder.ivDianzan.setImageResource(R.mipmap.weidianzan);
        }
        //点赞的单击事件
        holder.rlDianzan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listBean = list.get(position);
                id = listBean.getId();

                if (listBean.isIsClick() == true) {
                    //取消点赞
                    //开始执行设置不可点击 防止多次点击发生冲突
                    holder.rlDianzan.setEnabled(false);
                    NetUtils.getInstance().getApis()
                            .doCancleDianzan(1, id, userid)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<DianzanBean>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(DianzanBean dianzanBean) {
                                    //处理结束后恢复点击
                                    holder.rlDianzan.setEnabled(true);

                                    //取消点赞成功
                                    holder.ivDianzan.setImageResource(R.mipmap.weidianzan);
                                    listBean.setIsClick(false);

                                    //取消点赞成功数量加一
                                    String s = holder.tvDianzanCount.getText().toString();
                                    if (!s.contains("w")) {
                                        Integer integer = Integer.valueOf(s);

                                        integer -= 1;

                                        holder.tvDianzanCount.setText(integer + "");
                                    }
                                }

                                @Override
                                public void onError(Throwable e) {

                                }

                                @Override
                                public void onComplete() {

                                }
                            });
                } else if (listBean.isIsClick() == false) {
                    //点赞
                    holder.rlDianzan.setEnabled(false);

                    NetUtils.getInstance().getApis()
                            .doDianzan(userid, 1, id)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<DianzanBean>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(DianzanBean dianzanBean) {

                                    //处理结束后恢复点击
                                    holder.rlDianzan.setEnabled(true);

                                    //点赞成功
                                    holder.ivDianzan.setImageResource(R.mipmap.dianzan);
                                    listBean.setIsClick(true);

                                    //点赞成功数量加一
                                    String s = holder.tvDianzanCount.getText().toString();

                                    if (!s.contains("w")) {
                                        Integer integer = Integer.valueOf(s);

                                        integer += 1;

                                        holder.tvDianzanCount.setText(integer + "");
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

        //点赞数设置
        int clickNum = listBean.getClickNum();
        if (clickNum < 10000) {
            holder.tvDianzanCount.setText(clickNum + "");
        } else {
            String s = StringUtil.rawIntStr2IntStr(String.valueOf(clickNum));

            holder.tvDianzanCount.setText(s);
        }
        holder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listBean = list.get(position);
                CustomDialogCleanNotice.Builder builder = new CustomDialogCleanNotice.Builder(context);
                builder.setMessage("确定删除该动态吗?").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        NetUtils.getInstance().getApis()
                                .doDeleteDynamic(listBean.getId(), userid)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Observer<CollectDynamicBean>() {
                                    @Override
                                    public void onSubscribe(Disposable d) {

                                    }

                                    @Override
                                    public void onNext(CollectDynamicBean collectDynamicBean) {
                                        if (collectDynamicBean.getObject().equals("删除成功")) {
                                            EventBus.getDefault().post("刷新数据");
                                        }
                                        dialog.dismiss();
                                    }

                                    @Override
                                    public void onError(Throwable e) {

                                    }

                                    @Override
                                    public void onComplete() {

                                    }
                                });
                    }
                });
                builder.setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builder.create().show();
            }
        });


        //设置评论数
        holder.tvPinglunCount.setText(listBean.getCommentCount() + "");

        //转发点击事件
        holder.rlZhuanfa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelect();
            }
        });

        //获取发布时位置距离当前的距离
        String distance = listBean.getDistance();
        if (distance != null) {
            holder.tvDistance.setVisibility(View.VISIBLE);

            double a = Double.valueOf(distance);
            long round = Math.round(a);
            if (round < 1000) {
                holder.tvDistance.setText(round + "m");
            } else {
                holder.tvDistance.setText(round / 1000 + "km");
            }

        } else {
            holder.tvDistance.setVisibility(View.GONE);
        }

        //获取发布时间
        String createTime = listBean.getCreateTime();
        if (createTime != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd HH:mm:ss", Locale.SIMPLIFIED_CHINESE);
            try {
                Date parse = sdf.parse(createTime);

                long time = parse.getTime();

                String newChatTime = StringUtil.getNewChatTime(time);
                holder.tvTime.setText(newChatTime);

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public void setDate5(MyDynamicViewHolderVideoAndText holder, int position) {
        listBean = list.get(position);

        //跳转到动态详情页
        holder.rlItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listBean = list.get(position);
                id = listBean.getId();

                Intent intent = new Intent(context, DynamicDetailsActivity.class);
                intent.putExtra("id", id);
                context.startActivity(intent);
            }
        });

        //跳转到用户信息页
        holder.ivHeadimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listBean = list.get(position);

                Intent intent = new Intent(context, PersonHomeActivity.class);
                SaveIntentMsgBean saveIntentMsgBean = new SaveIntentMsgBean();
                saveIntentMsgBean.setId(listBean.getUserId());
                //2标记传入姓名  1标记传入id
                saveIntentMsgBean.setFlag(1);
                intent.putExtra("msg", saveIntentMsgBean);
                context.startActivity(intent);
            }
        });

        userInfo = list.get(position).getUserInfo();
        //设置用户名
        holder.tvUsername.setText(userInfo.getNickName());
        //加载头像
        Glide.with(context).load(userInfo.getHeadImg()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(holder.ivHeadimg);


        int attestation = userInfo.getAttestation();

        //认证等级
        if (attestation == 0) {
            holder.isattaction.setVisibility(View.GONE);
        } else if (attestation == 1) {
            holder.isattaction.setImageResource(R.mipmap.qingtong);
        } else if (attestation == 2) {
            holder.isattaction.setImageResource(R.mipmap.baiyin);
        } else if (attestation == 3) {
            holder.isattaction.setImageResource(R.mipmap.huangjin);
        } else if (attestation == 4) {
            holder.isattaction.setImageResource(R.mipmap.bojin);
        } else if (attestation == 5) {
            holder.isattaction.setImageResource(R.mipmap.zuanshi);
        }else{
            holder.isattaction.setImageResource(R.mipmap.guanfang);
        }


        //判断性别是否保密
        String userRole = userInfo.getUserRole();
        if (userRole != null) {
            if (userRole.equals("保密")) {
                holder.tvAge.setVisibility(View.GONE);
            } else if (userRole.equals("")) {
                holder.tvAge.setVisibility(View.GONE);
            } else {
                //设置角色
                holder.tvAge.setText(userRole);
            }
        } else {
            holder.tvAge.setVisibility(View.GONE);
        }


        //判断是否点赞
        if (listBean.isIsClick()) {
            holder.ivDianzan.setImageResource(R.mipmap.dianzan);
        } else {
            holder.ivDianzan.setImageResource(R.mipmap.weidianzan);
        }
        //点赞的单击事件
        holder.rlDianzan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listBean = list.get(position);
                id = listBean.getId();

                if (listBean.isIsClick() == true) {
                    //取消点赞
                    //开始执行设置不可点击 防止多次点击发生冲突
                    holder.rlDianzan.setEnabled(false);
                    NetUtils.getInstance().getApis()
                            .doCancleDianzan(1, id, userid)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<DianzanBean>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(DianzanBean dianzanBean) {
                                    //处理结束后恢复点击
                                    holder.rlDianzan.setEnabled(true);

                                    //取消点赞成功
                                    holder.ivDianzan.setImageResource(R.mipmap.weidianzan);
                                    listBean.setIsClick(false);

                                    //取消点赞成功数量加一
                                    String s = holder.tvDianzanCount.getText().toString();
                                    if (!s.contains("w")) {
                                        Integer integer = Integer.valueOf(s);

                                        integer -= 1;

                                        holder.tvDianzanCount.setText(integer + "");
                                    }
                                }

                                @Override
                                public void onError(Throwable e) {

                                }

                                @Override
                                public void onComplete() {

                                }
                            });
                } else if (listBean.isIsClick() == false) {
                    //点赞
                    holder.rlDianzan.setEnabled(false);

                    NetUtils.getInstance().getApis()
                            .doDianzan(userid, 1, id)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<DianzanBean>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(DianzanBean dianzanBean) {

                                    //处理结束后恢复点击
                                    holder.rlDianzan.setEnabled(true);

                                    //点赞成功
                                    holder.ivDianzan.setImageResource(R.mipmap.dianzan);
                                    listBean.setIsClick(true);

                                    //点赞成功数量加一
                                    String s = holder.tvDianzanCount.getText().toString();

                                    if (!s.contains("w")) {
                                        Integer integer = Integer.valueOf(s);

                                        integer += 1;

                                        holder.tvDianzanCount.setText(integer + "");
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

        //点赞数设置
        int clickNum = listBean.getClickNum();
        if (clickNum < 10000) {
            holder.tvDianzanCount.setText(clickNum + "");
        } else {
            String s = StringUtil.rawIntStr2IntStr(String.valueOf(clickNum));

            holder.tvDianzanCount.setText(s);
        }
        holder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listBean = list.get(position);
                CustomDialogCleanNotice.Builder builder = new CustomDialogCleanNotice.Builder(context);
                builder.setMessage("确定删除该动态吗?").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        NetUtils.getInstance().getApis()
                                .doDeleteDynamic(listBean.getId(), userid)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Observer<CollectDynamicBean>() {
                                    @Override
                                    public void onSubscribe(Disposable d) {

                                    }

                                    @Override
                                    public void onNext(CollectDynamicBean collectDynamicBean) {
                                        if (collectDynamicBean.getObject().equals("删除成功")) {
                                            EventBus.getDefault().post("刷新数据");
                                        }
                                        dialog.dismiss();
                                    }

                                    @Override
                                    public void onError(Throwable e) {

                                    }

                                    @Override
                                    public void onComplete() {

                                    }
                                });
                    }
                });
                builder.setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builder.create().show();
            }
        });


        //设置评论数
        holder.tvPinglunCount.setText(listBean.getCommentCount() + "");

        //转发点击事件
        holder.rlZhuanfa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelect();
            }
        });

        //获取发布时位置距离当前的距离
        String distance = listBean.getDistance();
        if (distance != null) {
            holder.tvDistance.setVisibility(View.VISIBLE);

            double a = Double.valueOf(distance);
            long round = Math.round(a);
            if (round < 1000) {
                holder.tvDistance.setText(round + "m");
            } else {
                holder.tvDistance.setText(round / 1000 + "km");
            }

        } else {
            holder.tvDistance.setVisibility(View.GONE);
        }

        //获取发布时间
        String createTime = listBean.getCreateTime();
        if (createTime != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd HH:mm:ss", Locale.SIMPLIFIED_CHINESE);
            try {
                Date parse = sdf.parse(createTime);

                long time = parse.getTime();

                String newChatTime = StringUtil.getNewChatTime(time);
                holder.tvTime.setText(newChatTime);

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public void setDate4(MyDynamicViewHolderVideo holder, int position) {
        listBean = list.get(position);

        //跳转到动态详情页
        holder.rlItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listBean = list.get(position);
                id = listBean.getId();

                Intent intent = new Intent(context, DynamicDetailsActivity.class);
                intent.putExtra("id", id);
                context.startActivity(intent);
            }
        });

        //跳转到用户信息页
        holder.ivHeadimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listBean = list.get(position);

                Intent intent = new Intent(context, PersonHomeActivity.class);
                SaveIntentMsgBean saveIntentMsgBean = new SaveIntentMsgBean();
                saveIntentMsgBean.setId(listBean.getUserId());
                //2标记传入姓名  1标记传入id
                saveIntentMsgBean.setFlag(1);
                intent.putExtra("msg", saveIntentMsgBean);
                context.startActivity(intent);
            }
        });

        userInfo = list.get(position).getUserInfo();
        //设置用户名
        holder.tvUsername.setText(userInfo.getNickName());
        //加载头像
        Glide.with(context).load(userInfo.getHeadImg()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(holder.ivHeadimg);


        int attestation = userInfo.getAttestation();

        //认证等级
        if (attestation == 0) {
            holder.isattaction.setVisibility(View.GONE);
        } else if (attestation == 1) {
            holder.isattaction.setImageResource(R.mipmap.qingtong);
        } else if (attestation == 2) {
            holder.isattaction.setImageResource(R.mipmap.baiyin);
        } else if (attestation == 3) {
            holder.isattaction.setImageResource(R.mipmap.huangjin);
        } else if (attestation == 4) {
            holder.isattaction.setImageResource(R.mipmap.bojin);
        } else if (attestation == 5) {
            holder.isattaction.setImageResource(R.mipmap.zuanshi);
        }else{
            holder.isattaction.setImageResource(R.mipmap.guanfang);
        }


        //判断性别是否保密
        String userRole = userInfo.getUserRole();
        if (userRole != null) {
            if (userRole.equals("保密")) {
                holder.tvAge.setVisibility(View.GONE);
            } else if (userRole.equals("")) {
                holder.tvAge.setVisibility(View.GONE);
            } else {
                //设置角色
                holder.tvAge.setText(userRole);
            }
        } else {
            holder.tvAge.setVisibility(View.GONE);
        }


        //判断是否点赞
        if (listBean.isIsClick()) {
            holder.ivDianzan.setImageResource(R.mipmap.dianzan);
        } else {
            holder.ivDianzan.setImageResource(R.mipmap.weidianzan);
        }
        //点赞的单击事件
        holder.rlDianzan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listBean = list.get(position);
                id = listBean.getId();

                if (listBean.isIsClick() == true) {
                    //取消点赞
                    //开始执行设置不可点击 防止多次点击发生冲突
                    holder.rlDianzan.setEnabled(false);
                    NetUtils.getInstance().getApis()
                            .doCancleDianzan(1, id, userid)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<DianzanBean>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(DianzanBean dianzanBean) {
                                    //处理结束后恢复点击
                                    holder.rlDianzan.setEnabled(true);

                                    //取消点赞成功
                                    holder.ivDianzan.setImageResource(R.mipmap.weidianzan);
                                    listBean.setIsClick(false);

                                    //取消点赞成功数量加一
                                    String s = holder.tvDianzanCount.getText().toString();
                                    if (!s.contains("w")) {
                                        Integer integer = Integer.valueOf(s);

                                        integer -= 1;

                                        holder.tvDianzanCount.setText(integer + "");
                                    }
                                }

                                @Override
                                public void onError(Throwable e) {

                                }

                                @Override
                                public void onComplete() {

                                }
                            });
                } else if (listBean.isIsClick() == false) {
                    //点赞
                    holder.rlDianzan.setEnabled(false);

                    NetUtils.getInstance().getApis()
                            .doDianzan(userid, 1, id)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<DianzanBean>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(DianzanBean dianzanBean) {

                                    //处理结束后恢复点击
                                    holder.rlDianzan.setEnabled(true);

                                    //点赞成功
                                    holder.ivDianzan.setImageResource(R.mipmap.dianzan);
                                    listBean.setIsClick(true);

                                    //点赞成功数量加一
                                    String s = holder.tvDianzanCount.getText().toString();

                                    if (!s.contains("w")) {
                                        Integer integer = Integer.valueOf(s);

                                        integer += 1;

                                        holder.tvDianzanCount.setText(integer + "");
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

        //点赞数设置
        int clickNum = listBean.getClickNum();
        if (clickNum < 10000) {
            holder.tvDianzanCount.setText(clickNum + "");
        } else {
            String s = StringUtil.rawIntStr2IntStr(String.valueOf(clickNum));

            holder.tvDianzanCount.setText(s);
        }
        holder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listBean = list.get(position);
                CustomDialogCleanNotice.Builder builder = new CustomDialogCleanNotice.Builder(context);
                builder.setMessage("确定删除该动态吗?").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        NetUtils.getInstance().getApis()
                                .doDeleteDynamic(listBean.getId(), userid)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Observer<CollectDynamicBean>() {
                                    @Override
                                    public void onSubscribe(Disposable d) {

                                    }

                                    @Override
                                    public void onNext(CollectDynamicBean collectDynamicBean) {
                                        if (collectDynamicBean.getObject().equals("删除成功")) {
                                            EventBus.getDefault().post("刷新数据");
                                        }
                                        dialog.dismiss();
                                    }

                                    @Override
                                    public void onError(Throwable e) {

                                    }

                                    @Override
                                    public void onComplete() {

                                    }
                                });
                    }
                });
                builder.setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builder.create().show();
            }
        });


        //设置评论数
        holder.tvPinglunCount.setText(listBean.getCommentCount() + "");

        //转发点击事件
        holder.rlZhuanfa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelect();
            }
        });

        //获取发布时位置距离当前的距离
        String distance = listBean.getDistance();
        if (distance != null) {
            holder.tvDistance.setVisibility(View.VISIBLE);

            double a = Double.valueOf(distance);
            long round = Math.round(a);
            if (round < 1000) {
                holder.tvDistance.setText(round + "m");
            } else {
                holder.tvDistance.setText(round / 1000 + "km");
            }

        } else {
            holder.tvDistance.setVisibility(View.GONE);
        }

        //获取发布时间
        String createTime = listBean.getCreateTime();
        if (createTime != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd HH:mm:ss", Locale.SIMPLIFIED_CHINESE);
            try {
                Date parse = sdf.parse(createTime);

                long time = parse.getTime();

                String newChatTime = StringUtil.getNewChatTime(time);
                holder.tvTime.setText(newChatTime);

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
}

