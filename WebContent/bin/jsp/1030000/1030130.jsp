<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.xsjy.servlet.servlet1030000.Servlet1030130"%> 
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
<title>消息查询</title>
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
       { title:'主题', name:'XXXX_FBZT' ,width:100, sortable:true, align:'center',lockDisplay: true },
       { title:'内容', name:'XXXX_FBNR' ,width:350, sortable:true, align:'center',lockDisplay: true },
       { title:'发布人', name:'XXXX_FBR' ,width:60, sortable:true, align:'center',lockDisplay: true },
       { title:'发布时间', name:'XXXX_FBSJ' ,width:60, sortable:true, align:'center',lockDisplay: true  },
       { title:'有效时间', name:'XXXX_YXSJ' ,width:60, sortable:true, align:'center',lockDisplay: true  },
  	   { title:'操作', name:'' ,width:30, sortable:true, align:'center',lockDisplay: true, renderer: function(val){
       		return '<img id="img-info" class="img-info" title="详情" src="<%=basePath%>/bin/img/detail.gif"></img>';
       }}
   ];

   intheight = document.documentElement.clientHeight -$('#editRegion').height()-$('#selectRegion').height()-80; 
   if(intheight<100){
	   intheight = 100;
   }

   mmg = $('.mmg').mmGrid({
         height: intheight
        ,cols: cols
        ,url: '<%=basePath%>/Servlet1030130'
        ,method: 'post'
        ,params:{CMD : "<%=Servlet1030130.CMD_SELECT%>"}
        ,remoteSort:true
        ,sortName: 'XXXX_XXID'
        ,sortStatus: 'ASC'
        ,root: 'items'
        ,multiSelect: false
        ,checkCol: false
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
        if($(e.target).is('.img-info')){
            e.stopPropagation();  //阻止事件冒泡
            dataBean = item;
     		iframeLayerOpen('<%=basePath%>/bin/jsp/1030000/1030131.jsp');
        }
   });
   
   $('#btnSearch').on('click', function(){
	   loadGridByBean();
   });
    loadGridByBean();   
});

function makeBeanIn(strFBSJ_B,strFBSJ_E,strFBR,strJSR){
	this.BEGIN = strFBSJ_B;
	this.END = strFBSJ_E;
	this.FBR = strFBR;
	this.JSR = strJSR;
}
function loadGridByBean(){
	var beanIn = new makeBeanIn(
			$('#txtSelFBSJ_BEGIN').val(),
			$('#txtSelFBSJ_END').val(),
			$('#txtSelFBR').val(),
			$('#txtSelJSR').val()	
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
				<th style="width:100px">发布时间</th>
		        <td><input style="font-size:12px;line-height:16px; height:18px;"  class="laydate-icon-default" id="txtSelFBSJ_BEGIN" type="text"  onclick="laydate()" readonly></td>
		        <th >-</th>
		        <td><input style="font-size:12px;line-height:16px; height:18px;"  class="laydate-icon-default" id="txtSelFBSJ_END" type="text"  onclick="laydate()" readonly></td>
		        <th style="width:80px">发布人</th>
		        <td><input style="width:100px" id="txtSelFBR" type="text"   ></td>
		        <th style="width:80px">接收人</th>
		        <td><input style="width:100px"  id="txtSelJSR" type="text"   ></td>
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