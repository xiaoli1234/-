package org.cjf.android.framework.app.services;

import java.io.ByteArrayInputStream;

import org.zw.android.framework.http.HttpAsyncTask;
import org.zw.android.framework.http.IObjectWrapper;
import org.zw.android.framework.util.InputStreamUtils;

import android.os.Handler;
import android.os.Message;

public class FrameworkHttpAsynTask extends HttpAsyncTask implements
		IObjectWrapper {

private Handler mhandler=null;
	
	public FrameworkHttpAsynTask(Handler handler){
//		_webresponse=webresponse;
		mhandler=handler;
		this.setObjectWrapper(this);
		
	}
	
	@Override
	public Object wrapper(Object value) {
		if(value instanceof ByteArrayInputStream){
			try {
				byte[] b=readInputStream((ByteArrayInputStream)value);
				String s=InputStreamUtils.byteTOString(b);
				 Message m= mhandler.obtainMessage();
				 
				 m.what=1001;
				 m.obj=s;
				 mhandler.sendMessage(m);
//				_webresponse.dispatchResponse(b);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				//返回一个连接错误
				Message m= mhandler.obtainMessage();
				 m.what=1001;
				 m.obj="connectException";
				 mhandler.sendMessage(m);
			}
		}else{
			Message m= mhandler.obtainMessage();
			 m.what=1001;
			 m.obj=value;
			 mhandler.sendMessage(m);
		}


		return null;
	}

}
