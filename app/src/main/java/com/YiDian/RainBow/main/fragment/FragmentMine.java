package com.YiDian.RainBow.main.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseFragment;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.base.Common;
import com.YiDian.RainBow.friend.activity.FriendsActivity;
import com.YiDian.RainBow.main.fragment.mine.activity.CollectActivity;
import com.YiDian.RainBow.main.fragment.mine.activity.EditMsgActivity;
import com.YiDian.RainBow.main.fragment.mine.activity.EveryDayRegisterActivity;
import com.YiDian.RainBow.main.fragment.mine.activity.FangkerecordActivity;
import com.YiDian.RainBow.main.fragment.mine.activity.GoldBalance;
import com.YiDian.RainBow.main.fragment.mine.activity.LavesMarkActivity;
import com.YiDian.RainBow.main.fragment.mine.activity.MyGiftActivity;
import com.YiDian.RainBow.main.fragment.mine.activity.MyQrCodeActivity;
import com.YiDian.RainBow.main.fragment.mine.activity.MydraftActivity;
import com.YiDian.RainBow.main.fragment.mine.activity.ReleaseDynamicsActivity;
import com.YiDian.RainBow.main.fragment.mine.bean.LoginUserInfoBean;
import com.YiDian.RainBow.setup.activity.BlackListActivity;
import com.YiDian.RainBow.setup.activity.RealnameActivity;
import com.YiDian.RainBow.setup.activity.SetupActivity;
import com.YiDian.RainBow.utils.NetUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.leaf.library.StatusBarUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

