package com.YiDian.RainBow.dynamic.activity;

import android.Manifest;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
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
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseAvtivity;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.custom.customDialog.CustomDialog;
import com.YiDian.RainBow.dynamic.adapter.DevelogmentImgAdapter;
import com.YiDian.RainBow.dynamic.adapter.HotHuatiAdapter;
import com.YiDian.RainBow.dynamic.bean.SaveAiteBean;
import com.YiDian.RainBow.dynamic.bean.SaveHotHuatiBean;
import com.YiDian.RainBow.dynamic.bean.SaveMsgSuccessBean;
import com.YiDian.RainBow.dynamic.bean.SaveWhoCanseeBean;
import com.YiDian.RainBow.dynamic.bean.WriteDevelopmentBean;
import com.YiDian.RainBow.feedback.activity.PathUtil;
import com.YiDian.RainBow.main.fragment.activity.SimplePlayerActivity;
import com.YiDian.RainBow.remember.bean.RememberPwdBean;
import com.YiDian.RainBow.utils.Base64;
import com.YiDian.RainBow.utils.KeyBoardUtils;
import com.YiDian.RainBow.utils.MD5Utils;
import com.YiDian.RainBow.utils.NetUtils;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.dmcbig.mediapicker.PickerActivity;
import com.dmcbig.mediapicker.PickerConfig;
import com.dmcbig.mediapicker.entity.Media;
import com.hw.videoprocessor.VideoProcessor;
import com.hw.videoprocessor.VideoUtil;
import com.hw.videoprocessor.util.CL;
import com.jaygoo.widget.RangeSeekBar;
import com.leaf.library.StatusBarUtil;
import com.qiniu.android.common.Config;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UpProgressHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Mac;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

