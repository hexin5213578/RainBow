package com.YiDian.RainBow.music.adapter;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.custom.image.CustomRoundAngleImageView;
import com.YiDian.RainBow.music.bean.GetMusicBean;
import com.YiDian.RainBow.music.bean.MusicDetailsBean;
import com.YiDian.RainBow.utils.NetUtils;
import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MusicAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Activity context;
    private final List<GetMusicBean.ObjectBean> list;
    String url = "https://autumnfish.cn/song/url";
    private PopupWindow mPopupWindow;
    private int music_id;
    private MediaPlayer mediaPlayer;
    private String url1;
    private boolean isplay = false;
    private String music_auth;
    private String music_img;
    private String music_name;
    private GetMusicBean.ObjectBean objectBean;

    public MusicAdapter(Activity context, List<GetMusicBean.ObjectBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_music, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        objectBean = list.get(position);
        music_id = objectBean.getMusic_id();
        music_auth = objectBean.getMusic_auth();
        music_img = objectBean.getMusic_img();
        music_name = objectBean.getMusic_name();

        //加载头像
        Glide.with(context).load(music_img).into(((ViewHolder)holder).ivHeadimg);
        //设置音乐名
        ((ViewHolder)holder).tvMusicname.setText(music_name);
        //设置作者
        ((ViewHolder)holder).tvAuthor.setText(music_auth);

        ((ViewHolder)holder).rlItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                objectBean = list.get(position);
                music_id = objectBean.getMusic_id();
                music_auth = objectBean.getMusic_auth();
                music_img = objectBean.getMusic_img();
                music_name = objectBean.getMusic_name();

                isplay = false;

                showIsSend();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_headimg)
        CustomRoundAngleImageView ivHeadimg;
        @BindView(R.id.rl_play)
        RelativeLayout rlPlay;
        @BindView(R.id.tv_musicname)
        TextView tvMusicname;
        @BindView(R.id.tv_author)
        TextView tvAuthor;
        @BindView(R.id.rl_item)
        RelativeLayout rlItem;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    //弹出选择是否发送
    public void showIsSend() {
        //创建popwiondow弹出框
        mPopupWindow = new PopupWindow();
        mPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_sendmusic, null);

        RelativeLayout rl_play = view.findViewById(R.id.rl_play);
        ImageView iv_play = view.findViewById(R.id.iv_isplay);
        ImageView iv_headimg = view.findViewById(R.id.iv_headimg);
        TextView tv_musicname = view.findViewById(R.id.tv_musicname);
        TextView tv_author = view.findViewById(R.id.tv_author);
        TextView tv_confirm = view.findViewById(R.id.tv_confirm);
        TextView tv_cancle = view.findViewById(R.id.tv_cancle);

        Glide.with(context).load(music_img).into(iv_headimg);
        tv_musicname.setText(music_name);
        tv_author.setText(music_auth);

        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //发送音乐事件
                EventBus.getDefault().post(objectBean);
                dismiss();
            }
        });
        tv_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer!=null){
                    mediaPlayer.release();
                }
                dismiss();
            }
        });
        //点击播放
        rl_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //先获取播放路径
                if (isplay==false){
                    Glide.with(context).load(R.mipmap.stop).into(iv_play);

                    NetUtils.getInstance()
                            .getApis().doGetMusicDetails(url,music_id)
                            .subscribeOn(Schedulers.io())
                            .subscribe(new Observer<MusicDetailsBean>() {
                                @Override
                                public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {

                                }

                                @Override
                                public void onNext(@io.reactivex.annotations.NonNull MusicDetailsBean musicDetailsBean) {
                                    if (musicDetailsBean.getCode()==200){
                                        MusicDetailsBean.DataBean dataBean = musicDetailsBean.getData().get(0);
                                        url1 = dataBean.getUrl();

                                        mediaPlayer = new MediaPlayer();
                                        Uri uri =Uri.parse(url1);
                                        try {
                                            mediaPlayer.setDataSource(context,uri);
                                            mediaPlayer.prepare();
                                            mediaPlayer.start();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        isplay = true;

                                    }
                                }

                                @Override
                                public void onError(@io.reactivex.annotations.NonNull Throwable e) {

                                }

                                @Override
                                public void onComplete() {

                                }
                            });
                }else{
                    Glide.with(context).load(R.mipmap.play).into(iv_play);

                    if (mediaPlayer!=null && mediaPlayer.isPlaying()){
                        mediaPlayer.release();
                        isplay =false;
                    }
                }

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
    /**
     * 显示PopupWindow
     */
    private void show(View v) {
        if (mPopupWindow != null && !mPopupWindow.isShowing()) {
            mPopupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
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
}
