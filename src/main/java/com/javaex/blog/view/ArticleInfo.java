package com.javaex.blog.view;

import java.util.Date;

/*
 * 文章
 */
public class ArticleInfo {
   private String articleId;
   private String title;
   private String content;
   private String contentText;  //文章的简介
   private String cover;    //文章的图片地址
   private int viewCount;   //浏览次数
   private String updateTime;   //更新时间
   private int status;   //文章状态(1:正常 0:回收站)
   private int typeId;  //文章类型的ID
   
   private String typeName;   //文章分类的名称
   
   
public String getTypeName() {
	return typeName;
}
public void setTypeName(String typeName) {
	this.typeName = typeName;
}
public String getArticleId() {
	return articleId;
}
public void setArticleId(String articleId) {
	this.articleId = articleId;
}

public String getTitle() {
	return title;
}
public void setTitle(String title) {
	this.title = title;
}
public String getContent() {
	return content;
}
public void setContent(String content) {
	this.content = content;
}
public String getContentText() {
	return contentText;
}
public void setContentText(String contentText) {
	this.contentText = contentText;
}
public String getCover() {
	return cover;
}
public void setCover(String cover) {
	this.cover = cover;
}



public int getViewCount() {
	return viewCount;
}
public void setViewCount(int viewCount) {
	this.viewCount = viewCount;
}
public String getUpdateTime() {
	return updateTime;
}
public void setUpdateTime(String updateTime) {
	this.updateTime = updateTime;
}
public int getStatus() {
	return status;
}
public void setStatus(int status) {
	this.status = status;
}
public int getTypeId() {
	return typeId;
}
public void setTypeId(int typeId) {
	this.typeId = typeId;
}
	
	   
   
}
