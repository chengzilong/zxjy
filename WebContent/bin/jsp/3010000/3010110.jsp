<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.xsjy.servlet.servlet3010000.Servlet3010110"%> 
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
<title>问题信息</title>
<script type="text/javascript" src="<%=basePath%>/bin/js/laydate/laydate.js"></script>
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
       { title:'问题领域', name:'KCMC' ,width:80, sortable:true, align:'center',lockDisplay: true },
       { title:'提问人', name:'TWR' ,width:80, sortable:true, align:'center',lockDisplay: true },
       { title:'提问内容', name:'WTXX_TWNR' ,width:300, sortable:true, align:'center',lockDisplay: true },
       { title:'提问时间', name:'WTXX_TWSJ' ,width:80, sortable:true, align:'center',lockDisplay: true },
       { title:'提问状态', name:'WTXX_TWZT' ,width:60, sortable:true, align:'center',lockDisplay: true },
       { title:'有效时间', name:'YXSJ' ,width:60, sortable:true, align:'center',lockDisplay: true },
       { title:'悬赏积分', name:'WTXX_TWJF' ,width:60, sortable:true, align:'center',lockDisplay: true },
       { title:'回答数量', name:'HDSL' ,width:60, sortable:true, align:'center',lockDisplay: true },
       { title:'操作', name:'' ,width:30, sortable:true, align:'center',lockDisplay: true, renderer: function(val){
           return '<img id="img-info" class="img-info" title="回答" src="<%=basePath%>/bin/img/detail.gif"></img>';
       }}
   ];

   intheight = document.documentElement.clientHeight -$('#selectRegion').height()-50; 
   if(intheight<100){
	   intheight = 100;
   }

   mmg = $('.mmg').mmGrid({
         height: intheight
        ,cols: cols
        ,url: '<%=basePath%>/Servlet3010110'
        ,method: 'post'
        ,params:{CMD : "<%=Servlet3010110.CMD_SELECT%>"}
        ,remoteSort:true
        ,sortName: 'WTXX_WTID'
        ,sortStatus: 'ASC'
        ,root: 'items'
        ,multiSelect: false
        ,indexCol: true
        ,indexColWidth: 35
        ,fullWidthRows: true
        ,autoLoad: false
        ,nowrap:true
        ,plugins: [
            $('#pg').mmPaginator({
              limit:30
            })
        ]
      });
   mmg.on('cellSelected', function(e, item, rowIndex, colIndex){
	  var arrList = mmg.row(rowIndex);
	  	
        //配置节点
        if($(e.target).is('.img-info')){
            e.stopPropagation();  //阻止事件冒泡
	    	window.location.href='<%=basePath%>/bin/jsp/3010000/3010111.jsp?wtid=' + item.WTXX_WTID+'&twr='+item.TWR+'&twnr='+item.WTXX_TWNR+'&twsj='+item.WTXX_TWSJ;
			
        }
   });
   $('#btnSearch').on('click', function(){
	   loadGridByBean();
   });  
    loadSearchSelect($("#txtSelKCID"),"TYPE_KCMC","问题领域");
    loadGridByBean();
});

function makeBeanIn(strKCID){
	this.WTXX_KCID = strKCID;
}
function loadGridByBean(){
	var beanIn = new makeBeanIn(
			$('#txtSelKCID').val()
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
				<th style="width:100px">问题领域</th>
				<td style="width:100px">
					<select id="txtSelKCID"> </select>
				</td>
				<th  style="width:100px"><input type="button" value="查询" id="btnSearch" /></th>
			</tr>
		</table>
	</fieldset>
	<div id="gridCanvas">
		<table id="mmg" class="mmg"></table>
		<div id="pg" style="text-align: right;"></div>
	</div>
</body>
</html>