<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.xsjy.servlet.servlet1070000.Servlet1070110"%>
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
<link rel="stylesheet" href="<%=basePath%>/bin/css/Validform/style.css" type="text/css" media="screen" />
<title>教师基本信息</title>
<script type="text/javascript" src="<%=basePath%>/bin/js/laydate/laydate.js"></script>
<script type="text/javascript" src="<%=basePath%>/bin/js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="<%=basePath%>/bin/js/Validform/Validform_v5.3.2.js"></script>
<script type="text/javascript" src="<%=basePath%>/bin/js/json2.js"></script>
<script type="text/javascript" src="<%=basePath%>/bin/js/common.js"></script>
<script type="text/javascript" src="<%=basePath%>/bin/js/school.js"></script>
<script type="text/javascript" src="<%=basePath%>/bin/bootstrap/lib/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/bin/js/layer/layer.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/bin/js/plupload/plupload.full.min.js"></script>
<style type="text/css">
*{margin:0;padding:0;outline:none;}
ul li{list-style:none;}
.provinceSchool{display:none;position:absolute;width:480px;height:auto;border:1px solid #72B9D7;background:#f5f5f5;margin-top:10px;}
.provinceSchool .title{width:100%;height:30px;background:#72B9D7;cursor:move;}
.provinceSchool .title span{margin-left:10px;font-weight:600;color:#FFF;line-height:30px;}
.provinceSchool .proSelect{width:480px;text-align:center;padding:15px 0;}
.provinceSchool .proSelect select{width:136px;}
.provinceSchool .proSelect span{padding-left:10px;}
.provinceSchool .proSelect input{display:none;}
.provinceSchool .schoolList{width:440px;height:180px;padding:10px 0;overflow-y:auto;border:1px solid #ddd;margin:0 auto;overflow-x:hidden;margin-bottom:22px;}
.provinceSchool .schoolList ul{width:510px;clear:both;}
.provinceSchool .schoolList ul span.entertext{display:block;height:180px;font:normal 16px/180px 'microsoft yahei';color:#999;}
.provinceSchool .schoolList ul li{float:left;text-align:center;width:160px;margin:5px;height:25px;line-height:25px;cursor:pointer;background:#fafafa;border-radius:3px;}
.provinceSchool .schoolList ul li.DoubleWidthLi{overflow:hidden;}
.provinceSchool .schoolList ul li:hover{background:#72B9D7;color:#fff;}
.provinceSchool .button{width:100%;height:40px;margin-top:8px;}
.provinceSchool .button button{float:right;height:30px;margin-right:15px;padding:2px 15px;background:#72B9D7;border:none;color:#FFF;font-weight:600;cursor:pointer;border-radius:3px;}
.provinceSchool .button button:hover{background:#2e90bd;}

.teacheredit_input_src{position:relative;top:-1570px;right:-480px;width:200px;}
.teacheredit_input_src table tr td img{border:1px solid #D8D8D8;width:192px;height:164px;}
.teacher_edit_input{height:24px;width:128px;border:1px solid #D8D8D8;float:left; line-height:24px;color:gray;font-size:12px}
.teacher_edit_input2{height:24px;width:35px;border:1px solid #D8D8D8;margin-left:-5px;background:#f5f5f5;text-align:center;float:left;line-height:24px;font-size:12px}
.teacher_edit_input2:hover{background:#ddd;}
.teacher_edit_input2 span:active{position:relative;top:1px;left:1px;}



</style>
<script type="text/javascript">
var optionFlag = "";
var strJSXX_JSID="";
var strJSXX_JSXM="";
var strJSXX_SFZH="";
var strJSXX_XB="";
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
$(document).ready(function(){
	loadEditSelect($("#selectEditJSZG"),"TYPE_JSZG","教师资格");
	var inputObj = window.parent.obj;
	optionFlag=inputObj.FLAG;
	strJSXX_JSID=inputObj.JSXX_JSID;
	strJSXX_JSXM=inputObj.JSXX_JSXM;
	strJSXX_SFZH=inputObj.JSXX_SFZH;
	strJSXX_XB=inputObj.JSXX_XB;
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
	$('#txtJSID').val(strJSXX_JSID);
	$('#txtJSXM').val(strJSXX_JSXM);
	$('#txtSFZH').val(strJSXX_SFZH);
	if(strJSXX_XB!=""&&strJSXX_XB!=null){
		$("input[name='radioXB'][value="+strJSXX_XB+"]").attr("checked",true);
	}
	$('#txtLXFS').val(strJSXX_LXFS);
	$('#txtBYXX').val(strJSXX_BYXX);
	$('#txtBYNF').val(strJSXX_BYNF);
	if(strJSXX_XL!=""&&strJSXX_XL!=null){
		$("#selectEditXL  option[value='"+strJSXX_XL+"'] ").attr("selected",true);
	}
	$('#txtCSRQ').val(strJSXX_CSRQ);
	$('#txtZZ').val(strJSXX_ZZ);
	$('#txtGRJJ').val(strJSXX_GRJJ);
	$('#txtZY').val(strJSXX_ZY);
	$('#txtNJ').val(strJSXX_NJ);
	if(strJSXX_JNJY!=""&&strJSXX_JNJY!=null){
		$("#selectEditJXJY  option[value='"+strJSXX_JNJY+"'] ").attr("selected",true);
	}
	if(strJSXX_JSZG!=""&&strJSXX_JSZG!=null){
		$("#selectEditJSZG  option[value='"+strJSXX_JSZG+"'] ").attr("selected",true);
	}
	$('#txtSCLY').val(strJSXX_SCLY);
	if(optionFlag == "ADD"){
	    $("#main-content").height(1800);
	    $("#dlyx").show();
		$("input[id='upload0']").parents(".uploader").find(".filename").val("请上传身份证...");
		$("input[id='upload']").parents(".uploader").find(".filename").val("请上传资格证...");
	}else{
	    $("#main-content").height(1700);
	    $("#dlyx").hide();
		getJSTP(strJSXX_JSID);
	}
	getKmList();
	getQyList();
	setKmList(strJSXX_JSID);
	setQyList(strJSXX_JSID);

	$('#btnSave').on('click', function(){
		if(optionFlag == "ADD"){
			if($('#txtLXFS').val()!=""){
				if(checkSJHMExist()){
					layer.alert('该手机号码已经存在！', 0, '友情提示');
					return false;
				}
			}

		    if(funEditCheck()==false) return;
		    layer.confirm('是否确定添加？', function() {
		    	if(insertJSXXCode()==true){
		    		layer.msg('恭喜：添加成功！', 1, 9);
		    		iframeLayerClose();
		        }else{
		        	layer.msg('提示：添加失败！', 1, 0);
		        }
		    });
		}else{
		    if(funEditCheck()==false) return;
		    layer.confirm('是否确定修改？', function() {
		    	if(updateJSXXCode()==true){
		    		layer.msg('恭喜：修改成功！', 1, 9);
		    		iframeLayerClose();
		        }else{
		        	layer.msg('提示：修改失败！', 1, 0);
		        }
		    });
		}
    });
     //取消点击事件
    $('#btnCancel').on('click', function(){
    	iframeLayerClosenoRefresh();
    });
// 	$("input[type=file]").each(function() {
// 		if ($(this).val() == "") {
// 			$(this).parents(".uploader").find(".filename").val("没有选择文件...");
// 		}
// 	});
//     $("#open").on('click', function(){
//             $("#upload").trigger("click");
//     });




    //获取学校
    //学校名称 激活状态
	$("#txtBYXX").focus(function(){
	  var top = $(this).position().top+$(this).height()+2;
	  var left = $(this).position().left;
	  $("div[class='provinceSchool']").css({top:top,left:left});
	  $("div[class='provinceSchool']").show();
	});
	//初始化省下拉框
	var provinceArray = "";
	var provicneSelectStr = "";
	for(var i=0,len=province.length;i<len;i++){
	  provinceArray = province[i];
	  provicneSelectStr = provicneSelectStr + "<option value='"+provinceArray[0]+"'>"+provinceArray[1]+"</option>";
	}
	$("div[class='proSelect'] select").html(provicneSelectStr);
	//初始化学校列表
	var selectPro = $("div[class='proSelect'] select").val();
	var schoolUlStr = "";
	var schoolListStr = new String(proSchool[selectPro]);
	var schoolListArray = schoolListStr.split(",");
	var tempSchoolName = "";
	for(var i=0,len=schoolListArray.length;i<len;i++){
	  tempSchoolName = schoolListArray[i];
	  if(tempSchoolName.length>13){
		schoolUlStr = schoolUlStr + "<li class='DoubleWidthLi' title="+schoolListArray[i]+">"+schoolListArray[i]+"</li>";
	  }else {
		schoolUlStr = schoolUlStr + "<li>"+schoolListArray[i]+"</li>";
	  }
	}
	$("div[class='schoolList'] ul").html(schoolUlStr);
	//省切换事件
	$("div[class='proSelect'] select").change(function(){
	  if("99"!=$(this).val()){
		$("div[class='proSelect'] span").show();
		$("div[class='proSelect'] input").hide();
		schoolUlStr = "";
		schoolListStr = new String(proSchool[$(this).val()]);
		schoolListArray = schoolListStr.split(",");
		for(var i=0,len=schoolListArray.length;i<len;i++){
		  tempSchoolName = schoolListArray[i];
		  if(tempSchoolName.length>13){
			schoolUlStr = schoolUlStr + "<li class='DoubleWidthLi' title="+schoolListArray[i]+">"+schoolListArray[i]+"</li>";
		  }else {
			schoolUlStr = schoolUlStr + "<li>"+schoolListArray[i]+"</li>";
		  }
		}
		$("div[class='schoolList'] ul").html(schoolUlStr);
		$("#sure").hide();
	  }
	  else {
		$("div[class='schoolList'] ul").html("<span class='entertext'>请在输入框内手动输入学校！</span>");
		$("div[class='proSelect'] span").hide();
		$("div[class='proSelect'] input").show();
		$("#sure").show();
	  }
	});
	//学校列表mouseover事件
	$("div[class='schoolList'] ul li").live("mouseover",function(){
	  $(this).css("background-color","#72B9D7");
	});
	//学校列表mouseout事件
	$("div[class='schoolList'] ul li").live("mouseout",function(){
	  $(this).css("background-color","");
	});
	//学校列表点击事件
	$("div[class='schoolList'] ul li").live("click",function(){
	  $("#txtBYXX").val($(this).html());
	  $("div[class='provinceSchool']").hide();
	});
	//按钮点击事件
	$("div[class='button'] button").live("click",function(){
	  var flag = $(this).attr("flag");
	  if("0"==flag){
		$("div[class='provinceSchool']").hide();
	  }else if("1"==flag){
		var selectPro = $("div[class='proSelect'] select").val();
		if("99"==selectPro){
		  $("#txtBYXX").val($("div[class='proSelect'] input").val());
		}
		$("div[class='provinceSchool']").hide();
	  }
	});
});
function closeyxxx(){
	    var selectPro = $("div[class='proSelect'] select").val();
		if("99"==selectPro){
		  $("#txtBYXX").val($("div[class='proSelect'] input").val());
		}
		$("div[class='provinceSchool']").hide();
}
function sureyxxx(){
		$("#txtBYXX").val($("#otherschool").val());
	    $("div[class='provinceSchool']").hide();
}
function funEditCheck() {

		if ($('#txtJSXM').val() == "") {
			$('#yzXM').show();
			$('#txtJSXM').focus();
			return false;
		}else{
			$('#yzXM').hide();
		}

		if(optionFlag == "ADD"){
		    if ($('#txtDLYX').val() == "") {
				$('#yzYX').show();
				$('#txtDLYX').focus();
				return false;
			}else{
				$('#yzYX').hide();
			}
		}

		if ($('#txtLXFS').val() == "") {
			$('#yzLXFS').show();
			$('#txtLXFS').focus();
			return false;
		}else{
			$('#yzXM').hide();
		}
		var sfyz = IdCardValidate();
		if(sfyz==false){
			$('#txtSFZH').focus();
			return false;
		}
	return true;
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
					$("input[id='upload0']").parents(".uploader").find(".filename").val(dataBean.JSTP_SFZ);
					$("#sfzname").val(dataBean.JSTP_SFZ);
					$("#img0").attr("src",'<%=basePath%>/bin/upload/sfz/' + dataBean.JSTP_SFZ);
				}else{
					$("input[id='upload0']").parents(".uploader").find(".filename").val("请上传身份证...");
				}
				if(dataBean.JSTP_ZGZ!=""){
					$("input[id='upload']").parents(".uploader").find(".filename").val(dataBean.JSTP_ZGZ);
					$("#zgzname").val(dataBean.JSTP_ZGZ);
					$("#img").attr("src",'<%=basePath%>/bin/upload/zgz/' + dataBean.JSTP_ZGZ);
				}else{
					$("input[id='upload']").parents(".uploader").find(".filename").val("请上传资格证...");
				}
			} else if (strResault == "DATA_NULL") {
				$("input[id='upload0']").parents(".uploader").find(".filename").val("请上传身份证...");
				$("input[id='upload']").parents(".uploader").find(".filename").val("请上传资格证...");
			}else{

			}
		}
	});
}
function makeBeanIninsert(strJSXM,strDLYX,strSFZH,strXB,strLXFS,strBYXX,strBYNF,strXL,strCSRQ,strZZ,strGRJJ,strZY,strNJ,strJXJY,strJSZG,strSCLY,strSFZ,strZGZ){
    this.JSXX_JSXM = strJSXM;
    this.JSXX_JSBM = strDLYX;
    this.JSXX_SFZH = strSFZH;
    this.JSXX_XB = strXB;
    this.JSXX_LXFS = strLXFS;
    this.JSXX_BYXX = strBYXX;
    this.JSXX_BYNF = strBYNF;
    this.JSXX_XL = strXL;
    this.JSXX_CSRQ = strCSRQ;
    this.JSXX_ZZ = strZZ;
    this.JSXX_GRJJ = strGRJJ;
    this.JSXX_ZY = strZY;
    this.JSXX_NJ = strNJ;
    this.JSXX_JNJY = strJXJY;
    this.JSXX_JSZG = strJSZG;
    this.JSXX_SCLY = strSCLY;
    this.SFZ = strSFZ;
    this.ZGZ = strZGZ;
}
function insertJSXXCode(){
	var blnRet = false;
	var beanIn = new makeBeanIninsert(
		   $('#txtJSXM').val(),
		   $('#txtDLYX').val(),
           $('#txtSFZH').val(),
           $('input:radio[name="radioXB"]:checked').val(),
           $('#txtLXFS').val(),
           $('#txtBYXX').val(),
           $('#txtBYNF').val(),
           $('#selectEditXL').val(),
           $('#txtCSRQ').val(),
           $('#txtZZ').val(),
           $('#txtGRJJ').val(),
           $('#txtZY').val(),
           $('#txtNJ').val(),
           $('#selectEditJXJY').val(),
           $('#selectEditJSZG').val(),
           $('#txtSCLY').val(),
           $('#sfzname').val(),
           $('#zgzname').val()
	);
 	var strKCMCS = "";
	$("input[name='checkkm']:checked").each(function(){
   		 strKCMCS+=$(this).val()+',';
    });
	if(strKCMCS==""){
		strKCMCS = ",";
	}

	var strQYMCS = "";
	$("input[name='checkqy']:checked").each(function(){
   		 strQYMCS+=$(this).val()+',';
    });
	if(strQYMCS==""){
		strQYMCS = ",";
	}
    $.ajax({
      async     : false,
      type      : "post",
      dataType  : "json",
      url: "<%=basePath%>/Servlet1070110",
      data: {
         CMD    : "<%=Servlet1070110.CMD_INSERT%>",
         BeanIn : JSON.stringify(beanIn),
         KCMCS: strKCMCS,
         QYMCS: strQYMCS
      },
      complete :function(response){},
      success: function(response){
          var strResault = response[0];
          if(strResault=="SUCCESS"){
             blnRet = true;
          }else if(strResault=="FAILURE"){
             blnRet = false;
          }
      }
   });
   return blnRet;
}
function makeBeanInedit(strJSID,strJSXM,strSFZH,strXB,strLXFS,strBYXX,strBYNF,strXL,strCSRQ,strZZ,strGRJJ,strZY,strNJ,strJXJY,strJSZG,strSCLY,strSFZ,strZGZ){
    this.JSXX_JSID = strJSID;
    this.JSXX_JSXM = strJSXM;
    this.JSXX_SFZH = strSFZH;
    this.JSXX_XB = strXB;
    this.JSXX_LXFS = strLXFS;
    this.JSXX_BYXX = strBYXX;
    this.JSXX_BYNF = strBYNF;
    this.JSXX_XL = strXL;
    this.JSXX_CSRQ = strCSRQ;
    this.JSXX_ZZ = strZZ;
    this.JSXX_GRJJ = strGRJJ;
    this.JSXX_ZY = strZY;
    this.JSXX_NJ = strNJ;
    this.JSXX_JNJY = strJXJY;
    this.JSXX_JSZG = strJSZG;
    this.JSXX_SCLY = strSCLY;
    this.SFZ = strSFZ;
    this.ZGZ = strZGZ;
}
function updateJSXXCode(){
	var blnRet = false;
	var beanIn = new makeBeanInedit(
		   $('#txtJSID').val(),
		   $('#txtJSXM').val(),
           $('#txtSFZH').val(),
           $('input:radio[name="radioXB"]:checked').val(),
           $('#txtLXFS').val(),
           $('#txtBYXX').val(),
           $('#txtBYNF').val(),
           $('#selectEditXL').val(),
           $('#txtCSRQ').val(),
           $('#txtZZ').val(),
           $('#txtGRJJ').val(),
           $('#txtZY').val(),
           $('#txtNJ').val(),
           $('#selectEditJXJY').val(),
           $('#selectEditJSZG').val(),
           $('#txtSCLY').val(),
           $('#sfzname').val(),
           $('#zgzname').val()
	);
 	var strKCMCS = "";
	$("input[name='checkkm']:checked").each(function(){
   		 strKCMCS+=$(this).val()+',';
    });
    if(strKCMCS==""){
		strKCMCS = ",";
	}
	var strQYMCS = "";
	$("input[name='checkqy']:checked").each(function(){
   		 strQYMCS+=$(this).val()+',';
    });
    if(strQYMCS==""){
		strQYMCS = ",";
	}
    $.ajax({
      async     : false,
      type      : "post",
      dataType  : "json",
      url: "<%=basePath%>/Servlet1070110",
      data: {
         CMD    : "<%=Servlet1070110.CMD_UPDATE%>",
         BeanIn : JSON.stringify(beanIn),
         KCMCS: strKCMCS,
         QYMCS: strQYMCS
      },
      complete :function(response){},
      success: function(response){
          var strResault = response[0];
          if(strResault=="SUCCESS"){
             blnRet = true;
          }else if(strResault=="ERROR"){
             blnRet = false;
          }
      }
   });
   return blnRet;
}
function getKmList(){
		$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/Servlet1070110",
		data: {
			CMD    : "<%=Servlet1070110.CMD_ALLKMLIST%>"
		},
		complete :function(response){},
		success: function(response){
			var strResault = response[0];
			var list = response[1];
			var listHtml = "";
			$.each(list, function(k, v) {
				listHtml+="<input type='checkbox' style='margin:0 10px 10px 0' name='checkkm' id='"+v.KCXX_KCID+"' value='"+v.KCXX_KCID+"'/>"+v.KCXX_KCMC+"";
				if((k!=0)&&((k+1)%7==0)){
				listHtml+="<br>";
				}
			});
			$("#km").html(listHtml);
			//设置默认选中
			if(strResault=="SUCCESS"){
			}else if(strResault=="LIST_NULL"){
				layer.msg('提示：获取课程信息出错！', 1, 0);
			}else{
				layer.msg('提示：系统异常！', 1, 0);
			}
		}
	});
}
function getQyList(){
		$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/Servlet1070110",
		data: {
			CMD    : "<%=Servlet1070110.CMD_ALLQYLIST%>"
		},
		complete :function(response){},
		success: function(response){
			var strResault = response[0];
			var list = response[1];
			var listHtml = "";
			$.each(list, function(k, v) {
				listHtml+="<input type='checkbox' style='margin:0 10px 10px 0' name='checkqy' id='"+v.XZQY_QYID+"' value='"+v.XZQY_QYID+"'/>"+v.XZQY_QYMC+"";
				if((k!=0)&&((k+1)%7==0)){
				listHtml+="<br>";
				}
			});
			$("#qy").html(listHtml);
			//设置默认选中
			if(strResault=="SUCCESS"){
			}else if(strResault=="LIST_NULL"){
				layer.msg('提示：获取课程信息出错！', 1, 0);
			}else{
				layer.msg('提示：系统异常！', 1, 0);
			}
		}
	});
}
function setKmList(strJSID){
		$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/Servlet1070110",
		data: {
			CMD    : "<%=Servlet1070110.CMD_OWNKMLIST%>",
		    JSID     :strJSID
		},
		complete :function(response){},
		success: function(response){
			var strResault = response[0];
			var list = response[1];
			$.each(list, function(k, v) {
				$("input[name='checkkm']").each(function () {
                    if ($(this).val() == v.JSKM_KCMC) {
                        $(this).attr("checked",true);
                    }
                });
			});
			if(strResault=="SUCCESS"){
			}else if(strResault=="LIST_NULL"){
				layer.msg('提示：获取擅长课程出错！', 1, 0);
			}else{
				layer.msg('提示：系统异常！', 1, 0);
			}
		}
	});
}
function setQyList(strJSID){
		$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/Servlet1070110",
		data: {
			CMD    : "<%=Servlet1070110.CMD_OWNQYLIST%>",
		    JSID     :strJSID
		},
		complete :function(response){},
		success: function(response){
			var strResault = response[0];
			var list = response[1];
			$.each(list, function(k, v) {
				$("input[name='checkqy']").each(function () {
                    if ($(this).val() == v.JSQY_QYID) {
                        $(this).attr("checked",true);
                    }
                });
			});
			if(strResault=="SUCCESS"){
			}else if(strResault=="LIST_NULL"){
				layer.msg('提示：获取授课区域出错！', 1, 0);
			}else{
				layer.msg('提示：系统异常！', 1, 0);
			}
		}
	});
}
function yanzhengxm(){
	if ($('#txtJSXM').val() == "") {
		$('#yzXM').show();
	}else{
		$('#yzXM').hide();
	}
}
function yanzhengyx(){
	if ($('#txtDLYX').val() == "") {
		$('#yzYX').show();
	}else{
		$('#yzYX').hide();
	}
}
function yanzhengfs(){
	if ($('#txtLXFS').val() == "") {
		$('#yzLXFS').show();
	}else{
		$('#yzLXFS').hide();
	}
}
//验证身份证
var Wi = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1 ];    // 加权因子
var ValideCode = [ 1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2 ];            // 身份证验证位值.10代表X
function IdCardValidate() {
	var idCard = $('#txtSFZH').val();
	if(idCard!=""){

	    idCard = trim(idCard.replace(/ /g, ""));               //去掉字符串头尾空格
	    if (idCard.length == 15) {
	        return isValidityBrithBy15IdCard(idCard);       //进行15位身份证的验证
	    } else if (idCard.length == 18) {
	        var a_idCard = idCard.split("");                // 得到身份证数组
	        if(isValidityBrithBy18IdCard(idCard)&&isTrueValidateCodeBy18IdCard(a_idCard)){   //进行18位身份证的基本验证和第18位的验证
	           	$("#yzSFZH").hide();
	            return true;
	        }else {
	        	$("#yzSFZH").show();
	            return false;
	        }
	    } else {
	    	$("#yzSFZH").show();
	        return false;
	    }
	}else{
		$("#yzSFZH").hide();
		return true;
	}
}
/**
 * 判断身份证号码为18位时最后的验证位是否正确
 * @param a_idCard 身份证号码数组
 * @return
 */
