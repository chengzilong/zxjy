<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.xsjy.servlet.servlet2010000.Servlet2010130"%> 
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
<title>报名查询</title>
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
       { title:'课程名称', name:'KCMC' ,width:80, sortable:true, align:'center',lockDisplay: true },
       { title:'课程类型', name:'KCLXMC' ,width:100, sortable:true, align:'center',lockDisplay: true },
       { title:'上课时段', name:'SKSD' ,width:60, sortable:true, align:'center',lockDisplay: true },
       { title:'学时', name:'BMXX_XS' ,width:60, sortable:true, align:'center',lockDisplay: true },
       { title:'授课教师', name:'KCJSXM' ,width:80, sortable:true, align:'center',lockDisplay: true },
       { title:'班次名称', name:'BCMC' ,width:100, sortable:true, align:'center',lockDisplay: true },
       { title:'定价', name:'BMXX_FY' ,width:60, sortable:true, align:'center',lockDisplay: true },
       { title:'已交费用', name:'BMXX_YJFY' ,width:60, sortable:true, align:'center',lockDisplay: true },
       { title:'未交费用', name:'BMXX_WJFY' ,width:60, sortable:true, align:'center',lockDisplay: true },
       { title:'报名方式', name:'BMFS' ,width:120, sortable:true, align:'center',lockDisplay: true },
       { title:'报名状态', name:'BMZT' ,width:60, sortable:true, align:'center',lockDisplay: true },
       { title:'推荐教师', name:'TJJSXM' ,width:80, sortable:true, align:'center',lockDisplay: true },
       { title:'备注信息', name:'BMXX_BZXX' ,width:300, sortable:true, align:'center',lockDisplay: true },
       { title:'登记人', name:'BMXX_CJR' ,width:80,sortable:true, align:'center',lockDisplay: true },
       { title:'登记时间', name:'BMXX_CJSJ' ,width:150,sortable:true, align:'center',lockDisplay: true }
   ];
             
    intheight = document.documentElement.clientHeight -$('#selectRegion').height()-80; 
   if(intheight<100){
	   intheight = 100;
   }

   mmg = $('.mmg').mmGrid({
         height: intheight
        ,cols: cols
        ,url: '<%=basePath%>/Servlet2010130'
        ,method: 'post'
        ,params:{CMD : "<%=Servlet2010130.CMD_SELECT%>"}
        ,remoteSort:true
        ,sortName: 'BMXX_BMID'
        ,sortStatus: 'ASC'
        ,root: 'items'
        ,multiSelect: false
        ,checkCol: false
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
   //查询点击事件
   $('#btnSearch').on('click', function(){
	   loadGridByBean();
   });
    loadGridByBean();
    

    loadSearchSelect($("#selectKcjs"),"TYPE_JSXM","课程教师");
    loadSearchSelect($("#selectBmzt"),"TYPE_BMZT","报名状态");
});

function makeBeanIn(strBMXX_JSID,strBMXX_BMZT){
    this.BMXX_JSID = strBMXX_JSID;
    this.BMXX_BMZT = strBMXX_BMZT;
}
//加载数据
function loadGridByBean(){
	var beanIn = new makeBeanIn(
			$('#selectKcjs').val(),
			$('#selectBmzt').val()
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
				<th style="width:100px">授课教师</th>
				<td><select id="selectKcjs" style="width: 100px"></select></td>
				<th style="width:100px">报名状态</th>
				<td><select id="selectBmzt" style="width: 100px"></select></td>
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