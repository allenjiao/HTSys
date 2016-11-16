<div class="navbar navbar-inverse">
		  <div class="navbar-inner">
		   <div class="container-fluid">
			  <a class="brand" href="login_index.do"><img alt="" src="/images/logo.png" style="vertical-align: middle;"/></a>
			  <big class="brand" style="padding-top: 18px;padding-left: 0px;">机房运行监控与管理系统平台</big>
			  <ul class="nav ace-nav pull-right">
					<li class="light-blue user-profile">
						<a class="user-menu dropdown-toggle" href="#" data-toggle="dropdown">
							<img alt="FH" src="avatars/user.jpg" class="nav-user-photo" />
							<span id="user_info">
								
							</span>
							<i class="icon-caret-down"></i>
						</a>
						<ul id="user_menu" class="pull-right dropdown-menu dropdown-yellow dropdown-caret dropdown-closer">
							<li><a onclick="editUserH();" style="cursor:pointer;"><i class="icon-cog"></i> 修改资料</a></li>
<!-- 							<li><a href="#"><i class="icon-user"></i> 设置2</a></li> -->
							<li class="divider"></li>
							<li><a href="logout.do"><i class="icon-off"></i> 退出</a></li>
						</ul>
					</li>
			  </ul><!--/.ace-nav-->
		   </div><!--/.container-fluid-->
		  </div><!--/.navbar-inner-->
		</div><!--/.navbar-->
		
		<script type="text/javascript">
			
			var USER_ID;
		
			function getUname(){
				$.ajax({
					type: "POST",
					url: '/head/getUname.do?tm='+new Date().getTime(),
			    	data: encodeURI(""),
					dataType:'json',
					//beforeSend: validateData,
					cache: false,
					success: function(data){
						//alert(data.list.length);
						 $.each(data.list, function(i, list){
							 
							 //登陆者资料
							 $("#user_info").html('<small>Welcome</small> '+list.NAME+'');
							 
							 //用户ID
							 USER_ID = list.USER_ID;
							 
						 });
					}
				});
			}
			getUname();
			
			
			//修改
			function editUserH(){
				 var diag = new top.Dialog();
				 diag.Drag=true;
				 diag.Title ="资料";
				 diag.URL = '/user/goEditU.do?USER_ID='+USER_ID+'&fx=head';
				 diag.Width = 225;
				 diag.Height = 260;
				 diag.CancelEvent = function(){ //关闭事件
					diag.close();
					//setTimeout("self.location.reload()",100);
				 };
				 diag.show();
			}
		</script>
		<div id="breadcrumbs" style="text-align: center;">
			<ul class="breadcrumb" style="font-family: Microsoft YaHei;font-size: 16px;line-height: 40px;">
				<li><a style="color: #555;" href="javascript:;">
				<i class="icon-home"></i> <b>机房监测</b></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</li>
				<li><a style="color: #555;" href="erwei/listErweiNew.do" target="_blank" >
				<i class="icon-picture"></i> <b>二维展示</b></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</li>
				<li><a style="color: #555;" href="resource/listResource.do">
				<i class="icon-save"></i> <b>资源信息</b></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</li>
				<li><a style="color: #555;" href="alarm/listAlarm.do">
				<i class="icon-bell"></i> <b>实时报警</b></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</li>
				<li><a style="color: #555;" href="report/listReport.do">
				<i class="icon-table"></i> <b>统计报表</b></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</li>
				<li><a style="color: #555;" href="http://10.10.21.16:8080/jsp/main/topology/topologyfor18.jsp" target="_blank">
				<i class="icon-table"></i> <b>系统拓扑</b></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</li>
				<li><a style="color: #555;" href="login_index.do">
				<i class="icon-cog"></i> <b>系统管理</b></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</li>
			</ul><!--.breadcrumb-->
		</div><!--#breadcrumbs-->