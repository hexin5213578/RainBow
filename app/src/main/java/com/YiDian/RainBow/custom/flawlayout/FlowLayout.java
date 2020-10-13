package com.YiDian.RainBow.custom.flawlayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.YiDian.RainBow.R;


/**
 * 1.重写onLayout， onMeasure， 构造方法
 * 2.使用measureChildren(widthMeasureSpec, heightMeasureSpec);测量所有的孩子
 * 3.使用MeasureSpec.getSize( widthMeasureSpec 或者 heightMeasureSpec)获取控件的宽高
 * 4.使用 setMeasuredDimension 方法测量自己
 * 5.添加 compare 方法，用来通过孩子的宽度配合控件的宽度以及使用的宽度，计算孩子的摆放位置
 * 6.给出初始化的已使用 宽度 和已使用 高度 ，初始化值为 getPaddingLeft() 以及 getPaddingTop()
 * 7.遍历，拿到所有的孩子
 * 8.拿到孩子的宽高
 * 9.通过 控件的宽度 与 孩子的宽度 与 已使用的宽度 结合判断，孩子是否需要换行显示
 * 10.如果需要换行，已使用宽度 改为 getPaddingLeft()； 已使用高度 += 孩子的高度
 * 11.然后把 已使用的宽度 += 孩子的宽度
 * 12.通过 new rect当做孩子的标记，其中 top 值为 已使用的高度，bottom 为已使用高度 + 孩子真正的高度
 *    left 为 已使用宽度 - 孩子真正的宽度， right 为 已使用宽度
 * 13.把 rect 当做 tag 设置给孩子
 * 14.在 onlayout 中， 遍历获取所有孩子
 * 15.拿到孩子的标记
 * 16.根据标记，重新通过 layout 方法，设置孩子的位置
 *
 *
 * 以下自定义属性流程：
 * 1.在我们values下面找到attrs.xml文件，如果没有，自己创建一个
 * 2.在attrs.xml中的<resources>标签下，添加<declare-styleable标签，其中name要和我们需要使用这个属性的类的类名严格一致
 * 3.在<declare-styleable></declare-styleable>下，添加<attr标签，name就是我们自定义属性的名字，
 *   format是自定义的限制，多个限制可以使用|分割
 * 4.在我们使用了这个类的布局文件中，在根布局中，添加 xmlns:app="http://schemas.android.com/apk/res-auto"
 *   注意，他和系统的提供的 xmlns:android="http://schemas.android.com/apk/res/android"很相近
 *   我们可以直接粘贴过来，把头部的 android 改成 app 尾部的res/android 改成 res-auto
 * 5.在我们具体的控件中，使用 app:我们自己写的属性名字，添加我们想要添加的值
 * 6.在具体类的至少两个参数的构造方法中，通过 context.obtainStyledAttributes(attrs, R.styleable.刚才第二步定义的类型);
 *   来获取TypedArray实例
 * 7.根据类型，使用getXXX获取具体的值，注意：在获取值的时候，需要传入 R.styleable.类型_属性名字，会有系统提示
 * 8.根据需求使用该属性
 *
 */
public class FlowLayout extends ViewGroup {
    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //让系统计算所有的孩子
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        //获取当前控件也就是ViewGroup的宽高
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        //控件总宽度 - 控件右内边距 = 控件实际可以使用宽度
        compare(width - getPaddingRight());
        //测量自己
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //根据孩子的数量进行遍历
        for(int i = 0; i < getChildCount(); i++){
            //根据角标，获取当前孩子
            View child = getChildAt(i);
            //获取标记
            Rect rect = (Rect) child.getTag();
            //根据标记，赋值
            child.layout(rect.left, rect.top, rect.right, rect.bottom);
        }
    }

    /**
     * 用来通过孩子的宽度配合控件的宽度以及使用的宽度，计算孩子的摆放位置
     * @param width 当前ViewGroup可以使用宽
     */
    private void compare(int width){
        //使用宽度，初始化是getPaddingLeft,也就是我们控件的左侧内边距
        int usedWidth = getPaddingLeft();

        //使用高度，初始化是getPaddingTop, 也就是我们控件的上边内边距
        int usedHeight = getPaddingTop();

        //根据孩子的数量进行遍历
        for(int i = 0; i < getChildCount(); i++){
            //根据角标，获取当前孩子
            View child = getChildAt(i);

            //拿到孩子的宽度，不包含外边距
            int childWidth = child.getMeasuredWidth();
            //拿到孩子的高度，不包含外边距
            int childHeight = child.getMeasuredHeight();

            //获取孩子的布局管理
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) child.getLayoutParams();

            //孩子真正占用的宽度 = 孩子测量的宽度 + 孩子左边外边距 + 孩子右边的外边距
            int childWidthReal = childWidth + layoutParams.leftMargin + layoutParams.rightMargin;

            //孩子真正占用的高度 = 孩子测量的高度 + 孩子上边外边距 + 孩子下边的外边距
            int childHeightReal = childHeight + layoutParams.topMargin + layoutParams.bottomMargin;

            //如果已经使用的 宽度 + 孩子真正占用的高度 > 屏幕宽度，说明需要换行
            if((usedWidth + childWidthReal) > width){
                //如果需要换行，使用过的宽度，肯定是控件的左侧内边距
                usedWidth = getPaddingLeft();
                //如果需要换行，使用过的高度，肯定以前的高度 + 孩子真正占用的高度
                usedHeight += childHeightReal;
            }

            //不管有没有换行，使用宽度都等于 原来使用的宽度 + 孩子真正的宽度
            usedWidth += childWidthReal;

            Rect rect = new Rect();
            rect.top = usedHeight;
            rect.bottom = usedHeight + childHeightReal;
            rect.left = usedWidth - childWidthReal;
            rect.right = usedWidth;

            //给孩子添加了标记
            child.setTag(rect);
        }
    }
}
