<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.xsjy.servlet.school.ServletLession"%>  
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
<title>课程信息</title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>/bin/css/resources/logreg.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>/bin/css/resources/newlayout_zy.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>/bin/js/common/header/header.css" id="headercss"/>
<script language="JavaScript" src="<%=basePath%>/bin/js/common.js"></script>
<script language="JavaScript" src="<%=basePath%>/bin/js/jquery-1.9.1.js"></script>
<script language="JavaScript" src="<%=basePath%>/bin/js/laypage/laypage.js"></script>
<script language="javascript">
var lastText;
var beanIn=null;
var x=1;
$(document).ready(function() {
	beanIn = new makeBeanIn("","","","","","");
	
	searchListCount();
})

function over(obj){
	
	var LI = obj.childNodes;
	var lastLI ;
	var lessionID;
	var baomined;
	if(typeof(LI[0].innerHTML)=="undefined"){
		lastLI = LI[LI.length-2];
		lessionID = LI[1].lessionid;
		baomined =  LI[1].BMED;
	}else{
		lastLI = LI[LI.length-1];
		lessionID = LI[0].lessionid;
		baomined =  LI[0].BMED;
	}
	/* lastText=lastLI.innerHTML;
	lastLI.innerHTML="报名";
	lastLI.style.background="#1dbde5";
	lastLI.style.color="#fff";
	lastLI.style.fontSize="20px";
	lastLI.style.cursor="pointer";
	lastLI.onclick = function() {
		joinlession(lessionID);
	}; */
	lastText=lastLI.innerHTML;
	lastLI.innerHTML=baomined;
	if(baomined=="报名"){
		lastLI.style.background="#1dbde5";
		lastLI.style.color="#fff";
		
		lastLI.onclick = function() {
			var ret = joinlession(lessionID);
			if(ret){
				if(typeof(LI[0].innerHTML)=="undefined"){
					LI[0].BMED = "已报名";	
				}else{
					LI[1].BMED = "已报名";
				}
			}
		};
	}else{
		lastLI.style.background="#cdcdcd";
		lastLI.style.color="#fff";
		lastLI.onclick = null;
	}
	lastLI.style.cursor="pointer";
	lastLI.style.fontSize="20px";
	


}
function out(obj){
	var LI = obj.childNodes;
	var lastLI;
	if(typeof(LI[0].innerHTML)=="undefined"){
		lastLI = LI[LI.length-2];
	}else{
		lastLI = LI[LI.length-1];
	}
	if(typeof(lastText)!="undefined"){
		lastLI.innerHTML=lastText;
	}
	lastLI.style.background="#fff";
	lastLI.style.color="#666";
	lastLI.style.fontSize="12px";
}
function joinlession(lessionID){
	var blnRet = false;
	//1.判断用户是否登录
	if(getLoginUserBySession()=="NOLOGIN"){
		location.href=getRootPath() + '/bin/jsp/login/login.jsp';
		return;
	}

	//2.判断是否传入参数
	if(lessionID==""){
		return;
	}
	
	//3.判断角色是否正确，是否重复报名
	if(checkExist(lessionID) == true) return;
	
	//4.开始报名操作
	if(confirm("是否确认报名？")==false) return;
	if(insertData(lessionID)==true){
		blnRet = true;
	}
	return blnRet;
}
//验证数据重复
function checkExist(lessionID){
	var blnRet = false;
	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/ServletLession",
		data: {
			CMD    : "<%=ServletLession.CMD_CHK_EXIST%>",
			lessionID : lessionID
		},
		complete : function(response) {
		},
		success : function(response) {
			var strResult = response[0];
			if (strResult == "DATA_EXIST") {
				alert("友情提示：报名重复，无法继续进行！");
				blnRet = true;
			} else if (strResult == "DATA_NOT_EXIST") {
				blnRet = false;
			} else if (strResult == "ROLE_ERROR") {
				alert("友情提示：当前登录用户不是学生，无法报名！");
				blnRet = true;
			} else {
				alert("提示：验证数据重复时出错！");
				blnRet = true;
			}
		}
	});
	return blnRet;
}
//新增数据
function insertData(lessionID){
	var blnRet = false;
	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/ServletLession",
		data: {
			CMD    : "<%=ServletLession.CMD_JOIN%>",
			lessionID : lessionID
		},
		complete :function(response){},
		success: function(response){
			var strResult = response[0];
			if (strResult == "SUCCESS") {
				alert("恭喜：报名成功！");
				blnRet = true;
			} else if (strResult == "FAILURE") {
				alert("对不起：新增数据失败！");
				blnRet = false;
			} else {
				alert("提示：新增数据出错！");
				blnRet = false;
			}
		}
	});
	return blnRet;
}
//由条件JS调用
function selectlist(strjxxd,strqjxk,strkclx,strsksd,strskqy,strkcfy){
	beanIn = new makeBeanIn(
			strjxxd,
			strqjxk,
			strkclx,
			strsksd,
			strskqy,
			strkcfy
	);
	
	searchListCount();

}
function searchListCount(){
	
	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/ServletLession",
		data: {
			CMD      : "<%=ServletLession.CMD_DATA_COUNT%>",
		    BeanIn   : JSON.stringify(beanIn)
		},
		complete :function(response){},
		success: function(response){
			var strResult = response[0];
			if (strResult == "SUCCESS") {
				var pageCount = response[1];
				var pages = Math.ceil((pageCount/12));
				$('#count').html(response[1]);
				if(pageCount=="0"){
					$('#laypage').hide();
				}else{
					$('#laypage').show();
				}
				//定义分页
				laypage({
				    cont: 'laypage', //容器。值支持id名、原生dom对象，jquery对象。【如该容器为】：<div id="laypage"></div>
				    pages: pages, //页数
				    curr: 1, //当前页
				    skin: '#molv',
				    jump: function(e){ //触发分页后的回调
				    	searchListInfo(e.curr);
				    }
				});
			} else if (strResult == "FAILURE") {
				$('#laypage').hide();
				alert("对不起：加载数据失败");
			} else {
				$('#laypage').hide();
				alert("提示：加载数据异常");
			}
		}
	});
}

