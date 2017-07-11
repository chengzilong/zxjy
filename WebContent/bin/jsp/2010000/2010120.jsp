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
<title>问题信息</title>
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
//初始化表格
$(document).ready(function(){
   getGRJF();
   var cols = [
       { title:'问题领域', name:'KCMC' ,width:80, sortable:true, align:'center',lockDisplay: true },
       { title:'提问内容', name:'WTXX_TWNR' ,width:250, sortable:true, align:'center',lockDisplay: true },
       { title:'提问时间', name:'WTXX_TWSJ' ,width:80, sortable:true, align:'center',lockDisplay: true },
       { title:'提问状态', name:'WTXX_TWZT' ,width:60, sortable:true, align:'center',lockDisplay: true },
       { title:'有效时间', name:'YXSJ' ,width:80, sortable:true, align:'center',lockDisplay: true },
       { title:'悬赏积分', name:'WTXX_TWJF' ,width:60, sortable:true, align:'center',lockDisplay: true },
       { title:'回答数量', name:'HDSL' ,width:60, sortable:true, align:'center',lockDisplay: true },
       { title:'操作', name:'' ,width:30, sortable:true, align:'center',lockDisplay: true, renderer: function(val,item){
	       if(item.WTXX_TWZT=="已结束"){
	           return '<img id="img-info" class="img-info" title="详情" src="<%=basePath%>/bin/img/detail.gif"></img>';
	       }else{
	       	   return '<img id="img-info" class="img-info" title="详情" src="<%=basePath%>/bin/img/detail.gif"></img>';
	       }
       }}
   ];

   intheight = document.documentElement.clientHeight -$('#editRegion').height()-$('#selectRegion').height()-80; 
   if(intheight<100){
	   intheight = 100;
   }

   mmg = $('.mmg').mmGrid({
         height: intheight
        ,cols: cols
        ,url: '<%=basePath%>/Servlet2010120'
        ,method: 'post'
        ,params:{CMD : "<%=Servlet2010120.CMD_SELECT%>"}
        ,remoteSort:true
        ,sortName: 'WTXX_WTID'
        ,sortStatus: 'ASC'
        ,root: 'items'
        ,multiSelect: false
        ,checkCol: true
        ,indexCol: true
        ,indexColWidth: 35
        ,fullWidthRows: true
        ,autoLoad: false
        ,nowrap:true
        ,plugins: [
            $('#pg').mmPaginator({
              limit:30
            })
        ]
      });
   mmg.on('cellSelected', function(e, item, rowIndex, colIndex){
	  var arrList = mmg.row(rowIndex);
	  	
        //配置节点
        if($(e.target).is('.img-info')){
            e.stopPropagation();  //阻止事件冒泡
            mmg.deselect('all');
            dataBean = item;
            iframeLayerOpenown('<%=basePath%>/bin/jsp/2010000/2010121.jsp');	
        }
         //配置节点
        if($(e.target).is('.img-config')){
            e.stopPropagation();  //阻止事件冒泡
            if(confirm("是否结束问题？")==false) return;
            setWTXXend(item.WTXX_WTID);
    		
        }
   });
   mmg.on('rowSelected', function(e, item, rowIndex, colIndex){//选择数据行
	   var arrList = mmg.row(rowIndex);  
	    $('#txtEditWTID').val(arrList.WTXX_WTID);
	    $("#selectEditKCID  option[value='"+arrList.WTXX_KCID+"'] ").attr("selected",true);
		$('#txtEditTWNR').val(arrList.WTXX_TWNR);
		$("#txtEditTWJF").val(arrList.WTXX_TWJF);
		$("#txtEditXSJF").val(arrList.WTXX_TWJF);
		$("#selectEditYXSJ  option[value='"+arrList.WTXX_YXSJ+"'] ").attr("selected",true);
		if(arrList.WTXX_TWZT=="已回答"){
			$('#REG').val("0");
		}else{
			$('#REG').val("1");
		}
       setButtonStatus("4");
       
   }).on('click', ':checkbox', function(){ //选择checkbox
	   if(this.checked == true){
		   var arrList = mmg.selectedRows();
		   if(arrList.length>0){
			   $("#txtEditWTID").val(arrList[0].WTXX_WTID);
			   $("#selectEditKCID  option[value='"+arrList[0].WTXX_KCID+"'] ").attr("selected",true);
			   $("#txtEditTWNR").val(arrList[0].WTXX_TWNR);
			   $("#txtEditTWJF").val(arrList[0].WTXX_TWJF);
			   $("#txtEditXSJF").val(arrList[0].WTXX_TWJF);
		       $("#selectEditYXSJ  option[value='"+arrList[0].WTXX_YXSJ+"'] ").attr("selected",true);
		       if(arrList[0].WTXX_TWZT=="已回答"){
					$('#REG').val("0");
				}else{
					$('#REG').val("1");
				}
	         setButtonStatus("4");   
		   }
	   }else{
			   $("#txtEditWTID").val("");
			   $("#selectEditKCID").val("000");
		       $("#txtEditTWNR").val("");
		       $("#txtEditTWJF").val("");
			   $("#txtEditXSJF").val("");
		       $("#selectEditYXSJ").val("000");
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
	      $("#txtEditWTID").val(arrList.WTXX_WTID);
		  $("#selectEditKCID  option[value='"+arrList.WTXX_KCID+"'] ").attr("selected",true);
	      $("#txtEditTWNR").val(arrList.WTXX_TWNR);
	      $("#txtEditTWJF").val(arrList.WTXX_TWJF);
		  $("#txtEditXSJF").val(arrList.WTXX_TWJF);
		  $("#selectEditYXSJ  option[value='"+arrList.WTXX_YXSJ+"'] ").attr("selected",true);
		  if(arrList.WTXX_TWZT=="已回答"){
			 $('#REG').val("0");
		  }else{
			 $('#REG').val("1");
	      }
      	  setButtonStatus("4"); 
       }else
       {
    	   $("#txtEditWTID").val("");
		   $("#selectEditKCID").val("000");
		   $("#txtEditTWNR").val("");
		   $("#txtEditTWJF").val("");
		   $("#txtEditXSJF").val("");
		   $("#selectEditYXSJ").val("000");
           setButtonStatus("2"); 
       }
    });

   
   $('#btnSearch').on('click', function(){
	   loadGridByBean();
   });
   
    
   $('#btnDel').on('click', function(){
	   var arrList = mmg.selectedRows();
	   if(arrList.length<=0){
		   layer.alert('请选择要删除的数据行！', 0, '友情提示');
 	       return;
	   }
	   layer.confirm('是否删除问题？', function() {
		   if(deleteWTXXCode()==true){
			   //重新查询数据
			   getGRJF();
			   loadGridByBean();
			   setButtonStatus("2");
			   mmg.deselect('all');
		   }
	   }); 
   });
    
    $('#btnCancel').on('click', function(){
    	setButtonStatus(2);
    	mmg.deselect('all');
    });

    $('#btnSave').on('click', function(){
    	if(optionFlag == "Add"){
    		if(funEditCheck()==false) return;
    		layer.confirm('是否添加问题？', function() {
	    		if(insertWTXXCode()==true){
	  	            //重新查询数据
	  	            getGRJF();
	  	            loadGridByBean();
	  	            setButtonStatus("2");
	  	            mmg.deselect('all');
				}
			});
    	}else if(optionFlag == "Upd"){
    		var arrList = mmg.selectedRows();
    		if(arrList.length<=0){
    			layer.alert('请选择要修改的数据行！', 0, '友情提示');
    			return;
    		}
    		if(funEditCheck()==false) return;
    		layer.confirm('是否修改问题？', function() {
	    		if(updateWTXXCode()==true){
		             //重新查询数据
		             getGRJF();
		             loadGridByBean();
		             setButtonStatus("2");
		             mmg.deselect('all');
		        }
	        });
    	}
    	//setButtonStatus("1");  
    });
    loadSearchSelect($("#txtSelKCID"),"TYPE_KCMC","问题领域");
    loadEditSelect($("#selectEditKCID"),"TYPE_KCMC","问题领域");
    loadEditSelect($("#selectEditYXSJ"),"TYPE_YXSJ","有效时间");
    loadGridByBean();
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
       		   getGRJF();
	      }
	  });
}
var optionFlag = "";

