package com.YiDian.RainBow.main.fragment.mine.activity;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseAvtivity;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.base.Common;
import com.YiDian.RainBow.custom.customDialog.EveryDayDialogDialog;
import com.YiDian.RainBow.main.fragment.mine.bean.ChackBuildLovesBean;
import com.YiDian.RainBow.main.fragment.mine.bean.LoveBulidBean;
import com.YiDian.RainBow.main.fragment.mine.bean.LoveStateBean;
import com.YiDian.RainBow.main.fragment.mine.bean.UserInfoById;
import com.YiDian.RainBow.utils.KeyBoardUtils;
import com.YiDian.RainBow.utils.NetUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.leaf.library.StatusBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LavesMarkActivity extends BaseAvtivity implements View.OnClickListener {
    private static final String TAG = "xxx";
    @BindView(R.id.ed_inputid)
    EditText edInputid;
    @BindView(R.id.r11)
    RelativeLayout r11;
    @BindView(R.id.iv_headimg1)
    ImageView ivHeadimg1;
    @BindView(R.id.tv_nicheng)
    TextView tvNicheng;
    @BindView(R.id.r12)
    LinearLayout r12;
    @BindView(R.id.tv_all)
    TextView tvAll;
    @BindView(R.id.r22)
    RelativeLayout r22;
    @BindView(R.id.iv_headimg)
    ImageView ivHeadimg;
    @BindView(R.id.tv_role)
    TextView tvRole;
    @BindView(R.id.r13)
    RelativeLayout r13;
    @BindView(R.id.tv_myid)
    TextView tvMyid;
    @BindView(R.id.tv_nicheng2)
    TextView tvNicheng2;
    @BindView(R.id.tv_explains)
    TextView tvExplains;
    @BindView(R.id.tv_bangding)
    TextView tvBangding;
    PopupWindow mPopupWindow1;
    int myid;
    @BindView(R.id.tv_jiechu)
    TextView tvJiechu;
    int loveid;
    @BindView(R.id.tv_requestCon)
    TextView tvRequestCon;
    @BindView(R.id.rl_ConsentRefusal)
    RelativeLayout rlConsentRefusal;
    @BindView(R.id.tv_true)
    TextView tvTrue;
    @BindView(R.id.tv_false)
    TextView tvFalse;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_back)
    LinearLayout ivBack;

    @Override
    protected int getResId() {
        return R.layout.activity_lavesmark;
    }

    @Override
    protected void getData() {
        //????????????
        tvBangding.setOnClickListener(this);
        tvJiechu.setOnClickListener(this);
        tvTrue.setOnClickListener(this);
        tvFalse.setOnClickListener(this);
        tvRequestCon.setOnClickListener(this);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //Integer.parseInt(Common.getUserId());
        myid = Integer.parseInt(Common.getUserId());
        //????????????????????????????????????
        StatusBarUtil.setGradientColor(LavesMarkActivity.this, toolbar);
        StatusBarUtil.setDarkMode(LavesMarkActivity.this);
        //
        refresh();
    }

    //??????????????????
    public void refresh() {

        NetUtils.getInstance().getApis().doGetLoveState(myid).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<LoveStateBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(LoveStateBean loveStateBean) {

                        if (loveStateBean.getType().equals("OK")) {
                            LoveStateBean.ObjectBean.UserInfoBean infoBean = loveStateBean.getObject().getUserInfo();
                            switch (loveStateBean.getMsg()) {
                                case "00":
                                    //????????????
                                    Log.d(TAG, "onNext: " + Common.getUserId());
                                    r11.setVisibility(View.VISIBLE);
                                    r12.setVisibility(View.GONE);
                                    r22.setVisibility(View.VISIBLE);
                                    r13.setVisibility(View.GONE);
                                    tvMyid.setText("??????ID???" + Common.getUserId());
                                    tvAll.setText("?????????????????????");
                                    break;
                                case "04":
                                    //????????????????????????
                                    r11.setVisibility(View.GONE);
                                    r12.setVisibility(View.VISIBLE);
                                    r13.setVisibility(View.GONE);
                                    r22.setVisibility(View.VISIBLE);
                                    tvRequestCon.setVisibility(View.GONE);
                                    rlConsentRefusal.setVisibility(View.VISIBLE);
                                    tvNicheng.setText(infoBean.getNickName());
                                    String path2 = infoBean.getHeadImg();
                                    Glide.with(LavesMarkActivity.this).load(path2).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(ivHeadimg1);
                                    tvAll.setText(infoBean.getNickName() + "????????????????????????");
                                    loveid = infoBean.getId();
                                    break;
                                case "11":
                                    //?????????
                                    r11.setVisibility(View.GONE);
                                    r12.setVisibility(View.GONE);
                                    r13.setVisibility(View.VISIBLE);
                                    r22.setVisibility(View.VISIBLE);
                                    tvAll.setText("??????????????????");
                                    //??????
                                    tvNicheng2.setText(loveStateBean.getObject().getUserInfo().getNickName());
                                    //??????
                                    if (infoBean.getExplains() != null) {
                                        tvExplains.setText(infoBean.getExplains());
                                    }
                                    //??????
                                    String path3 = infoBean.getHeadImg();
                                    Glide.with(LavesMarkActivity.this).load(path3).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(ivHeadimg);
                                    //??????
                                    if (infoBean.getUserRole() != null && (!infoBean.getUserRole().equals("??????"))) {
                                        tvRole.setText(infoBean.getUserRole());
                                    } else {
                                        tvRole.setVisibility(View.GONE);
                                    }
                                    //?????????id
                                    loveid = infoBean.getId();
                                    break;
                                case "03":

                                    //????????? ??????????????????
                                    r11.setVisibility(View.GONE);
                                    r22.setVisibility(View.VISIBLE);
                                    r12.setVisibility(View.VISIBLE);
                                    r13.setVisibility(View.GONE);
                                    tvAll.setText("???????????????????????????????????????");
                                    tvNicheng.setText(infoBean.getNickName());
                                    String path = infoBean.getHeadImg();
                                    Glide.with(LavesMarkActivity.this).load(path).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(ivHeadimg1);
                                    tvRequestCon.setVisibility(View.VISIBLE);
                                    rlConsentRefusal.setVisibility(View.GONE);
                                    loveid = infoBean.getId();
                                    break;

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

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void onClick(View v) {
        EveryDayDialogDialog.Builder builder = new EveryDayDialogDialog.Builder(LavesMarkActivity.this);
        switch (v.getId()) {
            //????????????
            case R.id.l_return:
                finish();
                break;
//                ????????????
            case R.id.tv_bangding:
                String str = edInputid.getText().toString();
                Log.d(TAG, "onClick: " + str);
                if (str.equals("")) {
                    builder.setMessage("?????????ID").setPositiveButton("??????", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.create().show();
                } else if (myid == Integer.parseInt(str)) {
                    builder.setMessage("???????????????????????????").setPositiveButton("??????", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.create().show();
                } else {
                    if (str.length() == 6) {
                        //?????????????????????????????????????????????????????????
                        NetUtils.getInstance().getApis().doGetUserInfobyId(Integer.parseInt(str)).
                                subscribeOn(Schedulers.io()).
                                observeOn(AndroidSchedulers.mainThread()).
                                subscribe(new Observer<UserInfoById>() {
                                    @Override
                                    public void onSubscribe(Disposable d) {

                                    }

                                    @Override
                                    public void onNext(UserInfoById userInfoById) {

                                        String msg = userInfoById.getMsg();

                                        if (userInfoById.getType().equals("OK")) {
                                            //???????????? ????????????
                                            showChangeName(userInfoById, myid, Integer.parseInt(str));
                                            //??????????????????
                                            KeyBoardUtils.closeKeyboard(LavesMarkActivity.this);

                                        } else {
                                            //???????????????
                                            EveryDayDialogDialog.Builder builder = new EveryDayDialogDialog.Builder(LavesMarkActivity.this);
                                            builder.setMessage("??????????????????").setPositiveButton("??????", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    //
                                                    dialog.dismiss();
                                                }
                                            });
                                            builder.create().show();
                                        }
                                    }

                                    @Override
                                    public void onError(Throwable e) {

                                    }

                                    @Override
                                    public void onComplete() {

                                    }
                                });

                    } else {
                        Log.d(TAG, "onClick: ??????ID??????");
                        builder.setMessage("??????ID??????").setPositiveButton("??????", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder.create().show();
                    }
                }
                break;
            //????????????
            case R.id.tv_jiechu:
                builder.setMessage("?????????????????????").setPositiveButton("??????", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //
                        NetUtils.getInstance().getApis().doGetReleaseLove(myid, loveid).
                                subscribeOn(Schedulers.io()).
                                observeOn(AndroidSchedulers.mainThread()).
                                subscribe(new Observer<LoveBulidBean>() {
                                    @Override
                                    public void onSubscribe(Disposable d) {

                                    }

                                    @Override
                                    public void onNext(LoveBulidBean loveBulidBean) {
                                        if (loveBulidBean.getType().equals("OK")) {
                                            refresh();
                                        }
                                    }

                                    @Override
                                    public void onError(Throwable e) {

                                    }

                                    @Override
                                    public void onComplete() {

                                    }
                                });
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("??????",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builder.create().show();
                break;
            //????????????
            case R.id.tv_requestCon:
                builder.setMessage("???????????????????????????").setPositiveButton("??????", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //
                        NetUtils.getInstance().getApis().doGetInterruption(myid, loveid).
                                subscribeOn(Schedulers.io()).
                                observeOn(AndroidSchedulers.mainThread()).
                                subscribe(new Observer<LoveBulidBean>() {
                                    @Override
                                    public void onSubscribe(Disposable d) {

                                    }

                                    @Override
                                    public void onNext(LoveBulidBean loveBulidBean) {
                                        if (loveBulidBean.getType().equals("OK")) {
                                            refresh();
                                        }
                                    }

                                    @Override
                                    public void onError(Throwable e) {

                                    }

                                    @Override
                                    public void onComplete() {

                                    }
                                });
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("??????",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builder.create().show();
                break;
                //??????????????????
            case R.id.tv_true:
                Log.d(TAG, "onClick: ------??????????????????");
                NetUtils.getInstance().getApis().doGetChackBuildLovers(myid,loveid,1).
                        subscribeOn(Schedulers.io()).
                        observeOn(AndroidSchedulers.mainThread()).
                        subscribe(new Observer<ChackBuildLovesBean>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(ChackBuildLovesBean chackBuildLovesBean) {
                                if(chackBuildLovesBean.getType().equals("OK")){

                                    refresh();
                                }else {
                                    Log.d(TAG, "onNext: ???????????????");
                                    refresh();
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
                // ??????????????????
            case R.id.tv_false:
                Log.d(TAG, "onClick: ------??????????????????");
                NetUtils.getInstance().getApis().doGetChackBuildLovers(myid,loveid,0).
                        subscribeOn(Schedulers.io()).
                        observeOn(AndroidSchedulers.mainThread()).
                        subscribe(new Observer<ChackBuildLovesBean>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(ChackBuildLovesBean chackBuildLovesBean) {
                                if(chackBuildLovesBean.getType().equals("OK")){

                                    refresh();
                                }else {
                                    Log.d(TAG, "onNext: ???????????????");
                                    refresh();
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

        }
    }

    // ???????????????
    public void showChangeName(UserInfoById userInfoById, int userPId, int userTId) {

        Log.d(TAG, "showChangeName: -------->");
        //??????popwiondow?????????
        mPopupWindow1 = new PopupWindow();
        mPopupWindow1.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow1.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_send_chack, null);
        TextView tvtrue = view.findViewById(R.id.tv_true);
        TextView tvfalse = view.findViewById(R.id.tv_false);
        TextView nicheng = view.findViewById(R.id.tv_dialog_nicheng);
        ImageView im = view.findViewById(R.id.iv_headimg4);
        //?????????????????? ??????
        nicheng.setText(userInfoById.getObject().getNickName());
        String path = userInfoById.getObject().getHeadImg();
        Glide.with(LavesMarkActivity.this).load(path).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(im);

        //??????????????????????????????  ????????????????????????
        tvtrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: ----------------------" + userPId + "-------" + userTId);
                NetUtils.getInstance().getApis().doGetBuildLovers(userPId, userTId).
                        subscribeOn(Schedulers.io()).
                        observeOn(AndroidSchedulers.mainThread()).
                        subscribe(new Observer<LoveBulidBean>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(LoveBulidBean loveBulidBean) {
                                if (loveBulidBean.getType().equals("OK")) {
                                    Log.d(TAG, "onNext: ????????????????????????????????????");
                                    dismiss();

                                }
                                //??????????????????
                                refresh();
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
        //????????????????????? ????????????
        tvfalse.setOnClickListener(new View.OnClickListener() {
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
        show(view);
    }

    /**
     * ??????PopupWindow
     */
    private void show(View v) {
        if (mPopupWindow1 != null && !mPopupWindow1.isShowing()) {
            mPopupWindow1.showAtLocation(v, Gravity.CENTER, 0, 0);
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

}
