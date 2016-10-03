var amendmentLeaves;
var amendmentLeaveSelected;
var row=1;
var paymentDetails;


////////////////new added by bkr ///////////

function annualLeaveReport(){
	var year=$jq('#ltcYear').val();
	if(year!=0){
		var ltcYear=$jq('#ltcYear').val();
		window.open('reports.htm?status=4&param=AnnualLeave&ltcYear='+ltcYear);
		Location.load();
	}else{
		alert("Please Select Annual Year");
	}
}


function checkYearValidOrnot(){
	
	var applyYear=$jq('#ltcYear').val();
	if(applyYear!=='0'){
		var today = new Date();
		var yyyy = today.getFullYear();
		if(parseInt(applyYear)>parseInt(yyyy)){
		
			$jq('#ltcYear').val('Select');
			alert("This is not current Annual Year Please try to apply current Annual Year ");
		}
	}
}


function checkApplying(){
	
	var noOfChildrenTickets=0;
	var noOfAdultsTickets=0;
	var status = true;
	for(var i=0;i<LtcFamilyMemberList.length;i++) {
		if($jq('#'+LtcFamilyMemberList[i].id).attr('checked')) {
			//if(parseInt(LtcFamilyMemberList[i].age) <= 12) {
			if(parseInt(LtcFamilyMemberList[i].age) < 18) {	
				noOfChildrenTickets = parseInt(noOfChildrenTickets)+1;
			}else if(parseInt(LtcFamilyMemberList[i].age) >= 18) {
				noOfAdultsTickets = parseInt(noOfAdultsTickets)+1;
			}
		}
	}
	$jq('#noOfChildrenTickets').val(noOfChildrenTickets);
	$jq('#noOfAdultsTickets').val(noOfAdultsTickets);
	var totalTickets=parseInt(noOfAdultsTickets)+parseInt(noOfChildrenTickets);
	$jq('#totalTickets').val(totalTickets);
	
}

function checkApplingLtcYear(){
	var yearId="";
	var status =true;
	if(status) {
		var requestDetails = {
				"ltcYear" :$jq('#ltcYear').val(),
				"param" :"ltcYearChecking"
			};
			$jq.post('ltcWaterRequest.htm', requestDetails, function(html) {
			$jq("#type").html(html);
			alert("Your Are already Applied This Annual Year,Please Select Other Annual Year");
			$jq('#ltcYear').val('Select');
				
			});
	}
	
}
function abcxyz(){
	console.log("hello");
}
function clearltcAdminWaterSettlement(){
	$jq('#settlementAdminDate').val('');
	$jq('#settlementAdminRemarks').val('');
	
}
function ltcAdminWaterReimbursement(){
	
	var errorMessage = "";
	var status = true;
	var ltcreimcashorcheck= null;
	
	if($jq('input:radio[id=ltcreimcashorcheck1]').is(':checked')){
	ltcreimcashorcheck = $jq('#ltcreimcashorcheck1').val();
	} 
	if($jq('input:radio[id=ltcreimcashorcheck2]').is(':checked')){
		ltcreimcashorcheck=$jq('#ltcreimcashorcheck2').val();
	}

/*	if ($jq('#reimDvno').val()=='') {
		errorMessage += "Please Enter dvno.\n";
		if(status){
		$jq('#reimDvno').focus();
		}
		status = false;
	}
	if ($jq('#reimDvDate').val()=='') {
		errorMessage += "Please Enter dvDate.\n";
		if(status){
		$jq('#reimDvDate').focus();
		}
		status = false;
	}*/
	if ($jq('#reimbursementAdminDate').val()=='') {
		errorMessage += "Please Enter ReimbursementDate.\n";
		if(status){
		$jq('#reimbursementAdminDate').focus();
		}
		status = false;
	}
	
	if(ltcreimcashorcheck=='' || ltcreimcashorcheck=='undefined' || ltcreimcashorcheck==null){
		errorMessage += "Please Check Amount Issed Type.\n";
		/*if(status){
		$jq('#dvDate').focus();
		}*/
		status = false;
		
	}
	if(ltcreimcashorcheck=='CASH'){
		ltcreimBankName='0';
	}
	
if(ltcreimcashorcheck=='CHEQUE'){
	
	if ($jq('#ltcreimBankName').val()=='') {
		errorMessage += "Please Enter BankName.\n";
		if(status){
		$jq('#ltcreimBankName').focus();
		}
		status = false;
	}
	if ($jq('#ltcreimBranchName').val()=='') {
		errorMessage += "Please Enter BranchName.\n";
		if(status){
		$jq('#ltcreimBranchName').focus();
		}
		status = false;
	}
	if ($jq('#ltcreimChequeNo').val()=='') {
		errorMessage += "Please Enter ChequeNo.\n";
		if(status){
		$jq('#ltcreimChequeNo').focus();
		}
		status = false;
	}
	/*if ($jq('#ltcreimAdminDvno').val()=='') {
		errorMessage += "Please Enter Dvno.\n";
		if(status){
		$jq('#ltcreimAdminDvno').focus();
		}
		status = false;
	}
	if ($jq('#ltcreimAdminDvDate').val()=='') {
		errorMessage += "Please Enter DvDate.\n";
		if(status){
		$jq('#ltcreimAdminDvDate').focus();
		}
		status = false;
	}*/
}
	
	
	var reimbursementAdminRemarks=$jq('#reimbursementAdminRemarks').val();
	var reimbursementAdminDate=$jq('#reimbursementAdminDate').val();
	
	
	/*var reimDvno=$jq('#reimDvno').val();
	var reimDvDate=$jq('#reimDvDate').val();*/
	var requestID=$jq('#requestID').val();
	var requestId=$jq('#requestId').val();
	var ltcreimAdminDvno=$jq('#ltcreimAdminDvno').val();
	var ltcreimAdminDvDate=$jq('#ltcreimAdminDvDate').val();
	
	if(ltcreimcashorcheck=='CHEQUE'){
	var ltcreimBankName=$jq('#ltcreimBankName').val();
	}
	var ltcreimChequeNo=$jq('#ltcreimChequeNo').val();
	var ltcreimBranchName=$jq('#ltcreimBranchName').val();
	
	if(status){
	var requestDetails = {

				"reimbursementAdminRemarks" :reimbursementAdminRemarks,
				"reimbursementAdminDate" :reimbursementAdminDate,
				"requestID" :requestID,
				"requestId" :requestId,
				"ltcreimAdminDvno" :ltcreimAdminDvno,
				"ltcreimAdminDvDate" :ltcreimAdminDvDate,
				"ltcreimBankName" :ltcreimBankName,
				"ltcreimChequeNo" :ltcreimChequeNo,
				"ltcreimBranchName" :ltcreimBranchName,
				"ltcreimcashorcheck" :ltcreimcashorcheck,
				"param" : "ltcWaterAdminReimbursement",
				"type" :"ltcWaterApproval"			
		};
	$jq.ajax( {
		type : "POST",
		url : "ltcWaterRequest.htm?",
		data : requestDetails,
		cache : false,
		success : function(html) {
			$jq('#result').html(html);
			if ($jq('.success').is(':visible')) {
				var check=confirm(" LTC WATER  Reimbursement has been Successfully Sent...");
				if(check){
						
						document.forms[0].param.value = "ltcWaterSettlements";
						document.forms[0].action = "ltcWaterRequest.htm";
						document.forms[0].submit();
					
				}else{
				clearTadaAdvanceCumReq();
				}
			}
			
		}
	
	
	});
	}else{
		alert(errorMessage);
	}

	
}

function clearltcAdminWaterReimbursement(){
	
	$jq('#reimbursementAdminRemarks').val('');
	$jq('#reimbursementAdminDate').val('');
	$jq('#ltcreimAdminDvno').val('');
	$jq('#ltcreimAdminDvDate').val('');
	$jq('#ltcreimChequeNo').val('');
	
	$jq('#ltcreimBranchName').val('');
	//$jq('#ltcreimcashorcheck').val('');
	
	
}

function ltcAdminWaterSettlement(){
	var errorMessage = "";
	var status = true;

	if ($jq('#settlementAdminDate').val()=='') {
		errorMessage += "Please Enter SettlementDate.\n";
		if(status){
		$jq('#settlementAdminDate').focus();
		}
		status = false;
	}
	
	var settlementAdminDate=$jq('#settlementAdminDate').val();
	var settlementAdminRemarks=$jq('#settlementAdminRemarks').val();
	var requestID=$jq('#requestID').val();
	var requestId=$jq('#requestId').val();

	if(status){
	var requestDetails = {

				"settlementAdminDate" :settlementAdminDate,
				"settlementAdminRemarks" :settlementAdminRemarks,
				"requestID" :requestID,
				"requestId" :requestId,
				"param" : "ltcWaterAdminSettlement",
				"type" :"ltcWaterApproval"			
		};
	$jq.ajax( {
		type : "POST",
		url : "ltcWaterRequest.htm?",
		data : requestDetails,
		cache : false,
		success : function(html) {
			$jq('#result').html(html);
			if ($jq('.success').is(':visible')) {
				var check=confirm(" RECESS  Settlement has been Successfully Sent...");
				if(check){
						
						document.forms[0].param.value = "ltcWaterSettlements";
						document.forms[0].action = "ltcWaterRequest.htm";
						document.forms[0].submit();
					
				}else{
				clearTadaAdvanceCumReq();
				}
			}
			
		}
	
	});
	}else{
		alert(errorMessage);
	}
}

function clearltcWaterTadaASettlement(){
	
	$jq('#ltcsettleOrReimApplyDate').val('');
	$jq('#ltcselOrReimRemarks').val('');
	$jq('#ltcactualExpenditure').val('0');
	var totalTicketsAmt=$jq('#totalTicketsAmt').val();
	
	$jq('#ltcsettleOrReimAmt').val(totalTicketsAmt);
	
}

function ltcWaterTadaASettlement(){
	var errorMessage = "";
	var status = true;
	var requestID=$jq('#requestID').val();
	var requestId=$jq('#requestId').val();
	var ltcsettleOrReimApplyDate=$jq('#ltcsettleOrReimApplyDate').val();
	var ltcselOrReimRemarks=$jq('#ltcselOrReimRemarks').val();
	var ltcsettleOrReimAmt=$jq('#ltcsettleOrReimAmt').val();
	var ltcactualExpenditure=$jq('#ltcactualExpenditure').val();
	if(ltcsettleOrReimAmt>=0){
		var ltcsettleOrReim='Settlement';
	}else{
		
		var ltcsettleOrReim='Reimbursement';
	}
	
	if ($jq('#ltcactualExpenditure').val()=='') {
		errorMessage += "Please Enter actualExpenditure Amount.\n";
		if(status){
		$jq('#ltcactualExpenditure').focus();
		}
		status = false;
	}
	if ($jq('#ltcselOrReimRemarks').val()=='') {
		errorMessage += "Please Enter Remarks .\n";
		if(status){
		$jq('#ltcselOrReimRemarks').focus();
		}
		status = false;
	}
	if ($jq('#ltcsettleOrReimApplyDate').val()=='') {
		errorMessage += "Please Enter Apply Date .\n";
		if(status){
		$jq('#ltcsettleOrReimApplyDate').focus();
		}
		status = false;
	}
	if(status){
	var requestDetails = {

			"requestID" :requestID,
			"requestId" :requestId,
			"ltcsettleOrReimApplyDate" :ltcsettleOrReimApplyDate,
			"ltcselOrReimRemarks" :ltcselOrReimRemarks,
			"ltcsettleOrReimAmt" :ltcsettleOrReimAmt,
			"ltcactualExpenditure" :ltcactualExpenditure,
			"ltcsettleOrReim" :ltcsettleOrReim,
			"param" : "ltcWaterSettlementRequest",
			"type" :"ltcWaterApproval"			
	};
	
	$jq.ajax( {
		type : "POST",
		url : "ltcWaterRequest.htm?",
		data : requestDetails,
		cache : false,
		success : function(html) {
			$jq('#result').html(html);
			if ($jq('.success').is(':visible')) {
				var check=confirm(" LTC WATER  Settlement Request has been Successfully Sent...");
				if(check){
					
					document.forms[0].param.value = "ltcWaterSettAndReim";
					document.forms[0].action = "ltcWaterRequest.htm";
					document.forms[0].submit();
					
				}else{
				clearTadaAdvanceCumReq();
				}
			}
		}
	});
	} else{
		alert(errorMessage);
	}
}

function ltcsettlementOrReimAmt(){
	
	var totalTicketsAmt=$jq('#totalTicketsAmt').val();
	var ltcactualExpenditure=$jq('#ltcactualExpenditure').val();
	var rem=totalTicketsAmt-ltcactualExpenditure;
	$jq('#ltcsettleOrReimAmt').val(rem);
	
}


function ltcadvChequeFields(SOH){
	if(SOH=='show'){
		$jq('#trdata').css('display', 'block');
		
	}else{
		$jq('#reimAdminDvno').val('');
		$jq('#reimAdminDvDate').val('');
		$jq('#reimBankName').val('');
		$jq('#reimChequeNo').val('');
		$jq('#reimBranchName').val('');
		
		$jq('#trdata').css('display', 'none');
	}
}

function TotalAdultsAount(){
	
	var amountAdults=$jq('#amountAdults').val();	
	var noOfAdultsTickets=$jq('#noOfAdultsTickets').val();	
	var mul=parseInt(amountAdults)*parseInt(noOfAdultsTickets);
	$jq('#adultsTotAmt').val(mul);	
	
	var adultsTotAmt=$jq('#adultsTotAmt').val();	
	var childrenTotAmt=$jq('#childrenTotAmt').val();	
	var otherAmt=$jq('#otherAmt').val();
	
	var sum=parseInt(adultsTotAmt)+parseInt(childrenTotAmt)+parseInt(otherAmt);
	$jq('#totalTicketsAmt').val(sum);	
	
	
}


function TotalChildrenAmt(){
	
	var noOfChildrenTickets=$jq('#noOfChildrenTickets').val();	
	var amountChildren=$jq('#amountChildren').val();	
	var mul=parseInt(noOfChildrenTickets)*parseInt(amountChildren);
	//alert(mul);
	$jq('#childrenTotAmt').val(mul);	
	
	
	var adultsTotAmt=$jq('#adultsTotAmt').val();	
	var childrenTotAmt=$jq('#childrenTotAmt').val();	
	var otherAmt=$jq('#otherAmt').val();
	
	var sum=parseInt(adultsTotAmt)+parseInt(childrenTotAmt)+parseInt(otherAmt);
	$jq('#totalTicketsAmt').val(sum);
	
}

function TotalAmountAll(){
	var adultsTotAmt=$jq('#adultsTotAmt').val();	
	var childrenTotAmt=$jq('#childrenTotAmt').val();	
	var otherAmt=$jq('#otherAmt').val();
	
	var sum=parseInt(adultsTotAmt)+parseInt(childrenTotAmt)+parseInt(otherAmt);
	$jq('#totalTicketsAmt').val(sum);
	
}


function issueLtcAdvance(){
	var errorMessage = "";
	var status = true;
	//var ltcMembersJson={};
	
	if($jq('input:radio[id=ltcadvcashorcheck1]').is(':checked')){
		var ltcadvcashorcheck=$jq('#ltcadvcashorcheck1').val();
		} 
		if($jq('input:radio[id=ltcadvcashorcheck2]').is(':checked')){
			var ltcadvcashorcheck=$jq('#ltcadvcashorcheck2').val();
		}
		
		
	
	if ($jq('#amountAdults').val()=='') {
		errorMessage += "Please Enter Amount for Adults Tickets \n";
		if(status){
		$jq('#amountAdults').focus();
		}
		status = false;
	}
	if ($jq('#amountChildren').val()=='') {
		errorMessage += "Please Enter Amount for Children Tickets \n";
		if(status){
		$jq('#amountChildren').focus();
		}
		status = false;
	}
	
	if ($jq('#refLetterDate').val()=='') {
		errorMessage += "Please Enter Reference Letter Date \n";
		if(status){
		$jq('#refLetterDate').focus();
		}
		status = false;
	}
	if ($jq('#refLetterNo').val()=='') {
		errorMessage += "Please Enter Reference Letter No \n";
		if(status){
		$jq('#refLetterNo').focus();
		}
		status = false;
	}
	
	
	
	if(ltcadvcashorcheck=='CASH'){
		ltcadvBankName='0';
	}
	
	if(ltcadvcashorcheck=='CHEQUE'){
		

		
		if ($jq('#ltcadvBankName').val()=='') {
			errorMessage += "Please Enter BankName.\n";
			if(status){
			$jq('#ltcadvBankName').focus();
			}
			status = false;
		}
		if ($jq('#ltcadvBranchName').val()=='') {
			errorMessage += "Please Enter BranchName.\n";
			if(status){
			$jq('#ltcadvBranchName').focus();
			}
			status = false;
		}
		if ($jq('#ltcadvChequeNo').val()=='') {
			errorMessage += "Please Enter ChequeNo.\n";
			if(status){
			$jq('#ltcadvChequeNo').focus();
			}
			status = false;
		}
	
//		un commented by bkr according to ravi sir requirment
		if ($jq('#ltcadvAdminDvno').val()=='') {
			errorMessage += "Please Enter Dvno.\n";
			if(status){
			$jq('#ltcadvAdminDvno').focus();
			}
			status = false;
		}
		if ($jq('#ltcadvAdminDvDate').val()=='') {
			errorMessage += "Please Enter DvDate.\n";
			if(status){
			$jq('#ltcadvAdminDvDate').focus();
			}
			status = false;
		}
		
		
	}
	
	
	var amountAdults=$jq('#amountAdults').val();
	var adultsTotAmt=$jq('#adultsTotAmt').val();
	var amountChildren=$jq('#amountChildren').val();
	var childrenTotAmt=$jq('#childrenTotAmt').val();
	var refLetterDate=$jq('#refLetterDate').val();
	var refLetterNo=$jq('#refLetterNo').val();
	var otherAmt=$jq('#otherAmt').val();
	var totalTicketsAmt=$jq('#totalTicketsAmt').val();
	var requestID=$jq('#requestID').val();
	var requestId=$jq('#requestId').val();
	
	
	if(ltcadvcashorcheck=='CHEQUE'){
	var ltcadvBankName=$jq('#ltcadvBankName').val();
	}
	var ltcadvBranchName=$jq('#ltcadvBranchName').val();
	var ltcadvChequeNo=$jq('#ltcadvChequeNo').val();
	var ltcadvAdminDvno=$jq('#ltcadvAdminDvno').val();
	var ltcadvAdminDvDate=$jq('#ltcadvAdminDvDate').val();
	
	if(parseInt(totalTicketsAmt)<1){
		errorMessage += "Please Issue Amount for Tickets.\n";
		status = false;
		
	}
	
	//status=false;
	
	if(status){
		
		
		
		
		var requestDetails = {

				"amountAdults" :amountAdults,
				"adultsTotAmt" :adultsTotAmt,
				"amountChildren" :amountChildren,
				"childrenTotAmt" :childrenTotAmt,
				"refLetterDate" :refLetterDate,
				"refLetterNo" :refLetterNo,
				"otherAmt" :otherAmt,
				"requestID" :requestID,
				"requestId" :requestId,
				"ltcadvBankName" :ltcadvBankName,  
				"ltcadvBranchName" :ltcadvBranchName,
				"ltcadvChequeNo" :ltcadvChequeNo,
				"ltcadvAdminDvno" :ltcadvAdminDvno,
				"ltcadvAdminDvDate" :ltcadvAdminDvDate,
				"totalTicketsAmt" :totalTicketsAmt,
				"ltcadvcashorcheck" :ltcadvcashorcheck,
				"param" : "ltcWaterAdvanceIssue",
				"type" :"ltcWaterApproval"			
		};
		$jq.ajax( {
			type : "POST",
			url : "ltcWaterRequest.htm?",
			data : requestDetails,
			cache : false,
			success : function(html) {
				$jq('#result').html(html);
				if ($jq('.success').is(':visible')) {
					var check=confirm(" LTC WATER  Advance has been Successfully Sent...");
					if(check){
							
						document.forms[0].param.value = "ltcWaterSettlements";
						document.forms[0].action = "ltcWaterRequest.htm";
						document.forms[0].submit();
						
					}else{
						clearissueLtcAdvance();
					}
				}
				
			}
		
		
		});
		
		
		
	}else{
		alert(errorMessage);
	}
	
	
	
}
function clearissueLtcAdvance(){
	$jq('#amountAdults').val('0');
	$jq('#adultsTotAmt').val('0');
	$jq('#amountChildren').val('0');
	$jq('#childrenTotAmt').val('0');
	$jq('#refLetterDate').val('');
	$jq('#refLetterNo').val('');
	$jq('#otherAmt').val('0');
	$jq('#totalTicketsAmt').val('0');
	
	
	$jq('#ltcadvBranchName').val('');
	$jq('#ltcadvChequeNo').val('');
	$jq('#ltcadvAdminDvno').val('');
	$jq('#ltcadvAdminDvDate').val('');
	
}

function totalTicketsvalue(){
	
	var noOfAdultsTickets=$jq('#noOfAdultsTickets').val();
	var noOfChildrenTickets=$jq('#noOfChildrenTickets').val();
	
	var sum=parseInt(noOfAdultsTickets)+parseInt(noOfChildrenTickets);
	$jq('#totalTickets').val(sum);
	
}

