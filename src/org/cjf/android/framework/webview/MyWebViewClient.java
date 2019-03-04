package org.cjf.android.framework.webview;

import android.graphics.Bitmap;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MyWebViewClient extends WebViewClient {
	@Override  
    public boolean shouldOverrideUrlLoading(WebView view, String url) {  

        return super.shouldOverrideUrlLoading(view, url);  
    }  

    @Override  
    public void onPageFinished(WebView view, String url) {  

        view.getSettings().setJavaScriptEnabled(true);  

        super.onPageFinished(view, url);  
        // html加载完成之后，添加监听图片的点击js函数  
        addImageClickListner(view);  

    } 
    
 // 注入js函数监听  

    private void addImageClickListner(WebView v) {  
        // 这段js函数的功能就是，遍历所有的img几点，并添加onclick函数，函数的功能是在图片点击的时候调用本地java接口并传递url过去  
		v.loadUrl("javascript:(function(){" +
		"var objs = document.getElementsByTagName(\"img\"); " + 
				"for(var i=0;i<objs.length;i++)  " + 
		"{"
				+ "    objs[i].onclick=function()  " + 
		"    {  " 
				+ "        window.imagelistner.openImage(this.src);  " + 
		"    }  " + 
		"}" + 
		"})()");
    }  

    @Override  
    public void onPageStarted(WebView view, String url, Bitmap favicon) {  
        view.getSettings().setJavaScriptEnabled(true);  

        super.onPageStarted(view, url, favicon);  
    }  

    @Override  
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {  

        super.onReceivedError(view, errorCode, description, failingUrl);  

    } 
}
