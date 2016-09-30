//this file added by bkr 27/09/2016

function submitRetrimentAmt(){
	var errorMessage = "";
	var status = true;
	var id=$jq('#updateID').val();
	var cashorcheck=$jq('#cashorcheck').val();

	
	if ($jq('#dvno').val()=='') {
		errorMessage += "Please Enter DVNO.\n";
		if(status){
		$jq('#dvno').focus();
		}
		status = false;
	}
	if ($jq('#dvDate').val()=='') {
		errorMessage += "Please EnterDV DATE.\n";
		if(status){
		$jq('#dvDate').focus();
		}
		status = false;
	}
	
	if($jq('input:radio[id=cashorcheck1]').is(':checked')){
		var cashorcheck=$jq('#cashorcheck1').val();
		} 
		if($jq('input:radio[id=cashorcheck2]').is(':checked')){
			var cashorcheck=$jq('#cashorcheck2').val();
		}
	
	if(cashorcheck=='' || cashorcheck=='undefined' || cashorcheck==null){
		errorMessage += "Please Check Amount Issed Type.\n";
		status = false;
	}
	if(cashorcheck==="CHEQUE"){
	if ($jq('#bankName').val()=='') {
		errorMessage += "Please Enter BankName \n";
		if(status){
		$jq('#bankName').focus();
		}
		status = false;
	}
	if ($jq('#branchName').val()=='') {
		errorMessage += "Please Enter Branch Name\n";
		if(status){
		$jq('#branchName').focus();
		}
		status = false;
	}
	if ($jq('#chequeNo').val()=='') {
		errorMessage += "Please Enter  Cheque No\n";
		if(status){
		$jq('#chequeNo').focus();
		}
		status = false;
	}
	
	}
	var bankName="";
	var branchName="";
	var chequeNo="";
	if(cashorcheck=="CHEQUE"){
		bankName= $jq('#bankName').val();
		branchName= $jq('#branchName').val();
		chequeNo= $jq('#chequeNo').val();
	}
	
	
	if (status) {
		var requestDetails = {
				"id" : id,
				"dvno" : $jq('#dvno').val(),
				"dvDate" : $jq('#dvDate').val(),
				"bankName" : bankName,
				"branchName" : branchName,
				"chequeNo" : chequeNo,
				"cashorcheck" : cashorcheck,
				"param" : "retrimentPaymentSave"
			};
			$jq.ajax( {
				type : "POST",
				url : "retriment.htm?",
				data : requestDetails,
				cache : false,
				success : function(html) {
					$jq('#result').html(html);
					if ($jq('.success').is(':visible')) {
						document.forms[0].param.value = "retrimentAmtIssueHome";
						document.forms[0].action = "retriment.htm";
						document.forms[0].submit();
					}else{
						document.forms[0].param.value = "retrimentAmtIssueHome";
						document.forms[0].action = "retriment.htm";
						document.forms[0].submit();
					}
				}
			});

	} else {
		alert(errorMessage);
	}

}


function showBankDetails(type){
	
	if(type=="hide"){
	document.getElementById('bnkdetails').style.display= 'none';
	}
	if(type=="show"){
		document.getElementById('bnkdetails').style.display= 'inline';
	}
}


function retrimentEmpDetails(id){
		document.forms[0].param.value = "retrimentAmtHome";
		document.forms[0].action = "retriment.htm?id="+id;
		document.forms[0].submit();
}

function clearTons(){
	$jq('#noofTons').val('');
}

function noOfTons(){
	var noofTons=$jq('#noofTons').val();
	var empType=$jq('#empType').val();
	if(empType==="C"){
		if(parseInt(noofTons)>3){
			alert("Your are Eligible upto 3 tons Only");
			$jq('#noofTons').val('');
		}
	}
	if(empType==="P"){
		if(parseInt(noofTons)>5){
			alert("Your are Eligible upto 5 tons Only");
			$jq('#noofTons').val('');
		}
	}
}

function noOFPeople(){
	var noOfPerson=$jq('#noOfPerson').val();
	if(parseInt(noOfPerson)>6){
		alert("Allow upto 6 members Only");
		$jq('#noOfPerson').val('');
	}
}

function totBenfitAmt(){
	var retrimentAmt=$jq('#retrimentAmt').val();
	var transportAmt=$jq('#transportAmt').val();
	var luggageAmt=$jq('#luggageAmt').val();
	if(retrimentAmt===null || retrimentAmt===""){
		retrimentAmt="0";
	}
	if(transportAmt===null  || transportAmt===""){
		transportAmt="0";
	}
	if(luggageAmt===null  || luggageAmt===""){
		luggageAmt="0";
	}
	var totAmt=parseInt(retrimentAmt)+parseInt(transportAmt)+parseInt(luggageAmt);
	$jq('#totAmt').val(totAmt);
}



