package com.example.c2e;

public class login {
	int login_id,user_id;

	public login(int login_id, int user_id) {
		super();
		this.login_id = login_id;
		this.user_id = user_id;
	}

	public int getLogin_id() {
		return login_id;
	}

	public void setLogin_id(int login_id) {
		this.login_id = login_id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

}
