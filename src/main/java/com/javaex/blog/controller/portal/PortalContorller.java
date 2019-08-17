package com.javaex.blog.controller.portal;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.javaex.blog.service.article_info.ArticleInfoService;
import com.javaex.blog.service.type_info.TypeInfoService;
import com.javaex.blog.view.ArticleInfo;
import com.javaex.blog.view.Result;
import com.javaex.blog.view.TypeInfo;



@Controller
@RequestMapping("portal")
public class PortalContorller {
	@Autowired
	private ArticleInfoService articleInfoService;	
	@Autowired
	private TypeInfoService typeInfoService;

	 /**
	  * 查询所有文章(正常)
	  * @return
	  */
	@RequestMapping("index.action")
	public String listNormal(ModelMap map,
			@RequestParam(value="pageNum",defaultValue="1")int pageNum,
			@RequestParam(value="pageSize",defaultValue="3")int pageSize) {
		//将数据传给数据库
		Map<String, String> param = new HashMap<String, String>();
		
		param.put("status", "1");
		//pageHelper分页插件
		//只需在查询之前调用，传入当前页码，以及每一页显示多少条
		PageHelper.startPage(pageNum, pageSize);
		List<ArticleInfo> list = articleInfoService.list(param);
		//map.put("listNormal", listNormal);
		PageInfo<ArticleInfo> pageInfo = new PageInfo<ArticleInfo>(list);
		map.put("pageInfo", pageInfo);		
		return "portal/index";
	}
	/**
	 * 页面一加载，就向后台请求文章分类的数据
	 */
	//拦截前端发出的请求
	@RequestMapping("get_type.json")
	//将controller的方法返回的对象通过适当的转换器转换为指定的格式之后，
	//写入到response对象的body区，通常用来返回JSON数据或者是XML
	@ResponseBody
	public Result getType() {
		List<TypeInfo> typeList = typeInfoService.list();
		return Result.success().add("typeList", typeList);
	}

	 /**
	  * 查询指定类型的文章(正常)
	  * @return
	  */
	@RequestMapping("type.action")
	public String type(ModelMap map,
			@RequestParam(value="typeId")String typeId,
			@RequestParam(value="pageNum",defaultValue="1")int pageNum,
			@RequestParam(value="pageSize",defaultValue="3")int pageSize) {
		//将数据传给数据库
		Map<String, String> param = new HashMap<String, String>();		
		param.put("typeId", typeId);
		param.put("status", "1");
		//pageHelper分页插件
		//只需在查询之前调用，传入当前页码，以及每一页显示多少条
		PageHelper.startPage(pageNum, pageSize);
		List<ArticleInfo> list = articleInfoService.list(param);
		//map.put("listNormal", listNormal);
		PageInfo<ArticleInfo> pageInfo = new PageInfo<ArticleInfo>(list);
		map.put("pageInfo", pageInfo);	
		map.put("typeInfo", typeInfoService.selectById(typeId));
		return "portal/typeIndex";
	}
	
	 /**
	  * 根据主键查询文章(正常)
	  * @return
	  */
	@RequestMapping("article.action")
	public String listNormal(ModelMap map,
			@RequestParam(value="articleId")String articleId){	
		 ArticleInfo articleInfo  = articleInfoService.selectById(articleId);
	    map.put("articleInfo", articleInfo);
		return "portal/article";
	}
	
}
