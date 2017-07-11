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
<title>发布消息-详情二级页</title>
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
			var obj;//参数对象
			//初始化表格
			$(document).ready(function(){
				/* 初始化传递参数Start */
				obj = window.parent.obj;
				/* 初始化传递参数End */
				/* 初始化表单数据 */
				$('#txtXXID').val(obj.XXXX_XXID);
		        $('#txtFBZT').val(obj.XXXX_FBZT);
		        $('#txtFBNR').val(obj.XXXX_FBNR);
		        $('#txtFBSJ').val(obj.XXXX_FBSJ);
		        $('#txtYXSJ').val(obj.XXXX_YXSJ);
		        /* 初始化表格数据 */
		        //设置列值
				var cols = [
					{ title:'接收人', name:'XXMX_JSR' ,width:80, sortable:true, align:'center',lockDisplay: true },
					{ title:'查看状态', name:'CKZTMC' ,width:80, sortable:true, align:'center',lockDisplay: true  }
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
					,params:{CMD : "<%=Servlet3020140.CMD_INDEX_DETAIL%>"}
					,remoteSort:true
					,sortName: 'XXMX_MXID'
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
							limit:20
						})
					]
				});
				//初始化页面加载数据
				loadGridByBean(obj.XXXX_XXID);
			});
			//加载数据方法
			function loadGridByBean(strXXID){
				//重新查询数据
				mmg.load({
			    	XXID  :  strXXID
				});
			}	
		</script>
	</head>
  	<body>
  		<fieldset id = "editRegion">
			<legend id="cxq">发布信息</legend>
			<table id="detailCanvas" class="eTable6">
				<tr>	
					<th style="width:100px">发布主题</th>	
					<td colspan="3"><input style="width:350px" id="txtFBZT" name="发布主题"  onfocus=this.blur() readonly /></td>
				</tr>
				<tr>	
				    <th style="width:100px">发布日期</th>	
					<td><input type="hidden" id="txtXXID"><input style="width:120px" id="txtFBSJ" name="发布日期" onfocus=this.blur() readonly /></td>
					<th style="width:100px">有效日期</th>	
					<td><input style="width:120px" id="txtYXSJ" name="有效日期"  onfocus=this.blur() readonly /></td>
				</tr>
				<tr>
					<th style="width:100px">发布内容</th>	
					<td height="60" valign="middle" bgcolor="white" class="wo7" colspan="3"  >
							<textarea  id="txtFBNR"  style="width: 99%;height: 100%" onfocus=this.blur() readonly ></textarea>
					</td>
				</tr>
			</table>
		</fieldset>
		<fieldset id="selectRegion">
			<legend id="cxq">接收人列表</legend>
		</fieldset>
		<div id="gridCanvas">
			<table id="mmg" class="mmg"></table>
			<div id="pg" style="text-align: right;"></div>
		</div>
	</body>
</html>