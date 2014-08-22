cordova.define('cordova/plugin_list', function(require, exports, module) {
module.exports = [
    {
        "file": "plugins/com.cabot.plugins.googleplus/www/plugins/googleConnectPlugin.js",
        "id": "com.cabot.plugins.googleplus.GoogleConnectPlugin",
        "clobbers": [
            "navigator.googleConnectPlugin"
        ]
    }
];
module.exports.metadata = 
// TOP OF METADATA
{
    "com.cabot.plugins.googleplus": "0.0.1"
}
// BOTTOM OF METADATA
});