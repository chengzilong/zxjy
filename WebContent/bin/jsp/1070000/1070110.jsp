<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.xsjy.servlet.servlet1070000.Servlet1070110"%> 
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
<meta http-equiv="X-UA-Compatible" content="IE=9">
<!--表格样式Start  -->
<link rel="stylesheet" href="<%=basePath%>/bin/css/mmgrid/main.css" type="text/css">
<link rel="stylesheet" href="<%=basePath%>/bin/css/mmgrid/mmGrid.css" type="text/css">
<link rel="stylesheet" href="<%=basePath%>/bin/css/mmgrid/mmPaginator.css" type="text/css">
<link rel="stylesheet" href="<%=basePath%>/bin/css/mmgrid/normalize.css" type="text/css">
<link rel="stylesheet" href="<%=basePath%>/bin/css/mmgrid/tablesize.css" type="text/css">
<!--表格样式End  -->
<title>教师信息</title>
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
var obj;
//初始化表格
$(document).ready(function(){
   var cols = [
       { title:'教师姓名', name:'JSXX_JSXM' ,width:80, sortable:true, align:'center',lockDisplay: true },
       { title:'性别', name:'JSXX_XBMC' ,width:30, sortable:true, align:'center',lockDisplay: true  },
       { title:'身份证号', name:'JSXX_SFZH' ,width:150, sortable:true, align:'center',lockDisplay: true  },
       { title:'出生日期', name:'JSXX_CSRQ' ,width:100, sortable:true, align:'center',lockDisplay: true  },
       { title:'联系方式', name:'JSXX_LXFS' ,width:100, sortable:true, align:'center',lockDisplay: true  },
       { title:'住址', name:'JSXX_ZZ' ,width:200, sortable:true, align:'center',lockDisplay: true  },
       { title:'学历', name:'JSXX_XL' ,width:40, sortable:true, align:'center',lockDisplay: true  },
       { title:'毕业学校', name:'JSXX_BYXX' ,width:150, sortable:true, align:'center',lockDisplay: true  },
       { title:'专业', name:'JSXX_ZY' ,width:150, sortable:true, align:'center',lockDisplay: true  },
       { title:'毕业时间', name:'JSXX_BYNF' ,width:80, sortable:true, align:'center',lockDisplay: true  },
       { title:'教师资格', name:'JSXX_ZGMC' ,width:100, sortable:true, align:'center',lockDisplay: true  }
   ];

   intheight = document.documentElement.clientHeight -$('#editRegion').height()-$('#selectRegion').height()-80; 
   if(intheight<100){
	   intheight = 100;
   }

   mmg = $('.mmg').mmGrid({
         height: intheight
        ,cols: cols
        ,url: '<%=basePath%>/Servlet1070110'
        ,method: 'post'
        ,params:{CMD : "<%=Servlet1070110.CMD_SELECT%>"}
        ,remoteSort:true
        ,sortName: 'JSXX_JSID'
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
	   $('#txtEditJSID').val(arrList.JSXX_JSID);
       setButtonStatus("4");
       
   }).on('click', ':checkbox', function(){ //选择checkbox
	   if(this.checked == true){
		   var arrList = mmg.selectedRows();
		   if(arrList.length>0){
			   $('#txtEditJSID').val(arrList[0].JSXX_JSID);
	           setButtonStatus("4");   
		   }
	   }else{
	    	   $('#txtEditSHID').val("");
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
       $('#txtEditJSID').val(arrList.JSXX_JSID);
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
	   var strJSID=arrList[0].JSXX_JSID;
	   layer.confirm('是否删除教师信息？', function() {
		   if(deleteJSXXCode(strJSID)==true){
			   //重新查询数据
			   loadGridByBean();
			   mmg.deselect('all');
		   }
	   });
   });
   $('#btnAdd').on('click', function(){
	   obj = new Object();
		obj.FLAG="ADD";	
	   iframeLayerOpen('<%=basePath%>/bin/jsp/common/teacherEdit.jsp');
    });
    $('#btnUpd').on('click', function(){
    	var arrList = mmg.selectedRows();
    	if(arrList.length<=0){
    		layer.alert('请选择要修改的数据行！', 0, '友情提示');
    		return;
    	}
    	obj = new Object();
    	obj.FLAG="UP";
		obj.JSXX_JSID=arrList[0].JSXX_JSID;	
		obj.JSXX_JSXM=arrList[0].JSXX_JSXM;	
		obj.JSXX_SFZH=arrList[0].JSXX_SFZH;	
		obj.JSXX_XB=arrList[0].JSXX_XB;	
		obj.JSXX_CSRQ=arrList[0].JSXX_CSRQ;	
		obj.JSXX_LXFS=arrList[0].JSXX_LXFS;	
		obj.JSXX_ZZ=arrList[0].JSXX_ZZ;	
		obj.JSXX_XL=arrList[0].JSXX_XL;	
		obj.JSXX_BYXX=arrList[0].JSXX_BYXX;	
		obj.JSXX_BYNF=arrList[0].JSXX_BYNF;	
		obj.JSXX_GRJJ=arrList[0].JSXX_GRJJ;	
		obj.JSXX_ZY=arrList[0].JSXX_ZY;
		obj.JSXX_NJ=arrList[0].JSXX_NJ;
		obj.JSXX_JNJY=arrList[0].JSXX_JNJY;
		obj.JSXX_JSZG=arrList[0].JSXX_JSZG;
		obj.JSXX_SCLY=arrList[0].JSXX_SCLY;
		iframeLayerOpen('<%=basePath%>/bin/jsp/common/teacherEdit.jsp');
    });
    loadGridByBean();  
});
function makeBeanIn(strJSXM){
	this.JSXX_JSXM = strJSXM;
}
function makeBeanIndel(strJSID){
    this.JSXX_JSID = strJSID;  
}
function loadGridByBean(){
	var beanIn = new makeBeanIn(
			$('#txtSelJSXM').val()
	);
	//重新查询数据
	mmg.load({
    	beanLoad  :  JSON.stringify(beanIn)
	});
}
function deleteJSXXCode(strJSID){
	var blnRet = false;
	var beanIn = new makeBeanIndel(
			strJSID
	);

	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/Servlet1070110",
		data: {
			CMD    : "<%=Servlet1070110.CMD_DELETE%>",
			BeanIn : JSON.stringify(beanIn)
		},
		complete : function(response) {},
		success : function(response) {
			var strResault = response[0];
			if (strResault == "SUCCESS") {
				layer.msg('恭喜：删除教师信息成功！', 1, 9);
				blnRet = true;
			} else if (strResault == "FAILURE") {
				layer.msg('对不起：删除教师信息失败！', 1, 8);
				blnRet = false;
			} else {
				layer.msg('提示：删除教师信息出错！', 1, 0);
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
	} else if (strFlag == "2") {//查询后/返回
		$('#btnAdd').removeAttr("disabled");
		$('#btnUpd').attr("disabled", "disabled");
		$('#btnDel').attr("disabled", "disabled");
	} else if (strFlag.substring(0, 1) == "3") {
		//新增/修改
		$('#btnAdd').attr("disabled","disabled");
		$('#btnUpd').attr("disabled", "disabled");
		$('#btnDel').attr("disabled", "disabled");
	} else if (strFlag == "4") {//选中行
		$('#btnAdd').removeAttr("disabled");
		$('#btnUpd').removeAttr("disabled");
		$('#btnDel').removeAttr("disabled");
	}
}
</script>
</head>
<body>
	<fieldset id = "selectRegion">
		<legend>查询条件</legend>
		<table>
			<tr>
				<th style="width:100px">教师姓名</th>
				<td><input id="txtSelJSXM" name="商户代码" maxlength="20" /></td>
				<th  style="width:100px"><input type="button" value="查询" id="btnSearch" /></th>
			</tr>
		</table>
	</fieldset>
	<div id="gridCanvas">
		<table id="mmg" class="mmg"></table>
		<div id="pg" style="text-align: right;"></div>
	</div>
	<fieldset id = "editRegion">
		<legend>操作</legend>
		<div id="buttonCanvas" class="gToolbar gTbrCenter ">
			<input type="hidden" id="txtEditSHID">
			<input type="button" value="新增" id="btnAdd" name="btnAdd"  />
			<input type="button" value="修改" id="btnUpd" name="btnUpd"  /> 
			<input type="button" value="删除" id="btnDel" name="btnDel" /> 
		</div>
	</fieldset>
</body>
</html>