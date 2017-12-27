package com.example.administrator.bottomoptionselector.selector;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.bottomoptionselector.R;

import java.util.ArrayList;

/**
 * <pre>
 *     author : Leero
 *     e-mail : 925230519@qq.com
 *     time  : 2017-12-16
 *     desc  :
 *     version: 1.0
 * </pre>
 */
public class OptionSelector extends LinearLayout  implements View.OnClickListener  {
    private Context mContext;

    // 默认选中字体颜色
    private int TextSelectedColor = Color.parseColor("#ef801c");
    // 默认字体颜色
    private int TextEmptyColor = Color.parseColor("#323232");
    // 顶部的tab集合
    private ArrayList<Tab> tabs;
    // 顶部按钮layout
    private View buttonView;
    // tabs的外层layout
    private LinearLayout tabs_layout;
    // 会移动的横线布局
    private Line line;
    // 总共tab的数量
    private int tabAmount = 1;
    // 当前tab的位置
    private int tabIndex = 0;
    // 分隔线
    private View grayLine;
    // 列表文字颜色
    private int listTextNormalColor = Color.parseColor("#323232");
    // 列表文字选中的颜色
    private int listTextSelectedColor = Color.parseColor("#ef801c");

    // 列表的适配器
    private AddressAdapter addressAdapter;
    // 展示的数据
    private ArrayList<OptionInterface> options;
    // 顶部按钮点击事件
    private OnConfirmListener onConfirmListener;
    // 列表item点击事件
    private OnItemClickListener onItemClickListener;
    // tab栏目点击事件
    private OnTabSelectedListener onTabSelectedListener;
    private RecyclerView list;
    // 选择的结果存储列表
    private ArrayList<OptionInterface> results;

    public OptionSelector(Context context) {
        super(context);
        init(context);
    }

