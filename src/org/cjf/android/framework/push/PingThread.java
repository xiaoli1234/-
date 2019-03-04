package org.cjf.android.framework.push;

import org.cjf.android.framework.push.MQTTConnectionThread.ConnectionThreadhandler;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

//心跳线程
public class PingThread extends Thread {
	
	public PingThread(Handler handler){
		mqqtthreadHandler=handler;
	}
	
	// 持有Mqtt线程
	private Handler mqqtthreadHandler = null;
	
	@Override
	public void run() {

		//super.run();
		// 创建该线程的Looper对象，用于接收消息
		while(true){
			try {
				//像主线程发送消息  发送心跳
//				PowerManager pm=（PowerManager ）getSystemService(Context.POWER_SERVICE);
//				WakeLock wakelock=pm.newWakeLock(PowerManagerPARTIAL_WAKE_LOCK,"mywakelock");
//				wakelock.acquire();
				mqqtthreadHandler.obtainMessage(2).sendToTarget();
				this.sleep(20000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
