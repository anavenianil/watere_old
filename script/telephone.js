function selectDesignationWiseEmployees(){
	var flag=true;
	if (document.getElementById('designationId').value == "select") {
		errorMessages = "Please Select Designation ?\n";
		document.getElementById('designationId').focus();
		flag = false;
	}
	if(flag){
	$jq('#param').val('getEmployeeData');
	$jq.post('telephone.htm', $jq('#telephone').serialize(),
			function(html) {
				$jq("#result1").html(html);
				$jq("#result").html('');
			});
	}
	if (status) {
		var requestDetails = {
			"mainClaimList" : JSON.stringify(mainJSON),
		    "grandTotal" :$jq('#grandTotal').val(),
		    "param"  :   "submitTFRequestDetails",
		    "limitId"  :  $jq('#limitId').val(),
		};
		$jq.post('tutionFee.htm',requestDetails,function(html) {
			$jq("#returnrequest").html(html);
			});
		clearTutionFeeDetails();
	}
}
function selectDesignationWiseEmployeesList(){
	var errorMessage ="";
	var flag=true;
	if($jq('#designationId').val()=='select'){
	    flag=false;
		$jq('#SelectLeft').find('option').remove().end();
		$jq('#SelectRight').find('option').remove().end();
	}
	if(flag){
	$jq('#param').val('getEmployeeDesignationListData');
	$jq.post('telephone.htm', $jq('#telephone').serialize(),
			function(html) {
		$jq("#result1").html(html);
		$jq("#result").html('');
				
		});
	}
}
function submitEmployeeSelectedList(){
	var selectedValues = "";
	var errorMessages = "";
	var flag = true;
	var status = false;
	if($jq('#designationId').val()=='select'){
		errorMessages += "Please Select Designation Name ";
		flag=false;
	}
	for(var i=0; i<document.getElementById('SelectRight').options.length; i++){
		selectedValues += document.getElementById('SelectRight').options[i].value+ ",";
		status = true;
	}
	if (flag) {
		$jq('#pk').val(selectedValues);
		$jq('#designationId').find('option:selected').val();
		$jq('#param').val('submitSelectedTelephoneEmployeeList');
		$jq.post('telephone.htm', $jq('#telephone').serialize(),function(html) {
			$jq("#result").html(html);
			 $jq('#pk').val('');
			 clearEmployeeSelectedList();
		});
	}else{
		alert(errorMessages);
	}
	
}
function clearEmployeeSelectedList(){
	$jq('#designationId').val('select');
	$jq('#SelectLeft').find('option').remove().end();
	$jq('#SelectRight').find('option').remove().end();
}
function submitTelephoneEligibleDetails(){
	var selectedValues = "";
	var errorMessages = "";
	var flag = true;
	var status = false;
	if (document.getElementById('designationId').value == "select") {
		errorMessages = "Please Select Designation ?\n";
		document.getElementById('designationId').focus();
		flag = false;
	}else if($jq('#amount').val()==''){
		errorMessages = "Please Enter Amount?\n";
		flag = false;
	}else if($jq('#serviceTax').val()==''){
		errorMessages = "Please Enter Servicre Tax?\n";
		flag = false;
	}else if($jq('input:radio[name=internetFlag]:checked').val()!='0' && $jq('input:radio[name=internetFlag]:checked').val()!='1'){
		errorMessages = "Please Select internet Flag ?\n";
		flag = false;
	}
	for ( var i = 0; i < document.getElementById('SelectRight').options.length; i++) {
		selectedValues += document.getElementById('SelectRight').options[i].value+ ",";
		status = true;
	}
	if (flag) {
		$jq('#pk').val(selectedValues);
		$jq('#param').val('submitEligibleEmpList');
		$jq('input:radio[name=internetFlag]:checked').val();
		$jq.post('telephone.htm', $jq('#telephone').serialize(),function(html) {
			$jq("#result").html(html);
			 $jq('#pk').val('');
		});
		clearTelephoneBillEligibleDetails();
	} else {
		alert(errorMessages);
	}
	
}
function submitTelephoneBillDesignationEligibilityDetails(){
	var selectedValues = "";
	var errorMessages = "";
	var flag = true;
	var status = false;
	var selectedDesig = "";
	$jq("#selectedDiv option").each(function() {
		selectedDesig += $jq(this).val() + ",";
	});
	if (selectedDesig == "") {
		errorMessages += "Please Add The Designations.\n";
		flag = false;
	}
	if($jq('#amount').val()==''){
		errorMessages += "Please Enter Amount As '0' If Amount Not Exit?\n";
		flag = false;
	}
	if($jq('#amount1').val()==''){
		errorMessages += "Please Enter '0' If Amount Not Exit?\n";
		flag = false;
	}
	 if($jq('#serviceTax').val()==''){
		errorMessages += "Please Enter '0' If Servicre Tax Not Exit?\n";
		flag = false;
	}
	 if($jq('input:radio[name=applicableEmployee]:checked').val()!='0' && $jq('input:radio[name=applicableEmployee]:checked').val()!='1'){
		errorMessages += "Please Select Aplicable For All Employees ?\n";
		flag = false;
	}
	for(var i=0; i<document.getElementById('SelectRight').options.length; i++){
		selectedValues += document.getElementById('SelectRight').options[i].value+ ",";
		status = true;
	}
	if (flag) {
			if (selectedDesig != "") {
				selectedDesig = selectedDesig.substring(0,
						selectedDesig.length - 1);
			}
			
			var requestDetails = {
				"amountWithInternet" 		   	: $jq('#amount').val(),
				"serviceTaxWithInternet" 		: $jq('#serviceTax').val(),
				"totAmountWithInternet" 		: $jq('#totAmount').val(),
				"amountWithoutInternet" 		: $jq('#amount1').val(),
				"serviceTaxWithoutInternet"   	: $jq('#serviceTax1').val(),
				"totAmountWithoutInternet" 		: $jq('#totAmount1').val(),
				"designationId" 				: selectedDesig,
				"param" 						: "submitTeleDesigEligibileDetails",
				"applicableEmployee" :$jq('input:radio[name=applicableEmployee]:checked').val()	
			};
			$jq.post('telephone.htm',requestDetails,function(html) {
				$jq("#result").html(html);
				if ($jq('.success').is(':visible')) {
					clearTelephoneBillEligibleDetails();
				}
				});
		}else{
		alert(errorMessages);
	}
}
function editTeleDesigDetails(designationsIds,amountWithInternet,serviceTaxWithInternet,totAmountWithInternet,amountWithoutInternet,serviceTaxWithoutInternet,totAmountWithoutInternet,applicableEmployee){
		$jq('#amount').val(amountWithInternet);
		$jq('#serviceTax').val(serviceTaxWithInternet);
		$jq('#totAmount').val(totAmountWithInternet);
		$jq('#amount1').val(amountWithoutInternet);
		$jq('#serviceTax1').val(serviceTaxWithoutInternet);
		$jq('#totAmount1').val(totAmountWithoutInternet);
	if(applicableEmployee==1){
		$jq("#applicableEmployee1[value=" + applicableEmployee + "]").attr('checked', true);
		}
	else if(applicableEmployee==0){
			$jq("#applicableEmployee2[value=" + applicableEmployee + "]").attr('checked', true);
		}
	var requestDetails = {
			"param" : "getSelectedDesignationList",
			"editDesignations" : designationsIds,
			// "pk" :id
		};
		$jq.post('telephone.htm'+'?'+dispUrl, requestDetails, function(html) {
			$jq("#selectedDiv").html(html);
		});
}
function deleteTeleDesigDetails(designationsIds){
	var check=confirm("Do u want to delete ?");
	if(check){
		var requestDetails = {
			"param" : "deleteTelephoneDesigEligibilityDetails",
			"designationId" :designationsIds			
		};
		$jq.post('telephone.htm'+'?'+dispUrl, requestDetails, function(html) {
			$jq("#result").html(html);
		});
	}

}
function clearTelephoneBillEligibleDetails(){
	$jq('#SelectRight').find('option').remove().end();
	$jq('#serviceTax').val('');
	$jq('#totAmount').val('');
	$jq('#amount').val('');
	$jq('#serviceTax1').val('');
	$jq('#totAmount1').val('');
	$jq('#amount1').val('');
	$jq("input:radio").attr("checked", false);
}
function calculateInternetTeleEligibleAmount(){
	var error='';
	var amount=0;
	var servicetax=0;
	var totAmount=0.0;
	if($jq('#amount').val()==''){
		error+="Please Enter Amount!";
	}
	if(error==''){
	amount=$jq('#amount').val();
	if($jq('#serviceTax').val()!=''){
	servicetax=$jq('#serviceTax').val();
	totAmount=parseInt(amount)+(parseFloat(servicetax)*parseInt(amount))/100.0;
	// $jq('#totAmount').val(totAmount)
	$jq('#totAmount').val(Math.round(totAmount));
	}
	}else{
		alert(error);
		$jq('#serviceTax').val('');
		$jq('#totAmount').val('');
	}
}
function calculateWithoutInternetTeleEligibleAmount(){
	var error='';
	var amount=0;
	var servicetax=0;
	var totAmount=0.0;
	if($jq('#amount1').val()==''){
		error+="Please Enter Amount!";
	}
	if(error==''){
	amount=$jq('#amount1').val();
	if($jq('#serviceTax1').val()!=''){
	servicetax=$jq('#serviceTax1').val();
	totAmount=parseInt(amount)+(parseFloat(servicetax)*parseInt(amount))/100.0;
	$jq('#totAmount1').val(Math.round(totAmount));
	}
	}else{
		alert(error);
		$jq('#serviceTax1').val('');
		$jq('#totAmount1').val('');
	}
}
function submitTelephoneBillRequestDetails(){
	var errorMessage = "";
	var error = '';
	var status = true;
	var jsonFieldSetRow={};
	if($jq('#fromDate').val()==''){
		errorMessage +="Please Select The FromDate of PeriodOfClaim\n";
		status = false;
	}
	if($jq('#toDate').val()==''){
		errorMessage +="Please Select The ToDate of PeriodOfClaim\n";
		status = false;
	}
	/*
	 * if($jq('#telephoneNo').val()==''){ errorMessage +="Please Enter
	 * TelephoneNo\n"; status = false; } if($jq('#billNo').val()==''){
	 * errorMessage +="Please Enter BillNo\n"; status = false; }
	 * if($jq('#receiptNo').val()==''){ errorMessage +="Please Enter The
	 * RecieptNo\n"; status = false; } if($jq('#amountClaimed').val()==''){
	 * errorMessage +="Please Enter The Claimed Amount\n"; status = false; }
	 * if($jq('#serviceTax').val()==''){ errorMessage +="Please Enter The
	 * ServiceTax\n"; status = false; }
	 */
	 $jq('#telephoneDetails').find('table').each(function(){
		 var flag3=true;
		 $jq(this).find('tr:not(:first)').each(function(){
			 var name=$jq(this).find("td").eq(0).text();
			 var flags4=true;
			 if(flags4){
				 if($jq(this).find('td').eq(2).find('input').val()=='' && $jq(this).find('td').eq(3).find('input').val()=='' && $jq(this).find('td').eq(4).find('input').val()=='' && $jq(this).find('td').eq(5).find('input').val()=='' && $jq(this).find('td').eq(6).find('input').val()=='' && $jq(this).find('td').eq(7).find('input').val()==''){
					 flags4=false;
				}else{
					if($jq(this).find('td').eq(2).find('input').val()==''){
						errorMessage +=" BillNo Is Empty For :" +name+ "\n"
						status = false;
					}
				   if($jq(this).find('td').eq(3).find('input').val()==''){
					   errorMessage +=" BillDated Is Empty For :" +name+ "\n"
						status = false;
					}
				   if($jq(this).find('td').eq(4).find('input').val()==''){
				    	errorMessage +=" ReceiptNo Is Empty For :" +name+ "\n"
						status = false;
					}
				   if($jq(this).find('td').eq(5).find('input').val()==''){
				    	errorMessage +=" ReceiptDated Is Empty For :" +name+ "\n"
						status = false;
					}
				    if($jq(this).find('td').eq(6).find('input').val()==''){
				    	errorMessage +=" AmountClaimed Is Empty For :" +name+ "\n"
						status = false;
					}
				    if($jq(this).find('td').eq(7).find('input').val()==''){
					   errorMessage +=" ServiceTax Is Empty For :" +name+ "\n"
						status = false;
					}
				    flag3=false;
				}
			 } 
		 });
		 if(flag3){
			  errorMessage+="Please Fill The  Details Of Internet/Broadband,LandLine and Mobile. \n"
			  status=false;
		  }
	 });
	 $jq('#telephoneDetails').find('table tr:not(:first)').each(function(){
			var mainTableJSON={};
			var mainJSON={};
			var i=0;
			$jq('#telephoneDetails').find('table  tr:not(:first)').each(function() {
				var id=$jq(this).find("td").eq(2).find('input').attr('id').substring(6);
				var jsonTableRow={};
				   if($jq(this).find("td").eq(1).find('input').val()!=''){
					jsonTableRow['telephoneNo'+id]=$jq(this).find("td").eq(1).find('input').val();
				   }else{
					   jsonTableRow['telephoneNo'+id]=$jq(this).find("td").eq(1).text();
				   }
					  jsonTableRow['billNo'+id]=$jq(this).find("td").eq(2).find('input').val();
				    jsonTableRow['billDated'+id]=$jq(this).find("td").eq(3).find('input').val();
				    jsonTableRow['receiptNo'+id]=$jq(this).find("td").eq(4).find('input').val();
				    jsonTableRow['recieptDated'+id]=$jq(this).find("td").eq(5).find('input').val();
					if($jq(this).find("td").eq(6).find('input').val()=='')
	                jsonTableRow['amountClaimed'+id]=0;
				    else 
				    jsonTableRow['amountClaimed'+id]=$jq(this).find("td").eq(6).find('input').val();
					jsonTableRow['serviceTax'+id]=$jq(this).find("td").eq(7).find('input').val();
					if($jq(this).find("td").eq(8).find('input').val()=='')
					jsonTableRow['total'+id] =0;
					else
			        jsonTableRow['total'+id]=parseFloat($jq(this).find("td").eq(8).find('input').val());
			        jsonTableRow['claimId'+id]=$jq(this).find("td").eq(9).find('input').attr('Value');
					mainTableJSON[i]=jsonTableRow;
					i++;
				});
		     jsonFieldSetRow = mainTableJSON;
		    	 });
  if (status) {
		var requestDetails = {
				"mainTelephoneList" : JSON.stringify(jsonFieldSetRow),
				"grandTotal" :$jq('#grandTotal').val(),
		        "param"  :   "submitTelephoneBillRequestDetails",
		        "fromDate" :$jq('#fromDate').val(),
				"toDate" :$jq('#toDate').val(),
			    "internetFlag"   :$jq('input:radio[name=teleApplicableEmp]:checked').val(),
			    "userRemarks"   :$jq('#userRemarks').val(),
			    "missingPeriodFrom" :$jq('#missingPeriodDiv').find('fieldset:visible').find('#missingPeriodFrom').val(),
			    "missingPeriodTo" :$jq('#missingPeriodDiv').find('fieldset:visible').find('#missingPeriodTo').val(),
			    "missingClaimRemarks":$jq('#missingPeriodDiv').find('fieldset:visible').find('#missingClaimRemarks').val(),
		      	};
		$jq.post('telephone.htm',requestDetails,function(html) {
			$jq("#result").html(html);
			if ($jq('.success').is(':visible')) {
				//var requestType = $jq('#headTitle').html();
				var check=confirm( "Telephone Bill has been Successfully Sent...\n\nPlease click ok to 'Preview Telephone Bill Application Form 'Take print' from here\n\n");
				if(check){
				document.forms[0].requestId.value = $jq.trim(requestIDtel);
				//document.forms[0].roleId.value = 'roleId';
			   document.forms[0].param.value = "requestDetails";
				document.forms[0].action = "workflowmap.htm";
				document.forms[0].submit();	
				}
				
			}clearTelephoneBillRequestDetails();
			});
	} else {
		alert(errorMessage);
	}
}
function clearTelephoneBillRequestDetails(){
	$jq('#fromDate').val('');
	$jq('#toDate').val('');
	$jq('#grandTotal').val('');
	$jq('#telephoneDetailsTab tr:not(:first)').each(function (){
		$jq(this).find('input').val('');
	});
	$jq('#missingPeriodFrom').val('');
	$jq('#missingPeriodTo').val('');
	$jq('#missingClaimRemarks').val('');
	$jq('#missingPeriodDiv').css("display","none");
	$jq('#userRemarks').val('');
}
/*
 * function sumTelephoneEntries(){ var sum=0;
 * $jq('#telephoneDetails').find('table tr:not(:first)').each(function() { var
 * claimId=$jq(this).find('td').eq(8).find('input').attr('id');
 * if($jq(this).find('td').eq(6).find('input').val()!=''){
 * if($jq(this).find('td').eq(7).find('input').val()!=''){
 * 
 * //$jq(this).find('td').eq(8).find('input').val((parseInt($jq(this).find('td').eq(6).find('input').val())+parseInt(($jq(this).find('td').eq(7).find('input').val()*($jq(this).find('td').eq(6).find('input').val()))/100)));
 * $jq(this).find('td').eq(8).find('input').val(parseFloat($jq(this).find('td').eq(6).find('input').val())+parseFloat($jq(this).find('td').eq(7).find('input').val()));
 * }else{ //$jq(this).find('td').eq(7).find('input').val('0');
 * $jq(this).find('td').eq(8).find('input').val($jq(this).find('td').eq(6).find('input').val()); }
 *  } if($jq(this).find('td').eq(8).find('input').val() == ''){ sum+=0; }else{
 * sum+=parseFloat($jq(this).find('td').eq(8).find('input').val()); } });
 * $jq('#grandTotal').val(Math.round(sum)); }
 */
