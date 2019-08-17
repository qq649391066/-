<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>     
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>   
<meta charset="utf-8">
<title>编辑文章</title>
<link href="${pageContext.request.contextPath}/static/javaex/pc/lib/highlight/highlight.css" rel="stylesheet" />
<script src="${pageContext.request.contextPath}/static/javaex/pc/lib/highlight/highlight.min.js"></script>
<style>
	.file-container .cover {
		width: 100px;
		height: 100px;
	}
	.file-container .cover img {
		margin: 20px auto;
	}
</style>
</head>

<body>
   <!--顶部导航-->
  <div class="admin-navbar">
		<div class="admin-container-fluid clear">
	<c:import url="../headerANDmenu/header.jsp"></c:import>	
	</div>
	</div>
	<!--主题内容-->
	<div class="admin-mian">
		<!--左侧菜单-->		
	 <c:import url="../headerANDmenu/left_menu.jsp"></c:import>			
		<!--iframe载入内容-->
	 <div class="admin-markdown">
		<div class="breadcrumb">
		   <span>文章管理</span>
		   <span class="divider">/</span>
		   <span class="active">文章编辑</span>
	    </div>
		<div class="list-content">
			<!--块元素-->
	   <div class="block">
		 <!--修饰块元素名称-->
		<div class="banner">
			<p class="tab fixed">文章编辑</p>
		</div>	
		<!--正文内容-->
		<div class="main">
			<form id="form">
			<input type="hidden" name="articleId" value="${articleInfo.articleId}">
				<!--文本框-->
				<div class="unit clear">
					<div class="left"><span class="required">*</span><p class="subtitle">标题</p></div>
					<div class="right">
						<input type="text" id="title" class="text" name="title" data-type="必填"  placeholder="请输入文章标题" value="${articleInfo.title}"/>
					</div>
				</div>							
				<!--下拉选择框-->
				<div class="unit clear">
					<div class="left"><p class="subtitle">所属类型</p></div>
					<div class="right">
						<select id="type_idSelect" name="typeId" >
						<c:forEach items="${typeList}" var="typeInfo" varStatus="status">
					         <option value="${typeInfo.typeId}"<c:if test="${articleInfo.typeId==typeInfo.typeId}">selected</c:if>>${typeInfo.typeName}</option>					         
					   </c:forEach>					
						</select>
					</div>
				</div>					
				<!-- 上传封面图片 -->
				<div class="unit clear">
				<br/>
				 <div class="left"><p class="subtitle">上传封面图</p></div>
				  <div class="right">
				   <div id="container" class="file-container">
					<div class="cover">
					  <!--如果不需要回显图片，src留空即可-->
					  <c:choose>
				       <c:when test="${empty articleInfo.cover}">
				             <img class="upload-img-cover" src="${pageContext.request.contextPath}/static/javaex/pc/images/upAvatar.jpg"/>
				       </c:when>
				       <c:otherwise>
				              <img class="upload-img-cover" src="${articleInfo.cover}"/>
				       </c:otherwise>                        
				      </c:choose>					  					  
					   <input type="file" class="file" id="upload" accept="image/gif, image/jpeg, image/jpg, image/png" />
				  </div>
				 </div>
				 <input type="hidden" id="cover" name="cover" value="">
				</div>
				</div>	
					<!-- 富文本编辑器 -->										
				<div id="edit" class="edit-container">${articleInfo.content} </div>
				<input type="hidden" id="content" name="content" value=" " />
				<input type="hidden" id="contentText" name="contentText" value=" " />							
				<!--提交按钮-->
				<div class="unit clear" style="width: 800px;">
					<div style="text-align: center;">
						<!--表单提交时，必须是input元素，并指定type类型为button，否则ajax提交时，会返回error回调函数-->
						<input type="button" id="return" class="button no" value="返回" />
						<input type="button" id="submit" class="button yes" value="保存" />
					</div>
				</div>
			</form>
		</div>
	   </div>
		</div>						
		</div>
	</div>
</body>
<script>
	//富文本编辑器
	var content = '${articleInfo.content}';
	javaex.edit({
		id : "edit",
		image : {
			url : "upload.json",	// 请求路径
			param : "file",		// 参数名称，Spring中与MultipartFile的参数名保持一致
			dataType : "url",	// 返回的数据类型：base64 或 url
			isShowOptTip : true,	// 是否显示上传提示
			rtn : "rtnData",	// 后台返回的数据对象，在前台页面用该名字存储
			imgUrl : "data.imgUrl",	// 根据返回的数据对象，获取图片地址。  例如后台返回的数据为：{code: "000000", message: "操作成功！", data: {imgUrl: "/1.jpg"}}
		    prefix:"/upload/" , //图片地址的前缀，根据实际情况填		   
		},
		// content: content,
		isInit : true,
		callback : function(rtn) {
			$("#content").val(rtn.html);
			console.log($("#content").val(rtn.html));
			$("#contentText").val(rtn.text.substring(0,100));
			console.log($("#contentText").val(rtn.text.substring(0,100)));
		}
	});
	//下拉选择框
	javaex.select({
		id : "type_idSelect",
		isSearch : false,
		// 回调函数
		callback: function (rtn) {
			console.log("selectValue:" + rtn.selectValue);
			console.log("selectName:" + rtn.selectName);
		}
	});
	//上传封面
	javaex.upload({
		type : "image",
		url: "upload.json",  //请求路径
		id : "upload",	// <input type="file" />的id
		containerId : "container",	// 容器id
		param: "file",
		dataType : "url",	// 返回的数据类型：base64 或 url
		callback : function (rtn) {
			console.log(rtn);
			var imgUrl = rtn.data.imgUrl
			$("#container img").attr("src", "/upload/"+imgUrl);
			$("#cover").val("/upload/"+imgUrl);
//			if (rtn.code=="000000") {
//				$("#container img").attr("src", rtn.data.imgUrl);
//			} else {
//			javaex.optTip({
//					content : rtn.message,
//					type : "error"
//				});
//			}
		}
	});
	//返回按钮
	 $("#return").click(function(){
		 history.back();
	 });
	//保存按钮
	 $("#submit").click(function(){
		 $.ajax({
			   url:"save.json",
			   type:"POST",
			   dataType:"json",
			   data:$("#form").serialize(),
			   success:function(rtn){
				   console.log(rtn);
	                 if(rtn.code=="000000"){
	                	 javaex.optTip({
	             			content : rtn.message,
	             		});
	                	// 建议延迟加载
	 					setTimeout(function() {
	 						//跳转页面
	 					window.location.href="${pageContext.request.contextPath}/article_info/list_normal.action";
	 					}, 2000);			                	
	                 }else{
	                	 javaex.optTip({
	             			content : rtn.message,
	             			type : "error"
	             		});
	                 }
			   }			 
	   });

	 });
</script>
</html>  
  