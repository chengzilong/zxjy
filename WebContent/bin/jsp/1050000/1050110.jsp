<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.xsjy.servlet.servlet1050000.Servlet1050110"%> 
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
<title>临时报名</title>
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
       { title:'学生姓名', name:'LSBM_XSXM' ,width:80, sortable:true, align:'center',lockDisplay: true },
       { title:'学生电话', name:'LSBM_XSDH' ,width:80, sortable:true, align:'center',lockDisplay: true },
       { title:'所选课程', name:'KCMC' ,width:60, sortable:true, align:'center',lockDisplay: true },
       { title:'是否回访', name:'SFHF' ,width:50, sortable:true, align:'center',lockDisplay: true },
       { title:'备注信息', name:'LSBM_BZXX' ,width:200, sortable:true, align:'center',lockDisplay: true },
       { title:'登记人', name:'LSBM_GXR' ,width:50,sortable:true, align:'center',lockDisplay: true },
       { title:'登记时间', name:'LSBM_GXSJ' ,width:100,sortable:true, align:'center',lockDisplay: true },
       { title:'修改人', name:'LSBM_GXR' ,width:50,sortable:true, align:'center',lockDisplay: true,hidden:true },
       { title:'修改时间', name:'LSBM_GXSJ' ,width:100,sortable:true, align:'center',lockDisplay: true,hidden:true }
   ];
             
    intheight = document.documentElement.clientHeight -$('#editRegion').height()-$('#selectRegion').height()-80; 
   if(intheight<100){
	   intheight = 100;
   }

   mmg = $('.mmg').mmGrid({
         height: intheight
        ,cols: cols
        ,url: '<%=basePath%>/Servlet1050110'
        ,method: 'post'
        ,params:{CMD : "<%=Servlet1050110.CMD_SELECT%>"}
        ,remoteSort:true
        ,sortName: 'LSBM_LSBMID'
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
	   var arrList = mmg.selectedRows();
	   if(arrList.length>0){
         $('#txtEditXsxm').val(arrList[0].LSBM_XSXM);
         $('#txtEditXsdh').val(arrList[0].LSBM_XSDH);
         $('#selectEditKcmc').val(arrList[0].LSBM_KCID);
         $('#selectEditSfhf').val(arrList[0].LSBM_SFHF);
         $('#txtEditBzxx').val(arrList[0].LSBM_BZXX);
         setButtonStatus("4");   
	   } else {
		   $('#txtEditXsxm').val("");
		   $('#txtEditXsdh').val("");
		   $('#selectEditKcmc').val("000");
		   $('#selectEditSfhf').val("0");
		   $('#txtEditBzxx').val("");
		   setButtonStatus(2);//未选中行,则只有新增按钮可用
	   }
   }).on('click', ':checkbox', function(){ //选择checkbox
	   if(this.checked == true){
		   var arrList = mmg.selectedRows();
		   if(arrList.length>0){
			   $('#txtEditXsxm').val(arrList[0].LSBM_XSXM);
		         $('#txtEditXsdh').val(arrList[0].LSBM_XSDH);
		         $('#selectEditKcmc').val(arrList[0].LSBM_KCID);
		         $('#selectEditSfhf').val(arrList[0].LSBM_SFHF);
		         $('#txtEditBzxx').val(arrList[0].LSBM_BZXX);
		         setButtonStatus("4");   
		   }
	   }else{
		   $('#txtEditXsxm').val("");
		   $('#txtEditXsdh').val("");
		   $('#selectEditKcmc').val("000");
		   $('#selectEditSfhf').val("0");
		   $('#txtEditBzxx').val("");
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
       var arrList = mmg.selectedRows();
	   if(arrList.length>0){
         $('#txtEditXsxm').val(arrList[0].LSBM_XSXM);
         $('#txtEditXsdh').val(arrList[0].LSBM_XSDH);
         $('#selectEditKcmc').val(arrList[0].LSBM_KCID);
         $('#selectEditSfhf').val(arrList[0].LSBM_SFHF);
         $('#txtEditBzxx').val(arrList[0].LSBM_BZXX);
         setButtonStatus("4");   
	   } else {
		   $('#txtEditXsxm').val("");
		   $('#txtEditXsdh').val("");
		   $('#selectEditKcmc').val("000");
		   $('#selectEditSfhf').val("0");
		   $('#txtEditBzxx').val("");
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
    		layer.confirm('是否增加交费信息？', function() {
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
	    		if(updateData(arrList[0].LSBM_LSBMID)==true){
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
 		 if(deleteData(arrList[0].LSBM_LSBMID)==true){
 		   //重新查询数据
 		   loadGridByBean();
 		   setButtonStatus("2");
 		   mmg.deselect('all');
 	   }  
 	  });
    });
    
    loadGridByBean();
    
    loadEditSelect($("#selectEditKcmc"),"TYPE_KCMC","课程名称");
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

function makeBeanIn(strLSBM_LSBMID,strLSBM_XSXM,strLSBM_XSDH,strLSBM_KCID,strLSBM_SFHF,strLSBM_BZXX){
    this.LSBM_LSBMID = strLSBM_LSBMID;
    this.LSBM_XSXM = strLSBM_XSXM;
    this.LSBM_XSDH = strLSBM_XSDH;
    this.LSBM_KCID = strLSBM_KCID;
    this.LSBM_SFHF = strLSBM_SFHF;
    this.LSBM_BZXX = strLSBM_BZXX;
}
//加载数据
function loadGridByBean(){
	var beanIn = new makeBeanIn(
			"",
			$('#txtSelectXsxm').val(),
			$('#txtSelectXsdh').val(),
			"",
			$('#selectSfhf').val(),
			""
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
			$('#txtEditXsxm').val(),
			$('#txtEditXsdh').val(),
			$('#selectEditKcmc').val(),
			$('#selectEditSfhf').val(),
			$('#txtEditBzxx').val()
	);
	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/Servlet1050110",
		data: {
			CMD    : "<%=Servlet1050110.CMD_INSERT%>",
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
			$('#txtEditXsxm').val(),
			$('#txtEditXsdh').val(),
			$('#selectEditKcmc').val(),
			$('#selectEditSfhf').val(),
			$('#txtEditBzxx').val()
	);
    $.ajax({
      async     : false,
      type      : "post",
      dataType  : "json",
      url: "<%=basePath%>/Servlet1050110",
      data: {
         CMD    : "<%=Servlet1050110.CMD_UPDATE%>",
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
		url: "<%=basePath%>/Servlet1050110",
		data: {
			CMD    : "<%=Servlet1050110.CMD_DELETE%>",
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
		$('#txtEditXsxm').attr("disabled","disabled");
		$('#txtEditXsdh').attr("disabled","disabled");
		$('#selectEditKcmc').attr("disabled","disabled");
		$('#selectEditSfhf').attr("disabled","disabled");
		$('#txtEditBzxx').attr("disabled","disabled");
		
		$('#txtEditXsxm').val("");
		$('#txtEditXsdh').val("");
		$('#selectEditKcmc').val("000");
		$('#selectEditSfhf').val("0");
		$('#txtEditBzxx').val("");
	} else if (strFlag.substring(0, 1) == "3") {
		//新增/修改
		$('#btnAdd').attr("disabled","disabled");
		$('#btnUpd').attr("disabled", "disabled");
		$('#btnDel').attr("disabled", "disabled");
		$('#btnSave').removeAttr("disabled");
		$('#btnCancel').removeAttr("disabled");
		if (strFlag == "31") {//新增
			$('#txtEditXsxm').removeAttr("disabled");
			$('#txtEditXsdh').removeAttr("disabled");
			$('#selectEditKcmc').removeAttr("disabled");
			$('#selectEditSfhf').removeAttr("disabled");
			$('#txtEditBzxx').removeAttr("disabled");
			
			$('#txtEditXsxm').val("");
			$('#txtEditXsdh').val("");
			$('#selectEditKcmc').val("000");
			$('#selectEditSfhf').val("0");
			$('#txtEditBzxx').val("");
			$('#txtEditXsxm').focus();
		} else if (strFlag == "32") {//修改
			$('#txtEditXsxm').removeAttr("disabled");
			$('#txtEditXsdh').removeAttr("disabled");
			$('#selectEditKcmc').removeAttr("disabled");
			$('#selectEditSfhf').removeAttr("disabled");
			$('#txtEditBzxx').removeAttr("disabled");
			$('#txtEditXsxm').focus();
		} else if (strFlag == "32") {//删除
			$('#txtEditXsxm').attr("disabled","disabled");
			$('#txtEditXsdh').attr("disabled","disabled");
			$('#selectEditKcmc').attr("disabled","disabled");
			$('#selectEditSfhf').attr("disabled","disabled");
			$('#txtEditBzxx').attr("disabled","disabled");
		}
	} else if (strFlag == "4") {//选中行
		$('#btnAdd').removeAttr("disabled");
		$('#btnUpd').removeAttr("disabled");
		$('#btnDel').removeAttr("disabled");
		$('#btnSave').attr("disabled", "disabled");
		$('#btnCancel').attr("disabled", "disabled");
		$('#txtEditXsxm').attr("disabled","disabled");
		$('#txtEditXsdh').attr("disabled","disabled");
		$('#selectEditKcmc').attr("disabled","disabled");
		$('#selectEditSfhf').attr("disabled","disabled");
		$('#txtEditBzxx').attr("disabled","disabled");
	}
}
//验证编辑输入数据
function funEditCheck() {
	if (optionFlag == "Add" || optionFlag == "Upd") {
		if ($('#txtEditXsxm').val() == "") {
			layer.alert('请输入学生姓名！', 0, '友情提示', function() {
				$('#txtEditXsxm').focus();
				layer.close(layer.index);
			});
			return false;
		}
		if ($('#txtEditXsdh').val() == "") {
			layer.alert('请输入学生电话！', 0, '友情提示', function() {
				$('#txtEditXsdh').focus();
				layer.close(layer.index);
			});
			return false;
		}
		if ($('#selectEditKcmc').val() == "000") {
		    layer.alert('请选择所选课程！', 0, '友情提示', function() {
				$('#selectEditKcmc').focus();
				layer.close(layer.index);
			});
			return false;
		}
		if (optionFlag == "Add") {//新增：判断是否临时报名信息已存在
			if (checkExist() == true) {
				layer.alert('临时报名信息已存在，无法重复新增！', 0, '友情提示');
				return false;
			}
		} else if (optionFlag == "Upd") {//修改：判断是临时报名信息已存在
// 			if (checkExist() == true) {
// 				alert("临时报名信息已存在，无需再次修改！");
// 				return false;
// 			}
		}
	}
	return true;
}
//验证数据重复
function checkExist(){
	var blnRet = false;
	var beanIn = new makeBeanIn(
			"",
			$('#txtEditXsxm').val(),
			$('#txtEditXsdh').val(),
			$('#selectEditKcmc').val()
	);
	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/Servlet1050110",
		data: {
			CMD    : "<%=Servlet1050110.CMD_CHK_EXIST%>",
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
				layer.msg('提示：验证数据重复时出错！', 1, 0);
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
				<th style="width:100px">是否回访</th>
				<td>
					<select id="selectSfhf" style="width: 100px">
						<option value="000">所有</option>
						<option value="0">未回访</option>
						<option value="1">已回访</option>
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
		<legend>编辑</legend>
		<table id="detailCanvas" class="eTable6">
			<tr>
				<th style="width:100px">学生姓名</th>
				<td><input id="txtEditXsxm" name="学生姓名" maxlength="20" /></td>
				<th style="width:100px">学生电话</th>
				<td><input id="txtEditXsdh" name="学生电话" maxlength="20" /></td>
				<th style="width:100px">所选课程</th>
				<td>
					<select id="selectEditKcmc" style="width: 100px"></select>
				</td>
				<th style="width:100px">是否回访</th>
				<td>
					<select id="selectEditSfhf" style="width: 100px">
						<option value="0" selected="selected">未回访</option>
						<option value="1">已回访</option>
					</select>
				</td>
			</tr>
			<tr>
				<th style="width:100px">备注信息</th>
				<td colspan="8"><input id="txtEditBzxx" name="备注信息" maxlength="200" style="width: 820px" /></td>
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