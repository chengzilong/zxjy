<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.xsjy.servlet.servlet2010000.Servlet2010120"%> 
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
<title>回答信息</title>
<script type="text/javascript" src="<%=basePath%>/bin/js/laydate/laydate.js"></script>
<script type="text/javascript" src="<%=basePath%>/bin/js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="<%=basePath%>/bin/js/common.js"></script>
<script type="text/javascript" src="<%=basePath%>/bin/js/json2.js"></script>
<script type="text/javascript" src="<%=basePath%>/bin/js/layer/layer.min.js"></script>
<script type="text/javascript">
			var obj;//参数对象
			//初始化表格
			$(document).ready(function(){
				/* 初始化传递参数Start */
				//obj = window.dialogArguments;//获取Dialog窗口传递参数
			    obj = window.parent.dataBean;//参数对象
				getHDJF(obj.WTID,obj.HDXX_HDID);
				//$('#txtSYJF').html(obj.HDXX_SYJF);
				//$('#txtXSJF').val(obj.HDXX_XSJF);
				//alert(obj.HDXX_XSJF);
				$('#txtFS').val(obj.HDXX_HDJF);
				//$('#txtWTID').val(obj.WTID);
				/* 初始化传递参数End */		
		         $('#btnSave').on('click', function(){
		       	   if(parseFloat($('#txtFS').val())>parseFloat($('#txtSYJF').html())){
			    			layer.msg('对不起：分值过大！', 1, 8);
			    			return;
			        }
			    	if(updateHDXXCode(obj.HDXX_HDID)==true){
			  				//layer.close(layer.index);
			  				iframeLayerClosenoRefresh();
					}
			    });
			});
			function getHDJF(strWTID,strHDID){
			$.ajax({
				async     : false,
				type      : "post",
				dataType  : "json",
				url: "<%=basePath%>/Servlet2010120",
				data: {
					CMD    : "CMD_SYJF",
					WTID   :  strWTID,
					HDID   :  strHDID
				},
				complete : function(response) {},
				success : function(response) {
					var strResault = response[0];
					var dataBean = response[1];
					if (strResault == "SUCCESS") {
						$('#txtSYJF').html(obj.HDXX_XSJF-dataBean.HDZF);				
						blnRet = true;
					} else if (strResault == "DATA_NULL") {
						$('#txtEditGRJF').html("");
		
						blnRet = false;
					}else{
						//alert("系统异常！");
					}
				}
			});
		}
		function makeBeanInedit(strHDID,strHDDF){
		    this.HDXX_HDID = strHDID;
		    this.HDDF = strHDDF;
        }
		function updateHDXXCode(strHDID){
		var blnRet = false;
		var beanIn = new makeBeanInedit(
			   strHDID,
	           $('#txtFS').val()       
		);
	    $.ajax({
	      async     : false,
	      type      : "post",
	      dataType  : "json",
	      url: "<%=basePath%>/Servlet2010120",
	      data: {
	         CMD    : "<%=Servlet2010120.CMD_DAFEN%>",
	         BeanIn : JSON.stringify(beanIn)
	      },
	      complete :function(response){},
	      success: function(response){
	          var strResault = response[0];
	          if(strResault=="SUCCESS"){
	        	
	             blnRet = true;
	          }else if(strResault=="FAILURE"){
	             layer.msg('对不起：打分失败！', 1, 8);
	             blnRet = false;
	          }
	      }
	   });
	   return blnRet;
	}
		
		</script>
	</head>
  	<body>
  	<table>
  		<tr>
  			<td>剩余积分</td>
  			<td id="txtSYJF"></td>
  		</tr>
  		<tr>
  			<td><input type="hidden"  id="txtXSJF" /><input style="width:60px" type="text"  id="txtFS"  onfocus="this.select();"/></td>
  			<td><input type="button"  id="btnSave" value="打分"></td>
  		</tr>
  		
    </table>
	</body>
</html>