package com.zkteco.android.framework.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import java.util.List;

/**
 * 启动activity 工具类
 * @author Administrator
 *
 */
public class StartActivityUtil
{

	/**
	 * 启动actvity
	 * @param mContext
	 * @param className  启动的类名
	 */
	public  static  void startActivityByClassName(Context mContext, String className)
	{
		try {
			Intent intent =new Intent();
			intent.setClass(mContext, Class.forName(className));
			mContext.startActivity(intent);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 启动actvity
	 * @param mContext
	 * @param className  启动的类名
	 */
	public  static  void startActivity(Context mContext,Class className)
	{
		Intent intent =new Intent();
		intent.setClass(mContext,className);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		mContext.startActivity(intent);
	}
	/**
	 * 启动actvity
	 * @param mContext
	 */
	public static void startActivity(Context mContext,String packageName,String className)
	{
		try
		{
			ComponentName com = new ComponentName(packageName, className);
			Intent  intent = new Intent();
			intent.setComponent(com);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			mContext.startActivity(intent);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 通过包名启动应用
	 * @param context
	 * @param packagename
	 */
	public static void startActivity(Context context, String packagename)
	{
		try
		{
			PackageManager packageManager = context.getPackageManager();
			Intent intent = packageManager.getLaunchIntentForPackage(packagename);
			context.startActivity(intent);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public static void startActivityByPackageName(Context context, String packagename)
	{
		try
		{
			Intent intent =new Intent();
			intent.setPackage(packagename);
			context.startActivity(intent);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	/**
	 * 通过INTENT启动
	 * @param context
	 * @param intent
	 */
	public static void startActivity(Context context, Intent intent){
		try{
			context.startActivity(intent);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public static void sendBroadcast(Context context,String action)
	{
		Intent mIntent = new Intent(action);
		context.sendBroadcast(mIntent);
	}

	/**
	 * 检测该包名所对应的应用是否存在
	 * @param packageName
	 * @return
	 */
	public static boolean checkPackage(Context mContext,String packageName)
	{
		if (packageName == null || "".equals(packageName))
			return false;
		try
		{
			mContext.getPackageManager().getApplicationInfo(packageName, 0);
			return true;
		}
		catch (PackageManager.NameNotFoundException e)
		{
			return false;
		}
	}

	public static void doStartApplicationWithPackageName(Context mContext,String packagename) {

		// 通过包名获取此APP详细信息，包括Activities、services、versioncode、name等等
		PackageInfo packageinfo = null;
		try {
			packageinfo = mContext.getPackageManager().getPackageInfo(packagename, 0);
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		if (packageinfo == null) {
			return;
		}

		// 创建一个类别为CATEGORY_LAUNCHER的该包名的Intent
		Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
		resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		resolveIntent.setPackage(packageinfo.packageName);

		// 通过getPackageManager()的queryIntentActivities方法遍历
		List<ResolveInfo> resolveinfoList = mContext.getPackageManager()
				.queryIntentActivities(resolveIntent, 0);

		ResolveInfo resolveinfo = resolveinfoList.iterator().next();
		if (resolveinfo != null) {
			// packagename = 参数packname
			String packageName = resolveinfo.activityInfo.packageName;
			// 这个就是我们要找的该APP的LAUNCHER的Activity[组织形式：packagename.mainActivityname]
			String className = resolveinfo.activityInfo.name;
			// LAUNCHER Intent
			Intent intent = new Intent(Intent.ACTION_MAIN);
			intent.addCategory(Intent.CATEGORY_LAUNCHER);

			// 设置ComponentName参数1:packagename参数2:MainActivity路径
			ComponentName cn = new ComponentName(packageName, className);

			intent.setComponent(cn);
			mContext.startActivity(intent);
		}
	}

	public static boolean isIntentAvailable(Context context,Intent intent) {
		List<ResolveInfo> list = getIntentList(context,intent);
		if(list == null){
			return false;
		}
		return list.size() > 0;
	}

	public static List<ResolveInfo> getIntentList(Context context,Intent intent){
		final PackageManager packageManager = context.getPackageManager();
		return packageManager.queryIntentActivities(intent,0);
	}


}