function sumTelephoneEntries(){
var sum=0;
var indClaimTotal=0;
$jq('#telephoneDetails').find('table tr:not(:first)').each(function() {
	var claimId=$jq(this).find('td').eq(8).find('input').attr('id');
	if($jq(this).find('td').eq(6).find('input').val()!=''){
		if($jq(this).find('td').eq(7).find('input').val()!=''){
			indClaimTotal=parseFloat($jq(this).find('td').eq(6).find('input').val())+Math.abs(parseFloat($jq(this).find('td').eq(7).find('input').val()).toFixed(2));
			$jq(this).find('td').eq(8).find('input').val(indClaimTotal);
		}else{
		// $jq(this).find('td').eq(7).find('input').val('0');
			$jq(this).find('td').eq(8).find('input').val($jq(this).find('td').eq(6).find('input').val());
		}
	}
	if($jq(this).find('td').eq(6).find('input').val()==''){
		 $jq(this).find('td').eq(8).find('input').val('');
		 $jq(this).find('td').eq(7).find('input').val('');
		 $jq('#grandTotal').val('');
	}
		if($jq(this).find('td').eq(8).find('input').val() == ''){
			sum+=0;
		}else{
			sum+=parseFloat($jq(this).find('td').eq(8).find('input').val());
		}
	});
	$jq('#grandTotal').val(Math.round(sum));
	}
