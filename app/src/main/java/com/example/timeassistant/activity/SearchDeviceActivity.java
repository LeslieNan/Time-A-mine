package com.example.timeassistant.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.timeassistant.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchDeviceActivity extends BaseActivity {

    @BindView(R.id.btn_toolbar_left)
    Button btnToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.btn_toolbar_right)
    Button btnToolbarRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_searchdevice)
    RecyclerView rvSearchdevice;
    @BindView(R.id.swipe_searchdevice)
    SwipeRefreshLayout swipeSearchdevice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_device);
        ButterKnife.bind(this);
        initToolbar(getWindow().getDecorView(), "Search", getDrawable(R.drawable.ic_back_white_24dp), null);
        initSwip();
    }


    @OnClick(R.id.btn_toolbar_left)
    public void onViewClicked() {
        finish();
    }

    private void initSwip() {
//        swipeSearchdevice.setRefreshing(true);
        swipeSearchdevice.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

            }
        });
    }
}
