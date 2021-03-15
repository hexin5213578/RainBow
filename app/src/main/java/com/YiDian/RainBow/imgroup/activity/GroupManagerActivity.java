package com.YiDian.RainBow.imgroup.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseAvtivity;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.base.Common;
import com.YiDian.RainBow.custom.loading.CustomDialog;
import com.YiDian.RainBow.imgroup.bean.ChangeGroupHeadBean;
import com.YiDian.RainBow.imgroup.bean.SaveIdAndHeadImgBean;
import com.YiDian.RainBow.login.bean.ComPleteMsgBean;
import com.YiDian.RainBow.main.fragment.mine.activity.EditMsgActivity;
import com.YiDian.RainBow.utils.MD5Utils;
import com.YiDian.RainBow.utils.NetUtils;
import com.YiDian.RainBow.utils.SPUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.leaf.library.StatusBarUtil;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

import org.json.JSONException;
import org.json.JSONObject;
import org.lym.image.select.PictureSelector;

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetGroupInfoCallback;
import cn.jpush.im.android.api.model.GroupInfo;
import cn.jpush.im.api.BasicCallback;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class GroupManagerActivity extends BaseAvtivity implements View.OnClickListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_back)
    LinearLayout ivBack;
    @BindView(R.id.rl_top)
    RelativeLayout rlTop;
    @BindView(R.id.iv_headimg)
    ImageView ivHeadimg;
    @BindView(R.id.iv)
    ImageView iv;
    @BindView(R.id.rl_change_headimg)
    RelativeLayout rlChangeHeadimg;
    @BindView(R.id.rl_shenhelist)
    RelativeLayout rlShenhelist;
    @BindView(R.id.rl_userlist)
    RelativeLayout rlUserlist;
    private String path;
    private UploadManager uploadManager;
    private String token;
    private String url;
    private static final String serverPath = "http://img.rianbow.cn/";
    private String headimg;
    private int id;
    private int jgId;
    private CustomDialog customDialog;
    private Intent intent;

    @Override
    protected int getResId() {
        return R.layout.activity_group_manager;
    }

    @Override
    protected void getData() {
        StatusBarUtil.setGradientColor(GroupManagerActivity.this, toolbar);
        StatusBarUtil.setDarkMode(GroupManagerActivity.this);
        //获取七牛云token
        token = Common.getToken();

        customDialog = new CustomDialog(GroupManagerActivity.this, "正在更换头像...");
        /* 获取需要管理的群ID */
        Intent intent = getIntent();

        SaveIdAndHeadImgBean msg = (SaveIdAndHeadImgBean) intent.getSerializableExtra("msg");

        id = msg.getId();
        headimg = msg.getHeadimg();
        jgId = msg.getJgId();
        //先加载传过来的头像
        //加载圆角头像
        Glide.with(GroupManagerActivity.this).load(headimg).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(ivHeadimg);

        Log.d("xxx", "群ID为" + id +"   头像为"+ headimg);

        ivBack.setOnClickListener(this);
        rlChangeHeadimg.setOnClickListener(this);
        rlShenhelist.setOnClickListener(this);
        rlUserlist.setOnClickListener(this);
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.rl_change_headimg:
                if(Build.VERSION.SDK_INT==30){
                    Toast.makeText(this, "Android11暂不支持更换头像", Toast.LENGTH_SHORT).show();
                }else{
                    PictureSelector
                            .with(this)
                            .selectSpec()
                            .setOpenCamera()
                            .needCrop()
                            .setOutputX(200)
                            .setOutputY(200)
                            //开启拍照功能一定得设置该属性，为了兼容Android7.0相机拍照问题
                            //在manifest文件中也需要注册该provider
                            .setAuthority("com.YiDian.RainBow.utils.MyFileProvider")
                            .startForResult(100);
                }
                break;
                //审核列表
            case R.id.rl_shenhelist:
                intent = new Intent(GroupManagerActivity.this,ReviewListActivity.class);
                intent.putExtra("id",id);
                intent.putExtra("jgid",jgId);
                startActivity(intent);
                break;
                //成员管理
            case R.id.rl_userlist:
                intent = new Intent(GroupManagerActivity.this,MemberManageActivity.class);
                intent.putExtra("id",id);
                intent.putExtra("jgid",jgId);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                //图片单选和多选数据都是以ArrayList的字符串数组返回的。
                List<String> paths = PictureSelector.obtainPathResult(data);
                path = paths.get(0);
                customDialog.show();
                //上传至七牛云
                // 图片上传到七牛 重用 uploadManager。一般地，只需要创建一个 uploadManager 对象
                uploadManager = new UploadManager();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                // 设置名字
                String s = MD5Utils.string2Md5_16(path);

                File file = new File(path);
                if(!file.exists())
                {
                    Toast.makeText(this, "文件不存在", Toast.LENGTH_SHORT).show();
                }

                String key = s + sdf.format(new Date()) + ".jpg";
                uploadManager.put(file, key, token,
                        new UpCompletionHandler() {
                            @Override
                            public void complete(String key, ResponseInfo info, JSONObject res) {
                                //res 包含 hash、key 等信息，具体字段取决于上传策略的设置
                                if (info.isOK()) {
                                    // 七牛返回的文件名
                                    try {
                                        String upimg = res.getString("key");
                                        //将七牛返回图片的文件名添加到list集合中
                                        url = serverPath + upimg;

                                        //先发起更换极光群组的接口
                                        File file = new File(path);
                                        Log.d("xxx",file.getAbsolutePath());
                                        JMessageClient.getGroupInfo(jgId, new GetGroupInfoCallback() {
                                            @Override
                                            public void gotResult(int i, String s, GroupInfo groupInfo) {
                                                Log.d("xxx","请求码为"+i+"原因为"+s);
                                                if (i==0){
                                                    //获取成功群组信息
                                                    groupInfo.updateAvatar(file, "jpg", new BasicCallback() {
                                                        @Override
                                                        public void gotResult(int i, String s) {
                                                            if (i==0){
                                                                Log.d("xxx","极光群组头像更换成功");
                                                                //调用修改群组头像的借口 保持一致
                                                                NetUtils.getInstance().getApis()
                                                                        .doChangeGroupHeadImg(id,url)
                                                                        .subscribeOn(Schedulers.io())
                                                                        .observeOn(AndroidSchedulers.mainThread())
                                                                        .subscribe(new Observer<ChangeGroupHeadBean>() {
                                                                            @Override
                                                                            public void onSubscribe(@NonNull Disposable d) {

                                                                            }

                                                                            @Override
                                                                            public void onNext(@NonNull ChangeGroupHeadBean changeGroupHeadBean) {
                                                                                customDialog.dismiss();
                                                                                if (changeGroupHeadBean.getMsg().equals("修改成功")){
                                                                                    Toast.makeText(GroupManagerActivity.this, "头像修改成功", Toast.LENGTH_SHORT).show();
                                                                                    Glide.with(GroupManagerActivity.this).load(path).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(ivHeadimg);
                                                                                }
                                                                            }

                                                                            @Override
                                                                            public void onError(@NonNull Throwable e) {
                                                                                customDialog.dismiss();
                                                                                Toast.makeText(GroupManagerActivity.this, "头像修改失败", Toast.LENGTH_SHORT).show();
                                                                            }

                                                                            @Override
                                                                            public void onComplete() {

                                                                            }
                                                                        });
                                                            }
                                                        }
                                                    });
                                                }
                                            }
                                        });

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    Log.i("xxx", "Upload Fail");
                                    Log.i("xxx", info.toString());
                                    //如果失败，这里可以把 info 信息上报自己的服务器，便于后面分析上传错误原因
                                }
                                Log.i("xxx", url);
                            }
                        }, null);
            }
        }
    }
}
