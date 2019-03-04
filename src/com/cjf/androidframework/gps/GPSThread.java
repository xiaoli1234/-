package com.cjf.androidframework.gps;

import java.util.List;

import org.zw.android.framework.ILocationProxy;
import org.zw.android.framework.ILocationProxy.LocationCallback;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Message;

//GPS定位线程  每隔2秒定位一次
public class GPSThread extends Thread implements LocationCallback {
	
	public GPSThread(){
		
	}
//	mGeocoder 			= new Geocoder(context);
	@SuppressWarnings("deprecation")
	@Override
	public void run() {
//		while(true){
//			try{
//				List<Address> list = mGeocoder.getFromLocation(getLatitude(), getLongitude(), 1);
//				for(Address add : list){
//					mAddress = add ;
//					mAddressText = add.getAddressLine(0) + add.getAddressLine(1) +  add.getAddressLine(2);
//					break ;
//				}
//			}catch(Exception e){
//				e.printStackTrace() ;
//			}
//			//
//			mHandler.obtainMessage(1).sendToTarget() ;
//		}
	}
	@Override
	public void callback(Location location, Address address, String addText) {
		//定位返回接口
//		 Message m= mhandler.obtainMessage();
//		 
//		 m.what=1001;
//		 m.obj=location;
//		 mhandler.sendMessage(m);
	}

}
