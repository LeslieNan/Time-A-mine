package com.zkteco.android.framework.view.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zkteco.framework.R;


/**
 * Created by Allen-Lan on 2015/8/11.
 */
public class ZKCustomDialogUtils {
    private static final String TAG = "ZKCustomDialogUtils";
    public static final int DIALOG_LOADING = 0;
    public static final int DIALOG_SINGLE = 1;
    public static final int DIALOG_DOUBLE = 2;
    //用于在每个界面弹出打卡提醒的对话z框
    public static final int DIALOG_BASE = 3;
    public static final int DIALOG_EDIT = 4;

    public static final String DIALOG_GPS = "dialog_gps";

    public static Dialog dialog;
    public static Activity mActivity;

    /**
     * 得到自定义的progressDialog
     *
     * @param act
     * @param dialogStyle
     * @return
     */

    public static void show(Activity act, int dialogStyle) {
        if (DIALOG_LOADING == dialogStyle && getLoadingDialog(act) != null) {
            mActivity = act;
            if (act != null && getLoadingDialog(act) != null && !act.isFinishing()) {
                getLoadingDialog(act).show();
            }
        }
    }

    public static void show(Activity act, DialogInfo dialogInfo) {
        if (act != null && getContentDialog(act, dialogInfo) != null && !act.isFinishing()) {
            mActivity = act;
            getContentDialog(act, dialogInfo).show();
        }
    }

    public static void show(Context context, DialogInfo dialogInfo, DialogCallback callback) {
        dialog = null;
        if(context == null){
            return;
        }
        if((context instanceof Activity) && ((Activity)context).isFinishing()){
            return;
        }
        switch (dialogInfo.getmDialogStyle()) {
            case DIALOG_SINGLE:
            case DIALOG_DOUBLE:
                if (context != null && (createContentDialog(context, dialogInfo, callback) != null)) {
                    mActivity = (Activity) context;
                    createContentDialog(context, dialogInfo, callback).show();
                }
                break;
            case DIALOG_EDIT:
                if (context != null && (createEditDialog(context, dialogInfo, callback) != null)) {
                    mActivity = (Activity) context;
                    createEditDialog(context, dialogInfo, callback).show();
                }
        }
    }

    public static void show(Activity act, DialogInfo dialogInfo, int dialogStyle) {

        if (act == null || act.isFinishing()) {
            return;
        }
        mActivity = act;
        switch (dialogStyle) {
            case DIALOG_BASE:
                createContentBaseDialog(act, dialogInfo).show();
                break;
            case DIALOG_EDIT:
                createEditDialog(act, dialogInfo).show();
                break;
            default:
                break;
        }
    }

    public static Dialog getLoadingDialog(Activity act) {
        if (dialog == null) {
            dialog = createLoadingDialog(act);
        }
        return dialog;
    }

    public static Dialog getContentDialog(Activity act, DialogInfo dialogInfo) {
        if (dialog == null) {
            dialog = createContentDialog(act, dialogInfo);
        }
        return dialog;
    }

    public static Dialog createLoadingDialog(Activity act) {

        if (dialog == null) {
            LayoutInflater inflater = LayoutInflater.from(act);
            View v = inflater.inflate(R.layout.dialog_loading, null);// 得到加载view
//        LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_laoding_layout);// 加载布局
            ImageView loadingImg = (ImageView) v.findViewById(R.id.loading_img);

            AnimationDrawable animationDrawable = (AnimationDrawable) loadingImg.getDrawable();
            animationDrawable.start();
            dialog = new Dialog(act, R.style.dialog);// 创建自定义样式dialog
            dialog.setCancelable(false);// 不可以用“返回键”取消
            dialog.setCanceledOnTouchOutside(false);

            dialog.setContentView(v);
//        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.MATCH_PARENT,
//                LinearLayout.LayoutParams.WRAP_CONTENT));// 设置布局
        }
        return dialog;
    }

