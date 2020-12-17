package com.YiDian.RainBow.main.fragment.home.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseAvtivity;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.base.Common;
import com.YiDian.RainBow.custom.customDialog.CustomDialogCancleFollow;
import com.YiDian.RainBow.custom.image.NineGridTestLayout;
import com.YiDian.RainBow.custom.videoplayer.SampleCoverVideo;
import com.YiDian.RainBow.main.fragment.home.adapter.NewDynamicAdapter;
import com.YiDian.RainBow.main.fragment.home.adapter.TopicsAdapter;
import com.YiDian.RainBow.main.fragment.home.bean.DianzanBean;
import com.YiDian.RainBow.main.fragment.home.bean.DynamicDetailsBean;
import com.YiDian.RainBow.main.fragment.home.bean.FollowBean;
import com.YiDian.RainBow.topic.SaveIntentMsgBean;
import com.YiDian.RainBow.topic.TopicDetailsActivity;
import com.YiDian.RainBow.user.PersonHomeActivity;
import com.YiDian.RainBow.utils.KeyBoardUtils;
import com.YiDian.RainBow.utils.NetUtils;
import com.YiDian.RainBow.utils.StringUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.leaf.library.StatusBarUtil;
import com.liaoinstan.springview.widget.SpringView;

import org.greenrobot.eventbus.EventBus;

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
    @BindView(R.id.iv_close)
    ImageView ivClose;
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
    private int id;
    private int userId;
    private DynamicDetailsBean.ObjectBean bean;
    private DynamicDetailsBean.ObjectBean.UserInfoBean userInfo;
    public static final String TAG = "ListNormalAdapter22";

    @Override
    protected int getResId() {
        return R.layout.activity_dynamicdetails;
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
        ivClose.setOnClickListener(this);
        rlDianzan.setOnClickListener(this);
        rlZhuanfa.setOnClickListener(this);
        ivCollection.setOnClickListener(this);
        btConfirm.setOnClickListener(this);
        rlPinglun.setOnClickListener(this);
        //获取指定id下动态的详情
        showDialog();

        //获取动态
        getDetails();
        //获取评论

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
            //隐藏提示信息
            case R.id.iv_close:
                //隐藏展示信息
                rlTips.setVisibility(View.GONE);
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
                Toast.makeText(this, "123456", Toast.LENGTH_SHORT).show();
                break;
            //点击收藏
            case R.id.iv_collection:
                //判断是否已经收藏过
                if (bean.isIsCollect()) {
                    //已经收藏过 取消收藏
                    ivCollection.setFocusable(false);
                } else {
                    //未收藏过 收藏动态
                }
                break;
            //发送评论
            case R.id.bt_confirm:

                break;
            //去评论
            case R.id.rl_pinglun:

                KeyBoardUtils.openKeyBoard(etContent);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
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

    public void getDetails() {
        //获取动态详情
        NetUtils.getInstance().getApis()
                .dogetDynamicDetails(id, userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DynamicDetailsBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @SuppressLint("ResourceAsColor")
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
                        tvAge.setText(userInfo.getUserRole());
                        //判断性别是否保密
                        String userRole = userInfo.getUserRole();
                        if (userRole.equals("保密")) {
                            tvAge.setVisibility(View.GONE);
                        }
                        //判断是否认证
                        if (userInfo.getAttestation() == 1) {
                            isattaction.setVisibility(View.VISIBLE);
                        } else {
                            isattaction.setVisibility(View.GONE);
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
                            String round = StringUtil.round(distance);
                            tvDistance.setText(round + "km");
                        } else {
                            tvDistance.setVisibility(View.GONE);
                        }
                        //获取发布时间
                        String createTime = bean.getCreateTime();
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
                                imglist.add(split[i].trim());
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
                                imglist.add(split[i].trim());
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
}
