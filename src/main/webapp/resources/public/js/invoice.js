var app = angular.module('myApp', [ 'angularFileUpload' ]);

   app.controller('newInvoiceCntrl',['$scope','$http',function($scope, $http) {
//					    $scope.date = new Date();
//					    $scope.invoiceDate = $filter("date")(Date.now(), 'yyyy-MM-dd');
//					    var numberOfDaysToAdd = 5;
//					    $scope.dueDate = $scope.invoiceDate.setDate($scope.invoiceDate.getDate() + numberOfDaysToAdd);
					    $scope.showMessage = false;
					    var upToDecimalSupport = 2;
						$scope.vm = {};
						$scope.inv = {};
						$scope.ven =[];
						$scope.file ={};
						$scope.addMoreFiles = [];
						$scope.DocViewObjList=[];
						$scope.newAddedDocListForInvoice=[];
						$scope.ms = {
				                errorMessages: [],
				                infoMessages: [],
				            };
						$scope.searchInvoice = {
								fromDate : '',
								toDate : '',
								searchByName : '',
								approvalStatus : 0,
								fromAmt : '',
								toAmt : '',
								invoiceStatusField : false,
								approvalStatusField : false
						}
						$scope.successMsg='';
						$scope.error = '';
						getDataForInvoiceView();
						function getDataForInvoiceView(){
							$http.get('/create-invoice/getDataForView' ,{								
							}).success(function(data, status) {
								$scope.invoiceList = data;
							})
						}
						
							
						// Fetch Payment Terms Type
						function getPaymentTermData(){
				  			$http.get('/vendorInfo/getPaymentTerm',{
								
							}).success(function(data, status, headers,config) {
								$scope.paymentTerm = data;
							});
						}
						
						// Fetch Payment Terms Type
						function getInvoiceType(){
				  			$http.get('/create-invoice/getInvoiceType',{
								
							}).success(function(data, status) {
								$scope.invoiceType = data;
							});
   						}
			  			
						$scope.getAuditTrailData = function(invID) {
								var parameters = {};
								parameters.refID = invID;
								parameters.refType = 'invoiceDetail';
							 $http.get('/detailRegistration/viewAuditData', {
								    params: parameters
								}).success(function(data, status, headers,config) {
									 console.log(data);
									  $scope.auditData = data;
									})
							}
							
						$scope.getFileInfoDoc = function(invID){
							var parameters = {};
							parameters.orgtionID = invID;
							parameters.referenceType = 'invoiceDetail';
						   $http.get('/detailRegistration/uploadedFileDataView', {
								   params: parameters
								   }).success(function(data, status) {
									  $scope.DocViewObjList = data;
							 })
						}
						
						$scope.updateInvoiceInfo = function(invObj){
							if(!validationCheckForSaveInvoice(invObj)){
					    		return;
					    	}
							if ($scope.selectedIncoiveApproverList.length > 0) {
								invObj.selectedIncoiveApproverList = $scope.selectedIncoiveApproverList;
							}
							$http({
								method : 'PUT',
								url : '/create-invoice/updateInvoice',
								data : invObj
							}).then(function(response) {
								console.log(response);
								if (response.status == 200) {
									getDataForInvoiceView();
									$scope.resetCreateInvoicePopUpValue();
									$('#myModalEdit .close').click();
									userMsgAlert('','Selected Invoice has been updated successfully');
								}
							});
							
						}
					    
						$scope.viewInvoice = function(invList){
							console.log('invoice is       '+invList);
							$scope.vm = invList;
							$scope.getFileInfoDoc(invList.invoiceID);
							$scope.getAuditTrailData(invList.invoiceID);
							
						}
						
						$scope.editInvoice = function(invList){
							$scope.vm = invList;
							$scope.getValueForBeneficiaryName();
							$scope.inv.selectedIncoiveApproverList = fetchAlreadySelectedApproverList(invList.invoiceID);
							$scope.vm.taxAmount = ($scope.vm.taxAmount == undefined ? 0 : $scope.vm.taxAmount);
							$scope.vm.amount = ($scope.vm.amount == undefined ? 0 : $scope.vm.amount);
							$scope.vm.totalAmount = (+$scope.vm.taxAmount + +$scope.vm.amount).toFixed(upToDecimalSupport);
						}
						
						function fetchAlreadySelectedApproverList(invoiceID){
							console.log(invoiceID);
							var parameters = {};
							parameters.invId = invoiceID;
							$http.get('/create-invoice/getSelectedApprover', {
								   params: parameters
								   }).success(function(data, status) {
									  $scope.invoiceUserViewInEdit = data;
							 })
						
							
							return;
						}
						
						
						function removeFunction (myObjects,prop,valu)
				        {
				             return myObjects.filter(function (val) {
				              return val[prop] !== valu;
				          });

				        }
						
						$scope.selectedIncoiveApproverList = [];
						
						$scope.addValueToApproverInvoice = function(){
							var selectedAppendList = [];
							var selectedVal = '';
							
							$('#list1 li').each(function(){
								selectedVal += $(this).find('input[type="checkbox"]:checked').val();
								var checkedval = $(this).find('input[type="checkbox"]:checked').val();
								if(checkedval){
									selectedAppendList.push(parseInt(checkedval));
								}
							});
							if(selectedAppendList == ''){
								alert('Please select value in Target list to move in Selected List');
								return;
							}
							console.log('selectedAppendList         '+selectedAppendList);
							
							
							$.each($scope.incoiveApprover, function(key, val){
								if($.inArray(val.appUserID, selectedAppendList) != -1) {
									$scope.selectedIncoiveApproverList.push(val);
								}
							});
							
							$.each($scope.selectedIncoiveApproverList, function(key, val){								
								var index = $scope.incoiveApprover.indexOf(val);								
								if(index !== -1){
									$scope.incoiveApprover.splice(index, 1);
								}
							});
							
						}
						
						$scope.removeValueToApproverInvoice = function(){
							var selectedAppendList = [];
							var selectedVal = '';
							$('#list2 li').each(function(){
								selectedVal += $(this).find('input[type="checkbox"]:checked').val();
								var checkedval = $(this).find('input[type="checkbox"]:checked').val();
								if(checkedval){
									selectedAppendList.push(parseInt(checkedval));
								}
							});
							
							if(selectedAppendList == ''){
								alert('please select value in Selected List to move in Target List');
								return;
							}
							$.each($scope.selectedIncoiveApproverList, function(key, val){
								if($.inArray(val.appUserID, selectedAppendList) != -1) {
									$scope.incoiveApprover.push(val);
								}
							});
							
							$.each($scope.incoiveApprover, function(key, val){								
								var index = $scope.selectedIncoiveApproverList.indexOf(val);								
								if(index !== -1){
									$scope.selectedIncoiveApproverList.splice(index, 1);
								}
							});
						}					
					    
						/*function validationCheckForSaveInvoice(invObj){
							if(invObj.invoiceNumber == undefined){
								userMsgAlert('Please fill the Invoice Number','');
					    		return false;
					    	}
							return true;
						}*/
						
						
						function validationCheckForSaveInvoice(invObj){
							if(invObj.beneficiaryID == undefined){
								userMsgAlert('Please select Beneficiary Name','');
					    		return false;
							}else if(invObj.invoiceType == undefined){
								userMsgAlert('Please select the Invoice Type','');
					    		return false;
							}else if(invObj.invoiceNumber == undefined){
								userMsgAlert('Please fill the Invoice Number','');
					    		return false;
					    	}else if(invObj.invoiceDate == undefined){
								userMsgAlert('Please define Invoice Date','');
					    		return false;
							}else if(invObj.paymentTerm  == undefined){
								userMsgAlert('Please select Payment Term','');
					    		return false;
							}else if(invObj.dueDate == undefined){
								userMsgAlert('Please define Due Date','');
					    		return false;
							}else if(invObj.amount == undefined){
								userMsgAlert('Please input valid Basic Amount','');
					    		return false;
							}
							return true;
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
					    
					    $scope.saveAsDraftInvoice = function(inv){
					    	console.log('wroking to saave invoice in drafttttt  ');
					    	if(!validationCheckForSaveInvoice(inv)){
					    		return;
					    	}
						    if ($scope.selectedIncoiveApproverList.length > 0) {
							  if (confirm("'SAVE AS DRAFT' function can not save the Target Approver List. Press OK to still want to continue"
										+ " or press Cancel and Click on 'CREATE INVOICE' function to also save Target approver")) {
								  $scope.selectedIncoiveApproverList = [];
								  $scope.finalSubmitOfInvoice(inv);
								}
							}else{
								  $scope.finalSubmitOfInvoice(inv);
							}
					    }
					    
					    $scope.createInvoice = function(inv){
					    	console.log('wroking to saave invoice in craeteeeeee  ');
					    	if(!validationCheckForSaveInvoice(inv)){
					    		return;
					    	}
					    	if ($scope.selectedIncoiveApproverList.length == 0) {
					    		userMsgAlert('Please select value in Target Approver List','');
					    		return;
							}
					    	if ($scope.selectedIncoiveApproverList.length > 0) {
					    		inv.selectedIncoiveApproverList = $scope.selectedIncoiveApproverList;
							}
					    	$scope.finalSubmitOfInvoice(inv);
					    }
					    
					    $scope.finalSubmitOfInvoice = function(invObj) { 
						    	console.log('Creating user with first name '+invObj);					   
								if ($scope.DocViewObjList.length > 0) {
									invObj.newAddedDocListForInvoice = $scope.addMoreFiles;
								}
								$http({
									method : 'POST',
									url : '/create-invoice/createInvoice',
									data : invObj,
									headers: {
										"Content-Type": "application/json",
										"Accept": "text/plain, application/json"
									}
								}).success(function (response) {											
									getDataForInvoiceView();
									userMsgAlert('','New Invoice has been created successfully');
									$('#myModalInvoice .close').click();
									$scope.resetCreateInvoicePopUpValue();
										
								 }).error(function (response) { /* display proper error message */
									 userMsgAlert(response, '');
								 });
								
								
								
								
								/*.then(function(response) {
									if (response.status == 200) {
										getDataForInvoiceView();
										userMsgAlert('','New Invoice has been created successfully');
										$('#myModalInvoice .close').click();
										$scope.resetCreateInvoicePopUpValue();
								   }									
								});	*/					
						 }
					    
					    $scope.resetCreateInvoicePopUpValue = function(){
					    	$scope.inv = {};
					    	$scope.vm = {};
					    	$scope.selectedIncoiveApproverList = [];
					    }
					    
					    $scope.addManualFileForVendor = function(addFiles){								
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
							console.log($scope.addMoreFiles);
						}
					    
					    $scope.setAttributeForFileUpload = function(value) {
							$scope.uploadFileType = value;
							console.log($scope.uploadFileType);
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
					    
					    $scope.uploadBatchFile = function(){
							var parameters = {};
							console.log('$scope.file.batchUpload    '+$scope.file.batchFiles);
							parameters.fileName = $scope.file.batchFiles;
							console.log('in the batch file uplaod controolerrrrrrrrrrrr    ++++ ');
							$http({
								method : 'POST',
								url : '/create-invoice/batchUpload',
								params : parameters
							}).then(function(response) {
								if(response.status == 200){
									$scope.showMessage = true;
									//$scope.reset();
									showInfoMessage("File uploaded successfully");
									$http.get('/create-invoice/getDataForView' ,{								
									}).success(function(data, status) {
										$scope.invoiceList = data;
									})
									}else{
										
									}
								});						
						    }
					    
					    function showInfoMessage(infoMessage) {
					    	 clearMessages();
				             $scope.ms.infoMessages.push({description: infoMessage});
				            
				            }
					    
					    function clearMessages() {
			                $scope.ms.errorMessages = [];
			                $scope.ms.infoMessages = [];
			            }
					    
					    $scope.getValueForBeneficiaryName = function(){
					    	$http.get('/create-invoice/getDataForBeneficiary' ,{								
							}).success(function(data, status) {
								$scope.beneficryList = data;
							})
							
							$http.get('/create-invoice/invoiceApprover' ,{								
							}).success(function(data, status) {
								$scope.incoiveApprover = data;
							})
							getPaymentTermData();
					    	getInvoiceType();
							
					    }
					    
					    $scope.vendorInfo = function(vendorID){
					    	var parameters = {};
							parameters.vendorID = vendorID;
						  $http.get('/vendorInfo/getVendorOnID', {
							    params: parameters
							}).success(function(data, status, headers,config) {
								  $scope.ven = data[0];
								})
					    	
					    }

						$scope.searchInvoiceTrigger = function(conditionParam) {
								var parameters = {};
								if (conditionParam.fromDate == '' && conditionParam.toDate == '' && conditionParam.searchByName == ''
										&& conditionParam.fromAmt == ''	&& conditionParam.toAmt == ''	&& conditionParam.invoiceStatusField == false
										&& conditionParam.approvalStatusField == false) {
									userMsgAlert('Please enter the valid value to search','');
									return;
								}
								if (conditionParam.invoiceStatusField == false	&& conditionParam.approvalStatusField == false) {
									if (conditionParam.approvalStatus != 0) {
										userMsgAlert('Please select checkbox of column name either invoice status or approval status','');
										return;
									}
								}
								if (conditionParam.invoiceStatusField != false	|| conditionParam.approvalStatusField != false) {
									if (conditionParam.approvalStatus == 0) {
										userMsgAlert('Please select value for status','');
										return;
									}
								}
								if (conditionParam.fromAmt == undefined	|| conditionParam.toAmt == undefined) {
									userMsgAlert('please select valid value for amount','');
									return;
								} else {
									parameters.fromAmt = (conditionParam.fromAmt != '') ? conditionParam.fromAmt: '0';
									parameters.toAmt = (conditionParam.toAmt != '') ? conditionParam.toAmt: '0';
									if (parameters.fromAmt > parameters.toAmt) {
										userMsgAlert('From amount not be greater then To Amount','');
										return;
									}
								}
								var fromDate = (conditionParam.fromDate == undefined) ? '': conditionParam.fromDate;
								var toDate = (conditionParam.toDate == undefined) ? '': conditionParam.toDate;

								if (conditionParam.fromDate != '' && conditionParam.toDate != '') {
									if (new Date(conditionParam.fromDate) > new Date(conditionParam.toDate)) {
										userMsgAlert('From date is not be greater then to date in search criteria','');
										return;
									}
									fromDate = myFunction(new Date(conditionParam.fromDate));
									toDate = myFunction(new Date(conditionParam.toDate));
								} else if (conditionParam.fromDate != '' && conditionParam.toDate == '') {
									fromDate = myFunction(new Date(conditionParam.fromDate));
									toDate = myFunction(new Date());
								} else if (conditionParam.fromDate == '' && conditionParam.toDate != '') {
									toDate = myFunction(new Date(conditionParam.toDate));
								}

								if (conditionParam.searchByName == undefined || conditionParam.searchByName == '') {
									parameters.searchName = '';
								} else {
									parameters.searchName = conditionParam.searchByName;
								}
								parameters.fromDate = fromDate;
								parameters.toDate = toDate;
								parameters.invoiceStatusField = (conditionParam.invoiceStatusField == true) ? 1 : 0;
								parameters.approvalStatusField = (conditionParam.approvalStatusField == true) ? 1 : 0;
								parameters.approvalStatus = conditionParam.approvalStatus;
								console.log(parameters);
								$http.get('/create-invoice/searchDataForInvoice', {
									params : parameters
								}).success(function(data, status, headers,config) {
										$scope.invoiceList = data;
								})

							}
					    
					    function myFunction(date) {
							var dd = date.getDate();
							if(dd <= 10){
								dd = '0' + dd;									
							}
					        var mm = date.getMonth() + 1; //January is 0!
						    var yyyy = date.getFullYear();
						    var finalDate = yyyy + '-' + mm + '-' + dd;
							console.log('return date is    '+finalDate);
							return finalDate;                // Function returns the product of a and b
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
					    
					    $scope.clearSearchData = function(){
					    	getDataForInvoiceView();
					    	$scope.searchInvoice = {};
					    	
					    }
					    
					    $scope.onPaymentTermChange = function(obj){
							console.log('itemSelected     '+obj.paymentTerm)
						   if(obj.invoiceDate == undefined){
							   userMsgAlert('Please select invoice date first', '');
							   return;
						   }
						   if(obj.paymentTerm != undefined && obj.paymentTerm!= ''){
							   var itemReturn = obj.paymentTerm.split(/[ ,]+/)[1];
							   itemReturn = (itemReturn == undefined ? '0' : itemReturn);
							   obj.dueDate = convertDateToSystemFormat(obj.invoiceDate, itemReturn);							   
						   }  	
					    }
					    
					    $scope.totalAmtCalculationEvent = function(inv){							
							if(inv.amount != null && inv.amount != undefined && inv.amount != '' && inv.amount >0){
								var  calTaxAmt = 0;
								var tdsDeductionAmt = 0;
								var taxPercent = (inv.taxPercent == undefined || inv.taxPercent == '' ? 0 : inv.taxPercent);
								if(taxPercent > 0){
									calTaxAmt = (inv.amount * (taxPercent/100)).toFixed(upToDecimalSupport);
								}
								inv.taxAmount = calTaxAmt;
								inv.totalAmount = (+inv.taxAmount + +inv.amount).toFixed(upToDecimalSupport);
								var tdsDeductionPercnt = (inv.tdsDeductionPrecnt == undefined || inv.tdsDeductionPrecnt == '' ? 0 
								         : inv.tdsDeductionPrecnt);
							    if(tdsDeductionPercnt > 0){
									tdsDeductionAmt = (inv.amount * (tdsDeductionPercnt/100)).toFixed(upToDecimalSupport);
								}
							    inv.tdsDeductionAmt = tdsDeductionAmt;
								var otherDeductionAmtt = (inv.otherDeductionAmt == undefined || inv.otherDeductionAmt == '' ? 0 
								         : inv.otherDeductionAmt);
										 console.log('otherDeductionAmtt      '+otherDeductionAmtt);
										 inv.netInvoiceAmount = (+inv.totalAmount - +inv.tdsDeductionAmt - +otherDeductionAmtt).
								                           toFixed(upToDecimalSupport);
								
							}else{
								userMsgAlert('First please input valid Basic Amount', '');
							}
						}
					    
					  //Satrt Code to Fetch Approval Log Invoice
					     $scope.getAproverLog = function(invoiceID){
					    	  $scope.successMsg='';
					  		  $scope.error = '';
					    	  var parameters = {};
								parameters.invId = invoiceID;
								console.log('In invoice aapproval log js method invoice id = ' + invoiceID);
								
							 $http.get('/create-invoice/approverLog', {
									   params: parameters
									   }).success(function(data, status) {
										   $scope.approverLog = data;
										   $scope.inv.selectedIncoiveApproverList = data;
										   console.log('invoice approval log = ' + data);
								 })
					        }
					      //End Code to Fetch Approval Log Invoice
					     
					     
					     
					     $scope.setInvoiceIdForCancel = function(invoiceID){
					    	 $scope.invoiceIDForCalncelInvoice = invoiceID;
					     }
					     
					     $scope.finalCancelTheInvoice = function(){
					    	 console.log($scope.invoiceIDForCalncelInvoice);
					    	 var parameters = {};
					    	 parameters.invID = $scope.invoiceIDForCalncelInvoice;
					    	 var postData = {
					    			 invoiceID: $scope.invoiceIDForCalncelInvoice,
					             };
					    	 $http.get('/create-invoice/cancelOfInvoice', {
								   params: parameters
								   }).success(function(data, status) {
									   if(status == 200){
										   var statusID = data;
										   if(statusID == 0 || statusID == 5 || statusID == 3  || statusID == 4){
											   $http({
													method : 'PUT',
													url : '/create-invoice/cancelOfInvoiceAfterCheck',
													params: parameters,
													headers: {
									                    "Content-Type": "application/json","Accept": "text/plain"}
												}).then(function(response) {
													if (response.status == 200) {														
														getDataForInvoiceView();
														$scope.resetCreateInvoicePopUpValue();
														userMsgAlert('','Selected Invoice has been updated successfully');
													}else{
														userMsgAlert(response.data,'');
													}													
												});
										   }else{
											  userMsgAlert('Selected invoice can not be cancelled as thier corresponding payment is already Approved','');
										   }
										   $('#myModalDelete .close').click();
									   }else{
										   userMsgAlert('Somthing went wrong','');
									   }
							 });
							 
					    	 
//					    	 $http({
//									method : 'POST',
//									url : '/create-invoice/cancelOfInvoice',
//									data: postData,
//									headers: {
//					                    "Content-Type": "application/json","Accept": "text/plain"}
//								}).then(function(response) {
//									console.log(response);
//									if (response.status == 200) {
//										
////										getDataForInvoiceView();
////										$scope.resetCreateInvoicePopUpValue();
////										userMsgAlert('','Selected Invoice has been updated successfully');
//									}else{
//										userMsgAlert(response.data,'');
//									}
//									$('#myModalDelete .close').click();
//								});
					    	 
					     }

				} ]);
   

   app.controller('invoiceApprovalCntrl', ['$scope','$http',function($scope, $http) {
	   			
	   			$scope.successMsg='';
	   			$scope.error = '';
	   			  $scope.invoiceIdForApproval = '';
			      $http.get('/create-invoice/pendingApprovalInvoice', {}).success(
					    function(data, status) {
						   $scope.pendingApprovalList = data;
					   })
					
		          $scope.viewInvoiceInfo = function(invoiceId){
			    	  $scope.successMsg='';
			   		  $scope.error = '';
			    	  var parameters = {};
						parameters.invId = invoiceId;
					 $http.get('/create-invoice/invoiceDataForView', {
							   params: parameters
							   }).success(function(data, status) {
								  $scope.venId = data;
						 })
						 $scope.getFileInfoDoc(invoiceId);
					     $scope.getAuditTrailData(invoiceId);
			         }
			      
			      $scope.getFileInfoDoc = function(invID){
						var parameters = {};
						parameters.orgtionID = invID;
						parameters.referenceType = 'invoiceDetail';
					 $http.get('/detailRegistration/uploadedFileDataView', {
							   params: parameters
							   }).success(function(data, status) {
								  $scope.DocViewObjList = data;
						 })
					}
			      
			      $scope.getAuditTrailData = function(invID) {
			    	  $scope.successMsg='';
			   		  $scope.error = '';
			    	  var parameters = {};
						parameters.refID = invID;
						parameters.refType = 'invoiceDetail';
					 $http.get('/detailRegistration/viewAuditData', {
						    params: parameters
						}).success(function(data, status, headers,config) {
							  $scope.auditData = data;
							})
					}
			      
			      $scope.vendorInfo = function(vendorID){
			    	  $scope.successMsg='';
			   		  $scope.error = '';
				    	var parameters = {};
						parameters.vendorID = vendorID;
					  $http.get('/vendorInfo/getVendorOnID', {
						    params: parameters
						}).success(function(data, status, headers,config) {
							  $scope.ven = data[0];
							})
					$scope.getAuditTrailData(vendorID);	
				    	
				    }
			      
			      $scope.submitApprovalInvoice = function(invId, comment){			    	 
			    	  if(comment == undefined){
			    		  $scope.commenterror="Please write somthing in the comment section";
			    		  return;
			    	  }
			    	  var parameters = {};
						parameters.invoiceID = invId;
						parameters.comment = comment;
						$http({
								method : 'PUT',
								url : '/create-invoice/updateInvoiceApproval',
								params: parameters
							}).then(function(response) {
								$('#myModalComment .close').click();
								if (response.status == 200) {
									 $scope.successMsg="Invoice Approved successfully.";
								}else{
									$scope.error = "Some problem occured please try again.";
								}
								 $http.get('/create-invoice/pendingApprovalInvoice', {}).success(
										    function(data, status) {
											   $scope.pendingApprovalList = data;
										   })
							
							});	
													
			         }
			      
			      $scope.approveInvoice = function(invoiceID){
			    	  $scope.invoiceIdForApproval =invoiceID;
			    	  	$scope.successMsg='';
			  			$scope.error = '';
					}
			      // Invoice Decline code start
			      $scope.declineInvoice = function(invoiceID){
			    	  $scope.invoiceIdForDecline =invoiceID;
			    	  	$scope.successMsg='';
			  		  	$scope.error = '';
					}
			      
			      $scope.submitDeclineInvoice = function(invId, comment){			    	 
			    	  if(comment == undefined){
			    		  $scope.commenterror="Please write a comment";
			    		  return;
			    	  }
			    	  var parameters = {};
						parameters.invoiceID = invId;
						parameters.comment = comment;
						$http({
								method : 'PUT',
								url : '/create-invoice/updateInvoiceDecline',
								params: parameters
							}).then(function(response) {
								$('#myModalDeclineComment .close').click();
								if (response.status == 200) {
									 $scope.successMsg="Invoice Declined successfully.";
								}else{
									$scope.error = "Some problem occured please try again.";
								}
								$http.get('/create-invoice/pendingApprovalInvoice', {}).success(
									    function(data, status) {
										   $scope.pendingApprovalList = data;
							   })
							  
							});													
			         }
			      // Invoice Decline code end
			      
			      
			      //Satrt Code to Fetch Approval Log Invoice
			      $scope.getAproverLog = function(invoiceApp){
			    	  $scope.successMsg='';
			  		  $scope.error = '';
			    	  var parameters = {};
						parameters.invId = invoiceApp.invoiceID;
						console.log('In invoice aapproval log js method invoice id = ' + invoiceApp.invoiceID);
						
					 $http.get('/create-invoice/approverLog', {
							   params: parameters
							   }).success(function(data, status) {
								   $scope.approverLog = data;
								   console.log('invoice approval log = ' + data);
						 })
			         }
			      //End Code to Fetch Approval Log Invoice
			      
			      
			      // Start Code for Invoice Approval History
					$scope.invoiceAppHistory = function() {
						console.log('In invoiceAppHistory js method')
				    $http.get('/create-invoice/approvalInvoiceHistory', {}).success(
				    		function(data, status) {
				    			$scope.approvalHistoryList = data;
				    			console.log('Approval History Data found it is : ' + data)
				    		})
					}
				// End Code for Invoice Approval History
					
				// Start Code for Invoice Approval Search
					$scope.searchInvoices = function(invc) {
						console.log('In Search Invoice with data : ')
												
						var stDt = (invc.dateFrom != null) ? invc.dateFrom : '';
						var endDt = (invc.dateTo != null) ? invc.dateTo : '';
						var nam = (invc.Name != null) ? invc.Name : '';
						var amFr = (invc.amountFrom != null) ? invc.amountFrom : '0';
						var amTo = (invc.amountTo != null) ? invc.amountTo : '0';					
						
						var parameters = {};
						parameters.dateFrom = stDt;
						parameters.dateTo = endDt;
						parameters.Name = nam;
						parameters.amountFrom = amFr;
						parameters.amountTo = amTo;
						
						$http.get('/create-invoice/searchInvoiceData' ,{
							params: parameters
						}).success(function(data, status) {
							console.log('Return from search invoce data with data = ' + data);
							$scope.pendingApprovalList = data;
						})
					}
				// End Code for Invoice Approval Search	
				
				// Start Code for Invoice Approval History Search
					$scope.searchInvoiceHistory = function(invcHis) {
						console.log('In Search Invoice History with data : ')
												
						var startDt = (invcHis.dateFrom != null) ? invcHis.dateFrom : '';
						var endDt = (invcHis.dateTo != null) ? invcHis.dateTo : '';
						var nam = (invcHis.Name != null) ? invcHis.Name : '';
						var amountFr = (invcHis.amountFrom != null) ? invcHis.amountFrom : '0';
						var amountTo = (invcHis.amountTo != null) ? invcHis.amountTo : '0';					
						
						var parameters = {};
						parameters.dateFrom = startDt;
						parameters.dateTo = endDt;
						parameters.Name = nam;
						parameters.amountFrom = amountFr;
						parameters.amountTo = amountTo;
						
						$http.get('/create-invoice/searchInvcAprvlHistoryData' ,{
							params: parameters
						}).success(function(data, status) {
							console.log('Return from search invoce data with data = ' + data);
							$scope.approvalHistoryList = data;
						})
					}
				// End Code for Invoice Approval History Search						
					
						   
			      

		   } ]);
         
   app.controller('FileUploadCtrl',['$scope','FileUploader',function($scope, FileUploader) {
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