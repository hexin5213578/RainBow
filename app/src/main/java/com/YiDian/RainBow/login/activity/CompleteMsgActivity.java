package com.YiDian.RainBow.login.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseAvtivity;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.custom.customradio.RadioGroupEx;
import com.YiDian.RainBow.main.activity.MainActivity;
import com.YiDian.RainBow.utils.BasisTimesUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

import org.lym.image.select.PictureSelector;

import java.util.List;

import butterknife.BindView;

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

    @Override
    protected int getResId() {
        return R.layout.activity_complete_msg;
    }

    @Override
    protected void getData() {
        //申请开启内存卡权限
        if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) && (CompleteMsgActivity.this.checkSelfPermission
                (Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
            //请求权限
            CompleteMsgActivity.this.requestPermissions(new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        Glide.with(CompleteMsgActivity.this).load(R.mipmap.headimg).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(ivHeadimg);
        tvBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BasisTimesUtils.showDatePickerDialog(CompleteMsgActivity.this, "请选择年月日", 2015, 1, 1, new BasisTimesUtils.OnDatePickerListener() {
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
                Toast.makeText(this, "" + str, Toast.LENGTH_SHORT).show();
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
                String s = paths.get(0);
                Glide.with(CompleteMsgActivity.this).load(s).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(ivHeadimg);
            }
        }
    }
}
