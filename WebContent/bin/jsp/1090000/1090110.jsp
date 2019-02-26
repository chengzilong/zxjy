<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.xsjy.servlet.servlet1090000.Servlet1090110"%>
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
<title>课程信息</title>
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
       { title:'阶段', name:'JDMC' ,width:80, sortable:true, align:'center',lockDisplay: true },
       { title:'科目', name:'KMMC' ,width:80, sortable:true, align:'center',lockDisplay: true },
       { title:'课程名称', name:'KCXX_KCMC' ,width:80, sortable:true, align:'center',lockDisplay: true },
       { title:'课程描述', name:'KCXX_KCMS' ,width:80, sortable:true, align:'center',lockDisplay: true },
       { title:'状态', name:'SCBZ' ,width:80, sortable:true, align:'center',lockDisplay: true  },
       { title:'创建人', name:'KCXX_CJR' ,width:50,sortable:true, align:'center',lockDisplay: true,hidden:true },
       { title:'创建时间', name:'KCXX_CJSJ' ,width:100,sortable:true, align:'center',lockDisplay: true,hidden:true },
       { title:'修改人', name:'KCXX_GXR' ,width:50,sortable:true, align:'center',lockDisplay: true,hidden:true },
       { title:'修改时间', name:'KCXX_GXSJ' ,width:100,sortable:true, align:'center',lockDisplay: true,hidden:true }
   ];

    intheight = document.documentElement.clientHeight -$('#editRegion').height()-$('#selectRegion').height()-80;
   if(intheight<100){
	   intheight = 100;
   }

   mmg = $('.mmg').mmGrid({
         height: intheight
        ,cols: cols
        ,url: '<%=basePath%>/Servlet1090110'
        ,method: 'post'
        ,params:{CMD : "<%=Servlet1090110.CMD_SELECT%>"}
        ,remoteSort:true
        ,sortName: 'KCXX_KCID'
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
		   $('#selectEditSsjd').val(arrList[0].KCXX_KCJD);
			$('#selectEditKmmc').val(arrList[0].KCXX_KMID);
			$('#txtEditKcms').val(arrList[0].KCXX_KCMS);
			setButtonStatus("4");
	   } else {
		   $('#selectEditSsjd').val("000");
		   $('#selectEditKmmc').val("000");
		   $('#txtEditKcms').val("");
		   setButtonStatus(2);//未选中行,则只有新增按钮可用
	   }
   }).on('click', ':checkbox', function(){ //选择checkbox
	   if(this.checked == true){
		   var arrList = mmg.selectedRows();
		   if(arrList.length>0){
			   $('#selectEditSsjd').val(arrList[0].KCXX_KCJD);
				$('#selectEditKmmc').val(arrList[0].KCXX_KMID);
				$('#txtEditKcms').val(arrList[0].KCXX_KCMS);
				setButtonStatus("4");
		   }
	   }else{
		   $('#selectEditSsjd').val("000");
		   $('#selectEditKmmc').val("000");
		   $('#txtEditKcms').val("");
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
		   $('#selectEditSsjd').val(arrList[0].KCXX_KCJD);
			$('#selectEditKmmc').val(arrList[0].KCXX_KMID);
			$('#txtEditKcms').val(arrList[0].KCXX_KCMS);
			setButtonStatus("4");
	   } else {
		   $('#selectEditSsjd').val("000");
		   $('#selectEditKmmc').val("000");
		   $('#txtEditKcms').val("");
		   setButtonStatus(2);//未选中行,则只有新增按钮可用
	   }
    });
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
    		if(funEditCheck(arrList[0].KCXX_KCID)==false) return;
    		layer.confirm('是否修改选中数据？', function() {
	    		if(updateData(arrList[0].KCXX_KCID)==true){
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
 	   layer.confirm('是否删除选中数据？', function() {
	 	   if(deleteData(arrList[0].KCXX_KCID)==true){
	 		   //重新查询数据
	 		   loadGridByBean();
	 		   setButtonStatus("2");
	 		   mmg.deselect('all');
	 	   }
	   });
    });
    loadSearchSelect($("#selectSsjd"),"TYPE_SSJD","所属阶段");
    loadSearchSelect($("#selectKmmc"),"TYPE_KMMC","科目名称");

    loadEditSelect($("#selectEditSsjd"),"TYPE_SSJD","所属阶段");
    loadEditSelect($("#selectEditKmmc"),"TYPE_KMMC","科目名称");

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

function makeBeanIn(strKCXX_KCID,strKCXX_KCJD,strJDMC,strKCXX_KMID,strKMMC,strKCXX_KCMS){
    this.KCXX_KCID = strKCXX_KCID;
    this.KCXX_KCJD = strKCXX_KCJD;
    this.JDMC = strJDMC;
    this.KCXX_KMID = strKCXX_KMID;
    this.KMMC = strKMMC;
    this.KCXX_KCMS = strKCXX_KCMS;
}
//加载数据
function loadGridByBean(){
	var beanIn = new makeBeanIn(
			"",
			$('#selectSsjd').val(),
			"",
			$('#selectKmmc').val(),
			"",
			$('#txtSelectKcms').val()
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
			$('#selectEditSsjd').val(),
			$('#selectEditSsjd').find("option:selected").text(),
			$('#selectEditKmmc').val(),
			$('#selectEditKmmc').find("option:selected").text(),
			$('#txtEditKcms').val()
	);
	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/Servlet1090110",
		data: {
			CMD    : "<%=Servlet1090110.CMD_INSERT%>",
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
function updateData(dataId){
	var blnRet = false;
	var beanIn = new makeBeanIn(
			dataId,
			$('#selectEditSsjd').val(),
			$('#selectEditSsjd').find("option:selected").text(),
			$('#selectEditKmmc').val(),
			$('#selectEditKmmc').find("option:selected").text(),
			$('#txtEditKcms').val()
	);
    $.ajax({
      async     : false,
      type      : "post",
      dataType  : "json",
      url: "<%=basePath%>/Servlet1090110",
      data: {
         CMD    : "<%=Servlet1090110.CMD_UPDATE%>",
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
function deleteData(dataId){
	var blnRet = false;
	var beanIn = new makeBeanIn(
			dataId,"","","","",""
	);
	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/Servlet1090110",
		data: {
			CMD    : "<%=Servlet1090110.CMD_DELETE%>",
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
		$('#selectEditSsjd').attr("disabled","disabled");
		$('#selectEditKmmc').attr("disabled","disabled");
		$('#txtEditKcms').attr("disabled","disabled");

		$('#selectEditSsjd').val("000");
		$('#selectEditKmmc').val("000");
		$('#txtEditKcms').val("");
	} else if (strFlag.substring(0, 1) == "3") {
		//新增/修改
		$('#btnAdd').attr("disabled","disabled");
		$('#btnUpd').attr("disabled", "disabled");
		$('#btnDel').attr("disabled", "disabled");
		$('#btnSave').removeAttr("disabled");
		$('#btnCancel').removeAttr("disabled");
		if (strFlag == "31") {//新增
			$('#selectEditSsjd').removeAttr("disabled");
			$('#selectEditKmmc').removeAttr("disabled");
			$('#txtEditKcms').removeAttr("disabled");

			$('#selectEditSsjd').val("000");
			$('#selectEditKmmc').val("000");
			$('#txtEditKcms').val("");
			$('#selectEditSsjd').focus();
		} else if (strFlag == "32") {//修改
			$('#selectEditSsjd').removeAttr("disabled");
			$('#selectEditKmmc').removeAttr("disabled");
			$('#txtEditKcms').removeAttr("disabled");
			$('#selectEditSsjd').focus();
		} else if (strFlag == "32") {//删除
			$('#selectEditSsjd').attr("disabled","disabled");
			$('#selectEditKmmc').attr("disabled","disabled");
			$('#txtEditKcms').attr("disabled","disabled");
		}
	} else if (strFlag == "4") {//选中行
		$('#btnAdd').removeAttr("disabled");
		$('#btnUpd').removeAttr("disabled");
		$('#btnDel').removeAttr("disabled");
		$('#btnSave').attr("disabled", "disabled");
		$('#btnCancel').attr("disabled", "disabled");
		$('#selectEditSsjd').attr("disabled","disabled");
		$('#selectEditKmmc').attr("disabled","disabled");
		$('#txtEditKcms').attr("disabled","disabled");
	}
}
//验证编辑输入数据
function funEditCheck(KCID) {
	if (optionFlag == "Add" || optionFlag == "Upd") {
		if ($('#selectEditSsjd').val() == "000") {
			layer.alert('请选择所属阶段！', 0, '友情提示', function() {
				$('#selectEditSsjd').focus();
				layer.close(layer.index);
			});
			return false;
		}
		if ($('#selectEditKmmc').val() == "000") {
			layer.alert('请选择科目名称！', 0, '友情提示', function() {
				$('#selectEditKmmc').focus();
				layer.close(layer.index);
			});
			return false;
		}
		if (optionFlag == "Add") {//新增：判断是否课程信息已存在
			if (checkExist() == true) {
				layer.alert('课程信息已存在，无法重复新增！', 0, '友情提示', function() {
					layer.close(layer.index);
				});
				return false;
			}
		} else if (optionFlag == "Upd") {//修改：判断是否课程信息已存在
			if (checkExist(KCID) == true) {
				layer.alert('课程信息已存在，无需再次修改！', 0, '友情提示', function() {
					layer.close(layer.index);
				});
				return false;
			}
		}
	}
	return true;
}
//验证数据重复
function checkExist(KCID){
	var blnRet = false;
	var beanIn = new makeBeanIn(
	        KCID,
			$('#selectEditSsjd').val(),
			"",
			$('#selectEditKmmc').val(),
			"",""
	);
	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/Servlet1090110",
		data: {
			CMD    : "<%=Servlet1090110.CMD_CHK_EXIST%>",
			BeanIn : JSON.stringify(beanIn)
		},
		complete : function(response) {
		},
		success : function(response) {
			var strResult = response[0];
			if (strResult == "DATA_EXIST") {
				blnRet = true;
			} else if (strResult == "DATA_NOT_EXIST") {
				blnRet = false;
			} else {
				layer.msg('提示：验证新增数据重复时出错！', 1, 0);
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
				<th style="width:80px">所属阶段</th>
				<td><select id="selectSsjd" style="width: 100px"></select></td>
				<th style="width:80px">科目名称</th>
				<td><select id="selectKmmc" style="width: 100px"></select></td>
				<th style="width:100px">课程描述</th>
				<td><input id="txtSelectKcms" name="课程描述" maxlength="20" /></td>
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
				<th style="width:80px">所属阶段</th>
				<td><select id="selectEditSsjd" style="width: 100px"></select></td>
				<th style="width:80px">科目名称</th>
				<td><select id="selectEditKmmc" style="width: 100px"></select></td>
				<th style="width:100px">课程描述</th>
				<td><input id="txtEditKcms" name="课程描述" maxlength="20" /></td>
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