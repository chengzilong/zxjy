<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.xsjy.servlet.servlet3020000.Servlet3020110"%> 
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
<title>学生录入</title>
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
       { title:'学生编码', name:'XSXX_XSBM' ,width:100, sortable:true, align:'center',lockDisplay: true },
       { title:'学生姓名', name:'XSXX_XSXM' ,width:80, sortable:true, align:'center',lockDisplay: true  },
       { title:'性别', name:'XSXB' ,width:50, sortable:true, align:'center',lockDisplay: true  },
       { title:'出生日期', name:'XSXX_CSRQ' ,width:80, sortable:true, align:'center',lockDisplay: true  },
       { title:'联系方式', name:'XSXX_LXFS' ,width:150, sortable:true, align:'center',lockDisplay: true  },
       { title:'住址', name:'XSXX_ZZ' ,width:200, sortable:true, align:'center',lockDisplay: true  },
       { title:'阶段年级', name:'JDNJ' ,width:100, sortable:true, align:'center',lockDisplay: true  },
       { title:'个人简介', name:'XSXX_CRJJ' ,width:300, sortable:true, align:'center',lockDisplay: true  },
       { title:'创建人', name:'XSXX_CJR' ,width:50,hidden: true,sortable:true, align:'center',lockDisplay: true },
       { title:'创建时间', name:'XSXX_CJSJ' ,width:100,hidden: true,sortable:true, align:'center',lockDisplay: true },
       { title:'修改人', name:'XSXX_GXR' ,width:50,hidden: true,sortable:true, align:'center',lockDisplay: true },
       { title:'修改时间', name:'XSXX_GXSJ' ,width:100,hidden: true,sortable:true, align:'center',lockDisplay: true }
   ];
             
    intheight = document.documentElement.clientHeight -$('#editRegion').height()-$('#selectRegion').height()-80; 
   if(intheight<100){
	   intheight = 100;
   }

   mmg = $('.mmg').mmGrid({
         height: intheight
        ,cols: cols
        ,url: '<%=basePath%>/Servlet3020110'
        ,method: 'post'
        ,params:{CMD : "<%=Servlet3020110.CMD_SELECT%>"}
        ,remoteSort:true
        ,sortName: 'XSXX_XSID'
        ,sortStatus: 'ASC'
        ,root: 'items'
        ,multiSelect: false
        ,checkCol: true
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
   
	//重新查询数据
   $('#btnSearch').on('click', function(){
	   loadGridByBean();
   });
    //删除点击事件
    $('#btnDel').on('click', function(){
 	   var arrList = mmg.selectedRows();
 	   if(arrList.length<=0){
 		  layer.alert('请选择要删除的数据行！', 0, '友情提示');
  	       return;
 	   }
 	  layer.confirm('是否删除选中数据？', function() {
 		 if(deleteData(arrList[0].XSXX_XSID, arrList[0].XSXX_XSBM)==true){
 	 		   //重新查询数据
 	 		   loadGridByBean();
 	 		   setButtonStatus("2");
 	 		   mmg.deselect('all');
 	 	   }
 	  });
    });
    //页面初始化加载数据
    loadGridByBean();
    //初始化下拉列表
    loadSearchSelect($("#selectJd"),"TYPE_SSJD","阶段");
    loadSearchSelect($("#selectNj"),"TYPE_XSNJ","年级");
});

function btn_Add(){
	iframeLayerOpen('<%=basePath%>/bin/jsp/common/studentEdit.jsp?option=Add');
}
function btn_Upd(){
	var arrList = mmg.selectedRows();
	   if(arrList.length<=0){
		   layer.alert('请选择要修改的数据行！', 0, '友情提示');
	       return;
	   }
	layer.confirm('是否修改选中数据？', function() {
		layer.close(layer.index);
		dataBean = arrList[0];
		iframeLayerOpen('<%=basePath%>/bin/jsp/common/studentEdit.jsp?option=Upd');
	});
}

function makeBeanIn(strXSXX_XSID,strXSXX_XSBM,strXSXX_XSXM,strXSXX_JD,strXSXX_NJ){
    this.XSXX_XSID = strXSXX_XSID;
    this.XSXX_XSBM = strXSXX_XSBM;
    this.XSXX_XSXM = strXSXX_XSXM;
    this.XSXX_JD = strXSXX_JD;
    this.XSXX_NJ = strXSXX_NJ;
}
//加载数据
function loadGridByBean(){
	var beanIn = new makeBeanIn(
			"",
			$('#txtSelectCode').val(),
			$('#txtSelectName').val(),
			$('#selectJd').val(),
			$('#selectNj').val()
	);
	//重新查询数据
	mmg.load({
    	beanLoad  :  JSON.stringify(beanIn)
	});
}
//删除数据
function deleteData(dataId, xsbm){
	var blnRet = false;
	var beanIn = new makeBeanIn(
			dataId,xsbm,"","",""
	);
	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/Servlet3020110",
		data: {
			CMD    : "<%=Servlet3020110.CMD_DELETE%>",
			BeanIn : JSON.stringify(beanIn)
		},
		complete : function(response) {},
		success : function(response) {
			var strResult = response[0];
			if (strResult == "SUCCESS") {
				layer.msg('恭喜：删除数据成功！', 1, 9);
				blnRet = true;
			} else if (strResult == "FAILURE") {
				layer.msg('对不起：删除数据失败！', 1, 8);
				blnRet = false;
			} else {
				layer.msg('提示：删除数据出错！', 1, 0);
				blnRet = false;
	        }
		}
	});
	return blnRet;
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
				<th style="width:100px">阶段</th>
				<td><select id="selectJd" style="width: 100px"></select></td>
				<th style="width:100px">年级</th>
				<td><select id="selectNj" style="width: 100px"></select></td>
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
			<input type="button" value="新增" onclick="btn_Add()" />
			<input type="button" value="修改" onclick="btn_Upd()" /> 
			<input type="button" value="删除" id="btnDel" name="btnDel" /> 
		</div>
	</fieldset>
</body>
</html>