package com.YiDian.RainBow.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.media.MediaPlayer;
import android.os.Environment;
import android.util.Base64;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ScrollView;

import androidx.annotation.DrawableRes;


import com.YiDian.RainBow.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BitmapUtil {

    private static RequestOptions options;

    static {
        initOptions();
    }

    private static void initOptions() {
        options = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)    //加载成功之前占位图
                .error(R.drawable.ic_launcher_background)    //加载错误之后的错误图
                .dontAnimate()
//                .override(400,400)	//指定图片的尺寸
                //指定图片的缩放类型为fitCenter （等比例缩放图片，宽或者是高等于ImageView的宽或者是高。）
//                .fitCenter()
                //指定图片的缩放类型为centerCrop （等比例缩放图片，直到图片的狂高都大于等于ImageView的宽度，然后截取中间的显示。）
//                .centerCrop()//指定图片的缩放类型为centerCrop
//                .circleCrop()//指定图片为圆形
//                .skipMemoryCache(true)	//跳过内存缓存
                .diskCacheStrategy(DiskCacheStrategy.ALL);    //缓存所有版本的图像
//                .diskCacheStrategy(DiskCacheStrategy.NONE)	//跳过磁盘缓存
//                .diskCacheStrategy(DiskCacheStrategy.DATA)	//只缓存原来分辨率的图片
//                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);	//只缓存最终的图片
    }

    private static RequestOptions buildOptions(@DrawableRes int res) {
        RequestOptions options = new RequestOptions()
                .placeholder(res)    //加载成功之前占位图
                .error(res)    //加载错误之后的错误图
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        return options;
    }

    /**
     * 显示gif
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void showGIF(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .asGif()
                .load(url)
                .apply(options)
                .into(imageView);
    }

    /**
     * 显示gif，自定义默认图
     *
     * @param context
     * @param url
     * @param res
     * @param imageView
     */
    public static void showGIF(Context context, String url, @DrawableRes int res, ImageView imageView) {
        Glide.with(context)
                .asGif()
                .load(url)
                .apply(buildOptions(res))
                .into(imageView);
    }

    /**
     * 显示图片，自定义option
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void showImage(Context context, String url, RequestOptions options, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .apply(options)
                .into(imageView);
    }

    /**
     * 显示图片，先显示默认图
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void showImage(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .apply(options)
                .into(imageView);
    }

    /**
     * 显示图片，先显示默认图
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void showImage(Context context, String url, ImageView imageView, RequestListener<Drawable> callback) {
        Glide.with(context)
                .load(url)
                .apply(options)
                .listener(callback)
                .into(imageView);
    }


    /**
     * 显示 圆角 图片，先显示默认图
     * scaleType 为 CenterCrop
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void showRadiusImageCenterCrop(Context context, String url, @DrawableRes int res, int radius, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .transform(new CenterCrop())
                .placeholder(res)    //加载成功之前占位图
                .error(res)    //加载错误之后的错误图
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.ALL);

        Glide.with(context)
                .load(url)
                .apply(options)
                .into(imageView);

    }

    /**
     * 加载本地图片
     *
     * @param url
     * @return
     */
    public static Bitmap getLocalBitmap(String url) {
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(url, options);
            //计算采样率
            options.inSampleSize = calculateInSampleSize(options, 768, 1024);
            options.inJustDecodeBounds = false;
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            Bitmap bitmap = BitmapFactory.decodeFile(url, options);
            //旋转图片
            int d = getPictureDegree(url);
            if (d != 0) {
                bitmap = rotateBitmap(bitmap, d);
            }
            return bitmap;
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void getCacheImage(Context context, String imgUrl, SimpleTarget<Bitmap> callback) {
        Glide.with(context).asBitmap().load(imgUrl).into(callback);
    }

    /**
     * 将文件生成Drawable
     *
     * @param path
     * @return
     */
    public static Drawable getDrawable(String path) {
        Drawable bd = BitmapDrawable.createFromPath(path);
        return bd;
    }

    /**
     * 返回Bitmap显示像素所占位数
     *
     * @param config
     * @return
     */
    public static int getBytesPerPixel(Bitmap.Config config) {
        if (config == Bitmap.Config.ARGB_8888) {
            return 4;
        } else if (config == Bitmap.Config.RGB_565) {
            return 2;
        } else if (config == Bitmap.Config.ARGB_4444) {
            return 2;
        } else if (config == Bitmap.Config.ALPHA_8) {
            return 1;
        }
        return 1;
    }

    /**
     * 获取图片旋转角度
     *
     * @param path
     * @return
     */
    public static int getPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 计算图片的缩放值
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    /**
     * 旋转图片
     *
     * @param bitmap
     * @param degree
     * @return
     */
    public static Bitmap rotateBitmap(Bitmap bitmap, int degree) {
        if (bitmap != null) {
            Matrix m = new Matrix();
            m.postRotate(degree);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);
            return bitmap;
        }
        return bitmap;
    }

    /**
     *  获取资源时长
     * @param source
     * @param context
     * @return
     */
    public static int getDuration(File source, Context context) {
        int duration = 0;
        MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            FileInputStream fis = new FileInputStream(source);
            mediaPlayer.setDataSource(fis.getFD());
            mediaPlayer.prepare();

            duration = mediaPlayer.getDuration();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return duration / 1000;

    }
    /**
     * 保存Bitmap为文件
     *
     * @param bitmap
     * @return
     */
    public static File saveBitmap(Bitmap bitmap) {
        FileOutputStream outputStream = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");

            File mFolder = new File(Environment.getExternalStorageDirectory() + "/temp.jpg");
            if (!mFolder.exists()) {
                mFolder.mkdirs();
            }
            File file = new File(mFolder, sdf.format(new Date()));
            outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.flush();
            outputStream.close();
            bitmap.recycle();
            return file;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 给定宽高，根据宽高计算采样率压缩图片 并 旋转图片
     *
     * @param photoPath 原图片路径
     * @param reqWidth  要求的宽
     * @param reqHeight 要求的高
     * @return
     */
    public static Bitmap zipBitmapWithReq(String photoPath, int reqWidth, int reqHeight) {
        // 压缩图片
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(photoPath, opts);
        opts.inSampleSize = BitmapUtil.calculateInSampleSize(opts, reqWidth, reqHeight);
        opts.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(photoPath, opts);
    }

    /**
     * 根据给定 质量百分比 压缩图片
     *
     * @param bitmap
     * @param quality 质量压缩比例(100为不压缩)
     * @return
     */
    public static Bitmap zipBitmapWithQuality(Bitmap bitmap, int quality) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
        byte[] bytes = baos.toByteArray();
        bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        return bitmap;
    }

    /**
     * 给定宽高，根据宽高计算采样率压缩图片并存为文件
     *
     * @param photoPath    原图片路径
     * @param saveFilePath 压缩后图片保存路径
     * @param fileName     压缩后图片文件名
     * @param reqWidth     要求的宽
     * @param reqHeight    要求的高
     * @return
     */
    public static File zipBitmapAndSave(String photoPath, String saveFilePath, String fileName, int reqWidth, int reqHeight) {
        Bitmap bitmap = BitmapUtil.zipBitmapWithReq(photoPath, reqWidth, reqHeight);
        // 旋转图片
        bitmap = BitmapUtil.rotateBitmap(bitmap, BitmapUtil.getPictureDegree(photoPath));
        return BitmapUtil.saveBitmap(bitmap);
    }

    /**
     * 给定宽高，根据宽高计算采样率，
     * 给定质量压缩比例
     * 多重压缩图片并存为文件
     *
     * @param photoPath    原图片路径
     * @param saveFilePath 压缩后图片保存路径
     * @param fileName     压缩后图片文件名
     * @param reqWidth     要求的宽
     * @param reqHeight    要求的高
     * @param quality      质量压缩比例(100为不压缩)
     * @return
     */
    public static File zipBitmapMultiAndSave(String photoPath, String saveFilePath, String fileName, int reqWidth, int reqHeight, int quality) {
        Bitmap bit = zipBitmapWithReq(photoPath, reqWidth, reqHeight);
        bit = zipBitmapWithQuality(bit, quality);
        // 旋转图片
        bit = BitmapUtil.rotateBitmap(bit, BitmapUtil.getPictureDegree(photoPath));
        return BitmapUtil.saveBitmap(bit);
    }

    /**
     * 把Bitmap转Byte
     */
    public static byte[] bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    /**
     * 根据高度放大, 并显示中间部分
     *
     * @param bitmap
     * @param windowWidth
     * @param windowHeight
     * @return
     */
    public static Bitmap compressBitmap(Bitmap bitmap, int windowWidth, int windowHeight) {
        Matrix matrix = new Matrix();
        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();
        float scaleY = windowHeight > bitmapHeight ? Float.valueOf(windowHeight) / Float.valueOf(bitmapHeight) : 1.0f;
        matrix.postScale(scaleY, scaleY);
        int offsetX = scaleY > 1 ? (int) ((bitmapWidth * scaleY - bitmapWidth) / 2) : 0;
        int showHeight = windowHeight > bitmapHeight ? windowHeight : bitmapHeight;
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmapWidth, bitmapHeight, matrix, true);
        try {
            return Bitmap.createBitmap(bitmap, offsetX, 0, windowWidth, showHeight);
        } catch (Exception e) {

        }
        return bitmap;
    }

    public static Bitmap getTransparentBitmap(Bitmap sourceImg, int number) {
        int[] argb = new int[sourceImg.getWidth() * sourceImg.getHeight()];
        sourceImg.getPixels(argb, 0, sourceImg.getWidth(), 0, 0, sourceImg.getWidth(), sourceImg.getHeight());// 获得图片的ARGB值
        number = number * 255 / 100;
        for (int i = 0; i < argb.length; i++) {
            argb[i] = (number << 24) | (argb[i] & 0x00FFFFFF);
        }
        sourceImg = Bitmap.createBitmap(argb, sourceImg.getWidth(), sourceImg.getHeight(), Bitmap.Config.ARGB_8888);
        return sourceImg;
    }


    /**
     * 截取ScrollView显示内容
     *
     * @param scrollView
     * @return
     */
    public static Bitmap getScrollViewBitmap(ScrollView scrollView) {
        int h = 0;
        Bitmap bitmap = null;
        for (int i = 0; i < scrollView.getChildCount(); i++) {
            h += scrollView.getChildAt(i).getHeight();
            scrollView.getChildAt(i).setBackgroundColor(Color.parseColor("#ffffff"));
        }
        bitmap = Bitmap.createBitmap(scrollView.getWidth(), h, Bitmap.Config.RGB_565);
        final Canvas canvas = new Canvas(bitmap);
        scrollView.draw(canvas);
        return bitmap;
    }


    /**
     * 截取WebView显示内容
     *
     * @param webView
     * @return
     */
    public static Bitmap getWebViewBitmap(WebView webView) {
        Bitmap bm = null;
        try {
            int height = (int) (webView.getContentHeight() * webView.getScale());
            int width = webView.getWidth();
            int pH = webView.getHeight();
            bm = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            Canvas canvas = new Canvas(bm);
            int top = height;
            while (top > 0) {
                if (top < pH) {
                    top = 0;
                } else {
                    top -= pH;
                }
                canvas.save();
                canvas.clipRect(0, top, width, top + pH);
                webView.scrollTo(0, top);
                webView.draw(canvas);
                canvas.restore();
            }
        } catch (OutOfMemoryError e) {
            if (bm != null) {
                bm.recycle();
                bm = null;
            }
        }
        return bm;
    }

    /**
     * 通过drawable文件名获取Drawable
     *
     * @param context
     * @param name
     * @return
     */
    public static Drawable getDrawableFromName(Context context, String name) {
        try {
            Resources resources = context.getResources();
            return resources.getDrawable(resources.getIdentifier(name, "drawable", context.getPackageName()));
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * base64字符串转换bitmap
     *
     * @param base64
     * @return
     */
    public static Bitmap base64ToBitmap(String base64) {
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray = Base64.decode(base64.split(",")[1], Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
