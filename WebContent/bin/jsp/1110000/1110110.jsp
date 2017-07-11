<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.xsjy.servlet.servlet1110000.Servlet1110110"%> 
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
	<script type="text/javascript" src="<%=basePath%>/bin/js/laydate/laydate.js"></script>
	<script type="text/javascript" src="<%=basePath%>/bin/js/jquery-1.9.1.js"></script>
	<script type="text/javascript" src="<%=basePath%>/bin/js/json2.js"></script>
	<script type="text/javascript" src="<%=basePath%>/bin/js/common.js"></script>
	<script type="text/javascript" src="<%=basePath%>/bin/js/school.js"></script>
	<script type="text/javascript" src="<%=basePath%>/bin/bootstrap/lib/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>/bin/js/layer/layer.min.js"></script>
	
<title>Insert title here</title>
<script type="text/javascript">
$(document).ready(function(){
	getInfo();
	$('#btnSave').on('click', function(){
			if(!yanzhengxm()){
				$('#txtYHXM').focus();
				return;
			}
			layer.confirm('是否确定保存？', function() {
				if(updateGRXXCode()==true){
		        	window.parent.setUserName();
		          // window.location.href="<%=basePath%>/bin/jsp/home/home.jsp";
		        }else{
		          
		        }
	        });
    });
});
function yanzhengxm(){
	if ($('#txtYHXM').val() == "") {
		$('#yzXM').show();
		return false;
	}else{
		$('#yzXM').hide();
		return true;
	}
}
function getInfo(){
	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/Servlet1110110",
		data: {
			CMD    : "CMD_DATA"
		},
		complete : function(response) {},
		success : function(response) {
			var strResault = response[0];
			var dataBean = response[1];
			if (strResault == "SUCCESS") {
				$('#txtYGID').val(dataBean.YGXX_YGID);
				$('#txtYHID').val(dataBean.YHID);
				$('#txtYHXM').val(dataBean.YHMC);
				if(dataBean.YGXX_XB!=""&&dataBean.YGXX_XB!=null){
					$("input[name='radioXB'][value="+dataBean.YGXX_XB+"]").attr("checked",true); 
				}
				$('#txtLXFS').val(dataBean.YGXX_LXFS);
				$('#txtCSRQ').val(dataBean.YGXX_CSRQ);
				$('#txtGRJJ').val(dataBean.YGXX_GRJJ);
				blnRet = true;
			} else if (strResault == "DATA_NULL") {
				layer.alert('取得个人信息出错！', 0, '友情提示');
				$('#txtYGID').val("");
				$('#txtYHID').val("");
				$('#txtYHXM').val("");
				$('#txtLXFS').val("");
				$('#txtCSRQ').val("");
				$('#txtGRJJ').val("");
				blnRet = false;
			}else{
				layer.alert('系统异常！', 0, '友情提示');
			}
		}
	});
}
function makeBeanInedit(strYGID,strYHID,strYHXM,strXB,strLXFS,strCSRQ,strGRJJ){
	this.YGXX_YGID = strYGID;
	this.YGXX_YGBM = strYHID;
	this.YGXX_YGXM = strYHXM;
	this.YGXX_XB = strXB;
	this.YGXX_LXFS = strLXFS;
	this.YGXX_CSRQ = strCSRQ;
	this.YGXX_GRJJ = strGRJJ;	
}
function updateGRXXCode(){
	var blnRet = false;
	var beanIn = new makeBeanInedit(
		   $('#txtYGID').val(),
		   $('#txtYHID').val(),
		   $('#txtYHXM').val(),
		   $('input:radio[name="radioXB"]:checked').val(),
           $('#txtLXFS').val(),
           $('#txtCSRQ').val(),
           $('#txtGRJJ').val()
	);
    $.ajax({
      async     : false,
      type      : "post",
      dataType  : "json",
      url: "<%=basePath%>/Servlet1110110",
      data: {
         CMD    : "<%=Servlet1110110.CMD_UPDATE%>",
         BeanIn : JSON.stringify(beanIn)
      },
      complete :function(response){},
      success: function(response){
          var strResault = response[0];
          if(strResault=="SUCCESS"){
             layer.msg('恭喜：更新个人信息成功！', 1, 9);
             blnRet = true;
          }else if(strResault=="FAILURE"){
             layer.msg('对不起：更新个人信息失败！', 1, 8);
             blnRet = false;
          }else{
          	 layer.msg('提示：更新个人信息出错！', 1, 0);
          	 blnRet = false;
          }
      }
   });
   return blnRet;
}
</script>
</head>

<body style="background: none;">
		<div id="main-content"> 			
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
									<label>姓名：</label>
										<input type="hidden" id="txtYGID" /><input type="hidden" id="txtYHID" /><input class="text-input small-input" type="text" id="txtYHXM" name="small-input"  onblur="yanzhengxm()" /><strong><span id="notLXFS" style="color:red;font-size:15px;"> *</span></strong><span id="yzXM"  style="display:none" class="input-notification error png_bg">姓名不能为空</span>
								</p>	
								<p>
									<label>性别：</label>
									<input type="radio" id="ss" name="radioXB"  value="1" checked/> 男
									<input type="radio" id="ss" name="radioXB"  value="2"/> 女
								</p>
								<p>
									<label>联系方式：</label>
									<input class="text-input small-input" type="text" id="txtLXFS" name="medium-input"   />
								</p>
									<p>
									<label>出生日期：</label>              
									<input class="text-input small-input" type="text" id="txtCSRQ" name="medium-input"  onclick="laydate()" readonly/>
								</p>				
								<p>
									<label>个人简介：</label>
									<textarea class="text-input textarea wysiwyg" id="txtGRJJ" name="textfield" cols="79" rows="10"  ></textarea>  
								</p>	
								<p>
									<input class="button" type="button"  id="btnSave" value="确定" />
								</p>
								
							</fieldset>
							<div class="clear"></div>
						</form>						
					</div>
				</div>				
			</div>
			
			<div class="clear"></div>
			<script src="resources/scripts/footer_admin.js" type="text/javascript"></script>			
		</div>
</body>
</html>