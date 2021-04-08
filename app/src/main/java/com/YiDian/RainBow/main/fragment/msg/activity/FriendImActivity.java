package com.YiDian.RainBow.main.fragment.msg.activity;

import android.Manifest;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
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
import com.YiDian.RainBow.custom.svag.SvgaUtils;
import com.YiDian.RainBow.main.fragment.msg.adapter.FriendImAdapter;
import com.YiDian.RainBow.main.fragment.msg.adapter.GridViewAdapter;
import com.YiDian.RainBow.main.fragment.msg.adapter.ViewPagerAdapter;
import com.YiDian.RainBow.main.fragment.msg.bean.GiftMsgBean;
import com.YiDian.RainBow.main.fragment.msg.bean.GlodNumBean;
import com.YiDian.RainBow.map.MyMapActivity;
import com.YiDian.RainBow.map.bean.SaveNearMessageBean;
import com.YiDian.RainBow.music.SendMusicActivity;
import com.YiDian.RainBow.music.bean.SaveSendMusicBean;
import com.YiDian.RainBow.setup.bean.InsertRealBean;
import com.YiDian.RainBow.user.bean.UserMsgBean;
import com.YiDian.RainBow.utils.BitmapUtil;
import com.YiDian.RainBow.utils.KeyBoardUtils;
import com.YiDian.RainBow.utils.MD5Utils;
import com.YiDian.RainBow.utils.NetUtils;
import com.amap.api.services.core.LatLonPoint;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.dmcbig.mediapicker.PickerActivity;
import com.dmcbig.mediapicker.PickerConfig;
import com.dmcbig.mediapicker.entity.Media;
import com.leaf.library.StatusBarUtil;
import com.liaoinstan.springview.container.AliFooter;
import com.liaoinstan.springview.widget.SpringView;
import com.opensource.svgaplayer.SVGAImageView;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.shuyu.gsyvideoplayer.GSYVideoManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.IDN;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.exceptions.JMFileSizeExceedException;
import cn.jpush.im.android.api.exceptions.JMessageException;
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
    @BindView(R.id.tv_qianming)
    TextView tvQianming;
    @BindView(R.id.iv_location)
    ImageView ivLocation;
    @BindView(R.id.iv_music)
    ImageView ivMusic;
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
    /*当前显示的是第几页*/
    private int curIndex = 0;
    private static final String serverPath = "http://img.rianbow.cn/";
    private int selectnum = -1;
    private TextView tv_balance;
    private CustomDialog dialog;
    private File file;
    private CustomDialog customDialog;
    private String token;
    private UploadManager uploadManager;
    private String img;

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
        ivLocation.setOnClickListener(this);
        ivMusic.setOnClickListener(this);
        etContent.setOnClickListener(this);

        select = new ArrayList<>();
        select1 = new ArrayList<>();

        dialog = new CustomDialog(this, "正在加载...");
        customDialog = new CustomDialog(this, "正在发送...");

        token = Common.getToken();

        Request();

        mediaPlayer1 = new MediaPlayer();
        animationDrawable = new AnimationDrawable();
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
                int duration = BitmapUtil.getDuration(file, FriendImActivity.this);

                Log.d("xxx", "拿到语音文件的长度为" + duration);

                customDialog.show();
                sendYuyinMessage(FriendImActivity.this, file, duration);

            }
        });

        allList = new ArrayList<>();

        //获取传递过来的用户信息
        Intent intent = getIntent();
        id = intent.getStringExtra("userid");
        String name = intent.getStringExtra("name");

        //判断ID长度 为六位时为单聊
        if (id.length() == 6 || id.length() == 4) {
            Log.d("xxx", "传入聊天页聊天的id为" + id);

            //通过传过来的id对应对话的对象
            conversation = JMessageClient.getSingleConversation(id);

            //设置聊天对应用户的用户名
            tvName.setText(name);
        } else if (id.length() == 8) {
            Log.d("xxx", "传入聊天页群组的id为" + id);

            conversation = JMessageClient.getGroupConversation(Long.parseLong(id));

            //设置聊天对应用户的群名
            tvName.setText(conversation.getTitle());
        }


        //获取当前登录的用户名 设置状态栏提示
        userName = Common.getUserName();
        userid = Integer.valueOf(Common.getUserId());

        imAdapter = new FriendImAdapter(FriendImActivity.this,id);
        //先获取聊天记录  获取为空展示聊天对方用户信息
        getListFromIm(page, size);

        //设置下拉加载更多聊天记录
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

    /**
     * @param saveNearMessageBean 获取需要发送的定位信息
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getLocationMsg(SaveNearMessageBean saveNearMessageBean) {
        Log.d("xxx", "执行一次，发送给" + id);
        Log.d("xxx", "当前发送地址为" + saveNearMessageBean.getAddress());

        String address = saveNearMessageBean.getAddress();
        String shengfen = saveNearMessageBean.getShengfen();
        String shiqu = saveNearMessageBean.getShiqu();
        String xian = saveNearMessageBean.getXian();
        LatLonPoint locataion = saveNearMessageBean.getLocataion();
        double latitude = locataion.getLatitude();
        double longitude = locataion.getLongitude();

        sendLocation(id, latitude, longitude, 3, shengfen + shiqu + xian + address + "," + saveNearMessageBean.getTitle());
    }

    /**
     * 获取需要发送的音乐信息
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getMusicMsg(SaveSendMusicBean bean) {
        Log.d("xxx","需要发送的音乐传递到了聊天页");

        String name = bean.getName();
        String img = bean.getImg();
        String author = bean.getAuthor();
        String path = bean.getPath();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Bitmap bitmap = returnBitMap(img);
                    String imageName = System.currentTimeMillis() + ".png";
                    file = saveFile(bitmap, imageName);
                    customDialog.show();
                    sendMusic(id,file,name+"!!"+img+"!!"+author+"!!"+path);
                } catch (IOException | JMFileSizeExceedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
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
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        //界面销毁 销毁播放音频
        if (mediaPlayer1.isPlaying()) {
            mediaPlayer1.stop();
            mediaPlayer1.release();
            mediaPlayer1 = null;
        }
        GSYVideoManager.releaseAllVideos();

        animationDrawable.stop();
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

        if (conversation.getType().name().equals("group")) {
            rlMsgUser.setVisibility(View.GONE);
            sv.setVisibility(View.VISIBLE);
            if (messagesFromNewest.size() > 0 && messagesFromNewest != null) {
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

                imAdapter = new FriendImAdapter(FriendImActivity.this,id);

                imAdapter.setData(allList);
                //设置对方头像
                File avatarFile = conversation.getAvatarFile();
                imAdapter.setHeadimg(avatarFile);

                rcImlist.setAdapter(imAdapter);
            }
        } else {
            if (messagesFromNewest != null && messagesFromNewest.size() > 0) {
                Log.d("xxx", "获取到了" + messagesFromNewest.size() + "条");
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

                imAdapter = new FriendImAdapter(FriendImActivity.this,id);

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
                                        if (userRole != null) {
                                            if (userRole.equals("保密")) {
                                                tvRole.setVisibility(View.GONE);
                                            } else {
                                                tvRole.setText(userInfo.getUserRole());
                                            }
                                        } else {
                                            tvRole.setVisibility(View.GONE);
                                        }
                                        String explains = userInfo.getExplains();
                                        if (explains != null) {
                                            if (explains.equals("")) {
                                                tvQianming.setText("还没有设置签名哦");
                                            } else {
                                                tvQianming.setText("个性签名:" + explains);
                                            }
                                        } else {
                                            tvQianming.setText("还没有设置签名哦");
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
                    Log.d("xxx", "文本发送成功");

                    allList.clear();
                    page = 0;
                    size = 50;
                    getListFromIm(page, size);
                } else {
                    Log.d("xxx", "文本发送失败");
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
     * @param lat     维度
     * @param lng     经度
     * @param scale   地图缩放比例
     * @param address 详细地址
     */
    public void sendLocation(String senduserid, double lat, double lng, int scale, String address) {
        Message message = JMessageClient.createSingleLocationMessage(senduserid, Common.get_JG(), lat, lng, scale, address);

        message.setOnSendCompleteCallback(new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                Log.i("TAG", "register：code：" + i + "  msg：" + s);
                if (i == 0) {
                    Log.d("xxx", "位置发送成功");
                    allList.clear();
                    page = 0;
                    size = 50;
                    getListFromIm(page, size);
                } else {
                    Log.d("xxx", "位置发送失败");
                }
            }
        });

        MessageSendingOptions options = new MessageSendingOptions();

        options.setNotificationTitle(userName);
        options.setNotificationText("[位置信息]");
        options.setCustomNotificationEnabled(true);
        options.setRetainOffline(true);
        options.setShowNotification(true);
        JMessageClient.sendMessage(message, options);
    }


    /**
     *
     * @param senduserid
     * @param file
     * @param filename
     */
    public void sendMusic(String senduserid, File file,String filename) throws FileNotFoundException, JMFileSizeExceedException {
        Message message = JMessageClient.createSingleFileMessage(senduserid, Common.get_JG(), file, filename);

        message.setOnSendCompleteCallback(new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                Log.i("TAG", "register：code：" + i + "  msg：" + s);
                if (i == 0) {
                    Log.d("xxx", "音乐发送成功");
                    customDialog.show();
                    allList.clear();
                    page = 0;
                    size = 50;
                    getListFromIm(page, size);
                } else {
                    Log.d("xxx", "音乐发送失败");
                    customDialog.dismiss();
                }
            }
        });

        MessageSendingOptions options = new MessageSendingOptions();

        options.setNotificationTitle(userName);
        options.setNotificationText("[文件信息]");
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
                        Log.d("xxx", "语音发送成功");
                        customDialog.dismiss();
                        allList.clear();
                        page = 0;
                        size = 50;
                        getListFromIm(page, size);
                    } else {
                        Log.d("xxx", "语音发送失败");
                        customDialog.dismiss();

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

    /**
     * @param context
     * @param imageFile 图片路径
     */
    public void SendImgMessage(Context context, File imageFile) {
        try {
            Message message = JMessageClient.createSingleImageMessage(id, Common.get_JG(), imageFile);

            message.setOnSendCompleteCallback(new BasicCallback() {
                @Override
                public void gotResult(int i, String s) {
                    Log.i("TAG", "register：code：" + i + "  msg：" + s);
                    if (i == 0) {
                        Log.d("xxx", "图片发送成功");
                        customDialog.dismiss();
                        allList.clear();
                        page = 0;
                        size = 50;
                        getListFromIm(page, size);
                    } else {
                        Log.d("xxx", "图片发送失败");
                        customDialog.dismiss();

                    }
                }
            });
            MessageSendingOptions options = new MessageSendingOptions();

            options.setNotificationTitle(userName);
            options.setNotificationText("[图片信息]");
            options.setCustomNotificationEnabled(true);
            options.setRetainOffline(true);
            options.setShowNotification(true);
            JMessageClient.sendMessage(message, options);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param context
     * @param VideoFile
     * @param thumbImage
     * @param thumbFormat
     * @param duration
     */
    public void SendVideoMessage(Context context, File VideoFile, Bitmap thumbImage, String thumbFormat, int duration) {
        try {
            Message message = JMessageClient.createSingleVideoMessage(id, Common.get_JG(), thumbImage, thumbFormat, VideoFile, "",duration);

            message.setOnSendCompleteCallback(new BasicCallback() {
                @Override
                public void gotResult(int i, String s) {
                    Log.i("TAG", "register：code：" + i + "  msg：" + s);
                    if (i == 0) {
                        Log.d("xxx", "视频发送成功");
                        customDialog.dismiss();
                        allList.clear();
                        page = 0;
                        size = 50;
                        getListFromIm(page, size);
                    } else {
                        Log.d("xxx", "视频发送失败");
                        customDialog.dismiss();
                    }
                }
            });
            MessageSendingOptions options = new MessageSendingOptions();

            options.setNotificationTitle(userName);
            options.setNotificationText("[视频信息]");
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
                //退出此会话
                JMessageClient.exitConversation();
                break;
            case R.id.ll_more:
                //跳转到设置页
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

                //发送文本信息
                sendMessage(FriendImActivity.this, s);
                etContent.setText("");
                //发送成功关闭键盘
                KeyBoardUtils.closeKeyboard(etContent);

                rcImlist.scrollToPosition(0);
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
            //调起相机拍照
            case R.id.iv_paizhao:
                //跳转到拍照摄像页
                int request = ContextCompat.checkSelfPermission(FriendImActivity.this, Manifest.permission.CAMERA);
                if (request != PackageManager.PERMISSION_GRANTED)//缺少权限，进行权限申请
                {
                    Toast.makeText(this, "相机权限未开启", Toast.LENGTH_SHORT).show();
                } else {
                    intent = new Intent(FriendImActivity.this, JCameraViewActivity.class);
                    intent.putExtra("userid", id);
                    startActivity(intent);
                }
                break;
            //调起相册
            case R.id.iv_xiangce:
                showSelectImgAndVideo();
                break;
            //展示礼物选择框
            case R.id.iv_liwu:
                // TODO: 2021/1/13 0013 展示选择礼物框
                getGiftMsg();
                break;
            //发送位置
            case R.id.iv_location:
                intent = new Intent(FriendImActivity.this, MyMapActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_music:
                intent = new Intent(FriendImActivity.this, SendMusicActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_keyborad:
                rlSendmsg.setVisibility(View.VISIBLE);
                rlSendyuyin.setVisibility(View.GONE);
                break;
            case R.id.et_content:
                rlMenu.setVisibility(View.GONE);
                break;
            default:
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
                        if (giftMsgBean.getMsg().equals("查询成功")) {
                            list = giftMsgBean.getObject();
                            showSelectGift();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        dialog.dismiss();
                        Toast.makeText(FriendImActivity.this, "获取礼物列表失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    //获取金币数
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
                        if (glodNumBean.getMsg().equals("查询成功")) {
                            tv_balance.setText(glodNumBean.getObject().getGoldAll() + "");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(FriendImActivity.this, "获取余额失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    //弹出选择礼物列表弹出框
    public void showSelectGift() {
        //添加成功后处理  63 50
        mPopupWindow1 = new PopupWindow();
        mPopupWindow1.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow1.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        View view = LayoutInflater.from(FriendImActivity.this).inflate(R.layout.dialog_sendgift, null);

        vp = view.findViewById(R.id.vp);
        lldot = view.findViewById(R.id.ll_dot);
        LinearLayout llrecharge = view.findViewById(R.id.ll_recharge);
        RelativeLayout ll_close = view.findViewById(R.id.ll_close);
        SVGAImageView svagimg = view.findViewById(R.id.svgaImage);
        tv_balance = view.findViewById(R.id.tv_balance);
        RelativeLayout rl_send = view.findViewById(R.id.rl_send);


        //获取金币数
        getGlodNum();

        //总的页数=总数/每页数量，并取整
        pageCount = (int) Math.ceil(list.size() * 1.0 / 8);
        inflater = LayoutInflater.from(FriendImActivity.this);

        //页面集合
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
//                            Log.i("tag","==位置2："+i+"..id:"+id);
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
                //送出当前选中的礼物
                if (selectnum == -1) {
                    Toast.makeText(FriendImActivity.this, "请先选择礼物", Toast.LENGTH_SHORT).show();
                } else {
                    GiftMsgBean.ObjectBean giftbean = list.get(selectnum);
                    Log.d("xxx", "当前选中为" + selectnum);


                    //发起赠送礼物的接口
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
                                    if (insertRealBean.getMsg().equals("送出成功")) {
                                        //送出成功 刷新余额
                                        getGlodNum();

                                        //展示动画
                                        final SvgaUtils svgaUtils = new SvgaUtils(FriendImActivity.this, svagimg);
                                        svgaUtils.initAnimator();
                                        switch (selectnum) {
                                            case 0:
                                                svgaUtils.startAnimator("guzhang");
                                                break;
                                            case 1:
                                                svgaUtils.startAnimator("liuliuliu");
                                                break;
                                            case 2:
                                                svgaUtils.startAnimator("aixin");
                                                break;
                                            case 3:
                                                svgaUtils.startAnimator("dangao");

                                                break;
                                            case 4:
                                                svgaUtils.startAnimator("pijiu");

                                                break;
                                            case 5:
                                                svgaUtils.startAnimator("jiayou");

                                                break;
                                            case 6:
                                                svgaUtils.startAnimator("jiaonang");

                                                break;
                                            case 7:
                                                svgaUtils.startAnimator("feiji");

                                                break;
                                            case 8:
                                                svgaUtils.startAnimator("jiangbei");

                                                break;
                                            case 9:
                                                svgaUtils.startAnimator("wen");

                                                break;
                                            case 10:
                                                svgaUtils.startAnimator("huojian");

                                                break;
                                            case 11:
                                                svgaUtils.startAnimator("paoche");

                                                break;
                                            case 12:
                                                svgaUtils.startAnimator("zhishengji");

                                                break;
                                            case 13:
                                                svgaUtils.startAnimator("motianlun");

                                                break;
                                            default:
                                                break;
                                        }
                                        //送出成功 发送图片
                                        String giftImg = giftbean.getGiftImg();

                                        Thread thread = new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                try {
                                                    Bitmap bitmap = returnBitMap(giftImg);
                                                    String imageName = System.currentTimeMillis() + ".png";
                                                    File file = saveFile(bitmap, imageName);

                                                    SendImgMessage(FriendImActivity.this, file);
                                                    sendMessage(FriendImActivity.this, "送出了一个" + giftbean.getGiftName());
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        });
                                        thread.start();

                                    }
                                    if (insertRealBean.getMsg().equals("余额不足")) {
                                        // TODO: 2021/1/24 0024 接入充值功能提示去充值
                                        Toast.makeText(FriendImActivity.this, "账户余额不足", Toast.LENGTH_SHORT).show();
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
                //去充值
                Toast.makeText(FriendImActivity.this, "充值通道未开启，请关注后续通知哦", Toast.LENGTH_SHORT).show();
            }
        });

        //popwindow设置属性
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

    public final static Bitmap returnBitMap(String url) {
        URL myFileUrl = null;
        Bitmap bitmap = null;
        try {
            myFileUrl = new URL(url);
            HttpURLConnection conn;
            conn = (HttpURLConnection) myFileUrl.openConnection();
            conn.setDoInput(true);
            int length = conn.getContentLength();
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is, length);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 2;    // 设置缩放比例
            Rect rect = new Rect(0, 0, 0, 0);
            bitmap = BitmapFactory.decodeStream(bis, rect, options);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * 将Bitmap转换成文件
     * 保存文件
     *
     * @param bm
     * @param fileName
     * @throws IOException
     */
    public static File saveFile(Bitmap bm, String fileName) throws IOException {
        String path = getSDPath() + "/wuliu/";
        File dirFile = new File(path);
        if (!dirFile.exists()) {
            dirFile.mkdir();
        }
        File myCaptureFile = new File(path + fileName);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
        bos.flush();
        bos.close();
        return myCaptureFile;
    }

    //获取sd卡路径
    public static String getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(Environment.MEDIA_MOUNTED);//判断sd卡是否存在
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
        }
        return sdDir.toString();
    }

    /**
     * 设置圆点
     */
    public void setOvalLayout() {
        for (int i = 0; i < pageCount; i++) {
            lldot.addView(inflater.inflate(R.layout.dot, null));
        }
        // 默认显示第一页
        lldot.getChildAt(0).findViewById(R.id.v_dot)
                .setBackgroundResource(R.drawable.dot_selected);
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                arr[0].notifyDataSetChanged();


                // 取消圆点选中
                lldot.getChildAt(curIndex)
                        .findViewById(R.id.v_dot)
                        .setBackgroundResource(R.drawable.dot_normal);
                // 圆点选中
                lldot.getChildAt(position)
                        .findViewById(R.id.v_dot)
                        .setBackgroundResource(R.drawable.dot_selected);
                curIndex = position;
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
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

    public static Bitmap getNetVideoBitmap(String videoUrl) {
        Bitmap bitmap = null;

        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            FileInputStream inputStream = new FileInputStream(new File(videoUrl).getAbsolutePath());
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            if (requestCode == 200 && resultCode == PickerConfig.RESULT_CODE) {

                if (data.getParcelableArrayListExtra(PickerConfig.EXTRA_RESULT).size() > 0) {
                    select1 = data.getParcelableArrayListExtra(PickerConfig.EXTRA_RESULT);

                    Log.d("xxx", "获取到" + select1.size() + "条视频");
                    String path = select1.get(0).path;
                    Log.d("xxx", select1.get(0).path);
                    //获取视频缩略图
                    Bitmap netVideoBitmap = getNetVideoBitmap(path);

                    //视频文件
                    File file = new File(path);
                    //视频时长
                    MediaMetadataRetriever retriever = new MediaMetadataRetriever();
                    retriever.setDataSource(path);
                    String time = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);

                    customDialog.show();

                    //发送一条视频文件
                    SendVideoMessage(FriendImActivity.this, file, netVideoBitmap, "mp4", Integer.parseInt(time) / 1000);
                }

            } else if (requestCode == 201 && resultCode == PickerConfig.RESULT_CODE) {

                if (data.getParcelableArrayListExtra(PickerConfig.EXTRA_RESULT).size() > 0) {
                    select = data.getParcelableArrayListExtra(PickerConfig.EXTRA_RESULT);

                    customDialog.show();
                    if (select.size() > 1) {
                        for (int i = 0; i < select.size(); i++) {
                            //发送多张图片
                            File file = new File(select.get(i).path);
                            SendImgMessage(FriendImActivity.this, file);
                        }
                    } else {
                        //发送单张图片
                        File file = new File(select.get(0).path);
                        SendImgMessage(FriendImActivity.this, file);
                    }

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

    private void show(View v) {
        if (mPopupWindow1 != null && !mPopupWindow1.isShowing()) {
            mPopupWindow1.showAtLocation(v, Gravity.BOTTOM, 0, 0);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
