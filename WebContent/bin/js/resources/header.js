var currobj;
$(document).ready(function(){
	refashUser();
	var a = document.scripts;
	for(var i=0;i<a.length;i++){
		var b = a[i].src;
		if(a[i].src.indexOf("header.js")>-1){
			var idvalue=a[i].src.substring(a[i].src.lastIndexOf("?")+4);
			if(idvalue!="" && typeof(idvalue)!="undefined"){
				for(var id=1;id<9;id++){
					if(idvalue==id){
						currobj=document.getElementById("index"+id);
						currobj.className="header_nav_curr";
					}else{
						currobj=document.getElementById("index"+id);
						currobj.className="header_ziti";
					}
				}
				return;
			}
		}
	}
	
})

function refashUser(){
	var username = getLoginUserBySession();
	if(username=="NOLOGIN" || username=="ERROR" || username==""){
		document.getElementById("loginbefore").style.display = "block";
		document.getElementById("loginafter").style.display = "none";
	}else{
		document.getElementById("loginusername").innerHTML=username;
		document.getElementById("loginbefore").style.display = "none";
		document.getElementById("loginafter").style.display = "block";
	}	
}

function subclick(obj){
	var objid = obj.id;
	var rootpath=getRootPath();
	if(objid=="index1"){
		location.href=rootpath + '/bin/index.jsp';
	}else if(objid=="index2"){
		location.href=rootpath + '/bin/jsp/school/teacherlist.jsp';
	}else if(objid=="index3"){
		location.href=rootpath + '/bin/jsp/school/lessionlist.jsp';
	}else if(objid=="index4"){
		location.href=rootpath + '/bin/jsp/school/classlist.jsp';
	}else if(objid=="index5"){
		location.href=rootpath + '/bin/jsp/school/resources.jsp';
	}else if(objid=="index6"){
		location.href=rootpath + '/bin/jsp/school/applyteaching.jsp';
	}else if(objid=="index7"){
		location.href=rootpath + '/bin/jsp/school/contactus.jsp';
	}else if(objid=="index8"){
		location.href=rootpath + '/bin/jsp/school/aboutus.jsp';
	}else if(objid=="userlogin"){
		location.href=rootpath + '/bin/jsp/login/login.jsp';
	}else if(objid=="userregister"){
		location.href=rootpath + '/bin/jsp/login/register.jsp';
	}else if(objid=="adminpage"){
		location.href=rootpath + '/bin/jsp/home/home.jsp';
	}

}

function logoutUser(){
	clearSession();
	refashUser();
}
document.writeln("<div class=\"bg_img\">");
document.writeln("	  	<div class=\"wid1004\">");
document.writeln("			 <div class=\"header_right_yyy fr\">");
document.writeln("			 	<span class=\"fr\">");
document.writeln("			 		<span id='loginbefore'>");
document.writeln("                  	<a id='userlogin' style='cursor:pointer;' onclick='subclick(this)'>用户登录</a>");
document.writeln("                      <span class=\"line\"> | </span>");
document.writeln("                      <a id='userregister' style='cursor:pointer;' onclick='subclick(this)'>用户注册</a>");
document.writeln("                  </span>");
document.writeln("                  <span id='loginafter' >	");
document.writeln("				 			<span id='loginusername' class=\"line\">|</span>");
document.writeln("							<a id='adminpage' style='cursor:pointer;' onclick='subclick(this)'>个人中心</a>");
document.writeln("							<span class=\"line\">|</span>");
document.writeln("						<a onclick='logoutUser()' style=\'cursor:pointer;\'>退出</a>");
document.writeln("                  </span>");

document.writeln("				</span>");
document.writeln("			 	<span class=\"header_input fr\">");
document.writeln("				 	<span class=\"header_input_le fl\">&nbsp;</span>");
document.writeln("				 	<input name=\"\" type=\"text\" placeholder=\"输入教师 / 科目 / 学校\"/>");
document.writeln("				 	<span class=\"header_input_ri fr\"><a href=\"#\"><img src=\""+getRootPath()+"/bin/img/resources/header_08.png\"></a></span>");
document.writeln("			 	</span>");
document.writeln("			 </div>");
document.writeln("			 <div class=\"header_left_yyy fl\">");
document.writeln("				<em class=\"icon_header_xing\">&nbsp;</em>你好，欢迎来秀石培训！400-000-0000 ( 早9:00-晚9:00 ) ");
document.writeln("		     </div>");
document.writeln("		 </div>");
document.writeln("	</div>");
document.writeln("	<div class=\"bg_img_nav\">");
document.writeln("		<div class=\"wid960\">");
document.writeln("			<ul>");
document.writeln("				<li class=\"header_nav_curr\" id='index1' onclick='subclick(this)'>首页</li>");
document.writeln("				<li class=\"header_ziti\" id='index2' onclick='subclick(this)'>教师查找</li>");
document.writeln("				<li class=\"header_ziti\" id='index3' onclick='subclick(this)'>课程信息</li>");
document.writeln("				<li class=\"header_ziti\" id='index4' onclick='subclick(this)'>最新开班</li>");
document.writeln("				<li class=\"header_ziti\" id='index5' onclick='subclick(this)'>师资力量</li>");
document.writeln("				<li class=\"header_ziti\" id='index6' onclick='subclick(this)'>申请教学</li>");
document.writeln("				<li class=\"header_ziti\" id='index7' onclick='subclick(this)'>联系我们</li>");
document.writeln("				<li class=\"header_ziti\" id='index8' onclick='subclick(this)'>关于秀石</li>");
document.writeln("			</ul>");
document.writeln("		</div>");
document.writeln("	</div>");
document.writeln("");
