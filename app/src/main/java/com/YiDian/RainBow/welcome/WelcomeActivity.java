package com.YiDian.RainBow.welcome;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

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
        Request();
        handler = new Handler();

        JMessageClient.login("15686", "15686", new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                if (i==0){
                    Log.d("xxx", "15686 极光登录状态为" + i + "原因为" + s);


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
                    UserInfo myInfo = JMessageClient.getMyInfo();
                    Log.d("xxx","15686信息为"+myInfo.toJson());

                    String avatarFile = myInfo.getAvatar();
                    if (!avatarFile.equals(null)){
                        Log.d("xxx","存在头像");
                    }else{
                        Log.d("xxx","不存在头像");
                    }

                    //JMessageClient.deleteSingleConversation("15686","87ce5706efafab51ddd2be08");

                }
            }
        });

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
                        Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
            }
        } else {

        }
    }
    @Override
    protected BasePresenter initPresenter() {
        return null;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 100) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    //如果拒绝授予权限,且勾选了再也不提醒
                    if (!shouldShowRequestPermissionRationale(permissions[0])) {

                    } else {
                    }
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
