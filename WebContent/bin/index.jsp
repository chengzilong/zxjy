<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.xsjy.servlet.school.ServletSchool"%>
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
<meta http-equiv="X-UA-Compatible" content="IE=9" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="<%=basePath%>/bin/css/resources/settings.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>/bin/css/resources/newlayout_zy.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>/bin/js/common/header/header.css" id="headercss"/>
<script src="<%=basePath%>/bin/js/jquery-1.9.1.js" type="text/javascript"></script>
<script src="<%=basePath%>/bin/js/common.js" type="text/javascript"></script>
<script type="text/javascript">
var basePath="<%=basePath%>";
window.onload=function(){
	var w = window.screen.width;
	var h = window.screen.height;

	window.moveTo(0,0);
    window.resizeTo(w,h);


   //$("#main_frame").attr("src",basePath + "/bin/img/logo.jpg");
   /* setInterval('refreshDateTime()',100); */
};

$(document).ready(function(){
	$("#discipline").on("blur",function() {
		if($("#discipline").val()==""){
			$("#spdiscipline").show();
		}

	});
	$("#idtime").on("blur",function() {
		if($("#idtime").val()==""){
			$("#spidtime").show();
		}

	});
	$("#idname").on("blur",function() {
		if($("#idname").val()==""){
			$("#spidname").show();
		}

	});
	$("#idphone").on("blur",function() {
		if($("#idphone").val()==""){
			$("#spidphone").show();
		}

	});
	getTeacherList();
	getSchoolList();
	getTeacherInfo();
});
function getTeacherList(){
	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/ServletSchool",
		data: {
			CMD    : "<%=ServletSchool.CMD_SELECTJS%>"
		},
		complete :function(response){},
		success: function(response){
			var strResault = response[0];
			var list = response[1];


			if(strResault=="SUCCESS"){
					var listHtml = "";
					var jsid = "";
					listHtml+="<p class='pad_bot1 zengjiayangshi'>";
					if (list != "undefined") {
						$.each(list, function(k, v) {
							jsid = v.JSID;
							listHtml+="&nbsp;<a href='<%=basePath%>/bin/jsp/school/teachershow.jsp?teaId="+v.JSID+"'  style='cursor:pointer;'><span>"+v.XXJS+"</span></a>";
							if((k!=0)&&((k+1)%2==0)){
								listHtml+="<span class='img_ys bd_0'>";
							}else{
								listHtml+="<span class='img_ys'>";
							}
							if(v.JSXX_SFRZ=="0"){
								listHtml+="<img src='<%=basePath%>/bin/img/resources/v_s_hui.png'/></span>";
							}else{
								listHtml+="<img src='<%=basePath%>/bin/img/resources/v_s.png'/></span>";
							}
							if((k!=0)&&((k+1)%2==0)){
								if((k+1)%10==0){
									listHtml+="</p>";
								}else{
									listHtml+="</p>";
									listHtml+="<p class='pad_bot1 zengjiayangshi'>";
								}
							}
						});
					} else {
						listHtml+="</p>";
					}
					$("#js").html(listHtml);
			}else if(strResault=="FAILURE"){

			}else{
				alert("系统异常！");
			}
		}
	});
}

