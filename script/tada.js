var countt=0;
var countJourney=0;
var countNewDA=0;
var countRMAKm=0;
var countRMADay=0;
var countRMALocal=0; 
var totalAdjAmount=0;
var countDaNewAcc=0;
var countNewDAFood=0;
var countRequestJourney=0;
var countRMADaily=0;
var tdEditFlag=true;



function generateTaReport(){
	var fromDate=$jq('#fromDate').val();
	var toDate=$jq('#toDate').val();
	window.open('reports.htm?status=5&param=TadaDetails&fromDate='+fromDate+'&toDate='+toDate);
	
//	$jq.post('reports.htm?status=1&param=TadaDetails&fromDate='+fromDate+'&toDate='+toDate)
	
}


function reimChequeFields(SOH){
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


function chequeFields(abc){
	
	if(abc=='show'){
		$jq('#trdata').css('display', 'block');
	}else{
		
		$jq('#advDvno').val('');
		$jq('#advDvDate').val('');
		$jq('#advBankName').val('0');
		$jq('#advChequeNo').val('');
		$jq('#advBranchName').val('');
		
		
		$jq('#trdata').css('display', 'none');
	}
}

/*function tadaSelect(){
	alert("tadaSelect");
	  var jsonData = {
		      "Table": [{
		          "stateid": "2",
		          "statename": "Texas"
		      }, {
		          "stateid": "3",
		          "statename": "Louisiana"
		      }, {
		          "stateid": "4",
		          "statename": "California"
		      }, {
		          "stateid": "5",
		          "statename": "Nevada"
		      }, {
		          "stateid": "6",
		          "statename": "Massachusetts"
		      }]
		  };
	  
	  var listItems = '<option selected="selected" value="0">- Select -</option>';
	  console.log("listItems1");
      for (var i = 0; i < jsonData.Table.length; i++) {
    	  console.log("listItems2");
             listItems += "<option value='" + jsonData.Table[i].stateid + "'>" + jsonData.Table[i].statename + "</option>";
         }
      console.log("listItems3");
         $jq("#DLState").append(listItems);
         console.log("listItems4"+$jq("#DLState"));
         console.log("listItems4"+listItems);
}*/



function tadaAdminWaterSettlement(){
	var errorMessage = "";
	var status = true;

	if ($jq('#waterSettlementDate').val()=='') {
		errorMessage += "Please Enter SettlementDate.\n";
		if(status){
		$jq('#waterSettlementDate').focus();
		}
		status = false;
	}
	
	var waterSettlementDate=$jq('#waterSettlementDate').val();
	var settlementRemarks=$jq('#settlementRemarks').val();
	var requestID=$jq('#requestID').val();
	var requestId=$jq('#requestId').val();

	if(status){
	var requestDetails = {

				"waterSettlementDate" :waterSettlementDate,
				"settlementRemarks" :settlementRemarks,
				"requestID" :requestID,
				"requestId" :requestId,
				"param" : "tadaWaterAdminSettlement",
				"type" :"tadaWaterApproval"			
		};
	$jq.ajax( {
		type : "POST",
		url : "tadaWaterRequest.htm?",
		data : requestDetails,
		cache : false,
		success : function(html) {
			$jq('#result').html(html);
			if ($jq('.success').is(':visible')) {
				var check=confirm(" TADA WATER  Settlement has been Successfully Sent...");
				if(check){
						
						document.forms[0].param.value = "tadaWaterSettlements";
						document.forms[0].action = "tadaWaterRequest.htm";
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

function cleaTadaAdminWaterSettlement(){
	
	$jq('#waterSettlementDate').val('');
	$jq('#settlementRemarks').val('');
	
}

function tadaWaterReimbursement(){
	
	
//alert($jq('#reimBankName').val());
	//alert("updateTadaAdvance");
	var errorMessage = "";
	var status = true;
	
	if($jq('input:radio[id=reimcashorcheck1]').is(':checked')){
	var reimcashorcheck=$jq('#reimcashorcheck1').val();
	} 
	if($jq('input:radio[id=reimcashorcheck2]').is(':checked')){
		var reimcashorcheck=$jq('#reimcashorcheck2').val();
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
	if ($jq('#waterReimbursementDate').val()=='') {
		errorMessage += "Please Enter ReimbursementDate.\n";
		if(status){
		$jq('#waterReimbursementDate').focus();
		}
		status = false;
	}
	
	if(reimcashorcheck=='' || reimcashorcheck=='undefined' || reimcashorcheck==null){
		errorMessage += "Please Check Amount Issed Type.\n";
		/*if(status){
		$jq('#dvDate').focus();
		}*/
		status = false;
		
	}
	if(reimcashorcheck=='CASH'){
		reimBankName='0';
	}
	
if(reimcashorcheck=='CHEQUE'){
	
	if ($jq('#reimBankName').val()=='') {
		errorMessage += "Please Enter BankName.\n";
		if(status){
		$jq('#reimBankName').focus();
		}
		status = false;
	}
	if ($jq('#reimBranchName').val()=='') {
		errorMessage += "Please Enter BranchName.\n";
		if(status){
		$jq('#reimBranchName').focus();
		}
		status = false;
	}
	if ($jq('#reimChequeNo').val()=='') {
		errorMessage += "Please Enter ChequeNo.\n";
		if(status){
		$jq('#reimChequeNo').focus();
		}
		status = false;
	}
/*	if ($jq('#reimAdminDvno').val()=='') {
		errorMessage += "Please Enter Dvno.\n";
		if(status){
		$jq('#reimAdminDvno').focus();
		}
		status = false;
	}
	if ($jq('#reimAdminDvDate').val()=='') {
		errorMessage += "Please Enter DvDate.\n";
		if(status){
		$jq('#reimAdminDvDate').focus();
		}
		status = false;
	}*/
	
	
	
}
	
	
	var reimbursementRemarks=$jq('#reimbursementRemarks').val();
	var waterReimbursementDate=$jq('#waterReimbursementDate').val();
	var reimDvno=$jq('#reimDvno').val();
	var reimDvDate=$jq('#reimDvDate').val();
	var requestID=$jq('#requestID').val();
	var requestId=$jq('#requestId').val();
	
	
	var reimAdminDvno=$jq('#reimAdminDvno').val();
	var reimAdminDvDate=$jq('#reimAdminDvDate').val();
	if(reimcashorcheck=='CHEQUE'){
	var reimBankName=$jq('#reimBankName').val();
	}
	var reimChequeNo=$jq('#reimChequeNo').val();
	var reimBranchName=$jq('#reimBranchName').val();
	
	
	//alert(reimBankName);


	if(status){
	var requestDetails = {

				"reimDvDate" :reimDvDate,
				"reimDvno" :reimDvno,
				"waterReimbursementDate" :waterReimbursementDate,
				"reimbursementRemarks" :reimbursementRemarks,
				"requestID" :requestID,
				"requestId" :requestId,
				"reimcashorcheck" :reimcashorcheck,
				"reimAdminDvno" :reimAdminDvno,
				"reimAdminDvDate" :reimAdminDvDate,
				"reimBankName" :reimBankName,
				"reimChequeNo" :reimChequeNo,
				"reimBranchName" :reimBranchName,
				"param" : "tadaWaterAdminReimbursement",
				"type" :"tadaWaterApproval"			
		};
	$jq.ajax( {
		type : "POST",
		url : "tadaWaterRequest.htm?",
		data : requestDetails,
		cache : false,
		success : function(html) {
			$jq('#result').html(html);
			if ($jq('.success').is(':visible')) {
				var check=confirm(" TADA WATER  Reimbursement has been Successfully Sent...");
				if(check){
						
						document.forms[0].param.value = "tadaWaterSettlements";
						document.forms[0].action = "tadaWaterRequest.htm";
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


function clearTadaWaterReimbursement(){
	
	$jq('#reimbursementRemarks').val('');
	$jq('#waterReimbursementDate').val('');
	$jq('#reimDvno').val('');
	$jq('#reimDvDate').val('');
	
}

//this function added by bkr 11/05/2016 
function waterTadaASettlement(){
	var errorMessage = "";
	var status = true;
	
	var requestID=$jq('#requestID').val();
	var requestId=$jq('#requestId').val();
	var settleOrReimApplyDate=$jq('#settleOrReimApplyDate').val();
	var selOrReimRemarks=$jq('#selOrReimRemarks').val();
	var settleOrReimAmt=$jq('#settleOrReimAmt').val();
	var actualExpenditure=$jq('#actualExpenditure').val();
	if(settleOrReimAmt>=0){
		var settleOrReim='Settlement';
	}else{
		
		var settleOrReim='Reimbursement';
	}
	if ($jq('#actualExpenditure').val()=='') {
		errorMessage += "Please Enter actualExpenditure Amount.\n";
		if(status){
		$jq('#actualExpenditure').focus();
		}
		status = false;
	}
	if ($jq('#selOrReimRemarks').val()=='') {
		errorMessage += "Please Enter Remarks .\n";
		if(status){
		$jq('#selOrReimRemarks').focus();
		}
		status = false;
	}
	if ($jq('#settleOrReimApplyDate').val()=='') {
		errorMessage += "Please Enter Apply Date .\n";
		if(status){
		$jq('#settleOrReimApplyDate').focus();
		}
		status = false;
	}
	if(status){
	var requestDetails = {

			"requestID" :requestID,
			"requestId" :requestId,
			"settleOrReimApplyDate" :settleOrReimApplyDate,
			"selOrReimRemarks" :selOrReimRemarks,
			"settleOrReimAmt" :settleOrReimAmt,
			"actualExpenditure" :actualExpenditure,
			"settleOrReim" :settleOrReim,
			"param" : "tadaWaterSettlementRequest",
			"type" :"tadaWaterApproval"			
	};
	
	$jq.ajax( {
		type : "POST",
		url : "tadaWaterRequest.htm?",
		data : requestDetails,
		cache : false,
		success : function(html) {
			$jq('#result').html(html);
			if ($jq('.success').is(':visible')) {
				var check=confirm(" TADA WATER  Settlement Request has been Successfully Sent...");
				if(check){
					document.forms[0].param.value = "tadaWaterSettAndReim";
					document.forms[0].action = "tadaWaterRequest.htm";
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

function clearWaterTadaASettlement(){
	$jq('#settleOrReimApplyDate').val('');
	$jq('#actualExpenditure').val('');
	$jq('#selOrReimRemarks').val('');
	
}

//this function added by bkr 10/05/2016
function settlementOrReimAmt(){
	
	var totalAmt=$jq('#totalAmt').val();
	var actualExpenditure=$jq('#actualExpenditure').val();
	
	var rem=totalAmt-actualExpenditure;
	//alert("krisna"+rem);
	$jq('#settleOrReimAmt').val(rem);
	
	
	
}


//this function added by bkr 09/05/2016
function updateTadaAdvance(){
	//alert("updateTadaAdvance");
	var errorMessage = "";
	var status = true;
	
	var chequedetails=true;
	
	//alert("sumbittadaAdvance");
	
	
	if($jq('input:radio[id=cashorcheck1]').is(':checked')){
	var cashorcheck=$jq('#cashorcheck1').val();
	} 
	if($jq('input:radio[id=cashorcheck2]').is(':checked')){
		var cashorcheck=$jq('#cashorcheck2').val();
	}
	//alert(cashorcheck);

	if(cashorcheck=='CASH'){
		
		advBankName='0';
		
	}
	
	if ($jq('#fromDate').val()=='') {
		errorMessage += "Please Enter fromDate.\n";
		if(status){
		$jq('#fromDate').focus();
		}
		status = false;
	}
	if ($jq('#toDate').val()=='') {
		errorMessage += "Please Enter toDate.\n";
		if(status){
		$jq('#toDate').focus();
		}
		status = false;
	}
	if ($jq('#daAmt').val()=='') {
		errorMessage += "Please Enter daAmt.\n";
		if(status){
		$jq('#daAmt').focus();
		}
		status = false;
	}
	if ($jq('#taxiAmt').val()=='') {
		errorMessage += "Please Enter taxiAmt.\n";
		if(status){
		$jq('#taxiAmt').focus();
		}
		status = false;
	}
	if ($jq('#perDayFoodandAccmAmt').val()=='') {
		errorMessage += "Please Enter perDayFoodandAccmAmt.\n";
		if(status){
		$jq('#perDayFoodandAccmAmt').focus();
		}
		status = false;
	}
	if ($jq('#transitAmt').val()=='') {
		errorMessage += "Please Enter transitAmt.\n";
		if(status){
		$jq('#transitAmt').focus();
		}
		status = false;
	}
	if ($jq('#dvno').val()=='') {
		errorMessage += "Please Enter dvno.\n";
		if(status){
		$jq('#dvno').focus();
		}
		status = false;
	}
	if ($jq('#dvDate').val()=='') {
		errorMessage += "Please Enter dvDate.\n";
		if(status){
		$jq('#dvDate').focus();
		}
		status = false;
	}
	
	if(cashorcheck=='' || cashorcheck=='undefined' || cashorcheck==null){
		errorMessage += "Please Check Amount Issed Type.\n";
		/*if(status){
		$jq('#dvDate').focus();
		}*/
		status = false;
		
		
	}
	if(cashorcheck=='CHEQUE'){
		
		if ($jq('#advBankName').val()=='') {
			errorMessage += "Please Enter BankName.\n";
			if(status){
			$jq('#advBankName').focus();
			}
			status = false;
		}
		if ($jq('#advBranchName').val()=='') {
			errorMessage += "Please Enter BranchName.\n";
			if(status){
			$jq('#advBranchName').focus();
			}
			status = false;
		}
		if ($jq('#advChequeNo').val()=='') {
			errorMessage += "Please Enter ChequeNo.\n";
			if(status){
			$jq('#advChequeNo').focus();
			}
			status = false;
		}
		/*if ($jq('#advDvno').val()=='') {
			errorMessage += "Please Enter Dvno.\n";
			if(status){
			$jq('#advDvno').focus();
			}
			status = false;
		}
		if ($jq('#advDvDate').val()=='') {
			errorMessage += "Please Enter DvDate.\n";
			if(status){
			$jq('#advDvDate').focus();
			}
			status = false;
		}*/
		
		
		
	}

	var fromDate=$jq('#fromDate').val();
	var toDate=$jq('#toDate').val();
	var noOfDays=$jq('#noOfDays').val();
	var perDayFoodandAccmAmt=$jq('#perDayFoodandAccmAmt').val();
	var foodandAccmAmt=$jq('#foodandAccmAmt').val();
	var daAmt=$jq('#daAmt').val();
	var taxiAmt=$jq('#taxiAmt').val();
	var totalAmt=$jq('#totalAmt').val();
	var transitAmt=$jq('#transitAmt').val();
	var dvDate=$jq('#dvDate').val();
	var dvno=$jq('#dvno').val();
	var requestID=$jq('#requestID').val();
	var requestId=$jq('#requestId').val();
	
	
	var advDvno=$jq('#advDvno').val();
	var advDvDate=$jq('#advDvDate').val();
	if(cashorcheck=='CHEQUE'){
	var advBankName=$jq('#advBankName').val();
	}
	var advChequeNo=$jq('#advChequeNo').val();
	var advBranchName=$jq('#advBranchName').val();
	

	
	//alert(advBankName);
	

	if(status){
	var requestDetails = {

				"transitAmt" :transitAmt,
				"fromDate" :fromDate,
				"toDate" :toDate,
				"noOfDays" :noOfDays,
				"perDayFoodandAccmAmt" :perDayFoodandAccmAmt,
				"foodandAccmAmt" :foodandAccmAmt,
				"daAmt" :daAmt,
				"taxiAmt" :taxiAmt,
				"totalAmt" :totalAmt,
				"dvDate" :dvDate,
				"dvno" :dvno,
				"requestID" :requestID,
				"requestId" :requestId,
				"cashorcheck" :cashorcheck,
				"advDvno" :advDvno,
				"advDvDate" :advDvDate,
				"advBankName" :advBankName,
				"advChequeNo" :advChequeNo,
				"advBranchName" :advBranchName,
				"param" : "updateTadaAdvanceCumRequest",
				"type" :"tadaWaterApproval"			
		};
	$jq.ajax( {
		type : "POST",
		url : "tadaWaterRequest.htm?",
		data : requestDetails,
		cache : false,
		success : function(html) {
			$jq('#result').html(html);
			if ($jq('.success').is(':visible')) {
				var check=confirm(" TADA WATER  Advance has been Successfully Sent...");
				if(check){
						
						document.forms[0].param.value = "tadaWaterSettlements";
						document.forms[0].action = "tadaWaterRequest.htm";
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


function clearUpdateTadaAdvance(){
	
	$jq('#dvDate').val('');
	$jq('#dvno').val('');
	
	
}

//added by bkr 29/04/2016 for tada water advance cum request
function submitTadaAdvanceCumReq(){
	var errorMessage = "";
	var status = true;
	//var status = false;
	
	
	if ($jq('#claimPurpose').val()=='') {
		errorMessage += "Please Enter ClaimPurpose.\n";
		if(status){
		$jq('#claimPurpose').focus();
		}
		status = false;
	}
	if ($jq('#travellingTo').val()=='') {
		errorMessage += "Please Enter travellingTo.\n";
		if(status){
		$jq('#travellingTo').focus();
		}
		status = false;
	}
	if ($jq('#fromDate').val()=='') {
		errorMessage += "Please Enter fromDate.\n";
		if(status){
		$jq('#fromDate').focus();
		}
		status = false;
	}
	if ($jq('#toDate').val()=='') {
		errorMessage += "Please Enter toDate.\n";
		if(status){
		$jq('#toDate').focus();
		}
		status = false;
	}
	if ($jq('#daAmt').val()=='') {
		errorMessage += "Please Enter daAmt.\n";
		if(status){
		$jq('#daAmt').focus();
		}
		status = false;
	}
	if ($jq('#taxiAmt').val()=='') {
	/*	errorMessage += "Please Enter taxiAmt.\n";
		if(status){
		$jq('#taxiAmt').focus();
		}
		status = false;*/
	}
	if ($jq('#perDayFoodandAccmAmt').val()=='') {
		errorMessage += "Please Enter perDayFoodandAccmAmt.\n";
		if(status){
		$jq('#perDayFoodandAccmAmt').focus();
		}
		status = false;
	}
	if ($jq('#transitAmt').val()=='') {
		errorMessage += "Please Enter transitAmt.\n";
		if(status){
		$jq('#transitAmt').focus();
		}
		status = false;
	}
	
	
	
	
	var claimPurpose=$jq('#claimPurpose').val();
	var travellingTo=$jq('#travellingTo').val();
	var fromDate=$jq('#fromDate').val();
	var toDate=$jq('#toDate').val();
	var noOfDays=$jq('#noOfDays').val();
	var perDayFoodandAccmAmt=$jq('#perDayFoodandAccmAmt').val();
	var foodandAccmAmt=$jq('#foodandAccmAmt').val();
	var daAmt=$jq('#daAmt').val();
	var taxiAmt=$jq('#taxiAmt').val();
	
	if (taxiAmt==='') {
		taxiAmt=0;
	}
	
	
	var totalAmt=$jq('#totalAmt').val();
	
	var transitAmt=$jq('#transitAmt').val();


	var a=fromDate.substring(7);
	var b=toDate.substring(7);
	var res = fromDate.substring(3, 6); 
	var c=monthtonum(res);
	var res1 = toDate.substring(3, 6); 
	var d=monthtonum(res1);
	var e=fromDate.substring(0, 2); 
	var f=toDate.substring(0, 2); 

	var datestatus = true;
	
	if(parseInt(a)>parseInt(b)){
		errorMessage += "To Date Must Be GreaterThan from date\n";
		status = false;
		datestatus=false;
	}
	if(datestatus){
	if(parseInt(a)==parseInt(b)){
		if(parseInt(c)>parseInt(d)){
			errorMessage += "To Date Must Be GreaterThan from date\n";
			status = false;
			datestatus=false;
		}
		
	}
	}
	
	if(datestatus){
		if(parseInt(c)==parseInt(d)){
		if(parseInt(e)>parseInt(f)){
			errorMessage += "To Date Must Be GreaterThan from date\n";
			status = false;
			datestatus=false;
		}
		}
	}
	
	if((parseInt(totalAmt))<1){
		
		errorMessage += "Please Enter Amounts\n";
		status = false;
		
	}
	
	if(status){
	var requestDetails = {

				"transitAmt" :transitAmt,
				"travellingTo" :travellingTo,
				"claimPurpose" :claimPurpose,
				"fromDate" :fromDate,
				"toDate" :toDate,
				"noOfDays" :noOfDays,
				"perDayFoodandAccmAmt" :perDayFoodandAccmAmt,
				"foodandAccmAmt" :foodandAccmAmt,
				"daAmt" :daAmt,
				"taxiAmt" :taxiAmt,
				"totalAmt" :totalAmt,											
				"param" : "submitTadaAdvanceCumRequest",
				"type" :"tadaWaterApproval"			
		};
	
	$jq.ajax( {
		type : "POST",
		url : "tadaWaterRequest.htm?",
		data : requestDetails,
		cache : false,
		success : function(html) {
			$jq('#result').html(html);
			if ($jq('.success').is(':visible')) {
				var check=confirm(" TADA WATER Request has been Successfully Sent...\nPreview TADA WATER Application Form & print from here");
				if(check){
					
					document.forms[0].requestId.value = $jq.trim(requestIDtadaA);
					
					document.forms[0].param.value = "tadaWaterRequestDetails";
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
//added by bkr 29/04/2016 for tada water advance cum request
function multiplyFunction(){
	var noOfDays=$jq('#noOfDays').val();
	var amountPerDay=$jq('#perDayFoodandAccmAmt').val();
	var sum=noOfDays*amountPerDay;
	$jq('#foodandAccmAmt').val(sum);
	
	var transit=$jq('#transitAmt').val();
	var taxi=$jq('#taxiAmt').val();
	if(taxi==="" || taxi===null){
		taxi=0;
	}
	
	
	var daamt=$jq('#daAmt').val();
	var foodamt=$jq('#foodandAccmAmt').val();
	var sum1=(parseInt(transit)+parseInt(taxi)+parseInt(foodamt)+parseInt(daamt));
	$jq('#totalAmt').val(sum1);
	
}
//added by bkr 29/04/2016 for tada water advance cum request
function clearTadaAdvanceCumReq(){
	$jq('#claimPurpose').val('');
	$jq('#travellingTo').val('');
	$jq('#fromDate').val('');
	$jq('#toDate').val('');
	$jq('#perDayFoodandAccmAmt').val('0');
	$jq('#transitAmt').val('0');
	$jq('#taxiAmt').val('0');
	$jq('#totalAmt').val('0');
	$jq('#daAmt').val('0');
	$jq('#noOfDays').val('0');
	$jq('#foodandAccmAmt').val('0');
	
}
//added by bkr 29/04/2016 for tada water advance cum request
function noOfdays(){
if($jq('#toDate').val()!=''){
	if($jq('#fromDate').val()!=''){
		var firstDate=$jq('#toDate').val();
		var	secondDate=$jq('#fromDate').val();
		
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
            $jq('#noOfDays').val(diffdays);
	}
	
}
}
//added by bkr 29/04/2016 for tada water advance cum request
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
//this function added by bkr 22/04/2016
function setFoodAmtPerDayZero(){
	$jq('#foodAmtPerDay').val('0');
	$jq('#noOfDaysFood').val('0');
	$jq('#totalFoodAmt').val('0');
	
}

//this function added by bkr 22/04/2016
function multiolyFunction(){
	var noOfDays=$jq('#noOfDaysAcc').val();
	var amountPerDay=$jq('#accAmtPerDay').val();
	var sum=noOfDays*amountPerDay;
	$jq('#totalAccAmt').val(sum);
	//alert(sum);
		noOfDays=$jq('#noOfDaysFood').val();
		amountPerDay=$jq('#foodAmtPerDay').val();
		sum=noOfDays*amountPerDay;
		$jq('#totalFoodAmt').val(sum);
		//alert(sum);
	
}


function submitDaDetails(){
	var errorMessage = "";
	var status = true;
	if ($jq('#cityClass').val()=='Select') {
		errorMessage += "Please Select City Type.\n";
		$jq('#payRangeFrom').focus();
		status = false;
	}
	if ($jq('#payRangeFrom').val()=='') {
		errorMessage += "Please Enter Pay Range From.\n";
		$jq('#payRangeFrom').focus();
		status = false;
	}
	if ($jq('#payRangeTo').val()=='') {
		errorMessage += "Please Enter Pay Range To.\n";
		$jq('#payRangeTo').focus();
		status = false;
	}
	if ($jq('#ord').val()=='') {
		errorMessage += "Please Enter ORD Amount.\n";
		$jq('#ord').focus();
		status = false;
	}
	if ($jq('#hotel').val()=='') {
		errorMessage += "Please Enter Hotel Amount.\n";
		$jq('#hotel').focus();
		status = false;
	}
	var payRangeFrom=parseFloat($jq('#payRangeFrom').val());
	var payRangeTo=parseFloat($jq('#payRangeTo').val());
	if(payRangeFrom > payRangeTo){
		errorMessage += "Pay Range From must be Less than Pay Range To.\n";
		$jq('#payRangeFrom').val('');
		$jq('#payRangeTo').val('');
		$jq('#payRangeFrom').focus();
		status = false;
	}

	if (status) {
		var requestDetails = {
				"nodeID" : nodeID,
				"cityClassId" : $jq('#cityClass').val(),
				"payRangeFrom" : $jq('#payRangeFrom').val(),
				"payRangeTo" : $jq('#payRangeTo').val(),
				"ord" : $jq('#ord').val(),
				"hotel" : $jq('#hotel').val(),
				"param" : "manageDaDetails"
			};
			$jq.ajax( {
				type : "POST",
				url : "tada.htm?"+dispUrl,
				data : requestDetails,
				cache : false,
				success : function(html) {
					$jq('#result').html(html);
					if ($jq('.success').is(':visible')) {
						clearDaDetails();
					}
				}
			});

	} else {
		alert(errorMessage);
	}
}
function editDaDetails(id,cityClass,payRangeFrom,payRangeTo,ord,hotel) {
	nodeID=id;
	$jq('#cityClass').val(cityClass);
	$jq('#payRangeFrom').val(payRangeFrom);
	$jq('#payRangeTo').val(payRangeTo);
	$jq('#ord').val(ord);
	$jq('#hotel').val(hotel);
}

function deleteDaDetails(id) {
	var requestDetails = {
		"id" : id,
		"param" : "deleteDaDetails"
	};
	$jq.ajax( {
		type : "POST",
		url : "tada.htm",
		data : requestDetails,
		cache : false,
		success : function(html) {
			$jq('#result').html(html);
			if ($jq('.success').is(':visible')) {
				clearDaDetails();
			}
		}
	});
}
function clearDaDetails() {
	nodeID=0;
	$jq('#cityClass').val('Select');
	$jq('#payRangeFrom').val('');
	$jq('#payRangeTo').val('');
	$jq('#ord').val('');
	$jq('#hotel').val('');
	
}
function enableEntitlement(){
	// $jq('#travelType').find('Select').remove().end();
	$jq("#travelType :not(:first)").each(function() {
		$jq(this).remove();
	});
	if(travelTypeMapDetailsJSON!=null){
		for(var i=0;i<travelTypeMapDetailsJSON.length;i++){
			var gradePay=travelTypeMapDetailsJSON[i].id;
			if($jq('#gradePay option:selected').text()==gradePay){
				for(var j=0;j<travelTypeListMapJSON.length;j++){
					if(travelTypeListMapJSON[j].id==travelTypeMapDetailsJSON[i].travelTypeId){
						$jq('#travelType').append($jq('<option value='+travelTypeListMapJSON[j].id+'>'+travelTypeListMapJSON[j].travelType+'</option>'));
					}
				}
            }
		}
	}
}
function enableTravelTypeClass(){
	$jq('#SelectRight').find('option').remove().end();
	$jq('#SelectLeft').find('option').remove().end();
	for(var s=0;s<entitleClassListJSON.length;s++){
		var flag=true;
		var ttid=entitleClassListJSON[s].entitleTypeId;
		if($jq('#travelType').val()==ttid){
			for(var i=0;i<taEntitleClassListJSON.length;i++){
				if($jq('#gradePay').val()==taEntitleClassListJSON[i].gradePay){
					if(taEntitleClassListJSON[i].entitleClassId==entitleClassListJSON[s].id){
						$jq('#SelectRight').append($jq('<option value='+entitleClassListJSON[s].id+'>'+entitleClassListJSON[s].entitleClass+'</option>'+''));
						flag=false;
						break;
					}
				}
			}
			if(flag){
				$jq('#SelectLeft').append($jq('<option value='+entitleClassListJSON[s].id+'>'+entitleClassListJSON[s].entitleClass+'</option>'+''));
			}
			
		}
	}
}

function enableConveyanceMode(){
	if(travelTypeMapDetailsJSON!=null){
		// $jq('#traveltype').append($jq('<div class="line">'+'<div id="cMode"
		// class="quarter leftmar">'+'Mode Of Conveyance'+'<span
		// class="mandatory">*</span></div></div>')).css("display","block");
		$jq('#travelType').append($jq('<div class="line">'+'<div class="quarter leftmar">'+'Mode Of Conveyance'+'<span class="mandatory">*</span></div><div id="cMode" class="half">'+'</div></div>')).css("display","block");
		for(var i=0;i<travelTypeMapDetailsJSON.length;i++){
			var gradePay=travelTypeMapDetailsJSON[i].id;
			var ttid=travelTypeMapDetailsJSON[i].travelTypeId;
			for( var s=0;s<travelTypeListJSON.length;s++){
				if(travelTypeListJSON[s].id==travelTypeMapDetailsJSON[i].travelTypeId){
					$jq('#cMode').append($jq('<div class="quarter">'+'<input id='+travelTypeMapDetailsJSON[i].flag+' type="radio" value='+travelTypeMapDetailsJSON[i].travelTypeId+' onclick="javascript:enableEntitleClass();" name="conveyanceMode"><label for='+travelTypeListJSON[s].id+'>'+travelTypeListJSON[s].travelType+'</label>'+''));
				}
			}
		}
	}
}
function enableEntitleClass(){
	$jq("#entitleClass :not(:first)").each(function() {
		$jq(this).remove();
	});
	$jq('#entitleClass').append($jq('<div class="line">'+'<div class="quarter leftmar">'+'Class Of Entitlement'+'<span class="mandatory">*</span></div><div class="quarter"><select id="entitleId"  style=width:65%>'+'<option value=Select>Select</option>'+'</select>'+'</div></div>')).css("display","block");
	for(var i=0;i<travelTypeMapDetailsJSON.length;i++){
		if($jq('input:radio[name=conveyanceMode]:checked').val()==travelTypeMapDetailsJSON[i].travelTypeId){
			for(var j=0;j<entitleClassListJSON.length;j++){
				for(var k=0;k<taEntitleClassListJSON.length;k++){
					if(entitleClassListJSON[j].id==taEntitleClassListJSON[k].entitleClassId){
						if(travelTypeMapDetailsJSON[i].travelTypeId==entitleClassListJSON[j].entitleTypeId){
						    $jq('#entitleId').append('<option value='+entitleClassListJSON[j].id+'>'+entitleClassListJSON[j].entitleClass+'</option>');
						}
						
					}
				}
			}
		}
	}
}
function submitEntitlement(){
	var selectedValues = "";
	var deSelectedValues = "";
	var errorMessages = "";
	var flag = true;
	var status = false;
	if ($jq('#gradePay').val() == 'Select') {
		errorMessages += "Please Select Grade Pay.\n";
		document.getElementById('travelType').focus();
		flag = false;
	}
	if ($jq('#travelType').val()== 'Select') {
		errorMessages += "Please Select Travel Type.\n";
		document.getElementById('travelType').focus();
		flag = false;
	}
	if(document.getElementById('SelectRight').options.length==0){
		errorMessages += "Please Select Entitlement Class.\n";
		flag = false;
	}
	for ( var i = 0; i < document.getElementById('SelectRight').options.length; i++) {
		selectedValues += document.getElementById('SelectRight').options[i].value+ ",";
		status = true;
	}
	for ( var i = 0; i < document.getElementById('SelectLeft').options.length; i++) {
		deSelectedValues += document.getElementById('SelectLeft').options[i].value+ ",";
		status = true;
	}
	if (flag) {
		var requestDetails = {
				"gradePay" : $jq('#gradePay').val(),
				"travelTypeId" : $jq('#travelType').val(),
				"entitleClass" : selectedValues,
				"nonEntitleClass" : deSelectedValues,
				"param" : "manageEntitlement"
		};
		
		$jq.ajax( {
			type : "POST",
			url : "tada.htm?"+dispUrl,
			data : requestDetails,
			cache : false,
			success : function(html) {
				$jq('#result').html(html);
				if ($jq('.success').is(':visible')) {
					clearEntitlement();
				}
			}
		});
	} else {
		alert(errorMessages);
	}
}
function clearEntitlement(){
	$jq('#gradePay').val('Select');
	$jq('#travelType').val('Select');
	$jq('#SelectRight').find('option').remove().end();
	$jq('#SelectLeft').find('option').remove().end();
}
function submitEntitleType(){
	var errorMessage = "";
	var status = true;
	if ($jq('#travelType').val()=='Select') {
		errorMessage += "Please Select Entitlement Type.\n";
		status = false;
	}
	if ($jq('#entitleClass').val()=='') {
		errorMessage += "Please Enter Class Of Entitlement.\n";
		$jq('#entitleClass').focus();
		status = false;
	}
	if (status) {
		var requestDetails = {
				"nodeID" : nodeID,
				"entitleTypeId" : $jq('#travelType').val(),
				"entitleClass" : $jq('#entitleClass').val(),
				"description" : $jq('#description').val(),
				"param" : "manageEntitleType"
			};
			$jq.ajax( {
				type : "POST",
				url : "tada.htm?"+dispUrl,
				data : requestDetails,
				cache : false,
				success : function(html) {
					$jq('#result').html(html);
					if ($jq('.success').is(':visible')) {
						clearEntitleType();
					}
				}
			});

	} else {
		alert(errorMessage);
	}
}
function getEntitleClassList(){
	var errorMessage = "";
	var status = true;
	if (status) {
		var requestDetails = {
				"nodeID" : nodeID,
				"entitleTypeId" : $jq('#travelType').val(),
				"param" : "entitleClassList"
			};
			$jq.ajax( {
				type : "POST",
				url : "tada.htm?"+dispUrl,
				data : requestDetails,
				cache : false,
				success : function(html) {
					$jq('#result').html(html);
					if ($jq('.success').is(':visible')) {
					}
				}
			});

	} else {
		alert(errorMessage);
	}
}
function editEntitleType(id,entitleType,entitleClass,description){
	nodeID=id;
	$jq('#travelType').val(entitleType);
	$jq('#entitleClass').val(entitleClass);
	$jq('#description').val(description);
}
function clearEntitleType() {
	nodeID=0;
    $jq('#travelType').val('Select');
	$jq('#entitleClass').val('');
	$jq('#description').val('');
}
function deleteEntitleType(id) {
	var requestDetails = {
		"id" : id,
		"entitleTypeId" : $jq('#travelType').val(),
		"param" : "deleteEntitleType"
	};
	$jq.ajax( {
		type : "POST",
		url : "tada.htm",
		data : requestDetails,
		cache : false,
		success : function(html) {
			$jq('#result').html(html);
			if ($jq('.success').is(':visible')) {
				clearEntitleType();
			}
		}
	});
}
function submitCityType(){
	var errorMessage = "";
	var status = true;
	if ($jq('#cityClass').val()=='Select') {
		errorMessage += "Please Select Class Of City\n";
		$jq('#payRangeFrom').focus();
		status = false;
	}
	if ($jq('#cityName').val()=='') {
		errorMessage += "Please Enter Name Of City\n";
		$jq('#payRangeTo').focus();
		status = false;
	}
	
	if (status) {
		var requestDetails = {
				"nodeID" : nodeID,
				"cityClass" : $jq('#cityClass').val(),
				"cityName" : $jq('#cityName').val(),
				"param" : "manageCityType"
			};
			$jq.ajax( {
				type : "POST",
				url : "tada.htm?"+dispUrl,
				data : requestDetails,
				cache : false,
				success : function(html) {
					$jq('#result').html(html);
					if ($jq('.success').is(':visible')) {
						clearCityType();
					}
				}
			});

	} else {
		alert(errorMessage);
	}
}
function cityTypeList(){
	var errorMessage = "";
	var status = true;
	if (status) {
		var requestDetails = {
				"nodeID" : nodeID,
				"cityClass" : $jq('#cityClass').val(),
				"param" : "cityTypeList"
			};
			$jq.ajax( {
				type : "POST",
				url : "tada.htm?"+dispUrl,
				data : requestDetails,
				cache : false,
				success : function(html) {
					$jq('#result').html(html);
					if ($jq('.success').is(':visible')) {
						
					}
				}
			});

	} else {
		alert(errorMessage);
	}
}
function editCityType(id,cityClass,cityName) {
	nodeID=id;
	$jq('#cityClass').val(cityClass);
	$jq('#cityName').val(cityName);
}
function clearCityType() {
	nodeID=0;
	$jq('#cityClass').val('select');
	$jq('#cityName').val('');
	
}
function deleteCityType(id) {
	var requestDetails = {
		"id" : id,
		"cityClass" : $jq('#cityClass').val(),
		"param" : "deleteCityType"
	};
	$jq.ajax( {
		type : "POST",
		url : "tada.htm",
		data : requestDetails,
		cache : false,
		success : function(html) {
			$jq('#result').html(html);
			if ($jq('.success').is(':visible')) {
				clearCityType();
			}
		}
	});
}

function submitTravelType(){
	var errorMessage = "";
	var status = true;
	if ($jq('#travelType').val()=='') {
		errorMessage += "Please Enter Travel Type\n";
		$jq('#travelType').focus();
		status = false;
	}
	if (status) {
		var requestDetails = {
				"nodeID" : nodeID,
				"travelType" : $jq('#travelType').val(),
				"description" : $jq('#description').val(),
				"param" : "manageTravelType"
			};
			$jq.ajax( {
				type : "POST",
				url : "tada.htm?"+dispUrl,
				data : requestDetails,
				cache : false,
				success : function(html) {
					$jq('#result').html(html);
					if ($jq('.success').is(':visible')) {
						clearTravelType();
					}
				}
			});

	} else {
		alert(errorMessage);
	}

}
function editTravelType(id,travelType,description) {
	nodeID=id;
	$jq('#travelType').val(travelType);
	$jq('#description').val(description);
}
function clearTravelType() {
	nodeID=0;
	$jq('#travelType').val('');
	$jq('#description').val('');
	
}
function deleteTravelType(id) {
	var requestDetails = {
		"id" : id,
		"param" : "deleteTravelType"
	};
	$jq.ajax( {
		type : "POST",
		url : "tada.htm",
		data : requestDetails,
		cache : false,
		success : function(html) {
			$jq('#result').html(html);
			if ($jq('.success').is(':visible')) {
				clearCityType();
			}
		}
	});
}
function submitTravelTypeMap() {
	var tempJSON={};
	var tempTravelJSON={};
	for(var s=0;s<travelTypeListMapJSON.length;s++){
		var travelJSON={};
		travelJSON['travelType']=travelTypeListMapJSON[s].travelType;
		travelJSON['id']=travelTypeListMapJSON[s].id;
		tempTravelJSON[s]=travelJSON;
	}
	for(var j=0;j<gradePayListMapJSON.length;j++){
		for(var i=0;i<travelTypeListMapJSON.length;i++){
		    var exceptionJSON={};
		    exceptionJSON['gradePay']=gradePayListMapJSON[j].gradePay;
		    tempJSON[j]=exceptionJSON;
		}
	}
	
	var mainjson={};
	var counter=0;
	
	for(var j=0;j<gradePayListMapJSON.length;j++){
		if(tempJSON[j]!=null){
			var gpID=tempJSON[j].gradePay;
			for(var k=0;k<travelTypeListMapJSON.length;k++){
				if($jq('#gradePayList').find('tr').eq(j+1).find('td').eq(k+1).find('input').is(':checked')==false){
					var temp={};
					temp['gradePay']=tempJSON[j].gradePay;
					temp['travelType']=tempTravelJSON[k].travelType;
					temp['key']=j+1;
					temp['value']=k+1;
					temp['travelTypeId']=tempTravelJSON[k].id;
					temp['type']="noCheck";
					mainjson[counter]=temp;
					counter=counter+1;
				}else if($jq('#gradePayList').find('tr').eq(j+1).find('td').eq(k+1).find('input').is(':checked')==true){
					var temp={};
					temp['gradePay']=tempJSON[j].gradePay;
					temp['travelType']=tempTravelJSON[k].travelType;
					temp['key']=j+1;
					temp['value']=k+1;
					temp['travelTypeId']=tempTravelJSON[k].id;
					temp['type']="check";
					mainjson[counter]=temp;
					counter=counter+1;
				}
			}
		}
		
	}
	var requestDetails = {
			"exceptionsJson" : JSON.stringify(mainjson),
			"param" : "manageTravelTypeMap"
		};
		$jq.ajax( {
			type : "POST",
			url : "tada.htm?"+dispUrl,
			data : requestDetails,
			cache : false,
			success : function(html) {
				$jq('#result').html(html);
				if ($jq('.success').is(':visible')) {
				}
			}
		});
}
function loadTravelTypeMapp(){
	for(var i=0;i<gradePayListMapJSON.length;i++){
		for(var j=0;j<travelTypeMapDetailsJSON.length;j++){
			if($jq('#gradePayList').find('tr').eq(i+1).find('td').eq(0).text()==travelTypeMapDetailsJSON[j].id){
				for(k=0;k<travelTypeListMapJSON.length;k++){
					if($jq('#gradePayList').find('tr').eq(0).find('th').eq(k+1).text()==travelTypeListMapJSON[k].travelType){
						if(travelTypeMapDetailsJSON[j].travelTypeId==travelTypeListMapJSON[k].id){
							$jq('#gradePayList').find('tr').eq(i+1).find('td').eq(k+1).find('input').attr('checked',true);
						}
					}
				}
			}
		}
	}
}
function enableMilageAllw(){
	if($jq('input:radio[name=milageAllw]:checked').val()=="perkm"){
	    $jq('#travelById').show();
	    $jq('#distanceId').show();
	    $jq('#amountId').hide();
	}
	if($jq('input:radio[name=milageAllw]:checked').val()=="perday"){
		$jq('#amountId').show();
		$jq('#travelById').hide();
	    $jq('#distanceId').hide();
	}
}
function submitDaNewDetails(){
	var errorMessage = "";
	var status = true;
	if ($jq('#gradePay option:selected').text()=='Select') {
		errorMessage += "Please Select Grade Pay.\n";
		$jq('#gradePay').focus();
		status = false;
	}
	if ($jq('#accommodationBill').val()=='') {
		errorMessage += "Please Enter Accommodation Bill.\n";
		$jq('#accommodationBill').focus();
		status = false;
	}
	if ($jq('#foodBill').val()=='') {
		errorMessage += "Please Enter Food Bill.\n";
		$jq('#foodBill').focus();
		status = false;
	}
	if($jq('input:radio[name=milageAllw]:checked').val()!="perkm" && $jq('input:radio[name=milageAllw]:checked').val()!="perday"){
		errorMessage += "Please Select Milage Allowances.\n";
		status = false;
	}
	if ($jq('input:radio[name=milageAllw]:checked').val()=="perkm"){
		if($jq('#travelBy').val()=='Select'){
			errorMessage += "Please Select Travel By.\n";
			status = false;
		}
		if($jq('#distance').val()==''){
			errorMessage += "Please Enter Distance.\n";
			$jq('#distance').focus();
			status = false;
		}
	}
	if ($jq('input:radio[name=milageAllw]:checked').val()=="perday"){
		if($jq('#amount').val()==''){
			errorMessage += "Please Enter Amount Per Day.\n";
			$jq('#amount').focus();
			status = false;
		}
	}
	if (status) {
		var requestDetails = {
				"nodeID" : nodeID,
				"gradePay" : $jq('#gradePay option:selected').val(),
				"accommodationBill" : $jq('#accommodationBill').val(),
				"foodBill" : $jq('#foodBill').val(),
				"milageAllw" : $jq('input:radio[name=milageAllw]:checked').val(),
				"travelBy" : $jq('#travelBy').val(),
				"distance" : $jq('#distance').val(),
				"amount" : $jq('#amount').val(),
				"param" : "manageDaNewDetails"
			};
			$jq.ajax( {
				type : "POST",
				url : "tada.htm?"+dispUrl,
				data : requestDetails,
				cache : false,
				success : function(html) {
					$jq('#result').html(html);
					if ($jq('.success').is(':visible')) {
						clearDaNewDetails();
					}
				}
			});

	} else {
		alert(errorMessage);
	}
}
function editDaNewDetails(id,gradePay,accommodationBill,foodBill,milageAllw,travelBy,distance,amount){
	nodeID=id;
	$jq('#gradePay').val(gradePay);
	$jq('#accommodationBill').val(accommodationBill);
	$jq('#foodBill').val(foodBill);
	if(milageAllw=='perkm'){
		$jq("#milageAllw[value=" + milageAllw + "]").attr('checked', true);
		$jq('#travelById').show();
	    $jq('#distanceId').show();
	} else {
		$jq("#milageAllw[value=" + milageAllw + "]").attr('checked', true);
		$jq('#amountId').show();
	}
	$jq('#travelBy').val(travelBy);
	$jq('#distance').val(distance);
	$jq('#amount').val(amount);
}
function clearDaNewDetails(){
	$jq('#gradePay').val('Select');
	$jq('#accommodationBill').val('');
	$jq('#foodBill').val('');
	$jq('input:radio').attr("checked",false);
	$jq('#travelBy').val('');
	$jq('#distance').val('');
	$jq('#amount').val('');
	$jq('#amountId').hide();
	$jq('#travelById').hide();
    $jq('#distanceId').hide();
}
function deleteDaNewDetails(id) {
	var requestDetails = {
		"id" : id,
		"param" : "deleteDaNewDetails"
	};
	$jq.ajax( {
		type : "POST",
		url : "tada.htm",
		data : requestDetails,
		cache : false,
		success : function(html) {
			$jq('#result').html(html);
			if ($jq('.success').is(':visible')) {
				clearDaNewDetails();
			}
		}
	});
}
function submitTdApproval(){
	var errorMessage = "";
	var status = true;
	var reqJourneyDetailsJson={};
	var reqJourneyDetailsArry=new Array();
	var mainJson={};
	var flag=false;
	var requestId;
	var amendmentType = "";
	var projDirName='';
	var totalStayDays=0;
	var selectedValues='';
	var ticketCancelCharges=0;
	var ticketCancelReason='';
	
	/*added by bkr 21/04/2016  oneline inly*/ 
	
	document.getElementById("authorizedMove").checked = true;
	//$jq( "#authorizedMove" ).prop( "checked", true );
	//alert("authorizedMove"+document.getElementById("authorizedMove"));
	
	if($jq('input:radio[name=amendmentType]:checked').val()=='amendment'){
		if($jq('#stayDuration').val()==tadaRequestBean.stayDuration && $jq('#tdWorkPlace').val()==tadaRequestBean.tdWorkPlace && 
				$jq('#purpose').val()==tadaRequestBean.purpose && 
				$jq('input:radio[name=authorizedMove]:checked').val()==tadaRequestBean.authorizedMove && 
				$jq('input:radio[name=tadaRequirement]:checked').val()==tadaRequestBean.daType){
			if((parseFloat(document.getElementById("requestJourneyDetailsId").rows.length)-1)==parseFloat(tadaRequestBean.tadaTdReqJourneyList.length)){
				var t=0;
				$jq('#requestJourneyDetailsId tr:not(:first)').each(function(){
					for(var u=t;u<tadaRequestBean.tadaTdReqJourneyList.length;u++){
						if($jq(this).find('td').eq(0).find('input').val()!=tadaRequestBean.tadaTdReqJourneyList[u].strDepartureDate || 
								$jq(this).find('td').eq(1).find('input').val()!=tadaRequestBean.tadaTdReqJourneyList[u].strArrivalDate ||
								$jq(this).find('td').eq(4).find('input').val()!=tadaRequestBean.tadaTdReqJourneyList[u].noOfDays || 
								$jq(this).find('td').eq(7).find('select').val()!=tadaRequestBean.tadaTdReqJourneyList[u].tatkalQuota){
							flag=true;
						}
						if(flag==false){
							if($jq(this).find('td').eq(2).find('select').val()!='Other'){
								if($jq(this).find('td').eq(2).find('select').val()!=tadaRequestBean.tadaTdReqJourneyList[u].fromPlace){
									flag=true;
								}
							} else{
								if($jq(this).find('td').eq(2).find('input').val()!=tadaRequestBean.tadaTdReqJourneyList[u].fromPlace){
									flag=true;
								}
							}
						}
						if(flag==false){
							if($jq(this).find('td').eq(3).find('select').val()!='Other'){
								if($jq(this).find('td').eq(3).find('select').val()!=tadaRequestBean.tadaTdReqJourneyList[u].toPlace){
									flag=true;
								}
							} else{
								if($jq(this).find('td').eq(3).find('input').val()!=tadaRequestBean.tadaTdReqJourneyList[u].toPlace){
									flag=true;
								}
							}
						}
						if(flag==false){
							for( var s=0;s<travelTypeListJSON.length;s++){
								if(travelTypeListJSON[s].travelType==tadaRequestBean.tadaTdReqJourneyList[u].conveyanceMode){
									if($jq(this).find('td').eq(5).find('select').val()!=travelTypeListJSON[s].id){
										flag=true;
									}
								}
							}
							for(var s=0;s<entitleClassListJSON.length;s++){
								if(entitleClassListJSON[s].entitleClass==tadaRequestBean.tadaTdReqJourneyList[u].classOfEntitlement){
									if($jq(this).find('td').eq(6).find('select').val()!=entitleClassListJSON[s].id){
										flag=true;
									}
								}
							}
						}
						break;
					}
					t++;
				});
				if(flag==false){                                                            //This condition has been added for amendment check                                               
					if($jq('#projDirName').is(':visible')){
						if($jq('#projDirName').val()==tadaRequestBean.projDirName){
							 errorMessage ="You didn't change any value so please change atleast one value before submit the request.\n";
							   status=false;
						}
					}else{
						errorMessage ="You didn't change any value so please change atleast one value before submit the request.\n";
						   status=false;
					}
					}
			}else if((parseFloat(document.getElementById("requestJourneyDetailsId").rows.length)-1)>parseFloat(tadaRequestBean.tadaTdReqJourneyList.length)){
				flag=true;
			}else{
				alert("You deleted "+(parseFloat(tadaRequestBean.tadaTdReqJourneyList.length)-(parseFloat(document.getElementById("requestJourneyDetailsId").rows.length)-1)).toString()+" row from journey details.Do you want to continue");
				flag=true;
			}
		}else if($jq('#stayDuration').val()!=tadaRequestBean.stayDuration || 
				$jq('#tdWorkPlace').val()!=tadaRequestBean.tdWorkPlace || 
				$jq('#purpose').val()!=tadaRequestBean.purpose || 
				$jq('input:radio[name=authorizedMove]:checked').val()!=tadaRequestBean.authorizedMove || 
				$jq('input:radio[name=tadaRequirement]:checked').val()!=tadaRequestBean.daType){
			flag=true;
		}
		if(flag==false){
			var flag1=false;
			if($jq('#ticketCancelReason').is(':visible') && $jq('#ticketCancelReason').val()==''){
				errorMessage += "Please Enter Reason for ticket(s) cancellation";
				status = false;
				flag1=true;
			}
			if(flag1){
				errorMessage += "You didn't change any value so please change atleast one value before submit the request.\n";
				status = false;
			}
		}
		if($jq('#remarks').val()==''){
			errorMessage +="Please Enter Reason for Amendment.\n";
			status=false;
			$jq('#remarks').focus();
		}
		if($jq('#ticketCancelReason').is(':visible') && $jq('#ticketCancelReason').val()==''){
			errorMessage +="Please Enter Reason for ticket(s) cancellation.\n";
			status=false;
			$jq('#ticketCancelReason').focus();
		}
	} else if($jq('input:radio[name=amendmentType]:checked').val()=='cancel'){
		if($jq('#remarks').val()==''){
			errorMessage +="Please Enter Reason for Cancellation.\n";
			status=false;
			$jq('#remarks').focus();
		}
		if($jq('#ticketCancelReason').is(':visible') && $jq('#ticketCancelReason').val()==''){
			errorMessage +="Please Enter Reason for ticket(s) cancellation.\n";
			status=false;
			$jq('#ticketCancelReason').focus();
		}
	}else if($jq('input:radio[name=amendmentType]').is(':visible')){
		if(!$jq('input:radio[name=amendmentType]').is(':checked')){
			errorMessage +="Please Select Amendment Type.\n";
			status=false;
		}
	}
	if ($jq('#stayDuration').val()=='') {
		errorMessage += "Please Enter Stay Duration.\n";
		$jq('#stayDuration').focus();
		status = false;
	}

	if ($jq('#workingPlace').val()=='') {
		errorMessage += "Please Enter TD Place.\n";
		$jq('#tdWorkPlace').focus();
		status = false;
	}

	if ($jq('#tdWorkPlace').val()=='') {
		errorMessage += "Please Enter TD Work Place.\n";
		$jq('#tdWorkPlace').focus();
		status = false;
	}
	if ($jq('#purpose').val()=='') {
		errorMessage += "Please Enter Purpose.\n";
		$jq('#purpose').focus();
		status = false;
	}
	if(!$jq('input:radio[name=authorizedMove]').is(':checked')){
		errorMessage += "Please Select Project For Which Move is Authorized.\n";
		status = false;
	}
	
	/*commented by bkr 21/04/2016 */
	/*if(!$jq('input:radio[name=tadaRequirement]').is(':checked')){
		errorMessage += "Please Select Whether TA/DA is required with Hotel/Normal rates.\n";
		status = false;
	}*/
	if(parseFloat($jq('#stayDuration').val())<=0){
		errorMessage += "Total Stay Duration at Out Station should be grater than or equal to one day.\n";
		status = false;
	}
	if($jq('#requestJourneyDetailsId').is(':visible')){
		$jq('#requestJourneyDetailsId tr:not(:first)').each(function(){
			if($jq(this).find('td').eq(0).find('input').val()=='' && $jq(this).find('td').eq(1).find('input').val()=='' && $jq(this).find('td').eq(2).find('select').val()=='Select' && $jq(this).find('td').eq(3).find('select').val()=='Select' && $jq(this).find('td').eq(5).find('select').val()=='Select' && $jq(this).find('td').eq(6).find('select').val()=='Select'){
				errorMessage += "Please Fill all details in journey details.\n";
				status = false;
			}else{
				if($jq(this).find('td').eq(0).find('input').val()==''){
					errorMessage += "Please Choose Departure Date in journey details.\n";
					status = false;
				}
				if($jq(this).find('td').eq(1).find('input').val()==''){
					errorMessage += "Please Choose Arrival Date in journey details.\n";
					status = false;
				}
				if($jq(this).find('td').eq(2).find('select').val()=='Select'){
					errorMessage += "Please Select From Place in journey details.\n";
					status = false;
				}
				if($jq(this).find('td').eq(2).find('select').val()=='Other' && $jq(this).find('td').eq(2).find('input').val()==''){
					errorMessage += "Please Enter From Place in journey details.\n";
					status = false;
				}
				if($jq(this).find('td').eq(3).find('select').val()=='Select'){
					errorMessage += "Please Select To Place in journey details.\n";
					status = false;
				}
				if($jq(this).find('td').eq(3).find('select').val()=='Other' && $jq(this).find('td').eq(3).find('input').val()==''){
					errorMessage += "Please Enter To Place in journey details.\n";
					status = false;
				}
				if($jq(this).find('td').eq(5).find('select').val()=='Select'){
					errorMessage += "Please Select Mode Of Conveyance in journey details.\n";
					status = false;
				}
				if($jq(this).find('td').eq(6).find('select').val()=='Select'){
					
					//$jq('#entitlementExemption2').val();
					document.getElementById("entitlementExemption2").checked = true;
					//errorMessage += "Please Select Class Of Entitlement in journey details11.\n";
				//	status = false;
				}
			}
		});
	}
	$jq('#requestJourneyDetailsId tr:not(:first)').each(function(){
		totalStayDays=parseFloat($jq(this).find('td').eq(4).find('input').val())+totalStayDays;
	});
	if(totalStayDays!=parseFloat($jq('#stayDuration').val())){
		errorMessage += "Stay duration at out station must be equal to sum of no. of days in journey details.\n";
		status = false;
		$jq('#stayDuration').focus()
	}
	if($jq('#projDirName').is(':visible')){
		if($jq('#projDirName').val()=='Select'){
			errorMessage += "Please select one project director.\n";
			status = false;
		}
	}
	if($jq('input:radio[name=leaveRequirement]:checked').val()=='1'){
		if(document.getElementById('SelectRight').options.length==1){
			if($jq('#SelectRight').find('option').val()=='select'){
				errorMessage += "Please select atleast one leave.\n";
				status = false;
			}else{
				for ( var i = 0; i < document.getElementById('SelectRight').options.length; i++) {
					selectedValues += document.getElementById('SelectRight').options[i].value+ ",";
					status = true;
				}
			}
		}else{
			for ( var i = 0; i < document.getElementById('SelectRight').options.length; i++) {
				selectedValues += document.getElementById('SelectRight').options[i].value+ ",";
				status = true;
			}
		}
		if(document.getElementById('SelectLeft').options.length==1){
			if($jq('#SelectLeft').find('option').val()=='no'){
				errorMessage += "Please select atleast one leave.\n";
				status = false;
			}
		}
	}
	if($jq('#requestJourneyDetailsId tr:last').find('td').eq(3).find('select').val()!='Hyderabad'){
		errorMessage += "You are not entered complete round trip journey details.\n";
		status = false;
	}
	if (status) {
		var i=0;
		$jq('#requestJourneyDetailsId tr:not(:first)').each(function(){
			var valueJson={};
			valueJson['departueDate']=$jq(this).find('td').eq(0).find('input').val();
			valueJson['arrivalDate']=$jq(this).find('td').eq(1).find('input').val();
			if($jq(this).find('td').eq(2).find('select').val()!='Other'){
				valueJson['fromPlace']=$jq(this).find('td').eq(2).find('select').val();
			}else {
				valueJson['fromPlace']=$jq(this).find('td').eq(2).find('input').val();
			}
			if($jq(this).find('td').eq(3).find('select').val()!='Other'){
				valueJson['toPlace']=$jq(this).find('td').eq(3).find('select').val();
			}else {
				valueJson['toPlace']=$jq(this).find('td').eq(3).find('input').val();
			}
			valueJson['days']=$jq(this).find('td').eq(4).find('input').val();
			valueJson['modeOfConveyance']=$jq(this).find('td').eq(5).find('select').val();
			valueJson['classOfEntitlement']=$jq(this).find('td').eq(6).find('select').val();
			valueJson['tatkalQuota']=$jq(this).find('td').eq(7).find('select').val();
			reqJourneyDetailsJson[i]=valueJson;
			reqJourneyDetailsArry[i]=valueJson;
			i++;
		});
		mainJson["reqJourneyDetails"]=reqJourneyDetailsJson;
		if($jq('input:radio[name=amendmentType]:checked').val()=='amendment' || $jq('input:radio[name=amendmentType]:checked').val()=='cancel'){
			amendmentType += "tadaApproval";
			requestId=tadaRequestBean.requestId;
		}
		if($jq('input:radio[name=authorizedMove]:checked').val()=='2'){
			if($jq('#projDirName').is(':visible'))
				projDirName=$jq('#projDirName').val();
			else
				projDirName=tadaRequestBean.projDirName;
		}
		var stayDuration='';
		var tdWorkPlace='';
		var workingPlace='';
		var purpose='';
		var authorizedMove='';
		var daType='';
		var remarks='';
		if($jq('input:radio[name=amendmentType]:checked').val()=='cancel'){
			stayDuration=0;
			tdWorkPlace=tadaRequestBean.tdWorkPlace;
			workingPlace=tadaRequestBean.workingPlace;
			purpose=tadaRequestBean.purpose;
			authorizedMove=tadaRequestBean.authorizedMove;
			daType=tadaRequestBean.daType;
			entitlementExemption=tadaRequestBean.entitleExemption;
			remarks=$jq('#remarks').val();
			if(!isNaN($jq('#ticketCancelCharges').val()) && (parseFloat($jq('#ticketCancelCharges').val())>0)){
				ticketCancelCharges=$jq('#ticketCancelCharges').val();
				ticketCancelReason=$jq('#ticketCancelReason').val();
			}
		}else{
			stayDuration=$jq('#stayDuration').val();
			//tdWorkPlace=$jq('#tdPlace').val()+'###'+$jq('#tdWorkPlace').val();
			tdWorkPlace=$jq('#tdWorkPlace').val();
			workingPlace=$jq('#workingPlace').val();
			purpose=$jq('#purpose').val();
			authorizedMove=$jq('input:radio[name=authorizedMove]:checked').val();
			daType=$jq('input:radio[name=tadaRequirement]:checked').val();
			entitlementExemption=$jq('input:radio[name=entitlementExemption]:checked').val();
			if($jq('#remarks').is(':visible')){
				remarks=$jq('#remarks').val();
			}
			if($jq('#ticketCancelCharges').is(':visible')){
				if(!isNaN($jq('#ticketCancelCharges').val()) && (parseFloat($jq('#ticketCancelCharges').val())>0)){
					ticketCancelCharges=$jq('#ticketCancelCharges').val();
					ticketCancelReason=$jq('#ticketCancelReason').val();
				}
			}
		}
		var tempDeptDate=reqJourneyDetailsArry[0].departueDate;
		var tempArrDate=reqJourneyDetailsArry[0].arrivalDate;
		for(var i=0;i<reqJourneyDetailsArry.length;i++){
			if((parseFloat(compareDates(convertDate(tempDeptDate),convertDate(reqJourneyDetailsArry[i].departueDate)))+1)<0){
				tempDeptDate=reqJourneyDetailsArry[i].departueDate;
			}
			if((parseFloat(compareDates(convertDate(tempArrDate),convertDate(reqJourneyDetailsArry[i].arrivalDate)))+1)>0){
				tempArrDate=reqJourneyDetailsArry[i].arrivalDate;
			}
		}
		var requestDetails = {
				"nodeID" : nodeID,
				"stayDuration" : stayDuration,
				"tdWorkPlace" : tdWorkPlace,
				"workingPlace" : workingPlace,
				"purpose" : purpose,
				"authorizedMove" : authorizedMove,
				"daType" : daType,
				"entitlementExemption" : entitlementExemption,
				"projDirName" : projDirName,
				"type" :"tadaApproval",
				"jsonValue" : JSON.stringify(mainJson),
				"amendmentReqId" : requestId,
				"amendmentType" :amendmentType,
				"leaveId" : selectedValues,
				"reason" : remarks,
				"departureDate" : tempDeptDate,
				"arrivalDate" : tempArrDate,
				"ticketCancelCharges" : ticketCancelCharges,
				"ticketCancelReason" : ticketCancelReason,
				"param" : "submitRequest"
			};
			$jq.ajax( {
				type : "POST",
				url : "tadaApprovalRequest.htm?"+dispUrl,
				data : requestDetails,
				cache : false,
				success : function(html) {
					$jq('#result').html(html);
					if ($jq('.success').is(':visible')) {
						if($jq('input:radio[name=amendmentType]').is(':visible'))
							$jq('input:radio[name=amendmentType]').attr('disabled','disabled');
						var check=confirm(" TADA Request has been Successfully Sent...\nPreview TADA Application Form & print from here");
						if(check){
						document.forms[0].requestId.value = $jq.trim(requestIDtadaA);
						
					   document.forms[0].param.value = "requestDetails";
						document.forms[0].action = "workflowmap.htm";
						document.forms[0].submit();	
						}
						//clearTdApproval();
					}
					if($jq('.TD request is already applied').is(':visible')){
						clearTdApproval();
					}
				}
			});

	} else {
		alert(errorMessage);
	}
}
function clearTdApproval(){
	$jq('#workingPlace').val('');
	$jq('#departureDate').val('');
	$jq('input:radio').attr("checked",false);
	$jq('#leaveRequirement2').attr('checked',true);
	$jq('#entitleId').val('');
	$jq('#stayDuration').val('');
	$jq('#stayDurationId').text(':0');                     //Clear the stayduration
	$jq('#tdWorkPlace').val('');
	$jq('#purpose').val('');
	
	$jq('#projDirNameDiv').hide();
	$jq('#entitleExemptionDiv').hide();
	$jq('#requestJourneyDetailsId tr:not(:first)').each(function(){
		$jq(this).find('td').eq(0).find('input').val('');
		$jq(this).find('td').eq(1).find('input').val('');
		//$jq(this).find('td').eq(2).find('select').val('Select');
		if($jq(this).find('td').eq(2).find('input').is(':visible')){
			$jq(this).find('td').eq(2).find('input').val('');
		}
		$jq(this).find('td').eq(3).find('select').val('Select');
		if($jq(this).find('td').eq(3).find('input').is(':visible')){
			$jq(this).find('td').eq(3).find('input').val('');
		}
		$jq(this).find('td').eq(4).find('input').val('0');
		$jq(this).find('td').eq(5).find('select').val('Select');
		$jq(this).find('td').eq(6).find('select').val('Select');
		$jq(this).find('td').eq(7).find('select').val('Select');
	});
	var tableID1='requestJourneyDetailsId';
	var dynRow1 = document.getElementById(tableID1);
	var i=dynRow1.rows.length-1;
	while(i!=1){
		dynRow1.deleteRow(i);
		dynRow1 = document.getElementById(tableID1);
		i=dynRow1.rows.length-1;
	}
	$jq('#SelectRight').find('option').remove().end();
	$jq('#SelectLeft').find('option').remove().end();
	$jq('#leaveDiv').hide();
	$jq('#remarksDiv').hide();
	if($jq('#ticketCancellationDiv').is(':visible')){
		$jq('#ticketCancellationDiv').hide();
		$jq('#ticketCancelReasonDiv').hide();
		$jq('#ticketCancelReasonDiv1').hide();
	}
	$jq('#ticketCancelCharges').val('');
	countRequestJourney=0;
}
function submitTdAdvance(requestId){
	var errorMessage = "";
	var status = true;
	var nodeID = "";
	var departureDate = "";
	var conveyanceMode = "";
	var entitlementClass = "";
	var stayDuration = "";
	var tdWorkPlace = "";
	var authorizedMove = "";
	var reqJourneyDetailsJson={};
	var mainJson={};
	var amendmentType ="";
	var amendmentReqId;
	var flag=false;
	var projectName="";
	if(tadaRequestBean.param=='amendment'){
		amendmentType ="tadaTdAdvance";
		amendmentReqId=tadaRequestBean.requestId;
	}
	
	for(var i=0;i<tdRequestDetails.length;i++){
		//departureDate = tdRequestDetails[i].departureDateOne;
		//conveyanceMode = tdRequestDetails[i].conveyanceMode;
		//entitlementClass = tdRequestDetails[i].entitlementClass;
		stayDuration = tdRequestDetails[i].stayDuration;
		tdWorkPlace = tdRequestDetails[i].tdWorkPlace;
		authorizedMove = tdRequestDetails[i].authorizedMove;
	}
	var s=1;
	$jq('#requestJourneyDetailsId tr:not(:first)').each(function(){
		if($jq(this).find('td').eq(7).find('input').val()==''){
			errorMessage += "Please Enter TicketFare in row-"+s+" of Journy Details.\n";
			status = false;
		}
		s++;
	});
	if(tadaRequestBean.param=='amendment'){
		var l=0;
		$jq('#requestJourneyDetailsId tr:not(:first)').each(function(){
			for(var i=l;i<tdReqJourneyDetails.length;i++){
				if(parseFloat($jq(this).find('td').eq(7).find('input').val())!=parseFloat(tdReqJourneyDetails[i].ticketFare)){
					flag=true;
				}
				break;
			}
			l++;
		});
		if(flag==false){
			errorMessage +="Please Change atleast one Ticket Fare before submit the request.\n";
			status=false;
		}
	}
	if($jq('#projectName').is(':visible')){
		if($jq('#projectName').val()=='Select'){
			errorMessage +="Please Select One Project.\n";
			status=false;
		}else{
			projectName=$jq('#projectName').val();
		}
	}
	if (status) {
		var i=0;
		$jq('#requestJourneyDetailsId tr:not(:first)').each(function(){
			var valueJson={};
//			valueJson['departueDate']=$jq(this).find('td').eq(0).text();
//			valueJson['fromPlace']=$jq(this).find('td').eq(1).text();
//			valueJson['toPlace']=$jq(this).find('td').eq(2).text();
//			
//			
//			valueJson['days']=$jq(this).find('td').eq(3).text();
//			valueJson['modeOfConveyance']=$jq(this).find('td').eq(4).text();
//			valueJson['classOfEntitlement']=$jq(this).find('td').eq(5).text();
//			valueJson['tatkalQuota']=$jq(this).find('td').eq(6).text();
			valueJson['ticketFare']=$jq(this).find('td').eq(7).find('input').val();
			valueJson['id']=$jq(this).find('td').eq(7).find('input').attr('id');
			reqJourneyDetailsJson[i]=valueJson;
			i++;
		});
		mainJson["reqJourneyDetails"]=reqJourneyDetailsJson;
		var requestDetails = {
				"nodeID" : nodeID,
				"departureDate" : departureDate,
				"conveyanceMode" : conveyanceMode,
				"entitlementClass" : entitlementClass,
				"tdWorkPlace" : tdWorkPlace,
				"authorizedMove" : authorizedMove,
				"stayDuration" : stayDuration,
				"tadaAdvanceAmount" : $jq('#tadaAdvanceAmount').val(),
				"ticketFare" : $jq('#ticketFare').val(),
				"noOfDaysAcc" :$jq('#noOfDaysAcc').val(),
				"accAmtPerDay" : $jq('#accAmtPerDay').val(),
				"noOfDaysFood" :$jq('#noOfDaysFood').val(),
				"foodAmtPerDay" : $jq('#foodAmtPerDay').val(),
				"referenceRequestID" : requestId,
				"type" :"tadaTdAdvance",
				"jsonValue" : JSON.stringify(mainJson),
				"amendmentType" : amendmentType,
				"amendmentReqId" : amendmentReqId,
				"projectName" : projectName,
				"taxi" : $jq('#taxi').val(),
				"onTransit" : $jq('#onTransit').val(),
				"param" : "submitRequest"
			};
		$jq.ajax( {
				type : "POST",
				url : "tadaApprovalRequest.htm?"+dispUrl,
				data : requestDetails,
				cache : false,
				success : function(html) {
					$jq('#result').html(html);
					if ($jq('.success').is(':visible')) {
						var check=confirm(" TADA ADVANCE Request has been Successfully Sent...\n\nPlease click ok to 'Preview TADA ADVANCE Application Form 'Take print' from here\n\n");
						if(check){
						document.forms[0].requestId.value = $jq.trim(requestIDtadaA);
						//document.forms[0].roleId.value = 'roleId';
					   document.forms[0].param.value = "requestDetails";
						document.forms[0].action = "workflowmap.htm";
						document.forms[0].submit();	
						}
						//clearTdAdvance();
					}
				}
			});

	} else {
		alert(errorMessage);
	}
	
}
function submitTdAdvanceAmendement(requestId){
	var errorMessage = "";
	var status = true;
	var nodeID = "";
	var departureDate = "";
	var conveyanceMode = "";
	var entitlementClass = "";
	var stayDuration = "";
	var tdWorkPlace = "";
	var authorizedMove = "";
	var s=1;
	$jq('#requestJourneyDetailsId tr:not(:first)').each(function(){
		if($jq(this).find('td').eq(7).find('input').val()==''){
			errorMessage += "Please Enter TicketFare in row-"+s+" of Journy Details.\n";
			status = false;
		}
		s++;
	});
	
	for(var i=0;i<tdRequestDetails.length;i++){
		//departureDate = tdRequestDetails[i].departureDateOne;
		//conveyanceMode = tdRequestDetails[i].conveyanceMode;
		//entitlementClass = tdRequestDetails[i].entitlementClass;
		stayDuration = tdRequestDetails[i].stayDuration;
		tdWorkPlace = tdRequestDetails[i].tdWorkPlace;
		authorizedMove = tdRequestDetails[i].authorizedMove;
	}
	if (status) {
		var requestDetails = {
				"nodeID" : nodeID,
				"departureDate" : departureDate,
				"conveyanceMode" : conveyanceMode,
				"entitlementClass" : entitlementClass,
				"tdWorkPlace" : tdWorkPlace,
				"authorizedMove" : authorizedMove,
				"stayDuration" : stayDuration,
				"tadaAdvanceAmount" : $jq('#tadaAdvanceAmount').val(),
				"type" :"tadaTdAdvance",
				"amendmentType" :"tadaTdAdvance",
				"amendmentReqId" : requestId,
				"param" : "submitRequest"
			};
		$jq.ajax( {
				type : "POST",
				url : "tadaApprovalRequest.htm?"+dispUrl,
				data : requestDetails,
				cache : false,
				success : function(html) {
					$jq('#result').html(html);
					if ($jq('.success').is(':visible')) {
						clearDaNewDetails();
					}
				}
			});

	} else {
		alert(errorMessage);
	}
	
}
function clearTdAdvance(){
	//$jq('#ticketFare').val('');
	$jq('#tadaAdvanceAmount').val(parseFloat($jq('#totalAccAmt').val())+parseFloat($jq('#totalFoodAmt').val()));
	$jq('#requestJourneyDetailsId tr:not(:first)').each(function(){
			$jq(this).find('td').eq(7).find('input').val('');
		});
}
function amendmentRequest(requestId,requestType,journeyDate) {
	var sysdate = new Date();
	var jDate = new Date(journeyDate.split("-")[2], getMonthID(journeyDate
			.split("-")[1]) + 1, journeyDate.split("-")[0], 0, 0, 1, 0)
	    var param ="";
		document.forms[0].action = "tadaApprovalRequest.htm";
		document.forms[0].requestId.value = requestId;
		document.forms[0].requestType.value = requestType;
		document.forms[0].param.value = "amendment";
		document.forms[0].submit();
}
function setAmendmentValues() {
	if(tadaRequestBean!='') {
		var dd=tadaRequestBean.departureDate;
		amendmentType= tadaRequestBean.typeValue;// amendmentType global
													// variable
		amendmentRequestId = tadaRequestBean.id; // amendmentRequestId global
													// variable
		$jq('#departureDate').val(tadaRequestBean.departureDateOne);
		$jq('#stayDuration').val(tadaRequestBean.stayDuration);
		/*var workPlace=new Array();
		workPlace=tadaRequestBean.tdWorkPlace.split("###");
		if(workPlace.length==2){
			$jq('#tdPlace').val(tadaRequestBean.tdWorkPlace.split("###")[0]);
			$jq('#tdWorkPlace').val(tadaRequestBean.tdWorkPlace.split("###")[1]);
		}else{
			$jq('#tdWorkPlace').val(tadaRequestBean.tdWorkPlace);
		}*/
		$jq('#tdWorkPlace').val(tadaRequestBean.tdWorkPlace);
		$jq('#workingPlace').val(tadaRequestBean.workingPlace);
		$jq('#purpose').val(tadaRequestBean.purpose);
		$jq("#authorizedMove[value='" + tadaRequestBean.authorizedMove + "']").attr('checked', true);
		$jq("#tadaRequirement[value=" + tadaRequestBean.daType + "]").attr('checked', true);
		
		
		$jq("#tatkalQuota[value=" + tadaRequestBean.tatkalQuota + "]").attr('checked', true);
	  
		if($jq('input:radio[name=authorizedMove]:checked').val()=='2' || $jq('input:radio[name=authorizedMove]:checked').val()=='1'){
			if(tadaRequestBean.entitlementExemption=='yes'){
				$jq('input:radio[id=entitlementExemption1]').attr('checked',true);
			}else if(tadaRequestBean.entitlementExemption=='no'){
				$jq('input:radio[id=entitlementExemption2]').attr('checked',true);
			}
		}
		if($jq('#gpfNo').is(':visible')){
			$jq('#gpfNo').val(tadaRequestBean.gpfNo);
		}
		/*if(travelTypeMapDetailsJSON!=null){
			$jq('#travelType').append($jq('<div class="line">'+'<div class="quarter leftmar">'+'Mode Of Conveyance'+'<span class="mandatory">*</span></div><div id="cMode" class="quarter leftmar">'+'</div></div>')).css("display","block");
			for(var i=0;i<travelTypeMapDetailsJSON.length;i++){
				var gradePay=travelTypeMapDetailsJSON[i].id;
				var ttid=travelTypeMapDetailsJSON[i].travelTypeId;
				for( var s=0;s<travelTypeListJSON.length;s++){
					if(travelTypeListJSON[s].id==travelTypeMapDetailsJSON[i].travelTypeId){
						$jq('#cMode').append($jq('<div class="quarter">'+'<input id='+travelTypeMapDetailsJSON[i].flag+' type="radio" value='+travelTypeMapDetailsJSON[i].travelTypeId+' onchange="javascript:enableEntitleClass();" name="conveyanceMode"><label for='+travelTypeListJSON[s].id+'>'+travelTypeListJSON[s].travelType+'</label>'+''));
						if(tadaRequestBean.conveyanceMode==travelTypeMapDetailsJSON[i].travelTypeId){
							$jq("#"+travelTypeMapDetailsJSON[i].flag+"[value=" + tadaRequestBean.conveyanceMode + "]").attr('checked', true);
						}
					}
				}
			}
		}*/
		
		$jq("#entitleClass :not(:first)").each(function() {
			$jq(this).remove();
		});
		enableEntitleClass();
		$jq('#entitleId').val(tadaRequestBean.entitlementClass);
		showProjDirNames();
		$jq('#projDirName').val(tadaRequestBean.projDirName);
		setTatkalQuota();
	}
}
function setTdReqJourneyList(){
	for(var m=1;m<tadaRequestBean.tadaTdReqJourneyList.length;m++){
		funcreatenewRequestJourneyRow('requestJourneyDetailsId');
		enableConveyanceModes(m);
	}
	if(parseFloat(tadaRequestBean.tadaTdReqJourneyList.length)!=0){
		$jq('#amendmentTypeDiv').show();
	}else{
		$jq('#requestDetailsDiv').show();
		$jq('#journeyDetailsDiv').show();
	}
	var i=0;
	var d=new Date();
	$jq('#requestJourneyDetailsId tr:not(:first)').each(function(){
		for(var j=i;j<tadaRequestBean.tadaTdReqJourneyList.length;j++){
			$jq(this).find('td').eq(0).find('input').val(tadaRequestBean.tadaTdReqJourneyList[j].strDepartureDate);
			$jq(this).find('td').eq(1).find('input').val(tadaRequestBean.tadaTdReqJourneyList[j].strArrivalDate);
			for(var k=0;k<cityList.length;k++){
				if(cityList[k].cityName==tadaRequestBean.tadaTdReqJourneyList[j].fromPlace){
					$jq(this).find('td').eq(2).find('select').val(tadaRequestBean.tadaTdReqJourneyList[j].fromPlace);
					setToPlace(j);
					break;
				} else{
					showOtherPlace(j);
					$jq(this).find('td').eq(2).find('select').val('Other');
					$jq(this).find('td').eq(2).find('input').val(tadaRequestBean.tadaTdReqJourneyList[j].fromPlace);
					setToPlace(j);
				}
			}
			for(var k=0;k<cityList.length;k++){
				if(cityList[k].cityName==tadaRequestBean.tadaTdReqJourneyList[j].toPlace){
					$jq(this).find('td').eq(3).find('select').val(tadaRequestBean.tadaTdReqJourneyList[j].toPlace);
					showOtherPlace(j);
					break;
				} else{
					showOtherPlace(j);
					$jq(this).find('td').eq(3).find('select').val('Other');
					$jq(this).find('td').eq(3).find('input').val(tadaRequestBean.tadaTdReqJourneyList[j].toPlace);
				}
			}
			$jq(this).find('td').eq(4).find('input').val(tadaRequestBean.tadaTdReqJourneyList[j].noOfDays);
			for( var s=0;s<travelTypeListJSON.length;s++){
				if(s==0){
					enableConveyanceModes(s);
				}
				if(travelTypeListJSON[s].travelType==tadaRequestBean.tadaTdReqJourneyList[j].conveyanceMode){
					$jq(this).find('td').eq(5).find('select').val(travelTypeListJSON[s].id);
					enableEntitleClasses(j);
				}
			}
			for(var s=0;s<entitleClassListJSON.length;s++){
				if(entitleClassListJSON[s].entitleClass==tadaRequestBean.tadaTdReqJourneyList[j].classOfEntitlement){
					$jq(this).find('td').eq(6).find('select').val(entitleClassListJSON[s].id);
				}
			}
			$jq(this).find('td').eq(7).find('select').val(tadaRequestBean.tadaTdReqJourneyList[j].tatkalQuota);
			break;
		}
		i++;
	});
}
function setTdLeaveDetailsList(){
	if(tadaRequestBean.tdLeaveDetailsList.length!=0){
		var create1 = '';
		var create2 = '';
		$jq("#leaveRequirement1[value=1]").attr('checked',true);
		enableLeave();
		tempJsonList=tdLeaveTypeList;
		$jq('#SelectRight').find('option').remove().end();
		$jq('#SelectLeft').find('option').remove().end();
		for(var i = 0; i < tdLeaveTypeList.length;i++)
	    {
	    	var flag=true;
	    	for(var j=0;tadaRequestBean.tdLeaveDetailsList != null && j<tadaRequestBean.tdLeaveDetailsList.length;j++) 
	    		{
	    			if(tdLeaveTypeList[i].key==tadaRequestBean.tdLeaveDetailsList[j].leaveRequestId){
	    				create2 += '<option value="'+tdLeaveTypeList[i].key+'">'+tdLeaveTypeList[i].name+'</option>';
	    				flag=false;
	    				break;
	    			}
	    		}
	    	if(flag){
	    		create1 += '<option value="'+tdLeaveTypeList[i].key+'">'+tdLeaveTypeList[i].name+'</option>';
	    	}
	    }
	    $jq('#SelectRight').append(create2);
	    $jq('#SelectLeft').append(create1);
		
	}else{
		$jq("#leaveRequirement2[value=2]").attr('checked',true);
	}
	$jq('#ajaxBusy').remove();
}
function submitTdAmendmentApproval(requestId){
	var errorMessage = "";
	var status = true;
	
	if ($jq('#gpfNo').val()=='') {
		errorMessage += "Please Enter GPF No.\n";
		$jq('#gpfNo').focus();
		status = false;
	}
	if ($jq('#departureDate').val()=='') {
		errorMessage += "Please Select Departure Date.\n";
		$jq('#departureDate').focus();
		status = false;
	}
	var checkConveyanceMode=!$jq('input:radio[name=conveyanceMode]').is(':checked');
	if(checkConveyanceMode){
		errorMessage += "Please Select Mode Of Conveyance.\n";
		status = false;
	}
	if($jq('#entitleId').is(':visible')){
		if($jq('#entitleId').val()=='Select'){
			errorMessage += "Please Select Class Of Entitlement.\n";
			status = false;
		}
	}
	if ($jq('#stayDuration').val()=='') {
		errorMessage += "Please Enter Stay Duration.\n";
		$jq('#stayDuration').focus();
		status = false;
	}
	if ($jq('#tdWorkPlace').val()=='') {
		errorMessage += "Please Enter TD Work Place.\n";
		$jq('#tdWorkPlace').focus();
		status = false;
	}
	if ($jq('#purpose').val()=='') {
		errorMessage += "Please Enter Purpose.\n";
		$jq('#purpose').focus();
		status = false;
	}
	if(!$jq('input:radio[name=authorizedMove]').is(':checked')){
		errorMessage += "Please Select Project For Which Move is Authorized.\n";
		status = false;
	}
	if(!$jq('input:radio[name=tadaRequirement]').is(':checked')){
		errorMessage += "Please Select Whether TA/DA is required with Hotel/Normal rates.\n";
		status = false;
	}
	if(!$jq('input:radio[name=tatkalQuota]').is(':checked')){
		errorMessage += "Please Select Tatkal Quota.\n";
		status = false;
	}
	if(tadaRequestBean!='') {
		if($jq('#departureDate').val()==tadaRequestBean.departureDateOne && $jq('#stayDuration').val()==tadaRequestBean.stayDuration && $jq('#tdWorkPlace').val()==tadaRequestBean.tdWorkPlace && $jq('#purpose').val()==tadaRequestBean.purpose && $jq('input:radio[name=authorizedMove]:checked').val()==tadaRequestBean.authorizedMove && $jq('input:radio[name=tadaRequirement]:checked').val()==tadaRequestBean.daType && $jq('input:radio[name=tatkalQuota]:checked').val()==tadaRequestBean.tatkalQuota && $jq('#entitleId').val()==tadaRequestBean.entitlementClass){
			if(travelTypeMapDetailsJSON!=null){
				for(var i=0;i<travelTypeMapDetailsJSON.length;i++){
					var gradePay=travelTypeMapDetailsJSON[i].id;
					var ttid=travelTypeMapDetailsJSON[i].travelTypeId;
					for( var s=0;s<travelTypeListJSON.length;s++){
						if(travelTypeListJSON[s].id==travelTypeMapDetailsJSON[i].travelTypeId){
							if(tadaRequestBean.conveyanceMode==travelTypeMapDetailsJSON[i].travelTypeId){
								if($jq('input:radio[name=conveyanceMode]:checked').val()==tadaRequestBean.conveyanceMode){
									errorMessage += "You didn't change any value so please change atleast one value before submit the request.\n";
									status = false;
								}
							}
						}
					}
				}
			}
		}
	}
	if (status) {
		var requestDetails = {
				"nodeID" : nodeID,
				"gpfNo" : $jq('#gpfNo').val(),
				"departureDate" : $jq('#departureDate').val(),
				"conveyanceMode" : $jq('input:radio[name=conveyanceMode]:checked').val(),
				"entitlementClass" : $jq('#entitleId').val(),
				"stayDuration" : $jq('#stayDuration').val(),
				"purpose" : $jq('#purpose').val(),
				"tdWorkPlace" : $jq('#tdWorkPlace').val(),
				"authorizedMove" : $jq('input:radio[name=authorizedMove]:checked').val(),
				"daType" : $jq('input:radio[name=tadaRequirement]:checked').val(),
				"tatkalQuota" : $jq('input:radio[name=tatkalQuota]:checked').val(),
				"amendmentReqId" : requestId,
				"type" :"tadaApproval",
				"amendmentType" :"tadaApproval",
				"param" : "submitRequest"
			};
			$jq.ajax( {
				type : "POST",
				url : "tadaApprovalRequest.htm?"+dispUrl,
				data : requestDetails,
				cache : false,
				success : function(html) {
					$jq('#result').html(html);
					if ($jq('.success').is(':visible')) {
						clearTdApproval();
					}
					if($jq('.TD request is already applied').is(':visible')){
						clearTdApproval();
					}
				}
			});

	} else {
		alert(errorMessage);
	}
}
function cancelTadaTdApproval(requestID,requestType,historyID){
	if(requestType=="tadaApproval") {requestType="TADA TD APPROVAL";}
		if(confirm('Do you want to cancel the TADA TD request')) {
			$jq.ajax( {
				type : "POST",
				url : "workflow.htm?param=declined&historyID="+historyID+"&requestID="+requestID+ '&requestType='+requestType+'&type=cancelled',
				cache : false,
				success : function(html) {			
					$jq("#result").html(html);
					tadaTdClaimAndSettlement();
			}
			});
	}
 }
function cancelTadaTdAdvance(requestID,requestType,historyID){
	if(confirm('Do you want to cancel the TADA TD request')) {
		$jq.ajax( {
			type : "POST",
			url : "workflow.htm?param=declined&historyID="+historyID+"&requestID="+requestID+ '&requestType='+requestType+'&type=cancelled',
			cache : false,
			success : function(html) {			
				$jq("#result").html(html);
				tadaTdClaimAndSettlement();
				
		}
		});
	}
}
function cancelTadaTdSettlement(requestID,requestType,historyID){
	if(confirm('Do you want to Cancel the TADA TD Settlement')) {
		$jq.ajax( {
			type : "POST",
			url : "workflow.htm?param=declined&historyID="+historyID+"&requestID="+requestID+ '&requestType='+requestType+'&type=cancelled',
			cache : false,
			success : function(html) {			
				$jq("#result").html(html);
				tadaTdClaimAndSettlement();
		}
		});
	}
}
function cancelTadaTdReimbursement(requestID,requestType,historyID){
	if(confirm('Do you want to cancel the TADA TD Reimbursement')) {
		$jq.ajax( {
			type : "POST",
			url : "workflow.htm?param=declined&historyID="+historyID+"&requestID="+requestID+ '&requestType='+requestType+'&type=cancelled',
			cache : false,
			success : function(html) {			
				$jq("#result").html(html);
				tadaTdClaimAndSettlement();
		}
		});
	}
}
function settlementTdApproval(requestID,requestType,historyID){
	var status=true;
	var errorMessage='';
	for(var i=0;i<tadaTdTxnDetails.length;i++){
		if(tadaTdTxnDetails[i].requestId==requestID){
			if(tadaTdTxnDetails[i].leaveDetailsList.length>0){
				for(var j=0;j<tadaTdTxnDetails[i].leaveDetailsList.length;j++){
					if(tadaTdTxnDetails[i].leaveDetailsList[j].flag=='no'){
						errorMessage += "You are applied "+tadaTdTxnDetails[i].leaveDetailsList[j].name+" so please raise request amendment.\n";
						status=false;
						break;
					}else{
						if(tadaTdTxnDetails[i].leaveDetailsList[j].value!="29"){
							errorMessage += "Your "+tadaTdTxnDetails[i].leaveDetailsList[j].name+" is in processing so until completion of leave request you can't settle.\n";
							status=false;
							break;
						}
					}
					
				}
			}
		}
	}
	for(var i=0;i<tadaTdTxnDetails.length;i++){
		if(tadaTdTxnDetails[i].requestId==requestID){
			if(tadaTdTxnDetails[i].status==2){
				errorMessage += "Your TD Request is under processing so you can't apply settlement.\n";
				status=false;
			}
		}
	}
	if(status){
		document.forms[0].action = "tadaApprovalRequest.htm";
		document.forms[0].requestId.value = requestID;
		document.forms[0].requestType.value = requestType;
		document.forms[0].param.value = "settlement";
		document.forms[0].type.value ="tadaTdSettlement"
		document.forms[0].submit();
	}else{
		alert(errorMessage);
	}
	
}

function funcreatenewJourneyRow(idvalue,countVal)
{
	$jq.post('tadaApprovalRequest.htm?&param=serverHit',function(data){});
	// var count=0;
	countJourney=$jq('#journeyDetailsId tr:gt(1)').length-1;
	var countRow=countVal;
	countJourney++;
	
	var row = "<tr id=journeyRow"+countJourney+">";
	row += "<td>";
		row += "<input type=text readonly=readonly style=height:16px;width:65px;font-size:9px;font-weight:bold; id=journeyDate"+countJourney+" onfocus=javascript:Calendar.setup({inputField:'journeyDate"+countJourney+"',ifFormat:'%d-%b-%Y',showsTime:false,button:'journeyDate"+countJourney+"',step:1}); onchange=javascript:checkJourneyDate(\'journeyDate"+countJourney+"\'); />";
	row += "</td>";
	
	row += "<td>";
		row += "<input type=text name=startTime readonly=readonly class=startTime id=startTime"+countJourney+" style=width:37px  onmouseover=javascript:timePicker('startTime'); />";
	row += "</td>";
	
	row += "<td>";
	row += "<select id=startStation"+countJourney+" style=width:50px onchange='javascript:enableFromCityClass("+countJourney+")';><option value=Select>Select</option>";
	for(var i=0;i<cityTypeList.length;i++){
		row+= "<option value="+cityTypeList[i].cityName+">"+cityTypeList[i].cityName+"</option>";
	}
	row+="<option value=Other>Other</option><input type=text id=otherStartStation"+countJourney+" style=display:none size=7px />";
	row += "</td>";

	row += "<td>";
		row += "<input type=text readonly=readonly id=journeyEndDate"+countJourney+" style=height:16px;width:70px;font-size:9px;font-weight:bold;  onfocus=javascript:Calendar.setup({inputField:'journeyEndDate"+countJourney+"',ifFormat:'%d-%b-%Y',showsTime:false,button:'journeyEndDate"+countJourney+"',step:1}); onchange=javascript:checkArrivalDate('journeyEndDate"+countJourney+"'); />";
	row += "</td>";
	
	row += "<td>";
		row += "<input type=text name=endTime readonly=readonly class=endTime id=endTime"+countJourney+" style='width:37px'  onmouseover=javascript:timePicker('endTime'); />";
	row += "</td>";
	
	row += "<td>";
	row += "<select id=endStation"+countJourney+" style=width:50px onchange='javascript:enableToCityClass("+countJourney+")';><option value=Select>Select</option>";
	for(var i=0;i<cityTypeList.length;i++){
		row+= "<option value="+cityTypeList[i].cityName+">"+cityTypeList[i].cityName+"</option>";
	}
	row+="<option value=Other>Other</option><input type=text id=otherEndStation"+countJourney+" style=display:none size=7px />";
	row += "</td>";
	
    row += "<td>";
		row += "<input type=text id=distanceJourney"+countJourney+" style=width:60px onkeypress='javascript:return checkFloatTada(event,distanceJourney"+countJourney+")'; />";			
	row += "</td>";
	
	row += "<td>";
	row += "<select id=modeOfTravel"+countJourney+" style=width:70px onchange='javascript:enableToCityClass("+countJourney+")';><option value=Select>Select</option>";
	for(var i=0;i<travelTypeList.length;i++){
		row+= "<option value="+travelTypeList[i].travelType+">"+travelTypeList[i].travelType+"</option>";
	}
	row += "</td>";
	
	row += "<td>";
	    row += "<input type=text id=ticketFare"+countJourney+" style=width:50px onkeypress='javascript:return checkFloatTada(event,ticketFare"+countJourney+")'; onchange='javascript:totalAdjustment("+countJourney+")'/>";			
    row += "</td>";
	
	row += "<td>";
		row += "<input type=text id=totalJourneyAmount"+countJourney+" value='0' style=width:50px onchange='javascript:totalAdjustment("+countJourney+");checkCancellationTotal();' onkeypress='return checkFloat(event,'totalJourneyAmount"+countJourney+"');'/>";			
	row += "</td>";

	row += "<td>";
		row += "<input type=text id=ticketNo"+countJourney+" style=width:50px />";			
	row += "</td>";
	
	row += "<td>";
		row += "<input type=button id=add"+countJourney+"   value=+ onclick=javascript:checkJourneyRow("+countJourney+"); />";
	row += "</td>";

	row += "<td>";
		row += "<input type=button id=del"+countJourney+"   value=- onclick=javascript:deleteJourneyDetailsRow(this,\'"+idvalue+"\'); />";
	row += "</td>";
	
row += "</tr>";

$jq("#journeyRow"+(countRow)).after(row);
}
function deleteJourneyDetailsRow(node,tableID){
	var dynRow = document.getElementById(tableID);
	var index=node.parentNode.parentNode.rowIndex;
	var del=confirm("Do you want to delete this row?");
	var length1 = dynRow.rows.length;
	if(del==true) {
		if(index>=0){
			if(length1>4)
				dynRow.deleteRow(index);
			else
				alert("you can't delete this row");
		}
	}
	if(length1=='4'){
		funcreatenewJourneyRow(tableID);
	}
	countJourney--;
	totalAdjustment(countJourney);
}

function funcreateOldDaRow(idvalue)
{
	$jq.post('tadaApprovalRequest.htm?&param=serverHit',function(data){});
	// var count=0;
	countt++;
	
	var row = "<tr id=daOldRow"+countt+">";
	row += "<td>";
		row += "<input type=text readonly=readonly style=height:16px;width:70px;font-size:9px;font-weight:bold; id=startDate"+countt+" onfocus=javascript:Calendar.setup({inputField:'startDate"+countt+"',ifFormat:'%d-%b-%Y',showsTime:false,button:'startDate"+countt+"',step:1}); onchange=javascript:checkDates('startDate"+countt+"'); />";
	row += "</td>";
	
	row += "<td>";
	    row += "<input type=text name=startStayTime readonly=readonly class=startStayTime id=startStayTime"+countJourney+" style=width:37px  onmouseover=javascript:timePicker('startStayTime'); />";
    row += "</td>";
	
	row += "<td>";
	    row += "<input type=text readonly=readonly style=height:16px;width:70px;font-size:9px;font-weight:bold; id=endDate"+countt+" onfocus=javascript:Calendar.setup({inputField:'endDate"+countt+"',ifFormat:'%d-%b-%Y',showsTime:false,button:'endDate"+countt+"',step:1}); onchange=javascript:checkDates('endDate"+countt+"'); />";
    row += "</td>";
    
    row += "<td>";
        row += "<input type=text name=endStayTime readonly=readonly class=endStayTime id=endStayTime"+countJourney+" style=width:37px  onmouseover=javascript:timePicker('endStayTime'); />";
    row += "</td>";
	
	row += "<td>";
	row += "<select id=city"+countt+" style=width:145px onchange='javascript:enableCityClass("+countt+")';><option value=Select>Select</option><option value=Other>Other</option>";
	for(var i=0;i<cityTypeList.length;i++){
		// row+= $jq('#city'+countt+'').append($jq('<option
		// value='+cityTypeList[i].cityName+'>'+cityTypeList[i].cityName+'</option>'+''));
		row+= "<option value="+cityTypeList[i].cityName+">"+cityTypeList[i].cityName+"</option>";
	}
    row += "</td>";
	
	row += "<td>";
		row += "<input type=text id=cityClass"+countt+" style=width:100px />";			
	row += "</td>";
	
	row += "<td>";
		row += "<input type=text id=oldDaAmtPerDay"+countt+" style=width:150px onkeypress='javascript:return checkFloatTada(event,oldDaAmtPerDay"+countt+")' onchange='javascript:getDaOldAmount("+countt+");totalAdjustment("+countt+")'/>";			
	row += "</td>";

	row += "<td>";
		row += "<input type=text readOnly=readOnly id=oldDaAmount"+countt+" style=width:150px onkeypress=javascript:return checkFloatTada(event,oldDaAmount"+countt+"); onchange='javascript:totalAdjustment("+countt+")'; />";			
	row += "</td>";
	
	row += "<td>";
		row += "<input type=button id=add"+countt+"   value=+ onclick=javascript:checkStayDARow(); />";
	row += "</td>";

	row += "<td>";
		row += "<input type=button id=del"+countt+"   value=- onclick=javascript:deleteOldDaRow(this,\'"+idvalue+"\'); />";
	row += "</td>";
	
row += "</tr>";

$jq("#daOldRow"+(countt-1)).after(row);
}
function deleteOldDaRow(node,tableID)
{
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
		funcreateOldDaRow(tableID);
	}
	countt--;
	totalAdjustment(countt);
}

function enableDA(){
	if($jq('input:radio[name=tadaRequirement]:checked').val()=="oldDA"){
		var grandTotal=0;
		$jq('#daId').show();
		// $jq('#daNewDetailsID').hide();
		$jq('#daNewAccDetailsID').hide();
		$jq('#daNewFoodDetailsID').hide();
		$jq('#daNewRMAKmID').show();
		$jq('#daNewRMADayID').hide();
		$jq('#daNewRMALocalID').show();
		$jq('#daNewRMADailyID').show();
		var dynRowJourney=document.getElementById("journeyDetailsId");
		var lengthJourney=dynRowJourney.rows.length;
		
		for(var j=4;j<lengthJourney;j++){
			var journeyVal = parseFloat(dynRowJourney.rows[j].cells[9].childNodes[0].value);
			if(isNaN(journeyVal)){
				journeyVal=0;
			}
			grandTotal=journeyVal+grandTotal;
		}
		$jq("#totAmt").html(roundNumber(grandTotal,2));
		
		$jq("#settlementAmount").html(parseFloat(grandTotal)-parseFloat($jq("#advAmount").html()));
		
	} else if($jq('input:radio[name=tadaRequirement]:checked').val()=="newDA"){
		var grandTotal=0;
		// $jq('#daNewDetailsID').show();
		$jq('#daNewAccDetailsID').show();
		$jq('#daNewFoodDetailsID').show();
		$jq('#daOldDetailsID').hide();
		$jq('input:radio[name=daOldRequirement]:checked').attr('checked',false);
		$jq('#daId').hide();
		for(var i=0;i<daNewDetailsList.length;i++){
			if(TadaRequestBean.tadaApprovalRequestDTO.gradePay==daNewDetailsList[i].gradePay){
				if(daNewDetailsList[i].milageAllw=="perkm"){
					$jq('#daNewRMAKmID').show();
					$jq('#daNewRMALocalID').show();
					$jq('#daNewRMADayID').hide();
					$jq('#daOldDetailsID').hide();
					$jq('#daNewRMADailyID').show();
					var dynRowJourney=document.getElementById("journeyDetailsId");
					var lengthJourney=dynRowJourney.rows.length;
					
					for(var j=4;j<lengthJourney;j++){
						var journeyVal = parseFloat(dynRowJourney.rows[j].cells[9].childNodes[0].value);
						if(isNaN(journeyVal)){
							journeyVal=0;
						}
						grandTotal=journeyVal+grandTotal;
					}
					$jq("#totAmt").html(roundNumber(grandTotal,2));
					
					$jq("#settlementAmount").html(parseFloat(grandTotal)-parseFloat($jq("#advAmount").html()));
					
				}
				if(daNewDetailsList[i].milageAllw=="perday"){
					$jq('#daNewRMADayID').show();
					$jq('#daNewRMALocalID').show();
					$jq('#daNewRMAKmID').hide();
					$jq('#daOldDetailsID').hide();
					$jq('#daNewRMADailyID').show();
					var dynRowJourney=document.getElementById("journeyDetailsId");
					var lengthJourney=dynRowJourney.rows.length;
					
					for(var j=4;j<lengthJourney;j++){
						var journeyVal = parseFloat(dynRowJourney.rows[j].cells[9].childNodes[0].value);
						if(isNaN(journeyVal)){
							journeyVal=0;
						}
						grandTotal=journeyVal+grandTotal;
					}
					$jq("#totAmt").html(roundNumber(grandTotal,2));
					
					$jq("#settlementAmount").html(parseFloat(grandTotal)-parseFloat($jq("#advAmount").html()));
				}
				
			}
		}
		totalAdjustment(0);
	}else {
		$jq('#daId').hide();
		$jq('#daNewAccDetailsID').hide();
		$jq('#daNewFoodDetailsID').hide();
		$jq('#rmaKmId').attr('checked',false);
		$jq('#daNewRMAKmId').hide();
		$jq('#daNewRMAKmID').hide();
		$jq('#rmaDayId').attr('checked',false);
		$jq('#daNewRMADayId').hide();
		$jq('#daNewRMADayID').hide();
		$jq('#rmaId').attr('checked',false);
		$jq('#daNewRMALocalId').hide();
		$jq('#daNewRMALocalID').hide();
		$jq('#rmaDailyId').attr('checked',false);
		$jq('#daNewRMADailyId').hide();
		$jq('#daNewRMADailyID').hide();
		
		totalAdjustment(0);
	}
	
}
function enableDaDetails(){
	var mainJson={};
	var journeyJson={};
	var status=true;
	var requestId='';
	if(tadaRequestBean!=null){
		requestId=tadaRequestBean.requestId;
	}
	if (status) {
		var k=0;
		$jq("#journeyDetailsId tr:gt(1)").each(function() {
			if(parseFloat($jq(this).find("td").eq(9).find('input').val())==0){
				var valuesJson={};
				valuesJson['journeyDate'] = $jq(this).find("td").eq(0).find('input').val();
				valuesJson['startTime'] = $jq(this).find("td").eq(1).find('input').val();
				if($jq(this).find("td").eq(2).find('select').val()!='Other' && $jq(this).find("td").eq(2).find('select').val()!='Select'){
					valuesJson['startStation'] = $jq(this).find("td").eq(2).find('select').val();
				} else {
					valuesJson['startStation'] = $jq(this).find("td").eq(2).find('input').val();
				}
				valuesJson['journeyEndDate'] = $jq(this).find("td").eq(3).find('input').val();
				valuesJson['endTime'] = $jq(this).find("td").eq(4).find('input').val();
				if($jq(this).find("td").eq(5).find('select').val()!='Select' && $jq(this).find("td").eq(5).find('select').val()!='Other'){
					valuesJson['endStation'] = $jq(this).find("td").eq(5).find('select').val();
				} else {
					valuesJson['endStation'] = $jq(this).find("td").eq(5).find('input').val();
				}
				if($jq(this).find("td").eq(6).find('input').val()!='')
					valuesJson['distanceJourney'] = $jq(this).find("td").eq(6).find('input').val();
				else
					valuesJson['distanceJourney'] = "0";
				valuesJson['modeOfTravel'] = $jq(this).find("td").eq(7).find('select').val();
				valuesJson['ticketFare'] = $jq(this).find("td").eq(8).find('input').val();
				if($jq(this).find("td").eq(9).find('input').val()!='')
					valuesJson['totalJourneyAmount'] = $jq(this).find("td").eq(9).find('input').val();
				else
					valuesJson['totalJourneyAmount'] = "0";
				if($jq(this).find("td").eq(10).find('input').val()!='')
					valuesJson['ticketNo'] = $jq(this).find("td").eq(10).find('input').val();
				else
					valuesJson['ticketNo'] = "0";
				journeyJson[k]=valuesJson;
				k++;
			}
		});
		mainJson['journeyDetails']=journeyJson;
		var requestDetails = {
				"param" : "jDADetails",
				"jsonValue" : JSON.stringify(mainJson),
				"requestId" : tadaRequestBean.requestId,
				"daType" : $jq('input:radio[name=daOldRequirement]:checked').val()
			};
			$jq.ajax( {
				type : "POST",
				url : "tadaApprovalRequest.htm?"+dispUrl,
				data : requestDetails,
				cache : false,
				success : function(data) {
					$jq('#result9').html(data);
					if ($jq('.success').is(':visible')) {
						
					}
					
				}
			});

	} else {
		alert(errorMessage);
	}
}
function showJDADetails(){
	$jq('#daOldDetailsID').show();
}
function funcreatenewNewDaRow(idvalue)
{
	$jq.post('tadaApprovalRequest.htm?&param=serverHit',function(data){});
	// var count=0;
	countNewDA++;
	
	var row = "<tr id=daNewRow"+countNewDA+">";
	row += "<td>";
		row += "<input type=text readonly=readonly style=height:16px;width:100px;font-size:9px;font-weight:bold; id=date"+countNewDA+" onfocus=javascript:Calendar.setup({inputField:'date"+countNewDA+"',ifFormat:'%d-%b-%Y',showsTime:false,button:'date"+countNewDA+"',step:1}); onchange=javascript:checkDates('date"+countNewDA+"'); />";
	row += "</td>";
	
	row += "<td>";
	    row += "<input type=text id=stayDuration"+countNewDA+" style=width:100px onkeypress='javascript:return checkFloatTada(event,stayDuration"+countNewDA+")'; />";			
    row += "</td>";
	
	row += "<td>";
		row += "<input type=text id=food"+countNewDA+" style=width:200px onkeypress='javascript:return checkFloatTada(event,food"+countNewDA+")'; onchange='javascript:getDaNewAmount("+countNewDA+"); totalAdjustment("+countNewDA+");checkDaNewAmount("+countNewDA+")' />";			
	row += "</td>";
	
	row += "<td>";
		row += "<input type=text id=accommodation"+countNewDA+" style=width:180px onkeypress='javascript:return checkFloatTada(event,accommodation"+countNewDA+")'; onchange='javascript:getDaNewAmount("+countNewDA+"); totalAdjustment("+countNewDA+");checkDaNewAmount("+countNewDA+")' />";			
	row += "</td>";
	
	row += "<td>";
	    row += "<input type=text readonly=readonly id=amount"+countNewDA+" style=width:180px />";			
    row += "</td>";
	
	row += "<td>";
		row += "<input type=button id=newDaAdd"+countNewDA+"   value=+ onclick=javascript:funcreatenewNewDaRow('daNewDetailsId'); />";
	row += "</td>";

	row += "<td>";
		row += "<input type=button id=newDaDel"+countNewDA+"   value=- onclick=javascript:deleteNewDaRow(this,\'"+idvalue+"\'); />";
	row += "</td>";
	
row += "</tr>";

$jq("#daNewRow"+(countNewDA-1)).after(row);
}
function deleteNewDaRow(node,tableID)
{
	var dynmicRow = document.getElementById(tableID);
	var index=node.parentNode.parentNode.rowIndex;
	
	 
	var del=confirm("Do you want to delete this row?");
	if(del==true) 
	{
		if(index>=0)
		{
			dynmicRow.deleteRow(index);
		}
		
	}
	var length1 = dynmicRow.rows.length;
	
	if(length1=='4')
	{
		funcreatenewNewDaRow(tableID);
	}
	countNewDA--;
	totalAdjustment(countNewDA);
}

function funcreatenewNewDaAccRow(idvalue)
{
	$jq.post('tadaApprovalRequest.htm?&param=serverHit',function(data){});
	// var count=0;
	countDaNewAcc++;
	
	var row = "<tr id=daNewAccRow"+countDaNewAcc+">";
	row += "<td>";
		row += "<input type=text readonly=readonly style=height:16px;width:170px;font-size:9px;font-weight:bold; id=fromDateAcc"+countDaNewAcc+" onfocus=javascript:Calendar.setup({inputField:'fromDateAcc"+countDaNewAcc+"',ifFormat:'%d-%b-%Y',showsTime:false,button:'fromDateAcc"+countDaNewAcc+"',step:1});   onchange=javascript:clearAccAmt("+countDaNewAcc+");checkJourneyDate('fromDateAcc"+countDaNewAcc+"'); />";
	row += "</td>";
	
	row += "<td>";
	    row += "";
    row += "</td>";
    
    row += "<td>";
	    row += "<input type=text readonly=readonly style=height:16px;width:170px;font-size:9px;font-weight:bold; id=toDateAcc"+countDaNewAcc+" onfocus=javascript:Calendar.setup({inputField:'toDateAcc"+countDaNewAcc+"',ifFormat:'%d-%b-%Y',showsTime:false,button:'toDateAcc"+countDaNewAcc+"',step:1}); onchange=javascript:clearAccAmt("+countDaNewAcc+");checkNewDate(this);checkFromToAccDate("+countDaNewAcc+"); />";
    row += "</td>";
	
	row += "<td>";
	    row += "";
    row += "</td>";
	
	row += "<td>";
		row += "<input type=text id=accAmount"+countDaNewAcc+" style=width:260px onkeypress='javascript:return checkFloatTada(event,accAmount"+countDaNewAcc+")'; onchange=javascript:enableAccClaimAmount();totalAdjustment("+countDaNewAcc+"); />";			
	row += "</td>";
	
	row += "<td>";
	    row += "<input type=text readonly=readonly id=totalAccAmount"+countDaNewAcc+" style=width:180px />";			
    row += "</td>";
	
	row += "<td>";
		row += "<input type=button id=newDaAccAdd"+countDaNewAcc+"   value=+ onclick=javascript:checkDaNewAccRow("+countDaNewAcc+"); />";
	row += "</td>";

	row += "<td>";
		row += "<input type=button id=newDaAccDel"+countDaNewAcc+"   value=- onclick=javascript:deleteNewDaAccRow(this,\'"+idvalue+"\'); />";
	row += "</td>";
	
row += "</tr>";

$jq("#daNewAccRow"+(countDaNewAcc-1)).after(row);
}
function deleteNewDaAccRow(node,tableID)
{
	var dynmicRow = document.getElementById(tableID);
	var index=node.parentNode.parentNode.rowIndex;
	
	 
	var del=confirm("Do you want to delete this row?");
	if(del==true) 
	{
		if(index>=0)
		{
			dynmicRow.deleteRow(index);
		}
		
	}
	var length1 = dynmicRow.rows.length;
	
	if(length1=='4')
	{
		funcreatenewNewDaAccRow(tableID);
	}
	countDaNewAcc--;
	totalAdjustment(countDaNewAcc);
}

function funcreatenewNewDaFoodRow(idvalue)
{
	$jq.post('tadaApprovalRequest.htm?&param=serverHit',function(data){});
	// var count=0;
	countNewDAFood++;
	
	var row = "<tr id=daNewFoodRow"+countNewDAFood+">";
	row += "<td>";
		row += "<input type=text readonly=readonly style=height:16px;width:180px;font-size:9px;font-weight:bold; id=fromDateFood"+countNewDAFood+" onfocus=javascript:Calendar.setup({inputField:'fromDateFood"+countNewDAFood+"',ifFormat:'%d-%b-%Y',showsTime:false,button:'fromDateFood"+countNewDAFood+"',step:1}); onchange=javascript:clearFoodAmt("+countNewDAFood+");checkNewDate(this); />";
	row += "</td>";
	
	row += "<td>";
	    row += "<input type=text readonly=readonly style=height:16px;width:180px;font-size:9px;font-weight:bold; id=toDateFood"+countNewDAFood+" onfocus=javascript:Calendar.setup({inputField:'toDateFood"+countNewDAFood+"',ifFormat:'%d-%b-%Y',showsTime:false,button:'toDateFood"+countNewDAFood+"',step:1});  onchange=javascript:checkFromToFoodDate("+countNewDAFood+");clearFoodAmt("+countNewDAFood+");checkNewDate(this);  />";
    row += "</td>";
	
	row += "<td>";
		row += "<input type=text id=foodAmount"+countNewDAFood+" style=width:270px onkeypress='javascript:return event,foodAmount"+countNewDAFood+")'; onchange='javascript:enableFoodAmtAftCal("+countNewDAFood+");checkFoodAmount("+countNewDAFood+");totalAdjustment("+countNewDAFood+");' />";			
	row += "</td>";
	
	row += "<td>";
	    row += "<input type=text readonly=readonly id=totalFoodAmount"+countNewDAFood+" style=width:180px />";			
    row += "</td>";
	
	row += "<td>";
		row += "<input type=button id=newDaFoodAdd"+countNewDAFood+"   value=+ onclick=javascript:checkDaNewFoodRow("+countNewDAFood+"); />";
	row += "</td>";

	row += "<td>";
		row += "<input type=button id=newDaFoodDel"+countNewDAFood+"   value=- onclick=javascript:deleteNewDaFoodRow(this,\'"+idvalue+"\'); />";
	row += "</td>";
	
row += "</tr>";

$jq("#daNewFoodRow"+(countNewDAFood-1)).after(row);
}
function deleteNewDaFoodRow(node,tableID)
{
	var dynmicRow = document.getElementById(tableID);
	var index=node.parentNode.parentNode.rowIndex;
	
	 
	var del=confirm("Do you want to delete this row?");
	if(del==true) 
	{
		if(index>=0)
		{
			dynmicRow.deleteRow(index);
		}
		
	}
	var length1 = dynmicRow.rows.length;
	
	if(length1=='4')
	{
		funcreatenewNewDaFoodRow(tableID);
	}
	countNewDAFood--;
	totalAdjustment(countNewDAFood);
}

function funcreateNewRMAKmRow(idvalue)
{
	
	$jq.post('tadaApprovalRequest.htm?&param=serverHit',function(data){});
	// var count=0;
	countRMAKm++;
	
	var row = "<tr id=daNewRMAKmRow"+countRMAKm+">";
	row += "<td>";
		row += "<input type=text readonly=readonly style=height:16px;width:70px;font-size:9px;font-weight:bold; id=dateRMAKm"+countRMAKm+" onfocus=javascript:Calendar.setup({inputField:'dateRMAKm"+countRMAKm+"',ifFormat:'%d-%b-%Y',showsTime:false,button:'dateRMAKm"+countRMAKm+"',step:1}); onchange=javascript:checkNewDate(this); />";
	row += "</td>";
	
	row += "<td>";
		row += "<input type=text id=fromPlace"+countRMAKm+" style=width:110px onkeypress='javascript:return checkChar(event);'/>";			
	row += "</td>";
	
	row += "<td>";
		row += "<input type=text id=toPlace"+countRMAKm+" style=width:110px onkeypress='javascript:return checkChar(event);'/>";			
	row += "</td>";
	
	row += "<td id=travelByA >";
	    row += "<select id=travelBy"+countRMAKm+" style=width:100px onkeypress='javascript:return checkInt(event)' onchange='javascript:checkRMATravelType("+countRMAKm+")' name=travelBy><option value=Select>Select</option><option value='AC Taxi'>AC Taxi</option><option value='Non AC Taxi'>Non AC Taxi</option><option value='Ordinary Taxi'>Ordinary Taxi</option><option value=Other>Other</option></select>";
	row += "</td>";
	    
    row += "<td id=distanceA >";
        row += "<input type=text id=distance"+countRMAKm+" style=width:100px onkeypress='javascript:return checkFloatTada(event,distance"+countRMAKm+")'; onchange='javascript:getDaNewRMAKmAmount("+countRMAKm+"); totalAdjustment("+countRMAKm+");'/>";			
    row += "</td>";

    row += "<td id=amountPerKmA >";
        row += "<input type=text id=amountPerKm"+countRMAKm+" style=width:100px onkeypress='javascript:return checkFloatTada(event,amountPerKm"+countRMAKm+")'; onchange='javascript:getDaNewRMAKmAmount("+countRMAKm+"); totalAdjustment("+countRMAKm+")' />";			
    row += "</td>";
    
    row += "<td>";
        row += "<input type=text readonly=readonly id=totalRMAKmAmount"+countRMAKm+" style=width:180px />";			
    row += "</td>";
	
	row += "<td>";
		row += "<input type=button id=newDaRMAAdd"+countRMAKm+"   value=+ onclick=javascript:checkRMAKmRow(); />";
	row += "</td>";

	row += "<td>";
		row += "<input type=button id=newDaRMADel"+countRMAKm+"   value=- onclick=javascript:deleteNewRMAKmRow(this,\'"+idvalue+"\'); />";
	row += "</td>";
	
row += "</tr>";

$jq("#daNewRMAKmRow"+(countRMAKm-1)).after(row);
}
function deleteNewRMAKmRow(node,tableID)
{
	var dynmicRow = document.getElementById(tableID);
	var index=node.parentNode.parentNode.rowIndex;
	
	 
	var del=confirm("Do you want to delete this row?");
	if(del==true) 
	{
		if(index>=0)
		{
			dynmicRow.deleteRow(index);
		}
		
	}
	var length1 = dynmicRow.rows.length;
	
	if(length1=='4')
	{
		funcreateNewRMAKmRow(tableID);
	}
	countRMAKm--;
	totalAdjustment(countRMAKm);
}
function funcreateNewRMADailyRow(idvalue){
	$jq.post('tadaApprovalRequest.htm?&param=serverHit',function(data){});
	countRMADaily++;
	
	var row = "<tr id=daNewRMADailyRow"+countRMADaily+">";
	row += "<td>";
		row += "<input type=text readonly=readonly style=height:16px;width:70px;font-size:9px;font-weight:bold; id=dateRMADaily"+countRMADaily+" onfocus=javascript:Calendar.setup({inputField:'dateRMADaily"+countRMADaily+"',ifFormat:'%d-%b-%Y',showsTime:false,button:'dateRMADaily"+countRMADaily+"',step:1}); onchange=javascript:checkNewDate(this); />";
	row += "</td>";
	
	row += "<td>";
		row += "<input type=text id=fromPlaceDaily"+countRMADaily+" style=width:110px onkeypress='javascript:return checkChar(event);'/>";			
	row += "</td>";
	
	row += "<td>";
		row += "<input type=text id=toPlaceDaily"+countRMADaily+" style=width:110px onkeypress='javascript:return checkChar(event);'/>";			
	row += "</td>";
	
	row += "<td id=travelByA >";
	    row += "<select id=travelByDaily"+countRMADaily+" style=width:100px;visibility: visible; onkeypress='javascript:return checkInt(event)'  name=travelBy><option value=Select>Select</option><option value='AC Taxi'>AC Taxi</option><option value='Non AC Taxi'>Non AC Taxi</option><option value='Ordinary Taxi'>Ordinary Taxi</option><option value=Other>Other</option></select>";
	row += "</td>";
	    
    row += "<td id=distanceA >";
        row += "<input type=text id=distanceDaily"+countRMADaily+" style=width:100px onkeypress='javascript:return checkFloatTada(event,distanceDaily"+countRMADaily+")'; onchange='javascript:getDaNewRMADailyAmount("+countRMADaily+"); totalAdjustment("+countRMADaily+")'/>";			
    row += "</td>";

    row += "<td id=amountPerKmA >";
        row += "<input type=text id=amountPerKmDaily"+countRMADaily+" style=width:100px onkeypress='javascript:return checkFloatTada(event,amountPerKmDaily"+countRMADaily+")'; onchange='javascript:getDaNewRMADailyAmount("+countRMADaily+"); totalAdjustment("+countRMADaily+")' />";			
    row += "</td>";
    
    row += "<td>";
        row += "<input type=text readonly=readonly id=totalRMAKmAmountDaily"+countRMADaily+" style=width:180px />";			
    row += "</td>";
	
	row += "<td>";
		row += "<input type=button id=newDaRMADailyAdd"+countRMADaily+"   value=+ onclick=javascript:checkRMADailyRow(); />";
	row += "</td>";

	row += "<td>";
		row += "<input type=button id=newDaRMADailyDel"+countRMADaily+"   value=- onclick=javascript:deleteNewRMADailyRow(this,\'"+idvalue+"\'); />";
	row += "</td>";
	
row += "</tr>";

$jq("#daNewRMADailyRow"+(countRMADaily-1)).after(row);
}
function deleteNewRMADailyRow(node,tableID){
	var dynmicRow = document.getElementById(tableID);
	var index=node.parentNode.parentNode.rowIndex;
	
	 
	var del=confirm("Do you want to delete this row?");
	if(del==true) 
	{
		if(index>=0)
		{
			dynmicRow.deleteRow(index);
		}
		
	}
	var length1 = dynmicRow.rows.length;
	
	if(length1=='4')
	{
		funcreateNewRMADailyRow(tableID);
	}
	countRMADaily--;
	totalAdjustment(countRMADaily);
}
function funcreateNewRMADayRow(idvalue)
{
	
	$jq.post('tadaApprovalRequest.htm?&param=serverHit',function(data){});
	// var count=0;
	countRMADay++;
	
	var row = "<tr id=daNewRMADayRow"+countRMADay+">";
	row += "<td>";
		row += "<input type=text readonly=readonly style=height:16px;width:70px;font-size:9px;font-weight:bold; id=dateRMADay"+countRMADay+" onfocus=javascript:Calendar.setup({inputField:'dateRMADay"+countRMADay+"',ifFormat:'%d-%b-%Y',showsTime:false,button:'dateRMADay"+countRMADay+"',step:1});  />";
	row += "</td>";
	
	row += "<td>";
		row += "<input type=text id=fromPlaceRMADay"+countRMADay+" style=width:110px />";			
	row += "</td>";
	
	row += "<td>";
		row += "<input type=text id=toPlaceRMADay"+countRMADay+" style=width:110px />";			
	row += "</td>";
	
	row += "<td id=amountPerDayA>";
        row += "<input type=text id=amountPerDay"+countRMADay+" style=width:100px onkeypress='javascript:return checkFloatTada(event,amountPerDay"+countRMADay+")'; onchange='javascript:enableRMADayAmtAftCal("+countRMADay+"); totalAdjustment("+countRMADay+")'/>";			
    row += "</td>";
    
    row += "<td>";
        row += "<input type=text readOnly=readOnly id=amountPerDayAftCal"+countRMADay+" style=width:170px />";			
    row += "</td>";

    row += "<td>";
        row += "<input type=text readOnly=readOnly id=totalAmount"+countRMADay+" style=width:130px />";			
    row += "</td>";
	
	row += "<td>";
		row += "<input type=button id=newDaRMAAdd"+countRMADay+"   value=+ onclick=javascript:checkRMADayRow("+countRMADay+"); />";
	row += "</td>";

	row += "<td>";
		row += "<input type=button id=newDaRMADel"+countRMADay+"   value=- onclick=javascript:deleteNewRMADayRow(this,\'"+idvalue+"\'); />";
	row += "</td>";
	
row += "</tr>";

$jq("#daNewRMADayRow"+(countRMADay-1)).after(row);
}
function deleteNewRMADayRow(node,tableID)
{
	var dynmicRow = document.getElementById(tableID);
	var index=node.parentNode.parentNode.rowIndex;
	
	 
	var del=confirm("Do you want to delete this row?");
	if(del==true) 
	{
		if(index>=0)
		{
			dynmicRow.deleteRow(index);
		}
		
	}
	var length1 = dynmicRow.rows.length;
	
	if(length1=='4')
	{
		funcreateNewRMADayRow(tableID);
	}
	countRMADay--;
	totalAdjustment(countRMADay);
}

function funcreateNewRMALocalRow(idvalue)
{
	
	$jq.post('tadaApprovalRequest.htm?&param=serverHit',function(data){});
	// var count=0;
	countRMALocal++;
	
	var row = "<tr id=daNewRMALocalRow"+countRMALocal+">";
	row += "<td>";
		row += "<input type=text readonly=readonly style=height:16px;width:60px;font-size:9px;font-weight:bold; id=dateRMALocal"+countRMALocal+" onfocus=javascript:Calendar.setup({inputField:'dateRMALocal"+countRMALocal+"',ifFormat:'%d-%b-%Y',showsTime:false,button:'dateRMALocal"+countRMALocal+"',step:1}); onchange=javascript:checkNewDate(this); />";
	row += "</td>";
	
	row += "<td>";
		row += "<select id=fromPlaceLocal"+countRMALocal+" name=fromPlaceLocal style=width:85px; onchange=javascript:checkFromToLocalPlace("+countRMALocal+");checkLocalPlace("+countRMALocal+");>"
												+"<option value='Select'>Select</option>"
												+"<option value='ASL/DRDL'>ASL/DRDL</option>"
												+"<option value='RCI/CPDC'>RCI/CPDC</option>"
												+"<option value='Begumpet Airport'>Begumpet Airport</option>"
												+"<option value='Secunderabad Railway Station'>Secunderabad Railway Station</option>"
												+"<option value='Hyderabad Railway Station'>Hyderabad Railway Station</option>"
												+"<option value='MGBS'>MGBS</option>"
												+"<option value='JBS'>JBS</option>"
												+"<option value='Kachiguda'>Kachiguda</option>"
												+"<option value='Shamshabad Airport'>Shamshabad Airport</option>"
												+"<option value='Residence'>Residence</option>"
												+"<option value='Other'>Other</option>"
											+"</select>";			
	row += "</td>";
	
	row += "<td id=residencePlaceFromTd>";
        row +="<input type=text id=residencePlaceFrom"+countRMALocal+" style=width:70px;display:none onkeypress='javascript:return checkChar(event);' />";
    row +="</td>";

    row += "<td id=otherPlaceFromTd>";
        row +="<input type=text id=otherPlaceFrom"+countRMALocal+" style=width:60px;display:none onkeypress='javascript:return checkChar(event);' />";
    row +="</td>";
	
	row += "<td>";
	    row += "<select id=toPlaceLocal"+countRMALocal+" name=toPlaceLocal style=width:85px; onchange=javascript:checkLocalPlace("+countRMALocal+");enableLocalDistance("+countRMALocal+"); >"
											+"<option value='Select'>Select</option>"
										+"</select>";			
    row += "</td>";
    
    row += "<td id=residencePlaceToTd>";
        row +="<input type=text id=residencePlaceTo"+countRMALocal+" style=width:70px;display:none onkeypress='javascript:return checkChar(event);' />";
    row +="</td>";
    
    row += "<td id=otherPlaceToTd>";
        row +="<input type=text id=otherPlaceTo"+countRMALocal+" style=width:60px;display:none onkeypress='javascript:return checkChar(event);' />";
    row +="</td>";
	
    row += "<td>";
        row += "<select id=localConveyanceMode"+countRMALocal+" name=conveyanceMode style=width:75px onchange=javascript:checkLocalCmode("+countRMALocal+");totalAdjustment("+countRMALocal+"); >"
                                                +"<option value='Select'>Select</option>"
                                                +"<option value='Govt Tpt.'>Govt Tpt.</option>"
												+"<option value='Shared Taxi'>Shared Taxi</option>"
												+"<option value='Non-Shared Taxi'>Non-Shared Taxi</option>"
												+"<option value='Shared Auto'>Shared Auto</option>"
												+"<option value='Non-Shared Auto'>Non-Shared Auto</option>"
												+"<option value='Bus'>Bus</option>"
											+"</select>";			
    row += "</td>";
    
	row += "<td id=distanceLocalTd"+countRMALocal+">";
        row += "<input type=text id=distanceLocal"+countRMALocal+" style=width:50px onkeypress='javascript:return checkFloatTada(event,distanceLocal"+countRMALocal+")'; onchange='javascript:getRMALocalAmount("+countRMALocal+"); totalAdjustment("+countRMALocal+")'/>";			
    row += "</td>";
	
	row += "<td id=amountPerKmLocalTd"+countRMALocal+">";
        row += "<input type=text id=amountPerKmLocal"+countRMALocal+" style=width:50px onkeypress='javascript:return checkFloatTada(event,amountPerKmLocal"+countRMALocal+")'; onchange='javascript:getRMALocalAmount("+countRMALocal+"); totalAdjustment("+countRMALocal+")'/>";			
    row += "</td>";

    row += "<td>";
        row += "<input type=text readOnly=readOnly id=totalAmountLocal"+countRMALocal+" style=width:60px />";			
    row += "</td>";
	
	row += "<td>";
		row += "<input type=button id=newDaRMALocalAdd"+countRMALocal+"   value=+ onclick=javascript:checkRMALocalRow(); />";
	row += "</td>";

	row += "<td>";
		row += "<input type=button id=newDaRMALocalDel"+countRMALocal+"   value=- onclick=javascript:deleteNewRMALocalRow(this,\'"+idvalue+"\'); />";
	row += "</td>";
	
row += "</tr>";

$jq("#daNewRMALocalRow"+(countRMALocal-1)).after(row);
}
function deleteNewRMALocalRow(node,tableID)
{
	var dynmicRow = document.getElementById(tableID);
	var index=node.parentNode.parentNode.rowIndex;
	
	 
	var del=confirm("Do you want to delete this row?");
	if(del==true) 
	{
		if(index>=0)
		{
			dynmicRow.deleteRow(index);
		}
		
	}
	var length1 = dynmicRow.rows.length;
	
	if(length1=='4')
	{
		funcreateNewRMADayRow(tableID);
	}
	countRMALocal--;
	totalAdjustment(countRMALocal);
}
function enableTravelType(){
	for(var i=0;i<daNewDetailsList.length;i++){
		if(TadaRequestBean.tadaApprovalRequestDTO.gradePay==daNewDetailsList[i].gradePay){
			if(daNewDetailsList[i].milageAllw=="perkm"){
				$jq('#travelBy').show();
				$jq('#travelByA').show();
				$jq('#distance').show();
				$jq('#distanceA').show();
				$jq('#amountPerKm').show();
				$jq('#amountPerKmA').show();
				
			}
			if(daNewDetailsList[i].milageAllw=="perday"){
				$jq('#amountPerDayA').show();
				$jq('#amountPerDayA').show();
			}
			
		}
	}
}
function enableFromCityClass(countt){
	if($jq('#startStation'+countt+' option:selected').val()=="Other"){
		$jq('#otherStartStation'+countt+'').show();
		$jq('#otherStartStation'+countt+'').focus();
	}else{
		$jq('#otherStartStation'+countt+'').hide();
	}
}
function enableToCityClass(countt){
	if($jq('#endStation'+countt+' option:selected').val()=="Other"){
		$jq('#otherEndStation'+countt+'').show();
		$jq('#otherEndStation'+countt+'').focus();
	}else{
		$jq('#otherEndStation'+countt+'').hide();
	}
}

function checkOldDaAmount(countt){
	for(var i=0;i<daOldDetailsList.length;i++){
		if(TadaRequestBean.tadaApprovalRequestDTO.basicPay>=daOldDetailsList[i].payRangeFrom && TadaRequestBean.tadaApprovalRequestDTO.basicPay<=daOldDetailsList[i].payRangeTo){
			
						if($jq('input:radio[name=daOldRequirement]:checked').val()=="normalRate"){
							for(var j=0;j<cityTypeList.length;j++){
								if(cityTypeList[j].id==daOldDetailsList[i].cityTypeId){
									if($jq('#cityClass'+countt+'').val()==cityTypeList[j].cityClass){
										if($jq('#oldDaAmtPerDay'+countt+'').val()>daOldDetailsList[i].ord){
											alert("You Will get only maximum of Rs."+daOldDetailsList[i].ord+" per day");
											$jq('#oldDaAmtPerDay'+countt+'').val("");
											$jq('#oldDaAmtPerDay'+countt+'').focus();
											break;
										}
									}
								}
							}
						} else {
							$jq('#oldDaAmount'+countt+'').val($jq('#oldDaAmtPerDay'+countt+'').val());
						}
						if($jq('input:radio[name=daOldRequirement]:checked').val()=="hotelRate"){
							for(var j=0;j<cityTypeList.length;j++){
								if(cityTypeList[j].id==daOldDetailsList[i].cityTypeId){
									if($jq('#cityClass'+countt+'').val()==cityTypeList[j].cityClass){
										if($jq('#oldDaAmtPerDay'+countt+'').val()>daOldDetailsList[i].hotel){
											alert("You Will get only maximum of Rs."+daOldDetailsList[i].hotel+" per day");
											$jq('#oldDaAmtPerDay'+countt+'').val("");
											$jq('#oldDaAmtPerDay'+countt+'').focus();
											break;
										}
									}
								}
							}
						}
		}
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
function journeyTotalAmount(countJourney){
	$jq('#totalJourneyAmount'+countJourney+'').val($jq('#ticketFare'+countJourney+'').val());
}
function totalAdjustment(countTotal){
	var grandTotal=0;
	
	var dynRowJourney=document.getElementById("journeyDetailsId");
	var lengthJourney=dynRowJourney.rows.length;
	
	for(var j=2;j<lengthJourney;j++){
		var journeyVal=0;
		if(tadaRequestBean.tadaApprovalRequestDTO.stayDuration!=0){
			journeyVal = parseFloat(dynRowJourney.rows[j].cells[8].childNodes[0].value);
			if(!isNaN(parseFloat(dynRowJourney.rows[j].cells[9].childNodes[0].value))){
				if(parseFloat(dynRowJourney.rows[j].cells[9].childNodes[0].value)!=0 && parseFloat(dynRowJourney.rows[j].cells[9].childNodes[0].value)>0){
					journeyVal = parseFloat(dynRowJourney.rows[j].cells[9].childNodes[0].value);
				}
			}
		}else{
			journeyVal = parseFloat(dynRowJourney.rows[j].cells[9].childNodes[0].value);
		}
		if(isNaN(journeyVal)){
			journeyVal=0;
		}
		grandTotal=journeyVal+grandTotal;
	}
	if($jq('input:radio[name=tadaRequirement]:checked').val()=="oldDA" || $jq('input:radio[name=tadaRequirement]:checked').val()=="newDA"){
		var dynRowRMAKm=document.getElementById("daNewRMAKmId");
		var lengthRMAKm=dynRowRMAKm.rows.length;
		
		var dynRowRMALocal=document.getElementById("daNewRMALocalId");
		var lengthRMALocal=dynRowRMALocal.rows.length;
		
		var dynRowRMADaily=document.getElementById("daNewRMADailyId");
		var lengthRMADaily=dynRowRMADaily.rows.length;
		
		for(var i=1;i<lengthRMALocal;i++){
			var rmaLocalVal = parseFloat(dynRowRMALocal.rows[i].cells[8].childNodes[0].value);
			var rmaLocalVal1 = parseFloat(dynRowRMALocal.rows[i].cells[9].childNodes[0].value);
			if(isNaN(rmaLocalVal)){
				rmaLocalVal=0;
			}
			if(isNaN(rmaLocalVal1)){
				rmaLocalVal1=0;
			}
			grandTotal=(rmaLocalVal*rmaLocalVal1)+grandTotal;
		}
		for(var i=1;i<lengthRMAKm;i++){
			var rmaKmVal = parseFloat(dynRowRMAKm.rows[i].cells[4].childNodes[0].value);
			var rmaKmVal1 = parseFloat(dynRowRMAKm.rows[i].cells[5].childNodes[0].value);
			if(isNaN(rmaKmVal)){
				rmaKmVal=0;
			}
			if(isNaN(rmaKmVal1)){
				rmaKmVal1=0;
			}
			grandTotal=(rmaKmVal*rmaKmVal1)+grandTotal;
		}
		for(var i=1;i<lengthRMADaily;i++){
			var rmaKmVal = parseFloat(dynRowRMADaily.rows[i].cells[4].childNodes[0].value);
			var rmaKmVal1 = parseFloat(dynRowRMADaily.rows[i].cells[5].childNodes[0].value);
			if(isNaN(rmaKmVal)){
				rmaKmVal=0;
			}
			if(isNaN(rmaKmVal1)){
				rmaKmVal1=0;
			}
			grandTotal=(rmaKmVal*rmaKmVal1)+grandTotal;
		}
	}
	
	if($jq('input:radio[name=tadaRequirement]:checked').val()=="oldDA"){
		if($jq('#daOldDetailsId').is(':visible')){
			var dynRowDaOld=document.getElementById("daOldDetailsId");
			var lengthDaOld=dynRowDaOld.rows.length;
			
			for(var i=1;i<lengthDaOld;i++){
				var daOldVal = parseFloat(dynRowDaOld.rows[i].cells[6].childNodes[0].value);
				var daOldVal1 = parseFloat(dynRowDaOld.rows[i].cells[3].childNodes[0].value);
				if(isNaN(daOldVal)){
					daOldVal=0;
				}
				if(isNaN(daOldVal1)){
					daOldVal1=0;
				}
				grandTotal=daOldVal+daOldVal1+grandTotal;
			}
		}
	}
	if($jq('input:radio[name=tadaRequirement]:checked').val()=="newDA"){
		var dynRowDaNewAcc=document.getElementById("daNewAccDetailsId");
		var lengthDaNewAcc=dynRowDaNewAcc.rows.length;
		
		var dynRowDaNewFood=document.getElementById("daNewFoodDetailsId");
		var lengthDaNewFood=dynRowDaNewFood.rows.length;
		
		var dynRowRMADay=document.getElementById("daNewRMADayId");
		var lengthRMADay=dynRowRMADay.rows.length;
		
		for(var i=1;i<lengthRMADay;i++){
			var rmaDayVal = parseFloat(dynRowRMADay.rows[i].cells[3].childNodes[0].value);
			if(isNaN(rmaDayVal)){
				rmaDayVal=0;
			}
			grandTotal=rmaDayVal+grandTotal;
		}
		for(var i=1;i<lengthDaNewAcc;i++){
			var daNewAccVal = parseFloat(dynRowDaNewAcc.rows[i].cells[4].childNodes[0].value);
			if(isNaN(daNewAccVal)){
				daNewAccVal=0;
			}
			grandTotal=daNewAccVal+grandTotal;
		}
		for(var i=1;i<lengthDaNewFood;i++){
			var daNewFoodVal = parseFloat(dynRowDaNewFood.rows[i].cells[2].childNodes[0].value);
			if(isNaN(daNewFoodVal)){
				daNewFoodVal=0;
			}
			grandTotal=daNewFoodVal+grandTotal;
		}
	}
	
	$jq("#totAmt").html(roundNumber(grandTotal,2));
	
	$jq("#settlementAmount").html(parseFloat(grandTotal)-parseFloat($jq("#advAmount").html()));
}
function advanceTdApproval(requestID,requestType,historyID){
	document.forms[0].action = "tadaApprovalRequest.htm";
	document.forms[0].requestId.value = requestID;
	document.forms[0].requestType.value = requestType;
	document.forms[0].param.value = "tadaTdAdvance";
	document.forms[0].submit();
}
function reimbursementTdApproval(requestID,requestType,historyID){
	document.forms[0].action = "tadaApprovalRequest.htm";
	document.forms[0].requestId.value = requestID;
	document.forms[0].requestType.value = requestType;
	document.forms[0].param.value = "settlement";
	document.forms[0].type.value ="tadaTdReimbursement"
	document.forms[0].submit();
}
function getDaNewAmount(countNewDA){
	var food=0;
	var accommodation=0;
	food = parseFloat($jq('#food'+countNewDA+'').val());
	accommodation = parseFloat($jq('#accommodation'+countNewDA+'').val());
	if(isNaN(food))
		food=0;
	if(isNaN(accommodation))
		accommodation=0;
	$jq('#amount'+countNewDA+'').val(food + accommodation);
}
function getDaOldAmount(countOldDA){
	var amtperday=parseFloat($jq('#oldDaAmtPerDay'+countOldDA+'').val());
	if(isNaN(amtperday)){
		amtperday=0;
	}
	$jq('#oldDaAmount'+countOldDA+'').val(amtperday);
}
function checkDaNewAmount(countNewDA){
	tadaDaPercentage=parseFloat($jq('#tadaDaPercentage')).val()/100;
	for(var i=0;i<daNewDetailsList.length;i++){
		if(TadaRequestBean.tadaApprovalRequestDTO.gradePay==daNewDetailsList[i].gradePay){
			if(parseFloat($jq('#food'+countNewDA+'').val())>(daNewDetailsList[i].foodBill*0.5)){
				if(parseFloat($jq('#food'+countNewDA+'').val())>((daNewDetailsList[i].foodBill*tadaDaPercentage)+(daNewDetailsList[i].foodBill))){               //Here we increarse da percentage 0.25 to 0.50
					alert("You Will get only maximum of Rs."+((daNewDetailsList[i].foodBill*tadaDaPercentage)+(daNewDetailsList[i].foodBill))+" per day");             //Here we increarse da percentage 0.25 to 0.50
					$jq('#food'+countNewDA+'').val("");
					$jq('#amount'+countNewDA+'').val("");
					$jq('#food'+countNewDA+'').focus();
				}
			}
			if(parseFloat($jq('#accommodation'+countNewDA+'').val())>(daNewDetailsList[i].accommodationBill*0.5)){
				if(parseFloat($jq('#accommodation'+countNewDA+'').val())>((daNewDetailsList[i].accommodationBill*tadaDaPercentage)+(daNewDetailsList[i].accommodationBill))){             //Here we increarse da percentage 0.25 to 0.50
					alert("You Will get only maximum of Rs."+((daNewDetailsList[i].accommodationBill*tadaDaPercentage)+(daNewDetailsList[i].accommodationBill))+" per day");               //Here we increarse da percentage 0.25 to 0.50
					$jq('#accommodation'+countNewDA+'').val("");
					$jq('#amount'+countNewDA+'').val("");
					$jq('#accommodation'+countNewDA+'').focus();
				}
			}
		}
	}
	
}
function checkRMATravelType(countRMAKm){
	for(var i=0;i<daNewDetailsList.length;i++){
		if(TadaRequestBean.tadaApprovalRequestDTO.gradePay==daNewDetailsList[i].gradePay){
			if(daNewDetailsList[i].travelBy=='Non-AC Taxi'){
				if($jq('#travelBy'+countRMAKm+' option:selected').val()=='AC Taxi'){
					alert("You are not getting AC Taxi charges");
				}
			}
				
		}
	}
	
}
function getDaNewRMAKmAmount(countRMAKm){
	var distance=0;
	var amountPerKm=0;
	distance = parseFloat($jq('#distance'+countRMAKm+'').val());
	amountPerKm = parseFloat($jq('#amountPerKm'+countRMAKm+'').val());
	
	if(isNaN(distance) || isNaN(amountPerKm)){
		$jq('#totalRMAKmAmount'+countRMAKm+'').val('');
	} else if(isNaN(distance)){
		distance=0;
	} else if(isNaN(amountPerKm)){
		amountPerKm=0; 
	} else {
		$jq('#totalRMAKmAmount'+countRMAKm+'').val(distance * amountPerKm);
	}
}
function getDaNewRMADailyAmount(countRMADaily){
	var distance=0;
	var amountPerKm=0;
	distance = parseFloat($jq('#distanceDaily'+countRMADaily+'').val());
	amountPerKm = parseFloat($jq('#amountPerKmDaily'+countRMADaily+'').val());
	
	if(isNaN(distance) || isNaN(amountPerKm)){
		$jq('#totalRMAKmAmountDaily'+countRMADaily+'').val('');
	} else if(isNaN(distance)){
		distance=0;
	} else if(isNaN(amountPerKm)){
		amountPerKm=0; 
	} else {
		$jq('#totalRMAKmAmountDaily'+countRMADaily+'').val(distance * amountPerKm);
	}
}
function getDaNewRMADayAmount(countRMADay){
	$jq('#totalAmount'+countRMADay+'').val($jq('#amountPerDay'+countRMADay+'').val());
	
}
function saveTadaTdSettlement(requestId,type,amendmentType){
	var status=true;
	var status1=true;
	var errorMessage='';
	var daNewDetailsJson={};
	var daNewAccDetailsJson={};
	var daNewFoodDetailsJson={};
	var daOldDetailsJson={};
	var daNewRMAKmJson={};
	var daNewRMADayJson={};
	var journeyJson={};
	var localRMAJson={};
	var mainJson={};
	var daNewRMADailyJson={};
	if($jq('input:radio[name=tadaRequirement]:checked').val()=="newDA"){
		if(true){
			var i=0;
			$jq("#daNewAccDetailsId tr:not(:first)").each(function() {
				if($jq(this).find("td").eq(0).find('input').val()==''){
					errorMessage += "Please Select From Date.\n";
					$jq(this).find("td").eq(0).find('input').focus();
					status=false;
				}
				if($jq(this).find("td").eq(1).find('input').val()==''){
					errorMessage += "Please Select In Tme.\n";
					$jq(this).find("td").eq(1).find('input').focus();
					status=false;
				}
				if($jq(this).find("td").eq(2).find('input').val()==''){
					errorMessage += "Please Select To Date.\n";
					$jq(this).find("td").eq(2).find('input').focus();
					status=false;
				}
				if($jq(this).find("td").eq(3).find('input').val()==''){
					errorMessage += "Please Select Out Time.\n";
					$jq(this).find("td").eq(3).find('input').focus();
					status=false;
				}
				if($jq(this).find("td").eq(4).find('input').val()==''){
					errorMessage += "Please Enter Amount For Accommodation.\n";
					$jq(this).find("td").eq(4).find('input').focus();
					status=false;
				}
				i++;
			});
			
		}
		if(true){
			var i=0;
			$jq("#daNewFoodDetailsId tr:not(:first)").each(function() {
				if($jq(this).find("td").eq(0).find('input').val()==''){
					errorMessage += "Please Select From Date.\n";
					$jq(this).find("td").eq(0).find('input').focus();
					status=false;
				}
				if($jq(this).find("td").eq(1).find('input').val()==''){
					errorMessage += "Please Select To Date.\n";
					$jq(this).find("td").eq(1).find('input').focus();
					status=false;
				}
				if($jq(this).find("td").eq(2).find('input').val()==''){
					errorMessage += "Please Enter Amount For Food.\n";
					$jq(this).find("td").eq(2).find('input').focus();
					status=false;
				}
				i++;
			});
			
		}
		for(var i=0;i<daNewDetailsList.length;i++){
			if(TadaRequestBean.tadaApprovalRequestDTO.gradePay==daNewDetailsList[i].gradePay){
				if(daNewDetailsList[i].milageAllw=="perday"){
					if(true){
						var l=0;
						if($jq('#daNewRMADayId').is(':visible')){
							$jq("#daNewRMADayId tr:not(:first)").each(function() {
								if($jq(this).find("td").eq(0).find('input').val()==''){
									errorMessage += "Please Select Date.\n";
									$jq(this).find("td").eq(0).find('input').focus();
									status=false;
								}
								if($jq(this).find("td").eq(1).find('input').val()==''){
									errorMessage += "Please Enter From Place.\n";
									$jq(this).find("td").eq(1).find('input').focus();
									status=false;
								}
								if($jq(this).find("td").eq(2).find('input').val()==''){
									errorMessage += "Please Enter To Place.\n";
									$jq(this).find("td").eq(2).find('input').focus();
									status=false;
								}
								if($jq(this).find("td").eq(3).find('input').val()==''){
									errorMessage += "Please Enter Amount Per Day.\n";
									$jq(this).find("td").eq(3).find('input').focus();
									status=false;
								}
								l++;
							});
						}
					}
				} else {
					if(true){
						var j=0;
						if($jq('#daNewRMAKmId').is(':visible')){
							$jq("#daNewRMAKmId tr:not(:first)").each(function() {
								if($jq(this).find("td").eq(0).find('input').val()==''){
									errorMessage += "Please Select Date.\n";
									$jq(this).find("td").eq(0).find('input').focus();
									status=false;
								}
								if($jq(this).find("td").eq(1).find('input').val()==''){
									errorMessage += "Please Enter From Place.\n";
									$jq(this).find("td").eq(1).find('input').focus();
									status=false;
								}
								if($jq(this).find("td").eq(2).find('input').val()==''){
									errorMessage += "Please Enter To Place.\n";
									$jq(this).find("td").eq(2).find('input').focus();
									status=false;
								}
								if($jq(this).find("td").eq(3).find('select').val()=='Select'){
									errorMessage += "Please Select Travel By.\n";
									$jq(this).find("td").eq(3).find('select').focus();
									status=false;
								}
								if($jq(this).find("td").eq(4).find('input').val()==''){
									errorMessage += "Please Enter Distance.\n";
									$jq(this).find("td").eq(4).find('input').focus();
									status=false;
								}
								if($jq(this).find("td").eq(5).find('input').val()==''){
									errorMessage += "Please Enter Amount.\n";
									$jq(this).find("td").eq(5).find('input').focus();
									status=false;
								}
								j++;
							});
						}
					}
				}
			}
		}
	}
	if($jq('input:radio[name=tadaRequirement]:checked').val()=="oldDA"){
		if(true){
			var j=0;
			if($jq('#daNewRMAKmId').is(':visible')){
				$jq("#daNewRMAKmId tr:not(:first)").each(function() {
					if($jq(this).find("td").eq(0).find('input').val()==''){
						errorMessage += "Please Select Date.\n";
						$jq(this).find("td").eq(0).find('input').focus();
						status=false;
					}
					if($jq(this).find("td").eq(1).find('input').val()==''){
						errorMessage += "Please Enter From Place.\n";
						$jq(this).find("td").eq(1).find('input').focus();
						status=false;
					}
					if($jq(this).find("td").eq(2).find('input').val()==''){
						errorMessage += "Please Enter To Place.\n";
						$jq(this).find("td").eq(2).find('input').focus();
						status=false;
					}
					if($jq(this).find("td").eq(3).find('select').val()=='Select'){
						errorMessage += "Please Select Travel By.\n";
						$jq(this).find("td").eq(3).find('select').focus();
						status=false;
					}
					if($jq(this).find("td").eq(4).find('input').val()==''){
						errorMessage += "Please Enter Distance.\n";
						$jq(this).find("td").eq(4).find('input').focus();
						status=false;
					}
					if($jq(this).find("td").eq(5).find('input').val()==''){
						errorMessage += "Please Enter Amount.\n";
						$jq(this).find("td").eq(5).find('input').focus();
						status=false;
					}
					j++;
				});
			}
		}
	}
	if(true){
		var k=0;
		$jq("#journeyDetailsId tr:not(:first)").each(function() {
			if($jq(this).find("td").eq(0).find('input').val()=='' && 
					$jq(this).find("td").eq(1).find('input').val()=='' &&
					$jq(this).find("td").eq(2).find('select').val()=='Select' &&
					$jq(this).find("td").eq(3).find('input').val()=='' &&
					$jq(this).find("td").eq(4).find('input').val()=='' && $jq(this).find("td").eq(5).find('select').val()=='Select' &&
				    $jq(this).find("td").eq(7).find('select').val()=='Select' &&
					$jq(this).find("td").eq(8).find('input').val()==''){
				errorMessage += "Please Fill row-"+(k)+" of journey details.\n";
				status=false;
				
			}else{
				if($jq(this).find("td").eq(0).find('input').val()==''){
					errorMessage += "Please Select Departure Date.\n";
					$jq(this).find("td").eq(0).find('input').focus();
					status=false;
				}
				if($jq(this).find("td").eq(1).find('input').val()==''){
					errorMessage += "Please Select Departure Time.\n";
					$jq(this).find("td").eq(1).find('input').focus();
					status=false;
				}
				if($jq(this).find("td").eq(2).find('select').val()=='Select'){
					errorMessage += "Please Select Departure Station.\n";
					$jq(this).find("td").eq(2).find('input').focus();
					status=false;
				}
				if($jq(this).find("td").eq(3).find('input').val()==''){
					errorMessage += "Please Select Arrival Date.\n";
					$jq(this).find("td").eq(3).find('input').focus();
					status=false;
				}
				if($jq(this).find("td").eq(4).find('input').val()==''){
					errorMessage += "Please Select Arrival Time.\n";
					$jq(this).find("td").eq(4).find('input').focus();
					status=false;
				}
				if($jq(this).find("td").eq(5).find('select').val()=='Select'){
					errorMessage += "Please Select Arrival Station.\n";
					$jq(this).find("td").eq(5).find('input').focus();
					status=false;
				}
				/*if($jq(this).find("td").eq(6).find('input').val()==''){
					errorMessage += "Please Enter Distance.\n";
					$jq(this).find("td").eq(6).find('input').focus();
					status=false;
				}*/
				if($jq(this).find("td").eq(7).find('select').val()=='Select'){
					errorMessage += "Please Select Mode Of Travel.\n";
					$jq(this).find("td").eq(7).find('input').focus();
					status=false;
				}
				if($jq(this).find("td").eq(8).find('input').val()==''){
					errorMessage += "Please Enter Ticket Fare Rs.\n";
					$jq(this).find("td").eq(8).find('input').focus();
					status=false;
				}
				/*if($jq(this).find("td").eq(9).find('input').val()==''){
					errorMessage += "Please Enter Ticket Cancel Fare Rs.\n";
					$jq(this).find("td").eq(9).find('input').focus();
					status=false;
				}*/
				/*if($jq(this).find("td").eq(10).find('input').val()==''){
					errorMessage += "Please Enter Ticket No.\n";
					$jq(this).find("td").eq(10).find('input').focus();
					status=false;
				}*/
			}
			k++;
		});
		
	}
	if(true){
		var j=0;
		if($jq('#daNewRMALocalId').is(':visible')){
			$jq("#daNewRMALocalId tr:not(:first)").each(function() {
				if($jq(this).find("td").eq(0).find('input').val()==''){
					errorMessage += "Please Select Date.\n";
					$jq(this).find("td").eq(0).find('input').focus();
					status=false;
				}
				if($jq(this).find("td").eq(1).find('select').val()=='Select'){
					errorMessage += "Please Select From Place.\n";
					$jq(this).find("td").eq(1).find('select').focus();
					status=false;
				}
				if($jq(this).find("td").eq(2).find('input').is(':visible')){
					if($jq(this).find("td").eq(2).find('input').val()==''){
						errorMessage += "Please Enter Residence Place.\n";
						$jq(this).find("td").eq(2).find('input').focus();
						status=false;
					}
				}
				if($jq(this).find("td").eq(3).find('input').is(':visible')){
					if($jq(this).find("td").eq(3).find('input').val()==''){
						errorMessage += "Please Enter Other From Place.\n";
						$jq(this).find("td").eq(3).find('input').focus();
						status=false;
					}
				}
				if($jq(this).find("td").eq(4).find('select').val()=='Select'){
					errorMessage += "Please Select To Place.\n";
					$jq(this).find("td").eq(4).find('select').focus();
					status=false;
				}
				if($jq(this).find("td").eq(5).find('input').is(':visible')){
					if($jq(this).find("td").eq(5).find('input').val()==''){
						errorMessage += "Please Enter Residence Place.\n";
						$jq(this).find("td").eq(5).find('input').focus();
						status=false;
					}
				}
				if($jq(this).find("td").eq(6).find('input').is(':visible')){
					if($jq(this).find("td").eq(6).find('input').val()==''){
						errorMessage += "Please Enter Other To Place.\n";
						$jq(this).find("td").eq(6).find('input').focus();
						status=false;
					}
				}
				if($jq(this).find("td").eq(7).find('select').val()=='Select'){
					errorMessage += "Please Select Mode of Conveyance.\n";
					$jq(this).find("td").eq(7).find('select').focus();
					status=false;
				}
				if($jq(this).find("td").eq(8).find('input').val()==''){
					errorMessage += "Please Enter Distance.\n";
					$jq(this).find("td").eq(8).find('input').focus();
					status=false;
				}
				if($jq(this).find("td").eq(9).find('input').val()==''){
					errorMessage += "Please Enter Amount per k.m.\n";
					$jq(this).find("td").eq(9).find('input').focus();
					status=false;
				}
				
				j++;
			});
		}
	}
	if($jq('#DAReqDiv').is(':visible')){
		if($jq('input:radio[name=tadaRequirement]:checked').val()!="oldDA" && $jq('input:radio[name=tadaRequirement]:checked').val()!="newDA"){
			errorMessage += "Please Choose DA Requirement.\n";
			status=false;
		}
	}
	if($jq('input:radio[name=tadaRequirement]:checked').val()=="newDA"){ 
		if(status){
			var i=0;
			if($jq('#daNewAccDetailsId').is(':visible')){
				$jq("#daNewAccDetailsId tr:not(:first)").each(function() {
					var valuesJson={};
					valuesJson['fromDate'] = $jq(this).find("td").eq(0).find('input').val();
					//valuesJson['inTime'] = $jq(this).find("td").eq(1).find('input').val();
					valuesJson['toDate'] = $jq(this).find("td").eq(2).find('input').val();
					//valuesJson['outTime'] = $jq(this).find("td").eq(3).find('input').val();
					valuesJson['accAmount'] = $jq(this).find("td").eq(4).find('input').val();
					// valuesJson['calAccAmount'] =
					// $jq(this).find("td").eq(5).find('input').val();
					valuesJson['amountClaimed'] = $jq(this).find("td").eq(5).find('input').val();
					daNewAccDetailsJson[i]=valuesJson;
					i++;
				});
			}
		}
		if(status){
			var i=0;
			if($jq('#daNewFoodDetailsId').is(':visible')){
				$jq("#daNewFoodDetailsId tr:not(:first)").each(function() {
					var valuesJson={};
					valuesJson['fromDate'] = $jq(this).find("td").eq(0).find('input').val();
					valuesJson['toDate'] = $jq(this).find("td").eq(1).find('input').val();
					valuesJson['foodAmount'] = $jq(this).find("td").eq(2).find('input').val();
					// valuesJson['calFoodAmount'] =
					// $jq(this).find("td").eq(3).find('input').val();
					valuesJson['amountClaimed'] = $jq(this).find("td").eq(3).find('input').val();
					daNewFoodDetailsJson[i]=valuesJson;
					i++;
				});
			}
		}
		for(var i=0;i<daNewDetailsList.length;i++){
			if(TadaRequestBean.tadaApprovalRequestDTO.gradePay==daNewDetailsList[i].gradePay){
				if(daNewDetailsList[i].milageAllw=="perday"){
					if(status){
						var l=0;
						if($jq('#daNewRMADayId').is(':visible')){
							$jq("#daNewRMADayId tr:not(:first)").each(function() {
								var valuesJson={};
								valuesJson['dateRMADay'] = $jq(this).find("td").eq(0).find('input').val();
								valuesJson['fromPlace'] = $jq(this).find("td").eq(1).find('input').val();
								valuesJson['toPlace'] = $jq(this).find("td").eq(2).find('input').val();
								valuesJson['amountPerDay'] = $jq(this).find("td").eq(3).find('input').val();
								valuesJson['calAmountPerDay'] = $jq(this).find("td").eq(4).find('input').val();
								valuesJson['totalRMADayAmount'] = $jq(this).find("td").eq(5).find('input').val();
								daNewRMADayJson[l]=valuesJson;
								l++;
							});
						}
					}
				} else {
					if(status){
						var j=0;
						if($jq('#daNewRMAKmId').is(':visible')){
							$jq("#daNewRMAKmId tr:not(:first)").each(function() {
								var valuesJson={};
								valuesJson['dateRMAKm'] = $jq(this).find("td").eq(0).find('input').val();
								valuesJson['fromPlace'] = $jq(this).find("td").eq(1).find('input').val();
								valuesJson['toPlace'] = $jq(this).find("td").eq(2).find('input').val();
								valuesJson['travelBy'] = $jq(this).find("td").eq(3).find('select').val();
								valuesJson['distance'] = $jq(this).find("td").eq(4).find('input').val();
								valuesJson['amountPerKm'] = $jq(this).find("td").eq(5).find('input').val();
								valuesJson['totalRMAKmAmount'] = $jq(this).find("td").eq(6).find('input').val();
								daNewRMAKmJson[j]=valuesJson;
								j++;
							});
						}
					}
				}
			}
		}
	}
	if($jq('input:radio[name=tadaRequirement]:checked').val()=="oldDA"){ 
		if(status){
			var i=0;
			if($jq('#daOldDetailsId').is(':visible')){
				$jq("#daOldDetailsId tr:not(:first)").each(function() {
					var valuesJson={};
					valuesJson['oldDaDate'] = $jq(this).find("td").eq(0).find('input').val();
					valuesJson['jdaDays'] = $jq(this).find("td").eq(1).find('input').val();
					valuesJson['jdaAmount'] = $jq(this).find("td").eq(2).find('input').val();
					valuesJson['totalJdaAmount'] = $jq(this).find("td").eq(3).find('input').val();
					valuesJson['sdaDays'] = $jq(this).find("td").eq(4).find('input').val();
					valuesJson['sdaAmount'] = $jq(this).find("td").eq(5).find('input').val();
					valuesJson['totalSdaAmount'] = $jq(this).find("td").eq(6).find('input').val();
					valuesJson['daTypeRate'] = $jq('input:radio[name=daOldRequirement]:checked').val();
					daOldDetailsJson[i]=valuesJson;
					i++;
				});
			}
		}
		if(status){
			var j=0;
			if($jq('#daNewRMAKmId').is(':visible')){
				$jq("#daNewRMAKmId tr:not(:first)").each(function() {
					var valuesJson={};
					valuesJson['dateRMAKm'] = $jq(this).find("td").eq(0).find('input').val();
					valuesJson['fromPlace'] = $jq(this).find("td").eq(1).find('input').val();
					valuesJson['toPlace'] = $jq(this).find("td").eq(2).find('input').val();
					valuesJson['travelBy'] = $jq(this).find("td").eq(3).find('select').val();
					valuesJson['distance'] = $jq(this).find("td").eq(4).find('input').val();
					valuesJson['amountPerKm'] = $jq(this).find("td").eq(5).find('input').val();
					valuesJson['totalRMAKmAmount'] = $jq(this).find("td").eq(6).find('input').val();
					daNewRMAKmJson[j]=valuesJson;
					j++;
				});
			}
		}
	}
	
	
	if(status){
		var k=0;
		if($jq('#journeyDetailsId').is(':visible')){
			$jq("#journeyDetailsId tr:gt(1)").each(function() {
				var valuesJson={};
				valuesJson['journeyDate'] = $jq(this).find("td").eq(0).find('input').val();
				valuesJson['startTime'] = $jq(this).find("td").eq(1).find('input').val();
				if($jq(this).find("td").eq(2).find('select').val()!='Other' && $jq(this).find("td").eq(2).find('select').val()!='Select'){
					valuesJson['startStation'] = $jq(this).find("td").eq(2).find('select').val();
				} else {
					valuesJson['startStation'] = $jq(this).find("td").eq(2).find('input').val();
				}
				valuesJson['journeyEndDate'] = $jq(this).find("td").eq(3).find('input').val();
				valuesJson['endTime'] = $jq(this).find("td").eq(4).find('input').val();
				if($jq(this).find("td").eq(5).find('select').val()!='Select' && $jq(this).find("td").eq(5).find('select').val()!='Other'){
					valuesJson['endStation'] = $jq(this).find("td").eq(5).find('select').val();
				} else {
					valuesJson['endStation'] = $jq(this).find("td").eq(5).find('input').val();
				}
				if($jq(this).find("td").eq(6).find('input').val()!='')
					valuesJson['distanceJourney'] = $jq(this).find("td").eq(6).find('input').val();
				else
					valuesJson['distanceJourney'] = "0";
				valuesJson['modeOfTravel'] = $jq(this).find("td").eq(7).find('select').val();
				valuesJson['ticketFare'] = $jq(this).find("td").eq(8).find('input').val();
				if($jq(this).find("td").eq(9).find('input').val()!='')
					valuesJson['totalJourneyAmount'] = $jq(this).find("td").eq(9).find('input').val();
				else
					valuesJson['totalJourneyAmount'] = "0";
				if($jq(this).find("td").eq(10).find('input').val()!='')
					valuesJson['ticketNo'] = $jq(this).find("td").eq(10).find('input').val();
				else
					valuesJson['ticketNo'] = "0";
				journeyJson[k]=valuesJson;
				k++;
			});
		}
	}
	if(status){
		var j=0;
		if($jq('#daNewRMALocalId').is(':visible')){
			$jq("#daNewRMALocalId tr:not(:first)").each(function() {
				var valuesJson={};
				valuesJson['dateRMALocal'] = $jq(this).find("td").eq(0).find('input').val();
				if($jq(this).find("td").eq(2).find('input').is(':visible')){
					valuesJson['fromPlaceLocal'] = $jq(this).find("td").eq(2).find('input').val();
				} else if($jq(this).find("td").eq(3).find('input').is(':visible')){
					valuesJson['fromPlaceLocal'] = $jq(this).find("td").eq(3).find('input').val();
				} else {
					valuesJson['fromPlaceLocal'] = $jq(this).find("td").eq(1).find('select').val();
				}
				if($jq(this).find("td").eq(5).find('input').is(':visible')){
					valuesJson['toPlaceLocal'] = $jq(this).find("td").eq(5).find('input').val();
				} else if($jq(this).find("td").eq(6).find('input').is(':visible')){
					valuesJson['toPlaceLocal'] = $jq(this).find("td").eq(6).find('input').val();
				} else {
					valuesJson['toPlaceLocal'] = $jq(this).find("td").eq(4).find('select').val();
				}
				valuesJson['localCMode'] = $jq(this).find("td").eq(7).find('select').val();
				valuesJson['localRMADistance'] = $jq(this).find("td").eq(8).find('input').val();
				valuesJson['localRMAAmtPerKm'] = $jq(this).find("td").eq(9).find('input').val();
				valuesJson['totalRMALocalAmount'] = $jq(this).find("td").eq(10).find('input').val();
				localRMAJson[j]=valuesJson;
				j++;
			});
		}
	}
	if(status){
		var j=0;
		if($jq('#daNewRMADailyId').is(':visible')){
			$jq("#daNewRMADailyId tr:not(:first)").each(function() {
				var valuesJson={};
				valuesJson['dateRMAKm'] = $jq(this).find("td").eq(0).find('input').val();
				valuesJson['fromPlace'] = $jq(this).find("td").eq(1).find('input').val();
				valuesJson['toPlace'] = $jq(this).find("td").eq(2).find('input').val();
				valuesJson['travelBy'] = $jq(this).find("td").eq(3).find('select').val();
				valuesJson['distance'] = $jq(this).find("td").eq(4).find('input').val();
				valuesJson['amountPerKm'] = $jq(this).find("td").eq(5).find('input').val();
				valuesJson['totalRMAKmAmount'] = $jq(this).find("td").eq(6).find('input').val();
				daNewRMADailyJson[j]=valuesJson;
				j++;
			});
		}
	}
	mainJson["daNewAccDetails"]=daNewAccDetailsJson;
	mainJson["daNewFoodDetails"]=daNewFoodDetailsJson;
	mainJson["daOldDetails"]=daOldDetailsJson;
	mainJson["daNewRMAKmDetails"]=daNewRMAKmJson;
	mainJson["journeyDetails"]=journeyJson;
	mainJson["daNewRMADayDetails"]=daNewRMADayJson;
	mainJson["localRMADetails"]=localRMAJson;
	mainJson["daNewRMADailyDetails"]=daNewRMADailyJson;
	if(status){
		if($jq('#rmaDayId').is(':visible') && $jq('#rmaDailyId').is(':visible') && $jq('#rmaId').is(':visible')){
			if(!$jq('#rmaDayId').is(':checked') && !$jq('#rmaDailyId').is(':checked') && !$jq('#rmaId').is(':checked')){
				if(!confirm("Are you sure submit the request without Daily RMA at TD,RMA at TD & Local RMA Details.\n")){
					status1=false;
				}
			}
		}
	}
	if (status && status1) {
		var requestDetails = {
				"referenceRequestID" :requestId,
				"param" : "submitRequest",
				"type" : type,
				"amendmentType" : amendmentType,
				"jsonValue" : JSON.stringify(mainJson),
				"claimAmount" : $jq('#totAmt').html(),
				"advanceAmount" : $jq('#advAmount').html(),
				"userRemarks" : $jq('#userRemarks').val()
			};
			$jq.ajax( {
				type : "POST",
				url : "tadaApprovalRequest.htm?"+dispUrl,
				data : requestDetails,
				cache : false,
				success : function(data) {
					$jq('#result').html(data);
					if ($jq('.success').is(':visible')) {
						clearTadaTdSettlement();
						var requestType = $jq('#headTitle').html();
						var check=confirm( ""+requestType+" has been Successfully Sent...\n\nPlease click ok to 'Preview "+requestType+" Application Form 'Take print' from here\n\n");
						if(check){
						document.forms[0].requestId.value = $jq.trim(requestIDtadaA);
						//document.forms[0].roleId.value = 'roleId';
					   document.forms[0].param.value = "requestDetails";
						document.forms[0].action = "workflowmap.htm";
						document.forms[0].submit();	
						}
						
					}
					
				}
			});

	} else if(status==false){
		alert(errorMessage);
	}
}
function clearTadaTdSettlement(){
	var status=true;
	if(status){
		var i=0;
		$jq("#daNewAccDetailsId tr:not(:first)").each(function() {
			$jq(this).find("td").eq(0).find('input').val('');
			$jq(this).find("td").eq(1).find('input').val('');
			$jq(this).find("td").eq(2).find('input').val('');
			$jq(this).find("td").eq(3).find('input').val('');
			$jq(this).find("td").eq(4).find('input').val('');
			$jq(this).find("td").eq(5).find('input').val('');
			i++;
		});
	}
	if(status){
		var i=0;
		$jq("#daNewFoodDetailsId tr:not(:first)").each(function() {
			$jq(this).find("td").eq(0).find('input').val('');
			$jq(this).find("td").eq(1).find('input').val('');
			$jq(this).find("td").eq(2).find('input').val('');
			$jq(this).find("td").eq(3).find('input').val('');
			i++;
		});
	}
	if(status){
		var j=0;
		$jq("#daNewRMAKmId tr:not(:first)").each(function() {
			$jq(this).find("td").eq(0).find('input').val('');
			$jq(this).find("td").eq(1).find('input').val('');
			$jq(this).find("td").eq(2).find('input').val('');
			$jq(this).find("td").eq(3).find('select').val('');
			$jq(this).find("td").eq(4).find('input').val('');
			$jq(this).find("td").eq(5).find('input').val('');
			$jq(this).find("td").eq(6).find('input').val('');
			j++;
		});
		
	}
	if(status){
		var j=0;
		$jq("#daNewRMADayId tr:not(:first)").each(function() {
			$jq(this).find("td").eq(0).find('input').val('');
			$jq(this).find("td").eq(1).find('input').val('');
			$jq(this).find("td").eq(2).find('input').val('');
			$jq(this).find("td").eq(3).find('input').val('');
			$jq(this).find("td").eq(4).find('input').val('');
			$jq(this).find("td").eq(5).find('input').val('');
			j++;
		});
		
	}
	if(status){
		var j=0;
		$jq("#daNewRMADailyId tr:not(:first)").each(function() {
			$jq(this).find("td").eq(0).find('input').val('');
			$jq(this).find("td").eq(1).find('input').val('');
			$jq(this).find("td").eq(2).find('input').val('');
			$jq(this).find("td").eq(3).find('select').val('');
			$jq(this).find("td").eq(4).find('input').val('');
			$jq(this).find("td").eq(5).find('input').val('');
			$jq(this).find("td").eq(6).find('input').val('');
			j++;
		});
	}
	if(status){
		var k=0;
		$jq("#journeyDetailsId tr:not(:first)").each(function() {
			$jq(this).find("td").eq(0).find('input').val('');
			$jq(this).find("td").eq(1).find('input').val('');
			$jq(this).find("td").eq(2).find('select').val('Select');
			if($jq(this).find("td").eq(2).find('input').is(':visible')){
				$jq(this).find("td").eq(2).find('input').hide();
			}
			$jq(this).find("td").eq(3).find('input').val('');
			$jq(this).find("td").eq(4).find('input').val('');
			$jq(this).find("td").eq(5).find('select').val('Select');
			if($jq(this).find("td").eq(5).find('input').is(':visible')){
				$jq(this).find("td").eq(5).find('input').hide();
			}
			$jq(this).find("td").eq(6).find('input').val('');
			$jq(this).find("td").eq(7).find('select').val('Select');
			$jq(this).find("td").eq(8).find('input').val('');
			$jq(this).find("td").eq(9).find('input').val('');
			$jq(this).find("td").eq(10).find('input').val('');
			k++;
		});
		
	}
	if(status){
		var i=0;
		$jq("#daOldDetailsId tr:not(:first)").each(function() {
			$jq(this).find("td").eq(0).find('input').val('');
			$jq(this).find("td").eq(1).find('input').val('');
			$jq(this).find("td").eq(2).find('select').val('');
			$jq(this).find("td").eq(3).find('input').val('');
			$jq(this).find("td").eq(4).find('input').val('');
			$jq(this).find("td").eq(5).find('input').val('');
			i++;
		});
	}
	if(status){
		var k=0;
		$jq("#daNewRMALocalId tr:not(:first)").each(function() {
			$jq(this).find("td").eq(0).find('input').val('');
			$jq(this).find("td").eq(1).find('select').val('Select');
			$jq(this).find("td").eq(2).find('input').hide('');
			$jq(this).find("td").eq(3).find('input').hide('');
			$jq(this).find("td").eq(4).find('select').val('Select');
			$jq(this).find("td").eq(5).find('input').hide('');
			$jq(this).find("td").eq(6).find('input').hide('');
			$jq(this).find("td").eq(7).find('select').val('Select');
			$jq(this).find("td").eq(8).find('input').val('');
			$jq(this).find("td").eq(9).find('input').val('');
			$jq(this).find("td").eq(10).find('input').val('');
			k++;
		});
		
	}
	var tableID1='journeyDetailsId';
	var dynRow1 = document.getElementById(tableID1);
	if(dynRow1!=null){
		for(var i=3;i<dynRow1.rows.length;i++){
			dynRow1.deleteRow(i);
			dynRow1 = document.getElementById(tableID1);
			i=2;
		}
	}	
	var tableID3='daNewRMAKmId';
	var dynRow3 = document.getElementById(tableID3);
	if(dynRow3!=null){
		for(var i=2;i<dynRow3.rows.length;i++){
			dynRow3.deleteRow(i);
			dynRow3 = document.getElementById(tableID3);
			i=1;
		}
	}
	var tableID4='daNewRMADayId';
	var dynRow4 = document.getElementById(tableID4);
	if(dynRow4!=null){
		for(var i=2;i<dynRow4.rows.length;i++){
			dynRow4.deleteRow(i);
			dynRow4 = document.getElementById(tableID4);
			i=1;
		}
	}
	var tableID5='daOldDetailsId';
	var dynRow5 = document.getElementById(tableID5);
	if(dynRow5!=null){
		for(var i=2;i<dynRow5.rows.length;i++){
			dynRow5.deleteRow(i);
			dynRow5 = document.getElementById(tableID5);
			i=1;
		}
	}
	var tableID6='daNewRMALocalId';
	var dynRow6 = document.getElementById(tableID6);
	if(dynRow6!=null){
		for(var i=2;i<dynRow6.rows.length;i++){
			dynRow6.deleteRow(i);
			dynRow6 = document.getElementById(tableID6);
			i=1;
		}
	}
	var tableID7='daNewAccDetailsId';
	var dynRow7 = document.getElementById(tableID7);
	if(dynRow7!=null){
		for(var i=2;i<dynRow7.rows.length;i++){
			dynRow7.deleteRow(i);
			dynRow7 = document.getElementById(tableID7);
			i=1;
		}
	}
	var tableID8='daNewFoodDetailsId';
	var dynRow8 = document.getElementById(tableID8);
	if(dynRow8!=null){
		for(var i=2;i<dynRow8.rows.length;i++){
			dynRow8.deleteRow(i);
			dynRow8 = document.getElementById(tableID8);
			i=1;
		}
	}
	var tableID9='daNewRMADailyId';
	var dynRow9 = document.getElementById(tableID9);
	if(dynRow9!=null){
		for(var i=2;i<dynRow9.rows.length;i++){
			dynRow9.deleteRow(i);
			dynRow9 = document.getElementById(tableID9);
			i=1;
		}
	}
	$jq('input:radio[name=tadaRequirement]:checked').attr('checked',false);
	enableDA();
	$jq('input:radio[name=daOldRequirement]:checked').attr('checked',false);
	//enableDaDetails();
	$jq('#totAmt').text('0');
	$jq('#settlementAmount').text($jq('#advAmount').text());
	
	
}
function calculateAmountAfterRestriction(){
	var daNewAfterRestriction='';
	var foodDaNewAfterRestriction='';
	var stayDaNewAfterRestriction='';
	var accDaNewAfterRestriction='';
	var rmaKmAfterRestriction='';
	var journeyAfterRestriction='';
	var rmaDayAfterRestriction='';
	var daOldAfterRestriction='';
	var distanceAfterRestriction='';
	var kmAmountAfterRestriction='';
	var dayAmountAfterRestriction='';
	var daNewAmount=0;
	var foodDaNewAmount=0;
	var stayDaNewAmount=0;
	var accDaNewAmount=0;
	var rmaKmAmount=0;
	var journeyAmount=0;
	var rmaDayAmount=0;
	var daOldAmount=0;
	var distanceAftRes=0;
	var amountPerKmAftRes=0;
	var amountPerDayAftRes=0;
	var stayDaOldAfterRestriction='';
	var stayDaOldAmount=0;
	var amountPerDayOldDaAftRes='';
	var amountPerDay=0;
	var daOldAfterRestriction='';
	var daOldAmount=0;
	var status=true;
	$jq("#daNewDetailsId tr:not(:first)").each(function() {
		if($jq(this).find("td").eq(2).find('input').val()!='' && $jq(this).find("td").eq(2).find('input').val()!=undefined) {
			stayDaNewAfterRestriction = stayDaNewAfterRestriction + $jq(this).find("td").eq(2).find('input').val() + '-'+$jq(this).find("td").eq(2).find('input').attr('id')+'@'
			stayDaNewAmount = parseFloat(stayDaNewAmount)+parseFloat($jq(this).find("td").eq(2).find('input').val());
		}
		if($jq(this).find("td").eq(4).find('input').val()!='' && $jq(this).find("td").eq(4).find('input').val()!=undefined) {
			foodDaNewAfterRestriction = foodDaNewAfterRestriction + $jq(this).find("td").eq(4).find('input').val() + '-'+$jq(this).find("td").eq(4).find('input').attr('id')+'@'
			foodDaNewAmount = parseFloat(foodDaNewAmount)+parseFloat($jq(this).find("td").eq(4).find('input').val());
		}if($jq(this).find("td").eq(4).find('input').val()=='') {
			status=false;
		}
		if($jq(this).find("td").eq(6).find('input').val()!='' && $jq(this).find("td").eq(6).find('input').val()!=undefined) {
			accDaNewAfterRestriction = accDaNewAfterRestriction + $jq(this).find("td").eq(6).find('input').val() + '-'+$jq(this).find("td").eq(6).find('input').attr('id')+'@'
			accDaNewAmount = parseFloat(accDaNewAmount)+parseFloat($jq(this).find("td").eq(6).find('input').val());
		}if($jq(this).find("td").eq(6).find('input').val()=='') {
			status=false;
		}
		
		var value1=parseFloat($jq(this).find("td").eq(4).find('input').val());
		var value2=parseFloat($jq(this).find("td").eq(6).find('input').val());
		if(isNaN(value1)){
			value1=0;
		}
		if(isNaN(value2)){
			value2=0;
		}
		$jq(this).find("td").eq(8).find('input').val(value1+value2);
		if($jq(this).find("td").eq(8).find('input').val()!='' && $jq(this).find("td").eq(8).find('input').val()!=undefined) {
			daNewAfterRestriction = daNewAfterRestriction + $jq(this).find("td").eq(8).find('input').val() + '-'+$jq(this).find("td").eq(8).find('input').attr('id')+'@'
			daNewAmount = parseFloat(daNewAmount)+parseFloat($jq(this).find("td").eq(8).find('input').val());
		}if($jq(this).find("td").eq(8).find('input').val()=='') {
			status=false;
		}
		$jq('#restrictionAmount').html(daNewAmount);
		$jq('#daNewAmount').val(daNewAfterRestriction);
		$jq('#stayDaNewAmount').val(stayDaNewAfterRestriction);
		$jq('#foodDaNewAmount').val(foodDaNewAfterRestriction);
		$jq('#accDaNewAmount').val(accDaNewAfterRestriction);
		if(parseFloat(tadaAdvanceAmount) > 0) {
			$jq('#settleAmount').val(parseFloat(daNewAmount)-parseFloat(tadaAdvanceAmount));
		}else {
			$jq('#settleAmount').val(daNewAmount);
		}
		if(parseFloat(tadaAdvanceAmount)>0) {
			/**
			 * Settlement case
			 */
			$jq('#restrictionAmount').html(parseFloat(daNewAmount)-parseFloat(tadaAdvanceAmount));
		}
	});
	
	$jq("#daOldDetailsId tr:not(:first)").each(function() {
		if($jq(this).find("td").eq(2).find('input').val()!='' && $jq(this).find("td").eq(2).find('input').val()!=undefined) {
			stayDaOldAfterRestriction = stayDaNewAfterRestriction + $jq(this).find("td").eq(2).find('input').val() + '-'+$jq(this).find("td").eq(2).find('input').attr('id')+'@'
			stayDaOldAmount = parseFloat(stayDaNewAmount)+parseFloat($jq(this).find("td").eq(2).find('input').val());
		}
		if($jq(this).find("td").eq(6).find('input').val()!='' && $jq(this).find("td").eq(6).find('input').val()!=undefined) {
			amountPerDayOldDaAftRes = amountPerDayOldDaAftRes + $jq(this).find("td").eq(6).find('input').val() + '-'+$jq(this).find("td").eq(6).find('input').attr('id')+'@'
			amountPerDay = parseFloat(amountPerDay)+parseFloat($jq(this).find("td").eq(6).find('input').val());
		}if($jq(this).find("td").eq(6).find('input').val()=='') {
			status=false;
		}
		
		var value1=parseFloat($jq(this).find("td").eq(6).find('input').val());
		if(isNaN(value1)){
			value1=0;
		}
		$jq(this).find("td").eq(8).find('input').val(value1);
		if($jq(this).find("td").eq(8).find('input').val()!='' && $jq(this).find("td").eq(8).find('input').val()!=undefined) {
			daOldAfterRestriction = daOldAfterRestriction + $jq(this).find("td").eq(8).find('input').val() + '-'+$jq(this).find("td").eq(8).find('input').attr('id')+'@'
			daOldAmount = parseFloat(daOldAmount)+parseFloat($jq(this).find("td").eq(8).find('input').val());
		}if($jq(this).find("td").eq(8).find('input').val()=='') {
			status=false;
		}
		$jq('#restrictionAmount').html(daOldAmount);
		$jq('#daOldAmount').val(daOldAfterRestriction);
		$jq('#stayDaOldAmount').val(stayDaOldAfterRestriction);
		$jq('#amountPerDay').val(amountPerDayOldDaAftRes);
		if(parseFloat(tadaAdvanceAmount) > 0) {
			$jq('#settleAmount').val(parseFloat(daOldAmount)-parseFloat(tadaAdvanceAmount));
		}else {
			$jq('#settleAmount').val(daOldAmount);
		}
		if(parseFloat(tadaAdvanceAmount)>0) {
			/**
			 * Settlement case
			 */
			$jq('#restrictionAmount').html(parseFloat(daOldAmount)-parseFloat(tadaAdvanceAmount));
		}
	});
	
	$jq("#RMADayDetailsId tr:not(:first)").each(function() {
		if($jq(this).find("td").eq(4).find('input').val()!='' && $jq(this).find("td").eq(4).find('input').val()!=undefined) {
			dayAmountAfterRestriction = dayAmountAfterRestriction + $jq(this).find("td").eq(4).find('input').val() + '-'+$jq(this).find("td").eq(4).find('input').attr('id')+'@'
			amountPerDayAftRes = parseFloat(amountPerDayAftRes)+parseFloat($jq(this).find("td").eq(4).find('input').val());
		}if($jq(this).find("td").eq(4).find('input').val()=='') {
			status=false;
		}
		var value1=parseFloat($jq(this).find("td").eq(4).find('input').val());
		if(isNaN(value1))
			value1=0;
		
		$jq(this).find("td").eq(6).find('input').val(value1);
		if($jq(this).find("td").eq(6).find('input').val()!='' && $jq(this).find("td").eq(6).find('input').val()!=undefined) {
			rmaDayAfterRestriction = rmaDayAfterRestriction + $jq(this).find("td").eq(6).find('input').val() + '-'+$jq(this).find("td").eq(6).find('input').attr('id')+'@'
			rmaDayAmount = parseFloat(rmaDayAmount)+parseFloat($jq(this).find("td").eq(6).find('input').val());
		}if($jq(this).find("td").eq(6).find('input').val()=='') {
			status=false;
		}
		$jq('#restrictionAmount').html(rmaDayAmount);
		$jq('#rmaDayAmount').val(rmaDayAfterRestriction);
		$jq('#amountPerDayAftRes').val(dayAmountAfterRestriction);
		if(parseFloat(tadaAdvanceAmount) > 0) {
			$jq('#settleAmount').val(parseFloat(rmaDayAmount)-parseFloat(tadaAdvanceAmount));
		}else {
			$jq('#settleAmount').val(rmaDayAmount);
		}
		if(parseFloat(tadaAdvanceAmount)>0) {
			/**
			 * Settlement case
			 */
			$jq('#restrictionAmount').html(parseFloat(rmaDayAmount)-parseFloat(tadaAdvanceAmount));
		}
	});
	
	$jq("#RMAKmDetailsId tr:not(:first)").each(function() {
		if($jq(this).find("td").eq(5).find('input').val()!='' && $jq(this).find("td").eq(5).find('input').val()!=undefined) {
			distanceAfterRestriction = distanceAfterRestriction + $jq(this).find("td").eq(5).find('input').val() + '-'+$jq(this).find("td").eq(5).find('input').attr('id')+'@'
			distanceAftRes = parseFloat(distanceAftRes)+parseFloat($jq(this).find("td").eq(5).find('input').val());
		}if($jq(this).find("td").eq(5).find('input').val()=='') {
			status=false;
		}
		if($jq(this).find("td").eq(7).find('input').val()!='' && $jq(this).find("td").eq(7).find('input').val()!=undefined) {
			kmAmountAfterRestriction = kmAmountAfterRestriction + $jq(this).find("td").eq(7).find('input').val() + '-'+$jq(this).find("td").eq(7).find('input').attr('id')+'@'
			amountPerKmAftRes = parseFloat(amountPerKmAftRes)+parseFloat($jq(this).find("td").eq(7).find('input').val());
		}if($jq(this).find("td").eq(7).find('input').val()=='') {
			status=false;
		}
		var value1=parseFloat($jq(this).find("td").eq(7).find('input').val());
		var value2=parseFloat($jq(this).find("td").eq(5).find('input').val());
		if(isNaN(value1))
			value1=0;
		if(isNaN(value2))
			value2=0;
		
		$jq(this).find("td").eq(9).find('input').val(value1*value2);
		if($jq(this).find("td").eq(9).find('input').val()!='' && $jq(this).find("td").eq(9).find('input').val()!=undefined) {
			rmaKmAfterRestriction = rmaKmAfterRestriction + $jq(this).find("td").eq(9).find('input').val() + '-'+$jq(this).find("td").eq(9).find('input').attr('id')+'@'
			rmaKmAmount = parseFloat(rmaKmAmount)+parseFloat($jq(this).find("td").eq(9).find('input').val());
		}if($jq(this).find("td").eq(9).find('input').val()=='') {
			status=false;
		}
		$jq('#restrictionAmount').html(rmaKmAmount);
		$jq('#rmaKmAmount').val(rmaKmAfterRestriction);
		$jq('#distanceAftRes').val(distanceAfterRestriction);
		$jq('#amountPerKmAftRes').val(kmAmountAfterRestriction);
		if(parseFloat(tadaAdvanceAmount) > 0) {
			$jq('#settleAmount').val(parseFloat(rmaKmAmount)-parseFloat(tadaAdvanceAmount));
		}else {
			$jq('#settleAmount').val(rmaKmAmount);
		}
		if(parseFloat(tadaAdvanceAmount)>0) {
			/**
			 * Settlement case
			 */
			$jq('#restrictionAmount').html(parseFloat(rmaKmAmount)-parseFloat(tadaAdvanceAmount));
		}
	});
	$jq("#journeyDetailsId tr:not(:first)").each(function() {
		if($jq(this).find("td").eq(11).find('input').val()!='' && $jq(this).find("td").eq(11).find('input').val()!=undefined) {
			journeyAfterRestriction = journeyAfterRestriction + $jq(this).find("td").eq(11).find('input').val() + '-'+$jq(this).find("td").eq(11).find('input').attr('id')+'@'
			var val1=parseFloat(rmaKmAmount);
			var val2=parseFloat(rmaDayAmount);
			var val3=parseFloat(daNewAmount);
			var val4=parseFloat(daOldAmount)
			if(isNaN(val1))
				val1=0;
			if(isNaN(val2))
				val2=0;
			if(isNaN(val3))
				val3=0;
			if(isNaN(val4))
				val4=0;
			journeyAmount = parseFloat(journeyAmount)+parseFloat($jq(this).find("td").eq(11).find('input').val())+val1+val2+val3+val4;
		}if($jq(this).find("td").eq(11).find('input').val()=='') {
			status=false;
		}
		$jq('#restrictionAmount').html(journeyAmount);
		$jq('#journeyAmount').val(journeyAfterRestriction);
		if(parseFloat(tadaAdvanceAmount) > 0) {
			$jq('#settleAmount').val(parseFloat(journeyAmount)-parseFloat(tadaAdvanceAmount));
		}else {
			$jq('#settleAmount').val(journeyAmount);
		}
		if(parseFloat(tadaAdvanceAmount)>0) {
			/**
			 * Settlement case
			 */
			$jq('#restrictionAmount').html(parseFloat(journeyAmount)-parseFloat(tadaAdvanceAmount));
		}
	});
	if(parseFloat($jq('#restrictionAmount').text().trim()) < 0) {
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
		var remark=Math.abs($jq('#excessAmount').val())+parseFloat($jq('#excessAmountFine').val());
		$jq('#remarks').val('Pay '+remark+' amount through MRO');
	}else{
		$jq('#remarks').val('');
	}
	return status;
}
function calculateTadaPenalFines(cdaAmount){
	if($jq('#penalInterestTabId tr:gt(1)').find('td').eq(0).find('input:radio').is(':visible')){
		$jq('#penalInterestReq10').attr('checked',true);
	}
	var i=0;
	$jq('#penalInterestTabId tr:eq(1)').each(function(){
		if($jq('input:radio[name=penalInterestReq'+i+']').is(':visible')){
			if($jq('input:radio[name=penalInterestReq'+i+']:checked').val()=='yes'){
				if($jq('#noOfDays'+i).val()!='' && $jq('#interestRate'+i).val()!=''){
					$jq('#excessAmountFine'+i).val(Math.round(Math.abs((parseFloat($jq('#excessAmount').val())*Math.round($jq('#noOfDays'+i).val())))/365*parseFloat($jq('#interestRate'+i).val())/100));
				$jq('#totMroAmount0').val(parseFloat($jq('#unUtilizedBal0').val())+parseFloat($jq('#excessAmountFine0').val()));
				}
				if($jq('#excessAmount').is(':visible') && $jq('#excessAmountFine'+i).val()!='') {
					var remark=Math.abs($jq('#excessAmount').val())+parseFloat($jq('#excessAmountFine'+i).val());
					$jq('#remarks').val('Pay '+remark+' amount to MRO');
				}else{
					$jq('#remarks').val('');
				}
			} else if($jq('input:radio[name=penalInterestReq'+i+']:checked').val()=='no'){
				if($jq('#excessAmount').is(':visible')) {
					var remark=Math.abs($jq('#excessAmount').val());
					$jq('#remarks').val('Pay '+remark+' amount to MRO');
				}else{
					$jq('#remarks').val('');
				}
			}
		}else{
			if(($jq('#noOfDays'+i).val()!='' && $jq('#noOfDays'+i).val()!=undefined) && ($jq('#interestRate'+i).val()!='' && $jq('#interestRate'+i).val()!=undefined)){
				$jq('#excessAmountFine'+i).val(Math.round(Math.abs((parseFloat($jq('#excessAmount').val())*Math.round($jq('#noOfDays'+i).val())))/365*parseFloat($jq('#interestRate'+i).val())/100));
			}
			if($jq('#excessAmount').is(':visible') && ($jq('#excessAmountFine'+i).val()!='' && $jq('#excessAmountFine'+i).val()!=undefined)) {
				var remark=Math.abs($jq('#excessAmount').val())+parseFloat($jq('#excessAmountFine'+i).val());
				$jq('#remarks').val('Pay '+remark+' amount to MRO');
			}else{
				$jq('#remarks').val('');
			}
		}
		
		i++;
	});
	i=1;
	$jq('#penalInterestTabId tr:gt(1)').each(function(){
		if(($jq('#noOfDays'+i).val()!='' && $jq('#noOfDays'+i).val()!=undefined) && ($jq('#interestRate'+i).val()!='' && $jq('#interestRate'+i).val()!=undefined)){
			$jq('#excessAmountFine'+i).val(Math.round(Math.abs((parseFloat($jq('#excessAmount').val())*Math.round($jq('#noOfDays'+i).val())))/365*parseFloat($jq('#interestRate'+i).val())/100));
			$jq('#totMroAmount'+i).val(parseFloat($jq('#unUtilizedBal'+i).val())+parseFloat($jq('#excessAmountFine'+i).val()));
		}
		if($jq('#excessAmount').is(':visible') && ($jq('#excessAmountFine'+i).val()!='' && $jq('#excessAmountFine'+i).val()!=undefined)) {
			var remark=parseFloat($jq('#excessAmountFine'+i).val());
//			$jq('#remarks').val('Pay '+remark+' amount to MRO');
			
			$jq('#remarks').val('Pay '+$jq('#totMroAmount'+i).val()+' amount to MRO');  //This line has changed.

		}else{
			$jq('#remarks').val('');
		}
		i++;
	});
}
function saveCdaAmont(type) {
	var mainJSON = {};
	var status=true;
	var errorMessage='';
	if(type=='advance') {
//		for(var i=0;i<=$jq('#advance').find("tr").length;i++) {
//			if($jq('#adv'+i).is(':checked')) {
				if($jq('#sanctionNoAdv').val()=='') {
					errorMessage+='Please Enter Sanction No\n';
					$jq('#sanctionNoAdv').focus();
					status=false;
				}if($jq('#billNoAdv').val()=='') {
					errorMessage+='Please Enter Bill No\n';
					$jq('#billNoAdv').focus();
					status=false;
				}if($jq('#accOfficerAdv').val()=='select') {
					errorMessage+='Please Select Acc. Officer.\n';
					$jq('#accOfficerAdv'+i).focus();
					status=false;
				}
//				if($jq('#issueAuthority'+i).val()=='asl'){
//					if($jq('#cdaAmountAdv'+i).val()==''){
//						errorMessage+='Please Enter CDA Amount.\n';
//						$jq('#cdaAmountAdv'+i).focus();
//						status=false;
//					}
//				}
//			}
//		}
		
		for(var i=0,j=0;i<=$jq('#advance').find("tr").length;i++) {
			if($jq('#adv'+i).is(':checked')) {
				var subJSON={};
				subJSON['tadaAdvanceAmount']=$jq('#tadaAdvanceAmount'+i).val();
				subJSON['requestId']=$jq('#adv'+i).val();
//				subJSON['cdaAmount']=$jq('#cdaAmountAdv'+i).val();
				subJSON['sanctionNo']=$jq('#sanctionNoAdv').val();
				subJSON['billNo']=$jq('#billNoAdv').val();
//				if($jq('#issueAuthority').val()=='asl'){
//					subJSON['dvNo']="asl";
//					subJSON['dvDate']="01-Jan-1990";
//				}else{
//					subJSON['dvNo']=$jq('#dvNoAdv').val();
//					subJSON['dvDate']=$jq('#dvDateAdv'+i).val();
//				}
             	/*subJSON['cdaAmount']='';
				subJSON['dvNo']='';
				subJSON['dvDate']='';*/
				
				subJSON['accOfficer']=$jq('#accOfficerAdv').val();
				subJSON['issueAuthority']=$jq('#issueAuthority'+i).val();
				mainJSON[j]=subJSON;
				j++;
			}
		}
	}else if(type=='settlement') {
		/*for(var i=0;i<=$jq('#settlement').find("tr").length;i++) {
			if($jq('#sett'+i).is(':checked')) {
		*/		if($jq('#sanctionNoSett').val()=='') {
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
//			}
//		}
		for(var i=0,j=0;i<=$jq('#settlement').find("tr").length;i++) {
			if($jq('#sett'+i).is(':checked')) {
				var subJSON={};
				subJSON['tadaAdvanceAmount']=$jq('#tadaAdvanceAmount'+i).val();
				subJSON['requestId']=$jq('#sett'+i).val();
//				subJSON['cdaAmount']=$jq('#cdaAmountSett'+i).val();
				subJSON['sanctionNo']=$jq('#sanctionNoSett').val();
				subJSON['billNo']=$jq('#billNoSett').val();
//				subJSON['dvNo']=$jq('#dvNoSett'+i).val();
//				subJSON['dvDate']=$jq('#dvDateSett'+i).val();
				subJSON['accOfficer']=$jq('#accOfficerSett').val();
				mainJSON[j]=subJSON;
				j++;
			}
		}
		
	}else if(type=='reimbursement') {
		/*for(var i=0;i<=$jq('#reim').find("tr").length;i++) {
			if($jq('#reim'+i).is(':checked')) {*/
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
		/*		
			}
		}*/
		for(var i=0,j=0;i<=$jq('#reim').find("tr").length;i++) {
			if($jq('#reim'+i).is(':checked')) {
				var subJSON={};
				subJSON['tadaAdvanceAmount']=$jq('#tadaAdvanceAmount'+i).val();
				subJSON['requestId']=$jq('#reim'+i).val();
//				subJSON['cdaAmount']=$jq('#cdaAmountReim'+i).val();	
				subJSON['sanctionNo']=$jq('#sanctionNoReim').val();
				subJSON['billNo']=$jq('#billNoReim').val();
//				subJSON['dvNo']=$jq('#dvNoReim'+i).val();
//				subJSON['dvDate']=$jq('#dvDateReim'+i).val();
				subJSON['accOfficer']=$jq('#accOfficerReim').val();
				mainJSON[j]=subJSON;
				j++;
			}
		}
		
	}else if(type=='cdaSettlement') {
		for(var i=0;i<=$jq('#cdaSettlement').find("tr").length;i++) {
//			var name=$jq('#cdaSettlement').find('td').eq(3).text();
			if($jq('#cdaSettlement'+i).is(':checked')) {
		/*if($jq('#dvNoSett').val()=='') {
			errorMessage+='Enter DV Number\n';
			$jq('#dvNoSett').focus();
			status=false;
		}
		if($jq('#dvDateSett').val()=='') {
			errorMessage+='Enter DV  Date\n';
			$jq('#dvDateSett').focus();
			status=false;
		}*/
		if($jq('#cdaAmountSett'+i).val()=='') {
			errorMessage+='Please select CDA amount for  '+$jq('#employeeNameSett'+i).val()+'\n';
			$jq('#cdaAmountSett'+i).focus();
			status=false;
		}
			}
		}
		if($jq('#dvNoSett').val()=='') {
			errorMessage+='Please enter DV Number\n';
			$jq('#dvNoSett').focus();
			status=false;
		}
		if($jq('#dvDateSett').val()=='') {
			errorMessage+='Please enter DV  Date\n';
			$jq('#dvDateSett').focus();
			status=false;
		}
		
		for(var i=0,j=0;i<=$jq('#cdaSettlement').find("tr").length;i++) {
			if($jq('#cdaSettlement'+i).is(':checked')) {
				var subJSON={};
				subJSON['tadaAdvanceAmount']=$jq('#tadaAdvanceAmount'+i).val();
				subJSON['requestId']=$jq('#cdaSettlement'+i).val();
				subJSON['cdaAmount']=$jq('#cdaAmountSett'+i).val();
				subJSON['sanctionNo']=$jq('#sanctionNoSett'+i).val();
				subJSON['billNo']=$jq('#billNoSett'+i).val();
				subJSON['dvNo']=$jq('#dvNoSett').val();
				subJSON['dvDate']=$jq('#dvDateSett').val();
				subJSON['accOfficer']=$jq('#accOfficerSett'+i).val();
				mainJSON[j]=subJSON;
				j++;
			}
	}
	}	else if(type=='cdaAdvance') {
		for(var i=0;i<=$jq('#cdaAdv').find("tr").length;i++) {
			if($jq('#cdaAdv'+i).is(':checked')) {
		
		/*if($jq('#dvNoAdv').val()=='') {
			errorMessage+='Enter DV Number\n';
			$jq('#dvNoAdv').focus();
			status=false;
		}
		if($jq('#dvDateAdv').val()=='') {
			errorMessage+='Enter DV  Date\n';
			$jq('#dvDateAdv').focus();
			status=false;
		}*/
		if($jq('#cdaAmountAdv'+i).val()=='') {
			errorMessage+='Please select CDA amount for  '+$jq('#employeeNameAdv'+i).val()+'\n';
			$jq('#cdaAmountAdv'+i).focus();
			status=false;
		}
			}
		}
		
		/*if($jq('#dvNoAdv').val()=='') {
			errorMessage+='Please enter DV Number\n';
			$jq('#dvNoAdv').focus();
			status=false;
		}
		if($jq('#dvDateAdv').val()=='') {
			errorMessage+='Please enter DV  Date\n';
			$jq('#dvDateAdv').focus();
			status=false;
		}*/
		
for(var i=0,j=0;i<=$jq('#cdaAdv').find("tr").length;i++) {
	if($jq('#cdaAdv'+i).is(':checked')) {
		var subJSON={};
		subJSON['tadaAdvanceAmount']=$jq('#tadaAdvanceAmount'+i).val();
		subJSON['requestId']=$jq('#cdaAdv'+i).val();
		subJSON['sanctionNo']=$jq('#sanctionNoAdv'+i).val();
		subJSON['billNo']=$jq('#billNoAdv'+i).val();
		subJSON['accOfficer']=$jq('#accOfficerAdv'+i).val();
		if($jq('#issueAuthority'+i).val()=='asl'){
			subJSON['dvNo']="asl";
			subJSON['dvDate']="01-Jan-1990";
		}else{
			subJSON['dvNo']=$jq('#dvNoAdv').val();
			subJSON['dvDate']=$jq('#dvDateAdv').val();
		}
		/*subJSON['dvNo']=$jq('#dvNoAdv').val();
		subJSON['dvDate']=$jq('#dvDateAdv').val();*/
		subJSON['issueAuthority']=$jq('#issueAuthority'+i).val();  //This line added for  issue Authority
		subJSON['cdaAmount']=$jq('#cdaAmountAdv'+i).val();
		
		mainJSON[j]=subJSON;
		j++;
	}
}

}
	else if(type=='cdaReimbursement') {
		for(var i=0;i<=$jq('#cdaReim').find("tr").length;i++) {
			if($jq('#cdaReim'+i).is(':checked')) {
		
				/*if($jq('#dvNoReim').val()=='') {
					errorMessage+='Enter DV Number\n';
					$jq('#dvNoReim').focus();
					status=false;
				}
				if($jq('#dvDateReim').val()=='') {
					errorMessage+='Enter DV  Date\n';
					$jq('#dvDateReim').focus();
					status=false;
				}*/
				if($jq('#cdaAmountReim'+i).val()=='') {
					errorMessage+='Please select CDA amount for  '+$jq('#employeeNameReim'+i).val()+'\n';
					$jq('#cdaAmountReim').focus();
					status=false;
				}
			}
		}
			
		if($jq('#dvNoReim').val()=='') {
			errorMessage+='Please enter DV Number\n';
			$jq('#dvNoReim').focus();
			status=false;
		}
		if($jq('#dvDateReim').val()=='') {
			errorMessage+='Please enter DV  Date\n';
			$jq('#dvDateReim').focus();
			status=false;
		}
		
		for(var i=0,j=0;i<=$jq('#cdaReim').find("tr").length;i++) {
			if($jq('#cdaReim'+i).is(':checked')) {
				var subJSON={};
				subJSON['tadaAdvanceAmount']=$jq('#tadaAdvanceAmount'+i).val();
				subJSON['requestId']=$jq('#cdaReim'+i).val();
				subJSON['sanctionNo']=$jq('#sanctionNoReim'+i).val();
				subJSON['billNo']=$jq('#billNoReim'+i).val();
				subJSON['accOfficer']=$jq('#accOfficerReim'+i).val();
				subJSON['dvNo']=$jq('#dvNoReim').val();
				subJSON['dvDate']=$jq('#dvDateReim').val();
				subJSON['cdaAmount']=$jq('#cdaAmountReim'+i).val();	
				mainJSON[j]=subJSON;
				j++;
			}
		}
		
	}
	if(JSON.stringify(mainJSON).length==2) {
		errorMessage+='Please Check Atleast One Check Box \n';
		status = false;
	}
	if(status) {
		$jq.post('tadaApprovalRequest.htm?param=manage&type='+type+'&jsonValue='+JSON.stringify(mainJSON), function(html) {
			if(type=='advance') {
				$jq("#advanceList").html(html);
			}else if(type=='settlement') {
				$jq("#settlementList").html(html);
			}else if(type=='reimbursement') {
				$jq("#reimbursementList").html(html);
			}else if(type=='cdaReimbursement') {
				$jq("#cdaReimbursementList").html(html);
			}else if(type=='cdaAdvance') {
				$jq("#cdaAdvanceList").html(html);
			}else if(type=='cdaSettlement') {
				$jq("#cdasettlementList").html(html);
			}
			
    	  });
	}else {
		alert(errorMessage);
	}
}
function getTdInitialReport(requestId,requestType,draft) {
	if(requestType=='tdSettTourParticulars')
		window.open('./report.htm?param=initial&type='+requestType+'&requestID='+requestId+'&draft='+draft+'&category=settlement','TADA','fullscreen=no,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
	else if(requestType=='tdReimTourParticulars')
		window.open('./report.htm?param=initial&type='+requestType+'&requestID='+requestId+'&draft='+draft+'&category=reimbursement','TADA','fullscreen=no,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
	else
		window.open('./report.htm?param=initial&type='+requestType+'&requestID='+requestId+'&draft='+draft,'TADA','fullscreen=no,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
}
function getTdAdvFinReport(requestId,i){
	var requestType="";
	if($jq('#issueAuthority'+i).val()=='asl'){
		requestType = "tadaAdvanceFinLevel";
	} else{
		requestType = "tadaAdvanceCDALevel";
	}
	window.open('./report.htm?param=initial&type='+requestType+'&requestID='+requestId,'TADA','fullscreen=no,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no')
}
function getMROReport(requestId,id,requestType) {
	window.open('./report.htm?param=initial&type='+requestType+'&requestID='+requestId+'&mroId='+id,'TADA','fullscreen=no,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no')
}
function setAdvanceValue(){
	for(var i=0;i<tdRequestDetails.length;i++){
		if(tdRequestDetails[i].tadaAdvanceAmount!=0){
			$jq('#tadaAdvanceAmountId').append($jq('<input type=text name=tadaAdvanceAmount id=tadaAdvanceAmount value='+tdRequestDetails[i].tadaAdvanceAmount+'></input>')).css('display','block');
			$jq('#ticketFare').val(tdRequestDetails[i].ticketFare);
		} else {
			$jq('#tadaAdvanceAmountId').append($jq('<input type=text name=tadaAdvanceAmount readonly=readonly id=tadaAdvanceAmount></input>')).css('display','block');
			$jq('#tadaAdvanceAmount').val(parseFloat($jq('#totalAccAmt').val())+parseFloat($jq('#totalFoodAmt').val()));
		}
	}
	var j=0;
	if(tadaRequestBean.param=='amendment'){
		$jq('#requestJourneyDetailsId tr:not(:first)').each(function(){
			for(var i=j;i<tdReqJourneyDetails.length;i++){
				$jq(this).find('td').eq(7).find('input').val(Math.round(parseFloat(tdReqJourneyDetails[i].ticketFare)));
				break;
			}
			j++;
		});
	}	
}
function calculateTdAdvance(){
	var tadaPercentage=parseFloat(tadaDaNewDetails.daOnTourPercentage)/100;                // This  variable has been created
	for(var i=0;i<tdRequestDetails.length;i++){
		$jq('#noOfDaysAcc').val(tdRequestDetails[i].stayDuration);
		$jq('#noOfDaysFood').val(tdRequestDetails[i].stayDuration);
		if(tdRequestDetails[i].daType=='hotelrate'){
			
			$jq('#accAmtPerDay').val((tadaDaNewDetails.accommodationBill)+(tadaDaNewDetails.accommodationBill*tadaPercentage));          // tadaDaNewDetailsHere we increarse da percentage 0.25 to 0.50
			$jq('#foodAmtPerDay').val((tadaDaNewDetails.foodBill)+(tadaDaNewDetails.foodBill*tadaPercentage));                            //Here we increarse da percentage 0.25 to 0.50
		}else if(tdRequestDetails[i].daType=='normalrate'){
			$jq('#accAmtPerDay').val(0);
			$jq('#foodAmtPerDay').val((tadaDaNewDetails.foodBill)+(tadaDaNewDetails.foodBill*tadaPercentage));                        //Here we increarse da percentage 0.25 to 0.50
		}else if(tdRequestDetails[i].daType=='na'){
			$jq('#accAmtPerDay').val(0);
			$jq('#foodAmtPerDay').val(0);
		}
	}
	$jq('#totalAccAmt').val(Math.round(parseFloat($jq('#noOfDaysAcc').val())*parseFloat($jq('#accAmtPerDay').val())));
	$jq('#totalFoodAmt').val(Math.round(parseFloat($jq('#noOfDaysFood').val())*parseFloat($jq('#foodAmtPerDay').val())));
}
function setTotalTdAdvance(){
	//$jq('#tadaAdvanceAmount').val(parseFloat($jq('#totalAccAmt').val())+parseFloat($jq('#totalFoodAmt').val())+(parseFloat($jq('#ticketFare').val()))*2);
	//alert("hi krishna");
	var ticketAmount=0;
	$jq('#requestJourneyDetailsId tr:not(:first)').each(function(){
		if($jq(this).find('td').eq(7).find('input').val()!=''){
			ticketAmount=parseFloat($jq(this).find('td').eq(7).find('input').val())+parseFloat(ticketAmount);
		}
	});
	//this line commented by bkr 22/04/2016
	//$jq('#tadaAdvanceAmount').val(Math.round(parseFloat($jq('#totalAccAmt').val())+parseFloat($jq('#totalFoodAmt').val())+(parseFloat(ticketAmount))));
	//this line added by bkr 22/04/2016
	$jq('#tadaAdvanceAmount').val(Math.round(parseFloat($jq('#totalAccAmt').val())+parseFloat($jq('#totalFoodAmt').val())+parseFloat($jq('#taxi').val())+parseFloat($jq('#onTransit').val())+(parseFloat(ticketAmount))));
}
function selectDAType(){
	$jq('#tadaRequirementId').append($jq('<input id=tadaRequirement type=radio value=oldDA onchange="javascript:enableDA();" name=tadaRequirement><label>Old DA</label><input id=tadaRequirement type=radio value=newDA onchange="javascript:enableDA();totalAdjustment(0);" name=tadaRequirement><label>New DA</label>')).css('display','block');
	$jq("#tadaRequirement[value=" + tadaRequestBean.daType + "]").attr('checked', true);
	enableDA();
	$jq("#daOldRequirement[value=" + tadaRequestBean.daTypeRate + "]").attr('checked',true);
	//enableDaDetails();
}
function enableSettList(){
	for(var i=0;i<tadaFinSettlementList.length;i++){
		for(var j=0;j<tadaTdFinSettlementCmplList.length;j++){
			if(tadaFinSettlementList[i].requestId==tadaTdFinSettlementCmplList[j].requestId){
				$jq('#sanctionNoSett'+i+'').val(tadaTdFinSettlementCmplList[j].sanctionNo);
				$jq('#billNoSett'+i+'').val(tadaTdFinSettlementCmplList[j].billNo);
				$jq('#accOfficerSett'+i+'').val(tadaTdFinSettlementCmplList[j].accountentSign);
				$jq('#dvNoSett'+i+'').val(tadaTdFinSettlementCmplList[j].dvNo);
				$jq('#dvDateSett'+i+'').val(tadaTdFinSettlementCmplList[j].dvDate);
				if(tadaTdFinSettlementCmplList[j].dvDate!=0 && tadaTdFinSettlementCmplList[j].dvDate!=''){
					$jq('#cdaAmountSett'+i+'').val(tadaTdFinSettlementCmplList[j].cdaAmount);
				}else{
					$jq('#cdaAmountSett'+i+'').val('');
				}
				
			}
		}
	}
}
function enableAdvList(advance){
	var tableID1='advance';
	var dynRow1 = document.getElementById(tableID1);
	for(var i=0;i<tadaFinAdvList.length;i++){
		for(var j=0;j<tadaTdFinAdvCmplList.length;j++){
			if(tadaFinAdvList[i].requestId==tadaTdFinAdvCmplList[j].requestId){
				if(tadaTdFinAdvCmplList[j].dvNo=='asl'){
					$jq('#issueAuthority'+i+'').val('asl');
					showDvNoAndDate(i);
				} else {
					$jq('#issueAuthority'+i+'').val('cda');
					showDvNoAndDate(i);
				}
				$jq('#sanctionNoAdv'+i+'').val(tadaTdFinAdvCmplList[j].sanctionNo);
				$jq('#billNoAdv'+i+'').val(tadaTdFinAdvCmplList[j].billNo);
				$jq('#accOfficerAdv'+i+'').val(tadaTdFinAdvCmplList[j].accountentSign);
				$jq('#dvNoAdv'+i+'').val(tadaTdFinAdvCmplList[j].dvNo);
				$jq('#dvDateAdv'+i+'').val(tadaTdFinAdvCmplList[j].dvDate);
				if(tadaTdFinAdvCmplList[j].cdaAmount!=0 && tadaTdFinAdvCmplList[j].cdaAmount!=''){
					$jq('#cdaAmountAdv'+i+'').val(tadaTdFinAdvCmplList[j].cdaAmount);
				}else if($jq('#sanctionNoAdv'+i+'').val()==0 && $jq('#billNoAdv'+i+'').val()==0){
					$jq('#cdaAmountAdv'+i+'').val(tadaTdFinAdvCmplList[j].cdaAmount);
				}else{
					$jq('#cdaAmountAdv'+i+'').val('');
				}	
			}
		}
	}
	//setAdvListOrder();
}
var orderVal=1;
function setAdvListOrder(){
	
	$jq('#advance tr:gt('+orderVal+')').each(function(){
		if($jq(this).find('td').eq(7).find('input:text').val()=='' && $jq(this).find('td').eq(8).find('input:text').val()==''){
			$jq(this).insertBefore($jq(this).prev());
			orderVal++;
			setAdvListOrder();
		}
	});
}
function enableReimList(){
	for(var i=0;i<tadaFinReimList.length;i++){
		for(var j=0;j<tadaTdFinReimCmplList.length;j++){
			if(tadaFinReimList[i].requestId==tadaTdFinReimCmplList[j].requestId){
				$jq('#sanctionNoReim'+i+'').val(tadaTdFinReimCmplList[j].sanctionNo);
				$jq('#billNoReim'+i+'').val(tadaTdFinReimCmplList[j].billNo);
				$jq('#accOfficerReim'+i+'').val(tadaTdFinReimCmplList[j].accountentSign);
				$jq('#dvNoReim'+i+'').val(tadaTdFinReimCmplList[j].dvNo);
				$jq('#dvDateReim'+i+'').val(tadaTdFinReimCmplList[j].dvDate);
				if(tadaTdFinReimCmplList[j].cdaAmount!=0 && tadaTdFinReimCmplList[j].cdaAmount!=''){
					$jq('#cdaAmountReim'+i+'').val(tadaTdFinReimCmplList[j].cdaAmount);
				}else{
					$jq('#cdaAmountReim'+i+'').val('');
				}
			}
		}
	}
}
function enableGpfAcNo(){
	if(empDetailsJSON.empDetailsList.gpfAcNo=="" && empDetailsJSON.empDetailsList.ppaNo=="" && empDetailsJSON.empDetailsList.pranNo==""){
		$jq('#gpfAcNoId').append($jq('<div>GPF No. :'+'<input type=text id=gpfNo value=""/></div>')).css("display","block");
	} else if(empDetailsJSON.empDetailsList.gpfAcNo!="" && empDetailsJSON.empDetailsList.ppaNo!="" && empDetailsJSON.empDetailsList.pranNo!=""){
		$jq('#gpfAcNoId').append($jq('<div>GPF No. :'+''+empDetailsJSON.empDetailsList.gpfAcNo+'</div>')).css("display","block");
	} else if(empDetailsJSON.empDetailsList.gpfAcNo!="" && empDetailsJSON.empDetailsList.ppaNo=="" && empDetailsJSON.empDetailsList.pranNo==""){
		$jq('#gpfAcNoId').append($jq('<div>GPF No. :'+''+empDetailsJSON.empDetailsList.gpfAcNo+'</div>')).css("display","block");
	} else if(empDetailsJSON.empDetailsList.gpfAcNo=="" && empDetailsJSON.empDetailsList.ppaNo!="" && empDetailsJSON.empDetailsList.pranNo==""){
		$jq('#gpfAcNoId').append($jq('<div>PPA No. :'+''+empDetailsJSON.empDetailsList.ppaNo+'</div>')).css("display","block");
	} else if(empDetailsJSON.empDetailsList.gpfAcNo=="" && empDetailsJSON.empDetailsList.ppaNo=="" && empDetailsJSON.empDetailsList.pranNo!=""){
		$jq('#gpfAcNoId').append($jq('<div>PRAN No. :'+''+empDetailsJSON.empDetailsList.pranNo+'</div>')).css("display","block");
	} else if(empDetailsJSON.empDetailsList.gpfAcNo!="" && empDetailsJSON.empDetailsList.ppaNo!="" && empDetailsJSON.empDetailsList.pranNo==""){
		$jq('#gpfAcNoId').append($jq('<div>GPF No. :'+''+empDetailsJSON.empDetailsList.gpfAcNo+'</div>')).css("display","block");
	} else if(empDetailsJSON.empDetailsList.gpfAcNo=="" && empDetailsJSON.empDetailsList.ppaNo!="" && empDetailsJSON.empDetailsList.pranNo!=""){
		$jq('#gpfAcNoId').append($jq('<div>PPA No. :'+''+empDetailsJSON.empDetailsList.ppaNo+'</div>')).css("display","block");
	} else if(empDetailsJSON.empDetailsList.gpfAcNo!="" && empDetailsJSON.empDetailsList.ppaNo=="" && empDetailsJSON.empDetailsList.pranNo!=""){
		$jq('#gpfAcNoId').append($jq('<div>PRAN No. :'+''+empDetailsJSON.empDetailsList.pranNo+'</div>')).css("display","block");
	}
	setAmendmentValues();
}
function editEntitlement(gradePay,entitleTypeId){
	$jq('#gradePay').val(gradePay);
	enableEntitlement();
	$jq('#travelType').val(entitleTypeId);
	enableTravelTypeClass();
}
function deleteEntitlement(gradePay,entitleTypeId){
	$jq('#gradePay').val(gradePay);
	enableEntitlement();
	$jq('#travelType').val(entitleTypeId);
	enableTravelTypeClass();
	var selectedValues = "";
	var deSelectedValues = "";
	var status=true;
	if(confirm("Do You Want To Delete")){
		for ( var i = 0; i < document.getElementById('SelectRight').options.length; i++) {
			for(var s=0;s<entitleClassListJSON.length;s++){
				if(document.getElementById('SelectRight').options[i].value==entitleClassListJSON[s].id){
					$jq('#SelectLeft').append($jq('<option value='+entitleClassListJSON[s].id+'>'+entitleClassListJSON[s].entitleClass+'</option>'+''));
				}
			}
		}
		$jq('#SelectRight').find('option').remove().end();
		
		for ( var i = 0; i < document.getElementById('SelectRight').options.length; i++) {
			selectedValues += document.getElementById('SelectRight').options[i].value+ ",";
			status = true;
		}
		for ( var i = 0; i < document.getElementById('SelectLeft').options.length; i++) {
			deSelectedValues += document.getElementById('SelectLeft').options[i].value+ ",";
			status = true;
		}
		if (status) {
			var requestDetails = {
					"gradePay" : $jq('#gradePay').val(),
					"travelTypeId" : $jq('#travelType').val(),
					"entitleClass" : selectedValues,
					"nonEntitleClass" : deSelectedValues,
					"param" : "manageEntitlement"
			};
			
			$jq.ajax( {
				type : "POST",
				url : "tada.htm?"+dispUrl,
				data : requestDetails,
				cache : false,
				success : function(html) {
					$jq('#result').html(html);
					if ($jq('.success').is(':visible')) {
						clearEntitlement();
					}
				}
			});
		}
	}
}
function checkDepartureDate(){
	var sysdate = new Date();
	var journeyDate = $jq('#departureDate').val();
	var jDate = new Date(journeyDate.split("-")[2], getMonthID(journeyDate
			.split("-")[1])-1, journeyDate.split("-")[0], 0, 0, 1, 0)
	if(jDate<=sysdate){
		alert("Please select proper date");
		$jq('#departureDate').val('');
	}
}
function checkFloatTada(e,id){
	var key;
	var value = document.getElementById(id.id).value; 
	var str1 = value.split(".");
	if(window.event){
		key = window.event.keyCode;
	}
	else{
		key = e.which;
	}
	if(key==46){	
	  var check = true;
		for(j=0;j<str1.length;j++){
			if(check){
				check = false;
			}else{
				alert("Invalid character");
				document.getElementById(id.id).focus();
				return false;
			}
		}
	}	
	if( key!=8 && key!=0 && key!=46 && (key<48 || key>57)){
		alert("Please Enter Numbers Only");
		document.getElementById(id.id).focus();
		return false;
	}
	if(key==8 || key==0){
		document.getElementById(id.id).focus();
		return true;
	}else{
		document.getElementById(id.id).focus();
	    return true;
	}
}
function otherFromPlaces(){
	if($jq('#fromPlace').val()=='Other'){
		$jq('#fromPlaceId').show();
	}
	if($jq('#fromPlace').val()!='Other'){
		$jq('#fromPlaceId').hide();
	}
}
function otherToPlaces(){
	if($jq('#toPlace').val()=='Other'){
		$jq('#toPlaceId').show();
	}
	if($jq('#toPlace').val()!='Other'){
		$jq('#toPlaceId').hide();
	}
}
function submitlocalRMA(){
	var errorMessage = "";
	var status = true;
	var otherFromPlaceVal;
	var otherToPlaceVal;
	if ($jq('#fromPlace').val()=='Select') {
		errorMessage += "Please Select From Place.\n";
		$jq('#fromPlace').focus();
		status = false;
	}
	if ($jq('#toPlace').val()=='Select') {
		errorMessage += "Please Select To Place.\n";
		$jq('#toPlace').focus();
		status = false;
	}
	if($jq('#fromPlaceId').is(':visible')){
		if($jq('#otherFromPlace').val()==''){
			errorMessage += "Please Enter Other From Place.\n";
			$jq('#otherFromPlace').focus();
			status = false;
		}
	}
	if($jq('#toPlaceId').is(':visible')){
		if($jq('#otherToPlace').val()==''){
			errorMessage += "Please Enter Other To Place.\n";
			$jq('#otherToPlace').focus();
			status = false;
		}
	}
	if ($jq('#localrmadistance').val()=='') {
		errorMessage += "Please Enter Distance.\n";
		$jq('#localrmadistance').focus();
		status = false;
	}
	if (status) {
		var requestDetails = {
				"nodeID" : nodeID,
				"fromPlace" : $jq('#fromPlace').val(),
				"toPlace" : $jq('#toPlace').val(),
				"localrmadistance" : $jq('#localrmadistance').val(),
				"otherFromPlace" :$jq('#otherFromPlace').val(),
				"otherToPlace" :$jq('#otherToPlace').val(),
				"param" : "manageLocalRMA"
			};
			$jq.ajax( {
				type : "POST",
				url : "tada.htm?"+dispUrl,
				data : requestDetails,
				cache : false,
				success : function(html) {
					$jq('#result').html(html);
					if ($jq('.success').is(':visible')) {
						clearlocalRMA();
					}
				}
			});

	} else {
		alert(errorMessage);
	}

}

function clearlocalRMA(){
	$jq('#fromPlace').val('Select');
	$jq('#toPlace').val('Select');
	if($jq('#fromPlaceId').is(':visible')){
		$jq('#otherFromPlace').val('');
		$jq('#fromPlaceId').hide();
	}
	if($jq('#toPlaceId').is(':visible')){
		$jq('#otherToPlace').val('');
		$jq('#toPlaceId').hide();
	}
	$jq('#localrmadistance').val('');
// otherFromPlaces();
// otherToPlaces()
	
}
//start funtions of DaOnTourMaster
function submitDaOnTour(){
	var errorMessage = "";
	var status = true;
	if ($jq('#daRangeFrom').val()=='') {
		errorMessage += "Please Enter DA RangeFrom Percentage.\n";
		$jq('#daRangeFrom').focus();
		status = false;
	}
	if ($jq('#daRangeTo').val()=='') {
		errorMessage += "Please Enter DA RangeTo Percentage.\n";
		$jq('#daRangeTo').focus();
		status = false;
	}if ($jq('#daOnTour').val()=='') {
		errorMessage += "Please Enter DA On Tour Percentage.\n";
		$jq('#daOnTour').focus();
		status = false;
	}
	
	if (status) {
		var requestDetails = {
				"nodeID" : nodeID,
				"daRangeFrom" : $jq('#daRangeFrom').val(),
				"daRangeTo" : $jq('#daRangeTo').val(),
				"daOnTour" : $jq('#daOnTour').val(),
				"param" : "manageDaOnTour"
			};
			$jq.ajax( {
				type : "POST",
				url : "tada.htm?"+dispUrl,
				data : requestDetails,
				cache : false,
				success : function(html) {
					$jq('#result').html(html);
					if ($jq('.success').is(':visible')) {
						clearDaOnTour();
					}
				}
			});

	} else {
		alert(errorMessage);
	}

	
}
function clearDaOnTour(){  
	$jq('#daRangeFrom').val("");
	$jq('#daRangeTo').val("");
	$jq('#daOnTour').val("");
	$jq('#daRangeFrom').focus();	
}
function editDaOnTour(id,daRangeFrom,daRangeTo,daOnTour){
	nodeID=id;
	$jq('#daRangeFrom').val(daRangeFrom);
	$jq('#daRangeTo').val(daRangeTo);
	$jq('#daOnTour').val(daOnTour);
}
function deleteDaOnTour(id){
	var requestDetails = {
			"id" : id,
			"param" : "deleteDaOnTour"
		};
		$jq.ajax( {
			type : "POST",
			url : "tada.htm",
			data : requestDetails,
			cache : false,
			success : function(html) {
				$jq('#result').html(html);
				if ($jq('.success').is(':visible')) {
					clearDaOnTour();
				}
			}
		});
}
//end of DaOnTourMaster functions
function editLocalRMA(id,fromPlace,toPlace,distance){
	nodeID=id;
	$jq('#fromPlace').val(fromPlace);
	$jq('#toPlace').val(toPlace);
	$jq('#localrmadistance').val(distance);
	if($jq('#fromPlace').val()=='Select'){
		$jq('#fromPlace').val('Other');
		$jq('#fromPlaceId').show();
		$jq('#otherFromPlace').val(fromPlace);
	}
	if($jq('#toPlace').val()=='Select'){
		$jq('#toPlace').val('Other');
		$jq('#toPlaceId').show();
		$jq('#otherToPlace').val(toPlace);
	}
}
function deleteCLocalRMA(id){
	var requestDetails = {
			"id" : id,
			"param" : "deleteLocalRMA"
		};
		$jq.ajax( {
			type : "POST",
			url : "tada.htm",
			data : requestDetails,
			cache : false,
			success : function(html) {
				$jq('#result').html(html);
				if ($jq('.success').is(':visible')) {
					clearlocalRMA();
				}
			}
		});
}
function checkFromToLocalPlace1(countRMALocal){
	if($jq('#fromPlaceLocal'+countRMALocal+'').val()!='Select' && $jq('#toPlaceLocal'+countRMALocal+'').val()!='Select'){
		if($jq('#fromPlaceLocal'+countRMALocal+'').val()==$jq('#toPlaceLocal'+countRMALocal+'').val()){
			alert("From Place and To Place should not be same");
			$jq('#toPlaceLocal'+countRMALocal+'').val('Select')
		}
	}
}
function checkFromToLocalPlace(countRMALocal){
	$jq("#fromPlaceLocal"+countRMALocal+" :not(:first)").each(function() {
		$jq('#toPlaceLocal'+countRMALocal+'').find('option').remove();
	});
	if($jq('#fromPlaceLocal'+countRMALocal+'').val()=='ASL/DRDL' || $jq('#fromPlaceLocal'+countRMALocal+'').val()=='RCI/CPDC' || $jq('#fromPlaceLocal'+countRMALocal+'').val()=='Residence' || $jq('#fromPlaceLocal'+countRMALocal+'').val()=='Other'){
		$jq('#toPlaceLocal'+countRMALocal+'').append($jq('<option value="Select">Select</option>'
				                                          +'<option value="Begumpet Airport">Begumpet Airport</option>'
				                                          +'<option value="Secunderabad Railway Station">Secunderabad Railway Station</option>'
				                                          +'<option value="Hyderabad Railway Station">Hyderabad Railway Station</option>'
				                                          +'<option value="MGBS">MGBS</option>'
				                                          +'<option value="JBS">JBS</option>'
				                                          +'<option value="Kachiguda">Kachiguda</option>'
				                                          +'<option value="Shamshabad Airport">Shamshabad Airport</option>'
				                                          +'<option value="Other">Other</option>'));
	} else {
		$jq('#toPlaceLocal'+countRMALocal+'').append($jq('<option value=Select>Select</option>'
				+'<option value="ASL/DRDL">ASL/DRDL</option>'
                +'<option value="RCI/CPDC">RCI/CPDC</option>'
                +'<option value="Residence">Residence</option>'
                +'<option value="Other">Other</option>'));
	}
}
function checkLocalPlace(countRMALocal){
	if($jq('#fromPlaceLocal'+countRMALocal+'').val()=='Other'){
		$jq('#otherPlaceFrom'+countRMALocal+'').show();
		$jq('#residencePlaceFrom'+countRMALocal+'').hide();
	}
	if($jq('#fromPlaceLocal'+countRMALocal+'').val()=='Residence'){
		$jq('#residencePlaceFrom'+countRMALocal+'').show();
		$jq('#otherPlaceFrom'+countRMALocal+'').hide();
	}
	if($jq('#fromPlaceLocal'+countRMALocal+'').val()!='Other' && $jq('#fromPlaceLocal'+countRMALocal+'').val()!='Residence'){
		$jq('#residencePlaceFrom'+countRMALocal+'').hide();
		$jq('#otherPlaceFrom'+countRMALocal+'').hide();
	}
	if($jq('#toPlaceLocal'+countRMALocal+'').val()=='Other'){
		$jq('#otherPlaceTo'+countRMALocal+'').show();
		$jq('#residencePlaceTo'+countRMALocal+'').hide();
	}
	if($jq('#toPlaceLocal'+countRMALocal+'').val()=='Residence'){
		$jq('#residencePlaceTo'+countRMALocal+'').show();
		$jq('#otherPlaceTo'+countRMALocal+'').hide();
	}
	if($jq('#toPlaceLocal'+countRMALocal+'').val()!='Other' && $jq('#toPlaceLocal'+countRMALocal+'').val()!='Residence'){
		$jq('#residencePlaceTo'+countRMALocal+'').hide();
		$jq('#otherPlaceTo'+countRMALocal+'').hide();
	}
}
function checkLocalCmode(countRMALocal){
	$jq("#localConveyanceMode"+countRMALocal+" :not(:first)").each(function() {
		//$jq('#distanceLocal'+countRMALocal+'').val('');
		$jq('#amountPerKmLocalTd'+countRMALocal+'').val('');
		$jq('#totalAmountLocal'+countRMALocal+'').val('');
	});
	if($jq('#localConveyanceMode'+countRMALocal+'').val()=='Govt Tpt.'){
		$jq('#amountPerKmLocalTd'+countRMALocal+'').find('input').remove();
		$jq('#amountPerKmLocalTd'+countRMALocal+'').append($jq('<input type=text style=width:50px readonly=readonly value=0 id=amountPerKmLocal'+countRMALocal+' />'));
		$jq('#totalAmountLocal'+countRMALocal+'').val(0);
	} else {
		// $jq('amountPerKmLocal'+countRMALocal+'').val('');
		$jq('#amountPerKmLocalTd'+countRMALocal+'').find('input').remove();
		$jq('#amountPerKmLocalTd'+countRMALocal+'').append($jq('<input type=text style=width:50px id=amountPerKmLocal'+countRMALocal+' onchange=javascript:getRMALocalAmount('+countRMALocal+');totalAdjustment('+countRMALocal+'); />'));
	}
}
function getRMALocalAmount(countRMALocal){
	if(parseFloat($jq('#amountPerKmLocal'+countRMALocal+'').val())==0){
		$jq('#totalAmountLocal'+countRMALocal+'').val(parseFloat($jq('#distanceLocal'+countRMALocal+'').val())*parseFloat($jq('#amountPerKmLocal'+countRMALocal+'').val()));
	} else {
		$jq('#totalAmountLocal'+countRMALocal+'').val('');
	}
	if(!isNaN(parseFloat($jq('#distanceLocal'+countRMALocal+'').val())) && !isNaN(parseFloat($jq('#amountPerKmLocal'+countRMALocal+'').val()))){
		$jq('#totalAmountLocal'+countRMALocal+'').val(parseFloat($jq('#distanceLocal'+countRMALocal+'').val())*parseFloat($jq('#amountPerKmLocal'+countRMALocal+'').val()));
	}
}
function enableLocalDistance(countRMALocal){
	$jq("#toPlaceLocal"+countRMALocal+" :not(:first)").each(function() {
		$jq('#distanceLocalTd'+countRMALocal+'').find('input').remove();
		$jq('#distanceLocalTd'+countRMALocal+'').append($jq('<input type=text id=distanceLocal'+countRMALocal+' style=width:50px onkeypress="javascript:return checkFloatTada(event,distanceLocal'+countRMALocal+');" onchange="javascript:getRMALocalAmount('+countRMALocal+');totalAdjustment('+countRMALocal+')" />'));
	});
	for(var i=0;i<localRMAList.length;i++){
		if((localRMAList[i].fromPlace==$jq('#fromPlaceLocal'+countRMALocal+'').val() || localRMAList[i].toPlace==$jq('#fromPlaceLocal'+countRMALocal+'').val()) && (localRMAList[i].toPlace==$jq('#toPlaceLocal'+countRMALocal+'').val() || localRMAList[i].fromPlace==$jq('#toPlaceLocal'+countRMALocal+'').val())){
			$jq('#distanceLocalTd'+countRMALocal+'').find('input').remove();
			$jq('#distanceLocalTd'+countRMALocal+'').append($jq('<input type=text id=distanceLocal'+countRMALocal+' readonly=readonly value='+localRMAList[i].localrmadistance+' style=width:50px onkeypress="javascript:return checkFloatTada(event,distanceLocal'+countRMALocal+');" onchange="javascript:getRMALocalAmount('+countRMALocal+');totalAdjustment('+countRMALocal+')" />'));
		} 
// else {
// $jq('#distanceLocalTd'+countRMALocal+'').find('input').remove();
// $jq('#distanceLocalTd'+countRMALocal+'').append($jq('<input type=text
// id=distanceLocal'+countRMALocal+' style=width:50px
// onkeypress=javascript:return
// checkFloatTada(event,distanceLocal'+countRMALocal+');
// onchange=javascript:getRMALocalAmount('+countRMALocal+');totalAdjustment('+countRMALocal+')
// />'));
// }
	}
}
function clearAccAmt(countDaNewAcc){
	$jq('#accAmount'+countDaNewAcc+'').val('');
	$jq('#accAmountAftCal'+countDaNewAcc+'').val('');
	$jq('#totalAccAmount'+countDaNewAcc+'').val('');
}
function checkFromToAccTime(countDaNewAcc){
	var inTimeArr={};
	var outTimeArr={};
	var inTimeHrs;
	var outTimeHrs;
	var inTimeMins;
	var outTimeMins;
	
	inTimeArr=$jq('#startAccTime'+countDaNewAcc+'').val().split(":");
	outTimeArr=$jq('#endAccTime'+countDaNewAcc+'').val().split(":");
	if($jq('#fromDateAcc'+countDaNewAcc+'').val()==$jq('#toDateAcc'+countDaNewAcc+'').val()){
		if($jq('#endAccTime'+countDaNewAcc+'').val()!='' && $jq('#startAccTime'+countDaNewAcc+'').val()!=''){
			if(parseFloat(outTimeArr[0])<parseFloat(inTimeArr[0])){
				alert("Out Time Should be grater than In Time.\n");
				$jq('#endAccTime'+countDaNewAcc+'').val('');
			} else if((parseFloat(outTimeArr[0])=parseFloat(inTimeArr[0])) && (parseFloat(outTimeArr[1])<parseFloat(inTimeArr[1]))){
				alert("Out Time Should be grater than In Time.\n");
				$jq('#endAccTime'+countDaNewAcc+'').val('');
			}
		}
	}
}
function checkInitFromDate(countDaNewAcc){
	var daysCount=0;
	var dynRowDaNewAcc=document.getElementById("daNewAccDetailsId");
	var lengthDaNewAcc=dynRowDaNewAcc.rows.length;
	
	for(var j=1;j<lengthDaNewAcc;j++){
		for(var k=2;k<lengthDaNewAcc;k++){
			if(dynRowDaNewAcc.rows[j].cells[2].childNodes[1].value!='' && dynRowDaNewAcc.rows[k].cells[0].childNodes[0].value!=''){
				daysCount=parseFloat(compareDates(convertDate(dynRowDaNewAcc.rows[j].cells[2].childNodes[1].value),convertDate(dynRowDaNewAcc.rows[k].cells[0].childNodes[0].value)));
				if(daysCount==0){
					var inTime=dynRowDaNewAcc.rows[j].cells[3].childNodes[0].value;
					var outTime=dynRowDaNewAcc.rows[k].cells[1].childNodes[0].value;
					var inTimeArr=inTime.split(":");
					var outTimeArr=outTime.split(":");
					
					if(parseFloat(outTimeArr[0])<parseFloat(inTimeArr[0])){
						alert("In Time in row-"+(parseFloat(k)-3).toString()+" Should be grater than Out Time in row-"+(parseFloat(j)-3).toString()+"");
						$jq('#'+dynRowDaNewAcc.rows[k].cells[1].childNodes[0].id).val('');
					} else if(parseFloat(outTimeArr[0])==parseFloat(inTimeArr[0]) && parseFloat(outTimeArr[1])>parseFloat(inTimeArr[1])){
						alert("In Time in row-"+(parseFloat(k)-3).toString()+" Should be grater than Out Time in row-"+(parseFloat(j)-3).toString()+"");
						$jq('#'+dynRowDaNewAcc.rows[k].cells[1].childNodes[0].id).val('');
					}
					
				} else if(daysCount<0){
					alert("From Date in row-"+(parseFloat(k)-3).toString()+" Should be grater or equal to To Date in row-"+(parseFloat(j)-3).toString()+"");
					$jq('#'+dynRowDaNewAcc.rows[k].cells[0].childNodes[0].id).val('');
				}
			}
		}
	}
	
}
function timeDiff(inTime,outTime){
	var inTimeArr=inTime.split(":");
	var outTimeArr=outTime.split(":");
	
	if(parseFloat(outTimeArr[0])<parseFloat(inTimeArr[0])){
		return alert("Out Time Should be grater than In Time");
	} else if(parseFloat(outTimeArr[0])==parseFloat(inTimeArr[0]) && parseFloat(outTimeArr[1])>parseFloat(inTimeArr[1])){
		return alert("Out Time Should be grater than In Time");
	}
}
function checkFromToAccDate(countDaNewAcc){
	if($jq('#fromDateAcc'+countDaNewAcc+'').val()!=''){
		var daysCount=parseFloat(compareDates(convertDate($jq('#fromDateAcc'+countDaNewAcc+'').val()),convertDate($jq('#toDateAcc'+countDaNewAcc+'').val())));
		if(daysCount<0){
			alert("To Date Should be grater than From Date.");
			$jq('#toDateAcc'+countDaNewAcc+'').val('');
		}
	}else{
		alert("Please Select From Date Before Selecting To Date.");
		$jq('#toDateAcc'+countDaNewAcc+'').val('');
	}
}
function checkFromToFoodDate(countDaNewFood){
	if($jq('#fromDateFood'+countDaNewFood+'').val()!=''){
		var daysCount=parseFloat(compareDates(convertDate($jq('#fromDateFood'+countDaNewFood+'').val()),convertDate($jq('#toDateFood'+countDaNewFood+'').val())));
		if(daysCount<0){
			alert("To Date Should be grater or equal to From Date.");
			$jq('#toDateFood'+countDaNewFood+'').val('');
		}
	}else{
		alert("Please Select From Date Before Selecting To Date.");
		$jq('#toDateFood'+countDaNewFood+'').val('');
	}
}
function checkInitFromDateFood(countDaNewFood){
	var daysCount=parseFloat(compareDates(convertDate($jq('#toDateFood'+(parseFloat(countDaNewFood)-1).toString()+'').val()),convertDate($jq('#fromDateFood'+countDaNewFood+'').val())));
	if(daysCount<=0){
		alert("From Date in row-"+(parseFloat(countDaNewFood)+1).toString()+" Should be grater to To Date in row-"+(parseFloat(countDaNewFood)).toString()+"");
		$jq('#fromDateFood'+countDaNewFood+'').val('');
	}
}
function checkDaNewFoodRow(countDaNewFood){
	var errorMessages="";
	var status=true;
	if($jq('#fromDateFood'+countDaNewFood+'').val()=='' && $jq('#toDateFood'+countDaNewFood+'').val()=='' && $jq('#foodAmount'+countDaNewFood+'').val()==''){
		errorMessages += "Please Fill All The Details in row-"+(parseFloat(countDaNewFood)+1).toString()+".\n";
		$jq('#fromDateFood'+countDaNewFood+'').focus();
		status=false;
	} else{
		if($jq('#fromDateFood'+countDaNewFood+'').val()==''){
			errorMessages += "Please Select From Date.\n";
			$jq('#fromDateFood'+countDaNewFood+'').focus();
			status=false;
		}
		if($jq('#toDateFood'+countDaNewFood+'').val()==''){
			errorMessages += "Please Select To Date.\n";
			$jq('#toDateFood'+countDaNewFood+'').focus();
			status=false;
		}
		if($jq('#foodAmount'+countDaNewFood+'').val()==''){
			errorMessages += "Please Enter Amount for Food.\n";
			$jq('#foodAmount'+countDaNewFood+'').focus();
			status=false;
		}
	}
	if(status){
		funcreatenewNewDaFoodRow('daNewFoodDetailsId');
	} else {
		alert(errorMessages);
	}
}
function enableFoodAmtAftCal(countDaNewFood){
	var tadaDaPercentage=parseFloat($jq('#tadaDaPercentage').val())/100; //This is new line for tadadapercentage
	var daysCount=parseFloat(compareDates(convertDate($jq('#fromDateFood'+countDaNewFood+'').val()),convertDate($jq('#toDateFood'+countDaNewFood+'').val())))+1;
	var foodAmtVal=parseFloat($jq('#foodAmount'+countDaNewFood+'').val()) / daysCount;
	var foodAmt=parseFloat(foodAmtVal);
	for(var i=0;i<daNewDetailsList.length;i++){
		if(TadaRequestBean.tadaApprovalRequestDTO.gradePay==daNewDetailsList[i].gradePay){
			if(foodAmt>(daNewDetailsList[i].foodBill*0.5)){
				if(foodAmt>((daNewDetailsList[i].foodBill*tadaDaPercentage)+(daNewDetailsList[i].foodBill))){                //Here we increarse da percentage 0.25 to 0.50 to variable	
					$jq('#foodAmountAftCal'+countDaNewFood+'').val((((daNewDetailsList[i].foodBill*tadaDaPercentage)+(daNewDetailsList[i].foodBill))*daysCount));     //Here we increarse da percentage 0.25 to 0.50 to variable
					$jq('#totalFoodAmount'+countDaNewFood+'').val($jq('#foodAmount'+countDaNewFood+'').val());
				}else{
					$jq('#foodAmountAftCal'+countDaNewFood+'').val($jq('#foodAmount'+countDaNewFood+'').val());
					$jq('#totalFoodAmount'+countDaNewFood+'').val($jq('#foodAmount'+countDaNewFood+'').val());
				}
			} else {
				$jq('#foodAmountAftCal'+countDaNewFood+'').val($jq('#foodAmount'+countDaNewFood+'').val());
				$jq('#totalFoodAmount'+countDaNewFood+'').val($jq('#foodAmount'+countDaNewFood+'').val());
			}
		}
	}
}
function clearFoodAmt(countDaNewFood){
	$jq('#foodAmount'+countDaNewFood+'').val('');
	$jq('#foodAmountAftCal'+countDaNewFood+'').val('');
	$jq('#totalFoodAmount'+countDaNewFood+'').val('');
}
function enableRMADayAmtAftCal(countRMADay){
	var tadaDaPercentage=parseFloat($jq('#tadaDaPercentage').val())/100; //This is new line for tadadapercentage
	for(var i=0;i<daNewDetailsList.length;i++){
		if(TadaRequestBean.tadaApprovalRequestDTO.gradePay==daNewDetailsList[i].gradePay && daNewDetailsList[i].milageAllw=='perday'){
			if(parseFloat($jq('#amountPerDay'+countRMADay+'').val())>(daNewDetailsList[i].amount*0.5)){
				if(parseFloat($jq('#amountPerDay'+countRMADay+'').val())>((daNewDetailsList[i].amount*tadaDaPercentage)+(daNewDetailsList[i].amount))){                    //Here we increarse da percentage 0.25 to 0.50
					$jq('#amountPerDayAftCal'+countRMADay+'').val(((daNewDetailsList[i].amount*tadaDaPercentage)+(daNewDetailsList[i].amount)));                           //Here we increarse da percentage 0.25 to 0.50
				} else {
					$jq('#amountPerDayAftCal'+countRMADay+'').val($jq('#amountPerDay'+countRMADay+'').val());
				}
			} else {
				$jq('#amountPerDayAftCal'+countRMADay+'').val($jq('#amountPerDay'+countRMADay+'').val());
			}
			$jq('#totalAmount'+countRMADay+'').val($jq('#amountPerDay'+countRMADay+'').val());
		}
	}
}
function checkRMADayRow(countRMADay){
	var errorMessages="";
	var status=true;
	if($jq('#dateRMADay'+countRMADay+'').val()=='' && $jq('#fromPlaceRMADay'+countRMADay+'').val()=='' && $jq('#toPlaceRMADay'+countRMADay+'').val()=='' && $jq('#amountPerDay'+countRMADay+'').val()==''){
		errorMessages += "Please Fill All The Details in row-"+(parseFloat(countRMADay)+1).toString()+".\n";
		$jq('#dateRMADay'+countRMADay+'').focus()
		status=false;
	} else{
		if($jq('#dateRMADay'+countRMADay+'').val()==''){
			errorMessages += "Please Select Date.\n";
			$jq('#dateRMADay'+countRMADay+'').focus()
			status=false;
		}
		if($jq('#fromPlaceRMADay'+countRMADay+'').val()==''){
			errorMessages += "Please Enter From Place.\n";
			$jq('#fromPlaceRMADay'+countRMADay+'').focus();
			status=false;
		}
		if($jq('#toPlaceRMADay'+countRMADay+'').val()==''){
			errorMessages += "Please Enter To Place.\n";
			$jq('#toPlaceRMADay'+countRMADay+'').focus();
			status=false;
		}
		if($jq('#amountPerDay'+countRMADay+'').val()==''){
			errorMessages += "Please Enter RMA Amount.\n";
			$jq('#amountPerDay'+countRMADay+'').focus();
			status=false;
		}
	}
	if(status){
		funcreateNewRMADayRow('daNewRMADayId');
	} else {
		alert(errorMessages);
	}
}
function checkInitDateRMADay(countRMADay){
	var daysCount=parseFloat(compareDates(convertDate($jq('#dateRMADay'+(parseFloat(countRMADay)-1).toString()+'').val()),convertDate($jq('#dateRMADay'+countRMADay+'').val())));
	if(daysCount<=0){
		alert("Date in row-"+(parseFloat(countRMADay)+1).toString()+" Should be grater than Date in row-"+(parseFloat(countRMADay)).toString()+"");
		$jq('#dateRMADay'+countRMADay+'').val('');
	}
}
function checkDaNewAccRow(countDaNewAcc){
	var errorMessages="";
	var status=true;
	if($jq('#fromDateAcc'+countDaNewAcc+'').val()=='' && $jq('#startAccTime'+countDaNewAcc+'').val()=='' && $jq('#toDateAcc'+countDaNewAcc+'').val()=='' && $jq('#endAccTime'+countDaNewAcc+'').val()=='' && $jq('#accAmount'+countDaNewAcc+'').val()==''){
		errorMessages += "Please Fill All The Details in row-"+(parseFloat(countDaNewAcc)+1).toString()+".\n";
		$jq('#fromDateAcc'+countDaNewAcc+'').focus();
		status=false;
	} else{
		if($jq('#fromDateAcc'+countDaNewAcc+'').val()==''){
			errorMessages += "Please Select From Date.\n";
			$jq('#fromDateAcc'+countDaNewAcc+'').focus();
			status=false;
		}
		if($jq('#startAccTime'+countDaNewAcc+'').val()==''){
			errorMessages += "Please Select In Time.\n";
			$jq('#startAccTime'+countDaNewAcc+'').focus();
			status=false;
		}
		if($jq('#toDateAcc'+countDaNewAcc+'').val()==''){
			errorMessages += "Please Select To Date..\n";
			$jq('#toDateAcc'+countDaNewAcc+'').focus();
			status=false;
		}
		if($jq('#endAccTime'+countDaNewAcc+'').val()==''){
			errorMessages += "Please Select Out Time.\n";
			$jq('#endAccTime'+countDaNewAcc+'').focus();
			status=false;
		}
		if($jq('#accAmount'+countDaNewAcc+'').val()==''){
			errorMessages += "Please Enter Amount for Acc.\n";
			$jq('#accAmount'+countDaNewAcc+'').focus();
			status=false;
		}
	}
	if(status){
		funcreatenewNewDaAccRow('daNewAccDetailsId');
	} else {
		alert(errorMessages);
	}
}
function checkJourneyRow(countJourney){
	var errorMessages="";
	var status=true;
	for(var i=0;i<=countJourney;i++){
		if($jq('#journeyDate'+i+'').val()=='' && $jq('#startTime'+i+'').val()=='' && $jq('#startStation'+i+'').val()=='Select' && 
				$jq('#journeyEndDate'+i+'').val()=='' && $jq('#endTime'+i+'').val()=='' && $jq('#endStation'+i+'').val()=='Select' && 
				$jq('#modeOfTravel'+i+'').val()=='Select' && $jq('#ticketFare'+i+'').val()==''){
			errorMessages += "Please Fill All The Details in row-"+(parseFloat(i)+1).toString()+".\n";
			$jq('#journeyDate'+i+'').focus();
			status=false;
		} else{
			if($jq('#journeyDate'+i+'').val()==''){
				errorMessages += "Please Select Departure Date in row-"+(parseFloat(i)+1)+".\n";
				$jq('#journeyDate'+i+'').focus();
				status=false;
			}
			if($jq('#startTime'+i+'').val()==''){
				errorMessages += "Please Select Departure Time in row-"+(parseFloat(i)+1)+".\n";
				status=false;
			}
			if($jq('#startStation'+i+'').val()=='Select'){
				errorMessages += "Please Select Departure Station in row-"+(parseFloat(i)+1)+".\n";
				$jq('#startStation'+i+'').focus();
				status=false;
			}
			if($jq('#otherStartStation'+i+'').is(':visible') && $jq('#otherStartStation'+i+'').val()==''){
				errorMessages += "Please Enter Departure Station in row-"+(parseFloat(i)+1)+".\n";
				$jq('#otherStartStation'+i+'').focus();
				status=false;
			}
			if($jq('#journeyEndDate'+i+'').val()==''){
				errorMessages += "Please Select Arrival Date in row-"+(parseFloat(i)+1)+".\n";
				$jq('#journeyEndDate'+i+'').focus();
				status=false;
			}
			if($jq('#endTime'+i+'').val()==''){
				errorMessages += "Please Select Arrival Time in row-"+(parseFloat(i)+1)+".\n";
				status=false;
			}
			if($jq('#endStation'+i+'').val()=='Select'){
				errorMessages += "Please Select Arrival Station in row-"+(parseFloat(i)+1)+".\n";
				$jq('#endStation'+i+'').focus();
				status=false;
			}
			if($jq('#otherEndStation'+i+'').is(':visible') && $jq('#otherEndStation'+i+'').val()==''){
				errorMessages += "Please Enter Arrival Station in row-"+(parseFloat(i)+1)+".\n";
				$jq('#otherEndStation'+i+'').focus();
				status=false;
			}
			/*if($jq('#distanceJourney'+i+'').val()==''){
				errorMessages += "Please Enter Distance in k.m. in row-"+(parseFloat(i)+1)+"\n";
				$jq('#distanceJourney'+i+'').focus();
				status=false;
			}*/
			if($jq('#modeOfTravel'+i+'').val()=='Select'){
				errorMessages += "Please Select Mode Of Travelin row-"+(parseFloat(i)+1)+" .\n";
				$jq('#modeOfTravel'+i+'').focus();
				status=false;
			}
			if($jq('#ticketFare'+i+'').val()==''){
				errorMessages += "Please Enter Ticket Fare in RS. in row-"+(parseFloat(i)+1)+".\n";
				$jq('#ticketFare'+i+'').focus();
				status=false;
			}
			/*if($jq('#totalJourneyAmount'+i+'').val()==''){
				errorMessages += "Please Enter Ticket Cancel Fare in RS. in row-"+(parseFloat(i)+1)+".\n";
				$jq('#totalJourneyAmount'+i+'').focus();
				status=false;
			}
			if($jq('#ticketNo'+i+'').val()==''){
				errorMessages += "Please Enter Ticket No. in row-"+(parseFloat(i)+1)+".\n";
				$jq('#ticketNo'+i+'').focus();
				status=false;
			}*/
		}
	}
	if(status){
		funcreatenewJourneyRow('journeyDetailsId',countJourney);
	} else {
		alert(errorMessages);
	}
}
function checkJourneyDate(id){
	var countDays=parseFloat(compareDates(convertDate(TadaRequestBean.tadaApprovalRequestDTO.departureDateOne),convertDate($jq('#'+id+'').val())));
	var countDays1=parseFloat(compareDates(convertDate($jq('#'+id+'').val()),convertDate(TadaRequestBean.strArrivalDate)));
	if(countDays<0 || countDays1<0){
		if(id.split("Date")[0]=='journey'){
			alert("Departure Date Should be in between "+TadaRequestBean.tadaApprovalRequestDTO.departureDateOne+" and "+TadaRequestBean.strArrivalDate+"");
			$jq('#'+id).val('');
		}
	}
	if(countDays<0 || countDays1<0){
		if(id.split("Acc")[0]=='fromDate' || id.split("Food")[0]=='fromDate' || id.split("Date")[0]=='start'){
			alert("From Date Should be in between "+TadaRequestBean.tadaApprovalRequestDTO.departureDateOne+" and "+TadaRequestBean.strArrivalDate+"");
			$jq('#'+id).val('');
		} else if(id.split("Day")[0]=='dateRMA' || id.split("Local")[0]=='dateRMA' || id.split("Km")[0]=='dateRMA'){
			alert("Date Should be in between "+TadaRequestBean.tadaApprovalRequestDTO.departureDateOne+" and "+TadaRequestBean.strArrivalDate+"");
			$jq('#'+id).val('');
		} 
	}
}
function checkFoodDate(count){
	var dayArry=new Array();
	var amountArry=new Array();
	var amount=0;
	var calAmount=0;
	var dynRowDaNewFood=document.getElementById("daNewFoodDetailsId");
	var lengthDaNewFood=parseFloat(dynRowDaNewFood.rows.length)-1;
	for(var i=1;i<lengthDaNewFood;i++){
		// var a=dynRowDaNewFood.rows[i].cells[0].childNodes[1].value;
		// var b=$jq('#fromDateFood'+count+'').val();
		// var c=dynRowDaNewFood.rows[i].cells[1].childNodes[1].value;
		if(i==1){
			var countDays1=parseFloat(compareDates(convertDate($jq('#fromDateFood'+count+'').val()),convertDate(dynRowDaNewFood.rows[i].cells[0].childNodes[1].value)));
			var countDays2=parseFloat(compareDates(convertDate($jq('#fromDateFood'+count+'').val()),convertDate(dynRowDaNewFood.rows[i].cells[1].childNodes[1].value)));
			var countDays3=parseFloat(compareDates(convertDate($jq('#toDateFood'+count+'').val()),convertDate(dynRowDaNewFood.rows[i].cells[1].childNodes[1].value)));
		} else if(parseFloat(i)-1!=parseFloat(count)){
			var countDays1=parseFloat(compareDates(convertDate($jq('#fromDateFood'+count+'').val()),convertDate(dynRowDaNewFood.rows[i].cells[0].childNodes[0].value)));
			var countDays2=parseFloat(compareDates(convertDate($jq('#fromDateFood'+count+'').val()),convertDate(dynRowDaNewFood.rows[i].cells[1].childNodes[0].value)));
			var countDays3=parseFloat(compareDates(convertDate($jq('#toDateFood'+count+'').val()),convertDate(dynRowDaNewFood.rows[i].cells[1].childNodes[0].value)));
		}
		if(countDays1<=0 && countDays2>=0 && countDays3>=0){
			$jq('#foodAmountAftCal'+count+'').hide();
			break;
		} else if((countDays1<=0 && countDays2>=0 && countDays3<0) || (countDays1>=0 && countDays2>=0 && countDays3<0)){
			if(i==1){
				$jq('#'+dynRowDaNewFood.rows[i].cells[3].childNodes[0].id+'').hide();
				$jq('#foodAmountAftCal'+count+'').show();
			} else {
				$jq('#'+dynRowDaNewFood.rows[i].cells[3].childNodes[0].id+'').hide();
				$jq('#foodAmountAftCal'+count+'').show();
			}
		} else {
			$jq('#foodAmountAftCal'+count+'').show();
		}
		
	}
	
}
function checkArrivalDate(journeyEndDate){
	$jq('#journeyDetailsId tr:gt(1)').each(function(){
		var diff=parseFloat(compareDates(convertDate($jq(this).find('td').eq(0).find('input').val()),convertDate($jq(this).find('td').eq(3).find('input').val())));
		if(diff<0){
			alert("Arrival Date Should be equal or after"+$jq(this).find('td').eq(0).find('input').val()+"");
			$jq(this).find('td').eq(3).find('input').val('');
			$jq(this).find('td').eq(3).find('input').focus();
		}
	});
}
function checkArrivalTime(){
	$jq('#journeyDetailsId tr:gt(1)').each(function(){
		var diff=parseFloat(compareDates(convertDate($jq(this).find('td').eq(0).find('input').val()),convertDate($jq(this).find('td').eq(3).find('input').val())));
		if(diff==0){
			var depTimeArr=$jq(this).find('td').eq(1).find('input').val().split(":");
			var arrTimeArr=$jq(this).find('td').eq(4).find('input').val().split(":");
			
			if(parseFloat(arrTimeArr[0])<parseFloat(depTimeArr[0])){
				alert("Arrival Time Should be grater than Departure Time");
				$jq(this).find('td').eq(4).find('input').val('');
			} else if(parseFloat(arrTimeArr[0])==parseFloat(depTimeArr[0]) && parseFloat(arrTimeArr[1])>parseFloat(depTimeArr[1])){
				alert("Arrival Time Should be grater than Departure Time");
				$jq(this).find('td').eq(4).find('input').val('');
			}
		}
	});
}
function checkArrivalStation(){
	$jq('#journeyDetailsId tr:gt(1)').each(function(){
		if($jq(this).find('td').eq(2).find('input').val().toUpperCase()==$jq(this).find('td').eq(5).find('input').val().toUpperCase()){
			alert("Departure Station and Arrival Station Should not be same.\n");
			$jq(this).find('td').eq(5).find('input').val('');
			$jq(this).find('td').eq(5).find('input').focus();
		}
	});
}
var MONTH_NAMES=new Array('January','February','March','April','May','June','July','August','September','October','November','December','Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec');
var DAY_NAMES=new Array('Sunday','Monday','Tuesday','Wednesday','Thursday','Friday','Saturday','Sun','Mon','Tue','Wed','Thu','Fri','Sat');
function LZ(x) {return(x<0||x>9?"":"0")+x}
function tadaFormatDate(date,format) {
	format=format+"";
	var result="";
	var i_format=0;
	var c="";
	var token="";
	var y=date.getYear()+"";
	var M=date.getMonth()+1;
	var d=date.getDate();
	var E=date.getDay();
	var H=date.getHours();
	var m=date.getMinutes();
	var s=date.getSeconds();
	var yyyy,yy,MMM,MM,dd,hh,h,mm,ss,ampm,HH,H,KK,K,kk,k;
	// Convert real date parts into formatted versions
	var value=new Object();
	if (y.length < 4) {y=""+(y-0+1900);}
	value["y"]=""+y;
	value["yyyy"]=y;
	value["yy"]=y.substring(2,4);
	value["M"]=M;
	value["MM"]=LZ(M);
	value["MMM"]=MONTH_NAMES[M-1];
	value["NNN"]=MONTH_NAMES[M+11];
	value["d"]=d;
	value["dd"]=LZ(d);
	value["E"]=DAY_NAMES[E+7];
	value["EE"]=DAY_NAMES[E];
	value["H"]=H;
	value["HH"]=LZ(H);
	if (H==0){value["h"]=12;}
	else if (H>12){value["h"]=H-12;}
	else {value["h"]=H;}
	value["hh"]=LZ(value["h"]);
	if (H>11){value["K"]=H-12;} else {value["K"]=H;}
	value["k"]=H+1;
	value["KK"]=LZ(value["K"]);
	value["kk"]=LZ(value["k"]);
	if (H > 11) { value["a"]="PM"; }
	else { value["a"]="AM"; }
	value["m"]=m;
	value["mm"]=LZ(m);
	value["s"]=s;
	value["ss"]=LZ(s);
	while (i_format < format.length) {
		c=format.charAt(i_format);
		token="";
		while ((format.charAt(i_format)==c) && (i_format < format.length)) {
			token += format.charAt(i_format++);
			}
		if (value[token] != null) { result=result + value[token]; }
		else { result=result + token; }
		}
	return result;
}
function checkInitDeptDate(id){
	var diff=parseFloat(compareDates(convertDate($jq('#'+id).val()),convertDate(TadaRequestBean.tadaApprovalRequestDTO.departureDateOne)));
	if(diff>0){
		alert("Departure Date Should be after "+TadaRequestBean.tadaApprovalRequestDTO.departureDateOne+"");
		$jq('#'+id).val('');
		$jq('#'+id).focus();
	}
}
function checkFoodAmount(){
	var fromDateArry=new Array();
	var toDateArry=new Array();
	$jq('#daNewFoodDetailsId tr:not(:first)').each(function(){
		fromDateArry.push($jq(this).find('td').eq(0).find('input').val());
		toDateArry.push($jq(this).find('td').eq(1).find('input').val())
	});
	var minVal=fromDateArry[0];
	var maxVal=toDateArry[0];
	for(var i=0;i<fromDateArry.length;i++){
		if(parseFloat(compareDates(convertDate(minVal),convertDate(fromDateArry[i])))<0){
			minVal=fromDateArry[i];
		}
	}
	for(var i=0;i<toDateArry.length;i++){
		if(parseFloat(compareDates(convertDate(maxVal),convertDate(toDateArry[i])))>0){
			maxVal=toDateArry[i];
		}
	}
	var diff=parseFloat(compareDates(convertDate(minVal),convertDate(maxVal)))+1;
	for(var i=0;i<diff;i++){
		$jq('#daNewFoodDetailsId tr:not(:first)').each(function(){
			var fromDate=$jq(this).find('td').eq(0).find('input').val();
			var toDate=$jq(this).find('td').eq(1).find('input').val();
			
		});
	}
}
function enableAccClaimAmount(){
	$jq('#daNewAccDetailsId tr:not(:first)').each(function(){
		$jq(this).find('td').eq(5).find('input').val($jq(this).find('td').eq(4).find('input').val());
	});
	
}
function checkAccDates(id){
	$jq('#daNewAccDetailsId tr:not(:first)').each(function(){
		if($jq(this).find('td').eq(0).find('input').id!=id){	
		}
	});
}
function enableAccAmtAftCal(countDaNewAcc){
	var errorMessage = "";
	var status=true;
	var accFromDate;
	var accToDate;
	var daysCount=0;
	var totAccAmount=0;
	var accBill=0;
	var leaveStartDate;
	var leaveEndDate;
	var tadaDaPercentage=parseFloat($jq('#tadaDaPercentage').val())/100;
	if(status){
		$jq('#daNewAccDetailsId tr:not(:first)').each(function(){
			if($jq(this).find('td').eq(5).find('input').val()!='' && $jq(this).find('td').eq(5).find('input').val()!=undefined){
				accFromDate = $jq(this).find('td').eq(0).find('input:text').val();
				accToDate = $jq(this).find('td').eq(2).find('input').val();
				daysCount = parseFloat(compareDates(convertDate(accFromDate),convertDate(accToDate)))+1;
				var finalDateJson=new Array();
				var finalDateJson1=new Array();
				for(var i=0;i<daysCount;i++){
					var dateJson={};
					dateJson['dateVal']=accFromDate;
					if(i==0){
						accFromDate=addDays(accFromDate,1);
						accFromDate=tadaFormatDate(accFromDate,'dd-NNN-yyyy');
					}else{
						accFromDate=addDays(accFromDate,1);
						accFromDate=tadaFormatDate(accFromDate,'dd-NNN-yyyy');
					}
					finalDateJson[i]=dateJson;
				}
				for(var j=0;j<WorkBeanMap.tdLeaveDetailsList.length;j++){
					var leaveDaysCount=0;
					leaveDaysCount= parseFloat(compareDates(convertDate(WorkBeanMap.tdLeaveDetailsList[j].strFromDate),convertDate(WorkBeanMap.tdLeaveDetailsList[j].strToDate)))+1
					leaveStartDate=WorkBeanMap.tdLeaveDetailsList[j].strFromDate;
					for(var k=0;k<leaveDaysCount;k++){
						var dateJson1={};
						dateJson1['dateVal']=leaveStartDate;
						if(WorkBeanMap.tdLeaveDetailsList[j].fromHalfDayFlag=='Y' && WorkBeanMap.tdLeaveDetailsList[j].toHalfDayFlag=='Y'){
							if(k==0){
								dateJson1['halfDayFlag']='Y';
							}else if(k==leaveDaysCount-1){
								dateJson1['halfDayFlag']='Y';
							}else{
								dateJson1['halfDayFlag']='N';
							}
						}else{
							if(WorkBeanMap.tdLeaveDetailsList[j].fromHalfDayFlag=='Y'){
								if(k==0){
									dateJson1['halfDayFlag']='Y';
								}else{
									dateJson1['halfDayFlag']='N';
								}
							}else if(WorkBeanMap.tdLeaveDetailsList[j].toHalfDayFlag=='Y'){
								if(k==leaveDaysCount-1){
									dateJson1['halfDayFlag']='Y';
								}else{
									dateJson1['halfDayFlag']='N';
								}
							}
						}
						if(k==0){
							leaveStartDate=addDays(leaveStartDate,1);
							leaveStartDate=tadaFormatDate(leaveStartDate,'dd-NNN-yyyy');
						}else{
							leaveStartDate=addDays(leaveStartDate,1);
							leaveStartDate=tadaFormatDate(leaveStartDate,'dd-NNN-yyyy');
						}
						
						finalDateJson1[k]=dateJson1;
					}
				}
				for(var i=0;i<daNewDetailsList.length;i++){
					if(WorkBeanMap.tadaTdAdvanceRequestDTO.gradePay==daNewDetailsList[i].gradePay){
						accBill=parseFloat($jq(this).find('td').eq(5).find('input').val()) / daysCount;
						for(var m=0;m<finalDateJson.length;m++){
							for(var n=0;n<finalDateJson1.length;n++){
								if(compareDates(convertDate(finalDateJson[m].dateVal),convertDate(finalDateJson1[n].dateVal))==0){
									if(finalDateJson1[n].halfDayFlag=='Y'){
										daysCount = daysCount-0.5;
									}else{
										daysCount = daysCount-1;
									}
								}
							}
						}
						//accBill=parseFloat($jq(this).find('td').eq(5).find('input').val()) / daysCount;
						if(accBill>daNewDetailsList[i].accommodationBill*tadaDaPercentage){
							if(accBill>(daNewDetailsList[i].accommodationBill*tadaDaPercentage)+(daNewDetailsList[i].accommodationBill)){                  //Here we increarse da percentage 0.25 to 0.50 to variable
								totAccAmount = totAccAmount + (((daNewDetailsList[i].accommodationBill*tadaDaPercentage)+(daNewDetailsList[i].accommodationBill))*(daysCount));                 //Here we increarse da percentage 0.25 to 0.50
							} else {
								totAccAmount = totAccAmount + (accBill*daysCount);
							}
						} else {
							totAccAmount = totAccAmount + (accBill*daysCount);
						}
						$jq(this).find('td').eq(7).find('input').val(totAccAmount);
					}
				}
				totAccAmount=0;
			}
		});
	} else {
		alert(errorMessage);
		$jq('#accAmount'+countDaNewAcc+'').val('');
	}
}
function submitProjectDirector(){
	var errorMessage="";
	var status=true;
	if ($jq('#programCode').val()=='select'){
		errorMessage += "Please Select Program Code.\n";
		status = false;
	}
	if ($jq('#projectName').val()==''){
		errorMessage += "Please Enter Project Name\n";
		status = false;
	}
	if ($jq('#projectDirector').val ()==''){
		errorMessage += "Please Enter Project Director SFID\n ";
		status = false;
	}
	if (status){
		var requestDetails = {
			"pk" : nodeID,
			"programCode" : $jq('#programCode').val(),
			"projectName" : $jq('#projectName').val(),
		    "projectDirector" : $jq('#projectDirector').val(),
		    "param" : "submitProjectDirector"
		    };
		$jq.post('tada.htm',requestDetails,function(html) {
			$jq("#result").html(html);
			if ($jq('.success').is(':visible')) {
				clearProjectDirector();
			}
		});
	}else{
		alert(errorMessage);
	}
}
function clearProjectDirector(){
	$jq('#programCode').val('select');
	$jq('#projectName').val('');
	$jq('#projectDirector').val('');
}
function editProjectDirector(id,projectName,projectDirector,programCode){
   nodeID = id;
   $jq('#projectName').val(projectName);
   $jq('#projectDirector').val(projectDirector);
   $jq('#programCode').val(programCode);
}
function deleteProjectDirector(id){
	alert(id);
	var check=confirm("Do u want delete ?")
	if(check){
		var requestDetails = {
				"param" :"deleteProjectDirector",
				"pk" : id
		};
		$jq.post('tada.htm'+'?'+dispUrl,requestDetails, function(html){
			$jq("#result").html(html);
			if ($jq('.delete').is(':visible')) {
				
			}
		});
	}
}

function enableFoodClaimedAmount(){
	
}
function enableRMAKmClaimedAmount(){
	$jq('#RMAKmDetailsId tr:not(:first)').each(function(){
		if($jq(this).find('td').eq(5).find('input').val()==''){
			alert("Please Enter Distance after restriction.\n");
			$jq(this).find('td').eq(5).find('input').focus()
		}
		if($jq(this).find('td').eq(5).find('input').val()!='' && $jq(this).find('td').eq(7).find('input').val()!=''){
			$jq(this).find('td').eq(9).find('input').val(parseFloat($jq(this).find('td').eq(5).find('input').val())*parseFloat($jq(this).find('td').eq(7).find('input').val()));
		} else if($jq(this).find('td').eq(5).find('input').val()=='' && $jq(this).find('td').eq(7).find('input').val()==''){
			$jq(this).find('td').eq(9).find('input').val('');
		}
	});
}
function enableRMADailyClaimedAmount(){
	$jq('#RMADailyDetailsId tr:not(:first)').each(function(){
		if($jq(this).find('td').eq(5).find('input').val()==''){
			alert("Please Enter Distance after restriction.\n");
			$jq(this).find('td').eq(5).find('input').focus()
		}
		if($jq(this).find('td').eq(5).find('input').val()!='' && $jq(this).find('td').eq(7).find('input').val()!=''){
			$jq(this).find('td').eq(9).find('input').val(parseFloat($jq(this).find('td').eq(5).find('input').val())*parseFloat($jq(this).find('td').eq(7).find('input').val()));
		} else if($jq(this).find('td').eq(5).find('input').val()=='' && $jq(this).find('td').eq(7).find('input').val()==''){
			$jq(this).find('td').eq(9).find('input').val('');
		}
	});
}
function enableRMALocalClaimedAmount(){
	$jq('#RMALocalDetailsId tr:not(:first)').each(function(){
		if($jq(this).find('td').eq(5).find('input').val()==''){
			alert("Please Enter Distance after restriction.\n");
			$jq(this).find('td').eq(5).find('input').focus()
		}
		if($jq(this).find('td').eq(5).find('input').val()!='' && $jq(this).find('td').eq(7).find('input').val()!=''){
			$jq(this).find('td').eq(9).find('input').val(parseFloat($jq(this).find('td').eq(5).find('input').val())*parseFloat($jq(this).find('td').eq(7).find('input').val()));
		} else if($jq(this).find('td').eq(5).find('input').val()=='' && $jq(this).find('td').eq(7).find('input').val()==''){
			$jq(this).find('td').eq(9).find('input').val('');
		}
	});
}
function enableJourneyClaimedAmount(){
	$jq('#journeyDetailsId tr:not(:first)').each(function(){
		if($jq(this).find('td').eq(9).find('input').val()!=''){
			$jq(this).find('td').eq(12).find('input').val($jq(this).find('td').eq(9).find('input').val());
		} else if($jq(this).find('td').eq(9).find('input').val()==''){
			$jq(this).find('td').eq(12).find('input').val('');
		}
	});
}
function getRepresentedTotalAmt(){
	var totAmount1=0;
	var totAmount2=0;
	var accTotal=0;
	$jq('#daNewFoodDayDetailsId tr:not(:first,:last)').each(function(){
		if($jq(this).find('td').eq(1).find('input:text').val()!='' && $jq(this).find('td').eq(1).find('input:text').is(':visible')){
			totAmount1=parseFloat($jq(this).find('td').eq(1).find('input:text').val())+parseFloat(totAmount1);
			//totAmount2=parseFloat($jq(this).find('td').eq(2).text())+parseFloat(totAmount2);
		}
	});
	totAmount1=roundNumber(totAmount1, 2);
	//totAmount2=roundNumber(totAmount2, 2);
	$jq('#daNewFoodDayDetailsId tr:last').each(function(){
		if($jq(this).find('td').eq(1).find('input:text').is(':visible')){
			$jq(this).find('td').eq(1).find('input:text').val(totAmount1);
			//$jq(this).find('td').eq(2).text(totAmount2);
		}
	});
	$jq('#daNewAccDayDetailsId tr:not(:first,:last):visible').each(function(){
		if($jq(this).find('td').eq(1).find('input:text').val()!='' && $jq(this).find('td').eq(1).find('input:text').is(':visible')){
			accTotal=parseFloat($jq(this).find('td').eq(1).find('input:text').val())+parseFloat(accTotal);
			//totAmount2=parseFloat($jq(this).find('td').eq(2).text())+parseFloat(totAmount2);
		}
	});
	accTotal=roundNumber(accTotal, 2);
	$jq('#daNewAccDayDetailsId tr:last').eq(1).find('input:text').val(accTotal);
	$jq('#totalAccAmount').val(accTotal);
	
	$jq('#daNewAccDayDetailsId tr:last').each(function(){
		if($jq(this).find('td').eq(1).find('input:text').is(':visible')){
			$jq(this).find('td').eq(1).find('input:text').val(accTotal);
			//$jq(this).find('td').eq(2).text(totAmount2);
		}
	});
	
	//$jq('#totalAccAmount').val(accTotal);
}
function showTotalFoodAmount(){
	var totAmount1=0;
	var totAmount2=0;
	$jq('#daNewFoodDayDetailsId tr:not(:first,:last)').each(function(){
		if($jq(this).find('td').eq(0).text()!='Total'){
			totAmount1=parseFloat($jq(this).find('td').eq(1).find('input:text').val())+parseFloat(totAmount1);
			//totAmount2=parseFloat($jq(this).find('td').eq(2).text())+parseFloat(totAmount2);
			
		}
	});
	totAmount1=roundNumber(totAmount1, 2);
	//totAmount2=roundNumber(totAmount2, 2);
	$jq('#totalFoodAmount').val(totAmount1);
	$jq('#totalFoodAmount').text(totAmount1);
	
	/*if($jq('input:radio[name=foodAmtRep]:checked').val()=='rep1'){
		$jq('#totalFoodAmount').val(totAmount1);
	} else {
		$jq('#totalFoodAmount').val(totAmount2);
	}*/
	//$jq('#foodDayRepID').hide();
}
function getTadaTdTotalAmount(){
	var totalCalculatedAmount=0;
	$jq('#journeyDetailsId tr:gt(1)').each(function(){
		if(($jq(this).find('td').eq(9).find('input').val()!=undefined && $jq(this).find('td').eq(9).find('input').val()!='') || 
				($jq(this).find('td').eq(12).find('input').val()!=undefined && $jq(this).find('td').eq(12).find('input').val()!='')){
			if(parseFloat($jq(this).find('td').eq(10).find('input').val())==0 && parseFloat($jq(this).find('td').eq(12).find('input').val())==0){
				totalCalculatedAmount = parseFloat($jq(this).find('td').eq(9).find('input').val())+parseFloat(totalCalculatedAmount);
			}else{
				if(!isNaN(parseFloat($jq(this).find('td').eq(12).find('input').val()))){
					totalCalculatedAmount = parseFloat($jq(this).find('td').eq(12).find('input').val())+parseFloat(totalCalculatedAmount);
				}
			}
		}
		
	});
	alert(totalCalculatedAmount);
	$jq('#daNewAccDetailsId tr:not(:first)').each(function(){
		if($jq(this).find('td').eq(7).find('input').val()!=undefined && $jq(this).find('td').eq(7).find('input').val()!=''){
			totalCalculatedAmount = parseFloat($jq(this).find('td').eq(7).find('input').val())+parseFloat(totalCalculatedAmount);
		}
	});
	if($jq('#totalFoodAmount').val()!=undefined && $jq('#totalFoodAmount').val()!=''){
		totalCalculatedAmount = parseFloat($jq('#totalFoodAmount').val())+parseFloat(totalCalculatedAmount);
	}
	alert(totalCalculatedAmount);
	$jq('#RMAKmDetailsId tr:not(:first)').each(function(){
		if($jq(this).find('td').eq(9).find('input').val()!=undefined && $jq(this).find('td').eq(9).find('input').val()!=''){
			totalCalculatedAmount = parseFloat($jq(this).find('td').eq(9).find('input').val())+parseFloat(totalCalculatedAmount);
		}
	});
	
	$jq('#RMADayDetailsId tr:not(:first)').each(function(){
		if($jq(this).find('td').eq(6).find('input').val()!=undefined && $jq(this).find('td').eq(6).find('input').val()!=''){
			totalCalculatedAmount = parseFloat($jq(this).find('td').eq(6).find('input').val())+parseFloat(totalCalculatedAmount);
		}
	});
	
	$jq('#RMADailyDetailsId tr:not(:first)').each(function(){
		if($jq(this).find('td').eq(9).find('input').val()!=undefined && $jq(this).find('td').eq(9).find('input').val()!=''){
			totalCalculatedAmount = parseFloat($jq(this).find('td').eq(9).find('input').val())+parseFloat(totalCalculatedAmount);
		}
	});
	
	$jq('#RMALocalDetailsId tr:not(:first)').each(function(){
		if($jq(this).find('td').eq(9).find('input').val()!=undefined && $jq(this).find('td').eq(9).find('input').val()!=''){
			totalCalculatedAmount = parseFloat($jq(this).find('td').eq(9).find('input').val())+parseFloat(totalCalculatedAmount);
		}
	});

	$jq('#totLocalRMAAmount').text();
	$jq('#daOldDetailsId tr:not(:first)').each(function(){
		if($jq(this).find('td').eq(3).find('input').val()!=undefined && $jq(this).find('td').eq(3).find('input').val()!=''){
			totalCalculatedAmount = parseFloat($jq(this).find('td').eq(3).find('input').val())+parseFloat(totalCalculatedAmount);
		}
		if($jq(this).find('td').eq(6).find('input').val()!=undefined && $jq(this).find('td').eq(6).find('input').val()!=''){
			totalCalculatedAmount = parseFloat($jq(this).find('td').eq(6).find('input').val())+parseFloat(totalCalculatedAmount);
		}
	});
	
	$jq('#totalTadaTdCalAmount').val(Math.round(totalCalculatedAmount));
	if(parseFloat(tadaAdvanceAmount)>Math.round(parseFloat(totalCalculatedAmount))){
		$jq('#penalInterestGrid').show();
	} else {
		$jq('#penalInterestGrid').hide();
	}
	if(!isNaN(parseFloat(tadaAdvanceAmount))){
		$jq('#excessAmount').val(Math.round(parseFloat(totalCalculatedAmount))-parseFloat(tadaAdvanceAmount));
		$jq('#unUtilizedBal0').val($jq('#excessAmount').val()*(-1));
		$jq('#totMroAmount0').val($jq('#excessAmount').val()*(-1));
	} else{
		$jq('#excessAmount').val(parseFloat(totalCalculatedAmount));
		$jq('#unUtilizedBal0').val($jq('#excessAmount').val()*(-1));
		$jq('#totMroAmount0').val($jq('#excessAmount').val()*(-1));
	}
}
function showPenalInterestDetails(){
	var i=0;
	$jq('#penalInterestTabId tr:not(:first)').each(function(){
		if($jq(this).find('td').eq(0).find('input:radio[name=penalInterestReq'+i+']:checked').val()=='no'){
			$jq(this).find('td').eq(1).find('input:text').val(0);
			$jq(this).find('td').eq(1).find('input:text').attr('readonly',true);
			//$jq(this).find('td').eq(2).find('input').hide();
			$jq(this).find('td').eq(4).find('input:text').val(0);
			$jq(this).find('td').eq(4).find('input:text').attr('readonly',true);
			$jq(this).find('td').eq(5).find('input:text').val(parseFloat($jq('#unUtilizedBal0').val())+parseFloat($jq('#excessAmountFine0').val()*(-1)));
		}else{
			$jq(this).find('td').eq(1).find('input:text').val('');
			$jq(this).find('td').eq(1).find('input:text').attr('readonly',false);
			//$jq(this).find('td').eq(2).find('input').show();
			$jq(this).find('td').eq(4).find('input:text').val('');
			$jq(this).find('td').eq(4).find('input:text').attr('readonly',false);
			//$jq(this).find('td').eq(5).find('input:text').val('');
		}
		i++;
	});
}
function checkRMAKmRow(){
	var errorMessage='';
	var status=true;
	$jq('#daNewRMAKmId tr:not(:first)').each(function(){
		if($jq(this).find('td').eq(0).find('input').val()=='' && $jq(this).find('td').eq(1).find('input').val()=='' && $jq(this).find('td').eq(2).find('input').val()=='' && $jq(this).find('td').eq(3).find('select').val()=='Select' && $jq(this).find('td').eq(4).find('input').val()=='' && $jq(this).find('td').eq(5).find('input').val()==''){
			errorMessage += "Please fill all details.\n";
			status=false;
		} else {
			if($jq(this).find('td').eq(0).find('input').val()==''){
				errorMessage += "Please Select date in RMA details.\n";
				status=false;
			}
			if($jq(this).find('td').eq(1).find('input').val()==''){
				errorMessage += "Please enter from place in RMA details.\n";
				status=false;
			}
			if($jq(this).find('td').eq(2).find('input').val()==''){
				errorMessage += "Please enter to place in RMA details.\n";
				status=false;
			}
			if($jq(this).find('td').eq(3).find('select').val()=='Select'){
				errorMessage += "Please select travel by in RMA details.\n";
				status=false;
			}
			if($jq(this).find('td').eq(4).find('input').val()==''){
				errorMessage += "Please enter distance in RMA details.\n";
				status=false;
			}
			if($jq(this).find('td').eq(5).find('input').val()==''){
				errorMessage += "Please enter amount per k.m in RMA details.\n";
				status=false;
			}
		}
	});
	if(status){
		funcreateNewRMAKmRow('daNewRMAKmId');
	}else {
		alert(errorMessage);
	}
}
function checkRMADailyRow(){
	var errorMessage='';
	var status=true;
	$jq('#daNewRMADailyId tr:not(:first)').each(function(){
		if($jq(this).find('td').eq(0).find('input').val()=='' && $jq(this).find('td').eq(1).find('input').val()=='' && $jq(this).find('td').eq(2).find('input').val()=='' && $jq(this).find('td').eq(3).find('select').val()=='Select' && $jq(this).find('td').eq(4).find('input').val()=='' && $jq(this).find('td').eq(5).find('input').val()==''){
			errorMessage += "Please fill all details.\n";
			status=false;
		} else {
			if($jq(this).find('td').eq(0).find('input').val()==''){
				errorMessage += "Please Select date in RMA details.\n";
				status=false;
			}
			if($jq(this).find('td').eq(1).find('input').val()==''){
				errorMessage += "Please enter from place in RMA details.\n";
				status=false;
			}
			if($jq(this).find('td').eq(2).find('input').val()==''){
				errorMessage += "Please enter to place in RMA details.\n";
				status=false;
			}
			if($jq(this).find('td').eq(3).find('select').val()=='Select'){
				errorMessage += "Please select travel by in RMA details.\n";
				status=false;
			}
			if($jq(this).find('td').eq(4).find('input').val()==''){
				errorMessage += "Please enter distance in RMA details.\n";
				status=false;
			}
			if($jq(this).find('td').eq(5).find('input').val()==''){
				errorMessage += "Please enter amount per k.m in RMA details.\n";
				status=false;
			}
		}
	});
	if(status){
		funcreateNewRMADailyRow('daNewRMADailyId');
	}else {
		alert(errorMessage);
	}
}
function checkRMALocalRow(){
	var errorMessage='';
	var status=true;
	$jq('#daNewRMALocalId tr:not(:first)').each(function(){
		if($jq(this).find('td').eq(0).find('input').val()=='' && $jq(this).find('td').eq(1).find('select').val()=='Select' && $jq(this).find('td').eq(4).find('select').val()=='Select' && $jq(this).find('td').eq(7).find('select').val()=='Select' && $jq(this).find('td').eq(8).find('input').val()=='' && $jq(this).find('td').eq(9).find('input').val()==''){
			errorMessage += "Please fill all details.\n";
			status=false;
		} else {
			if($jq(this).find('td').eq(0).find('input').val()==''){
				errorMessage += "Please Select date in Local RMA details.\n";
				status=false;
			}
			if($jq(this).find('td').eq(1).find('select').val()=='Select'){
				errorMessage += "Please select from place in Local RMA details.\n";
				status=false;
			}
			if($jq(this).find('td').eq(2).find('input').is(':visible')){
				if($jq(this).find('td').eq(2).find('input').val()==''){
					errorMessage += "Please enter from residence place in Local RMA details.\n";
					status=false;
				}
			}
			if($jq(this).find('td').eq(3).find('input').is(':visible')){
				if($jq(this).find('td').eq(3).find('input').val()==''){
					errorMessage += "Please enter from other place in Local RMA details.\n";
					status=false;
				}
			}
			if($jq(this).find('td').eq(4).find('select').val()=='Select'){
				errorMessage += "Please select to place in Local RMA details.\n";
				status=false;
			}
			if($jq(this).find('td').eq(5).find('input').is(':visible')){
				if($jq(this).find('td').eq(5).find('input').val()==''){
					errorMessage += "Please enter to residence place in Local RMA details.\n";
					status=false;
				}
			}
			if($jq(this).find('td').eq(6).find('input').is(':visible')){
				if($jq(this).find('td').eq(6).find('input').val()==''){
					errorMessage += "Please enter to other place in Local RMA details.\n";
					status=false;
				}
			}
			if($jq(this).find('td').eq(7).find('select').val()=='Select'){
				errorMessage += "Please select mode of conveyance in Local RMA details.\n";
				status=false;
			}
			if($jq(this).find('td').eq(8).find('input').val()==''){
				errorMessage += "Please enter distance in Local RMA details.\n";
				status=false;
			}
			if($jq(this).find('td').eq(9).find('input').val()==''){
				errorMessage += "Please enter amount per k.m in Local RMA details.\n";
				status=false;
			}
		}
	});
	if(status){
		funcreateNewRMALocalRow('daNewRMALocalId');
	}else {
		alert(errorMessage);
	}
}
function checkRequestJourneyRow(countJourney){
	var errorMessages="";
	var status=true;
	if($jq('#journeyDate'+countJourney+'').val()=='' && $jq('#fromPlace'+countJourney+'').val()=='Select' && $jq('#toPlace'+countJourney+'').val()=='Select'  && $jq('#modeOfTravel'+countJourney+'').val()=='Select' && $jq('#classOfEntitlement'+countJourney+'').val()=='Select'){
		errorMessages += "Please Fill All The Details in row-"+(parseFloat(countJourney)+1).toString()+".\n";
		$jq('#journeyDate'+countJourney+'').focus();
		status=false;
	} else{
		if($jq('#journeyDate'+countJourney+'').val()==''){
			errorMessages += "Please Select Departure Date in row-"+(countJourney+1)+".\n";
			$jq('#journeyDate'+countJourney+'').focus();
			status=false;
		}
		/*if($jq('#journeyArrDate'+countJourney+'').val()==''){   
			errorMessages += "Please Select Arrival Date.\n";
			$jq('#journeyDate'+countJourney+'').focus();
			status=false;
		}*/
		if($jq('#fromPlace'+countJourney+'').val()=='Select'){
			errorMessages += "Please Select From Place in row-"+(countJourney+1)+".\n";
			$jq('#fromPlace'+countJourney+'').focus();
			status=false;
		}
		if($jq('#otherFromPlace'+countJourney+'').is(':visible')){
			if($jq('#otherFromPlace'+countJourney+'').val()==''){
				errorMessages += "Please Enter From Place in row-"+(countJourney+1)+".\n";
				$jq('#otherFromPlace'+countJourney+'').focus();
				status=false;
			}
			
		}
		if($jq('#toPlace'+countJourney+'').val()=='Select'){
			errorMessages += "Please Select To Place in row-"+(countJourney+1)+".\n";
			$jq('#toPlace'+countJourney+'').focus();
			status=false;
		}
		if($jq('#otherToPlace'+countJourney+'').is(':visible')){
			if($jq('#otherToPlace'+countJourney+'').val()==''){
				errorMessages += "Please Enter To Place in row-"+(countJourney+1)+".\n";
				$jq('#otherToPlace'+countJourney+'').focus();
				status=false;
			}
			
		}
		/*if($jq('#noOfDays'+countJourney+'').val()==''){                    // no of days check implemented
			errorMessages += "Please Select at least one day in no of days row-"+(countJourney+1)+".\n";
			$jq('#noOfDays'+countJourney+'').focus();
			status=false;
		}else if($jq('#noOfDays'+countJourney+'').val()==0){
			errorMessages += "Please Select at least one day in no of days row-"+(countJourney+1)+".\n";
			$jq('#noOfDays'+countJourney+'').focus();
			status=false;
		}*/
		if($jq('#modeOfTravel'+countJourney+'').val()=='Select'){
			errorMessages += "Please Select Mode Of Conveyance in row-"+(countJourney+1)+".\n";
			$jq('#modeOfTravel'+countJourney+'').focus();
			status=false;
		}
		if($jq('#classOfEntitlement'+countJourney+'').val()=='Select'){
			errorMessages += "Please Select Class Of Entitlement in row-"+(countJourney+1)+".\n";
			$jq('#classOfEntitlement'+countJourney+'').focus();
			status=false;
		}
	}
	if(status){
		
		$jq('#add'+countJourney).attr('disabled','disabled');
		funcreatenewRequestJourneyRow('requestJourneyDetailsId');
		setRowFromPlace(countJourney);
		setToPlace(++countJourney);
		setSelectBoxWidth('fromPlace'+countJourney);
		enableConveyanceModes(countJourney);
		if($jq('input:radio[name=authorizedMove]:checked').val()=='2' || $jq('input:radio[name=authorizedMove]:checked').val()=='1'){
			if($jq('input:radio[name=entitlementExemption]:checked').val()=='1'){
				setCModes(countJourney,true);
			}
		}
		setReqDeptDate(countJourney);
	} else {
		alert(errorMessages);
	}
}
function funcreatenewRequestJourneyRow(idvalue)
{
	
	$jq.post('tadaApprovalRequest.htm?&param=serverHit',function(data){});
	countRequestJourney++;
	var toplaceId = "toPlace"+countRequestJourney;
	
	var row = "<tr id=requestJourneyRow"+countRequestJourney+">";
	row += "<td>";
		row += "<input type=text readonly=readonly style=height:16px;width:70px;font-size:9px;font-weight:bold; id=journeyDate"+countRequestJourney+" onfocus=javascript:Calendar.setup({inputField:'journeyDate"+countRequestJourney+"',ifFormat:'%d-%b-%Y',showsTime:false,button:'journeyDate"+countRequestJourney+"',step:1});  />";//onchange='javascript:checkReqDeptDate("+countRequestJourney+");enableLeave();changeArrivalDate("+countRequestJourney+");setStayOnDates("+countRequestJourney+");'
	row += "</td>";
	
	row += "<td>";
	    row += "<input type=text readonly=readonly style=height:16px;width:70px;font-size:9px;font-weight:bold; id=journeyArrDate"+countRequestJourney+" onfocus=javascript:Calendar.setup({inputField:'journeyArrDate"+countRequestJourney+"',ifFormat:'%d-%b-%Y',showsTime:false,button:'journeyArrDate"+countRequestJourney+"',step:1}); onchange='javascript:checkReqArrivalDate();enableLeave();setReqDeptDate("+(countRequestJourney+1)+");' />";
    row += "</td>";
	
	row += "<td>";
	    row += "<select id=fromPlace"+countRequestJourney+" disabled='disabled' style=width:90px onchange='javascript:setToPlace("+countRequestJourney+");showOtherFromPlace("+countRequestJourney+");setSelectBoxWidth("+toplaceId+");'><option value=Select>Select</option><option value=Other>Other</option>";
	    for(var i=0;i<cityList.length;i++){
		     row += "<option value="+cityList[i].cityName+">"+cityList[i].cityName+"</option>";
	    }
	    row+= "<option value=Other>Other</option></select><input type=text id=otherFromPlace"+countRequestJourney+" size=11px style=display:none />";
	row += "</td>";

	row += "<td>";
	    row += "<select id=toPlace"+countRequestJourney+" style=width:90px onchange=javascript:showOtherToPlace("+countRequestJourney+");setSelectBoxWidth(\'"+toplaceId+"\');setNextFromPlace("+countRequestJourney+"); ><option value=Select>Select</option></select>";
	    row += "<input type=text id=otherToPlace"+countRequestJourney+" size=11px style=display:none onchange=javascript:setNextFromPlace("+countRequestJourney+"); />";
	row += "</td>";
	
	row += "<td>";
        row += "<input type=text id=noOfDays"+countRequestJourney+" value=0 style=width:40px  maxlength=4 onkeypress='javascript:return checkInt(event);' onchange='javascript:setTotalStay();setReqDeptDate("+(countRequestJourney+1)+");' />";
    row += "</td>";
	
	row += "<td>";
	    row += "<select id=modeOfTravel"+countRequestJourney+" style=width:80px onchange=javascript:enableEntitleClasses("+countRequestJourney+");setTatkalQuota();><option value=Select>Select</option></select>";		
	row += "</td>";
	
	row += "<td>";
	    row += "<select id=classOfEntitlement"+countRequestJourney+" style=width:100px><option value=Select>Select</option></select>";			
    row += "</td>";
    
    row += "<td style=display:none>";
        row += "<select id=tatkalQuota"+countRequestJourney+" style=width:80px;><option value=na>N/A</option><option value=yes>Required</option>";			
    row += "</td>";
	
    row += "<td>";
		row += "<input type=button id=add"+countRequestJourney+"   value=+ onclick=javascript:checkRequestJourneyRow("+countRequestJourney+"); />";
	row += "</td>";

	row += "<td>";
		row += "<input type=button id=del"+countRequestJourney+"   value=- onclick=javascript:deleteRequestJourneyRow(this,\'"+idvalue+"\'); />";
	row += "</td>";
	
row += "</tr>";

$jq("#requestJourneyRow"+(countRequestJourney-1)).after(row);
}
function deleteRequestJourneyRow(node,tableID)
{
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
	
	if(length1=='1')
	{
		funcreatenewRequestJourneyRow(tableID);
	}
	setNextDeletePlace(countRequestJourney);
	setTotalStay();
	countRequestJourney--;
	$jq('#add'+countRequestJourney).removeAttr('disabled','disabled');    
	
}
function setToPlace(count){
	$jq("#toPlace"+count+" :not(:first)").each(function() {
		$jq(this).remove();
		$jq('#otherToPlace'+count+'').hide();
	});
	for(var i=0;i<cityList.length;i++){
		if($jq('#fromPlace'+count+'').val()!=cityList[i].cityName){
			$jq('#toPlace'+count+'').append($jq('<option value='+cityList[i].cityName+'>'+cityList[i].cityName+'</option>'));
		}
	}
	$jq('#toPlace'+count+'').append($jq('<option value=Other style=color:blue>Other</option>'));
}

function enableConveyanceModes(count){
	var conveyanceModeArray=new Array();
	var i=0;
	$jq('#requestJourneyDetailsId tr:not(:first)').each(function(){
		conveyanceModeArray[i]=$jq(this).find('td').eq(5).find('select').val();
		i++;
	});
	$jq("#modeOfTravel"+count+" :not(:first)").each(function() {
		$jq(this).remove();
	});
	if(travelTypeMapDetailsJSON!=null){
		for(var i=0;i<travelTypeMapDetailsJSON.length;i++){
			var gradePay=travelTypeMapDetailsJSON[i].id;
			var ttid=travelTypeMapDetailsJSON[i].travelTypeId;
			for( var s=0;s<travelTypeListJSON.length;s++){
				if(travelTypeListJSON[s].id==travelTypeMapDetailsJSON[i].travelTypeId){
					$jq('#modeOfTravel'+count+'').append($jq('<option value='+travelTypeMapDetailsJSON[i].travelTypeId+'>'+travelTypeListJSON[s].travelType+'</option>'));
					//$jq('#cMode').append($jq('<div class="quarter">'+'<input id='+travelTypeMapDetailsJSON[i].flag+' type="radio" value='+travelTypeMapDetailsJSON[i].travelTypeId+' onchange="javascript:enableEntitleClass1();" name="conveyanceMode"><label for='+travelTypeListJSON[s].id+'>'+travelTypeListJSON[s].travelType+'</label>'+''));
				    if($jq('#modeOfTravel'+(count+1)+'').is(':visible'))
				    	$jq('#modeOfTravel'+(count+1)+'').append($jq('<option value='+travelTypeMapDetailsJSON[i].travelTypeId+'>'+travelTypeListJSON[s].travelType+'</option>'));
				}
			}
		}
		for( var s=0;s<travelTypeListJSON.length;s++){
			if(entitleExemptionList!=null){
				for(var k=0;k<entitleExemptionList.length;k++){
					if(entitleExemptionList[k].sfID==empDetailsJSON.empDetailsList.userSfid){
						if(entitleExemptionList[k].entitleTypeId==travelTypeListJSON[s].id){
							$jq('#modeOfTravel'+count+'').append($jq('<option value='+travelTypeListJSON[s].id+'>'+travelTypeListJSON[s].travelType+'</option>'));
						}
					}
				}
			}
		}
	}
	for(var i=0;i<conveyanceModeArray.length;i++){
		$jq('#requestJourneyDetailsId').find('tr').eq(i+1).find('td').eq(5).find('select').val(conveyanceModeArray[i]);
	}
	enableEntitleClasses(count);
}
function enableEntitleClasses(count){
	var flag=true;
	var entitleClassArray=new Array();
	var j=0;
	$jq('#requestJourneyDetailsId tr:not(:first)').each(function(){
		entitleClassArray[j]=$jq(this).find('td').eq(6).find('select').val();
		j++;
	});
	$jq("#classOfEntitlement"+count+" :not(:first)").each(function() {
		$jq(this).remove();
	});
	if($jq('input:radio[name=entitlementExemption]:checked').is(':visible')){
		if($jq('input:radio[name=entitlementExemption]:checked').val()=='2'){
			for(var i=0;i<travelTypeMapDetailsJSON.length;i++){
				if($jq('#modeOfTravel'+count+'').val()==travelTypeMapDetailsJSON[i].travelTypeId){
					for(var j=0;j<entitleClassListJSON.length;j++){
						for(var k=0;k<taEntitleClassListJSON.length;k++){
							if(entitleClassListJSON[j].id==taEntitleClassListJSON[k].entitleClassId){
								if(travelTypeMapDetailsJSON[i].travelTypeId==entitleClassListJSON[j].entitleTypeId){
								    $jq('#classOfEntitlement'+count+'').append('<option value='+entitleClassListJSON[j].id+'>'+entitleClassListJSON[j].entitleClass+'</option>');
								}
							}
						}
					}
					flag=false;
				}
				if(flag){
					$jq("#classOfEntitlement"+count+" :not(:first)").each(function() {
						$jq(this).remove();
					});
					if($jq('#modeOfTravel'+count+'').val()!=travelTypeMapDetailsJSON[i].travelTypeId){
						if(i==travelTypeMapDetailsJSON.length-1){
							for(var j=0;j<entitleClassListJSON.length;j++){
								if(entitleClassListJSON[j].entitleTypeId==$jq('#modeOfTravel'+count+'').val()){
									$jq('#classOfEntitlement'+count+'').append('<option value='+entitleClassListJSON[j].id+'>'+entitleClassListJSON[j].entitleClass+'</option>');
								}
							}
						}
					}
				}
			}
		}else if($jq('input:radio[name=entitlementExemption]:checked').val()=='1'){
			$jq("#classOfEntitlement"+count+" :not(:first)").each(function() {
				$jq(this).remove();
			});
			for(var i=0;i<entitleClassListJSON.length;i++){
				if($jq('#modeOfTravel'+count).val()==entitleClassListJSON[i].entitleTypeId){
					$jq('#classOfEntitlement'+count).append('<option value='+entitleClassListJSON[i].id+'>'+entitleClassListJSON[i].entitleClass+'</option>');
				}
			}
		}
	}else{
		for(var i=0;i<travelTypeMapDetailsJSON.length;i++){
			if($jq('#modeOfTravel'+count+'').val()==travelTypeMapDetailsJSON[i].travelTypeId){
				for(var j=0;j<entitleClassListJSON.length;j++){
					for(var k=0;k<taEntitleClassListJSON.length;k++){
						if(entitleClassListJSON[j].id==taEntitleClassListJSON[k].entitleClassId){
							if(travelTypeMapDetailsJSON[i].travelTypeId==entitleClassListJSON[j].entitleTypeId){
							    $jq('#classOfEntitlement'+count+'').append('<option value='+entitleClassListJSON[j].id+'>'+entitleClassListJSON[j].entitleClass+'</option>');
							}
						}
					}
				}
				flag=false;
			}
			if(flag){
				$jq("#classOfEntitlement"+count+" :not(:first)").each(function() {
					$jq(this).remove();
				});
				if($jq('#modeOfTravel'+count+'').val()!=travelTypeMapDetailsJSON[i].travelTypeId){
					if(i==travelTypeMapDetailsJSON.length-1){
						for(var j=0;j<entitleClassListJSON.length;j++){
							if(entitleClassListJSON[j].entitleTypeId==$jq('#modeOfTravel'+count+'').val()){
								$jq('#classOfEntitlement'+count+'').append('<option value='+entitleClassListJSON[j].id+'>'+entitleClassListJSON[j].entitleClass+'</option>');
							}
						}
					}
				}
			}
		}
	}
	for(var i=0;i<entitleClassArray.length;i++){
		$jq('#requestJourneyDetailsId').find('tr').eq(i+1).find('td').eq(6).find('select').val(entitleClassArray[i]);
	}
}
function showOtherFromPlace(count){
	$jq("#fromPlace"+count+" :not(:first)").each(function() {
		$jq('#otherFromPlace'+count+'').hide();
	});
	if($jq('#fromPlace'+count+'').val()=='Other'){
		$jq('#otherFromPlace'+count+'').show();
		$jq('#otherFromPlace'+count+'').val('');
		$jq('#otherFromPlace'+count+'').focus();
	}
}
function showOtherToPlace(count){
	$jq("#toPlace"+count+" :not(:first)").each(function() {
		$jq('#otherToPlace'+count+'').hide();
	});
	if($jq('#toPlace'+count+'').val()=='Other'){
		$jq('#otherToPlace'+count+'').show();
		$jq('#otherToPlace'+count+'').val('');
		$jq('#otherToPlace'+count+'').focus();
	}
}
function showIssuedTdAdvAmt(){
	var issuedAdvamount=0;
	$jq('#reqJourneyDetailsId tr:not(:first)').each(function(){
		if($jq(this).find('td').eq(8).find('input').val()!='' && $jq(this).find('td').eq(8).find('input').val()!='.'){
			issuedAdvamount=parseFloat($jq(this).find('td').eq(8).find('input').val())+parseFloat(issuedAdvamount);
		}
	});
	//commented by bkr 26/04/2016
/*	$jq('#issuedAdvAmount').val(parseInt((parseFloat($jq('#totalAccAmt').val())+parseFloat($jq('#totalFoodAmt').val())+parseFloat(issuedAdvamount))/100)*100);
	$jq('#totalAdvAmount').val(parseInt((parseFloat($jq('#totalAccAmt').val())+parseFloat($jq('#totalFoodAmt').val())+parseFloat(issuedAdvamount))));
*/
	//modified by bkr 26/04/2016 
	
	$jq('#issuedAdvAmount').val(parseInt((parseFloat($jq('#totalAccAmt').val())+parseFloat($jq('#totalFoodAmt').val())+parseFloat($jq('#taxi').val())+parseFloat($jq('#onTransit').val())+parseFloat(issuedAdvamount))/100)*100);
	$jq('#totalAdvAmount').val(parseInt((parseFloat($jq('#totalAccAmt').val())+parseFloat($jq('#totalFoodAmt').val())+parseFloat($jq('#taxi').val())+parseFloat($jq('#onTransit').val())+parseFloat(issuedAdvamount))));

	
	
}
function showDvNoAndDate(count){
	if($jq('#issueAuthority'+count+'').val()=='asl'){
		$jq('#dvNoAdv'+count+'').hide();
		$jq('#dvDateAdv'+count+'').hide();
		$jq('#dvDateAdvImg'+count+'').hide();
	} else {
		$jq('#dvNoAdv'+count+'').show();
		$jq('#dvDateAdv'+count+'').show();
		$jq('#dvDateAdvImg'+count+'').show();
	}
}
function enableRMADayClaimAmt(){
	var tadaDaPercentage=parseFloat($jq('#tadaDaPercentage').val())/100;    //this is new line added for new da master
	$jq('#RMADayDetailsId tr:not(:first)').each(function(){
		if($jq(this).find('td').eq(4).find('input').val()!=''){
			for(var i=0;i<daNewDetailsList.length;i++){
				if(WorkBeanMap.tadaTdAdvanceRequestDTO.gradePay==daNewDetailsList[i].gradePay){
					if(parseFloat($jq(this).find('td').eq(4).find('input').val())>parseFloat(((daNewDetailsList[i].amount)+(daNewDetailsList[i].amount*tadaDaPercentage)))){                      //Here we increarse da percentage 0.25 to 0.50 to variable
						$jq(this).find('td').eq(6).find('input').val(parseFloat(((daNewDetailsList[i].amount)+(daNewDetailsList[i].amount*tadaDaPercentage))));                                   //Here we increarse da percentage 0.25 to 0.50 to variable
					}else{
						$jq(this).find('td').eq(6).find('input').val(parseFloat($jq(this).find('td').eq(4).find('input').val()));
					}
				}
			}
		}
	});
}
function checkStayDARow(){
	var errorMessage='';
	var status=true;
	var i=1;
	$jq('#daOldDetailsId tr:not(:first)').each(function(){
		if($jq(this).find('td').eq(0).find('input').val()=='' && 
				$jq(this).find('td').eq(1).find('input').val()=='' &&
				$jq(this).find('td').eq(2).find('select').val()=='Select' &&
				$jq(this).find('td').eq(4).find('input').val()=='' &&
				$jq(this).find('td').eq(5).find('input').val()=='' &&
				$jq(this).find('td').eq(6).find('select').val()=='Select'){
			errorMessage +="Please Fill All Details in row-"+i+" of Stay DA details.\n";
			status=false;
		} else {
			if($jq(this).find('td').eq(0).find('input').val()==''){
				errorMessage +="Please Select Departure Date in row-"+i+" of Stay DA details.\n";
				status=false;
			}
			if($jq(this).find('td').eq(1).find('input').val()==''){
				errorMessage +="Please Select Departure Time in row-"+i+" of Stay DA details.\n";
				status=false;
			}
			if($jq(this).find('td').eq(2).find('select').val()=='Select'){
				errorMessage +="Please Select Departure City in row-"+i+" of Stay DA details.\n";
				status=false;
			}
			if($jq(this).find('td').eq(4).find('input').val()==''){
				errorMessage +="Please Select Arrival Date in row-"+i+" of Stay DA details.\n";
				status=false;
			}
			if($jq(this).find('td').eq(5).find('input').val()==''){
				errorMessage +="Please Select Arrival Time in row-"+i+" of Stay DA details.\n";
				status=false;
			}
			if($jq(this).find('td').eq(6).find('select').val()=='Select'){
				errorMessage +="Please Select Arrival City in row-"+i+" of Stay DA details.\n";
				status=false;
			}
		}
		i++;
	});
	if(status){
		funcreateOldDaRow('daOldDetailsId');
	} else{
		alert(errorMessage);
	}
}
function setJdaValues(){
	var j=0;
	$jq('#daOldDetailsId tr:not(:first)').each(function(){
		for(var i=j;i<jdaDetailsList.length;i++){
			$jq(this).find('td').eq(3).find('input').val(Math.round(parseFloat(jdaDetailsList[i].jdaAmount*jdaDetailsList[i].jda)));
			$jq(this).find('td').eq(6).find('input').val(Math.round(parseFloat(jdaDetailsList[i].sdaAmount*jdaDetailsList[i].sda)));
			break;
		}
		j++;
	});
	
}
function showProjDirNames(){
	/*$jq("#modeOfTravel"+count+" :not(:first)").each(function() {
		$jq(this).remove();
	});*/
	if($jq('input:radio[name=authorizedMove]:checked').val()=='2'){
		$jq('#projDirNameDiv').show();
		if(travelTypeMapDetailsJSON.length!=travelTypeListJSON.length){
			$jq('#entitleExemptionDiv').show();
		}
	} else{
		$jq('#projDirName').val('Select');
		$jq('input:radio[name=entitlementExemption]:checked').attr('checked',false);
		$jq('#projDirNameDiv').hide();
		$jq('#entitleExemptionDiv').hide();
		var entitleClassArray=new Array();
		var j=0;
		$jq('#requestJourneyDetailsId tr:not(:first)').each(function(){
			entitleClassArray[j]=$jq(this).find('td').eq(6).find('select').val();
			j++;
		});
		var i=0;
		$jq('#requestJourneyDetailsId tr:not(:first)').each(function(){
			$jq(this).find('td').eq(6).find('select').find('option').remove();
			$jq(this).find('td').eq(6).find('select').append('<option value=Select>Select</option>');
			enableConveyanceModes(i);
			i++;
		});
		for(var i=0;i<entitleClassArray.length;i++){
			$jq('#requestJourneyDetailsId').find('tr').eq(i+1).find('td').eq(6).find('select').val(entitleClassArray[i]);
		}
	}
	if($jq('input:radio[name=authorizedMove]:checked').val()=='1'){
		if(travelTypeMapDetailsJSON.length!=travelTypeListJSON.length){
			$jq('#entitleExemptionDiv').show();
			if(tadaRequestBean.entitlementExemption=='yes'){
				$jq('input:radio[id=entitlementExemption1]').attr('checked',true);
			}else if(tadaRequestBean.entitlementExemption=='no'){
				$jq('input:radio[id=entitlementExemption2]').attr('checked',true);
			}
		}
	}
}
function checkJourneyTable(){
	var errorMessage='';
	var status=true;
	var i=1;
	$jq('#journeyDetailsId tr:gt(1)').each(function(){
		if($jq(this).find('td').eq(0).find('input').val()=='' && 
				$jq(this).find('td').eq(1).find('input').val()=='' &&
				$jq(this).find('td').eq(2).find('select').val()=='Select' &&
				$jq(this).find('td').eq(3).find('input').val()=='' &&
				$jq(this).find('td').eq(4).find('input').val()=='' &&
				$jq(this).find('td').eq(5).find('select').val()=='Select' &&
				$jq(this).find('td').eq(6).find('input').val()=='' &&
				$jq(this).find('td').eq(7).find('select').val()=='Select' &&
				$jq(this).find('td').eq(8).find('input').val()=='' &&
				$jq(this).find('td').eq(9).find('input').val()=='' &&
				$jq(this).find('td').eq(10).find('input').val()==''){
			errorMessage += "Please fill details of row-"+i+" in journey details.\n";
			status=false;
		}else {
			if($jq(this).find('td').eq(0).find('input').val()==''){
				errorMessage += "Please select departure date in row-"+i+" of journey details.\n";
				status=false;
			}
			if($jq(this).find('td').eq(1).find('input').val()==''){
				errorMessage += "Please select departure time in row-"+i+" of journey details.\n";
				status=false;
			}
			if($jq(this).find('td').eq(2).find('select').val()=='Select'){
				errorMessage += "Please select departure place in row-"+i+" of journey details.\n";
				status=false;
			}
			if($jq(this).find('td').eq(2).find('select').val()=='Other'){
				if($jq(this).find('td').eq(2).find('input').val()==''){
					errorMessage += "Please enter departure place in row-"+i+" of journey details.\n";
					status=false;
				}
			}
			if($jq(this).find('td').eq(3).find('input').val()==''){
				errorMessage += "Please select arrival date in row-"+i+" of journey details.\n";
				status=false;
			}
			if($jq(this).find('td').eq(4).find('input').val()==''){
				errorMessage += "Please select arrival time in row-"+i+" of journey details.\n";
				status=false;
			}
			if($jq(this).find('td').eq(5).find('select').val()=='Select'){
				errorMessage += "Please select arrival place in row-"+i+" of journey details.\n";
				status=false;
			}
			if($jq(this).find('td').eq(5).find('select').val()=='Other'){
				if($jq(this).find('td').eq(5).find('input').val()==''){
					errorMessage += "Please enter arrival place in row-"+i+" of journey details.\n";
					status=false;
				}
			}
			if($jq(this).find('td').eq(6).find('input').val()==''){
				errorMessage += "Please enter distance in row-"+i+" of journey details.\n";
				status=false;
			}
			if($jq(this).find('td').eq(7).find('input').val()==''){
				errorMessage += "Please select mode of travel in row-"+i+" of journey details.\n";
				status=false;
			}
			if($jq(this).find('td').eq(8).find('input').val()==''){
				errorMessage += "Please enter ticket fare in row-"+i+" of journey details.\n";
				status=false;
			}
			if($jq(this).find('td').eq(9).find('input').val()==''){
				errorMessage += "Please enter ticket cancel amount in row-"+i+" of journey details.\n";
				status=false;
			}
			if($jq(this).find('td').eq(10).find('input').val()==''){
				errorMessage += "Please enter ticket no. in row-"+i+" of journey details.\n";
				status=false;
			}
		}
		i++;
	});
	if(status){
		checkCancellationTotal();
	}else{
		alert(errorMessage);
		$jq('input:radio[name=tadaRequirement]:checked').attr('checked',false);
		enableDA();
	}
}
function setRowFromPlace(count){
	if($jq('#otherToPlace'+count).is(':visible')){
		$jq('#fromPlace'+(count+1)+'').val('Other');
		$jq('#otherFromPlace'+(count+1)+'').show();
		$jq('#otherFromPlace'+(count+1)+'').val($jq('#otherToPlace'+count+'').val());
		$jq('#otherFromPlace'+(count+1)+'').attr('readonly',true);
	}else{
		$jq('#fromPlace'+(count+1)+'').val($jq('#toPlace'+count+'').val());
	}
}
function showTdHyperLinks(){
}
function cancelTadaTdRequest(requestId,requestType,historyID){
	var requestDetails = {
			"requestId" : requestId,
			"requestType" : requestType,
			"historyID" : historyID,
			"param" : "cancelTadaTdRequest"
		};
		$jq.ajax( {
			type : "POST",
			url : "tadaApprovalRequest.htm?"+dispUrl,
			data : requestDetails,
			cache : false,
			success : function(html) {
				$jq('#result').html(html);
				if ($jq('.success').is(':visible')) {
					
				}
			}
		});
}
function checkStayDays(){
	if(parseFloat($jq('#stayDuration').val())>180){
		alert("Stay Duration should not exceed 180 days.");
		$jq('#stayDuration').val('');
		$jq('#stayDuration').focus();
	}
}
function setSelectBoxWidth(idVal){
	if($jq('#'+idVal).val()!='Other'){
		$jq('#'+idVal).css("width","145px");
	}
}
function setSelectBoxWidth1(idVal){
	if($jq('#'+idVal).val()!='Other'){
		$jq('#'+idVal).css("width","120px");
	}
}
function showLocalRMAGrid(){
	if($jq('#rmaId').is(':checked')==true){
		$jq('#daNewRMALocalId').show();
	} else {
		$jq('#daNewRMALocalId').hide();
	}
}
function showDayRMAGrid(){
	if($jq('#rmaDayId').is(':checked')==true){
		$jq('#daNewRMADayId').show();
	} else {
		$jq('#daNewRMADayId').hide();
	}
}
function showKmRMAGrid(){
	if($jq('#rmaKmId').is(':checked')==true){
		$jq('#daNewRMAKmId').show();
	} else {
		$jq('#daNewRMAKmId').hide();
	}
}
function showDailyRMAGrid(){
	if($jq('#rmaDailyId').is(':checked')==true){
		$jq('#daNewRMADailyId').show();
	} else {
		$jq('#daNewRMADailyId').hide();
	}
}
function showRequestDetails(){
	if($jq('input:radio[name=amendmentType]:checked').val()=='amendment'){
		$jq('#requestDetailsDiv').show();
		$jq('#journeyDetailsDiv').show();
		$jq('#remarksDiv').show();
		$jq('#ticketCancellationDiv').show();
		setTdLeaveDetailsList();
		var count=0;
		$jq('#requestJourneyDetailsId tr:not(:first)').each(function(){
			setCModes(count,true);
			count++;
		});
		for(var i=0;i<tadaRequestBean.tadaTdReqJourneyList.length;i++){
			var j=0;
			$jq('#requestJourneyDetailsId tr:not(:first)').each(function(){
				for(var k=0;k<travelTypeListJSON.length;k++){
					if(i==j){
						if(tadaRequestBean.tadaTdReqJourneyList[i].conveyanceMode==travelTypeListJSON[k].travelType){
							$jq(this).find('td').eq(5).find('select').val(travelTypeListJSON[k].id);
							enableEntitleClasses(j);
						}
					}
				}
				j++;
			});
		}
		for(var i=0;i<tadaRequestBean.tadaTdReqJourneyList.length;i++){
			var j=0;
			$jq('#requestJourneyDetailsId tr:not(:first)').each(function(){
				for(var k=0;k<entitleClassListJSON.length;k++){
					if(i==j){
						if(tadaRequestBean.tadaTdReqJourneyList[i].classOfEntitlement==entitleClassListJSON[k].entitleClass){
							$jq(this).find('td').eq(6).find('select').val(entitleClassListJSON[k].id);
						}
					}
				}
				j++;
			});
		}
		$jq('#reasonText').text('');
		$jq('#reasonText').append('Reason for TD Amendment<span class=mandatory>*</span>');
	}else{
		var count=0;
		$jq('#requestJourneyDetailsId tr:not(:first)').each(function(){
			setCModes(count,true);
			count++;
		});
		for(var i=0;i<tadaRequestBean.tadaTdReqJourneyList.length;i++){
			var j=0;
			$jq('#requestJourneyDetailsId tr:not(:first)').each(function(){
				for(var k=0;k<travelTypeListJSON.length;k++){
					if(i==j){
						if(tadaRequestBean.tadaTdReqJourneyList[i].conveyanceMode==travelTypeListJSON[k].travelType){
							$jq(this).find('td').eq(5).find('select').val(travelTypeListJSON[k].id);
							enableEntitleClasses(j);
						}
					}
				}
				j++;
			});
		}
		for(var i=0;i<tadaRequestBean.tadaTdReqJourneyList.length;i++){
			var j=0;
			$jq('#requestJourneyDetailsId tr:not(:first)').each(function(){
				for(var k=0;k<entitleClassListJSON.length;k++){
					if(i==j){
						if(tadaRequestBean.tadaTdReqJourneyList[i].classOfEntitlement==entitleClassListJSON[k].entitleClass){
							$jq(this).find('td').eq(6).find('select').val(entitleClassListJSON[k].id);
						}
					}
				}
				j++;
			});
		}
		$jq('#requestDetailsDiv').hide();
		$jq('#journeyDetailsDiv').hide();
		$jq('#remarksDiv').show();
		$jq('#reasonText').text('');
		$jq('#reasonText').append('Reason for TD cancellation<span class=mandatory>*</span>');
		$jq('#ticketCancellationDiv').show();
	}
}
function enableLeave(){
	var status=true;
	var errorMessage='';
	var totalStayDays=0;
	if($jq('input:radio[name=leaveRequirement]:checked').val()=='1'){
		if ($jq('#stayDuration').val()=='') {
			errorMessage += "Please Enter Stay Duration.\n";
			$jq('#stayDuration').focus();
			status = false;
		}
		if ($jq('#tdPlace').val()=='') {
			errorMessage += "Please Enter TD Place.\n";
			$jq('#tdPlace').focus();
			status = false;
		}
		if ($jq('#tdWorkPlace').val()=='') {
			errorMessage += "Please Enter TD Work Place.\n";
			$jq('#tdWorkPlace').focus();
			status = false;
		}
		if ($jq('#purpose').val()=='') {
			errorMessage += "Please Enter Purpose.\n";
			$jq('#purpose').focus();
			status = false;
		}
		if(!$jq('input:radio[name=authorizedMove]').is(':checked')){
			errorMessage += "Please Select Project For Which Move is Authorized.\n";
			status = false;
		}
		if(!$jq('input:radio[name=tadaRequirement]').is(':checked')){
			errorMessage += "Please Select Whether TA/DA is required with Hotel/Normal rates.\n";
			status = false;
		}
		$jq('#requestJourneyDetailsId tr:not(:first)').each(function(){
			if($jq(this).find('td').eq(0).find('input').val()=='' && $jq(this).find('td').eq(1).find('select').val()=='Select' && $jq(this).find('td').eq(2).find('select').val()=='Select' && $jq(this).find('td').eq(4).find('select').val()=='Select' && $jq(this).find('td').eq(5).find('select').val()=='Select'){
				errorMessage += "Please Fill all details in journey details.\n";
				status = false;
			}else{
				if($jq(this).find('td').eq(0).find('input').val()==''){
					errorMessage += "Please Choose Departure Date in journey details.\n";
					status = false;
				}
				if($jq(this).find('td').eq(1).find('select').val()=='Select'){
					errorMessage += "Please Select From Place in journey details.\n";
					status = false;
				}
				if($jq(this).find('td').eq(1).find('select').val()=='Other' && $jq(this).find('td').eq(1).find('input').val()==''){
					errorMessage += "Please Enter From Place in journey details.\n";
					status = false;
				}
				if($jq(this).find('td').eq(2).find('select').val()=='Select'){
					errorMessage += "Please Select To Place in journey details.\n";
					status = false;
				}
				if($jq(this).find('td').eq(2).find('select').val()=='Other' && $jq(this).find('td').eq(2).find('input').val()==''){
					errorMessage += "Please Enter To Place in journey details.\n";
					status = false;
				}
				if($jq(this).find('td').eq(4).find('select').val()=='Select'){
					errorMessage += "Please Select Mode Of Conveyance in journey details.\n";
					status = false;
				}
				if($jq(this).find('td').eq(5).find('select').val()=='Select'){
					errorMessage += "Please Select Class Of Entitlement in journey details.\n";
					status = false;
				}
			}
		});
		if($jq('#requestJourneyDetailsId tr:last').find('td').eq(1).find('input:text').val()==''){
			errorMessage += "Please Choose Arrival Date in journey details.\n";
			status = false;
		}
		$jq('#requestJourneyDetailsId tr:not(:first)').each(function(){
			totalStayDays=parseFloat($jq(this).find('td').eq(4).find('input').val())+totalStayDays;
		});
		if(totalStayDays!=parseFloat($jq('#stayDuration').val())){
			errorMessage += "Stay duration at out station must be equal to sum of no. of days in journey details.\n";
			status = false;
			$jq('#stayDuration').focus()
		}
		if($jq('#projDirName').is(':visible')){
			if($jq('#projDirName').val()=='Select'){
				errorMessage += "Please select one project director.\n";
				status = false;
			}
		}
	}
	if(status){
		if($jq('input:radio[name=leaveRequirement]:checked').val()=='1'){
			$jq('#leaveDiv').show();
			var departureDate=$jq('#journeyDate0').val();
			var arrivalDate=$jq('#requestJourneyDetailsId tr:last').find('td').eq(1).find('input:text').val();
			var requestDetails = {
					"departureDate" : departureDate,
					"arrivalDate" : arrivalDate,
					"param" : "leaveDetails"
				};
				$jq.ajax( {
					type : "POST",
					url : "tadaApprovalRequest.htm?"+dispUrl,
					data : requestDetails,
					cache : false,
					success : function(html) {
						$jq('#SelectLeft').html(html);
						if ($jq('.success').is(':visible')) {
							
						}
					}
				});
		}else{
			$jq('#SelectRight').find('option').remove().end();
			$jq('#SelectLeft').find('option').remove().end();
			 $jq('#leaveDiv').hide();
		}
	}else{
		alert(errorMessage);
		$jq('input:radio[name=leaveRequirement]').attr('checked',false);
	}
}
function setTdLeaveList(){
	$jq('#SelectRight').find('option').remove();
	if(tdLeaveTypeList.length==0){
		$jq('#SelectLeft').append($jq('<option  value=no>No leaves are applied</option>'));
		//$jq('#SelectLeft').attr('disabled','disabled');
		$jq('#SelectRight').find('option').remove();
	}else {
		if($jq('#SelectRight').find('option').text()==""){
			$jq('#SelectRight').append($jq('<option  value=select>Please Select Leaves</option>'));
			$jq('#SelectRight').attr('disabled','disabled');
			$jq('#SelectLeft').attr('disabled',false);
		}
		for(var i=0;i<tdLeaveTypeList.length;i++){
			$jq('#SelectLeft').append($jq('<option  value='+tdLeaveTypeList[i].key+'>'+tdLeaveTypeList[i].name+'</option>'));
		}
		
	}
	$jq('#ajaxBusy').remove();
}
function checkReqArrivalDate(){
	var errorMessage='';
	var status=true;
	$jq('#requestJourneyDetailsId tr:not(:first)').each(function(){
		if($jq(this).find('td').eq(0).find('input').val()!=''){
			if(parseFloat(compareDates(convertDate($jq(this).find('td').eq(0).find('input').val()),convertDate($jq(this).find('td').eq(1).find('input').val())))<0){
				errorMessage += "Arrival Date should be equal or after "+$jq(this).find('td').eq(0).find('input').val()+".\n";
				$jq(this).find('td').eq(1).find('input').val('');
				status=false;
			}
			if(parseFloat(compareDates(convertDate($jq(this).find('td').eq(0).find('input').val()),convertDate($jq(this).find('td').eq(1).find('input').val())))+1>7){
				errorMessage += "Journey Period should be equal to one week or less than one week.\n";
				$jq(this).find('td').eq(1).find('input').val('');
				status=false;
			}
		}else if($jq(this).find('td').eq(0).find('input').val()==''){
			errorMessage += "Plese select departure date before selecting arrival date.\n";
			$jq(this).find('td').eq(1).find('input').val('');
			status=false;
		}
	});
	if(status){
		
	}else{
		alert(errorMessage);
	}
}
function submitEntitleExemption(){
	var errorMessage = "";
	var status = true;
	if ($jq('#sfID').val()=='Select') {
		errorMessage += "Please Select SFID.\n";
		status = false;
	}
	if ($jq('#programCode').val()=='Select') {
		errorMessage += "Please Select Program.\n";
		status = false;
	}
	if ($jq('#projectName').val()=='Select') {
		errorMessage += "Please Select Project.\n";
		status = false;
	}
	if ($jq('#entitleType').val()=='Select') {
		errorMessage += "Please Select Entitle Type.\n";
		status = false;
	}
	if ($jq('#description').val()=='') {
		errorMessage += "Please Enter Remarks.\n";
		status = false;
	}
	if (status) {
		var requestDetails = {
				"nodeID" : nodeID,
				"exemptionSfID" : $jq('#sfID').val(),
				"programCode" : $jq('#programCode').val(),
				"projectName" : $jq('#projectName').val(),
				"entitleTypeId" : $jq('#entitleType').val(),
				"description" : $jq('#description').val(),
				"param" : "submitEntitleExemption"
			};
			$jq.ajax( {
				type : "POST",
				url : "tada.htm?"+dispUrl,
				data : requestDetails,
				cache : false,
				success : function(html) {
					$jq('#result').html(html);
					if ($jq('.success').is(':visible')) {
						clearEntitleExemption();
					}
				}
			});

	} else {
		alert(errorMessage);
	}
}
function clearEntitleExemption(){
	$jq('#sfID').val('select');
	$jq('#programCode').val('select');
	showProjects();
	$jq('#projectName').val('select');
	$jq('#entitleType').val('select');
	$jq('#description').val('');
	nodeID='';
}
function editEntitleExemption(id,sfID,projectName,entitleTypeId,remarks,programCode){
	$jq('#sfID').val(sfID);
	$jq('#programCode').val(programCode);
	showProjects();
	$jq('#projectName').val(projectName);
	showExemptEntitle();
	$jq('#entitleType').val(entitleTypeId);
	$jq('#description').val(remarks);
	nodeID=id;
}
function deleteEntitleExemption(id){
	var status = true;
	if (status) {
		var requestDetails = {
				"nodeID" : id,
				"param" : "deleteEntitleExemption"
			};
			$jq.ajax( {
				type : "POST",
				url : "tada.htm?"+dispUrl,
				data : requestDetails,
				cache : false,
				success : function(html) {
					$jq('#result').html(html);
					if ($jq('.success').is(':visible')) {
						clearEntitleExemption();
					}
				}
			});

	} else {
		alert(errorMessage);
	}
}
function submitProgram(){
	var errorMessage = "";
	var status = true;
	if ($jq('#programCode').val()=='') {
		errorMessage += "Please Enter Program Code.\n";
		status = false;
	}
	if ($jq('#programName').val()=='') {
		errorMessage += "Please Enter Program Name.\n";
		status = false;
	}
	if (status) {
		var requestDetails = {
				"nodeID" : nodeID,
				"programCode" : $jq('#programCode').val(),
				"programName" : $jq('#programName').val(),
				"param" : "submitProgram"
			};
			$jq.ajax( {
				type : "POST",
				url : "tada.htm?"+dispUrl,
				data : requestDetails,
				cache : false,
				success : function(html) {
					$jq('#result').html(html);
					if ($jq('.success').is(':visible')) {
						clearProgram();
					}
				}
			});

	} else {
		alert(errorMessage);
	}
}
function clearProgram(){
	$jq('#programCode').val('');
	$jq('#programName').val('');
	nodeID='';
}
function editProgram(id,programCode,programName){
	nodeID=id;
	$jq('#programCode').val(programCode);
	$jq('#programName').val(programName);
}
function deleteProgram(id){
	var status = true;
	if (status) {
		var requestDetails = {
				"nodeID" : id,
				"param" : "deleteProgram"
			};
			$jq.ajax( {
				type : "POST",
				url : "tada.htm?"+dispUrl,
				data : requestDetails,
				cache : false,
				success : function(html) {
					$jq('#result').html(html);
					if ($jq('.success').is(':visible')) {
						clearProgram();
					}
				}
			});

	} else {
		alert(errorMessage);
	}
}
function showProjects(){
	$jq("#projectName :not(:first)").each(function() {
		$jq(this).remove();
	});
	for(var i=0;i<projectsList.length;i++){
		if($jq('#programCode').val()==projectsList[i].programCode){
			$jq('#projectName').append($jq('<option value='+projectsList[i].projectName+'>'+projectsList[i].projectName+'</option>'));
		}
	}
}
function showExemptEntitle(){
	$jq("#entitleType :not(:first)").each(function() {
		$jq(this).remove();
	});
	for(var i=0;i<gradePayList.length;i++){
		if(gradePayList[i].sfid==$jq('#sfID').val()){
			for(var j=0;j<travelTypeMapDetailsJSON.length;j++){
				for(var k=0;k<travelTypeList.length;k++){
					if(travelTypeMapDetailsJSON[j].id==gradePayList[i].gradePay){
						if(travelTypeMapDetailsJSON[j].travelTypeId==travelTypeList[k].id){
							$jq('#entitleType').append($jq('<option value='+travelTypeList[k].id+'>'+travelTypeList[k].travelType+'</option>'));
						}
					}
				}
			}
		}
	}
}
function removePenalAndExcessAmtDiv(){
	$jq('#penalInterestReqId').remove();
	$jq('#excessAmountFineDiv').remove();
}
function setSettlementValues(){
	for(var i=0;i<WorkBeanMap.mroDetailsList.length;i++){
		$jq('#totalTadaTdCalAmount').val(parseFloat(WorkBeanMap.cdaDetailsDTO.cdaAmount)-parseFloat(WorkBeanMap.mroDetailsList[0].unUtilizedBalance));
		var amount="-"+WorkBeanMap.mroDetailsList[0].unUtilizedBalance;
		$jq('#excessAmount').val(parseFloat(amount));
		/*$jq('#excessAmountFineDiv'+WorkBeanMap.mroDetailsList[WorkBeanMap.mroDetailsList.length-1].id+'').show();
		$jq('#penalInterestReqId'+WorkBeanMap.mroDetailsList[WorkBeanMap.mroDetailsList.length-1].id+'').show();
		$jq('input:radio[id=penalInterestReq1'+WorkBeanMap.mroDetailsList[WorkBeanMap.mroDetailsList.length-1].id+']').attr('checked',true);
		$jq('#excessAmountFine'+WorkBeanMap.mroDetailsList[WorkBeanMap.mroDetailsList.length-1].id+'').val(WorkBeanMap.mroDetailsList[WorkBeanMap.mroDetailsList.length-1].penalInterestAmount);
		$jq('#noOfDays'+WorkBeanMap.mroDetailsList[WorkBeanMap.mroDetailsList.length-1].id+'').val(WorkBeanMap.mroDetailsList[WorkBeanMap.mroDetailsList.length-1].noOfDays);
		$jq('#MRODetailsDiv'+WorkBeanMap.mroDetailsList[WorkBeanMap.mroDetailsList.length-1].id+'').show();*/
	}
}
var penalcount=0;
function createPenalInterestRow(idvalue,count)
{
	penalcount=count;
	var status=true;
	var errorMessage='';
	if($jq('#MROPaidNo'+penalcount).val()==''){
		errorMessage+="Please Enter MRO No..\n";
		status=false;
	}
	if($jq('#MROPaidDate'+penalcount).val()==''){
		errorMessage+="Please Select MRO Date.\n";
		status=false;
	}
	if(status){
		
		$jq.post('tadaApprovalRequest.htm?&param=serverHit',function(data){});
		penalcount++;
		var row = "<tr id=penalInterestRow"+penalcount+">";
		row += "<td>";
			row += "yes";
		row += "</td>";
		
		row += "<td>";
		    row += "<input type=text name=noOfDays"+penalcount+" id=noOfDays"+penalcount+" style='width:60px;background-color:pink' onkeyup=javascript:calculateTadaPenalFines(\'"+WorkBeanMap.cdaDetailsDTO.cdaAmount+"\'); onkeypress='javascript:return checkInt(event)';/>";
	    row += "</td>";
	
	    row += "<td>";
		    row += "<input type=text readonly=readonly id=interestRate"+penalcount+" style=width:60px value=\'"+WorkBeanMap.ltcPenalInterestRate.typeValue+"\' />";
	    row += "</td>";
		
	    row += "<td>";
	    row += "<input type=text name=unUtilizedBal"+penalcount+" id=unUtilizedBal"+penalcount+" style='width:60px;background-color:pink ' value=0 onkeyup=javascript:calculateTadaPenalFines(\'"+WorkBeanMap.cdaDetailsDTO.cdaAmount+"\'); onkeypress='javascript:return checkInt(event)'; />";                         // it has modified for editable
    row += "</td>";
	    
	    
	    
		row += "<td>";
		    row += "<input type=text disabled=disabled name=excessAmountFine"+penalcount+" id=excessAmountFine"+penalcount+" style=width:60px value=0 onkeypress='javascript:return checkInt(event)';/>";
	    row += "</td>";
		
	    row += "<td>";
	    row += "<input type=text disabled=disabled id=totMroAmount"+penalcount+" style=width:80px value=0 />";
    row += "</td>";
	    
	    
		row += "<td>";
			row += " ";			
		row += "</td>";
		
		row += "<td>";
		    row += "";			
	    row += "</td>";
	    
	    row += "<td>";
	        row += "";			
        row += "</td>";
		
		row += "<td>";
			row += "";
		row += "</td>";

		row += "<td>";
			row += "<input type=button id=penalInterestDel"+penalcount+" value=- onclick=javascript:deletePenalInterestRow(this,\'"+idvalue+"\');refreshMroDetails()  />";
		row += "</td>";
		
	row += "</tr>";

	$jq("#penalInterestRow"+(penalcount-1)).after(row);
	}else{
		alert(errorMessage);
	}
}
function deletePenalInterestRow(node,tableID)
{
	var dynmicRow = document.getElementById(tableID);
	var index=node.parentNode.parentNode.rowIndex;
	
	 
	var del=confirm("Do you want to delete this row?");
	if(del==true) 
	{
		if(index>=0)
		{
			dynmicRow.deleteRow(index);
			$jq('#remarks').val('');
		}
		
	}
	var length1 = dynmicRow.rows.length;
	
	if(length1=='4')
	{
		createPenalInterestRow(tableID);
	}
	
	penalcount--;
}
function showPenalInterestGrid(){
	$jq('#penalInterestGrid').show();
}
function setSettValues(){
	for(var i=0;i<TadaRequestBean.tdReqJourneyList.length;i++){
		var flag1=true;
		var flag2=true;
		$jq('#modeOfTravel'+i).val(TadaRequestBean.tdReqJourneyList[i].conveyanceMode);
		for(var j=0;j<cityTypeList.length;j++){
			if(cityTypeList[j].cityName==TadaRequestBean.tdReqJourneyList[i].fromPlace){
				$jq('#startStation'+i).val(TadaRequestBean.tdReqJourneyList[i].fromPlace);
				setSelectBoxWidth1('startStation'+i+'');
				var flag1=false;
			}
			if(cityTypeList[j].cityName==TadaRequestBean.tdReqJourneyList[i].toPlace){
				$jq('#endStation'+i).val(TadaRequestBean.tdReqJourneyList[i].toPlace);
				setSelectBoxWidth1('endStation'+i+'');
				var flag2=false;
			}
		}
		if(flag1){
			$jq('#startStation'+i).val('Other');
			$jq('#otherStartStation'+i).show();
			$jq('#otherStartStation'+i).val(TadaRequestBean.tdReqJourneyList[i].fromPlace);
		}
		if(flag2){
			$jq('#endStation'+i).val('Other');
			$jq('#otherEndStation'+i).show();
			$jq('#otherEndStation'+i).val(TadaRequestBean.tdReqJourneyList[i].toPlace);
		}
	}
}
function checkPayValues(id){
	if(!isNaN(parseFloat($jq('#'+id).val()))){
		if(parseFloat($jq('#'+id).val())==0){
			alert("Please enter the value grater than 0");
			$jq('#'+id).val('');
			$jq('#'+id).focus();
		}
	}
}
function setTotalStay(){
	var totalDays=0;
	$jq('#requestJourneyDetailsId tr:not(:first)').each(function(){
	if(!isNaN(parseFloat($jq(this).find('td').eq(4).find('input').val()))){
		totalDays=totalDays+parseFloat($jq(this).find('td').eq(4).find('input').val());
	}
	});
	$jq('#stayDuration').val(totalDays);
	$jq('#stayDurationId').html(':'+totalDays);
	
}
function setTatkalQuota(){
	$jq('#requestJourneyDetailsId tr:not(:first)').each(function(){
		if($jq(this).find('td').eq(5).find('select').val()!="2"){
			$jq(this).find('td').eq(7).find('select').attr('disabled','disabled');
		}else{
			$jq(this).find('td').eq(7).find('select').attr('disabled',false);
		}
	});
}
function setCModes(count,flag){
	if($jq('input:radio[name=entitlementExemption]').is(':visible')){
		if(flag==true){
			if($jq('#requestJourneyDetailsId').find('tr:not(:first)').length>1){
				var count=0;
				$jq('#requestJourneyDetailsId tr:not(:first)').each(function(){
					if($jq('input:radio[name=entitlementExemption]:checked').val()=='1'){
						var mot=$jq('#modeOfTravel'+count).val();
						var coe=$jq('#classOfEntitlement'+count).val();
						$jq("#modeOfTravel"+count+" :not(:first)").each(function() {
							$jq(this).remove();
						});
						for(var i=0;i<travelTypeListJSON.length;i++){
							$jq('#modeOfTravel'+count).append($jq('<option value='+travelTypeListJSON[i].id+'>'+travelTypeListJSON[i].travelType+'</option>'));
						}
						$jq('#modeOfTravel'+count).val(mot);
					}else{
						enableConveyanceModes(count);
					}
					count++;
				});
			}else{
				if($jq('input:radio[name=entitlementExemption]:checked').val()=='1'){
					var mot=$jq('#modeOfTravel'+count).val();
					var coe=$jq('#classOfEntitlement'+count).val();
					$jq("#modeOfTravel"+count+" :not(:first)").each(function() {
						$jq(this).remove();
					});
					for(var i=0;i<travelTypeListJSON.length;i++){
						$jq('#modeOfTravel'+count).append($jq('<option value='+travelTypeListJSON[i].id+'>'+travelTypeListJSON[i].travelType+'</option>'));
					}
					$jq('#modeOfTravel'+count).val(mot);
				}else{
					enableConveyanceModes(count);
				}
			}
		}
	}	
}
function checkReqDeptDate(count){
	var journeyDate1='';
	if($jq('#journeyArrDate'+(count-1)).val()!=''){
		journeyDate1=$jq('#journeyArrDate'+(count-1)).val();
	}else{
		journeyDate1=$jq('#journeyDate'+(count-1)).val();
	}
	var journeyDate2=$jq('#journeyDate'+count).val();
	if(parseFloat($jq('#noOfDays'+(count-1)).val())!=0){
		if(parseFloat(compareDates(convertDate(journeyDate1),convertDate(journeyDate2)))<(parseFloat($jq('#noOfDays'+(count-1)).val())-1)){
			if($jq('#journeyArrDate'+(count-1)).val()!=''){
				alert("Departure Date should be equal or after "+tadaFormatDate(addDays($jq('#journeyArrDate'+(count-1)).val(),parseFloat($jq('#noOfDays'+(count-1)).val())-1),'dd-NNN-yyyy')+"");
				$jq('#journeyDate'+count).val(tadaFormatDate(addDays($jq('#journeyArrDate'+(count-1)).val(),parseFloat($jq('#noOfDays'+(count-1)).val())-1),'dd-NNN-yyyy'));
			}else{
				alert("Departure Date should be equal or after "+tadaFormatDate(addDays($jq('#journeyDate'+(count-1)).val(),parseFloat($jq('#noOfDays'+(count-1)).val())-1),'dd-NNN-yyyy')+"");
				$jq('#journeyDate'+count).val(tadaFormatDate(addDays($jq('#journeyDate'+(count-1)).val(),parseFloat($jq('#noOfDays'+(count-1)).val())-1),'dd-NNN-yyyy'));
			}
			
		}
	}else{
		if(parseFloat(compareDates(convertDate(journeyDate1),convertDate(journeyDate2)))<(parseFloat($jq('#noOfDays'+(count-1)).val()))){
			alert("Departure Date should be equal or after "+tadaFormatDate(addDays($jq('#journeyDate'+(count-1)).val(),parseFloat($jq('#noOfDays'+(count-1)).val())),'dd-NNN-yyyy')+"");
			$jq('#journeyDate'+count).val(tadaFormatDate(addDays($jq('#journeyDate'+(count-1)).val(),parseFloat($jq('#noOfDays'+(count-1)).val())),'dd-NNN-yyyy'));
		}
	}
	
}
function setNextFromPlace(count){
	if($jq('#toPlace'+(count+1)).is(':visible')){
		if($jq('#toPlace'+count).val()=='Other'){
			$jq('#fromPlace'+(count+1)).val($jq('#toPlace'+count).val());
			$jq('#otherFromPlace'+(count+1)+'').show();
			$jq('#otherFromPlace'+(count+1)+'').val($jq('#otherToPlace'+count).val());
			$jq('#otherFromPlace'+(count+1)+'').attr('readonly',true);
		}else{
			$jq('#fromPlace'+(count+1)).val($jq('#toPlace'+count).val());
			if($jq('#otherFromPlace'+(count+1)).is(':visible'))
				$jq('#otherFromPlace'+(count+1)).hide();
		}
		setToPlace((count+1));
	}
}
function setLeaveCheck(){
	$jq('input:radio[id=leaveRequirement2]').attr('checked',true);
}
function addSelect(){
	if($jq('#MoveRight').val()==' Add '){
		if($jq('#SelectRight').find('option').length==1){
			if($jq('#SelectRight').find('option').val()=='select'){
				$jq('#SelectRight').find('option').remove();
			}
		}
	}
}
function removeSelect(){
	if($jq('#MoveLeft').val()==' Remove '){
		if($jq('#SelectRight').find('option').length==0){
			$jq('#SelectRight').append($jq('<option  value=select>Please Select Leaves</option>'));
			$jq('#SelectRight').attr('disabled','disabled');
		}
	}
}
function changeArrivalDate(count){
	$jq('#journeyArrDate'+count).val('');
}
function setReqDeptDate(count){
	var journeyDate1='';
	if($jq('#journeyArrDate'+(count-1)).val()!=''){
		journeyDate1=$jq('#journeyArrDate'+(count-1)).val();
	}else{
		journeyDate1=$jq('#journeyDate'+(count-1)).val();
	}
	if(parseFloat($jq('#noOfDays'+(count-1)).val())!=0){
		if($jq('#journeyArrDate'+(count-1)).val()!=''){
			
			if(!isNaN(parseFloat($jq('#noOfDays'+(count-1)).val())-1)){
				$jq('#journeyDate'+count).val(tadaFormatDate(addDays($jq('#journeyArrDate'+(count-1)).val(),parseFloat($jq('#noOfDays'+(count-1)).val())-1),'dd-NNN-yyyy'));              //parseFloat($jq('#noOfDays'+(count-1)).val())-1) remove -1 prasad			
			}else{
				//$jq('#journeyDate'+count).val(tadaFormatDate(addDays($jq('#journeyArrDate'+(count-1)).val(),0),'dd-NNN-yyyy'));              //parseFloat($jq('#noOfDays'+(count-1)).val())-1) remove -1  ,line shifted prasad	
				parseFloat($jq('#noOfDays'+(count-1)).val('0'))
			}
		}else{
			$jq('#journeyDate'+count).val(tadaFormatDate(addDays($jq('#journeyDate'+(count-1)).val(),parseFloat($jq('#noOfDays'+(count-1)).val())-1),'dd-NNN-yyyy'));
		}
	}else{
		//$jq('#journeyDate'+count).val(tadaFormatDate(addDays($jq('#journeyArrDate'+(count-1)).val(),parseFloat($jq('#noOfDays'+(count-1)).val())),'dd-NNN-yyyy'));                   //The id has been changed instead of journeyDate journeyArrDate  Prasad
	}
	$jq('#journeyArrDate'+count).val('');
}
function setDelegateReqId(){
	$jq('#requestId').find('option').remove();
	$jq('#requestId').append($jq('<option value=Select>Select</option>'));
	$jq('#absence').find('input:checkbox').remove();
	$jq('#absence').find('label').remove();
	$jq('#delegateTo').find('option').remove();
	$jq('#delegateTo').append($jq('<option value=Select>Select</option>'));
	for(var i=0;i<pendingReqList.length;i++){
		if($jq('#requestType').val()==pendingReqList[i].requestTypeID){
			$jq('#requestId').append($jq('<option value='+pendingReqList[i].requestID+'>'+pendingReqList[i].requestID+'</option>'));
		}
	}
}
function setSuperior(){
	$jq('#absence').find('input:checkbox').remove();
	$jq('#absence').find('label').remove();
	for(var i=0;i<pendingReqList.length;i++){
		for(var j=0;j<keyList.length;j++){
			if($jq('#requestId').val()==pendingReqList[i].requestID){
				if(pendingReqList[i].assignedRoleID==keyList[j].id && pendingReqList[i].requestID==keyList[j].flag){
					$jq('#absence').append($jq('<input type=checkbox name=absenceSfid id=absenceSfid value='+pendingReqList[i].assignedRoleID+' onchange=javascript:setDelegateTo(); /><label>'+keyList[j].value+'-'+keyList[j].name+'</label>'));
				}
			}
		}
	}
}
function setDelegateTo(){
	$jq('#delegateTo').find('option').remove();
	$jq('#delegateTo').append($jq('<option value=Select>Select</option>'));
	if($jq('input:checkbox[name=absenceSfid]').is(':checked')){
		if($jq('input:checkbox[name=absenceSfid]:checked').val()==tdReqBean.bossId.split('#')[1]){
			/*if(tdReqBean.bossesBossId.split('#')[1]!=tdReqBean.dyTechDirId.split('#')[1] && tdReqBean.bossesBossId.split('#')[1]!=tdReqBean.tdId.split('#')[1] && 
					tdReqBean.bossesBossId.split('#')[1]!=tdReqBean.adId.split('#')[1] && tdReqBean.bossesBossId.split('#')[1]!=tdReqBean.labDirId.split('#')[1]){
				$jq('#delegateTo').append($jq('<option value='+tdReqBean.bossesBossId.split('#')[1]+'>'+tdReqBean.bossesBossId.split('#')[0]+' - '+tdReqBean.bossesBossId.split('#')[3]+' - '+tdReqBean.bossesBossId.split('#')[2]+'</option>'));
			}*/
			if(tdReqBean.dyTechDirId!='')
				$jq('#delegateTo').append($jq('<option value='+tdReqBean.dyTechDirId.split('#')[1]+'>'+tdReqBean.dyTechDirId.split('#')[0]+' - '+tdReqBean.dyTechDirId.split('#')[3]+' - '+tdReqBean.dyTechDirId.split('#')[2]+'</option>'));
		}
		if($jq('input:checkbox[name=absenceSfid]:checked').val()==tdReqBean.tdId.split('#')[1]){
			/*if(tdReqBean.type=='no'){
				$jq('#delegateTo').append($jq('<option value='+tdReqBean.dyTechDirId.split('#')[1]+'>'+tdReqBean.dyTechDirId.split('#')[0]+' - '+tdReqBean.dyTechDirId.split('#')[3]+' - '+tdReqBean.dyTechDirId.split('#')[2]+'</option>'));
			}*/
			if(tdReqBean.adId!='')
				$jq('#delegateTo').append($jq('<option value='+tdReqBean.adId.split('#')[1]+'>'+tdReqBean.adId.split('#')[0]+' - '+tdReqBean.adId.split('#')[3]+' - '+tdReqBean.adId.split('#')[2]+'</option>'));
		}
	}
}
function tdRequestDelegate(){
	var checkedRequests = "";
	var errorMessage = "";
	var status=true;
	checkedRequests+=$jq('#requestId').val()+",";
	if($jq('#requestType').val()=='Select'){
		errorMessage += "Please Select Request Type.\n";
		status=false;
	}
	if($jq('#requestId').val()=='Select'){
		errorMessage += "Please Select Request Id.\n";
		status=false;
	}
	if(!($jq('#absenceSfid').is(':visible') && $jq('#absenceSfid').is(':checked'))){
		errorMessage += "Please Select Absence Of Superiors.\n";
		status=false;
	}
	if($jq('#delegateTo').val()=='Select'){
		errorMessage += "Please Select Delegate To.\n";
		status=false;
	}
	if($jq('#reason').val()==''){
		errorMessage += "Please Enter Remarks.\n";
		status=false;
	}
	if (status) {
		var requestDetails = {
				"instanceId" : $jq('#delegateTo').val(),
				"requestIDs" : checkedRequests,
				"remarks" : $jq('#reason').val(),
				"param" : "delegateSubmit"
			};
			$jq.ajax( {
				type : "POST",
				url : "workflow.htm?"+dispUrl,
				data : requestDetails,
				cache : false,
				success : function(html) {
					$jq('#result').html(html);
					if ($jq('.success').is(':visible')) {
						clearTdRequestDelegate();
					}
				}
			});

	} else {
		alert(errorMessage);
	}
}
function clearTdRequestDelegate(){
	$jq('#requestType').val('Select');
	$jq('#reason').val('');
	setDelegateReqId();
}
function setTotalAccAdvAmount(){
	if($jq('#noOfDaysAcc').val()!='' && $jq('#accAmtPerDay').val()!='.' && $jq('#accAmtPerDay').val()!=''){
		$jq('#totalAccAmt').val($jq('#noOfDaysAcc').val()*$jq('#accAmtPerDay').val());
	}else if($jq('#accAmtPerDay').val()==''){
		$jq('#totalAccAmt').val(0);
	}
}
function setTotalFoodAdvAmount(){
	if($jq('#noOfDaysFood').val()!='' && $jq('#foodAmtPerDay').val()!='.' && $jq('#foodAmtPerDay').val()!=''){
		$jq('#totalFoodAmt').val($jq('#noOfDaysFood').val()*$jq('#foodAmtPerDay').val());
	}else if($jq('#foodAmtPerDay').val()==''){
		$jq('#totalFoodAmt').val(0);
	}
}
function setDeptPlace(){
	var flag=true;
	for(var i=0;i<cityList.length;i++){
		if(cityList[i].cityName=='Hyderabad'){
			$jq('#fromPlace0').val(cityList[i].cityName);
			flag=false;
			$jq('#fromPlace0').attr('disabled','disabled');
			setToPlace(0);
			setSelectBoxWidth('fromPlace0');
			break;
		}
	}
	if(flag){
		$jq('#fromPlace0').val('Hyderabad');
		$jq('#fromPlace0').attr('disabled','disabled');
		setToPlace(0);
		setSelectBoxWidth('fromPlace0');
	}
}
function showOtherPlace(count){
	$jq("#fromPlace"+count+" :not(:first)").each(function() {
		$jq('#otherFromPlace'+count+'').hide();
	});
	$jq("#toPlace"+count+" :not(:first)").each(function() {
		$jq('#otherToPlace'+count+'').hide();
	});
	if($jq('#fromPlace'+count+'').val()=='Other'){
		$jq('#otherFromPlace'+count+'').show();
		//$jq('#otherFromPlace'+count+'').val('');
		$jq('#otherFromPlace'+count+'').focus();
	}
	if($jq('#toPlace'+count+'').val()=='Other'){
		$jq('#otherToPlace'+count+'').show();
		//$jq('#otherToPlace'+count+'').val('');
		$jq('#otherToPlace'+count+'').focus();
	}
}
function showTicketCancelReason(){
	if(!isNaN($jq('#ticketCancelCharges').val())){
		if(parseFloat($jq('#ticketCancelCharges').val())>0){
			$jq('#ticketCancelReasonDiv').show();
			$jq('#ticketCancelReasonDiv1').show();
		}else{
			$jq('#ticketCancelReasonDiv').hide();
			$jq('#ticketCancelReasonDiv1').hide();
		}
	}
}
function setJdaSdaTotal(){
	$jq('#daOldDetailsId tr:not(:first)').each(function(){
		if(!isNaN($jq(this).find('td').eq(1).find('input').val())){
			$jq(this).find('td').eq(3).find('input').val(parseFloat($jq(this).find('td').eq(1).find('input').val())*parseFloat($jq(this).find('td').eq(2).find('input').val()));
		}
		if(!isNaN($jq(this).find('td').eq(4).val())){
			$jq(this).find('td').eq(6).find('input').val(parseFloat($jq(this).find('td').eq(4).find('input').val())*parseFloat($jq(this).find('td').eq(5).find('input').val()));
		}
	});
}
function getFoodTotal(){
	var total=0;
	$jq('#daNewFoodDayDetailsId tr:not(:first,:last)').each(function(){
		if($jq(this).find('td').eq(1).find('input').val()!=''){
			total=parseFloat($jq(this).find('td').eq(1).find('input').val())+total;
		}
	});
	$jq('#daNewFoodDayDetailsId').find('tr:last').find('td').eq(1).find('input').val(total);
}
function setSelectedValue(id,repSigValue) {
	document.getElementById(id).value=repSigValue;
}
function checkDAJourneyRow(count){
	var errorMessage="";
	var status=true;
	var flag=false;
	$jq('#journeyDetailsId').find('tr').eq(count+2).find('td').each(function(){
		if($jq(this).find('input:text').is(':visible') && $jq(this).find('input:text').val()==''){
			flag=true;
		}
	});
	if(flag){
		errorMessage+="Please fill all details of row-"+(count+1).toString()+" in Journey Details";
		status=false;
	}
	if(status){
		createDAJourneyRow('journeyDetailsId',count);
	}else{
		alert(errorMessage);
	}
}
var countJourneyDA=0;
function createDAJourneyRow(idvalue,countVal){
	$jq.post('tadaApprovalRequest.htm?&param=serverHit',function(data){});
	countJourneyDA=countVal;
	countJourneyDA++;
	
	var row = "<tr id=journeyRow"+countJourneyDA+">";
	row += "<td><input type=hidden value='' /><input type=text size=9px id=journeyDeptDate"+countJourneyDA+" onfocus=javascript:Calendar.setup({inputField:'journeyDeptDate"+countJourneyDA+"',ifFormat:'%d-%b-%Y',showsTime:false,button:'journeyDeptDate"+countJourneyDA+"',step:1}); /></td>";
	row += "<td><input type=text size=2px id=journeyDeptTime"+countJourneyDA+" class=journeyDeptTime onfocus=javascript:timePicker('journeyDeptTime'); /></td>";
	row += "<td><input type=text size=8px id=journeyDeptStn"+countJourneyDA+" onkeypress='javascript:return checkChar(event);' /></td>";
	row += "<td><input type=text size=9px id=journeyArrDate"+countJourneyDA+" onfocus=javascript:Calendar.setup({inputField:'journeyArrDate"+countJourneyDA+"',ifFormat:'%d-%b-%Y',showsTime:false,button:'journeyArrDate"+countJourneyDA+"',step:1}); /></td>";
	row += "<td><input type=text size=2px id=journeyArrTime"+countJourneyDA+" class=journeyArrTime onfocus=javascript:timePicker('journeyArrTime'); /></td>";
	row += "<td><input type=text size=8px id=journeyArrStn"+countJourneyDA+" onkeypress=javascript:return checkChar(event); /></td>";
	row += "<td><input type=text size=5px id=journeyDistance"+countJourneyDA+" onkeypress='javascript:return checkFloatTada(event,journeyDistance"+countJourneyDA+")'; /></td>";
	row += "<td><input type=text size=4px id=modeOfTravel"+countJourneyDA+" onkeypress=javascript:return checkChar(event); /></td>";
	row += "<td><input type=text size=4px id=ticketFare"+countJourneyDA+" style='text-align: right;' onkeypress='javascript:return checkFloatTada(event,ticketFare"+countJourneyDA+")'; /></td>";
	row += "<td><input type=text size=4px id=ticketFareAftRes"+countJourneyDA+" style='background-color: pink;text-align: right;' onkeypress='javascript:return checkFloatTada(event,ticketFareAftRes"+countJourneyDA+")'; onkeyup=javascript:individualTotAmount('journeyDetailsId'); /></td>";
	row += "<td><input type=text size=4px id=ticketCancelAmount"+countJourneyDA+" style='text-align: right;' onkeypress='javascript:return checkFloatTada(event,ticketCancelAmount"+countJourneyDA+")'; /></td>";
	row += "<td><input type=text size=4px id=ticketNo"+countJourneyDA+" /></td>";
	row += "<td><input type=text size=4px id=ticketCancelAmountAftRes"+countJourneyDA+" style='background-color: pink;text-align: right;' onkeypress='javascript:return checkFloatTada(event,ticketCancelAmountAftRes"+countJourneyDA+")'; onkeyup=javascript:individualTotAmount('journeyDetailsId'); /></td>";
	row += "<td ><input type=button id=add"+countJourneyDA+" value=+ onclick=javascript:checkDAJourneyRow("+countJourneyDA+"); /></td>";
	row += "<td ><input type=button id=del"+countJourneyDA+" value=- onclick=javascript:deleteDAJourneyRow(this,'journeyDetailsId'); /></td>";
    row += "</tr>";
    $jq("#journeyRow"+(countJourneyDA-1)).after(row);
}
function deleteDAJourneyRow(node,tableID){
	var dynRow = document.getElementById(tableID);
	var index=node.parentNode.parentNode.rowIndex;
	var del=confirm("Do you want to delete this row?");
	if(del==true) {
		if(index>=0){
			dynRow.rows[index].remove();
		}
	}
	var length1 = dynRow.rows.length;
	countJourneyDA--;
	//getTadaTdTotalAmount();
	individualTotAmount('journeyDetailsId');
}
function checkFinDANewAccRow(count){
	var errorMessage="";
	var status=true;
	var flag=false;
	$jq('#daNewAccDetailsId').find('tr').eq(count+1).find('td').each(function(){
		if($jq(this).find('input:text').is(':visible') && $jq(this).find('input:text').val()==''){
			flag=true;
		}
	});
	if(flag){
		errorMessage+="Please fill all details of row-"+(count+1).toString()+" in Accommodation Details";
		status=false;
	}
	if(status){
		createFinDANewAccRow('daNewAccDetailsId',count);
	}else{
		alert(errorMessage);
	}
}
var countFinDaNewAcc=0;
function createFinDANewAccRow(idvalue,countVal){
	$jq.post('tadaApprovalRequest.htm?&param=serverHit',function(data){});
	countFinDaNewAcc=countVal;
	countFinDaNewAcc++;
	
	var row = "<tr id=tdDaNewAccRow"+countFinDaNewAcc+">";
	row += "<td><input type=hidden value='' /><input type=text size=10px id=accFromDate"+countFinDaNewAcc+" onfocus=javascript:Calendar.setup({inputField:'accFromDate"+countFinDaNewAcc+"',ifFormat:'%d-%b-%Y',showsTime:false,button:'accFromDate"+countFinDaNewAcc+"',step:1}); onchange='javascript:checkNewFoodDate(this);' /></td>";
	row += "<td></td>";
	row += "<td><input type=text size=10px id=accToDate"+countFinDaNewAcc+" onfocus=javascript:Calendar.setup({inputField:'accToDate"+countFinDaNewAcc+"',ifFormat:'%d-%b-%Y',showsTime:false,button:'accToDate"+countFinDaNewAcc+"',step:1}); onchange='javascript:checkNewFoodDate(this);'/></td>";
	row += "<td></td>";
	row += "<td><input type=text size=20px id=accAmount"+countFinDaNewAcc+" style='text-align: right;' onkeypress='javascript:return checkFloatTada(event,accAmount"+countFinDaNewAcc+")'; /></td>";
	row += "<td><input type=text size=20px id=accAmountAftRes"+countFinDaNewAcc+" style='background-color: pink;text-align: right;' onkeypress='javascript:return checkFloatTada(event,accAmountAftRes"+countFinDaNewAcc+")'; onkeyup=javascript:enableAccAmtAftCal("+countFinDaNewAcc+");individualTotAmount('daNewAccDetailsId'); /></td>";
	row += "<td><input type=text size=20px id=claimedAmount"+countFinDaNewAcc+" style='text-align: right;' onkeypress='javascript:return checkFloatTada(event,claimedAmount"+countFinDaNewAcc+")'; /></td>";
	row += "<td><input type=text size=25px id=claimedAmountAftRes"+countFinDaNewAcc+" style='background-color: pink;text-align: right;' onkeypress='javascript:return checkFloatTada(event,claimedAmountAftRes"+countFinDaNewAcc+")'; onkeyup=javascript:individualTotAmount('daNewAccDetailsId'); /></td>";
	row += "<td ><input type=button id=add"+countFinDaNewAcc+" value=+ onclick=javascript:checkFinDANewAccRow("+countFinDaNewAcc+"); /></td>";
	row += "<td ><input type=button id=del"+countFinDaNewAcc+" value=- onclick=javascript:deleteFinDANewAccRow(this,'daNewAccDetailsId'); /></td>";
    row += "</tr>";
    $jq("#tdDaNewAccRow"+(countFinDaNewAcc-1)).after(row);
}
function deleteFinDANewAccRow(node,tableID){
	var dynRow = document.getElementById(tableID);
	var index=node.parentNode.parentNode.rowIndex;
	var del=confirm("Do you want to delete this row?");
	if(del==true) {
		if(index>=0){
			dynRow.rows[index].hide();
		}
	}
	var length1 = dynRow.rows.length;
	countFinDaNewAcc--;
	//getTadaTdTotalAmount();
	individualTotAmount('daNewAccDetailsId');
}
function checkFinDANewFoodRow(count){
	var errorMessage="";
	var status=true;
	var flag=false;
	$jq('#daNewFoodDetailsId').find('tr').eq(count+1).find('td').each(function(){
		if($jq(this).find('input:text').is(':visible') && $jq(this).find('input:text').val()==''){
			flag=true;
		}
	});
		if(flag){
		errorMessage+="Please fill all details of row-"+(count+1).toString()+" in Food Details";
		status=false;
	}
	if(status){
		createFinDANewFoodRow('daNewFoodDetailsId',count);
	}else{
		alert(errorMessage);
	}
}
var countFinDaNewFood=0;
function createFinDANewFoodRow(idvalue,countVal){
	$jq.post('tadaApprovalRequest.htm?&param=serverHit',function(data){});
	//$jq.post('workflowmap.htm?&param=serverHit',function(data){});
	countFinDaNewFood=countVal;
	countFinDaNewFood++;
	
	var row = "<tr id=tdDaNewFoodRow"+countFinDaNewFood+">";
	
	row += "<td><input type=hidden value='' /><input type=text size=10px id=foodFromDate"+countFinDaNewFood+"  onfocus=javascript:Calendar.setup({inputField:'foodFromDate"+countFinDaNewFood+"',ifFormat:'%d-%b-%Y',showsTime:false,button:'foodFromDate"+countFinDaNewFood+"',step:1}); onchange='javascript:checkNewFoodDate(this);'/></td>";
	row += "<td><input type=text size=10px id=foodToDate"+countFinDaNewFood+" onfocus=javascript:Calendar.setup({inputField:'foodToDate"+countFinDaNewFood+"',ifFormat:'%d-%b-%Y',showsTime:false,button:'foodToDate"+countFinDaNewFood+"',step:1}); onchange='javascript:checkNewFoodDate(this);'/></td>";
	row += "<td><input type=text size=30px id=foodAmount"+countFinDaNewFood+" style='text-align: right;' onkeypress='javascript:return checkFloatTada(event,foodAmount"+countFinDaNewFood+")'; /></td>";
	row += "<td><input type=text size=35px id=foodAmountAftRes"+countFinDaNewFood+" style='background-color: pink;text-align: right;' onkeypress='javascript:return checkFloatTada(event,foodAmountAftRes"+countFinDaNewFood+")'; /></td>";
	row += "<td><input type=text size=25px id=claimedFoodAmount"+countFinDaNewFood+" style='text-align: right;' onkeypress='javascript:return checkFloatTada(event,claimedFoodAmount"+countFinDaNewFood+")'; /></td>";
	row += "<td ><input type=button id=add"+countFinDaNewFood+" value=+ onclick=javascript:checkFinDANewFoodRow("+countFinDaNewFood+"); /></td>";
	row += "<td ><input type=button id=del"+countFinDaNewFood+" value=- onclick=javascript:deleteFinDANewFoodRow(this,'daNewFoodDetailsId'); /></td>";
    row += "</tr>";
    $jq("#tdDaNewFoodRow"+(countFinDaNewFood-1)).after(row);
}
function deleteFinDANewFoodRow(node,tableID){
	var dynRow = document.getElementById(tableID);
	var index=node.parentNode.parentNode.rowIndex;
	/*if(jq('#'+tableID).find('tr').length>2)
	{*/
	var del=confirm("Do you want to delete this row?");
	if(del==true) {
		if(index>=0){
			
			//$jq('#row' + rowIndex).remove();
		
			id = $jq('#tdDaNewFoodRow'+(index-1)).find('td').find('input:hidden').val();
			dynRow.rows[index].remove();
		if(id!=""){
		$jq.post('tadaApprovalRequest.htm?&param=deletefood&id='+id,function(data){});
			}
		}
	}
	var length1 = dynRow.rows.length;
	countFinDaNewFood--;
	//getTadaTdTotalAmount();
	individualTotAmount('all');
	
}
function checkFinRMAKmRow(count){
	var errorMessage="";
	var status=true;
	var flag=false;
	$jq('#RMAKmDetailsId').find('tr').eq(count+1).find('td').each(function(){
		if($jq(this).find('input:text').is(':visible') && $jq(this).find('input:text').val()==''){
			flag=true;
		}
	});
	if(flag){
		errorMessage+="Please fill all details of row-"+(count+1).toString()+" in RMA at TD Place Details.";
		status=false;
	}
	if(status){
		createFinRMAKmRow('RMAKmDetailsId',count);
	}else{
		alert(errorMessage);
	}
}
var countFinRMAKm=0;
function createFinRMAKmRow(idvalue,countVal){
	$jq.post('tadaApprovalRequest.htm?&param=serverHit',function(data){});
	countFinRMAKm=countVal;
	countFinRMAKm++;
	
	var row = "<tr id=tdRMAKmRow"+countFinRMAKm+">";
	row += "<td><input type=hidden value='' /><input type=text id=rmaKmDate"+countFinRMAKm+" size=9px onfocus=javascript:Calendar.setup({inputField:'rmaKmDate"+countFinRMAKm+"',ifFormat:'%d-%b-%Y',showsTime:false,button:'rmaKmDate"+countFinRMAKm+"',step:1}); /></td>";
	row += "<td><input type=text id=rmaKmFromPlace"+countFinRMAKm+" size=8px onkeypress=javascript:return isAlphaNumaricExp(event); /></td>";
	row += "<td><input type=text id=rmaKmToPlace"+countFinRMAKm+" size=8px onkeypress=javascript:return isAlphaNumaricExp(event); /></td>";
	row += "<td><input type=text id=rmaKmTravelBy"+countFinRMAKm+" size=8px onkeypress=javascript:return isAlphaNumaricExp(event); /></td>";
	row += "<td><input type=text id=rmaKmDiatance"+countFinRMAKm+" style='text-align: right;' size=5px onkeypress='javascript:return checkFloatTada(event,rmaKmDiatance"+countFinRMAKm+")'; /></td>";
	row += "<td><input type=text id=rmaKmDiatanceAftRes"+countFinRMAKm+" style='text-align: right;background-color: pink' size=10px onkeypress='javascript:return checkFloatTada(event,rmaKmDiatanceAftRes"+countFinRMAKm+")'; onkeyup=javascript:enableRMAKmClaimedAmount();individualTotAmount('RMAKmDetailsId'); /></td>";
	row += "<td><input type=text id=rmaKmAmountPerKm"+countFinRMAKm+" style='text-align: right;' size=5px onkeypress='javascript:return checkFloatTada(event,rmaKmAmountPerKm"+countFinRMAKm+")'; /></td>";
	row += "<td><input type=text id=rmaKmAmountPerKmAftRes"+countFinRMAKm+" style='text-align: right;background-color: pink' size=10px onkeypress='javascript:return checkFloatTada(event,rmaKmAmountPerKmAftRes"+countFinRMAKm+")'; onkeyup=javascript:enableRMAKmClaimedAmount();individualTotAmount('RMAKmDetailsId'); /></td>";
	row += "<td><input type=text id=rmaKmClaimedAmount"+countFinRMAKm+" style='text-align: right;' size=10px onkeypress='javascript:return checkFloatTada(event,rmaKmClaimedAmount"+countFinRMAKm+")'; /></td>";
	row += "<td><input type=text id=rmaKmClaimedAmountAftRes"+countFinRMAKm+" style='text-align: right;' size=10px onkeypress='javascript:return checkFloatTada(event,rmaKmClaimedAmountAftRes"+countFinRMAKm+")'; onkeyup=javascript:individualTotAmount('RMAKmDetailsId'); /></td>";
    row += "<td><input type=button id=add"+countFinRMAKm+" value=+ onclick=javascript:checkFinRMAKmRow("+countFinRMAKm+"); /></td>";
    row += "<td><input type=button id=del"+countFinRMAKm+" value=- onclick=javascript:deleteFinRMAKmRow(this,'RMAKmDetailsId'); /></td>";
	row += "</tr>";
    $jq("#tdRMAKmRow"+(countFinRMAKm-1)).after(row);
}
function deleteFinRMAKmRow(node,tableID){
	var dynRow = document.getElementById(tableID);
	var index=node.parentNode.parentNode.rowIndex;
	var del=confirm("Do you want to delete this row?");
	if(del==true) {
		if(index>=0){
			dynRow.rows[index].remove();
		}
	}
	var length1 = dynRow.rows.length;
	countFinRMAKm--;
	//getTadaTdTotalAmount();
	individualTotAmount('RMAKmDetailsId');
}
function checkFinRMALocalRow(count){
	var errorMessage="";
	var status=true;
	var flag=false;
	$jq('#RMALocalDetailsId').find('tr').eq(count+1).find('td').each(function(){
		if($jq(this).find('input:text').is(':visible') && $jq(this).find('input:text').val()==''){
			flag=true;
		}
	});
	if(flag){
		errorMessage+="Please fill all details of row-"+(count+1).toString()+" in Local RMA Details.";
		status=false;
	}
	if(status){
		createFinRMALocalRow('RMALocalDetailsId',count);
	}else{
		alert(errorMessage);
	}
}
var countFinRMALocal=0;
function createFinRMALocalRow(idvalue,countVal){
	$jq.post('tadaApprovalRequest.htm?&param=serverHit',function(data){});
	countFinRMALocal=countVal;
	countFinRMALocal++;
	
	var row = "<tr id=tdRMALocalRow"+countFinRMALocal+">";
	row += "<td><input type=hidden value='' /><input type=text id=rmaLocalDate"+countFinRMALocal+" size=9px onfocus=javascript:Calendar.setup({inputField:'rmaLocalDate"+countFinRMALocal+"',ifFormat:'%d-%b-%Y',showsTime:false,button:'rmaLocalDate"+countFinRMALocal+"',step:1}); /></td>";
	row += "<td><input type=text id=rmaLocalFromPlace"+countFinRMALocal+" size=8px onkeypress=javascript:return isAlphaNumaricExp(event); /></td>";
	row += "<td><input type=text id=rmaLocalToPlace"+countFinRMALocal+" size=8px onkeypress=javascript:return isAlphaNumaricExp(event); /></td>";
	row += "<td><input type=text id=rmaLocalTravelBy"+countFinRMALocal+" size=8px onkeypress=javascript:return isAlphaNumaricExp(event); /></td>";
	row += "<td><input type=text id=rmaLocalDiatance"+countFinRMALocal+" style='text-align: right;' size=5px onkeypress='javascript:return checkFloatTada(event,rmaLocalDiatance"+countFinRMALocal+")'; /></td>";
	row += "<td><input type=text id=rmaLocalDiatanceAftRes"+countFinRMALocal+" style='text-align: right;background-color: pink' size=10px onkeypress='javascript:return checkFloatTada(event,rmaLocalDiatanceAftRes"+countFinRMALocal+")'; onkeyup=javascript:enableRMALocalClaimedAmount();individualTotAmount('RMALocalDetailsId'); /></td>";
	row += "<td><input type=text id=rmaLocalAmountPerKm"+countFinRMALocal+" style='text-align: right;' size=5px onkeypress='javascript:return checkFloatTada(event,rmaLocalAmountPerKm"+countFinRMALocal+")'; /></td>";
	row += "<td><input type=text id=rmaLocalAmountPerKmAftRes"+countFinRMALocal+" style='text-align: right;background-color: pink' size=10px onkeypress='javascript:return checkFloatTada(event,rmaLocalAmountPerKmAftRes"+countFinRMALocal+")'; onkeyup=javascript:enableRMALocalClaimedAmount();individualTotAmount('RMALocalDetailsId'); /></td>";
	row += "<td><input type=text id=rmaLocalClaimedAmount"+countFinRMALocal+" style='text-align: right;' size=10px onkeypress='javascript:return checkFloatTada(event,rmaLocalClaimedAmount"+countFinRMALocal+")'; /></td>";
	row += "<td><input type=text id=rmaLocalClaimedAmountAftRes"+countFinRMALocal+" style='text-align: right;' size=10px onkeypress='javascript:return checkFloatTada(event,rmaLocalClaimedAmountAftRes"+countFinRMALocal+")'; onkeyup=javascript:individualTotAmount('RMALocalDetailsId'); /></td>";
    row += "<td><input type=button id=add"+countFinRMALocal+" value=+ onclick=javascript:checkFinRMALocalRow("+countFinRMALocal+"); /></td>";
    row += "<td><input type=button id=del"+countFinRMALocal+" value=- onclick=javascript:deleteFinRMALocalRow(this,'RMALocalDetailsId'); /></td>";
	row += "</tr>";
    $jq("#tdRMALocalRow"+(countFinRMALocal-1)).after(row);
}
function deleteFinRMALocalRow(node,tableID){
	var dynRow = document.getElementById(tableID);
	var index=node.parentNode.parentNode.rowIndex;
	var del=confirm("Do you want to delete this row?");
	if(del==true) {
		if(index>=0){
			dynRow.rows[index].hide();
			
		}
	}
	var length1 = dynRow.rows.length;
	countFinRMALocal--;
	//getTadaTdTotalAmount();
	individualTotAmount('RMALocalDetailsId');
}
function checkFinRMADailyRow(count){
	var errorMessage="";
	var status=true;
	var flag=false;
	$jq('#RMADailyDetailsId').find('tr').eq(count+1).find('td').each(function(){
		if($jq(this).find('input:text').is(':visible') && $jq(this).find('input:text').val()==''){
			flag=true;
		}
	});
	if(flag){
		errorMessage+="Please fill all details of row-"+(count+1).toString()+" in RMA at TD Place Details.";
		status=false;
	}
	if(status){
		createFinRMADailyRow('RMADailyDetailsId',count);
	}else{
		alert(errorMessage);
	}
}
var countFinRMADaily=0;
function createFinRMADailyRow(idvalue,countVal){
	$jq.post('tadaApprovalRequest.htm?&param=serverHit',function(data){});
	countFinRMADaily=countVal;
	countFinRMADaily++;
	var row = "<tr id=tdRMADailyRow"+countFinRMADaily+">";
	row += "<td><input type=hidden value='' /><input type=text id=rmaDailyDate"+countFinRMADaily+" size=9px onfocus=javascript:Calendar.setup({inputField:'rmaDailyDate"+countFinRMADaily+"',ifFormat:'%d-%b-%Y',showsTime:false,button:'rmaDailyDate"+countFinRMADaily+"',step:1}); /></td>";
	row += "<td><input type=text id=rmaDailyFromPlace"+countFinRMADaily+" size=8px onkeypress=javascript:return isAlphaNumaricExp(event); /></td>";
	row += "<td><input type=text id=rmaDailyToPlace"+countFinRMADaily+" size=8px onkeypress=javascript:return isAlphaNumaricExp(event); /></td>";
	row += "<td><input type=text id=rmaDailyTravelBy"+countFinRMADaily+" size=8px onkeypress=javascript:return isAlphaNumaricExp(event); /></td>";
	row += "<td><input type=text id=rmaDailyDiatance"+countFinRMADaily+" style='text-align: right;' size=5px onkeypress='javascript:return checkFloatTada(event,rmaDailyDiatance"+countFinRMADaily+")'; /></td>";
	row += "<td><input type=text id=rmaDailyDiatanceAftRes"+countFinRMADaily+" style='text-align: right;background-color: pink' size=10px onkeypress='javascript:return checkFloatTada(event,rmaDailyDiatanceAftRes"+countFinRMADaily+")'; onkeyup=javascript:enableRMADailyClaimedAmount();individualTotAmount('RMADailyDetailsId'); /></td>";
	row += "<td><input type=text id=rmaDailyAmountPerDaily"+countFinRMADaily+" style='text-align: right;' size=5px onkeypress='javascript:return checkFloatTada(event,rmaDailyAmountPerDaily"+countFinRMADaily+")'; /></td>";
	row += "<td><input type=text id=rmaDailyAmountPerDailyAftRes"+countFinRMADaily+" style='text-align: right;background-color: pink' size=10px onkeypress='javascript:return checkFloatTada(event,rmaDailyAmountPerDailyAftRes"+countFinRMADaily+")'; onkeyup=javascript:enableRMADailyClaimedAmount();individualTotAmount('RMADailyDetailsId');/></td>";
	row += "<td><input type=text id=rmaDailyClaimedAmount"+countFinRMADaily+" style='text-align: right;' size=10px onkeypress='javascript:return checkFloatTada(event,rmaDailyClaimedAmount"+countFinRMADaily+")'; /></td>";
	row += "<td><input type=text id=rmaDailyClaimedAmountAftRes"+countFinRMADaily+" style='text-align: right;' size=10px onkeypress='javascript:return checkFloatTada(event,rmaDailyClaimedAmountAftRes"+countFinRMADaily+")'; onkeyup=javascript:individualTotAmount('RMADailyDetailsId'); /></td>";
    row += "<td><input type=button id=add"+countFinRMADaily+" value=+ onclick=javascript:checkFinRMADailyRow("+countFinRMADaily+"); /></td>";
    row += "<td>< type=button id=del"+countFinRMADaily+" value=- onclick=javascript:deleteFinRMADailyRow(this,'RMADailyDetailsId'); /></td>";
	row += "</tr>";
    $jq("#tdRMADailyRow"+(countFinRMADaily-1)).after(row);
}
function deleteFinRMADailyRow(node,tableID){
	var dynRow = document.getElementById(tableID);
	var index=node.parentNode.parentNode.rowIndex;
	var del=confirm("Do you want to delete this row?");
	if(del==true) {
		if(index>=0){
			dynRow.rows[index].hide();
		}
	}
	var length1 = dynRow.rows.length;
	countFinRMADaily--;
	//getTadaTdTotalAmount();
	individualTotAmount('RMADailyDetailsId');
}
function checkFinRMADailyRow(count){
	var errorMessage="";
	var status=true;
	var flag=false;
	$jq('#RMADailyDetailsId').find('tr').eq(count+1).find('td').each(function(){
		if($jq(this).find('input:text').is(':visible') && $jq(this).find('input:text').val()==''){
			flag=true;
		}
	});
	if(flag){
		errorMessage+="Please fill all details of row-"+(count+1).toString()+" in RMA at TD Place Details.";
		status=false;
	}
	if(status){
		createFinRMADailyRow('RMADailyDetailsId',count);
	}else{
		alert(errorMessage);
	}
}
var countFinRMADaily=0;
function createFinRMADailyRow(idvalue,countVal){
	$jq.post('tadaApprovalRequest.htm?&param=serverHit',function(data){});
	countFinRMADaily=countVal;
	countFinRMADaily++;
	
	var row = "<tr id=tdRMADailyRow"+countFinRMADaily+">";
	row += "<td><input type=hidden value='' /><input type=text id=rmaDailyDate"+countFinRMADaily+" size=9px onfocus=javascript:Calendar.setup({inputField:'rmaDailyDate"+countFinRMADaily+"',ifFormat:'%d-%b-%Y',showsTime:false,button:'rmaDailyDate"+countFinRMADaily+"',step:1}); /></td>";
	row += "<td><input type=text id=rmaDailyFromPlace"+countFinRMADaily+" size=8px onkeypress=javascript:return isAlphaNumaricExp(event); /></td>";
	row += "<td><input type=text id=rmaDailyToPlace"+countFinRMADaily+" size=8px onkeypress=javascript:return isAlphaNumaricExp(event); /></td>";
	row += "<td><input type=text id=rmaDailyTravelBy"+countFinRMADaily+" size=8px onkeypress=javascript:return isAlphaNumaricExp(event); /></td>";
	row += "<td><input type=text id=rmaDailyDiatance"+countFinRMADaily+" style='text-align: right;' size=5px onkeypress='javascript:return checkFloatTada(event,rmaDailyDiatance"+countFinRMADaily+")'; /></td>";
	row += "<td><input type=text id=rmaDailyDiatanceAftRes"+countFinRMADaily+" style='text-align: right;background-color: pink' size=10px onkeypress='javascript:return checkFloatTada(event,rmaDailyDiatanceAftRes"+countFinRMADaily+")'; onkeyup=javascript:enableRMADailyClaimedAmount();individualTotAmount('RMADailyDetailsId'); /></td>";
	row += "<td><input type=text id=rmaDailyAmountPerDaily"+countFinRMADaily+" style='text-align: right;' size=5px onkeypress='javascript:return checkFloatTada(event,rmaDailyAmountPerDaily"+countFinRMADaily+")'; /></td>";
	row += "<td><input type=text id=rmaDailyAmountPerDailyAftRes"+countFinRMADaily+" style='text-align: right;background-color: pink' size=10px onkeypress='javascript:return checkFloatTada(event,rmaDailyAmountPerDailyAftRes"+countFinRMADaily+")'; onkeyup=javascript:enableRMADailyClaimedAmount();individualTotAmount('RMADailyDetailsId'); /></td>";
	row += "<td><input type=text id=rmaDailyClaimedAmount"+countFinRMADaily+" style='text-align: right;' size=10px onkeypress='javascript:return checkFloatTada(event,rmaDailyClaimedAmount"+countFinRMADaily+")'; /></td>";
	row += "<td><input type=text id=rmaDailyClaimedAmountAftRes"+countFinRMADaily+" style='text-align: right;' size=10px onkeypress='javascript:return checkFloatTada(event,rmaDailyClaimedAmountAftRes"+countFinRMADaily+")'; onkeyup=javascript:individualTotAmount('RMADailyDetailsId'); /></td>";
    row += "<td><input type=button id=add"+countFinRMADaily+" value=+ onclick=javascript:checkFinRMADailyRow("+countFinRMADaily+"); /></td>";
    row += "<td><input type=button id=del"+countFinRMADaily+" value=- onclick=javascript:deleteFinRMADailyRow(this,'RMADailyDetailsId'); /></td>";
	row += "</tr>";
    $jq("#tdRMADailyRow"+(countFinRMADaily-1)).after(row);
}
function deleteFinRMADailyRow(node,tableID){
	var dynRow = document.getElementById(tableID);
	var index=node.parentNode.parentNode.rowIndex;
	var del=confirm("Do you want to delete this row?");
	if(del==true) {
		if(index>=0){
			dynRow.rows[index].hide();
		}
	}
	var length1 = dynRow.rows.length;
	countFinRMADaily--;
	//getTadaTdTotalAmount();
	individualTotAmount('RMADailyDetailsId');
}
function checkFinRMADayRow(count){
	var errorMessage="";
	var status=true;
	var flag=false;
	$jq('#RMADayDetailsId').find('tr').eq(count+1).find('td').each(function(){
		if($jq(this).find('input:text').is(':visible') && $jq(this).find('input:text').val()==''){
			flag=true;
		}
	});
	if(flag){
		errorMessage+="Please fill all details of row-"+(count+1).toString()+" in RMA at TD Place Details.";
		status=false;
	}
	if(status){
		createFinRMADayRow('RMADayDetailsId',count);
	}else{
		alert(errorMessage);
	}
}
var countFinRMADay=0;
function createFinRMADayRow(idvalue,countVal){
	$jq.post('tadaApprovalRequest.htm?&param=serverHit',function(data){});
	countFinRMADay=countVal;
	countFinRMADay++;
	
	var row = "<tr id=tdRMADayRow"+countFinRMADay+">";
	row += "<td><input type=hidden value='' /><input type=text id=rmaDayDate"+countFinRMADay+" size=10px onfocus=javascript:Calendar.setup({inputField:'rmaDayDate"+countFinRMADay+"',ifFormat:'%d-%b-%Y',showsTime:false,button:'rmaDayDate"+countFinRMADay+"',step:1}); /></td>";
	row += "<td><input type=text id=rmaDayFromPlace"+countFinRMADay+" size=10px onkeypress=javascript:return isAlphaNumaricExp(event); /></td>";
	row += "<td><input type=text id=rmaDayToPlace"+countFinRMADay+" size=10px onkeypress=javascript:return isAlphaNumaricExp(event); /></td>";
	row += "<td><input type=text id=rmaDayAmountPerDay"+countFinRMADay+" style='text-align: right;' size=15px onkeypress='javascript:return checkFloatTada(event,rmaDayAmountPerDay"+countFinRMADay+")'; /></td>";
	row += "<td><input type=text id=rmaDayAmountPerDayAftRes"+countFinRMADay+" style='text-align: right;background-color: pink' size=18px onkeypress='javascript:return checkFloatTada(event,rmaDayAmountPerDayAftRes"+countFinRMADay+")'; onkeyup=javascript:enableRMADayClaimAmt();individualTotAmount('RMADayDetailsId'); /></td>";
	row += "<td><input type=text id=rmaDayClaimedAmount"+countFinRMADay+" style='text-align: right;' size=18px onkeypress='javascript:return checkFloatTada(event,rmaDayClaimedAmount"+countFinRMADay+")'; /></td>";
	row += "<td><input type=text id=rmaDayClaimedAmountAftRes"+countFinRMADay+" style='text-align: right;' size=18px onkeypress='javascript:return checkFloatTada(event,rmaDayClaimedAmountAftRes"+countFinRMADay+")'; onkeyup=javascript:individualTotAmount('RMADayDetailsId'); /></td>";
    row += "<td><input type=button id=add"+countFinRMADay+" value=+ onclick=javascript:checkFinRMADayRow("+countFinRMADay+"); /></td>";
    row += "<td><input type=button id=del"+countFinRMADay+" value=- onclick=javascript:deleteFinRMADayRow(this,'RMADayDetailsId'); /></td>";
	row += "</tr>";
    $jq("#tdRMADayRow"+(countFinRMADay-1)).after(row);
}
function deleteFinRMADayRow(node,tableID){
	var dynRow = document.getElementById(tableID);
	var index=node.parentNode.parentNode.rowIndex;
	var del=confirm("Do you want to delete this row?");
	if(del==true) {
		if(index>=0){
			dynRow.rows[index].hide();
			
		}
	}
	var length1 = dynRow.rows.length;
	countFinRMADay--;
	//getTadaTdTotalAmount();
	individualTotAmount('RMADayDetailsId');
}
function setNextDeletePlace(countVal){
	$jq('#fromPlace'+(countVal)).val($jq('#toPlace'+(countVal-2)).val());
	$jq('#toPlace'+(countVal)).val('Select');
}
function checkNewFoodDate(date){
	var errorMessage="";
	var i = 0;
	//for(var i=0;i<travelTypeMapDetailsJSON.length;i++){
	var depDate1 = $jq('#journeyDeptDate0').val();
	var arvDate1 = $jq('#journeyArrDate0').val();
	var finalDepDate1 = depDate1;
	var finalArvDate1 = arvDate1;
	for(var i=0;i<=10;i++){
		if($jq('#journeyDeptDate'+i).is(':visible') || $jq('#journeyArrDate'+i).is(':visible')){	
	    var fromDate = $jq('#journeyDeptDate'+i).val();
	    var toDate = $jq('#journeyArrDate'+i).val();
	  }
		if(parseFloat(compareDates(convertDate(finalDepDate1),convertDate(fromDate)))<0){
			finalDepDate1 = fromDate;
	   }
		if(parseFloat(compareDates(convertDate(finalArvDate1),convertDate(toDate)))>0){
			finalArvDate1 = toDate;
		}
	}
	var depDate = $jq('#journeyDeptDate0').val();
	var  newFoodDate= $jq(date).val();
	depDate = finalDepDate1;
	arvDate = finalArvDate1;
	  if(parseFloat(compareDates(convertDate(depDate),convertDate(newFoodDate)))<0 || parseFloat(compareDates(convertDate(arvDate),convertDate(newFoodDate)))> 0 ){
	         $jq(date).val('');	
		  errorMessage += "Please Enter Valid Dates.\n Date Must Between '"+finalDepDate1+"' and '"+finalArvDate1+"' "; 
		  alert(errorMessage);
	}	
}
function checkNewDate(date){
	var errorMessage="";
	var i = 0;
	
	var depDate1 = $jq('#journeyDate0').val();
	var arvDate1 = $jq('#journeyEndDate0').val();
	var finalDepDate1 = depDate1;
	var finalArvDate1 = arvDate1;
	for(var i=0;i<=10;i++){
		if($jq('#journeyDate'+i).is(':visible') || $jq('#journeyEndDate'+i).is(':visible')){
		
	    var fromDate = $jq('#journeyDate'+i).val();
	    var toDate = $jq('#journeyEndDate'+i).val();
	  }
		if(parseFloat(compareDates(convertDate(finalDepDate1),convertDate(fromDate)))<0){
			finalDepDate1 = fromDate;
	   }
		if(parseFloat(compareDates(convertDate(finalArvDate1),convertDate(toDate)))>0){
			finalArvDate1 = toDate;
		}
	}
	/*var frdate = $jq('#accFromDate').val();
	var todate = $jq('#accToDate').val();*/
	var  newDate= $jq(date).val();
	var depDate = finalDepDate1;
	var arvDate = finalArvDate1;
	  if(parseFloat(compareDates(convertDate(depDate),convertDate(newDate)))<0 || parseFloat(compareDates(convertDate(arvDate),convertDate(newDate)))> 0 ){
	      $jq(date).val('');
		  errorMessage += "Please Enter Valid Dates.\n Date Must Between '"+depDate+"' and '"+arvDate+"' "; 		 
		  alert(errorMessage);
	}
}
function checkNewDate1(date){
	var errorMessage="";
	var i = 0;
	//for(var i=0;i<travelTypeMapDetailsJSON.length;i++){
	for(var i=0;i<=10;i++){
		
		var date1 = $jq('#journeyEndDate'+i);
		if(date1.is(':visible')){
				
	    var fromDate = date1.val();
	   }
	}
	var depDate = $jq('#journeyDate0').val();
	var  newFoodDate= $jq(date).val();
	arvDate = fromDate;
		
	  if(parseFloat(compareDates(convertDate(arvDate),convertDate(newFoodDate)))>0 || parseFloat(compareDates(convertDate(depDate),convertDate(newFoodDate)))< 0 ){
	         
		  $jq(date).val('');	

		  errorMessage += "Please Enter Valid Dates.\n Date Must Between '"+depDate+"' and '"+fromDate+"' "; 

		  errorMessage += "ToDate Should not GreaterThan '"+fromDate+"' ";

		  alert(errorMessage);
	}
	
}

//kumari
/*function totalJourneyAmount(){
	var totalAmount=0;
	$jq('#journeyDetailsId tr:gt(1)').each(function(){
		if(($jq(this).find('td').eq(9).find('input').val()!=undefined && $jq(this).find('td').eq(9).find('input').val()!='') || 
				($jq(this).find('td').eq(12).find('input').val()!=undefined && $jq(this).find('td').eq(12).find('input').val()!='')){
			if(parseFloat($jq(this).find('td').eq(10).find('input').val())==0 && parseFloat($jq(this).find('td').eq(12).find('input').val())==0){
				totalAmount = parseFloat($jq(this).find('td').eq(9).find('input').val())+parseFloat(totalAmount);
			}else{
				if(!isNaN(parseFloat($jq(this).find('td').eq(12).find('input').val()))){
					totalAmount = parseFloat($jq(this).find('td').eq(12).find('input').val())+parseFloat(totalAmount);
				}
			}
		}
	});
	$jq('#totJourneyAmount').text(totalAmount);
}
function totalAccommodationAmount(){
	var totalAmount=0;
	$jq('#daNewAccDetailsId tr:not(:first)').each(function(){
		if($jq(this).find('td').eq(7).find('input').val()!=undefined && $jq(this).find('td').eq(7).find('input').val()!=''){
			totalAmount = parseFloat($jq(this).find('td').eq(7).find('input').val())+parseFloat(totalAmount);
			
		}
	});
	$jq('#totAccAmount').text(totalAmount);
}*/
function individualTotAmount(gridType){
	var totJourneyAmount=0;
	var totAccAmount=0;
	var foodTot=0;
	var totLocalRMAAmount=0;
	var totDailyRMAAmount=0;
	var totDailyRmaKMAmount=0;
	var totRMAAtTdAmount=0;
	var totStayJourneyDAAmount=0;
	var finalAmount=0;
	var errorMessage="";
	var totalBeforeRoundVal = 0;
	
	if(tdEditFlag==true){
	//if(gridType=='journeyDetailsId' || gridType=='all'){
		$jq('#journeyDetailsId tr:gt(1):visible').each(function(){
			if(($jq(this).find('td').eq(9).find('input').val()!=undefined && $jq(this).find('td').eq(9).find('input').val()!='') || 
					($jq(this).find('td').eq(12).find('input').val()!=undefined && $jq(this).find('td').eq(12).find('input').val()!='')){
				if(parseFloat($jq(this).find('td').eq(10).find('input').val())==0 && parseFloat($jq(this).find('td').eq(12).find('input').val())==0){
					totJourneyAmount = parseFloat($jq(this).find('td').eq(9).find('input').val())+parseFloat(totJourneyAmount);
				}else{
					if(!isNaN(parseFloat($jq(this).find('td').eq(12).find('input').val()))){
						totJourneyAmount = parseFloat($jq(this).find('td').eq(12).find('input').val())+parseFloat(totJourneyAmount);
					}
				}
			}else if(($jq(this).find('td').eq(9).text()!=undefined && $jq(this).find('td').eq(9).text()!='') || 
					($jq(this).find('td').eq(12).text()!=undefined && $jq(this).find('td').eq(12).text()!='')){
				if(parseFloat($jq(this).find('td').eq(10).text())==0 && parseFloat($jq(this).find('td').eq(12).text())==0){
					totJourneyAmount = parseFloat($jq(this).find('td').eq(9).text())+parseFloat(totJourneyAmount);
				}else{
					if(!isNaN(parseFloat($jq(this).find('td').eq(12).text()))){
						totJourneyAmount = parseFloat($jq(this).find('td').eq(12).text())+parseFloat(totJourneyAmount);
					}
				}
			}
		});
		totalBeforeRoundVal  +=  totJourneyAmount;
		totJourneyAmount=roundNumber(totJourneyAmount, 2);
		$jq('#totJourneyAmount').text(totJourneyAmount);
		
	//}
	//if(gridType=='daNewAccDayDetailsId' || gridType=='all'){
		$jq('#kdiv').html('');
		$jq('#daNewAccDayDetailsId tr:not(:first,:last):visible').each(function(){
			if($jq(this).find('td').eq(1).find('input').val()!=undefined && $jq(this).find('td').eq(1).find('input').val()!=''){
				totAccAmount = parseFloat($jq(this).find('td').eq(1).find('input').val())+parseFloat(totAccAmount);
				
			}else if($jq(this).find('td').eq(1).text()!=undefined && $jq(this).find('td').eq(1).text()!=''){
				totAccAmount = parseFloat($jq(this).find('td').eq(1).text())+parseFloat(totAccAmount);
			}
		});
		totalBeforeRoundVal += totAccAmount;
		totAccAmount=roundNumber(totAccAmount, 2);
		$jq('#daNewAccDayDetailsId').find('tr:last').eq(0).find('input:text').val(totAccAmount);
		//$jq('#daNewAccDayDetailsId').find('tr:last').eq(1).text(totAccAmount);
	//}
	
	//if(gridType=='daNewFoodDayDetailsId' || gridType=='all'){
	$jq('#daNewFoodDayDetailsId tr:not(:first,:last)').each(function(){
		if($jq(this).find('td').eq(0).text()!='Total'){
			foodTot=parseFloat($jq(this).find('td').eq(1).find('input:text').val())+parseFloat(foodTot);	
		}
	});
	totalBeforeRoundVal  += foodTot;
	foodTot=roundNumber(foodTot, 2);
	$jq('#totalFoodAmount').val(foodTot);
	
	//foodTot=parseFloat($jq('#daNewFoodDayDetailsId').find('tr:last').eq(0).find('input:text').val());
	//}
	
	//if(gridType=='daOldDetailsId' || gridType=='all'){
		$jq('#daOldDetailsId tr:not(:first):visible').each(function(){
			if($jq(this).find('td').eq(3).find('input').val()!=undefined && $jq(this).find('td').eq(3).find('input').val()!=''){
				totStayJourneyDAAmount = parseFloat($jq(this).find('td').eq(3).find('input').val())+parseFloat(totStayJourneyDAAmount);
			}else if($jq(this).find('td').eq(3).text()!=undefined && $jq(this).find('td').eq(3).text()!=''){
				totStayJourneyDAAmount = parseFloat($jq(this).find('td').eq(3).text())+parseFloat(totStayJourneyDAAmount);
			}
			if($jq(this).find('td').eq(6).find('input').val()!=undefined && $jq(this).find('td').eq(6).find('input').val()!=''){
				totStayJourneyDAAmount = parseFloat($jq(this).find('td').eq(6).find('input').val())+parseFloat(totStayJourneyDAAmount);
			}else if($jq(this).find('td').eq(6).text()!=undefined && $jq(this).find('td').eq(6).text()!=''){
				totStayJourneyDAAmount = parseFloat($jq(this).find('td').eq(6).text())+parseFloat(totStayJourneyDAAmount);
			}
			
		});
		totalBeforeRoundVal += totStayJourneyDAAmount;
		totStayJourneyDAAmount=roundNumber(totStayJourneyDAAmount, 2);
		$jq('#totStayJourneyDAAmount').text(totStayJourneyDAAmount);
		
	//}
	//if(gridType=='RMALocalDetailsId' || gridType=='all'){
		$jq('#RMALocalDetailsId tr:not(:first):visible').each(function(){
			if($jq(this).find('td').eq(9).find('input').val()!=undefined && $jq(this).find('td').eq(9).find('input').val()!=''){
				totLocalRMAAmount = parseFloat($jq(this).find('td').eq(9).find('input').val())+parseFloat(totLocalRMAAmount);
			}else if($jq(this).find('td').eq(9).text()!=undefined && $jq(this).find('td').eq(9).text()!=''){
				totLocalRMAAmount = parseFloat($jq(this).find('td').eq(9).text())+parseFloat(totLocalRMAAmount);
			}
		});
		totalBeforeRoundVal += totLocalRMAAmount;
		totLocalRMAAmount=roundNumber(totLocalRMAAmount, 2);
		$jq('#totLocalRMAAmount').text(totLocalRMAAmount);
		
	//}
	//if(gridType=='RMADayDetailsId' || gridType=='all'){
		$jq('#RMADayDetailsId tr:not(:first):visible').each(function(){
			if($jq(this).find('td').eq(6).find('input').val()!=undefined && $jq(this).find('td').eq(6).find('input').val()!=''){
				totDailyRMAAmount = parseFloat($jq(this).find('td').eq(6).find('input').val())+parseFloat(totDailyRMAAmount);
			}else if($jq(this).find('td').eq(6).text()!=undefined && $jq(this).find('td').eq(6).text()!=''){
				totDailyRMAAmount = parseFloat($jq(this).find('td').eq(6).text())+parseFloat(totDailyRMAAmount);
			}
		});
		totalBeforeRoundVal += totDailyRMAAmount;
		totDailyRMAAmount=roundNumber(totDailyRMAAmount, 2);
		$jq('#totDailyRMAAmount').text(totDailyRMAAmount);
		
	//}
	//if(gridType=='RMAKmDetailsId' || gridType=='all'){
		$jq('#RMAKmDetailsId tr:not(:first):visible').each(function(){
			if($jq(this).find('td').eq(9).find('input').val()!=undefined && $jq(this).find('td').eq(9).find('input').val()!=''){
				totDailyRmaKMAmount = parseFloat($jq(this).find('td').eq(9).find('input').val())+parseFloat(totDailyRmaKMAmount);
			}else if($jq(this).find('td').eq(9).text()!=undefined && $jq(this).find('td').eq(9).text()!=''){
				totDailyRmaKMAmount = parseFloat($jq(this).find('td').eq(9).text())+parseFloat(totDailyRmaKMAmount);
			}
		});
		totalBeforeRoundVal  += totDailyRmaKMAmount;
		totDailyRmaKMAmount=roundNumber(totDailyRmaKMAmount, 2);
		$jq('#totDailyRmaKMAmount').text(totDailyRmaKMAmount);
		
	//}
	//if(gridType=='RMADailyDetailsId' || gridType=='all'){
		$jq('#RMADailyDetailsId tr:not(:first):visible').each(function(){
			if($jq(this).find('td').eq(9).find('input').val()!=undefined && $jq(this).find('td').eq(9).find('input').val()!=''){
				totRMAAtTdAmount = parseFloat($jq(this).find('td').eq(9).find('input').val())+parseFloat(totRMAAtTdAmount);
			}else if($jq(this).find('td').eq(9).text()!=undefined && $jq(this).find('td').eq(9).text()!=''){
				totRMAAtTdAmount = parseFloat($jq(this).find('td').eq(9).text())+parseFloat(totRMAAtTdAmount);
			}
		});
		totalBeforeRoundVal  += totRMAAtTdAmount;
		totRMAAtTdAmount=roundNumber(totRMAAtTdAmount, 2);
		$jq('#totRMAAtTdAmount').text(totRMAAtTdAmount);
	
	//}
		if(totJourneyAmount>=0&& totAccAmount>=0 && foodTot>=0 && totLocalRMAAmount>=0 && totDailyRMAAmount>=0 && totDailyRmaKMAmount>=0 && totRMAAtTdAmount>=0 && totStayJourneyDAAmount>=0){
	    finalAmount=totJourneyAmount+totAccAmount+foodTot+totLocalRMAAmount+totDailyRMAAmount+totDailyRmaKMAmount+totRMAAtTdAmount+totStayJourneyDAAmount;
		}
	$jq('#totalTadaTdCalAmount').val(Math.round(finalAmount));
	 $jq('#totalBeforeRound').text("(Amount Before RounfOff:"+parseFloat(totalBeforeRoundVal)+ ")");
	
	if(parseFloat(tadaAdvanceAmount)>Math.round(parseFloat(finalAmount))){
		$jq('#penalInterestGrid').show();
	} else {
		$jq('#penalInterestGrid').hide();
	}
	/*	if(!isNaN(parseFloat(tadaAdvanceAmount))){*/
	if($jq('#penalInterestGrid').find('tr').find('td').eq(6).find('input').is(':visible') && $jq('#penalInterestGrid').find('tr').find('td').eq(6).find('input').val()!='' && $jq('#penalInterestGrid').find('tr').find('td').eq(6).find('input').val()!=undefined){
		
		$jq('#excessAmount').val(Math.round(parseFloat(finalAmount))-parseFloat(tadaAdvanceAmount));
		if($jq('#excessAmount').val()*(-1)>$jq('#unUtilizedBal0').val()){
	      $jq('#light456').show();
		  $jq('#light123').show();
		}else if($jq('#excessAmount').val()*(-1)<$jq('#unUtilizedBal0').val()){
			alert('You have already paid MRO.You are unable to decrease amount</n>It wil be going to refresh.Please fill once again');
			location.reload(true);
		}
	} else{
		var penalInterest=0;
		if($jq('#penalInterestGrid').find('tr').find('td').eq(4).find('input').is(':visible')){
				 penalInterest=parseFloat($jq('#penalInterestGrid').find('tr').find('td').eq(4).find('input').val());	
	}else {
		 penalInterest=parseFloat($jq('#penalInterestGrid').find('tr').find('td').eq(4).text());
		
	}
		$jq('#excessAmount').val(Math.round(parseFloat(finalAmount))-parseFloat(tadaAdvanceAmount));
		if(isNaN($jq('#excessAmount').val()))
			$jq('#excessAmount').val('0');
		/*$jq('#excessAmount').val(parseFloat(finalAmount));*/
		$jq('#unUtilizedBal0').val($jq('#excessAmount').val()*(-1));
		$jq('#totMroAmount0').val($jq('#excessAmount').val()*(-1)+(penalInterest));
		calculateTadaPenalFines($jq('#tadaAdvanceAmount').val());
	}
	}
}
function checkForEdit(){
	var errorMessage="";
	if(($jq('#penalInterestGrid').is(':visible') && $jq('#penalInterestGrid').find('tr:eq(1)').find('td').eq(6).text()!="" && $jq('#penalInterestGrid').find('tr:eq(1)').find('td').eq(6).text()!=undefined) || ($jq('#penalInterestGrid').is(':visible') && $jq('#penalInterestGrid').find('tr:eq(1)').find('td').eq(4).text()!="" && $jq('#penalInterestGrid').find('tr:eq(1)').find('td').eq(4).text()!=undefined) ){
		//if($jq('#penalInterestGrid').is(':visible') && $jq('#penalInterestGrid').find('tr:eq(1)').find('td').find('input').val()!="" && $jq('#penalInterestGrid').find('tr:eq(1)').find('td').find('input').val()!=undefined){
		errorMessage += 'MRO Amount Already Paid You Cant Edit Values';
		tdEditFlag=false;
		alert(errorMessage);
		//}
	}
	return tdEditFlag;
}
function getTdFinSettReport(requestType){
	var amount='';
	var flag=false;
	var flag1=false;
	var billNo='';
	var errorMessage='';
	var length=0;
	var status=true;
	var requestId='';
	if(requestType=='tadaTdSettlement'){
//		$jq('#settlement tr:not(:first)').each(function(){                     //This condition has changed.
		
		$jq('#cdaSettlement tr:not(:first)').each(function(){   
		
			if($jq(this).find('td').eq(0).find('input:checkbox').is(':checked')){
				if(amount==''){
					amount=$jq(this).find('td').eq(5).text().trim();
				}else{
					if(parseFloat(amount)!=0 && $jq(this).find('td').eq(5).text()==0){
						flag=true;
					}else if(parseFloat(amount)==0 && $jq(this).find('td').eq(5).text()!=0){
						flag=true;
					}
				}
				if(billNo==''){
					billNo=$jq(this).find('td').eq(7).find('input').val();
				}else{
					if(billNo!=$jq(this).find('td').eq(7).find('input').val()){
						flag1=true;
						
					}
				}
				length++;
			}
		});
	}
	if(requestType=='tadaTdReimbursement'){
		$jq('#reim tr:not(:first)').each(function(){
			if($jq(this).find('td').eq(0).find('input:checkbox').is(':checked')){
				if(billNo==''){
					billNo=$jq(this).find('td').eq(7).find('input').val();
				}else{
					if(billNo!=$jq(this).find('td').eq(7).find('input').val()){
						flag1=true;
						
					}
				}
				length++;
			}
		});
	}
	if(flag)
		errorMessage += "You can't get both claim and nill claim reports at a time.\n";
	if(flag1)
		errorMessage += "You can't get claim report for diffrent bill numbers.\n";
	if(length==0)
		errorMessage += "Please Check Atleast One Check Box.\n";
	if(errorMessage!=''){
		status=false;
		alert(errorMessage);
	}
	if(status){
//		$jq('#settlement tr:not(:first)').each(function(){
		$jq('#cdaSettlement tr:not(:first)').each(function(){
			if($jq(this).find('td').eq(0).find('input:checkbox').is(':checked')){
				requestId += $jq(this).find('td').eq(1).text().trim()+",";
			}
		});
		if(requestType=='tadaTdSettlement'){
			if(parseFloat(amount)!=0){
				requestType='tadaTdSett';
			}else{
				requestType='tadaTdNillSett';
			}
		}else{
			requestType='tadaTdReim';
		}
		window.open('./report.htm?param=initial&type='+requestType+'&requestID='+requestId,'TADA','fullscreen=no,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
	}
}
function checkCancellationTotal(){
	
	var total = $jq('#TicketCancel').text().trim().split(".")[1];
	var cancelTotalAmt=0;
	$jq('#journeyDetailsId tr:gt(1)').each(function(){
		if($jq(this).find('td').eq(9).find('input:text').val()!=''){
			cancelTotalAmt=cancelTotalAmt+parseFloat($jq(this).find('td').eq(9).find('input:text').val());
		}
	});
	if(total < cancelTotalAmt){
		
		if (total == 0){
			alert("Please take amendement for clamining cancellation charges");
			$jq('#journeyDetailsId tr:gt(1)').each(function(){
				$jq(this).find('td').eq(9).find('input:text').val('0');
				
			});
		}else{
		alert("Sanctioned cancellation charges is Rs."+total+"/- only");
		$jq('#journeyDetailsId tr:gt(1)').each(function(){
			$jq(this).find('td').eq(9).find('input:text').val('0');
			
		});
		}
		$jq('input:radio[name=tadaRequirement]').attr('checked',false);
	}else{
		enableDA();
	}	
}
function showTadaTdDir(){
	$jq('#projectType').attr('disabled',true);
	$jq('#refId').show();
}
function showTadaTdPrjDirector(){
	$jq('#projectType').attr('disabled',false);
}
function showTdSearchWithOption(){
	if($jq('input:radio[name=searchWith]:checked').val() =='requestId'){
		$jq('#requestIdDiv').show();
		$jq('#requestTypeDiv').hide();
		$jq('#sfidDiv').hide();
		$jq('#directorateDiv').hide();
		$jq('#datesDiv').hide();
		$jq('#tadaAppliedUsersList').hide();
		$jq('#statusDiv').hide();
	}else if($jq('input:radio[name=searchWith]:checked').val() =='sfid'){
		$jq('#requestIdDiv').hide();
		$jq('#requestTypeDiv').show();
		$jq('#sfidDiv').show();
		$jq('#directorateDiv').hide();
		$jq('#datesDiv').show();	
		$jq('#tadaAppliedUsersList').hide();
		$jq('#statusDiv').show();
		$jq('#departureDate').val('');
		$jq('#arrivalDate').val('');
	}else if($jq('input:radio[name=searchWith]:checked').val() =='directorate'){
		$jq('#requestIdDiv').hide();
		$jq('#requestTypeDiv').show();
		$jq('#sfidDiv').hide();
		$jq('#directorateDiv').show();
		$jq('#datesDiv').show();	
		$jq('#tadaAppliedUsersList').hide();
		$jq('#statusDiv').show();
		$jq('#departureDate').val('');
		$jq('#arrivalDate').val('');
	}else if($jq('input:radio[name=searchWith]:checked').val() =='dates'){
		$jq('#requestIdDiv').hide();
		$jq('#requestTypeDiv').show();
		$jq('#sfidDiv').hide();
		$jq('#directorateDiv').hide();
		$jq('#datesDiv').show();
		$jq('#tadaAppliedUsersList').hide();
		$jq('#statusDiv').show();
	}else if($jq('input:radio[name=searchWith]:checked').val()=='entitlementexemption'){
		$jq('#requestIdDiv').hide();
		$jq('#requestTypeDiv').hide();
		$jq('#sfidDiv').hide();
		$jq('#directorateDiv').hide();
		$jq('#datesDiv').show();	
		$jq('#tadaAppliedUsersList').hide();
		$jq('#statusDiv').show();
		$jq('#departureDate').val('');
		$jq('#arrivalDate').val('');
	}else if($jq('input:radio[name=searchWith]:checked').val()=='Select'){
		$jq('#requestIdDiv').hide();
		$jq('#requestTypeDiv').show();
		$jq('#sfidDiv').hide();
		$jq('#directorateDiv').hide();
		$jq('#datesDiv').show();	
		$jq('#tadaAppliedUsersList').hide();
		$jq('#statusDiv').show();
	}
}
function searchAppliedUsers(){
	var status=true;
	var errorMessage="";
	if($jq('#searchWith').val()=='Select' && $jq('#requestType').val()=='Select'){
		
		$jq('#searchWith').find('input').each(function(){
			var temp = $jq('#check').find('input:checked').length;
			
			if(temp == 0){
			if(!$jq(this).attr('checked')){
				status = false;
				
			}
			errorMessage += "Please Select a Search Type.\n";
		}
		});
		
		
		
	}
	if($jq('input:radio[name=searchWith]:checked').val()=='requestId' && $jq('#requestId').val()==''){
		errorMessage += "Please Enter Request Id.\n";
		status=false;
	}
	if($jq('input:radio[name=searchWith]:checked').val() =='sfid' && $jq('#sfID').val()==''){
		errorMessage += "Please Enter SFID.\n";
		status=false;
	}
	if($jq('input:radio[name=searchWith]:checked').val() =='directorate'  && $jq('#directorate').val()=='Select'){
		errorMessage += "Please Select Directorate.\n";
		status=false;
	}
	if($jq('#searchWith').val()=='dates'){
		if($jq('#departureDate').val()==''){
			errorMessage += "Please Select From Date.\n";
			status=false;
		}
		if($jq('#departureDate').val()==''){
			errorMessage += "Please Select To Date.\n";
			status=false;
		}
	}
//	if($jq('input:checkbox[name=status]:checked').length==0){
//		errorMessage += "Please Select atleast one status.\n";
//		status=false;
//	}
	if(status){
		var jsonVal={};
		var i=0;
		$jq('#statusDiv').find('input:checkbox').each(function(){
			if($jq(this).attr('checked')){
				var tempJson={};
				tempJson['statusVal']=$jq(this).val();
				jsonVal[i]=tempJson;
				i++;
			}});
		if(jsonVal[0] == null){
		$jq('#statusDiv').find('input:checkbox').each(function(){
			  if(!$jq(this).attr('checked')){
				var tempJsonWithoutStatus={};
				tempJsonWithoutStatus['statusVal']=$jq(this).val();
				jsonVal[i]=tempJsonWithoutStatus;
				i++;
			}   });
	}
		if($jq('#requestType').val() == 'Select'){
			for(var i = 1; i <= $jq('#requestType')[0].length- 1;i++ ){
			var  requestTypeJson={};
			requestTypeJson['requestType']=  $jq('#requestType')[0][i].value ;
			jsonVal[i]=requestTypeJson;
			
		}
		}
		$jq('#param').val('searchTdAppliedUsers');
		$jq('#jsonValue').val(JSON.stringify(jsonVal));
		$jq.post('tadaApprovalRequest.htm', $jq('#tadaRequestBean').serialize(), function(html) {
			$jq('#tadaAppliedUsersList').show();
			$jq("#tadaAppliedUsersList").html(html);
		});
	}else{
		alert(errorMessage);
	}
	
}
var mainList={};
function getTdAppliedUsersReport(mainList){
	var status=true;
	var errorMessage="";
	if($jq('input:radio[name=searchWith]:checked').val()=='Select' && $jq('#requestType').val()=='Select'){
		errorMessage += "Please Select a Search Type.\n";
		status=false;
	}else if($jq('input:radio[name=searchWith]:checked').val()=='requestId' && $jq('#requestId').val()==''){
		errorMessage += "Please Enter Request Id.\n";
		status=false;
	}else if($jq('input:radio[name=searchWith]:checked').val()=='sfid' && $jq('#sfID').val()==''){
		errorMessage += "Please Enter SFID.\n";
		status=false;
	}else if($jq('input:radio[name=searchWith]:checked').val()=='directorate' && $jq('#directorate').val()=='Select'){
		errorMessage += "Please Select Directorate.\n";
		status=false;
	}else if($jq('input:radio[name=searchWith]:checked').val()=='dates'){
		if($jq('#departureDate').val()==''){
			errorMessage += "Please Select From Date.\n";
			status=false;
		}
		if($jq('#departureDate').val()==''){
			errorMessage += "Please Select To Date.\n";
			status=false;
		}
	}
	if(status){
		var mainJson={};
		var jsonVal={};
		var listJson={};
		var i=0;
		$jq('#statusDiv').find('input:checkbox').each(function(){
			if($jq(this).attr('checked')){
				var tempJson={};
				tempJson['statusVal']=$jq(this).val();
				jsonVal[i]=tempJson;
				i++;
			}
		});
		var j=0;
		$jq('#tdAppliedUsersList tr:not(:first)').each(function(){
			var tempJson={};
			tempJson['requestId']=$jq.trim($jq(this).find('td').eq(0).text());
			tempJson['requestType']=$jq.trim($jq(this).find('td').eq(1).text());
			tempJson['sfid']= $jq.trim($jq(this).find('td').eq(2).text());
			tempJson['name']=$jq.trim($jq(this).find('td').eq(3).text());
			tempJson['applyDate']=$jq.trim($jq(this).find('td').eq(4).text())
			tempJson['status']=$jq.trim($jq(this).find('td').eq(5).text());
			listJson[j]=tempJson;
			j++;
		});
		mainJson["statusData"]=jsonVal;
		mainJson["listData"]=listJson;
		
		 $jq('#jsonValue').val (JSON.stringify(mainJson));
		window.open('./report.htm?param=initial&type=tdAppliedUsers&searchWith='+$jq('input:radio[name=searchWith]:checked').val()+'&requestType='+$jq('#requestType').val()+'&requestId='+$jq('#requestId').val()+'&directorate='+$jq('#directorate').find('option:selected').text()+'&departureDate='+$jq('#departureDate').val()+'&arrivalDate='+$jq('#arrivalDate').val()+'&sfID='+$jq('#sfID').val()+'&jsonValue='+$jq('#jsonValue').val());
	}else{
		alert(errorMessage);
	}
}
function setReimPaging(){
	$jq( function(){
		$jq("#Pagination").displayTagAjax('reimbursementPaging');
	})
}
function clearAppliedUsers(){
	$jq('#searchWith').val('Select');
	$jq('#requestType').val('Select');
	$jq('#requestId').val('');
	//$jq('#requestId').hide();
	$jq('#sfID').val('');
	//$jq('#sfID').hide();
	$jq('#directorate').val('Select');
	$jq('#directorateDiv').hide();
	$jq('#departureDate').val('');
	$jq('#arrivalDate').val('');
	$jq('#datesDiv').hide();
	$jq('input:checkbox').attr('checked',false);
	$jq('input:radio').attr('checked',false);
	$jq('#completedStatus').attr('checked',true);
}
function checkDate(date){                                                          //This new function for tada request time check 
	 var tadaTimeBoundPast=parseFloat(tadaRequestBean.tadaTimeBoundPast);
     var tadaTimeBoundFuture=parseFloat(tadaRequestBean.tadaTimeBoundFuture);
	 var id = date.id;
	 var sysdate=new Date();
	 var sys=tadaFormatDate(sysdate,'dd-NNN-yyyy');
	 var sys2=tadaFormatDate(addDays(sys,-tadaTimeBoundPast),'dd-NNN-yyyy');
	 	 sys=tadaFormatDate(addDays(sys,tadaTimeBoundFuture),'dd-NNN-yyyy');
	 	
	  if(compareDate(sys2,date.value)){
	 		alert("Departure Date  Should Not Before "+tadaTimeBoundFuture+" Days From Today");
	 	     $jq('#'+id).val('');
	 	     }
	  if(compareDate(date.value,sys)){
	 alert("Departure Date  Should Not After "+tadaTimeBoundFuture+" Days From Today");
     $jq('#'+id).val('');
	 }
}
function finalCdaAmount(tableId,finalAmount,finalSettlementAmount){                                                               
	if(parseFloat($jq('#'+finalAmount).val())>parseFloat($jq('#'+tableId).find('tr').find('#'+finalSettlementAmount).val())){
		if(confirm('CDA amount greater then Actual Amount\n Do you Wish continue')){
			
			$jq('#'+finalAmount).focus();
		}else{
			alert('You should modify CDA Amount');
			$jq('#'+finalAmount).val("");
		}
		
		
	}
}
function setStayOnDates(count){   
	
	var date1 = new Date(createDDMMYYY(convertDate($jq('#journeyDate'+(count)).val())));
	var date2 = new Date(createDDMMYYY(convertDate($jq('#journeyArrDate'+(count-1)).val())));

/*$jq('#noOfDays'+(count-1)).val(
		(parseFloat($jq('#journeyDate'+(count)).val().split('-')[0])-
				
				parseFloat($jq('#journeyArrDate'+(count-1)).val().split('-')[0]))+1);
*/
$jq('#noOfDays'+(count-1)).val(Math.ceil(Math.abs(date1.getTime()-date2.getTime())/(1000*3600*24))+1);                  //Here added one day for diff b/w two days
setTotalStay();
}
function confirmStatus(id){
    if(id=='YES'){
		$jq('#light123').hide();
		$jq('#light456').hide();
		createPenalInterestRow('penalInterestTabId','0'); 
	}else if(id=='NO'){
		$jq('#light123').hide();
		$jq('#light456').hide();
		location.reload(true);
		
	}
    }
function showAccAmtDayRepresentation(requestId,sfid,roleInstanceName){
	accAmtDayRepresentation(requestId,sfid,roleInstanceName);
		individualTotAmount('all');	
}
function showFoodAmtDayRepresentation(requestId,sfid,roleInstanceName){
		foodAmtDayRepresentation(requestId,sfid,roleInstanceName);
		showTotalFoodAmount();
		individualTotAmount('all');
}










/*new screens*/

function submitTadaAdvanceCumReq1(){
	var errorMessage = "";
	var status = true;
	var reqJourneyDetailsJson={};
	var reqJourneyDetailsArry=new Array();
	var mainJson={};
	var flag=false;
	var requestId;
	var amendmentType = "";
	var projDirName='';
	var totalStayDays=0;
	var selectedValues='';
	var ticketCancelCharges=0;
	var ticketCancelReason='';
	
	/*added by bkr 21/04/2016  oneline inly*/ 
	
	document.getElementById("authorizedMove").checked = true;
	//$jq( "#authorizedMove" ).prop( "checked", true );
	//alert("authorizedMove"+document.getElementById("authorizedMove"));
	
	if($jq('input:radio[name=amendmentType]:checked').val()=='amendment'){
		if($jq('#stayDuration').val()==tadaRequestBean.stayDuration && $jq('#tdWorkPlace').val()==tadaRequestBean.tdWorkPlace && 
				$jq('#purpose').val()==tadaRequestBean.purpose && 
				$jq('input:radio[name=authorizedMove]:checked').val()==tadaRequestBean.authorizedMove && 
				$jq('input:radio[name=tadaRequirement]:checked').val()==tadaRequestBean.daType){
			if((parseFloat(document.getElementById("requestJourneyDetailsId").rows.length)-1)==parseFloat(tadaRequestBean.tadaTdReqJourneyList.length)){
				var t=0;
				$jq('#requestJourneyDetailsId tr:not(:first)').each(function(){
					for(var u=t;u<tadaRequestBean.tadaTdReqJourneyList.length;u++){
						if($jq(this).find('td').eq(0).find('input').val()!=tadaRequestBean.tadaTdReqJourneyList[u].strDepartureDate || 
								$jq(this).find('td').eq(1).find('input').val()!=tadaRequestBean.tadaTdReqJourneyList[u].strArrivalDate ||
								$jq(this).find('td').eq(4).find('input').val()!=tadaRequestBean.tadaTdReqJourneyList[u].noOfDays || 
								$jq(this).find('td').eq(7).find('select').val()!=tadaRequestBean.tadaTdReqJourneyList[u].tatkalQuota){
							flag=true;
						}
						if(flag==false){
							if($jq(this).find('td').eq(2).find('select').val()!='Other'){
								if($jq(this).find('td').eq(2).find('select').val()!=tadaRequestBean.tadaTdReqJourneyList[u].fromPlace){
									flag=true;
								}
							} else{
								if($jq(this).find('td').eq(2).find('input').val()!=tadaRequestBean.tadaTdReqJourneyList[u].fromPlace){
									flag=true;
								}
							}
						}
						if(flag==false){
							if($jq(this).find('td').eq(3).find('select').val()!='Other'){
								if($jq(this).find('td').eq(3).find('select').val()!=tadaRequestBean.tadaTdReqJourneyList[u].toPlace){
									flag=true;
								}
							} else{
								if($jq(this).find('td').eq(3).find('input').val()!=tadaRequestBean.tadaTdReqJourneyList[u].toPlace){
									flag=true;
								}
							}
						}
						if(flag==false){
							for( var s=0;s<travelTypeListJSON.length;s++){
								if(travelTypeListJSON[s].travelType==tadaRequestBean.tadaTdReqJourneyList[u].conveyanceMode){
									if($jq(this).find('td').eq(5).find('select').val()!=travelTypeListJSON[s].id){
										flag=true;
									}
								}
							}
							for(var s=0;s<entitleClassListJSON.length;s++){
								if(entitleClassListJSON[s].entitleClass==tadaRequestBean.tadaTdReqJourneyList[u].classOfEntitlement){
									if($jq(this).find('td').eq(6).find('select').val()!=entitleClassListJSON[s].id){
										flag=true;
									}
								}
							}
						}
						break;
					}
					t++;
				});
				if(flag==false){                                                            //This condition has been added for amendment check                                               
					if($jq('#projDirName').is(':visible')){
						if($jq('#projDirName').val()==tadaRequestBean.projDirName){
							 errorMessage ="You didn't change any value so please change atleast one value before submit the request.\n";
							   status=false;
						}
					}else{
						errorMessage ="You didn't change any value so please change atleast one value before submit the request.\n";
						   status=false;
					}
					}
			}else if((parseFloat(document.getElementById("requestJourneyDetailsId").rows.length)-1)>parseFloat(tadaRequestBean.tadaTdReqJourneyList.length)){
				flag=true;
			}else{
				alert("You deleted "+(parseFloat(tadaRequestBean.tadaTdReqJourneyList.length)-(parseFloat(document.getElementById("requestJourneyDetailsId").rows.length)-1)).toString()+" row from journey details.Do you want to continue");
				flag=true;
			}
		}else if($jq('#stayDuration').val()!=tadaRequestBean.stayDuration || 
				$jq('#tdWorkPlace').val()!=tadaRequestBean.tdWorkPlace || 
				$jq('#purpose').val()!=tadaRequestBean.purpose || 
				$jq('input:radio[name=authorizedMove]:checked').val()!=tadaRequestBean.authorizedMove || 
				$jq('input:radio[name=tadaRequirement]:checked').val()!=tadaRequestBean.daType){
			flag=true;
		}
		if(flag==false){
			var flag1=false;
			if($jq('#ticketCancelReason').is(':visible') && $jq('#ticketCancelReason').val()==''){
				errorMessage += "Please Enter Reason for ticket(s) cancellation";
				status = false;
				flag1=true;
			}
			if(flag1){
				errorMessage += "You didn't change any value so please change atleast one value before submit the request.\n";
				status = false;
			}
		}
		if($jq('#remarks').val()==''){
			errorMessage +="Please Enter Reason for Amendment.\n";
			status=false;
			$jq('#remarks').focus();
		}
		if($jq('#ticketCancelReason').is(':visible') && $jq('#ticketCancelReason').val()==''){
			errorMessage +="Please Enter Reason for ticket(s) cancellation.\n";
			status=false;
			$jq('#ticketCancelReason').focus();
		}
	} else if($jq('input:radio[name=amendmentType]:checked').val()=='cancel'){
		if($jq('#remarks').val()==''){
			errorMessage +="Please Enter Reason for Cancellation.\n";
			status=false;
			$jq('#remarks').focus();
		}
		if($jq('#ticketCancelReason').is(':visible') && $jq('#ticketCancelReason').val()==''){
			errorMessage +="Please Enter Reason for ticket(s) cancellation.\n";
			status=false;
			$jq('#ticketCancelReason').focus();
		}
	}else if($jq('input:radio[name=amendmentType]').is(':visible')){
		if(!$jq('input:radio[name=amendmentType]').is(':checked')){
			errorMessage +="Please Select Amendment Type.\n";
			status=false;
		}
	}
	if ($jq('#stayDuration').val()=='') {
		errorMessage += "Please Enter Stay Duration.\n";
		$jq('#stayDuration').focus();
		status = false;
	}

	if ($jq('#workingPlace').val()=='') {
		errorMessage += "Please Enter TD Place.\n";
		$jq('#tdWorkPlace').focus();
		status = false;
	}

	if ($jq('#tdWorkPlace').val()=='') {
		errorMessage += "Please Enter TD Work Place.\n";
		$jq('#tdWorkPlace').focus();
		status = false;
	}
	if ($jq('#purpose').val()=='') {
		errorMessage += "Please Enter Purpose.\n";
		$jq('#purpose').focus();
		status = false;
	}
	if(!$jq('input:radio[name=authorizedMove]').is(':checked')){
		errorMessage += "Please Select Project For Which Move is Authorized.\n";
		status = false;
	}
	
	/*commented by bkr 21/04/2016 */
	/*if(!$jq('input:radio[name=tadaRequirement]').is(':checked')){
		errorMessage += "Please Select Whether TA/DA is required with Hotel/Normal rates.\n";
		status = false;
	}*/
	if(parseFloat($jq('#stayDuration').val())<=0){
		errorMessage += "Total Stay Duration at Out Station should be grater than or equal to one day.\n";
		status = false;
	}
	if($jq('#requestJourneyDetailsId').is(':visible')){
		$jq('#requestJourneyDetailsId tr:not(:first)').each(function(){
			if($jq(this).find('td').eq(0).find('input').val()=='' && $jq(this).find('td').eq(1).find('input').val()=='' && $jq(this).find('td').eq(2).find('select').val()=='Select' && $jq(this).find('td').eq(3).find('select').val()=='Select' && $jq(this).find('td').eq(5).find('select').val()=='Select' && $jq(this).find('td').eq(6).find('select').val()=='Select'){
				errorMessage += "Please Fill all details in journey details.\n";
				status = false;
			}else{
				if($jq(this).find('td').eq(0).find('input').val()==''){
					errorMessage += "Please Choose Departure Date in journey details.\n";
					status = false;
				}
				if($jq(this).find('td').eq(1).find('input').val()==''){
					errorMessage += "Please Choose Arrival Date in journey details.\n";
					status = false;
				}
				if($jq(this).find('td').eq(2).find('select').val()=='Select'){
					errorMessage += "Please Select From Place in journey details.\n";
					status = false;
				}
				if($jq(this).find('td').eq(2).find('select').val()=='Other' && $jq(this).find('td').eq(2).find('input').val()==''){
					errorMessage += "Please Enter From Place in journey details.\n";
					status = false;
				}
				if($jq(this).find('td').eq(3).find('select').val()=='Select'){
					errorMessage += "Please Select To Place in journey details.\n";
					status = false;
				}
				if($jq(this).find('td').eq(3).find('select').val()=='Other' && $jq(this).find('td').eq(3).find('input').val()==''){
					errorMessage += "Please Enter To Place in journey details.\n";
					status = false;
				}
				if($jq(this).find('td').eq(5).find('select').val()=='Select'){
					errorMessage += "Please Select Mode Of Conveyance in journey details.\n";
					status = false;
				}
				if($jq(this).find('td').eq(6).find('select').val()=='Select'){
					
					//$jq('#entitlementExemption2').val();
					document.getElementById("entitlementExemption2").checked = true;
					//errorMessage += "Please Select Class Of Entitlement in journey details11.\n";
				//	status = false;
				}
			}
		});
	}
	$jq('#requestJourneyDetailsId tr:not(:first)').each(function(){
		totalStayDays=parseFloat($jq(this).find('td').eq(4).find('input').val())+totalStayDays;
	});
	if(totalStayDays!=parseFloat($jq('#stayDuration').val())){
		errorMessage += "Stay duration at out station must be equal to sum of no. of days in journey details.\n";
		status = false;
		$jq('#stayDuration').focus()
	}
	if($jq('#projDirName').is(':visible')){
		if($jq('#projDirName').val()=='Select'){
			errorMessage += "Please select one project director.\n";
			status = false;
		}
	}
	if($jq('input:radio[name=leaveRequirement]:checked').val()=='1'){
		if(document.getElementById('SelectRight').options.length==1){
			if($jq('#SelectRight').find('option').val()=='select'){
				errorMessage += "Please select atleast one leave.\n";
				status = false;
			}else{
				for ( var i = 0; i < document.getElementById('SelectRight').options.length; i++) {
					selectedValues += document.getElementById('SelectRight').options[i].value+ ",";
					status = true;
				}
			}
		}else{
			for ( var i = 0; i < document.getElementById('SelectRight').options.length; i++) {
				selectedValues += document.getElementById('SelectRight').options[i].value+ ",";
				status = true;
			}
		}
		if(document.getElementById('SelectLeft').options.length==1){
			if($jq('#SelectLeft').find('option').val()=='no'){
				errorMessage += "Please select atleast one leave.\n";
				status = false;
			}
		}
	}
	if($jq('#requestJourneyDetailsId tr:last').find('td').eq(3).find('select').val()!='Hyderabad'){
		errorMessage += "You are not entered complete round trip journey details.\n";
		status = false;
	}
	if (status) {
		var i=0;
		$jq('#requestJourneyDetailsId tr:not(:first)').each(function(){
			var valueJson={};
			valueJson['departueDate']=$jq(this).find('td').eq(0).find('input').val();
			valueJson['arrivalDate']=$jq(this).find('td').eq(1).find('input').val();
			if($jq(this).find('td').eq(2).find('select').val()!='Other'){
				valueJson['fromPlace']=$jq(this).find('td').eq(2).find('select').val();
			}else {
				valueJson['fromPlace']=$jq(this).find('td').eq(2).find('input').val();
			}
			if($jq(this).find('td').eq(3).find('select').val()!='Other'){
				valueJson['toPlace']=$jq(this).find('td').eq(3).find('select').val();
			}else {
				valueJson['toPlace']=$jq(this).find('td').eq(3).find('input').val();
			}
			valueJson['days']=$jq(this).find('td').eq(4).find('input').val();
			valueJson['modeOfConveyance']=$jq(this).find('td').eq(5).find('select').val();
			valueJson['classOfEntitlement']=$jq(this).find('td').eq(6).find('select').val();
			valueJson['tatkalQuota']=$jq(this).find('td').eq(7).find('select').val();
			reqJourneyDetailsJson[i]=valueJson;
			reqJourneyDetailsArry[i]=valueJson;
			i++;
		});
		mainJson["reqJourneyDetails"]=reqJourneyDetailsJson;
		if($jq('input:radio[name=amendmentType]:checked').val()=='amendment' || $jq('input:radio[name=amendmentType]:checked').val()=='cancel'){
			amendmentType += "tadaApproval";
			requestId=tadaRequestBean.requestId;
		}
		if($jq('input:radio[name=authorizedMove]:checked').val()=='2'){
			if($jq('#projDirName').is(':visible'))
				projDirName=$jq('#projDirName').val();
			else
				projDirName=tadaRequestBean.projDirName;
		}
		var stayDuration='';
		var tdWorkPlace='';
		var workingPlace='';
		var purpose='';
		var authorizedMove='';
		var daType='';
		var remarks='';
		if($jq('input:radio[name=amendmentType]:checked').val()=='cancel'){
			stayDuration=0;
			tdWorkPlace=tadaRequestBean.tdWorkPlace;
			workingPlace=tadaRequestBean.workingPlace;
			purpose=tadaRequestBean.purpose;
			authorizedMove=tadaRequestBean.authorizedMove;
			daType=tadaRequestBean.daType;
			entitlementExemption=tadaRequestBean.entitleExemption;
			remarks=$jq('#remarks').val();
			if(!isNaN($jq('#ticketCancelCharges').val()) && (parseFloat($jq('#ticketCancelCharges').val())>0)){
				ticketCancelCharges=$jq('#ticketCancelCharges').val();
				ticketCancelReason=$jq('#ticketCancelReason').val();
			}
		}else{
			stayDuration=$jq('#stayDuration').val();
			//tdWorkPlace=$jq('#tdPlace').val()+'###'+$jq('#tdWorkPlace').val();
			tdWorkPlace=$jq('#tdWorkPlace').val();
			workingPlace=$jq('#workingPlace').val();
			purpose=$jq('#purpose').val();
			authorizedMove=$jq('input:radio[name=authorizedMove]:checked').val();
			daType=$jq('input:radio[name=tadaRequirement]:checked').val();
			entitlementExemption=$jq('input:radio[name=entitlementExemption]:checked').val();
			if($jq('#remarks').is(':visible')){
				remarks=$jq('#remarks').val();
			}
			if($jq('#ticketCancelCharges').is(':visible')){
				if(!isNaN($jq('#ticketCancelCharges').val()) && (parseFloat($jq('#ticketCancelCharges').val())>0)){
					ticketCancelCharges=$jq('#ticketCancelCharges').val();
					ticketCancelReason=$jq('#ticketCancelReason').val();
				}
			}
		}
		var tempDeptDate=reqJourneyDetailsArry[0].departueDate;
		var tempArrDate=reqJourneyDetailsArry[0].arrivalDate;
		for(var i=0;i<reqJourneyDetailsArry.length;i++){
			if((parseFloat(compareDates(convertDate(tempDeptDate),convertDate(reqJourneyDetailsArry[i].departueDate)))+1)<0){
				tempDeptDate=reqJourneyDetailsArry[i].departueDate;
			}
			if((parseFloat(compareDates(convertDate(tempArrDate),convertDate(reqJourneyDetailsArry[i].arrivalDate)))+1)>0){
				tempArrDate=reqJourneyDetailsArry[i].arrivalDate;
			}
		}
		var requestDetails = {
				"nodeID" : nodeID,
				"stayDuration" : stayDuration,
				"tdWorkPlace" : tdWorkPlace,
				"workingPlace" : workingPlace,
				"purpose" : purpose,
				"authorizedMove" : authorizedMove,
				"daType" : daType,
				"entitlementExemption" : entitlementExemption,
				"projDirName" : projDirName,
				"type" :"tadaApproval",
				"jsonValue" : JSON.stringify(mainJson),
				"amendmentReqId" : requestId,
				"amendmentType" :amendmentType,
				"leaveId" : selectedValues,
				"reason" : remarks,
				"departureDate" : tempDeptDate,
				"arrivalDate" : tempArrDate,
				"ticketCancelCharges" : ticketCancelCharges,
				"ticketCancelReason" : ticketCancelReason,
				"param" : "submitRequest"
			};
			$jq.ajax( {
				type : "POST",
				url : "tadaWaterRequest.htm?"+dispUrl,
				data : requestDetails,
				cache : false,
				success : function(html) {
					$jq('#result').html(html);
					if ($jq('.success').is(':visible')) {
						if($jq('input:radio[name=amendmentType]').is(':visible'))
							$jq('input:radio[name=amendmentType]').attr('disabled','disabled');
						var check=confirm(" TADA Request has been Successfully Sent...\nPreview TADA Application Form & print from here");
						if(check){
						document.forms[0].requestId.value = $jq.trim(requestIDtadaA);
						
					   document.forms[0].param.value = "requestDetails";
						document.forms[0].action = "workflowmap.htm";
						document.forms[0].submit();	
						}
						//clearTdApproval();
					}
					if($jq('.TD request is already applied').is(':visible')){
						clearTdApproval();
					}
				}
			});

	} else {
		alert(errorMessage);
	}

	
	
	
	
	
}



