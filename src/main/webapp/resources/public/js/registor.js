var app = angular.module('userRegApp', []);

//angular.module('newUserRegistor',[ 'spring-security-csrf-token-interceptor'])
		app.controller('NewRegistorCtrl',['$scope','$http','$rootScope', function($scope, $http, $rootScope) {

							$scope.showMessage = false;
							   $scope.vm = {
						                errorMessages: [],
						                infoMessages: []
						            };
							$scope.successMsg='';
							$scope.error = '';
							$rootScope.successMsg='';
							$rootScope.error = '';
							// Start code to fetch user list from DB   
							   $scope.vm = {
									userList : [],
							   }
							
								console.log('Fetch user list');
								$http.get('/register/userList' ,{
								
								}).success(function(data, status) {
									$scope.vm.userList = data;
								});
								// End code to fetch user list
							   
							$scope.userRole = '';
							$scope.registerUser = function() {
								//var deferred = $q.defer();
								console.log('Creating user with first name '+ $scope.vm.firstName+ ' and last name '+ $scope.vm.lastName);
								$scope.vm.submitted = true;
								if ($scope.form.$invalid) {
									return;
								}
								if(isNaN($scope.vm.phoneNo)){
									$rootScope.userMsgAlert("Only numeric value allow in phone no field", '');
									return;
								}
								var postData = {
									firstName : $scope.vm.firstName,
									lastName : $scope.vm.lastName,
									contactNo : $scope.vm.phoneNo,
									email : $scope.vm.emailId,
									organization : $scope.vm.organizationName
								};

								$http({
									method : 'POST',
									url : '/register/firstRegistration',
									data : postData,
									headers: {
				                        "Content-Type": "application/json",
				                        "Accept": "text/plain, application/json"
				                    }
								}).then(function(response) {
									if (response.status == 200) {
										$scope.reset();
										window.location.href = "thankyou.html";
									}else{
										$rootScope.userMsgAlert(response.data, '');
										//showInfoMessage(response.data);
										}
								});

								$scope.reset = function() {
									$scope.masterReset = {};
									$scope.vm = angular.copy($scope.masterReset);
								};
							}
							
							
							$scope.getValueFromRole = function() {
								$scope.successMsg='';
								$scope.error = '';
								$http.get('/register/roleValue', {

								}).success(function(data, status, headers, config) {
										$scope.userRole = data;

									})
									
								console.log('Going to fetch organization detail')
								$http.get('/register/orgDetail',{
									
								}).success(function(data, status, headers, config) {
									 console.log('orgDetail found orgDetail : ' + data);
									 $scope.usr.orgId = data.orgId;
									 $scope.usr.orgName = data.orgName;
								})

							}
							
							$scope.userOb = '';
							$scope.dsblUser = function(usrOb) {
								$scope.userOb  = usrOb;
								$scope.successMsg='';
								$scope.error = '';
							} 
							
							$scope.disableUser = function(usrOb){
								var postData = {
										userId : usrOb.userId
									};
									 $http({
									 method : 'PUT',
									 url : '/register/disableUser',
									 data : postData
									 }).then(function(response) {
										 	$('#myModalDelete .close').click();
											if (response.status == 200) {
												//$scope.showMessage = true;
												 //showInfoMessage("User Deactivated successfully .");
												 $scope.successMsg="User Deactivated successfully.";
											}else{
												//$scope.showMessage = true;
												//showInfoMessage(response.data);
												$scope.error = "Some problem occured please try again.";
												}
											
											setTimeout(function(){
										        location.reload();
											}, 1000);
										}); 
							}

							$scope.userDetail = '';
							$scope.auditLogDetail = '';
							$scope.getUserDetail = function(userInfo) {
								$scope.successMsg='';
								$scope.error = '';
								
								$scope.userDetail  = userInfo;
								
								 var parameters = {};
								 parameters.refID = userInfo.userId;
								 parameters.refType = 'userDetail';
								$http.get('/detailRegistration/viewAuditData', {params: parameters

								})
								.success(function(data, status, headers,config) {
									$scope.auditLogDetail = data;
								})
							}
							
							$scope.getUserDetailWithRole = function(userInfo) {
								$scope.successMsg='';
								$scope.error = '';
								
								$scope.userDetail  = userInfo;
								console.log('in editttt     '+$scope.userDetail.roleList);
								$scope.getValueFromRole();
							   }				
							
							$scope.updateUserDetail = function() {
															
								$scope.vm.submitted = true;
								if ($scope.form.$invalid) {
									return;
								}

								var postData = {
									userId : $scope.userDetail.userId,
									firstName : $scope.userDetail.firstName,
									lastName : $scope.userDetail.lastName,
									contactNo : $scope.userDetail.contactNo,
									roleList : $scope.userDetail.selectedValues,
									email : $scope.userDetail.email,
									organization : $scope.userDetail.organization,
									employeeNo : $scope.userDetail.employeeNo,
									addressLine1 : $scope.userDetail.addressLine1
								};

								 $http({
								 method : 'PUT',
								 url : '/register/updtUserDetail',
								 data : postData
								 }).then(function(response) {
									 $('#myModalEdit .close').click();
										if (response.status == 200) {
											//$scope.showMessage = true;
											// showInfoMessage("User details updated successfully.");
											 $scope.successMsg="User details updated successfully.";
										}else{
											//$scope.showMessage = true;
											//showInfoMessage(response.data);
											$scope.error = "Some problem occured please try again";
											}
										
										/*setTimeout(function(){
									        location.reload();
										}, 2000);*/
									});
							}
							
							
							$scope.usr = {};
							$scope.addNewUser = function() {
								
								$scope.usr.submitted = true;

								if ($scope.form.$invalid) {
									return;
								}

								var postData = {
									firstName : $scope.usr.firstName,
									lastName : $scope.usr.lastName,
									contactNo : $scope.usr.phoneNo,
									roleList : $scope.usr.selectedValues,
									email : $scope.usr.email,
									organization : $scope.usr.orgName,
									employeeNo : $scope.usr.employeeNo,
									addressLine1 : $scope.usr.address,
									orgID : $scope.usr.orgId
								};

								 $http({
								 method : 'POST',
								 url : '/register/addNewUser',
								 data : postData,
								 headers: {
				                        "Content-Type": "application/json",
				                        "Accept": "text/plain, application/json"
				                    }
								 }).then(function(response) {
									 
										if (response.status == 200) {
											//$scope.showMessage = true;
											//$scope.resetUser();
											 //showInfoMessage("You have successfully register. The link to create password has been" +
											 	//	" sent to the given EmailID");
											$('#myModal .close').click();
											$scope.successMsg="You have successfully register. The link to create password has been sent to the given EmailId";
										}else{
											//$scope.showMessage = true;
											//showInfoMessage(response.data);
											$scope.error = "Some problem occured please try again";
											}
										/*setTimeout(function(){
									        location.reload();
										}, 100);*/
									});
								$scope.resetUser = function() {
									$scope.masterReset = {};
									$scope.usr = angular.copy($scope.masterReset);
								};
							}		
							
							
							$scope.searchUsers = function(user) {
								console.log("In searchUser Js method");
								var parameters = {};
								parameters.firstName = user.firstName;
								parameters.showInactive = user.showInactive;
							 $http.get('/register/searchUser', {
								    params: parameters
								}).success(function(data, status) {
											$scope.vm.userList = data;
										})
							}
							
							$rootScope.userMsgAlert = function(errorMsg, successMsg){
								$rootScope.successMsg='';
								$rootScope.error = '';
								$rootScope.successMsg=successMsg;
								$rootScope.error = errorMsg;
								 $('#msgTimeOut').show();
									setTimeout(function() {
										$('#msgTimeOut').hide();
									}, 10000);
						    }
							
							
						} ])						
					 .controller('bankDetailCtrl', ['$scope','$http','$rootScope', function($scope, $http, $rootScope) {
							$scope.usr = {};
							$scope.saveBankDetail = function() {
							console.log('Here we go for Creating user with vendor first name ');
								$scope.usr.submitted = true;
								if ($scope.form.$invalid) {
									return;
								}
								console.log('Here we go for creating user with vendor first name '+ $scope.usr.bankName);
								var bankData = {
									bankName : $scope.usr.bankName,
									accHolderName : $scope.usr.holderName,
									accountNumber : $scope.usr.accNo,
									branchAddress : $scope.usr.branchAddress,
									micrNo : $scope.usr.micrNo,
									ibanNo : $scope.usr.ibanNo,
									amountLimit : $scope.usr.amountLimit,
									availableForAP : $scope.usr.availableApValue,
									defaultForAP : $scope.usr.defaultApValue,
									availableForAR : $scope.usr.availableArValue
								};
								 $http({
								 method : 'POST',
								 url : '/detailRegistration/bankDetail',
								 data : bankData
								 })
								console.log('Creating user with  last name ');
							}

						} ])
						.controller('userDetailCtrl', ['$scope','$http', '$rootScope', function($scope, $http, $rootScope) {
							$scope.usr = {};
							$scope.getAllUsersDetail = function() {
								console.log('Fetch User list');
								$http.get('/register/userList', {

								})
								.success(function(data, status, headers,config) {
									$scope.userList = data;
								})
							}

						} ]);