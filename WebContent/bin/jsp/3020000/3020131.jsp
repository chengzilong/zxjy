<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.xsjy.servlet.servlet3020000.Servlet3020130"%> 
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
<title>我的学生-详情</title>
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
var obj;//参数对象
//初始化表格
$(document).ready(function(){
	/* 初始化传递参数Start */
	obj = window.parent.obj;
	/* 初始化表格 */
   var cols = [
       { title:'学生编码', name:'XSXX_XSBM' ,width:100, sortable:true, align:'center',lockDisplay: true },
       { title:'学生姓名', name:'XSXX_XSXM' ,width:80, sortable:true, align:'center',lockDisplay: true  },
       { title:'班次名称', name:'BCMC' ,width:100, sortable:true, align:'center',lockDisplay: true  },
       { title:'班次状态', name:'BCZTMC' ,width:60, sortable:true, align:'center',lockDisplay: true  },
       { title:'课程名称', name:'KCMC' ,width:80, sortable:true, align:'center',lockDisplay: true  },
       { title:'上课时段', name:'SKSDMC' ,width:100, sortable:true, align:'center',lockDisplay: true  },
       { title:'学时', name:'XS' ,width:60, sortable:true, align:'center',lockDisplay: true  },
       { title:'上课地点', name:'SKDD' ,width:300, sortable:true, align:'center',lockDisplay: true  }
   ];
             
    intheight = document.documentElement.clientHeight -$('#editRegion').height()-$('#selectRegion').height()-80; 
   if(intheight<100){
	   intheight = 100;
   }

   mmg = $('.mmg').mmGrid({
         height: intheight
        ,cols: cols
        ,url: '<%=basePath%>/Servlet3020130'
        ,method: 'post'
        ,params:{CMD : "<%=Servlet3020130.CMD_INDEX_DETAIL%>"}
        ,remoteSort:true
        ,sortName: 'XSXX_XSID'
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
    //页面初始化加载数据
    loadGridByBean();
});
//定义bean
function makeBeanIn(strXSXX_XSID){
    this.XSXX_XSID = strXSXX_XSID;
}
//加载数据
function loadGridByBean(){
	var beanIn = new makeBeanIn(
			obj.stuId
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
		<legend>明细信息</legend>
	</fieldset>
	<div id="gridCanvas">
		<table id="mmg" class="mmg"></table>
		<div id="pg" style="text-align: right;"></div>
	</div>
</body>
</html>