package com.YiDian.RainBow.main.fragment.home.activity;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseAvtivity;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.base.Common;
import com.YiDian.RainBow.main.fragment.home.adapter.CommentDetailsAdapter;
import com.YiDian.RainBow.main.fragment.home.bean.CollectDynamicBean;
import com.YiDian.RainBow.main.fragment.home.bean.CommentBean;
import com.YiDian.RainBow.main.fragment.home.bean.DianzanBean;
import com.YiDian.RainBow.main.fragment.home.bean.OneCommentBean;
import com.YiDian.RainBow.topic.SaveIntentMsgBean;
import com.YiDian.RainBow.user.PersonHomeActivity;
import com.YiDian.RainBow.utils.KeyBoardUtils;
import com.YiDian.RainBow.utils.NetUtils;
import com.YiDian.RainBow.utils.StringUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.leaf.library.StatusBarUtil;
import com.liaoinstan.springview.container.AliFooter;
import com.liaoinstan.springview.container.AliHeader;
import com.liaoinstan.springview.widget.SpringView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

//????????????
public class CommentDetailsActivity extends BaseAvtivity implements View.OnClickListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_back)
    LinearLayout ivBack;
    @BindView(R.id.iv_more)
    LinearLayout ivMore;
    @BindView(R.id.rc_comment)
    RecyclerView rcComment;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.bt_confirm)
    Button btConfirm;
    @BindView(R.id.iv_headimg)
    ImageView ivHeadimg;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.iv_dianzan)
    ImageView ivDianzan;
    @BindView(R.id.tv_dianzan_count)
    TextView tvDianzanCount;
    @BindView(R.id.rl_dianzan)
    LinearLayout rlDianzan;
    @BindView(R.id.sv)
    SpringView sv;
    @BindView(R.id.rl_send)
    RelativeLayout rlSend;
    @BindView(R.id.et_reply)
    EditText etReply;
    @BindView(R.id.bt_reply)
    Button btReply;
    @BindView(R.id.rl_send_reply)
    RelativeLayout rlSendReply;
    @BindView(R.id.rl_middle)
    RelativeLayout rlMiddle;
    @BindView(R.id.rl_notdata)
    RelativeLayout rlNotdata;
    @BindView(R.id.rl_item)
    RelativeLayout rlItem;
    private int commentId;
    private int userId;
    int page = 1;
    int size = 15;
    private int commentUserid;
    List<CommentBean.ObjectBean> AllList = new ArrayList<>();
    private OneCommentBean.ObjectBean bean;
    File f = new File(
            "/data/data/com.YiDian.RainBow/shared_prefs/comment.xml");

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
    protected int getResId() {
        return R.layout.activity_comment_details;
    }

    @Override
    protected void getData() {
        StatusBarUtil.setGradientColor(CommentDetailsActivity.this, toolbar);
        StatusBarUtil.setDarkMode(CommentDetailsActivity.this);
        ivBack.setOnClickListener(this);
        ivMore.setOnClickListener(this);
        btConfirm.setOnClickListener(this);
        rlDianzan.setOnClickListener(this);

        //????????????
        Intent intent = getIntent();
        commentId = intent.getIntExtra("id", 0);
        userId = Integer.valueOf(Common.getUserId());

        addLayoutListener(rlItem,rlSend);

        sv.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        AllList.clear();
                        int page = 1;
                        InitData();
                        getSecondComment(page, size);
                        sv.onFinishFreshAndLoad();
                    }
                }, 1000);
            }

            @Override
            public void onLoadmore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page++;
                        getSecondComment(page, size);
                        sv.onFinishFreshAndLoad();
                    }
                }, 1000);
            }
        });
        InitData();
        //????????????????????????
        getSecondComment(page, size);

        //???????????????
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
        etReply.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s) && s.length() > 0) {
                    btReply.setBackground(getResources().getDrawable(R.drawable.content_bt_bg));
                    btReply.setEnabled(true);
                } else {
                    btReply.setBackground(getResources().getDrawable(R.drawable.content_bt_nodata_bg));
                    btReply.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        rlMiddle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rlSendReply.setVisibility(View.GONE);

                KeyBoardUtils.openKeyBoard(etContent);
            }
        });
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //??????
            case R.id.rl_dianzan:
                if (bean.isClick()) {
                    rlDianzan.setEnabled(false);
                    //????????????
                    NetUtils.getInstance().getApis().doCancleDianzan(2, bean.getId(), userId)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<DianzanBean>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(DianzanBean dianzanBean) {
                                    rlDianzan.setEnabled(true);

                                    bean.setClick(false);
                                    ivDianzan.setImageResource(R.mipmap.weidianzan);
                                    InitData();

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
                            .doDianzan(userId, 2, bean.getId())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<DianzanBean>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(DianzanBean dianzanBean) {
                                    rlDianzan.setEnabled(true);

                                    bean.setClick(true);

                                    ivDianzan.setImageResource(R.mipmap.dianzan);

                                    InitData();
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
            //???????????????
            case R.id.iv_back:
                finish();
                break;
            //????????????
            case R.id.iv_more:
                // TODO: 2020/12/28 0028 ????????????
                break;
            //????????????
            case R.id.bt_confirm:
                //??????????????????
                String s = etContent.getText().toString();
                NetUtils.getInstance().getApis().doWriteComment(userId, commentUserid, s, 1, commentId, 1)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<CollectDynamicBean>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(CollectDynamicBean collectDynamicBean) {
                                AllList.clear();
                                InitData();
                                getSecondComment(1, 15);
                                KeyBoardUtils.closeKeyboard(etContent);
                                etContent.setText("");
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        });
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getStr(String str) {
        if (str.equals("????????????")) {
            AllList.clear();
            getSecondComment(1, 15);
        }
    }

    public void getSecondComment(int page, int size) {
        //????????????????????????
        NetUtils.getInstance().getApis()
                .doGetComment(commentId, 1, userId, page, size)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CommentBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(CommentBean commentBean) {
                        List<CommentBean.ObjectBean> list = commentBean.getObject();
                        //??????????????????????????????
                        if (list.size() > 0 && list != null) {
                            sv.setHeader(new AliHeader(CommentDetailsActivity.this));

                            rlNotdata.setVisibility(View.GONE);
                            sv.setVisibility(View.VISIBLE);

                            AllList.addAll(list);
                            //???????????????
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CommentDetailsActivity.this, RecyclerView.VERTICAL, false);
                            rcComment.setLayoutManager(linearLayoutManager);
                            CommentDetailsAdapter commentDetailsAdapter = new CommentDetailsAdapter(CommentDetailsActivity.this, AllList);
                            rcComment.setAdapter(commentDetailsAdapter);
                        } else {
                            if (AllList.size() > 0 && AllList != null) {
                                Toast.makeText(CommentDetailsActivity.this, "?????????????????????", Toast.LENGTH_SHORT).show();
                            } else {
                                rlNotdata.setVisibility(View.VISIBLE);
                                sv.setVisibility(View.GONE);
                            }
                        }

                        if (list.size() > 14) {
                            sv.setFooter(new AliFooter(CommentDetailsActivity.this));
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

    public void InitData() {
        NetUtils.getInstance().getApis()
                .doGetCommentbyId(commentId, userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<OneCommentBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(OneCommentBean oneCommentBean) {


                        bean = oneCommentBean.getObject();
                        commentUserid = bean.getId();
                        //??????????????????
                        OneCommentBean.ObjectBean.UserInfoBean userInfo = bean.getUserInfo();

                        //???????????????
                        tvName.setText(userInfo.getNickName());
                        //????????????
                        Glide.with(CommentDetailsActivity.this).load(userInfo.getHeadImg()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(ivHeadimg);
                        //???????????????
                        int clickNum = bean.getClickNum();
                        if (clickNum < 10000) {
                            tvDianzanCount.setText(clickNum + "");
                        } else {
                            String s = StringUtil.rawIntStr2IntStr(String.valueOf(clickNum));

                            tvDianzanCount.setText(s);
                        }
                        //????????????????????????
                        ivHeadimg.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent intent = new Intent(CommentDetailsActivity.this, PersonHomeActivity.class);
                                SaveIntentMsgBean saveIntentMsgBean = new SaveIntentMsgBean();
                                saveIntentMsgBean.setId(userInfo.getId());
                                //2??????????????????  1????????????id
                                saveIntentMsgBean.setFlag(1);
                                intent.putExtra("msg", saveIntentMsgBean);
                                startActivity(intent);
                            }
                        });
                        //??????????????????
                        if (bean.isClick()) {
                            ivDianzan.setImageResource(R.mipmap.dianzan);
                        } else {
                            ivDianzan.setImageResource(R.mipmap.weidianzan);
                        }
                        //????????????
                        tvContent.setText(bean.getCommentInfo());
                        //??????????????????
                        String createTime = bean.getCreateTime();
                        if (createTime != null) {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd HH:mm:ss", Locale.SIMPLIFIED_CHINESE);
                            try {
                                Date parse = sdf.parse(createTime);
                                long time = parse.getTime();
                                //??????????????????
                                long l = System.currentTimeMillis();
                                //????????????????????????
                                long difference = l - time;

                                //????????????12?????? ????????????
                                if (difference > 43200000) {
                                    tvTime.setText(createTime);
                                }
                                //????????????12?????? ????????????
                                if (difference > 1800000 && difference < 43200000) {
                                    String[] s = createTime.split(" ");
                                    tvTime.setText(s[1]);
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
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getBean(CommentBean.ObjectBean bean) {
        if (bean != null) {
            rlSendReply.setVisibility(View.VISIBLE);
            KeyBoardUtils.openKeyBoard(etReply);

            etReply.setHint("??????:" + bean.getUserInfo().getNickName());
            btReply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String str = etReply.getText().toString();
                    NetUtils.getInstance().getApis()
                            .doWriteComment(userId, bean.getUserId(), str, 1, commentId, 2)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<CollectDynamicBean>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(CollectDynamicBean collectDynamicBean) {
                                    //??????????????????
                                    rlSendReply.setVisibility(View.GONE);
                                    etReply.setText("");
                                    KeyBoardUtils.closeKeyboard(etReply);
                                    //????????????
                                    AllList.clear();
                                    getSecondComment(1, 15);
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
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
