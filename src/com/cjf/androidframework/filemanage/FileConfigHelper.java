package com.cjf.androidframework.filemanage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.zw.android.framework.util.PathUtil;

import android.content.Context;
import android.os.Environment;
import android.util.Xml;


//读取文件配置项
public class FileConfigHelper {
	
	private static List<FileModel> ReadXML(InputStream  xml,String filepath){
		List<FileModel> lst=new ArrayList<FileModel>();
		try {
	        XmlPullParser pullParser = Xml.newPullParser();
	        pullParser.setInput(xml, "UTF-8"); //为Pull解释器设置要解析的XML数据        
	        int event = pullParser.getEventType();
	        int count=0;
	        
	        //获得文件的父路径
	        File file=new File(filepath);
	        String path=file.getParent().toString();
	        
	        while (event != XmlPullParser.END_DOCUMENT){
	            
	            switch (event) {
	            
	            case XmlPullParser.START_DOCUMENT:
	            	lst = new ArrayList<FileModel>();                
	                break;    
	            case XmlPullParser.START_TAG:    
	                if ("File".equals(pullParser.getName())){
	                	FileModel m=new FileModel();
	                	m.FileName=pullParser.getAttributeValue(null, "FileName");
	                	m.eName=pullParser.getAttributeValue(null, "eName");
	                	m.Filetype=pullParser.getAttributeValue(null, "Filetype");
	                	m.Type=Integer.parseInt(pullParser.getAttributeValue(null, "Type"));
	                	m.Path=path+"/"+m.FileName;
	                	m.Title=pullParser.getAttributeValue(null, "title");
	                	lst.add(m);
	                }                                
	                break;
	                
	            case XmlPullParser.END_TAG:
	                break;
	                
	            }

	            event = pullParser.next();
	        }
	        return lst;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static List<FileModel> GetFileConfigModel(Context context,String config){
		InputStream xml;
		try {
			File file=new File(config);
			xml=new FileInputStream(file);
			//xml = context.getAssets().open(config);
			//xml=new 
			return ReadXML(xml,config);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}
}
