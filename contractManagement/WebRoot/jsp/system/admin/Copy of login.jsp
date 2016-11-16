<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
	//out.print(path);
%>
<!DOCTYPE html>
<html lang="en">
    
<head>
        <title>FH后台管理系统</title><meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<link rel="stylesheet" href="<%=path %>/admin00/login/bootstrap.min.css" />
		<link rel="stylesheet" href="<%=path %>/admin00/login/bootstrap-responsive.min.css" />
        <link rel="stylesheet" href="<%=path %>/admin00/login/matrix-login.css" />
        <link href="<%=path %>/admin00/login/font-awesome.css" rel="stylesheet" />
		

	<c:if test="${error != null && error != '' }">
		<script type="text/javascript">
			alert("${error}");
		</script>
	</c:if>
	
	<script type="text/javascript">
		function denglu(){
			$("#loginForm").submit();
		}
		function quxiao(){
			$("#loginname").val('');
			$("#password").val('');
		}
	</script>
	
    </head>
    <body>
        <div id="loginbox">            
            <form action="login_login.do" method="post" name="loginForm" id="loginForm" onsubmit="return check();">
				 <div class="control-group normal_text"> <h3><img src="<%=path %>/admin00/login/logo.png" alt="Logo" /></h3></div>
                <div class="control-group">
                    <div class="controls">
                        <div class="main_input_box">
                            <span class="add-on bg_lg"><i><img height="37" src="<%=path %>/admin00/login/user.png" /></i></span><input type="text" name="loginname" id="loginname" value="${loginname }" placeholder="请输入用户名" />
                        </div>
                    </div>
                </div>
                <div class="control-group">
                    <div class="controls">
                        <div class="main_input_box">
                            <span class="add-on bg_ly"><i><img height="37" src="<%=path %>/admin00/login/suo.png" /></i></span><input  type="password" name="password" id="password" placeholder="请输入密码" value="${password }" />
                        </div>
                    </div>
                </div>
                
                <div class="form-actions" align="center">
                	<div style="width:86%;">
                    <span class="pull-left">
                    	<div style="float: left;">
	                    	<div style="float: left;"><i><img src="<%=path %>/admin00/login/yan.png" /></i></div>
	                    	<div style="float: right;"><input type="text" name="code" id="code" class="login_code" style="height:16px; padding-top: 0px;"/></div>
                    	</div>
                    	<div style="float: right;">&nbsp;<img style="height:22px;" id="codeImg" alt="点击更换" title="点击更换" src=""/></div>
                    </span>
                    <span class="pull-right"><a href="javascript:quxiao();" class="btn btn-success">取消</a></span>
                    <span class="pull-right"><a onclick="denglu();" class="flip-link btn btn-info" id="to-recover">登录</a></span>
                	</div>
                </div>
                
            </form>
            
            
               		<div class="controls">
                        <div class="main_input_box">
                                  	<font color="white"><span id="nameerr">Copyright © FUHANG 2100</span></font>
									<font color="white"><span id="pwderr"></span></font>
									<font color="white"><span id="codeerr"></span></font>
                        </div>
                    </div>
               
            
        </div>
        
	<script type="text/javascript" src="js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript">
		var errInfo = "${errInfo}";
		$(document).ready(function(){
			changeCode();
			$("#codeImg").bind("click",changeCode);
			if(errInfo!=""){
				if(errInfo.indexOf("验证码")>-1){
					$("#nameerr").hide();
					$("#codeerr").show();
					$("#codeerr").html(errInfo);
					$("#code").focus();
				}else{
					$("#nameerr").show();
					$("#nameerr").html(errInfo);
				}
			}
			$("#loginname").focus();
		});
		
		$(document).keyup(function(event){
			  if(event.keyCode ==13){
			    $("#to-recover").trigger("click");
			  }
			});
	
		function genTimestamp(){
			var time = new Date();
			return time.getTime();
		}
	
		function changeCode(){
			$("#codeImg").attr("src","code.do?t="+genTimestamp());
		}
		
		function resetErr(){
			$("#nameerr").hide();
			$("#nameerr").html("");
			$("#pwderr").hide();
			$("#pwderr").html("");
			$("#codeerr").hide();
			$("#codeerr").html("");
		}
		
		function check(){
			resetErr();
			if($("#loginname").val()==""){
				$("#nameerr").show();
				$("#nameerr").html("用户名不得为空！");
				$("#loginname").focus();
				return false;
			}else{
				$("#loginname").val(jQuery.trim($('#loginname').val()));
			}
			
			if($("#password").val()==""){
				$("#pwderr").show();
				$("#pwderr").html("密码不得为空！");
				$("#password").focus();
				return false;
			}
			if($("#code").val()==""){
				$("#codeerr").show();
				$("#codeerr").html("验证码不得为空！");
				$("#code").focus();
				return false;
			}
			
			return true;
		}
		</script>		
		<script>
		//TOCMAT重启之后 点击左侧列表跳转登录首页 
		if (window != top) {
			top.location.href = location.href; 
		}
	  </script>
	
</body>

</html>