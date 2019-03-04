package test;

import org.zw.android.framework.IFrameworkFacade;

import android.app.Activity;
import android.os.Bundle;

public class BaseActivity extends Activity {

	protected IFrameworkFacade mFramework ;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		TestApplication app = (TestApplication)getApplication() ;
		
		mFramework = app.getFrameworkFacade() ;
	}
}
