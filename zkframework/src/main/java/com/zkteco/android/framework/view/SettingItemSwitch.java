package com.zkteco.android.framework.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zkteco.framework.R;


/**
 * Created by xqc on 2015/8/19.
 * ZKTeco app
 */
public class SettingItemSwitch extends LinearLayout {
    private ImageView ivItemIcon;
    private TextView tvItemText;
    private Context mContext;
    private ImageView imgSwitch;
    private LinearLayout mView;

    private boolean mState;

    public SettingItemSwitch(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        LayoutInflater.from(mContext).inflate(R.layout.view_item_setting_switch, this, true);

        ivItemIcon = (ImageView) findViewById(R.id.img_settingItem_icon);
        tvItemText = (TextView) findViewById(R.id.tv_settingItem_text);
        imgSwitch = (ImageView) findViewById(R.id.img_switch);

        initView(attrs);

        //原本用于只点快关才能切换,,,现在暂时不用
        //this原本是imgSwitch
//        this.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mState = !mState;
//                setSwitchState(mState);
//                if (mOnSwitchListener != null) {
//                    mOnSwitchListener.onSwitchChange(mState);
//                }
//            }
//        });
    }

    private void initView(AttributeSet attrs) {
        TypedArray attrArray = mContext.obtainStyledAttributes(attrs, R.styleable.SettingItem);
        int count = attrArray.getIndexCount();
        for (int i = 0; i < count; i++) {
            int attrName = attrArray.getIndex(i);
            if (attrName == R.styleable.SettingItem_settingItemIcon) {
                int resourceId = attrArray.getResourceId(R.styleable.SettingItem_settingItemIcon, -1);
                if (resourceId != -1) {
                    ivItemIcon.setImageResource(resourceId);
                }
                continue;
            }
            if (attrName == R.styleable.SettingItem_settingItemText) {
                tvItemText.setText(attrArray.getString(R.styleable.SettingItem_settingItemText));
                continue;
            }
            if (attrName == R.styleable.SettingItem_settingItemTextSize) {
                //得到的是px
                tvItemText.setTextSize(TypedValue.COMPLEX_UNIT_PX, attrArray.getDimensionPixelSize(R.styleable.SettingItem_settingItemTextSize, 0));
                continue;
            }
            if (attrName == R.styleable.SettingItem_settingItemDefaultState) {
                setSwitchState(attrArray.getBoolean(R.styleable.SettingItem_settingItemDefaultState, false));
                continue;
            }
            if (attrName == R.styleable.SettingItem_settingItemHideIcon) {
                boolean isHideIcon = attrArray.getBoolean(
                        R.styleable.SettingItem_settingItemHideIcon, false);
                if (isHideIcon) {
                    ivItemIcon.setVisibility(GONE);
                }
                continue;
            }
        }
        attrArray.recycle();
    }

    public void setText(int resId) {
        tvItemText.setText(resId);
    }

    public void setText(String string) {
        tvItemText.setText(string);
    }

    public void setIcon(int resId) {
        ivItemIcon.setImageResource(resId);
    }

    public void setSwitchState(boolean state) {
        mState = state;
        imgSwitch.setImageResource(mState ? R.drawable.icon_switch_on : R.drawable.icon_switch_off);
    }

    public boolean getSwitchState() {
        return mState;
    }

    private OnSwitchListener mOnSwitchListener;

    @Deprecated
    public void setOnSwitchListener(OnSwitchListener onSwitchListener) {
        mOnSwitchListener = onSwitchListener;
    }

    public interface OnSwitchListener {
        void onSwitchChange(boolean newState);
    }

    public void onSwitchClick() {
        setSwitchState(!mState);
    }
}
