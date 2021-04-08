package com.YiDian.RainBow.main.fragment.msg.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.main.fragment.msg.activity.FriendImActivity;
import com.YiDian.RainBow.topic.SaveIntentMsgBean;
import com.YiDian.RainBow.user.PersonHomeActivity;
import com.YiDian.RainBow.utils.StringUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetGroupInfoCallback;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.GroupInfo;
import cn.jpush.im.android.api.model.UserInfo;

public class MsgRecordingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context context;
    private List<Conversation> list;
    private Conversation conversation;
    private String targetId;
    private File avatarFile;


    public MsgRecordingAdapter(Context context) {

        this.context = context;
    }

    public void setData(List<Conversation> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_msg_recording, null);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        conversation = list.get(position);
        //判断聊天对象是否为群组
        if (conversation.getType().name().equals("group")){
            targetId = conversation.getTargetId();

            JMessageClient.getGroupInfo(Long.parseLong(targetId), new GetGroupInfoCallback() {
                @Override
                public void gotResult(int i, String s, GroupInfo groupInfo) {
                    if (i==0){
                        avatarFile = groupInfo.getAvatarFile();

                        Glide.with(context).load(avatarFile).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(((ViewHolder) holder).ivHeadimg);
                    }
                }
            });
            //跳转到群信息页
            ((ViewHolder) holder).ivHeadimg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO: 2021/3/12 先判断加入还是创建 再跳转至详细信息页

                }
            });
            ((ViewHolder)holder).rlItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO: 2021/1/8 0008  跳转至聊天详情页
                    Conversation conversation = list.get(position);

                    //进入会话
                    JMessageClient.enterGroupConversation(Long.parseLong(conversation.getTargetId()));
                    //发送到聊天详情页
                    Intent intent = new Intent(context, FriendImActivity.class);
                    intent.putExtra("userid",conversation.getTargetId()+"");
                    context.startActivity(intent);
                }
            });
        }else{
            targetId = conversation.getTargetId();
            JMessageClient.getUserInfo(targetId, "87ce5706efafab51ddd2be08", new GetUserInfoCallback() {
                @Override
                public void gotResult(int i, String s, UserInfo userInfo) {
                    if (i == 0) {
                         avatarFile = userInfo.getAvatarFile();

                        Log.d("xxx", "获取用户信息成功,用户头像为" + avatarFile);
                        // TODO: 2021/1/14 0014 头像为空加载默认
                        if (avatarFile == null) {
                            Glide.with(context).load(R.mipmap.headimg3).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(((ViewHolder) holder).ivHeadimg);
                        } else {
                            Glide.with(context).load(avatarFile).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(((ViewHolder) holder).ivHeadimg);
                        }
                    }
                }
            });
            //跳转到用户信息页
            ((ViewHolder) holder).ivHeadimg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    conversation = list.get(position);

                    Intent intent = new Intent(context, PersonHomeActivity.class);
                    SaveIntentMsgBean saveIntentMsgBean = new SaveIntentMsgBean();
                    saveIntentMsgBean.setId(Integer.parseInt(conversation.getTargetId()));
                    //2标记传入姓名  1标记传入id
                    saveIntentMsgBean.setFlag(1);
                    intent.putExtra("msg", saveIntentMsgBean);
                    context.startActivity(intent);
                }
            });
            ((ViewHolder)holder).rlItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO: 2021/1/8 0008  跳转至聊天详情页

                    Conversation conversation = list.get(position);

                    //进入会话
                    JMessageClient.enterSingleConversation(conversation.getTargetId());
                    //发送到聊天详情页
                    Intent intent = new Intent(context, FriendImActivity.class);
                    intent.putExtra("userid",conversation.getTargetId());
                    intent.putExtra("name",conversation.getTitle()+"");
                    EventBus.getDefault().post("刷新消息列表");

                    context.startActivity(intent);
                }
            });
        }

        ((ViewHolder) holder).tvUsername.setText(conversation.getTitle());
        if (conversation.getLatestType().name().equals("voice")) {
            ((ViewHolder) holder).tvLastMsg.setText("[语音消息]");

        } else if (conversation.getLatestType().name().equals("text")) {

            ((ViewHolder) holder).tvLastMsg.setText(conversation.getLatestText());
        } else if (conversation.getLatestType().name().equals("image")) {

            ((ViewHolder) holder).tvLastMsg.setText("[图片消息]");

        } else if (conversation.getLatestType().name().equals("video")) {

            ((ViewHolder) holder).tvLastMsg.setText("[视频消息]");
        }else if (conversation.getLatestType().name().equals("location")){

            ((ViewHolder) holder).tvLastMsg.setText("[位置消息]");
        }else if (conversation.getLatestType().name().equals("file")){

            ((ViewHolder) holder).tvLastMsg.setText("[文件消息]");
        }

        long lastMsgDate = conversation.getLastMsgDate();

        String newChatTime = StringUtil.getNewChatTime(lastMsgDate);

        ((ViewHolder) holder).tvTime.setText(newChatTime);

        //设置未读消息数
        int unReadMsgCnt = conversation.getUnReadMsgCnt();
        if (unReadMsgCnt == 0) {
            ((ViewHolder) holder).weiducount.setVisibility(View.GONE);
        } else {
            ((ViewHolder) holder).weiducount.setVisibility(View.VISIBLE);
            ((ViewHolder) holder).weiducount.setText(unReadMsgCnt + "");
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_headimg)
        ImageView ivHeadimg;
        @BindView(R.id.tv_username)
        TextView tvUsername;
        @BindView(R.id.tv_last_msg)
        TextView tvLastMsg;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.weiducount)
        TextView weiducount;
        @BindView(R.id.rl_item)
        RelativeLayout rlItem;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
