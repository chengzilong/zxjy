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
<!--表格样式Start  -->
<link rel="stylesheet" href="<%=basePath%>/bin/css/mmgrid/main.css" type="text/css">
<link rel="stylesheet" href="<%=basePath%>/bin/css/mmgrid/mmGrid.css" type="text/css">
<link rel="stylesheet" href="<%=basePath%>/bin/css/mmgrid/mmPaginator.css" type="text/css">
<link rel="stylesheet" href="<%=basePath%>/bin/css/mmgrid/normalize.css" type="text/css">
<link rel="stylesheet" href="<%=basePath%>/bin/css/mmgrid/tablesize.css" type="text/css">
<!--表格样式End  -->
<title>教师认证</title>
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
var obj = new Object();//参数对象
//初始化表格
$(document).ready(function(){
   var cols = [
       { title:'教师姓名', name:'JSXX_JSXM' ,width:80, sortable:true, align:'center',lockDisplay: true },
       { title:'性别', name:'JSXX_XBMC' ,width:30, sortable:true, align:'center',lockDisplay: true  },
       { title:'身份证号', name:'JSXX_SFZH' ,width:150, sortable:true, align:'center',lockDisplay: true  },
       { title:'出生日期', name:'JSXX_CSRQ' ,width:100, sortable:true, align:'center',lockDisplay: true  },
       { title:'联系方式', name:'JSXX_LXFS' ,width:100, sortable:true, align:'center',lockDisplay: true  },
       { title:'住址', name:'JSXX_ZZ' ,width:200, sortable:true, align:'center',lockDisplay: true  },
       { title:'学历', name:'JSXX_XL' ,width:40, sortable:true, align:'center',lockDisplay: true  },
       { title:'毕业学校', name:'JSXX_BYXX' ,width:100, sortable:true, align:'center',lockDisplay: true  },
       { title:'毕业年份', name:'JSXX_BYNF' ,width:80, sortable:true, align:'center',lockDisplay: true  },
       { title:'是否认证', name:'JSXX_SFRZ' ,width:60, sortable:true, align:'center',lockDisplay: true  },
       { title:'操作', name:'' ,width:40, sortable:true, align:'center',lockDisplay: true, renderer: function(val,item){
         	if(item.JSXX_SFRZ=="已认证"){
       			return '<img id="img-info" class="img-info" title="详情" src="<%=basePath%>/bin/img/detail.gif"></img>';
       		}else{
       			 return '<img id="img-info" class="img-info" title="详情" src="<%=basePath%>/bin/img/detail.gif"></img>&nbsp;<img id="img-config" class="img-config" title="通过认证" src="<%=basePath%>/bin/img/identificate.gif"></img>';
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
        ,url: '<%=basePath%>/Servlet1070130'
        ,method: 'post'
        ,params:{CMD : "<%=Servlet1070130.CMD_SELECT%>"}
        ,remoteSort:true
        ,sortName: 'JSXX_JSID'
        ,sortStatus: 'ASC'
        ,root: 'items'
        ,multiSelect: false
        ,checkCol: false
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
        if($(e.target).is('.img-config')){
            e.stopPropagation();  //阻止事件冒泡
            layer.confirm('是否通过认证？', function() {
            	var reg = setKmList(item.JSXX_JSID);
                if(!reg){
                	layer.alert('该教师没有授课科目，无法通过认证！', 0, '友情提示');
    				return;
                }
                Teacheridentification(item.JSXX_JSID);
            });
        }
        if($(e.target).is('.img-info')){
            e.stopPropagation();  //阻止事件冒泡
           	obj.JSXX_JSID=item.JSXX_JSID;	//教师ID
           	obj.JSXX_JSBM=item.JSXX_JSBM;	//教师编码
			obj.JSXX_JSXM=item.JSXX_JSXM;	//教师姓名
			obj.JSXX_XBMC=item.JSXX_XBMC;//性别
			obj.JSXX_CSRQ=item.JSXX_CSRQ;	//出生日期
			obj.JSXX_LXFS=item.JSXX_LXFS;//联系方式	
			obj.JSXX_ZZ=item.JSXX_ZZ;//住址
			obj.JSXX_XL=item.JSXX_XL;//学历
			obj.JSXX_BYXX=item.JSXX_BYXX;//毕业学校
			obj.JSXX_BYNF=item.JSXX_BYNF;//毕业年份
			obj.JSXX_SFZH=item.JSXX_SFZH;//身份证号	
			obj.JSXX_ZY=item.JSXX_ZY;//专业
			obj.JSXX_NJ=item.JSXX_NJ;//年级
			obj.JSXX_JNJY=item.JSXX_JNJY;//几年经验
			obj.JSXX_JSZG=item.JSXX_JSZG;//教师资格
			obj.JSXX_SCLY=item.JSXX_SCLY;//擅长领域
			obj.JSXX_XQAH=item.JSXX_XQAH;//兴趣爱好	
			obj.JSXX_GRJJ=item.JSXX_GRJJ;//个人简介
			obj.JSXX_SFRZ=item.JSXX_SFRZ;//是否认证
			iframeLayerOpen('<%=basePath%>/bin/jsp/1070000/1070111.jsp');
        }
   });
   
   $('#btnSearch').on('click', function(){
	   loadGridByBean();
   });
    loadGridByBean();  
});
function makeBeanIn(strJSXM,strSFRZ){
	this.JSXX_JSXM = strJSXM;
	this.JSXX_SFRZ = strSFRZ;
}
function makeBeanInedit(strJSID,strJSBM,strJSXM){
	this.JSXX_JSID = strJSID;
	this.JSXX_JSBM = strJSBM;
	this.JSXX_JSXM = strJSXM;
}
function loadGridByBean(){
	var beanIn = new makeBeanIn(
			$('#txtSelJSXM').val(),
			$('#txtSelSFRZ').val()
	);
	//重新查询数据
	mmg.load({
    	beanLoad  :  JSON.stringify(beanIn)
	});
}
function Teacheridentification(strJSID,strJSBM,strJSXM){
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
             loadGridByBean();
          }else if(strResault=="FAILURE"){
        	  layer.msg('提示：操作失败！', 1, 8);
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
			var strKM = response[2];
			if(strKM=="NOKM"){
				strREG=false;
			}
		}
	});
	return strREG;
}
</script>
</head>
<body>
	<fieldset id = "selectRegion">
		<legend>查询条件</legend>
		<table>
			<tr>
				<th style="width:100px">教师姓名</th>
				<td><input id="txtSelJSXM" name="商户代码" maxlength="20" /></td>
				<th style="width:100px">是否认证</th>
				<td style="width:100px" >
					<select id="txtSelSFRZ"> 
						<option value="000" selected>所有</option> 
						<option value="0" >未认证</option> 
						<option value="1" >已认证</option> 
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