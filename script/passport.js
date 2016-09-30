var passportSubmitType;

function selfFinance() {
	if ($jq('input:radio[name=selfFinanceFlag]:checked').val() == "Y") {
		$jq('#sourceOfAmount').val("");
		$jq('#sourceOfAmountDivNo').hide();
		$jq('#sourceOfAmountDiv').show();
	} else {	
		$jq('#sourceOfAmountDiv').hide();	
		$jq('#sourceOfAmountDivNo').show();
	}
}

function enableContractulaFlag() {
	if ($jq('input:radio[name=contractualFlag]:checked').val() == "Y") {
		$jq('#contractualDetailsDiv').show();
		$jq('#coPeriodFrom').val("");
		$jq('#coPeriodTo').val("");
	} else {	
		$jq('#contractualDetailsDiv').hide();	
	}
}


function supportingDetails() {
	$jq('#supportingDetailsDiv').show();
}

function enableFamilyMembers(){
	if ($jq('input:radio[name=familyMembersFlag]:checked').val() == "Y") {
		$jq('#familyMemberId').val("0");
		$jq('#familyDetailsDiv').hide();
	} else {
		$jq('#familyDetailsDiv').show();
		showFamilyMembers();
	}
}
function enablePassportDetails(){
	if ($jq('input:radio[name=passportPossessFlag]:checked').val() == "Y") {
		$jq('#passportDetailsDiv').show();
		getPassportDetailsList();
	} else {
		$jq('#passportType').val("0");
		$jq('#passportNumber').val("");
		$jq('#issueDate').val("");
		$jq('#validityDate').val("");
		$jq('#passportDetailsDiv').hide();
	}
}

function enableLostPassportDetails(){
	if ($jq('input:radio[name=passportLostFlag]:checked').val() == "Y") {
		$jq('#LostPassportDetailsDiv').show();
		getPassportDetailsList1();
	} else {
		$jq('#lostPassportType').val("0");
		$jq('#lostPassportNumber').val("");
		$jq('#ppissueDate').val("");
		$jq('#ppvalidityDate').val("");
		$jq('#LostPassportDetailsDiv').hide();
	}
}

function getPassportDetailsList(){
	document.getElementById("passportType").options.length = 1;
		for ( var i = 0; i < passPortTypeJson.length; i++) {
			var len = document.getElementById("passportType").options.length++;
			document.getElementById("passportType").options[len].text = passPortTypeJson[i].name;
			document.getElementById("passportType").options[len].value = passPortTypeJson[i].id;
		}
}
function getPassportDetailsList1(){
	document.getElementById("lostPassportType").options.length = 1;
		for ( var i = 0; i < passPortTypeJson.length; i++) {
			var len = document.getElementById("lostPassportType").options.length++;
			document.getElementById("lostPassportType").options[len].text = passPortTypeJson[i].name;
			document.getElementById("lostPassportType").options[len].value = passPortTypeJson[i].id;
		}
}

function showFamilyMembers(){
	$jq('#familyMemberId').val("");
	$jq('#familyMemberId').text("");
	for(i=0;i<familyMembersJson.length;i++){
		$jq('#familyMemberId').append($jq("<option></option>").attr("value",familyMembersJson[i].id).text(familyMembersJson[i].name));
		}
}