function getSchoolList(){
	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/ServletSchool",
		data: {
			CMD    : "<%=ServletSchool.CMD_SELECTXX%>"
		},
		complete :function(response){},
		success: function(response){
			var strResault = response[0];
			var list = response[1];
			if(strResault=="SUCCESS"){
					var listHtml = "";
					var jsid = "";
					listHtml+="<p class='pad_bot1 zengjiayangshi'>";
					if (list != "undefined") {
						$.each(list, function(k, v) {
							jsid = v.JSID;
							listHtml+="&nbsp;<span>"+v.JSXX_BYXX+"</span>";
							if((k!=0)&&((k+1)%4==0)){
								listHtml+="";
							}else{
								listHtml+="|";
							}
							if((k!=0)&&((k+1)%4==0)){
							listHtml+="</p>";

							listHtml+="<p class='pad_bot1 zengjiayangshi'>";
							}
						});
					} else {
						listHtml+="</p>";
					}
					$("#xx").html(listHtml);
			}else if(strResault=="FAILURE"){
				//alert("获取课程信息出错！");
			}else{
				alert("系统异常！");
			}
		}
	});
}
function getTeacherInfo(){
	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/ServletSchool",
		data: {
			CMD    : "<%=ServletSchool.CMD_SELECTINFO%>"
		},
		complete :function(response){},
		success: function(response){
			var strResault = response[0];
			var list = response[1];
			if(strResault=="SUCCESS"){
				var jsid = "";
				var ul = $("#uljs");
				if (list != "undefined") {
					$.each(list, function(k, v) {
						jsid = v.JSID;
						ul.append("<li><div class='quyu2_all'><a href='<%=basePath%>/bin/jsp/school/teachershow.jsp?teaId="+v.JSID+"'  style='cursor:pointer;'><img src='<%=basePath%>/bin/img/resources/nopic2.png'></a><p><span class='quyuall_title'>"+v.JSXX_JSXM+"</span> </p><p>"+v.JSXX_BYXX+" |  "+v.JSXX_NJ+"</p><p></p><b></b></div></li>");
					});
				}
			}else if(strResault=="FAILURE"){
// 				alert("获取教师信息出错！");
			}else{
				alert("系统异常！");
			}
		}
	});
}
function cancel_input(){
	$("#spdiscipline").hide();
}
function focus_span(){
	$("#discipline").focus();
}
function cancel_input2(){
	$("#spidtime").hide();
}
function focus_span2(){
	$("#idtime").focus();
}
function cancel_input3(){
	$("#spidname").hide();
}
function focus_span3(){
	$("#idname").focus();
}
function cancel_input4(){
	$("#spidphone").hide();
}
function focus_span4(){
	$("#idphone").focus();
}

function findteacher(dataId, flag){
	location.href='<%=basePath%>/bin/jsp/school/teacherlist.jsp?dataId=' + dataId + '&flag=' + flag;
}

function showteacher(){
	location.href='<%=basePath%>/bin/jsp/school/teachershow.jsp';
}
function findlession(){
	location.href='<%=basePath%>/bin/jsp/school/lessionlist.jsp';
}
function joinlession(){
	if($('#idname').val()==""){
		alert("请输入您的姓名!");
		return;
	}
	if($('#idphone').val()==""){
		alert("请输入您的手机号码!");
		return;
	}

	var name = $('#idname').val();
	var phone = $('#idphone').val();
	var remark = $('#discipline').val() +"|"+ $('#idtime').val();

	insertJoinLession(name,phone,remark);

}

</script>
</head>

