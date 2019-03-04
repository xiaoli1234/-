package org.zw.android.framework.util;

import android.annotation.SuppressLint;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressLint("SimpleDateFormat")
public final class StringUtils {

	// Email正则表达式
	public static final String EMAIL_PATTERN 				= "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
	// 手机号正则表达式
	public static final String PHONE_PATTERN 				= "^(\\+?\\d+)?1[3456789]\\d{9}$";
	// 密码格式验证
	public static final String PASSWORD_PATTERN				= "^[a-zA-Z0-9_]{6,20}$" ;
	// 特殊字符串
	public static final String SPECIAL_STRING_PATTERN		= "[`~!@#$%^&*()+=|{}':;'\",\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";

	private StringUtils() {
	}

	public static boolean isEmpty(String source) {

		if (source == null || "".equals(source))
			return true;

		for (int i = 0; i < source.length(); i++) {
			char c = source.charAt(i);
			if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
				return false;
			}
		}
		return true;
	}

	public static boolean matches(String source, String pattern) {

		if (source == null || !source.matches(pattern)) {
			return false;
		}
		
		return true;
	}
	
	public static boolean isSpecialString(String source){
		return matches(source, SPECIAL_STRING_PATTERN);
	}

	public static boolean isEmail(String source) {
		return matches(source, EMAIL_PATTERN);
	}

	public static boolean isPhone(String source) {
		return matches(source, PHONE_PATTERN);
	}
	
	public static boolean isPassword(String source){
		return matches(source, PASSWORD_PATTERN);
	}
	
	public static boolean isHasEmptyChar(String source){
		
		if(source == null){
			return true ;
		}
		
		return source.trim().indexOf(' ') >= 0 ;
	}

	public static String toNotNullString(String source) {
		return source == null ? "" : source;
	}
	
	public static String arrayToString(String[] array, String separator) {

		if (array == null || array.length == 0) {
			return "";
		}
		StringBuilder result = new StringBuilder();
		for (String temp : array) {
			result.append(temp).append(separator);
		}
		return result.substring(0, result.length() - 2);
	}

	public static String[] toArray(String source) {

		if (isEmpty(source)) {
			return null;
		}
		return source.split(",");
	}

	public static String capitalize(String str) {

		if (str == null || str.length() == 0) {
			return str;
		}
		StringBuilder sb = new StringBuilder(str.length());
		sb.append(Character.toUpperCase(str.charAt(0)));
		sb.append(str.substring(1));
		return sb.toString();
	}

	public static String getTimeName() {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		return dateFormat.format(date);
	}

	public static boolean larger(String source, int length) {
		return (source != null && source.length() > length);
	}

	public static String toString(Object source) {
		return (source == null ? null : source.toString());
	}
	
	//获取路径字符串中的文件名
	public static String GetFileName(String path){
		String name="";
		String[] fileName = path.split("/");
		if(fileName!=null && fileName.length>0){
			name=fileName[fileName.length-1];
		}
		return name;
	}
	
	public static String string2Unicode(String s) {
	    try {
	      StringBuffer out = new StringBuffer("");
	      byte[] bytes = s.getBytes("unicode");
	      for (int i = 2; i < bytes.length - 1; i += 2) {
	        out.append("u");
	        String str = Integer.toHexString(bytes[i + 1] & 0xff);
	        for (int j = str.length(); j < 2; j++) {
	          out.append("0");
	        }
	        String str1 = Integer.toHexString(bytes[i] & 0xff);

	        out.append(str);
	        out.append(str1);
	        out.append(" ");
	      }
	      return out.toString().toUpperCase();
	    }
	    catch (UnsupportedEncodingException e) {
	      e.printStackTrace();
	      return null;
	    }
	  } 

	 

	public static String unicode2String(String unicodeStr){
	    StringBuffer sb = new StringBuffer();
	    String str[] = unicodeStr.toUpperCase().split("U");
	    for(int i=0;i<str.length;i++){
	      if(str[i].equals("")) continue;
	      char c = (char)Integer.parseInt(str[i].trim(),16);
	      sb.append(c);
	    }
	    return sb.toString();
	  }

}
