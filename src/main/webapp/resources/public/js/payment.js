var app = angular.module('myPaymentApp', [ 'angularFileUpload' ]);

app.controller('PaymentCreateCtrl',['$scope','$http', function($scope, $http) {
	

							$scope.vm = {
								approvedVenList : [],
								venInvoiceList : [],
								paymentList : [],
								invoiceAmtTotal : 0,
								paymentAmtTotal : 0
							}
	

							$scope.payment = {};
							$scope.dueDateFlag = 1;
							$scope.invoiceAmountFlag = 1;
							$scope.paymentApprovalReqList = [];
							$scope.dueInvoiceAmt ='';
							$scope.payTotalRowWise = '';
							$scope.noteForApprover = '';
							$scope.chargesRate = 5;
							$scope.payReleaseTotal = 0;
							$scope.totalChargesCal =0;
							$scope.releasePayDate = '';
							$scope.searchPay = {
									fromDate : '',
									toDate : '',
									searchByName : '',
									approvalStatus : 0,
									fromAmt : '',
									toAmt : ''
							}
							$scope.paymentReleaseReqList = [];

							// Start code to fetch all Payment list
							callToGetPaymentList();
							function callToGetPaymentList(){
								$http.get('/payment/paymentList', {
	
								}).success(function(data, status) {
									$scope.vm.paymentList = data;
									$scope.payHistory = data;
									$scope.getPaymentApprover();
								});
							}
	                    // End code to fetch Payment list
	
							$scope.getPaymentInfo = function(paymentObj) {
								$scope.payDetail = paymentObj;
								$scope.getValueToShowUploadedFile(paymentObj.paymentId, 'paymentDetail');
								$scope.auditTrailLog(paymentObj.paymentId);
							}
    

							$scope.auditTrailLog = function(paymentId) {
								var parameters = {};
								parameters.refID = paymentId;
								parameters.refType = 'paymentDetail';
								$http.get('/detailRegistration/viewAuditData',{
												params : parameters
											}).success(function(data, status, headers,config) {
												$scope.auditLogDetail = data;
											})
							  }
	
	

					// Fetch Approved Vendor List
					    	$scope.getApprovedVendor = function() {
					    		$http.get('/payment/getDataForBeneficiary' ,{								
								}).success(function(data, status) {
									$scope.vm.approvedVenList = data;
								})		
							}

						   $scope.getPaymentApprover = function() {
								$http.get('/payment/paymentApprover', {})
										.success(function(data, status) {
											$scope.paymentApprover = data;
										});
						  	}
						   
						   $scope.findInvoiceBasedOnInvoiceNo = function(){
							   if($scope.payment.invoiceNo != undefined && $scope.payment.invoiceNo != ''){
								   console.log('invoiceNo     '+$scope.payment.invoiceNo);
								   var parameters = {};
								   parameters.invoiceNo = $scope.payment.invoiceNo;
								   $http.get('/payment/getInvoiceObjBasedOnInvoiceNo',{
										params : parameters
									}).success(function(data, status) {
										if(data != undefined && data != ''){
											$scope.payment.dueDate = data.dueDate;
											$scope.payment.invoiceAmount = data.netInvoiceAmount;
											$scope.dueDateFlag = 1;
											$scope.invoiceAmountFlag = 1;											
										}else{
											$scope.dueDateFlag = 0;
											$scope.invoiceAmountFlag = 0;
											$scope.payment.dueDate = '';
											$scope.payment.invoiceAmount = '';
										}
										});
						          }
							   }
	
	// Fetch Invoice Details of Vendor
							/*$scope.getInvoiceForVendor = function(vendorId) {
								console.log('Selected Vendor Id = ' + vendorId)
								var parameters = {};
								parameters.vendorId = vendorId;
								$http.get('/payment/getInvoiceListForVendor',{
													params : parameters
											}).success(function(data, status) {
													$scope.vm.venInvoiceList = data;
												});
							   }*/
	

							$scope.getInvoiceObj = function(invoiceId) {
								var invoice = {};
								angular.forEach($scope.vm.venInvoiceList,
										function(value, key) {
											if (value.invoiceID == invoiceId) {
												invoice = value;
											}
										});

								console.log(invoice);

								$scope.payment.dueDate = (invoice) ? invoice.dueDate: '';
								$scope.payment.amount = (invoice) ? invoice.amount: '';
							}
	
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
	
	
	// Start Manage Payment Approval list
	                        $scope.selectedPaymentApproverList = [];

							$scope.addValueToApproverPayment = function() {
								var selectedAppendList = [];
								var selectedVal = '';

								$('#list1 li').each(function() {
													selectedVal += $(this).find('input[type="checkbox"]:checked').val();
													var checkedval = $(this).find('input[type="checkbox"]:checked').val();
													if (checkedval) {
														selectedAppendList.push(parseInt(checkedval));
													}
												});
								if (selectedAppendList == '') {
									alert('Please select value in Target list to move in Selected List');
									return;
								}
								$.each($scope.paymentApprover, function(key,val) {
									if ($.inArray(val.userId,selectedAppendList) != -1) {
										$scope.selectedPaymentApproverList.push(val);
									}
								});

								$.each($scope.selectedPaymentApproverList,function(key, val) {
											var index = $scope.paymentApprover.indexOf(val);
											if (index !== -1) {
												$scope.paymentApprover.splice(index, 1);
											}
										});

							}
	

							$scope.removeValueToApproverPayment = function() {
								var selectedAppendList = [];
								var selectedVal = '';
								console.log('ttttttttttttttttttttttttt    tt');
								$('#list2 li').each(function() {
													selectedVal += $(this).find('input[type="checkbox"]:checked').val();
													var checkedval = $(this).find('input[type="checkbox"]:checked').val();
													if (checkedval) {
														selectedAppendList.push(parseInt(checkedval));
													}
												});

								if (selectedAppendList == '') {
									alert('please select value in Selected List to move in Target List');
									return;
								}
								$.each($scope.selectedPaymentApproverList,function(key, val) {
											if ($.inArray(val.userId,
													selectedAppendList) != -1) {
												$scope.paymentApprover.push(val);
											}
										});

								$.each($scope.paymentApprover,function(key, val) {
													var index = $scope.selectedPaymentApproverList.indexOf(val);
													if (index !== -1) {
														$scope.selectedPaymentApproverList.splice(index,1);
													}
												});
							}
	                    // End Manage Payment Approval list	
							function doPrevalidateBeforeSavePayment(payment){
								if(payment.vendorId == undefined || payment.vendorId == ''){
									userMsgAlert('Please select Beneficiary name', '');
									return false;
								}
								if(payment.invoiceAmount != undefined && payment.invoiceAmount != ''){
									if(isNaN(payment.invoiceAmount)){
										userMsgAlert('Please input valid invoice amount', '');
										return false;
									}
								}
								if(payment.paymentAmount == undefined || payment.paymentAmount == ''){
									userMsgAlert('Payment amount can not be left empty', '');
									return false;
								}else{
									if(isNaN(payment.paymentAmount)){
										userMsgAlert('Please input valid payment Amount', '');
										return false;
									}
								}
								if(payment.paymentProcessDate == undefined || payment.paymentProcessDate == ''){
									userMsgAlert('Please select Process date', '');
									return false;
								}
								if(payment.paymentDate == undefined || payment.paymentDate == ''){
									userMsgAlert('Please select payment Date', '');
									return false;
								}
								if(payment.paymentDate < payment.paymentProcessDate){
									userMsgAlert('Payment Date can not be greater then process date', '');
									return false;
								}
								return true;
							}

	                    // Start Add New Payment
							$scope.addNewPayment = function(payment, saveRequest) {
								if(!doPrevalidateBeforeSavePayment(payment)){
									return;
								}
								if (saveRequest == 'sendForApproval' && $scope.selectedPaymentApproverList.length == 0) {
									$scope.error = "Please select value inTarget List for payment approve";
									return;
								}
								if (saveRequest == 'draft'	&& $scope.selectedPaymentApproverList.length != 0) {
									if (confirm("'SAVE AS DRAFT' function can not save the approver List. Press OK to still want to continue"
											+ " or press Cancel and Click on 'SEND FOR APPROVAL' function to also save approver List")) {
										$scope.selectedPaymentApproverList = [];
										createPaymentCommonBlock(payment);
									}
								}else{
									payment.selectedPaymentApproverList = $scope.selectedPaymentApproverList;
									createPaymentCommonBlock(payment);
								}
								
								/*var postData = {
									vendorId : $scope.payment.vendorId,
									invoiceId : $scope.payment.invoiceId,
									paymentAmount : $scope.payment.paymentAmount,
									paymentProcessDate : $scope.payment.paymentProcessDate,
									paymentDate : $scope.payment.paymentDate,
									remarks : $scope.payment.remarks,
									selectedPaymentApproverList : $scope.selectedPaymentApproverList,
									newAddedDocListForPayment :$scope.addMoreFiles
								};
								$http({
									method : 'POST',
									url : '/payment/addPayment',
									data : payment

								}).then(function(response) {
										if (response.status == 200) {
											userMsgAlert('','You have successfully create payment.');
											$scope.resetPayment();
											} else {
												userMsgAlert(response.data, '');
											}
										}); */
							}
							// End Add New Payment
							
							function createPaymentCommonBlock(payment){
								$http({
									method : 'POST',
									url : '/payment/addPayment',
									data : payment

								}).then(function(response) {
										if (response.status == 200) {
											$('#myModalPayment .close').click();
											callToGetPaymentList();
											userMsgAlert('','You have successfully create payment.');
											$scope.resetPayment();
											} else {
												userMsgAlert(response.data, '');
											}
										});
							 }
							
							$scope.onProcessDateChange = function(obj){
							   if(obj.paymentProcessDate != undefined && obj.paymentProcessDate!= ''){
								   obj.paymentDate = convertDateToSystemFormat(obj.paymentProcessDate, 1);							   
							   }  	
						    }
							
							// date conversion common methoddd......
							function convertDateToSystemFormat(defaultDate,	increaseDayCount) {
								var dateParts = defaultDate.split("/");
								var dateObject = new Date(dateParts[2],dateParts[1] - 1, dateParts[0]);
								dateObject.setDate(+dateObject.getDate() + +increaseDayCount);
								var dd = (dateObject.getDate() < 10 ? '0'+ dateObject.getDate() : dateObject.getDate());
								var mm = dateObject.getMonth() + 1; // January is 0!
								mm = (mm < 10 ? '0' + mm : mm);
								var yyyy = dateObject.getFullYear();
								var dateAfterConversion = dd + '/' + mm + '/' + yyyy;
								return dateAfterConversion;
							}
						// end heree
							
							$scope.resetPayment = function() {
								$scope.dueDateFlag = 1;
								$scope.invoiceAmountFlag = 1;
								$scope.masterReset = {};
								$scope.DocViewObjList=[];
								$scope.selectedPaymentApproverList = [];
								$scope.payment = angular.copy($scope.masterReset);
							};

							// Start Add Files
							$scope.file ={};
							$scope.addMoreFiles = [];
							$scope.DocViewObjList=[];
							$scope.successMsg='';
							$scope.error = '';
							
							$scope.setAttributeForFileUpload = function(value) {
								$scope.uploadFileType = value;
								console.log($scope.uploadFileType);
							}
							
							
							$scope.addManualFileForPayment = function(addFiles){								
									var data = {};					
									if ($scope.file.manuallyFiles) {
										data.manuallyGivenFileName = addFiles.manuallyGivenFileName;
										data.fileName = $scope.file.manuallyFiles;
										$scope.addMoreFiles.push(data);
										$scope.DocViewObjList.push(data);
										$scope.addFiles = {};	
										$scope.file.manuallyFiles ='';
										angular.element('.otherdoc_set input[type="file"]').val('');
									}															
								}
							
						      $scope.batchFileUpload = function(){
								var parameters = {};
								if($scope.file.batchFiles){
									parameters.fileName = $scope.file.batchFiles;								
									$http.get('/payment/batchUploadForPayment', {
										   params: parameters
									    }).success(function(data, status) {
											console.log(data);
											if(status == 200){												
											  $scope.batchError = data;
											  if($scope.batchError == undefined || $scope.batchError.length == 0 || $scope.batchError == ''){
												  $('#myModalSubmiterror .close').click();
												  callToGetPaymentList();
												  $scope.successMsg="File upload successfully.";
												  $scope.error = "";												  
											   }else{
												   $scope.successMsg='';
												   $scope.error = '';
											   }
											}else{
												$('#myModalSubmiterror .close').click();
												$scope.error ="File uplodation fail. Please try again";
												$scope.successMsg='';
											}
										  });
									   angular.element('.otherdoc_set input[type="file"], .batch_upload input[type="file"]').val('');
								     }else{
								    	 $('#myModalSubmiterror .close').click();
								    	 $scope.error ="Please upload the file first."; 
								    	 $scope.successMsg ='';
								    	 
								    	 
								    	 
								     }
								  //  $interval(function(){$('#msgTimeOut').hide();}, 6000);
								}
	                    // End Add Files
						      
						      
						    $scope.getValueToShowUploadedFile = function(refId, referenceType) {
								  var parameters = {};
								  parameters.orgtionID = refId;
								  parameters.referenceType = referenceType;
								
								   $http.get('/detailRegistration/uploadedFileDataView', {
									   params: parameters}).success(function(data, status, headers,config) {
												$scope.DocViewObjList = data;
											})						
								   }
						    
						    $scope.getApprovalLogInfo = function(paymentId){
								var parameters = {};
								parameters.paymentId = paymentId;
								$http.get('/payment/approvalLogData', {
									   params: parameters
									   }).success(function(data, status) {
										   $scope.appLogList = data;
								 })
								
							  }
						    
						   $scope.cancelCreatedPayment = function(payId){
							   $scope.payIdForCancelPayment = payId;
							   console.log($scope.payIdForCancelPayment);
							   $('#btn-open-modal').click();
							   
						   }
						   
						   $scope.finalSubmitToCancelPayment = function(){
							    var parameters = {};
							    parameters.payID = $scope.payIdForCancelPayment;
								$http({
									method : 'PUT',
									url : '/payment/cancelSelectedPayment',
									params : parameters,
								}).then(function(response) {
									if(response.status == 200){
										$('#btn-open-modal .close').click();
										userMsgAlert('', 'You have Successfully cancel the selected payment');
										callToGetPaymentList();										
									}else{
										$('#btn-open-modal .close').click();
										$scope.successMsg='';
										$scope.error = response.data;
									}
									
								})
						   }
						   

						$scope.getVendorInfo = function(vendorID) {
								var parameters = {};
								parameters.vendorID = vendorID;
								$http.get('/vendorInfo/getVendorOnID', {
									params : parameters
								}).success(function(data, status, headers, config) {
											$scope.ven = data[0];
										})
								$scope.auditTrailView(vendorID, 'vendorDetail');

							}
						
						
						$scope.getInvoiceInfo = function(invoiceId){
							var parameters = {};
							parameters.invId = invoiceId;
							$http.get('/create-invoice/invoiceDataForView', {
								    params: parameters
								}).success(function(data, status, headers,config) {
									  $scope.invObj = data;
									})	
							$scope.getValueToShowUploadedFile(invoiceId, 'invoiceDetail');
							$scope.auditTrailView(invoiceId, 'invoiceDetail');						
						   }
						
						
						$scope.auditTrailView = function(venDetailId, refType) {
							$scope.auditData={};
							var parameters = {};
							parameters.refID = venDetailId;
							parameters.refType = refType;
							$http.get('/detailRegistration/viewAuditData', {
								   params: parameters
							 }).success(function(data, status, headers,config) {
									 $scope.auditData = data;
								})
						 }
						
						

						$scope.addValueForEditPayment = function(index, checkBoxValue, payAmt, proDate, invAmt, payIDs) {
								if (checkBoxValue) {
									if (payAmt && proDate) {
										var paymentApproval = '';
										var paymentApprovale = {
											'payAmt' : payAmt,
											'proDate' : proDate,
											'payID' : payIDs
										};
										$scope.paymentApprovalReqList.push(paymentApprovale);
										$('.proDate')[index].disabled = true;
										$('.payAmtID')[index].disabled = true;
										$scope.dueInvoiceAmt = +$scope.dueInvoiceAmt + +invAmt;
										$scope.payTotalRowWise = +$scope.payTotalRowWise + +payAmt;
									} else {
										$('.payAmtID')[index].disabled = false;
										$('.proDate')[index].disabled = false;
										$('.tocheck')[index].checked = false;
										if (payAmt == undefined	|| payAmt == null || payAmt == '') {
											userMsgAlert("Payment Amount field have invalid value",'');
										}
										if (proDate == undefined || proDate == null || proDate == '') {
											userMsgAlert("Process Date must be have some value",'');
											// $scope.error="Process Date must be have some value";
										}
									}
								} else {
									$scope.dueInvoiceAmt = (+$scope.dueInvoiceAmt - +invAmt).toFixed(2);
									$scope.payTotalRowWise = (+$scope.payTotalRowWise - +payAmt).toFixed(2);
									for (var i = 0; i < $scope.paymentApprovalReqList.length; i++) {
										var obj = $scope.paymentApprovalReqList[i];
										if (obj.payID == payIDs) {
											$scope.paymentApprovalReqList.splice(i, 1);
										}
									}
									$('.payAmtID')[index].disabled = false;
									$('.proDate')[index].disabled = false;
									/*
									 * $scope.dueInvoiceAmt = (+$scope.dueInvoiceAmt - +invAmt).toFixed(2);
									 * $scope.payTotalRowWise = (+$scope.payTotalRowWise - +payAmt).toFixed(2); var todelete=
									 * $scope.paymentApprovalReqList.indexOf($scope.paymentApprovalReqList)
									 * $scope.paymentApprovalReqList.splice(todelete,1)
									 * $('.payAmtID')[index].disabled=false;
									 * $('.proDate')[index].disabled=false;
									 */
								}
								console.log($scope.paymentApprovalReqList);
							}
						
						
						$scope.savePaymentAsDraftOrSendForApproval = function(saveRequest) {
								$scope.error = '';
								var parameters = {};
								console.log($scope.selectedPaymentApproverList.length);
								console.log(saveRequest);
								console.log($scope.noteForApprover);
								console.log(+$scope.paymentApprovalReqList);
								if ($scope.paymentApprovalReqList == undefined	|| $scope.paymentApprovalReqList == null
										|| $scope.paymentApprovalReqList == '') {
									userMsgAlert('Please select any of payment to save from table', '');
									return;
								}
								if(saveRequest == 'sendApproval'){
									 if($scope.selectedPaymentApproverList.length == 0){
									   console.log('rrrrrrrrrrrrrrrrrr    '+$scope.selectedPaymentApproverList);
									   userMsgAlert('Selected list of Payment approver can not be empty', '');
									   return;
							        }else{
										parameters.approverList = $scope.selectedPaymentApproverList;
									}
								}else{
									parameters.approverList = '';
								}
								parameters.reqListLength = $scope.paymentApprovalReqList.length;
								parameters.noteForApprover = $scope.noteForApprover == undefined ? '' : $scope.noteForApprover;
								parameters.requestList = $scope.paymentApprovalReqList;
								parameters.saveCondition = saveRequest;								
								console.log($scope.selectedPaymentApproverList);
								if(saveRequest == 'draft' && $scope.selectedPaymentApproverList.length != 0){
									if (confirm("'SAVE AS DRAFT' function can not save the approver List. Press OK to still want to continue" +
											" or press Cancel and Click on 'SEND FOR APPROVAL' function to also save approver List") == true) {
										$scope.updatePaymentCommonBlock(parameters);										
									}
								}else{
									$scope.updatePaymentCommonBlock(parameters);
								}
							}
						
						
						$scope.updatePaymentCommonBlock = function(parameters){
							$http({
								method : 'POST',
								url : '/payment/savePaymentAsDraftOrSendForApproval',
								params : parameters
								}).then(function(response) {
									if (response.status == 200) {
										userMsgAlert('', 'Payment modified successfully');
										callToGetPaymentList();
										$scope.paymentApprovalReqList =[];
										$scope.selectedPaymentApproverList = [];
										$scope.noteForApprover = ''; 
									 } 
							  });
						}
						
						$scope.paymentReleaseTriger = function(){
							$scope.resetSearchCondittion();
							$http.get('/payment/getPaymentReleaseList', {
								
							}).success(function(data, status, headers, config) {
										$scope.payRelease = data;
									})
									
						    $http.get('/payment/getAcctForPayRelease', {
								
							}).success(function(data, status, headers, config) {
										$scope.accNoList = data;
									})				
							 
						  }
						
						$scope.addValueForEditPaymentRelease = function(index, checkBoxValue, payAmt, payIDs){
							//console.log('cekcbox selected and index is   '+index+"  checkBoxValue   "+checkBoxValue +" payAmt  "+payAmt+"  payID  "+payID)
							if(checkBoxValue){
								var paymentApprovale={'payID':payIDs};
								$scope.paymentReleaseReqList.push(paymentApprovale);
								$scope.payReleaseTotal = (+$scope.payReleaseTotal + +payAmt).toFixed(2);
								$scope.totalChargesCal = (+$scope.totalChargesCal + +$scope.chargesRate).toFixed(2);
							}else{
								for (var i = 0; i < $scope.paymentReleaseReqList.length; i++) {
									var obj = $scope.paymentReleaseReqList[i];
									if (obj.payID == payIDs) {
										$scope.paymentReleaseReqList.splice(i, 1);
									}
								}
								$scope.payReleaseTotal = (+$scope.payReleaseTotal - +payAmt).toFixed(2);
								$scope.totalChargesCal = (+$scope.totalChargesCal - +$scope.chargesRate).toFixed(2);
								
							}	
							console.log($scope.paymentReleaseReqList);
						 }
						
						
						$scope.processDateChangeInReleasePayment = function(releaseProDate){
							  $scope.releasePayDate = new Date();
							  $scope.releasePayDate.setDate(new Date(releaseProDate).getDate() + 1);
							  var dd = $scope.releasePayDate.getDate();
						      var mm = $scope.releasePayDate.getMonth()+1; //January is 0!
							  var yyyy = $scope.releasePayDate.getFullYear();
							  var today = dd+'/'+mm+'/'+yyyy;
							  $scope.releasePayDate = today;
								
							}
						
							function myFunction(date) {
								var dd = date.getDate();
								if(dd <= 10){
									dd = '0' + dd;									
								}
						        var mm = date.getMonth() + 1; //January is 0!
							    var yyyy = date.getFullYear();
							    var finalDate = yyyy+'-'+mm+'-'+dd;
								console.log('return date is    '+finalDate);
								return finalDate;                // Function returns the product of a and b
							}
					
						$scope.getSearchesForPayment = function(trigerCondition){
								$scope.error = '';
								var fromDate = '';
								var toDate = '';
								if($scope.searchPay.fromDate || $scope.searchPay.toDate || $scope.searchPay.searchByName ||
										$scope.searchPay.approvalStatus  || $scope.searchPay.fromAmt  || $scope.searchPay.toAmt){
									if($scope.searchPay.fromDate != undefined && $scope.searchPay.toDate != undefined){
										if($scope.searchPay.fromDate != '' && $scope.searchPay.toDate!= ''){
											if(new Date($scope.searchPay.fromDate) > new Date($scope.searchPay.toDate)){
												userMsgAlert('From date is not be greater then to date in search criteria', '');
												return;
											}
											fromDate = myFunction(new Date($scope.searchPay.fromDate));
											toDate = myFunction(new Date($scope.searchPay.toDate));										
										}else if($scope.searchPay.fromDate!= '' && $scope.searchPay.toDate == ''){
											fromDate = myFunction(new Date($scope.searchPay.fromDate));
											toDate = myFunction(new Date());
										}else if($scope.searchPay.fromDate == '' && $scope.searchPay.toDate != ''){
											toDate = myFunction(new Date($scope.searchPay.toDate));
										}
									}
								    var parameters = {};
									parameters.fromDate  = fromDate;
									parameters.toDate  = toDate;
									
									if($scope.searchPay.searchByName == undefined || $scope.searchPay.searchByName == ''){
										parameters.searchName  = '';
									}else{
										parameters.searchName  = $scope.searchPay.searchByName;
									}
									if($scope.searchPay.approvalStatus == undefined || $scope.searchPay.approvalStatus == ''){
										parameters.approvalStatus  = 0;
									}else{
										parameters.approvalStatus  = $scope.searchPay.approvalStatus;
									}
									parameters.searchCondition  = trigerCondition;
									if($scope.searchPay.fromAmt == undefined || $scope.searchPay.fromAmt == ''){
										parameters.fromAmt  = 0;
									}else{
										parameters.fromAmt  = $scope.searchPay.fromAmt;
									}
									if($scope.searchPay.toAmt == undefined || $scope.searchPay.toAmt == ''){
										parameters.toAmt  = 0;
									}else{
										parameters.toAmt  = $scope.searchPay.toAmt;
									}
									$http.get('/payment/searchForCreatePayment', {
										params : parameters
									}).success(function(data, status, headers,config) {
										fetchDataConditionForViewInSearch(trigerCondition, data);
										})							
									
								}else{
									userMsgAlert('Please enter the valid value to search', '');
								}
							
					    	}
						
							function fetchDataConditionForViewInSearch(condition, data) {
								if(condition == 'Release'){
									$scope.payRelease = data;									
								}else if(condition == 'createPayment'){
									$scope.vm.invoiceAmtTotal = 0;
									$scope.vm.paymentAmtTotal = 0;
									$scope.vm.paymentList = data;									
								}else if(condition == 'History'){
									$scope.payHistory = data;
								}
								
							}
							
							
						 $scope.resetSearchCondittion = function(){
								$scope.searchPay ={};
							 }
					 
					 
						 $scope.getHistoryPaymentData = function(){
							 $scope.resetSearchCondittion();
								$http.get('/payment/getPaymentHistoryList', {
									
								}).success(function(data, status, headers, config) {
									    $scope.payHistory = data;
									})
						  }
					 
							 function userMsgAlert(errorMsg, successMsg){
								$scope.successMsg='';
								$scope.error = '';
								$scope.successMsg=successMsg;
								$scope.error = errorMsg;
								 $('#msgTimeOut').show();
								    setTimeout(function() {
										$('#msgTimeOut').hide();
									}, 10000);
							 }
					
						 
						$scope.finalReleaseOfPayment = function(){
							console.log($scope.paymentReleaseReqList);
							if($scope.paymentReleaseReqList == undefined || $scope.paymentReleaseReqList == ''){
								userMsgAlert('Please first select payment for Release', '');
							}
							var parameters = {};
						    parameters.payLst = $scope.paymentReleaseReqList;
							$http({
								method : 'PUT',
								url : '/payment/finalPaymentRelease',
								params : parameters,
							}).then(function(response) {
								if(response.status == 200){
									userMsgAlert('', 'You have Successfully Release the selected payment');
									$scope.paymentReleaseTriger();										
								}								
							})								
						}
						
						$scope.generateOTPForPaymentRelease = function(relAmount){							
							console.log($rootScope.phonNoForOTP);
							if(accNo == undefined || accNo == ''){
								$rootScope.userMsgAlert("Before OTP verification you must have to specify the Account No", '');
								return;
							}								
							$scope.randomGenratedOTP = Math.floor(Math.random()*900000) + 100000;
                            var data = "method=SendMessage&send_to="+$rootScope.phonNoForOTP+"&msg=You have initiated fund transfer for Rs. " 
							+relAmount+" that needs an OTP. NEVER SHARE IT WITH ANYONE. PaymentVista NEVER CALLS TO VERIFY IT. The OTP is " +
							$scope.randomGenratedOTP+"&msg_type=TEXT&userid=2000161437&auth_scheme=plain&password=" +
							"Check@123&v=1.1&format=text"
							$rootScope.OTPGatewayVerification(data);							
						}
					 
					 
	

	
            } ]);