function btn_Add(){
	setButtonStatus("31");
	optionFlag = "Add";
}
function btn_Upd(){
   setButtonStatus("32");
   optionFlag = "Upd";
}

function makeBeanIn(strKCID){
	this.WTXX_KCID = strKCID;
}
function makeBeanInadd(strKCID,strTWNR,strXSJF,strYXSJ){
	this.WTXX_KCID = strKCID;
    this.WTXX_TWNR = strTWNR; 
    this.WTXX_TWJF = strXSJF; 
    this.WTXX_YXSJ = strYXSJ; 
}
function makeBeanInedit(strWTID,strKCID,strTWNR,strXSJF,strTWJF,strYXSJ){
    this.WTXX_WTID = strWTID;
    this.WTXX_KCID = strKCID;
    this.WTXX_TWNR = strTWNR;  
    this.WTXX_TWJF = strXSJF;  
    this.TWJF = strTWJF;  
    this.WTXX_YXSJ = strYXSJ;
}
function makeBeanIndel(strWTID,strTWJF){
    this.WTXX_WTID = strWTID;
    this.WTXX_TWJF = strTWJF;
}
function loadGridByBean(){
	var beanIn = new makeBeanIn(
			$('#txtSelKCID').val()
	);
	//重新查询数据
	mmg.load({
    	beanLoad  :  JSON.stringify(beanIn)
	});
}