function isTrueValidateCodeBy18IdCard(a_idCard) {
    var sum = 0;                             // 声明加权求和变量
    if (a_idCard[17].toLowerCase() == 'x') {
        a_idCard[17] = 10;                    // 将最后位为x的验证码替换为10方便后续操作
    }
    for ( var i = 0; i < 17; i++) {
        sum += Wi[i] * a_idCard[i];            // 加权求和
    }
    valCodePosition = sum % 11;                // 得到验证码所位置
    if (a_idCard[17] == ValideCode[valCodePosition]) {
    	$("#yzSFZH").hide();
        return true;
    } else {
   	    $("#yzSFZH").show();
        return false;
    }
}
/**
  * 验证18位数身份证号码中的生日是否是有效生日
  * @param idCard 18位书身份证字符串
  * @return
  */
function isValidityBrithBy18IdCard(idCard18){
    var year =  idCard18.substring(6,10);
    var month = idCard18.substring(10,12);
    var day = idCard18.substring(12,14);
    var temp_date = new Date(year,parseFloat(month)-1,parseFloat(day));
    // 这里用getFullYear()获取年份，避免千年虫问题
    if(temp_date.getFullYear()!=parseFloat(year)
          ||temp_date.getMonth()!=parseFloat(month)-1
          ||temp_date.getDate()!=parseFloat(day)){
            $("#yzSFZH").show();
            return false;

    }else{
    	$("#yzSFZH").hide();
        return true;
    }
}
  /**
   * 验证15位数身份证号码中的生日是否是有效生日
   * @param idCard15 15位书身份证字符串
   * @return
   */
  function isValidityBrithBy15IdCard(idCard15){
      var year =  idCard15.substring(6,8);
      var month = idCard15.substring(8,10);
      var day = idCard15.substring(10,12);
      var temp_date = new Date(year,parseFloat(month)-1,parseFloat(day));
      // 对于老身份证中的你年龄则不需考虑千年虫问题而使用getYear()方法
      	if(temp_date.getYear()!=parseFloat(year)||temp_date.getMonth()!=parseFloat(month)-1||temp_date.getDate()!=parseFloat(day)){
                $("#yzSFZH").show();
                return false;
        }else{
       		$("#yzSFZH").hide();
            return true;
        }
  }
