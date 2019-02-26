<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.xsjy.servlet.servlet1070000.Servlet1070110"%>
<%@ page import="com.xsjy.servlet.servlet1070000.Servlet1070130"%>
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
<link rel="stylesheet" href="<%=basePath%>/bin/css/resources/reset.css" type="text/css" media="screen" />
<link rel="stylesheet" href="<%=basePath%>/bin/css/resources/style.css" type="text/css" media="screen" />
<link rel="stylesheet" href="<%=basePath%>/bin/css/resources/invalid.css" type="text/css" media="screen" />
<title>教师基本信息</title>
<script type="text/javascript" src="<%=basePath%>/bin/js/laydate/laydate.js"></script>
<script type="text/javascript" src="<%=basePath%>/bin/js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="<%=basePath%>/bin/js/json2.js"></script>
<script type="text/javascript" src="<%=basePath%>/bin/js/common.js"></script>
<script type="text/javascript" src="<%=basePath%>/bin/js/school.js"></script>
<script type="text/javascript" src="<%=basePath%>/bin/bootstrap/lib/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/bin/js/layer/layer.min.js"></script>
<style type="text/css">
.teacheredit_input_src{position:relative;top:-1470px;right:-480px;width:200px;}
.teacheredit_input_src table tr td img{border:1px solid #D8D8D8;width:192px;height:164px;}
.teacher_edit_input{height:24px;width:128px;border:1px solid #D8D8D8;float:left; line-height:24px;color:gray;font-size:12px}
.teacher_edit_input2{height:24px;width:35px;border:1px solid #D8D8D8;margin-left:-5px;background:#f5f5f5;text-align:center;float:left;line-height:24px;font-size:12px}
.teacher_edit_input2:hover{background:#ddd;}
.teacher_edit_input2 span:active{position:relative;top:1px;left:1px;}
</style>
<script type="text/javascript">
var strJSXX_JSID="";
var strJSXX_JSXM="";
var strJSXX_SFZH="";
var strJSXX_XBMC="";
var strJSXX_CSRQ="";
var strJSXX_LXFS="";
var strJSXX_ZZ="";
var strJSXX_XL="";
var strJSXX_BYXX="";
var strJSXX_BYNF="";
var strJSXX_GRJJ="";
var strJSXX_ZY="";
var strJSXX_NJ="";
var strJSXX_JNJY="";
var strJSXX_JSZG="";
var strJSXX_SCLY="";
var strJSXX_XQAH="";
var strJSXX_SFRZ="";
$(document).ready(function(){
	var inputObj = window.parent.obj;
	strJSXX_JSID=inputObj.JSXX_JSID;
	strJSXX_JSBM=inputObj.JSXX_JSBM;
	strJSXX_JSXM=inputObj.JSXX_JSXM;
	strJSXX_SFZH=inputObj.JSXX_SFZH;
	strJSXX_XBMC=inputObj.JSXX_XBMC;
	strJSXX_CSRQ=inputObj.JSXX_CSRQ;
	strJSXX_LXFS=inputObj.JSXX_LXFS;
	strJSXX_ZZ=inputObj.JSXX_ZZ;
	strJSXX_XL=inputObj.JSXX_XL;
	strJSXX_BYXX=inputObj.JSXX_BYXX;
	strJSXX_BYNF=inputObj.JSXX_BYNF;
	strJSXX_GRJJ=inputObj.JSXX_GRJJ;
	strJSXX_ZY=inputObj.JSXX_ZY;
	strJSXX_NJ=inputObj.JSXX_NJ;
	strJSXX_JNJY=inputObj.JSXX_JNJY;
	strJSXX_JSZG=inputObj.JSXX_JSZG;
	strJSXX_SCLY=inputObj.JSXX_SCLY;
	strJSXX_XQAH=inputObj.JSXX_XQAH;
	strJSXX_SFRZ=inputObj.JSXX_SFRZ;
	strN ="年";
	strJ ="级";
	$('#txtJSXM').val(strJSXX_JSXM);
	$('#txtSFZH').val(strJSXX_SFZH);
	$('#txtXB').val(strJSXX_XBMC);
	$('#txtLXFS').val(strJSXX_LXFS);
	$('#txtBYXX').val(strJSXX_BYXX);
	$('#txtBYNF').val(strJSXX_BYNF);
	$('#txtXL').val(strJSXX_XL);
	$('#txtCSRQ').val(strJSXX_CSRQ);
	$('#txtZZ').val(strJSXX_ZZ);
	$('#txtGRJJ').val(strJSXX_GRJJ);
	$('#txtZY').val(strJSXX_ZY);
	$('#txtNJ').val(strJSXX_NJ+strJ);
	$('#txtJNJY').val(strJSXX_JNJY+strN);
    $('#txtJSZG').val(strJSXX_JSZG);
	$('#txtSCLY').val(strJSXX_SCLY);
	$('#txtXQAH').val(strJSXX_XQAH);
	if(strJSXX_SFRZ=="已认证"){
		$("#btnSave").hide();
	}
	getJSTP(strJSXX_JSID);
	var reg = setKmList(strJSXX_JSID);
	$('#btnSave').on('click', function(){
		layer.confirm('是否通过认证？', function() {
			if(!reg){
				layer.alert('该教师没有授课科目，无法通过认证！', 0, '友情提示');
				return;
			}
			if(Teacheridentification(strJSXX_JSID,strJSXX_JSBM,strJSXX_JSXM)){
				iframeLayerClose();
			}
		});
    });
      //取消点击事件
    $('#btnCancel').on('click', function(){
    	iframeLayerClosenoRefresh();
    });
});
//定义varName为全局使用
function toGlobal (varName) {
	 window.execScript(varName);
}
function getJSTP(strJSID){
	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/Servlet1070110",
		data: {
			CMD    : "CMD_JSTP",
			JSID     : strJSID
		},
		complete : function(response) {},
		success : function(response) {
			var strResault = response[0];
			var dataBean = response[1];
			if (strResault == "SUCCESS") {
				if(dataBean.JSTP_SFZ!=""){
					//$("input[id='upload0']").parents(".uploader").find(".filename").val(dataBean.JSTP_SFZ);
					$("#img0").attr("src",'<%=basePath%>/bin/upload/sfz/' + dataBean.JSTP_SFZ);
				}else{
					//$("input[id='upload0']").parents(".uploader").find(".filename").val("请上传身份证...");
				}
				if(dataBean.JSTP_ZGZ!=""){
					//$("input[id='upload']").parents(".uploader").find(".filename").val(dataBean.JSTP_ZGZ);
					$("#img").attr("src",'<%=basePath%>/bin/upload/zgz/' + dataBean.JSTP_ZGZ);
				}else{
					//$("input[id='upload']").parents(".uploader").find(".filename").val("请上传资格证...");
				}
			} else if (strResault == "DATA_NULL") {
				//$("input[id='upload0']").parents(".uploader").find(".filename").val("请上传身份证...");
				//$("input[id='upload']").parents(".uploader").find(".filename").val("请上传资格证...");
			}else{

			}
		}
	});
}
function setKmList(strJSID){
		var strREG=true;
		$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/Servlet1070110",
		data: {
			CMD    : "<%=Servlet1070110.CMD_OWNKMLISTINFO%>",
		    JSID     :strJSID
		},
		complete :function(response){},
		success: function(response){
			var strResault = response[0];
			var list = response[1];
			var strKM = response[2];
			var sp = $("#sp");
			$.each(list, function(k, v) {
				sp.append("【"+v.JSKM_KCMC+"】 ");
				if((k!=0)&&(k%7==0)){
				sp.ppend("<br>");
				}
			});
			if(strResault=="SUCCESS"){
			}else if(strResault=="LIST_NULL"){
				layer.msg('提示：获取擅长课程出错！', 1, 0);
			}else{
				layer.msg('提示：系统异常！', 1, 0);
			}
			if(strKM=="NOKM"){
				strREG=false;
			}else{
			}
		}
	});
	return strREG;
}
function makeBeanInedit(strJSID,strJSBM,strJSXM){
	this.JSXX_JSID = strJSID;
	this.JSXX_JSBM = strJSBM;
	this.JSXX_JSXM = strJSXM;
}
function Teacheridentification(strJSID,strJSBM,strJSXM){
	var res =false;
	var beanIn = new makeBeanInedit(
		   strJSID,
		   strJSBM,
		   strJSXM
	);
    $.ajax({
      async     : false,
      type      : "post",
      dataType  : "json",
      url: "<%=basePath%>/Servlet1070130",
      data: {
         CMD    : "<%=Servlet1070130.CMD_IDENTIFICATE%>",
         BeanIn : JSON.stringify(beanIn)
      },
      complete :function(response){},
      success: function(response){
          var strResault = response[0];
          if(strResault=="SUCCESS"){
        	  layer.msg('恭喜：操作成功！', 1, 9);
             res =true;
          }else if(strResault=="ERROR"){
        	  layer.msg('恭喜：操作失败！', 1, 8);
             res =false;
          }
      }
   });
   return res;
}
</script>
</head>

