package com.YiDian.RainBow.wxapi;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WXEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {

    private IWXAPI iwxapi;
    private ProgressDialog mProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //接收到分享以及登录的intent传递handleIntent方法，处理结果
        iwxapi = WXAPIFactory.createWXAPI(this, "wxf8a5f128098b4df3", false);
        iwxapi.handleIntent(getIntent(), this);

    }

        @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        //登录回调
        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                switch (baseResp.getType()){
                        //微信分享回调
                    case ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX:

                        Toast.makeText(this, "分享成功", Toast.LENGTH_SHORT).show();
                        finish();

                        break;
                        //微信登录回调
                    case ConstantsAPI.COMMAND_SENDAUTH:
                        String code = ((SendAuth.Resp) baseResp).code;
                        //获取accesstoken
                        getAccessToken(code);

                        break;
                    default:
                        finish();
                        break;
                }
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED://用户拒绝授权
                switch (baseResp.getType()){
                        //微信分享回调
                    case ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX:
                        //微信登录回调
                    case ConstantsAPI.COMMAND_SENDAUTH:
                        finish();
                        Toast.makeText(this, "用户拒绝授权", Toast.LENGTH_SHORT).show();
                        break;
                }
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL://用户取消
                switch (baseResp.getType()){
                    //微信分享回调
                    case ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX:
                        finish();
                        Toast.makeText(this, "用户取消分享", Toast.LENGTH_SHORT).show();
                        break;
                    //微信登录回调
                    case ConstantsAPI.COMMAND_SENDAUTH:
                        finish();
                        Toast.makeText(this, "用户取消登录", Toast.LENGTH_SHORT).show();
                        break;
                }
                break;
            default:
                finish();
                break;
        }

    }
    private void getAccessToken(String code) {
        createProgressDialog();
        //获取授权
        StringBuffer loginUrl = new StringBuffer();
        loginUrl.append("https://api.weixin.qq.com/sns/oauth2/access_token")
                .append("?appid=")
                .append("wxf8a5f128098b4df3")
                .append("&secret=")
                .append("8be2e90b3272cbcc121e13f6adc97ad5")
                .append("&code=")
                .append(code)
                .append("&grant_type=authorization_code");
        Log.d("urlurl", loginUrl.toString());

        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(loginUrl.toString())
                .get()//默认就是GET请求，可以不写
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("fan12", "onFailure: ");
                mProgressDialog.dismiss();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseInfo= response.body().string();
                Log.d("fan12", "onResponse: " +responseInfo);
                String access = null;
                String openId = null;
                try {
                    JSONObject jsonObject = new JSONObject(responseInfo);
                    access = jsonObject.getString("access_token");
                    openId = jsonObject.getString("openid");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                getUserInfo(access, openId);
            }
        });
    }
    private void createProgressDialog() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);//转盘
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setTitle("提示");
        mProgressDialog.setMessage("登录中，请稍后");
        mProgressDialog.show();
    }
    private void getUserInfo(String access, String openid) {
        String getUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=" + access + "&openid=" + openid;
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(getUserInfoUrl)
                .get()//默认就是GET请求，可以不写
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("fan12", "onFailure: ");
                mProgressDialog.dismiss();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseInfo= response.body().string();
                Log.d("fan123", "onResponse: " + responseInfo);
                SharedPreferences.Editor editor= getSharedPreferences("wechatInfo", MODE_PRIVATE).edit();
                editor.putString("responseInfo", responseInfo);
                editor.commit();
                finish();
                mProgressDialog.dismiss();
            }
        });
    }
}
