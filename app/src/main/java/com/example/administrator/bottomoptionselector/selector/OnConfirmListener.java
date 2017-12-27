package com.example.administrator.bottomoptionselector.selector;

import java.util.List;

/**
 * <pre>
 *     author : Leero
 *     e-mail : 925230519@qq.com
 *     time  : 2017-12-16
 *     desc  :
 *     version: 1.0
 * </pre>
 */
public interface OnConfirmListener {
    void onCancel();
    void onConfirm(List<OptionInterface> results);
}
