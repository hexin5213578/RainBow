package com.YiDian.RainBow.main.fragment.find.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.Common;
import com.YiDian.RainBow.custom.customDialog.CustomDialogCancleFollow;
import com.YiDian.RainBow.main.fragment.find.bean.NearPersonBean;
import com.YiDian.RainBow.main.fragment.home.adapter.NewDynamicAdapter;
import com.YiDian.RainBow.main.fragment.home.bean.FollowBean;
import com.YiDian.RainBow.utils.NetUtils;
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

public class NearPersonAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    private final List<NearPersonBean.ObjectBean.ListBean> list;
    private NearPersonBean.ObjectBean.ListBean listBean;
    private int userid;

    public NearPersonAdapter(Context context, List<NearPersonBean.ObjectBean.ListBean> list) {

        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_near_person, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        listBean = list.get(position);
        Glide.with(context).load(listBean.getHeadImg()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(((ViewHolder) holder).ivHeadimg);

        userid = Integer.valueOf(Common.getUserId());
        //设置用户名
        ((ViewHolder)holder).tvName.setText(listBean.getNickName());
        //设置签名
        ((ViewHolder)holder).tvAutograph.setText(listBean.getExplains());
        //判断是否关注
        if (listBean.getIsFans()==0){
            //设置成未关注
            ((ViewHolder)holder).btGuanzhu.setText("关注");
            //未关注 发起关注
        }else if(listBean.getIsFans()==1){
            //已关注 取消关注
            ((ViewHolder)holder).btGuanzhu.setText("已关注");
        }
        ((ViewHolder)holder).btGuanzhu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listBean = list.get(position);

                if (listBean.getIsFans()==0){
                    //未关注 发起关注
                    ((ViewHolder)holder).btGuanzhu.setEnabled(false);
                    NetUtils.getInstance().getApis()
                            .doFollow(userid, listBean.getId())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<FollowBean>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(FollowBean followBean) {
                                    ((ViewHolder)holder).btGuanzhu.setEnabled(true);
                                    ((ViewHolder)holder).btGuanzhu.setText("已关注");
                                    listBean.setIsFans(1);

                                    EventBus.getDefault().post("匹配过的刷新界面");
                                }

                                @Override
                                public void onError(Throwable e) {

                                }

                                @Override
                                public void onComplete() {

                                }
                            });
                }else if(listBean.getIsFans()==1){
                    //已关注 取消关注
                    ((ViewHolder)holder).btGuanzhu.setEnabled(false);

                    CustomDialogCancleFollow.Builder builder = new CustomDialogCancleFollow.Builder(context);
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //已关注 取消关注
                            ((ViewHolder)holder).btGuanzhu.setEnabled(false);
                            dialog.dismiss();

                            NetUtils.getInstance().getApis()
                                    .doCancleFollow(userid, listBean.getId())
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Observer<FollowBean>() {
                                        @Override
                                        public void onSubscribe(Disposable d) {

                                        }

                                        @Override
                                        public void onNext(FollowBean followBean) {
                                            ((ViewHolder)holder).btGuanzhu.setEnabled(true);
                                            ((ViewHolder)holder).btGuanzhu.setText("关注");
                                            listBean.setIsFans(0);

                                            EventBus.getDefault().post("我喜欢的刷新界面");
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
                            new android.content.DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    builder.create().show();
                }
            }
        });
        //获取发布时位置距离当前的距离
        int distance = listBean.getDistance();
            if(distance<1000){
                ((ViewHolder) holder).tvDistance.setText(distance + "m");
            }else{
                ((ViewHolder) holder).tvDistance.setText(distance/1000 + "km");
            }

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
        @BindView(R.id.tv_distance)
        TextView tvDistance;
        @BindView(R.id.bt_guanzhu)
        Button btGuanzhu;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
