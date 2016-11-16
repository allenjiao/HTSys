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
		<link rel="stylesheet" href="css/ace.min.css" />
		<link rel="stylesheet" href="css/ace-responsive.min.css" />
		<link rel="stylesheet" href="css/ace-skins.min.css" />
		
		
		<script type="text/javascript" src="js/jquery-1.7.2.js"></script>	

		<script type="text/javascript">
				$(document).ready(function(){
					if($("#menuId").val()!=""){
						var parentId = $("#pId").val();
						if(parentId=="0"){
							$("#parentId").attr("disabled",true);
						}else{
							$("#parentId").val(parentId);
						}
					}
				});
				
				var menuUrl = "";
				function setMUR(){
					menuUrly = $("#menuUrl").val();
					if(menuUrly != ''){menuUrl = menuUrly;}
					if($("#parentId").val()=="0"){
						$("#menuUrl").attr("readonly",true);
						$("#menuUrl").val("");
					}else{
						$("#menuUrl").attr("readonly",false);
						$("#menuUrl").val(menuUrl);
					}
				}
				
				//保存
				function save(){
					if($("#menuName").val()==""){
						$("#menuName").focus();
						return false;
					}
					if($("#menuOrder").val()==""){
						$("#menuOrder").focus();
						return false;
					}
					
					if(isNaN(Number($("#menuOrder").val()))){
						$("#menuOrder").focus();
						$("#menuOrder").val(1);
						return false;
					}
					
					$("#menuForm").submit();
					$("#zhongxin").hide();
					$("#zhongxin2").show();
				}
		</script>
		
	</head>
	
	<body>
		<form action="menu/edit.do" name="menuForm" id="menuForm" method="post">
			<input type="hidden" name="MENU_ID" id="menuId" value="${pd.MENU_ID}"/>
			<input type="hidden" name="pId" id="pId" value="${pd.PARENT_ID}"/>
			<div id="zhongxin">
			<table>
				<tr>
					<td>
						<select name="PARENT_ID" id="parentId" onchange="setMUR()" title="菜单">
							<option value="0">顶级菜单</option>
							<c:forEach items="${menuList}" var="menu">
								<option value="${menu.MENU_ID }">${menu.MENU_NAME }</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				
				<tr class="info">
					<td><input type="text" name="MENU_NAME" id="menuName" placeholder="这里输入菜单名称" value="${pd.MENU_NAME }" title="名称"/></td>
				</tr>
				<tr class="info">
					<td><input type="text" name="MENU_URL" id="menuUrl" placeholder="这里输入链接地址" value="${pd.MENU_URL }" title="地址"/></td>
				</tr>
				<tr class="info">
					<td><input type="number" name="MENU_ORDER" id="menuOrder" placeholder="这里输入序号" value="${pd.MENU_ORDER}" title="序号"/></td>
				</tr>
				<tr>
				<td style="text-align: center;">
					<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
					<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
				</td>
			</tr>
			</table>
			</div>
			<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><img src="images/jiazai.gif" /><br/><h4 class="lighter block green"></h4></div>
		</form>
	</body>
</html>