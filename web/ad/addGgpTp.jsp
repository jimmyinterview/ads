<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<script type="text/javascript">
	$(document).ready(function() {
		initFileInput("moreFile", "<%=basePath%>/ggp/uploadMultipleFile.do");
	});
	
	//初始化fileinput控件（第一次初始化）
	function initFileInput(ctrlName, uploadUrl) {
		var control = $('#' + ctrlName);
		control.fileinput({
			language : 'zh', //设置语言
			uploadUrl : uploadUrl, //上传的地址
			allowedFileExtensions : [ 'jpg', 'png', 'gif' ],//接收的文件后缀
			maxFileCount: 100,
            enctype: 'multipart/form-data',
			showUpload : true, //是否显示上传按钮
			showCaption : false,//是否显示标题
			browseClass : "btn btn-primary", //按钮样式             
			previewFileIcon : "<i class='glyphicon glyphicon-king'></i>",
			msgFilesTooMany: "选择上传的文件数量({n}) 超过允许的最大数值{m}！",
		});
		$(control).on("fileuploaded", function(event, data, previewId, index) {
			if('ERROR'==data.response.status)
			{
				alert(data.response.statusMsg);
			}
		});
	}
</script>
<div  class="well" style="height:auto">
	<form method="post" class="form-horizontal" action="<%=basePath%>ggp/saveGgpTp.do"
		id="ggpTpAddForm" enctype="multipart/form-data" method="post">
		<div class="modal-body" style="height:100%">
			<div class="row form-group">
				<div class="col-md-2 control-label">
					<label>广告牌类型：</label>
				</div>
				<div class="input-group">
					<select class="form-control" id="lx" name="lx">
						<option value="-1">请选择</option>
						<c:forEach items="${ggpList}" var="ggp">
							<option value="${ggp.id}">${ggp.ms}</option>
						</c:forEach>
					</select> <label id="ggpTypeInfo" class="errorInfo">*必须选择一个广告牌</label>
				</div>
			</div>

			<div class="row form-group">
				<div class="col-md-2 control-label">
					<label>广告牌图片上传：</label>
				</div>
			</div>
			<div  class="form-group" style="position:static;float:none;clear:both;">
				<input id="moreFile" name="moreFile" type="file" multiple data-preview-file-type="any" >
			</div>
			<div class="modal-footer" style="position:static;float:nono;clear:both;margin-top:20px;">
				<button id="subnitBtn" class="btn btn-primary pull-left"
					type="button" onclick="submitGgpTpAdd('ggpTpAddForm',true)">提交</button>
			</div>
		</div>
	</form>
</div>