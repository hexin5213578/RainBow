package com.YiDian.RainBow.setup.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseAvtivity;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.utils.MD5Utils;
import com.YiDian.RainBow.utils.SPUtil;
import com.bumptech.glide.Glide;
import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.OnSuccessListener;
import com.huawei.hmf.tasks.Task;
import com.huawei.hms.mlplugin.card.icr.cn.MLCnIcrCapture;
import com.huawei.hms.mlplugin.card.icr.cn.MLCnIcrCaptureConfig;
import com.huawei.hms.mlplugin.card.icr.cn.MLCnIcrCaptureFactory;
import com.huawei.hms.mlplugin.card.icr.cn.MLCnIcrCaptureResult;
import com.huawei.hms.mlsdk.card.MLCardAnalyzerFactory;
import com.huawei.hms.mlsdk.card.icr.MLIcrAnalyzer;
import com.huawei.hms.mlsdk.card.icr.MLIcrAnalyzerSetting;
import com.huawei.hms.mlsdk.card.icr.MLIdCard;
import com.huawei.hms.mlsdk.common.MLFrame;
import com.leaf.library.StatusBarUtil;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

public class RealnameActivity extends BaseAvtivity implements View.OnClickListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_back)
    LinearLayout ivBack;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_idcard)
    EditText etIdcard;
    @BindView(R.id.rl_zhengmian)
    RelativeLayout rlZhengmian;
    @BindView(R.id.rl_fanmian)
    RelativeLayout rlFanmian;
    @BindView(R.id.bt_confirm)
    Button btConfirm;
    @BindView(R.id.rl_confirm)
    RelativeLayout rlConfirm;
    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_idcard)
    TextView tvIdcard;
    @BindView(R.id.rl_isreal)
    RelativeLayout rlIsreal;
    @BindView(R.id.iv_zhengm)
    ImageView ivZhengm;
    @BindView(R.id.iv_fanm)
    ImageView ivFanm;
    private Handler handler;
    private UploadManager uploadManager;
    private String upToken;
    private ArrayList<String> upimg_key_list;
    private static final String serverPath = "http://img.rianbow.cn/";
    private List<String> imgList = new ArrayList<>();
    String access_token = "24.5f32ded24e2e71d9896ba936d0f021f0.2592000.1612421054.282335-23489488";

    @Override
    protected int getResId() {
        return R.layout.activity_realname;
    }

    private MLCnIcrCapture.CallBack idCallback = new MLCnIcrCapture.CallBack() {
        @Override
        public void onSuccess(MLCnIcrCaptureResult idCardResult) {
            // 识别成功处理。
            Log.d("xxx", idCardResult.name);
            Log.d("xxx", "识别成功");

        }

        @Override
        public void onCanceled() {
            // 用户取消处理。
            Log.d("xxx", "取消识别");

        }

        // 识别不到任何文字信息或识别过程发生系统异常的回调方法。
        // retCode：错误码。
        // bitmap：检测失败的身份证图片。
        @Override
        public void onFailure(int retCode, Bitmap bitmap) {
            // 识别异常处理。

            Log.d("xxx", "识别错误，错误码为"+retCode+"错误图片为"+bitmap.toString());

        }

        @Override
        public void onDenied() {
            // 相机不支持等场景处理。
            Log.d("xxx", "相机不支持此操作");

        }
    };

    @Override
    protected void getData() {
        StatusBarUtil.setGradientColor(RealnameActivity.this, toolbar);
        StatusBarUtil.setDarkMode(RealnameActivity.this);

        ivBack.setOnClickListener(this);
        btConfirm.setOnClickListener(this);
        rlZhengmian.setOnClickListener(this);
        rlFanmian.setOnClickListener(this);
        ivZhengm.setOnClickListener(this);
        ivFanm.setOnClickListener(this);

        upToken = SPUtil.getInstance().getData(RealnameActivity.this, SPUtil.FILE_NAME, SPUtil.UPTOKEN);


    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.rl_zhengmian:
                onTakePhoto();
                startCaptureActivity(idCallback, true);

                break;
            case R.id.rl_fanmian:
                onTakePhoto();
                startCaptureActivity(idCallback, false);

                break;
            case R.id.bt_confirm:
                String name = etName.getText().toString();
                String idcard = etIdcard.getText().toString();

                break;
        }
    }

    private void startCaptureActivity(MLCnIcrCapture.CallBack callback, boolean isFront) {
        MLCnIcrCaptureConfig config = new MLCnIcrCaptureConfig.Factory()
                // 设置识别身份证的正反面。
                // true：正面。
                // false：反面。
                .setFront(isFront)
                .create();
        MLCnIcrCapture icrCapture = MLCnIcrCaptureFactory.getInstance().getIcrCapture(config);
        icrCapture.capture(callback, this);
    }

    public void getUpimg(String imagePath, int picSize, Handler mHandler) {

        Log.d("xxx", "到这里了");
        upimg_key_list = new ArrayList<String>();
        new Thread() {
            public void run() {
                // 图片上传到七牛 重用 uploadManager。一般地，只需要创建一个 uploadManager 对象
                uploadManager = new UploadManager();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                // 设置名字
                String s = MD5Utils.string2Md5_16(imagePath);
                String key = s + sdf.format(new Date()) + ".jpg";
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
                                    upimg_key_list.add(serverPath + upimg);//将七牛返回图片的文件名添加到list集合中

                                    if (upimg_key_list.size() == picSize) {
                                        Bundle bundle = new Bundle();
                                        bundle.putStringArrayList("resultImagePath", upimg_key_list);
                                        Message message = new Message();
                                        message.what = 1;
                                        message.setData(bundle);
                                        handler.sendMessage(message);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Log.d("xxx", e.getMessage());
                                }
                            }
                        }, null);
            }
        }.start();
    }

    //开启相机相册动态权限
    public void onTakePhoto() {
        if (Build.VERSION.SDK_INT >= 23) {
            int request = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
            if (request != PackageManager.PERMISSION_GRANTED)//缺少权限，进行权限申请
            {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 123);
                return;//
            } else {
                //权限同意，不需要处理,去掉用拍照的方法               Toast.makeText(this,"权限同意",Toast.LENGTH_SHORT).show();
            }
        } else {
            //低于23 不需要特殊处理，去掉用拍照的方法
        }
    }
}
