

//added by bkr 19/07/2016

function submitHealthSubscriptionDetails(){
	var status=true;
	var errorMessage="";
	
	var sfId=$jq('#sfId').val();
	var hicName=$jq('#hicName').val();
	var hicNo=$jq('#hicNo').val();
	var subScriptiomAmt=$jq('#subScriptiomAmt').val();
	var effctiveDate=$jq('#effctiveDate').val();
	if(sfId==="" || sfId===null){
		errorMessage += "Please Enter  SFID.\n";
		if(status){
			$jq('#sfId').focus();
			status=false;
		}
	}
	if(hicName==="" || hicName===null){
		errorMessage += "Please Enter Health Insurance Company Name.\n";
		if(status){
			$jq('#hicName').focus();
			status=false;
		}
	}
	if(hicNo==="" || hicNo===null){
		errorMessage += "Please Enter Health Insurance Company Number.\n";
		if(status){
			$jq('#hicNo').focus();
			status=false;
		}
	}
	if(subScriptiomAmt==="" || subScriptiomAmt===null){
		errorMessage += "Please Enter Subscription Amount.\n";
		if(status){
			$jq('#subScriptiomAmt').focus();
			status=false;
		}
	}
	if(effctiveDate==="" || effctiveDate===null){
		errorMessage += "Please Enter Effctive Date.\n";
		if(status){
			$jq('#effctiveDate').focus();
			status=false;
		}
	}
	
	if(status){
	
		var requestDetails={
				"sfId" : sfId,
				"hicName" : hicName,
				"hicNo" : hicNo,
				"subScriptiomAmt" : subScriptiomAmt,
				"effctiveDate" : effctiveDate,
				"param" : "HealthSubscriptionSubmit"
		};
		
		
		$jq.ajax({
			type : "POST",
			url : "healthInsuranceEnrollment.htm",
			data : requestDetails,
			cache : false,
			success : function(html){
				alert("Saved Sucessfully..");
				document.forms[0].param.value = "SubscriptionDetailsHome";
				document.forms[0].action = "healthInsuranceEnrollment.htm";
				document.forms[0].submit();
			}
			
			
		});
		
	}else{
		alert(errorMessage);
	}

	
	
}

function clearsubmitHealthSubscriptionDetails(){
	$jq('#sfId').val('');
	$jq('#hicName').val('');
	$jq('#hicNo').val('');
	$jq('#subScriptiomAmt').val('');
	$jq('#effctiveDate').val('');
}

function applyInsuranceAmt(sfId){
	$jq('#sfId').val(sfId);
}

function getHealthEmployeeDetails(){
	var status=true;
	var nameOrSfid= $jq("#nameOrSfid").val();
	
	if(nameOrSfid===null || nameOrSfid===""){
		alert("Please Enter Name Or SFID");
		$jq("#nameOrSfid").focus();
		status=false;
		
		
	}

	requestDetails = {
			"param" :"healthInsuranceEmpSearch",
			"nameOrSfid" : nameOrSfid
	}
	if(status){
	$jq.ajax( {
		type : "POST",
		url : "healthInsuranceEnrollment.htm",
		data : requestDetails,
		cache : false,
		success : function(html) {
			
			document.forms[0].param.value = "SubscriptionDetailsHome";
			document.forms[0].action = "healthInsuranceEnrollment.htm";
			document.forms[0].submit();
			}
		});
	}
}






//added by bhaskar and suresh


function clearCheckBox(){
	$jq('#tabs').find('thead').each(function(){
		   $jq(this).find('th').eq(0).find('input').removeAttr('checked');	
		  checkBoxCheckAll($jq(this).find('th').find('input:checkbox').attr('name'),'rowh')
		
		});
}



function clearHealthInsuranceDetails(){
	$jq('#fromDate').val('');
} 

function saveHealthEnrollmentDetails(){
	var status=true;
	var errorMessages="";
	var mainJSON = {};
	if(status  && errorMessages == ''){
		
		for(var i=0,j=0;i<=$jq('#health').find("tr").length;i++) {
			if($jq('#hea'+i).is(':checked')) {
				var subJSON={};
				subJSON['sfid']=$jq('#hea'+i).val();
				subJSON['fullName']=document.getElementById("healthName"+i).innerHTML;
				subJSON['Desg']=document.getElementById("healthdesg"+i).innerHTML;
				subJSON['Doj']=document.getElementById("healthjoin"+i).innerHTML;
				subJSON['status']=document.getElementById("healthstatus"+i).innerHTML;
				subJSON['EnrollmentDate']=$jq('#fromDate').val();
				mainJSON[j]=subJSON;
				j++;
			}
		}
		
		requestDetails = {
				"param" :"HealthEnrollmentSave",
				"jsonValue":JSON.stringify(mainJSON)
		}
		
		$jq.ajax( {
			type : "POST",
			url : "healthInsuranceEnrollment.htm",
			data : requestDetails,
			cache : false,
			success : function(html) {
				$jq('#result').html(html);
				if ($jq('.success').is(':visible')) {
					var check=confirm("Health Insurance Details Saved Successfully");
					if(check){
						document.forms[0].action = "healthInsuranceEnrollment.htm";
						document.forms[0].submit();
					}else
						{
						alert(errorMessages);
						clearHealthInsuranceDetails(type,param);
					}
					}
				}
			});
	}
}


function getEmployeeDetails() {
	var requestDetails = {
		"changeSfid" : $jq("#changeSfid").val(),
		"param" : "changeEmployee"
	};
	$jq.ajax( {
		type : "POST",
		url : "master.htm",
		data : requestDetails,
		cache : true,
		success : function(html) {
		$jq('#result').html(html);
		if(sfidResult=='employeeexists') {
			if (type == 'address') {
				getAddress();
			} else if (type == 'family') {
				familyDetails();
			} else if (type == 'qualification') {
				viewQualification();
			} else if (type == 'training') {
				getTraining();
			} else if (type == 'passport') {
				getPassport();
			} else if (type == 'experience') {
				getEmpExperienceDetails();
			} else if (type == 'nominee') {
				NomineeDetails();
			}else if (type == 'preOrganisation') {
				PreOrgnDetails();
			} else if (type == 'retirement') {
				getRetirementDetails();
			}else if (type == 'quarter') {
				getQuarterDetails();
			}else if (type == 'award') {
				getAwardDetails();
			}
		}
		}
	});
}

	


