package com.YiDian.RainBow.user;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseAvtivity;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.base.Common;
import com.YiDian.RainBow.topic.SaveIntentMsgBean;
import com.YiDian.RainBow.user.bean.UserMsgBean;
import com.YiDian.RainBow.utils.NetUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.leaf.library.StatusBarUtil;
import com.liaoinstan.springview.widget.SpringView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

//用户主页  头像点击进入
public class PersonHomeActivity extends BaseAvtivity implements View.OnClickListener {
    @BindView(R.id.Iv_beijing)
    ImageView IvBeijing;
    @BindView(R.id.tv_gxqianming)
    TextView tvGxqianming;

    @BindView(R.id.ll_tag)
    LinearLayout llTag;
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
    @BindView(R.id.ll_fenguanli)
    LinearLayout llFenguanli;
    @BindView(R.id.rc_dynamic)
    RecyclerView rcDynamic;
    @BindView(R.id.ll_globe)
    LinearLayout llGlobe;
    @BindView(R.id.sv)
    SpringView sv;
    @BindView(R.id.tv_age)
    TextView tvAge;
    @BindView(R.id.tv_fensi_c)
    TextView tvFensiC;
    @BindView(R.id.tv_fensi)
    TextView tvFensi;

    @BindView(R.id.tv_guanzhu_c)
    TextView tvGuanzhuC;
    @BindView(R.id.tv_guanzhu)
    TextView tvGuanzhu;
    @BindView(R.id.tv_liwu_c)
    TextView tvLiwuC;
    @BindView(R.id.tv_liwu)
    TextView tvLiwu;
    @BindView(R.id.r0)
    RelativeLayout r0;
    @BindView(R.id.v1)
    View v1;


    //================================================================================//


    @Override
    protected int getResId() {
        return R.layout.activity_personhome;
    }

    @Override
    protected void getData() {

        Intent intent =
                getIntent();
        SaveIntentMsgBean msg = (SaveIntentMsgBean) intent.getSerializableExtra("msg");
        int flag = msg.getFlag();
        //获取这个人的信息id
        int thePageuserId = msg.getId();

        int myId = Integer.parseInt(Common.getUserId());
        String myName = Common.getUserName();

        //2标记传入姓名  1标记传入id
        if (flag == 2) {
            String name = msg.getMsg();
//            llBack.setOnClickListener(this);
            if (!name.equals(myName)) {
                Log.d("xxx", "getData: 不是自己的主页");
                isotherpage();
            }
            Log.d("xxx", "getData: 自己的主页");
            bandpageinfo(myId, name);

            Log.d("xxx", "传过来的姓名为" + name);

        } else {
            if (!(thePageuserId == myId)) {
                Log.d("xxx", "getData: 不是自己的主页");
                isotherpage();
            }
            Log.d("xxx", "getData: 自己的主页");
            bandpageinfo(myId, thePageuserId);


            Log.d("xxx", "传过来的id为" + thePageuserId);
        }

        llBack.setOnClickListener(this);
        llFensi.setOnClickListener(this);
        llLiwu.setOnClickListener(this);
        llGuanzhu.setOnClickListener(this);
        llCandian.setOnClickListener(this);


        //设置背景透明
        StatusBarUtil.setTransparentForWindow(this);
        //设置状态栏字体黑色
        StatusBarUtil.setLightMode(this);

        Log.d("xxx", "进入getdata");

    }

