package com.YiDian.RainBow.main.fragment.home.activity;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.App;
import com.YiDian.RainBow.base.BaseAvtivity;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.base.Common;
import com.YiDian.RainBow.custom.customDialog.CustomDialogCancleFollow;
import com.YiDian.RainBow.custom.image.NineGridTestLayout;
import com.YiDian.RainBow.custom.videoplayer.SampleCoverVideo;
import com.YiDian.RainBow.main.fragment.home.adapter.CommentAdapter;
import com.YiDian.RainBow.main.fragment.home.bean.CollectDynamicBean;
import com.YiDian.RainBow.main.fragment.home.bean.CommentBean;
import com.YiDian.RainBow.main.fragment.home.bean.FirstCommentBean;
import com.YiDian.RainBow.main.fragment.home.adapter.NewDynamicAdapter;
import com.YiDian.RainBow.main.fragment.home.adapter.TopicsAdapter;
import com.YiDian.RainBow.main.fragment.home.bean.DianzanBean;
import com.YiDian.RainBow.main.fragment.home.bean.DynamicDetailsBean;
import com.YiDian.RainBow.main.fragment.home.bean.FollowBean;
import com.YiDian.RainBow.main.fragment.mine.adapter.MyDraftsAdapter;
import com.YiDian.RainBow.topic.SaveIntentMsgBean;
import com.YiDian.RainBow.topic.TopicDetailsActivity;
import com.YiDian.RainBow.user.PersonHomeActivity;
import com.YiDian.RainBow.utils.KeyBoardUtils;
import com.YiDian.RainBow.utils.NetUtils;
import com.YiDian.RainBow.utils.StringUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.leaf.library.StatusBarUtil;
import com.liaoinstan.springview.container.AliFooter;
import com.liaoinstan.springview.container.AliHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.tencent.connect.share.QQShare;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.modelmsg.WXVideoObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.greenrobot.eventbus.EventBus;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.YiDian.RainBow.main.fragment.home.adapter.NewDynamicAdapter.getNetVideoBitmap;

