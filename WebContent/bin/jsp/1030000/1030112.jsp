<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.xsjy.servlet.servlet1030000.Servlet1030120"%> 
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
			var mmgR;
			var obj;//参数对象
			//初始化表格
			$(document).ready(function(){
				/* 初始化传递参数Start */
				obj = window.parent.dataBean;//参数对象
				/* 初始化传递参数End */
				/* 初始化表单数据 */
				$('#txtXXID').val(obj.XXXX_XXID);
		        $('#txtFBSJ').val(obj.XXXX_FBSJ);
		        $('#txtYXSJ').val(obj.XXXX_YXSJ);
		        $('#txtFBZT').val(obj.XXXX_FBZT);
		        $('#txtFBNR').val(obj.XXXX_FBNR);
		        /* 初始化表格数据 */
				var colsRenyuan = [
					{ title:'接收人', name:'XXMX_JSR' ,width:100, sortable:true, align:'center',lockDisplay: true },
					{ title:'查看状态', name:'XXMX_CKZT' ,width:150, sortable:true, align:'center',lockDisplay: true  }
				];
				
				var intheightR = document.documentElement.clientHeight - $('#editRegion').height() - $('#selectRegion').height()  - 80;
				if(intheightR < 100){
					intheightR = 80;
				}
				
				mmgR = $('.mmgR').mmGrid({
					height: intheightR
					,cols: colsRenyuan
					,url: '<%=basePath%>/Servlet1030120'
					,method: 'post'
					,params:{CMD : "<%=Servlet1030120.CMD_SELECTMX%>"}
					,remoteSort:true
					,sortName: ''
					,sortStatus: 'ASC'
					,root: 'items'
					,multiSelect: true
					,checkCol: false
					,indexCol: true
					,indexColWidth: 35
					,fullWidthRows: true
					,autoLoad: false
					,plugins: [
						$('#pgMenu').mmPaginator({
							limit:20
						})
					]
				});
				
				loadGridByBeanR(obj.XXXX_XXID);
			});

			function loadGridByBeanR(strXXID){
				//重新查询数据
				mmgR.load({
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
			<table id="mmgR" class="mmgR"></table>
			<div id="pgMenu" style="text-align: right;"></div>
		</div>
	</body>
</html>