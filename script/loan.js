function manageLoanTypeMaster() {
	var errorMessage = "";
	var status = true;

	if ($jq('#loanName').val() == '') {
		errorMessage = "Please enter Loan/Advance name\n";
		status = false;
		$jq('#loanName').focus();
	}
	if ($jq('#loanType').val() == '0') {
		errorMessage += "Please select Loan/Advance type\n";
		if (status) {
			status = false;
			$jq('#loanType').focus();
		}
	}

	if (status) {
		var requestDetails = {
			"nodeID" : nodeID,
			"loanName" : $jq('#loanName').val(),
			"loanType" : $jq('#loanType').val(),
			"param" : "manage"
		};
		$jq.ajax( {
			type : "POST",
			url : 'loan.htm?'+dispUrl,
			data : requestDetails,
			cache : false,
			success : function(html) {
				$jq('#result').html(html);
				clearLoanTypeMaster();
			}
		});
	} else {
		alert(errorMessage);
	}
}


function editInstalDetails(){
	$jq('#reqDiv').show();
	$jq('#InsDiv').show();
	$jq('#saveInstButton').show();
	$jq('#reqAmount').val('');
	$jq('#requestedInstalments').val('');
	$jq('#requestedInterestInstalments').val('');
}
function saveInstalDetails(historyID, requestID){
	errorMessages="";
	status = true;
	if ($jq.trim($jq('#reqAmount').val()) == '') {
		errorMessages += "Please Enter requested amount \n";
		if (status) {
		status = false;
		$jq('#reqAmount').focus();
		}
	}
	if ($jq.trim($jq('#requestedInstalments').val()) == '') {
		errorMessages += "Please Enter no of installments for principle amount \n";
		if (status) {
		status = false;
		$jq('#requestedInstalments').focus();
		}
	}
	if ($jq.trim($jq('#requestedInterestInstalments').val()) == '') {
		errorMessages += "Please Enter no of installments for Interest amount \n";
		if (status) {
		status = false;
		$jq('#requestedInterestInstalments').focus();
		}
	}
	
	if(status){
		requestDetails = {
				"nodeID" : requestID,
				"param" : "editInstallments",
				"requestedInstalments" : $jq('#requestedInstalments').val() ,
				"requestedInterestInstalments" : $jq('#requestedInterestInstalments').val() ,
				"reqAmount": $jq('#reqAmount').val()
		}
		$jq.post('loanRequest.htm', requestDetails, function(){
			$jq('#reqDiv').parent().find('span').eq(0).text(parseFloat($jq('#reqAmount').val()));
			$jq('#InsDiv').parent().find('span').eq(0).text(parseFloat($jq('#requestedInstalments').val())+parseFloat($jq('#requestedInterestInstalments').val()));
			$jq('#reqDiv').hide();
			$jq('#InsDiv').hide();
		});
		}
	else{
		alert(errorMessages);
	}
}
function clearLoanTypeMaster() {
	$jq('#loanName').val('');
	$jq('#loanType').val('0');
	nodeID = "";
}

function editLoanTypeMaster(id, loanName, loanType) {
	nodeID = id;
	$jq('#loanName').val(loanName);
	$jq('#loanType').val(loanType);
}

function deleteLoanTypeMaster(id) {
	var del = confirm("Do you want to delete this item?");
	if (del == true) {
		var requestDetails = {
			"nodeID" : id,
			"param" : "delete"
		};
		$jq.ajax( {
			type : "POST",
			url : 'loan.htm?' + dispUrl,
			data : requestDetails,
			cache : false,
			success : function(html) {
				$jq('#result').html(html);
				clearLoanTypeMaster();
			}
		});
	}
}

// loan Festival Master start
function saveLoanFestival() {
	
	var errorMessage = "";
	var status = true;
	if($jq.trim($jq('#festivalName').val())== ''){
		errorMessage += "Please enter the Festival Name\n";
		if(status){
			$jq('#festivalName').focus();
			status = false;
		}
	}
	else{
		if($jq.trim($jq('#festivalName').val()).length < 4 || $jq.trim($jq('#festivalName').val()).length > 30){
			errorMessage += "Festival Name should be between 4-30 characters \n";
			if(status){
				$jq('#festivalName').focus();
				status = false;
			}
	}
	}
//		if($jq.trim($jq('#festivalDate').val())== ''){
//			errorMessage += "please Enter the Festival Date\n";
//			if(status){
//				$jq('#festivalDate').focus();
//				status = false;
//			}
//		}
//			if($jq.trim($jq('#festivalLoanStartDate').val())== ''){
//				errorMessage += "please Enter the Loan Application Start Date\n";
//				if(status){
//					$jq('#festivalLoanStartDate').focus();
//					status = false;
//				}
//			}
//				if($jq.trim($jq('#festivalLoanEndDate').val())== ''){
//					errorMessage += "please Enter the Loan Application Last Date\n";
//					if(status){
//						$jq('#festivalLoanEndDate').focus();
//						status = false;
//					}
//				}
//					if (($jq("#festivalLoanStartDate").val() != "") && ($jq("#festivalLoanEndDate").val() != "") && (compareDates($jq("#festivalLoanStartDate").val(), 'dd-MMM-yyyy', $jq("#festivalLoanEndDate").val(),'dd-MMM-yyyy') == 1)) {
//						errorMessage += "Last Date greater than start Date.\n";
//						if (status) {
//							status = false;
//							$jq("#festivalLoanStartDate").focus();
//						}
//					}
	
	if (status) {
		var requestDetails = {
			"nodeID" : nodeID,
			"festivalName" : $jq.trim($jq('#festivalName').val())  ,
//			"festivalDate" : $jq.trim($jq('#festivalDate').val())  ,
//			"festivalLoanStartDate" : $jq.trim($jq('#festivalLoanStartDate').val())  ,
//			"festivalLoanEndDate" : $jq.trim($jq('#festivalLoanEndDate').val())  ,
			"param" : "festivalManage"
		};
		$jq.ajax( {
			type : "POST",
			url : 'loan.htm?' + dispUrl,
			data : requestDetails,
			cache : false,
			success : function(html) {
				$jq('#result').html(html);
				clearLoanFestival();
			}
		});
	}
	else{
		alert(errorMessage);
	}
}
function clearLoanFestival() {
	$jq('#festivalName').val('');
//	$jq('#festivalDate').val('');
//	$jq('#festivalLoanStartDate').val('');
//	$jq('#festivalLoanEndDate').val('');
	nodeID = "";
}

function editLoanFestivalDetails(id,festName,festDate,festStartDate,festEndDate) {
	nodeID = id;
	$jq('#festivalName').val(festName);
//	$jq('#festivalDate').val(festDate);
//	$jq('#festivalLoanStartDate').val(festStartDate);
//	$jq('#festivalLoanEndDate').val(festEndDate);
}


function deleteLoanFestivalDetails(id) {
	var del = confirm("Do you want to delete this item?");
	if (del == true) {
		var requestDetails = {
			"nodeID" : id,
			"param" : "festivalDelete"
		};
		$jq.ajax( {
			type : "POST",
			url : 'loan.htm?' + dispUrl,
			data : requestDetails,
			cache : false,
			success : function(html) {
				$jq('#result').html(html);
				clearLoanFestival();
			}
		});
	}
}
// loan Festival Master end

// Start of GPF Sub Type
function resetGPFSubTypeDetails() {
	$jq('#gpfType').val('0');
	$jq('#purpose').val('');
	$jq('#rule').val('');
	nodeID = "";
}
function editGPFSubTypeDetails(id, gpfType, purpose, rule) {
	nodeID = id;
	$jq('#gpfType').val(gpfType);
	$jq('#purpose').val(purpose);
	$jq('#rule').val(rule);
}
function submitGPFSubTypeDetails() {
	var flag = true;
	var errorMessage = "";
	if ($jq('#gpfType').val() == '0') {
		errorMessage += "Please Select GPF Type\n";
		if (flag) {
			$jq('#gpfType').focus();
			flag = false;
		}
	}
	if ($jq('#purpose').val() == '') {
		errorMessage += "Please Enter purpose\n";
		if (flag) {
			$jq('#purpose').focus();
			flag = false;
		}
	}
	if ($jq('#rule').val() == '') {
		errorMessage += "Please Enter Rule \n";
		if (flag) {
			$jq('#rule').focus();
			flag = false;
		}
	}

	if (flag) {
		var requestDetails = {
			"gpfType" : $jq('#gpfType').val(),
			"purpose" : $jq('#purpose').val(),
			"rule" : $jq('#rule').val(),
			"status" : "1",
			"param" : "manageGpfSubType",
			"nodeID" : nodeID
		};
		$jq.post('loan.htm?' + dispUrl, requestDetails, function(html) {
			$jq("#result").html(html);
			resetGPFSubTypeDetails();
		});
	} else {
		alert(errorMessage);
	}
}
function deleteGPFSubTypeDetails(id) {
	var check = confirm("Do you want to delete ?");
	if (check) {
		var requestDetails = {
			"status" : "0",
			"param" : "manageGpfSubType",
			"nodeID" : id
		};
		$jq.post('loan.htm?' + dispUrl, requestDetails, function(html) {
			$jq("#result").html(html);
		});
	}
}
// GPF SubType end

