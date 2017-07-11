<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.xsjy.servlet.servlet1050000.Servlet1050120"%> 
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
<title>正式报名</title>
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
       { title:'学生姓名', name:'XSXM' ,width:80, sortable:true, align:'center',lockDisplay: true },
       { title:'课程名称', name:'KCMC' ,width:80, sortable:true, align:'center',lockDisplay: true },
       { title:'课程类型', name:'KCLXMC' ,width:100, sortable:true, align:'center',lockDisplay: true },
       { title:'上课时段', name:'SKSD' ,width:80, sortable:true, align:'center',lockDisplay: true },
       { title:'学时', name:'BMXX_XS' ,width:80, sortable:true, align:'center',lockDisplay: true },
       { title:'授课教师', name:'KCJSXM' ,width:80, sortable:true, align:'center',lockDisplay: true },
       { title:'班次名称', name:'BCMC' ,width:150, sortable:true, align:'center',lockDisplay: true },
       { title:'定价', name:'BMXX_FY' ,width:50, sortable:true, align:'center',lockDisplay: true },
       { title:'已交费用', name:'BMXX_YJFY' ,width:50, sortable:true, align:'center',lockDisplay: true },
       { title:'未交费用', name:'BMXX_WJFY' ,width:50, sortable:true, align:'center',lockDisplay: true },
       { title:'报名方式', name:'BMFS' ,width:150, sortable:true, align:'center',lockDisplay: true },
       { title:'报名状态', name:'BMZT' ,width:50, sortable:true, align:'center',lockDisplay: true },
       { title:'推荐教师', name:'TJJSXM' ,width:80, sortable:true, align:'center',lockDisplay: true },
       { title:'备注信息', name:'BMXX_BZXX' ,width:200, sortable:true, align:'center',lockDisplay: true },
       { title:'登记人', name:'BMXX_CJR' ,width:80,sortable:true, align:'center',lockDisplay: true },
       { title:'登记时间', name:'BMXX_CJSJ' ,width:150,sortable:true, align:'center',lockDisplay: true },
       { title:'修改人', name:'BMXX_GXR' ,width:80,sortable:true, align:'center',lockDisplay: true,hidden:true },
       { title:'修改时间', name:'BMXX_GXSJ' ,width:100,sortable:true, align:'center',lockDisplay: true,hidden:true }
   ];
             
    intheight = document.documentElement.clientHeight -$('#editRegion').height()-$('#selectRegion').height()-80; 
   if(intheight<100){
	   intheight = 100;
   }

   mmg = $('.mmg').mmGrid({
         height: intheight
        ,cols: cols
        ,url: '<%=basePath%>/Servlet1050120'
        ,method: 'post'
        ,params:{CMD : "<%=Servlet1050120.CMD_SELECT%>"}
        ,remoteSort:true
        ,sortName: 'BMXX_BMID'
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
   //查询点击事件
   $('#btnSearch').on('click', function(){
	   if (funEditCheck() == false) return;
	   getXsxm($('#txtSelectXsdh').val());
	   loadGridByBean();
   });
 //删除点击事件
   $('#btnDel').on('click', function(){
	   var arrList = mmg.selectedRows();
	   if(arrList.length<=0){
		   layer.alert('请选择要删除的报名信息！', 0, '友情提示');
 	       return;
	   }
	   layer.confirm('是否删除选中的信息？', function() {
		   if(deleteData(arrList[0])==true){
			   //重新查询数据
			   loadGridByBean();
			   mmg.deselect('all');
		   }
	   });
   });
});

