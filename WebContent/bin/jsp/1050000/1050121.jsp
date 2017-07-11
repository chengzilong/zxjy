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
var obj = new Object();//参数对象
//初始化表格
$(document).ready(function(){
	/* 初始化传递参数Start */
	obj = window.parent.obj;
	optionFlag = obj.option;
	/* 初始化传递参数End */
   var cols = [
       { title:'课程名称', name:'KCMC' ,width:100, sortable:true, align:'center',lockDisplay: true },
       { title:'课程类型', name:'LXMC' ,width:80, sortable:true, align:'center',lockDisplay: true },
       { title:'上课时段', name:'SDMC' ,width:100, sortable:true, align:'center',lockDisplay: true  },
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
        ,url: '<%=basePath%>/Servlet1050120'
        ,method: 'post'
        ,params:{CMD : "<%=Servlet1050120.CMD_SELECT_COURSE%>"}
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
    	if(optionFlag == "AddXk"){
    		layer.confirm('是否选定此课程？', function() {
    			if(insertData(arrList[0])==true){
        			iframeLayerClose();
    			}
    		});
    	}else if(optionFlag == "UpdTk"){
			layer.confirm('是否调整为此课程？', function() {
				if(updateData(arrList[0])==true){
	    			iframeLayerClose();
		        }
    		});
    	}
    });
    //取消点击事件
    $('#btnCancel').on('click', function(){
    	iframeLayerClosenoRefresh();
    });
    /* 页面初始化加载数据 */
    loadGridByBean();
    /* 初始化下拉列表 */
    loadSearchSelect($("#selectKcmc"),"TYPE_KCMC","课程名称");
    loadSearchSelect($("#selectKclx"),"TYPE_KCLX","课程类型");
    loadSearchSelect($("#selectSksd"),"TYPE_SKSD","上课时段");
    loadSearchSelect($("#selectSkjs"),"TYPE_JSXM","授课教师");
	loadEditSelect($("#selectEditTjjs"),"TYPE_JSXM","推荐教师");
	/* 初始化数据 */
	if(optionFlag == "UpdTk"){
		$('#selectEditTjjs').val(obj.tjjsId);
		$('#txtEditBzxx').val(obj.bzxx);
	}
});
//查询bean
function makeBeanInSearch(strKCMC,strKCLX,strSKSD,strJS){
	this.KCFY_XXID = strKCMC;
	this.KCFY_LXID = strKCLX;
    this.KCFY_SDID = strSKSD;
    this.KCFY_JSID = strJS;
}
//查询数据
function loadGridByBean(){
	var beanIn = new makeBeanInSearch(
			$('#selectKcmc').val(),
			$('#selectKclx').val(),
			$('#selectSksd').val(),
			$('#selectSkjs').val()
	);
	//重新查询数据
	mmg.load({
    	beanLoad  :  JSON.stringify(beanIn)
	});
}
//操作bean
function makeBeanInEdit(strBMXX_BMID,strBMXX_XSID,strBMXX_XXID,strBMXX_SDID,strBMXX_XS,
		strBMXX_JSID,strBMXX_FY,strBMXX_LXID,strBMXX_KCFYID,strBMXX_TJJSID,strBMXX_BZXX,strBMXX_SSBC){
	this.BMXX_BMID = strBMXX_BMID;
	this.BMXX_XSID = strBMXX_XSID;
    this.BMXX_XXID = strBMXX_XXID;
    this.BMXX_SDID = strBMXX_SDID;
    this.BMXX_XS = strBMXX_XS;
    this.BMXX_JSID = strBMXX_JSID;
    this.BMXX_FY = strBMXX_FY;
    this.BMXX_LXID = strBMXX_LXID;
    this.BMXX_KCFYID = strBMXX_KCFYID;
    this.BMXX_TJJSID = strBMXX_TJJSID;
    this.BMXX_BZXX = strBMXX_BZXX;
    this.BMXX_SSBC = strBMXX_SSBC;
}
//新增数据
function insertData(dataBean){
	var blnRet = false;
	var beanIn = new makeBeanInEdit(
			"",
			obj.studentId,
			dataBean.KCFY_XXID,
			dataBean.KCFY_SDID,
			dataBean.KCFY_XS,
			dataBean.KCFY_JSID,
			dataBean.KCFY_FY,
			dataBean.KCFY_LXID,
			dataBean.KCFY_FYID,
			$('#selectEditTjjs').val(),
			$('#txtEditBzxx').val(),
			""
	);
	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/Servlet1050120",
		data: {
			CMD    : "<%=Servlet1050120.CMD_INSERT_XK%>",
		    BeanIn : JSON.stringify(beanIn)
		},
		complete :function(response){},
		success: function(response){
			var strResult = response[0];
			if (strResult == "SUCCESS") {
				layer.msg('恭喜：选课成功！', 1, 9);
				blnRet = true;
			} else if (strResult == "FAILURE") {
				layer.msg('对不起：选课失败！', 1, 8);
				blnRet = false;
			} else {
				layer.msg('提示：选课出错！', 1, 0);
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
			"",
			dataBean.KCFY_XXID,
			dataBean.KCFY_SDID,
			dataBean.KCFY_XS,
			dataBean.KCFY_JSID,
			dataBean.KCFY_FY,
			dataBean.KCFY_LXID,
			dataBean.KCFY_FYID,
			$('#selectEditTjjs').val(),
			$('#txtEditBzxx').val(),
			obj.bcId
	);
    $.ajax({
      async     : false,
      type      : "post",
      dataType  : "json",
      url: "<%=basePath%>/Servlet1050120",
      data: {
         CMD    : "<%=Servlet1050120.CMD_UPDATE_TK%>",
         BeanIn : JSON.stringify(beanIn)
      },
      complete :function(response){},
      success: function(response){
          var strResult = response[0];
          if (strResult == "SUCCESS") {
        	  layer.msg('恭喜：调课成功！', 1, 9);
             blnRet = true;
          } else if (strResult == "FAILURE") {
        	  layer.msg('对不起：调课失败！', 1, 8);
             blnRet = false;
          } else {
        	  layer.msg('提示：调课出错！', 1, 0);
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
				<th style="width:100px">授课教师</th>
				<td>
					<select id="selectSkjs" style="width:100px"> </select>
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
				<th style="width:100px">推荐教师</th>
				<td><select id="selectEditTjjs" style="width:100px"> </select></td>
				<th style="width:100px">备注信息</th>
				<td><input id="txtEditBzxx" name="备注信息" maxlength="200" style="width: 200px" /></td>
				<th  style="width:100px"><input type="button" value="保存" id="btnSave" name="btnSave" /><input type="button" value="取消" id="btnCancel" name="btnCancel" /> </th>
			</tr>
		</table>
	</fieldset>
</body>
</html>