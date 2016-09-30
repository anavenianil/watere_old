function manageReferralHospital() {
	var status=true;
	var errorMessage='';
	if($jq('#hospitalName').val()==''){	
		errorMessage+='Please Enter hospital name\n';
		if(status) {
			$jq('#hospitalName').focus();
			status=false;
		}
	}
	/*
	 * if($jq('#bankName').val()==''){ errorMessage+='Please Enter bank name\n';
	 * if(status) { $jq('#bankName').focus(); status=false; } }
	 * if($jq('#branchName').val()==''){ errorMessage+='Please Enter branch
	 * name\n'; if(status) { $jq('#branchName').focus(); status=false; } }
	 * if($jq('#acNumber').val()==''){ errorMessage+='Please Enter Account
	 * Number\n'; if(status) { $jq('#acNumber').focus(); status=false; } }
	 */
	if($jq('#hospitalLocation').val()==''){	
		errorMessage+='Please Enter hospital location\n';
		if(status) {
			$jq('#hospitalLocation').focus();
			status=false;
		}
	}
	if($jq('#address').val()==''){	
		errorMessage+='Please Enter address\n';
		if(status) {
			$jq('#address').focus();
			status=false;
		}
	}
	if(status) {
		$jq('#param').val('manage');
		$jq.post('cghs.htm'+'?'+dispUrl, $jq('#cghs').serialize(), function(html) {
			$jq("#hospitalList").html(html);
			clearReferralHospital();
		});
	}else {
		alert(errorMessage);
	}
}
function clearReferralHospital() {
	$jq('#acNumber').val('');
	$jq('#bankName').val('');
	$jq('#branchName').val('');
	$jq('#hospitalName').val('');
	$jq('#hospitalLocation').val('');
	// $jq('#creationDate').val('');
	$jq('#address').val('');
	$jq('#pk').val('');
	$jq('#counter').val('500');
	
}
function editReferralHospital(id){
	for (var i=0 ;i<referralList.length;i++){
		if(id==referralList[i].id) {
			$jq('#acNumber').val(referralList[i].acNumber);
			$jq('#bankName').val(referralList[i].bankName);
			$jq('#branchName').val(referralList[i].branchName);
			$jq('#creationDate').val(referralList[i].strCreationDate);
			$jq('#hospitalName').val(referralList[i].hospitalName);
			$jq('#hospitalLocation').val(referralList[i].hospitalLocation);
			$jq('#address').val(referralList[i].address);
			$jq('#pk').val(id);
			 if (referralList[i].address .length> 0) {
				  document.getElementById('counter').value = 500;
				  document.getElementById('counter').value -= referralList[i].address.length;
				 } 
		}
	}
}
function deleteReferralHospital(id) {
	$jq('#param').val('delete');
	$jq('#pk').val(id);
	$jq.post('cghs.htm'+'?'+dispUrl, $jq('#cghs').serialize(), function(html) {
		$jq("#hospitalList").html(html);
	});
}
function selectedDeleteReferralHospital() {
	var selectedValues='';
	var status=true;
	var errorMessage='';
	/*
	 * for (var i=0 ;i<referralList.length;i++){ var j=referralList[i].id;
	 * if(document.getElementById('hospitalId'+j).checked) { selectedValues =
	 * selectedValues + document.getElementById('hospitalId'+j).value+','; } }
	 */
	if($jq('#deletionDate').is(':visible') && $jq('#deletionDate').val()=='') {
		errorMessage+='Please enter deletion date\n';
		if(status) {
			$jq('#deletionDate').focus();
			status=false;
		}
	}
	$jq("#referral tr:not(:first)").each(function() {	
		if($jq(this).find("td").eq(0).find(":input:checkbox").is(':checked')){
			selectedValues+=$jq(this).find("td").eq(0).find(":input:checkbox").attr('value')+","
		}		
	});
	if(selectedValues=='') {
		errorMessage+="Check any referral hospital in bellow list ";
		status=false;
	}
	if(status) {
		$jq('#selectedDelete').val(selectedValues);
		$jq('#param').val('selectedDelete');
		$jq.post('cghs.htm', $jq('#cghs').serialize(), function(html) {
			$jq("#hospitalList").html(html);
		});
	}else {
		alert(errorMessage);
	}
}
function enableDeleteDatePicker() {
	for (var i=0 ;i<referralList.length;i++){
		var j=referralList[i].id;
		if($jq('#hospitalId'+j).is(':visible') && $jq('#hospitalId'+j).is(':checked')) {
			$jq('#deleteDate').attr('style','display:block');
			return;
		}else {
			$jq('#deleteDate').attr('style','display:none');
			$jq('#deletionDate').val('');
		}
	}
}
function clearWardType() {
	$jq('#wardName').val('');
	$jq('#startBasicPay').val('');
	$jq('#endBasicPay').val('');
	$jq('#pk').val('');
}
function manageWardType() {
	var status=true;
	var errorMessage='';
	if($jq('#wardName').val()==''){	
		errorMessage+='Please Enter ward name\n';
		if(status) {
			$jq('#wardName').focus();
			status=false;
		}
	}
	if($jq('#startBasicPay').val()==''){	
		errorMessage+='Please Enter pay band from\n';
		if(status) {
			$jq('#startBasicPay').focus();
			status=false;
		}
	}
	if($jq('#endBasicPay').val()==''){	
		errorMessage+='Please Enter pay band to\n';
		if(status) {
			$jq('#endBasicPay').focus();
			status=false;
		}
	}
	if(status){
		$jq('#param').val('wardManage');
		$jq.post('cghs.htm'+'?'+dispUrl, $jq('#cghs').serialize(), function(html) {
			$jq("#wardTypeList").html(html);
			clearWardType();
		});
	}else {
		alert(errorMessage);
	}
}
function editWardType(id){
	for (var i=0 ;i<wardTypeList.length;i++){
		if(id==wardTypeList[i].id) {
			$jq('#wardName').val(wardTypeList[i].name);
			$jq('#startBasicPay').val(wardTypeList[i].startBasicPay);
			$jq('#endBasicPay').val(wardTypeList[i].endBasicPay);
			$jq('#pk').val(id);
		}
	}
}
function deleteWardType(id) {
	$jq('#param').val('wardDelete');
	$jq('#pk').val(id);
	$jq.post('cghs.htm'+'?'+dispUrl, $jq('#cghs').serialize(), function(html) {
		$jq("#wardTypeList").html(html);
		clearWardType();
	});
}
function displayFamilyMemberDetails() {
	if($jq('#familyMemberId').val()=='') {
		$jq('#familyMemberDetails').attr('style','display:none');
	}else {
		$jq('#familyMemberDetails').attr('style','display:block');
		for(var i=0;i<familyMember.length;i++) {	
			if($jq('#familyMemberId').val()==familyMember[i].id){
				$jq('#dob').html(familyMember[i].dob);
				$jq('#age').html(familyMember[i].age);
				$jq('#cghscardref').val(familyMember[i].cghscard);
				if(familyMember[i].gender.toUpperCase()=='M') {
					$jq('#gender').html('Male'); }
				else if(familyMember[i].gender.toUpperCase()=='F') {
					$jq('#gender').html('Female'); }
				else {
					$jq('#gender').html(''); 
				}
			}
			
		}
	}
}

function displayFamilyMemberDetailsreimb(id) {
	if($jq('#'+id).val()=='') {
		$jq('#familyMemberDetails').attr('style','display:none');
	}else {
		$jq('#familyMemberDetails').attr('style','display:block');
		for(var i=0;i<familyMember.length;i++) {	
			if($jq('#'+id).val()==familyMember[i].id){
				$jq('#dob').html(familyMember[i].dob);
				$jq('#age').html(familyMember[i].age);
				$jq('#cghscardref').val(familyMember[i].cghscard);
				if(familyMember[i].gender.toUpperCase()=='M') {
					$jq('#gender').html('Male'); }
				else if(familyMember[i].gender.toUpperCase()=='F') {
					$jq('#gender').html('Female'); }
				else {
					$jq('#gender').html(''); 
				}
			}
			
		}
	}
}
function clearTreatmentRequest() {
	$jq('#familyMemberId').val('');
	// $jq('#disease').val('');
	$jq('#prescriptionDate').val('');
	$jq('#cghsRequestTypeId').val('');
	$jq('#referredDoctor').val('');
	$jq('#referralHospitalId').val('');
	$jq('#referenceSpecialist').val('');
	$jq('#prescriptionFile').val('');
}
function getRefHospitalList(todaysDate) {
	
	if(parseInt(compareDates(convertDate($jq('#prescriptionDate').val()),convertDate(todaysDate)))<=30){
		$jq.post('cghsRequest.htm?param=refHos&permissionDetails='+$jq('#prescriptionDate').val(), function(html) {
		$jq("#refHospitalList").html(html);
	});
	}else {
		alert('Prescription date should be not exceed 30 days from current date');
		$jq('#prescriptionDate').val('');
	}
	
}
/*
 * jQuery(window).bind('beforeunload',function(event){ event.stopPropagation();
 * event.returnValue = "Are you Sure You Want to Close Window? You Must Complete
 * Advance page "; return event.returnValue; });
 * 
 */
