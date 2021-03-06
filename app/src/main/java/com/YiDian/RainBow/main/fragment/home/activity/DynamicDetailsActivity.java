package com.YiDian.RainBow.main.fragment.home.activity;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
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
import com.YiDian.RainBow.custom.loading.CustomDialog;
import com.YiDian.RainBow.custom.videoplayer.SampleCoverVideo;
import com.YiDian.RainBow.main.fragment.home.adapter.CommentAdapter;
import com.YiDian.RainBow.main.fragment.home.bean.CollectDynamicBean;
import com.YiDian.RainBow.main.fragment.home.bean.CommentBean;
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
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.leaf.library.StatusBarUtil;
import com.liaoinstan.springview.container.AliFooter;
import com.liaoinstan.springview.container.AliHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.tencent.connect.share.QQShare;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXVideoObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

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
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.YiDian.RainBow.base.Common.decodeUriAsBitmapFromNet;

//???????????????
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
    private CustomDialog dialog;
    private int releseid;

    @Override
    protected int getResId() {
        return R.layout.activity_dynamicdetails;
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
    @Override
    protected void getData() {

        Intent intent =
                getIntent();
        id = intent.getIntExtra("id", 0);
        userId = Integer.valueOf(Common.getUserId());

        //??????????????????????????????????????????
        StatusBarUtil.setGradientColor(this, toolbar);
        StatusBarUtil.setDarkMode(this);

        //??????????????????
        ivBack.setOnClickListener(this);
        ivMore.setOnClickListener(this);
        ivHeadimg.setOnClickListener(this);
        tvGuanzhu.setOnClickListener(this);
        rlDianzan.setOnClickListener(this);
        rlZhuanfa.setOnClickListener(this);
        ivCollection.setOnClickListener(this);
        btConfirm.setOnClickListener(this);
        rlPinglun.setOnClickListener(this);

        dialog = new CustomDialog(this, "????????????...");

        mTencent = Tencent.createInstance("101906973", this);

        sv.setHeader(new AliHeader(DynamicDetailsActivity.this));

        //?????????????????????????????????????????????
        addLayoutListener(rlItem,rlSend);

        //????????????
        getDetails();
        //????????????
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

    //??????????????????
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //??????????????????
            case R.id.iv_back:
                finish();
                break;
            //????????? ?????????
            case R.id.iv_more:

                break;
            //??????????????????????????????
            case R.id.iv_headimg:
                Intent intent = new Intent(this, PersonHomeActivity.class);
                SaveIntentMsgBean saveIntentMsgBean = new SaveIntentMsgBean();
                saveIntentMsgBean.setId(releseid);
                //2??????????????????  1????????????id
                saveIntentMsgBean.setFlag(1);
                intent.putExtra("msg",saveIntentMsgBean);
                startActivity(intent);
                break;
            //????????????
            case R.id.tv_guanzhu:
                int userInfoId = userInfo.getId();

                if (bean.isIsAttention()) {
                    CustomDialogCancleFollow.Builder builder = new CustomDialogCancleFollow.Builder(DynamicDetailsActivity.this);
                    builder.setPositiveButton("??????", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //?????????????????????????????? ??????????????????????????????
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
                                            //???????????????????????????
                                            tvGuanzhu.setEnabled(true);
                                            if (followBean.getMsg().equals("??????????????????")) {

                                                //??????????????????
                                                getDetails();
                                                bean.setIsAttention(false);
                                                // TODO: 2020/12/15 0015 ????????????
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
                    builder.setNegativeButton("??????",
                            new android.content.DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    builder.create().show();
                } else {
                    //?????????????????????????????? ??????????????????????????????
                    tvGuanzhu.setEnabled(false);

                    //??????
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
                                    //???????????????????????????
                                    tvGuanzhu.setEnabled(true);
                                    if (followBean.getMsg().equals("????????????")) {

                                        //??????????????????
                                        getDetails();

                                        bean.setIsAttention(true);
                                        // TODO: 2020/12/15 0015 ????????????


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
            //??????
            case R.id.rl_dianzan:
                if (bean.isIsClick()) {
                    //????????????
                    //?????????????????????????????? ??????????????????????????????
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
                                    //???????????????????????????
                                    rlDianzan.setEnabled(true);

                                    //??????????????????
                                    ivDianzan.setImageResource(R.mipmap.weidianzan);
                                    bean.setIsClick(false);

                                    //??????????????????????????????
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
                    //??????
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

                                    //???????????????????????????
                                    rlDianzan.setEnabled(true);

                                    if (dianzanBean.getObject().equals("????????????")) {
                                        //????????????
                                        ivDianzan.setImageResource(R.mipmap.dianzan);
                                        bean.setIsClick(true);

                                        //????????????????????????
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
            //??????????????????
            case R.id.rl_zhuanfa:
                showSelect();
                break;
            //????????????
            case R.id.iv_collection:
                //???????????????????????????
                if (bean.isIsCollect()) {
                    //??????????????? ????????????
                    //????????????????????? ????????????????????????
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

                                    Toast.makeText(DynamicDetailsActivity.this, "??????????????????", Toast.LENGTH_SHORT).show();
                                    //?????????????????????
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
                    //???????????? ????????????
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
                                    Toast.makeText(DynamicDetailsActivity.this, "????????????", Toast.LENGTH_SHORT).show();
                                    //?????????????????????
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
            //????????????
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
                                if(collectDynamicBean.getObject().equals("???????????????")){
                                    //??????????????????
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
            //?????????
            case R.id.rl_pinglun:
                KeyBoardUtils.openKeyBoard(etContent);
                break;
        }
    }

    @SuppressLint("ResourceAsColor")
    public static Spannable getWeiBoContent(final Context context, String source, TextView tv) {

        SpannableStringBuilder spannable = new SpannableStringBuilder(source);
        // ?????????????????????
        String AT = "@[\\u4e00-\\u9fa5\\w\\-]+";// @???
        String TOPIC = "#([^\\#|.]+)#";// ##??????
        //????????????
        Pattern pattern = Pattern.compile("(" + AT + ")|" + "(" + TOPIC + ")");
        Matcher matcher = pattern.matcher(spannable);

        Log.d("xxx", matcher.groupCount() + "");

        while (matcher.find()) {
            // ??????group???????????????????????????????????????????????????(0???????????????1?????????????????????)
            final String at = matcher.group(1);
            final String topic = matcher.group(2);
            // ??????@??????
            if (at != null) {
                //??????????????????
                int start = matcher.start(1);
                int end = start + at.length();

                tv.setMovementMethod(LinkMovementMethod.getInstance());
                spannable.setSpan(new NewDynamicAdapter.MyClickableSpanAt() {
                    @Override
                    public void onClick(View widget) {
                        //???????????????????????????????????????????????????Toast??????
                        String substring = at.substring(1);

                        Intent intent = new Intent(context, PersonHomeActivity.class);
                        SaveIntentMsgBean saveIntentMsgBean = new SaveIntentMsgBean();
                        saveIntentMsgBean.setMsg(substring);
                        //2??????????????????  1????????????id
                        saveIntentMsgBean.setFlag(2);
                        intent.putExtra("msg", saveIntentMsgBean);
                        context.startActivity(intent);
                    }
                }, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            }

            // ????????????##??????
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
                        //2?????????????????????  1????????????id
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
                            //???????????????
                            CommentAdapter commentAdapter = new CommentAdapter(DynamicDetailsActivity.this, AllList);
                            rcComment.setAdapter(commentAdapter);

                        }else{
                            if(AllList.size()>0){
                                Toast.makeText(DynamicDetailsActivity.this, "?????????????????????", Toast.LENGTH_SHORT).show();
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
        dialog.show();
        //????????????id??????????????????
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
                        dialog.dismiss();
                        userInfo = dynamicDetailsBean.getObject().getUserInfo();

                        releseid = userInfo.getId();
                        bean = dynamicDetailsBean.getObject();
                        List<DynamicDetailsBean.ObjectBean.TopicsBean> topics =
                                bean.getTopics();
                        //????????????
                        Glide.with(DynamicDetailsActivity.this).load(userInfo.getHeadImg()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(ivHeadimg);
                        //????????????
                        tvUsername.setText(userInfo.getNickName());
                        //????????????
                        //????????????????????????
                        String userRole = userInfo.getUserRole();
                        if (userRole!=null){
                            if (userRole.equals("??????")) {
                                tvAge.setVisibility(View.GONE);
                            }else  if (userRole.equals("")){
                                tvAge.setVisibility(View.GONE);
                            }
                            else{
                                tvAge.setText(userInfo.getUserRole());
                            }
                        }else{
                            tvAge.setVisibility(View.GONE);
                        }

                        //???????????????????????????????????? ????????? ??????????????????
                        if(userId==userInfo.getId()){
                            tvGuanzhu.setVisibility(View.GONE);
                        }
                        
                        int attestation = userInfo.getAttestation();
                        //????????????
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

                        //??????????????????
                        if (bean.isIsClick()) {
                            ivDianzan.setImageResource(R.mipmap.dianzan);
                        } else {
                            ivDianzan.setImageResource(R.mipmap.weidianzan);
                        }
                        //??????????????????
                        if (bean.isIsCollect()) {
                            ivCollection.setImageResource(R.mipmap.yishoucang);
                        } else {
                            ivCollection.setImageResource(R.mipmap.weishoucang);
                        }
                        //???????????????
                        tvDianzanCount.setText(bean.getClickNum() + "");
                        //???????????????
                        tvPinglunCount.setText(bean.getCommentCount() + "");
                        //??????????????????
                        if (bean.isIsAttention()) {
                            tvGuanzhu.setBackground(DynamicDetailsActivity.this.getResources().getDrawable(R.drawable.newdynamic_yiguanzhu));
                            tvGuanzhu.setText("?????????");
                            tvGuanzhu.setTextColor(R.color.color_999999);
                        } else {
                            tvGuanzhu.setBackground(DynamicDetailsActivity.this.getResources().getDrawable(R.drawable.newdynamic_weiguanzhu));
                            tvGuanzhu.setText("??????");
                            tvGuanzhu.setTextColor(Color.BLACK);
                        }

                        //??????????????????????????????????????????
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
                        //??????????????????
                        String createTime = bean.getCreateTime();
                        if(createTime!=null){
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd HH:mm:ss", Locale.SIMPLIFIED_CHINESE);
                            try {
                                Date parse = sdf.parse(createTime);

                                long time = parse.getTime();

                                //??????????????????
                                long l = System.currentTimeMillis();
                                //????????????????????????
                                long difference = l - time;

                                //????????????12?????? ????????????
                                if (difference > 1800000 ) {
                                    String newChatTime = StringUtil.getNewChatTime(time);
                                    tvTime.setText(newChatTime);
                                }
                                if (difference > 1200000 && difference < 1800000) {
                                    tvTime.setText("??????????????????");
                                }
                                if (difference > 600000 && difference < 1200000) {
                                    tvTime.setText("20???????????????");
                                }
                                if (difference > 300000 && difference < 600000) {
                                    tvTime.setText("10???????????????");
                                }
                                if (difference > 240000 && difference < 300000) {
                                    tvTime.setText("5???????????????");
                                }
                                if (difference > 180000 && difference < 240000) {
                                    tvTime.setText("4???????????????");
                                }
                                if (difference > 120000 && difference < 180000) {
                                    tvTime.setText("3???????????????");
                                }
                                if (difference > 60000 && difference < 120000) {
                                    tvTime.setText("2???????????????");
                                }
                                if (difference < 60000) {
                                    tvTime.setText("1???????????????");
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                        //??????????????????
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

                            rcImage1.setIsShowAll(false); //???????????????????????????9???????????????????????????
                            rcImage1.setSpacing(5); //?????????????????????????????????
                            rcImage1.setUrlList(imglist); //?????????????????????url

                        }
                        if (imgType == 3) {
                            rlText.setVisibility(View.GONE);
                            rlImg.setVisibility(View.GONE);
                            rlImgandtext.setVisibility(View.GONE);
                            rlVideo.setVisibility(View.VISIBLE);
                            rlVideoandtext.setVisibility(View.GONE);

                            //??????????????????
                            String contentImg = bean.getContentImg();

                            Bitmap netVideoBitmap = decodeUriAsBitmapFromNet(contentImg+"?vframe/jpg/offset/1/w/480/h/360");
                            //????????????
                            videoPlayer1.loadCoverImage(contentImg, netVideoBitmap);

                            videoPlayer1.setUpLazy(contentImg, true, null, null, "");

                            //??????????????????
                            videoPlayer1.setPlayTag(TAG);
                            videoPlayer1.setLockLand(true);
                            //?????????????????????????????????????????????????????????????????????????????????????????? setLockLand ?????????????????? orientationUtils ??????
                            videoPlayer1.setAutoFullWithSize(false);
                            //?????????????????????????????????
                            videoPlayer1.setReleaseWhenLossAudio(false);
                            //????????????
                            videoPlayer1.setShowFullAnimation(true);
                            //????????????????????????
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

                            rcImage.setIsShowAll(false); //???????????????????????????9???????????????????????????
                            rcImage.setSpacing(5); //?????????????????????????????????
                            rcImage.setUrlList(imglist); //?????????????????????url

                            getWeiBoContent(DynamicDetailsActivity.this, bean.getContentInfo(), tvDynamicText2);
                        }
                        if (imgType == 31) {
                            rlText.setVisibility(View.GONE);
                            rlImg.setVisibility(View.GONE);
                            rlImgandtext.setVisibility(View.GONE);
                            rlVideo.setVisibility(View.GONE);
                            rlVideoandtext.setVisibility(View.VISIBLE);

                            //??????????????????
                            String contentImg = bean.getContentImg();

                            Bitmap netVideoBitmap = decodeUriAsBitmapFromNet(contentImg+"?vframe/jpg/offset/1/w/480/h/360");

                            //????????????
                            videoPlayer2.loadCoverImage(contentImg, netVideoBitmap);

                            videoPlayer2.setUpLazy(contentImg, true, null, null, "");

                            //??????????????????
                            videoPlayer2.setPlayTag(TAG);
                            videoPlayer2.setLockLand(true);
                            //?????????????????????????????????????????????????????????????????????????????????????????? setLockLand ?????????????????? orientationUtils ??????
                            videoPlayer2.setAutoFullWithSize(false);
                            //?????????????????????????????????
                            videoPlayer2.setReleaseWhenLossAudio(false);
                            //????????????
                            videoPlayer2.setShowFullAnimation(true);
                            //????????????????????????
                            videoPlayer2.setIsTouchWiget(false);

                            getWeiBoContent(DynamicDetailsActivity.this, bean.getContentInfo(), tvDynamicText);
                        }

                        //????????????
                        if (topics.size() > 0 && topics != null) {
                            //??????????????????
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
                        dialog.dismiss();

                        Toast.makeText(DynamicDetailsActivity.this, "??????????????????", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    public void showSelect() {
        //?????????????????????
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
                //?????????QQ??????
                onClickShare();
            }
        });
        space.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //?????????QQ??????
                onClickShareQzone();
            }
        });
        wechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //?????????????????????
                onclickShareWechatFriend();
            }
        });
        moments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //??????????????????
                onclickShareWechatmoments();
            }
        });
        //popwindow????????????
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
    //?????????QQ??????
    private void onClickShare() {
        int imgType = bean.getImgType();
        Bundle params = new Bundle();

        if(imgType==1){
            params.putString(QQShare.SHARE_TO_QQ_SUMMARY, bean.getContentInfo());
            params.putString(QQShare.SHARE_TO_QQ_TITLE, userInfo.getNickName()+"?????????");
            params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "http://web.p.qq.com/qqmpmobile/aio/app.html?id=101906973");
            params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
            params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE);
        }
        if(imgType==21){
            String[] split = bean.getContentImg().split(",");

            params.putString(QQShare.SHARE_TO_QQ_SUMMARY, bean.getContentInfo());
            params.putString(QQShare.SHARE_TO_QQ_TITLE, userInfo.getNickName()+"?????????");
            params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "http://web.p.qq.com/qqmpmobile/aio/app.html?id=101906973");
            params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, split[0]);
            params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
            params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE);
        }
        if(imgType==2){
            String[] split = bean.getContentImg().split(",");
            params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, split[0]);
            params.putString(QQShare.SHARE_TO_QQ_TITLE, userInfo.getNickName()+"?????????");
            params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "http://web.p.qq.com/qqmpmobile/aio/app.html?id=101906973");
            params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare. SHARE_TO_QQ_TYPE_IMAGE);
            params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE);
        }
        if(imgType==3){
            params.putString(QQShare.SHARE_TO_QQ_AUDIO_URL, bean.getContentImg());
            params.putString(QQShare.SHARE_TO_QQ_TITLE, userInfo.getNickName()+"?????????");
            params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "http://web.p.qq.com/qqmpmobile/aio/app.html?id=101906973");
            params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare. SHARE_TO_QQ_TYPE_AUDIO);
            params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE);
        }
        if(imgType==31){
            params.putString(QQShare.SHARE_TO_QQ_SUMMARY, bean.getContentInfo());
            params.putString(QQShare.SHARE_TO_QQ_TITLE, userInfo.getNickName()+"?????????");
            params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "http://web.p.qq.com/qqmpmobile/aio/app.html?id=101906973");
            params.putString(QQShare.SHARE_TO_QQ_AUDIO_URL, bean.getContentImg());
            params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
            params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE);
        }
        mTencent.shareToQQ(this, params, new IUiListener() {
            @Override
            public void onComplete(Object o) {
                Log.e(TAG, "????????????: " + o.toString());
            }

            @Override
            public void onError(UiError uiError) {
                Log.e(TAG, "????????????: " + uiError.toString());
            }

            @Override
            public void onCancel() {
                Log.e(TAG, "????????????");

            }
        });

    }

    //?????????QQ??????
    private void onClickShareQzone() {
        int imgType = bean.getImgType();
        Bundle params = new Bundle();

        if(imgType==1){
            params.putString(QQShare.SHARE_TO_QQ_SUMMARY, bean.getContentInfo());
            params.putString(QQShare.SHARE_TO_QQ_TITLE, userInfo.getNickName()+"?????????");
            params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "http://web.p.qq.com/qqmpmobile/aio/app.html?id=101906973");
            params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
            params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
        }
        if(imgType==21){
            String[] split = bean.getContentImg().split(",");
            params.putString(QQShare.SHARE_TO_QQ_SUMMARY, bean.getContentInfo());
            params.putString(QQShare.SHARE_TO_QQ_TITLE, userInfo.getNickName()+"?????????");
            params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "http://web.p.qq.com/qqmpmobile/aio/app.html?id=101906973");
            params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, split[0]);
            params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
            params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
        }
        if(imgType==2){
            String[] split = bean.getContentImg().split(",");
            params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, split[0]);
            params.putString(QQShare.SHARE_TO_QQ_TITLE, userInfo.getNickName()+"?????????");
            params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "http://web.p.qq.com/qqmpmobile/aio/app.html?id=101906973");
            params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare. SHARE_TO_QQ_TYPE_IMAGE);
            params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);

        }
        if(imgType==3){
            params.putString(QQShare.SHARE_TO_QQ_AUDIO_URL, bean.getContentImg());
            params.putString(QQShare.SHARE_TO_QQ_TITLE, userInfo.getNickName()+"?????????");
            params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "http://web.p.qq.com/qqmpmobile/aio/app.html?id=101906973");
            params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare. SHARE_TO_QQ_TYPE_AUDIO);
            params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);

        }
        if(imgType==31){
            params.putString(QQShare.SHARE_TO_QQ_SUMMARY, bean.getContentInfo());
            params.putString(QQShare.SHARE_TO_QQ_TITLE, userInfo.getNickName()+"?????????");
            params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "http://web.p.qq.com/qqmpmobile/aio/app.html?id=101906973");
            params.putString(QQShare.SHARE_TO_QQ_AUDIO_URL, bean.getContentImg());
            params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
            params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);

        }
        mTencent.shareToQQ(this, params, new IUiListener() {
            @Override
            public void onComplete(Object o) {
                Log.e(TAG, "????????????: " + o.toString());
            }
            @Override
            public void onError(UiError uiError) {

            }

            @Override
            public void onCancel() {

            }
        });
    }

    //?????????????????????
    private void onclickShareWechatFriend() {
        int imgType = bean.getImgType();
        if (!App.getWXApi().isWXAppInstalled()){
            Toast.makeText(this, "??????????????????", Toast.LENGTH_SHORT).show();
            return;
        }else{
            if(imgType==1){
                // TODO: 2020/12/18 0018 ??????????????????

                //????????? WXImageObject ??? WXMediaMessage ??????
                WXWebpageObject localWXWebpageObject = new WXWebpageObject();
                localWXWebpageObject.webpageUrl = wechatUrl;

                WXMediaMessage msg = new WXMediaMessage(localWXWebpageObject);
                msg.description = bean.getContentInfo();

                msg.title = userInfo.getNickName()+"?????????";
                //????????????Req
                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.transaction = String.valueOf(System.currentTimeMillis());
                req.message = msg;
                req.scene = SendMessageToWX.Req.WXSceneSession;
                //??????api??????????????????????????????
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
                                localWXMediaMessage.title = userInfo.getNickName()+"?????????";//?????????????????????????????????????????????????????????????????????????????????????????????
                                localWXMediaMessage.description = "";
                                localWXMediaMessage.thumbData = getBitmapBytes(resource, false);

                                //????????????Req
                                SendMessageToWX.Req req = new SendMessageToWX.Req();
                                req.transaction = String.valueOf(System.currentTimeMillis());
                                req.message = localWXMediaMessage;
                                req.scene = SendMessageToWX.Req.WXSceneSession;
                                //??????api??????????????????????????????
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
                                localWXMediaMessage.title = userInfo.getNickName()+"?????????";//?????????????????????????????????????????????????????????????????????????????????????????????
                                localWXMediaMessage.description = bean.getContentInfo();
                                localWXMediaMessage.thumbData = getBitmapBytes(resource, false);

                                //????????????Req
                                SendMessageToWX.Req req = new SendMessageToWX.Req();
                                req.transaction = String.valueOf(System.currentTimeMillis());
                                req.message = localWXMediaMessage;
                                req.scene = SendMessageToWX.Req.WXSceneSession;
                                //??????api??????????????????????????????
                                App.getWXApi().sendReq(req);
                            }
                        });
            }
            if(imgType==3){
                //???????????????WXVideoObject?????????url
                WXVideoObject video = new WXVideoObject();
                video.videoUrl =bean.getContentImg();

                //??? WXVideoObject ????????????????????? WXMediaMessage ??????
                WXMediaMessage msg = new WXMediaMessage(video);
                msg.title =userInfo.getNickName()+"?????????";

                Bitmap netVideoBitmap = decodeUriAsBitmapFromNet(bean.getContentImg()+"?vframe/jpg/offset/1/w/480/h/360");

                //????????????
                msg.thumbData =getBitmapBytes(netVideoBitmap, false);

                //????????????Req
                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.transaction = String.valueOf(System.currentTimeMillis());
                req.message =msg;
                req.scene = SendMessageToWX.Req.WXSceneSession;

                //??????api??????????????????????????????
                App.getWXApi().sendReq(req);
            }
            if(imgType==31){
                //???????????????WXVideoObject?????????url
                WXVideoObject video = new WXVideoObject();
                video.videoUrl =bean.getContentImg();

                //??? WXVideoObject ????????????????????? WXMediaMessage ??????
                WXMediaMessage msg = new WXMediaMessage(video);
                msg.title =userInfo.getNickName()+"?????????";
                msg.description= bean.getContentInfo();
                Bitmap netVideoBitmap = decodeUriAsBitmapFromNet(bean.getContentImg()+"?vframe/jpg/offset/1/w/480/h/360");

                //????????????
                msg.thumbData =getBitmapBytes(netVideoBitmap, false);

                //????????????Req
                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.transaction = String.valueOf(System.currentTimeMillis());
                req.message =msg;
                req.scene = SendMessageToWX.Req.WXSceneSession;

                //??????api??????????????????????????????
                App.getWXApi().sendReq(req);

            }
        }



    }

    //????????????????????????
    private void onclickShareWechatmoments() {
        int imgType = bean.getImgType();
        if (!App.getWXApi().isWXAppInstalled()){
            Toast.makeText(this, "??????????????????", Toast.LENGTH_SHORT).show();
            return;
        }else{
            if(imgType==1){
                // TODO: 2020/12/18 0018 ??????????????????

                //????????? WXImageObject ??? WXMediaMessage ??????
                WXWebpageObject localWXWebpageObject = new WXWebpageObject();
                localWXWebpageObject.webpageUrl = wechatUrl;

                WXMediaMessage msg = new WXMediaMessage(localWXWebpageObject);
                msg.description = bean.getContentInfo();

                msg.title = userInfo.getNickName()+"?????????";
                //????????????Req
                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.transaction = String.valueOf(System.currentTimeMillis());
                req.message = msg;
                req.scene = SendMessageToWX.Req.WXSceneTimeline;
                //??????api??????????????????????????????
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
                                localWXMediaMessage.title = userInfo.getNickName()+"?????????";//?????????????????????????????????????????????????????????????????????????????????????????????
                                localWXMediaMessage.description = "";
                                localWXMediaMessage.thumbData = getBitmapBytes(resource, false);

                                //????????????Req
                                SendMessageToWX.Req req = new SendMessageToWX.Req();
                                req.transaction = String.valueOf(System.currentTimeMillis());
                                req.message = localWXMediaMessage;
                                req.scene = SendMessageToWX.Req.WXSceneTimeline;
                                //??????api??????????????????????????????
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
                                localWXMediaMessage.title = userInfo.getNickName()+"?????????";//?????????????????????????????????????????????????????????????????????????????????????????????
                                localWXMediaMessage.description = bean.getContentInfo();
                                localWXMediaMessage.thumbData = getBitmapBytes(resource, false);

                                //????????????Req
                                SendMessageToWX.Req req = new SendMessageToWX.Req();
                                req.transaction = String.valueOf(System.currentTimeMillis());
                                req.message = localWXMediaMessage;
                                req.scene = SendMessageToWX.Req.WXSceneTimeline;
                                //??????api??????????????????????????????
                                App.getWXApi().sendReq(req);
                            }
                        });
            }
            if(imgType==3){
                //???????????????WXVideoObject?????????url
                WXVideoObject video = new WXVideoObject();
                video.videoUrl =bean.getContentImg();

                //??? WXVideoObject ????????????????????? WXMediaMessage ??????
                WXMediaMessage msg = new WXMediaMessage(video);
                msg.title =userInfo.getNickName()+"?????????";

                Bitmap netVideoBitmap = decodeUriAsBitmapFromNet(bean.getContentImg()+"?vframe/jpg/offset/1/w/480/h/360");

                //????????????
                msg.thumbData =getBitmapBytes(netVideoBitmap, false);

                //????????????Req
                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.transaction = String.valueOf(System.currentTimeMillis());
                req.message =msg;
                req.scene = SendMessageToWX.Req.WXSceneTimeline;

                //??????api??????????????????????????????
                App.getWXApi().sendReq(req);
            }
            if(imgType==31){
                //???????????????WXVideoObject?????????url
                WXVideoObject video = new WXVideoObject();
                video.videoUrl =bean.getContentImg();

                //??? WXVideoObject ????????????????????? WXMediaMessage ??????
                WXMediaMessage msg = new WXMediaMessage(video);
                msg.title =userInfo.getNickName()+"?????????";
                msg.description= bean.getContentInfo();
                Bitmap netVideoBitmap = decodeUriAsBitmapFromNet(bean.getContentImg()+"?vframe/jpg/offset/1/w/480/h/360");

                //????????????
                msg.thumbData =getBitmapBytes(netVideoBitmap, false);

                //????????????Req
                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.transaction = String.valueOf(System.currentTimeMillis());
                req.message =msg;
                req.scene = SendMessageToWX.Req.WXSceneTimeline;

                //??????api??????????????????????????????
                App.getWXApi().sendReq(req);

            }
        }
    }

    //???????????????
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
     * ??????PopupWindow
     */

    private void show(View v) {
        if (mPopupWindow != null && !mPopupWindow.isShowing()) {
            mPopupWindow.showAtLocation(v, Gravity.CENTER_HORIZONTAL, 0, 0);
        }
        setWindowAlpa(true);
    }

    /**
     * ??????PopupWindow
     */
    public void dismiss() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
    }
    // ????????????????????????????????????????????????log?????????thumbData????????????
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
