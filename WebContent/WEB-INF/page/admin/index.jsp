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
	<c:import url="./headerANDmenu/header.jsp"></c:import>	
	</div>
	</div>
	<!--主题内容-->
	<div class="admin-mian">
		<!--左侧菜单-->
			<c:import url="./headerANDmenu/left_menu.jsp"></c:import>			
	</div>
</body>
</html>