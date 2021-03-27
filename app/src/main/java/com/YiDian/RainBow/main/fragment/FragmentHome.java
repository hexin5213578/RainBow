package com.YiDian.RainBow.main.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseFragment;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.base.Common;
import com.YiDian.RainBow.custom.customDialog.CustomDialogCleanNotice;
import com.YiDian.RainBow.custom.svag.SvgaUtils;
import com.YiDian.RainBow.custom.zbar.CaptureActivity;
import com.YiDian.RainBow.dynamic.activity.DevelopmentDynamicActivity;
import com.YiDian.RainBow.friend.activity.AddFriendActivity;
import com.YiDian.RainBow.main.fragment.home.fragment.FragmentAttDynamic;
import com.YiDian.RainBow.main.fragment.home.fragment.FragmentHotDynamic;
import com.YiDian.RainBow.main.fragment.home.fragment.FragmentNearDynamic;
import com.YiDian.RainBow.main.fragment.home.fragment.FragmentNewDynamic;
import com.YiDian.RainBow.setup.activity.RealnameActivity;
import com.YiDian.RainBow.setup.bean.GetRealDataBean;
import com.YiDian.RainBow.utils.NetUtils;
import com.leaf.library.StatusBarUtil;
import com.opensource.svgaplayer.SVGACallback;
import com.opensource.svgaplayer.SVGADrawable;
import com.opensource.svgaplayer.SVGAImageView;
import com.opensource.svgaplayer.SVGAParser;
import com.opensource.svgaplayer.SVGAVideoEntity;
import com.shuyu.gsyvideoplayer.GSYVideoManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FragmentHome extends BaseFragment implements RadioGroup.OnCheckedChangeListener {
    @BindView(R.id.ll_search)
    LinearLayout llSearch;
    @BindView(R.id.iv_add)
    ImageView ivAdd;
    @BindView(R.id.rbNewDynamic)
    RadioButton rbNewDynamic;
    @BindView(R.id.rbNearDynamic)
    RadioButton rbNearDynamic;
    @BindView(R.id.rbAttDynamic)
    RadioButton rbAttDynamic;
    @BindView(R.id.rbHotDynamic)
    RadioButton rbHotDynamic;
    @BindView(R.id.rgTab)
    RadioGroup rgTab;


    RadioButton[] rbs = new RadioButton[4];
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.vp)
    ViewPager vp;
    @BindView(R.id.iv_saoyisao)
    ImageView ivSaoyisao;
    @BindView(R.id.v1)
    View v1;
    @BindView(R.id.svgaImage)
    SVGAImageView svga;

    /**
     * 创建Fragment实例
     */
    private FragmentNewDynamic fragmentNewDynamic;
    private FragmentNearDynamic fragmentNearDynamic;
    private FragmentAttDynamic fragmentAttDynamic;
    private FragmentHotDynamic fragmentHotDynamic;
    private List<Fragment> list;
    private int userid;
    private boolean isClick = false;
    private CustomDialogCleanNotice.Builder builder;
    String animationFileName = "aixin";
    @Override
    protected void getid(View view) {

    }

    @Override
    protected int getResId() {
        return R.layout.home_fragment_home;
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("ResourceAsColor")
    @Override
    protected void getData() {
        StatusBarUtil.setGradientColor(getActivity(), toolbar);
        StatusBarUtil.setDarkMode(getActivity());


        final SvgaUtils svgaUtils = new SvgaUtils(getContext(),svga);
        svgaUtils.initAnimator();


        userid = Integer.valueOf(Common.getUserId());
        getRealStatus();

        list = new ArrayList<>();

        llSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到搜索页
                startActivity(new Intent(getContext(), AddFriendActivity.class));
            }
        });
        ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isClick) {
                    //跳转到发布动态页
                    startActivity(new Intent(getContext(), DevelopmentDynamicActivity.class));
                } else {
                    builder = new CustomDialogCleanNotice.Builder(getContext());
                    builder.setMessage("您还没有提交实名认证信息").setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(getContext(), RealnameActivity.class));
                        }
                    });
                    builder.setNegativeButton("取消",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    builder.create().show();
                }
            }
        });


        ivSaoyisao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CaptureActivity.class);
                startActivityForResult(intent, 100);

                //svgaUtils.startAnimator(animationFileName);

            }
        });
        //申请开启内存卡权限
        if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) && (getContext().checkSelfPermission
                (Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
            //请求权限
            this.requestPermissions(new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }

        if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) && (getContext().checkSelfPermission
                (Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
            //请求权限
            this.requestPermissions(new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        rbs[0] = rbNewDynamic;
        rbs[1] = rbNearDynamic;
        rbs[2] = rbAttDynamic;
        rbs[3] = rbHotDynamic;

        rgTab.setOnCheckedChangeListener(this);
        //创建fragment实例
        fragmentNewDynamic = new FragmentNewDynamic();
        fragmentNearDynamic = new FragmentNearDynamic();
        fragmentAttDynamic = new FragmentAttDynamic();
        fragmentHotDynamic = new FragmentHotDynamic();

        list.add(fragmentNewDynamic);
        list.add(fragmentNearDynamic);
        list.add(fragmentAttDynamic);
        list.add(fragmentHotDynamic);

        MyAdapter myAdapter = new MyAdapter(getChildFragmentManager());
        vp.setAdapter(myAdapter);

        /**
         * 首次进入加载第一个界面
         */
        rbNewDynamic.setChecked(true);
        vp.setCurrentItem(0);
        rbNewDynamic.setTextSize(18);
        rbNewDynamic.setTextAppearance(getContext(), R.style.txt_bold);

    }

    //获取实名认证状态
    public void getRealStatus() {
        NetUtils.getInstance().getApis()
                .doGetRealMsg(userid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GetRealDataBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(GetRealDataBean getRealDataBean) {
                        String msg = getRealDataBean.getMsg();
                        if (msg.equals("您还没有提交实名信息")) {
                            isClick = false;
                        } else {
                            int auditStatus = getRealDataBean.getObject().getAuditStatus();
                            if (auditStatus == 2) {
                                isClick = false;
                            }
                            if (auditStatus == 1) {
                                isClick = true;
                            }
                            if (auditStatus == 0) {
                                isClick = false;
                            }
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
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            //设置切换页面及选中字体大小
            case R.id.rbNewDynamic:
                vp.setCurrentItem(0);
                rbNewDynamic.setTextSize(18);
                rbNewDynamic.setTextAppearance(getContext(), R.style.txt_bold);

                rbNearDynamic.setTextSize(16);
                rbNearDynamic.setTextAppearance(getContext(), R.style.txt_nomal);

                rbAttDynamic.setTextSize(16);
                rbAttDynamic.setTextAppearance(getContext(), R.style.txt_nomal);

                rbHotDynamic.setTextSize(16);
                rbHotDynamic.setTextAppearance(getContext(), R.style.txt_nomal);
                break;
            case R.id.rbNearDynamic:

                vp.setCurrentItem(1);

                rbNearDynamic.setTextSize(18);
                rbNearDynamic.setTextAppearance(getContext(), R.style.txt_bold);

                rbNewDynamic.setTextSize(16);
                rbNewDynamic.setTextAppearance(getContext(), R.style.txt_nomal);

                rbAttDynamic.setTextSize(16);
                rbAttDynamic.setTextAppearance(getContext(), R.style.txt_nomal);

                rbHotDynamic.setTextSize(16);
                rbHotDynamic.setTextAppearance(getContext(), R.style.txt_nomal);
                break;
            case R.id.rbAttDynamic:
                vp.setCurrentItem(2);

                rbAttDynamic.setTextSize(18);
                rbAttDynamic.setTextAppearance(getContext(), R.style.txt_bold);

                rbNewDynamic.setTextSize(16);
                rbNewDynamic.setTextAppearance(getContext(), R.style.txt_nomal);

                rbNearDynamic.setTextSize(16);
                rbNearDynamic.setTextAppearance(getContext(), R.style.txt_nomal);

                rbHotDynamic.setTextSize(16);
                rbHotDynamic.setTextAppearance(getContext(), R.style.txt_nomal);
                break;
            case R.id.rbHotDynamic:
                vp.setCurrentItem(3);

                rbHotDynamic.setTextSize(18);
                rbHotDynamic.setTextAppearance(getContext(), R.style.txt_bold);

                rbNewDynamic.setTextSize(16);
                rbNewDynamic.setTextAppearance(getContext(), R.style.txt_nomal);

                rbNearDynamic.setTextSize(16);
                rbNearDynamic.setTextAppearance(getContext(), R.style.txt_nomal);

                rbAttDynamic.setTextSize(16);
                rbAttDynamic.setTextAppearance(getContext(), R.style.txt_nomal);
                break;
            default:
                break;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        GSYVideoManager.onPause();

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getStr(String str) {
        if (str.equals("重新获取我的基本信息")) {
            Log.d("xxx", "走到这里了");
            getRealStatus();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        GSYVideoManager.releaseAllVideos();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        GSYVideoManager.onResume();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        GSYVideoManager.releaseAllVideos();
    }
}