    public static Dialog createContentDialog(Activity act, DialogInfo dialogInfo) {
        if (dialog == null) {
            LayoutInflater inflater = LayoutInflater.from(act);
            View v = inflater.inflate(R.layout.zk_dialog, null);// 得到加载view
            TextView titleText = (TextView) v.findViewById(R.id.dialog_title);
            TextView messageText = (TextView) v.findViewById(R.id.dialog_message);
            RelativeLayout operateLayout = null;

            titleText.setText(dialogInfo.getmDialogTitle());
            messageText.setText(dialogInfo.getmDialogMessage());
            if (DIALOG_SINGLE == dialogInfo.getmDialogStyle()) {
                Button singleBtn = (Button) v.findViewById(R.id.dialog_button_single);
                singleBtn.setText(dialogInfo.getmSingleText());
                operateLayout = (RelativeLayout) v.findViewById(R.id.single_layout);
            } else if (DIALOG_DOUBLE == dialogInfo.getmDialogStyle()) {
                Button leftBtn = (Button) v.findViewById(R.id.dialog_button_left);
                Button rightBtn = (Button) v.findViewById(R.id.dialog_button_right);
                leftBtn.setText(dialogInfo.getmLeftText());
                rightBtn.setText(dialogInfo.getmRightText());
                rightBtn.setTag(dialogInfo.getmTag());
                operateLayout = (RelativeLayout) v.findViewById(R.id.double_layout);
            }

            if (operateLayout != null) {
                operateLayout.setVisibility(View.VISIBLE);
            }

            dialog = new Dialog(act, R.style.dialog);// 创建自定义样式dialog
            dialog.setCancelable(true);// 不可以用“返回键”取消
            dialog.setCanceledOnTouchOutside(false);

            dialog.setContentView(v);
        }
        return dialog;
    }

