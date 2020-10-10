package com.YiDian.RainBow.main.fragment.home.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.SparseArray;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.custom.viewpager.HackyViewPager;
import com.YiDian.RainBow.regist.activity.RegistActivity;
import com.YiDian.RainBow.utils.PhotoLoader;
import com.YiDian.RainBow.welcome.WelcomeActivity;
import com.leaf.library.StatusBarUtil;

import org.lym.image.select.imageloader.GlideImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import indi.liyi.viewer.ImageViewer;
import indi.liyi.viewer.listener.OnItemClickListener;

public class NewDynamicImage extends FragmentActivity {
    private static final String STATE_POSITION = "STATE_POSITION";
    public static final String EXTRA_IMAGE_URLS = "image_urls";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.vp)
    ImageViewer vp;

    private List<String> urls;
    private int index;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_img);
        ButterKnife.bind(this);
        //右进左出
        this.overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);

        //全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //设置状态栏
        StatusBarUtil.setGradientColor(NewDynamicImage.this, toolbar);
        StatusBarUtil.setLightMode(NewDynamicImage.this);
        urls = getIntent().getStringArrayListExtra(
                EXTRA_IMAGE_URLS);
        index = getIntent().getIntExtra("index", 0);

        //获取数据
        Intent intent = getIntent();
        index = intent.getIntExtra("index", 0);
        urls = intent.getStringArrayListExtra("img");

        vp.overlayStatusBar(false) // ImageViewer 是否会占据 StatusBar 的空间
                .imageData(urls) // 图片数据
                .imageLoader(new PhotoLoader()) // 设置图片加载方式
                .playEnterAnim(true) // 是否开启进场动画，默认为true
                .playExitAnim(true) // 是否开启退场动画，默认为true
                .draggable(false)
                .showIndex(true) // 是否显示图片索引，默认为true
                .watch(index); // 开启浏览
        vp.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public boolean onItemClick(int position, ImageView imageView) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 400);//2秒后执行Runnable中的run方法
                return false;
            }
        });

    }


    //宿主activity中的getTitles()方法
    public List<String> getUrl() {
        return urls;
    }
    //宿主activity中的getTitles()方法
    public int getIndex() {
        return index;
    }

}
