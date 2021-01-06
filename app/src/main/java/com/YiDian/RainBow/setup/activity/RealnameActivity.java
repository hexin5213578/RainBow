package com.YiDian.RainBow.setup.activity;

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
import com.huawei.hms.mlplugin.card.icr.cn.MLCnIcrCapture;
import com.huawei.hms.mlplugin.card.icr.cn.MLCnIcrCaptureConfig;
import com.huawei.hms.mlplugin.card.icr.cn.MLCnIcrCaptureFactory;
import com.huawei.hms.mlplugin.card.icr.cn.MLCnIcrCaptureResult;
import com.leaf.library.StatusBarUtil;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

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
    @BindView(R.id.bt_reconfirm)
    Button btReconfirm;
    private Handler handler;
    private UploadManager uploadManager;
    private String upToken;
    private static final String serverPath = "http://img.rianbow.cn/";
    String Username;
    String num;
    private File file1;
    private File file2;
    private String img1;
    private String img2;
    private int userid;

    @Override
    protected int getResId() {
        return R.layout.activity_realname;
    }

    private MLCnIcrCapture.CallBack idCallback = new MLCnIcrCapture.CallBack() {
        @Override
        public void onSuccess(MLCnIcrCaptureResult idCardResult) {
            // 识别成功处理。
            if (idCardResult.sideType == 1) {
                ivZhengm.setVisibility(View.VISIBLE);
                rlZhengmian.setVisibility(View.GONE);

                ivZhengm.setImageBitmap(idCardResult.cardBitmap);

                file1 = getFile(idCardResult.cardBitmap);

                Username = idCardResult.name;
                num = idCardResult.idNum;

                Log.d("xxx",idCardResult.idNum);
                // 图片上传到七牛 重用 uploadManager。一般地，只需要创建一个 uploadManager 对象
                uploadManager = new UploadManager();
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
            } else if (idCardResult.sideType == 2) {
                ivFanm.setVisibility(View.VISIBLE);
                rlFanmian.setVisibility(View.GONE);
                ivFanm.setImageBitmap(idCardResult.cardBitmap);

                file2 = getFile(idCardResult.cardBitmap);

                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                // 设置名字
                String s = MD5Utils.string2Md5_16(file2.getAbsolutePath());
                String key = s + sdf.format(new Date()) + ".jpg";
                uploadManager.put(file2, key, upToken,
                        new UpCompletionHandler() {
                            @Override
                            public void complete(String key, ResponseInfo info, JSONObject res) {
                                //res 包含 hash、key 等信息，具体字段取决于上传策略的设置
                                if (info.isOK()) {
                                    // 七牛返回的文件名
                                    try {
                                        String upimg = res.getString("key");
                                        //将七牛返回图片的文件名添加到list集合中
                                        img2 = serverPath + upimg;
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    Log.i("xxx", "Upload Fail");
                                    //如果失败，这里可以把 info 信息上报自己的服务器，便于后面分析上传错误原因
                                }
                                Log.i("xxx", img2);
                            }
                        }, null);
            }
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

            Log.d("xxx", "识别错误，错误码为" + retCode + "错误图片为" + bitmap.toString());

        }

        @Override
        public void onDenied() {
            // 相机不支持等场景处理。
            Toast.makeText(RealnameActivity.this, "相机不支持此操作", Toast.LENGTH_SHORT).show();
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
        btReconfirm.setOnClickListener(this);
        userid = Integer.valueOf(Common.getUserId());
        upToken = SPUtil.getInstance().getData(RealnameActivity.this, SPUtil.FILE_NAME, SPUtil.UPTOKEN);

        //获取状态
        doGetRealStatus();
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }
    public void doGetRealStatus(){
        //先判断是否认证过
        NetUtils.getInstance().getApis()
                .doGetRealMsg(userid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GetRealDataBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(GetRealDataBean getRealDataBean) {
                        String msg = getRealDataBean.getMsg();
                        if (msg.equals("您还没有提交实名信息")) {
                            rlConfirm.setVisibility(View.VISIBLE);
                            rlIsreal.setVisibility(View.GONE);
                        } else {

                            rlConfirm.setVisibility(View.GONE);
                            rlIsreal.setVisibility(View.VISIBLE);
                            btReconfirm.setVisibility(View.GONE);

                            GetRealDataBean.ObjectBean bean = getRealDataBean.getObject();

                            int auditStatus = bean.getAuditStatus();
                            tvName.setText(bean.getUserName());
                            tvIdcard.setText(bean.getIdNum());
                            if (auditStatus == 2) {
                                tvStatus.setText("您的提交正在审核中");
                            }
                            if (auditStatus == 1) {
                                tvStatus.setText("你已通过实名认证");
                            }
                            if (auditStatus == 0) {
                                tvStatus.setText("您的提交审核失败");
                                btReconfirm.setVisibility(View.VISIBLE);
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
                if (!TextUtils.isEmpty(img1)) {
                    if (!TextUtils.isEmpty(img2)) {
                        if (!TextUtils.isEmpty(name)) {
                            if (StringUtil.checkIdCard(idcard)) {
                                if (name.equals(Username)) {
                                    if (idcard.equals(num)) {
                                        NetUtils.getInstance().getApis()
                                                .doInsertReal(idcard,userid,name,img1,img2)
                                                .subscribeOn(Schedulers.io())
                                                .observeOn(AndroidSchedulers.mainThread())
                                                .subscribe(new Observer<InsertRealBean>() {
                                                    @Override
                                                    public void onSubscribe(Disposable d) {

                                                    }

                                                    @Override
                                                    public void onNext(InsertRealBean insertRealBean) {
                                                        String str = insertRealBean.getObject();
                                                        if(str.equals("新增成功")){
                                                            //上传成功 获取审核状态
                                                            doGetRealStatus();
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
                                        Toast.makeText(this, "输入的身份证号与身份证信息不一致", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(this, "输入的姓名与身份证信息不一致", Toast.LENGTH_SHORT).show();
                                }
                            }
                        } else {
                            Toast.makeText(this, "请输入您的真实姓名", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "请先上传身份证反面照", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "请先上传身份证正面照", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.bt_reconfirm:
                rlConfirm.setVisibility(View.VISIBLE);
                rlIsreal.setVisibility(View.GONE);
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

    //在这里抽取了一个方法   可以封装到自己的工具类中...
    public File getFile(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        File file = new File(Environment.getExternalStorageDirectory() + "/temp.jpg");
        try {
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            InputStream is = new ByteArrayInputStream(baos.toByteArray());
            int x = 0;
            byte[] b = new byte[1024 * 100];
            while ((x = is.read(b)) != -1) {
                fos.write(b, 0, x);
            }
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
