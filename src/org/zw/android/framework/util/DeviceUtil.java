package org.zw.android.framework.util;

import android.content.Context;
import android.os.Build;
import android.provider.Settings.Secure;

public class DeviceUtil {
	//获取设备唯一编码
	public static String GetDeviceCode(Context context){
		return Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
	}
	
	//获取设备型号
	public static String GetDeviceType(Context context){
		Build build=new Build();
		return build.MODEL;
	}
}
