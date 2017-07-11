<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.xsjy.servlet.servlet1030000.Servlet1030130"%> 
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
<title>消息详情</title>
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
var obj;//参数对象
//初始化表格
$(document).ready(function(){
	//初始化数据
	obj = window.parent.dataBean;//参数对象
	$('#txtFBSJ').val(obj.XXXX_FBSJ);
	$('#txtYXSJ').val(obj.XXXX_YXSJ);
	$('#txtFBR').val(obj.XXXX_FBR);
	$('#txtFBNR').val(obj.XXXX_FBNR);
	$('#txtFBZT').val(obj.XXXX_FBZT);
   var cols = [
       { title:'姓名', name:'XM' ,width:60, sortable:true, align:'center',lockDisplay: true },
       { title:'联系方式', name:'LXFS' ,width:60, sortable:true, align:'center',lockDisplay: true  }      
   ];
   intheight = document.documentElement.clientHeight -$('#editRegion').height()-$('#selectRegion').height()-100; 
   if(intheight<100){
	   intheight = 100;
   }

      mmg = $('.mmg').mmGrid({
        height: intheight
        ,cols: cols
        ,url: '<%=basePath%>/Servlet1030130'
        ,method: 'post'
        ,params:{CMD : "<%=Servlet1030130.CMD_SELECTRY%>"}
        ,remoteSort:true
        ,sortName: 'LX'
        ,sortStatus: 'ASC'
        ,root: 'items'
        ,multiSelect: true
        ,checkCol: false
        ,indexCol: true
        ,indexColWidth: 35
        ,fullWidthRows: true
        ,autoLoad: false
        ,plugins: [
            $('#pg').mmPaginator({
              limit:20
            })
        ]
      }); 
      loadGridByBean();
});
function makeBeanIn(strXXID){
	this.XXID = strXXID;
}
function loadGridByBean(){
	var beanIn = new makeBeanIn(
			obj.XXXX_XXID
	);
	//重新查询数据
		mmg.load({
	    	beanLoad  :  JSON.stringify(beanIn)
		});
	
}
</script>
</head>
<body>
	<fieldset id = "editRegion">
		<table id="detailCanvas" class="eTable6">
			<tr>	
				<th style="width:100px">发布主题</th>	
				<td colspan="3"><input style="width:350px" id="txtFBZT" name="发布主题"  onfocus=this.blur() readonly /></td>
			</tr>
			<tr>	
			    <th style="width:100px">发布日期</th>	
				<td><input type="hidden" id="txtEditJFMXID"><input style="width:120px" id="txtFBSJ" name="发布日期" onfocus=this.blur() readonly /></td>
				<th style="width:100px">有效日期</th>	
				<td><input style="width:120px" id="txtYXSJ" name="有效日期"  onfocus=this.blur() readonly /></td>
			</tr>
			<tr>
				<th style="width:100px">发布人</th>	
				<td colspan="3"><input style="width:350px" id="txtFBR" name="发布人"  onfocus=this.blur() readonly /></td>
			</tr>
			<tr>
				<th style="width:100px">发布内容</th>	
				<td height="60" valign="middle" bgcolor="white" class="wo7" colspan="3"  >
						<textarea  id="txtFBNR"  style="width: 99%;height: 100%" onfocus=this.blur() readonly ></textarea>
				</td>
			</tr>
		</table>
	</fieldset>
	<br>
	<fieldset id="selectRegion">
			<legend id="cxq">接收人列表</legend>
		</fieldset>
	<div id="gridCanvas" >
		<table id="mmg" class="mmg"></table>
		<div id="pg" style="text-align: right;"></div>
	</div>
</body>
</html>