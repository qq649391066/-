package com.javaex.blog.service.article_info;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;

import com.javaex.blog.dao.article_info.IArticleInfoDao;
import com.javaex.blog.view.ArticleInfo;

@Service("ArticleInfoService")
public class ArticleInfoService {
	 @Autowired
	private IArticleInfoDao iArticleInfoDao;
	
	
	/**
	  * 查询所有文章
	  * @return
	  */
	public List<ArticleInfo> list(Map<String, String> param) {
	    
		return iArticleInfoDao.list(param);
	}
	/**
	  * 查询一篇文章
	  * @return
	  */
	public ArticleInfo selectById(String articleId) {

		ArticleInfo articleInfo =  iArticleInfoDao.selectById(articleId);
		//获取当前的浏览量
		if (articleInfo!=null) {
			int nViewCount = articleInfo.getViewCount();
			//浏览量自增
			nViewCount++;
			//更新浏览量
			iArticleInfoDao.updateViewCount(articleId,nViewCount);
		}
		
		return articleInfo;		
	}

    /**
     * 保存文章
     * @param articleInfo
     */
	public void save(ArticleInfo articleInfo) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
		//判断文章是新增还是更新
		if (StringUtils.isEmpty(articleInfo.getArticleId())) {
			//新增
			articleInfo.setStatus(1);			
			articleInfo.setUpdateTime(df.format(new Date()));
			articleInfo.setViewCount(1);
			iArticleInfoDao.insert(articleInfo);
		}else {
			//更新
			articleInfo.setUpdateTime(df.format(new Date()));
			iArticleInfoDao.update(articleInfo);
		}
		
	}
  
	  /**
	    * 批量更新文章(包括更新文章类型(typeId)和是否放在回收站(status))
	    * @param param   
	    */
		public void batchUpdate(Map<String, Object> param) {
			iArticleInfoDao.batchUpdate(param);	
		}

		/**
		    * 批量彻底删除文章
		 * @param idArr 
		    * @param param   
		    */
	public void batchDelete(String[] idArr) {
		
		iArticleInfoDao.batchDelete(idArr);
	}

}
