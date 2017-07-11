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
<title>秀石培训</title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>/bin/css/resources/common_teachershow.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>/bin/css/resources/newlayout_zy.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>/bin/js/common/header/header.css" id="headercss"/>
<script type="text/javascript" src="<%=basePath%>/bin/js/common.js"></script>
<script type="text/javascript" src="<%=basePath%>/bin/js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="<%=basePath%>/bin/js/laypage/laypage.js"></script>
<script type="text/javascript">
var teaId;//定义参数
$(document).ready(function() {
	/* 解析父页面URL参数 */
	$.getUrlParam = function(name) {
		var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
		var r = window.location.search.substr(1).match(reg);
		if (r!=null) return unescape(r[2]); return null;
	}
	teaId = $.getUrlParam('teaId');
	getDataDetail();
	//定义按钮事件
	$("#attention").on("click", function() {
		attention();
	});
	$("#cancelAttention").on("click", function() {
		if(confirm("是否取消关注？")==false) return;
		cancelAttention();
	});
});
//获取数据详情方法
function getDataDetail() {
	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/ServletTeacher",
		data: {
			CMD	: "<%=ServletTeacher.CMD_DATA_DETAIL%>",
		    TEAID	: teaId
		},
		complete :function(response){},
		success: function(response){
			var strResult = response[0];
			if (strResult == "SUCCESS") {
				var dataBean = response[1];
				/* 动态生成教师基础信息Start */
				var rz;
				if (dataBean.JSXX_SFRZ == 1) {
					rz = "<span class=\'icon_shi icon_position1\'></span><span class=\'icon_zi_green icon_position1_zi\'>教师已认证</span>";
				} else {
					rz = "<span class=\'icon_shi_no icon_position1\'></span><span class=\'icon_zi_grey icon_position1_zi\'>教师未认证</span>";
				}
				if (dataBean.ISGZ == 0) {
					$("#attentionDiv").show();
					$("#cancelAttentionDiv").hide();
				} else {
					$("#attentionDiv").hide();
					$("#cancelAttentionDiv").show();
				}
				$("#jsmcid").append("<strong>" + dataBean.JSXX_JSXM + "</strong>" + rz);
				$("#lz").html(dataBean.JSXX_BYXX + " - " + dataBean.JSXX_XL + " - " + dataBean.JSXX_ZY + " - " + dataBean.NJ);
				$("#skkm").html(dataBean.SKKM);
				$("#skqy").html(dataBean.SKQY);
				$("#grjj").html(dataBean.JSXX_GRJJ);
				$("#scly").html(dataBean.JSXX_SCLY);
				$("#xqah").html(dataBean.JSXX_XQAH);
				var jbxx = "<table>"
								+"	<tr>"
								+"		<td width=\'300\'>性别：<span>" + dataBean.XBMC + "</span></td>"
								+"		<td width=\'300\'>级别称谓：<span>" + dataBean.JBCW + "</span></td>"
								+"	</tr>"
								+"	<tr>"
								+"		<td>类型：<span>" + dataBean.JSZGMC + "</span></td>"
								+"		<td>授课经验：<span>" + dataBean.SKJY + "</span></td>"
								+"	</tr>"
								+"	<tr>"
								+"		<td colspan=\'2\'>住址:<span>" + dataBean.JSXX_ZZ + "</span></td>"
								+"	</tr>"
								+"</table>";
				$("#jbxx").append(jbxx);
				/* 动态生成教师基础信息End */
				/* 动态生成教师排课表信息Start */
				var dateList = response[2];
				//表头，动态星期
				var title = "<tr><td class=\'color_hs\'>\\</td>"; 
				$.each(dateList, function(i, item) {
					title += "<td class=\'color_hs\'>" + item.ROTA_WEEK + "</td>";
				});
				title += "</tr>";
				var courseList = response[3];
				//表内容，动态上午、下午、晚上是否有课
				var content = "";
				$.each(courseList, function(i, item) {
					content += "<tr><td rowspan=\'2\' class=\'color_hs\'>" + item.ROTA_SDMC + "</td></tr>"
								+"<tr class=\'gao_53\'>"
								+"	<td><img src=\'<%=basePath%>/bin/img/resources/" + item.ROTA_DAY1 + ".jpg\'></td>"
								+"	<td><img src=\'<%=basePath%>/bin/img/resources/" + item.ROTA_DAY2 + ".jpg\'></td>"
								+"	<td><img src=\'<%=basePath%>/bin/img/resources/" + item.ROTA_DAY3 + ".jpg\'></td>"
								+"	<td><img src=\'<%=basePath%>/bin/img/resources/" + item.ROTA_DAY4 + ".jpg\'></td>"
								+"	<td><img src=\'<%=basePath%>/bin/img/resources/" + item.ROTA_DAY5 + ".jpg\'></td>"
								+"	<td><img src=\'<%=basePath%>/bin/img/resources/" + item.ROTA_DAY6 + ".jpg\'></td>"
								+"	<td><img src=\'<%=basePath%>/bin/img/resources/" + item.ROTA_DAY7 + ".jpg\'></td>"
								+"</tr>";
				});
				$("#rotaTable").append(title + content);
				/* 动态生成教师排课表信息End */
				var pjxxCount = response[4];
				$("#allListNum").html("(" + pjxxCount + ")");
				pjxxPage(pjxxCount, teaId);
				var hdxxCount = response[5];
				$("#adoptListNum").html("(" + hdxxCount + ")");
				hdxxPage(hdxxCount, teaId);
				var classList = response[6];
				var classContent = "";
				$.each(classList, function(i, item) {
					classContent += "<table>"
										+"	<tr>"
										+"		<td rowspan=\'4\' class=\'bg_pic_banci\'>"
										+"			班次名称：<br/>"
										+"			" + item.BCXX_BCMC + "<br/>"
										+"			地点：<br/>" + item.BCXX_SKDD
										+"		</td>"
										+"		<td class=\'bg_juse\'>课程名称</td>"
										+"		<td class=\'bg_juse\'>开始时间</td>"
										+"		<td class=\'bg_juse\'>结束时间</td>"
										+"		<td class=\'bg_juse\'>班次状态</td>"
										+"	</tr>"
										+"	<tr class=\'bg_pic_nr\'>"
										+"		<td>" + item.KCMC + "</td>"
										+"		<td>" + item.BCXX_KSSJ + "</td>"
										+"		<td>" + item.BCXX_JSSJ + "</td>"
										+"		<td>" + item.BCZT + "</td>"
										+"	</tr>"
										+"	<tr class=\'bg_pic_nr\'>"
										+"		<td>课程类型</td>"
										+"		<td>上课时段</td>"
										+"		<td>学时</td>"
										+"		<td>费用</td>"
										+"	</tr>"
										+"	<tr class=\'bg_pic_nr\'>"
										+"		<td>" + item.LXMC + "</td>"
										+"		<td>" + item.SDMC + "</td>"
										+"		<td>" + item.XS + "</td>"
										+"		<td>" + item.BCXX_FY + "</td>"
										+"	</tr>"
										+"</table>";
				});
				$("#classInfo").append(classContent);
			} else if (strResult == "FAILURE") {
				
			} else {
				alert("提示：加载数据异常");
			}
		}
	});
}
//定义评价信息分页
function pjxxPage(pjxxCount, teaId) {
	//定义分页
	laypage({
	    cont: 'pjxxPage', //容器。值支持id名、原生dom对象，jquery对象。【如该容器为】：<div id="page1"></div>
	    pages: Math.ceil((pjxxCount/2)), //总页数
	    curr: 1, //当前页
	    skin: '#molv',
	    jump: function(e){ //触发分页后的回调
	    	var page = e.curr;//当前页数
	    	getPjxx(page, teaId);
	    }
	});
}
//获取评价信息
function getPjxx(page, teaId) {
	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/ServletTeacher",
		data: {
			CMD    : "<%=ServletTeacher.CMD_PJXX_LIST%>",
			PAGE	: page,
		    TEAID	: teaId
		},
		complete :function(response){},
		success: function(response){
			var strResult = response[0];
			var dataList = response[1];
			$('#pjxx').empty();//清空ul标签中的内容
			if (strResult == "SUCCESS") {
				/* 动态生成评价信息Start */
				var pjxx = "";
				$.each(dataList, function(i, item) {
					pjxx += "<li>"
							+"<dl>"
							+"<dd class=\'expert-comment\'><span class=\'prof-value-good\'>&nbsp;" + item.XSXM + "：</span>" + item.PJXX_PJNR + "<span class=\'blue_liang\'>" + item.PJXX_PJSJ + "</span></dd>"
							+"</dl>"
							+"</li>";
				});
				$("#pjxx").append(pjxx);
				/* 动态生成评价信息End */
			} else if (strResult == "FAILURE") {
				$('#pjxxPage').hide();
			} else {
				alert("提示：加载数据异常");
			}
		}
	});
}
//定义回答信息分页
function hdxxPage(hdxxCount, teaId) {
	//定义分页
	laypage({
	    cont: 'hdxxPage', //容器。值支持id名、原生dom对象，jquery对象。【如该容器为】：<div id="page1"></div>
	    pages: Math.ceil((hdxxCount/2)), //总页数
	    curr: 1, //当前页
	    skin: '#molv',
	    jump: function(e){ //触发分页后的回调
	    	var page = e.curr;//当前页数
	    	getHdxx(page, teaId);
	    }
	});
}
//获取回答信息
function getHdxx(page, teaId) {
	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/ServletTeacher",
		data: {
			CMD    : "<%=ServletTeacher.CMD_HDXX_LIST%>",
			PAGE	: page,
		    TEAID	: teaId
		},
		complete :function(response){},
		success: function(response){
			var strResult = response[0];
			var dataList = response[1];
			$('#hdxx').empty();//清空ul标签中的内容
			if (strResult == "SUCCESS") {
				/* 动态生成回答信息Start */
				var hdxx = "";
				$.each(dataList, function(i, item) {
					hdxx += "<li>"
							+"<dl>"
							+"<dt class=\'expert-icons\'><a href=\'#\' target=\'_blank\' class=\'blue_an\'>" + item.WTXX_TWNR + "</a><span>" + item.WTXX_TWSJ + "</span></dt>"
							+"<dd class=\'expert-icons\'>" + item.HDXX_HDNR + "</dd>"
// 							+"<dd class=\'expert-comment\'><span class=\'prof-value-good\'>&nbsp;提问者评价：</span>谢了O(∩_∩)O</dd>"
							+"</dl>"
							+"</li>";
				});
				$("#hdxx").append(hdxx);
				/* 动态生成回答信息End */
			} else if (strResult == "FAILURE") {
				$('#hdxxPage').hide();
			} else {
				alert("提示：加载数据异常");
			}
		}
	});
}
//关注教师方法
function attention() {
	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/ServletTeacher",
		data: {
			CMD    : "<%=ServletTeacher.CMD_ATTENTION%>",
		    TEAID	: teaId
		},
		complete :function(response){},
		success: function(response){
			var strResult = response[0];
			if (strResult == "SUCCESS") {
				window.location.reload();
			} else if (strResult == "FAILURE") {
			} else if (strResult == "NOT_LOGIN") {
				location.href='<%=basePath%>/bin/jsp/login/login.jsp';
			} else {
				alert("提示：加载数据异常");
			}
		}
	});
}
//取消关注教师方法
function cancelAttention() {
	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/ServletTeacher",
		data: {
			CMD    : "<%=ServletTeacher.CMD_CANCEL_ATTENTION%>",
		    TEAID	: teaId
		},
		complete :function(response){},
		success: function(response){
			var strResult = response[0];
			if (strResult == "SUCCESS") {
				window.location.reload();
			} else if (strResult == "FAILURE") {
			} else {
				alert("提示：加载数据异常");
			}
		}
	});
}
</script>
</head>
<body>
<%-- <script src="<%=basePath%>/bin/js/resources/header.js" type="text/javascript"></script> --%>
<script src="<%=basePath%>/bin/js/common/header/header.js" type="text/javascript"></script>
<%-- <script src="<%=basePath%>/bin/js/resources/nav.js" type="text/javascript"></script><!-- 导航 --> --%>

	<div class="layout-center prof-menu-page">
		<div id="body" class="container">       		
			<div class="expert-main">
				<!-- 教师简介 begin -->
				<div class="expert-top">
					<div class="expert-top-main mb0">
						<div class="expert-single-card" style="margin-top:10px;">
							<div class="expert-inner">
								<div class="expert-single-port grid">
									<div><em><img src="<%=basePath%>/bin/img/resources/nopic2.png" alt=""></em></div>
									<i class="expert-icons exp-port-shadow"></i>
								</div>
								<div class="expert-single-info grid" id="expertInfoView">
									<h3 id="jsmcid"></h3>
									<div class="expert-single-input" id="expertInfo">
										<p><label>来自：</label><i id="lz"></i></p>
										<p><label>授课科目：<i id="skkm" class="blue_liang"></i></p>
										<p><label>授课区域：</label><i><span id="skqy" class="intro-trunc"></span></i></p>
									</div>
									<div id="attentionDiv" class="guanzhu"><a id="attention" href="#"><img src="<%=basePath%>/bin/img/school/jiaguanzhu.png"></a></div> 
									<div id="cancelAttentionDiv" class="guanzhu"><a id="cancelAttention" href="#"><img src="<%=basePath%>/bin/img/school/quxiaoguanzhu.png"></a></div>
								</div>
								<div class="expert-single-d f-yahei">
									<div class="expert-star">
										<span class="expert-star-c" style="width:200px;"></span>
									</div>
									<p class="expert-single-rate"><b>100%</b>好评率（230人评价）</p>
									<dl class="expert-single-data">
										<dt class="first"><label>280</label>人气数</dt>
										<dt><label>222</label>好评数</dt>
										<dt><label>644</label>回答问题数</dt>
									</dl>
								</div>
							</div>
						</div>
					</div>
				</div>
				<!-- 教师简介 end -->
				<div id="pjxxlaypage" class="page tc">
				</div>
				<!-- 教师简介-详细 begin -->
				<div class="expert-bottom expert-view-bottom">
					<div class="expert-bottom-left w635 ml25">
						<div class="expert-single-answer mt28">
							<!-- 课程表 -->
							<div class="kechengbiao">
								<table id="rotaTable">
								</table> <span class="kechengbiao_imp yellow_an">* 根据老师的授课日程预约，更能提高成功率!</span>
							</div>
							<div id="classInfo" class="class_kebiao">
							</div>
							<div class="expert-tab">
								<ol>
								<li data-info="infoList" class="current">个人资料</li>
								<li data-info="allList">学生评价<em id="allListNum"></em></li>
								<li data-info="adoptList">精彩回答<em id="adoptListNum"></em></li>
								</ol>
							</div>
							<div class="expert-data-list">
								<!-- 个人资料 -->
								<div id="infoList" class="tab-content current ziliao">
									<ul>
										<li>
											<dl>
											<dt class="ziliao_jxnl">个人基本信息：<span></span></dt>
											<dt id="jbxx" class="ziliao_jxnl_wz">
											</dt>
											</dl>
										</li>
										<li>
											<dl>
											<dt class="ziliao_jxnl">个人简介：<span></span></dt>
											<dt id="grjj" class="ziliao_jxnl_wz"></dt>
											</dl>
										</li>
										<li>
											<dl>
											<dt class="ziliao_jxnl">擅长领域：<span></span></dt>
											<dt id="scly" class="ziliao_jxnl_wz"></dt>
											</dl>
										</li>
										<li>
											<dl>
											<dt class="ziliao_jxnl">兴趣爱好：<span></span></dt>
											<dt id="xqah" class="ziliao_jxnl_wz"></dt>
											</dl>
										</li>
									</ul>
								</div>
								<!-- 学生评价 -->
								<div id="allList" class="tab-content">
									<ul id="pjxx">
									</ul>
									<p id="allListPage" class="pager">
									</p>
								</div>
								<div id="pjxxPage"></div>
								<!-- 精彩回答 -->
								<div id="adoptList" class="tab-content">
								<ul id="hdxx">
								</ul>
									<p id="adoptListPage" class="pager">
									</p>
								</div>	
								<div id="hdxxPage"></div>
							</div>
						</div>
					</div>
					<!-- 教师简介-详细 end -->
					<!-- 右侧在线教师显示 begin -->
					<div class="expert-bottom-right w283">
						<div class="prof-aside">
							<h3 class="f-yahei"><span> 463 </span>位<b> 高中数学 </b>专家在线</h3>
							<ul>
								<li>
									<a class="portrait" href="#" target="_blank"><img src="<%=basePath%>/bin/img/resources/nopic2.png"></a>
									<a href="#" target="_blank" class="f-yahei">刘志浩</a>教育从业者
									<p>上海同济大学...</p>
								</li>
								<li>
									<a class="portrait" href="#" target="_blank"><img src="<%=basePath%>/bin/img/resources/nopic2.png"></a>
									<a href="#" target="_blank" class="f-yahei">王川</a>学生
									<p>天津大学...</p>
								</li>
								<li>
									<a class="portrait" href="#" target="_blank"><img src="<%=basePath%>/bin/img/resources/nopic2.png"></a>
									<a href="#" target="_blank" class="f-yahei">刘志浩</a>教育从业者
									<p>上海同济大学...</p>
								</li>
								<li>
									<a class="portrait" href="#" target="_blank"><img src="<%=basePath%>/bin/img/resources/nopic2.png"></a>
									<a href="#" target="_blank" class="f-yahei">王川</a>学生
									<p>天津大学...</p>
								</li>
								
							</ul>
							<h4 class="f-yahei visit-count">本页浏览量&nbsp;&nbsp;<span><b>3</b><b>4</b><b>8</b><b>2</b><b>5</b></span></h4>
							<div class="prof-detail"></div>
						</div>
					</div>
					<!-- 右侧在线教师显示 end -->
				</div>
			</div>
		</div>
		<div class="clear"></div>
		<%-- <script src="<%=basePath%>/bin/js/common/footer/footer_common.js" type="text/javascript"></script> --%>
	</div>
	<script type="text/javascript" src="<%=basePath%>/bin/js/resources/mod.js"></script>
	<script type="text/javascript" src="<%=basePath%>/bin/js/resources/framework.js"></script>
	<script type="text/javascript" src="<%=basePath%>/bin/js/resources/module.js"></script>
	<script type="text/javascript">	
		!function(){
			require.async(['common:widget/js/util/tangram/tangram.js', 
               'prof-misc:widget/js/tab/tab.js', 
               'common:widget/js/ui/prof-list/prof-list.js', 
               'common:widget/js/util/log/log.js'], 
               function(T, TabCtrl, ProfList, log){
					T(function(){
						var _getList = [],
							_tabList = T('.expert-single-answer .expert-tab li'),
							_tabContent = T('.expert-single-answer .expert-data-list div.tab-content');
				
						TabCtrl.init({
							'tabList' : _tabList,
							'tabContent' : _tabContent,
							'getContent' : function(order){
								var _type = T(_tabContent[order]).attr('id');
								if(!T.array(_getList).contains(_type)){
									_getList.push(_type);
								}
							}
						});
					});
			   }
			 );
		}();
	</script>
</body>
</html>