package com.javaex.blog.service.user_info;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaex.blog.dao.user_info.IUserInfoDao;
import com.javaex.blog.view.UserInfo;
@Service("UserInfoService")
public class UserInfoService {
   @Autowired
   private IUserInfoDao iUserInfoDao;
   
   /**
    * 校验用户登录
    * @param loginName 登录名
    * @param passWord 登录密码
    * @return
    */
   public UserInfo checktUser(String loginName,String passWord) {
	   return iUserInfoDao.selectUser(loginName, passWord);
   }
}
