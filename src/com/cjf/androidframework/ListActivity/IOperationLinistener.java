package com.cjf.androidframework.ListActivity;

import java.util.List;

import org.zw.android.framework.IAccessDatabase;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;

//所有操作调用的接口
public interface IOperationLinistener {
	//添加一条记录操作
	public void AddData(Context context);
	
	//删除操作接口
	public void DeleteData(List<ListModel> lst,IAccessDatabase db);
	
	//设置上传按钮点击事件回调
	public void setUploadClick(ImageView imageview,Context context,List<ListModel> lst);
	
	//设置查询按钮点击回调
	public void setQueryClick(ImageView imageview,Context context);
	
	//设置列表项单击回调
	public void ItemClick(ListModel model,Context context);
	
	//初始化数据列表
	public List<ListModel> initData(IAccessDatabase db);
	
	//设置列表项的view
	public void setItemView(LinearLayout layout,ListModel model);
	
	//搜索项的返回函数
	public List<ListModel> search(String where_txt,IAccessDatabase db);
}
