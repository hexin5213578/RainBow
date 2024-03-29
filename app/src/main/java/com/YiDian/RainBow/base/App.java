package com.YiDian.RainBow.base;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;


import androidx.multidex.MultiDex;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.utils.GlobalEventListener;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.im.android.api.JMessageClient;


/**
 * @ClassName: App
 * @Description: (java类作用描述)
 * @Author: hmy
 */
public class App extends Application {
    private static Context context;
    private static  IWXAPI mWXApi;
    private static final String WX_AppId = "wxf8a5f128098b4df3";

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        MultiDex.install(this);
        //fresco初始化
        Fresco.initialize(context);
        //沙箱测试
        //EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);


        //imageloader初始化
        initImageloader();

        Bugly.init(getApplicationContext(), "9d9b5f2a2f", false);

        Context context = getApplicationContext();
        // 获取当前包名
        String packageName = context.getPackageName();
        // 获取当前进程名
        String processName = getProcessName(android.os.Process.myPid());
        // 设置是否为上报进程
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
        strategy.setUploadProcess(processName == null || processName.equals(packageName));
         //初始化Bugly
        CrashReport.initCrashReport(getApplicationContext(), "c11555727a", false);
        // 如果通过“AndroidManifest.xml”来配置APP信息，初始化方法如下
         CrashReport.initCrashReport(context, strategy);

        initWX();
        //初始化极光
        JMessageClient.setDebugMode(true);
        JMessageClient.init(this);

        //注册全局事件监听类
        JMessageClient.registerEventReceiver(new GlobalEventListener(getApplicationContext()));
        //初始化极光推送
        // 设置开启日志,发布时请关闭日志
        JPushInterface.setDebugMode(true);
        // 初始化 JPush
        JPushInterface.init(this);


    }

    private void initWX() {
        mWXApi = WXAPIFactory.createWXAPI(context, null);
        mWXApi.registerApp(WX_AppId);
    }

    public static IWXAPI getWXApi() {
        return mWXApi;
    }

    public static Context getContext() {
        return context;
    }
    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }
    public void initImageloader() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                // default 图片在下载前是否重置,复位
                .resetViewBeforeLoading(false)
                // 图片开始加载前的延时.默认是0
                .delayBeforeLoading(100)
                // default , 是否缓存在内存中, 默认不缓存
                .cacheInMemory(true)
                // default , 是否缓存在硬盘 , 默认不缓存
                .cacheOnDisk(true)
                // default , 设置是否考虑JPEG图片的EXIF参数信息,默认不考虑
                .considerExifParams(false)
                // default , 指定图片缩放的方式,ListView/GridView/Gallery推荐使用此默认值
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
                // default , 指定图片的质量,默认是 ARGB_8888
                .bitmapConfig(Bitmap.Config.RGB_565)
                // default , 设置图片显示的方式,用于自定义
                .displayer(new SimpleBitmapDisplayer())
                // default ,设置图片显示的方式和ImageLoadingListener的监听, 用于自定义
                .handler(new Handler())
                .build();

        File picPath = new File(Environment.getExternalStorageDirectory().getPath() + File.separator + "MyApp"
                + File.separator + "files");

        ImageLoaderConfiguration config   = new ImageLoaderConfiguration.Builder(context)
                // 指定缓存到内存时图片的大小,默认是屏幕尺寸的长宽
                .memoryCacheExtraOptions(480, 800)
                // default, 指定线程池大小
                .threadPoolSize(5)
                // default ,指定线程优先级
                .threadPriority(Thread.NORM_PRIORITY - 2)
                // default , 指定加载显示图片的任务队列的类型
                .tasksProcessingOrder(QueueProcessingType.FIFO)
                // 禁止在内存中缓存同一张图片的多个尺寸类型
                .denyCacheImageMultipleSizesInMemory()
                // 指定内存缓存的大小,默认值为1/8 应用的最大可用内存
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
                .memoryCacheSize(2 * 1024 * 1024)
                // default
                .memoryCacheSizePercentage(13)
                // default , 指定硬盘缓存的地址
                .diskCache(new UnlimitedDiskCache(picPath))
                // 指定硬盘缓存的大小
                .diskCacheSize(200 * 1024 * 1024)
                // 指定硬盘缓存的文件个数
                .diskCacheFileCount(100)
                // default , 指定硬盘缓存时文件名的生成器
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator())
                // default , 指定图片下载器
                .imageDownloader(new BaseImageDownloader(context))
                // default , 指定图片显示的配置
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .build();

        // 初始化配置
        ImageLoader.getInstance().init(config);
    }
}
