<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.xsjy.servlet.servlet9010000.Servlet9010140"%> 
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
<title>菜单管理</title>
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
       { title:'菜单编号', name:'MENU_CDID' ,width:80, sortable:true, align:'center',lockDisplay: true },
       { title:'菜单名称', name:'MENU_CDMC' ,width:150, sortable:true, align:'center',lockDisplay: true  },
       { title:'菜单地址', name:'MENU_URL' ,width:150, sortable:true, align:'center',lockDisplay: true  },
       { title:'菜单层级', name:'CDCJ' ,width:80, sortable:true, align:'center',lockDisplay: true  },
       { title:'父节点', name:'MENU_FJDID' ,width:100, sortable:true, align:'center',lockDisplay: true  },
       { title:'序号', name:'MENU_XH' ,width:50, sortable:true, align:'center',lockDisplay: true  },
       { title:'菜单类型', name:'CDLX' ,width:80, sortable:true, align:'center',lockDisplay: true  },
       { title:'菜单状态', name:'MENU_SCBZ' ,width:80, sortable:true, align:'center',lockDisplay: true  },
       { title:'创建人', name:'MENU_CJR' ,width:80,hidden: true,sortable:true, align:'center',lockDisplay: true },
       { title:'创建时间', name:'MENU_CJSJ' ,width:100,hidden: true,sortable:true, align:'center',lockDisplay: true },
       { title:'修改人', name:'MENU_GXR' ,width:80, hidden: true,sortable:true, align:'center',lockDisplay: true  },
       { title:'修改时间', name:'MENU_GXSJ' ,width:100, hidden: true,sortable:true, align:'center',lockDisplay: true  }
   ];

   intheight = document.documentElement.clientHeight -$('#editRegion').height()-$('#selectRegion').height()-80; 
   if(intheight<100){
	   intheight = 100;
   }

   mmg = $('.mmg').mmGrid({
         height: intheight
        ,cols: cols
        ,url: '<%=basePath%>/Servlet9010140'
        ,method: 'post'
        ,params:{CMD : "<%=Servlet9010140.CMD_SELECT%>"}
        ,remoteSort:true
        ,sortName: 'MENU_CDID'
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
   
   mmg.on('rowSelected', function(e, item, rowIndex, colIndex){//选择数据行
	   var arrList = mmg.selectedRows();
	   if(arrList.length>0){
         $('#txtEditCode').val(arrList[0].MENU_CDID);
         $('#txtEditName').val(arrList[0].MENU_CDMC);
         $('#txtEditUrl').val(arrList[0].MENU_URL);
         $('#txtEditLevel').val(arrList[0].MENU_CDCJ);
         $('#txtEditParent').val(arrList[0].MENU_FJDID);
         $('#txtEditXh').val(arrList[0].MENU_XH);
         $('#selectEditCdlx').val(arrList[0].MENU_CDLX);

         setButtonStatus("4");   
	   }else{
		   $('#txtEditCode').val("");
		   $('#txtEditName').val("");
	       $('#txtEditUrl').val("");
	       $('#txtEditLevel').val("");
	       $('#txtEditParent').val("");
	       $('#txtEditXh').val("");
	       $('#selectEditCdlx').val("000");

	       setButtonStatus(2);//未选中行,则只有新增按钮可用
	   }
   }).on('click', ':checkbox', function(){ //选择checkbox
	   if(this.checked == true){
		   var arrList = mmg.selectedRows();
		   if(arrList.length>0){
	         $('#txtEditCode').val(arrList[0].MENU_CDID);
	         $('#txtEditName').val(arrList[0].MENU_CDMC);
	         $('#txtEditUrl').val(arrList[0].MENU_URL);
	         $('#txtEditLevel').val(arrList[0].MENU_CDCJ);
	         $('#txtEditParent').val(arrList[0].MENU_FJDID);
	         $('#txtEditXh').val(arrList[0].MENU_XH);
	         $('#selectEditCdlx').val(arrList[0].MENU_CDLX);

	         setButtonStatus("4");   
		   }
	   }else{
		   $('#txtEditCode').val("");
		   $('#txtEditName').val("");
	       $('#txtEditUrl').val("");
	       $('#txtEditLevel').val("");
	       $('#txtEditParent').val("");
	       $('#txtEditXh').val("");
	       $('#selectEditCdlx').val("000");

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
         $('#txtEditCode').val(arrList[0].MENU_CDID);
         $('#txtEditName').val(arrList[0].MENU_CDMC);
         $('#txtEditUrl').val(arrList[0].MENU_URL);
         $('#txtEditLevel').val(arrList[0].MENU_CDCJ);
         $('#txtEditParent').val(arrList[0].MENU_FJDID);
         $('#txtEditXh').val(arrList[0].MENU_XH);
         $('#selectEditCdlx').val(arrList[0].MENU_CDLX);

         setButtonStatus("4");   
	   }else{
		   $('#txtEditCode').val("");
		   $('#txtEditName').val("");
	       $('#txtEditUrl').val("");
	       $('#txtEditLevel').val("");
	       $('#txtEditParent').val("");
	       $('#txtEditXh').val("");
	       $('#selectEditCdlx').val("000");

	       setButtonStatus(2);//未选中行,则只有新增按钮可用
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
	   layer.confirm('是否删除菜单信息？', function() {
		   if(deleteMenuCode()==true){
			   //重新查询数据
			   loadGridByBean();
			   setButtonStatus("2");
			   mmg.deselect('all');
		   }
	   });  
   });
   
   $('#btnRecovery').on('click', function() {
	   var arrList = mmg.selectedRows();
	   if (arrList.length <= 0) {
		   layer.alert('请选择要恢复的菜单！', 0, '友情提示');
		   return;
	   }
	   if (arrList.length > 0) {
		   for(var i = 0; i < arrList.length; i++) {
				if (arrList[i].YHXX_SCBZ == 0) {
					ayer.alert('提示：所选菜单中，存在未删除的菜单，无需恢复！', 0, '友情提示');
					return; 
				}
			}
	   }
	   layer.confirm('是否恢复选中的菜单？', function() {
		   if (recoveryUser() == true) {
			   loadGridByBean();
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
    		layer.confirm('是否增加菜单信息？', function() {
	    		if(insertMenu()==true){
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
    		layer.confirm('是否修改菜单信息？', function() {
	    		if(updateMenu()==true){
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

function makeBeanIn(strMENU_CDID,strMENU_CDMC,strMENU_URL,strMENU_CDCJ,strMENU_FJDID,strMENU_XH,strMENU_CDLX,strMENU_SCBZ){
    this.MENU_CDID = strMENU_CDID;
    this.MENU_CDMC = strMENU_CDMC;
    this.MENU_URL = strMENU_URL;
    this.MENU_CDCJ = strMENU_CDCJ;
    this.MENU_FJDID = strMENU_FJDID;
	this.MENU_XH = strMENU_XH;
	this.MENU_CDLX = strMENU_CDLX;
	this.MENU_SCBZ = strMENU_SCBZ;
}

function loadGridByBean(){
	var beanIn = new makeBeanIn(
			$('#txtSelectCode').val(),
			$('#txtSelectName').val(),
			"",
			$('#txtSelectLevel').val(),
			$('#txtSelectParent').val(),
			"",
			$('#selectCdlx').val(),
			$('#txtSelectDel').val()
	);
	//重新查询数据
	mmg.load({
    	beanLoad  :  JSON.stringify(beanIn)
	});
}

function insertMenu(){
	var blnRet = false;
	var beanIn = new makeBeanIn(
			$('#txtEditCode').val(),
			$('#txtEditName').val(),
			$('#txtEditUrl').val(),
			$('#txtEditLevel').val(),
			$('#txtEditParent').val(),
			$('#txtEditXh').val(),
			$('#selectEditCdlx').val(),
			""
	);
	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/Servlet9010140",
		data: {
			CMD    : "<%=Servlet9010140.CMD_INSERT%>",
		    BeanIn : JSON.stringify(beanIn)
		},
		complete :function(response){},
		success: function(response){
			var strResult = response[0];
			if (strResult == "SUCCESS") {
				layer.msg('恭喜：新增菜单信息成功！', 1, 9);
				blnRet = true;
			} else if (strResult == "FAILURE") {
				layer.msg('对不起：新增菜单信息失败！', 1, 8);
	            blnRet = false;
			} else if (strResult == "EXCEPTION") {
				layer.msg('提示：新增菜单信息出错！', 1, 0);
				blnRet = false;
			}
		}
	});
	return blnRet;
}
function updateMenu(){
	var blnRet = false;
	var beanIn = new makeBeanIn(
			$('#txtEditCode').val(),
			$('#txtEditName').val(),
			$('#txtEditUrl').val(),
			$('#txtEditLevel').val(),
			$('#txtEditParent').val(),
			$('#txtEditXh').val(),
			$('#selectEditCdlx').val(),
			""
	);
    $.ajax({
      async     : false,
      type      : "post",
      dataType  : "json",
      url: "<%=basePath%>/Servlet9010140",
      data: {
         CMD    : "<%=Servlet9010140.CMD_UPDATE%>",
         BeanIn : JSON.stringify(beanIn)
      },
      complete :function(response){},
      success: function(response){
    	  var strResult = response[0];
			if (strResult == "SUCCESS") {
				layer.msg('恭喜：修改菜单信息成功！', 1, 9);
				blnRet = true;
			} else if (strResult == "FAILURE") {
				layer.msg('对不起：修改菜单信息失败！', 1, 8);
	            blnRet = false;
			} else if (strResult == "EXCEPTION") {
				layer.msg('提示：修改菜单信息出错！', 1, 0);
				blnRet = false;
			}
      }
   });
   return blnRet;
}
//删除菜单
function deleteMenuCode(){
	var blnRet = false;
	var beanIn = new makeBeanIn(
			$('#txtEditCode').val(),
			"","","","","","",""
	);
	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/Servlet9010140",
		data: {
			CMD    : "<%=Servlet9010140.CMD_DELETE%>",
			BeanIn : JSON.stringify(beanIn)
		},
		complete : function(response) {},
		success : function(response) {
			var strResult = response[0];
			if (strResult == "SUCCESS") {
				layer.msg('恭喜：删除菜单信息成功！', 1, 9);
				blnRet = true;
			} else if (strResult == "FAILURE") {
				layer.msg('对不起：删除菜单信息失败！', 1, 8);
	            blnRet = false;
			} else if (strResult == "EXCEPTION") {
				layer.msg('提示：删除菜单信息出错！', 1, 0);
				blnRet = false;
			}
		}
	});
	return blnRet;
}
//恢复删除菜单
function recoveryUser() {
	var blnRet = false;
	var beanIn = new makeBeanIn(
			$('#txtEditCode').val(),
			"","","","","","",""
	);

	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/Servlet9010140",
		data: {
			CMD    : "<%=Servlet9010140.CMD_RECOVERY%>",
			BeanIn : JSON.stringify(beanIn)
		},
		complete : function(response) {},
		success : function(response) {
			var strResult = response[0];
			if (strResult == "SUCCESS") {
				layer.msg('恭喜：恢复菜单信息成功！', 1, 9);
				blnRet = true;
			} else if (strResult == "FAILURE") {
				layer.msg('对不起：恢复菜单信息失败！', 1, 8);
	            blnRet = false;
			} else if (strResult == "EXCEPTION") {
				layer.msg('提示：恢复菜单信息出错！', 1, 0);
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
		$('#btnRecovery').attr("disabled", "disabled");
		$('#btnSave').attr("disabled", "disabled");
		$('#btnCancel').attr("disabled", "disabled");
	} else if (strFlag == "2") {//查询后/返回
		$('#btnAdd').removeAttr("disabled");
		$('#btnUpd').attr("disabled", "disabled");
		$('#btnDel').attr("disabled", "disabled");
		$('#btnRecovery').attr("disabled", "disabled");
		$('#btnSave').attr("disabled", "disabled");
		$('#btnCancel').attr("disabled", "disabled");
		$('#txtEditCode').attr("disabled","disabled");
		$('#txtEditName').attr("disabled","disabled");
		$('#txtEditUrl').attr("disabled","disabled");
		$('#txtEditLevel').attr("disabled","disabled");
		$('#txtEditParent').attr("disabled","disabled");
		$('#txtEditXh').attr("disabled","disabled");
		$('#selectEditCdlx').attr("disabled","disabled");
		
		$('#txtEditCode').val("");
		$('#txtEditName').val("");
		$('#txtEditUrl').val("");
		$('#txtEditLevel').val("");
		$('#txtEditParent').val("");
		$('#txtEditXh').val("");
		$('#selectEditCdlx').val("000");
	} else if (strFlag.substring(0, 1) == "3") {
		//新增/修改
		$('#btnAdd').attr("disabled","disabled");
		$('#btnUpd').attr("disabled", "disabled");
		$('#btnDel').attr("disabled", "disabled");
		$('#btnRecovery').attr("disabled", "disabled");
		$('#btnSave').removeAttr("disabled");
		$('#btnCancel').removeAttr("disabled");
		if (strFlag == "31") {//新增
			$('#txtEditCode').removeAttr("disabled");
			$('#txtEditName').removeAttr("disabled");
			$('#txtEditUrl').removeAttr("disabled");
			$('#txtEditLevel').removeAttr("disabled");
			$('#txtEditParent').removeAttr("disabled");
			$('#txtEditXh').removeAttr("disabled");
			$('#selectEditCdlx').removeAttr("disabled");
			
			$('#txtEditCode').val("");
			$('#txtEditName').val("");
			$('#txtEditUrl').val("");
			$('#txtEditLevel').val("");
			$('#txtEditParent').val("");
			$('#txtEditXh').val("");
			$('#selectEditCdlx').val("000");
			
			$('#txtEditCode').focus();
		} else if (strFlag == "32") {//修改
			$('#txtEditCode').attr("disabled", "disabled");
			$('#txtEditName').removeAttr("disabled");
			$('#txtEditUrl').removeAttr("disabled");
			$('#txtEditLevel').removeAttr("disabled");
			cdcjToFjd();
			$('#txtEditXh').removeAttr("disabled");
			$('#selectEditCdlx').removeAttr("disabled");
			$('#txtEditName').focus();
		} else if (strFlag == "32") {//删除
			$('#txtEditCode').attr("disabled","disabled");
			$('#txtEditName').attr("disabled","disabled");
			$('#txtEditUrl').attr("disabled","disabled");
			$('#txtEditLevel').attr("disabled","disabled");
			$('#txtEditParent').attr("disabled","disabled");
			$('#txtEditXh').attr("disabled","disabled");
			$('#selectEditCdlx').attr("disabled","disabled");
		}
	} else if (strFlag == "4") {//选中行
		$('#btnAdd').removeAttr("disabled");
		$('#btnUpd').removeAttr("disabled");
		$('#btnDel').removeAttr("disabled");
		$('#btnRecovery').removeAttr("disabled");
		$('#btnSave').attr("disabled", "disabled");
		$('#btnCancel').attr("disabled", "disabled");
		$('#txtEditCode').attr("disabled","disabled");
		$('#txtEditName').attr("disabled","disabled");
		$('#txtEditUrl').attr("disabled","disabled");
		$('#txtEditLevel').attr("disabled","disabled");
		$('#txtEditParent').attr("disabled","disabled");
		$('#txtEditXh').attr("disabled","disabled");
		$('#selectEditCdlx').attr("disabled","disabled");
	}
}
//验证编辑输入数据
function funEditCheck() {
	if (optionFlag == "Add" || optionFlag == "Upd") {
		if ($('#txtEditCode').val() == "") {
			layer.alert('请输入菜单编号！', 0, '友情提示', function() {
				$('#txtEditCode').focus();
				layer.close(layer.index);
			});
			return false;
		}
		if ($('#txtEditName').val() == "") {
			layer.alert('请输入菜单名称！', 0, '友情提示', function() {
				$('#txtEditName').focus();
				layer.close(layer.index);
			});
			return false;
		}
		if ($('#txtEditUrl').val() == "") {
			layer.alert('请输入菜单地址！', 0, '友情提示', function() {
				$('#txtEditUrl').focus();
				layer.close(layer.index);
			});
			return false;
		}
		if ($('#txtEditLevel').val() == "") {
			layer.alert('请输入菜单层级！', 0, '友情提示', function() {
				$('#txtEditLevel').focus();
				layer.close(layer.index);
			});
			return false;
		}
		if ($('#txtEditParent').val() == "") {
			layer.alert('请输入父节点！', 0, '友情提示', function() {
				$('#txtEditParent').focus();
				layer.close(layer.index);
			});
			return false;
		}
		if ($('#txtEditXh').val() == "") {
			layer.alert('请输入序号！', 0, '友情提示', function() {
				$('#txtEditXh').focus();
				layer.close(layer.index);
			});
			return false;
		}
		if ($('#selectEditCdlx').val() == "000") {
			layer.alert('请选择菜单类型！', 0, '友情提示', function() {
				$('#selectEditCdlx').focus();
				layer.close(layer.index);
			});
			return false;
		}
		if (optionFlag == "Add"){//新增：判断是否用户代码已存在
			if(checkMenuExist()==true){
				layer.alert('菜单代码已存在，不能新增！', 0, '友情提示');
				return false;
			}
		}else if(optionFlag == "Upd"){//修改：判断是否用户代码已存在
			if(checkMenuExist()==false){
				layer.alert('菜单代码不存在，不能修改！', 0, '友情提示');
				return false;
			}
		}
	}
	return true;
}
//验证菜单信息是否存在
function checkMenuExist(){
	var blnRet = false;
	var beanIn = new makeBeanIn(
			$('#txtEditCode').val(),
			"","","","","","",""
	);
	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/Servlet9010140",
		data: {
			CMD    : "<%=Servlet9010140.CMD_CHK_EXIST%>",
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
//通过选择父菜单，自动生成父节点编号
function cdcjToFjd() {
	var cdcj = $('#txtEditLevel').val();
	if(cdcj == '0') {
		$('#txtEditParent').val("0000000");
		$('#txtEditParent').attr("disabled","disabled");
	} else {
		$('#txtEditParent').removeAttr("disabled");
	}
}

</script>
</head>
<body>
	<fieldset id = "selectRegion">
		<legend>查询条件</legend>
		<table>
			<tr>
				<th style="width:80px">菜单编号</th>
				<td><input id="txtSelectCode" name="菜单编号" maxlength="7" onkeypress="filterKeyForNumber(this,'CNUMONLY');"/></td>
				<th style="width:80px">菜单名称</th>
				<td><input id="txtSelectName" name="菜单名称" maxlength="10" /></td>
				<th style="width:80px">菜单层级</th>
				<td>
					<select id="txtSelectLevel" style="width: 100px"> 
						<option value="" selected>请选择</option>
						<option value="0">父节点</option>
						<option value="1">子节点</option> 
					</select> 
				</td>
				<th style="width:80px">父节点</th>
				<td><input id="txtSelectParent" name="父节点" maxlength="7" onkeypress="filterKeyForNumber(this,'CNUMONLY');"/></td>
			</tr>
			<tr>
				<th style="width:100px">菜单类型</th>
				<td>
					<select id="selectCdlx" style="width: 100px;">
						<option value="000">所有</option>
						<option value="0">管理菜单</option>
						<option value="1">教师菜单</option>
						<option value="2">学生菜单</option>
					</select>
				</td>
				<th style="width:80px">菜单状态</th>
				<td>
					<select id="txtSelectDel" style="width: 100px"> 
						<option value="" selected>请选择</option>
						<option value="0">可用</option>
						<option value="1">不可用</option> 
					</select> 
				</td>
				<th  style="width:100px" colspan="4" align="right"><input type="button" value="查询" id="btnSearch" /></th>
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
				<th style="width:100px">菜单编号</th>
				<td><input id="txtEditCode" name="菜单代码" maxlength="7" onkeypress="filterKeyForNumber(this,'CNUMONLY');"/></td>
				<th style="width:100px">菜单名称</th>
				<td><input id="txtEditName" name="菜单名称" maxlength="10" /></td>
				<th style="width:100px">菜单地址</th>
				<td><input id="txtEditUrl" name="菜单地址" maxlength="16" /></td>
				<th style="width:100px">菜单层级</th>
				<td>
					<select id="txtEditLevel" style="width: 100px" onchange="cdcjToFjd()"> 
						<option value="" selected>请选择</option>
						<option value="0">父节点</option>
						<option value="1">子节点</option> 
					</select> 
				</td>
			</tr>
			<tr>
				<th style="width:100px">父节点</th>
				<td><input id="txtEditParent" name="父节点" maxlength="7" /></td>
				<th style="width:100px">序号</th>
				<td><input id="txtEditXh" name="序号" maxlength="2" /></td>
				<th style="width:100px">菜单类型</th>
				<td>
					<select id="selectEditCdlx" style="width: 100px;">
						<option value="000">请选择</option>
						<option value="0">管理菜单</option>
						<option value="1">教师菜单</option>
						<option value="2">学生菜单</option>
					</select>
				</td>
			</tr>
		</table>
		<div id="buttonCanvas" class="gToolbar gTbrCenter ">
			<input type="button" value="新增" id="btnAdd" name="btnAdd" onclick="btn_Add()" />
			<input type="button" value="修改" id="btnUpd" name="btnUpd" onclick="btn_Upd()" /> 
			<input type="button" value="删除" id="btnDel" name="btnDel" /> 
			<input type="button" value="保存" id="btnSave" name="btnSave" /> 
			<input type="button" value="取消" id="btnCancel" name="btnCancel" /> 
			<input type="button" value="恢复" id="btnRecovery" name="btnRecovery" /> 
		</div>
	</fieldset>
</body>
</html>