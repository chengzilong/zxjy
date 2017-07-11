<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.xsjy.servlet.servlet1080000.Servlet1080110"%> 
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
<title>网上交费</title>
<script type="text/javascript" src="<%=basePath%>/bin/js/laydate/laydate.js"></script>
<script type="text/javascript" src="<%=basePath%>/bin/js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="<%=basePath%>/bin/js/common.js"></script>
<script type="text/javascript" src="<%=basePath%>/bin/js/json2.js"></script>
<!--表格脚本Start  -->
<script type="text/javascript" src="<%=basePath%>/bin/js/mmgrid/mmGrid.js"></script>
<script type="text/javascript" src="<%=basePath%>/bin/js/mmgrid/mmPaginator.js"></script>
<!--表格脚本End  -->
<script type="text/javascript">
var mmg;
var intheight;
//初始化表格
$(document).ready(function(){
   var cols = [
       { title:'学生姓名', name:'XSXM' ,width:80, sortable:true, align:'center',lockDisplay: true },
       { title:'课程类型', name:'KCLX' ,width:80, sortable:true, align:'center',lockDisplay: true  },
       { title:'上课时段', name:'SKSD' ,width:80, sortable:true, align:'center',lockDisplay: true  },
       { title:'学时', name:'BMXX_XS' ,width:40, sortable:true, align:'center',lockDisplay: true  },
       { title:'课程名称', name:'KCMC' ,width:100, sortable:true, align:'center',lockDisplay: true  },
       { title:'教师姓名', name:'JSXM' ,width:80, sortable:true, align:'center',lockDisplay: true  },
       { title:'报名方式', name:'BMFS' ,width:120, sortable:true, align:'center',lockDisplay: true  },
       { title:'推荐教师', name:'TJJS' ,width:80, sortable:true, align:'center',lockDisplay: true  },
       { title:'费用', name:'BMXX_FY' ,width:40, sortable:true, align:'center',lockDisplay: true  },
       { title:'已交费用', name:'BMXX_YJFY' ,width:40, sortable:true, align:'center',lockDisplay: true  },
       { title:'未交费用', name:'BMXX_WJFY' ,width:40, sortable:true, align:'center',lockDisplay: true  }
   ];

   intheight = document.documentElement.clientHeight -$('#editRegion').height()-$('#selectRegion').height()-80; 
   if(intheight<100){
	   intheight = 100;
   }

   mmg = $('.mmg').mmGrid({
         height: intheight
        ,cols: cols
        ,url: '<%=basePath%>/Servlet1080110'
        ,method: 'post'
        ,params:{CMD : "<%=Servlet1080110.CMD_SELECT%>"}
        ,remoteSort:true
        ,sortName: 'BMXX_BMID'
        ,sortStatus: 'ASC'
        ,root: 'items'
        ,multiSelect: false
        ,checkCol: false
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
   
   $('#btnSearch').on('click', function(){
	   loadGridByBean();
   });   
    loadGridByBean();
    loadSearchSelect($("#selectEditBMFS"),"TYPE_BMFS","报名方式");
});

function makeBeanIn(strBMFS,strXSXM,strJSXM){
	this.BMXX_BMFS = strBMFS;
    this.XSXM = strXSXM;
    this.JSXM = strJSXM;
}
function loadGridByBean(){
	var beanIn = new makeBeanIn(
			$('#selectEditBMFS').val(),
			$('#txtSelXSXM').val(),
			$('#txtSelJSXM').val()
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
				<th style="width:100px">报名方式</th>
				<td style="width:100px">
					<select id="selectEditBMFS"> 
						<option value="000" selected>请选择</option> 
					</select>
				</td>
				<th style="width:100px">学生姓名</th>
				<td><input id="txtSelXSXM" name="学生姓名" maxlength="20" /></td>
				<th style="width:100px">教师姓名</th>
				<td><input id="txtSelJSXM" name="教师姓名" maxlength="20" /></td>
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