var app = angular.module('myApp',['angularFileUpload']);

app.controller('vendorForApprovalCtrl', ['$scope','$http',function($scope, $http ) {
		// Satrt Code to Fetch Vendors For Approval	
		console.log('Going to fetch vendorApproval data');
		$http.get('/vendorInfo/getVendorForApproval', {})
			.success( function(data, status) {
				$scope.vendorData = data;
				console.log('vendor data found vendor data = ' + data);
			});
		// End Code to Fetch Vendors For Approval
		
		// Satrt Code to Fetch Approval History Vendors
		$scope.apprVendorHistory = function() {
		console.log('Going to fetch vendorApproval History data');
		$scope.successMsg='';
		$scope.error = '';
		$http.get('/vendorInfo/getVendorApprovalHistory', {})
			.success( function(data, status) {
				$scope.vendorHistoryData = data;
				console.log('vendor Approval History data found vendor data = ' + data);
			});
		}
		// End Code to Fetch Approval History Vendors
			
		// Start Approve Vendor
		$scope.successMsg='';
		$scope.error = '';
		$scope.vendorObj = '';
		$scope.apprVendor = function(venObj) {
			$scope.vendorObj  = venObj;
			$scope.successMsg='';
			$scope.error = '';
		}
		
		$scope.approveVendor = function(venObj) {
			console.log('In js method to approve vendor id = ' + venObj.vendID);
			console.log('In js method to approve vendor Comment = ' + venObj.comment);
			var parameters = {};
			parameters.vendorId = venObj.vendID;
			parameters.comment = venObj.comment;
			
			$http({
				 method : 'PUT',
				 url : '/vendorInfo/approveVendor',
				 params : parameters
			}).then(function(response) {
			 	$('#myModalCommentAppr .close').click();
				if (response.status == 200) {
					 $scope.successMsg="Vendor Approved successfully.";
				}else{
					$scope.error = "Some problem occured please try again.";
					}
				
				setTimeout(function(){
			        location.reload();
				}, 10000);
			});
		}
		// End Approve Vendor
		
		// Start Decline Vendor
		
		$scope.regectVendor = function(venObj) {
			$scope.vendorObj  = venObj;
			$scope.successMsg='';
			$scope.error = '';
		}
		
		$scope.declineVendor = function(venObj) {
			console.log('In js method to decline vendor id = ' + venObj.vendID);
			console.log('In js method to decline vendor Comment = ' + venObj.comment);
			var parameters = {};
			parameters.vendorId = venObj.vendID;
			parameters.comment = venObj.comment;
			
			$http({
				 method : 'PUT',
				 url : '/vendorInfo/declineVendor',
				 params : parameters
			}).then(function(response) {
			 	$('#myModalCommentDecl .close').click();
				if (response.status == 200) {
					 $scope.successMsg="Vendor Declined successfully.";
				}else{
					$scope.error = "Some problem occured please try again.";
					}
				
				setTimeout(function(){
			        location.reload();
				}, 10000);
			}); 
		}
		// End Decline Vendor
			
		// Start Vendor And Audit Detail
		$scope.venDetail = '';
		$scope.auditLogDetail = '';
		$scope.getVendorDetail = function(venObj) {
			$scope.venDetail = venObj;
			$scope.auditTrailView(venObj);
			$scope.successMsg='';
			$scope.error = '';
		}
				
		$scope.auditTrailView = function(venDetail) {
			console.log('Vendor id = ' + venDetail.vendID);
			$scope.successMsg='';
			$scope.error = '';
			var parameters = {};
			parameters.refID = venDetail.vendID;
			parameters.refType = 'vendorDetail';
		 $http.get('/detailRegistration/viewAuditData', {
			    params: parameters
			}).success(function(data, status, headers,config) {
				 console.log(data);
				  $scope.auditLogDetail = data;
				})
		}
		// End Vendor And Audit Detail
		
		// Start Get Vendor Approver Comment For Vendor			
		$scope.approverLog = '';
		$scope.getAproverLog = function(venObj) {
			var parameters = {};
			parameters.vendorId = venObj.vendID;
			$http.get('/vendorInfo/approverLog', {
				params: parameters
			}).success(function(data, status, headers,config) {
				console.log(data);
				$scope.approverLog = data;
			})
		}		
		// End Get Vendor Approver Comment For Vendor
			
		// Start Disable Vendor
		$scope.venObjDetail = '';
			$scope.dsblVendor = function(venObj){
				console.log('In dsblVendor method');
				$scope.venObjDetail = venObj;
				$scope.successMsg='';
				$scope.error = '';
			}
			
			$scope.disableVendor = function(vendDTO){
				$http({
					method : 'PUT',
					url : '/vendorInfo/inactiveSelectedVendor',
					data : vendDTO
				}).then(function(response) {
				 	$('#myModalDelete .close').click();
					if (response.status == 200) {
						 $scope.successMsg="Vendor Dissabled successfully.";
					}else{
						$scope.error = "Some problem occured please try again.";
						}
					
					setTimeout(function(){
				        location.reload();
					}, 7000);
				}); 
			}
		
		// End Disable Vendor
			
		// Start Search Vendor For Approval
		$scope.searchVendorForApproval = function(searchVendor) {
			console.log("First name entered = ");
			console.log(searchVendor.supplierName);
			
			var stDt = (searchVendor.dateFrom != null) ? searchVendor.dateFrom : '';
			var endDt = (searchVendor.dateTo != null) ? searchVendor.dateTo : '';
			var suplName = (searchVendor.supplierName != null) ? searchVendor.supplierName : '';
		
			var parameters = {};
			parameters.dateFrom = stDt;
			parameters.dateTo = endDt;
			parameters.supplierName = suplName;
				
			$http.get('/vendorInfo/searchVendor', {
				params: parameters
			}).success(function(data, status) {
				$scope.vendorData = data;
			})
		}		
		// End Search Vendor For Approval
		
		// Start Search Vendor For Approval History
		$scope.searchVendorForApprovalHistory = function(searchVendorHistory) {
			console.log("First name entered = ");
			console.log(searchVendorHistory.supplierName);
			
			var stDt = (searchVendorHistory.dateFrom != null) ? searchVendorHistory.dateFrom : '';
			var endDt = (searchVendorHistory.dateTo != null) ? searchVendorHistory.dateTo : '';
			var suplName = (searchVendorHistory.supplierName != null) ? searchVendorHistory.supplierName : '';
		
			var parameters = {};
			parameters.dateFrom = stDt;
			parameters.dateTo = endDt;
			parameters.supplierName = suplName;
				
			$http.get('/vendorInfo/searchVendorApprovalHistory', {
				params: parameters
			}).success(function(data, status) {
				console.log('In success data = ' + data)
				$scope.vendorHistoryData = data;
			})
		}		
		// End Search Vendor For Approval History
		

		}

	]);