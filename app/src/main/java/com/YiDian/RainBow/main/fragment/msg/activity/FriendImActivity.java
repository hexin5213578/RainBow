package com.YiDian.RainBow.main.fragment.msg.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.App;
import com.YiDian.RainBow.base.BaseAvtivity;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.base.Common;
import com.YiDian.RainBow.custom.audiorecord.AudioRecorderButton;
import com.YiDian.RainBow.main.fragment.msg.adapter.FriendImAdapter;
import com.YiDian.RainBow.main.fragment.msg.bean.ImMsgBean;
import com.YiDian.RainBow.user.bean.UserMsgBean;
import com.YiDian.RainBow.utils.KeyBoardUtils;
import com.YiDian.RainBow.utils.NetUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.leaf.library.StatusBarUtil;
import com.liaoinstan.springview.container.AliFooter;
import com.liaoinstan.springview.widget.SpringView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.options.MessageSendingOptions;
import cn.jpush.im.api.BasicCallback;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
//                       _oo0oo_
//                      o8888888o
//                      88" . "88
//                      (| -_- |)
//                      0\  =  /0
//                    ___/`---'\___
//                  .' \\|     |// '.
//                 / \\|||  :  |||// \
//                / _||||| -:- |||||- \
//               |   | \\\  -  /// |   |
//               | \_|  ''\---/''  |_/ |
//               \  .-\__  '-'  ___/-. /
//             ___'. .'  /--.--\  `. .'___
//          ."" '<  `.___\_<|>_/___.' >' "".
//         | | :  `- \`.;`\ _ /`;.`/ - ` : | |
//         \  \ `_.   \_ __\ /__ _/   .-` /  /
//     =====`-.____`.___ \_____/___.-`___.-'=====
//                       `=---='
//
//
//     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//
//               佛祖保佑         永无BUG

