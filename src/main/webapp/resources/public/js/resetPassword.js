angular.module(
		'resetNewPassword',['common', 'spring-security-csrf-token-interceptor','editableTableWidgets' ])
		.controller('resetPassCntrl',['$scope','$http','$rootScope',function($scope, $http, $rootScope) {
			       $rootScope.successMsg='';
			       $rootScope.error = '';
			      /* $scope.showMessage = false;
			       $scope.m = {
		                errorMessages: [],
		                infoMessages: []
		                 };*/
			       	
					$scope.resetPassword = function() {
						console.log('Creating user with first name '+ $scope.m.password + ' and last name '+ $scope.m.confirmPass);
						if($scope.m.password  != $scope.m.confirmPass){
							$scope.userMsgAlert("Password not match", '');
							 return;
						}
						if($scope.m.loginTermsAndConditon == '' || $scope.m.loginTermsAndConditon == false || $scope.m.loginTermsAndConditon == undefined){
							$scope.userMsgAlert("Please accept term and condition to login with Payment Vista", '');
							 return;
						}
						$scope.m.submitted = true;

						if ($scope.form.$invalid) {
							return;
						}
						var absUrl = window.location.href;
						var array = absUrl.split('=');
						var postData = {
						    password : $scope.m.password,
						    confirmPass : $scope.m.confirmPass,
						    email : array[1],
						    loginTermsAndConditon : $scope.m.loginTermsAndConditon
						};
						console.log(postData);
						$http({
							method : 'POST',
							url : '/resetpassword',
							data : postData,
							param : absUrl
						}).success(function(data, status, headers,config) {
							$scope.login(postData.email, postData.password);
							//$scope.showMessage = true;
							$scope.reset();
							// showInfoMessage("You have successfully create the password");
							
			                  })
					}
					
					
					$scope.reset = function() {
						$scope.masterReset = {};
						$scope.m = angular.copy($scope.masterReset);
					};
					
					$scope.userMsgAlert = function(errorMsg, successMsg){
						$rootScope.successMsg='';
						$rootScope.error = '';
						$rootScope.successMsg=successMsg;
						$rootScope.error = errorMsg;
						 $('#msgTimeOut').show();
							setTimeout(function() {
								$('#msgTimeOut').hide();
							}, 10000);
				    }

				} ]);