var OtherRuleFlag = "No";
var doGlobal;
var nodeID = 0;
var retirementDate = '';
var currentNextYearBalance = '';
var futureAvailableLeaves = '';





//newly added by bkr


function leaveErpApplicationForm(requestId){
	
	//alert("welcome");
	//ajax call from leave form 
	var status =true;
	if(status) {
		var requestDetails = {
				"requestID" :requestId,
			};
		$jq.ajax( {
			type : "POST",
			url : "hello.htm",
			data : requestDetails,
			cache : false,
			success : function(html) {
			/*$jq("#dateCheck").html(html);
				if(html.dateCheck==='NOTEligible'){
					alert("Sorry! You Are Not Eligible For This Leave");
					//$jq('#leaveType').val('----Select----');
					
				}*/
			}
		});
	}

}

function erpabc(){
	alert("welcome");
	//window.open("./leaveRequest.htm?param=getPreccriptionFile,'preview','fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no')
}

function ShowLeavesList(target) {
	//show div
	document.getElementById(target).style.display = 'block';
}

function thisLeaveAvailOrNot(){
	
	//ajax call from leave form 
	var status =true;
	var leaveType=$jq('#leaveType').val();
	if(status) {
		var requestDetails = {
				"leaveType" :leaveType,
			};
		$jq.ajax( {
			type : "POST",
			url : "thisLeaveAvailableOrNotForU.htm",
			data : requestDetails,
			cache : false,
			success : function(html) {
			$jq("#dateCheck").html(html);
				if(html.dateCheck==='NOTEligible'){
					alert("Sorry! You Are Not Eligible For This Leave");
					$jq('#leaveType').val('----Select----');
					
				}
			}
		});
	}

	
}



function ERPleaveAmendmentApplicationForm(noOfDays,leaveType,param,requestId){
	
	
	var noOfDays=noOfDays;
	var leaveType=leaveType;
	var param="ApplicationAmendFormHome";
	var requestId= requestId;
	
	//alert("amendment");
	document.forms[0].noOfDays.value =noOfDays;
	document.forms[0].leaveType.value =leaveType;
	document.forms[0].requestId.value =requestId;
	document.forms[0].param.value = "ApplicationAmendFormHome";
	document.forms[0].action = "leaveWaterRequest.htm?";
	document.forms[0].submit();
}

function submitErpLeaveCancelApplication(noOfDays,leaveType,param,requestId){
	var status = true;
	var errorMessage = "";
	var flag = true;
	var reasonCancellation=$jq('#reasonCancellation').val();

	if(reasonCancellation===''){
		errorMessage += "Please Enter Reason for Cancellation\n";
		if(status){
		$jq('#reasonCancellation').focus();
		}
		status = false;
	}
	if(status){

		var requestDetails = {

					"leaveType" :leaveType,
					"cancelRequestId" :requestId,
					"noOfDays" :noOfDays,
					"reasonCancellation" :reasonCancellation,
					"param" : "ErpLeaveCancellation",
					"type" :"ERP Leave Cancel"			
			};
		$jq.ajax( {
			type : "POST",
			url : "leaveWaterRequest.htm?",
			data : requestDetails,
			cache : false,
			success : function(html) {
			$jq('#result').html(html);
			
			if ($jq('.success').is(':visible')) {
					var check=confirm("Leave Cancellation Application Sent Sucessfully...");
					if(check){
						document.forms[0].cancelRequestId.value = $jq.trim(copyrequestIDleaveA);
						document.forms[0].requestId.value = $jq.trim(requestIDleaveA);
						document.forms[0].param.value = "erpLeaveApplyDetails";
						document.forms[0].action = "workflowmap.htm";
						document.forms[0].submit();	
					}else{
						clearErpLeaveApplication()
					}
				}
				
			}
		});
	}else{
		alert(errorMessage);
	}

}

function ERPleaveCancelApplicationForm(noOfDays,leaveType,param,requestId){
	var noOfDays=noOfDays;
	var leaveType=leaveType;
	var param="ApplicationCancelFormHome";
	var requestId= requestId;
	
	document.forms[0].noOfDays.value =noOfDays;
	document.forms[0].leaveType.value =leaveType;
	document.forms[0].requestId.value =requestId;
	document.forms[0].param.value = "ApplicationCancelFormHome";
	document.forms[0].action = "leaveWaterRequest.htm?";
	document.forms[0].submit();
	
}

function docterCertificateDeatils(){
	
	var leaveType=$jq('#leaveType').val();
	if(leaveType==='SL' || leaveType==='ML'){
		document.getElementById('prescription1').checked='checked';
		$jq('#tddata').css('display', 'block');
		$jq('#tddata1').css('display', 'block');
		$jq('#tddata4').css('display', 'block');
		$jq('#tddata3').css('display', 'block');
		
	}else{
		document.getElementById('prescription1').checked='unchecked';
		$jq('#tddata').css('display', 'none');
		$jq('#tddata1').css('display', 'none');
		$jq('#tddata4').css('display', 'none');
		$jq('#tddata3').css('display', 'none');
	}
}

function prescriptionBrowse(SOH){
	if(SOH=='show'){

	$jq('#tddata').css('display', 'block');
	$jq('#tddata1').css('display', 'block');
		
	}else{
		$jq('#tddata').css('display', 'none');
		$jq('#tddata1').css('display', 'none');
	}
}

function checkAppliedDays(){
	var leaveType=$jq('#leaveType').val();
	var fromDate=$jq('#fromDate').val();
	var toDate=$jq('#toDate').val();
	if(fromDate!=='' && toDate!=='' && leaveType!=='NS'){
		var noOfDays=$jq('#noOfDays').val();
		if(noOfDays>=0){
		//alert(" checkAppliedDays function");
		var requestDetails = {
				"leaveType" : leaveType,
				"fromDate" :fromDate,
				"toDate" : toDate,
				"noOfDays" : noOfDays
			};
		
		$jq.ajax( {
			type : "POST",
			url : "searchNoOfLeaveDays.htm",
			data : requestDetails,
			cache : false,
			success : function(html) {
			$jq("#dateCheck").html(html);
			
				if(html.dateCheck==='notvalid'){
				//	console.log("not valid");
					alert("Please Check Your Previous Applied Leave Dates");
					$jq('#toDate').val('');
					$jq('#noOfDays').val('');
				}
				if(html.type==='notvalid'){
					alert("Sorry! You don't have enough balance in your account");
					$jq('#toDate').val('');
					$jq('#noOfDays').val('');
				}
			
			}
		});
		}
	}
}

function ErpLeaveCancel(noOfDays,leaveType,param,requestId){
	var flag=confirm("Are You Sure To Cancel Leave ?");
	var noOfDays=noOfDays;
	var leaveType=leaveType;
	var param=param;
	var requestId= requestId;
	if(flag){
		var requestDetails = {
				"noOfDays" :noOfDays,
				"leaveType" : leaveType,
				"param" : param,
				"requestId" : requestId
			};
		$jq.ajax( {
			type : "POST",
			url : "leaveWaterRequest.htm",
			data : requestDetails,
			cache : false,
			success : function(html) {
				$jq('#result').html(html);
				if ($jq('.success').is(':visible')) {
					alert("Leaves Cancelled Sucesfully..");
					document.forms[0].param.value = "ApplicationFormHome";
					document.forms[0].action = "leaveWaterRequest.htm";
					document.forms[0].submit();
					}
			}
		});
	}
}

function todateChecking(){
	//ajax call from leave form 
	var status =true;
	var fromDate=$jq('#fromDate').val();
	var toDate=$jq('#toDate').val();
	var amendRequestId=$jq('#amendRequestId').val();
	if(status) {
		var requestDetails = {
				"amendRequestId" : amendRequestId,
				"toDate" :toDate,
				"param" : "leaveDateExistOrNot"
			};
		
		$jq.ajax( {
			type : "POST",
			url : "searchLeaveToDateCheck.htm",
			data : requestDetails,
			cache : false,
			success : function(html) {
			$jq("#dateCheck").html(html);
				if(html.dateCheck==='NotVaild'){
				//	console.log("not valid");
					alert("Already other leave is applied in these dates, please try to apply other dates");
					$jq('#toDate').val('');
					$jq('#noOfDays').val('');
				}
			}
		});
	}
}

function erpPrescriptionDownloded(){
	alert("downloded.......");
	var status =true;
	if(status) {
		var requestDetails = {
				"param" : "leaveDateExistOrNot"
			};
		
		$jq.ajax( {
			type : "POST",
			url : "downlodedPrescriptionCopy.htm",
			data : requestDetails,
			cache : false,
			success : function(html) {
			$jq("#dateCheck").html(html);
				if(html.dateCheck==='NotVaild'){
				//	console.log("not valid");
					alert("Already other leave is applied in these dates, please try to apply other dates");
					$jq('#toDate').val('');
					$jq('#noOfDays').val('');
				}
			}
		});
	}
	
}

function dateChecking(){
	//ajax call from leave form 
	var status =true;
	var fromDate=$jq('#fromDate').val();
	//added by amendRequestID for amand purpose
	var amendRequestId=$jq('#amendRequestId').val();
	if(status) {
		var requestDetails = {
				"amendRequestId" : amendRequestId,
				"fromDate" :fromDate,
				"param" : "leaveDateExistOrNot"
			};
		$jq.ajax( {
			type : "POST",
			url : "searchLeaveFromDateCheck.htm",
			data : requestDetails,
			cache : false,
			success : function(html) {
			$jq("#dateCheck").html(html);
				if(html.dateCheck==='NotVaild'){
					alert("Already other leave is applied in these dates., please try to apply other dates");
					$jq('#fromDate').val('');
					$jq('#noOfDays').val('');
				}
			}
		});
	}
}

function emptyDateFileds(){
	//alert("krishna");
	$jq('#fromDate').val('');
	$jq('#toDate').val('');
	$jq('#noOfDays').val('');
	
}
function submitErpEnterAvailableLeaves(){
	var status = true;
	var errorMessage = "";
	var flag = true;
	var entrySfid=$jq('#entrySfid').val();
	var annualLeaves=$jq('#annualLeaves').val();
	var sickLeaves=$jq('#sickLeaves').val();
	var maternityLeaves=$jq('#maternityLeaves').val();
	var peternityLeaves=$jq('#peternityLeaves').val();
	if(entrySfid==='' ){
		errorMessage += "Please Enter Employee ID\n";
		if(status){
		$jq('#entrySfid').focus();
		}
		status = false;
	}
	if(annualLeaves===''){
		errorMessage += "Please Enter No.Of Annual Leaves\n";
		if(status){
		$jq('#annualLeaves').focus();
		}
		status = false;
	}
	if(sickLeaves===''){
		errorMessage += "Please Enter No.of Sick Leaves\n";
		if(status){
		$jq('#sickLeaves').focus();
		}
		status = false;
	}
	if(maternityLeaves===''){
		errorMessage += "Please Enter No.of Maternity Leaves\n";
		if(status){
		$jq('#maternityLeaves').focus();
		}
		status = false;
	}
	if(peternityLeaves===''){
		errorMessage += "Please Enter No.of Peternity Leaves \n";
		if(status){
		$jq('#peternityLeaves').focus();
		}
		status = false;
	}
	if(entrySfid.length < 4){
		errorMessage += "Please Enter Employee ID minimum 6 Characters \n";
		if(status){
		$jq('#peternityLeaves').focus();
		}
		status = false;
	}
	if(status){
		var requestDetails = {
					"entrySfid" :entrySfid,
					"peternityLeaves" :peternityLeaves,
					//"requestID" :requestID,
					//"requestId" :requestId,
					"maternityLeaves" :maternityLeaves,
					"sickLeaves" :sickLeaves,
					"annualLeaves" :annualLeaves,
					"param" : "ErpEmpLeavesEntrySave",
					"type" :"ERP Leave"			
			};
		$jq.ajax( {
			type : "POST",
			url : "leaveAdmin.htm?",
			data : requestDetails,
			cache : false,
			success : function(html) {
			$jq('#result').html(html);
			if ($jq('.success').is(':visible')) {
				alert("Leaves Enter Sucesfully..");
				document.forms[0].param.value = "dataErpEntryHome";
				document.forms[0].action = "leaveAdmin.htm";
				document.forms[0].submit();
				}
			}
		});
	}else{
		alert(errorMessage);
	}
}
function resetErpEnterAvailableLeaves(){
	$jq('#entrySfid').val('');
	$jq('#annualLeaves').val('');
	$jq('#sickLeaves').val('');
	$jq('#maternityLeaves').val('');
	$jq('#peternityLeaves').val('');
}

function fillErpAvailableLeaves(sfidValue) {
	if (sfidValue != '' && sfidValue.length >= 4) {
		leavesExists = false;
		if (JsonErpAvailabeLeavesList != null) {
			for (var i = 0; i < JsonErpAvailabeLeavesList.length; i++) {
				if (JsonErpAvailabeLeavesList[i].sfID == sfidValue.toUpperCase()) {
					if (JsonErpAvailabeLeavesList[i].verifiedBy == "") {
						leavesExists = true;
		
					} else {
						$jq('#entrySfid').val('');
						alert("Already entry Completed");
						return;
					}				
				}
			}
		}
	}
}

function submitErpLeaveApplication(){
	
	//alert($jq('#amendRequestId').val());
	
	
	var files=$jq('#files').val();
	//alert("files    "+files);
	$jq('#prescriptiondoc').val(files);
	
	
	var prescriptionYorN="";
	
	if(document.getElementById('prescription1')!= null && document.getElementById('prescription1').checked==true){
		prescriptionYorN=$jq('#prescription1').val();
	}
	if(document.getElementById('prescription2')!= null &&  document.getElementById('prescription2').checked==true){
		prescriptionYorN=$jq('#prescription2').val();
	}
	var status = true;
	var errorMessage = "";
	var flag = true;
	var amendRequestId=$jq('#amendRequestId').val();
	if(amendRequestId===''){
		amendRequestId='';
		
	}
	
	var leaveType=$jq('#leaveType').val();
	//alert("leaveType    "+leaveType);
	var fromDate=$jq('#fromDate').val();
	var toDate=$jq('#toDate').val();
	var noOfDays=$jq('#noOfDays').val();
	var reason=$jq('#reason').val();
	var contactNo=$jq('#contactNo').val();
	if(leaveType==="NS"){
		errorMessage += "Please Select Nature of Leave.\n";
		if(status){
		$jq('#leaveType').focus();
		}
		status = false;
	}
	if(fromDate===''){
		errorMessage += "Please Enter From Date\n";
		if(status){
		$jq('#fromDate').focus();
		}
		status = false;
	}
	if(toDate===''){
		errorMessage += "Please Enter To Date\n";
		if(status){
		$jq('#toDate').focus();
		}
		status = false;
	}

	if(reason===''){
		errorMessage += "Please Enter Reason for Leave\n";
		if(status){
		$jq('#reason').focus();
		}
		status = false;
	}
	if(contactNo===''){
		errorMessage += "Please Enter Contact Number\n";
		if(status){
		$jq('#contactNo').focus();
		}
		status = false;
	}
	if(noOfDays!==''){
		if(noOfDays<0){
			errorMessage += "Please Enter ToDate Greater Than from Date\n";
			if(status){
				$jq('#fromDate').focus();
				}
				status = false;
		}
	}
	
	if(status){

		var requestDetails = {

					"leaveType" :leaveType,
					"fromDate" :fromDate,
					"amendRequestId" : amendRequestId,
					"prescriptiondoc" : prescriptiondoc,
					"prescriptionYorN" : prescriptionYorN,
					//"requestID" :requestID,
					//"requestId" :requestId,
					"toDate" :toDate,
					"noOfDays" :noOfDays,
					"reason" :reason,
					"contactNo" :contactNo,
					"param" : "ErpLeaveSave",
					"type" :"ERP Leave"			
			};
		
		
		$jq('#param').val('ErpLeaveSave');
		$jq('#type').val('ERP Leave');
		$jq('#leaveTypeCopy').val(leaveType); 
		//$jq('#leaveType').val(leaveType); 
		
		$jq.ajaxFileUpload(
				{
					url:"leaveWaterRequest.htm?"+ $jq('#LeaveWaterRequest').serialize(),
					secureuri:false,
					fileElementId :'files',
					// sync: true,
					// async: false,
					success: function (data, status) {
					if(status=='success'){
				   $jq("#result").html($jq(data.body).html());
				   var check=confirm("Leave Application Sent Sucessfully...");
					if(check){
						document.forms[0].requestId.value = $jq.trim(requestIDleaveA);
						document.forms[0].param.value = "erpLeaveApplyDetails";
						document.forms[0].action = "workflowmap.htm";
						document.forms[0].submit();	
					}else{
						clearErpLeaveApplication();
					}
					 }
					// alert('success');
					}, error: function (data, status, e) {
					// when failure
					status=false; }
					}); 
		
		
		
	}else{
		alert(errorMessage);
	}
}
function clearErpLeaveApplication(){
	
$jq('#leaveType').val('----Select----');
$jq('#fromDate').val('');
$jq('#toDate').val('');
$jq('#noOfDays').val('');
$jq('#reason').val('');
$jq('#contactNo').val('');
}

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




function submitLeaveDays(){
	
	var status = true;
	var errorMessage = "";
	var flag = true;
	
		if($jq('input:radio[id=male1]').is(':checked')){
			annualLeaveGender = $jq('#male1').val();
		} 
		if($jq('input:radio[id=female1]').is(':checked')){
			annualLeaveGender=$jq('#female1').val();
		}
		if($jq('input:radio[id=all1]').is(':checked')){
			annualLeaveGender=$jq('#all1').val();
		}
		
		if($jq('input:radio[id=male2]').is(':checked')){
			sickLeaveGender = $jq('#male2').val();
		} 
		if($jq('input:radio[id=female2]').is(':checked')){
			sickLeaveGender=$jq('#female2').val();
		}
		if($jq('input:radio[id=all2]').is(':checked')){
			sickLeaveGender=$jq('#all2').val();
		}
	var leaveYear=$jq('#leaveYear').val();
	
	var annualLeavePermanent=$jq('#annualLeavePermanent').val();
	var annualLeaveContract=$jq('#annualLeaveContract').val();
	
	var sickLeavePermanent=$jq('#sickLeavePermanent').val();
	var sickLeaveContract=$jq('#sickLeaveContract').val();
	
	
	var maternityLeavePermanent=$jq('#maternityLeavePermanent').val();
	var maternityLeaveContract=$jq('#maternityLeaveContract').val();
	
	
	var paternityLeavePermanent=$jq('#paternityLeavePermanent').val();
	var paternityLeaveContract=$jq('#paternityLeaveContract').val();
	
	if ($jq('#leaveYear').val()=='0') {
		errorMessage += "Please Enter leaveYear.\n";
		if(status){
		$jq('#leaveYear').focus();
		}
		status = false;
	}
	
	
	if ($jq('#annualLeavePermanent').val()=='') {
		errorMessage += "Please Enter annualLeavePermanent.\n";
		if(status){
		$jq('#annualLeavePermanent').focus();
		}
		status = false;
	}
	if ($jq('#annualLeaveContract').val()=='') {
		errorMessage += "Please Enter annualLeaveContract.\n";
		if(status){
		$jq('#annualLeaveContract').focus();
		}
		status = false;
	}
	
	
	
	if ($jq('#sickLeavePermanent').val()=='') {
		errorMessage += "Please Enter sickLeavePermanent.\n";
		if(status){
		$jq('#sickLeavePermanent').focus();
		}
		status = false;
	}
	if ($jq('#sickLeaveContract').val()=='') {
		errorMessage += "Please Enter sickLeaveContract.\n";
		if(status){
		$jq('#sickLeaveContract').focus();
		}
		status = false;
	}
	
	

	if ($jq('#maternityLeavePermanent').val()=='') {
		errorMessage += "Please Enter maternityLeavePermanent.\n";
		if(status){
		$jq('#maternityLeavePermanent').focus();
		}
		status = false;
	}
	if ($jq('#maternityLeaveContract').val()=='') {
		errorMessage += "Please Enter maternityLeaveContract.\n";
		if(status){
		$jq('#maternityLeaveContract').focus();
		}
		status = false;
	}
	
	

	if ($jq('#paternityLeavePermanent').val()=='') {
		errorMessage += "Please Enter paternityLeavePermanent.\n";
		if(status){
		$jq('#paternityLeavePermanent').focus();
		}
		status = false;
	}
	if ($jq('#paternityLeaveContract').val()=='') {
		errorMessage += "Please Enter paternityLeaveContract.\n";
		if(status){
		$jq('#paternityLeaveContract').focus();
		}
		status = false;
	}
	//status = false;
	
	if(status){
		var requestDetails = {

					"annualLeavePermanent" :annualLeavePermanent,
					"annualLeaveContract" :annualLeaveContract,
					//"requestID" :requestID,
					//"requestId" :requestId,
					"annualLeaveGender" :annualLeaveGender,
					"sickLeavePermanent" :sickLeavePermanent,
					"sickLeaveContract" :sickLeaveContract,
					"sickLeaveGender" :sickLeaveGender,
					"maternityLeavePermanent" :maternityLeavePermanent,
					"maternityLeaveContract" :maternityLeaveContract,
					"maternityLeaveGender" :"F",
					"paternityLeavePermanent" :paternityLeavePermanent,
					"paternityLeaveContract" :paternityLeaveContract,
					"paternityLeaveGender" :"M",
					"leaveYear" :leaveYear,
					"param" : "leaveTypeDaysSave",
					"type" :"leaveDaysEntry"			
			};
		$jq.ajax( {
			type : "POST",
			url : "leaveWaterRequest.htm?",
			data : requestDetails,
			cache : false,
			success : function(html) {
				$jq('#result').html(html);
				if ($jq('.success').is(':visible')) {
					var check=confirm("Leave days Entered Sucessfully...");
					if(check){
							//alert("inserted");
						document.forms[0].param.value = "LeaveWaterDaysEntry";
						document.forms[0].action = "leaveWaterRequest.htm";
						document.forms[0].submit();
						
					}else{
						clearLeaveDays();
					}
				}
				
			}
		
		
		});
		}else{
			alert(errorMessage);
		}
	
}

function clearLeaveDays(){
	//alert("clear function");
	
$jq('#leaveYear').val('----SELECT----');
$jq('#annualLeavePermanent').val('0');
$jq('#annualLeaveContract').val('0');
$jq('#sickLeavePermanent').val('0');
$jq('#sickLeaveContract').val('0');
$jq('#maternityLeavePermanent').val('0');
$jq('#maternityLeaveContract').val('0');
$jq('#paternityLeavePermanent').val('0');
$jq('#paternityLeaveContract').val('0');
document.getElementById('all1').checked = true;
document.getElementById('all2').checked = true;
document.getElementById('female3').checked = true;
document.getElementById('male4').checked = true;
	
	
}




/////////

function selectLeaveType() {
	if ($jq('#leaveTypeId option:selected').val() != 'select') {
		$jq('#param').val('getLeaveDetails');
		$jq.post('leave.htm', $jq('#LeaveManagementBean').serialize(), function(html) {
			$jq("#result").html(html);
		});
	} else {
		$jq("#result").html('');
		alert("Please select leave type\n");
	}
}

function showTypeIdDetails() {
	$jq('#result1').html('');
	$jq('#param').val('getLeavesubtypeDetailslist');
	$jq.post('leave.htm', $jq('#LeaveManagementBean').serialize(), function(html) {
		$jq("#contentpage").html(html);
	});
}

function loadLeaveMappings()
{
	var tempJSON={};
	for(var i=1;i<=16;i++)
	{
		switch(i)
		{
		case 1:
				var exceptionJSON={};
				 exceptionJSON['exp']="EL";
				 exceptionJSON['expID']=i;
				 tempJSON[i]=exceptionJSON;
				 break;
		case 2:
				var exceptionJSON={};
				exceptionJSON['exp']="HPL";
				exceptionJSON['expID']=i;
				tempJSON[i]=exceptionJSON;
				break;
		case 3:
				var exceptionJSON={};
				 exceptionJSON['exp']="CL";
				 exceptionJSON['expID']=i;
				 tempJSON[i]=exceptionJSON;
				 break;
		case 4:
				var exceptionJSON={};
				exceptionJSON['exp']="CML";
				exceptionJSON['expID']=i;
				tempJSON[i]=exceptionJSON;
				break;
		case 5:
				var exceptionJSON={};
				 exceptionJSON['exp']="LND";
				 exceptionJSON['expID']=i;
				 tempJSON[i]=exceptionJSON;
				 break;
		case 6:
				var exceptionJSON={};
				exceptionJSON['exp']="EOL";
				exceptionJSON['expID']=i;
				tempJSON[i]=exceptionJSON;
				break;
		
		case 8:
				var exceptionJSON={};
				exceptionJSON['exp']="PL";
				exceptionJSON['expID']=i;
				tempJSON[i]=exceptionJSON;
				break;
		case 9:
				var exceptionJSON={};
				 exceptionJSON['exp']="CCL";
				 exceptionJSON['expID']=i;
				 tempJSON[i]=exceptionJSON;
				 break;
		case 10:
				var exceptionJSON={};
				exceptionJSON['exp']="SL";
				exceptionJSON['expID']=i;
				tempJSON[i]=exceptionJSON;
				break;
		case 11:
				var exceptionJSON={};
				 exceptionJSON['exp']="SPL";
				 exceptionJSON['expID']=i;
				 tempJSON[i]=exceptionJSON;
				 break;
		case 12:
				var exceptionJSON={};
				exceptionJSON['exp']="PREL";
				exceptionJSON['expID']=i;
				tempJSON[i]=exceptionJSON;
				break;
		case 13:
				var exceptionJSON={};
				 exceptionJSON['exp']="MISL";
				 exceptionJSON['expID']=i;
				 tempJSON[i]=exceptionJSON;
				 break;
		case 14:
				var exceptionJSON={};
				exceptionJSON['exp']="AL";
				exceptionJSON['expID']=i;
				tempJSON[i]=exceptionJSON;
				break;
		case 15:
			var exceptionJSON={};
			 exceptionJSON['exp']="EOLWOMC";
			 exceptionJSON['expID']=i;
			 tempJSON[i]=exceptionJSON;
			 break;
		case 16:
			var exceptionJSON={};
			 exceptionJSON['exp']="ALM";
			 exceptionJSON['expID']=i;
			 tempJSON[i]=exceptionJSON;
			 break;
		} 
		
	}
	if (LeaveRelationMappingListJson != null) {
		for ( var i = 0; i < LeaveRelationMappingListJson.length; i++) {
			
			var temp=tempJSON[LeaveRelationMappingListJson[i].key].exp;
			var temp1=LeaveRelationMappingListJson[i].name;
			$jq('#'+temp+temp1).attr('checked',true)
			
		}
	}
}

function submitLeaveMappingDetails()
{
	var tempJSON={};
	for(var i=1;i<=16;i++)
	{
		switch(i)
		{
		case 1:
				var exceptionJSON={};
				 exceptionJSON['exp']="EL";
				 exceptionJSON['expID']=i;
				 tempJSON[i]=exceptionJSON;
				 break;
		case 2:
				var exceptionJSON={};
				exceptionJSON['exp']="HPL";
				exceptionJSON['expID']=i;
				tempJSON[i]=exceptionJSON;
				break;
		case 3:
				var exceptionJSON={};
				 exceptionJSON['exp']="CL";
				 exceptionJSON['expID']=i;
				 tempJSON[i]=exceptionJSON;
				 break;
		case 4:
				var exceptionJSON={};
				exceptionJSON['exp']="CML";
				exceptionJSON['expID']=i;
				tempJSON[i]=exceptionJSON;
				break;
		case 5:
				var exceptionJSON={};
				 exceptionJSON['exp']="LND";
				 exceptionJSON['expID']=i;
				 tempJSON[i]=exceptionJSON;
				 break;
		case 6:
				var exceptionJSON={};
				exceptionJSON['exp']="EOL";
				exceptionJSON['expID']=i;
				tempJSON[i]=exceptionJSON;
				break;
		case 15:
				var exceptionJSON={};
				 exceptionJSON['exp']="EOLWOMC";
				 exceptionJSON['expID']=i;
				 tempJSON[i]=exceptionJSON;
				 break;
		case 8:
				var exceptionJSON={};
				exceptionJSON['exp']="PL";
				exceptionJSON['expID']=i;
				tempJSON[i]=exceptionJSON;
				break;
		case 9:
				var exceptionJSON={};
				 exceptionJSON['exp']="CCL";
				 exceptionJSON['expID']=i;
				 tempJSON[i]=exceptionJSON;
				 break;
		case 10:
				var exceptionJSON={};
				exceptionJSON['exp']="SL";
				exceptionJSON['expID']=i;
				tempJSON[i]=exceptionJSON;
				break;
		case 11:
				var exceptionJSON={};
				 exceptionJSON['exp']="SPL";
				 exceptionJSON['expID']=i;
				 tempJSON[i]=exceptionJSON;
				 break;
		case 12:
				var exceptionJSON={};
				exceptionJSON['exp']="PREL";
				exceptionJSON['expID']=i;
				tempJSON[i]=exceptionJSON;
				break;
		case 13:
				var exceptionJSON={};
				 exceptionJSON['exp']="MISL";
				 exceptionJSON['expID']=i;
				 tempJSON[i]=exceptionJSON;
				 break;
		case 14:
				var exceptionJSON={};
				exceptionJSON['exp']="AL";
				exceptionJSON['expID']=i;
				tempJSON[i]=exceptionJSON;
				break;
		case 16:
			var exceptionJSON={};
			exceptionJSON['exp']="ALM";
			exceptionJSON['expID']=i;
			tempJSON[i]=exceptionJSON;
			break;
		} 
		
	}
	
	var mainjson={};
	var counter=0;
	for(var j=1;j<=16;j++){
		if(tempJSON[j]!=null){
			var strID=tempJSON[j].exp;
			for(var k=1;k<=16;k++){
			
				if($jq('#'+strID+k).is(':checked')==true)
				{
					var temp={};
					temp['map1']=tempJSON[j].expID;
					temp['map2']=$jq('#'+strID+k).val();
					mainjson[counter]=temp;
					counter=counter+1;
				}
			}
		}
		
	}
	
	var stringjson=JSON.stringify(mainjson);
	$jq('#exceptionsJson').val(stringjson);
	$jq('#param').val('insertMappingDetails');
	$jq.post('leave.htm', $jq('#LeaveManagementBean').serialize(),
					function(html) {
								
					$jq("#result1").html(html);
					
					});
}