app.controller('paymentApprovalCTRL',['$scope','$http', function($scope, $http) {
			console.log('paymentApprovalCTRL.....................');
			$scope.approveDeclinedFlag = true;
			
			
			$http.get('/payment/pendingPaymentApproval' ,{
	
			   }).success(function(data, status) {
					  $scope.pendingPayApprovalList = data;
			   });
	
	
			$scope.getVendorInfo = function(vendorID){
		    	var parameters = {};
				parameters.vendorID = vendorID;
			  $http.get('/vendorInfo/getVendorOnID', {
				    params: parameters
				}).success(function(data, status, headers,config) {
					  $scope.ven = data[0];
					})
	
				$scope.auditTrailView(vendorID, 'vendorDetail');
	
		    }
	
			$scope.auditTrailView = function(venDetailId, refType) {
				var parameters = {};
				parameters.refID = venDetailId;
				parameters.refType = refType;
			 $http.get('/detailRegistration/viewAuditData', {
				    params: parameters
				}).success(function(data, status, headers,config) {
					  $scope.auditData = data;
					})
			}
			
			$scope.approvePendingPayment = function(paymentAppId){
				console.log('payment id is       '+paymentAppId);
				$scope.approveDeclinedFlag = true;
				$scope.approvalIdForFianlSubmit = paymentAppId;		
				
			}
			
			$scope.submitApprovalPayment = function(paymentIdForApproval,comment){
				console.log('invoiceIdForApproval    '+paymentIdForApproval);
				console.log('comment    '+comment);
				if(comment == undefined || comment == ''){
					$scope.commenterror="Please write something in the comment section";
					return;
				} 
				var parameters = {};
				parameters.comment = comment;
				parameters.payAppID = paymentIdForApproval;
				$http({
					method : 'PUT',
					url : '/payment/approvePendingPaymentApp',
					params : parameters
				}).then(function(response) {
					if(response.status == 200){
						$('#myModalComment .close').click();
						$scope.commenterror='';
						$scope.comment='';
						$http.get('/payment/pendingPaymentApproval' ,{
							
						   }).success(function(data, status) {
								  $scope.pendingPayApprovalList = data;
						   });
					}
					
				})	
			  }
			
			$scope.viewPaymentDetail = function(payObj){
				$scope.pay = payObj;
				$scope.auditTrailView($scope.pay.paymentId, 'paymentDetail');
			}
			
			$scope.viewApprovalHostory = function(){
				console.log('in the approval hosty panelllllllllll   555555555555555   ');
				$http.get('/payment/paymentList' ,{
					
				}).success(function(data, status) {
					console.log('All Payment data = '+ data)
					$scope.payList = data;
				});
			}
		
			
			$scope.declinedPendingPayment = function(paymentAppId){
				console.log('payment id is in declineddddd      '+paymentAppId);
				$scope.approveDeclinedFlag = false;
				$scope.approvalIdForFianlSubmit = paymentAppId;		
				
			}
			
			$scope.submitDeclinedPayment = function(paymentIdForDeclined,comment){
				if(comment == undefined || comment == ''){
					$scope.commenterror="Please write something in the comment section";
					return;
				} 
				var parameters = {};
				parameters.comment = comment;
				parameters.payAppID = paymentIdForDeclined;
				$http({
					method : 'PUT',
					url : '/payment/declinedPendingPaymentApp',
					params : parameters
				}).then(function(response) {
					if(response.status == 200){
						$('#myModalComment .close').click();
						$scope.commenterror='';
						$scope.comment='';
						$scope.approveDeclinedFlag = true;
						$http.get('/payment/pendingPaymentApproval' ,{
							
						   }).success(function(data, status) {
								  $scope.pendingPayApprovalList = data;
						   });
					}
					
				})
			}
			
			$scope.getApprovalLogInfo = function(paymentId){
				var parameters = {};
				parameters.paymentId = paymentId;
				$http.get('/payment/approvalLogData', {
					   params: parameters
					   }).success(function(data, status) {
						   $scope.appLogList = data;
				 })
				
			}
	
	
    }]);



app.controller('FileUploadCtrl',['$scope','FileUploader',function($scope, FileUploader) {
				var uploader = $scope.uploader = new FileUploader({
					method : 'POST',
					url : '/fileUploadProcess/fileUploadOnServer',
					autoUpload : true,
				});
				
				if ($scope.uploadFileType == 'manualFileSelected') {
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
							if ($scope.uploadFileType == 'manualFileSelected') {
								$scope.file.manuallyFiles = response;
								console.log($scope.file.manuallyFiles);
							} else if ($scope.uploadFileType == 'batchUpload') {
								$scope.file.batchFiles = response;
								console.log($scope.file.manuallyFiles);
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