function submitLTCWaterRequest(){
	
	var errorMessage = "";
	var status = true;
	var ltcMembersJson={};
	
	var k=0;
	$jq("#dataList tr:not(:first)").each(function() {
		if($jq(this).find("td").eq(0).find(":input:checkbox").is(':checked')){
			ltcMembersJson[k]=$jq(this).find("td").eq(0).find(":input:checkbox")[0].id
			k++;
			ltcMemberFlag=true;
		}		
	});
	
	
	
	
	
	if ($jq('#ltcYear').val()=='0') {
		errorMessage += "Please Enter Annual Year.\n";
		if(status){
		$jq('#ltcYear').focus();
		}
		status = false;
	}
	
	
	if ($jq('#startHoliday').val()=='') {
		errorMessage += "Please Enter startHoliday.\n";
		if(status){
		$jq('#startHoliday').focus();
		}
		status = false;
	}
	if ($jq('#returnHoliday').val()=='') {
		errorMessage += "Please Enter returnHoliday.\n";
		if(status){
		$jq('#returnHoliday').focus();
		}
		status = false;
	}
	if ($jq('#nod').val()=='') {
		errorMessage += "Please Enter NO OF Days.\n";
		if(status){
		$jq('#nod').focus();
		}
		status = false;
	}
	if ($jq('#noOfAdultsTickets').val()=='') {
		errorMessage += "Please Enter noOfAdultsTickets.\n";
		if(status){
		$jq('#noOfAdultsTickets').focus();
		}
		status = false;
	}
	if ($jq('#noOfChildrenTickets').val()=='') {
		errorMessage += "Please Enter noOfChildrenTickets.\n";
		if(status){
		$jq('#noOfChildrenTickets').focus();
		}
		status = false;
	}
	
	
	var startHoliday=$jq('#startHoliday').val();
	var returnHoliday=$jq('#returnHoliday').val();
	var nod=$jq('#nod').val();
	var noOfAdultsTickets=$jq('#noOfAdultsTickets').val();
	var noOfChildrenTickets=$jq('#noOfChildrenTickets').val();
	var typeOfLtc=$jq('#typeOfLtc').val();
	var ltcYear=$jq('#ltcYear').val();
	var hometownAddr=$jq('#hometownAddr').val();
	var leaveType=$jq('#leaveType').val();	
	
	var totalTickets=$jq('#totalTickets').val();	
	
	


	var a=startHoliday.substring(7);
	var b=returnHoliday.substring(7);
	var res = startHoliday.substring(3, 6); 
	var c=monthtonum(res);
	var res1 = returnHoliday.substring(3, 6); 
	var d=monthtonum(res1);
	var e=startHoliday.substring(0, 2); 
	var f=returnHoliday.substring(0, 2); 
	var datestatus = true;
	
	if(parseInt(a)>parseInt(b)){
		errorMessage += "returnHoliday Date Must Be GreaterThan startHoliday date\n";
		status = false;
		datestatus=false;
	}
	if(datestatus){
	if(parseInt(a)==parseInt(b)){
		if(parseInt(c)>parseInt(d)){
			errorMessage += "returnHoliday Date Must Be GreaterThan startHoliday date\n";
			status = false;
			datestatus=false;
		}
		
	}
	}
	
	if(datestatus){
		if(parseInt(c)==parseInt(d)){
		if(parseInt(e)>parseInt(f)){
			errorMessage += "returnHoliday Date Must Be GreaterThan startHoliday date\n";
			status = false;
			datestatus=false;
		}
		}
	}
	
	if((parseInt(totalTickets))<1){
		
		errorMessage += "Please Enter No OF Tickets\n";
		if(status){
			$jq('#noOfAdultsTickets').focus();
			}
		status = false;
		
	}
	
	
	if(status){
	if((parseInt(nod))>28){
		
		alert("Annual Leave Considered upto 28 Days Only but Your Are take More than That");
		
		/*errorMessage += "No Of Days Should be lessthan 29 Days\n";
		if(status){
			$jq('#nod').focus();
			}
		status = false;*/
		
	}
	}
	if(status){
		var ltcYear1=$jq('#ltcYear').val();
		var ltcStartyear=ltcYear1.substring(0,4);
		var ltcEndyear=parseInt(ltcStartyear)+2;
		
		ltcStartyear="01-Jun-"+ltcStartyear;
		ltcEndyear="31-May-"+ltcEndyear;
		
		var startHoliday1=$jq('#startHoliday').val();
		var returnHoliday1=$jq('#returnHoliday').val();
		
		
		var ltcA=startHoliday1.substring(7);
		var ltcH=returnHoliday1.substring(7);
		var ltcB=ltcStartyear.substring(7);
		var ltcG=ltcEndyear.substring(7);
		
		
		var ltcRes1 = startHoliday1.substring(3, 6); 
		var ltcI=monthtonum(ltcRes1);
		
		var ltcRes2 = returnHoliday1.substring(3, 6); 
		var ltcJ=monthtonum(ltcRes2);
		
		var ltcRes3 = ltcStartyear.substring(3, 6); 
		var ltcK=monthtonum(ltcRes3);
		
		var ltcRes4 = ltcEndyear.substring(3, 6); 
		var ltcL=monthtonum(ltcRes4);
		
		
		
		
		var ltcE=startHoliday1.substring(0, 2); 
		var ltcF=ltcStartyear.substring(0, 2); 
		
		var ltcdatestatus = true;
		var ltcdatestatus1 = true;
		
	
	var ltcyearcheck=((parseInt(ltcA)>=parseInt(ltcB)) && (parseInt(ltcA)<=parseInt(ltcG)) );
	
	var ltcyearcheck1=((parseInt(ltcH)>=parseInt(ltcB)) && (parseInt(ltcH)<=parseInt(ltcG)) );
		
		if(!(ltcyearcheck)){
			
			errorMessage += "Please Enter startHoliday Date between Selected Annual years.\n";
			if(status){
			$jq('#startHoliday').focus();
			}
			status = false;
			ltcdatestatus=false;
			
		}
		
		if(ltcdatestatus){
			
			
			if((parseInt(ltcA)==parseInt(ltcB))){
				if(!(parseInt(ltcI)>=6)){
					errorMessage += "StartHoliday Date Must Be GreaterThan or Equal to June \n";
					$jq('#startHoliday').focus();
					status = false;
					ltcdatestatus=false;
				}
				
			}
			if((parseInt(ltcA)==parseInt(ltcG))){
				if(!(parseInt(ltcI)<=5)){
					errorMessage += "StartHoliday Date Must Be LessThan June \n";
					status = false;
					ltcdatestatus=false;
				}
				
			}
			
		}
		
		
		
		
	if(!(ltcyearcheck1)){
			
			errorMessage += "Please Enter returnHoliday Date between Selected Annual years.\n";
			if(status){
			$jq('#returnHoliday').focus();
			}
			status = false;
			ltcdatestatus1=false;
			
		}
	
	
	
	if(ltcdatestatus1){
		
		
		if((parseInt(ltcH)==parseInt(ltcB))){
			if(!(parseInt(ltcJ)>=6)){
				errorMessage += "returnHoliday Date Must Be GreaterThan or Equal to June \n";
				$jq('#startHoliday').focus();
				status = false;
				ltcdatestatus1=false;
			}
			
		}
		if((parseInt(ltcH)==parseInt(ltcG))){
			if(!(parseInt(ltcJ)<=5)){
				errorMessage += "returnHoliday Date Must Be LessThan June \n";
				status = false;
				ltcdatestatus1=false;
			}
			
		}
		
	}
		
		
	}
	
	//status = false;
	if(status){
	var requestDetails = {

				"startHoliday" :startHoliday,
				"returnHoliday" :returnHoliday,
				"nod" :nod,
				"noOfAdultsTickets" :noOfAdultsTickets,
				"noOfChildrenTickets" :noOfChildrenTickets,
				"typeOfLtc" :typeOfLtc,
				"ltcYear" :ltcYear,
				"hometownAddr" :hometownAddr,
				"leaveType" :leaveType,
				"totalTickets" :totalTickets,
				"jsonValue":JSON.stringify(ltcMembersJson),
				"param" : "submitLTCWaterRequest",
				"type" :"ltcWaterApproval"	
		};
	
	$jq.ajax( {
		type : "POST",
		url : "ltcWaterRequest.htm?",
		data : requestDetails,
		cache : false,
		success : function(html) {
			$jq('#result').html(html);
			if ($jq('.success').is(':visible')) {
				var check=confirm(" LTC WATER Request has been Successfully Sent...\nPreview LTC WATER Application Form & print from here");
				if(check){
					
					document.forms[0].requestId.value = $jq.trim(requestIDltcA);
					
					document.forms[0].param.value = "ltcWaterRequestDetails";
					document.forms[0].action = "workflowmap.htm";
						//document.forms[0].action = "workflowmap.htm?param=requestDetails";
						document.forms[0].submit();
					
				}else{
				clearTadaAdvanceCumReq();
				}
			}
			
		}
	
	
	});
	
	}else{
		alert(errorMessage);
	}
	
	

	
	
	
}

function clearLTCWaterRequest(){
	
	$jq('#hometownAddr').val('');
	$jq('#startHoliday').val('');
	$jq('#returnHoliday').val('');
	$jq('#nod').val('0');
	$jq('#noOfAdultsTickets').val('0');
	$jq('#noOfChildrenTickets').val('0');
	$jq('#totalTickets').val('0');
	
	
	
}

function noOfdays(){
	if($jq('#returnHoliday').val()!=''){
		if($jq('#startHoliday').val()!=''){
			var firstDate=$jq('#returnHoliday').val();
			var	secondDate=$jq('#startHoliday').val();
			
			var dd1=firstDate.substring(0,2);
			var mm1=firstDate.substring(3,6);
			var yy1=firstDate.substring(7,11);
			var dd2=secondDate.substring(0,2);
			var mm2=secondDate.substring(3,6);
			var yy2=secondDate.substring(7,11);
			mm1=monthtonum(mm1);
			mm2=monthtonum(mm2);
			firstDate=mm1+"/"+dd1+"/"+yy1;
			secondDate=mm2+"/"+dd2+"/"+yy2;
			var startDay = new Date(firstDate);
			 var endDay = new Date(secondDate);
			 var millisecondsPerDay = 1000 * 60 * 60 * 24;
	            var millisBetween = startDay.getTime() - endDay.getTime();
	            var days = millisBetween / millisecondsPerDay;
	            days=parseInt(days);
	            if(days>=0){
	            days=parseInt(days)+1;
	            }else{
	            	 days=parseInt(days)-1;
	            }
			var diffdays=Math.floor(days);
	            $jq('#nod').val(diffdays);
		}
		
	}
	}
	function monthtonum(a)
	{
		if(a=="Jan"){return  1;}
		if(a=="Feb"){return  2;}
		if(a=="Mar"){return  3;}
		if(a=="Apr"){return  4;}
		if(a=="May"){return  5;}
		if(a=="Jun"){return  6;}
		if(a=="Jul"){return  7;}
		if(a=="Aug"){return  8;}
		if(a=="Sep"){return  9;}
		if(a=="Oct"){return  10;}
		if(a=="Nov"){return  11;}
		if(a=="Dec"){return  12;}
	}


/////////////////////////////////old/////////////

