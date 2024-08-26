var app = angular.module('editRegApp', [ 'angularFileUpload' ]);

 app.controller('editRegistrationCtrll',['$scope','$http', '$rootScope',function($scope, $http, $rootScope) {
							$scope.vn = {};
							$scope.uploadFileType = '';
							$scope.DocViewObjList = {};
							$scope.addMoreFiles = [];
							$scope.addFiles = {};
							$scope.refType = '';

							
							console.log('going to fetch the value for edit registrationnnnnnnnnnnnnnnn    ');
							$http.get('/detailRegistration/editRegistration',
									{}).success(function(data, status, headers, config) {
										$scope.vn = data;
										$scope.vn.countryID = "" + $scope.vn.countryID;
										$scope.loadStateName($scope.vn.countryID);
										$scope.getValueToShowUploadedFile($scope.vn.orgID,'orgDetail');										
										$scope.refType = 'orgDetail';
										$scope.vn.stateNameID = "" + $scope.vn.stateNameID;
									})														
							
							
							$scope.getValueToShowUploadedFile = function(orgId, referenceType) {
								  var parameters = {};
								  parameters.orgtionID = orgId;
								  parameters.referenceType = referenceType;
								
							   $http.get('/detailRegistration/uploadedFileDataView', {
								   params: parameters}).success(
											function(data, status, headers,config) {
											$scope.DocViewObjList = data;
										})	
								
								
							       }
							
							$http.get('/detailRegistration/countryView', {

							}).success(function(data, status, headers, config) {
								$scope.countriesData = data;
							   })
							
							$scope.addManualFile = function(addFiles){								
								var data = {};								
								data.manuallyGivenFileName = addFiles.manuallyGivenFileName;
								data.fileName = $scope.addFiles.fileName;
								data.referenceId = $scope.vn.orgID;
								data.refType = $scope.refType;
								
								if($scope.addFiles.fileName){
									$scope.addMoreFiles.push(data);
									$scope.DocViewObjList.push(data);
								}
								
								$scope.addFiles = {};
								
								angular.element('.otherdoc_set input[type="file"]').val('');
							}
							
							$scope.setAttribute = function(value){
								$scope.uploadFileType = value;
							}
							
							$scope.loadStateName = function(countryID){								
								var parameters = {};
								 parameters.countryID = countryID;
							  	   $http.get('/vendorInfo/getStatList',{
										params: parameters
									}).success(function(data, status, headers,config) {
										$scope.stateList = data;
							    })
						     };

							
				} ]) 
 app.controller('updateRegistrationCntl',['$scope','$http', '$rootScope', function($scope, $http, $rootScope) {
	 
							$scope.updateRegistration = function() {
								if ($scope.form.$invalid) {
									return;
								}
								if(!doPreValidationOfORG()){
									return;
								}
								
								var updateData = {
									addressProofDoc : $scope.vn.addDoc,
									panCardDoc : $scope.vn.addPan,
									legalEntityName : $scope.vn.legalEntityName,
									organization : $scope.vn.organization,
									cin : $scope.vn.cin,
									panNo : $scope.vn.panNo,
									addressLine1 : $scope.vn.addressLine1,
									addressLine2 : $scope.vn.addressLine2,
									addressLine3 : $scope.vn.addressLine3,
									pinCode : $scope.vn.pinCode,
									url : $scope.vn.url,									
									contactNo : $scope.vn.contactNo,
									email : $scope.vn.email,
									inboxEmailID : $scope.vn.inboxEmailID,
									industry : $scope.vn.industry,
									employCount : $scope.vn.employCount,
									entityType : $scope.vn.entityType,
									countryID : $scope.vn.countryID,
									citiName : $scope.vn.citiName,
									stateNameID : $scope.vn.stateNameID

								};
								if($scope.addMoreFiles.length > 0){
									updateData.newAddedDocList = $scope.addMoreFiles;
								}
							
								
								$http({
									method : 'PUT',
									url : '/detailRegistration/updateRegistrationDetail',
									data : updateData,
									headers: {
										"Content-Type": "application/json",
										"Accept": "text/plain, application/json"
									}
								}).success(function (response) {											
									$rootScope.userMsgAlert('', 'Company information modified successfully');
								
								}).error(function (response) { /* display proper error message */
									$rootScope.userMsgAlert(response, '');
								});
						 }
							
							function doPreValidationOfORG(){
								if($scope.vn.pinCode != undefined && $scope.vn.pinCode != '' && isNaN($scope.vn.pinCode)){
									$rootScope.userMsgAlert("Please define the valid pinCode code", '');
									return false;
								}
								if($scope.vn.contactNo != undefined && $scope.vn.contactNo != '' && isNaN($scope.vn.contactNo)){
									$rootScope.userMsgAlert("Please define the valid Phone Number", '');
									return false;
								}
								return true;
							}
							
							
					 $scope.deleteDocInfo = function(docID){												
								console.log(docID);
								var parameters = {};
								parameters.docID = docID;
								
								if(confirm('Do you wants to delete this item?')) {
									$http({
										method : 'PUT',
										url : '/detailRegistration/deleteDocInfo',
										params: parameters
									}).success(function(){
										$('#doc-view-'+parameters.docID).remove();
									});
								}						
							 }
					 
					 $scope.deleteColumnValueOfDocOfOrg = function(columnName, rowId){												
							var parameters = {};
							parameters.columnName = columnName;
							parameters.rowId = rowId;									
					   if(confirm('Do you wants to delete this item?')) {
						 $http({
									method : 'PUT',
									url : '/detailRegistration/deleteDocInfoFromOrg',
									params: parameters
								}).success(function(){
									$('#'+columnName+'-'+rowId).remove();
							});
						 }							
							
				     }
 
			     }
            ]);
 
 
 app.controller('FileUploadCtrl',['$scope','FileUploader',function($scope, FileUploader) {
		var uploader = $scope.uploader = new FileUploader({
			method : 'POST',
			url : '/fileUploadProcess/fileUploadOnServer',
			autoUpload : true,
		});
		
		if ($scope.uploadFileType == 'manualFileSelected' || $scope.uploadFileType == 'address' || $scope.uploadFileType == 'panCard') {
			uploader.filters.push({
		        name: 'imageFilter',
		        fn: function(item /*{File|FileLikeObject}*/, options) {
		            var type = '|' + item.type.slice(item.type.lastIndexOf('/') + 1) + '|';
		            return '|jpg|png|jpeg|bmp|gif|'.indexOf(type) !== -1;
		        }
		    });
		}else if($scope.uploadFileType == 'batchUpload'){
			uploader.filters.push({
				name : 'customFilter',
				fn: function(item /*{File|FileLikeObject}*/, options) {
		            var type = '|' + item.type.slice(item.type.lastIndexOf('/') + 1) + '|';
		            return '|vnd.ms-excel|'.indexOf(type) !== -1;
		        }
			});
		  }
		// CALLBACKS
	
			uploader.onWhenAddingFileFailed = function(item /* {File|FileLikeObject} */, filter,options) {
				if($scope.uploadFileType == 'batchUpload'){
					userMsgAlert('Selected list of Payment approver can not be empty', '');
					$scope.error ="Supported format is : CSV. Please upload file only in the given format";
					$scope.successMsg='';							
				}else if($scope.uploadFileType == 'manualFileSelected'){
					$scope.error ="Supported formats are : JPG |PNG |JPEG. Please upload file only in the given format";
					$scope.successMsg='';
					alert('Supported formats are : JPG |PNG |JPEG. Please upload file only in the given format')
				}
				angular.element('.otherdoc_set input[type="file"], .batch_upload input[type="file"]').val('');
			};
			uploader.onAfterAddingFile = function(fileItem) {
			};
			uploader.onAfterAddingAll = function(addedFileItems) {
			};
			uploader.onBeforeUploadItem = function(item) {
				console.info('onBeforeUploadItem', item);
			};
			uploader.onProgressItem = function(fileItem,
					progress) {
			};
			uploader.onProgressAll = function(progress) {
				console.info('onProgressAll', progress);
			};
			uploader.onSuccessItem = function(fileItem,response, status, headers) {
				console.info('onSuccessItem', fileItem,response, status, headers);
				$('.alert').addClass('hide');
				if (response.error) {
					$('.alert-danger').html(response.message)
							.removeClass('hide');
				} else {
					console.log($scope.uploadFileType);
					if($scope.uploadFileType == 'address') {
						$scope.vn.addDoc = response;
					} else if($scope.uploadFileType == 'panCard'){
						$scope.vn.addPan = response;
					}else if($scope.uploadFileType == 'manualFileSelected'){
						$scope.addFiles.fileName = response;
					} 
				}
			};
			uploader.onErrorItem = function(fileItem, response,
					status, headers) {
				console.info('onErrorItem', fileItem, response,
						status, headers);
			};
			uploader.onCancelItem = function(fileItem,
					response, status, headers) {
				console.info('onCancelItem', fileItem,
						response, status, headers);
			};
			uploader.onCompleteItem = function(fileItem,
					response, status, headers) {
				console.info('onCompleteItem', fileItem,
						response, status, headers);
			};
			uploader.onCompleteAll = function() {
				console.info('onCompleteAll');
			};
		
			console.info('uploader', uploader);
} ]);