function resetPassportAndProceeding(){
	$jq('#familyMemberId').val("");
	$jq('#familyMemberId').text("");
	
	$jq('input:text').val("");
	$jq('select').val("0");
	$jq('textarea').val("");
	$jq("input:radio").attr("checked", false);
	$jq('#passportDetailsDiv').hide();
	$jq('#familyDetailsDiv').hide();
	$jq('#sourceOfAmountDiv').hide();
	
	$jq('#supportingDetailsDiv').hide();
	$jq('#LostPassportDetailsDiv').hide();
	$jq('#contractualDetailsDiv').hide();
	
	
	$jq('#sub').show();	
	$jq('#sourceOfAmountDivNo').hide();
	$jq('#sfidName').html('');
	$jq('#sfidDesig').html('');
	$jq('#sfidAddress').html('');
	
	$jq('#familyMemberId').val("");
	$jq('#familyMemberId').text("");
	nodeID="";
}
function managePassportAndProceeding(){
	var errorMessage = "";
	var status = true;
	var selectedMembers="";

	var i=0;
	if ($jq("#sfid").val() == "") {
		errorMessage += "Please enter SFID.\n";
		if (status) {
			status = false;
			$jq("#sfid").focus();
		}
	}
	if (!$jq('input:radio[name=vigilanceFlag]').is(':checked')) {
		errorMessage += "Please select Vigilence.\n";
		if (status) {
			status = false;
			$jq("#vigilanceFlag").focus();
		}
	}
	if (!$jq('input:radio[name=passportLostFlag]').is(':checked')) {
		errorMessage += "Please select passport lost flag.\n";
		if (status) {
			status = false;
		}
	}
	if (!$jq('input:radio[name=passportPossessFlag]').is(':checked')) {
		errorMessage += "Please select possession of passport.\n";
		if (status) {
			status = false;
			$jq("#passportPossessFlag").focus();
		}
	}
	if (!$jq('input:radio[name=detailsFlag]').is(':checked')) {
		errorMessage += "Please select Details of Father/Husband/Guardian.\n";
		if (status) {
			status = false;
			$jq("#detailsFlag").focus();
		}
	}
	if ($jq("#departureDate").val() == "") {
		errorMessage += "Please enter Departure Date.\n";
		if (status) {
			status = false;
			$jq("#departureDate").focus();
		}
	}
	else {
		var sysdate = new Date();
		var dodDate = $jq("#departureDate").val();
		var date2 = new Date(dodDate.split("-")[2], getMonthID(dodDate
				.split("-")[1]) - 1, dodDate.split("-")[0], 0, 0, 1, 0)
		if (sysdate > date2) {
			errorMessage += "Date of Departure can not be Previous or Present Date.\n";
			if (status) {
				status = false;
				$jq("#departureDate").focus();
			}
		}
	}
	if ($jq("#returnDate").val() == "") {
		errorMessage += "Please enter Return Date.\n";
		if (status) {
			status = false;
			$jq("#returnDate").focus();
		}
	}
	if ($jq("#duration").val() == "") {
		errorMessage += "Please enter Duration Of Stay.\n";
		if (status) {
			status = false;
			$jq("#duration").focus();
		}
	}
	if ($jq("#purpose").val() == "") {
		errorMessage += "Please enter purpose Of Visit.\n";
		if (status) {
			status = false;
			$jq("#purpose").focus();
		}
	}
	if (!$jq('input:radio[name=familyMembersFlag]').is(':checked')) {
		errorMessage += "Please select whether going alone or with Family.\n";
		if (status) {
			status = false;
			$jq("#familyMembersFlag").focus();
		}
	}
	if ($jq("#spendingAmount").val() == "") {
		errorMessage += "Please enter amount to be Spent(approx.).\n";
		if (status) {
			status = false;
			$jq("#spendingAmount").focus();
		}
	}
	if (!$jq('input:radio[name=selfFinanceFlag]').is(':checked')) {
		errorMessage += "Please select trip Financed by Self or not.\n";
		if (status) {
			status = false;
			$jq("#selfFinanceFlag").focus();
		}
	}
	if ($jq("#passportDetailsDiv").is(':visible')) {
	    if ($jq("#passportType").val() == "0") {
		errorMessage += "Please select Passport Type.\n";
		if (status) {
			$jq('#passportType').focus();
			status = false;
		}
	    }
	    if ($jq("#passportNumber").val() == "") {
			errorMessage += "Please enter Passport Number.\n";
			if (status) {
				$jq('#passportNumber').focus();
				status = false;
			}
		} 
	    if ($jq("#issueDate").val() == "") {
			errorMessage += "Please enter Issue Date.\n";
			if (status) {
				$jq('#issueDate').focus();
				status = false;
			}
		}
	    if ($jq("#validityDate").val() == "") {
			errorMessage += "Please enter Validity Date.\n";
			if (status) {
				$jq('#validityDate').focus();
				status = false;
			}
		}
	}
	if ($jq("#LostPassportDetailsDiv").is(':visible')) {
	    if ($jq("#lostPassportType").val() == "0") {
		errorMessage += "Please select Lost Passport Type.\n";
		if (status) {
			$jq('#lostPassportType').focus();
			status = false;
		}
	    }
	    if ($jq("#lostPassportNumber").val() == "") {
			errorMessage += "Please enter Lost Passport Number.\n";
			if (status) {
				$jq('#lostPassportNumber').focus();
				status = false;
			}
		} 
	    if ($jq("#ppissueDate").val() == "") {
			errorMessage += "Please Select Lost Passport Issue Date.\n";
			if (status) {
				$jq('#ppissueDate').focus();
				status = false;
			}
		}
	    if ($jq("#ppvalidityDate").val() == "") {
			errorMessage += "Please Select Lost Passport Validity Date.\n";
			if (status) {
				$jq('#ppvalidityDate').focus();
				status = false;
			}
		}

	}
	if ($jq("#supportingDetailsDiv").is(':visible')) {

	    if ($jq("#supportName").val() == "") {
			errorMessage += "Please enter Name.\n";
			if (status) {
				$jq('#supportName').focus();
				status = false;
			}
		} 
	    if ($jq("#supportOccupation").val() == "") {
			errorMessage += "Please enter Occupation.\n";
			if (status) {
				$jq('#supportOccupation').focus();
				status = false;
			}
		}
	    if ($jq("#supportAddress").val() == "") {
			errorMessage += "Please enter Address.\n";
			if (status) {
				$jq('#supportAddress').focus();
				status = false;
			}
		}
	}	
	if ($jq("#sourceOfAmountDiv").is(':visible')) {
		if ($jq("#sourceOfAmount").val() == "") {
			errorMessage += "Please enter source of amount.\n";
			if (status) {
				$jq('#sourceOfAmount').focus();
				status = false;
			}
		}
	}//start
	if (!$jq('input:radio[name=certifyStatus]').is(':checked')) {
		errorMessage += "Please select certify status.\n";
		if (status) {
			status = false;
			$jq("#certifyStatus").focus();
		}
	}
	
	if (!$jq('input:radio[name=settleFlag]').is(':checked')) {
		errorMessage += "Please select settling status.\n";
		if (status) {
			status = false;
			$jq("#settleFlag").focus();
		}
	}

	if (!$jq('input:radio[name=idCardFlag]').is(':checked')) {
		errorMessage += "Please select ID Card Status.\n";
		if (status) {
			status = false;
			$jq("#idCardFlag").focus();
		}
	}
	if (!$jq('input:radio[name=contractualFlag]').is(':checked')) {
		errorMessage += "Please select Contractual Flag.\n";
		if (status) {
			status = false;
		}
	}
//ends
	
	if ($jq("#sourceOfAmountDivNo").is(':visible')) {
		    if ($jq("#lenderName").val() == "") {
				errorMessage += "Please enter Name.\n";
				if (status) {
					$jq('#lenderName').focus();
					status = false;
				}
			}
		    if ($jq("#nationality").val() == "") {
				errorMessage += "Please enter Nationality.\n";
				if (status) {
					$jq('#lenderName').focus();
					status = false;
				}
			}
		    if ($jq("#lenderRelationship").val() == "") {
				errorMessage += "Please enter Relationship.\n";
				if (status) {
					$jq('#lenderRelationship').focus();
					status = false;
				}
			}

		}
    if ($jq("#countriesToVisit").val() == "") {
		errorMessage += "Please enter countries to be Visited.\n";
		if (status) {
			$jq('#validityDate').focus();
			status = false;
		}
	}
    if ($jq("#familyDetailsDiv").is(':visible')) {
		if ($jq("#familyMemberId").val() == null) {
			errorMessage += "Please select Family Members.\n";
			if (status) {
				status = false;
				$jq("#familyMemberId").focus();
			}
		}
		else{
			$jq("#familyMemberId").each(function() {
				selectedMembers += $jq(this).val() + ",";
			});
		}
	}
    if(passportSubmitType != "passport & abroad"){
  
		if (status) {
			var requestDetails = {
				"id":nodeID,
				"relations" : $jq("#relations").val(),
				"employmentDetails" : $jq("#employmentDetails").val(),
				"vigilanceFlag":$jq('input:radio[name=vigilanceFlag]:checked').val(),
				"departureDate" : $jq("#departureDate").val(),
				"returnDate" : $jq("#returnDate").val(),
				"duration" : $jq("#duration").val(),
				"purpose" : $jq("#purpose").val(),
				"spendingAmount" : $jq("#spendingAmount").val(),
				"passportType"   : $jq("#passportType").val(),
				"passportNumber" : $jq("#passportNumber").val(),
				"issueDate" : $jq("#issueDate").val(),
				"validityDate" :$jq("#validityDate").val(),
				"familyMemberId":selectedMembers,
				"sourceOfAmount":$jq("#sourceOfAmount").val(),
				"lenderName":$jq("#lenderName").val(),
				"nationality":$jq("#nationality").val(),
				"lenderRelationship":$jq("#lenderRelationship").val(),
				"countriesToVisit":$jq("#countriesToVisit").val(),
				"returnDate":$jq("#returnDate").val(),
				"passportPossessFlag":$jq('input:radio[name=passportPossessFlag]:checked').val(),
				"familyMembersFlag":$jq('input:radio[name=familyMembersFlag]:checked').val(),
				"selfFinanceFlag":$jq('input:radio[name=selfFinanceFlag]:checked').val(),
				"remarks":$jq("#remarks").val(),
				"sfID":$jq("#sfid").val().toUpperCase(),
				"verificationType":"N",
				
				"detailsFlag" : $jq('input:radio[name=detailsFlag]:checked').val(),
				"supportName" : $jq("#supportName").val(),
				"supportOccupation":$jq("#supportOccupation").val(),
				"supportAddress" : $jq("#supportAddress").val(),
				"passportLostFlag" :$jq('input:radio[name=passportLostFlag]:checked').val(),
				"lostPassportType" : $jq("#lostPassportType").val(),
				"lostPassportNumber" : $jq("#lostPassportNumber").val(),
				"ppissueDate" : $jq("#ppissueDate").val(),
				"ppvalidityDate"   : $jq("#ppvalidityDate").val(),
				"hospitalFlag" : $jq('input:radio[name=hospitalFlag]:checked').val(),
				"prevCountryDetails" : $jq("#prevCountryDetails").val(),
				"idCardFlag" :$jq('input:radio[name=idCardFlag]:checked').val(),
				"settleFlag":$jq('input:radio[name=settleFlag]:checked').val(),
				"contractualFlag":$jq('input:radio[name=contractualFlag]:checked').val(),
				"coPeriodFrom":$jq("#coPeriodFrom").val(),
				"coPeriodTo":$jq("#coPeriodTo").val(),
				"certifyStatus":$jq('input:radio[name=certifyStatus]:checked').val(),
				
				"param" : "save"
			};
			$jq.ajax( {
				type : "POST",
				url : "passport.htm",
				data : requestDetails,
				cache : false,
				success : function(html) {
					$jq('#resultPassport').html(html);
					$jq("#ajaxBusy").remove();
					if ($jq('.success').is(':visible')) {
						resetPassportAndProceeding();
					}
				}
			});
		} else {
			alert(errorMessage);
		}
    }
}

