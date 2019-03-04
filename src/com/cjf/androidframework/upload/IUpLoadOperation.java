package com.cjf.androidframework.upload;

//定义一个上传数据的接口
public interface IUpLoadOperation {
	//开始上传数据
	public void start();
	
	//上传数据成功
	public void succeed();
	
	//上传数据失败
	public void fail();
}
