cordova.define("com.cabot.plugins.googleplus.GoogleConnectPlugin", function(require, exports, module) { var googleConnectPlugin = {
    googleLogin: function( successCallback, errorCallback) {
        cordova.exec(
            successCallback, // success callback function
            errorCallback, // error callback function
            'GoogleConnectPlugin', // mapped to our native Java class called "CalendarPlugin"
            'cordovaGooglePlusLogin', // with this action name
            []
        ); 
     }
}
module.exports = googleConnectPlugin;

});
