var currobj;
//判断浏览器是否支持placeholder
function hasPlaceholderSupport() {
	var input = document.createElement('input');
	return ('placeholder' in input);
}
$(document).ready(function(){
	//loadcssfile("header");
	var input = document.createElement('input');
	input.id="searchteacher";
	if(!('placeholder' in input)){
		input.type="text";
		input.value="搜索教师";
		input.onfocus = function() {
			if(this.value==="搜索教师"){
				this.value="";
			}
		};
		input.onblur = function() {
			if(this.value==""){
				this.value="搜索教师";
			}
		};
	}else{
		//input.type="search";
		input.placeholder="搜索教师";
	}
	var homeTeacher = document.getElementById("thome");
	homeTeacher.appendChild(input);

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

function findteacher(){
	var teachername = document.getElementById("searchteacher");
	var rootpath=getRootPath();
	location.href=rootpath + '/bin/jsp/school/teacherlist.jsp?teaname='+teachername.value;
	//alert("teachername.value="+teachername.value);

}

function refashUser(){
	var username = getLoginUserBySession();
	if(username=="NOLOGIN" || username=="ERROR" || username==""){
		document.getElementById("loginbefore").style.display = "block";
		document.getElementById("loginafter").style.display = "none";
	}else{
		document.getElementById("loginusername").innerHTML=username;
		document.getElementById("loginbefore").style.display = "none";
		document.getElementById("loginafter").style.display = "block";
		document.getElementById("userimg").src=getRootPath()+"/bin/js/common/header/user.jpg";
		setMainNav();
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
/*用户样式相关Start*/
var $$ = function (id) {
	return document.getElementById(id);
}
var getByClass = function (oParent, sClass) {
	var aEle = oParent.getElementsByTagName("*");
	var re = new RegExp("\\b" + sClass + "\\b");
	var arr = [];
	for (var i = 0; i < aEle.length; i++) {
		if (re.test(aEle[i].className)) {
			arr.push(aEle[i]);
		}
	}
	return arr;
}
var setMainNav = function () {
	var oMainNav = $$("usermainNav");
	var aLi = getByClass(oMainNav, "user_list")[0].getElementsByTagName("li");
	var aGameHover = getByClass(oMainNav, "curr_user");
	var aHoverCont = getByClass(oMainNav, "user_menu");
	for (var i = 0; i < aGameHover.length; i++) {
		aGameHover[i].index = i;
		aGameHover[i].onmouseover = function () {
			this.className += " "+"curr_user_current";
			for (var j = 0; j < aHoverCont.length; j++) {
				aHoverCont[j].index_j = j;
				aHoverCont[j].style.display = "none";
				aHoverCont[j].onmouseover = function () {
					this.style.display = "block";
					aGameHover[this.index_j].className += " "+"curr_user_current";
				}
				aHoverCont[j].onmouseout = function () {
					this.style.display = "none";
				}
			}
			if (aHoverCont[this.index]) {
				aHoverCont[this.index].style.display = "block";
			}
		}
	}
	for (var i = 0; i < aLi.length; i++) {
		aLi[i].index = i;
		aLi[i].onmouseout = function () {
			if (aHoverCont[this.index]) {
				aHoverCont[this.index].style.display = "none";
			}
			aGameHover[this.index].className = "curr_user";
		}
	}
}
/*用户样式相关End*/
document.writeln("<div class=\"bg_img\">");
document.writeln("	  	<div class=\"wid1004\">");
document.writeln("			 <div class=\"header_right_yyy fr\">");
document.writeln("			 	<span class=\"fr\">");
document.writeln("			 		<span id='loginbefore'>");
document.writeln("                  	<a id='userlogin' class='header-btn' onclick='subclick(this)'>用户登录</a>");
document.writeln("                      <span class=\"line\" style='display:none;'>|</span>");
document.writeln("                      <a id='userregister' class='header-btn' onclick='subclick(this)'>用户注册</a>");
document.writeln("                  </span>");
/*document.writeln("                  <span id='loginafter' >	");
document.writeln("				 		<span id='loginusername' class=\"line\">|</span>");
document.writeln("						<a id='adminpage' style='cursor:pointer;' onclick='subclick(this)'>个人中心</a>");
document.writeln("						<span class=\"line\">|</span>");
document.writeln("						<a onclick='logoutUser()' style=\'cursor:pointer;\'>退出</a>");
document.writeln("                  </span>");*/
document.writeln("                  <span id='loginafter' >	");
document.writeln("                    <div class='user_nav' id='usermainNav'>");
document.writeln("                      <ul class='user_list'>");
document.writeln("                          <li>");
document.writeln("                              <a class='curr_user'><img id='userimg' class='user_img'/><span style='display:none;' id='loginusername'>当前用户</span></a>");
document.writeln("                              <div class='user_menu'>");
document.writeln("                                  <div class='nav_cont'>");
document.writeln("                                      <div class='nav_li'>");
document.writeln("                                          <div class='nav_li_l'>");
document.writeln("                                          	<a id='adminpage' style='cursor:pointer;' onclick='subclick(this)'>个人中心</a>");
document.writeln("                                          </div>");
document.writeln("                                      </div>");
document.writeln("                                      <div class='nav_li' style='border:0;'>");
document.writeln("                                          <div class='nav_li_l'>");
document.writeln("                                          	<a onclick='logoutUser()' style=\'cursor:pointer;\'>退出登录</a>");
document.writeln("                                          </div>");
document.writeln("                                      </div>");
document.writeln("                                  </div>");
document.writeln("                              </div>");
document.writeln("                          </li>");
document.writeln("                      </ul>");
document.writeln("                    </div>");
document.writeln("                  </span>");

document.writeln("				</span>");
document.writeln("			 	<span id='thome' class=\"header_input fr\">");
document.writeln("				 	<span class=\"header_input_le fl\">&nbsp;</span>");
//document.writeln("				 		<input id='searchteacher' type='search' placeholder='搜索教师'/>");
document.writeln("				 	<span class=\"header_input_ri fr\"><a style='cursor:pointer;' onclick='findteacher(this)'><img src=\""+getRootPath()+"/bin/img/resources/header_08.png\"></a></span>");
document.writeln("			 	</span>");
document.writeln("			 </div>");
document.writeln("			 <div class=\"header_left_yyy fl\">");
document.writeln("				<em class=\"icon_header_xing\">&nbsp;</em>你好，欢迎来志翔教育！400-024-8888 ( 早9:00-晚9:00 ) ");
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
document.writeln("				<li class=\"header_ziti\" id='index8' onclick='subclick(this)'>关于志翔</li>");
document.writeln("			</ul>");
document.writeln("		</div>");
document.writeln("	</div>");
document.writeln("");
