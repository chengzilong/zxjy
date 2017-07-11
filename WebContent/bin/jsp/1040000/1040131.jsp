<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.xsjy.servlet.servlet1040000.Servlet1040130"%> 
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
<title>教师分配-教师基础信息</title>
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
var optionFlag;//操作标识
var obj;//参数对象
//初始化表格
$(document).ready(function(){
	/* 初始化传递参数Start */
	obj = window.parent.obj;
	/* 初始化传递参数End */
   var cols = [
       { title:'教师姓名', name:'JSXX_JSXM' ,width:80, sortable:true, align:'center',lockDisplay: true },
       { title:'教师性别', name:'JSXB' ,width:50, sortable:true, align:'center',lockDisplay: true },
       { title:'是否认证', name:'SFRZ' ,width:50, sortable:true, align:'center',lockDisplay: true },
       { title:'是否有课', name:'SFYK' ,width:50, sortable:true, align:'center',lockDisplay: true },
       { title:'授课区域', name:'SKQY' ,width:170, sortable:true, align:'center',lockDisplay: true },
       { title:'授课科目', name:'SKKM' ,width:170, sortable:true, align:'center',lockDisplay: true },
       { title:'出生日期', name:'JSXX_CSRQ' ,width:80, sortable:true, align:'center',lockDisplay: true },
       { title:'联系方式', name:'JSXX_LXFS' ,width:100, sortable:true, align:'center',lockDisplay: true  },
       { title:'身份证号', name:'JSXX_SFZH' ,width:150, sortable:true, align:'center',lockDisplay: true  },
       { title:'住址', name:'JSXX_ZZ' ,width:200, sortable:true, align:'center',lockDisplay: true  },
       { title:'毕业年份', name:'JSXX_BYNF' ,width:80, sortable:true, align:'center',lockDisplay: true  },
       { title:'毕业学校', name:'JSXX_BYXX' ,width:100, sortable:true, align:'center',lockDisplay: true  },
       { title:'学历', name:'JSXX_XL' ,width:50, sortable:true, align:'center',lockDisplay: true  },
       { title:'个人简介', name:'JSXX_GRJJ' ,width:200, sortable:true, align:'center',lockDisplay: true  },
       { title:'登记人', name:'JSXX_CJR' ,width:80, sortable:true, align:'center',lockDisplay: true  },
       { title:'登记时间', name:'JSXX_CJSJ' ,width:150, sortable:true, align:'center',lockDisplay: true  }
   ];

   intheight = document.documentElement.clientHeight -$('#editRegion').height()-$('#selectRegion').height()-80; 
   if(intheight<100){
	   intheight = 100;
   }

   mmg = $('.mmg').mmGrid({
         height: intheight
        ,cols: cols
        ,url: '<%=basePath%>/Servlet1040130'
        ,method: 'post'
        ,params:{CMD : "<%=Servlet1040130.CMD_SELECT_RELATION%>"}
        ,remoteSort:true
        ,sortName: 'JSXX_JSID'
        ,sortStatus: 'ASC'
        ,root: 'items'
        ,multiSelect: true
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
   /* 查询课程费用 */
   $('#btnSearch').on('click', function(){
	   loadGridByBean();
   });
  //保存点击事件（班次关联教师）
    $('#btnSave').on('click', function(){
		var arrList = mmg.selectedRows();
		var ary = new Array();
		if(arrList.length<=0) {
			layer.alert('请选择授课教师！', 0, '友情提示');
			return; 
		}else{
			for(var i = 0; i < arrList.length; i++) {
				ary[i] = arrList[i].JSXX_JSID;		
			}
		}
		if(isRepeat(ary)){
			layer.alert('存在重复的授课教师,无法分配！', 0, '友情提示');
			return;
		}
		if(!checkExist()){
			return;
		}
		layer.confirm('请确认是否保存？', function() {
			if(relationData()==true){
				//重新查询数据
	   			iframeLayerClose();
			}
		});
    });
    //取消点击事件
    $('#btnCancel').on('click', function(){
    	iframeLayerClosenoRefresh();
    });
    /* 页面初始化加载数据 */
    loadGridByBean();
});
//验证数组是否重复
function isRepeat(arr){
     var hash = {};
     for(var i in arr) {
         if(hash[arr[i]])
             return true;
         hash[arr[i]] = true;
     }
     return false;
}
//验证重复数据
function checkExist() {
	var blnRet = true;
	var arrList = mmg.selectedRows();
	var teaIds = "";
	var teaMcs = "";
	if(arrList.length>0) {
		for(var i = 0; i < arrList.length; i++) {
			teaIds += "'" + arrList[i].JSXX_JSID + "',";
			teaMcs += "" + arrList[i].JSXX_JSXM + ",";
		}
	}
	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/Servlet1040130",
		data: {
			CMD		: "<%=Servlet1040130.CMD_CHK_EXIST%>",
			BCID			: obj.bcId,
			TEAIDS		: teaIds,
			TEAMCS      :teaMcs
		},
		complete : function(response) {
		},
		success : function(response) {
			var CMD = response[0];
			var REG  = response[1];
			if (CMD == "BCJS_EXIST") {
				layer.alert('存在已分配的教师！ 教师姓名：'+REG, 0, '友情提示');
				blnRet = false;
			} else {
				blnRet = true;
			}
		}
	});
	return blnRet;
}
//查询bean
function makeBeanIn(strJSXX_JSXM,strJSXX_JSBM){
	this.JSXX_JSXM = strJSXX_JSXM;
	this.JSXX_JSBM = strJSXX_JSBM;
}
//查询数据
function loadGridByBean(){
	var beanIn = new makeBeanIn(
			$('#txtSelectJsxm').val(),
			$('#txtSelectJsdh').val()
	);
	//重新查询数据
	mmg.load({
    	beanLoad  :  JSON.stringify(beanIn)
	});
}
//新增数据
function relationData() {
	var blnRet = false;
	var arrList = mmg.selectedRows();
	var teaIds = "";
	if(arrList.length>0) {
		for(var i = 0; i < arrList.length; i++) {
			teaIds += "'" + arrList[i].JSXX_JSID + "',";
		}
	}
	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/Servlet1040130",
		data: {
			CMD		: "<%=Servlet1040130.CMD_RELATION%>",
			BCID			: obj.bcId,
			TEAIDS		: teaIds
		},
		complete : function(response) {
		},
		success : function(response) {
			var CMD = response[0];
			if (CMD == "CMD_OK") {
				layer.msg('恭喜：保存成功！', 1, 9);
				blnRet = true;
			} else if(CMD == "CMD_ERROR"){
				layer.msg('对不起：保存失败！', 1, 8);
				blnRet = false;
			} else if (CMD == "CMD_EXCEPTION") {
				layer.msg('提示：保存出错！', 1, 0);
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
				<th style="width:100px">教师姓名</th>
				<td><input id="txtSelectJsxm" name="教师姓名" maxlength="20" /></td>
				<th style="width:100px">教师电话</th>
				<td><input id="txtSelectJsdh" name="教师电话" maxlength="20" /></td>
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
		<table id="detailCanvas" class="eTable6">
			<tr>
				<th  style="width:100px" colspan="3" align="right"><input type="button" value="保存" id="btnSave" name="btnSave" /><input type="button" value="取消" id="btnCancel" name="btnCancel" /> </th>
			</tr>
		</table>
	</fieldset>
</body>
</html>