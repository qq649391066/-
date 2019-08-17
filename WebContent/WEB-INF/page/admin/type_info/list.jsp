<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>     
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>   
<meta charset="utf-8">
<title>管理页面</title>
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
		   <span>分类管理</span>
		   <span class="divider">/</span>
		   <span class="active">文章分类</span>
	    </div>
			<!--  <iframe id="page" src="../page/admin/type_info/iframe-page.jsp"></iframe>  -->
			<div class="list-content">
			<!--块元素-->
	<div class="block">
		<!--页面有多个表格时，可以用于标识表格-->
		<h2>文章分类列表</h2>	
		<!--正文内容-->
		<div class="main">						
			<!--表格上方的操作元素，添加、删除等-->
			<div class="operation-wrap">
				<div class="buttons-wrap">
					<button id="add" class="button blue radius-3"><span class="icon-plus"></span> 添加</button>
					<button id="delete" class="button red radius-3"><span class="icon-close2"></span> 删除</button>
					<button id="save" class="button green radius-3"><span class="icon-check_circle"></span> 保存</button>
				</div>
			</div>
			<table id="table" class="table ">
				<thead>
					<tr>
						<th class="checkbox"><input type="checkbox" class="fill  listen-1" /> </th>
						<th>显示排序</th>
						<th>名称</th>						
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${list}" var="entity" varStatus="status">
                       <tr>
			            <td><input type="checkbox" class="fill   listen-1-2" name="typeId" value="${entity.typeId}"/> </td>
			            <td><input type="text" class="text" name="typeSort" data-type="正整数" error-msg="必须输入正整数" value="${entity.typeSort}"/></td>
			            <td><input type="text" class="text" name="typeName" data-type="必填的哦！"  value="${entity.typeName}"/></td>
                       </tr>
                    </c:forEach>

				</tbody>
			</table>
		</div>
	</div>
			</div>						
		</div>
	</div>
</body>
<script>
    var idArr = new Array();
    var sortArr = new Array();
    var nameArr = new Array();
	/**
	 *设置添加按钮的功能
	 */
	$("#add").click(function(){
		var html='';
		html +='<tr>';
		html +='<td><input type="checkbox" class="fill   listen-1-2" name="typeId" value="${entity.typeId}"/> </td>';
		html +='<td><input type="text" class="text" name="typeSort" data-type="正整数" error-msg="必须输入正整数" value="${entity.typeSort}"/></td>';
        html +='<td><input type="text" class="text" name="typeName" data-type="必填的哦！"  placeholder="请输入分类名称" value=""/></td>';
		html +='</tr>';
		
		$("#table tbody").append(html);
		//javaex框架的重新渲染(重新加载样式,不然上面新添加的文本没有样式效果)
		javaex.render();
	});
	
	/**
	 *设置保存按钮功能
	 */
	 $("#save").click(function(){
		 if(javaexVerify()){
			 idArr = [];
			 sortArr = [];
			 nameArr = [];		 
			 //获取typeId
			 $(':checkbox[name="typeId"]').each(function(){
				 idArr.push($(this).val());
			 });
			 //获取typeSort
			 $('input[name="typeSort"]').each(function(){
				 sortArr.push($(this).val());
			 });
			 //获取typeName
			 $('input[name="typeName"]').each(function(){
				 nameArr.push($(this).val());
			 });
			// console.log("idArr"+idArr);
			// console.log("sortArr"+sortArr);
			// console.log("nameArr"+nameArr);
			 $.ajax({
				         url:"save.json",		             
			             type:"POST",
			             dataType: "json",
			             data:{
			            	 "idArr" : idArr,
			            	 "sortArr" : sortArr,
			            	 "nameArr" : nameArr,
			             },
			             traditional:true, //默认false
			             success:function(rtn){
			                 console.log(rtn);
			                 if(rtn.code=="000000"){
			                	 javaex.optTip({
			             			content : rtn.message,
			             		});
			                	// 建议延迟加载
			 					setTimeout(function() {
			 						//刷新页面
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
	   /**
		 *设置删除按钮功能
		 */
	$("#delete").click(function(){
		idArr = [];
		//遍历获取被选中的复选框
		$(':checkbox[name="typeId"]:checked').each(function(){
			//添加被选中的复选框中含有typeId进数组
			var delTypeId = $(this).val();
			if(delTypeId != ""){
				idArr.push(delTypeId);
			}					
		});
		//判断所勾选的是不是新增的内容
		if(idArr.length==0){
			$(':checkbox[name="typeId"]:checked').each(function(){
				//前台无刷新去除新增的tr
				$(this).parent().parent().parent().remove();
			})
		}else{
			$.ajax({
		         url:"delete.json",		             
	             type:"POST",
	             dataType: "json",
	             data:{
	            	 "idArr" : idArr,	            	
	             },
	             traditional:true, //默认false
	             success:function(rtn){
	                 console.log(rtn);
	                 if(rtn.code=="000000"){
	                	 javaex.optTip({
	             			content : rtn.message,
	             		});
	                	// 建议延迟加载
	 					setTimeout(function() {
	 						//刷新页面
	 						 window.location.reload();
	 					}, 2000);			                	
	                 }else{
	                	 javaex.optTip({
	             			content : rtn.message,
	             			live:4000,
	             			type : "error"
	             		});
	                 }
	             }
	         });
		}
		
	});
</script>
</html>  
  