<body>
<script src="<%=basePath%>/bin/js/common/header/header.js?id=1" type="text/javascript"></script>
<%-- <script src="<%=basePath%>/bin/js/resources/nav.js" type="text/javascript"></script><!-- 导航 --> --%>
<!-- 首页banner广告 begin -->
	<div class="web">
		<script src="<%=basePath%>/bin/js/resources/jquery.themepunch.revolution.min.js" type="text/javascript"></script>
		<script src="<%=basePath%>/bin/js/resources/gensee.slidershow.js" type="text/javascript"></script>
		<div class="slideshow-widget">
			<div class="fullwidthbanner-container">
			<span class="Apple-tab-span"> </span>
				<div id="slideshowMain" class="fullwidthbanner">
					<ul>
						<li data-transition="turnoff" data-slotamount="7" data-masterspeed="300">
							<img src="<%=basePath%>/bin/img/resources/banner-bg.jpg" data-fullwidthcentering="off">
							<div class="tp-caption lfb" data-x="50" data-y="85" data-speed="400" data-start="1200" data-easing="easeOutExpo">
							<img class="tran" src="<%=basePath%>/bin/img/resources/banner-macbook.png"></div>
							<div class="tp-caption tp-caption-1 lfr" data-x="650" data-y="130" data-speed="300" data-start="1000" data-easing="easeOutExpo">
							<img class="tran" src="<%=basePath%>/bin/img/resources/banner-logo.png">
							</div>
							<div class="tp-caption tp-caption-1 lfr" data-x="650" data-y="200" data-speed="300" data-start="1000" data-easing="easeOutExpo">师资认证</div>
							<div class="tp-caption tp-caption-2 lfr" data-x="650" data-y="260" data-speed="300" data-start="1400" data-easing="easeOutExpo">中国公民在各类学校或者其他教育机构中<br>专门从事教育教学工作，必须依法取得教师资格。</div>
							<div class="tp-caption sfr" data-x="650" data-y="300" data-speed="300" data-start="1400" data-easing="easeOutExpo">
							</div>
						</li>
						<li data-transition="turnoff" data-slotamount="7" data-masterspeed="300">
							<img src="<%=basePath%>/bin/img/resources/banner-bg.jpg" data-fullwidthcentering="on">

							<div class="tp-caption lfb" data-x="50" data-y="50" data-speed="400" data-start="1200" data-easing="easeOutExpo">
							<img class="tran" src="<%=basePath%>/bin/img/resources/banner-mac.png"></div>
							<div class="tp-caption tp-caption-1 lfr" data-x="680" data-y="200" data-speed="300" data-start="1000" data-easing="easeOutExpo">免费体验</div>
							<div class="tp-caption tp-caption-2 lfr" data-x="680" data-y="260" data-speed="300" data-start="1400" data-easing="easeOutExpo">平台将匹配10个学生信息作为免费的教师体验。<br>平台将赠送1000积分作为学生的免费体验。</div>
						</li>
						<li data-transition="3dcurtain-horizontal" data-slotamount="7" data-masterspeed="300">
							<img src="<%=basePath%>/bin/img/resources/banner-bg.jpg" data-fullwidthcentering="on">

							<div class="tp-caption lfb" data-x="-10" data-y="20" data-speed="400" data-start="1200" data-easing="easeOutExpo">
							<img class="tran" src="<%=basePath%>/bin/img/resources/banner-ipad.png"></div>
							<div class="tp-caption tp-caption-1 lfr" data-x="680" data-y="200" data-speed="300" data-start="1000" data-easing="easeOutExpo">资源整合</div>
							<div class="tp-caption tp-caption-2 lfr" data-x="680" data-y="260" data-speed="300" data-start="1400" data-easing="easeOutExpo">突破传统培训的招生模式，<br>实现学生资源的重复利用，教师间紧密合作。</div>
						</li>
						<li data-transition="3dcurtain-horizontal" data-slotamount="7" data-masterspeed="300">
							<img src="<%=basePath%>/bin/img/resources/banner-bg.jpg" data-fullwidthcentering="on">

							<div class="tp-caption lfb" data-x="-10" data-y="20" data-speed="400" data-start="1200" data-easing="easeOutExpo">
							<img class="tran" src="<%=basePath%>/bin/img/resources/banner-ipad.png"></div>
							<div class="tp-caption tp-caption-1 lfr" data-x="680" data-y="200" data-speed="300" data-start="1000" data-easing="easeOutExpo">满意付费</div>
							<div class="tp-caption tp-caption-2 lfr" data-x="680" data-y="260" data-speed="300" data-start="1400" data-easing="easeOutExpo">培训费由平台代收取，对课程及教师不满意可提出申诉，<br/>平台工作人员第一时间出面解决。</div>
						</li>
					</ul>
				</div>
			</div>
		</div>
		<div id="coverLayer"></div>
	</div>
	<!-- 首页banner广告 end -->

	<!-- 首页导航 begin -->
	<div class="toolbar toolbar_nob">
		<div class="height40">
			<div class="tbbox clearfix">
				<div class="nav_imp fl"><a onclick="findteacher('6','2');" style="cursor:pointer;">一对一辅导教师</a></div>
				<div class="nav_imp fl"><a onclick="findteacher('9C5CA147D1554FDD8CEEC234C442CFCB','1');" style="cursor:pointer;">外语老师</a></div>
				<div class="nav_imp fl"><a onclick="findteacher('191164FE39D243E0A6D677A8AD5CD83D','1');" style="cursor:pointer;">数学教师</a></div>
				<div class="nav_imp fl"><a onclick="findteacher('FDF774570E3941389428CB1086EF5985','1');" style="cursor:pointer;">物理教师</a></div>
				<div class="nav_imp fl"><a onclick="findteacher('5675843A50514076A5D1F0A82EC04C18','1');" style="cursor:pointer;">化学教师</a></div>
				<div class="nav_imp fl"><a onclick="findteacher('4A77CF5EC75E4705A021BC112E3D513C','1');" style="cursor:pointer;">语文教师</a></div>
				<div class="nav_imp fl"><a onclick="findteacher('7376E5F52D274ABCA70D1AF61FE4A78C','1');" style="cursor:pointer;">艺术课教师</a></div>

				<div class="nav_imp fl bg_blue"><a href="index.html">志翔教育首页</a></div>
			</div>
		</div>
	</div>
	<!-- 首页导航 end -->

	<!-- 首页四项优惠 begin -->
	<div class="body3">
		<div class="main">
			<article id="content">
				<div class="wrapper">
					<ul>
						<li class="cols">
							<h3><span class="dropcap">1</span>师资认证</h3>
							<p class="pad_bot"><a style="cursor:pointer;" target="_blank"></a>中国公民在各类学校或者其他教育机构中专门从事教育教学工作，必须依法取得教师资格。</p>
							<a style="cursor:pointer;" class="link1">师资认证，严格审核</a>
						</li>
						<li class="cols pad_left1">
							<h3><span class="dropcap">2</span>免费体验</h3>
							<p class="pad_bot">平台将匹配10个学生信息作为免费的教师体验。平台将赠送1000积分作为学生的免费体验。</p>
							<a style="cursor:pointer;" class="link1">以上活动对象为已认证学生及教师</a>
						</li>
						<li class="cols pad_left1">
							<h3><span class="dropcap">3</span>资源整合</h3>
							<p class="pad_bot">突破传统培训的招生模式，实现学生资源的重复利用，教师间紧密合作。</p>
							<a style="cursor:pointer;" class="link1">资源重复利用，教师紧密合作</a>
						</li>
						<li class="cols pad_left1">
							<h3><span class="dropcap">4</span>满意付费</h3>
							<p class="pad_bot">培训费由平台收取，对课程及教师不满意可提出申诉，平台工作人员第一时间出面解决。</p>
							<a style="cursor:pointer;" class="link1">严格审查，进行相应退款。</a>
						</li>
					</ul>
				</div>
			</article>
		</div>
	</div>
	<!-- 首页四项优惠 end -->

	<!-- 左需求 右报名 begin -->
	<div style="background:#f5f5f5">
		<div class="main">
			<article id="content">
				<div class="wrapper">
					<ul>
						<li class="col1">
							<h2 class="under">提交您的需求，好老师我们帮您选</h2>
							<div class="wrapper">
								<div class="fl marg_right1">
									<img src="<%=basePath%>/bin/img/resources/id_shujiazuoye.jpg" width="221px" alt="">
								</div>

								<div id="js">
								</div>
								<div id="xx">

								</div>
							</div>
						</li>
						<li class="col2 pad_left1">
							<h2 class="under h2_ys">免费电话：400-024-8888</h2>
							<div class="testimonials">
								<div id="testimonials">
								  <ul>
									<li>
										<div class="baoming_in">
											<input type="text" size="30" name="discipline" id="discipline" onkeydown="cancel_input()"/>
											<span onclick="focus_span()" id="spdiscipline">辅导学科：（科目 / 年级）</span>
										</div>
										<div class="baoming_in">
											<input type="text" size="30" name="idtime" id="idtime" onkeydown="cancel_input2()"/>
											<span onclick="focus_span2()" id="spidtime">辅导时间：（时间段 / 地点）</span>
										</div>
										<div class="baoming_in">
											<input type="text" size="30" name="idname" id="idname" onkeydown="cancel_input3()"/>
											<span onclick="focus_span3()" id="spidname">您的姓名</span>
										</div>
										<div class="baoming_in">
											<input type="text" size="30" name="idphone" id="idphone" onkeydown="cancel_input4()"/>
											<span onclick="focus_span4()" id="spidphone">您的手机号码</span>
										</div>
										<span class="botton_tj"><input name="提交" type="button" onclick="joinlession();" value="提 交"/></span>
									</li>
								  </ul>
								</div>
							</div>
						</li>
					</ul>
				</div>
			</article>
		</div>
	</div>
	<!-- 左需求 右报名 end -->

	<!-- 开课展示3个颜色 begin -->
		<div class="main quyu">
			<ul>
				<li>
					<div class="quyu_blue quyu_all"><span>公开课</span>
					<p>公开课频道领略名师风采,感受教学魅力...</p>
					<b><a onclick="findlession()" style="cursor:pointer;" >>>详情</a></b>
					</div>
				</li>
				<li>
					<div class="quyu_green quyu_all"><span>基础课</span>
					<p>针对初高中的基础教学，渗透教学重点...</p>
					<b><a onclick="findlession()" style="cursor:pointer;">>>详情</a></b>
					</div>
				</li>
				<li>
					<div class="quyu_orange quyu_all"><span>私教课</span>
					<p>优秀教师一对一和精品小班私教补习...</p>
					<b><a onclick="findlession()" style="cursor:pointer;">>>详情</a></b>
					</div>
				</li>
			</ul>
		</div>
		<div class="main">
			<div class="quyu_bottom">
				同一平台可以满足不同的应用场景，客户可根据实际需求进行选择<br/>
				一个平台实现三个的功能和应用，并且还有统一的教学管理
			</div>
		</div>
	<!-- 开课展示3个颜色 end -->

	<!-- 优秀教师展示 begin -->
	<div class="main quyu2">
		<ul id="uljs">

		</ul>
	</div>
	<div class="clear"></div>
	<!-- 优秀教师展示 end -->

	<!-- 学员展示 begin -->
	<div class="main quyu3">
	<div class="main">
		<div class="quyu_top">
		 - 你们相信志翔教育 - 我们用成绩让您满意 -
		</div>
	</div>
		<ul>
			<li>
				<div class="quyu3_all">
				<a><img src="<%=basePath%>/bin/img/resources/nopic2.png"></a>
				<p><span class="quyuall_title">王静同学</span> - [ 辅导科目：数学 ]</p>
				<p>辅导教师：李老师</p>
				<p>原分数区：80分 - 90分</p>
				<p>现分数区：<span class="quyuall_orange">100分 - 130分</span></p>
				<p>家长点评：教师辅导非常用心教师辅导非常用心教师辅导...</p>
				</div>
			</li>
			<li>
				<div class="quyu3_all">
				<a><img src="<%=basePath%>/bin/img/resources/nopic2.png"></a>
				<p><span class="quyuall_title">张子寒同学</span> - [ 辅导科目：数学 ]</p>
				<p>辅导教师：李老师</p>
				<p>原分数区：80分 - 90分</p>
				<p>现分数区：<span class="quyuall_orange">100分 - 130分</span></p>
				<p>家长点评：教师辅导非常用心教师辅导非常用心教师辅导...</p>
				</div>
			</li>
			<li>
				<div class="quyu3_all">
				<a><img src="<%=basePath%>/bin/img/resources/nopic2.png"></a>
				<p><span class="quyuall_title">周小丽同学</span> - [ 辅导科目：数学 ]</p>
				<p>辅导教师：李老师</p>
				<p>原分数区：80分 - 90分</p>
				<p>现分数区：<span class="quyuall_orange">100分 - 130分</span></p>
				<p>家长点评：教师辅导非常用心教师辅导非常用心教师辅导...</p>
				</div>
			</li>
			<li>
				<div class="quyu3_all">
				<a><img src="<%=basePath%>/bin/img/resources/nopic2.png"></a>
				<p><span class="quyuall_title">陈媛媛同学</span> - [ 辅导科目：数学 ]</p>
				<p>辅导教师：李老师</p>
				<p>原分数区：80分 - 90分</p>
				<p>现分数区：<span class="quyuall_orange">100分 - 130分</span></p>
				<p>家长点评：教师辅导非常用心教师辅导非常用心教师辅导...</p>
				</div>
			</li>
		</ul>
	</div>
	<div class="clear"></div>
	<!-- 学员展示 end -->
	<script src="<%=basePath%>/bin/js/resources/footer.js" type="text/javascript"></script>
</body>
</html>
