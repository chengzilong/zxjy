<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.xsjy.servlet.servlet2020000.Servlet2020130"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	    <title>课表日程</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">    
		<!--表格样式Start  -->
		<link rel="stylesheet" href="<%=basePath%>/bin/css/mmgrid/main.css" type="text/css">
		<link rel="stylesheet" href="<%=basePath%>/bin/css/mmgrid/mmGrid.css" type="text/css">
		<link rel="stylesheet" href="<%=basePath%>/bin/css/mmgrid/mmPaginator.css" type="text/css">
		<link rel="stylesheet" href="<%=basePath%>/bin/css/mmgrid/normalize.css" type="text/css">
		<!--表格样式End  -->
		<script type="text/javascript" src="<%=basePath%>/bin/js/jquery-1.9.1.js"></script>
		<script type="text/javascript" src="<%=basePath%>/bin/js/common.js"></script>
		<script type="text/javascript" src="<%=basePath%>/bin/js/json2.js"></script>
		<!--表格脚本Start  -->
		<script type="text/javascript" src="<%=basePath%>/bin/js/mmgrid/mmGrid.js"></script>
		<script type="text/javascript" src="<%=basePath%>/bin/js/mmgrid/mmPaginator.js"></script>
		<!--表格脚本End  -->
		<script type="text/javascript">
			var mmg;
			//初始化表格
			$(document).ready(function() {
				//定义列值
				var cols = new Array({ title:'班次名称', name:'BCXX_BCMC' ,width:120, sortable:true, align:'center',lockDisplay: true  });
				$.ajax({
					async     : false,
					type      : "post",
					dataType  : "json",
					url: "<%=basePath%>/Servlet2020130",
					data: {
						CMD    : "<%=Servlet2020130.CMD_GET_TITLE%>"
					},
					complete : function(response) {
					},
					success : function(response) {
						var strResult = response[0];
						var titleList = response[1];
						if(strResult == "SUCCESS") {
							$(titleList).each(function(i) {
								var obj = { title:titleList[i].ROTA_WEEK, name:'ROTA_DAY' + (i+1) ,width:80, sortable:true, align:'center',lockDisplay: true};
								cols.push(obj);
							});
						} else {
							alert("提示：加载表头信息出错！");
						}
					}
				});
				//定义表格宽度
				var intheight = document.documentElement.clientHeight - $('#selectRegion').height() - 80;
				if(intheight < 100){
					intheight = 100;
				}
				//定义表格对象
				mmg = $('.mmg').mmGrid({
					height: intheight
					,cols: cols
					,url: '<%=basePath%>/Servlet2020130'
					,method: 'post'
					,params:{CMD : "<%=Servlet2020130.CMD_SELECT%>"}
					,remoteSort:true
					,sortName: 'BCXX_BCID'
					,sortStatus: 'ASC'
					,root: 'items'
					,multiSelect: true
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
				//定义查询按钮事件
				$('#btnSearch').on('click', function(){
					loadGridByBean();
				});
				//页面初始化加载数据
				loadGridByBean();
				//初始化下拉列表
				loadSearchSelect($("#txtSelectBcmc"),"TYPE_BCMCXS","班次名称");
			});
			//定义bean
			function makeBeanIn(strBCXX_BCID) {
			    this.BCXX_BCID = strBCXX_BCID;
			}
			//加载数据方法
			function loadGridByBean() {
				var beanIn = new makeBeanIn(
					$('#txtSelectBcmc').val()
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
					<th style="width:100px">班次名称</th>
					<td style="width:100px">
						<select id="txtSelectBcmc"> </select>
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
