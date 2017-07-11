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
<title>我的学生</title>
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
       { title:'学生编码', name:'XSXX_XSBM' ,width:80, sortable:true, align:'center',lockDisplay: true },
       { title:'学生姓名', name:'XSXX_XSXM' ,width:80, sortable:true, align:'center',lockDisplay: true  },
       { title:'联系方式', name:'XSXX_LXFS' ,width:100, sortable:true, align:'center',lockDisplay: true  },
       { title:'住址', name:'XSXX_ZZ' ,width:300, sortable:true, align:'center',lockDisplay: true  },
       { title:'阶段年级', name:'JDNJ' ,width:100, sortable:true, align:'center',lockDisplay: true  },
       { title:'创建人', name:'XSXX_CJR' ,width:50,hidden: true,sortable:true, align:'center',lockDisplay: true },
       { title:'创建时间', name:'XSXX_CJSJ' ,width:100,hidden: true,sortable:true, align:'center',lockDisplay: true },
       { title:'修改人', name:'XSXX_GXR' ,width:50,hidden: true,sortable:true, align:'center',lockDisplay: true },
       { title:'修改时间', name:'XSXX_GXSJ' ,width:100,hidden: true,sortable:true, align:'center',lockDisplay: true },
       { title:'操作', name:'' ,width:30, sortable:true, align:'center',lockDisplay: true, renderer: function(val){
      		return '<img id="img-info" class="img-info" title="查看" src="<%=basePath%>/bin/img/detail.gif">&nbsp;</img><img id="img-send" class="img-send" title="发送" src="<%=basePath%>/bin/img/send.gif"></img>&nbsp;<img id="img-talk" class="img-talk" title="评价" src="<%=basePath%>/bin/img/talk.gif"></img>';
      }}
   ];
             
    intheight = document.documentElement.clientHeight -$('#selectRegion').height()-80; 
   if(intheight<100){
	   intheight = 100;
   }

   mmg = $('.mmg').mmGrid({
         height: intheight
        ,cols: cols
        ,url: '<%=basePath%>/Servlet3020130'
        ,method: 'post'
        ,params:{CMD : "<%=Servlet3020130.CMD_INDEX_SELECT%>"}
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
   mmg.on('cellSelected', function(e, item, rowIndex, colIndex){
	        if($(e.target).is('.img-info')){
	            e.stopPropagation();  //阻止事件冒泡
	            obj = new Object();
	            obj.stuId = item.XSXX_XSID;
	            iframeLayerOpen('<%=basePath%>/bin/jsp/3020000/3020131.jsp');
	        }
	        if($(e.target).is('.img-send')){
	            e.stopPropagation();  //阻止事件冒泡
	            obj = new Object();
	            obj.stuID = item.XSXX_XSID;
	            obj.stuBM = item.XSXX_XSBM;
	            obj.stuXM = item.XSXX_XSXM;
	            obj.stuDH = item.XSXX_LXFS;
	            iframeLayerOpen('<%=basePath%>/bin/jsp/3020000/3020132.jsp');
	        }
	        if($(e.target).is('.img-talk')){
	            e.stopPropagation();  //阻止事件冒泡
	            obj = new Object();
	            obj.stuID = item.XSXX_XSID;
	            obj.stuBM = item.XSXX_XSBM;
	            iframeLayerOpen('<%=basePath%>/bin/jsp/3020000/3020133.jsp');
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
function makeBeanIn(strXSXX_XSBM,strXSXX_XSXM){
    this.XSXX_XSBM = strXSXX_XSBM;
    this.XSXX_XSXM = strXSXX_XSXM;
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
				<th style="width:100px">学生编码</th>
				<td><input id="txtSelectCode" name="学生编码" maxlength="20" /></td>
				<th style="width:100px">学生姓名</th>
				<td><input id="txtSelectName" name="学生姓名" maxlength="20" /></td>
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