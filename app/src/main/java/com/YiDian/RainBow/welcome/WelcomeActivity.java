package com.YiDian.RainBow.welcome;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseAvtivity;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.base.Common;
import com.YiDian.RainBow.main.activity.MainActivity;
import com.YiDian.RainBow.regist.activity.RegistActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import javax.xml.xpath.XPathExpression;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;

public class WelcomeActivity extends BaseAvtivity{


    private Handler handler;

    @Override
    protected int getResId() {
        return R.layout.activity_welcome;
    }
    @Override
    protected void getData() {

        // 设置标题栏颜色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            Window window = WelcomeActivity.this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(WelcomeActivity.this.getResources().getColor(R.color.white));
        }
        requestPermission();

        handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

               /* String isLogin = Common.getIsLogin();
                if(isLogin!=null){
                    if(isLogin.equals("0")){
                        Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }else{
                        Intent intent = new Intent(WelcomeActivity.this, RegistActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }else{
                    Intent intent = new Intent(WelcomeActivity.this, RegistActivity.class);
                    startActivity(intent);
                    finish();
                }*/
            }
        }, 2000);//2秒后执行Runnable中的run方法
    }
    //安卓10.0定位权限
    public void Request() {
        if (Build.VERSION.SDK_INT >= 23) {
            int request = ContextCompat.checkSelfPermission(WelcomeActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);
            if (request != PackageManager.PERMISSION_GRANTED)//缺少权限，进行权限申请
            {
                ActivityCompat.requestPermissions(WelcomeActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);
                return;//
            } else {
                JMessageClient.login("15686", "15686", new BasicCallback() {
                    @Override
                    public void gotResult(int i, String s) {
                        if (i==0){
                            Log.d("xxx", "15686 极光登录状态为" + i + "原因为" + s);

                            Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
               /*     Bitmap bitmap=BitmapFactory.decodeResource(getResources(),R.mipmap.headimg3,null);//将资源文件转化为bitmap

                    File file = getFile(bitmap);

                    Log.d("xxx",file.getAbsolutePath());

                    JMessageClient.updateUserAvatar(file, new BasicCallback() {
                        @Override
                        public void gotResult(int i, String s) {
                            if (i==0){
                                Log.d("xxx","15686 头像设置成功");
                            }else {
                                Log.d("xxx",s);
                            }
                        }
                    });
*/
                            /*UserInfo myInfo = JMessageClient.getMyInfo();
                            Log.d("xxx","15686信息为"+myInfo.toJson());

                            String avatarFile = myInfo.getAvatar();
                            if (!avatarFile.equals(null)){
                                Log.d("xxx","存在头像");
                            }else{
                                Log.d("xxx","不存在头像");
                            }*/

                            //JMessageClient.deleteSingleConversation("15686","87ce5706efafab51ddd2be08");

                        }
                    }
                });

            }
        } else {

        }
    }
    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // 先判断有没有权限
            if (Environment.isExternalStorageManager()) {
                Toast.makeText(this, "有权限了", Toast.LENGTH_SHORT).show();

                Request();
            } else {
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                intent.setData(Uri.parse("package:" + WelcomeActivity.this.getPackageName()));
                startActivityForResult(intent, 101);
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Request();

            // 先判断有没有权限
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            } else {

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 101);
            }
        } else {
            Request();

        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (Environment.isExternalStorageManager()) {
                Toast.makeText(this, "存储权限获取成功", Toast.LENGTH_SHORT).show();

                Request();

            } else {
                Toast.makeText(this, "存储权限获取失败", Toast.LENGTH_SHORT).show();

                Request();
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 100) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    JMessageClient.login("15686", "15686", new BasicCallback() {
                        @Override
                        public void gotResult(int i, String s) {
                            if (i==0){
                                Log.d("xxx", "15686 极光登录状态为" + i + "原因为" + s);

                                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
               /*     Bitmap bitmap=BitmapFactory.decodeResource(getResources(),R.mipmap.headimg3,null);//将资源文件转化为bitmap

                    File file = getFile(bitmap);

                    Log.d("xxx",file.getAbsolutePath());

                    JMessageClient.updateUserAvatar(file, new BasicCallback() {
                        @Override
                        public void gotResult(int i, String s) {
                            if (i==0){
                                Log.d("xxx","15686 头像设置成功");
                            }else {
                                Log.d("xxx",s);
                            }
                        }
                    });
*/
                            /*UserInfo myInfo = JMessageClient.getMyInfo();
                            Log.d("xxx","15686信息为"+myInfo.toJson());

                            String avatarFile = myInfo.getAvatar();
                            if (!avatarFile.equals(null)){
                                Log.d("xxx","存在头像");
                            }else{
                                Log.d("xxx","不存在头像");
                            }*/

                                //JMessageClient.deleteSingleConversation("15686","87ce5706efafab51ddd2be08");

                            }
                        }
                    });
                } else {
                    //如果拒绝授予权限,且勾选了再也不提醒
                    if (!shouldShowRequestPermissionRationale(permissions[0])) {

                    } else {
                    }
                }
            }
        }else{
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "存储权限获取成功", Toast.LENGTH_SHORT).show();
                Request();

            } else {
                Toast.makeText(this, "存储权限获取失败", Toast.LENGTH_SHORT).show();

                Request();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
