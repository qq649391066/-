<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>     
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>   
<meta charset="utf-8">
<title>文章列表</title>
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
		   <span class="active">文章列表</span>
	    </div>
			<div class="list-content">
			<!--块元素-->
	<div class="block">
		<!--页面有多个表格时，可以用于标识表格-->
		<h2>文章分类列表</h2>	
		<!--正文内容-->
		<div class="main">	
	     <div style="text-align:right;margin-bottom:10px;">	     
		        <!-- 文章分类 -->          
			 <select id="type_id">
			 <option value="">请选择</option>
			 <c:forEach items="${typeList}" var="typeInfo" varStatus="status">
			        <option value="${typeInfo.typeId}"
			        <c:if test="${typeInfo.typeId==typeId}">selected</c:if>>${typeInfo.typeName}</option>
			   </c:forEach>
				</select>
			 	<!-- 范围日期框 -->		
			 	<input type="text" id="date2" class="ex-date" style="width: 220px;" value="" readonly/>		
				<!-- 标题检索 -->
				<input type="text" class="text" id="title" value="${keyWord}" placeholder="搜索文章标题"/>
				<!-- 点击查询按钮 -->
				<button class="button blue icon-search" style="margin-bottom: 3px;" onclick="search()"></button>
	     </div>
			<!--表格上方的操作元素，添加、删除等-->
			<div class="operation-wrap">
			 <div class="buttons-wrap">
			  <a href="edit.action">
			   <button id="add" class="button blue radius-3"><span class="icon-plus"></span> 添加</button>
			  </a>					
			</div>
			</div>
			<table id="table" class="table ">
				<thead>
					<tr>
						<th class="checkbox"><input type="checkbox" class="fill  listen-1" /> </th>
						<th>序号</th>
						<th>文章类型</th>	
						<th>文章标题</th>
						<th>发表日期</th>	
						<th>阅读量</th>		
						<th>编辑</th>					
					</tr>
				</thead>
				<tbody>
				  <c:choose>
                        <c:when test="${fn:length(pageInfo.list)==0}">  
                         <tr>
                           <td colspan="7" style="text-align:center;">暂无记录</td>
                         </tr>                   
                        </c:when>
                        <c:otherwise>
                          <c:forEach items="${pageInfo.list}" var="entity" varStatus="status">
				           <tr>
				           <td><input type="checkbox" class="fill   listen-1-2" name="articleId" value="${entity.articleId}"/> </td>
				            <td>${status.index+1}</td>
				            <td>${entity.typeName}</td>
				            <td>${entity.title}</td>
				            <td>${entity.updateTime}</td>
				            <td>${entity.viewCount}</td>
				            <td>
				             <a href="edit.action?articleId=${entity.articleId}">
			                     <button class="button wathet"><span class="icon-edit-2"></span> 编辑</button>
			                 </a>	
				            </td>
				           </tr>
                         </c:forEach>
                        </c:otherwise>                        
                  </c:choose>
				</tbody>
			</table>
			<!-- 分页 -->
			<div class="page">
				<ul id="page" class="pagination"></ul>
			</div>	
			<!--内容区域-->
              <div class="list-content">	
				<!--块元素-->
				<div class="block no-shadow">
					<!--banner用来修饰块元素的名称-->
					<div class="banner">
						<p class="tab fixed">批量操作</p>
					</div>
					<!--正文内容-->
					<div class="main" style="margin-bottom:150px;">
					<input type="radio" class="fill" name="radio" value="move"/>批量移动到分类
					<select id="type_id2">
						<c:forEach items="${typeList}" var="typeInfo" varStatus="status">
					        <option value="${typeInfo.typeId}">${typeInfo.typeName}</option>
					    </c:forEach>
					</select>
					<br/>
					<br/>
					<input type="radio" class="fill" name="radio" value="recycle"/>批量删除
					<br/>
					<br/>
					<button id="submit" class="button green"><span class="icon-check"></span>提交</button>
					</div>
				</div>		
		   </div>
	     </div>
	     </div>
			</div>						
		</div>
	</div>
