<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%
	String path = request.getContextPath();
%>
<div class="row">
	<div class="col-md-12">
		<c:forEach items="${rankList }" var="rank" varStatus="s">
			<div class="media mg-tp10">
				<div class="media-body pull-left zx-max-w text-els text-prwp">
					<span class="badge tip-bg-3">${s.index+1 }</span> <a href="#">${rank.name
						}（销售额${rank.count }￥）</a>
				</div>
				<div class="media-right pull-right text-nowrap">
					<span class="text-muted ft-aa ft-sz12">${rank.kssj } -
						${rank.jssj }</span>
				</div>
			</div>
			<hr class="mg-tp5 mg-bm0 hr-dsd">
		</c:forEach>
	</div>
</div>
