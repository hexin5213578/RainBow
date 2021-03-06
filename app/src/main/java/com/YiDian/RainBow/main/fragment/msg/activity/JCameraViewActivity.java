package com.YiDian.RainBow.main.fragment.msg.activity;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseAvtivity;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.base.Common;
import com.YiDian.RainBow.utils.BitmapUtil;
import com.cjt2325.cameralibrary.JCameraView;
import com.cjt2325.cameralibrary.listener.ClickListener;
import com.cjt2325.cameralibrary.listener.ErrorListener;
import com.cjt2325.cameralibrary.listener.JCameraListener;

import org.greenrobot.eventbus.EventBus;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.options.MessageSendingOptions;
import cn.jpush.im.api.BasicCallback;

public class JCameraViewActivity extends BaseAvtivity {
    @BindView(R.id.cameraview)
    JCameraView cameraview;
    private String id;
    private String username;
    private File file;
    private File videofile;

    @Override
    protected int getResId() {
        return R.layout.activity_jcamera;
    }

    @Override
    protected void getData() {

        Intent intent = getIntent();
        id = intent.getStringExtra("userid");

        username = Common.getUserName();

        if (Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        } else {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(option);
        }

        //????????????????????????
        cameraview.setSaveVideoPath(Environment.getExternalStorageDirectory().getPath() + File.separator + "JCamera");

        //??????????????????????????????????????????????????????????????????????????????
        cameraview.setFeatures(JCameraView.BUTTON_STATE_BOTH);

        //??????????????????
        cameraview.setMediaQuality(JCameraView.MEDIA_QUALITY_HIGH);
        //JCameraView??????
        cameraview.setErrorLisenter(new ErrorListener() {
            @Override
            public void onError() {
                //??????Camera????????????
                Log.i("CJT", "open camera error");
            }

            @Override
            public void AudioPermissionError() {
                //????????????????????????
                Log.i("CJT", "AudioPermissionError");
            }
        });

        cameraview.setJCameraLisenter(new JCameraListener() {
            @Override
            public void captureSuccess(Bitmap bitmap) {
                //????????????bitmap
                Log.i("CJT", "bitmap = " + bitmap.toString());

                // TODO: 2021/1/11 0011  ?????????????????? ?????????????????????????????????
                file = getFile(bitmap);
                Log.i("CJT", "file = " + file.getAbsolutePath());

                SendImgMessage(JCameraViewActivity.this, file);

                //????????????
                finish();
            }

            @Override
            public void recordSuccess(String url, Bitmap firstFrame) {
                //??????????????????
                Log.i("CJT", "url = " + url);

                // TODO: 2021/1/11 0011 ?????????????????? ?????????????????????????????????
                videofile = new File(url);
                //????????????
                MediaMetadataRetriever retriever = new MediaMetadataRetriever();
                retriever.setDataSource(url);
                String time = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);

                SendVideoMessage(JCameraViewActivity.this, videofile, firstFrame, "mp4", Integer.valueOf(time)/1000);
                //????????????
                finish();
            }
        });
        //????????????????????????
        cameraview.setLeftClickListener(new ClickListener() {
            @Override
            public void onClick() {
                JCameraViewActivity.this.finish();
            }
        });
        //????????????????????????
        cameraview.setRightClickListener(new ClickListener() {
            @Override
            public void onClick() {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        cameraview.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        cameraview.onPause();
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    /**
     * @param context
     * @param imageFile ????????????
     */
    public void SendImgMessage(Context context, File imageFile) {
        try {
            Message message = JMessageClient.createSingleImageMessage(id, Common.get_JG(), imageFile);

            message.setOnSendCompleteCallback(new BasicCallback() {
                @Override
                public void gotResult(int i, String s) {
                    Log.i("TAG", "register???code???" + i + "  msg???" + s);
                    if (i == 0) {
                        Log.d("xxx", "??????????????????");
                        EventBus.getDefault().post("???????????????");

                        imageFile.delete();
                    } else {

                        Toast.makeText(context, "????????????", Toast.LENGTH_SHORT).show();

                        Log.d("xxx", "????????????" + i + "?????????" + s);
                    }
                }
            });
            MessageSendingOptions options = new MessageSendingOptions();

            options.setNotificationTitle(username);
            options.setNotificationText("[????????????]");
            options.setCustomNotificationEnabled(true);
            options.setRetainOffline(true);
            options.setShowNotification(true);
            JMessageClient.sendMessage(message, options);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param context
     * @param VideoFile   ????????????
     * @param thumbImage  ???????????????
     * @param thumbFormat ???????????????
     * @param duration    ??????
     */
    public void SendVideoMessage(Context context, File VideoFile, Bitmap thumbImage, String thumbFormat, int duration) {
        try {
            Message message = JMessageClient.createSingleVideoMessage(id, Common.get_JG(), thumbImage, thumbFormat, VideoFile, "", duration);

            message.setOnSendCompleteCallback(new BasicCallback() {
                @Override
                public void gotResult(int i, String s) {
                    Log.i("TAG", "register???code???" + i + "  msg???" + s);
                    if (i == 0) {
                        Log.d("xxx", "??????????????????");
                        EventBus.getDefault().post("???????????????");

                    } else {
                        Toast.makeText(context, "??????????????????", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            MessageSendingOptions options = new MessageSendingOptions();

            options.setNotificationTitle(username);
            options.setNotificationText("[????????????]");
            options.setCustomNotificationEnabled(true);
            options.setRetainOffline(true);
            options.setShowNotification(true);
            JMessageClient.sendMessage(message, options);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public File getFile(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getExternalFilesDir(Environment.DIRECTORY_MUSIC);
        File file = new File(directory+"/temp.jpg");

        try {
                file.createNewFile();
                FileOutputStream fos = new FileOutputStream(file);
                InputStream is = new ByteArrayInputStream(baos.toByteArray());
                int x = 0;
                byte[] b = new byte[1024 * 100];
                while ((x = is.read(b)) != -1) {
                    fos.write(b, 0, x);
                }
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        return file;
    }
}
