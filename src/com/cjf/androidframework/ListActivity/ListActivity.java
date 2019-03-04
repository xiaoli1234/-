package com.cjf.androidframework.ListActivity;

import java.util.List;

import org.android.framework.R;
import org.cjf.android.framework.app.BaseActivity;
import org.zw.android.framework.IFrameworkFacade;
import org.zw.android.framework.util.NetworkUtil;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class ListActivity extends BaseActivity implements OnClickListener {
	private ListView lit_tc;
	private ImageView img_back,img_search,img_sc,img_add;
	private IOperationLinistener mLinistener;
	private TextView txt_resoure_lst_title;
	
	protected List<ListModel> lst;
	
	ListManage manage;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lst_layout);

		
		img_back=(ImageView)findViewById(R.id.img_back);
		img_search=(ImageView)findViewById(R.id.img_search);
		img_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		img_sc=(ImageView)findViewById(R.id.img_sc);
		img_add=(ImageView)findViewById(R.id.img_add);

	}
	
	//初始化数据和操作
	protected void initData(IOperationLinistener listener){
		setOperationListener(listener);
		manage.setListModels(listener.initData(mFramework.getAccessDatabase()));
	}
	
	//设置对象的操作接口
	protected void setOperationListener(IOperationLinistener listener){
		mLinistener=listener;
		img_search.setOnClickListener(this);
		img_sc.setOnClickListener(this);
		img_add.setOnClickListener(this);
		manage=new ListManage(this,mLinistener);
	}
	
	protected void SetlstTitle(String title){
		txt_resoure_lst_title=(TextView)findViewById(R.id.txt_resoure_lst_title);
		txt_resoure_lst_title.setText(title);
	}
	
	protected void showadd(){
		img_add.setVisibility(View.VISIBLE);
	}
	

	
	public void updatalst(){
		if(mLinistener==null){
			return;
		}
		initData(mLinistener);
	}
	
	//隐藏上传按钮
	public void hideUpload(){
		img_sc=(ImageView)findViewById(R.id.img_sc);
		img_sc.setVisibility(View.GONE);
	}
	
	//隐藏搜索按钮
	public void hideSearch(){
		img_search=(ImageView)findViewById(R.id.img_search);
		img_search.setVisibility(View.GONE);
	}
	
	public IFrameworkFacade getFramework(){
		return mFramework;
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			if(manage!=null){
				if(!manage.reback()){
					finish();
				}else{
					return true;
				}
			}else{
				finish();
			}
			return true;
		} else {
			return super.onKeyDown(keyCode, event);
		}

	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		if(arg0.getId()==R.id.img_search){
			mLinistener.setQueryClick(img_search,this);
			
		}else if(arg0.getId()==R.id.img_sc){
			if(manage.getSelectModel()==null || manage.getSelectModel().size()==0){
				Toast.makeText(this, "没有选中要素！", Toast.LENGTH_LONG).show();
				return;
			}
			mLinistener.setUploadClick(img_sc,this,manage.getSelectModel());
		}else if(arg0.getId()==R.id.img_add){
			mLinistener.AddData(this);
		}
	}

}
