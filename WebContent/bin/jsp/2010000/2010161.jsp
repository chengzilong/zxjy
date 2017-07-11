<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.xsjy.servlet.servlet2010000.Servlet2010160"%> 
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
<title>回答信息</title>
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
   $("#twr").text($.getUrlParam('twr'));
   $("#twnr").text($.getUrlParam('twnr'));
   var cols = [
       { title:'回答人', name:'HDR' ,width:80, sortable:true, align:'center',lockDisplay: true },
       { title:'回答内容', name:'HDXX_HDNR' ,width:500, sortable:true, align:'center',lockDisplay: true },
       { title:'回答时间', name:'HDXX_HDSJ' ,width:80, sortable:true, align:'center',lockDisplay: true },
       { title:'回答积分', name:'HDXX_HDJF' ,width:60, sortable:true, align:'center',lockDisplay: true },
       { title:'操作', name:'' ,width:30, sortable:true, align:'center',lockDisplay: true, renderer: function(val,item){
       	if(item.HDXX_HDJF==""){
       		
      	}else{
     		 return '<img id="img-del" class="img-del" title="删除" src="<%=basePath%>/bin/img/delete.gif"></img>';   
     	}
       }}
   ];

   intheight = document.documentElement.clientHeight -$('#editRegion').height()-$('#selectRegion').height()-120; 
   if(intheight<100){
	   intheight = 100;
   }

   mmg = $('.mmg').mmGrid({
         height: intheight
        ,cols: cols
        ,url: '<%=basePath%>/Servlet2010160'
        ,method: 'post'
        ,params:{CMD : "<%=Servlet2010160.CMD_SELECTHD%>"}
        ,remoteSort:true
        ,sortName: 'HDXX_HDID'
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
    mmg.on('cellSelected', function(e, item, rowIndex, colIndex){	  	
        //配置节点
        if($(e.target).is('.img-del')){
        	mmg.deselect('all');
            e.stopPropagation();  //阻止事件冒泡
            layer.confirm('是否删除该条回答信息？', function() {
	            if(deleteHDXXCode(item.HDXX_HDID)==true){
				   //重新查询数据
				   loadGridByBean();
				   setButtonStatus("2");
				   //mmg.deselect('all');
		        }
	        });	
        }
    });
    mmg.on('rowSelected', function(e, item, rowIndex, colIndex){//选择数据行
	   var arrList = mmg.selectedRows();
	   if(arrList.length>0){
		    $('#txtEditHDNR').val(arrList.HDXX_HDNR);
		    $('#txtEditHDID').val(arrList.HDXX_HDID);
	        setButtonStatus("4");
       }else {
		   $('#txtEditHDNR').val("");
		   $('#txtEditHDID').val("");
		   setButtonStatus(2);//未选中行,则只有新增按钮可用
	   }
       
   }).on('click', ':checkbox', function(){ //选择checkbox
	   if(this.checked == true){
		   var arrList = mmg.selectedRows();
		   if(arrList.length>0){
			   $("#txtEditHDNR").val(arrList[0].HDXX_HDNR);
			   $('#txtEditHDID').val(arrList[0].HDXX_HDID);
	         setButtonStatus("4");   
		   }
	   }else{
			   $("#txtEditHDNR").val("");
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
       var arrList1 = mmg.selectedRows();
       var arrList = mmg.row(rowIndex);
       if(arrList1.length>0) {
	      $('#txtEditHDNR').val(arrList.HDXX_HDNR);
	      $('#txtEditHDID').val(arrList.HDXX_HDID);
      	  setButtonStatus("4"); 
       }else
       {
       	   $('#txtEditHDID').val("");
    	   $('#txtEditHDNR').val("");
           setButtonStatus("2"); 
       }
    }); 
    $('#btnCancel').on('click', function(){
    	setButtonStatus(2);
    	mmg.deselect('all');
    });
    $('#btnReturn').on('click', function(){
	  window.location.href='<%=basePath%>/bin/jsp/2010000/2010160.jsp';
   });

    
    $('#btnSave').on('click', function(){
    	if(optionFlag == "Add"){
    		if(funEditCheck()==false) return;
    		layer.confirm('是否添加答案？', function() {
	    		if(insertHDXXCode()==true){
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
    		layer.confirm('是否修改回答内容？', function() {
	    		if(updateHDXXCode()==true){
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
function makeBeanIn(){
	this.HDXX_WTID = $.getUrlParam('wtid') ;
}
function makeBeanInadd(strWTID,strHDNR){
	this.HDXX_WTID = strWTID;
    this.HDXX_HDNR = strHDNR; 
}
function makeBeanInedit(strHDID,strHDNR){
    this.HDXX_HDID = strHDID;
    this.HDXX_HDNR = strHDNR; 
}
function makeBeanIndel(strHDID){
    this.HDXX_HDID = strHDID;
}
function loadGridByBean(){
	//重新查询数据
	mmg.load({
    	WTID  :  $.getUrlParam('wtid')
	});
}

function insertHDXXCode(){
	var blnRet = false;
	var beanIn = new makeBeanInadd(
           $.getUrlParam('wtid'),
           $('#txtEditHDNR').val()
	);
	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/Servlet2010160",
		data: {
			CMD    : "<%=Servlet2010160.CMD_INSERT%>",
		    BeanIn : JSON.stringify(beanIn)
		},
		complete :function(response){},
		success: function(response){
			var strResault = response[0];
			if(strResault=="SUCCESS"){
				layer.msg('恭喜：添加答案成功！', 1, 9);
				blnRet = true;
			}else if(strResault=="FAILURE"){
				layer.msg('对不起：添加答案失败！', 1, 8);
				blnRet = false;
			}else{
				layer.msg('提示：添加答案出错！', 1, 0);
				blnRet = false;
			}
		}
	});
	return blnRet;
}
function updateHDXXCode(){
	var blnRet = false;
	var beanIn = new makeBeanInedit(
		   $('#txtEditHDID').val(),	
           $('#txtEditHDNR').val()
     
	);
   
    $.ajax({
      async     : false,
      type      : "post",
      dataType  : "json",
      url: "<%=basePath%>/Servlet2010160",
      data: {
         CMD    : "<%=Servlet2010160.CMD_UPDATE%>",
         BeanIn : JSON.stringify(beanIn)
      },
      complete :function(response){},
      success: function(response){
          var strResault = response[0];
          if(strResault=="SUCCESS"){
        	 layer.msg('恭喜：修改答案成功！', 1, 9);
             blnRet = true;
          }else if(strResault=="FAILURE"){
          	 layer.msg('对不起：修改答案失败！', 1, 8);
             blnRet = false;
          }else{
          	 layer.msg('提示：修改答案出错！', 1, 0);
			 blnRet = false;
          }
      }
   });
   return blnRet;
}

function deleteHDXXCode(strHDID){
	var blnRet = false;
	var beanIn = new makeBeanIndel(
			strHDID
	);

	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/Servlet2010160",
		data: {
			CMD    : "<%=Servlet2010160.CMD_DELETE%>",
			BeanIn : JSON.stringify(beanIn)
		},
		complete : function(response) {},
		success : function(response) {
			var strResault = response[0];
			if (strResault == "SUCCESS") {
				layer.msg('恭喜：删除答案成功！', 1, 9);
				blnRet = true;
			} else if (strResault == "FAILURE") {
				layer.msg('对不起：删除答案失败！', 1, 8);
				blnRet = false;
			} else {
				layer.msg('提示：删除答案出错！', 1, 0);
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
		$('#txtEditHDNR').attr("disabled","disabled");
	
		
		$('#txtEditHDNR').val("");

	} else if (strFlag.substring(0, 1) == "3") {
		//新增/修改
		$('#btnAdd').attr("disabled","disabled");
		$('#btnUpd').attr("disabled", "disabled");
		$('#btnDel').attr("disabled", "disabled");
		$('#btnSave').removeAttr("disabled");
		$('#btnCancel').removeAttr("disabled");
		if (strFlag == "31") {//新增
			$('#txtEditHDNR').removeAttr("disabled");
		
			
			$('#txtEditHDNR').val("");
			$('#txtEditHDNR').focus();
		} else if (strFlag == "32") {//修改
			$('#txtEditHDNR').removeAttr("disabled");
			$('#txtEditHDNR').focus();
		} else if (strFlag == "33") {//删除
		
		}
	} else if (strFlag == "4") {//选中行
		$('#btnAdd').removeAttr("disabled");
		$('#btnUpd').removeAttr("disabled");
		$('#btnDel').removeAttr("disabled");
		$('#btnSave').attr("disabled", "disabled");
		$('#btnCancel').attr("disabled", "disabled");
		$('#txtEditHDNR').attr("disabled","disabled");
	}
}
//验证编辑输入数据
function funEditCheck() {
	if (optionFlag == "Add" || optionFlag == "Upd") {
		if ($('#txtEditHDNR').val() == "") {
			layer.alert('请输入问题答案！', 0, '友情提示', function() {
				$('#txtEditHDNR').focus();
				layer.close(layer.index);
			});
			return false;
		}	
	}
	return true;
}
$.getUrlParam = function(name) {
				var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
				var r = window.location.search.substr(1).match(reg);
				if (r!=null) return unescape(r[2]); return null;
};
</script>
</head>
<body>
	<fieldset id = "selectRegion">
		<legend>问题信息</legend>
		<i><label id="twr"></label></i><label>&nbsp;&nbsp;提问：&nbsp;&nbsp;</label><i><label id="twnr"></label></i>
		<br>
	</fieldset>
	<fieldset id = "selectRegion">
		<legend>我的回答</legend>
	</fieldset>
	<div id="gridCanvas">
		<table id="mmg" class="mmg"></table>
		<div id="pg" style="text-align: right;"></div>
	</div>
	<fieldset id = "editRegion">
		<legend>编辑</legend>
		<table id="detailCanvas" class="eTable6">
			<tr>
				<th style="width:100px">添加答案</th>	
				<td colspan="3">
				<input type="hidden"  id="txtEditHDID"  />
				<textarea id="txtEditHDNR" cols="79" rows="5"  ></textarea>  
				</td>
			</tr>
		</table>
		<div id="buttonCanvas" class="gToolbar gTbrCenter ">
			<input type="button" value="新增" id="btnAdd" name="btnAdd" onclick="btn_Add()" />
			<input type="button" value="修改" id="btnUpd" name="btnUpd" onclick="btn_Upd()" /> 
			<input type="button" value="保存" id="btnSave" name="btnSave" /> 
			<input type="button" value="取消" id="btnCancel" name="btnCancel" /> 			
		</div>
	</fieldset>
</body>
</html>