package com.zkteco.android.framework.view.dialog;

import android.content.Context;
import android.support.annotation.StringRes;

/**
 * Created by Allen-Lan on 2015/8/12.
 */
public class DialogInfo {
    public static Context mContext;
    private String mDialogTitle = "";
    private String mDialogMessage = "";
    private String mLeftText = "";
    private String mRightText = "";
    private String mSingleText = "";
    private int mDialogStyle = 0;
    private String mTag;

    public DialogInfo() {
    }

    public DialogInfo(Context context, @StringRes int title, String text, @StringRes int btnText) {
        mDialogTitle = context.getString(title);
        mDialogMessage = text;
        mSingleText = context.getString(btnText);
        mDialogStyle = ZKCustomDialogUtils.DIALOG_SINGLE;
    }

    public static Context getmContext() {
        return mContext;
    }

    public static void setmContext(Context mContext) {
        DialogInfo.mContext = mContext;
    }

    public String getmTag() {
        return mTag;
    }

    public void setmTag(String mTag) {
        this.mTag = mTag;
    }

    public String getmDialogTitle() {
        return mDialogTitle;
    }

    public void setmDialogTitle(String mDialogTitle) {
        this.mDialogTitle = mDialogTitle;
    }

    public String getmDialogMessage() {
        return mDialogMessage;
    }

    public void setmDialogMessage(String mDialogMessage) {
        this.mDialogMessage = mDialogMessage;
        if (mDialogMessage != null &&
                mDialogMessage.contains("http error code:")) {
            if (this.mContext != null) {
//                if(ZKTool.isDeviceMode(this.mContext)) {
//                    this.mDialogMessage = "请切换到联网模式才能使用";
//                } else {
                this.mDialogMessage = "当前网络异常，请检查";
//                }
            }

        }
    }

    public String getmLeftText() {
        return mLeftText;
    }

    public void setmLeftText(String mLeftText) {
        this.mLeftText = mLeftText;
    }

    public String getmRightText() {
        return mRightText;
    }

    public void setmRightText(String mRightText) {
        this.mRightText = mRightText;
    }

    public String getmSingleText() {
        return mSingleText;
    }

    public void setmSingleText(String mSingleText) {
        this.mSingleText = mSingleText;
    }

    public int getmDialogStyle() {
        return mDialogStyle;
    }

    public void setmDialogStyle(int mDialogStyle) {
        this.mDialogStyle = mDialogStyle;
    }

    @Override
    public String toString() {
        return "DialogInfo{" +
                "mDialogTitle='" + mDialogTitle + '\'' +
                ", mDialogMessage='" + mDialogMessage + '\'' +
                ", mLeftText='" + mLeftText + '\'' +
                ", mRightText='" + mRightText + '\'' +
                ", mSingleText='" + mSingleText + '\'' +
                ", mDialogStyle=" + mDialogStyle +
                '}';
    }

}