function editRetrimentDetails(id,sfID,empName,empType,noOfPerson,retrimentDate,retrimentAmt,transportAmt,luggageAmt,noofTons,totAmt){
	$jq('#updateID').val(id);
	$jq('#sfID').val(sfID);
	$jq('#empName').val(empName);
	$jq('#empType').val(empType);
	$jq('#noOfPerson').val(noOfPerson);
	$jq('#retrimentDate').val(retrimentDate);
	$jq('#retrimentAmt').val(retrimentAmt);
	$jq('#transportAmt').val(transportAmt);
	$jq('#luggageAmt').val(luggageAmt);
	$jq('#noofTons').val(noofTons);
	$jq('#totAmt').val(totAmt);
}
function deleteRetrimentDetails(id){
	var errorMessage = "";
	var status = true;
	if (status) {
		var requestDetails = {
				"id" : id,
				"param" : "retrimentDelete"
			};
			$jq.ajax( {
				type : "POST",
				url : "retriment.htm?",
				data : requestDetails,
				cache : false,
				success : function(html) {
					$jq('#result').html(html);
					if ($jq('.success').is(':visible')) {
						clearRetrimentDetails();
					}else{
						alert("Deleted Sucessfully");
						document.forms[0].param.value = "retrimentHome";
						document.forms[0].action = "retriment.htm";
						document.forms[0].submit();
					}
				}
			});

	} else {
		alert(errorMessage);
	}
}

function submitRetrimentDetails(){
	alert("id value"+$jq('#updateID').val());
	var errorMessage = "";
	var status = true;
	if ($jq('#sfID').val()=='') {
		errorMessage += "Please Enter EmployeeID.\n";
		if(status){
		$jq('#sfID').focus();
		}
		status = false;
	}
	if ($jq('#empName').val()=='') {
		errorMessage += "Please Enter Employee Name.\n";
		if(status){
		$jq('#empName').focus();
		}
		status = false;
	}
	if ($jq('#empType').val()==='Select') {
		errorMessage += "Please Select Employee Type.\n";
		if(status){
		$jq('#empType').focus();
		}
		status = false;
	}
	if ($jq('#noOfPerson').val()=='') {
		errorMessage += "Please Enter No.Of Persons.\n";
		if(status){
		$jq('#noOfPerson').focus();
		}
		status = false;
	}
	if ($jq('#retrimentDate').val()=='') {
		errorMessage += "Please Select RetrimentDate.\n";
		if(status){
		$jq('#retrimentDate').focus();
		}
		status = false;
	}
	if ($jq('#retrimentAmt').val()=='') {
		errorMessage += "Please Enter Retriment Benfit Amount\n";
		if(status){
		$jq('#retrimentAmt').focus();
		}
		status = false;
	}
	if ($jq('#transportAmt').val()=='') {
		errorMessage += "Please Enter Transport Amount\n";
		if(status){
		$jq('#transportAmt').focus();
		}
		status = false;
	}
	if ($jq('#luggageAmt').val()=='') {
		errorMessage += "Please Enter Luggage Amount\n";
		if(status){
		$jq('#luggageAmt').focus();
		}
		status = false;
	}
	if ($jq('#noofTons').val()=='') {
		errorMessage += "Please Enter no of Tons Luggage \n";
		if(status){
		$jq('#noofTons').focus();
		}
		status = false;
	}
	if ($jq('#totAmt').val()=='') {
		errorMessage += "Please Enter totAmt  \n";
		if(status){
		$jq('#totAmt').focus();
		}
		status = false;
	}
	
	if (status) {
		var requestDetails = {
				"sfID" : $jq('#sfID').val(),
				"updateID" : $jq('#updateID').val(),
				"empName" : $jq('#empName').val(),
				"noofTons" : $jq('#noofTons').val(),
				"empType" : $jq('#empType').val(),
				"noOfPerson" : $jq('#noOfPerson').val(),
				"retrimentDate" : $jq('#retrimentDate').val(),
				"retrimentAmt" : $jq('#retrimentAmt').val(),
				"transportAmt" : $jq('#transportAmt').val(),
				"noOfPerson" : $jq('#noOfPerson').val(),
				"totAmt" : $jq('#totAmt').val(),
				"luggageAmt" : $jq('#luggageAmt').val(),
				"param" : "retrimentSave"
			};
			$jq.ajax( {
				type : "POST",
				url : "retriment.htm?",
				data : requestDetails,
				cache : false,
				success : function(html) {
					$jq('#result').html(html);
					if ($jq('.success').is(':visible')) {
						document.forms[0].param.value = "retrimentHome";
						document.forms[0].action = "retriment.htm";
						document.forms[0].submit();
					}else{
						clearRetrimentDetails();
						document.forms[0].param.value = "retrimentHome";
						document.forms[0].action = "retriment.htm";
						document.forms[0].submit();
					}
				}
			});

	} else {
		alert(errorMessage);
	}
}

function clearRetrimentDetails(){
	$jq('#empType').val('Select');
	$jq('#noOfPerson').val('');
	$jq('#retrimentDate').val('');
	$jq('#retrimentAmt').val('');
	$jq('#transportAmt').val('');
	$jq('#luggageAmt').val('');
	$jq('#totAmt').val('');
	$jq('#noofTons').val('');
	$jq('#updateID').val('');
	$jq('#sfID').val('');
	$jq('#empName').val('');
	
}

function changeRetrimentDetails(){

	var changeSfid= $jq("#changeSfid").val();
	
	if(changeSfid!==""){
	var requestDetails = {
		"changeSfid" : $jq("#changeSfid").val(),
		"param" : "changeEmployeeOne"
	};
	$jq.ajax( {
		type : "POST",
		url : "retriment.htm",
		data : requestDetails,
		cache : true,
		success : function(html) {
			document.forms[0].param.value = "retrimentHome";
			document.forms[0].action = "retriment.htm";
			document.forms[0].submit();
		}
	});
	}else{
		alert("Please Enter Employee ID And Search Again.");
		$jq("#changeSfid").val('');
		$jq('#changeSfid').focus();
	}
}
