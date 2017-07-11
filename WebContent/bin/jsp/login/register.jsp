<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.xsjy.servlet.login.ServletRegister"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path;

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>志翔教育</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="<%=basePath%>/bin/css/resources/logreg.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath%>/bin/css/resources/newlayout_zy.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>/bin/js/common/header/header.css" id="headercss"/>
<script language="JavaScript" src="<%=basePath%>/bin/js/common.js"></script>
<script language="JavaScript" src="<%=basePath%>/bin/js/jquery-1.9.1.js"></script>
<script language="JavaScript">
$(document).ready(function(){

	$("#phonenumber").on("blur",function() {
		if($("#phonenumber").val()==""){
			$("#spphonenumber").show();
		}

	});
	$("#code").on("blur",function() {
		if($("#code").val()==""){
			$("#spcode").show();
		}

	});
	$("#nickname").on("blur",function() {
		if($("#nickname").val()==""){
			$("#spnickname").show();
		}

	});
	$("#password").on("blur",function() {
		if($("#password").val()==""){
			$("#sppassword").show();
		}

	});
	$("#repassword").on("blur",function() {
		if($("#repassword").val()==""){
			$("#sprepassword").show();
		}

	});
	createCode();

});
function cancel_phonenumber(){
	$("#spphonenumber").hide();
}
function cancel_code(){
	$("#spcode").hide();
}
function cancel_nickname(){
	$("#spnickname").hide();
}
function cancel_pwd(){
	$("#sppassword").hide();
}
function cancel_repwd(){
	$("#sprepassword").hide();
}
function focus_phonenumber(){
	$("#phonenumber").focus();
}
function focus_code(){
	$("#code").focus();
}
function focus_nickname(){
	$("#nickname").focus();
}
function focus_pwd(){
	$("#password").focus();
}
function focus_repwd(){
	$("#repassword").focus();
}
function regist_click(){
	//判断非空
	if($('#phonenumber').val() == ""){
		alert("邮箱不能为空！");
		$('#phonenumber').focus();
		return false;
	}
	if($('#nickname').val() == ""){
		alert("姓名不能为空！");
		$('#nickname').focus();
		return false;
	}
	if($('#password').val() == ""){
		alert("登录密码不能为空！");
		$('#password').focus();
		return false;
	}
	if($('#repassword').val() == ""){
		alert("确认密码不能为空！");
		$('#repassword').focus();
		return false;
	}
	if($('#password').val() != $('#repassword').val()){
		alert("两次输入的密码不一致！");
		$('#password').focus();
		return false;
	}
	if(checkSJHMExist()){
		alert("该手机号码已经注册！");
		return false;
	}

	if($('#code').val().length <=0){
		alert("请输入验证码！");
		return false;
	}else if($('#code').val().toUpperCase() != code.toUpperCase() ){
		alert("验证码输入错误！");
		createCode();
		return false;
	}

	var strSJHM = $('#phonenumber').val();
	var strYHQF=$('input:radio[name="RadioGroup1"]:checked').val();
	var strNC = $('#nickname').val();
	var strDLMM = $('#password').val();
	//用户注册
	$.ajax({
        async:false,
        type: "post",
        dataType: "json",
        url: "<%=basePath%>/ServletRegister",
        data: {
            CMD         : "<%=ServletRegister.CMD_REGIST %>",
            SJHM      : strSJHM,
            YHQF       : strYHQF,
            NC           : strNC,
            DLMM    :strDLMM
        },
        complete :function(){
        },
  		success : function(response) {
			var strResault = response[0];
			if (strResault == "SUCCESS") {
				alert("恭喜您！注册成功！");
				location.href='<%=basePath%>/bin/jsp/login/login.jsp';
			} else{
				alert("对不起！注册失败！");
			}
		}
    });
}
function checkSJHMExist(){
	var blnRet = false;
	var strSJHM = $('#phonenumber').val();

	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/ServletRegister",
		data: {
			CMD    : "<%=ServletRegister.CMD_CHK_EXIST%>",
			SJHM : strSJHM
		},
		complete : function(response) {
		},
		success : function(response) {
			var strResault = response[0];
			if (strResault == "USER_EXIST") {
				blnRet = true;
			} else{
				blnRet = false;
			}
		}
	});
	return blnRet;
}

