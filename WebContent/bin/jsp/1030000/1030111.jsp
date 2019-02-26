<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.xsjy.servlet.servlet1030000.Servlet1030110"%>
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
<title>消息发布-添加修改二级页</title>
<script type="text/javascript" src="<%=basePath%>/bin/js/laydate/laydate.js"></script>
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
var optionFlag;//操作标识
var myDate = new Date();   //获取系统日期
var year = "";
var month = "";
var day = "";
var timeStr = "";
year = myDate.getFullYear();    //获取完整的年份(4位,1970-????)
month = myDate.getMonth()+1;       //获取当前月份(0-11,0代表1月)
if(month<10)
	{
	month = "0"+month;
	}
day = myDate.getDate();        //获取当前日(1-31)
if(day < 10) {
	day = "0"+day;
}
timeStr = year+"-"+month+"-"+day;

//初始化表格
$(document).ready(function(){
	$('#txtEditFBSJ').val(timeStr);
	/* 初始化传递参数Start */
	$.getUrlParam = function(name) {
		var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
		var r = window.location.search.substr(1).match(reg);
		if (r!=null) return unescape(r[2]); return null;
	};
	optionFlag = $.getUrlParam('option');
	/* 初始化传递参数End */
	/* 初始化数据 */
	if(optionFlag == "Upd"){
		var obj = window.parent.dataBean;//参数对象
		$('#txtEditXXID').val(obj.XXXX_XXID);
        $('#txtEditFBSJ').val(obj.XXXX_FBSJ);
        $('#txtEditYXSJ').val(obj.XXXX_YXSJ);
        $('#txtEditFBZT').val(obj.XXXX_FBZT);
        $('#txtEditFBNR').val(obj.XXXX_FBNR);
	}
	 var cols = [
          { title:'姓名22', name:'XM' ,width:60, sortable:true, align:'center',lockDisplay: true },
          { title:'联系方式', name:'LXFS' ,width:60, sortable:true, align:'center',lockDisplay: true  },
          { title:'类型', name:'LX' ,width:100, sortable:true, align:'center',lockDisplay: true  },
      ];
      intheight = document.documentElement.clientHeight -$('#editRegion').height()-$('#selectRegion').height()-80;
      if(intheight<100){
   	   intheight = 100;
      }

      mmg = $('.mmg').mmGrid({
        height: intheight
        ,cols: cols
        ,url: '<%=basePath%>/Servlet1030110'
        ,method: 'post'
        ,params:{CMD : "<%=Servlet1030110.CMD_SELECTRY%>"}
        ,remoteSort:true
        ,sortName: 'LX'
        ,sortStatus: 'ASC'
        ,root: 'items'
        ,multiSelect: true
        ,checkCol: true
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
    //定义表格事件
      mmg.on('loadSuccess', function(e, data) {
    	  mmg.select(function(item, index) {
    		  if (item.JSRID != "") {
    			  return true;
    		  }
    	  });
	   });
  //保存点击事件（新增/更新）
    $('#btnSave').on('click', function(){
    	var strID = "";
   	   var strXM = "";
   	   var strLXFS = "";
    	var arrList = mmg.selectedRows();
		if(arrList.length <= 0){
			layer.alert('请选择消息接收人！', 0, '友情提示');
		}else{
			for(var i = 0; i < arrList.length; i++) {
				strID += "'" + arrList[i].ID + "',";
				strXM += "'" + arrList[i].XM + "',";
				strLXFS += "'" + arrList[i].LXFS + "',";
			}
		}
    	if(optionFlag == "Add"){
    		if(funEditCheck()==false) return;
    		layer.confirm('是否添加消息？', function() {
    			if(insertData(strID,strXM,strLXFS)==true){
        			iframeLayerClose();
    			}
    		});
    	}else if(optionFlag == "Upd"){
    		if(funEditCheck()==false) return;
    		layer.confirm('是否修改消息？', function() {
    			if(updateData(strID,strXM,strLXFS)==true){
        			iframeLayerClose();
    	        }
    		});
    	}
    });
  //查询条件
  	$('#btnSearch').on('click', function(){
		loadGridByBean();
	});
  //取消点击事件
    $('#btnCancel').on('click', function(){
    	iframeLayerClosenoRefresh();
    });
  //初始化加载列表数据
	loadGridByBean();
});

function makeBeanIn(strXXID,strFBSJ,strYXSJ,strFBZT,strFBNR, strXSXM, strJSXM, strID, strXM, strLXFS){
    this.XXXX_XXID = strXXID;
    this.XXXX_FBSJ = strFBSJ;
    this.XXXX_YXSJ = strYXSJ;
    this.XXXX_FBZT = strFBZT;
    this.XXXX_FBNR = strFBNR;
    this.XSXM = strXSXM;
	this.JSXM = strJSXM;
	this.ID = strID;
	this.XM = strXM;
	this.LXFS = strLXFS;
}

function loadGridByBean(){
	var beanIn = new makeBeanIn(
			$('#txtEditXXID').val(),"","","","",
			$('#txtEditXSXM').val(),
			$('#txtEditJSXM').val(),
			"","",""
	);
	//重新查询数据
		mmg.load({
	    	beanLoad  :  JSON.stringify(beanIn)
		});
}
//新增消息
function insertData(strID,strXM,strLXFS){
	var blnRet = false;
	var beanIn = new makeBeanIn(
			"",
           $('#txtEditFBSJ').val(),
           $('#txtEditYXSJ').val(),
           $('#txtEditFBZT').val(),
           $('#txtEditFBNR').val(),
           "","",
           strID,
           strXM,
           strLXFS
	);
	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/Servlet1030110",
		data: {
			CMD    : "<%=Servlet1030110.CMD_INSERT%>",
		    BeanIn : JSON.stringify(beanIn)
		},
		complete :function(response){},
		success: function(response){
			var strResault = response[0];
			if(strResault=="SUCCESS"){
				blnRet = true;
			}else{
				layer.msg('对不起：发布消息失败！', 1, 8);
				blnRet = false;
			}
		}
	});
	return blnRet;
}
//调整消息
function updateData(strID,strXM,strLXFS){
	var blnRet = false;
	var beanIn = new makeBeanIn(
		   $('#txtEditXXID').val(),
           $('#txtEditFBSJ').val(),
           $('#txtEditYXSJ').val(),
           $('#txtEditFBZT').val(),
           $('#txtEditFBNR').val(),
           "","",
           strID,
           strXM,
           strLXFS
	);

    $.ajax({
      async     : false,
      type      : "post",
      dataType  : "json",
      url: "<%=basePath%>/Servlet1030110",
      data: {
         CMD    : "<%=Servlet1030110.CMD_UPDATE%>",
         BeanIn : JSON.stringify(beanIn)
      },
      complete :function(response){},
      success: function(response){
          var strResault = response[0];
          if(strResault=="SUCCESS") {
        	layer.msg('恭喜：修改消息成功！', 1, 9);
			blnRet = true;
          }else if(strResault=="ERROR"){
        	 layer.msg('对不起：修改消息失败！', 1, 8);
             blnRet = false;
          }
      }
   });
   return blnRet;
}
//验证编辑输入数据
function funEditCheck() {
	if (optionFlag == "Add" || optionFlag == "Upd") {
		if ($('#txtEditFBSJ').val() == "") {
			layer.alert('请输入发布时间！', 0, '友情提示', function() {
				$('#txtEditFBSJ').focus();
				layer.close(layer.index);
			});
			return false;
		}
		if ($('#txtEditYXSJ').val() == "") {
			layer.alert('请输入有效时间！', 0, '友情提示', function() {
				$('#txtEditYXSJ').focus();
				layer.close(layer.index);
			});
			return false;
		}
		if ($('#txtEditFBZT').val() == "") {
			layer.alert('请输入发布主题！', 0, '友情提示', function() {
				$('#txtEditFBZT').focus();
				layer.close(layer.index);
			});
			return false;
		}
		if ($('#txtEditFBNR').val() == "") {
			layer.alert('请输入发布内容！', 0, '友情提示', function() {
				$('#txtEditFBNR').focus();
				layer.close(layer.index);
			});
			return false;
		}
	}
	return true;
}
</script>
</head>
<body>
	<fieldset id = "editRegion">
		<legend>编辑</legend>
		<table id="detailCanvas" class="eTable6">
			<tr>
				<th style="width:100px">发布时间</th>
				<td><input type="hidden" id="txtEditXXID" /><input id="txtEditFBSJ" name="发布时间" style="font-size:12px;line-height:16px; height:18px;"  class="laydate-icon-default" onclick="laydate()" readonly/></td>
				<th style="width:100px">有效时间</th>
				<td><input id="txtEditYXSJ" name="有效时间"  style="font-size:12px;line-height:16px; height:18px;"  class="laydate-icon-default" onclick="laydate()" readonly/></td>
			</tr>
			<tr>
				<th style="width:100px">发布主题</th>
				<td colspan="3"><input id=txtEditFBZT name="发布主题" maxlength="200" style="width: 450px" /></td>
			</tr>
			<tr>
				<th style="width:100px">发布内容</th>
				<td height="60" valign="middle" bgcolor="white" class="wo7" colspan="3"  >
						<textarea  id="txtEditFBNR"  style="width: 89%;height: 100%"></textarea>
				</td>
			</tr>
		</table>
		<div id="buttonCanvas" class="gToolbar gTbrCenter ">
			<input type="button" value="发布" id="btnSave" name="btnSave" />
			<input type="button" value="取消" id="btnCancel" name="btnCancel" />

		</div>
	</fieldset>
	<fieldset id = "selectRegion"  >
		<table>
				<tr>
					<th style="width:100px">学生姓名</th>
					<td><input style="width:120px" id="txtEditXSXM" name="学生姓名" maxlength="20" /></td>
					<th style="width:100px">教师姓名</th>
					<td><input style="width:120px" id="txtEditJSXM" name="教师姓名" maxlength="20" /></td>
					<th  style="width:100px"><input type="button" value="查询" id="btnSearch" /></th>
				</tr>
		</table>
	</fieldset>
	<div id="gridCanvas" >
		<table id="mmg" class="mmg"></table>
		<div id="pg" style="text-align: right;"></div>
	</div>
</body>
</html>