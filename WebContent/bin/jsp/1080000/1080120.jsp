<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.xsjy.servlet.servlet1080000.Servlet1080120"%> 
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
<title>教学点交费</title>
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
var mmg;
var intheight;
var obj;//参数对象
//初始化表格
$(document).ready(function(){
   var cols = [
       { title:'学生姓名', name:'XSXM' ,width:80, sortable:true, align:'center',lockDisplay: true },
       { title:'班次名称', name:'BCMC' ,width:100, sortable:true, align:'center',lockDisplay: true },
       { title:'课程类型', name:'KCLX' ,width:80, sortable:true, align:'center',lockDisplay: true  },
       { title:'上课时段', name:'SKSD' ,width:60, sortable:true, align:'center',lockDisplay: true  },
       { title:'学时', name:'BMXX_XS' ,width:40, sortable:true, align:'center',lockDisplay: true  },
       { title:'课程名称', name:'KCMC' ,width:80, sortable:true, align:'center',lockDisplay: true  },
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
        ,url: '<%=basePath%>/Servlet1080120'
        ,method: 'post'
        ,params:{CMD : "<%=Servlet1080120.CMD_SELECT%>"}
        ,remoteSort:true
        ,sortName: 'BMXX_BMID'
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
   
   mmg.on('rowSelected', function(e, item, rowIndex, colIndex){//选择数据行
	   var arrList = mmg.row(rowIndex);  
	   $('#txtEditBMID').val(arrList.BMXX_BMID);
	   $('#txtEditXSXM').val(arrList.XSXM);
       $('#txtEditKCMC').val(arrList.KCMC);
       $('#txtEditJSXM').val(arrList.JSXM);
       $('#txtEditFY').val(arrList.BMXX_FY);
       $('#txtEditYJFY').val(arrList.BMXX_YJFY);
       $('#txtEditWJFY').val(arrList.BMXX_WJFY);
       setButtonStatus("4");
       
   }).on('click', ':checkbox', function(){ //选择checkbox
	   if(this.checked == true){
		   var arrList = mmg.selectedRows();
		   if(arrList.length>0){
			   $('#txtEditBMID').val(arrList[0].BMXX_BMID);
			   $('#txtEditXSXM').val(arrList[0].XSXM);
		       $('#txtEditKCMC').val(arrList[0].KCMC);
		       $('#txtEditJSXM').val(arrList[0].JSXM);
		       $('#txtEditFY').val(arrList[0].BMXX_FY);
		       $('#txtEditYJFY').val(arrList[0].BMXX_YJFY);
		       $('#txtEditWJFY').val(arrList[0].BMXX_WJFY);
	         setButtonStatus("4");   
		   }
	   }else{
			   $('#txtEditBMID').val("");
			   $('#txtEditXSXM').val("");
		       $('#txtEditKCMC').val("");
		       $('#txtEditJSXM').val("");
		       $('#txtEditFY').val("");
		       $('#txtEditYJFY').val("");
		       $('#txtEditWJFY').val("");
		   setButtonStatus(2);//未选中行,则只有新增按钮可用
	   }
   }).on('loadSuccess',function(e, data){
       //设置按钮状态
	   setButtonStatus(2);
   }).on('click', 'tr', function(e){ //点击行;
	   if(mmg.rowsLength()<=0) return; //无数据,不进行操作
       var rowIndex = e.target.parentNode.rowIndex;
       if(typeof(rowIndex) == "undefined"){
    	   rowIndex = e.target.parentNode.parentNode.rowIndex;
       }
       var arrList = mmg.row(rowIndex);
       var arrList1 = mmg.selectedRows();
       if(arrList1.length>0) {
	       $('#txtEditBMID').val(arrList.BMXX_BMID);
		   $('#txtEditXSXM').val(arrList.XSXM);
	       $('#txtEditKCMC').val(arrList.KCMC);
	       $('#txtEditJSXM').val(arrList.JSXM);
	       $('#txtEditFY').val(arrList.BMXX_FY);
	       $('#txtEditYJFY').val(arrList.BMXX_YJFY);
	       $('#txtEditWJFY').val(arrList.BMXX_WJFY);
      	   setButtonStatus("4"); 
       } else {
           setButtonStatus("2"); 
       }
    });
   
   $('#btnSearch').on('click', function(){
	   loadGridByBean();
   });   
   
   $('#btnPay').on('click', function(){
    	var arrList = mmg.selectedRows();
    	if(arrList.length<=0){
    		layer.alert('请选择要交费的报名信息！', 0, '友情提示');
    		return;
    	}

    	obj = new Object();
		obj.JFMX_BMID=arrList[0].BMXX_BMID;
		layer.confirm('请确认是否交费？', function() {
			layer.close(layer.index);
			iframeLayerOpenown('<%=basePath%>/bin/jsp/1080000/1080121.jsp');
		});
    });
    loadGridByBean();
    loadSearchSelect($("#selectEditBMFS"),"TYPE_BMFS","报名方式");
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
var optionFlag = "";
function btn_Upd(){
   setButtonStatus("32");
   optionFlag = "Upd";
}

function makeBeanIn(strBMFS,strXSXM,strJSXM){
	this.BMXX_BMFS = strBMFS;
    this.XSXM = strXSXM;
    this.JSXM = strJSXM;
}
function makeBeanInedit(strBMID,strYJFY,strWJFY){
    this.BMXX_BMID = strBMID;    
    this.BMXX_YJFY = strYJFY;  
    this.BMXX_WJFY = strWJFY;  
}
function makeBeanIneditmx(strJFLXID,strJFJE,strBZ){
    this.JFMX_JFLXID = strJFLXID;    
    this.JFMX_JFJE = strJFJE;  
    this.JFMX_BZ = strBZ;  
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
function updateBMFYCode(){
	var blnRet = false;
	var beanIn = new makeBeanInedit(
		   $('#txtEditBMID').val(),
           $('#txtEditYJFY').val(),
           $('#txtEditWJFY').val()
	);
   var beanInmx = new makeBeanIneditmx(
           $('#selectEditJFLX').val(),
           $('#txtEditYJFY').val(),
           $('#txtEditBZ').val()
	);
    $.ajax({
      async     : false,
      type      : "post",
      dataType  : "json",
      url: "<%=basePath%>/Servlet1080120",
      data: {
         CMD    : "<%=Servlet1080120.CMD_UPDATE%>",
         BeanIn : JSON.stringify(beanIn),
         BeanInmx : JSON.stringify(beanInmx)
      },
      complete :function(response){},
      success: function(response){
          var strResault = response[0];
          if(strResault=="SUCCESS"){
        	  layer.msg('恭喜：交费成功！', 1, 9);
             blnRet = true;
          }else if(strResault=="FAILURE"){
        	  layer.msg('对不起：交费失败！', 1, 8);
             blnRet = false;
          }
      }
   });
   return blnRet;
}
//设置按钮状态
function setButtonStatus(strFlag) {
	if (strFlag == "1") {//初始状态
		$('#btnUpd').attr("disabled", "disabled");
		$('#btnSave').attr("disabled", "disabled");
		$('#btnCancel').attr("disabled", "disabled");
	} else if (strFlag == "2") {//查询后/返回
		$('#btnUpd').attr("disabled", "disabled");
		$('#btnSave').attr("disabled", "disabled");
		$('#btnCancel').attr("disabled", "disabled");
		$('#selectEditJFLX').attr("disabled","disabled");
		$('#txtEditBZ').attr("disabled","disabled");
		$('#txtEditFY').attr("disabled","disabled");
		$('#txtEditYJFY').attr("disabled","disabled");
		$('#txtEditWJFY').attr("disabled","disabled");
		$('#txtEditXSXM').attr("disabled","disabled");
		$('#txtEditKCMC').attr("disabled","disabled");
		$('#txtEditJSXM').attr("disabled","disabled");
	
		
		$('#txtEditBMID').val("");			
		$('#selectEditJFLX').val("000");
		$('#txtEditBZ').val("");
		$('#txtEditFY').val("");
		$('#txtEditYJFY').val("");
		$('#txtEditWJFY').val("");
		$('#txtEditXSXM').val("");
		$('#txtEditKCMC').val("");
		$('#txtEditJSXM').val("");

	} else if (strFlag.substring(0, 1) == "3") {
		//新增/修改
		$('#btnUpd').attr("disabled", "disabled");
		$('#btnSave').removeAttr("disabled");
		$('#btnCancel').removeAttr("disabled");
		if (strFlag == "31") {//新增
		} else if (strFlag == "32") {//修改
			$('#selectEditJFLX').removeAttr("disabled");
			$('#txtEditBZ').removeAttr("disabled");
			$('#txtEditYJFY').removeAttr("disabled");
			$('#txtEditYJFY').focus();
			$('#txtEditYJFY').select();
		} else if (strFlag == "33") {//删除
		
		}
	} else if (strFlag == "4") {//选中行
		$('#btnUpd').removeAttr("disabled");
		$('#btnSave').attr("disabled", "disabled");
		$('#btnCancel').attr("disabled", "disabled");
		$('#selectEditJFLX').attr("disabled","disabled");
		$('#txtEditBZ').attr("disabled","disabled");
		$('#txtEditFY').attr("disabled","disabled");
		$('#txtEditYJFY').attr("disabled","disabled");
		$('#txtEditWJFY').attr("disabled","disabled");
		$('#txtEditXSXM').attr("disabled","disabled");
		$('#txtEditKCMC').attr("disabled","disabled");
		$('#txtEditJSXM').attr("disabled","disabled");
	}
}
//验证编辑输入数据
function funEditCheck() {
	if (optionFlag == "Upd") {
		if ($('#txtEditYJFY').val() == "") {
			layer.alert('已交费用不能为空！', 0, '友情提示', function() {
				$('#txtEditYJFY').focus();
				layer.close(layer.index);
			});
			return false;
		}	
		if (parseFloat($('#txtEditYJFY').val())>parseFloat($('#txtEditFY').val()))  {
			layer.alert('已交费用不能大于费用！', 0, '友情提示', function() {
				$('#txtEditYJFY').focus();
				layer.close(layer.index);
			});
			return false;
		}
		if ($('#selectEditJFLX').val() == "000") {
			layer.alert('请选择交费类型！', 0, '友情提示', function() {
				$('#selectEditJFLX').focus();
				layer.close(layer.index);
			});
			return false;
		}	
	}
	return true;
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
	<fieldset id = "editRegion">
		<legend>操作</legend>
		<div id="buttonCanvas" class="gToolbar gTbrCenter ">
			<input type="hidden" id="txtEditBMID" />
			<input type="button" value="交费" id="btnPay" name="btnUpd" /> 
		</div>
	</fieldset>
</body>
</html>