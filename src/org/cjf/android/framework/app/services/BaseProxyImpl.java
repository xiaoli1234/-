package org.cjf.android.framework.app.services;

import java.util.HashMap;
import java.util.Iterator;

import org.zw.android.framework.IExecuteAsyncTask;
import org.zw.android.framework.http.AbstractTask.MethodType;
import org.zw.android.framework.http.AbstractTask.ResultType;
import org.zw.android.framework.util.NetworkUtil;

import android.widget.Toast;

import com.alibaba.fastjson.JSON;


public class BaseProxyImpl {
	
	protected static boolean DEBUG = false;
	protected static final int CODE_OK = 1;
	protected static final int CODE_FAILED = 2;
	protected static final int CODE_TOKEN_TIMEOUT = 3;

	// �̻߳ص�handler
	// Handler handler=null;

	// �̻߳ٵ�����
	private IWebResponse webresponse = null;

	public IExecuteAsyncTask executeAsyncTask = null;

	public BaseProxyImpl(IExecuteAsyncTask task) {
		executeAsyncTask = task;

	}

	// ִ��html����
	public void executeHttpGetTask(String url, IWebResponse listener) {
		if (url == "") {
			return;
		}

		ProxyImplHandler handler = new ProxyImplHandler(listener);
		FrameworkHttpAsynTask task = new FrameworkHttpAsynTask(handler);
		task.setUrl(url);
		task.setMethodType(MethodType.GET);
		task.addProperty("Content-Type", "application/json");
		//task.addProperty("Content-Type", "charset=utf-8");
		executeAsyncTask.executeTask(task);
	}

	// ִ��html����
	public void executeHttpGetTask(String url, IWebResponse listener,
			Class<?> objclass) {
		if (url == "") {
			return;
		}
		if (listener == null || objclass == null) {
			return;
		}
		ProxyImplHandler handler = new ProxyImplHandler(listener, objclass);
		FrameworkHttpAsynTask task = new FrameworkHttpAsynTask(handler);
		task.setUrl(url);
		task.setMethodType(MethodType.GET);
		task.addProperty("Content-Type", "application/json");
		//task.addProperty("Content-Type", "application/json;charset=utf-8");
		executeAsyncTask.executeTask(task);
	}
	
	// ִ��post html���� �Ѷ���post��������
		public void executeHttpPostTask(String url, String str,
				IWebResponse listener, Class<?> objclass) {
			if (url == "") {
				return;
			}
			if (listener == null || objclass == null) {
				return;
			}
			ProxyImplHandler handler = new ProxyImplHandler(listener, objclass);
			FrameworkHttpAsynTask task = new FrameworkHttpAsynTask(handler);
			task.setUrl(url);
			if (!str.equals("")) {
				task.setPostjsonstring(str);
			}

			task.setMethodType(MethodType.POST);
			task.addProperty("Content-Type", "application/json");
			executeAsyncTask.executeTask(task);
		}


	// ִ��post html���� �Ѷ���post��������
	public void executeHttpPostTask(String url, Object objects,
			IWebResponse listener, Class<?> objclass) {
		if (url == "") {
			return;
		}
		if (listener == null ) {//|| objclass == null
			return;
		}
		ProxyImplHandler handler = new ProxyImplHandler(listener, objclass);
		FrameworkHttpAsynTask task = new FrameworkHttpAsynTask(handler);
		task.setUrl(url);
		if (objects != null) {
			task.setPostjsonstring(JSON.toJSONString(objects));
			//task.setPostjsonstring("111");
		}

		task.setMethodType(MethodType.POST);

		task.addProperty("Content-Type", "application/json");
		executeAsyncTask.executeTask(task);
	}

	// ִ��post html���� Post�ֽ�����������
	public void executeHttpPostTask(String url, byte[] b,
			IWebResponse listener, Class<?> objclass) {
		if (url == "") {
			return;
		}
		if (listener == null || objclass == null) {
			return;
		}
		ProxyImplHandler handler = new ProxyImplHandler(listener, objclass);
		FrameworkHttpAsynTask task = new FrameworkHttpAsynTask(handler);
		task.setUrl(url);
		 if(b!=null){
//			 String ss="sfaasdasdfasdfsdfsdfsadf";
//			 task.setPostByteArray(ss.getBytes());
			 //b.length;
			 task.setPostByteArray(b);
			 
			 //task.setPostjsonstring("111");
		 }
		task.setMethodType(MethodType.POST);
        //connection.setRequestProperty("Connection", "Keep-Alive");
		task.setReadTimeout(100000);
//		task.setResultType(ResultType.BYTE_ARRAY);
		task.addProperty("Content-Type", "binary/octet-stream");
		executeAsyncTask.executeTask(task);
	}
	
	public void executeHttpPostTask(String url, byte[] b,
			IWebResponse listener) {
		if (url == "") {
			return;
		}
		if (listener == null) {
			return;
		}
		ProxyImplHandler handler = new ProxyImplHandler(listener, null);
		FrameworkHttpAsynTask task = new FrameworkHttpAsynTask(handler);
		task.setUrl(url);
		 if(b!=null){
			 task.setPostByteArray(b);
		 }
		task.setMethodType(MethodType.POST);
		task.setIsKeepAlive(true);
		task.addProperty("Content-Type", "binary/octet-stream");
		executeAsyncTask.executeTask(task);
	}
	
	

	// ִ��html����
	public void executeHttpPostTask(String url,
			HashMap<String, String> parameter, IWebResponse listener,
			Class<?> objclass) {
		if (url == "") {
			return;
		}
		if (listener == null || objclass == null) {
			return;
		}
		ProxyImplHandler handler = new ProxyImplHandler(listener, objclass);
		FrameworkHttpAsynTask task = new FrameworkHttpAsynTask(handler);
		task.setUrl(url);

		if (parameter != null) {
			Iterator<String> iter = parameter.keySet().iterator();
			while (iter.hasNext()) {
				String key = (String) iter.next();
				String val = (String) parameter.get(key);
				task.addParameter(key, val);
			}
		}

		task.setMethodType(MethodType.POST);
		task.addProperty("Content-Type", "application/json");
		executeAsyncTask.executeTask(task);
	}

	// ִ��html����
	public void executeHttpPostTask(String url,
			HashMap<String, String> parameter, IWebResponse listener) {
		if (url == "") {
			return;
		}
		ProxyImplHandler handler = new ProxyImplHandler(listener);
		FrameworkHttpAsynTask task = new FrameworkHttpAsynTask(handler);
		task.setUrl(url);

		if (parameter != null) {
			Iterator<String> iter = parameter.keySet().iterator();
			while (iter.hasNext()) {
				String key = (String) iter.next();
				String val = (String) parameter.get(key);
				task.addParameter(key, val);
			}
		}

		task.setMethodType(MethodType.POST);
		task.addProperty("Content-Type", "application/json");
		executeAsyncTask.executeTask(task);
	}

	// ���post�������
	public void addParameter(String key, String val) {
	}
}
