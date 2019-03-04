package org.cjf.android.framework.push;

import java.io.IOException;

import org.android.framework.R;

import com.ibm.mqtt.IMqttClient;
import com.ibm.mqtt.MqttClient;
import com.ibm.mqtt.MqttException;
import com.ibm.mqtt.MqttPersistence;
import com.ibm.mqtt.MqttPersistenceException;
import com.ibm.mqtt.MqttSimpleCallback;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.provider.Settings.Secure;
import android.util.Log;

//本不该把本身很轻量级的框架加很多东西，因为有些东西不方便找，以后不需要的在删除吧
//基于Mqtt 的消息推送服务android  和.net服务端的对接程序
//此处只是一个实例，建议项目中复制过去修改
@SuppressLint("Wakelock") public class PushService extends Service {
	//保存单例的连接状态
	public static Boolean ISConnection=false;
	
	public static String MQTT_CLIENT_ID = "CJF";
	public static final String ACTION_START = MQTT_CLIENT_ID + ".START";
	public static final String ACTION_STOP = MQTT_CLIENT_ID + ".STOP";
	public static final String ACTION_KEEPALIVE = MQTT_CLIENT_ID
			+ ".KEEP_ALIVE";
	public static final String ACTION_RECONNECT = MQTT_CLIENT_ID
			+ ".RECONNECT";

	// 持有子线程的Handler 向子线程发送消息
	private Handler mqqtthreadHandler = null;

	// 子线程单例
	private MQTTConnectionThread mthread;

	// 建立一个新的线程处理连接和发送心跳等操作
	private Handler mhandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			// case MSG_SUCCESS:
			//
			// break;
			}
		}
	};

	// Static method to start the service
	public static void actionStart(Context ctx) {
		Intent i = new Intent(ctx, PushService.class);
		i.setAction(ACTION_START);
		ctx.startService(i);
	}
	
	public static void autoStart(Context ctx){
		Intent i = new Intent(ctx, PushService.class);
		i.setAction(ACTION_START);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
		ctx.startService(i);
	}

	// Static method to stop the service
	public static void actionStop(Context ctx) {
		Intent i = new Intent(ctx, PushService.class);
		i.setAction(ACTION_STOP);
		ctx.startService(i);
	}

	// Static method to send a keep alive message
	public static void actionPing(Context ctx) {
		Intent i = new Intent(ctx, PushService.class);
		i.setAction(ACTION_KEEPALIVE);
		ctx.startService(i);
	}

	// 向子线程发送消息函数
	private void SendMsgToThread(int type) {
		if (mthread == null) {
			mthread = MQTTConnectionThread.Get_Instance(this);

		}
		if (mqqtthreadHandler == null) {
			mqqtthreadHandler = mthread.getmConnectionThreadhandler();
		}
		switch (type) {
		case 0:// 开始链接
			mqqtthreadHandler.obtainMessage(0).sendToTarget();
			break;
		case 1:// 断开链接
			mqqtthreadHandler.obtainMessage(1).sendToTarget();
			break;
		case 2:// 发送心跳
			mqqtthreadHandler.obtainMessage(2).sendToTarget();
			break;
		case 3:// 重新连接
			mqqtthreadHandler.obtainMessage(3).sendToTarget();
			break;
		case 4:// 断开连接
			mqqtthreadHandler.obtainMessage(4).sendToTarget();
			break;
		default:
			break;
		}
	}

	@Override
	public void onCreate() {
		super.onCreate();
		// 判断是否已经启动链接 启动如果链接已经启动停止发送心跳 开启新的连接
		handleCrashedService();
	}

	private void handleCrashedService() {
		// if (wasStarted() == true) {
		// stopKeepAlives();
		//
		// start();
		// }
	}

	@Override
	public void onDestroy() {
		if (mthread != null) {
			mthread.destroy();
		}
	}

	@Override
	public void onStart(Intent intent, int startId) {
		try{
		super.onStart(intent, startId);
		if (intent.getAction().equals(ACTION_STOP) == true) {
			stop();
			stopSelf();
		} else if (intent.getAction().equals(ACTION_START) == true) {
			start();
		} else if (intent.getAction().equals(ACTION_KEEPALIVE) == true) {
			keepAlive();
		} else if (intent.getAction().equals(ACTION_RECONNECT) == true) {
			reconnectIfNecessary();
		}
		}
		catch(Exception e){
			
		}

	}

	// 服务启动 停止 心跳保持和从启方法
	private void stop() {
		if (mthread != null) {
			mthread.destroy();
		}
		// 注销网络变化监听器
		unregisterReceiver(mConnectivityChanged);
	}

	private void start() {
		PowerManager pm=(PowerManager)getSystemService(Context.POWER_SERVICE);
		WakeLock wakelock=pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,"mywakelock");
		wakelock.acquire();
		if (mthread == null) {
			mthread = MQTTConnectionThread.Get_Instance(this);
		}
		if (mthread.isAlive()) {
			return;
		}
		mthread.start();
		// 注册网络变化监听器
		registerReceiver(mConnectivityChanged, new IntentFilter(
				ConnectivityManager.CONNECTIVITY_ACTION));

	}

	private void keepAlive() {
		SendMsgToThread(2);
	}

	private void reconnectIfNecessary() {
		SendMsgToThread(3);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	// This receiver listeners for network changes and updates the MQTT
	// connection
	// accordingly
	private BroadcastReceiver mConnectivityChanged = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			NetworkInfo info = (NetworkInfo) intent
					.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
			// Is there connectivity?
			boolean hasConnectivity = (info != null && info.isConnected()) ? true
					: false;

			if (hasConnectivity) {
				reconnectIfNecessary();
			} else {
				// 发送消息过去断开链接
				SendMsgToThread(4);
			}
		}
	};
}
