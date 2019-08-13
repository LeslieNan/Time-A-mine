package com.zkteco.android.framework.base;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.widget.BaseAdapter;

public abstract class ZkBaseAdapter<T extends View> extends BaseAdapter
{
	public Context mContext;
	public List<T> list;
	public View Q;

	public ZkBaseAdapter(Context ct, List<T> list)
	{
		super();
		this.mContext = ct;
		this.list = list;
	}

	public ZkBaseAdapter(Context ct, List<T> list, View q)
	{
		super();
		this.mContext = ct;
		this.list = list;
		Q = q;
	}

	public ZkBaseAdapter()
	{
		super();

	}

	@Override
	public int getCount()
	{
		return list.size();
	}

	@Override
	public Object getItem(int position)
	{
		return list.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}

}
