package com.project.android.framework.model;

import java.util.Date;
import java.util.List;

import org.zw.android.framework.IAccessDatabase;
import org.zw.android.framework.db.ColumnDate;
import org.zw.android.framework.db.ColumnInt;
import org.zw.android.framework.db.ColumnShort;
import org.zw.android.framework.db.ColumnString;
import org.zw.android.framework.db.Table;
import org.zw.android.framework.db.core.ColumnPrimaryKey;
import org.zw.android.framework.db.core.ColumnPrimaryKey.PrimaryKeyType;

//搜索历史记录实体类
@Table(TableName = "SearchHistoryModel")
public class SearchHistoryModel {
	//搜索前10行的sql语句
	public static String selectsql="select top 10 * from SearchHistoryModel ORDER BY time DESC";
	
	@ColumnPrimaryKey(Type = PrimaryKeyType.AUTO)
	@ColumnInt
	private int id ;
	
	@ColumnString(length=100)
	private String key ;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	@ColumnDate
	private Date time;
	
	//获得一个要素
	public static SearchHistoryModel getModel(String key,IAccessDatabase db){
		List<SearchHistoryModel> lst=db.queryObjects("select * from SearchHistoryModel where key='"+key+"'", SearchHistoryModel.class);
		return lst.get(0);
	}
	
	//判断一个关键字的数据是否存在
	public static boolean IsExist(String key,IAccessDatabase db){
		if(key==""){
			return false;
		}
		List<SearchHistoryModel> lst=db.queryObjects("select * from SearchHistoryModel where key='"+key+"'", SearchHistoryModel.class);
		if(lst==null){
			return false;
		}
		if(lst.size()>0){
			return true;
		}
		return false;
	}
	
	
}