//去掉字符串头尾空格
function trim(str) {
    return str.replace(/(^\s*)|(\s*$)/g, "");
}
function checkSJHMExist(){
	var blnRet = false;
	var strSJHM = $('#txtLXFS').val();
	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/Servlet1070110",
		data: {
			CMD    : "<%=Servlet1070110.CMD_CHK_EXIST%>",
			SJHM : strSJHM
		},
		complete : function(response) {
		},
		success : function(response) {
			var strResault = response[0];
			if (strResault == "USER_EXIST") {
				blnRet = true;
			} else{
				blnRet = false;
			}
		}
	});
	return blnRet;
}
</script>
</head>

<body style="background: none;">
<form id="teacherForm"  method="POST" enctype="multipart/form-data">
		<div id="main-content" style="height:1700px;">
			<div class="content-box chuan_mar2">
				<div class="content-box-header">
					<h3>完善个人信息</h3>

					<div class="clear"></div>
				</div>
				<div class="content-box-content">
					<div class="tab-content default-tab" id="tab1">

							<fieldset>
								<p>
									<label>您的真实姓名：</label>
										<input type="hidden" id="txtJSID" />
										<input class="text-input small-input inputxt" type="text" id="txtJSXM" name="small-input"  onblur="yanzhengxm()"/><strong><span id="notLXFS" style="color:red;font-size:15px;"> *</span></strong><span id="yzXM"  style="display:none" class="input-notification error png_bg">姓名不能为空</span>
										<br /><small>请输入您的真实姓名，以便审核。</small>
								</p>
                                <p id="dlyx">
                                  <label>登陆邮箱：</label>
                                    <input class="text-input small-input inputxt" type="text" id="txtDLYX" name="small-input"  onblur="yanzhengyx()"/><strong><span id="notDLYX" style="color:red;font-size:15px;"> *</span></strong><span id="yzYX"  style="display:none" class="input-notification error png_bg">登陆邮箱不能为空</span>
                                    <br /><small>请输入登陆邮箱，用于登陆系统。</small>
                                </p>
								<p>
									<label>请输入您的身份证号码：</label>
										<input class="text-input small-input" type="text" id="txtSFZH" name="small-input"  onblur="IdCardValidate()"/><span id="yzSFZH"  style="display:none" class="input-notification error png_bg">身份证不合法</span>
										<br /><small>上传身份证信息，通过1-3天人工审核期</small>
								</p>

								<p>
									<label>性别：</label>
									<input type="radio" id="ss" name="radioXB"  checked  value="1"/> 男
									<input type="radio" id="ss" name="radioXB"  value="2"/> 女
								</p>

								<p>
									<label>联系方式：</label>
									<input class="text-input small-input inputxt" type="text" id="txtLXFS" name="medium-input"  onblur="yanzhengfs()"/><strong><span id="notLXFS" style="color:red;font-size:15px;"> *</span></strong><span id="yzLXFS"  style="display:none" class="input-notification error ">联系方式不能为空</span>
								</p>
									<p>
									<label>出生日期：</label>
									<input class="text-input small-input" type="text" id="txtCSRQ" name="medium-input"  onclick="laydate()" readonly/>
								</p>

								<p>
									<label>您的住址：</label>
									<input class="text-input medium-input datepicker" type="text" id="txtZZ" name="medium-input" />
								</p>
								<p>
									<label for='txtBYXX'>毕业院校：</label>
									<input class="text-input medium-input datepicker"  type="text"  id="txtBYXX"  name="medium-input"  />
								</p>
								<p>
									<label>专业：</label>
									<input class="text-input small-input" type="text" id="txtZY" name="medium-input"  />
								</p>
								<p>
									<label>年级：</label>
									<input class="text-input small-input" type="text" id="txtNJ" name="medium-input"  />
									<br /><small>如2008级，直接输入2008。</small>
								</p>
								<P>
									<label>毕业时间：</label>
									<input class="text-input small-input" type="text" id="txtBYNF" name="medium-input"  onclick="laydate()" readonly/>
								</p>
									<div id="proSchool" class="provinceSchool"  style="z-index:99999;">
								   	 	<div class="title"><span style="float:left;width:440px;">请选择学校</span><a href="#" onclick="closeyxxx()"><span  style="font-size:18px;">X</span></a></div>
									  		<div class="proSelect">
										        <select></select>
										        <span>如没找到选择项，请选择其他手动填写</span>
										        <input id="otherschool" type="text" style="line-height:24px;height:25px;border:1px solid #ddd"/><a onclick="sureyxxx()" id="sure" style="display:none;line-height:48px;background:#72B9D7;color:#fff;padding:2px 8px;" href="#">确定</a>
									        </div>
									        <div class="schoolList">
									        	<ul></ul>
									        </div>

							        </div>
								<p>
									<label>学历：</label>
									<select name="dropdown" class="small-input"  id="selectEditXL">
									    <option value="其他">其他</option>
										<option value="专科">专科</option>
										<option value="本科">本科</option>
										<option value="硕士">硕士</option>
										<option value="博士">博士</option>
									</select>
								</p>
								<p>
									<label>教学经验：</label>
									<select name="dropdown" class="small-input"  id="selectEditJXJY">
									    <option value="0">无</option>
										<option value="1">1年</option>
										<option value="2">2年</option>
										<option value="3">3年</option>
										<option value="4">4年</option>
										<option value="5">5年</option>
										<option value="6">6年</option>
										<option value="7">7年</option>
										<option value="8">8年</option>
										<option value="9">9年</option>
										<option value="10">10年</option>
										<option value="11">大于10年</option>
									</select>
								</p>
								<p>
									<label>教师资格：</label>
									<select name="dropdown" class="small-input"  id="selectEditJSZG">
									</select>
								</p>
								<p>
									<label>擅长授课科目：</label>

								</p>
								<div id="km">
								</div>
								<br>
								<p>
									<label>授课区域：</label>

								</p>
								<div id="qy">
								</div>
								<br>
								<p>
									<label>擅长领域：</label>
									<textarea class="text-input textarea wysiwyg" id="txtSCLY" name="textfield" cols="79" rows="6"></textarea>
								    <br /><small>请控制在200字内。</small>
								</p>
								<p>
									<label>个人简介：</label>
									<textarea class="text-input textarea wysiwyg" id="txtGRJJ" name="textfield" cols="79" rows="10"></textarea>
								    <br /><small>请控制在500字内。</small>
								</p>
								<p>
									<input class="button" type="button"  id="btnSave" value="确 定" />&nbsp;&nbsp;<input class="button"  type="submit" value="取 消" id="btnCancel" name="btnCancel" />
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
                                            <div id="container">
											<input type="hidden" id="sfzname" />
											<input type="text" class="filename teacher_edit_input"  onfocus="this.blur()" readonly />
											<div id="selectsfz"  type="button"   class="teacher_edit_input2"><span>浏览...</span></div>
											<div id="uploadsfz"   type="button"   class="teacher_edit_input2"><span>上传</span></div>
											<input type="file" id="upload0" style="display:none;">
                                            </div>
										</td>
									</tr>
									<tr>
										<td align="center">
											<font id="font0"  style="color:gray">*图片格式为.jpg,.gif,.png</font>
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
                                            <div id="container1">
											<input type="hidden" id="zgzname" />
											<input type="text" class="filename teacher_edit_input" onfocus="this.blur()"  readonly/>
											<div id="selectzgz"  type="button"  id="open" class="teacher_edit_input2"><span>浏览...</span></div>
					 						<div id="uploadzgz"   type="button"   class="teacher_edit_input2"><span>上传</span></div>
					 						<input type="file" id="upload" style="display:none;">
                                            </div>
										</td>
									</tr>
									<tr>
										<td align="center">
											<font id="font"  style="color:gray">*图片格式为.jpg,.gif,.png</font>
										</td>
									</tr>
								</table>
							</div>


							</div>
				</div>
			</div>

			<div class="clear"></div>
			<script type="text/javascript" src="<%=basePath%>/bin/js/resources/footer_admin.js" type="text/javascript"></script>
		</div>
