# OptionSelector
* 自由深度的多级联动选择器（层数过深需要自行把字体改小）</br>
#### 运行效果图
![image](https://github.com/leeroGG/OptionSelector/raw/master/effect.gif)</br>
#### 实现原理
* 一层一层地把view添加到LinearLayout中</br>
* Tab栏实际上是LinearLayout中添加TextView，每个Tab本质上就是一个TextView</br>
* Tab底部滑动的指示横线，实际上也是一个LinearLayout，添加一个滑动的动画效果</br>
* 最后一部分就是我们所熟悉的RecyclerView
#### 使用
```
// 选择器设置
selector = contentView.findViewById(R.id.select_view);
selector.setTabAmount(array.length);
// 默认先添加第一组数据
selector.setListData(array[0]);
// tab点击更换数据
selector.setOnTabSelectedListener(new OnTabSelectedListener() {
    @Override
    public void onTabSelected(OptionSelector optionSelector, Tab tab) {
        optionSelector.setListData(array[tab.getIndex()]);
    }

    @Override
    public void onTabReselected(OptionSelector optionSelector, Tab tab) {
        optionSelector.setListData(array[tab.getIndex()]);
    }
});
selector.setOnItemClickListener(new OnItemClickListener() {
    @Override
    public void itemClick(OptionSelector selector, OptionInterface option, int tabPosition) {
        // 设置下一项的列表数据
        if (tabPosition < array.length - 1) {
            selector.setListData(array[tabPosition + 1]);
        }
    }
});
```
结果获取提供setOnConfirmListener的监听，确认按钮的onConfirm回调方法中提供一个结果列表，记录已选的子项。
