<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!-- jsp文件头和头部 -->
<%@ include file="/jsp/system/admin/top.jsp"%>   

<body>
		
<!-- 页面顶部¨ -->
<%@ include file="/jsp/system/admin/head.jsp"%> 
		
<div class="container-fluid" id="main-container">

<a href="#" id="menu-toggler"><span></span></a><!-- menu toggler -->

<!-- 左侧菜单 -->
<%@ include file="/jsp/system/admin/left.jsp"%> 
		
<div id="main-content" class="clearfix">

<div id="breadcrumbs">

<ul class="breadcrumb">
	<li><i class="icon-home"></i> <a>系统管理</a><span class="divider"><i class="icon-angle-right"></i></span></li>
	<li class="active">设备管理</li>
</ul><!--.breadcrumb-->

<div id="nav-search">
</div><!--#nav-search-->

</div><!--#breadcrumbs-->


<div id="page-content" class="clearfix">
						
  <div class="row-fluid">


	<div class="row-fluid">
	
			<!-- 检索  -->
			<form action="equipment/listEquipments.do?isM1=${pdm.isM1 }&isM2=${pdm.isM2 }" method="post" name="userForm" id="userForm">
			<table>
				<tr>
					<td>检索：</td>
					<td>
						<input type="text" name="EQUIPMENT_NAME" value="${pd.EQUIPMENT_NAME }" placeholder="输入设备名称" style="width:100px;"/>
					</td>
					<td>
						<input type="text" name="PARENT_EQUIPMENT_NAME" value="${pd.PARENT_EQUIPMENT_NAME }"
							placeholder="输入父设备名称" style="width:100px;"/>
					</td>
					<td style="vertical-align:top;"> 
					
					<select class="chzn-select" name="EQUIPMENT_TYPE_ID" data-placeholder="设备类型" style="vertical-align:top;width: 120px;">
						<option value=""></option>
						<c:forEach items="${equipmentTypes}" var="equipmentType">
							<option value="${equipmentType.EQUIPMENT_TYPE_ID }" 
								<c:if test="${pd.EQUIPMENT_TYPE_ID == equipmentType.EQUIPMENT_TYPE_ID}">selected="selected"</c:if>>
							${equipmentType.EQUIPMENT_TYPE_NAME }
							</option>
						</c:forEach>
					</select>
					
					</td>
					<td style="vertical-align:top;"> 
					 	<select class="chzn-select" name="AREA_ID" data-placeholder="组织机构" style="vertical-align:top;width: 120px;">
							<option value=""></option>
							<c:forEach items="${organs}" var="organ">
								<option value="${organ.ORGAN_ID }"
									<c:if test="${pd.AREA_ID==organ.ORGAN_ID}">selected</c:if>>${organ.doubleName}</option>
							</c:forEach>
						</select>
					</td>
					<td ><input type="text" name="IP" value="${pd.IP }" placeholder="输入IP地址" style="width:100px;"/></td>
					<td style="vertical-align:top;">
						<button class="btn btn-mini btn-light" onclick="search();" style="margin-left: 10px;">
							<i id="nav-search-icon" class="icon-search"></i></button>
					</td>
					<c:if test="${QX.add == 1 }">
						<td style="vertical-align:top;">
							<a class="btn btn-small btn-success" onclick="add();" style="margin-left: 50px;">新增</a>
						</td>
						<td style="vertical-align:top;">
							<a class="btn btn-mini btn-light" onclick="fromExcel();" title="导入设备">
								<i id="nav-search-icon" class="icon-cloud-upload"></i></a>
						</td>
					</c:if>
				</tr>
			</table>
			<!-- 检索  -->
		
		
			<table id="table_report" class="table table-striped table-bordered table-hover">
				
				<thead>
					<tr>
						<th>编号</th>
						<th>设备名称</th>
						<th>父设备名称</th>
						<th>设备类型</th>
						<th>组织机构</th>
						<th>IP地址</th>
						<th class="center">操作</th>
					</tr>
				</thead>
										
				<tbody>
					
				<!-- 开始循环 -->	
				<c:choose>
					<c:when test="${not empty equipmentList}">
						<c:if test="${QX.cha == 1 }">
						<c:forEach items="${equipmentList}" var="user" varStatus="vs">
									
							<tr>
								<td class='center' style="width: 30px;">${user.EQUIPMENT_ID}</td>
								<td><a>${user.EQUIPMENT_NAME }</a></td>
								<td>${user.PARENT_EQUIPMENT_NAME }</td>
								<td>${user.EQUIPMENT_TYPE_NAME }</td>
								<td>
									<c:forEach items="${organs}" var="organ">
										<c:if test="${user.AREA_ID==organ.ORGAN_ID}">
											${organ.doubleName }
										</c:if>
									</c:forEach>
								</td>
								<td>${user.IP}</td>
								<td style="width: 60px;">
									<div class='hidden-phone visible-desktop btn-group'>
										
										<c:if test="${QX.edit != 1 && QX.del != 1 }">
										<span class="label label-large label-grey arrowed-in-right arrowed-in"><i class="icon-lock" title="无权限"></i></span>
										</c:if>
										
										<c:if test="${QX.edit == 1 }">
											<a class='btn btn-mini btn-info' title="编辑基本属性" onclick="editUser('${user.EQUIPMENT_ID }');"><i class='icon-edit'></i></a>
											<a class='btn btn-mini btn-info' title="编辑扩展属性" onclick="editUser1('${user.EQUIPMENT_ID}','${user.EQUIPMENT_TYPE_ID }');"><i class='icon-edit'></i></a>
											<a class='btn btn-mini btn-info' title="编辑设备位置" onclick="editUser2('${user.EQUIPMENT_ID}','${user.POSITION_ID}');"><i class='icon-edit'></i></a>
											<a class='btn btn-mini btn-info' title="编辑采集配置" onclick="editUser3('${user.EQUIPMENT_ID }');"><i class='icon-edit'></i></a>
										</c:if>
										<c:if test="${QX.del == 1 }">
										 <a class='btn btn-mini btn-danger' title="删除" onclick="delUser('${user.EQUIPMENT_ID }','${user.POSITION_ID }');"><i class='icon-trash'></i></a>
										</c:if>
									</div>
								</td>
							</tr>
						
						</c:forEach>
						</c:if>
						
						<c:if test="${QX.cha == 0 }">
							<tr>
								<td colspan="10" class="center">您无权查看</td>
							</tr>
						</c:if>
					</c:when>
					<c:otherwise>
						<tr class="main_info">
							<td colspan="10" class="center">没有相关数据</td>
						</tr>
					</c:otherwise>
				</c:choose>
					
				
				</tbody>
			</table>
			
		<div class="page-header position-relative">
		<table style="width:100%;">
			<tr>
				<td style="vertical-align:top;"></td>
				<td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
			</tr>
		</table>
		</div>
		</form>
	</div>
 
 
 
 
	<!-- PAGE CONTENT ENDS HERE -->
  </div><!--/row-->
	
