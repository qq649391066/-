<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="grid-1-3 container">
	<div id="logo">
		<a href="javascript:;">刘镇波的博客</a>
	</div>
	<div id="nav">
		<ul class="nav">
			<li><a href="${pageContext.request.contextPath}/portal/index.action">首页</a></li>
			<li style="position: relative;">
				<a href="javascript:;">文章目录</a>
				<ul id="typeList" class="classified-nav">
				<li><a href="${pageContext.request.contextPath}/portal/index.action">所有文章</a>
				</ul>
			</li>
			<li><a href="#">关于我</a></li>
			<li><a href="javascript:;" onClick="openSearch()"><span class="icon-search" style="font-size: 14px;"></span></a></li>
		</ul>
		<div id="search" class="hide">
			<span class="search-field-wrapper form-group">
				<input type="text" class="search" placeholder="搜索…" value="" name="">
			</span>
			<span id="close-search" class="icon-close" style="font-size: 16px;"></span>
		</div>
	</div>
</div>
<script>
  //页面预加载就向后台请求文章分类的数据
  $(function(){
	 $.ajax({
		 //请求地址
		 url:"${pageContext.request.contextPath}/portal/get_type.json",
		 //请求类型
		 type:"POST",
		 //回传数据类型
		 dataType:"json",
		 //传送到服务端的数据
		 data:{},
		 //成功的回调函数
		 success:function(rtn){
			 console.log(rtn);
			 var typeList = rtn.data.typeList;
			 var html='';			
			 for (var i = 0;i<typeList.length;i++){
				 html += '<li><a href="${pageContext.request.contextPath}/portal/type.action?typeId='+typeList[i].typeId+'">'+typeList[i].typeName+'</a></li>';
			 }
			 $('#typeList').append(html);
		 }
	 });
  });
</script>