function submitspclDetails() {
	var errorMessage = "";
	var status = true;
	var mainJSON = {};
	if ($jq('#leaveSubType option:selected').val() == 'select') {
		errorMessage += "Please select leave sub-type\n";
		if (status) {
			$jq('#leaveSubType').focus();
			status = false;
		}
	} else if ($jq('#leaveSubType option:selected').val() == 'new') {
		if ($jq('#specialLeaveDesc').val() == '') {
			errorMessage += "Please enter new leave sub-type\n";
			if (status) {
				$jq('#specialLeaveDesc').focus();
				status = false;
			}
		} else if ($jq('#categoryType2').is(':checked') == true) {
			if (specialLeavesListJson != null) {
				for ( var i = 0; i < specialLeavesListJson.length; i++) {
					if (specialLeavesListJson[i].key == 'T2' && specialLeavesListJson[i].name.toLowerCase() == $jq('#specialLeaveDesc').val().toLowerCase() && $jq('#fromDate').val() == specialLeavesListJson[i].value && $jq('#toDate').val() == specialLeavesListJson[i].flag) {
						errorMessage += "New leave sub type is already exist\n";
						if (status) {
							$jq('#specialLeaveDesc').focus();
							status = false;
						}
					}
				}
			}
		} else if ($jq('#categoryType1').is(':checked') == true) {
			if (specialLeavesListJson != null) {
				for ( var i = 0; i < specialLeavesListJson.length; i++) {
					if (specialLeavesListJson[i].key == 'T1' && specialLeavesListJson[i].name.toLowerCase() == $jq('#specialLeaveDesc').val().toLowerCase()) {
						errorMessage += "New leave sub type is already exist\n";
						if (status) {
							$jq('#specialLeaveDesc').focus();
							status = false;
						}
					}
				}
			}
		}
	}
	
	if ($jq('#eligiblityFlag1').is(':checked') == false && $jq('#eligiblityFlag2').is(':checked') == false && $jq('#eligiblityFlag3').is(':checked') == false) {
		errorMessage += "Please select eligiblity\n";
		if (status) {
			$jq('#eligiblityFlag1').focus();
			status = false;
		}
	}
	
	if ($jq('#noofdays').val() == '') {
		errorMessage += "Please enter no of days\n";
		if (status) {
			$jq('#noofdays').focus();
			status = false;
		}
	}
	
	if ($jq('#categoryType2').is(':checked') == true) {
		if ($jq('#fromDate').val() == '') {
			errorMessage += "Please select from date\n";
			if (status) {
				$jq('#fromDate').focus();
				status = false;
			}
		}
		
		if ($jq('#toDate').val() == '') {
			errorMessage += "Please select to date\n";
			if (status) {
				$jq('#toDate').focus();
				status = false;
			}
		}
	}
	
	if ($jq('#priorApprovalFlag1').is(':checked') == false && $jq('#priorApprovalFlag2').is(':checked') == false ) {
		errorMessage += "Please select prior approval\n";
		if (status) {
			$jq('#priorApprovalFlag1').focus();
			status = false;
		}
	} else if ($jq('#priorApprovalFlag1').is(':checked') == true) {
		 var priorApprovalJSON = {};
		 var dynRow = document.getElementById("priorapporval");
		 var length = dynRow.rows.length;
		 for (i = 1; i < length; i++) {
			 if (dynRow.rows[i].cells[1].childNodes[0].value != "") {
				 var exceptionJSON = {};
				 exceptionJSON['exp'] = dynRow.rows[i].cells[1].childNodes[0].value;
				 exceptionJSON['expID'] = JSONExceptionDetailsList['PRIOR_APPR'];
				 priorApprovalJSON[i-1] = exceptionJSON;
			 }
		 }
		 mainJSON['prior'] = priorApprovalJSON;
	} else {
		 var priorApprovalJSON = {};
		 mainJSON['prior'] = priorApprovalJSON;
	}
	
	if ($jq('#noticePeriodFlag1').is(':checked') == false && $jq('#noticePeriodFlag2').is(':checked') == false) {
		errorMessage += "Please select notice period\n";
		if (status) {
			$jq('#noticePeriodFlag1').focus();
			status = false;
		}
	}
		
	if ($jq('#serviceBookFlag1').is(':checked') == false && $jq('#serviceBookFlag2').is(':checked') == false ) {
		errorMessage += "Please select service book entry\n";
		if (status) {
			$jq('#serviceBookFlag1').focus();
			status = false;
		}
	}
	
	if ($jq('#doPartFlag1').is(':checked') == false && $jq('#doPartFlag2').is(':checked') == false) {
		errorMessage += "Please select publish DO part\n";
		if (status) {
			$jq('#doPartFlag1').focus();
			status = false;
		}
	}
	
	if ($jq('#medicalcert1').is(':checked') == false && $jq('#medicalcert2').is(':checked') == false) {
		errorMessage += "Please select medical certificate\n";
		if (status) {
			$jq('#medicalcert1').focus();
			status = false;
		}
	} else if ($jq('#medicalcert1').is(':checked') == true) {
		 var medicalJSON = {};
		 var dynRow = document.getElementById("medical");
		 var length = dynRow.rows.length;
		 for (i = 1; i < length; i++) {
			 if (dynRow.rows[i].cells[1].childNodes[0].value != "") {
				 var exceptionJSON = {};
				 exceptionJSON['exp'] = dynRow.rows[i].cells[1].childNodes[0].value;
				 exceptionJSON['expID'] = JSONExceptionDetailsList['MED'];
				 medicalJSON[i - 1] = exceptionJSON;
			 }
		 }
		 mainJSON['medical'] = medicalJSON;
	} else {
		var medicalJSON = {};
		mainJSON['medical'] = medicalJSON;
	}
	
	if ($jq('#othercertflag1').is(':checked') == false && $jq('#othercertflag2').is(':checked') == false) {
		errorMessage += "Please select Any Other attachments\n";
		if (status) {
			$jq('#othercertflag1').focus();
			status = false;
		}
	} else if ($jq('#othercertflag1').is(':checked') == true) {
		 var otherCertJSON = {};
		 var dynRow = document.getElementById("certExcID");
		 var length = dynRow.rows.length;
		 for (i = 1; i < length; i++) {
			 if (dynRow.rows[i].cells[1].childNodes[0].value != "") {
				 var exceptionJSON = {};
				 exceptionJSON['exp'] = dynRow.rows[i].cells[1].childNodes[0].value;
				 exceptionJSON['expID'] = JSONExceptionDetailsList['OTHER_CERT'];
				 otherCertJSON[i - 1] = exceptionJSON;
			 }
		 }
		 mainJSON['othercert'] = otherCertJSON;
	} else {
		 var otherCertJSON = {};
		 mainJSON['othercert'] = otherCertJSON;
	}
	
	if (status) {
		var stringjson = JSON.stringify(mainJSON);
		$jq('#exceptionsJson').val(stringjson);
		$jq('#param').val('insertSpecialCasualDetails');
		$jq.post('leave.htm', $jq('#LeaveManagementBean').serialize(), function(html) {
			$jq("#result1").html(html);
			if ($jq('.success').is(':visible')) {
				if ($jq('#leaveSubType option:selected').val() == 'new') {
					var lstvalue = $jq("#res").html();
					$jq('#leaveSubType').append($jq("<option></option>").attr("value", lstvalue).text($jq('#specialLeaveDesc').val()).attr("selected", "selected"));
				} else {
					$jq('#leaveSubType option:selected').text($jq('#specialLeaveDesc').val());
				}
			}
		});
	} else {
		alert(errorMessage);
	}
}

function getleavesubtypedetails() {
	if ($jq('#leaveSubType option:selected').val() != 'select') {
		$jq("#result1").html('');
		
		if ($jq('#leaveSubType option:selected').val() == 'new') {
			$jq("#newleavedescID").attr('style', 'display:block');
		} else {
			$jq("#newleavedescID").attr('style', 'display:none');
		}
		
		$jq('#param').val('getLeavesubtypeDetails');
		$jq.post('leave.htm', $jq('#LeaveManagementBean').serialize(), function(html) {
			$jq("#contentpage").html(html);	       
		});
	} else {
		alert("Please select leave sub-type\n");
	}
}

function onloadservices() {
	if ($jq('#leaveTypeId option:selected').val() != '11') {
		showPriorApprovalDetails();
		shownLeaveDedit();
		showFamilyImpactDetails();
		showEncashmentDetails();
		showotherCertificate();
		showLeaveConversionType();
		showMedicalCertificate();
		showFitnessCertificate();
		showOtherLeaveCredit();
		showMaxMinExceptionsDetails();
		showDaysEntries();
		
		if ($jq('#eligiblityFlag1').is(':checked') && $jq('#eligiblityFlag2').is(':checked') && $jq('#eligiblityFlag3').is(':checked')) {
			OtherRuleFlag = "No";
		} else {
			OtherRuleFlag = "Yes";
		}
	} else {
		showPriorApprovalDetails();
		showMedicalCertificate();
		showotherCertificate();
	}
}

function showMaxMinExceptionsDetails() {

	if (document.getElementById('minspell').rows.length == 1) {
		funCreateNewRow('minspell', 'minExc');
	}
	if (document.getElementById('maxspell').rows.length == 1) {
		funCreateNewRow('maxspell', 'maxExc');
	}
}

function showPriorApprovalDetails() {
	if ($jq('#priorApprovalFlag1').is(':checked')) {
		$jq('#priorApprovalID').attr('style', 'display:block');
		if (document.getElementById('priorapporval').rows.length == 1) {
			funCreateNewRow('priorapporval', 'priorapprExc');
		}
	} else {
		$jq('#priorApprovalID').attr('style', 'display:none');
	}	 
}

function shownLeaveDedit() {
	if ($jq('#debitflagid1').is(':checked') || $jq('#debitflagid3').is(':checked')) {
		$jq('#debitmapID').attr('style', 'display:none');
	} else if ($jq('#debitflagid2').is(':checked')) {
		$jq('#debitmapID').attr('style', 'display:block');
	}
}

function showFamilyImpactDetails() {
	if ($jq('#familyImpactFlag1').is(':checked')) {
		$jq('#familyimpactID').attr('style', 'display:block');
	} else {
		$jq('#familyimpactID').attr('style', 'display:none');
	}
}

function showEncashmentDetails() {
	if ($jq('#encashmentFlag1').is(':checked')) {
		$jq('#enchasmentID').attr('style', 'display:block');
	} else {
		$jq('#enchasmentID').attr('style', 'display:none');
	}
}

function showotherCertificate() {
	if ($jq('#othercertflag1').is(':checked')) {
		$jq('#certNameID').attr('style', 'display:block');
		if (document.getElementById('certExcID').rows.length == 1) {
			funCreateNewRow('certExcID', 'certExc');
		}
	} else {
		$jq('#certNameID').attr('style', 'display:none');
	}
}

function showLeaveConversionType() {
	if ($jq('#leaveconversion1').is(':checked')) {
		$jq('#conversionID').attr('style', 'display:none');
	} else if ($jq('#leaveconversion2').is(':checked') || $jq('#leaveconversion3').is(':checked') || $jq('#leaveconversion4').is(':checked') || $jq('#leaveconversion5').is(':checked')) {
		$jq('#conversionID').attr('style', 'display:block');
		if(document.getElementById('ElConversionID').rows.length == 1) {
			funCreateNewELRow('ElConversionID', 'elconversion', 'leaveConversionType');
		}
	}
}

function showMedicalCertificate() {
	if ($jq('#medicalcert1').is(':checked')) {
		$jq('#medicalCertID').attr('style', 'display:block');
		if (document.getElementById('medical').rows.length == 1) {
			funCreateNewRow('medical', 'mcExc');
		}
	} else {
		$jq('#medicalCertID').attr('style', 'display:none');
	}
}

function showFitnessCertificate() {
	if ($jq('#fitnesscert1').is(':checked')) {
		$jq('#fitnessCertID').attr('style', 'display:block');
		if (document.getElementById('fitness').rows.length == 1) {
			funCreateNewRow('fitness', 'fitExc');
		}
	} else {
		$jq('#fitnessCertID').attr('style', 'display:none');
	}
}

function showOtherLeaveCredit() {
	if ($jq('#otherCredit1').is(':checked')) {
		$jq('#otherCreditID').attr('style', 'display:block');
		if (document.getElementById('otherException').rows.length == 1) {
			funCreateNewRow('otherException', 'otherExc');
		}
	} else {
		$jq('#otherCreditID').attr('style', 'display:none');
	}
}

function clearMessage()
{
	$jq('#result1').html('');
}

function submitLeaveOtherDetails()
{
	
	if(OtherRuleFlag=="No")
	{
		alert("Provide general details first");
		return;
	}
	var errorMessage="";
	var status=true;
	
	if($jq('#familyImpactFlag1').is(':checked')==false && $jq('#familyImpactFlag2').is(':checked')==false )
	{
		errorMessage+="Please select impact on famliy members .\n";
		if(status){
			$jq('#familyImpactFlag1').focus();
			status=false;
		}
	}
	else if($jq('#familyImpactFlag1').is(':checked')==true)
	{  
		if($jq('#servivingChild').val()=='')
		{
			errorMessage+="Please enter noof surviving children .\n";
			if(status){
				$jq('#servivingChild').focus();
				status=false;
			}
		}
	
		
	}
	
	if($jq('#encashmentFlag1').is(':checked')==false && $jq('#encashmentFlag2').is(':checked')==false )
	{
		errorMessage+="Please select Encashment Possible .\n";
		if(status){
			$jq('#encashmentFlag1').focus();
			status=false;
		}
	}
	
	
	if(errorMessage=="")
	{
		$jq('#param').val('insertLeaveOtherDetails');
		$jq.post('leave.htm', $jq('#LeaveManagementBean').serialize(),
						function(html) {
			$jq("#result1").html(html);
					       
						});
	}
	else
	{
		alert(errorMessage);
	}
}

function submitLeaveRulesDetails()
{
	if(OtherRuleFlag=="No")
	{
		alert("Provide general details first");
		return;
	}
	var errorMessage="";
	var status=true;
	var mainJSON={};
	
	 var minSpellJSON={};
	 var dynRow=document.getElementById("minspell");
	 var length=dynRow.rows.length;
	 for(i=1;i<length;i++)
	 {
		 if(dynRow.rows[i].cells[1].childNodes[0].value!="")
		 {
			 var exceptionJSON={};
			 exceptionJSON['exp']=dynRow.rows[i].cells[1].childNodes[0].value;
			 exceptionJSON['expID']=JSONExceptionDetailsList['MIN_PER_SPELL'];
			 minSpellJSON[i-1]=exceptionJSON;
		 }
	 }
	 mainJSON['minspell']=minSpellJSON;
	 
	 var maxSpellJSON={};
	 var dynRow1=document.getElementById("maxspell");
	 var length1=dynRow1.rows.length;
	 for(i=1;i<length1;i++)
	 {
		 if(dynRow1.rows[i].cells[1].childNodes[0].value!="")
		 {
			 var exceptionJSON={};
			 exceptionJSON['exp']=dynRow1.rows[i].cells[1].childNodes[0].value;
			 exceptionJSON['expID']=JSONExceptionDetailsList['MAX_PER_SPELL'];
			 maxSpellJSON[i-1]=exceptionJSON;
		 }
	 }
	 mainJSON['maxspell']=maxSpellJSON;
	
	if(errorMessage=="")
	{
		var stringjson=JSON.stringify(mainJSON);
		$jq('#exceptionsJson').val(stringjson);
		$jq('#param').val('insertLeaveRulesDetails');
		$jq.post('leave.htm', $jq('#LeaveManagementBean').serialize(),
						function(html) {
							
			$jq("#result1").html(html);
						});
	}
	else
	{
		alert(errorMessage);
	}
}

function submitLeaveGeneralConfigurationDetails()
{
	var status=true;
	var errorMessage="";
	var mainJSON={};
	if($jq('#eligiblityFlag1').is(':checked')==false && $jq('#eligiblityFlag2').is(':checked')==false && $jq('#eligiblityFlag3').is(':checked')==false)
	{
		errorMessage+="Please select eligiblity.\n";
		if(status){
			$jq('#eligiblityFlag1').focus();
			status=false;
		}
	}
	if($jq('#priorApprovalFlag1').is(':checked')==false && $jq('#priorApprovalFlag2').is(':checked')==false )
	{
		errorMessage+="Please select prior approval.\n";
		if(status){
			$jq('#priorApprovalFlag1').focus();
			status=false;
		}
	}
	else if($jq('#priorApprovalFlag1').is(':checked')==true)
	{
		 var priorApprovalJSON={};
		 var dynRow=document.getElementById("priorapporval");
		 var length=dynRow.rows.length;
		 for(i=1;i<length;i++)
		 {
			 if(dynRow.rows[i].cells[1].childNodes[0].value!="")
			 {
				 var exceptionJSON={};
				 exceptionJSON['exp']=dynRow.rows[i].cells[1].childNodes[0].value;
				 exceptionJSON['expID']=JSONExceptionDetailsList['PRIOR_APPR'];
				 priorApprovalJSON[i-1]=exceptionJSON;
			 }
		 }
		 mainJSON['prior']=priorApprovalJSON;
	}
	else
	{
		 var priorApprovalJSON={}
		 mainJSON['prior']=priorApprovalJSON;
	}
	if($jq('#holidayIntFlag1').is(':checked')==false && $jq('#holidayIntFlag2').is(':checked')==false )
	{
		errorMessage+="Please select holiday intervening.\n";
		if(status){
			$jq('#holidayIntFlag1').focus();
			status=false;
		}
	}
	
	if($jq('#halfdayflag1').is(':checked')==false && $jq('#halfdayflag2').is(':checked')==false )
	{
		errorMessage+="Please select half day leave.\n";
		if(status){
			$jq('#halfdayflag1').focus();
			status=false;
		}
	}
	
	if($jq('#noticeperiodflag1').is(':checked')==false && $jq('#noticeperiodflag2').is(':checked')==false )
	{
		errorMessage+="Please select Available in notice period.\n";
		if(status){
			$jq('#noticeperiodflag1').focus();
			status=false;
		}
	}
	if($jq('#ltcAvailFlag1').is(':checked')==false && $jq('#ltcAvailFlag2').is(':checked')==false )
	{
		errorMessage+="Please select Ltc Available .\n";
		if(status){
			$jq('#ltcAvailFlag1').focus();
			status=false;
		}
	}
	if($jq('#viewflag1').is(':checked')==false && $jq('#viewflag2').is(':checked')==false )
	{
		errorMessage+="Please select Shown to User.\n";
		if(status){
			$jq('#viewflag1').focus();
			status=false;
		}
	}
	
	if($jq('#maxshownflag1').is(':checked')==false && $jq('#maxshownflag2').is(':checked')==false && $jq('#maxshownflag3').is(':checked')==false )
	{
		$jq('#maxshownflag3').attr('checked',true)
	}	
	
	if($jq("input[name=maxShownFlag]:checked").val()=='N' && $jq("#maxleaves").val()==""){
		errorMessage+="Please enter maximum leaves limit.\n";
		if(status){
			$jq('#maxleaves').focus();
			status=false;
		}
	}
	
	
	if($jq('#offlineflag1').is(':checked')==false && $jq('#offlineflag2').is(':checked')==false )
	{
		errorMessage+="Please select Offline Leave Entry.\n";
		if(status){
			$jq('#offlineflag1').focus();
			status=false;
		}
	}
	
	if($jq('#availwoleaves1').is(':checked')==false && $jq('#availwoleaves2').is(':checked')==false )
	{
		errorMessage+="Please select Available without leaves.\n";
		if(status){
			$jq('#availwoleaves1').focus();
			status=false;
		}
	}
	
	if($jq('#debitflagid1').is(':checked')==false && $jq('#debitflagid2').is(':checked')==false && $jq('#debitflagid3').is(':checked')==false )
	{
		errorMessage+="Please select Direct Leave debit.\n";
		if(status){
			$jq('#availwoleaves1').focus();
			status=false;
		}
	}
	else if($jq('#debitflagid2').is(':checked')==true)
	{
		
		
		if($jq('#debitMappingID option:selected').val()=='select')
		{
			errorMessage+="Please select  debit leave type.\n";
			if(status){
				$jq('#debitMappingID').focus();
				status=false;
			}
		}
		if($jq('#debitmappingleaves').val()=='')
		{
			errorMessage+="Please Enter  debit leaves.\n";
			if(status){
				$jq('#debitmappingleaves').focus();
				status=false;
			}
		}
	}
	
	if($jq('#medicalcert1').is(':checked')==false && $jq('#medicalcert2').is(':checked')==false )
	{
		errorMessage+="Please select medical certificate.\n";
		if(status){
			$jq('#medicalcert1').focus();
			status=false;
		}
	}
	else if($jq('#medicalcert1').is(':checked')==true)
	{
		 var medicalJSON={};
		 var dynRow=document.getElementById("medical");
		 var length=dynRow.rows.length;
		 for(i=1;i<length;i++)
		 {
			 if(dynRow.rows[i].cells[1].childNodes[0].value!="")
			 {
				 var exceptionJSON={};
				 exceptionJSON['exp']=dynRow.rows[i].cells[1].childNodes[0].value;
				 exceptionJSON['expID']=JSONExceptionDetailsList['MED'];
				 medicalJSON[i-1]=exceptionJSON;
			 }
		 }
		 mainJSON['medical']=medicalJSON;
	}
	else
	{
		var medicalJSON={};
		 mainJSON['medical']=medicalJSON;
	}
	
	if($jq('#fitnesscert1').is(':checked')==false && $jq('#fitnesscert2').is(':checked')==false )
	{
		errorMessage+="Please select fitness certificate.\n";
		if(status){
			$jq('#fitnesscert1').focus();
			status=false;
		}
	}
	else if($jq('#fitnesscert1').is(':checked')==true)
	{
		 var fitnessJSON={};
		 var dynRow=document.getElementById("fitness");
		 var length=dynRow.rows.length;
		 for(i=1;i<length;i++)
		 {
			 if(dynRow.rows[i].cells[1].childNodes[0].value!="")
			 {
				 var exceptionJSON={};
				 exceptionJSON['exp']=dynRow.rows[i].cells[1].childNodes[0].value;
				 exceptionJSON['expID']=JSONExceptionDetailsList['FIT'];
				 fitnessJSON[i-1]=exceptionJSON;
			 }
		 }
		 mainJSON['fitness']=fitnessJSON;
	}
	else
	{
		 var fitnessJSON={};
		 mainJSON['fitness']=fitnessJSON;
	}
	if($jq('#othercertflag1').is(':checked')==false && $jq('#othercertflag2').is(':checked')==false )
	{
		errorMessage+="Please select Other Certificate.\n";
		if(status){
			$jq('#othercertflag1').focus();
			status=false;
		}
	}
	else if($jq('#othercertflag1').is(':checked')==true)
	{
		
		
		 var otherCertJSON={};
		 var dynRow=document.getElementById("certExcID");
		 var length=dynRow.rows.length;
		 for(i=1;i<length;i++)
		 {
			 if(dynRow.rows[i].cells[1].childNodes[0].value!="")
			 {
				 var exceptionJSON={};
				 exceptionJSON['exp']=dynRow.rows[i].cells[1].childNodes[0].value;
				 exceptionJSON['expID']=JSONExceptionDetailsList['OTHER_CERT'];
				 otherCertJSON[i-1]=exceptionJSON;
			 }
		 }
		 mainJSON['othercert']=otherCertJSON;
	}
	else
	{
		 var otherCertJSON={};
		 mainJSON['othercert']=otherCertJSON;
	}
	
	if($jq('#otherCredit1').is(':checked')==false && $jq('#otherCredit2').is(':checked')==false )
	{
		errorMessage+="Please select other leaves are in credit.\n";
		if(status){
			$jq('#otherCredit1').focus();
			status=false;
		}
	}
	else if($jq('#otherCredit1').is(':checked')==true)
	{
		 var otherCreditJSON={};
		 var dynRow=document.getElementById("otherException");
		 var length=dynRow.rows.length;
		 for(i=1;i<length;i++)
		 {
			 if(dynRow.rows[i].cells[0].childNodes[0].value!="")
			 {
				 var exceptionJSON={};
				 exceptionJSON['exp']=dynRow.rows[i].cells[1].childNodes[0].value;
				 exceptionJSON['expID']=JSONExceptionDetailsList['OTHER_CREDIT'];
				 otherCreditJSON[i-1]=exceptionJSON;
			 }
		 }
		 mainJSON['otherCredit']=otherCreditJSON;
	}
	else
	{
		 var otherCreditJSON={};
		 mainJSON['otherCredit']=otherCreditJSON;
	}
	
	if($jq('#leavecancellation1').is(':checked')==false && $jq('#leavecancellation2').is(':checked')==false && $jq('#leavecancellation3').is(':checked')==false && $jq('#leavecancellation4').is(':checked')==false && $jq('#leavecancellation5').is(':checked')==false)
	{
		errorMessage+="Please select leave cancellation possibility.\n";
		if(status){
			$jq('#leavecancellation1').focus();
			status=false;
		}
	}
	
	if($jq('#leaveconversion1').is(':checked')==false && $jq('#leaveconversion2').is(':checked')==false && $jq('#leaveconversion3').is(':checked')==false && $jq('#leaveconversion4').is(':checked')==false && $jq('#leaveconversion5').is(':checked')==false)
	{
		errorMessage+="Please select leave Conversion possible.\n";
		if(status){
			$jq('#leaveconversion1').focus();
			status=false;
		}
	}
	else if($jq('#leaveconversion1').is(':checked')==false)
	{
		 var conversionJSON={};
		 var dynRow=document.getElementById("ElConversionID");
		 var length=dynRow.rows.length;
		 var count=0;
		 for(i=1;i<length;i++)
		 {
			 if(dynRow.rows[i].cells[1].childNodes[0].value!="" && document.getElementById("leaveConversionType"+i).value!="Select")
			 {
				 var exceptionJSON={};
				 exceptionJSON['noofdays']=dynRow.rows[i].cells[1].childNodes[0].value;
				 exceptionJSON['leavetype']=document.getElementById("leaveConversionType"+i).value;
				 conversionJSON[i-1]=exceptionJSON;
				 count=count+1;
			 }
		 }
		 mainJSON['elconversion']=conversionJSON;
		 if(count==0)
		 {
			 errorMessage+="Please enter atleast one leave conversion possibilities.\n";
			
		 }
		
	}
	else
	{
		 var conversionJSON={};
		 mainJSON['elconversion']=conversionJSON;
	}
	
	if($jq('#dopartflag1').is(':checked')==false && $jq('#dopartflag2').is(':checked')==false )
	{
		errorMessage+="Please select Publish in DO Part II.\n";
		if(status){
			$jq('#dopartflag1').focus();
			status=false;
		}
	}
	
	if($jq('#servicebookflag1').is(':checked')==false && $jq('#servicebookflag2').is(':checked')==false )
	{
		errorMessage+="Please select Service book entry.\n";
		if(status){
			$jq('#servicebookflag1').focus();
			status=false;
		}
	}
	
	
	
	if(errorMessage=="")
	{
		var stringjson=JSON.stringify(mainJSON);
		$jq('#exceptionsJson').val(stringjson);
		$jq('#param').val('insertLeaveGeneralDetails');
		$jq.post('leave.htm', $jq('#LeaveManagementBean').serialize(),
						function(html) {
							
						$jq("#result1").html(html);
						if($jq('.success').is(':visible')){
							OtherRuleFlag="Yes";
						}
						});
	}
	else
	{
		alert(errorMessage);
	}
}

function funCreateNewRow(idvalue, exidvalue) {
	var dynRow = document.getElementById(idvalue);
	var length = dynRow.rows.length;
	var index = dynRow.rows.length - 1;
    var errorMessage = "";
	var flag = true;

	if (flag) {
		var dynRowIns = insertNewRow(dynRow);	
		var count = index + 1;
		var el2 = document.createElement('textarea');
		el2.name = exidvalue + count;
		el2.id = exidvalue + count;			
		el2.setAttribute("cols", "47");
		el2.setAttribute("rows", "3");
		if (document.all) {
			el2.className = "boot";// end
		} else {
			el2.setAttribute("class", "boot");	
		}
        var elb = document.createElement('input');
		elb.type = 'button';
		elb.name = 'btn' + count;
		elb.id = 'btn' + count;
		// if NN6 then OK to use the standard setAttribute
		if ((!document.all) && (document.getElementById)) {
			elb.setAttribute("onClick", "javascript: funCreateNewExceptionRow('"+ idvalue +"', '"+ exidvalue +"')");
		}
		// workaround for IE 5.x
		if ((document.all) && (document.getElementById)) {
			elb["onclick"] = new Function("javascript:funCreateNewExceptionRow('"+ idvalue +"', '"+ exidvalue +"')");
		}
		elb.value = "+";
		elb.setAttribute("class", "boot" );
		dynRowIns.cells[0].innerHTML = "<b>Exception</b> ";
		dynRowIns.cells[1].appendChild(el2);
		dynRowIns.cells[2].appendChild(elb);
	} else {
		alert(errorMessage);
		return status;
	}
}

function funCreateNewExceptionRow(idvalue, exidvalue) {
	var dynRow = document.getElementById(idvalue);
	var length = dynRow.rows.length;
	var index = dynRow.rows.length - 1;
    var errorMessage = "";
	var flag = true;
		
	if (flag) {
		var dynRowIns = insertNewRow(dynRow);	
		var count = index + 1;
		var el2 = document.createElement('textarea');
		el2.name = exidvalue + count;
		el2.id = exidvalue + count;

		el2.setAttribute("cols", "47");
		el2.setAttribute("rows", "3");
		if (document.all) {
			el2.className="boot";// end
		} else {
			el2.setAttribute("class", "boot");	
		}
        var elb = document.createElement('input');
		elb.type = 'button';
		elb.name = 'btn' + count;
		elb.id = 'btn' + count;
		// if NN6 then OK to use the standard setAttribute
		if ((!document.all) && (document.getElementById)) {
			elb.setAttribute("onClick", "javascript: deleteSpecificRow(this, '"+ idvalue +"')");
		}    
		// workaround for IE 5.x
		if ((document.all) && (document.getElementById)) {
			elb["onclick"] = new Function("javascript: deleteSpecificRow(this, '"+ idvalue +"')");
		}
		elb.value = "-";
		elb.setAttribute("class", "boot");
		dynRowIns.cells[0].innerHTML = "<b>Exception</b> ";
		dynRowIns.cells[1].appendChild(el2);
		dynRowIns.cells[2].appendChild(elb);
	} else {
		alert(errorMessage);
	   	return status;
	}
}

function insertNewRow(dynRow) {
	noOfRows = dynRow.rows.length;
	noOfCols = dynRow.rows[noOfRows - 1].cells.length;
	var x = dynRow.insertRow(noOfRows);
	for (var j = 0; j < noOfCols; j++) {
		newCell = x.insertCell(j);
	}
	return x;
}

function deleteSpecificRow(node, tableID) {
	var dynRow = document.getElementById(tableID);
	var index = node.parentNode.parentNode.rowIndex;
	var length1 = dynRow.rows.length;
	var del = confirm("Do you want to delete this Row?");
	if (del) {
		if (index >= 0) {
			dynRow.deleteRow(index);
		}
	}
}

function showDaysEntries() {
	if ($jq('#leaveDurationID option:selected').val() != '4') {
		$jq('#noofDaysID').attr("style", "display:block;float:left");
	} else {
		$jq('#noofDaysID').attr("style", "display:none;float:left");
	}
}

function showDaysEntries1() {
	if ($jq('#leaveDurationID option:selected').val() != '4') {
		$jq('#noofDaysID').attr("style", "display:block;float:left");
	} else {
		$jq('#leaveCredits').val('');
		$jq('#noofDaysID').attr("style", "display:none;float:left");
	}
}

