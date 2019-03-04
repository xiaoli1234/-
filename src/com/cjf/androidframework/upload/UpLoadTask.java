package com.cjf.androidframework.upload;

import java.util.List;

import org.cjf.android.framework.app.services.IWebResponse;
import org.zw.android.framework.util.AlertDialogUtil;

import android.app.Dialog;
import android.content.Context;

public class UpLoadTask implements IWebResponse {
	//定义所有上传数据的接口
	private List<BaseUpLoadOperation> mUploadoperation;
	private Context mcontext;
	//标记当前执行上传的个数
	private int cCounnt=0;
	
	private Dialog dialog;
	
	//整个任务执行完成回调
	private IWebResponse OnCompleBack;
	public IWebResponse getOnCompleBack() {
		return OnCompleBack;
	}

	public void setOnCompleBack(IWebResponse onCompleBack) {
		OnCompleBack = onCompleBack;
	}

	public UpLoadTask(List<BaseUpLoadOperation> operation,Context context){
		mUploadoperation=operation;
		mcontext=context;
	}
	
	//数据挨个上传 全部上传完了就停止
	public void start(){
		if(mUploadoperation==null){
			return;
		}
		if(mUploadoperation.size()==0){
			return;
		}
		progress();
		//开始第一个
		mUploadoperation.get(0).SetOnRebackListener(this);
		mUploadoperation.get(0).start();

	}
	
	//控制上传进度条
	public void progress(){
		//判断是否有图片上传进度
		for(int i=0;i<mUploadoperation.size();i++){
			if(mUploadoperation.get(i) instanceof ImageUpLoadOperation){
				//添加图片的进度控制条
				ImageUpLoadOperation imgoperation=(ImageUpLoadOperation)mUploadoperation.get(i);
				dialog=imgoperation.progress();
				return;
			}
		}
		//没有图片上传的话直接就显示一个dialog  然后结束之后隐藏掉
		dialog=AlertDialogUtil.createLoadingDialog(mcontext);
		dialog.show();
		
	}

	@Override
	public void WebResponse(Object value) {
		// 上传回调接口   返回值为0为成功 1为失败
		if(value.toString().equals("0")){
			mUploadoperation.get(cCounnt).succeed();
		}else{
			mUploadoperation.get(cCounnt).fail();
		}
		cCounnt++;
		if(cCounnt<=mUploadoperation.size()-1){
			mUploadoperation.get(cCounnt).SetOnRebackListener(this);
			mUploadoperation.get(cCounnt).start();
		}else{
			//上传全部执行完毕
			if(dialog!=null){
				dialog.hide();
			}
			if(this.OnCompleBack!=null){
				this.OnCompleBack.WebResponse("1");
			}
		}
		
	}
}
