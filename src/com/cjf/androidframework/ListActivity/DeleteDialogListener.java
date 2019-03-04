package com.cjf.androidframework.ListActivity;

import java.util.List;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.widget.Toast;


public class DeleteDialogListener {
	public OnClickListener confirmlistener;
	public OnClickListener cancellistener;
	
	public ListActivity mContext;
	private List<ListModel> mlst;
	
	//持有操作接口
	private IOperationLinistener mlistener;
	
	
	public DeleteDialogListener(ListActivity context,List<ListModel> lst,IOperationLinistener listener){
		mContext=context;
		mlst=lst;
		mlistener=listener;
		confirmlistener=new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				if(mlistener!=null){
					mlistener.DeleteData(mlst,mContext.getFramework().getAccessDatabase());
				}
				//DALTCInfo.DeleteShowModel(mContext.getFramework().getAccessDatabase(), mlst);
				Toast.makeText(mContext,"删除成功", Toast.LENGTH_LONG).show();
				mContext.updatalst();
				
			}
		};
		cancellistener=new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				
			}
		};
	}
}
