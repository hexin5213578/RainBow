package com.YiDian.RainBow.main.fragment.msg.adapter;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.main.fragment.home.adapter.ViewHolder;
import com.YiDian.RainBow.main.fragment.msg.bean.ImMsgBean;
import com.YiDian.RainBow.main.fragment.msg.bean.ImVocieBean;
import com.YiDian.RainBow.utils.StringUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.IOException;
import java.util.List;

import cn.jpush.im.android.api.model.Message;

import static cn.jpush.im.android.api.jmrtc.JMRTCInternalUse.getApplicationContext;


public class FriendImAdapter extends RecyclerView.Adapter<ImViewHolder> {


    private List<Message> list;
    private Context context;
    private File file;
    private String newChatTime;
    private Message message;
    private Gson gson;
    private MediaPlayer mediaPlayer;
    private ImViewHolder viewHolder;

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
            //接收纯文本
            viewHolder =ImViewHolder.createViewHolder(context, parent, R.layout.item_im_left);
            return viewHolder;
        }
        if (viewType == 1) {
            //接收语音
            viewHolder = ImViewHolder.createViewHolder(context, parent, R.layout.item_im_left_voice);
            return viewHolder;
        }
        if (viewType == 2) {
            //接收图片
        }
        if (viewType == 3) {
            //接收视频
        }
        if (viewType == 4) {
            //发送文本
            viewHolder  =ImViewHolder.createViewHolder(context, parent, R.layout.item_im_right);
            return viewHolder;
        }
        if (viewType == 5) {
            //发送语音
            viewHolder= ImViewHolder.createViewHolder(context, parent, R.layout.item_im_right_voice);
            return viewHolder;
        }
        if (viewType == 6) {
            //发送图片
        }
        if (viewType == 7) {
            //发送视频
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ImViewHolder holder, int position) {

        message = list.get(position);
        mediaPlayer = new MediaPlayer();

        if (position != list.size() - 1) {
            Message imMsgBean = list.get(position + 1);

            newChatTime = StringUtil.getNewChatTime(message.getCreateTime());
            String newChatTime2 = StringUtil.getNewChatTime(imMsgBean.getCreateTime());

            if (newChatTime.length() < 6) {
                String[] split = newChatTime.split(":");
                String[] split1 = newChatTime2.split(":");

                if (split1[1].equals(split[1]) || split1[1].equals((Integer.valueOf(split[0])-1)) || split1[1].equals((Integer.valueOf(split[0])+1))) {
                    holder.tvTime.setVisibility(View.GONE);
                }else{
                    holder.tvTime.setVisibility(View.VISIBLE);
                    holder.tvTime.setText(newChatTime);
                }
            }
        }
        holder.tvTime.setText(newChatTime);

        if (message.getDirect().name().equals("receive")) {
            Glide.with(context).load(file).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(holder.ivHeadimg);
        } else {
            Glide.with(context).load(R.mipmap.headimg).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(holder.ivHeadimg);
        }


        gson = new Gson();
        if (message.getContentType().name().equals("voice")) {
            ImVocieBean imVocieBean = gson.fromJson(message.toJson(), ImVocieBean.class);
            holder.tvDuration.setText(imVocieBean.getContent().getDuration()+"'");
            holder.llVocie.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mediaPlayer.isPlaying()){
                        mediaPlayer.stop();
                    }else{
                        //播放声音
                        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                        try {
                            mediaPlayer.setDataSource(getApplicationContext(), Uri.parse(imVocieBean.getContent().getLocal_path()));
                            mediaPlayer.prepare();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        mediaPlayer.start();
                    }
                }
            });
        } else if (message.getContentType().name().equals("text")) {

            ImMsgBean imMsgBean = gson.fromJson(message.toJson(), ImMsgBean.class);

            //获取文本内容
            holder.tvMsg.setText(imMsgBean.getContent().getText());

        } else if (message.getContentType().name().equals("image")) {

        } else if (message.getContentType().name().equals("video")) {

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
}
