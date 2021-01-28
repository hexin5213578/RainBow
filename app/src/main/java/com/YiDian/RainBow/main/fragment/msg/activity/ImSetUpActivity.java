package com.YiDian.RainBow.main.fragment.msg.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseAvtivity;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.base.Common;
import com.YiDian.RainBow.custom.customDialog.CustomDialogCleanNotice;
import com.YiDian.RainBow.main.fragment.home.bean.FollowBean;
import com.YiDian.RainBow.setup.bean.InsertRealBean;
import com.YiDian.RainBow.topic.SaveIntentMsgBean;
import com.YiDian.RainBow.user.PersonHomeActivity;
import com.YiDian.RainBow.utils.NetUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.leaf.library.StatusBarUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.api.BasicCallback;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ImSetUpActivity extends BaseAvtivity implements View.OnClickListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_back)
    LinearLayout ivBack;
    @BindView(R.id.iv_headimg)
    ImageView ivHeadimg;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.l2)
    RelativeLayout l2;
    @BindView(R.id.l3)
    RelativeLayout l3;
    @BindView(R.id.l4)
    RelativeLayout l4;
    @BindView(R.id.l5)
    RelativeLayout l5;
    @BindView(R.id.l1)
    RelativeLayout l1;
    private Conversation conversation;
    private String id;
    private CustomDialogCleanNotice.Builder builder;
    private int userid;

    @Override
    protected int getResId() {
        return R.layout.activity_im_setup;
    }

    @Override
    protected void getData() {
        StatusBarUtil.setGradientColor(ImSetUpActivity.this, toolbar);
        StatusBarUtil.setDarkMode(ImSetUpActivity.this);

        userid = Integer.valueOf(Common.getUserId());

        ivBack.setOnClickListener(this);
        l1.setOnClickListener(this);
        l2.setOnClickListener(this);
        l3.setOnClickListener(this);
        l4.setOnClickListener(this);
        l5.setOnClickListener(this);

        Intent intent = getIntent();
        String imgfile1 = intent.getStringExtra("imgfile");
        String name = intent.getStringExtra("name");
        id = intent.getStringExtra("userid");

        //加载圆角头像
        Glide.with(ImSetUpActivity.this).load(imgfile1).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(ivHeadimg);
        tvName.setText(name);

        conversation = JMessageClient.getSingleConversation(id);
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                EventBus.getDefault().post("收到了信息");
                finish();
                break;
            //跳转到用户主页
            case R.id.l1:
                //跳转到用户信息页
                Intent intent = new Intent(ImSetUpActivity.this, PersonHomeActivity.class);
                SaveIntentMsgBean saveIntentMsgBean = new SaveIntentMsgBean();
                saveIntentMsgBean.setId(Integer.parseInt(id));
                //2标记传入姓名  1标记传入id
                saveIntentMsgBean.setFlag(1);
                intent.putExtra("msg", saveIntentMsgBean);
                startActivity(intent);
                break;
            //取消关注
            case R.id.l2:
                builder = new CustomDialogCleanNotice.Builder(ImSetUpActivity.this);
                builder.setMessage("确定取消关注该用户?").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        NetUtils.getInstance().getApis()
                                .doCancleFollow(userid, Integer.parseInt(id))
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Observer<FollowBean>() {
                                    @Override
                                    public void onSubscribe(Disposable d) {

                                    }

                                    @Override
                                    public void onNext(FollowBean followBean) {
                                        dialog.dismiss();
                                        Toast.makeText(ImSetUpActivity.this, "" + followBean.getMsg(), Toast.LENGTH_SHORT).show();
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
                builder.setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builder.create().show();
                break;
            //加入黑名单
            case R.id.l3:
                builder = new CustomDialogCleanNotice.Builder(ImSetUpActivity.this);
                builder.setMessage("确定加入黑名单?").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        NetUtils.getInstance().getApis()
                                .doInsertBlackFriend(userid, Integer.parseInt(id))
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Observer<InsertRealBean>() {
                                    @Override
                                    public void onSubscribe(Disposable d) {

                                    }

                                    @Override
                                    public void onNext(InsertRealBean insertRealBean) {
                                        if (insertRealBean.getMsg().equals("增加成功")) {
                                            List<String> list = new ArrayList<>();

                                            list.add(id);

                                            JMessageClient.addUsersToBlacklist(list, new BasicCallback() {
                                                @Override
                                                public void gotResult(int i, String s) {
                                                    if (i == 0) {
                                                        Toast.makeText(ImSetUpActivity.this, "加入黑名单成功", Toast.LENGTH_SHORT).show();
                                                        dialog.dismiss();
                                                    } else {
                                                        dialog.dismiss();
                                                        Log.d("xxx", "错误码为" + i + "原因为" + s);
                                                    }
                                                }
                                            });
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
                builder.setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builder.create().show();
                break;
            //清空聊天记录
            case R.id.l4:
                builder = new CustomDialogCleanNotice.Builder(ImSetUpActivity.this);
                builder.setMessage("确定清空所有聊天记录?").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        conversation.deleteAllMessage();
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builder.create().show();
                break;
            //举报
            case R.id.l5:
                Intent intent2 = new Intent(ImSetUpActivity.this, ReportActivity.class);
                intent2.putExtra("id",id);
                startActivity(intent2);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
