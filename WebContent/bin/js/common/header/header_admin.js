$(document).ready(function(){
	alert("start");
	loadcssfile("header_admin");
	alert("end");
})
document.writeln("<div id='header' class='toolbar'>");
document.writeln("	  <div class=\"tbbox clearfix\">");
document.writeln("		 <div class=\"site_r fr\">");
/*document.writeln("			 <div class=\"fl\">");
document.writeln("			 <span class=\"blue_zy\"><input name=\"\" type=\"text\" value=\"输入教师 / 科目 / 学校\"/></span>");
document.writeln("			 <span class=\"blue_zy\"><span class=\"line\">-</span></span>");
document.writeln("			 </div>");*/
document.writeln("			 <div class=\"fl\">");
document.writeln("				 <span class=\"blue_zy\"><a style='color: #fff;' class='header-btn' href=\""+getRootPath()+"/bin/index.jsp\">秀石培训</a><span class=\"line\">|</span></span>");
document.writeln("				 <span class=\"blue_zy\"><a class='header-btn' style='color: #fff;' href=\""+getRootPath()+"/bin/jsp/login/login.jsp\">退出</a></span>");
document.writeln("			 </div>");
document.writeln("		 </div>");
document.writeln("	  </div>");
document.writeln("	</div>");