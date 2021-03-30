package com.YiDian.RainBow.map.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.map.bean.SaveNearMessageBean;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @ProjectName: LianXiang_lianzhonglian
 * @Package: com.LianXiangKeJi.SupplyChain.map.adapter
 * @ClassName: MapApapter
 * @Description: (java类作用描述)
 * @Author: 何梦洋
 * @CreateDate: 2020/7/16 19:52
 */
public class MapApapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private OnItemListener onItemListener;
    private Context context;
    private List<SaveNearMessageBean> list;
    private SaveNearMessageBean saveNearMessageBean;
    private int defItem = -1;

    public void setContext(Context con) {
        this.context = con;
    }

    public void setData(List<SaveNearMessageBean> list1) {
        this.list = list1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_map, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        saveNearMessageBean = list.get(position);

        if(saveNearMessageBean.isIsselect()==true){
            //选中状态
            ((ViewHolder) holder).ivSelect.setVisibility(View.VISIBLE);
        }else {
            ((ViewHolder) holder).ivSelect.setVisibility(View.GONE);
        }

        if (defItem != -1) {
            if (defItem == position) {
                //点击的位置
                if(saveNearMessageBean.isIsselect()==true){
                    //选中状态
                    ((ViewHolder) holder).ivSelect.setVisibility(View.VISIBLE);
                }
            } else {
                  //没有点击的位置都变成不展示
                ((ViewHolder) holder).ivSelect.setVisibility(View.GONE);
            }
        }

        ((ViewHolder) holder).tv.setText(saveNearMessageBean.getTitle());
        ((ViewHolder) holder).tv1.setText(saveNearMessageBean.getShengfen() + saveNearMessageBean.getShiqu() + saveNearMessageBean.getXian() + saveNearMessageBean.getAddress());

        ((ViewHolder) holder).ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveNearMessageBean saveNearMessageBean = list.get(position);
                EventBus.getDefault().post(saveNearMessageBean);


                if (onItemListener != null) {
                    onItemListener.onClick(((ViewHolder)holder), position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv)
        TextView tv;
        @BindView(R.id.tv1)
        TextView tv1;
        @BindView(R.id.ll)
        RelativeLayout ll;
        @BindView(R.id.iv_select)
        ImageView ivSelect;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
    public void setOnItemListener(OnItemListener onItemListener) {
        this.onItemListener = onItemListener;
    }
    public interface OnItemListener {
        void onClick(ViewHolder holder, int position);
    }
    public void setDefSelect(int position) {
        this.defItem = position;
    }

}
