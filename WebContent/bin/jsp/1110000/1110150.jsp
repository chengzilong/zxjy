<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.xsjy.servlet.servlet1110000.Servlet1110150"%> 
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

<link rel="stylesheet" href="<%=basePath%>/bin/css/mmgrid/normalize.css" type="text/css">
<link rel="stylesheet" href="<%=basePath%>/bin/css/mmgrid/tablesize.css" type="text/css">
<link rel="stylesheet" href="<%=basePath%>/bin/css/mmgrid/style.css" type="text/css">
<!--表格样式End  -->
<title>个人信息</title>
<script type="text/javascript" src="<%=basePath%>/bin/js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="<%=basePath%>/bin/js/common.js"></script>
<script type="text/javascript" src="<%=basePath%>/bin/js/json2.js"></script>
<script type="text/javascript" src="<%=basePath%>/bin/js/layer/layer.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
		getValue();  
});
function makeBeanInedit(strUUID,strYHMC,strYHMM){
    this.YHXX_UUID = strUUID;  
    this.YHXX_YHMC = strYHMC;  
    this.YHXX_YHMM = strYHMM;  
}
function makeBeanIn(strUUID,strYMM){
    this.YHXX_UUID = strUUID;  
    this.YHXX_YHMM = strYMM;  
}
function updatePassword(){
	var blnRet = false;
	var beanIn = new makeBeanInedit(
		   $('#txtUUID').val(),
           $('#txtYHMC').val(),
           $('#txtXMM').val()  
	);
	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/Servlet1110150",
		data: {
			CMD    : "<%=Servlet1110150.CMD_UPDATE%>",
		    BeanIn : JSON.stringify(beanIn)
		},
		complete :function(response){},
		success: function(response){
			var strResault = response[0];
			if(strResault=="SUCCESS"){
				blnRet = true;
			}else{
				blnRet = false;
			}
		}
	});
	return blnRet;
}
function validatePassword(){
	var blnRet = false;
	var beanIn = new makeBeanIn(
		   $('#txtUUID').val(),
           $('#txtYMM').val()  
	);
	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/Servlet1110150",
		data: {
			CMD    : "<%=Servlet1110150.CMD_VALIDATE%>",
		    BeanIn : JSON.stringify(beanIn)
		},
		complete :function(response){},
		success: function(response){
			var strResault = response[0];
			if(strResault=="SUCCESS"){
				blnRet = true;
			}else{
				blnRet = false;
			}
		}
	});
	return blnRet;
}
function savepassword(){
	 		if(funEditCheck()==false) return;
    		layer.confirm('是否确认修改？', function() {
	    		if(validatePassword()==false){
	    			layer.alert('原密码不正确！', 0, '友情提示');
	    			return
	    		}
	    		if(updatePassword()==true){
		  	       	layer.msg('恭喜：修改成功！', 1, 9);
		  	       	window.location.href='<%=basePath%>/bin/jsp/1110000/1110150.jsp';
				}else{
					layer.msg('对不起：修改失败！', 1, 8);
				}
			});
}
function getValue(){
	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/Servlet1110150",
		data: {
			CMD    : "CMD_INFO"
		},
		complete : function(response) {},
		success : function(response) {
			var strResault = response[0];
			var dataBean = response[1];
			if (strResault == "SUCCESS") {
				$('#txtUUID').val(dataBean.YHXX_UUID);
				$('#txtYHID').val(dataBean.YHXX_YHID);
				$('#txtYHMC').val(dataBean.YHXX_YHMC);
				$('#txtYHJS').val(dataBean.YHXX_JSMC);
				blnRet = true;
			} else if (strResault == "DATA_NULL") {
				layer.alert('取得个人信息出错！', 0, '友情提示');
				$('#txtUUID').val("");
				$('#txtYHID').val("");
				$('#txtYHMC').val("");
				$('#txtYHJS').val("");
				blnRet = false;
			}else{
				layer.alert('系统异常！', 0, '友情提示');
			}
		}
	});
}
function funEditCheck() {
		if ($('#txtYMM').val() == "") {
			layer.alert('请输入原密码！', 0, '友情提示', function() {
				$('#txtYMM').focus();
				layer.close(layer.index);
			});
			return false;
		}	
		if ($('#txtXMM').val() == "") {
			layer.alert('请输入新密码！', 0, '友情提示', function() {
				$('#txtXMM').focus();
				layer.close(layer.index);
			});
			return false;
		}	
		if ($('#txtXMM').val() == $('#txtYMM').val()) {
			layer.alert('新密码不能和原密码相同！', 0, '友情提示', function() {
				$('#txtXMM').focus();
				layer.close(layer.index);
			});
			return false;
		}	
		if ($('#txtQRMM').val() == "") {
			layer.alert('请输入确认密码！', 0, '友情提示', function() {
				$('#txtQRMM').focus();
				layer.close(layer.index);
			});
			return false;
		}
		if ($('#txtXMM').val()  != $('#txtQRMM').val()) {
			layer.alert('两次输入的密码不一致！', 0, '友情提示', function() {
				$('#txtXMM').focus();
				layer.close(layer.index);
			});
			return false;
		}	
	return true;
}
</script>
</head>
<body>
<div class="table_reset">
<table cellspacing="0" cellpadding="0">
				<tr>
					<td colspan="4" class="title_ border_left_none border_bottom_none">
    <span>修 改 密 码</span></td>
				</tr>
				<tr>
					<td class="border_top">用户ID：</td>
					<td class="border_top border_right" ><input type="hidden" id="txtUUID" /><input type="text" id="txtYHID"  onfocus=this.blur() readonly /></td>
				</tr>
				<tr>
					<td>用户名称：</td>
					<td class="border_right"><input type="text" id="txtYHMC" style="width:98%;" onfocus=this.blur() readonly/></td>
				</tr>
				<tr>
					<td>用户角色：</td>
					<td class="border_right" ><input type="text" id="txtYHJS"  onfocus=this.blur() readonly /></td>
				</tr>
			    <tr>
					<td>原密码：</td>
					<td class="border_right" ><input type="password" id="txtYMM" style="width:98%;"/></td>
				</tr>
				<tr>
					<td>新密码：</td>
					<td class="border_right"><input type="password" id="txtXMM" style="width:98%;"/></td>
				</tr>
				<tr>
					<td>确认密码：</td>
					<td class="border_right"><input type="password" id="txtQRMM" style="width:98%;"/></td>
				</tr>
				<tr>
				<td></td>
				<td class="border_right"><a class="botton" id="btnSave" name="btnSave"  onclick="savepassword()">提交</a>
                </td>
				</tr>
				</table>
                </div>
</body>
</html>