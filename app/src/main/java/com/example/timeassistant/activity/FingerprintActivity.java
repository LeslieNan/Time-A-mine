package com.example.timeassistant.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timeassistant.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FingerprintActivity extends BaseActivity {

    @BindView(R.id.btn_toolbar_left)
    Button btnToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.btn_toolbar_right)
    Button btnToolbarRight;
    @BindView(R.id.rv_fingerprint)
    RecyclerView rvFingerprint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fingerprint);
        ButterKnife.bind(this);
        initToolbar(getWindow().getDecorView(),"Fingerprint",getDrawable(R.drawable.ic_back_white_24dp),getDrawable(R.drawable.ic_add_white_24dp));

    }

    @OnClick({R.id.btn_toolbar_left, R.id.btn_toolbar_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_toolbar_left:
                break;
            case R.id.btn_toolbar_right:
                break;
        }
    }
}
