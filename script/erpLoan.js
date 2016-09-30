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
	
	$jq("#reasonForLoan").val();
	$jq("#amountRequested").val();
	$jq("#loanType").val();
	//getLoanType();
}

function checkLoanAmount(sal){
	var amountReq = parseInt($jq("#amountRequested").val());
	if(amountReq>parseInt(sal)){
		alert("Requested amount should not be greater than salary.");
		$jq("#amountRequested").val("");
	}
	else{
		unHide();
	}
	//alert(amountReq+" - "+sal);
}

function unHide(){
	
	$jq('#buttons').show();
}


function loanSubmit(){
	erpLoanRequest.param = "erpSubmitRequest";
	erpLoanRequest.reasonForLoan = $jq("#reasonForLoan").val();
	erpLoanRequest.amountRequested = $jq("#amountRequested").val();
	erpLoanRequest.loanType = $jq("#loanType").val();
	erpLoanRequest.requestedLoanType = $jq("#requestedLoanType").val();
	
	$jq.ajax( {
		type : "POST",
		url : "erpLoanRequest.htm",
		data : erpLoanRequest,
		cache : false,
		success : function(html) {
			$jq("#result").html(html);
			if ($jq('.success').is(':visible')) {
				
				 var check=confirm(" Loan  has been Successfully Sent...\nPreview Loan Application Form & print from here");
	     			if(check){
	     			//alert(requestIDLOAN);
	     			document.forms[0].requestId.value = requestIDLOAN;
	     		    document.forms[0].param.value = "requestDetails";
	     			document.forms[0].action = "workflowmap.htm";
	     			document.forms[0].submit();	
	     						}
				
			}
		}
	});
}


function manageLoanPurpose() {
	var loanType= $jq("#loanType").val();
	var requestDetails = {
			"nodeID" : nodeID,
			"param" : "manage",
			"loanType" : loanType
	};

	$jq.ajax( {
		type : "POST",
		url : "erpLoanPurpose.htm?",
		data : requestDetails,
		cache : false,
		success : function(html) {
			$jq('#result').html(html);
			clearLoanPurpose();
				}
	});
}

function clearLoanPurpose() {
	$jq('#loanType').val('');
	nodeID = "";
}

function editLoanPurpose(id, loanType) {
	nodeID = id;
	$jq('#loanType').val(loanType);
}

function deleteLoanPurpose(id) {
	var del = confirm("Do you want to delete this item?");
	if (del == true) {
		var requestDetails = {
			"nodeID" : id,
			"param" : "delete"
		};
		$jq.ajax( {
			type : "POST",
			url : 'erpLoanPurpose.htm?' + dispUrl,
			data : requestDetails,
			cache : false,
			success : function(html) {
				$jq('#result').html(html);
				clearLoanPurpose();
			}
		});
	}
}


function convertDateFormat(date) {
	var str = date.split(" ")[0];
	return str.split("-")[2] + "-" + getMonthMON(str.split("-")[1]) + "-"
			+ str.split("-")[0];
}




