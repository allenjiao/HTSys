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
		<link rel="stylesheet" href="css/datepicker.css">
		
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
	<form action="equipment/editU.do" name="userForm" id="userForm" method="post">
		
		<input type="hidden" name="EQUIPMENT_ID" value="${pd.EQUIPMENT_ID }">
		
		<div id="zhongxin">
		<table style="width: 100%;">
			
			<tr>
				<td style="text-align: right;padding-right: 10px;">设备名称：</td>
				<td><input type="text" name="EQUIPMENT_NAME"   value="${pd.EQUIPMENT_NAME }" title="设备名称"/></td>
				<td style="text-align: right;padding-right: 10px;">设备类型：</td>
				<td style="vertical-align:top;">
					<select class="chzn-select" name="EQUIPMENT_TYPE_ID">
						<option value=""></option>
						<c:forEach items="${equipmentTypes}" var="equipmentTypes">
							<option value="${equipmentTypes.EQUIPMENT_TYPE_ID }" 
								<c:if test="${pd.EQUIPMENT_TYPE_ID==equipmentTypes.EQUIPMENT_TYPE_ID}">selected</c:if>>
							${equipmentTypes.EQUIPMENT_TYPE_NAME }
							</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td style="text-align: right;padding-right: 10px;">设备层次：</td>
				<td><input type="text" name="EQUIPMENT_LEVEL"   value="${pd.EQUIPMENT_LEVEL }" title="设备层次"/></td>
				<td style="text-align: right;padding-right: 10px;">检测标志：</td>
				<td><input type="text" name="MONITOR_FLAG"   value="${pd.MONITOR_FLAG }" title="检测标志"/></td>
			</tr>
			<tr>
				<td style="text-align: right;padding-right: 10px;">父设备：</td>
				<td style="vertical-align:top;">
					<select class="chzn-select" name="PARENT_EQUIPMENT_ID">
						<option value=""></option>
						<c:forEach items="${equipments}" var="equipments">
							<option value="${equipments.EQUIPMENT_ID }" 
								<c:if test="${pd.PARENT_EQUIPMENT_ID==equipments.EQUIPMENT_ID}">selected</c:if>>
								${equipments.EQUIPMENT_NAME }
							</option>
						</c:forEach>
					</select>
				</td>
				<td style="text-align: right;padding-right: 10px;">设备3D模型名称：</td>
				<td><input type="text" name="NAME_3D"   value="${pd.NAME_3D }" title="设备3D模型名称"/></td>
			</tr>
			<tr>
				<td style="text-align: right;padding-right: 10px;">设备状态：</td>
				<td><input type="text" name="STATUS"   value="${pd.STATUS }" title="设备状态"/></td>
				<td style="text-align: right;padding-right: 10px;">组织机构：</td>
				<td>
					<select class="chzn-select" id="AREA_ID"  name="AREA_ID" style="vertical-align:top;">
						<option value=""></option>
						<c:forEach items="${organs}" var="organ">
							<option value="${organ.ORGAN_ID }" title="${organ.doubleName}"
								<c:if test="${organ.ORGAN_ID == pd.AREA_ID }">selected</c:if>>
								<c:if test="${organ.ORGAN_LEVEL>1}">
									<c:forEach begin="1" end="${organ.ORGAN_LEVEL-1}">. </c:forEach>
								</c:if>
								${organ.doubleName }
							</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td style="text-align: right;padding-right: 10px;">IP地址：</td>
				<td><input type="text" name="IP"   value="${pd.IP }" title="IP地址"/></td>
				<td style="text-align: right;padding-right: 10px;">采集系统：</td>
 				<td style="vertical-align:top;">
 					<select class="chzn-select" name="GATHER_SYSTEM_ID">
 						<option value=""></option>
 						<c:forEach items="${systems}" var="systems">
 							<option value="${systems.SYSTEM_CODING }" 
 								<c:if test="${pd.GATHER_SYSTEM_ID == systems.SYSTEM_CODING}">selected="selected"</c:if>>
 								${systems.SUBSYSTEM_NAME}
 							</option>
 						</c:forEach>
					</select>
 				</td>
			</tr>
			<tr>
				<td style="text-align: right;padding-right: 10px;">端口：</td>
				<td><input type="text" name="PORT"   value="${pd.PORT }" title="端口"/></td>
				<td style="text-align: right;padding-right: 10px;">采集属性编码：</td>
				<td><input type="text" name="GATHER_ATTR_CODE"   value="${pd.GATHER_ATTR_CODE }" title="采集属性编码"/></td>
			</tr>
			<tr>
				<td style="text-align: right;padding-right: 10px;">地址码：</td>
				<td><input type="text" name="ADRESS_CODE"   value="${pd.ADRESS_CODE }" title="地址码"/></td>
				<td style="text-align: right;padding-right: 10px;">采集设备编码：</td>
				<td><input type="text" name="GATHER_DEV_CODE"   value="${pd.GATHER_DEV_CODE }" title="采集设备编码"/></td>
			</tr>
			<tr>
				<td style="text-align: right;padding-right: 10px;">姓名：</td>
				<td><input type="text" name="USER_NAME"   value="${pd.USER_NAME }" title="姓名"/></td>
				<td style="text-align: right;padding-right: 10px;">设备描述：</td>
				<td><input type="text" name="EQUIPMENT_DESC"   value="${pd.EQUIPMENT_DESC }" title="设备描述"/></td>
			</tr>
			<tr>
				<td style="text-align: right;padding-right: 10px;">密码：</td>
				<td><input type="password" name="PASSWORD"   value="${pd.PASSWORD }" title="密码"/></td>
				<td style="text-align: right;padding-right: 10px;">二维页面简称：</td>
				<td><input type="text" name="SHORT_NAME_2D"   value="${pd.SHORT_NAME_2D }" title="二维页面简称"/></td>
			</tr>
			<tr>
				<td style="text-align: right;padding-right: 10px;">通道号：</td>
				<td><input type="text" name="CHANNEL_NO"   value="${pd.CHANNEL_NO }" title="通道号"/></td>
				<td style="text-align: right;padding-right: 10px;">二维页面URL：</td>
				<td><input type="text" name="URL_2D"   value="${pd.URL_2D }" title="二维页面URL"/></td>
			</tr>
			<tr>
				<td style="text-align: right;padding-right: 10px;">产品序列号：</td>
				<td><input type="text" name="SN"   value="${pd.SN }" title="产品序列号"/></td>
				<td style="text-align: right;padding-right: 10px;">是否是该排的默认设备：</td>
				<td><input type="text" name="IS_DEFAULT"   value="${pd.IS_DEFAULT }" title="是否是该排的默认设备"/></td>
			</tr>
			<tr>
				<td style="text-align: right;padding-right: 10px;">顺序号：</td>
				<td><input type="text" name="SORT_NO"   value="${pd.SORT_NO }" title="顺序号"/></td>
				<td style="text-align: right;padding-right: 10px;">显示标志：</td>
				<td><input type="text" name="DISPLAY_FLAG"   value="${pd.DISPLAY_FLAG }" title="显示标志"/></td>
			</tr>
			<tr>
				<td style="text-align: right;padding-right: 10px;">资产编号：</td>
				<td><input type="text" name="ASSET_NO"   value="${pd.ASSET_NO }" title="资产编号"/></td>
				<td style="text-align: right;padding-right: 10px;">运行软件：</td>
				<td style="vertical-align:top;">
					<select class="chzn-select" id="BIZ_SYSTEM_ID"  name="BIZ_SYSTEM_ID">
						<option value=""></option>
						<c:forEach items="${softwares}" var="softwares">
							<option value="${softwares.SOFTWARE_ID }" 
								<c:if test="${softwares.SOFTWARE_ID == pd.BIZ_SYSTEM_ID}">selected</c:if>>
								${softwares.SOFTWARE_NAME}
							</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td style="text-align: right;padding-right: 10px;">设备型号：</td>
				<td><input type="text" name="EQUIPMENT_MODEL"   value="${pd.EQUIPMENT_MODEL }" title="设备型号"/></td>
				<td style="text-align: right;padding-right: 10px;">所属部门：</td>
				<td><input type="text" name="DEPARTMENT"   value="${pd.DEPARTMENT }" title="所属部门"/></td>
			</tr>
			<tr>
				<td style="text-align: right;padding-right: 10px;">报警状态：</td>
				<td style="vertical-align:top;">
					<select class="chzn-select" id="ALARM_STATUS"  name="ALARM_STATUS" title="报警状态">
						<option value=""></option>
						<option value="0" <c:if test="${pd.ALARM_STATUS==0 }">selected</c:if>>报警</option>
						<option value="1" <c:if test="${pd.ALARM_STATUS==1 }">selected</c:if>>未报警</option>
					</select>
				</td>
				<td style="text-align: right;padding-right: 10px;">上架时间：</td>
				<td><input class="span3 date-picker" name="PUTAWAY_TIME" value="${pd.PUTAWAY_TIME}" 
					type="text" data-date-format="yyyy-mm-dd"/>
				</td>
			</tr>
			<tr>
				<td style="text-align: right;padding-right: 10px;">设备位置：</td>
				<td>
					<input type="text" name="POSITION_ID"   value="${pd.POSITION_ID }"/>
				</td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td></td>
				<td style="text-align: right;">
					<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
				</td>
				<td style="text-align: left;">
					<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
				</td>
				<td></td>
			</tr>
		</table>
		
		</div>
		
		<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/>
			<img src="images/jiazai.gif" /><br/><h4 class="lighter block green"></h4></div>
		
	</form>
	
	
		<!-- 引入 -->
		<script src="1.9.1/jquery.min.js"></script>
		<script type="text/javascript">window.jQuery || document.write("<script src='js/jquery-1.9.1.min.js'>\x3C/script>");</script>
		<script src="js/bootstrap.min.js"></script>
		<script src="js/ace-elements.min.js"></script>
		<script src="js/ace.min.js"></script>
		<script type="text/javascript" src="js/chosen.jquery.min.js"></script><!-- 单选框 -->
		<script type="text/javascript" src="js/bootstrap-datepicker.min.js"></script><!-- 日期框 -->
		<script type="text/javascript" src="js/bootbox.min.js"></script><!-- 确认窗口 -->
		
		<!--引入弹窗组件start-->
		<script type="text/javascript" src="js/attention/zDialog/zDrag.js"></script>
		<script type="text/javascript" src="js/attention/zDialog/zDialog.js"></script>
		<!--引入弹窗组件end-->
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