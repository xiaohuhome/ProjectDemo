$(function() {
	$("#rememberMe").removeAttr("checked");
	getUpCookie();
});

function userLogin(){
	JO("usernameAlert").innerHTML="";
	JO("passwordAlert").innerHTML="";
	
	var username = JV("username").trim();
	var password = JV("password").trim();

	var legit = true;
	
	if(username==''){
		JO("usernameAlert").innerHTML="账号不能为空";
		legit = false;
	}
	
	if(password==''){
		JO("passwordAlert").innerHTML="密码不能为空";
		legit = false;
	}
	
	if (!legit) {
		return;
	}
	
	disabledButton(JO("userLogin"), "登录中...");
	
	getBrowerType();
	
	var url = $WEB_ROOT_PATH + "/login/userLogin";
	var params = {
		'username':username,
		'password':password
	}
	
	$.ajax({
		url:url,
		type:'POST',
		data:params,
		dataType:'json',
		success:function(data){
			var msg = data.msg;
			if(msg == '0'){
				setUpCookie(username,password);
				$("#userLogin").attr("disabled", true);
				//允许登录
				window.location.href = $WEB_ROOT_PATH + "/user/index";
			}else if(msg == '1'){
				alert("用户不存在");
			}else if(msg == '2'){
				alert("密码错误");
			}
		},
		error:function(msg){
			alert("LOGIN ERROR");
		}
	});
}


/*
 * 
 * cookie模块
 */

function getBrowerType(){
	var browser = {
			versions : function() {
				var u = navigator.userAgent, app = navigator.appVersion;
				return { //移动终端浏览器版本信息 
					trident : u.indexOf('Trident') > -1, //IE内核 
					presto : u.indexOf('Presto') > -1, //opera内核 
					webKit : u.indexOf('AppleWebKit') > -1, //苹果、谷歌内核 
					gecko : u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1, //火狐内核 
				};
			}(),
			language : (navigator.browserLanguage || navigator.language)
					.toLowerCase()
		}
		if (browser.versions.trident) {
			changePassTypeToText();
		} else {
			JO("password").value = "";
			JO("password").type = 'text';
		}
}

/*
禁止浏览器保存密码的简单原理
测试了多个浏览器，各个浏览器保存密码的原理都不太一样
但最后发现，只要代码里的type是text，并且最后提交时type是text，
无论过程中间是是不是password类型的，浏览器都不会提示保存密码
所以总结起来就是在代码里写成text，然后初始化时改为password，
然后在提交时再改为text就行
!!!!!但是，ie下不允许改变input的type类型
!!!!!但是，chrome下，<input type="password" autocomplete="new-password" style="height:0px;margin-top:0px;border:none;" tabIndex="-1"/>
得在页面上加上这么一个空间，让浏览器找不到到底哪个才是真正的密码框
所以得用两个input，一个为password，另外一个为text，两个input轮番显示出来



*/
function changePassTypeToPass(pwd){
$("#password").remove();

var passObj = createElement("input");
passObj.type = 'password';
$(passObj).attr("placeholder","请输入密码");
$(passObj).attr("id","password");
$(passObj).attr("name","password");
$(passObj).attr("onkeydown","key_down(event)");
$(passObj).val(pwd);
$(passObj).insertBefore("#passwordAlert");
}

function changePassTypeToText(){
	$("#password").remove();
	
	var passObj = createElement("input");
	passObj.type = 'text';
	$(passObj).attr("id","password");
	$(passObj).attr("name","password");
	$(passObj).attr("autocomplete","off");
	$(passObj).val("");
	$(passObj).insertBefore("#passwordAlert");
}

function setUpCookie(u,p) {
	if(!JO("rememberMe").checked){
		//删除cookie
		clearCookie("loginName");
		clearCookie("loginPwd");
	}
	else{
		var exp =365;
		setCookie2("loginName",u,exp);
		setCookie2("loginPwd",p,exp);
	}
}

//清除cookie    
function clearCookie(name) {    
    setCookie2(name, "", -1);    
}  

//设置cookie  
function setCookie2(cname, cvalue, exdays) {  
    var d = new Date();  
    d.setTime(d.getTime() + (exdays*24*60*60*1000));  
    var expires = "expires="+d.toUTCString();  
    document.cookie = cname + "=" + cvalue + "; " + expires;  
}  

function key_down(ent) {
	var evt = ent ? ent : (window.event ? window.event : null);
	// 回车键提交
	if (evt.keyCode==13) {
		$("#userLogin").click();
	}
}

//获取cookie  
function getCookie2(cname) {  
	
    var name = cname + "=";  
    var ca = document.cookie.split(';');  
    for(var i=0; i<ca.length; i++) {  
        var c = ca[i];  
        while (c.charAt(0)==' ') c = c.substring(1);  
        if (c.indexOf(name) != -1) 
        	return c.substring(name.length, c.length);  
    }  
    return "";  
}  

function getUpCookie() {
	var name = getCookie2("loginName");
	var pwd = getCookie2("loginPwd");
	if(name && pwd){
		$("#rememberMe").attr("checked","checked");
		name = $.trim(name);
		
		if(name != "" && pwd !=""){
		  JO("rememberMe").checked=true;
		  $("#username").val(name);
		}
	}
	var pwd2 = "";
	if(name != "" && pwd !=""){
		pwd2 = pwd;
	}
	changePassTypeToPass(pwd2);
}