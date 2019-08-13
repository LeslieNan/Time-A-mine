package com.zkteco.android.framework.base;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.IntegerRes;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zkteco.framework.R;

/**
 * *********************************************************************
 * 描述:com.zkteco.attendanceassistant.base.BaseFragment
 *
 * @date: 2016/4/13 15:34
 * @version: 1.0.0
 * @author: Li Yihua
 * @email: leeyh.lee@zkteco.com
 * *********************************************************************
 */
public abstract class BaseFragment extends Fragment {
    protected View contentView;
    protected Context mContext;
    private LinearLayout fragmentTopLayout;
    private TextView tvTitle;
    protected RelativeLayout rlyRight;
    protected View viewHorDiv;

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        /*setView();
        initTopLayout();
        if (isAdded())
        {
            setValue();
            setListener();
        }*/
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        mContext = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        try{
            contentView = LayoutInflater.from(getActivity()).inflate(loadLayout(),
                    null);
            setView();
            initTopLayout();
            setValue();
            setListener();
        }catch(Exception e){
            e.printStackTrace();
        }
        return contentView;
    }
    private void initTopLayout(){
        fragmentTopLayout = (LinearLayout)contentView.findViewById(R.id.fragment_top_layout);
        tvTitle  = (TextView)contentView.findViewById(R.id.tv_title);
        rlyRight = (RelativeLayout)contentView.findViewById(R.id.rly_right);
        viewHorDiv = contentView.findViewById(R.id.view_hor_div);
    }
    protected void setTextTitle(@StringRes int rightText){
        if(tvTitle!=null){
            tvTitle.setText(rightText);
        }
    }
    protected void setHorDivGone(){
        viewHorDiv.setVisibility(View.GONE);
    }
    protected void setRightVisible(int visible){
        if(rlyRight!=null){
            rlyRight.setVisibility(visible);
        }
    }
    protected void setTopLayoutTransparent(){
        fragmentTopLayout.setBackgroundResource(R.color.transparent);
    }
    protected void setTextTitleColor(@ColorRes int color){
        tvTitle.setTextColor(getResources().getColor(color));
    }

    protected abstract int  loadLayout();
    protected abstract void  setView();
    protected abstract void  setValue();
    protected abstract void  setListener();

    public String getStringSafe(int resId){
        if(isAdded() && getResources() != null){
            return getString(resId);
        }else{
            return BaseApplication.getInstance().getResources().getString(resId);
        }
    }

    public Resources getResourcesSafe(){
        if(isAdded() && getResources() != null){
            return getResources();
        }else{
            return BaseApplication.getInstance().getResources();
        }
    }
}
