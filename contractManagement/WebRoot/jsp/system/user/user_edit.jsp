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
		
<script type="text/javascript">
	
$(document).ready(function(){
	if($("#user_id").val()!=""){// 不允许修改登录用户名
		$("#loginname").attr("readonly","readonly");
		$("#loginname").css("color","gray");
	}
});
	
	//保存
	function save(){
		if($("#role_id").val()==""){
			$("#role_id").focus();
			return false;
		}
		if($("#USER_TYPE").val()==""){
			$("#USER_TYPE").focus();
			return false;
		}
		if($("#loginname").val()=="" || $("#loginname").val()=="此用户名已存在!"){
			$("#loginname").focus();
			$("#loginname").val('');
			$("#loginname").css("background-color","white");
			return false;
		}else{
			$("#loginname").val(jQuery.trim($('#loginname').val()));
		}

		if($("#user_id").val()=="" && $("#password").val()==""){
			$("#password").focus();
			return false;
		}
		if($("#password").val()!=$("#chkpwd").val()){
			alert("两次密码不相同!");
			$("#password").focus();
			return false;
		}
		if($("#name").val()==""){
			$("#name").focus();
			return false;
		}
		if($("#user_id").val()==""){
			hasU();
		}else{
			$("#userForm").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}
	}
	
	//判断用户名是否存在
	function hasU(){
		var USERNAME = $("#loginname").val();
		var url = "user/hasU.do?USERNAME="+USERNAME+"&tm="+new Date().getTime();
		$.get(url,function(data){
			if(data=="error"){
				$("#loginname").css("background-color","#D16E6C");
				
				setTimeout("$('#loginname').val('此用户名已存在!')",500);
				
			}else{
				$("#userForm").submit();
				$("#zhongxin").hide();
				$("#zhongxin2").show();
			}
		});
	}
	
</script>
	</head>
<body style="overflow-x: hidden;">
	<form action="user/${msg }.do" name="userForm" id="userForm" method="post">
		<input type="hidden" name="USER_ID" id="user_id" value="${pd.USER_ID }"/>
		<div id="zhongxin">
		<table style="width: 100%;">
			
			<c:if test="${fx != 'head'}">
			<c:if test="${pd.ROLE_ID != '1'}">	
			<tr class="info">
				<td>
				<select class="chzn-select" name="ROLE_ID" id="role_id" data-placeholder="请选择职位"
					style="vertical-align:top;width: 99%;">
					<option value=""></option>
					<c:forEach items="${roleList}" var="role">
						<option value="${role.ROLE_ID }" <c:if test="${role.ROLE_ID == pd.ROLE_ID }">selected</c:if>>${role.ROLE_NAME }</option>
					</c:forEach>
				</select>
				</td>
			</tr>
			<tr class="info">
				<td>
				<select class="chzn-select" name="USER_TYPE" id="USER_TYPE" data-placeholder="请选择用户类型"
					style="vertical-align:top;width: 99%;">
					<option value=""></option>
					<option value="0" <c:if test="${0 == pd.USER_TYPE }">selected</c:if>>管理用户</option>
					<option value="1" <c:if test="${1 == pd.USER_TYPE }">selected</c:if>>普通用户</option>
				</select>
				</td>
			</tr>
			<tr class="info">
				<td>
				<select class="chzn-select" name="ORGAN_ID" id="ORGAN_ID" data-placeholder="请选择组织机构"
					style="vertical-align:top;width: 99%;">
					<option value=""></option>
					<c:forEach items="${organList}" var="organ">
						<option value="${organ.ORGAN_ID }" title="${organ.doubleName}"
							<c:if test="${organ.ORGAN_ID == pd.ORGAN_ID }">selected</c:if>>
							<c:if test="${organ.ORGAN_LEVEL>1}">
								<c:forEach begin="1" end="${organ.ORGAN_LEVEL-1}">. </c:forEach>
							</c:if>
							${organ.doubleName }
						</option>
					</c:forEach>
				</select>
				</td>
			</tr>
			</c:if>
			<c:if test="${pd.ROLE_ID == '1'}">
			<input name="ROLE_ID" id="role_id" value="1" type="hidden" />
			</c:if>
			</c:if>
			
			<c:if test="${fx == 'head'}">
				<input name="ROLE_ID" id="role_id" value="${pd.ROLE_ID }" type="hidden" />
				<input name="ORGAN_ID" id="ORGAN_ID" value="${pd.ORGAN_ID }" type="hidden" />
				<input name="USER_TYPE" id="USER_TYPE" value="${pd.USER_TYPE }" type="hidden" />
			</c:if>
			
			<tr class="info">
				<td>
					<input type="text" name="USERNAME" id="loginname" value="${pd.USERNAME }" placeholder="这里输入用户名" title="用户名"
						style="width: 95%;"/>
				</td>
			</tr>
			<tr>
				<td><input type="password" name="PASSWORD" id="password"  placeholder="输入密码" title="密码" style="width: 95%;"/></td>
			</tr>
			<tr>
				<td><input type="password" name="chkpwd" id="chkpwd"  placeholder="确认密码" title="确认密码" style="width: 95%;"/></td>
			</tr>
			<tr>
				<td>
					<input type="text" name="NAME" id="name"  value="${pd.NAME }" placeholder="这里输入姓名" title="姓名" style="width: 95%;"/>
				</td>
			</tr>
			<tr>
				<td><input type="text" name="BZ" id="BZ"value="${pd.BZ }" placeholder="这里输入备注" title="备注" style="width: 95%;"/></td>
			</tr>
			<tr>
				<td style="text-align: center;">
					<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
					<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
				</td>
			</tr>
		</table>
		</div>
		
		<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><img src="images/jiazai.gif" /><br/><h4 class="lighter block green"></h4></div>
		
	</form>
	
	
		<!-- 引入 -->
		<script src="1.9.1/jquery.min.js"></script>
		<script type="text/javascript">window.jQuery || document.write("<script src='js/jquery-1.9.1.min.js'>\x3C/script>");</script>
		<script src="js/bootstrap.min.js"></script>
		<script src="js/ace-elements.min.js"></script>
		<script src="js/ace.min.js"></script>
		<script type="text/javascript" src="js/chosen.jquery.min.js"></script><!-- 单选框 -->
		
		<script type="text/javascript">
		
		$(function() {
			
			//单选框
			$(".chzn-select").chosen({allow_single_deselect:true}); 
			$(".chzn-select-deselect").chosen({allow_single_deselect:true}); 
			
			//日期框
			$('.date-picker').datepicker();
			
		});
		
		</script>
	
</body>
</html>