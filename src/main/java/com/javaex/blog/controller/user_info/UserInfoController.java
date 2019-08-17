package com.javaex.blog.controller.user_info;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.javaex.blog.exception.customException;
import com.javaex.blog.service.user_info.UserInfoService;

import com.javaex.blog.view.Result;
import com.javaex.blog.view.UserInfo;


@Controller
@RequestMapping("admin")
public class UserInfoController {
	
	//在类下添加这句话
    private Logger log = Logger.getLogger(this.getClass());
	
	
	
	@Autowired
	private UserInfoService userInfoService;
		
	
	@RequestMapping("index.action")
	public String index() {
		return "admin/index";
	}
	
	@RequestMapping("login.action")
	public String login() {
		return "admin/login";
	}
	@RequestMapping("login.json")
	@ResponseBody
	public Result loginCheck(ModelMap map,HttpServletRequest request) throws customException {
		log.debug("登录开始");
		log.info("登录开始");
		log.error("登录开始");
		//1.获取参数
		String loginName=request.getParameter("loginName");
		String passWord = request.getParameter("password");
		//2.校验参数
		//2.1判断参数是否为空
		if (StringUtils.isEmpty(loginName)||StringUtils.isEmpty(passWord)) {
			throw new customException("用户名或密码为空");
			//return Result.error("用户名或密码为空");
			
		}
		//2.2判断用户名密码是否正确
		UserInfo userInfo = userInfoService.checktUser(loginName, passWord);
		if (userInfo==null) {
			//用户名或密码不正确
			throw new customException("用户名或密码不正确");
			//return Result.error("用户名或密码不正确");
		}
		
		//3.设置session
		//返回一个结果参数
		request.getSession().setAttribute("userInfo", userInfo);
		
		return Result.success();
	}
	
	
	@RequestMapping("logout.action")
	public String loginOut(HttpSession session) {				
	    //销毁session
		session.invalidate();		
		return "admin/login";
	}
	

}
