package com.YiDian.RainBow.feedback.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseAvtivity;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.feedback.adapter.FeedBackImgAdapter;
import com.YiDian.RainBow.main.activity.MainActivity;

import org.lym.image.select.PictureSelector;

import java.util.List;

import butterknife.BindView;

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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            Window window = FeedBackActivity.this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(FeedBackActivity.this.getResources().getColor(R.color.white));
        }

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
                // TODO: 2020/10/6 0006 携带提出的问题图片 描述 电话/邮箱 提交到服务器
                break;
            case R.id.rl_selectedimg:
                //调用图片选择器选择多张图片
                //图片多选
                PictureSelector.with(this)
                        .selectSpec()
                        .setOpenCamera()
                        .setMaxSelectImage(6)
                        .startForResult(100);
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
                if(paths.size()!=0 && paths!=null){
                    Log.d("xxx",paths.size()+"");
                    tvSelectedimg.setVisibility(View.GONE);
                    rlSelectedimg.setVisibility(View.GONE);
                    rcProImg.setVisibility(View.VISIBLE);

                    //设置图片适配器 展示问题图片
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(FeedBackActivity.this, 6);
                    rcProImg.setLayoutManager(gridLayoutManager);
                    FeedBackImgAdapter feedBackImgAdapter = new FeedBackImgAdapter(FeedBackActivity.this, paths);
                    rcProImg.setAdapter(feedBackImgAdapter);
                }
            }
        }
    }
}
