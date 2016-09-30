
function checkBoxCheckX(){//For all employees / For all employees except MD
	$jq('#selectall').attr("checked", true);
}

function checkBoxCheckY(){
	var k=0;
	$jq("#dataList tr:not(:first)").each(function() {
		if($jq(this).find("td").eq(0).find(":input:checkbox").is(':checked')){
			if($jq(this).find("td").eq(3).text().toUpperCase() === 'DIRECTOR')
			$jq("#"+$jq(this).find("td").eq(0).find(":input:checkbox")[0].id).attr("checked", false);
			k++;
		}		
	});
}

function hideShowRadioButtons(){
	$jq('#selectall').attr("checked", false);
	checkBoxCheckAll('selectall','row');
	$jq('#allowanceForALL').attr("checked", false);
    $jq('#allowanceForExceptMD').attr("checked", false);
	$jq('#allowanceForReq').attr("checked", false);
	if($jq('#deptId').val() == 'select'){
		
		document.getElementById("allowanceForALL").disabled = true;
		document.getElementById("allowanceForExceptMD").disabled = true;
		document.getElementById("allowanceForReq").disabled = true;
		$jq('#hideEmployeesDetails').show();
		$jq('#effectiveToDateAdv1').hide();
		$jq('#w-input-search').val('');
		$jq('#searchEmp').hide();
	}
	if($jq('#deptId').val() === "1" || $jq('#deptId').val() === "2")
	 {
		document.getElementById("allowanceForALL").disabled = false;
		document.getElementById("allowanceForExceptMD").disabled = false;
		document.getElementById("allowanceForReq").disabled = true;
		
		$jq('#hideEmployeesDetails').show();
		$jq('#effectiveToDateAdv1').hide();
		$jq('#w-input-search').val('');
		$jq('#searchEmp').hide();
	 }
	if($jq('#deptId').val() === "3"){
		
		document.getElementById("allowanceForALL").disabled = true;
		document.getElementById("allowanceForExceptMD").disabled = true;
		document.getElementById("allowanceForReq").disabled = false;
		
	    $jq('#hideEmployeesDetails').hide();
	    $jq('#effectiveToDateAdv1').show();
	    $jq('#searchEmp').show();
	}
}



