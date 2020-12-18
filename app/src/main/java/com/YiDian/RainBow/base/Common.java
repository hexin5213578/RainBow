package com.YiDian.RainBow.base;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.MediaMetadataRetriever;
import android.util.Log;
import android.widget.Toast;

import com.YiDian.RainBow.custom.HalfType;
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
import java.util.HashMap;
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

    /**
     * 获取token,不为空内种
     *
     * @return token:未登录状态返回空字符串
     */
    public static String getToken() {
        return SPUtil.getInstance().getData(App.getContext(),SPUtil.FILE_NAME, SPUtil.KEY_TOKEN);
    }

    //获取userid
    public static String getUserId() {
        return "1030";
    }

    //获取手机号
    public static String getPhone() {
        return SPUtil.getInstance().getData(App.getContext(),SPUtil.FILE_NAME, SPUtil.KEY_PHONE);
    }
    public static String getUserName(){
        return "何梦阳";
    }
    //获取是否已经登录过状态
    public static String getIsLogin() {
        return SPUtil.getInstance().getData(App.getContext(),SPUtil.FILE_NAME, SPUtil.IS_LOGIN);
    }

    //社会是否完善信息
    public static String getIsPerfect() {
        return SPUtil.getInstance().getData(App.getContext(),SPUtil.FILE_NAME, SPUtil.IS_PERFECT);
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
    /**
     * 将图片的四角圆弧化
     *
     * @param bitmap      原图
     * @param roundPixels 弧度
     * @param half        （上/下/左/右）半部分圆角
     * @return
     */
    public static Bitmap getRoundCornerImage(Bitmap bitmap, int roundPixels, HalfType half) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        Bitmap roundConcerImage = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);//创建一个和原始图片一样大小的位图
        Canvas canvas = new Canvas(roundConcerImage);//创建位图画布
        Paint paint = new Paint();//创建画笔

        Rect rect = new Rect(0, 0, width, height);//创建一个和原始图片一样大小的矩形
        RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);// 抗锯齿

        canvas.drawRoundRect(rectF, roundPixels, roundPixels, paint);//画一个基于前面创建的矩形大小的圆角矩形
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));//设置相交模式
        canvas.drawBitmap(bitmap, null, rect, paint);//把图片画到矩形去

        switch (half) {
            case LEFT:
                return Bitmap.createBitmap(roundConcerImage, 0, 0, width - roundPixels, height);
            case RIGHT:
                return Bitmap.createBitmap(roundConcerImage, width - roundPixels, 0, width - roundPixels, height);
            case TOP: // 上半部分圆角化 “- roundPixels”实际上为了保证底部没有圆角，采用截掉一部分的方式，就是截掉和弧度一样大小的长度
                return Bitmap.createBitmap(roundConcerImage, 0, 0, width, height - roundPixels);
            case BOTTOM:
                return Bitmap.createBitmap(roundConcerImage, 0, height - roundPixels, width, height - roundPixels);
            case ALL:
                return roundConcerImage;
            default:
                return roundConcerImage;
        }
    }

    /**
     *
     * @param videoUrl 视频路径
     * @return
     */
    public static Bitmap getNetVideoBitmap(String videoUrl) {
        Bitmap bitmap = null;

        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            //根据url获取缩略图
            retriever.setDataSource(videoUrl, new HashMap());
            //获得第一帧图片
            bitmap = retriever.getFrameAtTime();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } finally {
            retriever.release();
        }
        return bitmap;
    }
}
