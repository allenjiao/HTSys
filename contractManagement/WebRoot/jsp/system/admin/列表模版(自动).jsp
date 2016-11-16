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




<div class="row-fluid">
	<div class="table-header">
		组织管理
	</div>
	
		<table id="table_report" class="table table-striped table-bordered table-hover">
			
			<thead>
				<tr>
					<th class="center">
						<label><input type="checkbox" /><span class="lbl"></span></label>
					</th>
					<th>用户</th>
					<th>价格</th>
					<th class="hidden-480">点击</th>
					<th class="hidden-phone"><i class="icon-time hidden-phone"></i> 更新日期</th>
					<th class="hidden-480">状态</th>
					<th></th>
				</tr>
			</thead>
									
			<tbody>
				
				<tr>
					<td class='center'>
						<label><input type='checkbox' /><span class="lbl"></span></label>
					</td>
					<td><a href='#'>中国</a></td>
					<td>$45</td>
					<td class='hidden-480'>3,330</td>
					<td class='hidden-phone'>Feb 12</td>
					<td class='hidden-480'><span class='label label-warning'>Expiring</span></td>
					<td>
						<div class='hidden-phone visible-desktop btn-group'>
							<button class='btn btn-mini btn-success'><i class='icon-ok'></i></button>
							<button class='btn btn-mini btn-info'><i class='icon-edit'></i></button>
							<button class='btn btn-mini btn-danger'><i class='icon-trash'></i></button>
							<button class='btn btn-mini btn-warning'><i class='icon-flag'></i></button>
						</div>
						<div class='hidden-desktop visible-phone'>
							<div class="inline position-relative">
								<button class="btn btn-minier btn-yellow dropdown-toggle" data-toggle="dropdown"><i class="icon-caret-down icon-only"></i></button>
								<ul class="dropdown-menu dropdown-icon-only dropdown-yellow pull-right dropdown-caret dropdown-close">
									<li><a href="#" class="tooltip-success" data-rel="tooltip" title="Edit" data-placement="left"><span class="green"><i class="icon-edit"></i></span></a></li>
									<li><a href="#" class="tooltip-warning" data-rel="tooltip" title="Flag" data-placement="left"><span class="blue"><i class="icon-flag"></i></span> </a></li>
									<li><a href="#" class="tooltip-error" data-rel="tooltip" title="Delete" data-placement="left"><span class="red"><i class="icon-trash"></i></span> </a></li>
								</ul>
							</div>
						</div>
					</td>
				</tr>
				
				
				<tr>
					<td class='center'>
						<label><input type='checkbox' /><span class="lbl"></span></label>
					</td>
					<td><a href='#'>base.com</a></td>
					<td>$35</td>
					<td class='hidden-480'>2,595</td>
					<td class='hidden-phone'>Feb 18</td>
					<td class='hidden-480'><span class='label label-success'>Registered</span></td>
					<td>
						<div class='hidden-phone visible-desktop btn-group'>
							<button class='btn btn-mini btn-success'><i class='icon-ok'></i></button>
							<button class='btn btn-mini btn-info'><i class='icon-edit'></i></button>
							<button class='btn btn-mini btn-danger'><i class='icon-trash'></i></button>
							<button class='btn btn-mini btn-warning'><i class='icon-flag'></i></button>
						</div>
						<div class='hidden-desktop visible-phone'>
							<div class="inline position-relative">
								<button class="btn btn-minier btn-yellow dropdown-toggle" data-toggle="dropdown"><i class="icon-caret-down icon-only"></i></button>
								<ul class="dropdown-menu dropdown-icon-only dropdown-yellow pull-right dropdown-caret dropdown-close">
									<li><a href="#" class="tooltip-success" data-rel="tooltip" title="Edit" data-placement="left"><span class="green"><i class="icon-edit"></i></span></a></li>
									<li><a href="#" class="tooltip-warning" data-rel="tooltip" title="Flag" data-placement="left"><span class="blue"><i class="icon-flag"></i></span> </a></li>
									<li><a href="#" class="tooltip-error" data-rel="tooltip" title="Delete" data-placement="left"><span class="red"><i class="icon-trash"></i></span> </a></li>
								</ul>
							</div>
						</div>
					</td>
				</tr>
				
				
				<tr>
					<td class='center'>
						<label><input type='checkbox' /><span class="lbl"></span></label>
					</td>
					<td><a href='#'>max.com</a></td>
					<td>$60</td>
					<td class='hidden-480'>4,400</td>
					<td class='hidden-phone'>Mar 11</td>
					<td class='hidden-480'><span class='label label-warning'>Expiring</span></td>
					<td>
						<div class='hidden-phone visible-desktop btn-group'>
							<button class='btn btn-mini btn-success'><i class='icon-ok'></i></button>
							<button class='btn btn-mini btn-info'><i class='icon-edit'></i></button>
							<button class='btn btn-mini btn-danger'><i class='icon-trash'></i></button>
							<button class='btn btn-mini btn-warning'><i class='icon-flag'></i></button>
						</div>
						<div class='hidden-desktop visible-phone'>
							<div class="inline position-relative">
								<button class="btn btn-minier btn-yellow dropdown-toggle" data-toggle="dropdown"><i class="icon-caret-down icon-only"></i></button>
								<ul class="dropdown-menu dropdown-icon-only dropdown-yellow pull-right dropdown-caret dropdown-close">
									<li><a href="#" class="tooltip-success" data-rel="tooltip" title="Edit" data-placement="left"><span class="green"><i class="icon-edit"></i></span></a></li>
									<li><a href="#" class="tooltip-warning" data-rel="tooltip" title="Flag" data-placement="left"><span class="blue"><i class="icon-flag"></i></span> </a></li>
									<li><a href="#" class="tooltip-error" data-rel="tooltip" title="Delete" data-placement="left"><span class="red"><i class="icon-trash"></i></span> </a></li>
								</ul>
							</div>
						</div>
					</td>
				</tr>
				
				
				<tr>
					<td class='center'>
						<label><input type='checkbox' /><span class="lbl"></span></label>
					</td>
					<td><a href='#'>best.com</a></td>
					<td>$75</td>
					<td class='hidden-480'>6,500</td>
					<td class='hidden-phone'>Apr 03</td>
					<td class='hidden-480'><span class='label label-inverse arrowed-in'>Flagged</span></td>
					<td>
						<div class='hidden-phone visible-desktop btn-group'>
							<button class='btn btn-mini btn-success'><i class='icon-ok'></i></button>
							<button class='btn btn-mini btn-info'><i class='icon-edit'></i></button>
							<button class='btn btn-mini btn-danger'><i class='icon-trash'></i></button>
							<button class='btn btn-mini btn-warning'><i class='icon-flag'></i></button>
						</div>
						<div class='hidden-desktop visible-phone'>
							<div class="inline position-relative">
								<button class="btn btn-minier btn-yellow dropdown-toggle" data-toggle="dropdown"><i class="icon-caret-down icon-only"></i></button>
								<ul class="dropdown-menu dropdown-icon-only dropdown-yellow pull-right dropdown-caret dropdown-close">
									<li><a href="#" class="tooltip-success" data-rel="tooltip" title="Edit" data-placement="left"><span class="green"><i class="icon-edit"></i></span></a></li>
									<li><a href="#" class="tooltip-warning" data-rel="tooltip" title="Flag" data-placement="left"><span class="blue"><i class="icon-flag"></i></span> </a></li>
									<li><a href="#" class="tooltip-error" data-rel="tooltip" title="Delete" data-placement="left"><span class="red"><i class="icon-trash"></i></span> </a></li>
								</ul>
							</div>
						</div>
					</td>
				</tr>
				
				
				<tr>
					<td class='center'>
						<label><input type='checkbox' /><span class="lbl"></span></label>
					</td>
					<td><a href='#'>pro.com</a></td>
					<td>$55</td>
					<td class='hidden-480'>4,250</td>
					<td class='hidden-phone'>Jan 21</td>
					<td class='hidden-480'><span class='label label-success'>Registered</span></td>
					<td>
						<div class='hidden-phone visible-desktop btn-group'>
							<button class='btn btn-mini btn-success'><i class='icon-ok'></i></button>
							<button class='btn btn-mini btn-info'><i class='icon-edit'></i></button>
							<button class='btn btn-mini btn-danger'><i class='icon-trash'></i></button>
							<button class='btn btn-mini btn-warning'><i class='icon-flag'></i></button>
						</div>
						<div class='hidden-desktop visible-phone'>
							<div class="inline position-relative">
								<button class="btn btn-minier btn-yellow dropdown-toggle" data-toggle="dropdown"><i class="icon-caret-down icon-only"></i></button>
								<ul class="dropdown-menu dropdown-icon-only dropdown-yellow pull-right dropdown-caret dropdown-close">
									<li><a href="#" class="tooltip-success" data-rel="tooltip" title="Edit" data-placement="left"><span class="green"><i class="icon-edit"></i></span></a></li>
									<li><a href="#" class="tooltip-warning" data-rel="tooltip" title="Flag" data-placement="left"><span class="blue"><i class="icon-flag"></i></span> </a></li>
									<li><a href="#" class="tooltip-error" data-rel="tooltip" title="Delete" data-placement="left"><span class="red"><i class="icon-trash"></i></span> </a></li>
								</ul>
							</div>
						</div>
					</td>
				</tr>
				
				
				<tr>
					<td class='center'>
						<label><input type='checkbox' /><span class="lbl"></span></label>
					</td>
					<td><a href='#'>team.com</a></td>
					<td>$40</td>
					<td class='hidden-480'>3,200</td>
					<td class='hidden-phone'>Feb 09</td>
					<td class='hidden-480'><span class='label label-inverse arrowed-in'>Flagged</span></td>
					<td>
						<div class='hidden-phone visible-desktop btn-group'>
							<button class='btn btn-mini btn-success'><i class='icon-ok'></i></button>
							<button class='btn btn-mini btn-info'><i class='icon-edit'></i></button>
							<button class='btn btn-mini btn-danger'><i class='icon-trash'></i></button>
							<button class='btn btn-mini btn-warning'><i class='icon-flag'></i></button>
						</div>
						<div class='hidden-desktop visible-phone'>
							<div class="inline position-relative">
								<button class="btn btn-minier btn-yellow dropdown-toggle" data-toggle="dropdown"><i class="icon-caret-down icon-only"></i></button>
								<ul class="dropdown-menu dropdown-icon-only dropdown-yellow pull-right dropdown-caret dropdown-close">
									<li><a href="#" class="tooltip-success" data-rel="tooltip" title="Edit" data-placement="left"><span class="green"><i class="icon-edit"></i></span></a></li>
									<li><a href="#" class="tooltip-warning" data-rel="tooltip" title="Flag" data-placement="left"><span class="blue"><i class="icon-flag"></i></span> </a></li>
									<li><a href="#" class="tooltip-error" data-rel="tooltip" title="Delete" data-placement="left"><span class="red"><i class="icon-trash"></i></span> </a></li>
								</ul>
							</div>
						</div>
					</td>
				</tr>
				
				
				<tr>
					<td class='center'>
						<label><input type='checkbox' /><span class="lbl"></span></label>
					</td>
					<td><a href='#'>up.com</a></td>
					<td>$95</td>
					<td class='hidden-480'>8,520</td>
					<td class='hidden-phone'>Feb 22</td>
					<td class='hidden-480'><span class='label label-info arrowed arrowed-right'>Sold</span></td>
					<td>
						<div class='hidden-phone visible-desktop btn-group'>
							<button class='btn btn-mini btn-success'><i class='icon-ok'></i></button>
							<button class='btn btn-mini btn-info'><i class='icon-edit'></i></button>
							<button class='btn btn-mini btn-danger'><i class='icon-trash'></i></button>
							<button class='btn btn-mini btn-warning'><i class='icon-flag'></i></button>
						</div>
						<div class='hidden-desktop visible-phone'>
							<div class="inline position-relative">
								<button class="btn btn-minier btn-yellow dropdown-toggle" data-toggle="dropdown"><i class="icon-caret-down icon-only"></i></button>
								<ul class="dropdown-menu dropdown-icon-only dropdown-yellow pull-right dropdown-caret dropdown-close">
									<li><a href="#" class="tooltip-success" data-rel="tooltip" title="Edit" data-placement="left"><span class="green"><i class="icon-edit"></i></span></a></li>
									<li><a href="#" class="tooltip-warning" data-rel="tooltip" title="Flag" data-placement="left"><span class="blue"><i class="icon-flag"></i></span> </a></li>
									<li><a href="#" class="tooltip-error" data-rel="tooltip" title="Delete" data-placement="left"><span class="red"><i class="icon-trash"></i></span> </a></li>
								</ul>
							</div>
						</div>
					</td>
				</tr>
				
				
				<tr>
					<td class='center'>
						<label><input type='checkbox' /><span class="lbl"></span></label>
					</td>
					<td><a href='#'>view.com</a></td>
					<td>$45</td>
					<td class='hidden-480'>4,100</td>
					<td class='hidden-phone'>Mar 12</td>
					<td class='hidden-480'><span class='label label-success'>Registered</span></td>
					<td>
						<div class='hidden-phone visible-desktop btn-group'>
							<button class='btn btn-mini btn-success'><i class='icon-ok'></i></button>
							<button class='btn btn-mini btn-info'><i class='icon-edit'></i></button>
							<button class='btn btn-mini btn-danger'><i class='icon-trash'></i></button>
							<button class='btn btn-mini btn-warning'><i class='icon-flag'></i></button>
						</div>
						<div class='hidden-desktop visible-phone'>
							<div class="inline position-relative">
								<button class="btn btn-minier btn-yellow dropdown-toggle" data-toggle="dropdown"><i class="icon-caret-down icon-only"></i></button>
								<ul class="dropdown-menu dropdown-icon-only dropdown-yellow pull-right dropdown-caret dropdown-close">
									<li><a href="#" class="tooltip-success" data-rel="tooltip" title="Edit" data-placement="left"><span class="green"><i class="icon-edit"></i></span></a></li>
									<li><a href="#" class="tooltip-warning" data-rel="tooltip" title="Flag" data-placement="left"><span class="blue"><i class="icon-flag"></i></span> </a></li>
									<li><a href="#" class="tooltip-error" data-rel="tooltip" title="Delete" data-placement="left"><span class="red"><i class="icon-trash"></i></span> </a></li>
								</ul>
							</div>
						</div>
					</td>
				</tr>
				
				
				<tr>
					<td class='center'>
						<label><input type='checkbox' /><span class="lbl"></span></label>
					</td>
					<td><a href='#'>nice.com</a></td>
					<td>$38</td>
					<td class='hidden-480'>3,940</td>
					<td class='hidden-phone'>Feb 12</td>
					<td class='hidden-480'><span class='label label-info arrowed arrowed-right'>Sold</span></td>
					<td>
						<div class='hidden-phone visible-desktop btn-group'>
							<button class='btn btn-mini btn-success'><i class='icon-ok'></i></button>
							<button class='btn btn-mini btn-info'><i class='icon-edit'></i></button>
							<button class='btn btn-mini btn-danger'><i class='icon-trash'></i></button>
							<button class='btn btn-mini btn-warning'><i class='icon-flag'></i></button>
						</div>
						<div class='hidden-desktop visible-phone'>
							<div class="inline position-relative">
								<button class="btn btn-minier btn-yellow dropdown-toggle" data-toggle="dropdown"><i class="icon-caret-down icon-only"></i></button>
								<ul class="dropdown-menu dropdown-icon-only dropdown-yellow pull-right dropdown-caret dropdown-close">
									<li><a href="#" class="tooltip-success" data-rel="tooltip" title="Edit" data-placement="left"><span class="green"><i class="icon-edit"></i></span></a></li>
									<li><a href="#" class="tooltip-warning" data-rel="tooltip" title="Flag" data-placement="left"><span class="blue"><i class="icon-flag"></i></span> </a></li>
									<li><a href="#" class="tooltip-error" data-rel="tooltip" title="Delete" data-placement="left"><span class="red"><i class="icon-trash"></i></span> </a></li>
								</ul>
							</div>
						</div>
					</td>
				</tr>
				
				
				<tr>
					<td class='center'>
						<label><input type='checkbox' /><span class="lbl"></span></label>
					</td>
					<td><a href='#'>fine.com</a></td>
					<td>$25</td>
					<td class='hidden-480'>2,983</td>
					<td class='hidden-phone'>Apr 01</td>
					<td class='hidden-480'><span class='label label-warning'>Expiring</span></td>
					<td>
						<div class='hidden-phone visible-desktop btn-group'>
							<button class='btn btn-mini btn-success'><i class='icon-ok'></i></button>
							<button class='btn btn-mini btn-info'><i class='icon-edit'></i></button>
							<button class='btn btn-mini btn-danger'><i class='icon-trash'></i></button>
							<button class='btn btn-mini btn-warning'><i class='icon-flag'></i></button>
						</div>
						<div class='hidden-desktop visible-phone'>
							<div class="inline position-relative">
								<button class="btn btn-minier btn-yellow dropdown-toggle" data-toggle="dropdown"><i class="icon-caret-down icon-only"></i></button>
								<ul class="dropdown-menu dropdown-icon-only dropdown-yellow pull-right dropdown-caret dropdown-close">
									<li><a href="#" class="tooltip-success" data-rel="tooltip" title="Edit" data-placement="left"><span class="green"><i class="icon-edit"></i></span></a></li>
									<li><a href="#" class="tooltip-warning" data-rel="tooltip" title="Flag" data-placement="left"><span class="blue"><i class="icon-flag"></i></span> </a></li>
									<li><a href="#" class="tooltip-error" data-rel="tooltip" title="Delete" data-placement="left"><span class="red"><i class="icon-trash"></i></span> </a></li>
								</ul>
							</div>
						</div>
					</td>
				</tr>
				
				
				<tr>
					<td class='center'>
						<label><input type='checkbox' /><span class="lbl"></span></label>
					</td>
					<td><a href='#'>good.com</a></td>
					<td>$50</td>
					<td class='hidden-480'>6,500</td>
					<td class='hidden-phone'>Feb 02</td>
					<td class='hidden-480'><span class='label label-inverse arrowed-in'>Flagged</span></td>
					<td>
						<div class='hidden-phone visible-desktop btn-group'>
							<button class='btn btn-mini btn-success'><i class='icon-ok'></i></button>
							<button class='btn btn-mini btn-info'><i class='icon-edit'></i></button>
							<button class='btn btn-mini btn-danger'><i class='icon-trash'></i></button>
							<button class='btn btn-mini btn-warning'><i class='icon-flag'></i></button>
						</div>
						<div class='hidden-desktop visible-phone'>
							<div class="inline position-relative">
								<button class="btn btn-minier btn-yellow dropdown-toggle" data-toggle="dropdown"><i class="icon-caret-down icon-only"></i></button>
								<ul class="dropdown-menu dropdown-icon-only dropdown-yellow pull-right dropdown-caret dropdown-close">
									<li><a href="#" class="tooltip-success" data-rel="tooltip" title="Edit" data-placement="left"><span class="green"><i class="icon-edit"></i></span></a></li>
									<li><a href="#" class="tooltip-warning" data-rel="tooltip" title="Flag" data-placement="left"><span class="blue"><i class="icon-flag"></i></span> </a></li>
									<li><a href="#" class="tooltip-error" data-rel="tooltip" title="Delete" data-placement="left"><span class="red"><i class="icon-trash"></i></span> </a></li>
								</ul>
							</div>
						</div>
					</td>
				</tr>
				
				
				<tr>
					<td class='center'>
						<label><input type='checkbox' /><span class="lbl"></span></label>
					</td>
					<td><a href='#'>great.com</a></td>
					<td>$55</td>
					<td class='hidden-480'>6,400</td>
					<td class='hidden-phone'>Feb 24</td>
					<td class='hidden-480'><span class='label label-success'>Registered</span></td>
					<td>
						<div class='hidden-phone visible-desktop btn-group'>
							<button class='btn btn-mini btn-success'><i class='icon-ok'></i></button>
							<button class='btn btn-mini btn-info'><i class='icon-edit'></i></button>
							<button class='btn btn-mini btn-danger'><i class='icon-trash'></i></button>
							<button class='btn btn-mini btn-warning'><i class='icon-flag'></i></button>
						</div>
						<div class='hidden-desktop visible-phone'>
							<div class="inline position-relative">
								<button class="btn btn-minier btn-yellow dropdown-toggle" data-toggle="dropdown"><i class="icon-caret-down icon-only"></i></button>
								<ul class="dropdown-menu dropdown-icon-only dropdown-yellow pull-right dropdown-caret dropdown-close">
									<li><a href="#" class="tooltip-success" data-rel="tooltip" title="Edit" data-placement="left"><span class="green"><i class="icon-edit"></i></span></a></li>
									<li><a href="#" class="tooltip-warning" data-rel="tooltip" title="Flag" data-placement="left"><span class="blue"><i class="icon-flag"></i></span> </a></li>
									<li><a href="#" class="tooltip-error" data-rel="tooltip" title="Delete" data-placement="left"><span class="red"><i class="icon-trash"></i></span> </a></li>
								</ul>
							</div>
						</div>
					</td>
				</tr>
				
				
				<tr>
					<td class='center'>
						<label><input type='checkbox' /><span class="lbl"></span></label>
					</td>
					<td><a href='#'>shine.com</a></td>
					<td>$25</td>
					<td class='hidden-480'>2,200</td>
					<td class='hidden-phone'>Apr 01</td>
					<td class='hidden-480'><span class='label label-inverse arrowed-in'>Flagged</span></td>
					<td>
						<div class='hidden-phone visible-desktop btn-group'>
							<button class='btn btn-mini btn-success'><i class='icon-ok'></i></button>
							<button class='btn btn-mini btn-info'><i class='icon-edit'></i></button>
							<button class='btn btn-mini btn-danger'><i class='icon-trash'></i></button>
							<button class='btn btn-mini btn-warning'><i class='icon-flag'></i></button>
						</div>
						<div class='hidden-desktop visible-phone'>
							<div class="inline position-relative">
								<button class="btn btn-minier btn-yellow dropdown-toggle" data-toggle="dropdown"><i class="icon-caret-down icon-only"></i></button>
								<ul class="dropdown-menu dropdown-icon-only dropdown-yellow pull-right dropdown-caret dropdown-close">
									<li><a href="#" class="tooltip-success" data-rel="tooltip" title="Edit" data-placement="left"><span class="green"><i class="icon-edit"></i></span></a></li>
									<li><a href="#" class="tooltip-warning" data-rel="tooltip" title="Flag" data-placement="left"><span class="blue"><i class="icon-flag"></i></span> </a></li>
									<li><a href="#" class="tooltip-error" data-rel="tooltip" title="Delete" data-placement="left"><span class="red"><i class="icon-trash"></i></span> </a></li>
								</ul>
							</div>
						</div>
					</td>
				</tr>
				
				
				<tr>
					<td class='center'>
						<label><input type='checkbox' /><span class="lbl"></span></label>
					</td>
					<td><a href='#'>rise.com</a></td>
					<td>$42</td>
					<td class='hidden-480'>3,900</td>
					<td class='hidden-phone'>Feb 01</td>
					<td class='hidden-480'><span class='label label-info arrowed arrowed-right'>Sold</span></td>
					<td>
						<div class='hidden-phone visible-desktop btn-group'>
							<button class='btn btn-mini btn-success'><i class='icon-ok'></i></button>
							<button class='btn btn-mini btn-info'><i class='icon-edit'></i></button>
							<button class='btn btn-mini btn-danger'><i class='icon-trash'></i></button>
							<button class='btn btn-mini btn-warning'><i class='icon-flag'></i></button>
						</div>
						<div class='hidden-desktop visible-phone'>
							<div class="inline position-relative">
								<button class="btn btn-minier btn-yellow dropdown-toggle" data-toggle="dropdown"><i class="icon-caret-down icon-only"></i></button>
								<ul class="dropdown-menu dropdown-icon-only dropdown-yellow pull-right dropdown-caret dropdown-close">
									<li><a href="#" class="tooltip-success" data-rel="tooltip" title="Edit" data-placement="left"><span class="green"><i class="icon-edit"></i></span></a></li>
									<li><a href="#" class="tooltip-warning" data-rel="tooltip" title="Flag" data-placement="left"><span class="blue"><i class="icon-flag"></i></span> </a></li>
									<li><a href="#" class="tooltip-error" data-rel="tooltip" title="Delete" data-placement="left"><span class="red"><i class="icon-trash"></i></span> </a></li>
								</ul>
							</div>
						</div>
					</td>
				</tr>
				
				
				<tr>
					<td class='center'>
						<label><input type='checkbox' /><span class="lbl"></span></label>
					</td>
					<td><a href='#'>above.com</a></td>
					<td>$35</td>
					<td class='hidden-480'>3,420</td>
					<td class='hidden-phone'>Mar 12</td>
					<td class='hidden-480'><span class='label label-warning'>Expiring</span></td>
					<td>
						<div class='hidden-phone visible-desktop btn-group'>
							<button class='btn btn-mini btn-success'><i class='icon-ok'></i></button>
							<button class='btn btn-mini btn-info'><i class='icon-edit'></i></button>
							<button class='btn btn-mini btn-danger'><i class='icon-trash'></i></button>
							<button class='btn btn-mini btn-warning'><i class='icon-flag'></i></button>
						</div>
						<div class='hidden-desktop visible-phone'>
							<div class="inline position-relative">
								<button class="btn btn-minier btn-yellow dropdown-toggle" data-toggle="dropdown"><i class="icon-caret-down icon-only"></i></button>
								<ul class="dropdown-menu dropdown-icon-only dropdown-yellow pull-right dropdown-caret dropdown-close">
									<li><a href="#" class="tooltip-success" data-rel="tooltip" title="Edit" data-placement="left"><span class="green"><i class="icon-edit"></i></span></a></li>
									<li><a href="#" class="tooltip-warning" data-rel="tooltip" title="Flag" data-placement="left"><span class="blue"><i class="icon-flag"></i></span> </a></li>
									<li><a href="#" class="tooltip-error" data-rel="tooltip" title="Delete" data-placement="left"><span class="red"><i class="icon-trash"></i></span> </a></li>
								</ul>
							</div>
						</div>
					</td>
				</tr>
				
				
				<tr>
					<td class='center'>
						<label><input type='checkbox' /><span class="lbl"></span></label>
					</td>
					<td><a href='#'>share.com</a></td>
					<td>$30</td>
					<td class='hidden-480'>3,200</td>
					<td class='hidden-phone'>Feb 11</td>
					<td class='hidden-480'><span class='label label-info arrowed arrowed-right'>Sold</span></td>
					<td>
						<div class='hidden-phone visible-desktop btn-group'>
							<button class='btn btn-mini btn-success'><i class='icon-ok'></i></button>
							<button class='btn btn-mini btn-info'><i class='icon-edit'></i></button>
							<button class='btn btn-mini btn-danger'><i class='icon-trash'></i></button>
							<button class='btn btn-mini btn-warning'><i class='icon-flag'></i></button>
						</div>
						<div class='hidden-desktop visible-phone'>
							<div class="inline position-relative">
								<button class="btn btn-minier btn-yellow dropdown-toggle" data-toggle="dropdown"><i class="icon-caret-down icon-only"></i></button>
								<ul class="dropdown-menu dropdown-icon-only dropdown-yellow pull-right dropdown-caret dropdown-close">
									<li><a href="#" class="tooltip-success" data-rel="tooltip" title="Edit" data-placement="left"><span class="green"><i class="icon-edit"></i></span></a></li>
									<li><a href="#" class="tooltip-warning" data-rel="tooltip" title="Flag" data-placement="left"><span class="blue"><i class="icon-flag"></i></span> </a></li>
									<li><a href="#" class="tooltip-error" data-rel="tooltip" title="Delete" data-placement="left"><span class="red"><i class="icon-trash"></i></span> </a></li>
								</ul>
							</div>
						</div>
					</td>
				</tr>
				
				
				<tr>
					<td class='center'>
						<label><input type='checkbox' /><span class="lbl"></span></label>
					</td>
					<td><a href='#'>fair.com</a></td>
					<td>$35</td>
					<td class='hidden-480'>3,900</td>
					<td class='hidden-phone'>Mar 26</td>
					<td class='hidden-480'><span class='label label-inverse arrowed-in'>Flagged</span></td>
					<td>
						<div class='hidden-phone visible-desktop btn-group'>
							<button class='btn btn-mini btn-success'><i class='icon-ok'></i></button>
							<button class='btn btn-mini btn-info'><i class='icon-edit'></i></button>
							<button class='btn btn-mini btn-danger'><i class='icon-trash'></i></button>
							<button class='btn btn-mini btn-warning'><i class='icon-flag'></i></button>
						</div>
						<div class='hidden-desktop visible-phone'>
							<div class="inline position-relative">
								<button class="btn btn-minier btn-yellow dropdown-toggle" data-toggle="dropdown"><i class="icon-caret-down icon-only"></i></button>
								<ul class="dropdown-menu dropdown-icon-only dropdown-yellow pull-right dropdown-caret dropdown-close">
									<li><a href="#" class="tooltip-success" data-rel="tooltip" title="Edit" data-placement="left"><span class="green"><i class="icon-edit"></i></span></a></li>
									<li><a href="#" class="tooltip-warning" data-rel="tooltip" title="Flag" data-placement="left"><span class="blue"><i class="icon-flag"></i></span> </a></li>
									<li><a href="#" class="tooltip-error" data-rel="tooltip" title="Delete" data-placement="left"><span class="red"><i class="icon-trash"></i></span> </a></li>
								</ul>
							</div>
						</div>
					</td>
				</tr>
				
				
				<tr>
					<td class='center'>
						<label><input type='checkbox' /><span class="lbl"></span></label>
					</td>
					<td><a href='#'>year.com</a></td>
					<td>$48</td>
					<td class='hidden-480'>3,990</td>
					<td class='hidden-phone'>Feb 15</td>
					<td class='hidden-480'><span class='label label-warning'>Expiring</span></td>
					<td>
						<div class='hidden-phone visible-desktop btn-group'>
							<button class='btn btn-mini btn-success'><i class='icon-ok'></i></button>
							<button class='btn btn-mini btn-info'><i class='icon-edit'></i></button>
							<button class='btn btn-mini btn-danger'><i class='icon-trash'></i></button>
							<button class='btn btn-mini btn-warning'><i class='icon-flag'></i></button>
						</div>
						<div class='hidden-desktop visible-phone'>
							<div class="inline position-relative">
								<button class="btn btn-minier btn-yellow dropdown-toggle" data-toggle="dropdown"><i class="icon-caret-down icon-only"></i></button>
								<ul class="dropdown-menu dropdown-icon-only dropdown-yellow pull-right dropdown-caret dropdown-close">
									<li><a href="#" class="tooltip-success" data-rel="tooltip" title="Edit" data-placement="left"><span class="green"><i class="icon-edit"></i></span></a></li>
									<li><a href="#" class="tooltip-warning" data-rel="tooltip" title="Flag" data-placement="left"><span class="blue"><i class="icon-flag"></i></span> </a></li>
									<li><a href="#" class="tooltip-error" data-rel="tooltip" title="Delete" data-placement="left"><span class="red"><i class="icon-trash"></i></span> </a></li>
								</ul>
							</div>
						</div>
					</td>
				</tr>
				
				
				<tr>
					<td class='center'>
						<label><input type='checkbox' /><span class="lbl"></span></label>
					</td>
					<td><a href='#'>day.com</a></td>
					<td>$55</td>
					<td class='hidden-480'>5,600</td>
					<td class='hidden-phone'>Jan 29</td>
					<td class='hidden-480'><span class='label label-info arrowed arrowed-right'>Sold</span></td>
					<td>
						<div class='hidden-phone visible-desktop btn-group'>
							<button class='btn btn-mini btn-success'><i class='icon-ok'></i></button>
							<button class='btn btn-mini btn-info'><i class='icon-edit'></i></button>
							<button class='btn btn-mini btn-danger'><i class='icon-trash'></i></button>
							<button class='btn btn-mini btn-warning'><i class='icon-flag'></i></button>
						</div>
						<div class='hidden-desktop visible-phone'>
							<div class="inline position-relative">
								<button class="btn btn-minier btn-yellow dropdown-toggle" data-toggle="dropdown"><i class="icon-caret-down icon-only"></i></button>
								<ul class="dropdown-menu dropdown-icon-only dropdown-yellow pull-right dropdown-caret dropdown-close">
									<li><a href="#" class="tooltip-success" data-rel="tooltip" title="Edit" data-placement="left"><span class="green"><i class="icon-edit"></i></span></a></li>
									<li><a href="#" class="tooltip-warning" data-rel="tooltip" title="Flag" data-placement="left"><span class="blue"><i class="icon-flag"></i></span> </a></li>
									<li><a href="#" class="tooltip-error" data-rel="tooltip" title="Delete" data-placement="left"><span class="red"><i class="icon-trash"></i></span> </a></li>
								</ul>
							</div>
						</div>
					</td>
				</tr>
				
				
				<tr>
					<td class='center'>
						<label><input type='checkbox' /><span class="lbl"></span></label>
					</td>
					<td><a href='#'>light.com</a></td>
					<td>$40</td>
					<td class='hidden-480'>3,100</td>
					<td class='hidden-phone'>Feb 17</td>
					<td class='hidden-480'><span class='label label-success'>Registered</span></td>
					<td>
						<div class='hidden-phone visible-desktop btn-group'>
							<button class='btn btn-mini btn-success'><i class='icon-ok'></i></button>
							<button class='btn btn-mini btn-info'><i class='icon-edit'></i></button>
							<button class='btn btn-mini btn-danger'><i class='icon-trash'></i></button>
							<button class='btn btn-mini btn-warning'><i class='icon-flag'></i></button>
						</div>
						<div class='hidden-desktop visible-phone'>
							<div class="inline position-relative">
								<button class="btn btn-minier btn-yellow dropdown-toggle" data-toggle="dropdown"><i class="icon-caret-down icon-only"></i></button>
								<ul class="dropdown-menu dropdown-icon-only dropdown-yellow pull-right dropdown-caret dropdown-close">
									<li><a href="#" class="tooltip-success" data-rel="tooltip" title="Edit" data-placement="left"><span class="green"><i class="icon-edit"></i></span></a></li>
									<li><a href="#" class="tooltip-warning" data-rel="tooltip" title="Flag" data-placement="left"><span class="blue"><i class="icon-flag"></i></span> </a></li>
									<li><a href="#" class="tooltip-error" data-rel="tooltip" title="Delete" data-placement="left"><span class="red"><i class="icon-trash"></i></span> </a></li>
								</ul>
							</div>
						</div>
					</td>
				</tr>
				
				
				
				<tr>
					<td class='center'>
						<label><input type='checkbox' /><span class="lbl"></span></label>
					</td>
					<td><a href='#'>right.com</a></td>
					<td>$50</td>
					<td class='hidden-480'>4,400</td>
					<td class='hidden-phone'>Apr 01</td>
					<td class='hidden-480'><span class='label label-warning'>Expiring</span></td>
					<td>
						<div class='hidden-phone visible-desktop btn-group'>
							<button class='btn btn-mini btn-success'><i class='icon-ok'></i></button>
							<button class='btn btn-mini btn-info'><i class='icon-edit'></i></button>
							<button class='btn btn-mini btn-danger'><i class='icon-trash'></i></button>
							<button class='btn btn-mini btn-warning'><i class='icon-flag'></i></button>
						</div>
						<div class='hidden-desktop visible-phone'>
							<div class="inline position-relative">
								<button class="btn btn-minier btn-yellow dropdown-toggle" data-toggle="dropdown"><i class="icon-caret-down icon-only"></i></button>
								<ul class="dropdown-menu dropdown-icon-only dropdown-yellow pull-right dropdown-caret dropdown-close">
									<li><a href="#" class="tooltip-success" data-rel="tooltip" title="Edit" data-placement="left"><span class="green"><i class="icon-edit"></i></span></a></li>
									<li><a href="#" class="tooltip-warning" data-rel="tooltip" title="Flag" data-placement="left"><span class="blue"><i class="icon-flag"></i></span> </a></li>
									<li><a href="#" class="tooltip-error" data-rel="tooltip" title="Delete" data-placement="left"><span class="red"><i class="icon-trash"></i></span> </a></li>
								</ul>
							</div>
						</div>
					</td>
				</tr>
				
				
				
			</tbody>
		</table>
	
