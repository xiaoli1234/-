package com.cjf.androidframework.filemanage;

import java.util.Collections;
import java.util.List;

import org.android.framework.R;
import org.cjf.android.framework.app.BaseActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;


//简单的文件管理器，不包含父级节点 通过读取配置文件处理
public class SimpleFileManageActivity extends BaseActivity {
	ListView lit_com_res;
	List<FileModel> lst;
	//文件夹上下级记录
	FileHistoryModel history;
	//保存扩展名
	String extensionName;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.file_manage_layout);
		//获得访问文件夹路径
		Intent intent=this.getIntent();
		String path=intent.getCharSequenceExtra("path").toString();
		history=new FileHistoryModel(this,path, null);
		lit_com_res = (ListView) findViewById(R.id.lit_com_res);
		refreshLst(history);
		// 后退按钮
		ImageView img = (ImageView) findViewById(R.id.img_back);

		img.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				back();

			}
		});
		
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			back();
			return false;
		} else {
			return super.onKeyDown(keyCode, event);
		}

	}
	
	private void back() {
		if (history.Parent!=null) {
			history=history.Parent;
			refreshLst(history);
		} else {
			finish();
		}
	}
	
	//根据路径更新列表
	public void refreshLst(FileHistoryModel historymodel){
		lst= historymodel.files;
		//把lst排序
		Collections.sort(lst);
		FileAdapter adapter=new FileAdapter(this, lst);
		lit_com_res.setAdapter(adapter);
		lit_com_res.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				FileModel model= lst.get(arg2);
				//判断点击的是文件夹还是文件
				if(model.Type==0){
					//文件夹
					FileHistoryModel h=new FileHistoryModel(SimpleFileManageActivity.this,model.Path, history);
					history=h;
					refreshLst(h);

				}else{
					//启动显示文件的activity
					if(model.eName.equals("html") || model.eName.equals("htm")){
						Intent intent=new Intent(SimpleFileManageActivity.this,WebViewActivity.class);
						intent.putExtra("path", model.Path);
						intent.putExtra("type", model.Filetype);
						startActivity(intent);
					}else if(model.eName.equals("jpg")){
						Intent intent=new Intent(SimpleFileManageActivity.this,ShowWebImageActivity.class);
						intent.putExtra("path", model.Path);
						startActivity(intent);
					}else if(model.eName.equals("avi") || model.eName.equals("mp4")){
						Intent intent=new Intent(SimpleFileManageActivity.this,VideoActivity.class);
						intent.putExtra("path", model.Path);
						startActivity(intent);
					}
					
					
				}
				
			}
			
		});
	}
}