function insertWTXXCode(){
	var blnRet = false;
	var beanIn = new makeBeanInadd(
           $('#selectEditKCID').val(),
           $('#txtEditTWNR').val(),
           $('#txtEditXSJF').val(),
           $('#selectEditYXSJ').val()
	);
	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/Servlet2010120",
		data: {
			CMD    : "<%=Servlet2010120.CMD_INSERT%>",
		    BeanIn : JSON.stringify(beanIn)
		},
		complete :function(response){},
		success: function(response){
			var strResault = response[0];
			if(strResault=="SUCCESS"){
				layer.msg('恭喜：添加问题成功！', 1, 9);
				blnRet = true;
			}else if(strResault=="FAILURE"){
				layer.msg('对不起：添加问题失败！', 1, 8);
				blnRet = false;
			}else{
				layer.msg('提示：添加问题出错！', 1, 0);
				blnRet = false;
			}
		}
	});
	return blnRet;
}
function updateWTXXCode(){
	var blnRet = false;
	var beanIn = new makeBeanInedit(
		   $('#txtEditWTID').val(),
           $('#selectEditKCID').val(),
           $('#txtEditTWNR').val(),
           $('#txtEditXSJF').val(),
           $('#txtEditTWJF').val(),
           $('#selectEditYXSJ').val()
	);
   
    $.ajax({
      async     : false,
      type      : "post",
      dataType  : "json",
      url: "<%=basePath%>/Servlet2010120",
      data: {
         CMD    : "<%=Servlet2010120.CMD_UPDATE%>",
         BeanIn : JSON.stringify(beanIn)
      },
      complete :function(response){},
      success: function(response){
          var strResault = response[0];
          if(strResault=="SUCCESS"){
        	 layer.msg('恭喜：修改问题成功！', 1, 9);
             blnRet = true;
          }else if(strResault=="FAILURE"){
             layer.msg('对不起：修改问题失败！', 1, 8);
             blnRet = false;
          }else{
          	 layer.msg('提示：修改问题出错！', 1, 0);
          	 blnRet = false;
          }
      }
   });
   return blnRet;
}

