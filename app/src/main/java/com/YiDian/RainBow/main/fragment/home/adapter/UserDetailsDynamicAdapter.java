package com.YiDian.RainBow.main.fragment.home.adapter;

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
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.App;
import com.YiDian.RainBow.base.Common;
import com.YiDian.RainBow.custom.customDialog.CustomDialogCancleFollow;
import com.YiDian.RainBow.main.fragment.home.activity.DynamicDetailsActivity;
import com.YiDian.RainBow.main.fragment.home.bean.DianzanBean;
import com.YiDian.RainBow.main.fragment.home.bean.FollowBean;
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


import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.YiDian.RainBow.base.Common.decodeUriAsBitmapFromNet;
import static com.YiDian.RainBow.main.fragment.home.fragment.FragmentNewDynamic.isget;

public class UserDetailsDynamicAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
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
    private ViewHolderText viewHolder;
    private ViewHolderImg viewHolderImg;
    private ViewHolderTextandimg viewHolderTextandimg;
    private ViewHolderVideo viewHolderVideo;
    private ViewHolderVideoAndText viewHolderVideoAndText;
    public UserDetailsDynamicAdapter(Activity context, List<NewDynamicBean.ObjectBean.ListBean> list, Tencent mTencent) {
        this.context = context;
        this.list = list;
        this.mTencent = mTencent;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //?????????
        if (viewType == 1) {
            viewHolder = ViewHolderText.createViewHolder(context, parent, R.layout.item_dynamic_text);
            return viewHolder;
        }
        //?????????
        if (viewType == 2) {
            viewHolderImg = ViewHolderImg.createViewHolder(context, parent, R.layout.item_dynamic_img);
            return viewHolderImg;
        }
        //???????????????
        if (viewType == 21) {
            viewHolderTextandimg = ViewHolderTextandimg.createViewHolder(context, parent, R.layout.item_dynamic_text_img);
            return viewHolderTextandimg;
        }
        //?????????
        if (viewType == 3) {
            viewHolderVideo = ViewHolderVideo.createViewHolder(context, parent, R.layout.item_dynamic_video);
            return viewHolderVideo;
        }
        //???????????????
        if (viewType == 31) {
            viewHolderVideoAndText = ViewHolderVideoAndText.createViewHolder(context, parent, R.layout.item_dynamic_video_text);
            return viewHolderVideoAndText;
        }
        return null;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        userid = Integer.valueOf(Common.getUserId());

        listBean = list.get(position);

        if (holder instanceof ViewHolderText){
            viewHolder = (ViewHolderText) holder;

            setData(viewHolder,position);
            //??????????????????
            String contentInfo = listBean.getContentInfo();

            //??????@ ## ???????????????
            if (!contentInfo.equals("") && contentInfo.contains("@") || contentInfo.contains("#")) {
                getWeiBoContent(context, contentInfo, viewHolder.tvDynamicText);
            } else {
                viewHolder.tvDynamicText.setText(contentInfo);
            }
        }
        else if (holder instanceof ViewHolderImg){
            viewHolderImg = (ViewHolderImg) holder;

            setData1(viewHolderImg,position);

            String contentImg = list.get(position).getContentImg();
            String[] split = contentImg.split(",");

            List<String> imglist = new ArrayList<>();

            for (int i = 0; i < split.length; i++) {
                imglist.add(split[i].trim() + "?imageView2/0/format/jpg/w/400");
            }

            viewHolderImg.layout.setIsShowAll(false); //???????????????????????????9???????????????????????????
            viewHolderImg.layout.setSpacing(5); //?????????????????????????????????
            viewHolderImg.layout.setAnimation(null);
            viewHolderImg.layout.setBackground(null);
            viewHolderImg.layout.setUrlList(imglist); //?????????????????????url
        }
        else if ((holder instanceof ViewHolderTextandimg)){
            viewHolderTextandimg = (ViewHolderTextandimg) holder;

            setData2(viewHolderTextandimg,position);

            //??????????????????
            String contentInfo = listBean.getContentInfo();

            //??????@ ## ???????????????
            if (!contentInfo.equals("") && contentInfo.contains("@") || contentInfo.contains("#")) {
                getWeiBoContent(context, contentInfo, viewHolderTextandimg.tvDynamicText);
            } else {
                viewHolderTextandimg.tvDynamicText.setText(contentInfo);
            }
            //????????????
            String contentImg = list.get(position).getContentImg();
            String[] split = contentImg.split(",");
            List<String> img = new ArrayList<>();
            for (int i = 0; i < split.length; i++) {
                img.add(split[i].trim() + "?imageView2/0/format/jpg/w/400");
            }
            viewHolderTextandimg.layout.setIsShowAll(false); //???????????????????????????9???????????????????????????
            viewHolderTextandimg.layout.setSpacing(5); //?????????????????????????????????
            viewHolderTextandimg.layout.setAnimation(null);
            viewHolderTextandimg.layout.setBackground(null);
            viewHolderTextandimg.layout.setUrlList(img); //?????????????????????url
        }
        else if (holder instanceof ViewHolderVideo){
            viewHolderVideo = (ViewHolderVideo) holder;
            setData3(viewHolderVideo,position);

            //??????????????????
            String contentImg = list.get(position).getContentImg();

            Bitmap bitmap = decodeUriAsBitmapFromNet(contentImg + "?vframe/jpg/offset/1/w/480/h/360");

            //????????????
            viewHolderVideo.videoPlayer.loadCoverImage(contentImg, bitmap);

            viewHolderVideo.videoPlayer.setUpLazy(contentImg, true, null, null, "");

            //??????????????????
            viewHolderVideo.videoPlayer.setPlayTag(TAG);
            viewHolderVideo.videoPlayer.setLockLand(true);
            viewHolderVideo.videoPlayer.setPlayPosition(position);
            //?????????????????????????????????????????????????????????????????????????????????????????? setLockLand ?????????????????? orientationUtils ??????
            viewHolderVideo.videoPlayer.setAutoFullWithSize(false);
            //?????????????????????????????????
            viewHolderVideo.videoPlayer.setReleaseWhenLossAudio(false);
            //????????????
            viewHolderVideo.videoPlayer.setShowFullAnimation(true);
            //????????????????????????
            viewHolderVideo.videoPlayer.setIsTouchWiget(false);
        }else if (holder instanceof ViewHolderVideoAndText){
            viewHolderVideoAndText = (ViewHolderVideoAndText) holder;

            setData4(viewHolderVideoAndText,position);
            //????????????
            //??????????????????
            String contentInfo = listBean.getContentInfo();

            //??????@ ## ???????????????
            if (!contentInfo.equals("") && contentInfo.contains("@") || contentInfo.contains("#")) {
                getWeiBoContent(context, contentInfo, viewHolderVideoAndText.tvDynamicText);
            } else {
                viewHolderVideoAndText.tvDynamicText.setText(contentInfo);
            }

            //??????????????????
            String contentImg = list.get(position).getContentImg();

            Bitmap bitmap = decodeUriAsBitmapFromNet(contentImg + "?vframe/jpg/offset/1/w/480/h/360");
            //????????????
            viewHolderVideoAndText.videoPlayer.loadCoverImage(contentImg, bitmap);

            //??????????????????
            viewHolderVideoAndText.videoPlayer.setUpLazy(contentImg, true, null, null, "");

            //??????????????????
            viewHolderVideoAndText.videoPlayer.setPlayTag(TAG);
            viewHolderVideoAndText.videoPlayer.setLockLand(true);
            viewHolderVideoAndText.videoPlayer.setPlayPosition(position);
            //?????????????????????????????????????????????????????????????????????????????????????????? setLockLand ?????????????????? orientationUtils ??????
            viewHolderVideoAndText.videoPlayer.setAutoFullWithSize(false);
            //?????????????????????????????????
            viewHolderVideoAndText.videoPlayer.setReleaseWhenLossAudio(false);
            //????????????
            viewHolderVideoAndText.videoPlayer.setShowFullAnimation(true);
            //????????????????????????
            viewHolderVideoAndText.videoPlayer.setIsTouchWiget(false);

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
        //?????????????????????
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
                //?????????QQ??????
                onClickShare();
            }
        });
        space.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //?????????QQ??????
                onClickShareQzone();
            }
        });
        wechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //?????????????????????
                onclickShareWechatFriend();
            }
        });
        moments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //??????????????????
                onclickShareWechatmoments();
            }
        });
        //popwindow????????????
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


    //?????????QQ??????
    private void onClickShare() {
        int imgType = listBean.getImgType();
        Bundle params = new Bundle();

        if(imgType==1){
            params.putString(QQShare.SHARE_TO_QQ_SUMMARY, listBean.getContentInfo());
            params.putString(QQShare.SHARE_TO_QQ_TITLE, userInfo.getNickName()+"?????????");
            params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "http://web.p.qq.com/qqmpmobile/aio/app.html?id=101906973");
            params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
            params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE);
        }
        if(imgType==21){
            String[] split = listBean.getContentImg().split(",");

            params.putString(QQShare.SHARE_TO_QQ_SUMMARY, listBean.getContentInfo());
            params.putString(QQShare.SHARE_TO_QQ_TITLE, userInfo.getNickName()+"?????????");
            params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "http://web.p.qq.com/qqmpmobile/aio/app.html?id=101906973");
            params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, split[0]);
            params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
            params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE);
        }
        if(imgType==2){
            String[] split = listBean.getContentImg().split(",");
            params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, split[0]);
            params.putString(QQShare.SHARE_TO_QQ_TITLE, userInfo.getNickName()+"?????????");
            params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "http://web.p.qq.com/qqmpmobile/aio/app.html?id=101906973");
            params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare. SHARE_TO_QQ_TYPE_IMAGE);
            params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE);
        }
        if(imgType==3){
            params.putString(QQShare.SHARE_TO_QQ_AUDIO_URL, listBean.getContentImg());
            params.putString(QQShare.SHARE_TO_QQ_TITLE, userInfo.getNickName()+"?????????");
            params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "http://web.p.qq.com/qqmpmobile/aio/app.html?id=101906973");
            params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare. SHARE_TO_QQ_TYPE_AUDIO);
            params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE);
        }
        if(imgType==31){
            params.putString(QQShare.SHARE_TO_QQ_SUMMARY, listBean.getContentInfo());
            params.putString(QQShare.SHARE_TO_QQ_TITLE, userInfo.getNickName()+"?????????");
            params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "http://web.p.qq.com/qqmpmobile/aio/app.html?id=101906973");
            params.putString(QQShare.SHARE_TO_QQ_AUDIO_URL, listBean.getContentImg());
            params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
            params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE);
        }
        mTencent.shareToQQ(context, params, new IUiListener() {
            @Override
            public void onComplete(Object o) {
                Log.e(TAG, "????????????: " + o.toString());
            }

            @Override
            public void onError(UiError uiError) {
                Log.e(TAG, "????????????: " + uiError.toString());
            }

            @Override
            public void onCancel() {
                Log.e(TAG, "????????????");

            }
        });

    }

    //?????????QQ??????
    private void onClickShareQzone() {
        int imgType = listBean.getImgType();
        Bundle params = new Bundle();

        if(imgType==1){
            params.putString(QQShare.SHARE_TO_QQ_SUMMARY, listBean.getContentInfo());
            params.putString(QQShare.SHARE_TO_QQ_TITLE, userInfo.getNickName()+"?????????");
            params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "http://web.p.qq.com/qqmpmobile/aio/app.html?id=101906973");
            params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
            params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
        }
        if(imgType==21){
            String[] split = listBean.getContentImg().split(",");
            params.putString(QQShare.SHARE_TO_QQ_SUMMARY, listBean.getContentInfo());
            params.putString(QQShare.SHARE_TO_QQ_TITLE, userInfo.getNickName()+"?????????");
            params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "http://web.p.qq.com/qqmpmobile/aio/app.html?id=101906973");
            params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, split[0]);
            params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
            params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
        }
        if(imgType==2){
            String[] split = listBean.getContentImg().split(",");
            params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, split[0]);
            params.putString(QQShare.SHARE_TO_QQ_TITLE, userInfo.getNickName()+"?????????");
            params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "http://web.p.qq.com/qqmpmobile/aio/app.html?id=101906973");
            params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare. SHARE_TO_QQ_TYPE_IMAGE);
            params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);

        }
        if(imgType==3){
            params.putString(QQShare.SHARE_TO_QQ_AUDIO_URL, listBean.getContentImg());
            params.putString(QQShare.SHARE_TO_QQ_TITLE, userInfo.getNickName()+"?????????");
            params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "http://web.p.qq.com/qqmpmobile/aio/app.html?id=101906973");
            params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare. SHARE_TO_QQ_TYPE_AUDIO);
            params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);

        }
        if(imgType==31){
            params.putString(QQShare.SHARE_TO_QQ_SUMMARY, listBean.getContentInfo());
            params.putString(QQShare.SHARE_TO_QQ_TITLE, userInfo.getNickName()+"?????????");
            params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "http://web.p.qq.com/qqmpmobile/aio/app.html?id=101906973");
            params.putString(QQShare.SHARE_TO_QQ_AUDIO_URL, listBean.getContentImg());
            params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
            params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);

        }
        mTencent.shareToQQ(context, params, new IUiListener() {
            @Override
            public void onComplete(Object o) {
                Log.e(TAG, "????????????: " + o.toString());
            }
            @Override
            public void onError(UiError uiError) {

            }

            @Override
            public void onCancel() {

            }
        });
    }

    //?????????????????????
    private void onclickShareWechatFriend() {
        int imgType = listBean.getImgType();
        if (!App.getWXApi().isWXAppInstalled()){
            Toast.makeText(context, "??????????????????", Toast.LENGTH_SHORT).show();
            return;
        }else{
            if(imgType==1){
                // TODO: 2020/12/18 0018 ??????????????????

                //????????? WXImageObject ??? WXMediaMessage ??????
                WXWebpageObject localWXWebpageObject = new WXWebpageObject();
                localWXWebpageObject.webpageUrl = wechatUrl;

                WXMediaMessage msg = new WXMediaMessage(localWXWebpageObject);
                msg.description = listBean.getContentInfo();

                msg.title = userInfo.getNickName()+"?????????";
                //????????????Req
                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.transaction = String.valueOf(System.currentTimeMillis());
                req.message = msg;
                req.scene = SendMessageToWX.Req.WXSceneSession;
                //??????api??????????????????????????????
                App.getWXApi().sendReq(req);
            }
            if(imgType==2){
                String[] split = listBean.getContentImg().split(",");

                Glide.with(context).asBitmap().load(split[0])
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                WXWebpageObject localWXWebpageObject = new WXWebpageObject();
                                localWXWebpageObject.webpageUrl = wechatUrl;
                                WXMediaMessage localWXMediaMessage = new WXMediaMessage(
                                        localWXWebpageObject);
                                localWXMediaMessage.title = userInfo.getNickName()+"?????????";//?????????????????????????????????????????????????????????????????????????????????????????????
                                localWXMediaMessage.description = "";
                                localWXMediaMessage.thumbData = getBitmapBytes(resource, false);

                                //????????????Req
                                SendMessageToWX.Req req = new SendMessageToWX.Req();
                                req.transaction = String.valueOf(System.currentTimeMillis());
                                req.message = localWXMediaMessage;
                                req.scene = SendMessageToWX.Req.WXSceneSession;
                                //??????api??????????????????????????????
                                App.getWXApi().sendReq(req);
                            }
                        });
            }
            if(imgType==21){
                String[] split = listBean.getContentImg().split(",");

                Glide.with(context).asBitmap().load(split[0])
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                WXWebpageObject localWXWebpageObject = new WXWebpageObject();
                                localWXWebpageObject.webpageUrl = wechatUrl;
                                WXMediaMessage localWXMediaMessage = new WXMediaMessage(
                                        localWXWebpageObject);
                                localWXMediaMessage.title = userInfo.getNickName()+"?????????";//?????????????????????????????????????????????????????????????????????????????????????????????
                                localWXMediaMessage.description = listBean.getContentInfo();
                                localWXMediaMessage.thumbData = getBitmapBytes(resource, false);

                                //????????????Req
                                SendMessageToWX.Req req = new SendMessageToWX.Req();
                                req.transaction = String.valueOf(System.currentTimeMillis());
                                req.message = localWXMediaMessage;
                                req.scene = SendMessageToWX.Req.WXSceneSession;
                                //??????api??????????????????????????????
                                App.getWXApi().sendReq(req);
                            }
                        });
            }
            if(imgType==3){
                //???????????????WXVideoObject?????????url
                WXVideoObject video = new WXVideoObject();
                video.videoUrl =listBean.getContentImg();

                //??? WXVideoObject ????????????????????? WXMediaMessage ??????
                WXMediaMessage msg = new WXMediaMessage(video);
                msg.title =userInfo.getNickName()+"?????????";

                Bitmap netVideoBitmap = decodeUriAsBitmapFromNet(listBean.getContentImg()+"?vframe/jpg/offset/1/w/480/h/360");

                //????????????
                msg.thumbData =getBitmapBytes(netVideoBitmap, false);

                //????????????Req
                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.transaction = String.valueOf(System.currentTimeMillis());
                req.message =msg;
                req.scene = SendMessageToWX.Req.WXSceneSession;

                //??????api??????????????????????????????
                App.getWXApi().sendReq(req);
            }
            if(imgType==31){
                //???????????????WXVideoObject?????????url
                WXVideoObject video = new WXVideoObject();
                video.videoUrl =listBean.getContentImg();

                //??? WXVideoObject ????????????????????? WXMediaMessage ??????
                WXMediaMessage msg = new WXMediaMessage(video);
                msg.title =userInfo.getNickName()+"?????????";
                msg.description= listBean.getContentInfo();
                Bitmap netVideoBitmap = decodeUriAsBitmapFromNet(listBean.getContentImg()+"?vframe/jpg/offset/1/w/480/h/360");

                //????????????
                msg.thumbData =getBitmapBytes(netVideoBitmap, false);

                //????????????Req
                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.transaction = String.valueOf(System.currentTimeMillis());
                req.message =msg;
                req.scene = SendMessageToWX.Req.WXSceneSession;

                //??????api??????????????????????????????
                App.getWXApi().sendReq(req);

            }
        }



    }

    //????????????????????????
    private void onclickShareWechatmoments() {
        int imgType = listBean.getImgType();
        if (!App.getWXApi().isWXAppInstalled()){
            Toast.makeText(context, "??????????????????", Toast.LENGTH_SHORT).show();
            return;
        }else{
            if(imgType==1){
                // TODO: 2020/12/18 0018 ??????????????????

                //????????? WXImageObject ??? WXMediaMessage ??????
                WXWebpageObject localWXWebpageObject = new WXWebpageObject();
                localWXWebpageObject.webpageUrl = wechatUrl;

                WXMediaMessage msg = new WXMediaMessage(localWXWebpageObject);
                msg.description = listBean.getContentInfo();

                msg.title = userInfo.getNickName()+"?????????";
                //????????????Req
                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.transaction = String.valueOf(System.currentTimeMillis());
                req.message = msg;
                req.scene = SendMessageToWX.Req.WXSceneTimeline;
                //??????api??????????????????????????????
                App.getWXApi().sendReq(req);
            }
            if(imgType==2){
                String[] split = listBean.getContentImg().split(",");

                Glide.with(context).asBitmap().load(split[0])
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                WXWebpageObject localWXWebpageObject = new WXWebpageObject();
                                localWXWebpageObject.webpageUrl = wechatUrl;
                                WXMediaMessage localWXMediaMessage = new WXMediaMessage(
                                        localWXWebpageObject);
                                localWXMediaMessage.title = userInfo.getNickName()+"?????????";//?????????????????????????????????????????????????????????????????????????????????????????????
                                localWXMediaMessage.description = "";
                                localWXMediaMessage.thumbData = getBitmapBytes(resource, false);

                                //????????????Req
                                SendMessageToWX.Req req = new SendMessageToWX.Req();
                                req.transaction = String.valueOf(System.currentTimeMillis());
                                req.message = localWXMediaMessage;
                                req.scene = SendMessageToWX.Req.WXSceneTimeline;
                                //??????api??????????????????????????????
                                App.getWXApi().sendReq(req);
                            }
                        });
            }
            if(imgType==21){
                String[] split = listBean.getContentImg().split(",");

                Glide.with(context).asBitmap().load(split[0])
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                WXWebpageObject localWXWebpageObject = new WXWebpageObject();
                                localWXWebpageObject.webpageUrl = wechatUrl;
                                WXMediaMessage localWXMediaMessage = new WXMediaMessage(
                                        localWXWebpageObject);
                                localWXMediaMessage.title = userInfo.getNickName()+"?????????";//?????????????????????????????????????????????????????????????????????????????????????????????
                                localWXMediaMessage.description = listBean.getContentInfo();
                                localWXMediaMessage.thumbData = getBitmapBytes(resource, false);

                                //????????????Req
                                SendMessageToWX.Req req = new SendMessageToWX.Req();
                                req.transaction = String.valueOf(System.currentTimeMillis());
                                req.message = localWXMediaMessage;
                                req.scene = SendMessageToWX.Req.WXSceneTimeline;
                                //??????api??????????????????????????????
                                App.getWXApi().sendReq(req);
                            }
                        });
            }
            if(imgType==3){
                //???????????????WXVideoObject?????????url
                WXVideoObject video = new WXVideoObject();
                video.videoUrl =listBean.getContentImg();

                //??? WXVideoObject ????????????????????? WXMediaMessage ??????
                WXMediaMessage msg = new WXMediaMessage(video);
                msg.title =userInfo.getNickName()+"?????????";

                Bitmap netVideoBitmap = decodeUriAsBitmapFromNet(listBean.getContentImg()+"?vframe/jpg/offset/1/w/480/h/360");

                //????????????
                msg.thumbData =getBitmapBytes(netVideoBitmap, false);

                //????????????Req
                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.transaction = String.valueOf(System.currentTimeMillis());
                req.message =msg;
                req.scene = SendMessageToWX.Req.WXSceneTimeline;

                //??????api??????????????????????????????
                App.getWXApi().sendReq(req);
            }
            if(imgType==31){
                //???????????????WXVideoObject?????????url
                WXVideoObject video = new WXVideoObject();
                video.videoUrl =listBean.getContentImg();

                //??? WXVideoObject ????????????????????? WXMediaMessage ??????
                WXMediaMessage msg = new WXMediaMessage(video);
                msg.title =userInfo.getNickName()+"?????????";
                msg.description= listBean.getContentInfo();
                Bitmap netVideoBitmap = decodeUriAsBitmapFromNet(listBean.getContentImg()+"?vframe/jpg/offset/1/w/480/h/360");

                //????????????
                msg.thumbData =getBitmapBytes(netVideoBitmap, false);

                //????????????Req
                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.transaction = String.valueOf(System.currentTimeMillis());
                req.message =msg;
                req.scene = SendMessageToWX.Req.WXSceneTimeline;

                //??????api??????????????????????????????
                App.getWXApi().sendReq(req);

            }
        }
    }

    //???????????????
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
     * ??????PopupWindow
     */

    private void show(View v) {
        if (mPopupWindow != null && !mPopupWindow.isShowing()) {
            mPopupWindow.showAtLocation(v, Gravity.CENTER_HORIZONTAL, 0, 0);
        }
        setWindowAlpa(true);
    }

    /**
     * ??????PopupWindow
     */
    public void dismiss() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
    }
    @SuppressLint("ResourceAsColor")
    public static Spannable getWeiBoContent(final Context context, String source,TextView tv) {

        SpannableStringBuilder spannable = new SpannableStringBuilder(source);
        // ?????????????????????
        String AT = "@[\\u4e00-\\u9fa5\\w\\-]+";// @???
        String TOPIC = "#([^\\#|.]+)#";// ##??????
        //????????????
        Pattern pattern = Pattern.compile("("+AT+")|"+"("+TOPIC+")");
        Matcher matcher = pattern.matcher(spannable);


        while (matcher.find()) {
            // ??????group???????????????????????????????????????????????????(0???????????????1?????????????????????)
            final String at = matcher.group(1);
            final String topic = matcher.group(2);
            // ??????@??????
            if (at != null) {
                //??????????????????
                int start = matcher.start(1);
                int end = start + at.length();

                tv.setMovementMethod(LinkMovementMethod.getInstance());
                spannable.setSpan(new MyClickableSpanAt(){
                    @Override
                    public void onClick(View widget) {
                        //???????????????????????????????????????????????????Toast??????
                        String substring = at.substring(1);

                        Intent intent = new Intent(context, PersonHomeActivity.class);
                        SaveIntentMsgBean saveIntentMsgBean = new SaveIntentMsgBean();
                        saveIntentMsgBean.setMsg(substring);
                        //2??????????????????  1????????????id
                        saveIntentMsgBean.setFlag(2);
                        intent.putExtra("msg",saveIntentMsgBean);
                        context.startActivity(intent);
                    }
                }, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            }

            // ????????????##??????
            if (topic != null) {
                int start = matcher.start(2);
                int end = start + topic.length();

                tv.setMovementMethod(LinkMovementMethod.getInstance());
                spannable.setSpan(new MyClickableSpanTopic(){
                    @Override
                    public void onClick(@NonNull View widget) {
                        String substring = topic.substring(1, topic.length() - 1);

                        Intent intent = new Intent(context, TopicDetailsActivity.class);
                        SaveIntentMsgBean saveIntentMsgBean = new SaveIntentMsgBean();
                        saveIntentMsgBean.setMsg(substring);
                        //2?????????????????????  1????????????id
                        saveIntentMsgBean.setFlag(2);
                        intent.putExtra("msg",saveIntentMsgBean);
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
    // ????????????????????????????????????????????????log?????????thumbData????????????
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
            localCanvas.drawBitmap(bitmap, new Rect(0, 0, i, j), new Rect(0, 0,80
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
                Log.d("xxx",e.getMessage());
            }
            i = bitmap.getHeight();
            j = bitmap.getHeight();
        }
    }
    public void setData(ViewHolderText holder,int position){
        //????????????????????????
        holder.rlItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isget) {

                } else {
                    listBean = list.get(position);
                    id = listBean.getId();

                    Intent intent = new Intent(context, DynamicDetailsActivity.class);
                    intent.putExtra("id", id);
                    context.startActivity(intent);
                }
            }
        });
        userInfo = list.get(position).getUserInfo();
        //???????????????
        holder.tvUsername.setText(userInfo.getNickName());
        //????????????
        Glide.with(context).load(userInfo.getHeadImg()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(holder.ivHeadimg);

        int attestation = userInfo.getAttestation();

        //????????????
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
        }


        //????????????????????????
        String userRole = userInfo.getUserRole();
        if (userRole != null) {
            if (userRole.equals("??????")) {
                holder.tvAge.setVisibility(View.GONE);
            } else {
                //????????????
                holder.tvAge.setText(userRole);
            }
        } else {
            holder.tvAge.setVisibility(View.GONE);
        }


        //??????????????????
        if (listBean.isIsClick()) {
            holder.ivDianzan.setImageResource(R.mipmap.dianzan);
        } else {
            holder.ivDianzan.setImageResource(R.mipmap.weidianzan);
        }
        //???????????????????????????????????? ????????? ??????????????????
        if (userid == userInfo.getId()) {
            holder.tvGuanzhu.setVisibility(View.GONE);
        }
        //???????????????????????????????????????
        if (listBean.isIsAttention() == null) {
            holder.tvGuanzhu.setVisibility(View.GONE);
        }
        //?????????????????????
        holder.rlDianzan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listBean = list.get(position);
                id = listBean.getId();

                if (listBean.isIsClick() == true) {
                    //????????????
                    //?????????????????????????????? ??????????????????????????????
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
                                    //???????????????????????????
                                    holder.rlDianzan.setEnabled(true);

                                    //??????????????????
                                    holder.ivDianzan.setImageResource(R.mipmap.weidianzan);
                                    listBean.setIsClick(false);

                                    //??????????????????????????????
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
                    //??????
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

                                    //???????????????????????????
                                    holder.rlDianzan.setEnabled(true);

                                    //????????????
                                    holder.ivDianzan.setImageResource(R.mipmap.dianzan);
                                    listBean.setIsClick(true);

                                    //????????????????????????
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

        //???????????????
        int clickNum = listBean.getClickNum();
        if (clickNum < 10000) {
            holder.tvDianzanCount.setText(clickNum + "");
        } else {
            String s = StringUtil.rawIntStr2IntStr(String.valueOf(clickNum));

            holder.tvDianzanCount.setText(s);
        }

        //???????????????
        holder.tvPinglunCount.setText(listBean.getCommentCount() + "");

        //??????????????????
        if (listBean.isIsAttention()) {
            holder.tvGuanzhu.setBackground(context.getResources().getDrawable(R.drawable.newdynamic_yiguanzhu));
            holder.tvGuanzhu.setText("?????????");
            holder.tvGuanzhu.setTextColor(context.getResources().getColor(R.color.color_999999));
        } else {
            holder.tvGuanzhu.setTextColor(context.getResources().getColor(R.color.color_3C025A));
            holder.tvGuanzhu.setBackground(context.getResources().getDrawable(R.drawable.newdynamic_weiguanzhu));
            holder.tvGuanzhu.setText("??????");
        }
        //????????????
        holder.tvGuanzhu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listBean = list.get(position);

                if (listBean.isIsAttention()) {
                    CustomDialogCancleFollow.Builder builder = new CustomDialogCancleFollow.Builder(context);
                    builder.setPositiveButton("??????", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //?????????????????????????????? ??????????????????????????????
                            holder.tvGuanzhu.setEnabled(false);
                            NetUtils.getInstance().getApis()
                                    .doCancleFollow(userid, listBean.getUserId())
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Observer<FollowBean>() {
                                        @Override
                                        public void onSubscribe(Disposable d) {

                                        }

                                        @Override
                                        public void onNext(FollowBean followBean) {
                                            //???????????????????????????
                                            holder.tvGuanzhu.setEnabled(true);
                                            if (followBean.getMsg().equals("??????????????????")) {

                                                EventBus.getDefault().post("????????????");
                                                listBean.setIsAttention(false);

                                                // TODO: 2020/12/15 0015 ????????????

                                                dialog.dismiss();

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
                    });
                    builder.setNegativeButton("??????",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    builder.create().show();
                } else {
                    //?????????????????????????????? ??????????????????????????????
                    holder.tvGuanzhu.setEnabled(false);

                    //??????
                    NetUtils.getInstance().getApis()
                            .doFollow(userid, listBean.getUserId())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<FollowBean>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(FollowBean followBean) {
                                    //???????????????????????????
                                    holder.tvGuanzhu.setEnabled(true);
                                    if (followBean.getMsg().equals("????????????")) {

                                        EventBus.getDefault().post("????????????");

                                        listBean.setIsAttention(true);


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

        //??????????????????
        holder.rlZhuanfa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelect();
            }
        });

        //??????????????????????????????????????????
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

        //??????????????????
        String createTime = listBean.getCreateTime();
        if (createTime != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd HH:mm:ss", Locale.SIMPLIFIED_CHINESE);
            try {
                Date parse = sdf.parse(createTime);

                long time = parse.getTime();

                //??????????????????
                long l = System.currentTimeMillis();
                //????????????????????????
                long difference = l - time;

                //????????????12?????? ????????????
                if (difference > 43200000) {
                    holder.tvTime.setText(createTime);
                }
                //????????????12?????? ????????????
                if (difference > 1800000 && difference < 43200000) {
                    String[] s = createTime.split(" ");
                    holder.tvTime.setText(s[1]);
                }
                if (difference > 1200000 && difference < 1800000) {
                    holder.tvTime.setText("??????????????????");
                }
                if (difference > 600000 && difference < 1200000) {
                    holder.tvTime.setText("20???????????????");
                }
                if (difference > 300000 && difference < 600000) {
                    holder.tvTime.setText("10???????????????");
                }
                if (difference > 240000 && difference < 300000) {
                    holder.tvTime.setText("5???????????????");
                }
                if (difference > 180000 && difference < 240000) {
                    holder.tvTime.setText("4???????????????");
                }
                if (difference > 120000 && difference < 180000) {
                    holder.tvTime.setText("3???????????????");
                }
                if (difference > 60000 && difference < 120000) {
                    holder.tvTime.setText("2???????????????");
                }
                if (difference < 60000) {
                    holder.tvTime.setText("1???????????????");
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
    public void setData1(ViewHolderImg holder,int position){
        //????????????????????????
        holder.rlItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isget) {

                } else {
                    listBean = list.get(position);
                    id = listBean.getId();

                    Intent intent = new Intent(context, DynamicDetailsActivity.class);
                    intent.putExtra("id", id);
                    context.startActivity(intent);
                }
            }
        });
        userInfo = list.get(position).getUserInfo();
        //???????????????
        holder.tvUsername.setText(userInfo.getNickName());
        //????????????
        Glide.with(context).load(userInfo.getHeadImg()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(holder.ivHeadimg);

        int attestation = userInfo.getAttestation();

        //????????????
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
        }


        //????????????????????????
        String userRole = userInfo.getUserRole();
        if (userRole != null) {
            if (userRole.equals("??????")) {
                holder.tvAge.setVisibility(View.GONE);
            } else {
                //????????????
                holder.tvAge.setText(userRole);
            }
        } else {
            holder.tvAge.setVisibility(View.GONE);
        }


        //??????????????????
        if (listBean.isIsClick()) {
            holder.ivDianzan.setImageResource(R.mipmap.dianzan);
        } else {
            holder.ivDianzan.setImageResource(R.mipmap.weidianzan);
        }
        //???????????????????????????????????? ????????? ??????????????????
        if (userid == userInfo.getId()) {
            holder.tvGuanzhu.setVisibility(View.GONE);
        }
        //???????????????????????????????????????
        if (listBean.isIsAttention() == null) {
            holder.tvGuanzhu.setVisibility(View.GONE);
        }
        //?????????????????????
        holder.rlDianzan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listBean = list.get(position);
                id = listBean.getId();

                if (listBean.isIsClick() == true) {
                    //????????????
                    //?????????????????????????????? ??????????????????????????????
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
                                    //???????????????????????????
                                    holder.rlDianzan.setEnabled(true);

                                    //??????????????????
                                    holder.ivDianzan.setImageResource(R.mipmap.weidianzan);
                                    listBean.setIsClick(false);

                                    //??????????????????????????????
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
                    //??????
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

                                    //???????????????????????????
                                    holder.rlDianzan.setEnabled(true);

                                    //????????????
                                    holder.ivDianzan.setImageResource(R.mipmap.dianzan);
                                    listBean.setIsClick(true);

                                    //????????????????????????
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

        //???????????????
        int clickNum = listBean.getClickNum();
        if (clickNum < 10000) {
            holder.tvDianzanCount.setText(clickNum + "");
        } else {
            String s = StringUtil.rawIntStr2IntStr(String.valueOf(clickNum));

            holder.tvDianzanCount.setText(s);
        }

        //???????????????
        holder.tvPinglunCount.setText(listBean.getCommentCount() + "");

        //??????????????????
        if (listBean.isIsAttention()) {
            holder.tvGuanzhu.setBackground(context.getResources().getDrawable(R.drawable.newdynamic_yiguanzhu));
            holder.tvGuanzhu.setText("?????????");
            holder.tvGuanzhu.setTextColor(context.getResources().getColor(R.color.color_999999));
        } else {
            holder.tvGuanzhu.setTextColor(context.getResources().getColor(R.color.color_3C025A));
            holder.tvGuanzhu.setBackground(context.getResources().getDrawable(R.drawable.newdynamic_weiguanzhu));
            holder.tvGuanzhu.setText("??????");
        }
        //????????????
        holder.tvGuanzhu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listBean = list.get(position);

                if (listBean.isIsAttention()) {
                    CustomDialogCancleFollow.Builder builder = new CustomDialogCancleFollow.Builder(context);
                    builder.setPositiveButton("??????", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //?????????????????????????????? ??????????????????????????????
                            holder.tvGuanzhu.setEnabled(false);
                            NetUtils.getInstance().getApis()
                                    .doCancleFollow(userid, listBean.getUserId())
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Observer<FollowBean>() {
                                        @Override
                                        public void onSubscribe(Disposable d) {

                                        }

                                        @Override
                                        public void onNext(FollowBean followBean) {
                                            //???????????????????????????
                                            holder.tvGuanzhu.setEnabled(true);
                                            if (followBean.getMsg().equals("??????????????????")) {

                                                EventBus.getDefault().post("????????????");
                                                listBean.setIsAttention(false);

                                                // TODO: 2020/12/15 0015 ????????????

                                                dialog.dismiss();

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
                    });
                    builder.setNegativeButton("??????",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    builder.create().show();
                } else {
                    //?????????????????????????????? ??????????????????????????????
                    holder.tvGuanzhu.setEnabled(false);

                    //??????
                    NetUtils.getInstance().getApis()
                            .doFollow(userid, listBean.getUserId())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<FollowBean>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(FollowBean followBean) {
                                    //???????????????????????????
                                    holder.tvGuanzhu.setEnabled(true);
                                    if (followBean.getMsg().equals("????????????")) {

                                        EventBus.getDefault().post("????????????");

                                        listBean.setIsAttention(true);


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

        //??????????????????
        holder.rlZhuanfa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelect();
            }
        });

        //??????????????????????????????????????????
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

        //??????????????????
        String createTime = listBean.getCreateTime();
        if (createTime != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd HH:mm:ss", Locale.SIMPLIFIED_CHINESE);
            try {
                Date parse = sdf.parse(createTime);

                long time = parse.getTime();

                //??????????????????
                long l = System.currentTimeMillis();
                //????????????????????????
                long difference = l - time;

                //????????????12?????? ????????????
                if (difference > 43200000) {
                    holder.tvTime.setText(createTime);
                }
                //????????????12?????? ????????????
                if (difference > 1800000 && difference < 43200000) {
                    String[] s = createTime.split(" ");
                    holder.tvTime.setText(s[1]);
                }
                if (difference > 1200000 && difference < 1800000) {
                    holder.tvTime.setText("??????????????????");
                }
                if (difference > 600000 && difference < 1200000) {
                    holder.tvTime.setText("20???????????????");
                }
                if (difference > 300000 && difference < 600000) {
                    holder.tvTime.setText("10???????????????");
                }
                if (difference > 240000 && difference < 300000) {
                    holder.tvTime.setText("5???????????????");
                }
                if (difference > 180000 && difference < 240000) {
                    holder.tvTime.setText("4???????????????");
                }
                if (difference > 120000 && difference < 180000) {
                    holder.tvTime.setText("3???????????????");
                }
                if (difference > 60000 && difference < 120000) {
                    holder.tvTime.setText("2???????????????");
                }
                if (difference < 60000) {
                    holder.tvTime.setText("1???????????????");
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
    public void setData2(ViewHolderTextandimg holder,int position){
        //????????????????????????
        holder.rlItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isget) {

                } else {
                    listBean = list.get(position);
                    id = listBean.getId();

                    Intent intent = new Intent(context, DynamicDetailsActivity.class);
                    intent.putExtra("id", id);
                    context.startActivity(intent);
                }
            }
        });
        //????????????????????????
        holder.ivHeadimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isget) {

                } else {
                    listBean = list.get(position);

                    Intent intent = new Intent(context, PersonHomeActivity.class);
                    SaveIntentMsgBean saveIntentMsgBean = new SaveIntentMsgBean();
                    saveIntentMsgBean.setId(listBean.getUserId());
                    //2??????????????????  1????????????id
                    saveIntentMsgBean.setFlag(1);
                    intent.putExtra("msg", saveIntentMsgBean);
                    context.startActivity(intent);
                }
            }
        });

        userInfo = list.get(position).getUserInfo();
        //???????????????
        holder.tvUsername.setText(userInfo.getNickName());
        //????????????
        Glide.with(context).load(userInfo.getHeadImg()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(holder.ivHeadimg);

        int attestation = userInfo.getAttestation();

        //????????????
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
        }


        //????????????????????????
        String userRole = userInfo.getUserRole();
        if (userRole != null) {
            if (userRole.equals("??????")) {
                holder.tvAge.setVisibility(View.GONE);
            } else {
                //????????????
                holder.tvAge.setText(userRole);
            }
        } else {
            holder.tvAge.setVisibility(View.GONE);
        }


        //??????????????????
        if (listBean.isIsClick()) {
            holder.ivDianzan.setImageResource(R.mipmap.dianzan);
        } else {
            holder.ivDianzan.setImageResource(R.mipmap.weidianzan);
        }
        //???????????????????????????????????? ????????? ??????????????????
        if (userid == userInfo.getId()) {
            holder.tvGuanzhu.setVisibility(View.GONE);
        }
        //???????????????????????????????????????
        if (listBean.isIsAttention() == null) {
            holder.tvGuanzhu.setVisibility(View.GONE);
        }
        //?????????????????????
        holder.rlDianzan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listBean = list.get(position);
                id = listBean.getId();

                if (listBean.isIsClick() == true) {
                    //????????????
                    //?????????????????????????????? ??????????????????????????????
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
                                    //???????????????????????????
                                    holder.rlDianzan.setEnabled(true);

                                    //??????????????????
                                    holder.ivDianzan.setImageResource(R.mipmap.weidianzan);
                                    listBean.setIsClick(false);

                                    //??????????????????????????????
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
                    //??????
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

                                    //???????????????????????????
                                    holder.rlDianzan.setEnabled(true);

                                    //????????????
                                    holder.ivDianzan.setImageResource(R.mipmap.dianzan);
                                    listBean.setIsClick(true);

                                    //????????????????????????
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

        //???????????????
        int clickNum = listBean.getClickNum();
        if (clickNum < 10000) {
            holder.tvDianzanCount.setText(clickNum + "");
        } else {
            String s = StringUtil.rawIntStr2IntStr(String.valueOf(clickNum));

            holder.tvDianzanCount.setText(s);
        }

        //???????????????
        holder.tvPinglunCount.setText(listBean.getCommentCount() + "");

        //??????????????????
        if (listBean.isIsAttention()) {
            holder.tvGuanzhu.setBackground(context.getResources().getDrawable(R.drawable.newdynamic_yiguanzhu));
            holder.tvGuanzhu.setText("?????????");
            holder.tvGuanzhu.setTextColor(context.getResources().getColor(R.color.color_999999));
        } else {
            holder.tvGuanzhu.setTextColor(context.getResources().getColor(R.color.color_3C025A));
            holder.tvGuanzhu.setBackground(context.getResources().getDrawable(R.drawable.newdynamic_weiguanzhu));
            holder.tvGuanzhu.setText("??????");
        }
        //????????????
        holder.tvGuanzhu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listBean = list.get(position);

                if (listBean.isIsAttention()) {
                    CustomDialogCancleFollow.Builder builder = new CustomDialogCancleFollow.Builder(context);
                    builder.setPositiveButton("??????", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //?????????????????????????????? ??????????????????????????????
                            holder.tvGuanzhu.setEnabled(false);
                            NetUtils.getInstance().getApis()
                                    .doCancleFollow(userid, listBean.getUserId())
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Observer<FollowBean>() {
                                        @Override
                                        public void onSubscribe(Disposable d) {

                                        }

                                        @Override
                                        public void onNext(FollowBean followBean) {
                                            //???????????????????????????
                                            holder.tvGuanzhu.setEnabled(true);
                                            if (followBean.getMsg().equals("??????????????????")) {

                                                EventBus.getDefault().post("????????????");
                                                listBean.setIsAttention(false);

                                                // TODO: 2020/12/15 0015 ????????????

                                                dialog.dismiss();

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
                    });
                    builder.setNegativeButton("??????",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    builder.create().show();
                } else {
                    //?????????????????????????????? ??????????????????????????????
                    holder.tvGuanzhu.setEnabled(false);

                    //??????
                    NetUtils.getInstance().getApis()
                            .doFollow(userid, listBean.getUserId())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<FollowBean>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(FollowBean followBean) {
                                    //???????????????????????????
                                    holder.tvGuanzhu.setEnabled(true);
                                    if (followBean.getMsg().equals("????????????")) {

                                        EventBus.getDefault().post("????????????");

                                        listBean.setIsAttention(true);


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

        //??????????????????
        holder.rlZhuanfa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelect();
            }
        });

        //??????????????????????????????????????????
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

        //??????????????????
        String createTime = listBean.getCreateTime();
        if (createTime != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd HH:mm:ss", Locale.SIMPLIFIED_CHINESE);
            try {
                Date parse = sdf.parse(createTime);

                long time = parse.getTime();

                //??????????????????
                long l = System.currentTimeMillis();
                //????????????????????????
                long difference = l - time;

                //????????????12?????? ????????????
                if (difference > 43200000) {
                    holder.tvTime.setText(createTime);
                }
                //????????????12?????? ????????????
                if (difference > 1800000 && difference < 43200000) {
                    String[] s = createTime.split(" ");
                    holder.tvTime.setText(s[1]);
                }
                if (difference > 1200000 && difference < 1800000) {
                    holder.tvTime.setText("??????????????????");
                }
                if (difference > 600000 && difference < 1200000) {
                    holder.tvTime.setText("20???????????????");
                }
                if (difference > 300000 && difference < 600000) {
                    holder.tvTime.setText("10???????????????");
                }
                if (difference > 240000 && difference < 300000) {
                    holder.tvTime.setText("5???????????????");
                }
                if (difference > 180000 && difference < 240000) {
                    holder.tvTime.setText("4???????????????");
                }
                if (difference > 120000 && difference < 180000) {
                    holder.tvTime.setText("3???????????????");
                }
                if (difference > 60000 && difference < 120000) {
                    holder.tvTime.setText("2???????????????");
                }
                if (difference < 60000) {
                    holder.tvTime.setText("1???????????????");
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
    public void setData3(ViewHolderVideo holder,int position){
        //????????????????????????
        holder.rlItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isget) {

                } else {
                    listBean = list.get(position);
                    id = listBean.getId();

                    Intent intent = new Intent(context, DynamicDetailsActivity.class);
                    intent.putExtra("id", id);
                    context.startActivity(intent);
                }
            }
        });

        userInfo = list.get(position).getUserInfo();
        //???????????????
        holder.tvUsername.setText(userInfo.getNickName());
        //????????????
        Glide.with(context).load(userInfo.getHeadImg()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(holder.ivHeadimg);

        int attestation = userInfo.getAttestation();

        //????????????
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
        }


        //????????????????????????
        String userRole = userInfo.getUserRole();
        if (userRole != null) {
            if (userRole.equals("??????")) {
                holder.tvAge.setVisibility(View.GONE);
            } else {
                //????????????
                holder.tvAge.setText(userRole);
            }
        } else {
            holder.tvAge.setVisibility(View.GONE);
        }


        //??????????????????
        if (listBean.isIsClick()) {
            holder.ivDianzan.setImageResource(R.mipmap.dianzan);
        } else {
            holder.ivDianzan.setImageResource(R.mipmap.weidianzan);
        }
        //???????????????????????????????????? ????????? ??????????????????
        if (userid == userInfo.getId()) {
            holder.tvGuanzhu.setVisibility(View.GONE);
        }
        //???????????????????????????????????????
        if (listBean.isIsAttention() == null) {
            holder.tvGuanzhu.setVisibility(View.GONE);
        }
        //?????????????????????
        holder.rlDianzan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listBean = list.get(position);
                id = listBean.getId();

                if (listBean.isIsClick() == true) {
                    //????????????
                    //?????????????????????????????? ??????????????????????????????
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
                                    //???????????????????????????
                                    holder.rlDianzan.setEnabled(true);

                                    //??????????????????
                                    holder.ivDianzan.setImageResource(R.mipmap.weidianzan);
                                    listBean.setIsClick(false);

                                    //??????????????????????????????
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
                    //??????
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

                                    //???????????????????????????
                                    holder.rlDianzan.setEnabled(true);

                                    //????????????
                                    holder.ivDianzan.setImageResource(R.mipmap.dianzan);
                                    listBean.setIsClick(true);

                                    //????????????????????????
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

        //???????????????
        int clickNum = listBean.getClickNum();
        if (clickNum < 10000) {
            holder.tvDianzanCount.setText(clickNum + "");
        } else {
            String s = StringUtil.rawIntStr2IntStr(String.valueOf(clickNum));

            holder.tvDianzanCount.setText(s);
        }

        //???????????????
        holder.tvPinglunCount.setText(listBean.getCommentCount() + "");

        //??????????????????
        if (listBean.isIsAttention()) {
            holder.tvGuanzhu.setBackground(context.getResources().getDrawable(R.drawable.newdynamic_yiguanzhu));
            holder.tvGuanzhu.setText("?????????");
            holder.tvGuanzhu.setTextColor(context.getResources().getColor(R.color.color_999999));
        } else {
            holder.tvGuanzhu.setTextColor(context.getResources().getColor(R.color.color_3C025A));
            holder.tvGuanzhu.setBackground(context.getResources().getDrawable(R.drawable.newdynamic_weiguanzhu));
            holder.tvGuanzhu.setText("??????");
        }
        //????????????
        holder.tvGuanzhu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listBean = list.get(position);

                if (listBean.isIsAttention()) {
                    CustomDialogCancleFollow.Builder builder = new CustomDialogCancleFollow.Builder(context);
                    builder.setPositiveButton("??????", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //?????????????????????????????? ??????????????????????????????
                            holder.tvGuanzhu.setEnabled(false);
                            NetUtils.getInstance().getApis()
                                    .doCancleFollow(userid, listBean.getUserId())
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Observer<FollowBean>() {
                                        @Override
                                        public void onSubscribe(Disposable d) {

                                        }

                                        @Override
                                        public void onNext(FollowBean followBean) {
                                            //???????????????????????????
                                            holder.tvGuanzhu.setEnabled(true);
                                            if (followBean.getMsg().equals("??????????????????")) {

                                                EventBus.getDefault().post("????????????");
                                                listBean.setIsAttention(false);

                                                // TODO: 2020/12/15 0015 ????????????

                                                dialog.dismiss();

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
                    });
                    builder.setNegativeButton("??????",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    builder.create().show();
                } else {
                    //?????????????????????????????? ??????????????????????????????
                    holder.tvGuanzhu.setEnabled(false);

                    //??????
                    NetUtils.getInstance().getApis()
                            .doFollow(userid, listBean.getUserId())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<FollowBean>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(FollowBean followBean) {
                                    //???????????????????????????
                                    holder.tvGuanzhu.setEnabled(true);
                                    if (followBean.getMsg().equals("????????????")) {

                                        EventBus.getDefault().post("????????????");

                                        listBean.setIsAttention(true);


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

        //??????????????????
        holder.rlZhuanfa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelect();
            }
        });

        //??????????????????????????????????????????
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

        //??????????????????
        String createTime = listBean.getCreateTime();
        if (createTime != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd HH:mm:ss", Locale.SIMPLIFIED_CHINESE);
            try {
                Date parse = sdf.parse(createTime);

                long time = parse.getTime();

                //??????????????????
                long l = System.currentTimeMillis();
                //????????????????????????
                long difference = l - time;

                //????????????12?????? ????????????
                if (difference > 43200000) {
                    holder.tvTime.setText(createTime);
                }
                //????????????12?????? ????????????
                if (difference > 1800000 && difference < 43200000) {
                    String[] s = createTime.split(" ");
                    holder.tvTime.setText(s[1]);
                }
                if (difference > 1200000 && difference < 1800000) {
                    holder.tvTime.setText("??????????????????");
                }
                if (difference > 600000 && difference < 1200000) {
                    holder.tvTime.setText("20???????????????");
                }
                if (difference > 300000 && difference < 600000) {
                    holder.tvTime.setText("10???????????????");
                }
                if (difference > 240000 && difference < 300000) {
                    holder.tvTime.setText("5???????????????");
                }
                if (difference > 180000 && difference < 240000) {
                    holder.tvTime.setText("4???????????????");
                }
                if (difference > 120000 && difference < 180000) {
                    holder.tvTime.setText("3???????????????");
                }
                if (difference > 60000 && difference < 120000) {
                    holder.tvTime.setText("2???????????????");
                }
                if (difference < 60000) {
                    holder.tvTime.setText("1???????????????");
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
    public void setData4(ViewHolderVideoAndText holder,int position){
        //????????????????????????
        holder.rlItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isget) {

                } else {
                    listBean = list.get(position);
                    id = listBean.getId();

                    Intent intent = new Intent(context, DynamicDetailsActivity.class);
                    intent.putExtra("id", id);
                    context.startActivity(intent);
                }
            }
        });

        userInfo = list.get(position).getUserInfo();
        //???????????????
        holder.tvUsername.setText(userInfo.getNickName());
        //????????????
        Glide.with(context).load(userInfo.getHeadImg()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(holder.ivHeadimg);

        int attestation = userInfo.getAttestation();

        //????????????
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
        }


        //????????????????????????
        String userRole = userInfo.getUserRole();
        if (userRole != null) {
            if (userRole.equals("??????")) {
                holder.tvAge.setVisibility(View.GONE);
            } else {
                //????????????
                holder.tvAge.setText(userRole);
            }
        } else {
            holder.tvAge.setVisibility(View.GONE);
        }


        //??????????????????
        if (listBean.isIsClick()) {
            holder.ivDianzan.setImageResource(R.mipmap.dianzan);
        } else {
            holder.ivDianzan.setImageResource(R.mipmap.weidianzan);
        }
        //???????????????????????????????????? ????????? ??????????????????
        if (userid == userInfo.getId()) {
            holder.tvGuanzhu.setVisibility(View.GONE);
        }
        //???????????????????????????????????????
        if (listBean.isIsAttention() == null) {
            holder.tvGuanzhu.setVisibility(View.GONE);
        }
        //?????????????????????
        holder.rlDianzan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listBean = list.get(position);
                id = listBean.getId();

                if (listBean.isIsClick() == true) {
                    //????????????
                    //?????????????????????????????? ??????????????????????????????
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
                                    //???????????????????????????
                                    holder.rlDianzan.setEnabled(true);

                                    //??????????????????
                                    holder.ivDianzan.setImageResource(R.mipmap.weidianzan);
                                    listBean.setIsClick(false);

                                    //??????????????????????????????
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
                    //??????
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

                                    //???????????????????????????
                                    holder.rlDianzan.setEnabled(true);

                                    //????????????
                                    holder.ivDianzan.setImageResource(R.mipmap.dianzan);
                                    listBean.setIsClick(true);

                                    //????????????????????????
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

        //???????????????
        int clickNum = listBean.getClickNum();
        if (clickNum < 10000) {
            holder.tvDianzanCount.setText(clickNum + "");
        } else {
            String s = StringUtil.rawIntStr2IntStr(String.valueOf(clickNum));

            holder.tvDianzanCount.setText(s);
        }

        //???????????????
        holder.tvPinglunCount.setText(listBean.getCommentCount() + "");

        //??????????????????
        if (listBean.isIsAttention()) {
            holder.tvGuanzhu.setBackground(context.getResources().getDrawable(R.drawable.newdynamic_yiguanzhu));
            holder.tvGuanzhu.setText("?????????");
            holder.tvGuanzhu.setTextColor(context.getResources().getColor(R.color.color_999999));
        } else {
            holder.tvGuanzhu.setTextColor(context.getResources().getColor(R.color.color_3C025A));
            holder.tvGuanzhu.setBackground(context.getResources().getDrawable(R.drawable.newdynamic_weiguanzhu));
            holder.tvGuanzhu.setText("??????");
        }
        //????????????
        holder.tvGuanzhu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listBean = list.get(position);

                if (listBean.isIsAttention()) {
                    CustomDialogCancleFollow.Builder builder = new CustomDialogCancleFollow.Builder(context);
                    builder.setPositiveButton("??????", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //?????????????????????????????? ??????????????????????????????
                            holder.tvGuanzhu.setEnabled(false);
                            NetUtils.getInstance().getApis()
                                    .doCancleFollow(userid, listBean.getUserId())
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Observer<FollowBean>() {
                                        @Override
                                        public void onSubscribe(Disposable d) {

                                        }

                                        @Override
                                        public void onNext(FollowBean followBean) {
                                            //???????????????????????????
                                            holder.tvGuanzhu.setEnabled(true);
                                            if (followBean.getMsg().equals("??????????????????")) {

                                                EventBus.getDefault().post("????????????");
                                                listBean.setIsAttention(false);

                                                // TODO: 2020/12/15 0015 ????????????

                                                dialog.dismiss();

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
                    });
                    builder.setNegativeButton("??????",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    builder.create().show();
                } else {
                    //?????????????????????????????? ??????????????????????????????
                    holder.tvGuanzhu.setEnabled(false);

                    //??????
                    NetUtils.getInstance().getApis()
                            .doFollow(userid, listBean.getUserId())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<FollowBean>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(FollowBean followBean) {
                                    //???????????????????????????
                                    holder.tvGuanzhu.setEnabled(true);
                                    if (followBean.getMsg().equals("????????????")) {

                                        EventBus.getDefault().post("????????????");

                                        listBean.setIsAttention(true);


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

        //??????????????????
        holder.rlZhuanfa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelect();
            }
        });

        //??????????????????????????????????????????
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

        //??????????????????
        String createTime = listBean.getCreateTime();
        if (createTime != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd HH:mm:ss", Locale.SIMPLIFIED_CHINESE);
            try {
                Date parse = sdf.parse(createTime);

                long time = parse.getTime();

                //??????????????????
                long l = System.currentTimeMillis();
                //????????????????????????
                long difference = l - time;

                //????????????12?????? ????????????
                if (difference > 43200000) {
                    holder.tvTime.setText(createTime);
                }
                //????????????12?????? ????????????
                if (difference > 1800000 && difference < 43200000) {
                    String[] s = createTime.split(" ");
                    holder.tvTime.setText(s[1]);
                }
                if (difference > 1200000 && difference < 1800000) {
                    holder.tvTime.setText("??????????????????");
                }
                if (difference > 600000 && difference < 1200000) {
                    holder.tvTime.setText("20???????????????");
                }
                if (difference > 300000 && difference < 600000) {
                    holder.tvTime.setText("10???????????????");
                }
                if (difference > 240000 && difference < 300000) {
                    holder.tvTime.setText("5???????????????");
                }
                if (difference > 180000 && difference < 240000) {
                    holder.tvTime.setText("4???????????????");
                }
                if (difference > 120000 && difference < 180000) {
                    holder.tvTime.setText("3???????????????");
                }
                if (difference > 60000 && difference < 120000) {
                    holder.tvTime.setText("2???????????????");
                }
                if (difference < 60000) {
                    holder.tvTime.setText("1???????????????");
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
}

