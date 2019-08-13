package com.example.timeassistant.utils;

import com.zkteco.android.framework.base.BaseApplication;
import com.zkteco.android.framework.common.utils.SharedPreferenceUtil;


public class AttenAssiSharedPreferenceUtil {

//	/* 欢迎页面是否有加载 */
	private final static String DEVICESNANDNAME = "deviceSnAndName";
	private final static String TARGETEMAIL = "TargetEmail";
	private final static String AUTHORITY = "Authority";
	private final static String ISCHECKNETWORK = "isCheckNetwork";
	private final static String ARRAYFUCTION = "arrayfuction";

	/**
	 * 设备名和序列号 格式为:设备名-序列号
	 * @param deviceSnAndName
     */
	public static void setDeviceSnAndName(String deviceSnAndName){
		SharedPreferenceUtil.setStringValue(BaseApplication.getInstance().getContext(),DEVICESNANDNAME,deviceSnAndName);
	}

	/**
	 * 获取设备名和序列号 格式为:设备名-序列号
	 * @return
     */
	public static String getDeviceSnAndName(){
		return SharedPreferenceUtil.getString(BaseApplication.getInstance().getContext(),DEVICESNANDNAME,"");
	}

	/**
	 * 保存邮箱
	 * @param email
     */
	public static void setTargetEmail(String email) {
		SharedPreferenceUtil.setStringValue(BaseApplication.getInstance().getContext(),TARGETEMAIL,email);
	}

	/**
	 * 获取邮箱
	 * @return
     */
	public static String getTargetEmail() {
		return SharedPreferenceUtil.getString(BaseApplication.getInstance().getContext(),TARGETEMAIL,"");
	}

	/**
	 * 保存权限
	 * @param hasAuthority
     */
	public static void setAuthority(boolean hasAuthority) {
		SharedPreferenceUtil.setBooleanValue(BaseApplication.getInstance().getContext(),AUTHORITY,hasAuthority);
	}
	/**
	 * 获取权限
	 */
	public static boolean getAuthority() {
		return SharedPreferenceUtil.getBoolean(BaseApplication.getInstance().getContext(),AUTHORITY,false);
	}

	/**
	 * 连接成功开始检查网络是否改变    是否需要检验网络
	 * @param isCheckNetwork
     */
	public static void setIsCheckNetwork(boolean isCheckNetwork) {
		SharedPreferenceUtil.setBooleanValue(BaseApplication.getInstance().getContext(),ISCHECKNETWORK,isCheckNetwork);
	}

	/**
	 * 获取是否需要检验网络
     */
	public static boolean getIsCheckNetwork() {
		return SharedPreferenceUtil.getBoolean(BaseApplication.getInstance().getContext(),ISCHECKNETWORK,false);
	}

	/**
	 * 设置是否部门排班或者个人排班 0部门排班 1 个人排班
	 * @param orderby
     */
	public static void setArrayFuction(int orderby){
		SharedPreferenceUtil.setIntValue(BaseApplication.getInstance().getContext(),ARRAYFUCTION,orderby);
	}

	public static int getArrayFuction(){
		return SharedPreferenceUtil.getInt(BaseApplication.getInstance().getContext(),ARRAYFUCTION,0);
	}
}
