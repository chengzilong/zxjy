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
<title>科目信息</title>
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
	/* 初始化传递参数 */
	obj = window.parent.obj;
	/* 初始化表格Start */
   var cols = [
       { title:'学生姓名', name:'XSXX_XSXM' ,width:80, sortable:true, align:'center',lockDisplay: true },
       { title:'班次名称', name:'BCXX_BCMC' ,width:150, sortable:true, align:'center',lockDisplay: true  },
       { title:'评价内容', name:'PJXX_PJNR' ,width:300, sortable:true, align:'center',lockDisplay: true  },
       { title:'评价积分', name:'PJXX_PJJF' ,width:50, sortable:true, align:'center',lockDisplay: true  },
       { title:'评价时间', name:'PJXX_PJSJ' ,width:80, sortable:true, align:'center',lockDisplay: true  }
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
        ,params:{CMD : "<%=Servlet3020130.CMD_TALK_SELECT%>"}
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
        ,nowrap:true
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
         $('#txtEditPjjf').val(arrList[0].PJXX_PJJF);
         $('#txtEditPjnr').val(arrList[0].PJXX_PJNR);
         setButtonStatus("4");   
	   } else {
		   $('#txtEditPjjf').val("");
		   $('#txtEditPjnr').val("");
		   setButtonStatus(2);//未选中行,则只有新增按钮可用
	   }
   }).on('click', ':checkbox', function(){ //选择checkbox
	   if(this.checked == true){
		   var arrList = mmg.selectedRows();
		   if(arrList.length>0){
	         $('#txtEditPjjf').val(arrList[0].PJXX_PJJF);
	         $('#txtEditPjnr').val(arrList[0].PJXX_PJNR);
	         setButtonStatus("4");   
		   }
	   }else{
		   $('#txtEditPjjf').val("");
		   $('#txtEditPjnr').val("");
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
         $('#txtEditPjjf').val(arrList[0].PJXX_PJJF);
         $('#txtEditPjnr').val(arrList[0].PJXX_PJNR);
         setButtonStatus("4");   
	   } else {
		   $('#txtEditPjjf').val("");
		   $('#txtEditPjnr').val("");
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
    		var arrList = mmg.selectedRows();
    		if(arrList.length<=0){
    			layer.alert('请选择要新增评价的数据行！', 0, '友情提示');
    			return;
    		}
    		if(funEditCheck()==false) return;
    		layer.confirm('是否新增评价？', function() {
    			if(insertData(arrList[0].BCXX_BCID)==true){
      	            //重新查询数据
      	            loadGridByBean();
      	            setButtonStatus("2");
      	            mmg.deselect('all');
    			}
    		});
    	}else if(optionFlag == "Upd"){
    		var arrList = mmg.selectedRows();
    		if(arrList.length<=0){
    			layer.alert('请选择要修改评价的数据行！', 0, '友情提示');
    			return;
    		}
    		if(funEditCheck()==false) return;
    		layer.confirm('是否修改评价？', function() {
    			if(updateData(arrList[0].PJXX_PJID)==true){
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
 	   }
 	  layer.confirm('是否删除评价？', function() {
 		 if(deleteData(arrList[0].PJXX_PJID)==true){
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
function makeBeanIn(strPJXX_PJID,strPJXX_PJJF,strPJXX_PJNR,strXSXX_XSID,strBCXX_BCID){
	this.PJXX_PJID = strPJXX_PJID;
	this.PJXX_PJJF = strPJXX_PJJF;
	this.PJXX_PJNR = strPJXX_PJNR;
	this.XSXX_XSID = strXSXX_XSID;
	this.BCXX_BCID = strBCXX_BCID;
}
//加载数据
function loadGridByBean(){
	var beanIn = new makeBeanIn(
			"","","",obj.stuID,""
	);
	//重新查询数据
	mmg.load({
    	beanLoad  :  JSON.stringify(beanIn)
	});
}
//新增数据
function insertData(bcId){
	var blnRet = false;
	var beanIn = new makeBeanIn(
			"",
			$('#txtEditPjjf').val(),
			$('#txtEditPjnr').val(),
            obj.stuID,
            bcId
	);
	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/Servlet3020130",
		data: {
			CMD    : "<%=Servlet3020130.CMD_TALK_INSERT%>",
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
function updateData(pjId){
	var blnRet = false;
	var beanIn = new makeBeanIn(
			pjId,
			$('#txtEditPjjf').val(),
			$('#txtEditPjnr').val(),
            "",""
	);
    $.ajax({
      async     : false,
      type      : "post",
      dataType  : "json",
      url: "<%=basePath%>/Servlet3020130",
      data: {
         CMD    : "<%=Servlet3020130.CMD_TALK_UPDATE%>",
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
function deleteData(pjId){
	var blnRet = false;
	var beanIn = new makeBeanIn(
			pjId,"","","",""
	);
	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/Servlet3020130",
		data: {
			CMD    : "<%=Servlet3020130.CMD_TALK_DELETE%>",
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
		$('#btnAdd').attr("disabled", "disabled");
		$('#btnUpd').attr("disabled", "disabled");
		$('#btnDel').attr("disabled", "disabled");
		$('#btnSave').attr("disabled", "disabled");
		$('#btnCancel').attr("disabled", "disabled");
		$('#txtEditPjjf').attr("disabled","disabled");
		$('#txtEditPjnr').attr("disabled","disabled");
		
		$('#txtEditPjjf').val("");
		$('#txtEditPjnr').val("");
	} else if (strFlag.substring(0, 1) == "3") {
		//新增/修改
		$('#btnAdd').attr("disabled","disabled");
		$('#btnUpd').attr("disabled", "disabled");
		$('#btnDel').attr("disabled", "disabled");
		$('#btnSave').removeAttr("disabled");
		$('#btnCancel').removeAttr("disabled");
		if (strFlag == "31") {//新增
			$('#txtEditPjjf').removeAttr("disabled");
			$('#txtEditPjnr').removeAttr("disabled");
			
			$('#txtEditPjjf').val("");
			$('#txtEditPjnr').val("");
			$('#txtEditPjjf').focus();
		} else if (strFlag == "32") {//修改
			$('#txtEditPjjf').removeAttr("disabled");
			$('#txtEditPjnr').removeAttr("disabled");
			$('#txtEditPjjf').focus();
		} else if (strFlag == "32") {//删除
			$('#txtEditPjjf').attr("disabled","disabled");
			$('#txtEditPjnr').attr("disabled","disabled");
		}
	} else if (strFlag == "4") {//选中行
		var arrList = mmg.selectedRows();
		if (arrList[0].PJXX_PJID == "") {
			$('#btnAdd').removeAttr("disabled");
			$('#btnUpd').attr("disabled", "disabled");
			$('#btnDel').attr("disabled", "disabled");
		} else {
			$('#btnAdd').attr("disabled", "disabled");
			$('#btnUpd').removeAttr("disabled");
			$('#btnDel').removeAttr("disabled");
		}
		$('#btnSave').attr("disabled", "disabled");
		$('#btnCancel').attr("disabled", "disabled");
		$('#txtEditPjjf').attr("disabled","disabled");
		$('#txtEditPjnr').attr("disabled","disabled");
	}
}
//验证编辑输入数据
function funEditCheck() {
	if (optionFlag == "Add" || optionFlag == "Upd") {
		if ($('#txtEditPjjf').val() == "") {
			layer.alert('请给出评价积分！', 0, '友情提示', function() {
				$('#txtEditPjjf').focus();
				layer.close(layer.index);
			});
			return false;
		}
		if ($('#txtEditPjnr').val() == "") {
			layer.alert('请填写评价内容！', 0, '友情提示', function() {
				$('#txtEditPjnr').focus();
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
		<legend>评价列表</legend>
	</fieldset>
	<div id="gridCanvas">
		<table id="mmg" class="mmg"></table>
		<div id="pg" style="text-align: right;"></div>
	</div>
	<fieldset id = "editRegion">
		<legend>编辑</legend>
		<table id="detailCanvas" class="eTable6">
			<tr>
				<th style="width:100px">评价积分</th>
				<td colspan="3"><input id="txtEditPjjf" name="评价积分" maxlength="20" style="width: 395px" /></td>
			</tr>
			<tr>
				<th style="width:100px">评价内容</th>
				<td colspan="3"><textarea id="txtEditPjnr" name="评价内容" cols="63" rows="3"/></textarea>
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