<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.xsjy.servlet.servlet3030000.Servlet3030110"%>
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
<title>我的课程</title>
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
//初始化表格
$(document).ready(function(){
   var cols = [
       { title:'课程名称', name:'KCMC' ,width:100, sortable:true, align:'center',lockDisplay: true },
       { title:'课程类型', name:'LXMC' ,width:80, sortable:true, align:'center',lockDisplay: true },
       { title:'上课时段', name:'SDMC' ,width:100, sortable:true, align:'center',lockDisplay: true  },
       { title:'学时', name:'KCFY_XS' ,width:100, sortable:true, align:'center',lockDisplay: true  },
       { title:'费用', name:'KCFY_FY' ,width:150, sortable:true, align:'center',lockDisplay: true  }
   ];

   intheight = document.documentElement.clientHeight -$('#editRegion').height()-$('#selectRegion').height()-80;
   if(intheight<100){
	   intheight = 100;
   }

   mmg = $('.mmg').mmGrid({
         height: intheight
        ,cols: cols
        ,url: '<%=basePath%>/Servlet3030110'
        ,method: 'post'
        ,params:{CMD : "<%=Servlet3030110.CMD_SELECT%>"}
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

   mmg.on('rowSelected', function(e, item, rowIndex, colIndex){//选择数据行
	   var arrList = mmg.row(rowIndex);
	   $('#txtEditFYID').val(arrList.KCFY_FYID);
	   $('#selectEditKCMC').val(arrList.KCFY_XXID);
       $("#selectEditKCLX  option[value='"+arrList.KCFY_LXID+"'] ").attr("selected",true);
       $("#selectEditSKSD  option[value='"+arrList.KCFY_SDID+"'] ").attr("selected",true);
       $('#txtEditXS').val(arrList.KCFY_XS);
       $('#txtEditFY').val(arrList.KCFY_FY);
       $('#txtFY').val(arrList.KCFY_FY);
       setButtonStatus("4");

   }).on('click', ':checkbox', function(){ //选择checkbox
	   if(this.checked == true){
		   var arrList = mmg.selectedRows();
		   if(arrList.length>0){
			 $('#txtEditFYID').val(arrList[0].KCFY_FYID);
			 $('#selectEditKCMC').val(arrList[0].KCFY_XXID);
	         $("#selectEditKCLX  option[value='"+arrList[0].KCFY_LXID+"'] ").attr("selected",true);
	         $("#selectEditSKSD  option[value='"+arrList[0].KCFY_SDID+"'] ").attr("selected",true);
	         $('#txtEditXS').val(arrList[0].KCFY_XS);
	         $('#txtEditFY').val(arrList[0].KCFY_FY);
	         $('#txtFY').val(arrList[0].KCFY_FY);
	         setButtonStatus("4");
		   }
	   }else{
			   $('#txtEditFYID').val("");
			   $('#selectEditKCMC').val("000");
		       $("#selectEditKCLX  option[value='000'] ").attr("selected",true);
		       $("#selectEditSKSD  option[value='000'] ").attr("selected",true);
		       $('#txtEditXS').val("");
		       $('#txtEditFY').val("");
		       $('#txtFY').val("");
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
	       $('#txtEditFYID').val(arrList.KCFY_FYID);
	       $('#selectEditKCMC').val(arrList.KCFY_XXID);
	       $('#selectEditKCLX').val(arrList.KCFY_LXID);
	       $('#selectEditSKSD').val(arrList.KCFY_SDID);
	       //$("#selectEditKCLX option[value='"+arrList.KCFY_LXID+"'] ").attr("selected",true);
	       //$("#selectEditSKSD option[value='"+arrList.KCFY_SDID+"'] ").attr("selected",true);
	       $('#txtEditXS').val(arrList.KCFY_XS);
	       $('#txtEditFY').val(arrList.KCFY_FY);
	       $('#txtFY').val(arrList.KCFY_FY);
      	   setButtonStatus("4");
       } else {
    	   $('#txtEditFYID').val("");
		   $('#selectEditKCMC').val("000");
		   $('#selectEditKCLX').val("000");
		   $('#selectEditSKSD').val("000");
	       //$("#selectEditKCLX option[value='000'] ").attr("selected",true);
	       //$("#selectEditSKSD option[value='000'] ").attr("selected",true);
	       $('#txtEditXS').val("");
	       $('#txtEditFY').val("");
	       $('#txtFY').val("");
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
	   layer.confirm('是否删除课程费用信息？', function() {
		   if(deleteKCFYCode()==true){
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
    		layer.confirm('是否增加课程费用信息？', function() {
    			if(insertKCFYCode()==true){
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
    		layer.confirm('是否修改课程费用信息？', function() {
    			if(updateKCFYCode()==true){
	   	             //重新查询数据
	   	             loadGridByBean();
	   	             setButtonStatus("2");
	   	             mmg.deselect('all');
	   	        }
    		});
    	}
    });
    loadGridByBean();
    loadEditSelect($("#selectEditKCMC"),"TYPE_KCMC","课程名称");
    loadEditSelect($("#selectEditKCLX"),"TYPE_KCLX","课程类型");
    loadEditSelect($("#selectEditSKSD"),"TYPE_SKSD","上课时段");
    loadSearchSelect($("#txtSelKCMC"),"TYPE_KCMC","课程名称");
    loadSearchSelect($("#txtSelKCLX"),"TYPE_KCLX","课程类型");
    loadSearchSelect($("#txtSelSKSD"),"TYPE_SKSD","上课时段");
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

function makeBeanIn(strKCMC,strKCLX,strSKSD){
	this.KCFY_XXID = strKCMC;
	this.KCFY_LXID = strKCLX;
    this.KCFY_SDID = strSKSD;
}
function makeBeanInadd(strKCMC,strKCLX,strSKSD,strXS,strFY){
	this.KCFY_XXID = strKCMC;
    this.KCFY_LXID = strKCLX;
    this.KCFY_SDID = strSKSD;
    this.KCFY_XS = strXS;
    this.KCFY_FY = strFY;
}
function makeBeanInedit(strFYID,strKCMC,strKCLX,strSKSD,strXS,strFY){
    this.KCFY_FYID = strFYID;
    this.KCFY_XXID = strKCMC;
    this.KCFY_LXID = strKCLX;
    this.KCFY_SDID = strSKSD;
    this.KCFY_XS = strXS;
    this.KCFY_FY = strFY;
}
function makeBeanIndel(strFYID){
    this.KCFY_FYID = strFYID;
}
function loadGridByBean(){
	var beanIn = new makeBeanIn(
			$('#txtSelKCMC').val(),
			$('#txtSelKCLX').val(),
			$('#txtSelSKSD').val()
	);
	//重新查询数据
	mmg.load({
    	beanLoad  :  JSON.stringify(beanIn)
	});
}

function insertKCFYCode(){
	var blnRet = false;
	var beanIn = new makeBeanInadd(
           $('#selectEditKCMC').val(),
           $('#selectEditKCLX').val(),
           $('#selectEditSKSD').val(),
           $('#txtEditXS').val(),
           $('#txtEditFY').val()
	);
	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/Servlet3030110",
		data: {
			CMD    : "<%=Servlet3030110.CMD_INSERT%>",
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
function updateKCFYCode(){
	var blnRet = false;
	var beanIn = new makeBeanInedit(
		   $('#txtEditFYID').val(),
           $('#selectEditKCMC').val(),
           $('#selectEditKCLX').val(),
           $('#selectEditSKSD').val(),
           $('#txtEditXS').val(),
           $('#txtEditFY').val()
	);

    $.ajax({
      async     : false,
      type      : "post",
      dataType  : "json",
      url: "<%=basePath%>/Servlet3030110",
      data: {
         CMD    : "<%=Servlet3030110.CMD_UPDATE%>",
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

function deleteKCFYCode(){
	var blnRet = false;
	var beanIn = new makeBeanIndel(
			$('#txtEditFYID').val()
	);

	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/Servlet3030110",
		data: {
			CMD    : "<%=Servlet3030110.CMD_DELETE%>",
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
		$('#selectEditKCMC').attr("disabled","disabled");
		$('#selectEditKCLX').attr("disabled","disabled");
		$('#selectEditSKSD').attr("disabled","disabled");
		$('#txtEditXS').attr("disabled","disabled");
		$('#txtEditFY').attr("disabled","disabled");


		$('#txtEditFYID').val("");
		$('#selectEditKCMC').val("000");
		$('#selectEditKCLX').val("000");
		$('#selectEditSKSD').val("000");
		$('#txtEditXS').val("");
		$('#txtEditFY').val("");
		$('#txtFY').val("");

	} else if (strFlag.substring(0, 1) == "3") {
		//新增/修改
		$('#btnAdd').attr("disabled","disabled");
		$('#btnUpd').attr("disabled", "disabled");
		$('#btnDel').attr("disabled", "disabled");
		$('#btnSave').removeAttr("disabled");
		$('#btnCancel').removeAttr("disabled");
		if (strFlag == "31") {//新增
			$('#selectEditKCMC').removeAttr("disabled");
			$('#selectEditKCLX').removeAttr("disabled");
			$('#selectEditSKSD').removeAttr("disabled");
			$('#txtEditXS').removeAttr("disabled");
			$('#txtEditFY').removeAttr("disabled");


			$('#txtEditFYID').val("");
			$('#selectEditKCMC').val("000");
			$('#selectEditKCLX').val("000");
			$('#selectEditSKSD').val("000");
			$('#txtEditXS').val("");
			$('#txtEditFY').val("");
			$('#txtFY').val("");
			$('#selectEditKCMC').focus();
		} else if (strFlag == "32") {//修改
			$('#selectEditKCMC').removeAttr("disabled");
			$('#selectEditKCLX').removeAttr("disabled");
			$('#selectEditSKSD').removeAttr("disabled");
			$('#txtEditXS').removeAttr("disabled");
			$('#txtEditFY').removeAttr("disabled");
			toFy();
			$('#selectEditKCMC').focus();
		} else if (strFlag == "33") {//删除

		}
	} else if (strFlag == "4") {//选中行
		$('#btnAdd').removeAttr("disabled");
		$('#btnUpd').removeAttr("disabled");
		$('#btnDel').removeAttr("disabled");
		$('#btnSave').attr("disabled", "disabled");
		$('#btnCancel').attr("disabled", "disabled");
		$('#selectEditKCMC').attr("disabled","disabled");
		$('#selectEditKCLX').attr("disabled","disabled");
		$('#selectEditSKSD').attr("disabled","disabled");
		$('#txtEditXS').attr("disabled","disabled");
		$('#txtEditFY').attr("disabled","disabled");
	}
}
//验证编辑输入数据
function funEditCheck() {
	if (optionFlag == "Add" || optionFlag == "Upd") {
		if ($('#selectEditKCMC').val() == "000") {
			layer.alert('请选择课程名称！', 0, '友情提示', function() {
				$('#selectEditKCMC').focus();
				layer.close(layer.index);
			});
			return false;
		}
		if ($('#selectEditKCLX').val() == "000") {
			layer.alert('请选择课程类型！', 0, '友情提示', function() {
				$('#selectEditKCLX').focus();
				layer.close(layer.index);
			});
			return false;
		}
		if ($('#selectEditSKSD').val() == "000") {
			layer.alert('请选择上课时段！', 0, '友情提示', function() {
				$('#selectEditSKSD').focus();
				layer.close(layer.index);
			});
			return false;
		}
		if ($('#txtEditXS').val() == "") {
			layer.alert('请输入学时！', 0, '友情提示', function() {
				$('#txtEditXS').focus();
				layer.close(layer.index);
			});
			return false;
		}
		if ($('#txtEditFY').val() == "") {
			layer.alert('请输入费用！', 0, '友情提示', function() {
				$('#txtEditFY').focus();
				layer.close(layer.index);
			});
			return false;
		}

	}
	return true;
}
//课程类型控制费用
function toFy() {
	if ($('#selectEditKCLX').val() == "1") {
		$('#txtEditFY').val("0");
		$('#txtEditFY').attr("disabled", "disabled");
	} else {
		$('#txtEditFY').val($('#txtFY').val());
		$('#txtEditFY').removeAttr("disabled");
	}
}
</script>
</head>
<body>
	<fieldset id = "selectRegion">
		<legend>查询条件</legend>
		<table>
			<tr>
				<th style="width:100px">课程名称</th>
				<td style="width:100px">
					<select id="txtSelKCMC"> </select>
				</td>
				<th style="width:100px">课程类型</th>
				<td style="width:100px">
					<select id="txtSelKCLX">
						<option value="000" selected>请选择</option>
					</select>
				</td>
				<th style="width:100px">上课时段</th>
				<td style="width:100px">
					<select id="txtSelSKSD">
						<option value="000" selected>请选择</option>
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
				<th style="width:100px">课程名称</th>
				<td style="width:100px">
					<select id="selectEditKCMC">
						<option value="000" selected>请选择</option>
					 </select>
				</td>
				<th style="width:100px">课程类型</th>
				<td style="width:100px">
					<select id="selectEditKCLX" onchange="toFy()">
						<option value="000" selected>请选择</option>
					</select>
				</td>
				<th style="width:100px">上课时段</th>
				<td style="width:100px">
					<select id="selectEditSKSD">
						<option value="000" selected>请选择</option>
					</select>
				</td>
				<th style="width:100px">学时</th>
				<td><input type="hidden" id="txtEditFYID" /><input id="txtEditXS" name="学时" maxlength="20" /></td>
				<th style="width:100px">费用</th>
				<td><input type="hidden"  id="txtFY" /><input id="txtEditFY" name="费用" maxlength="20" /></td>
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