    public static Dialog createContentDialog(Context context, DialogInfo dialogInfo, final DialogCallback callback) {
        if (callback == null) return null;
        if (dialog == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View v = inflater.inflate(R.layout.zk_dialog, null);// 得到加载view
            TextView titleText = (TextView) v.findViewById(R.id.dialog_title);
            TextView messageText = (TextView) v.findViewById(R.id.dialog_message);
            RelativeLayout operateLayout = null;

            titleText.setText(dialogInfo.getmDialogTitle());
            messageText.setText(dialogInfo.getmDialogMessage());
            if (DIALOG_SINGLE == dialogInfo.getmDialogStyle()) {
                Button singleBtn = (Button) v.findViewById(R.id.dialog_button_single);
                singleBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        callback.onSingle();
                    }
                });
                singleBtn.setText(dialogInfo.getmSingleText());
                operateLayout = (RelativeLayout) v.findViewById(R.id.single_layout);
            } else if (DIALOG_DOUBLE == dialogInfo.getmDialogStyle()) {
                Button leftBtn = (Button) v.findViewById(R.id.dialog_button_left);
                leftBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cancelTry();
                        callback.onNegative();
                    }
                });
                Button rightBtn = (Button) v.findViewById(R.id.dialog_button_right);
                rightBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        callback.onPositive();
                    }
                });
                leftBtn.setText(dialogInfo.getmLeftText());
                rightBtn.setText(dialogInfo.getmRightText());
                rightBtn.setTag(dialogInfo.getmTag());
                operateLayout = (RelativeLayout) v.findViewById(R.id.double_layout);
            }

            if (operateLayout != null) {
                operateLayout.setVisibility(View.VISIBLE);
            }

            dialog = new Dialog(context, R.style.dialog);// 创建自定义样式dialog
            dialog.setCancelable(true);// 不可以用“返回键”取消
            dialog.setCanceledOnTouchOutside(false);
            dialog.setContentView(v);
        }
        return dialog;
    }

    public static Dialog createContentBaseDialog(Activity act, DialogInfo dialogInfo) {
        if (dialog == null) {
            LayoutInflater inflater = LayoutInflater.from(act);
            View v = inflater.inflate(R.layout.zk_dialog_base, null);// 得到加载view
            TextView titleText = (TextView) v.findViewById(R.id.dialog_title);
            TextView messageText = (TextView) v.findViewById(R.id.dialog_message);
            RelativeLayout operateLayout = null;

            titleText.setText(dialogInfo.getmDialogTitle());
            messageText.setText(dialogInfo.getmDialogMessage());

            Button leftBtn = (Button) v.findViewById(R.id.dialog_button_left);
            Button rightBtn = (Button) v.findViewById(R.id.dialog_button_right);
            leftBtn.setText(dialogInfo.getmLeftText());
            rightBtn.setText(dialogInfo.getmRightText());
            operateLayout = (RelativeLayout) v.findViewById(R.id.double_layout);

            if (operateLayout != null) {
                operateLayout.setVisibility(View.VISIBLE);
            }

            dialog = new Dialog(act, R.style.dialog);// 创建自定义样式dialog
            dialog.setCancelable(true);// 不可以用“返回键”取消
            dialog.setCanceledOnTouchOutside(false);

            dialog.setContentView(v);
        }
        return dialog;
    }

    public static Dialog createEditDialog(Activity act, DialogInfo dialogInfo) {
        if (dialog == null) {
            LayoutInflater inflater = LayoutInflater.from(act);
            View v = inflater.inflate(R.layout.zk_edit_dialog, null);
            TextView titleText = (TextView) v.findViewById(R.id.dialog_title);
            EditText messageText = (EditText) v.findViewById(R.id.dialog_message);
//            RelativeLayout operateLayout = (RelativeLayout) v.findViewById(R.id.double_layout);

            titleText.setText(dialogInfo.getmDialogTitle());
            messageText.setText(dialogInfo.getmDialogMessage());
            Button leftBtn = (Button) v.findViewById(R.id.dialog_button_left);
            Button rightBtn = (Button) v.findViewById(R.id.dialog_button_right);
            leftBtn.setText(dialogInfo.getmLeftText());
            rightBtn.setText(dialogInfo.getmRightText());
            rightBtn.setTag(messageText);

            dialog = new Dialog(act, R.style.dialog);// 创建自定义样式dialog
            dialog.setCancelable(true);// 不可以用“返回键”取消
            dialog.setCanceledOnTouchOutside(false);
            //弹出输入框
//            InputMethodManager imm = (InputMethodManager) act.getSystemService(Context.INPUT_METHOD_SERVICE);
//            imm.showSoftInput(messageText, InputMethodManager.SHOW_FORCED);

            dialog.setContentView(v);
        }
        return dialog;
    }

    public static Dialog createEditDialog(Context context, DialogInfo dialogInfo, final DialogCallback callback) {
        if (callback == null) return null;
        if (dialog == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View v = inflater.inflate(R.layout.zk_edit_dialog, null);
            TextView titleText = (TextView) v.findViewById(R.id.dialog_title);
            final EditText messageText = (EditText) v.findViewById(R.id.dialog_message);
//            RelativeLayout operateLayout = (RelativeLayout) v.findViewById(R.id.double_layout);

            titleText.setText(dialogInfo.getmDialogTitle());
            messageText.setText(dialogInfo.getmDialogMessage());
            Button leftBtn = (Button) v.findViewById(R.id.dialog_button_left);
            leftBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.onCancel();
                }
            });
            Button rightBtn = (Button) v.findViewById(R.id.dialog_button_right);
            rightBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.onInputConfirm(messageText.getText().toString());
                }
            });
            leftBtn.setText(dialogInfo.getmLeftText());
            rightBtn.setText(dialogInfo.getmRightText());
            rightBtn.setTag(messageText);

            dialog = new Dialog(context, R.style.dialog);// 创建自定义样式dialog
            dialog.setCancelable(true);// 不可以用“返回键”取消
            dialog.setCanceledOnTouchOutside(false);
            //弹出输入框
//            InputMethodManager imm = (InputMethodManager) act.getSystemService(Context.INPUT_METHOD_SERVICE);
//            imm.showSoftInput(messageText, InputMethodManager.SHOW_FORCED);

            dialog.setContentView(v);
        }
        return dialog;
    }

    public static void cancel() {
        try {
            if (dialog != null && mActivity != null && !mActivity.isFinishing()) {
                if(dialog.isShowing()){
                    dialog.dismiss();
                }
                cancelTry();
            }
        }catch (Exception e){
            Log.e(TAG, "cancelTry: ", e);
        }
    }

    public static void cancelTry() {
        if (dialog != null) {
            try {
                if(mActivity != null && !mActivity.isFinishing()){
                    dialog.cancel();
                    dialog = null;
                }
            } catch (Exception e) {
                Log.e(TAG, "cancelTry: ", e);
            }
        }
    }
}