function searchListInfo(currpage){
	
	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/ServletLession",
		data: {
			CMD      : "<%=ServletLession.CMD_DATA_LIST%>",
		    BeanIn   : JSON.stringify(beanIn),
		    CurrPage : currpage,
		    limit    : 12,
		    Sort     : "KCFY_GXSJ"
		},
		complete :function(response){},
		success: function(response){
			var strResult = response[0];
			if (strResult == "SUCCESS") {
				displayList(response[1]);
			} else if (strResult == "FAILURE") {
				alert("对不起：加载数据失败");
			} else {
				alert("提示：加载数据异常");
			}
		}
	});
}


//定义bean
function makeBeanIn(strJXXD,strQJXK,strKCLX,strSKSD,strSKQY,strKCFY){
	this.JXXD = strJXXD;
	this.QJXK = strQJXK;
	this.KCLX = strKCLX;
	this.SKSD = strSKSD;
	this.SKQY = strSKQY;
	this.KCFY = strKCFY;
	
}
function displayList(tableList){
	$('#listtable').empty();//清空ul标签中的内容
	$('#listtable').hide();
	var divTable = document.getElementById("listtable");
//x=x+1;
	for ( var i = 0; i < tableList.length; i++) { 
	/* for ( var i = 0; i < x; i++) { */	
			
		
		var tableObj = tableList[i];
		var ulRow = document.createElement('UL');
		var liCol0 = document.createElement('LI');
		var liCol1 = document.createElement('LI');
		var liCol2 = document.createElement('LI');
		var liCol3 = document.createElement('LI');
		var liCol4 = document.createElement('LI');
		var liCol5 = document.createElement('LI');
		var liCol6 = document.createElement('LI');
		var liCol7 = document.createElement('LI');
		var liCol8 = document.createElement('LI');

		liCol1.className="width127 text_center text_ellipsis";
		liCol2.className="width127 text_center text_ellipsis";
		liCol3.className="width90 text_center text_ellipsis";
		liCol4.className="width90 text_center text_ellipsis";
		liCol5.className="width110 text_center text_ellipsis";
		liCol6.className="width127 text_center text_ellipsis";
		liCol7.className="width110 text_center text_ellipsis";
		liCol8.className="width104 text_center text_ellipsis";
		
		liCol0.lessionid=tableObj.KCFY_FYID;
		liCol0.BMED=tableObj.BMED;
 		liCol1.innerHTML="<span class='jingpin'>&nbsp;</span>";
		liCol2.innerHTML=(tableObj.KCMC == "" ? "&nbsp;" : tableObj.KCMC);
		liCol3.innerHTML=(tableObj.KCFY_XS == "" ? "&nbsp;" : tableObj.KCFY_XS+"学时");
		liCol4.innerHTML=(tableObj.SDMC == "" ? "&nbsp;" : tableObj.SDMC);
		liCol5.innerHTML=(tableObj.JSXM == "" ? "&nbsp;" : tableObj.JSXM);
		liCol6.innerHTML=(tableObj.SKQY == "" ? "&nbsp;" : tableObj.SKQY);
		liCol7.innerHTML=(tableObj.KCFY_FY == "" ? "&nbsp;" : tableObj.KCFY_FY);
		liCol8.innerHTML=(tableObj.LXMC == "" ? "&nbsp;" : tableObj.LXMC); 

		ulRow.appendChild(liCol0);
		ulRow.appendChild(liCol1);
		ulRow.appendChild(liCol2);
		ulRow.appendChild(liCol3);
		ulRow.appendChild(liCol4);
		ulRow.appendChild(liCol5);
		ulRow.appendChild(liCol6);
		ulRow.appendChild(liCol7);
		ulRow.appendChild(liCol8);
		ulRow.onmouseover=function() {over(this);};
		ulRow.onmouseout=function() {out(this);};
		divTable.appendChild(ulRow);
	}
	$('#listtable').show();
}
</script>
</head>
<body>

