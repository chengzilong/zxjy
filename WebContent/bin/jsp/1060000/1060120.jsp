<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.xsjy.servlet.servlet1060000.Servlet1060120"%> 
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
<title>积分管理</title>
<script type="text/javascript" src="<%=basePath%>/bin/js/laydate/laydate.js"></script>
<script type="text/javascript" src="<%=basePath%>/bin/js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="<%=basePath%>/bin/js/common.js"></script>
<script type="text/javascript" src="<%=basePath%>/bin/js/json2.js"></script>
<!--表格脚本Start  -->
<script type="text/javascript" src="<%=basePath%>/bin/js/mmgrid/mmGrid.js"></script>
<script type="text/javascript" src="<%=basePath%>/bin/js/mmgrid/mmPaginator.js"></script>
<!--表格脚本End  -->
<script type="text/javascript" src="<%=basePath%>/bin/js/layer/layer.min.js"></script>
<script type="text/javascript">

var mmg;
var intheight;
//初始化表格
$(document).ready(function(){
   var cols = [
       { title:'姓名', name:'JJXX_RYXM' ,width:80, sortable:true, align:'center',lockDisplay: true },
       { title:'积分总数', name:'JJXX_JJZF' ,width:80, sortable:true, align:'center',lockDisplay: true  },
       { title:'人员类型', name:'JJXX_RYQF' ,width:80, sortable:true, align:'center',lockDisplay: true  },
       { title:'操作', name:'' ,width:30, sortable:true, align:'center',lockDisplay: true, renderer: function(val){
           return '<img id="img-info" class="img-info" title="详情" src="<%=basePath%>/bin/img/detail.gif"></img>';
       }}
       
   ];

   intheight = document.documentElement.clientHeight -$('#selectRegion').height()-80; 
   if(intheight<100){
	   intheight = 100;
   }

   mmg = $('.mmg').mmGrid({
         height: intheight
        ,cols: cols
        ,url: '<%=basePath%>/Servlet1060120'
        ,method: 'post'
        ,params:{CMD : "<%=Servlet1060120.CMD_SELECT%>"}
        ,remoteSort:true
        ,sortName: 'JJXX_JJID'
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

   mmg.on('cellSelected', function(e, item, rowIndex, colIndex){
        //配置节点
        if($(e.target).is('.img-info')){
            e.stopPropagation();  //阻止事件冒泡
            var jjId = item.JJXX_JJID;
            iframeLayerOpen('<%=basePath%>/bin/jsp/1060000/1060121.jsp');
        }
   });
   
   $('#btnSearch').on('click', function(){
	   loadGridByBean();
   });
    loadGridByBean();  
});
function makeBeanIn(strRYXM){
	this.JJXX_RYXM = strRYXM;
}
function loadGridByBean(){
	var beanIn = new makeBeanIn(
			$('#txtSelRYXM').val()
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
				<th style="width:100px">姓名</th>
				<td><input id="txtSelRYXM" name="姓名" maxlength="20" /></td>
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