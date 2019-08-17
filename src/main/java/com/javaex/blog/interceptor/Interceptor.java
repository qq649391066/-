package com.javaex.blog.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.javaex.blog.view.UserInfo;

public class Interceptor implements HandlerInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//1.获取请求的地址
		String url= request.getRequestURI();
		//2.对特殊地址，直接放行
		if (url.indexOf("login")>=0 ||url.indexOf("portal")>=0) {
			return true;
		}
		//3.0判断session，session存在的话，登录后台
		HttpSession session = request.getSession();
		UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
		if (userInfo!=null) {
			//身份正确，放行
			return true;
		}
		//4.执行这里表示用户身份需要验证，跳到登录界面
		request.getRequestDispatcher("/WEB-INF/page/admin/login.jsp").forward(request, response);
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

		
	}

}
