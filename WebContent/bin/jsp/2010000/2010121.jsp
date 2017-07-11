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
<!--表格样式Start  -->
<link rel="stylesheet" href="<%=basePath%>/bin/css/mmgrid/main.css" type="text/css">
<link rel="stylesheet" href="<%=basePath%>/bin/css/mmgrid/mmGrid.css" type="text/css">
<link rel="stylesheet" href="<%=basePath%>/bin/css/mmgrid/mmPaginator.css" type="text/css">
<link rel="stylesheet" href="<%=basePath%>/bin/css/mmgrid/normalize.css" type="text/css">
<link rel="stylesheet" href="<%=basePath%>/bin/css/mmgrid/tablesize.css" type="text/css">
<!--表格样式End  -->
<title>回答信息</title>
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
			var mmgR;
			var obj;//参数对象
			//初始化表格
			$(document).ready(function(){
				/* 初始化传递参数Start */
			    obj = window.parent.dataBean;//参数对象
				/* 初始化传递参数End */
				/* 初始化表单数据 */
				$('#txtWTLY').val(obj.KCMC);
		        $('#txtTWSJ').val(obj.WTXX_TWSJ);
		        $('#txtYXSJ').val(obj.YXSJ);
		        $('#txtTWNR').val(obj.WTXX_TWNR);    
		        $('#txtXSJF').val(obj.WTXX_TWJF);   
		        getHDJF(obj.WTXX_WTID);
		        /* 初始化表格数据 */
				var colsRenyuan = [
					{ title:'回答人', name:'HDR' ,width:80, sortable:true, align:'center',lockDisplay: true },
					{ title:'回答内容', name:'HDXX_HDNR' ,width:350, sortable:true, align:'center',lockDisplay: true  },
					{ title:'回答时间', name:'HDXX_HDSJ' ,width:60, sortable:true, align:'center',lockDisplay: true  },
					{ title:'回答得分', name:'HDXX_HDJF' ,width:20, sortable:true, align:'center',lockDisplay: true  },
					{ title:'操作', name:'' ,width:20, sortable:true, align:'center',lockDisplay: true, renderer: function(val,item){
						if(obj.WTXX_TWZT=="已结束"){
							$('#btnEnd').attr("disabled", "disabled");
							//return '<img id="img-info" class="img-dafen" title="打分" src="<%=basePath%>/bin/img/good.gif"></img>';
						}else{
						 	return '<img id="img-info" class="img-dafen" title="打分" src="<%=basePath%>/bin/img/good.gif"></img>';
							$('#btnEnd').removeAttr("disabled");
						}
         		 		
      				}}
				];
				
				var intheightR = document.documentElement.clientHeight - $('#editRegion').height() - $('#editRegion1').height() - $('#selectRegion').height()  - 100;
				if(intheightR < 100){
					intheightR = 80;
				}
				
				mmgR = $('.mmgR').mmGrid({
					height: intheightR
					,cols: colsRenyuan
					,url: '<%=basePath%>/Servlet2010120'
					,method: 'post'
					,params:{CMD : "<%=Servlet2010120.CMD_SELECTHD%>"}
					,remoteSort:true
					,sortName: ''
					,sortStatus: 'ASC'
					,root: 'items'
					,multiSelect: false
					,indexCol: true
					,indexColWidth: 35
					,fullWidthRows: true
					,autoLoad: false
					,nowrap:true
					,plugins: [
						$('#pgMenu').mmPaginator({
							limit:20
						})
					]
				});
				mmgR.on('cellSelected', function(e, item, rowIndex, colIndex){
	 		    var arrList = mmgR.row(rowIndex);
					//配置节点
			        if($(e.target).is('.img-dafen')){
			            e.stopPropagation();  //阻止事件冒泡		
						item.WTID = obj.WTXX_WTID;
						item.HDXX_XSJF = obj.WTXX_TWJF;
						item.HDXX_SYJF = $('#txtSYJF').val();
						dataBean = item;
			      		iframeLayerOpenown('<%=basePath%>/bin/jsp/2010000/2010122.jsp',obj.WTXX_WTID);			  
			        }
			    });
				loadGridByBeanR(obj.WTXX_WTID);
				mmgR.on('rowSelected', function(e, item, rowIndex, colIndex){//选择数据行
				   var arrList = mmgR.row(rowIndex);  
				   $('#txtEditHDNR').val(arrList.HDXX_HDNR);
			       
				}).on('loadSuccess',function(e, data){
				       //设置按钮状态
				}).on('click', 'tr', function(e){ //点击行;
					   if(mmgR.rowsLength()<=0) return; //无数据,不进行操作
				       var rowIndex = e.target.parentNode.rowIndex;
				       if(typeof(rowIndex) == "undefined"){
				    	   rowIndex = e.target.parentNode.parentNode.rowIndex;
				       }
				       var arrList = mmgR.row(rowIndex);
				       var arrList1 = mmgR.selectedRows();
				       if(arrList1.length>0) {
					      $('#txtEditHDNR').val(arrList.HDXX_HDNR);
				       }else
				       {
				    	   $("#txtEditHDNR").val("");
				       }
				    });
				   $('#btnEnd').on('click', function(){
				   	 	if($('#txtSYJF').val()!="0"){
				   	 		var grjf;
				   	 		layer.confirm('还有剩余悬赏积分未分配，结束问题系统会扣除10%的分数，是否继续？', function() {
					   	 		if(parseFloat($('#txtSYJF').val())>1&&parseFloat($('#txtSYJF').val())<=10){
					   	 			grjf = parseFloat($('#txtSYJF').val())-1;
					   	 		}else if(parseFloat($('#txtSYJF').val())>10){
					   	 			grjf = parseFloat($('#txtSYJF').val()) - parseFloat($('#txtSYJF').val())/10;
					   	 		}else if(parseFloat($('#txtSYJF').val())<=1){
					   	 			grjf = 0;
					   	 		}
					   	 		returnGRJF(grjf);
					   	 		setGRJF();
					   	 		if(setWTXXend(obj.WTXX_WTID)==true){
									iframeLayerClose();
								}
				   	 		});
				   	 	}else{
				   	 		
				   	 		layer.confirm('是否结束问题？', function() {
				   	 			setGRJF();
				   	 			if(setWTXXend(obj.WTXX_WTID)==true){
									iframeLayerClose();
								}
				   	 		});
				   	 	}
				   	 	
				
						
						
						
				   	 	
				    });
			});
			//自定义弹出层方法
			function iframeLayerOpenown(url,wtid) {
				$.layer({
				      type: 2,
				      title: false,
				      maxmin: false,
				      shadeClose: false, //开启点击遮罩关闭层
				      area : ['300px' , '100px'],
				      offset : ['30%', '60%'],
				      iframe: {src: url},
				      end : function(){//弹出层彻底关闭执行的回调函数
					       loadGridByBeanR(wtid);
			    		   getHDJF(wtid);
				      }
				  });
			}
			function setGRJF() {
				var blnRet = true;
				var arrList = mmgR.rows();    //所有行
				var strHDR = "";
				var strHDJF = "";
				if(arrList.length>0) {
					for(var i = 0; i < arrList.length; i++) {
						if(arrList[i].HDXX_HDJF != ""&&arrList[i].HDXX_HDJF !="0"){
							strHDR += arrList[i].HDXX_HDR + ",";
							strHDJF += arrList[i].HDXX_HDJF + ",";
						}
					}
				}
				if(strHDR != ""){
						$.ajax({
						async     : false,
						type      : "post",
						dataType  : "json",
						url: "<%=basePath%>/Servlet2010120",
						data: {
							CMD    : "<%=Servlet2010120.CMD_SETHDJF%>",
							HDRS : strHDR ,
							HDJFS  : strHDJF					
						},
						complete : function(response) {
						},
						success : function(response) {
							var CMD = response[0];
							if (CMD == "CMD_OK") {				
								blnRet = true;
							} else if (CMD == "CMD_EXCEPTION") {
								layer.msg('对不起：回答人得分出错！', 1, 8);
								blnRet = false;
							}
						}
					});		
				}
			
				return blnRet;	
			}
			function loadGridByBeanR(strWTID){
				//重新查询数据
				mmgR.load({
			    	WTID  :  strWTID
				});
			}
			function getHDJF(strWTID){
			$.ajax({
				async     : false,
				type      : "post",
				dataType  : "json",
				url: "<%=basePath%>/Servlet2010120",
				data: {
					CMD    : "CMD_HDJF",
					WTID   :  strWTID
				},
				complete : function(response) {},
				success : function(response) {
					var strResault = response[0];
					var dataBean = response[1];
					if (strResault == "SUCCESS") {
						$('#txtSYJF').val($('#txtXSJF').val()-dataBean.HDZF);				
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
		function makeBeanInend(strWTID){
			this.WTXX_WTID = strWTID;
		}
		function setWTXXend(strWTID){
			var blnRet = false;
			var beanIn = new makeBeanInend(
				   strWTID
			);
		    $.ajax({
		      async     : false,
		      type      : "post",
		      dataType  : "json",
		      url: "<%=basePath%>/Servlet2010120",
		      data: {
		         CMD    : "<%=Servlet2010120.CMD_WTXXEND%>",
		         BeanIn : JSON.stringify(beanIn)   
		      },
		      complete :function(response){},
		      success: function(response){
		          var strResault = response[0];
		          if(strResault=="SUCCESS"){
		             blnRet = true;
		          }else if(strResault=="FAILURE"){
		             layer.msg('对不起：操作失败！', 1, 8);
		             blnRet = false;
		          }
		      }
		   });
		   return blnRet;
		}
		function returnGRJF(strGRJF){
		    $.ajax({
		      async     : false,
		      type      : "post",
		      dataType  : "json",
		      url: "<%=basePath%>/Servlet2010120",
		      data: {
		         CMD    : "<%=Servlet2010120.CMD_RETURNJF%>",
		         GRJF    : strGRJF
		      },
		      complete :function(response){},
		      success: function(response){
		          var strResault = response[0];
		          if(strResault=="SUCCESS"){
		        	 layer.msg('提示：剩余积分已返回给提问人！', 1, 0);
		             blnRet = true;	             
		          }else if(strResault=="FAILURE"){
		             layer.msg('对不起：更新个人积分出错！', 1, 8);
		             blnRet = false;
		          }
		      }
		   });
		   return blnRet;
		}
		</script>
	</head>
  	<body>
  		<fieldset id = "editRegion">
			<legend id="cxq">问题信息</legend>
			<table id="detailCanvas" class="eTable6">
				<tr>	
					<th style="width:100px">问题领域</th>	
					<td><input style="width:120px" id="txtWTLY" name="问题领域"  onfocus=this.blur() readonly /></td>
					<th style="width:100px">悬赏积分</th>	
					<td><input type="hidden"  id="txtSYJF"><input style="width:120px" id="txtXSJF" name="悬赏积分"  onfocus=this.blur() readonly /></td>
				</tr>
				<tr>	
				    <th style="width:100px">提问时间</th>	
					<td><input style="width:120px" id="txtTWSJ" name="发布日期" onfocus=this.blur() readonly /></td>
					<th style="width:100px">有效时间</th>	
					<td><input style="width:120px" id="txtYXSJ" name="有效日期"  onfocus=this.blur() readonly /></td>
				</tr>
				<tr>
					<th style="width:100px">提问内容</th>	
					<td height="100" valign="middle" bgcolor="white" class="wo7" colspan="3"  >
							<textarea  id="txtTWNR"  style="width: 99%;height: 100%" onfocus=this.blur() readonly ></textarea>
					</td>
				</tr>
				<tr>
					<td></td><td><input type="button"  id="btnEnd"  value="结束问题"></td>
				</tr>
			</table>
		</fieldset>
		<fieldset id="selectRegion">
			<legend id="cxq">回答列表</legend>
		</fieldset>
		<div id="gridCanvas">
			<table id="mmgR" class="mmgR"></table>
			<div id="pgMenu" style="text-align: right;"></div>
		</div>
		<fieldset id = "editRegion1">
		<legend>答案详情</legend>
		<table id="detailCanvas" class="eTable6">
			<tr>
				<td style="width:10px">
				</td>
				<td colspan="3">
				<textarea id="txtEditHDNR" cols="120" rows="5"    readonly></textarea>  
				</td>
			</tr>
		</table>
	</fieldset>
	</body>
</html>