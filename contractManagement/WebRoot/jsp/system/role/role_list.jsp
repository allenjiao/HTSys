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
	<li class="active">组织管理</li>
</ul><!--.breadcrumb-->

<div id="nav-search">
</div><!--#nav-search-->

</div><!--#breadcrumbs-->


<div id="page-content" class="clearfix">
						
  <div class="row-fluid">
  	<!-- 作部分组织机构树 -->
	<div class="span3">
		<div class="widget-box pricing-box">
			<div class="widget-header header-color-dark">
				<h5 class="bigger lighter">组织机构</h5>
			</div>
			<div class="widget-body">
				<div class="widget-main">
				
					<ul id="tree" class="tree" style="overflow:auto;"></ul>
					
					<hr />
					<div class="price">
						<a class="btn btn-mini btn-primary" onclick="organ();">组织机构维护</a>
					</div>
				</div>
			</div>
		</div>
	</div>

<script type="text/javascript">
	var zTree;
	
	$(document).ready(function() {
		var setting = {
		
			isSimpleData : true,   //是否采用简单 Array 格式,true表示采用 
			treeNodeKey : "id",    
			treeNodeParentKey : "pId",//如果为true必须声明父子关系 
		
			showLine : true,
			checkable : false,
			callback : {
				onClick : function(e, treeId, treeNode) {
					var zTree = $.fn.zTree.getZTreeObj("customerTree"), nodes = zTree.getSelectedNodes(), names = "", groupIds = "";
					
					
					
					for ( var i = 0, l = nodes.length; i < l; i++) {
						names += nodes[i].name + ",";
						groupIds += nodes[i].id + ",";
					}
					if (names.length > 0) {
						names = names.substring(0, names.length - 1);
						groupIds = groupIds.substring(0, groupIds.length - 1);
					}
					var cityObj = $("#customerSel");
					cityObj.attr("value", names);
					$("#cusGroupId").attr("value", groupIds);
					$("#cusGroupName").attr("value", names);
					getCustomerNodes(nodes[0]);
				}
			}
		};
		var zn = '${zTreeNodes}';
		var zTreeNodes = eval(zn);
		zTree = $("#tree").zTree(setting, zTreeNodes);
	});

	function organ() {
		var diag = new top.Dialog();
		diag.Drag = true;
		diag.Title = "组织机构维护";
		diag.URL = '/organ.do';
		diag.Width = 900;
		diag.Height = 590;
		diag.CancelEvent = function() { //关闭事件
			diag.close();
			setTimeout("self.location.reload()",100);
		};
		diag.show();

	}

	function save() {
		var nodes = zTree.getCheckedNodes();
		var tmpNode;
		var ids = "";
		for ( var i = 0; i < nodes.length; i++) {
			tmpNode = nodes[i];
			if (i != nodes.length - 1) {
				ids += tmpNode.id + ",";
			} else {
				ids += tmpNode.id;
			}
		}

		var userId = "${userId}";
		var roleId = "${roleId}";
		var url = "role/auth/save.do";
		var postData;
		if (userId != "") {
			postData = {
				"userId" : userId,
				"menuIds" : ids
			};
		} else {
			postData = {
				"ROLE_ID" : roleId,
				"menuIds" : ids
			};
		}

		$("#zhongxin").hide();
		$("#zhongxin2").show();
		$.post(url, postData, function(data) {
			if (data && data == "success") {
				top.Dialog.close();
			}
		});

	}
</script>

					<!-- 右部分原组织管理内容 -->
	<div class="span9">

