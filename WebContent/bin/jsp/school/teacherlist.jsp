<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.xsjy.servlet.school.ServletTeacher"%>
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
<title>教师查找</title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>/bin/css/resources/logreg.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>/bin/css/resources/newlayout_zy.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>/bin/js/common/header/header.css" id="headercss"/>
<script type="text/javascript" src="<%=basePath%>/bin/js/common.js"></script>
<script type="text/javascript" src="<%=basePath%>/bin/js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="<%=basePath%>/bin/js/laypage/laypage.js"></script>
<script type="text/javascript">
//定义全局变量
var jxxd;
var qjxk;
var jslx;
var xbyq;
var skqy;
var flbq;
var page;
var pageCount;
var limit;
var teaname;
$(document).ready(function() {
	/* 解析父页面URL参数 */
	$.getUrlParam = function(name) {
		var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
		var r = window.location.search.substr(1).match(reg);
		if (r!=null) return unescape(r[2]); return null;
	}
	/* 通过URL参数初始化查询条件及查询数据Start */
	var dataId = $.getUrlParam('dataId');
	var flag = $.getUrlParam('flag');
	teaname = $.getUrlParam('teaname');
	if (dataId != null && flag != null) {
		if (flag == '1') {
			var for_discipline_a = $("#for_discipline li[class!='fg']").find("a");
			for_discipline_a.removeClass("active");
			$("a#" + dataId).addClass("active");
			qjxk = dataId;
		} else if (flag == '2') {
			var teacher_type_a = $("#teacher_type li[class!='fg']").find("a");
			teacher_type_a.removeClass("active");
			$("a#" + dataId).addClass("active");
			jslx = dataId;
		}
		fenye();
	}
	/* 通过URL参数初始化查询条件及查询数据End */
	//教学学段
	var teaching_section_a = $("#teaching_section li[class!='fg']").find("a");
	teaching_section_a.each(function(i, ele){
		$(ele).click(function() {
			teaching_section_a.removeClass("active");
			$(this).addClass("active");
			jxxd = $(this).attr("id");
	    	fenye();
		});
	});
	//求教学科
	var for_discipline_a = $("#for_discipline li[class!='fg']").find("a");
	for_discipline_a.each(function(i, ele){
		$(ele).click(function() {
			for_discipline_a.removeClass("active");
			$(this).addClass("active");
			qjxk = $(this).attr("id");
	    	fenye();
		});
	});
	//教师类型
	var teacher_type_a = $("#teacher_type li[class!='fg']").find("a");
	teacher_type_a.each(function(i, ele){
		$(ele).click(function() {
			teacher_type_a.removeClass("active");
			$(this).addClass("active");
			jslx = $(this).attr("id");
	    	fenye();
		});
	});
	//性别要求
	var gender_requirement_a = $("#gender_requirement li[class!='fg']").find("a");
	gender_requirement_a.each(function(i, ele){
		$(ele).click(function() {
			gender_requirement_a.removeClass("active");
			$(this).addClass("active");
			xbyq = $(this).attr("id");
	    	fenye();
		});
	});
	//授课区域
	var teachering_area_a = $("#teachering_area li[class!='fg']").find("a");
	teachering_area_a.each(function(i, ele){
		$(ele).click(function() {
			teachering_area_a.removeClass("active");
			$(this).addClass("active");
			skqy = $(this).attr("id");
	    	fenye();
		});
	});
	//分类标签
	var sort_t_li = $(".sort-t").find("li");
	var sort_t_a = $(".sort-t li").find("a");
	sort_t_a.each(function(i, ele){
		$(ele).click(function() {
			sort_t_li.removeClass("on");
			sort_t_li.eq(i).addClass("on");
			flbq = $(this).attr("id");
	    	fenye();
		});
	});
	//初始化分页
	fenye();
});
//定义分页
function fenye() {
	//页面初始化加载获取数据条数
	getDataCount();
	//定义分页
	laypage({
	    cont: 'laypage', //容器。值支持id名、原生dom对象，jquery对象。【如该容器为】：<div id="page1"></div>
	    pages: limit, //总页数
	    curr: 1, //当前页
	    skin: '#molv',
	    jump: function(e){ //触发分页后的回调
	    	page = e.curr;
	    	getDataInfo();
	    }
	});
}
//定义bean
function makeBeanIn(strJXXD,strQJXK,strJSLX,strXBYQ,strSKQY,strFLBQ,strPAGE,strJSXX_JSXM){
	this.JXXD = strJXXD;
	this.QJXK = strQJXK;
	this.JSLX = strJSLX;
	this.XBYQ = strXBYQ;
	this.SKQY = strSKQY;
	this.FLBQ = strFLBQ;
	this.PAGE = strPAGE;
	this.JSXX_JSXM = strJSXX_JSXM;
}
//获取数据个数
function getDataCount() {
	var beanIn = new makeBeanIn(
			jxxd,
			qjxk,
			jslx,
			xbyq,
			skqy,
			flbq,
			"",
			teaname
	);
	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/ServletTeacher",
		data: {
			CMD    : "<%=ServletTeacher.CMD_DATA_COUNT%>",
		    BeanIn : JSON.stringify(beanIn)
		},
		complete :function(response){},
		success: function(response){
			var strResult = response[0];
			if (strResult == "SUCCESS") {
				pageCount = response[1];
				limit = Math.ceil((pageCount/2));
				$('#count').html(response[1]);
			} else if (strResult == "FAILURE") {
				alert("对不起：加载数据失败");
			} else {
				alert("提示：加载数据异常");
			}
		}
	});
}
//获取数据信息
function getDataInfo() {
	var beanIn = new makeBeanIn(
			jxxd,
			qjxk,
			jslx,
			xbyq,
			skqy,
			flbq,
			page,
			teaname
	);
	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/ServletTeacher",
		data: {
			CMD    : "<%=ServletTeacher.CMD_DATA_LIST%>",
		    BeanIn : JSON.stringify(beanIn)
		},
		complete :function(response){},
		success: function(response){
			var strResult = response[0];
			var dataList = response[1];
			$('#teaInfoList').empty();//清空ul标签中的内容
			if (strResult == "SUCCESS") {
				$('#laypage').show();
				$.each(dataList, function(i, item) {
					var strHtml = "<li>"
										+"	<dl class=\'clearfix\'>"
										+"		<dt>"
										+"			<div class=\'img mb15\'>"
										+"				<a class=\'img-h\' href=\'#\' target=\'_blank\'> "
										+"					<img src=\'<%=basePath%>/bin/img/resources/nopic2.png\' alt=\'\'></img>"
										+"				</a> "
										+"				<a class=\'design\' target=\'_blank\' href=\'<%=basePath%>/bin/jsp/school/teachershow.jsp?teaId=" + item.JSXX_JSID + "\'></a>"
										+"			</div>"
										+"			<div class=\'tc\'>"
										+"				<a class=\'submit_style5\'>联系老师</a>"
										+"			</div>"
										+"		</dt>"
										+"		<dd>"
										+"			<div class=\'titlebox mb\'>"
										+"				<span class=\'time fr\'> </span> <a href=\'<%=basePath%>/bin/jsp/school/teachershow.jsp?teaId=" + item.JSXX_JSID + "\' target=\'_blank\' class=\'name mr\'>" + item.JSXX_JSXM + "</a> <em class=\'i_orange mr\'><span>好评率：" + item.HPL + "</span></em>"
										+"				<em class=\'i_green mr\'><span>" + item.SKJY + "</span></em> <em class=\'i_blue\'><span>" + item.JSZGMC + "</span></em>"
										+"				<div></div>"
										+"				<!-- <div class=\'icon_all icon_silvery\'>优秀讲师</div> -->"
										+"			</div>"
										+"			<div class=\'lc\'>"
										+"				<b>" + item.JSXX_BYXX + " / " + item.JSXX_XL + " / " + item.JSXX_ZY + " / " + item.NJ + "</b>"
										+"			</div>"
										+"			<div class=\'lc\'>"
										+"				<strong class=\'bt\'>授课区域：" + item.SKQY + "</strong>"
										+"			</div>"
										+"			<div class=\'lc\'>"
										+"				授课科目： <b class=\'shanchang\'> " + item.SKKM + " </b> "
										+"			</div>"
										+"			<div 	class=\'lc\'>"
										+"				<span class=\'ziti_s\'>个人简介：" + item.JSXX_GRJJ + "</span>"
										+"			</div>"
										+"			<div class=\'lc\'></div>"
										+"			<div class=\'butbox\'>"
										+"				<a href=\'<%=basePath%>/bin/jsp/school/teachershow.jsp?teaId=" + item.JSXX_JSID + "\' target=\'_blank\' class=\'submit_style6\'>查看详细信息</a>"
										+"			</div>"
										+"		</dd>"
										+"	</dl>"
										+"</li>";
					$('#teaInfoList').append(strHtml);
					/* 判定教师级别动态加载图片及文字Start */
					var jsjb = $(".titlebox.mb").find("div");
					if (item.JSXX_JSJB == 0) {
						jsjb.addClass("icon_all icon_goal");
					} else {
						jsjb.addClass("icon_all icon_silvery");
					}
					jsjb.html(item.JBCW);
					/* 判定教师级别动态加载图片及文字End */
	            });
			} else if (strResult == "FAILURE") {
				$('#laypage').hide();
			} else {
				alert("提示：加载数据异常");
			}
		}
	});
}
</script>
</head>
<body>
<script src="<%=basePath%>/bin/js/common/header/header.js?id=2" type="text/javascript"></script>
	<!-- 主体 begin -->
	<div id="content"><br/>
		<script src="<%=basePath%>/bin/js/resources/nav_detail.js" type="text/javascript"></script>
		<!-- 详细导航和搜索条件 -->
		<!-- 显示搜索内容区（左面） begin -->
		<div class="box680 fl mr">
			<div class="p_sort">
				<ul class="p_ul">
					<li class="">共有<label id="count"></label>条搜索结果</li>
<!-- 					<li class="">( 15位在线 )</li> -->
				</ul>
			</div>
			<div class="teacher_listbox">
				<ul id="teaInfoList">
				</ul>
			</div>
			<!-- 显示搜索内容区（左面） end -->

			<!-- 页码显示 begin -->
			<div class="pro_bottom_box clearfix page_pa40">
				<div id="laypage" class="page tc">
<!-- 					<a href="#">&#171; 上一页</a> <a class="hover">1</a> <a href="#">2</a> -->
<!-- 					<a href="#">3</a> <a href="#">4</a> <a href="#">5</a> <a href="#">下一页 -->
<!-- 						&#187;</a> -->
				</div>
			</div>
			<!-- 页码显示 end -->

		</div>

		<!-- 显示广告区（右面） begin -->
		<div class="box270 fl">
			<div class=" mb">
				<img src="<%=basePath%>/bin/img/resources/id_shujiazuoye.jpg">
			</div>
		</div>
		<!-- 显示广告区（右面） end -->

	</div>
	<!-- 主体 begin -->
	<div class="clear"></div>
	<br/><br/><br/>
	<script src="<%=basePath%>/bin/js/common/footer/footer_common.js" type="text/javascript"></script>
</body>
</html>