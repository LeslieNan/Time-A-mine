package com.example.timeassistant.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

import com.example.timeassistant.R;

/**
 * 自定义EditText
 */
public class MyEditText extends EditText {
    public MyEditText(Context context) {
        super(context);
        init();
    }

    public MyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        setBackgroundResource(R.drawable.background_edit);
        setPadding(30,15,30,15);
        setTextSize(15);
    }
}