    //获取用户（好友或自己）信息 并绑定的到页面
    public void bandpageinfo(int myId, int thePageuserId) {
        //自己id   要查询id   并绑定到标签上面
        NetUtils.getInstance().getApis().doGetUserMsgById(myId, thePageuserId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserMsgBean>() {

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(UserMsgBean userMsgBean) {
                        //拿到查询好友的用户信息
                        if (userMsgBean.getMsg().equals("查询成功")) {
                            //拿到头像图片的路经  背景路径
                            UserMsgBean.ObjectBean object = userMsgBean.getObject();
                            UserMsgBean.ObjectBean.UserInfoBean userInfo = userMsgBean.getObject().getUserInfo();

                            String fenSiCount = object.getCountFansNum() + "";
                            String guanzhu = object.getCountFavoriteNum() + "";
                            String liwu = object.getCountGiftNum() + "";

                            String backImg = userInfo.getBackImg();
                            int friendAge = userInfo.getAge();

                            String userRole = userInfo.getUserRole();// 用户真实名
                            String userRoleAge = null;     //用户真实名   + 年龄
                            String friendname = userInfo.getNickName();//昵称
                            String headImg = userInfo.getHeadImg();//头像
                            String gxQianMing = "个性签名：" + userInfo.getExplains();//个性签名

                            if (userRole == null) {
                                userRoleAge = friendAge + "";
                            } else if (userRole.equals("保密")) {
                                userRoleAge = friendAge + "";
                            } else {
                                userRoleAge = userRole + friendAge;
                            }


                            Glide.with(PersonHomeActivity.this).load(headImg).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(ivUserimg);
                            if (backImg == null)
                                IvBeijing.setBackgroundColor(PersonHomeActivity.this.getResources().getColor(R.color.color_8867E7));
                            else {
                                Glide.with(PersonHomeActivity.this).load(backImg).into(IvBeijing);
                            }

                            //设置标签值
                            tvAge.setText(userRoleAge);
                            tvFensiC.setText(fenSiCount);//粉丝数
                            tvGuanzhuC.setText(guanzhu);//关注数
                            tvLiwuC.setText(liwu);//礼物数
                            tvUsername.setText(friendname);//设置昵称
                            tvGxqianming.setText(gxQianMing);

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
                        //拿到查询好友的用户信息
                        if (userMsgBean.getMsg().equals("查询成功")) {
                            //拿到头像图片的路经  背景路径
                            UserMsgBean.ObjectBean object = userMsgBean.getObject();
                            UserMsgBean.ObjectBean.UserInfoBean userInfo = userMsgBean.getObject().getUserInfo();

                            String fenSiCount = object.getCountFansNum() + "";
                            String guanzhu = object.getCountFavoriteNum() + "";
                            String liwu = object.getCountGiftNum() + "";

                            String backImg = userInfo.getBackImg();
                            int friendAge = userInfo.getAge();
                            String userRoleAge = userInfo.getUserRole() + " " + friendAge;
                            String friendname = userInfo.getNickName();
                            String headImg = userInfo.getHeadImg();
                            String gxQianMing = "个性签名：" + userInfo.getExplains();//个性签名

                            Glide.with(PersonHomeActivity.this).load(headImg).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(ivUserimg);
                            if (backImg == null)
                                IvBeijing.setBackgroundColor(PersonHomeActivity.this.getResources().getColor(R.color.color_8867E7));
                            else {
                                Glide.with(PersonHomeActivity.this).load(backImg).into(IvBeijing);
                            }

                            //设置标签值
                            tvAge.setText(userRoleAge);
                            tvFensiC.setText(fenSiCount);//粉丝数
                            tvGuanzhuC.setText(guanzhu);//关注数
                            tvLiwuC.setText(liwu);//礼物数
                            tvUsername.setText(friendname);
                            tvGxqianming.setText(gxQianMing);

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

    //true 是别人的主页   false  是自己的主页
    public void isotherpage() {
        llFensi.setEnabled(false);
        llGuanzhu.setEnabled(false);
        llLiwu.setEnabled(false);
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
    protected void onDestroy() {
        Log.d("xxx", "onDestroy: 该页面已经销毁");
        super.onDestroy();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //粉丝被点击
            case R.id.ll_fensi:
//
                break;
            case R.id.ll_guanzhu:

                break;
            case R.id.ll_liwu:

                break;

            case R.id.ll_back:
                finish();
                break;
            case R.id.ll_candian:

                break;
            case R.id.Iv_beijing:
//                showSelectImgAndVideo();

                Toast.makeText(this, "更换背景", Toast.LENGTH_SHORT).show();
                break;


        }


    }
}
