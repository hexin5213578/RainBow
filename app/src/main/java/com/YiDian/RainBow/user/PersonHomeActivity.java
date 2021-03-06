package com.YiDian.RainBow.user;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import androidx.viewpager.widget.ViewPager;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseAvtivity;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.base.Common;
import com.YiDian.RainBow.custom.loading.CustomDialog;
import com.YiDian.RainBow.friend.activity.FriendsActivity;
import com.YiDian.RainBow.login.bean.ComPleteMsgBean;
import com.YiDian.RainBow.main.fragment.home.adapter.UserDetailsDynamicAdapter;
import com.YiDian.RainBow.main.fragment.home.bean.FollowBean;
import com.YiDian.RainBow.main.fragment.home.bean.NewDynamicBean;
import com.YiDian.RainBow.main.fragment.mine.activity.MyGiftActivity;
import com.YiDian.RainBow.main.fragment.msg.activity.FriendImActivity;
import com.YiDian.RainBow.main.fragment.msg.adapter.GridViewAdapter;
import com.YiDian.RainBow.main.fragment.msg.adapter.ViewPagerAdapter;
import com.YiDian.RainBow.main.fragment.msg.bean.GiftMsgBean;
import com.YiDian.RainBow.main.fragment.msg.bean.GlodNumBean;
import com.YiDian.RainBow.setup.bean.InsertRealBean;
import com.YiDian.RainBow.topic.SaveIntentMsgBean;
import com.YiDian.RainBow.user.bean.UserMsgBean;
import com.YiDian.RainBow.utils.MD5Utils;
import com.YiDian.RainBow.utils.NetUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.dmcbig.mediapicker.PickerActivity;
import com.dmcbig.mediapicker.PickerConfig;
import com.dmcbig.mediapicker.entity.Media;
import com.leaf.library.StatusBarUtil;
import com.liaoinstan.springview.container.AliFooter;
import com.liaoinstan.springview.container.AliHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.tencent.tauth.Tencent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

//-----------------------------------------------------


//????????????  ??????????????????
public class PersonHomeActivity extends BaseAvtivity implements View.OnClickListener {

    String TAG = "xxx";


