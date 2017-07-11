<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.xsjy.servlet.servlet1040000.Servlet1040110"%> 
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
<title>报名统计</title>
<script type="text/javascript" src="<%=basePath%>/bin/js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="<%=basePath%>/bin/js/common.js"></script>
<script type="text/javascript" src="<%=basePath%>/bin/js/json2.js"></script>
<!--表格脚本Start  -->
<script type="text/javascript" src="<%=basePath%>/bin/js/mmgrid/mmGrid.js"></script>
<script type="text/javascript" src="<%=basePath%>/bin/js/mmgrid/mmPaginator.js"></script>
<!--表格脚本End  -->
<script type="text/javascript">
var mmg;
var intheight;
//初始化表格
$(document).ready(function(){
   var cols = [
       { title:'课程名称', name:'KCMC' ,width:80, sortable:true, align:'center',lockDisplay: true },
       { title:'报名人数', name:'BMRS' ,width:150, sortable:true, align:'center',lockDisplay: true }
   ];
             
    intheight = document.documentElement.clientHeight -$('#selectRegion').height()-80; 
   if(intheight<100){
	   intheight = 100;
   }

   mmg = $('.mmg').mmGrid({
         height: intheight
        ,cols: cols
        ,url: '<%=basePath%>/Servlet1040110'
        ,method: 'post'
        ,params:{CMD : "<%=Servlet1040110.CMD_SELECT%>"}
        ,remoteSort:true
        ,sortName: 'KCID'
        ,sortStatus: 'ASC'
        ,root: 'items'
        ,multiSelect: false
        ,checkCol: false
        ,indexCol: true
        ,indexColWidth: 35
        ,fullWidthRows: true
        ,autoLoad: false
      });
   //查询点击事件
   $('#btnSearch').on('click', function(){
	   loadGridByBean();
   });
    loadGridByBean();
    
    loadSearchSelect($("#selectKcmc"),"TYPE_KCMC","课程名称");
});

function makeBeanIn(strKCID){
    this.KCID = strKCID;
}
//加载数据
function loadGridByBean(){
	var beanIn = new makeBeanIn(
			$('#selectKcmc').val()
	);
	//重新查询数据
	mmg.load({
    	beanLoad  :  JSON.stringify(beanIn)
	});
}
</script>
</head>
<body>
	<fieldset id = "selectRegion">
		<legend>查询条件</legend>
		<table>
			<tr>
				<th style="width:100px">课程名称</th>
				<td><select id="selectKcmc" style="width: 100px"></select></td>
				<th  style="width:100px"><input type="button" value="查询" id="btnSearch" /></th>
			</tr>
		</table>
	</fieldset>
	<div id="gridCanvas">
		<table id="mmg" class="mmg"></table>
	</div>
</body>
</html>