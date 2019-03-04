package com.cjf.androidframework.upload;

import org.cjf.android.framework.app.services.IWebResponse;

public class BaseUpLoadOperation {
	//定义上传数据的地址
	protected String murl;
	//上传回调接口
	protected IWebResponse mlistener;
	
	public void SetOnRebackListener(IWebResponse listener){
		mlistener=listener;
	}
	
	public BaseUpLoadOperation(String url){
		murl=url;
	}
	

	public void start() {
		// TODO Auto-generated method stub

	}

	public void succeed() {
		// TODO Auto-generated method stub

	}

	public void fail() {
		// TODO Auto-generated method stub

	}

}