<div>
		<table class="center" style="width:100%;">
			<tr height="35">
				<c:if test="${QX.add == 1 }">
				<td style="width:69px;"><a href="javascript:addRole();" class="btn btn-small btn-success">新增</a></td>
				</c:if>
					<c:choose>
					<c:when test="${not empty roleList}">
					<c:forEach items="${roleList}" var="role" varStatus="vs">
						<td style="width:100px;" class="center" <c:choose><c:when test="${pd.ROLE_ID == role.ROLE_ID}">bgcolor="#FFC926" onMouseOut="javascript:this.bgColor='#FFC926';"</c:when><c:otherwise>bgcolor="#E5E5E5" onMouseOut="javascript:this.bgColor='#E5E5E5';"</c:otherwise></c:choose>  onMouseMove="javascript:this.bgColor='#FFC926';" >
							<a href="role.do?ROLE_ID=${role.ROLE_ID }&isM1=${pdm.isM1 }&isM2=${pdm.isM2 }" style="text-decoration:none; display:block;"><li class=" icon-group"></li>&nbsp;<font color="#666666">${role.ROLE_NAME }</font></a>
						</td>
						<td style="width:5px;"></td>
					</c:forEach>
					</c:when>
					<c:otherwise>
						<tr>
						<td colspan="100">没有相关数据</td>
						</tr>
					</c:otherwise>
					</c:choose>
				<td></td>
			</tr>
		</table>
		
		<table>
			<tr height="7px;"><td colspan="100"></td></tr>
			<tr>
			<td><font color="#808080">本部门：</font></td>
			<td>
			<c:if test="${QX.edit == 1 }">
			<a class="btn btn-mini btn-info" onclick="editRole('${pd.ROLE_ID }');">修改名称<i class="icon-arrow-right  icon-on-right"></i></a>
			</c:if>
				<c:choose>
					<c:when test="${pd.ROLE_ID == '99'}">
					</c:when>
					<c:otherwise>
					<c:if test="${QX.edit == 1 }">
					<a class="btn btn-mini btn-purple" onclick="editRights('${pd.ROLE_ID }');"><i class="icon-pencil"></i>菜单权限</a>
					</c:if>
					</c:otherwise>
				</c:choose>
				<c:choose> 
					<c:when test="${pd.ROLE_ID == '6' or pd.ROLE_ID == '4' or pd.ROLE_ID == '1' or pd.ROLE_ID == '7'}">
					</c:when>
					<c:otherwise>
					 <c:if test="${QX.del == 1 }">
					 <a class='btn btn-mini btn-danger' title="删除" onclick="delRole('${pd.ROLE_ID }','z');"><i class='icon-trash'></i></a>
					 </c:if>
					</c:otherwise>
				</c:choose>
			</td>
			</tr>
			<tr height="7px;"><td colspan="100"></td></tr>
		</table>
		
		
	</div>
	<table id="table_report" class="table table-striped table-bordered table-hover">
		<thead>
		<tr>
			<th class="center">序号</th>
			<th>职位</th>

			<c:if test="${pd.ROLE_ID == '4' }">
			<th class="center" bgcolor="#FFBF00">操作</th>
			<th class="center" bgcolor="#EFFFBF">产品</th>
			<th class="center" bgcolor="#BFEFFF">信件</th>
			<th class="center" bgcolor="#EFBFFF">服务</th>
			</c:if>
			
			<c:if test="${pd.ROLE_ID == '6' }">
			<th class="center" bgcolor="#BFEFFF">信件数</th>
			</c:if>
			
			<th class="center">增</th>
			<th class="center">删</th>
			<th class="center">写</th>
			<th class="center">读</th>
			<th style="width:155px;"  class="center">操作</th>
		</tr>
		</thead>
		<c:choose>
			<c:when test="${not empty roleList_z}">
				<c:if test="${QX.cha == 1 }">
				<c:forEach items="${roleList_z}" var="var" varStatus="vs">
				
				<c:if test="${pd.ROLE_ID == '4' }">
					<c:forEach items="${kefuqxlist}" var="varK" varStatus="vsK">
						<c:if test="${var.QX_ID == varK.GL_ID }">
							<c:set value="${varK.FX_QX }" var="fx_qx"></c:set>
							<c:set value="${varK.FW_QX }" var="fw_qx"></c:set>
							<c:set value="${varK.QX1 }" var="qx1"></c:set>
							<c:set value="${varK.QX2 }" var="qx2"></c:set>
						</c:if>
					</c:forEach>
				</c:if>
				<c:if test="${pd.ROLE_ID == '6' }">
					<c:forEach items="${gysqxlist}" var="varG" varStatus="vsG">
						<c:if test="${var.QX_ID == varG.U_ID }">
							<c:set value="${varG.C1 }" var="c1"></c:set>
							<c:set value="${varG.C2 }" var="c2"></c:set>
							<c:set value="${varG.Q1 }" var="q1"></c:set>
							<c:set value="${varG.Q2 }" var="q2"></c:set>
						</c:if>
					</c:forEach>
				</c:if>
				<tr>
				<td class='center' style="width: 30px;">${vs.index+1}</td>
				<td id="ROLE_NAMETd${var.ROLE_ID }">${var.ROLE_NAME }</td>

				<c:if test="${pd.ROLE_ID == '4' }"> 
				<td style="width:60px;" class="center"><label><input type="checkbox" class="ace-switch ace-switch-3" id="qx1${vs.index+1}" <c:if test="${qx1 == 1 }">checked="checked"</c:if> onclick="kf_qx(this.id,'${var.QX_ID}','kfqx1')" /><span class="lbl"></span></label></td>
				<td style="width:60px;" class="center"><label><input type="checkbox" class="ace-switch ace-switch-3" id="qx2${vs.index+1}" <c:if test="${qx2 == 1 }">checked="checked"</c:if>  onclick="kf_qx(this.id,'${var.QX_ID}','kfqx2')"/><span class="lbl"></span></label></td>
				<td style="width:60px;" class="center"><label><input type="checkbox" class="ace-switch ace-switch-3" id="qx3${vs.index+1}" <c:if test="${fx_qx == 1 }">checked="checked"</c:if>  onclick="kf_qx(this.id,'${var.QX_ID}','fxqx')"/><span class="lbl"></span></label></td>
				<td style="width:60px;" class="center"><label><input type="checkbox" class="ace-switch ace-switch-3" id="qx4${vs.index+1}" <c:if test="${fw_qx == 1 }">checked="checked"</c:if>  onclick="kf_qx(this.id,'${var.QX_ID}','fwqx')"/><span class="lbl"></span></label></td>
				</c:if>
				
				<c:if test="${pd.ROLE_ID == '6' }">
				<td style="width:55px;" class="center"><input name="xinjian" id="xj${vs.index+1}" value="${c1 }" style="width:30px;height:100%;text-align:center;" onkeyup="c1(this.id,'c1',this.value,'${var.QX_ID}')" type="number"/></td>
				</c:if>
				<td style="width:60px;" class="center"><label><input type="checkbox" class="ace-switch ace-switch-6" id="z${vs.index+1}" <c:if test="${var.ADD_QX == '1' }">checked="checked"</c:if> onclick="add_qx(this.id,'${var.ROLE_ID}')" /><span class="lbl"></span></label></td>
				<td style="width:60px;" class="center"><label><input type="checkbox" class="ace-switch ace-switch-6" id="s${vs.index+1}" <c:if test="${var.DEL_QX == '1' }">checked="checked"</c:if>  onclick="del_qx(this.id,'${var.ROLE_ID}')"/><span class="lbl"></span></label></td>
				<td style="width:60px;" class="center"><label><input type="checkbox" class="ace-switch ace-switch-6" id="g${vs.index+1}" <c:if test="${var.EDIT_QX == '1' }">checked="checked"</c:if>  onclick="edit_qx(this.id,'${var.ROLE_ID}')"/><span class="lbl"></span></label></td>
				<td style="width:60px;" class="center"><label><input type="checkbox" class="ace-switch ace-switch-6" id="c${vs.index+1}" <c:if test="${var.CHA_QX == '1' }">checked="checked"</c:if>  onclick="cha_qx(this.id,'${var.ROLE_ID}')"/><span class="lbl"></span></label></td>
				
				<td style="width:155px;">
				
				<c:if test="${QX.edit != 1 && QX.del != 1 }">
				<div style="width:100%;" class="center">
				<span class="label label-large label-grey arrowed-in-right arrowed-in"><i class="icon-lock" title="无权限"></i></span>
				</div>
				</c:if>
				
				<c:if test="${QX.edit == 1 }">
				<a class="btn btn-mini btn-purple" onclick="editRights('${var.ROLE_ID }');"><i class="icon-pencil"></i>菜单权限</a>
				<a class='btn btn-mini btn-info' title="编辑" onclick="editRole('${var.ROLE_ID }');"><i class='icon-edit'></i></a>
				</c:if>
				<c:choose> 
					<c:when test="${var.ROLE_ID == '2' or var.ROLE_ID == '1'}">
					</c:when>
					<c:otherwise>
					 <c:if test="${QX.del == 1 }">
					 <a class='btn btn-mini btn-danger' title="删除" onclick="delRole('${var.ROLE_ID }','c');"><i class='icon-trash'></i></a>
					 </c:if>
					</c:otherwise>
				</c:choose>
				</tr>
				</c:forEach>
				</c:if>
						<c:if test="${QX.cha == 0 }">
							<tr>
								<td colspan="100" class="center">您无权查看</td>
							</tr>
						</c:if>
			</c:when>
			<c:otherwise>
				<tr>
				<td colspan="100" class="center" >没有相关数据</td>
				</tr>
			</c:otherwise>
		</c:choose>
	</table>
			
		<div class="page-header position-relative">
		<c:if test="${QX.add == 1 }">
		<table style="width:100%;">
			<tr>
				<td style="vertical-align:top;"><a class="btn btn-small btn-success" onclick="addRole2('${pd.ROLE_ID }');">新增</a></td>
			</tr>
		</table>
		</c:if>
		</div>

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
		
		<!-- 树组件 start -->
