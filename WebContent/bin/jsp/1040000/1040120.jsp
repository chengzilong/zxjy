<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.xsjy.servlet.servlet1040000.Servlet1040120"%> 
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
<title>班次设定</title>
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
var obj;//参数对象
//初始化表格
$(document).ready(function(){
   var cols = [
       { title:'班次名称', name:'BCXX_BCMC' ,width:150, sortable:true, align:'center',lockDisplay: true },
       { title:'课程名称', name:'KCMC' ,width:100, sortable:true, align:'center',lockDisplay: true },
       { title:'开始时间', name:'BCXX_KSSJ' ,width:100, sortable:true, align:'center',lockDisplay: true },
       { title:'结束时间', name:'BCXX_JSSJ' ,width:100, sortable:true, align:'center',lockDisplay: true },
       { title:'上课地点', name:'BCXX_SKDD' ,width:150, sortable:true, align:'center',lockDisplay: true },
       { title:'班次状态', name:'BCZT' ,width:80, sortable:true, align:'center',lockDisplay: true },
       { title:'课程类型', name:'LXMC' ,width:80, sortable:true, align:'center',lockDisplay: true },
       { title:'上课时段', name:'SDMC' ,width:50, sortable:true, align:'center',lockDisplay: true },
       { title:'学时', name:'XS' ,width:50, sortable:true, align:'center',lockDisplay: true },
       { title:'授课教师', name:'JSXM' ,width:80, sortable:true, align:'center',lockDisplay: true },
       { title:'费用', name:'BCXX_FY' ,width:80, sortable:true, align:'center',lockDisplay: true },
       { title:'是否验证', name:'SFYZ' ,width:80, sortable:true, align:'center',lockDisplay: true },
       { title:'录入人', name:'BCXX_LRR' ,width:80, sortable:true, align:'center',lockDisplay: true }
   ];
             
   intheight = document.documentElement.clientHeight -$('#editRegion').height()-$('#selectRegion').height()-80; 
   if(intheight<100){
	   intheight = 100;
   }

   mmg = $('.mmg').mmGrid({
         height: intheight
        ,cols: cols
        ,url: '<%=basePath%>/Servlet1040120'
        ,method: 'post'
        ,params:{CMD : "<%=Servlet1040120.CMD_SELECT%>"}
        ,remoteSort:true
        ,sortName: 'BCXX_BCID'
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
   //查询点击事件
   $('#btnSearch').on('click', function(){
	   loadGridByBean();
   });
   //验证点击事件
   $('#btnCheck').on('click', function(){
	   var arrList = mmg.selectedRows();
 	   if(arrList.length<=0){
 		  layer.alert('请选择要验证的数据行！', 0, '友情提示');
  	       return;
 	   } else if (arrList[0].BCXX_SFYZ == '1') {
 		  layer.alert('班次已验证，无需重新验证！', 0, '友情提示');
  	      return;
 	   }
 	  layer.confirm('是否验证选中数据？', function() {
 		 if(checkClass(arrList[0].BCXX_BCID)==true){
 	 		   //重新查询数据
 	 		   loadGridByBean();
 	 		   mmg.deselect('all');
 	 	   }
 	  });
   });
 //取消验证点击事件
   $('#btnCancelCheck').on('click', function(){
	   var arrList = mmg.selectedRows();
 	   if(arrList.length<=0){
 		  layer.alert('请选择要取消验证的数据行！', 0, '友情提示');
  	      return;
 	   } else if (arrList[0].BCXX_SFYZ == '0') {
 		  layer.alert('班次尚未验证，无需取消！', 0, '友情提示');
  	      return;
 	   }
 	  layer.confirm('是否取消验证选中数据？', function() {
 		 if (cancelCheckClass(arrList[0].BCXX_BCID) == true) {
 	 		   //重新查询数据
 	 		   loadGridByBean();
 	 		   mmg.deselect('all');
 	 	   }
 	 });
   });
   /* 页面初始化加载数据 */
    loadGridByBean();
    /* 下拉列表初始化加载 */
    loadSearchSelect($("#selectKcmc"),"TYPE_KCMC","课程名称");
  	//删除点击事件
    $('#btnDel').on('click', function(){
 	   var arrList = mmg.selectedRows();
 	   if(arrList.length<=0){
 		  layer.alert('请选择要删除的数据行！', 0, '友情提示');
  	      return;
 	   }
 	  layer.confirm('是否删除选中数据？', function() {
 		 if (deleteData(arrList[0].BCXX_BCID) == true) {
 	 		   //重新查询数据
 	 		   loadGridByBean();
 	 		   mmg.deselect('all');
 	 	   }
 	 });
    });
});
//定义bean
function makeBeanIn(strBCXX_BCID, strBCXX_BCMC, strBCXX_KCID, strBCXX_BCZT, strBCXX_SFYZ){
	this.BCXX_BCID = strBCXX_BCID;
	this.BCXX_BCMC = strBCXX_BCMC;
    this.BCXX_KCID = strBCXX_KCID;
    this.BCXX_BCZT = strBCXX_BCZT;
    this.BCXX_SFYZ = strBCXX_SFYZ;
}
//加载数据
function loadGridByBean(){
	var beanIn = new makeBeanIn(
			"",
			$('#txtSelectBcmc').val(),
			$('#selectKcmc').val(),
			$('#selectBczt').val(),
			$('#selectSfyz').val()
			
	);
	//重新查询数据
	mmg.load({
    	beanLoad  :  JSON.stringify(beanIn)
	});
}
//创建班次方法
function btn_Add() {
	obj = new Object();
	obj.option = "Add";
	iframeLayerOpen('<%=basePath%>/bin/jsp/1040000/1040121.jsp');
}
//调整班次方法
function btn_Upd() {
	var arrList = mmg.selectedRows();
	   if(arrList.length<=0){
		   layer.alert('请选择要调整的班次！', 0, '友情提示');
	       return;
	   }
	obj = new Object();
	obj.option = "Upd";
	obj.dataId = arrList[0].BCXX_BCID;
	obj.bcmc = arrList[0].BCXX_BCMC;
	obj.kssj = arrList[0].BCXX_KSSJ;
	obj.jssj = arrList[0].BCXX_JSSJ;
	obj.skdd = arrList[0].BCXX_SKDD;
	iframeLayerOpen('<%=basePath%>/bin/jsp/1040000/1040121.jsp');
}
//删除班次方法
function deleteData(dataId){
	var blnRet = false;
	var beanIn = new makeBeanIn(
			dataId,"","","","",""
	);
	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/Servlet1040120",
		data: {
			CMD    : "<%=Servlet1040120.CMD_DELETE%>",
			BeanIn : JSON.stringify(beanIn)
		},
		complete : function(response) {},
		success : function(response) {
			var strResult = response[0];
			if (strResult == "SUCCESS") {
				layer.msg('恭喜：删除数据成功！', 1, 9);
				blnRet = true;
			} else if (strResult == "FAILURE") {
				layer.msg('对不起：删除数据失败！', 1, 8);
				blnRet = false;
			} else {
				layer.msg('提示：删除数据出错！', 1, 0);
				blnRet = false;
			}
		}
	});
	return blnRet;
}
//验证班次方法
function checkClass(bcid) {
	var blnRet = false;
	var beanIn = new makeBeanIn(
			bcid,"","","","",""
	);
	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/Servlet1040120",
		data: {
			CMD    : "<%=Servlet1040120.CMD_CHECK%>",
			BeanIn : JSON.stringify(beanIn)
		},
		complete : function(response) {},
		success : function(response) {
			var strResult = response[0];
			if (strResult == "SUCCESS") {
				layer.msg('恭喜：验证数据成功！', 1, 9);
				blnRet = true;
			} else if (strResult == "FAILURE") {
				layer.msg('对不起：验证数据失败！', 1, 8);
				blnRet = false;
			} else {
				layer.msg('提示：验证数据出错！', 1, 0);
				blnRet = false;
			}
		}
	});
	return blnRet;
}
//取消验证班次方法
function cancelCheckClass(bcid) {
	var blnRet = false;
	var beanIn = new makeBeanIn(
			bcid,"","","","",""
	);
	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/Servlet1040120",
		data: {
			CMD    : "<%=Servlet1040120.CMD_CANCEL_CHECK%>",
			BeanIn : JSON.stringify(beanIn)
		},
		complete : function(response) {},
		success : function(response) {
			var strResult = response[0];
			if (strResult == "SUCCESS") {
				layer.msg('恭喜：取消验证数据成功！', 1, 9);
				blnRet = true;
			} else if (strResult == "FAILURE") {
				layer.msg('对不起：取消验证数据失败！', 1, 8);
				blnRet = false;
			} else {
				layer.msg('提示：取消验证数据出错！', 1, 0);
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
				<th style="width:100px">班次名称</th>
				<td><input id="txtSelectBcmc" name="班次名称" maxlength="20" /></td>
				<th style="width:100px">课程名称</th>
				<td><select id="selectKcmc" style="width: 100px"></select></td>
				<th style="width:100px">班次状态</th>
				<td>
					<select id="selectBczt" style="width: 100px">
						<option value="000">所有</option>
						<option value="0">未开课</option>
						<option value="1">已开课</option>
						<option value="2">已结束</option>
					</select>
				</td>
				<th style="width:100px">是否验证</th>
				<td>
					<select id="selectSfyz" style="width: 100px">
						<option value="000">所有</option>
						<option value="0">未验证</option>
						<option value="1">已验证</option>
					</select>
				</td>
				<th  style="width:100px"><input type="button" value="查询" id="btnSearch" /></th>
			</tr>
		</table>
	</fieldset>
	<div id="gridCanvas">
		<table id="mmg" class="mmg"></table>
		<div id="pg" style="text-align: right;"></div>
	</div>
	<fieldset id = "editRegion">
		<legend>报名操作</legend>
		<div id="buttonCanvas" class="gToolbar gTbrCenter ">
			<input type="button" value="创建班次" onclick="btn_Add()" />
			<input type="button" value="调整班次" onclick="btn_Upd()" />
			<input type="button" value="删除班次" id="btnDel" name="btnDel" />
			<input type="button" value="验证" id="btnCheck" />
			<input type="button" value="取消验证" id="btnCancelCheck" /> 
		</div>
	</fieldset>
</body>
</html>