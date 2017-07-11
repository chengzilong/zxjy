<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.xsjy.servlet.servlet1050000.Servlet1050140"%> 
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
       { title:'学生姓名', name:'XSXM' ,width:80, sortable:true, align:'center',lockDisplay: true },
       { title:'课程名称', name:'KCMC' ,width:80, sortable:true, align:'center',lockDisplay: true },
       { title:'课程类型', name:'KCLXMC' ,width:100, sortable:true, align:'center',lockDisplay: true },
       { title:'上课时段', name:'SKSD' ,width:80, sortable:true, align:'center',lockDisplay: true },
       { title:'学时', name:'BMXX_XS' ,width:80, sortable:true, align:'center',lockDisplay: true },
       { title:'授课教师', name:'KCJSXM' ,width:80, sortable:true, align:'center',lockDisplay: true },
       { title:'班次名称', name:'BCMC' ,width:150, sortable:true, align:'center',lockDisplay: true },
       { title:'定价', name:'BMXX_FY' ,width:50, sortable:true, align:'center',lockDisplay: true },
       { title:'已交费用', name:'BMXX_YJFY' ,width:50, sortable:true, align:'center',lockDisplay: true },
       { title:'未交费用', name:'BMXX_WJFY' ,width:50, sortable:true, align:'center',lockDisplay: true },
       { title:'报名方式', name:'BMFS' ,width:150, sortable:true, align:'center',lockDisplay: true },
       { title:'报名状态', name:'BMZT' ,width:50, sortable:true, align:'center',lockDisplay: true },
       { title:'推荐教师', name:'TJJSXM' ,width:80, sortable:true, align:'center',lockDisplay: true },
       { title:'备注信息', name:'BMXX_BZXX' ,width:200, sortable:true, align:'center',lockDisplay: true },
       { title:'登记人', name:'BMXX_CJR' ,width:80,sortable:true, align:'center',lockDisplay: true },
       { title:'登记时间', name:'BMXX_CJSJ' ,width:150,sortable:true, align:'center',lockDisplay: true },
       { title:'修改人', name:'BMXX_GXR' ,width:80,sortable:true, align:'center',lockDisplay: true,hidden:true },
       { title:'修改时间', name:'BMXX_GXSJ' ,width:100,sortable:true, align:'center',lockDisplay: true,hidden:true }
   ];
             
    intheight = document.documentElement.clientHeight -$('#selectRegion').height()-80; 
   if(intheight<100){
	   intheight = 100;
   }

   mmg = $('.mmg').mmGrid({
         height: intheight
        ,cols: cols
        ,url: '<%=basePath%>/Servlet1050140'
        ,method: 'post'
        ,params:{CMD : "<%=Servlet1050140.CMD_SELECT%>"}
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
    
    loadSearchSelect($("#selectKcmc"),"TYPE_KCMC","课程名称");
    loadSearchSelect($("#selectKclx"),"TYPE_KCLX","课程类型");
    loadSearchSelect($("#selectSksd"),"TYPE_SKSD","上课时段");
    loadSearchSelect($("#selectKcjs"),"TYPE_JSXM","课程教师");
    loadSearchSelect($("#selectBmfs"),"TYPE_BMFS","报名方式");
    loadSearchSelect($("#selectBmzt"),"TYPE_BMZT","报名状态");
    loadSearchSelect($("#selectTjjs"),"TYPE_JSXM","推荐教师");
});

function makeBeanIn(strXSXM,strBMXX_XXID,strBMXX_LXID,strBMXX_SDID,
		strBMXX_JSID,strBMXX_BMFS,strBMXX_BMZT,strBMXX_TJJSID){
    this.XSXM = strXSXM;
    this.BMXX_XXID = strBMXX_XXID;
    this.BMXX_LXID = strBMXX_LXID;
    this.BMXX_SDID = strBMXX_SDID;
    this.BMXX_JSID = strBMXX_JSID;
    this.BMXX_BMFS = strBMXX_BMFS;
    this.BMXX_BMZT = strBMXX_BMZT;
    this.BMXX_TJJSID = strBMXX_TJJSID;
}
//加载数据
function loadGridByBean(){
	var beanIn = new makeBeanIn(
			$('#txtSelectXsxm').val(),
			$('#selectKcmc').val(),
			$('#selectKclx').val(),
			$('#selectSksd').val(),
			$('#selectKcjs').val(),
			$('#selectBmfs').val(),
			$('#selectBmzt').val(),
			$('#selectTjjs').val()
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
				<th style="width:100px">学生姓名</th>
				<td><input id="txtSelectXsxm" name="学生姓名" maxlength="20" /></td>
				<th style="width:100px">课程名称</th>
				<td><select id="selectKcmc" style="width: 100px"></select></td>
				<th style="width:100px">课程类型</th>
				<td><select id="selectKclx" style="width: 100px"></select></td>
				<th style="width:100px">上课时段</th>
				<td><select id="selectSksd" style="width: 100px"></select></td>
			</tr>
			<tr>
				<th style="width:100px">授课教师</th>
				<td><select id="selectKcjs" style="width: 100px"></select></td>
				<th style="width:100px">报名方式</th>
				<td><select id="selectBmfs" style="width: 100px"></select></td>
				<th style="width:100px">报名状态</th>
				<td><select id="selectBmzt" style="width: 100px"></select></td>
				<th style="width:100px">推荐教师</th>
				<td><select id="selectTjjs" style="width: 100px"></select></td>
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