function lables(type) {
	var req="<span class='failure'>*</span>";
	
	/*added by bkr 11/04/2016 2 lines*/
	var ifcondition=true;
	if(ifcondition!=true){
		/*commented by bkr 11/04/2016*/
	/* if (paymentDetails != '' && paymentDetails !=undefined) {
		if (paymentDetails == 'BASIC PAY GRADE PAY DETAILS NOT ENTER') {
			$jq('#ltcerror').attr('style', 'display:none');
			 $jq('#ltcerrorb').html('<span class="ltcerrordiplay">Your Basic Pay or Grade Pay details does not exist.<br/>Please Contact Admin	 Task Holder as LTC Request cannot be raised.</span>');
			if (type == 'advance') {
				document.title = 'LTC Advance Request';
				$jq('#headTitle').html('LTC Advance Request');
				$jq('#desiredAdvanceAmountDiv').attr('style', 'display:block');
				$jq('#numberOfTicketsDiv').attr('style', 'display:block');
				$jq('#amountPerPersonDiv').attr('style', 'display:block');
				$jq('#noOfInfantTicketsDiv').attr('style', 'display:block');
				$jq('#amountPerEachInfantDiv').attr('style', 'display:block');
				$jq('#dateOfDepartureDiv').attr('style', 'display:none');
				$jq('#dateOfReturnDiv').attr('style', 'display:none');
				$jq('#ltcDetails').attr('style', 'display:none');
				$jq('#homeTownAddress').attr('style', 'display:none');
				$jq('#individualDetails').attr('style', 'display:none');

			}
			if (type == 'ltcApproval') {
				document.title = 'LTC Approval Request';
				$jq('#headTitle').html('LTC Approval Request');

			}
			if (type == 'reimbursement') {
				document.title = 'LTC Reimbursement Request';
				$jq('#headTitle').html('LTC Reimbursement Request');
			}
			
			if (type == 'ltcAdvance') {
				document.title = 'LTC Approval Cum Advance Request';
				$jq('#headTitle').html('LTC Approval & Advance Request');
				$jq('#desiredAdvanceAmountDiv').attr('style', 'display:block');
				$jq('#numberOfTicketsDiv').attr('style', 'display:block');
				$jq('#amountPerPersonDiv').attr('style', 'display:block');
				$jq('#noOfInfantTicketsDiv').attr('style', 'display:block');
				$jq('#amountPerEachInfantDiv').attr('style', 'display:block');
				$jq('#individualDetails').attr('style', 'display:none');
			}
		} else {
			$jq('#ltcerrorb').html("<span class='ltcerrordiplay'>Your Home Town Address does not exist.<br/>Please Contact Admin Task Holder as LTC Request cannot be raised.</span>");
			$jq('#ltcerror').hide();
			
			
			
			if (type == 'advance') {
				document.title = 'LTC Advance Request';
				$jq('#headTitle').html('LTC Advance Request');
				$jq('#desiredAdvanceAmountDiv').attr('style', 'display:block');
				$jq('#numberOfTicketsDiv').attr('style', 'display:block');
				$jq('#amountPerPersonDiv').attr('style', 'display:block');
				$jq('#noOfInfantTicketsDiv').attr('style', 'display:block');
				$jq('#amountPerEachInfantDiv').attr('style', 'display:block');
				$jq('#dateOfDepartureDiv').attr('style', 'display:none');
				$jq('#dateOfReturnDiv').attr('style', 'display:none');
				$jq('#ltcDetails').attr('style', 'display:none');
				$jq('#homeTownAddress').attr('style', 'display:none');
				$jq('#individualDetails').attr('style', 'display:none');

			}
			if (type == 'ltcApproval') {
				document.title = 'LTC Approval Request';
				$jq('#headTitle').html('LTC Approval Request');

			}
			if (type == 'reimbursement') {
				document.title = 'LTC Reimbursement Request';
				$jq('#headTitle').html('LTC Reimbursement Request');
			}
			
			if (type == 'ltcAdvance') {
				document.title = 'LTC Approval Cum Advance Request';
				$jq('#headTitle').html('LTC Approval & Advance Request');
				$jq('#desiredAdvanceAmountDiv').attr('style', 'display:block');
				$jq('#numberOfTicketsDiv').attr('style', 'display:block');
				$jq('#amountPerPersonDiv').attr('style', 'display:block');
				$jq('#noOfInfantTicketsDiv').attr('style', 'display:block');
				$jq('#amountPerEachInfantDiv').attr('style', 'display:block');
				$jq('#individualDetails').attr('style', 'display:none');
			}
		}
	*/} else {
		if (type == 'ltcType') {
			document.title = 'LTC Type Master';
			$jq('#headTitle').html('Create LTC Type');
			$jq('#labelType').html("LTC Type Name"+req);
		} else if (type == 'ltcBlock') {
			document.title = 'LTC Block Master';
			$jq('#headTitle').html('Create LTC Block');
			$jq('#labelType').html("LTC Block Name"+req);
		} else if (type == 'ltcBlockYear') {
			document.title='LTC Block Year Master';
			$jq('#headTitle').html('Create LTC Block Year');
		} else if (type == 'ltcPenalInterestMaster') {
			document.title='LTC Penal Interest Master';
			$jq('#headTitle').html('Penal Interest Master');
			$jq('#labelType').html("Penal Interest (%)"+req);
		} else if (type == 'reimbursement') {
			document.title='LTC Reimbursement Request';
			$jq('#headTitle').html('LTC Reimbursement Request');
		} else if (type == 'settlement') {
			document.title='LTC Settlement Request';
			$jq('#headTitle').html('LTC Settlement Request');
		} else if (type == 'ltcAdvance') {
			document.title='LTC Approval Cum Advance Request';
			$jq('#headTitle').html('LTC Approval & Advance Request');
			$jq('#desiredAdvanceAmountDiv').attr('style','display:block');
			$jq('#numberOfTicketsDiv').attr('style','display:block');
			$jq('#amountPerPersonDiv').attr('style','display:block');
			$jq('#noOfInfantTicketsDiv').attr('style','display:block');
			$jq('#amountPerEachInfantDiv').attr('style','display:block');
			$jq('#individualDetails').attr('style','display:none');
		} else if (type == 'ltcApproval') {
			document.title='LTC Approval Request';
			$jq('#headTitle').html('LTC Approval Request');
			if ($jq('#departureDate').val()=='') {
				$jq('#individualDetails').attr('style','display:block');
			}
		} else if(type == 'advance') {
			document.title='LTC Advance Request';
			$jq('#headTitle').html('LTC Advance Request');
			$jq('#desiredAdvanceAmountDiv').attr('style','display:block');
			$jq('#numberOfTicketsDiv').attr('style','display:block');
			$jq('#amountPerPersonDiv').attr('style','display:block');
			$jq('#noOfInfantTicketsDiv').attr('style','display:block');
			$jq('#amountPerEachInfantDiv').attr('style','display:block');
			$jq('#dateOfDepartureDiv').attr('style','display:none');
			$jq('#dateOfReturnDiv').attr('style','display:none');
			$jq('#ltcDetails').attr('style','display:none');
			$jq('#homeTownAddress').attr('style','display:none');
			$jq('#individualDetails').attr('style','display:none');
		}
	}
}
function submitRefundDetails(requestID,historyID,requestType,leaveTypeId,ltcRequestType){
	
	var status=true;
	var errorMessage="";
	if(requestType!='LTC APPROVAL' && $jq('#issuedAmount').text()=='' && ltcRequestType != 'LTC ADVANCE') {
		errorMessage+="CDA Amount not yet issued\n";
		status =false;
	}if(requestType=='LTC APPROVAL CUM ADVANCE' && $jq('#issuedAmount').text()=='' && ltcRequestType =='LTC APPROVAL CUM ADVANCE') {
		errorMessage="";
		status =true;
	}if(requestType!='LTC APPROVAL' && ltcRequestType == 'LTC ADVANCE' && $jq('#financeIssuedAmount').text()=='') {
		errorMessage+="Before cancle this Approval request Please cancel the LTC Advance request in the Dash Board\n";
		status =false;
	}
	if($jq('#remarks').val()=='')
	{
		errorMessage+="Enter reason for not performing journey\n";
		$jq('#remarks').focus();
		status =false;
	}
	if(leaveTypeId!='' && $jq('#leaveCancelFlag1').is(':visible') && $jq('#leaveCancelFlag2').is(':visible')) {
		if(!$jq('#leaveCancelFlag1').is(':checked') && !$jq('#leaveCancelFlag2').is(':checked')) {
			errorMessage+="Do you want to cancle Leave?";
			$jq('#leaveCancelFlag1').focus();
			status =false;
		}
	}
	if(status){
		$jq('#param').val('submitRequest');
		$jq('#requestIDs').val(requestID);
		if(requestType=='LTC APPROVAL') {
			$jq('#type').val('ltcCancle');
		}else 
		if(requestType=='LTC APPROVAL CUM ADVANCE' && ltcRequestType =='LTC APPROVAL CUM ADVANCE') {
			$jq('#type').val('ltcApprCumAdvCancle');
			$jq('#cancleType').val('ltcApprCumAdvanceCancle');
		}else {
			$jq('#type').val('ltcrefundtype');
		}
		$jq.post('ltcApprovalRequest.htm', $jq('#LtcApplicationBean').serialize(),
				function(html) {
					$jq("#result").html(html);
					$jq('#remarks').val('');
					if($jq('#leaveCancelFlag1').is(':checked') && $jq('#leaveCancelFlag1').val()=='Y') {
						var ltcCancelReason="Leave cancelled due to LTC cancel";
						$jq.post('leaveRequest.htm?requestID='+leaveTypeId+'&param=cancelLeave&reason='+ltcCancelReason+'&ltcRequestType=ltcLeaveCancel',  
						           function(data){ 
						            	 $jq('#result').html(data);
						            	 $jq('input:radio[name=leaveCancelFlag]').attr("checked",false);
						           } 
						      );
					}
					
				});
	}else{
		alert(errorMessage);
	}
	
}
function cancelLtcRequest(requestType,requestID,doPartID,historyID,financeAmount,cdaAmount)
{
	if(doPartID!="0" && doPartID!=''){
		if((financeAmount!='' && cdaAmount!='') || (financeAmount=='' && cdaAmount=='')) {
			document.forms[0].param.value = "LTCRefundHome";
			document.forms[0].type.value = requestType;   
			document.forms[0].id.value=requestID;
			document.forms[0].action="ltcApprovalRequest.htm"
			document.forms[0].submit();	
		}else {
			alert('Advance Amount is in Process at CDA. Cancellation not posible');
		}
	}else{
		if(requestType=="ltcApproval") {requestType="LTC APPROVAL";}else {requestType="LTC APPROVAL CUM ADVANCE";}
		if(confirm('Do you want to cancel the LTC request')) {
			$jq.ajax( {
				type : "POST",
				url : "workflow.htm?param=declined&historyID="+historyID+"&requestID="+requestID+ '&requestType='+requestType+'&type=cancelled',
				cache : false,
				success : function(html) {			
					$jq("#historyDetails").html(html);
					ltcApprovalDetails();
				}
			});
		}
	}
}
function clearLtcMaster(){
	masterID = "";
	$jq('#typeValue').val('');
	$jq('#description').val('');
	$jq('#counter').val('250');
	$jq('#fromDate').val('');
	$jq('#toDate').val('');
	$jq('#extendedDate').val('');
	$jq('#ltcBlockId').val('Select');
	$jq('#fourYearBlockId').val('Select');
	$jq('#blockYearDiv').css("display","none");
}
function manageLtcMaster(type){
	var errorMessage='';
	var status=true;
	document.forms[0].param.value="manage";
	document.forms[0].type.value=type;
	var name=$jq('#typeValue').val();
	
	if(type=='ltcBlockYear'){
		if($jq('#ltcBlockId option:selected').val()=='Select'){
			errorMessage+="Please Select LTC Block \n";
			if(status){
				status=false;
				$jq('#ltcBlockId').focus();
			}
		}if($jq('#fourYearBlockId').is(':visible') && $jq('#fourYearBlockId option:selected').val()=='Select'){
			errorMessage+="Please Select Four Year Block \n";
			if(status){
				status=false;
				$jq('#fourYearBlockId').focus();
			}
		}
		if($jq('#fromDate').val()==''){
			errorMessage+="Please Enter From Date\n";
			if(status){
				status=false;
				$jq('#fromDate').focus();
			}
		}
		if($jq('#toDate').val()=='' && $jq('#ltcBlockId option:selected').text()!='One Year'){
			errorMessage+="Please Enter To Date\n";
			if(status){
				status=false;
				$jq('#toDate').focus();
			}
		}
		if(status && $jq('#fromDate').val()!='' && $jq('#toDate').val()!=''){
			var days=1000*60*60*24*365;
			var date1=getDateFromFormat($jq('#fromDate').val(), "dd-NNN-y");
			var date2=getDateFromFormat($jq('#toDate').val(), "dd-NNN-y");
			var diffDate=Math.round((date2-date1)/days);
			if($jq('#ltcBlockId option:selected').text()=='Four Year'){
				if(diffDate!=4){
					status=false;
					errorMessage+="Please Enter Correct  To Date\n";
					$jq('#toDate').focus();
				}
			}
			if(date1>=date2){
				errorMessage+="To Date must be greater than From Date\n";
				if(status){
					status=false;
					$jq('#toDate').val('');
					$jq('#toDate').focus();
					}
			}
			if($jq('#ltcBlockId option:selected').text()=='One Year'){
			   if($jq('#extendedDate').val()!=''){
				   var date3=getDateFromFormat($jq('#extendedDate').val(), "dd-NNN-y");
				   if(date2>date3){
					  status=false;
					  errorMessage+="Please Enter Correct Extended Date\n";
					  $jq('#extendedDate').focus();
				}
			}}
			if($jq('#ltcBlockId option:selected').text()=='Two Year'){
				
				if($jq('#ltcBlockYear').length > 0 && $jq('#ltcBlockYear option:selected').val()!='Select'){
					var blockYearArray=$jq('#ltcBlockYear option:selected').val().split('-');
					var toYear=blockYearArray[0].substring(0,2)+blockYearArray[1];
					if(diffDate!=2){
						status=false;
						errorMessage+="Please Enter Correct To Date\n";
						$jq('#toDate').focus();
					}
					var fromDateYear=parseInt($jq('#fromDate').val().split('-')[2]);
					var toDateYear=parseInt($jq('#toDate').val().split('-')[2]);
					if(!(parseInt(fromDateYear) > parseInt(blockYearArray[0])) &&
							!(parseInt(fromDateYear) < parseInt(toYear))){
						status=false;
						errorMessage+="Please Enter Correct From Date\n";
						$jq('#fromDate').focus();
					}
					if(!(parseInt(toDateYear) > parseInt(blockYearArray[0])) &&
							!(parseInt(toDateYear) < toYear)){
						status=false;
						errorMessage+="Please Enter Correct To Date\n";
						$jq('#toDate').focus();
					}
					if($jq('#extendedDate').val()!=''){
						if(!(parseInt($jq('#extendedDate').val()) > parseInt(toYear)) &&
								!(parseInt(toDateYear) < (parseInt(toYear)+1))){
							status=false;
							errorMessage+="Please Enter Correct Extended Date\n";
							$jq('#extendedDate').focus();
						}
					}
				}
				
			}
		}
		if($jq('#fourYearBlockId').val()=='Select') {
			$jq('#fourYearBlockId').val('');
		}
		if(status){
			$jq.post(
							'ltcMaster.htm?id='+masterID+'&'+dispUrl,$jq('#ltcMaster').serialize(), 
						     function(data){ 
						       	 $jq('#result').html(data);
						       	 clearLtcMaster();
						    } 
					); 
				
		}else{
			alert(errorMessage);
		}
	}else if(type=='ltcPenalInterestMaster'){
		if($jq('#typeValue').val()==''){
			errorMessage = "Please Enter Penal Interest\n";
			status=false;
			$jq('#typeValue').focus();
		}
		if($jq('#fromDate').val()==''){
			errorMessage+="Please Enter From Date\n";
			if(status){
				status=false;
				$jq('#fromDate').focus();
			}
		}
		if($jq('#toDate').val()==''){
			errorMessage+="Please Enter To Date\n";
			if(status){
				status=false;
				$jq('#toDate').focus();
			}
		}
		var date1=getDateFromFormat($jq('#fromDate').val(), "dd-NNN-y");
		var date2=getDateFromFormat($jq('#toDate').val(), "dd-NNN-y");
		if($jq('#fromDate').val()!='' && $jq('#toDate').val()!=''){
			if(date1>=date2){
				errorMessage+="To Date must be greater than From Date\n";
				if(status){
					status=false;
					$jq('#toDate').val('');
					$jq('#toDate').focus();
					}
			}
		}
		
		if(status){
			$jq.post(
					
				    'ltcMaster.htm?id='+masterID+'&name='+name+'&'+dispUrl,$jq('#ltcMaster').serialize(), 
				     function(data){ 
				       	 $jq('#result').html(data);
				       	 clearLtcMaster();
				    } 
			);
		}else{
			alert(errorMessage);
		}
	}
	else if(type=='ltcType'){
		if($jq('#typeValue').val()==''){
			errorMessage = "Please Enter LTC Type Name";
			status=false;
			$jq('#typeValue').focus();
		}
		else if($jq('#typeValue').val()=='All India'){
			errorMessage = "Please Do Not Enter LTC Type Name as All India";
			status=false;
			$jq('#typeValue').focus();
		}
		else if($jq('#typeValue').val()=='Home Town'){
			errorMessage = "Please Do Not Enter LTC Type Name as Home Town";
			status=false;
			$jq('#typeValue').focus();
		}
		if(status){
			$jq.post(
					
				    'ltcMaster.htm?id='+masterID+'&name='+name+'&'+dispUrl,$jq('#ltcMaster').serialize(), 
				     function(data){ 
				       	 $jq('#result').html(data);
				       	 clearLtcMaster();
				    } 
			); 
		}else{
			alert(errorMessage);
		}
	}
	
	else{
		if($jq('#typeValue').val()==''){
			if (type == 'ltcBlock') {
				errorMessage = "Please Enter LTC Block Name";
			} else {
				errorMessage = "Please Enter LTC Type Name"
			}
			status=false;
			$jq('#typeValue').focus();
		}
		if(status){
			$jq.post(
					
				    'ltcMaster.htm?id='+masterID+'&name='+name+'&'+dispUrl,$jq('#ltcMaster').serialize(), 
				     function(data){ 
				       	 $jq('#result').html(data);
				       	 clearLtcMaster();
				    } 
			); 
		}else{
			alert(errorMessage);
		}
	}
}
function editLtcMaster(jsonData,id){
	for ( var i = 0; i < jsonData.length; i++) {
		if (jsonData[i].id == id) {
			masterID=jsonData[i].id;
			if($jq('#typeValue').length > 0){
				$jq('#typeValue').val(jsonData[i].name);
			}
			if($jq('#description').length > 0){
				$jq('#description').val(jsonData[i].description);
			}
			if($jq('#fromDate').length > 0){
				$jq('#fromDate').val(jsonData[i].fromDateOne);
			}
			if($jq('#toDate').length > 0){
				$jq('#toDate').val(jsonData[i].toDateOne);
			}
			if($jq('#ltcBlockId').length > 0){
				$jq('#ltcBlockId').val(jsonData[i].ltcBlockId);
			}
			if($jq('#extendedDate').length > 0){
				$jq('#extendedDate').val(jsonData[i].extendedDateOne);
			}
			if(jsonData[i].fourYearBlockId!=''){
				$jq("#blockYearDiv").attr('style','display:block');
				$jq('#fourYearBlockId').val(jsonData[i].fourYearBlockId);
			}else {
				$jq("#blockYearDiv").attr('style','display:none');
			}
		}
	}
}
function editLtcPenalMaster(jsonData,id){
	for ( var i = 0; i < jsonData.length; i++) {
		if (jsonData[i].id == id) {
			masterID=jsonData[i].id;
			if($jq('#typeValue').length > 0){
				$jq('#typeValue').val(jsonData[i].typeValue);
			}
			if($jq('#description').length > 0){
				$jq('#description').val(jsonData[i].description);
			}
			if($jq('#fromDate').length > 0){
				$jq('#fromDate').val(jsonData[i].fromDateOne);
			}
			if($jq('#toDate').length > 0){
				$jq('#toDate').val(jsonData[i].toDateOne);
			}
			
		}
	}
}
function deleteLtcMaster(id, type) {
	document.forms[0].param.value="delete";
	document.forms[0].type.value=type;
	var del = confirm("Do you want to delete this item?");
	if (del == true) {
		$jq.post( 
		    'ltcMaster.htm?id='+id+'&'+dispUrl,$jq('#ltcMaster').serialize(),  
		     function(data){ 
		       	 $jq('#result').html(data);
		       	 clearLtcMaster();
		    } 
		  ); 
	}
}
function textCounter(e,des,tbox,maxlimit){
	   var code = (e.keyCode ? e.keyCode : e.which);
	   if (code == 8){
		   if((des.val().length-1)>=0)
			   tbox.val(maxlimit-(des.val().length-1));
		   return true;
	   }else{
		   if ((des.val().length)+1 > maxlimit) {
			   des.val(des.val().substring(0, maxlimit));
				// alert( 'Textarea value can only be 10 characters in length.' );
				return false;
			} else {
				tbox.val(maxlimit - ((des.val().length)+1));
				return true;
			}
	   }
}
function resetBlockMaster(){
	$jq('#blockType').val('select');
	$jq('#startingDate').val('');
	$jq('#endingDate').val('');
	$jq('#extendingDate').val('');
}
function editBlockYear(id,blockID,startingDate,endingDate,extendingDate){
	blockYearID = id;
	$jq('#blockType').val(blockID);
	$jq('#startingDate').val(startingDate);
	$jq('#endingDate').val(endingDate);
	$jq('#extendingDate').val(extendingDate);
}
function submitBlockMaster(){
	if( $jq('#ltcMaster').valid() == true) {
		document.forms[0].param.value="saveDetails";
		$jq.post('ltcMaster.htm?blockYearID='+blockYearID,$jq('#ltcMaster').serialize(),  
	           function(data){ 
	            	 $jq('#result').html(data);
	           } 
	      );
	}
}
function deleteBlockYear(id){
	$jq.post('ltcMaster.htm?param=delete&blockYearID='+id,
	           function(data){ 
	            	 $jq('#result').html(data);
	           } 
	      );
}
/*function submitLtcFamily(){
	var id="";
	var content="";
	$jq("#familyTable tr:not(:first)").each(function() {
		id = $jq(this).find("td").eq(0).find(":input").attr('id');
		if($jq(this).find("td").eq(0).find(":input").is(':checked')){
			content+=id+"-1,"
		}else{
			content+=id+"-0,"
		}
	});
	if(content!=""){
		$jq.post('ltcMaster.htm?param=familySubmit&selectCont='+content,  
		           function(data){ 
		            	 $jq('#result').html(data);
		           } 
		      );
	}
}*/
function enableTypeOfLtc(){
	
	/*added by bkr 12/04/2016 one line only*/
	$jq('#placeOfVisit').val('Home Town');
	/**
	 * In below code 6,18 or district ids of Hyderabad and Rangareddi in District_Master table
	 */
	if(!(place!='' && (district=="6" || district=="18") && $jq('#ltcTypeId').val()!="1")) {
		if($jq('#ltcTypeId').val()!=2) {
			$jq('#typeOfLtcLables').css('display','none');
			$jq('#typeOfLtc').css('display','block');
			$jq('#homeTownAddress').css('display','none');
		}else{ 
			$jq('#homeTownAddress').css('display','block');
			if(addId!='' && addId!=null){
				$jq('#placeOfVisit').val('');
				$jq('#nearestRlyStation').val('');
				
				$jq('#typeOfLtc').css('display','none');
				$jq('#typeOfLtcLables').css('display','block');
			}else{
				$jq('#ltcTypeId').val('Select');
				alert("Your HomeTown Address Details are not entered. Please intimate to Admin");
			}if(rlyStation=='') {
				$jq('#ltcTypeId').val('Select');
				alert("Nearest Railway Station is not entered ");
			}
		}
	}else {
		if($jq('#ltcTypeId').val()=="2") {
			alert('You are not eligible for Home Town LTC');
		}else{
			alert('You are not eligible for Converstion of Home Town LTC');
		}
		$jq('#ltcTypeId').val('Select');
	}
	
	
	if($jq('#ltcTypeId').val()==2){		
		$jq('.BGWORLD').addClass('BGWORLD_home').removeClass('BGWORLD_jammu');	
		
	}
	else if($jq('#ltcTypeId').val()==1){	
		$jq('.BGWORLD').addClass('BGWORLD_allindia').removeClass('BGWORLD_home','BGWORLD_jammu','BGWORLD_north','BGWORLD_andaman').removeClass('BGWORLD_home','BGWORLD_jammu','BGWORLD_north','BGWORLD_andaman');

	}
	else if($jq('#ltcTypeId').val()==4){	
		
		$jq('.BGWORLD').addClass('BGWORLD_jammu').removeClass('BGWORLD_home','BGWORLD_north','BGWORLD_allindia','BGWORLD_andaman');	
		
	}
	else if($jq('#ltcTypeId').val()==3){		
		//	$jq('.BGWORLD').addClass('BGWORLD_northeast');
		$jq('.BGWORLD').addClass('BGWORLD_north').removeClass('BGWORLD_home','BGWORLD_jammu','BGWORLD_allindia','BGWORLD_andaman');	
		
	}
	
	else if($jq('#ltcTypeId').val()==5){		
		$jq('.BGWORLD').addClass('BGWORLD_andaman').removeClass('BGWORLD_jammu').removeClass('BGWORLD_north').removeClass('BGWORLD_allindia').removeClass('BGWORLD_home');
		
	}
	else {		
		$jq('.BGWORLD').addClass('BGWORLD_allindia').removeClass('BGWORLD_home','BGWORLD_jammu','BGWORLD_north','BGWORLD_andaman');
		
	}
}
function encashOfEL(){
	if($jq('#encashTypeId option:selected').val()=='0'){
		if($jq('#encashmentDays').length > 0){
			$jq('#encashmentDays').val('');
		}
		$jq('#numOfDays').css('display','none');
	}else {
		$jq('#numOfDays').css('display','block');
	}
}
function ltcAdvanceDrawn(){
	if($jq('#ltcAdvance1').is(':checked')==true) {
		$jq('#ltcAdvDrawn').css('display','block');
	}else if($jq('#ltcAdvance2').is(':checked')==true){
		if($jq('#prevAdvanceDate').length > 0){
			$jq('#prevAdvanceDate').val('');
		}
		if($jq('#prevAmount').length > 0){
			$jq('#prevAmount').val('');
		}
		if($jq('#prevVisitPlace').length > 0){
			$jq('#prevVisitPlace').val('');
		}
		if($jq('#prevAdvanceReason').length > 0){
			$jq('#prevAdvanceReason').val('');
		}
		$jq('#ltcAdvDrawn').css('display','none');
	}
}
function clearLtcApprRequest(){
	$jq('#ltcTypeId').val('Select');
	$jq('#typeOfLtc').css('display','none');
	$jq('#typeOfLtcLables').css('display','none');
	$jq('#ltcBlockYearId').val('Select');
	$jq('#placeOfVisit').val('');
	$jq('#nearestRlyStation').val('');
	$jq('#departureDate').val('');
	$jq('#leaveRequestId').val('Select');
	$jq('#leaveDurationFrom').val('');
	$jq('#leaveDurationTo').val('');
	$jq('#encashTypeId').val('0');
	$jq('#numOfDays').css('display','none');
	$jq('#ltcAdvDrawn').css('display','none');
	$jq('#ltcAdvance1').attr('checked', false);
	$jq('#ltcAdvance2').attr('checked', false);
	$jq('#spouseEmploymentFlag1').attr('checked', false);
	$jq('#spouseEmploymentFlag2').attr('checked', false);
	$jq("form#ltcApprovalRequest INPUT[@name=familyId][type='checkbox']").attr('checked', false);
	$jq('#memName').val('');
	$jq('#memDOB').val('');
	$jq('#memAge').val('');
	$jq('#memRelation').val('');
	$jq('#returnDate').val('');
	$jq('#amountClaimed').val('');
	$jq('#noOfTickets').val('');
	$jq('#amountPerPerson').val('');

	$jq('#escortMemName').val('');
	$jq('#escortMemDOB').val('');
	$jq('#escortMemAge').val('');
	$jq('#escortMemRelation').val('');
	$jq('#noOfInfantTickets').val('');
	$jq('#amountPerEachInfant').val('');
	$jq('#visitPlace').val('');
}
function submitLtcApprRequest(type,referenceRequestID,typeValue,attachedLeave,leaveCancelRequestID){
	var count = 0;
	var ltcTypeId='';
	var status=true;
	var ltcMemberFlag=false;
	var errorMessage='';
	var ltcMembersJson={};
	var ltcEmpJson={};
	var qryStr='';
	var escortDetails='';
	var noChangeStatus=true;
	var familyString='';
//	added by bkr 12/04/2016 3 lines only
	$jq('#amountPerPerson').val('0.00');
	$jq('#amountClaimed').val('0.00');
	$jq('#amountPerEachInfant').val('0.00');
	//alert($jq('#leaveRequestId').val());
	//$jq('#leaveRequestId').val()==999;
	//alert($jq('#leaveRequestId').val());
	
	if(ltcApplicationBean!='' & typeValue=='ltcAdvanceAmendment'|| typeValue=='ltcApprovalAmendment') {
		if(ltcApplicationBean.ltcTypeId!=$jq('#ltcTypeId').val()){
			noChangeStatus=false;
		}
		if(ltcApplicationBean.ltcBlockYearId!=$jq('#ltcBlockYearId option:selected').val()){
			noChangeStatus=false;
		}
		if($jq('#placeOfVisit').is(':visible')){
				if(ltcApplicationBean.placeOfVisit!=$jq('#placeOfVisit').val()){
					noChangeStatus=false;	
				}
			}
		if($jq('#nearestRlyStation').is(':visible')){
			if(ltcApplicationBean.nearestRlyStation!=$jq('#nearestRlyStation').val()){
				noChangeStatus=false;	
			}
		}
		if($jq('#departureDate').is(':visible')){
			if(ltcApplicationBean.formatedDepartureDate!=$jq('#departureDate').val()){
				noChangeStatus=false;	
			}
		}
		if($jq('#returnDate').is(':visible')){
			if(ltcApplicationBean.formatedReturnDate!=$jq('#returnDate').val()){
				noChangeStatus=false;	
			}
		}
		if($jq('#leaveRequestId').is(':visible')){
			if(ltcApplicationBean.leaveRequestId!=$jq('#leaveRequestId').val()){
				noChangeStatus=false;	
			}
		}
		if($jq('#encashTypeId').is(':visible')){
			if(ltcApplicationBean.encashTypeId!=$jq('#encashTypeId').val()){
				noChangeStatus=false;	
			}
		}
		if($jq('#encashmentDays').is(':visible')){
			if(ltcApplicationBean.encashmentDays!=$jq('#encashmentDays').val()){
				noChangeStatus=false;	
			}
		}
		if($jq('#noOfTickets').is(':visible')){
			if(ltcApplicationBean.noOfTickets!=$jq('#noOfTickets').val()){
				noChangeStatus=false;	
			}
		}
		if($jq('#amountPerPerson').is(':visible')){
             Perperson=round2Fixed($jq('#amountPerPerson').val());
			$jq('#amountPerPerson').val(Perperson);
			if(ltcApplicationBean.amountPerPerson!=$jq('#amountPerPerson').val()){
				noChangeStatus=false;	
			}
		}
		if($jq('#noOfInfantTickets').is(':visible')){
			if(ltcApplicationBean.noOfInfantTickets!=$jq('#noOfInfantTickets').val()){
				noChangeStatus=false;	
			}
		}
		if($jq('#amountPerEachInfant').is(':visible')){
			PerInfant=round2Fixed($jq('#amountPerEachInfant').val());
			$jq('#amountPerEachInfant').val(PerInfant);
			
			if(ltcApplicationBean.amountPerEachInfant!=$jq('#amountPerEachInfant').val()){
				noChangeStatus=false;	
			}
		}
		if($jq('#amountClaimed').is(':visible')){
			if(ltcApplicationBean.amountClaimed!=$jq('#amountClaimed').val()){
				noChangeStatus=false;	
			}
		}
		$jq('#dataList >tbody tr').each(function(){
			if($jq(this).find('td').eq(0).find('input').is(':checked')){
				if(familyString!=''){familyString+=',';}
				familyString+=$jq(this).find('td').eq(0).find('input:checked').attr("id");
			}
		});
		if(ltcApplicationBean.familyMember!=familyString){
			noChangeStatus=false;	
		}
		if(noChangeStatus==true){
			errorMessage+="Please change atleast one value for amendment\n";
			if(status){
				$jq('#ltcTypeId').focus();
				status=false;
			}
		}
	}
	
	if(typeValue=='ltcAdvanceAmendment'|| typeValue=='ltcApprovalAmendment'){
		if($jq('#leaveRequestId').is(':visible') && attachedLeave== $jq('#leaveRequestId').val()){
			if(!(leaveCancelRequestID=='')){
				errorMessage+="Selected Leave is in cancellation state please attach new leave\n";
				if(status){
					$jq('#leaveRequestId').focus();
					status=false;
				}
				
			}
		}
		/*if($jq('#departureDate').val()!='' && typeValue=='ltcAdvanceAmendment'){
			if(compareDate(checkDate,$jq('#departureDate').val())) {
				errorMessage+="LTC AdvanceAmendment Request should send before "+ltcAdvanceDays+ " days of departure date\n";
				status=false;
			}
		}*/
	}else{
		if($jq('#leaveRequestId').is(':visible') && $jq('#result').find('div').find('div').text()=='Cancel Request initiated for Attached Leave'){
			errorMessage+="Selected Leave is in cancellation state please attach new leave\n";
			if(status){
				$jq('#leaveRequestId').focus();
				status=false;
			}
		}
	}
	
	if($jq('#ltcTypeId').is(':visible') && $jq('#ltcTypeId').val()=='Select'){
		errorMessage+="Please Select Type of LTC\n";
		if(status){
			$jq('#ltcTypeId').focus();
			status=false;
		}
	}	
	if($jq('#ltcBlockYearId').is(':visible') && $jq('#ltcBlockYearId option:selected').val()=='Select'){
		errorMessage+="Please Select Block Year\n";
		if(status){
			$jq('#ltcBlockYearId').focus();
			status=false;
		}
	}
	if($jq('#placeOfVisit').is(':visible') && $jq('#placeOfVisit').val()==''){
		errorMessage+="Please Enter Place of Visit\n";
		if(status){
			$jq('#placeOfVisit').focus();
			status=false;
		}
	}
	
	
	if($jq('#nearestRlyStation').is(':visible') &&  $jq('#nearestRlyStation').val()==''){
		errorMessage+="Please Enter Nearest Railway Station\n";
		if(status){
			$jq('#nearestRlyStation').focus();
			status=false;
		}
	}
	if($jq('#departureDate').is(':visible') && $jq('#departureDate').val()==''){
		errorMessage+="Please Enter Date of Departure\n";
		if(status){
			$jq('#departureDate').focus();
			status=false;
		}
	}
	if($jq('#returnDate').is(':visible') && $jq('#returnDate').val()==''){
		errorMessage+="Please Enter Date of Return\n";
		if(status){
			$jq('#returnDate').focus();
			status=false;
		}
	}
	if(!$jq('#returnDate').val()=='' && !$jq('#departureDate').val()=='') {
		if(!compareDate($jq('#returnDate').val(),$jq('#departureDate').val())) {
			errorMessage+="Invalid dates \n";
			status=false;
		}
		//alert(  addMonths($jq('#departureDate').val(),6));
	}
	
	
	if($jq('#leaveRequestId').is(':visible') && $jq('#leaveRequestId option:selected').val()==''){
		/*added by bkr  06/04/2016*/
		$jq('#encashmentDays').val()=='9999';
		/*commented by bkr 06/04/2016*/
		/*errorMessage+="Please Select Approved Leave\n";
		if(status){
			$jq('#leaveRequestId').focus();
			status=false;
		}*/
	}
	
	
	/*if($jq('#encashTypeId').is(':visible')){
		if($jq('#encashmentDays').val()=='') {
			//$jq('#encashmentDays').val('');
			errorMessage+="Please Enter No. of Encashment Days...(Enter 0 If not Required)\n";
			status=false;
		}
	}*/
	
	if(type=='ltcAdvance' || type=='advance'){
		if($jq('#amountPerPerson').val()!=''){
		Perperson=round2Fixed($jq('#amountPerPerson').val());
		$jq('#amountPerPerson').val(Perperson);}
		if($jq('#amountPerEachInfant').val()!=''){
			PerInfant=round2Fixed($jq('#amountPerEachInfant').val());
			$jq('#amountPerEachInfant').val(PerInfant);}
		
		if($jq('#amountPerPerson').val()==''){
			errorMessage+="Please Enter Amount per Person\n";
			if(status){
				$jq('#amountPerPerson').focus();
				status=false;
			}
		}
		if($jq('#noOfInfantTickets').val()!='' && $jq('#amountPerEachInfant').val()==''){
			errorMessage+="Please Enter Infant Amount\n";
			if(status){
				$jq('#amountPerEachInfant').focus();
				status=false;
			}
		}
		/*if($jq('#departureDate').val()!=''){
			if(compareDate(checkDate,$jq('#departureDate').val())) {
				errorMessage+="LTC Advance Request should send before "+ltcAdvanceDays+ " days of departure date\n";
				status=false;
			}
		}*/
		
	}
	
	var status1 = true;
	for(var i=0;i<LtcFamilyMemberList.length;i++) {
		  if($jq('#'+LtcFamilyMemberList[i].id).attr('checked') && (parseInt(LtcFamilyMemberList[i].age) <= 12)) {
		       status1 =  false;
		   }
       }
	if(!status1)
	for(var i=0;i<LtcFamilyMemberList.length;i++) {
		  if($jq('#'+LtcFamilyMemberList[i].id).attr('checked') && (parseInt(LtcFamilyMemberList[i].age) > 12)) {
		       status1 =  true;
	     }
		}
	if(!status1){
	      errorMessage+="Please select Atleast One person\n";
	       status=false;
	}
	
	var k=0;
	$jq("#dataList tr:not(:first)").each(function() {
		if($jq(this).find("td").eq(0).find(":input:checkbox").is(':checked')){
			ltcMembersJson[k]=$jq(this).find("td").eq(0).find(":input:checkbox")[0].id
			k++;
			ltcMemberFlag=true;
		}		
	});
	if(!ltcMemberFlag){
		errorMessage+="Please Check Family Members for Ltc\n";
		status=false;
	}
	///Escort Alert
	$jq("#dataList tr:last").each(function() {
	$jq(this).find('td').each(function(){
		if($jq(this).find('input:text').is(':visible') && $jq(this).find('input:text').val()=='')
		/*if($jq('#escortMemName').val()=='' || $jq('#escortMemDOB').val()=='' || $jq('#escortMemAge').val()=='')*/{
			errorMessage += "Please Enter EscortMember Details.\n";
			status=false;
		}
	});
	});
	//////
	if($jq('#escortMemName').val()!='' && $jq('#escortMemName').is(':visible')){
		escortDetails=$jq('#escortMemName').val();
		if($jq('#escortMemDOB').val()!=''){
			escortDetails+=","+$jq('#escortMemDOB').val();
		}
		if($jq('#escortMemAge').val()!=''){
			escortDetails+=","+$jq('#escortMemAge').val();
		}
		if($jq('#escortMemRelation').val()!=''){
			escortDetails+=","+$jq('#escortMemRelation').val();
		}
	}
	if($jq('#ltcTypeId').val()==2) {
		$jq('#nearestRlyStation').val(rlyStation);
		if($jq('#visitPlace').find('input:text').val()==''){
		$jq('#placeOfVisit').val(place);}else{
			$jq('#placeOfVisit').val($jq('#visitPlace').find('input:text').val());
		}
	}
	if($jq('#leaveRequestId').val()=='Select') {
		$jq('#leaveRequestId').val('');
	}
	if($jq('#noOfTickets').val()==''){
		var fullticket=0;
	}else{
		var fullticket=$jq('#noOfTickets').val();	
	}if($jq('#noOfInfantTickets').val()==''){
		var infantTicket=0;
	}else{
		var infantTicket=$jq('#noOfInfantTickets').val();
	}
	noOfPerson=parseInt(fullticket)+parseInt(infantTicket);
	if(k>0 && $jq('#noOfTickets').is(':visible') &&  k!=noOfPerson)
	{
		/**
		 * Check number of tickets and checked family members 
		 */
		errorMessage+="Number of family member is invalid\n";
		status = false;
	}
	
	//added rakesh/*
	/*if($jq('#ltcTypeId').val()==3){
		var fdate = $jq('#departureDate').val();
		var tdate = '30-Apr-2014';
		//alert(compareDate(fdate,tdate));
		alert(compareDates(fdate,'dd-MM',tdate,'dd-MM'));
		if(compareDate(fdate,tdate)){
			errorMessage+=" Date of Arrival and  DepartureDate  should be less than April 30";
			status = false;	
			
		}
		var fdate = new Date($jq('#departureDate').val().split('-')[2],getMonthID($jq('#departureDate').val().split('-')[1])-1,$jq('#departureDate').val().split('-')[0]);
		//var rdate = new Date($jq('#returnDate').val().split('-')[2],getMonthID($jq('#returnDate').val().split('-')[1])-1,$jq('#returnDate').val().split('-')[0]);
		
		var fmaxdate = new Date($jq('#departureDate').val().split('-')[2],'04','01');
		//var rmaxdate = new Date($jq('#returnDate').val().split('-')[2],'04','01');
		//alert(fdate>tdate);
		//if(fdate>fmaxdate || rdate>rmaxdate){
		if(fdate>fmaxdate ){
			errorMessage+=" Date of DepartureDate  should be less than or equal to April 30";
			status = false;	
			//$jq('#departureDate').val('');
	     	$jq('#returnDate').val('');
			//clearLtcApprRequest();
		}
		//alert(compareDate(fdate,tdate));
		alert(compareDates(fdate,'dd-MM',tdate,'dd-MM'));
		if(compareDate(fdate,tdate)){
			errorMessage+=" Date of Arrival and  DepartureDate  should be less than April 30";
			status = false;	
			
		}
		
		
		
		alert(compareDates(fdate,'dd-MM',tdate,'dd-MM'));
			if(compareDates(fdate,'dd-MM',tdate,'dd-MM')==-1){
				errorMessage+="DepartureDate ";
				status = false;	
			}
	//alert(compareDate(tdate,$jq('#departureDate').val()));
		
		alert(convertDate($jq('#departureDate').val())>dateLimit);
		if(convertDate($jq('#departureDate').val())>formatDate('30-Apr-2014','dd-NNN-yyyy'))
		{
			errorMessage+="DepartureDate ";
			status = false;	
		}
		
	}
	if($jq('#ltcTypeId').val()==4){
		var fdate = new Date($jq('#departureDate').val().split('-')[2],getMonthID($jq('#departureDate').val().split('-')[1])-1,$jq('#departureDate').val().split('-')[0]);
		//var rdate = new Date($jq('#returnDate').val().split('-')[2],getMonthID($jq('#returnDate').val().split('-')[1])-1,$jq('#returnDate').val().split('-')[0]);
		
		var fmaxdate = new Date($jq('#departureDate').val().split('-')[2],'05','18');
		//var rmaxdate = new Date($jq('#returnDate').val().split('-')[2],'05','18');
		//if(fdate>=fmaxdate || rdate>=rmaxdate){
		if(fdate>=fmaxdate ){
			errorMessage+=" Date of   DepartureDate  should be less than or equal to June 17 ";
			status = false;	
			$jq('#departureDate').val('');
	     	//$jq('#returnDate').val('');
			//clearLtcApprRequest();
		}
		
	}
	*/
	
	if(status){
		var requestDetails = {
				"ltcTypeId" : $jq('#ltcTypeId').val(),
				"addressId" : addId,
				"placeOfVisit" : $jq('#placeOfVisit').val(),
				//"placeOfVisit"  :$jq('#visitPlace').find('input:text').val(),
				"nearestRlyStation":$jq('#nearestRlyStation').val(),
				"departureDate":$jq('#departureDate').val(),
				"leaveRequestId":$jq('#leaveRequestId').val(),
				"encashTypeId":$jq('#encashTypeId').val(),
				"encashmentDays":$jq('#encashmentDays').val(),
				"prevAdvanceDate":$jq('#prevAdvanceDate').val(),
				"prevAmount":$jq('#prevAmount').val(),
				"prevVisitPlace":$jq('#prevVisitPlace').val(),
				"prevAdvanceReason":$jq('#prevAdvanceReason').val(),
				"ltcBlockYearId":$jq('#ltcBlockYearId').val(),
				"returnDate":$jq('#returnDate').val(),
				"amountClaimed":$jq('#amountClaimed').val(),
				"noOfTickets":$jq('#noOfTickets').val(),
				"amountPerPerson":$jq('#amountPerPerson').val(),
				"amountPerEachInfant":$jq('#amountPerEachInfant').val(),
				"noOfInfantTickets":$jq('#noOfInfantTickets').val(),
				"jsonValue":JSON.stringify(ltcMembersJson),
				"escortDetails":escortDetails,
				"type":type,
				"referenceRequestID":referenceRequestID ,
				"typeValue" : amendmentType,//global variable
				"id" : amendmentRequestId,//global variable
				"param":"submitRequest"
			};
			$jq.ajax( {
				type : "POST",
				url : "ltcApprovalRequest.htm",
				data : requestDetails,
				cache : false,
				success : function(html) {			
					$jq("#result").html(html);
					if($jq('.success').is(':visible')){	
						var requestType = $jq('#headTitle').html();
						var check=confirm(" "+requestType+" has been Successfully Sent...\nPreview "+requestType+" Form & print from here");
						if(check){
							//requestIDLTCReimb
							
							if(type=='advance'){
								document.forms[0].requestId.value = $jq.trim(requestIDLTCReimb);
								}
							else{
						document.forms[0].requestId.value = $jq.trim(requestIDLTC);}
						//document.forms[0].roleId.value = 'roleId';
					   document.forms[0].param.value = "requestDetails";
						document.forms[0].action = "workflowmap.htm";
						document.forms[0].submit();	
						}
						
					}
					clearLtcApprRequest();
				}
			});
			
	}else{
		alert(errorMessage);
	}
}
function daydiff(first, second) {
    return (second-first)/(1000*60*60*24);
}
function enableBlockYearDiv(){
	if($jq('#ltcBlockId option:selected').text()=='Four Year' || $jq('#ltcBlockId option:selected').text()=='Select'){
		$jq('#fourYearBlockId').val('Select');
		$jq('#blockYearDiv').css("display","none");
	}else {
		$jq('#blockYearDiv').css("display","block");
	}
}
function advanceEncashOfEL(){
	if($jq('#encashTypeId option:selected').val()==''){
		if($jq('#noOfDays').length > 0){
			$jq('#noOfDays').val('');
		}
		$jq('#numOfDays').css('display','none');
	}else {
		$jq('#numOfDays').css('display','block');
	}
}
//Ltc advance end
/**
 * LTC Reimbursement start
 */
