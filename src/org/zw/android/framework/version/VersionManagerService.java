package org.zw.android.framework.version;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

//版本管理服务
public class VersionManagerService extends Service {
	//标志是否正在下载
	private Boolean isDownLoad=false;

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
