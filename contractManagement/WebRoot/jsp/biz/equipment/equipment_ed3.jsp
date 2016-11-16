<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
<body style="overflow: scroll;" >
	<form action="equipment/editU3.do" name="userForm" id="userForm" method="post">
		<input type="hidden" name="EQUIPMENT_ID" id="user_id" value="${pd.EQUIPMENT_ID}"/>
		<div id="zhongxin">
		<div style="float: right;width: 100%;">
			<a class="btn btn-mini btn-primary" onclick="addItem();" style="float: right;margin-right: 10px;">添加</a>
		</div>
			<table id="table_report" class="table table-striped table-bordered table-hover">
			
			<tr>
<!-- 				<th>采集配置名称</th> -->
				<th>采集属性名称<span style="color: red;">*</span></th>
<!-- 				<th>采集系统id</th> -->
				<th>采集属性code</th>
				<th>采集属性单位</th>
				<th>通道号</th>
<!-- 				<th>3DX</th> -->
<!-- 				<th>3DY</th> -->
<!-- 				<th>3DZ</th> -->
<!-- 				<th>默认值</th> -->
				<th>设备属性编码</th>
				<th>采集类型</th>
				<th>关联配置</th>
				<th class="center">操作</th>
			</tr>
			<!-- 模板start -->
			<tr id="trModel" style="display: none;">
					<td><input type="text" name="" value="" style="width: 140px"></td>
					<td><input type="text" name="" value="" style="width: 70px"></td>
					<td><input type="text" name="" value="" style="width: 70px"></td>
					<td><input type="text" name="" value="" style="width: 50px"></td>
					<td><input type="text" name="" value="" style="width: 140px"></td>
					<td>
						<select name="" style="width: 90px;">
							<option value="0">自身采集</option>
							<option value="1">关联采集</option>
						</select>
					</td>
					<td>
						<select name="">
							<option value=""></option>
							<c:forEach items="${otherConfigs}" var="otherConfig">
								<option value="${otherConfig.CONFIG_ID}">${otherConfig.ATTR_NAME}</option>
							</c:forEach>
						</select>
					</td>

					<td style="width: ;">
						 <a class='btn btn-mini btn-danger' title="删除" onclick="deleteItem(this.parentNode.parentNode)"><i class='icon-trash'></i></a>
					</td>
				</tr>
			<!-- 模板end -->
			
			<c:choose>
			<c:when test="${not empty configs}">
			<script type="text/javascript">
			var otherConfigsStr = eval("("+'${otherConfigsStr}'+")");
			var sHtmlTest = "";
			for( var o in otherConfigsStr){
				sHtmlTest += "<option value='" + otherConfigsStr[o].CONFIG_ID + "'>"  
		            + otherConfigsStr[o].ATTR_NAME
		            + "</option>";
			}
			function showOp(index,obj) {
				if (obj.value != '1') {
					return;
				}
				if ($("#"+index+'RELATED_CONFIG_ID').children('option').length>1) {
					return;
				}
				$("#"+index+'RELATED_CONFIG_ID').append(sHtmlTest);
				$("#"+index+'RELATED_CONFIG_ID').chosen({allow_single_deselect:true});
			}
			</script>
			<c:forEach items="${configs}" var="config" varStatus="vs">		
				<tr>
					<td>
						<input type="hidden" name="vos[${vs.index}].CONFIG_ID" value="${config.CONFIG_ID}"/>	
						<input type="text" name="vos[${vs.index}].ATTR_NAME" value="${config.ATTR_NAME}" style="width: 140px">
					</td>
					<td><input type="text" name="vos[${vs.index}].GATHER_ATTR_CODE" value="${config.GATHER_ATTR_CODE}" style="width: 70px"></td>
					<td><input type="text" name="vos[${vs.index}].ATTR_UNIT" value="${config.ATTR_UNIT}" style="width: 70px"></td>
					<td><input type="text" name="vos[${vs.index}].CHANNEL_NO" value="${config.CHANNEL_NO}" style="width: 50px"></td>
					<td><input type="text" name="vos[${vs.index}].ATTR_CODE" value="${config.ATTR_CODE}" style="width: 140px"></td>
					<td>
						<select name="vos[${vs.index}].GATHER_TYPE" class="" id="${vs.index}GATHER_TYPE" style="width: 90px;"
							onchange="showOp('${vs.index}',this);">
							<option value="0">自身采集</option>
							<option value="1">关联采集</option>
						</select>
					</td>
					<td>
						<select name="vos[${vs.index}].RELATED_CONFIG_ID" class="" id="${vs.index}RELATED_CONFIG_ID">
							<option value=""></option>
						</select>
					</td>
					<td style="width: ;">
						 <a class='btn btn-mini btn-danger' title="删除" onclick="deleteItem(this.parentNode.parentNode)"><i class='icon-trash'></i></a>
					</td>
				</tr>
				<script type="text/javascript">
					$(function() {
						if('${config.GATHER_TYPE}' =='1'){
							$("#"+'${vs.index}RELATED_CONFIG_ID').append(sHtmlTest);
						}
						$("#"+'${vs.index}GATHER_TYPE').val('${config.GATHER_TYPE}');
						$("#"+'${vs.index}GATHER_TYPE').chosen({allow_single_deselect:true});
						$("#"+'${vs.index}RELATED_CONFIG_ID').val('${config.RELATED_CONFIG_ID}');
						if('${config.GATHER_TYPE}' =='1'){
							$("#"+'${vs.index}RELATED_CONFIG_ID').chosen({allow_single_deselect:true});
						}
					});				
				</script>
						
			</c:forEach>
			</c:when>
			<c:otherwise>
				<tr>
					<td><input type="text" name="vos[0].ATTR_NAME" value="${config.ATTR_NAME}" style="width: 140px"></td>
					<td><input type="text" name="vos[0].GATHER_ATTR_CODE" value="${config.GATHER_ATTR_CODE}" style="width: 70px"></td>
					<td><input type="text" name="vos[0].ATTR_UNIT" value="${config.ATTR_UNIT}" style="width: 70px"></td>
					<td><input type="text" name="vos[0].CHANNEL_NO" value="${config.CHANNEL_NO}" style="width: 50px"></td>
					<td><input type="text" name="vos[0].ATTR_CODE" value="${config.ATTR_CODE}" style="width: 140px"></td>
					<td>
						<select name="vos[0].GATHER_TYPE" class="chzn-select" style="width: 90px;">
							<option value="0">自身采集</option>
							<option value="1">关联采集</option>
						</select>
					</td>
					<td>
						<select name="vos[0].RELATED_CONFIG_ID" class="chzn-select">
							<option value=""></option>
							<c:forEach items="${otherConfigs}" var="otherConfig">
								<option value="${otherConfig.CONFIG_ID}">${otherConfig.ATTR_NAME}</option>
							</c:forEach>
						</select>
					</td>

					<td style="width: ;">
						 <a class='btn btn-mini btn-danger' title="删除" onclick="deleteItem(this.parentNode.parentNode)"><i class='icon-trash'></i></a>
					</td>
				</tr>
			</c:otherwise>
			</c:choose>
			
			
		</table>
		<table  class="table table-striped table-bordered table-hover">
			<tr>
				<td style="text-align: right;">
					<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
				</td>
				<td style="text-align: left;" colspan="2">
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
		<script type="text/javascript" src="js/bootstrap-datepicker.min.js"></script><!-- 日期框 -->
		<script type="text/javascript" src="js/bootbox.min.js"></script><!-- 确认窗口 -->
		<!-- 引入 -->
		
		<!--引入弹窗组件start-->
		<script type="text/javascript" src="js/attention/zDialog/zDrag.js"></script>
		<script type="text/javascript" src="js/attention/zDialog/zDialog.js"></script>
		<!--引入弹窗组件end-->
		
		<script type="text/javascript">
		var num = '${fn:length(configs)}';
		if (num == 0) {
			num = 1;
		}
		function addItem() {
			var model = $('#trModel').clone();
			model.show();
			model.find('input[type="text"]').eq(0).attr('name','vos['+num+'].ATTR_NAME');
			model.find('input[type="text"]').eq(1).attr('name','vos['+num+'].GATHER_ATTR_CODE');
			model.find('input[type="text"]').eq(2).attr('name','vos['+num+'].ATTR_UNIT');
			model.find('input[type="text"]').eq(3).attr('name','vos['+num+'].CHANNEL_NO');
			model.find('input[type="text"]').eq(4).attr('name','vos['+num+'].ATTR_CODE');
			
			model.find('select').eq(0).attr('name','vos['+num+'].GATHER_TYPE');
			model.find('select').eq(1).attr('name','vos['+num+'].RELATED_CONFIG_ID');
			
			$('#table_report').append(model);
			
			model.find('select').eq(0).chosen({allow_single_deselect:true});// 异步初始化
			model.find('select').eq(1).chosen({allow_single_deselect:true});// 异步初始化
			
			num++;
		}
		
		function deleteItem(theRow) {
			$(theRow).remove();
		}
		
		
		$(function() {
			$(".chzn-select").each(function(i){
				if(this.id.indexOf('RELATED_CONFIG_ID')>0){
					
				}
			});
			
			//单选框
// 			$(".chzn-select").chosen();
			$(".chzn-select").chosen({allow_single_deselect:true});
// 			$(".chzn-select-deselect").chosen({allow_single_deselect:true}); 
			
			//日期框
// 			$('.date-picker').datepicker();
			
		});
		
		</script>
		
	
</body>
</html>