    @BindView(R.id.Iv_beijing)
    ImageView IvBeijing;
    @BindView(R.id.tv_gxqianming)
    TextView tvGxqianming;
    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.iv_userimg)
    ImageView ivUserimg;
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.ll_candian)
    LinearLayout llCandian;
    @BindView(R.id.ll_fensi)
    LinearLayout llFensi;
    @BindView(R.id.ll_guanzhu)
    LinearLayout llGuanzhu;
    @BindView(R.id.ll_liwu)
    LinearLayout llLiwu;
    @BindView(R.id.rc_dynamic)
    RecyclerView rcDynamic;
    @BindView(R.id.sv)
    SpringView sv;
    @BindView(R.id.tv_age)
    TextView tvAge;
    @BindView(R.id.tv_fensi_c)
    TextView tvFensiC;
    @BindView(R.id.tv_guanzhu_c)
    TextView tvGuanzhuC;
    @BindView(R.id.tv_liwu_c)
    TextView tvLiwuC;
    @BindView(R.id.v1)
    View v1;
    @BindView(R.id.rl_nodata)
    RelativeLayout rlNodata;
    @BindView(R.id.bt_send)
    Button btSend;
    @BindView(R.id.bt_guanzhu)
    Button btGuanzhu;
    private String img1;
    private int myId;
    private Intent intent;
    int page = 1;
    List<NewDynamicBean.ObjectBean.ListBean> allList;
    private Tencent mTencent;
    private int flag;
    private String name;
    private int thePageuserId;
    private PopupWindow mPopupWindow1;
    private ViewPager vp;
    private LinearLayout lldot;
    private TextView tv_balance;
    private int pageCount;
    private GridViewAdapter[] arr;
    /*???????????????????????????*/
    private int curIndex = 0;
    private List<GiftMsgBean.ObjectBean> list;
    private LayoutInflater inflater;
    private int reciveid = 0;
    private boolean isfollow = false;
    private int selectnum=-1;
    private CustomDialog dialog;

    @Override
    protected int getResId() {
        return R.layout.activity_personhome;
    }

    @Override
    protected void getData() {
        //??????AppId(???????????????App Id)????????????
        mTencent = Tencent.createInstance("101906973", PersonHomeActivity.this);

        llBack.setOnClickListener(this);
        llFensi.setOnClickListener(this);
        llLiwu.setOnClickListener(this);
        llGuanzhu.setOnClickListener(this);
        llCandian.setOnClickListener(this);
        IvBeijing.setOnClickListener(this);
        btSend.setOnClickListener(this);
        btGuanzhu.setOnClickListener(this);

        //??????????????????
        RecyclerView.ItemAnimator animator = rcDynamic.getItemAnimator();
        if (animator instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
        }


        allList = new ArrayList<>();
        //??????????????????
        StatusBarUtil.setTransparentForWindow(this);
        //???????????????????????????
        StatusBarUtil.setLightMode(this);

        Intent intent =
                getIntent();
        SaveIntentMsgBean msg = (SaveIntentMsgBean) intent.getSerializableExtra("msg");
        flag = msg.getFlag();
        //????????????id
        thePageuserId = msg.getId();
        //????????????name
        name = msg.getMsg();
        sv.setHeader(new AliHeader(this));
        myId = Integer.parseInt(Common.getUserId());
        String myName = Common.getUserName();

        dialog = new CustomDialog(this, "????????????");

        //2??????????????????  1????????????id
        if (flag == 2) {
            //??????????????????
            Log.d("xxx", "?????????????????????" + name);
            bandpageinfo(myId, name);
            dogetDynamicByName(page, name);

            if (!name.equals(myName)) {
                Log.d("xxx", "getData: ?????????????????????");
                isotherpage(false);
                //???????????????
                doInsert(name);

            } else {
                isotherpage(true);
                Log.d("xxx", "getData: ???????????????");
            }

            sv.setListener(new SpringView.OnFreshListener() {
                @Override
                public void onRefresh() {
                    bandpageinfo(myId, name);
                    allList.clear();
                    page = 1;
                    dogetDynamicByName(page, name);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //??????2.5??????????????????
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
                            dogetDynamicByName(page, name);
                            sv.onFinishFreshAndLoad();
                        }
                    }, 1000);
                }
            });
        } else {
            //??????????????????
            Log.d("xxx", "????????????id???" + thePageuserId);
            bandpageinfo(myId, thePageuserId);

            dogetDynamicById(page, thePageuserId);

            if (!(thePageuserId == myId)) {
                Log.d("xxx", "getData: ?????????????????????");
                isotherpage(false);//??????????????????????????????
                //???????????????
                doInsert(String.valueOf(thePageuserId));


            } else {
                isotherpage(true);
                Log.d("xxx", "getData: ???????????????");
            }
            //???????????????????????????????????????
            sv.setListener(new SpringView.OnFreshListener() {
                @Override
                public void onRefresh() {
                    bandpageinfo(myId, thePageuserId);
                    allList.clear();
                    page = 1;
                    dogetDynamicById(page, thePageuserId);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

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
                            dogetDynamicById(page, thePageuserId);
                            sv.onFinishFreshAndLoad();
                        }
                    }, 1000);
                }
            });
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    //????????????
    public void doInsert(String str) {
        NetUtils.getInstance().getApis()
                .doInsertFangke(str, myId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<InsertRealBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(InsertRealBean insertRealBean) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    //true ??????????????????   false  ??????????????????
    public void isotherpage(boolean ischeck) {
        llFensi.setEnabled(ischeck);
        llGuanzhu.setEnabled(ischeck);
        llLiwu.setEnabled(ischeck);
        IvBeijing.setEnabled(ischeck);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getStr(String str) {
        allList.clear();
        if (str.equals("????????????")) {
            if (flag == 2) {
                dogetDynamicByName(page, name);
                bandpageinfo(myId,name);
            } else {
                dogetDynamicById(page, thePageuserId);
                bandpageinfo(myId,thePageuserId);
            }
        }
    }

    //?????????????????????????????????
    public void dogetDynamicById(int page, int thePageuserId) {
        dialog.show();
        NetUtils.getInstance().getApis().
                doGetDynamicByUserid(thePageuserId, myId, page, 5).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.
                        mainThread()).
                subscribe(new Observer<NewDynamicBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(NewDynamicBean newDynamicBean) {
                        dialog.dismiss();
                        List<NewDynamicBean.ObjectBean.ListBean> list = newDynamicBean.getObject().getList();

                        if (list.size() > 0 && list != null) {
                            //RelativeLayout rlNodata;
                            rlNodata.setVisibility(View.GONE);
                            //RecyclerView  rcDynamic
                            rcDynamic.setVisibility(View.VISIBLE);

                            allList.addAll(list);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(PersonHomeActivity.this, RecyclerView.VERTICAL, false);
                            rcDynamic.setLayoutManager(linearLayoutManager);

                            UserDetailsDynamicAdapter userDetailsDynamicAdapter = new UserDetailsDynamicAdapter(PersonHomeActivity.this, allList, mTencent);
                            rcDynamic.setAdapter(userDetailsDynamicAdapter);
                        } else {
                            if (allList.size() > 0) {
                                Toast.makeText(PersonHomeActivity.this, "?????????????????????", Toast.LENGTH_SHORT).show();
                            } else {
                                rlNodata.setVisibility(View.VISIBLE);
                                rcDynamic.setVisibility(View.GONE);
                            }
                        }
                        if (list.size() > 4) {
                            //????????????
                            sv.setFooter(new AliFooter(PersonHomeActivity.this));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        dialog.dismiss();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void dogetDynamicByName(int page, String name) {
        dialog.show();
        //????????????
        NetUtils.getInstance().getApis().
                doGetDynamicByName(name, myId, page, 5).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.
                        mainThread()).
                subscribe(new Observer<NewDynamicBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(NewDynamicBean newDynamicBean) {
                        dialog.dismiss();

                        List<NewDynamicBean.ObjectBean.ListBean> list = newDynamicBean.getObject().getList();

                        if (list.size() > 0 && list != null) {
                            rlNodata.setVisibility(View.GONE);
                            rcDynamic.setVisibility(View.VISIBLE);

                            allList.addAll(list);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(PersonHomeActivity.this, RecyclerView.VERTICAL, false);
                            rcDynamic.setLayoutManager(linearLayoutManager);

                            UserDetailsDynamicAdapter userDetailsDynamicAdapter = new UserDetailsDynamicAdapter(PersonHomeActivity.this, allList, mTencent);
                            rcDynamic.setAdapter(userDetailsDynamicAdapter);
                        } else {
                            if (allList.size() > 0) {
                                Toast.makeText(PersonHomeActivity.this, "?????????????????????", Toast.LENGTH_SHORT).show();
                            } else {
                                rlNodata.setVisibility(View.VISIBLE);
                                rcDynamic.setVisibility(View.GONE);
                            }
                        }
                        if (list.size() > 4) {
                            sv.setFooter(new AliFooter(PersonHomeActivity.this));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        dialog.dismiss();

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    //??????????????????????????????????????? ?????????????????????
    public void bandpageinfo(int myId, int thePageuserId) {
        //??????id   ?????????id   ????????????????????????
        NetUtils.getInstance().getApis().doGetUserMsgById(myId, thePageuserId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserMsgBean>() {

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(UserMsgBean userMsgBean) {
                        //?????????????????????????????????
                        if (userMsgBean.getMsg().equals("????????????")) {
                            //???????????????????????????  ????????????
                            UserMsgBean.ObjectBean object = userMsgBean.getObject();
                            UserMsgBean.ObjectBean.UserInfoBean userInfo = userMsgBean.getObject().getUserInfo();

                            if (userInfo.getIsFans()==0){
                                isfollow=  false;
                                btGuanzhu.setText("??????");
                            }else{
                                isfollow = true;
                                btGuanzhu.setText("??????");
                            }
                            reciveid = userInfo.getId();

                            String fenSiCount = object.getCountFansNum() + "";
                            String guanzhu = object.getCountFavoriteNum() + "";
                            String liwu = object.getCountGiftNum() + "";

                            String backImg = userInfo.getBackImg();
                            Integer friendAge = userInfo.getAge();

                            String userRole = userInfo.getUserRole();// ???????????????
                            String userRoleAge = null;     //???????????????   + ??????
                            String friendname = userInfo.getNickName();//??????
                            String headImg = userInfo.getHeadImg();//??????

                            String explains = userInfo.getExplains();
                            if (explains == null) {
                                String gxQianMing = "";//????????????
                                tvGxqianming.setText(gxQianMing);
                            } else {
                                String gxQianMing = "???????????????" + explains;//????????????
                                tvGxqianming.setText(gxQianMing);
                            }

                            if (userRole == null) {
                                if (friendAge != null) {
                                    userRoleAge = friendAge + "";
                                } else {
                                    userRoleAge = 0 + "";
                                }
                            } else if (userRole.equals("??????")) {
                                if (friendAge != null) {
                                    userRoleAge = friendAge + "";
                                } else {
                                    userRoleAge = 0 + "";
                                }
                            } else {
                                if (friendAge != null) {
                                    userRoleAge = userRole + " " + friendAge;
                                } else {
                                    userRoleAge = 0 + "";
                                }
                            }


                            Glide.with(PersonHomeActivity.this).load(headImg).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(ivUserimg);
                            if (backImg == null)
                                IvBeijing.setBackgroundColor(PersonHomeActivity.this.getResources().getColor(R.color.color_8867E7));
                            else {
                                Glide.with(PersonHomeActivity.this).load(backImg).into(IvBeijing);
                            }

                            //???????????????
                            tvAge.setText(userRoleAge);
                            tvFensiC.setText(fenSiCount);//?????????
                            tvGuanzhuC.setText(guanzhu);//?????????
                            tvLiwuC.setText(liwu);//?????????
                            tvUsername.setText(friendname);//????????????

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

    public void bandpageinfo(int myId, String name) {
//        doGetUserMsgByName
        NetUtils.getInstance().getApis().doGetUserMsgByName(myId, name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserMsgBean>() {

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(UserMsgBean userMsgBean) {
                        //?????????????????????????????????
                        if (userMsgBean.getMsg().equals("????????????")) {
                            //???????????????????????????  ????????????
                            UserMsgBean.ObjectBean object = userMsgBean.getObject();
                            UserMsgBean.ObjectBean.UserInfoBean userInfo = userMsgBean.getObject().getUserInfo();

                            reciveid = userInfo.getId();

                            if (userInfo.getIsFans()==0){
                                isfollow=  false;
                                btGuanzhu.setText("??????");
                            }else{
                                isfollow = true;
                                btGuanzhu.setText("??????");
                            }

                            String fenSiCount = object.getCountFansNum() + "";
                            String guanzhu = object.getCountFavoriteNum() + "";
                            String liwu = object.getCountGiftNum() + "";

                            String backImg = userInfo.getBackImg();
                            Integer friendAge = userInfo.getAge();

                            String userRole = userInfo.getUserRole();// ???????????????
                            String userRoleAge = null;     //???????????????   + ??????
                            String friendname = userInfo.getNickName();//??????
                            String headImg = userInfo.getHeadImg();//??????

                            String explains = userInfo.getExplains();
                            if (explains.equals("null")) {
                                tvGxqianming.setText("");
                            } else {
                                String gxQianMing = "???????????????" + explains;//????????????
                                tvGxqianming.setText(gxQianMing);
                            }


                            if (userRole == null) {
                                if (friendAge == null) {
                                    userRoleAge = 0 + "";
                                } else {
                                    userRoleAge = friendAge + "";
                                }
                            } else if (userRole.equals("??????")) {
                                if (friendAge == null) {
                                    userRoleAge = 0 + "";
                                } else {
                                    userRoleAge = friendAge + "";
                                }
                            } else {
                                if (friendAge == null) {
                                    userRoleAge = userRole + " " + 0;
                                } else {
                                    userRoleAge = userRole + " " + friendAge;
                                }
                            }


                            Glide.with(PersonHomeActivity.this).load(headImg).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(ivUserimg);
                            if (backImg == null)
                                IvBeijing.setBackgroundColor(PersonHomeActivity.this.getResources().getColor(R.color.color_8867E7));
                            else {
                                Glide.with(PersonHomeActivity.this).load(backImg).into(IvBeijing);
                            }

                            //???????????????
                            tvAge.setText(userRoleAge);
                            tvFensiC.setText(fenSiCount);//?????????
                            tvGuanzhuC.setText(guanzhu);//?????????
                            tvLiwuC.setText(liwu);//?????????
                            tvUsername.setText(friendname);


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
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //???????????????
            case R.id.ll_fensi:
                intent = new Intent(PersonHomeActivity.this, FriendsActivity.class);
                intent.putExtra("flag", 2);
                startActivity(intent);
                break;
            //???????????????
            case R.id.ll_guanzhu:
                intent = new Intent(PersonHomeActivity.this, FriendsActivity.class);
                intent.putExtra("flag", 4);
                startActivity(intent);
                break;
            //???????????????
            case R.id.ll_liwu:
                intent = new Intent(PersonHomeActivity.this, MyGiftActivity.class);
                startActivity(intent);
                break;
            //??????
            case R.id.ll_back:
                finish();
                break;
            //??????
            case R.id.ll_candian:

                break;
            //????????????  ????????????????????????????????????
            case R.id.Iv_beijing:
                Log.d(TAG, "onClick: ------->" + "????????????");
                intent = new Intent(PersonHomeActivity.this, PickerActivity.class);
                intent.putExtra(PickerConfig.SELECT_MODE, PickerConfig.PICKER_IMAGE);//????????????????????????????????????????????????????????????(???????????????)
                long maxSize = 10485760L;//long long long long??????
                intent.putExtra(PickerConfig.MAX_SELECT_SIZE, maxSize);
                intent.putExtra(PickerConfig.MAX_SELECT_COUNT, 1); //???????????????????????????40?????????????????????
                intent.putExtra("type", 1);
                startActivityForResult(intent, 1);
                break;
                //????????????
            case R.id.bt_send:
                // TODO: 2021/1/13 0013 ?????????????????????
                getGiftMsg();
                break;
            case R.id.bt_guanzhu:
                if (isfollow){
                    Log.d("xxx","????????? ??????");



                }else{
                    Log.d("xxx","????????????????????????");
                    btGuanzhu.setEnabled(false);
                    //??????
                    NetUtils.getInstance().getApis()
                            .doFollow(myId, reciveid)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<FollowBean>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(FollowBean followBean) {
                                    //???????????????????????????
                                    btGuanzhu.setEnabled(true);
                                    if (followBean.getMsg().equals("????????????")) {
                                        btGuanzhu.setText("??????");
                                        isfollow = true;
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
        }
    }

    //???????????????
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            ArrayList<Media> select = new ArrayList();
            //======================================================================
            if (requestCode == 1 && resultCode == PickerConfig.RESULT_CODE) {

                if (data.getParcelableArrayListExtra(PickerConfig.EXTRA_RESULT).size() > 0) {
                    select = data.getParcelableArrayListExtra(PickerConfig.EXTRA_RESULT);
                    String path = select.get(0).path;
                    Glide.with(PersonHomeActivity.this).load(path).into(IvBeijing);

                    Log.d(TAG, "onActivityResult: ????????????????????????");

                    upbeijing(path);
                }
                Log.i("select", "???????????????" + select.size());
            }
        }
    }

    //??????????????????
    public void upbeijing(String file1) {
        String upToken;
        String serverPath = "http://img.rianbow.cn/";

        upToken = Common.getToken();

        // ????????????????????? ?????? uploadManager???????????????????????????????????? uploadManager ??????
        UploadManager uploadManager = new UploadManager();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        // ????????????
        String s = MD5Utils.string2Md5_16(file1);
        String key = s + sdf.format(new Date()) + ".jpg";
        uploadManager.put(file1, key, upToken,
                new UpCompletionHandler() {
                    @Override
                    public void complete(String key, ResponseInfo info, JSONObject res) {
                        //res ?????? hash???key ??????????????????????????????????????????????????????
                        if (info.isOK()) {
                            // ????????????????????????
                            try {
                                String upimg = res.getString("key");
                                //??????????????????????????????????????????list?????????
                                img1 = serverPath + upimg;

                                //???????????? ?????????????????????????????????
                                NetUtils.getInstance().getApis()
                                        .doComPleteBackImg(myId, img1)
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new Observer<ComPleteMsgBean>() {
                                            @Override
                                            public void onSubscribe(Disposable d) {

                                            }

                                            @Override
                                            public void onNext(ComPleteMsgBean comPleteMsgBean) {
                                                if (comPleteMsgBean.getMsg().equals("?????????????????????")) {
                                                    Toast.makeText(PersonHomeActivity.this, "??????????????????", Toast.LENGTH_SHORT).show();
                                                }
                                            }

                                            @Override
                                            public void onError(Throwable e) {

                                            }

                                            @Override
                                            public void onComplete() {

                                            }
                                        });
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Log.i("xxx", "Upload Fail");
                            Log.i("xxx", "" + info);
                            //?????????????????????????????? info ?????????????????????????????????????????????????????????????????????
                        }
                        Log.i("xxx", img1);
                    }
                }, null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
    public  void getGiftMsg(){
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

                        if (giftMsgBean.getMsg().equals("????????????")){
                            list = giftMsgBean.getObject();
                            showSelectGift();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        dialog.dismiss();

                        Toast.makeText(PersonHomeActivity.this, "????????????????????????", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    //???????????????
    public void  getGlodNum(){
        NetUtils.getInstance().getApis()
                .dogetGldNum(myId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GlodNumBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(GlodNumBean glodNumBean) {
                        if (glodNumBean.getMsg().equals("????????????")){
                            tv_balance.setText(glodNumBean.getObject().getGoldAll()+"");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(PersonHomeActivity.this, "??????????????????", Toast.LENGTH_SHORT).show();
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
        View view = LayoutInflater.from(PersonHomeActivity.this).inflate(R.layout.dialog_sendgift, null);

        vp = view.findViewById(R.id.vp);
        lldot = view.findViewById(R.id.ll_dot);
        LinearLayout llrecharge= view.findViewById(R.id.ll_recharge);
        RelativeLayout ll_close = view.findViewById(R.id.ll_close);
        ImageView iv_anim = view.findViewById(R.id.iv_anim);
        tv_balance = view.findViewById(R.id.tv_balance);
        RelativeLayout rl_send = view.findViewById(R.id.rl_send);


        //???????????????
        getGlodNum();

        //????????????=??????/????????????????????????
        pageCount = (int) Math.ceil(list.size() * 1.0 / 8);
        inflater = LayoutInflater.from(PersonHomeActivity.this);

        //????????????
        List<View> mPagerList= new ArrayList<>();
        arr = new GridViewAdapter[pageCount];

        for (int j = 0; j < pageCount; j++) {
            final GridView gridview = (GridView) inflater.inflate(R.layout.bottom_vp_gridview, vp, false);
            final GridViewAdapter gridAdapter = new GridViewAdapter(PersonHomeActivity.this, list, j);
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
        vp.setAdapter(new ViewPagerAdapter(mPagerList, PersonHomeActivity.this));

        setOvalLayout();

        rl_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //???????????????????????????
                if (selectnum==-1){
                    Toast.makeText(PersonHomeActivity.this, "??????????????????", Toast.LENGTH_SHORT).show();
                }else{
                    GiftMsgBean.ObjectBean giftbean = list.get(selectnum);
                    Log.d("xxx","???????????????"+selectnum);


                    //???????????????????????????
                    NetUtils.getInstance().getApis()
                            .doSendGift(reciveid,myId,giftbean.getId())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<InsertRealBean>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(InsertRealBean insertRealBean) {
                                    if (insertRealBean.getMsg().equals("????????????")){
                                        //???????????? ????????????
                                        getGlodNum();

                                        //????????????
                                        iv_anim.setVisibility(View.VISIBLE);
                                        Glide.with(PersonHomeActivity.this).load(giftbean.getGiftImg()).into(iv_anim);


                                        ObjectAnimator Animator1 = ObjectAnimator.ofFloat(iv_anim, "translationY", -700);
                                        Animator1.setInterpolator(new LinearInterpolator());
                                        Animator1.setDuration(1000);

                                        ObjectAnimator Animator2 = ObjectAnimator.ofFloat(iv_anim, "rotation", 0.0F, 1080.0f);
                                        Animator2.setInterpolator(new LinearInterpolator());
                                        Animator2.setDuration(1500);

                                        AnimatorSet set=new AnimatorSet();
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
                                    if (insertRealBean.getMsg().equals("????????????")){
                                        // TODO: 2021/1/24 0024 ?????????????????????????????????
                                        Toast.makeText(PersonHomeActivity.this, "??????????????????", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(PersonHomeActivity.this, "????????????????????????????????????????????????", Toast.LENGTH_SHORT).show();
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
    //???????????????
    public void setWindowAlpa(boolean isopen) {
        if (Build.VERSION.SDK_INT < 11) {
            return;
        }

        final Window window = PersonHomeActivity.this.getWindow();
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
}

