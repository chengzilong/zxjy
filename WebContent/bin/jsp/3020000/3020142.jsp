<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.xsjy.servlet.servlet3020000.Servlet3020140"%>
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
//设置默认当天时间
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

var optionFlag;//操作标识
var userid;
var obj;//参数对象
//初始化表格
$(document).ready(function(){
	/* 初始化传递参数Start */
	obj = window.parent.obj;
	optionFlag = obj.optionFlag;
	userid = obj.userId;
	/* 初始化传递参数End */
	if(optionFlag=="Add"){
		$("#txtEditFBSJ").val(timeStr);
	}
	//定义列值
	 var cols = [
          { title:'学生编码', name:'XSXX_XSBM' ,width:100, sortable:true, align:'center',lockDisplay: true },
          { title:'学生姓名', name:'XSXX_XSXM' ,width:80, sortable:true, align:'center',lockDisplay: true },
          { title:'联系方式', name:'XSXX_LXFS' ,width:100, sortable:true, align:'center',lockDisplay: true  },
          { title:'住址', name:'XSXX_ZZ' ,width:200, sortable:true, align:'center',lockDisplay: true  },
      ];
	//定义高度
      intheight = document.documentElement.clientHeight -$('#editRegion').height()-$('#selectRegion').height()-80; 
      if(intheight<100){
   	   intheight = 100;
      }
	//定义表格对象
      mmg = $('.mmg').mmGrid({
        height: intheight
        ,cols: cols
        ,url: '<%=basePath%>/Servlet3020140'
        ,method: 'post'
        ,params:{CMD : "<%=Servlet3020140.CMD_STU_SELECT%>"}
        ,remoteSort:true
        ,sortName: 'XSXX_XSID'
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
    	var stuInfos = "";
    	var arrList = mmg.selectedRows();
		if(arrList.length <= 0){
			layer.alert('请选择消息接收人！', 0, '友情提示');
			return;
		}else{
			for(var i = 0; i < arrList.length; i++) {
				stuInfos += arrList[i].XSXX_XSID + "-" + arrList[i].XSXX_XSXM + "-" + arrList[i].XSXX_LXFS + ",";
			}
		}
    	if(optionFlag == "Add"){
    		if(funEditCheck()==false) return;
    		layer.confirm('是否添加消息？', function() {
    			if(insertData(stuInfos)==true){
    				iframeLayerClose();
    			}
    		});
    	}else if(optionFlag == "Upd"){
    		if(funEditCheck()==false) return;
    		layer.confirm('是否修改消息？', function() {
    			if(updateData(stuInfos)==true){
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
    /* 初始化数据 */
	if(optionFlag == "Upd"){
		$('#txtEditXXID').val(obj.XXXX_XXID);
        $('#txtEditFBSJ').val(obj.XXXX_FBSJ);
        $('#txtEditYXSJ').val(obj.XXXX_YXSJ);
        $('#txtEditFBZT').val(obj.XXXX_FBZT);
        $('#txtEditFBNR').val(obj.XXXX_FBNR);
	}
    //初始化下拉列表数据
	loadSearchSelect($("#selectBCMC"),"TYPE_BCMC-"+userid,"班次名称");
});
//定义查询bean
function makeBeanInSearch(strXXID,strBCID){
  this.XXID = strXXID;
  this.BCID = strBCID;
}
//查询消息方法
function loadGridByBean(){
	var beanIn = new makeBeanInSearch(
			obj.XXXX_XXID, $('#selectBCMC').val()
	);
	//重新查询数据
		mmg.load({
	    	beanLoad  :  JSON.stringify(beanIn)
		});
}
//定义编辑bean
function makeBeanInEdit(strXXID,strFBZT,strFBNR,strFBSJ,strYXSJ,strSTUINFO){
    this.XXXX_XXID = strXXID;
    this.XXXX_FBZT = strFBZT;
    this.XXXX_FBNR = strFBNR;
    this.XXXX_FBSJ = strFBSJ;
    this.XXXX_YXSJ = strYXSJ;
    this.STUINFO = strSTUINFO;
}
//新增消息方法
function insertData(stuInfos){
	var blnRet = false;
	var beanIn = new makeBeanInEdit(
			"",
           $('#txtEditFBZT').val(),
           $('#txtEditFBNR').val(),
           $('#txtEditFBSJ').val(),
           $('#txtEditYXSJ').val(),
           stuInfos
	);
	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/Servlet3020140",
		data: {
			CMD    : "<%=Servlet3020140.CMD_INSERT%>",
		    BeanIn : JSON.stringify(beanIn)
		},
		complete :function(response){},
		success: function(response){
			var strResult = response[0];
			if (strResult == "SUCCESS") {
				layer.msg('恭喜：新增数据成功！', 1, 9);
				blnRet = true;
			} else if (strResult == "FAILURE") {
				layer.msg('对不起：新增数据失败！', 1, 8);
				blnRet = false;
			} else {
				layer.msg('提示：新增数据出错！', 1, 0);
				blnRet = false;
	        }
		}
	});
	return blnRet;
}
//调整消息方法
function updateData(stuInfos){
	var blnRet = false;
	var beanIn = new makeBeanInEdit(
		   $('#txtEditXXID').val(),
           $('#txtEditFBZT').val(),
           $('#txtEditFBNR').val(),
           $('#txtEditFBSJ').val(),
           $('#txtEditYXSJ').val(),
           stuInfos
	);
    $.ajax({
      async     : false,
      type      : "post",
      dataType  : "json",
      url: "<%=basePath%>/Servlet3020140",
      data: {
         CMD    : "<%=Servlet3020140.CMD_UPDATE%>",
         BeanIn : JSON.stringify(beanIn)
      },
      complete :function(response){},
      success: function(response){
    	  var strResult = response[0];
			if (strResult == "SUCCESS") {
				layer.msg('恭喜：修改数据成功！', 1, 9);
				blnRet = true;
			} else if (strResult == "FAILURE") {
				layer.msg('对不起：修改数据失败！', 1, 8);
				blnRet = false;
			} else {
				layer.msg('提示：修改数据出错！', 1, 0);
				blnRet = false;
	        }
      }
   });
   return blnRet;
}
//验证编辑输入数据
function funEditCheck() {
	if (optionFlag == "Add" || optionFlag == "Upd") {
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
				<td><input type="hidden" id="txtEditXXID" /><input id="txtEditFBSJ"  style="font-size:12px;line-height:16px; height:18px;"  class="laydate-icon-default" name="发布时间"  onclick="laydate()" readonly/></td>
				<th style="width:100px">有效时间</th>	
				<td><input id="txtEditYXSJ"  style="font-size:12px;line-height:16px; height:18px;"  class="laydate-icon-default" name="有效时间"  onclick="laydate()" readonly/></td>
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
					<th style="width:80px">班次名称</th>
					<td>
						<select id=selectBCMC style="width: 100px"> 
						</select> 
					</td>
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