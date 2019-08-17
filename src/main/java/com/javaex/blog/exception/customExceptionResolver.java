package com.javaex.blog.exception;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

public class customExceptionResolver implements HandlerExceptionResolver{

	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
	      //1.打印错误信息到控制台
		ex.printStackTrace();
		//定义一个错误信息给客户看
		String message = "操作失败!";
		//判断该错误是否是预期的错误
		if (ex instanceof customException) {
			message =((customException)ex).getMessage();
		}
		//2.判断请求类型
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		ResponseBody responseBody = handlerMethod.getMethod().getAnnotation(ResponseBody.class);
		if (responseBody!=null) {
			//2.1如果是jason请求，则返回json数据
			Map<String, Object> responseMap = new HashMap<String, Object>();
			responseMap.put("code", "999999");
			responseMap.put("message", message);
			String json = new Gson().toJson(responseMap);
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json;charset=utf-8");
			try {
				response.getWriter().write(json);
				response.getWriter().flush();
			}catch(IOException e1) {
				e1.printStackTrace();
			}
			//返回一个空的ModelAndView表示已经手动生成响应
			return new ModelAndView();
		}
		
		//2.2如果是跳转页面的请求，则跳转到error页面
		ModelAndView modelAndView = new ModelAndView();
		//将错误信息传到页面
		modelAndView.addObject("message", message);
		//指向错误页面
		modelAndView.setViewName("error");
		
		return modelAndView;
	
	}	
}
