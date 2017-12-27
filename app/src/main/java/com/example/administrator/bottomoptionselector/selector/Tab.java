package com.example.administrator.bottomoptionselector.selector;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;

/**
 * <pre>
 *     author : Leero
 *     e-mail : 925230519@qq.com
 *     time  : 2017-12-16
 *     desc  :
 *     version: 1.0
 * </pre>
 */
public class Tab extends android.support.v7.widget.AppCompatTextView {

    public int index = 0;
    private int TextSelectedColor = Color.parseColor("#ef801c");
    private int TextEmptyColor = Color.parseColor("#323232");

    public boolean isSelected = false;  // 选中状态

    public Tab(Context context) {
        super(context);
        init();
    }

    public Tab(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Tab(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setTextSize(15);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
        if (isSelected) {
            setTextColor(TextSelectedColor);
        } else {
            setTextColor(TextEmptyColor);
        }
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        isSelected = selected;
        setText(getText());
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void resetState() {
        isSelected = false;
        setText(getText());
    }

    public void setTextSelectedColor(int textSelectedColor) {
        TextSelectedColor = textSelectedColor;
    }

    public void setTextEmptyColor(int textEmptyColor) {
        TextEmptyColor = textEmptyColor;
    }
}
