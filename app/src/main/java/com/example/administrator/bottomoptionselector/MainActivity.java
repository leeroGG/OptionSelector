package com.example.administrator.bottomoptionselector;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.administrator.bottomoptionselector.selector.OnConfirmListener;
import com.example.administrator.bottomoptionselector.selector.OnItemClickListener;
import com.example.administrator.bottomoptionselector.selector.OnTabSelectedListener;
import com.example.administrator.bottomoptionselector.selector.Option;
import com.example.administrator.bottomoptionselector.selector.OptionInterface;
import com.example.administrator.bottomoptionselector.selector.OptionSelector;
import com.example.administrator.bottomoptionselector.selector.Tab;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private OptionSelector selector;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.show_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Option> cities = new ArrayList<>();
                cities.add(new Option(1, "粤"));
                cities.add(new Option(2, "湘"));
                cities.add(new Option(3, "川"));
                cities.add(new Option(4, "鄂"));
                cities.add(new Option(5, "京"));
                cities.add(new Option(6, "津"));
                cities.add(new Option(7, "沈"));
                cities.add(new Option(7, "福特"));
                cities.add(new Option(7, "日产"));
                cities.add(new Option(7, "保时捷"));

                ArrayList<Option> codes = new ArrayList<>();
                codes.add(new Option(1, "A"));
                codes.add(new Option(2, "B"));
                codes.add(new Option(3, "C"));
                codes.add(new Option(4, "D"));
                codes.add(new Option(5, "E"));

                showDialog(cities, codes, codes, codes);
                selector.setOnConfirmListener(new OnConfirmListener() {
                    @Override
                    public void onCancel() {
                        dialog.dismiss();
                    }

                    @Override
                    public void onConfirm(List<OptionInterface> results) {
                        String str = "";
                        for (OptionInterface data : results) {
                            str += data.getName();
                        }
                        Toast.makeText(MainActivity.this, str, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
    
    private void showDialog(final ArrayList<Option>... array) {
        dialog = new Dialog(this, R.style.BottomDialog);
        View contentView = LayoutInflater.from(this).inflate(R.layout.layout_bottom_selector_dialog, null);
        dialog.setContentView(contentView);

        // 设置Dialog底部位置
        ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
        layoutParams.width = getResources().getDisplayMetrics().widthPixels;
        contentView.setLayoutParams(layoutParams);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        // 支持外部点击取消
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();

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
    }
}
