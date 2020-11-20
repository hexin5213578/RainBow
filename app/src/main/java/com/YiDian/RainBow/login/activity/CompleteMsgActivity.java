package com.YiDian.RainBow.login.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseAvtivity;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.base.Common;
import com.YiDian.RainBow.login.bean.ComPleteMsgBean;
import com.YiDian.RainBow.main.activity.MainActivity;
import com.YiDian.RainBow.utils.BasisTimesUtils;
import com.YiDian.RainBow.utils.NetUtils;
import com.YiDian.RainBow.utils.SPUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.leaf.library.StatusBarUtil;

import org.lym.image.select.PictureSelector;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

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

        //申请开启内存卡权限
        if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) && (CompleteMsgActivity.this.checkSelfPermission
                (Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
            //请求权限
            CompleteMsgActivity.this.requestPermissions(new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
        path = SPUtil.getInstance().getData(this, SPUtil.FILE_NAME, SPUtil.HEAD_IMG);
        if(path!=null){
            Glide.with(CompleteMsgActivity.this).load(path).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(ivHeadimg);
        }
        String data1 = SPUtil.getInstance().getData(this, SPUtil.FILE_NAME, SPUtil.USER_NAME);
        if(data1!=null){
            etName.setText(data1);
        }
        tvBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BasisTimesUtils.showDatePickerDialog(CompleteMsgActivity.this, "请选择年月日", 1998, 1, 1, new BasisTimesUtils.OnDatePickerListener() {
                    @Override
                    public void onConfirm(int year, int month, int dayOfMonth) {
                        tvBirth.setText(year + "-" + month + "-" + dayOfMonth);
                    }

                    @Override
                    public void onCancel() {

                    }
                });
            }
        });
        tvJumpMain.setOnClickListener(this);
        btconfirm.setOnClickListener(this);
        ivHeadimg.setOnClickListener(this);
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
                int userId = Integer.valueOf(Common.getUserId());
                if(!TextUtils.isEmpty(str)){
                    if(!TextUtils.isEmpty(name)){
                        if(str.equals("保密")){
                            str="";
                        }
                        NetUtils.getInstance().getApis().doComPleteMsg(name,path,birth,str,0,userId)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Observer<ComPleteMsgBean>() {
                                    @Override
                                    public void onSubscribe(Disposable d) {

                                    }

                                    @Override
                                    public void onNext(ComPleteMsgBean comPleteMsgBean) {
                                        if (comPleteMsgBean.getType().equals("OK")){
                                            startActivity(new Intent(CompleteMsgActivity.this, MainActivity.class));

                                            SPUtil.getInstance().saveData(CompleteMsgActivity.this, SPUtil.FILE_NAME, SPUtil.IS_PERFECT, "0");
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
            }
        }
    }
}
