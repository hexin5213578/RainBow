package com.YiDian.RainBow.main.fragment.mine.activity;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextUtils;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseAvtivity;
import com.YiDian.RainBow.base.BasePresenter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.leaf.library.StatusBarUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;

//编辑资料
public class EditMsgActivity extends BaseAvtivity implements View.OnClickListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_back)
    LinearLayout ivBack;
    @BindView(R.id.iv_headimg)
    ImageView ivHeadimg;
    @BindView(R.id.rl_change_headimg)
    RelativeLayout rlChangeHeadimg;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.rl_name)
    RelativeLayout rlName;
    @BindView(R.id.tv_age)
    TextView tvAge;
    @BindView(R.id.rl_age)
    RelativeLayout rlAge;
    @BindView(R.id.tv_qianming)
    TextView tvQianming;
    @BindView(R.id.rl_qianming)
    RelativeLayout rlQianming;
    @BindView(R.id.tv_myrole)
    TextView tvMyrole;
    @BindView(R.id.rl_myrole)
    RelativeLayout rlMyrole;
    @BindView(R.id.tv_mylikerole)
    TextView tvMylikerole;
    @BindView(R.id.rl_mylikerole)
    RelativeLayout rlMylikerole;
    @BindView(R.id.tv_mystate)
    TextView tvMystate;
    @BindView(R.id.rl_ganqingstate)
    RelativeLayout rlGanqingstate;
    @BindView(R.id.tv_mylable)
    TextView tvMylable;
    @BindView(R.id.rl_mylable)
    RelativeLayout rlMylable;
    private PopupWindow mPopupWindow;
    private PopupWindow mPopupWindow1;

    @Override
    protected int getResId() {
        return R.layout.activity_editmsg;
    }

    @Override
    protected void getData() {
        //设置状态栏颜色及字体颜色
        StatusBarUtil.setGradientColor(this, toolbar);
        StatusBarUtil.setDarkMode(this);

        rlChangeHeadimg.setOnClickListener(this);
        rlName.setOnClickListener(this);
        rlAge.setOnClickListener(this);
        rlQianming.setOnClickListener(this);
        rlMyrole.setOnClickListener(this);
        rlMylikerole.setOnClickListener(this);
        rlGanqingstate.setOnClickListener(this);
        rlMylable.setOnClickListener(this);
        ivBack.setOnClickListener(this);

        // TODO: 2020/11/26 0026 获取当前用户个人信息展示

        //加载圆角图
        Glide.with(this).load(R.mipmap.headimg).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(ivHeadimg);


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
            //更换头像
            case R.id.rl_change_headimg:

                break;
            //更换昵称
            case R.id.rl_name:
                showChangeName();
                break;
            //年龄
            case R.id.rl_age:

                break;
            //个性签名
            case R.id.rl_qianming:
                //展示自定义弹出框
                showChangeQianming();
                break;
            //我的角色
            case R.id.rl_myrole:

                break;
            //我喜欢的角色
            case R.id.rl_mylikerole:

                break;
            //我的感情状态
            case R.id.rl_ganqingstate:

                break;
            //我的标签
            case R.id.rl_mylable:

                break;
        }
    }

    /**
     * 禁止EditText输入空格
     *
     * @param editText
     */
    public static void setEditTextInhibitInputSpace(EditText editText) {
        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (source.equals(" ")) return "";
                else return null;
            }
        };
        editText.setFilters(new InputFilter[]{filter});
    }

    /**
     * 禁止EditText输入特殊字符
     *
     * @param editText
     */
    public static void setEditTextInhibitInputSpeChat(EditText editText) {

        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                String speChat = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
                Pattern pattern = Pattern.compile(speChat);
                Matcher matcher = pattern.matcher(source.toString());
                if (matcher.find()) return "";
                else return null;
            }
        };
        editText.setFilters(new InputFilter[]{filter});
    }

    //弹出编辑个性签名
    public void showChangeQianming() {
        //创建popwiondow弹出框
        mPopupWindow = new PopupWindow();
        mPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_qianming, null);

        TextView cancle = view.findViewById(R.id.tv_cancle);
        TextView confrim = view.findViewById(R.id.tv_confirm);
        TextView count = view.findViewById(R.id.tv_count);
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
                // TODO: 2020/11/26 0026  调用更换签名的接口
                String str = text.getText().toString();


                tvQianming.setText(str);
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

                count.setText(30 - length + "");

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
        if (Build.VERSION.SDK_INT < 11) {
            return;
        }
        final Window window = this.getWindow();
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

    // 弹出修改昵称弹出框
    public void showChangeName() {
        //创建popwiondow弹出框
        mPopupWindow1 = new PopupWindow();
        mPopupWindow1.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow1.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_changename, null);
        EditText et_name = view.findViewById(R.id.et_name);
        TextView tv_rem = view.findViewById(R.id.tv_Rem);

        //将用户名 回显到输入框
        et_name.setText(tvName.getText().toString());

        //禁止输入特殊字符及空格
        setEditTextInhibitInputSpace(et_name);
        setEditTextInhibitInputSpeChat(et_name);

        //文本输入前 获取长度赋值给长度计算
        int length = et_name.getText().length();
        tv_rem.setText(length + "/10");
        //输入框监听器
        et_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //文本输入结束后 获取长度赋值给长度计算
                int length = et_name.getText().length();
                tv_rem.setText(length + "/10");
            }
        });
        //取消
        view.findViewById(R.id.tv_cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss1();
            }
        });
        view.findViewById(R.id.tv_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取输入框文本  判空发起更换昵称的网络请求  请求成功关闭弹出框
                String s = et_name.getText().toString();
                if (!TextUtils.isEmpty(s)) {
                    // TODO: 2020/10/28 0028 调用修改昵称的接口


                } else {
                    Toast.makeText(EditMsgActivity.this, "昵称不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //popwindow设置属性
        mPopupWindow1.setContentView(view);
        mPopupWindow1.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow1.setFocusable(true);
        mPopupWindow1.setOutsideTouchable(true);
        mPopupWindow1.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setWindowAlpa(false);
            }
        });
        show1(view);
    }

    /**
     * 显示PopupWindow
     */
    private void show1(View v) {
        if (mPopupWindow1 != null && !mPopupWindow1.isShowing()) {
            mPopupWindow1.showAtLocation(v, Gravity.CENTER, 0, 0);
        }
        setWindowAlpa(true);

    }

    /**
     * 消失PopupWindow
     */
    public void dismiss1() {
        if (mPopupWindow1 != null && mPopupWindow1.isShowing()) {
            mPopupWindow1.dismiss();
        }
    }


}