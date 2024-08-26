var app = angular.module('paymentTermApp', []);

app.controller('paymentTermCntrl',['$scope','$http',function($scope, $http) { 
	
	// Start code to fetch all Role list   
	$scope.vm = {
			paymentTermsList : [],
	}
	
	// Start Code to Get All Payment Terms
	//$scope.getPaymentTrems = function() {
		console.log('In paymentTerm.js getPaymentTrems js method !!!!!!!!!!!!!!!!!!!!!!!!!');
		$http.get('/role-access/paymentTerms' ,{
			
		}).success(function(data, status) {
			console.log('All Payment Terms = '+ data)
			$scope.vm.paymentTermsList = data;
		});
//	}
	// End Code to Get All Payment Terms
	
	
} ]);