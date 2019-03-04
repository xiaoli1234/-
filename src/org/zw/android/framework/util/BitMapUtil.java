package org.zw.android.framework.util;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.R.bool;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;

public class BitMapUtil {
	// 保存图片缩略图
	public static void SaveSmartImage(String path, String savepath, int scale) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = scale;
		options.inJustDecodeBounds = false;
		// Bitmap bitmap =BitmapFactory.decodeFile(path);
		// 通过这个bitmap获取图片的宽和高
		Bitmap bitmap = BitmapFactory.decodeFile(path, options);
		try {
			BitMapUtil.saveFile(bitmap, savepath);
			// BitMapUtil.saveFile(bitmap, savepath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void saveFile(Bitmap bmp, String path) throws IOException {
		File myCaptureFile = new File(path);
		if (!myCaptureFile.exists()) {
			boolean b = myCaptureFile.createNewFile();
			if (!b) {
				return;
			}
		}

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bmp.compress(Bitmap.CompressFormat.JPEG, 60, baos);
		try {
			FileOutputStream fos = new FileOutputStream(myCaptureFile);
			fos.write(baos.toByteArray());
			fos.flush();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// 根据路径获得bitmap
	public static Bitmap getBitmap(String path){
		if(path.equals("")){
			return null;
		}
		return BitmapFactory.decodeFile(path);
	}
	public static byte[] Bitmap2Bytes(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		//bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
		bm.compress(Bitmap.CompressFormat.JPEG, 60, baos);
		return baos.toByteArray();
	}

	public static Bitmap Bytes2Bimap(byte[] b) {
		if (b.length != 0) {
			return BitmapFactory.decodeByteArray(b, 0, b.length);
		} else {
			return null;
		}
	}
}
