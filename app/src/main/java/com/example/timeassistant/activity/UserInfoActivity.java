package com.example.timeassistant.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.timeassistant.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserInfoActivity extends BaseActivity {

    @BindView(R.id.btn_toolbar_left)
    Button btnToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.btn_toolbar_right)
    Button btnToolbarRight;
    @BindView(R.id.et_user_id)
    EditText etUserId;
    @BindView(R.id.et_user_firstname)
    EditText etUserFirstname;
    @BindView(R.id.et_user_lastname)
    EditText etUserLastname;
    @BindView(R.id.sp_user_permission)
    Spinner spUserPermission;
    @BindView(R.id.et_user_password)
    EditText etUserPassword;
    @BindView(R.id.tv_user_fingercount)
    TextView tvUserFingercount;
    @BindView(R.id.fl_user_fingerprint)
    FrameLayout flUserFingerprint;
    @BindView(R.id.btn_user_delete)
    Button btnUserDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        ButterKnife.bind(this);
        initToolbar(getWindow().getDecorView(),"User",getDrawable(R.drawable.ic_back_white_24dp),getDrawable(R.drawable.ic_save_white_24dp));

    }

    @OnClick({R.id.btn_toolbar_left, R.id.btn_toolbar_right, R.id.fl_user_fingerprint, R.id.btn_user_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_toolbar_left:
                break;
            case R.id.btn_toolbar_right:
                break;
            case R.id.fl_user_fingerprint:
                break;
            case R.id.btn_user_delete:
                break;
        }
    }
}
