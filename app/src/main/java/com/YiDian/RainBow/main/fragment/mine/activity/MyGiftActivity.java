package com.YiDian.RainBow.main.fragment.mine.activity;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseAvtivity;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.base.Common;
import com.YiDian.RainBow.main.fragment.mine.activity.giftfragment.ReciveGiftFragment;
import com.YiDian.RainBow.main.fragment.mine.activity.giftfragment.SendGiftFragment;
import com.YiDian.RainBow.utils.SPUtil;
import com.leaf.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

//我的礼物
public class MyGiftActivity extends BaseAvtivity implements RadioGroup.OnCheckedChangeListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_back)
    LinearLayout ivBack;
    @BindView(R.id.rb_send)
    RadioButton rbSend;
    @BindView(R.id.rb_revice)
    RadioButton rbRevice;
    @BindView(R.id.rgTab)
    RadioGroup rgTab;
    @BindView(R.id.vp)
    ViewPager vp;

    RadioButton[] rbs = new RadioButton[2];
    private List<Fragment> list;
    String TAG = "xxx";

    /**
     * 创建Fragment实例
     */
    private SendGiftFragment sendGiftFragment;
    private ReciveGiftFragment reciveGiftFragment;
    private int userid;

    @Override
    protected int getResId() {
        return R.layout.activity_mygift;
    }

    @Override
    protected void getData() {
        StatusBarUtil.setGradientColor(MyGiftActivity.this,toolbar);
        StatusBarUtil.setDarkMode(MyGiftActivity.this);

        //创建集合存入fragment
        list = new ArrayList<>();

        userid = Integer.parseInt(Common.getUserId());

        String sendcount = SPUtil.getInstance().getData(MyGiftActivity.this, SPUtil.FILE_NAME, SPUtil.SENG_COUNT);
        String recivecount = SPUtil.getInstance().getData(MyGiftActivity.this, SPUtil.FILE_NAME, SPUtil.RECIVE_COUNT);
        if (sendcount!=null && !sendcount.equals("")){
            rbSend.setText("送出("+sendcount+")");
        }else{
            rbSend.setText("送出("+0+")");
        }

        if (recivecount!=null && !recivecount.equals("")){
            rbRevice.setText("收到("+recivecount+")");
        }else{
            rbRevice.setText("收到("+0+")");
        }

        rbs[0] = rbSend;
        rbs[1] = rbRevice;


        rgTab.setOnCheckedChangeListener(this);

        sendGiftFragment = new SendGiftFragment();
        reciveGiftFragment = new ReciveGiftFragment();

        list.add(sendGiftFragment);
        list.add(reciveGiftFragment);


        MyAdapter myAdapter = new MyAdapter(getSupportFragmentManager());
        vp.setAdapter(myAdapter);

        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        rbSend.setChecked(true);
                        break;
                    case 1:
                        rbRevice.setChecked(true);
                        break;
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        vp.setCurrentItem(0);
        rbSend.setChecked(true);
        rbSend.setTextSize(15);
        rbSend.setTextAppearance(MyGiftActivity.this, R.style.txt_bold);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            //设置切换页面及选中字体大小
            case R.id.rb_send:
                vp.setCurrentItem(0);

                rbSend.setTextSize(15);
                rbSend.setTextAppearance(MyGiftActivity.this, R.style.txt_bold);

                rbRevice.setTextSize(12);
                rbRevice.setTextAppearance(MyGiftActivity.this, R.style.txt_nomal);


                break;
            case R.id.rb_revice:


                vp.setCurrentItem(1);

                rbRevice.setTextSize(15);
                rbRevice.setTextAppearance(MyGiftActivity.this, R.style.txt_bold);

                rbSend.setTextSize(12);
                rbSend.setTextAppearance(MyGiftActivity.this, R.style.txt_nomal);

                break;
        }
    }

    public class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }
    }


    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

}
