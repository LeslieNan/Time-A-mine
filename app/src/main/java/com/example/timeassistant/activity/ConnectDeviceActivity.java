package com.example.timeassistant.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.timeassistant.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ConnectDeviceActivity extends BaseActivity {

    @BindView(R.id.et_wifiname)
    EditText etWifiname;
    @BindView(R.id.et_deviceip)
    EditText etDeviceip;
    @BindView(R.id.et_port)
    EditText etPort;
    @BindView(R.id.btn_connect)
    Button btnConnect;
    @BindView(R.id.btn_toolbar_left)
    Button btnToolbarLeft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_device);
        ButterKnife.bind(this);
        initToolbar(getWindow().getDecorView(), "Connect Device", getDrawable(R.drawable.ic_back_white_24dp), null);
    }


    @OnClick({R.id.btn_toolbar_left, R.id.btn_connect})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_toolbar_left:
                finish();
                break;
            case R.id.btn_connect:
                break;
        }
    }
}
