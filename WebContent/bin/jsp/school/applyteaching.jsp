<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path;

%>      
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=9" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>关于我们</title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>/bin/css/school/contentpage-three.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>/bin/css/resources/logreg.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>/bin/css/resources/newlayout_zy.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>/bin/js/common/header/header.css" id="headercss"/>
<script language="JavaScript" src="<%=basePath%>/bin/js/common.js"></script>
<script language="JavaScript" src="<%=basePath%>/bin/js/jquery-1.9.1.js"></script>
</head>
<body style="background:#f5f9fa;">
	<script src="<%=basePath%>/bin/js/common/header/header.js?id=6" type="text/javascript"></script>
	<div class="body_index">
        <div class="body_index_top"></div>
        <div class="body_index_center">
        
            <div class="step_common step1">
            注册成为会员，登录会员信息<br/>
            1 / 已有秀石帐号：点击这里<a href="<%=basePath%>/bin/jsp/login/login.jsp"> 登录 </a>登录账户；<br/>
            2 / 没有秀石帐号：点击这里<a href="<%=basePath%>/bin/jsp/login/register.jsp"> 注册 </a>注册新账户；<br/>
            若已经登录，请忽略此步骤，查看下一步。       
            </div>
            
            <div class="step_common step2">
            完善个人资料、个人信息、头像等<br/>
            1 / 真实姓名、身份验证信息；<br/>
            2 / 性别、电子邮箱、毕业院校、学历、专业等；<br/>
            3 / 出生日期、家庭住址等；<br/>
            4 / 擅长领域、擅长专业、个人从业经历等。<br/>   
            </div>
            
            <div class="step_common step3">
            审核通过！<br/>
            1 / 通过审核后，可以开班授课；<br/>
            2 / 制定班次计划，制定人员数、开课时间地点等；<br/>
            3 / 课前课后学员了解等；<br/>
            4 / 学生老师对教学质量互评打分。<br/>   
            </div>
            
            <a href="#"><img src="<%=basePath%>/bin/img/school/touming.gif" width="194" height="49" /></a>
            
            <div class="importtt">注意事项</div>
            <div class="importttneir">
            	<b>其他网申请的账号可以使用在秀石培训网站上吗？</b><br/>
            	教师必须注册了秀石培训的账号才可申请开课！<br/><br/>
                <b>老师都是很可靠的吗？</b><br/>
            	秀石网站的教师都是通过实名认证的老师，上传学历证明，验证后标有验证标识，请认准认证教师！<br/><br/>
                <b>在秀石网站补习一定会进步吗？</b><br/>
            	秀石教师秉承因材施教的教学模式，针对不同的孩子用不同的方法，孩子会进步需要靠老师和父母共同努力！<br/><br/>
                <b>秀石的优势在哪里？</b><br/>
            	师资资源，均来自世界名校、中国重点大学，放心保证！<br/><br/>
                <b>秀石可以网上教学吗？</b><br/>
            	现在只有线下教学，日后会逐渐增加此功能，线上教学比较适合主动学习的孩子！<br/><br/>
                <b>可以多次报名不同的班次吗？</b><br/>
            	完全可以，秀石教育支持学生全面发展，不同班次可多次报名！<br/><br/>
            </div>
        </div>
        <!--footer-->
		<script src="<%=basePath%>/bin/js/common/footer/footer_common.js" type="text/javascript"></script>
    </div>
</body>
</html>