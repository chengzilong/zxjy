<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.xsjy.servlet.servlet1110000.Servlet1110130"%> 
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
$(document).ready(function(){
    //初始化下拉列表
    loadEditSelect($("#selectEditJd"),"TYPE_SSJD","阶段");
    loadEditSelect($("#selectEditNj"),"TYPE_XSNJ","年级");
    getInfo();
    //保存点击事件
  	$('#btnSave').on('click', function(){
		    if(funEditCheck()==false) return;
			layer.confirm('是否确定保存信息？', function() {
				if(updateData()==true){
		           layer.msg('恭喜：保存成功！', 1, 9);
		           window.parent.setUserName();
		        }else{
		           layer.msg('对不起：保存失败！', 1, 8);
		        }
	        });
    });
});
function getInfo(){
	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/Servlet1110130",
		data: {
			CMD    : "CMD_DATA"
		},
		complete : function(response) {},
		success : function(response) {
			var strResault = response[0];
			var dataBean = response[1];
			if (strResault == "SUCCESS") {
				$('#txtEditXsid').val(dataBean.XSXX_XSID);
			  	$('#txtEditXsbm').val(dataBean.XSXX_XSBM);
				$('#txtEditXsxm').val(dataBean.XSXX_XSXM);
				$("input[name='txtEditXsxb'][value='"+dataBean.XSXX_XB+"']").attr("checked", true);
				$('#txtEditCsrq').val(dataBean.XSXX_CSRQ);
				$('#txtEditLxfs').val(dataBean.XSXX_LXFS);
				$('#txtEditZz').val(dataBean.XSXX_ZZ);
				$('#selectEditJd').val(dataBean.XSXX_JD);
				$('#selectEditNj').val(dataBean.XSXX_NJ);
				$('#txtEditGrjj').val(dataBean.XSXX_CRJJ);
			} else if (strResault == "DATA_NULL") {
				layer.alert('取得个人信息出错！', 0, '友情提示');
			}else{
				layer.alert('系统异常！', 0, '友情提示');
			}
			
		}
	});
}
function makeBeanIn(strXSXX_XSID,strXSXX_XSBM,strXSXX_XSXM,strXSXX_XB,strXSXX_CSRQ,strXSXX_LXFS,strXSXX_ZZ,strXSXX_JD,strXSXX_NJ,strXSXX_CRJJ){
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
			$('#txtEditGrjj').val()
	);
    $.ajax({
      async     : false,
      type      : "post",
      dataType  : "json",
      url: "<%=basePath%>/Servlet1110130",
      data: {
         CMD    : "<%=Servlet1110130.CMD_UPDATE%>",
         BeanIn : JSON.stringify(beanIn)
      },
      complete :function(response){},
      success: function(response){
          var strResult = response[0];
          if (strResult == "SUCCESS") {
             blnRet = true;
          } else if (strResult == "FAILURE") {
             blnRet = false;
          }
      }
   });
   return blnRet;
}
function yanzhengxm(){
		if ($('#txtEditXsxm').val() == "") {
				$('#yzXM').show();
			}else{
				$('#yzXM').hide();
			}
}
function yanzhengfs(){
		if ($('#txtEditLxfs').val() == "") {
				$('#yzLXFS').show();
			}else{
				$('#yzLXFS').hide();
			}
}
//验证编辑输入数据
function funEditCheck() {
		if ($('#txtEditXsxm').val() == "") {
			$('#yzXM').show();
			$('#txtEditXsxm').focus();
			return false;
		}else{
			$('#yzXM').hide();
		}	
		if ($('#txtEditLxfs').val() == "") {
			$('#yzLXFS').show();
			$('#txtEditLxfs').focus();
			return false;
		}else{
			$('#yzXM').hide();
		}	
		return true;
}
</script>
</head>
<body style="background: none;">
<form id="studentForm" style="height:600px">
	<div id="main-content"  style="height:92%; width:97.5%;overflow:auto"> 			
		<div class="content-box chuan_mar2">				
			<div class="content-box-header">					
				<h3>学生基本信息</h3>					
<!-- 					<ul class="content-box-tabs"> -->
<!-- 						<li><a href="#tab1" class="default-tab">个人基础信息</a></li>						 -->
<!-- 					</ul>					 -->
				<div class="clear"></div>					
			</div>				
			<div class="content-box-content">					
				<div class="tab-content default-tab" id="tab1">						
				  <fieldset>								
					<p>
						<label>学生姓名：</label>
							<input type="hidden" id="txtEditXsid" />
							<input type="hidden" id="txtEditXsbm" />
							<input class="text-input small-input inputxt" type="text" id="txtEditXsxm" name="small-input"  onblur="yanzhengxm()"/><strong><span id="notLXFS" style="color:red;font-size:15px;"> *</span></strong><span id="yzXM"  style="display:none" class="input-notification error png_bg">姓名不能为空</span> 
					</p>
					<p>
						<label>性别：</label>
						<input type="radio" name="txtEditXsxb"  checked value="1" /> 男
						<input type="radio" name="txtEditXsxb" value="2" /> 女
					</p>
					<p>
						<label>出生日期：</label>
						<input class="text-input small-input datepicker" type="text" readonly="readonly" id="txtEditCsrq" name="medium-input" onclick="laydate()" /> 
					</p>
					<p>
						<label>联系方式：</label>
						<input class="text-input small-input datepicker inputxt" type="text" id="txtEditLxfs" name="medium-input"  onblur="yanzhengfs()"/><strong><span id="notLXFS" style="color:red;font-size:15px;"> *</span></strong><span id="yzLXFS"  style="display:none" class="input-notification error ">联系方式不能为空</span>
					</p>
					<p>
						<label>住址：</label>
						<input class="text-input medium-input datepicker" type="text" id="txtEditZz" name="medium-input" /> 
					</p>
					<p>
						<label>阶段年级：</label>
						<select class="small-input" id="selectEditJd"></select><select class="small-input" id="selectEditNj"></select> 
					</p>
					<p>
						<label>个人简介：</label>
						<textarea class="text-input textarea" id="txtEditGrjj" name="textfield" cols="79" rows="15"></textarea>
					</p>
					<p >
						<input class="button" type="button" value="保 存" id="btnSave" name="btnSave" />
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