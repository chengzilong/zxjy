<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.xsjy.servlet.servlet2020000.Servlet2020120"%> 
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
				return '<button class="btn btn-info">详情</button>';
			}},
	   ];
	
	   intheight = document.documentElement.clientHeight -$('#selectRegion').height()-50; 
	   if(intheight<100){
		   intheight = 100;
	   }
	
	   mmg = $('.mmg').mmGrid({
	         height: intheight
	        ,cols: cols
	        ,url: '<%=basePath%>/Servlet2020120'
	        ,method: 'post'
	        ,params:{CMD : "<%=Servlet2020120.CMD_SELECT%>"}
	        ,remoteSort:true
	        ,sortName: 'BCRC_BCRCID'
	        ,sortStatus: 'ASC'
	        ,root: 'items'
	        ,multiSelect: false
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
				iframeLayerOpen('<%=basePath%>/bin/jsp/2020000/2020121.jsp');
			}
	   });
	      
	   $('#btnSearch').on('click', function(){
	       if($("#txtSelBCMC").val()=="000"){
			   layer.alert('请选择班次！', 0, '友情提示');
			   return;
		   }
		   loadGridByBean();
	   });
	   loadSearchSelect($("#txtSelBCMC"),"TYPE_BCMCXS","班次名称");
	});
	function makeBeanIn(strBCID){	
	    this.BCRC_BCID = strBCID;//班次ID
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
</body>
</html>