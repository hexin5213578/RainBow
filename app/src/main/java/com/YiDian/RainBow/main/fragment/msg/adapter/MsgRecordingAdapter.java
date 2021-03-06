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

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.UserInfo;

public class MsgRecordingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context context;
    private List<Conversation> list;
    private Conversation conversation;


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

        String targetId = conversation.getTargetId();
        JMessageClient.getUserInfo(targetId, "87ce5706efafab51ddd2be08", new GetUserInfoCallback() {
            @Override
            public void gotResult(int i, String s, UserInfo userInfo) {
                if (i == 0) {
                    File avatarFile = userInfo.getAvatarFile();

                    Log.d("xxx", "????????????????????????,???????????????" + avatarFile);
                    // TODO: 2021/1/14 0014 ????????????????????????
                    if (avatarFile == null) {
                        Glide.with(context).load(R.mipmap.headimg3).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(((ViewHolder) holder).ivHeadimg);
                    } else {
                        Glide.with(context).load(avatarFile).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(((ViewHolder) holder).ivHeadimg);
                    }
                }
            }
        });
        //????????????????????????
        ((ViewHolder) holder).ivHeadimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                conversation = list.get(position);

                Intent intent = new Intent(context, PersonHomeActivity.class);
                SaveIntentMsgBean saveIntentMsgBean = new SaveIntentMsgBean();
                saveIntentMsgBean.setId(Integer.parseInt(conversation.getTargetId()));
                //2??????????????????  1????????????id
                saveIntentMsgBean.setFlag(1);
                intent.putExtra("msg", saveIntentMsgBean);
                context.startActivity(intent);
            }
        });
        ((ViewHolder)holder).rlItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2021/1/8 0008  ????????????????????????

                Conversation conversation = list.get(position);

                //????????????
                JMessageClient.enterSingleConversation(conversation.getTargetId());

                //????????????????????????
                Intent intent = new Intent(context, FriendImActivity.class);
                intent.putExtra("userid",conversation.getTargetId());
                context.startActivity(intent);
            }
        });
        ((ViewHolder) holder).tvUsername.setText(conversation.getTitle());
        if (conversation.getLatestType().name().equals("voice")) {
            ((ViewHolder) holder).tvLastMsg.setText("[????????????]");

        } else if (conversation.getLatestType().name().equals("text")) {

            ((ViewHolder) holder).tvLastMsg.setText(conversation.getLatestText());
        } else if (conversation.getLatestType().name().equals("image")) {

            ((ViewHolder) holder).tvLastMsg.setText("[????????????]");

        } else if (conversation.getLatestType().name().equals("video")) {

            ((ViewHolder) holder).tvLastMsg.setText("[????????????]");
        }

        long lastMsgDate = conversation.getLastMsgDate();

        String newChatTime = StringUtil.getNewChatTime(lastMsgDate);

        ((ViewHolder) holder).tvTime.setText(newChatTime);

        //?????????????????????
        int unReadMsgCnt = conversation.getUnReadMsgCnt();
        if (unReadMsgCnt == 0) {
            ((ViewHolder) holder).weiducount.setVisibility(View.GONE);
        } else {
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
