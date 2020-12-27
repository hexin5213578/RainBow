package com.YiDian.RainBow.custom.friend;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.YiDian.RainBow.R;

public class LetterView extends LinearLayout {
    private Context mContext;
    private CharacterClickListener mListener;

    public LetterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;//接收传进来的上下文
        setOrientation(VERTICAL);
        initView();
    }

    private void initView(){

        for (char i = 'A';i<='Z';i++){
            final String character = i + "";
            TextView tv = buildTextLayout(character);
            addView(tv);
        }

        addView(buildTextLayout("#"));
    }

    private TextView buildTextLayout(final String character){
        LayoutParams layoutParams =
                new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT,1);
            TextView tv = new TextView(mContext);
        tv.setLayoutParams(layoutParams);
        tv.setGravity(Gravity.CENTER);
        tv.setClickable(true);
        tv.setTextSize(12);
        tv.setText(character);
        tv.setOnClickListener(new OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                if (mListener != null){
                    mListener.clickCharacter(character);
                    tv.setTextColor(R.color.color_8867E7);
                }
            }
        });
        return tv;
    }
    public void setCharacterListener(CharacterClickListener listener) {
        mListener = listener;
    }

    public interface CharacterClickListener {
        void clickCharacter(String character);
    }
}
