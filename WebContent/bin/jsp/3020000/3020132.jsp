<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.xsjy.servlet.servlet3020000.Servlet3020130"%>
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
<title>发布信息</title>
<script type="text/javascript" src="<%=basePath%>/bin/js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="<%=basePath%>/bin/js/common.js"></script>
<script type="text/javascript" src="<%=basePath%>/bin/js/json2.js"></script>
<!--表格脚本Start  -->
<script type="text/javascript" src="<%=basePath%>/bin/js/mmgrid/mmGrid.js"></script>
<script type="text/javascript" src="<%=basePath%>/bin/js/mmgrid/mmPaginator.js"></script>
<!--表格脚本End  -->
<script type="text/javascript" src="<%=basePath%>/bin/js/laydate/laydate.js"></script>
<script type="text/javascript" src="<%=basePath%>/bin/js/layer/layer.min.js"></script>
<script type="text/javascript">
var mmg;
var intheight;
//设置默认当天时间
var myDate = new Date();   //获取系统日期
var year = "";
var month = "";
var day = "";
var timeStr = "";
year = myDate.getFullYear();    //获取完整的年份(4位,1970-????)
month = myDate.getMonth()+1;       //获取当前月份(0-11,0代表1月)
if(month<10)
	{
	month = "0"+month;
	}
day = myDate.getDate();        //获取当前日(1-31)
if(day < 10) {
	day = "0"+day;
}
timeStr = year+"-"+month+"-"+day;


