package com.YiDian.RainBow.main.fragment.mine.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.Common;
import com.YiDian.RainBow.custom.image.NineGridTestLayout;
import com.YiDian.RainBow.custom.videoplayer.SampleCoverVideo;
import com.YiDian.RainBow.main.fragment.home.adapter.NewDynamicAdapter;
import com.YiDian.RainBow.main.fragment.mine.bean.SelectAllDraftsBean;
import com.YiDian.RainBow.topic.SaveIntentMsgBean;
import com.YiDian.RainBow.topic.TopicDetailsActivity;
import com.YiDian.RainBow.user.PersonHomeActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

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

import static com.YiDian.RainBow.main.fragment.home.adapter.NewDynamicAdapter.TAG;

public class MyDraftsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    private final List<SelectAllDraftsBean.ObjectBean.ListBean> list;



    public MyDraftsAdapter(Context context, List<SelectAllDraftsBean.ObjectBean.ListBean> list) {

        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //纯文本
        if (viewType == 1) {
            View view = View.inflate(context, R.layout.item_drafts_text, null);
            return new ViewHolder(view);
        }
        //纯图片
        if (viewType == 2) {
            View view = View.inflate(context, R.layout.item_drafts_img, null);
            return new ViewHolder(view);
        }
        //文本加图片
        if (viewType == 21) {
            View view = View.inflate(context, R.layout.item_drafts_text_img, null);
            return new ViewHolder(view);
        }
        //纯视频
        if (viewType == 3) {
            View view = View.inflate(context, R.layout.item_drafts_video, null);
            return new ViewHolder(view);
        }
        //视频加文本
        if (viewType == 31) {
            View view = View.inflate(context, R.layout.item_drafts_video_text, null);
            return new ViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        SelectAllDraftsBean.ObjectBean.ListBean listBean = list.get(position);
        SelectAllDraftsBean.ObjectBean.ListBean.UserInfoBean userInfo = listBean.getUserInfo();

        int imgType = listBean.getImgType();
        userInfo = list.get(position).getUserInfo();
        //设置用户名
        ((ViewHolder) holder).tvUsername.setText(userInfo.getNickName());
        //加载头像
        Glide.with(context).load(userInfo.getHeadImg()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(((ViewHolder) holder).ivHeadimg);
        //设置角色
        ((ViewHolder) holder).tvAge.setText(userInfo.getUserRole());
        //是否认证
        if (userInfo.getAttestation() == 1) {
            ((ViewHolder) holder).ivIsatt.setVisibility(View.VISIBLE);
        } else {
            ((ViewHolder) holder).ivIsatt.setVisibility(View.GONE);
        }
        //判断性别是否保密
        String userRole = userInfo.getUserRole();
        if (userRole.equals("保密")) {
            ((ViewHolder) holder).tvAge.setVisibility(View.GONE);
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
            ((ViewHolder) holder).rcImage.setIsShowAll(false); //当传入的图片数超过9张时，是否全部显示
            ((ViewHolder) holder).rcImage.setSpacing(5); //动态设置图片之间的间隔
            ((ViewHolder) holder).rcImage.setUrlList(imglist); //最后再设置图片url
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
            ((ViewHolder) holder).rcImage.setIsShowAll(false); //当传入的图片数超过9张时，是否全部显示
            ((ViewHolder) holder).rcImage.setSpacing(5); //动态设置图片之间的间隔
            ((ViewHolder) holder).rcImage.setUrlList(img); //最后再设置图片url
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
        //文本加视频
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
        //返回获取到的动态类型
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
        @BindView(R.id.rl_xiala)
        RelativeLayout rlXiala;
        @Nullable
        @BindView(R.id.tv_dynamic_text)
        TextView tvDynamicText;
        @Nullable
        @BindView(R.id.rc_image)
        NineGridTestLayout rcImage;
        @Nullable
        @BindView(R.id.video_player)
        SampleCoverVideo videoPlayer;
        @BindView(R.id.iv_isatt)
        ImageView ivIsatt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
    @SuppressLint("ResourceAsColor")
    public static Spannable getWeiBoContent(final Context context, String source, TextView tv) {

        SpannableStringBuilder spannable = new SpannableStringBuilder(source);
        // 定义正则表达式
        String AT = "@[\\u4e00-\\u9fa5\\w\\-]+";// @人
        String TOPIC = "#([^\\#|.]+)#";// ##话题
        //设置正则
        Pattern pattern = Pattern.compile("("+AT+")|"+"("+TOPIC+")");
        Matcher matcher = pattern.matcher(spannable);

        Log.d("xxx",matcher.groupCount()+"");

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
                spannable.setSpan(new NewDynamicAdapter.MyClickableSpanAt(){
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
                spannable.setSpan(new NewDynamicAdapter.MyClickableSpanTopic(){
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
}
