<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.xsjy.servlet.servlet2020000.Servlet2020110"%> 
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
<script type="text/javascript">
var mmg;
var intheight;
//初始化表格
$(document).ready(function(){
   var cols = [
       { title:'班次名称', name:'BCXX_BCMC' ,width:100, sortable:true, align:'center',lockDisplay: true },
       { title:'课程名称', name:'KCMC' ,width:80, sortable:true, align:'center',lockDisplay: true },
       { title:'开始时间', name:'BCXX_KSSJ' ,width:80, sortable:true, align:'center',lockDisplay: true  },
       { title:'结束时间', name:'BCXX_JSSJ' ,width:80, sortable:true, align:'center',lockDisplay: true  },
       { title:'上课地点', name:'BCXX_SKDD' ,width:400, sortable:true, align:'center',lockDisplay: true  },
       { title:'班次状态', name:'BCZT' ,width:60, sortable:true, align:'center',lockDisplay: true  },
  	   { title:'费用', name:'BCXX_FY' ,width:60, sortable:true, align:'center',lockDisplay: true  }
   ];

   intheight = document.documentElement.clientHeight -$('#editRegion').height()-$('#selectRegion').height()-80; 
   if(intheight<100){
	   intheight = 100;
   }

   mmg = $('.mmg').mmGrid({
         height: intheight
        ,cols: cols
        ,url: '<%=basePath%>/Servlet2020110'
        ,method: 'post'
        ,params:{CMD : "<%=Servlet2020110.CMD_SELECT%>"}
        ,remoteSort:true
        ,sortName: 'BCXX_BCID'
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
  
   $('#btnSearch').on('click', function(){
	   loadGridByBean();
   });
    loadGridByBean();   
    loadSearchSelect($("#selectKCMC"),"TYPE_KCMC","课程名称");
});

function makeBeanIn(strBCMC,strKCMC,strSKSJ_B,strSKSJ_E,strBCZT){
	this.BCXX_BCMC = strBCMC;
	this.BCXX_KCID = strKCMC;
	this.BEGIN = strSKSJ_B;
	this.END = strSKSJ_E;
	this.BCXX_BCZT = strBCZT;
}
function loadGridByBean(){
	var beanIn = new makeBeanIn(
			$('#txtSelBCMC').val(),
			$('#selectKCMC').val(),
			$('#txtSelFBSJ_BEGIN').val(),
			$('#txtSelFBSJ_END').val(),
			$('#selectBCZT').val()
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
				<th style="width:80px">班次名称</th>
		        <td><input style="width:100px" id="txtSelBCMC" type="text"   ></td>
		        <th style="width:100px">课程名称</th>
				<td><select id="selectKCMC" style="width: 100px"></select></td>
				<th style="width:100px">上课时间</th>
		        <td><input style="font-size:12px;line-height:16px; height:18px;"  class="laydate-icon-default" id="txtSelFBSJ_BEGIN" type="text"  onclick="laydate()" readonly></td>
		        <th >-</th>
		        <td><input style="font-size:12px;line-height:16px; height:18px;"  class="laydate-icon-default" id="txtSelFBSJ_END" type="text"  onclick="laydate()" readonly></td>
		     	<th style="width:100px">班次状态</th>
				<td>
					<select id="selectBCZT" style="width: 100px">
						<option value="000">所有</option>
						<option value="0">未开课</option>
						<option value="1">已开课</option>
						<option value="2">已结束</option>
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
</body>
</html>