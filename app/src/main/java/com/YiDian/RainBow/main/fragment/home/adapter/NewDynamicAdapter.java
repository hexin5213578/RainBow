package com.YiDian.RainBow.main.fragment.home.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.Common;
import com.YiDian.RainBow.custom.image.NineGridTestLayout;
import com.YiDian.RainBow.custom.videoplayer.SampleCoverVideo;
import com.YiDian.RainBow.login.activity.CompleteMsgActivity;
import com.YiDian.RainBow.main.fragment.home.bean.NewDynamicBean;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewDynamicAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    private final List<NewDynamicBean.ObjectBean.ListBean> list;

    public static final String TAG = "ListNormalAdapter22";
    public NewDynamicAdapter(Context context, List<NewDynamicBean.ObjectBean.ListBean> list) {
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
        int imgType = list.get(position).getImgType();
        //纯文本
        if (imgType == 1) {
            ((ViewHolder)holder).tvDynamicText.setText(list.get(position).getContentInfo());
            Glide.with(context).load(R.mipmap.headimg).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(((ViewHolder)holder).ivHeadimg);
        }
        //纯图片
        if (imgType == 2) {
            //加载圆角图
            Glide.with(context).load(R.mipmap.headimg).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(((ViewHolder)holder).ivHeadimg);

            String contentImg = list.get(position).getContentImg();
            String[] split = contentImg.split(",");
             List<String> imglist = new ArrayList<>();
            for (int i =0;i<split.length;i++){
                imglist.add(split[i]);
            }
            imglist.add("https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1602230034&di=117e0463b051e06aa0447ba7a9d9a29d&src=http://a3.att.hudong.com/14/75/01300000164186121366756803686.jpg");
            imglist.add("https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1602230034&di=117e0463b051e06aa0447ba7a9d9a29d&src=http://a3.att.hudong.com/14/75/01300000164186121366756803686.jpg");
            imglist.add("https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1602230034&di=117e0463b051e06aa0447ba7a9d9a29d&src=http://a3.att.hudong.com/14/75/01300000164186121366756803686.jpg");
            imglist.add("https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1602230034&di=117e0463b051e06aa0447ba7a9d9a29d&src=http://a3.att.hudong.com/14/75/01300000164186121366756803686.jpg");
            imglist.add("https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1602230034&di=117e0463b051e06aa0447ba7a9d9a29d&src=http://a3.att.hudong.com/14/75/01300000164186121366756803686.jpg");
            imglist.add("https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1602230034&di=117e0463b051e06aa0447ba7a9d9a29d&src=http://a3.att.hudong.com/14/75/01300000164186121366756803686.jpg");
            imglist.add("https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1602230034&di=117e0463b051e06aa0447ba7a9d9a29d&src=http://a3.att.hudong.com/14/75/01300000164186121366756803686.jpg");
            imglist.add("https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1602230034&di=117e0463b051e06aa0447ba7a9d9a29d&src=http://a3.att.hudong.com/14/75/01300000164186121366756803686.jpg");
            imglist.add("https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1602230034&di=117e0463b051e06aa0447ba7a9d9a29d&src=http://a3.att.hudong.com/14/75/01300000164186121366756803686.jpg");
            imglist.add("https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1602230034&di=117e0463b051e06aa0447ba7a9d9a29d&src=http://a3.att.hudong.com/14/75/01300000164186121366756803686.jpg");
            ((ViewHolder)holder).layout.setIsShowAll(false); //当传入的图片数超过9张时，是否全部显示
            ((ViewHolder)holder).layout.setSpacing(5); //动态设置图片之间的间隔
            ((ViewHolder)holder).layout.setUrlList(imglist); //最后再设置图片url

        }
        //文本加图片
        if (imgType == 21) {
            Glide.with(context).load(R.mipmap.headimg).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(((ViewHolder)holder).ivHeadimg);

            //设置文本
            ((ViewHolder)holder).tvDynamicText.setText(list.get(position).getContentInfo());
            //设置图片
            String contentImg = list.get(position).getContentImg();
            String[] split = contentImg.split(",");
            List<String> img = new ArrayList<>();
            for (int i =0;i<split.length;i++){
                img.add(split[i]);
            }

            ((ViewHolder)holder).layout.setIsShowAll(false); //当传入的图片数超过9张时，是否全部显示
            ((ViewHolder)holder).layout.setSpacing(5); //动态设置图片之间的间隔
            ((ViewHolder)holder).layout.setUrlList(img); //最后再设置图片url
        }
        //纯视频
        if (imgType == 3) {
            Glide.with(context).load(R.mipmap.headimg).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(((ViewHolder)holder).ivHeadimg);

            //设置播放视频
            String contentImg = list.get(position).getContentImg();

            //设置封面
            //((ViewHolder)holder).videoPlayer.loadCoverImage(contentImg,netVideoBitmap);
            ((ViewHolder)holder).videoPlayer.setUpLazy(contentImg, true, null, null, "");

            //防止错位设置
            ((ViewHolder)holder).videoPlayer.setPlayTag(TAG);
            ((ViewHolder)holder).videoPlayer.setLockLand(true);
            ((ViewHolder)holder).videoPlayer.setPlayPosition(position);
            //是否根据视频尺寸，自动选择竖屏全屏或者横屏全屏，这个标志为和 setLockLand 冲突，需要和 orientationUtils 使用
            ((ViewHolder)holder).videoPlayer.setAutoFullWithSize(false);
            //音频焦点冲突时是否释放
            ((ViewHolder)holder).videoPlayer.setReleaseWhenLossAudio(false);
            //全屏动画
            ((ViewHolder)holder).videoPlayer.setShowFullAnimation(true);
            //小屏时不触摸滑动
            ((ViewHolder)holder).videoPlayer.setIsTouchWiget(false);

        }
        //视频加文本
        if (imgType == 31) {
            Glide.with(context).load(R.mipmap.headimg).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(((ViewHolder)holder).ivHeadimg);

            //设置文本
            ((ViewHolder)holder).tvDynamicText.setText(list.get(position).getContentInfo());

            //设置播放视频
            String contentImg = list.get(position).getContentImg();


            //设置封面
            //((ViewHolder)holder).videoPlayer.loadCoverImage(contentImg,netVideoBitmap);
            //设置播放路径
            ((ViewHolder)holder).videoPlayer.setUpLazy(contentImg, true, null, null, "");

            //防止错位设置
            ((ViewHolder)holder).videoPlayer.setPlayTag(TAG);
            ((ViewHolder)holder).videoPlayer.setLockLand(true);
            ((ViewHolder)holder).videoPlayer.setPlayPosition(position);
            //是否根据视频尺寸，自动选择竖屏全屏或者横屏全屏，这个标志为和 setLockLand 冲突，需要和 orientationUtils 使用
            ((ViewHolder)holder).videoPlayer.setAutoFullWithSize(false);
            //音频焦点冲突时是否释放
            ((ViewHolder)holder).videoPlayer.setReleaseWhenLossAudio(false);
            //全屏动画
            ((ViewHolder)holder).videoPlayer.setShowFullAnimation(true);
            //小屏时不触摸滑动
            ((ViewHolder)holder).videoPlayer.setIsTouchWiget(false);

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
        @BindView(R.id.tv_liulan_count)
        TextView tvLiulanCount;
        @Nullable
        @BindView(R.id.rc_image)
        NineGridTestLayout layout;
        @Nullable
        @BindView(R.id.video_player)
        SampleCoverVideo videoPlayer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
