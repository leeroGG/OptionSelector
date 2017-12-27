package com.example.administrator.bottomoptionselector.selector;

/**
 * <pre>
 *     author : Leero
 *     e-mail : 925230519@qq.com
 *     time  : 2017-12-16
 *     desc  :
 *     version: 1.0
 * </pre>
 */
public interface OnItemClickListener {
    /**
     * @param option 返回列表对应点击的对象
     * @param tabPosition 对应tab的位置
     * */
    void itemClick(OptionSelector optionSelector, OptionInterface option, int tabPosition);
}
