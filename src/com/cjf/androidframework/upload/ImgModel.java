package com.cjf.androidframework.upload;

import org.zw.android.framework.util.BitMapUtil;
import org.zw.android.framework.util.PathUtil;

import android.graphics.Bitmap;

//图片上传用的图片实体
public class ImgModel {
	//public Bitmap bitmap;
	
	public String imgName="";
	
	public String img_src;
	
	public ImgModel(String path){
		img_src=path;
		String filename=PathUtil.getFileName(path);
		imgName=PathUtil.getFileNameNoEx(PathUtil.getFileName(path));
		
		//bitmap=BitMapUtil.getBitmap(img_src);
	}
	
	public Bitmap getBitmap(){
		return BitMapUtil.getBitmap(img_src);
	}
}
