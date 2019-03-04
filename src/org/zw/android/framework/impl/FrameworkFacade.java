package org.zw.android.framework.impl;

import org.zw.android.framework.IAccessDatabase;
import org.zw.android.framework.IBitmapDownloader;
import org.zw.android.framework.IExecuteAsyncTask;
import org.zw.android.framework.IFrameworkFacade;
import org.zw.android.framework.ILocationProxy;
import org.zw.android.framework.util.NetworkUtil;

import android.content.Context;

/**
 * Android Service Common Facade
 * 
 * @author cjf
 *
 */
public final class FrameworkFacade implements IFrameworkFacade {
	
	private static FrameworkFacade _instance ;
	
	private IBitmapDownloader mDownloader ;
	private IExecuteAsyncTask mExecutor ;
	private Context 		  mContext ;
	private ILocationProxy	  mLocation ;
	private IAccessDatabase	  mDatabaseApi ;
	
	private FrameworkFacade(FrameworkConfig config){
		
		if(config == null){
			throw new RuntimeException("Framework Facade config is null");
		}
		
		mContext = config.getContext() ;
		
		if(mContext == null){
			throw new RuntimeException("FrameworkFacade Context is null");
		}
		
		mDownloader		= new BitmapDownloaderImpl(mContext,
									config.getCacheName(),
									config.getCachePercent(),
									config.getMaxWidth(),
									config.getMaxHeight());
		
		mDatabaseApi 	= new AccessDatabaseImpl(mContext);
		mExecutor		= ExecuteAsyncTaskImpl.defaultSyncExecutor(mContext)  ;
		NetworkUtil.initApplication(mContext);
	}
	
	/** Create Framework instance by Context */
	public static IFrameworkFacade create(Context context){
		return create(FrameworkConfig.defaultConfig(context)) ;
	}
	
	/** Create Framework instance by Framework Config */
	public static IFrameworkFacade create(FrameworkConfig config){
		
		if(_instance == null){
			_instance = new FrameworkFacade(config);
		}
		
		return _instance ;
	}
	
	/** get Framework instance. Note : Please call create() before this method*/
	public static IFrameworkFacade getFrameworkFacade(){
		return _instance ;
	}
	
	@Override
	public Context getContext() {
		return mContext;
	}
	
	@Override
	public IBitmapDownloader getBitmapDownloader() {
		return mDownloader ;
	}

	@Override
	public IExecuteAsyncTask getAsyncExecutor() {
		return mExecutor;
	}

	@Override
	public ILocationProxy getLocationProxy() {
		
		if(mLocation == null){
			mLocation = new LocationProxyImpl(mContext);
		}
		
		return mLocation ;
	}

	@Override
	public IAccessDatabase getAccessDatabase() {
		return mDatabaseApi;
	}

}
