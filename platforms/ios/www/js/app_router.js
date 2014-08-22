/*'use strict';
*/
/* App Module */

/*var asthmaApp =angular.module('AsthmaApp', ['ui.router']);
asthmaApp.config(function($stateProvider,$urlRouterProvider) {  
	$urlRouterProvider.otherwise('/views/login');
	 
    $stateProvider
        .state('registration', {
            url: '/registration',
            templateUrl: 'views/registration_screen.html',
            controller: 'registrationCtrl'
        }
        .state('login'),{
        	 url: '/login',
             templateUrl: 'views/login_screen.html',
             controller: 'loginCtrl'
        });
   
});*/
var sampleApp = angular.module('GoogleSampleApp', ['ui.router','ngResource']);

sampleApp.config(function($stateProvider, $urlRouterProvider) {
    
    $urlRouterProvider.otherwise('/login');
    
    $stateProvider
        
        // HOME STATES AND NESTED VIEWS ========================================
        .state('login', {
            url: '/login',
            templateUrl: 'views/login_screen.html',
            controller: 'loginCtrl'
        })
        
    });
       