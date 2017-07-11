<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.xsjy.servlet.servlet3030000.Servlet3030140"%> 
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	    <title>学生分配</title>
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
		<script type="text/javascript" src="<%=basePath%>/bin/js/json2.js"></script>
		<script type="text/javascript" src="<%=basePath%>/bin/js/common.js"></script>
		<!--表格脚本Start  -->
		<script type="text/javascript" src="<%=basePath%>/bin/js/mmgrid/mmGrid.js"></script>
		<script type="text/javascript" src="<%=basePath%>/bin/js/mmgrid/mmPaginator.js"></script>
		<!--表格脚本End  -->
		<script type="text/javascript" src="<%=basePath%>/bin/js/layer/layer.min.js"></script>
		<script type="text/javascript">
			var mmgClass;//班次表格对象
			var mmgStu;//学生表格对象
			$(document).ready(function(){
				/* 初始化表格Start */
				//班次列明细
				var colsClass = [
				   { title:'班次名称', name:'BCXX_BCMC' ,width:100, sortable:true, align:'center',lockDisplay: true },
			       { title:'课程名称', name:'KCMC' ,width:100, sortable:true, align:'center',lockDisplay: true },
			       { title:'开始时间', name:'BCXX_KSSJ' ,width:100, sortable:true, align:'center',lockDisplay: true },
			       { title:'结束时间', name:'BCXX_JSSJ' ,width:100, sortable:true, align:'center',lockDisplay: true },
			       { title:'上课地点', name:'BCXX_SKDD' ,width:200, sortable:true, align:'center',lockDisplay: true },
			       { title:'班次状态', name:'BCZT' ,width:80, sortable:true, align:'center',lockDisplay: true },
			       { title:'课程类型', name:'KCLXMC' ,width:80, sortable:true, align:'center',lockDisplay: true },
			       { title:'上课时段', name:'SKSDMC' ,width:50, sortable:true, align:'center',lockDisplay: true },
			       { title:'学时', name:'XS' ,width:50, sortable:true, align:'center',lockDisplay: true },
			       { title:'授课教师', name:'SKJSXM' ,width:80, sortable:true, align:'center',lockDisplay: true },
			       { title:'费用', name:'BCXX_FY' ,width:50, sortable:true, align:'center',lockDisplay: true }
				];
				//学生列明细
				var colsStu = [
					{ title:'学生编码', name:'XSXX_XSBM' ,width:100, sortable:true, align:'center',lockDisplay: true },
					{ title:'学生姓名', name:'XSXX_XSXM' ,width:80, sortable:true, align:'center',lockDisplay: true  },
					{ title:'联系方式', name:'XSXX_LXFS' ,width:100, sortable:true, align:'center',lockDisplay: true  },
					{ title:'住址', name:'XSXX_ZZ' ,width:300, sortable:true, align:'center',lockDisplay: true  },
					{ title:'阶段年级', name:'JDNJ' ,width:100, sortable:true, align:'center',lockDisplay: true  }
				];
				//设置班次列表宽度
				var intheightClass = document.documentElement.clientHeight - $('#selectRegion').height() - 400;
				if(intheightClass < 100){
					intheightClass = 80;
				}
				//设置学生列表宽度
				var intheightStu = document.documentElement.clientHeight - intheightClass - 250;
				if(intheightStu < 100){
					intheightStu = 80;
				}
				//获取班次列表对象
				mmgClass = $('.mmgClass').mmGrid({
					height: intheightClass
					,cols: colsClass
					,url: '<%=basePath%>/Servlet3030140'
					,method: 'post'
					,params:{CMD : "<%=Servlet3030140.CMD_SELECT_CLASS%>"}
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
					,plugins: [
						$('#pgClass').mmPaginator({
							limit:30
						})
					]
				});
				//获取学生列表对象
				mmgStu = $('.mmgStu').mmGrid({
					height: intheightStu
					,cols: colsStu
					,url: '<%=basePath%>/Servlet3030140'
					,method: 'post'
					,params:{CMD : "<%=Servlet3030140.CMD_SELECT_STUDENT%>"}
					,remoteSort:true
					,sortName: 'XSXX_XSID'
					,sortStatus: 'ASC'
					,root: 'items'
					,multiSelect: true
					,checkCol: true
					,indexCol: true
					,indexColWidth: 35
					,fullWidthRows: true
					,autoLoad: false
					,plugins: [
						$('#pgStu').mmPaginator({
							limit:20
						})
					]
				});
				/* 初始化表格End */
				/* 班次列表点击事件初始化Start */
				mmgClass.on('click', ':checkbox', function(){ //选择checkbox
					loadGridByBean();
				}).on('loadSuccess',function(e, data){
				}).on('rowSelected', function(e, item, rowIndex, colIndex){//选择数据行
					loadGridByBean();
				}).on('click', 'tr', function(e){ //点击行;
					if(mmgClass.rowsLength()<=0) return; //无数据,不进行操作
					var rowIndex = e.target.parentNode.rowIndex;
					if(typeof(rowIndex) == "undefined"){
						rowIndex = e.target.parentNode.parentNode.rowIndex;
					}
					loadGridByBean();
				});
				/* 班次列表点击事件初始化End */
				/* 页面初始化加载数据 */
				loadGridByBeanClass();
				/* 下拉列表初始化加载 */
			    loadSearchSelect($("#selectKcmc"),"TYPE_KCMC","课程名称");
				//取消分班
				$('#btnCancel').on('click', function(){
					var arrList = mmgStu.selectedRows();
					if(mmgStu.rowsLength()<=0) {
						layer.alert('请选择需要取消分班的学生！', 0, '友情提示');
						return; 
					}
					if(arrList.length<=0) {
						layer.alert('请选择需要取消分班的学生！', 0, '友情提示');
						return; 
					}
					layer.confirm('请确认是否取消分班？', function() {
						if(cancelRelation()==true){
			  	            //重新查询学生数据
			  	            loadGridByBean();
						}
					});
			    });
			});
			//查询条件Bean
			function makeBeanIn(strBCXX_BCID, strBCXX_BCMC, strBCXX_KCID, strBCXX_BCZT, strBCID){
				this.BCXX_BCID = strBCXX_BCID;
				this.BCXX_BCMC = strBCXX_BCMC;
			    this.BCXX_KCID = strBCXX_KCID;
			    this.BCXX_BCZT = strBCXX_BCZT;
			    this.BCID = strBCID;
			}
			//加载班次数据
			function loadGridByBeanClass(){
				var beanIn = new makeBeanIn(
						"",
						$('#txtSelectBcmc').val(),
						$('#selectKcmc').val(),
						$('#selectBczt').val()
				);
				//重新查询数据
				mmgClass.load({
			    	beanLoad  :  JSON.stringify(beanIn)
				});
			}
			//加载学生数据
			function loadGridByBean(){
				var arrList = mmgClass.selectedRows();
				var bcId = "";
				if (arrList.length > 0) {
					bcId = arrList[0].BCXX_BCID;
				}
				var beanIn = new makeBeanIn(
						"","","","",
						bcId
				);
				//重新查询数据
				mmgStu.load({
					beanLoad  :  JSON.stringify(beanIn)
				});
			}
			//分班操作
			function btn_Relation() {
				var arrList = mmgClass.selectedRows();
				if (arrList.length<=0) {
					layer.alert('请选择班次！', 0, '友情提示');
					return; 
				}
				obj = new Object();
				obj.bcId = arrList[0].BCXX_BCID;
				obj.kcfyId = arrList[0].BCXX_KCFYID;
				iframeLayerOpen('<%=basePath%>/bin/jsp/3030000/3030141.jsp');
			}
			//取消分班操作
			function cancelRelation() {
				var blnRet = false;
				var arrClassList = mmgClass.selectedRows();
				var arrStuList = mmgStu.selectedRows();
				var bcId = arrClassList[0].BCXX_BCID;
				var stuIds = "";
				var bcxsids = "";
				if(arrStuList.length>0) {
					for(var i = 0; i < arrStuList.length; i++) {
						stuIds += "'" + arrStuList[i].XSXX_XSID + "',";//学生ID
						bcxsids += "'" + arrStuList[i].BCXSID + "',";//班次学生ID--后加的
					}
				}
				$.ajax({
					async     : false,
					type      : "post",
					dataType  : "json",
					url: "<%=basePath%>/Servlet3030140",
					data: {
						CMD		: "<%=Servlet3030140.CMD_CANCEL_RELATION%>",
						BCID			: bcId,
						STUIDS		: stuIds,
						BCXSIDS      :bcxsids
					},
					complete : function(response) {
					},
					success : function(response) {
						var CMD = response[0];
						if (CMD == "CMD_OK") {
							layer.msg('恭喜：取消分班学生成功！', 1, 9);
							blnRet = true;
						} else if(CMD == "CMD_ERROR"){
							layer.msg('对不起：取消分班学生失败！', 1, 8);
							blnRet = false;
						} else if (CMD == "CMD_EXCEPTION") {
							layer.msg('提示：取消分班学生出错！', 1, 0);
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
			<legend id="cxq">班次信息</legend>
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
					<th  style="width:100px"><input type="button" value="查询" id="btnSearch" /></th>
				</tr>
			</table>
		</fieldset>
		<div id="gridCanvas">
			<table id="mmgClass" class="mmgClass"></table>
			<div id="pgClass" style="text-align: right;"></div>
		</div>
		<fieldset id = "editRegion">
			<legend>操作</legend>
			<table id="detailCanvas" class="eTable6">
				<tr>
					<td>
							<input type="button" value="学生分配" onclick="btn_Relation()" />
							<input type="button" value="取消分配" id="btnCancel" name="btnCancel" />
					</td>
				</tr>
			</table>
		</fieldset>
		<fieldset id="selectRegion">
			<legend id="cxq">学生信息</legend>
		</fieldset>
		<div id="gridCanvas">
			<table id="mmgStu" class="mmgStu"></table>
			<div id="pgStu" style="text-align: right;"></div>
		</div>
	</body>
</html>