package com.YiDian.RainBow.feedback;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseAvtivity;
import com.YiDian.RainBow.base.BasePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FeedBackActivity extends BaseAvtivity implements View.OnClickListener {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.et_pro)
    EditText etPro;
    @BindView(R.id.rc_pro_img)
    RecyclerView rcProImg;
    @BindView(R.id.rl_selectedimg)
    RelativeLayout rlSelectedimg;
    @BindView(R.id.tv_selectedimg)
    TextView tvSelectedimg;
    @BindView(R.id.et_contact)
    EditText etContact;
    @BindView(R.id.bt_submit)
    Button btSubmit;

    @Override
    protected int getResId() {
        return R.layout.activity_feedback;
    }

    @Override
    protected void getData() {
        back.setOnClickListener(this);
        btSubmit.setOnClickListener(this);
        rlSelectedimg.setOnClickListener(this);
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.bt_submit:
                // TODO: 2020/10/6 0006 携带提出的问题图片提交到服务器
                break;
            case R.id.rl_selectedimg:
                //调用图片选择器选择多张图片

                break;
        }
    }
}