//动态详情页
public class DynamicDetailsActivity extends BaseAvtivity implements View.OnClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_back)
    LinearLayout ivBack;
    @BindView(R.id.iv_more)
    LinearLayout ivMore;
    @BindView(R.id.iv_headimg)
    ImageView ivHeadimg;
    @BindView(R.id.tv_age)
    TextView tvAge;
    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.isattaction)
    ImageView isattaction;
    @BindView(R.id.tv_guanzhu)
    TextView tvGuanzhu;
    @BindView(R.id.tv_dynamic_text1)
    TextView tvDynamicText1;
    @BindView(R.id.rl_text)
    RelativeLayout rlText;
    @BindView(R.id.rc_image1)
    NineGridTestLayout rcImage1;
    @BindView(R.id.rl_img)
    RelativeLayout rlImg;
    @BindView(R.id.tv_dynamic_text2)
    TextView tvDynamicText2;
    @BindView(R.id.rc_image)
    NineGridTestLayout rcImage;
    @BindView(R.id.rl_imgandtext)
    RelativeLayout rlImgandtext;
    @BindView(R.id.video_player1)
    SampleCoverVideo videoPlayer1;
    @BindView(R.id.rl_video)
    RelativeLayout rlVideo;
    @BindView(R.id.tv_dynamic_text)
    TextView tvDynamicText;
    @BindView(R.id.video_player2)
    SampleCoverVideo videoPlayer2;
    @BindView(R.id.rl_videoandtext)
    RelativeLayout rlVideoandtext;
    @BindView(R.id.rc_topic)
    RecyclerView rcTopic;
    @BindView(R.id.iv_dianzan)
    ImageView ivDianzan;
    @BindView(R.id.tv_dianzan_count)
    TextView tvDianzanCount;
    @BindView(R.id.rl_dianzan)
    RelativeLayout rlDianzan;
    @BindView(R.id.iv_pinglun)
    ImageView ivPinglun;
    @BindView(R.id.tv_pinglun_count)
    TextView tvPinglunCount;
    @BindView(R.id.rl_pinglun)
    RelativeLayout rlPinglun;
    @BindView(R.id.rl_zhuanfa)
    RelativeLayout rlZhuanfa;
    @BindView(R.id.iv_collection)
    ImageView ivCollection;
    @BindView(R.id.rl_collection)
    RelativeLayout rlCollection;
    @BindView(R.id.rl_tips)
    RelativeLayout rlTips;
    @BindView(R.id.rc_comment)
    RecyclerView rcComment;
    @BindView(R.id.rl_notdata)
    RelativeLayout rlNotdata;
    @BindView(R.id.rl_bottom)
    RelativeLayout rlBottom;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.bt_confirm)
    Button btConfirm;
    @BindView(R.id.tv_distance)
    TextView tvDistance;
    @BindView(R.id.sv)
    SpringView sv;
    @BindView(R.id.rl_item)
    RelativeLayout rlItem;
    @BindView(R.id.rl_send)
    RelativeLayout rlSend;
    private int id;
    private int userId;
    private DynamicDetailsBean.ObjectBean bean;
    private DynamicDetailsBean.ObjectBean.UserInfoBean userInfo;
    public static final String TAG = "ListNormalAdapter22";
    int page = 1;
    int count = 15;
    private PopupWindow mPopupWindow;
    private Tencent mTencent;
    String wechatUrl = "http://web.p.qq.com/qqmpmobile/aio/app.html?id=101906973";
    private List<CommentBean.ObjectBean> AllList = new ArrayList<>();
    @Override
    protected int getResId() {
        return R.layout.activity_dynamicdetails;
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
    @Override
    protected void getData() {

        Intent intent =
                getIntent();
        id = intent.getIntExtra("id", 0);
        userId = Integer.valueOf(Common.getUserId());

        //设置状态栏背景颜色及字体颜色
        StatusBarUtil.setGradientColor(this, toolbar);
        StatusBarUtil.setDarkMode(this);

        //绑定单击事件
        ivBack.setOnClickListener(this);
        ivMore.setOnClickListener(this);
        ivHeadimg.setOnClickListener(this);
        tvGuanzhu.setOnClickListener(this);
        rlDianzan.setOnClickListener(this);
        rlZhuanfa.setOnClickListener(this);
        ivCollection.setOnClickListener(this);
        btConfirm.setOnClickListener(this);
        rlPinglun.setOnClickListener(this);

        mTencent = Tencent.createInstance("101906973", this);

        sv.setHeader(new AliHeader(DynamicDetailsActivity.this));

        //重新测量键盘弹出的界面整体布局
        addLayoutListener(rlItem,rlSend);

        //获取动态
        getDetails();
        //获取评论
        getComment(page,count);
        sv.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                AllList.clear();
                page = 1;
                getComment(page, count);
                getDetails();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        sv.onFinishFreshAndLoad();
                    }
                },1000);
            }

            @Override
            public void onLoadmore() {
                page++;
                getComment(page, count);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        sv.onFinishFreshAndLoad();
                    }
                },1000);
            }
        });
        etContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!TextUtils.isEmpty(s) && s.length()>0){
                    btConfirm.setBackground(getResources().getDrawable(R.drawable.content_bt_bg));
                    btConfirm.setEnabled(true);
                }else {
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
    protected BasePresenter initPresenter() {
        return null;
    }

    //绑定单击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //退出当前界面
            case R.id.iv_back:
                finish();
                break;
            //右上角 省略号
            case R.id.iv_more:

                break;
            //点击头像进入用户主页
            case R.id.iv_headimg:
                Intent intent = new Intent(this, PersonHomeActivity.class);
                SaveIntentMsgBean saveIntentMsgBean = new SaveIntentMsgBean();
                saveIntentMsgBean.setId(userId);
                //2标记传入姓名  1标记传入id
                saveIntentMsgBean.setFlag(1);
                intent.putExtra("msg",saveIntentMsgBean);
                startActivity(intent);
                break;
            //点击关注
            case R.id.tv_guanzhu:
                int userInfoId = userInfo.getId();

                if (bean.isIsAttention()) {
                    CustomDialogCancleFollow.Builder builder = new CustomDialogCancleFollow.Builder(DynamicDetailsActivity.this);
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //开始执行设置不可点击 防止多次点击发生冲突
                            tvGuanzhu.setEnabled(false);

                            dialog.dismiss();
                            NetUtils.getInstance().getApis()
                                    .doCancleFollow(userId, userInfoId)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Observer<FollowBean>() {
                                        @Override
                                        public void onSubscribe(Disposable d) {

                                        }

                                        @Override
                                        public void onNext(FollowBean followBean) {
                                            //处理结束后恢复点击
                                            tvGuanzhu.setEnabled(true);
                                            if (followBean.getMsg().equals("取消关注成功")) {

                                                //重新获取数据
                                                getDetails();
                                                bean.setIsAttention(false);
                                                // TODO: 2020/12/15 0015 发送通知
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
                    });
                    builder.setNegativeButton("取消",
                            new android.content.DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    builder.create().show();
                } else {
                    //开始执行设置不可点击 防止多次点击发生冲突
                    tvGuanzhu.setEnabled(false);

                    //关注
                    NetUtils.getInstance().getApis()
                            .doFollow(userId, userInfoId)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<FollowBean>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(FollowBean followBean) {
                                    //处理结束后恢复点击
                                    tvGuanzhu.setEnabled(true);
                                    if (followBean.getMsg().equals("关注成功")) {

                                        //重新获取数据
                                        getDetails();

                                        bean.setIsAttention(true);
                                        // TODO: 2020/12/15 0015 推送通知


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
                break;
            //点赞
            case R.id.rl_dianzan:
                if (bean.isIsClick()) {
                    //取消点赞
                    //开始执行设置不可点击 防止多次点击发生冲突
                    rlDianzan.setEnabled(false);
                    NetUtils.getInstance().getApis()
                            .doCancleDianzan(1, id, userId)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<DianzanBean>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(DianzanBean dianzanBean) {
                                    //处理结束后恢复点击
                                    rlDianzan.setEnabled(true);

                                    //取消点赞成功
                                    ivDianzan.setImageResource(R.mipmap.weidianzan);
                                    bean.setIsClick(false);

                                    //取消点赞成功数量加一
                                    String s = tvDianzanCount.getText().toString();
                                    Integer integer = Integer.valueOf(s);

                                    integer -= 1;
                                    tvDianzanCount.setText(integer + "");

                                }

                                @Override
                                public void onError(Throwable e) {

                                }

                                @Override
                                public void onComplete() {

                                }
                            });
                } else {
                    //点赞
                    rlDianzan.setEnabled(false);

                    NetUtils.getInstance().getApis()
                            .doDianzan(userId, 1, id)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<DianzanBean>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(DianzanBean dianzanBean) {

                                    //处理结束后恢复点击
                                    rlDianzan.setEnabled(true);

                                    if (dianzanBean.getObject().equals("插入成功")) {
                                        //点赞成功
                                        ivDianzan.setImageResource(R.mipmap.dianzan);
                                        bean.setIsClick(true);

                                        //点赞成功数量加一
                                        String s = tvDianzanCount.getText().toString();
                                        Integer integer = Integer.valueOf(s);
                                        integer += 1;
                                        tvDianzanCount.setText(integer + "");
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
                break;
            //弹出转发弹框
            case R.id.rl_zhuanfa:
                showSelect();
                break;
            //点击收藏
            case R.id.iv_collection:
                //判断是否已经收藏过
                if (bean.isIsCollect()) {
                    //已经收藏过 取消收藏
                    //先设置不可点击 避免多次点击冲突
                    ivCollection.setEnabled(false);
                    NetUtils.getInstance().getApis().doCancleCollectDynamic(userId,id)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<CollectDynamicBean>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(CollectDynamicBean collectDynamicBean) {

                                    Toast.makeText(DynamicDetailsActivity.this, "取消收藏成功", Toast.LENGTH_SHORT).show();
                                    //执行完毕可点击
                                    ivCollection.setEnabled(true);

                                    ivCollection.setImageResource(R.mipmap.weishoucang);
                                    bean.setIsCollect(false);
                                }

                                @Override
                                public void onError(Throwable e) {

                                }

                                @Override
                                public void onComplete() {

                                }
                            });

                } else {
                    //未收藏过 收藏动态
                    ivCollection.setEnabled(false);
                    NetUtils.getInstance().getApis()
                            .doCollectDynamic(userId,id)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<CollectDynamicBean>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(CollectDynamicBean collectDynamicBean) {
                                    Toast.makeText(DynamicDetailsActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
                                    //执行完毕可点击
                                    ivCollection.setEnabled(true);

                                    ivCollection.setImageResource(R.mipmap.yishoucang);
                                    bean.setIsCollect(true);
                                }

                                @Override
                                public void onError(Throwable e) {

                                }

                                @Override
                                public void onComplete() {

                                }
                            });
                }
                break;
            //发送评论
            case R.id.bt_confirm:
                String s = etContent.getText().toString();
                NetUtils.getInstance().getApis()
                        .doWriteComment(userId,bean.getUserId(),s,0,bean.getId(),0)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<CollectDynamicBean>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(CollectDynamicBean collectDynamicBean) {
                                if(collectDynamicBean.getObject().equals("写评论成功")){
                                    //再次请求评论
                                    AllList.clear();
                                    getComment(1,15);
                                    getDetails();
                                    KeyBoardUtils.closeKeyboard(etContent);
                                    etContent.setText("");
                                }
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        });
                break;
            //去评论
            case R.id.rl_pinglun:
                KeyBoardUtils.openKeyBoard(etContent);
                break;
        }
    }

    @SuppressLint("ResourceAsColor")
    public static Spannable getWeiBoContent(final Context context, String source, TextView tv) {

        SpannableStringBuilder spannable = new SpannableStringBuilder(source);
        // 定义正则表达式
        String AT = "@[\\u4e00-\\u9fa5\\w\\-]+";// @人
        String TOPIC = "#([^\\#|.]+)#";// ##话题
        //设置正则
        Pattern pattern = Pattern.compile("(" + AT + ")|" + "(" + TOPIC + ")");
        Matcher matcher = pattern.matcher(spannable);

        Log.d("xxx", matcher.groupCount() + "");

        while (matcher.find()) {
            // 根据group的括号索引，可得出具体匹配哪个正则(0代表全部，1代表第一个括号)
            final String at = matcher.group(1);
            final String topic = matcher.group(2);
            // 处理@符号
            if (at != null) {
                //获取匹配位置
                int start = matcher.start(1);
                int end = start + at.length();

                tv.setMovementMethod(LinkMovementMethod.getInstance());
                spannable.setSpan(new NewDynamicAdapter.MyClickableSpanAt() {
                    @Override
                    public void onClick(View widget) {
                        //这里需要做跳转用户的实现，先用一个Toast代替
                        String substring = at.substring(1);

                        Intent intent = new Intent(context, PersonHomeActivity.class);
                        SaveIntentMsgBean saveIntentMsgBean = new SaveIntentMsgBean();
                        saveIntentMsgBean.setMsg(substring);
                        //2标记传入姓名  1标记传入id
                        saveIntentMsgBean.setFlag(2);
                        intent.putExtra("msg", saveIntentMsgBean);
                        context.startActivity(intent);
                    }
                }, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            }

            // 处理话题##符号
            if (topic != null) {
                int start = matcher.start(2);
                int end = start + topic.length();

                tv.setMovementMethod(LinkMovementMethod.getInstance());
                spannable.setSpan(new NewDynamicAdapter.MyClickableSpanTopic() {
                    @Override
                    public void onClick(@NonNull View widget) {
                        String substring = topic.substring(1, topic.length() - 1);

                        Intent intent = new Intent(context, TopicDetailsActivity.class);
                        SaveIntentMsgBean saveIntentMsgBean = new SaveIntentMsgBean();
                        saveIntentMsgBean.setMsg(substring);
                        //2标记传入话题名  1标记传入id
                        saveIntentMsgBean.setFlag(2);
                        intent.putExtra("msg", saveIntentMsgBean);
                        context.startActivity(intent);

                    }
                }, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            }
        }
        tv.setText(spannable);

        return spannable;
    }
    public void getComment(int page,int pagesize){
        NetUtils.getInstance().getApis()
                .doGetComment(id,0,userId,page,pagesize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CommentBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(CommentBean commentBean) {
                        List<CommentBean.ObjectBean> list = commentBean.getObject();
                        if(list.size()>0 && list!=null){
                            rlNotdata.setVisibility(View.GONE);
                            rcComment.setVisibility(View.VISIBLE);

                            AllList.addAll(list);

                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DynamicDetailsActivity.this, RecyclerView.VERTICAL, false);
                            rcComment.setLayoutManager(linearLayoutManager);
                            //创建适配器
                            CommentAdapter commentAdapter = new CommentAdapter(DynamicDetailsActivity.this, AllList);
                            rcComment.setAdapter(commentAdapter);

                        }else{
                            if(AllList.size()>0){
                                Toast.makeText(DynamicDetailsActivity.this, "没有更多内容了", Toast.LENGTH_SHORT).show();
                            }else{

                                rlNotdata.setVisibility(View.VISIBLE);
                                rcComment.setVisibility(View.GONE);
                            }
                        }
                        if(list.size()>14){
                            sv.setFooter(new AliFooter(DynamicDetailsActivity.this));
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
    public void getDetails() {
        //获取指定id下动态的详情
        showDialog();
        NetUtils.getInstance().getApis()
                .dogetDynamicDetails(id, userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DynamicDetailsBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @SuppressLint({"ResourceAsColor", "SetTextI18n"})
                    @Override
                    public void onNext(DynamicDetailsBean dynamicDetailsBean) {
                        hideDialog();
                        userInfo = dynamicDetailsBean.getObject().getUserInfo();
                        bean = dynamicDetailsBean.getObject();
                        List<DynamicDetailsBean.ObjectBean.TopicsBean> topics =
                                bean.getTopics();
                        //设置头像
                        Glide.with(DynamicDetailsActivity.this).load(userInfo.getHeadImg()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(ivHeadimg);
                        //设置姓名
                        tvUsername.setText(userInfo.getNickName());
                        //设置角色
                        //判断性别是否保密
                        String userRole = userInfo.getUserRole();
                        if (userRole!=null){
                            if (userRole.equals("保密")) {
                                tvAge.setVisibility(View.GONE);
                            }else{
                                tvAge.setText(userInfo.getUserRole());
                            }
                        }else{
                            tvAge.setVisibility(View.GONE);
                        }

                        //判断当前用户与动态发布者 是一人 隐藏关注按钮
                        if(userId==userInfo.getId()){
                            tvGuanzhu.setVisibility(View.GONE);
                        }
                        
                        int attestation = userInfo.getAttestation();
                        //认证等级
                        if (attestation == 0) {
                            isattaction.setVisibility(View.GONE);
                        } else if(attestation==1){
                            isattaction.setImageResource(R.mipmap.qingtong);
                        }else if(attestation==2){
                            isattaction.setImageResource(R.mipmap.baiyin);
                        }else if(attestation==3){
                            isattaction.setImageResource(R.mipmap.huangjin);
                        }else if(attestation==4){
                            isattaction.setImageResource(R.mipmap.bojin);
                        }else if (attestation==5){
                            isattaction.setImageResource(R.mipmap.zuanshi);
                        }

                        //判断是否点赞
                        if (bean.isIsClick()) {
                            ivDianzan.setImageResource(R.mipmap.dianzan);
                        } else {
                            ivDianzan.setImageResource(R.mipmap.weidianzan);
                        }
                        //判断是否收藏
                        if (bean.isIsCollect()) {
                            ivCollection.setImageResource(R.mipmap.yishoucang);
                        } else {
                            ivCollection.setImageResource(R.mipmap.weishoucang);
                        }
                        //设置点赞数
                        tvDianzanCount.setText(bean.getClickNum() + "");
                        //设置评论数
                        tvPinglunCount.setText(bean.getCommentCount() + "");
                        //判断是否关注
                        if (bean.isIsAttention()) {
                            tvGuanzhu.setBackground(DynamicDetailsActivity.this.getResources().getDrawable(R.drawable.newdynamic_yiguanzhu));
                            tvGuanzhu.setText("已关注");
                            tvGuanzhu.setTextColor(R.color.color_999999);
                        } else {
                            tvGuanzhu.setBackground(DynamicDetailsActivity.this.getResources().getDrawable(R.drawable.newdynamic_weiguanzhu));
                            tvGuanzhu.setText("关注");
                            tvGuanzhu.setTextColor(Color.BLACK);
                        }

                        //获取发布时位置距离当前的距离
                        String distance = bean.getDistance();
                        if (distance != null) {
                            tvDistance.setVisibility(View.VISIBLE);

                            double a = Double.valueOf(distance);
                            long round = Math.round(a);
                            if(round<1000){
                                tvDistance.setText(round + "m");
                            }else{
                                tvDistance.setText(round/1000 + "km");
                            }

                        } else {
                            tvDistance.setVisibility(View.GONE);
                        }
                        //获取发布时间
                        String createTime = bean.getCreateTime();
                        if(createTime!=null){
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd HH:mm:ss", Locale.SIMPLIFIED_CHINESE);
                            try {
                                Date parse = sdf.parse(createTime);

                                long time = parse.getTime();

                                //获取当前时间
                                long l = System.currentTimeMillis();
                                //获取发布过的时长
                                long difference = l - time;

                                //时长大于12小时 显示日期
                                if (difference > 43200000) {
                                    tvTime.setText(createTime);
                                }
                                //时长小于12小时 展示时间
                                if (difference > 1800000 && difference < 43200000) {
                                    String[] s = createTime.split(" ");
                                    tvTime.setText(s[1]);
                                }
                                if (difference > 1200000 && difference < 1800000) {
                                    tvTime.setText("半小时前发布");
                                }
                                if (difference > 600000 && difference < 1200000) {
                                    tvTime.setText("20分钟前发布");
                                }
                                if (difference > 300000 && difference < 600000) {
                                    tvTime.setText("10分钟前发布");
                                }
                                if (difference > 240000 && difference < 300000) {
                                    tvTime.setText("5分钟前发布");
                                }
                                if (difference > 180000 && difference < 240000) {
                                    tvTime.setText("4分钟前发布");
                                }
                                if (difference > 120000 && difference < 180000) {
                                    tvTime.setText("3分钟前发布");
                                }
                                if (difference > 60000 && difference < 120000) {
                                    tvTime.setText("2分钟前发布");
                                }
                                if (difference < 60000) {
                                    tvTime.setText("1分钟前发布");
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                        //判断动态类型
                        int imgType = bean.getImgType();
                        if (imgType == 1) {
                            rlText.setVisibility(View.VISIBLE);
                            rlImg.setVisibility(View.GONE);
                            rlImgandtext.setVisibility(View.GONE);
                            rlVideo.setVisibility(View.GONE);
                            rlVideoandtext.setVisibility(View.GONE);

                            getWeiBoContent(DynamicDetailsActivity.this, bean.getContentInfo(), tvDynamicText1);
                        }
                        if (imgType == 2) {
                            rlText.setVisibility(View.GONE);
                            rlImg.setVisibility(View.VISIBLE);
                            rlImgandtext.setVisibility(View.GONE);
                            rlVideo.setVisibility(View.GONE);
                            rlVideoandtext.setVisibility(View.GONE);

                            String contentImg = bean.getContentImg();
                            String[] split = contentImg.split(",");

                            Log.d("xxx", split.length + "");
                            List<String> imglist = new ArrayList<>();

                            for (int i = 0; i < split.length; i++) {
                                imglist.add(split[i].trim()+"?imageView2/0/format/jpg/w/400");
                            }

                            rcImage1.setIsShowAll(false); //当传入的图片数超过9张时，是否全部显示
                            rcImage1.setSpacing(5); //动态设置图片之间的间隔
                            rcImage1.setUrlList(imglist); //最后再设置图片url

                        }
                        if (imgType == 3) {
                            rlText.setVisibility(View.GONE);
                            rlImg.setVisibility(View.GONE);
                            rlImgandtext.setVisibility(View.GONE);
                            rlVideo.setVisibility(View.VISIBLE);
                            rlVideoandtext.setVisibility(View.GONE);

                            //设置播放视频
                            String contentImg = bean.getContentImg();

                            Bitmap netVideoBitmap = getNetVideoBitmap(contentImg);
                            //设置封面
                            videoPlayer1.loadCoverImage(contentImg, netVideoBitmap);

                            videoPlayer1.setUpLazy(contentImg, true, null, null, "");

                            //防止错位设置
                            videoPlayer1.setPlayTag(TAG);
                            videoPlayer1.setLockLand(true);
                            //是否根据视频尺寸，自动选择竖屏全屏或者横屏全屏，这个标志为和 setLockLand 冲突，需要和 orientationUtils 使用
                            videoPlayer1.setAutoFullWithSize(false);
                            //音频焦点冲突时是否释放
                            videoPlayer1.setReleaseWhenLossAudio(false);
                            //全屏动画
                            videoPlayer1.setShowFullAnimation(true);
                            //小屏时不触摸滑动
                            videoPlayer1.setIsTouchWiget(false);

                        }
                        if (imgType == 21) {
                            rlText.setVisibility(View.GONE);
                            rlImg.setVisibility(View.GONE);
                            rlImgandtext.setVisibility(View.VISIBLE);
                            rlVideo.setVisibility(View.GONE);
                            rlVideoandtext.setVisibility(View.GONE);
                            String contentImg = bean.getContentImg();
                            String[] split = contentImg.split(",");

                            Log.d("xxx", split.length + "");
                            List<String> imglist = new ArrayList<>();

                            for (int i = 0; i < split.length; i++) {
                                imglist.add(split[i].trim()+"?imageView2/0/format/jpg/w/400");
                            }

                            rcImage.setIsShowAll(false); //当传入的图片数超过9张时，是否全部显示
                            rcImage.setSpacing(5); //动态设置图片之间的间隔
                            rcImage.setUrlList(imglist); //最后再设置图片url

                            getWeiBoContent(DynamicDetailsActivity.this, bean.getContentInfo(), tvDynamicText2);
                        }
                        if (imgType == 31) {
                            rlText.setVisibility(View.GONE);
                            rlImg.setVisibility(View.GONE);
                            rlImgandtext.setVisibility(View.GONE);
                            rlVideo.setVisibility(View.GONE);
                            rlVideoandtext.setVisibility(View.VISIBLE);

                            //设置播放视频
                            String contentImg = bean.getContentImg();

                            Bitmap netVideoBitmap = getNetVideoBitmap(contentImg);
                            //设置封面
                            videoPlayer2.loadCoverImage(contentImg, netVideoBitmap);

                            videoPlayer2.setUpLazy(contentImg, true, null, null, "");

                            //防止错位设置
                            videoPlayer2.setPlayTag(TAG);
                            videoPlayer2.setLockLand(true);
                            //是否根据视频尺寸，自动选择竖屏全屏或者横屏全屏，这个标志为和 setLockLand 冲突，需要和 orientationUtils 使用
                            videoPlayer2.setAutoFullWithSize(false);
                            //音频焦点冲突时是否释放
                            videoPlayer2.setReleaseWhenLossAudio(false);
                            //全屏动画
                            videoPlayer2.setShowFullAnimation(true);
                            //小屏时不触摸滑动
                            videoPlayer2.setIsTouchWiget(false);

                            getWeiBoContent(DynamicDetailsActivity.this, bean.getContentInfo(), tvDynamicText);
                        }

                        //展示话题
                        if (topics.size() > 0 && topics != null) {
                            //设置展示话题
                            rcTopic.setVisibility(View.VISIBLE);
                            GridLayoutManager gridLayoutManager = new GridLayoutManager(DynamicDetailsActivity.this, 4);
                            rcTopic.setLayoutManager(gridLayoutManager);
                            TopicsAdapter topicsAdapter = new TopicsAdapter(DynamicDetailsActivity.this, topics);
                            rcTopic.setAdapter(topicsAdapter);

                        } else {
                            rcTopic.setVisibility(View.GONE);
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        hideDialog();
                        Toast.makeText(DynamicDetailsActivity.this, "获取数据失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    public void showSelect() {
        //添加成功后处理
        mPopupWindow = new PopupWindow();
        mPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        View view = LayoutInflater.from(DynamicDetailsActivity.this).inflate(R.layout.dialog_share, null);

        LinearLayout qq = view.findViewById(R.id.ll_share_qq);
        LinearLayout space = view.findViewById(R.id.ll_share_space);
        LinearLayout wechat = view.findViewById(R.id.ll_share_Wechat);
        LinearLayout moments = view.findViewById(R.id.ll_share_wechatmoments);

        qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //分享到QQ好友
                onClickShare();
            }
        });
        space.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //分享到QQ空间
                onClickShareQzone();
            }
        });
        wechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //分享到微信好友
                onclickShareWechatFriend();
            }
        });
        moments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //分享到朋友圈
                onclickShareWechatmoments();
            }
        });
        //popwindow设置属性
        mPopupWindow.setContentView(view);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setWindowAlpa(false);
            }
        });
        show(view);
    }
    //分享到QQ信息
    private void onClickShare() {
        int imgType = bean.getImgType();
        Bundle params = new Bundle();

        if(imgType==1){
            params.putString(QQShare.SHARE_TO_QQ_SUMMARY, bean.getContentInfo());
            params.putString(QQShare.SHARE_TO_QQ_TITLE, userInfo.getNickName()+"的动态");
            params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "http://web.p.qq.com/qqmpmobile/aio/app.html?id=101906973");
            params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
            params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE);
        }
        if(imgType==21){
            String[] split = bean.getContentImg().split(",");

            params.putString(QQShare.SHARE_TO_QQ_SUMMARY, bean.getContentInfo());
            params.putString(QQShare.SHARE_TO_QQ_TITLE, userInfo.getNickName()+"的动态");
            params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "http://web.p.qq.com/qqmpmobile/aio/app.html?id=101906973");
            params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, split[0]);
            params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
            params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE);
        }
        if(imgType==2){
            String[] split = bean.getContentImg().split(",");
            params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, split[0]);
            params.putString(QQShare.SHARE_TO_QQ_TITLE, userInfo.getNickName()+"的动态");
            params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "http://web.p.qq.com/qqmpmobile/aio/app.html?id=101906973");
            params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare. SHARE_TO_QQ_TYPE_IMAGE);
            params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE);
        }
        if(imgType==3){
            params.putString(QQShare.SHARE_TO_QQ_AUDIO_URL, bean.getContentImg());
            params.putString(QQShare.SHARE_TO_QQ_TITLE, userInfo.getNickName()+"的动态");
            params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "http://web.p.qq.com/qqmpmobile/aio/app.html?id=101906973");
            params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare. SHARE_TO_QQ_TYPE_AUDIO);
            params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE);
        }
        if(imgType==31){
            params.putString(QQShare.SHARE_TO_QQ_SUMMARY, bean.getContentInfo());
            params.putString(QQShare.SHARE_TO_QQ_TITLE, userInfo.getNickName()+"的动态");
            params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "http://web.p.qq.com/qqmpmobile/aio/app.html?id=101906973");
            params.putString(QQShare.SHARE_TO_QQ_AUDIO_URL, bean.getContentImg());
            params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
            params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE);
        }
        mTencent.shareToQQ(this, params, new IUiListener() {
            @Override
            public void onComplete(Object o) {
                Log.e(TAG, "分享成功: " + o.toString());
            }

            @Override
            public void onError(UiError uiError) {
                Log.e(TAG, "分享失败: " + uiError.toString());
            }

            @Override
            public void onCancel() {
                Log.e(TAG, "取消分享");

            }
        });

    }

    //分享到QQ空间
    private void onClickShareQzone() {
        int imgType = bean.getImgType();
        Bundle params = new Bundle();

        if(imgType==1){
            params.putString(QQShare.SHARE_TO_QQ_SUMMARY, bean.getContentInfo());
            params.putString(QQShare.SHARE_TO_QQ_TITLE, userInfo.getNickName()+"的动态");
            params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "http://web.p.qq.com/qqmpmobile/aio/app.html?id=101906973");
            params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
            params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
        }
        if(imgType==21){
            String[] split = bean.getContentImg().split(",");
            params.putString(QQShare.SHARE_TO_QQ_SUMMARY, bean.getContentInfo());
            params.putString(QQShare.SHARE_TO_QQ_TITLE, userInfo.getNickName()+"的动态");
            params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "http://web.p.qq.com/qqmpmobile/aio/app.html?id=101906973");
            params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, split[0]);
            params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
            params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
        }
        if(imgType==2){
            String[] split = bean.getContentImg().split(",");
            params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, split[0]);
            params.putString(QQShare.SHARE_TO_QQ_TITLE, userInfo.getNickName()+"的动态");
            params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "http://web.p.qq.com/qqmpmobile/aio/app.html?id=101906973");
            params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare. SHARE_TO_QQ_TYPE_IMAGE);
            params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);

        }
        if(imgType==3){
            params.putString(QQShare.SHARE_TO_QQ_AUDIO_URL, bean.getContentImg());
            params.putString(QQShare.SHARE_TO_QQ_TITLE, userInfo.getNickName()+"的动态");
            params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "http://web.p.qq.com/qqmpmobile/aio/app.html?id=101906973");
            params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare. SHARE_TO_QQ_TYPE_AUDIO);
            params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);

        }
        if(imgType==31){
            params.putString(QQShare.SHARE_TO_QQ_SUMMARY, bean.getContentInfo());
            params.putString(QQShare.SHARE_TO_QQ_TITLE, userInfo.getNickName()+"的动态");
            params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "http://web.p.qq.com/qqmpmobile/aio/app.html?id=101906973");
            params.putString(QQShare.SHARE_TO_QQ_AUDIO_URL, bean.getContentImg());
            params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
            params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);

        }
        mTencent.shareToQQ(this, params, new IUiListener() {
            @Override
            public void onComplete(Object o) {
                Log.e(TAG, "分享成功: " + o.toString());
            }
            @Override
            public void onError(UiError uiError) {

            }

            @Override
            public void onCancel() {

            }
        });
    }

    //分享到微信好友
    private void onclickShareWechatFriend() {
        int imgType = bean.getImgType();
        if (!App.getWXApi().isWXAppInstalled()){
            Toast.makeText(this, "您未安装微信", Toast.LENGTH_SHORT).show();
            return;
        }else{
            if(imgType==1){
                // TODO: 2020/12/18 0018 要跳转的链接

                //初始化 WXImageObject 和 WXMediaMessage 对象
                WXWebpageObject localWXWebpageObject = new WXWebpageObject();
                localWXWebpageObject.webpageUrl = wechatUrl;

                WXMediaMessage msg = new WXMediaMessage(localWXWebpageObject);
                msg.description = bean.getContentInfo();

                msg.title = userInfo.getNickName()+"的动态";
                //构造一个Req
                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.transaction = String.valueOf(System.currentTimeMillis());
                req.message = msg;
                req.scene = SendMessageToWX.Req.WXSceneSession;
                //调用api接口，发送数据到微信
                App.getWXApi().sendReq(req);
            }
            if(imgType==2){
                String[] split = bean.getContentImg().split(",");

                Glide.with(DynamicDetailsActivity.this).asBitmap().load(split[0])
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                WXWebpageObject localWXWebpageObject = new WXWebpageObject();
                                localWXWebpageObject.webpageUrl = wechatUrl;
                                WXMediaMessage localWXMediaMessage = new WXMediaMessage(
                                        localWXWebpageObject);
                                localWXMediaMessage.title = userInfo.getNickName()+"的动态";//不能太长，否则微信会提示出错。不过博主没验证过具体能输入多长。
                                localWXMediaMessage.description = "";
                                localWXMediaMessage.thumbData = getBitmapBytes(resource, false);

                                //构造一个Req
                                SendMessageToWX.Req req = new SendMessageToWX.Req();
                                req.transaction = String.valueOf(System.currentTimeMillis());
                                req.message = localWXMediaMessage;
                                req.scene = SendMessageToWX.Req.WXSceneSession;
                                //调用api接口，发送数据到微信
                                App.getWXApi().sendReq(req);
                            }
                        });
            }
            if(imgType==21){
                String[] split = bean.getContentImg().split(",");

                Glide.with(DynamicDetailsActivity.this).asBitmap().load(split[0])
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                WXWebpageObject localWXWebpageObject = new WXWebpageObject();
                                localWXWebpageObject.webpageUrl = wechatUrl;
                                WXMediaMessage localWXMediaMessage = new WXMediaMessage(
                                        localWXWebpageObject);
                                localWXMediaMessage.title = userInfo.getNickName()+"的动态";//不能太长，否则微信会提示出错。不过博主没验证过具体能输入多长。
                                localWXMediaMessage.description = bean.getContentInfo();
                                localWXMediaMessage.thumbData = getBitmapBytes(resource, false);

                                //构造一个Req
                                SendMessageToWX.Req req = new SendMessageToWX.Req();
                                req.transaction = String.valueOf(System.currentTimeMillis());
                                req.message = localWXMediaMessage;
                                req.scene = SendMessageToWX.Req.WXSceneSession;
                                //调用api接口，发送数据到微信
                                App.getWXApi().sendReq(req);
                            }
                        });
            }
            if(imgType==3){
                //初始化一个WXVideoObject，填写url
                WXVideoObject video = new WXVideoObject();
                video.videoUrl =bean.getContentImg();

                //用 WXVideoObject 对象初始化一个 WXMediaMessage 对象
                WXMediaMessage msg = new WXMediaMessage(video);
                msg.title =userInfo.getNickName()+"的动态";

                Bitmap netVideoBitmap = getNetVideoBitmap(bean.getContentImg());
                //设置封面
                msg.thumbData =getBitmapBytes(netVideoBitmap, false);

                //构造一个Req
                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.transaction = String.valueOf(System.currentTimeMillis());
                req.message =msg;
                req.scene = SendMessageToWX.Req.WXSceneSession;

                //调用api接口，发送数据到微信
                App.getWXApi().sendReq(req);
            }
            if(imgType==31){
                //初始化一个WXVideoObject，填写url
                WXVideoObject video = new WXVideoObject();
                video.videoUrl =bean.getContentImg();

                //用 WXVideoObject 对象初始化一个 WXMediaMessage 对象
                WXMediaMessage msg = new WXMediaMessage(video);
                msg.title =userInfo.getNickName()+"的动态";
                msg.description= bean.getContentInfo();
                Bitmap netVideoBitmap = getNetVideoBitmap(bean.getContentImg());
                //设置封面
                msg.thumbData =getBitmapBytes(netVideoBitmap, false);

                //构造一个Req
                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.transaction = String.valueOf(System.currentTimeMillis());
                req.message =msg;
                req.scene = SendMessageToWX.Req.WXSceneSession;

                //调用api接口，发送数据到微信
                App.getWXApi().sendReq(req);

            }
        }



    }

    //分享到微信朋友圈
    private void onclickShareWechatmoments() {
        int imgType = bean.getImgType();
        if (!App.getWXApi().isWXAppInstalled()){
            Toast.makeText(this, "您未安装微信", Toast.LENGTH_SHORT).show();
            return;
        }else{
            if(imgType==1){
                // TODO: 2020/12/18 0018 要跳转的链接

                //初始化 WXImageObject 和 WXMediaMessage 对象
                WXWebpageObject localWXWebpageObject = new WXWebpageObject();
                localWXWebpageObject.webpageUrl = wechatUrl;

                WXMediaMessage msg = new WXMediaMessage(localWXWebpageObject);
                msg.description = bean.getContentInfo();

                msg.title = userInfo.getNickName()+"的动态";
                //构造一个Req
                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.transaction = String.valueOf(System.currentTimeMillis());
                req.message = msg;
                req.scene = SendMessageToWX.Req.WXSceneTimeline;
                //调用api接口，发送数据到微信
                App.getWXApi().sendReq(req);
            }
            if(imgType==2){
                String[] split = bean.getContentImg().split(",");

                Glide.with(DynamicDetailsActivity.this).asBitmap().load(split[0])
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                WXWebpageObject localWXWebpageObject = new WXWebpageObject();
                                localWXWebpageObject.webpageUrl = wechatUrl;
                                WXMediaMessage localWXMediaMessage = new WXMediaMessage(
                                        localWXWebpageObject);
                                localWXMediaMessage.title = userInfo.getNickName()+"的动态";//不能太长，否则微信会提示出错。不过博主没验证过具体能输入多长。
                                localWXMediaMessage.description = "";
                                localWXMediaMessage.thumbData = getBitmapBytes(resource, false);

                                //构造一个Req
                                SendMessageToWX.Req req = new SendMessageToWX.Req();
                                req.transaction = String.valueOf(System.currentTimeMillis());
                                req.message = localWXMediaMessage;
                                req.scene = SendMessageToWX.Req.WXSceneTimeline;
                                //调用api接口，发送数据到微信
                                App.getWXApi().sendReq(req);
                            }
                        });
            }
            if(imgType==21){
                String[] split = bean.getContentImg().split(",");

                Glide.with(DynamicDetailsActivity.this).asBitmap().load(split[0])
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                WXWebpageObject localWXWebpageObject = new WXWebpageObject();
                                localWXWebpageObject.webpageUrl = wechatUrl;
                                WXMediaMessage localWXMediaMessage = new WXMediaMessage(
                                        localWXWebpageObject);
                                localWXMediaMessage.title = userInfo.getNickName()+"的动态";//不能太长，否则微信会提示出错。不过博主没验证过具体能输入多长。
                                localWXMediaMessage.description = bean.getContentInfo();
                                localWXMediaMessage.thumbData = getBitmapBytes(resource, false);

                                //构造一个Req
                                SendMessageToWX.Req req = new SendMessageToWX.Req();
                                req.transaction = String.valueOf(System.currentTimeMillis());
                                req.message = localWXMediaMessage;
                                req.scene = SendMessageToWX.Req.WXSceneTimeline;
                                //调用api接口，发送数据到微信
                                App.getWXApi().sendReq(req);
                            }
                        });
            }
            if(imgType==3){
                //初始化一个WXVideoObject，填写url
                WXVideoObject video = new WXVideoObject();
                video.videoUrl =bean.getContentImg();

                //用 WXVideoObject 对象初始化一个 WXMediaMessage 对象
                WXMediaMessage msg = new WXMediaMessage(video);
                msg.title =userInfo.getNickName()+"的动态";

                Bitmap netVideoBitmap = getNetVideoBitmap(bean.getContentImg());
                //设置封面
                msg.thumbData =getBitmapBytes(netVideoBitmap, false);

                //构造一个Req
                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.transaction = String.valueOf(System.currentTimeMillis());
                req.message =msg;
                req.scene = SendMessageToWX.Req.WXSceneTimeline;

                //调用api接口，发送数据到微信
                App.getWXApi().sendReq(req);
            }
            if(imgType==31){
                //初始化一个WXVideoObject，填写url
                WXVideoObject video = new WXVideoObject();
                video.videoUrl =bean.getContentImg();

                //用 WXVideoObject 对象初始化一个 WXMediaMessage 对象
                WXMediaMessage msg = new WXMediaMessage(video);
                msg.title =userInfo.getNickName()+"的动态";
                msg.description= bean.getContentInfo();
                Bitmap netVideoBitmap = getNetVideoBitmap(bean.getContentImg());
                //设置封面
                msg.thumbData =getBitmapBytes(netVideoBitmap, false);

                //构造一个Req
                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.transaction = String.valueOf(System.currentTimeMillis());
                req.message =msg;
                req.scene = SendMessageToWX.Req.WXSceneTimeline;

                //调用api接口，发送数据到微信
                App.getWXApi().sendReq(req);

            }
        }
    }

    //设置透明度
    public void setWindowAlpa(boolean isopen) {
        if (Build.VERSION.SDK_INT < 11) {
            return;
        }
        final Window window = this.getWindow();
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

    /**
     * 显示PopupWindow
     */

    private void show(View v) {
        if (mPopupWindow != null && !mPopupWindow.isShowing()) {
            mPopupWindow.showAtLocation(v, Gravity.CENTER_HORIZONTAL, 0, 0);
        }
        setWindowAlpa(true);
    }

    /**
     * 消失PopupWindow
     */
    public void dismiss() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
    }
    // 需要对图片进行处理，否则微信会在log中输出thumbData检查错误
    private static byte[] getBitmapBytes(Bitmap bitmap, boolean paramBoolean) {
        Bitmap localBitmap = Bitmap.createBitmap(80, 80, Bitmap.Config.RGB_565);
        Canvas localCanvas = new Canvas(localBitmap);
        int i;
        int j;
        if (bitmap.getHeight() > bitmap.getWidth()) {
            i = bitmap.getWidth();
            j = bitmap.getWidth();
        } else {
            i = bitmap.getHeight();
            j = bitmap.getHeight();
        }
        while (true) {
            localCanvas.drawBitmap(bitmap, new Rect(0, 0, i, j), new Rect(0, 0,80
                    , 80), null);
            if (paramBoolean)
                bitmap.recycle();
            ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
            localBitmap.compress(Bitmap.CompressFormat.JPEG, 100,
                    localByteArrayOutputStream);
            localBitmap.recycle();
            byte[] arrayOfByte = localByteArrayOutputStream.toByteArray();
            try {
                localByteArrayOutputStream.close();
                return arrayOfByte;
            } catch (Exception e) {
                Log.d("xxx",e.getMessage());
            }
            i = bitmap.getHeight();
            j = bitmap.getHeight();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GSYVideoManager.releaseAllVideos();
    }
}
