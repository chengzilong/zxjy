<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.xsjy.servlet.servlet1030000.Servlet1030110"%>
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
<title>消息发布</title>
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
       { title:'主题222', name:'XXXX_FBZT' ,width:100, sortable:true, align:'center',lockDisplay: true },
       { title:'内容22', name:'XXXX_FBNR' ,width:350, sortable:true, align:'center',lockDisplay: true },
       { title:'发布人', name:'FBRXM' ,width:60, sortable:true, align:'center',lockDisplay: true },
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
        ,url: '<%=basePath%>/Servlet1030110'
        ,method: 'post'
        ,params:{CMD : "<%=Servlet1030110.CMD_SELECT%>"}
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
     		iframeLayerOpen('<%=basePath%>/bin/jsp/1030000/1030112.jsp');
        }
   });

   $('#btnSearch').on('click', function(){
	   loadGridByBean();
   });

   $('#btnDel').on('click', function(){
	   var arrList = mmg.selectedRows();
	   if(arrList.length<=0){
		   layer.alert('请选择要删除的数据行！', 0, '友情提示');
 	       return;
	   }
	   layer.confirm('是否删除消息？', function() {
		   if(deleteXXXXCode(arrList[0].XXXX_XXID)==true){
			   //重新查询数据
			   loadGridByBean();
		   }
	   });
   });

    loadGridByBean();
  //定义新增点击事件
    $('#btnAdd').on('click', function(){
    	iframeLayerOpen('<%=basePath%>/bin/jsp/1030000/1030111.jsp?option=Add');
    });
  //定义修改点击事件
    $('#btnUpd').on('click', function(){
    	var arrList = mmg.selectedRows();
    	if (arrList.length <= 0) {
    		layer.alert('请选择要调课的消息！', 0, '友情提示');
    	    return;
    	}
 		dataBean = arrList[0];
 		iframeLayerOpen('<%=basePath%>/bin/jsp/1030000/1030111.jsp?option=Upd');
    });
});

function makeBeanIn(strXXID, strYXSJ){
    this.XXXX_XXID = strXXID;
	this.XXXX_YXSJ = strYXSJ;
}

function loadGridByBean(){
	var beanIn = new makeBeanIn(
			"",$('#txtSelYXSJ').val()
	);
	//重新查询数据
	mmg.load({
    	beanLoad  :  JSON.stringify(beanIn)
	});
}

function deleteXXXXCode(dataId){
	var blnRet = false;
	var beanIn = new makeBeanIn(
			dataId,""
	);

	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/Servlet1030110",
		data: {
			CMD    : "<%=Servlet1030110.CMD_DELETE%>",
			BeanIn : JSON.stringify(beanIn)
		},
		complete : function(response) {},
		success : function(response) {
			var strResault = response[0];
			if (strResault == "SUCCESS") {
				layer.msg('恭喜：删除消息成功！', 1, 9);
				blnRet = true;
			} else if (strResault == "ERROR") {
				layer.msg('提示：删除消息出错！', 1, 0);
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
		        <td><input id="txtSelYXSJ" type="text" style="font-size:12px;line-height:16px; height:18px;"  class="laydate-icon-default"   onclick="laydate()" readonly></td>
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
			<input type="button" value="新增" id="btnAdd" />

			<input type="button" value="调整" id="btnUpd" />
			<input type="button" value="删除" id="btnDel" name="btnDel" />

		</div>
	</fieldset>
</body>
</html>