function getLtcReimbursment(requestID,type) {
	document.forms[0].param.value = "ltcReimbursementHome";
	document.forms[0].type.value=type;
	document.forms[0].id.value=requestID;
	document.forms[0].action="ltcApprovalRequest.htm"
	document.forms[0].submit();	
}
function getLtcSettlement(requestID,type,issuedAmount,cdaAmount){
	if((issuedAmount!='' && cdaAmount!='')) {
		document.forms[0].param.value = "ltcReimbursementHome";
		document.forms[0].type.value=type;
		document.forms[0].id.value=requestID;
		document.forms[0].action="ltcApprovalRequest.htm"
		document.forms[0].submit();	
	}else {
		if(issuedAmount==''){
			alert('Finance Amount is not entered .\n Please Contact Finance to raise your Settlement Request.');
		}else{
		alert('CDA Amount is not entered .\n Please Contact Finance to raise your Settlement Request.');
		}
	}

}
function saveLTCReimbursement(){
	var status=true;
	var errorMessage='';
	var ltcMembersJson={};
	var journeyJson={};
	var mainJson={};
	
	if($jq('#unitFormation').val() == ''){
		errorMessage='Please Enter Unit Formation \n';
		if(status){
			$jq('#unitFormation').focus();
			status=false;
		}
	}
	if($jq('#claimPreferred').val() == ''){
		errorMessage+='Please Enter Claim preferred on \n';
		if(status){
			$jq('#claimPreferred').focus();
			status=false;
		}
	}
	var k=0;
	$jq("#Pagination tr:not(:first)").each(function() {
		if($jq(this).find("td").eq(0).find(":input:checkbox").is(':checked')){
			ltcMembersJson[k]=$jq(this).find("td").eq(0).find(":input:checkbox")[0].id
			k++;
			status=true;
		}		
	});
	if(JSON.stringify(ltcMembersJson).length==2){
		errorMessage+="Please Check Family Members for Ltc Reimbursement\n";
		status=false;
	}
	if($jq('#modeOfPayment').val() == ''){
		errorMessage+='Please Enter Mode of Payment \n';
		if(status){
			$jq('#modeOfPayment').focus();
			status=false;
		}
	}
	if($jq('#excessAmountFine').is(':visible') && $jq('#excessAmountFine').val()==''){
		errorMessage+="Please Enter Excess Amount Fine\n";
		if(status){
			$jq('#excessAmountFine').focus();
			status=false;
		}
	}
	if($jq('#MROPaidNo').is(':visible') && $jq('#MROPaidNo').val()==''){
		errorMessage+="Please Enter MRO Paid No\n";
		if(status){
			$jq('#MROPaidNo').focus();
			status=false;
		}
	}
	if($jq('#MROPaidDate').is(':visible') && $jq('#MROPaidDate').val()==''){
		errorMessage+="Please Enter MRO Paid Date\n";
		if(status){
			$jq('#MROPaidDate').focus();
			status=false;
		}
	}
	$jq("#journeyDetailsID tr:not(:first)").each(function() {
		if($jq(this).find("td").eq(0).find('input').val()=='') {
			errorMessage+="Please select start date \n";
			if(status) {
				$jq(this).find("td").eq(0).find('input').focus();
				status = false;
			}
		}
		if($jq(this).find("td").eq(1).find('input').val()=='') {
			errorMessage+="Please select start time \n";
			if(status) {
				$jq(this).find("td").eq(1).find('input').focus();
				status = false;
			}
		}
		if($jq(this).find("td").eq(2).find('input').val()=='') {
			errorMessage+="Please enter station \n";
			if(status) {
				$jq(this).find("td").eq(2).find('input').focus();
				status = false;
			}
		}
		if($jq(this).find("td").eq(3).find('input').val()=='') {
			errorMessage+="Please select end date \n";
			if(status) {
				$jq(this).find("td").eq(3).find('input').focus();
				status = false;
			}
		}
		if($jq(this).find("td").eq(4).find('input').val()=='') {
			errorMessage+="Please enter end time \n";
			if(status) {
				$jq(this).find("td").eq(4).find('input').focus();
				status = false;
			}
		}
		if($jq(this).find("td").eq(5).find('input').val()=='') {
			errorMessage+="Please enter station \n";
			if(status) {
				$jq(this).find("td").eq(5).find('input').focus();
				status = false;
			}
		}
		if($jq(this).find("td").eq(6).find('input').val()=='') {
			errorMessage+="Please enter distance \n";
			if(status) {
				$jq(this).find("td").eq(6).find('input').focus();
				status = false;
			}
		}
		if($jq(this).find("td").eq(7).find('input').val()=='') {
			errorMessage+="Please enter mode of travel \n";
			if(status) {
				$jq(this).find("td").eq(7).find('input').focus();
				status = false;
			}
		}
		if($jq(this).find("td").eq(8).find('input').val()=='') {
			errorMessage+="Please enter fare \n";
			if(status) {
				$jq(this).find("td").eq(8).find('input').focus();
				status = false;
			}
		}
		if($jq(this).find("td").eq(9).find('input').val()=='') {
			errorMessage+="Please enter no of persons \n";
			if(status) {
				$jq(this).find("td").eq(9).find('input').focus();
				status = false;
			}
		}
		if($jq(this).find("td").eq(11).find('input').val()=='') {
			errorMessage+="Please enter ticket nos \n";
			if(status) {
				$jq(this).find("td").eq(11).find('input').focus();
				status = false;
			}
		}
		if($jq(this).find("td").eq(12).find('select').val()=='') {
			errorMessage+="Please select journey type \n";
			if(status) {
				$jq(this).find("td").eq(12).find('select').focus();
				status = false;
			}
		}
	});
	if(status){
		var i=0
		$jq("#journeyDetailsID tr:not(:first)").each(function() {	
			var valuesJson={};
			valuesJson['dDate'] = $jq(this).find("td").eq(0).find('input').val();
			valuesJson['dTime'] = $jq(this).find("td").eq(1).find('input').val();
			valuesJson['dStation'] = $jq(this).find("td").eq(2).find('input').val();
			valuesJson['aDate'] = $jq(this).find("td").eq(3).find('input').val();
			valuesJson['aTime'] = $jq(this).find("td").eq(4).find('input').val();
			valuesJson['aStation'] = $jq(this).find("td").eq(5).find('input').val();
			valuesJson['distance'] = $jq(this).find("td").eq(6).find('input').val();
			valuesJson['modeOfTravel'] = $jq(this).find("td").eq(7).find('input').val();
			valuesJson['fare'] = $jq(this).find("td").eq(8).find('input').val();
			valuesJson['persons'] = $jq(this).find("td").eq(9).find('input').val();
			valuesJson['totalAmt'] = $jq(this).find("td").eq(10).find('input').val();
			valuesJson['ticketNo'] = $jq(this).find("td").eq(11).find('input').val();
			valuesJson['journyType'] = $jq(this).find("td").eq(12).find('select').val();
			journeyJson[i]=valuesJson;
			i++;
		});
		
		}
	mainJson["ltcMembers"]=ltcMembersJson;
	mainJson["journeyDetails"]=journeyJson;
	if(status){
	
		document.forms[0].param.value="submitRequest";
		document.forms[0].jsonValue.value=JSON.stringify(mainJson);
		$jq.post('ltcApprovalRequest.htm?totalAmount='+$jq('#totAmt').html()+'&leaveRequestId='+leaveRequestId,$jq('#ltcApprovalRequest').serialize(),  
		           function(data){ 
		            	 $jq('#result').html(data);
		            	 if($jq('.success').is(':visible')){
		            		 var requestType = $jq('#headTitle').html();
								var check=confirm( ""+requestType+" has been Successfully Sent...\n\nPlease click ok to 'Preview "+requestType+" Application Form 'Take print' from here\n\n");
								if(check){
								document.forms[0].requestId.value = $jq.trim(requestIDLTCReimb);
								//document.forms[0].roleId.value = 'roleId';
							   document.forms[0].param.value = "requestDetails";
								document.forms[0].action = "workflowmap.htm";
								document.forms[0].submit();	
								}
		            	 clearLTCReimbursement();
		            	 $jq("#journeyDetailsID").hide();
		            	 $jq('#journeyDetailsID').parent().prev().hide();
		            	 $jq('#ltcReimburseButtons').hide();
		           } }
		      );
	}else{
		alert(errorMessage);
	}
}
function clearLTCReimbursement(){
	$jq('#unitFormation').val('');
	$jq('#claimPreferred').val('');
	$jq('#modeOfPayment').val('');
	$jq('#totAmt').html('');
	$jq("form#ltcApprovalRequest INPUT[@name=familymemberschk][type='checkbox']").attr('checked', false);
	
	
	$jq("#journeyDetailsID tr:not(:first)").each(function() {	
		$jq(this).find("td").eq(0).find('input').val('');
		$jq(this).find("td").eq(1).find('input').val('');
		$jq(this).find("td").eq(2).find('input').val('');
		$jq(this).find("td").eq(3).find('input').val('');
		$jq(this).find("td").eq(4).find('input').val('');
		$jq(this).find("td").eq(5).find('input').val('');
		$jq(this).find("td").eq(6).find('input').val('');
		$jq(this).find("td").eq(7).find('input').val('');
		$jq(this).find("td").eq(8).find('input').val('');
		$jq(this).find("td").eq(9).find('input').val('');
		$jq(this).find("td").eq(10).find('input').val('');
		$jq(this).find("td").eq(11).find('input').val('');
		$jq(this).find("td").eq(12).find('select').find('option').remove().end().append($jq("<option></option>").attr("value",'').text('Select'));
	});
}
function funcreatenewJourneyRow(idvalue)
{
	$jq.post('ltcApprovalRequest.htm?&param=serverHit',function(data){});
	count++;
	
	var row = "<tr id=row"+count+">";
	row += "<td>";
		row += "<input type=text readonly=readonly style=height:16px;width:60px;font-size:9px;font-weight:bold; id=startDate"+count+" onfocus=javascript:Calendar.setup({inputField:'startDate"+count+"',ifFormat:'%d-%b-%Y',showsTime:false,button:'startDate"+count+"',step:1}); onchange=javascript:checkDates('startDate"+count+"'); />";
	row += "</td>";
	
	row += "<td>";
		row += "<input type=text name=startTime readonly=readonly class=startTime id=startTime"+count+" style=width:37px  onmouseover=javascript:timePicker('startTime'); />";
	row += "</td>";
	
	row += "<td>";
		row += "<input type=text id=startStation"+count+" style=width:55px />";
	row += "</td>";

	row += "<td>";
		row += "<input type=text readonly=readonly id=endDate"+count+" style=height:16px;width:60px;font-size:9px;font-weight:bold;  onfocus=javascript:Calendar.setup({inputField:'endDate"+count+"',ifFormat:'%d-%b-%Y',showsTime:false,button:'endDate"+count+"',step:1}); onchange=javascript:checkDates('endDate"+count+"'); />";
																												   				
	row += "</td>";
	
	row += "<td>";
		row += "<input type=text name=endTime readonly=readonly class=endTime id=endTime"+count+" style='width:37px'  onmouseover=javascript:timePicker('endTime'); />";
	row += "</td>";

	row += "<td>";
		row += "<input type=text id=endStation"+count+" style=width:55px />";
	row += "</td>";
	
	row += "<td>";
		row += "<input type=text id=distance"+count+" style=width:55px onkeypress='javascript:return checkInt(event)'; />";			
	row += "</td>";
	
	row += "<td>";
		row += "<input type=text id=modeOfTravel"+count+" style=width:100px />";			
	row += "</td>";
	
	row += "<td>";
		row += "<input type=text id=farePerPerson"+count+" style=width:49px onchange=javascript:getTotal() onkeypress='javascript:return checkInt(event)';/>";			
	row += "</td>";

	row += "<td>";
		row += "<input type=text id=noOfPersons"+count+" style=width:56px onchange=javascript:getTotal() onkeypress='javascript:return checkInt(event)';/>";			
	row += "</td>";

	row += "<td>";
		row += "<input type=text id=totalAmount"+count+" style=width:52px onchange=javascript:totalAdjustment() onkeypress='javascript:return checkInt(event)';/>";			
	row += "</td>";

	row += "<td>";
		row += "<input type=text id=ticketNo"+count+" style=width:70px onkeypress=javascript:return checkInt(event); />";			
	row += "</td>";
	
	
	row += "<td>";
	row += "<select style=width:70px id=JourneyType"+count+">";
			row += "<option value=>Select</option>";
			row += "<option value=O>Onward</option>";
			row += "<option value=R>Return</option>";
	row += "</td>";
	
	row += "<td>";
		row += "<input type=button id=add"+count+" class=smallbtn  value=+ onclick=javascript:funcreatenewJourneyRow('journeyDetailsID'); />";
	row += "</td>";

	row += "<td>";
		row += "<input type=button id=del"+count+" class=smallbtn  value=- onclick=javascript:deleteJourneyDetailsRow(this,\'"+idvalue+"\'); />";
	row += "</td>";
	
row += "</tr>";

$jq("#row"+(count-1)).after(row);
}
function deleteJourneyDetailsRow(node,tableID)
{
	count--;
	var dynRow = document.getElementById(tableID);
	var index=node.parentNode.parentNode.rowIndex;
	
	 
	var del=confirm("Do you want to delete this row?");
	if(del==true) 
	{
		if(index>=0)
		{
			dynRow.deleteRow(index);
		}
		
	}
	var length1 = dynRow.rows.length;
	
	if(length1=='4')
	{
		funcreatenewJourneyRow(tableID);
	}
	
	getTotal();
}

