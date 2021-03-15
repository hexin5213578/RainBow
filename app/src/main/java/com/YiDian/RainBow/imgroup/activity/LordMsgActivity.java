package com.YiDian.RainBow.imgroup.activity;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseAvtivity;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.base.Common;
import com.YiDian.RainBow.custom.customDialog.CustomDialogCleanNotice;
import com.YiDian.RainBow.custom.image.CustomRoundAngleImageView;
import com.YiDian.RainBow.custom.loading.CustomDialog;
import com.YiDian.RainBow.imgroup.adapter.GroupMemberAdapter;
import com.YiDian.RainBow.imgroup.bean.ChangeGroupHeadBean;
import com.YiDian.RainBow.imgroup.bean.GroupMemberBean;
import com.YiDian.RainBow.imgroup.bean.GroupMsgBean;
import com.YiDian.RainBow.imgroup.bean.SaveIdAndHeadImgBean;
import com.YiDian.RainBow.login.bean.ComPleteMsgBean;
import com.YiDian.RainBow.main.fragment.mine.activity.EditMsgActivity;
import com.YiDian.RainBow.main.fragment.mine.activity.GroupQrCodeActivity;
import com.YiDian.RainBow.setup.bean.CheckNickNameBean;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetGroupInfoCallback;
import cn.jpush.im.android.api.model.GroupInfo;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author hmy 群主信息页
 */