</body>
<script>	
	   //文章分类下拉框
			javaex.select({
				id : "type_id",
				isSearch:false
			});	
	   //中间的下拉选择框
	   javaex.select({
		id : "type_id2",
		isSearch : false,
	});
		   //范围日期框
		   var startDate="";
		   var endDate="";
		   javaex.date({
			id : "date2",		// 承载日期组件的id
			monthNum : 2,		// 2代表选择范围日期
			alignment:"right",
			startDate : "${startDate}",	// 开始日期
			endDate : "${endDate}",		// 结束日期
			// 重新选择日期之后返回一个时间对象
			callback : function(rtn) {
				startDate = rtn.startDate;
				endDate = rtn.endDate;
				console.log(rtn.startDate);
				console.log(rtn.endDate);
				//alert(rtn.startDate + " - " + rtn.endDate);
			}
		});
		   /**
			*分页
			*/ 
		 var currentPage="${pageInfo.pageNum}";
		 var pageCount="${pageInfo.pages}";
	     var totalNum = "${pageInfo.total}"; 
		javaex.page({
			id : "page",
			pageCount : pageCount,	// 总页数
			currentPage : currentPage,// 默认选中第几页
		    //perPageCount : 20,	// 每页显示多少条，不填时，不显示该条数选择控件
			//isShowJumpPage : true,	// 是否显示跳页
		    //totalNum : 5,		// 总条数，不填时，不显示
			position : "center",
			callback : function(rtn) {
				console.log("当前选中的页数：" + rtn.pageNum);
				//console.log("每页显示条数：" + rtn.perPageCount);
				search(rtn.pageNum);
			}
		});	
		//将第几页的pageNum回传给Controller，然后遍历第几页的文章
		function search(pageNum){
			if(pageNum == undefined){
				pageNum = 1;
			}
			//文章分类
			var typeId = $("#type_id").val();
			//关键字检索
			var keyWord = $("#title").val();
			
			window.location.href="list_normal.action"
				+"?pageNum="+pageNum
				+"&typeId="+typeId
				+"&startDate="+startDate
				+"&endDate="+endDate
				+"&keyWord="+keyWord;
			
			
		}
		
		
		//批量提交按钮点击事件
		$("#submit").click(function(){
			var idArr = new Array();
			idArr=[];
			$(':checkbox[name="articleId"]:checked').each(function(){
				idArr.push($(this).val());
				console.log(idArr);
			});
			//判断至少选择一条记录
			if(idArr.length==0){
				javaex.optTip({
					content : "至少选择一条记录",
					type : "error"
				});
				return;
			}			
			//判断选择的哪一个单选框进行操作
			var opt = $(':radio[name="radio"]:checked').val();
			if(opt=="move"){
				console.log("move");
				//获取目标分类的typeId
				var typeId = $("#type_id2").val();
				
				 $.ajax({
					   url:"move.json",
					   type:"POST",
					   dataType:"json",
					   traditional:true,
					   data:{
			                  "idArr" : idArr,
			                  "typeId": typeId
			},
					   success:function(rtn){
						   if(rtn.code=="000000"){
			                	 javaex.optTip({
			             			content : rtn.message,
			             		});
			                	// 建议延迟加载
			 					setTimeout(function() {
			 						//跳转页面
			 					window.location.reload();
			 					}, 2000);			                	
			                 }else{
			                	 javaex.optTip({
			             			content : rtn.message,
			             			type : "error"
			             		});
			                 }
					   }
			   });

			}else if(opt=="recycle"){				
				console.log("recycle");
				//将文章放进回收站
				 $.ajax({
					   url:"update_status.json",
					   type:"POST",
					   dataType:"json",
					   traditional:true,
					   data:{
			                  "idArr" : idArr,
			                  "status": "0"
			},
					   success:function(rtn){
						   if(rtn.code=="000000"){
			                	 javaex.optTip({
			             			content : rtn.message,
			             		});
			                	// 建议延迟加载
			 					setTimeout(function() {
			 						//跳转页面
			 					window.location.reload();
			 					}, 2000);			                	
			                 }else{
			                	 javaex.optTip({
			             			content : rtn.message,
			             			type : "error"
			             		});
			                 }
					   }
			   });
			}
		}); 		
</script>
</html>  
  