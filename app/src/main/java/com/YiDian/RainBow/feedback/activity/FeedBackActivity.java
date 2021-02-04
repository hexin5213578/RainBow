package com.YiDian.RainBow.feedback.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.App;
import com.YiDian.RainBow.base.BaseAvtivity;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.custom.loading.CustomDialog;
import com.YiDian.RainBow.feedback.adapter.FeedBackImgAdapter;
import com.YiDian.RainBow.main.fragment.mine.bean.ChackBuildLovesBean;
import com.YiDian.RainBow.utils.NetUtils;
import com.YiDian.RainBow.utils.StringUtil;
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
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

//问题反馈
public class FeedBackActivity extends BaseAvtivity implements View.OnClickListener {
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
    @BindView(R.id.iv_back)
    LinearLayout ivBack;
    private List<String> paths;
    private FeedBackImgAdapter feedBackImgAdapter;
    private GridLayoutManager gridLayoutManager;
    private ArrayList<Media> select;
    private CustomDialog dialog;

    @Override
    protected int getResId() {
        return R.layout.activity_feedback;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void getData() {
        dialog = new CustomDialog(this, "正在登录...");

        ivBack.setOnClickListener(this);
        btSubmit.setOnClickListener(this);
        rlSelectedimg.setOnClickListener(this);
        StatusBarUtil.setGradientColor(FeedBackActivity.this, toolbar);
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
            case R.id.iv_back:
                finish();
                break;
            case R.id.bt_submit:
                String str = etPro.getText().toString();

                String phone = etContact.getText().toString();
                // TODO: 2020/10/6 0006 携带提出的问题图片 描述 电话/邮箱 提交到服务器
                if (paths!=null && paths.size()==0 || TextUtils.isEmpty(str)){
                    Toast.makeText(this, "请先描述问题", Toast.LENGTH_SHORT).show();
                }else{
                    if (TextUtils.isEmpty(phone)) {
                        Toast.makeText(App.getContext(), "请输入您的联系方式", Toast.LENGTH_SHORT).show();
                    }else{
                        dialog.show();
                        NetUtils.getInstance().getApis()
                                .doInsertFeedBack("12315646546541321")
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Observer<ChackBuildLovesBean>() {
                                    @Override
                                    public void onSubscribe(Disposable d) {

                                    }

                                    @Override
                                    public void onNext(ChackBuildLovesBean chackBuildLovesBean) {
                                        dialog.dismiss();
                                        if (chackBuildLovesBean.getObject().equals("提交成功")){
                                            Toast.makeText(FeedBackActivity.this, "问题反馈成功", Toast.LENGTH_SHORT).show();
                                            finish();
                                        }else{
                                            Toast.makeText(FeedBackActivity.this, "问题反馈失败,请重新提交", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        dialog.dismiss();
                                    }

                                    @Override
                                    public void onComplete() {

                                    }
                                });
                    }

                }
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
                    if (paths.size() <= 4) {
                        //设置图片适配器 展示问题图片
                        gridLayoutManager = new GridLayoutManager(FeedBackActivity.this, paths.size());
                        rcProImg.setLayoutManager(gridLayoutManager);
                        feedBackImgAdapter = new FeedBackImgAdapter(FeedBackActivity.this, paths);
                        rcProImg.setAdapter(feedBackImgAdapter);
                    }
                    if (paths.size() == 4) {
                        rlSelectedimg.setVisibility(View.GONE);
                    }
                    Log.d("xxx", paths.size() + "");
                }
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