function getSFIDDetails(){
	var sID= $jq("#sfid").val(); 
	resetPassportAndProceeding()
	document.getElementById('sfid').value=sID;
	var status=true;
	 if ($jq("#sfid").val() == "") 
			status = false;
		
	if(status){
		$jq.ajax( {
			type : "GET",
			url : "passport.htm",
			data : "param=sfidDetails&sfid="
				+ sID.toUpperCase(),
			success : function(html) {
			$jq('#result').html(html);
		
			}
		});
	}
}

function deletePassProceedDetails(id){
	var del = confirm("Do you want to delete this item?");
	if (del == true) {	
		$jq.ajax( {
			type : "POST",
			url : "passport.htm",
			data : "param=delete&beanType=PassportApplicationDetailsDTO&id="
				+ id,
			success : function(html) {
			$jq('#resultPassport').html(html);
			resetPassportAndProceeding();
			}
		});
	}

}

function editPassProceed(requestID,sfid,passportType,relations,vigilanceFlag,passportNumber,issueDate,returnDate,validityDate,duration,
		purpose,familyMemberId,remarks,spendingAmount,sourceOfAmount,lenderName,nationality,lenderRelationship,departureDate,countriesToVisit,
		employmentDetails,passportPossessFlag,familyMembersFlag,selfFinanceFlag,editType,verificationType,
		detailsFlag,supportName,supportOccupation,supportAddress,passportLostFlag,lostPassportType,lostPassportNumber,ppissueDate,
		ppvalidityDate,hospitalFlag,prevCountryDetails,idCardFlag,settleFlag,contractualFlag,coPeriodFrom,coPeriodTo,certifyStatus){
	
	
	if(editType=="PassportProceedingDetailsList"){
		$jq('#sfid').val(sfid);
		getSFIDDetails()
		
	}
	nodeID=requestID;	
	$jq('#relations').val(relations);
	$jq('#employmentDetails').val(employmentDetails);
	$jq("input:radio[name=vigilanceFlag]").filter("[value="+vigilanceFlag+"]").attr("checked","checked");
	$jq("input:radio[name=passportPossessFlag]").filter("[value="+passportPossessFlag+"]").attr("checked","checked");
	if(passportPossessFlag=="Y"){
		$jq('#passportDetailsDiv').show();
		getPassportDetailsList();
		$jq('#passportType').val(passportType);
		$jq('#passportNumber').val(passportNumber);
		$jq('#issueDate').val(issueDate);
		$jq('#validityDate').val(validityDate);
	}
	$jq('#departureDate').val(departureDate);
	$jq('#returnDate').val(returnDate);
	$jq('#duration').val(duration);
	$jq('#purpose').val(purpose);
	$jq("input:radio[name=familyMembersFlag]").filter("[value="+familyMembersFlag+"]").attr("checked","checked");
	if(familyMembersFlag=="N"){
		$jq('#familyDetailsDiv').show();
		showFamilyMembers();
		//var familyMemLen=$jq('#familyMemberId').length;
		//var familyMemLen=document.getElementById('familyMemberId').length;

		var membersList = familyMemberId.split(',');
		
		for ( var i = 0; i < familyMembersJson.length; i++) {
		for ( var j = 0; j < membersList.length; j++) {
			if(membersList[j]==familyMembersJson[i].id){
				$jq('#familyMemberId').find('option[value='+membersList[j]+']').attr("selected","selected")
				//$jq('#familyMemberId').find('option').attr("selected","selected");
				
			}
		}	
		}
	}
	$jq('#spendingAmount').val(spendingAmount);
	$jq("input:radio[name=selfFinanceFlag]").filter("[value="+selfFinanceFlag+"]").attr("checked","checked");
	if(selfFinanceFlag=="Y"){
		$jq('#sourceOfAmountDivNo').hide();
		$jq('#sourceOfAmountDiv').show();
		$jq('#sourceOfAmount').val(sourceOfAmount);
	}else if(selfFinanceFlag=="N"){
		$jq('#sourceOfAmountDiv').hide();	
		$jq('#sourceOfAmountDivNo').show();
		$jq('#lenderName').val(lenderName);
		$jq('#nationality').val(nationality);
		$jq('#lenderRelationship').val(lenderRelationship);
	}
	$jq('#countriesToVisit').val(countriesToVisit);
	$jq('#remarks').val(remarks);
	
	
	if(editType=="nocPassportResult" || editType=="PassportProceedingDetailsList" ){
		if(verificationType=="Y")
		$jq('#sub').hide();	
		
	}
	
	
	//edit starts

	
	$jq("input:radio[name=passportLostFlag]").filter("[value="+passportLostFlag+"]").attr("checked","checked");
	if(passportLostFlag=="Y"){
		$jq('#LostPassportDetailsDiv').show();
		getPassportDetailsList1();
		$jq('#lostPassportType').val(lostPassportType);
		$jq('#lostPassportNumber').val(lostPassportNumber);
		$jq('#ppissueDate').val(ppissueDate);
		$jq('#ppvalidityDate').val(ppvalidityDate);
	}
	
	$jq("input:radio[name=detailsFlag]").filter("[value="+detailsFlag+"]").attr("checked","checked");
	if(detailsFlag=="F" || detailsFlag=="H" || detailsFlag=="G"){
		$jq('#supportingDetailsDiv').show();
		$jq('#supportName').val(supportName);
		$jq('#supportOccupation').val(supportOccupation);
		$jq('#supportAddress').val(supportAddress);
	}
	$jq("input:radio[name=hospitalFlag]").filter("[value="+hospitalFlag+"]").attr("checked","checked");
	$jq('#prevCountryDetails').val(prevCountryDetails);
	
	$jq("input:radio[name=idCardFlag]").filter("[value="+idCardFlag+"]").attr("checked","checked");
	$jq("input:radio[name=settleFlag]").filter("[value="+settleFlag+"]").attr("checked","checked");
	$jq("input:radio[name=contractualFlag]").filter("[value="+contractualFlag+"]").attr("checked","checked");
	if(detailsFlag=="Y"){
		$jq('#contractualDetailsDiv').show();
		$jq('#coPeriodFrom').val(coPeriodFrom);
		$jq('#coPeriodTo').val(coPeriodTo);
	}
	$jq("input:radio[name=certifyStatus]").filter("[value="+certifyStatus+"]").attr("checked","checked");
	
	//edit ends
}


