package com.javaex.blog.dao.type_info;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.javaex.blog.view.TypeInfo;

public interface ITypeInfoDao {
	
	/**
	 * 查询所有文章分类
	 * @return
	 */
	public List<TypeInfo> list();
    /*
     * 插入一条新的数据
     * typeSort：排序
     * typeName：分类名称
     */
	public int insert(@Param("typeSort")String typeSort,@Param("typeName") String typeName);
    /*
     * 更新一条数据
     * typeId:文章类型id
     * typeSort：排序
     * typeName：分类名称
     */
	public int update(@Param("typeId")String typeId,@Param("typeSort") String typeSort,@Param("typeName") String typeName);
	
	/*
	 * 通过typeid存到idArr数组里然后实现批量删除删除
	 */
	public int delete(@Param("idArr")String[] idArr);
	
	/**
	   * 根据typeId查询文章分类
	   * @param typeId
	   * @return
	   */
	public TypeInfo selectById(String typeId);

}
