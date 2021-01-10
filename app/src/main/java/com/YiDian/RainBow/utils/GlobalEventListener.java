package com.YiDian.RainBow.utils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.content.CustomContent;
import cn.jpush.im.android.api.content.EventNotificationContent;
import cn.jpush.im.android.api.content.ImageContent;
import cn.jpush.im.android.api.content.PromptContent;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.content.VoiceContent;
import cn.jpush.im.android.api.enums.ConversationType;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.event.NotificationClickEvent;
import cn.jpush.im.android.api.event.OfflineMessageEvent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.GroupInfo;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;

/**
 * 在demo中对于通知栏点击事件和在线消息接收事件，我们都直接在全局监听
 */
public class GlobalEventListener {
    private Context appContext;

    public GlobalEventListener(Context context) {
        appContext = context;
        JMessageClient.registerEventReceiver(this);
        Log.i("xxx", "GlobalEventListener");

    }

    public void onEvent(OfflineMessageEvent event) {
        List<Message> msgs = event.getOfflineMessageList();

            Log.i("xxx", "离线消息:" + msgs.size()+"条");

            EventBus.getDefault().postSticky("收到了信息");
    }
    public void onEvent(MessageEvent event) {

        Message msg = event.getMessage();
        checkMessage(msg);
        //jumpToActivity(event.getMessage());

    }
    private void checkMessage(Message msg) {
        switch (msg.getContentType()) {
            case text:
                //处理文字消息
                TextContent textContent = (TextContent) msg.getContent();
                final String message = textContent.getText();

                Log.d("xxx","收到了文本信息"+message);

                EventBus.getDefault().postSticky("收到了信息");

                break;
            case image:
                //处理图片消息
                ImageContent imageContent = (ImageContent) msg.getContent();
                imageContent.getLocalPath();//图片本地地址
                imageContent.getLocalThumbnailPath();//图片对应缩略图的本地地址

                Log.d("xxx","收到了图片消息");

                EventBus.getDefault().postSticky("收到了信息");

                break;
            case voice:
                //处理语音消息
                VoiceContent voiceContent = (VoiceContent) msg.getContent();
                voiceContent.getLocalPath();//语音文件本地地址
                voiceContent.getDuration();//语音文件时长

                Log.d("xxx","收到了语音消息");

                EventBus.getDefault().postSticky("收到了信息");

                break;
            case custom:
                //处理自定义消息
                CustomContent customContent = (CustomContent) msg.getContent();
                customContent.getNumberValue("custom_num"); //获取自定义的值
                customContent.getBooleanValue("custom_boolean");
                customContent.getStringValue("custom_string");

                Log.d("xxx","自定义消息");

                EventBus.getDefault().postSticky("收到了信息");

                break;
            case eventNotification:
                //处理事件提醒消息
                EventNotificationContent eventNotificationContent = (EventNotificationContent) msg.getContent();
                switch (eventNotificationContent.getEventNotificationType()) {
                    case group_member_added:
                        //群成员加群事件
                        break;
                    case group_member_removed:
                        //群成员被踢事件
                        break;
                    case group_member_exit:
                        //群成员退群事件
                        break;
                    case group_info_updated://since 2.2.1
                        //群信息变更事件
                        break;
                }
                break;
            case unknown:
                // 处理未知消息，未知消息的Content为PromptContent 默认提示文本为“当前版本不支持此类型消息，请更新sdk版本”，上层可选择不处理
                PromptContent promptContent = (PromptContent) msg.getContent();
                promptContent.getPromptType();//未知消息的type是unknown_msg_type
                promptContent.getPromptText();//提示文本，“当前版本不支持此类型消息，请更新sdk版本”
                break;
        }
    }


   /* public void onEvent(NotificationClickEvent event) {
        jumpToActivity(event.getMessage());
    }
    */
   /* private void jumpToActivity(Message msg) {
        UserInfo fromUser = msg.getFromUser();
        //final Intent notificationIntent = new Intent(appContext, ShowMessageActivity.class);
        //notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        String userName = fromUser.getUserName();
        String appKey = fromUser.getAppKey();
        int msgId = msg.getId();
        Conversation conversation = JMessageClient.getSingleConversation(userName, appKey);
        Message message = conversation.getMessage(msgId);
        TextContent textContent = (TextContent) msg.getContent();
        String text = textContent.getText();
        Toast.makeText(appContext, "消息内容:" + text, Toast.LENGTH_SHORT).show();
      *//*  if (msg.getTargetType() == ConversationType.group) {
            GroupInfo groupInfo = (GroupInfo) msg.getTargetInfo();
            notificationIntent.putExtra(ShowMessageActivity.EXTRA_IS_GROUP, true);
            notificationIntent.putExtra(ShowMessageActivity.EXTRA_GROUPID, groupInfo.getGroupID());
        } else {
            notificationIntent.putExtra(ShowMessageActivity.EXTRA_IS_GROUP, false);
        }*//*

        *//*notificationIntent.putExtra(ShowMessageActivity.EXTRA_FROM_USERNAME, fromUser.getUserName());
        notificationIntent.putExtra(ShowMessageActivity.EXTRA_FROM_APPKEY, fromUser.getAppKey());
        notificationIntent.putExtra(ShowMessageActivity.EXTRA_MSG_TYPE, msg.getContentType().toString());
        notificationIntent.putExtra(ShowMessageActivity.EXTRA_MSGID, msg.getId());*//*
        //appContext.startActivity(notificationIntent);
    }*/
}
