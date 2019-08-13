package com.example.timeassistant.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.timeassistant.R;

import butterknife.BindView;
import butterknife.OnClick;

public class DisconnectDialog extends DialogFragment {

    @BindView(R.id.tv_dialog_content)
    TextView tvDialogContent;
    @BindView(R.id.btn_dialog_cancel)
    Button btnDialogCancel;
    @BindView(R.id.btn_dialog_yes)
    Button btnDialogYes;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_disconnect, container, false);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @OnClick({R.id.btn_dialog_cancel, R.id.btn_dialog_yes})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_dialog_cancel:
                dismiss();
                break;
            case R.id.btn_dialog_yes:

                break;
        }
    }

}
