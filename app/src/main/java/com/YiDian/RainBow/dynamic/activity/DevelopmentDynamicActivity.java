package com.YiDian.RainBow.dynamic.activity;

import android.content.Intent;
import android.graphics.Color;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseAvtivity;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.dynamic.bean.SaveAiteBean;
import com.YiDian.RainBow.utils.StringUtil;
import com.leaf.library.StatusBarUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;

//发布动态
public class DevelopmentDynamicActivity extends BaseAvtivity implements View.OnClickListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_back)
    LinearLayout ivBack;
    @BindView(R.id.tv_release)
    TextView tvRelease;
    @BindView(R.id.rl_top)
    RelativeLayout rlTop;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.tv_count)
    TextView tvCount;
    @BindView(R.id.rl_content)
    RelativeLayout rlContent;
    @BindView(R.id.rc_pro_img)
    RecyclerView rcProImg;
    @BindView(R.id.rl_selectedimg)
    RelativeLayout rlSelectedimg;
    @BindView(R.id.rc_hotHuati)
    RecyclerView rcHotHuati;
    @BindView(R.id.tv_mylocation)
    TextView tvMylocation;
    @BindView(R.id.ll_my_location)
    LinearLayout llMyLocation;
    @BindView(R.id.rl_aiteFriend)
    RelativeLayout rlAiteFriend;
    @BindView(R.id.rl_developHuati)
    RelativeLayout rlDevelopHuati;
    @BindView(R.id.ll_whocansee)
    LinearLayout llWhocansee;

    @Override
    protected int getResId() {
        return R.layout.activity_development_dynamic;
    }

    @Override
    protected void getData() {
        //设置状态栏颜色 跟状态栏字体颜色
        StatusBarUtil.setGradientColor(DevelopmentDynamicActivity.this, toolbar);
        StatusBarUtil.setDarkMode(DevelopmentDynamicActivity.this);

        //绑定单击事件
        tvRelease.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        rlSelectedimg.setOnClickListener(this);
        llMyLocation.setOnClickListener(this);
        rlAiteFriend.setOnClickListener(this);
        rlDevelopHuati.setOnClickListener(this);
        llWhocansee.setOnClickListener(this);

        //初次进入内容为空 不能发布动态
        tvRelease.setBackground(this.getDrawable(R.drawable.nine_radious_gray));
        tvRelease.setClickable(false);
        //文本监听
        etContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //输入完后获取当前文本长度 给右下角文本长度赋值
                int length = etContent.getText().length();
                //判断输入字符串长度 大于0有内容可以发布 等于0不能发布
                if (length > 0) {
                    tvRelease.setBackground(DevelopmentDynamicActivity.this.getDrawable(R.drawable.background_gradient_nine_radious));
                    tvRelease.setClickable(true);
                } else {
                    tvRelease.setBackground(DevelopmentDynamicActivity.this.getDrawable(R.drawable.nine_radious_gray));
                    tvRelease.setClickable(false);
                }
                //输入完毕后获取剩余可输入长度 并展示
                tvCount.setText(100 - length + "");



            }
        });

    }
    //获取选择的好友
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getAiteFriend(SaveAiteBean bean){
        String substring = bean.getString();

        //获取当前光标所在位置
        int index = etContent.getSelectionStart();
        Editable editable = etContent.getEditableText();
        //插入到光标所在位置
        editable.insert(index," @"+substring);


    }
    @Override
    protected void onStart() {
        super.onStart();
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(EventBus.getDefault().isRegistered(this)){
           EventBus.getDefault().unregister(this);
        }
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //返回
            case R.id.iv_back:
                finish();
                // TODO: 2020/11/21 0021 判断是否有内容可以保存到草稿箱 有内容保存 无内容直接退出
                break;
                //发布
            case R.id.tv_release:
                // TODO: 2020/11/21 0021 获取内容 判断发布类型  1纯文本 2纯图片 3纯视频  21文本加图片 31文本加视频


                break;
                //选择图片或视频
            case R.id.rl_selectedimg:
                // TODO: 2020/11/21 0021 弹出选择图片或视频弹出框


                break;
                //获取我的当前位置
            case R.id.ll_my_location:

                break;
                //@好友
            case R.id.rl_aiteFriend:
                //跳转到选择好友列表页
                startActivity(new Intent(this,SelectFriendActivity.class));

                break;
                //发表话题
            case R.id.rl_developHuati:

                //获取当前光标位置
                int index = etContent.getSelectionStart();

                //插入到光标所在位置
                Editable editable = etContent.getEditableText();
                editable.insert(index,"##");

                //设置光标到##中间
                String s = etContent.getText().toString();
                etContent.setSelection(s.length()-1);

                int num = getStringCount(s, "#");

                if(num % 2 ==0){
                    if(s.contains("#") && s!=null){
                        for (int j =0;j<=num;j+=2){

                            int i = s.indexOf("#");

                            int i1 = s.indexOf("#",i+1);
                            Log.e("xxx",i+"  "+i1);

                            SpannableString builder = new SpannableString(s);
                            ForegroundColorSpan redSpan = new ForegroundColorSpan(Color.RED);
                            builder.setSpan(redSpan, i, i1+1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                            etContent.setText(builder);
                        }

                    }
                }

                break;
                //谁可以看
            case R.id.ll_whocansee:

                break;
        }
    }
    //获取一个字符串，查找这个字符串出现的次数;
    public static int getStringCount(String str, String key) {
        int count = 0;
        int index = 0;
        int num = str.indexOf(key);
        while ((index = str.indexOf(key)) != -1) {
            count++;
            str = str.substring(str.indexOf(key) + key.length());
        }
        return count;
    }
}