// old working code
/*
 * function sumAllOfTelephoneAtSanctionStatus(totAmount){ var amountPerChild =
 * 0; $jq('#telephoneClaimTable tr:not(:first)').each(function(){
 * if($jq(this).find('td').eq(6).find('input').val() == ''){ amountPerChild+=0;
 * 
 * }else{
 * amountPerChild+=parseInt(($jq(this).find('td').eq(6).find('input').val())+($jq('#cashAssignment').val()));
 * if(amountPerChild>totAmount){ alert("Max Amount Should not be Greater Than
 * "+totAmount+"") $jq('#telephoneClaimTable tr:not(:first)').each(function(){ //
 * added condition to make entry fields empty if value greater than totAmount
 * $jq(this).find('td').eq(6).find('input').val(''); });
 * $jq('#telephoneClaimDetails tr:not(:first)').each(function(){
 * $jq(this).find('td').eq(4).find('input').val(0); }); }else{
 * $jq('#telephoneClaimDetails tr:not(:first)').each(function(){
 * $jq(this).find('td').eq(4).find('input').val(amountPerChild); }); } } }); }
 */
// new code and present working code 30102013
/*
 * function sumAllOfTelephoneAtSanctionStatus(totAmount){ var amountPerChild =
 * 0;
 * 
 * $jq('#telephoneClaimTable tr:not(:first ,:last)').each(function(){
 * if($jq(this).find('td').eq(6).find('input').val() == ''){ amountPerChild+=0;
 * 
 * }else{
 * amountPerChild+=parseInt($jq(this).find('td').eq(6).find('input').val()); }
 * }); if($jq('#telephoneClaimTable
 * tr:last').find('td').eq(1).find('input').val()!= undefined){
 * amountPerChild=parseInt($jq('#telephoneClaimTable
 * tr:last').find('td').eq(1).find('input').val())+amountPerChild; }
 * $jq('#telephoneClaimDetails tr:not(:first)').each(function(){
 * $jq(this).find('td').eq(4).find('input').val(amountPerChild); });
 * 
 * $jq('#telephoneTotal').val(amountPerChild); if(amountPerChild>totAmount){
 * alert("Max Amount Should not be Greater Than "+totAmount+"")
 * $jq('#telephoneClaimTable tr:not(:first)').each(function(){ // added
 * condition to make entry fields empty if value greater than totAmount
 * $jq(this).find('td').eq(6).find('input').val(''); });
 * $jq('#telephoneClaimDetails tr:not(:first)').each(function(){
 * $jq(this).find('td').eq(4).find('input').val(0); });
 * $jq('#telephoneClaimTable tr:last').find('td').eq(1).find('input').val(0)
 * $jq('#totalAmountSanctionDiv').find('td').eq(1).find('input').val('');
 *  }
 *  }
 */
