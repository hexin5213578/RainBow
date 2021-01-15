package com.YiDian.RainBow.user;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseAvtivity;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.base.Common;
import com.YiDian.RainBow.main.fragment.home.activity.DynamicDetailsActivity;
import com.YiDian.RainBow.main.fragment.home.adapter.NewDynamicAdapter;
import com.YiDian.RainBow.main.fragment.home.adapter.TopicsAdapter;
import com.YiDian.RainBow.main.fragment.home.bean.DynamicDetailsBean;
import com.YiDian.RainBow.main.fragment.home.bean.NewDynamicBean;
import com.YiDian.RainBow.main.fragment.msg.activity.FriendImActivity;
import com.YiDian.RainBow.setup.activity.RealnameActivity;
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
import com.liaoinstan.springview.widget.SpringView;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.tencent.bugly.proguard.A;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.BlockingDeque;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

//-----------------------------------------------------
import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseAvtivity;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.base.Common;
import com.YiDian.RainBow.setup.bean.GetRealDataBean;
import com.YiDian.RainBow.setup.bean.InsertRealBean;
import com.YiDian.RainBow.utils.MD5Utils;
import com.YiDian.RainBow.utils.NetUtils;
import com.YiDian.RainBow.utils.SPUtil;
import com.YiDian.RainBow.utils.StringUtil;
import com.bumptech.glide.Glide;
import com.huawei.hms.mlplugin.card.icr.cn.MLCnIcrCapture;
import com.huawei.hms.mlplugin.card.icr.cn.MLCnIcrCaptureConfig;
import com.huawei.hms.mlplugin.card.icr.cn.MLCnIcrCaptureFactory;
import com.huawei.hms.mlplugin.card.icr.cn.MLCnIcrCaptureResult;
import com.leaf.library.StatusBarUtil;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.tencent.tauth.Tencent;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


//用户主页  头像点击进入
public class PersonHomeActivity extends BaseAvtivity implements View.OnClickListener {

    String TAG = "xxx";


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
    private String img1;

    //================================================================================//
    private ArrayList<Media> select1;

    @Override
    protected int getResId() {
        return R.layout.activity_personhome;
    }

    @Override
    protected void getData() {

        llBack.setOnClickListener(this);
        llFensi.setOnClickListener(this);
        llLiwu.setOnClickListener(this);
        llGuanzhu.setOnClickListener(this);
        llCandian.setOnClickListener(this);
        IvBeijing.setOnClickListener(this);


        //设置背景透明
        StatusBarUtil.setTransparentForWindow(this);
        //设置状态栏字体黑色
        StatusBarUtil.setLightMode(this);


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

                //增加访问量



            }else{
                Log.d("xxx", "getData: 自己的主页");
            }

            bandpageinfo(myId, name);

            Log.d("xxx", "传过来的姓名为" + name);

        } else {
            if (!(thePageuserId == myId)) {
                Log.d("xxx", "getData: 不是自己的主页");
                isotherpage();//设置部分控件不能点击
                //渲染动态
                Log.d(TAG, "getData: ---------------------------------------------");
                //dongtai(1,thePageuserId,myId);
                //增加访问量


            }else {
                Log.d("xxx", "getData: 自己的主页");
            }

            bandpageinfo(myId, thePageuserId);
            Log.d("xxx", "传过来的id为" + thePageuserId);
        }





    }


    //渲染列表
//        final  List<NewDynamicBean.ObjectBean.ListBean> list;
    List<NewDynamicBean.ObjectBean.ListBean>  rcDynamics;
    public void dongtai(int page,int thePageuserId,int myId){
        //展示话题
        NetUtils.getInstance().getApis().
                doGetDynamicByUserid(thePageuserId,myId,page,15).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.
                mainThread()).
                subscribe(new Observer<NewDynamicBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(NewDynamicBean newDynamicBean) {
                        if(newDynamicBean.getMsg().equals("findContentByUserId")){
                            rcDynamics = newDynamicBean.getObject().getList();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

//        if (rcDynamics.size()) {
//            //设置展示话题
//            rcDynamic.setVisibility(View.VISIBLE);
//            GridLayoutManager gridLayoutManager = new GridLayoutManager(PersonHomeActivity.this, 4);
//            rcDynamic.setLayoutManager(gridLayoutManager);
//            Tencent  mTencent = Tencent.createInstance("101906973", this);
//            NewDynamicAdapter adapter = new NewDynamicAdapter(PersonHomeActivity.this, rcDynamics,mTencent);
//            rcDynamic.setAdapter(adapter);
//
//        } else {
//            rcDynamic.setVisibility(View.GONE);
//        }

//        NewDynamicAdapter adapter = new NewDynamicAdapter(PersonHomeActivity.this,list)
//        rcDynamic.setAdapter(adapter);

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
                                userRoleAge = userRole +" "+ friendAge;
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
                                userRoleAge = userRole +" "+ friendAge;
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

                //Test
//                showSelectImgAndVideo();

                Log.d(TAG, "onClick: ------->" + "点击背景");
                Intent intent = new Intent(PersonHomeActivity.this, PickerActivity.class);
                intent.putExtra(PickerConfig.SELECT_MODE, PickerConfig.PICKER_IMAGE);//设置选择类型，默认是图片和视频可一起选择(非必填参数)
                long maxSize = 10485760L;//long long long long类型
                intent.putExtra(PickerConfig.MAX_SELECT_SIZE, maxSize);
                intent.putExtra(PickerConfig.MAX_SELECT_COUNT, 1); //最大选择数量，默认40（非必填参数）
//                PersonHomeActivity.this.startActivityForResult(intent, 201);
                intent.putExtra("type", 1);
                startActivityForResult(intent, 1);

                //Toast.makeText(this, "更换背景", Toast.LENGTH_SHORT).show();



                break;


        }


    }
    //设置背景图
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
                    Log.d(TAG, "onActivityResult: 准备更换网络图片");

//                    upbeijing(new File(path));

                }
                Log.i("select", "图片长度为" + select.size());


            }

        }

    }




    //上传背景图片
    public void upbeijing(File file1) {
        String upToken;
        String serverPath = "http://img.rianbow.cn/";


        upToken = SPUtil.getInstance().getData(PersonHomeActivity.this, SPUtil.FILE_NAME, SPUtil.UPTOKEN);

        // 图片上传到七牛 重用 uploadManager。一般地，只需要创建一个 uploadManager 对象
        UploadManager uploadManager = new UploadManager();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        // 设置名字
        String s = MD5Utils.string2Md5_16(file1.getAbsolutePath());
        String key = s + sdf.format(new Date()) + ".jpg";
        uploadManager.put(file1, key, upToken,
                new UpCompletionHandler() {
                    @Override
                    public void complete(String key, ResponseInfo info, JSONObject res) {
                        //res 包含 hash、key 等信息，具体字段取决于上传策略的设置
                        if (info.isOK()) {
                            // 七牛返回的文件名
                            try {
                                String upimg = res.getString("key");
                                //将七牛返回图片的文件名添加到list集合中
                                img1 = serverPath + upimg;
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Log.i("xxx", "Upload Fail");
                            //如果失败，这里可以把 info 信息上报自己的服务器，便于后面分析上传错误原因
                        }
                        Log.i("xxx", img1);
                    }
                }, null);

    }
}

