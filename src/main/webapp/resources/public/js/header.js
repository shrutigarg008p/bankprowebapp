
app.controller('UserRoleCntrl',['$scope','$http','$rootScope',function($scope, $http, $rootScope) { 

						   $scope.vm = {
								userRoleList : [],
							}
							$rootScope.phonNoForOTP = '';
							$scope.userRolePermissions = {};
							$rootScope.successMsg='';
							$rootScope.error = '';
							
							$scope.userPermissionList = {
								DASHBOARD : 'DASHBOARD',
								USER : 'USER',
								BENEFICIARY : 'VENDOR LIST, APPROVE, EMPLOYEE',
								INVOICES : 'VIEW, APPROVE INVOICES',
								PAYMENT : 'PAYMENT CREATE,PAYMENT APPROVE',
								SETTINGS : 'BANK DETAIL,SET APPROVERS,ROLE ACCESS,PAYMENT TERMS',
							};

							angular.forEach(
											$scope.userPermissionList,
											function(value, key) {
												// console.log(key + ': ' +
												// value);
												$scope.userRolePermissions[value.roleName] = value.roleDesciption;
											});

							// console.log('In header.js trying to fetch
							// userRoleList');
							$http.get('/role-access/userRoleList', {
									})
									.success(function(data, status) {
												console.log('All role data of user = '+ data);
												$scope.vm.userRoleList = data;
												$rootScope.phonNoForOTP = $scope.vm.userRoleList.contactNo;
	                                  });
	
							
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
							
							
							$rootScope.OTPGatewayVerification = function(data) {
								console.log('yapppppppppppppiiiiiiiiiiiiiiii    '+$rootScope.phonNoForOTP);
								try {

									// Set all the parameters for the API call
									// GENERATE and send OTP:
									
									//data += "&override_dnd=true&phone="+$rootScope.phonNoForOTP+"&key=1e777cc2135bf443c957d775cd32449a";// OTP
									//console.log(data);
									var finalUrl = 'https://enterprise.smsgupshup.com/GatewayAPI/rest?'+data;
									console.log(finalUrl);
									// generation payload
									var xmlHttp = new XMLHttpRequest();
								    xmlHttp.open("GET", finalUrl, true); // false for synchronous request
								    xmlHttp.send();
								  //  console.log('resonse isssssss     '+ xmlHttp.responseText);
									if (xmlHttp.readyState == 4 && xmlHttp.status == 200) {
										var response = JSON.parse(xmlHttp.responseText);
										alert(response);
									}
								} catch (Exception) {
									
								}
							}
	
} ]);