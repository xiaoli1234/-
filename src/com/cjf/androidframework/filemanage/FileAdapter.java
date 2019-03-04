package com.cjf.androidframework.filemanage;

import java.util.List;

import org.android.framework.R;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class FileAdapter extends BaseAdapter {
	protected Context mcontext ; 
	protected LayoutInflater minflater ;
	private List<FileModel> mlst;
	public FileAdapter(Context context,List<FileModel> lst){
		minflater = LayoutInflater.from(context);
		this.mcontext = context ;
		mlst=lst;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mlst.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return mlst.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
				FileModel model=mlst.get(arg0);
				LinearLayout layout=(LinearLayout)minflater.inflate(R.layout.file_adapter, null);
				TextView tx=(TextView)layout.findViewById(R.id.txt_resource_name);
				String title=model.FileName;
				//String title=model.Title;
				if(title.length()>40){
					title=title.substring(0, 40)+"...";
				}
				tx.setText(title);
				ImageView img=(ImageView)layout.findViewById(R.id.img_ico);
				if(model.Type==0){
					img.setBackgroundResource(R.drawable.rwjj);
				}else{
					img.setBackgroundResource(R.drawable.rwj);
				}
				TextView txt_wd=(TextView)layout.findViewById(R.id.txt_wd);
				if(model.Filetype.equals("jpg") || model.Filetype.equals("png")){
					txt_wd.setText("图片资料");
				}else if(model.Filetype.equals("video")){
					txt_wd.setText("视频资料");
				}else if(model.Filetype.equals("ppt")){
					txt_wd.setText("PPT文档");
				}else if(model.Filetype.equals("directory")) {
					txt_wd.setText("文件夹");
				}else{
					txt_wd.setText("文档资料");
				}
				
				return layout;
	}

}
