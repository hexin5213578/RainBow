package com.YiDian.RainBow.main.fragment;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
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
import com.YiDian.RainBow.custom.zbar.CaptureActivity;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

//??????
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
    private String birthday;
    private String userrole;

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
        //????????????????????????????????????
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

        userid = Integer.valueOf(Common.getUserId());

        //????????????????????????
        if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) && (getContext().checkSelfPermission
                (Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)) {
            //????????????
            getActivity().requestPermissions(new String[]{
                    Manifest.permission.CAMERA}, 1);
        }

        // TODO: 2020/11/26 0026 ????????????????????????????????????
        getMyInfo();
        getUserInfo();
    }


    public void getMyInfo(){
        Log.d("xxx", "getMyInfo: ??????????????????");
        username = Common.getUserName();
        qm = Common.getQM();
        birthday = Common.getBirthday();
        userrole = Common.getRole();
        headimg = Common.getHeadImg();

        tvUsername.setText(username);
        // TODO: 2020/11/26 0026 ????????????????????????????????????
        if (birthday !=null && !birthday.equals("")){
            Date d=new Date();
            try{
                d= new SimpleDateFormat("yyyy-MM-dd").parse(birthday);

                int age = getAgeByBirthday(d);

                //?????????????????????
                SPUtil.getInstance().saveData(getContext(),SPUtil.FILE_NAME,SPUtil.AGE, String.valueOf(age));

                if (userrole !=null && !userrole.equals("") ){
                    if (age>0){
                        tvAge.setText(userrole+" "+age);
                    }else{
                        tvAge.setText(userrole+" "+0);
                    }
                }else{
                    if (age>0){
                        tvAge.setText(" "+age);
                    }else{
                        tvAge.setText(" "+0);
                    }
                }
            }
            catch(Exception e){

            }
        }else{
            if (userrole !=null && !userrole.equals("") ){
                tvAge.setText(userrole);
                tvAge.setVisibility(View.VISIBLE);
            }else{
                tvAge.setVisibility(View.GONE);
            }
        }

        if (qm!=null && !qm.equals("")){
            tvSignature.setText(qm);
        }else{
            tvSignature.setText("????????????????????????~");
        }

        //???????????????
        if (headimg!=null && !headimg.equals("")){
            Glide.with(this).load(headimg).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(ivHeadimg);
        }else{
            Glide.with(this).load(R.mipmap.headimg3).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(ivHeadimg);
        }

        //ID??????
        tvUserId.setText(userid+"");
    }
    //??????????????????
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

                        //????????????
                        tvCountHaoyou.setText(bean.getCountFriendNum()+"");
                        tvCountFensi.setText(bean.getCountFansNum()+"");
                        tvCountGuanzhu.setText(bean.getCountFollowNum()+"");
                        tvCountQunzu.setText(bean.getCountGroupNum()+"");

                        if (bean.getCountVisitorNum()!=null){
                            //??????
                            tvCountFangke.setText(bean.getCountVisitorNum()+"");
                        }else{
                            tvCountFangke.setText("0");
                        }

                        //???????????????????????????
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

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser){
            getUserInfo();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getString(String str){
        if (str.equals("??????????????????????????????")){
            userid = Integer.valueOf(Common.getUserId());

            getMyInfo();
            getUserInfo();
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //????????????
            case R.id.ll_my_money:
                //???????????????????????????
                 intent = new Intent(getContext(), GoldBalance.class);
                startActivity(intent);
                break;
            //????????????
            case R.id.ll_certification:

                break;
            //???????????????
            case R.id.iv_QrCode:
                //????????????????????????
                intent = new Intent(getContext(), MyQrCodeActivity.class);
                startActivity(intent);
                break;
            //????????????ID
            case R.id.tv_copy:
                //??????id
                String id = tvUserId.getText().toString();
                //???????????????????????????
                ClipboardManager cm = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                // ?????????????????????ClipData
                ClipData mClipData = ClipData.newPlainText("Label", id);
                // ???ClipData?????????????????????????????????
                cm.setPrimaryClip(mClipData);
                Toast.makeText(getContext(), "?????????????????????", Toast.LENGTH_SHORT).show();
                break;
            //??????????????????
            case R.id.ll_fangke:
                intent = new Intent(getContext(), FangkerecordActivity.class);
                startActivity(intent);
                break;
            //??????
            case R.id.ll_haoyou:
                intent = new Intent(getContext(), FriendsActivity.class);
                intent.putExtra("flag",1);
                startActivity(intent);
                break;
            //??????
            case R.id.ll_fensi:
                intent = new Intent(getContext(), FriendsActivity.class);
                intent.putExtra("flag",2);
                startActivity(intent);
                break;
            //??????
            case R.id.ll_guanzhu:
                intent = new Intent(getContext(), FriendsActivity.class);
                intent.putExtra("flag",4);
                startActivity(intent);
                break;
            //??????
            case R.id.ll_qunzu:
                intent = new Intent(getContext(), FriendsActivity.class);
                intent.putExtra("flag",3);
                startActivity(intent);
                break;
            //???????????????
            case R.id.ll_dongtai:
                intent  = new Intent(getContext(), ReleaseDynamicsActivity.class);
                startActivity(intent);
                break;
            //?????????
            case R.id.ll_caogaoxiang:
                //????????????????????????
                intent = new Intent(getContext(), MydraftActivity.class);
                startActivity(intent);
                break;
            //??????
            case R.id.ll_shoucang:
                intent = new Intent(getContext(), CollectActivity.class);
                startActivity(intent);
                break;
            //????????????
            case R.id.ll_yaoqing:
                intent = new Intent(getContext(), LavesMarkActivity.class);
                startActivity(intent);
                break;
            //?????????
            case R.id.ll_qiandao:
                intent = new Intent(getContext(), EveryDayRegisterActivity.class);
                startActivity(intent);
                break;
            //????????????
            case R.id.ll_liwu:
                intent = new Intent(getContext(), MyGiftActivity.class);
                startActivity(intent);
                break;
            //?????????
            case R.id.ll_saoyisao:
                //???????????????
                intent = new Intent(getContext(), CaptureActivity.class);
                startActivityForResult(intent, 100);
                break;
            //??????
            case R.id.ll_shezhi:
                startActivity(new Intent(getContext(), SetupActivity.class));
                break;
            //??????????????????
            case R.id.iv_headimg:
            case R.id.tv_username:
                startActivity(new Intent(getContext(), EditMsgActivity.class));
                break;
        }
    }


    /**
     * ??????????????????????????????
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
