<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.xsjy.servlet.servlet3030000.Servlet3030120"%> 
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
<title>课程费用信息</title>
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
	optionFlag = obj.option;
	if (optionFlag == "Add") {
		$('#txtEditBcmc').focus();
	} else if (optionFlag == "Upd") {
		$('#txtEditBcmc').val(obj.bcmc);
		$('#txtEditKssj').val(obj.kssj);
		$('#txtEditJssj').val(obj.jssj);
		$('#txtEditSkdd').val(obj.skdd);
		$('#txtEditFyid').val(obj.fyid);
		
	}
	/* 初始化传递参数End */
   var cols = [
       { title:'课程名称', name:'KCMC' ,width:100, sortable:true, align:'center',lockDisplay: true },
       { title:'课程类型', name:'LXMC' ,width:80, sortable:true, align:'center',lockDisplay: true },
       { title:'上课时段', name:'SDMC' ,width:100, sortable:true, align:'center',lockDisplay: true  },
       { title:'授课区域', name:'SKQY' ,width:120, sortable:true, align:'center',lockDisplay: true  },
       { title:'学时', name:'KCFY_XS' ,width:100, sortable:true, align:'center',lockDisplay: true  },
       { title:'教师姓名', name:'JSXM' ,width:100, sortable:true, align:'center',lockDisplay: true  },
       { title:'费用', name:'KCFY_FY' ,width:150, sortable:true, align:'center',lockDisplay: true  }
   ];

   intheight = document.documentElement.clientHeight -$('#editRegion').height()-$('#selectRegion').height()-80; 
   if(intheight<100){
	   intheight = 100;
   }

   mmg = $('.mmg').mmGrid({
         height: intheight
        ,cols: cols
        ,url: '<%=basePath%>/Servlet3030120'
        ,method: 'post'
        ,params:{CMD : "<%=Servlet3030120.CMD_SELECT_COURSE%>"}
        ,remoteSort:true
        ,sortName: 'KCFY_FYID'
        ,sortStatus: 'ASC'
        ,root: 'items'
        ,multiSelect: false
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
    //定义表格事件
    mmg.on('loadSuccess', function(e, data) {
    	mmg.select(function(item, index) {
    		if (item.SXKC != "") {
    			return true;
    		}
    	});
	 });
   /* 查询课程费用 */
   $('#btnSearch').on('click', function(){
	   loadGridByBean();
   });
  //保存点击事件（新增/更新）
    $('#btnSave').on('click', function(){
    	var arrList = mmg.selectedRows();
 	   if(arrList.length<=0){
 		  layer.alert('请选择课程！', 0, '友情提示');
 	       return;
 	   }
    	if(optionFlag == "Add"){
    		if(funEditCheck()==false) return;
    		layer.confirm('是否新增班次？', function() {
    			if(insertData(arrList[0])==true){
    				iframeLayerClose();
    			}
    		});
    	}else if(optionFlag == "Upd"){
    		if(funEditCheck()==false) return;
    		layer.confirm('是否调整班次？', function() {
    			if(updateData(arrList[0])==true){
    				iframeLayerClose();
    	        }
    		});
    	}
    });
    //取消点击事件
    $('#btnCancel').on('click', function(){
    	window.close();
    });
    /* 页面初始化加载数据 */
    loadGridByBean();
    /* 初始化下拉列表 */
    loadSearchSelect($("#selectKcmc"),"TYPE_KCMC","课程名称");
    loadSearchSelect($("#selectKclx"),"TYPE_KCLX","课程类型");
    loadSearchSelect($("#selectSksd"),"TYPE_SKSD","上课时段");
    /* 日期控件设置Start */
    var start = {
    	    elem: '#txtEditKssj',
    	    format: 'YYYY-MM-DD',
    	    istime: false,
    	    istoday: false,
    	    choose: function(datas){
    	         end.min = datas; //开始日选好后，重置结束日的最小日期
    	         end.start = datas; //将结束日的初始值设定为开始日
    	    }
    	};
   	var end = {
   	    elem: '#txtEditJssj',
   	 	format: 'YYYY-MM-DD',
   	    istime: false,
   	    istoday: false,
   	    choose: function(datas){
   	        start.max = datas; //结束日选好后，重置开始日的最大日期
   	    }
   	};
   	laydate(start);
   	laydate(end);
   	laydate.skin("default");
   	/* 日期控件设置End */
});
//查询bean
function makeBeanInSearch(strKCMC,strKCLX,strSKSD,strFYID){
	this.KCFY_XXID = strKCMC;
	this.KCFY_LXID = strKCLX;
    this.KCFY_SDID = strSKSD;
    this.KCFY_FYID = strFYID;
}
//查询数据
function loadGridByBean(){
	var beanIn = new makeBeanInSearch(
			$('#selectKcmc').val(),
			$('#selectKclx').val(),
			$('#selectSksd').val(),
			$('#txtEditFyid').val()
	);
	//重新查询数据
	mmg.load({
    	beanLoad  :  JSON.stringify(beanIn)
	});
}
//编辑bean
function makeBeanInEdit(strBCXX_BCID, strBCXX_KCID, strBCXX_KCFYID, strBCXX_BCMC,
		strBCXX_KSSJ, strBCXX_JSSJ, strBCXX_SKDD, strBCXX_FY, strJSID){
	this.BCXX_BCID = strBCXX_BCID;
	this.BCXX_KCID = strBCXX_KCID;
	this.BCXX_KCFYID = strBCXX_KCFYID;
	this.BCXX_BCMC = strBCXX_BCMC;
	this.BCXX_KSSJ = strBCXX_KSSJ;
	this.BCXX_JSSJ = strBCXX_JSSJ;
	this.BCXX_SKDD = strBCXX_SKDD;
	this.BCXX_FY = strBCXX_FY;
	this.JSID = strJSID;
}
//新增数据
function insertData(dataBean){
	var blnRet = false;
	var beanIn = new makeBeanInEdit(
			"",
			dataBean.KCFY_XXID,
			dataBean.KCFY_FYID,
			$('#txtEditBcmc').val(),
			$('#txtEditKssj').val(),
			$('#txtEditJssj').val(),
			$('#txtEditSkdd').val(),
			dataBean.KCFY_FY,
			dataBean.KCFY_JSID
	);
	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/Servlet3030120",
		data: {
			CMD    : "<%=Servlet3030120.CMD_INSERT%>",
		    BeanIn : JSON.stringify(beanIn)
		},
		complete :function(response){},
		success: function(response){
			var strResult = response[0];
			if (strResult == "SUCCESS") {
				layer.msg('恭喜：创建班次成功！', 1, 9);
				blnRet = true;
			} else if (strResult == "FAILURE") {
				layer.msg('对不起：创建班次失败！', 1, 8);
				blnRet = false;
			} else {
				layer.msg('提示：创建班次出错！', 1, 0);
				blnRet = false;
	        }
		}
	});
	return blnRet;
}
//更新数据
function updateData(dataBean){
	var blnRet = false;
	var beanIn = new makeBeanInEdit(
			obj.dataId,
			dataBean.KCFY_XXID,
			dataBean.KCFY_FYID,
			$('#txtEditBcmc').val(),
			$('#txtEditKssj').val(),
			$('#txtEditJssj').val(),
			$('#txtEditSkdd').val(),
			dataBean.KCFY_FY,
			dataBean.KCFY_JSID
	);
    $.ajax({
      async     : false,
      type      : "post",
      dataType  : "json",
      url: "<%=basePath%>/Servlet3030120",
      data: {
         CMD    : "<%=Servlet3030120.CMD_UPDATE%>",
         BeanIn : JSON.stringify(beanIn)
      },
      complete :function(response){},
      success: function(response){
    	  var strResult = response[0];
			if (strResult == "SUCCESS") {
				layer.msg('恭喜：调整班次成功！', 1, 9);
				blnRet = true;
			} else if (strResult == "FAILURE") {
				layer.msg('对不起：调整班次失败！', 1, 8);
				blnRet = false;
			} else {
				layer.msg('提示：调整班次出错！', 1, 0);
				blnRet = false;
	        }
      }
   });
   return blnRet;
}
//验证编辑输入数据
function funEditCheck() {
	if ($('#txtEditBcmc').val() == "") {
		layer.alert('请输入班次名称！', 0, '友情提示', function() {
			$('#txtEditBcmc').focus();
			layer.close(layer.index);
		});
		return false;
	}
	if ($('#txtEditKssj').val() == "") {
		layer.alert('请选择上课时间！', 0, '友情提示', function() {
			$('#txtEditKssj').focus();
			layer.close(layer.index);
		});
		return false;
	}
	if ($('#txtEditJssj').val() == "") {
		layer.alert('请选择上课时间！', 0, '友情提示', function() {
			$('#txtEditJssj').focus();
			layer.close(layer.index);
		});
		return false;
	}
	if ($('#txtEditSkdd').val() == "") {
		layer.alert('请输入上课地点！', 0, '友情提示', function() {
			$('#txtEditSkdd').focus();
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
				<th style="width:100px">课程名称</th>
				<td>
					<select id="selectKcmc" style="width:100px"> </select>
				</td>
				<th style="width:100px">课程类型</th>
				<td>
					<select id="selectKclx" style="width:100px"> </select>
				</td>
				<th style="width:100px">上课时段</th>
				<td>
					<select id="selectSksd" style="width:100px"> </select>
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
		<legend>编辑</legend>
		<table id="detailCanvas" class="eTable6">
			<tr>
				<th style="width:100px">班次名称</th>
				<td colspan="2"><input type="hidden"  id="txtEditFyid" /><input id="txtEditBcmc" name="班次名称" maxlength="20" /></td>
				<th style="width:100px">上课地点</th>
				<td><input id="txtEditSkdd" name="上课地点" maxlength="200" style="width: 300px" /></td>
			</tr>
			<tr>
				<th style="width:100px">上课时间</th>
				<td><input id="txtEditKssj" name="开始时间"  style="font-size:12px;line-height:16px; height:18px;"  class="laydate-icon-default" maxlength="20" readonly="readonly" /></td>
				<th style="width:20px">-</th>
				<td><input id="txtEditJssj" name="结束时间"  style="font-size:12px;line-height:16px; height:18px;"  class="laydate-icon-default" maxlength="20" readonly="readonly" /></td>
				<th  style="width:100px" colspan="3" align="right"><input type="button" value="保存" id="btnSave" name="btnSave" /><input type="button" value="取消" id="btnCancel" name="btnCancel" /> </th>
			</tr>
		</table>
	</fieldset>
</body>
</html>