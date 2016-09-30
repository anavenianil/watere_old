function submitPisRequest() {
	var jsoncell = {};
	var errorMessage = "";
	var status = true;
	if(document.getElementById('personalNumber').value=='' && document.getElementById('internalNo').value=='' && document.getElementById('residenceNo').value==''){
		errorMessage = "Please enter any value \n";
		status=false;
	}
	if(status){
		if(document.getElementById('personalNumber').value!=''){
			jsoncell["PERSONAL_NUMBER"] = document.getElementById('personalNumber').value
		}else{
			jsoncell["PERSONAL_NUMBER"] = "";
		}
		if(document.getElementById('internalNo').value!=''){
			jsoncell["INTERNAL_PHONE_NO"] = document.getElementById('internalNo').value;
		}else{
			jsoncell["INTERNAL_PHONE_NO"] = "";
		}
		if(document.getElementById('residenceNo').value!=''){
			jsoncell["RESIDENCE_NO"] =  document.getElementById('residenceNo').value;
			
		}else{
			jsoncell["RESIDENCE_NO"] =  "";
			
		}
		document.forms[0].changedValues.value = JSON.stringify(jsoncell);
		document.forms[0].param.value = "submitRequest";
		new Ajax.Updater('result', 'employee.htm', {
			onComplete : function() {
			if($jq('.success').is(':visible')){
				var requestType = $jq('#headTitle').html();
				var check=confirm(" PIS Request has been Successfully Sent...\n\nPlease click ok to 'Preview PIS Request 'Take print' from here\n\n");
				if(check){
				document.forms[0].requestId.value = $jq.trim(requestIDpis);
				
			   document.forms[0].param.value = "requestDetails";
				document.forms[0].action = "workflowmap.htm";
				document.forms[0].submit();	
				}}
			//resetPisRequest();
			},
			parameters : Form.serialize(document.forms[0]),
			asynchronous : true,
			evalScripts : true
		});		
	}else{
		alert(errorMessage);
	}
}

function declineRequest(historyID, requestType, requestID) {
	var errorMessages = "";
	var status = true;
	if (requestType == 'TADA TD BUILDUP' || requestType == 'TADA TD PROJECT') {
		if (reqStatus == 2 && advanceFlag == 'C') {
			errorMessages += "You are not declined this request because this request is already processed through hard copy.\n";
			status = false;
		} else {
			if ($jq('#remarks').val() == '') {
				errorMessages += "Please enter Remarks\n";
				if (status) {
					status = false;
					$jq('#remarks').focus();
				}
			}
		}
	}
	if (requestType == 'LOAN' || requestType == 'HOUSE BUILDING LOAN' || requestType == 'LOAN CONVEYANCE' ) {
		if ($jq('#remarks').val() == '') {
			errorMessages += "Please enter Remarks\n";
			if (status) {
				status = false;
				$jq('#remarks').focus();
			}
		}
	} else if (requestType != 'TADA TD BUILDUP' && requestType != 'TADA TD PROJECT') {
		if ($jq('#remarks').val() == '') {
			errorMessages += "Please enter Remarks\n";
			if (status) {
				status = false;
				$jq('#remarks').focus();
			}
		}
	}
	if (requestType == 'TUITION FEE' || requestType == 'TELEPHONE BILL') {
		if ($jq('#remarks').val() == '') {
			errorMessages += "Please enter Remarks\n";
			if (status) {
				status = false;
				$jq('#remarks').focus();
			}
		}
	}
	
	if (status) {
		if (confirm('Are you sure Do u want to decline the request ?')) {
			new Ajax.Updater('historyDetails', 'workflow.htm?param=declined&historyID=' + historyID + '&requestID=' + requestID + '&remarks='
				+ document.getElementById('remarks').value + '&requestType=' + requestType + '&type=declined', {
			onComplete : function() {
				blockButtons();
			},
			asynchronous : true,
			evalScripts : true
			});
		}
	} else {
		alert(errorMessages);
	}
}
function setLtcReimbursement(claimAmount,financeAmount,excessAmount) {
	if(parseInt(claimAmount) < 0) {
		$jq('#amountClaimedLabel').text('Excess Amount');		
		//$jq('#amountClaimed').text('Rs.'+(parseInt(claimAmount)*-1)+" /-");
		$jq('#amountClaimed').text(':').append('<font size="4.5em"><span class="WebRupee" >R</span></font>'+(parseInt(claimAmount)*-1)+" /-");
		//$jq('#amountClaimed').append('<font size="4.5em"><span class="WebRupee" >R</span></font>').text(+(parseInt(claimAmount)*-1)+" /-"));
		$jq('#financeAmountLabel').text('Finance Declared Excess Amount');
		//$jq('#financeAmount').text('Rs.'+(parseInt(financeAmount)*-1)+" /-");\
		$jq('#financeAmount').text(':').append('<font size="4.5em"><span class="WebRupee" >R</span></font>'+(parseInt(financeAmount)*-1)+" /-");
	}
	if(excessAmount!='') {
		$jq('#MroFormDiv').attr('style','display:block');
		$jq('#excessAmount').val(excessAmount);
		$jq('#restrictionAmount').text(-excessAmount);
		$jq('#MRODetailsDiv').attr('style','display:block');
		$jq('#excessAmountDiv').attr('style','display:block');
		
	}
}
function calculateAdvancePercentage(claimAmount,percentage,noOfFullTic,noOfInfantTic) {
	/**
	 * floor the value
	 * Ex: 1284====1250
	 * 		1234====1200	
	 */
	var infantAmount = 0;
	var fullAmount = 0;
	var oldTotalValue=0;
	
	oldTotalValue = Math.floor((parseInt(claimAmount)*parseInt(percentage))/100/100)*100;
	
	if($jq('#amountPerPerson').is(':visible') && $jq('#amountPerPerson').val()!='') {
		fullAmount = $jq('#amountPerPerson').val();
	}
	if($jq('#amountPerEachInfant').is(':visible') && $jq('#amountPerEachInfant').val()!='') {
		infantAmount = $jq('#amountPerEachInfant').val();
	}
	/*if(noOfInfantTic!='') {
		if(noOfFullTic!=''){
			claimAmount = parseInt(fullAmount) *2* parseInt(noOfFullTic) + parseInt(infantAmount) *2* parseInt(noOfInfantTic);
		}else{
			claimAmount = parseInt(infantAmount) *2* parseInt(noOfInfantTic);
		}
		}else {
		claimAmount = parseInt(fullAmount) *2* parseInt(noOfFullTic)
	}
	$jq('#issuedAmount').val(Math.floor((parseInt(claimAmount)*parseInt(percentage))/100/100)*100);
	*/
	
	
	
	if(noOfInfantTic!='') {
		if(noOfFullTic!=''){
			claimAmount = fullAmount *2* noOfFullTic + infantAmount *2* noOfInfantTic;
		}else{
			claimAmount = infantAmount*2* noOfInfantTic;
		}
		}else {
		claimAmount = fullAmount *2* noOfFullTic
	}
	issuedamountindecimal = round2Fixed(Math.floor((claimAmount*percentage)/100/100)*100);
	
	$jq('#issuedAmount').val(issuedamountindecimal);
	
	if($jq('#doPartDate').is(':visible')) {
		$jq('#financeEachAmountDiv').attr('style','display:none');
	}
	/*commented by bkr 12/04/2016*/
	/*if(parseInt(oldTotalValue) < parseInt($jq('#issuedAmount').val())) {
		alert('Issueing amount is greater than requested amount11');
		$jq('#amountPerPerson').val('');
	}*/
	
	
}
function cancelLeaveRequest(leaveRequestID,historyID) {
	if(leaveRequestID!='' && historyID!='') {
		if(confirm('Do u want to cancel the Leave request also')){
			new Ajax.Updater('', 'workflow.htm?param=declined&historyID='
					+ historyID + '&requestID=' + leaveRequestID + '&remarks=Due to LTC decline/cancel&requestType=LEAVE&type=cancelled', {
				onComplete : function() {
					blockButtons();
				},
				asynchronous : true,
				evalScripts : true
			});
		}
	}
}

function cancelRequest(historyID, requestType, requestID) {
	var status = true;
	errorMessage = '';
	if (requestType == 'TADA TD BUILDUP' || requestType == 'TADA TD PROJECT') {
		if (reqStatus == 2 && advanceFlag == 'C' && (ammendementId == '' || ammendementId == null || ammendementId == undefined)) {
			errorMessage += "You are not cancel this request because for this request advance is already sanctioned.\n";
			status = false;
		}
	}
	if (status) {
		if (confirm('Are you sure do u want to cancel the request ?\n')) {
			new Ajax.Updater('historyDetails', 'workflow.htm?param=declined&historyID='+ historyID + '&requestID=' + requestID + '&requestType='
					+ requestType +'&type=cancelled', {
				onComplete : function() {
					blockButtons();
				},
				asynchronous : true,
				evalScripts : true
			});
		}
	} else {
		alert(errorMessage);
	}
}

function blockButtons() {
	document.getElementById('requestButtons').style.display = "none";
	document.getElementById('requestBackBtn').style.display = "block";
}
function delegateRequest(historyID, requestType, requestID) {
	var errorMessage = "";
	var status = true;

	if (document.getElementById('deletedSfid').value == 'select') {
		errorMessage = "Please Select Delegated Employee\n";
		status = false;
		document.getElementById('deletedSfid').focus();
	}
		//naresh
		if ($jq('#remarks').val() == '') {
			errorMessage += "Please enter Remarks\n";
			if (status) {
			status = false;
			$jq('#remarks').focus();
			}
			} 
	
	if (status) {
		new Ajax.Updater('historyDetails',
				'workflow.htm?param=delegated&historyID=' + historyID
						+ '&parentID='
						+ document.getElementById('deletedSfid').value
						+ '&remarks='
						+ document.getElementById('remarks').value, {
					onComplete : function() {

			          /* $jq('#delegateBtnId').hide();*/
						blockButtons(); /*if delegated list is selected and approved button is visible user clicks approved by mistake*/

			         //  $jq('#delegateBtnId').hide();
					 blockButtons(); /*if delegated list is selected and approved button is visible user clicks approved by mistake*/

					},
					asynchronous : true,
					evalScripts : true
				});
	} else {
		alert(errorMessage);
	}
}

function escalated() {
	new Ajax.Updater('historyDetails', 'workflow.htm?param=escalated', {
		onComplete : function() {
		},
		asynchronous : true,
		evalScripts : true
	});
}

