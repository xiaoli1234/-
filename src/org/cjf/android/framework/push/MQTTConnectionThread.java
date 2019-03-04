package org.cjf.android.framework.push;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.android.framework.R;
import org.json.JSONException;
import org.json.JSONObject;
import org.zw.android.framework.util.Debug;

import com.ibm.mqtt.MqttException;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

//推送长连接线程
public class MQTTConnectionThread extends Thread implements IPushMsgCallBack {

	// 线程单例模式
	private static MQTTConnectionThread _instance;

	public static final String TAG = "PushService";
	public static final String PREF_DEVICE_ID = "deviceID";

	// 服务端地址 回头改成参数传过来
	private static final String MQTT_HOST = "202.114.112.14";

	// 接收消息handler
	private ConnectionThreadhandler mConnectionThreadhandler = null;

	// Mqqt链接对象
	private MQTTConnection mConnection;

	private Context mcontext = null;

	private SharedPreferences mPrefs;

	// 标记链接是否启动
	private boolean mStarted = false;

	private ConnectivityManager mConnMan;

	// 链接开始时间
	private long mStartTime;

	public static final String PREF_RETRY = "retryInterval";
	private static final long INITIAL_RETRY_INTERVAL = 1000 * 50;
	private static final long MAXIMUM_RETRY_INTERVAL = 1000 * 60 * 30;
	public static final String PREF_STARTED = "isStarted";
	//private static final long KEEP_ALIVE_INTERVAL = 1000 * 60 * 28;
	private static final long KEEP_ALIVE_INTERVAL = 1000 * 20;
	
	//持有心跳线程
	//private PingThread pthread;
	
	private MQTTConnectionThread(Context context) {
		mcontext = context;
		mPrefs = mcontext.getSharedPreferences(TAG, mcontext.MODE_PRIVATE);

		mConnMan = (ConnectivityManager) mcontext
				.getSystemService(mcontext.CONNECTIVITY_SERVICE);
		mStartTime = System.currentTimeMillis();

	}

	public static MQTTConnectionThread Get_Instance(Context context) {
		if (_instance == null) {
			_instance = new MQTTConnectionThread(context);
		}
		return _instance;
	}

	@Override
	public void run() {

		super.run();
		// 创建该线程的Looper对象，用于接收消息
		Looper.prepare();
		// 启动推送链接
		// startMqqtConnect();
		// 线程的looper创建的handler表示消息接收者是子线程
		mConnectionThreadhandler = new ConnectionThreadhandler(
				Looper.myLooper());
		// 循环从MessageQueue中取消息。
		startMqqtConnect();

		Looper.loop();

	}

	private synchronized void startMqqtConnect() {
		Debug.d("Push", "Starting service...");

		// Do nothing, if the service is already running.
		if (mStarted == true) {
			Log.w(TAG, "Attempt to start connection that is already active");
			
			return;
		}

		// Establish an MQTT connection
		connect();
	}