function makeBeanIn(strXSDH, strBMXX_BMID, strBMXX_XSID, strBMXX_SSBC){
	this.XSDH = strXSDH;
    this.BMXX_BMID = strBMXX_BMID;
    this.BMXX_XSID = strBMXX_XSID;
    this.BMXX_SSBC = strBMXX_SSBC;
}
//加载数据
function loadGridByBean(){
	var beanIn = new makeBeanIn(
			$('#txtSelectXsdh').val(),
			"","",""
	);
	//重新查询数据
	mmg.load({
    	beanLoad  :  JSON.stringify(beanIn)
	});
}
//检查学生是否存在
function getXsxm(xsdh) {
	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/Servlet1050120",
		data: {
			CMD    : "<%=Servlet1050120.CMD_GET_XSXM%>",
			XSDH   : xsdh
		},
		complete : function(response) {},
		success : function(response) {
			var strResult = response[0];
			if (strResult == "SUCCESS") {
				var dataBean = response[1];
				$('#txtXsxm').html(dataBean.XSXX_XSXM);
				$('#txtXsid').val(dataBean.XSXX_XSID);
			} else if (strResult == "FAILURE") {
				layer.msg('提示：系统未找到此学生信息，请重新查询！', 1, 0);
				$('#txtXsxm').html("&nbsp;");
				$('#txtXsid').val("");
				blnRet = false;
			} else {
				layer.msg('提示：查找学生信息出错！', 1, 0);
	            blnRet = false;
			}
		}
	});
}
//选课报名
function btn_Add_Xk(){
	if ($('#txtXsid').val() == "") {
		layer.alert('请查询学生信息后再进行此操作！', 0, '友情提示');
		return false;
	} else {
		obj = new Object();
		obj.option = "AddXk";
		obj.studentId = $('#txtXsid').val();
		iframeLayerOpen('<%=basePath%>/bin/jsp/1050000/1050121.jsp');
	}
}
//选班报名
function btn_Add_Xb(){
	if ($('#txtXsid').val() == "") {
		layer.alert('请查询学生信息后再进行此操作！', 0, '友情提示');
		return false;
	} else {
		obj = new Object();
		obj.option = "AddXb";
		obj.studentId = $('#txtXsid').val();
		iframeLayerOpen('<%=basePath%>/bin/jsp/1050000/1050122.jsp');
	}
}
//报名调课
function btn_Upd_Tk(){
	var arrList = mmg.selectedRows();
	   if(arrList.length<=0){
		   layer.alert('请选择要调课的报名信息！', 0, '友情提示');
	       return;
	   }
	layer.confirm('是否调课？', function() {
		layer.close(layer.index);
		obj = new Object();
		obj.option = "UpdTk";
		obj.dataId = arrList[0].BMXX_BMID;
		obj.bcId = arrList[0].BMXX_SSBC;
		obj.tjjsId = arrList[0].BMXX_TJJSID;
		obj.bzxx = arrList[0].BMXX_BZXX;
		iframeLayerOpen('<%=basePath%>/bin/jsp/1050000/1050121.jsp');
	});
}
//报名调班
function btn_Upd_Tb(){
	var arrList = mmg.selectedRows();
	   if(arrList.length<=0){
		   layer.alert('请选择要调班的报名信息！', 0, '友情提示');
	       return;
	   }
	layer.confirm('是否调班？', function() {
		layer.close(layer.index);
		obj = new Object();
		obj.option = "UpdTb";
		obj.studentId = arrList[0].BMXX_XSID;
		obj.dataId = arrList[0].BMXX_BMID;
		obj.bcId = arrList[0].BMXX_SSBC;
		obj.tjjsId = arrList[0].BMXX_TJJSID;
		obj.bzxx = arrList[0].BMXX_BZXX;
		iframeLayerOpen('<%=basePath%>/bin/jsp/1050000/1050122.jsp');
	});
}
//删除数据
function deleteData(dataBean){
	var blnRet = false;
	var beanIn = new makeBeanIn(
			"",
			dataBean.BMXX_BMID,
			dataBean.BMXX_XSID,
			dataBean.BMXX_SSBC
	);
	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/Servlet1050120",
		data: {
			CMD    : "<%=Servlet1050120.CMD_DELETE%>",
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
//验证编辑输入数据
function funEditCheck() {
	if ($('#txtSelectXsdh').val() == "") {
		layer.alert('请输入学生电话！', 0, '友情提示', function() {
			$('#txtSelectXsdh').focus();
			layer.close(layer.index);
		});
		return false;
	}
	return true;
}
</script>
</head>
<body>
	<fieldset id = "selectRegion">
		<legend>查询条件</legend>
		<table>
			<tr>
				<th style="width:100px">学生电话</th>
				<td><input id="txtSelectXsdh" name="学生电话" maxlength="20" /></td>
				<th style="width:100px">学生姓名：</th>
				<td><label id="txtXsxm" style="background-color: #999;display: block;width: 100px">&nbsp;</label><input id="txtXsid" type="hidden" name="学生ID" maxlength="20" /></td>
				<th  style="width:100px"><input type="button" value="查询学生" id="btnSearch" /></th>
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
			<input type="button" value="选课报名" onclick="btn_Add_Xk()" />
			<input type="button" value="选班报名" onclick="btn_Add_Xb()" />
			<input type="button" value="调整课程" onclick="btn_Upd_Tk()" />
			<input type="button" value="调整班次" onclick="btn_Upd_Tb()" />
			<input type="button" value="删除报名" id="btnDel" name="btnDel" /> 
		</div>
	</fieldset>
</body>
</html>