function approveRequest(historyID, requestType, requestID, approvedDept,
		stateID,requestTypeID) {
	reqTypeID = requestTypeID;// set to globle var
	var errorMessages = "";
//	$jq('#doPartNo').val('9873');
	//alert($jq('#doPartNo').val('9873'));
//alert("12welcome"+$jq('#doPartNo').val());

	
	//$jq('#doPartDate').val('10-Apr-2015');
	//$jq('#doPartNo').val('GAZ/24');
	//$jq('#doPartDate').val('10-Apr-2015');
	//$jq('#doPartNo').val('4221');
	/*alert($jq('#doPartDate').val());
	alert($jq('#doPartNo').val()s);
	added by bkr 13/04/2014 only two lines
	$jq('#doPartDate').val('30-Apr-2015');
	//$jq('#doPartNo').val('GAZ/24');
	*/
	
	/*//NARESH
	if (document.getElementById('deletedSfid').value != 'select') {
		errorMessage = "Please Select Delegated Employee";
		status = false;
		document.getElementById('deletedSfid').focus();
	}*/
	var status = true;	
	
	var url = 'workflow.htm?historyID=' + historyID + '&requestType='
			+ requestType + '&requestID=' + requestID + '&workflowStageID='
			+ stateID + '&requestTypeID='+requestTypeID+'&approvedDept=' 
			+ approvedDept+'&gazType='+gazType;
	
	if(requestType=="DEMAND"){
		if(approvedDept=='BUDGET'){
			var status=true;
			var len=$jq("#demandMaterial")[0].rows.length;
			for(var i=1;i<len;i++){
				if($jq("#amountType"+i+" option:selected").val()=="select")
				{
					status=false;
					break;
				}
			}
			if(!status){
				alert("please select amount type");
				return;
			}
		}else if(approvedDept=='CFA'){
			var status=true;
			var len=$jq("#demandMaterial")[0].rows.length;
			var total="";
			for(var i=1;i<len;i++){
				var unitRate=$jq("#unitRate"+i).val();
				var qty=$jq("#qty"+i).val();
				if(unitRate<1)
				{
					status=false;
					break;
				}
				if(qty<1)
				{
					status=false;
					break;
				}
				total+=unitRate*qty;
			}
			if(total>30000)
				status=false;
			if(!status){
				alert("please enter proper qty/unitRate");
				return;
			}
		}
	}
	else if(requestType=="INVOICE"){
		if(approvedDept=='MMG'){
			if(!$jq("#hardCopyFlag")[0].checked)
			{
				alert("Please verify whether hard copy received or not");
				return;
			}
		}
	}
	else if(requestType=="CGHS" && ($jq('#referenceNumber').is(':visible') || $jq('#approvedBy').is(':visible') ||$jq('#prescriptionReceivedFlag').is(':visible'))) {
		if($jq('#referenceNumber').val()=='ASL/') {
			alert('Enter Reference Number');
			$jq('#referenceNumber').focus();
			return;
		}
		if($jq('#approvedDate').val()=='') {
			alert('Enter approved date');
			$jq('#approvedDate').focus();
			return;
		}
		/*if(!$jq('#prescriptionReceivedFlagYes').is(':checked') && !$jq('#prescriptionReceivedFlagNo').is(':checked')) {
			alert('Please check Prescription copy received');
			$jq('#prescriptionReceivedFlag').focus();
			return;
		}*/
	}else if(requestType=="CGHS ADVANCE"){
		if($jq('#issuedAmount').val()=='' && $jq('#issuedAmount').is(':visible')) {
			alert('Please enter issueing amount');
			$jq('#issuedAmount').focus();
			return;
		}
		
	}else if(requestType=="LTC APPROVAL" || requestType=="LTC APPROVAL CUM ADVANCE" || requestType=="LTC ADVANCE" || requestType=="LTC ADVANCE AMENDMENT" || requestType=="LTC APPROVAL AMENDMENT") {
		/*if(leaveStatus!="" && leaveStatus=='pending' && $jq('#doPartNo').is(':visible')) {
			alert('Attached Leave Request "'+leaveID+'" is in Pending State. Complete leave request first');
			return;
		}*/
		/*commented by bkr 13/04/2016*/
		/*
		if($jq('#doPartDate').val()=='' && $jq('#doPartDate').is(':visible')) {
			alert('Please select Do-Part Date');
			$jq('#doPartDate').focus();
			return;
		}*/
		

		if($jq('#doPartDate').val()=='' && $jq('#doPartDate').is(':visible')) {
			
			alert('Enter DoPartDate');
			$jq('#doPartDate').focus();
			return;
			/*alert('Please select Do-Part Date');
			$jq('#doPartDate').focus();
			return;*/
			//$jq('#doPartDate').val('10-Apr-2015');
			//$jq('#doPartDate').val('14-Apr-2016');
			//alert("welcome"+$jq('#doPartDate').val());
			
		}
		/*commented by bkr 13/04/2016*/
		/*if($jq('#doPartNo').val()=='' && $jq('#doPartNo').is(':visible')) {
			alert('Please Select Do-Part Number');
			$jq('#doPartNo').focus();
			return;
		}*/
		
		if($jq('#doPartNo').val()=='' && $jq('#doPartNo').is(':visible')) {
			
			alert('Enter doPartNo');
			$jq('#doPartNo').focus();
			return;
			
			/*alert('Please Select Do-Part Number');
			$jq('#doPartNo').focus();
			return;*/
			//$jq('#doPartNo').val('4220');
			//alert("welcome1"+$jq('#doPartNo').val());
			
		}
		
		
		if($jq('#issuedAmount').val()=='' && $jq('#issuedAmount').is(':visible')) {
			alert('Enter Issued Amount');
			$jq('#issuedAmount').focus();
			return;
		}
		if($jq('#amountPerPerson').val()=='' && $jq('#amountPerPerson').is(':visible')) {
			alert('Enter full ticket Amount');
			$jq('#amountPerPerson').focus();
			return;
		}
		if($jq('#amountPerEachInfant').val()=='' && $jq('#amountPerEachInfant').is(':visible')) {
			alert('Enter Infant Amount');
			$jq('#amountPerEachInfant').focus();
			return;
		}
		
		if($jq('#issuedAmount').val()!='' && $jq('#issuedAmount').val()!=undefined  && $jq('#amountPerPerson').val()!='' && $jq('#amountPerPerson').val()!=undefined){
			issued=round2Fixed($jq('#issuedAmount').val());
			$jq('#issuedAmount').val(issued);
			Perperson=round2Fixed($jq('#amountPerPerson').val());
			$jq('#amountPerPerson').val(Perperson);
			if($jq('#amountPerEachInfant').val()!='' && $jq('#amountPerEachInfant').val()!=undefined){
				infant =round2Fixed($jq('#amountPerEachInfant').val());
				$jq('#amountPerEachInfant').val(infant);
			}
			
		} 
		
	}else if(requestType=='LTC SETTLEMENT' || requestType=='LTC REIMBURSEMENT'){
		if(!calculateAmountAfterRestriction()) {
			status=false;
			errorMessages +='enter amount after restriction \n';
		}
		if($jq('#MROPaidNo').val()=='' && $jq('#MROPaidNo').is(':visible')) {
			status = false;
			errorMessages += 'Please enter MRO Paid No.\n';
			$jq('#MROPaidNo').focus();
		}
		if($jq('#MROPaidDate').val()=='' && $jq('#MROPaidDate').is(':visible')) {
			status = false;
			errorMessages += 'Please Enter MRO Paid Date.\n';
			$jq('#MROPaidDate').focus();
		}
		
		
	     if(requestType == 'LTC SETTLEMENT'&& parseInt($jq('#restrictionAmount').text().trim())<0 ||$jq('#restrictionAmount').text().trim()=="NaN"){
			
			if($jq('#excessAmountFine').val()==''){
				status=false;
				errorMessages +='enter Penal Interest\n';
			}
			if($jq('#noOfDays').val()==''){
				status=false;
				errorMessages +='enter No of Days\n';
			}
			//ltcpenalNoofDays= $jq('#noOfDays').val();
			//document.forms[0].ltcpenalNoofDays =$jq('#noOfDays').val();
			$jq('#ltcpenalNoofDays').val($jq('#noOfDays').val());
			/*if(status){
				url += '&excessAmount='+$jq('#excessAmount').val()+'&amount='+$jq('#amount').val()+'&excessAmountFine='+$jq('#excessAmountFine').val()+'&penalNoOfDays='+$jq('#noOfDays').val()+'&penalInterestId='+$jq('input:text[name=interestRate]').attr('id');	
			}*/
			/*if(status){
				//url += '&penalNoOfDays='+$jq('#noOfDays').val();
				//document.forms[0].penalNoOfDays=$jq('#noOfDays').val();
				//penalNoOfDays=	$jq('#noOfDays').val();
			}*/
		} 
	}
	
		
	
	else if(requestType=='LTC REFUND' || requestType == 'LTC CANCEL' || requestType == 'LTC APPR CUM ADV CANCEL'){
		if($jq('#doPartDate').val()=='' && $jq('#doPartDate').is(':visible')) {
			status = false;
			errorMessages += 'Select Do Part Date\n';
			$jq('#doPartDate').focus();
		}
		if($jq('#doPartNo').val()=='' && $jq('#doPartNo').is(':visible')) {
			errorMessages += 'Select Do Part No.\n';
			if(status) {
				$jq('#doPartNo').focus();
				status = false;
			}
		}
		if($jq('#excessAmount').val()=='' && $jq('#excessAmount').is(':visible')) {
			errorMessages += 'Enter excess amount.\n';
			if(status) {
				$jq('#excessAmount').focus();
				status = false;
			}
		}
		if($jq('#excessAmountFine').val()=='' && $jq('#excessAmountFine').is(':visible')) {
			errorMessages += 'Enter penal interest.\n';
			if(status) {
				$jq('#excessAmountFine').focus();
				status = false;
			}
		}
		if($jq('#MROPaidNo').val()=='' && $jq('#MROPaidNo').is(':visible')) {
			errorMessages += 'MRO Paid No.\n';
			if(status) {
				$jq('#MROPaidNo').focus();
				status = false;
			}
		}
		if($jq('#MROPaidDate').val()=='' && $jq('#MROPaidDate').is(':visible')) {
			errorMessages += 'MRO Paid Date.\n';
			if(status) {
				$jq('#MROPaidDate').focus();
				status = false;
			}
		}
	} else if (requestType == 'Self Lab Transfer' || requestType == 'Employee Lab Transfer') {	
		if ($jq('#doPartNo').is(':visible')) {
			if ($jq('#doPartNo').val() == '') {
				errorMessages += "Please enter ION number\n";
				if (status) {
					status = false;
					$jq('#doPartNo').focus();
				}
			}
			if ($jq('#doPartDate').val() == '') {
				errorMessages += "Please enter ION date\n";
				if (status) {
					status = false;
					$jq('#doPartDate').focus();
				}
			}
		}

		if ($jq('#hqRefNo').is(':visible')) {
			if ($jq('#hqRefNo').val() == '') {
				errorMessages += "Please enter HQ Reference Number\n";
				if (status) {
					status = false;
					$jq('#hqRefNo').focus();
				}
			}
			if ($jq('#receivedDate').val() == '') {
				errorMessages += "Please enter received date\n";
				if (status) {
					status = false;
					$jq('#receivedDate').focus();
				}
			}
		}
	} else if (requestType == 'LOAN' || requestType == 'HOUSE BUILDING LOAN' || requestType == 'LOAN CONVEYANCE') {
		if ($jq('#sanctionedAmount').is(':visible') && $jq('#sanctionedAmount').val() == '') {
			errorMessages += "Please enter sanctioned amount\n";
			if (status) {
				status = false;
				$jq('#sanctionedAmount').focus();
			}
		}else{
			if ($jq('#sanctionedAmount').is(':visible') && isNaN($jq("#sanctionedAmount").val())) {
				errorMessages += "Please enter valid Integer for Sanctioned Amount\n";
				if (status) {
					status = false;
					$jq("#sanctionedAmount").focus();
				}
			}
			if ($jq('#sanctionedAmount').is(':visible') && $jq("#sanctionedAmount").val() < 0) {
				errorMessages += "Sanctioned Amount should be Positive\n";
				if (status) {
					status = false;
					$jq("#sanctionedAmount").focus();
				}
			}
		
		}
		if ($jq('#sanctionedDate').val() == '') {
			errorMessages += "Please select sanctioned date\n";
			if (status) {
				status = false;
				$jq('#sanctionedDate').focus();
			}
		}
		if ($jq('#sanctionedInstalments').is(':visible') && $jq('#sanctionedInstalments').val() == '') {
			errorMessages += "Please enter sanctioned installments\n";
			if (status) {
				status = false;
				$jq('#sanctionedInstalments').focus();
			}
		}else{
			if ($jq('#sanctionedInstalments').is(':visible') && isNaN($jq("#sanctionedInstalments").val())) {
				errorMessages += "Please enter valid Integer for Sanctioned Instalments\n";
				if (status) {
					status = false;
					$jq("#sanctionedInstalments").focus();
				}
			}
			if ($jq('#sanctionedInstalments').is(':visible') && $jq("#sanctionedInstalments").val() < 0) {
				errorMessages += "Sanctioned Instalments should be Positive\n";
				if (status) {
					status = false;
					$jq("#sanctionedInstalments").focus();
				}
			}
		
		
		}
		if ($jq('#recStartDate').is(':visible') && $jq('#recStartDate').val() == '') {
			errorMessages += "Please select recovery start date\n";
			if (status) {
				status = false;
				$jq('#recStartDate').focus();
			}
		}
		if ($jq('#emi').is(':visible') && $jq('#emi').val() == '') {
			errorMessages += "Please enter emi\n";
			if (status) {
				status = false;
				$jq('#emi').focus();
			}
		}else{
			if ($jq('#emi').is(':visible') && isNaN($jq("#emi").val())) {
				errorMessages += "Please enter valid Integer for Emi\n";
				if (status) {
					status = false;
					$jq("#emi").focus();
				}
			}
			if ($jq('#emi').is(':visible') && $jq("#emi").val() < 0) {
				errorMessages += "Emi should be Positive\n";
				if (status) {
					status = false;
					$jq("#emi").focus();
				}
			}
		
		
		
		}
		if ($jq('#loanRefNo').val() == '') {
			errorMessages += "Please enter reference number\n";
			if (status) {
				status = false;
				$jq('#loanRefNo').focus();
			}
		}
		if ($jq('#bankAccount').is(':visible') && $jq('#bankAccount').val() == '') {
			errorMessages += "Please enter bank account number\n";
			if (status) {
				status = false;
				$jq('#bankAccount').focus();
			}
		}
		if ($jq('#bankBranch').is(':visible') && $jq('#bankBranch').val() == '') {
			errorMessages += "Please enter bank branch details\n";
			if (status) {
				status = false;
				$jq('#bankBranch').focus();
			}
		}
	}
	else if (requestType == 'NEW QUARTER' || requestType == 'CHANGE QUARTER') {
		/*if ($jq('#quarterType').val() == '0') {
			errorMessages += "Please select Quarter Type.\n";
			if (status) {
				status = false;
				$jq('#quarterType').focus();
			}
		}
		if ($jq('#allotedDate').val() == '') {
			errorMessages += "Please select Allotted Date.\n";
			if (status) {
				status = false;
				$jq('#allotedDate').focus();
			}
		}
		if ($jq('#quarterNo').val() == '') {
			errorMessages += "Please enter Quarter Number.\n";
			if (status) {
				status = false;
				$jq('#quarterNo').focus();
			}
		}
		if ($jq('#occupiedDate').val() == '') {
			errorMessages += "Please select Occupied Date.\n";
			if (status) {
				status = false;
				$jq('#occupiedDate').focus();
			}
		}if($jq('#allotedDate').val() !=undefined && $jq('#occupiedDate').val() !=undefined){
		var allotedDate = $jq('#allotedDate').val();
		var occupiedDate = $jq('#occupiedDate').val();
		var date1 = new Date(allotedDate.split("-")[2], getMonthID(allotedDate.split("-")[1]) - 1, allotedDate.split("-")[0], 0, 0, 1, 0);
		var date2 = new Date(occupiedDate.split("-")[2], getMonthID(occupiedDate.split("-")[1]) - 1, occupiedDate.split("-")[0], 0, 0, 1, 0);
		}
		if (date1 > date2) {
			errorMessages += "Allotted Date Can't Be Greater Than Occupied Date.\n";
			if (status) {
				status = false;
				$jq("#allotedDate").focus();
			}
		}*/
		if ($jq('#quarterType').val() == "") {
			$jq('#quarterType').val('0');
		}
		status = true;

	}else if (requestType == 'HIGHER QUALIFICATION') {
		if(!$jq('input:radio[name=dischargeOfDuties]').is(':checked') && $jq('input:radio[name=dischargeOfDuties]').is(':visible')){
			errorMessages += "Please Select Discharge Of Duties.\n";
			if(status){
				status = false;
				$jq('#dischargeOfDuties').focus();
			}
		}else if(!$jq('input:radio[name=labWork]').is(':checked') && $jq('input:radio[name=labWork]').is(':visible')){
			errorMessages += "Please Select Study Course Will Be Useful For Lab Work.\n";
			if(status){
				status = false;
				$jq('#labWork').focus();
			}
		}

	} else if(requestType == 'TADA TD PROJECT'){
		if($jq('#projectType option:selected').val()==0){
			errorMessages += "Please Select One Project Type.\n";
			status=false;
		}
	} else if(requestType == 'TADA TD REIMBURSEMENTSSSSS'){
		var strTicketFareAftRes='';
		var strClaimedJourneyAmtAftRes='';
		var strAccAmtAftRes='';
		var strClaimedAccAmtAftRes='';
		var strRMAKmDisAftRes='';
		var strRMAKmAmtAftRes='';
		var strClaimedRMAKmAftRes='';
		var strRMALocalDisAftRes='';
		var strRMALocalAmtAftRes='';
		var strClaimedRMALocalAftRes='';
		var strRMADayAmtPerDay='';
		var strRMADayAmtAftRes='';
		var strFoodAmtAftRes='';
		var strRMADailyDisAftRes='';
		var strRMADailyAmtAftRes='';
		var strClaimedRMADailyAftRes='';
		var accDetailsJson={};
		var accDayJson={};
		var mainJson={};
		$jq('#journeyDetailsId tr:gt(1)').each(function(){
			strTicketFareAftRes=strTicketFareAftRes + $jq(this).find('td').eq(9).find('input').val()+'-'+$jq(this).find('td').eq(9).find('input').attr('id')+'@'
			if(parseFloat($jq(this).find('td').eq(12).text())!=0){
				strClaimedJourneyAmtAftRes=strClaimedJourneyAmtAftRes + $jq(this).find('td').eq(12).find('input').val()+'-'+$jq(this).find('td').eq(12).find('input').attr('id')+'@'
			}
		});
		$jq('#daNewAccDetailsId tr:not(:first)').each(function(){
			strAccAmtAftRes=strAccAmtAftRes + $jq(this).find('td').eq(5).find('input').val()+'-'+$jq(this).find('td').eq(5).find('input').attr('id')+'@'
			strClaimedAccAmtAftRes=strClaimedAccAmtAftRes + $jq(this).find('td').eq(7).find('input').val()+'-'+$jq(this).find('td').eq(7).find('input').attr('id')+'@'
		});
		$jq('#RMAKmDetailsId tr:not(:first)').each(function(){
			strRMAKmDisAftRes=strRMAKmDisAftRes + $jq(this).find('td').eq(5).find('input').val()+'-'+$jq(this).find('td').eq(5).find('input').attr('id')+'@'
			strRMAKmAmtAftRes=strRMAKmAmtAftRes + $jq(this).find('td').eq(7).find('input').val()+'-'+$jq(this).find('td').eq(7).find('input').attr('id')+'@'
			strClaimedRMAKmAftRes=strClaimedRMAKmAftRes + $jq(this).find('td').eq(9).find('input').val()+'-'+$jq(this).find('td').eq(9).find('input').attr('id')+'@'
		});
		$jq('#RMALocalDetailsId tr:not(:first)').each(function(){
			strRMALocalDisAftRes=strRMALocalDisAftRes + $jq(this).find('td').eq(5).find('input').val()+'-'+$jq(this).find('td').eq(5).find('input').attr('id')+'@'
			strRMALocalAmtAftRes=strRMALocalAmtAftRes + $jq(this).find('td').eq(7).find('input').val()+'-'+$jq(this).find('td').eq(7).find('input').attr('id')+'@'
			strClaimedRMALocalAftRes=strClaimedRMALocalAftRes + $jq(this).find('td').eq(9).find('input').val()+'-'+$jq(this).find('td').eq(9).find('input').attr('id')+'@'
		});
		$jq('#daNewFoodDetailsId tr:not(:first)').each(function(){
			strFoodAmtAftRes=strFoodAmtAftRes + $jq(this).find('td').eq(3).find('input').val()+'-'+$jq(this).find('td').eq(3).find('input').attr('id')+'@'
		});
		$jq('#RMADayDetailsId tr:not(:first)').each(function(){
			strRMADayAmtPerDay=strRMALocalDisAftRes + $jq(this).find('td').eq(4).find('input').val()+'-'+$jq(this).find('td').eq(4).find('input').attr('id')+'@'
			strRMADayAmtAftRes=strRMALocalAmtAftRes + $jq(this).find('td').eq(6).find('input').val()+'-'+$jq(this).find('td').eq(6).find('input').attr('id')+'@'
		});
		$jq('#RMADailyDetailsId tr:not(:first)').each(function(){
			strRMADailyDisAftRes=strRMADailyDisAftRes + $jq(this).find('td').eq(5).find('input').val()+'-'+$jq(this).find('td').eq(5).find('input').attr('id')+'@'
			strRMADailyAmtAftRes=strRMADailyAmtAftRes + $jq(this).find('td').eq(7).find('input').val()+'-'+$jq(this).find('td').eq(7).find('input').attr('id')+'@'
			strClaimedRMADailyAftRes=strClaimedRMADailyAftRes + $jq(this).find('td').eq(9).find('input').val()+'-'+$jq(this).find('td').eq(9).find('input').attr('id')+'@'
		});
		var i=0;
		$jq('#daNewAccDetailsId tr:not(:first)').each(function(){
			var subJson={};
			subJson['fromDate']= $jq(this).find('td').eq(0).text();
			subJson['toDate']=$jq(this).find('td').eq(2).text();
			subJson['accAmount']=$jq(this).find('td').eq(4).text();
			subJson['accAmountAftRes']=$jq(this).find('td').eq(5).find('input').val();
			subJson['claimedAmount']=$jq(this).find('td').eq(6).text();
			subJson['claimedAmountAftRes']=$jq(this).find('td').eq(7).find('input').val();
			accDetailsJson[i]=subJson;
			i++;
		});
		mainJson['accDetails']=accDetailsJson;
		document.forms[0].strTicketFareAftRes.value = strTicketFareAftRes;
		document.forms[0].strClaimedJourneyAmtAftRes.value = strClaimedJourneyAmtAftRes;
		document.forms[0].strAccAmtAftRes.value = strAccAmtAftRes;
		document.forms[0].strClaimedAccAmtAftRes.value = strClaimedAccAmtAftRes;
		document.forms[0].strRMAKmDisAftRes.value = strRMAKmDisAftRes;
		document.forms[0].strRMAKmAmtAftRes.value = strRMAKmAmtAftRes;
		document.forms[0].strClaimedRMAKmAftRes.value = strClaimedRMAKmAftRes;
		document.forms[0].strRMALocalDisAftRes.value = strRMALocalDisAftRes;
		document.forms[0].strRMALocalAmtAftRes.value = strRMALocalAmtAftRes;
		document.forms[0].strClaimedRMALocalAftRes.value = strClaimedRMALocalAftRes;
		document.forms[0].strFoodAmtAftRes.value = strFoodAmtAftRes;
		document.forms[0].strRMADayAmtPerDay.value=strRMADayAmtPerDay;
		document.forms[0].strRMADayAmtAftRes.value=strRMADayAmtAftRes;
		document.forms[0].jsonValue.value = JSON.stringify(mainJson);
		document.forms[0].strRMADailyDisAftRes.value = strRMADailyDisAftRes;
		document.forms[0].strRMADailyAmtAftRes.value = strRMADailyAmtAftRes;
		document.forms[0].strClaimedRMADailyAftRes.value = strClaimedRMADailyAftRes;
		//document.forms[0].totalTadaTdCalAmount.value = $jq('#totalTadaTdCalAmount').val();
		
		if(true){
			var i=0;
			$jq("#daNewDetailsId tr:not(:first)").each(function() {
				if(($jq(this).find("td").eq(2).find('input').val()=='' || $jq(this).find("td").eq(2).find('input').val()==undefined) && $jq(this).find("td").eq(2).find('input').is(':visible')){
					errorMessages += "Please Enter Stay Duration Aft Restriction.\n";
					$jq(this).find("td").eq(2).find('input').focus();
					status=false;
				}
				if(($jq(this).find("td").eq(4).find('input').val()=='' || $jq(this).find("td").eq(4).find('input').val()==undefined) && $jq(this).find("td").eq(4).find('input').is(':visible')){
					errorMessages += "Please Enter Amount For Food Aft Restriction.\n";
					$jq(this).find("td").eq(4).find('input').focus();
					status=false;
				}
				if(($jq(this).find("td").eq(6).find('input').val()=='' || $jq(this).find("td").eq(6).find('input').val()==undefined) && $jq(this).find("td").eq(6).find('input').is(':visible')){
					errorMessages += "Please Enter Amount For Accommodation Aft Restriction.\n";
					$jq(this).find("td").eq(6).find('input').focus();
					status=false;
				}
				i++;
			});
		}
		if(true){
			var j=0;
			$jq("#RMAKmDetailsId tr:not(:first)").each(function() {
				if(($jq(this).find("td").eq(5).find('input').val()=='' || $jq(this).find("td").eq(5).find('input').val()==undefined) && $jq(this).find("td").eq(5).find('input').is(':visible')){
					errorMessages += "Please Enter Distance After Restriction.\n";
					$jq(this).find("td").eq(5).find('input').focus();
					status=false;
				}
				if(($jq(this).find("td").eq(7).find('input').val()=='' || $jq(this).find("td").eq(7).find('input').val()==undefined) && $jq(this).find("td").eq(7).find('input').is(':visible')){
					errorMessages += "Please Enter Amount Per k.m After Restriction.\n";
					$jq(this).find("td").eq(7).find('input').focus();
					status=false;
				}
				j++;
			});
		}
		if(true){
			var l=0;
			$jq("#RMADayDetailsId tr:not(:first)").each(function() {
				if(($jq(this).find("td").eq(4).find('input').val()=='' || $jq(this).find("td").eq(4).find('input').val()==undefined) && $jq(this).find("td").eq(4).find('input').is(':visible')){
					errorMessages += "Please Enter Amount Per Day After Restriction.\n";
					$jq(this).find("td").eq(4).find('input').focus();
					status=false;
				}
				l++;
			});
		}
		if(true){
			var k=0;
			$jq("#journeyDetailsId tr:not(:first)").each(function() {
				if(($jq(this).find("td").eq(11).find('input').val()=='' || $jq(this).find("td").eq(11).find('input').val()==undefined) && $jq(this).find("td").eq(11).find('input').is(':visible')){
					errorMessages += "Please Enter Amount After Restriction in Journey Details.\n";
					$jq(this).find("td").eq(11).find('input').focus();
					status=false;
				}
				k++;
			});
		}
		if(true){
			var j=0;
			$jq("#RMADailyDetailsId tr:not(:first)").each(function() {
				if(($jq(this).find("td").eq(5).find('input').val()=='' || $jq(this).find("td").eq(5).find('input').val()==undefined) && $jq(this).find("td").eq(5).find('input').is(':visible')){
					errorMessages += "Please Enter Distance After Restriction.\n";
					$jq(this).find("td").eq(5).find('input').focus();
					status=false;
				}
				if(($jq(this).find("td").eq(7).find('input').val()=='' || $jq(this).find("td").eq(7).find('input').val()==undefined) && $jq(this).find("td").eq(7).find('input').is(':visible')){
					errorMessages += "Please Enter Amount Per k.m After Restriction.\n";
					$jq(this).find("td").eq(7).find('input').focus();
					status=false;
				}
				j++;
			});
		}
		if($jq('#noOfDays').val()=='' && $jq('#noOfDays').is(':visible')){
			errorMessages += "Please Enter No. Of Days.\n";
			$jq('#noOfDays').focus();
			status=false;
		}
		if($jq('#MROPaidNo').val()=='' && $jq('#MROPaidNo').is(':visible')){
			errorMessages += "Please Enter MRO Paid No..\n";
			$jq('#MROPaidNo').focus();
			status=false;
		}
		if($jq('#MROPaidDate').val()=='' && $jq('#MROPaidDate').is(':visible')){
			errorMessages += "Please Select MRO Paid Date..\n";
			$jq('#MROPaidDate').focus();
			status=false;
		}
	}else if(requestType == 'TADA TD SETTLEMENT' || requestType == 'TADA TD REIMBURSEMENT'){
		var mainJson={};
		var mroPaymentDetailsJson={};
		var journeyJson={};
		var accJson={};
		var foodJson={};
		var rmaKmJson={};
		var rmaDayJson={};
		var rmaDailyJson={};
		var rmaLocalJson={};
		var daOldValuesJson={};
		var foodDayJson={};
		var accDayJson={};
		var  mroDetailsJson={};
		if(WorkBeanMap.roleInstanceName=='TADA TASK HOLDER' || WorkBeanMap.roleInstanceName=='TA DA /Medical Section Head'){
			if($jq('#journeyDetailsId').is(':visible')){
				$jq('#journeyDetailsId tr:gt(1)').each(function(){
					$jq(this).find('td').each(function(){
						if($jq(this).find('input:text').is(':visible') && $jq(this).find('input:text').val()==''){
							errorMessages += "Please fill all details in journey details.\n";
							status=false;
						}
					});
				});
			}
			if($jq('#daNewAccDetailsId').is(':visible')){
				$jq('#daNewAccDetailsId tr:not(:first)').each(function(){
					$jq(this).find('td').each(function(){
						if($jq(this).find('input:text').is(':visible') && $jq(this).find('input:text').val()==''){
							errorMessages += "Please fill all details in accommodation details.\n";
							status=false;
						}
					});
				});
			}
			if($jq('#daNewFoodDetailsId').is(':visible')){
				$jq('#daNewFoodDetailsId tr:not(:first)').each(function(){
					$jq(this).find('td').each(function(){
						if($jq(this).find('input:text').is(':visible') && $jq(this).find('input:text').val()==''){
							errorMessages += "Please fill all details in food details.\n";
							status=false;
						}
					});
				});
			}
			if($jq('#RMAKmDetailsId').is(':visible')){
				$jq('#RMAKmDetailsId tr:not(:first)').each(function(){
					$jq(this).find('td').each(function(){
						if($jq(this).find('input:text').is(':visible') && $jq(this).find('input:text').val()==''){
							errorMessages += "Please fill all details in RMA at TD details.\n";
							status=false;
						}
					});
				});
			}
			if($jq('#RMADayDetailsId').is(':visible')){
				$jq('#RMADayDetailsId tr:not(:first)').each(function(){
					$jq(this).find('td').each(function(){
						if($jq(this).find('input:text').is(':visible') && $jq(this).find('input:text').val()==''){
							errorMessages += "Please fill all details in RMA at TD details.\n";
							status=false;
						}
					});
				});
			}
			if($jq('#RMADailyDetailsId').is(':visible')){
				$jq('#RMADailyDetailsId tr:not(:first)').each(function(){
					$jq(this).find('td').each(function(){
						if($jq(this).find('input:text').is(':visible') && $jq(this).find('input:text').val()==''){
							errorMessages += "Please fill all details in Daily RMA at TD details.\n";
							status=false;
						}
					});
				});
			}
			if($jq('#RMALocalDetailsId').is(':visible')){
				$jq('#RMALocalDetailsId tr:not(:first)').each(function(){
					$jq(this).find('td').each(function(){
						if($jq(this).find('input:text').is(':visible') && $jq(this).find('input:text').val()==''){
							errorMessages += "Please fill all details in Local RMA at TD details.\n";
							status=false;
						}
					});
				});
			}
			if($jq('#daNewFoodDayDetailsId').is(':visible')){
				$jq('#daNewFoodDayDetailsId tr:not(:first,:last)').each(function(){
					$jq(this).find('td').each(function(){
						if($jq(this).find('input:text').is(':visible') && $jq(this).find('input:text').val()==''){
							errorMessages += "Please fill all details in Day wise food details.\n";
							status=false;
						}
					});
				});
			}
			if($jq('#daOldDetailsId').is(':visible')){
				$jq('#daOldDetailsId tr:not(:first,:last)').each(function(){
					$jq(this).find('td').each(function(){
						if($jq(this).find('input:text').is(':visible') && $jq(this).find('input:text').val()==''){
							errorMessages += "Please fill all details in Stay DA and Journey DA Details.\n";
							status=false;
						}
					});
				});
			}
			if($jq('#foodDayRepID').is(':visible')){
				if(!$jq('#daNewFoodDayDetailsId').is(':visible')){
					errorMessages += "Please Generate Day wise food representaion.\n";
					status=false;
				}
			}
			
			if($jq('#foodDayRepID').is(':visible')){
				if(!$jq('#daNewAccDayDetailsId').is(':visible')){
					errorMessages += "Please Generate Day wise  representaion.\n";
					status=false;
				}
			}
			if(status){
				if($jq('#journeyDetailsId').is(':visible')){
					var i=0;
					$jq('#journeyDetailsId tr:gt(1)').each(function(){
						var subJson={};
						subJson['id']=$jq(this).find('td').eq(0).find('input:hidden').val();
						subJson['journeyDeptDate']=$jq(this).find('td').eq(0).find('input:text').val();
						subJson['journeyDeptTime']=$jq(this).find('td').eq(1).find('input').val();
						subJson['journeyDeptStn']=$jq(this).find('td').eq(2).find('input').val();
						subJson['journeyArrDate']=$jq(this).find('td').eq(3).find('input').val();
						subJson['journeyArrTime']=$jq(this).find('td').eq(4).find('input').val();
						subJson['journeyArrStn']=$jq(this).find('td').eq(5).find('input').val();
						subJson['journeyDistance']=$jq(this).find('td').eq(6).find('input').val();
						subJson['modeOfTravel']=$jq(this).find('td').eq(7).find('input').val();
						subJson['ticketFare']=$jq(this).find('td').eq(8).find('input').val();
						subJson['ticketFareAftRes']=$jq(this).find('td').eq(9).find('input').val();
						subJson['ticketCancelAmount']=$jq(this).find('td').eq(10).find('input').val();
						subJson['ticketNo']=$jq(this).find('td').eq(11).find('input').val();
						subJson['ticketCancelAmountAftRes']=$jq(this).find('td').eq(12).find('input').val();
						if($jq(this).find('td').eq(0).find('input:text').val() != undefined){
						if($jq(this).is(':visible'))
							subJson['editType']='add';
						else
							subJson['editType']='delete';
						journeyJson[i]=subJson;
						i++;
						}
					});
				}
				if($jq('#daNewAccDetailsId').is(':visible')){
					var i=0;
					$jq('#daNewAccDetailsId tr:not(:first)').each(function(){
						var subJson={};
						subJson['id']=$jq(this).find('td').eq(0).find('input:hidden').val();
						subJson['accFromDate']=$jq(this).find('td').eq(0).find('input:text').val();
						subJson['accToDate']=$jq(this).find('td').eq(2).find('input').val();
						subJson['accAmount']=$jq(this).find('td').eq(4).find('input').val();
						subJson['accAmountAftRes']=$jq(this).find('td').eq(5).find('input').val();
						subJson['claimedAmount']=$jq(this).find('td').eq(6).find('input').val();
						subJson['claimedAmountAftRes']=$jq(this).find('td').eq(7).find('input').val();
						if($jq(this).find('td').eq(0).find('input:text').val() != undefined){
						if($jq(this).is(':visible'))
							subJson['editType']='add';
						else
							subJson['editType']='delete';
						accJson[i]=subJson;
						i++;
						}
					});
				}
				if($jq('#daNewFoodDetailsId').is(':visible')){
					$jq('#kdiv').html('');
					var i=0;
					$jq('#daNewFoodDetailsId tr:not(:first)').each(function(){
						var subJson={};
						subJson['id']=$jq(this).find('td').eq(0).find('input:hidden').val();
						subJson['foodFromDate']=$jq(this).find('td').eq(0).find('input:text').val();
						subJson['foodToDate']=$jq(this).find('td').eq(1).find('input').val();
						subJson['foodAmount']=$jq(this).find('td').eq(2).find('input').val();
						subJson['foodAmountAftRes']=$jq(this).find('td').eq(3).find('input').val();
						subJson['claimedFoodAmount']=$jq(this).find('td').eq(4).find('input').val();
						if($jq(this).find('td').eq(0).find('input:text').val() != undefined){
						if($jq(this).is(':visible'))
							subJson['editType']='add';
						else
							subJson['editType']='delete';
						foodJson[i]=subJson;
						i++;
						}
					});
				}
				if($jq('#RMAKmDetailsId').is(':visible')){
					var i=0;
					$jq('#RMAKmDetailsId tr:not(:first)').each(function(){
						var subJson={};
						subJson['id']=$jq(this).find('td').eq(0).find('input:hidden').val();
						subJson['rmaKmDate']=$jq(this).find('td').eq(0).find('input:text').val();
						subJson['rmaKmFromPlace']=$jq(this).find('td').eq(1).find('input').val();
						subJson['rmaKmToPlace']=$jq(this).find('td').eq(2).find('input').val();
						subJson['rmaKmTravelBy']=$jq(this).find('td').eq(3).find('input').val();
						subJson['rmaKmDiatance']=$jq(this).find('td').eq(4).find('input').val();
						subJson['rmaKmDiatanceAftRes']=$jq(this).find('td').eq(5).find('input').val();
						subJson['rmaKmAmountPerKm']=$jq(this).find('td').eq(6).find('input').val();
						subJson['rmaKmAmountPerKmAftRes']=$jq(this).find('td').eq(7).find('input').val();
						subJson['rmaKmClaimedAmount']=$jq(this).find('td').eq(8).find('input').val();
						subJson['rmaKmClaimedAmountAftRes']=$jq(this).find('td').eq(9).find('input').val();
						if($jq(this).find('td').eq(0).find('input:text').val() != undefined){
						if($jq(this).is(':visible'))
							subJson['editType']='add';
						else
							subJson['editType']='delete';
						rmaKmJson[i]=subJson;
						i++;
						}
					});
				}
				if($jq('#RMADayDetailsId').is(':visible')){
					var i=0;
					$jq('#RMADayDetailsId tr:not(:first)').each(function(){
						var subJson={};
						subJson['id']=$jq(this).find('td').eq(0).find('input:hidden').val();
						subJson['rmaDayDate']=$jq(this).find('td').eq(0).find('input:text').val();
						subJson['rmaDayFromPlace']=$jq(this).find('td').eq(1).find('input').val();
						subJson['rmaDayToPlace']=$jq(this).find('td').eq(2).find('input').val();
						subJson['rmaDayAmountPerDay']=$jq(this).find('td').eq(3).find('input').val();
						subJson['rmaDayAmountPerDayAftRes']=$jq(this).find('td').eq(4).find('input').val();
						subJson['rmaDayClaimedAmount']=$jq(this).find('td').eq(5).find('input').val();
						subJson['rmaDayClaimedAmountAftRes']=$jq(this).find('td').eq(6).find('input').val();
						if($jq(this).find('td').eq(0).find('input:text').val() != undefined){
						if($jq(this).is(':visible'))
							subJson['editType']='add';
						else
							subJson['editType']='delete';
						rmaDayJson[i]=subJson;
						i++;
						}
					});
				}
				if($jq('#RMADailyDetailsId').is(':visible')){
					var i=0;
					$jq('#RMADailyDetailsId tr:not(:first)').each(function(){
						var subJson={};
						subJson['id']=$jq(this).find('td').eq(0).find('input:hidden').val();
						subJson['rmaDailyDate']=$jq(this).find('td').eq(0).find('input:text').val();
						subJson['rmaDailyFromPlace']=$jq(this).find('td').eq(1).find('input').val();
						subJson['rmaDailyToPlace']=$jq(this).find('td').eq(2).find('input').val();
						subJson['rmaDailyTravelBy']=$jq(this).find('td').eq(3).find('input').val();
						subJson['rmaDailyDiatance']=$jq(this).find('td').eq(4).find('input').val();
						subJson['rmaDailyDiatanceAftRes']=$jq(this).find('td').eq(5).find('input').val();
						subJson['rmaDailyAmountPerKm']=$jq(this).find('td').eq(6).find('input').val();
						subJson['rmaDailyAmountPerKmAftRes']=$jq(this).find('td').eq(7).find('input').val();
						subJson['rmaDailyClaimedAmount']=$jq(this).find('td').eq(8).find('input').val();
						subJson['rmaDailyClaimedAmountAftRes']=$jq(this).find('td').eq(9).find('input').val();
						if($jq(this).find('td').eq(0).find('input:text').val() != undefined){
						if($jq(this).is(':visible'))
							subJson['editType']='add';
						else
							subJson['editType']='delete';
						rmaDailyJson[i]=subJson;
						i++;
						}
					});
				}
				if($jq('#RMALocalDetailsId').is(':visible')){
					var i=0;
					$jq('#RMALocalDetailsId tr:not(:first)').each(function(){
						var subJson={};
						subJson['id']=$jq(this).find('td').eq(0).find('input:hidden').val();
						subJson['rmaLocalDate']=$jq(this).find('td').eq(0).find('input:text').val();
						subJson['rmaLocalFromPlace']=$jq(this).find('td').eq(1).find('input').val();
						subJson['rmaLocalToPlace']=$jq(this).find('td').eq(2).find('input').val();
						subJson['rmaLocalTravelBy']=$jq(this).find('td').eq(3).find('input').val();
						subJson['rmaLocalDiatance']=$jq(this).find('td').eq(4).find('input').val();
						subJson['rmaLocalDiatanceAftRes']=$jq(this).find('td').eq(5).find('input').val();
						subJson['rmaLocalAmountPerKm']=$jq(this).find('td').eq(6).find('input').val();
						subJson['rmaLocalAmountPerKmAftRes']=$jq(this).find('td').eq(7).find('input').val();
						subJson['rmaLocalClaimedAmount']=$jq(this).find('td').eq(8).find('input').val();
						subJson['rmaLocalClaimedAmountAftRes']=$jq(this).find('td').eq(9).find('input').val();
						if($jq(this).find('td').eq(0).find('input:text').val() != undefined){
						if($jq(this).is(':visible'))
							subJson['editType']='add';
						else
							subJson['editType']='delete';
						rmaLocalJson[i]=subJson;
						i++;
						}
					});
				}
				if($jq('#daOldDetailsId').is(':visible')){
					var i=0;
					$jq('#daOldDetailsId tr:not(:first)').each(function(){
						var subJson={};
						subJson['id']=$jq(this).find('td').eq(0).find('input:hidden').val();
						subJson['jdaDate']=$jq(this).find('td').eq(0).find('input:text').val();
						subJson['jdadays']=$jq(this).find('td').eq(1).find('input').val();
						subJson['jdaAmount']=$jq(this).find('td').eq(2).find('input').val();
						subJson['jdaAmountTot']=$jq(this).find('td').eq(3).find('input').val();
						subJson['sdadays']=$jq(this).find('td').eq(4).find('input').val();
						subJson['sdaAmount']=$jq(this).find('td').eq(5).find('input').val();
						subJson['sdaAmountTot']=$jq(this).find('td').eq(6).find('input').val();
						if($jq(this).find('td').eq(0).find('input:text').val() != undefined){
						if($jq(this).is(':visible'))
							subJson['editType']='add';
						else
							subJson['editType']='delete';
						daOldValuesJson[i]=subJson;
						i++;
						}
					});
				}
				if($jq('#daNewFoodDayDetailsId').is(':visible')){
					var i=0;
					$jq('#daNewFoodDayDetailsId tr:not(:first,:last)').each(function(){
						var subJson={};
						subJson['id']=$jq(this).find('td').eq(0).find('input:hidden').val();
						subJson['repDate']=$jq(this).find('td').eq(0).find('input:text').val();
						subJson['repAmount']=$jq(this).find('td').eq(1).find('input').val();
						if($jq(this).find('td').eq(0).find('input:hidden').val() != undefined && $jq(this).find('td').eq(0).find('input:text').val() != undefined && $jq(this).find('td').eq(1).find('input').val() != undefined){
							foodDayJson[i]=subJson;
							i++;
						}
						
					});
				}
				if($jq('#daNewAccDayDetailsId').is(':visible')){
					var i=0;
					$jq('#daNewAccDayDetailsId tr:not(:first,:last)').each(function(){
						var subJson={};
						subJson['id']=$jq(this).find('td').eq(0).find('input:hidden').val();
						subJson['repDate']=$jq(this).find('td').eq(0).find('input:text').val();
						subJson['repAmount']=$jq(this).find('td').eq(1).find('input').val();
						if($jq(this).find('td').eq(0).find('input:hidden').val() != undefined && $jq(this).find('td').eq(0).find('input:text').val() != undefined && $jq(this).find('td').eq(1).find('input').val() != undefined){
						accDayJson[i]=subJson;
						i++;
						}
					});
				}
			}
		}
		
		if($jq('#penalInterestTabId').is(':visible')){
		$jq('#penalInterestTabId tr:not(:first)').each(function(){
			if($jq(this).find('td').eq(0).find('input').is(':visible')){
			if(document.getElementById('penalInterestReq10').checked == false
					&& document.getElementById('penalInterestReq20').checked == false)
			{
				errorMessages +="Please select YES or NO radio button.\n";
				errorMessages +="Please Enter no of days.\n";
				status=false;
			}
			}
			if(($jq(this).find('td').eq(6).find('input').is(':visible') && $jq(this).find('td').eq(6).find('input').val()=='') || ($jq(this).find('td').eq(7).find('input').is(':visible') && $jq(this).find('td').eq(7).find('input').val()=='')){
			//if(($jq(this).find('td').eq(6).find('input').is(':visible') && $jq(this).find('td').eq(6).find('input').val()=='') || ($jq(this).find('td').eq(7).find('input').is(':visible') && $jq(this).find('td').eq(7).find('input').val()=='')||(!($jq(this).find('td').eq(7).find('input').is(':visible')||$jq(this).find('td').eq(6).find('input').is(':visible')))){	
			   errorMessages +="Please Hold The Request Until User Pay The MRO Amount.\n";
				errorMessages +="Please Fill All Details In Penal Interest Grid\n";
				status=false;
			}
			else{
				var i=0;
				var subJson ={};
				subJson['penalInterestReq10']=$jq(this).find('td').eq(0).find('input').val();
				
				if($jq(this).find('td').eq(0).find('input').is(':visible') && $jq(this).find('td').eq(0).find('input').val()!=''){
					subJson['penalInterestReq']=$jq(this).find('td').eq(0).find('input').val();
				}
				
				if($jq(this).find('td').eq(1).find('input').is(':visible') && $jq(this).find('td').eq(1).find('input').val()!=''){
					subJson['noOfDays']=$jq(this).find('td').eq(1).find('input').val();
				}
				if($jq(this).find('td').eq(2).find('input').is(':visible') && $jq(this).find('td').eq(2).find('input').val()!=''){
						subJson['interestRate']=$jq(this).find('td').eq(2).find('input').val();
				}
				if($jq(this).find('td').eq(3).find('input').is(':visible') && $jq(this).find('td').eq(3).find('input').val()!=''){
						subJson['unUtliliseBal']=$jq(this).find('td').eq(3).find('input').val();
				}
				
				if($jq(this).find('td').eq(4).find('input').is(':visible') && $jq(this).find('td').eq(4).find('input').val()!=''){
					 subJson['penalInterest']=$jq(this).find('td').eq(4).find('input').val();
				}
				if($jq(this).find('td').eq(5).find('input').is(':visible') && $jq(this).find('td').eq(5).find('input').val()!=''){
						subJson['excessAmountFine0']=$jq(this).find('td').eq(5).find('input').val();
				}
				/*if($jq(this).find('td').eq(5).find('input').is(':visible') && $jq(this).find('td').eq(5).find('input').val()!=''){
					subJson['PenalInterestId']=$jq(this).find('td').eq(5).find('input').val();
			}*/
				//subJson['id']=$jq(this).find('td').eq(6).find('input:hidden').val();
				//subJson['id']=$jq(this).find('td').eq(6).find('input').val('');
				subJson['id']='';
				mroDetailsJson[i]=subJson;
				i++
			}
			/*if($jq(this).find('td').eq(1).find('input').is(':visible') || $jq(this).find('td').eq(2).find('input').is(':visible') || $jq(this).find('td').eq(3).find('input').is(':visible')){
				errorMessages +="Please Hold The Request Until User Pay The MRO Amount.\n";
				status=false;
			}*/
		});
		}
		var k=0;
		$jq('#penalInterestTabId tr:not(:first)').each(function(){
				if($jq.trim($jq(this).find('td').eq(6).find('input').val())!='' && $jq.trim($jq(this).find('td').eq(7).find('input').val()) !=""){
				var subJson={};
        		subJson['mroId']=$jq(this).find('td').eq(0).find('input').val();
				if($jq(this).find('td').eq(6).find('input').is(':visible') && $jq(this).find('td').eq(6).find('input').val()!=''){
					subJson['mroNo']=$jq(this).find('td').eq(6).find('input').val();
				
				}
				if($jq(this).find('td').eq(7).find('input').is(':visible') && $jq(this).find('td').eq(7).find('input').val()!=''){
					subJson['mroDate']=$jq(this).find('td').eq(7).find('input').val();
					
				}
				if($jq(this).find('td').eq(6).find('input').val() != undefined && $jq(this).find('td').eq(7).find('input').val() != undefined ){
					mroPaymentDetailsJson[k]=subJson;
					k++;
				}
				
			}});
		mainJson['mroDetails'] = mroDetailsJson;
		mainJson['mroPaymentDetails']=mroPaymentDetailsJson;
		mainJson['journeyDetails']=journeyJson;
		mainJson['accDetails']=accJson;
		mainJson['foodDetails']=foodJson;
		mainJson['rmaKmDetails']=rmaKmJson;
		mainJson['rmaDayDetails']=rmaDayJson;
		mainJson['rmaDailyDetails']=rmaDailyJson;
		mainJson['rmaLocalDetails']=rmaLocalJson;
		mainJson['oldDaDetails']=daOldValuesJson;
		mainJson['foodDayDetails']=foodDayJson;
		mainJson['accDayDetails']=accDayJson;
		document.forms[0].jsonValue.value = JSON.stringify(mainJson);
	}else if(requestType == 'TADA TD ADVANCE'){
		var mainJson={};
		var tempJson={};
		var i=1;
		if($jq('#accAmtPerDay').val()=='' || $jq('#accAmtPerDay').val()=='.'){
			errorMessages += "Please Enter Amount per day for Accommodation.\n";
			status=false;
		}
		if($jq('#foodAmtPerDay').val()=='' || $jq('#foodAmtPerDay').val()=='.'){
			errorMessages += "Please Enter Amount per day for Food.\n";
			status=false;
		}
		if($jq('#projectName').val()== 'Select'){
			errorMessages += "Please Select Project.\n";
			status=false;
		}
		$jq('#reqJourneyDetailsId tr:not(:first)').each(function(){
			if($jq(this).find('td').eq(8).find('input').val()=='' || $jq(this).find('td').eq(8).find('input').val()=='.'){
				errorMessages += "Please Enter Ticket Fare Aft Res. in row-"+i+" of Journey Details.\n";
				status=false;
			}
			i++;
		});
		if(status){
			var i=0;
			$jq('#reqJourneyDetailsId tr:not(:first)').each(function(){
				var subJson={};
				subJson['id']=$jq(this).find('td').eq(8).find('input').attr('id');
				subJson['ticketFareAftRes']=$jq(this).find('td').eq(8).find('input').val();
				tempJson[i]=subJson;
				i++;
			});
			mainJson['tdAdvDetails']=tempJson;
			//mainJson['noOfDaysAcc']=$jq('#noOfDaysAcc').val();
			mainJson['accAmtPerDay']=$jq('#accAmtPerDay').val();
			mainJson['totalAccAmt']=$jq('#totalAccAmt').val();
			//mainJson['noOfDaysFood']=$jq('#noOfDaysFood').val();
			if($jq('#projectName').is(':visible')){
				mainJson['projectName'] = $jq('#projectName').val();
			}else{
				mainJson['projectName'] = '';
			}
			mainJson['foodAmtPerDay']=$jq('#foodAmtPerDay').val();
			mainJson['totalFoodAmt']=$jq('#totalFoodAmt').val();
			mainJson['reqId']=$jq('#noOfDaysAcc1').val();
			mainJson['issueAuthority']=$jq('#issueAuthority').val();
			document.forms[0].issuedAdvAmount.value=$jq('#issuedAdvAmount').val();
			document.forms[0].jsonValue.value = JSON.stringify(mainJson);
		}
	}else if(requestType == 'TUITION FEE'){
		var errorMessages='';
		var	peakJson={};
		var i=0;
		var j=0;
		$jq('#indDetails').find('fieldSet').each(function(){
		$jq(this).find('table tr:not(:first)').each(function(){
			if($jq(this).find("td").eq(4).find('input').val()==''){
				errorMessages="please Enter The Amt To Be Sanctioned.\n";
				status=false;
			}
	    });
		j++;
		});
		$jq('#indDetails').find('fieldSet').each(function(){
			var childId=$jq(this).attr('id');
			var mainJSON={};
			var h=0;
		$jq(this).find('table tr:not(:first)').each(function(){
			var subJSON={};
			if($jq(this).find("td").eq(4).find('input').val()!=''){
				subJSON['sanctionedAmount']=$jq(this).find("td").eq(4).find('input').val();
				subJSON['claimId']=$jq(this).find('td').eq(4).find('input').attr('id');
				subJSON['tuitionClaimTableId']=$jq(this).attr('id');
			}
			else{
				subJSON['sanctionedAmount']=0;
				subJSON['claimId']=$jq(this).find('td').eq(4).find('input').attr('id');
						
			}
			mainJSON[h]=subJSON;
			h++;
			});
		var	superJson={};
        superJson['claimDetails']=mainJSON;
		superJson['childId']=childId;
		superJson['subTotalAmount']=$jq('#claimDetails1').find('tr').eq(i+1).find("td").eq(7).find('input').val();
		peakJson[i]=superJson;
	    i++;
		});
		document.forms[0].jsonValue.value = JSON.stringify(peakJson);
	}else if(requestType == 'TELEPHONE BILL'){
		var i=0;
		var j=0;
		var h=0;
		var mainJSON={};
		var peakJSON={};
	    $jq('#telephoneClaimTable tr:not(:first)').each(function(){
			if($jq(this).find('td').eq(6).find('input').val()==''){
				errorMessages += "Please enter The Amount To Be Sanctioned\n"
			    status=false;
			}
			i++;
		});
	    
	    //$jq('#telephoneClaimTable tr:not(:first)').each(function (){
	    $jq('#telephoneClaimTable tr:not(:first,:last)').each(function (){
	    	var subJSON={};
	    	if($jq(this).find('td').eq(6).find('input').val()!=''){
	    		subJSON['sanctionedAmount']=$jq(this).find('td').eq(6).find('input').val();
	    		subJSON['claimId']=$jq(this).find('td').eq(6).find('input').attr('id');
	    	}else{
	    		subJSON['sanctionedAmount']=0;
	    		subJSON['claimId']=$jq(this).find('td').eq(6).find('input').attr('id');
	    	}
	    	mainJSON[h]=subJSON;
	    	h++;
	    });
	    $jq('#telephoneClaimDetails tr:not(:first)').each(function (){
	    	var superJSON={};
	    	superJSON['telephoneClaimDetails']=mainJSON;
	    	superJSON['subTotalAmount']=$jq(this).find('td').eq(4).find('input').val();
	    	superJSON['cashAssignmentAmount']=$jq('#telephoneClaimTable tr:last').find('td').eq(1).find('input').val();
	    	peakJSON[j]=superJSON;
	    	j++;
	    });
	    document.forms[0].remarks.value=$jq('#remarks').val();
	    document.forms[0].jsonValue.value = JSON.stringify(peakJSON);
	}else if(requestType == 'MT VEHICLE'){
		if(stateID !=null && stateID=='105'){
			if(complJCount !=0){
				status = false;
				errorMessages+="Please Allocate Vehicle For All Journies In This Request\n";
			}
		}
	}
	if(status){
		document.forms[0].param.value = "approved";
		document.forms[0].type.value = requestType;
		
		new Ajax.Updater('historyDetails', url, {
			
			onComplete : function() {
				blockButtons();
			},
			parameters : Form.serialize(document.forms[0]),
			asynchronous : false,
			evalScripts : true
		});
	} else {
		alert(errorMessages);
	}
	
}
function calculateAmountAfterRestriction() {
	var afterRestriction='';
	var amount=0;
	var status=true;
	$jq("#journeyDetails tr:not(:first)").each(function() {
		if($jq(this).find("td").eq(11).find('input').val()!='' && $jq(this).find("td").eq(11).find('input').val()!=undefined) {
			afterRestriction = afterRestriction + $jq(this).find("td").eq(11).find('input:text').val() + '-'+$jq(this).find("td").eq(11).find('input:text').attr('id')+'@'
			amount = parseInt(amount)+parseInt($jq(this).find("td").eq(11).find('input:text').val());
			$jq('#amountoo').val(amount);//= parseInt(amount)+parseInt($jq(this).find("td").eq(11).find('input:text').val());
			//amount= round2Fixed(amount);
			//amount = amount+($jq(this).find("td").eq(11).find('input:text').val());
		}
		
		if($jq(this).find("td").eq(11).find('input:text').val()=='') {
			status=false;
		}
	});
	$jq('#restrictionAmount').html(amount);
	$jq('#amount').val(afterRestriction);
	if(parseInt(ltcAdvanceMoney) > 0) {
		$jq('#settleAmount').val(parseInt(amount)-parseInt(ltcAdvanceMoney));
		//$jq('#settleAmount').val(amount-ltcAdvanceMoney);
	}else {
		$jq('#settleAmount').val(amount);
	}
	
	if(parseInt(ltcAdvanceMoney)>0) {
		/**
		 * Settlement case
		 */
		$jq('#restrictionAmount').html(parseInt(amount)-parseInt(ltcAdvanceMoney));
		//$jq('#restrictionAmount').html(amount-ltcAdvanceMoney);
	}
	
	if(parseInt($jq('#restrictionAmount').text().trim()) < 0) {
		$jq('#MRODetailsDiv').attr("style","display:block");
		$jq('#excessAmountDiv').attr("style","display:block");
		$jq('#excessAmountFineDiv').attr("style","display:block");
		$jq('#excessAmount').val($jq('#restrictionAmount').text().trim());
	}else {
		$jq('#MRODetailsDiv').attr("style","display:none");
		$jq('#excessAmountDiv').attr("style","display:none");
		$jq('#excessAmountFineDiv').attr("style","display:none");
		$jq('#excessAmount').val('');
	}
	if($jq('#excessAmount').is(':visible') && $jq('#excessAmountFine').val()!='') {
		var remark=Math.abs($jq('#excessAmount').val())+parseInt($jq('#excessAmountFine').val());
		$jq('#remarks').val('Pay '+remark+' amount to MRO');
	}else{
		$jq('#remarks').val('');
		
	}
	return status;
}
function resetPisRequest() {
	document.getElementById('personalNumber').value = "";
	document.getElementById('internalNo').value = "";
	document.getElementById('residenceNo').value = "";
}

