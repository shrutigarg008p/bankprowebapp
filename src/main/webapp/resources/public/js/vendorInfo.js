var app = angular.module('myApp',['ngCookies','angularFileUpload']);

app.controller('getVendorInfoCntrl', ['$scope','$http','$cookieStore','$rootScope',function($scope, $http, $cookieStore,$rootScope) {
	console.log('qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq  ');	
			$rootScope.successMsg='';
			$rootScope.error = '';
	
			$scope.viewDataForVendor= function(){
				$http.get('/vendorInfo/viewDataOfVendor', {})
					.success( function(data, status) {
						$scope.vendorData = data;
					})
			}
			$scope.viewDataForVendor();
			// Search Vendor by Suplier name
			$scope.searchVendors = function(search) {
				console.log("In searchVendors Js method");
				var parameters = {};
				parameters.suplierName = search.suplierName;
				parameters.showInactive = search.showInactive;
				$http.get('/vendorInfo/searchBySuplierName', {
					params: parameters
				}).success(function(data, status) {
					$scope.vendorData = data;
				})
			}
			
			$scope.fetchVendorApproval = function(vendorId){
				var parameters = {};
				parameters.vendorId = vendorId;						
				$http.get('/vendorInfo/approverLog', {
				   params: parameters}).success(function(data, status, headers,config) {
						$scope.vendAppList = data;
						})	
			    }
			
		}

	]);

  app.controller('newVendorCntrl',['$scope', '$http', '$cookieStore', '$rootScope',function($scope, $http, $cookieStore, $rootScope) {
			  console.log('uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu    ');
			    
			  		$scope.vm = {
			  			vendorType : [],
			  			companyType : [],
			  			countriesData : [],
			  			stateList : [],
			  			paymentTerm : [],
			  			paymentMethod : [],
		  			}
			  		// Fetch Vendor Type 
		  			$http.get('/vendorInfo/getVendorType' ,{
				
		  			}).success(function(data, status) {
		  				$scope.vm.vendorType = data;
		  			});
	
			  		// Fetch Company Type
		  			$http.get('/vendorInfo/getCompanyType' ,{
				
		  			}).success(function(data, status) {
		  				$scope.vm.companyType = data;
		  			});
		  			
			  		// Fetch Country List Type
		  			$http.get('/detailRegistration/countryView',{
							
					}).success(function(data, status, headers,config) {
						$scope.vm.countriesData = data;
					});
					
			  		// Fetch State List Type
		  			$scope.loadStateName = function(countryID){								
						var parameters = {};
						 parameters.countryID = countryID;
					  	   $http.get('/vendorInfo/getStatList',{
								params: parameters
							}).success(function(data, status, headers,config) {
								$scope.vm.stateList = data;
					    })
				     };
		  			
			  		// Fetch Payment Terms Type
		  			$http.get('/vendorInfo/getPaymentTerm',{
						
					}).success(function(data, status, headers,config) {
						$scope.vm.paymentTerm = data;
					});
		  			
		  			// Fetch Payment Method 
		  			$http.get('/vendorInfo/getPaymentMethod',{
						
					}).success(function(data, status, headers,config) {
						$scope.vm.paymentMethod = data;
					});
		  			
			  
			  $scope.getValueToShowUploadedFile = function(venId, referenceType) {
				   var parameters = {};
				   console.log('Creating org id doc is  ht    '+venId);
				   console.log('Creating org id doc is  qqqqq   '+referenceType);
				   parameters.orgtionID = venId;
				   parameters.referenceType = referenceType;
				
				   $http.get('/detailRegistration/uploadedFileDataView', {
					   params: parameters}).success(
								function(data, status, headers,config) {
								$scope.DocViewObjList = data;
							})	
					
					
				     }
			        var favoriteCookie = $cookieStore.get('editVenObj');
			        $scope.venUpdateFlag = false;
			        $scope.venSaveFlag = true;
			        $scope.showMessage = false;
			        $scope.ven = {};
			        $scope.file = {};
			     
			        $scope.addMoreFiles = [];
			        $scope.DocViewObjList = [];
			        $scope.addFiles = {};
					   $scope.vm = {
				                errorMessages: [],
				                infoMessages: [],
				            };
			        
			        if(favoriteCookie != undefined){
			        	$scope.ven = favoriteCookie;
			        	$scope.venUpdateFlag = true;
			        	$scope.venSaveFlag = false;
			        	$cookieStore.remove('editVenObj');
			        	$scope.getValueToShowUploadedFile($scope.ven.vendID,'vendorDetail');			        	
			        }
			      
					$scope.createVendorInfo = function(venObj) {
							venObj.addressProof = $scope.file.addDoc;
							venObj.panCardDoc = $scope.file.panDoc;
							venObj.cancelCheque = $scope.file.cancelCheque;
							if ($scope.DocViewObjList.length > 0) {
								venObj.newAddedDocListInVendor = $scope.addMoreFiles;
							}
							$http({
								method : 'POST',
								url : '/vendorInfo/saveVendorInfo',
								data : venObj
							}).then(function(response) {
								console.log('dsdfsafsadfs statusssssss  '+response.status);
								if (response.status == 200) {
									 window.location.href = "vendorInfo.html";
									 $rootScope.userMsgAlert('', 'New vendor created successfully.');
								}
							});
					}
					
					$scope.updateVendorInfo = function(venObj) {
							venObj.addressProof = $scope.file.addDoc;
							venObj.panCardDoc = $scope.file.panDoc;
							venObj.cancelCheque = $scope.file.cancelCheque;
							if ($scope.DocViewObjList.length > 0) {
								venObj.newAddedDocListInVendor = $scope.addMoreFiles;
							}
							$http({
								method : 'PUT',
								url : '/vendorInfo/updateVendorDetail',
								data : venObj
							}).then(function(response) {
								if (response.status == 200) {
									window.location.href = "vendorInfo.html";
									 $rootScope.userMsgAlert('', 'Data Modified successfully');
								}
							});
					}
					
					 function showInfoMessage(infoMessage) {
				    	 clearMessages();
			             $scope.vm.infoMessages.push({description: infoMessage});
			            
			            }
					  
					 function clearMessages() {
			                $scope.vm.errorMessages = [];
			                $scope.vm.infoMessages = [];
			            }
					
					$scope.reset = function() {
						$scope.masterReset = {};
						$scope.ven = angular.copy($scope.masterReset);
					};
						
					$scope.viewAndEditVendorDetail = function(vendDTO) {									
							$scope.ven = vendDTO;
							$scope.auditTrailView(vendDTO);
							console.log('view dataaaaaaaaaa          ' + $scope.ven );
						}
					
					$scope.editVendor = function(obj) {						
						console.log('rrrrrrrrrrrrrrrrr   fffffffffffff ');
						$cookieStore.put('editVenObj',obj);	
						window.location.href = "add-vendor.html";
					}
					
					$scope.setAttributeForFileUpload = function(value) {
						$scope.uploadFileType = value;
						console.log($scope.uploadFileType);
					}
					
					
  
					
					 $scope.addManualFileForVendor = function(addFiles){								
							var data = {};					
							console.log('fileeeeeeeeee    '+ $scope.file.fileName);
							if ($scope.file.fileName) {
								console.log('insideeeeeee    '+ $scope.file.fileName);
								data.manuallyGivenFileName = addFiles.manuallyGivenFileName;
								data.fileName = $scope.file.fileName;
								$scope.addMoreFiles.push(data);
								$scope.DocViewObjList.push(data);
								$scope.addFiles = {};	
								$scope.file.fileName ='';
								angular.element('.otherdoc_set input[type="file"]').val('');
							}															
							console.log($scope.addMoreFiles);
						}
					
					
					$scope.deleteColumnValueOfDocOfOrg = function(columnName, rowId){												
						var parameters = {};
						parameters.columnName = columnName;
						parameters.rowId = rowId;
					       if (confirm('Do you wants to delete this item?')) {
								$http({
											method : 'PUT',
											url : '/vendorInfo/deleteDocInfoFromVendor',
											params : parameters
										}).success(function() {
												$('#' + columnName + '-'+ rowId).remove();
											});
								}							
						
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
					
					$scope.auditTrailView = function(venDetail) {
						var parameters = {};
						parameters.refID = venDetail.vendID;
						parameters.refType = 'vendorDetail';
					 $http.get('/detailRegistration/viewAuditData', {
						    params: parameters
						}).success(function(data, status, headers,config) {
							 console.log(data);
							  $scope.auditData = data;
							})
					}
					
					$scope.disableVendorAuth = function(vendDTO){
						$scope.ven = vendDTO;
					}
					$scope.disableVendorInfo = function(vendDTO){
						$http({
							method : 'PUT',
							url : '/vendorInfo/inactiveSelectedVendor',
							data : vendDTO
						})
						
					}
					
					function userMsgAlert(errorMsg, successMsg){
						$rootScope.successMsg='';
						$rootScope.error = '';
						$rootScope.successMsg=successMsg;
						$rootScope.error = errorMsg;
						 $('#msgTimeOut').show();
						    setTimeout(function() {
								$('#msgTimeOut').hide();
							}, 10000);
					 }					
					
					
					$scope.uploadBatchFile = function(){
						var parameters = {};
						console.log('$scope.file.batchUpload    '+$scope.file.batchUpload);
						parameters.fileName = $scope.file.batchUpload;
						console.log('in the batch file uplaod controolerrrrrrrrrrrr    ++++ ');
						$http({
							method : 'POST',
							url : '/vendorInfo/batchUpload',
							params : parameters
						}).then(function(response) {
							if(response.status == 200){
								$scope.showMessage = true;
								$scope.reset();
								showInfoMessage("File uploaded successfully");
							   $http.get('/vendorInfo/viewDataOfVendor', {})
						           .success( function(data, status) {
									    $scope.vendorData = data;
								     })
								}else{
									
								}
							});						
					    }
						

				} ]);
  

  app.controller('FileUploadCtrl',['$scope','FileUploader',	function($scope, FileUploader) {
							var uploader = $scope.uploader = new FileUploader({
								method : 'POST',
								url : '/fileUploadProcess/fileUploadOnServer',
								autoUpload : true,
							});

							// FILTERS
							uploader.filters.push({
								name : 'customFilter',
								fn : function(
										item /* {File|FileLikeObject} */,
										options) {
									return this.queue.length < 10;
								}
							});

							// CALLBACKS

							uploader.onWhenAddingFileFailed = function(
									item /* {File|FileLikeObject} */, filter,
									options) {
								console.info('onWhenAddingFileFailed', item,
										filter, options);
							};
							uploader.onAfterAddingFile = function(fileItem) {
								console.info('onAfterAddingFile', fileItem);
							};
							uploader.onAfterAddingAll = function(addedFileItems) {
								console
										.info('onAfterAddingAll',
												addedFileItems);
							};
							uploader.onBeforeUploadItem = function(item) {
								console.info('onBeforeUploadItem', item);
							};
							uploader.onProgressItem = function(fileItem,
									progress) {
								console.info('onProgressItem', fileItem,
										progress);
							};
							uploader.onProgressAll = function(progress) {
								console.info('onProgressAll', progress);
							};
							uploader.onSuccessItem = function(fileItem,
									response, status, headers) {
								console.info('onSuccessItem', fileItem,
										response, status, headers);
								$('.alert').addClass('hide');
								if (response.error) {
									$('.alert-danger').html(response.message)
											.removeClass('hide');
								} else {
									console.log($scope.uploadFileType);
									if ($scope.uploadFileType == 'addressProof') {
										$scope.file.addDoc = response;
										console.log($scope.file.addDoc);
									} else if ($scope.uploadFileType == 'panCard') {
										$scope.file.panDoc = response;
									} else if ($scope.uploadFileType == 'manualFileSelected') {
										$scope.file.fileName = response;
									} else if ($scope.uploadFileType == 'cancelCheque') {
										$scope.file.cancelCheque = response;
									} else if ($scope.uploadFileType == 'batchUpload') {
										$scope.file.batchUpload = response;
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