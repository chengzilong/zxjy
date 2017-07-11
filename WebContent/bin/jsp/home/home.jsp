<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.xsjy.pojo.BaseTable.Pojo_YHXX" %>
<%@ page import="com.framework.session.SessionAttribute"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path;
	Pojo_YHXX user = (Pojo_YHXX)session.getAttribute(SessionAttribute.LOGIN_USER);
	String menuList = (String)session.getAttribute(SessionAttribute.LOGIN_MENU_JSON);
	String userInfo = (String)session.getAttribute(SessionAttribute.USER_INFO_JSON);
	String test = "";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>沈阳秀石教育管理系统</title>
<script type="text/javascript" src="<%=basePath%>/bin/js/common.js"></script>
<script type="text/javascript" src="<%=basePath%>/bin/js/menuschool.js"></script>
<script type="text/javascript" src="<%=basePath%>/bin/js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="<%=basePath%>/bin/js/json2.js"></script>

<link rel="stylesheet" href="<%=basePath%>/bin/css/resources/newlayout_zy.css" type="text/css" />
<link rel="stylesheet" href="<%=basePath%>/bin/css/resources/reset.css" type="text/css" media="screen" />
<link rel="stylesheet" href="<%=basePath%>/bin/css/resources/style.css" type="text/css" media="screen" />
<link rel="stylesheet" href="<%=basePath%>/bin/css/resources/invalid.css" type="text/css" media="screen" />
<link rel="stylesheet" href="<%=basePath%>/bin/css/menuschool.css" type="text/css" media="screen" />
<link rel="stylesheet" href="<%=basePath%>/bin/css/global.css" type="text/css" media="screen" />	

<script type="text/javascript" src="<%=basePath%>/bin/js/plupload/js/plupload.js"></script>
<script type="text/javascript" src="<%=basePath%>/bin/js/plupload/js/plupload.gears.js"></script>
<script type="text/javascript" src="<%=basePath%>/bin/js/plupload/js/plupload.silverlight.js"></script>
<script type="text/javascript" src="<%=basePath%>/bin/js/plupload/js/plupload.flash.js"></script>
<script type="text/javascript" src="<%=basePath%>/bin/js/plupload/js/plupload.browserplus.js"></script>
<script type="text/javascript" src="<%=basePath%>/bin/js/plupload/js/plupload.html4.js"></script>
<script type="text/javascript" src="<%=basePath%>/bin/js/plupload/js/plupload.html5.js"></script>



<script type="text/javascript">
var basePath='<%=basePath%>';
var userInfo=eval('<%=userInfo%>');
var userid='<%=user.getYHXX_YHID()%>';

window.onload=function(){
    changeDivHeight();
    if(userInfo!=null){
        //$('#USER_NAME').html(userInfo[0].USER_NAME);
        $('#INFO_MAST').html(userInfo[0].INFO_MAST);
        $('#INFO_SLAV').html(userInfo[0].INFO_SLAV);
        var menuid = userInfo[0].INFO_MSID;
        setUserName();
        setMessCount(userInfo[0].INFO_MSID);
        var headpic = userInfo[0].HEAD_PICS;
        if(headpic!=""){
        	$('#logo').attr('src','<%=basePath%>/bin/' + headpic);
        }
    }
    
};
function setUserName(){
	var userName = getUserName();
	if(userName!=""){
    	$('#USER_NAME').html("<a  style='cursor:pointer;'>"+userName+"</a>");	
    }else{
    	$('#USER_NAME').html("未知");
    }
}
function setMessCount(programId){
	var messCount = getUserMessCount();
	if(messCount>0){
    	$('#MESS_CONT').html("<a onclick='openMenuById("+programId+")' style='cursor:pointer;'>你有"+messCount+"条未读消息</a>");	
    }else{
    	$('#MESS_CONT').html("你有0条未读消息");
    }
}

function changeDivHeight(){   
   //设定菜单高度
   /* var menuHeight = document.body.clientHeight - $('#header').height() +30;
   $('#menu_container').height(menuHeight); */
	
   //设定主区域高度和宽度
   var mainHeight = document.body.clientHeight - $('#header').height()-5;
   var mainWidth = document.body.clientWidth - $('#sidebar').width()-30 ;
   $('#main_frame').height(mainHeight);
   $('#main_frame').width(mainWidth);  
}

function logout(){
	location.href='<%=basePath%>/bin/jsp/login/login.jsp';
}

</script>
</head>
<body>
<div id="mainContent">
	<script src="<%=basePath%>/bin/js/header_login.js" type="text/javascript"></script>
	<!-- 侧边导航 -->
	<div id="sidebar">
		<div class="bgimg">
		<div id="sidebar-wrapper">
			<h1 id="sidebar-title">
				<a href="#"></a>
			</h1>
			<div id="container" style="text-align: center;">
				<img id="logo" style='cursor:pointer;' />
				<!-- <input type="button" id="container" value="上传头像"> -->
			</div>
			<div id="profile-links">
				你好, <a id="USER_NAME"></a>,<span id="MESS_CONT"></span><br />
				<br />
				<span id="INFO_MAST"></span> | <span id="INFO_SLAV"></span>
			</div>      
			<ul id="main-nav"></ul>
		</div>
		</div>
	</div>
	<iframe id="main_frame" name="main_frame" src="" marginwidth=0 marginheight=0 frameborder=0 scrolling="no">
	
	</iframe>
</div>
<script type="text/javascript">
//加载菜单
function loadMenu() {
	var menuContainer = document.getElementById("main-nav");
	makeMenuSchool(menuContainer,<%=menuList%>);
}

loadMenu();
</script>
</body>
<script type="text/javascript">
 var uploader = new plupload.Uploader({
	    runtimes : 'flash',
	    browse_button : 'pickfiles',
	    container: 'container',
	    max_file_size : '60kb',
	    url : '<%=basePath%>/FileUploadServlet',
	    resize : {width : 320, height : 240, quality : 90},
	    flash_swf_url : '<%=basePath%>/bin/js/plupload/js/plupload.flash.swf',
	    filters : [
	       {title : "Image files", extensions : "jpg"}
	    ]
	 });

	 uploader.init();
	 
	 uploader.bind('Error', function(up, err) {
	     if(err.code=="-600"){
	    	 alert("照片文件大小应小于60K。");
	     }
	  });
	 
	 uploader.bind('FilesAdded', function(up, files) {
		 uploader.start();
		});
	 uploader.bind('FileUploaded', function(up, file, info) {
		 if(file.status=="5"){
			   //上传成功，返回文件名称.
			   var fileSrc = info.response;
			   $('#logo').attr('src','<%=basePath%>/bin/upload/pics/' + fileSrc);
		}
	});
</script>
</html>