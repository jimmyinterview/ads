<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<style type="text/css">
#myCarousel {
	margin: 0 auto;
	width: 500px;
}
</style>
<script type="text/javascript">
	$(function() {
		$('#myCarousel').carousel({
			interval : 500
		});
	});
</script>
<div class="">
	<div class="modal-header">
		<button aria-hidden="true" data-dismiss="modal" class="close"
			type="button">
			<span aria-hidden="true">&times;</span>
		</button>
		<h4 class="modal-title">广告牌信息</h4>
	</div>
	<form method="post" class="form-horizontal" id="updateGgpForm"
		action="<%=path%>/ggp/updateGgp.do">
		<div class="modal-body">
			<div class="row form-group">
				<div class="col-md-3 control-label">
					<label>广告牌描述：</label>
				</div>
				<div class="col-md-7 controls">
					<input type="text" class="form-control" id="updatems"
						name="updatems" value="${ggp.ms }"><label id="msInfo"
						class="errorInfo">*描述不能为空</label> <input type="hidden" name="id"
						value="${ggp.id }">
				</div>
			</div>
			<div class="row form-group">
				<div class="col-md-3 control-label">
					<label>广告牌类型：</label>
				</div>
				<div class="col-md-7 controls">
					<div class="input-group">
						<select class="form-control" id="lx" name="lx">
							<c:forEach items="${ggpTypeList}" var="gglx">
								<option value="${gglx.id}"
									<c:if test="${ggp.lx.id==gglx.id}">selected</c:if>>${gglx.mc}</option>
							</c:forEach>
						</select> <label id="ggpTypeInfo" class="errorInfo">*必须选择一个类型</label>
					</div>
				</div>
			</div>
			<div class="row form-group">
				<div class="col-md-3 control-label">
					<label>广告牌使用状态：</label>
				</div>
				<div class="col-md-7 controls">
					<div class="input-group">
						<select class="form-control" id="lx" name="zt">
							<option value="1" <c:if test="${ggp.zt==1}">selected</c:if>>空闲</option>
							<option value="2" <c:if test="${ggp.zt==2}">selected</c:if>>使用中</option>
						</select> <label id="ztInfo" class="errorInfo">*必须选择一个类型</label>
					</div>
				</div>
			</div>
			<div class="row form-group">
				<div class="col-md-3 control-label">
					<label>日价格：</label>
				</div>
				<div class="col-md-7 controls">
					<input type="text" class="form-control" id="updatejg"
						name="updatejg" value="${ggp.jg }"> <label id="jgInfo"
						class="errorInfo">*价格格式不正确</label>
				</div>
			</div>
			<div class="row form-group">
				<div class="col-md-3 control-label">
					<label>添加人：</label>
				</div>
				<div class="col-md-7 controls">
					<input type="text" class="form-control" id="tjr" name="tjr"
						value="${ggp.tjry }" readonly="true" />
				</div>
			</div>
		</div>
	</form>

	<div id="myCarousel" class="carousel slide">
		<ol class="carousel-indicators">
			<c:forEach items="${requestScope.ggptpList}" var="ggptp"
				varStatus="s">
				<li data-target="#myCarousel" data-slide-to="${s.index}"
					<c:if test="${s.index==0}">class="active"</c:if>></li>
			</c:forEach>
		</ol>
		<div class="carousel-inner">
			<c:forEach items="${requestScope.ggptpList}" var="ggptp"
				varStatus="s">
				<div <c:choose><c:when test="${s.index==0}">class="active item"</c:when>
				<c:otherwise>class="item"</c:otherwise> </c:choose>>
					<img src="<%=basePath%>${ggptp.url}" />
				</div>
			</c:forEach>
		</div>
	</div>

	<div class="modal-footer">
		<button id="updatesubnitBtn" class="btn btn-primary pull-right"
			type="button" onclick="submitGgpUpdate('updateGgpForm',false)">提交</button>
		<button data-dismiss="modal" class="btn btn-link pull-right"
			type="button">取消</button>
	</div>
</div>
