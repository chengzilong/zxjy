<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.xsjy.servlet.servlet1040000.Servlet1040150"%> 
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
<title>班次查询</title>
<script type="text/javascript" src="<%=basePath%>/bin/js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="<%=basePath%>/bin/js/common.js"></script>
<script type="text/javascript" src="<%=basePath%>/bin/js/json2.js"></script>
<!--表格脚本Start  -->
<script type="text/javascript" src="<%=basePath%>/bin/js/mmgrid/mmGrid.js"></script>
<script type="text/javascript" src="<%=basePath%>/bin/js/mmgrid/mmPaginator.js"></script>
<!--表格脚本End  -->
<script type="text/javascript" src="<%=basePath%>/bin/js/laydate/laydate.js"></script>
<script type="text/javascript">
var mmg;
var intheight;
//初始化表格
$(document).ready(function(){
   var cols = [
       { title:'班次名称', name:'BCXX_BCMC' ,width:100, sortable:true, align:'center',lockDisplay: true },
       { title:'课程名称', name:'KCMC' ,width:100, sortable:true, align:'center',lockDisplay: true },
       { title:'开始时间', name:'BCXX_KSSJ' ,width:100, sortable:true, align:'center',lockDisplay: true },
       { title:'结束时间', name:'BCXX_JSSJ' ,width:100, sortable:true, align:'center',lockDisplay: true },
       { title:'上课地点', name:'BCXX_SKDD' ,width:200, sortable:true, align:'center',lockDisplay: true },
       { title:'班次状态', name:'BCZT' ,width:80, sortable:true, align:'center',lockDisplay: true },
       { title:'课程类型', name:'LXMC' ,width:80, sortable:true, align:'center',lockDisplay: true },
       { title:'上课时段', name:'SDMC' ,width:50, sortable:true, align:'center',lockDisplay: true },
       { title:'学时', name:'XS' ,width:50, sortable:true, align:'center',lockDisplay: true },
       { title:'授课教师', name:'JSXM' ,width:80, sortable:true, align:'center',lockDisplay: true },
       { title:'费用', name:'BCXX_FY' ,width:80, sortable:true, align:'center',lockDisplay: true }
   ];
             
   intheight = document.documentElement.clientHeight -$('#selectRegion').height()-80; 
   if(intheight<100){
	   intheight = 100;
   }

   mmg = $('.mmg').mmGrid({
         height: intheight
        ,cols: cols
        ,url: '<%=basePath%>/Servlet1040150'
        ,method: 'post'
        ,params:{CMD : "<%=Servlet1040150.CMD_SELECT%>"}
        ,remoteSort:true
        ,sortName: 'BCXX_BCID'
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
   /* 页面初始化加载数据 */
    loadGridByBean();
    /* 下拉列表初始化加载 */
    loadSearchSelect($("#selectKcmc"),"TYPE_KCMC","课程名称");
    /* 日期控件设置Start */
    var start = {
    	    elem: '#txtSelectKssj',
    	    format: 'YYYY-MM-DD',
    	    istime: false,
    	    istoday: false,
    	    choose: function(datas){
    	         end.min = datas; //开始日选好后，重置结束日的最小日期
    	         end.start = datas; //将结束日的初始值设定为开始日
    	    }
    	};
   	var end = {
   	    elem: '#txtSelectJssj',
   	 	format: 'YYYY-MM-DD',
   	    istime: false,
   	    istoday: false,
   	    choose: function(datas){
   	        start.max = datas; //结束日选好后，重置开始日的最大日期
   	    }
   	};
   	laydate(start);
   	laydate(end);
   	laydate.skin("default");
   	/* 日期控件设置End */
});

function makeBeanIn(strBCXX_BCMC, strBCXX_KCID, strBCXX_KSSJ, strBCXX_JSSJ, strBCXX_BCZT){
	this.BCXX_BCMC = strBCXX_BCMC;
    this.BCXX_KCID = strBCXX_KCID;
    this.BCXX_KSSJ = strBCXX_KSSJ;
    this.BCXX_JSSJ = strBCXX_JSSJ;
    this.BCXX_BCZT = strBCXX_BCZT;
}
//加载数据
function loadGridByBean(){
	var beanIn = new makeBeanIn(
			$('#txtSelectBcmc').val(),
			$('#selectKcmc').val(),
			$('#txtSelectKssj').val(),
			$('#txtSelectJssj').val(),
			$('#selectBczt').val()
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
				<th style="width:100px">班次名称</th>
				<td><input id="txtSelectBcmc" name="班次名称" maxlength="20" /></td>
				<th style="width:100px">课程名称</th>
				<td><select id="selectKcmc" style="width: 100px"></select></td>
				<th style="width:100px">上课时间</th>
				<td><input id="txtSelectKssj" name="开始时间" style="font-size:12px;line-height:16px; height:18px;"  class="laydate-icon-default" maxlength="20" readonly="readonly" /></td>
				<th style="width:20px">-</th>
				<td><input id="txtSelectJssj" name="结束时间"  style="font-size:12px;line-height:16px; height:18px;"  class="laydate-icon-default" maxlength="20" readonly="readonly" /></td>
				<th style="width:100px">班次状态</th>
				<td>
					<select id="selectBczt" style="width: 100px">
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