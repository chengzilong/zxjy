<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.xsjy.servlet.servlet1040000.Servlet1040160"%> 
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
<link rel="stylesheet" href="<%=basePath%>/bin/css/mmgrid/main.css"	type="text/css">
<link rel="stylesheet" href="<%=basePath%>/bin/css/mmgrid/mmGrid.css" type="text/css">
<link rel="stylesheet" href="<%=basePath%>/bin/css/mmgrid/mmPaginator.css" type="text/css">
<link rel="stylesheet" href="<%=basePath%>/bin/css/mmgrid/normalize.css" type="text/css">
<link rel="stylesheet" href="<%=basePath%>/bin/css/mmgrid/tablesize.css" type="text/css">
<!--表格样式End  -->
<title>日程设定</title>
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
	var obj = new Object();
	//初始化表格
	$(document).ready(function(){
	   var cols = [
	       { title:'班次名称', name:'BCMC' ,width:100, sortable:true, align:'center',lockDisplay: false },
	       { title:'课程名称', name:'KCMC' ,width:100, sortable:true, align:'center',lockDisplay: false  },
	       { title:'班次时段', name:'BCSD' ,width:200,sortable:true, align:'center',lockDisplay: false },
	       { title:'上课年月', name:'BCRC_NY' ,width:100, sortable:true, align:'center',lockDisplay: true  },
	       { title:'01日', name:'BCRC_DAY01' ,width:30, sortable:true, align:'center',lockDisplay: true  },
	       { title:'02日', name:'BCRC_DAY02' ,width:30, sortable:true, align:'center',lockDisplay: true  },
	       { title:'03日', name:'BCRC_DAY03' ,width:30, sortable:true, align:'center',lockDisplay: true  },
	       { title:'04日', name:'BCRC_DAY04' ,width:30, sortable:true, align:'center',lockDisplay: true  },
	       { title:'05日', name:'BCRC_DAY05' ,width:30, sortable:true, align:'center',lockDisplay: true  },
	       { title:'06日', name:'BCRC_DAY06' ,width:30, sortable:true, align:'center',lockDisplay: true  },
	       { title:'07日', name:'BCRC_DAY07' ,width:30, sortable:true, align:'center',lockDisplay: true  },
	       { title:'08日', name:'BCRC_DAY08' ,width:30, sortable:true, align:'center',lockDisplay: true  },
	       { title:'09日', name:'BCRC_DAY09' ,width:30, sortable:true, align:'center',lockDisplay: true  },
	       { title:'10日', name:'BCRC_DAY10' ,width:30, sortable:true, align:'center',lockDisplay: true  },
	       { title:'11日', name:'BCRC_DAY11' ,width:30, sortable:true, align:'center',lockDisplay: true  },
	       { title:'12日', name:'BCRC_DAY12' ,width:30, sortable:true, align:'center',lockDisplay: true  },
	       { title:'13日', name:'BCRC_DAY13' ,width:30, sortable:true, align:'center',lockDisplay: true  },
	       { title:'14日', name:'BCRC_DAY14' ,width:30, sortable:true, align:'center',lockDisplay: true  },
	       { title:'15日', name:'BCRC_DAY15' ,width:30, sortable:true, align:'center',lockDisplay: true  },
	       { title:'16日', name:'BCRC_DAY16' ,width:30, sortable:true, align:'center',lockDisplay: true  },
	       { title:'17日', name:'BCRC_DAY17' ,width:30, sortable:true, align:'center',lockDisplay: true  },
	       { title:'18日', name:'BCRC_DAY18' ,width:30, sortable:true, align:'center',lockDisplay: true  },
	       { title:'19日', name:'BCRC_DAY19' ,width:30, sortable:true, align:'center',lockDisplay: true  },
	       { title:'20日', name:'BCRC_DAY20' ,width:30, sortable:true, align:'center',lockDisplay: true  },
	       { title:'21日', name:'BCRC_DAY21' ,width:30, sortable:true, align:'center',lockDisplay: true  },
	       { title:'22日', name:'BCRC_DAY22' ,width:30, sortable:true, align:'center',lockDisplay: true  },
	       { title:'23日', name:'BCRC_DAY23' ,width:30, sortable:true, align:'center',lockDisplay: true  },
	       { title:'24日', name:'BCRC_DAY24' ,width:30, sortable:true, align:'center',lockDisplay: true  },
	       { title:'25日', name:'BCRC_DAY25' ,width:30, sortable:true, align:'center',lockDisplay: true  },
	       { title:'26日', name:'BCRC_DAY26' ,width:30, sortable:true, align:'center',lockDisplay: true  },
	       { title:'27日', name:'BCRC_DAY27' ,width:30, sortable:true, align:'center',lockDisplay: true  },
	       { title:'28日', name:'BCRC_DAY28' ,width:30, sortable:true, align:'center',lockDisplay: true  },
	       { title:'29日', name:'BCRC_DAY29' ,width:30, sortable:true, align:'center',lockDisplay: true  },
	       { title:'30日', name:'BCRC_DAY30' ,width:30, sortable:true, align:'center',lockDisplay: true  },
	       { title:'31日', name:'BCRC_DAY31' ,width:30, sortable:true, align:'center',lockDisplay: true  },
	       { title:'操作', name:'' ,width:70, sortable:true, align:'center',lockDisplay: true, renderer: function(val){
				return '<button class="btn btn-info">整月设定</button>';
			}},
	   ];
	
	   intheight = document.documentElement.clientHeight -$('#editRegion').height()-$('#selectRegion').height()-80; 
	   if(intheight<100){
		   intheight = 100;
	   }
	
	   mmg = $('.mmg').mmGrid({
	         height: intheight
	        ,cols: cols
	        ,url: '<%=basePath%>/Servlet1040160'
	        ,method: 'post'
	        ,params:{CMD : "<%=Servlet1040160.CMD_SELECT%>"}
	        ,remoteSort:true
	        ,sortName: 'BCRC_BCRCID'
	        ,sortStatus: 'ASC'
	        ,root: 'items'
	        ,multiSelect: false
	        ,checkCol: true
	        ,indexCol: true
	        ,indexColWidth: 35
	        ,fullWidthRows: true
	        ,autoLoad: false
	        ,plugins: [
	            $('#pg').mmPaginator({
	              limit:30
	            })
	        ]
	      });
	   
	   mmg.on('cellSelected', function(e, item, rowIndex, colIndex){
			//查看
			if($(e.target).is('.btn-info')){
				e.stopPropagation();  //阻止事件冒泡
				obj.BCRC_BCRCID=item.BCRC_BCRCID;
				obj.BCRC_BCID=$("#txtSelBCMC").val();
				obj.BCRC_NY=item.BCRC_NY;			
				obj.BCRC_BCMC=item.BCMC;		
				obj.BCRC_BCSD=item.BCSD;
				obj.BCRC_SDID=item.SDID;
				obj.BCRC_SDMC=item.SDMC;
				iframeLayerOpenown('<%=basePath%>/bin/jsp/1040000/1040161.jsp');
			}
	   });
	   
	   mmg.on('click', ':checkbox', function(){ //选择checkbox
		   if(this.checked == true){
			   var arrList = mmg.selectedRows();
			   if(arrList.length>0){
			   	   $('#txtEditBCRCID').val(arrList[0].BCRC_BCRCID);
				   $('#txtEditBCMC').val(arrList[0].BCMC);
			       $('#txtEditKCMC').val(arrList[0].KCMC);
			       $('#txtEditBCSD').val(arrList[0].BCSD);
			       $('#txtEditSKNY').val(arrList[0].BCRC_NY);   
			       $('#txtEditSK').val(arrList[0].SDID);   
			       setButtonStatus("2");   
			   }
		   }else{
		       $('#txtEditBCRCID').val("");
			   $('#txtEditBCMC').val("");
		       $('#txtEditKCMC').val("");
		       $('#txtEditBCSD').val("");
		       $('#txtEditSKNY').val("");
		       $('#txtEditSK').val("");   
		       setButtonStatus("1");//未选中行
		   }
	   }).on('loadSuccess',function(e, data){
	       //设置按钮状态
		   setButtonStatus("1");
	   }).on('click', 'tr', function(e){ //点击行;
		   if(mmg.rowsLength()<=0) return; //无数据,不进行操作
	       var rowIndex = e.target.parentNode.rowIndex;
	       if(typeof(rowIndex) == "undefined"){
	    	   rowIndex = e.target.parentNode.parentNode.rowIndex;
	       }
	       if(typeof(rowIndex) == "undefined") return;
	       var arrList = mmg.row(rowIndex);  
	       var arrList1 = mmg.selectedRows();
	       if(arrList1.length>0) {
		       $('#txtEditBCRCID').val(arrList.BCRC_BCRCID);
	  	       $('#txtEditBCMC').val(arrList.BCMC);
		       $('#txtEditKCMC').val(arrList.KCMC);
		       $('#txtEditBCSD').val(arrList.BCSD);
		       $('#txtEditSKNY').val(arrList.BCRC_NY);
		       $('#txtEditSK').val(arrList.SDID);   
		       setButtonStatus("2"); 
	       }else{
	       	   $('#txtEditBCRCID').val("");
			   $('#txtEditBCMC').val("");
		       $('#txtEditKCMC').val("");
		       $('#txtEditBCSD').val("");
		       $('#txtEditSKNY').val("");
		       $('#txtEditSK').val("");   
		       setButtonStatus("1");//未选中行
	       }
	    });
	
	   
	   $('#btnSearch').on('click', function(){
	       if($("#txtSelBCMC").val()=="000"){
	    	   layer.alert('请选择班次！', 0, '友情提示');
			   return;
		   }
		   loadGridByBean();
	   });
	   $('#btnUpd').on('click', function(){
		   var arrList = mmg.selectedRows();
		   if(arrList.length<=0){
			   layer.alert('请选择要变更的数据行！', 0, '友情提示');
	 	       return;
		   }
		   var strNY=$("#txtEditSKNY").val().replace("-","");
		   var strQS=$("#txtEditBCSD").val().substr(0,10).replace(/[&\|\\\*^%$#@\-]/g,"");
		   var strJS=$("#txtEditBCSD").val().substr(11,10).replace(/[&\|\\\*^%$#@\-]/g,"");
		  if(strQS.substr(0,6) == strNY&&strJS.substr(0,6) == strNY){
		  	loadEditSelect($("#selectEditGZRQ"),"TYPE_QJRQ-" + strQS+"-"+strJS ,"工作日");
		  }else if(strQS.substr(0,6) == strNY){
		  	loadEditSelect($("#selectEditGZRQ"),"TYPE_QSRQ-" + strQS ,"工作日");
		  }else if(strJS.substr(0,6) == strNY){
		  	loadEditSelect($("#selectEditGZRQ"),"TYPE_JSRQ-" + strJS ,"工作日");
		  }else{
		  	loadEditSelect($("#selectEditGZRQ"),"TYPE_GZRQ-" + strNY ,"工作日");
		  }
		   
	       setButtonStatus("3"); 
	   });	   
	   $('#btnSave').on('click', function(){
		   if($('#selectEditGZRQ').val()=="000"){
			   layer.alert('请选择工作日！', 0, '友情提示');
			   return;
		   }
		   if($('#selectEditSFYK').val()=="000"){
			   layer.alert('请选择是否有课！', 0, '友情提示');
			   return;
		   }
		   
		   //保存排班信息
		   if(saveRCSD()){
		   	loadGridByBean();
		   	setButtonStatus("1");//未选中行
		   	mmg.deselect('all');
		   }

	   });
	   
	   $('#btnCancel').on('click', function(){
		   setButtonStatus("1");
		   mmg.deselect('all');
	   });
	   loadSearchSelect($("#txtSelBCMC"),"TYPE_BCMC","班次名称");
	   setButtonStatus("1");
	});
	//自定义弹出层方法
	function iframeLayerOpenown(url) {
		$.layer({
	        type: 2,
	        title: false,
	        maxmin: false,
	        shadeClose: false, //开启点击遮罩关闭层
	        area : ['800px' , '600px'],
	        offset : ['20px', ''],
	        iframe: {src: url},
	        end : function(){//弹出层彻底关闭执行的回调函数
	        	loadGridByBean();
	        }
	    });
	}
	function makeBeanIn(strBCID){	
	    this.BCRC_BCID = strBCID;//班次ID
	}
	function makeBeanInadd(strBCRCID,strBCID,strSKNY,strGZRQ,strSKSD,strSFYK){	
		this.BCRC_BCRCID = strBCRCID;//班次日程ID
	    this.BCRC_BCID = strBCID;//班次ID
	    this.BCRC_NY = strSKNY;//上课年月
	    this.GZR = strGZRQ;//工作日
	    this.SDJC = strSKSD;//时段简称
	    this.SFYK = strSFYK;//是否有课
	}
	function loadGridByBean(){
		var beanIn = new makeBeanIn(
			$('#txtSelBCMC').val()//班次名称		
		);
		//重新查询数据
		mmg.load({
	    	beanLoad  :  JSON.stringify(beanIn)
		});
	}
	
		
	//设置按钮状态
	function setButtonStatus(strFlag) {
		if (strFlag == "1") {//初始状态
			$('#btnUpd').attr("disabled", "disabled");
			$('#btnSave').attr("disabled", "disabled");
			$('#btnCancel').attr("disabled", "disabled");
			
			$('#txtEditBCMC').attr("disabled", "disabled");//项目类型
			$('#txtEditKCMC').attr("disabled", "disabled");//项目名称
			$('#txtEditBCSD').attr("disabled", "disabled");//岗位名称
			$('#txtEditSKNY').attr("disabled", "disabled");//工作年月
			$('#selectEditGZRQ').attr("disabled", "disabled");//工作日
			$('#selectEditSFYK').attr("disabled", "disabled");//是否有课
			
			$('#txtEditBCMC').val("");//项目类型
			$('#txtEditKCMC').val("");//项目名称
			$('#txtEditBCSD').val("");//岗位名称
			$('#txtEditSKNY').val("");//工作年月
			$('#selectEditGZRQ').val("000");//工作日
			$('#selectEditSFYK').val("000");//是否有课
			
			
		} else if (strFlag == "2") {//选择行后
			$('#btnUpd').removeAttr("disabled");
			$('#btnSave').attr("disabled", "disabled");
			$('#btnCancel').attr("disabled", "disabled");
		} else if (strFlag == "3") {//变更按下后
			$('#btnUpd').attr("disabled", "disabled");
			$('#btnSave').removeAttr("disabled");
			$('#btnCancel').removeAttr("disabled");
			$('#selectEditGZRQ').removeAttr("disabled");//工作日
			$('#selectEditSFYK').removeAttr("disabled");//是否有课
		} else if (strFlag == "4") {//取消/保存按下后
			$('#btnUpd').attr("disabled", "disabled");
			$('#btnSave').removeAttr("disabled");
			$('#btnCancel').removeAttr("disabled");
			$('#selectEditGZRQ').attr("disabled", "disabled");//工作日
			$('#selectEditSFYK').attr("disabled", "disabled");//是否有课
		}
	}
	
	function saveRCSD(){
		var blnRet = false;
		var beanIn = new makeBeanInadd(
				$('#txtEditBCRCID').val(),//班次日程ID
				$('#txtSelBCMC').val(),//班次ID
				$('#txtEditSKNY').val(),//上课年月
				$('#selectEditGZRQ').val(),//工作日
				$('#txtEditSK').val(),//上课时段
				$('#selectEditSFYK').val()//是否有课
		);
		$.ajax({
			async     : false,
			type      : "post",
			dataType  : "json",
			url: "<%=basePath%>/Servlet1040160",
			data: {
				CMD    : "<%=Servlet1040160.CMD_UPDATE%>",
			    BeanIn : JSON.stringify(beanIn)
			},
			complete :function(response){},
			success: function(response){
				var strResault = response[0];
				if(strResault=="SUCCESS"){
					blnRet = true;
				}else{
					layer.msg('提示：保存日程设定信息出错！', 1, 0);
					blnRet = false;
				}
			}
		});
		return blnRet;
	}

</script>
</head>
<body>
	<fieldset id = "selectRegion">
		<legend>查询条件</legend>
		<table>
			<tr>
				<th style="width:100px">班次名称</th>
				<td>
					<select id="txtSelBCMC"> 
						<option value="000" selected>请选择</option> 
					</select> 		
				</td>
				<th  style="width:100px"><input type="button" value="查询" id="btnSearch" /></th>
			</tr>
		</table>
	</fieldset>
	<div id="gridCanvas">
		<table id="mmg" class="mmg"></table>
		<div id="pg" style="text-align: right;"></div>
	</div>
	<fieldset id = "editRegion">
		<legend>编辑</legend>
		<div id="buttonCanvas" class="gToolbar gTbrCenter ">
		</div>
		<table>
			<tr>
				<th style="width:100px">班次名称</th>
				<td>
					<input type="hidden" id="txtEditSK" ><input type="hidden" id="txtEditBCRCID" /><input type="text" id="txtEditBCMC" style="width:150px;" >
				</td>
				<th style="width:100px">课程名称</th>
				<td>
					<input type="text" id="txtEditKCMC" style="width:150px;" readonly>
				</td>
				<th style="width:100px">班次时段</th>
				<td>
					<input type="text" id="txtEditBCSD" style="width:180px;" readonly>
				</td>
			</tr>
			<tr style="height:10px;"></tr>
			<tr>
				<th style="width:100px">上课年月</th>
				<td>
					<input type="text" id="txtEditSKNY" style="width:100px;" readonly>
				</td>
				<th style="width:100px">工作日</th>
				<td>
					<select id="selectEditGZRQ"> 
						<option value="000" selected>请选择</option> 
					</select> 
				</td>
				<th style="width:100px">排课</th>
				<td>
					<select id="selectEditSFYK"> 
						<option value="000" selected>请选择</option> 
						<option value="1" >有课</option> 
						<option value="2" >无课</option> 
					</select> 
				</td>
			</tr>
			<tr style="height:10px;"></tr>
		</table>
		<div id="buttonCanvas" class="gToolbar gTbrCenter ">
			<input type="button" value="变更" id="btnUpd" name="btnUpd" />
			<input type="button" value="保存" id="btnSave" name="btnSave"/>
			<input type="button" value="取消" id="btnCancel" name="btnCancel"/>
		</div>
	</fieldset>
</body>
</html>