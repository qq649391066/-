<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="grid-1-3 container">
	<div style="text-align: left;">
		<ul class="equal-4">
			<li><a href="#">联系我</a></li>
			<li id="error"><a href="#">友情链接</a></li>
			<li><a href="${pageContext.request.contextPath}/admin/index.action">后台管理</a></li>
		</ul>
	</div>
	<div style="text-align: right;color: #ccc;">联系方式:649391066@qq.com</div>
</div>
<script>
$("#error").click(function() {
	javaex.optTip({
		content : "该功能尚未开发，请谅解~",
		type : "error"
	});
});
</script>