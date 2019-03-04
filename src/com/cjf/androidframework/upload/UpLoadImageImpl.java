package com.cjf.androidframework.upload;

import java.util.ArrayList;
import java.util.List;

import org.cjf.android.framework.app.services.BaseProxyImpl;
import org.cjf.android.framework.app.services.IWebResponse;
import org.zw.android.framework.IExecuteAsyncTask;


public class UpLoadImageImpl extends BaseProxyImpl {
	public UpLoadImageImpl(IExecuteAsyncTask task) {
		super(task);
		// TODO Auto-generated constructor stub
	}
	
	public  void UpLoadImage(String url,IWebResponse listener,String name,byte[] b){
		executeHttpPostTask(url+"?name="+name, b, listener);
	}
}
