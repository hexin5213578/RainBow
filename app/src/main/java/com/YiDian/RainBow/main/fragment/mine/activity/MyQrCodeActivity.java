package com.YiDian.RainBow.main.fragment.mine.activity;

import android.Manifest;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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
import com.YiDian.RainBow.utils.EncodingUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.leaf.library.StatusBarUtil;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jmessage.support.okhttp3.internal.Util;

import static com.tencent.connect.share.QzonePublish.PUBLISH_TO_QZONE_TYPE_PUBLISHMOOD;
import static com.tencent.connect.share.QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT;

//???????????????

public class MyQrCodeActivity extends BaseAvtivity implements View.OnClickListener {
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
    String filename;//???????????????
    private int userid;

    @Override
    protected int getResId() {
        return R.layout.activity_myqrcode;
    }


    @SuppressLint("NewApi")
    @Override
    protected void getData() {
        //????????????????????????????????????
        StatusBarUtil.setTransparentForWindow(MyQrCodeActivity.this);
        StatusBarUtil.setDarkMode(this);
        //???????????????????????????
        if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) && (this.checkSelfPermission
                (Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
            //????????????
            this.requestPermissions(new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        //??????????????????
        ivBack.setOnClickListener(this);
        llSave.setOnClickListener(this);
        llShare.setOnClickListener(this);


        String userName = Common.getUserName();
        String headImg = Common.getHeadImg();

        //????????????????????????
        Glide.with(MyQrCodeActivity.this).load(headImg).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(ivHeadimg);

        userid = Integer.valueOf(Common.getUserId());

        //????????? ?????? + ??????ID ???????????????
        qrCode = EncodingUtils.createQRCode("??????App?????????"+userid, 200, 200, null);
        //????????????ID
        //?????????????????????  ????????????

        tvId.setText("ID:"+userid);
        tvName.setText(userName+"");
        //???????????????
        ivQrcode.setImageBitmap(qrCode);

        //??????????????????bitmap
        bitmap1 = loadBitmapFromView(rlQrcode);

        //??????AppId(???????????????App Id)????????????
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
                //????????????
                finish();
                break;
            case R.id.ll_save:
                //?????????????????????????????????
                saveScreenShot(bitmap1);
                break;
            case R.id.ll_share:
                //???????????????????????????
                showSelect();

                saveScreenShot(bitmap1);
                break;
        }
    }

    //??????????????????????????????
    private void saveScreenShot(Bitmap bitmap) {
        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        OutputStream outStream = null;
        //???????????????????????????
        filename = "MyQrcode";
        file = new File(extStorageDirectory, filename + ".JPEG");//?????????????????????????????????????????????????????????????????????
        try {
            outStream = new FileOutputStream(file);//???????????????
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
            outStream.close();

            // ?????????????????????????????????
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri uri = Uri.fromFile(file);
            intent.setData(uri);
            sendBroadcast(intent);//???????????????????????????????????????????????????????????????????????????????????????????????????????????????

            Toast.makeText(this, "??????????????????",
                    Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "exception:" + e,
                    Toast.LENGTH_SHORT).show();

        }
    }

    public void showSelect() {
        //?????????????????????
        mPopupWindow = new PopupWindow();
        mPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        view = LayoutInflater.from(MyQrCodeActivity.this).inflate(R.layout.dialog_share, null);

        LinearLayout qq = view.findViewById(R.id.ll_share_qq);
        LinearLayout space = view.findViewById(R.id.ll_share_space);
        LinearLayout wechat = view.findViewById(R.id.ll_share_Wechat);
        LinearLayout moments = view.findViewById(R.id.ll_share_wechatmoments);

        qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //?????????QQ??????
                onClickShare();
            }
        });
        space.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //?????????QQ??????
                onClickShareQzone();
            }
        });
        wechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //?????????????????????
                onclickShareWechatFriend();
            }
        });
        moments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //??????????????????
                onclickShareWechatmoments();
            }
        });
        //popwindow????????????
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

    //?????????QQ??????
    private void onClickShare() {
        Bundle params = new Bundle();

        params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, file.toString());
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE);
        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE);
        mTencent.shareToQQ(MyQrCodeActivity.this, params, new IUiListener() {
            @Override
            public void onComplete(Object o) {
                Log.e(TAG, "????????????: " + o.toString());
            }

            @Override
            public void onError(UiError uiError) {
                Log.e(TAG, "????????????: " + uiError.toString());
            }

            @Override
            public void onCancel() {
                Log.e(TAG, "????????????");

            }
        });
    }

    //?????????QQ??????
    private void onClickShareQzone() {
        Bundle params = new Bundle();

        params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, file.toString());
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE);
        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
        mTencent.shareToQQ(MyQrCodeActivity.this, params, new IUiListener() {
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

    //?????????????????????
    private void onclickShareWechatFriend() {
        //????????? WXImageObject ??? WXMediaMessage ??????
        WXImageObject imgObj = new WXImageObject(bitmap1);
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObj;

        //????????????Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("img");
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneSession;
        //??????api??????????????????????????????
        App.getWXApi().sendReq(req);

    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    //????????????????????????
    private void onclickShareWechatmoments() {
        //????????? WXImageObject ??? WXMediaMessage ??????
        WXImageObject imgObj = new WXImageObject(bitmap1);
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObj;

        //????????????Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("img");
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneTimeline;
        //??????api??????????????????????????????
        App.getWXApi().sendReq(req);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //???????????????????????????
        dismiss();
    }

    //???????????????bitmap
    public static Bitmap loadBitmapFromView(View v) {
        //???????????????????????????????????????null
        v.measure(
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
        v.buildDrawingCache();
        Bitmap bitmap = v.getDrawingCache();
        //??????????????????????????????????????????Bitmap.createBitmap( )??????????????????
        return bitmap;
    }

    //???????????????
    public void setWindowAlpa(boolean isopen) {
        if (android.os.Build.VERSION.SDK_INT < 11) {
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
     * ??????PopupWindow
     */

    private void show(View v) {
        if (mPopupWindow != null && !mPopupWindow.isShowing()) {
            mPopupWindow.showAtLocation(v, Gravity.CENTER_HORIZONTAL, 0, 0);
        }
        setWindowAlpa(true);
    }

    /**
     * ??????PopupWindow
     */
    public void dismiss() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
    }
}
