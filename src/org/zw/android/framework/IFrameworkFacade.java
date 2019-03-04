package org.zw.android.framework;

import android.content.Context;

/**
 * Android Framework Facade
 * 
 * 1. Bitmap downloader
 * 2. Execute Async Task
 * 3. Access Database
 * 4. Location
 * 
 * @author cjf
 *
 */
public interface IFrameworkFacade {
	
	public Context getContext();

	/** Cache model */
	public IBitmapDownloader getBitmapDownloader();
	
	/** Execute Async Task model */
	public IExecuteAsyncTask getAsyncExecutor() ;
	
	/** Location model */
	public ILocationProxy getLocationProxy() ;
	
	/** SQLite Database api */
	public IAccessDatabase getAccessDatabase() ;
}