</form>
</body>
<script type="text/javascript">
	//上传身份证
 	var uploader = new plupload.Uploader({
	    runtimes : 'html5,flash,silverlight,html4',
	    browse_button : 'selectsfz',
	    container: 'container',
	    multi_selection: false,
	    max_file_size : '100kb',
	    url : '<%=basePath%>/ImgUploadServlet?name=sfz',
	    resize : {width : 320, height : 240, quality : 90},
	    flash_swf_url: '<%=basePath%>/bin/js/plupload/Moxie.swf',
	    silverlight_xap_url: '<%=basePath%>/bin/js/plupload/Moxie.xap',
	    filters : [
	       {title : "Image files", extensions : "jpg,png,gif"}
	    ]
	 });
	 uploader.init();

	 uploader.bind('Error', function(up, err) {
	     if(err.code=="-600"){
	    	 layer.alert('照片文件大小应小于100K！', 0, '友情提示');
	     }
	  });
	//会在文件上传过程中不断触发，可以用此事件来显示上传进度
	uploader.bind('UploadProgress', function(up, files) {
		 //$("#img0").attr("src",'<%=basePath%>/bin/img/jindu.jpg');

	});
	document.getElementById('uploadsfz').onclick = function(){
        uploader.start(); //调用实例对象的start()方法开始上传文件，当然你也可以在其他地方调用该方法
    };
	 uploader.bind('FilesAdded',function(uploader,files){
		 for(var i = 0, len = files.length; i<len; i++){
			 var file_name = files[i].name; //文件名
			 //构造html来更新UI
			  $("input[id='upload0']").parents(".uploader").find(".filename").val(file_name);

		}
	 });
	 uploader.bind('FileUploaded', function(up, file, info) {
		 if(file.status=="5"){
			   //上传成功，返回文件名称.
			   var fileSrc = info.response;
			   //$("input[id='upload0']").parents(".uploader").find(".filename").val(fileSrc);
			   $("#img0").attr("src",'<%=basePath%>/bin/upload/sfz/' + fileSrc);
			   $("#sfzname").val(fileSrc);
			   $("input[id='upload0']").parents(".uploader").find(".filename").val(fileSrc);
			   layer.msg('恭喜：上传身份证成功！', 1, 9);
		}
	});

	//上传资格证
	 var uploader1 = new plupload.Uploader({
	    runtimes : 'html5,flash,silverlight,html4',
	    browse_button : 'selectzgz',
	    container: 'container1',
	    multi_selection: false,
	    max_file_size : '100kb',
	    url : '<%=basePath%>/ImgUploadServlet?name=zgz',
	    resize : {width : 320, height : 240, quality : 90},
	    flash_swf_url: '<%=basePath%>/bin/js/plupload/Moxie.swf',
	    silverlight_xap_url: '<%=basePath%>/bin/js/plupload/Moxie.xap',
	    filters : [
	       {title : "Image files", extensions : "jpg,png,gif"}
	    ]
	 });
	 uploader1.init();
	 uploader1.bind('Error', function(up, err) {
	     if(err.code=="-600"){
	    	 layer.alert('照片文件大小应小于100K！', 0, '友情提示');
	     }
	  });
	 document.getElementById('uploadzgz').onclick = function(){
        uploader1.start(); //调用实例对象的start()方法开始上传文件，当然你也可以在其他地方调用该方法
    };
    uploader1.bind('FilesAdded',function(uploader,files){
		 for(var i = 0, len = files.length; i<len; i++){
			 var file_name = files[i].name; //文件名
			 //构造html来更新UI
			  $("input[id='upload']").parents(".uploader").find(".filename").val(file_name);

		}
	 });
	uploader1.bind('FileUploaded', function(up, file, info) {
		 if(file.status=="5"){
			   //上传成功，返回文件名称.
			   var fileSrc = info.response;
			   //$("input[id='upload0']").parents(".uploader").find(".filename").val(fileSrc);
			   $("#img").attr("src",'<%=basePath%>/bin/upload/zgz/' + fileSrc);
			   $("#zgzname").val(fileSrc);
			   $("input[id='upload']").parents(".uploader").find(".filename").val(fileSrc);
			   layer.msg('恭喜：上传资格证成功！', 1, 9);
		}
	});
</script>
</html>