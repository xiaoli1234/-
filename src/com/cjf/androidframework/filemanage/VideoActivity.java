package com.cjf.androidframework.filemanage;

import org.android.framework.R;
import org.cjf.android.framework.app.BaseActivity;


import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.video_layout);
		String path=this.getIntent().getCharSequenceExtra("path").toString();
		Uri uri = Uri.parse(path);    
		VideoView videoView = (VideoView)this.findViewById(R.id.video_view);    
		videoView.setMediaController(new MediaController(this));    
		videoView.setVideoURI(uri);    
		videoView.start();    
		videoView.requestFocus(); 
	}

}