function allowanceSubmit(){
	var status=true;
	var errorMessages="";
	var	allowanceName="";
	var allowanceFor = "";
	var approvedBy = "";
	var approvalDateAdv = "";
	var effectiveFromDate = "";
	var effectiveToDate = "";
	var allowanceAmt = "";
	var empDetails = "";
	var allowanceEmpsJson={};
	var k=0;
	$jq("#dataList tr:not(:first)").each(function() {
		if($jq(this).find("td").eq(0).find(":input:checkbox").is(':checked')){
			alert($jq(this).find("td").eq(3).text());
			allowanceEmpsJson[k]=$jq(this).find("td").eq(0).find(":input:checkbox")[0].id
			k++;
			ltcMemberFlag=true;
		}		
	});
		
	if ($jq('#deptId').val() == 'select') {
		errorMessages +="Please Select Allowance Name \n";
		if(status){
			status = false;
			$jq('#deptId').focus();
		}
	}else{
		allowanceName = $jq('#deptId').val();
	}
	
	if (!$jq('#allowanceForALL').is(':checked') && !$jq('#allowanceForExceptMD').is(':checked') && !$jq('#allowanceForFixed').is(':checked') && !$jq('#allowanceForReq').is(':checked')) {
		errorMessages +="Please Select Allowance Applicable For \n";
		if(status){
			status = false;
			$jq('#allowanceForY').focus();
		}
	}else{
		if($jq('#allowanceForALL').is(':checked')){
			allowanceFor = $jq('#allowanceForALL').val();
		}
		if($jq('#allowanceForExceptMD').is(':checked')){
			allowanceFor = $jq('#allowanceForExceptMD').val();
		}
		if($jq('#allowanceForFixed').is(':checked')){
			allowanceFor = $jq('#allowanceForFixed').val();
		}
		if($jq('#allowanceForReq').is(':checked')){
			allowanceFor = $jq('#allowanceForReq').val();
		}
	}
	
	if ($jq('#approvedBy').val() == 'select') {
		errorMessages +="Please Select Approved By \n";
		if(status){
			status = false;
			$jq('#approvedBy').focus();
		}
	}else{
		approvedBy = $jq('#approvedBy').val();
	}
	
	if ($jq('#allowanceAmt').val() == '') {
		errorMessages += 'Please Enter Allowance Amount \n';
		if (status) {
			status = false;
			$jq('#allowanceAmt').focus();
		}		
	} else {
		allowanceAmt = $jq('#allowanceAmt').val();
	}
	
	if ($jq('#approvalDateAdv').val() == '') {
		errorMessages += 'Please Select Date of Approval \n';
		if (status) {
			status = false;
			$jq('#approvalDateAdv').focus();
		}		
	} else {
		approvalDateAdv = $jq('#approvalDateAdv').val();
	}
	
	if ($jq('#effectiveFromDateAdv').val() == '') {
		errorMessages += 'Please Select Effect from Date \n';
		if (status) {
			status = false;
			$jq('#effectiveFromDateAdv').focus();
		}		
	} else {
		effectiveFromDate = $jq('#effectiveFromDateAdv').val();
	}
	
	if($jq('#allowanceForReq').is(':checked')){
		if ($jq('#effectiveToDateAdv').val() == '') {
			errorMessages += 'Please Select Effect to Date \n';
			if (status) {
				status = false;
				$jq('#effectiveToDateAdv').focus();
			}		
		} else {
			effectiveToDate = $jq('#effectiveToDateAdv').val();
		} 
	}
	
	if($jq('#allowanceForReq').is(':checked')){
		if ($jq('#w-input-search').val() == '') {
			errorMessages += 'Please Select An Employee \n';
			if (status) {
				status = false;
				$jq('#w-input-search').focus();
			}		
		} else {
			empDetails = $jq('#w-input-search').val();
			empDetails = empDetails.substring(empDetails.indexOf("(")+1,empDetails.indexOf(")"));
		}
		
	}
	
	if(!$jq('#allowanceForReq').is(':checked')){
	var k1=0;
	var ltcMemberFlag1=false;
	$jq("#dataList tr:not(:first)").each(function() {
		if($jq(this).find("td").eq(0).find(":input:checkbox").is(':checked')){
			allowanceEmpsJson[k1]=$jq(this).find("td").eq(0).find(":input:checkbox")[0].id
			k1++;
			ltcMemberFlag1=true;
		}		
	});
	if(!ltcMemberFlag1){
		errorMessages +="Please Check Atleast One Employee \n";
		status=false;
	}
}
	
	
	if (status && errorMessages == '') {
		requestDetails={
				"param":"submitAllowanceRequest",
				"allowanceName" : allowanceName,
				"allowanceFor": allowanceFor,
				"approvedBy": approvedBy,
				"approvedDate": approvalDateAdv,
				"allowanceAmt": allowanceAmt,
				"effectFromDate": effectiveFromDate,
				"effectToDate": effectiveToDate,
				"empDetails": empDetails,
				"jsonValue":JSON.stringify(allowanceEmpsJson)
		}
		
		$jq.ajax({
			type : "POST",
			url : "allowancesRequest.htm",
			data : requestDetails,
			cache : false,
			success : function(html) {
				$jq("#result").html(html);				
				if ($jq('.success').is(':visible')) {
					resetAllowance();
					document.forms[0].param.value = "AllowancesHome";
					document.forms[0].action = "allowancesRequest.htm";
					document.forms[0].submit();
				}
			}
		});
		
		
	}else{
		if (errorMessages == '') {
			alert('Please check the exceptions');
		} else {
			alert(errorMessages);
		}
	}
}


function searchEmployeeByNameOrId(){
	var empDetails = $jq('#searchEmployee').val();
	requestDetails={
			"param":"searchEmployeeRequest",
			"empDetails": empDetails
	}
	
	$jq.ajax({
		type : "POST",
		url : "allowancesRequest.htm",
		data : requestDetails,
		 dataType : "json",
         success : function(data) {
                 response(data);
         }
	});
	
	
	
	
	
	
	
	
	
	
}

function resetAllowance(){
	$jq('input:radio').attr("checked",false);
	$jq('input').val('');
	$jq('#deptId').val('-- Select --');
}


function clearfpa(){
	$jq('input:radio').attr("checked",false);
	$jq('input').val('');
}