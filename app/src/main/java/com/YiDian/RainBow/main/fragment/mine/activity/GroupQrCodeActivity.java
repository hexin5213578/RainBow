package com.YiDian.RainBow.main.fragment.mine.activity;

import android.Manifest;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.App;
import com.YiDian.RainBow.base.BaseAvtivity;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.base.Common;
import com.YiDian.RainBow.imgroup.bean.SaveIdAndHeadImgBean;
import com.YiDian.RainBow.utils.EncodingUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.leaf.library.StatusBarUtil;
import com.tencent.connect.share.QQShare;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

import butterknife.BindView;

//群组二维码

public class GroupQrCodeActivity extends BaseAvtivity implements View.OnClickListener {
    @BindView(R.id.iv_back)
    LinearLayout ivBack;
    @BindView(R.id.rl_top)
    RelativeLayout rlTop;
    @BindView(R.id.iv_Qrcode)
    ImageView ivQrcode;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_id)
    TextView tvId;
    @BindView(R.id.iv_headimg)
    ImageView ivHeadimg;
    @BindView(R.id.ll_save)
    LinearLayout llSave;
    @BindView(R.id.ll_share)
    LinearLayout llShare;
    @BindView(R.id.rl_qrcode)
    RelativeLayout rlQrcode;
    private Bitmap qrCode;
    private Bitmap bitmap1;
    private PopupWindow mPopupWindow;
    private View view;
    private Tencent mTencent;
    String TAG = "MyQrCodeActivity";
    private File file;
    String filename;//声明文件名
    private String name;
    private int id;
    private String headimg;

    @Override
    protected int getResId() {
        return R.layout.activity_groupqrcode;
    }


    @SuppressLint("NewApi")
    @Override
    protected void getData() {
        //设置状态栏字体颜色及背景
        StatusBarUtil.setTransparentForWindow(GroupQrCodeActivity.this);
        StatusBarUtil.setDarkMode(this);
        //申请开启内存卡权限
        if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) && (this.checkSelfPermission
                (Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
            //请求权限
            this.requestPermissions(new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        //绑定单击事件
        ivBack.setOnClickListener(this);
        llSave.setOnClickListener(this);
        llShare.setOnClickListener(this);

        Intent intent = getIntent();
        SaveIdAndHeadImgBean msg = (SaveIdAndHeadImgBean) intent.getSerializableExtra("msg");

        headimg = msg.getHeadimg();
        id = msg.getId();
        name = msg.getName();

        //加载一张圆角头像
        Glide.with(GroupQrCodeActivity.this).load(headimg).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(ivHeadimg);


        //将使用 彩虹 + 用户ID 生成二维码
        qrCode = EncodingUtils.createQRCode("彩虹App内群组"+id, 200, 200, null);
        //获取用户ID
        //先获取用户信息  设置数据

        tvId.setText("ID:"+id);
        tvName.setText(name+"");
        //设置二维码
        ivQrcode.setImageBitmap(qrCode);

        //将布局转化为bitmap
        bitmap1 = loadBitmapFromView(rlQrcode);

        //腾讯AppId(替换你自己App Id)、上下文
        mTencent = Tencent.createInstance("101906973", this);
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                //退出界面
                finish();
                break;
            case R.id.ll_save:
                //将二维码保存到本地相册
                saveScreenShot(bitmap1);
                break;
            case R.id.ll_share:
                //将二维码分享给别人
                showSelect();
                saveScreenShot(bitmap1);
                break;
        }
    }

    //将图片文件保存到相册
    private void saveScreenShot(Bitmap bitmap) {
        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        OutputStream outStream = null;
        //以保存时间为文件名
        filename = "MyQrcode";
        file = new File(extStorageDirectory, filename + ".JPEG");//创建文件，第一个参数为路径，第二个参数为文件名
        try {
            outStream = new FileOutputStream(file);//创建输入流
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
            outStream.close();

            // 这三行可以实现相册更新
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri uri = Uri.fromFile(file);
            intent.setData(uri);
            sendBroadcast(intent);//这个广播的目的就是更新图库，发了这个广播进入相册就可以找到你保存的图片了！

            Toast.makeText(this, "已保存到相册",
                    Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "exception:" + e,
                    Toast.LENGTH_SHORT).show();

        }
    }

    public void showSelect() {
        //添加成功后处理
        mPopupWindow = new PopupWindow();
        mPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        view = LayoutInflater.from(GroupQrCodeActivity.this).inflate(R.layout.dialog_share, null);

        LinearLayout qq = view.findViewById(R.id.ll_share_qq);
        LinearLayout space = view.findViewById(R.id.ll_share_space);
        LinearLayout wechat = view.findViewById(R.id.ll_share_Wechat);
        LinearLayout moments = view.findViewById(R.id.ll_share_wechatmoments);

        qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //分享到QQ好友
                onClickShare();
            }
        });
        space.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //分享到QQ空间
                onClickShareQzone();
            }
        });
        wechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //分享到微信好友
                onclickShareWechatFriend();
            }
        });
        moments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //分享到朋友圈
                onclickShareWechatmoments();
            }
        });
        //popwindow设置属性
        mPopupWindow.setContentView(view);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setWindowAlpa(false);
            }
        });
        show(view);
    }

    //分享到QQ信息
    private void onClickShare() {
        Bundle params = new Bundle();

        params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, file.toString());
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE);
        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE);
        mTencent.shareToQQ(GroupQrCodeActivity.this, params, new IUiListener() {
            @Override
            public void onComplete(Object o) {
                Log.e(TAG, "分享成功: " + o.toString());
            }

            @Override
            public void onError(UiError uiError) {
                Log.e(TAG, "分享失败: " + uiError.toString());
            }

            @Override
            public void onCancel() {
                Log.e(TAG, "取消分享");

            }
        });
    }

    //分享到QQ空间
    private void onClickShareQzone() {
        Bundle params = new Bundle();

        params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, file.toString());
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE);
        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
        mTencent.shareToQQ(GroupQrCodeActivity.this, params, new IUiListener() {
            @Override
            public void onComplete(Object o) {
            }

            @Override
            public void onError(UiError uiError) {

            }

            @Override
            public void onCancel() {

            }
        });
    }

    //分享到微信好友
    private void onclickShareWechatFriend() {
        //初始化 WXImageObject 和 WXMediaMessage 对象
        WXImageObject imgObj = new WXImageObject(bitmap1);
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObj;

        //构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("img");
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneSession;
        //调用api接口，发送数据到微信
        App.getWXApi().sendReq(req);

    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    //分享到微信朋友圈
    private void onclickShareWechatmoments() {
        //初始化 WXImageObject 和 WXMediaMessage 对象
        WXImageObject imgObj = new WXImageObject(bitmap1);
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObj;

        //构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("img");
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneTimeline;
        //调用api接口，发送数据到微信
        App.getWXApi().sendReq(req);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //返回界面关闭弹出框
        dismiss();
    }

    //获取布局的bitmap
    public static Bitmap loadBitmapFromView(View v) {
        //先进行测量，这样不会获取为null
        v.measure(
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
        v.buildDrawingCache();
        Bitmap bitmap = v.getDrawingCache();
        //假如图片不符合要求，可以使用Bitmap.createBitmap( )方法处理图片
        return bitmap;
    }

    //设置透明度
    public void setWindowAlpa(boolean isopen) {
        if (Build.VERSION.SDK_INT < 11) {
            return;
        }
        final Window window = this.getWindow();
        final WindowManager.LayoutParams lp = window.getAttributes();
        window.setFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND, WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        ValueAnimator animator;
        if (isopen) {
            animator = ValueAnimator.ofFloat(1.0f, 0.5f);
        } else {
            animator = ValueAnimator.ofFloat(0.5f, 1.0f);
        }
        animator.setDuration(400);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float alpha = (float) animation.getAnimatedValue();
                lp.alpha = alpha;
                window.setAttributes(lp);
            }
        });
        animator.start();
    }

    /**
     * 显示PopupWindow
     */

    private void show(View v) {
        if (mPopupWindow != null && !mPopupWindow.isShowing()) {
            mPopupWindow.showAtLocation(v, Gravity.CENTER_HORIZONTAL, 0, 0);
        }
        setWindowAlpa(true);
    }

    /**
     * 消失PopupWindow
     */
    public void dismiss() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
    }
}