function leaveJsonType()
{

	var tempJSON={};
	for(var i=1;i<=15;i++)
	{
		switch(i)
		{
		case 1:
				var exceptionJSON={};
				 exceptionJSON['exp']="Earned Leave";
				 exceptionJSON['expID']=i;
				 tempJSON[i]=exceptionJSON;
				 break;
		case 2:
				var exceptionJSON={};
				exceptionJSON['exp']="Half Pay Leave";
				exceptionJSON['expID']=i;
				tempJSON[i]=exceptionJSON;
				break;
		case 3:
				var exceptionJSON={};
				 exceptionJSON['exp']="Casual Leave";
				 exceptionJSON['expID']=i;
				 tempJSON[i]=exceptionJSON;
				 break;
		case 4:
				var exceptionJSON={};
				exceptionJSON['exp']="Commuted Leave";
				exceptionJSON['expID']=i;
				tempJSON[i]=exceptionJSON;
				break;
		case 5:
				var exceptionJSON={};
				 exceptionJSON['exp']="Leave Not Due";
				 exceptionJSON['expID']=i;
				 tempJSON[i]=exceptionJSON;
				 break;
		case 6:
				var exceptionJSON={};
				exceptionJSON['exp']="Extra Ordinary Leave";
				exceptionJSON['expID']=i;
				tempJSON[i]=exceptionJSON;
				break;
		case 7:
				var exceptionJSON={};
				 exceptionJSON['exp']="Maternity Leave";
				 exceptionJSON['expID']=i;
				 tempJSON[i]=exceptionJSON;
				 break;
		case 8:
				var exceptionJSON={};
				exceptionJSON['exp']="Paternity Leave";
				exceptionJSON['expID']=i;
				tempJSON[i]=exceptionJSON;
				break;
		case 9:
				var exceptionJSON={};
				 exceptionJSON['exp']="Child Care Leave";
				 exceptionJSON['expID']=i;
				 tempJSON[i]=exceptionJSON;
				 break;
		case 10:
				var exceptionJSON={};
				exceptionJSON['exp']="Study Leave";
				exceptionJSON['expID']=i;
				tempJSON[i]=exceptionJSON;
				break;
		case 11:
				var exceptionJSON={};
				 exceptionJSON['exp']="Special Casual Leave";
				 exceptionJSON['expID']=i;
				 tempJSON[i]=exceptionJSON;
				 break;
		case 12:
				var exceptionJSON={};
				exceptionJSON['exp']="Pregnancy";
				exceptionJSON['expID']=i;
				tempJSON[i]=exceptionJSON;
				break;
		case 13:
				var exceptionJSON={};
				 exceptionJSON['exp']="Miscarriage/Abortion";
				 exceptionJSON['expID']=i;
				 tempJSON[i]=exceptionJSON;
				 break;
		case 14:
				var exceptionJSON={};
				exceptionJSON['exp']="Adopted Child";
				exceptionJSON['expID']=i;
				tempJSON[i]=exceptionJSON;
				break;
		case 15:
			var exceptionJSON={};
			exceptionJSON['exp']="EOL Without MC";
			exceptionJSON['expID']=i;
			tempJSON[i]=exceptionJSON;
			break;
		} 
		
	}
	
	return tempJSON;
	
}

function funCreateNewELRow(idvalue, exidvalue, leaveConType) {
	var dynRow = document.getElementById(idvalue);
	var length = dynRow.rows.length;
	var index = dynRow.rows.length-1;
    var errorMessage = "";
	var flag = true;
	var tempjson = leaveJsonType();
	var leaveTypevalue = tempjson[$jq('#leaveTypeId option:selected').val()].exp;

	if (flag) {
		var dynRowIns = insertNewRow(dynRow);	
		var count = index + 1;
		var el12 = document.createElement('input');
		el12.type = 'text';
		el12.name = exidvalue+ count;
		el12.id = exidvalue+ count;	
		
		if ((!document.all) && (document.getElementById)) {
			el12.setAttribute("onkeypress","javascript: return checkTwoDigitFloat(event, '"+ exidvalue+count +"')");
		}    
		// workaround for IE 5.x
		if ((document.all) && (document.getElementById)) {
			el12["onkeypress"]=new Function("javascript: return checkTwoDigitFloat(event, '"+ exidvalue+count +"')");
		}
		var el2 = document.createElement('select');
			
		el2.name = leaveConType + count;
		el2.id = leaveConType + count;			
		
		var elb = document.createElement('input');
		elb.type = 'button';
		elb.name = 'btn' + count;
		elb.id = 'btn' + count;
		// if NN6 then OK to use the standard setAttribute
		if ((!document.all) && (document.getElementById)) {
			       elb.setAttribute("onClick", "javascript: funCreateNewELConversionRow('"+ idvalue +"', '"+ exidvalue +"', '"+ leaveConType +"')");
		}
		// workaround for IE 5.x
		if ((document.all) && (document.getElementById)) {
			elb["onclick"] = new Function("javascript: funCreateNewELConversionRow('"+ idvalue +"', '"+ exidvalue +"', '"+ leaveConType +"')");
		}
		elb.value = "+";
		elb.setAttribute("class", "boot" );
		dynRowIns.cells[0].innerHTML = "1 " + leaveTypevalue + " = ";
		dynRowIns.cells[1].appendChild(el12);
		dynRowIns.cells[2].appendChild(el2);
		dynRowIns.cells[3].appendChild(elb);	

		var elconlen = document.getElementById("leaveTypeId").length;
		for (var k = 0, i = 0; k < elconlen; k++, i++) {
			document.getElementById(leaveConType + count).options[i] = new Option(document.getElementById("leaveTypeId").options[i].text);
			document.getElementById(leaveConType + count).options[i].value = document.getElementById("leaveTypeId").options[i].value;	
		}
	} else {
		alert(errorMessage);
		return status;
	}
}

function funCreateNewELConversionRow(idvalue, exidvalue, leaveConType) {
	var dynRow = document.getElementById(idvalue);
	var length = dynRow.rows.length;
	var index = dynRow.rows.length - 1;
    var errorMessage = "";
	var flag = true;
	var tempjson = leaveJsonType();
	var leaveTypevalue = tempjson[$jq('#leaveTypeId option:selected').val()].exp;

	if (flag) {
		var dynRowIns = insertNewRow(dynRow);	
		var count = index + 1;
		var el12 = document.createElement('input');
		el12.type = 'text';
		el12.name = exidvalue + count;
		el12.id = exidvalue + count;	

		if ((!document.all) && (document.getElementById)) {
			el12.setAttribute("onkeypress", "javascript: return checkTwoDigitFloat(event, '"+ exidvalue + count +"')");
		}
		// workaround for IE 5.x
		if ((document.all) && (document.getElementById)) {
			el12["onkeypress"] = new Function("javascript: return checkTwoDigitFloat(event, '"+ exidvalue + count +"')");
		}
		var el2 = document.createElement('select');
		el2.name = leaveConType + count;
		el2.id = leaveConType + count;			
        var elb = document.createElement('input');
		elb.type = 'button';
		elb.name = 'btn' + count;
		elb.id = 'btn' + count;
		// if NN6 then OK to use the standard setAttribute
		if ((!document.all) && (document.getElementById)) {
			elb.setAttribute("onClick", "javascript: deleteSpecificRow(this, '"+ idvalue +"')");
		}
		// workaround for IE 5.x
		if ((document.all) && (document.getElementById)) {
			elb["onclick"] = new Function("javascript: deleteSpecificRow(this, '"+ idvalue +"')");
		}
		elb.value = "-";
		elb.setAttribute("class", "boot" );
		dynRowIns.cells[0].innerHTML = "1 " + leaveTypevalue + " = ";
		dynRowIns.cells[1].appendChild(el12);
		dynRowIns.cells[2].appendChild(el2);
		dynRowIns.cells[3].appendChild(elb);
	
		var elconlen = document.getElementById("leaveTypeId").length;
		for (var k = 0, i = 0; k < elconlen; k++, i++) {
			document.getElementById(leaveConType + count).options[i] = new Option(document.getElementById("leaveTypeId").options[i].text);
			document.getElementById(leaveConType + count).options[i].value = document.getElementById("leaveTypeId").options[i].value;	
		}
	} else {
		alert(errorMessage);
		return status;
	}
}

function lapsMandatory() {
	if ($jq("input[name=maxShownFlag]:checked").val() == 'N') {
		$jq("#maxleaves").val(maxLeavesLimit);
		$jq("#lapsDiv").show();
	} else {
		$jq("#maxleaves").val('');
		$jq("#lapsDiv").hide();
	}
}

// Leave Request start
function resetLeave(source) {
	if (!(source == 'leaveType' || source == 'leaveSubType')) {
		$jq('#leaveType').val('select');
	}
	if ($jq("#leaveSubType").is(':visible') && source != 'leaveSubType') {
		$jq('#leaveSubType').empty().append('<option value="select">Select</option>').find('option:first').attr("selected", "selected");
		$jq("#subType").hide();
	}
	if ($jq("#offlineSFID").is(':visible') && !(source == 'leaveType' || source == 'leaveSubType') && source != 'sfid') {
		$jq("#offlineSFID").val('');
	}
	if ($jq("#studyDegree").is(':visible')) {
		$jq("#studyDegree").val('select');
		$jq("#studyImpact").hide();
	}
	if ($jq("#childName").is(':visible')) {
		$jq('#childName').find('option').remove().end().append($jq("<option></option>").attr("value", 'select').text('Select'));
		$jq("#childName").val('select');
		$jq("#familyImpact").hide();
	}	
	if ($jq("#deliveryDate").is(':visible')) {
		$jq("#deliveryDate").val('');
		$jq("#dateImpact").hide();
	}
	
	$jq('#fromDate').val('');
	$jq('#toDate').val('');
	currentNextYearBalance = '';
	futureAvailableLeaves = '';
	$jq('#days').text('0');
	$jq('#prefix').text('');
	$jq('#suffix').text('');
	
	if ($jq("#halfDayCheck").is(':visible')) {
		$jq("#fromHalfDayFlag").attr("checked", false);
		$jq("#toHalfDayFlag").attr("checked", false);
		$jq("#halfDayCheck").hide();
	}
	
	$jq('#selectAddress').val('select');
	if ($jq("#address").is(':visible')) {
		$jq('#address').val('');
		$jq('#addressDiv').hide();
	}
	$jq('#addressTextDiv').text('');
	$jq('#contactNumber').val('');	
	
	$jq('#reason').val('');	
	$jq('#counter').val('500');
	if (source != 'submit') {
		$jq('#result').text('');
	}
	
	if ($jq("#mcFile").is(':visible')) {
		$jq('#mcFile').val('');
		$jq("#medicalFlagDiv").hide();
	}
	if ($jq("#fitnessFile").is(':visible')) {
		$jq('#fitnessFile').val('');
		$jq("#fitnessFlagDiv").hide();
	}
	if ($jq("#otherCertFile").is(':visible')) {
		$jq('#otherCertificate').text('');
		$jq('#otherCertFile').val('');
		$jq("#otherCertDiv").hide();
	}
	
	if ($jq("#exceptions").is(':visible')) {
		$jq("#exceptions").text('');
		var expHtml = '<fieldset><legend><strong style=\"failure\">Exceptions</strong></legend>'+
							'<div id=\"priorApprExceptions\" class=\"line\" style=\"display:none\"></div>'+
							'<div id=\"minDaysExceptions\" class=\"line\" style=\"display:none\"></div>'+
							'<div id=\"maxDaysExceptions\" class=\"line\" style=\"display:none\"></div>'+
							'<div id=\"mcExceptions\" class=\"line\" style=\"display:none\"></div>	'+
							'<div id=\"fitnessExceptions\" class=\"line\" style=\"display:none\"></div>'+
							'<div id=\"otherAvailExceptions\" class=\"line\" style=\"display:none\"></div>'+
							'<div id=\"otherCertExceptions\" class=\"line\" style=\"display:none\"></div>'+
						'</fieldset>';
		$jq("#exceptions").html(expHtml);
		$jq("#exceptions").hide();
	}
}

function checkHolidayDate(id, fix) {
	arrDt = convertDate(selectedDPDate).split("-");
	var wday = new Date(arrDt[2], arrDt[1] - 1, arrDt[0]).getDay();
	if (_TT.indexOf(selectedDPDate) != -1) {
		var del = confirm("Selected Date is Holiday!\nDo you want to proceed ?\n");
		// alert("Selected Date is Holiday. Please select another date");
		if (del) {
			date = $jq('#fromDate').val();
		} else {
			$jq('#' + id).val('');
			$jq('#' + fix).text('');
			return false;
		}	
	}
	if (wday == 6) {
		var del = confirm("Selected Date is Saturday!\nDo you want to proceed ?\n");
		//alert("Selected Date is Saturday.Please select another date");
		//del=false;
		if (del) {
			date = $jq('#fromDate').val();
		} else {
			$jq('#' + id).val('');
			$jq('#' + fix).text('');
			return false;
		}
	}
	if (wday == 0) {
		var del;
		if (selectedDPDate == '28-Sep-2014') {
			del = true;
		} else {
			del = confirm("Selected Date is Sunday!\nDo you want to proceed ?\n");
		//alert("Selected Date is Sunday.Please select another date");
		//del=false;
		}
		//alert("Selected Date is Sunday.Please select another date");
		//del=false;
		
		if (del) {
			date = $jq('#fromDate').val();
		} else {
			$jq('#' + id).val('');
			$jq('#' + fix).text('');
			return false;
		}
	}
	var num = getNonBreakupHolidays(fix);
	if (num == 1) alert("Your child age has crossed the applicable limit for Child Care Leave\n");
	if ($jq('#fromDate').val() == $jq('#toDate').val()) {
		//$jq('#fromHalfDayFlag').siblings()[0].firstChild.replaceWholeText('Forenoon');
		//$jq('#toHalfDayFlag').siblings()[0].firstChild.replaceWholeText('Afternoon');
		$jq('#fromHalfDayFlag').siblings()[0].firstChild.data = 'Forenoon';
		$jq('#toHalfDayFlag').siblings()[0].firstChild.data = 'Afternoon';
	} else {
		$jq('#fromHalfDayFlag').siblings()[0].firstChild.data = 'Halfday on From Date';
		$jq('#toHalfDayFlag').siblings()[0].firstChild.data = 'Halfday on To Date';
	}
}

function getNonBreakupHolidays(type) {
	$jq("#fromHalfDayFlag").attr("checked", false);
	$jq("#toHalfDayFlag").attr("checked", false);
	if ($jq('#fromDate').val() != '' || $jq('#toDate').val() != '') {
		var date = null;
		if (type == 'prefix') {
			date = $jq('#fromDate').val();
		} else {
			date = $jq('#toDate').val();
		}
		$jq.ajax( {
			type : "POST",
			url : "leaveRequest.htm",
			data : "param=holidays&type=" + type + "&selectedDate=" + date,
			cache : false,
			success : function(html) {
				$jq("#" + type).html(html);
				if (jsonRetirementIntimation.length != 0) { // Added By Naresh
					alert(jsonRetirementIntimation);
					retirementDate = jsonRetirementIntimation.split(':')[1];
				} else {
					retirementDate = '';
				} // End
				if (!$jq("#leaveSubType").is(':visible') || $jq('#leaveSubType').val() == '6' || $jq('#leaveSubType').val() == '1' || $jq('#leaveSubType').val() == '4') { //  || ($jq('#leaveType').val() == 11)
					checkHolidaysInterveningFlag();
				} else {
					if ($jq('#fromDate').val() != '' && $jq('#toDate').val() != '') {
						$jq('#days').text(parseInt(compareDates(convertDate($jq('#fromDate').val()), convertDate($jq('#toDate').val()))) + 1);
					}
				} 
			}
		});
	}
	
	if (type == 'suffix') {
		if (familyImpactListJson != null) {		
			for (var k = 0; k < familyImpactListJson.length; k++) {
				if (familyImpactListJson[k].leaveTypeDetails.id == $jq("#leaveType").val()) {
					if (!(familyImpactListJson[k].childAge == 0 || familyImpactListJson[k].childAge == '') && familyMembersListJson != null) {
						var arr = new Array();
						arr = familyMembersListJson;
						$jq('#childName').find('option').remove().end().append($jq("<option></option>").attr("value", 'select').text('Select'));
						for (var l = 0; l < familyImpactListJson[k].noOfChildren;) {
							var dob = "first";
							for (m = 0; m < arr.length; m++) {						
								if (arr[m] != null && arr[m] != undefined && (dob == arr[m].dob || dob == "first")) {
									if ((arr[m].phtypeFlag == 'N' && parseFloat(familyImpactListJson[k].childAge) * 365.26 > parseFloat(compareDates(convertDate(arr[m].strDob), convertDate($jq('#toDate').val()))))
										||(arr[m].phtypeFlag == 'Y' && parseFloat(familyImpactListJson[k].phChildAge) * 365.26 > parseFloat(compareDates(convertDate(arr[m].strDob), convertDate($jq('#toDate').val()))))) {
										$jq('#childName').append($jq("<option></option>").attr("value", arr[m].id).text(arr[m].name));
									}
									dob = arr[m].dob;
									arr = jQuery.grep(arr, function(value) {
									    return value != arr[m];
									});
									l++;
									m--;									
								}
							}
							if (arr.length == 0) {
								l++;
							}
						}
					}
					//break;
				}
			}
		}
	}
	if (type == 'suffix' && $jq('#childName').is(':visible')) {
		return $jq('#childName')[0].length;
	} else {
		return 0;
	}
}

function isEmpty(obj) {
	for ( var prop in obj) {
		return false;
	}
	return true;
}

function getNoOfDays(fromDate, toDate, leaveType, rowID, mapDays) {	
	if (leaveType != 'select' && $jq("#" + fromDate).val() != '' && $jq("#" + toDate).val() != '') {	
		var requestDetails = {
			"leaveType" : leaveType,
			"fromDate" : $jq("#" + fromDate).val(),
			"toDate" : $jq("#" + toDate).val(),
			"param" : "noofdays"
		};
		$jq.ajax( {
			type : "POST",
			url : "leaveRequest.htm",
			data : requestDetails,
			cache : false,
			success : function(html) {
				if (mapDays == '') {
					$jq("#days").html($jq(html).find("#noDays").text());
					currentNextYearBalance = $jq(html).find('#currentNextToken').text(); //jsonCurrentNextToken;
					futureAvailableLeaves =  $jq(html).find('#futureLeavesToken').text(); //jsonFutureLeavesToken;
				} else {
					$jq("#days" + rowID).html('<div>' + parseFloat($jq(html).find("#noDays").text()) * parseFloat(mapDays) + '</div>');
				}
			}
		});	
	}
}

function selectedLeaveType(parameter) {
	selectedLeave = $jq("#leaveType").val();
	if (parameter != 'amendment') {
		resetLeave('leaveType');
	}
	if (selectedLeave == '11') {
		$jq("#subType").show();			
		addSpecialCasualLeaves();			
	} else {
		if (selectedLeave == '8') { // If the leave type is paternity leave display the delivery date
			$jq("#dateImpact").show();
		} else if (selectedLeave == '10') { // If the leave type is study leave display the degree type
			$jq("#studyImpact").show();
		} else if(selectedLeave == '16' || selectedLeave == '14') { // If the leave is adoption leave display the adoption date 
			$jq("#dateImpact").show();							    // 16-Child Adoption Leave for Male, 14-Child Adoption Leave for Female
		}
		checkConstraints();
		
		// Medical certificate checking
		if ($jq("#mcFile").is(':visible')) { // Added by Naresh
			displayExceptions(medCert, 'Medical Certificate is required\n', 'mcFile', 'mcExceptions', '');
		}
		// Fitness certificate checking check
		if ($jq("#fitnessFile").is(':visible')) {
			displayExceptions(fitCert, 'Fitness Certificate is required\n', 'fitnessFile', 'fitnessExceptions', '');
		}
		// Other certificate checking
		if ($jq("#otherCertFile").is(':visible')) {
			displayExceptions(otherCert, availableLeavesDetailsJson[i].certificateName + ' Certificate is required\n', 'otherCertFile', 'otherCertExceptions', '');
		} // End
	}
}

function addSpecialCasualLeaves() {
	if (specialLeavesListJson != null) {
		for (var i = 0; i < specialLeavesListJson.length; i++) {
			$jq('#leaveSubType').append($jq("<option></option>").attr("value", specialLeavesListJson[i].id).text(specialLeavesListJson[i].leaveSubType)); 			
		}
	}
}

function addFamilyMembers() {
	if (familyImpactListJson != null) {		
		for (var k = 0; k < familyImpactListJson.length; k++) {
			if (familyImpactListJson[k].leaveTypeDetails.id == $jq("#leaveType").val()) {
				var arr = new Array();
				arr = familyMembersListJson;	
				$jq('#childName').find('option').remove().end().append($jq("<option></option>").attr("value", 'select').text('Select'));
				
				if (familyImpactListJson[k].childAge == 0 || familyImpactListJson[k].childAge == '') {
					$jq("#familyImpact").hide();
					confNoOfMembers = familyImpactListJson[k].noOfChildren;
					if (arr != null) {
						empChildrens = arr.length;		
					} else {
						empChildrens = 0;		
					}				
				} else {
					if (arr != null) {
						for (var l = 0; l < familyImpactListJson[k].noOfChildren;) {
							var dob = "first";
							for (m = 0; m < arr.length; m++) {
								if (arr[m] != null && arr[m] != undefined && (dob == arr[m].dob || dob == "first")) {
									if ($jq('#toDate').val() != '') {
										if((arr[m].phtypeFlag == 'N' && parseFloat(familyImpactListJson[k].childAge) * 365.26 > parseFloat(compareDates(convertDate(arr[m].strDob), convertDate($jq('#toDate').val()))))
												||(arr[m].phtypeFlag == 'Y' && parseFloat(familyImpactListJson[k].phChildAge) * 365.26 > parseFloat(compareDates(convertDate(arr[m].strDob), convertDate($jq('#toDate').val()))))) {
												$jq('#childName').append($jq("<option></option>").
												          attr("value",arr[m].id).
												          text(arr[m].name)); 		
										}
									} else {
										if((arr[m].phtypeFlag == 'N' && parseFloat(familyImpactListJson[k].childAge) * 365.26 > parseFloat(compareDates(convertDate(arr[m].strDob), convertDate($jq('#currentDate').val()))))
											||(arr[m].phtypeFlag == 'Y' && parseFloat(familyImpactListJson[k].phChildAge) * 365.26 > parseFloat(compareDates(convertDate(arr[m].strDob), convertDate($jq('#currentDate').val()))))) {
											$jq('#childName').append($jq("<option></option>").
											          attr("value",arr[m].id).
											          text(arr[m].name)); 		
										}
									}
									
									dob = arr[m].dob;
									arr = jQuery.grep(arr, function(value) {
									    return value != arr[m];
									});
									l++;
									m--;
								}
							}	
							if (arr.length == 0) {
								l++;
							}
						}
					}
				}
			}
		}
	}
}

function checkConstraints() {
	if (availableLeavesDetailsJson != null) {
		for (var i = 0; i < availableLeavesDetailsJson.length; i++) {
			if (availableLeavesDetailsJson[i].leaveTypeDetails.id == selectedLeave) {
				// Family Impact Flag
				if (availableLeavesDetailsJson[i].familyImpactFlag == 'Y') {
					$jq("#familyImpact").show();
					addFamilyMembers();
				}
				// Holidays Intervening flag checking
				$jq('maxDaysExceptions').text('');
				if (availableLeavesDetailsJson[i].holidayIntFlag == 'N') { // Don't considered holidays in between leave dates
					getNoOfDays('fromDate', 'toDate', selectedLeave, '', '');
				} else if (availableLeavesDetailsJson[i].holidayIntFlag == 'Y') {
					if ($jq('#fromDate').val() !== '' && $jq('#toDate').val() !== '') {
						$jq('#days').text(parseInt(compareDates(convertDate($jq('#fromDate').val()), convertDate($jq('#toDate').val()))) + 1)
					}
				}
				
				// Half Day Flag checking
				if (availableLeavesDetailsJson[i].halfDayFlag == 'N') {
					$jq("#fromHalfDayFlag").attr("checked", false);
					$jq("#toHalfDayFlag").attr("checked", false);	
					$jq('#halfDayCheck').hide();
				} else if (availableLeavesDetailsJson[i].halfDayFlag == 'Y') {
					$jq("#fromHalfDayFlag").attr("checked", false);
					$jq("#toHalfDayFlag").attr("checked", false);	
					$jq('#halfDayCheck').show();			
				}
				
				// Medical Certificate Flag
				if (availableLeavesDetailsJson[i].medicalCertFlag == 'Y') {
					$jq('#medicalFlagDiv').show();
				} else {
					$jq('mcExceptions').text('');
					$jq('#medicalFlagDiv').hide();
				}
				
				// Fitness Certificate Flag
				if (availableLeavesDetailsJson[i].fitnessCertFlag == 'Y') {
					$jq('#fitnessFlagDiv').show();
				} else {
					$jq('fitnessExceptions').text('');
					$jq('#fitnessFlagDiv').hide();
				}
				
				// New certificate Flag
				if (availableLeavesDetailsJson[i].otherCertFlag == 'Y') {
					$jq('#otherCertDiv').show();
					$jq('#otherCertificate').text(availableLeavesDetailsJson[i].certificateName).append('<span> *</span>').find('span').attr('style', 'color:red');
				} else {
					$jq('#otherCertExceptions').text('');
					$jq('#otherCertificate').text('');
					$jq('#otherCertDiv').hide();
				}
				break;
			}
		}
	}
}

function checkHolidaysInterveningFlag() {
	/** 
	 * check whether holidays should be considered or not,
	 * if considered then send a request to server, otherwise calculate in the script
	 */
	selectedLeave = $jq("#leaveType").val();
	if (availableLeavesDetailsJson != null) {
		for (var i = 0; i < availableLeavesDetailsJson.length; i++) {
			if (availableLeavesDetailsJson[i].leaveTypeDetails.id == selectedLeave) {
				if (selectedLeave == '11' && ($jq('#leaveSubType').val() == '6' || $jq('#leaveSubType').val() == '1' || $jq('#leaveSubType').val() == '4')) {
					getNoOfDays('fromDate', 'toDate', selectedLeave, '', '');
					break;
				}
				if (availableLeavesDetailsJson[i].holidayIntFlag == 'N') {
					// Don't considered holidays in between leave dates
					getNoOfDays('fromDate', 'toDate', selectedLeave, '', '');
				} else if (availableLeavesDetailsJson[i].holidayIntFlag == 'Y') {
					if ($jq('#fromDate').val() != '' && $jq('#toDate').val() != '') {
						if (selectedLeave == '1') {
							var currentDate = getCurrentDate();
							var currentMonthId = parseInt(getMonthID(currentDate.split('-')[1]), 10);
							var toMonthId = parseInt(getMonthID($jq('#toDate').val().split('-')[1]), 10);
							if (compareDate($jq('#toDate').val(), currentDate) && (((1 <= currentMonthId && currentMonthId <= 6) && (7 <= toMonthId && toMonthId <= 12)) || ((7 <= currentMonthId && currentMonthId <= 12) && (1 <= toMonthId && toMonthId <= 6)))) { 
								getNoOfDays('fromDate', 'toDate', selectedLeave, '', '');
							}
						}
						$jq('#days').text(parseInt(compareDates(convertDate($jq('#fromDate').val()), convertDate($jq('#toDate').val()))) + 1);
					}
				}
				break;
			}
		}
	}
}

function displayNone(){
	$jq("#subType").hide();
	$jq("#familyImpact").hide();
	$jq("#studyImpact").hide();
	$jq("#dateImpact").hide();
}

function exceptionsEmpty(){	
	$jq("#priorApprExceptions").text('');
	$jq("#minDaysExceptions").text('');
	$jq("#maxDaysExceptions").text('');
	$jq("#mcExceptions").text('');
	$jq("#fitnessExceptions").text('');
	$jq("#otherAvailExceptions").text('');
	$jq("#otherCertExceptions").text('');
}

function setHalfDay(id) {
	if (parseFloat($jq.trim($jq('#days').text())) > 0 && $jq('#fromDate').val() != '' && $jq('#toDate').val() != '') {
		if (id == 'fromHalfDayFlag') {
			if ($jq('#fromDate').val() == $jq('#toDate').val() && $jq('input:checkbox[name=toHalfDayFlag]').is(':checked')) {
				$jq("#fromHalfDayFlag").attr("checked", false);
				alert("Invalid");
			} else {
				if ($jq('input:checkbox[name=fromHalfDayFlag]').is(':checked')) {
					$jq('#prefix').hide();
					$jq('#days').text(parseFloat($jq.trim($jq('#days').text())) - parseFloat('0.5'));
				} else {
					$jq('#prefix').show();
					$jq('#days').text(parseFloat($jq.trim($jq('#days').text())) + parseFloat('0.5'));
				}
			}
		} else {
			if ($jq('#fromDate').val() == $jq('#toDate').val() && $jq('input:checkbox[name=fromHalfDayFlag]').is(':checked')) {
				$jq("#toHalfDayFlag").attr("checked", false);
				alert("Invalid");
			} else {
				if ($jq('input:checkbox[name=toHalfDayFlag]').is(':checked')) {
					$jq('#suffix').hide();
					$jq('#days').text(parseFloat($jq.trim($jq('#days').text())) - parseFloat('0.5'));
				} else {
					$jq('#suffix').show();
					$jq('#days').text(parseFloat($jq.trim($jq('#days').text())) + parseFloat('0.5'));
				}
			}	
		}
	} else {
		$jq("#fromHalfDayFlag").attr("checked", false);
		$jq("#toHalfDayFlag").attr("checked", false);
		alert("You should select date first\n");
	}
}

function getLeaveAddress() {
	$jq('#address').text('');
	$jq('#addressDiv').hide();
	$jq('#addressTextDiv').text('');
	$jq('#contactNumber').val('');
	
	if ($jq('#selectAddress').val() != 'select' && $jq('#selectAddress').val() != '4') {
		var sfID = "";
		if ($jq("#offlineSFID").is(':visible')) {
			sfID = $jq('#offlineSFID').val();
		} else {
			sfID = 'self';
		}
		if (isEmpty(addressDetails) ||  addressDetails.length == 0) {
			$jq.ajax( {
				type : "POST",
				url : "leaveRequest.htm",
				data : "param=address&selectAddress="
						+ $jq('#selectAddress').val() + '&sfID = ' + sfID,
				cache : false,
				success : function(data) {
					$jq("#result").html(data);
					setAddressDetails();
				}
			});
		} else {
			setAddressDetails();
		}
	} else if($jq('#selectAddress').val() == '4') {
		$jq('#addressDiv').show();
	}
}

