package com.YiDian.RainBow.main.fragment;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseFragment;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.custom.customrecycle.SpacesItemDecoration;
import com.YiDian.RainBow.main.fragment.mine.adapter.HobbyAdapter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.leaf.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class FragmentMine extends BaseFragment implements View.OnClickListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_QrCode)
    ImageView ivQrCode;
    @BindView(R.id.ll_my_money)
    LinearLayout llMyMoney;
    @BindView(R.id.ll_certification)
    LinearLayout llCertification;
    @BindView(R.id.iv_headimg)
    ImageView ivHeadimg;
    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.iv_type)
    ImageView ivType;
    @BindView(R.id.tv_age)
    TextView tvAge;
    @BindView(R.id.tv_userId)
    TextView tvUserId;
    @BindView(R.id.tv_copy)
    TextView tvCopy;
    @BindView(R.id.tv_count_great)
    TextView tvCountGreat;
    @BindView(R.id.ll_great)
    LinearLayout llGreat;
    @BindView(R.id.rc_hobby)
    RecyclerView rcHobby;
    @BindView(R.id.tv_count_haoyou)
    TextView tvCountHaoyou;
    @BindView(R.id.ll_haoyou)
    LinearLayout llHaoyou;
    @BindView(R.id.tv_count_fensi)
    TextView tvCountFensi;
    @BindView(R.id.ll_fensi)
    LinearLayout llFensi;
    @BindView(R.id.tv_count_guanzhu)
    TextView tvCountGuanzhu;
    @BindView(R.id.ll_guanzhu)
    LinearLayout llGuanzhu;
    @BindView(R.id.tv_count_qunzu)
    TextView tvCountQunzu;
    @BindView(R.id.ll_qunzu)
    LinearLayout llQunzu;
    @BindView(R.id.tv_signature)
    TextView tvSignature;
    @BindView(R.id.ll_dongtai)
    LinearLayout llDongtai;
    @BindView(R.id.ll_caogaoxiang)
    LinearLayout llCaogaoxiang;
    @BindView(R.id.ll_shoucang)
    LinearLayout llShoucang;
    @BindView(R.id.ll_yaoqing)
    LinearLayout llYaoqing;
    @BindView(R.id.ll_qiandao)
    LinearLayout llJinbi;
    @BindView(R.id.ll_liwu)
    LinearLayout llLiwu;
    @BindView(R.id.ll_saoyisao)
    LinearLayout llSaoyisao;
    @BindView(R.id.ll_shezhi)
    LinearLayout llShezhi;
    int space = 9;
    private PopupWindow mPopupWindow;

    @Override
    protected void getid(View view) {

    }

    @Override
    protected int getResId() {
        return R.layout.home_fragment_mine;
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void getData() {

        //设置状态栏颜色与字体颜色

        StatusBarUtil.setDarkMode(getActivity());

        llMyMoney.setOnClickListener(this);
        ivQrCode.setOnClickListener(this);
        llCertification.setOnClickListener(this);
        llGreat.setOnClickListener(this);
        tvCopy.setOnClickListener(this);
        llHaoyou.setOnClickListener(this);
        llFensi.setOnClickListener(this);
        llGuanzhu.setOnClickListener(this);
        llQunzu.setOnClickListener(this);
        tvSignature.setOnClickListener(this);
        llDongtai.setOnClickListener(this);
        llCaogaoxiang.setOnClickListener(this);
        llShoucang.setOnClickListener(this);
        llYaoqing.setOnClickListener(this);
        llJinbi.setOnClickListener(this);
        llLiwu.setOnClickListener(this);
        llSaoyisao.setOnClickListener(this);
        llShezhi.setOnClickListener(this);

        //加载一张圆角头像
        Glide.with(getContext()).load(R.mipmap.headimg).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(ivHeadimg);

        //数量赋值
        tvCountHaoyou.setText("30");
        tvCountFensi.setText("50");
        tvCountGuanzhu.setText("100");
        tvCountQunzu.setText("5");

        //点赞数赋值
        tvCountGreat.setText("1168");
        //用户名赋值
        tvUsername.setText("何梦洋");
        //ID赋值
        tvUserId.setText("12346789");
        //年龄赋值
        tvAge.setText("35");
        // TODO: 2020/10/8 0008 需更换数据源 s
        //假数据测试爱好
        List<String> list = new ArrayList<>();
        list.add("美食");
        list.add("旅行");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        rcHobby.setLayoutManager(linearLayoutManager);
        //设置适配器
        HobbyAdapter hobbyAdapter = new HobbyAdapter(getContext(), list);
        rcHobby.setAdapter(hobbyAdapter);
        rcHobby.addItemDecoration(new SpacesItemDecoration(space));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //我的金币
            case R.id.ll_my_money:

                break;
                //特权认证
            case R.id.ll_certification:

                break;
                //我的二维码
            case R.id.iv_QrCode:

                break;
                //复制我的ID
            case R.id.tv_copy:
                //获取id
                String id= tvUserId.getText().toString();
                //获取剪贴板管理器：
                ClipboardManager cm = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                // 创建普通字符型ClipData
                ClipData mClipData = ClipData.newPlainText("Label", id);
                // 将ClipData内容放到系统剪贴板里。
                cm.setPrimaryClip(mClipData);
                Toast.makeText(getContext(), "已复制到剪切板", Toast.LENGTH_SHORT).show();
                break;
                //查看点赞详情
            case R.id.ll_great:

                break;
                //好友
            case R.id.ll_haoyou:

                break;
                //粉丝
            case R.id.ll_fensi:

                break;
                //关注
            case R.id.ll_guanzhu:

                break;
                //群组
            case R.id.ll_qunzu:

                break;
                //编辑个性签名
            case R.id.tv_signature:
                //展示自定义弹出框
                //showSelect();
                break;
                //发布的动态
            case R.id.ll_dongtai:

                break;
                //草稿箱
            case R.id.ll_caogaoxiang:

                break;
                //收藏
            case R.id.ll_shoucang:

                break;
                //邀请好友
            case R.id.ll_yaoqing:

                break;
                //去签到
            case R.id.ll_qiandao:

                break;
                //礼物记录
            case R.id.ll_liwu:

                break;
                //扫一扫
            case R.id.ll_saoyisao:


                break;
                //设置
            case R.id.ll_shezhi:

                break;

        }
    }
    //弹出选择规格
    public void showSelect() {
        //创建popwiondow弹出框
        mPopupWindow = new PopupWindow();
        mPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_qianming,null);

        TextView  cancle = view.findViewById(R.id.tv_cancle);
        TextView  confrim = view.findViewById(R.id.tv_confirm);
        TextView  count = view.findViewById(R.id.tv_count);
        EditText text = view.findViewById(R.id.et_text);
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        confrim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //调用更换签名的接口



                //更换成功隐藏弹出框
                dismiss();
            }
        });
        //设置EditText的显示方式为多行文本输入
        text.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        //文本显示的位置在EditText的最上方
        text.setGravity(Gravity.TOP);
        text.setSingleLine(false);
        //输入框文本监听
        text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //输入完后获取当前文本长度 给右下角文本长度赋值
                int length = text.getText().length();

                count.setText(30-length+"");

            }
        });
        //popwindow设置属性
        mPopupWindow.setAnimationStyle(R.style.popwindow_anim_style);
        mPopupWindow.setContentView(view);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setWindowAlpa(false);
            }
        });
        show(view);

    }

    //设置透明度
    public void setWindowAlpa(boolean isopen) {
        if (android.os.Build.VERSION.SDK_INT < 11) {
            return;
        }
        final Window window = getActivity().getWindow();
        final WindowManager.LayoutParams lp = window.getAttributes();
        window.setFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND, WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        ValueAnimator animator;
        if (isopen) {
            animator = ValueAnimator.ofFloat(1.0f, 0.5f);
        } else {
            animator = ValueAnimator.ofFloat(0.5f, 1.0f);
        }
        animator.setDuration(400);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float alpha = (float) animation.getAnimatedValue();
                lp.alpha = alpha;
                window.setAttributes(lp);
            }
        });
        animator.start();
    }

    /**
     * 显示PopupWindow
     */
    private void show(View v) {
        if (mPopupWindow != null && !mPopupWindow.isShowing()) {
            mPopupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
        }
        setWindowAlpa(true);

    }

    /**
     * 消失PopupWindow
     */
    public void dismiss() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
    }
}
