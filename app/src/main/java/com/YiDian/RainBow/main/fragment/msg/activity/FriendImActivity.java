package com.YiDian.RainBow.main.fragment.msg.activity;

import android.Manifest;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseAvtivity;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.base.Common;
import com.YiDian.RainBow.custom.audiorecord.AudioRecorderButton;
import com.YiDian.RainBow.dynamic.activity.DevelopmentDynamicActivity;
import com.YiDian.RainBow.dynamic.adapter.DevelogmentImgAdapter;
import com.YiDian.RainBow.main.fragment.msg.adapter.FriendImAdapter;
import com.YiDian.RainBow.user.bean.UserMsgBean;
import com.YiDian.RainBow.utils.KeyBoardUtils;
import com.YiDian.RainBow.utils.NetUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.dmcbig.mediapicker.PickerActivity;
import com.dmcbig.mediapicker.PickerConfig;
import com.dmcbig.mediapicker.entity.Media;
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
    @BindView(R.id.iv_paizhao)
    ImageView ivPaizhao;
    @BindView(R.id.iv_xiangce)
    ImageView ivXiangce;
    @BindView(R.id.iv_liwu)
    ImageView ivLiwu;
    @BindView(R.id.rl_menu)
    RelativeLayout rlMenu;
    @BindView(R.id.rl1)
    RelativeLayout rl1;
    @BindView(R.id.v1)
    View v1;
    @BindView(R.id.rl_layout)
    RelativeLayout rlLayout;
    @BindView(R.id.rl_bottom)
    RelativeLayout rlBottom;
    private String userName;
    private ArrayList<Media> select;
    private ArrayList<Media> select1;
    int page = 0;
    int size = 50;
    private Conversation conversation;
    private List<Message> allList;
    private FriendImAdapter imAdapter;
    private LinearLayoutManager linearLayoutManager;
    private String id;
    private int userid;
    boolean isfirst = true;
    private PopupWindow mPopupWindow1;

    @Override
    protected int getResId() {
        return R.layout.activity_im;
    }

    /**
     * addLayoutListener方法如下
     *
     * @param main   根布局
     * @param scroll 需要显示的最下方View
     */
    public void addLayoutListener(final View main, final View scroll) {
        main.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                //1、获取main在窗体的可视区域
                main.getWindowVisibleDisplayFrame(rect);
                //2、获取main在窗体的不可视区域高度，在键盘没有弹起时，main.getRootView().getHeight()调节度应该和rect.bottom高度一样
                int mainInvisibleHeight = main.getRootView().getHeight() - rect.bottom;
                int screenHeight = main.getRootView().getHeight();//屏幕高度
                //3、不可见区域大于屏幕本身高度的1/4：说明键盘弹起了
                if (mainInvisibleHeight > screenHeight / 4) {
                    int[] location = new int[2];
                    scroll.getLocationInWindow(location);
                    // 4､获取Scroll的窗体坐标，算出main需要滚动的高度
                    int srollHeight = (location[1] + scroll.getHeight()) - rect.bottom;
                    //5､让界面整体上移键盘的高度
                    main.scrollTo(0, srollHeight);
                } else {
                    //3、不可见区域小于屏幕高度1/4时,说明键盘隐藏了，把界面下移，移回到原有高度
                    main.scrollTo(0, 0);
                }
            }
        });
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
        ivPaizhao.setOnClickListener(this);
        ivXiangce.setOnClickListener(this);
        ivLiwu.setOnClickListener(this);
        etContent.setOnClickListener(this);

        select = new ArrayList<>();
        select1 = new ArrayList<>();

        Request();

        if (Build.VERSION.SDK_INT >= 23) {
            int REQUEST_CODE_CONTACT = 101;
            String[] permissions = {
                    Manifest.permission.WRITE_EXTERNAL_STORAGE};
            //验证是否许可权限
            for (String str : permissions) {
                if (FriendImActivity.this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    //申请权限
                    FriendImActivity.this.requestPermissions(permissions, REQUEST_CODE_CONTACT);
                    return;
                } else {
                    //这里就是权限打开之后自己要操作的逻辑
                }
            }
        }
        addLayoutListener(rlLayout, rlBottom);
        //直接取消动画
        RecyclerView.ItemAnimator animator = rcImlist.getItemAnimator();
        if (animator instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
        }
        //拿到监听
        btLuyin.setAudioFinishRecorderListener(new AudioRecorderButton.AudioFinishRecorderListener() {
            @Override
            public void onFinish(int seconds, String FilePath) {
                Log.d("xxx", "拿到录音回调，路径为" + FilePath);

                File file = new File(FilePath);
                int duration = getDuration(file, FriendImActivity.this);

                Log.d("xxx", "拿到语音文件的长度为" + duration);

                sendYuyinMessage(FriendImActivity.this, file, duration);

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
            size = 50;
            getListFromIm(page, size);
        }
    }

    public void getListFromIm(int page, int size) {
        List<Message> messagesFromNewest = conversation.getMessagesFromNewest(page, size);

        Log.d("xxx", "获取到了" + messagesFromNewest.size() + "条");
        if (messagesFromNewest.size() > 0 && messagesFromNewest != null) {

            rlMsgUser.setVisibility(View.GONE);
            sv.setVisibility(View.VISIBLE);
            this.page += 50;
            this.size += 50;

            sv.setHeader(new AliFooter(this));

            allList.addAll(messagesFromNewest);

            Log.d("xxx", "聊天记录长度为" + allList.size() + "");
            Log.d("xxx", "聊天记录第一条为" + messagesFromNewest.get(0).toJson());

            linearLayoutManager = new LinearLayoutManager(FriendImActivity.this, RecyclerView.VERTICAL, true);
            rcImlist.setLayoutManager(linearLayoutManager);
            linearLayoutManager.setStackFromEnd(true);
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
     * @param context 上下文
     * @param text    聊天文本
     */
    public void sendMessage(Context context, String text) {
        Message message = JMessageClient.createSingleTextMessage(id, Common.get_JG(), text);

        message.setOnSendCompleteCallback(new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                Log.i("TAG", "register：code：" + i + "  msg：" + s);
                if (i == 0) {
                    Log.d("xxx","发送成功");

                    allList.clear();
                    page = 0;
                    size = 50;
                    getListFromIm(page, size);
                } else {
                    Log.d("xxx","发送失败");
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
     * @param context
     * @param RecoderFile
     */
    public void sendYuyinMessage(Context context, File RecoderFile, int time) {
        Message message = null;
        try {
            message = JMessageClient.createSingleVoiceMessage(id, Common.get_JG(), RecoderFile, time);

            message.setOnSendCompleteCallback(new BasicCallback() {
                @Override
                public void gotResult(int i, String s) {
                    Log.i("TAG", "register：code：" + i + "  msg：" + s);
                    if (i == 0) {
                        Log.d("xxx","发送成功");

                        allList.clear();
                        page = 0;
                        size = 50;
                        getListFromIm(page, size);
                    } else {
                        Log.d("xxx","发送失败");

                    }
                }
            });

            MessageSendingOptions options = new MessageSendingOptions();

            options.setNotificationTitle(userName);
            options.setNotificationText("[语音信息]");
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

                //发送文本信息
                sendMessage(FriendImActivity.this, s);
                etContent.setText("");
                //发送成功关闭键盘
                KeyBoardUtils.closeKeyboard(etContent);
                break;
            case R.id.iv_yuyin:
                //展示语音录入框
                rlSendmsg.setVisibility(View.GONE);
                rlSendyuyin.setVisibility(View.VISIBLE);

                rlMenu.setVisibility(View.GONE);
                //关闭键盘
                KeyBoardUtils.closeKeyboard(etContent);
                break;
            case R.id.iv_jiahao:
                if (isfirst) {
                    KeyBoardUtils.closeKeyboard(etContent);
                    rlMenu.setVisibility(View.VISIBLE);

                    isfirst = false;
                } else {

                    rlMenu.setVisibility(View.GONE);
                    isfirst = true;
                }
                break;
            //调起相机拍照
            case R.id.iv_paizhao:
                //跳转到拍照摄像页
                int request = ContextCompat.checkSelfPermission(FriendImActivity.this, Manifest.permission.CAMERA);
                if (request != PackageManager.PERMISSION_GRANTED)//缺少权限，进行权限申请
                {
                    Toast.makeText(this, "相机权限未开启", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(FriendImActivity.this,JCameraViewActivity.class);
                    startActivity(intent);
                }
                break;
            //调起相册
            case R.id.iv_xiangce:
                showSelectImgAndVideo();
                break;
            //展示礼物选择框
            case R.id.iv_liwu:

                break;
            case R.id.iv_keyborad:
                rlSendmsg.setVisibility(View.VISIBLE);
                rlSendyuyin.setVisibility(View.GONE);
                break;
            case R.id.et_content:
                rlMenu.setVisibility(View.GONE);

                break;
        }
    }

    public static int getDuration(File source, Context context) {
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
    //安卓10.0定位权限
    public void Request() {
        if (Build.VERSION.SDK_INT >= 23) {
            int request = ContextCompat.checkSelfPermission(FriendImActivity.this, Manifest.permission.CAMERA);
            if (request != PackageManager.PERMISSION_GRANTED)//缺少权限，进行权限申请
            {
                ActivityCompat.requestPermissions(FriendImActivity.this, new String[]{Manifest.permission.CAMERA}, 100);
                return;//
            } else {

            }
        } else {

        }
    }
    //弹出选择图片视频的弹出框
    public void showSelectImgAndVideo() {
        //添加成功后处理
        mPopupWindow1 = new PopupWindow();
        mPopupWindow1.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow1.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        View view = LayoutInflater.from(FriendImActivity.this).inflate(R.layout.dialog_selector_resoues_way, null);

        RelativeLayout rl_img = view.findViewById(R.id.tv_img);
        RelativeLayout rl_video = view.findViewById(R.id.tv_video);
        RelativeLayout rl_cancle = view.findViewById(R.id.tv_cancle);

        rl_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //装被选中的文件
                select.clear();

                Intent intent = new Intent(FriendImActivity.this, PickerActivity.class);
                intent.putExtra(PickerConfig.SELECT_MODE, PickerConfig.PICKER_IMAGE);//设置选择类型，默认是图片和视频可一起选择(非必填参数)
                long maxSize = 10485760L;//long long long long类型
                intent.putExtra(PickerConfig.MAX_SELECT_SIZE, maxSize);
                intent.putExtra(PickerConfig.MAX_SELECT_COUNT, 9); //最大选择数量，默认40（非必填参数）
                FriendImActivity.this.startActivityForResult(intent, 201);

                dismiss();
            }
        });
        rl_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //装被选中的文件
                select1.clear();

                Intent intent = new Intent(FriendImActivity.this, PickerActivity.class);
                intent.putExtra(PickerConfig.SELECT_MODE, PickerConfig.PICKER_VIDEO);//设置选择类型，默认是图片和视频可一起选择(非必填参数)
                long maxSize = 104857600;//long long long long类型
                intent.putExtra(PickerConfig.MAX_SELECT_SIZE, maxSize);
                intent.putExtra(PickerConfig.MAX_SELECT_COUNT, 1); //最大选择数量，默认40（非必填参数）
                FriendImActivity.this.startActivityForResult(intent, 200);
                dismiss();
            }
        });
        rl_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        //popwindow设置属性
        mPopupWindow1.setContentView(view);
        mPopupWindow1.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow1.setFocusable(true);
        mPopupWindow1.setOutsideTouchable(true);
        mPopupWindow1.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setWindowAlpa(false);
            }
        });
        show1(view);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data!=null){
            if (requestCode == 200 && resultCode == PickerConfig.RESULT_CODE) {

                if (data.getParcelableArrayListExtra(PickerConfig.EXTRA_RESULT).size() > 0) {
                    select1 = data.getParcelableArrayListExtra(PickerConfig.EXTRA_RESULT);
                }
                Log.i("select", "视频长度为" + select1.size());

            } else if (requestCode == 201 && resultCode == PickerConfig.RESULT_CODE) {

                if (data.getParcelableArrayListExtra(PickerConfig.EXTRA_RESULT).size() > 0) {
                    select = data.getParcelableArrayListExtra(PickerConfig.EXTRA_RESULT);
                }
                Log.i("select", "图片长度为" + select.size());

            }
        }
    }

    //设置透明度
    public void setWindowAlpa(boolean isopen) {
        if (Build.VERSION.SDK_INT < 11) {
            return;
        }

        final Window window = FriendImActivity.this.getWindow();
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

    private void show1(View v) {
        if (mPopupWindow1 != null && !mPopupWindow1.isShowing()) {
            mPopupWindow1.showAtLocation(v, Gravity.CENTER_HORIZONTAL, 0, 0);
        }
        setWindowAlpa(true);
    }

    /**
     * 消失PopupWindow
     */
    public void dismiss() {
        if (mPopupWindow1 != null && mPopupWindow1.isShowing()) {
            mPopupWindow1.dismiss();
        }
    }
}
