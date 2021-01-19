package com.YiDian.RainBow.main.fragment.mine.activity;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseAvtivity;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.base.Common;
import com.YiDian.RainBow.custom.customDialog.CustomDialogCleanNotice;
import com.YiDian.RainBow.main.fragment.mine.bean.AddSignInBean;
import com.YiDian.RainBow.main.fragment.mine.bean.SigninMsgBean;
import com.YiDian.RainBow.utils.NetUtils;
import com.leaf.library.StatusBarUtil;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
public class EveryDayRegisterActivity extends BaseAvtivity implements View.OnClickListener {


    private static final String TAG = "xxxx";
    @BindView(R.id.tv_days)
    TextView tvDays;
    @BindView(R.id.tv_zhouyi)
    TextView tvZhouyi;
    @BindView(R.id.tv_zhouyi_count)
    TextView tvZhouyiCount;
    @BindView(R.id.rl_zhouyi)
    RelativeLayout rlZhouyi;
    @BindView(R.id.tv_zhouer)
    TextView tvZhouer;
    @BindView(R.id.tv_zhouer_count)
    TextView tvZhouerCount;
    @BindView(R.id.rl_zhouer)
    RelativeLayout rlZhouer;
    @BindView(R.id.tv_zhousan)
    TextView tvZhousan;
    @BindView(R.id.tv_zhousan_count)
    TextView tvZhousanCount;
    @BindView(R.id.rl_zhousan)
    RelativeLayout rlZhousan;
    @BindView(R.id.tv_zhousi)
    TextView tvZhousi;
    @BindView(R.id.tv_zhousi_count)
    TextView tvZhousiCount;
    @BindView(R.id.rl_zhousi)
    RelativeLayout rlZhousi;
    @BindView(R.id.tv_zhouwu)
    TextView tvZhouwu;
    @BindView(R.id.tv_zhouwu_count)
    TextView tvZhouwuCount;
    @BindView(R.id.rl_zhouwu)
    RelativeLayout rlZhouwu;
    @BindView(R.id.tv_zhouliu)
    TextView tvZhouliu;
    @BindView(R.id.tv_zhouliu_count)
    TextView tvZhouliuCount;
    @BindView(R.id.rl_zhouliu)
    RelativeLayout rlZhouliu;
    @BindView(R.id.tv_zhouri)
    TextView tvZhouri;
    @BindView(R.id.tv_zhouri_count)
    TextView tvZhouriCount;
    @BindView(R.id.rl_zhouri)
    RelativeLayout rlZhouri;
    @BindView(R.id.bt_qiandao)
    Button btQiandao;
    @BindView(R.id.rc_task)
    RecyclerView rcTask;
    @BindView(R.id.l_black)
    LinearLayout lBlack;
    Integer userId;
    int nowWeek;

    ArrayList<TextView> list1 = new ArrayList<>();
    ArrayList<View> list2 = new ArrayList<>();
    ArrayList<TextView> awardList = new ArrayList<>();
    private PopupWindow mPopupWindow1;

    /**
     * tv _zhouyi  天数 未签到换成补签
     * rl_zhouyi   每日签到的背景
     * tv_zhouyi count  当天签到金币数
     */
    @Override
    protected int getResId() {
        return R.layout.activity_everydayregister;
    }

