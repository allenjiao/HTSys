<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- jsp文件头和头部 -->
<%@ include file="../admin/top.jsp"%>   

<body>
		
<!-- 页面顶部¨ -->
<%@ include file="../admin/head.jsp"%> 
		
<div class="container-fluid" id="main-container">

<a href="#" id="menu-toggler"><span></span></a><!-- menu toggler -->

<!-- 左侧菜单 -->
<%@ include file="../admin/left.jsp"%> 
		
<div id="main-content" class="clearfix">

<div id="breadcrumbs">

<ul class="breadcrumb">
	<li><i class="icon-home"></i> <a>系统管理</a><span class="divider"><i class="icon-angle-right"></i></span></li>
	<li class="active">系统用户</li>
</ul><!--.breadcrumb-->

<div id="nav-search">
</div><!--#nav-search-->

</div><!--#breadcrumbs-->


<div id="page-content" class="clearfix">
						
  <div class="row-fluid">


	<div class="row-fluid">
	
			<!-- 检索  -->
			<form action="user/listUsers.do?isM1=${pdm.isM1 }&isM2=${pdm.isM2 }" method="post" name="userForm" id="userForm">
			<table>
				<tr>
					<td><font color="#808080">检索：</font></td>
					<td><input type="text" name="USERNAME" value="${pd.USERNAME }" placeholder="这里输入用户名" style="width:130px;"/></td>
					<td><input class="span10 date-picker" name="lastLoginStart" value="${pd.lastLoginStart}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;" placeholder="开始日期" title="最近登录开始"/></td>
					<td><input class="span10 date-picker" name="lastLoginEnd" value="${pd.lastLoginEnd}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;" placeholder="结束日期" title="最近登录结束"/></td>
					<td style="vertical-align:top;"> 
					 	<select class="chzn-select" name="ROLE_ID" id="role_id" data-placeholder="请选择职位" style="vertical-align:top;width: 120px;">
						<option value=""></option>
						<c:forEach items="${roleList}" var="role">
							<option value="${role.ROLE_ID }" <c:if test="${pd.ROLE_ID==role.ROLE_ID}">selected</c:if>>${role.ROLE_NAME }</option>
						</c:forEach>
					  	</select>
					</td>
					<td style="vertical-align:top;"><button class="btn btn-mini btn-light" onclick="search();"><i id="nav-search-icon" class="icon-search"></i></button></td>
				</tr>
			</table>
			<!-- 检索  -->
		
		
			<table id="table_report" class="table table-striped table-bordered table-hover">
				
				<thead>
					<tr>
						<th>序号</th>
						<th>用户名</th>
						<th>姓名</th>
						<th>职位</th>
						<th>组织机构</th>
						<th><i class="icon-time hidden-phone"></i>最近登录</th>
						<th>上次登录IP</th>
						<th class="center">操作</th>
					</tr>
				</thead>
										
				<tbody>
					
				<!-- 开始循环 -->	
				<c:choose>
					<c:when test="${not empty userList}">
						<c:if test="${QX.cha == 1 }">
						<c:forEach items="${userList}" var="user" varStatus="vs">
									
							<tr>
								<td class='center' style="width: 30px;">${vs.index+1}</td>
								<td><a>${user.USERNAME }</a></td>
								<td>${user.NAME }</td>
								<td>${user.ROLE_NAME }</td>
								<td>
									<c:forEach items="${organList}" var="organ">
										<c:if test="${organ.ORGAN_ID==user.ORGAN_ID}">${organ.doubleName}</c:if>
									</c:forEach>
								</td>
								<td>${user.LAST_LOGIN}</td>
								<td>${user.IP}</td>
								<td style="width: 60px;">
									<div class='hidden-phone visible-desktop btn-group'>
										
										<c:if test="${QX.edit != 1 && QX.del != 1 }">
										<span class="label label-large label-grey arrowed-in-right arrowed-in"><i class="icon-lock" title="无权限"></i></span>
										</c:if>
										
										<c:if test="${QX.edit == 1 }">
										<a class='btn btn-mini btn-info' title="编辑" onclick="editUser('${user.USER_ID }');"><i class='icon-edit'></i></a>
										</c:if>
										<c:choose>
											<c:when test="${user.USERNAME=='admin'}"></c:when>
											<c:otherwise>
												<c:if test="${QX.del == 1 }">
												 <a class='btn btn-mini btn-danger' title="删除" onclick="delUser('${user.USER_ID }');"><i class='icon-trash'></i></a>
												</c:if>
											</c:otherwise>
										</c:choose>
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
				<c:if test="${QX.add == 1 }">
				<td style="vertical-align:top;"><a class="btn btn-small btn-success" onclick="add();">新增</a></td>
				</c:if>
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
			 diag.Title ="新增";
			 diag.URL = 'goAddU.do';
			 diag.Width = 300;
			 diag.Height = 388;
			 diag.CancelEvent = function(){ //关闭事件
				diag.close();
				//setTimeout("self.location.reload()",100);
			 };
			 diag.show();
		}
		
		//修改
		function editUser(user_id){
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="资料";
			 diag.URL = 'goEditU.do?USER_ID='+user_id;
			 diag.Width = 300;
			 diag.Height = 388;
			 diag.CancelEvent = function(){ //关闭事件
				diag.close();
				//setTimeout("self.location.reload()",100);
			 };
			 diag.show();
		}
		
		//删除
		function delUser(userId){
			bootbox.confirm("确定要删除该记录?", function(result) {
				if(result) {
					var url = "user/deleteU.do?USER_ID="+userId+"&tm="+new Date().getTime();
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
		$(".chzn-select").chosen(); 
		$(".chzn-select-deselect").chosen({allow_single_deselect:true}); 
		
		$(function() {
			
			//日期框
			$('.date-picker').datepicker();
			
		});
		
		
		</script>
		
		
	</body>
</html>

