package org.cjf.android.framework.app;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.cjf.android.framework.push.IPushMsgCallBack;
import org.zw.android.framework.IFrameworkFacade;
import org.zw.android.framework.util.BitMapUtil;
import org.zw.android.framework.util.PathUtil;

import com.bg.android.framework.Listener.OnPhotoCompleteListener;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Window;
import android.widget.Toast;



//可以处理拍照的activity 为了可用性增加耦合
public class PhotoActivity extends BaseActivity {
	//图片传参数唯一id
	public String photoID;
	//保存图片文件名
	private String filePath;
	
	//图片保存成功回调监听器
	private OnPhotoCompleteListener mListener;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);	
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		  //返回值==-1表示拍摄成功
		  if (resultCode == -1) {
			  if(mListener!=null){
				  String fn=getSmartFileName();
				  //对图片进行压缩
				  BitMapUtil.SaveSmartImage(filePath, fn, 2);
				  mListener.Complete(fn, -1);
			  }

		   Toast.makeText(this, "保存成功", Toast.LENGTH_LONG).show();
		  } else {
			  if(mListener!=null){
				  mListener.Complete("", 0);
			  }
			  Toast.makeText(this, "取消拍照", Toast.LENGTH_LONG).show();
		  }

} 
	//拍照程序接口
	public void GetPhoto(OnPhotoCompleteListener listener){
		mListener=listener;
		Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
	    filePath = getFileName();
	    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(filePath)));
	    this.startActivityForResult(intent, 0);
	}
	private String getFileName() {
		  String saveDir = PathUtil.getStoragePath(PhotoActivity.this,true) + "/photo";
		  File dir = new File(saveDir);
		  if (!dir.exists()) {
		   dir.mkdir(); // 创建文件夹
		  }
		  String fileName = saveDir + "/"+UUID.randomUUID().toString()+ ".JPEG";
		  return fileName;
	}
	
	//获得压缩后的图片路径
	private String getSmartFileName(){
		  String saveDir =PathUtil.getExternalSDCardDirectory().toString() + "/SZCS/photo";
		  File dir = new File(saveDir);
		  if (!dir.exists()) {
		   dir.mkdir(); // 创建文件夹
		  }
		  String fileName = saveDir + "/"+UUID.randomUUID().toString()+ ".JPEG";
		  return fileName;
	}
	
//	public IFrameworkFacade getFrameworkFacade(){
//		return getFrameworkFacade();
//	}




}
