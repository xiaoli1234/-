package org.cjf.android.framework.app.services;

import org.zw.android.framework.util.JSONUitlForNET;

import com.alibaba.fastjson.JSON;

import android.os.Handler;
import android.os.Message;

public class ProxyImplHandler extends Handler {
private IWebResponse mlistener;
	
	private Class<?> mobjclass=null;
	
	
	public ProxyImplHandler(IWebResponse listener){
		mlistener=listener;
	}

	public ProxyImplHandler(IWebResponse listener,Class<?> objclass){
		mlistener=listener;
		mobjclass=objclass;
	}
	
	@Override
	public void handleMessage(Message msg) {
		if(mlistener!=null){
			Object o1=msg.obj;
			
			//�������쳣��Ϣ����ҵ��˴���
			if(o1 instanceof Exception){
				mlistener.WebResponse(o1);
				return;
			}
			
			if(mobjclass!=null){
				
				//String str=JSONUitlForNET.ConvertUnicodeToJsonFormatL(String.valueOf(msg.obj));
				String str=String.valueOf(msg.obj);
				char first = str.charAt(0);
				
				if(first=='\"'){
					str=JSONUitlForNET.ConvertUnicodeToJsonFormatL(str);
				}
				try{
					//��������
					if(str.charAt(0)=='['){
						o1=JSON.parseArray(str, mobjclass);
					}else{
						//Object o2=JSON.parseObject(str, mobjclass);
						o1=JSON.parseObject(str, mobjclass);
					}
				}catch(Exception r){
					mlistener.WebResponse("");
					return;
				}

				

				
			}
			
			mlistener.WebResponse(o1);
		}
	}
}