function LTCNOOFDAYS(){
	//alert("welcome noOfvacationdays");
	//alert($jq('#noOfvacationdays').val());
	$jq('#noOfvacationdays').val('5');
}



function checkApplingYear(typevalue) {
	checkApplingBlockYear();
	checkApplingYear1(typevalue);
	var errorMessage ='';
	
	var errorMessage ='';
	//alert(typevalue);
	//LTCNOOFDAYS();
	
	
	
	/*var departureYear;
	var returnYear;
	var status=false;
	var errorMessage ='';
	if($jq('#ltcBlockYearId').val()=='Select') {
		status=false;
		$jq('#ltcBlockYearId').focus();
		if(!($jq('#departureDate').val()=='' && $jq('#returnDate').val()=='')){
		errorMessage+="Select block year first\n";
		}
		$jq('#departureDate').val('');
		$jq('#returnDate').val('');
	}else if($jq('#departureDate').val()!='' || $jq('#returnDate').val()!='') {
		var blockYear=$jq('#ltcBlockYearId option:selected').text().split('-').length
		var block = $jq('#ltcBlockYearId option:selected').text().split('-');
		departureYear = $jq("#departureDate").val().split('-')[2];
		returnYear = $jq("#returnDate").val().split('-')[2];
		if(blockYear==2 && (parseInt(block[0].substring(block[0].length-2,0)+block[1])-parseInt(block[0]))==1) {
			*//**
			 * Block year list is 2 year block.
			 * Ex:If user choose 2010-11 is the block year then his departure and return date should be between 01-Jan-2010 to 31-Dec-2012
			 *//*
			if($jq('#departureDate').val()!='') {
				if(parseInt(departureYear)==parseInt(block[0]) || parseInt(departureYear)==parseInt(parseInt(block[0])+1)) {
					status=true;
				}else {
					status=false;
					errorMessage+="Departure date year should be either "+block[0]+" or "+parseInt(parseInt(block[0])+1)+" or "+parseInt(parseInt(block[0])+2)+" \n";
					$jq('#departureDate').val('');
				}
			}
			if($jq('#returnDate').val()!='') {
				if(parseInt(returnYear)>=parseInt(block[0]) || parseInt(returnYear)>=parseInt(parseInt(block[0])+1)) {
					status=true;
				}else {
					status=false;
					errorMessage+="Rerutn date year should be either "+block[0]+" or "+parseInt(parseInt(block[0])+1)+" or "+parseInt(parseInt(block[0])+2)+" or "+parseInt(parseInt(block[0])+3)+" \n";
					$jq('#returnDate').val('');
				}
			}
		}else if((parseInt(block[0].substring(block[0].length-2,0)+block[1])-parseInt(block[0]))==3){
			*//**
			 * Block year list is 4 year block.
			 * So, departure and return dates must be in last year of block year
			 *//*
			if($jq('#departureDate').val()!='') {
				if(parseInt(departureYear)==parseInt(block[0].substring(block[0].length-2,0)+block[1])) {
					status=true;
				}else {
					status=false;
					errorMessage+="Departure date year should be in the year "+(block[0].substring(block[0].length-2,0)+block[1])+" \n";
					$jq('#departureDate').val('');
				}
			}
			if($jq('#returnDate').val()!='') {
				if(parseInt(returnYear)>=parseInt(block[0].substring(block[0].length-2,0)+block[1])) {
					status=true;
				}else {
					status=false;
					errorMessage+="Return date year should be in the year "+(block[0].substring(block[0].length-2,0)+block[1])+" \n";
					$jq('#returnDate').val('');
				}
			}
			
		}else {
			*//**
			 * Block year list is 1 year block
			 * Ex: if block year is 2012 then his date of departure,return  should be between 01-Jan-2012 to 31-Dec-2013
			 *//*
			if($jq('#departureDate').val()!='') {
				if(parseInt(departureYear)==parseInt(block[0])) {
					status=true;
				}else {
					status=false;
					errorMessage+="Departure date year should be either "+block[0]+" or "+parseInt(parseInt(block[0])+1)+" \n";
					$jq('#departureDate').val('');
				}
			}
			if($jq('#returnDate').val()!='') {
				if(parseInt(returnYear)>=parseInt(block[0])) {
					status=true;
				}else {
					status=false;
					errorMessage+="Rerutn date year should be either "+block[0]+" or "+parseInt(parseInt(block[0])+1)+" \n";
					$jq('#returnDate').val('');
				}
			}
			
		}
	}*/
	if(!$jq('#amountClaimed').is(':input') || typevalue=='ltcAdvanceAmendment' || typevalue=='ltcApprovalAmendment' ) {
		if($jq('#leaveRequestId').is(':visible') && $jq('#leaveRequestId option:selected').val()!=''){
			amendmentLeaves=$jq('#approvedLeaveList').html();
			amendmentLeaveSelected=$jq('#leaveRequestId').val();
			$jq('#changeLeaveFieldset').show();
			$jq('#changeLeaveDiv').show();
		}
	}
	if($jq('#departureDate').val()!='' && $jq('#returnDate').val()!='') {
		if(!compareScriptDate(addMonths($jq('#departureDate').val(),6),addMonths($jq('#returnDate').val(),0))) {
			$jq('#returnDate').val('');
			errorMessage+="Return journey should not exceed 6 months";
			status = false;
		}
	}
	if($jq('#ltcTypeId').val()== 4){
		var fdate = new Date($jq('#departureDate').val().split('-')[2],getMonthID($jq('#departureDate').val().split('-')[1])-1,$jq('#departureDate').val().split('-')[0]);
		//var rdate = new Date($jq('#returnDate').val().split('-')[2],getMonthID($jq('#returnDate').val().split('-')[1])-1,$jq('#returnDate').val().split('-')[0]);
		
		//var fmaxdate = new Date($jq('#departureDate').val().split('-')[2],'2016','05','18');
		
		var fmaxdate = new Date('2016','08','27');
		//var rmaxdate = new Date($jq('#returnDate').val().split('-')[2],'05','18');
		//if(fdate>=fmaxdate || rdate>=rmaxdate){
		if(fdate>fmaxdate ){
			errorMessage+=" Date of   DepartureDate  should be less than or equal to 2016 September 27 ";
			status = false;	
			$jq('#departureDate').val('');
	     	//$jq('#returnDate').val('');
			//clearLtcApprRequest();
		}
	}
	if($jq('#ltcTypeId').val()== 3){
		var fdate = new Date($jq('#departureDate').val().split('-')[2],getMonthID($jq('#departureDate').val().split('-')[1])-1,$jq('#departureDate').val().split('-')[0]);
		//var rdate = new Date($jq('#returnDate').val().split('-')[2],getMonthID($jq('#returnDate').val().split('-')[1])-1,$jq('#returnDate').val().split('-')[0]);
		
		//var fmaxdate = new Date($jq('#departureDate').val().split('-')[2],'2016','05','18');
		
		var fmaxdate = new Date('2016','08','27');
		//var rmaxdate = new Date($jq('#returnDate').val().split('-')[2],'05','18');
		//if(fdate>=fmaxdate || rdate>=rmaxdate){
		if(fdate>fmaxdate ){
			errorMessage+=" Date of   DepartureDate  should be less than or equal to 2016 September 27 ";
			status = false;	
			$jq('#departureDate').val('');
	     	//$jq('#returnDate').val('');
			//clearLtcApprRequest();
		}
	}
	
	
	if($jq('#ltcTypeId').val()== 5){
		var fdate = new Date($jq('#departureDate').val().split('-')[2],getMonthID($jq('#departureDate').val().split('-')[1])-1,$jq('#departureDate').val().split('-')[0]);
		//var rdate = new Date($jq('#returnDate').val().split('-')[2],getMonthID($jq('#returnDate').val().split('-')[1])-1,$jq('#returnDate').val().split('-')[0]);
		var fmaxdate = new Date('2016','08','27');
		//var fmaxdate = new Date($jq('#departureDate').val().split('-')[2],'2016','08','27');
		//var rmaxdate = new Date($jq('#returnDate').val().split('-')[2],'04','01');
		//alert(fdate>tdate);
		//if(fdate>fmaxdate || rdate>rmaxdate){
		
		//compareDate(new Date('20') , $jq('#DepartureDate').val())
		
		if(fdate>fmaxdate ){
			errorMessage+=" Date of   DepartureDate  should be less than or equal to 2016 September 27";
			status = false;	
			$jq('#departureDate').val('');
	     	//$jq('#returnDate').val('');
			//clearLtcApprRequest();
		}
	
	}
	
	
	if(status && $jq('#departureDate').val()!='' && $jq('#returnDate').val()!='') {
		$jq.ajax( {
			type : "POST",
			url : "ltcApprovalRequest.htm",
			data : "param=leaves&departureDate=" + $jq("#departureDate").val()+"&returnDate="+$jq('#returnDate').val(),
			cache : false,
			success : function(html) {
				$jq("#approvedLeaveList").html(html);
				$jq("#ajaxBusy").remove();
				setLeaveList();
				if($jq('#changeLeaveDiv').is(':visible')){
					ltcLeaveAmendment();
				}
			}
		});
		
	
	}else if(errorMessage!=''){
		alert(errorMessage);
	}
}
function checkApplingBlockYear(){
	var yearId="";
	var status =true;
	if(status) {
		var requestDetails = {
				"id" :$jq('#ltcBlockYearId').val(),
				//"departureDate" :$jq("#departureDate").val(),
				"param" :"ltcBlockYearId"
			}
			$jq.post('ltcApprovalRequest.htm', requestDetails, function(html) {
				$jq("#type").html(html);
			});
	}	 
}
/*this function added by bkr 12/04/2016*/
function monthtonum(a)
{
	//alert("before1"+a);
	if(a=="Jan"){return  1;}
	if(a=="Feb"){return  2;}
	if(a=="Mar"){return  3;}
	if(a=="Apr"){return  4;}
	if(a=="May"){return  5;}
	if(a=="Jun"){return  6;}
	if(a=="Jul"){return  7;}
	if(a=="Aug"){return  8;}
	if(a=="Sep"){return  9;}
	if(a=="Oct"){return  10;}
	if(a=="Nov"){return  11;}
	if(a=="Dec"){return  12;}
}

