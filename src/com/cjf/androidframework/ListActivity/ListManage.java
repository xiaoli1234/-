package com.cjf.androidframework.ListActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.android.framework.R;
import org.zw.android.framework.util.AlertDialogUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;


//列表类型管理器
public class ListManage implements OnItemClickListener,OnClickListener {
	//定义操作函数
	private IOperationLinistener mlistener;
	
	//标记是否为编辑状态
		public Boolean IsEditer=false;
		
		
		public void setIsEditer(Boolean editer){
			IsEditer=editer;
			if(IsEditer){
		        txt_select_all.setVisibility(View.VISIBLE);
		        txt_delete.setVisibility(View.VISIBLE);
				if(lst!=null){
					for(int i=0;i<lst.size();i++){
						lst.get(i).IsEditer=true;
					}
				}
			}else{
		        txt_select_all.setVisibility(View.INVISIBLE);
		        txt_delete.setVisibility(View.INVISIBLE);
			}
		}
		
		private ListActivity mContext;
		private ListView lit_tc;
		private List<ListModel> lst;
		private ShowLstAdapter mapdater;
		
		private TextView txt_select_all,txt_delete;
		public ListManage(ListActivity context,IOperationLinistener listener){

			mContext=context;
			lit_tc=(ListView)context.findViewById(R.id.lit_tc);
			txt_select_all=(TextView)context.findViewById(R.id.txt_select_all);
			txt_delete=(TextView)context.findViewById(R.id.txt_delete);
			txt_select_all.setOnClickListener(this);
			txt_delete.setOnClickListener(this);
			mlistener=listener;
		}
		
		public void setListModels(List<ListModel> l){
			lst=l;
			//把列表按照是否已读排序
	
			if(lst!=null){
				Collections.sort(lst); 
				mapdater=new ShowLstAdapter(mContext, lst,mlistener);
				lit_tc.setAdapter(mapdater);
				lit_tc.setOnItemClickListener(this);
				lit_tc.setOnItemLongClickListener(new OnItemLongClickListener() {

					@Override
					public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						if(!IsEditer){
							setIsEditer(true);
							//长安的想为选中
							lst.get(arg2).IsSelect=true;
							//更新列表
							mapdater.notifyDataSetChanged();
							//震动
							Vibrator vibrator = (Vibrator)mContext.getSystemService(Context.VIBRATOR_SERVICE);  
					        long [] pattern = {100,400};   
					        vibrator.vibrate(pattern,-1);   
							//设置按钮的显示与隐藏

						}
						return false;
					}
				});
				setIsEditer(false);
			}
		}
		
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			// TODO Auto-generated method stub
			//启动编辑的activity
			if(IsEditer){
				if(lst.get(arg2).IsSelect){
					lst.get(arg2).IsSelect=false;
				}else{
					lst.get(arg2).IsSelect=true;
				}
				
				//更新列表
				mapdater.notifyDataSetChanged();
			}else{
				if(mlistener!=null){
					mlistener.ItemClick(lst.get(arg2),mContext);
				}
//				Intent intent=new Intent(mContext,ListActivity.class);
//				intent.putExtra("create", 0);
//				intent.putExtra("id", lst.get(arg2).model.getId());
//				mContext.startActivity(intent);
			}

		}
		
		//获得选中的集合
		public List<ListModel> getSelectModel(){
			if(lst==null){
				return null;
			}
			List<ListModel> lst1=new ArrayList<ListModel>();
			for(int i=0;i<lst.size();i++){
				if(lst.get(i).IsSelect){
					lst1.add(lst.get(i));
				}
			}
			
			return lst1;
		}
		
		

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			if(arg0.getId()==R.id.txt_delete){
				List<ListModel> lllst=getSelectModel();
				if(lllst==null){
					Toast.makeText(mContext, "请选中所要删除的要素", Toast.LENGTH_LONG).show();
					return;
				}
				if(lllst.size()==0){
					Toast.makeText(mContext, "请选中所要删除的要素", Toast.LENGTH_LONG).show();
					return;
				}
				DeleteDialogListener listener=new DeleteDialogListener(mContext, lllst,mlistener);
				//显示是否确认删除
				AlertDialogUtil.ShowAlertDialog(mContext, "确认删除选中踏查资料？", "删除确认",listener.confirmlistener , listener.cancellistener);

				//刷新列表
				
			}else{
				//全选
				if(lst!=null){
					for(int i=0;i<lst.size();i++){
						lst.get(i).IsSelect=true;
					}
					//更新列表
					mapdater.notifyDataSetChanged();
				}
			}
		}
		
		public Boolean reback(){
			if(IsEditer){
				setIsEditer(false);
				//设置按钮的显示与隐藏

				//退出编辑
				if(lst!=null){
					for(int i=0;i<lst.size();i++){
						lst.get(i).IsEditer=false;
						lst.get(i).IsSelect=false;
					}
				}
				mapdater.notifyDataSetChanged();
				return true;
				
			}else{
				return false;
			}
		}
}
