package org.zw.android.framework.util;

import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.storage.StorageManager;

public class PathUtil {
	public static File getExternalSDCardDirectory() {
		File innerDir = Environment.getExternalStorageDirectory();
		File rootDir = innerDir.getParentFile();
		File firstExtSdCard = innerDir;
		File[] files = rootDir.listFiles();
		for (File file : files) {
			if (file.compareTo(innerDir) != 0) {
				firstExtSdCard = file;
				break;
			}
		}
		return firstExtSdCard;
	}

	public static void getAllFiles(File dir, int level) {
		level++;
		File[] files = dir.listFiles();
		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				// 这里面用了递归的算法
				getAllFiles(files[i], level);
			} else {
			}
		}
	}

	/*
	 * Java文件操作 获取文件扩展名 0 * Created on: 2011-8-2 Author: blueeagle
	 */
	public static String getExtensionName(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length() - 1))) {
				return filename.substring(dot + 1);
			}
		}
		return filename;
	}

	/*
	 * Java文件操作 获取不带扩展名的文件名
	 * 
	 * Created on: 2011-8-2 Author: blueeagle
	 */
	public static String getFileNameNoEx(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length()))) {
				return filename.substring(0, dot);
			}
		}
		return filename;
	}
	
	//获取文件所在目录
	public static String getFileFolder(String path) {
		if ((path != null) && (path.length() > 0)) {
			int dot = path.lastIndexOf('/');
			if ((dot > -1) && (dot < (path.length()))) {
				return path.substring(0, dot);
			}
		}
		return path;
	}
	
	//获取文件名
	public static String getFileName(String path) {
		if ((path != null) && (path.length() > 0)) {
			int dot = path.lastIndexOf('/');
			if ((dot > -1) && (dot < (path.length()))) {
				return path.substring(dot+1, path.length());
			}
		}
		return path;
	}
	//或者内置内存卡方法识别本机文件的方法
	public static String getStoragePath(Context mContext, boolean is_removale) { 
		 
		   StorageManager mStorageManager = (StorageManager) mContext.getSystemService(Context.STORAGE_SERVICE);
		    Class<?> storageVolumeClazz = null;
		    try {
		      storageVolumeClazz = Class.forName("android.os.storage.StorageVolume");
		      Method getVolumeList = mStorageManager.getClass().getMethod("getVolumeList");
		      Method getPath = storageVolumeClazz.getMethod("getPath");
		      Method isRemovable = storageVolumeClazz.getMethod("isRemovable");
		      Object result = getVolumeList.invoke(mStorageManager);
		      final int length = Array.getLength(result);
		      for (int i = 0; i < length; i++) {
		        Object storageVolumeElement = Array.get(result, i);
		        String path = (String) getPath.invoke(storageVolumeElement);
		        boolean removable = (Boolean) isRemovable.invoke(storageVolumeElement);
		        if (is_removale != removable) {
		           return path+"/ydqt";
		        }
		      }
		    } catch (ClassNotFoundException e) {
		      e.printStackTrace();
		    } catch (InvocationTargetException e) {
		      e.printStackTrace();
		    } catch (NoSuchMethodException e) {
		      e.printStackTrace();
		    } catch (IllegalAccessException e) {
		      e.printStackTrace();
		    }
		    return null;
		}
	// 获得外部存储卡路径，这里紧保证手头的一个机型好使，剩下的以后再说-->//获取外置内存卡路径的通用方法
	public static String GetExtSdCardPath(Context mContext, boolean is_removale) {
		StorageManager mStorageManager = (StorageManager) mContext.getSystemService(Context.STORAGE_SERVICE);
        Class<?> storageVolumeClazz = null;
        try {
            storageVolumeClazz = Class.forName("android.os.storage.StorageVolume");
            Method getVolumeList = mStorageManager.getClass().getMethod("getVolumeList");
            Method getPath = storageVolumeClazz.getMethod("getPath");
            Method isRemovable = storageVolumeClazz.getMethod("isRemovable");
            Object result = getVolumeList.invoke(mStorageManager);
            final int length = Array.getLength(result);
            for (int i = 0; i < length; i++) {
                Object storageVolumeElement = Array.get(result, i);
                String path = (String) getPath.invoke(storageVolumeElement);
                boolean removable = (Boolean) isRemovable.invoke(storageVolumeElement);
                if (is_removale == removable) {
                    return path+"/ydqt";
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
//		File innerDir = Environment.getExternalStorageDirectory();
//		
//		File file=innerDir.getParentFile();
//		file.listFiles();
//		
//		file.getParentFile().listFiles()[2].listFiles();
		//return "/storage/sdcard1/ydqt";
		//return "/storage/0000-0000/ydqt";
		//return "/storage/extSdCard/ydqt";
//		Build build=new Build();
//		String model=build.MODEL;
		//对不同型号数据进行进行兼容性处理
	//	if(model.equals("SM-T700")){
	//		return "/storage/extSdCard/ydqt";
	//	}else if(model.equals("HUAWEI M2-803L")){
	//	    return "/storage/0123-4567/ydqt";
	//    }else{
	//    	return "/storage/sdcard1/ydqt";
	//    }
	//    return "";
}
}
