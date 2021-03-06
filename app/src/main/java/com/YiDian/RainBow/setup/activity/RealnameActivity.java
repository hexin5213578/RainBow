package com.YiDian.RainBow.setup.activity;

import android.Manifest;
import android.content.ContextWrapper;
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
            // ?????????????????????
            if (idCardResult.sideType == 1) {
                ivZhengm.setVisibility(View.VISIBLE);
                rlZhengmian.setVisibility(View.GONE);

                ivZhengm.setImageBitmap(idCardResult.cardBitmap);

                file1 = getFile(idCardResult.cardBitmap);



                Username = idCardResult.name;
                num = idCardResult.idNum;

                Log.d("xxx",idCardResult.idNum);
                // ????????????????????? ?????? uploadManager???????????????????????????????????? uploadManager ??????
                uploadManager = new UploadManager();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                // ????????????
                String s = MD5Utils.string2Md5_16(file1.getAbsolutePath());
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
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    Log.i("xxx", "Upload Fail");
                                    //?????????????????????????????? info ?????????????????????????????????????????????????????????????????????
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
                // ????????????
                String s = MD5Utils.string2Md5_16(file2.getAbsolutePath());
                String key = s + sdf.format(new Date()) + ".jpg";
                uploadManager.put(file2, key, upToken,
                        new UpCompletionHandler() {
                            @Override
                            public void complete(String key, ResponseInfo info, JSONObject res) {
                                //res ?????? hash???key ??????????????????????????????????????????????????????
                                if (info.isOK()) {
                                    // ????????????????????????
                                    try {
                                        String upimg = res.getString("key");
                                        //??????????????????????????????????????????list?????????
                                        img2 = serverPath + upimg;
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    Log.i("xxx", "Upload Fail");
                                    //?????????????????????????????? info ?????????????????????????????????????????????????????????????????????
                                }
                                Log.i("xxx", img2);
                            }
                        }, null);
            }
        }

        @Override
        public void onCanceled() {
            // ?????????????????????
            Log.d("xxx", "????????????");

        }

        // ?????????????????????????????????????????????????????????????????????????????????
        // retCode???????????????
        // bitmap????????????????????????????????????
        @Override
        public void onFailure(int retCode, Bitmap bitmap) {
            // ?????????????????????

            Log.d("xxx", "???????????????????????????" + retCode + "???????????????" + bitmap.toString());

        }

        @Override
        public void onDenied() {
            // ?????????????????????????????????
            Toast.makeText(RealnameActivity.this, "????????????????????????", Toast.LENGTH_SHORT).show();
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
        upToken = Common.getToken();

        //????????????
        doGetRealStatus();
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }
    public void doGetRealStatus(){
        //????????????????????????
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
                        if (msg.equals("??????????????????????????????")) {
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
                                tvStatus.setText("???????????????????????????");
                            }
                            if (auditStatus == 1) {
                                tvStatus.setText("????????????????????????");
                            }
                            if (auditStatus == 0) {
                                tvStatus.setText("????????????????????????");
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
                                                        if(str.equals("????????????")){
                                                            //???????????? ??????????????????
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
                                        Toast.makeText(this, "????????????????????????????????????????????????", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(this, "??????????????????????????????????????????", Toast.LENGTH_SHORT).show();
                                }
                            }
                        } else {
                            Toast.makeText(this, "???????????????????????????", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "??????????????????????????????", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "??????????????????????????????", Toast.LENGTH_SHORT).show();
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
                // ????????????????????????????????????
                // true????????????
                // false????????????
                .setFront(isFront)
                .create();
        MLCnIcrCapture icrCapture = MLCnIcrCaptureFactory.getInstance().getIcrCapture(config);
        icrCapture.capture(callback, this);
    }

    //??????????????????????????????
    public void onTakePhoto() {
        if (Build.VERSION.SDK_INT >= 23) {
            int request = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
            if (request != PackageManager.PERMISSION_GRANTED)//?????????????????????????????????
            {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 123);
                return;//
            } else {
                //??????????????????????????????,????????????????????????               Toast.makeText(this,"????????????",Toast.LENGTH_SHORT).show();
            }
        } else {
            //??????23 ????????????????????????????????????????????????
        }
    }

    //??????????????????????????????   ????????????????????????????????????...
    public File getFile(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);

        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getExternalFilesDir(Environment.DIRECTORY_MUSIC);
        File file = new File(directory+"/temp.jpg");

        if (file.exists()){
            file.delete();
        }else{
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
