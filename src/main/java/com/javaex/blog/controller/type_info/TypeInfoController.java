package com.javaex.blog.controller.type_info;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.javaex.blog.exception.customException;
import com.javaex.blog.service.type_info.TypeInfoService;
import com.javaex.blog.service.user_info.UserInfoService;
import com.javaex.blog.view.Result;
import com.javaex.blog.view.TypeInfo;
import com.javaex.blog.view.UserInfo;


@Controller
@RequestMapping("type_info")
public class TypeInfoController {
	

	@Autowired
	private TypeInfoService typeInfoService;
		
	/**
	 * 查询所有的文章分类
	 * @param map
	 * @return
	 */
	@RequestMapping("list.action")
	public String list(ModelMap map) {
		List<TypeInfo> list = typeInfoService.list();
		map.put("list", list);
		return "admin/type_info/list";
	}
	/**
	 * 批量更新/插入文章分类
	 * @param idArr
	 * @param sortArr
	 * @param nameArr
	 * @return
	 */
	@RequestMapping("save.json")
	@ResponseBody
	public Result save(@RequestParam(value = "idArr") String [] idArr,
			@RequestParam(value = "sortArr") String [] sortArr,
			@RequestParam(value = "nameArr") String [] nameArr){	
		
		typeInfoService.save(idArr,sortArr,nameArr);
		
		return Result.success();
	}
	
	/**
	 * 批量删除文章分类
	 * @param idArr
	 * @return
	 * @throws customException 
	 */
	 @RequestMapping("delete.json")
	 @ResponseBody
	 public Result delete(@RequestParam(value = "idArr") String [] idArr) throws customException{	
		
		typeInfoService.delete(idArr);
		
		return Result.success();
	}
}
