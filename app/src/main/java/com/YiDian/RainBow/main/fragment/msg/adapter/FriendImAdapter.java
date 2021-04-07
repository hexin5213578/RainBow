package com.YiDian.RainBow.main.fragment.msg.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.Common;
import com.YiDian.RainBow.main.fragment.activity.SimplePlayerActivity;
import com.YiDian.RainBow.main.fragment.home.activity.NewDynamicImage;
import com.YiDian.RainBow.main.fragment.msg.activity.FriendImActivity;
import com.YiDian.RainBow.main.fragment.msg.activity.ReportActivity;
import com.YiDian.RainBow.main.fragment.msg.bean.ImImageBean;
import com.YiDian.RainBow.main.fragment.msg.bean.ImLocationBean;
import com.YiDian.RainBow.main.fragment.msg.bean.ImMp3Bean;
import com.YiDian.RainBow.main.fragment.msg.bean.ImMsgBean;
import com.YiDian.RainBow.main.fragment.msg.bean.ImVideoBean;
import com.YiDian.RainBow.main.fragment.msg.bean.ImVocieBean;
import com.YiDian.RainBow.map.SendLocationActivity;
import com.YiDian.RainBow.topic.SaveIntentMsgBean;
import com.YiDian.RainBow.user.PersonHomeActivity;
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

import cn.jpush.im.android.api.callback.DownloadCompletionCallback;
import cn.jpush.im.android.api.content.ImageContent;
import cn.jpush.im.android.api.content.VideoContent;
import cn.jpush.im.android.api.model.Message;


public class FriendImAdapter extends RecyclerView.Adapter<ImViewHolder> {
    private List<Message> list;
    private Context context;
    private String contentId;
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
    private Intent intent1;


