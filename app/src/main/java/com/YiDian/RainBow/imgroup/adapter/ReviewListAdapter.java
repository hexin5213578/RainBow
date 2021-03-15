package com.YiDian.RainBow.imgroup.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.custom.image.CustomRoundAngleImageView;
import com.YiDian.RainBow.imgroup.bean.ShenHeListBean;
import com.YiDian.RainBow.topic.SaveIntentMsgBean;
import com.YiDian.RainBow.user.PersonHomeActivity;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jpush.im.android.api.callback.GetUserInfoListCallback;
import cn.jpush.im.android.api.event.GroupApprovalEvent;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;

public class ReviewListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    private final List<ShenHeListBean.ObjectBean.ListBean> list;
    private int jgid;
    private ShenHeListBean.ObjectBean.ListBean listBean;


    public ReviewListAdapter(Context context, List<ShenHeListBean.ObjectBean.ListBean> list, int jgid) {

        this.context = context;
        this.list = list;
        this.jgid = jgid;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_review1, null);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        listBean = list.get(position);

        Glide.with(context).load(listBean.getHeadImg()).into(((ViewHolder) holder).ivHeadimg);
        ((ViewHolder) holder).tvName.setText(listBean.getNickName() + "");

        String createTime = listBean.getCreateTime();
        String[] s = createTime.split(" ");
        ((ViewHolder) holder).tvTime.setText(s[0]+"申请加入");


        ((ViewHolder)holder).ivHeadimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listBean =list.get(position);

                Intent intent = new Intent(context, PersonHomeActivity.class);
                SaveIntentMsgBean saveIntentMsgBean = new SaveIntentMsgBean();
                saveIntentMsgBean.setId(listBean.getUserMsgId());
                //2标记传入姓名  1标记传入id
                saveIntentMsgBean.setFlag(1);
                intent.putExtra("msg", saveIntentMsgBean);
                context.startActivity(intent);
            }
        });

        if(listBean.getMsgType()==7){
            ((ViewHolder)holder).tvBt.setText("同意");
            ((ViewHolder)holder).tvBt.setBackground(context.getDrawable(R.drawable.tenradious_e6_bg));
            ((ViewHolder)holder).tvBt.setTextColor(context.getColor(R.color.color_999999));


            ((ViewHolder)holder).tvBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listBean =list.get(position);

                    //入群审批
                    GroupApprovalEvent groupApprovalEvent = new GroupApprovalEvent();

                    groupApprovalEvent.acceptGroupApproval(String.valueOf(listBean.getUserMsgId()), "", new BasicCallback() {
                        @Override
                        public void gotResult(int responseCode, String responseMessage) {
                            if (0 == responseCode) {
                                Toast.makeText(context, "添加成功", Toast.LENGTH_SHORT).show();
                            } else {
                                Log.i("xxx", "acceptApplyJoinGroup failed,"+ " code = " + responseCode + ";msg = " + responseMessage);
                                Toast.makeText(context, "添加失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }); //入群审批同意
                }
            });
            ((ViewHolder)holder).rlItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //跳转至同意拒绝

                }
            });
        }
        if (listBean.getMsgType()==8){
            ((ViewHolder)holder).tvBt.setText("已同意");
            ((ViewHolder)holder).tvBt.setBackground(context.getDrawable(R.drawable.tenradious_ae_bg));
            ((ViewHolder)holder).tvBt.setTextColor(context.getColor(R.color.white));
        }
        if (listBean.getMsgType()==9){
            ((ViewHolder)holder).tvBt.setText("已拒绝");
            ((ViewHolder)holder).tvBt.setBackground(context.getDrawable(R.drawable.tenradious_ae_bg));
            ((ViewHolder)holder).tvBt.setTextColor(context.getColor(R.color.white));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_headimg)
        CustomRoundAngleImageView ivHeadimg;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.rl_item)
        RelativeLayout rlItem;
        @BindView(R.id.tv_bt)
        TextView tvBt;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
