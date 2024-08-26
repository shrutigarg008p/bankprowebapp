var app = angular.module('roleAccessApp', []);

app.controller('roleAccessCntrl',['$scope','$http',function($scope, $http) { 
	
	// Start code to fetch all Role list   
	$scope.vm = {
		roleList : [],
		paramData : [],
	}
	
	
	// End code to fetch Role list
	
	// Set Permission array
	//$scope.roleType = [ "Company Admin", "Invoice Maker", "Invoice Approver", "Payment Maker", "Payment Approver" ];
	$scope.permissionList = [ "User", "Beneficiary", "Vendor List", "Vendor Approve", "Employee", "Invoice", "View Invoice", "Approve Invoice", "Payment", "Payment Create", "Payment Approve", "Settings",
	                          "Edit Registration Details", "Bank Detail", "Set Approvers", "Role Access", "Payment Terms" ];
	
	
	$http.get('/role-access/roleList' ,{
		
	}).success(function(data, status) {
		console.log('All role data = '+ data)
		$scope.vm.roleList = data;
		
		$scope.getData(data);
		
	});
	
	$scope.userPermissions = {};
	
	$scope.getData = function(Lists) {
		if(Lists) {
			angular.forEach(Lists, function(value, key) {
				  console.log(key + ': ' + value.roleDesciption +' : '+value.roleName);
				  
				  $scope.userPermissions[value.roleName] = value.roleDesciption;
			});
		}
		
		console.log($scope.userPermissions);
	}
	
	
	
	// Start Add New Role
	$scope.role = {};
	
	$scope.addNewRole = function(data){
		console.log('In addNewRole method Role assign are : ' + data);

		var save = {};
		save.params = [];
		angular.forEach(data.accessRole, function(value, key){
		   //  console.log(key + ': ' + value);
		     if(value == true){
		    	 save.params.push($scope.permissionList[key]);
		     }
		});
		
		console.log('final value for save : ' + save.params);
		
		$scope.role.submitted = true;
		if ($scope.form.$invalid) {
			return;
		}
		console.log('Going to save role with role name : ' + $scope.role.roleName + ' And description : ' +  save.params);
		var postData = {
				roleName : $scope.role.roleName,
				selectedPermissionList : save.params
				//roleDesciption : save.roles
			};
		 $http({
			 method : 'POST',
			 url : '/role-access/addNewRole',
			 data : postData,
			 }).then(function(response) {
					if (response.status == 200) {
						$scope.showMessage = true;
						$scope.resetRole();
						showInfoMessage("You have successfully create role.");
					}else{
						$scope.showMessage = true;
						showInfoMessage(response.data);
					}
				});
		 	 $scope.resetRole = function() {
		 		 $scope.masterReset = {};
		 		 $scope.role = angular.copy($scope.masterReset);
		 	 };
	}
	// End Add New Role
	
	
	// Start Show Error and Info Message
	function showErrorMessage(errorMessage) {
		clearMessages();
		$scope.vm.errorMessages.push({
			description : errorMessage
		});
	}

	function clearMessages() {
		$scope.vm.errorMessages = [];
		$scope.vm.infoMessages = [];
	}

	function showInfoMessage(infoMessage) {
		clearMessages();
		$scope.vm.infoMessages.push({
			description : infoMessage
		});

	}
// End Show Error and Info Message
	
	
} ]);