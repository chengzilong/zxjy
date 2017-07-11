var selMenus;

function makeMenuSchool(menuContainer, menuList) {
	selMenus = new Array();

	for ( var i = 0, len = menuList.length; i < len; i++) {
		var menuObj = menuList[i];
		if (menuObj.MENU_FJDID == "0000000") {//一级菜单，则创建新菜单
			createParentMenuItem(menuContainer, menuObj);
		}else{
			createChildrenMenuItem(menuObj.MENU_FJDID, menuObj);	
		}
		
	}
}
function createParentMenuItem(menuContainer, menuObj) {
	var level = "1";
	var menu = document.createElement('li');
	menu.id = "M" + menuObj.MENU_CDID;
	menu.menucode = menuObj.MENU_CDID;
	menu.upcode = "0000000";
	menu.menuname = menuObj.MENU_CDMC;
	menu.innerHTML = "<span class='nav-top-item no-submenu'>" + menuObj.MENU_CDMC + "</span>";
	
	menu.onmouseover = function() {
		btn_mouse_over(menu, level);
	};
	menu.onmouseout = function() {
		btn_mouse_out(menu, level);
	};
	menu.onclick = function() {
		btn_mouse_click(menu, level);
	};

	var menuBody = document.createElement('ul');
	menuBody.id = "MB" + menuObj.MENU_CDID;
	menuBody.style.display = "none";

	menuContainer.appendChild(menu);
	menuContainer.appendChild(menuBody);
}
function createChildrenMenuItem(upCode, menuObj) {
	var level = "2";
	var menu = document.createElement('li');
	menu.id = "M" + menuObj.MENU_CDID;
	menu.menucode = menuObj.MENU_CDID;
	menu.upcode = upCode;
	menu.menuurl = menuObj.MENU_URL;
	menu.menupid = menuObj.MENU_CDID;
	menu.menuname = menuObj.MENU_CDMC;
	menu.innerHTML = "<span>" + menuObj.MENU_CDMC + "</span>";

	menu.onmouseover = function() {
		btn_mouse_over(menu, level);
	};
	menu.onmouseout = function() {
		btn_mouse_out(menu, level);
	};
	menu.onclick = function() {
		btn_mouse_click(menu, level);
	};

	var parentMenuBody = document.getElementById('MB' + upCode);
	parentMenuBody.appendChild(menu);

}
function btn_mouse_over(menu, level) {//进入
	//menu.className = "menu_" + level + " menu_" + level + "_onmouse";
}
function btn_mouse_out(menu, level) {//离开时
	/*if (selMenus[level] == menu) {
		menu.className = "menu_" + level + " menu_" + level + "_select";
	} else {
		menu.className = "menu_" + level + " menu_" + level + "_normal";
	}*/
}
function btn_mouse_click(menu, level) {
	if (selMenus[level] == undefined) {//无展开菜单(初始)
		if(level=='1'){
			menu.className = "nav-top-item no-submenu current";
			document.getElementById("MB" + menu.menucode).style.display = "block";	
		}else{
			menu.innerHTML = "<span class='current'>" + menu.menuname + "</span>";
		}
	} else if (selMenus[level] == menu) {//已展开菜单为点击父菜单
		if(level=='1'){
			menu.className = "nav-top-item no-submenu current";
			document.getElementById("MB" + menu.menucode).style.display = "block";			
		}else{
			selMenus[level].innerHTML = "<span>" + selMenus[level].menuname + "</span>";//收起样式
			menu.innerHTML = "<span class='current'>" + menu.menuname + "</span>";
		}

	} else if (selMenus[level] != menu) {//已展开菜单不是点击父菜单
		if(level=='1'){
			selMenus[level].className = "nav-top-item no-submenu";//收起样式
			document.getElementById("MB" + selMenus[level].menucode).style.display = "none";//收起样式
			menu.className = "nav-top-item no-submenu current";//设置当前点击的菜单为显示
			document.getElementById("MB" + menu.menucode).style.display = "block";	
		}else{
			selMenus[level].innerHTML = "<span>" + selMenus[level].menuname + "</span>";//收起样式
			menu.innerHTML = "<span class='current'>" + menu.menuname + "</span>";
		}
		
	}
	selMenus[level] = menu;

	if (menu.menupid != undefined) {
		var main_frame = document.getElementById("main_frame");
		var murl = menu.menuurl;
		var strPid = menu.menupid;
		main_frame.src = basePath + "/" + murl + strPid+".jsp";
	}


}

function openMenuById(menuid){
	var parentCode = (""+menuid).substring(0, 4)+"000";
	var currMenu;
	
	if (selMenus[1] == undefined) {//无展开1级菜单(初始)
		document.getElementById("MB" + parentCode).style.display = "block";
	}else{//已展开1级菜单
		if(selMenus[1].menupid!=parentCode){//展开1级菜单不是当前菜单父菜单
			//收起原父菜单
			selMenus[1].className = "nav-top-item no-submenu";//收起样式
			document.getElementById("MB" + selMenus[1].menucode).style.display = "none";//收起样式
			//展开当前父菜单
			document.getElementById("MB" + parentCode).style.display = "block";
		}
	}
	selMenus[1] = document.getElementById("M" + parentCode);
	
	currMenu = document.getElementById("M" + menuid);//取得要显示菜单
	if (selMenus[2] == undefined) {//无选中2级菜单(初始)
		currMenu.innerHTML = "<span class='current'>" + currMenu.menuname + "</span>";
	}else{//已展开2级菜单
		if(selMenus[2].menupid!=menuid){//选中2级菜单不是当前菜单
			selMenus[2].className = "nav-top-item no-submenu";//收起样式
			currMenu.className = "nav-top-item no-submenu current";//设置当前点击的菜单为显示
			currMenu.innerHTML = "<span class='current'>" + currMenu.menuname + "</span>";
		}
	}
	selMenus[2] = currMenu;

	if (currMenu.menupid != undefined) {
		var main_frame = document.getElementById("main_frame");
		var murl = currMenu.menuurl;
		var strPid = currMenu.menupid;
		main_frame.src = basePath + "/" + murl + strPid+".jsp";
	}
}

