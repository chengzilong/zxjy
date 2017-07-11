<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.xsjy.servlet.servlet1040000.Servlet1040140"%> 
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
<title>学生分配-学生报名信息</title>
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
       { title:'学生姓名', name:'XSXM' ,width:80, sortable:true, align:'center',lockDisplay: true },
       { title:'学生电话', name:'XSDH' ,width:100, sortable:true, align:'center',lockDisplay: true },
       { title:'课程名称', name:'KCMC' ,width:100, sortable:true, align:'center',lockDisplay: true },
       { title:'课程类型', name:'KCLXMC' ,width:80, sortable:true, align:'center',lockDisplay: true },
       { title:'报名方式', name:'BMFS' ,width:150, sortable:true, align:'center',lockDisplay: true  },
       { title:'报名状态', name:'BMZT' ,width:100, sortable:true, align:'center',lockDisplay: true  },
       { title:'推荐教师', name:'TJJSXM' ,width:80, sortable:true, align:'center',lockDisplay: true  },
       { title:'备注信息', name:'BMXX_BZXX' ,width:200, sortable:true, align:'center',lockDisplay: true  },
       { title:'登记人', name:'BMXX_CJR' ,width:80, sortable:true, align:'center',lockDisplay: true  },
       { title:'登记时间', name:'BMXX_CJSJ' ,width:150, sortable:true, align:'center',lockDisplay: true  }
   ];

   intheight = document.documentElement.clientHeight -$('#editRegion').height()-$('#selectRegion').height()-80; 
   if(intheight<100){
	   intheight = 100;
   }

   mmg = $('.mmg').mmGrid({
         height: intheight
        ,cols: cols
        ,url: '<%=basePath%>/Servlet1040140'
        ,method: 'post'
        ,params:{CMD : "<%=Servlet1040140.CMD_SELECT_ENROLL%>"}
        ,remoteSort:true
        ,sortName: 'BMXX_BMID'
        ,sortStatus: 'ASC'
        ,root: 'items'
        ,multiSelect: true
        ,checkCol: true
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
   /* 查询课程费用 */
   $('#btnSearch').on('click', function(){
	   loadGridByBean();
   });
  //保存点击事件（班次关联学生）
    $('#btnSave').on('click', function(){
		var arrList = mmg.selectedRows();
		var ary = new Array();
		if(arrList.length<=0) {
			layer.alert('请选择报名学生！', 0, '友情提示');
			return; 
		}else{
			for(var i = 0; i < arrList.length; i++) {
				ary[i] = arrList[i].BMXX_XSID;		
			}
		}
		if(isRepeat(ary)){
			layer.alert('存在重复的报名信息,无法分配学生！', 0, '友情提示');
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
	var stuIds = "";
	var stuMcs = "";
	if(arrList.length>0) {
		for(var i = 0; i < arrList.length; i++) {
			stuIds += "'" + arrList[i].BMXX_XSID + "',";
			stuMcs += "" + arrList[i].XSXM + ",";
		}
	}
	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/Servlet1040140",
		data: {
			CMD		: "<%=Servlet1040140.CMD_CHK_EXIST%>",
			BCID			: obj.bcId,
			STUIDS		: stuIds,
			STUMCS      :stuMcs
		},
		complete : function(response) {
		},
		success : function(response) {
			var CMD = response[0];
			var REG  = response[1];
			if (CMD == "BCXS_EXIST") {
				layer.alert('存在已分配的学生！ 学生姓名：'+REG, 0, '友情提示');
				blnRet = false;
			} else {
				blnRet = true;
			}
		}
	});
	return blnRet;
}
//查询bean
function makeBeanIn(strXSXM,strXSDH,strBMXX_KCFYID,strBMXX_BMZT){
	this.XSXM = strXSXM;
	this.XSDH = strXSDH;
	this.BMXX_KCFYID = strBMXX_KCFYID;
	this.BMXX_BMZT = strBMXX_BMZT;
}
//查询数据
function loadGridByBean(){
	var beanIn = new makeBeanIn(
			$('#txtSelectXsxm').val(),
			$('#txtSelectXsdh').val(),
			obj.kcfyId,
			$('#selectBmzt').val()
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
	var dataIds = "";
	var stuIds = "";
	if(arrList.length>0) {
		for(var i = 0; i < arrList.length; i++) {
			dataIds += "'" + arrList[i].BMXX_BMID + "',";
			stuIds += "'" + arrList[i].BMXX_XSID + "',";
		}
	}
	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/Servlet1040140",
		data: {
			CMD		: "<%=Servlet1040140.CMD_RELATION%>",
			BCID			: obj.bcId,
			DATAIDS	: dataIds,
			STUIDS		: stuIds
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
				<th style="width:100px">学生姓名</th>
				<td><input id="txtSelectXsxm" name="学生姓名" maxlength="20" /></td>
				<th style="width:100px">学生电话</th>
				<td><input id="txtSelectXsdh" name="学生电话" maxlength="20" /></td>
				<th style="width:100px">报名状态</th>
				<td>
					<select id="selectBmzt" style="width: 100px">
						<option value="000">所有</option>
						<option value="1">未交费</option>
						<option value="2">已交费</option>
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
		<legend>操作</legend>
		<table id="detailCanvas" class="eTable6">
			<tr>
				<th  style="width:100px" colspan="3" align="right"><input type="button" value="保存" id="btnSave" name="btnSave" /><input type="button" value="取消" id="btnCancel" name="btnCancel" /> </th>
			</tr>
		</table>
	</fieldset>
</body>
</html>