//发布动态
public class DevelopmentDynamicActivity extends BaseAvtivity implements View.OnClickListener, AMapLocationListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_back)
    LinearLayout ivBack;
    @BindView(R.id.tv_release)
    TextView tvRelease;
    @BindView(R.id.rl_top)
    RelativeLayout rlTop;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.tv_count)
    TextView tvCount;
    @BindView(R.id.rl_content)
    RelativeLayout rlContent;
    @BindView(R.id.rc_pro_img)
    RecyclerView rcProImg;

    @BindView(R.id.rl_selectedimg)
    RelativeLayout rlSelectedimg;
    @BindView(R.id.rc_hotHuati)
    RecyclerView rcHotHuati;
    @BindView(R.id.tv_mylocation)
    TextView tvMylocation;
    @BindView(R.id.ll_my_location)
    RelativeLayout llMyLocation;
    @BindView(R.id.rl_aiteFriend)
    RelativeLayout rlAiteFriend;
    @BindView(R.id.rl_developHuati)
    RelativeLayout rlDevelopHuati;
    @BindView(R.id.ll_whocansee)
    LinearLayout llWhocansee;
    @BindView(R.id.tv_whocansee)
    TextView tvWhocansee;
    @BindView(R.id.rangeSeekBar)
    RangeSeekBar rangeSeekBar;
    @BindView(R.id.videoView)
    VideoView videoView;
    @BindView(R.id.iv_delete_vv)
    ImageView ivDeleteVv;
    @BindView(R.id.rl_vv)
    RelativeLayout rlVv;
    private PopupWindow mPopupWindow1;
    private ArrayList<Media> select;
    private ArrayList<Media> select1;
    //声明mlocationClient对象
    public AMapLocationClient mlocationClient;
    //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption = null;
    boolean isSelect = false;
    //文本的长度
    private int length;
    private DevelogmentImgAdapter develogmentImgAdapter;
    private String filePath;
    private Uri selectedVideoUri;
    private ProgressDialog progressDialog;
    //压缩后的视频路径
    private String strUri;

    private String substring;
    private double latitude;
    private double longitude;
    private String district;
    private ArrayList<String> list;
    int whocansee = 1;
    int userid = 1031;
    private UploadManager uploadManager;
    private String upToken;
    private ArrayList<String> upimg_key_list;
    private Handler handler;
    private static final String serverPath = "http://img.rianbow.cn/";
    @Override
    protected int getResId() {
        return R.layout.activity_development_dynamic;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void getData() {
        handler = new Handler();

        //设置状态栏颜色 跟状态栏字体颜色
        StatusBarUtil.setGradientColor(DevelopmentDynamicActivity.this, toolbar);
        StatusBarUtil.setDarkMode(DevelopmentDynamicActivity.this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(null);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("正在压缩视频，请耐心等待..");

        CL.setLogEnable(true);

        //申请开启内存卡权限
        if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) && (DevelopmentDynamicActivity.this.checkSelfPermission
                (Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
            //请求权限
            DevelopmentDynamicActivity.this.requestPermissions(new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        if (Build.VERSION.SDK_INT >= 23) {
            int request = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
            if (request != PackageManager.PERMISSION_GRANTED)//缺少权限，进行权限申请
            {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);
                return;//
            } else {

            }
        } else {

        }
        select = new ArrayList<>();
        select1 = new ArrayList<>();

        // TODO: 2020/11/23 0023 定位获取当前区县
        doLocation();

        //绑定单击事件
        tvRelease.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        rlSelectedimg.setOnClickListener(this);
        llMyLocation.setOnClickListener(this);
        rlAiteFriend.setOnClickListener(this);
        rlDevelopHuati.setOnClickListener(this);
        llWhocansee.setOnClickListener(this);
        ivDeleteVv.setOnClickListener(this);
        //初次进入内容为空 不能发布动态
        tvRelease.setBackground(this.getDrawable(R.drawable.nine_radious_gray));
        tvRelease.setClickable(false);
        //文本监听
        etContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //输入完后获取当前文本长度 给右下角文本长度赋值
                length = etContent.getText().length();
                //判断输入字符串长度 大于0有内容可以发布 等于0不能发布
                if (length > 0) {
                    tvRelease.setBackground(DevelopmentDynamicActivity.this.getDrawable(R.drawable.background_gradient_nine_radious));
                    tvRelease.setClickable(true);
                } else {

                    tvRelease.setBackground(DevelopmentDynamicActivity.this.getDrawable(R.drawable.nine_radious_gray));
                    tvRelease.setClickable(false);

                    if (select.size() == 0 && select1.size() == 0) {
                        tvRelease.setBackground(DevelopmentDynamicActivity.this.getDrawable(R.drawable.nine_radious_gray));
                        tvRelease.setClickable(false);
                    } else {
                        tvRelease.setBackground(DevelopmentDynamicActivity.this.getDrawable(R.drawable.background_gradient_nine_radious));
                        tvRelease.setClickable(true);
                    }
                }
                //输入完毕后获取剩余可输入长度 并展示
                tvCount.setText(100 - length + "");
            }
        });
        List<String> hotHuati = new ArrayList<>();
        hotHuati.add("热门话题1");
        hotHuati.add("热门话题2");
        hotHuati.add("热门话题3");
        hotHuati.add("热门话题4");
        //热门话题
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        rcHotHuati.setLayoutManager(gridLayoutManager);

        //创建适配器
        HotHuatiAdapter hotHuatiAdapter = new HotHuatiAdapter(this, hotHuati);
        rcHotHuati.setAdapter(hotHuatiAdapter);


        videoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DevelopmentDynamicActivity.this, SimplePlayerActivity.class);
                intent.putExtra("url", strUri);
                startActivity(intent);
            }
        });

        //获取七牛云uploadToken
        NetUtils.getInstance().getApis().getUpdateToken()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SaveMsgSuccessBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(SaveMsgSuccessBean saveMsgSuccessBean) {
                        upToken = saveMsgSuccessBean.getUpToken();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    //获取热门话题
    @SuppressLint("ResourceAsColor")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getHotHuati(SaveHotHuatiBean saveHotHuatiBean) {
        String str = saveHotHuatiBean.getStr();

        //获取当前光标位置
        int index = etContent.getSelectionStart();

        //插入到光标所在位置
        Editable editable = etContent.getEditableText();
        editable.insert(index, str);
        //插入到光标所在位置
        int end = index + str.length();//获取文本长度
        editable.setSpan(new ForegroundColorSpan(R.color.start), index, end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);//关键字变色


        String s = etContent.getText().toString();
        //设置光标到最后
        etContent.setSelection(s.length());

        tvRelease.setBackground(DevelopmentDynamicActivity.this.getDrawable(R.drawable.background_gradient_nine_radious));
        tvRelease.setClickable(true);
    }

    //获取选择的好友
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getAiteFriend(SaveAiteBean bean) {
        substring = bean.getString();
        String[] split = substring.split(",");
        //获取当前光标所在位置
        int index = etContent.getSelectionStart();
        Editable editable = etContent.getEditableText();

        if(split.length>0 && split!=null){
            for (int i =0;i<split.length;i++){
                //插入到光标所在位置
                editable.insert(index, "@"+split[i]+" ");
            }
        }
        int end = index + substring.length()+split.length;//获取文本长度

        editable.setSpan(new ForegroundColorSpan(Color.BLUE), index, end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);//关键字变色

        String s = etContent.getText().toString();

        //设置光标到最后
        etContent.setSelection(s.length());

        tvRelease.setBackground(DevelopmentDynamicActivity.this.getDrawable(R.drawable.background_gradient_nine_radious));
        tvRelease.setClickable(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        dismiss();
        videoView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        videoView.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        if(mlocationClient!=null){
            //停止定位
            mlocationClient.stopLocation();
        }
        if (videoView != null) {
            if (videoView.isPlaying()) {
                videoView.stopPlayback();
                videoView.suspend();
            }
        }


    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    public void doOkHttp(int userid,String content,String img,Double log,Double lat,int whocansee,int type,int status,String area){
        NetUtils.getInstance().getApis().doWriteDevelopment(userid, content, img, log, lat, whocansee, type, status, area)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WriteDevelopmentBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(WriteDevelopmentBean writeDevelopmentBean) {
                        hideDialog();
                        Toast.makeText(DevelopmentDynamicActivity.this, "动态发表成功", Toast.LENGTH_SHORT).show();
                        //发表成功退出界面
                        finish();
                        EventBus.getDefault().post("刷新界面");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void doOkHttpCaogao(int userid,String content,String img,Double log,Double lat,int whocansee,int type,int status,String area){
        NetUtils.getInstance().getApis().doWriteDevelopment(userid, content, img, log, lat, whocansee, type, status, area)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WriteDevelopmentBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(WriteDevelopmentBean writeDevelopmentBean) {
                        hideDialog();
                        Toast.makeText(DevelopmentDynamicActivity.this, "草稿发布成功", Toast.LENGTH_SHORT).show();
                        //发表成功退出界面
                        finish();
                        EventBus.getDefault().post("刷新界面");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    public void getUpimg(String imagePath,int picSize,Handler mHandler){

        Log.d("xxx","到这里了");
        upimg_key_list = new ArrayList<String>();
        new Thread() {
            public void run() {
                // 图片上传到七牛 重用 uploadManager。一般地，只需要创建一个 uploadManager 对象
                uploadManager = new UploadManager();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                // 设置名字
                String s = MD5Utils.string2Md5_16(imagePath);
                String key = s+sdf.format(new Date())+".jpg";
                uploadManager.put(imagePath, key, upToken,
                        new UpCompletionHandler() {
                            @Override
                            public void complete(String key, ResponseInfo info,
                                                 JSONObject res) {
                                // res 包含hash、key等信息，具体字段取决于上传策略的设置。
                                Log.i("xxx", key + ",\r\n " + info + ",\r\n "
                                        + res);
                                try {
                                    // 七牛返回的文件名
                                    String upimg = res.getString("key");
                                    upimg_key_list.add(serverPath+upimg);//将七牛返回图片的文件名添加到list集合中

                                    if(upimg_key_list.size() == picSize){
                                        Bundle bundle = new Bundle();
                                        bundle.putStringArrayList("resultImagePath",upimg_key_list);
                                        Message message = new Message();
                                        message.what = 1;
                                        message.setData(bundle);
                                        handler.sendMessage(message);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Log.d("xxx",e.getMessage());
                                }
                            }
                        }, null);
            }
        }.start();
    }


    public void getUpVideo(String imagePath,int picSize,Handler mHandler){

        Log.d("xxx","到这里了");
        upimg_key_list = new ArrayList<String>();
        new Thread() {
            public void run() {
                // 图片上传到七牛 重用 uploadManager。一般地，只需要创建一个 uploadManager 对象
                uploadManager = new UploadManager();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                // 设置名字
                String s = MD5Utils.string2Md5_16(imagePath);
                String key = s+sdf.format(new Date())+".mp4";
                uploadManager.put(imagePath, key, upToken,
                        new UpCompletionHandler() {
                            @Override
                            public void complete(String key, ResponseInfo info,
                                                 JSONObject res) {
                                // res 包含hash、key等信息，具体字段取决于上传策略的设置。
                                Log.i("xxx", key + ",\r\n " + info + ",\r\n "
                                        + res);
                                try {
                                    // 七牛返回的文件名
                                    String upimg = res.getString("key");
                                    upimg_key_list.add(serverPath+upimg);//将七牛返回图片的文件名添加到list集合中

                                    if(upimg_key_list.size() == picSize){
                                        Bundle bundle = new Bundle();
                                        bundle.putStringArrayList("resultImagePath",upimg_key_list);
                                        Message message = new Message();
                                        message.what = 1;
                                        message.setData(bundle);
                                        handler.sendMessage(message);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Log.d("xxx",e.getMessage());
                                }
                            }
                        }, null);
            }
        }.start();
    }
    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //返回
            case R.id.iv_back:
                // TODO: 2020/11/21 0021 判断是否有内容可以保存到草稿箱 有内容保存 无内容直接退出\
                if(etContent.getText().toString().length()>0 || select.size()>0 || select1.size()>0){
                    CustomDialog.Builder builder = new CustomDialog.Builder(this);
                    builder.setPositiveButton("保存", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            showDialog();
                            // TODO: 2020/11/21 0021 获取内容 判断发布类型  1纯文本 2纯图片 3纯视频  21文本加图片 31文本加视频
                            switch (tvWhocansee.getText().toString()) {
                                case "所有人可见":
                                    whocansee = 1;
                                    break;
                                case "仅关注可见":
                                    whocansee = 4;
                                    break;
                                case "仅相互关注可见":
                                    whocansee = 3;
                                    break;
                                case "仅自己可见":
                                    whocansee = 0;
                                    break;
                            }
                            String content = etContent.getText().toString();
                            if (isSelect) {
                                //传位置
                                //判断内容
                                if (content.length() > 0 && select.size()==0 && select1.size()==0) {
                                    //仅文本
                                    //类型为 1
                                    doOkHttpCaogao(userid,content,"", longitude, latitude, whocansee, 1, 0, district);
                                }
                                if (content.length() > 0 && select.size() > 0) {
                                    //文本加图片
                                    //类型为 21
                                    //先将图片传到七牛云生成路径 再调用发布动态的接口

                                    for (int i = 0; i < select.size()-1; i++) {
                                        String url = select.get(i).path;
                                        getUpimg(url,select.size()-1,handler);
                                    }
                                    handler = new Handler() {
                                        @Override
                                        public void handleMessage(Message msg) {
                                            super.handleMessage(msg);
                                            switch (msg.what) {
                                                case 1:
                                                    Bundle bundle = msg.getData();
                                                    if(null != bundle){
                                                        list = bundle.getStringArrayList("resultImagePath");
                                                        Log.d("xxx",list.size()+"======"+list.toString());

                                                        String s = list.toString().substring(1, list.toString().length() - 1);


                                                        doOkHttpCaogao(userid, content, s, longitude, latitude, whocansee, 21, 0, district);
                                                    }
                                                    break;
                                                default:
                                                    break;
                                            }
                                        }
                                    };


                                }
                                if (content.length() > 0 && select1.size() > 0) {
                                    //文本加视频
                                    //类型为31

                                    getUpVideo(filePath,select1.size(),handler);

                                    handler = new Handler() {
                                        @Override
                                        public void handleMessage(Message msg) {
                                            super.handleMessage(msg);
                                            switch (msg.what) {
                                                case 1:
                                                    Bundle bundle = msg.getData();
                                                    if(null != bundle){
                                                        list = bundle.getStringArrayList("resultImagePath");
                                                        Log.d("xxx",list.size()+"======"+list.toString());

                                                        String s = list.toString().substring(1, list.toString().length() - 1);


                                                        //调用发布动态接口
                                                        doOkHttpCaogao(userid, content, s, longitude, latitude, whocansee, 31, 0, district);
                                                    }
                                                    break;
                                                default:
                                                    break;
                                            }
                                        }
                                    };
                                }
                                if (content.length()==0 && select.size() > 0) {
                                    //纯图片
                                    //类型为2
                                    //先将图片传到七牛云生成路径 再调用发布动态的接口
                                    //将图片转换

                                    for (int i = 0; i < select.size()-1; i++) {
                                        String url = select.get(i).path;
                                        getUpimg(url,select.size()-1,handler);
                                    }
                                    handler = new Handler() {
                                        @Override
                                        public void handleMessage(Message msg) {
                                            super.handleMessage(msg);
                                            switch (msg.what) {
                                                case 1:
                                                    Bundle bundle = msg.getData();
                                                    if(null != bundle){
                                                        list = bundle.getStringArrayList("resultImagePath");
                                                        Log.d("xxx",list.size()+"======"+list.toString());

                                                        String s = list.toString().substring(1, list.toString().length() - 1);

                                                        //调用发布动态接口
                                                        doOkHttpCaogao(userid, "", s, longitude, latitude, whocansee, 2, 0, district);
                                                    }
                                                    break;
                                                default:
                                                    break;
                                            }
                                        }
                                    };



                                }
                                if (content.length()==0 && select1.size() > 0) {
                                    //纯视频
                                    //类型为3
                                    getUpVideo(filePath,select1.size(),handler);

                                    handler = new Handler() {
                                        @Override
                                        public void handleMessage(Message msg) {
                                            super.handleMessage(msg);
                                            switch (msg.what) {
                                                case 1:
                                                    Bundle bundle = msg.getData();
                                                    if(null != bundle){
                                                        list = bundle.getStringArrayList("resultImagePath");
                                                        Log.d("xxx",list.size()+"======"+list.toString());

                                                        String s = list.toString().substring(1, list.toString().length() - 1);


                                                        //调用发布动态接口
                                                        doOkHttpCaogao(userid, "", s, longitude, latitude, whocansee, 3, 0, district);
                                                    }
                                                    break;
                                                default:
                                                    break;
                                            }
                                        }
                                    };


                                }

                            } else {
                                //不传位置
                                //判断内容
                                if (content.length() > 0 && select.size()==0 && select1.size()==0) {
                                    //仅文本
                                    //类型为 1
                                    doOkHttpCaogao(userid, content, "", null, null, whocansee, 1, 0, "");
                                }
                                if (content.length() > 0 && select.size() > 0) {
                                    //文本加图片
                                    //类型为 21
                                    for (int i = 0; i < select.size()-1; i++) {
                                        String url = select.get(i).path;
                                        getUpimg(url,select.size()-1,handler);
                                    }
                                    handler = new Handler() {
                                        @Override
                                        public void handleMessage(Message msg) {
                                            super.handleMessage(msg);
                                            switch (msg.what) {
                                                case 1:
                                                    Bundle bundle = msg.getData();
                                                    if(null != bundle){
                                                        list = bundle.getStringArrayList("resultImagePath");
                                                        Log.d("xxx",list.size()+"======"+list.toString());

                                                        String s = list.toString().substring(1, list.toString().length() - 1);

                                                        doOkHttpCaogao(userid, content, s, null, null, whocansee, 21, 0, "");
                                                    }
                                                    break;
                                                default:
                                                    break;
                                            }
                                        }
                                    };

                                }
                                if (content.length() > 0 && select1.size()>0) {
                                    //文本加视频
                                    //类型为31
                                    getUpVideo(filePath,select1.size(),handler);


                                    handler = new Handler() {
                                        @Override
                                        public void handleMessage(Message msg) {
                                            super.handleMessage(msg);
                                            switch (msg.what) {
                                                case 1:
                                                    Bundle bundle = msg.getData();
                                                    if(null != bundle){
                                                        list = bundle.getStringArrayList("resultImagePath");
                                                        Log.d("xxx",list.size()+"======"+list.toString());

                                                        String s = list.toString().substring(1, list.toString().length() - 1);

                                                        //调用发布动态接口
                                                        doOkHttpCaogao(userid, content, s, null, null, whocansee, 31, 0, "");
                                                    }
                                                    break;
                                                default:
                                                    break;
                                            }
                                        }
                                    };



                                }
                                if (content.length() ==0 && select.size() > 0) {
                                    //纯图片
                                    //类型为2

                                    for (int i = 0; i < select.size()-1; i++) {
                                        String url = select.get(i).path;
                                        getUpimg(url,select.size()-1,handler);
                                    }
                                    handler = new Handler() {
                                        @Override
                                        public void handleMessage(Message msg) {
                                            super.handleMessage(msg);
                                            switch (msg.what) {
                                                case 1:
                                                    Bundle bundle = msg.getData();
                                                    if(null != bundle){
                                                        list = bundle.getStringArrayList("resultImagePath");
                                                        Log.d("xxx",list.size()+"======"+list.toString());

                                                        String s = list.toString().substring(1, list.toString().length() - 1);


                                                        doOkHttpCaogao(userid, "", s, null, null, whocansee, 2, 0, "");
                                                    }
                                                    break;
                                                default:
                                                    break;
                                            }
                                        }
                                    };

                                }
                                if (content.length()==0 && select1.size() > 0) {
                                    //纯视频
                                    //类型为3

                                    getUpVideo(filePath,select1.size(),handler);

                                    handler = new Handler() {
                                        @Override
                                        public void handleMessage(Message msg) {
                                            super.handleMessage(msg);
                                            switch (msg.what) {
                                                case 1:
                                                    Bundle bundle = msg.getData();
                                                    if(null != bundle){
                                                        list = bundle.getStringArrayList("resultImagePath");
                                                        Log.d("xxx",list.size()+"======"+list.toString());

                                                        String s = list.toString().substring(1, list.toString().length() - 1);

                                                        //调用发布动态接口
                                                        doOkHttpCaogao(userid, "", s, null, null, whocansee, 3, 0, "");
                                                    }
                                                    break;
                                                default:
                                                    break;
                                            }
                                        }
                                    };

                                }
                            }


                        }
                    });
                    builder.setNegativeButton("不保存",
                            new android.content.DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    finish();
                                }
                            });
                    builder.create().show();
                }
                else{
                    finish();
                }
                break;
            //发布
            case R.id.tv_release:

                showDialog();
                // TODO: 2020/11/21 0021 获取内容 判断发布类型  1纯文本 2纯图片 3纯视频  21文本加图片 31文本加视频
                switch (tvWhocansee.getText().toString()) {
                    case "所有人可见":
                        whocansee = 1;
                        break;
                    case "仅关注可见":
                        whocansee = 4;
                        break;
                    case "仅相互关注可见":
                        whocansee = 3;
                        break;
                    case "仅自己可见":
                        whocansee = 0;
                        break;
                }
                String content = etContent.getText().toString();
                if (isSelect) {
                    //传位置
                    //判断内容
                    if (content.length() > 0 && select.size()==0 && select1.size()==0) {
                        //仅文本
                        //类型为 1
                        doOkHttp(userid,content,"", longitude, latitude, whocansee, 1, 1, district);
                    }
                    if (content.length() > 0 && select.size() > 0) {
                        //文本加图片
                        //类型为 21
                        //先将图片传到七牛云生成路径 再调用发布动态的接口

                        for (int i = 0; i < select.size()-1; i++) {
                            String url = select.get(i).path;
                            getUpimg(url,select.size()-1,handler);
                        }
                        handler = new Handler() {
                            @Override
                            public void handleMessage(Message msg) {
                                super.handleMessage(msg);
                                switch (msg.what) {
                                    case 1:
                                        Bundle bundle = msg.getData();
                                        if(null != bundle){
                                            list = bundle.getStringArrayList("resultImagePath");
                                            Log.d("xxx",list.size()+"======"+list.toString());

                                            String s = list.toString().substring(1, list.toString().length() - 1);


                                            doOkHttp(userid, content, s, longitude, latitude, whocansee, 21, 1, district);
                                        }
                                        break;
                                    default:
                                        break;
                                }
                            }
                        };


                    }
                    if (content.length() > 0 && select1.size() > 0) {
                        //文本加视频
                        //类型为31

                        getUpVideo(filePath,select1.size(),handler);

                        handler = new Handler() {
                            @Override
                            public void handleMessage(Message msg) {
                                super.handleMessage(msg);
                                switch (msg.what) {
                                    case 1:
                                        Bundle bundle = msg.getData();
                                        if(null != bundle){
                                            list = bundle.getStringArrayList("resultImagePath");
                                            Log.d("xxx",list.size()+"======"+list.toString());

                                            String s = list.toString().substring(1, list.toString().length() - 1);

                                            //调用发布动态接口
                                            doOkHttp(userid, content, s, longitude, latitude, whocansee, 31, 1, district);
                                        }
                                        break;
                                    default:
                                        break;
                                }
                            }
                        };
                    }
                    if (content.length()==0 && select.size() > 0) {
                        //纯图片
                        //类型为2
                        //先将图片传到七牛云生成路径 再调用发布动态的接口
                        //将图片转换
                        for (int i = 0; i < select.size()-1; i++) {
                            String url = select.get(i).path;
                            getUpimg(url,select.size()-1,handler);
                        }
                        handler = new Handler() {
                            @Override
                            public void handleMessage(Message msg) {
                                super.handleMessage(msg);
                                switch (msg.what) {
                                    case 1:
                                        Bundle bundle = msg.getData();
                                        if(null != bundle){
                                            list = bundle.getStringArrayList("resultImagePath");
                                            Log.d("xxx",list.size()+"======"+list.toString());

                                            String s = list.toString().substring(1, list.toString().length() - 1);

                                            //调用发布动态接口
                                            doOkHttp(userid, "", s, longitude, latitude, whocansee, 2, 1, district);
                                        }
                                        break;
                                    default:
                                        break;
                                }
                            }
                        };


                    }
                    if (content.length()==0 && select1.size() > 0) {
                        //纯视频
                        //类型为3
                        getUpVideo(filePath,select1.size(),handler);

                        handler = new Handler() {
                            @Override
                            public void handleMessage(Message msg) {
                                super.handleMessage(msg);
                                switch (msg.what) {
                                    case 1:
                                        Bundle bundle = msg.getData();
                                        if(null != bundle){
                                            list = bundle.getStringArrayList("resultImagePath");
                                            Log.d("xxx",list.size()+"======"+list.toString());

                                            String s = list.toString().substring(1, list.toString().length() - 1);

                                            //调用发布动态接口
                                            doOkHttp(userid, "", s, longitude, latitude, whocansee, 3, 1, district);
                                        }
                                        break;
                                    default:
                                        break;
                                }
                            }
                        };
                    }
                } else {
                    //不传位置
                    //判断内容
                    if (content.length() > 0 && select.size()==0 && select1.size()==0) {
                        //仅文本
                        //类型为 1
                        doOkHttp(userid, content, "", null, null, whocansee, 1, 1, "");
                    }
                    if (content.length() > 0 && select.size() > 0) {
                        //文本加图片
                        //类型为 21
                        for (int i = 0; i < select.size()-1; i++) {
                            String url = select.get(i).path;
                            getUpimg(url,select.size()-1,handler);
                        }
                        handler = new Handler() {
                            @Override
                            public void handleMessage(Message msg) {
                                super.handleMessage(msg);
                                switch (msg.what) {
                                    case 1:
                                        Bundle bundle = msg.getData();
                                        if(null != bundle){
                                            list = bundle.getStringArrayList("resultImagePath");
                                            Log.d("xxx",list.size()+"======"+list.toString());

                                            String s = list.toString().substring(1, list.toString().length() - 1);

                                            doOkHttp(userid, content, s, null, null, whocansee, 21, 1, "");
                                        }
                                        break;
                                    default:
                                        break;
                                }
                            }
                        };

                    }
                    if (content.length() > 0 && select1.size()>0) {
                        //文本加视频
                        //类型为31
                        getUpVideo(filePath,select1.size(),handler);

                        handler = new Handler() {
                            @SuppressLint("HandlerLeak")
                            @Override
                            public void handleMessage(Message msg) {
                                super.handleMessage(msg);
                                switch (msg.what) {
                                    case 1:
                                        Bundle bundle = msg.getData();
                                        if(null != bundle){
                                            list = bundle.getStringArrayList("resultImagePath");
                                            Log.d("xxx",list.size()+"======"+list.toString());

                                            String s = list.toString().substring(1, list.toString().length() - 1);

                                            doOkHttp(userid, content, s, null, null, whocansee, 31, 1, "");

                                        }

                                        break;
                                    default:
                                        break;
                                }
                            }
                        };


                    }
                    if (content.length() ==0 && select.size() > 0) {
                        //纯图片
                        //类型为2

                        Log.d("xxx","选中图片长度为:"+select.size()+"");
                        for (int i = 0; i < select.size()-1; i++) {
                            String url = select.get(i).path;
                            getUpimg(url,select.size()-1,handler);
                        }
                        handler = new Handler() {
                            @SuppressLint("HandlerLeak")
                            @Override
                            public void handleMessage(Message msg) {
                                super.handleMessage(msg);
                                switch (msg.what) {
                                    case 1:
                                        Bundle bundle = msg.getData();
                                        if(null != bundle){
                                            list = bundle.getStringArrayList("resultImagePath");
                                            Log.d("xxx","获取到的图片长度为"+list.size()+"======"+list.toString());
                                            Log.d("xxx","走到这里了");

                                            String s = list.toString().substring(1, list.toString().length() - 1);

                                            doOkHttp(userid, "", s, null, null, whocansee, 2, 1, "");
                                        }
                                        break;
                                    default:
                                        break;
                                }
                            }
                        };

                    }
                    if (content.length()==0 && select1.size() > 0) {
                        //纯视频
                        //类型为3
                        getUpVideo(filePath,select1.size(),handler);

                        handler = new Handler() {
                            @Override
                            public void handleMessage(Message msg) {
                                super.handleMessage(msg);
                                switch (msg.what) {
                                    case 1:
                                        Bundle bundle = msg.getData();
                                        if(null != bundle){
                                            list = bundle.getStringArrayList("resultImagePath");
                                            Log.d("xxx",list.size()+"======"+list.toString());

                                            String s = list.toString().substring(1, list.toString().length() - 1);

                                            //调用发布动态接口
                                            doOkHttp(userid, "", s, null, null, whocansee, 3, 1, "");
                                        }
                                        break;
                                    default:
                                        break;
                                }
                            }
                        };

                    }
                }

                break;
            //选择图片或视频
            case R.id.rl_selectedimg:
                // TODO: 2020/11/21 0021 弹出选择图片或视频弹出框
                showSelect();
                break;
            //获取我的当前位置
            case R.id.ll_my_location:
                if (isSelect == false) {
                    llMyLocation.setBackground(this.getResources().getDrawable(R.drawable.tenradious_ae_bg));
                    tvMylocation.setTextColor(this.getResources().getColor(R.color.white));
                    isSelect = true;
                } else {
                    llMyLocation.setBackground(this.getResources().getDrawable(R.drawable.tenradious_f2_bg));
                    tvMylocation.setTextColor(this.getResources().getColor(R.color.color_666666));
                    isSelect = false;
                }
                break;
            //@好友
            case R.id.rl_aiteFriend:
                //跳转到选择好友列表页
                startActivity(new Intent(this, SelectFriendActivity.class));

                break;
            //发表话题
            case R.id.rl_developHuati:
                //获取当前光标位置
                int index = etContent.getSelectionStart();

                //插入到光标所在位置
                Editable editable = etContent.getEditableText();
                editable.insert(index, "##");

                //插入到光标所在位置
                int end = index + 2;//获取文本长度

                editable.setSpan(new ForegroundColorSpan(R.color.start), index, end,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);//关键字变色
                //设置光标到##中间
                String s = etContent.getText().toString();

                etContent.setSelection(s.length() - 1);
                //打开键盘
                KeyBoardUtils.openKeyBoard(etContent);

                tvRelease.setBackground(DevelopmentDynamicActivity.this.getDrawable(R.drawable.background_gradient_nine_radious));
                tvRelease.setClickable(true);

                break;
            //谁可以看
            case R.id.ll_whocansee:
                //跳转到设置是否可见界面
                //将获取到的内容传递到选择界面
                String str = tvWhocansee.getText().toString();
                if (str != null) {
                    Intent intent = new Intent(DevelopmentDynamicActivity.this, SelectWhoCanSeeActivity.class);
                    intent.putExtra("msg", str);
                    startActivity(intent);
                }
                break;
            //删除视频
            case R.id.iv_delete_vv:

                //释放videoview 隐藏VideoView 弹出选择框
                videoView.stopPlayback();
                rlVv.setVisibility(View.GONE);
                rlSelectedimg.setVisibility(View.VISIBLE);

                //清除视频
                select1.clear();

                //清除视频后判断别的内容是否为空
                if (length > 0 || select.size() > 0) {
                    tvRelease.setBackground(DevelopmentDynamicActivity.this.getDrawable(R.drawable.background_gradient_nine_radious));
                    tvRelease.setClickable(true);
                } else {
                    tvRelease.setBackground(DevelopmentDynamicActivity.this.getDrawable(R.drawable.nine_radious_gray));
                    tvRelease.setClickable(false);
                }
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getWhoCanSee(SaveWhoCanseeBean saveWhoCanseeBean) {
        //回显到发布动态上
        tvWhocansee.setText(saveWhoCanseeBean.getStr());
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                district = aMapLocation.getDistrict();

                //纬度
                latitude = aMapLocation.getLatitude();
                //经度
                longitude = aMapLocation.getLongitude();

                tvMylocation.setText(district);
            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
            }
        }
    }


    //删除列表内
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getint(Integer id) {
        //判断是否已经存在占位图
        boolean ch = false;
        select.remove(id);
        Log.d("xxx", select.size() + "");

        if (select.size() == 8) {
            for (int i = 0; i < select.size(); i++) {
                if (select.get(i).path.equals("123")) {
                    ch = false;
                } else {
                    ch = true;

                }
            }
        }
        if (ch == true) {
            select.add(new Media("123", "123", 1, 1, 1, 1, "123"));
            ch = false;
        }
        if (select.size() == 1) {
            select.clear();
            tvRelease.setBackground(DevelopmentDynamicActivity.this.getDrawable(R.drawable.nine_radious_gray));
            tvRelease.setClickable(false);

        }
        develogmentImgAdapter.notifyDataSetChanged();

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getString(String str) {
        if (str.equals("展示选择框")) {
            rcProImg.setVisibility(View.GONE);
            rlSelectedimg.setVisibility(View.VISIBLE);
        }

    }

    //开始定位
    public void doLocation() {
        mlocationClient = new AMapLocationClient(this);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位监听
        mlocationClient.setLocationListener(this);
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(10000);
        //设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为1000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
        //启动定位
        mlocationClient.startLocation();
    }

    public void showSelect() {
        //弹出联系方式并创建订单
        //添加成功后处理
        mPopupWindow1 = new PopupWindow();
        mPopupWindow1.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow1.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        View view = LayoutInflater.from(DevelopmentDynamicActivity.this).inflate(R.layout.dialog_selector_resoues_way, null);

        RelativeLayout rl_img = view.findViewById(R.id.tv_img);
        RelativeLayout rl_video = view.findViewById(R.id.tv_video);
        RelativeLayout rl_cancle = view.findViewById(R.id.tv_cancle);

        rl_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //测试图片选择
                //装被选中的文件
                Intent intent = new Intent(DevelopmentDynamicActivity.this, PickerActivity.class);
                intent.putExtra(PickerConfig.SELECT_MODE, PickerConfig.PICKER_IMAGE);//设置选择类型，默认是图片和视频可一起选择(非必填参数)
                long maxSize = 10485760L;//long long long long类型
                intent.putExtra(PickerConfig.MAX_SELECT_SIZE, maxSize);
                intent.putExtra(PickerConfig.MAX_SELECT_COUNT, 9); //最大选择数量，默认40（非必填参数）
                ArrayList<Media> defaultSelect = select;//可以设置默认选中的照片，比如把select刚刚选择的list设置成默认的。
                intent.putExtra(PickerConfig.DEFAULT_SELECTED_LIST, defaultSelect); //可以设置默认选中的照片(非必填参数)
                DevelopmentDynamicActivity.this.startActivityForResult(intent, 201);

                select1.clear();
            }
        });
        rl_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //测试视频选择
                //装被选中的文件
                Intent intent = new Intent(DevelopmentDynamicActivity.this, PickerActivity.class);
                intent.putExtra(PickerConfig.SELECT_MODE, PickerConfig.PICKER_VIDEO);//设置选择类型，默认是图片和视频可一起选择(非必填参数)
                long maxSize = 104857600;//long long long long类型
                intent.putExtra(PickerConfig.MAX_SELECT_SIZE, maxSize);
                intent.putExtra(PickerConfig.MAX_SELECT_COUNT, 1); //最大选择数量，默认40（非必填参数）
                ArrayList<Media> defaultSelect = select1;//可以设置默认选中的视频，比如把select刚刚选择的list设置成默认的。
                intent.putExtra(PickerConfig.DEFAULT_SELECTED_LIST, defaultSelect); //可以设置默认选中的照片(非必填参数)
                DevelopmentDynamicActivity.this.startActivityForResult(intent, 200);

                select.clear();
                rcProImg.setVisibility(View.GONE);
            }
        });
        rl_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        //popwindow设置属性
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
        show1(view);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200 && resultCode == PickerConfig.RESULT_CODE) {
            if (data.getParcelableArrayListExtra(PickerConfig.EXTRA_RESULT).size() > 0) {
                select1 = data.getParcelableArrayListExtra(PickerConfig.EXTRA_RESULT);

            }

            Log.i("select", "select1.size" + select1.size());
            if (select1.size() > 0) {
                tvRelease.setBackground(DevelopmentDynamicActivity.this.getDrawable(R.drawable.background_gradient_nine_radious));
                tvRelease.setClickable(true);
                Media media = select1.get(0);
                //将视频压缩并回显
                selectedVideoUri = Uri.parse(media.path);
                Log.d("xxx", "selectedVideoUri    " + selectedVideoUri);

                //压缩视频
                if (selectedVideoUri != null) {
                    executeScaleVideo((int) (rangeSeekBar.getCurrentRange()[0] * 1000), (int) (rangeSeekBar.getCurrentRange()[1] * 1000));
                } else {
                    Toast.makeText(getApplicationContext(), "Please upload a video", Toast.LENGTH_SHORT).show();
                }

            } else {
                tvRelease.setBackground(DevelopmentDynamicActivity.this.getDrawable(R.drawable.nine_radious_gray));
                tvRelease.setClickable(false);
            }
        } else if (requestCode == 201 && resultCode == PickerConfig.RESULT_CODE) {

            if (data.getParcelableArrayListExtra(PickerConfig.EXTRA_RESULT).size() > 0) {
                select = data.getParcelableArrayListExtra(PickerConfig.EXTRA_RESULT);
            }
            Log.i("select", "select.size" + select.size());
            boolean ch = false;

            if (select.size() > 0) {
                tvRelease.setBackground(DevelopmentDynamicActivity.this.getDrawable(R.drawable.background_gradient_nine_radious));
                tvRelease.setClickable(true);
                rlSelectedimg.setVisibility(View.GONE);
                rcProImg.setVisibility(View.VISIBLE);
                // TODO: 2020/11/23 0023 将图片数据设置到列表内


                //判断是否已经存在空数据 存在不添加
                for (int i = 0; i < select.size(); i++) {
                    if (select.get(i).path.equals("123")) {
                        ch = false;
                    } else {
                        ch = true;
                    }
                }

                if (ch == true) {
                    select.add(new Media("123", "123", 1, 1, 1, 1, "123"));
                    ch = false;
                }
                //创建布局管理器
                GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
                rcProImg.setLayoutManager(gridLayoutManager);
                //创建适配器

                develogmentImgAdapter = new DevelogmentImgAdapter(this, select);
                rcProImg.setAdapter(develogmentImgAdapter);

            } else {
                tvRelease.setBackground(DevelopmentDynamicActivity.this.getDrawable(R.drawable.nine_radious_gray));
                tvRelease.setClickable(false);

                rcProImg.setVisibility(View.GONE);
                rlSelectedimg.setVisibility(View.VISIBLE);

            }
        }
    }

    //设置透明度
    public void setWindowAlpa(boolean isopen) {
        if (Build.VERSION.SDK_INT < 11) {
            return;
        }

        final Window window = DevelopmentDynamicActivity.this.getWindow();
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

    private void show1(View v) {
        if (mPopupWindow1 != null && !mPopupWindow1.isShowing()) {
            mPopupWindow1.showAtLocation(v, Gravity.CENTER_HORIZONTAL, 0, 0);
        }
        setWindowAlpa(true);
    }

    /**
     * 消失PopupWindow
     */
    public void dismiss() {
        if (mPopupWindow1 != null && mPopupWindow1.isShowing()) {
            mPopupWindow1.dismiss();
        }
    }

    private void executeScaleVideo(final int startMs, final int endMs) {
        File moviesDir = getTempMovieDir();
        progressDialog.show();
        String filePrefix = "scale_video";
        String fileExtn = ".mp4";
        File dest = new File(moviesDir, filePrefix + fileExtn);
        int fileNo = 0;
        while (dest.exists()) {
            fileNo++;
            dest = new File(moviesDir, filePrefix + fileNo + fileExtn);
        }
        filePath = dest.getAbsolutePath();

        Log.d("xxx", "filePath的路径为:"+filePath);
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean success = true;
                try {
                    MediaMetadataRetriever retriever = new MediaMetadataRetriever();
                    retriever.setDataSource(DevelopmentDynamicActivity.this, selectedVideoUri);
                    int originWidth = Integer.parseInt(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH));
                    int originHeight = Integer.parseInt(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT));
                    int bitrate = Integer.parseInt(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_BITRATE));

                    int outWidth = originWidth / 2;
                    int outHeight = originHeight / 2;
                    VideoProcessor.scaleVideo(getApplicationContext(), selectedVideoUri, filePath, outWidth, outHeight);


                } catch (Exception e) {
                    success = false;
                    e.printStackTrace();
                    postError();
                }
                if (success) {
                    startPreviewActivity(filePath);
                }
                progressDialog.dismiss();
            }
        }).start();
    }


    private void startPreviewActivity(String videoPath) {
        String name = new File(videoPath).getName();
        int end = name.lastIndexOf('.');
        if (end > 0) {
            name = name.substring(0, end);
        }
        strUri = VideoUtil.savaVideoToMediaStore(this, videoPath, name, "From VideoProcessor", "video/mp4");


        Log.d("xxx", "压缩后的视频路径为:"+strUri);
        // TODO: 2020/11/24 0024 压缩成功 处理压缩后的视频

        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                rlSelectedimg.setVisibility(View.GONE);
                rlVv.setVisibility(View.VISIBLE);

                videoView.setVideoPath(strUri);
                videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        //视频加载完成,准备好播放视频的回调
                        //开始播放
                        videoView.start();
                    }
                });

            }
        });
    }

    private File getTempMovieDir() {
        File movie = new File(getCacheDir(), "movie");
        movie.mkdirs();
        return movie;
    }

    private void postError() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), "process error!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
