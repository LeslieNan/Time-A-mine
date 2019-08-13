package com.zkteco.android.framework.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zkteco.android.framework.utils.ZKLog;
import com.zkteco.android.framework.utils.ZKTools;
import com.zkteco.framework.R;


/**
 * Created by xqc on 2015/8/19.
 * ZKTeco app
 */
public class SettingItemArrow extends RelativeLayout {
    private static final String TAG = "SettingItemArrow ";
    private ImageView ivItemIcon;
    private TextView tvItemText;
    private TextView mDescription;
    private Context mContext;
    private RelativeLayout mView;

    public SettingItemArrow(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mView = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.view_item_setting_arrow, this, true);

        ivItemIcon = (ImageView) findViewById(R.id.img_settingItem_icon);
        tvItemText = (TextView) findViewById(R.id.tv_settingItem_text);
        mDescription = (TextView) findViewById(R.id.settingItem_description);

        initView(attrs);
        if (isInEditMode()) return;
    }

    private void initView(AttributeSet attrs) {
        TypedArray attrArray = mContext.obtainStyledAttributes(attrs, R.styleable.SettingItem);
        int count = attrArray.getIndexCount();
        for (int i = 0; i < count; i++) {
            int attrName = attrArray.getIndex(i);
            //图标
            if (attrName == R.styleable.SettingItem_settingItemIcon) {
                int resourceId = attrArray.getResourceId(R.styleable.SettingItem_settingItemIcon, -1);
                if (resourceId != -1) {
                    ivItemIcon.setImageResource(resourceId);
                }
                continue;
            }
            //项目名
            if (attrName == R.styleable.SettingItem_settingItemText) {
                tvItemText.setText(attrArray.getString(R.styleable.SettingItem_settingItemText));
                continue;
            }
            //字体大小
            if (attrName == R.styleable.SettingItem_settingItemTextSize) {
                //得到的是px
                tvItemText.setTextSize(TypedValue.COMPLEX_UNIT_PX, attrArray.getDimensionPixelSize(R.styleable.SettingItem_settingItemTextSize, 15));
                continue;
            }
            //是否隐藏icon
            if (attrName == R.styleable.SettingItem_settingItemHideIcon) {
                boolean isHideIcon = attrArray.getBoolean(
                        R.styleable.SettingItem_settingItemHideIcon, false);
                if (isHideIcon) {
                    ivItemIcon.setVisibility(GONE);
                }
                continue;
            }
            //描述
            if (attrName == R.styleable.SettingItem_settingItemDescription) {
                mDescription.setText(attrArray.getString(R.styleable.SettingItem_settingItemDescription));
                continue;
            }
            //是否隐藏箭头
            if (attrName == R.styleable.SettingItem_settingItemHideArrow) {
                boolean isHideArrow = attrArray.getBoolean(attrName, false);
                findViewById(R.id.settingItem_arrow).setVisibility(isHideArrow ? GONE : VISIBLE);
                mDescription.setPadding(0, 0, ZKTools.dip2px(mContext, 15), 0);
                continue;
            }
            //
            if (attrName == R.styleable.SettingItem_settingItemDesTextSize) {
                //得到的是px
                mDescription.setTextSize(TypedValue.COMPLEX_UNIT_PX, attrArray.getDimensionPixelSize(attrName, 15));
                continue;
            }
        }
        if (!isInEditMode()) {
            ZKLog.d(TAG, ivItemIcon.getResources() + "");
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

    public void setDescriptionColor(int resId) {
        mDescription.setTextColor(mContext.getResources().getColor(resId));
    }

    public void setDescriptionText(int resId) {
        mDescription.setText(resId);
    }

    public void setDescriptionText(String string) {
        mDescription.setText(string);
    }

    public String getDescriptionText() {
        return mDescription.getText().toString().trim();
    }

    public String getText() {
        return tvItemText.getText().toString();
    }
}
