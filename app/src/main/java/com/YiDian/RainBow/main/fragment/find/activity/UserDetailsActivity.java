package com.YiDian.RainBow.main.fragment.find.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseAvtivity;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.main.fragment.find.fragment.userdetailsfragment.FragmentDianzan;
import com.YiDian.RainBow.main.fragment.find.fragment.userdetailsfragment.FragmentFabiao;
import com.YiDian.RainBow.search.fragment.FragmentHotTopic;
import com.YiDian.RainBow.search.fragment.FragmentSearchPreson;
import com.YiDian.RainBow.search.fragment.FragmentSearchRoom;
import com.bumptech.glide.Glide;
import com.leaf.library.StatusBarUtil;
import com.stx.xhb.xbanner.XBanner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

//用户详情页 由匹配跳转

public class UserDetailsActivity extends BaseAvtivity implements RadioGroup.OnCheckedChangeListener {
    @BindView(R.id.xbn)
    XBanner xbn;
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.sandian)
    LinearLayout sandian;
    @BindView(R.id.tv_userId)
    TextView tvUserId;
    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.tv_guanzhu)
    TextView tvGuanzhu;
    @BindView(R.id.tv_age)
    TextView tvAge;
    @BindView(R.id.rc_hobby)
    RecyclerView rcHobby;
    @BindView(R.id.tv_distance)
    TextView tvDistance;
    @BindView(R.id.rbFabiao)
    RadioButton rbFabiao;
    @BindView(R.id.rbDianzan)
    RadioButton rbDianzan;
    @BindView(R.id.rgMenu)
    RadioGroup rgMenu;
    @BindView(R.id.flContent)
    FrameLayout flContent;
    RadioButton[] rbs = new RadioButton[2];
    private FragmentManager fmManager;
    /**
     * 当前展示的Fragment
     */
    private Fragment currentFragment;

    /**
     * 创建Fragment实例
     */
    private FragmentFabiao fragmentFabiao;
    private FragmentDianzan fragmentDianzan;

    @Override
    protected int getResId() {
        return R.layout.activity_user_details;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void getData() {
        //设置背景透明
        StatusBarUtil.setTransparentForWindow(this);
        //设置状态栏字体黑色
        StatusBarUtil.setLightMode(this);
        rbs[0] = rbFabiao;
        rbs[1] = rbDianzan;
        fmManager = getSupportFragmentManager();
        rgMenu.setOnCheckedChangeListener(this);
        //创建fragment实例
        fragmentFabiao = new FragmentFabiao();
        fragmentDianzan = new FragmentDianzan();
        /**
         * 首次进入加载第一个界面
         */
        rbFabiao.setChecked(true);
        rbFabiao.setTextSize(12);
        rbFabiao.setTextAppearance(R.style.txt_bold);
        rbFabiao.setTextColor(R.color.white);

        //点击返回 关闭界面
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        List<String> imgesUrl = new ArrayList<>();
        //设置xbn
        imgesUrl.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1602584344818&di=0fb9883ec184bad9e84f91b5b17e4397&imgtype=0&src=http%3A%2F%2Fattachments.gfan.com%2Fforum%2Fattachments2%2Fday_110625%2F11062523442556a300569aad62.jpg");
        imgesUrl.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1602584344817&di=8c9f250637890f72237730a15e4aa5c0&imgtype=0&src=http%3A%2F%2F00.minipic.eastday.com%2F20170122%2F20170122133549_02dc7a8d994ea57c5a7c11421a56eb2f_5.jpeg");
        imgesUrl.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1602584380308&di=de67c2c3804ec1648b0602458ccdd840&imgtype=0&src=http%3A%2F%2Fh.hiphotos.baidu.com%2Fzhidao%2Fpic%2Fitem%2Fc995d143ad4bd113cce8af145bafa40f4bfb05ea.jpg");
        imgesUrl.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1602584650798&di=82db921275340cb6dc2e1d16bd3005b9&imgtype=0&src=http%3A%2F%2Fimg.pconline.com.cn%2Fimages%2Fupload%2Fupc%2Ftx%2Fphotoblog%2F1108%2F30%2Fc11%2F8819835_8819835_1314699166425.jpg");
        xbn.setData(imgesUrl,null);
        xbn.setmAdapter(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
                Glide.with(UserDetailsActivity.this).load(imgesUrl.get(position)).into((ImageView) view);
            }
        });
        // 设置XBanner页面切换的时间，即动画时长
        xbn.setPageChangeDuration(1500);
    }
    /**
     * 切换页面显示fragment
     *
     * @param to 跳转到的fragment
     */
    private void replace(Fragment to) {
        if (to == null || to == currentFragment) {
            // 如果跳转的fragment为空或者跳转的fragment为当前fragment则不做操作
            return;
        }
        if (currentFragment == null) {
            // 如果当前fragment为空,即为第一次添加fragment
            fmManager.beginTransaction()
                    .add(R.id.flContent, to)
                    .commitAllowingStateLoss();
            currentFragment = to;
            return;
        }
        // 切换fragment
        FragmentTransaction transaction = fmManager.beginTransaction().hide(currentFragment);
        if (!to.isAdded()) {
            transaction.add(R.id.flContent, to);
        } else {
            transaction.show(to);
        }
        transaction.commitAllowingStateLoss();
        currentFragment = to;
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            //设置切换页面及选中字体大小
            case R.id.rbFabiao:
                //调用切换界面方法
                replace(fragmentFabiao);
                rbFabiao.setTextSize(12);
                rbFabiao.setTextAppearance(R.style.txt_bold);
                rbFabiao.setTextColor(R.color.white);

                rbDianzan.setTextSize(10);
                rbDianzan.setTextAppearance(R.style.txt_nomal);
                rbFabiao.setTextColor(R.color.color_666666);

                break;
            case R.id.rbDianzan:
                replace(fragmentDianzan);
                rbDianzan.setTextSize(12);
                rbDianzan.setTextAppearance(R.style.txt_bold);
                rbFabiao.setTextColor(R.color.white);

                rbFabiao.setTextSize(10);
                rbFabiao.setTextAppearance(R.style.txt_nomal);
                rbFabiao.setTextColor(R.color.color_666666);

                break;
            default:
                break;
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        xbn.startAutoPlay();
    }

    @Override
    protected void onStop() {
        super.onStop();
        xbn.stopAutoPlay();
    }
}
