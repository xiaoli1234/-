package org.cjf.android.framework.app;

import org.cjf.android.framework.push.PushService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

//开机启动push服务
public class BootBroadcastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context arg0, Intent arg1) {
		// TODO Auto-generated method stub
		Log.d("DEBUG", "开机自动服务自动启动...");
		//Toast.makeText(arg0, "启动成功了！", Toast.LENGTH_LONG).show();
		PushService.autoStart(arg0);
	}

}
