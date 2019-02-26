
var CDOT = "CDOT";				//There is a decimal point.
var CNUMONLY = "CNUMONLY";		//There is not a decimal point.


/**
 * @Function_Name: getBrowseVersion
 * @Description: 取得浏览器版本
 * @returns {String} 返回浏览器版本
 * @author misty
 * @date 2014年4月8日 下午10:07:17
 */
function getBrowseVersion() {
	var Sys = {};
	var ua = navigator.userAgent.toLowerCase();
	var s;
	(s = ua.match(/msie ([\d.]+)/)) ? Sys.ie = s[1] : (s = ua
			.match(/firefox\/([\d.]+)/)) ? Sys.firefox = s[1] : (s = ua
			.match(/chrome\/([\d.]+)/)) ? Sys.chrome = s[1] : (s = ua
			.match(/opera.([\d.]+)/)) ? Sys.opera = s[1] : (s = ua
			.match(/version\/([\d.]+).*safari/)) ? Sys.safari = s[1] : 0;

	// 以下进行测试
	if (Sys.ie)
		return 'IE: ' + Sys.ie;
	if (Sys.firefox)
		return 'Firefox: ' + Sys.firefox;
	if (Sys.chrome)
		return 'Chrome: ' + Sys.chrome;
	if (Sys.opera)
		return 'Opera: ' + Sys.opera;
	if (Sys.safari)
		return 'Safari: ' + Sys.safari;
}

/**
 * @Function_Name: filterKeyForNumber
 * @Description: 限制Input控件输入数字，onkeypress内调用
 * @param obj--限制输入对象
 * @param strFlg
 *            CDOT:带小数点数字
 *            CNUMONLY:纯数字
 * @author misty
 * @date 2014年4月8日 下午10:09:11
 */
function filterKeyForNumber(obj, strFlg) {
	switch (strFlg) {
		case CDOT:
			if ((event.keyCode < 48 || event.keyCode > 57) && event.keyCode != 46
					|| /[^\d\.]/.test(obj.value)) {
				event.returnValue = false;
			}
			break;
		case CNUMONLY:
			if (event.keyCode < 48 || event.keyCode > 57) {
				event.returnValue = false;
			}
			break;
	}
}

/**
 * @Function_Name: filterKeybordAZaz09
 * @Description: 限制输入A~Z，a~z,0~9
 * @param obj 返回值说明
 * @author misty
 * @date 2014年4月8日 下午10:15:50
 */
function filterKeybordAZaz09(obj){
	if((event.keyCode<48 || event.keyCode>57)
		&& (event.keyCode<65 || event.keyCode>90)
		&& (event.keyCode<97 || event.keyCode>122)){
		event.returnValue=false;
	}
}

/**
 * @Function_Name: filterKeybordAZaz09
 * @Description: 限制输入大小写字母
 * @param obj 返回值说明
 * @author misty
 * @date 2014年4月8日 下午10:17:07
 */
function filterKeybordAZaz(obj){
	if((event.keyCode<65 || event.keyCode>90)
		&& (event.keyCode<97 || event.keyCode>122)){
		event.returnValue=false;
	}
}

/**
 * @Function_Name: getObjPropert
 * @Description: 取得对象的所有属性
 * @param obj
 * @returns {String} 所有属性列表
 * @author misty
 * @date 2014年4月8日 下午10:43:26
 */
function getObjPropert(obj) {
	var props = "";
	// 开始遍历
	for ( var p in obj) { // 方法
		if (typeof (obj[p]) == "function") {
			obj[p]();
		} else {// p 为属性名称，obj[p]为对应属性的值
			props += p + " = " + obj[p] + "\t\n";
		}
	} // 最后显示所有的属性
	// $('#propert').val(props);
	return props;
}

/**
 * @Function_Name: loadSearchSelect
 * @Description: 设定下拉列表-用于查询
 * @param obj-下拉列表对象
 * @param Type-取得下拉列表类别
 * @param Name-下拉列表应用名称
 * @returns {Boolean-是否成功} 返回值说明
 * @author czl
 * @date 2017-07-26
 */