function deleteWTXXCode(){
	var blnRet = false;
	var beanIn = new makeBeanIndel(
			$('#txtEditWTID').val(),
			$('#txtEditXSJF').val()
	);

	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/Servlet2010120",
		data: {
			CMD    : "<%=Servlet2010120.CMD_DELETE%>",
			BeanIn : JSON.stringify(beanIn)
		},
		complete : function(response) {},
		success : function(response) {
			var strResault = response[0];
			if (strResault == "SUCCESS") {
				layer.msg('恭喜：删除问题成功！', 1, 9);
				blnRet = true;
			} else if (strResault == "FAILURE") {
				layer.msg('对不起：删除问题失败！', 1, 8);
				blnRet = false;
			} else{
				layer.msg('提示：删除问题出错！', 1, 0);
				blnRet = false;
			}
		}
	});
	return blnRet;
}

//设置按钮状态
function setButtonStatus(strFlag) {
	if (strFlag == "1") {//初始状态
		$('#btnAdd').attr("disabled", "disabled");
		$('#btnUpd').attr("disabled", "disabled");
		$('#btnDel').attr("disabled", "disabled");
		$('#btnSave').attr("disabled", "disabled");
		$('#btnCancel').attr("disabled", "disabled");
	} else if (strFlag == "2") {//查询后/返回
		$('#btnAdd').removeAttr("disabled");
		$('#btnUpd').attr("disabled", "disabled");
		$('#btnDel').attr("disabled", "disabled");
		$('#btnSave').attr("disabled", "disabled");
		$('#btnCancel').attr("disabled", "disabled");
		$('#selectEditKCID').attr("disabled","disabled");
		$('#selectEditYXSJ').attr("disabled","disabled");
		$('#txtEditTWNR').attr("disabled","disabled");
		$('#txtEditXSJF').attr("disabled","disabled");
	
		
		$('#selectEditKCID').val("000");
		$('#selectEditYXSJ').val("000");
		$('#txtEditTWNR').val("");
		$('#txtEditXSJF').val("");

	} else if (strFlag.substring(0, 1) == "3") {
		//新增/修改
		$('#btnAdd').attr("disabled","disabled");
		$('#btnUpd').attr("disabled", "disabled");
		$('#btnDel').attr("disabled", "disabled");
		$('#btnSave').removeAttr("disabled");
		$('#btnCancel').removeAttr("disabled");
		if (strFlag == "31") {//新增
			$('#selectEditKCID').removeAttr("disabled");
			$('#selectEditYXSJ').removeAttr("disabled");
			$('#txtEditTWNR').removeAttr("disabled");
		    $('#txtEditXSJF').removeAttr("disabled");
			
			$('#selectEditKCID').val("000");
			$('#selectEditYXSJ').val("000");
			$('#txtEditTWNR').val("");
			$('#txtEditXSJF').val("");
			$('#selectEditKCID').focus();
		} else if (strFlag == "32") {//修改
			$('#selectEditKCID').removeAttr("disabled");
			$('#selectEditYXSJ').removeAttr("disabled");
			$('#txtEditTWNR').removeAttr("disabled");
			if($('#REG').val()=="0"){
			}else{
				$('#txtEditXSJF').removeAttr("disabled");
				$('#txtEditXSJF').select();
			}
			$('#selectEditKCID').focus();
		} else if (strFlag == "33") {//删除
		
		}
	} else if (strFlag == "4") {//选中行
		$('#btnAdd').removeAttr("disabled");
		$('#btnUpd').removeAttr("disabled");
		
		if($('#REG').val()=="0"){
				$('#btnDel').attr("disabled", "disabled");
		}else{
				$('#btnDel').removeAttr("disabled");
		}
		$('#btnSave').attr("disabled", "disabled");
		$('#btnCancel').attr("disabled", "disabled");
		$('#selectEditKCID').attr("disabled","disabled");
		$('#selectEditYXSJ').attr("disabled","disabled");
		$('#txtEditTWNR').attr("disabled","disabled");
		$('#txtEditXSJF').attr("disabled","disabled");
	}
}
//验证编辑输入数据
function funEditCheck() {
		if ($('#selectEditKCID').val() == "000") {
			layer.alert('请选择问题领域！', 0, '友情提示', function() {
				$('#selectEditKCID').focus();
				layer.close(layer.index);
			});
			return false;
		}	
		if ($('#selectEditYXSJ').val() == "000") {
			layer.alert('请选择有效时间！', 0, '友情提示', function() {
				$('#selectEditYXSJ').focus();
				layer.close(layer.index);
			});
			return false;
		}	
		if ($('#txtEditXSJF').val() == "") {
			layer.alert('请输入0-100之间的悬赏积分！', 0, '友情提示', function() {
				$('#txtEditXSJF').focus();
				layer.close(layer.index);
			});
			return false;
		}
		if ($('#txtEditTWNR').val() == "") {
			layer.alert('请输入提问内容！', 0, '友情提示', function() {
				$('#txtEditTWNR').focus();
				layer.close(layer.index);
			});
			return false;
		}
		if (optionFlag == "Add") {
			if (parseFloat($('#txtEditXSJF').val()) > parseFloat($('#txtEditGRJF').html())||$('#txtEditGRJF').html()=="") {
				layer.alert('悬赏积分不能大于个人积分！', 0, '友情提示', function() {
					$('#txtEditXSJF').focus();
					layer.close(layer.index);
				});
				return false;
			}	
		}else if(optionFlag == "Upd"){
			if ((parseFloat($('#txtEditXSJF').val())-parseFloat($('#txtEditTWJF').val())) > parseFloat($('#txtEditGRJF').html())) {
				layer.alert('修改后的积分差值不能大于个人积分！', 0, '友情提示', function() {
					$('#txtEditXSJF').focus();
					layer.close(layer.index);
				});
				return false;
			}	
		}
	return true;
}
function getGRJF(){
	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/Servlet2010120",
		data: {
			CMD    : "CMD_GRJF"
		},
		complete : function(response) {},
		success : function(response) {
			var strResault = response[0];
			var dataBean = response[1];
			if (strResault == "SUCCESS") {
				$('#txtEditGRJF').html(dataBean.JJXX_JJZF);
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
             alert("操作成功!");
             loadGridByBean();
          }else if(strResault=="FAILURE"){
             alert("操作失败!");
          }
      }
   });
}
</script>
</head>
<body>
	<fieldset id = "selectRegion">
		<legend>查询条件</legend>
		<table>
			<tr>
				<th style="width:100px">问题领域</th>
				<td style="width:100px">
					<select id="txtSelKCID"> </select>
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
		<table id="detailCanvas" class="eTable6">
			<tr>
				<th style="width:100px">问题领域</th>	
				<td style="width:80px">
				<input type="hidden" id="txtEditWTID" />
					<select id="selectEditKCID">
						<option value="000" selected>请选择</option> 
					</select>
				</td>
				<th style="width:100px">有效时间</th>	
				<td style="width:80px">
					<select id="selectEditYXSJ">
						<option value="000" selected>请选择</option> 
					</select>
				</td>
				<th style="width:80px">个人积分:</th>	
				<td id="txtEditGRJF">
					
				</td>
				<th style="width:100px">悬赏积分</th>	
				<td>
					<input type="hidden"  id="REG" /><input type="hidden"  id="txtEditTWJF" /><input type="text"   style="width:36px" id="txtEditXSJF" >
				</td>
			</tr>
			<tr>
				<th style="width:100px">提问内容</th>	
				<td colspan="7">
				<textarea id="txtEditTWNR" cols="80" rows="5"  ></textarea>  
				</td>
			</tr>
		</table>
		<div id="buttonCanvas" class="gToolbar gTbrCenter ">
			<input type="button" value="新增" id="btnAdd" name="btnAdd" onclick="btn_Add()" />
			<input type="button" value="修改" id="btnUpd" name="btnUpd" onclick="btn_Upd()" /> 
			<input type="button" value="删除" id="btnDel" name="btnDel" /> 
			<input type="button" value="保存" id="btnSave" name="btnSave" /> 
			<input type="button" value="取消" id="btnCancel" name="btnCancel" /> 
			
		</div>
	</fieldset>
</body>
</html>