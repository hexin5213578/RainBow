package com.YiDian.RainBow.main.fragment.mine.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.main.fragment.home.adapter.NewDynamicAdapter;
import com.YiDian.RainBow.main.fragment.mine.activity.MydraftActivity;
import com.YiDian.RainBow.main.fragment.mine.bean.SelectAllDraftsBean;

import java.util.List;

public class MyDraftsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    private final List<SelectAllDraftsBean.ObjectBean.ListBean> list;

    public MyDraftsAdapter(Context context, List<SelectAllDraftsBean.ObjectBean.ListBean> list) {

        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //纯文本
        if (viewType == 1) {
            View view = View.inflate(context, R.layout.item_dynamic_text, null);
            return new ViewHolder(view);
        }
        //纯图片
        if (viewType == 2) {
            View view = View.inflate(context, R.layout.item_dynamic_img, null);
            return new ViewHolder(view);
        }
        //文本加图片
        if (viewType == 21) {
            View view = View.inflate(context, R.layout.item_dynamic_text_img, null);
            return new ViewHolder(view);
        }
        //纯视频
        if (viewType == 3) {
            View view = View.inflate(context, R.layout.item_dynamic_video, null);
            return new ViewHolder(view);
        }
        //视频加文本
        if (viewType == 31) {
            View view = View.inflate(context, R.layout.item_dynamic_video_text, null);
            return new ViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
