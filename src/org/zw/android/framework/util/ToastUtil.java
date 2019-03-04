package org.zw.android.framework.util;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {
	//	显示长信息
	public static void ShowLongToast(Context context,String text){
		Toast.makeText(context, text, Toast.LENGTH_LONG).show();
	}
	
	//	显示短信息
	public static void ShowShortToast(Context context,String text){
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}
}
