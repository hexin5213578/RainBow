package com.YiDian.RainBow.main.fragment.mine.activity;

import android.Manifest;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Environment;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseAvtivity;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.base.Common;
import com.YiDian.RainBow.login.activity.CompleteMsgActivity;
import com.YiDian.RainBow.login.bean.ComPleteMsgBean;
import com.YiDian.RainBow.setup.bean.CheckNickNameBean;
import com.YiDian.RainBow.utils.BasisTimesUtils;
import com.YiDian.RainBow.utils.MD5Utils;
import com.YiDian.RainBow.utils.NetUtils;
import com.YiDian.RainBow.utils.SPUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.leaf.library.StatusBarUtil;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;
import org.lym.image.select.PictureSelector;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.DownloadAvatarCallback;
import cn.jpush.im.android.api.callback.GetAvatarBitmapCallback;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

//????????????
public class EditMsgActivity extends BaseAvtivity implements View.OnClickListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_back)
    LinearLayout ivBack;
    @BindView(R.id.iv_headimg)
    ImageView ivHeadimg;
    @BindView(R.id.rl_change_headimg)
    RelativeLayout rlChangeHeadimg;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.rl_name)
    RelativeLayout rlName;
    @BindView(R.id.tv_age)
    TextView tvAge;
    @BindView(R.id.rl_age)
    RelativeLayout rlAge;
    @BindView(R.id.tv_qianming)
    TextView tvQianming;
    @BindView(R.id.rl_qianming)
    RelativeLayout rlQianming;
    @BindView(R.id.tv_myrole)
    TextView tvMyrole;
    @BindView(R.id.rl_myrole)
    RelativeLayout rlMyrole;
    @BindView(R.id.tv_mystate)
    TextView tvMystate;
    @BindView(R.id.rl_ganqingstate)
    RelativeLayout rlGanqingstate;
    private PopupWindow mPopupWindow;
    private PopupWindow mPopupWindow1;
    private String path;
    private UploadManager uploadManager;
    private String token;
    private static final String serverPath = "http://img.rianbow.cn/";
    private String url="";
    private UserInfo userInfo;
    private int userid;
    private String username;
    private String qm;
    String time = "";
    String role = "";
    String single = "";
    int a = 0;

    private String birthday;
    private PopupWindow mPopupWindow2;
    private String Userrole;
    private String issingle;
    private PopupWindow mPopupWindow3;
    private String headimg;

    @Override
    protected int getResId() {
        return R.layout.activity_editmsg;
    }

    @Override
    protected void getData() {
        //????????????????????????????????????
        StatusBarUtil.setGradientColor(this, toolbar);
        StatusBarUtil.setDarkMode(this);

        rlChangeHeadimg.setOnClickListener(this);
        rlName.setOnClickListener(this);
        rlAge.setOnClickListener(this);
        rlQianming.setOnClickListener(this);
        rlMyrole.setOnClickListener(this);
        rlGanqingstate.setOnClickListener(this);
        ivBack.setOnClickListener(this);

        userid = Integer.parseInt(Common.getUserId());
        token = Common.getToken();

        if (Build.VERSION.SDK_INT >= 23) {
            int REQUEST_CODE_CONTACT = 101;
            String[] permissions = {
                    Manifest.permission.WRITE_EXTERNAL_STORAGE};
            //????????????????????????
            for (String str : permissions) {
                if (EditMsgActivity.this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    //????????????
                    EditMsgActivity.this.requestPermissions(permissions, REQUEST_CODE_CONTACT);
                    return;
                } else {
                    //??????????????????????????????????????????????????????
                }
            }
        }
        username = Common.getUserName();
        qm = Common.getQM();
        birthday = Common.getBirthday();
        Userrole = Common.getRole();
        issingle = SPUtil.getInstance().getData(EditMsgActivity.this, SPUtil.FILE_NAME, SPUtil.ISSINGLE);
        headimg = Common.getHeadImg();

        tvName.setText(username);

        // TODO: 2020/11/26 0026 ????????????????????????????????????
        if (birthday!=null && !birthday.equals("")){
            tvAge.setText(birthday);
        }else{
            tvAge.setText("?????????");

        }
        if (qm!=null && !qm.equals("")){
            tvQianming.setText(qm);
        }else{
            tvQianming.setText("?????????");
        }

        if (Userrole!=null && !Userrole.equals("") ){
            tvMyrole.setText(Userrole);
        }else{
            tvMyrole.setText("?????????");
        }

        if (issingle!=null && !issingle.equals("")){
            if (issingle.equals("1")){
                tvMystate.setText("??????");
            }else if(issingle.equals("2")){
                tvMystate.setText("?????????");
            }else{
                tvMystate.setText("??????");
            }
        }else{
            tvMystate.setText("?????????");

        }
        //???????????????
        if (headimg!=null && !headimg.equals("")){
            Glide.with(this).load(headimg).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(ivHeadimg);
        }else{
            Glide.with(this).load(R.mipmap.headimg3).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(ivHeadimg);
        }


        userInfo = new UserInfo() {
            @Override
            public String getNotename() {
                return null;
            }

            @Override
            public String getNoteText() {
                return null;
            }

            @Override
            public long getBirthday() {
                return 0;
            }

            @Override
            public File getAvatarFile() {
                return null;
            }

            @Override
            public void getAvatarFileAsync(DownloadAvatarCallback downloadAvatarCallback) {

            }

            @Override
            public void getAvatarBitmap(GetAvatarBitmapCallback getAvatarBitmapCallback) {

            }

            @Override
            public File getBigAvatarFile() {
                return null;
            }

            @Override
            public void getBigAvatarBitmap(GetAvatarBitmapCallback getAvatarBitmapCallback) {

            }

            @Override
            public int getBlacklist() {
                return 0;
            }

            @Override
            public int getNoDisturb() {
                return 0;
            }

            @Override
            public boolean isFriend() {
                return false;
            }

            @Override
            public String getAppKey() {
                return null;
            }

            @Override
            public void setUserExtras(Map<String, String> map) {

            }

            @Override
            public void setUserExtras(String s, String s1) {

            }

            @Override
            public void setBirthday(long l) {

            }

            @Override
            public void setNoDisturb(int i, BasicCallback basicCallback) {

            }

            @Override
            public void removeFromFriendList(BasicCallback basicCallback) {

            }

            @Override
            public void updateNoteName(String s, BasicCallback basicCallback) {

            }

            @Override
            public void updateNoteText(String s, BasicCallback basicCallback) {

            }

            @Override
            public String getDisplayName() {
                return null;
            }
        };
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            //????????????
            case R.id.rl_change_headimg:
                if(Build.VERSION.SDK_INT==30){
                    Toast.makeText(this, "Android11????????????????????????", Toast.LENGTH_SHORT).show();
                }else{
                    PictureSelector
                            .with(this)
                            .selectSpec()
                            .setOpenCamera()
                            .needCrop()
                            .setOutputX(200)
                            .setOutputY(200)
                            //?????????????????????????????????????????????????????????Android7.0??????????????????
                            //???manifest???????????????????????????provider
                            .setAuthority("com.YiDian.RainBow.utils.MyFileProvider")
                            .startForResult(100);
                }
                break;
            //????????????
            case R.id.rl_name:
                showChangeName();
                break;
            //??????
            case R.id.rl_age:

                birthday = SPUtil.getInstance().getData(EditMsgActivity.this, SPUtil.FILE_NAME, SPUtil.BIRTHDAY);

                if (birthday!=null && !birthday.equals("")){
                    String[] split = birthday.split("-");

                    BasisTimesUtils.showDatePickerDialog(EditMsgActivity.this, "??????????????????", Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[1]), new BasisTimesUtils.OnDatePickerListener() {
                        @Override
                        public void onConfirm(int year, int month, int dayOfMonth) {
                            if(month<10 && dayOfMonth<10){
                                time = year + "-0" + month + "-0" + dayOfMonth;
                            }else if (month<10){
                                time = year + "-0" + month + "-" + dayOfMonth;
                            }else if(dayOfMonth<10){
                                time = year + "-" + month + "-0" + dayOfMonth;
                            }else{
                                time = year + "-" + month + "-" + dayOfMonth;
                            }

                            // TODO: 2021/1/7 0007 ??????????????????????????????????????????
                            NetUtils.getInstance()
                                    .getApis()
                                    .doComPleteAge(userid,time)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Observer<ComPleteMsgBean>() {
                                        @Override
                                        public void onSubscribe(Disposable d) {

                                        }

                                        @Override
                                        public void onNext(ComPleteMsgBean comPleteMsgBean) {
                                            if (comPleteMsgBean.getMsg().equals("?????????????????????")){
                                                tvAge.setText(time);
                                                SPUtil.getInstance().saveData(EditMsgActivity.this,SPUtil.FILE_NAME,SPUtil.BIRTHDAY,time);
                                            }
                                        }

                                        @Override
                                        public void onError(Throwable e) {

                                        }

                                        @Override
                                        public void onComplete() {

                                        }
                                    });

                        }

                        @Override
                        public void onCancel() {

                        }
                    });
                }else{
                    BasisTimesUtils.showDatePickerDialog(EditMsgActivity.this, "??????????????????", 1998, 1, 1, new BasisTimesUtils.OnDatePickerListener() {
                        @Override
                        public void onConfirm(int year, int month, int dayOfMonth) {
                            if(month<10 && dayOfMonth<10){
                                time = year + "-0" + month + "-0" + dayOfMonth;
                            }else if (month<10){
                                time = year + "-0" + month + "-" + dayOfMonth;
                            }else if(dayOfMonth<10){
                                time = year + "-" + month + "-0" + dayOfMonth;
                            }else{
                                time = year + "-" + month + "-" + dayOfMonth;
                            }

                            // TODO: 2021/1/7 0007 ??????????????????????????????????????????
                            NetUtils.getInstance()
                                    .getApis()
                                    .doComPleteAge(userid,time)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Observer<ComPleteMsgBean>() {
                                        @Override
                                        public void onSubscribe(Disposable d) {

                                        }

                                        @Override
                                        public void onNext(ComPleteMsgBean comPleteMsgBean) {
                                            if (comPleteMsgBean.getMsg().equals("?????????????????????")){
                                                tvAge.setText(time);
                                                SPUtil.getInstance().saveData(EditMsgActivity.this,SPUtil.FILE_NAME,SPUtil.BIRTHDAY,time);
                                                

                                            }
                                        }

                                        @Override
                                        public void onError(Throwable e) {

                                        }

                                        @Override
                                        public void onComplete() {

                                        }
                                    });

                        }

                        @Override
                        public void onCancel() {

                        }
                    });
                }
                break;
            //????????????
            case R.id.rl_qianming:
                //?????????????????????????????????
                showChangeQianming();
                break;
            //????????????
            case R.id.rl_myrole:
                //???????????????????????????
                showChangeRole();
                break;
            //??????????????????
                //?????????????????????????????????
            case R.id.rl_ganqingstate:
                showChangeSingle();
                break;
        }
    }


    private File creatfiles(){
        String dir = "/sdcard/download/pictures"; // download???pictures??????????????????????????????????????????????????????
        File file = new File(dir);
        if (!file.exists())
            file.mkdirs(); // ?????????????????????????????????????????????????????????????????????mkdirs
        return file;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                //????????????????????????????????????ArrayList??????????????????????????????
                List<String> paths = PictureSelector.obtainPathResult(data);
                path = paths.get(0);
                Glide.with(EditMsgActivity.this).load(path).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(ivHeadimg);


                //??????????????????
                // ????????????????????? ?????? uploadManager???????????????????????????????????? uploadManager ??????
                uploadManager = new UploadManager();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                // ????????????
                String s = MD5Utils.string2Md5_16(path);


                File file = new File(path);
                if(!file.exists())
                {
                    Toast.makeText(this, "???????????????", Toast.LENGTH_SHORT).show();
                }

                String key = s + sdf.format(new Date()) + ".jpg";
                uploadManager.put(file, key, token,
                        new UpCompletionHandler() {
                            @Override
                            public void complete(String key, ResponseInfo info, JSONObject res) {
                                //res ?????? hash???key ??????????????????????????????????????????????????????
                                if (info.isOK()) {
                                    // ????????????????????????
                                    try {
                                        String upimg = res.getString("key");
                                        //??????????????????????????????????????????list?????????
                                        url = serverPath + upimg;

                                        //??????????????????????????????
                                        File file = new File(path);
                                        Log.d("xxx",file.getAbsolutePath());

                                        JMessageClient.updateUserAvatar(file, new BasicCallback() {
                                            @Override
                                            public void gotResult(int i, String s) {
                                                if (i==0){
                                                    Log.d("xxx","????????????????????????????????????");
                                                    //???????????????????????????
                                                    NetUtils.getInstance().getApis()
                                                            .doComPleteHeadImg(userid,url)
                                                            .subscribeOn(Schedulers.io())
                                                            .observeOn(AndroidSchedulers.mainThread())
                                                            .subscribe(new Observer<ComPleteMsgBean>() {
                                                                @Override
                                                                public void onSubscribe(Disposable d) {

                                                                }

                                                                @Override
                                                                public void onNext(ComPleteMsgBean comPleteMsgBean) {
                                                                    if (comPleteMsgBean.getMsg().equals("?????????????????????")){
                                                                        dismiss();
                                                                        SPUtil.getInstance().saveData(EditMsgActivity.this,SPUtil.FILE_NAME,SPUtil.HEAD_IMG,url);
                                                                        Toast.makeText(EditMsgActivity.this, "??????????????????", Toast.LENGTH_SHORT).show();
                                                                        

                                                                    }

                                                                }
                                                                @Override
                                                                public void onError(Throwable e) {

                                                                }

                                                                @Override
                                                                public void onComplete() {

                                                                }
                                                            });
                                                }else {
                                                    Log.d("xxx",s);
                                                }
                                            }
                                        });

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    Log.i("xxx", "Upload Fail");
                                    Log.i("xxx", info.toString());
                                    //?????????????????????????????? info ?????????????????????????????????????????????????????????????????????
                                }
                                Log.i("xxx", url);
                            }
                        }, null);
            }
        }
    }
    /**
     * ??????EditText????????????
     *
     * @param editText
     */
    public static void setEditTextInhibitInputSpace(EditText editText) {
        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (source.equals(" ")) return "";
                else return null;
            }
        };
        editText.setFilters(new InputFilter[]{filter});
    }

    /**
     * ??????EditText??????????????????
     *
     * @param editText
     */
    public static void setEditTextInhibitInputSpeChat(EditText editText) {

        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                String speChat = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~???@#???%??????&*????????????+|{}????????????????????????????????????]";
                Pattern pattern = Pattern.compile(speChat);
                Matcher matcher = pattern.matcher(source.toString());
                if (matcher.find()) return "";
                else return null;
            }
        };
        editText.setFilters(new InputFilter[]{filter});
    }

    //????????????????????????
    public void showChangeQianming() {
        //??????popwiondow?????????
        mPopupWindow = new PopupWindow();
        mPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_qianming, null);

        TextView cancle = view.findViewById(R.id.tv_cancle);
        TextView confrim = view.findViewById(R.id.tv_confirm);
        TextView count = view.findViewById(R.id.tv_count);
        EditText text = view.findViewById(R.id.et_text);

        qm = SPUtil.getInstance().getData(EditMsgActivity.this, SPUtil.FILE_NAME, SPUtil.QIANMING);

        if (qm!=null){
            text.setText(qm);
        }

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        confrim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2020/11/26 0026  ???????????????????????????
                String str = text.getText().toString();

                NetUtils.getInstance().getApis()
                        .doComPleteQM(userid,str)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ComPleteMsgBean>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(ComPleteMsgBean comPleteMsgBean) {
                                if (comPleteMsgBean.getMsg().equals("?????????????????????")){
                                    dismiss();
                                    SPUtil.getInstance().saveData(EditMsgActivity.this,SPUtil.FILE_NAME,SPUtil.QIANMING,str);
                                    tvQianming.setText(str);
                                    Toast.makeText(EditMsgActivity.this, "??????????????????", Toast.LENGTH_SHORT).show();
                                    

                                }
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        });

                tvQianming.setText(str);
                //???????????????????????????
                dismiss();
            }
        });
        //??????EditText????????????????????????????????????
        text.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        //????????????????????????EditText????????????
        text.setGravity(Gravity.TOP);
        text.setSingleLine(false);
        //?????????????????????
        text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //???????????????????????????????????? ??????????????????????????????
                int length = text.getText().length();

                count.setText(30 - length + "");

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

    //???????????????
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
     * ??????PopupWindow
     */
    private void show(View v) {
        if (mPopupWindow != null && !mPopupWindow.isShowing()) {
            mPopupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
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

    // ???????????????????????????
    public void showChangeName() {
        //??????popwiondow?????????
        mPopupWindow1 = new PopupWindow();
        mPopupWindow1.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow1.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_changename, null);
        EditText et_name = view.findViewById(R.id.et_name);
        TextView tv_rem = view.findViewById(R.id.tv_Rem);
        TextView tv_jiance = view.findViewById(R.id.tv_jiance);
        TextView tvcancle = view.findViewById(R.id.tv_cancle);
        TextView tv_confirm = view.findViewById(R.id.tv_confirm);

        tv_confirm.setEnabled(false);


        //???????????? ??????????????????
        et_name.setText(tvName.getText().toString());
        //?????????????????????????????????
        setEditTextInhibitInputSpace(et_name);
        setEditTextInhibitInputSpeChat(et_name);

        //??????????????? ?????????????????????????????????
        int length = et_name.getText().length();
        tv_rem.setText(length + "/10");
        //??????????????????
        et_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s)){
                    tv_jiance.setBackground(EditMsgActivity.this.getResources().getDrawable(R.drawable.name_jiance_bg));
                    tv_jiance.setEnabled(true);
                }else{
                    tv_jiance.setBackground(EditMsgActivity.this.getResources().getDrawable(R.color.color_999999));
                    tv_jiance.setEnabled(false);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
                //????????????????????? ?????????????????????????????????
                int length = et_name.getText().length();
                tv_rem.setText(length + "/10");

                if (length>0){
                    tv_jiance.setBackground(EditMsgActivity.this.getResources().getDrawable(R.drawable.name_jiance_bg));
                    tv_jiance.setEnabled(true);
                }else{
                    tv_jiance.setBackground(EditMsgActivity.this.getResources().getDrawable(R.color.color_999999));
                    tv_jiance.setEnabled(false);
                }
            }
        });
        tv_jiance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = et_name.getText().toString();
                if (!TextUtils.isEmpty(s)) {
                    // TODO: 2021/1/6 0006 ????????????????????????
                    NetUtils.getInstance().getApis()
                            .doCheckName(s)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<CheckNickNameBean>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(CheckNickNameBean checkNickNameBean) {
                                    if (checkNickNameBean.getMsg().equals("?????????????????????")){
                                        Toast.makeText(EditMsgActivity.this, "??????????????? ??????????????????", Toast.LENGTH_SHORT).show();
                                        tv_confirm.setEnabled(false);
                                    }else if (checkNickNameBean.getMsg().equals("???????????????")){
                                        Toast.makeText(EditMsgActivity.this, "???????????????", Toast.LENGTH_SHORT).show();
                                        tv_confirm.setEnabled(true);
                                    }
                                }

                                @Override
                                public void onError(Throwable e) {

                                }

                                @Override
                                public void onComplete() {

                                }
                            });
                } else {
                    Toast.makeText(EditMsgActivity.this, "??????????????????", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //??????
        tvcancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss1();
            }
        });


        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //?????????????????????  ???????????????????????????????????????  ???????????????????????????
                String name = et_name.getText().toString();
                if (!TextUtils.isEmpty(name)) {

                    //??????????????????????????????????????????
                    userInfo.setNickname(name);
                    JMessageClient.updateMyInfo(UserInfo.Field.nickname,userInfo,new BasicCallback() {
                        @Override
                        public void gotResult(int i, String s) {
                            if (i==0){
                                Log.d("xxx","????????????????????????");
                                //???????????????????????? ??????????????????
                                NetUtils.getInstance().getApis()
                                        .doComPleteName(userid,name)
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new Observer<ComPleteMsgBean>() {
                                            @Override
                                            public void onSubscribe(Disposable d) {

                                            }

                                            @Override
                                            public void onNext(ComPleteMsgBean comPleteMsgBean) {
                                                if (comPleteMsgBean.getMsg().equals("?????????????????????")){
                                                    //??????sp
                                                    tvName.setText(name+"");
                                                    dismiss1();
                                                    SPUtil.getInstance().saveData(EditMsgActivity.this,SPUtil.FILE_NAME,SPUtil.USER_NAME,name);
                                                    

                                                }
                                            }

                                            @Override
                                            public void onError(Throwable e) {

                                            }

                                            @Override
                                            public void onComplete() {

                                            }
                                        });
                            }else{
                                Log.d("xxx","????????????????????????"+s);
                            }
                        }
                    });
                } else {
                    Toast.makeText(EditMsgActivity.this, "??????????????????", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //popwindow????????????
        mPopupWindow1.setContentView(view);
        mPopupWindow1.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow1.setFocusable(true);
        mPopupWindow1.setOutsideTouchable(true);
        mPopupWindow1.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setWindowAlpa(false);
            }
        });
        show1(view);
    }

    /**
     * ??????PopupWindow
     */
    private void show1(View v) {
        if (mPopupWindow1 != null && !mPopupWindow1.isShowing()) {
            mPopupWindow1.showAtLocation(v, Gravity.CENTER, 0, 0);
        }
        setWindowAlpa(true);

    }

    /**
     * ??????PopupWindow
     */
    public void dismiss1() {
        if (mPopupWindow1 != null && mPopupWindow1.isShowing()) {
            mPopupWindow1.dismiss();
        }
    }
    // ???????????????????????????
    public void showChangeRole() {
        //??????popwiondow?????????
        mPopupWindow2 = new PopupWindow();
        mPopupWindow2.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow2.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_changerole, null);



        TextView tv_confirm = view.findViewById(R.id.tv_confirm);
        TextView tv_cancle = view.findViewById(R.id.tv_cancle);
        RadioButton rb1 = view.findViewById(R.id.rb1);
        RadioButton rb2 = view.findViewById(R.id.rb2);
        RadioButton rb3 = view.findViewById(R.id.rb3);
        RadioButton rb4 = view.findViewById(R.id.rb4);
        RadioButton rb5 = view.findViewById(R.id.rb5);


        Userrole = SPUtil.getInstance().getData(EditMsgActivity.this, SPUtil.FILE_NAME, SPUtil.ROLE);

        if (Userrole!=null){
            if (Userrole.equals("T")){
                rb1.setChecked(true);
            }else if(Userrole.equals("P")){
                rb2.setChecked(true);
            }else if(Userrole.equals("H")){
                rb3.setChecked(true);
            }else if(Userrole.equals("BI")){
                rb4.setChecked(true);
            }else{
                rb5.setChecked(true);
            }
        }else{
            rb1.setChecked(true);
        }

        rb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rb1.setChecked(true);

                rb2.setChecked(false);
                rb3.setChecked(false);
                rb4.setChecked(false);
                rb5.setChecked(false);
            }
        });
        rb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rb2.setChecked(true);

                rb1.setChecked(false);
                rb3.setChecked(false);
                rb4.setChecked(false);
                rb5.setChecked(false);
            }
        });
        rb3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rb3.setChecked(true);

                rb1.setChecked(false);
                rb2.setChecked(false);
                rb4.setChecked(false);
                rb5.setChecked(false);
            }
        });
        rb4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rb4.setChecked(true);

                rb1.setChecked(false);
                rb2.setChecked(false);
                rb3.setChecked(false);
                rb5.setChecked(false);
            }
        });
        rb5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rb5.setChecked(true);

                rb1.setChecked(false);
                rb2.setChecked(false);
                rb3.setChecked(false);
                rb4.setChecked(false);
            }
        });

        tv_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss2();
            }
        });

        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rb1.isChecked()){
                    role = "T";
                }
                if (rb2.isChecked()){
                    role = "P";
                }
                if (rb3.isChecked()){
                    role = "H";
                }
                if (rb4.isChecked()){
                    role = "BI";
                }
                if (rb5.isChecked()){
                    role = "??????";
                }
                Toast.makeText(EditMsgActivity.this, "?????????????????????"+role, Toast.LENGTH_SHORT).show();

                NetUtils.getInstance()
                        .getApis()
                        .doComPleteUserRole(userid,role)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ComPleteMsgBean>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(ComPleteMsgBean comPleteMsgBean) {
                                if (comPleteMsgBean.getMsg().equals("?????????????????????")){
                                    //??????sp
                                    tvMyrole.setText(role+"");
                                    dismiss2();
                                    
                                    SPUtil.getInstance().saveData(EditMsgActivity.this,SPUtil.FILE_NAME,SPUtil.ROLE,role);
                                }
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        });
            }
        });
        //popwindow????????????
        mPopupWindow2.setContentView(view);
        mPopupWindow2.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow2.setFocusable(true);
        mPopupWindow2.setOutsideTouchable(true);
        mPopupWindow2.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setWindowAlpa(false);
            }
        });
        show2(view);
    }

    /**
     * ??????PopupWindow
     */
    private void show2(View v) {
        if (mPopupWindow2 != null && !mPopupWindow2.isShowing()) {
            mPopupWindow2.showAtLocation(v, Gravity.CENTER, 0, 0);
        }
        setWindowAlpa(true);

    }

    /**
     * ??????PopupWindow
     */
    public void dismiss2() {
        if (mPopupWindow2 != null && mPopupWindow2.isShowing()) {
            mPopupWindow2.dismiss();
        }
    }

    // ???????????????????????????
    public void showChangeSingle() {
        //??????popwiondow?????????
        mPopupWindow3 = new PopupWindow();
        mPopupWindow3.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow3.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_changeissingle, null);


        TextView tv_confirm = view.findViewById(R.id.tv_confirm);
        TextView tv_cancle = view.findViewById(R.id.tv_cancle);

        RelativeLayout rl1 = view.findViewById(R.id.rl_danshen);
        RelativeLayout rl2 = view.findViewById(R.id.rl_lianai);
        RelativeLayout rl3 = view.findViewById(R.id.rl_baomi);

        RadioButton rb1 = view.findViewById(R.id.check_danshen);
        RadioButton rb2 = view.findViewById(R.id.check_lianai);
        RadioButton rb3 = view.findViewById(R.id.check_baomi);

        rb1.setClickable(false);
        rb2.setClickable(false);
        rb3.setClickable(false);

        issingle = SPUtil.getInstance().getData(EditMsgActivity.this, SPUtil.FILE_NAME, SPUtil.ISSINGLE);

        if (issingle!=null){
            if (issingle.equals("1")){
                rb1.setChecked(true);
            }else if (issingle.equals("2")){
                rb2.setChecked(true);
            }else {
                rb3.setChecked(true);
            }
        }else{
            rb1.setChecked(true);
        }

        rl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rb1.setChecked(true);
                rb2.setChecked(false);
                rb3.setChecked(false);
            }
        });
        rl2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rb2.setChecked(true);
                rb1.setChecked(false);
                rb3.setChecked(false);
            }
        });
        rl3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rb3.setChecked(true);
                rb1.setChecked(false);
                rb2.setChecked(false);
            }
        });

        tv_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss3();
            }
        });
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (rb1.isChecked()){
                    single = "??????";
                    a= 1;
                }
                if (rb2.isChecked()){
                    single = "?????????";
                    a=  2;
                }
                if (rb3.isChecked()){
                    single = "??????";
                    a = 3;
                }
                NetUtils.getInstance()
                        .getApis()
                        .doComPleteIsSingle(userid,a)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ComPleteMsgBean>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(ComPleteMsgBean comPleteMsgBean) {
                                if (comPleteMsgBean.getMsg().equals("?????????????????????")){
                                    //??????sp
                                    tvMystate.setText(single+"");
                                    dismiss3();
                                    SPUtil.getInstance().saveData(EditMsgActivity.this,SPUtil.FILE_NAME,SPUtil.ISSINGLE, String.valueOf(a));
                                }
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        });
            }
        });
        //popwindow????????????
        mPopupWindow3.setContentView(view);
        mPopupWindow3.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow3.setFocusable(true);
        mPopupWindow3.setOutsideTouchable(true);
        mPopupWindow3.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setWindowAlpa(false);
            }
        });
        show3(view);
    }

    /**
     * ??????PopupWindow
     */
    private void show3(View v) {
        if (mPopupWindow3 != null && !mPopupWindow3.isShowing()) {
            mPopupWindow3.showAtLocation(v, Gravity.CENTER, 0, 0);
        }
        setWindowAlpa(true);

    }

    /**
     * ??????PopupWindow
     */
    public void dismiss3() {
        if (mPopupWindow3 != null && mPopupWindow3.isShowing()) {
            mPopupWindow3.dismiss();
        }
    }

}