function loadSearchSelect(obj,Type,Name){
	var blnRet = false;

	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: getRootPath()+"/CommonServlet",
		data: {
			CMD    : "LOAD_SELECT",
			TYPE   : Type
		},
		complete : function(response) {},
		success : function(response) {
			var strResault = response[0];
			if (strResault == "SUCCESS") {
				var roleList = response[1];
				try{
					obj.empty();//清空
					var option = $("<option>").text("所有").val("000");//增加<所有>项
					obj.append(option);
					var len = roleList.length;
					for (var i=0;i<len;i++){
						var option = $("<option>").text(roleList[i].NAME).val(roleList[i].CODE);
						obj.append(option);//增加下拉列表值
					}
					obj[0].selectedIndex = 0;//选中第一项
				}catch(e){
					alert("取得"+Name+"下拉列表出错:"+e.message);
				}
				blnRet = true;
			} else if (strResault == "ERROR") {
				alert("取得"+Name+"下拉列表出错！");
				blnRet = false;
			}
		}
	});
	return blnRet;
}
/**
 * @Function_Name: loadSearchSelectown
 * @Description: 设定下拉列表-用于查询 （第一个选项自定义）
 * @param obj-下拉列表对象
 * @param Type-取得下拉列表类别
 * @param Name-下拉列表应用名称
 * @returns {Boolean-是否成功} 返回值说明
 * @author czl
 * @date 2014年11月04日 上午9:52:02
 */
function loadSearchSelectown(obj,Type,Name){
	var blnRet = false;

	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: getRootPath()+"/CommonServlet",
		data: {
			CMD    : "LOAD_SELECT",
			TYPE   : Type
		},
		complete : function(response) {},
		success : function(response) {
			var strResault = response[0];
			if (strResault == "SUCCESS") {
				var roleList = response[1];
				try{
					var len = roleList.length;
					for (var i=0;i<len;i++){
						var option = $("<option>").text(roleList[i].NAME).val(roleList[i].CODE);
						obj.append(option);//增加下拉列表值
					}
					obj[0].selectedIndex = 0;//选中第一项
				}catch(e){
					alert("取得"+Name+"下拉列表出错:"+e.message);
				}
				blnRet = true;
			} else if (strResault == "ERROR") {
				alert("取得"+Name+"下拉列表出错！");
				blnRet = false;
			}
		}
	});
	return blnRet;
}
/**
 * @Function_Name: loadEditSelect
 * @Description: 设定下拉列表-用于编辑
 * @param obj-下拉列表对象
 * @param Type-取得下拉列表类别
 * @param Name-下拉列表应用名称
 * @returns {Boolean-是否成功} 返回值说明
 * @author ljg
 * @date 2014年7月21日 下午10:09:16
 */
function loadEditSelect(obj,Type,Name){
	var blnRet = false;
	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: getRootPath()+"/CommonServlet",
		data: {
			CMD    : "LOAD_SELECT",
			TYPE   : Type
		},
		complete : function(response) {},
		success : function(response) {
			var strResault = response[0];
			if (strResault == "SUCCESS") {
				var selectList = response[1];
				try{
					obj.empty();//清空
					var option = $("<option>").text("请选择").val("000");//增加<请选择>项
					obj.append(option);
					var len = selectList.length;
					for (var i=0;i<len;i++){
						var option = $("<option>").text(selectList[i].NAME).val(selectList[i].CODE);
						obj.append(option);//增加下拉列表值
					}
					obj[0].selectedIndex = 0;//选中第一项
				}catch(e){
					alert("取得"+Name+"下拉列表出错:"+e.message);
				}
				blnRet = true;
			} else if (strResault == "ERROR") {
				alert("取得"+Name+"下拉列表出错！");
				blnRet = false;
			}
		}
	});
	return blnRet;
}

//js获取项目根路径，如： http://localhost:8080/HJLWL
function getRootPath(){
	var result = "";
    /*//获取当前网址，如： http://localhost:8080/HJLWL/uimcardprj/share/meun.jsp
    var curWwwPath=window.document.location.href;
    //获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
    var pathName=window.document.location.pathname;
    var pos=curWwwPath.indexOf(pathName);
    //获取主机地址，如： http://localhost:8080/HJLWL
    var localhostPaht=curWwwPath.substring(0,pos);
    //获取带"/"的项目名，如：/uimcardprj
    var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);*/
	var curWwwPath=window.document.location.href;
	var pathName=window.document.location.pathname;
    var pos=curWwwPath.indexOf(pathName);
	var localhostPath=curWwwPath.substring(0,pos);
	var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
	if(projectName == "/XSJY"){
		result = localhostPath + projectName;
	}else{
		result = localhostPath + "/XSJY";
	}

//	result = localhostPath + projectName;
    return(result);
}

/* 左补0 */
function pad(num, n) {
    var len = num.toString().length;
    while(len < n) {
        num = "0" + num;
        len++;
    }
    return num;
}


/**
 * @Function_Name: insertJoinLession
 * @Description: 临时报名
 * @param obj-下拉列表对象
 * @param Type-取得下拉列表类别
 * @param Name-下拉列表应用名称
 * @returns {Boolean-是否成功} 返回值说明
 * @author ljg
 * @date 2014年7月21日 下午10:09:16
 */
