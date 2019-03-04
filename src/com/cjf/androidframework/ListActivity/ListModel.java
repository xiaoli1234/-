package com.cjf.androidframework.ListActivity;



import android.view.View;
import android.widget.LinearLayout;

//用于现实列表集成的实体类
public class ListModel implements Comparable<ListModel>{
	//标记是否被选中
	public Boolean IsSelect=false;
	public Boolean IsEditer=false;
	
	//标记是否显示加1
	public Boolean IsJ1=false;
	
	public ListModel(){
		IsSelect=false;
		IsEditer=false;
	}
	//获得列表VIEW方法  子类中重写
	public void SetView(LinearLayout layout){
		return;
	}
	@Override
	public int compareTo(ListModel arg0) {
		if(arg0.IsJ1){
			return 1;
		}
		return -1;
	}
	
	//获得用于搜索的字段
	
}
