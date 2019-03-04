package com.cjf.androidframework.filemanage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.zw.android.framework.util.PathUtil;

//文件夹管理
public class FolderManage {
	//传入指定路径 获取文件信息列表
	public static List<FileModel> GetModel(String path,String ename){
		if(path==""){
			return null;
		}
		
		File file=new File(path);
		List<FileModel> lst=new ArrayList<FileModel>();
		
		if(!file.isDirectory()){
			return null;
		}
		//获取文件夹中的所有文件
		File[] files=file.listFiles();
		for (File f : files){  
            if(f.isDirectory()){  
            	if(!f.getName().equals("data")){
            		//lst.add(new FileModel(f));
            	}
                  
            }else{  
                if(ename.equals("")){
                	lst.add(new FileModel(f));
                }else{
                	String e=PathUtil.getExtensionName(f.getName());
                	String[] e_s=ename.split(" ");
                	for(int i=0;i<e_s.length;i++){
                    	if(e.equals(e_s[i])){
                    		lst.add(new FileModel(f));
                    		break;
                    	}
                	}

                }
            }  
        } 
		return lst;
	}
	
	
	
}