//  NOC FOR PASSPORT STARTS


function saveNOCforPassport(){
	var status=true;
	var errorMessage = "";
	var selectedMembers="";
	if($jq("#sfID").val()=='')
		errorMessage+='Please Enter SFID\n';
	if($jq("#passportAppType").val()==''){
		errorMessage+='Please Enter NOC Type\n';
		if($jq("#adminNoteDt").val()==''){
			errorMessage+='Please Enter Admin Date\n';
			status=false;
		}
		if($jq("#idCertificateDt").val()==''){
			errorMessage+='Please Enter Id Certificate Date\n';
			status=false;
		}
		if($jq("#receivedDt").val()==''){
			errorMessage+='Please Enter Date Of Issue To Employee\n';
			status=false;
		}
	}
	if(errorMessage==""){
	if($jq('#passportAppType').val()=="passport"){
			if($jq("#letterDate").val()==''){
				errorMessage+='Please Enter letterDate\n';
				status=false;
			}
			if($jq("#adminNoteDt").val()==''){
				errorMessage+='Please Enter Admin Date\n';
				status=false;
			}
			if($jq("#idCertificateDt").val()==''){
				errorMessage+='Please Enter Id Certificate Date\n';
				status=false;
			}
			if($jq("#receivedDt").val()==''){
				errorMessage+='Please Enter Date Of Issue To Employee\n';
				status=false;
			}
		if (status) {
			var requestDetails = {
			    "id":nodeID,	
				"sfID":$jq("#sfID").val().toUpperCase(),
				"letterDesc":$jq("#letterDesc").val(),
				"letterDate":$jq("#letterDate").val(),
				"adminNoteNo":$jq("#adminNoteNo").val(),
				"adminNoteDt":$jq("#adminNoteDt").val(),
				
				"idCertificateNo":$jq("#idCertificateNo").val(),
				"idCertificateDt":$jq("#idCertificateDt").val(),
				
				"receivedDt":$jq("#receivedDt").val(),
				"passportAppType":$jq("#passportAppType").val(),
				"param" : "submitPassportDetailsforNOC"
			};
			$jq.ajax( {
				type : "POST",
				url : "passport.htm",
				data : requestDetails,
				cache : false,
				success : function(html) {
					$jq('#result1').html(html);
					nodeID="";
					if ($jq('.success').is(':visible')) {
						clearPassPortApplication();
						
					}
				}
			});
		}	else{
			alert(errorMessage);
		}
	}
	else if($jq('#passportAppType').val()=="passport & abroad" && $jq('#passportProceedDetailsDiv').is(':visible') ){
		var propReturnFlag="";
		var visualFlag="";
		var check=confirm("No Updations can be done for Application Form after Submitting  \n \t Do you want to re-check form details Once again?");
		if(!check){

			
			if (!$jq('input:radio[name=vigilanceFlag]').is(':checked')) {
				errorMessage += "Please select Vigilence.\n";
				if (status) {
					status = false;
					$jq("#vigilanceFlag").focus();
				}
			}
			if (!$jq('input:radio[name=passportPossessFlag]').is(':checked')) {
				errorMessage += "Please select possession of passport.\n";
				if (status) {
					status = false;
					$jq("#passportPossessFlag").focus();
				}
			}
			if ($jq("#departureDate").val() == "") {
				errorMessage += "Please enter Departure Date.\n";
				if (status) {
					status = false;
					$jq("#departureDate").focus();
				}
			}
			else {
				var sysdate = new Date();
				var dodDate = $jq("#departureDate").val();
				var date2 = new Date(dodDate.split("-")[2], getMonthID(dodDate
						.split("-")[1]) - 1, dodDate.split("-")[0], 0, 0, 1, 0)
				if (sysdate > date2) {
					errorMessage += "Date of Departure can not be Previous or Present Date.\n";
					if (status) {
						status = false;
						$jq("#departureDate").focus();
					}
				}
			}
			
			if ($jq("#returnDate").val() == "") {
				errorMessage += "Please enter Return Date.\n";
				if (status) {
					status = false;
					$jq("#returnDate").focus();
				}
			}
			if ($jq("#duration").val() == "") {
				errorMessage += "Please enter Duration Of Stay.\n";
				if (status) {
					status = false;
					$jq("#duration").focus();
				}
			}
			if ($jq("#purpose").val() == "") {
				errorMessage += "Please enter purpose Of Visit.\n";
				if (status) {
					status = false;
					$jq("#purpose").focus();
				}
			}
			if (!$jq('input:radio[name=familyMembersFlag]').is(':checked')) {
				errorMessage += "Please select whether going alone or with Family.\n";
				if (status) {
					status = false;
					$jq("#familyMembersFlag").focus();
				}
			}
			if ($jq("#spendingAmount").val() == "") {
				errorMessage += "Please enter amount to be Spent(approx.).\n";
				if (status) {
					status = false;
					$jq("#spendingAmount").focus();
				}
			}
			if (!$jq('input:radio[name=selfFinanceFlag]').is(':checked')) {
				errorMessage += "Please select trip Financed by Self or not.\n";
				if (status) {
					status = false;
					$jq("#selfFinanceFlag").focus();
				}
			}
			if ($jq("#passportDetailsDiv").is(':visible')) {
			    if ($jq("#passportType").val() == "0") {
				errorMessage += "Please select Passport Type.\n";
				if (status) {
					$jq('#passportType').focus();
					status = false;
				}
			}
			    if ($jq("#passportNumber").val() == "") {
					errorMessage += "Please enter Passport Number.\n";
					if (status) {
						$jq('#passportNumber').focus();
						status = false;
					}
				} 
			    if ($jq("#issueDate").val() == "") {
					errorMessage += "Please enter Issue Date.\n";
					if (status) {
						$jq('#issueDate').focus();
						status = false;
					}
				}
			    if ($jq("#validityDate").val() == "") {
					errorMessage += "Please enter Validity Date.\n";
					if (status) {
						$jq('#validityDate').focus();
						status = false;
					}
				}

			}
			if ($jq("#sourceOfAmountDiv").is(':visible')) {
					  if ($jq("#sourceOfAmount").val() == "") {
							errorMessage += "Please enter source of amount.\n";
							if (status) {
								$jq('#sourceOfAmount').focus();
								status = false;
							}
						}
				}
				if ($jq("#sourceOfAmountDivNo").is(':visible')) {
				    if ($jq("#lenderName").val() == "") {
						errorMessage += "Please enter Name.\n";
						if (status) {
							$jq('#lenderName').focus();
							status = false;
						}
					}
				    if ($jq("#nationality").val() == "") {
						errorMessage += "Please enter Nationality.\n";
						if (status) {
							$jq('#lenderName').focus();
							status = false;
						}
					}
				    if ($jq("#lenderRelationship").val() == "") {
						errorMessage += "Please enter Relationship.\n";
						if (status) {
							$jq('#lenderRelationship').focus();
							status = false;
						}
					}
				}
		    if ($jq("#countriesToVisit").val() == "") {
				errorMessage += "Please enter countries to be Visited.\n";
				if (status) {
					$jq('#validityDate').focus();
					status = false;
				}
			}
		    if ($jq("#familyDetailsDiv").is(':visible')) {
				if ($jq("#familyMemberId").val() == null) {
					errorMessage += "Please select Family Members.\n";
					if (status) {
						status = false;
						$jq("#familyMemberId").focus();
					}
				}
				else{
					$jq("#familyMemberId").each(function() {
						selectedMembers += $jq(this).val() + ",";
					});
				}
			}
			//start
			if (!$jq('input:radio[name=detailsFlag]').is(':checked')) {
				errorMessage += "Please select Details of Father/Husband/Guardian.\n";
				if (status) {
					status = false;
					$jq("#detailsFlag").focus();
				}
			}

				if ($jq("#LostPassportDetailsDiv").is(':visible')) {
			    if ($jq("#lostPassportType").val() == "0") {
				errorMessage += "Please select Lost Passport Type.\n";
				if (status) {
					$jq('#lostPassportType').focus();
					status = false;
				}
			    }
			    if ($jq("#lostPassportNumber").val() == "") {
					errorMessage += "Please enter Lost Passport Number.\n";
					if (status) {
						$jq('#lostPassportNumber').focus();
						status = false;
					}
				} 
			    if ($jq("#ppissueDate").val() == "") {
					errorMessage += "Please Select Lost Passport Issue Date.\n";
					if (status) {
						$jq('#ppissueDate').focus();
						status = false;
					}
				}
			    if ($jq("#ppvalidityDate").val() == "") {
					errorMessage += "Please Select Lost Passport Validity Date.\n";
					if (status) {
						$jq('#ppvalidityDate').focus();
						status = false;
					}
				}

			}	if ($jq("#supportingDetailsDiv").is(':visible')) {

			    if ($jq("#supportName").val() == "") {
					errorMessage += "Please enter Name.\n";
					if (status) {
						$jq('#supportName').focus();
						status = false;
					}
				} 
			    if ($jq("#supportOccupation").val() == "") {
					errorMessage += "Please enter Occupation.\n";
					if (status) {
						$jq('#supportOccupation').focus();
						status = false;
					}
				}
			    if ($jq("#supportAddress").val() == "") {
					errorMessage += "Please enter Address.\n";
					if (status) {
						$jq('#supportAddress').focus();
						status = false;
					}
				}
			}
			
			if (!$jq('input:radio[name=certifyStatus]').is(':checked')) {
				errorMessage += "Please select certify status.\n";
				if (status) {
					status = false;
					$jq("#certifyStatus").focus();
				}
			}
			
			if (!$jq('input:radio[name=settleFlag]').is(':checked')) {
				errorMessage += "Please select settling status.\n";
				if (status) {
					status = false;
					$jq("#settleFlag").focus();
				}
			}

			if (!$jq('input:radio[name=idCardFlag]').is(':checked')) {
				errorMessage += "Please select ID Card Status.\n";
				if (status) {
					status = false;
					$jq("#idCardFlag").focus();
				}
			}//ends
			
	/*	passportSubmitType="passport & abroad";
		managePassportAndProceeding()
		if ($jq("#familyDetailsDiv").is(':visible')) {
			if ($jq("#familyMemberId").val() == null) {
				errorMessage += "Please select Family Members.\n";
				if (status) {
					status = false;
					$jq("#familyMemberId").focus();
				}
			}
			else{
				$jq("#familyMemberId").each(function() {
					selectedMembers += $jq(this).val() + ",";
				});
			}
		}
		
		*/
		
		if($jq('#propReturnFlag').is(':checked')==true){
			propReturnFlag=$jq('#propReturnFlag').val();
		}else
		{
			propReturnFlag="";
		}
		if($jq('#visualFlag').is(':checked')==true){
			visualFlag=$jq('#visualFlag').val();
		}else
		{
			visualFlag="";
		}
		
		if (status) {
			var requestDetails = {
			    "id":nodeID,
			    "relations" : $jq("#relations").val(),
				"employmentDetails" : $jq("#employmentDetails").val(),
				"vigilanceFlag":$jq('input:radio[name=vigilanceFlag]:checked').val(),
				"departureDate" : $jq("#departureDate").val(),
				"returnDate" : $jq("#returnDate").val(),
				"duration" : $jq("#duration").val(),
				"purpose" : $jq("#purpose").val(),
				"spendingAmount" : $jq("#spendingAmount").val(),
				"passportType"   : $jq("#passportType").val(),
				"passportNumber" : $jq("#passportNumber").val(),
				"issueDate" : $jq("#issueDate").val(),
				"validityDate" :$jq("#validityDate").val(),
				"familyMemberId":selectedMembers,
				"sourceOfAmount":$jq("#sourceOfAmount").val(),
				"lenderName":$jq("#lenderName").val(),
				"nationality":$jq("#nationality").val(),
				"lenderRelationship":$jq("#lenderRelationship").val(),
				"countriesToVisit":$jq("#countriesToVisit").val(),
				"returnDate":$jq("#returnDate").val(),
				"passportPossessFlag":$jq('input:radio[name=passportPossessFlag]:checked').val(),
				"familyMembersFlag":$jq('input:radio[name=familyMembersFlag]:checked').val(),
				"selfFinanceFlag":$jq('input:radio[name=selfFinanceFlag]:checked').val(),
				"remarks":$jq("#remarks").val(),
				
				"detailsFlag" : $jq('input:radio[name=detailsFlag]:checked').val(),
				"supportName" : $jq("#supportName").val(),
				"supportOccupation":$jq("#supportOccupation").val(),
				"supportAddress" : $jq("#supportAddress").val(),
				"passportLostFlag" :$jq('input:radio[name=passportLostFlag]:checked').val(),
				"lostPassportType" : $jq("#lostPassportType").val(),
				"lostPassportNumber" : $jq("#lostPassportNumber").val(),
				"ppissueDate" : $jq("#ppissueDate").val(),
				"ppvalidityDate"   : $jq("#ppvalidityDate").val(),
				"hospitalFlag" : $jq('input:radio[name=hospitalFlag]:checked').val(),
				"prevCountryDetails" : $jq("#prevCountryDetails").val(),
				"idCardFlag" :$jq('input:radio[name=idCardFlag]:checked').val(),
				"settleFlag":$jq('input:radio[name=settleFlag]:checked').val(),
				"contractualFlag":$jq('input:radio[name=contractualFlag]:checked').val(),
				"coPeriodFrom":$jq("#coPeriodFrom").val(),
				"coPeriodTo":$jq("#coPeriodTo").val(),
				"certifyStatus":$jq('input:radio[name=certifyStatus]:checked').val(),
				
				"propReturnFlag":propReturnFlag,
				"visualFlag":visualFlag,
			    
				"sfID":$jq("#sfID").val().toUpperCase(),
				"adminNoteNo":$jq("#adminNoteNo").val(),
				"adminNoteDt":$jq("#adminNoteDt").val(),
				"idCertificateNo":$jq("#idCertificateNo").val(),
				"idCertificateDt":$jq("#idCertificateDt").val(),
				"receivedDt":$jq("#receivedDt").val(),
				"passportAppType":$jq("#passportAppType").val(),
				"verificationType":"Y",
				
				"param" : "submitPassportDetailsforNOC"
			};
			$jq.ajax( {
				type : "POST",
				url : "passport.htm",
				data : requestDetails,
				cache : false,
				success : function(html) {
				passportSubmitType="";
					$jq('#result1').html(html);
					nodeID="";
					if ($jq('.success').is(':visible')) {
						clearPassPortApplication();
						
					}
				}
			});
		}	else {
			alert(errorMessage);
		}
		}
	}
	else if ($jq('#passportAppType').val()=="passport & abroad"){
		if (status) {
			var requestDetails = {
			    "id":nodeID,	
				"sfID":$jq("#sfID").val().toUpperCase(),
				"letterDesc":"",
				"letterDate":"",
				"adminNoteNo":$jq("#adminNoteNo").val(),
				"adminNoteDt":$jq("#adminNoteDt").val(),
				"idCertificateNo":$jq("#idCertificateNo").val(),
				"idCertificateDt":$jq("#idCertificateDt").val(),
				"receivedDt":$jq("#receivedDt").val(),
				"passportAppType":$jq("#passportAppType").val(),
				"param" : "submitPassportDetailsforNOC",
				"type" : "submitPassportDetailsforNOC"
			};
			$jq.ajax( {
				type : "POST",
				url : "passport.htm",
				data : requestDetails,
				cache : false,
				success : function(html) {
					$jq('#result1').html(html);
					nodeID="";
					if ($jq('.success').is(':visible')) {
						clearPassPortApplication();
						
					}
				}
			});
		}	
	}
	}else{
		alert(errorMessage);
	}
}
function editNOCforPPDetails(id,sfID,passportType,letterDesc,adminNoteNo,adminNoteDt,letterDate,receivedDt,idCertificateNo,idCertificateDt){
	nodeID=id;
	if(passportType == "passport"){
		$jq('#letterDetailsDiv').show();
		$jq('#passportProceedDetailsDiv').hide();
		
		$jq('#letterDesc').val(letterDesc);
		$jq('#letterDate').val(letterDate);
	}
	else if(passportType == "passport & abroad"){
		$jq('#letterDetailsDiv').hide();
		$jq('#passportProceedDetailsDiv').hide();
		
		$jq('#letterDesc').val("");
		$jq('#letterDate').val("");
	}
	$jq('#sfID').val(sfID);
	$jq('#adminNoteNo').val(adminNoteNo);
	$jq('#adminNoteDt').val(adminNoteDt);
	$jq('#idCertificateNo').val(idCertificateNo);
	$jq('#idCertificateDt').val(idCertificateDt);
	$jq('#receivedDt').val(receivedDt);
	$jq('#passportAppType').val(passportType);
	
	

}

