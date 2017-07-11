<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.xsjy.servlet.servlet9010000.Servlet9010150"%> 
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
<title>站点管理</title>
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
//初始化表格
$(document).ready(function(){
   var cols = [
       { title:'站点名称', name:'PXWD_ZDMC' ,width:100, sortable:true, align:'center',lockDisplay: true },
       { title:'站点地址', name:'PXWD_ZDDZ' ,width:300, sortable:true, align:'center',lockDisplay: true  },
       { title:'联系人', name:'PXWD_LXR' ,width:80, sortable:true, align:'center',lockDisplay: true  },
       { title:'联系电话', name:'PXWD_LXDH' ,width:80, sortable:true, align:'center',lockDisplay: true  },
       { title:'备注', name:'PXWD_BZXX' ,width:300, sortable:true, align:'center',lockDisplay: true  }

   ];

   intheight = document.documentElement.clientHeight -$('#editRegion').height()-$('#selectRegion').height()-80; 
   if(intheight<100){
	   intheight = 100;
   }

   mmg = $('.mmg').mmGrid({
         height: intheight
        ,cols: cols
        ,url: '<%=basePath%>/Servlet9010150'
        ,method: 'post'
        ,params:{CMD : "<%=Servlet9010150.CMD_SELECT%>"}
        ,remoteSort:true
        ,sortName: 'PXWD_ZDID'
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
   
   mmg.on('rowSelected', function(e, item, rowIndex, colIndex){//选择数据行
	   var arrList = mmg.row(rowIndex);  
	   $('#txtEditZDID').val(arrList.PXWD_ZDID);
       $('#txtEditZDMC').val(arrList.PXWD_ZDMC);
       $('#txtEditZDDZ').val(arrList.PXWD_ZDDZ);
       $('#txtEditLXR').val(arrList.PXWD_LXR);
       $('#txtEditLXDH').val(arrList.PXWD_LXDH);
       $('#txtEditBZXX').val(arrList.PXWD_BZXX);
       setButtonStatus("4");
       
   }).on('click', ':checkbox', function(){ //选择checkbox
	   if(this.checked == true){
		   var arrList = mmg.selectedRows();
		   if(arrList.length>0){
		     $('#txtEditZDID').val(arrList.PXWD_ZDID);
		     $('#txtEditZDMC').val(arrList[0].PXWD_ZDMC);
       		 $('#txtEditZDDZ').val(arrList[0].PXWD_ZDDZ);
       		 $('#txtEditLXR').val(arrList[0].PXWD_LXR);
       		 $('#txtEditLXDH').val(arrList[0].PXWD_LXDH);    
       		 $('#txtEditBZXX').val(arrList[0].PXWD_BZXX);   		 
	         setButtonStatus("4");   
		   }
	   }else{
	        $('#txtEditZDMC').val("");
       		 $('#txtEditZDDZ').val("");
       		 $('#txtEditLXR').val("");
       		 $('#txtEditLXDH').val("");    
       		 $('#txtEditBZXX').val("");   		 
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
       var arrList = mmg.row(rowIndex);
       var arrList1 = mmg.selectedRows();
       if(arrList1.length>0) {
           $('#txtEditZDID').val(arrList.PXWD_ZDID);
	       $('#txtEditZDMC').val(arrList.PXWD_ZDMC);
	       $('#txtEditZDDZ').val(arrList.PXWD_ZDDZ);
	       $('#txtEditLXR').val(arrList.PXWD_LXR);
	       $('#txtEditLXDH').val(arrList.PXWD_LXDH);       
	       $('#txtEditBZXX').val(arrList.PXWD_BZXX);       
      	   setButtonStatus("4"); 
       }else
       {
           setButtonStatus("2"); 
       }
    });

   
   $('#btnSearch').on('click', function(){
	   loadGridByBean();
   });
   
    
   $('#btnDel').on('click', function(){
	   var arrList = mmg.selectedRows();
	   if(arrList.length<=0){
		   layer.alert('请选择要删除的数据行！', 0, '友情提示');
 	       return;
	   }
	   layer.confirm('是否删除站点信息？', function() {
		   if(deleteZDFLCode()==true){
			   //重新查询数据
			   loadGridByBean();
			   setButtonStatus("2");
			   mmg.deselect('all');
		   }
	   }); 
   });
    
    $('#btnCancel').on('click', function(){
    	setButtonStatus(2);
    	mmg.deselect('all');
    });
    
    $('#btnSave').on('click', function(){
    	if(optionFlag == "Add"){
    		if(funEditCheck()==false) return;
    		layer.confirm('是否增加站点信息？', function() {
	    		if(insertZDFLCode()==true){
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
    		layer.confirm('是否修改站点信息？', function() {
	    		if(updateZDFLCode()==true){
		             //重新查询数据
		             loadGridByBean();
		             setButtonStatus("2");
		             mmg.deselect('all');
		        }
	        });
    	}
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

function makeBeanIn(strPXWD_ZDMC,strPXWD_LXR){
    this.PXWD_ZDMC = strPXWD_ZDMC;
    this.PXWD_LXR = strPXWD_LXR;
}
function makeBeanInadd(strPXWD_ZDMC,strPXWD_ZDDZ,strPXWD_LXR,strPXWD_LXDH,strPXWD_BZXX){
    this.PXWD_ZDMC = strPXWD_ZDMC;  
    this.PXWD_ZDDZ = strPXWD_ZDDZ;
    this.PXWD_LXR = strPXWD_LXR;
    this.PXWD_LXDH = strPXWD_LXDH;
    this.PXWD_BZXX = strPXWD_BZXX;
}
function makeBeanInedit(strPXWD_ZDID,strPXWD_ZDMC,strPXWD_ZDDZ,strPXWD_LXR,strPXWD_LXDH,strPXWD_BZXX){
    this.PXWD_ZDID = strPXWD_ZDID;  
    this.PXWD_ZDMC = strPXWD_ZDMC;  
    this.PXWD_ZDDZ = strPXWD_ZDDZ;
    this.PXWD_LXR = strPXWD_LXR;
    this.PXWD_LXDH = strPXWD_LXDH;
    this.PXWD_BZXX = strPXWD_BZXX;
}
function makeBeanIndel(strPXWD_ZDID){
    this.PXWD_ZDID = strPXWD_ZDID;  
}
function loadGridByBean(){
	var beanIn = new makeBeanIn(
			$('#txtSelZDMC').val(),
			$('#txtSelLXR').val()
	);
	//重新查询数据
	mmg.load({
    	beanLoad  :  JSON.stringify(beanIn)
	});
}

function insertZDFLCode(){
	var blnRet = false;
	var beanIn = new makeBeanInadd(
		   $('#txtEditZDMC').val(),
           $('#txtEditZDDZ').val(),
           $('#txtEditLXR').val(),
           $('#txtEditLXDH').val(),
           $('#txtEditBZXX').val()
	);
	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/Servlet9010150",
		data: {
			CMD    : "<%=Servlet9010150.CMD_INSERT%>",
		    BeanIn : JSON.stringify(beanIn)
		},
		complete :function(response){},
		success: function(response){
			var strResult = response[0];
			if (strResult == "SUCCESS") {
				layer.msg('恭喜：新增站点信息成功！', 1, 9);
				blnRet = true;
			} else if (strResult == "FAILURE") {
				layer.msg('对不起：新增站点信息失败！', 1, 8);
	            blnRet = false;
			} else {
				layer.msg('提示：新增站点信息出错！', 1, 0);
				blnRet = false;
			}
		}
	});
	return blnRet;
}
function updateZDFLCode(){
	var blnRet = false;
	var beanIn = new makeBeanInedit(
			$('#txtEditZDID').val(),
			$('#txtEditZDMC').val(),
            $('#txtEditZDDZ').val(),
            $('#txtEditLXR').val(),
            $('#txtEditLXDH').val(),
            $('#txtEditBZXX').val()
	);
   
    $.ajax({
      async     : false,
      type      : "post",
      dataType  : "json",
      url: "<%=basePath%>/Servlet9010150",
      data: {
         CMD    : "<%=Servlet9010150.CMD_UPDATE%>",
         BeanIn : JSON.stringify(beanIn)
      },
      complete :function(response){},
      success: function(response){
          var strResault = response[0];
          var strResult = response[0];
			if (strResult == "SUCCESS") {
				layer.msg('恭喜：修改站点信息成功！', 1, 9);
				blnRet = true;
			} else if (strResult == "FAILURE") {
				layer.msg('对不起：修改站点信息失败！', 1, 8);
	            blnRet = false;
			} else {
				layer.msg('提示：修改站点信息出错！', 1, 0);
				blnRet = false;
			}
      }
   });
   return blnRet;
}

function deleteZDFLCode(){
	var blnRet = false;
	var beanIn = new makeBeanIndel(
			$('#txtEditZDID').val()
	);

	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/Servlet9010150",
		data: {
			CMD    : "<%=Servlet9010150.CMD_DELETE%>",
			BeanIn : JSON.stringify(beanIn)
		},
		complete : function(response) {},
		success : function(response) {
			var strResult = response[0];
			if (strResult == "SUCCESS") {
				layer.msg('恭喜：删除站点信息成功！', 1, 9);
				blnRet = true;
			} else if (strResult == "FAILURE") {
				layer.msg('对不起：删除站点信息失败！', 1, 8);
	            blnRet = false;
			} else {
				layer.msg('提示：删除站点信息出错！', 1, 0);
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
		$('#txtEditZDMC').attr("disabled","disabled");	
		$('#txtEditZDDZ').attr("disabled","disabled");
		$('#txtEditLXR').attr("disabled","disabled");
		$('#txtEditLXDH').attr("disabled","disabled");
		$('#txtEditBZXX').attr("disabled","disabled");
		
		$('#txtEditZDMC').val("");			
		$('#txtEditZDDZ').val("");
		$('#txtEditLXR').val("");
		$('#txtEditLXDH').val("");
		$('#txtEditBZXX').val("");

	} else if (strFlag.substring(0, 1) == "3") {
		//新增/修改
		$('#btnAdd').attr("disabled","disabled");
		$('#btnUpd').attr("disabled", "disabled");
		$('#btnDel').attr("disabled", "disabled");
		$('#btnSave').removeAttr("disabled");
		$('#btnCancel').removeAttr("disabled");
		if (strFlag == "31") {//新增
			$('#txtEditZDMC').removeAttr("disabled");
			$('#txtEditZDDZ').removeAttr("disabled");
			$('#txtEditLXR').removeAttr("disabled");
			$('#txtEditLXDH').removeAttr("disabled");
			$('#txtEditBZXX').removeAttr("disabled");
			
			$('#txtEditZDMC').val("");
			$('#txtEditZDDZ').val("");
			$('#txtEditLXR').val("");
			$('#txtEditLXDH').val("");	
			$('#txtEditBZXX').val("");		
			$('#txtEditZDMC').focus();
		} else if (strFlag == "32") {//修改
			$('#txtEditZDMC').removeAttr("disabled");
			$('#txtEditZDDZ').removeAttr("disabled");
			$('#txtEditLXR').removeAttr("disabled");
			$('#txtEditLXDH').removeAttr("disabled");
			$('#txtEditBZXX').removeAttr("disabled");
			$('#txtEditZDMC').focus();
		} else if (strFlag == "33") {//删除
		    $('#txtEditCode').attr("disabled","disabled");
			$('#selectEditSFZD').attr("disabled","disabled");
			$('#selectEditMDZD').attr("disabled","disabled");
			$('#txtEditSFBL').attr("disabled","disabled");
			$('#txtEditMDBL').attr("disabled","disabled");
			$('#txtEditGSBL').attr("disabled","disabled");
		}
	} else if (strFlag == "4") {//选中行
		//$('#btnAdd').removeAttr("disabled");
		$('#btnUpd').removeAttr("disabled");
		$('#btnDel').removeAttr("disabled");
		$('#btnSave').attr("disabled", "disabled");
		$('#btnCancel').attr("disabled", "disabled");
		$('#txtEditZDMC').attr("disabled","disabled");
		$('#txtEditZDDZ').attr("disabled","disabled");
		$('#txtEditLXR').attr("disabled","disabled");
		$('#txtEditLXDH').attr("disabled","disabled");
		$('#txtEditBZXX').attr("disabled","disabled");
	}
}
//验证编辑输入数据
function funEditCheck() {
	if (optionFlag == "Add" || optionFlag == "Upd") {
		if ($('#txtEditZDMC').val() == "") {
			layer.alert('请输入站点名称！', 0, '友情提示', function() {
				$('#txtEditZDMC').focus();
				layer.close(layer.index);
			});
			return false;
		}
		if ($('#txtEditZDDZ').val() == "") {
			layer.alert('请输入站点地址！', 0, '友情提示', function() {
				$('#txtEditZDDZ').focus();
				layer.close(layer.index);
			});
			return false;
		}
		if ($('#txtEditLXR').val() == "") {
			layer.alert('请输入联系人！', 0, '友情提示', function() {
				$('#txtEditLXR').focus();
				layer.close(layer.index);
			});
			return false;
		}	
		if ($('#txtEditLXDH').val() == "") {
			layer.alert('请输入联系电话！', 0, '友情提示', function() {
				$('#txtEditLXDH').focus();
				layer.close(layer.index);
			});
			return false;
		}		
		if (optionFlag == "Add"){//新增：判断是否站点已存在
// 			if(checkZDFLExistadd()==true){
// 				layer.alert('提示：该站点名称已存在，无法重复添加！', 0, '友情提示');
// 				return false;
// 			}
		}else 
		if(optionFlag == "Upd"){//修改：判断是否站点已存在
			if(checkZDFLExistedit()==false){
				layer.alert('提示：该站点信息不存在，不能修改！', 0, '友情提示');
				return false;
			}
		}
		
	}
	return true;
}

function checkZDFLExistadd(){
	var blnRet = false;
	var beanIn = new makeBeanInCheckadd(
			$('#txtEditZDMC').val()
	);

	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/Servlet9010150",
		data: {
			CMD    : "<%=Servlet9010150.CMD_CHK_EXIST%>",
			BeanIn : JSON.stringify(beanIn)
		},
		complete : function(response) {
		},
		success : function(response) {
			var strResault = response[0];
			if (strResault == "DATA_EXIST") {
				blnRet = true;
			} else{
				blnRet = false;
			}
		}
	});
	return blnRet;
}
function checkZDFLExistedit(){
	var blnRet = false;
	var beanIn = new makeBeanInCheckedit(
			$('#txtEditZDID').val()
	);

	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/Servlet9010150",
		data: {
			CMD    : "<%=Servlet9010150.CMD_CHK_EXIST%>",
			BeanIn : JSON.stringify(beanIn)
		},
		complete : function(response) {
		},
		success : function(response) {
			var strResault = response[0];
			if (strResault == "DATA_EXIST") {
				blnRet = true;
			} else{
				blnRet = false;
			}
		}
	});
	return blnRet;
}
function makeBeanInCheckadd(strPXWD_ZDMC){
	this.PXWD_ZDMC = strPXWD_ZDMC;	
}
function makeBeanInCheckedit(strPXWD_ZDID){
	this.PXWD_ZDID = strPXWD_ZDID;	
}
function makeBeanInmd(strZDFL_SFZDS){
	this.ZDFL_SFZDS = strZDFL_SFZDS;	
}
</script>
</head>
<body>
	<fieldset id = "selectRegion">
		<legend>查询条件</legend>
		<table>
			<tr>
				<th style="width:100px">站点名称</th>
				<td><input id="txtSelZDMC" name="站点名称" maxlength="20" /></td>
				<th style="width:100px">联系人</th>
				<td><input id="txtSelLXR" name="联系人" maxlength="20" /></td>
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
				<th style="width:100px">站点名称</th>
				<td><input type="hidden"  id="txtEditZDID"><input id="txtEditZDMC" name="站点名称" maxlength="20" /></td>
				<th style="width:100px">站点地址</th>	
				<td><input id="txtEditZDDZ" name="站点地址" maxlength="200" /></td>
			</tr>
			<tr>
				<th style="width:100px">联系人</th>
				<td><input id="txtEditLXR" name="联系人" maxlength="20" /></td>
				<th style="width:100px">联系电话</th>
				<td><input id="txtEditLXDH" name="联系电话" maxlength="20" /></td>
				<th style="width:100px">备注</th>
				<td><input  style="width:300px" id="txtEditBZXX" name="备注" maxlength="200" /></td>
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