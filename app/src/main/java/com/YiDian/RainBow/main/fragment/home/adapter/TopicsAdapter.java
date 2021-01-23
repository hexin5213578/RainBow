package com.YiDian.RainBow.main.fragment.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.main.fragment.home.bean.DynamicDetailsBean;
import com.YiDian.RainBow.topic.SaveIntentMsgBean;
import com.YiDian.RainBow.topic.TopicDetailsActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TopicsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    private final List<DynamicDetailsBean.ObjectBean.TopicsBean> list;

    public TopicsAdapter(Context context, List<DynamicDetailsBean.ObjectBean.TopicsBean> list) {

        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_topics, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder) holder).tvText.setText("#"+list.get(position).getTopicTitle());

        ((ViewHolder)holder).rlItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, TopicDetailsActivity.class);
                SaveIntentMsgBean saveIntentMsgBean = new SaveIntentMsgBean();
                saveIntentMsgBean.setMsg(list.get(position).getTopicTitle());
                //2标记传入话题名  1标记传入id
                saveIntentMsgBean.setFlag(2);
                intent.putExtra("msg", saveIntentMsgBean);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_text)
        TextView tvText;
        @BindView(R.id.rl_item)
        RelativeLayout rlItem;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