function checkApplingYear1(typevalue){
	
/*	
 * added and commented by bkr 11/04/2016
 * if($jq('#returnDate').val()!=''){
		//alert("welcome");
		if($jq('#departureDate').val()!=''){
	//alert("welcome1");
			//alert($jq('#returnDate').val()-$jq('#departureDate').val());
			//$jq('#noOfvacationdays').val('5');
			var firstDate=$jq('#returnDate').val();
			var	secondDate=$jq('#departureDate').val();
			//alert(firstDate-secondDate);
			var startDay = new Date(firstDate);
			//alert(startDay);
            var endDay = new Date(secondDate);
           // alert(startDay-endDay);
            var millisecondsPerDay = 1000 * 60 * 60 * 24;

            var millisBetween = startDay.getTime() - endDay.getTime();
            var days = millisBetween / millisecondsPerDay;

            // Round down.
            alert( Math.floor(days));
			
		}
	
	
	}
	*/
	
	/* added by bkr 12/04/2016 */
	
	if($jq('#returnDate').val()!=''){
		if($jq('#departureDate').val()!=''){
			var firstDate=$jq('#returnDate').val();
			var	secondDate=$jq('#departureDate').val();
			
			var dd1=firstDate.substring(0,2);
			var mm1=firstDate.substring(3,6);
			var yy1=firstDate.substring(7,11);
			var dd2=secondDate.substring(0,2);
			var mm2=secondDate.substring(3,6);
			var yy2=secondDate.substring(7,11);
			mm1=monthtonum(mm1);
			mm2=monthtonum(mm2);
			firstDate=mm1+"/"+dd1+"/"+yy1;
			secondDate=mm2+"/"+dd2+"/"+yy2;
			var startDay = new Date(firstDate);
			 var endDay = new Date(secondDate);
			 var millisecondsPerDay = 1000 * 60 * 60 * 24;
	            var millisBetween = startDay.getTime() - endDay.getTime();
	            var days = millisBetween / millisecondsPerDay;
			var diffdays=Math.floor(days);
	            $jq('#encashmentDays').val(diffdays);
		}
		
	}
	
	
	
	var year = ltcExtBlockYear;
	var departureYear;
	var returnYear;
	var status=false;
	var errorMessage ='';
	if($jq('#ltcBlockYearId').val()=='Select') {
		status=false;
		$jq('#ltcBlockYearId').focus();
		if(!($jq('#departureDate').val()=='' && $jq('#returnDate').val()=='')){
		errorMessage+="Select block year first\n";
		}
		$jq('#departureDate').val('');
		$jq('#returnDate').val('');
	}else if($jq('#departureDate').val()!='' || $jq('#returnDate').val()!='') {
		var blockYear=$jq('#ltcBlockYearId option:selected').text().split('-').length
		var block = $jq('#ltcBlockYearId option:selected').text().split('-');
		departureYear = $jq("#departureDate").val().split('-')[2];
		returnYear = $jq("#returnDate").val().split('-')[2];
		if(blockYear==2 && (parseInt(block[0].substring(block[0].length-2,0)+block[1])-parseInt(block[0]))==1) {
			
			 // Block year list is 2 year block.
			 //Ex:If user choose 2010-11 is the block year then his departure and return date should be between 01-Jan-2010 to 31-Dec-2012
			
			if($jq('#departureDate').val()!='') {
				if(parseInt(departureYear)==parseInt(block[0]) || parseInt(departureYear)==parseInt(parseInt(block[0])+1) || parseInt(departureYear)==year ) {
					status=true;
				}else {
					status=false;
					errorMessage+="Departure date year should be either "+block[0]+" or "+parseInt(parseInt(block[0])+1)+" or "+parseInt(parseInt(block[0])+2)+" \n";
					$jq('#departureDate').val('');
				}
			}
			if($jq('#returnDate').val()!='') {
				if(parseInt(returnYear)>=parseInt(block[0]) || parseInt(returnYear)>=parseInt(parseInt(block[0])+1) || parseInt(returnYear)==year || parseInt(returnYear)==parseInt(year)+1) {
					status=true;
				}else {
					status=false;
					errorMessage+="Rerutn date year should be either "+block[0]+" or "+parseInt(parseInt(block[0])+1)+" or "+parseInt(parseInt(block[0])+2)+" or "+parseInt(parseInt(block[0])+3)+" \n";
					$jq('#returnDate').val('');
				}
			}
		}else if((parseInt(block[0].substring(block[0].length-2,0)+block[1])-parseInt(block[0]))==3){
		
			 // Block year list is 4 year block.
			 // So, departure and return dates must be in last year of block year
			
			if($jq('#departureDate').val()!='') {
				if(parseInt(departureYear)==parseInt(block[0].substring(block[0].length-2,0)+block[1]) || parseInt(departureYear)==year) {
					status=true;
				}
				//departure and return dates must between   in Block year start and last year::new check added by Rakesh
				else if(parseInt(block[0]) <= parseInt(departureYear) &&  parseInt(departureYear) <= parseInt(block[0].substring(block[0].length-2,0)+block[1])){
					status = true;
				} else {
					status=false;
					errorMessage+="Departure date year should be in the year "+(block[0].substring(block[0].length-2,0)+block[1])+" \n";
					$jq('#departureDate').val('');
				}
			}
			if($jq('#returnDate').val()!='') {
				if(parseInt(returnYear)>=parseInt(block[0].substring(block[0].length-2,0)+block[1]) || parseInt(returnYear)==year) {
					status=true;
				}
				//departure and return dates must between   in Block year start and last year::new check added by Rakesh
				else if(parseInt(block[0]) <= parseInt(returnYear) &&  parseInt(returnYear) <= parseInt(block[0].substring(block[0].length-2,0)+block[1])){
					status = true;
				}
				else {
					status=false;
					errorMessage+="Return date year should be in the year "+(block[0].substring(block[0].length-2,0)+block[1])+" \n";
					$jq('#returnDate').val('');
				}
			}
			
		}else {
			
			 // Block year list is 1 year block
			 // Ex: if block year is 2012 then his date of departure,return  should be between 01-Jan-2012 to 31-Dec-2013
			
			if($jq('#departureDate').val()!='') {
				if(parseInt(departureYear)==parseInt(block[0]) || parseInt(departureYear)==year ||  parseInt(departureYear)==parseInt(year)+1) {
					status=true;
				}else {
					status=false;
					errorMessage+="Departure date year should be either "+block[0]+" or "+parseInt(parseInt(block[0])+1)+" \n";
					$jq('#departureDate').val('');
				}
			}
			if($jq('#returnDate').val()!='') {
				if(parseInt(returnYear)>=parseInt(block[0]) ||  parseInt(departureYear)==year || parseInt(departureYear)==parseInt(year)+1) {
					status=true;
				}else {
					status=false;
					errorMessage+="Rerutn date year should be either "+block[0]+" or "+parseInt(parseInt(block[0])+1)+" \n";
					$jq('#returnDate').val('');
				}
			}
			
		}
	}
	if(!$jq('#amountClaimed').is(':input') || typevalue=='ltcAdvanceAmendment' || typevalue=='ltcApprovalAmendment' ) {
		if($jq('#leaveRequestId').is(':visible') && $jq('#leaveRequestId option:selected').val()!=''){
			amendmentLeaves=$jq('#approvedLeaveList').html();
			amendmentLeaveSelected=$jq('#leaveRequestId').val();
			$jq('#changeLeaveFieldset').show();
			$jq('#changeLeaveDiv').show();
		}
	}
	/*if($jq('#departureDate').val()!='' && $jq('#returnDate').val()!='') {
		if(!compareScriptDate(addMonths($jq('#departureDate').val(),6),addMonths($jq('#returnDate').val(),0))) {
			$jq('#returnDate').val('');
			errorMessage+="Return journey should not exceed 6 months";
			status = false;
		}
	}*/
	
	if(status && $jq('#departureDate').val()!='' && $jq('#returnDate').val()!='') {
		$jq.ajax( {
			type : "POST",
			url : "ltcApprovalRequest.htm",
			data : "param=leaves&departureDate=" + $jq("#departureDate").val()+"&returnDate="+$jq('#returnDate').val(),
			cache : false,
			success : function(html) {
				$jq("#approvedLeaveList").html(html);
				$jq("#ajaxBusy").remove();
				setLeaveList();
				if($jq('#changeLeaveDiv').is(':visible')){
					ltcLeaveAmendment();
				}
			}
		});
		
	
	}else if(errorMessage!=''){
		alert(errorMessage);
	}
	
}






function checkSelfApplying() {
	//alert("check4");
	var nonInfantTickets=0;
	var infantTickets=0;
	noOfPerson = 0;
	var status = true;
	if(!$jq('#encashTypeId').is(':visible')){
		//$jq('#encashTypeId').val('');
		}else{
			advanceEncashOfEL();
		}
		if(!$jq('#encashmentDays').is(':visible')){
		//$jq('#encashmentDays').val('');
		}
	$jq('#leaveType').attr("style","display:none;");
	$jq('#approvedLeave').attr("style","display:none;");
	
	for(var i=0;i<LtcFamilyMemberList.length;i++) {
		if(LtcFamilyMemberList[i].relation=='SELF' && $jq('#'+LtcFamilyMemberList[i].id).attr('checked') && $jq('#returnDate').is(":visible")) {
			$jq('#leaveType').attr("style","display:block;");
			advanceEncashOfEL();
			$jq('#approvedLeave').attr("style","display:block;");
			if($jq('#departureDate').val()!='' && $jq('#returnDate').val()!='' && !$jq('#leaveRequestId').is(':visible')) {
				$jq.ajax( {
					type : "POST",
					url : "ltcApprovalRequest.htm",
					data : "param=leaves&departureDate=" + $jq("#departureDate").val()+"&returnDate="+$jq('#returnDate').val(),
					cache : false,
					success : function(html) {
						$jq("#approvedLeaveList").html(html);
						$jq("#ajaxBusy").remove();
						setLeaveList();
					}
				});
				
			
			}
		}
		if($jq('#'+LtcFamilyMemberList[i].id).attr('checked')) {
			//if(parseInt(LtcFamilyMemberList[i].age) <= 12) {
			if(parseInt(LtcFamilyMemberList[i].age) < 12) {	
				infantTickets = parseInt(infantTickets)+1;
				
			}else if(parseInt(LtcFamilyMemberList[i].age) >= 12) {
				nonInfantTickets = parseInt(nonInfantTickets)+1;
			}
		}
	}
	
	if(!$jq('#approvedLeave').is(':visible')){
		$jq('#leaveRequestId').val('');
	}
	if(parseInt(infantTickets)>0) {
		$jq('#noOfInfantTickets').val(infantTickets);
		noOfPerson = parseInt(infantTickets);
	}else {
		$jq('#noOfInfantTickets').val('');
		$jq('#amountPerEachInfant').val('');
	}
	if(parseInt(nonInfantTickets)>0) {
		$jq('#noOfTickets').val(nonInfantTickets);
		noOfPerson = parseInt(infantTickets) + parseInt(nonInfantTickets);
	}else {
		$jq('#noOfTickets').val('');
	}
	if($jq('#noOfInfantTickets').val()==''){
		$jq('#amountPerEachInfant').attr('readonly','readonly');
	}else {
		$jq('#amountPerEachInfant').attr('readonly',false);
	}
	
	calculateAdvance();
	//checkLtcAvail();
}

function setLeaveList() {
	document.getElementById('leaveRequestId').options.length = 1;
	document.getElementById('leaveRequestId').options[0].text = 'Select';
	document.getElementById('leaveRequestId').options[0].value = '';
	for(var i=1;i<=ltcLeaveTypeList.length;i++) {
		document.getElementById('leaveRequestId').options.length=i+1;
		document.getElementById('leaveRequestId').options[i].text = ltcLeaveTypeList[i-1].name;
		document.getElementById('leaveRequestId').options[i].value = ltcLeaveTypeList[i-1].key;
	}
}
function getTotal() {
	var grandTotal=0;
	var dynRow=document.getElementById("journeyDetailsID");
	var length=dynRow.rows.length;
	if($jq("#issuedAmount").html()!='') {
	for(var i=4;i<length;i++){
		var fare=0;
		var persons=0;
		if(dynRow.rows[i].cells[8].childNodes[0].value!='') {
			fare=parseFloat(dynRow.rows[i].cells[8].childNodes[0].value);
		}
		if(dynRow.rows[i].cells[9].childNodes[0].value!='') {
			persons=parseFloat(dynRow.rows[i].cells[9].childNodes[0].value);
		}
		dynRow.rows[i].cells[10].childNodes[0].value= roundNumber(fare*persons,2);
			grandTotal=parseFloat(dynRow.rows[i].cells[10].childNodes[0].value)+grandTotal;
		 }
	 $jq("#totAmt").html(roundNumber(grandTotal,2));
	 
	 if($jq("#settlementDiv").is(':visible')) {
		 $jq("#settlementAmount").html(parseFloat(grandTotal)-parseFloat($jq("#issuedAmount").html()));
	 }
	}else {
		alert('CDA Amount not yet issued');
		clearLTCReimbursement();
	}
}
function roundNumber(num, dec) {
	return Math.round(num*Math.pow(10,dec))/Math.pow(10,dec);
}
//LTC Reimbursement End

//LTC Advance after approval start

function getLtcAdvance(requestID) {
	document.forms[0].param.value = "advanceHome";
	document.forms[0].id.value = requestID;
	document.forms[0].action = "ltcApprovalRequest.htm";
	document.forms[0].submit();
}
/*function submitAdvanceRequest(referenceRequestID) {
var status=true;
var errorMessage = '';

if($jq('#amountClaimed').val()==''){
	errorMessage+="Please Enter Desired Amount\n";
	if(status){
		$jq('#amountClaimed').focus();
		status=false;
	}
}
if($jq('#noOfTickets').val()==''){
	errorMessage+="Please Enter No.of Ticketst\n";
	if(status){
		$jq('#noOfTickets').focus();
		status=false;
	}
}
if($jq('#amountPerPerson').val()==''){
	errorMessage+="Please Enter Amount per Person\n";
	if(status){
		$jq('#amountPerPerson').focus();
		status=false;
	}
}
	if(status) {
		var requestDetails = {
				"amountClaimed" : $jq('#amountClaimed').val(),
				"noOfTickets":$jq('#noOfTickets').val(),
				"amountPerPerson":$jq('#amountPerPerson').val(),
				"noOfInfantTickets":$jq('#noOfInfantTickets').val(),
				"amountPerEachInfant":$jq('#amountPerEachInfant').val(),
				"type":"advance" ,
				"param":"submitRequest"
			};
			$jq.post('ltcApprovalRequest.htm', requestDetails, function(html) {
				$jq("#result").html(html);
				clearLtcApprRequest()
			});
	}else {
		alert(errorMessage);
	}
	
}*/
function saveCdaAmont(type) {
	var mainJSON = {};
	var status=true;
	var errorMessage='';
	if(type=='advance') {
		//for(var i=0;i<=$jq('#advance').find("tr").length;i++) {
			//if($jq('#adv'+i).is(':checked')) {
		if($jq('.rowa').attr('checked')== true) {
				//if($jq('#sanctionNoAdv'+i).val()=='') {
				if($jq('#sanctionNoAdv').val()=='') {
					errorMessage+='enter sanction no\n';
					//$jq('#sanctionNoAdv'+i).focus();
					$jq('#sanctionNoAdv').focus();
					status=false;
				}if($jq('#billNoAdv').val()=='') {
					//if($jq('#billNoAdv'+i).val()=='') {
					errorMessage+='enter bill no\n';
					$jq('#billNoAdv').focus();
					//$jq('#billNoAdv'+i).focus();
					status=false;
				}if($jq('#accOfficerAdv').val()=='select') {
					//}if($jq('#accOfficerAdv'+i).val()=='select') {
					errorMessage+='Please select Acc. Officer.\n';
					$jq('#accOfficerAdv').focus();
					//$jq('#accOfficerAdv'+i).focus();
					status=false;
				}
				
				/*if($jq('#repSigAdv').val()=='select') {
					//}if($jq('#accOfficerAdv'+i).val()=='select') {
					errorMessage+='Please select CFA Sig. Officer.\n';
					$jq('#repSigAdv').focus();
					//$jq('#accOfficerAdv'+i).focus();
					status=false;
				}
				*/
				
				
				if($jq('#amountAdv').val()=='') {
					errorMessage+='enter amount\n';
					$jq('#amountAdv').focus();
					status=false;
				}
			}
		//}
		
		for(var i=0,j=0;i<=$jq('#advance').find("tr").length;i++) {
			//if($jq('#adv'+i).is(':checked')) {
				if($jq('#adv'+i).is(':checked')) {
				var subJSON={};
				/*subJSON['requestId']=$jq('#adv'+i).val();
				subJSON['cdaAmount']=$jq('#cdaAmountAdv'+i).val();
				subJSON['sanctionNo']=$jq('#sanctionNoAdv'+i).val();
				subJSON['billNo']=$jq('#billNoAdv'+i).val();
				subJSON['dvNo']=$jq('#dvNoAdv'+i).val();
				subJSON['dvDate']=$jq('#dvDateAdv'+i).val();
				subJSON['accOfficer']=$jq('#accOfficerAdv'+i).val();*/
				
				subJSON['requestId']=$jq('#adv'+i).val();
				
				subJSON['sanctionNo']=$jq('#sanctionNoAdv').val();
				subJSON['billNo']=$jq('#billNoAdv').val();
			
				
				subJSON['accOfficer']=$jq('#accOfficerAdv').val();
				//subJSON['repSig']=$jq('#repSigAdv').val();
				//subJSON['repSig']=$jq('#repSigSett').val();
				subJSON['amount']=$jq('#amountAdv'+i).val();
				
				mainJSON[j]=subJSON;
				j++;
			}
		}
	}else if(type=='settlement') {
		if($jq('.rows').attr('checked')== true) {
		//for(var i=0;i<=$jq('#settlement').find("tr").length;i++) {
			/*if($jq('#sett'+i).is(':checked')) {
				if($jq('#sanctionNoSett'+i).val()=='') {
					errorMessage+='enter sanction no\n';
					$jq('#sanctionNoSett'+i).focus();
					status=false;
				}
				if($jq('#billNoSett'+i).val()=='') {
					errorMessage+='enter bill no\n';
					$jq('#billNoSett'+i).focus();
					status=false;
				}
				if($jq('#accOfficerSett'+i).val()=='select') {
					errorMessage+='Please select Acc. Officer.\n';
					$jq('#accOfficerSett'+i).focus();
					status=false;
				}
			}*/
			//if($jq('#sett').is(':checked')) {
				if($jq('#sanctionNoSett').val()=='') {
					errorMessage+='enter sanction no\n';
					$jq('#sanctionNoSett').focus();
					status=false;
				}
				if($jq('#billNoSett').val()=='') {
					errorMessage+='enter bill no\n';
					$jq('#billNoSett').focus();
					status=false;
				}
				if($jq('#accOfficerSett').val()=='select') {
					errorMessage+='Please select Acc. Officer.\n';
					$jq('#accOfficerSett').focus();
					status=false;
				}
				/*if($jq('#repSigSett').val()=='select') {
					//}if($jq('#accOfficerAdv'+i).val()=='select') {
					errorMessage+='Please select CFA Sig. Officer.\n';
					$jq('#repSigSett').focus();
					//$jq('#accOfficerAdv'+i).focus();
					status=false;
				}*/
				
				if($jq('#amountSett').val()=='') {
					errorMessage+='enter amount\n';
					$jq('#amountSett').focus();
					status=false;
				}
				
			//}
			
		}
		for(var i=0,j=0;i<=$jq('#settlement').find("tr").length;i++) {
			/*if($jq('#sett'+i).is(':checked')) {
				var subJSON={};
				subJSON['requestId']=$jq('#sett'+i).val();
				subJSON['cdaAmount']=$jq('#cdaAmountSett'+i).val();
				subJSON['sanctionNo']=$jq('#sanctionNoSett'+i).val();
				subJSON['billNo']=$jq('#billNoSett'+i).val();
				subJSON['dvNo']=$jq('#dvNoSett'+i).val();
				subJSON['dvDate']=$jq('#dvDateSett'+i).val();
				subJSON['accOfficer']=$jq('#accOfficerSett'+i).val();
				subJSON['encashmentAmount']= "";
				mainJSON[j]=subJSON;
				j++;
			}*/
			
			if($jq('#sett'+i).is(':checked')) {
				var subJSON={};
				subJSON['requestId']=$jq('#sett'+i).val();
				
				subJSON['sanctionNo']=$jq('#sanctionNoSett').val();
				subJSON['billNo']=$jq('#billNoSett').val();
			
				subJSON['accOfficer']=$jq('#accOfficerSett').val();
				//subJSON['encashmentAmount']= "";
				//subJSON['repSig']=$jq('#repSigSett').val();
				subJSON['amount']=$jq('#amountSett'+i).val();
				mainJSON[j]=subJSON;
				j++;
			}
		}
		
	}else if(type=='reimbursement') {
		if($jq('.rowr').attr('checked')== true) {
		//for(var i=0;i<=$jq('#reimbursement').find("tr").length;i++) {
			/*if($jq('#reim'+i).is(':checked')) {
				if($jq('#sanctionNoReim'+i).val()=='') {
					errorMessage+='enter sanction no\n';
					$jq('#sanctionNoReim'+i).focus();
					status=false;
				}
				if($jq('#billNoReim'+i).val()=='') {
					errorMessage+='enter bill no\n';
					$jq('#billNoReim'+i).focus();
					status=false;
				}
				if($jq('#accOfficerReim'+i).val()=='select') {
					errorMessage+='Please select Acc. Officer.\n';
					$jq('#accOfficerReim'+i).focus();
					status=false;
				}
				
			}*/
			
			//if($jq('#reim'+i).is(':checked')) {
				if($jq('#sanctionNoReim').val()=='') {
					errorMessage+='enter sanction no\n';
					$jq('#sanctionNoReim').focus();
					status=false;
				}
				if($jq('#billNoReim').val()=='') {
					errorMessage+='enter bill no\n';
					$jq('#billNoReim').focus();
					status=false;
				}
				if($jq('#accOfficerReim').val()=='select') {
					errorMessage+='Please select Acc. Officer.\n';
					$jq('#accOfficerReim').focus();
					status=false;
				}
				/*if($jq('#repSigReim').val()=='select') {
					//}if($jq('#accOfficerAdv'+i).val()=='select') {
					errorMessage+='Please select CFA Sig. Officer.\n';
					$jq('#repSigReim').focus();
					//$jq('#accOfficerAdv'+i).focus();
					status=false;
				}*/
				
				if($jq('#amountReim').val()=='') {
					errorMessage+='enter amount\n';
					$jq('#amountReim').focus();
					status=false;
				}
				
		}
			//}
		//}
		for(var i=0,j=0;i<=$jq('#reimbursement').find("tr").length;i++) {
			/*if($jq('#reim'+i).is(':checked')) {
				var subJSON={};
				subJSON['requestId']=$jq('#reim'+i).val();
				subJSON['cdaAmount']=$jq('#cdaAmountReim'+i).val();
				subJSON['sanctionNo']=$jq('#sanctionNoReim'+i).val();
				subJSON['billNo']=$jq('#billNoReim'+i).val();
				subJSON['dvNo']=$jq('#dvNoReim'+i).val();
				subJSON['dvDate']=$jq('#dvDateReim'+i).val();
				subJSON['accOfficer']=$jq('#accOfficerReim'+i).val();
				mainJSON[j]=subJSON;
				j++;
			}*/
			if($jq('#reim'+i).is(':checked')) {
				var subJSON={};
				subJSON['requestId']=$jq('#reim'+i).val();
			
				subJSON['sanctionNo']=$jq('#sanctionNoReim').val();
				subJSON['billNo']=$jq('#billNoReim').val();
				
				subJSON['accOfficer']=$jq('#accOfficerReim').val();
				//subJSON['repSig']=$jq('#repSigReim').val();
				subJSON['amount']=$jq('#amountReim'+i).val();
				mainJSON[j]=subJSON;
				j++;
			}
		}
		
	}else if(type=='encashment') {
		//for(var i=0;i<=$jq('#encashment').find("tr").length;i++) {
			
		if($jq('.rowe').attr('checked')== true) {	
			/*
			if($jq('#encash'+i).is(':checked')) {
				if($jq('#sanctionNoEncash'+i).val()=='') {
					errorMessage+='enter sanction no\n';
					$jq('#sanctionNoEncash'+i).focus();
					status=false;
				}
				if($jq('#billNoEncash'+i).val()=='') {
					errorMessage+='enter bill no\n';
					$jq('#billNoEncash'+i).focus();
					status=false;
				}
				if($jq('#accOfficerEnc'+i).val()=='select') {
					errorMessage+='Please select Acc. Officer.\n';
					$jq('#accOfficerEnc'+i).focus();
					status=false;
				}
			}
		*/

			//if($jq('#encash'+i).is(':checked')) {
				if($jq('#sanctionNoEncash'+i).val()=='') {
					errorMessage+='enter sanction no\n';
					$jq('#sanctionNoEncash').focus();
					status=false;
				}
				if($jq('#billNoEncash').val()=='') {
					errorMessage+='enter bill no\n';
					$jq('#billNoEncash').focus();
					status=false;
				}
				if($jq('#accOfficerEnc').val()=='select') {
					errorMessage+='Please select Acc. Officer.\n';
					$jq('#accOfficerEnc').focus();
					status=false;
				}
				
				if($jq('#repSigEncash').val()=='select') {
					//}if($jq('#accOfficerAdv'+i).val()=='select') {
					errorMessage+='Please select CFA Sig. Officer.\n';
					$jq('#repSigEncash').focus();
					//$jq('#accOfficerAdv'+i).focus();
					status=false;
				}
				
				
				if($jq('#FinanceAmountEncash').val()=='') {
					errorMessage+='enter amount\n';
					$jq('#FinanceAmountEncash').focus();
					status=false;
				}
		}
				//if($jq('#amountEncash').val()=='') {
					//errorMessage+='enter amount\n';
					//$jq('#amountEncash').focus();
					//status=false;
				//}
			//}
			
		
		
		//}
		for(var i=0,j=0;i<=$jq('#encashment').find("tr").length;i++) {
			/*if($jq('#encash'+i).is(':checked')) {
				var subJSON={};
				subJSON['requestId']=$jq('#encash'+i).val();
				subJSON['sanctionNo']=$jq('#sanctionNoEncash'+i).val();
				subJSON['billNo']=$jq('#billNoEncash'+i).val();
				subJSON['accOfficer']=$jq('#accOfficerEnc'+i).val();
				subJSON['dvNo']=$jq('#dvNoEncash'+i).val();
				subJSON['dvDate']=$jq('#dvDateEncash'+i).val();
				subJSON['cdaAmount']=$jq('#cdaAmountEnc'+i).val();
				if(!$jq('#FinanceAmountEncash'+i).val()== null){
				subJSON['encashmentAmount']=$jq('#FinanceAmountEncash'+i).val();
				}else{
					subJSON['encashmentAmount']= "";
				}
				mainJSON[j]=subJSON;
				j++;
			}*/
			
			if($jq('#encash'+i).is(':checked')) {
				var subJSON={};
				subJSON['requestId']=$jq('#encash'+i).val();
				subJSON['sanctionNo']=$jq('#sanctionNoEncash').val();
				subJSON['billNo']=$jq('#billNoEncash').val();
				subJSON['accOfficer']=$jq('#accOfficerEnc').val();
				
				
				//subJSON['cdaAmount']=$jq('#cdaAmountEnc').val();
				subJSON['repSig']=$jq('#repSigEncash').val();
				//subJSON['amount']=$jq('#amountEncash'+i).val();
				//encashmentAmount   FinanceAmountEncash
				subJSON['encashmentAmount']=$jq('#FinanceAmountEncash'+i).val();
				/*if(!$jq('#FinanceAmountEncash').val()== null){
				subJSON['encashmentAmount']=$jq('#FinanceAmountEncash').val();
				}else{
					subJSON['encashmentAmount']= "";
				}*/
				mainJSON[j]=subJSON;
				j++;
			}
		}
		
	}
	if(JSON.stringify(mainJSON).length==2) {
		errorMessage+='please check at least one check box';
		status = false;
	}
	
	if(status) {
		$jq.post('ltcApprovalRequest.htm?param=manage&type='+type+'&jsonValues='+JSON.stringify(mainJSON), function(html) {
			//success: function (data, status)
			if(type=='advance') {
				$jq("#advanceList").html(html);
			
				// $jq("#result").html(html);
			}else if(type=='settlement') {
				$jq("#settlementList").html(html);
			}else if(type=='reimbursement') {
				$jq("#reimbursementList").html(html);
			}else if(type=='encashment') {
				$jq("#encashmentList").html(html);
			}
    	  });
	}else {
		alert(errorMessage);
	}clearfinance(type)
}