public class LordMsgActivity extends BaseAvtivity implements View.OnClickListener {
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.iv_img)
    CustomRoundAngleImageView ivImg;
    @BindView(R.id.tv_group_name)
    TextView tvGroupName;
    @BindView(R.id.tv_group_id)
    TextView tvGroupId;
    @BindView(R.id.tv_group_people_count)
    TextView tvGroupPeopleCount;
    @BindView(R.id.rl_top)
    RelativeLayout rlTop;
    @BindView(R.id.rc_member)
    RecyclerView rcMember;
    @BindView(R.id.rl_seemore)
    LinearLayout rlSeemore;
    @BindView(R.id.rl_change_name)
    RelativeLayout rlChangeName;
    @BindView(R.id.rl_change_gonggao)
    RelativeLayout rlChangeGonggao;
    @BindView(R.id.rl_change_jianjie)
    RelativeLayout rlChangeJianjie;
    @BindView(R.id.rl_qunzu_code)
    RelativeLayout rlQunzuCode;
    @BindView(R.id.rl_qunzu_manager)
    RelativeLayout rlQunzuManager;
    @BindView(R.id.rl_clean)
    RelativeLayout rlClean;
    @BindView(R.id.bt_disband)
    Button btDisband;
    @BindView(R.id.iv_bg)
    ImageView ivBg;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_jianjie)
    TextView tvJianjie;
    @BindView(R.id.tv_gonggao)
    TextView tvGonggao;
    private Intent intent;
    private int groupid;
    private String groupImg;
    private int jgGroupId;
    private int userId;
    private CustomDialogCleanNotice.Builder builder;
    private String groupName;
    private SaveIdAndHeadImgBean saveIdAndHeadImgBean;
    private UploadManager uploadManager;
    private String path;
    private String url;
    private static final String serverPath = "http://img.rianbow.cn/";
    private String token;
    private PopupWindow mPopupWindow1;
    private CustomDialog customDialog;
    private PopupWindow mPopupWindow;

    @Override
    protected int getResId() {
        return R.layout.activity_lordmsg;
    }

    @Override
    protected void getData() {
        //设置背景透明
        StatusBarUtil.setTransparentForWindow(this);
        //设置状态栏字体黑色
        StatusBarUtil.setLightMode(this);
        customDialog = new CustomDialog(LordMsgActivity.this, "正在修改昵称...");
        //获取当前登陆用户id
        userId = Integer.parseInt(Common.getUserId());

        //获取七牛云token
        token = Common.getToken();

        Intent intent = getIntent();
        groupid = intent.getIntExtra("groupid", 0);
        Log.d("xxx", "群ID为" + groupid);

        llBack.setOnClickListener(this);
        rlTop.setOnClickListener(this);
        rlSeemore.setOnClickListener(this);
        rlChangeName.setOnClickListener(this);
        rlChangeGonggao.setOnClickListener(this);
        rlChangeJianjie.setOnClickListener(this);
        rlQunzuCode.setOnClickListener(this);
        rlQunzuManager.setOnClickListener(this);
        rlClean.setOnClickListener(this);
        btDisband.setOnClickListener(this);


        //获取群信息
        getGroupMsg(groupid, userId);
        //获取群成员列表
        getGroupMember(groupid,1,25);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //获取群信息
        getGroupMsg(groupid, userId);
        //获取群成员列表
        getGroupMember(groupid,1,25);
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }
    //获取群成员
    public void getGroupMember(int groupid,int page,int size){
        NetUtils.getInstance().getApis()
                .doGetGroupMember(groupid,page,size)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GroupMemberBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull GroupMemberBean groupMemberBean) {
                        if (groupMemberBean.getType().equals("OK")){
                            List<GroupMemberBean.ObjectBean.ListBean> list = groupMemberBean.getObject().getList();
                            if (list!=null && list.size()>0){

                                GridLayoutManager gridLayoutManager = new GridLayoutManager(LordMsgActivity.this, 7);
                                rcMember.setLayoutManager(gridLayoutManager);

                                GroupMemberAdapter groupMemberAdapter = new GroupMemberAdapter(LordMsgActivity.this);
                                //设置数据源
                                groupMemberAdapter.setData(list);

                                rcMember.setAdapter(groupMemberAdapter);
                                //创建适配器
                            }
                            if (list.size()>21){
                                rlSeemore.setVisibility(View.VISIBLE);
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    //获取群信息
    public void getGroupMsg(int groupid, int userid) {
        NetUtils.getInstance().getApis()
                .doGetGroupMsg(groupid, userid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GroupMsgBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull GroupMsgBean groupMsgBean) {
                        if (groupMsgBean.getType().equals("OK")) {
                            GroupMsgBean.ObjectBean object = groupMsgBean.getObject();

                            groupImg = object.getGroupImg();
                            jgGroupId = object.getJgGroupId();
                            groupName = object.getGroupName();
                            //设置头像
                            Glide.with(LordMsgActivity.this).load(groupImg).into(ivImg);
                            //设置背景
                            Glide.with(LordMsgActivity.this).load(object.getBaseMap()).into(ivBg);
                            //群ID
                            tvGroupId.setText("ID:"+object.getId() + "");
                            //群人数
                            tvGroupPeopleCount.setText(object.getUserNum() + "");
                            //设置群名
                            tvGroupName.setText(object.getGroupName() + "");
                            tvName.setText(object.getGroupName() + "");
                            //设置公告
                            tvGonggao.setText(object.getGroupNotice()+"");
                            //设置简介
                            tvJianjie.setText(object.getGroupInfo()+"");


                            //设置极光简介
                            JMessageClient.getGroupInfo(jgGroupId, new GetGroupInfoCallback() {
                                @Override
                                public void gotResult(int i, String s, GroupInfo groupInfo) {
                                    if (i==0){
                                        groupInfo.updateDescription(object.getGroupInfo(), new BasicCallback() {
                                            @Override
                                            public void gotResult(int i, String s) {
                                                if (i==0){
                                                    Log.d("xxx","极光描述修改成功");
                                                }
                                            }
                                        });
                                    }
                                }
                            });
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //更换群组背景图
            case R.id.rl_top:
                if(Build.VERSION.SDK_INT==30){
                    Toast.makeText(this, "Android11暂不支持更换头像", Toast.LENGTH_SHORT).show();
                }else{
                    PictureSelector
                            .with(this)
                            .selectSpec()
                            .setOpenCamera()
                            //开启拍照功能一定得设置该属性，为了兼容Android7.0相机拍照问题
                            //在manifest文件中也需要注册该provider
                            .setAuthority("com.YiDian.RainBow.utils.MyFileProvider")
                            .startForResult(100);
                }
                break;
            //退出当前页面
            case R.id.ll_back:
                finish();
                break;
            //查看更多群成员
            case R.id.rl_seemore:
                intent = new Intent(LordMsgActivity.this,GroupMemberDetailsActivity.class);
                intent.putExtra("groupid",groupid);
                startActivity(intent);
                break;
            //更改群名
            case R.id.rl_change_name:
                //调用修改昵称的弹出框
                showChangeName();
                break;
            //更改群公告
            case R.id.rl_change_gonggao:

                break;
            //更改群简介
            case R.id.rl_change_jianjie:
                showChangeQianming();
                break;
            //查看群二维码
            case R.id.rl_qunzu_code:
                Intent intent = new Intent(LordMsgActivity.this, GroupQrCodeActivity.class);
                saveIdAndHeadImgBean = new SaveIdAndHeadImgBean();
                saveIdAndHeadImgBean.setId(groupid);
                saveIdAndHeadImgBean.setName(groupName);
                saveIdAndHeadImgBean.setHeadimg(groupImg);
                intent.putExtra("msg",saveIdAndHeadImgBean);
                startActivity(intent);
                break;
            //管理群聊
            case R.id.rl_qunzu_manager:
                this.intent = new Intent(LordMsgActivity.this,GroupManagerActivity.class);
                saveIdAndHeadImgBean = new SaveIdAndHeadImgBean();
                saveIdAndHeadImgBean.setId(groupid);
                saveIdAndHeadImgBean.setHeadimg(groupImg);
                saveIdAndHeadImgBean.setJgId(jgGroupId);
                this.intent.putExtra("msg", saveIdAndHeadImgBean);
                startActivity(this.intent);
                break;
            //清空聊天记录
            case R.id.rl_clean:
                builder = new CustomDialogCleanNotice.Builder(LordMsgActivity.this);
                builder.setMessage("确定要清空聊天记录嘛?").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        boolean b = JMessageClient.deleteGroupConversation(groupid);
                        if (b){
                            Toast.makeText(LordMsgActivity.this, "聊天记录删除成功", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("取消",
                        new android.content.DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builder.create().show();

                break;
            //解散群聊
            case R.id.bt_disband:

                builder = new CustomDialogCleanNotice.Builder(LordMsgActivity.this);
                builder.setMessage("确定要解散该群组嘛?").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //发起极光解散群组
                        JMessageClient.adminDissolveGroup(jgGroupId, new BasicCallback() {
                            @Override
                            public void gotResult(int i, String s) {
                                Log.d("xxx","错误码为"+i+",原因为"+s);
                                if (i==0){
                                    //极光解散成功 在解散本地服务器上的群
                                    NetUtils.getInstance().getApis()
                                            .doDeleteGroup(groupid)
                                            .subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(new Observer<ChangeGroupHeadBean>() {
                                                @Override
                                                public void onSubscribe(@NonNull Disposable d) {

                                                }

                                                @Override
                                                public void onNext(@NonNull ChangeGroupHeadBean changeGroupHeadBean) {
                                                    if (changeGroupHeadBean.getMsg().equals("删除成功")){
                                                        dialog.dismiss();
                                                        finish();
                                                    }
                                                }

                                                @Override
                                                public void onError(@NonNull Throwable e) {

                                                }

                                                @Override
                                                public void onComplete() {

                                                }
                                            });
                                }
                            }
                        });
                    }
                });
                builder.setNegativeButton("取消",
                        new android.content.DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builder.create().show();
                break;
            default:
                break;
        }
    }


    /**
     * 修改群简介
     */
    public void showChangeQianming() {
        //创建popwiondow弹出框
        mPopupWindow = new PopupWindow();
        mPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_jianjie, null);

        TextView cancle = view.findViewById(R.id.tv_cancle);
        TextView confrim = view.findViewById(R.id.tv_confirm);
        TextView count = view.findViewById(R.id.tv_count);
        EditText text = view.findViewById(R.id.et_text);

        if (tvJianjie.getText().toString().contains("还没有简介")){
            text.setText("");
        }else{
            text.setText(tvJianjie.getText().toString()+"");
        }
        //设置EditText的显示方式为多行文本输入
        text.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        //文本显示的位置在EditText的最上方
        text.setGravity(Gravity.TOP);
        text.setSingleLine(false);
        //输入框文本监听
        text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //输入完后获取当前文本长度 给右下角文本长度赋值
                int length = text.getText().length();

                count.setText(30 - length + "");
            }
        });

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        confrim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = text.getText().toString();
                if (content.length()==0){
                    Toast.makeText(LordMsgActivity.this, "请先输入内容", Toast.LENGTH_SHORT).show();
                }else{
                    NetUtils.getInstance().getApis()
                            .doChangeGroupJJ(groupid,content)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<ChangeGroupHeadBean>() {
                                @Override
                                public void onSubscribe(@NonNull Disposable d) {

                                }

                                @Override
                                public void onNext(@NonNull ChangeGroupHeadBean changeGroupHeadBean) {
                                    if (changeGroupHeadBean.getMsg().equals("修改成功")){
                                        tvJianjie.setText(content);
                                        //调用极光修改简介
                                        //设置极光简介
                                        JMessageClient.getGroupInfo(jgGroupId, new GetGroupInfoCallback() {
                                            @Override
                                            public void gotResult(int i, String s, GroupInfo groupInfo) {
                                                if (i==0){
                                                    groupInfo.updateDescription(content, new BasicCallback() {
                                                        @Override
                                                        public void gotResult(int i, String s) {
                                                            if (i==0){
                                                                Log.d("xxx","极光描述修改成功");
                                                                dismiss();
                                                            }
                                                        }
                                                    });
                                                }
                                            }
                                        });
                                    }
                                }

                                @Override
                                public void onError(@NonNull Throwable e) {

                                }

                                @Override
                                public void onComplete() {

                                }
                            });
                }
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
    /**
     * 显示PopupWindow
     */
    private void show(View v) {
        if (mPopupWindow != null && !mPopupWindow.isShowing()) {
            mPopupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
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

    /**
     * 修改群昵称
     */
    public void showChangeName() {
        //创建popwiondow弹出框
        mPopupWindow1 = new PopupWindow();
        mPopupWindow1.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow1.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_changegroupname, null);
        EditText et_name = view.findViewById(R.id.et_name);
        TextView tv_rem = view.findViewById(R.id.tv_Rem);
        TextView tvcancle = view.findViewById(R.id.tv_cancle);
        TextView tv_confirm = view.findViewById(R.id.tv_confirm);


        //将用户名 回显到输入框
        et_name.setText(tvName.getText().toString());
        //禁止输入特殊字符及空格
        setEditTextInhibitInputSpace(et_name);
        setEditTextInhibitInputSpeChat(et_name);

        //输入框监听器
        et_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                //文本输入结束后 获取长度赋值给长度计算
                int length = et_name.getText().length();
                tv_rem.setText(length + "/10");
            }
        });
        tvcancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss1();
            }
        });
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = et_name.getText().toString();
                customDialog.show();
                NetUtils.getInstance().getApis()
                        .doChangeGroupName(groupid,name)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ChangeGroupHeadBean>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {

                            }

                            @Override
                            public void onNext(@NonNull ChangeGroupHeadBean changeGroupHeadBean) {
                                if (changeGroupHeadBean.getMsg().equals("修改成功")){
                                    tvName.setText(name);
                                    //同步修改极光群昵称
                                    JMessageClient.getGroupInfo(jgGroupId, new GetGroupInfoCallback() {
                                        @Override
                                        public void gotResult(int i, String s, GroupInfo groupInfo) {
                                            if (i==0){
                                                Log.d("xxx","获取群组信息成功");
                                                groupInfo.updateName(name, new BasicCallback() {
                                                    @Override
                                                    public void gotResult(int i, String s) {
                                                        if (i==0){
                                                            Log.d("xxx","群昵称更新成功");
                                                            customDialog.dismiss();
                                                            dismiss1();
                                                        }
                                                    }
                                                });
                                            }
                                        }
                                    });
                                }
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        });
            }
        });


        //popwindow设置属性
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
    private void show1(View v) {
        if (mPopupWindow1 != null && !mPopupWindow1.isShowing()) {
            mPopupWindow1.showAtLocation(v, Gravity.CENTER, 0, 0);
        }
        setWindowAlpa(true);

    }

    /**
     * 消失PopupWindow
     */
    public void dismiss1() {
        if (mPopupWindow1 != null && mPopupWindow1.isShowing()) {
            mPopupWindow1.dismiss();
        }
    }
    /**
     * 禁止EditText输入空格
     *
     * @param editText
     */
    public static void setEditTextInhibitInputSpace(EditText editText) {
        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (!source.equals(" ")) {
                    return null;
                } else {
                    return "";
                }
            }
        };
        editText.setFilters(new InputFilter[]{filter});
    }

    /**
     * 禁止EditText输入特殊字符
     *
     * @param editText
     */
    public static void setEditTextInhibitInputSpeChat(EditText editText) {

        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                String speChat = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
                Pattern pattern = Pattern.compile(speChat);
                Matcher matcher = pattern.matcher(source.toString());
                if (!matcher.find()) {
                    return null;
                } else {
                    return "";
                }
            }
        };
        editText.setFilters(new InputFilter[]{filter});
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                //图片单选和多选数据都是以ArrayList的字符串数组返回的。
                List<String> paths = PictureSelector.obtainPathResult(data);
                path = paths.get(0);
                //上传至七牛云
                // 图片上传到七牛 重用 uploadManager。一般地，只需要创建一个 uploadManager 对象
                uploadManager = new UploadManager();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                // 设置名字
                String s = MD5Utils.string2Md5_16(path);

                File file = new File(path);
                if(!file.exists())
                {
                    Toast.makeText(this, "文件不存在", Toast.LENGTH_SHORT).show();
                }

                String key = s + sdf.format(new Date()) + ".jpg";
                uploadManager.put(file, key, token,
                        new UpCompletionHandler() {
                            @Override
                            public void complete(String key, ResponseInfo info, JSONObject res) {
                                //res 包含 hash、key 等信息，具体字段取决于上传策略的设置
                                if (info.isOK()) {
                                    // 七牛返回的文件名
                                    try {
                                        String upimg = res.getString("key");
                                        //将七牛返回图片的文件名添加到list集合中
                                        url = serverPath + upimg;
                                        NetUtils.getInstance()
                                                .getApis().doChangeGroupBackImg(groupid,url)
                                                .subscribeOn(Schedulers.io())
                                                .observeOn(AndroidSchedulers.mainThread())
                                                .subscribe(new Observer<ChangeGroupHeadBean>() {
                                                    @Override
                                                    public void onSubscribe(@NonNull Disposable d) {

                                                    }

                                                    @Override
                                                    public void onNext(@NonNull ChangeGroupHeadBean changeGroupHeadBean) {
                                                        //更换成功后回显
                                                        Glide.with(LordMsgActivity.this).load(url).into(ivBg);
                                                    }

                                                    @Override
                                                    public void onError(@NonNull Throwable e) {

                                                    }

                                                    @Override
                                                    public void onComplete() {

                                                    }
                                                });
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    Log.i("xxx", "Upload Fail");
                                    Log.i("xxx", info.toString());
                                    //如果失败，这里可以把 info 信息上报自己的服务器，便于后面分析上传错误原因
                                }
                                Log.i("xxx", url);
                            }
                        }, null);
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
