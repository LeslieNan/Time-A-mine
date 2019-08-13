package com.zkteco.android.framework.liteorm;

import android.os.Environment;

import com.litesuits.orm.LiteOrm;
import com.zkteco.android.framework.base.BaseApplication;

/**
 **********************************************************************
 * 描述:
 * @date: 2015-10-23 上午10:16:02
 * @version: 1.0.0
 * @author: Li Yihua
 * @email: liyh@txtws.com
 **********************************************************************
 */
public class DBHelper {

	private static DBHelper instance;
	private static LiteOrm mLiteOrm;
	public static final String SD_CARD = Environment.getExternalStorageDirectory().getAbsolutePath();
//	private static final String DBNAME = SD_CARD+"/liteorm/zk_atten_assis.db";
	private static final String DBNAME = "zk_atten_assis.db";
	private DBHelper(){
		mLiteOrm = LiteOrm.newCascadeInstance(BaseApplication.getInstance().getContext(), DBNAME);
	}
	
	public static DBHelper getInstance(){
		if(instance == null){
			instance = new DBHelper();
		}
		return instance;
	}
	
	public LiteOrm getLiteOrm(){
		return mLiteOrm;
	}
}
