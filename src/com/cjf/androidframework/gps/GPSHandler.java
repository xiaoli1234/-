package com.cjf.androidframework.gps;

import org.cjf.android.framework.app.FrameworkApplication;

import android.location.Location;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;
//GPS定位线程发送消息接口
public class GPSHandler extends Handler {
	FrameworkApplication mapp;
	
	public GPSHandler(FrameworkApplication app){
		mapp=app;
	}
	
	@Override
	public void handleMessage(Message msg) {
		Location location=(Location)msg.obj;
		mapp.setLocation(location);
		
		//测试的时候输出定位结果
		Toast.makeText(mapp, location.getLatitude()+"+"+location.getLongitude(), Toast.LENGTH_SHORT).show();
	}
	
}
