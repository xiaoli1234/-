package org.zw.android.framework.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.telephony.TelephonyManager;

/**
 * NETWORK_TYPE_CDMA 网络类型为CDMA 
 * NETWORK_TYPE_EDGE 网络类型为EDGE 
 * NETWORK_TYPE_EVDO_0 网络类型为EVDO0 
 * NETWORK_TYPE_EVDO_A 网络类型为EVDOA 
 * NETWORK_TYPE_GPRS 网络类型为GPRS
 * NETWORK_TYPE_HSDPA 网络类型为HSDPA 
 * NETWORK_TYPE_HSPA 网络类型为HSPA 
 * NETWORK_TYPE_HSUPA 网络类型为HSUPA 
 * NETWORK_TYPE_UMTS 网络类型为UMTS
 * 
 * 移动和联通的2G为GPRS或EDGE，电信的2G为CDMA，
 * 
 * 联通的3G为UMTS或HSDPA，
 * 电信 的3G为EVDO
 * 
 * @author mac
 * 
 */
public final class NetworkUtil {
	
	private static Context mContext ;
	
	private NetworkUtil(){
		
	}

	public static void initApplication(Context context){
		mContext	= context ;
	}
	
	public static boolean isNetworkConnected(){
		
		if(mContext == null){
			return false ;
		}
		
		ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		return ni != null && ni.isConnectedOrConnecting();
	}
	
	public static boolean isWifi(){
		
		if(mContext == null){
			return false ;
		}
		
		ConnectivityManager connectMgr = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
		 
		NetworkInfo info = connectMgr.getActiveNetworkInfo();
		 
		if(info != null && info.getType() ==  ConnectivityManager.TYPE_WIFI){
			return true ;
		}
		
		return false ;
	}
	
	public static boolean is2G(){
		
		if(mContext == null){
			return false ;
		}
		
		ConnectivityManager connectMgr = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
		 
		NetworkInfo info = connectMgr.getActiveNetworkInfo();
		
		int type = info != null ? info.getSubtype() : -10 ;
		
		return (type == TelephonyManager.NETWORK_TYPE_GPRS) // 移动
				|| (type == TelephonyManager.NETWORK_TYPE_EDGE) // 联通
				|| (type == TelephonyManager.NETWORK_TYPE_CDMA); // 电信
	}
	
	public static boolean is3G(){
		
		if(mContext == null){
			return false ;
		}
		
		ConnectivityManager connectMgr = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
		 
		State info = connectMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
		
		String str = info != null ? info.toString() : null ;
		
		return str != null ? str.equals("") : false ;
	}
}
