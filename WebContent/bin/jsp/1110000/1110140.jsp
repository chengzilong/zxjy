<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.xsjy.servlet.servlet1110000.Servlet1110140"%> 
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
<title>个人账户</title>
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
       { title:'银行名称', name:'GRZH_YHMC' ,width:100, sortable:true, align:'center',lockDisplay: true },
       { title:'银行卡号', name:'GRZH_YHKH' ,width:80, sortable:true, align:'center',lockDisplay: true },
       { title:'创建时间', name:'GRZH_CJSJ' ,width:80, sortable:true, align:'center',lockDisplay: true }
   ];

   intheight = document.documentElement.clientHeight -$('#editRegion').height()-$('#selectRegion').height()-80; 
   if(intheight<100){
	   intheight = 100;
   }

   mmg = $('.mmg').mmGrid({
         height: intheight
        ,cols: cols
        ,url: '<%=basePath%>/Servlet1110140'
        ,method: 'post'
        ,params:{CMD : "<%=Servlet1110140.CMD_SELECT%>"}
        ,remoteSort:true
        ,sortName: 'GRZH_ZHID'
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
	   $('#txtEditZHID').val(arrList.GRZH_ZHID);
	   $('#txtEditYHMC').val(arrList.GRZH_YHMC);
       $('#txtEditYHKH').val(arrList.GRZH_YHKH);
       setButtonStatus("4");
       
   }).on('click', ':checkbox', function(){ //选择checkbox
	   if(this.checked == true){
		   var arrList = mmg.selectedRows();
		   if(arrList.length>0){
			   $('#txtEditZHID').val(arrList[0].GRZH_ZHID);
			   $('#txtEditYHMC').val(arrList[0].GRZH_YHMC);
		       $('#txtEditYHKH').val(arrList[0].GRZH_YHKH);
	         setButtonStatus("4");   
		   }
	   }else{
			  $('#txtEditZHID').val("");
			   $('#txtEditYHMC').val("");
		       $('#txtEditYHKH').val("");
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
	       $('#txtEditZHID').val(arrList.GRZH_ZHID);
		   $('#txtEditYHMC').val(arrList.GRZH_YHMC);
	       $('#txtEditYHKH').val(arrList.GRZH_YHKH);
      	   setButtonStatus("4"); 
       }else
       {
    	   $('#txtEditZHID').val("");
		   $('#txtEditYHMC').val("");
		   $('#txtEditYHKH').val("");
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
	   layer.confirm('是否删除账户信息？', function() {
		   if(deleteGRZHCode()==true){
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
    		layer.confirm('是否增加账户信息？', function() {
	    		if(insertGRZHCode()==true){
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
    		layer.confirm('是否修改账户信息？', function() {
	    		if(updateGRZHCode()==true){
		             //重新查询数据
		             loadGridByBean();
		             setButtonStatus("2");
		             mmg.deselect('all');
		        }
		    });
    	}
    	//setButtonStatus("1");  
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

function makeBeanIn(strYHMC){
	this.GRZH_YHMC = strYHMC;
}
function makeBeanInadd(strYHMC,strYHKH){
	this.GRZH_YHMC = strYHMC;
    this.GRZH_YHKH = strYHKH;  
}
function makeBeanInedit(strZHID,strYHMC,strYHKH){
    this.GRZH_ZHID = strZHID;
    this.GRZH_YHMC = strYHMC;
    this.GRZH_YHKH = strYHKH;  
}
function makeBeanIndel(strZHID){
    this.GRZH_ZHID = strZHID;  
}
function loadGridByBean(){
	var beanIn = new makeBeanIn(
			$('#txtSelYHMC').val()
	);
	//重新查询数据
	mmg.load({
    	beanLoad  :  JSON.stringify(beanIn)
	});
}

function insertGRZHCode(){
	var blnRet = false;
	var beanIn = new makeBeanInadd(
           $('#txtEditYHMC').val(),
           $('#txtEditYHKH').val() 
	);
	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/Servlet1110140",
		data: {
			CMD    : "<%=Servlet1110140.CMD_INSERT%>",
		    BeanIn : JSON.stringify(beanIn)
		},
		complete :function(response){},
		success: function(response){
			var strResault = response[0];
			if(strResault=="SUCCESS"){
				layer.msg('恭喜：新增账户信息成功！', 1, 9);
				blnRet = true;
			}else if(strResault=="FAILURE"){
				layer.msg('对不起：新增账户信息失败！', 1, 8);
				blnRet = false;
			}else{
				layer.msg('提示：新增账户信息出错！', 1, 0);
				blnRet = false;	
			}
		}
	});
	return blnRet;
}
function updateGRZHCode(){
	var blnRet = false;
	var beanIn = new makeBeanInedit(
		   $('#txtEditZHID').val(),
           $('#txtEditYHMC').val(),
           $('#txtEditYHKH').val()
	);
   
    $.ajax({
      async     : false,
      type      : "post",
      dataType  : "json",
      url: "<%=basePath%>/Servlet1110140",
      data: {
         CMD    : "<%=Servlet1110140.CMD_UPDATE%>",
         BeanIn : JSON.stringify(beanIn)
      },
      complete :function(response){},
      success: function(response){
          var strResault = response[0];
          if(strResault=="SUCCESS"){
         	 layer.msg('恭喜：修改账户信息成功！', 1, 9);
             blnRet = true;
          }else if(strResault=="FAILURE"){
             layer.msg('对不起：修改账户信息失败！', 1, 8);
             blnRet = false;
          }else{
          	 layer.msg('提示：修改账户信息出错！', 1, 0);
          	 blnRet = false;
          }
      }
   });
   return blnRet;
}

function deleteGRZHCode(){
	var blnRet = false;
	var beanIn = new makeBeanIndel(
			$('#txtEditZHID').val()
	);

	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/Servlet1110140",
		data: {
			CMD    : "<%=Servlet1110140.CMD_DELETE%>",
			BeanIn : JSON.stringify(beanIn)
		},
		complete : function(response) {},
		success : function(response) {
			var strResault = response[0];
			if (strResault == "SUCCESS") {
				layer.msg('恭喜：删除账户信息成功！', 1, 9);
				blnRet = true;
			} else if (strResault == "ERROR") {
			    layer.msg('对不起：删除账户信息失败！', 1, 8);
				blnRet = false;
			} else {
				layer.msg('提示：删除账户信息出错！', 1, 0);
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
		$('#txtEditYHMC').attr("disabled","disabled");
		$('#txtEditYHKH').attr("disabled","disabled");
	
		
		$('#txtEditZHID').val("");
		$('#txtEditYHMC').val("");
		$('#txtEditYHKH').val("");

	} else if (strFlag.substring(0, 1) == "3") {
		//新增/修改
		$('#btnAdd').attr("disabled","disabled");
		$('#btnUpd').attr("disabled", "disabled");
		$('#btnDel').attr("disabled", "disabled");
		$('#btnSave').removeAttr("disabled");
		$('#btnCancel').removeAttr("disabled");
		if (strFlag == "31") {//新增
			$('#txtEditYHMC').removeAttr("disabled");
			$('#txtEditYHKH').removeAttr("disabled");
		
			
			$('#txtEditZHID').val("");			
			$('#txtEditYHMC').val("");
			$('#txtEditYHKH').val("");
			$('#txtEditYHMC').focus();
		} else if (strFlag == "32") {//修改
			$('#txtEditYHMC').removeAttr("disabled");
			$('#txtEditYHKH').removeAttr("disabled");
			$('#txtEditYHMC').focus();
		} else if (strFlag == "33") {//删除
		
		}
	} else if (strFlag == "4") {//选中行
		$('#btnAdd').removeAttr("disabled");
		$('#btnUpd').removeAttr("disabled");
		$('#btnDel').removeAttr("disabled");
		$('#btnSave').attr("disabled", "disabled");
		$('#btnCancel').attr("disabled", "disabled");
		$('#txtEditYHMC').attr("disabled","disabled");
		$('#txtEditYHKH').attr("disabled","disabled");
	}
}
//验证编辑输入数据
function funEditCheck() {
	if (optionFlag == "Add" || optionFlag == "Upd") {
		if ($('#txtEditYHMC').val() == "") {
			layer.alert('请输入银行名称！', 0, '友情提示', function() {
				$('#txtEditYHMC').focus();
				layer.close(layer.index);
			});
			return false;
		}	
		if ($('#txtEditYHKH').val() == "") {
			layer.alert('请输入银行卡号！', 0, '友情提示', function() {
				$('#txtEditYHKH').focus();
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
	<fieldset id = "selectRegion">
		<legend>查询条件</legend>
		<table>
			<tr>
				<th style="width:100px">银行名称</th>
				<td style="width:100px">
					<input id="txtSelYHMC" name="银行名称" maxlength="20" />
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
				<th style="width:100px">银行名称</th>	
				<td><input type="hidden" id="txtEditZHID" /><input id="txtEditYHMC" name="银行名称" maxlength="20" /></td>
				<th style="width:100px">银行卡号</th>	
				<td><input id="txtEditYHKH" name="银行卡号" maxlength="20" /></td>
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