function saveCdaAmontsett(type) {
	var mainJSON = {};
	var status=true;
	var errorMessage='';
	if(type=='advance') {
		if($jq('.rowa').attr('checked')== true) {
			
		//for(var i=0;i<=$jq('#advance').find("tr").length;i++) {
			//if($jq('#adv'+i).is(':checked')) {
				/*if($jq('#sanctionNoAdv'+i).val()=='') {
					errorMessage+='enter sanction no\n';
					$jq('#sanctionNoAdv'+i).focus();
					status=false;
				}if($jq('#billNoAdv'+i).val()=='') {
					errorMessage+='enter bill no\n';
					$jq('#billNoAdv'+i).focus();
					status=false;
				}if($jq('#accOfficerAdv'+i).val()=='select') {
					errorMessage+='Please select Acc. Officer.\n';
					$jq('#accOfficerAdv'+i).focus();
					status=false;
				}*/
				if($jq('#dvNoAdv').val()=='') {
					errorMessage+='Please enter dvno.\n';
					$jq('#dvNoAdv').focus();
					status=false;
				}
				if($jq('#dvDateAdv').val()=='') {
					errorMessage+='Please enter dvdate.\n';
					$jq('#dvDateAdv').focus();
					status=false;
				}
			}
		//}
		
		for(var i=0,j=0;i<=$jq('#advance').find("tr").length;i++) {
			if($jq('#adv'+i).is(':checked')) {
				var subJSON={};
				subJSON['requestId']=$jq('#adv'+i).val();
				subJSON['cdaAmount']=$jq('#cdaAmountAdv'+i).val();
				//subJSON['sanctionNo']=$jq('#sanctionNoAdv'+i).val();
				//subJSON['billNo']=$jq('#billNoAdv'+i).val();
				subJSON['dvNo']=$jq('#dvNoAdv').val();
				subJSON['dvDate']=$jq('#dvDateAdv').val();
				//subJSON['accOfficer']=$jq('#accOfficerAdv'+i).val();
				mainJSON[j]=subJSON;
				j++;
			}
		}
	}else if(type=='settlement') {
		//for(var i=0;i<=$jq('#settlement').find("tr").length;i++) {
			//if($jq('#sett'+i).is(':checked')) {
		if($jq('.rows').attr('checked')== true) {
				/*if($jq('#sanctionNoSett'+i).val()=='') {
					errorMessage+='enter sanction no\n';
					$jq('#sanctionNoSett'+i).focus();
					status=false;
				}
				if($jq('#billNoSett'+i).val()=='') {
					errorMessage+='enter bill no\n';
					$jq('#billNoSett'+i).focus();
					status=false;
				}
				if($jq('#accOfficerSett'+i).val()=='select') {
					errorMessage+='Please select Acc. Officer.\n';
					$jq('#accOfficerSett'+i).focus();
					status=false;
				}*/
				if($jq('#dvNoSett').val()=='') {
					errorMessage+='Please enter dvno.\n';
					$jq('#dvNoSett').focus();
					status=false;
				}
				if($jq('#dvDateSett').val()=='') {
					errorMessage+='Please enter dvdate.\n';
					$jq('#dvDateSett').focus();
					status=false;
				}
		//	}
		}
		for(var i=0,j=0;i<=$jq('#settlement').find("tr").length;i++) {
			if($jq('#sett'+i).is(':checked')) {
				var subJSON={};
				subJSON['requestId']=$jq('#sett'+i).val();
				subJSON['cdaAmount']=$jq('#cdaAmountSett'+i).val();
				//subJSON['sanctionNo']=$jq('#sanctionNoSett'+i).val();
				//subJSON['billNo']=$jq('#billNoSett'+i).val();
				//subJSON['dvNo']=$jq('#dvNoSett'+i).val();
				//subJSON['dvDate']=$jq('#dvDateSett'+i).val();
				subJSON['dvNo']=$jq('#dvNoSett').val();
				subJSON['dvDate']=$jq('#dvDateSett').val();
				//subJSON['accOfficer']=$jq('#accOfficerSett'+i).val();
				subJSON['encashmentAmount']= "";
				mainJSON[j]=subJSON;
				j++;
			}
		}
		
	}else if(type=='reimbursement') {
		//for(var i=0;i<=$jq('#reimbursement').find("tr").length;i++) {
			//if($jq('#reim'+i).is(':checked')) {
		if($jq('.rowr').attr('checked')== true) {
				/*if($jq('#sanctionNoReim'+i).val()=='') {
					errorMessage+='enter sanction no\n';
					$jq('#sanctionNoReim'+i).focus();
					status=false;
				}
				if($jq('#billNoReim'+i).val()=='') {
					errorMessage+='enter bill no\n';
					$jq('#billNoReim'+i).focus();
					status=false;
				}
				if($jq('#accOfficerReim'+i).val()=='select') {
					errorMessage+='Please select Acc. Officer.\n';
					$jq('#accOfficerReim'+i).focus();
					status=false;
				}*/
				if($jq('#dvNoReim').val()=='') {
					errorMessage+='Please enter dvno.\n';
					$jq('#dvNoReim').focus();
					status=false;
				}
				if($jq('#dvDateReim').val()=='') {
					errorMessage+='Please enter dvdate.\n';
					$jq('#dvDateReim').focus();
					status=false;
				}
				
			//}
		}
		for(var i=0,j=0;i<=$jq('#reimbursement').find("tr").length;i++) {
			if($jq('#reim'+i).is(':checked')) {
				var subJSON={};
				subJSON['requestId']=$jq('#reim'+i).val();
				subJSON['cdaAmount']=$jq('#cdaAmountReim'+i).val();
				//subJSON['sanctionNo']=$jq('#sanctionNoReim'+i).val();
				//subJSON['billNo']=$jq('#billNoReim'+i).val();
				subJSON['dvNo']=$jq('#dvNoReim').val();
				subJSON['dvDate']=$jq('#dvDateReim').val();
				//subJSON['dvNo']=$jq('#dvNoReim'+i).val();
				//subJSON['dvDate']=$jq('#dvDateReim'+i).val();
				
				//subJSON['accOfficer']=$jq('#accOfficerReim'+i).val();
				mainJSON[j]=subJSON;
				j++;
			}
		}
		
	}else if(type=='encashment') {
		//for(var i=0;i<=$jq('#encashment').find("tr").length;i++) {
			//if($jq('#encash'+i).is(':checked')) {
		if($jq('.rowe').attr('checked')== true) {
				/*if($jq('#sanctionNoEncash'+i).val()=='') {
					errorMessage+='enter sanction no\n';
					$jq('#sanctionNoEncash'+i).focus();
					status=false;
				}
				if($jq('#billNoEncash'+i).val()=='') {
					errorMessage+='enter bill no\n';
					$jq('#billNoEncash'+i).focus();
					status=false;
				}
				if($jq('#accOfficerEnc'+i).val()=='select') {
					errorMessage+='Please select Acc. Officer.\n';
					$jq('#accOfficerEnc'+i).focus();
					status=false;
				}*/
				if($jq('#dvNoEncash').val()=='') {
					errorMessage+='Please enter dvno.\n';
					$jq('#dvNoEncash').focus();
					status=false;
				}
				if($jq('#dvDateEncash').val()=='') {
					errorMessage+='Please enter dvdate.\n';
					$jq('#dvDateEncash').focus();
					status=false;
				}
			//}
		}
		for(var i=0,j=0;i<=$jq('#encashment').find("tr").length;i++) {
			if($jq('#encash'+i).is(':checked')) {
				var subJSON={};
				subJSON['requestId']=$jq('#encash'+i).val();
				//subJSON['sanctionNo']=$jq('#sanctionNoEncash'+i).val();
				//subJSON['billNo']=$jq('#billNoEncash'+i).val();
				//subJSON['accOfficer']=$jq('#accOfficerEnc'+i).val();
				//subJSON['dvNo']=$jq('#dvNoEncash'+i).val();
				//subJSON['dvDate']=$jq('#dvDateEncash'+i).val();
				subJSON['dvNo']=$jq('#dvNoEncash').val();
				subJSON['dvDate']=$jq('#dvDateEncash').val();
				subJSON['cdaAmount']=$jq('#cdaAmountEnc'+i).val();
				if(!$jq('#FinanceAmountEncash'+i).val()== null){
				subJSON['encashmentAmount']=$jq('#FinanceAmountEncash'+i).val();
				}else{
					subJSON['encashmentAmount']= "";
				}
				mainJSON[j]=subJSON;
				j++;
			}
		}
		
	}
	if(JSON.stringify(mainJSON).length==2) {
		errorMessage+='please check at least one check box';
		status = false;
	}
	
	if(status) {
		$jq.post('ltcApprovalRequest.htm?param=ltcCDAamountsettlsave&type='+type+'&jsonValues='+JSON.stringify(mainJSON), function(html) { 
			//$jq("#result").html(html);
			if(type=='advance') {
				$jq("#cdaadvanceList").html(html);
				//advanceList
				 //$jq("#result").html(html);
			}else if(type=='settlement') {
				//settlementList
				$jq("#cdasettlementList").html(html);
				//$jq("#result").html(html);
			}else if(type=='reimbursement') {
				//reimbursementList
				$jq("#cdaReimbursementList").html(html);
			}else if(type=='encashment') {
				$jq("#cdaencashmentList").html(html);
			}
    	  });
	}else {
		alert(errorMessage);
	}
	clearcda(type)
}
function clearcda(type){
	
	if(type=='advance'){
		$jq('#dvNoAdv').val('');
		$jq('#dvDateAdv').val('');
		
	}else if(type=='settlement'){
		
		$jq('#dvNoSett').val('');
		$jq('#dvDateSett').val('');
	}
	else if(type=='reimbursement'){
		$jq('#dvNoReim').val('');
		$jq('#dvDateReim').val('');
	}
	else if(type=='encashment'){
		$jq('#dvDateEncash').val('');
		$jq('#dvNoEncash').val('');
		
		
	}
}
	function clearfinance(type){
		
		if(type=='advance'){
			$jq('#sanctionNoAdv').val('');
			$jq('#billNoAdv').val('');
			$jq('#accOfficerAdv').val('');
		}else if(type=='settement'){
			
			$jq('#sanctionNoSett').val('');
			$jq('#billNoSett').val('');
			$jq('#accOfficerSett').val('');
		
		}
		else if(type=='reimbursement'){
			$jq('#sanctionNoReim').val('');
			$jq('#billNoReim').val('');
			$jq('#accOfficerReim').val('');
		}
		else if(type=='encashment'){
			$jq('#sanctionNoEncash').val('');
			$jq('#billNoEncash').val('');
			$jq('#accOfficerEnc').val('');	
			
		}
}

function setSelectedValue(id,repSigValue) {
	document.getElementById(id).value=repSigValue;
}
function getInitialReport(requestId,requestType) {
	window.open('./report.htm?param=initial&type='+requestType+'&requestID='+requestId,'LTC','fullscreen=no,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no')
}


function getInitialReportCda1() {
	window.open('./report.htm?param=initial&type='+requestType+'&requestID='+requestId,'LTC','fullscreen=no,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no')
}


