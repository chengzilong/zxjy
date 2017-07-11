<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.xsjy.servlet.servlet2020000.Servlet2020140"%> 
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
<title>我的老师</title>
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
       { title:'教师姓名', name:'JSXX_JSXM' ,width:80, sortable:true, align:'center',lockDisplay: true  },
       { title:'性别', name:'JSXB' ,width:40, sortable:true, align:'center',lockDisplay: true  },
       { title:'联系方式', name:'JSXX_LXFS' ,width:100, sortable:true, align:'center',lockDisplay: true  },
       { title:'学历', name:'JSXX_XL' ,width:100, sortable:true, align:'center',lockDisplay: true  },
       { title:'教学经验', name:'JSXX_JNJY' ,width:80, sortable:true, align:'center',lockDisplay: true  },
       { title:'教师级别', name:'JSXX_JSJB' ,width:80,hidden: true,sortable:true, align:'center',lockDisplay: true },
       { title:'操作', name:'' ,width:30, sortable:true, align:'center',lockDisplay: true, renderer: function(val){
      		return '<img id="img-talk" class="img-talk" title="评价" src="<%=basePath%>/bin/img/talk.gif"></img>';
      }}
   ];
             
    intheight = document.documentElement.clientHeight -$('#selectRegion').height()-80; 
   if(intheight<100){
	   intheight = 100;
   }

   mmg = $('.mmg').mmGrid({
         height: intheight
        ,cols: cols
        ,url: '<%=basePath%>/Servlet2020140'
        ,method: 'post'
        ,params:{CMD : "<%=Servlet2020140.CMD_INDEX_SELECT%>"}
        ,remoteSort:true
        ,sortName: 'JSXX_JSID'
        ,sortStatus: 'ASC'
        ,root: 'items'
        ,multiSelect: false
        ,checkCol: false
        ,indexCol: true
        ,indexColWidth: 35
        ,fullWidthRows: true
        ,autoLoad: false
        ,nowrap: true
        ,plugins: [
            $('#pg').mmPaginator({
              limit:30
            })
        ]
      });
   mmg.on('cellSelected', function(e, item, rowIndex, colIndex){
	        if($(e.target).is('.img-talk')){
	            e.stopPropagation();  //阻止事件冒泡
	            dataBean = item;
	            iframeLayerOpen('<%=basePath%>/bin/jsp/2020000/2020141.jsp');
	        }
	   });
	//重新查询数据
   $('#btnSearch').on('click', function(){
	   loadGridByBean();
   });
    //页面初始化加载数据
    loadGridByBean();
});
//定义bean
function makeBeanIn(strJSBM,strJSXM){
    this.JSXX_JSBM = strJSBM;
    this.JSXX_JSXM = strJSXM;
}
//加载数据
function loadGridByBean(){
	var beanIn = new makeBeanIn(
			$('#txtSelectCode').val(),
			$('#txtSelectName').val()
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
				<th style="width:100px">教师编码</th>
				<td><input id="txtSelectCode" name="教师编码" maxlength="20" /></td>
				<th style="width:100px">教师姓名</th>
				<td><input id="txtSelectName" name="教师姓名" maxlength="20" /></td>
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