    @Override
    protected void getData() {
        //
        //头
        list1.add(tvZhouyi);
        list1.add(tvZhouer);
        list1.add(tvZhousan);
        list1.add(tvZhousi);
        list1.add(tvZhouwu);
        list1.add(tvZhouliu);
        list1.add(tvZhouri);
        //背景
        list2.add(rlZhouyi);
        list2.add(rlZhouer);
        list2.add(rlZhousan);
        list2.add(rlZhousi);
        list2.add(rlZhouwu);
        list2.add(rlZhouliu);
        list2.add(rlZhouri);
        //金币
        awardList.add(tvZhouyiCount);
        awardList.add(tvZhouerCount);
        awardList.add(tvZhousanCount);
        awardList.add(tvZhousiCount);
        awardList.add(tvZhouwuCount);
        awardList.add(tvZhouliuCount);
        awardList.add(tvZhouriCount);
        btQiandao.setOnClickListener(this);
        rlZhouyi.setOnClickListener(this);
        rlZhouer.setOnClickListener(this);
        rlZhousan.setOnClickListener(this);
        rlZhousi.setOnClickListener(this);
        rlZhouwu.setOnClickListener(this);
        rlZhouliu.setOnClickListener(this);
        rlZhouri.setOnClickListener(this);
        lBlack.setOnClickListener(this);

        //设置状态栏透明
        StatusBarUtil.setTransparentForWindow(EveryDayRegisterActivity.this);
        //获取用户id
        userId = Integer.parseInt(Common.getUserId());
        Log.d(TAG, "getData:------>" + userId);
        nowWeek = 7;
//        getWeekbyDate(new Date())
        isSign();
    }
    List<SigninMsgBean.ObjectBean.SignInListBean> signInList = new ArrayList<>();