<script src="<%=basePath%>/bin/js/common/header/header.js?id=3" type="text/javascript"></script>
	<!-- 主体 begin -->
	<div id="content">
		<br/><script src="<%=basePath%>/bin/js/resources/nav_class_list.js" type="text/javascript"></script><!-- 详细导航和搜索条件 -->
		<div class="box960 fl mr">
			<!--公开课-->
			<div class="title_kuang">
				<div class="blue_t"></div><span class="gongong kechengxinxi_img">&nbsp;</span>
			</div>
			<div class="p_sort">
				<ul>
					<li style="text-align: left;"class="width127 text_center">
						<!-- <input name="" type="radio" value="" /> 
						<span>人气</span> 
						<input name="" type="radio" value="" /> 
						<span>时间</span>  -->
						&nbsp;&nbsp;搜索结果:<label id="count"></label>条
					</li>	
					<li class="width127 text_center">课程</li>	
					<li class="width90 text_center">学时</li>	
					<li class="width90 text_center">上课时段</li>	
					<li class="width110 text_center">任课老师</li>	
					<li class="width127 text_center">授课区域</li>	
					<li class="width110 text_center">费用</li>
					<li class="width104 text_center">类型</li>	
				</ul>
			</div>
			
			<div id="listtable" class="list_table">
			
			</div>
			<div style="height:0;line-height:0;overflow:hidden;"></div>
			<!-- 页码显示 begin -->&nbsp;
			<div class="pro_bottom_box clearfix page_pa40">
				<div id="laypage" class="page tc">
				</div>
			</div>
			<!-- 页码显示 end -->
			<br/><br/><br/>			
		</div>
	</div>
	
	<!-- 主体 begin -->
    <div class="clear"></div>
    <!--footer-->
	<script src="<%=basePath%>/bin/js/common/footer/footer_common.js" type="text/javascript"></script>
	
	
</body>
</html>