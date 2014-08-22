var FACEBOOK=1;
var GOOGLE_PLUS=2;
var loginStatus;
function isAndroid(){
    return navigator.userAgent.indexOf("Android") > 0;
}

function isiOS(){
    return ( navigator.userAgent.indexOf("iPhone") > 0 || navigator.userAgent.indexOf("iPad") > 0 || navigator.userAgent.indexOf("iPod") > 0);
}

function getDeviceID(){
	if(isiOS() || isAndroid()){
		return device.uuid;
	}
	else
		return 'cvhfvhj';
}
function showAlert(Title,message,button){
	
	if(isiOS() || isAndroid()){
		navigator.notification.alert(message,null,Title,button);
	}
	else
	{
		alert(message);
	}
}