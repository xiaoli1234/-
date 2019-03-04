package com.cjf.androidframework.login;

import org.android.framework.R;
import org.cjf.android.framework.app.BaseActivity;
import org.cjf.android.framework.app.services.IWebResponse;
import org.zw.android.framework.util.AlertDialogUtil;
import org.zw.android.framework.util.NetworkUtil;

import android.app.Activity;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

//登录界面
public class LoginActivity extends BaseActivity {
	private Button btn_login;
	private EditText txt_pws,txt_username;
	private ImageView img_username,img_pws;
	protected String loginurl;
	protected UserModel model;
	private Dialog loaddialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_layout);
		setlistener();
	}
	
	public void setlistener(){
		btn_login=(Button)findViewById(R.id.btn_login);
		txt_pws=(EditText)findViewById(R.id.txt_pws);
		txt_username=(EditText)findViewById(R.id.txt_username);
		
		img_username=(ImageView)findViewById(R.id.img_username);
		img_pws=(ImageView)findViewById(R.id.img_pws);
		
		txt_pws.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View arg0, boolean arg1) {
				// TODO Auto-generated method stub
				if(arg1){
					img_pws.setBackgroundResource(R.color.logion_img_select);
					img_username.setBackgroundResource(R.color.logion_img_unselect);
				}
			}
		});
		txt_username.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View arg0, boolean arg1) {
				// TODO Auto-generated method stub
				if(arg1){
					
					img_username.setBackgroundResource(R.color.logion_img_select);
					img_pws.setBackgroundResource(R.color.logion_img_unselect);
				}
			}
		});
		//初始化用户名
		SharedPreferences p= getSharedPreferences("user", 
				Activity.MODE_PRIVATE); 
		String username= p.getString("name", ""); 
		
		txt_username.setText(username);
		btn_login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//判断用户名密码是否为空
				String username=txt_username.getText().toString();
				String pws=txt_pws.getText().toString();
				
				if(username.equals("") || pws.equals("")){
					Toast.makeText(LoginActivity.this, "用户名密码不能为空！", Toast.LENGTH_LONG).show();
				}
				
				if(!NetworkUtil.isNetworkConnected()){
					Toast.makeText(LoginActivity.this, "网络连接不可用，请检查是否联网！", Toast.LENGTH_LONG).show();
					return;
				}
				model=new UserModel();
				model.setPws(pws);
				model.setUsername(username);
				loaddialog=AlertDialogUtil.createLoadingDialog(LoginActivity.this);
				loaddialog.show();
				LoginImpl impl=new LoginImpl(LoginActivity.this.mFramework.getAsyncExecutor(), loginurl);
				impl.login(new IWebResponse() {
					
					@Override
					public void WebResponse(Object value) {
						// TODO Auto-generated method stub
						if(value instanceof Exception){
							if(loaddialog!=null){
								loaddialog.hide();
								loaddialog=null;
							}
							Toast.makeText(LoginActivity.this, "连接服务器失败，请检查网络！", Toast.LENGTH_LONG).show();
							return;
						}
						if(((UserModel)value).getStatus()==0){
							fail();
							return;
						}
						if(((UserModel)value).getStatus()==1){
							//吧用户名保存下来 下次登录初始化用户名
							SharedPreferences mySharedPreferences= getSharedPreferences("user", 
									Activity.MODE_PRIVATE); 
							//实例化SharedPreferences.Editor对象（第二步） 
							SharedPreferences.Editor editor = mySharedPreferences.edit(); 
							//用putString的方法保存数据 
							UserModel mm=(UserModel)value;
							editor.putString("name", mm.getUsername()); 
							//提交当前数据 
							editor.commit(); 
							
							
							succeed(((UserModel)value));
						}
					}
				}, model);
			}
		});
	}
	
	//登录成功函数  子类复写
	public void succeed(UserModel model){
		if(loaddialog!=null){
			loaddialog.hide();
			loaddialog=null;
		}
		finish();
	}
	
	//登录失败函数
	public void fail(){
		if(loaddialog!=null){
			loaddialog.hide();
			loaddialog=null;
		}
		Toast.makeText(LoginActivity.this, "用户名或密码错误！", Toast.LENGTH_LONG).show();
		return;
	}

}
