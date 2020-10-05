package com.YiDian.RainBow.base;

import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;


import androidx.multidex.MultiDex;

import com.facebook.drawee.backends.pipeline.Fresco;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


/**
 * @ClassName: App
 * @Description: (java类作用描述)
 * @Author: hmy
 */
public class App extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        MultiDex.install(this);
        Fresco.initialize(context);
        //沙箱测试
        //EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);

        initWX();
       // Bugly.init(getApplicationContext(), "c11555727a", false);

        Context context = getApplicationContext();
        // 获取当前包名
        String packageName = context.getPackageName();
        // 获取当前进程名
        String processName = getProcessName(android.os.Process.myPid());
        // 设置是否为上报进程
       // CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
       // strategy.setUploadProcess(processName == null || processName.equals(packageName));
        // 初始化Bugly
        //CrashReport.initCrashReport(getApplicationContext(), "c11555727a", false);
        // 如果通过“AndroidManifest.xml”来配置APP信息，初始化方法如下
        // CrashReport.initCrashReport(context, strategy);
    }

    private void initWX() {
       // mWXApi = WXAPIFactory.createWXAPI(context, null);
       // mWXApi.registerApp("wxacf956e14f407890");
    }

   /* public static IWXAPI getWXApi() {
        return mWXApi;
    }*/

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
}
