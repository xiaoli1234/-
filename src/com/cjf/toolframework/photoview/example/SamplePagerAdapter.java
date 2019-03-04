package com.cjf.toolframework.photoview.example;

import com.cjf.toolframework.photoview.PhotoView;
import com.cjf.toolframework.photoview.PhotoViewAttacher.OnPhotoTapListener;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

public class SamplePagerAdapter extends PagerAdapter implements OnClickListener {
	public Activity mContext;
	public String[] mImage;
	public SamplePagerAdapter(Activity context,String[] image) {
		mContext = context;
		mImage=image;
	}


	@Override
	public int getCount() {
		return mImage.length;
	}

	@Override
	public View instantiateItem(ViewGroup container, int position) {
		try{
			PhotoView photoView = new PhotoView(container.getContext());
			photoView.setImageDrawable(new BitmapDrawable(mImage[position]));
			photoView.setOnPhotoTapListener(new OnPhotoTapListener() {
				
				@Override
				public void onPhotoTap(View view, float x, float y) {
					// TODO Auto-generated method stub
					mContext.finish();
				}
			});
			// Now just add PhotoView to ViewPager and return it
			container.addView(photoView, LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT);
			return photoView;
		}catch(Exception e){
			return null;
		}

	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		//mContext.finish();
	}

}
