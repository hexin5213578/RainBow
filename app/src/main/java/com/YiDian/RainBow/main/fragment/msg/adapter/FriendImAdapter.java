package com.YiDian.RainBow.main.fragment.msg.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.Common;
import com.YiDian.RainBow.main.fragment.activity.SimplePlayerActivity;
import com.YiDian.RainBow.main.fragment.home.activity.NewDynamicImage;
import com.YiDian.RainBow.main.fragment.msg.bean.ImImageBean;
import com.YiDian.RainBow.main.fragment.msg.bean.ImMsgBean;
import com.YiDian.RainBow.main.fragment.msg.bean.ImVideoBean;
import com.YiDian.RainBow.main.fragment.msg.bean.ImVocieBean;
import com.YiDian.RainBow.utils.StringUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.jpush.im.android.api.model.Message;


public class FriendImAdapter extends RecyclerView.Adapter<ImViewHolder> {



    private List<Message> list;
    private Context context;
    private File file;
    private String newChatTime;
    private Message message;
    private Gson gson;
    private MediaPlayer mediaPlayer;
    private ImViewHolder viewHolder;
    String flag = "emulated";
    private ArrayList<String> imgUrl;
    private Intent intent;
    public static final String TAG = "IMAdapter";

    public FriendImAdapter(Context context) {

        this.context = context;
    }

    public void setData(List<Message> messages) {

        this.list = messages;
    }

    public void setHeadimg(File file) {

        this.file = file;
    }

