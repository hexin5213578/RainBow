package com.YiDian.RainBow.feedback.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseAvtivity;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.feedback.adapter.FeedBackImgAdapter;
import com.YiDian.RainBow.login.activity.CompleteMsgActivity;
import com.YiDian.RainBow.main.activity.MainActivity;
import com.dmcbig.mediapicker.PickerActivity;
import com.dmcbig.mediapicker.PickerConfig;
import com.dmcbig.mediapicker.entity.Media;
import com.leaf.library.StatusBarUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.lym.image.select.PictureSelector;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

//问题反馈
public class FeedBackActivity extends BaseAvtivity implements View.OnClickListener {
    @BindView(R.id.iv_back)
    LinearLayout back;
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
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private List<String> paths;
    private FeedBackImgAdapter feedBackImgAdapter;
    private GridLayoutManager gridLayoutManager;
    private ArrayList<Media> select;

    @Override
    protected int getResId() {
        return R.layout.activity_feedback;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void getData() {
        back.setOnClickListener(this);
        btSubmit.setOnClickListener(this);
        rlSelectedimg.setOnClickListener(this);
        StatusBarUtil.setGradientColor(FeedBackActivity.this,toolbar);
        StatusBarUtil.setDarkMode(FeedBackActivity.this);

        //申请开启内存卡权限
        if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) && (FeedBackActivity.this.checkSelfPermission
                (Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
            //请求权限
            FeedBackActivity.this.requestPermissions(new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.bt_submit:
                // TODO: 2020/10/6 0006 携带提出的问题图片 描述 电话/邮箱 提交到服务器

                //测试视频选择
                //装被选中的文件
                select = new ArrayList<>();
                Intent intent =new Intent(FeedBackActivity.this, PickerActivity.class);
                intent.putExtra(PickerConfig.SELECT_MODE,PickerConfig.PICKER_VIDEO);//设置选择类型，默认是图片和视频可一起选择(非必填参数)
                long maxSize=10485760L;//long long long long类型
                intent.putExtra(PickerConfig.MAX_SELECT_SIZE,maxSize);
                intent.putExtra(PickerConfig.MAX_SELECT_COUNT,9); //最大选择数量，默认40（非必填参数）
                ArrayList<Media> defaultSelect = select;//可以设置默认选中的照片，比如把select刚刚选择的list设置成默认的。
                intent.putExtra(PickerConfig.DEFAULT_SELECTED_LIST,defaultSelect); //可以设置默认选中的照片(非必填参数)
                FeedBackActivity.this.startActivityForResult(intent,200);

                break;
            case R.id.rl_selectedimg:
                //调用图片选择器选择多张图片
                //图片多选
                PictureSelector.with(this)
                        .selectSpec()
                        .setOpenCamera()
                        .setMaxSelectImage(4)
                        .setAuthority("com.YiDian.RainBow.utils.MyFileProvider")
                        .startForResult(100);
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getint(Integer id) {
        feedBackImgAdapter.notifyDataSetChanged();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getString(String str) {
        if (str.equals("展示选择框")) {
            tvSelectedimg.setVisibility(View.VISIBLE);
            rlSelectedimg.setVisibility(View.VISIBLE);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            if (null != data) {
                //图片单选和多选数据都是以ArrayList的字符串数组返回的。
                paths = PictureSelector.obtainPathResult(data);
                if (paths.size() != 0 && paths != null) {
                    tvSelectedimg.setVisibility(View.GONE);
                    rcProImg.setVisibility(View.VISIBLE);
                    if(paths.size()<=4){
                        //设置图片适配器 展示问题图片
                        gridLayoutManager = new GridLayoutManager(FeedBackActivity.this, paths.size());
                        rcProImg.setLayoutManager(gridLayoutManager);
                        feedBackImgAdapter = new FeedBackImgAdapter(FeedBackActivity.this, paths);
                        rcProImg.setAdapter(feedBackImgAdapter);
                    }
                    if(paths.size()==4){
                        rlSelectedimg.setVisibility(View.GONE);
                    }
                    Log.d("xxx", paths.size() + "");
                }
            }
        }
        if(requestCode==200 && resultCode==PickerConfig.RESULT_CODE){
            select=data.getParcelableArrayListExtra(PickerConfig.EXTRA_RESULT);
            Log.i("select","select.size"+select.size());
            for (int i =0;i<select.size();i++){
                Log.e("select",select.get(i).size+" ");
            }
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
