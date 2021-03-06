package com.YiDian.RainBow.imgroup;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseAvtivity;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.base.Common;
import com.YiDian.RainBow.setup.bean.InsertRealBean;
import com.YiDian.RainBow.utils.NetUtils;
import com.leaf.library.StatusBarUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.CreateGroupCallback;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CreateGroupActivity extends BaseAvtivity implements View.OnClickListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_back)
    LinearLayout ivBack;
    @BindView(R.id.rl_creat)
    RelativeLayout rlCreat;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.tv_creat)
    TextView tvCreat;
    private int userid;
    String TAG = "xxx";
    @Override
    protected int getResId() {
        return R.layout.activity_creategroup;
    }

    @Override
    protected void getData() {
        StatusBarUtil.setGradientColor(CreateGroupActivity.this, toolbar);
        StatusBarUtil.setDarkMode(CreateGroupActivity.this);

        userid = Integer.valueOf(Common.getUserId());

        ivBack.setOnClickListener(this);
        rlCreat.setOnClickListener(this);
        //?????????????????????
        etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s) && s.length() > 0) {
                    tvCreat.setTextColor(getResources().getColor(R.color.color_8867E7));
                    rlCreat.setEnabled(true);
                } else {
                    tvCreat.setTextColor(getResources().getColor(R.color.color_999999));
                    rlCreat.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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
            case R.id.rl_creat:
                String groupname = etName.getText().toString();
                Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.mipmap.headimg3,null);//????????????????????????bitmap

                File file = getFile(bitmap);
                //????????????????????????
                JMessageClient.createPublicGroup(groupname, "??????????????????", file, "jpg", new CreateGroupCallback() {
                    @Override
                    public void gotResult(int i, String s, long l) {
                        if (i==0){
                            Log.d(TAG, "??????????????????"+"??????id???"+l);
                            NetUtils.getInstance().getApis()
                                    .doCreatGroup(userid,groupname)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Observer<InsertRealBean>() {
                                        @Override
                                        public void onSubscribe(Disposable d) {

                                        }

                                        @Override
                                        public void onNext(InsertRealBean insertRealBean) {
                                            String msg = insertRealBean.getMsg();
                                            if (msg.equals("????????????")){
                                                Toast.makeText(CreateGroupActivity.this, "??????????????? ??????????????????", Toast.LENGTH_SHORT).show();
                                            }else{
                                                Toast.makeText(CreateGroupActivity.this, "??????????????????", Toast.LENGTH_SHORT).show();
                                                finish();
                                            }
                                        }
                                        @Override
                                        public void onError(Throwable e) {

                                        }

                                        @Override
                                        public void onComplete() {

                                        }
                                    });

                        }else{
                            Log.d(TAG, "????????????"+i+"?????????"+s);
                        }
                    }
                });
                break;
        }
    }
    //??????????????????????????????   ????????????????????????????????????...
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
}
