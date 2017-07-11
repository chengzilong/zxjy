<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.xsjy.servlet.servlet9010000.Servlet9010130"%> 
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	    <title>角色权限</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">    
		<!--表格样式Start  -->
		<link rel="stylesheet" href="<%=basePath%>/bin/css/mmgrid/main.css" type="text/css">
		<link rel="stylesheet" href="<%=basePath%>/bin/css/mmgrid/mmGrid.css" type="text/css">
		<link rel="stylesheet" href="<%=basePath%>/bin/css/mmgrid/mmPaginator.css" type="text/css">
		<link rel="stylesheet" href="<%=basePath%>/bin/css/mmgrid/normalize.css" type="text/css">
		<link rel="stylesheet" href="<%=basePath%>/bin/css/mmgrid/tablesize.css" type="text/css">
		<!--表格样式End  -->
		<script type="text/javascript" src="<%=basePath%>/bin/js/jquery-1.9.1.js"></script>
		<script type="text/javascript" src="<%=basePath%>/bin/js/common.js"></script>
		<!--表格脚本Start  -->
		<script type="text/javascript" src="<%=basePath%>/bin/js/mmgrid/mmGrid.js"></script>
		<script type="text/javascript" src="<%=basePath%>/bin/js/mmgrid/mmPaginator.js"></script>
		<!--表格脚本End  -->
		<script type="text/javascript" src="<%=basePath%>/bin/js/json2.js"></script>
		<script type="text/javascript" src="<%=basePath%>/bin/js/My97DatePicker/WdatePicker.js"></script>
		<script src="<%=basePath%>/bin/js/laydate/laydate.js"></script>
		<script type="text/javascript" src="<%=basePath%>/bin/js/layer/layer.min.js"></script>
		<script type="text/javascript">
			var mmgRole;
			var mmgMenu;
			//初始化表格
			$(document).ready(function(){
				var colsRole = [
					{ title:'角色编号', name:'YHJS_JSID' ,width:50, sortable:true, align:'center',lockDisplay: true  },
					{ title:'角色名称', name:'YHJS_JSMC' ,width:100, sortable:true, align:'center',lockDisplay: true  },
					{ title:'角色类型', name:'JSLX' ,width:150, sortable:true, align:'center',lockDisplay: true },
					{ title:'角色描述', name:'YHJS_JSMS' ,width:150, sortable:true, align:'center',lockDisplay: true },
					{ title:'创建人', name:'YHJS_CJR' ,width:50, hidden: true,sortable:true, align:'center',lockDisplay: true },
					{ title:'创建时间', name:'YHJS_CJSJ' ,width:100, hidden: true,sortable:true, align:'center',lockDisplay: true },
					{ title:'修改人', name:'YHJS_GXR' ,width:50, hidden: true,sortable:true, align:'center',lockDisplay: true },
					{ title:'修改时间', name:'YHJS_GXSJ' ,width:100, hidden: true,sortable:true, align:'center',lockDisplay: true }
				];
				
				var colsMenu = [
					{ title:'菜单编号', name:'MENU_CDID' ,width:100, sortable:true, align:'center',lockDisplay: true },
					{ title:'菜单名称', name:'MENU_CDMC' ,width:150, sortable:true, align:'center',lockDisplay: true  },
					{ title:'菜单地址', name:'MENU_URL' ,width:150, sortable:true, align:'center',lockDisplay: true  },
					{ title:'菜单层级', name:'MENU_CDCJ' ,width:60, sortable:true, align:'center',lockDisplay: true  },
					{ title:'父节点', name:'MENU_FJDID' ,width:150, sortable:true, align:'center',lockDisplay: true  },
					{ title:'序号', name:'MENU_XH' ,width:60, sortable:true, align:'center',lockDisplay: true  },
					{ title:'菜单类型', name:'CDLX' ,width:60, sortable:true, align:'center',lockDisplay: true  },
					{ title:'关联状态', name:'MENU_SFGL' ,width:60, sortable:true, align:'center',lockDisplay: true  }
				];
				
				var intheightRole = document.documentElement.clientHeight - $('#selectRegion').height() - 400;
				if(intheightRole < 100){
					intheightRole = 80;
				}
				
				var intheightMenu = document.documentElement.clientHeight - intheightRole - 200;
				if(intheightMenu < 100){
					intheightMenu = 80;
				}
				
				mmgRole = $('.mmgRole').mmGrid({
					height: intheightRole
					,cols: colsRole
					,url: '<%=basePath%>/Servlet9010130'
					,method: 'post'
					,params:{CMD : "<%=Servlet9010130.CMD_ROLE_SELECT%>"}
					,remoteSort:true
					,sortName: 'YHJS_JSID'
					,sortStatus: 'ASC'
					,root: 'items'
					,multiSelect: false
					,checkCol: true
					,indexCol: true
					,indexColWidth: 35
					,fullWidthRows: true
					,autoLoad: false
					,plugins: [
						$('#pgRole').mmPaginator({
							limit:30
						})
					]
				});
				
				mmgMenu = $('.mmgMenu').mmGrid({
					height: intheightMenu
					,cols: colsMenu
					,url: '<%=basePath%>/Servlet9010130'
					,method: 'post'
					,params:{CMD : "<%=Servlet9010130.CMD_MENU_SELECT%>"}
					,remoteSort:true
					,sortName: 'MENU_CDID'
					,sortStatus: 'ASC'
					,root: 'items'
					,multiSelect: true
					,checkCol: true
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
				
				mmgRole.on('click', ':checkbox', function(){ //选择checkbox
					var arrList = mmgRole.selectedRows();
					if(arrList.length>0) {
						loadGridByBeanMenu(arrList[0].YHJS_JSID, arrList[0].YHJS_JSLX);
					} else {
						loadGridByBeanMenu("", "");
					}
				}).on('loadSuccess',function(e, data){
				}).on('rowSelected', function(e, item, rowIndex, colIndex){//选择数据行
					var arrList = mmgRole.selectedRows();
					if(arrList.length>0) {
						loadGridByBeanMenu(arrList[0].YHJS_JSID, arrList[0].YHJS_JSLX);
					} else {
						loadGridByBeanMenu("", "");
					}
				}).on('click', 'tr', function(e){ //点击行;
					if(mmgRole.rowsLength()<=0) return; //无数据,不进行操作
					var rowIndex = e.target.parentNode.rowIndex;
					if(typeof(rowIndex) == "undefined"){
						rowIndex = e.target.parentNode.parentNode.rowIndex;
					}
					var arrList = mmgRole.selectedRows();
					if(arrList.length>0) {
						loadGridByBeanMenu(arrList[0].YHJS_JSID, arrList[0].YHJS_JSLX);
					} else {
						loadGridByBeanMenu("", "");
					}
				});
				
				$('#btnSave').on('click', function(){
					var arrList = mmgMenu.selectedRows();
					if(mmgMenu.rowsLength()<=0) {
						layer.alert('请选择需要关联的菜单！', 0, '友情提示');
						return; 
					}
					if(arrList.length<=0) {
						layer.alert('请选择需要关联的菜单！', 0, '友情提示');
						return; 
					}
					if(arrList.length>0) {
				   		for(var i = 0; i < arrList.length; i++) {
							if (arrList[i].MENU_GLZT == 1) {
								layer.alert('提示：所选菜单中，存在已关联的菜单，无法再次关联！', 0, '友情提示');
								return; 
							}
						}
					}
		    		layer.confirm('请确认是否关联？', function() {
			    		if(insert()==true){
			  	            //重新查询数据
	// 		  	            loadGridByBeanRole();
	// 						mmgRole.deselect('all');
						}
					});
			    });
				
				$('#btnCancel').on('click', function(){
					var arrList = mmgMenu.selectedRows();
					if(mmgMenu.rowsLength()<=0) {
						layer.alert('请选择需要取消关联的菜单！', 0, '友情提示');
						return; 
					}
					if(arrList.length<=0) {
						layer.alert('请选择需要取消关联的菜单！', 0, '友情提示');
						return; 
					}
					if(arrList.length>0) {
				   		for(var i = 0; i < arrList.length; i++) {
							if (arrList[i].MENU_GLZT == 0) {
								layer.alert('提示：所选菜单中，存在未关联的菜单，无法取消关联！', 0, '友情提示');
								return; 
							}
						}
					}
					layer.confirm('请确认是否取消关联？', function() {
			    		if(cancel()==true){
			  	            //重新查询数据
	// 		  	            loadGridByBeanRole();
	// 						mmgRole.deselect('all');
						}
					});
			    });
				loadGridByBeanRole();
			});
			
			function loadGridByBeanRole(){
				//重新查询数据
				mmgRole.load({
				});
			}
			
			function loadGridByBeanMenu(strJsid, strJslx){
				//重新查询数据
				mmgMenu.load({
			    	JSID  :  strJsid,
			    	JSLX :   strJslx
				});
			}
			
			function insert() {
				var blnRet = false;
				var arrRoleList = mmgRole.selectedRows();
				var arrMenuList = mmgMenu.selectedRows();
				var strJSID = arrRoleList[0].YHJS_JSID;
				var strCDID = "";
				if(arrMenuList.length>0) {
					for(var i = 0; i < arrMenuList.length; i++) {
						strCDID += "'" + arrMenuList[i].MENU_CDID + "',";
					}
				}
				$.ajax({
					async     : false,
					type      : "post",
					dataType  : "json",
					url: "<%=basePath%>/Servlet9010130",
					data: {
						CMD     : "<%=Servlet9010130.CMD_RELATION%>",
						JSID      : strJSID,
						CDIDS   : strCDID
					},
					complete : function(response) {
					},
					success : function(response) {
						var CMD = response[0];
						if (CMD == "CMD_OK") {
							layer.msg('恭喜：角色关联菜单成功！', 1, 9);
							loadGridByBeanMenu(response[1]);
							blnRet = true;
						} else if(CMD == "CMD_ERROR"){
							layer.msg('对不起：角色关联菜单失败！', 1, 8);
							loadGridByBeanMenu(response[1]);
							blnRet = false;
						} else if (CMD == "CMD_EXCEPTION") {
							layer.msg('提示：角色关联菜单出错！', 1, 0);
							blnRet = false;
						}
					}
				});
				return blnRet;
			}
			
			function cancel() {
				var blnRet = false;
				var arrRoleList = mmgRole.selectedRows();
				var arrMenuList = mmgMenu.selectedRows();
				var strJSID = arrRoleList[0].YHJS_JSID;
				var strCDID = "";
				if(arrMenuList.length>0) {
					for(var i = 0; i < arrMenuList.length; i++) {
						strCDID += "'" + arrMenuList[i].MENU_CDID + "',";
					}
				}
				$.ajax({
					async     : false,
					type      : "post",
					dataType  : "json",
					url: "<%=basePath%>/Servlet9010130",
					data: {
						CMD     : "<%=Servlet9010130.CMD_CANCEL_RELATION%>",
						JSID      : strJSID,
						CDIDS   : strCDID
					},
					complete : function(response) {
					},
					success : function(response) {
						var CMD = response[0];
						if (CMD == "CMD_OK") {
							layer.msg('恭喜：取消关联菜单成功！', 1, 9);
							loadGridByBeanMenu(response[1]);
							blnRet = true;
						} else if(CMD == "CMD_ERROR"){
							layer.msg('对不起：取消关联菜单失败！', 1, 8);
							loadGridByBeanMenu(response[1]);
							blnRet = false;
						} else if (CMD == "CMD_EXCEPTION") {
							layer.msg('提示：取消关联菜单出错！', 1, 0);
							blnRet = false;
						}
					}
				});
				return blnRet;
			}
		</script>
	</head>
  	<body>
  		<fieldset id="selectRegion">
			<legend id="cxq">角色信息</legend>
		</fieldset>
		<div id="gridCanvas">
			<table id="mmgRole" class="mmgRole"></table>
			<div id="pgRole" style="text-align: right;"></div>
		</div>
		<fieldset id = "editRegion">
			<legend>操作</legend>
			<table id="detailCanvas" class="eTable6">
				<tr>
					<td>
							<input type="button" value="关联" id="btnSave" name="btnSave" />
							<input type="button" value="取消关联" id="btnCancel" name="btnCancel" />
					</td>
				</tr>
			</table>
		</fieldset>
		<fieldset id="selectRegion">
			<legend id="cxq">菜单信息</legend>
		</fieldset>
		<div id="gridCanvas">
			<table id="mmgMenu" class="mmgMenu"></table>
			<div id="pgMenu" style="text-align: right;"></div>
		</div>
	</body>
</html>