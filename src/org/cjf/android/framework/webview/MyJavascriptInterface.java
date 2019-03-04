package org.cjf.android.framework.webview;

import org.zw.android.framework.util.StringUtils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

public class MyJavascriptInterface {

	private Context context;

	public MyJavascriptInterface(Context context) {
		this.context = context;
	}

	@JavascriptInterface
	public void openImage(String img) {
		System.out.println(img);
		//
		Intent intent = new Intent();
		intent.putExtra("image", img);
		intent.setClass(context, ShowWebImageActivity.class);
		context.startActivity(intent);
		System.out.println(img);
	}
}
