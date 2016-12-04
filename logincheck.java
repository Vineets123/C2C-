package com.example.c2e;

public class logincheck {
	int login_id;
	String uname,paswd,type;
	public logincheck(int login_id,String uname,
			String paswd, String type) {
		super();
		this.login_id = login_id;
		
		this.uname = uname;
		this.paswd = paswd;
		this.type = type;
	}
	public int getLogin_id() {
		return login_id;
	}
	public void setLogin_id(int login_id) {
		this.login_id = login_id;
	}
	
	
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getPaswd() {
		return paswd;
	}
	public void setPaswd(String paswd) {
		this.paswd = paswd;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}