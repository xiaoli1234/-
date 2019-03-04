package com.cjf.androidframework.ListActivity;

import java.util.List;

import org.android.framework.R;
import org.cjf.android.framework.app.BaseActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.Toast;



public class SearchActivity extends BaseActivity implements
OnItemClickListener{
	private SearchView edt_search;
	private ListView lit_tc;
	private ShowLstAdapter mapdater;
	private List<ListModel> lst;
	
	private IOperationLinistener mlinistener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tc_search_layout);
		edt_search = (SearchView) findViewById(R.id.edt_search);
		lit_tc = (ListView) findViewById(R.id.lit_tc);
		// sOption = new SearchOptionManager(activity);
		edt_search.setOnQueryTextListener(new OnQueryTextListener() {

			@Override
			public boolean onQueryTextSubmit(String arg0) {
				// 执行搜索操作
				search();
				return false;
			}

			@Override
			public boolean onQueryTextChange(String arg0) {
				// TODO Auto-generated method stub
				return false;
			}
		});
	}
	
	//初始化操作项
	public void initOperation(IOperationLinistener linistener){
		mlinistener=linistener;
	}

	private void search() {
		String where_txt = edt_search.getQuery().toString();
		if(mlinistener!=null){
			lst = mlinistener.search(where_txt, this.mFramework.getAccessDatabase());
		}

		if (lst != null) {
			if(lst.size()==0){
				Toast.makeText(this, "没有搜索到结果！", Toast.LENGTH_LONG).show();
			}
			mapdater = new ShowLstAdapter(this, lst,mlinistener);
			lit_tc.setAdapter(mapdater);
			lit_tc.setOnItemClickListener(this);
		}else{
			Toast.makeText(this, "没有搜索到结果！", Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		if(mlinistener!=null){
			mlinistener.ItemClick(lst.get(arg2), this);
		}
	}
}
