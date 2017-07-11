<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.xsjy.servlet.servlet2010000.Servlet2010110"%> 
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
<title>我的消息</title>
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
       { title:'主题', name:'XXXX_FBZT' ,width:150, sortable:true, align:'center',lockDisplay: true },
       { title:'内容', name:'XXXX_FBNR' ,width:350, sortable:true, align:'center',lockDisplay: true },
       { title:'发布人', name:'XXXX_FBR' ,width:60, sortable:true, align:'center',lockDisplay: true },
       { title:'发布时间', name:'XXXX_FBSJ' ,width:60, sortable:true, align:'center',lockDisplay: true  },
       { title:'有效时间', name:'XXXX_YXSJ' ,width:60, sortable:true, align:'center',lockDisplay: true  },
       { title:'查看状态', name:'CKZT' ,width:60, sortable:true, align:'center',lockDisplay: true  },
  	   { title:'操作', name:'' ,width:30, sortable:true, align:'center',lockDisplay: true, renderer: function(val){
       		return '<img id="img-info" class="img-info" title="查看" src="<%=basePath%>/bin/img/detail.gif"></img>';
       }}
   ];

   intheight = document.documentElement.clientHeight -$('#editRegion').height()-$('#selectRegion').height()-80; 
   if(intheight<100){
	   intheight = 100;
   }

   mmg = $('.mmg').mmGrid({
         height: intheight
        ,cols: cols
        ,url: '<%=basePath%>/Servlet2010110'
        ,method: 'post'
        ,params:{CMD : "<%=Servlet2010110.CMD_SELECT%>"}
        ,remoteSort:true
        ,sortName: 'XXXX_XXID'
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
        if($(e.target).is('.img-info')){
            e.stopPropagation();  //阻止事件冒泡
			dataBean = item;
     		iframeLayerOpenown('<%=basePath%>/bin/jsp/2010000/2010111.jsp',item.MXID);	
        }
   });
   
   $('#btnSearch').on('click', function(){
	   loadGridByBean();
   });
    loadGridByBean();   
});
//自定义弹出层方法
function iframeLayerOpenown(url,mxid) {
	$.layer({
	      type: 2,
	      title: false,
	      maxmin: false,
	      shadeClose: false, //开启点击遮罩关闭层
	      area : ['800px' , '600px'],
	      offset : ['20px', ''],
	      iframe: {src: url},
	      end : function(){//弹出层彻底关闭执行的回调函数
		       updateCKZT(mxid);
			   window.parent.setMessCount('2010110');
			   loadGridByBean();	
	      }
	  });
}
function makeBeanIn(strFBSJ_B,strFBSJ_E,strFBR){
	this.BEGIN = strFBSJ_B;
	this.END = strFBSJ_E;
	this.FBR = strFBR;
}
function loadGridByBean(){
	var beanIn = new makeBeanIn(
			$('#txtSelFBSJ_BEGIN').val(),
			$('#txtSelFBSJ_END').val(),
			$('#txtSelFBR').val()
	);
	//重新查询数据
	mmg.load({
    	beanLoad  :  JSON.stringify(beanIn)
	});
}
function updateCKZT(strMXID){
	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/Servlet2010110",
		data: {
			CMD    : "<%=Servlet2010110.CMD_UPDATE%>",
			MXID : strMXID
		},
		complete : function(response) {},
		success : function(response) {
			var strResault = response[0];
			if (strResault == "SUCCESS") {
				
			} else if (strResault == "ERROR") {
				alert("更新消息查看状态失败！");
				
			}
		}
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
		        <td><input style="font-size:12px;line-height:16px; height:18px;"  class="laydate-icon-default"id="txtSelFBSJ_BEGIN" type="text"  onclick="laydate()" readonly></td>
		        <th >-</th>
		        <td><input style="font-size:12px;line-height:16px; height:18px;"  class="laydate-icon-default" id="txtSelFBSJ_END" type="text"  onclick="laydate()" readonly></td>
		        <th style="width:80px">发布人</th>
		        <td><input style="width:100px" id="txtSelFBR" type="text"   ></td>
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