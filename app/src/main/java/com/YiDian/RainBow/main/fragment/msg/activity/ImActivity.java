package com.YiDian.RainBow.main.fragment.msg.activity;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseAvtivity;
import com.YiDian.RainBow.base.BasePresenter;

import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.content.CustomContent;
import cn.jpush.im.android.api.content.EventNotificationContent;
import cn.jpush.im.android.api.content.ImageContent;
import cn.jpush.im.android.api.content.MessageContent;
import cn.jpush.im.android.api.content.PromptContent;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.content.VoiceContent;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.event.OfflineMessageEvent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.options.MessageSendingOptions;
import cn.jpush.im.api.BasicCallback;

//聊天界面
public class ImActivity extends BaseAvtivity {
    final String APP_KEY = "87ce5706efafab51ddd2be08";
    private Conversation mConversation;

    @Override
    protected int getResId() {
        return R.layout.activity_im;
    }

    @Override
    protected void getData() {
        //createConversation("1038");
        //sendMessage(ImActivity.this,"1038","奥术大师大所大asd");

    /*    List<Conversation> conversationList = JMessageClient.getConversationList();

        if (conversationList.size()>0){

            Log.d("xxx",conversationList.size()+"");

            Log.d("xxx",conversationList.get(0).toJsonString());

        }*/

    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    /**
     * 创建单聊会话
     * 参数说明
     * String username 会话对象的username.
     * String appkey 用户所属应用的appkey,如果填空则默认为本应用的appkey
     */
    public void createConversation(String userName) {
        Conversation singleConversation = Conversation.createSingleConversation(userName, APP_KEY);
        mConversation = singleConversation;

    }
    /**
     * 发送消息     使用默认的配置参数发送
     * 参数说明
     * Message message 消息对象
     */
    public void sendMessage(Context context, String userName, String text) {
        Message message = JMessageClient.createSingleTextMessage(userName, APP_KEY, text);

        message.setOnSendCompleteCallback(new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                Log.i("TAG", "register：code：" + i + "  msg：" + s);
                if (i == 0) {
                    Toast.makeText(context, "发送成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "发送失败", Toast.LENGTH_SHORT).show();
                }
            }
        });

        MessageSendingOptions options = new MessageSendingOptions();

        options.setNotificationTitle("李老四");
        options.setNotificationText(text);
        options.setCustomNotificationEnabled(true);
        options.setRetainOffline(true);
        options.setShowNotification(true);
        JMessageClient.sendMessage(message,options);
    }
}
