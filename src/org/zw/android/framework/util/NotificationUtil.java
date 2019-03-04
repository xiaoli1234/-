package org.zw.android.framework.util;


import org.android.framework.R;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

//通知管理类
public class NotificationUtil {
	private static final int VERSION_NOTIFY_ID = 1101;
	
	//获取通知管理器
	public static NotificationManager GetNotificationManager(Context context){
		return (NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);
	}
	
	
	//创建一个通知
	public static Notification AddNotification(Context context,String title,String cotent,int icon,int ID,Class<?> activity){
		NotificationManager notificationmanager=(NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);
		int micon = icon; //通知图标

		CharSequence contentTitle = title; //通知栏标题

		CharSequence contentText = cotent; //通知栏内容
		long when = System.currentTimeMillis(); //通知产生的时间，会在通知信息里显示

		//用上面的属性初始化Nofification

		Notification notification = new Notification(micon,contentTitle,when);
		notification.defaults|=Notification.DEFAULT_VIBRATE; //使用默认震动方式


		Intent notificationIntent = new Intent(context,activity); //点击该通知后要跳转的Activity

		PendingIntent contentIntent = PendingIntent.getActivity(context,0,notificationIntent,0);

		notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);

		//把Notification传递给NotificationManager

		notificationmanager.notify(ID,notification);
		
		return notification;
	}
	
	//修改通知
	public static Notification UpdateNotification(Context context,Notification natification,String title,String cotent,int ID,Class<?> activity){
		NotificationManager notificationmanager=(NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);
		Intent notificationIntent = new Intent(context,activity); //点击该通知后要跳转的Activity
		CharSequence contentTitle = title; //通知栏标题

		CharSequence contentText = cotent; //通知栏内容
		PendingIntent contentIntent = PendingIntent.getActivity(context,0,notificationIntent,0);

		natification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
		notificationmanager.notify(ID, natification);
		return natification;
	}
	//移除通知
	public static void RemoveNotification(Context context,int ID){
		NotificationManager notificationmanager=(NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);
		notificationmanager.cancel(ID);
	}
	
	//移除所有通知
	public static void RemoveAllNotification(Context context){
		NotificationManager notificationmanager=(NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);
		notificationmanager.cancelAll();
	}
}