function deletePassportDetailsforNOC(id){
	var del = confirm("Do you want to delete this item?");
	if (del == true) {	
		$jq.ajax( {
			type : "POST",
			url : "passport.htm",
			data : "param=deletePassportDetailsforNOC&beanType=PassportApplicationDTO&id="
				+ id,
			success : function(html) {
			$jq('#result1').html(html);
			resetPassportAndProceeding();
			}
		});
	}
}

function getLetterDetailsView(){
	nodeID="";
	if($jq('#sfID').val() != ""){
	   if($jq('#passportAppType').val() == "passport"){
		   $jq('#letterDetailsDiv').show();
		   $jq('#passportProceedDetailsDiv').hide();
	   }
	   else if($jq('#passportAppType').val() == "passport & abroad"){
		   $jq('#letterDetailsDiv').hide();
		   $jq('#passportProceedDetailsDiv').show();
		   getSFIDDetails1();
		   
		     for(var i=0;i<sfidPassPortDetailsJson.length;i++){
			    if(i==0){
			    	   var issDt=createDDMMYYY(sfidPassPortDetailsJson[0].issueDate);
			           editPassProceed(sfidPassPortDetailsJson[0].requestID,$jq('#sfID').val(),sfidPassPortDetailsJson[0].passportType,
					   sfidPassPortDetailsJson[0].relations,sfidPassPortDetailsJson[0].vigilanceFlag,sfidPassPortDetailsJson[0].passportNumber,
					   sfidPassPortDetailsJson[0].issueDate,sfidPassPortDetailsJson[0].returnDate,sfidPassPortDetailsJson[0].validityDate,
					   sfidPassPortDetailsJson[0].duration,sfidPassPortDetailsJson[0].purpose,sfidPassPortDetailsJson[0].familyMemberId,
					   sfidPassPortDetailsJson[0].remarks,sfidPassPortDetailsJson[0].spendingAmount,sfidPassPortDetailsJson[0].sourceOfAmount,
					   sfidPassPortDetailsJson[0].lenderName,sfidPassPortDetailsJson[0].nationality,sfidPassPortDetailsJson[0].lenderRelationship,
					   sfidPassPortDetailsJson[0].departureDate,sfidPassPortDetailsJson[0].countriesToVisit,sfidPassPortDetailsJson[0].employmentDetails,
					   sfidPassPortDetailsJson[0].passportPossessFlag,sfidPassPortDetailsJson[0].familyMembersFlag,sfidPassPortDetailsJson[0].selfFinanceFlag,
					   'nocPassportResult',sfidPassPortDetailsJson[0].verificationType,
					   sfidPassPortDetailsJson[0].detailsFlag,sfidPassPortDetailsJson[0].supportName,sfidPassPortDetailsJson[0].supportOccupation,
					   sfidPassPortDetailsJson[0].supportAddress,sfidPassPortDetailsJson[0].passportLostFlag,sfidPassPortDetailsJson[0].lostPassportType,
					   sfidPassPortDetailsJson[0].lostPassportNumber,sfidPassPortDetailsJson[0].ppissueDate,sfidPassPortDetailsJson[0].ppvalidityDate,
					   sfidPassPortDetailsJson[0].hospitalFlag,sfidPassPortDetailsJson[0].prevCountryDetails,sfidPassPortDetailsJson[0].idCardFlag,
					   sfidPassPortDetailsJson[0].settleFlag,sfidPassPortDetailsJson[0].contractualFlag,sfidPassPortDetailsJson[0].coPeriodFrom,
					   sfidPassPortDetailsJson[0].coPeriodTo,sfidPassPortDetailsJson[0].certifyStatus)
					   
			   }
		    }
	   }
	   else{
			$jq('#letterDetailsDiv').hide();
			 $jq('#passportProceedDetailsDiv').hide();
	   }  
	}
	else{
		 $jq('#passportAppType').val("") ;
		 alert("Please Enter SFID");
	}
  
}

