package com.cjf.androidframework.upload;

import java.util.List;

import org.cjf.android.framework.app.services.IWebResponse;
import org.zw.android.framework.IFrameworkFacade;
import org.zw.android.framework.util.BitMapUtil;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;


//图片上传接口    控制上传进度条
public class ImageUpLoadOperation extends BaseUpLoadOperation implements IWebResponse{
	public int img_count;
	
	public int currentcount;
	
	public List<ImgModel> mlst;
	
	private IFrameworkFacade mframework;
	
	private Context mContext;
	
//	private IComplete listener;

	private ProgressDialog mProgressDialog;
	
	private void uploadimg(Bitmap bitmap,String name){
		if(bitmap==null ||name.equals("")){
			WebResponse("1");
			return;
		}
		
		UpLoadImageImpl impl=new UpLoadImageImpl(mframework.getAsyncExecutor());
		impl.UpLoadImage(this.murl,this, name, BitMapUtil.Bitmap2Bytes(bitmap));
	}

	@Override
	public void WebResponse(Object value) {
		if(value.toString().equals("1")){
			currentcount++;
			mProgressDialog.setProgress(currentcount);
			if(currentcount==img_count){
				//上传成功
//				if(listener!=null){
//					listener.complete(1);
				this.mlistener.WebResponse("0");
					//succeed();
//				}
			}
			else{
				//继续上传
				uploadimg(mlst.get(currentcount).getBitmap(),mlst.get(currentcount).imgName);
			}
		}else{
			//上传失败
//			if(listener!=null){
//				listener.complete(0);
//			}
			this.mlistener.WebResponse("1");
			//fail();
		}
	}
	
	public ImageUpLoadOperation(String url,List<ImgModel> lst,IFrameworkFacade framework,Context context) {
		super(url);
		// TODO Auto-generated constructor stub
//		listener=complete;
		mlst=lst;
		img_count=lst.size();
		currentcount=0;
		mframework=framework;
		mContext=context;
	}

	@Override
	public void start() {

		//开始执行上传 一个一个传  都传完就成功了
		currentcount=0;
		if(mlst==null){
			return;
		}
		if(mlst.size()==0){
			return;
		}
		
		//从第一个开始上传
		uploadimg(mlst.get(0).getBitmap(),mlst.get(0).imgName);
	}

	@Override
	public void succeed() {
		//this.mlistener.WebResponse("0");
	}

	@Override
	public void fail() {
		//this.mlistener.WebResponse("1");
	}
	
	public Dialog progress(){
		if(mProgressDialog == null){
			mProgressDialog	= new ProgressDialog(mContext);
			mProgressDialog.setCancelable(false);
			mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			mProgressDialog.setMessage("正在上传照片...");
		}
		
		if(!mProgressDialog.isShowing())
			mProgressDialog.show();
		
		mProgressDialog.setMax(mlst.size());
		mProgressDialog.setProgress(0);
		return mProgressDialog;
	}

}
