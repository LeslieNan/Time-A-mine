package com.zkteco.android.framework.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zkteco.android.framework.view.dialog.ZKCustomDialogUtils;
import com.zkteco.android.framework.view.swipleback.SwipeBackActivityHelper;
import com.zkteco.android.framework.view.swipleback.SwipeBackLayout;
import com.zkteco.android.framework.view.swipleback.Utils;
import com.zkteco.framework.R;

/**
 * *********************************************************************
 * 描述:com.zkteco.android.framework.base.BaseActivity
 *
 * @date: 2016/4/12 14:15
 * @version: 1.0.0
 * @author: Li Yihua
 * @email: leeyh.lee@zkteco.com
 * *********************************************************************
 */
public abstract class BaseActivity extends AppCompatActivity {

    private SwipeBackActivityHelper mHelper;
    private RelativeLayout rlyAll;
    public TextView mTitleText;
    public TextView mReturnText;
    public TextView mRightText;
    public RelativeLayout mRightLayout;
    public ImageView mRightImg;
    public View contentView;
    public View viewDiv;
    public TextView mWarnText;
    public RelativeLayout mWarnLayout;
    protected LinearLayout topLayout;
    public boolean isShowWarnText = true;

    protected Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        mHelper = new SwipeBackActivityHelper(this);
        mHelper.onActivityCreate();
        BaseApplication.getInstance().addActivity(this);
        contentView = LayoutInflater.from(this).inflate(loadLayout(),
                null);
        setContentView(contentView);
        initTopLayout();
        setView();
        setValue();
        setListener();
    }

    private void initTopLayout() {
        topLayout = (LinearLayout)contentView.findViewById(R.id.top_layout) ;
        mWarnText = (TextView) contentView.findViewById(R.id.base_warn_text);
        mWarnLayout = (RelativeLayout) findViewById(R.id.base_warn_layout);
        mRightLayout = (RelativeLayout) findViewById(R.id.right_layout);
        mRightImg = (ImageView) findViewById(R.id.right_img);
        mRightText = (TextView) findViewById(R.id.right_text);
    }

    public void setTitleAndReturnText(View view, String titleText, String returnText) {
        mTitleText = (TextView) view.findViewById(R.id.title_text);
        mTitleText.setText(titleText);
        mReturnText = (TextView) view.findViewById(R.id.return_text);
        mReturnText.setText(returnText);
    }

    public void setDivVisible(int visibleOrGone){
        viewDiv = (View) findViewById(R.id.view_div);
        viewDiv.setVisibility(visibleOrGone);
    }

    public void setTopLayoutAndTitleColor(@ColorRes int topLayoutColor,@ColorRes int titleColor){
        setTopLayoutColor(topLayoutColor);
        setTitleColor(titleColor);
    }

    public void setTopLayoutColor(@ColorRes int color) {
        rlyAll = (RelativeLayout) findViewById(R.id.rly_all);
        rlyAll.setBackgroundColor(getResources().getColor(color));
    }
    public void setTitleColor(@ColorRes int color){
        if(mTitleText==null){
            mTitleText = (TextView) findViewById(R.id.title_text);
        }
        mTitleText.setTextColor(getResources().getColor(color));
    }

    protected void setTopLayout(int visible){
        if(topLayout!=null){
            topLayout.setVisibility(visible);
        }
    }

    public void setLeftLaoyutGone(){
        findViewById(R.id.left_layout).setVisibility(View.GONE);
    }

    public void setTitleAndReturnText(String titleText, String returnText) {
        mTitleText = (TextView) contentView.findViewById(R.id.title_text);
        mTitleText.setText(titleText);
        mReturnText = (TextView) contentView.findViewById(R.id.return_text);
        mReturnText.setTextSize(10);
        mReturnText.setText(returnText);
    }
    public void setTitleAndReturnText(@StringRes int titleText, @StringRes int returnText) {
        setTitleAndReturnText(getString(titleText), getString(returnText));
    }
    public void setReturnText(String returnText) {
        mReturnText = (TextView) contentView.findViewById(R.id.return_text);
        mReturnText.setText(returnText);
    }

    public String getReturnText() {
        if (mReturnText != null) {
            return mReturnText.getText().toString().trim();
        } else return "";
    }

    public void setRightLayout(View view, String rightText) {
        mRightLayout = (RelativeLayout) view.findViewById(R.id.right_layout);
        mRightLayout.setClickable(true);
        mRightText = (TextView) view.findViewById(R.id.right_text);
        mRightText.setText(rightText);
        mRightText.setVisibility(View.VISIBLE);
    }

    public void setRightLayout(String rightText) {
        mRightLayout = (RelativeLayout) contentView.findViewById(R.id.right_layout);
        mRightLayout.setClickable(true);
        mRightText = (TextView) contentView.findViewById(R.id.right_text);
        mRightText.setText(rightText);
        mRightText.setVisibility(View.VISIBLE);
    }
    public void setRightLayout(@StringRes int rightText) {
        setRightLayout(getString(rightText));
    }

    public void hiddenRightLayout() {
        if (mRightLayout != null) {
            mRightLayout.setClickable(false);
            mRightText.setVisibility(View.GONE);
        }
    }

    public void setRightLayoutVisibility(int visibility) {
        if (mRightLayout != null) {
            mRightLayout.setClickable(visibility == View.VISIBLE);
            mRightText.setVisibility(visibility);
            if (mRightImg != null) {
                mRightImg.setVisibility(visibility);
            }
        }
    }

    public void setRightImg(int imgId) {
        mRightImg = (ImageView) contentView.findViewById(R.id.right_img);
        mRightImg.setVisibility(View.VISIBLE);
        mRightImg.setImageResource(imgId);
    }

    public void setRightImgVisibility(int visibility){
        if(mRightImg!=null){
            mRightImg.setVisibility(visibility);
        }
    }

    public void setWarnText(String textId) {
        mWarnText.setText(textId);
        mWarnLayout.setVisibility(View.VISIBLE);
    }

    public void hideWarnText() {
        mWarnLayout.setVisibility(View.GONE);
    }

    public void setIsShowWarnText(boolean isShowWarnText) {
        this.isShowWarnText = isShowWarnText;
    }


    public void onClick(View view) {
        if (view.getId() == R.id.left_layout) {
            leftLayoutClick();
        }
    }

    public void leftLayoutClick(){
        this.finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isShowWarnText) {
           /* if (UuDeviceBusiness.isDeviceMode()*//*|| !UdkInterface.ping()*//*) {
                setWarnText(getString(R.string.prompt_when_detect_uu));
            } else {
                hideWarnText();
            }*/
        }
    }

    public boolean isShowWarnText() {
        return isShowWarnText;
    }

    public void showWarnText(boolean isUuDevice) {
        /*if (UuDeviceBusiness.isDeviceMode() && !isUuDevice) {
            setWarnText(getString(R.string.prompt_when_detect_uu));
        } else if (!UuDeviceBusiness.isDeviceMode() && isUuDevice) {
            setWarnText(getString(R.string.prompt_when_detect_non_uu));
        } else {
            hideWarnText();
        }*/
    }

    protected abstract int  loadLayout();
    protected abstract void  setView();
    protected abstract void  setValue();
    protected abstract void  setListener();

    @Override
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        onSaveInstance(bundle);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedBundle) {
        onRestoreInstance(savedBundle);
    }

    protected void onRestoreInstance(Bundle savedBundle)  {
        super.onRestoreInstanceState(savedBundle);
    };

    protected void onSaveInstance(Bundle bundle) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        ZKCustomDialogUtils.cancel();
        BaseApplication.getInstance().removeActivity(this);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mHelper.onPostCreate();
    }

    @Override
    public View findViewById(int id) {
        View v = super.findViewById(id);
        if (v == null && mHelper != null) {
            v = mHelper.findViewById(id);
        }
        return v;
    }

    public SwipeBackLayout getSwipeBackLayout() {
        return mHelper.getSwipeBackLayout();
    }

    /**
     * 描述：是否开启左滑关闭activity默认为false
     * @param enable
     */
    public void setSwipeBackEnable(boolean enable) {
        getSwipeBackLayout().setSwipeBackEnable(enable);
    }

    public void scrollToFinishActivity() {
        Utils.convertActivityToTranslucent(this);
        getSwipeBackLayout().scrollToFinishActivity();
    }

    protected <T extends View> T generateFindViewById(int id) {
        return (T) findViewById(id);
    }

    protected void openActivity(Class<?> pClass) {
        openActivity(pClass, null);
    }

    protected void openActivity(Class<?> pClass, Bundle pBundle) {
        Intent intent = new Intent(this, pClass);
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }
        startActivity(intent);
    }

}