// temp code for finance
function sumAllOfTelephoneAtSanctionStatus(totAmount,claimedAmount){
	var amountPerChild = 0;
	
	$jq('#telephoneClaimTable tr:not(:first ,:last)').each(function(){
		if($jq(this).find('td').eq(6).find('input').val() == ''){
			amountPerChild+=0;
			
		}else{
			amountPerChild+=parseInt($jq(this).find('td').eq(6).find('input').val());
		}
		});
	  if($jq('#telephoneClaimTable tr:last').find('td').eq(1).find('input').val()!= undefined && $jq('#telephoneClaimTable tr:last').find('td').eq(1).find('input').val()!=''){
		  
				 amountPerChild=parseInt($jq('#telephoneClaimTable tr:last').find('td').eq(1).find('input').val())+amountPerChild;
	  }
	  $jq('#telephoneClaimDetails tr:not(:first)').each(function(){
		$jq(this).find('td').eq(4).find('input').val(amountPerChild);
		});
	
	  $jq('#telephoneTotal').val(amountPerChild);
	  //This code refers to give alert if finace sanctioned amount is more than eligibility amount
	  //if(amountPerChild>claimedAmount){ 
		  //alert("Max Amount Should not be Greater Than "+totAmount+"")
		  //$jq('#telephoneClaimTable tr:not(:first)').each(function(){ // added condition to make entry fields empty if value greater than totAmount
		  //$jq(this).find('td').eq(6).find('input').val('');
		 // });
	     //$jq('#telephoneClaimDetails tr:not(:first)').each(function(){
        // $jq(this).find('td').eq(4).find('input').val(0); 
         //});
	     //$jq('#telephoneClaimTable tr:last').find('td').eq(1).find('input').val(0)
	     //$jq('#totalAmountSanctionDiv').find('td').eq(1).find('input').val('');
	 // }
	
}
function printTelephoneBillRequestFormDetails(requestId){
	window.open(
			"./report.htm?param=printTelephoneBillRequestFormDetails&returnValue=report&requestID="+requestId,
			'preview',
			'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
}
function printAllTelephoneBillRelatedDocuments(){
	var billNo='';
	var errorMessage='';
	var status=true;
	/*var i=0;
	$jq('#dataTable').find('table').find('tr:not(:first)').each(function(){
		if($jq(this).find('td').eq(0).find('input:checkbox').attr('checked')==true){
			i++;
		}
	});
	if(i==0){
		errorMessage="Please select atleast One Request!"
		status=false;
	}
	i=0;*/
	if(status){
		billNo=$jq('#billNo').val().split("/")[4];
			window
				.open(
						"./report.htm?param=printAllTelephoneBillRelatedDocuments&returnValue=report&billNo="+billNo,
						'preview',
						'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
			
		}else{
			alert(errorMessage);
		}		
}
// telephone bill individual report
function printTelephoneBillSanctionedIndividualReport(){
	var billNo='';
	var errorMessage='';
	var status=true;
	/*var i=0;
	$jq('#dataTable').find('table').find('tr:not(:first)').each(function(){
		if($jq(this).find('td').eq(0).find('input:checkbox').attr('checked')==true){
			i++;
		}
	});
	if(i==0){
		errorMessage="Please select atleast One Request!"
		status=false;
	}
	i=0;*/
	if(status){
		billNo=$jq('#billNo').val().split("/")[4];
			window
				.open(
						"./report.htm?param=printTelephoneBillSanctionedIndividualReport&returnValue=report&billNo="+billNo,
						'preview',
						'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
			
		}else{
			alert(errorMessage);
		}   
}
// report script in finance screen
function printAllTelephoneBillRelatedDocuments1(requestType,type){
	var billNo='';
	var errorMessage='';
	var status=true;
	if(type=='officers'){
		if($jq('#saveOfficerTuitionTeleFinance').find('#billNo').val()==''){
			errorMessage="Please Enter The BillNo For Print";
			status=false;
		}
	}else if(type=='staff'){
		if($jq('#saveStaffTuitionTeleFinance').find('#billNo').val()==''){
			errorMessage="Please Enter The BillNo For Print";
			status=false;
		}
	}
	if(type=='officers'){
		billNo=$jq('#saveOfficerTuitionTeleFinance').find('#billNo').val();
	}else if(type=='staff'){
		billNo=$jq('#saveStaffTuitionTeleFinance').find('#billNo').val();
	}
	if(status==true){
				window
				.open(
						"./report.htm?param=printAllTelephoneBillRelatedDocuments&returnValue=report&type=''&billNo="+billNo+'&requestType='+requestType,
						'preview',
						'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
		}else{
			alert(errorMessage);
			$jq('#financeBillNo').val('');
		}		
}
// telephone bill individual report
function printTelephoneBillSanctionedIndividualReport1(requestType,type){
	
	var billNo='';
	var errorMessage='';
	var status=true;
	if(type=='officers'){
		if($jq('#saveOfficerTuitionTeleFinance').find('#billNo').val()==''){
			errorMessage="Please Enter The BillNo For Print";
			status=false;
		}
	}else if(type=='staff'){
		if($jq('#saveStaffTuitionTeleFinance').find('#billNo').val()==''){
			errorMessage="Please Enter The BillNo For Print";
			status=false;
		}
	}
	if(type=='officers'){
		billNo=$jq('#saveOfficerTuitionTeleFinance').find('#billNo').val();
	}else if(type=='staff'){
		billNo=$jq('#saveStaffTuitionTeleFinance').find('#billNo').val();
	}
	if(status==true){
				window
				.open(
						"./report.htm?param=printTelephoneBillSanctionedIndividualReport&type=''&returnValue=report&billNo="+billNo+'&claimId='+$jq('#claimType').val()+'&requestType='+requestType,
						'preview',
						'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
		}else{
			alert(errorMessage);
			$jq('#financeBillNo').val('');
		}	 
}
function checkTeleEmpDetailsWithInternet(designationId){
	var flag=true;
	if($jq('#teleApplicableEmp1').is(':checked')==true || $jq('#teleApplicableEmp2').is(':checked')==true){
		$jq('#internetDetailsDiv').css("display","block");
	}
	if(flag){
		var requestDetails = {
				"param"         : "checkTeleEmpDetailsWithInternet",
			    "designationId" : designationId,
			    "internetFlag"  : $jq('input:radio[name=teleApplicableEmp]:checked').val()
		};
		$jq.post('telephone.htm'+'?'+dispUrl, requestDetails, function(html) {
			$jq("#internetDetailsDiv").html(html);
		});
	}
	}
/*function checkForTeleInternet(){
	var status = "";
	var errorMessage = "";
	if(($jq('#teleApplicableEmp1').is(':checked')==false && $jq('#teleApplicableEmp2').is(':checked')==false)){
		status=false;
		errorMessage+="Please Select Claim Which includes WithInternet and WithOutInternet";
	}else{
		status=true;
	}
	if(status==false){
		 alert(errorMessage);
		 $jq('#fromDate').val('');
	}
}*/
function checkForTeleInternet(){
	var status =true;
	var errorMessage = "";
	if(($jq('#teleApplicableEmp1').is(':checked')==false && $jq('#teleApplicableEmp2').is(':checked')==false)){
		status=false;
		errorMessage+="Please Select Claim Which includes WithInternet and WithOutInternet";
	}
	if(status){
		var requestDetails ={
				"param" : "checkMissingPeriodDetails",
				"fromDate" : $jq('#fromDate').val(),
		};
		$jq.post('telephone.htm',requestDetails,function(html) {
			$jq("#missingPeriodDiv").html(html);
			$jq("#missingPeriodDiv").css("display","block")
			});
	}else{
		alert(errorMessage);
		$jq('#fromDate').val('');
	}
}
function submitTeleCashAssignment(){
	var status =true;
	var errorMessage ="";
	
	if($jq('#teleSfid').val()==''){
		errorMessage += "Please Enter Sfid \n";
		status=false;
		$jq('#teleSfid').focus();
	}
	if($jq('#fromDate').val()==''){
		errorMessage += "Please Enter Effective From \n";
		status=false;
		$jq('#fromDate').focus();
	}
	if($jq('#toDate').val()==''){
		errorMessage += "Please Enter Effective To \n";
		status=false;
		$jq('#toDate').focus();
	}

	if(status==true){
		var requestDetails ={
				"pk" : nodeID,
				"param" : "submitTeleCashAssignment",
				"teleSfid" : $jq('#teleSfid').val(),
				"fromDate" : $jq('#fromDate').val(),
				"toDate" : $jq('#toDate').val(),
		};
		$jq.post('telephone.htm',requestDetails,function(html) {
			$jq("#result").html(html);
			if ($jq('.success').is(':visible')) {
				clearTeleCashAssignment();
			}
			});
	}else{
		alert(errorMessage);
	}
}
function editTeleCashAssignmentMaster(id,sfid,fromDate,toDate){
	nodeID = id;
	$jq('#teleSfid').val(sfid);
	$jq('#fromDate').val(fromDate);
	$jq('#toDate').val(toDate);
}
function deleteTeleCashAssignmentMaster(id){
	var check=confirm("Do u want to delete ?");
	if(check){
		var requestDetails = {
			"param" : "deleteTeleCashAssignmentMaster",
			"pk" :id			
		};
		$jq.post('telephone.htm'+'?'+dispUrl, requestDetails, function(html) {
			$jq("#result").html(html);
		});
	}
}
function clearTeleCashAssignment(){
	$jq('#teleSfid').val('');
	$jq('#fromDate').val('');
	$jq('#toDate').val('');
}
// dates check
function checkTelephoneDates(){
	var errorMessage ="";
	var status =true;
	
	var fdate=convertDate($jq('#fromDate').val());
	var tdate=convertDate($jq('#toDate').val());
	if(compareDates(fdate,'dd-MM-yyyy',tdate,'dd-MM-yyyy')==1){
		errorMessage+="ToDate must be greater than fromDate";
		status=false;
	}
	if(status==false){
		alert(errorMessage);
		$jq('#toDate').val('');
	}
	}
/*function checkTelephoneBillRecieptDates(){
	var errorMessage ="";
	var status=true;
	$jq('#telephoneDetails').find('table  tr:not(:first,:last)').each(function() {
		if(status==true){
			var id=$jq(this).find("td").eq(3).find('input').attr('id').substring(9);
			  billDate=convertDate($jq('#billDated'+id).val());
			  recieptDate=convertDate($jq('#receiptDated'+id).val());
			  if(compareDates(billDate,'dd-MM-yyyy',recieptDate,'dd-MM-yyyy')==1){
					errorMessage ="RecieptDate must be greater than BillDate";
					status=false;
				}
			  if(status==false){
				  alert(errorMessage);
				  $jq('#receiptDated'+id).val('');
			 }
		}
		});
}*/
function getTelephoneBillClaimDetails(){
	var errorMessage="";
	var status=true;
	var sfid='';
	var year='';
	if($jq('#teleSfid').val()==''){
		errorMessage+="Please Enter  Sfid For Printing TelephoneBill Details\n";
		status=false;
	}
	if($jq('#year').val()==''){
		errorMessage+="Please Enter Year For Printing TelephoneBill Details\n";
		status=false;
	}
	if(status){
		window
				.open(
						"./report.htm?param=printTelephoneBillClaimDetailsReport&returnValue=report&sfid="+$jq('#teleSfid').val()+'&year='+$jq('#year').val(),
						'preview',
						'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
			
		}else{
			alert(errorMessage);
		}	
}
function getUserTelephoneBillClaimDetails(sfid){
	var errorMessage="";
	var status=true;
	var year='';
	if($jq('#year').val()==''){
		errorMessage+="Please Enter Year For Printing TelephoneBill Details.";
		status=false;
	}
	if(status){
		window.open(
				"./report.htm?param=printTelephoneBillClaimDetailsReport&returnValue=report&sfid="+sfid+'&year='+$jq('#year').val(),
				'preview',
				'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
	}else{
		alert(errorMessage);
	}			
}