var code ; //在全局 定义验证码
function createCode(){
	code = new Array();
	var codeLength = 4;//验证码的长度
	var checkCode = document.getElementById("checkCode");
	checkCode.value = "";

	var selectChar = new Array(1,2,3,4,5,6,7,8,9,'A','B','C','D','E','F','G','H','J','K','L','M','N','P','Q','R','S','T','U','V','W','X','Y','Z');

	for(var i=0;i<codeLength;i++) {
	   var charIndex = Math.floor(Math.random()*32);
	   code +=selectChar[charIndex];
	}
	if(code.length != codeLength){
	   createCode();
	}
	checkCode.value = code;
}
</script>
<style>
.checkcode {
	font-family: Arial, 宋体;
	font-style: italic;
	color: green;
	border: 0;
	padding: 2px 3px;
	letter-spacing: 3px;
	font-weight: bolder;
}

.unchanged {
	border: 0;
}
</style>
</head>
<body style="height:100%;">
	<script src="<%=basePath%>/bin/js/common/header/header.js" type="text/javascript"></script>
	<!-- 注册主体 begin -->
	<div class="login_page">
		<div class="page_container">
			<div class="main2 fix">
				<div class="login_panel">
					<div class="form_login">
						<div class="header fix">
							<h2><em>账号注册</em><span>register</span></h2>
						</div>
						<div class="clear">&nbsp;</div>
						<div class="tr input_wrap loginname_wrap">
							<input type="text" id="phonenumber" onkeydown="cancel_phonenumber()" >
							<span onclick="focus_phonenumber()" id="spphonenumber" style="color:#999;position:relative;bottom:27px;left:9px;">邮箱</span>
						</div>
						<div class="tr input_wrap password_wrap">
							<input type="text" id="code" onkeydown="cancel_code()" >
							<span onclick="focus_code()" id="spcode" style="color:#999;position:relative;bottom:27px;left:9px;">验证码</span>
						</div>
						<div class="tr">
							<input type="button" id="checkCode" class="checkcode" style="width:60px" onClick="createCode()" />
							<span class="heshiphone">获取手机验证</span>
							<input class="submit2" onClick="createCode()" value="发送验证码">
						</div>
						<div id = "usertypeimg">
							<p  style="padding:90px 0 0 0;margin:0 0 0 40px;">
								<label style="padding-right:84px;">
								<input type="radio" name="RadioGroup1" value="0" id="RadioGroup1_0"  checked/>
								我是老师
								</label>
								<label>
								<input type="radio" name="RadioGroup1" value="1" id="RadioGroup1_1" />
								我是学生
								</label>
							</p>
						</div>

						<div class="tr input_wrap password_wrap">
							<input type="text" id="nickname" onkeydown="cancel_nickname()" >
							<span onclick="focus_nickname()" id="spnickname" style="color:#999;position:relative;bottom:27px;left:9px;">姓名</span>
						</div>
						<div class="tr input_wrap password_wrap">
							<input type="password" id="password" onkeydown="cancel_pwd()" >
							<span onclick="focus_pwd()" id="sppassword" style="color:#999;position:relative;bottom:27px;left:9px;">请输入登录密码</span>
						</div>
						<div class="tr input_wrap password_wrap">
							<input type="password" id="repassword" onkeydown="cancel_repwd()">
							<span onclick="focus_repwd()" id="sprepassword" style="color:#999;position:relative;bottom:27px;left:9px;">请确认密码</span>
						</div>


						<div class="tr submit_wrap">
							<a class="submit" onclick="regist_click()"> 提 交</a><br/>
						</div>
						<ul class="quick_nav fix">
							<li class="first">
								<input type="checkbox" name="remember" checked="checked" class="common_chk" value="true">我已阅读并接受 网站服务条款
							</li>
							<li class="last">
								<a href="<%=basePath%>/bin/jsp/login/login.jsp">登录</a>
							</li>
						</ul>
					</div>
				</div>
				<div class="main_dividing2"></div>

			</div>
		</div>
	</div>
	<br/><br/><br/>
	<!-- 注册主体 end -->
	<script src="<%=basePath%>/bin/js/common/footer/footer_common.js" type="text/javascript"></script>
</body>
</html>