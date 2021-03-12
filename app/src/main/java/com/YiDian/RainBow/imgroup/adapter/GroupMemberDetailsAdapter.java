package com.YiDian.RainBow.imgroup.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.custom.image.CustomRoundAngleImageView;
import com.YiDian.RainBow.imgroup.bean.GroupMemberBean;
import com.YiDian.RainBow.imgroup.bean.GroupMemberTwoBean;
import com.YiDian.RainBow.topic.SaveIntentMsgBean;
import com.YiDian.RainBow.user.PersonHomeActivity;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GroupMemberDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;

    private List<GroupMemberTwoBean.ObjectBean> list;
    private GroupMemberTwoBean.ObjectBean listBean;

    public GroupMemberDetailsAdapter(Context context) {

        this.context = context;
    }

    public  void setData(List<GroupMemberTwoBean.ObjectBean> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_group_member, null);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        listBean = list.get(position);

        ((ViewHolder)holder).tvName.setText(listBean.getNickName()+"");
        Glide.with(context).load(listBean.getHeadImg()).into(((ViewHolder)holder).ivImg);

        ((ViewHolder)holder).rlItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listBean =list.get(position);

                Intent intent = new Intent(context, PersonHomeActivity.class);
                SaveIntentMsgBean saveIntentMsgBean = new SaveIntentMsgBean();
                saveIntentMsgBean.setId(listBean.getUserId());
                //2标记传入姓名  1标记传入id
                saveIntentMsgBean.setFlag(1);
                intent.putExtra("msg", saveIntentMsgBean);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (list.size()>21){
            return 21;
        }else{
            return list.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_img)
        CustomRoundAngleImageView ivImg;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.rl_item)
        RelativeLayout rlItem;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
