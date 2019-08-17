package com.javaex.blog.service.type_info;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.AnnotationIntrospector.ReferenceProperty.Type;
import com.javaex.blog.dao.article_info.IArticleInfoDao;
import com.javaex.blog.dao.type_info.ITypeInfoDao;
import com.javaex.blog.exception.customException;
import com.javaex.blog.view.ArticleInfo;
import com.javaex.blog.view.Result;
import com.javaex.blog.view.TypeInfo;
@Service("TypeInfoService")
public class TypeInfoService {
	 @Autowired
	private ITypeInfoDao iTypeInfoDao;
	 @Autowired
	private IArticleInfoDao iArticleInfoDao;
	
	 
	 /**
	  * 查询所有文章分类
	  * @return
	  */
	public List<TypeInfo> list() {
		// TODO Auto-generated method stub
		return iTypeInfoDao.list();
	}

     /**
      * 批量更新/插入文章分类
     * @param nameArr 
     * @param sortArr 
     * @param idArr 
      */
	public void save(String [] idArr, String [] sortArr, String [] nameArr) {
		        //遍历第一个数组
				for(int i=0;i<idArr.length;i++) {
					//判断这条数据是需要更新还是插入的
					if (StringUtils.isEmpty(idArr[i])) {
						//插入
						iTypeInfoDao.insert(sortArr[i],nameArr[i]);
					}else {
						//更新
						iTypeInfoDao.update(idArr[i],sortArr[i],nameArr[i]);
					}
				}
		
	}
	
	/**
	 * 批量删除文章分类
	 * @param idArr  主键
	 * @throws customException 
	 */

	public void delete(String[] idArr) throws customException {
		//判断该文章分类typeId有没有被使用(如果有被使用的话则不能删除)
		//查询指定文章类型的正常的文章的数量
		int naturalCount = iArticleInfoDao.countByTypeIdArr(idArr,"1");
		if (naturalCount>0) {
			//被占用了，禁止删除
			throw new customException("存在已被使用的文章分类，无法删除");
		}
		//查询回收站的指定文章类型的文章数量
		int notNaturalCount = iArticleInfoDao.countByTypeIdArr(idArr,"0");
		if (notNaturalCount>0) {	
			throw new customException("回收站还有该文章类型的文章，"
					+ "请查看一下回收站的文章是否有需要，"
					+ "如无需要请删除后再删除该文章分类");
		}				
			iTypeInfoDao.delete(idArr);					
	}
  /**
   * 根据typeId查询文章分类
   * @param typeId
   * @return
   */
	public TypeInfo selectById(String typeId) {
		
		return iTypeInfoDao.selectById(typeId);
	}    	
}
