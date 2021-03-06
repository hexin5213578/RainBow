package com.YiDian.RainBow.main.fragment.find.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.custom.customDialog.CustomDialogCancleFollow;
import com.YiDian.RainBow.main.fragment.find.bean.UserMySeeBean;
import com.YiDian.RainBow.main.fragment.home.bean.FollowBean;
import com.YiDian.RainBow.topic.SaveIntentMsgBean;
import com.YiDian.RainBow.user.PersonHomeActivity;
import com.YiDian.RainBow.utils.NetUtils;
import com.YiDian.RainBow.utils.StringUtil;
import com.YiDian.RainBow.utils.TimeUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LikeMineAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context context;
    private final List<UserMySeeBean.ObjectBean> list;
    private UserMySeeBean.ObjectBean bean;

    public LikeMineAdapter(Context context, List<UserMySeeBean.ObjectBean> list) {

        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_iseen_person, null);
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
        String explains = bean.getExplains();
        if (explains!=null){
            ((ViewHolder)holder).tvAutograph.setText(explains);
        }else{
            ((ViewHolder)holder).tvAutograph.setText("????????????????????????");
        }
        //????????????
        String createTime = bean.getCreateTime();
        long l = TimeUtil.TimeToLong(createTime);
        String newChatTime = StringUtil.getNewChatTime(l);

        //????????????
        ((ViewHolder)holder).tvTime.setText(newChatTime);

        //????????????
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

        //????????????????????????
        ((ViewHolder)holder).ivHeadimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bean = list.get(position);

                Intent intent = new Intent(context, PersonHomeActivity.class);
                SaveIntentMsgBean saveIntentMsgBean = new SaveIntentMsgBean();
                saveIntentMsgBean.setId(bean.getUserId());
                //2??????????????????  1????????????id
                saveIntentMsgBean.setFlag(1);
                intent.putExtra("msg",saveIntentMsgBean);
                context.startActivity(intent);
            }
        });


        //??????????????????
        if (bean.getIsFans()==0){
            //??????????????????
            ((ViewHolder)holder).btGuanzhu.setText("??????");
            //????????? ????????????
        }else if(bean.getIsFans()==1){
            //????????? ????????????
            ((ViewHolder)holder).btGuanzhu.setText("?????????");
        }
        ((ViewHolder)holder).btGuanzhu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bean = list.get(position);

                if (bean.getIsFans()==0){
                    //????????? ????????????
                    ((ViewHolder)holder).btGuanzhu.setEnabled(false);
                    NetUtils.getInstance().getApis()
                            .doFollow(bean.getBuserId(),bean.getUserId())
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
                                    bean.setIsFans(1);

                                    EventBus.getDefault().post("????????????????????????");
                                }

                                @Override
                                public void onError(Throwable e) {

                                }

                                @Override
                                public void onComplete() {

                                }
                            });
                }else if(bean.getIsFans()==1){
                    //????????? ????????????
                    ((ViewHolder)holder).btGuanzhu.setEnabled(false);

                    CustomDialogCancleFollow.Builder builder = new CustomDialogCancleFollow.Builder(context);
                    builder.setPositiveButton("??????", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //????????? ????????????
                            ((ViewHolder)holder).btGuanzhu.setEnabled(false);
                            dialog.dismiss();

                            NetUtils.getInstance().getApis()
                                    .doCancleFollow(bean.getUserId(), bean.getBuserId())
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
                                            bean.setIsFans(0);

                                            EventBus.getDefault().post("????????????????????????");
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
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_autograph)
        TextView tvAutograph;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.bt_guanzhu)
        Button btGuanzhu;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
