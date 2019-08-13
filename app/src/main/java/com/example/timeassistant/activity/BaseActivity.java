package com.example.timeassistant.activity;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.timeassistant.R;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseApplication.getInstance().addActivity(this);
        setTopLayout();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BaseApplication.getInstance().removeActivity(this);
    }

    /**
     * 设置toolbar的样式
     *
     * @param view  根view
     * @param title 标题
     * @param left  左边icon
     * @param right 右边icon
     */
    public void initToolbar(View view, String title, Drawable left, Drawable right) {
        view.findViewById(R.id.btn_toolbar_left).setBackground(left);
        ((TextView) (view.findViewById(R.id.tv_toolbar_title))).setText(title);
        view.findViewById(R.id.btn_toolbar_right).setBackground(right);

    }

    /**
     * 设置顶部状态栏颜色
     */
    public void setTopLayout() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
