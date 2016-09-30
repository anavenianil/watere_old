var masterID='';

function getAdminMiscReport(type) {
	var status=true;
	var miscType=type;
	var errorMessage="";	
	var adminType=$jq("#adminType").val();
	if ($jq("#sfID").val() == "") {
		errorMessage += "Please Enter SFID.\n";
		if (status) {
			$jq('#sfID').focus();
			status = false;
		}
	}
    if ($jq("#purpose").val() == "") {
		errorMessage += "Please Enter Purpose.\n";
		if (status) {
			$jq('#purpose').focus();
			status = false;
		}
	}
	if ($jq("#letterNo").val() == "") {
		errorMessage += "Please Enter letterNo.\n";
		if (status) {
			$jq('#letterNo').focus();
			status = false;
		}
	}
    if ($jq("#letterDt").val() == "") {
		errorMessage += "Please Select letterDate.\n";
		if (status) {
			$jq('#letterDt').focus();
			status = false;
		}
	}
    
    if ($jq("#adminType").val() == "") {
		errorMessage += "Please Select GazettedType.\n";
		if (status) {
			$jq('#adminType').focus();
			status = false;
		}
	}
	
	if (status) {
		var requestDetails = {
			"id":nodeID,
			"sfID" : $jq("#sfID").val(),
			"purpose" : $jq("#purpose").val(),
			"letterNo" : $jq("#letterNo").val(),
			"letterDt" : $jq("#letterDt").val(),
			"adminType" : $jq("#adminType").val(),
			"param" : "save"
		};
		$jq.ajax( {
			type : "POST",
			url : "adminMisc.htm",
			data : requestDetails,
			cache : false,
			success : function(html) {
			clearFields();
			$jq('#result').html(html);
				//getAdminMiscReport1(miscType,adminType);
			}
		});
	} else{
		alert(errorMessage);
	}
	
}

function getAdminMiscReport1(miscType,idJson,gazType) {
	window.open('./report.htm?param=miscReport&type='+miscType+'&gazType='+gazType+'&requestID='+idJson,'Leave','fullscreen=no,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no')
}

function clearFields(){
	$jq('input:text').val("");
	$jq('Select').val("");
	$jq('textarea').val("");
	masterID=0;
}

function miscLablesView(type){
	if(type == 'residential'){
		document.title='Residential Certificate Report';
		$jq('#headTitle').html('Residential Certificate Report');
		
	}else if(type == 'service'){
		document.title='Service Certificate Report';
		$jq('#headTitle').html('Service Certificate Report');
	}

}

function saveMovableRecord(){
	$jq('#param').val("saveMovableRecord");
	var status=true;
	if(status){
		$jq.post(
		 'adminMisc.htm?id='+masterID,$jq('#adminMisc').serialize(),
		 function(html){
			$jq('#result').html(html);
			clearFields();
		 });	
	}
}

function editMovableProperty(){	
	for(var i=0;i<propertyJson.length;i++){
		masterID=propertyJson[i].id;
		$jq('#acqOrDis').val(propertyJson[i].acqOrDis);
		$jq('#acqOrDisDate').val(propertyJson[i].acqOrDisDate);
		$jq('#propertyActualDate').val(propertyJson[i].propertyActualDate);
		$jq('#modeOfReg').val(propertyJson[i].modeOfReg);
		$jq('#modeOfAcquisition').val(propertyJson[i].modeOfAcquisition);
		$jq('#saleOrPurchasePrice').val(propertyJson[i].saleOrPurchasePrice);
		$jq('#source').val(propertyJson[i].source);
		$jq('#personalSavings').val(propertyJson[i].personalSavings);
		$jq('#otherSource').val(propertyJson[i].otherSource);
		$jq('#partyName').val(propertyJson[i].partyName);
		$jq('#relationship').val(propertyJson[i].relationship);
		$jq('#applicantDealings').val(propertyJson[i].applicantDealings);
		$jq('#officialDealings').val(propertyJson[i].officialDealings);
		$jq('#transaction').val(propertyJson[i].transaction);
		$jq('#acqByGift').val(propertyJson[i].acqByGift);
		$jq('#remarks').val(propertyJson[i].remarks);
		$jq('#purpose').val(propertyJson[i].purpose);
		$jq('#propertyType').val(propertyJson[i].propertyType);	
		$jq('#description').val(propertyJson[i].description);	
	}
}

function deleteMovableProperty(id){	
	var resp=confirm("Do You Want to Delete this record?");
	
	if(resp){
		$jq.post(
			'adminMisc.htm?param=deletePropertyRecord&id='+id,
			function(html){
				('#result').html(html);
				clearFields();
			}
		);
	}
}

function sendRequestToWorkFlow(){	
	if(propertyJson.length>0){
	  var resp=confirm("No Updations Can be done for this record \n\t after Sending a Request \n\t Do You want to continue?");
	  if(resp){
		$jq.post(
			'adminMisc.htm?param=sendRequest&type=movable&id='+propertyJson[0].id,
			function(html){
				$jq('#result').html(html);
				//$jq("#ajaxBusy").remove();
				clearFields();
			}
		);
	  }
	}
	else {
	 alert("Request Cant Send without Data");	
	}
}


