function clearPassPortApplication(){
	$jq('#letterDetailsDiv').hide();
	 $jq('#passportProceedDetailsDiv').hide();
	 $jq('#sub').show();
	 resetPassportAndProceeding();
	
}

function clearPassPortApplicationDetails(){
	$jq('#letterDetailsDiv').hide();
	 $jq('#passportProceedDetailsDiv').hide();
	 $jq('#sub').show();
	 resetPassportAndProceeding();
	 $jq('#result').html('');
}

function getSFIDDetails1(){
	var sID= $jq("#sfID").val(); 
	//resetPassportAndProceeding()
	var status=true;
	 if (sID == "") 
			status = false;
		
	if(status){
		$jq.ajax( {
			type : "GET",
			url : "passport.htm",
			data : "param=sfidDetails&type=sfidDetails&sfid="
				+ sID.toUpperCase(),
			success : function(html) {
			$jq('#result').html(html);
		
			}
		});
	}
}


function adminNoteReport(sfid,adminNo,adminDt){
	window.open('./report.htm?param=adminReport&sfid='+sfid+'&adminNo='+adminNo+'&adminDt='+adminDt,'Leave','fullscreen=no,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no')
}

function passportReport(sfid){
	window.open('./report.htm?param=passportReport&sfid='+sfid,'Leave','fullscreen=no,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no')
}

