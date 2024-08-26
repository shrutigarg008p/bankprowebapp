var app = angular.module('myApp', [ 'angularFileUpload' ]);


app.controller('NewRegistorCtrl',['$scope','$http','$q','$rootScope',function($scope, $http, $q, $rootScope) {

							$scope.showMessage = false;

							$scope.getValueFromRole = function() {
								console.log('Creating user w role masterrrrrrrrrrr')
								$http.get('/register/roleValue', {

										})
										.success(function(data, status, headers,config) {
													console.log('Creating user w role masterrrrrrrrrrr  afterrrrr '+ data)
													$scope.userRole = data;

												})

							    }
							$rootScope.successMsg='';
							$rootScope.error = '';
							$scope.usr = {};
							$scope.countriesData = [];
							$scope.bankDialog = '';
							$scope.comanyDialog = '';
							$scope.uploadFileType = '';
							$scope.addFiles = {};
							$scope.orgId='';
							$scope.refType = '';
							$scope.addMoreFiles = [];
							$http.get('/detailRegistration/companyDialogDetail',
											{})	.success(function(data, status) {
												$scope.usr.email = data.emailID;
												$scope.usr.organization = data.orgName;
												$scope.bankDialog = data.bankDialogueFlag;
												$scope.comanyDialog = data.companyDialogFlag;
												$scope.orgId = data.orgID;
												if (data.bankDialogueFlag == true || data.companyDialogFlag == true) {
													$scope.loadCountryList();													
												}
												if (data.companyDialogFlag) {
													$('#btn-open-modal').click();
													$scope.getValueToShowUploadedFile(data.orgID,'orgDetail');
													$scope.refType = 'orgDetail';
												}
												console.log('before open the dialogue         '+ data.bankDialogueFlag);
												if ((data.companyDialogFlag == false)&& (data.bankDialogueFlag == true)) {
													//$scope.getValueToShowUploadedFile(data.orgID,'orgDetail');
													$('#btn-open-demo-2').click();
												}
										})
					
							$scope.getValueToShowUploadedFile = function(orgId, referenceType) {
								  var parameters = {};
								  console.log('Creating org id doc is  ht    '+orgId);
								  console.log('Creating org id doc is  qqqqq   '+referenceType);
								  parameters.orgtionID = orgId;
								  parameters.referenceType = referenceType;
								
							      $http.get('/detailRegistration/uploadedFileDataView', {
								   params: parameters}).success(
											function(data, status, headers,config) {
											$scope.DocViewObjList = data;
										})	
							        }
							
							// Fetch State List Type
							$scope.loadStateName = function(countryID){								
								var parameters = {};
								 parameters.countryID = countryID;
							  	   $http.get('/vendorInfo/getStatList',{
										params: parameters
									}).success(function(data, status, headers,config) {
										$scope.usr.stateList = data;
							    })
						     };
							
							
							
							$scope.loadCountryList = function() {
								$http.get('/detailRegistration/countryView',{
									
								}).success(function(data, status, headers,config) {
									$scope.countriesData = data;
								})
							};
							
							function doPreValidationOfORG(usr){
								if(usr.organization == undefined || usr.organization == ''){
									$rootScope.userMsgAlert("Organization Name can't be left empty", '');
									return false;
								}
								return true;
							}
							

							$scope.detailUserInfoSave = function(usr,submitFlag) {
								console.log('saving further detail of selected user with first name');
								if(!doPreValidationOfORG(usr)){
									return;
								}
								
								 console.log($scope.usr.addDoc);
								 usr.addressProofDoc = $scope.usr.addDoc;
								 usr.panCardDoc = $scope.usr.panDoc;
								 if($scope.addMoreFiles.length > 0){
							      usr.newAddedDocList = $scope.addMoreFiles;
								 }
								 if(submitFlag == 'draft'){
									 usr.submitDialogueRequestFlag = true;
								 }else{
									 usr.submitDialogueRequestFlag = false;
								 }

								$http({
											method : 'POST',
											url : '/detailRegistration/finalComanyDialogReq',
											data : usr,	
											headers: {
													"Content-Type": "application/json",
													"Accept": "text/plain, application/json"
												}
									     }).success(function (response) {											
												if ($scope.bankDialog == true) {
													$('#myModal .close').click();
													setTimeout(function() {
														$('#btn-open-demo-2').click();
													}, 300);
												} else {
													$('#myModal .close').click();
												}
												$rootScope.userMsgAlert('', 'Company details have been saved successfully');
											
										}).error(function (response) { /* display proper error message */
											$rootScope.userMsgAlert(response, '');
										});
											
                                 }
							
							$scope.addManualFile = function(addFiles){								
								var data = {};								
								data.manuallyGivenFileName = addFiles.manuallyGivenFileName;
								data.fileName = $scope.addFiles.fileName;
								data.referenceId = $scope.orgId;
								data.refType = $scope.refType;
								console.log('fileeeeeeeeee    '+$scope.addFiles.fileName);
								if($scope.addFiles.fileName){
									$scope.addMoreFiles.push(data);
									$scope.DocViewObjList.push(data);
								}
								
								$scope.addFiles = {};								
								angular.element('input[type="file"]').val('');								
								console.log($scope.addMoreFiles);
							}
							
							
							$scope.setAttributeForFileUpload = function(value) {
								$scope.uploadFileType = value;
								console.log($scope.uploadFileType);
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

app.controller('FileUploadCtrl',['$scope','FileUploader',function($scope, FileUploader) {
					var uploader = $scope.uploader = new FileUploader({
						method : 'POST',
						url : '/fileUploadProcess/fileUploadOnServer',
						autoUpload : true,
					});

					// FILTERS
					uploader.filters.push({
								name : 'customFilter',
								fn : function(item /* {File|FileLikeObject} */,
										options) {
									return this.queue.length < 10;
								}
							});

					// CALLBACKS

					uploader.onWhenAddingFileFailed = function(
							item /* {File|FileLikeObject} */, filter, options) {
						console.info('onWhenAddingFileFailed', item, filter,
								options);
					};
					uploader.onAfterAddingFile = function(fileItem) {
						console.info('onAfterAddingFile', fileItem);
					};
					uploader.onAfterAddingAll = function(addedFileItems) {
						console.info('onAfterAddingAll', addedFileItems);
					};
					uploader.onBeforeUploadItem = function(item) {
						console.info('onBeforeUploadItem', item);
					};
					uploader.onProgressItem = function(fileItem, progress) {
						console.info('onProgressItem', fileItem, progress);
					};
					uploader.onProgressAll = function(progress) {
						console.info('onProgressAll', progress);
					};
					uploader.onSuccessItem = function(fileItem, response,
							status, headers) {
						console.info('onSuccessItem', fileItem, response,
								status, headers);
						$('.alert').addClass('hide');
						if (response.error) {
							$('.alert-danger').html(response.message)
									.removeClass('hide');
						} else {							
							if($scope.uploadFileType == 'addressDoc'){
								$scope.usr.addDoc = response;
							}else if($scope.uploadFileType == 'panDoc'){
								$scope.usr.panDoc = response;
							}else if($scope.uploadFileType == 'manualFileSelected'){
								$scope.addFiles.fileName = response;
							}else if($scope.uploadFileType == 'debitMandate'){
								$scope.usr.debitMandate = response;
							}else if($scope.uploadFileType == 'boardResolution'){
								$scope.usr.boardResolution = response;
							}else if($scope.uploadFileType == 'panCardBank'){
								$scope.usr.panCard = response;
							}else if($scope.uploadFileType == 'addressBank'){
								$scope.usr.addressProof = response;
							}else if($scope.uploadFileType == 'cancelledCheque'){
								$scope.usr.cancelCheque = response;
							}							
						}
					};
					uploader.onErrorItem = function(fileItem, response, status,
							headers) {
						console.info('onErrorItem', fileItem, response, status,
								headers);
					};
					uploader.onCancelItem = function(fileItem, response,
							status, headers) {
						console.info('onCancelItem', fileItem, response,
								status, headers);
					};
					uploader.onCompleteItem = function(fileItem, response,
							status, headers) {
						console.info('onCompleteItem', fileItem, response,
								status, headers);
					};
					uploader.onCompleteAll = function() {
						console.info('onCompleteAll');
					};

					console.info('uploader', uploader);
				} ]);

app.controller('bankDetailCtrl',['$scope','$http','$rootScope',function($scope, $http, $rootScope) {
							$scope.addMoreFilesForBank = [];
							$scope.DocViewObjListForBank = [];
							$scope.randomGenratedOTP = '';
							$scope.addManualFileForBank= function(addFiles) {
								var data = {};
								
								data.manuallyGivenFileName = addFiles.manuallyGivenFileName;
								data.fileName = $scope.addFiles.fileName;
								data.referenceId = $scope.orgId;
								data.refType = $scope.refType;
								console.log('fileeeeeeeeee    '+$scope.addFiles.fileName);
								if ($scope.addFiles.fileName != 'undefined') {
									$scope.addMoreFilesForBank.push(data);
									$scope.DocViewObjListForBank.push(data);
								}

								$scope.addFiles = {};
								angular.element('input[type="file"]').val('');
								console.log($scope.addMoreFiles);
							}
							
							function validationOnSubmitOfBankDetail(usr){
								if(usr.accountNumber == undefined || isNaN(usr.accountNumber)){
									$rootScope.userMsgAlert("Please define the valid Account no", '');
									return false;
								}
								if(usr.termAcceptanceFlag == undefined || usr.termAcceptanceFlag == false){
									$rootScope.userMsgAlert("Please indicate that you accept the Terms and Conditions", '');
									return false;
								}
								if(usr.transPass != usr.confirmTransPass){
									$rootScope.userMsgAlert("Given transaction password not match", '');
									return false;
								}
								if($scope.randomGenratedOTP != usr.userDefinedOTP){
									$rootScope.userMsgAlert("OTP does not match. Please try again", '');
									return false;
								}
								return true;
							}
							
							$scope.generateOTPForBank = function(accNo){							
								console.log(accNo);
								console.log($rootScope.phonNoForOTP);
								if(accNo == undefined || accNo == ''){
									$rootScope.userMsgAlert("Before OTP verification you must have to specify the Account Name", '');
									return;
								}								
								$scope.randomGenratedOTP = Math.floor(Math.random()*900000) + 100000;
                                var data = "method=SendMessage&send_to="+$rootScope.phonNoForOTP+"&msg=You have initiated registration of " +
								"bank account "+accNo+" that needs an OTP. NEVER SHARE IT WITH ANYONE. PaymentVista NEVER CALLS TO " +
								"VERIFY IT. The OTP is "+$scope.randomGenratedOTP+"&msg_type=TEXT&userid=2000161437&auth_scheme=plain&password=" +
								"Check@123&v=1.1&format=text"
								$rootScope.OTPGatewayVerification(data);							
							}
							
							$scope.saveBankDetail = function(usr,bankDialogueFlag) {
								console.log('Here we go for save the bank detail for user ');
								$scope.usr.submitted = true;
								if ($scope.form.$invalid) {
									return;
								}
								if(!validationOnSubmitOfBankDetail(usr)){
									return
								}								
								console.log('Here we go for creating user with vendor first name '+ bankDialogueFlag);
								 if (bankDialogueFlag == 'draft') {
									usr.trigerBankDialogueFlag = true;
								} else {
									usr.trigerBankDialogueFlag = false;
								}
								 usr.debitMandateDoc = $scope.usr.debitMandate;
								 usr.boardResolutionDoc = $scope.usr.boardResolution;
								 usr.panCardDoc = $scope.usr.panCard;
								 usr.addressProofDoc = $scope.usr.addressProof;
								 usr.cancelCheque = $scope.usr.cancelCheque;
								 
								 if ($scope.DocViewObjListForBank.length > 0) {
									usr.newAddedDocListInBank = $scope.addMoreFilesForBank;
								}
							   /*$http({
									method : 'POST',
									url : '/detailRegistration/bankDetail',
									data : usr,
								}).success(function(data, status) {
									if(status == '200'){												
										$('#demo-2 .close').click();
									}	
								
								}) */
								
							}

						} ]);	 						