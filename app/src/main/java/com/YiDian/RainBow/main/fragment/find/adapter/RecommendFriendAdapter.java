package com.YiDian.RainBow.main.fragment.find.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.Common;
import com.YiDian.RainBow.friend.bean.RecommendUserBean;
import com.YiDian.RainBow.main.fragment.home.bean.FollowBean;
import com.YiDian.RainBow.topic.SaveIntentMsgBean;
import com.YiDian.RainBow.user.PersonHomeActivity;
import com.YiDian.RainBow.utils.NetUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RecommendFriendAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    private final List<RecommendUserBean.ObjectBean> list;
    private RecommendUserBean.ObjectBean bean;
    private int userid;


    public RecommendFriendAdapter(Context context, List<RecommendUserBean.ObjectBean> list) {

        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_recommendfriend, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        bean = list.get(position);

        userid = Integer.parseInt(Common.getUserId());
        switch (position%4){
            case 0:
                ((ViewHolder)holder).rlItem.setBackground(context.getDrawable(R.drawable.recommend1));
                break;
            case 1:
                ((ViewHolder)holder).rlItem.setBackground(context.getDrawable(R.drawable.recommend2));
                break;
            case 2:
                ((ViewHolder)holder).rlItem.setBackground(context.getDrawable(R.drawable.recommend3));
                break;
            case 3:
                ((ViewHolder)holder).rlItem.setBackground(context.getDrawable(R.drawable.recommend4));
                break;
            default:
                break;
        }
        Glide.with(context).load(bean.getHeadImg()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(((ViewHolder)holder).ivHeadimg);
        ((ViewHolder)holder).tvName.setText(bean.getNickName()+"");
        ((ViewHolder)holder).tvGuanzhu.setText("关注");

        ((ViewHolder)holder).rlItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bean = list.get(position);

                Intent intent = new Intent(context, PersonHomeActivity.class);
                SaveIntentMsgBean saveIntentMsgBean = new SaveIntentMsgBean();
                saveIntentMsgBean.setId(bean.getId());
                //2标记传入姓名  1标记传入id
                saveIntentMsgBean.setFlag(1);
                intent.putExtra("msg", saveIntentMsgBean);
                context.startActivity(intent);
            }
        });

        //关注点击
        ((ViewHolder)holder).tvGuanzhu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NetUtils.getInstance().getApis()
                        .doFollow(userid,list.get(position).getId())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<FollowBean>() {
                            @Override
                            public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {

                            }

                            @Override
                            public void onNext(@io.reactivex.annotations.NonNull FollowBean followBean) {
                                if (followBean.getMsg().equals("关注成功")){
                                    ((ViewHolder)holder).tvGuanzhu.setText("已关注");
                                    ((ViewHolder)holder).tvGuanzhu.setBackground(context.getResources().getDrawable(R.drawable.newdynamic_yiguanzhu));
                                    ((ViewHolder)holder).tvGuanzhu.setTextColor(context.getResources().getColor(R.color.color_999999));

                                    ((ViewHolder)holder).tvGuanzhu.setEnabled(false);
                                }
                            }

                            @Override
                            public void onError(@io.reactivex.annotations.NonNull Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        });
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
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_guanzhu)
        TextView tvGuanzhu;
        @BindView(R.id.rl_item)
        RelativeLayout rlItem;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
