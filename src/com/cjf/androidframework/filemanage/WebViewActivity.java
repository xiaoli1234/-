package com.cjf.androidframework.filemanage;

import org.android.framework.R;
import org.cjf.android.framework.app.BaseActivity;
import org.zw.android.framework.util.PathUtil;


import android.net.http.SslError;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class WebViewActivity extends BaseActivity {
	WebView wv = null;
	String path;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.webview_layout);
		wv=(WebView)findViewById(R.id.wv_view);
		// 启用javascript
		wv.getSettings().setJavaScriptEnabled(true);
		wv.setSaveEnabled(false);
		//wv.getSettings().setDefaultTextEncodingName("gb2312");
		wv.getSettings().setSupportZoom(true);
		wv.getSettings().setBuiltInZoomControls(true);
		wv.getSettings().setDefaultTextEncodingName("UTF-8");
		wv.getSettings().setBlockNetworkImage(false);
		//获取打开文件路径
		//path="file://"+this.getIntent().getCharSequenceExtra("path").toString();
		path=this.getIntent().getCharSequenceExtra("path").toString();
		//获得文件类型是否为ppt
		CharSequence type=this.getIntent().getCharSequenceExtra("type");
		if(type==null){
			String html=HtmlHelper.ReadHtml(path);
			String folderpath=PathUtil.getFileFolder(path);
			
			wv.loadDataWithBaseURL(folderpath,html,
					 "text/html","UTF-8",null);
			return;
		}
		if(type.equals("ppt")){
			path="file://"+this.getIntent().getCharSequenceExtra("path").toString();
			wv.loadUrl(path);
		}else{
			String html=HtmlHelper.ReadHtml(path);
			String folderpath=PathUtil.getFileFolder(path);
			
			wv.loadDataWithBaseURL(folderpath,html,
					 "text/html","UTF-8",null);
		}
//		wv.setWebViewClient(new WebViewClient(){
//
//			@Override
//			public void onReceivedSslError(WebView view,
//					SslErrorHandler handler, SslError error) {
//				// TODO Auto-generated method stub
//				//super.onReceivedSslError(view, handler, error);
//				//handler.proceed();  
//				Toast.makeText(WebViewActivity.this, "加载失败，重新加载", Toast.LENGTH_SHORT).show();
//			}
//
//			@Override
//			public boolean shouldOverrideUrlLoading(WebView view, String url) {
//				// TODO Auto-generated method stub
//				view.loadUrl(url);
//				return true;
//			}
//
//			@Override
//			public void onPageFinished(WebView view, String url) {
//				// TODO Auto-generated method stub
//				//super.onPageFinished(view, url);
//				//wv.setVisibility(View.VISIBLE);
//				Toast.makeText(WebViewActivity.this, "加载完成", Toast.LENGTH_SHORT).show();
//			}
//
//			@Override
//			public void onReceivedError(WebView view, int errorCode,
//					String description, String failingUrl) {
//				// TODO Auto-generated method stub
//				super.onReceivedError(view, errorCode, description, failingUrl);
//				wv.loadUrl(path);
//				Toast.makeText(WebViewActivity.this, "加载失败，重新加载", Toast.LENGTH_SHORT).show();
//			}
//			
//		});
//		//String path=this.getIntent().getCharSequenceExtra("path").toString();
		//wv.loadUrl(path);
		//wv.setVisibility(View.INVISIBLE);
//		 runOnUiThread(new Runnable() {
//
//			 @Override
//			 public void run() {
//			 // TODO Auto-generated method stub
//				 wv.loadUrl(path);
//
//
//			 }
//		 });
		//从本地读取html文件
		

//		wv.loadDataWithBaseURL(path, data, mimeType, encoding, historyUrl)
		//wv.loadData(PathUtil.GetExtSdCardPath()+"/ppt/幻灯片 1/幻灯片 1.html", "text/html;charset=UTF-8", null);
	}
}
