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
import android.media.MediaMetadataRetriever;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
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
import com.YiDian.RainBow.custom.customDialog.CustomDialogCancleFollow;
import com.YiDian.RainBow.custom.image.NineGridTestLayout;
import com.YiDian.RainBow.custom.videoplayer.SampleCoverVideo;
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
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    String wechatUrl = "http://web.p.qq.com/qqmpmobile/aio/app.html?id=101906973";

    private PopupWindow mPopupWindow;
    private Tencent mTencent;
    int userid;
    private NewDynamicBean.ObjectBean.ListBean listBean;
    private NewDynamicBean.ObjectBean.ListBean.UserInfoBean userInfo;
    private int id;

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

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        //腾讯AppId(替换你自己App Id)、上下文
        mTencent = Tencent.createInstance("101906973", context);
        userid = Integer.valueOf(Common.getUserId());


        listBean = list.get(position);

        //跳转到动态详情页
        ((ViewHolder) holder).rlItem.setOnClickListener(new View.OnClickListener() {
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
        ((ViewHolder)holder).ivHeadimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listBean = list.get(position);

                Intent intent = new Intent(context, PersonHomeActivity.class);
                SaveIntentMsgBean saveIntentMsgBean = new SaveIntentMsgBean();
                saveIntentMsgBean.setId(listBean.getUserId());
                //2标记传入姓名  1标记传入id
                saveIntentMsgBean.setFlag(1);
                intent.putExtra("msg",saveIntentMsgBean);
                context.startActivity(intent);
            }
        });

        userInfo = list.get(position).getUserInfo();
        //设置用户名
        ((ViewHolder) holder).tvUsername.setText(userInfo.getNickName());
        //加载头像
        Glide.with(context).load(userInfo.getHeadImg()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(((ViewHolder) holder).ivHeadimg);
        //设置角色
        ((ViewHolder) holder).tvAge.setText(userInfo.getUserRole());
        //是否认证
        if (userInfo.getAttestation() == 1) {
            ((ViewHolder) holder).isattaction.setVisibility(View.VISIBLE);
        } else {
            ((ViewHolder) holder).isattaction.setVisibility(View.GONE);
        }
        //判断性别是否保密
        String userRole = userInfo.getUserRole();
        if (userRole.equals("保密")) {
            ((ViewHolder) holder).tvAge.setVisibility(View.GONE);
        }

        //判断是否点赞
        if (listBean.isIsClick()) {
            ((ViewHolder) holder).ivDianzan.setImageResource(R.mipmap.dianzan);
        } else {
            ((ViewHolder) holder).ivDianzan.setImageResource(R.mipmap.weidianzan);
        }

        //点赞的单击事件
        ((ViewHolder) holder).rlDianzan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listBean = list.get(position);
                id = listBean.getId();

                if (listBean.isIsClick()==true) {
                    //取消点赞
                    //开始执行设置不可点击 防止多次点击发生冲突
                    ((ViewHolder) holder).rlDianzan.setEnabled(false);
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
                                    ((ViewHolder) holder).rlDianzan.setEnabled(true);


                                    if(dianzanBean.getObject().equals("删除成功")){
                                        //取消点赞成功
                                        ((ViewHolder) holder).ivDianzan.setImageResource(R.mipmap.weidianzan);
                                        listBean.setIsClick(false);

                                        //取消点赞成功数量加一
                                        String s = ((ViewHolder) holder).tvDianzanCount.getText().toString();
                                        if(!s.contains("w")){
                                            Integer integer = Integer.valueOf(s);

                                            integer -= 1;

                                            ((ViewHolder) holder).tvDianzanCount.setText(integer + "");
                                        }
                                    }else{
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
                                                        ((ViewHolder) holder).rlDianzan.setEnabled(true);

                                                        if (dianzanBean.getObject().equals("插入成功")) {
                                                            //点赞成功
                                                            ((ViewHolder) holder).ivDianzan.setImageResource(R.mipmap.dianzan);
                                                            listBean.setIsClick(true);

                                                            //点赞成功数量加一
                                                            //点赞成功数量加一
                                                            String s = ((ViewHolder) holder).tvDianzanCount.getText().toString();

                                                            if(!s.contains("w")){
                                                                Integer integer = Integer.valueOf(s);

                                                                integer += 1;

                                                                ((ViewHolder) holder).tvDianzanCount.setText(integer + "");
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
                                }

                                @Override
                                public void onError(Throwable e) {

                                }

                                @Override
                                public void onComplete() {

                                }
                            });
                } else if(listBean.isIsClick()==false){
                    //点赞
                    ((ViewHolder) holder).rlDianzan.setEnabled(false);

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
                                    ((ViewHolder) holder).rlDianzan.setEnabled(true);

                                    if (dianzanBean.getObject().equals("插入成功")) {
                                        //点赞成功
                                        ((ViewHolder) holder).ivDianzan.setImageResource(R.mipmap.dianzan);
                                        listBean.setIsClick(true);

                                        //点赞成功数量加一
                                        String s = ((ViewHolder) holder).tvDianzanCount.getText().toString();

                                        if(!s.contains("w")){
                                            Integer integer = Integer.valueOf(s);

                                            integer += 1;

                                            ((ViewHolder) holder).tvDianzanCount.setText(integer + "");
                                        }


                                    }else{
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
                                                        ((ViewHolder) holder).rlDianzan.setEnabled(true);


                                                        if(dianzanBean.getObject().equals("删除成功")){
                                                            //取消点赞成功
                                                            ((ViewHolder) holder).ivDianzan.setImageResource(R.mipmap.weidianzan);
                                                            listBean.setIsClick(false);

                                                            //取消点赞成功数量加一
                                                            String s = ((ViewHolder) holder).tvDianzanCount.getText().toString();
                                                            if(!s.contains("w")){
                                                                Integer integer = Integer.valueOf(s);

                                                                integer -= 1;

                                                                ((ViewHolder) holder).tvDianzanCount.setText(integer + "");
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
        if(clickNum<10000){
            ((ViewHolder)holder).tvDianzanCount.setText(clickNum+"");
        }else{
            String s = StringUtil.rawIntStr2IntStr(String.valueOf(clickNum));

            ((ViewHolder)holder).tvDianzanCount.setText(s);
        }

        //设置评论数
        ((ViewHolder) holder).tvPinglunCount.setText(listBean.getCommentCount() + "");

        //判断是否关注
        if (listBean.isIsAttention()) {
            ((ViewHolder) holder).tvGuanzhu.setBackground(context.getResources().getDrawable(R.drawable.newdynamic_yiguanzhu));
            ((ViewHolder) holder).tvGuanzhu.setText("已关注");
            ((ViewHolder) holder).tvGuanzhu.setTextColor(R.color.color_999999);
        } else {
            ((ViewHolder) holder).tvGuanzhu.setBackground(context.getResources().getDrawable(R.drawable.newdynamic_weiguanzhu));
            ((ViewHolder) holder).tvGuanzhu.setText("关注");
            ((ViewHolder) holder).tvGuanzhu.setTextColor(R.color.color_3C025A);
        }

        ((ViewHolder) holder).tvGuanzhu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listBean = list.get(position);

                if (listBean.isIsAttention()) {
                    CustomDialogCancleFollow.Builder builder = new CustomDialogCancleFollow.Builder(context);
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //开始执行设置不可点击 防止多次点击发生冲突
                            ((ViewHolder) holder).tvGuanzhu.setEnabled(false);
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
                                            //处理结束后恢复点击
                                            ((ViewHolder) holder).tvGuanzhu.setEnabled(true);
                                            if (followBean.getMsg().equals("取消关注成功")) {

                                                EventBus.getDefault().post("刷新界面");
                                                listBean.setIsAttention(false);

                                                // TODO: 2020/12/15 0015 发送通知

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
                    builder.setNegativeButton("取消",
                            new android.content.DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    builder.create().show();
                } else {
                    //开始执行设置不可点击 防止多次点击发生冲突
                    ((ViewHolder) holder).tvGuanzhu.setEnabled(false);

                    //关注
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
                                    //处理结束后恢复点击
                                    ((ViewHolder) holder).tvGuanzhu.setEnabled(true);
                                    if (followBean.getMsg().equals("关注成功")) {

                                        EventBus.getDefault().post("刷新界面");

                                        listBean.setIsAttention(true);

                                        // TODO: 2020/12/15 0015 推送通知

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

        //转发点击事件
        ((ViewHolder) holder).rlZhuanfa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelect();
            }
        });

        //获取发布时位置距离当前的距离
        String distance = listBean.getDistance();
        if (distance != null) {
            ((ViewHolder) holder).tvDistance.setVisibility(View.VISIBLE);
            String round = StringUtil.round(distance);
            ((ViewHolder) holder).tvDistance.setText(round + "km");
        } else {
            ((ViewHolder) holder).tvDistance.setVisibility(View.GONE);
        }

        //获取发布时间
        String createTime = listBean.getCreateTime();
        if(createTime!=null){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd HH:mm:ss", Locale.SIMPLIFIED_CHINESE);
            try {
                Date parse = sdf.parse(createTime);

                long time = parse.getTime();

                //获取当前时间
                long l = System.currentTimeMillis();
                //获取发布过的时长
                long difference = l - time;

                //时长大于12小时 显示日期
                if (difference > 43200000) {
                    ((ViewHolder) holder).tvTime.setText(createTime);
                }
                //时长小于12小时 展示时间
                if (difference > 1800000 && difference < 43200000) {
                    String[] s = createTime.split(" ");
                    ((ViewHolder) holder).tvTime.setText(s[1]);
                }
                if (difference > 1200000 && difference < 1800000) {
                    ((ViewHolder) holder).tvTime.setText("半小时前发布");
                }
                if (difference > 600000 && difference < 1200000) {
                    ((ViewHolder) holder).tvTime.setText("20分钟前发布");
                }
                if (difference > 300000 && difference < 600000) {
                    ((ViewHolder) holder).tvTime.setText("10分钟前发布");
                }
                if (difference > 240000 && difference < 300000) {
                    ((ViewHolder) holder).tvTime.setText("5分钟前发布");
                }
                if (difference > 180000 && difference < 240000) {
                    ((ViewHolder) holder).tvTime.setText("4分钟前发布");
                }
                if (difference > 120000 && difference < 180000) {
                    ((ViewHolder) holder).tvTime.setText("3分钟前发布");
                }
                if (difference > 60000 && difference < 120000) {
                    ((ViewHolder) holder).tvTime.setText("2分钟前发布");
                }
                if (difference < 60000) {
                    ((ViewHolder) holder).tvTime.setText("1分钟前发布");
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        int imgType = list.get(position).getImgType();
        //纯文本
        if (imgType == 1) {
            //获取文本内容
            String contentInfo = listBean.getContentInfo();

            //设置@ ## 颜色及点击
            if(!contentInfo.equals("") && contentInfo.contains("@") || contentInfo.contains("#")){
                getWeiBoContent(context,contentInfo,((ViewHolder)holder).tvDynamicText);
            }else{
                ((ViewHolder)holder).tvDynamicText.setText(contentInfo);
            }
        }
        //纯图片
        if (imgType == 2) {
            String contentImg = list.get(position).getContentImg();
            String[] split = contentImg.split(",");

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
            //获取文本内容
            String contentInfo = listBean.getContentInfo();

            //设置@ ## 颜色及点击
            if(!contentInfo.equals("") && contentInfo.contains("@") || contentInfo.contains("#")){
                getWeiBoContent(context,contentInfo,((ViewHolder)holder).tvDynamicText);
            }else{
                ((ViewHolder)holder).tvDynamicText.setText(contentInfo);
            }
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
            ((ViewHolder) holder).videoPlayer.loadCoverImage(contentImg, netVideoBitmap);

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
            //获取文本内容
            String contentInfo = listBean.getContentInfo();

            //设置@ ## 颜色及点击
            if(!contentInfo.equals("") && contentInfo.contains("@") || contentInfo.contains("#")){
                getWeiBoContent(context,contentInfo,((ViewHolder)holder).tvDynamicText);
            }else{
                ((ViewHolder)holder).tvDynamicText.setText(contentInfo);
            }

            //设置播放视频
            String contentImg = list.get(position).getContentImg();

            Bitmap netVideoBitmap = getNetVideoBitmap(contentImg);
            //设置封面
            ((ViewHolder) holder).videoPlayer.loadCoverImage(contentImg, netVideoBitmap);

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
        @BindView(R.id.rl_item)
        RelativeLayout rlItem;

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
        int imgType = listBean.getImgType();
        Bundle params = new Bundle();

        if(imgType==1){
            params.putString(QQShare.SHARE_TO_QQ_SUMMARY, listBean.getContentInfo());
            params.putString(QQShare.SHARE_TO_QQ_TITLE, userInfo.getNickName()+"的动态");
            params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "http://web.p.qq.com/qqmpmobile/aio/app.html?id=101906973");
            params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
            params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE);
        }
        if(imgType==21){
            String[] split = listBean.getContentImg().split(",");

            params.putString(QQShare.SHARE_TO_QQ_SUMMARY, listBean.getContentInfo());
            params.putString(QQShare.SHARE_TO_QQ_TITLE, userInfo.getNickName()+"的动态");
            params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "http://web.p.qq.com/qqmpmobile/aio/app.html?id=101906973");
            params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, split[0]);
            params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
            params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE);
        }
        if(imgType==2){
            String[] split = listBean.getContentImg().split(",");
            params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, split[0]);
            params.putString(QQShare.SHARE_TO_QQ_TITLE, userInfo.getNickName()+"的动态");
            params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "http://web.p.qq.com/qqmpmobile/aio/app.html?id=101906973");
            params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare. SHARE_TO_QQ_TYPE_IMAGE);
            params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE);
        }
        if(imgType==3){
            params.putString(QQShare.SHARE_TO_QQ_AUDIO_URL, listBean.getContentImg());
            params.putString(QQShare.SHARE_TO_QQ_TITLE, userInfo.getNickName()+"的动态");
            params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "http://web.p.qq.com/qqmpmobile/aio/app.html?id=101906973");
            params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare. SHARE_TO_QQ_TYPE_AUDIO);
            params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE);
        }
        if(imgType==31){
            params.putString(QQShare.SHARE_TO_QQ_SUMMARY, listBean.getContentInfo());
            params.putString(QQShare.SHARE_TO_QQ_TITLE, userInfo.getNickName()+"的动态");
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

        if(imgType==1){
            params.putString(QQShare.SHARE_TO_QQ_SUMMARY, listBean.getContentInfo());
            params.putString(QQShare.SHARE_TO_QQ_TITLE, userInfo.getNickName()+"的动态");
            params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "http://web.p.qq.com/qqmpmobile/aio/app.html?id=101906973");
            params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
            params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
        }
        if(imgType==21){
            String[] split = listBean.getContentImg().split(",");
            params.putString(QQShare.SHARE_TO_QQ_SUMMARY, listBean.getContentInfo());
            params.putString(QQShare.SHARE_TO_QQ_TITLE, userInfo.getNickName()+"的动态");
            params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "http://web.p.qq.com/qqmpmobile/aio/app.html?id=101906973");
            params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, split[0]);
            params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
            params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
        }
        if(imgType==2){
            String[] split = listBean.getContentImg().split(",");
            params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, split[0]);
            params.putString(QQShare.SHARE_TO_QQ_TITLE, userInfo.getNickName()+"的动态");
            params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "http://web.p.qq.com/qqmpmobile/aio/app.html?id=101906973");
            params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare. SHARE_TO_QQ_TYPE_IMAGE);
            params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);

        }
        if(imgType==3){
            params.putString(QQShare.SHARE_TO_QQ_AUDIO_URL, listBean.getContentImg());
            params.putString(QQShare.SHARE_TO_QQ_TITLE, userInfo.getNickName()+"的动态");
            params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "http://web.p.qq.com/qqmpmobile/aio/app.html?id=101906973");
            params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare. SHARE_TO_QQ_TYPE_AUDIO);
            params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);

        }
        if(imgType==31){
            params.putString(QQShare.SHARE_TO_QQ_SUMMARY, listBean.getContentInfo());
            params.putString(QQShare.SHARE_TO_QQ_TITLE, userInfo.getNickName()+"的动态");
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
        if (!App.getWXApi().isWXAppInstalled()){
            Toast.makeText(context, "您未安装微信", Toast.LENGTH_SHORT).show();
            return;
        }else{
            if(imgType==1){
                // TODO: 2020/12/18 0018 要跳转的链接

                //初始化 WXImageObject 和 WXMediaMessage 对象
                WXWebpageObject localWXWebpageObject = new WXWebpageObject();
                localWXWebpageObject.webpageUrl = wechatUrl;

                WXMediaMessage msg = new WXMediaMessage(localWXWebpageObject);
                msg.description = listBean.getContentInfo();

                msg.title = userInfo.getNickName()+"的动态";
                //构造一个Req
                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.transaction = String.valueOf(System.currentTimeMillis());
                req.message = msg;
                req.scene = SendMessageToWX.Req.WXSceneSession;
                //调用api接口，发送数据到微信
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
                                localWXMediaMessage.title = userInfo.getNickName()+"的动态";//不能太长，否则微信会提示出错。不过博主没验证过具体能输入多长。
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
                                localWXMediaMessage.title = userInfo.getNickName()+"的动态";//不能太长，否则微信会提示出错。不过博主没验证过具体能输入多长。
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
            if(imgType==3){
                //初始化一个WXVideoObject，填写url
                WXVideoObject video = new WXVideoObject();
                video.videoUrl =listBean.getContentImg();

                //用 WXVideoObject 对象初始化一个 WXMediaMessage 对象
                WXMediaMessage msg = new WXMediaMessage(video);
                msg.title =userInfo.getNickName()+"的动态";

                Bitmap netVideoBitmap = getNetVideoBitmap(listBean.getContentImg());
                //设置封面
                msg.thumbData =getBitmapBytes(netVideoBitmap, false);

                //构造一个Req
                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.transaction = String.valueOf(System.currentTimeMillis());
                req.message =msg;
                req.scene = SendMessageToWX.Req.WXSceneSession;

                //调用api接口，发送数据到微信
                App.getWXApi().sendReq(req);
            }
            if(imgType==31){
                //初始化一个WXVideoObject，填写url
                WXVideoObject video = new WXVideoObject();
                video.videoUrl =listBean.getContentImg();

                //用 WXVideoObject 对象初始化一个 WXMediaMessage 对象
                WXMediaMessage msg = new WXMediaMessage(video);
                msg.title =userInfo.getNickName()+"的动态";
                msg.description= listBean.getContentInfo();
                Bitmap netVideoBitmap = getNetVideoBitmap(listBean.getContentImg());
                //设置封面
                msg.thumbData =getBitmapBytes(netVideoBitmap, false);

                //构造一个Req
                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.transaction = String.valueOf(System.currentTimeMillis());
                req.message =msg;
                req.scene = SendMessageToWX.Req.WXSceneSession;

                //调用api接口，发送数据到微信
                App.getWXApi().sendReq(req);

            }
        }



    }

    //分享到微信朋友圈
    private void onclickShareWechatmoments() {
        int imgType = listBean.getImgType();
        if (!App.getWXApi().isWXAppInstalled()){
            Toast.makeText(context, "您未安装微信", Toast.LENGTH_SHORT).show();
            return;
        }else{
            if(imgType==1){
                // TODO: 2020/12/18 0018 要跳转的链接

                //初始化 WXImageObject 和 WXMediaMessage 对象
                WXWebpageObject localWXWebpageObject = new WXWebpageObject();
                localWXWebpageObject.webpageUrl = wechatUrl;

                WXMediaMessage msg = new WXMediaMessage(localWXWebpageObject);
                msg.description = listBean.getContentInfo();

                msg.title = userInfo.getNickName()+"的动态";
                //构造一个Req
                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.transaction = String.valueOf(System.currentTimeMillis());
                req.message = msg;
                req.scene = SendMessageToWX.Req.WXSceneTimeline;
                //调用api接口，发送数据到微信
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
                                localWXMediaMessage.title = userInfo.getNickName()+"的动态";//不能太长，否则微信会提示出错。不过博主没验证过具体能输入多长。
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
                                localWXMediaMessage.title = userInfo.getNickName()+"的动态";//不能太长，否则微信会提示出错。不过博主没验证过具体能输入多长。
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
            if(imgType==3){
                //初始化一个WXVideoObject，填写url
                WXVideoObject video = new WXVideoObject();
                video.videoUrl =listBean.getContentImg();

                //用 WXVideoObject 对象初始化一个 WXMediaMessage 对象
                WXMediaMessage msg = new WXMediaMessage(video);
                msg.title =userInfo.getNickName()+"的动态";

                Bitmap netVideoBitmap = getNetVideoBitmap(listBean.getContentImg());
                //设置封面
                msg.thumbData =getBitmapBytes(netVideoBitmap, false);

                //构造一个Req
                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.transaction = String.valueOf(System.currentTimeMillis());
                req.message =msg;
                req.scene = SendMessageToWX.Req.WXSceneTimeline;

                //调用api接口，发送数据到微信
                App.getWXApi().sendReq(req);
            }
            if(imgType==31){
                //初始化一个WXVideoObject，填写url
                WXVideoObject video = new WXVideoObject();
                video.videoUrl =listBean.getContentImg();

                //用 WXVideoObject 对象初始化一个 WXMediaMessage 对象
                WXMediaMessage msg = new WXMediaMessage(video);
                msg.title =userInfo.getNickName()+"的动态";
                msg.description= listBean.getContentInfo();
                Bitmap netVideoBitmap = getNetVideoBitmap(listBean.getContentImg());
                //设置封面
                msg.thumbData =getBitmapBytes(netVideoBitmap, false);

                //构造一个Req
                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.transaction = String.valueOf(System.currentTimeMillis());
                req.message =msg;
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
    public static Spannable getWeiBoContent(final Context context, String source,TextView tv) {

        SpannableStringBuilder spannable = new SpannableStringBuilder(source);
        // 定义正则表达式
        String AT = "@[\\u4e00-\\u9fa5\\w\\-]+";// @人
        String TOPIC = "#([^\\#|.]+)#";// ##话题
        //设置正则
        Pattern pattern = Pattern.compile("("+AT+")|"+"("+TOPIC+")");
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
                spannable.setSpan(new MyClickableSpanAt(){
                    @Override
                    public void onClick(View widget) {
                        //这里需要做跳转用户的实现，先用一个Toast代替
                        String substring = at.substring(1);

                        Intent intent = new Intent(context, PersonHomeActivity.class);
                        SaveIntentMsgBean saveIntentMsgBean = new SaveIntentMsgBean();
                        saveIntentMsgBean.setMsg(substring);
                        //2标记传入姓名  1标记传入id
                        saveIntentMsgBean.setFlag(2);
                        intent.putExtra("msg",saveIntentMsgBean);
                        context.startActivity(intent);
                    }
                }, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            }

            // 处理话题##符号
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
                        //2标记传入话题名  1标记传入id
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
}

