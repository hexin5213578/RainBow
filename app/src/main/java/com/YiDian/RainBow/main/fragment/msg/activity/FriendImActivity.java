package com.YiDian.RainBow.main.fragment.msg.activity;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
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
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import androidx.viewpager.widget.ViewPager;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseAvtivity;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.base.Common;
import com.YiDian.RainBow.custom.audiorecord.AudioRecorderButton;
import com.YiDian.RainBow.custom.loading.CustomDialog;
import com.YiDian.RainBow.main.fragment.msg.adapter.FriendImAdapter;
import com.YiDian.RainBow.main.fragment.msg.adapter.GridViewAdapter;
import com.YiDian.RainBow.main.fragment.msg.adapter.ViewPagerAdapter;
import com.YiDian.RainBow.main.fragment.msg.bean.GiftMsgBean;
import com.YiDian.RainBow.main.fragment.msg.bean.GlodNumBean;
import com.YiDian.RainBow.setup.bean.InsertRealBean;
import com.YiDian.RainBow.user.bean.UserMsgBean;
import com.YiDian.RainBow.utils.BitmapUtil;
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
import com.shuyu.gsyvideoplayer.GSYVideoManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.FileInputStream;
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
//               ????????????         ??????BUG

//????????????
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
    @BindView(R.id.tv_qianming)
    TextView tvQianming;
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
    private MediaPlayer mediaPlayer1;
    private AnimationDrawable animationDrawable;
    private Intent intent;
    private List<GiftMsgBean.ObjectBean> list;
    private LayoutInflater inflater;
    private ViewPager vp;
    private LinearLayout lldot;
    private int pageCount;
    private GridViewAdapter[] arr;
    /*???????????????????????????*/
    private int curIndex = 0;

    private int selectnum = -1;
    private TextView tv_balance;
    private Animation anim;
    private CustomDialog dialog;

    @Override
    protected int getResId() {
        return R.layout.activity_im;
    }

    /**
     * addLayoutListener????????????
     *
     * @param main   ?????????
     * @param scroll ????????????????????????View
     */
    public void addLayoutListener(final View main, final View scroll) {
        main.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                //1?????????main????????????????????????
                main.getWindowVisibleDisplayFrame(rect);
                //2?????????main???????????????????????????????????????????????????????????????main.getRootView().getHeight()??????????????????rect.bottom????????????
                int mainInvisibleHeight = main.getRootView().getHeight() - rect.bottom;
                int screenHeight = main.getRootView().getHeight();//????????????
                //3?????????????????????????????????????????????1/4????????????????????????
                if (mainInvisibleHeight > screenHeight / 4) {
                    int[] location = new int[2];
                    scroll.getLocationInWindow(location);
                    // 4?????????Scroll????????????????????????main?????????????????????
                    int srollHeight = (location[1] + scroll.getHeight()) - rect.bottom;
                    //5???????????????????????????????????????
                    main.scrollTo(0, srollHeight);
                } else {
                    //3????????????????????????????????????1/4???,???????????????????????????????????????????????????????????????
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

        dialog = new CustomDialog(this, "????????????");
        Request();

        mediaPlayer1 = new MediaPlayer();
        animationDrawable = new AnimationDrawable();
        if (Build.VERSION.SDK_INT >= 23) {
            int REQUEST_CODE_CONTACT = 101;
            String[] permissions = {
                    Manifest.permission.WRITE_EXTERNAL_STORAGE};
            //????????????????????????
            for (String str : permissions) {
                if (FriendImActivity.this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    //????????????
                    FriendImActivity.this.requestPermissions(permissions, REQUEST_CODE_CONTACT);
                    return;
                } else {
                    //??????????????????????????????????????????????????????
                }
            }
        }
        addLayoutListener(rlLayout, rlBottom);
        //??????????????????
        RecyclerView.ItemAnimator animator = rcImlist.getItemAnimator();
        if (animator instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
        }
        //????????????
        btLuyin.setAudioFinishRecorderListener(new AudioRecorderButton.AudioFinishRecorderListener() {
            @Override
            public void onFinish(int seconds, String FilePath) {
                Log.d("xxx", "??????????????????????????????" + FilePath);

                File file = new File(FilePath);
                int duration = BitmapUtil.getDuration(file, FriendImActivity.this);

                Log.d("xxx", "??????????????????????????????" + duration);

                sendYuyinMessage(FriendImActivity.this, file, duration);

            }
        });

        allList = new ArrayList<>();

        //?????????????????????????????????
        Intent intent = getIntent();
        id = intent.getStringExtra("userid");

        Log.d("xxx", "??????????????????id???" + id);

        //??????????????????id?????????????????????
        conversation = JMessageClient.getSingleConversation(id);

        //????????????????????????????????????
        tvName.setText(conversation.getTitle());

        //?????????????????????????????? ?????????????????????
        userName = Common.getUserName();
        userid = Integer.valueOf(Common.getUserId());

        imAdapter = new FriendImAdapter(FriendImActivity.this);
        //?????????????????????  ??????????????????????????????????????????
        getListFromIm(page, size);

        //????????????????????????????????????
        sv.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                getListFromIm(page, size);
                linearLayoutManager.scrollToPositionWithOffset(page - 5, 0);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {


                        sv.onFinishFreshAndLoad();
                    }
                }, 1000);
            }

            @Override
            public void onLoadmore() {

            }
        });


        //???????????? ????????????????????????????????????
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
        GSYVideoManager.onResume();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        //???????????? ??????????????????
        if (mediaPlayer1.isPlaying()) {
            mediaPlayer1.stop();
            mediaPlayer1.release();
            mediaPlayer1 = null;
        }
        GSYVideoManager.releaseAllVideos();

        animationDrawable.stop();
    }

    //???????????? ??????????????????
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getMsg(String str) {
        if (str.equals("???????????????")) {
            allList.clear();
            page = 0;
            size = 50;
            getListFromIm(page, size);
        }
    }

    public void getListFromIm(int page, int size) {
        List<Message> messagesFromNewest = conversation.getMessagesFromNewest(page, size);

        Log.d("xxx", "????????????" + messagesFromNewest.size() + "???");
        if (messagesFromNewest.size() > 0 && messagesFromNewest != null) {

            rlMsgUser.setVisibility(View.GONE);
            sv.setVisibility(View.VISIBLE);
            this.page += 50;
            this.size += 50;

            sv.setHeader(new AliFooter(this));

            allList.addAll(messagesFromNewest);

            Log.d("xxx", "?????????????????????" + allList.size() + "");
            Log.d("xxx", "????????????????????????" + messagesFromNewest.get(0).toJson());

            linearLayoutManager = new LinearLayoutManager(FriendImActivity.this, RecyclerView.VERTICAL, true);
            rcImlist.setLayoutManager(linearLayoutManager);
            linearLayoutManager.setStackFromEnd(true);
            linearLayoutManager.scrollToPositionWithOffset(0, 0);

            imAdapter = new FriendImAdapter(FriendImActivity.this);

            imAdapter.setData(allList);
            //??????????????????
            File avatarFile = conversation.getAvatarFile();
            imAdapter.setHeadimg(avatarFile);

            rcImlist.setAdapter(imAdapter);
        } else {
            if (allList.size() > 0) {
                Toast.makeText(this, "?????????????????????", Toast.LENGTH_SHORT).show();
            } else {
                sv.setVisibility(View.GONE);
                rlMsgUser.setVisibility(View.VISIBLE);

                //??????????????????????????????????????????
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
                                if (userMsgBean.getMsg().equals("????????????")) {
                                    tvUsername.setText(userInfo.getNickName());
                                    //????????????
                                    Glide.with(FriendImActivity.this).load(userInfo.getHeadImg()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(ivHeadimg);
                                    //????????????
                                    //????????????????????????
                                    String userRole = userInfo.getUserRole();
                                    if (userRole != null) {
                                        if (userRole.equals("??????")) {
                                            tvRole.setVisibility(View.GONE);
                                        } else {
                                            tvRole.setText(userInfo.getUserRole());
                                        }
                                    } else {
                                        tvRole.setVisibility(View.GONE);
                                    }
                                    String explains = userInfo.getExplains();
                                    if (explains!=null){
                                        if (explains.equals("")){
                                            tvQianming.setText("????????????????????????");
                                        }else{
                                            tvQianming.setText("????????????:"+explains);
                                        }
                                    }else{
                                        tvQianming.setText("????????????????????????");
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
     * @param context ?????????
     * @param text    ????????????
     */
    public void sendMessage(Context context, String text) {
        Message message = JMessageClient.createSingleTextMessage(id, Common.get_JG(), text);

        message.setOnSendCompleteCallback(new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                Log.i("TAG", "register???code???" + i + "  msg???" + s);
                if (i == 0) {
                    Log.d("xxx", "??????????????????");

                    allList.clear();
                    page = 0;
                    size = 50;
                    getListFromIm(page, size);
                } else {
                    Log.d("xxx", "??????????????????");
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
                    Log.i("TAG", "register???code???" + i + "  msg???" + s);
                    if (i == 0) {
                        Log.d("xxx", "??????????????????");

                        allList.clear();
                        page = 0;
                        size = 50;
                        getListFromIm(page, size);
                    } else {
                        Log.d("xxx", "??????????????????");

                    }
                }
            });

            MessageSendingOptions options = new MessageSendingOptions();

            options.setNotificationTitle(userName);
            options.setNotificationText("[????????????]");
            options.setCustomNotificationEnabled(true);
            options.setRetainOffline(true);
            options.setShowNotification(true);
            JMessageClient.sendMessage(message, options);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param context
     * @param imageFile ????????????
     */
    public void SendImgMessage(Context context, File imageFile) {
        try {
            Message message = JMessageClient.createSingleImageMessage(id, Common.get_JG(), imageFile);

            message.setOnSendCompleteCallback(new BasicCallback() {
                @Override
                public void gotResult(int i, String s) {
                    Log.i("TAG", "register???code???" + i + "  msg???" + s);
                    if (i == 0) {
                        Log.d("xxx", "??????????????????");

                        allList.clear();
                        page = 0;
                        size = 50;
                        getListFromIm(page, size);
                    } else {
                        Log.d("xxx", "??????????????????");

                    }
                }
            });
            MessageSendingOptions options = new MessageSendingOptions();

            options.setNotificationTitle(userName);
            options.setNotificationText("[????????????]");
            options.setCustomNotificationEnabled(true);
            options.setRetainOffline(true);
            options.setShowNotification(true);
            JMessageClient.sendMessage(message, options);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param context
     * @param VideoFile   ????????????
     * @param thumbImage  ???????????????
     * @param thumbFormat ???????????????
     * @param duration    ??????
     */
    public void SendVideoMessage(Context context, File VideoFile, Bitmap thumbImage, String thumbFormat, int duration) {
        try {
            Message message = JMessageClient.createSingleVideoMessage(id, Common.get_JG(), thumbImage, thumbFormat, VideoFile, "", duration);

            message.setOnSendCompleteCallback(new BasicCallback() {
                @Override
                public void gotResult(int i, String s) {
                    Log.i("TAG", "register???code???" + i + "  msg???" + s);
                    if (i == 0) {
                        Log.d("xxx", "??????????????????");

                        allList.clear();
                        page = 0;
                        size = 50;
                        getListFromIm(page, size);
                    } else {
                        Log.d("xxx", "??????????????????");

                    }
                }
            });
            MessageSendingOptions options = new MessageSendingOptions();

            options.setNotificationTitle(userName);
            options.setNotificationText("[????????????]");
            options.setCustomNotificationEnabled(true);
            options.setRetainOffline(true);
            options.setShowNotification(true);
            JMessageClient.sendMessage(message, options);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                EventBus.getDefault().post("???????????????");
                //???????????????
                JMessageClient.exitConversation();
                break;
            case R.id.ll_more:
                //??????????????????
                intent = new Intent(FriendImActivity.this, ImSetUpActivity.class);
                File avatarFile = conversation.getAvatarFile();
                String title = conversation.getTitle();
                if (avatarFile == null) {
                    intent.putExtra("imgfile", "http://img.rianbow.cn/20210113103522202909.png");
                } else {
                    intent.putExtra("imgfile", avatarFile.toString());
                }

                intent.putExtra("name", title);
                intent.putExtra("userid", conversation.getTargetId());

                startActivity(intent);
                break;
            case R.id.bt_confirm:
                String s = etContent.getText().toString();

                //??????????????????
                sendMessage(FriendImActivity.this, s);
                etContent.setText("");
                //????????????????????????
                KeyBoardUtils.closeKeyboard(etContent);

                rcImlist.scrollToPosition(0);
                break;
            case R.id.iv_yuyin:
                //?????????????????????
                rlSendmsg.setVisibility(View.GONE);
                rlSendyuyin.setVisibility(View.VISIBLE);

                rlMenu.setVisibility(View.GONE);
                //????????????
                KeyBoardUtils.closeKeyboard(etContent);
                break;
            case R.id.iv_jiahao:
                if (isfirst) {
                    KeyBoardUtils.closeKeyboard(etContent);
                    rlMenu.setVisibility(View.VISIBLE);
                    if (allList.size() > 0) {
                        linearLayoutManager.setStackFromEnd(true);
                        linearLayoutManager.scrollToPositionWithOffset(0, 0);
                    }
                    isfirst = false;
                } else {

                    rlMenu.setVisibility(View.GONE);
                    isfirst = true;
                }
                break;
            //??????????????????
            case R.id.iv_paizhao:
                //????????????????????????
                int request = ContextCompat.checkSelfPermission(FriendImActivity.this, Manifest.permission.CAMERA);
                if (request != PackageManager.PERMISSION_GRANTED)//?????????????????????????????????
                {
                    Toast.makeText(this, "?????????????????????", Toast.LENGTH_SHORT).show();
                } else {
                    intent = new Intent(FriendImActivity.this, JCameraViewActivity.class);
                    intent.putExtra("userid", id);
                    startActivity(intent);
                }
                break;
            //????????????
            case R.id.iv_xiangce:
                showSelectImgAndVideo();
                break;
            //?????????????????????
            case R.id.iv_liwu:
                // TODO: 2021/1/13 0013 ?????????????????????
                getGiftMsg();
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


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getAnim(AnimationDrawable drawable) {
        animationDrawable = drawable;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getMedia(MediaPlayer mediaPlayer) {
        mediaPlayer1 = mediaPlayer;
    }

    //??????10.0????????????
    public void Request() {
        if (Build.VERSION.SDK_INT >= 23) {
            int request = ContextCompat.checkSelfPermission(FriendImActivity.this, Manifest.permission.CAMERA);
            if (request != PackageManager.PERMISSION_GRANTED)//?????????????????????????????????
            {
                ActivityCompat.requestPermissions(FriendImActivity.this, new String[]{Manifest.permission.CAMERA}, 100);
                return;//
            } else {

            }
        } else {

        }
    }

    public void getGiftMsg() {
        dialog.show();
        NetUtils.getInstance().getApis()
                .doGetAllGiftMsg()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GiftMsgBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(GiftMsgBean giftMsgBean) {
                        dialog.dismiss();
                        if (giftMsgBean.getMsg().equals("????????????")) {
                            list = giftMsgBean.getObject();
                            showSelectGift();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        dialog.dismiss();
                        Toast.makeText(FriendImActivity.this, "????????????????????????", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    //???????????????
    public void getGlodNum() {
        NetUtils.getInstance().getApis()
                .dogetGldNum(userid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GlodNumBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(GlodNumBean glodNumBean) {
                        if (glodNumBean.getMsg().equals("????????????")) {
                            tv_balance.setText(glodNumBean.getObject().getGoldAll() + "");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(FriendImActivity.this, "??????????????????", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    //?????????????????????????????????
    public void showSelectGift() {
        //?????????????????????  63 50
        mPopupWindow1 = new PopupWindow();
        mPopupWindow1.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow1.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        View view = LayoutInflater.from(FriendImActivity.this).inflate(R.layout.dialog_sendgift, null);

        vp = view.findViewById(R.id.vp);
        lldot = view.findViewById(R.id.ll_dot);
        LinearLayout llrecharge = view.findViewById(R.id.ll_recharge);
        RelativeLayout ll_close = view.findViewById(R.id.ll_close);
        ImageView iv_anim = view.findViewById(R.id.iv_anim);
        tv_balance = view.findViewById(R.id.tv_balance);
        RelativeLayout rl_send = view.findViewById(R.id.rl_send);


        //???????????????
        getGlodNum();

        //????????????=??????/????????????????????????
        pageCount = (int) Math.ceil(list.size() * 1.0 / 8);
        inflater = LayoutInflater.from(FriendImActivity.this);

        //????????????
        List<View> mPagerList = new ArrayList<>();
        arr = new GridViewAdapter[pageCount];

        for (int j = 0; j < pageCount; j++) {
            final GridView gridview = (GridView) inflater.inflate(R.layout.bottom_vp_gridview, vp, false);
            final GridViewAdapter gridAdapter = new GridViewAdapter(FriendImActivity.this, list, j);
            gridview.setAdapter(gridAdapter);
            arr[j] = gridAdapter;
            gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    selectnum = (int) id;

                    for (int i = 0; i < list.size(); i++) {
                        GiftMsgBean.ObjectBean model = list.get(i);
                        if (i == id) {
                            if (model.isSelected()) {
                                model.setSelected(false);
                                selectnum = -1;
                            } else {
                                model.setSelected(true);
                            }
                        } else {
                            model.setSelected(false);
//                            Log.i("tag","==??????2???"+i+"..id:"+id);
                        }
                    }
                    gridAdapter.notifyDataSetChanged();
                }
            });
            mPagerList.add(gridview);
        }
        vp.setAdapter(new ViewPagerAdapter(mPagerList, FriendImActivity.this));

        setOvalLayout();

        rl_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //???????????????????????????
                if (selectnum == -1) {
                    Toast.makeText(FriendImActivity.this, "??????????????????", Toast.LENGTH_SHORT).show();
                } else {
                    GiftMsgBean.ObjectBean giftbean = list.get(selectnum);
                    Log.d("xxx", "???????????????" + selectnum);


                    //???????????????????????????
                    NetUtils.getInstance().getApis()
                            .doSendGift(Integer.parseInt(conversation.getTargetId()), userid, giftbean.getId())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<InsertRealBean>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(InsertRealBean insertRealBean) {
                                    if (insertRealBean.getMsg().equals("????????????")) {
                                        //???????????? ????????????
                                        getGlodNum();

                                        //????????????
                                        iv_anim.setVisibility(View.VISIBLE);
                                        Glide.with(FriendImActivity.this).load(giftbean.getGiftImg()).into(iv_anim);


                                        ObjectAnimator Animator1 = ObjectAnimator.ofFloat(iv_anim, "translationY", -700);
                                        Animator1.setInterpolator(new LinearInterpolator());
                                        Animator1.setDuration(1000);

                                        ObjectAnimator Animator2 = ObjectAnimator.ofFloat(iv_anim, "rotation", 0.0F, 1080.0f);
                                        Animator2.setInterpolator(new LinearInterpolator());
                                        Animator2.setDuration(1500);

                                        AnimatorSet set = new AnimatorSet();
                                        set.play(Animator1).before(Animator2);

                                        set.start();
                                        set.addListener(new Animator.AnimatorListener() {
                                            @Override
                                            public void onAnimationStart(Animator animation) {

                                            }

                                            @Override
                                            public void onAnimationEnd(Animator animation) {
                                                animation.cancel();

                                                iv_anim.setVisibility(View.GONE);
                                                ObjectAnimator Animator = ObjectAnimator.ofFloat(iv_anim, "translationY", 700);
                                                Animator.setInterpolator(new LinearInterpolator());
                                                Animator.setDuration(200);

                                                Animator.start();
                                            }

                                            @Override
                                            public void onAnimationCancel(Animator animation) {

                                            }

                                            @Override
                                            public void onAnimationRepeat(Animator animation) {

                                            }
                                        });
                                    }
                                    if (insertRealBean.getMsg().equals("????????????")) {
                                        // TODO: 2021/1/24 0024 ?????????????????????????????????
                                        Toast.makeText(FriendImActivity.this, "??????????????????", Toast.LENGTH_SHORT).show();
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
        });


        ll_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        llrecharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //?????????
                Toast.makeText(FriendImActivity.this, "????????????????????????????????????????????????", Toast.LENGTH_SHORT).show();
            }
        });

        //popwindow????????????
        mPopupWindow1.setAnimationStyle(R.style.popwindow_anim_style);
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
        show(view);
    }

    /**
     * ????????????
     */
    public void setOvalLayout() {
        for (int i = 0; i < pageCount; i++) {
            lldot.addView(inflater.inflate(R.layout.dot, null));
        }
        // ?????????????????????
        lldot.getChildAt(0).findViewById(R.id.v_dot)
                .setBackgroundResource(R.drawable.dot_selected);
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageSelected(int position) {
                arr[0].notifyDataSetChanged();
                arr[1].notifyDataSetChanged();
                arr[2].notifyDataSetChanged();

                // ??????????????????
                lldot.getChildAt(curIndex)
                        .findViewById(R.id.v_dot)
                        .setBackgroundResource(R.drawable.dot_normal);
                // ????????????
                lldot.getChildAt(position)
                        .findViewById(R.id.v_dot)
                        .setBackgroundResource(R.drawable.dot_selected);
                curIndex = position;
            }

            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }

    //????????????????????????????????????
    public void showSelectImgAndVideo() {
        //?????????????????????
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
                //?????????????????????
                select.clear();

                Intent intent = new Intent(FriendImActivity.this, PickerActivity.class);
                intent.putExtra(PickerConfig.SELECT_MODE, PickerConfig.PICKER_IMAGE);//????????????????????????????????????????????????????????????(???????????????)
                long maxSize = 10485760L;//long long long long??????
                intent.putExtra(PickerConfig.MAX_SELECT_SIZE, maxSize);
                intent.putExtra(PickerConfig.MAX_SELECT_COUNT, 9); //???????????????????????????40?????????????????????
                FriendImActivity.this.startActivityForResult(intent, 201);

                dismiss();
            }
        });
        rl_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //?????????????????????
                select1.clear();

                Intent intent = new Intent(FriendImActivity.this, PickerActivity.class);
                intent.putExtra(PickerConfig.SELECT_MODE, PickerConfig.PICKER_VIDEO);//????????????????????????????????????????????????????????????(???????????????)
                long maxSize = 104857600;//long long long long??????
                intent.putExtra(PickerConfig.MAX_SELECT_SIZE, maxSize);
                intent.putExtra(PickerConfig.MAX_SELECT_COUNT, 1); //???????????????????????????40?????????????????????
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
        //popwindow????????????
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            if (requestCode == 200 && resultCode == PickerConfig.RESULT_CODE) {

                if (data.getParcelableArrayListExtra(PickerConfig.EXTRA_RESULT).size() > 0) {
                    select1 = data.getParcelableArrayListExtra(PickerConfig.EXTRA_RESULT);

                    Log.d("xxx", "?????????" + select1.size() + "?????????");
                    String path = select1.get(0).path;
                    Log.d("xxx", select1.get(0).path);
                    //?????????????????????
                    Bitmap netVideoBitmap = getNetVideoBitmap(path);

                    //????????????
                    File file = new File(path);
                    //????????????
                    MediaMetadataRetriever retriever = new MediaMetadataRetriever();
                    retriever.setDataSource(path);
                    String time = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
                    Log.d("xxx", "???????????????" + Integer.parseInt(time) / 1000);
                    //????????????????????????
                    SendVideoMessage(FriendImActivity.this, file, netVideoBitmap, "mp4", Integer.parseInt(time) / 1000);
                }

            } else if (requestCode == 201 && resultCode == PickerConfig.RESULT_CODE) {

                if (data.getParcelableArrayListExtra(PickerConfig.EXTRA_RESULT).size() > 0) {
                    select = data.getParcelableArrayListExtra(PickerConfig.EXTRA_RESULT);

                    if (select.size() > 1) {
                        for (int i = 0; i < select.size(); i++) {
                            //??????????????????
                            File file = new File(select.get(i).path);
                            SendImgMessage(FriendImActivity.this, file);
                        }
                    } else {
                        //??????????????????
                        File file = new File(select.get(0).path);
                        SendImgMessage(FriendImActivity.this, file);
                    }

                }
                Log.i("select", "???????????????" + select.size());

            }
        }
    }

    //???????????????
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

    private void show(View v) {
        if (mPopupWindow1 != null && !mPopupWindow1.isShowing()) {
            mPopupWindow1.showAtLocation(v, Gravity.BOTTOM, 0, 0);
        }
        setWindowAlpa(true);
    }

    /**
     * ??????PopupWindow
     */
    public void dismiss() {
        if (mPopupWindow1 != null && mPopupWindow1.isShowing()) {
            mPopupWindow1.dismiss();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
