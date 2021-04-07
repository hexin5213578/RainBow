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

//图片展示
public class NewDynamicImage extends FragmentActivity {
    private static final String STATE_POSITION = "STATE_POSITION";
    public static final String EXTRA_IMAGE_URLS = "image_urls";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.vp)
    ImageViewer vp;
    String path = "?imageView2/0/format/jpg/w/400";
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

        List<String> url = new ArrayList<>();

        if (urls.get(0).contains(path)){
            for (int i =0;i<urls.size();i++){
                url.add(urls.get(i).substring(0,urls.get(i).lastIndexOf("?")));
            }
            // ImageViewer 是否会占据 StatusBar 的空间
            vp.overlayStatusBar(false)
                    // 图片数据
                    .imageData(url)
                    // 设置图片加载方式
                    .imageLoader(new PhotoLoader())
                    // 是否开启进场动画，默认为true
                    .playEnterAnim(true)
                    // 是否开启退场动画，默认为true
                    .playExitAnim(false)
                    .draggable(false)
                    // 是否显示图片索引，默认为true
                    .showIndex(true)
                    // 开启浏览
                    .watch(index);
        }else{
            // ImageViewer 是否会占据 StatusBar 的空间
            vp.overlayStatusBar(false)
                    // 图片数据
                    .imageData(urls)
                    // 设置图片加载方式
                    .imageLoader(new PhotoLoader())
                    // 是否开启进场动画，默认为true
                    .playEnterAnim(true)
                    // 是否开启退场动画，默认为true
                    .playExitAnim(false)
                    .draggable(false)
                    .showIndex(true)
                    // 是否显示图片索引，默认为true
                    .watch(index);
        }

        vp.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public boolean onItemClick(int position, ImageView imageView) {
                finish();

                return false;
            }
        });

    }
}
