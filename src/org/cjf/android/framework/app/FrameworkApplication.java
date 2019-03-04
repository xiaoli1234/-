package org.cjf.android.framework.app;

import java.util.ArrayList;
import java.util.List;

import org.zw.android.framework.IFrameworkFacade;
import org.zw.android.framework.ILocationProxy.LocationCallback;
import org.zw.android.framework.impl.FrameworkConfig;
import org.zw.android.framework.impl.FrameworkFacade;

import com.cjf.androidframework.gps.GPSHandler;
import com.cjf.androidframework.gps.GPSThread;
import com.cjf.androidframework.gps.IGPSLinistener;

import android.app.Application;
import android.content.Context;
import android.location.Address;
import android.location.Location;
import android.widget.Toast;

public class FrameworkApplication extends Application implements LocationCallback {
	//获取外置内存全局变量
	private static Context context;
	private IFrameworkFacade framework ;
	
	//维护一个GPS定位序列   每隔一段时间定位一次 
	private Location location;
	
	private GPSThread gpsthresd;
	
	private GPSHandler gpshandler;
	
	//维护GPS位置变化监听
	private List<IGPSLinistener> mGpsLinisteners;
	
	public void clearGpsListener(){
		if(mGpsLinisteners!=null){
			mGpsLinisteners.clear();
		}
		mGpsLinisteners=null;
	}
	
	public void addOnGpsListener(IGPSLinistener listener){
		if(listener==null){
			return;
		}
		if(mGpsLinisteners==null){
			mGpsLinisteners=new ArrayList<IGPSLinistener>();
		}
		mGpsLinisteners.add(listener);
	}
	
	public void removeGpsListener(IGPSLinistener listener){
		if(listener==null){
			return;
		}
		mGpsLinisteners.remove(listener);
	}
	
	public synchronized  Location getLocation() {
		return location;
	}

	public synchronized  void setLocation(Location location) {
		this.location = location;
	}

	public void startLocation(){
		this.framework.getLocationProxy().startLocation(this);
//		if(gpsthresd!=null){
//			gpsthresd.stop();
//			gpsthresd.destroy();
//			gpsthresd=null;
//		}
//		gpshandler=new GPSHandler(this);
//		gpsthresd=new GPSThread(gpshandler, this.framework.getLocationProxy());
//		gpsthresd.start();
	}
	
	public void stopLocation(){
//		if(gpsthresd!=null){
//			gpsthresd.stop();
//			gpsthresd.destroy();
//			gpsthresd=null;
//		}
	}

	@Override
	public void onCreate() {
		super.onCreate();
		
		FrameworkConfig config = FrameworkConfig.defaultConfig(this) ;
		
		framework = FrameworkFacade.create(config);
		//获取路径全局
		context = getApplicationContext();
	}
	
	public IFrameworkFacade getFrameworkFacade(){
		return framework ;
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
	}

	@Override
	public void callback(Location location, Address address, String addText) {
		// TODO Auto-generated method stub
		this.setLocation(location);
		//Toast.makeText(this, location.getLatitude()+"+"+location.getLongitude(), Toast.LENGTH_SHORT).show();
		if(mGpsLinisteners==null){
			return;
		}
		
		//一次调用所有的监听器
		for(int i=0;i<mGpsLinisteners.size();i++){
			IGPSLinistener listener=mGpsLinisteners.get(i);
			if(listener==null){
				continue;
			}else{
				listener.OnLocation(location);
			}
		}
		
		//Toast.makeText(this, location.getLatitude()+"+"+location.getLongitude(), Toast.LENGTH_SHORT).show();
	}
	//获取全局变量
	public static Context getContext() {  
        return context;  
    }
}
