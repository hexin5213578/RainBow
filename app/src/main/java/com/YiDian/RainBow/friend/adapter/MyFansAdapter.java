package com.YiDian.RainBow.friend.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.Common;
import com.YiDian.RainBow.custom.customDialog.CustomDialogCancleFollow;
import com.YiDian.RainBow.friend.bean.MyFansBean;
import com.YiDian.RainBow.main.fragment.find.adapter.MyLikeAdapter;
import com.YiDian.RainBow.main.fragment.home.bean.FollowBean;
import com.YiDian.RainBow.main.fragment.msg.activity.FriendImActivity;
import com.YiDian.RainBow.topic.SaveIntentMsgBean;
import com.YiDian.RainBow.user.PersonHomeActivity;
import com.YiDian.RainBow.utils.NetUtils;
import com.YiDian.RainBow.utils.Utils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jpush.im.android.api.model.Conversation;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MyFansAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    private final List<MyFansBean.ObjectBean.ListBean> list;
    private MyFansBean.ObjectBean.ListBean bean;

    public MyFansAdapter(Context context, List<MyFansBean.ObjectBean.ListBean> list) {

        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_fans, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        bean = list.get(position);
        //???????????????
        ((ViewHolder)holder).tvName.setText(bean.getNickName());
        //????????????
        Glide.with(context).load(bean.getHeadImg()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(((ViewHolder) holder).ivHeadimg);
        //????????????
        if (bean.getExplains()!=null){
            if (bean.getExplains().equals("")){
                ((ViewHolder)holder).tvAutograph.setText("????????????????????????");
            }else{
                ((ViewHolder)holder).tvAutograph.setText(bean.getExplains());
            }
        }else{
            ((ViewHolder)holder).tvAutograph.setText("????????????????????????");
        }
        //????????????????????????
        ((ViewHolder)holder).ivHeadimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bean = list.get(position);

                Intent intent = new Intent(context, PersonHomeActivity.class);
                SaveIntentMsgBean saveIntentMsgBean = new SaveIntentMsgBean();
                saveIntentMsgBean.setId(bean.getFansId());
                //2??????????????????  1????????????id
                saveIntentMsgBean.setFlag(1);
                intent.putExtra("msg",saveIntentMsgBean);
                context.startActivity(intent);
            }
        });
        String userRole = bean.getUserRole();
        if (userRole!=null){
            if(userRole.equals("??????")){
                ((ViewHolder)holder).tvXingbie.setVisibility(View.GONE);
            }else{
                ((ViewHolder)holder).tvXingbie.setVisibility(View.VISIBLE);
                ((ViewHolder)holder).tvXingbie.setText(userRole);
            }
        }else{
            ((ViewHolder)holder).tvXingbie.setVisibility(View.GONE);
        }
        //??????????????????
        if (bean.getIsAttention()==0){
            //??????????????????
            ((ViewHolder)holder).btGuanzhu.setText("??????");
            //????????? ????????????
        }else if(bean.getIsAttention()==1){
            //????????? ????????????
            ((ViewHolder)holder).btGuanzhu.setText("?????????");
        }
        ((ViewHolder)holder).btGuanzhu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bean = list.get(position);

                if (bean.getIsAttention()==0){
                    //????????? ????????????
                    ((ViewHolder)holder).btGuanzhu.setEnabled(false);
                    NetUtils.getInstance().getApis()
                            .doFollow(bean.getUserId(), bean.getFansId())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<FollowBean>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(FollowBean followBean) {
                                    ((ViewHolder)holder).btGuanzhu.setEnabled(true);
                                    ((ViewHolder)holder).btGuanzhu.setText("?????????");
                                    bean.setIsAttention(1);

                                    EventBus.getDefault().post("??????????????????");
                                }

                                @Override
                                public void onError(Throwable e) {

                                }

                                @Override
                                public void onComplete() {

                                }
                            });
                }else if(bean.getIsAttention()==1){
                    ((ViewHolder)holder).btGuanzhu.setEnabled(false);

                    CustomDialogCancleFollow.Builder builder = new CustomDialogCancleFollow.Builder(context);
                    builder.setPositiveButton("??????", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //????????? ????????????
                            ((ViewHolder)holder).btGuanzhu.setEnabled(false);
                            dialog.dismiss();

                            NetUtils.getInstance().getApis()
                                    .doCancleFollow(bean.getUserId(), bean.getFansId())
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Observer<FollowBean>() {
                                        @Override
                                        public void onSubscribe(Disposable d) {

                                        }

                                        @Override
                                        public void onNext(FollowBean followBean) {
                                            ((ViewHolder)holder).btGuanzhu.setEnabled(true);
                                            ((ViewHolder)holder).btGuanzhu.setText("??????");
                                            bean.setIsAttention(0);

                                            EventBus.getDefault().post("??????????????????");
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
                    builder.setNegativeButton("??????",
                            new android.content.DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    builder.create().show();
                }
            }
        });
        ((ViewHolder)holder).rlItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //???????????????????????????
                bean = list.get(position);
                if(bean.getIsAttention()==1){
                    // TODO: 2020/12/28 0028 ??????????????????
                    String fansId = String.valueOf(bean.getFansId());

                    Utils.createConversation(fansId);

                    EventBus.getDefault().post("???????????????");

                    //??????????????????id??????????????????
                    Intent intent = new Intent(context, FriendImActivity.class);
                    intent.putExtra("userid",fansId);
                    context.startActivity(intent);
                }else{
                    Toast.makeText(context, "????????????????????????????????????", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_headimg)
        ImageView ivHeadimg;
        @BindView(R.id.tv_xingbie)
        TextView tvXingbie;
        @BindView(R.id.rl_img)
        RelativeLayout rlImg;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_autograph)
        TextView tvAutograph;
        @BindView(R.id.bt_guanzhu)
        Button btGuanzhu;
        @BindView(R.id.rl_item)
        RelativeLayout rlItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