function manageTreatmentRequest() {
	var status=true;
	var errorMessage='';
	if($jq('.headTitle').html()=='Offline CGHS Request Form'){
		if($jq('#offlineSFID').val()==''){
		$jq('#offlineSFID').focus();
		status=false;
		errorMessage+='Please Enter SFID Number\n';
	}
		}
	if($jq('#familyMemberId').val()=='')
	{
		$jq('#familyMemberId').focus();
		status=false;
		errorMessage+='Please Select family member\n';
	}
	/*
	 * if($jq('#disease').val()=='') { if(status) { $jq('#disease').focus();
	 * status=false; } errorMessage+='Please Enter disease\n'; }
	 */
	/*if($jq('#cghsRequestTypeId').val() == 1){
		  var today=new Date();
		  today.setDate(today.getDate()+1);
		  today=formatDate(today,'dd-NNN-yyyy');
		  var pdate=$jq('#prescriptionDate').val();	  
          if(compareDate(pdate,today)){	  
	     	  status=false;
	     	  $jq('#prescriptionDate').focus();
	     	  errorMessage+='PrescriptionDate ShouldNot Cross TodayDate.Please Select Right Date!\n'
     	  }
	 }*/
	
	
	if($jq('#cghsRequestTypeId').val()=='')
	{
		if(status) {
		$jq('#cghsRequestTypeId').focus();
		status=false;
		}
		errorMessage+='Please Select request type\n';
	}
	/*
	 * if($jq('#referredDoctor').val()=='') { if(status) {
	 * $jq('#referredDoctor').focus(); status=false; } errorMessage+='Please
	 * Enter referred doctor name\n'; }
	 */
	if($jq('#referralHospitalId').val()=='')
	{	errorMessage+='Please Select Hospital Name\n';
		if(status) {
		$jq('#referralHospitalId').focus();
		status=false;
		}
	}
	if($jq('#prescriptionDate').val()=='')
	{
		if(status) {
		$jq('#prescriptionDate').focus();
		status=false;
		}
		errorMessage+='Please Select prescription date\n';
	}
	/*var sys=formatDate(sysdate,'dd-NNN-yyyy');
	if(compareDates(convertDate(sys),'dd-MM-yyyy',convertDate(fromDate),'dd-MM-yyyy')==1){
		$jq('#'+rid).attr("checked",false);
		errMsg += "From Date Must be Greater or Equal to Today Date \n";
		flag = false;
	}*/
	
/*
 * if($jq('#referenceSpecialist').val()=='') { if(status) {
 * $jq('#referenceSpecialist').focus(); status=false; } errorMessage+='Please
 * Enter reference specialist name\n'; }
 */
	if($jq('#files').val()!='' && !checkFileExtention($jq('#files').val().split('.')[$jq('#files').val().split('.').length-1])) {
	 	errorMessage+='Upload only .docx,.doc,.txt,.pdf\n';
	 	status = false;  
	}
	
	if($jq('#familyMemberId')!=''){
		for(var i=0;i<familyMember.length;i++) {
			if($jq('#familyMemberId').val().split('#')[0] ==familyMember[i].id){
		      var relation=familyMember[i].name;
		      if((relation.match('SON'))&&familyMember[i].age>25){
		    	  status=false;
		          errorMessage+='Sorry! Your Child Not Eligble For CGHS.Bcoz Age>25 Years.';
		    	  clearTreatmentRequest();
	        }
			
		}
		}
		}
	
	
	
	if(status){
		
		
		/*if($jq('#files').val()!='') {
			$jq.ajaxFileUpload(
		  		{
			   	url:"file.htm?param=upload&type=cghs",
			   	secureuri:false,
			   	fileElementId :'files',
			   	success: function (data, status) {
		  			// success
		  		}, error: function (data, status, e) {
			    		alert('File Upload failed');
			    	}
			 });	
		}
		*/
		   
	   
			$jq('#param').val('manage');
			$jq('#type').val('treatment');
			//$jq('#familyMemberId').val(familyId);

			$jq.ajaxFileUpload(
					{
						url:"cghsRequest.htm?"+ $jq('#cghs').serialize(),
						secureuri:false,
						fileElementId :'files',
						// sync: true,
						// async: false,
						success: function (data, status) {
						if(status=='success'){
					   $jq("#result").html($jq(data.body).html());
					   var check=confirm("CGHS Request has been Successfully Sent...\nPreview CGHS Application Form & print from here");
						if(check){
						document.forms[0].requestId.value = $jq.trim(requestIDCGHs);
						document.forms[0].param.value = "requestDetails";
						document.forms[0].action = "workflowmap.htm";
						document.forms[0].submit();	
						}
						 }
						// alert('success');
						}, error: function (data, status, e) {
						// when failure
						status=false; }
						}); 
		
		  setlement = $jq('input:radio[name=settlement]:checked').val();
		  
			/*
			 * $jq('#param').val('manage'); $jq('#type').val('treatment');
			 * $jq.post('cghsRequest.htm', $jq('#cghs').serialize(),
			 * function(html) { $jq("#result").html(html); });
			 */
	// }
		 clearTreatmentRequest();
		
		}else {
			alert(errorMessage);
		}
}
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
function clearAdvanceDetails() {
	$jq('#issuedPlace').val('');
	$jq('#validFrom').val('');
	$jq('#validTo').val('');
	$jq('#admissionDate').val('');
	$jq('#permissionDetails').val('');
	$jq('#files1').val('');
	$jq('#files3').val('');
	$jq('#files2').val('');
	$jq('#estimationAmount').val('');
	$jq('#estimationDate').val('');
	$jq('#InPatientNumber').val('');
}
function manageAdvanceDetails(requestId) {
var status=true;
var errorMessage='';
//$jq('#cghscardref').val(familyMember[i].cghscard);
if($jq('#admissionDate').val()=='')
{	if(status) {
		$jq('#admissionDate').focus();
		status=false;
	}errorMessage+='Please Enter admission date\n';
}
if($jq('#estimationDate').val()=='')
{	if(status) {
		$jq('#estimationDate').focus();
		status=false;
	}errorMessage+='Please Enter estimation date\n';
}
if($jq('#estimationAmount').val()=='')
{	if(status) {
	$jq('#estimationAmount').focus();
	status=false;
}errorMessage+='Please Enter estimation amount\n';
}
if (!$jq('#admissionDate').val()=='') {
	
	dateCheck = $jq('#admissionDate').val();
	var dob = $jq('#estimationDate').val();
	
	if(!compareDate(dob,dateCheck)) {
		errorMessage += "Estimation Date Of Relieving Is After Date of Joining \n";
		if (status) {
			$jq('#estimationDate').focus();
			status = false;
		}
	}
}

/*
 * if($jq('#InPatientNumber').val()=='') { if(status) {
 * $jq('#InPatientNumber').focus(); status=false; }errorMessage+='Please Enter
 * in-patient number\n'; }
 */
for(var i=1;i<=2;i++) {
	if($jq('#files'+i).val()!='' && !checkFileExtention($jq('#files'+i).val().split('.')[$jq('#files'+i).val().split('.').length-1])) {
		errorMessage+='Upload only .docx,.doc,.txt,.pdf\n';
		status = false;
	}
}
/*
 * cghsAdvanceRequestDetails={"param" : "manage", "type" : "cghsAdvance",
 * "settlement": "advance", "admissionDate" :$jq('#admissionDate').val(),
 * "estimationDate":$jq('#estimationDate').val(),
 * "estimationAmount":$jq('#estimationAmount').val(),
 * "InPatientNumber":$jq('#InPatientNumber').val(),
 * "permissionDetails":$jq('#permissionDetails').val(),
 * "estimationFile":$jq('#files1').val(), "cghsCardFile" : $jq('#files2').val(),
 * "requestId" : requestId,
 *  }
 */



if(status){
	$jq('#param').val('manage'); 

	$jq('#requestId').val(requestId);
	$jq('#type').val('cghsAdvance');
		if($jq('#files'+i).val()!='') {
			$jq.ajaxFileUpload(
					{
						url:"cghsRequest.htm?"+ $jq('#cghs').serialize(),
						secureuri:false,
						fileElementId :'files',
						success: function (data, status) {
						if(status=='success'){
						    $jq("#result").html($jq(data.body).html());
							var check=confirm(" CGHS Request has been Successfully Sent...\n\nPlease click ok to 'Preview CGHS	Request Form 'Take print' from here\n\n");
							if(check){
					    document.forms[0].requestId.value = $jq.trim(requestIDCGHs);
							//document.forms[0].roleId.value = 'roleId';
						   document.forms[0].param.value = "requestDetails";
							document.forms[0].action = "workflowmap.htm";
							document.forms[0].submit();	
						}}
						// alert('success');
						}, error: function (data, status, e) {
						// when failure
						// alert('File Upload failed');
						status=false; }
						}); 
		}
		clearAdvanceDetails();

// if(status) {
// $jq('#param').val('manage');
// $jq('#type').val('cghsAdvance');
// $jq('#settlement').val('advance');
// $jq.post('cghsRequest.htm?requestId='+requestId, $jq('#cghs').serialize(),
// function(html) {
// $jq("#result").html(html);
// });
// clearAdvanceDetails();
// }
}else {
	alert(errorMessage);
}
/*
 * jQuery(window).bind('beforeunload',function(event){ event.stopPropagation();
 * event.returnValue = "You Must Complete The Advance Form to get"; return
 * event.returnValue; });
 */
}
function clearReimbursementDetails() {
	$jq('#issuedPlace').val('');
	$jq('#validFrom').val('');
	$jq('#validTo').val('');
	$jq('#familyMembetId').val('');
	$jq('#disease').val('');
	$jq('#hospitalDetails').val('');
	$jq('#admissionDate').val('');
	$jq('#dischargeDate').val('');
	$jq('#amountClaimed').val('');
	$jq('#bankName').val('');
	$jq('#bankBranch').val('');
	$jq('#bankAcNo').val('');
	$jq('#permissionDetails').val('');
	$jq('#advanceDetails').val('');
	
	$jq('#labCharges').val('');
	$jq('#medicinesCharges').val('');
	$jq('#consultationFees').val('');
	$jq('#disposableCharges').val('');
	$jq('#specialDevices').val('');
	$jq('#miscellaneousCharges').val('');
	$jq('#roomRent').val('');
	$jq('#otCharges').val('');
	$jq('#otConsumables').val('');
	$jq('#anaesthesiaCharges').val('');
	$jq('#implantsCharges').val('');
	$jq('#artificialCharges').val('');
	$jq('#procedure').val('');
	$jq('#specialNurse').val('');
	$jq('#noofVouchers').val('');
	
	$jq('#files1').val('');
	$jq('#files2').val('');
	$jq('#files3').val('');
	$jq('#files4').val('');
	$jq('#files5').val('');
	$jq('#files6').val('');
	$jq('#files7').val('');
	$jq('#files8').val('');
	$jq('#files9').val('');
	$jq('#counter').val('500');
	$jq('#total').html('0');
	$jq('#requestId').val('');
	$jq('#requestID').val('');
}
function manageReimbursementDetails(requestId,type) {
var status=true;
var errorMessage='';
if($jq("#hospitalDetails").is(':visible') && $jq('#hospitalDetails').val()==''){	
	errorMessage+='Please Enter hospital details\n';
	if(status) {
		$jq('#hospitalDetails').focus();
		status=false;
	}
}
if($jq("#familyMembetId").is(':visible') && $jq('#familyMembetId').val()==''){	
	errorMessage+='Please Select Family Member\n';
	if(status) {
		$jq('#familyMembetId').focus();
		status=false;
	}
} 
if($jq('#disease').val()=='' && $jq("#disease").is(':visible')){	
	errorMessage+='Please Enter Disease Name\n';
	if(status) {
		$jq('#disease').focus();
		status=false;
	}
}
if($jq('#hostipalAddress').val()=='' && $jq("#hostipalAddress").is(':visible')){	
	errorMessage+='Please Enter Hostipal Address\n';
	if(status) {
		$jq('#hostipalAddress').focus();
		status=false;
	}
}
if($jq("#admissionDate").is(':visible') && $jq('#admissionDate').val()==''){	
	errorMessage+='Please Enter admission date\n';
	if(status) {
		$jq('#admissionDate').focus();
		status=false;
	}
}
if($jq("#dischargeDate").is(':visible') && $jq('#dischargeDate').val()==''){	
	errorMessage+='Please Enter discharge date\n';
	if(status) {
		$jq('#dischargeDate').focus();
		status=false;
	}
}
if($jq("#dischargeDate").is(':visible') && $jq("#admissionDate").is(':visible') && compareDatesForCghs($jq('#admissionDate').val(), 'dd-MMM-yyyy', $jq('#dischargeDate').val(),'dd-MMM-yyyy') == 1) {
	errorMessage += "Invalid Dates \n";
	if (status) {
		status = false;
		$jq('#admissionDate').focus();
	}
}
/*
 * if($jq('#amountClaimed').val()==''){ errorMessage+='Please Enter amount
 * calimed\n'; if(status) { $jq('#amountCalimed').focus(); status=false; } }
 */
$jq('#settleAmount').val($jq('#settelmentMoney').html());
 // hospital = $jq('#hospitalName').text().trim().split("\n")[1]);
// patient = $jq('#patientName').text().trim().split("\n")[1]);

// $jq('#familyMembetId').val(($jq('#patient').find('input:hidden').val()));
// $jq('#hospitalDetails').val(($jq('#hospital')).html());



for(var i=1;i<=4;i++) {
	if($jq('#files'+i).is(':visible') && $jq('#files'+i).val()!='' && !checkFileExtention($jq('#files'+i).val().split('.')[$jq('#files'+i).val().split('.').length-1])) {
		errorMessage+='Upload only .docx,.doc,.txt,.pdf\n';
		status = false;
	}
}
if($jq('#total').html()=='' || parseInt($jq('#total').html())==0) {
	errorMessage+='Could not process the request, total amount is zero.Please enter breakup amount details';
	status = false;
}
if(type=='cghssettlement' && $jq('#settelmentMoney').html()=='' || parseInt($jq('#settelmentMoney').html())==0){
	errorMessage+='Could not process the request, total amount is zero.Please enter breakup amount details\n';
	status = false;
}
if(type=='cghssettlement' && !$jq('#issuedAmount').is(':visible')) {
	errorMessage+='CDA amount not yet issued';
	$jq('#dischargeDate').val('');
	status = false;
}

// cghsReimRequestDetails={"param" : "manage",
// "type" : type,
// "requestId" : requestId,
// "settlement": "advance",
// "labCharges" : $jq('#labCharges').val(),
// "medicinesCharges":$jq('#medicinesCharges').val(),
// "disposableCharges":$jq('#disposableCharges').val(),
// "consultationFees":$jq('#consultationFees').val(),
// "specialDevices" : $jq('#specialDevices').val()
//        
// }
if(status) {
	$jq('#param').val('manage');
	$jq('#type').val(type);
    $jq('#requestId').val(requestId);
    if($jq('#headTitle').text()== "CGHS Reimbursement Request Details Form"){
	    $jq('#familyMembetId').val($jq('#patient').find('input:hidden').val().trim());
	    $jq('#hospitalDetails').val(($jq('#hospital').text().trim()));
	}
	
				$jq.ajaxFileUpload(
						{
							url:"cghsRequest.htm?"+$jq('#cghs').serialize(),
							secureuri:false,
							fileElementId :'files',
							// sync: true,
							// async: false,
							success: function (data, status) {
							 $jq("#result").html($jq(data.body).html());
							if ($jq('.success').is(':visible')) {
							   
							    var check=confirm(" NON CGHS Request has been Successfully Sent...\nPreview NON CGHS Application Form & print from here");
								if(check){
								document.forms[0].requestId.value = $jq.trim(requestIDCGHs);
								
							   document.forms[0].param.value = "requestDetails";
								document.forms[0].action = "workflowmap.htm";
								document.forms[0].submit();	
								}
							 }
							// alert('success');
							}, error: function (data, status, e) {
							// when failure
							// alert('File Upload failed');
							status=false; }
							});
			
			clearReimbursementDetails();
		
		
		
		
		/*
		 * if(status) { $jq('#param').val('manage'); $jq('#type').val(type);
		 * if($jq('#headTitle').text()== "CGHS Reimbursement Request Details
		 * Form"){
		 * $jq('#familyMembetId').val($jq('#patient').find('input:hidden').val().trim());
		 * $jq('#hospitalDetails').val(($jq('#hospital').text().trim())); }
		 * $jq.post('cghsRequest.htm?requestId='+requestId,$jq('#cghs').serialize(),function(html) {
		 * $jq("#result").html(html); });
		 */
	    	
		
}else {
	alert(errorMessage);
}
	
	
}
function sumAmount() {
	var cghsMoneyValue=0;
	if($jq("#labCharges").is(':visible') && $jq("#labCharges").val()!='') {
		cghsMoneyValue+=parseFloat($jq("#labCharges").val());
	}if($jq("#medicinesCharges").is(':visible') && $jq("#medicinesCharges").val()!='') {
		cghsMoneyValue+=parseFloat($jq("#medicinesCharges").val());
	}if($jq("#consultationFees").is(':visible') && $jq("#consultationFees").val()!='') {
		cghsMoneyValue+=parseFloat($jq("#consultationFees").val());
	}if($jq("#disposableCharges").is(':visible') && $jq("#disposableCharges").val()!='') {
		cghsMoneyValue+=parseFloat($jq("#disposableCharges").val());
	}if($jq("#specialDevices").is(':visible') && $jq("#specialDevices").val()!='') {
		cghsMoneyValue+=parseFloat($jq("#specialDevices").val());
	}if($jq("#roomRent").is(':visible') && $jq("#roomRent").val()!='') {
		cghsMoneyValue+=parseFloat($jq("#roomRent").val());
	}if($jq("#otCharges").is(':visible') && $jq("#otCharges").val()!='') {
		cghsMoneyValue+=parseFloat($jq("#otCharges").val());
	}if($jq("#otConsumables").is(':visible') && $jq("#otConsumables").val()!='') {
		cghsMoneyValue+=parseFloat($jq("#otConsumables").val());
	}if($jq("#anaesthesiaCharges").is(':visible') && $jq("#anaesthesiaCharges").val()!='') {
		cghsMoneyValue+=parseFloat($jq("#anaesthesiaCharges").val());
	}if($jq("#implantsCharges").is(':visible') && $jq("#implantsCharges").val()!='') {
		cghsMoneyValue+=parseFloat($jq("#implantsCharges").val());
	}if($jq("#artificialCharges").is(':visible') && $jq("#artificialCharges").val()!='') {
		cghsMoneyValue+=parseFloat($jq("#artificialCharges").val());
	}if($jq("#procedure").is(':visible') && $jq("#procedure").val()!='') {
		cghsMoneyValue+=parseFloat($jq("#procedure").val());
	}if($jq("#specialNurse").is(':visible') && $jq("#specialNurse").val()!='') {
		cghsMoneyValue+=parseFloat($jq("#specialNurse").val());
	}if($jq("#miscellaneousCharges").is(':visible') && $jq("#miscellaneousCharges").val()!='') {
		cghsMoneyValue+=parseFloat($jq("#miscellaneousCharges").val());
	}
	return cghsMoneyValue;
}
function CalculateAmount(type) {
	var cghsMoneyValue=0;
	if(type=='noncghsReimbursement' || type=='cghsreimbursement' || type=='emergency') {
		$jq('#total').html(sumAmount());
	}
	if($jq("#SettelmentAmount").is(':visible')) {
		cghsMoneyValue = sumAmount();
		$jq("#settelmentMoney").html(cghsMoneyValue-parseInt($jq("#issuedAmount").html()));
	}else if(type=='cghssettlement'){
		alert('CDA Amont not yet issued');
		$jq('#amountClaimed').val('');
	}
}
function downloadFile(file) {
	if(file!='') {
		window.open('file.htm?param=download','fullscreen=no,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no')
	}else {
		alert('Requester has not uploaded this document');
	}
	
// $jq('#type').val(type);
// $jq.post('file.htm?param=download',function(html) {
// $jq("#result").html(html);
// });
	
}
function setFormValues(cghsRequestType) {
	if(cghsRequestType=='cghsreimbursement') {
		$jq('#headTitle').html('CGHS Reimbursement Request Details Form')
		document.title="CGHS Reimbursement Request Details Form";
		$jq('#medicalAdvance').attr('style','display:none');
	}else if(cghsRequestType=='cghssettlement') {
		$jq('#headTitle').html('CGHS Settlement Request Details Form')
		document.title="CGHS Settlement Request Details Form";
		$jq('#settlement').attr('style','display:block');
	}else if(cghsRequestType=='noncghsReimbursement') {
		$jq('#headTitle').html('Non CGHS Claims')
		document.title="Non CGHS Claims";
		$jq('#wardType').attr('style','display:none');
		$jq('#hospitalAddress').attr('style','display:block');
		$jq('#hospitalName').attr('style','display:none');
		$jq('#patientName').attr('style','display:none');
		$jq('#familyMember').attr('style','display:block');
		// $jq('#medicalAdvance').attr('style','display:none');
		// $jq('#detailsofpermision').attr('style','display:none');
		// $jq('#permission').attr('style','display:none');
		// $jq('#prescription').attr('style','display:none');
		
	}
}
function setCghsValues(type) {
	if(type=='CGHS REIMBURSEMENT') {
		$jq('#wardType').attr('style','display:block');
		$jq('#disease').attr('style','display:none');
		$jq('#nonCghsValues').attr('style','display:none');
		$jq('#medicalAdvance').attr('style','display:none');
	}else if(type=='NON CGHS REIMBURSEMENT') {
		$jq('#wardType').attr('style','display:none');
		$jq('#disease').attr('style','display:block');
		$jq('#nonCghsValues').attr('style','display:block');
		$jq('#cghsDetails').attr('style','display:none');
	}else if(type=='CGHS SETTLEMENT') {
		$jq('#wardType').attr('style','display:block');
		$jq('#disease').attr('style','display:none');
		$jq('#nonCghsValues').attr('style','display:none');
		$jq('#medicalAdvance').attr('style','display:block');
	}
}
function setEmergencyDetails() {
	$jq('#requestForm').attr('style','display:block');
	if($jq('#nonCghsEmergency').is(':checked')) {
		$jq('#headTitle').html('Non CGHS Emergency Request Form')
		// document.title="Non CGHS Emergency Request Form";
		$jq('#reason').attr('style','display:bolck');
		$jq('#cghsHospital').attr('style','display:none');
		$jq('#nonCghsHospital').attr('style','display:block');
	}else if($jq('#cghsEmergency').is(':checked')) {
		$jq('#headTitle').html('CGHS Emergency Request Details Form')
		// document.title="CGHS Emergency Request Form";
		$jq('#reason').attr('style','display:none');
		$jq('#cghsHospital').attr('style','display:block');
		$jq('#nonCghsHospital').attr('style','display:none');
	}
}
function manageEmergencyRequest() {
	var status=true;
	var errorMessage='';
	
	if($jq('#familyMemberId').val()==''){
		errorMessage+='Please Select Name of the Patient\n';
		if(status) {
			$jq('#familyMemberId').focus();
			status=false;
		}
	}
	//$jq('#cghscardref').val(familyMember[i].cghscard);
	if($jq('#disease').val()==''){
		errorMessage+='Please Enter  Name of the Disease \n';
		if(status) {
			$jq('#disease').focus();
			status=false;
		}
	}
	if($jq('#admissionDate').val()==''){
		errorMessage+='Please Enter Admission Date\n';
		if(status) {
			$jq('#admissionDate').focus();
			status=false;
		}
	}
	if($jq('#dischargeDate').val()==''){
		errorMessage+='Please Enter Discharge Date\n';
		if(status) {
			$jq('#dischargeDate').focus();
			status=false;
		}
	}
	if(compareDate($jq('#admissionDate').val(),$jq('#dischargeDate').val())) {
		errorMessage += "Invalid Dates \n";
		if (status) {
			status = false;
			$jq('#admissionDate').focus();
		}
	}

	if($jq('#hospitalAddress').val()=='' && $jq('#hospitalAddress').is(':visible')){
		errorMessage+='Please Enter Hospital Address\n';
		if(status) {
			$jq('#hospitalAddress').focus();
			status=false;
		}
	}	
	if($jq('#referralHospitalId').val()=='' && $jq('#referralHospitalId').is(':visible')){
		errorMessage+='Please Select Hospital Name\n';
		if(status) {
			$jq('#referralHospitalId').focus();
			status=false;
		}
	}
	if($jq('#amountClaimed').val()==''){
		errorMessage+='Please Enter Amount Claimed\n';
		if(status) {
			$jq('#amountClaimed').focus();
			status=false;
		}
	}
	if($jq('#amountInWords').val()==''){
		errorMessage+='Please Enter Amount Claimed in words\n';
		if(status) {
			$jq('#amountInWords').focus();
			status=false;
		}
	}if($jq('#emergencyDetails').val()==''){
		errorMessage+='Please Enter Nature/ Brief Details of Emergency\n';
		if(status) {
			$jq('#emergencyDetails').focus();
			status=false;
		}
	}	
	if($jq('#cghsEmergency').is(':checked')) {
		$jq('#type').val('emergency');
	}else {
		$jq('#type').val('nonCghsEmergency');
		
		if($jq('#reason1').val()==''){
			errorMessage+='Please Enter Reason for not taking treatment in Govt.Hospital/CGHS Dispensary\n';
			if(status) {
				$jq('#reason1').focus();
				status=false;
			}
		}
	}
	if($jq('#total').html()=='' || parseInt($jq('#total').html())==0) {
		errorMessage+='Could not process the request, total amount is zero.Please enter the breakup amount details';
		status = false;
	}
if(status){
	       /*
			 * mc =$jq('#files1').val(); dc = $jq('#files2').val(); cc =
			 * $jq('#files3').val();
			 */
	     $jq('#param').val('manage');
	   //  $jq('#cghscardref').val(cghscard);
	     /*
			 * $jq('#medicalBillsFile').val(mc);
			 * $jq('#dischargeSummeryFile').val(dc);
			 * $jq('#cghsCardFile').val(cc);
			 */
		 $jq.ajaxFileUpload(
  	    	{
	    	url:"cghsRequest.htm?" + $jq('#cghs').serialize(),
	    	secureuri:false,
	    	fileElementId :'files',
	    	success: function (data, status) {
  			$jq("#result").html($jq(data.body).html());
  			var requestType = $jq('#headTitle').html();
			var check=confirm(" "+requestType+" has been Successfully Sent...\n\nPlease click ok to 'Preview "+requestType+" 'Take print' from here\n\n");
			if(check){
			document.forms[0].requestId.value = $jq.trim(requestIDCGHs);
			
		   document.forms[0].param.value = "requestDetails";
			document.forms[0].action = "workflowmap.htm";
			document.forms[0].submit();	
			}
  				// when success
  			 clearEmergencyRequest();
	    	}, error: function (data, status, e) {
  				// when failure
	    		// alert('File Upload failed');
	    		status = false;
	    	}
	    	 });
		
	
}else {
	alert(errorMessage);
}
	/*
	 * if(status) { $jq('#param').val('manage'); $jq.post('cghsRequest.htm',
	 * $jq('#cghs').serialize(), function(html) { $jq("#result").html(html);
	 * clearEmergencyRequest(); }); }
	 */
}
function clearEmergencyRequest() {
	$jq('#familyMemberId').val('');
	// $jq('#disease').val('');
	$jq('#admissionDate').val('');
	$jq('#dischargeDate').val('');
	$jq('#referralHospitalId').val('');
	$jq('#hospitalAddress').val('');
	$jq('#amountClaimed').val('');
	$jq('#amountInWords').val('');
	$jq('#emergencyDetails').val('');
	$jq('#patientIllAddress').val('');
	$jq('#otherAddress').val('');
	$jq('#nonCghsReason').val('');
	
	$jq('#labCharges').val('');
	$jq('#medicinesCharges').val('');
	$jq('#miscellaneousCharges').val('');
	$jq('#roomRent').val('');
	$jq('#otCharges').val('');
	$jq('#otConsumables').val('');
	$jq('#anaesthesiaCharges').val('');
	$jq('#implantsCharges').val('');
	$jq('#artificialCharges').val('');
	$jq('#procedure').val('');
	$jq('#specialNurse').val('');
	$jq('#total').html('0');
	
	$jq('#reason1').val('');
	$jq('#files1').val('');
	$jq('#files2').val('');
	$jq('#files3').val('');
	$jq('#files4').val('');
	$jq('#files5').val('');
	$jq('#files6').val('');
	$jq('#files7').val('');
	$jq('#counter').val('500');
	$jq('#counter1').val('500');
	$jq('#counter2').val('500');	
}
function setCghsEmergency(type) {
	if(type=='NC') {
		$jq('#hospitalName').attr('style','display:none');
		$jq('#emergency').html('Non CGHS Emergency Request');
		$jq('#hospitalAddress').attr('style','display:block');
	}else if(type=='C') {
		$jq('#hospitalName').attr('style','display:block');
		$jq('#emergency').html('CGHS Emergency Request');
		$jq('#hospitalAddress').attr('style','display:none');
	}
}
function saveCdaAmont(type,param){
	var mainJSON = {};
	var status=true;
	var errorMessage='';
	if(param=='advManage'){
	if(type=='advance') {
		if($jq('.row').attr('checked')== true) {
		//for(var i=0;i<=$jq('#advance').find("tr").length;i++) {
			/*if($jq('#adv'+i).is(':checked')) {*/
			//if($jq('#adv').is(':checked')){	
			if($jq('#sanctionNoAdv').val()=='') {
					errorMessage+='Pleas enter Sanction no\n';
					$jq('#sanctionNoAdv').focus();
					status=false;
				}
			
				if($jq('#billNoAdv').val()=='') {
					errorMessage+='Please enter Bill no\n';
					$jq('#billNoAdv').focus();
					status=false;
				}if($jq('#repSigAdv').val()=='select'  || $jq('#repSigAdv').val()== null) {
					errorMessage+='Please select CFA Reg. Sig.\n';
					$jq('#repSigAdv').focus();
					status=false;
				}//if($jq('#accOfficerAdv'+i).val()=='select' ||  $jq('#repSigAdv'+i).val() == null) {
				if($jq('#accOfficerAdv').val()=='select' ||  $jq('#accOfficerAdv').val() == null) {
					errorMessage+='Please select Account Officer.\n';
					$jq('#accOfficerAdv').focus();
					status=false;
				}
			//}
		}
		
		for(var i=0,j=0;i<=$jq('#advance').find("tr").length;i++) {
			if($jq('#adv'+i).is(':checked')) {
				var subJSON={};
				subJSON['requestId']=$jq('#adv'+i).val();
				subJSON['Amount']=$jq('#cdaAmountAdv'+i).val();
				//subJSON['sanctionNo']=$jq('#sanctionNoAdv'+i).val();
				//subJSON['billNo']=$jq('#billNoAdv'+i).val();
				subJSON['sanctionNo']=$jq('#sanctionNoAdv').val();
				subJSON['billNo']=$jq('#billNoAdv').val();
				subJSON['repSig']=$jq('#repSigAdv').val();
				//subJSON['repSig']=$jq('#repSigAdv'+i).val();
				//subJSON['dvNo']=$jq('#dvNoAdv'+i).val();
				//subJSON['dvDate']=$jq('#dvDateAdv'+i).val();
				subJSON['accOfficer']=$jq('#accOfficerAdv').val();
				mainJSON[j]=subJSON;
				j++;
			}
		}
		
	}else if(type=='settlement') {
		
			if($jq('.rows').attr('checked')== true) {
			//if($jq('#sett').is(':checked')){	
			if($jq('#sanctionNoSett').val()=='') {
					errorMessage+='Please enter sanction no\n';
					$jq('#sanctionNoSett').focus();
					status=false;
				}
				if($jq('#billNoSett').val()=='') {
					errorMessage+=' Please enter bill no\n';
					$jq('#billNoSett').focus();
					status=false;
				}
				/*if($jq('#repSigSett'+i).val()=='select' ||  $jq('#repSigAdv'+i).val() == null) {
					errorMessage+='Please select Reg. Sig.\n';
					$jq('#repSigSett'+i).focus();
					status=false;
				}*/
				if($jq('#repSigSett').val()=='select' ||  $jq('#repSigAdv').val() == null) {
					errorMessage+='Please select Reg. Sig.\n';
					$jq('#repSigSett').focus();
					status=false;
				}
				
				if($jq('#accOfficerSett').val()=='select' ||  $jq('#accOfficerSett').val() == null) {
					errorMessage+='Please select Account  Officer.\n';
					$jq('#accOfficerSett').focus();
					status=false;
				}
			}
		
		for(var i=0,j=0;i<=$jq('#settlement').find("tr").length;i++) {
			if($jq('#sett'+i).is(':checked')) {
				var subJSON={};
				subJSON['requestId']=$jq('#sett'+i).val();
				subJSON['Amount']=$jq('#cdaAmountSett'+i).val();
				/*subJSON['sanctionNo']=$jq('#sanctionNoSett'+i).val();
				subJSON['billNo']=$jq('#billNoSett'+i).val();*/
			
				subJSON['sanctionNo']=$jq('#sanctionNoSett').val();
				subJSON['billNo']=$jq('#billNoSett').val();
				//	subJSON['repSig']=$jq('#repSigSett'+i).val();
				subJSON['repSig']=$jq('#repSigSett').val();
			/*	subJSON['dvNo']=$jq('#dvNoSett'+i).val();
				subJSON['dvDate']=$jq('#dvDateSett'+i).val();*/
				subJSON['accOfficer']=$jq('#accOfficerSett').val();
				mainJSON[j]=subJSON;
				j++;
			}
		}
		
		
	}else if(type=='reimbursement') {
		if($jq('.rowr').attr('checked')== true) {
			if($jq('#sanctionNoReim').val()=='') {
					errorMessage+='Please enter Sanction no\n';
					$jq('#sanctionNoReim').focus();
					status=false;
				}
				if($jq('#billNoReim').val()=='') {
					errorMessage+='Please enter Bill no\n';
					$jq('#billNoReim').focus();
					status=false;
				}
				if($jq('#AmountReim'+i).val()=='') {
					errorMessage+='Please enter Amount\n';
					$jq('#AmountReim'+i).focus();
					status=false;
				}
				
				if($jq('#repSigReim').val()=='select' ||  $jq('#repSigReim').val() == null) {
					errorMessage+='Please select Reg. Sig.\n';
					$jq('#repSigReim').focus();
					status=false;
				}
				
				if($jq('#accOfficerReim').val()=='select' ||  $jq('#accOfficerReim').val() == null) {
					errorMessage+='Please select Acc. Officer.\n';
					$jq('#accOfficerReim').focus();
					status=false;
				}
				
			}
		
		for(var i=0,j=0;i<=$jq('#reimbursement').find("tr").length;i++) {
			if($jq('#reim'+i).is(':checked')) {
				var subJSON={};
				subJSON['requestId']=$jq('#reim'+i).val();
				subJSON['Amount']=$jq('#AmountReim'+i).val();
				
				subJSON['sanctionNo']=$jq('#sanctionNoReim').val();
				subJSON['billNo']=$jq('#billNoReim').val();
				subJSON['repSig']=$jq('#repSigReim').val();
				
				subJSON['accOfficer']=$jq('#accOfficerReim').val();
				mainJSON[j]=subJSON;
				j++;
			}
		}
		
		
	}else if(type=='nonCghsReim') {
		if($jq('.rown').attr('checked')== true) {
		
			//if($jq('#nonCghs'+i).is(':checked')) {
		//	if($jq('#nonCghs').is(':checked')) {
				if($jq('#sanctionNoNon').val()=='') {
					errorMessage+='Please enter Sanction no\n';
					$jq('#sanctionNoNon').focus();
					status=false;
				}
				if($jq('#billNoNon').val()=='') {
					errorMessage+='please enter Bill no\n';
					$jq('#billNoNon').focus();
					status=false;
				}
				/*if($jq('#repSigNon').val()=='select' ||  $jq('#repSigNon').val() == null) {
					errorMessage+='Please select Reg. Sig.\n';
					$jq('#repSigNon').focus();
					status=false;
				}*/
				if($jq('#repSigNon').val()=='select' ||  $jq('#repSigNon').val() == null) {
					errorMessage+='Please select CFA Reg. Sig.\n';
					$jq('#repSigNon').focus();
					status=false;
				}
				
				if($jq('#accOfficerNon').val()=='select' ||  $jq('#accOfficerNon').val() == null) {
					errorMessage+='Please select Account Officer.\n';
					$jq('#accOfficerNon').focus();
					status=false;
				}
				
			}//}
		
		for(var i=0,j=0;i<=$jq('#nonCghsReim').find("tr").length;i++) {
			//if($jq('#nonCghs'+i).is(':checked')) {
			if($jq('#nonCghs'+i).is(':checked')) {
				var subJSON={};
				subJSON['requestId']=$jq('#nonCghs'+i).val();
				subJSON['Amount']=$jq('#AmountNon'+i).val();
				subJSON['sanctionNo']=$jq('#sanctionNoNon').val();
				subJSON['billNo']=$jq('#billNoNon').val();
				subJSON['repSig']=$jq('#repSigNon').val();
				//subJSON['repSig']=$jq('#repSigNon'+i).val();
				//subJSON['dvNo']=$jq('#dvNoNon'+i).val();
				//subJSON['dvDate']=$jq('#dvDateNon'+i).val();
				subJSON['accOfficer']=$jq('#accOfficerNon').val();
				mainJSON[j]=subJSON;
				j++;
			}
		}
		}
		
	else if(type=='emergency') {
		if($jq('.rowe').attr('checked')== true) {
		//for(var i=0;i<=$jq('#emergency').find("tr").length;i++) {
			//if($jq('#emer'+i).is(':checked')) {
			//if($jq('#emer').is(':checked')) {
				if($jq('#sanctionNoEmer').val()=='') {
					errorMessage+='Please enter Sanction no\n';
					$jq('#sanctionNoEmer').focus();
					status=false;
				}
				if($jq('#billNoEmer').val()=='') {
					errorMessage+='Please enter Bill no\n';
					$jq('#billNoEmer').focus();
					status=false;
				}
				/*if($jq('#repSigEmer'+i).val()=='select' ||  $jq('#repSigEmer'+i).val() == null) {
					errorMessage+='Please select Reg. Sig.\n';
					$jq('#repSigEmer'+i).focus();
					status=false;
				}
				*/
				if($jq('#repSigEmer').val()=='select' ||  $jq('#repSigEmer').val() == null) {
					errorMessage+='Please select CFA Reg Sig.\n';
					$jq('#repSigEmer').focus();
					status=false;
				}
				if($jq('#accOfficerEmer').val()=='select' ||  $jq('#accOfficerEmer').val() == null) {
					errorMessage+='Please select Account  Officer.\n';
					$jq('#accOfficerEmer').focus();
					status=false;
				}
			//}
		}
		for(var i=0,j=0;i<=$jq('#emergency').find("tr").length;i++) {
			if($jq('#emer'+i).is(':checked')) {
				var subJSON={};
				subJSON['requestId']=$jq('#emer'+i).val();
				subJSON['Amount']=$jq('#AmountEmer'+i).val();
				subJSON['sanctionNo']=$jq('#sanctionNoEmer').val();
				subJSON['billNo']=$jq('#billNoEmer').val();
				subJSON['repSig']=$jq('#repSigEmer').val();
				//subJSON['repSig']=$jq('#repSigEmer'+i).val();
				//subJSON['dvNo']=$jq('#dvNoEmer'+i).val();
			//	subJSON['dvDate']=$jq('#dvDateEmer'+i).val();
				subJSON['accOfficer']=$jq('#accOfficerEmer').val();
				mainJSON[j]=subJSON;
				j++;
			}
		}
		
	 }
	
	}
	/*if(JSON.stringify(mainJSON).length==2) {
		errorMessage+='please check at least one check box';
		status = false;
	
	}*/else {
		if(type=='advance') {
			for(var i=0;i<=$jq('#advance').find("tr").length;i++) {
				if($jq('#adv'+i).is(':checked')) {
					/*if($jq('#sanctionNoAdv'+i).val()=='') {
						errorMessage+='enter sanction no\n';
						$jq('#sanctionNoAdv'+i).focus();
						status=false;
					}
					if($jq('#billNoAdv'+i).val()=='') {
						errorMessage+='enter bill no\n';
						$jq('#billNoAdv'+i).focus();
						status=false;
					}if($jq('#repSigAdv'+i).val()=='select'  || $jq('#repSigAdv'+i).val()== null) {
						errorMessage+='Please select Reg. Sig.\n';
						$jq('#repSigAdv'+i).focus();
						status=false;
					}if($jq('#accOfficerAdv'+i).val()=='select' ||  $jq('#repSigAdv'+i).val() == null) {
						errorMessage+='Please select Acc. Officer.\n';
						$jq('#accOfficerAdv'+i).focus();
						status=false;
					}*/
					if($jq('#dvNoAdv').val()=='') {
						errorMessage+='please enter dv no\n';
						$jq('#dvNoAdv').focus();
						status=false;
					}
					if($jq('#dvDateAdv').val()=='') {
						errorMessage+='please enter  dv date\n';
						$jq('#dvDateAdv').focus();
						status=false;
					}
				}
			}
			
			for(var i=0,j=0;i<=$jq('#advance').find("tr").length;i++) {
				if($jq('#adv'+i).is(':checked')) {
					var subJSON={};
					subJSON['requestId']=$jq('#adv'+i).val();
					subJSON['Amount']=$jq('#cdaAmountAdv'+i).val();
					subJSON['sanctionNo']=$jq('#sanctionNoAdv'+i).val();
					subJSON['billNo']=$jq('#billNoAdv'+i).val();
					subJSON['repSig']=$jq('#repSigAdv'+i).val();
					//subJSON['dvNo']=$jq('#dvNoAdv'+i).val();
					//subJSON['dvDate']=$jq('#dvDateAdv'+i).val();
					subJSON['dvNo']=$jq('#dvNoAdv').val();
					subJSON['dvDate']=$jq('#dvDateAdv').val();
					subJSON['accOfficer']=$jq('#accOfficerAdv'+i).val();
					mainJSON[j]=subJSON;
					j++;
				}
			}
		}else if(type=='settlement') {						
				if($jq('.rows').attr('checked')== true) {
					if($jq('#dvNoSett').val()=='') {
						errorMessage+='please enter dv no\n';
						$jq('#dvNoSett').focus();
						status=false;
					}
					if($jq('#dvDateSett').val()=='') {
						errorMessage+='please enter dv date\n';
						$jq('#dvDateSett').focus();
						status=false;
					}
				}
			for(var i=0,j=0;i<=$jq('#settlement').find("tr").length;i++) {
				if($jq('#sett'+i).is(':checked')) {
					var subJSON={};
					subJSON['requestId']=$jq('#sett'+i).val();
					subJSON['Amount']=$jq('#cdaAmountSett'+i).val();
					
					subJSON['dvNo']=$jq('#dvNoSett').val();
					subJSON['dvDate']=$jq('#dvDateSett').val();
					subJSON['accOfficer']=$jq('#accOfficerSett'+i).val();
					mainJSON[j]=subJSON;
					j++;
				}
			}
			
		}else if(type=='reimbursement') {			
			if($jq('.rowr').attr('checked')== true) {					
					if($jq('#dvNoReim').val()=='') {
						errorMessage+='please enter dv no\n';
						$jq('#dvNoReim').focus();
						status=false;
					}
					if($jq('#dvDateReim').val()=='') {
						errorMessage+='please enter dv date\n';
						$jq('#dvDateReim').focus();
						status=false;
					}
				}
			
			for(var i=0,j=0;i<=$jq('#reimbursement').find("tr").length;i++) {
				if($jq('#reim'+i).is(':checked')) {
					var subJSON={};
					subJSON['requestId']=$jq('#reim'+i).val();
					subJSON['Amount']=$jq('#cdaAmountReim'+i).val();
					subJSON['dvNo']=$jq('#dvNoReim').val();
					subJSON['dvDate']=$jq('#dvDateReim').val();
					mainJSON[j]=subJSON;
					j++;
				}
			}
	}else if(type=='nonCghsReim') {
			//for(var i=0;i<=$jq('#nonCghsReim').find("tr").length;i++) {
				//if($jq('#nonCghs'+i).is(':checked')) {
			if($jq('.rown').attr('checked')== true) {
					/*if($jq('#sanctionNoNon'+i).val()=='') {
						errorMessage+='enter sanction no\n';
						$jq('#sanctionNoNon'+i).focus();
						status=false;
					}
					if($jq('#billNoNon'+i).val()=='') {
						errorMessage+='enter bill no\n';
						$jq('#billNoNon'+i).focus();
						status=false;
					}
					if($jq('#repSigNon'+i).val()=='select' ||  $jq('#repSigNon'+i).val() == null) {
						errorMessage+='Please select Reg. Sig.\n';
						$jq('#repSigNon'+i).focus();
						status=false;
					}if($jq('#accOfficerNon'+i).val()=='select' ||  $jq('#accOfficerNon'+i).val() == null) {
						errorMessage+='Please select Acc. Officer.\n';
						$jq('#accOfficerNon'+i).focus();
						status=false;
					}*/
					
					if($jq('#dvNoNon').val()=='') {
						errorMessage+='please enter dv no\n';
						$jq('#dvNoNon').focus();
						status=false;
					}
					if($jq('#dvDateNon').val()=='') {
						errorMessage+='please enter dv date\n';
						$jq('#dvDateNon').focus();
						status=false;
					}
				}
			//}
			for(var i=0,j=0;i<=$jq('#nonCghsReim').find("tr").length;i++) {
				if($jq('#nonCghs'+i).is(':checked')) {
					var subJSON={};
					subJSON['requestId']=$jq('#nonCghs'+i).val();
					subJSON['Amount']=$jq('#cdaAmountNon'+i).val();
					subJSON['sanctionNo']=$jq('#sanctionNoNon'+i).val();
					subJSON['billNo']=$jq('#billNoNon'+i).val();
					subJSON['repSig']=$jq('#repSigNon'+i).val();
					//subJSON['dvNo']=$jq('#dvNoNon'+i).val();
					//subJSON['dvDate']=$jq('#dvDateNon'+i).val();
					subJSON['dvNo']=$jq('#dvNoNon').val();
					subJSON['dvDate']=$jq('#dvDateNon').val();
					subJSON['accOfficer']=$jq('#accOfficerNon'+i).val();
					mainJSON[j]=subJSON;
					j++;
				}
			}
			
		}else if(type=='emergency') {
			//for(var i=0;i<=$jq('#emergency').find("tr").length;i++) {
			//	if($jq('#emer'+i).is(':checked')) {
			if($jq('.rowe').attr('checked')== true) {
					/*if($jq('#sanctionNoEmer'+i).val()=='') {
						errorMessage+='enter sanction no\n';
						$jq('#sanctionNoEmer'+i).focus();
						status=false;
					}
					if($jq('#billNoEmer'+i).val()=='') {
						errorMessage+='enter bill no\n';
						$jq('#billNoEmer'+i).focus();
						status=false;
					}
					if($jq('#repSigEmer'+i).val()=='select' ||  $jq('#repSigEmer'+i).val() == null) {
						errorMessage+='Please select Reg. Sig.\n';
						$jq('#repSigEmer'+i).focus();
						status=false;
					}if($jq('#accOfficerEmer'+i).val()=='select' ||  $jq('#accOfficerEmer'+i).val() == null) {
						errorMessage+='Please select Acc. Officer.\n';
						$jq('#accOfficerEmer'+i).focus();
						status=false;
					}*/
					if($jq('#dvNoEmer').val()=='') {
						errorMessage+='please enter dv no\n';
						$jq('#dvNoEmer').focus();
						status=false;
					}
					if($jq('#dvDateEmer').val()=='') {
						errorMessage+='please enter dv date\n';
						$jq('#dvDateEmer').focus();
						status=false;
					}
				//}
			}
			for(var i=0,j=0;i<=$jq('#emergency').find("tr").length;i++) {
				if($jq('#emer'+i).is(':checked')) {
					var subJSON={};
					subJSON['requestId']=$jq('#emer'+i).val();
					subJSON['Amount']=$jq('#cdaAmountEmer'+i).val();
					subJSON['sanctionNo']=$jq('#sanctionNoEmer'+i).val();
					subJSON['billNo']=$jq('#billNoEmer'+i).val();
					subJSON['repSig']=$jq('#repSigEmer'+i).val();
					//subJSON['dvNo']=$jq('#dvNoEmer'+i).val();
					//subJSON['dvDate']=$jq('#dvDateEmer'+i).val();
					subJSON['dvNo']=$jq('#dvNoEmer').val();
					subJSON['dvDate']=$jq('#dvDateEmer').val();
					subJSON['accOfficer']=$jq('#accOfficerEmer'+i).val();
					mainJSON[j]=subJSON;
					j++;
				}
			}
			
		
			}}
		if(JSON.stringify(mainJSON).length==2) {
			errorMessage+='please check at least one check box';
			status = false;
		}
		
	
	
	if(status) {
		
			$jq.post( 'cghsRequest.htm?param='+param+'&type='+type+'&jsonValues='+JSON.stringify(mainJSON), function(html) { 
				
			/*$jq.ajax( { type : "POST",
			url : 'cghsRequest.htm?param='+param+'&type='+type+'&jsonValues='+JSON.stringify(mainJSON), function(html){
			cache : false,
			success : function(html, status) */			
			if(param=='cdaManage'){
				if(type=='advance'){
					//$jq("#cdaadvance").html(html);
					$jq("#cdaadvanceList").html(html);
					//cdaadvanceList
				}
				else if(type=='settlement'){
					//$jq("#cdasettlement").html(html);
					$jq("#cdasettlementList").html(html);
					//cdasettlementList
				}
				else if(type=='reimbursement'){
					//$jq("#cdareimbursement").html(html);
					$jq("#cdareimbursementList").html(html);
					//cdareimbursementList
				}
				else if(type=='nonCghsReim'){
					$jq("#cdanoncghsreimList").html(html);
					//$jq("#cdanoncghsreim").html(html);
					//cdanoncghsreimList
				}
				else if(type=='emergency'){
					$jq("#cdaemergencyList").html(html);
					//$jq("#cdaemergency").html(html);
					//cdaemergencyList
				}
				
			}else{
			if(type=='advance') {
				$jq("#advanceList").html(html);cdaadvanceList
			}else if(type=='settlement') {
				$jq("#settlementList").html(html);
			}else if(type=='reimbursement') {
				$jq("#reimbursementList").html(html);
			}else if(type=='nonCghsReim') {
				$jq("#NonCghsReimList").html(html);
			}else if(type=='emergency') {
				$jq("#emergencyList").html(html);
			}
		}
		});
	
	}else {
		alert(errorMessage);
	}clearcghsreimburse(type,param)
}


