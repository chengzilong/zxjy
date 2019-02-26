<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.xsjy.servlet.servlet9010000.Servlet9010110"%>
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
		<title>用户管理</title>
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
		   var cols = [
		       { title:'用户ID', name:'YHXX_YHID' ,width:80, sortable:true, align:'center',lockDisplay: true },
		       { title:'用户名称', name:'YHXX_YHMC' ,width:80, sortable:true, align:'center',lockDisplay: true },
		       { title:'用户密码', name:'YHXX_YHMM' ,width:100,sortable:true, align:'center',lockDisplay: true },
		       { title:'角色', name:'YHJS_JSMC' ,width:100, sortable:true, align:'center',lockDisplay: true  },
		       { title:'所属站点', name:'ZDMC' ,width:100, sortable:true, align:'center',lockDisplay: true  },
		       { title:'锁定状态', name:'SDZT' ,width:80,sortable:true, align:'center',lockDisplay: true },
		       { title:'是否删除', name:'SCBZ' ,width:80,sortable:true, align:'center',lockDisplay: true },
		       { title:'创建人', name:'YHXX_CJR' ,hidden: true,width:80,sortable:true, align:'center',lockDisplay: false },
		       { title:'创建时间', name:'YHXX_CJSJ',hidden: true,width:100,sortable:true, align:'center',lockDisplay: false },
		       { title:'修改人', name:'YHXX_GXR' ,hidden: true,width:80,sortable:true, align:'center',lockDisplay: false },
		       { title:'修改时间', name:'YHXX_GXSJ' ,hidden: true,width:100,sortable:true, align:'center',lockDisplay: false }
		   ];

		   intheight = document.documentElement.clientHeight -$('#editRegion').height()-$('#selectRegion').height()-80;
		   if(intheight<100){
			   intheight = 100;
		   }

		   mmg = $('.mmg').mmGrid({
		         height: intheight
		        ,cols: cols
		        ,url: '<%=basePath%>/Servlet9010110'
		        ,method: 'post'
		        ,params:{CMD : "<%=Servlet9010110.CMD_SELECT%>"}
		        ,remoteSort:true
		        ,sortName: 'YHXX_JSID,YHXX_CJSJ'
		        ,sortStatus: 'ASC'
		        ,root: 'items'
		        ,multiSelect: false
		        ,checkCol: true
		        ,indexCol: true
		        ,indexColWidth: 35
		        ,fullWidthRows: true
		        ,autoLoad: false
		        ,nowrap: true
		        ,plugins: [
		            $('#pg').mmPaginator({
		              limit:30
		            })
		        ]
		      });

		   mmg.on('rowSelected', function(e, item, rowIndex, colIndex){//选择数据行
			   var arrList = mmg.row(rowIndex);
			   $('#txtEditUuid').val(arrList.YHXX_UUID);
		       $('#txtEditCode').val(arrList.YHXX_YHID);
		       $('#txtEditName').val(arrList.YHXX_YHMC);

		       $('#selectEditRole').val(arrList.YHXX_JSID);
		       $('#selectEditZhanDian').val(arrList.YHXX_ZDID);
		       $('#txtEditPass').val("**********");
		       $('#txtEditConfirm').val("**********");
		       $('#selectEditState').val(arrList.YHXX_SDZT);

		       setButtonStatus("4");

		   }).on('click', ':checkbox', function(){ //选择checkbox
			   if(this.checked == true){
				   var arrList = mmg.selectedRows();
				   if(arrList.length>0){
				     $('#txtEditUuid').val(arrList[0].YHXX_UUID);
			         $('#txtEditCode').val(arrList[0].YHXX_YHID);
			         $('#txtEditName').val(arrList[0].YHXX_YHMC);
			         $('#selectEditRole').val(arrList[0].YHXX_JSID);
			         $('#selectEditZhanDian').val(arrList[0].YHXX_ZDID);
			         $('#txtEditPass').val("**********");
			         $('#txtEditConfirm').val("**********");
			         $('#selectEditState').val(arrList[0].YHXX_SDZT);
			         setButtonStatus("4");
				   }
			   }else{
			       $('#txtEditUuid').val("");
				   $('#txtEditCode').val("");
				   $('#txtEditName').val("");
			       $('#selectEditRole').val("");
			       $('#selectEditZhanDian').val("");
			       $('#txtEditPass').val("");
			       $('#txtEditConfirm').val("");
			       $('#selectEditState').val("0");
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
		       $('#txtEditUuid').val(arrList.YHXX_UUID);
		       $('#txtEditCode').val(arrList.YHXX_YHID);
		       $('#txtEditName').val(arrList.YHXX_YHMC);

		       $('#selectEditRole').val(arrList.YHXX_JSID);
		       $('#selectEditZhanDian').val(arrList.YHXX_ZDID);
		       $('#txtEditPass').val("**********");
		       $('#txtEditConfirm').val("**********");
		       $('#selectEditState').val(arrList.YHXX_SDZT);

		       setButtonStatus("4");
		    });


		   $('#btnSearch').on('click', function(){
			   loadGridByBean();
		   });

		   $('#btnRecovery').on('click', function() {
			   var arrList = mmg.selectedRows();
			   if (arrList.length <= 0) {
				   layer.alert('请选择要恢复的用户！', 0, '友情提示');
				   return;
			   }
			   if (arrList.length > 0) {
				   for(var i = 0; i < arrList.length; i++) {
						if (arrList[i].YHXX_SCBZ == 0) {
						    layer.alert('提示：所选用户中，存在未删除的用户，无需恢复！', 0, '友情提示');
							return;
						}
					}
			   }
			   layer.confirm('是否恢复选中的用户？', function() {
				   if (recoveryUser() == true) {
					   loadGridByBean();
					   mmg.deselect('all');
				   }
			   });
		   });


		   $('#btnDel').on('click', function(){
			   var arrList = mmg.selectedRows();
			   if(arrList.length<=0){
				   layer.alert('请选择要删除的数据行！', 0, '友情提示');
		 	       return;
			   }
			     if (arrList.length > 0) {
				   for(var i = 0; i < arrList.length; i++) {
						if (arrList[i].YHXX_SCBZ == 1) {
						    layer.alert('提示：该用户已经删除，无法再次删除！', 0, '友情提示');
							return;
						}
					}
			   }
			   layer.confirm('是否删除用户信息？', function() {
				   if(deleteUserCode()==true){
					   //重新查询数据
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
		    		layer.confirm('是否增加用户信息？', function() {
			    		if(insertUserCode()==true){
			  	            //重新查询数据
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
		    		layer.confirm('是否修改用户信息？', function() {
			    		if(updateUserCode()==true){
				             //重新查询数据
				             loadGridByBean();
				             setButtonStatus("2");
				             mmg.deselect('all');
				        }
			        });
		    	}
		    	//setButtonStatus("1");
		    });
		    loadGridByBean();

		    loadSearchSelect($("#selectRole"),"TYPE_YHJS","角色");
		    loadSearchSelect($("#selectZhanDian"),"TYPE_ZDMC","站点");

		    loadEditSelect($("#selectEditRole"),"TYPE_YHJS","角色");
		    loadEditSelect($("#selectEditZhanDian"),"TYPE_ZDMC","站点");


		});

		var optionFlag = "";

		function btn_Add(){
			setButtonStatus("31");
			optionFlag = "Add";
		}
		function btn_Upd(){
		   setButtonStatus("32");
		   optionFlag = "Upd";
		}

		function makeBeanIn(strUuid,strUser,strName,strZDID,strRole
				,strPass,strState){
		    this.YHXX_UUID = strUuid;
		    this.YHXX_YHID = strUser;
		    this.YHXX_YHMC = strName;
		    this.YHXX_ZDID = strZDID;
		    this.YHXX_JSID = strRole;
		    this.YHXX_YHMM = strPass;
		    this.YHXX_SDZT = strState;
		}

		function loadGridByBean(){
			var beanIn = new makeBeanIn(
					'',
					$('#txtSelectCode').val(),
					$('#txtSelectName').val(),
					$('#selectZhanDian').val(),
					$('#selectRole').val(),
					'',
					$('#selectState').val()
			);
			//重新查询数据
			mmg.load({
		    	beanLoad  :  JSON.stringify(beanIn)
			});
		}

		function recoveryUser() {
			var blnRet = false;
			var beanIn = new makeBeanIn(
					$('#txtEditUuid').val(),
					"","","","","",""
			);

			$.ajax({
				async     : false,
				type      : "post",
				dataType  : "json",
				url: "<%=basePath%>/Servlet9010110",
				data: {
					CMD    : "<%=Servlet9010110.CMD_RECOVERY%>",
					BeanIn : JSON.stringify(beanIn)
				},
				complete : function(response) {},
				success : function(response) {
					var strResult = response[0];
					if (strResult == "SUCCESS") {
						layer.msg('恭喜：恢复用户信息成功！', 1, 9);
						blnRet = true;
					} else if (strResult == "FAILURE") {
						layer.msg('对不起：恢复用户信息失败！', 1, 8);
			            blnRet = false;
					} else if (strResult == "EXCEPTION") {
						layer.msg('提示：恢复用户信息出错！', 1, 0);
						blnRet = false;
					}
				}
			});
			return blnRet;
		}

		function insertUserCode(){
			var blnRet = false;
			var beanIn = new makeBeanIn(
					'',
					$('#txtEditCode').val(),
					$('#txtEditName').val(),
					$('#selectEditZhanDian').val(),
					$('#selectEditRole').val(),
					$('#txtEditPass').val(),
					$('#selectEditState').val()
			);
			$.ajax({
				async     : false,
				type      : "post",
				dataType  : "json",
				url: "<%=basePath%>/Servlet9010110",
				data: {
					CMD    : "<%=Servlet9010110.CMD_INSERT%>",
				    BeanIn : JSON.stringify(beanIn)
				},
				complete :function(response){},
				success: function(response){
					var strResult = response[0];
					if (strResult=="SUCCESS") {
						layer.msg('恭喜：新增用户信息成功！', 1, 9);
						blnRet = true;
					} else if (strResult == "FAILURE") {
						layer.msg('对不起：新增用户信息失败！', 1, 8);
						blnRet = true;
					} else {
						layer.msg('提示：新增用户信息出错！', 1, 0);
						blnRet = false;
					}
				}
			});
			return blnRet;
		}
		function updateUserCode(){
			var blnRet = false;
			var beanIn = new makeBeanIn(
					$('#txtEditUuid').val(),
					$('#txtEditCode').val(),
					$('#txtEditName').val(),
					$('#selectEditZhanDian').val(),
					$('#selectEditRole').val(),
					$('#txtEditPass').val(),
					$('#selectEditState').val()
			);

		    $.ajax({
		      async     : false,
		      type      : "post",
		      dataType  : "json",
		      url: "<%=basePath%>/Servlet9010110",
		      data: {
		         CMD    : "<%=Servlet9010110.CMD_UPDATE%>",
		         BeanIn : JSON.stringify(beanIn)
		      },
		      complete :function(response){},
		      success: function(response){
		          var strResault = response[0];
		          if (strResault=="SUCCESS") {
		        	  layer.msg('恭喜：修改用户信息成功！', 1, 9);
		             blnRet = true;
		          } else if (strResault=="FAILURE") {
		             layer.msg('对不起：修改用户信息失败！', 1, 8);
		             blnRet = false;
		          } else {
		        	 layer.msg('提示：修改用户信息出错！', 1, 0);
		             blnRet = false;
		          }
		      }
		   });
		   return blnRet;
		}

		function deleteUserCode(){
			var blnRet = false;
			var beanIn = new makeBeanIn(
					$('#txtEditUuid').val(),
					"","","","","",""
			);

			$.ajax({
				async     : false,
				type      : "post",
				dataType  : "json",
				url: "<%=basePath%>/Servlet9010110",
				data: {
					CMD    : "<%=Servlet9010110.CMD_DELETE%>",
					BeanIn : JSON.stringify(beanIn)
				},
				complete : function(response) {},
				success : function(response) {
					var strResult = response[0];
					if (strResult == "SUCCESS") {
						layer.msg('恭喜：删除用户信息成功！', 1, 9);
						blnRet = true;
					} else if (strResult == "FAILURE") {
						layer.msg('对不起：删除用户信息失败！', 1, 8);
			            blnRet = false;
					} else if (strResult == "EXCEPTION") {
						layer.msg('提示：删除用户信息出错！', 1, 0);
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
				$('#btnRecovery').attr("disabled", "disabled");
				$('#btnSave').attr("disabled", "disabled");
				$('#btnCancel').attr("disabled", "disabled");
			} else if (strFlag == "2") {//查询后/返回
				$('#btnAdd').removeAttr("disabled");
				$('#btnUpd').attr("disabled", "disabled");
				$('#btnDel').attr("disabled", "disabled");
				$('#btnRecovery').attr("disabled", "disabled");
				$('#btnSave').attr("disabled", "disabled");
				$('#btnCancel').attr("disabled", "disabled");
				$('#txtEditCode').attr("disabled","disabled");
				$('#txtEditName').attr("disabled","disabled");

				$('#selectEditRole').attr("disabled","disabled");
				$('#selectEditZhanDian').attr("disabled","disabled");
				$('#txtEditPass').attr("disabled","disabled");
				$('#txtEditConfirm').attr("disabled","disabled");
				$('#selectEditState').attr("disabled","disabled");

				$('#txtEditUuid').val("");
				$('#txtEditCode').val("");
				$('#txtEditName').val("");
				$('#selectEditRole').val("000");
				$('#selectEditZhanDian').val("000");
				$('#txtEditPass').val("");
				$('#txtEditConfirm').val("");
				$('#selectEditState').val("000");
			} else if (strFlag.substring(0, 1) == "3") {
				//新增/修改
				$('#btnAdd').attr("disabled","disabled");
				$('#btnUpd').attr("disabled", "disabled");
				$('#btnDel').attr("disabled", "disabled");
				$('#btnRecovery').attr("disabled", "disabled");
				$('#btnSave').removeAttr("disabled");
				$('#btnCancel').removeAttr("disabled");
				if (strFlag == "31") {//新增
					$('#txtEditCode').removeAttr("disabled");
					$('#txtEditName').removeAttr("disabled");
					$('#selectEditRole').removeAttr("disabled");
					$('#selectEditZhanDian').removeAttr("disabled");
					$('#txtEditPass').removeAttr("disabled");
					$('#txtEditConfirm').removeAttr("disabled");
					$('#selectEditState').removeAttr("disabled");
					$('#txtEditUuid').val("");
					$('#txtEditCode').val("");
					$('#txtEditName').val("");
					$('#selectEditRole').val("000");
					$('#selectEditZhanDian').val("000");
					$('#txtEditPass').val("");
					$('#txtEditConfirm').val("");
					$('#selectEditState').val("");
					$('#txtEditCode').focus();
				} else if (strFlag == "32") {//修改
					$('#txtEditCode').attr("disabled", "disabled");
					$('#txtEditName').removeAttr("disabled");
					$('#selectEditRole').removeAttr("disabled");
					$('#selectEditZhanDian').removeAttr("disabled");
					$('#txtEditPass').removeAttr("disabled");
					$('#txtEditConfirm').removeAttr("disabled");
					$('#selectEditState').removeAttr("disabled");
					$('#txtEditName').focus();
				} else if (strFlag == "32") {//删除
					$('#txtEditCode').attr("disabled","disabled");
					$('#txtEditName').attr("disabled","disabled");
					$('#selectEditRole').attr("disabled","disabled");
					$('#selectEditZhanDian').attr("disabled","disabled");
					$('#txtEditPass').attr("disabled","disabled");
					$('#txtEditConfirm').attr("disabled","disabled");
					$('#selectEditState').attr("disabled","disabled");
				}
			} else if (strFlag == "4") {//选中行
				$('#btnAdd').removeAttr("disabled");
				$('#btnUpd').removeAttr("disabled");
				$('#btnDel').removeAttr("disabled");
				$('#btnRecovery').removeAttr("disabled");
				$('#btnSave').attr("disabled", "disabled");
				$('#btnCancel').attr("disabled", "disabled");
				$('#txtEditCode').attr("disabled","disabled");
				$('#txtEditName').attr("disabled","disabled");
				$('#selectEditRole').attr("disabled","disabled");
				$('#selectEditZhanDian').attr("disabled","disabled");
				$('#txtEditPass').attr("disabled","disabled");
				$('#txtEditConfirm').attr("disabled","disabled");
				$('#selectEditState').attr("disabled","disabled");
			}
		}
		//验证编辑输入数据
		function funEditCheck() {
			if (optionFlag == "Add" || optionFlag == "Upd") {
				if ($('#txtEditCode').val() == "") {
					layer.alert('请输入用户ID！', 0, '友情提示', function() {
						$('#txtEditCode').focus();
						layer.close(layer.index);
					});
					return false;
				}
				if ($('#txtEditName').val() == "") {
					layer.alert('请输入用户名称！', 0, '友情提示', function() {
						$('#txtEditName').focus();
						layer.close(layer.index);
					});
					return false;
				}
				if ($('#selectEditRole').val() == "000") {
					layer.alert('请选择用户角色！', 0, '友情提示', function() {
						$('#selectEditRole').focus();
						layer.close(layer.index);
					});
					return false;
				}
				if ($('#txtEditPass').val() == "") {
					layer.alert('请输入用户密码！', 0, '友情提示', function() {
						$('#txtEditPass').focus();
						layer.close(layer.index);
					});
					return false;
				}
				if($('#txtEditPass').val() != $('#txtEditConfirm').val()){
					layer.alert('输入的两次密码不一致，请检查！', 0, '友情提示', function() {
						$('#txtEditPass').focus();
						layer.close(layer.index);
					});
					return false;
				}

				if (optionFlag == "Add"){//新增：判断是否用户代码已存在
					if(checkUserExist()==true){
						layer.alert('用户代码已存在，不能新增！', 0, '友情提示');
						return false;
					}
				}else if(optionFlag == "Upd"){//修改：判断是否用户代码已存在
					if(checkUserExist()==false){
						layer.alert('用户代码不存在，不能修改！', 0, '友情提示');
						return false;
					}
				}

			}
			return true;
		}

		function checkUserExist(){
			var blnRet = false;
			var beanIn = new makeBeanIn(
					"",$('#txtEditCode').val(),
					"","","","",""
			);

			$.ajax({
				async     : false,
				type      : "post",
				dataType  : "json",
				url: "<%=basePath%>/Servlet9010110",
				data: {
					CMD    : "<%=Servlet9010110.CMD_CHK_EXIST%>",
					BeanIn : JSON.stringify(beanIn)
				},
				complete : function(response) {
				},
				success : function(response) {
					var strResault = response[0];
					if (strResault == "DATA_EXIST") {
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
	<body>
		<fieldset id = "selectRegion">
			<legend>查询条件</legend>
			<table>
				<tr>
					<th style="width:80px">用户ID</th>
					<td><input id="txtSelectCode" name="用户ID" maxlength="5" onkeypress="filterKeyForNumber(this,'CNUMONLY');"/></td>
					<th style="width:80px">用户名称</th>
					<td><input id="txtSelectName" name="用户名称" maxlength="4" /></td>
					<th style="width:80px">用户角色</th>
					<td>
						<select id="selectRole" style="width: 100px">
							<option value="000" selected>请选择</option>
						</select>
					</td>
					<th style="width:80px">所属站点</th>
					<td>
						<select id="selectZhanDian">
							<option value="000" selected>请选择</option>
						</select>
					</td>
					<th style="width:80px">锁定状态</th>
					<td>
						<select id="selectState" style="width: 100px">
							<option value="000" selected>所有</option>
							<option value="0">正常</option>
							<option value="1">冻结</option>
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
			<table id="detailCanvas" class="eTable6">
				<tr>
					<th style="width:100px">用户ID</th>
					<td><input type="hidden"  id="txtEditUuid"  /><input id="txtEditCode" name="用户ID" maxlength="10"/></td>
					<th style="width:100px">用户名称</th>
					<td><input id="txtEditName" name="用户名称" maxlength="4" /></td>
					<th style="width:100px">角色</th>
					<td>
						<select id="selectEditRole" style="width: 100px">
							<option value="所有" selected>请选择</option>
						</select>
					</td>
					<th style="width:100px">所属站点</th>
					<td>
						<select id="selectEditZhanDian">
							<option value="所有" selected>请选择</option>
						</select>
					</td>
				</tr>
				<tr>
				<th style="width:100px">用户密码</th>
					<td><input id="txtEditPass" name="用户密码" maxlength="20" type="password"/></td>
					<th style="width:100px">确认密码</th>
					<td><input id="txtEditConfirm" name="确认密码" maxlength="20" type="password"/></td>
					<th style="width:100px">锁定状态</th>
					<td>
						<select id="selectEditState" style="width: 100px">
							<option value="0" selected>正常</option>
							<option value="1">冻结</option>
						</select>
					</td>
				</tr>
			</table>
			<div id="buttonCanvas" class="gToolbar gTbrCenter ">
				<input type="button" value="新增" id="btnAdd" name="btnAdd" onclick="btn_Add()" />
				<input type="button" value="修改" id="btnUpd" name="btnUpd" onclick="btn_Upd()" />
				<input type="button" value="删除" id="btnDel" name="btnDel" />
				<input type="button" value="保存" id="btnSave" name="btnSave" />
				<input type="button" value="取消" id="btnCancel" name="btnCancel" />
				<input type="button" value="恢复" id="btnRecovery" name="btnRecovery" />
			</div>
		</fieldset>
	</body>
</html>