    public FriendImAdapter(Context context, String id) {

        this.context = context;
        this.contentId = id;
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
            //接收纯文本
            viewHolder = ImViewHolder.createViewHolder(context, parent, R.layout.item_im_left);
            return viewHolder;
        }
        if (viewType == 1) {
            //接收语音
            viewHolder = ImViewHolder.createViewHolder(context, parent, R.layout.item_im_left_voice);
            return viewHolder;
        }
        if (viewType == 2) {
            //接收图片
            viewHolder = ImViewHolder.createViewHolder(context, parent, R.layout.item_im_left_image);
            return viewHolder;
        }
        if (viewType == 3) {
            //接收视频
            viewHolder = ImViewHolder.createViewHolder(context, parent, R.layout.item_im_left_video);
            return viewHolder;
        }
        if (viewType == 4) {
            //发送文本
            viewHolder = ImViewHolder.createViewHolder(context, parent, R.layout.item_im_right);
            return viewHolder;
        }
        if (viewType == 5) {
            //发送语音
            viewHolder = ImViewHolder.createViewHolder(context, parent, R.layout.item_im_right_voice);
            return viewHolder;
        }
        if (viewType == 6) {
            //发送图片
            viewHolder = ImViewHolder.createViewHolder(context, parent, R.layout.item_im_right_image);
            return viewHolder;
        }
        if (viewType == 7) {
            //发送视频
            viewHolder = ImViewHolder.createViewHolder(context, parent, R.layout.item_im_right_video);
            return viewHolder;
        }
        if (viewType == 8) {
            //发出位置信息
            viewHolder = ImViewHolder.createViewHolder(context, parent, R.layout.item_im_right_location);
            return viewHolder;
        }
        if (viewType == 9) {
            //接收位置信息
            viewHolder = ImViewHolder.createViewHolder(context, parent, R.layout.item_im_left_location);
            return viewHolder;
        }
        if (viewType == 10) {
            //发出音乐
            viewHolder = ImViewHolder.createViewHolder(context, parent, R.layout.item_im_right_music);
            return viewHolder;
        }
        if (viewType == 11) {
            //收到音乐
            viewHolder = ImViewHolder.createViewHolder(context, parent, R.layout.item_im_left_music);
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

            holder.ivHeadimg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PersonHomeActivity.class);
                    SaveIntentMsgBean saveIntentMsgBean = new SaveIntentMsgBean();
                    saveIntentMsgBean.setId(Integer.valueOf(contentId));
                    //2标记传入姓名  1标记传入id
                    saveIntentMsgBean.setFlag(1);
                    intent.putExtra("msg", saveIntentMsgBean);
                    context.startActivity(intent);
                }
            });
        } else {
            Glide.with(context).load(headImg).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(holder.ivHeadimg);
            holder.ivHeadimg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PersonHomeActivity.class);
                    SaveIntentMsgBean saveIntentMsgBean = new SaveIntentMsgBean();
                    saveIntentMsgBean.setId(Integer.valueOf(Common.getUserId()));
                    //2标记传入姓名  1标记传入id
                    saveIntentMsgBean.setFlag(1);
                    intent.putExtra("msg", saveIntentMsgBean);
                    context.startActivity(intent);
                }
            });
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
                        //播放声音
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
            }
            else if (message.getContentType().name().equals("text")) {
                message = list.get(position);
                ImMsgBean imMsgBean = gson.fromJson(message.toJson(), ImMsgBean.class);

                //获取文本内容
                holder.tvMsg.setText(imMsgBean.getContent().getText());

            }
            else if (message.getContentType().name().equals("image")) {
                message = list.get(position);
                ImImageBean imImageBean = gson.fromJson(message.toJson(), ImImageBean.class);

                String local_path = imImageBean.getContent().getLocal_path();
                String localThumbnailPath = imImageBean.getContent().getLocalThumbnailPath();


                Glide.with(context).load(local_path).into(holder.ivImg);


                /*if (local_path != null) {
                    if (local_path.contains(flag)) {
                        Glide.with(context).load(localThumbnailPath).into(holder.ivImg);
                    } else {
                        Glide.with(context).load(local_path).into(holder.ivImg);
                    }
                } else {
                    Glide.with(context).load(localThumbnailPath).into(holder.ivImg);
                }*/

                holder.ivImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //先清空再添加
                        imgUrl.clear();
                        intent = new Intent(context, NewDynamicImage.class);
                        imgUrl.add(local_path);

                        /*if (local_path != null) {
                            if (local_path.contains(flag)) {
                                imgUrl.add(localThumbnailPath);
                            } else {
                                imgUrl.add(local_path);
                            }
                        } else {
                            imgUrl.add(localThumbnailPath);
                        }*/
                        intent.putStringArrayListExtra("img", imgUrl);
                        context.startActivity(intent);

                    }
                });
            }
            else if (message.getContentType().name().equals("video")) {
                message = list.get(position);
                ImVideoBean imVideoBean = gson.fromJson(message.toJson(), ImVideoBean.class);

                VideoContent videoContent = (VideoContent) message.getContent();
                videoContent.downloadVideoFile(message, new DownloadCompletionCallback() {
                    @Override
                    public void onComplete(int i, String s, File file) {
                        //设置缩略图
                        Bitmap netVideoBitmap = getNetVideoBitmap(file);
                        Drawable drawable = new BitmapDrawable(netVideoBitmap);

                        holder.videoView.setBackground(drawable);

                        //跳转到大图播放
                        holder.videoView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(context, SimplePlayerActivity.class);
                                intent.putExtra("url", file.getAbsolutePath());
                                context.startActivity(intent);
                            }
                        });

                    }
                });
            }
            else if (message.getContentType().name().equals("location")) {
                message = list.get(position);

                ImLocationBean imLocationBean = gson.fromJson(message.toJson(), ImLocationBean.class);

                String label = imLocationBean.getContent().getLabel();
                String[] split = label.split(",");

                holder.tvTitle.setText(split[1] + "");
                holder.tvAddress.setText(split[0] + "");
                double latitude = imLocationBean.getContent().getLatitude();
                double longitude = imLocationBean.getContent().getLongitude();

                holder.rlLocation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //跳转到查看位置页
                        Intent intent = new Intent(context, SendLocationActivity.class);
                        intent.putExtra("address", label);
                        intent.putExtra("lat", latitude);
                        intent.putExtra("lon", longitude);

                        context.startActivity(intent);
                    }
                });
            }
            else if (message.getContentType().name().equals("file")){
                message = list.get(position);

                ImMp3Bean mp3Bean = gson.fromJson(message.toJson(), ImMp3Bean.class);

                String fname = mp3Bean.getContent().getFname();
                String[] split = fname.split("!!");
                holder.tvMusicname.setText(split[0]);
                holder.tvAuthor.setText(split[2]);
                Glide.with(context).load(split[1]).into(holder.ivMusicimg);


                holder.rlPlay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       /* Uri uri = Uri.parse(split[3]);

                        MediaPlayer mediaPlayer = new MediaPlayer();
                        try {
                            mediaPlayer.setDataSource(context,uri);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }*/
                    }
                });
            }
        } else {
            if (message.getContentType().name().equals("voice")) {
                message = list.get(position);
                ImVocieBean imVocieBean = gson.fromJson(message.toJson(), ImVocieBean.class);

                holder.tvDuration.setText(imVocieBean.getContent().getDuration() + "'");

                //聊天信息长按事件
                holder.llVocie.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {

                        intent1 = new Intent(context, ReportActivity.class);
                        intent1.putExtra("id", imVocieBean.getFrom_id());
                        context.startActivity(intent1);
                        return false;
                    }
                });

                holder.llVocie.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mediaPlayer.reset();
                        //播放声音
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
            }
            else if (message.getContentType().name().equals("text")) {
                message = list.get(position);
                ImMsgBean imMsgBean = gson.fromJson(message.toJson(), ImMsgBean.class);

                //获取文本内容
                holder.tvMsg.setText(imMsgBean.getContent().getText());
                //文本信息长按事件
                holder.tvMsg.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        intent1 = new Intent(context, ReportActivity.class);
                        intent1.putExtra("id", imMsgBean.getFrom_id());
                        context.startActivity(intent1);
                        return false;
                    }
                });
            }
            else if (message.getContentType().name().equals("image")) {
                message = list.get(position);
                ImImageBean imImageBean = gson.fromJson(message.toJson(), ImImageBean.class);

                String localThumbnailPath = imImageBean.getContent().getLocalThumbnailPath();

                ImageContent imageContent = (ImageContent) message.getContent();

                imageContent.downloadThumbnailImage(message, new DownloadCompletionCallback() {
                    @Override
                    public void onComplete(int i, String s, File file) {
                        Glide.with(context).load(file).into(holder.ivImg);
                    }
                });
                /*if (local_path != null) {
                    if (local_path.contains(flag)) {
                        Glide.with(context).load(localThumbnailPath).into(holder.ivImg);
                    } else {
                        Glide.with(context).load(local_path).into(holder.ivImg);
                    }
                } else {
                    Glide.with(context).load(localThumbnailPath).into(holder.ivImg);
                }*/

                //图片的长按事件
                holder.ivImg.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        intent1 = new Intent(context, ReportActivity.class);
                        intent1.putExtra("id", imImageBean.getFrom_id());
                        context.startActivity(intent1);
                        return false;
                    }
                });


                holder.ivImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //先清空再添加
                        imgUrl.clear();
                        intent = new Intent(context, NewDynamicImage.class);

                        /*if (local_path != null) {
                            if (local_path.contains(flag)) {
                                imgUrl.add(localThumbnailPath);
                            } else {
                                imgUrl.add(local_path);
                            }
                        } else {
                            imgUrl.add(localThumbnailPath);
                        }*/
                        imgUrl.add(localThumbnailPath);

                        intent.putStringArrayListExtra("img", imgUrl);
                        context.startActivity(intent);
                    }
                });

            }
            else if (message.getContentType().name().equals("video")) {
                message = list.get(position);
                ImVideoBean imVideoBean = gson.fromJson(message.toJson(), ImVideoBean.class);

                VideoContent videoContent = (VideoContent) message.getContent();
                videoContent.downloadVideoFile(message, new DownloadCompletionCallback() {
                    @Override
                    public void onComplete(int i, String s, File file) {
                        //设置缩略图
                        Bitmap netVideoBitmap = getNetVideoBitmap(file);
                        Drawable drawable = new BitmapDrawable(netVideoBitmap);

                        holder.videoView.setBackground(drawable);

                        //跳转到大图播放
                        holder.videoView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(context, SimplePlayerActivity.class);
                                intent.putExtra("url", file.getAbsolutePath());
                                context.startActivity(intent);
                            }
                        });

                    }
                });

                //视频的长按事件
                holder.videoView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        intent1 = new Intent(context, ReportActivity.class);
                        intent1.putExtra("id", imVideoBean.getFrom_id());
                        context.startActivity(intent1);
                        return false;
                    }
                });

            }
            else if (message.getContentType().name().equals("location")) {
                message = list.get(position);

                ImLocationBean imLocationBean = gson.fromJson(message.toJson(), ImLocationBean.class);

                String label = imLocationBean.getContent().getLabel();
                String[] split = label.split(",");

                holder.tvTitle.setText(split[1] + "");
                holder.tvAddress.setText(split[0] + "");

                double latitude = imLocationBean.getContent().getLatitude();
                double longitude = imLocationBean.getContent().getLongitude();

                holder.rlLocation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //跳转到查看位置页
                        Intent intent = new Intent(context, SendLocationActivity.class);
                        intent.putExtra("address", label);
                        intent.putExtra("lat", latitude);
                        intent.putExtra("lon", longitude);

                        context.startActivity(intent);
                    }
                });
            }
            else if (message.getContentType().name().equals("file")){
                message = list.get(position);

                ImMp3Bean mp3Bean = gson.fromJson(message.toJson(), ImMp3Bean.class);

                String fname = mp3Bean.getContent().getFname();
                String[] split = fname.split("!!");
                holder.tvMusicname.setText(split[0]);
                holder.tvAuthor.setText(split[2]);
                Glide.with(context).load(split[1]).into(holder.ivMusicimg);

                holder.rlPlay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       /* Uri uri = Uri.parse(split[3]);

                        MediaPlayer mediaPlayer = new MediaPlayer();
                        try {
                            mediaPlayer.setDataSource(context,uri);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }*/
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
            } else if ((message.getContentType().name().equals("location"))) {
                return 9;
            } else if (message.getContentType().name().equals("file")) {
                return 11;
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
            } else if (message.getContentType().name().equals("location")) {
                return 8;
            } else if (message.getContentType().name().equals("file")) {
                return 10;
            }
        }
        return 12;
    }

    public static Bitmap getNetVideoBitmap(File videoUrl) {
        Bitmap bitmap = null;

        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            FileInputStream inputStream = new FileInputStream(videoUrl.getAbsolutePath());
            retriever.setDataSource(inputStream.getFD());
            //获得第一帧图片
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
