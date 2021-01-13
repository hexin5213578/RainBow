package com.YiDian.RainBow.main.fragment.msg.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.main.fragment.msg.bean.GiftMsgBean;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by ZXM on 2016/9/12.
 */
public class GridViewAdapter extends BaseAdapter {
    private List<GiftMsgBean.ObjectBean> mDatas;
    private LayoutInflater inflater;
    private Context mContext;
    /**
     * 页数下标,从0开始(当前是第几页)
     */
    private int curIndex;
    /**
     * 每一页显示的个数
     */
    private int pageSize = 8;

    public GridViewAdapter(Context context, List<GiftMsgBean.ObjectBean> mDatas, int curIndex) {
        inflater = LayoutInflater.from(context);
        this.mDatas = mDatas;
        this.curIndex = curIndex;
        this.mContext = context;
    }

    /**
     * 先判断数据集的大小是否足够显示满本页？mDatas.size() > (curIndex+1)*pageSize,
     * 如果够，则直接返回每一页显示的最大条目个数pageSize,
     * 如果不够，则有几项返回几,(mDatas.size() - curIndex * pageSize);(也就是最后一页的时候就显示剩余item)
     */
    @Override
    public int getCount() {
        return mDatas.size() > (curIndex + 1) * pageSize ? pageSize : (mDatas.size() - curIndex * pageSize);
    }

    @Override
    public GiftMsgBean.ObjectBean getItem(int position) {
        return mDatas.get(position + curIndex * pageSize);
    }

    @Override
    public long getItemId(int position) {
        return position + curIndex * pageSize;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_gridview, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tv = (TextView) convertView.findViewById(R.id.textView);
            viewHolder.iv = (ImageView) convertView.findViewById(R.id.imageView);
            viewHolder.tv_glod =  (TextView) convertView.findViewById(R.id.tv_glod);
            viewHolder.item_layout = (RelativeLayout) convertView.findViewById(R.id.item_layout);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        /**
         * 在给View绑定显示的数据时，计算正确的position = position + curIndex * pageSize，
         */
        GiftMsgBean.ObjectBean model = getItem(position);
        viewHolder.tv.setText(model.getGiftName());
        Glide.with(mContext).load(model.getGiftImg()).into(viewHolder.iv);
        viewHolder.tv_glod.setText(model.getGoldNum()+"金币");
        if (model.isSelected()){//被选中
            viewHolder.item_layout.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.item_selector));

            Animation rotateAnimation = new RotateAnimation(0, 20, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setInterpolator(new CycleInterpolator(4));
            rotateAnimation.setDuration(500);
            rotateAnimation.setFillAfter(false);

            viewHolder.iv.setAnimation(rotateAnimation);
        }else {
            viewHolder.item_layout.setBackgroundDrawable(null);
        }
        return convertView;
    }


    class ViewHolder {
        public RelativeLayout item_layout;
        public TextView tv;
        public ImageView iv;
        public TextView tv_glod;
    }
}