    @NonNull
    @Override
    public ImViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0) {
            //???????????????
            viewHolder = ImViewHolder.createViewHolder(context, parent, R.layout.item_im_left);
            return viewHolder;
        }
        if (viewType == 1) {
            //????????????
            viewHolder = ImViewHolder.createViewHolder(context, parent, R.layout.item_im_left_voice);
            return viewHolder;
        }
        if (viewType == 2) {
            //????????????
            viewHolder = ImViewHolder.createViewHolder(context, parent, R.layout.item_im_left_image);
            return viewHolder;
        }
        if (viewType == 3) {
            //????????????
            viewHolder = ImViewHolder.createViewHolder(context, parent, R.layout.item_im_left_video);
            return viewHolder;
        }
        if (viewType == 4) {
            //????????????
            viewHolder = ImViewHolder.createViewHolder(context, parent, R.layout.item_im_right);
            return viewHolder;
        }
        if (viewType == 5) {
            //????????????
            viewHolder = ImViewHolder.createViewHolder(context, parent, R.layout.item_im_right_voice);
            return viewHolder;
        }
        if (viewType == 6) {
            //????????????
            viewHolder = ImViewHolder.createViewHolder(context, parent, R.layout.item_im_right_image);
            return viewHolder;
        }
        if (viewType == 7) {
            //????????????
            viewHolder = ImViewHolder.createViewHolder(context, parent, R.layout.item_im_right_video);
            return viewHolder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ImViewHolder holder, int position) {

        message = list.get(position);
        mediaPlayer = new MediaPlayer();

        imgUrl = new ArrayList<>();

        if (position != list.size() - 1) {
            Message imMsgBean = list.get(position + 1);

            newChatTime = StringUtil.getNewChatTime(message.getCreateTime());
            String newChatTime2 = StringUtil.getNewChatTime(imMsgBean.getCreateTime());

            if (newChatTime.length() < 6) {
                String[] split = newChatTime.split(":");
                String[] split1 = newChatTime2.split(":");

                if (split1[1].equals(split[1]) || split1[1].equals((Integer.valueOf(split[0]) - 1)) || split1[1].equals((Integer.valueOf(split[0]) + 1))) {
                    holder.tvTime.setVisibility(View.GONE);
                } else {
                    holder.tvTime.setVisibility(View.VISIBLE);
                    holder.tvTime.setText(newChatTime);
                }
            }
        }
        holder.tvTime.setText(newChatTime);

        String headImg = Common.getHeadImg();

        if (message.getDirect().name().equals("receive")) {
            Glide.with(context).load(file).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(holder.ivHeadimg);
        } else {
            Glide.with(context).load(headImg).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(holder.ivHeadimg);
        }


        gson = new Gson();
        if (message.getDirect().name().equals("send")) {
            if (message.getContentType().name().equals("voice")) {

                message = list.get(position);

                ImVocieBean imVocieBean = gson.fromJson(message.toJson(), ImVocieBean.class);

                holder.tvDuration.setText(imVocieBean.getContent().getDuration() + "'");

                holder.llVocie.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        mediaPlayer.reset();
                        //????????????
                        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                        try {
                            File file = new File(imVocieBean.getContent().getLocal_path());
                            FileInputStream fis = new FileInputStream(file);
                            mediaPlayer.setDataSource(fis.getFD());
                            mediaPlayer.prepare();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        mediaPlayer.start();

                        holder.ivAnim.setImageResource(R.drawable.play_animation);
                        AnimationDrawable animationDrawabl = (AnimationDrawable) holder.ivAnim.getDrawable();

                        animationDrawabl.start();

                        EventBus.getDefault().post(mediaPlayer);
                        EventBus.getDefault().post(animationDrawabl);

                        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                animationDrawabl.stop();
                                holder.ivAnim.setImageResource(R.drawable.player4);

                            }
                        });
                    }
                });
            } else if (message.getContentType().name().equals("text")) {
                message = list.get(position);
                ImMsgBean imMsgBean = gson.fromJson(message.toJson(), ImMsgBean.class);

                //??????????????????
                holder.tvMsg.setText(imMsgBean.getContent().getText());

            } else if (message.getContentType().name().equals("image")) {
                message = list.get(position);
                ImImageBean imImageBean = gson.fromJson(message.toJson(), ImImageBean.class);

                String local_path = imImageBean.getContent().getLocal_path();
                String localThumbnailPath = imImageBean.getContent().getLocalThumbnailPath();

                if (local_path != null) {
                    if (local_path.contains(flag)) {
                        Glide.with(context).load(localThumbnailPath).into(holder.ivImg);
                    } else {
                        Glide.with(context).load(local_path).into(holder.ivImg);
                    }
                } else {
                    Glide.with(context).load(localThumbnailPath).into(holder.ivImg);
                }
                holder.ivImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //??????????????????
                        imgUrl.clear();
                        intent = new Intent(context, NewDynamicImage.class);

                        if (local_path != null) {
                            if (local_path.contains(flag)) {
                                imgUrl.add(localThumbnailPath);
                            } else {
                                imgUrl.add(local_path);
                            }
                        } else {
                            imgUrl.add(localThumbnailPath);
                        }
                        intent.putStringArrayListExtra("img", imgUrl);
                        context.startActivity(intent);

                    }
                });
            } else if (message.getContentType().name().equals("video")) {
                message = list.get(position);
                ImVideoBean imVideoBean = gson.fromJson(message.toJson(), ImVideoBean.class);

                String local_path = imVideoBean.getContent().getVideo().getLocal_path();

                Bitmap netVideoBitmap = getNetVideoBitmap(local_path);
                //????????????
                holder.videoPlayer.loadCoverImage(local_path, netVideoBitmap);
                int duration = imVideoBean.getContent().getDuration();
                if (duration < 10) {
                    holder.videoPlayer.setDuration("00:0" + duration);
                } else if (duration < 60) {
                    holder.videoPlayer.setDuration("00:" + duration);
                } else {
                    int second = duration / 60;
                    int minute = duration % 60;

                    if (minute == 0) {
                        holder.videoPlayer.setDuration(second + ":00");
                    } else {
                        holder.videoPlayer.setDuration(second + ":" + minute);
                    }
                }

                holder.videoPlayer.setUpLazy(local_path, true, null, null, "");

                //??????????????????
                holder.videoPlayer.setPlayTag(TAG);
                holder.videoPlayer.setLockLand(true);
                holder.videoPlayer.setPlayPosition(position);
                //?????????????????????????????????????????????????????????????????????????????????????????? setLockLand ?????????????????? orientationUtils ??????
                holder.videoPlayer.setAutoFullWithSize(false);
                //?????????????????????????????????
                holder.videoPlayer.setReleaseWhenLossAudio(false);
                //????????????
                holder.videoPlayer.setShowFullAnimation(true);
                //????????????????????????
                holder.videoPlayer.setIsTouchWiget(false);

                holder.videoPlayer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        intent = new Intent(context, SimplePlayerActivity.class);
                        intent.putExtra("url", imVideoBean.getContent().getThumb().getLocal_path());
                    }
                });
            }
        } else {
            if (message.getContentType().name().equals("voice")) {
                message = list.get(position);
                ImVocieBean imVocieBean = gson.fromJson(message.toJson(), ImVocieBean.class);

                holder.tvDuration.setText(imVocieBean.getContent().getDuration() + "'");

                holder.llVocie.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mediaPlayer.reset();
                        //????????????
                        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                        try {
                            File file = new File(imVocieBean.getContent().getLocal_path());
                            FileInputStream fis = new FileInputStream(file);
                            mediaPlayer.setDataSource(fis.getFD());
                            mediaPlayer.prepare();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        mediaPlayer.start();

                        holder.ivAnim.setImageResource(R.drawable.play_animation1);
                        AnimationDrawable animationDrawabl = (AnimationDrawable) holder.ivAnim.getDrawable();

                        animationDrawabl.start();

                        EventBus.getDefault().post(mediaPlayer);
                        EventBus.getDefault().post(animationDrawabl);

                        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                animationDrawabl.stop();
                                holder.ivAnim.setImageResource(R.drawable.play4);
                            }
                        });
                    }
                });
            } else if (message.getContentType().name().equals("text")) {
                message = list.get(position);
                ImMsgBean imMsgBean = gson.fromJson(message.toJson(), ImMsgBean.class);

                //??????????????????
                holder.tvMsg.setText(imMsgBean.getContent().getText());

            } else if (message.getContentType().name().equals("image")) {
                message = list.get(position);
                ImImageBean imImageBean = gson.fromJson(message.toJson(), ImImageBean.class);

                String local_path = imImageBean.getContent().getLocal_path();
                String localThumbnailPath = imImageBean.getContent().getLocalThumbnailPath();

                if (local_path != null) {
                    if (local_path.contains(flag)) {
                        Glide.with(context).load(localThumbnailPath).into(holder.ivImg);
                    } else {
                        Glide.with(context).load(local_path).into(holder.ivImg);
                    }
                } else {
                    Glide.with(context).load(localThumbnailPath).into(holder.ivImg);

                }

                holder.ivImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //??????????????????
                        imgUrl.clear();
                        intent = new Intent(context, NewDynamicImage.class);

                        if (local_path != null) {
                            if (local_path.contains(flag)) {
                                imgUrl.add(localThumbnailPath);
                            } else {
                                imgUrl.add(local_path);
                            }
                        } else {
                            imgUrl.add(localThumbnailPath);
                        }

                        intent.putStringArrayListExtra("img", imgUrl);
                        context.startActivity(intent);
                    }
                });

            } else if (message.getContentType().name().equals("video")) {
                message = list.get(position);
                ImVideoBean imVideoBean = gson.fromJson(message.toJson(), ImVideoBean.class);

                String media_id = imVideoBean.getContent().getThumb().getLocal_path();

                Bitmap netVideoBitmap = getNetVideoBitmap(media_id);
                //????????????
                holder.videoPlayer.loadCoverImage(media_id, netVideoBitmap);


                int duration = imVideoBean.getContent().getDuration();
                if (duration < 10) {
                    holder.videoPlayer.setDuration("00:0" + duration);
                } else if (duration < 60) {
                    holder.videoPlayer.setDuration("00:" + duration);
                } else {
                    int second = duration / 60;
                    int minute = duration % 60;

                    if (minute == 0) {
                        holder.videoPlayer.setDuration(second + ":00");
                    } else {
                        holder.videoPlayer.setDuration(second + ":" + minute);
                    }
                }
                holder.videoPlayer.setUpLazy(media_id, true, null, null, "");

                //??????????????????
                holder.videoPlayer.setPlayTag(TAG);
                holder.videoPlayer.setLockLand(true);
                holder.videoPlayer.setPlayPosition(position);
                //?????????????????????????????????????????????????????????????????????????????????????????? setLockLand ?????????????????? orientationUtils ??????
                holder.videoPlayer.setAutoFullWithSize(false);
                //?????????????????????????????????
                holder.videoPlayer.setReleaseWhenLossAudio(false);
                //????????????
                holder.videoPlayer.setShowFullAnimation(true);
                //????????????????????????
                holder.videoPlayer.setIsTouchWiget(false);
                holder.videoPlayer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        intent = new Intent(context, SimplePlayerActivity.class);
                        intent.putExtra("url", imVideoBean.getContent().getThumb().getLocal_path());
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        message = list.get(position);

        if (message.getDirect().name().equals("receive")) {
            if (message.getContentType().name().equals("voice")) {
                return 1;
            } else if (message.getContentType().name().equals("text")) {
                return 0;
            } else if (message.getContentType().name().equals("image")) {
                return 2;
            } else if (message.getContentType().name().equals("video")) {
                return 3;
            }
        } else {
            if (message.getContentType().name().equals("voice")) {
                return 5;
            } else if (message.getContentType().name().equals("text")) {
                return 4;
            } else if (message.getContentType().name().equals("image")) {
                return 6;
            } else if (message.getContentType().name().equals("video")) {
                return 7;
            }
        }
        return 8;
    }

    public static Bitmap getNetVideoBitmap(String videoUrl) {
        Bitmap bitmap = null;

        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            FileInputStream inputStream = new FileInputStream(new File(videoUrl).getAbsolutePath());
            retriever.setDataSource(inputStream.getFD());
            //?????????????????????
            bitmap = retriever.getFrameAtTime();
        } catch (IllegalArgumentException | FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            retriever.release();
        }
        return bitmap;
    }
}
