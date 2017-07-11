<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.xsjy.servlet.login.ServletLogin"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path;

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>沈阳志翔教育管理系统</title>

<link rel="stylesheet" type="text/css" href="<%=basePath%>/bin/css/resources/logreg.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>/bin/css/resources/newlayout_zy.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>/bin/js/common/header/header.css" id="headercss"/>
<script language="JavaScript" src="<%=basePath%>/bin/js/common.js"></script>
<script language="JavaScript" src="<%=basePath%>/bin/js/jquery-1.9.1.js"></script>
<script language="JavaScript">
document.onkeydown = index_onkeydown;
$(document).ready(function(){
	clearSession();

	var w = window.screen.width;
	var h = window.screen.height;

	window.moveTo(0,0);
    window.resizeTo(w,h);

	$("#username").on("blur",function() {
		if($("#username").val()==""){
			$("#spname").show();
		}

	});

	$("#password").on("blur",function() {
		if($("#password").val()==""){
			$("#sppassword").show();
		}

	});
	$("#username").focus();
});
function cancel_usr(){
	$("#spname").hide();
}
function cancel_pwd(){
	$("#sppassword").hide();
}
function focus_usr(){
	$("#username").focus();
}
function focus_pwd(){
	$("#password").focus();
}

function index_onkeydown(e){
	e = e || event;
	var obj = e.srcElement ? e.srcElement : e.target;
	if(e.keyCode==13){
		if(obj.id=="username"){
			$("#password").focus();
		}else if(obj.id=="password"){
			login_click();
		}
	}
	return true;
}


function login_click(){
	//判断非空
	if($('#username').val() == "" || $('#password').val()==""){
		$('#username').focus();
		return false;
	}

	//通过用户代码取得名称
	$.ajax({
        async:false,
        type: "post",
        dataType: "json",
        url: "<%=basePath%>/ServletLogin",
        data: {
            CMD         : "<%=ServletLogin.CMD_USER_CHECK %>",
            USERID      : $('#username').val(),
            PASSWORD    : $('#password').val()
        },
        complete :function(){},
        success: function(response){
            var CMD = response[0];

            if(CMD=="CMD_OK"){
            	var FLAG = response[1];
            	if(FLAG=="MENUSCHOOL"){
            		/* 跳转教师学生操作页面。 */
            		<%-- var strReferrer = document.referrer;
            		var arr = strReferrer.split("/");
            		if(arr[arr.length-1]=="" || arr[arr.length-1]=="register.jsp" || arr[arr.length-1]=="login.jsp"){
            			location.href='<%=basePath%>';
            		}else{
            			location.href=document.referrer;
            		} --%>
            		location.href='<%=basePath%>/bin/index.jsp';
            	}else{
            		/* 跳转管理操作页面。 */
                    location.href='<%=basePath%>/bin/jsp/home/home.jsp';
            	}
            }else if(CMD=="CMD_NOEXIST"){
               alert("用户不存在。");
               $('#username').focus();
            }else if(CMD=="CMD_PASS_ERR"){
               alert('用户名或密码不正确。');
               $('#password').focus();
            }else if(CMD=="CMD_MENU_NULL"){
	           alert('用户无权限。');
	           $('#password').focus();
            }else if(CMD=="CMD_EXCEPTION"){
            	alert('系统出现异常,请联系管理员。');
		       $('#password').focus();
            }else{
               alert('系统级问题,请联系管理员。');
               $('#password').focus();
            }
        }
    });
}
</script>

</head>
<body>
	<script src="<%=basePath%>/bin/js/common/header/header.js" type="text/javascript"></script>
	<!-- 登录主体 begin -->
	<div class="login_page" style="height:400px;">
		<div class="page_container2">
			<div class="main fix" id="main">
				<div class="login_panel" id="loginPanel">
					<div class="form_login">
						<div class="header fix">
							<h2>登录<span>login</span></h2>
						</div>
						<div>
							<div class="clear">&nbsp;</div>
							<div class="tr input_wrap loginname_wrap">
								<input type="text" name="username" id="username" onkeydown="cancel_usr()" />
								<span onclick="focus_usr()" id="spname" style="color:#999;position:relative;bottom:27px;left:9px;">请输入登录名</span>
							</div>
							<div class="tr input_wrap password_wrap">
								<input type="password" name="password" id="password" onkeydown="cancel_pwd()" />
								<span onclick="focus_pwd()" id="sppassword" style="color:#999;position:relative;bottom:27px;left:9px;">请输入登录密码</span>
							</div>
							<div class="tr submit_wrap">
								<a class="submit" onclick="login_click()"> 登 录 </a><br/>
							</div>
							<ul class="quick_nav fix">
								<li class="first">
									<input type="checkbox" name="remember" checked="checked" class="common_chk" value="true" />自动登录
								</li>
								<li>
									<a href="#" target="_blank" class="forget">忘记密码</a>
								</li>
								<li class="last">
									<a href="<%=basePath%>/bin/jsp/login/register.jsp">免费注册</a>
								</li>
							</ul>
						</div>
					</div>
				</div>

				<div class="main_dividing"></div>

			</div>

		</div>

	</div>


	<!-- 登录主体 end -->
	<script src="<%=basePath%>/bin/js/common/footer/footer_common.js" type="text/javascript"></script>

</body>
</html>