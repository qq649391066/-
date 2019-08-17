<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  <div class="admin-aside admin-aside-fixed">
			<h1><span class="admin-nav-name">菜单</span></h1>
			<div id="admin-toc" class="admin-toc">
				<div class="menu-box">
					<div id="menu" class="menu">
						<ul>
							<li class="menu-item">
								<a href="javascript:;">分类管理<i class="icon-keyboard_arrow_left"></i></a>
								<ul>									
									<li><a href="${pageContext.request.contextPath}/type_info/list.action">文章分类</a></li>
								</ul>
							</li>
							<li class="menu-item">
								<a href="javascript:;">文章管理<i class="icon-keyboard_arrow_left"></i></a>
								<ul>
									<li><a href="${pageContext.request.contextPath}/article_info/list_normal.action">文章列表</a></li>
									<li><a href="${pageContext.request.contextPath}/article_info/list_recycle.action">回收站</a></li>
								</ul>
							</li>
						</ul>
					</div>
				</div>
			</div>
		</div>
<script>
var hightUrl = "xxxx";
javaex.menu({
	id : "menu",
	isAutoSelected : true,
	key : "",
	url : hightUrl
});

$(function() {
	// 设置左侧菜单高度
	setMenuHeight();
});

/**
 * 设置左侧菜单高度
 */
function setMenuHeight() {
	var height = document.documentElement.clientHeight - $("#admin-toc").offset().top;
	height = height - 10;
	$("#admin-toc").css("height", height+"px");
}
</script>