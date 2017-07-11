<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.xsjy.servlet.servlet1080000.Servlet1080120"%> 
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
<title>教学点交费——二级操作页面</title>
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
var strJFMX_BMID="";
var mmg;
var intheight;
//初始化表格
$(document).ready(function(){
	//获取父页面传递参数
	var inputObj = window.parent.obj; 
	strJFMX_BMID=inputObj.JFMX_BMID;
   var cols = [
       { title:'费用', name:'JFMX_FY' ,width:60, sortable:true, align:'center',lockDisplay: true },
       { title:'当前交费', name:'JFMX_JFJE' ,width:60, sortable:true, align:'center',lockDisplay: true  },
       { title:'交费日期', name:'JFMX_JFRQ' ,width:80, sortable:true, align:'center',lockDisplay: true  },
       { title:'备注', name:'JFMX_BZ' ,width:300, sortable:true, align:'center',lockDisplay: true  }
   ];

   intheight = document.documentElement.clientHeight -$('#editRegion').height()-$('#selectRegion').height()-80; 
   if(intheight<100){
	   intheight = 100;
   }

   mmg = $('.mmg').mmGrid({
         height: intheight
        ,cols: cols
        ,url: '<%=basePath%>/Servlet1080120'
        ,method: 'post'
        ,params:{CMD : "<%=Servlet1080120.CMD_SELECTMX%>"}
        ,remoteSort:true
        ,sortName: 'JFMX_JFMXID'
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
	   $('#txtEditJFMXID').val(arrList.JFMX_JFMXID);
       $('#txtEditJFJE').val(arrList.JFMX_JFJE);
       $('#txtEditYJE').val(arrList.JFMX_JFJE);
       $('#txtEditBZ').val(arrList.JFMX_BZ);
       setButtonStatus("4");
       
   }).on('click', ':checkbox', function(){ //选择checkbox
	   if(this.checked == true){
		   var arrList = mmg.selectedRows();
		   if(arrList.length>0){
		 	   $('#txtEditJFMXID').val(arrList[0].JFMX_JFMXID);
		       $('#txtEditJFJE').val(arrList[0].JFMX_JFJE);
		       $('#txtEditYJE').val(arrList[0].JFMX_JFJE);
		       $('#txtEditBZ').val(arrList[0].JFMX_BZ);
	         setButtonStatus("4");   
		   }
	   }else{
	           $('#txtEditJFMXID').val("");
		       $('#txtEditJFJE').val("");
		       $('#txtEditYJE').val("");
		       $('#txtEditBZ').val("");
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
       	   $('#txtEditJFMXID').val(arrList.JFMX_JFMXID);
	       $('#txtEditJFJE').val(arrList.JFMX_JFJE);
	       $('#txtEditYJE').val(arrList.JFMX_JFJE);
	       $('#txtEditBZ').val(arrList.JFMX_BZ);
      	   setButtonStatus("4"); 
       }else
       {
           setButtonStatus("2"); 
       }
    });
    $('#btnDel').on('click', function(){
	   var arrList = mmg.selectedRows();
	   if(arrList.length<=0){
		   layer.alert('请选择要删除的交费信息！', 0, '友情提示');
 	       return;
	   }
	   layer.confirm('是否删除交费信息？', function() {
		   if(deleteJFMXCode(arrList[0].JFMX_JFMXID,arrList[0].JFMX_JFJE)==true){
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
    		layer.confirm('是否增加交费信息？', function() {
    			if(insertJFMXCode()==true){
      	            //重新查询数据
      	            loadGridByBean();
      	            setButtonStatus("2");
      	            mmg.deselect('all');
    			}
    		});
    	}else if(optionFlag == "Upd"){
    		var arrList = mmg.selectedRows();
    		if(arrList.length<=0){
    			layer.alert('请选择要修改的交费信息！', 0, '友情提示');
    			return;
    		}
    		if(funEditCheck()==false) return;
    		layer.confirm('是否修改交费信息？', function() {
    			if(updateJFMXCode(arrList[0].JFMX_JFJE)==true){
	   	             //重新查询数据
	   	             loadGridByBean();
	   	             setButtonStatus("2");
	   	             mmg.deselect('all');
	   	        }
    		});
    	}
    });
    loadGridByBean();
    loadEditSelect($("#selectEditJFLX"),"TYPE_JFLX","报名方式");
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

function makeBeanIn(strBMID){
	this.JFMX_BMID = strBMID;
}
function makeBeanInadd(strBMID,strJFJE){
    this.BMXX_BMID = strBMID;    
    this.BMXX_YJFY = strJFJE; 
}
function makeBeanInaddmx(strBMID,JFMXID,strJFLX,strBZ,strJFJE){
    this.JFMX_BMID = strBMID;    
    this.JFMX_BMID = JFMXID;    
    this.JFMX_JFLXID = strJFLX; 
    this.JFMX_BZ = strBZ;    
    this.JFMX_JFJE = strJFJE;  
}
function makeBeanInedit(strBMID,strJFFY){
    this.BMXX_BMID = strBMID;    
    this.BMXX_YJFY = strJFFY;  
}
function makeBeanIneditmx(strJFMXID,strJFLXID,strBZ,strJFJE){
    this.JFMX_JFMXID = strJFMXID;   
    this.JFMX_JFLXID = strJFLXID;    
    this.JFMX_BZ = strBZ;
    this.JFMX_JFJE = strJFJE; 
}
function makeBeanIndel(strBMID,strJFMXID,strJFJE){
	this.JFMX_BMID = strBMID;
    this.JFMX_JFMXID = strJFMXID;    
    this.JFMX_JFJE = strJFJE; 
}
function loadGridByBean(){
	var beanIn = new makeBeanIn(
			strJFMX_BMID
	);
	getFY(strJFMX_BMID);
	//重新查询数据
	mmg.load({
    	beanLoad  :  JSON.stringify(beanIn)
	});
}
function getFY(strBMID){
	  $.ajax({
      async     : false,
      type      : "post",
      dataType  : "json",
      url: "<%=basePath%>/Servlet1080120",
      data: {
         CMD    : "<%=Servlet1080120.CMD_VALUE%>",
         BMID   : strBMID
      },
      complete :function(response){},
      success: function(response){
          var strResault = response[0];
          var dataBean = response[1];
          if(strResault=="SUCCESS"){
			 $('#txtEditFY').val(dataBean.BMXX_FY);
			 $('#txtEditWJFY').val(dataBean.BMXX_WJFY);
          }else if(strResault=="DATA_NULL"){
        	  layer.msg('对不起：获取费用和未交费用失败！', 1, 8);
          }
      }
   });
}
function insertJFMXCode(){
	var blnRet = false;
	var beanIn = new makeBeanInadd(
		   strJFMX_BMID,
           $('#txtEditJFJE').val()
	);
   var beanInmx = new makeBeanInaddmx(
   		   strJFMX_BMID,
   		   $('#txtEditJFMXID').val(),
           $('#selectEditJFLX').val(),
           $('#txtEditBZ').val(),
           $('#txtEditJFJE').val()
	);
    $.ajax({
      async     : false,
      type      : "post",
      dataType  : "json",
      url: "<%=basePath%>/Servlet1080120",
      data: {
         CMD    : "<%=Servlet1080120.CMD_INSERT%>",
         BeanIn : JSON.stringify(beanIn),
         BeanInmx : JSON.stringify(beanInmx)
      },
      complete :function(response){},
      success: function(response){
          var strResault = response[0];
          if(strResault=="SUCCESS"){
        	  layer.msg('恭喜：交费成功！', 1, 9);
             blnRet = true;
          }else if(strResault=="FAILURE"){
        	  layer.msg('对不起：交费失败！', 1, 8);
             blnRet = false;
          }
      }
   });
   return blnRet;
}
function updateJFMXCode(strOJFJE){
	var blnRet = false;
	var beanIn = new makeBeanInedit(
		    strJFMX_BMID,
           parseFloat($('#txtEditJFJE').val())-parseFloat(strOJFJE)
	);
   var beanInmx = new makeBeanIneditmx(
   		   $('#txtEditJFMXID').val(),
           $('#selectEditJFLX').val(),
           $('#txtEditBZ').val(),
           $('#txtEditJFJE').val()
	);
    $.ajax({
      async     : false,
      type      : "post",
      dataType  : "json",
      url: "<%=basePath%>/Servlet1080120",
      data: {
         CMD    : "<%=Servlet1080120.CMD_UPDATE%>",
         BeanIn : JSON.stringify(beanIn),
         BeanInmx : JSON.stringify(beanInmx)
      },
      complete :function(response){},
      success: function(response){
          var strResault = response[0];
          if(strResault=="SUCCESS"){
        	  layer.msg('恭喜：修改成功！', 1, 9);
             blnRet = true;
          }else if(strResault=="FAILURE"){
        	  layer.msg('对不起：修改失败！', 1, 8);
             blnRet = false;
          }
      }
   });
   return blnRet;
}
function deleteJFMXCode(strJFMXID,strJFJE){
	var blnRet = false;
	var beanIn = new makeBeanIndel(
			strJFMX_BMID,
			strJFMXID,
			strJFJE
	);

	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/Servlet1080120",
		data: {
			CMD    : "<%=Servlet1080120.CMD_DELETE%>",
			BeanInmx : JSON.stringify(beanIn)
		},
		complete : function(response) {},
		success : function(response) {
			var strResault = response[0];
			if(strResault=="SUCCESS"){
	        	  layer.msg('恭喜：删除交费信息成功！', 1, 9);
	             blnRet = true;
	          }else if(strResault=="ERROR"){
	        	  layer.msg('对不起：删除交费信息失败！', 1, 8);
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
		$('#selectEditJFLX').attr("disabled","disabled");
		$('#txtEditBZ').attr("disabled","disabled");
		$('#txtEditFY').attr("disabled","disabled");
		$('#txtEditWJFY').attr("disabled","disabled");
		$('#txtEditJFJE').attr("disabled","disabled");
	
			
		$('#selectEditJFLX').val("000");
		$('#txtEditBZ').val("");
		$('#txtEditJFJE').val("");


	} else if (strFlag.substring(0, 1) == "3") {
		//新增/修改
		$('#btnAdd').attr("disabled", "disabled");
		$('#btnUpd').attr("disabled", "disabled");
		$('#btnDel').attr("disabled", "disabled");
		$('#btnSave').removeAttr("disabled");
		$('#btnCancel').removeAttr("disabled");
		if (strFlag == "31") {//新增
			
			$('#selectEditJFLX').removeAttr("disabled");
			$('#txtEditBZ').removeAttr("disabled");
			$('#txtEditJFJE').removeAttr("disabled");
			
			$('#txtEditJFJE').val(0);
			$('#txtEditJFJE').focus();
			$('#txtEditJFJE').select();
		} else if (strFlag == "32") {//修改
			$('#selectEditJFLX').removeAttr("disabled");
			$('#txtEditBZ').removeAttr("disabled");
			$('#txtEditJFJE').removeAttr("disabled");
			$('#txtEditJFJE').focus();
			$('#txtEditJFJE').select();
		} else if (strFlag == "33") {//删除
		
		}
	} else if (strFlag == "4") {//选中行
		$('#btnUpd').removeAttr("disabled");
		$('#btnDel').removeAttr("disabled");
		$('#btnSave').attr("disabled", "disabled");
		$('#btnCancel').attr("disabled", "disabled");
		$('#selectEditJFLX').attr("disabled","disabled");
		$('#txtEditBZ').attr("disabled","disabled");
		$('#txtEditFY').attr("disabled","disabled");
		$('#txtEditJFJE').attr("disabled","disabled");
		$('#txtEditWJFY').attr("disabled","disabled");
	}
}
//验证编辑输入数据
function funEditCheck() {
	
		if ($('#txtEditJFJE').val() == "") {
			layer.alert('交费金额不能为空！', 0, '友情提示', function() {
				$('#txtEditJFJE').val();
				$('#txtEditJFJE').focus();
				$('#txtEditJFJE').select();
				layer.close(layer.index);
			});
			return false;
		}
		if (optionFlag == "Add" ) {	
			if (parseFloat($('#txtEditJFJE').val())>parseFloat($('#txtEditWJFY').val()))  {
				layer.alert('交费金额不能大于未交费用！', 0, '友情提示', function() {
					$('#txtEditJFJE').focus();
					$('#txtEditJFJE').select();
					layer.close(layer.index);
				});
				return false;
			}
	 	}else{
	 		if ((parseFloat($('#txtEditJFJE').val())-parseFloat($('#txtEditYJE').val()))>parseFloat($('#txtEditWJFY').val()))  {
				layer.alert('修改后的金额差值不能大于未交费用！', 0, '友情提示', function() {
					$('#txtEditJFJE').focus();
					$('#txtEditJFJE').select();
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
	<div id="gridCanvas">
		<table id="mmg" class="mmg"></table>
		<div id="pg" style="text-align: right;"></div>
	</div>
	<fieldset id = "editRegion">
		<legend>编辑</legend>
		<table id="detailCanvas" class="eTable6">
			<tr>	
			    <th style="width:100px">费用</th>	
				<td><input type="hidden" id="txtEditJFMXID"><input style="width:80px" id="txtEditFY" name="费用" maxlength="20" /></td>
				<th style="width:100px">未交费用</th>	
				<td><input style="width:80px" id="txtEditWJFY" name="未交费用" maxlength="20" /></td>
				<th style="width:100px">交费金额</th>	
				<td><input type="hidden" id="txtEditYJE"  /><input style="width:80px" id="txtEditJFJE" name="交费金额" maxlength="20" /></td>
			</tr>
			<tr>
				<th style="width:100px">交费类型</th>	
				<td style="width:100px">
					<select id="selectEditJFLX"> 
						<option value="000" selected>请选择</option> 
					</select>
				</td>
				<th style="width:100px">备注</th>	
				<td colspan="3"><input style="width:310px" id="txtEditBZ" name="备注" maxlength="100" /></td>
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