var app = angular.module('forgotPwd', [ 'angularFileUpload' ]);

app.controller('ForgotPwdCtrl',['$scope','$http', function($scope, $http) {
	
	$scope.forgotPassword = function() {
		console.log('In forgotPassword Method')
		$scope.vm.submitted = true;
		if ($scope.form.$invalid) {
			return;
		}
		console.log('User Name = ' + $scope.vm.username);
		
		var parameters = {};
		parameters.userName = $scope.vm.username;
		
		$http({
			 method : 'POST',
			 url : '/register/forgotPasswrod',
			 params : parameters
			 }).success(function(data, status, headers,config) {
				 console.log('Success from Forgot Password JS method with below Data');
				 console.log(data);
			}).then(function(response) {
				if (response.status == 200) {
					if(response.data != "" || response.data != '0') {
						$scope.showMessage = true;
						showInfoMessage("The link to reset password has been sent to the given EmailId");
					}else{
						$scope.showMessage = true;
						showInfoMessage("The given EmailId is not registered with us !!!");
					}
				}else{
					$scope.showMessage = true;
					showInfoMessage(response.data);
					}
			});
		
	}
	
	// Start Show Error and Info Message
	function showInfoMessage(infoMessage) {
		clearMessages();
		$scope.vm.infoMessages.push({description: infoMessage});
    }
	
	function showErrorMessage(errorMessage) {
		clearMessages();
        $scope.vm.errorMessages.push({description: errorMessage});
	}
	function clearMessages() {
		$scope.vm.errorMessages = [];
        $scope.vm.infoMessages = [];
	}
	// End Show Error and Info Message
	
} ]);