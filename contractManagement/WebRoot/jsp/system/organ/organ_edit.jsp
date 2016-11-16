<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<base href="<%=basePath%>">
		
		<meta charset="utf-8" />
		<title></title>
		
		<meta name="description" content="overview & stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<link href="css/bootstrap.min.css" rel="stylesheet" />
		<link href="css/bootstrap-responsive.min.css" rel="stylesheet" />
		<link rel="stylesheet" href="css/font-awesome.min.css" />
		<!--[if IE 7]><link rel="stylesheet" href="css/font-awesome-ie7.min.css" /><![endif]-->
		<!--[if lt IE 9]><link rel="stylesheet" href="css/ace-ie.min.css" /><![endif]-->
		
		<!-- 单选框 -->
		<link rel="stylesheet" href="css/chosen.css" />
		
		<link rel="stylesheet" href="css/ace.min.css" />
		<link rel="stylesheet" href="css/ace-responsive.min.css" />
		<link rel="stylesheet" href="css/ace-skins.min.css" />
		
		<script type="text/javascript" src="js/jquery-1.7.2.js"></script>
		<script type="text/javascript" src="js/chosen.jquery.min.js"></script><!-- 单选框 -->
</head>

<script type="text/javascript">
	$(function() {
		//单选框
		$(".chzn-select").chosen(); 
		$(".chzn-select-deselect").chosen({allow_single_deselect:true}); 
		//日期框
		$('.date-picker').datepicker();
	});
	//保存
	function save(){
		if($("#ORGAN_NAME").val()==""){
			$("#ORGAN_NAME").focus();
			return false;
		}
		
		if($("#ORGAN_ORDER").val()==""){
			$("#ORGAN_ORDER").focus();
			return false;
		}
		
		if(isNaN(Number($("#ORGAN_ORDER").val()))){
			$("#ORGAN_ORDER").focus();
			$("#ORGAN_ORDER").val(1);
			return false;
		}
		
		if($("#ORGAN_TYPE").val()==""){
			$("#ORGAN_TYPE").focus();
			return false;
		}
		
		$("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}
	
</script>


<body>
	<form  action="organ/save.do" name="Form" id="Form" method="post" >
		<input type="hidden" name="ORGAN_ID" id="ORGAN_ID" value="${pd.ORGAN_ID }"/>
		<input type="hidden" name="PARENT_ID" id="PARENT_ID" value="${pd.PARENT_ID }"/>
		<div id="zhongxin">
		<table>
			<tr class="info">
				<td>
					<select class="chzn-select" name="ORGAN_TYPE" data-placeholder="请选择类型" title="类型">
						<option value="1"<c:if test="${pd.ORGAN_TYPE==1}"> selected="selected"</c:if>>机构</option>
						<option value="2"<c:if test="${pd.ORGAN_TYPE==2}"> selected="selected"</c:if>>部门</option>
						<option value="3"<c:if test="${pd.ORGAN_TYPE==3}"> selected="selected"</c:if>>区域</option>
					</select>
				</td>
			</tr>
			<tr class="info">
				<td><input type="text" name="ORGAN_NAME" id="ORGAN_NAME" placeholder="这里输入名称" value="${pd.ORGAN_NAME }" title="名称"/></td>
			</tr>
			<tr class="info">
				<td>
					<input type="text" name="ORGAN_DESC" id="ORGAN_DESC" placeholder="这里输入描述" value="${pd.ORGAN_DESC}" title="描述"/>
				</td>
			</tr>
			<tr class="info">
				<td>
					<input type="number" name="ORGAN_ORDER" id="ORGAN_ORDER" placeholder="这里输入序号" value="${pd.ORGAN_ORDER}" title="序号"/>
				</td>
			</tr>
			<tr>
				<td style="text-align: center;">
					<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
					<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
				</td>
			</tr>
		</table>
		</div>
		<div id="zhongxin2" class="center" style="display:none">
			<br/><br/><br/><img src="images/jzx.gif" style="width: 50px;" /><br/><h4 class="lighter block green"></h4>
		</div>
	</form>
</body>
</html>