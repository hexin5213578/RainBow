package com.YiDian.RainBow.login.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseAvtivity;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.base.Common;
import com.YiDian.RainBow.login.bean.ComPleteMsgBean;
import com.YiDian.RainBow.main.activity.MainActivity;
import com.YiDian.RainBow.main.fragment.mine.activity.EditMsgActivity;
import com.YiDian.RainBow.setpwd.activity.SetPwdActivity;
import com.YiDian.RainBow.setup.bean.CheckNickNameBean;
import com.YiDian.RainBow.utils.BasisTimesUtils;
import com.YiDian.RainBow.utils.MD5Utils;
import com.YiDian.RainBow.utils.NetUtils;
import com.YiDian.RainBow.utils.SPUtil;
import com.YiDian.RainBow.utils.StringUtil;
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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.DownloadAvatarCallback;
import cn.jpush.im.android.api.callback.GetAvatarBitmapCallback;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 完善信息页
 * @author hmy
 */
public class CompleteMsgActivity extends BaseAvtivity implements View.OnClickListener {
    @BindView(R.id.tv_jump_main)
    TextView tvJumpMain;
    @BindView(R.id.iv_headimg)
    ImageView ivHeadimg;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.tv_birth)
    TextView tvBirth;
    @BindView(R.id.rb1)
    RadioButton rb1;
    @BindView(R.id.rb2)
    RadioButton rb2;
    @BindView(R.id.rb3)
    RadioButton rb3;
    @BindView(R.id.rb4)
    RadioButton rb4;
    @BindView(R.id.rb5)
    RadioButton rb5;
    @BindView(R.id.rg)
    RadioGroup rg;
    @BindView(R.id.bt_confirm)
    Button btconfirm;
    String str = "";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private String path;
    private UploadManager uploadManager;
    private String token;
    private static final String serverPath = "http://img.rianbow.cn/";
    private String url;
    private boolean isClick = true;
    private boolean isjiance = true;
    private int userid;
    private String time;
    private UserInfo userInfo;
    private String headimg;
    private String name;
    private File file;

    @Override
    protected int getResId() {
        return R.layout.activity_complete_msg;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void getData() {
        //设置状态栏颜色与字体颜色
        StatusBarUtil.setGradientColor(this, toolbar);
        StatusBarUtil.setDarkMode(this);

        Intent intent = getIntent();
        headimg = intent.getStringExtra("headimg");
        name = intent.getStringExtra("name");

        userid = Integer.valueOf(Common.getUserId());

        if (headimg!=null){
            //先加载头像
            Glide.with(CompleteMsgActivity.this).load(headimg).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(ivHeadimg);
        }else {
            Glide.with(CompleteMsgActivity.this).load(Common.getHeadImg()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(ivHeadimg);
        }

        if (name!=null){
            etName.setText(name);
        }else{
            etName.setText(Common.getUserName());
            name = Common.getUserName();
        }

        userInfo = new UserInfo() {
            @Override
            public String getNotename() {
                return null;
            }

            @Override
            public String getNoteText() {
                return null;
            }

            @Override
            public long getBirthday() {
                return 0;
            }

            @Override
            public File getAvatarFile() {
                return null;
            }

            @Override
            public void getAvatarFileAsync(DownloadAvatarCallback downloadAvatarCallback) {

            }

            @Override
            public void getAvatarBitmap(GetAvatarBitmapCallback getAvatarBitmapCallback) {

            }

            @Override
            public File getBigAvatarFile() {
                return null;
            }

            @Override
            public void getBigAvatarBitmap(GetAvatarBitmapCallback getAvatarBitmapCallback) {

            }

            @Override
            public int getBlacklist() {
                return 0;
            }

            @Override
            public int getNoDisturb() {
                return 0;
            }

            @Override
            public boolean isFriend() {
                return false;
            }

            @Override
            public String getAppKey() {
                return null;
            }

            @Override
            public void setUserExtras(Map<String, String> map) {

            }

            @Override
            public void setUserExtras(String s, String s1) {

            }

            @Override
            public void setBirthday(long l) {

            }

            @Override
            public void setNoDisturb(int i, BasicCallback basicCallback) {

            }

            @Override
            public void removeFromFriendList(BasicCallback basicCallback) {

            }

            @Override
            public void updateNoteName(String s, BasicCallback basicCallback) {

            }

            @Override
            public void updateNoteText(String s, BasicCallback basicCallback) {

            }

            @Override
            public String getDisplayName() {
                return null;
            }
        };



        //申请开启内存卡权限
        if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) && (CompleteMsgActivity.this.checkSelfPermission
                (Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
            //请求权限
            CompleteMsgActivity.this.requestPermissions(new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }




        String role = Common.getRole();
        if (role != null && !role.equals("")) {
            if (role.equals("T")) {
                rb1.setChecked(true);
            } else if (role.equals("P")) {
                rb2.setChecked(true);
            } else if (role.equals("H")) {
                rb3.setChecked(true);
            } else if (role.equals("BI")) {
                rb4.setChecked(true);
            } else {
                rb5.setChecked(true);
            }
        } else {
            rb1.setChecked(true);
        }

        String birthday = SPUtil.getInstance().getData(this, SPUtil.FILE_NAME, SPUtil.BIRTHDAY);
        if (birthday != null && !birthday.equals("")) {
            tvBirth.setText(birthday);

            String[] split = birthday.split("-");
            tvBirth.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BasisTimesUtils.showDatePickerDialog(CompleteMsgActivity.this, "请选择年月日", Integer.valueOf(split[0]), Integer.valueOf(split[1]), Integer.valueOf(split[2]), new BasisTimesUtils.OnDatePickerListener() {
                        @Override
                        public void onConfirm(int year, int month, int dayOfMonth) {
                            if (month < 10 && dayOfMonth < 10) {
                                time = year + "-0" + month + "-0" + dayOfMonth;
                            } else if (month < 10) {
                                time = year + "-0" + month + "-" + dayOfMonth;
                            } else if (dayOfMonth < 10) {
                                time = year + "-" + month + "-0" + dayOfMonth;
                            } else {
                                time = year + "-" + month + "-" + dayOfMonth;
                            }

                            tvBirth.setText(time);
                        }

                        @Override
                        public void onCancel() {

                        }
                    });
                }
            });
        } else {
            tvBirth.setText("1998-01-01");
            tvBirth.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BasisTimesUtils.showDatePickerDialog(CompleteMsgActivity.this, "请选择年月日", 1998, 1, 1, new BasisTimesUtils.OnDatePickerListener() {
                        @Override
                        public void onConfirm(int year, int month, int dayOfMonth) {
                            if (month < 10 && dayOfMonth < 10) {
                                time = year + "-0" + month + "-0" + dayOfMonth;
                            } else if (month < 10) {
                                time = year + "-0" + month + "-" + dayOfMonth;
                            } else if (dayOfMonth < 10) {
                                time = year + "-" + month + "-0" + dayOfMonth;
                            } else {
                                time = year + "-" + month + "-" + dayOfMonth;
                            }

                            tvBirth.setText(time);
                        }

                        @Override
                        public void onCancel() {

                        }
                    });
                }
            });
        }
        tvJumpMain.setOnClickListener(this);
        btconfirm.setOnClickListener(this);
        ivHeadimg.setOnClickListener(this);
        token = Common.getToken();
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_headimg:
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
            case R.id.tv_jump_main:
                //修改SP完善资料状态为1
                SPUtil.getInstance().saveData(CompleteMsgActivity.this, SPUtil.FILE_NAME, SPUtil.IS_PERFECT, "1");
                startActivity(new Intent(CompleteMsgActivity.this, MainActivity.class));
                break;
            case R.id.bt_confirm:
                //判断选中的选项
                if (rb1.isChecked()) {
                    str = rb1.getText().toString();
                }
                if (rb2.isChecked()) {
                    str = rb2.getText().toString();
                }
                if (rb3.isChecked()) {
                    str = rb3.getText().toString();
                }
                if (rb4.isChecked()) {
                    str = rb4.getText().toString();
                }
                if (rb5.isChecked()) {
                    str = rb5.getText().toString();
                }
                //修改SP中完善资料状态为0
                String name = etName.getText().toString();
                String birth = tvBirth.getText().toString();

                if (name.equals(this.name)) {
                    //没有改变  修改年龄角色
                    NetUtils.getInstance().getApis()
                            .doComPleteUserAgeAndRole(userid, birth, str)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<ComPleteMsgBean>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(ComPleteMsgBean comPleteMsgBean) {
                                    if (comPleteMsgBean.getMsg().equals("数据修改成功！")) {
                                        Toast.makeText(CompleteMsgActivity.this, "资料已完善", Toast.LENGTH_SHORT).show();
                                        //直接跳转到主页
                                        startActivity(new Intent(CompleteMsgActivity.this, MainActivity.class));

                                        SPUtil.getInstance().saveData(CompleteMsgActivity.this, SPUtil.FILE_NAME, SPUtil.IS_PERFECT, "0");

                                        SPUtil.getInstance().saveData(CompleteMsgActivity.this, SPUtil.FILE_NAME, SPUtil.BIRTHDAY, birth);
                                        SPUtil.getInstance().saveData(CompleteMsgActivity.this, SPUtil.FILE_NAME, SPUtil.ROLE, str);
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
                    // TODO: 2021/1/6 0006 检测名称是否存在
                    NetUtils.getInstance().getApis()
                            .doCheckName(name)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<CheckNickNameBean>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(CheckNickNameBean checkNickNameBean) {
                                    if (checkNickNameBean.getMsg().equals("用户名已存在！")) {

                                        Toast.makeText(CompleteMsgActivity.this, "用户名重复 换一个试试吧", Toast.LENGTH_SHORT).show();

                                    } else if (checkNickNameBean.getMsg().equals("用户名可用")) {
                                        //再调用完善信息接口
                                        NetUtils.getInstance().getApis()
                                                .doComPlteAllMsg(userid, name, birth, str)
                                                .subscribeOn(Schedulers.io())
                                                .observeOn(AndroidSchedulers.mainThread())
                                                .subscribe(new Observer<ComPleteMsgBean>() {
                                                    @Override
                                                    public void onSubscribe(Disposable d) {

                                                    }

                                                    @Override
                                                    public void onNext(ComPleteMsgBean comPleteMsgBean) {
                                                        if (comPleteMsgBean.getMsg().equals("数据修改成功！")) {
                                                            Toast.makeText(CompleteMsgActivity.this, "资料已完善", Toast.LENGTH_SHORT).show();

                                                            SPUtil.getInstance().saveData(CompleteMsgActivity.this, SPUtil.FILE_NAME, SPUtil.IS_PERFECT, "0");

                                                            SPUtil.getInstance().saveData(CompleteMsgActivity.this, SPUtil.FILE_NAME, SPUtil.USER_NAME, name);
                                                            SPUtil.getInstance().saveData(CompleteMsgActivity.this, SPUtil.FILE_NAME, SPUtil.BIRTHDAY, birth);
                                                            SPUtil.getInstance().saveData(CompleteMsgActivity.this, SPUtil.FILE_NAME, SPUtil.ROLE, str);

                                                            //直接跳转到主页
                                                            startActivity(new Intent(CompleteMsgActivity.this, MainActivity.class));

                                                            userInfo.setNickname(name);
                                                            //登陆成功设置极光用户名及头像
                                                            JMessageClient.updateMyInfo(UserInfo.Field.nickname, userInfo, new BasicCallback() {
                                                                @Override
                                                                public void gotResult(int i, String s) {
                                                                    if (i == 0) {
                                                                        Log.d("xxx", "极光昵称设置成功");
                                                                        //极光更换成功调用 上传到服务器
                                                                    } else {
                                                                        Log.d("xxx", "设置失败，原因为" + s);
                                                                    }
                                                                }
                                                            });
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

                                @Override
                                public void onError(Throwable e) {

                                }

                                @Override
                                public void onComplete() {

                                }
                            });
                }
                break;
            default:

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            if (null != data) {
                //图片单选和多选数据都是以ArrayList的字符串数组返回的。
                List<String> paths = PictureSelector.obtainPathResult(data);
                path = paths.get(0);
                Glide.with(CompleteMsgActivity.this).load(path).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(ivHeadimg);

                //上传至七牛云
                // 图片上传到七牛 重用 uploadManager。一般地，只需要创建一个 uploadManager 对象
                uploadManager = new UploadManager();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                // 设置名字
                String s = MD5Utils.string2Md5_16(path);

                file = new File(path);
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

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    Log.i("xxx", "Upload Fail");
                                    Log.i("xxx", info.toString());
                                    //如果失败，这里可以把 info 信息上报自己的服务器，便于后面分析上传错误原因
                                }
                            }
                        }, null);
            }

            JMessageClient.updateUserAvatar(file, new BasicCallback() {
                @Override
                public void gotResult(int i, String s) {
                    if (i == 0) {
                        Log.d("xxx", "当前登录用户头像设置成功");
                    } else {
                        Log.d("xxx", s);
                    }
                }
            });

            //修改头像
            NetUtils.getInstance().getApis()
                    .doComPleteHeadImg(userid, url)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ComPleteMsgBean>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(ComPleteMsgBean comPleteMsgBean) {
                            Toast.makeText(CompleteMsgActivity.this, "头像修改成功", Toast.LENGTH_SHORT).show();


                            SPUtil.getInstance().saveData(CompleteMsgActivity.this, SPUtil.FILE_NAME, SPUtil.HEAD_IMG, url);

                            SPUtil.getInstance().saveData(CompleteMsgActivity.this, SPUtil.FILE_NAME, SPUtil.IS_PERFECT, "0");
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
}
