package org.cjf.android.framework.app;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLDecoder;

import org.android.framework.R;
import org.cjf.android.framework.app.BaseActivity;
import org.cjf.android.framework.view.OnImageTouchedListener;
import org.cjf.android.framework.view.ZoomableImageView;
import org.zw.android.framework.util.PathUtil;


import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.TextView;


public class ShowWebImageActivity extends BaseActivity {
	private TextView imageTextView = null;
	private String imagePath = null;
	private ZoomableImageView imageView = null;
	BitmapDrawable bg;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_webimage);


		this.imageTextView = (TextView) findViewById(R.id.show_webimage_imagepath_textview);
		imageTextView.setText(this.imagePath);
		imageView = (ZoomableImageView) findViewById(R.id.show_webimage_imageview);
		//imagePath = imagePath.replace("file://", "");
		imagePath=this.getIntent().getStringExtra("path");
		 bg= (BitmapDrawable) BitmapDrawable
				.createFromPath(URLDecoder.decode(imagePath));
		// BitmapDrawable
		// bd=(BitmapDrawable)this.getResources().getDrawable(R.drawable.compang);
		imageView.setImageBitmap(bg.getBitmap());
		imageView.setOnImageTouchedListener(new OnImageTouchedListener() {
			
			@Override
			public void onImageTouched() {
				// TODO Auto-generated method stub
				bg.getBitmap().recycle();
				finish();
			}
		});
	}

	public static Drawable loadImageFromUrl(String url) throws IOException {

		URL m = new URL(url);
		InputStream i = (InputStream) m.getContent();
		Drawable d = Drawable.createFromStream(i, "src");
		return d;
	}
}
