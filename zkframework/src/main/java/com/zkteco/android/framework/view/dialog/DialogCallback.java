package com.zkteco.android.framework.view.dialog;


/**
 * Created by xqc on 2015/12/16.
 * ZKTeco App
 */
public class DialogCallback {
    public void onPositive() {
        onCancel();
    }

    public void onNegative() {
        onCancel();
    }

    public void onSingle() {
        onCancel();
    }

    public void onCancel() {
        ZKCustomDialogUtils.cancelTry();
    }

    public void onInputConfirm(String text) {
        onCancel();
    }
}
