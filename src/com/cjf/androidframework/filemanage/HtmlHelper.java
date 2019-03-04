package com.cjf.androidframework.filemanage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class HtmlHelper {
	//读取本地html到文本
	public static String ReadHtml(String path){
		if(path==null || path==""){
			return "";
		}

		try {
			File f = new File(path);
			InputStream in = new FileInputStream(f);
	        byte[] data;
			try {
				data = readInputStream(in);
		       //String html1 = new String(data, "gb2312");  
				String html = new String(data, "UTF-8"); 
		        return html;  
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//得到html的二进制数据  

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
    public static byte[] readInputStream(InputStream inStream) throws Exception{  
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();  
        byte[] buffer = new byte[1024];  
        int len = 0;  
        while( (len=inStream.read(buffer)) != -1 ){  
            outStream.write(buffer, 0, len);  
        }  
        inStream.close();  
        return outStream.toByteArray();  
    } 
}
