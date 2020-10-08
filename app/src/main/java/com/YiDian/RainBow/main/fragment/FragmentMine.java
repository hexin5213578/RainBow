package com.YiDian.RainBow.main.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseFragment;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.custom.customrecycle.SpacesItemDecoration;
import com.YiDian.RainBow.main.fragment.mine.HobbyAdapter;
import com.YiDian.RainBow.main.fragment.msg.adapter.MsgRecordingAdapter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

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
    @BindView(R.id.tv_add_hobby)
    TextView tvAddHobby;
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
    @BindView(R.id.ll_jinbi)
    LinearLayout llJinbi;
    @BindView(R.id.ll_liwu)
    LinearLayout llLiwu;
    @BindView(R.id.ll_saoyisao)
    LinearLayout llSaoyisao;
    @BindView(R.id.ll_shezhi)
    LinearLayout llShezhi;
    int space = 9;

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
        llMyMoney.setOnClickListener(this);
        ivQrCode.setOnClickListener(this);
        llCertification.setOnClickListener(this);
        llGreat.setOnClickListener(this);
        tvAddHobby.setOnClickListener(this);
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

                break;
                //查看点赞详情
            case R.id.ll_great:

                break;
                //添加更多爱好
            case R.id.tv_add_hobby:

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
                //金币
            case R.id.ll_jinbi:

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
}