function getInitialReportCda(requestType){
	var billNo='';
	var errorMessage='';
	var status=true;
	var i=0;
	
	if(requestType=='ltcCdaSett'){
		billNo=$jq('#billNoSett').val();
		if(billNo==0){
			errorMessage="Please Enter BillNo"
				status=false;
		}
	}
	
	
	if(requestType=='ltcCdaAdv'){
		billNo=$jq('#billNoAdv').val();
		if(billNo==0){
			errorMessage="Please Enter BillNo"
				status=false;
		}
	}
	
	if(requestType=='ltcCdaReimb'){
		billNo=$jq('#billNoReim').val();
		if(billNo==0){
			errorMessage="Please Enter BillNo"
				status=false;
		}
	}
	
	if(requestType=='ltcCdaEncash'){
		billNo=$jq('#billNoEncash').val();
		if(billNo==0){
			errorMessage="Please Enter BillNo"
				status=false;
		}
	}
	if(status){
		
			window
			.open(
					"./report.htm?param=initialCda&returnValue=report&billNo="+billNo+'&type='+requestType,
					'preview',
					'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
	
		}else{
			alert(errorMessage);
			//$jq('#billNoSett').val('');
			billNo==0;
		}		
}
function amendmentRequest(requestId,requestType,financeAmount,cdaAmount,journeyDate,blockYear,doPartId,historyID,issuedAmount) {
	var sysdate = new Date();
	var jDate = new Date(journeyDate.split("-")[2], getMonthID(journeyDate
			.split("-")[1]) + 1, journeyDate.split("-")[0], 0, 0, 1, 0)

	if(jDate>=sysdate) {
		document.forms[0].param.value = "amendment";
		document.forms[0].id.value = requestId;
		document.forms[0].type.value = requestType;
		document.forms[0].ltcAmendmentBlockYear.value = blockYear;
		document.forms[0].cancelReqDoPartId.value = doPartId;
		document.forms[0].cancelReqHistoryId.value = historyID;
		document.forms[0].cancelReqIssuedAmount.value = issuedAmount;
		document.forms[0].cancelReqCdaAmount.value = cdaAmount;
		document.forms[0].action = "ltcApprovalRequest.htm";
		document.forms[0].submit();
	}else {
		alert('Amendment cannot be made after Date of Journey.');
	}
}
function setAmendmentValues() {
	if(ltcApplicationBean!='') {
		if(ltcApplicationBean.leaveDetails!='')  {
			/**
			 * If user already applied with specific leave type then that leave details show in drop down
			 */
			document.getElementById('leaveRequestId').options.length = document.getElementById('leaveRequestId').options.length+1;
			var lenght= document.getElementById('leaveRequestId').options.length;
			document.getElementById('leaveRequestId').options[lenght-1].text = ltcApplicationBean.leaveDetails.split('#')[1];
			document.getElementById('leaveRequestId').options[lenght-1].value = ltcApplicationBean.leaveDetails.split('#')[0];
		}
		
		amendmentType= ltcApplicationBean.typeValue;//amendmentType global variable
		amendmentRequestId = ltcApplicationBean.id; //amendmentRequestId global variable
		$jq('#departureDate').val(ltcApplicationBean.formatedDepartureDate);
		$jq('#returnDate').val(ltcApplicationBean.formatedReturnDate);
		if(ltcApplicationBean.ltcTypeId==2) {
			$jq('#ltcTypeId').val(ltcApplicationBean.ltcTypeId);
			$jq('#typeOfLtcLables').attr("style","display:block;");
			$jq('#nearestRailStationDiv').html(ltcApplicationBean.nearestRlyStation);
			$jq('#placeOfVisitDiv').html(ltcApplicationBean.placeOfVisit);
		}else {
			$jq('#homeTownAddress').attr("style","display:none;");
			$jq('#ltcTypeId').val(ltcApplicationBean.ltcTypeId);
			$jq('#typeOfLtc').attr("style","display:block;");
			$jq('#nearestRlyStation').val(ltcApplicationBean.nearestRlyStation);
			$jq('#placeOfVisit').val(ltcApplicationBean.placeOfVisit);
		}
		$jq('#ltcBlockYearId').val(ltcApplicationBean.ltcBlockYearId);
		if(ltcApplicationBean.leaveRequestId!='') {
			
			$jq('#leaveType').attr("style","display:block;");
			$jq('#approvedLeave').attr("style","display:block;");
		}
		$jq('#leaveRequestId').val(ltcApplicationBean.leaveRequestId);
		$jq('#encashTypeId').val(ltcApplicationBean.encashTypeId);
		$jq('#encashmentDays').val(ltcApplicationBean.encashmentDays);
		/**
		 * Already applied family member show with checking the checkbox
		 */
		var familMember = ltcApplicationBean.familyMember.split(',');
		for(var i=0;i<familMember.length;i++) {
			$jq('#'+familMember[i]).attr('checked',true);
		}
		if(ltcApplicationBean.typeValue=='ltcApprovalAmendment') {
			
			
		}else if(ltcApplicationBean.typeValue=='ltcAdvanceAmendment') {
			noOfPerson = parseInt(ltcApplicationBean.noOfTickets) + parseInt(ltcApplicationBean.noOfInfantTickets);
			if(ltcApplicationBean.issuedAmount!='') {
				/**
				 * When advance amount is issued by the finance division, then avoid asking amout per person and desired amount fields
				 */
				$jq('#numberOfTicketsDiv').attr('style','display:block');
				$jq('#noOfTickets').val(ltcApplicationBean.noOfTickets);
				$jq('#noOfInfantTicketsDiv').attr('style','display:block');
				$jq('#noOfInfantTickets').val(ltcApplicationBean.noOfInfantTickets);
				$jq('#dateOfReturnDiv').attr('style','display:block');
				$jq('#returnDate').val(ltcApplicationBean.formatedReturnDate);
				$jq('#desiredAdvanceAmountDiv').attr('style','display:block');
				$jq('#desiredAdvanceLable').html('Issued Advance');
				$jq('#amountClaimedLable').html(ltcApplicationBean.issuedAmount);
				$jq('#desiredAdvanceLable').attr('style','font-weight: bold');
				$jq('#amountClaimedLable').attr('style','font-weight: bold');
			}else {
				$jq('#noOfTickets').val(ltcApplicationBean.noOfTickets);
				$jq('#desiredAdvanceAmountDiv').attr('style','display:block');
				$jq('#amountClaimed').val(ltcApplicationBean.amountClaimed);
				$jq('#amountPerPerson').val(ltcApplicationBean.amountPerPerson);
				$jq('#noOfInfantTickets').val(ltcApplicationBean.noOfInfantTickets);
				$jq('#amountPerEachInfant').val(ltcApplicationBean.amountPerEachInfant);
			}
		}
	}
}
function excessAmountCheck() {
	if($jq('#excessAmount').val()!='') {
		$jq('#excessAmountFineDiv').css('display','block');
		$jq('#MRODetailsDiv').css('display','block');
	}else {
		$jq('#excessAmountFineDiv').css('display','none');
		$jq('#MRODetailsDiv').css('display','none');
	}
}
function timePicker(id) {
	$jq(function(){
		$jq('.'+id).each(function(i){
			$jq('.'+id).timepicker({});
			eval($jq(this).text());
		});
	});
}
function calculateAdvance() {
	var x=0;
	$jq('#amountClaimed').val('');
	var totalAmount=0;
	if($jq('#noOfTickets').val()!='' && $jq('#amountPerPerson').val()!='') {
		//totalAmount = parseInt($jq('#noOfTickets').val()) * parseInt($jq('#amountPerPerson').val()) * 2
		totalAmount = parseInt($jq('#noOfTickets').val()) * ($jq('#amountPerPerson').val()) * 2			
	}if($jq('#noOfInfantTickets').val()!='' && $jq('#amountPerEachInfant').val()!='') {
		//totalAmount = parseInt(totalAmount) + parseInt($jq('#noOfInfantTickets').val()) * parseInt($jq('#amountPerEachInfant').val()) * 2
		totalAmount = (totalAmount) + parseInt($jq('#noOfInfantTickets').val()) *($jq('#amountPerEachInfant').val()) * 2
		}
	//totalAmount=calcutattodecimalDecimal(totalAmount);
	//totalAmount=inprecise_round(totalAmount,2);
	//totalAmount=precise_round(totalAmount,2); 
	totalAmount=round2Fixed(totalAmount); ////based on the Requirement  i choose this function if Requirement change u can Try above functions also::rakesh 
	$jq('#amountClaimed').val(totalAmount);	
}
function checkDates(id) {
	if(!(compareDate($jq('#'+id).val(),departureDate) && compareDate(returnDate,$jq('#'+id).val()))) {
		alert('Journey dates should be between '+departureDate+' and '+returnDate);
		$jq('#'+id).val('');
	}
	
}
function totalAdjustment() {
	var grandTotal=0;
	var dynRow=document.getElementById("journeyDetailsID");
	var length=dynRow.rows.length;
	for(var i=4;i<length;i++){
			grandTotal=parseFloat(dynRow.rows[i].cells[10].childNodes[0].value)+grandTotal;
		 }
	 $jq("#totAmt").html(roundNumber(grandTotal,2));
	 
	 if($jq("#settlementDiv").is(':visible')) {
		 $jq("#settlementAmount").html(parseFloat(grandTotal)-parseFloat($jq("#issuedAmount").html()));
	 }
}
function setVisibility(journeyDate,currentDate,doPartID,amendmentId,type,cdaAmount,i) {
	
	if(type=='ltcApproval') {
		if((doPartID!='' && doPartID!='0')){
			$jq('#amendmentDiv'+i).attr('style','display:block');
		}
		if(compareDate(journeyDate,currentDate)) {
			$jq('#advanceDiv'+i).attr('style','display:block');
		}else{
			$jq('#advanceDiv'+i).attr('style','display:block');
		}
		journeyDate=journeyDate.split("-")[0]+"-"+getMonthMON(parseInt(getMonthID(journeyDate.split("-")[1])))+"-"+journeyDate.split("-")[2];
		if(compareDate(journeyDate,currentDate)) {
			$jq('#cancelDiv'+i).attr('style','display:block');
		}
		//to disable advance request when journey date is next date
		if((new Date(journeyDate.split("-")[2], getMonthID(journeyDate.split("-")[1]) - 1,journeyDate.split("-")[0]-1, 0, 0, 1, 0)).equalsTo(new Date(currentDate.split("-")[2], getMonthID(currentDate.split("-")[1]) - 1,journeyDate.split("-")[0], 0, 0, 1, 0))){
			//$jq('#advanceDiv'+i).hide();
		}
	}else {
		if((doPartID!='' && doPartID!='0')){
			$jq('#amendmentDiv'+i).attr('style','display:block');
		}
		journeyDate=journeyDate.split("-")[0]+"-"+getMonthMON(parseInt(getMonthID(journeyDate.split("-")[1]))+2)+"-"+journeyDate.split("-")[2];
		if(compareDate(journeyDate,currentDate)) {
			$jq('#cancelDiv'+i).attr('style','display:block');
		}
		
	}
	
}
//LTC Advance after approval end

//LTC Admin Entry Starts
function getFamilyMemberList(){
	$jq('#familyMemberId').find('option').remove().end();
	$jq("#ltcBlockYearId").append($jq("<option></option>").attr("value",'0').text('Select'));
	$jq('#ltcBlockYearId').find('option').remove().end();
	if ($jq("#refSfID").val() == '') {
		alert("Please enter sfid");
	} else {
		$jq.ajax( {
			type : "GET",
			url : "ltcApprovalRequest.htm",
			data : "param=getFamilyMembersList&refSfID="
					+ $jq("#refSfID").val().toUpperCase(),
			success : function(html) {
				$jq('#result1').html(html);
				if ($jq('.success').is(':visible') && familyMembersJson!=null) {
					for(i=0;i<familyMembersJson.length;i++){
					$jq('#familyMemberId').append($jq("<option></option>").attr("value",familyMembersJson[i].id).text(familyMembersJson[i].name));
					}
					$jq("#ltcBlockYearId").append($jq("<option></option>").attr("value",'0').text('Select'));
					for(i=0;i<blockYearListJson.length;i++){
					$jq('#ltcBlockYearId').append($jq("<option></option>").attr("value",blockYearListJson[i].id).text(blockYearListJson[i].name));
					}
				}
			}
		});
	}
}

function resetAdminEntry(){
	$jq('select').val("0");
	$jq('input').val("");
	$jq('#familyMemberId').find('option').remove().end();
	$jq('#result1').text('');
	$jq('#ltcBlockYearId').find('option').remove().end();
	$jq("#ltcBlockYearId").append($jq("<option></option>").attr("value",'0').text('Select'));
}
function manageAdminEntry(){
	var errorMessage = "";
	var status = true;
	var selectedMembers="";
	var selectedMembersJson={};
	var i=0;
	if ($jq("#refSfID").val() == "") {
		errorMessage += "Please enter SFID.\n";
		if (status) {
			status = false;
			$jq("#refSfID").focus();
		}
	}
	if ($jq("#familyMemberId").val() == null) {
		errorMessage += "Please select Family Members.\n";
		if (status) {
			status = false;
			$jq("#familyMemberId").focus();
		}
	}
	if ($jq("#ltcTypeId").val() == "0") {
		errorMessage += "Please select LTC Type.\n";
		if (status) {
			status = false;
			$jq("#ltcTypeId").focus();
		}
	}
	if ($jq("#departureDate").val() == "") {
		errorMessage += "Please enter Departure Date.\n";
		if (status) {
			status = false;
			$jq("#departureDate").focus();
		}
	}
	if ($jq("#returnDate").val() == "") {
		errorMessage += "Please enter Return Date.\n";
		if (status) {
			status = false;
			$jq("#returnDate").focus();
		}
	}
	if($jq('#departureDate').val()!='' && $jq('#returnDate').val()!=''){
		var fdate = convertDate($jq('#departureDate').val());
		var tdate = convertDate($jq('#returnDate').val());
		
		if(compareDates(fdate,'dd-MM-yyyy',tdate,'dd-MM-yyyy')==1){
			errorMessage += "return Date Must be Greater Than Departure Date. \n";
			status = false;
		}}
	if ($jq("#ltcBlockYearId").val() == "0") {
		errorMessage += "Please select LTC Block Year.\n";
		if (status) {
			status = false;
			$jq("#ltcBlockYearId").focus();
		}
	}
	if($jq('#encashmentDays').val()!= "" && ($jq('#encashmentDays').val()>28 || $jq('#encashmentDays').val()<1)){
		//errorMessage +="Please enter EncashmentDays from 1-10 only.\n";
		errorMessage +="Please enter No of Vacation Days from 1-28 only.\n";
		if (status) {
			status = false;
			$jq("#encashmentDays").focus();
		}
	}

	if ($jq("#placeOfVisit").val() == "") {
		errorMessage += "Please enter Place of Visit.\n";
		if (status) {
			status = false;
			$jq("#placeOfVisit").focus();
		}
	}
	
	if ($jq("#nearestRlyStation").val() == "") {
		errorMessage += "Please enter Nearest RailwayStation.\n";
		if (status) {
			status = false;
			$jq("#nearestRlyStation").focus();
		}
	}
	if(($jq.trim($jq('#result1').text()) == 'Employee not exists')){
		errorMessage += "Invalid SFID";
		if (status) {
			status = false;
			$jq('#refSfid').focus();			
		}
	}
	if ($jq('.success').is(':visible')) {
		$jq('.success').hide();
	}
	$jq("#familyMemberId").each(function() {
		selectedMembers += $jq(this).val() + ",";
	});
	if (selectedMembers == "") {
		errorMessage += "Please select members.\n";
		if (status) {
			$jq('#familyMemberId').focus();
			status = false;
		}
	}
	$jq("#familyMemberId option").each(function() {
		if($jq(this).is(':selected')){
		selectedMembersJson[i]=$jq(this).val();
			i++;	
		}
	});				

	if (status) {
		if (selectedMembers != "") {
			selectedMembers = selectedMembers.substring(0,
					selectedMembers.length - 1);
		}
		var requestDetails = {
			"nodeID" : nodeID,
			"refSfID" : $jq("#refSfID").val().toUpperCase(),
			"familyMemberId" : selectedMembers,
			"jsonValue" : JSON.stringify(selectedMembersJson),
			"ltcTypeId" : $jq("#ltcTypeId").val(),
			"ltcBlockYearId" : $jq.trim($jq("#ltcBlockYearId").val()),
			"encashmentDays" : $jq.trim($jq("#encashmentDays").val()),
			"placeOfVisit" : $jq("#placeOfVisit").val(),
			"nearestRlyStation" : $jq("#nearestRlyStation").val(),
			"departureDate" : $jq("#departureDate").val(),
			"returnDate" : $jq("#returnDate").val(),
			"param" : "manageAdminEntry"
		};
		$jq.ajax( {
			type : "POST",
			url : "ltcApprovalRequest.htm",
			data : requestDetails,
			cache : false,
			success : function(html) {
				$jq('#result').html(html);
				if ($jq('.success').is(':visible')) {
					resetAdminEntry();
				}
			}
		});
	} else {
		alert(errorMessage);
	}
}
function searchAdminEntryDetails(){
	var errorMessage = "";
	var status = true;

	if ($jq('#refSfID').val() == "") {
		errorMessage += "Please enter SFID.\n";
		if (status) {
			$jq('#refSfID').focus();
			status = false;
		}
	}
	if (status) {
		var requestDetails = {
			"refSfID" : $jq('#refSfID').val().toUpperCase(),
			"param" : "searchAdminEntryDetails"
		};
		$jq.ajax( {
			type : "POST",
			url : "ltcApprovalRequest.htm",
			data : requestDetails,
			cache : false,
			success : function(html) {
				$jq('#result').html(html);
			}
		});
	} else {
		alert(errorMessage);
	}

}
//LTC Admin Entry Starts
//LTC leave cancellation or change
function ltcLeaveAmendment(){
	if($jq('input:radio[name=changeLeaveFlag]:checked').val()=='change'){
		$jq('#cancelLeaveDiv').hide();	
		$jq('#ltcAmendmentSubmit').show();
		if($jq('#departureDate').val()!='' && $jq('#returnDate').val()!='') {
			$jq.ajax( {
				type : "POST",
				url : "ltcApprovalRequest.htm",
				data : "param=leaves&departureDate=" + $jq("#departureDate").val()+"&returnDate="+$jq('#returnDate').val(),
				cache : false,
				success : function(html) {
					$jq("#approvedLeaveList").html(html);
					$jq("#ajaxBusy").remove();
					setLeaveList();
				}
			});
			
		}
	}else if($jq('input:radio[name=changeLeaveFlag]:checked').val()=='cancel'){
		if(!$jq('#cancelLeaveDiv').is(':visible')){
			$jq('#ltcAmendmentSubmit').hide();
			$jq('#approvedLeaveList').html(amendmentLeaves);
			$jq('#leaveRequestId').val(amendmentLeaveSelected);
		$jq('#cancelLeaveDiv').show();	
		}
	}else if($jq('input:radio[name=changeLeaveFlag]:checked').val()=='none'){
		$jq('#cancelLeaveDiv').hide();
		$jq('#ltcAmendmentSubmit').show();
		$jq('#approvedLeaveList').html(amendmentLeaves);
		$jq('#leaveRequestId').val(amendmentLeaveSelected);
	}
}

function ltcLeaveCancleAmendment(typevalue,historyId,requestId,stageId,typeValue){
	if($jq('#cancelLeaveDiv').is(':visible') && $jq('input:radio[name=changeLeaveFlag]:checked').val()=='cancel'){
		if($jq('input:radio[name=cancelLeaveFlag]:checked').val()=='Yes'){
			if(confirm('This leave is attached to LTC \nDo you want to cancel this leave?')){
				getLtcLeaveDetails('cancel',requestId,stageId,historyId,typeValue);
//				$jq('#leaveCancelButton').html('');
//				$jq('#leaveCancelButton').html('<a href="javascript:cancelLtcLeave('+requestId+','+typeValue+');"  class="appbutton" style="text-decoration: none;float:right">Submit</a>');
			}
		}else if($jq('input:radio[name=cancelLeaveFlag]:checked').val()=='No'){
			$jq('#cancelLeaveDiv').hide();
			$jq("#none").attr("checked","checked");
			ltcLeaveAmendment();
		}
	}
}
function checkLeaveStatus(){
	$jq.ajax( {
		type : "POST",
		url : "ltcApprovalRequest.htm",
		data : "param=leaveStatusCheck&leaveRequestId=" + $jq("#leaveRequestId").val(),
		cache : false,
		success : function(html) {
			$jq("#result").html('');
			$jq("#result").html(html);
			$jq("#ajaxBusy").remove();
		}
	});
}

function getLtcLeaveDetails(type,requestID,stageID,historyID,typeValue){
	if(type=='cancel' && stageID=='1'){	
		var requestDetails = {
			"param" : "declined",
			"historyID" : historyID,
			"requestID" : requestID,
			"requestType" : "LEAVE",
			"type" : "cancelled",
			"requestFrom" : "other"
		};
		$jq.post('workflow.htm',requestDetails,  
	       function(data){ 
	        	 $jq('#result').html(data);
	        	 getUpdatedLeaveDetails();
	       } 
	      );
	} else {
		document.forms[0].type.value=type;
		document.forms[0].requestID.value=requestID;
		document.forms[0].param.value="getLeaveDetails";
		document.forms[0].action = "leaveRequest.htm";
		document.forms[0].ltcRequestType.value=typeValue;
		document.forms[0].submit();
	}
}




function clearCheckBoxr(){
	
	alert("ltc1");
	$jq('#tabs').find('thead').each(function(){
		   $jq(this).find('th').eq(0).find('input').removeAttr('checked');	
		  checkBoxCheckAll($jq(this).find('th').find('input:checkbox').attr('name'),'rowr')
		
		});
	
}
function clearCheckBoxe(){
	$jq('#tabs').find('thead').each(function(){
		   $jq(this).find('th').eq(0).find('input').removeAttr('checked');	
		  checkBoxCheckAll($jq(this).find('th').find('input:checkbox').attr('name'),'rowe')
		
		});
	
}

function clearCheckBoxa(){
	$jq('#tabs').find('thead').each(function(){
		   $jq(this).find('th').eq(0).find('input').removeAttr('checked');	
		  checkBoxCheckAll($jq(this).find('th').find('input:checkbox').attr('name'),'rowa')
		
		});
	
}

function clearCheckBoxs(){
	$jq('#tabs').find('thead').each(function(){
		   $jq(this).find('th').eq(0).find('input').removeAttr('checked');	
		  checkBoxCheckAll($jq(this).find('th').find('input:checkbox').attr('name'),'rows')
		
		});
	
}



function validdate(ltcTypeDetailsJson)
{
   if($jq('#ltcTypeId').val()==3) 
	{
		    for ( var i = 0; i < ltcTypeDetailsJson.length; i++) {
		    	
			   if (ltcTypeDetailsJson[i].validToDate == validToDate) {
			   //jsonData[i].accId == id
		         if(ltcTypeDetailsJson.validToDate!=null){
		     $jq('#validToDate').val(ltcApplicationBean.validToDate);
		    }    }
	     }       
	}
	if($jq('#ltcTypeId').val()==4) {
		$jq('#validToDate').val(ltcApplicationBean.validToDate);
	}
	if($jq('#ltcTypeId').val()==1) {
		$jq('#validToDate').val(ltcApplicationBean.validToDate);
	}
	
}

function fieldChecking(){
	var status = true;
	var errorMessage ='';
	
	if ($jq("#placeOfVisit").val() == "") {
		
		errorMessage += "Place of Visit is not entered .\n";
		if (status) {
			status = false;
			$jq("#placeOfVisit").focus();
		}
	}
	if($jq("#returnDate").val() ==""){
		errorMessage += "return date is not entered.\n";
		if (status) {
			status = false;
			$jq("#returnDate").focus();
		}
	}
	if($jq("#departureDate").val() ==""){
		errorMessage += "departure date is not entered.\n";
		if (status) {
			status = false;
			$jq("#departureDate").focus();
		}
	}
	if($jq("#ltcBlockYearId").val() ==""){
		errorMessage += "ltcBlock year  is not entered.\n";
		if (status) {
			status = false;
			$jq("#ltcBlockYearId").focus();
		}
	}
	if($jq("#ltcTypeId").val() ==""){
		errorMessage += "LTC Type is not entered.\n";
		if (status) {
			status = false;
			$jq("#ltcTypeId").focus();
		}
	}
	
	if(status){
		//alert(errorMessage);
	}
	else{
		alert("Leave list is empty  because /n" +errorMessage);
		alert(errorMessage);
		//prompt("Leave list is empty  because :: ",alert(errorMessage));
		//prompt("Leave list is empty  because :: ","errorMessage");
		//prompt("<div style='size:16px; color:#f00;'>Leave list is empty  because ::</div> ",errorMessage);
		//alert('<div style=size:16px; color:#f00;>Leave list is empty  because ::</div>');
		/*$.prompt("rakesh",{
			title : " Are you ready?",
			buttons : {"yes, iam ready": true, "no , h": false}
		});*/
		//prompt("");
		//alert("Leave list is empty  because :: ",errorMessage);
		//prompt("X ","y");
	}
	
}
function twoDecimalCalculationAmount(){
	if($jq('#amountPerson').val()!=''){
	amountPerPerson =$jq('#amountPerPerson').val();
	PerPerson =round2Fixed(amountPerPerson);
	$jq('#amountPerPerson').val(PerPerson);}
	if($jq('#amountPerEachInfant').val()!=''){
		amountPerInfant =$jq('#amountPerEachInfant').val();
		PerInfant =round2Fixed(amountPerInfant);
		$jq('#amountPerEachInfant').val(PerInfant);
		 
	 }
}
function twoDecimalCalculationAmount1(obj){
	if($jq('#'+obj.id).val()!=''){
		Finance =$jq('#'+obj.id).val();
		FinanceAmount=round2Fixed(Finance);
		$jq('#'+obj.id).val(FinanceAmount);
		
	}
}