function setAddressDetails() {
	if (!isEmpty(addressDetails)) {
		$jq.each(addressDetails, function(index, value) {
			if ((value.addressTypeId == $jq('#selectAddress').val()) || ($jq('#selectAddress').val() == '3' && value.addressTypeId == '4')) {
				var add = value.address1; 
				if (value.address2 != '') {
					add += ',\n' + value.address2; 
				}
				if (value.address3 != '') {
					add += ',\n' + value.address3;
				}
				if (value.city != '') {
					add +=  ',\n' + value.city;								
				}
				if (value.district != '') {
					add +=  ',\n' + value.district;
				}
				if (value.state != '') {
					add +=  ',\n' + value.state;
				}
				if (value.pincode != '') {
					add +=  ',\n' + value.pincode;
				}							
				$jq('#address').text(add);
				$jq('#contactNumber').val(value.phone);
				if (value.phone != '') {
					add += ',\n' + value.phone
				}								
				$jq('#addressTextDiv').text(add);								
			}
		});
	} else {
		$jq('#address').text('');
		$jq('#contactNumber').text('');
	}
}

function submitLeave(argument) {
	$jq('#result').text('');
	var argument = $jq.trim(argument);
	var errorMessage = "";
	var status = true;
	var sfID = "";
	var additionalData = "";
	var fromHalfDay = "N";
	var toHalfDay = "N";
	excepstatus = true;
	var leaveSubType = "";
	var mcExceptionID = "";
	
	if ($jq("#offlineSFID").is(':visible')) {
		if ($jq('#offlineSFID').val() == '') {
			errorMessage += 'Please Enter SFID\n';
			if (status) {
				status = false;
				$jq('#offlineSFID').focus();
			}
		} else {
			sfID = $jq('#offlineSFID').val().toUpperCase();
		}
	} else {
		sfID = "self";
	}
	if ($jq('#leaveType').val() == 'select') {
		errorMessage += 'Please select Nature of leave\n';
		if(status){
			status = false;
			$jq('#leaveType').focus();
		}		
	}
	if ($jq("#leaveSubType").is(':visible')) {
		if ($jq('#leaveSubType').val() == 'select') {
			errorMessage += 'Please select leave sub type\n';
			if(status){
				status = false;
				$jq('#leaveSubType').focus();
			}		
		} else {
			leaveSubType = $jq('#leaveSubType').val();
		}
	}
	if ($jq("#childName").is(':visible')) {
		if ($jq('#childName').val() == 'select') {
			errorMessage += 'Please select Child\n';
			if(status){
				status = false;
				$jq('#childName').focus();
			}		
		} else {
			additionalData = $jq('#childName option:selected').text();
		}
	}
	if ($jq("#studyDegree").is(':visible')) {
		if ($jq('#studyDegree').val() == 'select') {
			errorMessage += 'Please select degree\n';
			if(status){
				status = false;
				$jq('#studyDegree').focus();
			}		
		} else {
			additionalData = $jq('#studyDegree option:selected').text();
		}
	}
	if ($jq("#deliveryDate").is(':visible')) {
		if ($jq('#deliveryDate').val() == '') {
			errorMessage += 'Please select Delivery / Adoption date\n';
			if (status) {
				status = false;
				$jq('#deliveryDate').focus();
			}		
		} else {
			additionalData = $jq('#deliveryDate').val();
		}
	}
	if ($jq('#fromDate').val() == '') {
		errorMessage += 'Please select From Date\n';
		if (status) {
			status = false;
			$jq('#fromDate').focus();
		}
	}
	if ($jq('#toDate').val() == '') {
		errorMessage += 'Please select To Date\n';
		if (status) {
			status = false;
			$jq('#toDate').focus();
		}
	}
	if ($jq('#fromDate').val() != '' && $jq('#toDate').val() != '' && !compareDate($jq('#toDate').val(), $jq('#fromDate').val())) {
		errorMessage += "Invalid Dates \n";
		if (status) {
			status = false;
			$jq('#fromDate').focus();
		}
	} 
	// Added By Naresh
	if (retirementDate != '') {
		if (compareDate($jq('#fromDate').val(), retirementDate) || compareDate($jq('#toDate').val(), retirementDate)) {
			errorMessage += "Applying leave dates are crossing retirement date of an employee\n";
			if (status) status = false;
		}
	} // End
	/*if($jq('#fromDate').val() != '' && $jq('#toDate').val() != '') {
		var d1=getDateFromFormat(convertDate($jq('#toDate').val()),'dd-MM-yyyy');
		var d2=getDateFromFormat(convertDate($jq('#fromDate').val()),'dd-MM-yyyy');
		if (d1==0 || d2==0) {
			status = false;
			errorMessage += "Invalid Dates \n";
			$jq('#fromDate').focus();
			}
		else if (d1 < d2) {
			status = false;
			errorMessage += "Invalid Dates \n";
			$jq('#fromDate').focus();
			}
	}*/
	if ($jq("#halfDayCheck").is(':visible')) {
		if ($jq('input[name=fromHalfDayFlag]:checkbox').is(':checked')) {
			fromHalfDay = "Y";
		}
		if ($jq('input[name=toHalfDayFlag]:checkbox').is(':checked')) {
			toHalfDay = "Y";
		}
	}
	
	if ($jq('#reason').val() == '') {
		errorMessage += 'Please Enter Reason\n';
		if (status) {
			status = false;
			$jq('#reason').focus();
		}
	}
	if ($jq('#selectAddress').val() == 'select') {
		errorMessage += 'Please select Address\n';
		if (status) {
			status = false;
			$jq('#selectAddress').focus();
		}
	} else if($jq('#selectAddress').val() == '4' && $jq('#address').val() == '') {
		errorMessage += 'Please Enter Address\n';
		if (status) {
			status = false;
			$jq('#address').focus();
		}
	}
	if ($jq('#selectAddress').val() != 'select' && $jq('#selectAddress').val() != '4' && $jq('#addressTextDiv').text() == '') {
		errorMessage += 'Please select Other Address\n';
		if (status) {
			status = false;
			$jq('#address').focus();
		}
	}
	if ($jq('#contactNumber').val() == '') {
		errorMessage += 'Please Enter Contact Number\n';
		if (status) {
			status = false;
			$jq('#contactNumber').focus();
		}
	}
	
	// constraints
	if ($jq("#leaveSubType").is(':visible')) {
		// special casual leave
		if (specialLeavesListJson != null) {
			for(var i = 0; i < specialLeavesListJson.length; i++){
				if(specialLeavesListJson[i].id==$jq('#leaveSubType').val()){
					/**
					 * 1. prior approval check whether the leave date is greater
					 * than the system date, if yes check the priority flag. If
					 * the priority flag is 'Y', then check the exceptions. If
					 * the exceptions are available then display them otherwise
					 * give an alert
					 */
					if(specialLeavesListJson[i].priorApprovalFlag == 'Y' && !compareDate($jq('#fromDate').val(),$jq('#currentDate').val())){
						errorMessage += displayExceptions(priorAppr,'Prior Approval is required to apply this leave\n','fromDate','priorApprExceptions','');
					} else {
						$jq('#priorApprExceptions').text('');
						$jq('#priorApprExceptions').hide();
					}
					/**
					 * 2. Medical certificate checking check whether MC
					 * attachment displayed or not. If displayed then medical
					 * certificate is mandatory for this leave. If the user not
					 * attached any medical certificate, then check the
					 * exceptions, if the exceptions are available then display
					 * them otherwise give an alert
					 */
					if ($jq("#mcFile").is(':visible') && $jq("#mcFile").val()=='') {
						errorMessage += displayExceptions(medCert,'Medical Certificate is required\n','mcFile','mcExceptions','');
					} else {
						$jq('#mcExceptions').text('');
						$jq('#mcExceptions').hide();
					}
					/** 3. other certificate checking */
					if ($jq("#otherCertFile").is(':visible') && $jq("#otherCertFile").val()=='') {
						errorMessage += displayExceptions(otherCert,specialLeavesListJson[i].otherFileName+' Certificate is required\n','otherCertFile','otherCertExceptions','');
					} else {
						$jq('#otherCertExceptions').text('');
						$jq('#otherCertExceptions').hide();
					}
					/**
					 * 4. No of days If the current leave is type 2 special
					 * casual leave then we should check the noofdays otherwise
					 * check the dates
					 */
					errorMessage += checkSpecialCasualDates(specialLeavesListJson[i]);
				}
			}
		}
		
	} else {
		// other leaves
		if (availableLeavesDetailsJson != null) {
			for (var i = 0; i < availableLeavesDetailsJson.length; i++) {
				if (availableLeavesDetailsJson[i].leaveTypeDetails.id == $jq('#leaveType').val()) {
					dispExceptionsIds = "";
					/**
					 * 1. prior approval check whether the leave date is greater
					 * than the system date, if yes check the priority flag. If
					 * the priority flag is 'Y', then check the exceptions. If
					 * the exceptions are available then display them otherwise
					 * give an alert
					 */
					if (availableLeavesDetailsJson[i].priorApprovalFlag == 'Y' && !compareDate($jq('#fromDate').val(),$jq('#currentDate').val())) {
						errorMessage += displayExceptions(priorAppr,'Prior Approval is required to apply this leave\n','fromDate','priorApprExceptions','');
					} else {
						$jq('#priorApprExceptions').text('');
						$jq('#priorApprExceptions').hide();
					}				

					/**
					 * 2. Minimum number of leaves per spell Check whether the
					 * no of days less than the minimum number of leaves, if yes
					 * check the exceptions, if the exceptions are available
					 * then display them otherwise give an alert
					 */
					if (availableLeavesDetailsJson[i].minPerSpell != '' && parseFloat(availableLeavesDetailsJson[i].minPerSpell)>parseFloat($jq.trim($jq("#days").text()))) {
						errorMessage += displayExceptions(minLeaves, 'Please apply minimum of %1% leave/s\n', 'toDate', 'minDaysExceptions', availableLeavesDetailsJson[i].minPerSpell);
					} else {
						$jq('#minDaysExceptions').text('');
						$jq('#minDaysExceptions').hide();
					}
					
					/**
					 * 3. Maximum number of leaves per spell Check whether the
					 * no of days greater than the maximum number of leaves, if
					 * yes check the exceptions, if the exceptions are available
					 * then display them otherwise give an alert
					 */
					if ($jq('#leaveType').val() != '10') {
						if (availableLeavesDetailsJson[i].maxPerSPell != '' && parseFloat(availableLeavesDetailsJson[i].maxPerSPell) < parseFloat($jq.trim($jq("#days").text()))) {
							errorMessage += displayExceptions(maxLeaves, 'You can apply maximum of %1% leave/s\n', 'toDate', 'maxDaysExceptions', availableLeavesDetailsJson[i].maxPerSPell);
						} else {
							$jq('#maxDaysExceptions').text('');
							$jq('#maxDaysExceptions').hide();
						}
					} else {
						if (additionalData != '') {
							if (additionalData == 'Ph.D') {
								if (availableLeavesDetailsJson[i].maxPerSPell != '' && parseFloat(availableLeavesDetailsJson[i].maxPerSPell) < parseFloat($jq.trim($jq("#days").text()))) {
									errorMessage += displayExceptions(maxLeaves, 'You can apply maximum of %1% leave/s for ' + additionalData + '\n', 'toDate', 'maxDaysExceptions', availableLeavesDetailsJson[i].maxPerSPell);
								} else {
									$jq('#maxDaysExceptions').text('');
									$jq('#maxDaysExceptions').hide();
								}
							} else if (additionalData == 'PG') {
								if (availableLeavesDetailsJson[i].maxPerSPell != '' && parseFloat($jq.trim($jq("#days").text())) > 730) {
									errorMessage += displayExceptions(maxLeaves, 'You can apply maximum of %1% leave/s for ' + additionalData + '\n', 'toDate', 'maxDaysExceptions', 730);
								} else {
									$jq('#maxDaysExceptions').text('');
									$jq('#maxDaysExceptions').hide();
								}
							}
						}
					}
					
					/**
					 * 4. Medical certificate checking check whether MC
					 * attachment displayed or not. If displayed then medical
					 * certificate is mandatory for this leave. If the user not
					 * attached any medical certificate, then check the
					 * exceptions, if the exceptions are available then display
					 * them otherwise give an alert
					 */
					if ($jq("#mcFile").is(':visible') && $jq("#mcFile").val()=='') {
						errorMessage += displayExceptions(medCert, 'Medical Certificate is required\n', 'mcFile', 'mcExceptions', '');
					} else {
						$jq('#mcExceptions').text('');
						$jq('#mcExceptions').hide();
					}
					/**
					 * 5. Fitness certificate checking check whether FC
					 * attachment displayed or not. If displayed then medical
					 * certificate is mandatory for this leave. If the user not
					 * attached any medical certificate, then check the
					 * exceptions, if the exceptions are available then display
					 * them otherwise give an alert
					 */
					if ($jq("#fitnessFile").is(':visible') && $jq("#fitnessFile").val()=='') {
						errorMessage += displayExceptions(fitCert, 'Fitness Certificate is required\n', 'fitnessFile', 'fitnessExceptions', '');
					} else {
						$jq('#fitnessExceptions').text('');
						$jq('#fitnessExceptions').hide();
					}
					
					/** 6. other certificate checking */
					if ($jq("#otherCertFile").is(':visible') && $jq("#otherCertFile").val()=='') {
						errorMessage += displayExceptions(otherCert, availableLeavesDetailsJson[i].certificateName + ' Certificate is required\n', 'otherCertFile', 'otherCertExceptions', '');
					} else {
						$jq('#otherCertExceptions').text('');
						$jq('#otherCertExceptions').hide();
					}
					
					/** 7. other leaves credit checking* */
					if (availableLeavesDetailsJson[i].otherCreditCheck == 'Y' && checkOtherLeavesCredit($jq('#leaveType').val())) {
						/**
						 * If other leaves are available then employee should
						 * get the exception or alert
						 */
						errorMessage += displayExceptions(otherCredit, 'Other leaves are in credit\n', 'leaveType', 'otherAvailExceptions', '');
					} else {
						$jq('#otherAvailExceptions').text('');
						$jq('#otherAvailExceptions').hide();
					}
					
					/**
					 * 8. Available without leave credits. If the leave type is
					 * cl, check the applying year. If user applied the
					 * leaves(CL's) in previous year check in server.
					 */
					if (argument == '') {
						if (($jq('#leaveType').val() == cl && checkLeavesCredit($jq('#leaveType').val(), $jq.trim($jq('#days').text()), null)) ||
								($jq('#leaveType').val() != cl && (availableLeavesDetailsJson[i].availWOLeaves == 'N' && checkLeavesCredit($jq('#leaveType').val(), $jq.trim($jq('#days').text()), null)))) { // $jq('#currentDate').val().split('-')[2]==$jq('#toDate').val().split('-')[2]
							// Sufficient leaves are not available in his credit
							//errorMessage += "Only "+getLeaveCredits($jq('#leaveType').val())+" leaves are in credit\n";
							errorMessage += "Sorry! You don't have enough balance in your account\n";
							if (status) status = false;
						}
					} else {
						if (($jq('#leaveType').val() == cl && checkLeavesCredit($jq('#leaveType').val(), $jq.trim($jq('#days').text()), argument.split('#')[1])) ||
								($jq('#leaveType').val() != cl && (availableLeavesDetailsJson[i].availWOLeaves == 'N' && checkLeavesCredit($jq('#leaveType').val(), $jq.trim($jq('#days').text()), argument.split('#')[1])))) {
							errorMessage += "Sorry! You don't have enough balance in your account\n";
							if (status) status = false;
						}
					}
					
					/**
					 * 9. Direct debit checking If the leave are directly debit
					 * from other leave then check the leaves availability of
					 * that configuration leave But in case of EOL, the leaves
					 * are debit from EL's, If the EL are not in credit in that
					 * case also the user can able to apply this leave
					 */
					if (availableLeavesDetailsJson[i].directDebitFlag == 'N' && $jq('#leaveType').val() != eol) {
						// Sufficient leaves are not available in his credit
						errorMessage += checkConfiguredLeavesCredit($jq('#leaveType').val(), $jq.trim($jq('#days').text()), availableLeavesDetailsJson[i].debitMappingID,availableLeavesDetailsJson[i].debitMappingLeaves);
					}
					break;
				}
			}
		}
		
		/**
		 * If the leave is paternity leave, then the user is not able to apply
		 * after 6 months from the date of delivery
		 */
		if ($jq('#leaveType').val() == pl || $jq('#leaveType').val() == adpm) {
			/*if(addMonths($jq('#deliveryDate').val(),6)<createDDMMYYY(convertDate($jq('#toDate').val()))){
				errorMessage += $jq('#leaveType option:selected').text()+" can be applied with in 6 months after delivery/adoption date\n";
			}*/
			if ($jq('#leaveType').val() == pl) {
				if (createDDMMYYY(convertDate($jq('#fromDate').val())) < addDays($jq('#deliveryDate').val(), -15)) {
					errorMessage += $jq('#leaveType option:selected').text() + " can be applied after 15 days of delivery / adoption date\n";
				}
			}
			if (addMonths($jq('#toDate').val(), 0) > addMonths($jq('#deliveryDate').val(), 6)) { // Added by Naresh
				if (addMonths($jq('#fromDate').val(), 0) > addMonths($jq('#deliveryDate').val(), 6)) {
					errorMessage += $jq('#leaveType option:selected').text() + " can be applied with in 6 months after delivery / adoption date\n";
					if (status) status = false;
				} else {
					var date1 = new Date(createDDMMYYY(convertDate($jq('#fromDate').val())));
					var date2 = new Date(addMonths($jq('#deliveryDate').val(), 6));
					var days = Math.ceil(Math.abs(date1.getTime() - date2.getTime()) / (1000 * 3600 * 24));
					alert("You have only " + days + " day / days to apply for paternity leave\nPleae reselect the To Date field\n");
					if (status) {
						status = false;
						$jq('#toDate').val('');
					}
				}
			} // End
			
			if ($jq('#leaveType').val() == adpm) {
				if (createDDMMYYY(convertDate($jq('#fromDate').val())) < createDDMMYYY(convertDate($jq('#deliveryDate').val()))) {
					errorMessage += $jq('#leaveType option:selected').text() + " can be applied after delivery/adoption date\n";
				}
			}
		}
		/**
		 * If the leave type is cml, then we should debit the leaves from hpl in
		 * case of hpl are available, other wise give an errormessage alert
		 */
		if ($jq('#leaveType').val() == cml) {
			if(checkLeavesCredit(hpl, parseFloat($jq.trim($jq('#days').text())) * 2, null)) {
				errorMessage += "Only " + getLeaveCredits(hpl) + " HPL leaves are in credit\n";
			}
		}
		
		/**
		 * If the leave type is LND, we should check the HPL (If HPL's are not
		 * available then only the user can able to apply)
		 */
		if ($jq('#leaveType').val() == lnd) {
			if (!isLeaveZero(hpl)) {
				errorMessage += "HPL leave are available in your account.\n";
			}
		}
		
		/**
		 * CL's are not carry forward. So the user should apply the leaves in the current year only.
		 */
		/*if($jq('#leaveType').val()==cl){
			if($jq('#currentDate').val().split('-')[2]!=$jq('#toDate').val().split('-')[2]){
				errorMessage += "Insufficient CL balance in your account.\n";
			}			
		}*/		
	}
	//added by Narayana
	if ($jq('#leaveType').val()!='8') {
		confNoOfMembers = 0;
		empChildrens = 0;
	}
	
	if (confNoOfMembers < empChildrens && confNoOfMembers > 0) {
		errorMessage += "This leave can be applied only for first " + confNoOfMembers + " serviving children\n";
	}

	if (status && errorMessage == '' && excepstatus) {
		var medicalCert = "";
		var fitnessCert = "";
		var otherCertificate = "";
		var type = "";
		var referenceId = "";
		if ($jq("#mcFile").is(':visible')) {
			medicalCert = $jq("#mcFile").val();		
		}
		if ($jq("#fitnessFile").is(':visible')) {
			fitnessCert = $jq("#fitnessFile").val();	
		}
		if ($jq("#otherCertFile").is(':visible')) {
			otherCertificate = $jq("#otherCertFile").val();			
		}
		
		if ($jq("#mcExceptions").is(':visible') && ($jq('#leaveType').val() == 4 || $jq('#leaveType').val() == 5)) {
			mcExceptionID = $jq('input:radio[name=MED]:checked').val();
		}
		if (fromHalfDay == 'Y') {
			$jq('#prefix').text('');
		}
		if (toHalfDay == 'Y') {
			$jq('#suffix').text('');
		}
		if (argument != '') {
			type = 'amendment';
			referenceId = argument.split('#')[0];
		}
		leaveRequestDetails = {
			"param" : "submitRequest",
			"sfID" : sfID,
			"leaveType" : $jq('#leaveType').val(),
			"leaveSubType" : leaveSubType,
			"additionalData" : additionalData,
			"fromDate" : $jq('#fromDate').val(),
			"toDate" : $jq('#toDate').val(),
			"prefix" : $jq.trim($jq('#prefix').text()),
			"suffix" : $jq.trim($jq('#suffix').text()),
			"fromHalfDayFlag":fromHalfDay,
			"toHalfDayFlag":toHalfDay,
			"noOfDays" : $jq.trim($jq('#days').text()),
			"reason" : $jq('#reason').val(),
			"address" : $jq('#address').val(),
			"contactNumber" : $jq('#contactNumber').val(),
			"medicalCertName" : medicalCert,
			"fitnessCertName" : fitnessCert,
			"otherCertName" : otherCertificate,
			"otherCert" : otherCertificate,
			"serviceBookFlag" : "N",
			"exceptionalDetails" : JSON.stringify(getSeletedExceptions()),
			"remarks" : "",
			"type" : type,
			"referenceId" : referenceId
		};
		if (argument == '') {		
			submitLeaveReq(argument); //argument
		} else {
			return true;
		}
		/*for(var i=1;i<=4;i++) {
			if(i==1 && medicalCert!=""){
				leaveFileUpload('mcFile','Medical Certificate Upload failed.');
			}
			else if(i==2 && fitnessCert!=""){
				leaveFileUpload('fitnessFile','Fitness Certificate Upload failed.');
			}
			else if(i==3 && otherCertificate!=""){
				leaveFileUpload('otherCertFile',$jq('#otherCertificate').text()+' Certificate Upload failed.')
			}
			else if(i==4){
				if($jq.trim($jq('#result').text())==''){
					// send details to server
					submitLeaveReq();
				}
			}
		}*/		
	} else {
		if (errorMessage == '') {
			alert('Please check the exceptions');
		} else {
			alert(errorMessage);
		}
	}
}

function proceedLeave() {
	var status = true;
	leaveRequestDetails.param = "proceed";
	leaveRequestDetails.remarks = $jq.trim($jq("#remarks").text());
	leaveRequestDetails.type = "proceed";
	/*if (jsonPreviousDays != 0) {
		$jq('#fromDate').val(formatDateIntoString(addDays($jq('#fromDate').val(), -parseInt(jsonPreviousDays, 10)), 'dd-MMM-yyyy'));
	}
	if (jsonNextDays != 0) {
		$jq('#toDate').val(formatDateIntoString(addDays($jq('#toDate').val(), parseInt(jsonNextDays, 10)), 'dd-MMM-yyyy'));
	}
	$jq('#days').text(parseInt($jq('#days').text(), 10) + parseInt(jsonPreviousDays, 10) + parseInt(jsonNextDays, 10));
	$jq('#fromDate').css('background-color', '#ccff66');
	$jq('#toDate').css('background-color', '#ccff66');
	leaveRequestDetails.fromDate = $jq('#fromDate').val();
	leaveRequestDetails.toDate = $jq('#toDate').val();
	leaveRequestDetails.noOfDays = $jq.trim($jq('#days').text());*/
	status = !checkLeavesCredit($jq('#leaveType').val(), $jq.trim($jq('#days').text()), null);
	if (status) {
		leaveRequestDetails.fromDate = formatDateIntoString(addDays($jq('#fromDate').val(), parseInt(jsonPreviousDays, 10)), 'dd-MMM-yyyy');
		leaveRequestDetails.toDate = formatDateIntoString(addDays($jq('#toDate').val(), -parseInt(jsonNextDays, 10)), 'dd-MMM-yyyy');
		leaveRequestDetails.noOfDays = parseInt($jq('#days').text(), 10) - parseInt(jsonPreviousDays, 10) - parseInt(jsonNextDays, 10);
		$jq.ajax({
			type : "POST",
			url : "leaveRequest.htm",
			data : leaveRequestDetails,
			cache : false,
			success : function(html) {
				$jq("#result").html(html);
				if ($jq('.success').is(':visible')) {
					$jq('#applyLeaveBtn').show();
					debitLeaves(leaveRequestDetails.leaveType, parseInt(leaveRequestDetails.noOfDays, 10) + parseInt(jsonPreviousDays, 10) + parseInt(jsonNextDays, 10));
					getUpdatedLeaveDetails();
					resetLeave('');
					$jq('#fromDate').removeAttr('style');
					$jq('#toDate').removeAttr('style');
					$jq('#result').text('Successfully Sent Leave Request...');
					$jq('#result').attr('class', 'success');
					document.forms[0].requestId.value = $jq.trim(requestID);					
					document.forms[0].param.value = "requestDetails";
					document.forms[0].action = "workflowmap.htm";
					document.forms[0].submit();
				}
			}
		});
	} else {
		alert("Sorry! You donot have enough balance in your account\n");
	}
}

function cancelRemarks() {
	if (jsonPreviousDays != 0) {
		$jq('#fromDate').val(formatDateIntoString(addDays($jq('#fromDate').val(), parseInt(jsonPreviousDays, 10)), 'dd-MMM-yyyy'));
	}
	if (jsonNextDays != 0) {
		$jq('#toDate').val(formatDateIntoString(addDays($jq('#toDate').val(), -parseInt(jsonNextDays, 10)), 'dd-MMM-yyyy'));
	}
	$jq('#days').text(parseInt($jq('#days').text(), 10) - parseInt(jsonPreviousDays, 10) - parseInt(jsonNextDays, 10));
	$jq('#fromDate').removeAttr('style');
	$jq('#toDate').removeAttr('style');
	$jq('#result').text('');
	$jq('#applyLeaveBtn').show();
	leaveRequestDetails.type = "";
}

function submitLeaveReq(parameter) { 
	var check = (parameter == '') ? confirm("Leave period is from  " + leaveRequestDetails.fromDate + "  to  " + leaveRequestDetails.toDate + "   ( " + leaveRequestDetails.noOfDays + " )\n") : true;
	//var check = confirm("Leave period is from  " + leaveRequestDetails.fromDate + "  to  " + leaveRequestDetails.toDate + "   ( " + leaveRequestDetails.noOfDays + " )\n");
	if (check) {
		$jq.ajaxFileUpload({
			url : "leaveRequest.htm?" + $jq.param(leaveRequestDetails),
			secureuri : false,
			fileElementId : 'leaveFiles',
			success : function(data, status) {
				if (status == 'success') {
					$jq("#result").html($jq(data.body).html());
					if ($jq('.success').is(':visible')) {			
						debitLeaves(leaveRequestDetails.leaveType, leaveRequestDetails.noOfDays);
						getUpdatedLeaveDetails();
						//getRequestIdDetails(requestID);
						var check = (parameter == '') ? confirm("Leave Request has been sent successfully...\nPreview Leave Application Form & print from here\n") : confirm("Leave Amendment Request has been sent successfully...\nPreview Leave Amendment Application Form and take print from here\n");
						if (check) {
							document.forms[0].requestId.value = $jq.trim(requestID);
							//document.forms[0].roleId.value = 'roleId';
							document.forms[0].param.value = "requestDetails";
							document.forms[0].action = "workflowmap.htm";
							document.forms[0].submit();	
						}
						//resetLeave('submit');
					} else {
						if ($jq.trim(parameter) != '') {
							var remarksText = $jq.trim($jq('#result').find('div')[1].textContent);
							$jq.post('leaveRequest.htm?param=rollbackCancelAppliedLeave&requestID=' + parameter.split('#')[0], function(html) {
								$jq('#result').html(html);
								if ($jq('.success').hasClass('success')) {
									debitLeaves(leaveRequestDetails.leaveType, leaveRequestDetails.noOfDays);
									//alert("Sorry! You do not have enough balance in your account\n");
									$jq('#result').find('div')[0].innerHTML = remarksText;
									$jq('#result').attr('style', 'color:red');
								}
							});
						}
					}
				}
			},
			error : function(data, status, e) {
				alert('File upload failed');
			}
		});
	}
	/*$jq.ajax( {
		type : "POST",
		url : "leaveRequest.htm",
		data : leaveRequestDetails,
		cache : false,
		success : function(html) {			
			$jq("#result").html(html);
			if($jq('.success').is(':visible')){				
				debitLeaves(leaveRequestDetails.leaveType,leaveRequestDetails.noOfDays);
				getUpdatedLeaveDetails();
				resetLeave('submit');
			}
		}
	});*/
}

function getUpdatedLeaveDetails() {
	if ($jq(".success").is(':visible')) {
		$jq.post('leaveAdmin.htm?param=getLeaves&type=user', function(data) {
			$jq('#leaves').html(data);
	    });
	}
}