<!-- 		<script type="text/javascript" src="js/jquery-1.5.1.min.js"></script> -->
		<script type="text/javascript" src="js/zTree/jquery.ztree-2.6.min.js"></script>
		<!-- 树组件 end -->
		
		<!-- 引入 -->
<!-- 		<script src="1.9.1/jquery.min.js"></script> -->
<!-- 		<script type="text/javascript">window.jQuery || document.write("<script src='js/jquery-1.9.1.min.js'>\x3C/script>");</script> -->
		<script src="js/bootstrap.min.js"></script>
		<script src="js/ace-elements.min.js"></script>
		<script src="js/ace.min.js"></script>
		
		<script type="text/javascript" src="js/bootbox.min.js"></script><!-- 确认窗口 -->
		<!-- 引入 -->
		
		
		<script type="text/javascript">
		
		//新增部门
		function addRole(){
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="新增部门";
			 diag.URL = 'role/toAdd.do?parent_id=0';
			 diag.Width = 222;
			 diag.Height = 90;
			 diag.CancelEvent = function(){ //关闭事件
				diag.close();
				//setTimeout("self.location.reload()",100);
			 };
			 diag.show();
		}
		
		//新增职位
		function addRole2(pid){
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="新增职位";
			 diag.URL = 'role/toAdd.do?parent_id='+pid;
			 diag.Width = 222;
			 diag.Height = 90;
			 diag.CancelEvent = function(){ //关闭事件
				diag.close();
				//setTimeout("self.location.reload()",100);
			 };
			 diag.show();
		}
		
		//修改
		function editRole(ROLE_ID){
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="编辑";
			 diag.URL = 'role/toEdit.do?ROLE_ID='+ROLE_ID;
			 diag.Width = 222;
			 diag.Height = 90;
			 diag.CancelEvent = function(){ //关闭事件
				diag.close();
				//setTimeout("self.location.reload()",100);
			 };
			 diag.show();
		}
		
		//删除
		function delRole(ROLE_ID,msg){
			bootbox.confirm("确定要删除该记录?", function(result) {
				if(result) {
					var url = "role/delete.do?ROLE_ID="+ROLE_ID+"&guid="+new Date().getTime();
					$.get(url,function(data){
						if(data=="success"){
							if(msg == 'c'){
								document.location.reload();
							}else{
								window.location.href="/role.do?1=1&isM1=1&isM2=2";
							}
							
						}else if(data=="false"){
							bootbox.dialog("删除失败，请先删除此部门下的职位!", 
									[
									  {
										"label" : "关闭",
										"class" : "btn-small btn-success",
										"callback": function() {
											//Example.show("great success");
											}
										}]
								);
						}else if(data=="false2"){
							bootbox.dialog("删除失败，请先删除此职位下的用户!", 
									[
									  {
										"label" : "关闭",
										"class" : "btn-small btn-success",
										"callback": function() {
											//Example.show("great success");
											}
										}]
								);
						}
					});
				}
			});
		}
		
		</script>
		
		<script type="text/javascript">
		
		//权限 增
		var qxs1='';
		var id1 = '';
		function add_qx(id,ROLE_ID){
			if(id != id1){
				id1 = id;
				qxs1='';
			}
			var value = 1;
			var wqx = $("#"+id).attr("checked");
			if(qxs1 == ''){
				if(wqx == 'checked'){
					qxs1 = 'checked';
				}else{
					qxs1 = 'unchecked';
				}
			}
			if(qxs1 == 'checked'){
				value = 1;
				qxs1 = 'unchecked';
			}else{
				value = 0;
				qxs1 = 'checked';
			}

			var url = "role/qx.do?ROLE_ID="+ROLE_ID+"&msg=add_qx&value="+value+"&guid="+new Date().getTime();
			$.get(url,function(data){
				if(data=="success"){
					//document.location.reload();
				}
			});
		}
		//权限 删
		var qxs2='';
		var id2='';
		function del_qx(id,ROLE_ID){
			if(id != id2){
				id2 = id;
				qxs2='';
			}
			var value = 1;
			var wqx = $("#"+id).attr("checked");
			if(qxs2 == ''){
				if(wqx == 'checked'){
					qxs2 = 'checked';
				}else{
					qxs2 = 'unchecked';
				}
			}
			if(qxs2 == 'checked'){
				value = 1;
				qxs2 = 'unchecked';
			}else{
				value = 0;
				qxs2 = 'checked';
			}

				var url = "role/qx.do?ROLE_ID="+ROLE_ID+"&msg=del_qx&value="+value+"&guid="+new Date().getTime();
				$.get(url,function(data){
					if(data=="success"){
						//document.location.reload();
					}
				});
		}
		//权限 改
		var qxs3='';
		var id3='';
		function edit_qx(id,ROLE_ID){
			if(id != id3){
				id3 = id;
				qxs3='';
			}
			var value = 1;
			var wqx = $("#"+id).attr("checked");
			if(qxs3 == ''){
				if(wqx == 'checked'){
					qxs3 = 'checked';
				}else{
					qxs3 = 'unchecked';
				}
			}
			if(qxs3 == 'checked'){
				value = 1;
				qxs3 = 'unchecked';
			}else{
				value = 0;
				qxs3 = 'checked';
			}
				var url = "role/qx.do?ROLE_ID="+ROLE_ID+"&msg=edit_qx&value="+value+"&guid="+new Date().getTime();
				$.get(url,function(data){
					if(data=="success"){
						//document.location.reload();
					}
				});
		}
		//权限 查
		var qxs4='';
		var id4='';
		function cha_qx(id,ROLE_ID){
			if(id != id4){
				id4 = id;
				qxs4='';
			}
			var value = 1;
			var wqx = $("#"+id).attr("checked");
			if(qxs4 == ''){
				if(wqx == 'checked'){
					qxs4 = 'checked';
				}else{
					qxs4 = 'unchecked';
				}
			}
			if(qxs4 == 'checked'){
				value = 1;
				qxs4 = 'unchecked';
			}else{
				value = 0;
				qxs4 = 'checked';
			}
				var url = "role/qx.do?ROLE_ID="+ROLE_ID+"&msg=cha_qx&value="+value+"&guid="+new Date().getTime();
				$.get(url,function(data){
					if(data=="success"){
						//document.location.reload();
					}
				});
		}
		
		//扩展权限 ==============================================================
		var hcid = '';
		var qxhc = '';
		function kf_qx(id,kefu_id,msg){
			if(id != hcid){
				hcid = id;
				qxhc='';
			}
			var value = 1;
			var wqx = $("#"+id).attr("checked");
			if(qxhc == ''){
				if(wqx == 'checked'){
					qxhc = 'checked';
				}else{
					qxhc = 'unchecked';
				}
			}
			if(qxhc == 'checked'){
				value = 0;
				qxhc = 'unchecked';
			}else{
				value = 1;
				qxhc = 'checked';
			}
			
			
				var url = "role/kfqx.do?kefu_id="+kefu_id+"&msg="+msg+"&value="+value+"&guid="+new Date().getTime();
				$.get(url,function(data){
					if(data=="success"){
						//document.location.reload();
					}
				});
		}
		
		//保存信件数
		function c1(id,msg,value,kefu_id){
				if(isNaN(Number(value))){
					alert("请输入数字!");
					$("#"+id).val(0);
					return;
				}else{
				var url = "role/gysqxc.do?kefu_id="+kefu_id+"&msg="+msg+"&value="+value+"&guid="+new Date().getTime();
				$.get(url,function(data){
					if(data=="success"){
						//document.location.reload();
					}
				});
				}
		}
		
		//菜单权限
		function editRights(ROLE_ID){
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="菜单权限";
			 diag.URL = 'role/auth.do?ROLE_ID='+ROLE_ID;
			 diag.Width = 280;
			 diag.Height = 370;
			 diag.CancelEvent = function(){ //关闭事件
				diag.close();
				//setTimeout("self.location.reload()",100);
			 };
			 diag.show();
		}
		
		</script>
		
		
	</body>
</html>