//我的
public class FragmentMine extends BaseFragment implements View.OnClickListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_QrCode)
    ImageView ivQrCode;
    @BindView(R.id.iv_headimg)
    ImageView ivHeadimg;
    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.tv_fangke_count)
    TextView tvCountFangke;
    @BindView(R.id.ll_fangke)
    LinearLayout llFangke;
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
    @BindView(R.id.ll_shezhi)
    LinearLayout llShezhi;
    int space = 9;
    @BindView(R.id.ll_readdetails)
    LinearLayout llReaddetails;
    @BindView(R.id.ll_goldblance)
    LinearLayout llGoldblance;
    @BindView(R.id.ll_shiming)
    LinearLayout llShiming;
    @BindView(R.id.ll_chongzhi)
    LinearLayout llChongzhi;
    @BindView(R.id.ll_blacklist)
    LinearLayout llBlacklist;
    @BindView(R.id.ll_renzheng)
    LinearLayout llRenzheng;
    @BindView(R.id.tv_id)
    TextView tvId;
    private Intent intent;
    private int userid;
    private String headimg;
    private String username;

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

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void getData() {
        //设置状态栏颜色与字体颜色
        StatusBarUtil.setDarkMode(getActivity());


        llReaddetails.setOnClickListener(this);
        llBlacklist.setOnClickListener(this);
        llGoldblance.setOnClickListener(this);
        llChongzhi.setOnClickListener(this);
        llShiming.setOnClickListener(this);
        llRenzheng.setOnClickListener(this);
        ivQrCode.setOnClickListener(this);
        llFangke.setOnClickListener(this);
        llHaoyou.setOnClickListener(this);
        llFensi.setOnClickListener(this);
        llGuanzhu.setOnClickListener(this);
        llQunzu.setOnClickListener(this);
        llDongtai.setOnClickListener(this);
        llCaogaoxiang.setOnClickListener(this);
        llShoucang.setOnClickListener(this);
        llYaoqing.setOnClickListener(this);
        llJinbi.setOnClickListener(this);
        llLiwu.setOnClickListener(this);
        llShezhi.setOnClickListener(this);
        ivHeadimg.setOnClickListener(this);
        tvUsername.setOnClickListener(this);

        userid = Integer.valueOf(Common.getUserId());

        //申请开启相机权限
        if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) && (getContext().checkSelfPermission
                (Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)) {
            //请求权限
            getActivity().requestPermissions(new String[]{
                    Manifest.permission.CAMERA}, 1);
        }

        // TODO: 2020/11/26 0026 获取当前用户个人信息展示
        getMyInfo();
        getUserInfo();
    }


    public void getMyInfo() {
        Log.d("xxx", "getMyInfo: 执行刷新数据");
        username = Common.getUserName();
        headimg = Common.getHeadImg();

        tvUsername.setText(username);
        tvId.setText("ID:"+userid);
        //加载圆角图
        if (headimg != null && !headimg.equals("")) {
            Glide.with(this).load(headimg).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(ivHeadimg);
        } else {
            Glide.with(this).load(R.mipmap.headimg3).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(ivHeadimg);
        }
    }

    //获取我的信息
    public void getUserInfo() {
        NetUtils.getInstance()
                .getApis().doGetUserInfo(userid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginUserInfoBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(LoginUserInfoBean loginUserInfoBean) {
                        LoginUserInfoBean.ObjectBean bean = loginUserInfoBean.getObject();

                        //数量赋值
                        tvCountHaoyou.setText(bean.getCountFriendNum() + "");
                        tvCountFensi.setText(bean.getCountFansNum() + "");
                        tvCountGuanzhu.setText(bean.getCountFollowNum() + "");
                        tvCountQunzu.setText(bean.getCountGroupNum() + "");

                        if (bean.getCountVisitorNum() != null) {
                            //访客
                            if (bean.getCountVisitorNum() > 10000) {
                                tvCountFangke.setText(bean.getCountVisitorNum() / 10000 + "w");
                            } else {
                                tvCountFangke.setText(bean.getCountVisitorNum() + "");
                            }
                        } else {
                            tvCountFangke.setText("0");
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

    @Override
    public void onResume() {
        super.onResume();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getUserInfo();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getString(String str) {
        if (str.equals("重新获取我的基本信息")) {
            userid = Integer.valueOf(Common.getUserId());

            getMyInfo();
            getUserInfo();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //我的金币
            case R.id.ll_goldblance:
                //跳转到金币充值功能
                intent = new Intent(getContext(), GoldBalance.class);
                startActivity(intent);
                break;
            //特权认证
            case R.id.ll_readdetails:
            case R.id.ll_renzheng:
                Toast.makeText(getContext(), "特权认证功能暂未开放", Toast.LENGTH_SHORT).show();
                break;
            //去充值
            case R.id.ll_chongzhi:
                Toast.makeText(getContext(), "充值功能暂未开放", Toast.LENGTH_SHORT).show();
                break;
            //实名认证
            case R.id.ll_shiming:
                intent = new Intent(getContext(), RealnameActivity.class);
                startActivity(intent);
                break;
            //查看黑名单
            case R.id.ll_blacklist:
                intent = new Intent(getContext(), BlackListActivity.class);
                startActivity(intent);
                break;
            //我的二维码
            case R.id.iv_QrCode:
                //跳转到我的二维码
                intent = new Intent(getContext(), MyQrCodeActivity.class);
                startActivity(intent);
                break;
            //查看访客详情
            case R.id.ll_fangke:
                intent = new Intent(getContext(), FangkerecordActivity.class);
                startActivity(intent);
                break;
            //好友
            case R.id.ll_haoyou:
                intent = new Intent(getContext(), FriendsActivity.class);
                intent.putExtra("flag", 1);
                startActivity(intent);
                break;
            //粉丝
            case R.id.ll_fensi:
                intent = new Intent(getContext(), FriendsActivity.class);
                intent.putExtra("flag", 2);
                startActivity(intent);
                break;
            //关注
            case R.id.ll_guanzhu:
                intent = new Intent(getContext(), FriendsActivity.class);
                intent.putExtra("flag", 4);
                startActivity(intent);
                break;
            //群组
            case R.id.ll_qunzu:
                intent = new Intent(getContext(), FriendsActivity.class);
                intent.putExtra("flag", 3);
                startActivity(intent);
                break;
            //发布的动态
            case R.id.ll_dongtai:
                intent = new Intent(getContext(), ReleaseDynamicsActivity.class);
                startActivity(intent);
                break;
            //草稿箱
            case R.id.ll_caogaoxiang:
                //跳转到我的草稿箱
                intent = new Intent(getContext(), MydraftActivity.class);
                startActivity(intent);
                break;
            //收藏
            case R.id.ll_shoucang:
                intent = new Intent(getContext(), CollectActivity.class);
                startActivity(intent);
                break;
            //情侣标识
            case R.id.ll_yaoqing:
                intent = new Intent(getContext(), LavesMarkActivity.class);
                startActivity(intent);
                break;
            //去签到
            case R.id.ll_qiandao:
                intent = new Intent(getContext(), EveryDayRegisterActivity.class);
                startActivity(intent);
                break;
            //礼物记录
            case R.id.ll_liwu:
                intent = new Intent(getContext(), MyGiftActivity.class);
                startActivity(intent);
                break;
            //设置
            case R.id.ll_shezhi:
                startActivity(new Intent(getContext(), SetupActivity.class));
                break;
            //编辑个人信息
            case R.id.iv_headimg:
            case R.id.tv_username:
                startActivity(new Intent(getContext(), EditMsgActivity.class));
                break;
            default:
                break;
        }
    }


    /**
     * 根据用户生日计算年龄
     */
    public static int getAgeByBirthday(Date birthday) {
        Calendar cal = Calendar.getInstance();

        if (cal.before(birthday)) {
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }

        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH) + 1;
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

        cal.setTime(birthday);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH) + 1;
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                // monthNow==monthBirth
                if (dayOfMonthNow < dayOfMonthBirth) {
                    age--;
                }
            } else {
                // monthNow>monthBirth
                age--;
            }
        }
        return age;
    }

}
