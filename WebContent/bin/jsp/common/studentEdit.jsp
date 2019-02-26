<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.xsjy.servlet.servlet1060000.Servlet1060110"%>
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
<link rel="stylesheet" href="<%=basePath%>/bin/css/resources/reset.css" type="text/css" media="screen" />
<link rel="stylesheet" href="<%=basePath%>/bin/css/resources/style.css" type="text/css" media="screen" />
<link rel="stylesheet" href="<%=basePath%>/bin/css/resources/invalid.css" type="text/css" media="screen" />
<link rel="stylesheet" href="<%=basePath%>/bin/css/Validform/style.css" type="text/css" media="screen" />
<!--表格样式End  -->
<title>学生基本信息</title>
<script type="text/javascript" src="<%=basePath%>/bin/js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="<%=basePath%>/bin/js/common.js"></script>
<script type="text/javascript" src="<%=basePath%>/bin/js/json2.js"></script>
<!--表单验证脚本Start  -->
<script type="text/javascript" src="<%=basePath%>/bin/js/Validform/Validform_v5.3.2.js"></script>
<!--表单验证脚本End  -->
<script type="text/javascript" src="<%=basePath%>/bin/js/laydate/laydate.js"></script>
<script type="text/javascript" src="<%=basePath%>/bin/js/layer/layer.min.js"></script>
<script type="text/javascript">
var optionFlag;
var oldXsbm;
$(document).ready(function(){
	/* 表单信息验证Start */
	var studentForm=$("#studentForm").Validform({
		tiptype:4,
		label:".label",
		showAllError:true,
		datatype:{
			"zh1-6":/^[\u4E00-\u9FA5\uf900-\ufa2d]{1,6}$/
		},
		ajaxPost:true
	});

	studentForm.addRule([{
		ele:".inputxt:eq(0)",
		datatype:"*6-20"
	},
	{
		ele:".inputxt:eq(1)",
		datatype:"s"
	},
	{
		ele:".inputxt:eq(2)",
		datatype:"m"
	}]);
	//初始化下拉列表
    loadEditSelect($("#selectEditJd"),"TYPE_SSJD","阶段");
	/* 表单信息验证End */
	$.getUrlParam = function(name) {
		var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
		var r = window.location.search.substr(1).match(reg);
		if (r!=null) return unescape(r[2]); return null;
	};
	optionFlag = $.getUrlParam('option');
	if(optionFlag == "Upd"){
		var dataBean = window.parent.dataBean;
		oldXsbm = dataBean.XSXX_XSBM;
	  	$('#txtEditXsid').val(dataBean.XSXX_XSID);
	  	$('#txtEditXsbm').val(oldXsbm);
		$('#txtEditXsxm').val(dataBean.XSXX_XSXM);
		$("input[name='txtEditXsxb'][value='"+dataBean.XSXX_XB+"']").attr("checked", true);
		$('#txtEditCsrq').val(dataBean.XSXX_CSRQ);
		$('#txtEditLxfs').val(dataBean.XSXX_LXFS);
		$('#txtEditZz').val(dataBean.XSXX_ZZ);
		$('#selectEditJd').val(dataBean.XSXX_JD);
		changeJD();
		$('#selectEditNj').val(dataBean.XSXX_NJ);
		$('#txtEditGrjj').val(dataBean.XSXX_CRJJ);
	}
    //保存点击事件（新增/更新）
    $('#btnSave').on('click', function(){
    	if(optionFlag == "Add"){
    		if(funEditCheck()==false) return;
    		layer.confirm('是否新增数据？', function() {
    			if(insertData()==true){
        			iframeLayerClose();
    			}
    		});
    	}else if(optionFlag == "Upd"){
    		if(funEditCheck()==false) return;
    		layer.confirm('是否修改选中数据？', function() {
	    		if(updateData()==true){
	    			iframeLayerClose();
		        }
    		});
    	}
    });
    //取消点击事件
    $('#btnCancel').on('click', function(){
    	iframeLayerClosenoRefresh();
    });
});
//定义bean
function makeBeanIn(strXSXX_XSID,strXSXX_XSBM,strXSXX_XSXM,strXSXX_XB,strXSXX_CSRQ,strXSXX_LXFS,strXSXX_ZZ,strXSXX_JD,strXSXX_NJ,strXSXX_CRJJ,strOLD_XSBM){
    this.XSXX_XSID = strXSXX_XSID;
    this.XSXX_XSBM = strXSXX_XSBM;
    this.XSXX_XSXM = strXSXX_XSXM;
    this.XSXX_XB = strXSXX_XB;
    this.XSXX_CSRQ = strXSXX_CSRQ;
    this.XSXX_LXFS = strXSXX_LXFS;
    this.XSXX_ZZ = strXSXX_ZZ;
    this.XSXX_JD = strXSXX_JD;
    this.XSXX_NJ = strXSXX_NJ;
    this.XSXX_CRJJ = strXSXX_CRJJ;
    this.OLD_XSBM = strOLD_XSBM;
}
//新增数据
function insertData(){
	var blnRet = false;
	var beanIn = new makeBeanIn(
			"",
			$('#txtEditXsbm').val(),
			$('#txtEditXsxm').val(),
			$('input:radio[name=txtEditXsxb]:checked').val(),
			$('#txtEditCsrq').val(),
			$('#txtEditLxfs').val(),
			$('#txtEditZz').val(),
			$('#selectEditJd').val(),
			$('#selectEditNj').val(),
			$('#txtEditGrjj').val(),
			""
	);
	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/Servlet1060110",
		data: {
			CMD    : "<%=Servlet1060110.CMD_INSERT%>",
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
//更新数据
function updateData(){
	var blnRet = false;
	var beanIn = new makeBeanIn(
			$('#txtEditXsid').val(),
			$('#txtEditXsbm').val(),
			$('#txtEditXsxm').val(),
			$('input:radio[name=txtEditXsxb]:checked').val(),
			$('#txtEditCsrq').val(),
			$('#txtEditLxfs').val(),
			$('#txtEditZz').val(),
			$('#selectEditJd').val(),
			$('#selectEditNj').val(),
			$('#txtEditGrjj').val(),
			oldXsbm
	);
    $.ajax({
      async     : false,
      type      : "post",
      dataType  : "json",
      url: "<%=basePath%>/Servlet1060110",
      data: {
         CMD    : "<%=Servlet1060110.CMD_UPDATE%>",
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
	if ($('#txtEditXsbm').val() != "" && $('#txtEditXsxm').val() != "" && $('#txtEditLxfs').val() != "") {
		if (optionFlag == "Add") {//新增：判断是否学生编码已存在
			if (checkExist() == true) {
				layer.msg('学生编码已存在，无法重复新增！', 1, 0);
				return false;
			}
		} else if (optionFlag == "Upd") {//修改：判断是否学生编码已存在
			if ($('#txtEditXsbm').val() != oldXsbm) {
				if (checkExist() == true) {
					layer.msg('学生编码已存在，无法进行修改！', 1, 0);
					return false;
				}
			}
		}
		return true;
	} else {
		return false;
	}
}
//验证数据重复
function checkExist(){
	var blnRet = false;
	var beanIn = new makeBeanIn(
			"",$('#txtEditXsbm').val(),"","","","","","","",""
	);
	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/Servlet1060110",
		data: {
			CMD    : "<%=Servlet1060110.CMD_CHK_EXIST%>",
			BeanIn : JSON.stringify(beanIn)
		},
		complete : function(response) {
		},
		success : function(response) {
			var strResult = response[0];
			if (strResult == "DATA_EXIST") {
				blnRet = true;
			} else if (strResult == "DATA_NOT_EXIST") {
				blnRet = false;
			} else {
				layer.msg('提示：验证新增数据重复时出错！', 1, 0);
				blnRet = false;
			}
		}
	});
	return blnRet;
}
function changeJD(){
    if( $('#selectEditJd').val()=="000"){
        $("#selectEditNj").empty();
	    $("#selectEditNj").append("<option value='000' selected>请选择</option>");
    }else if( $('#selectEditJd').val()=="0"){
	    $("#selectEditNj").empty();
	    $("#selectEditNj").append("<option value='000' selected>请选择</option>");
		$("#selectEditNj").append("<option value='1'>一年级</option>");
		$("#selectEditNj").append("<option value='2'>二年级</option>");
		$("#selectEditNj").append("<option value='3'>三年级</option>");
		$("#selectEditNj").append("<option value='4'>四年级</option>");
		$("#selectEditNj").append("<option value='5'>五年级</option>");
		$("#selectEditNj").append("<option value='6'>六年级</option>");
	}else if( $('#selectEditJd').val()=="1"){
	    $("#selectEditNj").empty();
	    $("#selectEditNj").append("<option value='000' selected>请选择</option>");
		$("#selectEditNj").append("<option value='1'>一年级</option>");
		$("#selectEditNj").append("<option value='2'>二年级</option>");
		$("#selectEditNj").append("<option value='3'>三年级</option>");
	}else if( $('#selectEditJd').val()=="2"){
	    $("#selectEditNj").empty();
	    $("#selectEditNj").append("<option value='000' selected>请选择</option>");
		$("#selectEditNj").append("<option value='1'>一年级</option>");
		$("#selectEditNj").append("<option value='2'>二年级</option>");
		$("#selectEditNj").append("<option value='3'>三年级</option>");
	}else if( $('#selectEditJd').val()=="3"){
	    $("#selectEditNj").empty();
	    $("#selectEditNj").append("<option value='000' selected>所有</option>");
		$("#selectEditNj").append("<option value='1'>一年级</option>");
		$("#selectEditNj").append("<option value='2'>二年级</option>");
		$("#selectEditNj").append("<option value='3'>三年级</option>");
		$("#selectEditNj").append("<option value='4'>四年级</option>");
	}

}
</script>
</head>
<body style="background: none;">
<form id="studentForm">
	<div id="main-content" >
		<div class="content-box chuan_mar2">
			<div class="content-box-header">
				<h3>学生基本信息</h3>
				<div class="clear"></div>
			</div>
			<div class="content-box-content">
				<div class="tab-content default-tab" id="tab1">
				  <fieldset>
					<p>
						<label>学生编码：</label>
							<input class="text-input small-input" type="hidden" id="txtEditXsid" name="small-input" />
							<input class="text-input small-input inputxt" type="text" id="txtEditXsbm" name="small-input" />
					</p>
					<p>
						<label>学生姓名：</label>
							<input class="text-input small-input inputxt" type="text" id="txtEditXsxm" name="small-input" />
					</p>
					<p>
						<label>性别：</label>
						<input type="radio" name="txtEditXsxb" value="1"  checked/> 男
						<input type="radio" name="txtEditXsxb" value="2" /> 女
					</p>
					<p>
						<label>出生日期：</label>
						<input class="text-input medium-input datepicker" type="text" readonly="readonly" id="txtEditCsrq" name="medium-input" onclick="laydate()" />
					</p>
					<p>
						<label>联系方式：</label>
						<input class="text-input medium-input datepicker inputxt" type="text" id="txtEditLxfs" name="medium-input" />
					</p>
					<p>
						<label>住址：</label>
						<input class="text-input medium-input datepicker" type="text" id="txtEditZz" name="medium-input" />
					</p>
					<p>
						<label>阶段年级：</label>
						<select class="small-input" id="selectEditJd" onchange="changeJD()"></select>
                        <select class="small-input" id="selectEditNj">
                          <option value="000" selected>请选择</option>
                        </select>
					</p>
					<p>
						<label>个人简介：</label>
						<textarea class="text-input textarea" id="txtEditGrjj" name="textfield" cols="79" rows="15"></textarea>
					</p>
					<p >
						<input class="button" type="submit" value="保 存" id="btnSave" name="btnSave" />&nbsp;&nbsp;<input class="button"  type="submit" value="取 消" id="btnCancel" name="btnCancel" />
					</p>
				  </fieldset>
				</div>
			</div>
		</div>
		<div class="clear"></div>
	</div>
</form>
</body>
</html>