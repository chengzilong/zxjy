<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.xsjy.servlet.servlet2010000.Servlet2010150"%> 
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
<title>关注信息</title>
<script type="text/javascript" src="<%=basePath%>/bin/js/laydate/laydate.js"></script>
<script type="text/javascript" src="<%=basePath%>/bin/js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="<%=basePath%>/bin/js/common.js"></script>
<script type="text/javascript" src="<%=basePath%>/bin/js/json2.js"></script>
<!--表格脚本Start  -->
<script type="text/javascript" src="<%=basePath%>/bin/js/mmgrid/mmGrid.js"></script>
<script type="text/javascript" src="<%=basePath%>/bin/js/mmgrid/mmPaginator.js"></script>
<!--表格脚本End  -->
<script type="text/javascript">

var mmg;
var intheight;
//初始化表格
$(document).ready(function(){
   var cols = [
       { title:'关注教师', name:'JSXM' ,width:80, sortable:true, align:'center',lockDisplay: true },
       { title:'性别', name:'XB' ,width:40, sortable:true, align:'center',lockDisplay: true  },
       { title:'联系方式', name:'LXFS' ,width:100, sortable:true, align:'center',lockDisplay: true  },
       { title:'学历', name:'XL' ,width:60, sortable:true, align:'center',lockDisplay: true  },
       { title:'毕业学校', name:'BYXX' ,width:120, sortable:true, align:'center',lockDisplay: true  },
       { title:'教学经验', name:'JNJY' ,width:70, sortable:true, align:'center',lockDisplay: true  },
       { title:'教师资格', name:'JSZG' ,width:80, sortable:true, align:'center',lockDisplay: true  },
       { title:'备注', name:'GZXX_BZXX' ,width:300, sortable:true, align:'center',lockDisplay: true  }        
   ];

   intheight = document.documentElement.clientHeight -$('#editRegion').height()-$('#selectRegion').height()-80; 
   if(intheight<100){
	   intheight = 100;
   }

   mmg = $('.mmg').mmGrid({
         height: intheight
        ,cols: cols
        ,url: '<%=basePath%>/Servlet2010150'
        ,method: 'post'
        ,params:{CMD : "<%=Servlet2010150.CMD_SELECT%>"}
        ,remoteSort:true
        ,sortName: 'PJXX_PJID'
        ,sortStatus: 'ASC'
        ,root: 'items'
        ,multiSelect: false
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
       
       
   }).on('click', ':checkbox', function(){ //选择checkbox
	   if(this.checked == true){
		   var arrList = mmg.selectedRows();
		   if(arrList.length>0){
			 
	            
		   }
	   }else{
	    	  
		  	   
	   }
   }).on('loadSuccess',function(e, data){
       //设置按钮状态
	  
   }).on('click', 'tr', function(e){ //点击行;
	   if(mmg.rowsLength()<=0) return; //无数据,不进行操作
       var rowIndex = e.target.parentNode.rowIndex;
       if(typeof(rowIndex) == "undefined"){
    	   rowIndex = e.target.parentNode.parentNode.rowIndex;
       }
       var arrList = mmg.row(rowIndex);
       var arrList1 = mmg.selectedRows();
       if(arrList1.length>0) {
      
      	   
       }else
       {
           
       }
    });

   
   $('#btnSearch').on('click', function(){
	   loadGridByBean();
   });
    loadGridByBean();  
});
function makeBeanIn(strJSXM){
	this.JSXM = strJSXM;
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
</script>
</head>
<body>
	<fieldset id = "selectRegion">
		<legend>查询条件</legend>
		<table>
			<tr>
				<th style="width:100px">教师姓名</th>
				<td><input id="txtSelJSXM" name="教师姓名" maxlength="20" /></td>
				<th  style="width:100px"><input type="button" value="查询" id="btnSearch" /></th>
			</tr>
		</table>
	</fieldset>
	<div id="gridCanvas">
		<table id="mmg" class="mmg"></table>
		<div id="pg" style="text-align: right;"></div>
	</div>
</body>
</html>