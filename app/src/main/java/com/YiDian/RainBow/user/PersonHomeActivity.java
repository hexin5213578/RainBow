package com.YiDian.RainBow.user;

import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseAvtivity;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.base.Common;
import com.YiDian.RainBow.friend.activity.FriendsActivity;
import com.YiDian.RainBow.login.bean.ComPleteMsgBean;
import com.YiDian.RainBow.main.fragment.home.adapter.UserDetailsDynamicAdapter;
import com.YiDian.RainBow.main.fragment.home.bean.NewDynamicBean;
import com.YiDian.RainBow.main.fragment.mine.activity.MyGiftActivity;
import com.YiDian.RainBow.setup.bean.InsertRealBean;
import com.YiDian.RainBow.topic.SaveIntentMsgBean;
import com.YiDian.RainBow.user.bean.UserMsgBean;
import com.YiDian.RainBow.utils.MD5Utils;
import com.YiDian.RainBow.utils.NetUtils;
import com.YiDian.RainBow.utils.SPUtil;
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
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

//-----------------------------------------------------


//用户主页  头像点击进入
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
    private String img1;
    private int myId;
    private Intent intent;
    int page  =1;
    List<NewDynamicBean.ObjectBean.ListBean> allList ;
    private Tencent mTencent;
    private int flag;
    private String name;
    private int thePageuserId;

    @Override
    protected int getResId() {
        return R.layout.activity_personhome;
    }

    @Override
    protected void getData() {
        //腾讯AppId(替换你自己App Id)、上下文
        mTencent = Tencent.createInstance("101906973", PersonHomeActivity.this);

        llBack.setOnClickListener(this);
        llFensi.setOnClickListener(this);
        llLiwu.setOnClickListener(this);
        llGuanzhu.setOnClickListener(this);
        llCandian.setOnClickListener(this);
        IvBeijing.setOnClickListener(this);


        allList = new ArrayList<>();
        //设置背景透明
        StatusBarUtil.setTransparentForWindow(this);
        //设置状态栏字体黑色
        StatusBarUtil.setLightMode(this);

        Intent intent =
                getIntent();
        SaveIntentMsgBean msg = (SaveIntentMsgBean) intent.getSerializableExtra("msg");
        flag = msg.getFlag();
        //接收到的id
        thePageuserId = msg.getId();
        //接收到的name
        name = msg.getMsg();

        sv.setHeader(new AliHeader(this));

        myId = Integer.parseInt(Common.getUserId());
        String myName = Common.getUserName();

        //2标记传入姓名  1标记传入id
        if (flag == 2) {
            //获取用户信息
            Log.d("xxx", "传过来的姓名为" + name);
            bandpageinfo(myId, name);
            dogetDynamicByName(page, name);

            if (!name.equals(myName)) {
                Log.d("xxx", "getData: 不是自己的主页");
                isotherpage(false);
                //增加访问量
                doInsert(name);

            } else {
                isotherpage(true);
                Log.d("xxx", "getData: 自己的主页");
            }

            sv.setListener(new SpringView.OnFreshListener() {
                @Override
                public void onRefresh() {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            bandpageinfo(myId, name);
                            allList.clear();
                            page = 1;
                            dogetDynamicByName(page, name);
                            //等待2.5秒后结束刷新
                            sv.onFinishFreshAndLoad();
                        }
                    },1000);
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
                    },1000);
                }
            });
        } else {
            //获取用户信息
            Log.d("xxx", "传过来的id为" + thePageuserId);
            bandpageinfo(myId, thePageuserId);

            dogetDynamicById(page, thePageuserId);

            if (!(thePageuserId == myId)) {
                Log.d("xxx", "getData: 不是自己的主页");
                isotherpage(false);//设置部分控件不能点击
                //增加访问量
                doInsert(String.valueOf(thePageuserId));


            } else {
                isotherpage(true);
                Log.d("xxx", "getData: 自己的主页");
            }
            //实现列表滚动加载，刷新效果
            sv.setListener(new SpringView.OnFreshListener() {
                @Override
                public void onRefresh() {
                        new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            bandpageinfo(myId, thePageuserId);
                            allList.clear();
                            page =1;
                            dogetDynamicById(page, thePageuserId);
                            sv.onFinishFreshAndLoad();
                        }
                    },1000);
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
                    },1000);
                }
            });
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
    }

    public void doInsert(String str){
        NetUtils.getInstance().getApis()
                .doInsertFangke(str,myId)
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
    //true 是别人的主页   false  是自己的主页
    public void isotherpage(boolean ischeck) {
        llFensi.setEnabled(ischeck);
        llGuanzhu.setEnabled(ischeck);
        llLiwu.setEnabled(ischeck);
        IvBeijing.setEnabled(ischeck);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getStr(String str){
        allList.clear();
        if (str.equals("刷新界面")){
            if (flag==2){
                dogetDynamicByName(page, name);
            }else{
                dogetDynamicById(page, thePageuserId);
            }
        }
    }
    //动态信息填充到列表里面
    public void dogetDynamicById(int page, int thePageuserId) {
        showDialog();//显示加载圈
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
                        hideDialog();//隐藏加载圈
                        List<NewDynamicBean.ObjectBean.ListBean> list = newDynamicBean.getObject().getList();

                        if (list.size()>0 && list!=null){
                            //RelativeLayout rlNodata;
                            rlNodata.setVisibility(View.GONE);
                            //RecyclerView  rcDynamic
                            rcDynamic.setVisibility(View.VISIBLE);

                            allList.addAll(list);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(PersonHomeActivity.this, RecyclerView.VERTICAL, false);
                            rcDynamic.setLayoutManager(linearLayoutManager);

                            UserDetailsDynamicAdapter userDetailsDynamicAdapter = new UserDetailsDynamicAdapter(PersonHomeActivity.this, allList,mTencent);
                            rcDynamic.setAdapter(userDetailsDynamicAdapter);
                        }else{
                            if (allList.size()>0){
                                Toast.makeText(PersonHomeActivity.this, "没有更多内容了", Toast.LENGTH_SHORT).show();
                            }else{
                                rlNodata.setVisibility(View.VISIBLE);
                                rcDynamic.setVisibility(View.GONE);
                            }
                        }
                        if (list.size()>4){
                            //设置底部
                            sv.setFooter(new AliFooter(PersonHomeActivity.this));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        hideDialog();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    public void dogetDynamicByName(int page, String name) {
        showDialog();
        //展示话题
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
                        hideDialog();
                        List<NewDynamicBean.ObjectBean.ListBean> list = newDynamicBean.getObject().getList();

                        if (list.size()>0 && list!=null){
                            rlNodata.setVisibility(View.GONE);
                            rcDynamic.setVisibility(View.VISIBLE);

                            allList.addAll(list);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(PersonHomeActivity.this, RecyclerView.VERTICAL, false);
                            rcDynamic.setLayoutManager(linearLayoutManager);

                            UserDetailsDynamicAdapter userDetailsDynamicAdapter = new UserDetailsDynamicAdapter(PersonHomeActivity.this, allList,mTencent);
                            rcDynamic.setAdapter(userDetailsDynamicAdapter);
                        }else{
                            if (allList.size()>0){
                                Toast.makeText(PersonHomeActivity.this, "没有更多内容了", Toast.LENGTH_SHORT).show();
                            }else{
                                rlNodata.setVisibility(View.VISIBLE);
                                rcDynamic.setVisibility(View.GONE);
                            }
                        }
                        if (list.size()>4){
                            sv.setFooter(new AliFooter(PersonHomeActivity.this));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        hideDialog();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
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

                            String explains = userInfo.getExplains();
                            if (explains==null){
                                String gxQianMing = "";//个性签名
                                tvGxqianming.setText(gxQianMing);
                            }else{
                                String gxQianMing = "个性签名：" + explains;//个性签名
                                tvGxqianming.setText(gxQianMing);
                            }

                            if (userRole == null) {
                                userRoleAge = friendAge + "";
                            } else if (userRole.equals("保密")) {
                                userRoleAge = friendAge + "";
                            } else {
                                userRoleAge = userRole + " " + friendAge;
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
                            Integer friendAge = userInfo.getAge();

                            String userRole = userInfo.getUserRole();// 用户真实名
                            String userRoleAge = null;     //用户真实名   + 年龄
                            String friendname = userInfo.getNickName();//昵称
                            String headImg = userInfo.getHeadImg();//头像

                            String explains = userInfo.getExplains();
                            if (explains.equals("null")){
                                tvGxqianming.setText("");
                            }else{
                                String gxQianMing = "个性签名：" + explains;//个性签名
                                tvGxqianming.setText(gxQianMing);
                            }


                            if (userRole == null) {
                                if (friendAge==null){
                                    userRoleAge = 0 + "";
                                }else{
                                    userRoleAge = friendAge + "";
                                }
                            } else if (userRole.equals("保密")) {
                                if (friendAge==null){
                                    userRoleAge = 0 + "";
                                }else{
                                    userRoleAge = friendAge + "";
                                }
                            } else {
                                if (friendAge==null){
                                    userRoleAge = userRole + " " + 0;
                                }else{
                                    userRoleAge = userRole + " " + friendAge;
                                }
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
        if (EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //粉丝被点击
            case R.id.ll_fensi:
                intent = new Intent(PersonHomeActivity.this, FriendsActivity.class);
                intent.putExtra("flag",2);
                startActivity(intent);
                break;
                //关注被点击
            case R.id.ll_guanzhu:
                intent = new Intent(PersonHomeActivity.this, FriendsActivity.class);
                intent.putExtra("flag",4);
                startActivity(intent);
                break;
                //礼物被点击
            case R.id.ll_liwu:

                intent = new Intent(PersonHomeActivity.this, MyGiftActivity.class);
                startActivity(intent);
                break;
                //返回
            case R.id.ll_back:
                finish();
                break;
                //菜单
            case R.id.ll_candian:

                break;
                //点击背景  是自己的主页可以进行更换
            case R.id.Iv_beijing:
                Log.d(TAG, "onClick: ------->" + "点击背景");
                intent = new Intent(PersonHomeActivity.this, PickerActivity.class);
                intent.putExtra(PickerConfig.SELECT_MODE, PickerConfig.PICKER_IMAGE);//设置选择类型，默认是图片和视频可一起选择(非必填参数)
                long maxSize = 10485760L;//long long long long类型
                intent.putExtra(PickerConfig.MAX_SELECT_SIZE, maxSize);
                intent.putExtra(PickerConfig.MAX_SELECT_COUNT, 1); //最大选择数量，默认40（非必填参数）
                intent.putExtra("type", 1);
                startActivityForResult(intent, 1);
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

                    upbeijing(path);
                }
                Log.i("select", "图片长度为" + select.size());
            }
        }
    }

    //上传背景图片
    public void upbeijing(String file1) {
        String upToken;
        String serverPath = "http://img.rianbow.cn/";

        upToken = SPUtil.getInstance().getData(PersonHomeActivity.this, SPUtil.FILE_NAME, SPUtil.UPTOKEN);

        // 图片上传到七牛 重用 uploadManager。一般地，只需要创建一个 uploadManager 对象
        UploadManager uploadManager = new UploadManager();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        // 设置名字
        String s = MD5Utils.string2Md5_16(file1);
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

                                //转化成功 更新本地服务器用户信息
                                NetUtils.getInstance().getApis()
                                        .doComPleteBackImg(myId,img1)
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new Observer<ComPleteMsgBean>() {
                                            @Override
                                            public void onSubscribe(Disposable d) {

                                            }

                                            @Override
                                            public void onNext(ComPleteMsgBean comPleteMsgBean) {
                                                if (comPleteMsgBean.getMsg().equals("数据修改成功！")){
                                                    Toast.makeText(PersonHomeActivity.this, "背景修改成功", Toast.LENGTH_SHORT).show();
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
                            //如果失败，这里可以把 info 信息上报自己的服务器，便于后面分析上传错误原因
                        }
                        Log.i("xxx", img1);
                    }
                }, null);
    }
}

