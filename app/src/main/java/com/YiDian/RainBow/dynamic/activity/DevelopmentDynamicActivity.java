package com.YiDian.RainBow.dynamic.activity;

import android.Manifest;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
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
import com.YiDian.RainBow.base.Common;
import com.YiDian.RainBow.custom.customDialog.CustomDialogMsg;
import com.YiDian.RainBow.custom.loading.CustomDialog;
import com.YiDian.RainBow.dynamic.adapter.DevelogmentImgAdapter;
import com.YiDian.RainBow.dynamic.adapter.HotHuatiAdapter;
import com.YiDian.RainBow.dynamic.bean.HotTopicBean;
import com.YiDian.RainBow.dynamic.bean.SaveAiteBean;
import com.YiDian.RainBow.dynamic.bean.SaveHotHuatiBean;
import com.YiDian.RainBow.dynamic.bean.SaveWhoCanseeBean;
import com.YiDian.RainBow.dynamic.bean.WriteDevelopmentBean;
import com.YiDian.RainBow.main.fragment.activity.SimplePlayerActivity;
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
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

//????????????
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
    //??????mlocationClient??????
    public AMapLocationClient mlocationClient;
    //??????mLocationOption??????
    public AMapLocationClientOption mLocationOption = null;
    boolean isSelect = false;
    //???????????????
    private int length;
    private DevelogmentImgAdapter develogmentImgAdapter;
    private String filePath;
    private Uri selectedVideoUri;
    private CustomDialog progressDialog;
    //????????????????????????
    private String strUri;

    private String substring;
    private double latitude;
    private double longitude;
    private String district;
    private ArrayList<String> list;
    int whocansee = 1;
    int userid ;
    private UploadManager uploadManager;
    private String upToken;
    private ArrayList<String> upimg_key_list;
    private Handler handler;
    private static final String serverPath = "http://img.rianbow.cn/";
    private CustomDialog dialog;

    @Override
    protected int getResId() {
        return R.layout.activity_development_dynamic;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void getData() {
        handler = new Handler();

        //????????????????????? ????????????????????????
        StatusBarUtil.setGradientColor(DevelopmentDynamicActivity.this, toolbar);
        StatusBarUtil.setDarkMode(DevelopmentDynamicActivity.this);

        progressDialog = new CustomDialog(this,"??????????????????...");

        dialog = new CustomDialog(this, "????????????...");

        CL.setLogEnable(true);
        userid = Integer.parseInt(Common.getUserId());
        //???????????????????????????
        if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) && (DevelopmentDynamicActivity.this.checkSelfPermission
                (Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
            //????????????
            DevelopmentDynamicActivity.this.requestPermissions(new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        if (Build.VERSION.SDK_INT >= 23) {
            int request = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
            if (request != PackageManager.PERMISSION_GRANTED)//?????????????????????????????????
            {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);
                return;//
            } else {

            }
        } else {

        }
        select = new ArrayList<>();
        select1 = new ArrayList<>();

        // TODO: 2020/11/23 0023 ????????????????????????
        doLocation();

        //??????????????????
        tvRelease.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        rlSelectedimg.setOnClickListener(this);
        llMyLocation.setOnClickListener(this);
        rlAiteFriend.setOnClickListener(this);
        rlDevelopHuati.setOnClickListener(this);
        llWhocansee.setOnClickListener(this);
        ivDeleteVv.setOnClickListener(this);
        //???????????????????????? ??????????????????
        tvRelease.setBackground(this.getDrawable(R.drawable.nine_radious_gray));
        tvRelease.setClickable(false);
        //????????????
        etContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //???????????????????????????????????? ??????????????????????????????
                length = etContent.getText().length();
                //??????????????????????????? ??????0????????????????????? ??????0????????????
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
                //?????????????????????????????????????????? ?????????
                tvCount.setText(100 - length + "");
            }
        });
        NetUtils.getInstance().getApis()
                .dogetHotTopicBean()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HotTopicBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(HotTopicBean hotTopicBean) {
                        List<String> list = hotTopicBean.getObject();
                        if (list!=null && list.size()>0){
                            rcHotHuati.setVisibility(View.VISIBLE);

                            //????????????
                            GridLayoutManager gridLayoutManager = new GridLayoutManager(DevelopmentDynamicActivity.this, 4);
                            rcHotHuati.setLayoutManager(gridLayoutManager);
                            //???????????????
                            HotHuatiAdapter hotHuatiAdapter = new HotHuatiAdapter(DevelopmentDynamicActivity.this, list);
                            rcHotHuati.setAdapter(hotHuatiAdapter);
                        }else{
                            rcHotHuati.setVisibility(View.GONE);
                        }

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });



        videoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DevelopmentDynamicActivity.this, SimplePlayerActivity.class);
                intent.putExtra("url", strUri);
                startActivity(intent);
            }
        });

        //???????????????uptoken
        upToken = Common.getToken();
    }

    //??????????????????
    @SuppressLint("ResourceAsColor")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getHotHuati(SaveHotHuatiBean saveHotHuatiBean) {
        String str = saveHotHuatiBean.getStr();

        //????????????????????????
        int index = etContent.getSelectionStart();

        //???????????????????????????
        Editable editable = etContent.getEditableText();
        editable.insert(index, str);
        //???????????????????????????
        int end = index + str.length();//??????????????????
        editable.setSpan(new ForegroundColorSpan(R.color.start), index, end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);//???????????????


        String s = etContent.getText().toString();
        //?????????????????????
        etContent.setSelection(s.length());

        tvRelease.setBackground(DevelopmentDynamicActivity.this.getDrawable(R.drawable.background_gradient_nine_radious));
        tvRelease.setClickable(true);
    }

    //?????????????????????
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getAiteFriend(SaveAiteBean bean) {
        substring = bean.getString();
        String[] split = substring.split(",");
        //??????????????????????????????
        int index = etContent.getSelectionStart();
        Editable editable = etContent.getEditableText();

        if(split.length>0 && split!=null){
            for (int i =0;i<split.length;i++){
                //???????????????????????????
                editable.insert(index, "@"+split[i]+" ");
            }
        }
        int end = index + substring.length()+split.length;//??????????????????

        editable.setSpan(new ForegroundColorSpan(Color.BLUE), index, end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);//???????????????

        String s = etContent.getText().toString();

        //?????????????????????
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
            //????????????
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
        dialog.show();
        NetUtils.getInstance().getApis().doWriteDevelopment(userid, content, img, log, lat, whocansee, type, status, area)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WriteDevelopmentBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(WriteDevelopmentBean writeDevelopmentBean) {
                        dialog.show();
                        Toast.makeText(DevelopmentDynamicActivity.this, "??????????????????", Toast.LENGTH_SHORT).show();
                        //????????????????????????
                        finish();
                        EventBus.getDefault().post("????????????");
                    }

                    @Override
                    public void onError(Throwable e) {
                        dialog.show();

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void doOkHttpCaogao(int userid,String content,String img,Double log,Double lat,int whocansee,int type,int status,String area){
        dialog.show();

        NetUtils.getInstance().getApis().doWriteDevelopment(userid, content, img, log, lat, whocansee, type, status, area)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WriteDevelopmentBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(WriteDevelopmentBean writeDevelopmentBean) {
                        dialog.show();

                        //????????????????????????
                        finish();
                    }

                    @Override
                    public void onError(Throwable e) {
                        dialog.show();

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    public void getUpimg(String imagePath,int picSize,Handler mHandler){

        Log.d("xxx","????????????");
        upimg_key_list = new ArrayList<String>();
        new Thread() {
            public void run() {
                // ????????????????????? ?????? uploadManager???????????????????????????????????? uploadManager ??????
                uploadManager = new UploadManager();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                // ????????????
                String s = MD5Utils.string2Md5_16(imagePath);
                String key = s+sdf.format(new Date())+".jpg";
                uploadManager.put(imagePath, key, upToken,
                        new UpCompletionHandler() {
                            @Override
                            public void complete(String key, ResponseInfo info,
                                                 JSONObject res) {
                                // res ??????hash???key?????????????????????????????????????????????????????????
                                Log.i("xxx", key + ",\r\n " + info + ",\r\n "
                                        + res);
                                try {
                                    // ????????????????????????
                                    String upimg = res.getString("key");
                                    upimg_key_list.add(serverPath+upimg);//??????????????????????????????????????????list?????????

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

        Log.d("xxx","????????????");
        upimg_key_list = new ArrayList<String>();
        new Thread() {
            public void run() {
                // ????????????????????? ?????? uploadManager???????????????????????????????????? uploadManager ??????
                uploadManager = new UploadManager();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                // ????????????
                String s = MD5Utils.string2Md5_16(imagePath);
                String key = s+sdf.format(new Date())+".mp4";
                uploadManager.put(imagePath, key, upToken,
                        new UpCompletionHandler() {
                            @Override
                            public void complete(String key, ResponseInfo info,
                                                 JSONObject res) {
                                // res ??????hash???key?????????????????????????????????????????????????????????
                                Log.i("xxx", "??????"+key + ",\r\n " + info + ",\r\n "
                                        + res);
                                try {
                                    // ????????????????????????
                                    String upimg = res.getString("key");
                                    upimg_key_list.add(serverPath+upimg);//??????????????????????????????????????????list?????????

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
                                    Log.d("xxx","???????????????"+e.getMessage());
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
            //??????
            case R.id.iv_back:

                // TODO: 2020/11/21 0021 ????????????????????????????????????????????? ??????????????? ?????????????????????\
                if(etContent.getText().toString().length()>0 || select.size()>0 || select1.size()>0){
                    CustomDialogMsg.Builder builder = new CustomDialogMsg.Builder(this);
                    builder.setPositiveButton("??????", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            // TODO: 2020/11/21 0021 ???????????? ??????????????????  1????????? 2????????? 3?????????  21??????????????? 31???????????????
                            switch (tvWhocansee.getText().toString()) {
                                case "???????????????":
                                    whocansee = 1;
                                    break;
                                case "???????????????":
                                    whocansee = 4;
                                    break;
                                case "?????????????????????":
                                    whocansee = 3;
                                    break;
                                case "???????????????":
                                    whocansee = 0;
                                    break;
                            }
                            String content = etContent.getText().toString();
                            if (isSelect) {
                                //?????????
                                //????????????
                                if (content.length() > 0 && select.size()==0 && select1.size()==0) {
                                    //?????????
                                    //????????? 1
                                    doOkHttpCaogao(userid,content,"", longitude, latitude, whocansee, 1, 0, district);
                                }
                                if (content.length() > 0 && select.size() > 0) {
                                    //???????????????
                                    //????????? 21
                                    //??????????????????????????????????????? ??????????????????????????????

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
                                    //???????????????
                                    //?????????31

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


                                                        //????????????????????????
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
                                    //?????????
                                    //?????????2
                                    //??????????????????????????????????????? ??????????????????????????????
                                    //???????????????

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

                                                        //????????????????????????
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
                                    //?????????
                                    //?????????3
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


                                                        //????????????????????????
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
                                //????????????
                                //????????????
                                if (content.length() > 0 && select.size()==0 && select1.size()==0) {
                                    //?????????
                                    //????????? 1
                                    doOkHttpCaogao(userid, content, "", null, null, whocansee, 1, 0, "");
                                }
                                if (content.length() > 0 && select.size() > 0) {
                                    //???????????????
                                    //????????? 21
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
                                    //???????????????
                                    //?????????31
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

                                                        //????????????????????????
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
                                    //?????????
                                    //?????????2

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
                                    //?????????
                                    //?????????3

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
                                                        Log.d("xxx",list.size()+"?????????????????????======"+list.toString());

                                                        String s = list.toString().substring(1, list.toString().length() - 1);

                                                        //????????????????????????
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
                    builder.setNegativeButton("?????????",
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
            //??????
            case R.id.tv_release:
                // TODO: 2020/11/21 0021 ???????????? ??????????????????  1????????? 2????????? 3?????????  21??????????????? 31???????????????
                switch (tvWhocansee.getText().toString()) {
                    case "???????????????":
                        whocansee = 1;
                        break;
                    case "???????????????":
                        whocansee = 4;
                        break;
                    case "?????????????????????":
                        whocansee = 3;
                        break;
                    case "???????????????":
                        whocansee = 0;
                        break;
                }
                String content = etContent.getText().toString();
                if (isSelect) {
                    //?????????
                    //????????????
                    if (content.length() > 0 && select.size()==0 && select1.size()==0) {
                        //?????????
                        //????????? 1
                        doOkHttp(userid,content,"", longitude, latitude, whocansee, 1, 1, district);
                    }
                    if (content.length() > 0 && select.size() > 0) {
                        //???????????????
                        //????????? 21
                        //??????????????????????????????????????? ??????????????????????????????

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
                        //???????????????
                        //?????????31

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
                                            Log.d("xxx",list.size()+"?????????????????????======"+list.toString());

                                            String s = list.toString().substring(1, list.toString().length() - 1);

                                            //????????????????????????
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
                        //?????????
                        //?????????2
                        //??????????????????????????????????????? ??????????????????????????????
                        //???????????????
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

                                            //????????????????????????
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
                        //?????????
                        //?????????3
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
                                            Log.d("xxx",list.size()+"???????????????======"+list.toString());

                                            String s = list.toString().substring(1, list.toString().length() - 1);

                                            //????????????????????????
                                            doOkHttp(userid, "", s, longitude, latitude, whocansee, 3, 1, district);
                                        }
                                        break;
                                    default:
                                        break;
                                }
                            }
                        };
                    }
                }
                else {
                    //????????????
                    //????????????
                    if (content.length() > 0 && select.size()==0 && select1.size()==0) {
                        //?????????
                        //????????? 1
                        doOkHttp(userid, content, "", null, null, whocansee, 1, 1, "");
                    }
                    if (content.length() > 0 && select.size() > 0) {
                        //???????????????
                        //????????? 21
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
                        //???????????????
                        //?????????31
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
                        //?????????
                        //?????????2

                        Log.d("xxx","?????????????????????:"+select.size()+"");
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
                                            Log.d("xxx","???????????????????????????"+list.size()+"======"+list.toString());
                                            Log.d("xxx","???????????????");

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
                        //?????????
                        //?????????3
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

                                            //????????????????????????
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
            //?????????????????????
            case R.id.rl_selectedimg:
                // TODO: 2020/11/21 0021 ????????????????????????????????????
                showSelect();
                break;
            //????????????????????????
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
            //@??????
            case R.id.rl_aiteFriend:
                //??????????????????????????????
                startActivity(new Intent(this, SelectFriendActivity.class));

                break;
            //????????????
            case R.id.rl_developHuati:
                //????????????????????????
                int index = etContent.getSelectionStart();

                //???????????????????????????
                Editable editable = etContent.getEditableText();
                editable.insert(index, "##");

                //???????????????????????????
                int end = index + 2;//??????????????????

                //???????????????##??????
                String s = etContent.getText().toString();
                etContent.setSelection(s.length()-1);

                editable.setSpan(new ForegroundColorSpan(R.color.start), index, end,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);//???????????????

                //????????????
                KeyBoardUtils.openKeyBoard(etContent);

                tvRelease.setBackground(DevelopmentDynamicActivity.this.getDrawable(R.drawable.background_gradient_nine_radious));
                tvRelease.setClickable(true);

                break;
            //????????????
            case R.id.ll_whocansee:
                //?????????????????????????????????
                //??????????????????????????????????????????
                String str = tvWhocansee.getText().toString();
                if (str != null) {
                    Intent intent = new Intent(DevelopmentDynamicActivity.this, SelectWhoCanSeeActivity.class);
                    intent.putExtra("msg", str);
                    startActivity(intent);
                }
                break;
            //????????????
            case R.id.iv_delete_vv:

                //??????videoview ??????VideoView ???????????????
                videoView.stopPlayback();
                rlVv.setVisibility(View.GONE);
                rlSelectedimg.setVisibility(View.VISIBLE);

                //????????????
                select1.clear();

                //?????????????????????????????????????????????
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
        //????????????????????????
        tvWhocansee.setText(saveWhoCanseeBean.getStr());
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                //?????????????????????????????????????????????
                district = aMapLocation.getDistrict();

                //??????
                latitude = aMapLocation.getLatitude();
                //??????
                longitude = aMapLocation.getLongitude();

                tvMylocation.setText(district);
            } else {
                //??????????????????ErrCode???????????????errInfo???????????????????????????????????????
                Log.e("AmapError", "location Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
            }
        }
    }


    //???????????????
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getint(Integer id) {
        //?????????????????????????????????
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
        if (str.equals("???????????????")) {
            rcProImg.setVisibility(View.GONE);
            rlSelectedimg.setVisibility(View.VISIBLE);
        }

    }

    //????????????
    public void doLocation() {
        mlocationClient = new AMapLocationClient(this);
        //?????????????????????
        mLocationOption = new AMapLocationClientOption();
        //??????????????????
        mlocationClient.setLocationListener(this);
        //???????????????????????????????????????Battery_Saving?????????????????????Device_Sensors??????????????????
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //??????????????????,????????????,?????????2000ms
        mLocationOption.setInterval(10000);
        //??????????????????
        mlocationClient.setLocationOption(mLocationOption);
        // ????????????????????????????????????????????????????????????????????????????????????????????????????????????
        // ??????????????????????????????????????????????????????????????????1000ms?????????????????????????????????stopLocation()???????????????????????????
        // ???????????????????????????????????????????????????onDestroy()??????
        // ?????????????????????????????????????????????????????????????????????stopLocation()???????????????????????????sdk???????????????
        //????????????
        mlocationClient.startLocation();
    }

    public void showSelect() {
        //?????????????????????????????????
        //?????????????????????
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
                //??????????????????
                //?????????????????????
                Intent intent = new Intent(DevelopmentDynamicActivity.this, PickerActivity.class);
                intent.putExtra(PickerConfig.SELECT_MODE, PickerConfig.PICKER_IMAGE);//????????????????????????????????????????????????????????????(???????????????)
                long maxSize = 10485760L;//long long long long??????
                intent.putExtra(PickerConfig.MAX_SELECT_SIZE, maxSize);
                intent.putExtra(PickerConfig.MAX_SELECT_COUNT, 9); //???????????????????????????40?????????????????????
                ArrayList<Media> defaultSelect = select;//?????????????????????????????????????????????select???????????????list?????????????????????
                intent.putExtra(PickerConfig.DEFAULT_SELECTED_LIST, defaultSelect); //?????????????????????????????????(???????????????)
                DevelopmentDynamicActivity.this.startActivityForResult(intent, 201);

                select1.clear();
            }
        });
        rl_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //??????????????????
                //?????????????????????
                Intent intent = new Intent(DevelopmentDynamicActivity.this, PickerActivity.class);
                intent.putExtra(PickerConfig.SELECT_MODE, PickerConfig.PICKER_VIDEO);//????????????????????????????????????????????????????????????(???????????????)
                long maxSize = 104857600;//long long long long??????
                intent.putExtra(PickerConfig.MAX_SELECT_SIZE, maxSize);
                intent.putExtra(PickerConfig.MAX_SELECT_COUNT, 1); //???????????????????????????40?????????????????????
                ArrayList<Media> defaultSelect = select1;//?????????????????????????????????????????????select???????????????list?????????????????????
                intent.putExtra(PickerConfig.DEFAULT_SELECTED_LIST, defaultSelect); //?????????????????????????????????(???????????????)
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
                //????????????????????????
                selectedVideoUri = Uri.parse(media.path);
                Log.d("xxx", "selectedVideoUri    " + selectedVideoUri);

                //????????????
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
                // TODO: 2020/11/23 0023 ?????????????????????????????????


                //????????????????????????????????? ???????????????
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
                //?????????????????????
                GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
                rcProImg.setLayoutManager(gridLayoutManager);
                //???????????????

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

    //???????????????
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
     * ??????PopupWindow
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

        Log.d("xxx", "filePath????????????:"+filePath);
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


        Log.d("xxx", "???????????????????????????:"+strUri);
        // TODO: 2020/11/24 0024 ???????????? ????????????????????????

        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                rlSelectedimg.setVisibility(View.GONE);
                rlVv.setVisibility(View.VISIBLE);

                videoView.setVideoPath(strUri);
                videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        //??????????????????,??????????????????????????????
                        //????????????
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