//聊天界面
public class FriendImActivity extends BaseAvtivity implements View.OnClickListener {
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
    @BindView(R.id.iv_headimg)
    ImageView ivHeadimg;
    @BindView(R.id.tv_role)
    TextView tvRole;
    @BindView(R.id.rl_head)
    RelativeLayout rlHead;
    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.rl_msg_user)
    RelativeLayout rlMsgUser;
    @BindView(R.id.iv_yuyin)
    ImageView ivYuyin;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.bt_confirm)
    Button btConfirm;
    @BindView(R.id.iv_jiahao)
    ImageView ivJiahao;
    @BindView(R.id.iv_keyborad)
    ImageView ivKeyborad;
    @BindView(R.id.bt_luyin)
    AudioRecorderButton btLuyin;
    @BindView(R.id.rl_sendyuyin)
    RelativeLayout rlSendyuyin;
    @BindView(R.id.rl_sendmsg)
    RelativeLayout rlSendmsg;
    private String userName;
    int page = 0;
    int size = 30;
    private Conversation conversation;
    private List<ImMsgBean> allList;
    private FriendImAdapter imAdapter;
    private LinearLayoutManager linearLayoutManager;
    private String id;
    private int userid;

    @Override
    protected int getResId() {
        return R.layout.activity_im;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void getData() {
        StatusBarUtil.setGradientColor(FriendImActivity.this, toolbar);
        StatusBarUtil.setDarkMode(FriendImActivity.this);

        ivBack.setOnClickListener(this);
        llMore.setOnClickListener(this);
        btConfirm.setOnClickListener(this);
        ivYuyin.setOnClickListener(this);
        ivJiahao.setOnClickListener(this);
        ivKeyborad.setOnClickListener(this);

        //直接取消动画
        RecyclerView.ItemAnimator animator = rcImlist.getItemAnimator();
        if (animator instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
        }

        //拿到监听
        btLuyin.setAudioFinishRecorderListener(new AudioRecorderButton.AudioFinishRecorderListener() {
            @Override
            public void onFinish(int seconds, String FilePath) {
                Log.d("xxx","拿到录音回调，路径为"+FilePath);

                File file = new File(FilePath);
                int duration = getDuration(file, FriendImActivity.this);

                Log.d("xxx","拿到语音文件的长度为"+duration);

                sendYuyinMessage(FriendImActivity.this,file,duration);

            }
        });

        allList = new ArrayList<>();

        //获取传递过来的用户信息
        Intent intent = getIntent();
        id = intent.getStringExtra("userid");

        Log.d("xxx", "传入聊天页的id为" + id);

        //通过传过来的id对应对话的对象
        conversation = JMessageClient.getSingleConversation(id);

        //设置聊天对应用户的用户名
        tvName.setText(conversation.getTitle());

        //获取当前登录的用户名 设置状态栏提示
        userName = Common.getUserName();
        userid = Integer.valueOf(Common.getUserId());

        imAdapter = new FriendImAdapter(FriendImActivity.this);
        //先获取聊天记录  获取为空展示聊天对方用户信息
        getListFromIm(page, size);

        //设置下拉加载更多聊天记录
        sv.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getListFromIm(page, size);

                        linearLayoutManager.scrollToPositionWithOffset(page - 5, 0);

                        sv.onFinishFreshAndLoad();
                    }
                }, 1000);
            }

            @Override
            public void onLoadmore() {

            }
        });


        //获取长度 如果为空不能点击发送按钮
        String s = etContent.getText().toString();
        if (TextUtils.isEmpty(s)) {
            btConfirm.setEnabled(false);
        }

        etContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s) && s.length() > 0) {
                    btConfirm.setBackground(getResources().getDrawable(R.drawable.content_bt_bg));
                    btConfirm.setEnabled(true);
                } else {
                    btConfirm.setBackground(getResources().getDrawable(R.drawable.content_bt_nodata_bg));
                    btConfirm.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    //收到消息 刷新聊天记录
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getMsg(String str) {
        if (str.equals("收到了信息")) {
            allList.clear();
            page = 0;
            size = 30;
            getListFromIm(page, size);
        }
    }

    public void getListFromIm(int page, int size) {
        List<Message> messagesFromNewest = conversation.getMessagesFromNewest(page, size);

        Log.d("xxx", "获取到了" + messagesFromNewest.size() + "条");
        if (messagesFromNewest.size() > 0 && messagesFromNewest != null) {

            rlMsgUser.setVisibility(View.GONE);
            sv.setVisibility(View.VISIBLE);
            this.page += 30;
            this.size += 30;

            sv.setHeader(new AliFooter(this));

            for (int i = 0; i < messagesFromNewest.size(); i++) {
                String s = messagesFromNewest.get(i).toJson();
                Gson gson = new Gson();
                ImMsgBean imMsgBean = gson.fromJson(s, ImMsgBean.class);

                allList.add(imMsgBean);
            }

            Log.d("xxx", "聊天记录长度为" + allList.size() + "");
            Log.d("xxx", "聊天记录第一条为" + messagesFromNewest.get(0).toJson());

            linearLayoutManager = new LinearLayoutManager(FriendImActivity.this, RecyclerView.VERTICAL, true);
            rcImlist.setLayoutManager(linearLayoutManager);

            linearLayoutManager.scrollToPositionWithOffset(0, 0);

            imAdapter = new FriendImAdapter(FriendImActivity.this);

            imAdapter.setData(allList);
            //设置对方头像
            File avatarFile = conversation.getAvatarFile();
            imAdapter.setHeadimg(avatarFile);

            rcImlist.setAdapter(imAdapter);
        } else {
            if (allList.size() > 0) {
                Toast.makeText(this, "没有更多记录了", Toast.LENGTH_SHORT).show();
            } else {
                sv.setVisibility(View.GONE);
                rlMsgUser.setVisibility(View.VISIBLE);

                //获取传递过来的用户的基本信息
                NetUtils.getInstance().getApis()
                        .doGetUserMsgById(userid, Integer.parseInt(id))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<UserMsgBean>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(UserMsgBean userMsgBean) {
                                UserMsgBean.ObjectBean bean = userMsgBean.getObject();
                                UserMsgBean.ObjectBean.UserInfoBean userInfo = bean.getUserInfo();
                                if (userMsgBean.getMsg().equals("查询成功")) {
                                    tvUsername.setText(userInfo.getNickName());
                                    //加载头像
                                    Glide.with(FriendImActivity.this).load(userInfo.getHeadImg()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(ivHeadimg);
                                    //设置角色
                                    //判断性别是否保密
                                    String userRole = userInfo.getUserRole();
                                    if (userRole.equals("保密")) {
                                        tvRole.setVisibility(View.GONE);
                                    } else {
                                        tvRole.setText(userInfo.getUserRole());
                                    }
                                }
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        });

            }
        }
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    /**
     * @param context  上下文
     * @param text     聊天文本
     */
    public void sendMessage(Context context, String text) {
        Message message = JMessageClient.createSingleTextMessage(id, Common.get_JG(), text);

        message.setOnSendCompleteCallback(new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                Log.i("TAG", "register：code：" + i + "  msg：" + s);
                if (i == 0) {
                    Toast.makeText(context, "发送成功", Toast.LENGTH_SHORT).show();
                    allList.clear();
                    page = 0;
                    size = 15;
                    getListFromIm(page, size);
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

    /**
     *
     * @param context
     * @param RecoderFile
     */
    public void sendYuyinMessage(Context context, File RecoderFile,int time) {
        Message message = null;
        try {
            message = JMessageClient.createSingleVoiceMessage(id, Common.get_JG(), RecoderFile,time);

            message.setOnSendCompleteCallback(new BasicCallback() {
                @Override
                public void gotResult(int i, String s) {
                    Log.i("TAG", "register：code：" + i + "  msg：" + s);
                    if (i == 0) {
                        Toast.makeText(context, "发送成功", Toast.LENGTH_SHORT).show();
                        allList.clear();
                        page = 0;
                        size = 15;
                        getListFromIm(page, size);
                    } else {
                        Toast.makeText(context, "发送失败", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            MessageSendingOptions options = new MessageSendingOptions();

            options.setNotificationTitle(userName);
            options.setNotificationText("语音消息");
            options.setCustomNotificationEnabled(true);
            options.setRetainOffline(true);
            options.setShowNotification(true);
            JMessageClient.sendMessage(message, options);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                EventBus.getDefault().post("收到了信息");
                break;
            case R.id.ll_more:
                //跳转到设置页
                break;
            case R.id.bt_confirm:
                String s = etContent.getText().toString();

                sendMessage(FriendImActivity.this, s);
                etContent.setText("");
                KeyBoardUtils.closeKeyboard(etContent);
                break;
            case R.id.iv_yuyin:
                rlSendmsg.setVisibility(View.GONE);
                rlSendyuyin.setVisibility(View.VISIBLE);
                KeyBoardUtils.closeKeyboard(etContent);
                break;
            case R.id.iv_jiahao:

                break;
            case R.id.iv_keyborad:
                rlSendmsg.setVisibility(View.VISIBLE);
                rlSendyuyin.setVisibility(View.GONE);
                KeyBoardUtils.openKeyBoard(etContent);
                break;
        }
    }
    public static int getDuration(File source,Context context) {
        int duration = 0;
        Uri uri = Uri.fromFile(source);
        MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(context, uri);
            mediaPlayer.prepare();
            duration = mediaPlayer.getDuration();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return duration / 1000;

    }
}