function insertJoinLession(name,phone,remark){
	var blnRet = false;
	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: getRootPath()+"/CommonServlet",
		data: {
			CMD    : "JOIN_LESSION",
			NAME   : name,
			PHONE  : phone,
			REMARK : remark
		},
		complete : function(response) {},
		success : function(response) {
			var strResault = response[0];
			if (strResault == "SUCCESS") {
				alert("报名成功!")
				blnRet = true;
			} else if (strResault == "JOINED") {
				alert("已经报名！");
				blnRet = false;
			} else if (strResault == "ERROR") {
				alert("报名失败！");
				blnRet = false;
			}
		}
	});
	return blnRet;
}

/**
 * @Function_Name: getLoginUserBySession
 * @Description: 取得登录用户
 * @returns {String-用户名} 返回值说明
 * @author ljg
 * @date 2014年7月21日 下午10:09:16
 */
function getLoginUserBySession(){
	var blnRet = "";
	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: getRootPath()+"/CommonServlet",
		data: {
			CMD    : "GET_USER_SESSION"
		},
		complete : function(response) {},
		success : function(response) {
			blnRet = response[0];
		}
	});
	return blnRet;
}

/**
 * @Function_Name: getLoginUserBySession
 * @Description: 取得登录用户
 * @returns {String-用户名} 返回值说明
 * @author ljg
 * @date 2014年7月21日 下午10:09:16
 */
function getLoginUserIdBySession(){
	var blnRet = "";
	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: getRootPath()+"/CommonServlet",
		data: {
			CMD    : "GET_USERID_SESSION"
		},
		complete : function(response) {},
		success : function(response) {
			blnRet = response[0];
		}
	});
	return blnRet;
}

/**
 * @Function_Name: clearSession
 * @Description: 清除登录用户
 * @returns {String-用户名} 返回值说明
 * @author ljg
 * @date 2014年7月21日 下午10:09:16
 */
function clearSession(){
	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: getRootPath()+"/CommonServlet",
		data: {
			CMD    : "CLEAR_SESSION"
		},
		complete : function(response) {},
		success : function(response) {
		}
	});
}

/**
 * @FunctionName: loadcssfile
 * @Description: JS加载CSS文件(同名同目录)
 * @param filename
 * @return void
 * @author ljg
 * @date 2015年1月20日 上午9:45:59
 */
function loadcssfile(filename){
	var a = document.scripts,b,c = document,d = "getElementById",e = "getElementsByTagName",f=filename+"css";
	!c[d](f) && (function(){
		for(var i=0;i<a.length;i++){
			if(a[i].src.indexOf(filename + ".js")>-1){
				b = a[i].src;
				var p = b.substring(0, b.lastIndexOf("/") + 1) + filename + ".css";
				var g = document.createElement("link");
				g.type = "text/css",g.rel = "stylesheet",g.href = p,
				f && (g.id = f),c[e]("head")[0].appendChild(g);
			}
		}
	})();
}
function getUserName(){
	var username="";
	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: getRootPath()+"/CommonServlet",
		data: {
			CMD    : "USER_NAME"
		},
		complete : function(response) {},
		success : function(response) {
			var result = response[0];
			if(result=="SUCCESS"){
				username=response[1];
			}
		}
	});
	return username;
}

function getUserMessCount(){
	var messcount=0;
	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: getRootPath()+"/CommonServlet",
		data: {
			CMD    : "MESS_COUNT"
		},
		complete : function(response) {},
		success : function(response) {
			var result = response[0];
			if(result=="SUCCESS"){
				messcount=response[1];
			}
		}
	});
	return messcount;
}

//取得验证码
function createCheckCode(checkid){
	var code = new Array();
	var codeLength = 4;//验证码的长度
	var checkCode = document.getElementById(checkid);
	checkCode.value = "";

	var selectChar = new Array(1,2,3,4,5,6,7,8,9,'A','B','C','D','E','F','G','H','J','K','L','M','N','P','Q','R','S','T','U','V','W','X','Y','Z');

	for(var i=0;i<codeLength;i++) {
	   var charIndex = Math.floor(Math.random()*32);
	   code +=selectChar[charIndex];
	}
	if(code.length != codeLength){
	   createCode();
	}
	checkCode.value = code;
	return code;
}
//iframe弹出层打开
function iframeLayerOpen(url) {
	$.layer({
        type: 2,
        title: false,
        maxmin: false,
        shadeClose: false, //开启点击遮罩关闭层
        area : ['800px' , '600px'],
        offset : ['20px', ''],
        iframe: {src: url}
    });
}
//iframe弹出层关闭-刷新父页面
function iframeLayerClose() {
	var index = parent.layer.getFrameIndex(window.name);
	parent.location.reload();
	parent.layer.close(index);
}
//iframe弹出层关闭-不刷新父页面
function iframeLayerClosenoRefresh() {
	var index = parent.layer.getFrameIndex(window.name);
	parent.layer.close(index);
}