/*function leaveApplication1(id){	
	window.open('./leaveRequest.htm?param=leaveReport&folder=LeaveApplications&requestID='+id,'Leave','fullscreen=no,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no')
}*/
function leaveApplication(id,type,requestType){
	window.open('./report.htm?param=leaveReport&type='+type+'&requestID='+id+'&requestType='+requestType,'Leave','fullscreen=no,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no')
}
function showLeaveCard(sfid){	
	window.open('./leaveRequest.htm?param=leavecard&menuFlag=disable&sfID='+sfid,'LeaveCard','fullscreen=no,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no')
}
function showFile(fileName){
	window.open('./leaveRequest.htm?param=leaveReport&folder=LeaveAttachments&file='+fileName,'Leave','fullscreen=no,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no')
}
function showDatabaseFile(alertId){
	if(alertId !=""){
		window.open("./leaveRequest.htm?param=getDbFile&fileId="+ alertId,'preview','fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no')
	}else{
		alert("Please Upload The File \n");
	}	
}

function showDatabaseFile12(alertId,fileno,fileName){
	//alert("download The File \n");

	var fileno=fileno+1;
	window.open("./leaveRequest.htm?param=getPreccriptionFile&fileId="+ alertId+"&fileno="+fileno+"&fileName="+fileName,'preview','fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no')
	//alert("download The File \n");
}


