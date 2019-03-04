package org.zw.android.framework.db.core;


/**
 * Database update listener
 * 
 * @author cjf
 *
 */
public interface SQLiteUpdateListener {

	public void updateDatabase(int oldVersion, int newVersion) ;
}