</div><!--/#page-content-->
</div><!-- #main-content -->
</div><!--/.fluid-container#main-container-->
		
		<!-- 返回顶部  -->
		<a href="#" id="btn-scroll-up" class="btn btn-small btn-inverse">
			<i class="icon-double-angle-up icon-only"></i>
		</a>
		
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
		//检索
		function search(){
			$("#userForm").submit();
		}
		
		//新增
		function add(){
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="新增设备";
			 diag.URL = 'goAddU.do';
			 diag.Width = 800;
			 diag.Height = 600;
			 diag.CancelEvent = function(){ //关闭事件
				diag.close();
				nextPage('${page.currentPage}');
				//setTimeout("self.location.reload()",100);
			 };
			 diag.show();
		}
		
		//修改
		function editUser(user_id){
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="修改基本属性";
			 diag.URL = 'goEditU.do?EQUIPMENT_ID='+user_id;
			 diag.Width = 800;
			 diag.Height = 600;
			 diag.CancelEvent = function(){ //关闭事件
				diag.close();
				nextPage('${page.currentPage}');
				//setTimeout("self.location.reload()",100);
			 };
			 diag.show();
		}
		
		//修改
		function editUser1(user_id,equipment_type_id){
			
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="修改扩展属性";
			 diag.URL = 'goEditU1.do?EQUIPMENT_ID='+user_id+'&EQUIPMENT_TYPE_ID=' +equipment_type_id;
			 diag.Width = 900;
			 diag.Height = 500;
			 diag.CancelEvent = function(){ //关闭事件
				diag.close();
				//setTimeout("self.location.reload()",100);
			 };
			 diag.show(); 
		}
		
		//修改
		function editUser2(user_id,position_id){
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="修改设备位置";
			 diag.URL = 'goEditU2.do?EQUIPMENT_ID='+user_id +'&POSITION_ID='+ position_id;
			 diag.Width = 500;
			 diag.Height = 700;
			 diag.CancelEvent = function(){ //关闭事件
				diag.close();
				//setTimeout("self.location.reload()",100);
			 };
			 diag.show();
		}
		
		//打开上传excel页面
		function fromExcel(){
			 //window.parent.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="设备入库";
			 diag.URL = 'goUploadExcel.do';
			 diag.Width = 300;
			 diag.Height = 150;
			 diag.CancelEvent = function(){ //关闭事件
				diag.close();
			 };
			 diag.show();
		}
		
		
		//修改
		function editUser3(user_id){
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="修改采集配置";
			 diag.URL = 'goEditU3.do?EQUIPMENT_ID='+user_id;
			 diag.Width = 1200;
			 diag.Height = 600;
			 diag.CancelEvent = function(){ //关闭事件
				diag.close();
				//setTimeout("self.location.reload()",100);
			 };
			 diag.show();
		}
		
		//删除
		function delUser(userId,position_id){
			bootbox.confirm("确定要删除该设备?", function(result) {
				if(result) {
					var url = "equipment/deleteU.do?EQUIPMENT_ID="+userId+"&tm="+new Date().getTime()+"&POSITION_ID="+position_id;
					$.get(url,function(data){
						if(data=="success"){
							//document.location.reload();
							nextPage('${page.currentPage}');
						}
					});
				}
			});
		}
		
		</script>
		
		<script type="text/javascript">
			
			//单选框
			$(".chzn-select").chosen({allow_single_deselect:true}); 
			$(".chzn-select-deselect").chosen({allow_single_deselect:true}); 
			
		
		$(function() {
			
			//日期框
			$('.date-picker').datepicker();
			
		});
		
		
		</script>
		
		
	</body>
</html>

