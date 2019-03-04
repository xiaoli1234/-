package org.cjf.android.framework.webview;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.android.framework.R;
import org.cjf.android.framework.app.BaseActivity;
import org.cjf.android.framework.view.OnImageTouchedListener;
import org.cjf.android.framework.view.ZoomableImageView;
import org.zw.android.framework.util.StringUtils;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class ShowWebImageActivity extends BaseActivity {
	private TextView imageTextView = null;
	private String imagePath = null;
	private ZoomableImageView imageView = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_webimage);

		this.imagePath = getIntent().getStringExtra("image");

		this.imageTextView = (TextView) findViewById(R.id.show_webimage_imagepath_textview);
		imageTextView.setText(this.imagePath);
		imageView = (ZoomableImageView) findViewById(R.id.show_webimage_imageview);
		imagePath = imagePath.replace("file://", "");
		BitmapDrawable bd = (BitmapDrawable) BitmapDrawable
				.createFromPath(URLDecoder.decode(imagePath));
		// BitmapDrawable
		// bd=(BitmapDrawable)this.getResources().getDrawable(R.drawable.compang);
		imageView.setImageBitmap(bd.getBitmap());
		imageView.setOnImageTouchedListener(new OnImageTouchedListener() {
			
			@Override
			public void onImageTouched() {
				// TODO Auto-generated method stub
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
