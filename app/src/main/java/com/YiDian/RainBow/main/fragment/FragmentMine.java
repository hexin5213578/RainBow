package com.YiDian.RainBow.main.fragment;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseFragment;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.base.Common;
import com.YiDian.RainBow.custom.customrecycle.SpacesItemDecoration;
import com.YiDian.RainBow.custom.zbar.CaptureActivity;
import com.YiDian.RainBow.friend.FriendsActivity;
import com.YiDian.RainBow.main.fragment.mine.activity.EditMsgActivity;
import com.YiDian.RainBow.main.fragment.mine.activity.FangkerecordActivity;
import com.YiDian.RainBow.main.fragment.mine.activity.MyGiftActivity;
import com.YiDian.RainBow.main.fragment.mine.activity.MyQrCodeActivity;
import com.YiDian.RainBow.main.fragment.mine.activity.MydraftActivity;
import com.YiDian.RainBow.main.fragment.mine.activity.RechargeGlodActivity;
import com.YiDian.RainBow.main.fragment.mine.adapter.HobbyAdapter;
import com.YiDian.RainBow.main.fragment.mine.bean.LoginUserInfoBean;
import com.YiDian.RainBow.setup.activity.SetupActivity;
import com.YiDian.RainBow.utils.NetUtils;
import com.YiDian.RainBow.utils.SPUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.leaf.library.StatusBarUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;
import java.util.List;

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
    @BindView(R.id.ll_my_money)
    LinearLayout llMyMoney;
    @BindView(R.id.ll_certification)
    LinearLayout llCertification;
    @BindView(R.id.iv_headimg)
    ImageView ivHeadimg;
    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.tv_age)
    TextView tvAge;
    @BindView(R.id.tv_userId)
    TextView tvUserId;
    @BindView(R.id.tv_copy)
    TextView tvCopy;
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
    @BindView(R.id.tv_mygold)
    TextView tvMygold;
    private Intent intent;
    private int userid;
    private String headimg;
    private String username;
    private String qm;

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

        llMyMoney.setOnClickListener(this);
        ivQrCode.setOnClickListener(this);
        llCertification.setOnClickListener(this);
        llFangke.setOnClickListener(this);
        tvCopy.setOnClickListener(this);
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
        llSaoyisao.setOnClickListener(this);
        llShezhi.setOnClickListener(this);
        ivHeadimg.setOnClickListener(this);
        tvUsername.setOnClickListener(this);


        //申请开启相机权限
        if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) && (getContext().checkSelfPermission
                (Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)) {
            //请求权限
            getActivity().requestPermissions(new String[]{
                    Manifest.permission.CAMERA}, 1);
        }

        userid = Integer.valueOf(Common.getUserId());


        //ID赋值
        tvUserId.setText(userid+"");

        // TODO: 2020/11/26 0026 获取当前用户个人信息展示
        getUserInfo();
    }
    //获取我的信息
    public void getUserInfo(){
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
                        LoginUserInfoBean.ObjectBean.UserInfoBean info = bean.getUserInfo();


                        username = SPUtil.getInstance().getData(getContext(), SPUtil.FILE_NAME, SPUtil.USER_NAME);
                        headimg = SPUtil.getInstance().getData(getContext(), SPUtil.FILE_NAME, SPUtil.HEAD_IMG);
                        qm = SPUtil.getInstance().getData(getContext(), SPUtil.FILE_NAME, SPUtil.QIANMING);

                        //加载一张圆角头像
                        if (headimg==null){
                            SPUtil.getInstance().saveData(getContext(),SPUtil.FILE_NAME,SPUtil.HEAD_IMG,info.getHeadImg());
                            Glide.with(getContext()).load(info.getHeadImg()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(ivHeadimg);
                        }else{
                            //加载缓存内的头像信息
                            Glide.with(getContext()).load(headimg).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(ivHeadimg);
                        }
                        if (username==null){
                            SPUtil.getInstance().saveData(getContext(),SPUtil.FILE_NAME,SPUtil.USER_NAME,info.getNickName());
                            tvUsername.setText(info.getNickName());
                        }else{
                            tvUsername.setText(username);
                        }
                        //个性签名赋值
                        if (qm==null){
                            SPUtil.getInstance().saveData(getContext(),SPUtil.FILE_NAME,SPUtil.QIANMING,info.getExplains());
                            tvSignature.setText(info.getExplains());
                        }else{
                            tvSignature.setText(qm);
                        }

                        //数量赋值
                        tvCountHaoyou.setText(bean.getCountFriendNum()+"");
                        tvCountFensi.setText(bean.getCountFansNum()+"");
                        tvCountGuanzhu.setText(bean.getCountFollowNum()+"");
                        tvCountQunzu.setText(bean.getCountGroupNum()+"");

                        //访客
                        tvCountFangke.setText(bean.getCountVisitorNum()+"");

                        //年龄赋值
                        String userRole = info.getUserRole();
                        if (userRole!=null){
                            if (userRole.equals("保密")){
                                tvAge.setText(info.getAge()+"");
                            }else{
                                tvAge.setText(info.getAge()+" "+userRole);
                            }
                        }else{
                            tvAge.setText(info.getAge()+"");
                        }

                        //设置我的金币剩余数
                        tvMygold.setText(bean.getCountGoldNum()+"");
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
        if (!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getString(String str){
        if (str.equals("重新获取我的基本信息")){
            userid = Integer.valueOf(Common.getUserId());

            getUserInfo();

        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //我的金币
            case R.id.ll_my_money:
                //跳转到金币充值功能
                 intent = new Intent(getContext(), RechargeGlodActivity.class);
                startActivity(intent);
                break;
            //特权认证
            case R.id.ll_certification:

                break;
            //我的二维码
            case R.id.iv_QrCode:
                //跳转到我的二维码
                intent = new Intent(getContext(), MyQrCodeActivity.class);
                startActivity(intent);
                break;
            //复制我的ID
            case R.id.tv_copy:
                //获取id
                String id = tvUserId.getText().toString();
                //获取剪贴板管理器：
                ClipboardManager cm = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                // 创建普通字符型ClipData
                ClipData mClipData = ClipData.newPlainText("Label", id);
                // 将ClipData内容放到系统剪贴板里。
                cm.setPrimaryClip(mClipData);
                Toast.makeText(getContext(), "已复制到剪切板", Toast.LENGTH_SHORT).show();
                break;
            //查看访客详情
            case R.id.ll_fangke:
                intent = new Intent(getContext(), FangkerecordActivity.class);
                startActivity(intent);
                break;
            //好友
            case R.id.ll_haoyou:
                intent = new Intent(getContext(), FriendsActivity.class);
                intent.putExtra("flag",1);
                startActivity(intent);
                break;
            //粉丝
            case R.id.ll_fensi:
                intent = new Intent(getContext(), FriendsActivity.class);
                intent.putExtra("flag",2);
                startActivity(intent);
                break;
            //关注
            case R.id.ll_guanzhu:
                intent = new Intent(getContext(), FriendsActivity.class);
                intent.putExtra("flag",4);
                startActivity(intent);
                break;
            //群组
            case R.id.ll_qunzu:
                intent = new Intent(getContext(), FriendsActivity.class);
                intent.putExtra("flag",3);
                startActivity(intent);
                break;
            //发布的动态
            case R.id.ll_dongtai:

                break;
            //草稿箱
            case R.id.ll_caogaoxiang:
                //跳转到我的草稿箱
                intent = new Intent(getContext(), MydraftActivity.class);
                startActivity(intent);
                break;
            //收藏
            case R.id.ll_shoucang:

                break;
            //情侣标识
            case R.id.ll_yaoqing:

                break;
            //去签到
            case R.id.ll_qiandao:

                break;
            //礼物记录
            case R.id.ll_liwu:

                intent = new Intent(getContext(), MyGiftActivity.class);
                startActivity(intent);

                break;
            //扫一扫
            case R.id.ll_saoyisao:
                //扫描二维码
                intent = new Intent(getContext(), CaptureActivity.class);
                startActivityForResult(intent, 100);
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
        }
    }


}
