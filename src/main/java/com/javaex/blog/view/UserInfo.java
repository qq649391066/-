package com.javaex.blog.view;


public class UserInfo {
	private int id;
	private String loginName;
	private String passWord;
	public UserInfo() {
		super();
	}
	public UserInfo(int id, String loginName, String passWord) {
		super();
		this.id = id;
		this.loginName = loginName;
		this.passWord = passWord;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	
	
}
