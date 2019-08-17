package com.javaex.blog.dao.article_info;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.javaex.blog.view.ArticleInfo;

public interface IArticleInfoDao {
	
	/**
	 * 查询所有文章
	 * @param param 
	 * @return
	 */
	public List<ArticleInfo> list(Map<String, String> param);

	public ArticleInfo selectById(String articleId);
    /*
     * 新增文章
     */
	public int insert(ArticleInfo articleInfo);
    
	/*
	 * 更新文章
	 */
	public int update(ArticleInfo articleInfo);
     
	
	  /**
	    * 批量更新文章(包括更新文章类型(typeId)和是否放在回收站(status))
	    * @param param   
	    */
	public void batchUpdate(Map<String, Object> param);

	  /**
	    * 批量删除
	    * @param param   
	    */
	public void batchDelete(@Param("idArr") String[] idArr);
	
   /**
    * 根据文章分类，查询文章的数量
    * @param typeIdArr
    * @param status  文章的状态
    * @return
    */
	public int countByTypeIdArr(@Param("typeIdArr") String[] typeIdArr,@Param("status") String status);
    
	
	/**
	 * 通过查询某篇文章的时候更新浏览人数（viewCount）
	 * @param articleId
	 */
	public void updateViewCount(@Param("articleId")String articleId,@Param("viewCount") int viewCount);

 






       
}