<body style="background: none;">
		<div id="main-content" style="height:1660px;">
			<div class="content-box chuan_mar2">
				<div class="content-box-header">
					<h3>个人信息</h3>

					<div class="clear"></div>
				</div>
				<div class="content-box-content">
					<div class="tab-content default-tab" id="tab1">
						<form action="#" method="post">
							<fieldset>
								<p>
									<label>教师姓名：</label>
										<input class="text-input small-input" type="text" id="txtJSXM" name="small-input"  readonly/>
								</p>
								<p>
									<label>身份证号码：</label>
										<input class="text-input small-input" type="text" id="txtSFZH" name="small-input"  readonly />
								</p>

								<p>
									<label>性别：</label>
									<input class="text-input small-input" type="text" id="txtXB" name="small-input"  readonly />
								</p>

								<p>
									<label>联系方式：</label>
									<input class="text-input small-input" type="text" id="txtLXFS" name="medium-input"  readonly />
								</p>
									<p>
									<label>出生日期：</label>
									<input class="text-input small-input" type="text" id="txtCSRQ" name="medium-input"   readonly/>
								</p>

								<p>
									<label>您的住址：</label>
									<input class="text-input medium-input datepicker" type="text" id="txtZZ" name="medium-input"  readonly/>
								</p>
								<p>
									<label for='txtBYXX'>毕业院校：</label>
									<input class="text-input medium-input datepicker"  type="text"  id="txtBYXX"  name="medium-input"   readonly/>
								</p>
								<p>
									<label>专业：</label>
									<input class="text-input small-input" type="text" id="txtZY" name="medium-input"  readonly/>
								</p>
								<p>
									<label>年级：</label>
									<input class="text-input small-input" type="text" id="txtNJ" name="medium-input"  readonly/>
								</p>
								<P>
									<label>毕业时间：</label>
									<input class="text-input small-input" type="text" id="txtBYNF" name="medium-input"   readonly/>
								</p>
								<p>
									<label>学历：</label>
									<input class="text-input small-input" type="text" id="txtXL" name="medium-input"   readonly/>
								</p>
								<p>
									<label>教学经验：</label>
									<input class="text-input small-input" type="text" id="txtJNJY" name="medium-input"   readonly/>
								</p>
								<p>
									<label>教师资格：</label>
									<input class="text-input small-input" type="text" id="txtJSZG" name="medium-input"   readonly/>
								</p>
								<p>
									<label>擅长授课科目：</label>
									<span style="padding-left:10px" id="sp"></span>
								</p>

								<p>
									<label>擅长领域：</label>
									<textarea class="text-input textarea wysiwyg" id="txtSCLY" name="textfield" cols="79" rows="6"  readonly></textarea>
								</p>
								<p>
									<label>兴趣爱好：</label>
									<textarea class="text-input textarea wysiwyg" id="txtXQAH" name="textfield" cols="79" rows="3"  readonly></textarea>
								</p>
								<p>
									<label>个人简介：</label>
									<textarea class="text-input textarea wysiwyg" id="txtGRJJ" name="textfield" cols="79" rows="10"  readonly></textarea>
								</p>
								<p>
									<input class="button" type="button"  id="btnSave" value="认证" />&nbsp;&nbsp;<input class="button" type="button"  id="btnCancel" value="取消" />
								</p>

							</fieldset>

							<div class="clear"></div>
							<!--  添加上传zhaopia -->
							<div class="teacheredit_input_src">
								<table style="z:index:-9999;border-bottom:2px solid #fff;">
									<tr>
										<td width="192" align="center">
										<img id="img0"   src="" />
										</td>
									</tr>
									<tr>
										<td align="center"  class="uploader">
											<input type="hidden" id="sfzname" />
										</td>
									</tr>

									<tr style="height:350px"><tr/>
									<tr>
										<td width="192" align="center">
										<img id="img"  src="" />
										</td>
									</tr>
									<tr>
										<td align="center"  class="uploader">
											<input type="hidden" id="zgzname" />
										</td>
									</tr>

								</table>
							</div>
						</form>
					</div>
				</div>
			</div>

			<div class="clear"></div>
			<script type="text/javascript" src="<%=basePath%>/bin/js/resources/footer_admin.js" type="text/javascript"></script>
		</div>
</body>
</html>