package org.cjf.android.framework.app;

import org.zw.android.framework.IFrameworkFacade;


import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class BaseActivity extends Activity {
	public IFrameworkFacade mFramework ;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		FrameworkApplication app = (FrameworkApplication)getApplication() ;
		
		mFramework = app.getFrameworkFacade() ;
		
	}
}