	class ConnectionThreadhandler extends Handler {
		public ConnectionThreadhandler(Looper looper) {
			super(looper);

		}

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:// 开始链接
				Log.w(TAG, "Starting service...");
				if (mStarted == true) {
					Log.w(TAG,
							"Attempt to start connection that is already active");
					return;
				}
				connect();
				break;
			case 1:// 断开链接
				if (mStarted == false) {
					Log.w(TAG, "Attempt to stop connection not active.");
					return;
				}
				//setStarted(false);
				mConnection.disconnect();
				mConnection = null;
				cancelReconnect();
				break;
			case 2:// 发送心跳
				keepAlive();
				break;
			case 3:// 重新连接
				reconnectIfNecessary();
				break;
			case 4:// 断开连接
				try {
					//setStarted(false);
					mConnection.disconnect();
					mConnection = null;
					cancelReconnect();
				} catch (Exception e) {

				}
				break;
			default:
				break;
			}
		}
	}

	// 重新连接网络
	private synchronized void reconnectIfNecessary() {
		if (mStarted == true && mConnection == null) {
			// log("Reconnecting...");
			connect();
		}
	}

	// 连接网络
	private synchronized void connect() {
		// log("Connecting...");
		// fetch the device ID from the preferences.
		String deviceID = mPrefs.getString(PREF_DEVICE_ID, null);
		// Create a new connection only if the device id is not NULL
		if (deviceID == null) {
			// log("Device ID not found.");
		} else {
			try {
				mConnection = new MQTTConnection(MQTT_HOST, deviceID, mcontext,
						this);
				mStartTime = System.currentTimeMillis();
				startKeepAlives();
				PushService.ISConnection=true;
			} catch (MqttException e) {
				// Schedule a reconnect, if we failed to connect
				Debug.d("Pust",
						"MqttException: "
								+ (e.getMessage() != null ? e.getMessage()
										: "NULL"));
				if (isNetworkAvailable()) {
					mStartTime = System.currentTimeMillis();
					scheduleReconnect(mStartTime);
				}
			}
			setStarted(true);
		}
	}

	// 连接失败 再次连接
	public void scheduleReconnect(long startTime) {
		// the last keep-alive interval
		long interval = mPrefs.getLong(PREF_RETRY, INITIAL_RETRY_INTERVAL);

		// Calculate the elapsed time since the start
		long now = System.currentTimeMillis();
		long elapsed = now - startTime;

		// Set an appropriate interval based on the elapsed time since start
		if (elapsed < interval) {
			interval = Math.min(interval * 4, MAXIMUM_RETRY_INTERVAL);
		} else {
			interval = INITIAL_RETRY_INTERVAL;
		}

		Debug.e("Push", "Rescheduling connection in " + interval + "ms.");

		// Save the new internval
		mPrefs.edit().putLong(PREF_RETRY, interval).commit();
		interval=INITIAL_RETRY_INTERVAL;
		// Schedule a reconnect using the alarm manager.
		Intent i = new Intent();
		i.setClass(mcontext, PushService.class);
		i.setAction(PushService.ACTION_RECONNECT);
		PendingIntent pi = PendingIntent.getService(mcontext, 0, i, 0);
		AlarmManager alarmMgr = (AlarmManager) mcontext
				.getSystemService(mcontext.ALARM_SERVICE);
		alarmMgr.set(AlarmManager.RTC_WAKEUP, now + interval, pi);
	}



	// Sets whether or not the services has been started in the preferences.
	public void setStarted(boolean started) {
		mPrefs.edit().putBoolean(PREF_STARTED, started).commit();
		mStarted = started;
	}

	private boolean isNetworkAvailable() {
		NetworkInfo info = mConnMan.getActiveNetworkInfo();
		if (info == null) {
			return false;
		}
		return info.isConnected();
	}

	// 开始发送心跳 保持连接
	private void startKeepAlives() {
		Intent i = new Intent();
		i.setClass(mcontext, PushService.class);
		i.setAction(PushService.ACTION_KEEPALIVE);
		PendingIntent pi = PendingIntent.getService(mcontext, 0, i, 0);
		AlarmManager alarmMgr = (AlarmManager) mcontext
				.getSystemService(mcontext.ALARM_SERVICE);
		alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP,
				System.currentTimeMillis() + KEEP_ALIVE_INTERVAL,
				KEEP_ALIVE_INTERVAL, pi);		
	}

	// 停止发送心跳保持连接
	private void stopKeepAlives() {
		Intent i = new Intent();
		i.setClass(mcontext, PushService.class);
		i.setAction(PushService.ACTION_KEEPALIVE);
		PendingIntent pi = PendingIntent.getService(mcontext, 0, i, 0);
		AlarmManager alarmMgr = (AlarmManager) mcontext
				.getSystemService(mcontext.ALARM_SERVICE);
		alarmMgr.cancel(pi);
//		if(pthread!=null){
//			pthread.stop();
//			pthread=null;
//		}
	}

	// 保持心跳连接的方法
	private synchronized void keepAlive() {
		try {
			// Send a keep alive, if there is a connection.
			if (mStarted == true && mConnection != null) {
				mConnection.sendKeepAlive();
			}
		} catch (MqttException e) {
			Debug.e("Push",
					"MqttException: "
							+ (e.getMessage() != null ? e.getMessage() : "NULL"));
			//setStarted(false);
			mConnection.disconnect();
			mConnection = null;
			cancelReconnect();
		}
	}

	// 取消连接
	public void cancelReconnect() {
		Intent i = new Intent();
		i.setClass(mcontext, PushService.class);
		i.setAction(PushService.ACTION_RECONNECT);
		PendingIntent pi = PendingIntent.getService(mcontext, 0, i, 0);
		AlarmManager alarmMgr = (AlarmManager) mcontext
				.getSystemService(mcontext.ALARM_SERVICE);
		alarmMgr.cancel(pi);
		
//		mConnection.disconnect();
//		mConnection = null;
//		if(pthread!=null){
//			pthread.stop();
//			pthread=null;
//		}
	}

	// 将线程的handler返回到其他线程传递消息
	public ConnectionThreadhandler getmConnectionThreadhandler() {
		return mConnectionThreadhandler;
	}

	public void setmConnectionThreadhandler(
			ConnectionThreadhandler mConnectionThreadhandler) {
		this.mConnectionThreadhandler = mConnectionThreadhandler;
	}

	@Override
	public void publishMsg(byte[] msg) {
		// TODO Auto-generated method stub
		// 先添加个通知
		try {
			String s = new String(msg,"UTF-8");
			JSONObject jo = new JSONObject(s);
			String title = jo.getString("title");
			String m = jo.getString("msg");
			showNotification(title, m);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void showNotification(String title, String msg) {
		Notification n = new Notification();

		n.flags |= Notification.FLAG_SHOW_LIGHTS;
		n.flags |= Notification.FLAG_AUTO_CANCEL;

		n.defaults = Notification.DEFAULT_ALL;

		n.icon = R.drawable.notification;
		n.when = System.currentTimeMillis();

		// Simply open the parent activity
		// PendingIntent pi = PendingIntent.getActivity(this, 0,
		// new Intent(this, PushActivity.class), 0);

		// Change the name of the notification here
		n.setLatestEventInfo(mcontext, title, msg, null);
		NotificationManager mNotifMan;
		mNotifMan = (NotificationManager) mcontext
				.getSystemService(mcontext.NOTIFICATION_SERVICE);
		mNotifMan.notify(0, n);
	}

	@Override
	public void connectionLost() {
		 stopKeepAlives();
		 PushService.ISConnection=false;
		// // null itself
		//setStarted(false);
		mConnection = null;
		if (isNetworkAvailable() == true) {
			//reconnectIfNecessary();
		}
	}

	@Override
	public void disconnect() {
		// TODO Auto-generated method stub
		stopKeepAlives();
	}
}
