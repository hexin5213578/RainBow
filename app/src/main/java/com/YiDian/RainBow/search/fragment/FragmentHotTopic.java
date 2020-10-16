package com.YiDian.RainBow.search.fragment;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseFragment;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.custom.flawlayout.FlowLayout;
import com.YiDian.RainBow.search.activity.SearchMsgActivity;
import com.YiDian.RainBow.search.adapter.FirstHotSearchAdapter;
import com.YiDian.RainBow.search.adapter.HotTopicAdapter;
import com.YiDian.RainBow.utils.KeyBoardUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;

public class FragmentHotTopic extends BaseFragment implements View.OnClickListener {
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.iv_delete_history)
    ImageView ivDeleteHistory;
    @BindView(R.id.search_history)
    FlowLayout searchHistory;
    @BindView(R.id.rc_hotsearch_first)
    RecyclerView rcHotsearchFirst;
    @BindView(R.id.iv_tohotHuati)
    ImageView ivTohotHuati;
    @BindView(R.id.rc_hotHuati)
    RecyclerView rcHotHuati;
    Set<String> stringList = new HashSet<>();
    @BindView(R.id.rl2)
    RelativeLayout rl2;
    @BindView(R.id.v1)
    View v1;
    private TextView label;
    private PopupWindow mPopupWindow;

    @Override
    protected void getid(View view) {

    }

    @Override
    protected int getResId() {
        return R.layout.search_fragment_huati;
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void getData() {
        if (stringList.size() == 0) {
            rl2.setVisibility(View.GONE);
            v1.setVisibility(View.GONE);
        }
        //绑定点击事件
        tvSearch.setOnClickListener(this);
        ivDeleteHistory.setOnClickListener(this);
        List<String> list = new ArrayList<>();
        //设置热门搜索适配器
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        rcHotsearchFirst.setLayoutManager(gridLayoutManager);
        FirstHotSearchAdapter firstHotSearchAdapter = new FirstHotSearchAdapter(getContext(), list);
        rcHotsearchFirst.setAdapter(firstHotSearchAdapter);

        //设置热门话题
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        rcHotHuati.setLayoutManager(linearLayoutManager);
        HotTopicAdapter hotTopicAdapter = new HotTopicAdapter(getContext(), list);
        rcHotHuati.setAdapter(hotTopicAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_search:
                String s = etSearch.getText().toString();
                if (TextUtils.isEmpty(s)) {
                    Toast.makeText(getContext(), "搜索内容不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    stringList.add(s);

                    //判断是否有搜索历史
                    if (stringList.size() > 0) {
                        rl2.setVisibility(View.VISIBLE);
                        v1.setVisibility(View.VISIBLE);
                    }
                    searchHistory.removeAllViews();
                    //遍历展示
                    for (String string : stringList) {

                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT);
                        layoutParams.setMargins(15, 12, 0, 0);

                        View inflater = View.inflate(getContext(), R.layout.item_personal_flow_labels, null);
                        label = (TextView) inflater.findViewById(R.id.tv_label_name);
                        label.setText(string);
                        searchHistory.addView(inflater, layoutParams);

                        label.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //将选择的历史记录回显到输入框
                                etSearch.setText(string);
                            }
                        });
                    }
                    etSearch.setText("");

                    Intent intent = new Intent(getContext(), SearchMsgActivity.class);
                    intent.putExtra("text",s);
                    startActivity(intent);
                }
                break;
            case R.id.iv_delete_history:
                //展示删除历史记录框
                showselect();
                //关闭软键盘
                KeyBoardUtils.closeKeyboard(etSearch);
                break;
        }
    }


    //设置透明度
    public void setWindowAlpa(boolean isopen) {
        if (android.os.Build.VERSION.SDK_INT < 11) {
            return;
        }
        final Window window =getActivity().getWindow();
        final WindowManager.LayoutParams lp = window.getAttributes();
        window.setFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND, WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        ValueAnimator animator;
        if (isopen) {
            animator = ValueAnimator.ofFloat(1.0f, 0.5f);
        } else {
            animator = ValueAnimator.ofFloat(0.5f, 1.0f);
        }
        animator.setDuration(400);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float alpha = (float) animation.getAnimatedValue();
                lp.alpha = alpha;
                window.setAttributes(lp);
            }
        });
        animator.start();
    }
    public void showselect(){
        //要执行的操作
        mPopupWindow = new PopupWindow();
        mPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_tongyong, null);
        TextView tv_title = view.findViewById(R.id.tv_title);
        TextView tv_confirm = view.findViewById(R.id.tv_confirm);
        TextView tv_cancle = view.findViewById(R.id.tv_cancle);

        tv_title.setText("确认删除所有历史记录？");
        tv_confirm.setText("确认");
        tv_cancle.setText("取消");
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //清空集合
                stringList.clear();
                if (stringList.size() == 0) {
                    rl2.setVisibility(View.GONE);
                    v1.setVisibility(View.GONE);
                    dismiss();
                }
            }
        });
        tv_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        //popwindow设置属性
        mPopupWindow.setContentView(view);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.setFocusable(false);
        mPopupWindow.setOutsideTouchable(false);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setWindowAlpa(false);
            }
        });
        show(view);
    }
    private void show(View v) {
        if (mPopupWindow != null && !mPopupWindow.isShowing()) {
            mPopupWindow.showAtLocation(v, Gravity.CENTER_HORIZONTAL, 0, 0);
        }
        setWindowAlpa(true);
    }
    public void dismiss() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
    }
}
