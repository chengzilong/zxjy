<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.xsjy.servlet.servlet1090000.Servlet1090210"%> 
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
<title>行政区域</title>
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
       { title:'区域编码', name:'XZQY_QYID' ,width:100, sortable:true, align:'center',lockDisplay: true  },
       { title:'区域名称', name:'XZQY_QYMC' ,width:100, sortable:true, align:'center',lockDisplay: true },
       { title:'区域级别', name:'QYJB' ,width:80, sortable:true, align:'center',lockDisplay: true },
       { title:'上级区域', name:'SJQY' ,width:100, sortable:true, align:'center',lockDisplay: true  }
   ];

   intheight = document.documentElement.clientHeight -$('#editRegion').height()-$('#selectRegion').height()-80; 
   if(intheight<100){
	   intheight = 100;
   }

   mmg = $('.mmg').mmGrid({
         height: intheight
        ,cols: cols
        ,url: '<%=basePath%>/Servlet1090210'
        ,method: 'post'
        ,params:{CMD : "<%=Servlet1090210.CMD_SELECT%>"}
        ,remoteSort:true
        ,sortName: 'XZQY_QYID'
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
	   $('#txtEditQYID').val(arrList.XZQY_QYID);
	   $('#txtYBM').val(arrList.XZQY_QYID);
	   $('#txtEditQYMC').val(arrList.XZQY_QYMC);
       $("#selectEditQYJB  option[value='"+arrList.XZQY_QYJB+"'] ").attr("selected",true);
       getSJQY(arrList.XZQY_QYJB,arrList.XZQY_SJID);
       //$("#selectEditSJQY  option[value='"+arrList.XZQY_SJID+"'] ").attr("selected",true);
       setButtonStatus("4");
       
   }).on('click', ':checkbox', function(){ //选择checkbox
	   if(this.checked == true){
		   var arrList = mmg.selectedRows();
		   if(arrList.length>0){
			 	   $('#txtEditQYID').val(arrList[0].XZQY_QYID);
			 	   $('#txtYBM').val(arrList[0].XZQY_QYID);
				   $('#txtEditQYMC').val(arrList[0].XZQY_QYMC);
			       $("#selectEditQYJB  option[value='"+arrList[0].XZQY_QYJB+"'] ").attr("selected",true);
			       getSJQY(arrList[0].XZQY_QYJB,arrList[0].XZQY_SJID);
			       //$("#selectEditSJQY  option[value='"+arrList[0].XZQY_SJID+"'] ").attr("selected",true);
	         setButtonStatus("4");   
		   }
	   }else{
			   $('#txtEditQYID').val("");
			   $('#txtYBM').val("");
			   $('#txtEditQYMC').val("");
		       $("#selectEditQYJB  option[value='000'] ").attr("selected",true);
		       $("#selectEditSJQY  option[value='000'] ").attr("selected",true);
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
	       $('#txtEditQYID').val(arrList.XZQY_QYID);
	       $('#txtYBM').val(arrList.XZQY_QYID);
		   $('#txtEditQYMC').val(arrList.XZQY_QYMC);
	       $("#selectEditQYJB  option[value='"+arrList.XZQY_QYJB+"'] ").attr("selected",true);
	       getSJQY(arrList.XZQY_QYJB,arrList.XZQY_SJID);
	       //$("#selectEditSJQY  option[value='"+arrList.XZQY_SJID+"'] ").attr("selected",true);
      	   setButtonStatus("4"); 
       }else
       {
    	   $('#txtEditQYID').val("");
    	   $('#txtYBM').val("");
		   $('#txtEditQYMC').val("");
	       $("#selectEditQYJB  option[value='000'] ").attr("selected",true);
	       $("#selectEditSJQY  option[value='000'] ").attr("selected",true);
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
	   layer.confirm('是否删除区域信息？', function() {   
		   if(deleteQYCode()==true){
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
    		if(checkQYBMExist()){
				layer.alert('区域编码已存在！', 0, '友情提示');
				return false;
			}
    		layer.confirm('是否增加区域信息？', function() {
	    		if(insertQYCode()==true){
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
    		if($("#txtEditQYID").val()!=$("#txtYBM").val()){
    			if(checkQYBMExist()){
					layer.alert('区域编码已存在！', 0, '友情提示');
					return false;
				}
    		}
    		layer.confirm('是否修改区域信息？', function() {
	    		if(updateQYCode()==true){
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
    loadEditSelect($("#selectEditSJQY"),"TYPE_QYMC-0","上级区域");
});

var optionFlag = "";

function btn_Add(){
	setButtonStatus("31");
	optionFlag = "Add";
}
function btn_Upd(){
	var arrList = mmg.selectedRows();
   setButtonStatus("32");
   optionFlag = "Upd";

}
function getSJQY(strQYJB,strSJQY){
	if( strQYJB=="1"){	
		//loadEditSelect($("#selectEditSJQY"),"TYPE_QYMC-0","上级区域");
		$('#REG').val("0");
	}else if(strQYJB=="2"){	
		$('#REG').val("1");
		loadEditSelect($("#selectEditSJQY"),"TYPE_QYMC-1","上级区域");
		$("#selectEditSJQY  option[value='"+strSJQY+"'] ").attr("selected",true);
	}else if( strQYJB=="3"){	
		$('#REG').val("1");
		var arrList = mmg.selectedRows();
		loadEditSelect($("#selectEditSJQY"),"TYPE_QYMC-2","上级区域");
		$("#selectEditSJQY  option[value='"+strSJQY+"'] ").attr("selected",true);
	}
}
function makeBeanIn(strQYMC){
	this.XZQY_QYMC = strQYMC;
}
function makeBeanInadd(strQYID,strQYMC,strQYJB,strSJID){
	this.XZQY_QYID = strQYID;
    this.XZQY_QYMC = strQYMC;  
    this.XZQY_QYJB = strQYJB;  
    this.XZQY_SJID = strSJID;
}
function makeBeanInedit(strYBM,strQYID,strQYMC,strQYJB,strSJID){
	this.YBM = strYBM;
    this.XZQY_QYID = strQYID;
    this.XZQY_QYMC = strQYMC;  
    this.XZQY_QYJB = strQYJB;  
    this.XZQY_SJID = strSJID;
}
function makeBeanIndel(strQYID){
    this.XZQY_QYID = strQYID;  
}
function loadGridByBean(){
	var beanIn = new makeBeanIn(
			$('#txtSelQYMC').val()
	);
	//重新查询数据
	mmg.load({
    	beanLoad  :  JSON.stringify(beanIn)
	});
}

function insertQYCode(){
	var blnRet = false;
	var beanIn = new makeBeanInadd(
           $('#txtEditQYID').val(),
           $('#txtEditQYMC').val(),
           $('#selectEditQYJB').val(),
           $('#selectEditSJQY').val()    
	);
	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/Servlet1090210",
		data: {
			CMD    : "<%=Servlet1090210.CMD_INSERT%>",
		    BeanIn : JSON.stringify(beanIn)
		},
		complete :function(response){},
		success: function(response){
			var strResault = response[0];
			if(strResault=="SUCCESS"){
				layer.msg('恭喜：新增区域信息成功！', 1, 9);
				blnRet = true;
			}else if(strResault=="FAILURE"){
				layer.msg('对不起：新增区域信息失败！', 1, 8);
				blnRet = false;
			}else{
				layer.msg('提示：新增区域信息出错！', 1, 0);
				blnRet = false;
			}
		}
	});
	return blnRet;
}
function updateQYCode(){
	var blnRet = false;
	var beanIn = new makeBeanInedit(
	 	   $('#txtYBM').val(),
		   $('#txtEditQYID').val(),
           $('#txtEditQYMC').val(),
           $('#selectEditQYJB').val(),
           $('#selectEditSJQY').val()   
	);
   
    $.ajax({
      async     : false,
      type      : "post",
      dataType  : "json",
      url: "<%=basePath%>/Servlet1090210",
      data: {
         CMD    : "<%=Servlet1090210.CMD_UPDATE%>",
         BeanIn : JSON.stringify(beanIn)
      },
      complete :function(response){},
      success: function(response){
          var strResault = response[0];
          if(strResault=="SUCCESS"){
        	 layer.msg('恭喜：修改区域信息成功！', 1, 9);
             blnRet = true;
          }else if(strResault=="FAILURE"){
             layer.msg('对不起：修改区域信息失败！', 1, 8);
             blnRet = false;
          }else{
             layer.msg('提示：修改区域信息出错！', 1, 0);
             blnRet = false;
          }
      }
   });
   return blnRet;
}

function deleteQYCode(){
	var blnRet = false;
	var beanIn = new makeBeanIndel(
			$('#txtEditQYID').val()
	);

	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/Servlet1090210",
		data: {
			CMD    : "<%=Servlet1090210.CMD_DELETE%>",
			BeanIn : JSON.stringify(beanIn)
		},
		complete : function(response) {},
		success : function(response) {
			var strResault = response[0];
			if (strResault == "SUCCESS") {
				layer.msg('恭喜：删除区域信息成功！', 1, 9);
				blnRet = true;
			} else if (strResault == "FAILURE") {
				layer.msg('对不起：删除区域信息失败！', 1, 8);
				blnRet = false;
			} else {
				layer.msg('提示：删除区域信息出错！', 1, 0);
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
		$('#selectEditQYJB').attr("disabled","disabled");
		$('#selectEditSJQY').attr("disabled","disabled");
		$('#txtEditQYID').attr("disabled","disabled");
		$('#txtEditQYMC').attr("disabled","disabled");
	
		
		$('#txtEditQYID').val("");			
		$('#txtEditQYMC').val("");			
		$('#selectEditQYJB').val("000");
		$('#selectEditSJQY').val("000");

	} else if (strFlag.substring(0, 1) == "3") {
		//新增/修改
		$('#btnAdd').attr("disabled","disabled");
		$('#btnUpd').attr("disabled", "disabled");
		$('#btnDel').attr("disabled", "disabled");
		$('#btnSave').removeAttr("disabled");
		$('#btnCancel').removeAttr("disabled");
		if (strFlag == "31") {//新增
			$('#selectEditQYJB').removeAttr("disabled");
			$('#selectEditSJQY').removeAttr("disabled");
			$('#txtEditQYID').removeAttr("disabled");
			$('#txtEditQYMC').removeAttr("disabled");
		
			
			$('#txtEditQYID').val("");			
			$('#txtEditQYMC').val("");			
			$('#selectEditQYJB').val("000");
			$('#selectEditSJQY').val("000");
			$('#txtEditQYID').focus();
		} else if (strFlag == "32") {//修改
			$('#selectEditQYJB').removeAttr("disabled");
			if($('#REG').val()=="0"){
				
			}else{
				$('#selectEditSJQY').removeAttr("disabled");
			}
			$('#txtEditQYID').removeAttr("disabled");
			$('#txtEditQYMC').removeAttr("disabled");
			$('#txtEditQYID').focus();
		} else if (strFlag == "33") {//删除
		
		}
	} else if (strFlag == "4") {//选中行
		$('#btnAdd').removeAttr("disabled");
		$('#btnUpd').removeAttr("disabled");
		$('#btnDel').removeAttr("disabled");
		$('#btnSave').attr("disabled", "disabled");
		$('#btnCancel').attr("disabled", "disabled");
		$('#selectEditQYJB').attr("disabled","disabled");
		$('#selectEditSJQY').attr("disabled","disabled");
		$('#txtEditQYID').attr("disabled","disabled");
		$('#txtEditQYMC').attr("disabled","disabled");
	}
}
//验证编辑输入数据
function funEditCheck() {
	if (optionFlag == "Add" || optionFlag == "Upd") {
		if ($('#txtEditQYID').val() == "") {
			layer.alert('请输入区域编码！', 0, '友情提示', function() {
				$('#txtEditQYID').focus();
				layer.close(layer.index);
			});
			return false;
		}
		if ($('#txtEditQYMC').val() == "") {
			layer.alert('请输入区域名称！', 0, '友情提示', function() {
				$('#txtEditQYMC').focus();
				layer.close(layer.index);
			});
			return false;
		}	
		if ($('#selectEditQYJB').val() == "000") {
			layer.alert('请选择区域级别！', 0, '友情提示', function() {
				$('#selectEditQYJB').focus();
				layer.close(layer.index);
			});
			return false;
		}
	    if ($('#selectEditQYJB').val() != "1") {
	    	if($('#selectEditSJQY').val() == "000"){
				layer.alert('请选择上级区域！', 0, '友情提示', function() {
					$('#selectEditSJQY').focus();
					layer.close(layer.index);
				});
				return false;
	    	}	
		}
	}
	return true;
}
function changeQY(){
	if( $('#selectEditQYJB').val()=="1"){	
		//loadEditSelect($("#selectEditSJQY"),"TYPE_QYMC-0","上级区域");
		$('#selectEditSJQY').attr("disabled","disabled");
	}else if( $('#selectEditQYJB').val()=="2"){	
		$('#selectEditSJQY').removeAttr("disabled");
		loadEditSelect($("#selectEditSJQY"),"TYPE_QYMC-1","上级区域");
	}else if( $('#selectEditQYJB').val()=="3"){	
		$('#selectEditSJQY').removeAttr("disabled");
		loadEditSelect($("#selectEditSJQY"),"TYPE_QYMC-2","上级区域");
	}
	
}
function checkQYBMExist(){
	var blnRet = false;
	var strQYBM = $('#txtEditQYID').val();

	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/Servlet1090210",
		data: {
			CMD    : "<%=Servlet1090210.CMD_CHK_EXIST%>",
			QYBM : strQYBM
		},
		complete : function(response) {
		},
		success : function(response) {
			var strResault = response[0];
			if (strResault == "AREA_EXIST") {
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
				<th style="width:100px">区域名称</th>
				<td style="width:100px">
					<input type="text" id="txtSelQYMC"/>
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
				<th style="width:100px">区域编码</th>	
				<td><input type="hidden" id="REG" /><input type="hidden"  id="txtYBM" /><input id="txtEditQYID" name="区域编码" maxlength="20" /></td>
				<th style="width:100px">区域名称</th>	
				<td><input id="txtEditQYMC" name="区域名称" maxlength="20" /></td>
				<th style="width:100px">区域级别</th>
				<td style="width:100px">
					<select id="selectEditQYJB" onchange="changeQY()"> 
						<option value="000" selected>请选择</option> 
						<option value="1" >一级</option> 
						<option value="2" >二级</option> 
						<option value="3" >三级</option> 
					</select>
				</td>
				<th style="width:100px">上级区域</th>	
				<td style="width:100px">
					<select id="selectEditSJQY"> 
						<option value="000" selected>请选择</option> 
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
			
		</div>
	</fieldset>
</body>
</html>