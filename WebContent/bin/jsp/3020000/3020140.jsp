<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.xsjy.servlet.servlet3020000.Servlet3020140"%> 
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
<title>发送消息</title>
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
var userid=getLoginUserIdBySession();
var obj;
//初始化表格
$(document).ready(function(){
	//设置列值
   var cols = [
       { title:'主题', name:'XXXX_FBZT' ,width:150, sortable:true, align:'center',lockDisplay: true },
       { title:'发布内容', name:'XXXX_FBNR' ,width:400, sortable:true, align:'center',lockDisplay: true  },
       { title:'发布时间', name:'XXXX_FBSJ' ,width:80, sortable:true, align:'center',lockDisplay: true  },
       { title:'有效时间', name:'XXXX_YXSJ' ,width:80, sortable:true, align:'center',lockDisplay: true  },
  	   { title:'操作', name:'' ,width:30, sortable:true, align:'center',lockDisplay: true, renderer: function(val){
       		return '<img id="img-info" class="img-info" title="详情" src="<%=basePath%>/bin/img/detail.gif"></img>';
       }}
   ];
   //设置高度
   intheight = document.documentElement.clientHeight -$('#editRegion').height()-$('#selectRegion').height()-80; 
   if(intheight<100){
	   intheight = 100;
   }
	//定义表格对象
   mmg = $('.mmg').mmGrid({
         height: intheight
        ,cols: cols
        ,url: '<%=basePath%>/Servlet3020140'
        ,method: 'post'
        ,params:{CMD : "<%=Servlet3020140.CMD_INDEX_SELECT%>"}
        ,remoteSort:true
        ,sortName: 'XXXX_XXID'
        ,sortStatus: 'ASC'
        ,root: 'items'
        ,multiSelect: false
        ,checkCol: true
        ,indexCol: true
        ,indexColWidth: 35
        ,fullWidthRows: true
        ,autoLoad: false
        ,nowrap: true
        ,plugins: [
            $('#pg').mmPaginator({
              limit:30
            })
        ]
      });
   //定义表格事件
   mmg.on('cellSelected', function(e, item, rowIndex, colIndex){
	  var arrList = mmg.row(rowIndex);
        if($(e.target).is('.img-info')){
            e.stopPropagation();  //阻止事件冒泡
            obj = new Object();
			obj.XXXX_XXID = item.XXXX_XXID;
			obj.XXXX_FBZT = item.XXXX_FBZT;
			obj.XXXX_FBNR = item.XXXX_FBNR;
			obj.XXXX_FBSJ = item.XXXX_FBSJ;
			obj.XXXX_YXSJ = item.XXXX_YXSJ;
			iframeLayerOpen('<%=basePath%>/bin/jsp/3020000/3020141.jsp');
        }
   });
   //定义查询事件
   $('#btnSearch').on('click', function(){
	   loadGridByBean();
   });
   //触发删除事件
   $('#btnDel').on('click', function(){
	   var arrList = mmg.selectedRows();
	   if(arrList.length<=0){
		   layer.alert('请选择要删除的数据行！', 0, '友情提示');
 	       return;
	   }
	   layer.confirm('是否删除消息？', function() {
		   if(deleteData(arrList[0].XXXX_XXID)==true){
			   //重新查询数据
			   loadGridByBean();
		   }
	   });
   });
   //初始化页面加载数据
    loadGridByBean();   
});
//新增方法
function btn_Add(){
	obj = new Object();
	obj.optionFlag = "Add";
	obj.userId = userid;
	iframeLayerOpen('<%=basePath%>/bin/jsp/3020000/3020142.jsp');
}
//更新方法
function btn_Upd(){
	var arrList = mmg.selectedRows();
	if (arrList.length <= 0) {
		layer.alert('请选择要重新发送的消息！', 0, '友情提示');
		return;
	}
	obj = new Object();
	obj.XXXX_XXID=arrList[0].XXXX_XXID;
	obj.XXXX_FBSJ=arrList[0].XXXX_FBSJ;
	obj.XXXX_YXSJ=arrList[0].XXXX_YXSJ;
	obj.XXXX_FBZT=arrList[0].XXXX_FBZT;
	obj.XXXX_FBNR=arrList[0].XXXX_FBNR;
	obj.optionFlag = "Upd";
	obj.userId = userid;
	iframeLayerOpen('<%=basePath%>/bin/jsp/3020000/3020142.jsp');
}
//定义bean
function makeBeanIn(strXXID, strYXSJ){
    this.XXXX_XXID = strXXID;  
	this.XXXX_YXSJ = strYXSJ;
}
//加载数据方法
function loadGridByBean(){
	var beanIn = new makeBeanIn(
			"",$('#txtSelYXSJ').val()
	);
	//重新查询数据
	mmg.load({
    	beanLoad  :  JSON.stringify(beanIn)
	});
}
//删除方法
function deleteData(dataId){
	var blnRet = false;
	var beanIn = new makeBeanIn(
			dataId,""
	);
	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/Servlet3020140",
		data: {
			CMD    : "<%=Servlet3020140.CMD_DELETE%>",
			BeanIn : JSON.stringify(beanIn)
		},
		complete : function(response) {},
		success : function(response) {
			var strResult = response[0];
			if (strResult == "SUCCESS") {
				layer.msg('恭喜：删除消息成功！', 1, 9);
				blnRet = true;
			} else if (strResult == "FAILURE") {
				layer.msg('友情提示：消息已经被查看，无法删除！', 1, 0);
				blnRet = false;
			} else {
				layer.msg('提示：删除信息出现异常！', 1, 0);
				blnRet = false;
	        }
		}
	});
	return blnRet;
}
</script>
</head>
<body>
	<fieldset id = "selectRegion">
		<legend>查询条件</legend>
		<table>
			<tr>
				<th style="width:100px">有效时间</th>
		        <td><input id="txtSelYXSJ" type="text"  style="font-size:12px;line-height:16px; height:18px;"  class="laydate-icon-default"  onclick="laydate()" readonly></td>
				<th  style="width:100px"><input type="button" value="查询" id="btnSearch" /></th>
			</tr>
		</table>
	</fieldset>
	<div id="gridCanvas">
		<table id="mmg" class="mmg"></table>
		<div id="pg" style="text-align: right;"></div>
	</div>
	<fieldset id = "editRegion">
		<legend>操作</legend>
		<div id="buttonCanvas" class="gToolbar gTbrCenter ">
			<input type="button" value="新增" onclick="btn_Add()" />
<!-- 
			<input type="button" value="调整" onclick="btn_Upd()" /> 
			<input type="button" value="删除" id="btnDel" name="btnDel" /> 
 -->	
		</div>
	</fieldset>
</body>
</html>