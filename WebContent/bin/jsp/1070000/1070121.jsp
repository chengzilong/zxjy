<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.xsjy.servlet.servlet1070000.Servlet1070120"%> 
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path;

%>      
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!--表格样式Start  -->
<link rel="stylesheet" href="<%=basePath%>/bin/css/mmgrid/main.css" type="text/css">
<link rel="stylesheet" href="<%=basePath%>/bin/css/mmgrid/mmGrid.css" type="text/css">
<link rel="stylesheet" href="<%=basePath%>/bin/css/mmgrid/mmPaginator.css" type="text/css">
<link rel="stylesheet" href="<%=basePath%>/bin/css/mmgrid/normalize.css" type="text/css">
<link rel="stylesheet" href="<%=basePath%>/bin/css/mmgrid/tablesize.css" type="text/css">
<!--表格样式End  -->
<title>积分明细</title>
<script type="text/javascript" src="<%=basePath%>/bin/js/laydate/laydate.js"></script>
<script type="text/javascript" src="<%=basePath%>/bin/js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="<%=basePath%>/bin/js/common.js"></script>
<script type="text/javascript" src="<%=basePath%>/bin/js/json2.js"></script>
<!--表格脚本Start  -->
<script type="text/javascript" src="<%=basePath%>/bin/js/mmgrid/mmGrid.js"></script>
<script type="text/javascript" src="<%=basePath%>/bin/js/mmgrid/mmPaginator.js"></script>
<!--表格脚本End  -->
<script type="text/javascript">
var strJJMX_JJID="";
var mmg;
var intheight;
//初始化表格
$(document).ready(function(){
	if(window.dialogArguments != null) {
		var inputObj = window.dialogArguments; 
		strJJMX_JJID=inputObj.JJMX_JJID;
	}
   var cols = [
       { title:'积分类型', name:'JJMX_JJLX' ,width:80, sortable:true, align:'center',lockDisplay: true },
       { title:'积分数', name:'JJMX_JJFS' ,width:80, sortable:true, align:'center',lockDisplay: true  },
       { title:'获得时间', name:'JJMX_JJSJ' ,width:120, sortable:true, align:'center',lockDisplay: true  }
   ];

   intheight = document.documentElement.clientHeight -$('#editRegion').height()-$('#selectRegion').height()-80; 
   if(intheight<100){
	   intheight = 100;
   }

   mmg = $('.mmg').mmGrid({
         height: intheight
        ,cols: cols
        ,url: '<%=basePath%>/Servlet1070120'
        ,method: 'post'
        ,params:{CMD : "<%=Servlet1070120.CMD_SELECTMX%>"}
        ,remoteSort:true
        ,sortName: 'JJMX_JJMXID'
        ,sortStatus: 'ASC'
        ,root: 'items'
        ,multiSelect: false
        ,checkCol: false
        ,indexCol: true
        ,indexColWidth: 35
        ,fullWidthRows: true
        ,autoLoad: false
        ,plugins: [
            $('#pg').mmPaginator({
              limit:30
            })
        ]
      });
    loadGridByBean();
});


function makeBeanIn(strJJID){
	this.JJMX_JJID = strJJID;
}
function loadGridByBean(){
	var beanIn = new makeBeanIn(
			strJJMX_JJID
	);
	//重新查询数据
	mmg.load({
    	beanLoad  :  JSON.stringify(beanIn)
	});
}
</script>
</head>
<body>
	<div id="gridCanvas">
		<table id="mmg" class="mmg"></table>
		<div id="pg" style="text-align: right;"></div>
	</div>
</body>
</html>