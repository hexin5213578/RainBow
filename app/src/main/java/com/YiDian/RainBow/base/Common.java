package com.YiDian.RainBow.base;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
import android.widget.Toast;

import com.YiDian.RainBow.utils.SPUtil;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 共用工具类
 *
 * @author li
 * @since 2019/4/1
 */
public  class Common {

    //支付宝appid 2021001185635349

    /**
     * 获取token,不为空内种
     *
     * @return token:未登录状态返回空字符串
     */
    public static String getToken() {
        return SPUtil.getInstance().getData(App.getContext(),SPUtil.FILE_NAME, SPUtil.KEY_TOKEN);
    }


    /**
     * 获取指定文件大小
     *
     * @param file 文件
     * @return 文件长度
     */
    public static long getFileSize(File file) {
        long size = 0;
        try {
            if (file.exists()) {
                FileInputStream fis = null;
                fis = new FileInputStream(file);
                size = fis.available();
            } else {
                Log.e("获取文件大小", "文件不存在!");
            }
        } catch (Exception e) {

        }
        return size;
    }



    /**
     * 判断是否为正规手机号
     *
     * @param phone 手机号
     */
    public static boolean checkIsPhone(String phone) {
        boolean matches = false;
        String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
        if (phone.length() != 11) {
            Toast.makeText(App.getContext(), "手机号应为11位", Toast.LENGTH_SHORT).show();
        } else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(phone);
            matches = m.matches();
        }
        return matches;
    }

    public static String getResizeImg(String imageUrl, int width, int height) {
        return imageUrl + "?x-oss-process=image/resize,m_fill,h_" + height + ",w_" + width;
    }
    /**
     * 获得图片的像素方法
     *
     * @param bitmap
     */

    public static ArrayList<Integer> getPicturePixel(Bitmap bitmap) {

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        // 保存所有的像素的数组，图片宽×高
        int[] pixels = new int[width * height];

        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);

        ArrayList<Integer> rgb=new ArrayList<>();
        for (int i = 0; i < pixels.length; i++) {
            int clr = pixels[i];
            int red = (clr & 0x00ff0000) >> 16; // 取高两位
            int green = (clr & 0x0000ff00) >> 8; // 取中两位
            int blue = clr & 0x000000ff; // 取低两位
//            Log.d("tag", "r=" + red + ",g=" + green + ",b=" + blue);
            int color = Color.rgb(red, green, blue);
            //除去白色和黑色
            if (color!=Color.WHITE && color!=Color.BLACK){
                rgb.add(color);
            }
        }

        return rgb;
    }

}
