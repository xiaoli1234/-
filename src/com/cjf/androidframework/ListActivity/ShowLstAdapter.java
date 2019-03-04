package com.cjf.androidframework.ListActivity;

import java.util.List;

import org.android.framework.R;
import org.cjf.android.framework.app.MyBaseAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class ShowLstAdapter extends MyBaseAdapter {
	protected Context mcontext ; 
	protected LayoutInflater minflater;
	
	private List<ListModel> mlst;
	
	private IOperationLinistener mlinistener;
	
	public ShowLstAdapter(Context context,List<ListModel> lst,IOperationLinistener linistener){
		super(context);
		minflater = LayoutInflater.from(context);
		this.mcontext = context ;
		mlst=lst;
		mlinistener=linistener;
	}
	
	//计算行数，也就是图片列表的记录数量
	@Override
	public int getCount() {
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
		//初始化每一行   arg0是行数
		LinearLayout layout=(LinearLayout)minflater.inflate(R.layout.tc_clst_adapter, null);
		if(mlinistener!=null){
			mlinistener.setItemView(layout,mlst.get(arg0));
		}
//		TextView txt_taskid=(TextView)layout.findViewById(R.id.txt_taskid);
//		txt_taskid.setText(mlst.get(arg0).model.getTaskID());
//		TextView txt_people=(TextView)layout.findViewById(R.id.txt_people);
//		txt_people.setText(mlst.get(arg0).model.getPeople());
//		TextView txt_projectname=(TextView)layout.findViewById(R.id.txt_projectname);
//		txt_projectname.setText(mlst.get(arg0).model.getProjectName());
		ImageView img_j1=(ImageView)layout.findViewById(R.id.img_j1);
		if(mlst.get(arg0).IsJ1){
			img_j1.setVisibility(View.VISIBLE);
		}else{
			img_j1.setVisibility(View.INVISIBLE);
		}
		
		
		if(mlst.get(arg0).IsEditer){
			if(mlst.get(arg0).IsSelect){
				ImageView img_select=(ImageView)layout.findViewById(R.id.img_select);
				img_select.setBackgroundResource(R.drawable.check1);
				img_select.setVisibility(View.VISIBLE);
			}else{
				ImageView img_select=(ImageView)layout.findViewById(R.id.img_select);
				img_select.setBackgroundResource(R.drawable.check2);
				img_select.setVisibility(View.VISIBLE);
			}
		}
		return layout;
	}
}