function updatePassportApplication() {
	var status=true;
	var errorMessage = "";
	var selectedMembers="";

	if($jq('#sfidForAbroad').val() == ""){
		errorMessage += "Select SFID\n";
		status=false;
	}
	if($jq('#hqLetterNo').val() == ""){
		errorMessage += "Enter HQrs Letter No\n";
		status=false;
	}
	if($jq('#hqLetterDt').val() == ""){
		errorMessage += "Select HQrs Letter Date\n";
		status=false;
	}
		if (status) {
			var requestDetails = {
			    "id":nodeID,	
				"sfidForAbroad":$jq("#sfidForAbroad").val(),
				"hqLetterNo":$jq("#hqLetterNo").val(),
				"hqLetterDt":$jq("#hqLetterDt").val(),
				"param" : "updatePassportApplication"
			};
			$jq.ajax( {
				type : "POST",
				url : "passport.htm",
				data : requestDetails,
				cache : false,
				success : function(html) {
					$jq('#result2').html(html);
					nodeID="";
					if ($jq('.success').is(':visible')) {
						clearPassPortApplication();
						
					}
				}
			});
		}else{
			alert(errorMessage);
		}
}

function editPassportAndAbroad(id,sfid,letterNo,letterDt) {
	nodeID=id;
	$jq('#sfidForAbroad').val(sfid);
	$jq('#hqLetterNo').val(letterNo);
	$jq('#hqLetterDt').val(letterDt);
}
