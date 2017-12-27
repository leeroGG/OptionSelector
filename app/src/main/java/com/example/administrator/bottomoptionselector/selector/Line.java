package com.example.administrator.bottomoptionselector.selector;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * <pre>
 *     author : Leero
 *     e-mail : 925230519@qq.com
 *     time  : 2017-12-16
 *     desc  :
 *     version: 1.0
 * </pre>
 */
public class Line extends LinearLayout {

    private int sum = 1;
    private int oldIndex = 0;
    private int currentIndex = 0;
    private View indicator;
    private int selectedColor = Color.parseColor("#ef801c");

    public Line(Context context) {
        super(context);
        init(context);
    }

    public Line(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public Line(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setOrientation(HORIZONTAL);
        setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 6));
        setWeightSum(sum);

        indicator = new View(context);
        indicator.setLayoutParams(new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1));
        indicator.setBackgroundColor(selectedColor);

        addView(indicator);
    }

    public void setSum(int sum) {
        this.sum = sum;
        setWeightSum(sum);
    }

    public void setIndex(int index) {
        int onceWidth = getWidth() / sum;
        this.currentIndex = index;

        ObjectAnimator animator = ObjectAnimator.ofFloat(indicator, "translationX",
                indicator.getTranslationX(), (currentIndex - oldIndex) * onceWidth);
        animator.setDuration(300); // 动画时间
        animator.start();
    }
}
