package com.javaex.blog.dao.user_info;

import org.apache.ibatis.annotations.Param;

import com.javaex.blog.view.UserInfo;

public interface IUserInfoDao {
     /**
      * @param loginName 登录名
      * @param passWord 登录密码
      */
	public UserInfo selectUser(@Param("loginName") String loginName,@Param("passWord") String passWord);
}