var obj;//参数对象
//初始化表格
$(document).ready(function(){
	/* 初始化传递参数 */
	obj = window.parent.obj;
	/* 初始化表格Start */
   var cols = [
       { title:'发布主题', name:'XXXX_FBZT' ,width:150, sortable:true, align:'center',lockDisplay: true },
       { title:'发布内容', name:'XXXX_FBNR' ,width:350, sortable:true, align:'center',lockDisplay: true  },
       { title:'发布时间', name:'XXXX_FBSJ' ,width:80, sortable:true, align:'center',lockDisplay: true  },
       { title:'有效时间', name:'XXXX_YXSJ' ,width:80, sortable:true, align:'center',lockDisplay: true  },
       { title:'查看状态', name:'CKZT' ,width:80, sortable:true, align:'center',lockDisplay: true  }
   ];
      //表格高度
    intheight = document.documentElement.clientHeight -$('#editRegion').height()-$('#infoRegion').height()-80;
   if(intheight<100){
	   intheight = 100;
   }
	//表格属性定义
   mmg = $('.mmg').mmGrid({
         height: intheight
        ,cols: cols
        ,url: '<%=basePath%>/Servlet3020130'
        ,method: 'post'
        ,params:{CMD : "<%=Servlet3020130.CMD_SEND_SELECT%>"}
        ,remoteSort:true
        ,sortName: 'XXXX_XXID'
        ,sortStatus: 'ASC'
        ,root: 'items'
        ,multiSelect: false
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
   //行选中事件
   mmg.on('rowSelected', function(e, item, rowIndex, colIndex){//选择数据行
	   var arrList = mmg.selectedRows();
	   if(arrList.length>0){
         $('#txtEditFbzt').val(arrList[0].XXXX_FBZT);
         $('#txtEditFbrq').val(arrList[0].XXXX_FBSJ);
         $('#txtEditYxrq').val(arrList[0].XXXX_YXSJ);
         $('#txtEditFbnr').val(arrList[0].XXXX_FBNR);
         setButtonStatus("4");
	   } else {
		   $('#txtEditFbzt').val("");
		   $('#txtEditFbrq').val("");
		   $('#txtEditYxrq').val("");
		   $('#txtEditFbnr').val("");
		   setButtonStatus(2);//未选中行,则只有新增按钮可用
	   }
   }).on('click', ':checkbox', function(){ //选择checkbox
	   if(this.checked == true){
		   var arrList = mmg.selectedRows();
		   if(arrList.length>0){
	         $('#txtEditFbzt').val(arrList[0].XXXX_FBZT);
	         $('#txtEditFbrq').val(arrList[0].XXXX_FBSJ);
	         $('#txtEditYxrq').val(arrList[0].XXXX_YXSJ);
	         $('#txtEditFbnr').val(arrList[0].XXXX_FBNR);
	         setButtonStatus("4");
		   }
	   }else{
		   $('#txtEditFbzt').val("");
		   $('#txtEditFbrq').val("");
		   $('#txtEditYxrq').val("");
		   $('#txtEditFbnr').val("");
		   setButtonStatus(2);//未选中行,则只有新增按钮可用
	   }
   }).on('loadSuccess',function(e, data){
       //设置按钮状态
	   setButtonStatus(2);
   }).on('click', 'tr', function(e){ //点击行;
	   if(mmg.rowsLength()<=0) return; //无数据,不进行操作
       var rowIndex = e.target.parentNode.rowIndex;
       if(typeof(rowIndex) == "undefined"){
    	   rowIndex = e.target.parentNode.parentNode.rowIndex;
       }
       var arrList = mmg.selectedRows();
	   if(arrList.length>0){
         $('#txtEditFbzt').val(arrList[0].XXXX_FBZT);
         $('#txtEditFbrq').val(arrList[0].XXXX_FBSJ);
         $('#txtEditYxrq').val(arrList[0].XXXX_YXSJ);
         $('#txtEditFbnr').val(arrList[0].XXXX_FBNR);
         setButtonStatus("4");
	   } else {
		   $('#txtEditFbzt').val("");
		   $('#txtEditFbrq').val("");
		   $('#txtEditYxrq').val("");
		   $('#txtEditFbnr').val("");
		   setButtonStatus(2);//未选中行,则只有新增按钮可用
	   }
    });
   /* 初始化表格End */
   //查询点击事件
   $('#btnSearch').on('click', function(){
	   loadGridByBean();
   });
   //取消点击事件
    $('#btnCancel').on('click', function(){
    	setButtonStatus(2);
    	mmg.deselect('all');
    });
    //保存点击事件（新增/更新）
    $('#btnSave').on('click', function(){
    	if(optionFlag == "Add"){
    		if(funEditCheck()==false) return;
    		layer.confirm('是否新增数据？', function() {
    			if(insertData()==true){
      	            //重新查询数据
      	            loadGridByBean();
      	            setButtonStatus("2");
      	            mmg.deselect('all');
    			}
    		});
    	}else if(optionFlag == "Upd"){
    		var arrList = mmg.selectedRows();
    		if(arrList.length<=0){
    			layer.alert('请选择要修改的数据行！', 0, '友情提示');
    			return;
    		}
    		if(funEditCheck()==false) return;
    		layer.confirm('是否修改选中数据？', function() {
    			if(updateData(arrList[0].XXXX_XXID, arrList[0].XXMX_MXID)==true) {
	   	             //重新查询数据
	   	             loadGridByBean();
	   	             setButtonStatus("2");
	   	             mmg.deselect('all');
	   	        }
    		});
    	}
    });
    //删除点击事件
    $('#btnDel').on('click', function(){
 	   var arrList = mmg.selectedRows();
 	   if(arrList.length<=0){
 		  layer.alert('请选择要删除的数据行！', 0, '友情提示');
  	       return;
 	   } else {
 		   if (arrList[0].XXMX_CKZT == 1) {
 			  layer.alert('此条信息已经被查看，无法删除！', 0, '友情提示');
 	  	       return;
 		   }
 	   }
 	  layer.confirm('是否删除选中数据？', function() {
 		 if(deleteData(arrList[0].XXXX_XXID, arrList[0].XXMX_MXID)==true){
 	 		   //重新查询数据
 	 		   loadGridByBean();
 	 		   setButtonStatus("2");
 	 		   mmg.deselect('all');
 	 	   }
 	  });
    });

    loadGridByBean();
});

var optionFlag = "";

function btn_Add(){
	setButtonStatus("31");
	optionFlag = "Add";
}
function btn_Upd(){
   setButtonStatus("32");
   optionFlag = "Upd";
}
//定义bean
function makeBeanIn(strXXXX_XXID,strXXXX_FBZT,strXXXX_FBSJ,strXXXX_YXSJ,strXXXX_FBNR,
		strXXMX_MXID,strXXMX_JSR,strXXMX_JSRID,strXXMX_LXFS){
	this.XXXX_XXID = strXXXX_XXID;
    this.XXXX_FBZT = strXXXX_FBZT;
    this.XXXX_FBSJ = strXXXX_FBSJ;
    this.XXXX_YXSJ = strXXXX_YXSJ;
    this.XXXX_FBNR = strXXXX_FBNR;
    this.XXMX_MXID = strXXMX_MXID;
    this.XXMX_JSR = strXXMX_JSR;
    this.XXMX_JSRID = strXXMX_JSRID;
    this.XXMX_LXFS = strXXMX_LXFS;
}
//加载数据
function loadGridByBean(){
	var beanIn = new makeBeanIn(
			"","","","","","","","",""
	);
	//重新查询数据
	mmg.load({
    	beanLoad  :  JSON.stringify(beanIn)
	});
}
//新增数据
function insertData(){
	var blnRet = false;
	var beanIn = new makeBeanIn(
			"",
			$('#txtEditFbzt').val(),
			$('#txtEditFbrq').val(),
			$('#txtEditYxrq').val(),
			$('#txtEditFbnr').val(),
			"",
            obj.stuXM,
			obj.stuID,
            obj.stuDH
	);
	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/Servlet3020130",
		data: {
			CMD    : "<%=Servlet3020130.CMD_SEND_INSERT%>",
		    BeanIn : JSON.stringify(beanIn)
		},
		complete :function(response){},
		success: function(response){
			var strResult = response[0];
			if (strResult == "SUCCESS") {
				layer.msg('恭喜：新增数据成功！', 1, 9);
				blnRet = true;
			} else if (strResult == "FAILURE") {
				layer.msg('对不起：新增数据失败！', 1, 8);
				blnRet = false;
			} else {
				layer.msg('提示：新增数据出错！', 1, 0);
				blnRet = false;
	        }
		}
	});
	return blnRet;
}
//更新数据
function updateData(xxid,mxid){
	var blnRet = false;
	var beanIn = new makeBeanIn(
			xxid,
			$('#txtEditFbzt').val(),
			$('#txtEditFbrq').val(),
			$('#txtEditYxrq').val(),
			$('#txtEditFbnr').val(),
			"","","",""
	);
    $.ajax({
      async     : false,
      type      : "post",
      dataType  : "json",
      url: "<%=basePath%>/Servlet3020130",
      data: {
         CMD    : "<%=Servlet3020130.CMD_SEND_UPDATE%>",
         BeanIn : JSON.stringify(beanIn)
      },
      complete :function(response){},
      success: function(response){
    	  var strResult = response[0];
			if (strResult == "SUCCESS") {
				layer.msg('恭喜：修改数据成功！', 1, 9);
				blnRet = true;
			} else if (strResult == "FAILURE") {
				layer.msg('对不起：修改数据失败！', 1, 8);
				blnRet = false;
			} else {
				layer.msg('提示：修改数据出错！', 1, 0);
				blnRet = false;
	        }
      }
   });
   return blnRet;
}
//删除数据
function deleteData(xxid,mxid){
	var blnRet = false;
	var beanIn = new makeBeanIn(
			xxid,"","","","",mxid,"","",""
	);
	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/Servlet3020130",
		data: {
			CMD    : "<%=Servlet3020130.CMD_SEND_DELETE%>",
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
//设置按钮状态
function setButtonStatus(strFlag) {
	if (strFlag == "1") {//初始状态
		$('#btnAdd').attr("disabled", "disabled");
		$('#btnUpd').attr("disabled", "disabled");
		$('#btnDel').attr("disabled", "disabled");
		$('#btnSave').attr("disabled", "disabled");
		$('#btnCancel').attr("disabled", "disabled");
	} else if (strFlag == "2") {//查询后/返回
		$('#btnAdd').removeAttr("disabled");
		$('#btnUpd').attr("disabled", "disabled");
		$('#btnDel').attr("disabled", "disabled");
		$('#btnSave').attr("disabled", "disabled");
		$('#btnCancel').attr("disabled", "disabled");
		$('#txtEditFbzt').attr("disabled","disabled");
		$('#txtEditFbrq').attr("disabled","disabled");
		$('#txtEditYxrq').attr("disabled","disabled");
		$('#txtEditFbnr').attr("disabled","disabled");

		$('#txtEditFbzt').val("");
		$('#txtEditFbrq').val("");
		$('#txtEditYxrq').val("");
		$('#txtEditFbnr').val("");
	} else if (strFlag.substring(0, 1) == "3") {
		//新增/修改
		$('#btnAdd').attr("disabled","disabled");
		$('#btnUpd').attr("disabled", "disabled");
		$('#btnDel').attr("disabled", "disabled");
		$('#btnSave').removeAttr("disabled");
		$('#btnCancel').removeAttr("disabled");
		if (strFlag == "31") {//新增
			$('#txtEditFbzt').removeAttr("disabled");
			$('#txtEditFbrq').removeAttr("disabled");
			$('#txtEditYxrq').removeAttr("disabled");
			$('#txtEditFbnr').removeAttr("disabled");

			$('#txtEditFbzt').val("");
			$('#txtEditFbrq').val(timeStr);
			$('#txtEditYxrq').val("");
			$('#txtEditFbnr').val("");
			$('#txtEditFbzt').focus();
		} else if (strFlag == "32") {//修改
			$('#txtEditFbzt').removeAttr("disabled");
			$('#txtEditFbrq').removeAttr("disabled");
			$('#txtEditYxrq').removeAttr("disabled");
			$('#txtEditFbnr').removeAttr("disabled");
			$('#txtEditFbzt').focus();
		} else if (strFlag == "32") {//删除
			$('#txtEditFbzt').attr("disabled","disabled");
			$('#txtEditFbrq').attr("disabled","disabled");
			$('#txtEditYxrq').attr("disabled","disabled");
			$('#txtEditFbnr').attr("disabled","disabled");
		}
	} else if (strFlag == "4") {//选中行
		$('#btnAdd').removeAttr("disabled");
		$('#btnUpd').removeAttr("disabled");
		$('#btnDel').removeAttr("disabled");
		$('#btnSave').attr("disabled", "disabled");
		$('#btnCancel').attr("disabled", "disabled");
		$('#txtEditFbzt').attr("disabled","disabled");
		$('#txtEditFbrq').attr("disabled","disabled");
		$('#txtEditYxrq').attr("disabled","disabled");
		$('#txtEditFbnr').attr("disabled","disabled");
	}
}
//验证编辑输入数据
function funEditCheck() {
	if (optionFlag == "Add" || optionFlag == "Upd") {
		if ($('#txtEditFbzt').val() == "") {
			layer.alert('请输入发布主题！', 0, '友情提示', function() {
				$('#txtEditFbzt').focus();
				layer.close(layer.index);
			});
			return false;
		}
		if ($('#txtEditFbnr').val() == "") {
			layer.alert('请输入发布内容！', 0, '友情提示', function() {
				$('#txtEditFbnr').focus();
				layer.close(layer.index);
			});
			return false;
		}
		if ($('#txtEditFbrq').val() == "") {
			layer.alert('请选择发布日期！', 0, '友情提示', function() {
				$('#txtEditFbrq').focus();
				layer.close(layer.index);
			});
			return false;
		}
		if ($('#txtEditYxrq').val() == "") {
			layer.alert('请选择有效日期！', 0, '友情提示', function() {
				$('#txtEditYxrq').focus();
				layer.close(layer.index);
			});
			return false;
		}
	}
	return true;
}
</script>
</head>
<body>
	<fieldset id = "infoRegion">
		<legend>历史消息列表</legend>
	</fieldset>
	<div id="gridCanvas">
		<table id="mmg" class="mmg"></table>
		<div id="pg" style="text-align: right;"></div>
	</div>
	<fieldset id = "editRegion">
		<legend>编辑</legend>
		<table id="detailCanvas" class="eTable6">
			<tr>
				<th style="width:100px">发布主题</th>
				<td colspan="3"><input id="txtEditFbzt" name="发布主题" maxlength="20" style="width: 395px" /></td>
			</tr>
			<tr>
				<th style="width:100px">发布内容</th>
				<td colspan="3"><textarea id="txtEditFbnr" name="发布内容" cols="63" rows="3"/></textarea>
			</tr>
			<tr>
				<th style="width:100px">发布日期</th>
				<td><input type="text" style="font-size:12px;line-height:16px; height:18px;"  class="laydate-icon-default"  id="txtEditFbrq" name="发布日期" readonly="readonly"  onclick="laydate()"/></td>
				<th style="width:100px">有效日期</th>
				<td><input type="text" style="font-size:12px;line-height:16px; height:18px;"  class="laydate-icon-default"  id="txtEditYxrq" name="有效日期" readonly="readonly"  onclick="laydate()"/></td>
			</tr>
		</table>
		<div id="buttonCanvas" class="gToolbar gTbrCenter ">
			<input type="button" value="新增" id="btnAdd" name="btnAdd" onclick="btn_Add()" />
			<input type="button" value="修改" id="btnUpd" name="btnUpd" onclick="btn_Upd()" />
			<input type="button" value="删除" id="btnDel" name="btnDel" />
			<input type="button" value="保存" id="btnSave" name="btnSave" />
			<input type="button" value="取消" id="btnCancel" name="btnCancel" />
		</div>
	</fieldset>
</body>
</html>