    //刷新数据 Refresh  init
    public void isSign() {
        //拿到数据
        NetUtils.getInstance().getApis().
                doGetSigninMsg(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SigninMsgBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }
                    @Override
                    public void onNext(SigninMsgBean signinMsgBean) {
                        //设置连续签到天数
                        tvDays.setText(signinMsgBean.getObject().getContinuousDays()+"");
                        //拿到签到信息列表
                        signInList = signinMsgBean.getObject().getSignInList();
                        Log.d(TAG, "isSign: 返回信息条数" + signInList.size());
                        TextView textView;
                        View view1;
                        //遍历数据
                        for (int i = 1; i <= 7; i++) {
                            textView = list1.get(i - 1);
                            view1 = list2.get(i - 1);
                            //设置奖励金币
//                            Log.d(TAG, "onNext: ----------->"+signInList.get(i-1).getAward());
                            awardList.get(i-1).setText("金币 ×"+signInList.get(i-1).getAward());
                            if (i < nowWeek) {
                                //今天之前的操作
                                //判断是否签到设置背景
                                if (signInList.get(i - 1).getIsSign() == 1) {
                                    //禁止签到
                                    view1.setEnabled(false);
                                    //设置为签到
                                    textView.setBackgroundResource(R.drawable.select_qiandao_yiqiandao);
                                    view1.setBackgroundResource(R.drawable.select_qiandao_yiqiandao_bg);
                                } else {
                                    //设置为未签到
                                    textView.setBackgroundResource(R.drawable.select_qiandao_weiqiandao);
                                    view1.setBackgroundResource(R.drawable.select_qiandao_weiqiandao_bg);
                                    //设置补签
                                    textView.setText("点击补签");
                                }
                            } else if (i == nowWeek) {
                                Log.d(TAG, "idSign: 今天的操作=" + i);
                                //设置今天的操作
                                if (signInList.get(i - 1).getIsSign() == 1) {
                                    //设置按钮
                                    btQiandao.setEnabled(false);
                                    btQiandao.setBackgroundResource(R.drawable.background_gradient_yiqiandao);
                                    btQiandao.setText("已签到");
                                    view1.isEnabled();          //不允许点击
                                    //设置为签到
                                    textView.setBackgroundResource(R.drawable.select_qiandao_yiqiandao);
                                    view1.setBackgroundResource(R.drawable.select_qiandao_yiqiandao_bg);

                                } else {
                                    //设置为未签到
                                    btQiandao.setText("未签到");
                                    textView.setBackgroundResource(R.drawable.select_qiandao_weiqiandao);
                                    view1.setBackgroundResource(R.drawable.select_qiandao_weiqiandao_bg);
                                }
                            } else {
                                Log.d(TAG, "idSign: 今天以后的操作=" + i);
                                //设置今天之后的操作
                                view1.setEnabled(false);
                                textView.setBackgroundResource(R.drawable.select_qiandao_weiqiandao);
                                view1.setBackgroundResource(R.drawable.select_qiandao_weiqiandao_bg);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Log.d(TAG, "onError: 网络请求失败");
//                        Toast.makeText(EveryDayRegisterActivity.this, "网络请求失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    //获取当前日期
    public static int getWeekbyDate(Date pTime) {
        Calendar c = Calendar.getInstance();
        c.setTime(pTime);
        int dayForWeek = 0;
        if (c.get(Calendar.DAY_OF_WEEK) == 1) {
            dayForWeek = 7;
        } else {
            dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
        }
        return dayForWeek;
    }
//    public void  singing(){
//        view.setBackgroundResource(R.drawable.select_qiandao_yiqiandao_bg);
//    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }
    //发送签到请求修改服务器数据
    public void sendsign(int day) {
        NetUtils.getInstance().getApis().
                doAddSign(day, userId).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<AddSignInBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: ccccccccccccc");
                    }

                    @Override
                    public void onNext(AddSignInBean addSignInBean) {
                        Log.d(TAG, "onNext: "+day);
                        isSign();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }


    @Override
    public void onClick(View v) {

        //签到与补签事件
        switch (v.getId()) {
            case R.id.bt_qiandao:
                qiandao(nowWeek);
                break;
            case R.id.rl_zhouyi:
                qiandao(1);

                break;
            case R.id.rl_zhouer:
                qiandao(2);
                break;
            case R.id.rl_zhousan:
                qiandao(3);
                break;
            case R.id.rl_zhousi:
                qiandao(4);
                break;
            case R.id.rl_zhouwu:
                qiandao(5);
                break;
            case R.id.rl_zhouliu:
                qiandao(6);
                break;
            case R.id.rl_zhouri:
                qiandao(7);
                break;
            case R.id.l_black:
                Log.d(TAG, "onClick: 返回");
                finish();
                break;
        }
    }

    public void qiandao(int da){
        CustomDialogCleanNotice.Builder builder;
        if(nowWeek<da){
            //补签  拿到补签需要的金币数填充字符串
            String consume = "签到需要1金币";
            builder = new CustomDialogCleanNotice.Builder(EveryDayRegisterActivity.this);
            builder.setMessage(consume).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    sendsign(da);          //签到 补签
                    dialog.dismiss();
                    showChangeName(1,2,3,4);
                }
            });
            builder.setNegativeButton("取消",
                    new android.content.DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            builder.create().show();

        }else {
            builder = new CustomDialogCleanNotice.Builder(EveryDayRegisterActivity.this);
            builder.setMessage("确定签到？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    sendsign(da);          //签到 补签
                    dialog.dismiss();
                }
            });
            builder.setNegativeButton("取消",
                    new android.content.DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            builder.create().show();

        }
    }

    // 弹出修改昵称弹出框   flag=1 签到奖励   flag 补签奖励
    public void showChangeName(int flag,int award,int buqian,int continuousAward) {


        //创建popwiondow弹出框
        mPopupWindow1 = new PopupWindow();
        mPopupWindow1.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow1.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        TextView view = (TextView) LayoutInflater.from(this).inflate(R.layout.dialog_sign_jiangli, null);

        if(flag==1){
            //获取签到信息
            String str = "签到奖励金币 × "+award+"\n"+"连续签到奖励 × "+continuousAward;
            view.setText(str);

        }else {
            String str = "补签奖励金币 × "+buqian+"\n"+"连续签到奖励 × "+continuousAward;
            view.setText(str);
        }


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dismiss();
            }
        },2000);

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
        show(view);
    }

    /**
     * 显示PopupWindow
     */
    private void show(View v) {
        if (mPopupWindow1 != null && !mPopupWindow1.isShowing()) {
            mPopupWindow1.showAtLocation(v, Gravity.CENTER, 0, 0);
        }
        setWindowAlpa(true);

    }

    /**
     * 消失PopupWindow
     */
    public void dismiss() {
        if (mPopupWindow1 != null && mPopupWindow1.isShowing()) {
            mPopupWindow1.dismiss();
        }
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
}
