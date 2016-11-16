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
	if($("#user_id").val()!=""){
		$("#loginname").attr("readonly","readonly");
		$("#loginname").css("color","gray");
	}
});
	
	//保存
	function save(){
		$("#userForm").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
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
<body>
	<form action="equipment/editU2.do" name="userForm" id="userForm" method="post">
		<input type="hidden" name="EQUIPMENT_ID" id="user_id" value="${pd.EQUIPMENT_ID }"/>
		<input type="hidden" name="POSITION_ID" id="user_id" value="${pd.POSITION_ID }"/>
		<div id="zhongxin">
		<table>
			
			<tr>
				<td style="text-align: right;padding-right: 10px;">位置名称：</td>
				<td><input type="text" name="POSITION_NAME"   value="${pd.POSITION_NAME }" placeholder="这里输入位置名称" title="位置名称"/></td>
			</tr>
			<tr>
				<td style="text-align: right;padding-right: 10px;">图层：</td>
				<td><input type="text" name="MAP_ID"   value="${pd.MAP_ID }" placeholder="这里输入图层" title="图层"/></td>
			</tr>
			<tr>
				<td style="text-align: right;padding-right: 10px;">2DX：</td>
				<td><input type="text" name="X_2D"   value="${pd.X_2D }" placeholder="这里输入2DX" title="2DX"/></td>
			</tr>
			<tr>
				<td style="text-align: right;padding-right: 10px;">2DY：</td>
				<td><input type="text" name="Y_2D"   value="${pd.Y_2D }" placeholder="这里输入2DY" title="2DY"/></td>
			</tr>
			<tr>
				<td style="text-align: right;padding-right: 10px;">2DZ：</td>
				<td><input type="text" name="Z_2D"   value="${pd.Z_2D }" placeholder="这里输入2DZ" title="2DZ"/></td>
			</tr>
			<tr>
				<td style="text-align: right;padding-right: 10px;">H：</td>
				<td><input type="text" name="H"   value="${pd.H }" placeholder="这里输入H" title="H"/></td>
			</tr>
			<tr>
				<td style="text-align: right;padding-right: 10px;">P：</td>
				<td><input type="text" name="P"   value="${pd.P }" placeholder="这里输入P" title="P"/></td>
			</tr>
			<tr>
				<td style="text-align: right;padding-right: 10px;">R：</td>
				<td><input type="text" name="R"   value="${pd.R }" placeholder="这里输入R" title="R"/></td>
			</tr>
			<tr>
				<td style="text-align: right;padding-right: 10px;">3DX：</td>
				<td><input type="text" name="X_3D"   value="${pd.X_3D }" placeholder="这里输入3DX" title="3DX"/></td>
			</tr>
			
			<tr>
				<td style="text-align: right;padding-right: 10px;">3DY：</td>
				<td><input type="text" name="Y_3D"   value="${pd.Y_3D }" placeholder="这里输入3DY" title="3DY"/></td>
			</tr>
			<tr>
				<td style="text-align: right;padding-right: 10px;">3DZ：</td>
				<td><input type="text" name="Z_3D"   value="${pd.Z_3D }" placeholder="这里输入3DZ" title="3DZ"/></td>
			</tr>
			<tr>
				<td style="text-align: right;padding-right: 10px;">水平视角值：</td>
				<td><input type="text" name="H_ANGLE_VALUE"   value="${pd.H_ANGLE_VALUE }" placeholder="这里输入水平视角值" title="水平视角值"/></td>
			</tr>
			<tr>
				<td style="text-align: right;padding-right: 10px;">垂直视角值：</td>
				<td><input type="text" name="V_ANGLE_VALUE"   value="${pd.V_ANGLE_VALUE }" placeholder="这里输入垂直视角值" title="垂直视角值"/></td>
			</tr>
			<tr>
				<td style="text-align: right;padding-right: 10px;">可视距离：</td>
				<td><input type="text" name="VISUAL_RANGE"   value="${pd.VISUAL_RANGE }" placeholder="这里输入可视距离" title="可视距离"/></td>
			</tr>
			<tr>
				<td style="text-align: right;padding-right: 10px;">2DX_SIZE：</td>
				<td><input type="text" name="X_2D_SIZE"   value="${pd.X_2D_SIZE }" placeholder="这里输入2DX_SIZE" title="2DX_SIZE"/></td>
			</tr>
			<tr>
				<td style="text-align: right;padding-right: 10px;">2DY_SIZE：</td>
				<td><input type="text" name="Y_2D_SIZE"   value="${pd.Y_2D_SIZE }" placeholder="这里输入2DY_SIZE" title="2DY_SIZE"/></td>
			</tr>
			<tr>
				<td style="text-align: right;padding-right: 10px;">所属排：</td>
				<td><input type="text" name="ROW_NO"   value="${pd.ROW_NO }" placeholder="这里输入所属排" title="所属排"/></td>
			</tr>
			<tr>
				<td style="text-align: right;">
					<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
				</td>
				<td style="text-align: left;">
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
			$(".chzn-select").chosen(); 
			$(".chzn-select-deselect").chosen({allow_single_deselect:true}); 
			
			//日期框
			$('.date-picker').datepicker();
			
		});
		
		</script>
	
</body>
</html>