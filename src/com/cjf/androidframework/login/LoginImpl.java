package com.cjf.androidframework.login;

import java.util.List;

import org.cjf.android.framework.app.services.BaseProxyImpl;
import org.cjf.android.framework.app.services.IWebResponse;
import org.zw.android.framework.IExecuteAsyncTask;
import org.zw.android.framework.util.NetworkUtil;

import android.widget.Toast;


public class LoginImpl extends BaseProxyImpl {
	private String murl;

	public LoginImpl(IExecuteAsyncTask task,String url) {
		super(task);
		// TODO Auto-generated constructor stub
		murl=url;
	}
	
	//登录函数
	public void login(IWebResponse listener,UserModel lst){
		executeHttpPostTask(murl, lst, listener, UserModel.class);
	}

}


