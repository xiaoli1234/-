package com.cjf.androidframework.filemanage;

import java.util.List;

import android.content.Context;



public class FileHistoryModel {
	//保存文件夹路径
	public String Path;
	
	//保存父文件夹
	public FileHistoryModel Parent;
	
	public List<FileModel> files;
	public FileHistoryModel(String path,FileHistoryModel parent){
		Path=path;
		Parent=parent;
	}
	
	public FileHistoryModel(Context context,String path,FileHistoryModel parent){
		Path=path;
		Parent=parent;
		files=FileConfigHelper.GetFileConfigModel(context, path+"/Fileconfig.xml");
	}
	
	public static List<FileModel> GetFileModel(Context context,String path){
		return FileConfigHelper.GetFileConfigModel(context, path+"/Fileconfig.xml");
	}
	
	
}
