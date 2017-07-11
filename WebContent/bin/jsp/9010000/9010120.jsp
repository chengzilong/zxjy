<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.xsjy.servlet.servlet9010000.Servlet9010120"%> 
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
<title>角色管理</title>
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
       { title:'角色编号', name:'YHJS_JSID' ,width:50, sortable:true, align:'center',lockDisplay: true },
       { title:'角色名称', name:'YHJS_JSMC' ,width:100, sortable:true, align:'center',lockDisplay: true  },
       { title:'角色类型', name:'JSLX' ,width:100, sortable:true, align:'center',lockDisplay: true  },
       { title:'角色描述', name:'YHJS_JSMS' ,width:150, sortable:true, align:'center',lockDisplay: true  },
       { title:'创建人', name:'YHJS_CJR' ,width:50,hidden: true,sortable:true, align:'center',lockDisplay: true },
       { title:'创建时间', name:'YHJS_CJSJ' ,width:100,hidden: true,sortable:true, align:'center',lockDisplay: true },
       { title:'修改人', name:'YHJS_GXR' ,width:50,hidden: true,sortable:true, align:'center',lockDisplay: true },
       { title:'修改时间', name:'YHJS_GXSJ' ,width:100,hidden: true,sortable:true, align:'center',lockDisplay: true }
   ];
             
    intheight = document.documentElement.clientHeight -$('#editRegion').height()-$('#selectRegion').height()-80; 
   if(intheight<100){
	   intheight = 100;
   }

   mmg = $('.mmg').mmGrid({
         height: intheight
        ,cols: cols
        ,url: '<%=basePath%>/Servlet9010120'
        ,method: 'post'
        ,params:{CMD : "<%=Servlet9010120.CMD_SELECT%>"}
        ,remoteSort:true
        ,sortName: 'YHJS_JSID'
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
         $('#txtEditCode').val(arrList[0].YHJS_JSID);
         $('#txtEditName').val(arrList[0].YHJS_JSMC);
         $('#selectEditJslx').val(arrList[0].YHJS_JSLX);
         $('#txtEditMS').val(arrList[0].YHJS_JSMS);
         
         setButtonStatus("4");   
	   }else{
		   $('#txtEditCode').val("");
		   $('#txtEditName').val("");
		   $('#selectEditJslx').val("000");
		   $('#txtEditMS').val("");
		  
		   setButtonStatus(2);//未选中行,则只有新增按钮可用
	   }
   }).on('click', ':checkbox', function(){ //选择checkbox
	   if(this.checked == true){
		   var arrList = mmg.selectedRows();
		   if(arrList.length>0){
	         $('#txtEditCode').val(arrList[0].YHJS_JSID);
	         $('#txtEditName').val(arrList[0].YHJS_JSMC);
	         $('#selectEditJslx').val(arrList[0].YHJS_JSLX);
	         $('#txtEditMS').val(arrList[0].YHJS_JSMS);
	         
	         setButtonStatus("4");   
		   }
	   }else{
		   $('#txtEditCode').val("");
		   $('#txtEditName').val("");
		   $('#selectEditJslx').val("000");
		   $('#txtEditMS').val("");
		  
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
         $('#txtEditCode').val(arrList[0].YHJS_JSID);
         $('#txtEditName').val(arrList[0].YHJS_JSMC);
         $('#selectEditJslx').val(arrList[0].YHJS_JSLX);
         $('#txtEditMS').val(arrList[0].YHJS_JSMS);
         
         setButtonStatus("4");   
	   }else{
		   $('#txtEditCode').val("");
		   $('#txtEditName').val("");
		   $('#selectEditJslx').val("000");
		   $('#txtEditMS').val("");
		  
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
	   layer.confirm('是否删除角色信息？', function() {
		   if(deleteUserCode()==true){
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
    		layer.confirm('是否增加角色信息？', function() {
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
    		layer.confirm('是否修改角色信息？', function() {
	    		if(updateRoleCode()==true){
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

function makeBeanIn(strYHJS_JSID,strYHJS_JSMC,strYHJS_JSLX,strYHJS_JSMS){
    this.YHJS_JSID = strYHJS_JSID;
    this.YHJS_JSMC = strYHJS_JSMC;
    this.YHJS_JSLX = strYHJS_JSLX;
    this.YHJS_JSMS = strYHJS_JSMS;
}

function loadGridByBean(){
	var beanIn = new makeBeanIn(
			$('#txtSelectCode').val(),
			$('#txtSelectName').val(),
			$('#selectJslx').val(),
			$('#txtSelectMS').val()
	);
	//重新查询数据
	mmg.load({
    	beanLoad  :  JSON.stringify(beanIn)
	});
}

function insertData(){
	var blnRet = false;
	var beanIn = new makeBeanIn(
			$('#txtEditCode').val(),
			$('#txtEditName').val(),
			$('#selectEditJslx').val(),
			$('#txtEditMS').val()
	);
	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/Servlet9010120",
		data: {
			CMD    : "<%=Servlet9010120.CMD_INSERT%>",
		    BeanIn : JSON.stringify(beanIn)
		},
		complete :function(response){},
		success: function(response){
			var strResult = response[0];
			if (strResult=="SUCCESS") {
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
function updateRoleCode(){
	var blnRet = false;
	var beanIn = new makeBeanIn(
			$('#txtEditCode').val(),
			$('#txtEditName').val(),
			$('#selectEditJslx').val(),
			$('#txtEditMS').val()
	);
   
    $.ajax({
      async     : false,
      type      : "post",
      dataType  : "json",
      url: "<%=basePath%>/Servlet9010120",
      data: {
         CMD    : "<%=Servlet9010120.CMD_UPDATE%>",
         BeanIn : JSON.stringify(beanIn)
      },
      complete :function(response){},
      success: function(response){
          var strResult = response[0];
          if (strResult=="SUCCESS") {
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

function deleteUserCode(){
	var blnRet = false;
	var beanIn = new makeBeanIn(
			$('#txtEditCode').val(),
			"","",""
	);
	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/Servlet9010120",
		data: {
			CMD    : "<%=Servlet9010120.CMD_DELETE%>",
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
			} else if (strResult == "EXCEPTION") {
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
		$('#txtEditCode').attr("disabled","disabled");
		$('#txtEditName').attr("disabled","disabled");
		$('#selectEditJslx').attr("disabled","disabled");
		$('#txtEditMS').attr("disabled","disabled");
		
		$('#txtEditCode').val("");
		$('#txtEditName').val("");
		$('#selectEditJslx').val("000");
		$('#txtEditMS').val("");
	} else if (strFlag.substring(0, 1) == "3") {
		//新增/修改
		$('#btnAdd').attr("disabled","disabled");
		$('#btnUpd').attr("disabled", "disabled");
		$('#btnDel').attr("disabled", "disabled");
		$('#btnSave').removeAttr("disabled");
		$('#btnCancel').removeAttr("disabled");
		if (strFlag == "31") {//新增
			$('#txtEditCode').removeAttr("disabled");
			$('#txtEditName').removeAttr("disabled");
			$('#selectEditJslx').removeAttr("disabled");
			$('#txtEditMS').removeAttr("disabled");
			
			$('#txtEditCode').val("");
			$('#txtEditName').val("");
			$('#selectEditJslx').val("000");
			$('#txtEditMS').val("");
			
			$('#txtEditCode').focus();
		} else if (strFlag == "32") {//修改
			$('#txtEditCode').attr("disabled", "disabled");
			$('#txtEditName').removeAttr("disabled");
			$('#selectEditJslx').removeAttr("disabled");
			$('#txtEditMS').removeAttr("disabled");
			$('#txtEditName').focus();
		} else if (strFlag == "32") {//删除
			$('#txtEditCode').attr("disabled","disabled");
			$('#txtEditName').attr("disabled","disabled");
			$('#selectEditJslx').attr("disabled","disabled");
			$('#txtEditMS').attr("disabled","disabled");
		}
	} else if (strFlag == "4") {//选中行
		$('#btnAdd').removeAttr("disabled");
		$('#btnUpd').removeAttr("disabled");
		$('#btnDel').removeAttr("disabled");
		$('#btnSave').attr("disabled", "disabled");
		$('#btnCancel').attr("disabled", "disabled");
		$('#txtEditCode').attr("disabled","disabled");
		$('#txtEditName').attr("disabled","disabled");
		$('#selectEditJslx').attr("disabled","disabled");
		$('#txtEditMS').attr("disabled","disabled");
	}
}
//验证编辑输入数据
function funEditCheck() {
	if (optionFlag == "Add" || optionFlag == "Upd") {
		if ($('#txtEditCode').val() == "") {
			layer.alert('请输入角色编号！', 0, '友情提示', function() {
				$('#txtEditCode').focus();
				layer.close(layer.index);
			});
			return false;
		}
		if ($('#txtEditName').val() == "") {
			layer.alert('请输入角色名称！', 0, '友情提示', function() {
				$('#txtEditName').focus();
				layer.close(layer.index);
			});
			return false;
		}
		if ($('#selectEditJslx').val() == "000") {
			layer.alert('请选择角色类型！', 0, '友情提示', function() {
				$('#selectEditJslx').focus();
				layer.close(layer.index);
			});
			return false;
		}
		if (optionFlag == "Add"){//新增：判断是否角色代码已存在
			if(checkRoleExist()==true){
				layer.alert('角色代码已存在，不能新增！', 0, '友情提示');
				return false;
			}
		}else if(optionFlag == "Upd"){//修改：判断是否角色代码已存在
			if(checkRoleExist()==false){
				layer.alert('角色代码不存在，不能修改！', 0, '友情提示');
				return false;
			}
		}
	}
	return true;
}

function checkRoleExist(){
	var blnRet = false;
	var beanIn = new makeBeanIn(
			$('#txtEditCode').val(),
			"","",""
	);
	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/Servlet9010120",
		data: {
			CMD    : "<%=Servlet9010120.CMD_CHK_EXIST%>",
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

</script>
</head>
<body>
	<fieldset id = "selectRegion">
		<legend>查询条件</legend>
		<table>
			<tr>
				<th style="width:100px">角色编号</th>
				<td><input id="txtSelectCode" name="角色编号" maxlength="4" onkeypress="filterKeyForNumber(this,'CNUMONLY');"/></td>
				<th style="width:100px">角色名称</th>
				<td><input id="txtSelectName" name="角色名称" maxlength="10" /></td>
				<th style="width:100px">角色类型</th>
				<td>
					<select id="selectJslx" style="width: 100px;">
						<option value="000">所有</option>
						<option value="0">工作人员</option>
						<option value="1">教师</option>
						<option value="2">学生</option>
					</select>
				</td>
				<th style="width:100px">角色描述</th>
				<td><input id="txtSelectMS" name="角色描述" maxlength="20" /></td>
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
				<th style="width:100px">角色编号</th>
				<td><input id="txtEditCode" name="角色编号" maxlength="4" onkeypress="filterKeyForNumber(this,'CNUMONLY');"/></td>
				<th style="width:100px">角色名称</th>
				<td><input id="txtEditName" name="角色名称" maxlength="10" /></td>
				<th style="width:100px">角色类型</th>
				<td>
					<select id="selectEditJslx" style="width: 100px;">
						<option value="000">请选择</option>
						<option value="0">工作人员</option>
						<option value="1">教师</option>
						<option value="2">学生</option>
					</select>
				</td>
				<th style="width:100px">角色描述</th>
				<td><input id="txtEditMS" name="角色描述" maxlength="20" /></td>
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