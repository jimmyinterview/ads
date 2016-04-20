<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<style type="text/css">
   .thumbnail img {
    weight:auto;
    height:100px;
}
</style>


<base href="<%=basePath%>">
<div class="row">
	<c:forEach items="${pageResult.records}" var="ggtp">
		<div class="col-sm-6 col-md-3">
			<div class="thumbnail">
				<img src="<%=basePath%>${ggtp.url}" alt="${ggtp.ggp.ms}">
				<div class="caption">
					<p>广告牌描述:${ggtp.ggp.ms}</p>
					<p>
						<a href="ggp/deleteGgpTp.do?id=${ggtp.id}" class="btn btn-default" role="button"> 删除 </a>
					</p>
				</div>
			</div>
		</div>
	</c:forEach>
</div>
<!-- 分页 -->
<div class="col-sm-12">
	<input type="hidden" name="pageSize" id="pageSize"
		value="${pageResult.page.pageLength}" /> <input type="hidden"
		name="curPage" id="curPage" value="${pageResult.page.currentPage}" />
	<input type="hidden" name="totPage" id="totPage"
		value="${pageResult.totPage}" /> <input type="hidden" name="totCount"
		id="totCount" value="${pageResult.totalRecords}" />
	<div class="pagination-main">
		<ul id="pagination" class="pagination"></ul>
		<span class="page-list min-dply-none"></span>
	</div>
</div>
