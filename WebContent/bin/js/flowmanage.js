 
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
 * @Function_Name: flowSelect 
 * @Description: 设定下拉列表-用于选择处理部门
 * @param obj-下拉列表对象
 * @param Type-取得下拉列表类别
 * @param Name-下拉列表应用名称
 * @returns {Boolean-是否成功} 返回值说明
 * @author czl
 * @date 2014-11-04
 */
function flowSelect(obj,Type,Name){
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
		result = localhostPath;
	}
    return(result);
}
