package org.cjf.android.framework.app;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.Toast;

//定义点击两下返回按钮退出程序类
public class ReBackActivity extends PhotoActivity {
	private long exitTime = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);		
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			ExitApp();
			return true;
		} else {
			return super.onKeyDown(keyCode, event);
		}

	}

    public void ExitApp()
    {
            if ((System.currentTimeMillis() - exitTime) > 2000 || exitTime==0)
            {
                    Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                    exitTime = System.currentTimeMillis();
            } else
            {
                    this.finish();
            }

    }
}