// GPF closing balance start
function resetGpfClosingBalance() {
	$jq('#changeSfID').val('');
	$jq('#balance').val('');
	$jq('#fromDate').val('');
	$jq('#toDate').val('');
	nodeID = "";
}
function editGpfClosingBalance(id, sfid, amount, fromDate, toDate) {
	nodeID = id;
	$jq('#changeSfID').val(sfid);
	$jq('#balance').val(amount);
	$jq('#fromDate').val(fromDate);
	$jq('#toDate').val(toDate);
}
function saveGpfClosingBalance() {
	var flag = true;
	var errorMessage = "";
	if ($jq('#changeSfID').val() == '') {
		errorMessage += "Please Enter Sfid\n";
		if (flag) {
			$jq('#changeSfID').focus();
			flag = false;
		}
	}
	if ($jq('#balance').val() == '') {
		errorMessage += "Please Enter Closing Balance\n";
		if (flag) {
			$jq('#balance').focus();
			flag = false;
		}
	}
	if ($jq('#fromDate').val() == '') {
		errorMessage += "Please Enter From Date.\n";
		if (flag) {
			$jq('#fromDate').focus();
			flag = false;
		}
	}
	if ($jq('#toDate').val() == '') {
		errorMessage += "Please Enter To Date.\n";
		if (flag) {
			$jq('#toDate').focus();
			flag = false;
		}
	}
	if (compareDate($jq('#fromDate').val(), $jq('#toDate').val())) {
		errorMessage += "Invalid Dates \n";
		if (flag) {
			flag = false;
			$jq('#fromDate').focus();
		}
	}
	if (flag) {
		var requestDetails = {
			"changeSfID" : $jq('#changeSfID').val().toUpperCase(),
			"balance" : $jq('#balance').val(),
			"fromDate" : $jq('#fromDate').val(),
			"toDate" : $jq('#toDate').val(),
			"param" : "gpfBalanceManage",
			"nodeID" : nodeID
		};
		$jq.ajax( {
			type : "POST",
			url : 'loan.htm?' + dispUrl,
			data : requestDetails,
			cache : false,
			success : function(html) {
				$jq("#result").html(html);
				/*if($jq('.success').is(':visible')){
					var check=confirm(" Tution Fee Details has been Successfully Sent...\n\nPlease click ok to 'Preview Tution Fee Details Form 'Take print' from here\n\n");
					if(check){
					document.forms[0].requestId.value = $jq.trim(requestIDt);
					
				   document.forms[0].param.value = "requestDetails";
					document.forms[0].action = "workflowmap.htm";
					document.forms[0].submit();	
					}}*/
				resetGpfClosingBalance();
			}
		});
	} else {
		alert(errorMessage);
	}
}
function deleteGpfClosingBalance(id) {
	var check = confirm("Do you want to delete ?");
	if (check) {
		var requestDetails = {
			"param" : "gpfBalanceDelete",
			"nodeID" : id
		};
		$jq.post('loan.htm?' + dispUrl, requestDetails, function(html) {
			$jq("#result").html(html);
			resetGpfClosingBalance();
		});
	}
}
function reportGpfClosingBalance(sfid){
	
	window
	.open(
			"./report.htm?param=gpfclosing&returnValue=report&sfid="+sfid,
			'preview',
			'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
	} 
// GPF closing balance end

// Loan Type Details
function manageLoanDetails() {
	var errorMessages = "";
	var status = true;
	var selectedLeaves = "";
	var loanSubTypeID = "0";

	if ($jq("#loanType").val() == "0") {
		errorMessages += "Please select loan type\n";
		status = false;
		$jq("#loanType").focus();
	}

	if ($jq("#loanSubType").val() != null) {
		loanSubTypeID = $jq("#loanSubType").val();
	}

	if ($jq("#minInstallments").val() != ""
			&& $jq("#maxInstallments").val() != ""
			&& parseFloat($jq("#minInstallments").val()) > parseFloat($jq(
					"#maxInstallments").val())) {
		errorMessages += "Maximum principle installments should be greater than minimum principle installments.\n";
		if (status) {
			status = false;
			$jq("#minInstallments").focus();
		}
	}
	if ($jq("#minInterestInstallments").val() != ""
		&& $jq("#maxInterestInstallments").val() != ""
		&& parseFloat($jq("#minInterestInstallments").val()) > parseFloat($jq(
				"#maxInterestInstallments").val())) {
	errorMessages += "Maximum interest installments should be greater than minimum interest installments.\n";
	if (status) {
		status = false;
		$jq("#minInstallments").focus();
	}
}

	if ($jq("#experience").val() == "") {
		errorMessages += "Please enter minimum experience required to apply this loan\n";
		if (status) {
			status = false;
			$jq("#experience").focus();
		}
	} else {
		if (isNaN($jq("#experience").val())) {
			errorMessages += "Please enter valid Integer for Minimum Service\n";
			if (status) {
				status = false;
				$jq("#experience").focus();
			}
		} else if ($jq("#experience").val() < 0) {
			errorMessages += "Minimum Service should be Positive\n";
			if (status) {
				status = false;
				$jq("#experience").focus();
			}
		}
	}
	if (!$jq('input:radio[name=periodTypeFlag]').is(':checked')) {
		errorMessages += "Please select Impact on time period\n";
		if (status) {
			status = false;
			$jq("#periodTypeFlag").focus();
		}
	}

	if ($jq('input:radio[name=periodTypeFlag]:checked').val() == "Y") {
		if ($jq("#fromDate").val() == "") {
			errorMessages += "Please select from date\n";
			if (status) {
				status = false;
				$jq("#fromDate").focus();
			}
		}
		if ($jq("#toDate").val() == "") {
			errorMessages += "Please select to date\n";
			if (status) {
				status = false;
				$jq("#toDate").focus();
			}
		}
		if (compareDate($jq("#fromDate").val(), $jq("#toDate").val())) {
			errorMessages += "ToDate should not earlier than FromDate \n";
			if (status) {
				status = false;
				$jq("#recoveryFlagNo").focus();
			}
		}
	}
	if (!$jq('input:radio[name=recoveryFlag]').is(':checked')) {
		errorMessages += "Please select Recovery\n";
		if (status) {
			status = false;
			$jq("#recoveryFlag").focus();
		}
	}
	if ($jq('input:radio[name=recoveryFlag]:checked').val() == "Y") {
		if ($jq("#minInstallments").val() == "") {
			errorMessages += "Please enter minimum principle installments\n";
			if (status) {
				status = false;
				$jq("#minInstallments").focus();
			}
		} else {
			if (isNaN($jq("#minInstallments").val())) {
				errorMessages += "Please enter valid Integer for min principle installments\n";
				if (status) {
					status = false;
					$jq("#minInstallments").focus();
				}
			} else if ($jq("#minInstallments").val() < 0) {
				errorMessages += "Minimum principle installments should be Positive\n";
				if (status) {
					status = false;
					$jq("#minInstallments").focus();
				}
			}
		}
		if ($jq("#maxInstallments").val() == "") {
			errorMessages += "Please enter maximum principle installments\n";
			if (status) {
				status = false;
				$jq("#maxInstallments").focus();
			}
		} else {
			if (isNaN($jq("#maxInstallments").val())) {
				errorMessages += "Please enter valid Integer for Max principle installments\n";
				if (status) {
					status = false;
					$jq("#maxInstallments").focus();
				}
			} else if ($jq("#maxInstallments").val() < 0) {
				errorMessages += "Maximum principle installments should be Positive\n";
				if (status) {
					status = false;
					$jq("#maxInstallments").focus();
				}
			}
		}
		if ($jq("#minInterestInstallments").val() == "") {
			errorMessages += "Please enter minimum interest installments\n";
			if (status) {
				status = false;
				$jq("#minInterestInstallments").focus();
			}
		} else {
			if (isNaN($jq("#minInterestInstallments").val())) {
				errorMessages += "Please enter valid Integer for min interest installments\n";
				if (status) {
					status = false;
					$jq("#minInterestInstallments").focus();
				}
			} else if ($jq("#minInterestInstallments").val() < 0) {
				errorMessages += "Minimum interest installments should be Positive\n";
				if (status) {
					status = false;
					$jq("#minInterestInstallments").focus();
				}
			}
		}
		if ($jq("#maxInterestInstallments").val() == "") {
			errorMessages += "Please enter maximum interest installments\n";
			if (status) {
				status = false;
				$jq("#maxInterestInstallments").focus();
			}
		} else {
			if (isNaN($jq("#maxInterestInstallments").val())) {
				errorMessages += "Please enter valid Integer for Max interest installments\n";
				if (status) {
					status = false;
					$jq("#maxInterestInstallments").focus();
				}
			} else if ($jq("#maxInterestInstallments").val() < 0) {
				errorMessages += "Maximum interest installments should be Positive\n";
				if (status) {
					status = false;
					$jq("#maxInterestInstallments").focus();
				}
			}
		}
		if ($jq("#noOfMonths").val() == "") {
			errorMessages += "Please enter Recovery starts from\n";
			if (status) {
				status = false;
				$jq("#noOfMonths").focus();
			}
		} else {
			if (isNaN($jq("#noOfMonths").val())) {
				errorMessages += "Please enter valid Integer for Recovery Starts From\n";
				if (status) {
					status = false;
					$jq("#noOfMonths").focus();
				}
			}
			if ($jq("#noOfMonths").val() < 0) {
				errorMessages += "Recovery Starts From should be Positive\n";
				if (status) {
					status = false;
					$jq("#noOfMonths").focus();
				}
			}
		}
		if ($jq("#maxRecoveryPeriod").val() == "") {
			errorMessages += "Please enter maximum recovery period\n";
			if (status) {
				status = false;
				$jq("#maxRecoveryPeriod").focus();
			}
		} else {
			if (isNaN($jq("#maxRecoveryPeriod").val())) {
				errorMessages += "Please enter valid Integer for Max Recovery Period\n";
				if (status) {
					status = false;
					$jq("#maxRecoveryPeriod").focus();
				}
			}
			if ($jq("#maxRecoveryPeriod").val() < 0) {
				errorMessages += "Maximum recovery period should be positive\n";
				if (status) {
					status = false;
					$jq("#maxRecoveryPeriod").focus();
				}
			}
		}
		if ($jq("#interestRate").val() == "") {
			errorMessages += "Please enter interest rate\n";
			if (status) {
				status = false;
				$jq("#interestRate").focus();
			}
		} else if (isNaN($jq("#interestRate").val())) {
			errorMessages += "Please enter interest rate between 0 to 100 \n";
			if (status) {
				status = false;
				$jq("#interestRate").focus();
			}
		} else if ($jq("#interestRate").val() < 0
				|| $jq("#interestRate").val() > 100) {
			errorMessages += "Please enter interest rate between 0 to 100 \n";
			if (status) {
				status = false;
				$jq("#interestRate").focus();
			}
		}
	}
	if (!$jq('input:radio[name=impactOnLeaveFlag]').is(':checked')) {
		errorMessages += "Please select Impact on leave\n";
		if (status) {
			status = false;
			$jq("#impactOnLeaveFlag").focus();
		}
	}

	if ($jq('input:radio[name=impactOnLeaveFlag]:checked').val() == "Y") {
		if ($jq("#SelectRight option").val() == null) {
			errorMessages += "Please select Leaves\n";
			if (status) {
				status = false;
				$jq("#SelectRight").focus();
			}
		}
	}

	if (status) {
		$jq("#SelectRight option").each(function() {
			selectedLeaves += $jq(this).val() + ",";
		});
		if (selectedLeaves != "") {
			selectedLeaves = selectedLeaves.substring(0,
					selectedLeaves.length - 1);
		}
		var requestDetails = {
			"loanType" : $jq('#loanType').val(),
			"loanSubType" : loanSubTypeID,
			"minInstallments" : $jq('#minInstallments').val(),
			"maxInstallments" : $jq('#maxInstallments').val(),
			"minInterestInstallments" : $jq('#minInterestInstallments').val(),
			"maxInterestInstallments" : $jq('#maxInterestInstallments').val(),
			"experience" : $jq('#experience').val(),
			"periodTypeFlag" : $jq('input:radio[name=periodTypeFlag]:checked')
					.val(),
			"fromDate" : $jq('#fromDate').val(),
			"toDate" : $jq('#toDate').val(),
			"recoveryFlag" : $jq('input:radio[name=recoveryFlag]:checked')
					.val(),
			"noOfMonths" : $jq('#noOfMonths').val(),
			"maxRecoveryPeriod" : $jq('#maxRecoveryPeriod').val(),
			"impactOnLeaveFlag" : $jq(
					'input:radio[name=impactOnLeaveFlag]:checked').val(),
			"leave" : selectedLeaves,
			"interestRate" : $jq('#interestRate').val(),
			"param" : "manageLoanDetails",
			"nodeID" : nodeID
		};
		$jq.post('loan.htm?' + dispUrl, requestDetails, function(html) {
			$jq("#LoanTypeList").html(html);
			clearLoanDetails();
		});
	} else {
		alert(errorMessages);
	}
}

function clearLoanDetails() {
	$jq("input:radio").attr("checked", false);
	$jq('input:radio').each(function(){
	  if($jq(this).val()=='N') $jq(this).attr('checked',true);		
	});		
	impactOnTimePeriod();
	recovery();
	impactOnLeave();
	$jq('input:text').val("");
	$jq('#periodDiv').hide();
	$jq('#LoanSubTypeDiv').hide();
	$jq('#LeaveDiv').hide();
	$jq('#RecoveryDiv').hide();
	$jq('select').val("0");
	$jq('#loanSubType').find('option').remove().end();
	nodeID = "";
	resetLeaves();
}

function resetLeaves() {
	if ($jq('#SelectRight') != null) {
		$jq("#SelectRight > option").each(
				function() {
					$jq("#SelectLeft").append(
							'<option value=' + this.value + '>' + this.text
									+ '</option>');
				});
		$jq('#SelectRight').find('option').remove().end();
	}
}

function editLoanDetails(id, loanType, loanSubType, minInstallments,
		maxInstallments,minInterestInstallments,maxInterestInstallments, experience, periodTypeFlag, fromDate, toDate,
		recoveryFlag, noOfMonths, maxRecoveryPeriod, impactOnLeaveFlag,
		interestRate) {
	nodeID = id;
	$jq('#loanType').val(loanType), $jq('#minInstallments')
			.val(minInstallments),
			$jq('#maxInstallments').val(maxInstallments),
			$jq('#minInterestInstallments').val(minInterestInstallments),
			$jq('#maxInterestInstallments').val(maxInterestInstallments), $jq('#experience')
					.val(experience), $jq('#fromDate').val(
					(fromDate == "") ? "" : convertDateFormat(fromDate)), $jq(
					'#toDate').val(
					(toDate == "") ? "" : convertDateFormat(toDate)), $jq(
					'#noOfMonths').val(noOfMonths), $jq('#maxRecoveryPeriod')
					.val(maxRecoveryPeriod), $jq('#interestRate').val(
					interestRate);

	$jq("#periodTypeFlag[value=" + periodTypeFlag + "]").attr('checked', true);
	if (periodTypeFlag == "Y") {
		$jq('#periodDiv').show();
	} else {
		$jq('#periodDiv').hide();
	}
	$jq("#recoveryFlag[value=" + recoveryFlag + "]").attr('checked', true);
	if (recoveryFlag == "Y") {
		$jq('#RecoveryDiv').show();
	} else {
		$jq('#recoveryDiv').hide();
	}
	$jq("#impactOnLeaveFlag[value=" + impactOnLeaveFlag + "]").attr('checked',
			true);
	if (impactOnLeaveFlag == "Y") {
		$jq('#LeaveDiv').show();
	} else {
		$jq('#LeaveDiv').hide();
	}
	if (loanType == festivalID) {
		selectedSubLoan();
		jQuery('#loanSubType').val(loanSubType)
	} else {
		$jq('#LoanSubTypeDiv').hide();
	}
	loanLeavesSelect(id);
}

function selectedSubLoan() {
	$jq('#loanSubType').find('option').remove().end();
	$jq("#LoanSubTypeDiv").hide();
	if (jsonLoanSubType != null && $jq('#loanType').val() == festivalID) {
		for ( var i = 0; i < jsonLoanSubType.length; i++) {
			$jq("#LoanSubTypeDiv").show();
			$jq("#loanSubType").append(
					'<option value=' + jsonLoanSubType[i].id + '>'
							+ jsonLoanSubType[i].festivalName
							+ '</option>');

		}
	}
}

function convertDateFormat(date) {
	var str = date.split(" ")[0];
	return str.split("-")[2] + "-" + getMonthMON(str.split("-")[1]) + "-"
			+ str.split("-")[0];
}

function loanLeavesSelect(id) {
	$jq("#SelectRight option").attr("selected", "selected");
	$jq('#MoveLeft').click();
	$jq('#SelectLeft option').attr('selected', '');
	$jq('#SelectRight').find('option').remove().end();
	if (jsonAvailabeLeavesList != null) {
		for ( var i = 0; i < jsonAvailabeLeavesList.length; i++) {
			if (jsonAvailabeLeavesList[i].loanDetailsID == id) {
				$jq(
						"#SelectLeft option[ value='"
								+ jsonAvailabeLeavesList[i].leaveTypeID + "']")
						.attr("selected", "selected");
			}
		}
	}
	$jq("#MoveRight").click();
	$jq("#SelectRight option").attr("selected", false);
}
function deleteLoanDetails(id) {
	var check = confirm("Do you want to delete ?");
	if (check) {
		var requestDetails = {
			"param" : "loanDetailsDelete",
			"nodeID" : id
		};
		$jq.post('loan.htm?' + dispUrl, requestDetails, function(html) {
			$jq("#LoanTypeList").html(html);
			clearLoanDetails();
		});
	}
}
function impactOnTimePeriod() {
	if ($jq('input:radio[name=periodTypeFlag]:checked').val() == "Y") {
		$jq('#periodDiv').show();
	} else {
		$jq("#fromDate").val("");
		$jq("#toDate").val("");
		$jq("#periodDiv").hide();
	}
}
function recovery() {
	if ($jq('input:radio[name=recoveryFlag]:checked').val() == "Y") {
		$jq('#RecoveryDiv').show();
	} else {
		$jq("#minInstallments").val("");
		$jq("#maxInstallments").val("");
		$jq("#minInterestInstallments").val("");
		$jq("#maxInterestInstallments").val("");
		$jq("#noOfMonths").val("");
		$jq("#maxRecoveryPeriod").val("");
		$jq("#interestRate").val("");
		$jq("#RecoveryDiv").hide();
	}
}

function impactOnLeave() {
	if ($jq('input:radio[name=impactOnLeaveFlag]:checked').val() == "Y") {
		$jq('#LeaveDiv').show();
	} else {
		$jq("#SelectRight option").attr("selected", "selected");
		$jq('#MoveLeft').click();
		$jq("#SelectLeft option").attr("selected", false);
		$jq('#LeaveDiv').hide();
	}
}
// Loan amount details Start
function manageLoanAmountDetails() {
	var errorMessage = "";
	var status = true;
	var selectedDesignations = "";
	var amountGrid = "";
	if ($jq("#loanType").val() == "0") {
		errorMessage += "Please select Loan Type.\n";
		status = false;
		$jq("#loanType").focus();
	}

	if ($jq("#gazType").val() == "0") {
		errorMessage += "Please select Gazetted Type.\n";
		if (status) {
			status = false;
			$jq("#gazType").focus();
		}
	}
	$jq("#SelectRight option").each(function() {
		selectedDesignations += $jq(this).val() + ",";
	});
	if (selectedDesignations != "") {
		selectedDesignations = selectedDesignations.substring(0,
				selectedDesignations.length - 1);
	} else {
		errorMessage += "Please select designations\n";
		if (status) {
			status = false;
			$jq("#SelectRight").focus();
		}
	}
	
	if (!$jq('input:radio[name=impactOnPayFlag]').is(':checked')) {
		errorMessage += "Please select Impact on Pay\n";
		if (status) {
			status = false;
			$jq("#impactOnPayFlag").focus();
		}
	}
	if (!$jq('input:radio[name=impactOnTimesFlag]').is(':checked')) {
		errorMessage += "Please select Impact on No.of Times\n";
		if (status) {
			status = false;
			$jq("#impactOnTimesFlag").focus();
		}
	}
	if (!$jq('input:radio[name=impactOnDAFlag]').is(':checked')) {
		errorMessage += "Please select Impact on DA\n";
		if (status) {
			status = false;
			$jq("#impactOnDAFlag").focus();
		}
	}
	if (!$jq('input:radio[name=impactOnBalanceFlag]').is(':checked')) {
		errorMessage += "Please select Impact on Balance\n";
		if (status) {
			status = false;
			$jq("#impactOnBalanceFlag").focus();
		}
	}
	if (!$jq('input:radio[name=impactOnNoOfMonthsPayFlag]').is(':checked')) {
		errorMessage += "Please select Impact on No.of Months Pay\n";
		if (status) {
			status = false;
			$jq("#impactOnNoOfMonthsPayFlag").focus();
		}
	}	
	
	if ($jq('input:radio[name=impactOnDAFlag]:checked').val() == "Y") {
		if (isNaN($jq("#daPercentage").val())) {
			errorMessage += "Please enter number in DA percent\n";
			if (status) {
				status = false;
				$jq("#daPercentage").focus();
			}
		}
	}
	if ($jq('input:radio[name=impactOnDAFlag]:checked').val() == "Y") {
		if ($jq("#daPercentage").val() < 1 || $jq("#daPercentage").val() > 100) {
			errorMessage += "Please enter DA percent between 1 to 100 \n";
			if (status) {
				status = false;
				$jq("#daPercentage").focus();
			}
		}
	}
	
	amountGrid = getLoanAmountGrid();
	errorMessage += globalMessage;
	if (status && globalMessage == '') {
		var requestDetails = {
			"loanType" : $jq("#loanType").val(),
			"loanSubType" : $jq("#loanSubType").val(),
			"gazType" : $jq("#gazType").val(),
			"nodeID" : nodeID,
			"designation" : selectedDesignations,
			"amountGrid" : JSON.stringify(amountGrid),
			"impactOnPayFlag" : $jq('input:radio[name=impactOnPayFlag]:checked').val(),
			"impactOnTimesFlag" : $jq('input:radio[name=impactOnTimesFlag]:checked').val(),
			"impactOnDAFlag" : $jq('input:radio[name=impactOnDAFlag]:checked').val(),
			"impactOnBalanceFlag" : $jq('input:radio[name=impactOnBalanceFlag]:checked').val(),
			"impactOnNoOfMonthsPayFlag" : $jq('input:radio[name=impactOnNoOfMonthsPayFlag]:checked').val(),
			"daPercentage" : $jq("#daPercentage").val(),
			"param" : "manageLoanAmount"
		};
		$jq.post('loan.htm?' + dispUrl, requestDetails, function(html) {
			$jq("#loanAmountDetails").html(html);
			clearLoanAmountDetails();
		});
	} else {
		alert(errorMessage);
	}
}
  
function clearLoanAmountDetails() {
	$jq('#initGrid input:text').val("");
	$jq("input:radio").attr("checked", false);
	$jq('input:radio').each(function(){
	if($jq(this).val()=='N') $jq(this).attr("checked",true);		
	});
	impactOnPay();
	impactOnTime();
	impactOnTimeDA('');
	impactOnBalance();
	impactOnNoOfMonthsPay();
	$jq("#selectedLoan").val("0");
	$jq("#gazType").val("0");
	$jq("#daPercentage").val('1');
	$jq("#loanType").val("0");
	$jq("#SelectRight").find('option').remove().end();
	$jq('#loanSubType').find('option').remove().end();
	selectedDesignation();
	nodeID = "";
	$jq("#grid").text("");
	
}
// Loan amount details End

// Loan Request starts
function checkPreviousLoanDiv(suffix) {
	if ($jq('input:radio[id=prevLoanFlag'+suffix+']:checked').val() == "Y") {
		$jq('#previousLoanDiv'+suffix).show();
	} else {
		if(suffix=='F'){
		$jq('#prevLoanName'+suffix).val('');
		} else if(suffix=='G'){
			$jq('#prevLoanDetails').val('');
		} else if(suffix=='M'){
			$jq('#prevAdvanceDateM').val('');
		}
		$jq("input:radio[name=prevLoanRecFlag"+suffix+"]").attr("checked", false);
		$jq('#previousLoanDiv'+suffix).hide();
	}
}

function getLoanType() {
	$jq('#buttons').hide();
	$jq('#festival').hide();
	$jq('#gpf').hide();
	$jq('#motor').hide();
	$jq('#loanSubTypeDiv').hide();
	if ($jq('#loanType').val() == festivalID) {
		$jq('#festival').show();
		$jq('#buttons').show();
		getFestivals();
	} else if ($jq('#loanType').val() == motorcarID
			|| $jq('#loanType').val() == motorScooter
			|| $jq('#loanType').val() == pcID
			|| $jq('#loanType').val() == motorCycle
			|| $jq('#loanType').val() == moped) {
		$jq('#motor').show();
		$jq('#buttons').show();
	} else if ($jq('#loanType').val() == gpfAdvanceID
			|| $jq('#loanType').val() == gpfWithdrawalID) {
		$jq('#gpf').show();
		$jq('#buttons').show();
		getGpfSubTypes();
	}
}

function getFestivals() {
	$jq('#loanSubTypeDiv').show();
	$jq('#loanSubName').text('Name of the festival');
	$jq('#loanSubType').find('option').remove().end().append(
			'<option value="0">Select</option>').val('0');
	for ( var i = 0; i < jsonFestivalObject.length; i++) {
		$jq("#loanSubType").append(
				'<option value=' + jsonFestivalObject[i].id + '>'
						+ jsonFestivalObject[i].festivalName + '</option>');
	}
}
function getGpfSubTypes() {
	$jq('#loanSubTypeDiv').show();
	$jq('#loanSubName').text('Purpose');
	$jq('#loanSubType').find('option').remove().end().append(
			'<option value="0">Select</option>').val('0');
	for ( var i = 0; i < jsonArrayObject.length; i++) {
		if (jsonArrayObject[i].loanTypeID == $jq("#loanType").val()) {
			$jq("#loanSubType").append(
					'<option value=' + jsonArrayObject[i].id + '>'
							+ jsonArrayObject[i].purpose + '</option>');
		}
	}
}

function getRules() {
	$jq('#gpfRule').text('');
	if ($jq('#loanType').val() == gpfAdvanceID
			|| $jq('#loanType').val() == gpfWithdrawalID) {
		for ( var i = 0; i < jsonArrayObject.length; i++) {
			if (jsonArrayObject[i].id == $jq("#loanSubType").val()) {
				$jq('#gpfRule').text(jsonArrayObject[i].rule);
			}
		}
	}
}

function loanSubmit() {
	var status = true;
	var errorMessage = "";
	var requestDetails = "";
	
	$jq('#result').text('');
	if($jq('#offlineSFID').is(':visible') && $jq('#offlineSFID').val()==''){
		errorMessages += "Please Enter sfID \n";
		if (status) {
			status = false;
			$jq('#offlineSFID').focus();
			}
	}
	if ($jq('#loanType').val() == festivalID) {
		if ($jq('#loanSubType').val() == '0') {
			errorMessage += "Please select Festival Name\n";
			status = false;
			$jq('#loanSubType').focus();
		}
		if (!$jq('input:radio[name=prevLoanFlag]').is(':checked')) {
			errorMessage += "Please select Festival advance has been drawn earlier during the current financial year\n";
			if (status) {
				status = false;
				$jq('#loanSubType').focus();
			}
		} else if ($jq('input:radio[id=prevLoanFlagF]:checked').val() == "Y") {
			if ($jq('#prevLoanName').val() == '0') {
				errorMessage += "Please enter Festival name\n";
				if (status) {
					status = false;
					$jq('#prevLoanName').focus();
				}
			}
			if (!$jq('input:radio[id=prevLoanRecFlagF]').is(':checked')) {
				errorMessage += "Please select previous advance recovery\n";
				if (status) {
					status = false;
					$jq('#prevLoanRecFlagF').focus();
				}
			}
			else if($jq('input:radio[id=prevLoanRecFlagF]:checked').val() == "N"){
				errorMessage += "Previous advance recovery should be recovered fully\n";
				if (status) {
					status = false;
					$jq('#prevLoanRecFlagF').focus();
				}
			}
		}
		if (!$jq('input:radio[name=currentPositionFlag]').is(':checked')) {
			errorMessage += "Please select whether under Suspension/EXOL/HPL/LPR and the period of leave\n";
			if (status) {
				status = false;
				$jq('#currentPositionFlagF').focus();
			}
		} else if ($jq('input:radio[id=currentPositionFlagF]:checked').val() == "Y") {
			if ($jq('#leaveFromDateF').val() == '0') {
				errorMessage += "Please select date of commencement of leave\n";
				if (status) {
					status = false;
					$jq('#leaveFromDateF').focus();
				}
			}
			if ($jq('#leaveToDateF').val() == '0') {
				errorMessage += "Please select date of expiry of leave\n";
				if (status) {
					status = false;
					$jq('#leaveToDateF').focus();
				}
			}
		}
		if (status) {
			requestDetails = {
				"loanType" : festivalID,
				"loanSubType" : $jq('#loanSubType').val(),
				"prevLoanFlag" : $jq('input:radio[id=prevLoanFlagF]:checked')
						.val(),
				"prevLoanName" : $jq('#prevLoanNameF').val(),
				"prevLoanRecFlag" : $jq(
						'input:radio[id=prevLoanRecFlagF]:checked').val(),
				"currentPositionFlag" : $jq(
						'input:radio[id=currentPositionFlagF]:checked').val(),
				"leaveFromDate" : $jq('#leaveFromDateF').val(),
				"leaveToDate" : $jq('#leaveToDateF').val()
			}
			if($jq('#offlineSFID').is(':visible') && $jq('#offlineSFID').val()!=''){
				$jq('#offlineSFID').val($jq.trim($jq('#offlineSFID').val()).toUpperCase());
				requestDetails.offlineSFID=$jq('#offlineSFID').val();
			}
		}

	} else if ($jq('#loanType').val() == gpfAdvanceID
			|| $jq('#loanType').val() == gpfWithdrawalID) {
		if ($jq('#loanType').val() == '0') {
			errorMessage += "Please select loan type\n";
			if (status) {
				status = false;
				$jq('#loanType').focus();
			}
		}
		if ($jq('#loanSubType').val() == '0') {
			errorMessage += "Please select purpose\n";
			if (status) {
				status = false;
				$jq('#loanSubType').focus();
			}
		}
		if ($jq('#reqAmountG').val() == '' || $jq('#reqAmountG').val() == '0') {
			errorMessage += "Please enter required loan amount\n";
			if (status) {
				status = false;
				$jq('#reqAmountG').focus();
			}
		}else if(isNaN($jq('#reqAmountG').val()) || $jq('#reqAmountG').val()< 0 ){
			errorMessage += "Please enter valid required loan amount\n";
			if (status) {
				status = false;
				$jq('#reqAmountG').focus();
			}
		}
		if ($jq('#circumstance').val() == '') {
			errorMessage += "Please enter circumstance justifying the application for the advance\n";
			if (status) {
				status = false;
				$jq('#circumstance').focus();
			}
		}
		if (!$jq('input:radio[id=prevLoanFlagG]').is(':checked')) {
			errorMessage += "Please select whether any withdrawal was taken for the same purpose earlier\n";
			if (status) {
				status = false;
				$jq('#prevLoanFlagG').focus();
			}
		} else if ($jq('input:radio[id=prevLoanFlagG]:checked').val() == "Y") {
			if ($jq('#prevLoanDetails').val() == '') {
				errorMessage += "Please enter Previous Loan Details\n";
				if (status) {
					status = false;
					$jq('#prevLoanDetailsG').focus();
				}
			}
			if (!$jq('input:radio[id=prevLoanRecFlagG]').is(':checked')) {
				errorMessage += "Please select previous advance recovery\n";
				if (status) {
					status = false;
					$jq('#prevLoanRecFlagG').focus();
				}
			}
		}
		requestDetails = {
			"loanType" : $jq('#loanType').val(),
			"loanSubType" : $jq('#loanSubType').val(),
			"reqAmount" : $jq('#reqAmountG').val(),
			"circumstance" : $jq('#circumstance').val(),
			"prevLoanFlag" : $jq('input:radio[id=prevLoanFlagG]:checked')
					.val(),
			"prevLoanRecFlag" : $jq(
					'input:radio[id=prevLoanRecFlagG]:checked').val(),
			"prevLoanName" : $jq('#prevLoanDetails').val()
		}
		if($jq('#offlineSFID').is(':visible') && $jq('#offlineSFID').val()!=''){
			requestDetails.offlineSFID=$jq('#offlineSFID').val();
		}
	} else if ($jq('#loanType').val() == motorcarID
			|| $jq('#loanType').val() == motorScooter
			|| $jq('#loanType').val() == pcID
			|| $jq('#loanType').val() == motorCycle
			|| $jq('#loanType').val() == moped) {
		if ($jq('#loanType').val() == '0') {
			errorMessage += "Please select loan type\n";
			if (status) {
				status = false;
				$jq('#loanType').focus();
			}
		}
		if ($jq('#anticipatedAmount').val() == '0'|| $jq.trim($jq('#anticipatedAmount').val()) == '') {
			errorMessage += "Please enter anticipated amount\n";
			if (status) {
				status = false;
				$jq('#anticipatedAmount').focus();
			}
		}else if(isNaN($jq('#anticipatedAmount').val()) || $jq('#anticipatedAmount').val()< 0 ){
			errorMessage += "Please enter valid anticipated amount\n";
			if (status) {
				status = false;
				$jq('#anticipatedAmount').focus();
			}
		}
		if ($jq('#reqAmountM').val() == '0'|| $jq.trim($jq('#reqAmountM').val()) == '') {
			errorMessage += "Please enter required loan amount\n";
			if (status) {
				status = false;
				$jq('#reqAmountM').focus();
			}
		}else if(isNaN($jq('#reqAmountM').val()) || $jq('#reqAmountM').val()< 0 ){
			errorMessage += "Please enter valid required loan amount\n";
			if (status) {
				status = false;
				$jq('#reqAmountM').focus();
			}
		}
		if ($jq('#requestedInstalments').val() == '0'|| $jq.trim($jq('#requestedInstalments').val()) == '' ) {
			errorMessage += "Please enter number of required principle installments\n";
			if (status) {
				status = false;
				$jq('#requestedInstalments').focus();
			}
		}else if(isNaN($jq('#requestedInstalments').val()) || $jq('#requestedInstalments').val()< 0 ){
			errorMessage += "Please enter valid required principle installments\n";
			if (status) {
				status = false;
				$jq('#requestedInstalments').focus();
			}
		}
		if ($jq('#requestedInterestInstalments').val() == '0'|| $jq.trim($jq('#requestedInterestInstalments').val()) == '' ) {
			errorMessage += "Please enter number of required interest installments\n";
			if (status) {
				status = false;
				$jq('#requestedInterestInstalments').focus();
			}
		}else if(isNaN($jq('#requestedInterestInstalments').val()) || $jq('#requestedInterestInstalments').val()< 0 ){
			errorMessage += "Please enter valid required interest installments\n";
			if (status) {
				status = false;
				$jq('#requestedInterestInstalments').focus();
			}
		}
		if (!$jq('input:radio[id=prevLoanFlagM]').is(':checked')) {
			errorMessage += "Please select whether the advance for similar purpose was obtained previously\n";
			if (status) {
				status = false;
				$jq('#prevLoanFlagM').focus();
			}
		} else if ($jq('input:radio[id=prevLoanFlagM]:checked').val() == "Y") {
			if ($jq('#prevAdvanceDate').val() == '') {
				errorMessage += "Please select Date of drawal of the advance\n";
				if (status) {
					status = false;
					$jq('#prevAdvanceDate').focus();
				}
			}else{
				var sysdate = new Date();
				var advDate = $jq("#prevAdvanceDate").val();
				var date2 = new Date(advDate.split("-")[2], getMonthID(advDate
						.split("-")[1]) - 1, advDate.split("-")[0], 0, 0, 1, 0)
				if (sysdate < date2) {
					errorMessage += "Date of drawal of the advance can not be Future Date.\n";
					if (status) {
						status = false;
						$jq("#prevAdvanceDate").focus();
					}
				}	
			}
			if (!$jq('input:radio[id=prevLoanRecFlagM]').is(':checked')) {
				errorMessage += "Please select previous advance recovery\n";
				if (status) {
					status = false;
					$jq('#prevLoanRecFlagM').focus();
				}
			}else if($jq('input:radio[id=prevLoanRecFlagM]:checked').val() == "N"){
				errorMessage += "Previous advance recovery should be recovered fully\n";
				if (status) {
					status = false;
					$jq('#prevLoanRecFlagM').focus();
				}		
			}
			
		}
		if (!$jq('input:radio[name=intensionFlag]').is(':checked')) {
			errorMessage += "Please select whether the intension is to purchase a new or an old Motor Car/Cycle/Scooter/Moped/Personal Computer\n";
			if (status) {
				status = false;
				$jq('#intensionFlag').focus();
			}
		}
		if (!$jq('input:radio[name=intensionRuleFlag]').is(':checked')) {
			errorMessage += "Please select If the intension is to purchase Motor Car/Cycle/Scooter/Moped/Personal Computer from a person having official dealings with the government servant, whether previous sanction of the component authority has been obtained as required under Rule 18(3) of the Central Civil Services (Conduct) Rules, 1964\n";
			if (status) {
				status = false;
				$jq('#intensionRuleFlag').focus();
			}
		}
		if (!$jq('input:radio[id=currentPositionFlagM]').is(':checked')) {
			errorMessage += "Please select whether the officer is on leave or is about to proceed on leave\n";
			if (status) {
				status = false;
				$jq('#currentPositionFlagM').focus();
			}
		} else if ($jq('input:radio[id=currentPositionFlagM]:checked').val() == "Y") {
			if ($jq('#leaveFromDateM').val() == '0') {
				errorMessage += "Please select date of commencement of leave\n";
				if (status) {
					status = false;
					$jq('#leaveFromDateM').focus();
				}
			}
			if ($jq('#leaveToDateM').val() == '0') {
				errorMessage += "Please select date of expiry of leave\n";
				if (status) {
					status = false;
					$jq('#leaveToDateM').focus();
				}
			}
			if($jq('#leaveFromDateM').val() != '0' && $jq('#leaveToDateM').val() != '0' && (compareDates($jq('#leaveFromDateM').val(), 'dd-MMM-yyyy', $jq('#leaveToDateM').val(),'dd-MMM-yyyy') == 1))
			{
				errorMessage += "Date of commencement of leave should be greater than Date of expiry of leave\n";
				if (status) {
					status = false;
					$jq('#leaveToDateM').focus();
				}
			}
		}
		if (!$jq('input:radio[name=negotiationFlag]').is(':checked')) {
			errorMessage += "Please select any negotiations or preliminary enquiries being made so that delivery may be taken of the Motor Car/Cycle/Scooter/Moped/Personal Computer within month from the date of drawal of the advance\n";
			if (status) {
				status = false;
				$jq('#negotiationFlag').focus();
			}
		}
		if (!$jq('input:radio[name=declarationFlag]').is(':checked')) {
			errorMessage += "Please select certified that the information given about is complete and true\n";
			if (status) {
				status = false;
				$jq('#declarationFlag').focus();
			}
		}
		if (!$jq('input:radio[name=commitmentFlag]').is(':checked')) {
			errorMessage += "Please select Certified that I have not taken delivery of the Motor Car/Cycle/Scooter/Moped/Personal Computer on account of which I apply for the advance, that I shall complete negotiations for the purchase of, pay finally and take possession of the same before the expiry of one month from the date of drawal of the advance\n";
			if (status) {
				status = false;
				$jq('#commitmentFlag').focus();
			}
		}

		requestDetails = {
			"loanType" : $jq('#loanType').val(),
			"loanSubType" : "0",
			"anticipatedAmount" : $jq('#anticipatedAmount').val(),
			"reqAmount" : $jq('#reqAmountM').val(),
			"requestedInstalments" : $jq("#requestedInstalments").val(),
			"requestedInterestInstalments" : $jq("#requestedInterestInstalments").val(),
			"prevLoanFlag" : $jq('input:radio[id=prevLoanFlagM]:checked')
					.val(),
			"prevLoanRecFlag" : $jq(
					'input:radio[id=prevLoanRecFlagM]:checked').val(),
			"prevAdvanceDate" : $jq('#prevAdvanceDate').val(),
			"intensionFlag" : $jq('input:radio[name=intensionFlag]:checked')
					.val(),
			"intensionRuleFlag" : $jq(
					'input:radio[name=intensionRuleFlag]:checked').val(),
			"currentPositionFlag" : $jq(
					'input:radio[id=currentPositionFlagM]:checked').val(),
			"leaveFromDate" : $jq('#leaveFromDateM').val(),
			"leaveToDate" : $jq('#leaveToDateM').val(),
			"negotiationFlag" : $jq('input:radio[name=negotiationFlag]:checked')
					.val(),
			"declarationFlag" : $jq('input:radio[name=declarationFlag]:checked')
					.val(),
			"commitmentFlag" : $jq('input:radio[name=commitmentFlag]:checked')
					.val()
		}
		if($jq('#offlineSFID').is(':visible') && $jq('#offlineSFID').val()!=''){
			requestDetails.offlineSFID=$jq('#offlineSFID').val();
		}
	}
	if (status) {
		requestDetails.reason = $jq("#reason").val();
		requestDetails.param = "submitRequest";
		$jq.ajax( {
			type : "POST",
			url : "loanRequest.htm",
			data : requestDetails,
			cache : false,
			success : function(html) {
				$jq("#result").html(html);
				if ($jq('.success').is(':visible')) {
					
					 var check=confirm(" Loan  has been Successfully Sent...\nPreview Loan Application Form & print from here");
		     			if(check){
		     			document.forms[0].requestId.value = $jq.trim(requestIDLOAN);
		     		    document.forms[0].param.value = "requestDetails";
		     			document.forms[0].action = "workflowmap.htm";
		     			document.forms[0].submit();	
		     						}
					
				}
				clearLoan();
			}
		});
	} else {
		alert(errorMessage);
	}
}

function clearLoan() {
	var sfid=$jq('#offlineSFID').val();
	$jq("input:radio").attr("checked", false);
	$jq('input:text').val("");
	$jq('select').val("0");
	$jq('#loanSubTypeDiv').hide();
	$jq('div[id^=previousLoanDiv]').hide();
	$jq('#div[id^=leaveDiv]').hide();
	$jq('#reason').val("");
	$jq('#offlineSFID').val(sfid);
	getLoanType();
}

function showLeaveDiv(suffix) {
	if ($jq('input:radio[id=currentPositionFlag'+suffix+']:checked').val() == "Y") {
		$jq('#leaveDiv'+suffix).show();
	} else {
		$jq('#leaveFromDate'+suffix).val('');
		$jq('#leaveToDate'+suffix).val('');
		$jq('#leaveDiv'+suffix).hide();
	}
}
// Loan Request ends

// Start Loan Immediate Relief
function clearRelief() {
	$jq('#refSfID').val('');
	$jq('#dod').val('');
	$jq('#appname').val('');
	$jq('#advanceAmount').val('');
	$jq('#witness1').val('');
	$jq('#witness2').val('');
	$jq('#address').val('');
	$jq('#approvedBy').val('');
	$jq('#relationShipID').val('0');
	nodeID = "";
	$jq('#relationShipID option:not(:first)').each(function(){if($jq(this).val()=='16'){$jq(this).remove();}});
}

function manageRelief() {
	var errorMessage = "";
	var status = true;
	if ($jq("#refSfID").val() == "") {
		errorMessage += "Please enter SFID.\n";
		if (status) {
			status = false;
			$jq("#refSfID").focus();
		}
	}
	if ($jq("#dod").val() == "") {
		errorMessage += "Please enter Date of Death.\n";
		if (status) {
			status = false;
			$jq("#dod").focus();
		}
	}
	else {
		var sysdate = new Date();
		var dodDate = $jq("#dod").val();
		var date2 = new Date(dodDate.split("-")[2], getMonthID(dodDate
				.split("-")[1]) - 1, dodDate.split("-")[0], 0, 0, 1, 0)
		if (sysdate < date2) {
			errorMessage += "Date of death can not be Future Date.\n";
			if (status) {
				status = false;
				$jq("#dod").focus();
			}
		}

		}
		
	if ($jq.trim($jq("#appname").val()) == "") {
		errorMessage += "Please enter Name of the Applicant.\n";
		if (status) {
			status = false;
			$jq("#appname").focus();
		}
	}
	if ($jq("#relationShipID").val() == "0") {
		errorMessage += "Please select Relationship.\n";
		if (status) {
			status = false;
			$jq("#relationShipID").focus();
		}
	}

	if ($jq("#advanceAmount").val() == "") {
		errorMessage += "Please enter Amount of advance applied.\n";
		if (status) {
			status = false;
			$jq("#advanceAmount").focus();
		}
	}
	if ($jq("#address").val() == "") {
		errorMessage += "Please enter Residential Address.\n";
		if (status) {
			status = false;
			$jq("#address").focus();
		}
	}
	if ($jq("#approvedBy").val() == "") {
		errorMessage += "Please enter Approved by.\n";
		if (status) {
			status = false;
			$jq("#approvedBy").focus();
		}
	}
	if (status) {
		var requestDetails = {
			"nodeID" : nodeID,
			"refSfID" : $jq("#refSfID").val().toUpperCase(),
			"dod" : $jq("#dod").val(),
			"appname" : $jq.trim($jq("#appname").val()),
			"advanceAmount" : $jq("#advanceAmount").val(),
			"relationShipID" : $jq("#relationShipID").val(),
			"witness1" : $jq("#witness1").val(),
			"witness2" : $jq("#witness2").val(),
			"address" : $jq("#address").val(),
			"approvedBy" : $jq("#approvedBy").val(),
			"param" : "reliefManage"
		};
		$jq.ajax( {
			type : "POST",
			url : 'loan.htm?' + dispUrl,
			data : requestDetails,
			cache : false,
			success : function(html) {
				$jq('#result').html(html);
				if ($jq('.success').is(':visible')) {
					clearRelief();
				}
			}
		});
	} else {
		alert(errorMessage);
	}
}

function editRelief(id, sfid, dod, name, relID, advamt, witness1, witness2,
		addr, approved) {
	nodeID = id;
	$jq('#refSfID').val(sfid);
	$jq('#dod').val(dod);
	$jq('#appname').val(name);
	$jq('#relationShipID').val(relID);
	$jq('#advanceAmount').val(advamt);
	$jq('#witness1').val(witness1);
	$jq('#witness2').val(witness2);
	$jq('#address').val(addr);
	$jq('#approvedBy').val(approved);
}

function deleteRelief(id) {
	var del = confirm("Do you want to delete this item?");
	if (del == true) {
		var requestDetails = {
			"nodeID" : id,
			"param" : "deleteRelief"
		};
		$jq.ajax( {
			type : "POST",
			url : 'loan.htm?' + dispUrl,
			data : requestDetails,
			cache : false,
			success : function(html) {
				$jq("#result").html(html);
				clearRelief();
			}
		});
	}
}
function CheckDeathReliefAmountValid(e){
	if($jq('#advanceAmount').val() <= 8000){
		return true;
	}else{
		alert("Advance amount Limit is 8000");
		$jq('#advanceAmount').val('');
		$jq('#advanceAmount').focus()
		return false;
	}
}
// Loan amount starts
function stepGrid(stepID) {
	return '<fieldset><legend><strong><font color=green><span id=stepNo>'
			+ stepID + '</span> Time</font></strong></legend><table id=step'
			+ stepID + ' cellpadding=0 cellspacing=0 width=100%>'
			+ '<tr><td id=tpay></td></tr>' + '<tr><td id=mpay></td></tr>'
			+ '<tr><td id=gpf></td></tr>' + '</table></fieldset>';
}

function initialGrid() {
	headID = 1;
	return '<table id=initGrid cellpadding=0 cellspacing=0 width=100%>'
			+ '<tr><td width=80%>'
			+ stepGrid(headID)
			+ '</td>'
			+ '<td width=10%></td>'
			+ '<td width=5%></td>'
			+ '<td width=5% id=stepBut><input type=button value=+ onclick=nextGrid() /></td>'
			+ '</tr>' + '</table>';
}

function nextGrid() {
	headID++;
	var row = '<tr><td width=80%>'
			+ stepGrid(headID)
			+ '</td>'
			+ '<td width=10%>'
			+ '<select id=subSeqRelation style=width:100% onchange=subSeqRelationChange(this)> '
			+ '<option value=0 >Select</option><option value=M >No.Of Months</option><option value=R >Previous Recovery</option> '
			+ '</select>' 
			+ '</td>'
			+ '<td width=5%><input type=text id=subSeqMonths style=width:90%;display:none /></td>'
			+ '<td width=5%><input type=button value=- onclick=deleteGridRow(this) /></td>'
			+ '</tr>';
	$jq('#initGrid').append(row);
	impactOnPay();
	impactOnBalance();
	impactOnNoOfMonthsPay();
	setStepIDs();
}

function subSeqRelationChange(e)
{
	if($jq(e).parent().find('#subSeqRelation').val()== 0 || $jq(e).parent().find('#subSeqRelation').val()== 'R'){
		$jq(e).parent().parent().find('#subSeqMonths').val('0');
		$jq(e).parent().parent().find('#subSeqMonths').hide();
	}
	else{
	$jq(e).parent().parent().find('#subSeqMonths').show();
	}
}
function checkFirst() {
	if ($jq.trim($jq('#grid').text()) == '') {
		// New
		$jq('#grid').append(initialGrid());
		impactOnTime();
	}
}
function resetGridCheck() {
	if (!($jq('input:radio[name=impactOnPayFlag]:checked').val() == "Y")
			&& !($jq('input:radio[name=impactOnBalanceFlag]:checked').val() == "Y")
			&& !($jq('input:radio[name=impactOnNoOfMonthsPayFlag]:checked').val() == "Y")) {
		$jq('#grid').text('');
	}
}

function impactOnPay() {
	if ($jq('input:radio[name=impactOnPayFlag]:checked').val() == "Y") {
		checkFirst();
		payStructureGrid('create');
		impactOnTimeDA();
	} else {
		payStructureGrid('delete');
		resetGridCheck();
	}
}

function payStructureGrid(type) {
	if (type == 'create') {
		$jq("#initGrid tr").each(function() {
			if ($jq.trim($jq(this).find("#tpay").html()) == '') {
				$jq(this).find("#tpay").html(createPayStr(''));
			}
		});
	} else {
		$jq("#initGrid tr").each(function() {
			$jq(this).find("#tpay").text('');
		});
	}
}

function createPayStr(obj) {
	return '<table id=payGrid cellpadding=0 cellspacing=0 width=100%>' + createPayRow(
			'', 'first',obj) + '</table>';
}

function createPayRow(e, type,obj) {
	var amount = "amount";
	var payOrGPF="pay";
	var da = "da";
	var color ="#A29A9A";
	if(obj!=''){
		amount = obj.amount;
		payOrGPF=obj.payOrGpf;
		da = obj.da;
		color ="#000";
	}	
	
	var row = '<tr>';
	row += '<td style=width:30%><input type=text name=amount value='+amount+' style=color:'+color+' onclick=\"javascript:emptyTextBox(this);\" onblur=\"javascript:setMessage(this);\"/></td>';
	row += '<td style=width:10%>></td>';
	row += '<td style=width:30%><input type=text name=pay value='+payOrGPF+' style=color:'+color+' onclick=\"javascript:emptyTextBox(this);\" onblur=\"javascript:setMessage(this);\" /></td>';
	row += '<td style=width:20% class=da><input type=text name=da value='+da+' style=color:'+color+' size=10 onclick=\"javascript:emptyTextBox(this);\" onblur=\"javascript:setMessage(this);\" />%</td>';
	if (type == 'first') {
		row += "<td style=width:10%><input type=button class=smallbtn value=+ onclick=\"javascript:createPayRow(this,'next','');\" /></td>";
	} else {
		row += "<td style=width:10%><input type=button  class=smallbtn value=- onclick=\"javascript:deleteRow(this);\" /></td>";
	}
	row += "</tr>";
	if (type != 'first') {
		$jq(e).parent().closest("table").append(row);
		impactOnTimeDA();
	} else {
		return row;
	}
}

function emptyTextBox(e){
	funtype = $jq(e).val();
	if(funtype=='amount' || funtype=='pay' || funtype=='da' || funtype=='Times' || funtype=='balance'){
		$jq(e).val('');
		$jq(e).attr('style','color:#000');
	}
}

function setMessage(e){
	if($jq.trim($jq(e).val())==''){
		$jq(e).val(funtype);
		$jq(e).attr('style','color:#A29A9A');
	}
}
function deleteRow(e) {
	$jq(e).parent().closest("tr").remove();
}

function deleteGridRow(e) {
	$jq(e).parent().closest("tr").remove();
	setStepIDs();
}

function setStepIDs() {
	var count = 1;
	$jq("#initGrid >tbody >tr").each(function() {
		$jq(this).find("#stepNo").text(count);
		count++;
	});
}

function impactOnBalance() {
	if ($jq('input:radio[name=impactOnBalanceFlag]:checked').val() == "Y") {
		checkFirst();
		balanceStructure('create');
		impactOnTimeDA();
	} else {
		balanceStructure('delete');
		resetGridCheck();
	}
}

function balanceStructure(type) {
	if (type == 'create') {
		$jq("#initGrid tr").each(function() {
			if ($jq.trim($jq(this).find("#gpf").text()) == '') {
				$jq(this).find("#gpf").html(createBalanceStr(''));
			}
		});
	} else {
		$jq("#initGrid tr").each(function() {
			$jq(this).find("#gpf").text('');
		});
	}
}

function createBalanceStr(obj) {
	var amount = "balance";
	var da = "da";
	var color ="#A29A9A";
	if(obj!=''){
		amount = obj.amount;
		da = obj.da;
		color ="#000";
	}	
	return '<table id=balanceGrid cellpadding=0 cellspacing=0 width=100%><tr>'
			+ '<td width=30%><input type=text name=amount value='+amount+' style=color:'+color+' onclick=\"javascript:emptyTextBox(this);\" onblur=\"javascript:setMessage(this);\" />%</td>'
			+ '<td width=10%></td>' + '<td width=30%>GPF</td>'
			+ '<td width=20% class=da><input type=text name=da value='+da+' style=color:'+color+' size=10 onclick=\"javascript:emptyTextBox(this);\" onblur=\"javascript:setMessage(this);\" />%</td>'
			+ '<td width=10%></td>' + '</tr></table>';
}

function impactOnNoOfMonthsPay() {
	if ($jq('input:radio[name=impactOnNoOfMonthsPayFlag]:checked').val() == "Y") {
		checkFirst();
		monthlyPayStructure('create');
		impactOnTimeDA();
	} else {
		monthlyPayStructure('delete');
		resetGridCheck();
	}
}

function monthlyPayStructure(type) {
	if (type == 'create') {
		$jq("#initGrid tr").each(function() {
			if ($jq.trim($jq(this).find("#mpay").text()) == '') {
				$jq(this).find("#mpay").html(createMonthlyPay(''));
			}
		});
	} else {
		$jq("#initGrid tr").each(function() {
			$jq(this).find("#mpay").text('');
		});
	}
}

function createMonthlyPay(obj) {
	var amount = "Times";
	var da = "da";
	var color ="#A29A9A";
	if(obj!=''){
		amount = obj.amount;
		da = obj.da;
		color ="#000";
	}
	return '<table id=monthlyPayGrid cellpadding=0 cellspacing=0 width=100%><tr>'
			+ '<td width=30%><input type=text name=amount value='+amount+' style=color:'+color+' onclick=\"javascript:emptyTextBox(this);\" onblur=\"javascript:setMessage(this);\" /></td>'
			+ '<td width=10%></td>'
			+ '<td width=30%>Pay</td>'
			+ '<td width=20% class=da><input type=text name=da value='+da+' style=color:'+color+' size=10 onclick=\"javascript:emptyTextBox(this);\" onblur=\"javascript:setMessage(this);\" />%</td>'
			+ '<td width=10%></td>' + '</tr></table>';
}

function impactOnTimeDA() {
	if ($jq('input:radio[name=impactOnDAFlag]:checked').val() == "Y") {
		$jq('.da').show();
		$jq('#daPercentDiv').show();
	} else {
		$jq('input[name=da]').val('1');
		$jq('.da').hide();
		$jq('#daPercentDiv').hide();
		$jq('#daPercentage').val('1');
	}
}

function impactOnTime() {
	if ($jq('input:radio[name=impactOnTimesFlag]:checked').val() == "Y") {
		$jq('#stepBut').show();
	} else {
		$jq("#initGrid >tbody >tr:not(:first)").each(function() {
			$jq(this).remove();
		});
		$jq('#stepBut').hide();
	}
}

function getLoanAmountGrid() {
	globalMessage = "";
	var loanAmountGrid = {};
	var i = 0;
	var timeID = 1;
	$jq("#initGrid").find('table[id^="step"]').each(function() {
		var stepRow = {};
		stepRow['payGrid'] = getPayGrid(this, timeID);
		stepRow['monthlyPay'] = monthlyPayRow(this, timeID);
		stepRow['gpfBalance'] = gpfBalanceRow(this, timeID);
		stepRow['subSeqRelation'] = getSubSeqRelation(this, timeID);
		stepRow['subSeqMonths'] = getSubSeqMonths(this, timeID);
		loanAmountGrid[i] = stepRow;
		i++;
		timeID++;
	});
	return loanAmountGrid;
}
function getSubSeqRelation(e, timeID) {
	if (timeID > 1) {
		if ($jq(e).parent().parent().parent().find('#subSeqRelation').val() == '0')
			globalMessage += 'Please select SubSequence relation in ' + timeID + ' time \n';
		return $jq(e).parent().parent().parent().find('#subSeqRelation').val();
	} else {
		return '';
	}
}

function getSubSeqMonths(e, timeID) {
	if (timeID > 1) {
		if ($jq(e).parent().parent().parent().find('#subSeqMonths').val() == '')
			globalMessage += 'Please select SubSequence Months in ' + timeID + ' time \n';
		return $jq(e).parent().parent().parent().find('#subSeqMonths').val();
	} else {
		return '0';
	}
}

function getPayGrid(e, timeID) {
	var payGrid = {};
	var i = 0;
	$jq(e)
			.find('#tpay')
			.find('table#payGrid >tbody >tr')
			.each(
					function() {
						var payRow = {};
						if ($jq(this).find("td").eq(0).find('input').val() == ''
								|| isNaN($jq(this).find("td").eq(0).find(
										'input').val())) {
							globalMessage += 'Please enter amount in ' + timeID
									+ ' time ' + (i + 1) + ' row\n';
						} else {
							payRow['amount'] = $jq(this).find("td").eq(0).find(
									'input').val();
						}
						payRow['relation'] = $jq.trim($jq(this).find("td")
								.eq(1).text());
						if ($jq(this).find("td").eq(2).find('input').val() == ''
								|| isNaN($jq(this).find("td").eq(2).find(
										'input').val())) {
							globalMessage += 'Please enter pay in ' + timeID
									+ ' time ' + (i + 1) + ' row\n';
						} else {
							payRow['pay'] = $jq(this).find("td").eq(2).find(
									'input').val();
						}
						if ($jq('input:radio[name=impactOnDAFlag]:checked')
								.val() == "Y"
								&& ($jq(this).find("td").eq(3).find('input')
										.val() == '' || isNaN($jq(this).find(
										"td").eq(3).find('input').val()))
								|| $jq(this).find("td").eq(3).find('input')
										.val() < 1 ||$jq(this).find("td").eq(3).find('input').val() > 100 ) {
							globalMessage += 'Please enter da in ' + timeID
									+ ' time ' + (i + 1) + ' row and it should be between 1 to 100\n';
						} else {
							payRow['da'] = $jq(this).find("td").eq(3).find(
									'input').val();
						}
						payGrid[i] = payRow;
						i++;
					});
	return payGrid;
}

function monthlyPayRow(e, timeID) {
	var monthlyPayRow = {};
	$jq(e)
			.find('#mpay')
			.find('table#monthlyPayGrid >tbody >tr')
			.each(
					function() {
						if ($jq(this).find("td").eq(0).find('input').val() == ''|| isNaN($jq(this).find("td").eq(0).find('input').val())) {
							globalMessage += 'Please enter number of times in ' + timeID + ' time \n';
						} else {
							monthlyPayRow['amount'] = $jq(this).find("td")
									.eq(0).find('input').val();
						}
						monthlyPayRow['relation'] = 'T';
						monthlyPayRow['pay'] = 'P';
						if ($jq('input:radio[name=impactOnDAFlag]:checked')
								.val() == "Y"
								&& ($jq(this).find("td").eq(3).find('input')
										.val() == '' ||isNaN($jq(this).find("td").eq(3).find('input').val()))) {
							globalMessage += 'Please enter da in pay ' + timeID + ' time \n';
						} else {
							monthlyPayRow['da'] = $jq(this).find("td").eq(3)
									.find('input').val();
						}
					});
	return monthlyPayRow;
}

function gpfBalanceRow(e, timeID) {
	var gpfBalanceRow = {};
	$jq(e)
			.find('#gpf')
			.find('table#balanceGrid >tbody >tr')
			.each(
					function() {
						if ($jq(this).find("td").eq(0).find('input').val() == '' || isNaN($jq(this).find("td").eq(0).find('input').val())) {
							globalMessage += 'Please enter GPF % in ' + timeID + ' time \n';
						} else {
							gpfBalanceRow['amount'] = $jq(this).find("td")
									.eq(0).find('input').val();
						}

						gpfBalanceRow['relation'] = '%';
						gpfBalanceRow['pay'] = 'G';
						if ($jq('input:radio[name=impactOnDAFlag]:checked')
								.val() == "Y"
								&& ($jq(this).find("td").eq(3).find('input')
										.val() == '' || isNaN($jq(this).find("td").eq(3).find('input').val()))) {
							globalMessage += 'Please enter da in GPF ' + timeID + ' time \n';
						} else {
							gpfBalanceRow['da'] = $jq(this).find("td").eq(3)
									.find('input').val();
						}
					});
	return gpfBalanceRow;
}

function editLoanAmountDetails(amountID){
	clearLoanAmountDetails();
	nodeID = amountID;
	if(jsonLoanAmountDetails!=null){
		for(var i = 0; i < jsonLoanAmountDetails.length; i++){
			if(jsonLoanAmountDetails[i].id==amountID){
				$jq("#loanType").val(jsonLoanAmountDetails[i].loanTypeID);
//				selectedSubLoan();				
//				$jq("#loanSubType").val(jsonLoanAmountDetails[i].loanSubTypeID);
				$jq("#gazType").val(jsonLoanAmountDetails[i].gazType);
				selectedDesignation();
				$jq("input:radio[name=impactOnPayFlag]").filter("[value="+jsonLoanAmountDetails[i].payFlag+"]").attr("checked","checked");
				$jq("input:radio[name=impactOnTimesFlag]").filter("[value="+jsonLoanAmountDetails[i].multipleFlag+"]").attr("checked","checked");
				$jq("input:radio[name=impactOnDAFlag]").filter("[value="+jsonLoanAmountDetails[i].daFlag+"]").attr("checked","checked");
				$jq("input:radio[name=impactOnBalanceFlag]").filter("[value="+jsonLoanAmountDetails[i].balanceFlag+"]").attr("checked","checked");
				$jq("input:radio[name=impactOnNoOfMonthsPayFlag]").filter("[value="+jsonLoanAmountDetails[i].monthsFlag+"]").attr("checked","checked");
				$jq("#daPercentage").val(jsonLoanAmountDetails[i].daPercentage);					
			}
		}
		if(jsonLoanDesigMappings!=null){
			for(var i = 0; i < jsonLoanDesigMappings.length; i++){
				if(jsonLoanDesigMappings[i].loanAmountID==amountID){
					$jq("#SelectLeft option").each(function() {
						if($jq(this).val()==jsonLoanDesigMappings[i].designationID){							
							$jq("#SelectRight").append('<option value="' + $jq(this).val() + '">' + $jq(this).text() + '</option>');
							$jq(this).remove();
						}
					});				
				}
			}
		}
		if(jsonLoanAmountGrid!=null){
			var stageID = 0;			
			for(var i = 0; i < jsonLoanAmountGrid.length; i++){
				if(jsonLoanAmountGrid[i].loanAmountID==amountID){					
					if(stageID==0){
						checkFirst();
						stageID = jsonLoanAmountGrid[i].stageID;						
					} else if(stageID!=jsonLoanAmountGrid[i].stageID){
						nextGrid();
						if(jsonLoanAmountGrid[i].subseqRelation != '' )
						{
						$jq('#step'+stageID).parent().parent().parent().parent().find("#subSeqRelation").val(jsonLoanAmountGrid[i].subseqRelation);
						subSeqRelationChange($jq('#step'+stageID).parent().parent().parent());
						}
						$jq('#step'+stageID).parent().parent().parent().parent().find("#subSeqMonths").val(jsonLoanAmountGrid[i].subseqMonths);
						stageID = jsonLoanAmountGrid[i].stageID;
						$jq('#step'+stageID+' >tbody >tr').find('#tpay').html('');
					} 
					if (jsonLoanAmountGrid[i].payBalanceRelation == 1) {
						// PayGrid
						if($jq.trim($jq('#step'+stageID+' >tbody >tr').find('#tpay').text())==''){
							$jq('#step'+stageID+' >tbody >tr').find('#tpay').html(createPayStr(jsonLoanAmountGrid[i]));
						} else {							
							createPayRow($jq('#step'+stageID+' >tbody >tr').find('#tpay').find('#payGrid >tbody >tr'), 'next',jsonLoanAmountGrid[i])
						}
					} else if (jsonLoanAmountGrid[i].payBalanceRelation == 2) {
						// GPF Balance
						$jq('#step'+stageID+' >tbody >tr').find('#gpf').html(createBalanceStr(jsonLoanAmountGrid[i]));
					} else if (jsonLoanAmountGrid[i].payBalanceRelation == 3) {
						// Monthly Pay
						$jq('#step'+stageID+' >tbody >tr').find('#mpay').html(createMonthlyPay(jsonLoanAmountGrid[i]));
					}
					impactOnTimeDA();
				}
			}
			impactOnTime();
			setStepIDs();
		}
	}
}

function deleteLoanAmountDetails(id) {
	var check = confirm("Do you want to delete ?");
	if (check) {
		var requestDetails = {
			"param" : "loanAmountDetailsDelete",
			"nodeID" : id
		};
		$jq.post('loan.htm?' + dispUrl, requestDetails, function(html) {
			$jq("#loanAmountDetails").html(html);
			clearLoanDetails();
		});
	}
}

function viewDeathForm(sfID){
	window.open('./report.htm?param=loanReport&requestID='+sfID+'&type=death','Loan','fullscreen=no,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no')
}

//Sending Report Starts
function clearSendingReport(){
	$jq('input:text').val("");
	$jq('select').val("0");
	$jq('#cdaReportNoDiv').hide();
	$jq('#hqReportDateDiv').hide();
	$jq('#submitBut').hide();
	$jq('#reportDateDiv').hide();
	$jq('#loanRequestDeatails').hide();
}

function searchSendingReport() {
	var errorMessages = "";
	var status = true;
	if ($jq('#financialYear').val() == '0') {
		errorMessages += "please select financial year\n";
		$jq('#cdaReportNoDiv').hide();
		$jq('#hqReportDateDiv').hide();
		if (status) {
			status = false;
			$jq("#financialYear").focus();
		}
	}
	if (status) {
		var requestDetails = {
			"financialYear" : $jq('#financialYear').val(),
			"loanType" : $jq('#loanType').val(),
			"param" : "searchSendingReport"
		};
		$jq.post('loan.htm?' + dispUrl, requestDetails, function(html) {
			$jq("#loanRequestDeatails").html(html);
		});

		$jq('#submitBut').show();
		$jq('#clearBut').show();
		$jq('#reportNoDiv').show();
		$jq('#reportDateDiv').show();
		$jq('#loanRequestDeatails').show();
	} else {
		alert(errorMessages);
	}
}

function submitSendingReport() {
	var errorMessages = "";
	var checkedRequests = "";
	var status = true;

	if ($jq('#reportNumber').val() == '') {
		errorMessages += "please Enter Report Number\n";
		if (status) {
			status = false;
			$jq("#reportNumber").focus();
		}
	}
	if ($jq('#reportDate').val() == '') {
		errorMessages += "please Enter Report Date\n";
		if (status) {
			status = false;
			$jq("#reportDate").focus();
		}
	}
	if ($jq('#dataList') == null) {
		errorMessages += "Please Select Requests for submission\n";
		status = false;
	} else {
		$jq('#dataList tbody tr').each(function()
		{
			if ($jq(this).find('input').attr('checked')) {
				checkedRequests += ($jq(this).find('input').attr('id')) + ",";
			}
		});

	}
	if (checkedRequests == '') {
		errorMessages += "Please Select Requests for submission in the table\n";
		status = false;
	}

	if (status) {
		var requestDetails = {
			"reportNumber" : $jq('#reportNumber').val(),
			"reportDate" : $jq('#reportDate').val(),
			"param" : "submitSendingReport",
			"requests" : checkedRequests
		};
		$jq.post('loan.htm?' + dispUrl, requestDetails, function(html) {
			$jq("#result").html(html);
			searchSendingReport();
			$jq('#reportNumber').val('');
			$jq('#reportDate').val('');
		});
		

	} else {
		alert(errorMessages);
	}

}
//Sending Report ends

//cda report starts
function clearHQReport(){
	$jq('input:text').val("");
	$jq('#result').html('');
	$jq('select').val("0");
	$jq('#submitBut').hide();
	$jq('#reGenerateBut').hide();
	$jq('#cdaReportNoDiv').hide();
	$jq('#hqReportDateDiv').hide();
	$jq('#loanReportDetails').hide();
	
}
function searchHQReport(){
	var errorMessages = "";
	var status = true;
	if ($jq('#reportNumber').val() == '0') {
		errorMessages += "please select Report Number\n";
		clearHQReport();
		if (status) {
			status = false;
			$jq("#reportNumber").focus();
		}
	}
	
	if (status) {

		var requestDetails = {
			"reportNumber" : $jq('#reportNumber').val(),
			"loanType":$jq('#loanType').val()=="selected"?null:$jq('#loanType').val(),
			"param" : "searchHQReport"
		};
		$jq.post('loan.htm?' + dispUrl, requestDetails, function(html) {
			$jq("#loanReportDetails").html(html);
		});
		$jq('#submitBut').show();
		$jq('#clearBut').show();
		$jq('#reGenerateBut').show();
		$jq('#cdaReportNoDiv').show();
		$jq('#hqReportDateDiv').show();
		$jq('#loanReportDetails').show();

	} else {
		alert(errorMessages);
	}
}
function reGenerateHQReport(){
	var errorMessages = "";
	var status = true;
	
	if ($jq("#dataList tbody tr td").html() == "Nothing found to display.") {
		errorMessages += 'Please get the Employee list';
		if (status) {
			status = false;
		}
	}
	else{
	var checkbox=true;
	$jq("#dataList tbody tr").each(function() {
		if ($jq(this).find('td').eq(0).find('input').is(':checked')){
			checkbox=false;
		}
	});
	if (checkbox) {
	errorMessages += "Please Select Requests for submission\n";
	status = false;
	}
	 else {
var cdaReportGrid = {};
var i=0;
$jq("#dataList tbody tr").each(function() {
	if ($jq(this).find('td').eq(0).find('input').attr('checked')) {
	var row={};
	row['reqId']=$jq.trim($jq(this).find('td').eq(1).text());
	row['id']=$jq(this).find('td').eq(5).find('input').attr('id');
	row['cdaID']=$jq(this).find('td').eq(11).find('input').attr('id');
	cdaReportGrid[i] = row;
	i++
	}
});
}
}
	if (status) {
		var requestDetails = {
		"cdaReportGrid" : JSON.stringify(cdaReportGrid),
		"param" : "reGenerateHQReport"
		};
		$jq.post('loan.htm?' + dispUrl,requestDetails,function(html) {
				$jq("#loanReportDetails").html('');
				$jq("#result").html(html);
				if($jq('.success').is(':visible')){
					clearHQReport();
				$jq("#result").html(html);
				}
		});
	 } else {
		alert(errorMessages);
	}
	
}
function submitHQReport() {
	var errorMessages = "";
	var status = true;
	$jq('#result').html('');

	if ($jq('#hqReportNumber').val() == '') {
		errorMessages += "please Enter HQ Report Number\n";
		if (status) {
			status = false;
			$jq("#hqReportNumber").focus();
		}
	}
	if ($jq('#hqReportDate').val() == '') {
		errorMessages += "please Enter HQ Report Date\n";
		if (status) {
			status = false;
			$jq("#hqReportDate").focus();
		}
	}
	
	if ($jq("#dataList tbody tr td").html() == "Nothing found to display.") {
		errorMessages += 'Please get the Employee list';
		if (status) {
			status = false;
		}
	}
	
	else{
		var checkbox=true;
		$jq("#dataList tbody tr").each(function() {
			if ($jq(this).find('td').eq(0).find('input').is(':checked')){
				checkbox=false;
			}
		});
		if (checkbox) {
		errorMessages += "Please Select Requests for submission\n";
		status = false;
		}
		 else {
	var cdaReportGrid = {};
	var i=0;
	var j=0;
	$jq("#dataList tbody tr").each(function() {
		if ($jq(this).find('td').eq(0).find('input').attr('checked')) {
		var row={};
		row['reqId']=$jq.trim($jq(this).find('td').eq(1).text());
		row['id']=$jq(this).find('td').eq(5).find('input').attr('id');
		if($jq(this).find('td').eq(5).find('input').val()=='')
		{
			errorMessages += 'Please enter Santioned amount in ' + (j + 1) + ' row\n';
			if (status) {
				status = false;
			}
		}
		else{
			if(isNaN($jq(this).find('td').eq(5).find('input').val())){
				errorMessages += 'Please enter a valid number in Santioned amount of ' + (i + 1) + ' row\n';
				if (status) {
					status = false;
				}
			}
			else{
			if(parseFloat($jq(this).find('td').eq(5).find('input').val() > parseFloat($jq.trim($jq(this).find('td').eq(4).text()))) || ($jq(this).find('td').eq(5).find('input').val()<=0) )
			{
				errorMessages += 'Sanctioned amount can not be greater than requested amount or negative in ' + (j + 1) + ' row\n';
				if (status) {
					status = false;
				}
			}
			else{
		row['sanAmount']=$jq(this).find('td').eq(5).find('input').val();
			}
			}
		}
		if($jq(this).find('td').eq(6).find('input').val()=='')
		{
			errorMessages += 'Please select recovery start date in ' + (j + 1) + ' row\n';
			if (status) {
				status = false;
			}
		}else if($jq(this).find('td').eq(6).find('input').val()!=''){
			var hqReportDate=$jq('#hqReportDate').val();
			var sandate = new Date(hqReportDate.split("-")[2], getMonthID(hqReportDate
					.split("-")[1]) - 1, hqReportDate.split("-")[0], 0, 0, 1, 0);
			var dodDate = $jq(this).find('td').eq(6).find('input').val();
			var date2 = new Date(dodDate.split("-")[2], getMonthID(dodDate
					.split("-")[1]) - 1, dodDate.split("-")[0], 0, 0, 1, 0)
			if (sandate > date2) {
				errorMessages += 'recovery start date can not be Past Date in ' + (j + 1) + ' row\n';
				if (status) {
					status = false;
				}
			}else{	
			row['recDate']=$jq(this).find('td').eq(6).find('input').val();
			}
		}
		if($jq(this).find('td').eq(7).find('input').val()=='')
		{
			errorMessages += 'Please select principle Installments in ' + (j + 1) + ' row\n';
			if (status) {
				status = false;
			}
		}
		else{
		row['installment']=$jq(this).find('td').eq(7).find('input').val();
		}
		if($jq(this).find('td').eq(8).find('input').val()=='')
		{
			errorMessages += 'Please select Principle Emi in ' + (j + 1) + ' row\n';
			if (status) {
				status = false;
			}
		}
		else{
		row['emi']=$jq(this).find('td').eq(8).find('input').val();
		}
		if($jq(this).find('td').eq(9).find('input').val()=='')
		{
			errorMessages += 'Please select Principle last Emi in ' + (j + 1) + ' row\n';
			if (status) {
				status = false;
			}
		}
		else{
		row['lastEmi']=$jq(this).find('td').eq(9).find('input').val();
		}
		if($jq(this).find('td').eq(10).find('input').val()=='')
		{
			errorMessages += 'Please select Interest Amount in ' + (j + 1) + ' row\n';
			if (status) {
				status = false;
			}
		}
		else{
		row['interestAmount']=$jq(this).find('td').eq(10).find('input').val();
		}
		if($jq(this).find('td').eq(11).find('input').val()=='')
		{
			errorMessages += 'Please select Interest Installments in ' + (j + 1) + ' row\n';
			if (status) {
				status = false;
			}
		}
		else{
		row['intInstallments']=$jq(this).find('td').eq(11).find('input').val();
		}
		if($jq(this).find('td').eq(12).find('input').val()=='')
		{
			errorMessages += 'Please select Interest EMI in ' + (j + 1) + ' row\n';
			if (status) {
				status = false;
			}
		}
		else{
		row['interestEmi']=$jq(this).find('td').eq(12).find('input').val();
		}
		if($jq(this).find('td').eq(13).find('input').val()=='')
		{
			errorMessages += 'Please select Interest last EMI in ' + (j + 1) + ' row\n';
			if (status) {
				status = false;
			}
		}
		else{
		row['interestLastEmi']=$jq(this).find('td').eq(13).find('input').val();
		}
		row['cdaID']=$jq(this).find('td').eq(11).find('input').attr('id');
		cdaReportGrid[i] = row;
		i++
		}
		j++;
	});
	}
	}
	
	if (status) {
		var requestDetails = {
		"hqReportNumber" : $jq('#hqReportNumber').val(),
		"hqReportDate" : $jq('#hqReportDate').val(),
		"cdaReportGrid" : JSON.stringify(cdaReportGrid),
		"param" : "submitCDAReport"
		};
		$jq.post('loan.htm?' + dispUrl,requestDetails,function(html) {
				$jq("#result").html(html);
				if($jq('.success').is(':visible')){	
					searchHQReport();
					$jq('#hqReportNumber').val('');
					$jq('#hqReportDate').val('');
				}
		});
	 } else {
		alert(errorMessages);
	}
	
}


//Hba Starts

function selectedHouseType() {
	$jq('#plotAndConstructionsDiv').hide();
	$jq('#enlargingHouseDiv').hide();
	$jq('#readyBuiltHouseDiv').hide();
	$jq('#miscellaneousDiv').hide();
	$jq('#miscellaneous').hide();
	$jq('#buttons').hide();
	$jq("input:radio[name=ruralOrUrban]").attr("checked", false);
	$jq("input:radio[name=demarAndDev]").attr("checked", false);
	$jq("input:radio[name=miscFlag]").attr("checked", false);
	$jq('input:text').val('');
	$jq('textarea').val('');
	$jq('#result').text('');
	$jq("#floorTable >tbody >tr").each(function() {
		if($jq(this).closest('tr').index() > 2 ){
			$jq(this).remove();
		}
	});
	if ($jq('#houseAdvanceType').val() == plotAndConstructionID ) {
		$jq('#plotAndConstructionsDiv').show();
		$jq('#miscellaneous').show();
		$jq('#buttons').show();
	}
	if ($jq('#houseAdvanceType').val() == enlargingExistingHouseID ) {
		$jq('#enlargingHouseDiv').show();
		$jq('#miscellaneous').show();
		$jq('#buttons').show();
	}
	if ($jq('#houseAdvanceType').val() == readyBuiltHouseID ) {
		$jq('#readyBuiltHouseDiv').show();
		$jq('#miscellaneous').show();
		$jq('#buttons').show();
	}
}

function clearHBADetails() {
	$jq('#plotAndConstructionsDiv').hide();
	$jq('#enlargingHouseDiv').hide();
	$jq('#readyBuiltHouseDiv').hide();
	$jq('#miscellaneousDiv').hide();
	$jq('#buttons').hide();
	$jq("input:radio").attr("checked", false);
	$jq('input:text').val('');
	$jq('textarea').val('');
	$jq('#miscellaneous').hide();
	$jq('select').val("0");
	$jq("#floorTable >tbody >tr").each(function() {
		if($jq(this).closest('tr').index() > 2 ){
			$jq(this).remove();
		}
	});
}

function manageHBADetails() {
	var status = true;
	errorMessages = "";
	$jq('#result').text('');
	if (!$jq('input:radio[name=pfFlag]').is(':checked')) {
		errorMessages += "Please select Amount of Provident Fund/any other advance/final withdrawal taken for purchase of  Land/construction  \n";
		if (status) {
		status = false;
		$jq('input:radio[name=pfFlag]').focus();
		}
	}
	if($jq('#offlineSFID').is(':visible') && $jq('#offlineSFID').val()==''){
		errorMessages += "Please Enter sfID \n";
		if (status) {
			status = false;
			$jq('#offlineSFID').focus();
			}
	}
	if ($jq('#houseAdvanceType').val() == '0' ) {
		errorMessages += "Please select House building advance Type \n";
		if (status) {
		status = false;
		$jq('#houseAdvanceType').focus();
		}
	}
	else if ($jq('#houseAdvanceType').val() == plotAndConstructionID ) {
		if ($jq.trim($jq('#locationWithAddress1').val()) == '') {
			errorMessages += "Please Enter Location with address \n";
			if (status) {
			status = false;
			$jq('#locationWithAddress1').focus();
				}
		}
		if (!$jq('input:radio[name=ruralOrUrban]').is(':checked')) {
			errorMessages += "Please Enter Rural/Urban \n";
			if (status) {
			status = false;
			$jq('#ruralOrUrban').focus();
			}
		}
		
		if (!$jq('input:radio[name=demarAndDev]').is(':checked')) {
			errorMessages += "Please Enter Is it clearly demarketed and developeds \n";
			if (status) {
			status = false;
			$jq('#demarAndDev').focus();
			}
		}
		if ($jq.trim($jq('#approximateArea').val()) == '') {
			errorMessages += "Please Enter Approximate Area(in sq.mtrs.) \n";
			if (status) {
			status = false;
			$jq('#approximateArea').focus();
			}
		}
		if ($jq.trim($jq('#cost').val()) == '') {
			errorMessages += "Please Enter (a)Cost  \n";
			if (status) {
			status = false;
			$jq('#cost').focus();
			}
		}
		if ($jq.trim($jq('#amountPaid').val()) == '') {
			errorMessages += "Please Enter (b)Amount actually paid \n";
			if (status) {
			status = false;
			$jq('#amountPaid').focus();
			}
		}
		/*if ($jq.trim($jq('#proposedAcquire').val()) == '') {
			errorMessages += "Please Enter when proposed to be acquire \n";
			if (status) {
			status = false;
			$jq('#proposedAcquire').focus();
			}
		}*/
		if ($jq.trim($jq('#unExPortionLease').val()) == '') {
			errorMessages += "Please Enter Un expired portion of lease if not free hold \n";
			if (status) {
			status = false;
			$jq('#unExPortionLease').focus();
			}
		}
		
		
		var floorGrid={};
		var i=0;
		var cost=0;
		$jq("#floorTable >tbody >tr").each(function() {
			if($jq(this).closest('tr').index() > 1 ){
			var row={}
			row['floorType']=$jq(this).find('td').eq(0).attr("id");
			if($jq(this).find('input').eq(0).val() ==''){
				errorMessages += "Please Enter Estimated Cost in row"+(i+1)+"\n";
				if (status) {
					status = false;
					$jq(this).find('input').eq(0).focus();
					}
			}else{
			row['estimatedCost']=$jq(this).find('input').eq(0).val();
			cost=parseFloat(cost)+parseFloat($jq(this).find('input').eq(0).val());
			}
			floorGrid[i]=row;
			i++;
			}}
		);
		if ($jq.trim($jq('#advanceReq1').val()) == '') {
			errorMessages += "Please Enter Amount of advance required \n";
			if (status) {
			status = false;
			$jq('#advanceReq1').focus();
			}
		}
		if ($jq.trim($jq('#noOfInstalPrinciple1').val()) == '') {
			errorMessages += "Please Enter No.of Principle installments for repayment \n";
			if (status) {
			status = false;
			$jq('#noOfInstalPrinciple1').focus();
			}
		}else if($jq('#noOfInstalPrinciple1').val()> 180 || $jq('#noOfInstalPrinciple1').val() < 1){
			errorMessages += "no of Principle amount installments should not be greater than 180 \n";
			if (status) {
			status = false;
			$jq('#noOfInstalPrinciple1').focus();
			}
		}
		if ($jq.trim($jq('#noOfInstalInterest1').val()) == '') {
			errorMessages += "Please Enter No.of Interest installments for repayment \n";
			if (status) {
			status = false;
			$jq('#noOfInstalInterest1').focus();
			}
		}else if($jq('#noOfInstalInterest1').val()> 60 || $jq('#noOfInstalInterest1').val() < 1){
			errorMessages += "no of Interest amount installments should not be greater than 60 \n";
			if (status) {
			status = false;
			$jq('#noOfInstalInterest1').focus();
			}
		}

			requestDetails = {
				"pfFlag": $jq('input:radio[name=pfFlag]:checked').val(),
				"houseAdvanceType" : $jq('#houseAdvanceType').val(),
				"locationWithAddress" : $jq('#locationWithAddress1').val(),
				"ruralOrUrban" : $jq('input:radio[name=ruralOrUrban]:checked').val(),
				"demarAndDev" : $jq('input:radio[name=demarAndDev]:checked').val(),
				"approximateArea" : $jq('#approximateArea').val(),
				"cost" : $jq('#cost').val(),
				"amountPaid" : $jq('#amountPaid').val(),
				"proposedAcquire" : $jq('#proposedAcquire').val(),
				"unExPortionLease" : $jq('#unExPortionLease').val(),
				"advanceReq" : $jq('#advanceReq1').val() ,
				"noOfInstalPrinciple" : $jq('#noOfInstalPrinciple1').val() ,
				"noOfInstalInterest" : $jq('#noOfInstalInterest1').val() ,
				"floorGrid":JSON.stringify(floorGrid),
				"miscFlag" : $jq('input:radio[name=miscFlag]:checked').val(),
				"houseTotalCost": cost
		}
			if (!$jq('input:radio[name=miscFlag]').is(':checked')) {
				errorMessages += "Please select Family already own(s) house state \n";
				if (status) {
				status = false;
				$jq('#miscFlag').focus();
				}
			}else{
				if($jq('input:radio[name=miscFlag]:checked').val() == "Y"){
					status = validateMisellaneousDiv(status);	
				}
			}
	} else if ($jq('#houseAdvanceType').val() == enlargingExistingHouseID ) {
		if ($jq.trim($jq('#locationWithAddress2').val()) == '') {
			errorMessages += "Please Enter Location with address \n";
			if (status) {
			status = false;
			$jq('#locationWithAddress2').focus();
			}
		}
		if ($jq.trim($jq('#plinthArea2').val()) == '') {
			errorMessages += "Please Enter Plinth area( in sq.mtrs.) \n";
			if (status) {
			status = false;
			$jq('#plinthArea2').focus();
			}
		}
		if ($jq.trim($jq('#pliProposedEnlarge').val()) == '') {
			errorMessages += "Please Enter Plinth area proposed for enlargement \n";
			if (status) {
			status = false;
			$jq('#pliProposedEnlarge').focus();
			}
		}
		if ($jq.trim($jq('#costAcquisition').val()) == '') {
			errorMessages += "Please Enter Cost of construction/ Acquisition of existing house \n";
			if (status) {
			status = false;
			$jq('#costAcquisition').focus();
			}
		}
		if ($jq.trim($jq('#costProposed').val()) == '') {
			errorMessages += "Please Enter cost of proposed enlargement \n";
			if (status) {
			status = false;
			$jq('#costProposed').focus();
			}
		}
		if ($jq.trim($jq('#totalPlinth').val()) == '') {
			errorMessages += "Please Enter Total plinth area \n";
			if (status) {
			status = false;
			$jq('#totalPlinth').focus();
			}
		}
		if ($jq.trim($jq('#totalCost').val()) == '') {
			errorMessages += "Please Enter Total Cost \n";
			if (status) {
			status = false;
			$jq('#totalCost').focus();
			}
		}
		if ($jq.trim($jq('#advanceReq2').val()) == '') {
			errorMessages += "Please Enter Amount of advance required \n";
			if (status) {
			status = false;
			$jq('#advanceReq2').focus();
			}
		}
		if ($jq.trim($jq('#noOfInstalPrinciple2').val()) == '') {
			errorMessages += "Please Enter No.of Principle installments for repayment \n";
			if (status) {
			status = false;
			$jq('#noOfInstalPrinciple2').focus();
			}
		}else if($jq('#noOfInstalPrinciple2').val()> 180 || $jq('#noOfInstalPrinciple2').val() < 1){
			errorMessages += "no of Principle amount installments should not be greater than 180 \n";
			if (status) {
			status = false;
			$jq('#noOfInstalPrinciple2').focus();
			}
		}
		if ($jq.trim($jq('#noOfInstalInterest2').val()) == '') {
			errorMessages += "Please Enter No.of Interest installments for repayment \n";
			if (status) {
			status = false;
			$jq('#noOfInstalInterest2').focus();
			}
		}else if($jq('#noOfInstalInterest2').val()> 60 || $jq('#noOfInstalInterest2').val() < 1){
			errorMessages += "no of Interest amount installments should not be greater than 60 \n";
			if (status) {
			status = false;
			$jq('#noOfInstalInterest2').focus();
			}
		}
			requestDetails = {
				"pfFlag": $jq('input:radio[name=pfFlag]:checked').val(),
				"houseAdvanceType" : $jq('#houseAdvanceType').val(),
				"locationWithAddress" : $jq('#locationWithAddress2').val(),
				"plinthArea" : $jq('#plinthArea2').val(),
				"pliProposedEnlarge" : $jq('#pliProposedEnlarge').val(),
				"costAcquisition" : $jq('#costAcquisition').val(),
				"costProposed" : $jq('#costProposed').val(),
				"totalPlinth" : $jq('#totalPlinth').val(),
				"totalCost" : $jq('#totalCost').val(),
				"advanceReq" : $jq('#advanceReq2').val(),
				"noOfInstalPrinciple" : $jq('#noOfInstalPrinciple2').val() ,
				"noOfInstalInterest" : $jq('#noOfInstalInterest2').val() ,
				"miscFlag" : $jq('input:radio[name=miscFlag]:checked').val(),
				"houseTotalCost": $jq('#totalCost').val()
		}
			if (!$jq('input:radio[name=miscFlag]').is(':checked')) {
				errorMessages += "Please select Family already own(s) house state \n";
				if (status) {
				status = false;
				$jq('#miscFlag').focus();
				}
			}else{
				if($jq('input:radio[name=miscFlag]:checked').val() == "Y"){
					status = validateMisellaneousDiv(status);	
				}
			}
	}else if($jq('#houseAdvanceType').val() == readyBuiltHouseID ) {
		if ($jq.trim($jq('#locationWithAddress3').val()) == '') {
			errorMessages = "Please Enter Location with address \n"
			if (status) {
			status = false;
			$jq('#locationWithAddress3').focus();
			}
		}
		if ($jq.trim($jq('#plinthArea3').val()) == '') {
			errorMessages += "Please Enter Plinth area( in sq.mtrs.) \n";
			if (status) {
			status = false;
			$jq('#plinthArea3').focus();
			}
		}
		if ($jq.trim($jq('#whenConstructed').val()) == '') {
			errorMessages += "Please Enter When constructed \n";
			if (status) {
			status = false;
			$jq('#whenConstructed').focus();
			}
		}
		if ($jq.trim($jq('#priceSettled').val()) == '') {
			errorMessages += "Please Enter price settled \n";
			if (status) {
			status = false;
			$jq('#priceSettled').focus();
			}
		}
		if ($jq.trim($jq('#agencyFrmPurchased').val()) == '') {
			errorMessages += "Please Enter The agency from whom to be purchased \n";
			if (status) {
			status = false;
			$jq('#agencyFrmPurchased').focus();
			}
		}
		if ($jq.trim($jq('#amtAlreadyPaid').val()) == '') {
			errorMessages += "Please Enter Amount (a)already paid \n";
			if (status) {
			status = false;
			$jq('#amtAlreadyPaid').focus();
			}
		}
		if ($jq.trim($jq('#amtToPaid').val()) == '') {
			errorMessages += "Please Enter (b)to be paid \n";
			if (status) {
			status = false;
			$jq('#amtToPaid').focus();
			}
		}
		if ($jq.trim($jq('#advanceReq3').val()) == '') {
			errorMessages += "Please Enter Amount of advance required \n";
			if (status) {
			status = false;
			$jq('#advanceReq3').focus();
			}
		}
		if ($jq.trim($jq('#noOfInstalPrinciple3').val()) == '') {
			errorMessages += "Please Enter No.of Principle installments for repayment \n";
			if (status) {
			status = false;
			$jq('#noOfInstalPrinciple3').focus();
			}
		}else if($jq('#noOfInstalPrinciple3').val()> 180 || $jq('#noOfInstalPrinciple3').val() < 1){
			errorMessages += "no of Principle amount installments should not be greater than 180 \n";
			if (status) {
			status = false;
			$jq('#noOfInstalPrinciple3').focus();
			}
		}
		if ($jq.trim($jq('#noOfInstalInterest3').val()) == '') {
			errorMessages += "Please Enter No.of Interest installments for repayment \n";
			if (status) {
			status = false;
			$jq('#noOfInstalInterest3').focus();
			}
		}else if($jq('#noOfInstalInterest3').val()> 60 || $jq('#noOfInstalInterest3').val() < 1){
			errorMessages += "no of Interest amount installments should not be greater than 60 \n";
			if (status) {
			status = false;
			$jq('#noOfInstalInterest3').focus();
			}
		}
			requestDetails = {
				"pfFlag": $jq('input:radio[name=pfFlag]:checked').val(),
				"houseAdvanceType" : $jq('#houseAdvanceType').val(),
				"locationWithAddress" : $jq('#locationWithAddress3').val(),
				"plinthArea" : $jq('#plinthArea3').val(),
				"whenConstructed" : $jq('#whenConstructed').val(),
				"priceSettled" : $jq('#priceSettled').val(),
				"agencyFrmPurchased" : $jq('#agencyFrmPurchased').val(),
				"amtAlreadyPaid" : $jq('#amtAlreadyPaid').val(),
				"amtToPaid" : $jq('#amtToPaid').val(),
				"advanceReq" : $jq('#advanceReq3').val(),
				"noOfInstalPrinciple" : $jq('#noOfInstalPrinciple3').val() ,
				"noOfInstalInterest" : $jq('#noOfInstalInterest3').val() ,
				"miscFlag" : $jq('input:radio[name=miscFlag]:checked').val(),
				"houseTotalCost": parseFloat($jq('#amtAlreadyPaid').val())+ parseFloat($jq('#amtToPaid').val())
		}	
			if (!$jq('input:radio[name=miscFlag]').is(':checked')) {
				errorMessages += "Please select Family already own(s) house state \n";
				if (status) {
				status = false;
				$jq('#miscFlag').focus();
				}
			}else{
				if($jq('input:radio[name=miscFlag]:checked').val() == "Y"){
					status = validateMisellaneousDiv(status);
				}
			}
	}
	
	if(status == true){
		if($jq('#offlineSFID').is(':visible') && $jq('#offlineSFID').val()!=''){
			requestDetails.offlineSFID=$jq('#offlineSFID').val();
		}
		requestDetails.param = "submitHBARequest";
		$jq.post('loanHbaRequest.htm', requestDetails, function(html) {
			$jq("#result").html(html);
			if ($jq('.success').is(':visible')) {
				 var check=confirm(" HBALoan  has been Successfully Sent...\nPreview Loan Application Form & print from here");
	     			if(check){
	     			document.forms[0].requestId.value = $jq.trim(requestIDHBALOAN);
	     		    document.forms[0].param.value = "requestDetails";
	     			document.forms[0].action = "workflowmap.htm";
	     			document.forms[0].submit();	
	     						}
				
			//clearHBADetails();
			}
	});
	}
	else {
		alert(errorMessages);
	}
}
function validateMisellaneousDiv(status){

	if ($jq.trim($jq('#locationWithAddress4').val()) == '') {
		errorMessages += "Please Enter Location with address of own House\n";
		if (status) {
		status = false;
		$jq('#locationWithAddress4').focus();
		}
	}
	if ($jq.trim($jq('#plinthAreaFW').val()) == '') {
		errorMessages += "Please Enter Plinth area(Floor wise) \n";
		if (status) {
		status = false;
		$jq('#plinthAreaFW').focus();
		}
	}
	if ($jq.trim($jq('#presentMarketValue').val()) == '') {
		errorMessages += "Please Enter Present fair market value \n";
		if (status) {
		status = false;
		$jq('#presentMarketValue').focus();
		}
	}
	if ($jq.trim($jq('#reasons').val()) == '') {
		errorMessages += "Please Enter Reasons \n";
		if (status) {
		status = false;
		$jq('#reasons').focus();
		}
	}
		requestDetails.miscLocationAddress = $jq('#locationWithAddress4').val();
		requestDetails.plinthAreaFW = $jq('#plinthAreaFW').val();
		requestDetails.presentMarketValue = $jq('#presentMarketValue').val();
		requestDetails.reasons = $jq('#reasons').val();
		return status;
}
function insertRow() {
	var row = "<tr>" +
			"<td></td>" +
			"<td><input type=text onkeypress=\"return isNumberExp(event);\"/></td>" +
			"<td></td>" +
			"<td></td>" +
			"<td><center><input type=button value=+ onclick='insertRow()' /></center></td>" +
			"<td><center><input type=button value=- onclick='deleteRow(this),setRowIDs()' /></center></td>" +
			"</tr>";
	
	$jq('#floorTable').append(row);
	setRowIDs();
}
function setRowIDs() {
	var count = 1;
	$jq("#floorTable >tbody >tr").each(function() {
		if($jq(this).closest('tr').index() > 2 ){
		$jq(this).find('td').eq(0).text(count+' Floor')
		$jq(this).find('td').eq(0).attr("id",count);
		count++;
		}
	});
}
//HBA interest Rates starts
function insertHBAInterestRow() {
	var row = "<tr>" +
			"<td><input type=text onkeypress=\"return isNumberExp(event);\"/></td>" +
			"<td><input type=text onkeypress=\"return isNumberExp(event);\"/></td>" +
			"<td><input type=text onkeypress=\"return isNumberExp(event);\"/></td>" +
			"<td><center><input type=button value=+ onclick='insertHBAInterestRow()' /></center></td>" +
			"<td><center><input type=button value=- onclick='deleteRow(this)' /></center></td>" +
			"</tr>";
	
	$jq('#HBAInterestGrid').append(row);
}
function manageHBAInterestRates(jsonLoanHBAInterestGridList){
	var status=true;
	if(nodeID == null)
	nodeID="";
	errorMessages="";
	var count=1;
	if($jq("#hbaInterestYear").val()==""){
		errorMessages+="please select Applicable Year\n "
			if(status){
				status=false;
				$jq("#hbaInterestYear").focus();
			}
	}
	var hbaInterestGrid={};
	var i=0;
	$jq("#HBAInterestGrid >tbody tr:not(:first)").each(
			function(){
				var row={};
				if($jq(this).find('td').eq(0).find('input').val()==""){
					errorMessages+="please Enter Lower Amount Limit in "+count+" row\n "
					if(status){
						status=false;
						$jq(this).find('td').eq(0).find('input').focus();
					}
				}else{
					row['lowerAmountLimit']=parseInt($jq(this).find('td').eq(0).find('input').val());
				}
				if($jq(this).find('td').eq(1).find('input').val()==""){
					errorMessages+="please Enter Upper Amount Limit in "+count+" row\n "
					if(status){
						status=false;
						$jq(this).find('td').eq(1).find('input').focus();
					}
				}else{
					row['upperAmountLimit']=parseInt($jq(this).find('td').eq(1).find('input').val());
				}
			   //srinu
				if(($jq(this).find('td').eq(0).find('input').val()) >($jq(this).find('td').eq(1).find('input').val()))
				{
					errorMessages+="please enter upper Amount is not less than lowerAmount \n "
					if(status){
						status=false;
						$jq(this).find('td').eq(1).find('input').focus();
				    }
				}
				//ens srinu
				if($jq(this).find('td').eq(2).find('input').val()==""){
					errorMessages+="please Interest Rate in "+count+" row\n "
					if(status){
						status=false;
						$jq(this).find('td').eq(2).find('input').focus();
					}
				}else{
					row['interestRate']=$jq(this).find('td').eq(2).find('input').val();
				}

				if(status)
				{
					if(row['lowerAmountLimit'] > row['upperAmountLimit'])
					{
						errorMessages+="please Enter Lower Amount Limit less than Lower Amount Limit in "+count+" row\n "
					}
				
				}	

				hbaInterestGrid[i]=row;
				count++;
				i++;
			});
	         
	        if(status)
	        {
	        	var tempAry = hbaInterestGrid;
	        	var flag = true;
	        	for(var j=0;flag && j<i;j++)
	        	{
	        		for(var k=0;flag && k<i;k++)
		        	{
		        		if(j!=k)
		        		{
		        		if(((tempAry[k]['lowerAmountLimit'] >= hbaInterestGrid[j]['lowerAmountLimit'] && tempAry[k]['lowerAmountLimit'] >= hbaInterestGrid[j]['upperAmountLimit'] && tempAry[k]['upperAmountLimit'] > hbaInterestGrid[j]['lowerAmountLimit'] && tempAry[k]['upperAmountLimit'] > hbaInterestGrid[j]['upperAmountLimit']) || (tempAry[k]['lowerAmountLimit'] < hbaInterestGrid[j]['lowerAmountLimit'] && tempAry[k]['upperAmountLimit'] <= hbaInterestGrid[j]['lowerAmountLimit'] && tempAry[k]['lowerAmountLimit'] <= hbaInterestGrid[j]['upperAmountLimit'] && tempAry[k]['upperAmountLimit'] < hbaInterestGrid[j]['upperAmountLimit'])) && !(tempAry[k]['lowerAmountLimit'] == tempAry[k]['upperAmountLimit'] == hbaInterestGrid[j]['lowerAmountLimit'] == hbaInterestGrid[j]['upperAmountLimit']))
		        				{
		        			      
		        				}
		        				else
		        				{
		        						flag = false;
			        			       status = false;
			        			       var k1=k;
			        			       var j1=j;
			        			       errorMessages+="Lower Amount Limit and Upper Amount Limit in "+(k1+1)+" row is Controduction with the "+(j1+1)+" row \n"
		        				}
		        		}
		        	}
	        	}
	        	
	        	for(var j=0;flag && j<i;j++)
	        	{
	        		for(var k=0;flag && jsonLoanHBAInterestGridList != null && jsonLoanHBAInterestGridList.length > 0 && k<jsonLoanHBAInterestGridList.length;k++)
		        	{
		        		
		        		if(compareDates($jq("#hbaInterestYear").val(),'dd-MMM-yyyy',formatDate(new Date(jsonLoanHBAInterestGridList[k].applicableYear.year+1900,jsonLoanHBAInterestGridList[k].applicableYear.month,jsonLoanHBAInterestGridList[k].applicableYear.date),'dd-MMM-yyyy'),'dd-MMM-yyyy')==0 && compareDates(formatDate(new Date(jsonLoanHBAInterestGridList[k].applicableYear.year+1900,jsonLoanHBAInterestGridList[k].applicableYear.month,jsonLoanHBAInterestGridList[k].applicableYear.date),'dd-MMM-yyyy'),'dd-MMM-yyyy',$jq("#hbaInterestYear").val(),'dd-MMM-yyyy')==0 && ((nod1 == null) || (nod1 != null && jsonLoanHBAInterestGridList[k].id != parseInt(nod1))))
		        		{
	        			if(((jsonLoanHBAInterestGridList[k].lowerAmountLimit >= hbaInterestGrid[j]['lowerAmountLimit'] && jsonLoanHBAInterestGridList[k].lowerAmountLimit >= hbaInterestGrid[j]['upperAmountLimit'] && jsonLoanHBAInterestGridList[k].upperAmountLimit > hbaInterestGrid[j]['lowerAmountLimit'] && jsonLoanHBAInterestGridList[k].upperAmountLimit > hbaInterestGrid[j]['upperAmountLimit']) || (jsonLoanHBAInterestGridList[k].lowerAmountLimit < hbaInterestGrid[j]['lowerAmountLimit'] && jsonLoanHBAInterestGridList[k].upperAmountLimit <= hbaInterestGrid[j]['lowerAmountLimit'] && jsonLoanHBAInterestGridList[k].lowerAmountLimit < hbaInterestGrid[j]['upperAmountLimit'] && jsonLoanHBAInterestGridList[k].upperAmountLimit <= hbaInterestGrid[j]['upperAmountLimit'])) && !(jsonLoanHBAInterestGridList[k].lowerAmountLimit == jsonLoanHBAInterestGridList[k].upperAmountLimit == hbaInterestGrid[j]['lowerAmountLimit'] == hbaInterestGrid[j]['upperAmountLimit']))
		        				{
		        			      
		        				}
		        				else
		        				{
		        						flag = false;
			        			       status = false;
			        			       errorMessages+="Lower Amount Limit and Upper Amount Limit of already Entered Values are Controduction with the "+(k+1)+" row \n"
		        				}
		        		}
		        		
		        	}
	        	}
	        	
	        	
	        	
	        	
	        	
	        	
	        	
	        }
	if(status){
		requestDetails = {
				"hbaInterestYear" :$jq("#hbaInterestYear").val(),
				"hbaInterestGrid" : JSON.stringify(hbaInterestGrid),
				"param" : "manageHBAInterestGrid",
				"nodeID": nodeID
		}
		$jq.post('loan.htm?' + dispUrl,requestDetails,function(html){
			clearHBAInterestRates();
			$jq("#result").html(html);
		});
	}
	else{
		alert(errorMessages);
	}
}

function clearHBAInterestRates(){
	nodeID=null;
	nod1=null;
	$jq("#add").show();
	$jq("#hbaInterestYear").val('');
	$jq("#HBAInterestGrid >tbody >tr:not(:first)").each(function() 
			{
		$jq("#HBAInterestGrid").find('input').val('');
		$jq("#HBAInterestGrid").find('input').eq(3).val('+');
		if($jq(this).closest('tr').index() > 1 ){
			$jq(this).remove();
		}
	});
}
function deleteLoanHBAInterestDetails(id){
	
	var del = confirm("Do you want to delete this item?");
	if (del == true) {
		var requestDetails = {
			"nodeID": id ,
			"param" : "deleteHBAGrid"
		};
		$jq.post('loan.htm?' + dispUrl, requestDetails, function(html) {
			$jq("#result").html(html);
		});
	}
}

function editLoanHBAInterestDetails(id,lower,upper,interest,year) {
	clearHBAInterestRates()
	nodeID = id;
	nod1=id;
	$jq('#hbaInterestYear').val(convertDateFormat(year));
	
	$jq('#HBAInterestGrid >tbody >tr:not(:first)').each(
			function(){
				$jq(this).find('td').eq(0).find('input').val(lower);
				$jq(this).find('td').eq(1).find('input').val(upper);
				$jq(this).find('td').eq(2).find('input').val(interest);
				$jq(this).find('td').eq(3).find('input').hide();
			});
	
}
//HBA interest Rates End
function getEmpService(dor){
	var today= new Date();
	var servMon;
	  var date1 = dor.split("-");
	  var date1year=date1[2].split(" ");
	  var sDate = new Date(date1[0]+"/"+date1[1]+"/"+date1year[0]);
	  var eDate = new Date(today.getFullYear()+"/"+today.getMonth()+"/"+today.getDate());
	  
	  var exp=eDate-sDate;
	  var years=parseInt(exp/(1000*60*60*24*365));
	  var months=parseInt((exp/(1000*60*60*24*30))-(12*years));
	  
	$jq('#empService').text(years+" Years "+months+" months");
	
}

function selectedMisc(){
	if($jq('input:radio[name=miscFlag]:checked').val() == "Y"){
		$jq('#miscellaneousDiv').show();
	}else{
		$jq('#miscellaneousDiv').hide();
	}
}

function calculateTotalPlinth(){
	if(!($jq.trim($jq('#plinthArea2').val()) == '' || $jq.trim($jq('#pliProposedEnlarge').val()) == ''))
		$jq('#totalPlinth').val( parseFloat($jq('#plinthArea2').val()) + parseFloat($jq('#pliProposedEnlarge').val()) );
}

function calculateTotalCost(){
	if(!($jq.trim($jq('#costAcquisition').val()) == '' || $jq.trim($jq('#costProposed').val()) == ''))
		$jq('#totalCost').val( parseFloat($jq('#costAcquisition').val()) + parseFloat($jq('#costProposed').val()) );
}

function validateNoOfInst(row){
	if(!($jq.trim($jq('#noOfInstalPrinciple'+row).val()) == '' || $jq.trim($jq('#noOfInstalInterest'+row).val()) == '')){
	}	
}

//function testFileUpload(fileID,errorMsg){
//	requestDetails ={
//			"param" : "submitTest"
//	}
//	$jq.post('test.htm', $jq('#test').serialize (), function(html) {
//		$jq("#result").html("Completed");
//});
//}

function testFileUpload(fileID,errorMsg){
	alert($jq('#test').serialize());
	$jq.ajaxFileUpload
    ({
            url:'test.htm?param=SubmitRequest',
            secureuri:false,
            fileElementId:fileID,
            dataType: 'json', 
            async: false,
            success: function (data, status)
            {
        		// return true;
            }, error: function (data, status, e)
            {
               // $jq("#result").text(errorMsg);
                // return false;
            }
     });	
}

//reports
function saveSrAdminOfficer(repNumber,repDate,e){
	var status=true;
	errorMessages="";
	if($jq(e).parent().closest("tr").find('td').eq(2).find('select').val()=='select'){
		errorMessages+="please select Sr Admin Officer in current row\n "
		if(status){
			status=false;
			$jq(e).parent().closest("tr").find('td').eq(2).find('select').focus();
		}
	}
	if(status){
		requestDetails ={
				"reportNumber" : repNumber,
				"reportDate": repDate,
				"srAccOfficer" :$jq(e).parent().closest("tr").find('td').eq(2).find('select').val(),
				"param" : "saveSrAdminOfficer"
		}
		$jq.post('loan.htm?' + dispUrl,requestDetails,function(html){
			//$jq('select').val('select');
			$jq("#result").html(html);
		});
	}else{
		alert(errorMessages);
	}
}

function saveCDAdetails(list){
	status=true;
	errorMessages="";
	var checkbox=true;
	$jq("#"+list+" tbody tr").each(function() {
		if ($jq(this).find('td').eq(0).find('input').is(':checked')){
			checkbox=false;
		}
	});
	if (checkbox) {
	errorMessages += "Please Select Requests for submission\n";
	status = false;
	}else{
		var cdaGrid = {};
		var i=0;
		var j=0;
		$jq("#"+list+" tbody tr").each(function() {
			if ($jq(this).find('td').eq(0).find('input').attr('checked')) {
			var row={};
			row['id']=$jq(this).find('td').eq(0).find('input').attr('id');
			row['requestID']=$jq.trim($jq(this).find('td').eq(1).text());
			
			if($jq(this).find('td').eq(4).find('input').val()=='')
			{
				errorMessages += 'Please select Sanction number in ' + (j + 1) + ' row\n';
				if (status) {
					status = false;
				}
			}
			else{
			row['sanctionNo']=$jq(this).find('td').eq(4).find('input').val();
			}
			
			if($jq(this).find('td').eq(5).find('input').val()=='')
			{
				errorMessages += 'Please select Bill number in ' + (j + 1) + ' row\n';
				if (status) {
					status = false;
				}
			}
			else{
			row['billNo']=$jq(this).find('td').eq(5).find('input').val();
			}
			
			if($jq(this).find('td').eq(6).find('select').val()=='select')
			{
				errorMessages += 'Please select Account officer in ' + (j + 1) + ' row\n';
				if (status) {
					status = false;
				}
			}
			else{
			row['accOfficer']=$jq(this).find('td').eq(6).find('select').val();
			}
			
			if($jq(this).find('td').eq(7).find('select').val()=='select')
			{
				errorMessages += 'Please select CFA officer in ' + (j + 1) + ' row\n';
				if (status) {
					status = false;
				}
			}
			else{
			row['cfaOfficer']=$jq(this).find('td').eq(7).find('select').val();
			}
			
//			if($jq(this).find('td').eq(8).find('input').val()=='')
//			{
//				errorMessages += 'Please select DV Number in ' + (j + 1) + ' row\n';
//				if (status) {
//					status = false;
//				}
//			}
//			else{
			row['dvNo']=$jq(this).find('td').eq(8).find('input').val();
//			}
//			
//			if($jq(this).find('td').eq(9).find('input').val()=='')
//			{
//				errorMessages += 'Please select DV date in ' + (j + 1) + ' row\n';
//				if (status) {
//					status = false;
//				}
//			}
//			else{
			row['dvDate']=$jq(this).find('td').eq(9).find('input').val();
//			}
//			
//			if($jq(this).find('td').eq(10).find('input').val()=='')
//			{
//				errorMessages += 'Please select CDA Amount in ' + (j + 1) + ' row\n';
//				if (status) {
//					status = false;
//				}
//			}
//			else{
			row['cdaAmount']=$jq(this).find('td').eq(10).find('input').val();
//			}
			cdaGrid[i]=row;
			i++;
			}
			j++;
		});
		
	}
	if(status){
		requestDetails ={
				"cdaGrid" : JSON.stringify(cdaGrid),
				"param" : "saveCDAReport",
				"listType" : list,
				"reportNumber" : $jq('#reportNumber').val(),
				"reportDate" :$jq('#reportDate').val(),
				"financialYear" : $jq('#financialYear').val(),
				"loanType" : festivalID,
				"loanSubType" : $jq('#loanSubType').val()
		}
		$jq.post('loan.htm?' + dispUrl,requestDetails,function(html){
			
			$jq("#"+list+"result").html(html);
			
		});
	}else{
		alert(errorMessages);
	}
}
function selectedDates() {
	$jq('#reportDate').find('option').remove().end();
	if($jq('#reportNumber').val()=='0'){
		$jq("#reportDateDiv").hide();
	}else{
	$jq("#reportDateDiv").show();
	}
	if (jsonReportDate != null) {
		var j=0;
		var array=[];
		for ( var i = 0; i < jsonReportDate.length; i++) {
			if(jsonReportDate[i].sendingReportNumber==$jq('#reportNumber').val()){
				var dateob=new Date(jsonReportDate[i].sendingReportDate.time);
//				fullyear=""+dateob.getFullYear()+"";
//				var year=fullyear.split("")[2]+fullyear.split("")[3];
				var dateddmonyy=dateob.getDate()+"-"+getMonthMON(dateob.getMonth()+1)+"-"+dateob.getFullYear();
				  array[j]=dateddmonyy;
				j++;
			}
		}
		array=$jq.unique(array);
		for(i=0;i< array.length;i++){
			$jq("#reportDate").append('<option value=' +array[i] + '>'+ array[i]+'</option>');
		}
	}
}

function searchPaybillDetails(){
	var errorMessages = "";
	var status = true;
	if ($jq('#reportNumber').val() == '0') {
		errorMessages += "please select Report Number\n";
		if (status) {
			status = false;
			$jq("#reportNumber").focus();
		}
	}else
	if ($jq('#reportDate').val() == null) {
		errorMessages += "please select Report Date\n";
		if (status) {
			status = false;
			$jq("#reportDate").focus();
		}
	}
	if (status) {
		requestDetails ={
				"reportNumber" : $jq('#reportNumber').val(),
				"reportDate" :$jq('#reportDate').val(),
				"param" : "searchPayBillAcReport"
		};
		$jq.post('loan.htm?' + dispUrl, requestDetails, function(html) {
			$jq("#LoanPaybillAcquittanceList").html(html);
		});
		$jq('#submitBut').show();
		$jq('#clearBut').show();
	}
	else{
		alert(errorMessages);
	}
}
function clearPaybillDetails(){
	$jq('#reportDateDiv').hide();
	
	$jq('#Pagination').text('');
	$jq('#reportNumber').val('0');
	$jq('#reportDate').find('option').remove().end();
	$jq("input:radio").attr("checked", false);
	$jq('input:text').val("");
	$jq('#dataList tr:not(:first)').each(function(){$jq(this).remove();});
	
}
function clearPayAcReport(){
	$jq('#financialYear').val('0');
	$jq('#loanSubType').val('0');
	$jq('#festivalList tr:not(:first)').each(function(){$jq(this).remove()});
	$jq("input:radio").attr("checked", false);
	$jq('input:text').val("");
}
function viewConveyanceReport(repNumber,repDate,repType,report){
	window.open('./report.htm?param='+repType+'&type='+repType+'&sendingReportNumber='+repNumber+'&sendingReportDate='+repDate+'&report='+report,'Loan','fullscreen=no,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no')
}

function viewAcquittanceReport(repType,list){

	var status=true;
	var errorMessage='';
	var requestId='';
	if(repType=='acquittance') {
		$jq("#"+list+" tr:not(:first)").each(function() {	
				if($jq(this).find("td").eq(0).find(":input:checkbox").is(':checked')){
					requestId+=$jq(this).find("td").eq(1).text()+","
				}		
			});
	}
	if(requestId=='') {
		errorMessage+='please check any one check box';
		status = false;
	}
	if(status) {
		window.open('./report.htm?param=loanAcquittanceReport&requestID='+requestId,'Loan','fullscreen=no,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no')

	}else {
		alert(errorMessage);
	}

	
}

function searchPayAcReport(){
	var errorMessages = "";
	var status = true;
	if ($jq('#financialYear').val() == '0') {
		errorMessages += "please select financial year\n";
		if (status) {
			status = false;
			$jq("#financialYear").focus();
		}
	}
	if ($jq('#loanSubType').val() == '') {
		errorMessages += "please select Festival name\n";
		if (status) {
			status = false;
			$jq("#loanSubType").focus();
		}
	}
	if (status) {
		var requestDetails = {
			"financialYear" : $jq('#financialYear').val(),
			"loanType" : festivalID,
			"loanSubType" : $jq('#loanSubType').val(),
			"param" : "searchPayAcReport"
		};
		$jq.post('loan.htm?' + dispUrl, requestDetails, function(html) {
			$jq("#FestivalPayAcquittanceList").html(html);
		});
	} else {
		alert(errorMessages);
	}
}

function setRuleValues(){
	for(var i=0;i<jsonLoanTypeDetailsList.length;i++){
		if(jsonLoanTypeDetailsList[i].loanTypeID==$jq('#loanType').val()){
			$jq('#requestedInstalments').closest('div').find('span').remove();
			$jq('#requestedInstalments').closest('div').append('<span class="rule" >&nbsp;Min-Max('+jsonLoanTypeDetailsList[i].minInstallments+'-'+jsonLoanTypeDetailsList[i].maxInstallments+')</span>');
			$jq('#requestedInterestInstalments').closest('div').find('span').remove();
			$jq('#requestedInterestInstalments').closest('div').append('<span class="rule" >&nbsp;Min-Max('+jsonLoanTypeDetailsList[i].minInterestInstallments+'-'+jsonLoanTypeDetailsList[i].maxInterestInstallments+')</span>');
		}
	}
	for(i=0;i<jsonLoanAmountDetails.length;i++){
		if(jsonLoanAmountDetails[i].loanTypeID==$jq('#loanType').val()){
			if(jsonLoanAmountDetails[i].gazType==type){
				 for(var j=0;j<jsonLoanAmountGrid.length;j++){
					 if(jsonLoanAmountGrid[j].loanAmountID==jsonLoanAmountDetails[i].id){
						 if($jq('input:radio[id=prevLoanFlagM]:checked').val()!='Y'){
							 if(jsonLoanAmountGrid[j].stageID==1 && jsonLoanAmountGrid[j].payBalanceRelation==1){
								 var configAmount=jsonLoanAmountGrid[j].amount;
							 }else if(jsonLoanAmountGrid[j].stageID==1 && jsonLoanAmountGrid[j].payBalanceRelation==3){
								 var basicpayAmount=jsonLoanAmountGrid[j].amount*basicPay;
							 }
								 
						 }else{
							 if(jsonLoanAmountGrid[j].stageID==2 && jsonLoanAmountGrid[j].payBalanceRelation==1){
								 var configAmount=jsonLoanAmountGrid[j].amount;
							 }
							 if(jsonLoanAmountGrid[j].stageID==2 && jsonLoanAmountGrid[j].payBalanceRelation==3){
								 var basicpayAmount=jsonLoanAmountGrid[j].amount*basicPay;
							 }	 
						 }
					 }
				 }
				
			}
			if(configAmount<basicpayAmount || basicpayAmount==undefined)
			var amount=configAmount;
			else if(configAmount>basicpayAmount)
			var amount=basicpayAmount;
			$jq('#reqAmountM').closest('div').find('span').remove();
			$jq('#reqAmountM').closest('div').append('<span class="rule" >&nbsp;Min-Max(1-'+amount+')</span>');
		}
	}
}

function totalInstMessageBasedOnPrin(){
	var status=true;
	var errorMessage ="";
	
	if($jq('#requestedInstalments').val()!='' && ($jq('#loanType').val() == motorcarID || $jq('#loanType').val() == motorScooter
			|| $jq('#loanType').val() == pcID
			|| $jq('#loanType').val() == motorCycle
			|| $jq('#loanType').val() == moped)){
		$jq('#message').remove();
		
		for(var i=0;jsonLoanTypeDetailsList != null && i<jsonLoanTypeDetailsList.length;i++)
		{
			if(jsonLoanTypeDetailsList[i].loanTypeID==$jq('#loanType').val())
			{
			if ($jq('#requestedInstalments').val() == '0'|| $jq.trim($jq('#requestedInstalments').val()) == '' ) {
				errorMessage += "Please enter number of required principle installments\n";
				status = false;
			}else if(isNaN($jq('#requestedInstalments').val()) || $jq('#requestedInstalments').val()< 0 ){
				errorMessage += "Please enter valid required principle installments\n";
				status = false;
			}
			else if(((jsonLoanTypeDetailsList[i].minInstallments>$jq('#requestedInstalments').val()) || (jsonLoanTypeDetailsList[i].maxInstallments<$jq('#requestedInstalments').val())))
			{
				errorMessage += "Loan Principle Installments should be between "+jsonLoanTypeDetailsList[i].minInstallments+" and "+jsonLoanTypeDetailsList[i].maxInstallments+"\n";
				status = false;      	
				
			}
			
			}
		}
		if(status)
		{
			totalInstMessage();
		}
		else
		{
		   alert(errorMessage);	
		   $jq('#requestedInstalments').val('');
		   $jq('#requestedInstalments').focus();
		}
	}
		
		
		
	
}
function totalInstMessageBasedOnInst(){
	var status=true;
	var errorMessage ="";
	
	if($jq('#requestedInterestInstalments').val()!='' && ($jq('#loanType').val() == motorcarID || $jq('#loanType').val() == motorScooter
			|| $jq('#loanType').val() == pcID
			|| $jq('#loanType').val() == motorCycle
			|| $jq('#loanType').val() == moped)){
		
		
		for(var i=0;jsonLoanTypeDetailsList != null && i<jsonLoanTypeDetailsList.length;i++)
		{
			if(jsonLoanTypeDetailsList[i].loanTypeID==$jq('#loanType').val())
			{
				if ($jq('#requestedInterestInstalments').val() == '0'|| $jq.trim($jq('#requestedInterestInstalments').val()) == '' ) {
					errorMessage += "Please enter number of required interest installments\n";
					status = false;
					
				}else if(isNaN($jq('#requestedInterestInstalments').val()) || $jq('#requestedInterestInstalments').val()< 0 ){
					errorMessage += "Please enter valid required interest installments\n";
					status = false;
				}
				else if(((jsonLoanTypeDetailsList[i].minInterestInstallments>$jq('#requestedInterestInstalments').val()) || (jsonLoanTypeDetailsList[i].maxInterestInstallments<$jq('#requestedInterestInstalments').val())))
				{
					errorMessage += "Loan Interest Installments should be between "+jsonLoanTypeDetailsList[i].minInterestInstallments+" and "+jsonLoanTypeDetailsList[i].maxInterestInstallments+"\n";
					status = false;
	      		}
			
			
			}
			
		}
		if(status)
		{
			
		}
		else
		{
		   alert(errorMessage);	
		   $jq('#requestedInterestInstalments').val('');
		   $jq('#requestedInterestInstalments').focus();
		}
		
		
		
	}
}


function totalInstMessage(){
	if($jq('#requestedInstalments').val()!=''){
		$jq('#message').remove();
					
		$jq('#requestedInterestInstalments').closest('div .line').append('<div class="line" id="message"><div class="half">&nbsp;</div><div class="half leftmar" >Your Principle loan amount will be recovered in '+ parseInt(parseInt($jq('#requestedInstalments').val())) + ' equal installments</div></div>')
	}
}

function calculateInstallEmi(present){
	var dom = new Object();
	var principleEmi;
	var principleLastEmi;
	var interestRate;
	var sanctionAmt;
	var sanctionPrincipleInst;
	var interestAmt;
	var interestInstall;
	var interestEmi;
	dom=$jq(present).parent().parent();
	
	if(dom.find('td').eq(5).find('input').val()!='' && dom.find('td').eq(7).find('input').val()!='' && dom.find('td').eq(14).find('input').val()!=''){
	interestRate=parseFloat(dom.find('td').eq(14).find('input').val());
	sanctionAmt=parseFloat(dom.find('td').eq(5).find('input').val());
	sanctionPrincipleInst=parseFloat(dom.find('td').eq(7).find('input').val());
	if(sanctionPrincipleInst==0){
		dom.find('td').eq(8).find('input').val(0);
		principleEmi=0;
	}else{
	if(sanctionAmt%sanctionPrincipleInst!=0){
		principleEmi=(Math.floor(Math.floor(sanctionAmt/sanctionPrincipleInst)/10)* 10)+10;
		principleLastEmi=sanctionAmt-(principleEmi*(sanctionPrincipleInst-1));
	}else{
	principleEmi=sanctionAmt/sanctionPrincipleInst;
	principleLastEmi=0;
	}
	dom.find('td').eq(8).find('input').val(principleEmi);
	dom.find('td').eq(9).find('input').val(principleLastEmi);
	}
	var interestAmt=Math.round(((sanctionPrincipleInst*(sanctionPrincipleInst+1))/2)*(principleEmi/12)*(parseFloat(parseFloat(interestRate)+2.5)/100));
	interestInstall=parseFloat(Math.ceil(interestAmt/principleEmi));
	var interestLastEmi=0;
	if(interestInstall==1){
		interestEmi=interestAmt;
	}else{
		interestEmi=parseFloat(Math.ceil((interestAmt/interestInstall)/10)*10);
		interestLastEmi = interestAmt-((interestInstall-1)*interestEmi)
	}
	dom.find('td').eq(10).find('input').val(interestAmt);
	dom.find('td').eq(11).find('input').val((isNaN(interestInstall))?0:interestInstall);
	dom.find('td').eq(12).find('input').val((isNaN(interestEmi))?0:interestEmi);
	
	dom.find('td').eq(13).find('input').val((isNaN(interestLastEmi))?0:interestLastEmi);
	}
}



//Sanction report & contingent bills

function selectedHQDates() {
	$jq('#reportDate').find('option').remove().end();
	if($jq('#reportNumber').val()=='0'){
		$jq("#reportDateDiv").hide();
	}else{
	$jq("#reportDateDiv").show();
	}
	if (jsonHQReportDate != null) {
		var j=0;
		var array=[];
		for ( var i = 0; i < jsonHQReportDate.length; i++) {
			if(jsonHQReportDate[i].hqReportNumber==$jq('#reportNumber').val()){
				var dateob=new Date(jsonHQReportDate[i].hqReportDate.time);
				var dateddmonyy=dateob.getDate()+"-"+getMonthMON(dateob.getMonth()+1)+"-"+dateob.getFullYear();
				  array[j]=dateddmonyy;
				j++;
			}
		}
		array=$jq.unique(array);
		for(i=0;i< array.length;i++){
			$jq("#reportDate").append('<option value=' +array[i] + '>'+ array[i]+'</option>');
		}
	}
}


function searchSanctionContingentDetails(){
	var errorMessages = "";
	var status = true;
	if ($jq('#reportNumber').val() == '0') {
		errorMessages += "please select HQ Report Number\n";
		if (status) {
			status = false;
			$jq("#reportNumber").focus();
		}
	}else
	if ($jq('#reportDate').val() == null) {
		errorMessages += "please select HQ Report Date\n";
		if (status) {
			status = false;
			$jq("#reportDate").focus();
		}
	}
	if (status) {
		requestDetails ={
				"reportNumber" : $jq('#reportNumber').val(),
				"reportDate" :$jq('#reportDate').val(),
				"param" : "searchSanctionConReport"
		};
		$jq.post('loan.htm?' + dispUrl, requestDetails, function(html) {
			$jq("#LoanSanctionContingentList").html(html);
		});
		$jq('#submitBut').show();
		$jq('#clearBut').show();
	}
	else{
		alert(errorMessages);
	}
}


