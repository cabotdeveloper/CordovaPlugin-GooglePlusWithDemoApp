sampleApp.controller('loginCtrl',
  function($scope,$window,$location) {

	$scope.gLogin=function(){
		
		//Login with Google Api Client Plugin
		if(isAndroid() || isiOS()){
			navigator.googleConnectPlugin.googleLogin(function(userProfile){
				//alert('User Name'+JSON.stringify(userProfile));
				localStorage.setItem('UserProfile',JSON.stringify(userProfile));
			},function(error){
				alert('Error :'+error);
			});
		}
		else
			{
				console.log('no feature');
				
			}
	}
	$scope.getProfile=function(){
		if(localStorage.getItem('UserProfile')!=null)
			alert('User Details'+localStorage.getItem('UserProfile'));
		else
			alert('User Details cannot be retrieved');
	}
	
});
