package com.cjf.androidframework.filemanage;

import java.io.File;

import org.zw.android.framework.util.PathUtil;

//文件实体
public class FileModel implements Comparable {
	//文件名
	public String FileName="";
	//全名
	public String FullName="";
	//路径
	public String Path="";
	//后缀名
	public String eName="";
	//类型  文件夹或者文件 0文件夹 1文件
	public int Type=1;
	
	public String Filetype="";//ppt   html
	
	public String Title="";
	
	public FileModel(){
		
	}
	

	
	public FileModel(File file){
		FullName=file.getName();
		Path=file.getPath();
		FileName=PathUtil.getFileNameNoEx(FullName);
		eName=PathUtil.getExtensionName(FullName);
		if(file.isDirectory()){
			Type=0;
		}else{
			Type=1;
		}
	}

	@Override
	public int compareTo(Object arg0) {
		if(this.Type!=0){
			return 1;
		}
		return -1;
	}
}
