//定义全局变量
var jxxd="";
var qjxk="";
var kclx="";
var sksd="";
var skqy="";
var kcfy="";
var page="";
var pageCount;
var limit;
$(document).ready(function() {
	//教学学段
	var teaching_section_a = $("#teaching_section li[class!='fg']").find("a");
	teaching_section_a.each(function(i, ele){
		$(ele).click(function() {
			teaching_section_a.removeClass("active");
			$(this).addClass("active");
			jxxd = $(this).attr("id");
			selectlist(jxxd,qjxk,kclx,sksd,skqy,kcfy);
	    	//fenye();
		});
	});
	//求教学科
	var for_discipline_a = $("#for_discipline li[class!='fg']").find("a");
	for_discipline_a.each(function(i, ele){
		$(ele).click(function() {
			for_discipline_a.removeClass("active");
			$(this).addClass("active");
			qjxk = $(this).attr("id");
			selectlist(jxxd,qjxk,kclx,sksd,skqy,kcfy);
	    	//fenye();
		});
	});
	//课程类型
	var class_type_a = $("#class_type li[class!='fg']").find("a");
	class_type_a.each(function(i, ele){
		$(ele).click(function() {
			class_type_a.removeClass("active");
			$(this).addClass("active");
			kclx = $(this).attr("id");
			selectlist(jxxd,qjxk,kclx,sksd,skqy,kcfy);
	    	//fenye();
		});
	});
	//上课时段
	var class_time_a = $("#class_time li[class!='fg']").find("a");
	class_time_a.each(function(i, ele){
		$(ele).click(function() {
			class_time_a.removeClass("active");
			$(this).addClass("active");
			sksd = $(this).attr("id");
			selectlist(jxxd,qjxk,kclx,sksd,skqy,kcfy);
	    	//fenye();
		});
	});
	//授课区域
	var teachering_area_a = $("#teachering_area li[class!='fg']").find("a");
	teachering_area_a.each(function(i, ele){
		$(ele).click(function() {
			teachering_area_a.removeClass("active");
			$(this).addClass("active");
			skqy = $(this).attr("id");
			selectlist(jxxd,qjxk,kclx,sksd,skqy,kcfy);
	    	//fenye();
		});
	});
	//课程费用
	var course_fee_a = $("#course_fee li[class!='fg']").find("a");
	course_fee_a.each(function(i, ele){
		$(ele).click(function() {
			course_fee_a.removeClass("active");
			$(this).addClass("active");
			kcfy = $(this).attr("id");
			selectlist(jxxd,qjxk,kclx,sksd,skqy,kcfy);
	    	//fenye();
		});
	});
	//初始化分页
//	fenye();
});
document.writeln("<div class=\"category-list mb20\">");
document.writeln("			<div class=\"category-info\">");
document.writeln("				<div class=\"category-class\">");
document.writeln("					<span class=\"tit-h bk\">教学学段</span>");
document.writeln("					<div class=\"cont bk\">");
document.writeln("						<ul id=\"teaching_section\" class=\"clearfix z1\">");
document.writeln("							<li><a id=\"000\" href=\"#\" class=\"active\"><span>不限</span></a></li><li class=\"fg\">|</li>");
document.writeln("							<li><a id=\"1\" href=\"#\" ><span>初中</span></a></li><li class=\"fg\">|</li>");
document.writeln("							<li><a id=\"2\" href=\"#\" ><span>高中</span></a></li><li class=\"fg\">|</li>");
document.writeln("						</ul>");
document.writeln("					</div>");
document.writeln("				</div>");
document.writeln("				<div class=\"category-class\">");
document.writeln("					<span class=\"tit-h bk\">求教学科</span>");
document.writeln("					<div class=\"cont bk\">");
document.writeln("						<ul id=\"for_discipline\" class=\"clearfix z1\">");
document.writeln("							<li><a id=\"000\" href=\"#\" class=\"active\"><span>不限</span></a></li><li class=\"fg\">|</li>");
document.writeln("							<li><a id=\"191164FE39D243E0A6D677A8AD5CD83D\" href=\"#\" ><span>数学</span></a></li><li class=\"fg\">|</li>");
document.writeln("							<li><a id=\"4A77CF5EC75E4705A021BC112E3D513C\" href=\"#\" ><span>语文</span></a></li><li class=\"fg\">|</li>");
document.writeln("							<li><a id=\"9C5CA147D1554FDD8CEEC234C442CFCB\" href=\"#\" ><span>外语</span></a></li><li class=\"fg\">|</li>");
document.writeln("							<li><a id=\"FDF774570E3941389428CB1086EF5985\" href=\"#\" ><span>物理</span></a></li><li class=\"fg\">|</li>");
document.writeln("							<li><a id=\"7EA1551AABCF4B99BA0D8A4B59800851\" href=\"#\" ><span>生物</span></a></li><li class=\"fg\">|</li>");
document.writeln("							<li><a id=\"5675843A50514076A5D1F0A82EC04C18\" href=\"#\" ><span>化学</span></a></li><li class=\"fg\">|</li>");
document.writeln("							<li><a id=\"B2BE4751DA6B421F80C25D6B11CC9FE2\" href=\"#\" ><span>地理</span></a></li><li class=\"fg\">|</li>");
document.writeln("							<li><a id=\"8F3257F12D93495AABF871A330577576\" href=\"#\" ><span>政治</span></a></li><li class=\"fg\">|</li>");
document.writeln("						</ul>");
document.writeln("					</div>");
document.writeln("				</div>");
document.writeln("				<div class=\"category-class\">");
document.writeln("					<span class=\"tit-h bk\">课程类型</span>");
document.writeln("					<div class=\"cont bk\">");
document.writeln("						<ul id=\"class_type\" class=\"clearfix z1\">");
document.writeln("							<li><a id=\"000\" href=\"#\" class=\"active\"><span>不限</span></a></li><li class=\"fg\">|</li>");
document.writeln("							<li><a id=\"1\" href=\"#\" ><span>免费公开课</span></a></li><li class=\"fg\">|</li>");
document.writeln("							<li><a id=\"2\" href=\"#\" ><span>普通班</span></a></li><li class=\"fg\">|</li>");
document.writeln("							<li><a id=\"3\" href=\"#\" ><span>精选班</span></a></li><li class=\"fg\">|</li>");
document.writeln("						</ul>");
document.writeln("					</div>");
document.writeln("				</div>");
document.writeln("				<div class=\"category-class\">");
document.writeln("					<span class=\"tit-h bk\">上课时段</span>");
document.writeln("					<div class=\"cont bk\">");
document.writeln("						<ul id=\"class_time\" class=\"clearfix z1\">");
document.writeln("							<li><a id=\"000\" href=\"#\" class=\"active\"><span>不限</span></a></li><li class=\"fg\">|</li>");
document.writeln("							<li><a id=\"1\" href=\"#\" ><span>上午</span></a></li><li class=\"fg\">|</li>");
document.writeln("							<li><a id=\"2\" href=\"#\" ><span>下午</span></a></li><li class=\"fg\">|</li>");
document.writeln("							<li><a id=\"3\" href=\"#\" ><span>晚上</span></a></li><li class=\"fg\">|</li>");
document.writeln("						</ul>");
document.writeln("					</div>");
document.writeln("				</div>");
document.writeln("				<div class=\"category-class\">");
document.writeln("					<span class=\"tit-h bk\">授课区域</span>");
document.writeln("					<div class=\"cont bk\">");
document.writeln("						<ul id=\"teachering_area\" class=\"clearfix z1\">");
document.writeln("							<li><a id=\"000\" href=\"#\" class=\"active\"><span>不限</span></a></li><li class=\"fg\">|</li>");
document.writeln("							<li><a id=\"210014\" href=\"#\" ><span>和平区</span></a></li><li class=\"fg\">|</li>");
document.writeln("							<li><a id=\"210015\" href=\"#\" ><span>沈河区</span></a></li><li class=\"fg\">|</li>");
document.writeln("							<li><a id=\"210013\" href=\"#\" ><span>大东区</span></a></li><li class=\"fg\">|</li>");
document.writeln("							<li><a id=\"210016\" href=\"#\" ><span>皇姑区</span></a></li><li class=\"fg\">|</li>");
document.writeln("							<li><a id=\"210011\" href=\"#\" ><span>铁西区</span></a></li><li class=\"fg\">|</li>");
document.writeln("							<li><a id=\"210017\" href=\"#\" ><span>东陵区</span></a></li><li class=\"fg\">|</li>");
document.writeln("							<li><a id=\"210018\" href=\"#\" ><span>沈北新区</span></a></li><li class=\"fg\">|</li>");
document.writeln("							<li><a id=\"210019\" href=\"#\" ><span>于洪区</span></a></li><li class=\"fg\">|</li>");
document.writeln("							<li><a id=\"210012\" href=\"#\" ><span>浑南新区</span></a></li><li class=\"fg\">|</li>");
document.writeln("						</ul>");
document.writeln("					</div>");
document.writeln("				</div>");
document.writeln("				<div class=\"category-class\">");
document.writeln("					<span class=\"tit-h bk\">课程费用</span>");
document.writeln("					<div class=\"cont bk\">");
document.writeln("						<ul id=\"course_fee\" class=\"clearfix z1\">");
document.writeln("							<li><a id=\"000\" href=\"#\" class=\"active\"><span>不限</span></a></li><li class=\"fg\">|</li>");
document.writeln("							<li><a id=\"1\" href=\"#\" ><span>0-100</span></a></li><li class=\"fg\">|</li>");
document.writeln("							<li><a id=\"2\" href=\"#\" ><span>101-500</span></a></li><li class=\"fg\">|</li>");
document.writeln("							<li><a id=\"3\" href=\"#\" ><span>500以上</span></a></li><li class=\"fg\">|</li>");
document.writeln("						</ul>");
document.writeln("					</div>");
document.writeln("				</div>");
document.writeln("			</div>");
document.writeln("		</div>");