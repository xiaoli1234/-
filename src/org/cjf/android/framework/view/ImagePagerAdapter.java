package org.cjf.android.framework.view;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;


public class ImagePagerAdapter extends PagerAdapter {
	private String[] image_paths;
	private List<View> mListViews;
	
	public ImagePagerAdapter(Context context,String[] imagepaths){
		mListViews=new ArrayList<View>();
		image_paths=imagepaths;
		for(int i=0;i<imagepaths.length;i++){
			ZoomableImageView view=new ZoomableImageView(context);
			BitmapDrawable bd = (BitmapDrawable) BitmapDrawable
					.createFromPath(imagepaths[i]);
			view.setImageBitmap(bd.getBitmap());
			mListViews.add(view);
		}
	}
	@Override  
    public void destroyItem(ViewGroup container, int position, Object object)   {     
        container.removeView(mListViews.get(position));  
    }  


    @Override  
    public Object instantiateItem(ViewGroup container, int position) {            
         container.addView(mListViews.get(position), 0);  
         return mListViews.get(position);  
    }  

    @Override  
    public int getCount() {           
        return  mListViews.size();  
    }  
      
    @Override  
    public boolean isViewFromObject(View arg0, Object arg1) {             
        return arg0==arg1;  
    }  

}