/*function eerpPrescriptionDownloded1(){
	alert("Please Upload The File \n");

		window.open("./leaveRequest.htm?param=getPreccriptionFile,'preview','fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no')

		alert("Please Upload The File \n");
	}	
}*/


function ltcApplicationForm(id){
	window.open('./file.htm?param=download&folder=LTCAttachments&requestID='+id,'LTC','fullscreen=no,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
}
function viewLoanForm(loanID,requestID,report){
	window.open('./report.htm?param=loanReport&requestID='+requestID+'&type='+loanID+'&report='+report,'Loan','fullscreen=no,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no')
}
function viewFPAForm(requestId,report){
	window.open('./report.htm?param=fpaReport&requestID='+requestId+'&report='+report,'FPA','fullscreen=no,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
}
function viewAttachment(id){
	if(id!=''){
		window.open('./file.htm?param=download&folder=TransferAttachments&requestID='+id,'Transfer','fullscreen=no,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
	} else {
		alert('Employee didn\'t uploaded');
	}	
}
function sendToEMU(historyID,param,requestID, requestTypeID){
	var url = 'workflow.htm?param='+param+'&historyID=' + historyID
	+ '&requestTypeID=' + requestTypeID+'&requestID=' + requestID+'&remarks='+$jq("#remarks").val();
	new Ajax.Updater('historyDetails', url, {
			onComplete : function() {
			},
			asynchronous : true,
			evalScripts : true
		});
}
function waitRequest(historyID, requestID, requestTypeID) {
	var errorMessages = "";
	var status = true;
	var requestDetails = {};
	reqTypeID = requestTypeID;//Set to global
	var url = 'workflow.htm?param=wait&historyID=' + historyID
			+ '&requestTypeID=' + requestTypeID + '&requestID=' + requestID+'&remarks='+$jq("#remarks").val();

	if (requestTypeID == selfTransferRequestID || requestTypeID == empTransferRequestID) {			
		if($jq('#doPartNo').val()!='' && $jq('#doPartDate').val()==''){
			errorMessages += "Please enter ION date\n";
			if(status){
				status = false;
				$jq('#doPartDate').focus();
			}
		}
		if($jq('#doPartNo').val()=='' && $jq('#doPartDate').val()!=''){
			errorMessages += "Please enter ION number\n";
			if(status){
				status = false;
				$jq('#doPartNo').focus();
			}
		}
		if($jq('#doPartNo').val()!='' && $jq('#doPartDate').val()!=''){
			url += '&doPartNo='+$jq('#doPartNo').val()+'&doPartDate='+$jq('#doPartDate').val()+'&transferType='+$jq('#transferType').val();
		}		
	} 
	if($jq('#foodDayRepID').is(':visible')){
		if(!$jq('#daNewFoodDayDetailsId').is(':visible')){
			errorMessages += "Please Generate Day wise  representaion.\n";
			status=false;
		}
	}
/*	if($jq('#penalInterestTabId').is(':visible')){                                                 //These lines has been commented for remove comments::Prasad
		$jq('#penalInterestTabId tr:not(:first)').each(function(){
			$jq(this).find('td').each(function(){
				if($jq(this).find('input:text').is(':visible') && $jq(this).find('input:text').val()==''){
					errorMessages += "Please fill all details in Penal details.\n";
					status=false;
				}
			});
		});
	}*/
//		else if (requestTypeID == loanRequestID) {
//		if ($jq('#sanctionedAmount').val() == '') {
//			errorMessages += "Please enter sanctioned amount\n";
//			if (status) {
//				status = false;
//				$jq('#sanctionedAmount').focus();
//			}
//		}
//		if ($jq('#sanctionedDate').val() == '') {
//			errorMessages += "Please select sanctioned date\n";
//			if (status) {
//				status = false;
//				$jq('#sanctionedDate').focus();
//			}
//		}
//		if ($jq('#sanctionedInstalments').val() == '') {
//			errorMessages += "Please enter sanctioned installments\n";
//			if (status) {
//				status = false;
//				$jq('#sanctionedInstalments').focus();
//			}
//		}
//		if ($jq('#recStartDate').val() == '') {
//			errorMessages += "Please select recovery start date\n";
//			if (status) {
//				status = false;
//				$jq('#recStartDate').focus();
//			}
//		}
//		if ($jq('#emi').val() == '') {
//			errorMessages += "Please enter emi\n";
//			if (status) {
//				status = false;
//				$jq('#emi').focus();
//			}
//		}
//		if ($jq('#loanRefNo').val() == '') {
//			errorMessages += "Please enter reference number\n";
//			if (status) {
//				status = false;
//				$jq('#loanRefNo').focus();
//			}
//		}
//		if ($jq('#bankAccount').val() == '') {
//			errorMessages += "Please enter bank account number\n";
//			if (status) {
//				status = false;
//				$jq('#bankAccount').focus();
//			}
//		}
//		if ($jq('#bankBranch').val() == '') {
//			errorMessages += "Please enter bank branch details\n";
//			if (status) {
//				status = false;
//				$jq('#bankBranch').focus();
//			}
//		}
//		url += '&sanctionedAmount='+$jq('#sanctionedAmount').val()+'&sanctionedDate='+$jq('#sanctionedDate').val()+'&sanctionedInstalments='+$jq('#sanctionedInstalments').val()+
//		'&recStartDate='+$jq('#recStartDate').val()+'&emi='+$jq('#emi').val()+'&loanRefNo='+$jq('#loanRefNo').val()+'&bankAccount='+$jq('#bankAccount').val()+'&bankBranch='+$jq('#bankBranch').val();
//		
//	}
		else if(requestTypeID == ltcSettlementReqID){
		if(!calculateAmountAfterRestriction()) {
			status=false;
			errorMessages +='enter amount after restriction \n';
		}
		if($jq('#excessAmountFine').val()==''){
			status=false;
			errorMessages +='enter Penal Interest\n';
		}
		if($jq('#noOfDays').val()==''){
			status=false;
			errorMessages +='enter No of Days\n';
		}
		if(status){
			url += '&excessAmount='+$jq('#excessAmount').val()+'&amount='+$jq('#amount').val()+'&excessAmountFine='+$jq('#excessAmountFine').val()+'&penalNoOfDays='+$jq('#noOfDays').val()+'&penalInterestId='+$jq('input:text[name=interestRate]').attr('id');	
		}
		
	}  if(requestTypeID == '48'){
		var accDetailsJson={};
		var mainJson={};
		var mroDetailsJson={};
		var mroPaymentDetailsJson={};
		var daOldValuesJson={};
		var journeyJson={};
		var accJson={};
		var accDayJson={};
		var foodJson={};
		var rmaKmJson={};
		var rmaDayJson={};
		var rmaDailyJson={};
		var rmaLocalJson={};
		var foodDayJson={};
		if(WorkBeanMap.roleInstanceName=='TADA TASK HOLDER' || WorkBeanMap.roleInstanceName=='TA DA /Medical Section Head'){
			if($jq('#journeyDetailsId').is(':visible')){
				$jq('#journeyDetailsId tr:gt(1)').each(function(){
					$jq(this).find('td').each(function(){
						if($jq(this).find('input:text').is(':visible') && $jq(this).find('input:text').val()==''){
							errorMessages += "Please fill all details in journey details.\n";
							status=false;
						}
					});
				});
			}
			if($jq('#daNewAccDetailsId').is(':visible')){
				$jq('#daNewAccDetailsId tr:not(:first)').each(function(){
					$jq(this).find('td').each(function(){
						if($jq(this).find('input:text').is(':visible') && $jq(this).find('input:text').val()==''){
							errorMessages += "Please fill all details in accommodation details.\n";
							status=false;
						}
					});
				});
			}
			if($jq('#daNewFoodDetailsId').is(':visible')){
				$jq('#daNewFoodDetailsId tr:not(:first)').each(function(){
					$jq(this).find('td').each(function(){
						if($jq(this).find('input:text').is(':visible') && $jq(this).find('input:text').val()==''){
							errorMessages += "Please fill all details in food details.\n";
							status=false;
						}
					});
				});
			}
			if($jq('#RMAKmDetailsId').is(':visible')){
				$jq('#RMAKmDetailsId tr:not(:first)').each(function(){
					$jq(this).find('td').each(function(){
						if($jq(this).find('input:text').is(':visible') && $jq(this).find('input:text').val()==''){
							errorMessages += "Please fill all details in RMA at TD details.\n";
							status=false;
						}
					});
				});
			}
			if($jq('#RMADayDetailsId').is(':visible')){
				$jq('#RMADayDetailsId tr:not(:first)').each(function(){
					$jq(this).find('td').each(function(){
						if($jq(this).find('input:text').is(':visible') && $jq(this).find('input:text').val()==''){
							errorMessages += "Please fill all details in RMA at TD details.\n";
							status=false;
						}
					});
				});
			}
			if($jq('#RMADailyDetailsId').is(':visible')){
				$jq('#RMADailyDetailsId tr:not(:first)').each(function(){
					$jq(this).find('td').each(function(){
						if($jq(this).find('input:text').is(':visible') && $jq(this).find('input:text').val()==''){
							errorMessages += "Please fill all details in Daily RMA at TD details.\n";
							status=false;
						}
					});
				});
			}
			if($jq('#RMALocalDetailsId').is(':visible')){
				$jq('#RMALocalDetailsId tr:not(:first)').each(function(){
					$jq(this).find('td').each(function(){
						if($jq(this).find('input:text').is(':visible') && $jq(this).find('input:text').val()==''){
							errorMessages += "Please fill all details in Local RMA at TD details.\n";
							status=false;
						}
					});
				});
			}
			if($jq('#daNewFoodDayDetailsId').is(':visible')){
				$jq('#daNewFoodDayDetailsId tr:not(:first,:last)').each(function(){
					$jq(this).find('td').each(function(){
						if($jq(this).find('input:text').is(':visible') && $jq(this).find('input:text').val()==''){
							errorMessages += "Please fill all details in Day wise food details.\n";
							status=false;
						}
					});
				});
			}
			if($jq('#daOldDetailsId').is(':visible')){
				$jq('#daOldDetailsId tr:not(:first,:last)').each(function(){
					$jq(this).find('td').each(function(){
						if($jq(this).find('input:text').is(':visible') && $jq(this).find('input:text').val()==''){
							errorMessages += "Please fill all details in Stay DA and Journey DA Details.\n";
							status=false;
						}
					});
				});
			}
		var y=1;                                                          //This function has been commented for to avoid checks
			$jq('#penalInterestTabId tr:not(:first)').each(function(){
				if($jq(this).find('td').eq(1).find('input:text').is(':visible')){
					if($jq(this).find('td').eq(1).find('input:text').val()==''){
						errorMessages += "Please Enter No. of days of row-"+y+" in penal interest details.\n";
						status=false;
					}
				}
				if($jq(this).find('td').eq(4).find('input:text').is(':visible')){
					if($jq(this).find('td').eq(4).find('input:text').val()==''){
						errorMessages += "Please Enter penal interest of row-"+y+" in penal interest details.\n";
						status=false;
					}
				}
				/*if($jq(this).find('td').eq(6).find('input:text').is(':visible')){
					if($jq(this).find('td').eq(6).find('input:text').val()==''){
						errorMessages += "Please Enter MRO No. of row-"+y+" in penal interest details.\n";
						status=false;
					}
				}
				if($jq(this).find('td').eq(7).find('input:text').is(':visible')){
					if($jq(this).find('td').eq(7).find('input:text').val()==''){
						errorMessages += "Please Select MRO Date. of row-"+y+" in penal interest details.\n";
						status=false;
					}
				}*/
				y++;
			});
			var flagVal=false;
			if(status){                    
				i=0;
				$jq('#penalInterestTabId tr:not(:first)').each(function(){
					if($jq(this).find('td').eq(6).find('input:text').is(':visible') && $jq(this).find('td').eq(7).find('input:text').is(':visible') && !$jq(this).next().is(':visible')){
					if( $jq(this).find('td').eq(6).find('input:text').val()!=''){
						
						status=false;
						flagVal=true;
						i++
					}
						
					}
				});
			}
			if(flagVal){
				errorMessages += "This request is already in holding state.\n";
			}
			if(status){
				if($jq('#journeyDetailsId').is(':visible')){
					var i=0;
					$jq('#journeyDetailsId tr:gt(1)').each(function(){
						var subJson={};
						subJson['id']=$jq(this).find('td').eq(0).find('input:hidden').val();
						subJson['journeyDeptDate']=$jq(this).find('td').eq(0).find('input:text').val();
						subJson['journeyDeptTime']=$jq(this).find('td').eq(1).find('input').val();
						subJson['journeyDeptStn']=$jq(this).find('td').eq(2).find('input').val();
						subJson['journeyArrDate']=$jq(this).find('td').eq(3).find('input').val();
						subJson['journeyArrTime']=$jq(this).find('td').eq(4).find('input').val();
						subJson['journeyArrStn']=$jq(this).find('td').eq(5).find('input').val();
						subJson['journeyDistance']=$jq(this).find('td').eq(6).find('input').val();
						subJson['modeOfTravel']=$jq(this).find('td').eq(7).find('input').val();
						subJson['ticketFare']=$jq(this).find('td').eq(8).find('input').val();
						subJson['ticketFareAftRes']=$jq(this).find('td').eq(9).find('input').val();
						subJson['ticketCancelAmount']=$jq(this).find('td').eq(10).find('input').val();
						subJson['ticketNo']=$jq(this).find('td').eq(11).find('input').val();
						subJson['ticketCancelAmountAftRes']=$jq(this).find('td').eq(12).find('input').val();
						if($jq(this).find('td').eq(0).find('input:text').val() != undefined){
						if($jq(this).is(':visible'))
							subJson['editType']='add';
						else
							subJson['editType']='delete';
						journeyJson[i]=subJson;
						i++;
						}
					});
				}
				if($jq('#daNewAccDetailsId').is(':visible')){
					var i=0;
					$jq('#daNewAccDetailsId tr:not(:first)').each(function(){
						var subJson={};
						subJson['id']=$jq(this).find('td').eq(0).find('input:hidden').val();
						subJson['accFromDate']=$jq(this).find('td').eq(0).find('input:text').val();
						subJson['accToDate']=$jq(this).find('td').eq(2).find('input').val();
						subJson['accAmount']=$jq(this).find('td').eq(4).find('input').val();
						subJson['accAmountAftRes']=$jq(this).find('td').eq(5).find('input').val();
						subJson['claimedAmount']=$jq(this).find('td').eq(6).find('input').val();
						subJson['claimedAmountAftRes']=$jq(this).find('td').eq(7).find('input').val();
						if($jq(this).find('td').eq(0).find('input:text').val() != undefined){
						if($jq(this).is(':visible'))
							subJson['editType']='add';
						else
							subJson['editType']='delete';
						accJson[i]=subJson;
						i++;
						}
					});
				}
				if($jq('#daNewFoodDetailsId').is(':visible')){
					var i=0;
					$jq('#daNewFoodDetailsId tr:not(:first)').each(function(){
						var subJson={};
						subJson['id']=$jq(this).find('td').eq(0).find('input:hidden').val();
						subJson['foodFromDate']=$jq(this).find('td').eq(0).find('input:text').val();
						subJson['foodToDate']=$jq(this).find('td').eq(1).find('input').val();
						subJson['foodAmount']=$jq(this).find('td').eq(2).find('input').val();
						subJson['foodAmountAftRes']=$jq(this).find('td').eq(3).find('input').val();
						subJson['claimedFoodAmount']=$jq(this).find('td').eq(4).find('input').val();
						if($jq(this).find('td').eq(0).find('input:text').val() != undefined){
						if($jq(this).is(':visible'))
							subJson['editType']='add';
						else
							subJson['editType']='delete';
						foodJson[i]=subJson;
						i++;
						}
					});
				}
				if($jq('#RMAKmDetailsId').is(':visible')){
					var i=0;
					$jq('#RMAKmDetailsId tr:not(:first)').each(function(){
						var subJson={};
						subJson['id']=$jq(this).find('td').eq(0).find('input:hidden').val();
						subJson['rmaKmDate']=$jq(this).find('td').eq(0).find('input:text').val();
						subJson['rmaKmFromPlace']=$jq(this).find('td').eq(1).find('input').val();
						subJson['rmaKmToPlace']=$jq(this).find('td').eq(2).find('input').val();
						subJson['rmaKmTravelBy']=$jq(this).find('td').eq(3).find('input').val();
						subJson['rmaKmDiatance']=$jq(this).find('td').eq(4).find('input').val();
						subJson['rmaKmDiatanceAftRes']=$jq(this).find('td').eq(5).find('input').val();
						subJson['rmaKmAmountPerKm']=$jq(this).find('td').eq(6).find('input').val();
						subJson['rmaKmAmountPerKmAftRes']=$jq(this).find('td').eq(7).find('input').val();
						subJson['rmaKmClaimedAmount']=$jq(this).find('td').eq(8).find('input').val();
						subJson['rmaKmClaimedAmountAftRes']=$jq(this).find('td').eq(9).find('input').val();
						if($jq(this).find('td').eq(0).find('input:text').val() != undefined){
						if($jq(this).is(':visible'))
							subJson['editType']='add';
						else
							subJson['editType']='delete';
						rmaKmJson[i]=subJson;
						i++;
						}
					});
				}
				if($jq('#RMADayDetailsId').is(':visible')){
					var i=0;
					$jq('#RMADayDetailsId tr:not(:first)').each(function(){
						var subJson={};
						subJson['id']=$jq(this).find('td').eq(0).find('input:hidden').val();
						subJson['rmaDayDate']=$jq(this).find('td').eq(0).find('input:text').val();
						subJson['rmaDayFromPlace']=$jq(this).find('td').eq(1).find('input').val();
						subJson['rmaDayToPlace']=$jq(this).find('td').eq(2).find('input').val();
						subJson['rmaDayAmountPerDay']=$jq(this).find('td').eq(3).find('input').val();
						subJson['rmaDayAmountPerDayAftRes']=$jq(this).find('td').eq(4).find('input').val();
						subJson['rmaDayClaimedAmount']=$jq(this).find('td').eq(5).find('input').val();
						subJson['rmaDayClaimedAmountAftRes']=$jq(this).find('td').eq(6).find('input').val();
						if($jq(this).find('td').eq(0).find('input:text').val() != undefined){
						if($jq(this).is(':visible'))
							subJson['editType']='add';
						else
							subJson['editType']='delete';
						rmaDayJson[i]=subJson;
						i++;
						}
					});
				}
				if($jq('#RMADailyDetailsId').is(':visible')){
					var i=0;
					$jq('#RMADailyDetailsId tr:not(:first)').each(function(){
						var subJson={};
						subJson['id']=$jq(this).find('td').eq(0).find('input:hidden').val();
						subJson['rmaDailyDate']=$jq(this).find('td').eq(0).find('input:text').val();
						subJson['rmaDailyFromPlace']=$jq(this).find('td').eq(1).find('input').val();
						subJson['rmaDailyToPlace']=$jq(this).find('td').eq(2).find('input').val();
						subJson['rmaDailyTravelBy']=$jq(this).find('td').eq(3).find('input').val();
						subJson['rmaDailyDiatance']=$jq(this).find('td').eq(4).find('input').val();
						subJson['rmaDailyDiatanceAftRes']=$jq(this).find('td').eq(5).find('input').val();
						subJson['rmaDailyAmountPerKm']=$jq(this).find('td').eq(6).find('input').val();
						subJson['rmaDailyAmountPerKmAftRes']=$jq(this).find('td').eq(7).find('input').val();
						subJson['rmaDailyClaimedAmount']=$jq(this).find('td').eq(8).find('input').val();
						subJson['rmaDailyClaimedAmountAftRes']=$jq(this).find('td').eq(9).find('input').val();
						if($jq(this).find('td').eq(0).find('input:text').val() != undefined){
						if($jq(this).is(':visible'))
							subJson['editType']='add';
						else
							subJson['editType']='delete';
						rmaDailyJson[i]=subJson;
						i++;
						}
					});
				}
				if($jq('#RMALocalDetailsId').is(':visible')){
					var i=0;
					$jq('#RMALocalDetailsId tr:not(:first)').each(function(){
						var subJson={};
						subJson['id']=$jq(this).find('td').eq(0).find('input:hidden').val();
						subJson['rmaLocalDate']=$jq(this).find('td').eq(0).find('input:text').val();
						subJson['rmaLocalFromPlace']=$jq(this).find('td').eq(1).find('input').val();
						subJson['rmaLocalToPlace']=$jq(this).find('td').eq(2).find('input').val();
						subJson['rmaLocalTravelBy']=$jq(this).find('td').eq(3).find('input').val();
						subJson['rmaLocalDiatance']=$jq(this).find('td').eq(4).find('input').val();
						subJson['rmaLocalDiatanceAftRes']=$jq(this).find('td').eq(5).find('input').val();
						subJson['rmaLocalAmountPerKm']=$jq(this).find('td').eq(6).find('input').val();
						subJson['rmaLocalAmountPerKmAftRes']=$jq(this).find('td').eq(7).find('input').val();
						subJson['rmaLocalClaimedAmount']=$jq(this).find('td').eq(8).find('input').val();
						subJson['rmaLocalClaimedAmountAftRes']=$jq(this).find('td').eq(9).find('input').val();
						if($jq(this).find('td').eq(0).find('input:text').val() != undefined){
						if($jq(this).is(':visible'))
							subJson['editType']='add';
						else
							subJson['editType']='delete';
						rmaLocalJson[i]=subJson;
						i++;
						}
					});
				}
				if($jq('#daNewFoodDayDetailsId').is(':visible')){
					var i=0;
					$jq('#daNewFoodDayDetailsId tr:not(:first,:last)').each(function(){
						var subJson={};
						subJson['id']=$jq(this).find('td').eq(0).find('input:hidden').val();
						subJson['repDate']=$jq(this).find('td').eq(0).find('input:text').val();
						subJson['repAmount']=$jq(this).find('td').eq(1).find('input').val();
						if($jq(this).find('td').eq(0).find('input:hidden').val() != undefined && $jq(this).find('td').eq(0).find('input:text').val() != undefined && $jq(this).find('td').eq(1).find('input').val() != undefined){
							foodDayJson[i]=subJson;
							i++;
						}
						
					});
				}
				if($jq('#daNewAccDayDetailsId').is(':visible')){
					var i=0;
					$jq('#daNewAccDayDetailsId tr:not(:first,:last)').each(function(){
						var subJson={};
						subJson['id']=$jq(this).find('td').eq(0).find('input:hidden').val();
						subJson['repDate']=$jq(this).find('td').eq(0).find('input:text').val();
						subJson['repAmount']=$jq(this).find('td').eq(1).find('input').val();
						accDayJson[i]=subJson;
						i++;
					});
				}
			}
		}
		
		
		var unUtliliseBal=0;         // This is new line is add for modification.
		
		if($jq('#penalInterestTabId').is(':visible')){
		var j=0;
		$jq('#penalInterestTabId tr:not(:first)').each(function(){
			var subJson={};
			if($jq(this).find('td').eq(0).find('input:radio').is(':visible')){
				subJson['penalInterestReq']=$jq(this).find('td').eq(0).find('input:radio[name=penalInterestReq'+j+']:checked').val();
				subJson['id']="";
			}else{
				subJson['penalInterestReq']="yes";
				if($jq(this).find('td').eq(0).find('input:hidden').val()!='' && $jq(this).find('td').eq(0).find('input:hidden').val()!=undefined){
					subJson['id']=$jq(this).find('td').eq(0).find('input:hidden').val();
				}else{
					subJson['id']="";
				}
			}
			if(! $jq(this).find('td').eq(1).find('input').is(':visible')){
				subJson['noOfDays']=$jq(this).find('td').eq(1).text();
				
			}else if($jq(this).find('td').eq(1).find('input').is(':visible') && $jq.trim($jq(this).find('td').eq(1).find('input').val()) !=''){
				subJson['noOfDays']=$jq(this).find('td').eq(1).find('input').val();
			}
			if(!$jq(this).find('td').eq(2).find('input').is(':visible')  ){
				subJson['interestRate']=$jq(this).find('td').eq(2).text();
			}else if($jq(this).find('td').eq(1).find('input').is(':visible') && $jq.trim($jq(this).find('td').eq(2).find('input').val()) !=''){
				subJson['interestRate']=$jq(this).find('td').eq(1).find('input').val();
			}
			
			if( $jq(this).find('td').eq(3).find('input').is(':visible')){     // This line newly added for MroDetails
				subJson['unUtliliseBal']= $jq(this).find('td').eq(3).find('input:text').val();
			}else{
				subJson['unUtliliseBal']=parseFloat($jq(this).find('td').eq(3).text());
			}
			
			if($jq(this).find('td').eq(4).find('input').is(':visible') && $jq.trim($jq(this).find('td').eq(4).find('input').val()) !='' ){
				subJson['penalInterest']=$jq(this).find('td').eq(4).find('input').val();
			}else{
				subJson['penalInterest']=parseFloat($jq(this).find('td').eq(4).text());
			}
			if($jq(this).find('td').eq(1).find('input').val() != undefined && $jq(this).find('td').eq(2).find('input').val() != undefined && $jq(this).find('td').eq(4).find('input').val() != undefined){
				mroDetailsJson[j]=subJson;
				j++;
			}else if( $jq(this).find('td').eq(1).text() !='' && $jq(this).find('td').eq(2).text() !='' && $jq(this).find('td').eq(4).text() !=''){
				mroDetailsJson[j]=subJson;
				j++;
			}
			
			
		});
		}
		var k=0;
	
		$jq('#penalInterestTabId tr:not(:first)').each(function(){
			if($jq(this).find('td').eq(6).find('input').is(':visible') && $jq(this).find('td').eq(7).find('input').is(':visible')){
				if($jq.trim($jq(this).find('td').eq(6).find('input').val())!='' && $jq.trim($jq(this).find('td').eq(7).find('input').val()) !=""){
				var subJson={};
        		subJson['mroId']=$jq(this).find('td').eq(0).find('input').val();
				if($jq(this).find('td').eq(6).find('input').is(':visible') && $jq(this).find('td').eq(6).find('input').val()!=''){
					subJson['mroNo']=$jq(this).find('td').eq(6).find('input').val();
				
				}
				if($jq(this).find('td').eq(7).find('input').is(':visible') && $jq(this).find('td').eq(7).find('input').val()!=''){
					subJson['mroDate']=$jq(this).find('td').eq(7).find('input').val();
					
				}
				if($jq(this).find('td').eq(6).find('input').val() != undefined && $jq(this).find('td').eq(7).find('input').val() != undefined ){
					mroPaymentDetailsJson[k]=subJson;
					k++;
				}
				
			}}
		});
		var k=0;
		if($jq('#daOldDetailsId').is(':visible')){
			$jq('#daOldDetailsId tr:not(:first)').each(function(){
				var subJson={};
				subJson['id']=$jq(this).find('td').eq(0).find('input:hidden').val();
				subJson['jdaDate']=$jq(this).find('td').eq(0).find('input:text').val();
				subJson['jdadays']=$jq(this).find('td').eq(1).find('input').val();
				subJson['jdaAmount']=$jq(this).find('td').eq(2).find('input').val();
				subJson['jdaAmountTot']=$jq(this).find('td').eq(3).find('input').val();
				subJson['sdadays']=$jq(this).find('td').eq(4).find('input').val();
				subJson['sdaAmount']=$jq(this).find('td').eq(5).find('input').val();
				subJson['sdaAmountTot']=$jq(this).find('td').eq(6).find('input').val();
				if($jq(this).find('td').eq(0).find('input:hidden').val() != undefined && $jq(this).find('td').eq(1).find('input').val() != undefined && $jq(this).find('td').eq(2).find('input').val() != undefined && $jq(this).find('td').eq(3).find('input').val() != undefined &&  $jq(this).find('td').eq(4).find('input').val() != undefined &&  $jq(this).find('td').eq(5).find('input').val() != undefined && $jq(this).find('td').eq(6).find('input').val() != undefined){
					daOldValuesJson[k]=subJson;
					k++;
				}
				
			});
		}
		mainJson['mroDetails']=mroDetailsJson;
		mainJson['mroPaymentDetails']=mroPaymentDetailsJson;
		mainJson['oldDaDetails']=daOldValuesJson;
		mainJson['journeyDetails']=journeyJson;
		mainJson['accDetails']=accJson;
		mainJson['foodDetails']=foodJson;
		mainJson['rmaKmDetails']=rmaKmJson;
		mainJson['rmaDayDetails']=rmaDayJson;
		mainJson['rmaDailyDetails']=rmaDailyJson;
		mainJson['rmaLocalDetails']=rmaLocalJson;
		mainJson['foodDayDetails']=foodDayJson;
		mainJson['accDayDetails']=accDayJson;
		document.forms[0].jsonValue.value = JSON.stringify(mainJson);
		
	requestDetails  ={
				"jsonValue" : JSON.stringify(mainJson),
				"totalTadaTdCalAmount": $jq('#totalTadaTdCalAmount').val(),
				"penalInterestId" : $jq('#penalID0').val()
				} ;
		
	 //url += '&jsonValue='+JSON.stringify(mainJson)+'&totalTadaTdCalAmount='+$jq('#totalTadaTdCalAmount').val()+'&penalInterestId='+$jq('#penalID0').val();
		
		if($jq('#penalInterestTabId').is(':visible')){
			if(parseFloat(document.getElementById("penalInterestTabId").rows.length)==2 || parseFloat(document.getElementById("penalInterestTabId").rows.length)==1){
				url += '&excessAmount='+$jq('#excessAmount').val();
			}else if(parseFloat(document.getElementById("penalInterestTabId").rows.length)>2){
//				url += '&excessAmount=0';
				url += '&excessAmount='+unUtliliseBal*(-1);
			}
		}
		
		if($jq('#noOfDays').val()=='' && $jq('#noOfDays').is(':visible')){
			errorMessages += "Please Enter No. Of Days.\n";
			$jq('#noOfDays').focus();
			status=false;
		}
		/*if($jq('#MROPaidNo').val()=='' && $jq('#MROPaidNo').is(':visible')){
			errorMessages += "Please Enter MRO Paid No..\n";
			$jq('#MROPaidNo').focus();
			status=false;
		}
		if($jq('#MROPaidDate').val()=='' && $jq('#MROPaidDate').is(':visible')){
			errorMessages += "Please Select MRO Paid Date..\n";
			$jq('#MROPaidDate').focus();
			status=false;
		}*/
	}
	if ($jq('#remarks').val() == '') {
		errorMessages += "Please enter remarks\n";
		if (status) {
			status = false;
			$jq('#remarks').focus();
		}
	}	
	if(status){
		var check=confirm("Are You Sure Want To Hold This Request ?");
		if(check==false){
			status=false;
			return;
		}
	}
	if (status) {
			
		$jq.ajax( {
			type : "POST",
			url : url,
			data : requestDetails,
			cache : false,
			success : function(html) {
				
				if(requestTypeID=='48' && $jq('#penalInterestTabId').is(':visible')){
					$jq('#tadaApproved').css("display","none");
					$jq('#tadaDecline').css("display","none");
					$jq('#tadaHold').css("display","none");
					
					
				}
				$jq("#result").html(html);	
			}
		});
		
		/*$jq.post('historyDetails', url, {
			onComplete : function() {
			
			if(requestTypeID=='48' && $jq('#penalInterestTabId').is(':visible')){
				$jq('#tadaApproved').css("display","none");
				$jq('#tadaDecline').css("display","none");
				$jq('#tadaHold').css("display","none");
				
				
			}
			},
			asynchronous : false,
			evalScripts : true
		});*/
	} else {
		alert(errorMessages);
	}
}

function setDetails(result) {
	if (result == 'success') {
		if (reqTypeID == selfTransferRequestID
				|| reqTypeID == empTransferRequestID) {
			if ($jq('#doPartNo').is(':visible') && $jq('#doPartNo').val() != '') {
				$jq('#ionNoDiv').text($jq('#doPartNo').val());
				$jq('#ionDateDiv').text($jq('#doPartDate').val());
			}
			if ($jq('#hqRefNo').is(':visible') && $jq('#hqRefNo').val() != '') {
				$jq('#hqrNoDiv').text($jq('#hqRefNo').val());
				$jq('#hqrDateDiv').text($jq('#receivedDate').val());
			}
		} else if (reqTypeID == loanRequestID) {
			if ($jq('#sanctionedAmount').is(':visible')) {

				var dojDRDO = $jq('#dobDRDO').text();
				var retirement = $jq('#retirement').text();
				var requestDetails = {
					"requestId" : $jq('#requestID').text(),
					"param" : "reqSpeDet"
				}
				$jq.ajax( {
					type : "POST",
					url : "workflowmap.htm",
					data : requestDetails,
					cache : false,
					success : function(html) {
						$jq("#loanDetails").html(html);
						$jq('#dobDRDO').text(dojDRDO);
						$jq('#retirement').text(retirement);
					}
				});
			}
		} else if (reqTypeID == newQuarterRequestID
				|| reqTypeID == changeQuarterRequestID) {
			if ($jq('#quarterType').is(':visible')
					&& $jq('#quarterType').val() != '0') {
				$jq('#quarterTypeDiv').text(
						$jq('#quarterType option:selected').text());
				$jq('#allotedDateDiv').text($jq('#allotedDate').val());
				$jq('#occupiedDateDiv').text($jq('#occupiedDate').val());
				$jq('#quarterNoDiv').text($jq('#quarterNo').val());
			}
		}
	}
}
//Create Workflow
function getWorkflowStages(type){
	if(type=='page'){
		$jq('#result').text('');		
	}
	if($jq('#workflowName').val()=='select'){
		$jq('#stages').text('');
		$jq('#flowname').val('');
		$jq('#flownameDiv').hide();
	} else if($jq('#workflowName').val()=='new'){
		$jq('#flownameDiv').show();
		$jq('#flowname').val('');
		$jq('#stages').text('');
		var requestDetails = {
			"param" : "stages",
			"type" : "new"
		};
		$jq.post('createworkflow.htm', requestDetails, function(html) {
			$jq("#stages").html(html);
		});
	} else {
		//Edit
		$jq('#flowname').val('');
		$jq('#flownameDiv').hide();
		$jq('#stages').text('');
		var requestDetails = {
			"param" : "stages",
			"nodeID" : $jq('#workflowName').val(),
			"type" : "old"
		};
		$jq.post('createworkflow.htm', requestDetails, function(html) {
			$jq("#stages").html(html);
			setWorkflowStages();
		});
	}
}

function getAssignedList(rowID,from,to){
	var from = from+rowID;
	var to = to+rowID;
	
	$jq('#'+to).find('option').remove().end().append($jq("<option></option>").attr("value",'0').text('Select'));
	if($jq('#'+from).val()=='1'){
		//Relative
		if (relationsListJSON != null) {
			assignList(relationsListJSON,to);
		}		
	} else if($jq('#'+from).val()=='2'){
		//Absolute
		if (rolesListJSON != null) {
			assignList(rolesListJSON,to);
		}
	} else if($jq('#'+from).val()=='3'){
		//Dependent
		if (dependentListJSON != null) {
			assignList(dependentListJSON,to);
		}
	}else if($jq('#'+from).val()=='4'){
		//Dynamic
		if (dynamicTableListJSON != null) {
			assignList(dynamicTableListJSON,to);
		}
	}	
}

function assignList(jsonObject,id) {
	if (jsonObject != null) {
		for ( var i = 0; i < jsonObject.length; i++) {			
			$jq("#"+id).append(
					'<option value=' + jsonObject[i].id + '>'
							+ jsonObject[i].name + '</option>');		
			
		}
	}	
}
//Create New Row
function insertNewRow(rowIndex,id){
	if(id=='dynWorkFlow'){
		createWorkflowRow(rowIndex);
	}
}
var flagVal=true;
function deleteRow(rowIndex,id){
	if($jq('#'+id+' tr').length<=2){
		alert("Sorry you cannot delete the last row");
		flagVal=false;
	} else if(rowIndex+1!=parseFloat($jq('#'+id+' tr').length)-1){
		alert("Sorry you cannot delete this row");
		flagVal=false;
	}else {
		var del = confirm("Do you want to delete this item?");
		if (del == true) {
			$jq('#'+id+' tbody > tr:nth-child('+  (levelID + 1) + ')').remove();
			if(id=='dynWorkFlow'){
				setWorkflowFrom();
			}
			levelID = levelID-1;
			count--;
			flagVal=true
			
			$jq('#dynWorkFlow').find('tr')[rowIndex].lastElementChild.previousElementSibling.firstChild.disabled=false;
			//$jq('#dynWorkFlow').find('tr')[rowIndex-1].lastElementChild.previousElementSibling.firstChild.attributes[4].value=false;
		}
	}	
}
function loadScript(tableID) {
	$jq("#"+tableID+" tr").live('click', function() {
		if(levelID=='' || levelID==undefined)
			levelID = $jq(this)[0].rowIndex;
		else{
			if(flagVal==true)
				levelID = levelID+1;
		}
			
	});
}

function createWorkflowRow(rowIndex){
	count++;
	var relationsList = "";
	if (relationshipListJSON != null) {
		for ( var i = 0; i < relationshipListJSON.length; i++) {
			relationsList += '<option value=' + relationshipListJSON[i].id
					+ '>' + relationshipListJSON[i].name + '</option>';
		}
	}	
	var stagesList = "";
	if (workflowStageListJSON != null) {
		for ( var i = 0; i < workflowStageListJSON.length; i++) {
			stagesList += '<option value=' + workflowStageListJSON[i].id
					+ '>' + workflowStageListJSON[i].name + '</option>';
		}
	}	
	
	var row = "<tr id=row"+count+">";
		row += "<td>";
			row += "<select name=from id=workflowfrom"+count+" class=formClass disabled=true>";
				row += "<option value=0>Select</option>";					
			row += "</select>";	
		row += "</td>";
		
		row += "<td>";
			row += "<select name=relation id=workflowrelation"+count+" class=formClass onchange=getAssignedList("+count+",\'workflowrelation\',\'workflowto\')>";
				row += "<option value=0>Select</option>";
				row += relationsList;				
			row += "</select>";	
		row += "</td>";
		
		row += "<td>";
			row += "<select name=to id=workflowto"+count+" class=formClass onchange=setWorkflowFrom()>";
				row += "<option value=0>Select</option>";		
			row += "</select>";	
		row += "</td>";
	
		row += "<td>";
			row += "<select name=esclateRelation id=escalaterelation"+count+" class=formClass onchange=getAssignedList("+count+",\'escalaterelation\',\'escalteTo\')>";
				row += "<option value=0>Select</option>";
				row += relationsList;
			row += "</select>";	
		row += "</td>";
		
		row += "<td>";
			row += "<select name=escalteTo id=escalteTo"+count+" class=formClass>";
				row += "<option value=0>Select</option>";		
			row += "</select>";	
		row += "</td>";
	
		row += "<td>";
			row += "<select name=workflowStage id=workflowStage"+count+" class=formClass>";
				row += "<option value=0>Select</option>";	
				row += stagesList;
			row += "</select>";	
		row += "</td>";
		
		row += "<td>";
			row += "<input type=button class=smallbtn value=+ onclick=insertNewRow("+count+",\'dynWorkFlow\');setEditWorkflowFrom(); />";			
		row += "</td>";
		
		row += "<td>";
			row += "<input type=button id=delete class=smallbtn value=- onclick=deleteRow("+count+",\'dynWorkFlow\'); />";			
		row += "</td>";
	row += "</tr>";
	
	$jq("#row"+rowIndex).after(row);
	setWorkflowFrom();
	if(rowIndex > 0){
	$jq('#dynWorkFlow').find('tr')[rowIndex+1].lastElementChild.previousSibling.firstChild.disabled=true;
	$jq('#dynWorkFlow').find('tr')[rowIndex+1].lastElementChild.previousSibling.firstChild.attributes[4].value=true;
	}else{
		$jq('#dynWorkFlow').find('tr')[1].lastElementChild.previousElementSibling.firstElementChild.disabled=true;
		$jq('#dynWorkFlow').find('tr')[1].lastElementChild.previousElementSibling.firstElementChild.attributes[4].value=true;
	}
	}

function setWorkflowFrom() {
	if($jq('#dynWorkFlow tbody > tr:nth-child('+ (levelID + 1) +') td:nth-child(3) ').find('select')
			.find('option:selected').text()!=''){
		$jq('#dynWorkFlow tbody > tr:nth-child(' + (levelID + 2) + ') td:nth-child(1) ')
			.find('select').find('option').remove().end().append(
				$jq("<option></option>").attr("value", $jq('#dynWorkFlow tbody > tr:nth-child('+ (levelID + 1) +') td:nth-child(3) ').find('select')
				.find('option:selected').val()).text($jq('#dynWorkFlow tbody > tr:nth-child('+ (levelID + 1) +') td:nth-child(3) ').find('select')
				.find('option:selected').text()));
	}
}
function setEditWorkflowFrom(){
	for(var i=1;i<=levelID+1;i++){
		if(i+1!=2){
			if($jq('#dynWorkFlow tbody > tr:nth-child('+ (i + 1) +') td:nth-child(1) ').find('select')
					.find('option:selected').text()=='Select'){
				$jq('#dynWorkFlow tbody > tr:nth-child(' + (i +1) + ') td:nth-child(1) ')
				.find('select').find('option').remove().end().append(
					$jq("<option></option>").attr("value", $jq('#dynWorkFlow tbody > tr:nth-child('+ (i) +') td:nth-child(3) ').find('select')
					.find('option:selected').val()).text($jq('#dynWorkFlow tbody > tr:nth-child('+ (i) +') td:nth-child(3) ').find('select')
					.find('option:selected').text()));
			}
		}
	}
}

function clearWorkFlowStages(){
	$jq("#workflowName").val('select');
	$jq("#toworkflow").val('0');
	removeStages('dynWorkFlow');
	$jq("#dynWorkFlow tr:not(:first)").each(function() {	
		$jq(this).find("td").eq(0).find('select').val('0');
		$jq(this).find("td").eq(1).find('select').val('0');
		$jq(this).find("td").eq(2).find('select').find('option').remove().end().append($jq("<option></option>").attr("value",'0').text('Select'));
		$jq(this).find("td").eq(3).find('select').val('0');
		$jq(this).find("td").eq(4).find('select').find('option').remove().end().append($jq("<option></option>").attr("value",'0').text('Select'));
		$jq(this).find("td").eq(5).find('select').val('0');
	});
}

function removeStages(id) {
	$jq("#" + id).find("tr:gt(1)").remove();
}
function setWorkflowStages() {
	count = 0;
	levelID = 1;
	if (workflowStageDetailsJSON != null) {
		for ( var i = 0; i < workflowStageDetailsJSON.length; i++) {
			$jq('#workflowfrom' + i).val(workflowStageDetailsJSON[i].from);
			$jq('#workflowrelation' + i).val(workflowStageDetailsJSON[i].relation);
			getAssignedList(i, 'workflowrelation', 'workflowto');

			$jq('#workflowto' + i).val(workflowStageDetailsJSON[i].to);
			$jq('#escalaterelation' + i).val(workflowStageDetailsJSON[i].esclateRelation);
			getAssignedList(i, 'escalaterelation', 'escalteTo');
			$jq('#escalteTo' + i).val(workflowStageDetailsJSON[i].escalteTo);
			$jq('#workflowStage' + i).val(workflowStageDetailsJSON[i].workflowStage);

			if (i < workflowStageDetailsJSON.length - 1) {
				createWorkflowRow(i);
				levelID++;
			}
		}
	}
}

function manageWorkflowStages() {
	var errorMessage = "";
	var mainJSON = {};
	var i = 0;
	
	$jq("#dynWorkFlow tr:not(:first)").each(function() {		
		var jsonWorkflowRow={};
		jsonWorkflowRow['from']=$jq(this).find("td").eq(0).find('select').val();
		jsonWorkflowRow['relation']=$jq(this).find("td").eq(1).find('select').val();
		jsonWorkflowRow['to']=$jq(this).find("td").eq(2).find('select').val();
		jsonWorkflowRow['erelation']=$jq(this).find("td").eq(3).find('select').val();
		jsonWorkflowRow['eto']=$jq(this).find("td").eq(4).find('select').val();
		jsonWorkflowRow['stage']=$jq(this).find("td").eq(5).find('select').val();			
		mainJSON[i] = 	jsonWorkflowRow;	
		i++;
	});
	
	errorMessage = validateWorkflowStages(mainJSON);
	if (errorMessage == '') {
		var requestDetails = {
			"param" : "manage",
			"nodeID" : $jq('#workflowName').val(),
			"flowname" : $jq('#flowname').val(),
			"toworkflow" : $jq('#toworkflow').val(),
			"stages" : JSON.stringify(mainJSON)
		};
		$jq.post('createworkflow.htm', requestDetails, function(html) {
			$jq("#result").html(html);
			if ($jq('.success').is(':visible')) {
				if ($jq('#workflowName').val() == 'new') {
					$jq("#workflowName").find('option').end().append(
							$jq("<option></option>").attr("value", nodeID)
									.text($jq('#flowname').val()));
				}
				clearWorkFlowStages();
				getWorkflowStages('manage');
			}
		});
	} else {
		alert(errorMessage);
	}
}

function validateWorkflowStages(mainJSON) {
	var errorMessages = "";
	$jq.each(mainJSON, function(index, value) {
		var line = parseInt(index) + 1;
		if (value.relation == '0') {
			errorMessages += "Please select relation at Line :" + line + "\n";
		}
		if (value.to == '0') {
			errorMessages += "Please select to at Line :" + line + "\n";
		}
		if (value.erelation == '0') {
			errorMessages += "Please select escalation relation at Line :"
					+ line + "\n";
		}
		if (value.eto == '0') {
			errorMessages += "Please select escalation to at Line :" + line
					+ "\n";
		}
		if (value.stage == '0') {
			errorMessages += "Please select stage at Line :" + line + "\n";
		}
	});
	return errorMessages;
}

function deleteWorkFlow() {
	var requestDetails = {
		"param" : "delete",
		"nodeID" : $jq('#workflowName').val()
	};
	$jq.post('createworkflow.htm', requestDetails, function(html) {
		$jq("#result").html(html);
		if ($jq('.success').is(':visible')) {
			$jq('#workflowName option:selected').remove();

			clearWorkFlowStages();
			getWorkflowStages('manage');
		}
	});
}
function getInitialReport(requestId,type) {
	window.open('./report.htm?param=initial&type='+type+'&requestID='+requestId,'LTC','fullscreen=no,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no')
}
function getInitialDocReport(requestId,requestType) {
	window.open('./report.htm?param=initialDocs&type='+requestType+'&requestID='+requestId,'LTC','fullscreen=no,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no')
}
function getInitialPdfReport(requestId,requestType) {
	window.open('./report.htm?param=initialDocspdf&type='+requestType+'&requestID='+requestId,'LTC','fullscreen=no,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no')
}
function individualDetails() {
	if(!$jq('#approveDiv').is(':visible')) {
		$jq('#individualNote').attr('style','display:block')
	}
}
function hQApplication(id){
	window.open('./report.htm?param=hQReport&requestID='+id,'HigherQualification','fullscreen=no,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no')
}
function quarterApplication(id){
	window.open('./report.htm?param=quarterReport&requestID='+id,'Quarter','fullscreen=no,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no')
}
function emuApplication(id){
	window.open('./report.htm?param=caQuarterReport&requestID='+id,'Quarter','fullscreen=no,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no')
}
function emuChangeApplication(id){
	window.open('./report.htm?param=ChangeQuarterReport&requestID='+id,'Quarter','fullscreen=no,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no')
}
function quarterION(id){
	window.open('./report.htm?param=IONQuarterReport&requestID='+id,'Quarter','fullscreen=no,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no')
}
function saveInstallmentDetails(historyID, requestID){
	errorMessages="";
	var status = true;
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
		$jq.post('workflowmap.htm', requestDetails, function(html){
			$jq('#reqDiv').hide();
			$jq('#InsDiv').hide();
			$jq('#saveButton').hide();
			$jq('#resultLoan').html(html);
			if($jq('#resultLoan div').find('.success').is(':visible')){
				$jq('#reqDiv').parent().find('span').eq(0).text(parseFloat($jq('#reqAmount').val()));
				$jq('#InsDiv').parent().find('span').eq(0).text($jq('#requestedInstalments').val()).append('+').append($jq('#requestedInterestInstalments').val());
			}
		});
		}
	else{
		alert(errorMessages);
	}
}
//loan
function editInstallmentDetails (){
	$jq('#reqDiv').show();
	$jq('#InsDiv').show();
	$jq('#saveButton').show();
	$jq('#reqAmount').val('');
	$jq('#requestedInstalments').val('');
	$jq('#requestedInterestInstalments').val('');
}

function sendHQRequest(historyID, requestID, requestTypeID) {
	var errorMessages = "";
	var status = true;
	reqTypeID = requestTypeID;//Set to global
	
	var url = 'workflow.htm?param=sendToHQ&historyID=' + historyID
			+ '&requestTypeID=' + requestTypeID + '&requestID=' + requestID+'&remarks='+$jq("#remarks").val();
	
	if ($jq('#remarks').val() == '') {
		errorMessages += "Please enter remarks\n";
		if (status) {
			status = false;
			$jq('#remarks').focus();
		}
	}	
	if (status) {
		new Ajax.Updater('historyDetails', url, {
			onComplete :function() {
				blockButtons();
			},
			asynchronous : true,
			evalScripts : true
		});
	} else {
		alert(errorMessages);
	}
}

function calculatePenalFine(cdaAmount){
	if($jq('#noOfDays').val()!='' && $jq('#interestRate').val()!=''){
		$jq('#excessAmountFine').val(Math.round(Math.abs(($jq('#excessAmount').val()*Math.round($jq('#noOfDays').val()))/365)*(parseFloat($jq('input:text[name=interestRate]').val())/100)));
		calculateAmountAfterRestriction();
	}
}
//cghs uploads
function manageUploadedFiles(fileId1,fileId2,fileId3,fileId4){
	var status = true;		
	var errorMessage = "";
	var Message = "";
	var count = 0;
	var flag = 0;
	var fileId  = [fileId1,fileId2,fileId3,fileId4];                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      
	var uploadtype = null;
	var file1 = $jq('#files1').attr("name");
	var file3 = $jq('#files3').attr("name");
	var file2 = $jq('#files2').attr("name");
	 reqId = $jq('#requestID').html();
	 length1 = $jq('#NoFiles').find('input:visible').length;
	   
	 for(var i=0;i<length1;i++){
	 if($jq('#NoFiles').find('input').eq(i).val() ==''){
		  flag++;
     }
	 }
	 if(flag == length1){
		 errorMessage += "Please Select Any File \n"; 
		 status = false;
	 }
	 else if(!($jq('#NoFiles').find('a').eq(0).attr('href').split("(")[1].split(")")[0] =='')||($jq('#NoFiles').find('a').eq(1).attr('href').split("(")[1].split(")")[0]=='')){
		 Message += "Are You Sure  \n The Existing Files Will Be Overwrited"; 	                                                                   	
         alert(Message);
        
	 }  
	 
	 for(var k=1;k<=length1;k++) {
		   if($jq('#files'+k).val()!='' && $jq('#files'+k).is(':visible')){
			if(!checkFileExtention($jq('#files'+k).val().split('.')[$jq('#files'+k).val().split('.').length-1])) {
				errorMessage+='Upload only .docx,.doc,.txt,.pdf\n';
			   status = false;
			}}}  
	 if(length1<=2){
		 if(file1 == 'prescriptionFile'){
		 uploadtype = "normal";
		 }else if(file2 !='medicalBillsFile'){
			 uploadtype = "advance";
		 }else{
			 uploadtype = "reimbursement";
		 }
	 }else {
		 if(file3 == 'dischargeSummeryFile'){
			 uploadtype = "emergency"; 
		  }	 
		 else{
			 uploadtype = "reimbursement";
	    }
    }
	 if( status){
	   for(var k=1;k<=length1;k++) 
		  {	   
		   
		   if($jq('#files'+k).val()!='' && $jq('#files'+k).is(':visible')){   
                  var filename = $jq('#files'+k).attr("name");
                  var file = $jq('#files'+k).val();
                  $jq('#param').val('');
                  $jq('#type').val('') ;
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  
                  var pc =  fileId[k-1];
                  if(pc == undefined){
                   pc = ""; 
                  }
                  
                  if(uploadtype == "normal"){
                	 if(filename == "prescriptionFile"){
     		        	 $jq('#prescriptionFileName').val(pc); 
     		             $jq('#prescriptionFile').val(file);
                	  }
                  }else if(uploadtype == "reimbursement"){
                  if(filename =="cghsCardFile"){ 
                	  $jq('#cghsCardFile').val(file);
                      $jq('#cghsCardFileName').val(pc);
                     
		         }else if(filename == "prescriptionFile"){
		        	 $jq('#prescriptionFileName').val(pc); 
		             $jq('#prescriptionFile').val(file);
		            
		         }else if(filename == "medicalBillsFile"){
		        	 $jq('#medicalBillsFileName').val(pc);
		        	 $jq('#medicalBillsFile').val(file);
		        	
		         }else if(filename == "refundChequeFile"){
		        	 $jq('#refundChequeFileName').val(pc);
		        	 $jq('#refundChequeFile').val(file);
		        	
		         }
            }else if(uploadtype == "advance"){
                	if(filename =="cghsCardFile"){ 
                  	  $jq('#cghsCardFile').val(file);
                      $jq('#cghsCardFileName').val(pc);
                	}else if(filename =="estimationFile"){
                		$jq('#estimationFile').val(file);
                        $jq('#estimationFileName').val(pc);	
                    }
                	}else if(uploadtype == "emergency"){
                		if(filename =="cghsCardFile"){ 
                         $jq('#cghsCardFile').val(file);
                         $jq('#cghsCardFileName').val(pc);
                		}else if(filename =="medicalBillsFile"){
                		 $jq('#medicalBillsFileName').val(pc);
       		        	 $jq('#medicalBillsFile').val(file);
                		}else if(filename =="dischargeSummeryFile"){
                		 $jq('#dischargeSummeryFileName').val(pc);
                		 $jq('#dischargeSummeryFile').val(file);
                		}
                	}   
		   }
		   
		   }
	              $jq('#param').val('uploadToDatabase');
                  $jq('#type').val('cghs');
                  requestID = $jq('#requestID').html();
                  //$jq('#requestType').val()= uploadtype;
			   $jq.ajaxFileUpload(
	     				{
	     				url:'cghsRequest.htm?requestType='+uploadtype+'&' +$jq('#workflowmap').serialize(),
	     				secureuri:false,
	     				fileElementId :'NoFiles',
	     				async: false,
	     				success: function (data, status) {
	     				$jq("#result1").html($jq(data.body).html());
	     				getRequestIdDetails(requestID);
	     				alert('success');
	     				 count++; 
	     				}, error: function (data, status, e) {
	     				// when failure
	     				//alert('File Upload failed');
	     				status=false; }
	     				}); 		
			   clearUploads(); 
	        }
	 else{
		   alert(errorMessage);
		}
	     }

	   /*if(status) {
		   $jq('#param').val('manageUpload');
			$jq('#type').val('cghs');
		    $jq.post('workflowmap.htm?requestId='+reqId+'&requestType='+uploadtype ,$jq('#workflowmap').serialize(), function(html) {
		    $jq("#result").html(html);
		    });
		    
		    if(count !=0 ){
				errorMessage += ""+count+"Files uploaded successfully \n Please Refresh To View The Files";
			}  
			
		}*/
//	  else{
//		   //clearUploads();	
//		  //alert(errorMessage);	
//		}
//	   
	 



	function checkFileExtention(type) {
		var status = false;
		var allowType = "docx,doc,txt,pdf";
		for(var i=0;i<allowType.split(',').length;i++) {
			if(allowType.split(',')[i].toUpperCase()==type.toUpperCase()) {
				status = true;
				return status;
			}
		}
		return status;
	}

	function clearUploads(){
		$jq('#files1').val('');	
		$jq('#files2').val('');
		$jq('#files3').val('');
	}	
		
function isSpecialCharacter(e){
	var key;
	var objRegExp  = /^[^~!@$%^*+`=|\"';?><]*$/
	if(window.event){
		key = window.event.keyCode;
	}
	else{
		key = e.which;
	}
  	if( objRegExp.test(String.fromCharCode(key)) || key==8 || key==0 ){
  		
		return true;
	}
  	else{
		alert("place should not contain ~!@$%^*+`=|\"';?>< ");
		return false;
	}
	
	
	
}
function showProjectList() {
	$jq('#projectName').attr('disabled',false);	
}

function viewaddressReports(id,sfid,addressTypeId){
	window.open('./report.htm?param=viewaddressReports&requestID='+id+'&requestersfid='+sfid+'&addressTypeId='+addressTypeId,'fullscreen=no,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no')
}
function viewaddressReports1(){
	window.open('./report.htm?param=viewaddressReports1','fullscreen=no,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no')
}
//
