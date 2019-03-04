package com.cjf.androidframework.login;


//用户实体类
public class UserModel {
	private String id="-1";
	private int status=-1;
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	private String username;
	private String pws;
	private String bz;
	public String getBz() {
		return bz;
	}
	public void setBz(String bz) {
		this.bz = bz;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPws() {
		return pws;
	}
	public void setPws(String pws) {
		this.pws = pws;
	}
	
}
