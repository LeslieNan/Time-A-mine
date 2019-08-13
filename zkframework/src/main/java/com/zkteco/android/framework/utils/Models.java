package com.zkteco.android.framework.utils;

import android.os.Build;

public class Models {
	public final static String CHE1_CL10 = "Che1-CL10";
	public final static String NEM_AL10 = "NEM-AL10";
	public static boolean isModel(String modelName) {
		return Build.MODEL.equalsIgnoreCase(modelName);
	}

}
