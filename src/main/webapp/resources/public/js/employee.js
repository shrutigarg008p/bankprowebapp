var app = angular.module('myEmpApp', [ 'angularFileUpload' ]);

app.controller('EmployeeCtrl',['$scope','$http', '$rootScope', function($scope, $http, $rootScope) {

				// Start code to fetch all employee list from DB   
				$scope.vm = {
					empList : [],
					paymentMethod : [],
				}
				$scope.emp = {};
				$rootScope.successMsg='';
				$rootScope.error = '';
				$scope.nameToSearch= '';
				
				$scope.file = {};
			
				console.log('Fetch employee list');				
				$scope.fetchEmployeeListForView = function(){
					$scope.nameToSearch='';
					$http.get('/emp/empList' ,{
											
					}).success(function(data, status) {
						console.log('All emp data = '+ data)
						$scope.vm.empList = data;
					});
                 }
				$scope.fetchEmployeeListForView();
				// End code to fetch employee list
				
				// Fetch Payment Method 
				$http.get('/vendorInfo/getPaymentMethod',{
					
				}).success(function(data, status, headers,config) {
					$scope.vm.paymentMethod = data;
				});
			
			
				// Start Employee and Audit Detail
				$scope.getEmpDetail = function(empOb) {
					$scope.empDetail = empOb;
					$scope.getEmpAuditLogDetail(empOb);
				}
				// End Employee and Audit Detail 
				
				// Start Employee Audit Log Detail
				$scope.getEmpAuditLogDetail = function(empOb) {
					$scope.successMsg='';
					$scope.error = '';
					var parameters = {};
					parameters.refID = empOb.vendID;
					parameters.refType = 'EmployeeDetail';
				 $http.get('/detailRegistration/viewAuditData', {
					    params: parameters
					}).success(function(data, status, headers,config) {
						 console.log(data);
						  $scope.auditLogDetail = data;
						})
				}
				// End Employee Audit Log Detail
				
				// Start Disable Employee
				$scope.empObj = '';
				$scope.dsblEmployee = function(empInfo) {
					$scope.empObj  = empInfo;
				}
				
				$scope.disableEmp = function(empOb) {
						console.log("In dissable emp js method");
						console.log(empOb.empId);
						
						var postData = {
								vendID : empOb.vendID
						};
						$http({
							method : 'PUT',
							url : '/emp/disableEmp',
							data : postData
						}).then(function(response) {
						 	$('#myModalDelete .close').click();
							if (response.status == 200) {
								$rootScope.userMsgAlert('', 'Employee Deactivated successfully.');
								$scope.resetEmployeePopValue();
								$scope.fetchEmployeeListForView();
							}else{
								$rootScope.userMsgAlert('Some problem occured please try again.', '');
								}
						}); 
				  }
				// End Disable Employee
				
				// Search Employee Start
				$scope.searchEmployees = function() {
					console.log("In Search Employee Js Method");
					console.log($scope.nameToSearch);
					if($scope.nameToSearch == undefined || $scope.nameToSearch == ''){
						$rootScope.userMsgAlert('Please input somthing to search', '');
						return;
					}
					var parameters = {};
					parameters.firstName = $scope.nameToSearch;
					
					$http.get('/emp/searchEmp', {
					    params: parameters
					}).success(function(data, status) {
								$scope.vm.empList = data;
								if($scope.vm.empList == undefined || $scope.vm.empList == ''){
									$rootScope.userMsgAlert('', 'No result found');									
								}
							})
				}
				// Search Employee End
				
				// validation of employeee add and modify pop up
				 function doPreValidationOfEmp(emp){
					 if(emp.accountNo == undefined || isNaN(emp.accountNo)){
							$rootScope.userMsgAlert("Please define the valid Account no", '');
							return false;
						}
					 if(isNaN(emp.phnNo)){
						    $rootScope.userMsgAlert("Please enter the valid Phone no", '');
							return false;
					 }					 
					 return true;
				 }
				
				
				// Start Add New Employee
				
				$scope.addNewEmployee = function(emp){
					console.log("In addNewEmployee method   "+emp.mID);
					//$scope.emp.submitted = true;
					if ($scope.form.$invalid) {
						return;
					}
					if(!doPreValidationOfEmp(emp)){
						return;
					}					
					 $http({
						 method : 'POST',
						 url : '/emp/addEmployee',
						 data : emp,
						 }).then(function(response) {
								if (response.status == 200) {
									$('#myModal .close').click();
									$rootScope.userMsgAlert('', 'You have successfully add the employee.');
									$scope.resetEmployeePopValue();
									$scope.fetchEmployeeListForView();
								}else{
									$scope.error = "Some problem occured please try again";
									}
							});
			        	}
				// End Add New Employee
				
				$scope.resetEmployeePopValue = function(){
					$scope.emp = {};
				}
				
				// Start Update Employee		
				
				$scope.updateEmployee = function(empDetail) {
					$scope.successMsg='';
					$scope.error = '';
					console.log("In updateEmployee js method");
					$scope.vm.submitted = true;
					if ($scope.form.$invalid) {
						return;
					}					
					$http({
						 method : 'PUT',
						 url : '/emp/updtEmpDetail',
						 data : empDetail
						 }).then(function(response) {
								if (response.status == 200) {
									$('#myModalEdit .close').click();
									$rootScope.userMsgAlert('', 'Employee data is updated successfully');
									$scope.resetEmployeePopValue();
									$scope.fetchEmployeeListForView();
								}else{
									$scope.error = "Some problem occured please try again";
									}
							 });
				    }
				// End Update Employee
				
				// Start Show Error and Info Message
				function showErrorMessage(errorMessage) {
					clearMessages();
			        $scope.vm.errorMessages.push({description: errorMessage});
				}
				 
				function clearMessages() {
					$scope.vm.errorMessages = [];
			        $scope.vm.infoMessages = [];
				}
				 
				function showInfoMessage(infoMessage) {
					clearMessages();
					$scope.vm.infoMessages.push({description: infoMessage});
			       
			    }
				// End Show Error and Info Message
				
				$scope.uploadEmpBatchFile = function() {
					console.log("This is my js method");
					console.log($scope.file.addDoc);
					
					var parameters = {};
					parameters.fileName = $scope.file.addDoc;
					$http({
						 method : 'POST',
						 url : '/emp/uploadEmp',
						 params : parameters
						 }).success(function(data, status, headers,config) {
						 console.log(data);
						  $scope.auditLogDetail = data;
						}).then(function(response) {
							if (response.status == 200) {
								$scope.showMessage = true;
			//					$scope.resetEmp();
								 showInfoMessage("File Uploaded Successfully");
							}else{
								$scope.showMessage = true;
								showInfoMessage(response.data);
								}
						});
					
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
			$scope.file.addDoc = response;
			
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