    public OptionSelector(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public OptionSelector(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        removeAllViews();
        this.mContext = context;
        setOrientation(VERTICAL);

        // 初始化结果列表
        results = new ArrayList<>();

        // 添加确定、取消按钮
        buttonView = LayoutInflater.from(mContext).inflate(R.layout.selector_button_layout, this, false);
        addView(buttonView);
        buttonView.findViewById(R.id.cancel).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onConfirmListener.onCancel();
            }
        });
        buttonView.findViewById(R.id.confirm).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onConfirmListener.onConfirm(results);
            }
        });

        // tabs外层设置（水平LinearLayout）
        tabs_layout = new LinearLayout(mContext);
        tabs_layout.setWeightSum(tabAmount);
        tabs_layout.setLayoutParams(new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));
        tabs_layout.setOrientation(HORIZONTAL);
        addView(tabs_layout);

        // 填充tabs 首个默认为选中状态
        tabs = new ArrayList<>();
        Tab tab = newTab("请选择", true);
        tabs_layout.addView(tab);
        tabs.add(tab);
        for (int i = 1; i < tabAmount; i++) {
            Tab _tab = newTab("", false);
            _tab.setIndex(i);
            _tab.setLayoutParams(new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1.0f));
            tabs_layout.addView(_tab);
            tabs.add(_tab);
        }

        // 添加底部滑动的横线
        line = new Line(mContext);
        line.setLayoutParams(new LayoutParams(
                LayoutParams.MATCH_PARENT, 6));
        line.setSum(tabAmount);
        addView(line);

        // 添加tab和数据列表的分割线
        grayLine = new View(mContext);
        grayLine.setLayoutParams(new LayoutParams(
                LayoutParams.MATCH_PARENT, 2));
        grayLine.setBackgroundColor(Color.parseColor("#DDDDDD"));
        addView(grayLine);

        // 初始化内容列表
        list = new RecyclerView(mContext);
        list.setLayoutParams(new ViewGroup.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));
        list.setLayoutManager(new LinearLayoutManager(mContext));
        addView(list);
    }

    /**
     * 得到一个新的tab对象
     */
    private Tab newTab(CharSequence text, boolean isSelected) {
        Tab tab = new Tab(mContext);
        tab.setLayoutParams(new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1));
        tab.setGravity(Gravity.CENTER);
        tab.setPadding(0, 40, 0, 40);
        tab.setSelected(isSelected);
        tab.setText(text);
        tab.setTextEmptyColor(TextEmptyColor);
        tab.setTextSelectedColor(TextSelectedColor);
        tab.setOnClickListener(this);
        return tab;
    }

    /**
     * 设置tab的数量
     *
     * @param tabAmount tab的数量
     */
    public void setTabAmount(int tabAmount) {
        if (tabAmount >= 1) {
            this.tabAmount = tabAmount;
            init(mContext);
        } else
            throw new RuntimeException("tabs number must more than 1!");
    }

    /**
     * 设置顶部按钮的点击事件回调
     */
    public void setOnConfirmListener(OnConfirmListener onConfirmListener) {
        this.onConfirmListener = onConfirmListener;
    }

    /**
     * 设置顶部tab的点击事件回调
     */
    public void setOnTabSelectedListener(OnTabSelectedListener onTabSelectedListener) {
        this.onTabSelectedListener = onTabSelectedListener;
    }

    /**
     * 设置列表的点击事件回调接口
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * 设置列表的数据源，设置后立即生效
     */
    public void setListData(ArrayList options) {
        if (options == null || options.size() <= 0)
            return;
        if (options.get(0) instanceof OptionInterface) {
            this.options = options;
            if (addressAdapter == null) {
                addressAdapter = new OptionSelector.AddressAdapter();
                list.setAdapter(addressAdapter);
            }
            addressAdapter.notifyDataSetChanged();
        } else {
            throw new RuntimeException("Data must be implemented OptionInterface!");
        }
    }

    /**
     * tab的点击监听
     */
    @Override
    public void onClick(View v) {
        Tab tab = (Tab) v;
        //如果点击的tab大于等于当前index则直接跳出
        if (tab.index >= tabIndex)
            return;

        //处理选择结果
        //1、点击第一个tab，清空结果记录
        //2、点击位置非首项，且当前结果列表size大于点击tab的层级数，移除当前位置往后的记录
        if (tab.getIndex() == 0) {
            results.clear();
        } else if (results.size() > tab.getIndex() + 1) {
            for (int n = results.size() - 1; n > tab.getIndex(); n--) {
                results.remove(n);
            }
        }

        tabIndex = tab.getIndex();
        if (onTabSelectedListener != null) {
            if (tab.isSelected)
                onTabSelectedListener.onTabReselected(this, tab);
            else
                onTabSelectedListener.onTabSelected(this, tab);
        }
        resetAllTabs(tabIndex);
        line.setIndex(tabIndex);
        tab.setSelected(true);
    }

    private void resetAllTabs(int tabIndex) {
        if (tabs != null)
            for (int i = 0; i < tabs.size(); i++) {
                tabs.get(i).resetState();
                if (i > tabIndex) {
                    tabs.get(i).setText("");
                }
            }
    }

    private class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.MyViewHolder> {
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder viewHolder = new MyViewHolder(LayoutInflater.from(mContext).inflate(
                            android.R.layout.simple_expandable_list_item_1, parent, false));
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            // tab内容和列表中内容相同时改变字体颜色
            if (TextUtils.equals(tabs.get(tabIndex).getText(), options.get(position).getName())) {
                holder.tv.setTextColor(listTextSelectedColor);
            } else {
                holder.tv.setTextColor(listTextNormalColor);
            }
            holder.tv.setText(options.get(position).getName());
            holder.itemView.setTag(options.get(position));
            holder.itemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        OptionInterface option = (OptionInterface) v.getTag();
                        // 添加结果
                        // 当前点击的item的层级等于当前选择的层级，移除最后一个再添加
                        if (results.size() == tabIndex + 1){
                            results.remove(tabIndex);
                            results.add(option);
                        } else {
                            results.add(option);
                        }

                        // 更换当前tab
                        onItemClickListener.itemClick(OptionSelector.this, option, tabIndex);
                        tabs.get(tabIndex).setText(option.getName());
                        tabs.get(tabIndex).setTag(v.getTag());
                        if (tabIndex + 1 < tabs.size()) {
                            tabIndex++;
                            resetAllTabs(tabIndex);
                            line.setIndex(tabIndex);
                            tabs.get(tabIndex).setText("请选择");
                            tabs.get(tabIndex).setSelected(true);
                        }

                        // 刷新列表显示状态
                        notifyDataSetChanged();
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return options.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView tv;
            public View itemView;

            public MyViewHolder(View itemView) {
                super(itemView);
                this.itemView = itemView;
                tv = itemView.findViewById(android.R.id.text1);
            }
        }
    }
}
