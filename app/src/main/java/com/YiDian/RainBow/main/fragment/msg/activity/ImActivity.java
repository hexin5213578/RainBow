package com.YiDian.RainBow.main.fragment.msg.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseAvtivity;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.base.Common;
import com.YiDian.RainBow.main.fragment.msg.adapter.ImAdapter;
import com.YiDian.RainBow.main.fragment.msg.bean.ImMsgBean;
import com.google.gson.Gson;
import com.leaf.library.StatusBarUtil;
import com.liaoinstan.springview.container.AliFooter;
import com.liaoinstan.springview.widget.SpringView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.options.MessageSendingOptions;
import cn.jpush.im.api.BasicCallback;

//聊天界面
public class ImActivity extends BaseAvtivity implements View.OnClickListener {
    final String APP_KEY = "87ce5706efafab51ddd2be08";
    @BindView(R.id.rc_imlist)
    RecyclerView rcImlist;
    @BindView(R.id.sv)
    SpringView sv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_back)
    LinearLayout ivBack;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.ll_more)
    LinearLayout llMore;
    private Conversation mConversation;
    private String userName;
    int page = 0;
    int size = 15;
    private Conversation conversation;
    private List<ImMsgBean> allList;
    private ImAdapter imAdapter;

    @Override
    protected int getResId() {
        return R.layout.activity_im;
    }

    @Override
    protected void getData() {
        StatusBarUtil.setGradientColor(ImActivity.this,toolbar);
        StatusBarUtil.setDarkMode(ImActivity.this);

        ivBack.setOnClickListener(this);
        llMore.setOnClickListener(this);

        allList = new ArrayList<>();

        Intent intent = getIntent();

        String userid = intent.getStringExtra("userid");

        conversation = JMessageClient.getSingleConversation(userid);

        tvName.setText(conversation.getTitle());

        userName = Common.getUserName();

        imAdapter = new ImAdapter(ImActivity.this);

        getListFromIm(page, size);

        sv.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getListFromIm(page,size);

                        sv.onFinishFreshAndLoad();
                    }
                }, 1000);
            }

            @Override
            public void onLoadmore() {

            }
        });

      /*  createConversation("1038");
        sendMessage(ImActivity.this,"1038","健康阿达asasd");*/


    }

    public void getListFromIm(int page, int size) {
        List<Message> messagesFromNewest = conversation.getMessagesFromNewest(page, size);

        Log.d("xxx","获取到了"+messagesFromNewest.size()+"条");
        if (messagesFromNewest.size()>0 && messagesFromNewest!=null){
            this.page+=15;
            this.size+=15;

            for (int i =0;i<messagesFromNewest.size();i++){
                String s = messagesFromNewest.get(i).toJson();
                Gson gson = new Gson();
                ImMsgBean imMsgBean = gson.fromJson(s, ImMsgBean.class);

                allList.add(imMsgBean);
            }
            Log.d("xxx","聊天记录长度为"+allList.size()+"");
            Log.d("xxx","聊天记录第一条为"+messagesFromNewest.get(0).toJson());

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ImActivity.this, RecyclerView.VERTICAL, true);
            rcImlist.setLayoutManager(linearLayoutManager);

            linearLayoutManager.scrollToPositionWithOffset(0,0);

            imAdapter = new ImAdapter(ImActivity.this);

            imAdapter.setData(allList);
            //设置对方头像
            File avatarFile = conversation.getAvatarFile();
            imAdapter.setHeadimg(avatarFile);

            rcImlist.setAdapter(imAdapter);
        }else{
            Toast.makeText(this, "没有更多内容了", Toast.LENGTH_SHORT).show();
        }

        if (messagesFromNewest.size()>4){
            sv.setHeader(new AliFooter(this));
        }

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

        options.setNotificationTitle(userName);
        options.setNotificationText(text);
        options.setCustomNotificationEnabled(true);
        options.setRetainOffline(true);
        options.setShowNotification(true);
        JMessageClient.sendMessage(message, options);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.ll_more:
                //跳转到设置页
                break;
        }
    }
}