function getInitialReportCdaCghs(requestType){
	var billNo='';
	var errorMessage='';
	var status=true;
	var i=0;
	
	if(requestType=='cghsCdaSett'){
		billNo=$jq('#billNoSett').val();
		if(billNo==0){
			errorMessage="Please Enter BillNo"
				status=false;
		}
	}
	
	
	if(requestType=='cghsCdaAdv'){
		billNo=$jq('#billNoAdv').val();
		if(billNo==0){
			errorMessage="Please Enter BillNo"
				status=false;
		}
	}
	
	if(requestType=='cghsCdaReimb'){
		billNo=$jq('#billNoReim').val();
		if(billNo==0){
			errorMessage="Please Enter BillNo"
				status=false;
		}
	}
	
	if(requestType=='emergency'){
		billNo=$jq('#billNoEmer').val();
		if(billNo==0){
			errorMessage="Please Enter BillNo"
				status=false;
		}
	}
	
	if(requestType=='nonCghsReim'){ 
		billNo=$jq('#billNoNon').val();
		if(billNo==0){
			errorMessage="Please Enter BillNo"
				status=false;
		}
	}
	if(status){
		
			window
			.open(
					"./report.htm?param=initialCdacghs&returnValue=report&billNo="+billNo+'&type='+requestType,
					'preview',
					'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
	
		}else{
			alert(errorMessage);
			//$jq('#billNoSett').val('');
			billNo==0;
		}		
}
/*function clearcghsreimburse(type, param){
	if(param=='advManage')	{
		if(type=='advance'){
		$jq('#sanctionNoAdv').val('');
		$jq('#billNoAdv').val('');
		}
		else if(type=='settlement') {
			$jq('sanctionNoSett').val('');
			$jq('#billNoSett').val('');
		}
		else if(type=='reimbursement') {
			$jq('#sanctionNoReim').val('');
			
			$jq('#billNoReim').val('');
		}
		else if(type=='nonCghsReim') {
			$jq('#sanctionNoNon').val('');
			$jq('#billNoNon').val('');
			
			
		}
		else if(type=='emergency') {			
			$jq('#sanctionNoEmer').val('');
			$jq('#billNoEmer').val('');
			
			
		}
		
	}
 else(param=='cdaManage'){
		if(type=='advance'){
			$jq('#dvNoAdv').val('');
			$jq('#dvDateAdv').val('');
		}
		else if(type=='settlement') {
			$jq('#dvNoSett').val('');
			$jq('#dvDateSett').val('');
		}
		else if(type=='reimbursement') {
			$jq('#dvNoReim').val('');
			$jq('#dvDateReim').val('');
		}
		else if(type=='nonCghsReim') {
			
			$jq('#dvNoNon').val('');
			$jq('#dvDateNon').val('');
		}
		else if(type=='emergency') {
			$jq('#dvNoEmer').val('');
			$jq('#dvDateEmer').val('');
			
		}
	}
}*/
function clearcghsreimburse(type, param){
	if(param=='advManage'){
		if(type=='advance'){
		$jq('#sanctionNoAdv').val('');
		$jq('#billNoAdv').val('');
		$jq('#accOfficerAdv').val('');
		$jq('#repSigAdv').val('');
		
		}
		else if(type=='settlement') {
			$jq('#sanctionNoSett').val('');
			$jq('#billNoSett').val('');
			$jq('#accOfficerSett').val('');
			$jq('#repSigSett').val('');
			
		}
		else if(type=='reimbursement') {
			$jq('#sanctionNoReim').val('');
			$jq('#billNoReim').val('');
			$jq('#accOfficerReim').val('');
			$jq('#repSigReim').val('');
			
		}
		else if(type=='nonCghsReim') {
			$jq('#sanctionNoNon').val('');
			$jq('#billNoNon').val('');
			$jq('#accOfficerNon').val('');
			$jq('#repSigNon').val('');
			
			
		}
		else if(type=='emergency') {			
			$jq('#sanctionNoEmer').val('');
			$jq('#billNoEmer').val('');
			$jq('#accOfficerEmer').val('');
			$jq('#repSigEmer').val('');
			
			
		}}
	else {
		if(type=='advance'){
			$jq('#dvNoAdv').val('');
			$jq('#dvDateAdv').val('');
		}
		else if(type=='settlement') {
			$jq('#dvNoSett').val('');
			$jq('#dvDateSett').val('');
		}
		else if(type=='reimbursement') {
			$jq('#dvNoReim').val('');
			$jq('#dvDateReim').val('');
		}
		else if(type=='nonCghsReim') {
			
			$jq('#dvNoNon').val('');
			$jq('#dvDateNon').val('');
		}
		else if(type=='emergency') {
			$jq('#dvNoEmer').val('');
			$jq('#dvDateEmer').val('');
			
		}
		
	}
}
function getReport(requestId,type) {
	var status=true;
	var errorMessage='';
	if(type=='reimbursement') {
		$jq("#reimbursementList tr:not(:first)").each(function() {	
				if($jq(this).find("td").eq(0).find(":input:checkbox").is(':checked')){
					requestId+=$jq(this).find("td").eq(0).find(":input:checkbox").attr('value')+","
				}		
			});
	}
	if(requestId=='') {
		errorMessage+='please check any one check box';
		status = false;
	}
	if(status) {
		window.open('./report.htm?param=report&type='+type+'&requestID='+requestId,'CGHS','fullscreen=no,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no')

	}else {
		alert(errorMessage);
	}
}
function getInitialReport(requestId,type) {
	window.open('./report.htm?param=initial&type='+type+'&requestID='+requestId,'CGHS','fullscreen=no,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no')
	//window.open('./report.htm?param=initial&type='+type+'&requestID='43618','CGHS','fullscreen=no,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no')
}
function setSelectedValue(id,repSigValue) {
	document.getElementById(id).value=repSigValue;
}
function checkAdmissibleValue(id,oldValue) {
	var admissibleTotalAmount = 0;
	
	/*
	 * Disabled due to finance amount may be greater than requested amount or
	 * equal to zero on 30-May-2012
	 * 
	 * if(!(parseInt($jq('#'+id).val()) >0 && parseInt($jq('#'+id).val()) <=
	 * parseInt(oldValue))) { alert('Amount should not exceed/empty '+oldValue);
	 * $jq('#'+id).val(oldValue); $jq('#'+id).focus(); }
	 */
	if($jq('#admissibleLabCharges').is(':visible') && $jq('#admissibleLabCharges').val()!='') {
		admissibleTotalAmount = parseInt(admissibleTotalAmount) +  parseInt($jq('#admissibleLabCharges').val());
	}
	if($jq('#admissibleMedicinesCharges').is(':visible') && $jq('#admissibleMedicinesCharges').val()!='') {
		admissibleTotalAmount = parseInt(admissibleTotalAmount) +  parseInt($jq('#admissibleMedicinesCharges').val());
	}
	if($jq('#admissibleRoomRent').is(':visible') && $jq('#admissibleRoomRent').val()!='') {
		admissibleTotalAmount = parseInt(admissibleTotalAmount) +  parseInt($jq('#admissibleRoomRent').val());
	}
	if($jq('#admissibleOtCharges').is(':visible') && $jq('#admissibleOtCharges').val()!='') {
		admissibleTotalAmount = parseInt(admissibleTotalAmount) +  parseInt($jq('#admissibleOtCharges').val());
	}
	if($jq('#admissibleOtConsumables').is(':visible') && $jq('#admissibleOtConsumables').val()!='') {
		admissibleTotalAmount = parseInt(admissibleTotalAmount) +  parseInt($jq('#admissibleOtConsumables').val());
	}
	if($jq('#admissibleAnaesthesiaCharges').is(':visible') && $jq('#admissibleAnaesthesiaCharges').val()!='') {
		admissibleTotalAmount = parseInt(admissibleTotalAmount) +  parseInt($jq('#admissibleAnaesthesiaCharges').val());
	}
	if($jq('#admissibleImplantsCharges').is(':visible') && $jq('#admissibleImplantsCharges').val()!='') {
		admissibleTotalAmount = parseInt(admissibleTotalAmount) +  parseInt($jq('#admissibleImplantsCharges').val());
	}
	if($jq('#admissibleArtificialCharges').is(':visible') && $jq('#admissibleArtificialCharges').val()!='') {
		admissibleTotalAmount = parseInt(admissibleTotalAmount) +  parseInt($jq('#admissibleArtificialCharges').val());
	}
	if($jq('#admissibleProcedure').is(':visible') && $jq('#admissibleProcedure').val()!='') {
		admissibleTotalAmount = parseInt(admissibleTotalAmount) +  parseInt($jq('#admissibleProcedure').val());
	}
	if($jq('#admissibleSpecialNurse').is(':visible') && $jq('#admissibleSpecialNurse').val()!='') {
		admissibleTotalAmount = parseInt(admissibleTotalAmount) +  parseInt($jq('#admissibleSpecialNurse').val());
	}
	if($jq('#admissibleMiscellaneousCharges').is(':visible') && $jq('#admissibleMiscellaneousCharges').val()!='') {
		admissibleTotalAmount = parseInt(admissibleTotalAmount) +  parseInt($jq('#admissibleMiscellaneousCharges').val());
	}
	if($jq('#admissibleConsultationFees').is(':visible') && $jq('#admissibleConsultationFees').val()!='') {
		admissibleTotalAmount = parseInt(admissibleTotalAmount) +  parseInt($jq('#admissibleConsultationFees').val());
	}
	if($jq('#admissibleDisposableCharges').is(':visible') && $jq('#admissibleDisposableCharges').val()!='') {
		admissibleTotalAmount = parseInt(admissibleTotalAmount) +  parseInt($jq('#admissibleDisposableCharges').val());
	}
	if($jq('#admissibleSpecialDevices').is(':visible') && $jq('#admissibleSpecialDevices').val()!='') {
		admissibleTotalAmount = parseInt(admissibleTotalAmount) +  parseInt($jq('#admissibleSpecialDevices').val());
	}
	if(cghsAdvance!='') {
		admissibleTotalAmount = parseInt(admissibleTotalAmount) - parseInt(cghsAdvance);
	}
	$jq('#financeIssue').text("Rs."+admissibleTotalAmount+"/-");
	$jq('#admissibleTotalAmount').val(admissibleTotalAmount);
}
function getFamilyMembers() {
	$jq.ajax( {
		type : "POST",
		url : "cghsRequest.htm?param=Dependent&offlineSFID="+$jq('#offlineSFID').val().toUpperCase(),
		cache : false,
		success : function(html, status) {			
			$jq("#result").html(html);
			$jq('#familyMemberId').find('option').remove().end().append($jq("<option></option>").attr("value",'').text('Select'));
			if(familyMembersListJson!=null && familyMembersListJson!='') {
				for(m=0; m < familyMembersListJson.length; m++){	
					$jq('#familyMemberId').append($jq("<option></option>").attr("value",familyMembersListJson[m].id).text(familyMembersListJson[m].name));
				}
			}
		}
	});
}
function setValues(type) {
	if(type!='') {
		$jq('.headTitle').html('Offline CGHS Request Form');
		$jq('#offlineSFIDDiv').attr('style','display:block');
	}
}
function compareDatesForCghs(date1,dateformat1,date2,dateformat2) {
	var d1=getDateFromFormatForCghs(date1,dateformat1);
	var d2=getDateFromFormatForCghs(date2,dateformat2);
	if (d1==0 || d2==0) {
		return -1;
		}
	else if (d1 > d2) {
		return 1;
		}
	return 0;
	}

function getDateFromFormatForCghs(val,format) {
	val=val+"";
	format=format+"";
	var i_val=0;
	var i_format=0;
	var c="";
	var token="";
	var token2="";
	var x,y;
	var now=new Date();
	var year=now.getYear();
	var month=now.getMonth()+1;
	var date=1;
	var hh=now.getHours();
	var mm=now.getMinutes();
	var ss=now.getSeconds();
	var ampm="";
	
	while (i_format < format.length) {
		// Get next token from format string
		c=format.charAt(i_format);
		token="";
		while ((format.charAt(i_format)==c) && (i_format < format.length)) {
			token += format.charAt(i_format++);
			}
		// Extract contents of value based on format token
		if (token=="yyyy" || token=="yy" || token=="y") {
			if (token=="yyyy") { x=4;y=4; }
			if (token=="yy")   { x=2;y=2; }
			if (token=="y")    { x=2;y=4; }
			year=__getInt(val,i_val,x,y);
			if (year==null) { return 0; }
			i_val += year.length;
			if (year.length==2) {
				if (year > 70) { year=1900+(year-0); }
				else { year=2000+(year-0); }
				}
			}
		else if (token=="MMM"||token=="NNN"){
			month=0;
			for (var i=0; i<MONTH_NAMES.length; i++) {
				var month_name=MONTH_NAMES[i];
				if (val.substring(i_val,i_val+month_name.length).toLowerCase()==month_name.toLowerCase()) {
					if (token=="MMM"||(token=="NNN"&&i>11)) {
						month=i+1;
						if (month>12) { month -= 12; }
						i_val += month_name.length;
						break;
						}
					}
				}
			if ((month < 1)||(month>12)){return 0;}
			}
		else if (token=="EE"||token=="E"){
			for (var i=0; i<DAY_NAMES.length; i++) {
				var day_name=DAY_NAMES[i];
				if (val.substring(i_val,i_val+day_name.length).toLowerCase()==day_name.toLowerCase()) {
					i_val += day_name.length;
					break;
					}
				}
			}
		else if (token=="MM"||token=="M") {
			month=__getInt(val,i_val,token.length,2);
			if(month==null||(month<1)||(month>12)){return 0;}
			i_val+=month.length;}
		else if (token=="dd"||token=="d") {
			date=__getInt(val,i_val,token.length,2);
			if(date==null||(date<1)||(date>31)){return 0;}
			i_val+=date.length;}
		else if (token=="hh"||token=="h") {
			hh=__getInt(val,i_val,token.length,2);
			if(hh==null||(hh<1)||(hh>12)){return 0;}
			i_val+=hh.length;}
		else if (token=="HH"||token=="H") {
			hh=__getInt(val,i_val,token.length,2);
			if(hh==null||(hh<0)||(hh>23)){return 0;}
			i_val+=hh.length;}
		else if (token=="KK"||token=="K") {
			hh=__getInt(val,i_val,token.length,2);
			if(hh==null||(hh<0)||(hh>11)){return 0;}
			i_val+=hh.length;}
		else if (token=="kk"||token=="k") {
			hh=__getInt(val,i_val,token.length,2);
			if(hh==null||(hh<1)||(hh>24)){return 0;}
			i_val+=hh.length;hh--;}
		else if (token=="mm"||token=="m") {
			mm=__getInt(val,i_val,token.length,2);
			if(mm==null||(mm<0)||(mm>59)){return 0;}
			i_val+=mm.length;}
		else if (token=="ss"||token=="s") {
			ss=__getInt(val,i_val,token.length,2);
			if(ss==null||(ss<0)||(ss>59)){return 0;}
			i_val+=ss.length;}
		else if (token=="a") {
			if (val.substring(i_val,i_val+2).toLowerCase()=="am") {ampm="AM";}
			else if (val.substring(i_val,i_val+2).toLowerCase()=="pm") {ampm="PM";}
			else {return 0;}
			i_val+=2;}
		else {
			if (val.substring(i_val,i_val+token.length)!=token) {return 0;}
			else {i_val+=token.length;}
			}
		}
	// If there are any trailing characters left in the value, it doesn't match
	if (i_val != val.length) { return 0; }
	// Is date valid for month?
	if (month==2) {
		// Check for leap year
		if ( ( (year%4==0)&&(year%100 != 0) ) || (year%400==0) ) { // leap year
			if (date > 29){ return 0; }
			}
		else { if (date > 28) { return 0; } }
		}
	if ((month==4)||(month==6)||(month==9)||(month==11)) {
		if (date > 30) { return 0; }
		}
	// Correct hours value
	if (hh<12 && ampm=="PM") { hh=hh-0+12; }
	else if (hh>11 && ampm=="AM") { hh-=12; }
	var newdate=new Date(year,month-1,date,hh,mm,ss);
	return newdate.getTime();
	}
function __getInt(str,i,minlength,maxlength) {
	for (var x=maxlength; x>=minlength; x--) {
		var token=str.substring(i,i+x);
		if (token.length < minlength) { return null; }
		if (__isInteger(token)) { return token; }
		}
	return null;
	}

function __isInteger(val) {
	var digits="1234567890";
	for (var i=0; i < val.length; i++) {
		if (digits.indexOf(val.charAt(i))==-1) { return false; }
		}
	return true;
	}
//
function enableOption(){
	if($jq('#cghsRequestTypeId').val() == 2){
	  $jq('#showsettlement').show();
      }else if(($jq('#cghsRequestTypeId').val() == 1) || ($jq('#cghsRequestTypeId').val() == 3)){
      
    	  $jq('#showsettlement').hide();
    	  $jq('#button1').attr("style","display:none");
    	  $jq('#button').show();
      }
}
function changeButton(){
	if(($jq('input:radio[name=settlement]:checked').val() == "advance") || ($jq('input:radio[name=settlement]:checked').val() == "reimbursement")){
		
	$jq('#button').attr("style","display:none");
	$jq('#button1').show();		
	
	}	
	else if(($jq('input:radio[name=settlement]:checked').val() == "permission")){
		$jq('#button1').attr("style","display:none");
		$jq('#button').show();		
	
	}
	
}
function manageInvistigationRequest() {
	var status=true;
	var errorMessage='';
	if($jq('#familyMemberId').val()=='')
	{
		$jq('#familyMemberId').focus();
		status=false;
		errorMessage+='Please Select family member\n';
	}
	/*
	 * if($jq('#disease').val()=='') { if(status) { $jq('#disease').focus();
	 * status=false; } errorMessage+='Please Enter disease\n'; }
	 */
	if($jq('#prescriptionDate').val()=='')
	{
		if(status) {
		$jq('#prescriptionDate').focus();
		status=false;
		}
		errorMessage+='Please Select prescription date\n';
	}
	if($jq('#cghsRequestTypeId').val()=='')
	{
		if(status) {
		$jq('#cghsRequestTypeId').focus();
		status=false;
		}
		errorMessage+='Please Select request type\n';
	}
	/*
	 * if($jq('#referredDoctor').val()=='') { if(status) {
	 * $jq('#referredDoctor').focus(); status=false; } errorMessage+='Please
	 * Enter referred doctor name\n'; }
	 */
	if($jq('#referralHospitalId').val()=='')
	{	errorMessage+='Please Select referral hospital\n';
		if(status) {
		$jq('#referralHospitalId').focus();
		status=false;
		}
	}
/*
 * if($jq('#referenceSpecialist').val()=='') { if(status) {
 * $jq('#referenceSpecialist').focus(); status=false; } errorMessage+='Please
 * Enter reference specialist name\n'; }
 */
	if($jq('#files').val()!='' && !checkFileExtention($jq('#files').val().split('.')[$jq('#files').val().split('.').length-1])) {
		errorMessage+='Upload only .docx,.doc,.txt,.pdf\n';
	}
	if(status){
	
		/*if($jq('#files').val()!='') {
			$jq.ajaxFileUpload(
		  		{
			   	url:"file.htm?param=upload&type=cghs",
			   	secureuri:false,
			   	fileElementId :'files',
			   	success: function (data, status) {
		  			// success
		  		}, error: function (data, status, e) {
			    		alert('File Upload failed');
			    	}
			 });	
		}*/
		  setlement = $jq('input:radio[name=settlement]:checked').val();
		  
		// $jq('input:radio[name=amendmentType]:checked').val()=='cancel'
		if(($jq('input:radio[name=settlement]:checked').val() == "advance")||($jq('input:radio[name=settlement]:checked').val() =="reimbursement")){
			document.forms[0].param.value = "manageinvestigation";
			document.forms[0].type.value = "treatment";
			// document.forms[0].prescriptionFile.value = $jq('#files').val();
			document.forms[0].action = "cghsRequest.htm?";
			document.forms[0].submit();
			/*
			 * $jq('#param').val('manageinvestigation');
			 * $jq('#type').val('treatment'); $jq.post('cghsRequest.htm',
			 * $jq('#cghs').serialize(), function(html) {
			 * //$jq("#result").html(html); alert("aaaa"); });
			 */
		}else {
			$jq('#param').val('manageinvestigation');
			$jq('#type').val('treatment');
		    $jq.post('cghsRequest.htm', $jq('#cghs').serialize(), function(html) {
		    $jq("#result").html(html);
		    });
		}
		
		alert("Please Wait You Will Be Rediretced To Next Page")
		 
		 clearTreatmentRequest();
		}else {
			alert(errorMessage);
		}
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


function clearCheckBoxn(){
	$jq('#tabs').find('thead').each(function(){
		   $jq(this).find('th').eq(0).find('input').removeAttr('checked');	
		  checkBoxCheckAll($jq(this).find('th').find('input:checkbox').attr('name'),'rown')
		
		});
	
}
function clearCheckBoxr(){
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





//
var MONTH_NAMES=new Array('January','February','March','April','May','June','July','August','September','October','November','December','Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec');
var DAY_NAMES=new Array('Sunday','Monday','Tuesday','Wednesday','Thursday','Friday','Saturday','Sun','Mon','Tue','Wed','Thu','Fri','Sat');