</div>
 	<div class="page-header position-relative">
		<button class="btn btn-small btn-success" onclick="add('1');">新增</button>
		
		<button class="btn btn-small btn-danger">删除</button>
	</div><!--/page-header-->
 
<!-- PAGE CONTENT ENDS HERE -->
						 </div><!--/row-->
	
					</div><!--/#page-content-->
			</div><!-- #main-content -->
		</div><!--/.fluid-container#main-container-->
		<a href="#" id="btn-scroll-up" class="btn btn-small btn-inverse">
			<i class="icon-double-angle-up icon-only"></i>
		</a>
		<!-- basic scripts -->
		<script src="1.9.1/jquery.min.js"></script>
		<script type="text/javascript">
		window.jQuery || document.write("<script src='js/jquery-1.9.1.min.js'>\x3C/script>");
		</script>
		
		<script src="js/bootstrap.min.js"></script>
		<!-- page specific plugin scripts -->
		
		<script type="text/javascript" src="js/jquery.dataTables.min.js"></script>
		<script type="text/javascript" src="js/jquery.dataTables.bootstrap.js"></script>
		<!-- ace scripts -->
		<script src="js/ace-elements.min.js"></script>
		<script src="js/ace.min.js"></script>
		<!-- inline scripts related to this page -->
		
		<script type="text/javascript">
		$(function() {
			var oTable1 = $('#table_report').dataTable( {
			"aoColumns": [
		      { "bSortable": false },
		      null, null,null, null, null,
			  { "bSortable": false }
			] } );
			
			
			$('table th input:checkbox').on('click' , function(){
				var that = this;
				$(this).closest('table').find('tr > td:first-child input:checkbox')
				.each(function(){
					this.checked = that.checked;
					$(this).closest('tr').toggleClass('selected');
				});
					
			});
			$('[data-rel=tooltip]').tooltip();
		})
		</script>

		
		<!--引入弹窗组件start-->
		<script type="text/javascript" src="js/attention/zDialog/zDrag.js"></script>
		<script type="text/javascript" src="js/attention/zDialog/zDialog.js"></script>
		<!--引入弹窗组件end-->
		<script type="text/javascript">

		/*
		* 新增
		*/
		function add(pid){
		
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="新增职位";
			 diag.URL = 'role/toAdd.do?parent_id='+pid;
			 diag.Width = 500;
			 diag.Height = 300;
			 diag.CancelEvent = function(){ //关闭事件
				diag.close();
				//setTimeout("self.location.reload()",100);
			 };
			 diag.show();

		}
		
		</script>
	</body>
</html>