function leaveFileUpload(fileID,errorMsg){
	$jq.ajaxFileUpload
    ({
            url:'leaveRequest.htm?param=upload',
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


function getSeletedExceptions() {
	var exceptionDetails = {"priorApprExceptions":"","minDaysExceptions":"","maxDaysExceptions":"","mcExceptions":"","fitnessExceptions":"",
			"otherAvailExceptions":"","otherCertExceptions":""};
	if ($jq("#exceptions").is(':visible')) {
		if ($jq("#priorApprExceptions").is(':visible')) {
			exceptionDetails.priorApprExceptions = $jq('input:radio[name=PRIOR_APPR]:checked').val();
		}
		if ($jq("#minDaysExceptions").is(':visible')) {
			exceptionDetails.minDaysExceptions = $jq('input:radio[name=MIN_PER_SPELL]:checked').val();
		}
		if ($jq("#maxDaysExceptions").is(':visible')) {
			exceptionDetails.maxDaysExceptions = $jq('input:radio[name=MAX_PER_SPELL]:checked').val();
		}
		if ($jq("#mcExceptions").is(':visible')) {
			exceptionDetails.mcExceptions = $jq('input:radio[name=MED]:checked').val();
		}
		if ($jq("#fitnessExceptions").is(':visible')) {
			exceptionDetails.fitnessExceptions = $jq('input:radio[name=FIT]:checked').val();
		}
		if ($jq("#otherAvailExceptions").is(':visible')) {
			exceptionDetails.otherAvailExceptions = $jq('input:radio[name=OTHER_CREDIT]:checked').val();
		}
		if ($jq("#otherCertExceptions").is(':visible')) {
			exceptionDetails.otherCertExceptions = $jq('input:radio[name=OTHER_CERT]:checked').val();
		}
	}
	return exceptionDetails;
}

function checkSpecialCasualDates(specialLeavesObj){
	var errorMessage = "";
	if(specialLeavesObj.categoryType=='T1'){
		// Type 1
		if(parseFloat($jq.trim($jq('#days').text()))>parseFloat(specialLeavesObj.noOfDays)) {
			errorMessage = "You cannot apply more than "+specialLeavesObj.noOfDays+" leaves";
		}
	} else {
		// Type 2
		// check the dates
		var fordateArr1 = specialLeavesObj.strFromDate.split("-");
	    var formatDate1 = new Date(fordateArr1[2], getMonthID(fordateArr1[1])-1, fordateArr1[0]);
	    var fordateArr2 = specialLeavesObj.strToDate.split("-");
	    var formatDate2 = new Date(fordateArr2[2], getMonthID(fordateArr2[1])-1, fordateArr2[0]);	    
	    
	    var fromDateArr = $jq('#fromDate').val().split("-");
	    var toDateArr = $jq('#toDate').val().split("-");
	    var formatFromDate = new Date(fromDateArr[2], getMonthID(fromDateArr[1])-1, fromDateArr[0]);
	    var formatToDate = new Date(toDateArr[2], getMonthID(toDateArr[1])-1, toDateArr[0]);
	    
	    if(formatFromDate>formatToDate || formatDate1>formatFromDate ||  formatDate2<formatToDate){
	    	errorMessage += "Dates should be between "+specialLeavesObj.strFromDate+" and "+specialLeavesObj.strToDate+"\n";
	    }
	}			
	return errorMessage;
}

function displayExceptions(expType, errorMsg, focusID, resultID, replaceStr) {
	var errorMessage = "";
	var expstatus = true;
	var exceptions = "";
	var expDesc = "";
	dispExceptionsIds = "";
	if (exceptionalDetailsListJson != null) {
		for (var j = 0; j < exceptionalDetailsListJson.length; j++) {	
			// if ((id==11 then check subtype) or (leavetype!=11)) &&
			// name=extype
			if (((exceptionalDetailsListJson[j].leaveTypeID == 11 && exceptionalDetailsListJson[j].leaveSubTypeID == $jq('#leaveSubType').val())
						|| (exceptionalDetailsListJson[j].leaveTypeID != 11 && exceptionalDetailsListJson[j].leaveTypeID == $jq('#leaveType').val())) && exceptionalDetailsListJson[j].exceptionTypeDetails.name == expType)
			{
				if (expstatus) {
					$jq('#exceptions').show();
					$jq('#' + resultID).show();
					dispExceptionsIds += expType + '#';
					// Exception Title from Exception type master table
					expDesc = exceptionalDetailsListJson[j].exceptionTypeDetails.description;
					exceptions = '<div>' + expDesc + '</div>';
				}
				expstatus = false;								
				// display the exceptions from exception details table
				exceptions +='<div style=margin-left:20px><input type=radio id=\"' + exceptionalDetailsListJson[j].id + '\" name=\"' + expType + '\" value=\"' + exceptionalDetailsListJson[j].id + '\"/>' + exceptionalDetailsListJson[j].description + '</div>';
			}
		}
		if (expstatus) {
			errorMessage += errorMsg.replace('%1%', replaceStr);
			if (status) {
				$jq('#'+focusID).focus();
			}
		} else {
			// check whether the employee checked the radio button or not
			if ($jq('#'+resultID).text() != '') {
				var excep = dispExceptionsIds.split("#");
				for (var i = 0; i < excep.length; i++) {
					if (excep[i] == expType && undefined == $jq("input[name='" + expType + "']:checked").val()) {
						// give an alert for checking radio
						errorMessage += "Please select " + expDesc + "\n";
					}
				}
			} else {
				$jq('#'+resultID).html(exceptions);
				if (excepstatus) {
					excepstatus = false;
				}
			}	
		}
	} else {
		errorMessage += errorMsg.replace('%1%', replaceStr);
		if (status) {
			$jq('#' + focusID).focus();
		}
	}
	return errorMessage;
}

function checkOtherLeavesCredit(leaveTypeID) {
	if (leaveCreditsListJson == null) {
		return true;
	} else {
		for (var k = 0; k < leaveCreditsListJson.length; k++) {
			if (leaveCreditsListJson[k].id != leaveTypeID) {
				return true;
			}
		}
	}
	return false;
}

function checkLeavesCredit(leaveTypeID, noOfDays, input) {
	var input = (input != null) ? parseFloat(input) : input;
	if (leaveCreditsListJson != null) {
		for (var k = 0; k < leaveCreditsListJson.length; k++) {
			if (leaveCreditsListJson[k].leaveTypeDetails.id == leaveTypeID && leaveTypeID == '3') {
				return isLeaveApplicable(leaveTypeID, roundNumber(parseFloat(leaveCreditsListJson[k].availableLeaves), 2), parseFloat(noOfDays), input);
			} else if (leaveCreditsListJson[k].leaveTypeDetails.id == leaveTypeID && leaveTypeID == '1') {
				return isLeaveApplicable(leaveTypeID, roundNumber(parseFloat(leaveCreditsListJson[k].availableLeaves), 2), parseFloat(noOfDays), input);
			}  else if (leaveCreditsListJson[k].leaveTypeDetails.id == leaveTypeID && roundNumber(parseFloat(leaveCreditsListJson[k].availableLeaves), 2) >= parseFloat(noOfDays)) {
				return false;
			}
		}
	}	
	return true;
}

function isLeaveApplicable(leaveId, currentBalance, days, inputDays) {
	if (leaveId == '3' || leaveId == '1') {
		var fromYear = parseInt($jq('#fromDate').val().split('-')[2], 10);
		var toYear = parseInt($jq('#toDate').val().split('-')[2], 10);
		var currentYear = parseInt(getCurrentDate().split('-')[2], 10);
		var currentToken = 0.0;
		var futureToken = 0.0;
		var futureLeaves = 0.0;
		if (currentNextYearBalance != '' && futureAvailableLeaves != '') {
			currentToken = parseFloat(currentNextYearBalance.split('#')[0]);
			futureToken = parseFloat(currentNextYearBalance.split('#')[1]);
			futureLeaves = parseFloat(futureAvailableLeaves);
		}

		if (fromYear == currentYear && currentYear == toYear) {
			return (inputDays == null) ? (currentBalance >= days ? false : true) : (currentBalance + inputDays >= days) ? false : true;
		} else if (fromYear <= currentYear && currentYear < toYear) {
			if ($jq('#halfDayCheck').is(':visible')) {
				if ($jq('#fromHalfDayFlag').is(':checked')) {
					currentToken -= parseFloat($jq('#fromHalfDayFlag').val()); 
				}
				if ($jq('#toHalfDayFlag').is(':checked')) {
					futureToken -= parseFloat($jq('#toHalfDayFlag').val()); 
				}
			}
			return (currentBalance >= currentToken && futureLeaves >= futureToken) ? false : true;
		} else if (fromYear > currentYear && toYear > currentYear) {
			if ($jq('#halfDayCheck').is(':visible')) {
				if ($jq('#fromHalfDayFlag').is(':checked')) {
					futureToken -= parseFloat($jq('#fromHalfDayFlag').val()); 
				}
				if ($jq('#toHalfDayFlag').is(':checked')) {
					futureToken -= parseFloat($jq('#toHalfDayFlag').val()); 
				}
			}
			return (futureLeaves >= futureToken) ? false : true;
		}
	}
	return false;
}

function debitLeaves(leaveTypeID, noOfDays) {
	if (leaveCreditsListJson != null) {
		for (var k = 0; k < leaveCreditsListJson.length; k++) {
			if (leaveCreditsListJson[k].leaveTypeDetails.id == leaveTypeID) {				
				leaveCreditsListJson[k].availableLeaves = roundNumber(parseFloat(leaveCreditsListJson[k].availableLeaves) - parseFloat(noOfDays), 2);
				return false;
			}
		}
	}
	return true;
}

function creditLeaves(leaveTypeId, days) {
	if (leaveCreditsListJson != null) {
		for (var k = 0; k < leaveCreditsListJson.length; k++) {
			if (leaveCreditsListJson[k].leaveTypeDetails.id == leaveTypeId) {				
				leaveCreditsListJson[k].availableLeaves = roundNumber(parseFloat(leaveCreditsListJson[k].availableLeaves) + parseFloat(days), 2);
				break;
			}
		}
	}
}

function isLeaveZero(leaveTypeID){
	if (leaveCreditsListJson!=null){
		for (var k = 0; k < leaveCreditsListJson.length; k++) {
			if (leaveCreditsListJson[k].leaveTypeDetails.id == leaveTypeID && roundNumber(parseFloat(leaveCreditsListJson[k].availableLeaves),2)<=0) {
				return true;
			}
		}
	}
	return false;
}

function getLeaveCredits(leaveTypeID) {
	if (leaveCreditsListJson != null) {
		for (var k = 0; k < leaveCreditsListJson.length; k++) {
			if (leaveCreditsListJson[k].leaveTypeDetails.id == leaveTypeID) {
				return roundNumber(leaveCreditsListJson[k].availableLeaves, 2);
			}
		}
	}
	return 0;
}

function checkConfiguredLeavesCredit(leaveTypeID,noOfDays,mapLeaveID,configLeaves){
	if(leaveCreditsListJson==null){
		return 'Only 0 '+leaveCreditsListJson[k].leaveTypeDetails.leaveType+'\'s are in credit\n';
	} else {
		for ( var k = 0; k < leaveCreditsListJson.length; k++) {
			if (leaveCreditsListJson[k].id == mapLeaveID){
				if(roundNumber(parseFloat(leaveCreditsListJson[k].availableLeaves))<parseFloat(configLeaves*noOfDays)){
					return 'Only '+roundNumber(leaveCreditsListJson[k].availableLeaves,2)+' '+leaveCreditsListJson[k].leaveTypeDetails.leaveType+'\'s are in credit\n';
				}
			}
		}
	}	
	return '';
}

function selectedSubLeaveType() {
	resetLeave('leaveSubType');
	if (specialLeavesListJson != null) {
		for (var i = 0; i < specialLeavesListJson.length; i++) {
			if (specialLeavesListJson[i].id == $jq('#leaveSubType').val()) {
				if (specialLeavesListJson[i].categoryType == 'T2') { // Type 2
					$jq('#fromDate').val(specialLeavesListJson[i].strFromDate);
					$jq('#toDate').val(specialLeavesListJson[i].strToDate);
					getNonBreakupHolidays('prefix');
					getNonBreakupHolidays('suffix');
					$jq('#days').text(parseInt(compareDates(convertDate($jq('#fromDate').val()), convertDate($jq('#toDate').val()))) + 1);
				}
				// Medical Certificate Flag
				if (specialLeavesListJson[i].medicalCertFlag == 'Y') {
					$jq('#medicalFlagDiv').show();
					$jq('#mmc').show();
				} else {
					$jq('mcExceptions').text('');
					$jq('#medicalFlagDiv').hide();
				}
				// New certificate Flag
				if (specialLeavesListJson[i].otherCertFlag == 'Y') {
					$jq('#otherCertDiv').show();
					$jq('#otherCertificate').html(specialLeavesListJson[i].otherFileName + '<span class=failure style=display:none id=moc>&nbsp;*</span>');
					$jq('#moc').show();
				} else {
					$jq('#otherCertExceptions').text('');
					$jq('#otherCertificate').text('');
					$jq('#otherCertDiv').hide();
				}
				break;
			}
		}
	}	
}

function checkEmployee() {
	$jq('#result').text('');
	var sfid = $jq.trim($jq("#searchSfid").val()); 
	if (sfid != '' && sfid.length >= 6) {
		/*$jq.ajax({
			type : "POST",
			url : "leaveRequest.htm",
			data : "param=checkEmp&searchSfid=" + sfid.toUpperCase(),
			cache : false,
			success : function(html) {
				$jq("#result").html(html);
			}
		});*/
		$jq.ajax({
				type : "POST",
				url : "leaveAdmin.htm",
				data : "param=checkEmp&searchSfid=" + sfid.toUpperCase(),
				cache : false,
				success : function(html) {
					$jq("#result").html(html);
					if (years != null && years != "") {
						$jq('#yearID').find('option:not(:first)').remove().end();
						for (var i = 0; i < years.length; i++) {
							$jq("#yearID").append('<option value=' + years[i].id + '>' + years[i].name + '</option>');
						}
					} else {
						$jq('#yearID').find('option:not(:first)').remove().end();
					}
				}
		});
	}
}
/*
 * function leaveApplication1(id){
 * window.open('./leaveRequest.htm?param=leaveReport&folder=LeaveApplications&requestID='+id,'Leave','fullscreen=no,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no') }
 */
function leaveApplication(id,type,requestType){
	window.open('./report.htm?param=leaveReport&type='+type+'&requestID='+id+'&requestType='+requestType,'Leave','fullscreen=no,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no')
}

function getLeaveReport() {
	var status = true;
	var errorMessage = "";
	var type = "";
	if($jq.trim($jq("#searchSfid").val()) == '') {
		errorMessage = "Please Enter SFID\n";
		if(status) {
			status = false;
			$jq('#searchSfid').focus();
		}
	} 
	if (!$jq('input:radio[name=type]').is(':checked')) {
		errorMessage += "Please Select Type\n";
		if(status)
			status = false;
	}
	if($jq("#yearID").val() == '0') {
		errorMessage += "Please Select Year\n";
		if(status)
			status = false;
	}
	if(status) {
		if($jq('input:radio[id=type1]').is(':checked')) {
			type = "card";
		} else {
			type = "account";
		}
		if($jq(".success").is(':visible')) {
			window.open('./leaveRequest.htm?param=leaveAccount&folder=LeaveAccount&searchSfid=' + $jq('#searchSfid').val().toUpperCase() + "&type=" + type + "&year=" + $jq("#yearID option:selected").text(), 'Leave','fullscreen=no,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no')
		} else {
			alert("Invalid SFID");
		}
	} else {
		alert(errorMessage);
	}
}

function resetLeaveAccountSearch() {
	$jq("#searchSfid").val('');
	$jq('input:radio[name=type]').removeAttr('checked');
	$jq("#result").text('');
	$jq('#yearID').find('option:not(:first)').remove().end();
	$jq("#yearID").val('0');
}
/**
 * Leave Data entry script starts
 */
function resetEnterAvailableLeaves() {
	$jq('#entrySfid').val('SF0');
	$jq('#earnedLeaveId').val('');
	$jq('#halfPayLeaveId').val('');
	$jq('#casualLeaveId').val('');
	$jq('#leaveNotDue').val('');
	$jq('#childCareLeave').val('');
	$jq('#studyLeave').val('');
	$jq('#eolLeaveId').val('');
	$jq('#eolWOMCLeaveId').val('');
	$jq('#eolWOMCInSerive').val('');
	$jq('#cmlwomc').val('');
	$jq('#leaveNotDuewomc').val('');
	$jq('#ltcEncashmentDays').val('LTC Encashed Days');
	$jq('#cclYearSpells').val('Availed Spells in year');
	// $jq('#cclServiceSpells').val('Availed Spells in service');
	// $jq('#slYearSpells').val('Availed Spells in year');
	// $jq('#slServiceSpells').val('Availed Spells in service');
	$jq('#ltcEncashmentDays').attr('style','color:#A29A9A');
	$jq('#cclYearSpells').attr('style','color:#A29A9A');
	// $jq('#cclServiceSpells').attr('style','color:#A29A9A');
	// $jq('#slYearSpells').attr('style','color:#A29A9A');
	// $jq('#slServiceSpells').attr('style','color:#A29A9A');
}


function submitEnterAvailableLeaves() {
	var flag = true;
	var errorMessage = "";
	var verifyFlag = "N";
	var cclYearSpells = $jq('#cclYearSpells').val();
	// var cclServiceSpells = $jq('#cclServiceSpells').val();
	var eolWOMCInSerive = $jq('#eolWOMCInSerive').val();
	var designationID =  $jq('#designationID').val();
	if ($jq('#entrySfid').val() == '' || $jq('#entrySfid').val().substring(3) == "") {
		errorMessage += "Please Enter SFID\n";
		if (flag) {
			$jq('#entrySfid').focus();
			flag = false;
		}
	}
	if ($jq('#earnedLeaveId').val() == '') {
		errorMessage += "Please Enter Earned Leaves\n";
		if (flag) {
			$jq('#earnedLeaveId').focus();
			flag = false;
		}
	}
	if ($jq('#ltcEncashmentDays').val() == 'LTC Encashed Days') {
		errorMessage += "Please Enter LTC Encashment Days\n";
		if (flag) {
			$jq('#ltcEncashmentDays').focus();
			flag = false;
		}
	}
	if ($jq('#halfPayLeaveId').val() == '') {
		errorMessage += "Please Enter Half Pay Leaves\n";
		if (flag) {
			$jq('#halfPayLeaveId').focus();
			flag = false;
		}
	}
	if ($jq('#casualLeaveId').val() == '') {
		errorMessage += "Please Enter Casual Leaves\n";
		if (flag) {
			$jq('#casualLeaveId').focus();
			flag = false;
		}
	}
/*
	if ($jq('#leaveNotDue').val() == '') {
		errorMessage += "Please Enter LeaveNot Due\n";
		if (flag) {
			$jq('#leaveNotDue').focus();
			flag = false;
		}
	}
	if ($jq('#childCareLeave').val() != '') {
		if (cclYearSpells == 'Availed Spells in year') {
			errorMessage += "Please Enter CCL Number of spells in present year\n";
			if (flag) {
				$jq('#cclYearSpells').val('');
				$jq('#cclYearSpells').focus(); flag = false;
			}
		}
		if (cclServiceSpells == 'Availed Spells in service') {
			errorMessage += "Please Enter CCL Number of spells in entire service\n";
			if (flag) {
				$jq('#cclServiceSpells').focus();
				flag = false;
			}
		}
	} else {
		cclYearSpells = ''; //cclServiceSpells = 0;
	}
	if ($jq('#studyLeave').val() == '') {
		errorMessage += "Please Enter Study Leaves\n";
		if (flag) {
			$jq('#studyLeave').focus(); flag = false;
		}
	}
	if ($jq('#slYearSpells').val() == 'Availed Spells in year') {
	errorMessage += "Please Enter Study leaves Number of spells in present year\n";
		if (flag) {
			$jq('#slYearSpells').focus();
			flag = false;
		}
	}
	if ($jq('#slServiceSpells').val() == 'Availed Spells in service') {
		errorMessage += "Please Enter Study leaves Number of spells in entire service\n";
		if (flag) {
			$jq('#slServiceSpells').focus(); flag = false;
		}
	}
*/
	if ($jq('#verifyFlag').is(':visible') && $jq('input:checkbox[id=verifyFlag]').is(':checked')) {
		verifyFlag = "Y";
	}
	if (flag) {
		requestDetails = {
			"entrySfid" : $jq('#entrySfid').val().toUpperCase(),
			"earnedLeaveId" : $jq('#earnedLeaveId').val(),
			"halfPayLeaveId" : $jq('#halfPayLeaveId').val(),
			"casualLeaveId" : $jq('#casualLeaveId').val(),
			"leaveNotDue" : $jq('#leaveNotDue').val(),
			"childCareLeave" : $jq('#childCareLeave').val(),
			"cclYearSpells" :$jq('#cclYearSpells').val(),
			"studyLeave" : $jq('#studyLeave').val(),		
			// "cclServiceSpells" : cclServiceSpells,
			// "slYearSpells" : $jq('#slYearSpells').val(),
			// "slServiceSpells" : $jq('#slServiceSpells').val(),
			"eolWOMCInSerive" : $jq('#eolWOMCInSerive').val(),
			"ltcEncashmentDays" : $jq('#ltcEncashmentDays').val(),
			"eolLeaveId" : $jq('#eolLeaveId').val(),
			"eolWOMCLeaveId" : $jq('#eolWOMCLeaveId').val(),
			"cmlwomc" : $jq('#cmlwomc').val(),
			"leaveNotDuewomc" : $jq('#leaveNotDuewomc').val(),			
			"verifyFlag" : verifyFlag,
			"param" : "saveEnterLeaves",
			"designationID" : $jq('#designationID').val()
		};
		if ($jq('#verifyFlag').is(':visible')) {
			requestDetails.param = "verifyLeaves";
			$jq.post('leaveAdmin.htm?' + dispUrl, requestDetails, function(html) {
				$jq("#result").html(html);
				resetLeaveBalance();
			});
		} else {
			if (leavesExists) {				
				$jq.prompt('Do you want to overwrite?', {
					callback: mycallbackform,
					buttons: { Ok: 'Ok', Cancel: 'Cancel' }
				});
			} else {
				$jq.post('leaveAdmin.htm?' + dispUrl, requestDetails, function(html) {
					$jq("#result").html(html);
					updateJsonAvailableLeaves();
					resetEnterAvailableLeaves();
				});
			}
		}
	} else {
		alert(errorMessage);
	}
}

function mycallbackform(v, m, f) {
	if (v == 'Ok') {
		$jq.post('leaveAdmin.htm?' + dispUrl, requestDetails, function(html) {
			$jq("#result").html(html);
			updateJsonAvailableLeaves();
			resetEnterAvailableLeaves();
		});
	}
}

function updateJsonAvailableLeaves() {
	if (jsonAvailabeLeavesList != null) {
		var status = true;
		for (var i = 0; i < jsonAvailabeLeavesList.length; i++) {
			if (jsonAvailabeLeavesList[i].sfID == $jq('#entrySfid').val().toUpperCase()) {
				status = false;
				jsonAvailabeLeavesList[i].cclyear = $jq('#cclYearSpells').val();
				jsonAvailabeLeavesList[i].hpl = $jq('#halfPayLeaveId').val();
				jsonAvailabeLeavesList[i].cl = $jq('#casualLeaveId').val();
				jsonAvailabeLeavesList[i].lnd = $jq('#leaveNotDue').val();
				jsonAvailabeLeavesList[i].ccl = $jq('#childCareLeave').val();
				jsonAvailabeLeavesList[i].stl = $jq('#studyLeave').val();
				jsonAvailabeLeavesList[i].el = $jq('#earnedLeaveId').val();
				jsonAvailabeLeavesList[i].eol = $jq('#eolLeaveId').val();
				jsonAvailabeLeavesList[i].eolwomc = $jq('#eolWOMCLeaveId').val();
				jsonAvailabeLeavesList[i].cmlwomc = $jq('#cmlwomc').val();
				jsonAvailabeLeavesList[i].lndwomc = $jq('#leaveNotDuewomc').val();
			}
		}
		if (status) {
			var lengh = jsonAvailabeLeavesList.length++;		
			var requestDetails = {
					"sfID" : $jq('#entrySfid').val().toUpperCase(),
					"el" : $jq('#earnedLeaveId').val(),
					"hpl" : $jq('#halfPayLeaveId').val(),
					"cl" : $jq('#casualLeaveId').val(),
					"lnd" : $jq('#leaveNotDue').val(),
					"ccl" : $jq('#childCareLeave').val(),
					"stl" : $jq('#studyLeave').val(),
					"eol" : $jq('#eolLeaveId').val(),
					"eolwomc" : $jq('#eolWOMCLeaveId').val(),
					"cmlwomc" : $jq('#cmlwomc').val(),
					"cclyear": $jq('#cclYearSpells').val(),
					"lndwomc" : $jq('#leaveNotDuewomc').val(),
					"verifiedBy" : ""				
			};
			jsonAvailabeLeavesList[lengh] = requestDetails;
		}
	} else {
		jsonAvailabeLeavesList = new Array();
		var requestDetails = {
				"sfID" : $jq('#entrySfid').val().toUpperCase(),
				"el" : $jq('#earnedLeaveId').val(),
				"hpl" : $jq('#halfPayLeaveId').val(),
				"cl" : $jq('#casualLeaveId').val(),
				"lnd" : $jq('#leaveNotDue').val(),
				"ccl" : $jq('#childCareLeave').val(),
				"stl" : $jq('#studyLeave').val(),
				"eol" : $jq('#eolLeaveId').val(),
				"eolwomc" : $jq('#eolWOMCLeaveId').val(),
				"cmlwomc" : $jq('#cmlwomc').val(),
				"lndwomc" : $jq('#leaveNotDuewomc').val(),
				"cclyear": $jq('#cclYearSpells').val(),
				"verifiedBy" : ""
		};
		jsonAvailabeLeavesList[0] = requestDetails;		
	}
}

function getSpellsDetails() {
	//alert("2");
	var userId = $jq.trim($jq('#entrySfid').val()).toUpperCase();
	if (userId != '' && userId.length >= 6) {
		//alert("2  1");
		// Get the spells details
		$jq.post('leaveAdmin.htm?param=spellsDetails&sfID=' + userId + '&' + dispUrl, function(html) {
			$jq("#result").html(html);
			if (jsonLeaveSpellsList.ltcEncashmentDays != null) {
				$jq('#ltcEncashmentDays').val(jsonLeaveSpellsList.ltcEncashmentDays);
			}
			if (jsonLeaveSpellsList.cclYearSpellsCount != null) {
				$jq('#cclYearSpells').val(jsonLeaveSpellsList.cclYearSpellsCount);
			}
			if (jsonLeaveSpellsList.eolwomc != null) {
				$jq('#eolWOMCInSerive').val(jsonLeaveSpellsList.eolwomc);
			}			
			// if(jsonLeaveSpellsList.cclServiceSpellsCount!=null){$jq('#cclServiceSpells').val(jsonLeaveSpellsList.cclServiceSpellsCount);}
			// if(jsonLeaveSpellsList.slYearSpellsCount!=null){$jq('#slYearSpells').val(jsonLeaveSpellsList.slYearSpellsCount);}
			// if(jsonLeaveSpellsList.slServiceSpellsCount!=null){$jq('#slServiceSpells').val(jsonLeaveSpellsList.slServiceSpellsCount);}
		});	
	}
}

function fillAvailableLeaves(sfidValue) {
	//alert("1");
	if (sfidValue != '' && sfidValue.length >= 6) {
		leavesExists = false;
		$jq('#earnedLeaveId').val('');
		$jq('#halfPayLeaveId').val('');
		$jq('#casualLeaveId').val('');
		$jq('#leaveNotDue').val('');
		$jq('#childCareLeave').val('');
		$jq('#studyLeave').val('');	
		$jq('#eolLeaveId').val('');	
		$jq('#eolWOMCLeaveId').val('');
		$jq('#cmlwomc').val('');	
		$jq('#leaveNotDuewomc').val('');
		$jq('#eolWOMCInSerive').val('');
		$jq('#ltcEncashmentDays').val('LTC Encashed Days');
		$jq('#cclYearSpells').val('Availed Spells in year');
		// $jq('#cclServiceSpells').val('Availed Spells in service');
		// $jq('#slYearSpells').val('Availed Spells in year');
		// $jq('#slServiceSpells').val('Availed Spells in service');
		$jq('#ltcEncashmentDays').attr('style','color:#A29A9A');
		$jq('#cclYearSpells').attr('style','color:#A29A9A');
		// $jq('#cclServiceSpells').attr('style','color:#A29A9A');
		// $jq('#slYearSpells').attr('style','color:#A29A9A');
		// $jq('#slServiceSpells').attr('style','color:#A29A9A');
		
		if (jsonAvailabeLeavesList != null) {
			for (var i = 0; i < jsonAvailabeLeavesList.length; i++) {
				if (jsonAvailabeLeavesList[i].sfID == sfidValue.toUpperCase()) {
					if (jsonAvailabeLeavesList[i].verifiedBy == "") {
						leavesExists = true;
						$jq('#earnedLeaveId').val(jsonAvailabeLeavesList[i].el);
						$jq('#halfPayLeaveId').val(jsonAvailabeLeavesList[i].hpl);
						$jq('#casualLeaveId').val(jsonAvailabeLeavesList[i].cl);
						$jq('#leaveNotDue').val(jsonAvailabeLeavesList[i].lnd);
						$jq('#childCareLeave').val(jsonAvailabeLeavesList[i].ccl);
						$jq('#studyLeave').val(jsonAvailabeLeavesList[i].stl);	
						$jq('#eolLeaveId').val(jsonAvailabeLeavesList[i].eol);
						$jq('#eolWOMCLeaveId').val(jsonAvailabeLeavesList[i].eolwomc);
						$jq('#cmlwomc').val(jsonAvailabeLeavesList[i].cmlwomc);
						$jq('#leaveNotDuewomc').val(jsonAvailabeLeavesList[i].lndwomc);
					} else {
						$jq('#entrySfid').val('');
						alert("Already verified");
						return;
					}				
				}
			}
		}
	}
}

function resetLeaveBalance() {
	$jq('#earnedLeaveId').removeAttr('disabled');
	$jq('#halfPayLeaveId').removeAttr('disabled');
	$jq('#casualLeaveId').removeAttr('disabled');
	$jq('#leaveNotDue').removeAttr('disabled');
	$jq('#childCareLeave').removeAttr('disabled');
	$jq('#studyLeave').removeAttr('disabled');
	$jq('#eolLeaveId').removeAttr('disabled');
	$jq('#eolWOMCLeaveId').removeAttr('disabled');
	$jq('#ltcEncashmentDays').removeAttr('disabled');
	$jq('#cclYearSpells').removeAttr('disabled');
	$jq('#eolWOMCInSerive').removeAttr('disabled');
	// $jq('#cclServiceSpells').removeAttr('disabled');
	// $jq('#slYearSpells').removeAttr('disabled');
	// $jq('#slServiceSpells').removeAttr('disabled');
	$jq('#cmlwomc').removeAttr('disabled');
	$jq('#leaveNotDuewomc').removeAttr('disabled');	
	$jq('#verifyFlag').removeAttr('checked');
	$jq('#verifyFlag').removeAttr('disabled');
	$jq('#button').show();
	$jq('#entrySfid').val('');
	$jq('#earnedLeaveId').val('');
	$jq('#halfPayLeaveId').val('');
	$jq('#casualLeaveId').val('');
	$jq('#leaveNotDue').val('');
	$jq('#childCareLeave').val('');
	$jq('#studyLeave').val('');	
	$jq('#eolLeaveId').val('');
	$jq('#eolWOMCLeaveId').val('');
	$jq('#cmlwomc').val('');
	// $jq('#designationID').val('Select');
	$jq('#leaveNotDuewomc').val('');
	$jq('#eolWOMCInSerive').val('');
	$jq('#ltcEncashmentDays').val('LTC Encashed Days');
	$jq('#cclYearSpells').val('Availed Spells in year');
	// $jq('#cclServiceSpells').val('Availed Spells in service');
	// $jq('#slYearSpells').val('Availed Spells in year');
	// $jq('#slServiceSpells').val('Availed Spells in service');
	$jq('#ltcEncashmentDays').attr('style','color:#A29A9A');
	$jq('#cclYearSpells').attr('style','color:#A29A9A');
	// $jq('#cclServiceSpells').attr('style','color:#A29A9A');
	// $jq('#slYearSpells').attr('style','color:#A29A9A');
	// $jq('#slServiceSpells').attr('style','color:#A29A9A');
}

function editLeaveBalance(id, sfid, cl, hpl, el, lnd, ccl, stl, verifiedBy, encash, cclyear, cclservice, slyear, slservice, eol, eolwomc, cmlwomc, lndwomc, eolinservice) {	
	resetLeaveBalance();
	
	$jq('#entrySfid').val(sfid);
	$jq('#earnedLeaveId').val(el);
	$jq('#halfPayLeaveId').val(hpl);
	$jq('#casualLeaveId').val(cl);
	$jq('#leaveNotDue').val(lnd);
	$jq('#childCareLeave').val(ccl);
	$jq('#studyLeave').val(stl);
	$jq('#eolLeaveId').val(eol);
	$jq('#eolWOMCLeaveId').val(eolwomc);
	$jq('#cmlwomc').val(cmlwomc);
	$jq('#leaveNotDuewomc').val(lndwomc);
	$jq('#eolWOMCInSerive').val(eolinservice);
	if (encash == '') {
		$jq('#ltcEncashmentDays').val('LTC Encashed Days');
		$jq('#ltcEncashmentDays').attr('style','color:#A29A9A');
	} else {
		$jq('#ltcEncashmentDays').val(encash);
	}
	if (cclyear == '') {
		$jq('#cclYearSpells').val('Availed Spells in year');
		$jq('#cclYearSpells').attr('style','color:#A29A9A');
	} else {
		$jq('#cclYearSpells').val(cclyear);
	}
	if (cclservice == '') {
		// $jq('#cclServiceSpells').val('Availed Spells in service');
		// $jq('#cclServiceSpells').attr('style','color:#A29A9A');
	} else {
		// $jq('#cclServiceSpells').val(cclservice);
	}
	if (slyear == '') {
		// $jq('#slYearSpells').val('Availed Spells in year');
		// $jq('#slYearSpells').attr('style','color:#A29A9A');
	} else {
		// $jq('#slYearSpells').val(slyear);
	}
	if (slservice == '') {
		// $jq('#slServiceSpells').val('Availed Spells in service');
		// $jq('#slServiceSpells').attr('style','color:#A29A9A');
	} else {
		// $jq('#slServiceSpells').val(slservice);
	}
	
	if (verifiedBy != "") {
		$jq('#verifyFlag').attr('checked', 'checked');
		$jq('#verifyFlag').attr('disabled', 'disabled');
		$jq('#verifyFlag').css('background-color', '#f2f2f2');
		$jq('#entrySfid').attr('disabled', 'disabled');
		if (el != '') {
			$jq('#earnedLeaveId').attr('disabled', 'disabled');
		}
		if (hpl != '') {
			$jq('#halfPayLeaveId').attr('disabled', 'disabled');
		}
		if (cl != '') {
			$jq('#casualLeaveId').attr('disabled', 'disabled');
		}
		if (lnd != '') {
			$jq('#leaveNotDue').attr('disabled', 'disabled');
		}
		if (ccl != '') {
			$jq('#childCareLeave').attr('disabled', 'disabled');
		}
		if (stl != '') {
			$jq('#studyLeave').attr('disabled', 'disabled');	
		}
		if (eol != '') {
			$jq('#eolLeaveId').attr('disabled', 'disabled');	
		}
		if (eolwomc != '') {
			$jq('#eolWOMCLeaveId').attr('disabled', 'disabled');
		}
		if(encash != '') {
			$jq('#ltcEncashmentDays').attr('disabled', 'disabled');
		}
		if (cclyear != '') {
			$jq('#cclYearSpells').attr('disabled', 'disabled');
		}
		// $jq('#cclServiceSpells').attr('disabled','disabled');
		// $jq('#slYearSpells').attr('disabled','disabled');
		// $jq('#slServiceSpells').attr('disabled','disabled');
		if (cmlwomc != '') {
			$jq('#cmlwomc').attr('disabled', 'disabled');
		}
		if (lndwomc != '') {
			$jq('#leaveNotDuewomc').attr('disabled', 'disabled');
		}
		if (eolinservice != '') {
			$jq('#eolWOMCInSerive').attr('disabled', 'disabled');
		}
		if (el != '' && hpl != '' && cl != '' && lnd != '' && ccl != '' && stl != '' && eol != '' && eolwomc != '' && encash != '' &&
				cclyear != '' && cmlwomc != '' && lndwomc != '' && eolinservice != '') {
			$jq('#button').hide();
		} else {
			$jq('#button').show();
		}
	}
}

function verifyCheck() {
	if ($jq('input:checkbox[id=verifyFlag]').is(':checked')) {
		$jq('#entrySfid').attr('disabled', 'disabled');
		if ($jq('#earnedLeaveId').val() != '') {
			$jq('#earnedLeaveId').attr('disabled', 'disabled');
		}
		if ($jq('#halfPayLeaveId').val() != '') {
			$jq('#halfPayLeaveId').attr('disabled', 'disabled');
		}
		if ($jq('#casualLeaveId').val() != '') {
			$jq('#casualLeaveId').attr('disabled', 'disabled');
		}
		if ($jq('#leaveNotDue').val() != '') {
			$jq('#leaveNotDue').attr('disabled', 'disabled');
		}	
		if ($jq('#childCareLeave').val() != '') {
			$jq('#childCareLeave').attr('disabled', 'disabled');
		}
		if ($jq('#studyLeave').val() != '') {
			$jq('#studyLeave').attr('disabled', 'disabled');	
		}
		if ($jq('#eolLeaveId').val() != '') {
			$jq('#eolLeaveId').attr('disabled', 'disabled');
		}
		if ($jq('#eolWOMCLeaveId').val() != '') {
			$jq('#eolWOMCLeaveId').attr('disabled', 'disabled');
		}
		if ($jq('#ltcEncashmentDays').val() != 'LTC Encashed Days') {
			$jq('#ltcEncashmentDays').attr('disabled', 'disabled');
		}
		if ($jq('#cclYearSpells').val() != 'Availed Spells in year') {
			$jq('#cclYearSpells').attr('disabled', 'disabled');
		}
		// $jq('#cclServiceSpells').attr('disabled', 'disabled');
		// $jq('#slYearSpells').attr('disabled', 'disabled');
		// $jq('#slServiceSpells').attr('disabled', 'disabled');
		if ($jq('#cmlwomc').val() != '') {
			$jq('#cmlwomc').attr('disabled', 'disabled');
		}
		if ($jq('#leaveNotDuewomc').val() != '') {
			$jq('#leaveNotDuewomc').attr('disabled', 'disabled');
		}
		if ($jq('#eolWOMCInSerive').val() != '') {
			$jq('#eolWOMCInSerive').attr('disabled', 'disabled');
		}
	} else {
		$jq('#entrySfid').removeAttr('disabled');
		$jq('#earnedLeaveId').removeAttr('disabled');
		$jq('#halfPayLeaveId').removeAttr('disabled');
		$jq('#casualLeaveId').removeAttr('disabled');
		$jq('#leaveNotDue').removeAttr('disabled');
		$jq('#childCareLeave').removeAttr('disabled');
		$jq('#studyLeave').removeAttr('disabled');
		$jq('#eolLeaveId').removeAttr('disabled');
		$jq('#eolWOMCLeaveId').removeAttr('disabled');
		$jq('#ltcEncashmentDays').removeAttr('disabled');
		$jq('#cclYearSpells').removeAttr('disabled');
		// $jq('#cclServiceSpells').removeAttr('disabled');
		// $jq('#slYearSpells').removeAttr('disabled');
		// $jq('#slServiceSpells').removeAttr('disabled');
		$jq('#cmlwomc').removeAttr('disabled');
		$jq('#leaveNotDuewomc').removeAttr('disabled');	
		$jq('#eolWOMCInSerive').removeAttr('disabled');			
	}
}

function getSearchEmployees() {
	$jq.post('leaveAdmin.htm?param=empSearch&designationID=' + $jq('#designationID').val(), function(data) {
		$jq('#result').html(data);
	});	
}

function getGazettedType() {
	$jq('#param').val('gazetted');
	$jq('#type').val('');
	gazetted = $jq('#gazettedType option:selected').val();
	$jq.post('leaveAdmin.htm', $jq('#leaveAdmin').serialize(), function(html) {
		$jq('#result').html(html);
		$jq('#gazettedType').val(gazetted);
		$jq('#doPartNumberDiv').show();
	});	
}

/**
 * Leave Data entry script ends
 */

/**
 * Leave DO Part script starts
 */
function resetLeaveSearch() {
	$jq('#fromDate').val('');
	$jq('#toDate').val('');
}


function leaveSearch(type) {
	var status = true;
	var errorMessage = "";
	var flag = true;
	
	if (document.getElementById('fromDate').value == '') {
		errorMessage += "Please Select From Date\n";
		if (status) {
			$jq('#fromDate').focus();
			status = false;
		}
	}
	if (document.getElementById('toDate').value == '') {
		errorMessage += "Please Select To Date\n";
		if (status) {
			$jq('#toDate').focus();
			status = false;
		}
	}
	if (!document.getElementById('toDate').value == '') {
		var dateCheck = document.getElementById('fromDate').value;
		var tod = document.getElementById('toDate').value;
		   if (!compareDate(tod, dateCheck)) {
				errorMessage += "Invalid Date..[To Date Is should after  Form Date] \n";
				if (flag) {
					document.getElementById('toDate').focus();
					flag = false;
				}
		    }
    }
	
//	if (($jq('#fromDate').val() == '' && $jq('#toDate').val() != '') || ($jq('#fromDate').val() != '' && $jq('#toDate').val() == '')) {
//		errorMessage += "Please select two dates\n";
//		if (status) {
//			status = false;
//			$jq('#fromDate').focus();
//		}
//	}		
//	if(compareDate($jq('#fromDate').val(),$jq('#toDate').val())) {
//		errorMessage += "Invalid Dates No Dates Availble \n";
//		if (status) {
//			status = false;
//			$jq('#fromDate').focus();
//		}
//	}
	if (status) {
		$jq.post('leaveAdmin.htm?param=getLeaves&fromDate=' + $jq('#fromDate').val() + '&toDate=' + $jq('#toDate').val() + '&type=' + type,    
			function(data) { 
		       	$jq('#result').html(data);
		       	$jq('#conversionOfLeave').show();
		       	$jq('#grantOfLeave').show();
		       	$jq('#cancellationOfLeave').show();
			} 
		);
	} else {
		alert(errorMessage);
	}
}

function completeRequests() {
	var checkedValues = "";
	var errorMessage = "";
	var gazType = "";
	var dno = "";
	var cid = "";
	var status = true;
	if ($jq('#doPartNo').val() == "") {
		errorMessage = "Please enter do part number\n";
		$jq('#doPartNo').focus();
		status = false;
	}
	if ($jq('#casualityId').val() == 0) {
		errorMessage = "Please select Casuality\n";
		$jq('#casualityId').focus();
		status = false;
	}
	if ($jq('#serialNumber').val() == "") {
		errorMessage = "Please Enter Serial Number\n";
		$jq('#serialnumber').focus();
		status = false;
	}

	/*if($jq('#doPartDate').val()==""){
		errorMessage += "Please select do part date\n";
		if(status){
			$jq('#doPartDate').focus();
			status = false;
		}
	}*/
	if ($jq('#gazettedType').val() == "Select") {
		errorMessage += "Please select Gazetted Type\n";
		if (status) {
			$jq('#gazettedType').focus();
			status = false;
		}
	}
	if ($jq('#convertall').is(':checked')) {
		$jq('#serialNumberDiv').show();
	} else {
		$jq('#serialNumberDiv').hide();
	}
	$jq("#leaveList tr:not(:first)").each(function() {	
		if ($jq(this).find("td").eq(0).find(":input:checkbox").is(':checked')) {
			checkedValues += $jq(this).find("td").eq(0).find(":input:checkbox").attr('id') + "#leave" + ","
		}
	});
		
	$jq("#leaveCancelList tr:not(:first)").each(function() {	
		if ($jq(this).find("td").eq(0).find(":input:checkbox").is(':checked')) {
			checkedValues += $jq(this).find("td").eq(0).find(":input:checkbox").attr('id') + "#leaveCancel" + ","
		}
	});
	
	$jq("#leaveConvertList tr:not(:first)").each(function() {	
		if ($jq(this).find("td").eq(0).find(":input:checkbox").is(':checked')) {
			checkedValues += $jq(this).find("td").eq(0).find(":input:checkbox").attr('id') + "#leaveConvert" + ","
		}
	});
	if (checkedValues == '') {
		errorMessage += "Please select any request\n";
		status = false;
	}
	if (status) {
		gazType = $jq('#gazettedType').val();
		dno = $jq('#doPartNo').val().split('#');
		dnos  = $jq('#doPartNo').val();
		cid = $jq('#casualityId').val();
		var requestDetails = {
				"doPartNo" : $jq('#doPartNo').val(),
				"doPartDate" : $jq('#doPartDate').val(),
				"gazettedType" : $jq('#gazettedType').val(),
				"casualityId" : $jq('#casualityId').val(),
				"serialNumber" : $jq('#serialNumber').val(),
				"param" : "publishLeaves",
				"requestIDs":checkedValues,	
		};
		$jq.ajax( {
			type : "POST",
			url : "leaveAdmin.htm",
			data : requestDetails,
			cache : false,
			success : function(html) {
			$jq('#result').html(html);
			$jq('#gazettedType').val(gazType);
			getGazettedType();
			if (dno[0] != "") {
				$jq('#doPartNo').val(dnos);
				getdoPartButton();	
			} else {
				$jq('#saveButtonDiv').show();
            	$jq('#publishButtonDiv').hide();
            	var date = '#'+formatDate(new Date(),'dd-MMM-yyyy');
            	$jq('#sysdate').val(date);
            		 $jq('#doPartNo').val(dnos);
				}
				getDateWiseLeaveList();
				$jq('#casualityId').val(cid);
				getLeaveSearchList();
			}
		});	
	} else {
		alert(errorMessage);
	}	
}

function resetLeaveTxnView(){
	$jq("#yearID").val('0');
	$jq('input:radio[name=type]').removeAttr('checked');
}

function getLeaveTxnView(){
	var status = true;
	var errorMessage = "";
	
	if($jq("#yearID").val()=='0'){
		errorMessage = "Please select year\n";
		if(status){
			status = false;
			$jq("#yearID").focus();
		}
	} 
	if (!$jq('input:radio[name=type]').is(':checked')) {
		errorMessage += "Please select type\n";
		if (status) {
			status = false;
			$jq("#type1").focus();
		}
	}
	if(status){
		if($jq('input:radio[id=type1]').is(':checked')){
			document.forms[0].year.value = $jq("#yearID option:selected").text();
			document.forms[0].param.value = "leavecard";
			document.forms[0].action = "leaveRequest.htm";
			document.forms[0].submit();			
		} else {
			window.open('./leaveRequest.htm?param=leaveAccount&folder=LeaveAccount&year='+$jq("#yearID option:selected").text(),'Leave','fullscreen=no,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no')
		}		
	} else {
		alert(errorMessage);
	}
}


function resetDoPartSearch(){
	$jq("#doPartNo").val('');
	$jq("#doPartDate").val('');
	$jq("#gazettedType").val('');
	$jq("#searchSfid").val('');
	$jq('#year').val('');
}

/*function getDoPartReport(id){	
	 window.open('./leaveRequest.htm?param=leaveReport&folder=LeaveDoParts&requestID=DO_PART_'+id,'Leave','fullscreen=no,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no')
}*/
function doPartSearch(){
	if($jq("#doPartNo").val()=='' && $jq("#doPartDate").val()=="" && $jq("#gazettedType").val()=="" && $jq("#searchSfid").val()=="" && $jq("#year").val()==""){
		alert("Please Select Gazetted Type and/or do part number and/or do part date and/or year for search");
	} else {
		$jq.post('leaveAdmin.htm?param=getDoPartList&doPartNo='+$jq('#doPartNo').val()+'&doPartDate='+$jq('#doPartDate').val()+'&gazettedType='+$jq("#gazettedType").val()+'&year='+$jq("#year").val()+'&searchSfid='+$jq("#searchSfid").val().toUpperCase(), function(html) {
			$jq("#result").html(html);		
		});	
	}	
}
/**
 * Leave DO Part script ends
 */


/**
 * DO Part-II Script starts
 */
function submitTypeDetails(){
	var errorMessage = "";
	var status = true;
	if($jq('#name').val()==""){
		errorMessage = "Please enter Name\n";
		$jq('#name').focus();
		status = false;
	}
	else if($jq('#name').val().length > 20){
		errorMessage = "Name should not GreaterThan 20 Characters\n";
		$jq('#name').focus();
		status = false;
	}
	
	
	if($jq('#prefixName').val()==""){
		errorMessage += "Please enter Prefix\n";
		$jq('#prefixName').focus();
		status = false;
	}
	else if($jq('#prefixName').val().length > 10){
		errorMessage = "Prefix should not GreaterThan 10 Characters\n";
		$jq('#prefixName').focus();
		status = false;
	}
	if($jq('#delimeter').val()==""){
		errorMessage += "Please enter Delimeter\n";
		$jq('#delimeter').focus();
		status = false;
	}else if($jq('#delimeter').val().length > 10){
		errorMessage = "Delimeter should not GreaterThan 10 Characters\n";
		$jq('#delimeter').focus();
		status = false;
	}

	if($jq('#description').val() != "G" ){
		if($jq('#description').val() != "NG" ){
		errorMessage += "Description should be either  G or NG \n";
		$jq('#description').focus();
		status = false;
		}
	}
	
	if (status) {
		var requestDetails = {
			"nodeID" : nodeID,
			"name" : $jq("#name").val().toUpperCase(),
			"prefixName" : $jq("#prefixName").val(),
			"description" :$jq("#description").val(),
			"delimeter" :$jq("#delimeter").val(),
			"param" : "saveTypeDetails"
		};
		$jq.ajax( {
			type : "POST",
			url : "leaveAdmin.htm",
			data : requestDetails,
			cache : false,
			success : function(html) {
				$jq('#result').html(html);
				if ($jq('.success').is(':visible')) {
					resetTypeDetails();
				}
			}
		});
	} else {
		alert(errorMessage);
	}

}
function editTypeDetails(id,name,prefixName,description,delimeter){
	nodeID = id;
	$jq('#name').val(name);
	$jq('#prefixName').val(prefixName);
	$jq('#description').val(description);
	$jq('#delimeter').val(delimeter);
}
function resetTypeDetails(){
	nodeID=0;
	$jq('input').val("");
	$jq('textarea').val("");
}
function deleteTypeDetails(id){
	var requestDetails = {
			"nodeID" : id,
			"param" : "deleteTypeDetails"
		};
		$jq.ajax( {
			type : "POST",
			url : "leaveAdmin.htm",
			data : requestDetails,
			cache : false,
			success : function(html) {
				$jq('#result').html(html);
			}
		});
}
function getGazettedWiseCasualityList(){
	var requestDetails = {
			"typeId" :$jq('#typeId').val(),
			"moduleId":$jq('#moduleId').val(),
			"param" : "getGazettedWiseCasuality"
		};
		$jq.ajax( {
			type : "POST",
			url : "leaveAdmin.htm",
			data : requestDetails,
			cache : false,
			success : function(html) {
				$jq('#result').html(html);
			}
		});

}
function submitCasualityDetails(){
	var errorMessage = "";
	var status = true; 
	
	if($jq('#typeId').val()==""){
		errorMessage = "Please select Gazetted Type\n";
		$jq('#typeId').focus();
		status = false;
	}
	
	if($jq('#moduleId').val()==0){
		errorMessage += "Please select Module\n";
		$jq('#moduleId').focus();
		status = false;
	}
	if($jq('#name').val()==''){
		errorMessage += "Please Enter Casuality\n";
		$jq('#name').focus();
		status = false;
	}
	if($jq('#code').val()==''){
		errorMessage += "Please Enter Code\n";
		$jq('#code').focus();
		status = false;
	}
	if($jq('#order').val()==''){
		errorMessage += "Please Enter Order\n";
		$jq('#order').focus();
		status = false;
	}

	if (status) {
		var requestDetails = {
			"nodeID" : nodeID,
			"name" : $jq("#name").val(),
			"moduleId" : $jq("#moduleId").val(),
			"typeId" : $jq("#typeId").val(),
			"description" :$jq("#description").val(),
			"order" :$jq("#order").val(),
			"code":$jq("#code").val(),
			"param" : "saveCasualityDetails"
		};
		$jq.ajax( {
			type : "POST",
			url : "leaveAdmin.htm?"+dispUrl,
			data : requestDetails,
			cache : false,
			success : function(html) {
				$jq('#result').html(html);
				nodeID=0;
				//if ($jq('.success').is(':visible')) {
					resetCasualityDetails1();
				//}
			}
		});
	} else {
		alert(errorMessage);
	}

}
function resetCasualityDetails1() {
	$jq('#moduleId').val(0);
	$jq('#name').val("");
	$jq('#description').val("");
	$jq('#order').val("");
	$jq('#typeId').val(0);
	$jq('#code').val("");
	nodeID=0;
}

function editCasualityDetails(id,moduleId,name,description,orderNo,typeId,code){
	nodeID = id;
	$jq('#moduleId').val(moduleId);
	$jq('#name').val(name);
	$jq('#description').val(description);
	$jq('#order').val(orderNo);
	$jq('#typeId').val(typeId);
	$jq('#code').val(code);
}
function deleteCasualityDetails(id){
	var typeId;
	var moduleId;
	if($jq('#typeId').val()!=''){
		typeId=$jq('#typeId').val();
	}
	if($jq('#moduleId').val()!=''){
		moduleId=$jq('#moduleId').val();
	}
	var requestDetails = {
			"nodeID" : id,
			"typeId" : typeId,
			"moduleId" : moduleId,
			"param" : "deleteCasualitiesDetails"
		};
		$jq.ajax( {
			type : "POST",
			url : "leaveAdmin.htm",
			data : requestDetails,
			cache : false,
			success : function(html) {
				$jq('#result').html(html);
			}
		});
	
}
function resetCasualityDetails(){
	nodeID="";
	$jq('input:text').val("");
	$jq('select').val("0");
	$jq('textarea').val("");
	
}
function submitTaskHolderDetails(){
	var errorMessage = "";
	var status = true;
	if($jq('#typeId').val()==0){
		errorMessage = "Please select Type\n";
		$jq('#typeId').focus();
		status = false;
	}
	if($jq('#roleId').val()==0){
		errorMessage += "Please select Role\n";
		$jq('#roleId').focus();
		status = false;
	}

	if (status) {
		var requestDetails = {
			"nodeID" : nodeID,
			"roleId" : $jq("#roleId").val(),
			"typeId" : $jq("#typeId").val(),
			"remarks" : $jq("#remarks").val(),
			"param" : "saveTaskHolderDetails"
		};
		$jq.ajax( {
			type : "POST",
			url : "leaveAdmin.htm",
			data : requestDetails,
			cache : false,
			success : function(html) {
				$jq('#result').html(html);
				if ($jq('.success').is(':visible')) {
					resetTaskHolderDetails();
				}
			}
		});
	} else {
		alert(errorMessage);
	}

}
function getDeselectedDesig(){
	
}
function resetTaskHolderDetails(){
	nodeID="";
	$jq('input:text').val("");
	$jq('select').val("0");
	$jq('textarea').val("");
}
function editTaskHolderDetails(id,roleId,remarks,typeId){
	nodeID=id;
	$jq('#roleId').val(roleId);
	$jq('#remarks').val(remarks);
	$jq('#typeId').val(typeId);
}
function deleteTaskHolderDetails(id){
	var requestDetails = {
			"nodeID" : id,
			"param" : "deleteTaskHolderDetails"
		};
		$jq.ajax( {
			type : "POST",
			url : "leaveAdmin.htm",
			data : requestDetails,
			cache : false,
			success : function(html) {
				$jq('#result').html(html);
			}
		});

}
function submitTypeDesigDetails(){
	var errorMessage = "";
	var status = true;
	var selectedDesig = "";
	$jq("#SelectRight option").each(function() {
		selectedDesig += $jq(this).val() + ",";
	});
	if($jq('#typeId').val()==0){
		errorMessage = "Please select Type\n";
		$jq('#typeId').focus();
		status = false;
	}
	if (selectedDesig == "") {
		errorMessage += "Please select designation.\n";
		if (status) {
			$jq('#SelectLeft').focus();
			status = false;
		}
	}
	if (status) {
		if (selectedDesig != "") {
			selectedDesig = selectedDesig.substring(0,
					selectedDesig.length - 1);
		}
		
		var requestDetails = {
			"nodeID" : nodeID,
			"typeId" : $jq('#typeId').val(),
			"remarks" : $jq('#remarks').val(),
			"designation" : selectedDesig,
			"param" : "manageTypeDesignations"
		};
		$jq.ajax( {
			type : "POST",
			url : "leaveAdmin.htm?"+dispUrl,
			data : requestDetails,
			cache : false,
			success : function(html) {
				$jq('#result').html(html);
				if ($jq('.success').is(':visible')) {
					resetTypeDesigDetails();
					getDeselectedDesig();
				}
			}
		});
	}else {
		alert(errorMessage);
	}

}

function resetTypeDesigDetails(){
	nodeID="";
	nodeIds="";
	$jq('input:text').val("");
	$jq('select').val("0");
	$jq('textarea').val("");
	jQuery('#SelectRight option').remove();
	if ($jq('#SelectRight') != null) {
		$jq("#SelectRight > option").each(
				function() {
					$jq("#SelectRight").append(
							'<option value=' + this.value + '>' + this.text
									+ '</option>');
					$jq(this).remove();
				});
	}

}

function editTypeDesigDetails(id,typeId,remarks,desigIds,nodeIds,designationListJSON,deselectedDesig){

	//resetTypeDesigDetails();  //kranthi
	//editNewTypeDesignations(); //kranthi
	nodeID=id;
	nodeIds=nodeIds;
	$jq('#typeId').val(typeId);
	$jq('#remarks').val(remarks);
	var desigList = desigIds.split(',');
	jQuery('#SelectRight option').remove();
	for(var i=0; i<designationListJSON.length; i++){
		
    	for(var j=0;j<desigList.length;j++) 
    		{
    			if(desigList[j]==designationListJSON[i].id){
    				$jq('#SelectRight').append($jq("<option></option>").attr("value",designationListJSON[i].id).text(designationListJSON[i].name));
    				j=desigList.length;
    			}
    		}
	}
	
	/* kranthi 
	for ( var i = 0; i < desigList.length; i++) {
		$jq("#SelectLeft > option").each(
				function() {
					if (desigList[i] == this.value) {
						$jq("#SelectRight").append(
								'<option value=' + this.value + '>' + this.text
										+ '</option>');
						$jq(this).remove();
					}
				});
	} */
}
function editNewTypeDesignations(){
$jq('#SelectRight').find('option').remove().end();
if(designationListJSON!=null){
	for(i=0;i<designationListJSON.length;i++){
		$jq('#SelectRight').append($jq("<option></option>").attr("value",designationListJSON[i].id).text(designationListJSON[i].name));
		}
    }
}
function deleteTypeDesigDetails(typeId){
	var del=confirm("Do you want to delete this Record?");
	if(del==true) {
	var requestDetails = {
			"typeId" : typeId,
			"param" : "deleteTypeDesigDetails"
		};
		$jq.ajax( {
			type : "POST",
			url : "leaveAdmin.htm",
			data : requestDetails,
			cache : false,
			success : function(html) {
				$jq('#result').html(html);
			}
		});
	}
}
function TypeselectedDesignation() {
	$jq('#SelectRight').find('option').remove().end();
	$jq('#SelectLeft').find('option').remove().end();
	if (jsonArrayObject != null) {
		for ( var i = 0; i < jsonArrayObject.length; i++) {
			if ($jq("#gazType").val() == 0
					|| jsonArrayObject[i].key == $jq("#gazType").val() || ($jq("#gazType").val()=='GAZ' && jsonArrayObject[i].key=='GAZETTED') || ($jq("#gazType").val()=='NG' && jsonArrayObject[i].key=='NON GAZETTED')) {
				$jq("#SelectLeft").append(
						'<option value=' + jsonArrayObject[i].id + '>'
								+ jsonArrayObject[i].name + '</option>');
			}
		}
	}
}
function submitTaskHolderDesig(){
	var errorMessage = "";
	var status = true;
	var selectedDesig = "";
	$jq("#SelectRight option").each(function() {
		selectedDesig += $jq(this).val() + ",";
	});
	if($jq('#taskHolderMap').val()==0){
		errorMessage = "Please select TaskHolder\n";
		$jq('#typeId').focus();
		status = false;
	}
	if (selectedDesig == "") {
		errorMessage += "Record Can't save for Task Holder With out any Designation.\n";
		if (status) {
			$jq('#SelectLeft').focus();
			status = false;
		}
	}
	if (status) {
		if (selectedDesig != "") {
			selectedDesig = selectedDesig.substring(0,
					selectedDesig.length - 1);
		}
		
		var requestDetails = {
			"nodeID" : nodeID,
			"taskHolderMap" :$jq('#taskHolderMap').val().split('#')[0],
			"designation" : selectedDesig,
			"param" : "manageTaskHolderDesig"
		};
		$jq.ajax( {
			type : "POST",
			url : "leaveAdmin.htm?"+dispUrl,
			data : requestDetails,
			cache : false,
			success : function(html) {
				$jq('#result').html(html);
				if ($jq('.success').is(':visible')) {
					//resetTaskHolderDesig1();
				}
			}
		});
	}else {
		alert(errorMessage);
	}

}
function resetTaskHolderDesig(){
	nodeID="";
	nodeIds="";
	$jq('input:text').val("");
	$jq('select').val("0");
	$jq('textarea').val("");
	if ($jq('#SelectRight') != null) {
		$jq("#SelectRight > option").each(
				function() {
					$jq("#SelectLeft").append(
							'<option value=' + this.value + '>' + this.text
									+ '</option>');
					$jq(this).remove();
				});
	}
}
function resetTaskHolderDesig1(){
	nodeID="";
	nodeIds="";
	$jq('select').val("0");
	
	jQuery('#SelectRight option').remove();
	jQuery('#SelectLeft option').remove();
}
function editTaskHolderDesigDetails(id,roleName,typeDesigIds,roleId,typeId){
	
	resetTaskHolderDesig();
	addAllDesignations();
	nodeID=id;
	nodeIds=nodeIds;
	$jq('#taskHolderMap').val(roleId);
	var desigList = typeDesigIds.split(',');
	for ( var i = 0; i < desigList.length; i++) {
		$jq("#SelectLeft > option").each(
				function() {
					if (desigList[i] == this.value) {
						$jq("#SelectRight").append(
								'<option value=' + this.value + '>' + this.text
										+ '</option>');
						$jq(this).remove();
					}
				});
	}
	
	//$jq('#taskHolderMap').val().split('#')[0]=$jq('#taskHolderMap').val(id+'#'+typeId);
	$jq('#taskHolderMap').val(id+'#'+typeId);
}

function editTaskHolderDesigDetails1(id,roleName,typeDesigIds,roleId,typeId){
	$jq('#taskHolderMap').val(id+'#'+typeId);
	
	if($jq('#taskHolderMap').val()!=0)(
			$jq.post('leaveAdmin.htm?param=getAssignedDesignations&taskHolderMap='+id,'&gazettedType='+typeId,
				function(data){
					$jq('#getResponse').html(data);
			})		
	);
	
}

function addAllDesignations(){
	$jq('#SelectLeft').find('option').remove().end();
	for(i=0;i<getAllDesignations.length;i++){
			$jq('#SelectLeft').append($jq("<option></option>").attr("value",getAllDesignations[i].id).text(getAllDesignations[i].name));
	}
}

function deleteTaskHolderDesig(roleId){
	var requestDetails = {
			"taskHolderMap" : roleId,
			"param" : "deleteTaskHolderDesigDetails"
		};
		$jq.ajax( {
			type : "POST",
			url : "leaveAdmin.htm",
			data : requestDetails,
			cache : false,
			success : function(html) {
				$jq('#result').html(html);
			}
		});

}
function saveDOPartPortal(id,i){
	var errorMessage = "";
	var status = true;
	
	if($jq('#ReleasedDate'+i).is(':visible')){
	if($jq('#ReleasedDate'+i).val()=='') {
		errorMessage+='Please enter Released Date\n';
		$jq('#ReleasedDate'+i).focus();
		status=false;
	}}
	if($jq('#AcceptedDate'+i).is(':visible')){
	if($jq('#AcceptedDate'+i).val()=='') {
		errorMessage+='Please enter Accepted Date\n';
		$jq('#AcceptedDate'+i).focus();
		status=false;
	}}
	if (status) {
		var requestDetails = {
			"nodeID" : id,
			"typeId" : $jq('#typeId').val(),                         //THIS IS VARIABLE ADDED
			"releasedDate" :$jq('#ReleasedDate'+i).val(),
			"acceptedDate" :$jq('#AcceptedDate'+i).val(),
			"type" :type,
			"param" : "saveDoPartPotal"
		};
		$jq.ajax( {
			type : "POST",
			url : "leaveAdmin.htm?"+dispUrl,
			data : requestDetails,
			cache : false,
			success : function(html) {
				$jq('#result').html(html);
				if ($jq('.success').is(':visible')) {
					//resetTaskHolderDesig();
				}
			}
		});
	}else {
		alert(errorMessage);
	}

}
function getDOPartPortalList(){                                                //This new function to sort records
	var requestDetails = {
			"typeId" : $jq('#typeId').val(),
			"type" : $jq('#type').val(),
			"param" : "releaseDOPartList"
			};
			$jq.ajax( {
			type : "POST",
			url : "leaveAdmin.htm",
			data : requestDetails,
			cache : false,
			success : function(html) {
			$jq('#result').html(html);
			}
			}); 
}

function doFreezeOut(id,i){
	var status=confirm("Do Want UnFreeze This Record ?");
	if(status){
		var requestDetails = {
				"nodeID" : id,
				 "typeId": $jq('#typeId').val(),
				"type" :type,
				"param" : "freezeOut"
			};
			$jq.ajax( {
				type : "POST",
				url : "leaveAdmin.htm?"+dispUrl,
				data : requestDetails,
				cache : false,
				success : function(html) {
					$jq('#result').html(html);
					if ($jq('.success').is(':visible')) {
						//resetTaskHolderDesig();
					}
				}
			});
	}
}

function labels(type) {
	if(type == 'r'){
		document.title='Release Do Part II';
		$jq('#headTitle').html('Release Do Part II');
	}else if(type == 'a'){
		document.title='Accept Do Part II';
		$jq('#headTitle').html('Accept Do Part II');
	}else if(type == 'all'){
		document.title='Do Part II Details';
		$jq('#headTitle').html('Do Part II Details');
	}
}
function getDoPartModuleDetails(id){
	$jq('#moduleWise').html('');
	var requestDetails = {
			"nodeID" : id,
			"param" : "getModuleWiseDoPartDetails"
		};
		$jq.ajax( {
			type : "POST",
			url : "leaveAdmin.htm?"+dispUrl,
			data : requestDetails,
			cache : false,
			success : function(html) {
				$jq('#moduleWise').html(html);
				if ($jq('.success').is(':visible')) {
					$jq('#ajaxBusy').hide();
					//resetTaskHolderDesig();
				}
			}
		});

}
function getLeaveSearchList(){
	var code= $jq('#casualityId').val().split('#')[1]
	if (code==3) {
		$jq('#grantOfLeave').show();
		$jq('#conversionOfLeave').hide();
		$jq('#cancellationOfLeave').hide();
	} else if(code==1){
		$jq('#conversionOfLeave').show();
		$jq('#cancellationOfLeave').hide();
		$jq('#grantOfLeave').hide();
	}else if(code==2){
		$jq('#cancellationOfLeave').show();
		$jq('#grantOfLeave').hide();
		$jq('#conversionOfLeave').hide();
	}else{
		$jq('#grantOfLeave').hide();
		$jq('#conversionOfLeave').hide();
		$jq('#cancellationOfLeave').hide();
	}
}

function getdoPartButton() {
	var doNo = $jq('#doPartNo').val().split("#");
	if (doNo[0] == 0) {
		$jq('#saveButtonDiv').show();
		$jq('#publishButtonDiv').hide();
		var date = '#'+formatDate(new Date(), 'dd-MMM-yyyy');
		$jq('#sysdate').val(date);
		//$jq('#doPartNo').val();
	} else if ($jq('#doPartNo').val() == 'select') {
		$jq('#publishButtonDiv').hide();
		$jq('#saveButtonDiv').hide();
	} else {
		$jq('#publishButtonDiv').show();
		$jq('#saveButtonDiv').hide();
	}
}

function enableDoPartNo(listType) {
	var flag = false;
	$jq("#" + listType + " tr:not(:first)").each(function() {	
		if ($jq(this).find("td").eq(0).find(":input:checkbox").is(':checked')) {
			flag = true;
		}
	});
	if (flag) {
		$jq('#serialNumberDiv').show();
	} else {
		$jq('#serialNumberDiv').hide();
	}
}

function getDateWiseLeaveList() {
	$jq('#param').val('gazettedNEW');
	$jq('#type').val('dateWiseLeaveList');
	//$jq('#gazettedType').val();
	
	gazetted = $jq('#gazettedType option:selected').val();
	//doNo=$jq('#doPartNo option:selected').val();
	var doPartId = $jq('#doPartNo').val();
	var doNo = $jq('#doPartNo').val().split("#");
	if (!(doNo[0] == "select")) {
		$jq.post('leaveAdmin.htm', $jq('#leaveAdmin').serialize(), function(html) {
			$jq('#result').html(html);
			$jq('#gazettedType').val(gazetted);
			$jq('#casualityDiv').show();
		    $jq('#doPartNumberDiv').show();
		            	
		    if (doNo[0] != "") {
		    	$jq('#doPartNo').val(doPartId);
		    	getdoPartButton();
		    } else {
		    	$jq('#saveButtonDiv').show();
		    	$jq('#publishButtonDiv').hide();
		    	var date = '#' + formatDate(new Date(), 'dd-MMM-yyyy');
		    	$jq('#sysdate').val(date);
		    	$jq('#doPartNo').val(doPartId);
		    }
		    // doGlobal=$jq('#doPartNo').val();
		});	
	}
}

function getDoPartCount(){
	var requestDetails = {
			"typeId" :  $jq('#typeId').val(),
			"type" :  "homeList",
			"param" : "getDoPartCount"
		};
		$jq.ajax( {
			type : "POST",
			url : "leaveAdmin.htm",
			data : requestDetails,
			cache : false,
			success : function(html) {
				$jq('#result').html(html);
			}
		});
}
function getOrderedDoList(year,typeid,month){
	var doType;
	if(month==""){
		doType="monthWise";
	}else{
		doType="dateWise";
	}
	var requestDetails = {
			"year" :  year,
			"type" : doType,
			"typeId" :  typeid,
			"doMonth" :  month,
			"param" : "getDoPartCount"
		};
		$jq.ajax( {
			type : "POST",
			url : "leaveAdmin.htm",
			data : requestDetails,
			cache : false,
			success : function(html) {
				$jq('#result').html(html);
			}
		});

}
/**
 * DO Part-II script ends
 */


/**
 * Leave cancellation starts
 */
function getLeaveDetails(type, requestID, stageID, historyID) {
	if (type == 'cancel' && stageID == '1') {
		var requestDetails = {
			"param" : "declined",
			"historyID" : historyID,
			"requestID" : requestID,
			"requestType" : "LEAVE",
			"type" : "cancelled",
			"requestFrom" : "other"
		};
		$jq.post('workflow.htm', requestDetails, function(data) {
			$jq('#result').html(data);
	        getUpdatedLeaveDetails();
	    });
	} else {
		document.forms[0].type.value = type;
		document.forms[0].requestID.value = requestID;
		document.forms[0].param.value = "getLeaveDetails";
		document.forms[0].action = "leaveRequest.htm";
		document.forms[0].submit();
	}
}

function cancelLeave(requestID) {
	$jq('#result').text('');
	var requestDetails = {
			"requestID" : requestID,
			"param" : "cancelLeave",
			"reason" : $jq.trim($jq('#reason').val())
	};
	$jq.post('leaveRequest.htm', requestDetails, function(html) { 
		$jq('#result').html(html);
	    if ($jq('.success').is(':visible')) {
	    	var check = confirm("Leave cancellation request has been sent successfully...\nPlease click OK to preview Leave Application Form 'Take print' from here\n");
	     	if (check) {
	     		//getLeaveDetails(type,requestID,stageID,historyID);
	     		document.forms[0].requestId.value = $jq.trim(requestId);
	     		//document.forms[0].requestId.value = $jq.trim(requestID);
	     		//document.forms[0].historyID.value = historyID;
	     		//document.forms[0].message.value = message;
	     		//document.forms[0].statusMsg.value = status;
	     		//document.forms[0].roleId.value = 'roleId';
	     		document.forms[0].param.value = "requestDetails";
	     		document.forms[0].action = "workflowmap.htm";
	     		document.forms[0].submit();	
	     	}
	     }
	});
} // Leave cancellation ends

/**
 * Leave conversion code starts
 */
function getPrefix(id, leaveTypeID, mapDays, holidayIntFlag) {
	var rowID = id.split("fromDate")[1];
	if ($jq("#" + id).val() != '') {
		$jq.ajax({
			type : "POST",
			url : "leaveRequest.htm",
			data : "param=holidays&type=prefix&selectedDate=" + $jq("#" + id).val(),
			cache : false,
			success : function(html) {
				$jq("#prefix" + rowID).html(html);
				if (holidayIntFlag == 'N') {
					getNoOfDays("fromDate" + rowID, "toDate" + rowID, leaveTypeID, rowID, mapDays);
				} else if (holidayIntFlag == 'Y') {
					if ($jq('#fromDate' + rowID).val() != '' && $jq('#toDate' + rowID).val() != '') {
						$jq('#days' + rowID).text('');
						$jq('#days' + rowID).html('<div>' + parseInt(compareDates(convertDate($jq('#fromDate' + rowID).val()), convertDate($jq('#toDate' + rowID).val())) + 1) + '</div>');
						if (leaveTypeID == '2') {
							$jq('#avil3').text((parseInt($jq('#avil' + rowID).text(), 10) - parseInt($jq('#days' + rowID).text(), 10)) / 2);
						}
						if (leaveTypeID == '4' && $jq('#avil1').prev()[0].id.split('#')[1] == '2') {
							$jq('#avil1').text(parseInt($jq('#avil1').text(), 10) - parseInt($jq('#days' + rowID).text(), 10) * 2);
						}
					}			
				}
			}
		});	
	} else {
		$jq("#prefix" + rowID).html('');
	}
}

function getSuffix(id, leaveTypeID, mapDays, holidayIntFlag) {
	var rowID = id.split("toDate")[1];
	if ($jq("#" + id).val() != '') {		
		$jq.ajax({
			type : "POST",
			url : "leaveRequest.htm",
			data : "param=holidays&type=suffix&selectedDate=" + $jq("#" + id).val(),
			cache : false,
			success : function(html) {
				$jq("#suffix" + rowID).html(html);
				if (holidayIntFlag == 'N') {
					getNoOfDays("fromDate" + rowID, "toDate" + rowID, leaveTypeID, rowID, mapDays)
				} else if (holidayIntFlag == 'Y') {
					if ($jq('#fromDate' + rowID).val() != '' && $jq('#toDate' + rowID).val() != '') {
						$jq('#days' + rowID).text('');
						$jq('#days' + rowID).html('<div>' + parseInt(compareDates(convertDate($jq('#fromDate' + rowID).val()), convertDate($jq('#toDate' + rowID).val())) + 1) + '</div>');
						if (leaveTypeID == '2') {
							$jq('#avil3').text((parseInt($jq('#avil' + rowID).text(), 10) - parseInt($jq('#days' + rowID).text(), 10)) / 2);
						}
						if (leaveTypeID == '4' && $jq('#avil1').prev()[0].id.split('#')[1] == '2') {
							$jq('#avil1').text(parseInt($jq('#avil1').text(), 10) - parseInt($jq('#days' + rowID).text(), 10) * 2);
						}
					}
				}
			}
		});
	} else {
		$jq("#suffix" + rowID).html('');
	}
}

function rollBackMappedLeaves(rowId, leaveId) {
	if (leaveId == '2' && $jq('#days' + rowId).text() != '') {
		$jq('#avil3').text(parseFloat($jq('#avil3').text()) + parseInt($jq('#days' + rowId).text(), 10) / 2);
	}
	if (leaveId == '4' && $jq('#days' + rowId).text() != '') {
		$jq('#avil1').text(parseInt($jq('#avil1').text(), 10) + parseInt($jq('#days' + rowId).text(), 10) * 2);
	}
}

function convertLeave(requestID) {
	$jq("#result").text('');
	var datesArr = new Array();
	var convertLeaves = new Array();
	var convetingLeaveDays = 0;
	var errorMessage = "";
	var status = true;
	var flag = true;
	
	var date1 = $jq.trim(appliedLeaveFromDate).split('-');//$jq.trim($jq("#fromDate").text()).substring(2, $jq.trim($jq("#fromDate").text()).length).split("-");
    var formatDate1 = new Date(date1[2], getMonthID(date1[1]) - 1, date1[0]);
    var date2 = $jq.trim(appliedLeaveToDate).split('-');//$jq.trim($jq("#toDate").text()).substring(2, $jq.trim($jq("#toDate").text()).length).split("-");
    var formatDate2 = new Date(date2[2], getMonthID(date2[1]) - 1, date2[0]);

	$jq("#convertTable tr:not(:first)").each(function() {
		flag = false;
		var fromDate = $jq(this).find("td").eq(2).find(":input").val();
		var toDate = $jq(this).find("td").eq(3).find(":input").val();
		if (fromDate != '' && toDate != '') {
			var leaveTypeId = $jq.trim($jq(this).find("td").eq(0).attr('id')).split('#')[1]; 
			var leaveType = $jq.trim($jq(this).find("td").eq(0).html()); 
		    var availLeaves = $jq.trim($jq(this).find("td").eq(1).html()); 
		    var appliedLeaves = $jq.trim($jq(this).find("td").eq(4).find('div').text());
		    convetingLeaveDays = appliedLeaves;
		    var prefix = $jq.trim($jq(this).find("td").eq(5).find('div').text()); 
		    var suffix = $jq.trim($jq(this).find("td").eq(6).find('div').text()); 

		    var fromDateArr = fromDate.split("-");
		    var toDateArr = toDate.split("-");
		    var formatFromDate = new Date(fromDateArr[2], getMonthID(fromDateArr[1]) - 1, fromDateArr[0]);
		    var formatToDate = new Date(toDateArr[2], getMonthID(toDateArr[1]) - 1, toDateArr[0]);
		    
		    if (formatFromDate > formatToDate || formatDate1 > formatFromDate ||  formatDate2 < formatToDate) {
		    	errorMessage += "Dates invalid for " + leaveType + "\n";
		    } else {
		    	var count = 0;
		    	$jq.each(datesArr, function(i, item) {
		    		if (!(formatToDate < item.fromDate || item.toDate < formatFromDate)) {
		    			if (count == 0) {
		    				errorMessage += "Dates invalid for " + leaveType + "\n";
		    			}
		    			count++;
		    		}
		        });
		    	var datesObj = { "fromDate" : formatFromDate, "toDate" : formatToDate };
		    	datesArr.push(datesObj);
		    }
		    if (appliedLeave == '1' && leaveTypeId == '3') {
		    	var fromYear = parseInt(appliedLeaveFromDate.split('-')[2], 10);
		    	var toYear = parseInt(appliedLeaveToDate.split('-')[2], 10);
		    	var currentYear = parseInt(getCurrentDate().split('-')[2], 10);
		    	if (fromYear < currentYear && toYear < currentYear) {
		    		errorMessage += 'Converting into Casual Leave for past dates is not possible\n';
		    		status = false;
		    	} else if (fromYear < currentYear && toYear == currentYear) {
		    		if (parseInt(fromDate.split('-')[2]) != currentYear && parseInt(toDate.split('-')[2]) != currentYear) {
		    			errorMessage += 'Converting into Casual Leave for past dates is not possible\n';
			    		status = false;
		    		}
		    	}
		    }
		    if (parseFloat(availLeaves) >= parseFloat(appliedLeaves)) {
		    	var convertLeaveObj = new Object();
		    	convertLeaveObj = {"leaveTypeId" : leaveTypeId, "fromDate" : fromDate, "toDate" : toDate, "appliedLeaves" : appliedLeaves,
		    			"prefix" : prefix, "suffix" : suffix, "formattedFromDate" : formatFromDate, "formattedToDate" : formatToDate};
		    	convertLeaves.push(convertLeaveObj);		    	
		    } else {
		    	errorMessage += "Please enter less number of leaves for " + leaveType + "\n";
		    }	
		}		   
	});
	
	if (flag) {
		errorMessage += "Leave conversion not possible\n";
	}
	// check each date in the converted dates
	var notConfiguredDates = "";
	if (datesArr.length > 0) { 		
		while (formatDate1 <= formatDate2) {
			var status = false;		
			$jq.each(datesArr, function(i, item) {
	    		if (formatDate1 >= item.fromDate && formatDate1 <= item.toDate) {
	    			status = true;
	    		}
	        });
			if (!status) {
				notConfiguredDates += formatDate1.getDate() + "-" + getMonthMON(formatDate1.getMonth() + 1) + "-" + (formatDate1.getYear() + 1900) + ", ";
			}
			formatDate1 = nextDay(formatDate1);
		}
	}
	if (notConfiguredDates != "") {
		errorMessage += "Following dates are not configured : " + notConfiguredDates.substring(0, notConfiguredDates.length - 2);
	}

	if (errorMessage == "") {
		if (convertLeaves.length == 0) {
			alert("Please select dates\n");
		} else {
			convertLeavesDetails = Object.toJSON(convertLeaves.sort(DateSort));
			leaveRequestDetails = {
				"param" : "convertLeave",
				"convertLeaves" : convertLeavesDetails,
				"requestID" : requestID,
				"type" : "direct",
				"reason" : $jq.trim($jq('#reason').val()),
				"remarks": ""
			};
			if (appliedLeave == '3') {
				if (appliedLeaveNoDays.indexOf('.') == -1) {
					if (parseInt(appliedLeaveNoDays, 10) != parseInt(convetingLeaveDays, 10)) {
						errorMessage += 'Leave conversion is not possible as no of days mis-matches\n';
						status = false;
					}
				} else {
					errorMessage += 'Leave conversion is not possible for Casual Leave which has half days\n';
					status = false;
				}
			}
			if (status) {
				leaveConvertSubmit();
			} else {
				alert(errorMessage);
			}
		}
	} else {
		alert(errorMessage);
	}
}

function leaveConvertSubmit() {
	$jq.post('leaveRequest.htm', leaveRequestDetails, function(html) {
		$jq('#result').html(html);
        $jq('#applyLeaveBtn').show();
        
        updateAvailableLeaves();
        
        if ($jq('.success').is(':visible')) {
        	$jq('input').val('');
        	$jq('#counter').val(100);
            $jq('textarea').val('');
            $jq('#convertTable >tbody >tr:not(:first)').each(function() {
            	$jq(this).find('td').eq(4).find('div').html('');
            	$jq(this).find('td').eq(5).find('div').html('');
            	$jq(this).find('td').eq(6).find('div').html('');
            });
            var check = confirm("Leave conversion request has been sent Successfully...\nPlease click OK to 'Preview Leave Conversion Application Form 'Take print' from here\n");
			if (check) {
				document.forms[0].requestId.value = $jq.trim(requestIDcon);
				//document.forms[0].roleId.value = 'roleId';
				document.forms[0].param.value = "requestDetails";
				document.forms[0].action = "workflowmap.htm";
				document.forms[0].submit();	
			}
        }
	});
}

function nextDay(date) {
	date.setDate(date.getDate() + 1);
	return date;
}
	 
function DateSort(a, b) {
  return ((a.formattedFromDate == b.formattedFromDate) ? 0 : ((a.formattedFromDate > b.formattedFromDate) ? 1 : -1 ));
}
function updateAvailableLeaves() {
	var status = true;
	var i = 1;
	if ($jq(".success").is(':visible')) {
		$jq("#convertTable tr:not(:first)").each(function() {		
			 var avilLeaves = $jq.trim($jq(this).find("td").eq(1).html()); 
			 var appliedLeaves = $jq.trim($jq(this).find("td").eq(4).html());			 
			 if (appliedLeaves != '' && appliedLeaves != 0) {
				$jq(this).find("td").eq(1).html(parseInt((parseFloat(avilLeaves) - parseFloat($jq.trim($jq(this).find("td").eq(4).find("div").html()))).toFixed(2)));
			 }
		});		
	}
}

/**
 * Leave conversion code ends
 */

/**
 * Leave card code starts
 */
function setSource() {
	if ($jq('#firstEntryDate').text() == '') {
		if ($jq("#currentYear").val() != '2011') {
			$jq(".curyear").text("As on 31/12/" + (parseInt($jq("#currentYear").val(), 10) - 1));
		} else {
			$jq(".curyear").text("As on 01/01/" + $jq("#currentYear").val());
		}
	} else {
		$jq(".curyear").text("As on " + $jq('#firstEntryDate').text().split('-')[0] + '/' + $jq('#firstEntryDate').text().split('-')[1] + '/' + $jq('#firstEntryDate').text().split('-')[2]);
	}
	/*if($jq('#cltable').find("tr").eq(3).length==1) {		
		//$jq('#cltable').find("tr").eq(2).find("td").eq(1).text(parseFloat($jq('#cltable').find("tr").eq(3).find("td").eq(4).text()) - parseFloat($jq('#cltable').find("tr").eq(3).find("td").eq(1).text()));
		$jq('#cltable').find("tr").eq(2).find("td").eq(1).text(0);
	}
	if($jq('#eltable').find("tr").eq(3).length==1){
		$jq('#eltable').find("tr").eq(2).find("td").eq(2).text(parseFloat($jq('#eltable').find("tr").eq(3).find("td").eq(5).text()) - parseFloat($jq('#eltable').find("tr").eq(3).find("td").eq(2).text()));
	} 
	if($jq('#hpltable').find("tr").eq(3).length==1) {
		$jq('#hpltable').find("tr").eq(2).find("td").eq(2).text(parseFloat($jq('#hpltable').find("tr").eq(3).find("td").eq(5).text()) - parseFloat($jq('#hpltable').find("tr").eq(3).find("td").eq(2).text()));
	} 
	if($jq('#ccltable').find("tr").eq(3).length==1) {
		$jq('#ccltable').find("tr").eq(2).find("td").eq(1).text(parseFloat($jq('#ccltable').find("tr").eq(3).find("td").eq(4).text()) - parseFloat($jq('#ccltable').find("tr").eq(3).find("td").eq(1).text()));
	}*/
	/*
	 * if($jq('#lndtable').find("tr").eq(3).length==1) {
	 * $jq('#lndtable').find("tr").eq(2).find("td").eq(0).text(parseFloat($jq('#lndtable').find("tr").eq(3).find("td").eq(3).text()) -
	 * parseFloat($jq('#lndtable').find("tr").eq(3).find("td").eq(0).text())); }
	 */
	
	/*$jq("#cltable tr:not(:first)").each(function() {
		if ($jq(this).closest("tr").prevAll("tr").length > 2) {
			if ($jq(this).find("td").eq(6).text() == 'Leave Request' || $jq(this).find("td").eq(6).text() == 'Leave Conversion') {
				//$jq(this).find("td").eq(0).text($jq(this).find("td").eq(0).text().substring(1));
			} else if ($jq(this).find("td").eq(6).text() == 'Credit/Laps') {
				if (parseFloat($jq(this).find("td").eq(0).text()) < 0) {
					$jq(this).find("td").eq(6).text('Laps');
				} else {
					$jq(this).find("td").eq(6).text('Credit');
				}
			}
		}
	});
	$jq("#eltable tr:not(:first)").each(function() {
		if ($jq(this).closest("tr").prevAll("tr").length > 2) {
			if ($jq(this).find("td").eq(7).text() == 'Leave Request' || $jq(this).find("td").eq(7).text() == 'Leave Conversion') {
				//$jq(this).find("td").eq(1).text($jq(this).find("td").eq(1).text().substring(1));	
			} else if ($jq(this).find("td").eq(7).text() == 'Credit/Laps') {
				if (parseFloat($jq(this).find("td").eq(2).text()) < 0) {
					$jq(this).find("td").eq(7).text('Laps');
				} else {
					$jq(this).find("td").eq(7).text('Credit');
				}
			}
			if ($jq(this).find("td").eq(0).text().toUpperCase().indexOf('EOL')!= -1) {
				// Multiply with 10
				//$jq(this).find("td").eq(1).text(parseFloat($jq(this).find("td").eq(1).text())*10);
				// Division with 10
				$jq(this).find("td").eq(2).text(parseFloat($jq(this).find("td").eq(1).text()) / 10);
			}
		}			
	});
	
	$jq('#eltable tr').next().next().next().each(function() {
		if ($jq(this).find('td').eq(1).text().toUpperCase().indexOf('EOL') != -1) {
			$jq(this).find('td').eq(7).text(parseInt($jq(this).find('td').eq(6).text(), 10) + parseInt(, 10))
			alert('Hai');
		}
	});
	
	$jq("#hpltable tr:not(:first)").each(function() {
		if ($jq(this).closest("tr").prevAll("tr").length > 2) {
			if ($jq(this).find("td").eq(7).text() == 'Leave Request' || $jq(this).find("td").eq(7).text() == 'Leave Conversion') {
				//$jq(this).find("td").eq(1).text($jq(this).find("td").eq(1).text().substring(1));	
			} else if ($jq(this).find("td").eq(7).text() == 'Credit/Laps') {
				if (parseFloat($jq(this).find("td").eq(2).text()) < 0) {
					$jq(this).find("td").eq(7).text('Laps');
				} else {
					$jq(this).find("td").eq(7).text('Credit');
				}
			}
		}
	});
	$jq("#ccltable tr:not(:first)").each(function() {
		if ($jq(this).closest("tr").prevAll("tr").length > 2) {
			if ($jq(this).find("td").eq(6).text() == 'Leave Request' || $jq(this).find("td").eq(6).text() == 'Leave Conversion') {
				//$jq(this).find("td").eq(0).text($jq(this).find("td").eq(0).text().substring(1));
			}
		}
	});*/
} //Leave card code ends
 
// Leave request ends
// Holidays script starts

function getSeletedYearHolidays() {
	//resetHoliday();	
	if ($jq('#year').val() != 'select') {
		$jq("#title").text("Add Holidays For The Year " + $jq('#year').val());
		$jq.post('holidays.htm?param=getList&year=' + $jq('#year option:selected').text() + '&' + dispUrl, function(html) {
			$jq("#holidaysList").html(html);
		});
	}
}

function resetHoliday() {	
	$jq('#year').val('select');
	$jq('#holiday').val('');
	$jq('#holidayDate').val('');
	$jq('#description').val('');
	holidayID = "";	
}

function editHolidayDetails(id, holiday, holidayDate, description) {
	holidayID = id;
	$jq('#year').val(holidayDate.split('-')[2]);
	$jq('#holiday').val(holiday);
	$jq('#holidayDate').val(holidayDate);
	$jq('#description').val(description);	
}

function deleteHolidayDetails(id) {
	var del = confirm("Are you sure Do u want to delete this Record ?\n");
	if (del) {
		var requestDetails = {
			"param" : "deleteHoliday",
			"pk" : id,
			"year" : $jq('#year option:selected').text()
		};
		$jq.post('holidays.htm' + '?' + dispUrl, requestDetails, function(html) {
			$jq("#holidaysList").html(html);
		});
	}
}

function manageHoliday() {
	var flag = true;
	var errorMessage = "";
	if ($jq('#year').val() == 'select') {
		errorMessage += "Please Select Year \n";
		if (flag) {
			$jq('#year').focus();
			flag = false;
		}
	}
	if ($jq('#holiday').val() == '') {
		errorMessage += "Please Enter Holiday Name \n";
		if (flag) {
			$jq('#holiday').focus();
			flag = false;
		}
	}
	if ($jq('#holidayDate').val() == '') {
		errorMessage += "Please Select Holiday Date \n";
		if (flag) {
			$jq('#holidayDate').focus();
			flag = false;
		}
	} else if ($jq('#holidayDate').val().split("-")[2] != $jq('#year option:selected').text()) {
		errorMessage += "Please select the date in "+$jq('#year option:selected').text()+" year\n";
		if (flag) {
			$jq('#holidayDate').focus();
			flag = false;
		}
	}	
	
	if (flag) {
		var requestDetails = {
				"holiday" : $jq('#holiday').val(),
				"holidayDate" : $jq('#holidayDate').val(),
				"description" : $jq('#description').val(),				
				"year":$jq('#year option:selected').text(),
				"param":"manage",
				"pk":holidayID
			};
			$jq.post('holidays.htm'+'?'+dispUrl, requestDetails, function(html) {
				$jq("#holidaysList").html(html);
				resetHoliday();
			});
	} else {
		alert(errorMessage);
	}
}
// Holidays script ends

function appendText(text,id){
	if($jq('#'+id).val()==''){
		$jq('#'+id).attr('style','color:#A29A9A');
		$jq('#'+id).val(text);		
	}else if($jq('#'+id).val()==text){
		$jq('#'+id).attr('style','color:#A29A9A');
	}
}

function clearText(text,id){
	if($jq('#'+id).val()==text){
		$jq('#'+id).val('');
		$jq('#'+id).removeAttr('style');
	}		
}
function changeColor(id){
	$jq('#'+id).attr('style','color:#000');
}

// leave manual run script
function runLeaveScript() {
	var flag = true;
	var errorMessage = "";
	$jq('#result').text('');
	var scriptRunDate = $jq.trim($jq('#scriptDate').val());
	if (scriptRunDate == '') {
		errorMessage = "Please select date to run leave manual script\n";
		if (flag) {
			flag = false;
			$jq('#scriptDate').focus();
		}
	} else {
		if (scriptRunDate.split('-')[1] == 'Jan') {
			if (scriptRunDate.indexOf('01-Jan') == -1) {
				errorMessage = "Leave auto run date should be 01-Jan-" + scriptRunDate.split('-')[2];
				flag = false;
			}
		} else {
			if (scriptRunDate.indexOf('01-Jul') == -1) {
				errorMessage = "Leave auto run date should be 01-Jul-" + scriptRunDate.split('-')[2];
				flag = false;
			}
		}
	}
	if (flag) {
		$jq.post('leaveAdmin.htm?param=runScript&scriptDate=' + scriptRunDate, function(html) {
			$jq("#result").html(html);
			if ($jq('.success').is(':visible')) {
				resetLeaveScript();
			}
		});
	} else {
		alert(errorMessage);
	}
}

function resetLeaveScript() {
	$jq("#scriptDate").val('');
}

// leave Transaction Search start
function searchTxnLeave() {
	var errorMessage = '';
	var status = true;
	if ($jq('#fromDate').val() == '') {
		errorMessage += 'Please select from date\n';
		if (status) {
			status = false;
			$jq('#fromDate').focus();
		}
	}
	if ($jq('#toDate').val() == '') {
		errorMessage += 'Please select to date\n';
		if (status) {
			status = false;
			$jq('#toDate').focus();
		}
	}
	if ($jq('#fromDate').val() != '' && $jq('#toDate').val() != '' && !compareDate($jq('#toDate').val(), $jq('#fromDate').val())) {
		errorMessage += "Invalid Dates \n";
		if (status) status = false;
	}
	
	if (status) {
		var jsonVal = {};
		var i = 0;
		$jq('#leaveTxnTypeDiv').find('input:checkbox').each(function() {
			if($jq(this).attr('checked')) {
				 jsonVal[i] = $jq(this).val();
				 i++;
			}
		});
		if(JSON.stringify(jsonVal).length == 2) jsonVal = 0;
					
		$jq.post('leaveAdmin.htm?param=txnLeaveSearch&searchSfid='+ $jq('#searchSfid').val().toUpperCase() +'&leaveType='+ $jq('#leaveType').val() +'&fromDate='+ $jq('#fromDate').val() +'&toDate='+ $jq('#toDate').val() +'&txnList='+ JSON.stringify(jsonVal), function(html) {
			$jq("#leaveTxnSearchList").html(html);		
		});	
	} else {
		alert(errorMessage);
	}	
}

function resetTxnLeave() {
	$jq("#searchSfid").val('');
	$jq("#leaveType").val('');
	$jq("#fromDate").val('');
	$jq("#toDate").val('');
}

// End of leave Transaction Search

function saveLeaveExceptionEmp() {
	var selectedValues = "";
	var errorMessages = "";
	var status = false;
	
	for ( var i = 0; i < document.getElementById('selectRight').options.length; i++) {
			selectedValues += (document.getElementById('selectRight').options[i].value) + ",";
	}
	document.forms[0].param.value = "leaveExpSubmit";
	document.forms[0].selectedLinks.value = selectedValues;
	
	var b = confirm("These Employees will not get balance credit at script run.\n Are you sure do you want to continue ?...");
	if(b == true) {
		$jq.post('leaveAdmin.htm', $jq('#leaveExceptionalEmp').serialize(), function(html) {
			$jq("#result").html(html);		
		});	
	}
}

// start of Leave Adit

function saveLeaveAdit() {
	var flag = true;
	var errorMessage = "";
	if ($jq.trim($jq('#searchSfid').val()) == '') {
		errorMessage += "Please Enter SFID \n";
		if (flag) {
			$jq('#searchSfid').focus();
			flag = false;
		}
	}
	if ($jq('#leaveType').val() == '0') {
		errorMessage += "Please Select Leave Type \n";
		if (flag)
			flag = false;
	}
	if ($jq('#txnType').val() == 'select') {
		errorMessage += "Please Select Transaction Type \n";
		if (flag)
			flag = false;
	}
	if ($jq('#txnDate').val() == '') {
		errorMessage += "Please Select Adit Transaction Date \n";
		if (flag) {
			$jq('#txnDate').focus();
			flag = false;
		}
	} 
	if ($jq('#noOfDays').val() == '') {
		errorMessage += "Please Enter Number of days \n";
		if (flag) {
			$jq('#noOfDays').focus();
			flag = false;
		}
	} 
	if ($jq('#remarks').val() == '') {
		errorMessage += "Please provide Remarks \n";
		if (flag) {
			$jq('#remarks').focus();
			flag = false;
		}
	} 	
	if(flag) {
		var requestDetails = {
				"searchSfid" : $jq('#searchSfid').val().toUpperCase(),
				"leaveType" : $jq('#leaveType').val(),
				"txnDate" : $jq('#txnDate').val(),				
				"noOfDays":$jq('#noOfDays').val(),
				"remarks":$jq('#remarks').val(),
				"txnType":$jq('#txnType').val(),
				"txnTypeVal":$jq('#txnType option:selected').val(),
				"param":"saveLeaveAdit"
			};
			$jq.post('leaveAdmin.htm', requestDetails, function(html) {
				$jq("#result").html(html);
				resetLeaveAdit()
			});
	} else {
		alert(errorMessage);
	}
}

function resetLeaveAdit(){
	$jq("#searchSfid").val('');
	$jq("#leaveType").val('0');
	$jq("#txnDate").val('');
	$jq("#noOfDays").val('');
	$jq("#remarks").val('');
	$jq("#remarks").val('');
	$jq("#txnType").val('select');
	$jq("#availableLeaves").text('');
}
// end of Leave Adit

function convertRemarks() {
	if($jq(".failure").text().indexOf('continuously with the same leave') >= 0) {
		$jq('#applyLeaveBtn').hide();
		$jq('#proceedBtn').show();
	}
}

function cancelConvert(){
	$jq(".failure").text('');
	$jq('#proceedBtn').hide();
	$jq('#applyLeaveBtn').show();
}

function proceedConvert(prevHolidays,nextHolidays){
	var errorMessage = "";
	$jq("#convertTable tr:not(:first)").each(function() {
		if(parseFloat($jq.trim($jq(this).find("td").eq(1).html()))<(parseFloat(prevHolidays)+parseFloat($jq.trim($jq(this).find("td").eq(4).html())))) {
			errorMessage += "Only "+$jq.trim($jq(this).find("td").eq(4).html())+" "+$jq.trim($jq(this).find("td").eq(0).html())+ " are available\n";
		}
		return;			   
	});
	$jq("#convertTable tr:last").each(function() {
		if($jq('#convertTable tr').length<3 && errorMessage!=""){
			nextHolidays += 	prevHolidays;
		}
		if(parseFloat($jq.trim($jq(this).find("td").eq(1).html()))<(parseFloat(nextHolidays)+parseFloat($jq.trim($jq(this).find("td").eq(4).html())))) {
			errorMessage += "Only "+$jq.trim($jq(this).find("td").eq(4).html())+" "+$jq.trim($jq(this).find("td").eq(0).html())+ " are available";
		}
		return;			   
	});
	if(errorMessage==""){
		var convDetails = JSON.parse(convertLeavesDetails);
		var noOfDays = "";
		var convert = 1;
		
		for(var i=0;i<convDetails.length;i++){
			noOfDays = convDetails[i].appliedLeaves;
			if(i==0){
				// First
				if(prevHolidays!='null'){
					var fordateArr1 = $jq.trim(convDetails[i].fromDate).split("-");
				    var formatDate1 = new Date(fordateArr1[2], getMonthID(fordateArr1[1])-1, fordateArr1[0]);
				    var toArr1 = $jq.trim(convDetails[i].toDate).split("-");
				    var toDate1 = new Date(toArr1[2], getMonthID(toArr1[1])-1, toArr1[0]);
				    convert = parseFloat(noOfDays)/parseFloat(((toDate1-formatDate1)/86400000)+1);
				    
					noOfDays = parseFloat(noOfDays)+(parseFloat(prevHolidays)*parseFloat(convert));	
					$jq("#convertTable tr:not(:first)").each(function() {
						if($jq(this).find("td").eq(2).find(":input").val()==convDetails[i].fromDate) {
							$jq(this).find("td").eq(4).find("div").html(noOfDays);
							return;	
						}
					});
				}				
			}
			if(i==convDetails.length-1){
				// Last
				if(nextHolidays!='null'){
					var fordateArr1 = $jq.trim(convDetails[i].fromDate).split("-");
				    var formatDate1 = new Date(fordateArr1[2], getMonthID(fordateArr1[1])-1, fordateArr1[0]);
				    var toArr1 = $jq.trim(convDetails[i].toDate).split("-");
				    var toDate1 = new Date(toArr1[2], getMonthID(toArr1[1])-1, toArr1[0]);
				    convert = parseFloat(noOfDays)/parseFloat(((toDate1-formatDate1)/86400000)+1);
				    
					noOfDays = parseFloat(noOfDays)+(parseFloat(nextHolidays)*parseFloat(convert));	
					$jq("#convertTable tr:not(:first)").each(function() {
						if($jq(this).find("td").eq(2).find(":input").val()==convDetails[i].fromDate) {
							$jq(this).find("td").eq(4).find("div").html(noOfDays);
							return;	
						}
					});
				}				
			}	
			convDetails[i].appliedLeaves = noOfDays;			
		}
		
		convertLeavesDetails = Object.toJSON(convDetails.sort(DateSort));
		leaveRequestDetails.convertLeaves=convertLeavesDetails;
		leaveRequestDetails.type="proceed";
		leaveRequestDetails.remarks=$jq(".failure").text();
		leaveConvertSubmit();
	} else {
		alert(errorMessage);
	}
}

function getAvailableLeaves(){	
	if($jq("#leaveType").val()!="0" && $jq("#searchSfid").val()!=""){
		var requestDetails ={
				"leaveType" : $jq("#leaveType").val(),
				"param" : "availableLeaves",
				"searchSfid" : $jq("#searchSfid").val().toUpperCase()
		};
		$jq.ajax({
			url  :  "leaveAdmin.htm",
			type :  "POST",
			data :  requestDetails,
			cache: false,
			success : function(html) {
				$jq("#availableLeaves").html(html);
				balance = $jq.trim($jq("#leaveBalance").text());
				$jq("#noOfDays").val('');
			}
		});
	}
}
function manageLeaves(){
	if(balance !=""){
		if(parseFloat(balance)<parseFloat($jq("#noOfDays").val())){
			alert("Insufficient balance.\n");
		}else if(!isNaN(parseFloat($jq("#noOfDays").val()))){
			$jq("#availableLeaves").text(parseFloat(balance)+parseFloat($jq("#noOfDays").val()));
		}	else{
			$jq("#availableLeaves").text(parseFloat(balance));
		}
	}else{
		$jq("#availableLeaves").text("");
		
	}
	
}
function cancelLtcLeave(requestID,typeValue){
	document.forms[0].requestID.value=requestID;
	document.forms[0].param.value="cancelLeave";
	document.forms[0].ltcRequestType.value=typeValue;
	$jq.post('leaveRequest.htm',$jq('#leaveRequest').serialize(),  
	           function(data){ 
	            	 $jq('#result').html(data);
	           } 
	      );
}


//sridhar

function getAssignedDesignations(){
	var thMap= $jq('#taskHolderMap').val().split('#')[0];
	var gazType= $jq('#taskHolderMap').val().split('#')[1];
	
	if($jq('#taskHolderMap').val()!=0)(
	$jq.post('leaveAdmin.htm?param=getAssignedDesignations&taskHolderMap='+thMap,'&gazettedType='+gazType,
		function(data){
			$jq('#getResponse').html(data);
	})		
	);
}

function setAssignedDesignations() {
	var create1 = '';
	var create2 = '';
	jQuery('#SelectRight option').remove();
	jQuery('#SelectLeft option').remove();
	
	for(var i = 0; i < listObj.length;i++) {
	    	var flag=true;
	    	for(var j=0;j<delistObj.length;j++) {
	    		if(delistObj[j].id==listObj[i].id){
	    				create2 += '<option value="'+listObj[i].id+'">'+listObj[i].name+'</option>';
	    				flag=false;
	    				break;
	    			}
	    	}
	    	if(flag){
	    		var flag1=true;
	    		for(var l=0;l<assDesigJson.length;l++)
	    			if(assDesigJson[l].id==listObj[i].id)
	    				flag1=false;
	    		
	    		if(flag1)
	    			create1 += '<option value="'+listObj[i].id+'">'+listObj[i].name+'</option>';	
	    	}
	}
	
	jQuery('#SelectRight').append(create2);
	jQuery('#SelectLeft').append(create1);
	
}	

function deleteAssignedTypeDesig(id){
	var del=confirm("Do you want to delete this Record?");
	if(del==true) {
	var requestDetails = {
			"taskHolderId" : id,
			"param" : "deleteAssignedTypeDesig"
		};
		$jq.ajax( {
			type : "POST",
			url : "leaveAdmin.htm",
			data : requestDetails,
			cache : false,
			success : function(html) {
				$jq('#result').html(html);
			}
		});
	}
}

function updateUploadedFiles(mc, fc, oc, requestID) {
	var medicalCert = "";
	var fitnessCert = "";
	var otherCertificate = "";
	var status = true;
	var flag = 0;
	var length = $jq("#certiId").find('input:file').length;
	$jq("#certiId").find('input:file').each(function() {
		if ($jq(this).val() == "") {
			flag++;
		}
	});
	if (length == flag) {
		status= false;
	}
	if (status) {
		$jq('#param').val('uploadedFilesUpdate');
		// Medical certificates
		if ($jq("#mcFile").is(':visible') && $jq("#mcFile").val() != "") {
			medicalCert = mc;
			$jq.ajaxFileUpload( {
				url : "leaveRequest.htm?medicalCertName=" + medicalCert + "&requestID=" + requestID + "&" + $jq('#workflowmap').serialize(),
				secureuri : false,
				fileElementId : 'mcFile',
				success : function() {
					alert("You have Successfully Uploaded Medical Certificate \n");
					getRequestIdDetails(requestID);
				},
				error : function() {
					//alert('File Upload failed');
				}
			});
		}
		// Fitness certificates
		if ($jq("#fitnessFile").is(':visible') && $jq("#fitnessFile").val() != "") {
			fitnessCert = fc;
			$jq.ajaxFileUpload( {
				url : "leaveRequest.htm?fitnessCertName=" + fitnessCert + "&requestID=" + requestID + "&" + $jq('#workflowmap').serialize(),
				secureuri : false,
				fileElementId : 'fitnessFile',
				success : function() {
					alert("You have Successfully Uploaded Fitness Certificate \n");
					getRequestIdDetails(requestID);
				},
				error : function() {
					//alert('File Upload failed');
				}
			});
		}
		// Other certificates
		if ($jq("#otherCertFile").is(':visible') && $jq("#otherCertFile").val() != "") {
			otherCertificate = oc;
			$jq.ajaxFileUpload( {
				url : "leaveRequest.htm?otherCertName=" + otherCertificate + "&requestID=" + requestID + "&" + $jq('#workflowmap').serialize(),
				secureuri : false,
				fileElementId : 'otherCertFile',
				success : function() {
					alert("You have Successfully Uploaded Other Certificate \n");
					getRequestIdDetails(requestID);
				},
				error : function() {
					//alert('File Upload failed');
				}
			});
		}
		/*requestDetails = {
				"param" : "uploadedFilesUpdate",
				"mcFile": medicalCert,
				"fitnessFile": fitnessCert,
				"otherCertFile": otherCertificate,
				"medicalCertName" : mc,
				"fitnessCertName" : fc,
				"otherCertName" : oc
			};
		$jq.post('leaveRequest.htm', requestDetails, function(html) {
			$jq("#result").html(html);
		});*/
	} else {
		alert("Please select atleast one file\n");
	}
}

//Added by Narayana and modified by Naresh
function getOfflineSfidLeaves() {
	var status = true;
	var sfid = "";
	var errorMessage = "";
	if ($jq.trim($jq('#offlineSFID').val()) == '') {
		errorMessage += 'Please enter SFID\n';
		if (status) {
			status = false;
			$jq('#offlineSFID').focus();
		}
	} else {
		sfid = $jq.trim($jq('#offlineSFID').val());
	}
	if (status) {
		/*var requestDetais={
				"param" : "home",
				"offlineSFID" : $jq('#offlineSFID').val()
		};
		$jq.post('leaveRequest.htm',requestDetais,function(html) {
			$jq("#leaves").html(html);
			$jq('#offlineSFID').val(sfid);
		});	*/
		document.forms[0].type.value = "offline";
		document.forms[0].param.value = "home";
		document.forms[0].offlineSFID.value = sfid;
		document.forms[0].action = "leaveRequest.htm";
		document.forms[0].submit();
		$jq('#offlineSFID').val(sfid);
	} else {
		alert(errorMessage);
	}
}

function checkDopartDate(releaseDate) {
	 var sys = $jq('#' + releaseDate.id).parent().parent().find('td')[1].textContent;
	 if(compareDate(sys, releaseDate.value) && !($jq.trim(sys.toUpperCase().toString()) == $jq.trim(releaseDate.value.toUpperCase().toString()))) {
		 alert("Selected Date should Greater than Dopart Date");
		 $jq('#'+releaseDate.id).val('');
	 }
}

// Added by Naresh
// Leave Business Rules
function defaultBusinessRules(id) {
	$jq('.lbreditview' + id).css('display', 'none');
	$jq('.lbredit' + id).css('display', 'block');
	$jq('#encashed' + id).attr('disabled', 'true');
}

function editBusinessRules(id) {
	ruleId = id;
	if (!$jq('#encashed' + id).is(':checked')) {
		getRequestTypes();
		getLeaveTypes(new Array('one', 'two', 'three', 'four', 'five'));
		getConditionTypes();
		getDurationTypes();
		$jq('.lbredit' + id).css('display', 'none');
		$jq('#encashed' + id).removeAttr('disabled', 'true');
		$jq('#encashed' + id).attr('checked', 'true');
		$jq('.lbreditview' + id).css('display', 'block');
	}
}

function getRequestTypes() {
	$jq('#requesttype').find('option:not(:first)').remove().end();
	$jq('#requesttype').append("<option value='2'>Request</option>");
	$jq('#requesttype').append("<option value='6'>Cancel</option>");
	$jq('#requesttype').append("<option value='7'>Convert</option>");
}

function getLeaveTypes(components) {
	if (jsonLeaveTypes != null) {
		for (var c = 0; c < components.length; c++) {
			$jq('#' + components[c]).find('option:not(:first)').remove().end();
			for (var i = 0; i < jsonLeaveTypes.length; i++) {
				$jq('#' + components[c]).append($jq("<option></option>").attr("value", jsonLeaveTypes[i].id).text(jsonLeaveTypes[i].code));
			}
		}
	}
}

function getConditionTypes() {
	$jq('#condition').find('option:not(:first)').remove().end();
	$jq('#condition').append("<option value='='>=</option>");
	$jq('#condition').append("<option value='>'>></option>");
	$jq('#condition').append("<option value='>='>>=</option>");
	$jq('#condition').append("<option value='<'><</option>");
	$jq('#condition').append("<option value='<='><=</option>");
}

function getDurationTypes() {
	$jq('#durationtype').find('option:not(:first)').remove().end();
	$jq('#durationtype').append("<option value='1'>Days</option>");
	$jq('#durationtype').append("<option value='2'>Months</option>");
	$jq('#durationtype').append("<option value='3'>Years</option>");
}

function submitBusinessRules() {
	var errorMessage = '';
	var i = 0;
	var recordCount = 0;
	var mainJson = {};
	var validationStatus = true;
	var status = false;
	var count = -1;
	$jq('#result').text('');
	if ($jq('#businessRules').find('tr:not(:first)').children().size() == 1)
		count = 0;
	$jq('#businessRules').find('tr:not(:first)').each(function() {
		recordCount++;
		if ($jq(this).find('td').eq(0).find('input:checkbox').attr('checked')) {
			var requestType = $jq(this).find('td').eq(1).find('select').val();
			var one = $jq(this).find('td').eq(2).find('select').val();
			var two = $jq(this).find('td').eq(3).find('select').val();
			var three = $jq(this).find('td').eq(4).find('select').val();
			var four = $jq(this).find('td').eq(5).find('select').val();
			var five = $jq(this).find('td').eq(6).find('select').val();
			var condition = $jq(this).find('td').eq(7).find('select').val();
			var resultValue = $jq(this).find('td').eq(8).find('input').val();
			var duration = $jq(this).find('td').eq(9).find('select').val();
			var remarks = $jq.trim($jq(this).find('td').eq(10).find('textarea').val());
			if (requestType == 'select') {
				errorMessage += 'Select leave request type for the record : ' + recordCount + '\n';
				validationStatus = false;
			}
			if (one == 'select') {
				errorMessage += 'Select leave type-1 for the record : ' + recordCount + '\n';
				validationStatus = false;
			}
			if (two == 'select') {
				errorMessage += 'Select leave type-2 for the record : ' + recordCount + '\n';
				validationStatus = false;
			}
			if (condition == 'select') {
				errorMessage += 'Select Condition for the record : ' + recordCount + '\n';
				validationStatus = false;
			}
			if (resultValue == '') {
				errorMessage += 'Enter result value for the record : ' + recordCount + '\n';
				validationStatus = false;
			}
			if (duration == 'select') {
				errorMessage += 'Select Duration for the record : ' + recordCount + '\n';
				validationStatus = false;
			}
			if (remarks == '') {
				errorMessage += 'Enter remarks for the record : ' + recordCount + '\n';
				validationStatus = false;
			}
		}
	});
	if (validationStatus) {
		$jq('#businessRules').find('tr:not(:first)').each(function() {
			var innerJson = {};
			var three, four, five;
			if ($jq(this).find('td').eq(0).find('input:checkbox').attr('checked')) {
				status = true;
				innerJson['pk'] = $jq(this).find('td').eq(0).find('input:hidden').val();
				innerJson['requestyType'] = $jq(this).find('td').eq(1).find('select').val();
				innerJson['one'] = $jq(this).find('td').eq(2).find('select').val();
				innerJson['two'] = $jq(this).find('td').eq(3).find('select').val();
				three = ($jq(this).find('td').eq(4).find('select').val() != 'select') ? $jq(this).find('td').eq(4).find('select').val() : null; 
				innerJson['three'] = three;
				four = ($jq(this).find('td').eq(5).find('select').val() != 'select') ? $jq(this).find('td').eq(5).find('select').val() : null;
				innerJson['four'] = four;
				five = ($jq(this).find('td').eq(6).find('select').val() != 'select') ? $jq(this).find('td').eq(6).find('select').val() : null;
				innerJson['five'] = five;
				innerJson['condition'] = $jq(this).find('td').eq(7).find('select').val();
				innerJson['resultValue'] = $jq(this).find('td').eq(8).find('input').val();
				innerJson['duration'] = $jq(this).find('td').eq(9).find('select').val();
				innerJson['remarks'] = $jq(this).find('td').eq(10).find('textarea').val();
				mainJson[i] = innerJson;
				i++;
			}
		});
		if (status) {
			/*var requestDetails = {
					"param" : "saveBusinessRules",
					"businessRulesList" : JSON.stringify(mainJson)
			};
			$jq.post("leaveAdmin.htm", requestDetails, function(html) {
				$jq('#result').html(html);
			});*/
			document.forms[0].param.value = "saveBusinessRules";
			document.forms[0].businessRulesList.value = JSON.stringify(mainJson); 
			document.forms[0].action = "leaveAdmin.htm";
			document.forms[0].submit();
		} else {
			if (count == 0)
				alert("No more records found\n");
			else
				alert("Please Check atleast one Record\n");
		}
	} else {
		alert(errorMessage);
	}
}

/*function clearBusinessRules() {
	$jq('.lbredit').css('display', 'block');
	$jq('.lbreditview').css('display', 'none');
}
*/
function deleteBusinessRule(key) {
	var check = confirm("Are you sure do u want to disable this rule ?");
	if (check) {
		/*var requestDetails = {
				"param" : 'deleteBusinessRule',
				"pk" : key
		};
		$jq.post("leaveAdmin.htm", requestDetails, function(html) {
			$jq('#result').html(html);
		});*/
		document.forms[0].param.value = "deleteBusinessRule";
		document.forms[0].pk.value = key;
		document.forms[0].businessRulesList.value = null; 
		document.forms[0].action = "leaveAdmin.htm";
		document.forms[0].submit();
	}
}

var dynarow = 0;
function createRecord() {
	dynarow++;
	var dynaClass;
	if ($jq('#businessRules').find('tr:not(:first)').children().size() == 1) {
		$jq('#businessRules').find('tr:last').remove();
		dynaClass = 'even';
	} else if ($jq('#businessRules').find('tr:not(:first)').children().size() == 0) {
		dynaClass = 'even';
	} else {
		dynaClass = ($jq('#businessRules').find('tr:last')[0].attributes[0].nodeValue == 'even') ? 'odd' : 'even';
	}
	$jq('#businessRules').append($jq('<tr class="'+ dynaClass +'">'+
					'<td><input type="checkbox" id="dynacheckbox" disabled="true" checked="true" /><input type="hidden" value="0" /></td>'+
					'<td><select id="dynarequesttype" style="width: 100%;"><option value="select">Select</option><option value="2">Request</option><option value="6">Cancel</option><option value="7">Convert</option></select></td>'+
					'<td><select id="dynaone" style="width: 100%;"><option value="select">Select</option></select></td>'+
					'<td><select id="dynatwo" style="width: 100%;"><option value="select">Select</option></select></td>'+
					'<td><select id="dynathree" style="width: 100%;"><option value="select">Select</option></select></td>'+
					'<td><select id="dynafour" style="width: 100%;"><option value="select">Select</option></select></td>'+
					'<td><select id="dynafive" style="width: 100%;"><option value="select">Select</option></select></td>'+
					'<td><select id="dynacondition" style="width: 100%;"><option value="select">Select</option><option value="=">=</option><option value=">">></option><option value=">=">>=</option><option value="<"><</option><option value="<="><=</option></select></td>'+
					'<td><input type="text" size="5" maxlength="4" value="" style="text-align: right;" onkeypress="return checkInt(event)" /></td>'+
					'<td><select id="dynadurationtype" style="width: 100%;"><option value="select">Select</option><option value="1">Days</option><option value="2">Months</option><option value="3">Years</option></select></td>'+
					'<td colspan="3"><textarea cols="56" rows="1" id="dynaremarks" style="width: 99%;"></textarea></td>'+
				'</tr>'
	)).css("display", "block");
	getLeaveTypes(new Array('dynaone', 'dynatwo', 'dynathree', 'dynafour', 'dynafive'));
	$jq('#cancelRule').css('display', 'block');
}

function deleteRecord() {
	if (dynarow > 0) {
		$jq('#businessRules').find('tr:last').remove();
		dynarow--;
		if (dynarow == 0) $jq('#cancelRule').css('display', 'none');
	}
}
// End
function leaveAmendment(requestId, leaveCode, type) {
	$jq('#result').text('');
	$jq.post('leaveRequest.htm?param=checkCancelConvertStatus&requestID=' + requestId, function(html) {
		$jq('#result').html(html);
		if ($jq('.success').hasClass('success')) {
			document.forms[0].param.value = "leaveAmendment";
			document.forms[0].requestID.value = requestId;
			document.forms[0].action = "leaveRequest.htm";
			document.forms[0].submit();
		} else {
			alert("Cancellation / Conversion request has been applied for this leave\nHence Leave amendment is not possible!...");
		}
    });
}

function buildContent(leaveId) {
	switch (leaveId) {
		case 3 : if (fromHalfDayFlag == 'Y') {
					$jq('#fromHalfDayFlag').attr('checked', 'checked');  
				}
				if (toHalfDayFlag == 'Y') {
					$jq('#toHalfDayFlag').attr('checked', 'checked');
				}
				if ($jq.trim(fromDate) == $jq.trim(toDate)) {
					$jq('#fromHalfDayFlag').siblings().text('Forenoon');
					$jq('#toHalfDayFlag').siblings().text('Afternoon');
				}
				$jq('#days').text(noOfDays);
				break;
		case 8 : $jq('#deliveryDate').val(additionalData);
				break;
		case 9 : if ($jq('#childName').find('option:not(:first)').size() >= 1) {
					$jq('#childName').find('option:not(:first)').attr('selected', 'selected');
				}
				break;
		case 10 : if ($jq('#studyDegree').is(':visible')) {
					for (var i = 0; i < $jq('#studyDegree').find('option:not(:first)').size(); i++) {
						if ($jq.trim($jq('#studyDegree').find('option')[i + 1].text) == $jq.trim(additionalData)) {
							$jq('#studyDegree').val($jq('#studyDegree').find('option')[i + 1].value).attr('selected', 'selected');
							break;
						}
					}
				}
				break;
		case 14 : $jq('#deliveryDate').val(additionalData);
				break;
		case 16 : $jq('#deliveryDate').val(additionalData);
				break;
	}

	if (leaveId != 4 && leaveId != 5) {
		if ($jq('#otherCertExceptions').is(':visible')) {
			$jq('#otherCertExceptions').find('input:radio').attr('checked', 'checked');
		}
		if ($jq('#mcExceptions').is(':visible')) {
			$jq('#mcExceptions').find('input:radio').attr('checked', 'checked');
		}
		if ($jq('#fitnessExceptions').is(':visible')) {
			$jq('#fitnessExceptions').find('input:radio').attr('checked', 'checked');
		}
	}
}

function submitAmendmentDetails(inputString) {
	//var components = new Array(fromDate, previousHolidays, fromHalfDayFlag, toDate, nextHolidays, toHalfDayFlag, noOfDays);
	var errorMessage = '';
	var status = true;
	if ($jq('#fromDate').val() == fromDate && $jq('#toDate').val() == toDate && parseFloat($jq('#days').text()) == parseFloat(noOfDays)) {
		errorMessage += 'Amendment is not possible!\n';
		status = false;
	}
	if ($jq('#leaveType').val() == '10') {
		if (compareDate($jq('#toDate').val(), formatDateIntoString(addMonths($jq('#fromDate').val(), 4), 'dd-MMM-yyyy'))) {
			errorMessage += 'Study leave can not be extend to 4 months\nHence amendment is not possible!\n';
			status = false;
		}
	}
	if (status) {
		status = submitLeave(inputString);
	}
	if (status) {
		var check = confirm('Leave period is from  ' + $jq('#fromDate').val() + '  to  ' + $jq('#toDate').val() + '   ( ' + $jq('#days').text() + ' )\n');
		if (check) {
			// cancel already applied leave
			$jq.post('leaveRequest.htm?param=cancelAppliedLeave&requestID=' + inputString.split('#')[0], function(html) {
				$jq('#result').html(html);
				if ($jq('.success').hasClass('success')) {
					creditLeaves($jq('#leaveType').val(), noOfDays);
					var submission = !checkLeavesCredit($jq('#leaveType').val(), $jq.trim($jq('#days').text()), null);
					if (submission) {
						submitLeaveReq(inputString);
					} else {
						// Rollback cancellation of already applied leave
						$jq.post('leaveRequest.htm?param=rollbackCancelAppliedLeave&requestID=' + requestId, function(html) {
							$jq('#result').html(html);
							if ($jq('.success').hasClass('success')) {
								debitLeaves(leaveRequestDetails.leaveType, leaveRequestDetails.noOfDays);
								alert("Sorry! You do not have enough balance in your account\n");
							}
						});
					}
				}
			});
		}
	} else {
		(status != undefined) ? alert(errorMessage) : false;
	}
}
