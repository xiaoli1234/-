package com.cjf.androidframework.upload;

import java.util.HashMap;

import org.cjf.android.framework.app.services.BaseProxyImpl;
import org.cjf.android.framework.app.services.IWebResponse;
import org.zw.android.framework.IExecuteAsyncTask;
import org.zw.android.framework.util.AlertDialogUtil;

import com.cjf.androidframework.login.LoginActivity;

import android.app.Dialog;
import android.content.Context;
import android.widget.Toast;
//执行前显示加载框
public class ShowDialogProxyImpl extends BaseProxyImpl {
	private Context mContext;
	
	private IWebResponse mListener;
	
	private IWebResponse nListener;
	
	private Dialog mDialog;
	
	

	public ShowDialogProxyImpl(IExecuteAsyncTask task,Context context) {
		super(task);
		// TODO Auto-generated constructor stub
		mContext=context;
		mListener=new IWebResponse() {
			
			@Override
			public void WebResponse(Object value) {
				// TODO Auto-generated method stub
				//如果返回结果为异常  则上传失败

				
				if(mDialog!=null){
					mDialog.hide();
				}
				if(value instanceof Exception){
					Toast.makeText(mContext, "上传失败！", Toast.LENGTH_LONG).show();
					return;
				}
				
				if(nListener!=null){
					nListener.WebResponse(value);
				}
			}
		};
	}

	@Override
	public void executeHttpGetTask(String url, IWebResponse listener) {
		nListener=listener;
		mDialog=AlertDialogUtil.createLoadingDialog(mContext);
		mDialog.show();
		// TODO Auto-generated method stub
		super.executeHttpGetTask(url, mListener);
	}

	@Override
	public void executeHttpGetTask(String url, IWebResponse listener,
			Class<?> objclass) {
		nListener=listener;
		mDialog=AlertDialogUtil.createLoadingDialog(mContext);
		mDialog.show();
		// TODO Auto-generated method stub
		super.executeHttpGetTask(url, mListener, objclass);
	}

	@Override
	public void executeHttpPostTask(String url, String str,
			IWebResponse listener, Class<?> objclass) {
		// TODO Auto-generated method stub
		nListener=listener;
		mDialog=AlertDialogUtil.createLoadingDialog(mContext);
		mDialog.show();
		super.executeHttpPostTask(url, str, mListener, objclass);
	}

	@Override
	public void executeHttpPostTask(String url, Object objects,
			IWebResponse listener, Class<?> objclass) {
		// TODO Auto-generated method stub
		nListener=listener;
		mDialog=AlertDialogUtil.createLoadingDialog(mContext);
		mDialog.show();
		super.executeHttpPostTask(url, objects, mListener, objclass);
	}

	@Override
	public void executeHttpPostTask(String url, byte[] b,
			IWebResponse listener, Class<?> objclass) {
		// TODO Auto-generated method stub
		nListener=listener;
		mDialog=AlertDialogUtil.createLoadingDialog(mContext);
		mDialog.show();
		super.executeHttpPostTask(url, b, mListener, objclass);
	}

	@Override
	public void executeHttpPostTask(String url, byte[] b, IWebResponse listener) {
		// TODO Auto-generated method stub
		nListener=listener;
		mDialog=AlertDialogUtil.createLoadingDialog(mContext);
		mDialog.show();
		super.executeHttpPostTask(url, b, mListener);
	}

	@Override
	public void executeHttpPostTask(String url,
			HashMap<String, String> parameter, IWebResponse listener,
			Class<?> objclass) {
		// TODO Auto-generated method stub
		nListener=listener;
		mDialog=AlertDialogUtil.createLoadingDialog(mContext);
		mDialog.show();
		super.executeHttpPostTask(url, parameter, mListener, objclass);
	}

	@Override
	public void executeHttpPostTask(String url,
			HashMap<String, String> parameter, IWebResponse listener) {
		// TODO Auto-generated method stub
		nListener=listener;
		mDialog=AlertDialogUtil.createLoadingDialog(mContext);
		mDialog.show();
		super.executeHttpPostTask(url, parameter, mListener);
	}
	
	

}
