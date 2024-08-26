var app = angular.module('bankApp', [ 'angularFileUpload' ]);

app.controller('editRegistrationCtrl',['$scope','$http',function($scope, $http) {
        	   
        	   $scope.vn = {};

							$http.get('/detailRegistration/countryView',{
									
								}).success(function(data, status, headers,config) {
									$scope.countriesData = data;
								})
							
				} ]) 						
app.controller('viewBankDetailCtrl',['$scope','$http',	function($scope, $http) {
							console.log('going to fetch the value for bank detailsssss       ');
							$scope.bankDataForView={};
							$scope.addMoreFiles = [];
							$scope.addFiles = {};
							$scope.usr = {};
							$scope.DocViewObjList = [];
								$http.get('/detailRegistration/viewBankDetail',{

										}).success(
												function(data, status) {
													$scope.bankDataForView = data;
												})								
														
						$scope.viewAndEditBankDetail = function(bankDetail) {									
								$scope.vm = bankDetail;
								console.log('view dataaaaaaaaaa          ' +$scope.vm.panCardDoc);
								$scope.getValueToShowUploadedFile($scope.vm.bankId, 'bankDetail')
							}
						

						$scope.auditTrailView = function(bankDetail) {
							var parameters = {};
							parameters.refID = bankDetail.bankId;
							parameters.refType = 'bankDetail';						
						 $http.get('/detailRegistration/viewAuditData', {
							    params: parameters
							}).success(function(data, status, headers,config) {
										$scope.auditData = data;
									})
						}
							



						$scope.updateBankData = function(bankData) {
								console.log('in the update of bank detail formmmmmmmmmmmm    ');
								bankData.debitMandateDoc = $scope.usr.debitMandate;
								bankData.boardResolutionDoc = $scope.usr.boardResolution;
								bankData.panCardDoc = $scope.usr.panCard;
								bankData.addressProofDoc = $scope.usr.addressProof;
								bankData.cancelCheque = $scope.usr.cancelCheque;
								if ($scope.DocViewObjList.length > 0) {
									bankData.newAddedDocListInBank = $scope.addMoreFiles;
								}
								
								$http({
									method : 'PUT',
									url : '/detailRegistration/udpateBankDetail',
									data : bankData
								}).then(function(response) {

									if (response.status == 200) {
										setTimeout(function(){
									        location.reload();
										}, 3000);
									}
								});

							}		
						 
						$scope.saveBankDetail = function(usr){
							 usr.debitMandateDoc = $scope.usr.debitMandate;
							 usr.boardResolutionDoc = $scope.usr.boardResolution;
							 usr.panCardDoc = $scope.usr.panCard;
							 usr.addressProofDoc = $scope.usr.addressProof;
							 usr.cancelCheque = $scope.usr.cancelCheque;
							 usr.trigerBankDialogueFlag = true;
							 if ($scope.DocViewObjList.length > 0) {
								 usr.newAddedDocListInBank = $scope.addMoreFiles;
								}
							 
							 console.log('in the save of bank detail form   '+usr.debitMandateDoc);
							 
							 
							 $http({
								 method : 'POST',
								 url : '/detailRegistration/bankDetail',
								 data : usr
								 }).then(function(response) {
										setTimeout(function(){
									        location.reload();
										}, 3000);
									}); 
							 
						 }
						 
						$scope.deleteSelectedFile = function(bankDTO){
							 console.log('in the save of bank detail form   '+bankDTO.bankId);											 
							 $http({
								 method : 'PUT',
								 url : '/detailRegistration/deleteDocInfo',
								 data : bankDTO,
//								 params: Indata
								 })
							 
						 }
						 

						$scope.inactiveSelectedAcct = function(bankDTO) {
								$http({
									method : 'PUT',
									url : '/detailRegistration/inactiveSelectedAcct',
									data : bankDTO,
								})

							}
							
							
					  $scope.setAttributeForBankFileUpload = function(value) {
								$scope.uploadFileType = value;
								console.log($scope.uploadFileType);
							}
						
					  $scope.addManualFileForBank = function(addFiles){								
							var data = {};					
							console.log('fileeeeeeeeee    '+ $scope.addFiles.fileName);
							if ($scope.addFiles.fileName != 'undefined') {
								console.log('insideeeeeee    '+ $scope.addFiles.fileName);
								data.manuallyGivenFileName = addFiles.manuallyGivenFileName;
								data.fileName = $scope.addFiles.fileName;
								$scope.addMoreFiles.push(data);
								$scope.DocViewObjList.push(data);
								$scope.addFiles = {};							
								angular.element('.otherdoc_set input[type="file"]').val('');
							}															
							console.log($scope.addMoreFiles);
						}
						
					  $scope.getValueToShowUploadedFile = function(bankId, referenceType) {
							  var parameters = {};
							  parameters.orgtionID = bankId;
							  parameters.referenceType = referenceType;
							
						   $http.get('/detailRegistration/uploadedFileDataView', {
							   params: parameters}).success(
										function(data, status, headers,config) {
										$scope.DocViewObjList = data;
									})						
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
							   
					 $scope.deleteColumnValueOfDoc = function(columnName, rowId){												
									var parameters = {};
									parameters.columnName = columnName;
									parameters.rowId = rowId;									
							  if(confirm('Do you wants to delete this item?')) {
								 $http({
											method : 'PUT',
											url : '/detailRegistration/deleteDocFromColumn',
											params: parameters
										}).success(function(){
											$('#'+columnName+'-'+rowId).remove();
									});
								}							
									
						 }
						 
							
				} ])
							


app.controller('bankFileUploadCtrl', [ '$scope', 'FileUploader', function($scope, FileUploader) {
					var uploader = $scope.uploader = new FileUploader({
						method : 'POST',
						url : '/detailRegistration/fileUploadOnServer',
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
													
							 if($scope.uploadFileType == 'manualFileSelected'){
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
             